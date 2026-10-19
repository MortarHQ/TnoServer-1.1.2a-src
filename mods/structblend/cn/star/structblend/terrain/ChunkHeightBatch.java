package cn.star.structblend.terrain;

import cn.star.structblend.mixin.NoiseChunkInvoker;
import cn.star.structblend.mixin.NoiseGeneratorAccessor;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.DensityFunctions.BeardifierOrMarker;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.service.MixinService;

public final class ChunkHeightBatch {
   static final long NO_PAIR = Long.MIN_VALUE;
   private static final int SIZE = 17;
   private static final int FULLY_VERIFIED_FLUSHES = 8;
   private static final int SAMPLE_INTERVAL = 64;
   private static final boolean VERIFY_ALL = Boolean.getBoolean("structblend.heightBatchVerifyAll");
   private static final byte UNKNOWN = 0;
   private static final byte PENDING = 1;
   private static final byte KNOWN = 2;
   private static final Set<String> HEIGHT_METHODS = Set.of("getBaseHeight", "iterateNoiseColumn");
   private static final String OWN_MIXINS = "cn.star.structblend.mixin.";
   private static final String OVERWRITE = "Lorg/spongepowered/asm/mixin/Overwrite;";
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Object HOOK_LOCK = new Object();
   private static final BeardifierOrMarker NO_STRUCTURES = noStructures();
   static volatile boolean disabledForTest;
   private static volatile List<String> foreignHooks;
   private final NoiseBasedChunkGenerator generator;
   private final ChunkAccess chunk;
   private final RandomState randomState;
   private final int minX;
   private final int minZ;
   private final long[] pairs = new long[289];
   private final byte[] states = new byte[289];
   private SurfaceHeightCache cache;
   private int pending;
   private boolean trusted = true;

   private ChunkHeightBatch(NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState) {
      this.generator = generator;
      this.chunk = chunk;
      this.randomState = randomState;
      this.minX = chunk.getPos().getMinBlockX();
      this.minZ = chunk.getPos().getMinBlockZ();
   }

   public static ChunkHeightBatch create(NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState) {
      return !disabledForTest && NO_STRUCTURES != null && generator.getClass() == NoiseBasedChunkGenerator.class
         ? new ChunkHeightBatch(generator, chunk, randomState)
         : null;
   }

   static ChunkHeightBatch forTest(NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState) {
      return new ChunkHeightBatch(generator, chunk, randomState);
   }

   public static void logStatus() {
      if (NO_STRUCTURES == null) {
         LOGGER.info("[StructBlend heights] chunk-wide height scanning OFF (BeardifierMarker unavailable)");
      } else {
         List<String> hooks = foreignHeightHooks();
         LOGGER.info(
            "[StructBlend heights] chunk-wide height scanning ON (runtime verification active){}", hooks.isEmpty() ? "" : "; other height hooks: " + hooks
         );
      }
   }

   boolean has(int x, int z) {
      int index = this.index(x, z);
      return index >= 0 && this.states[index] == 2 && this.trusted && !this.cache.batchGuard.disabled;
   }

   long pair(int x, int z) {
      return this.pairs[this.index(x, z)];
   }

   void request(int x, int z) {
      int index = this.index(x, z);
      if (this.trusted && index >= 0 && this.states[index] == 0) {
         this.states[index] = 1;
         this.pending++;
      }
   }

   boolean flush() {
      if (this.pending != 0 && this.trusted) {
         if (this.cache == null) {
            this.cache = SurfaceHeightCache.forContext(this.generator, this.randomState);
         }

         if (this.cache.batchGuard.disabled) {
            this.trusted = false;
            return false;
         }

         int[] columns = new int[this.pending];
         int count = 0;
         int cached = 0;

         for (int index = 0; index < this.states.length; index++) {
            if (this.states[index] == 1) {
               long hit = this.cache.cachedPair(this.minX + index % 17, this.minZ + index / 17, this.chunk);
               if (hit != Long.MIN_VALUE) {
                  this.pairs[index] = hit;
                  this.states[index] = 2;
                  cached++;
               } else {
                  columns[count++] = index;
               }
            }
         }

         this.pending = 0;
         long started = HeightQueryDiagnostics.ENABLED ? System.nanoTime() : 0L;
         if (count > 0) {
            this.scan(columns, count);
            this.verify(columns, count);
         }

         HeightQueryDiagnostics.batchFlushed(count, cached, HeightQueryDiagnostics.ENABLED ? System.nanoTime() - started : 0L);
         return true;
      } else {
         return false;
      }
   }

   long[] scanForTest(int[] xs, int[] zs) {
      int[] columns = new int[xs.length];

      for (int k = 0; k < xs.length; k++) {
         columns[k] = this.index(xs[k], zs[k]);
      }

      this.scan(columns, columns.length);
      long[] result = new long[xs.length];

      for (int k = 0; k < xs.length; k++) {
         result[k] = this.pairs[columns[k]];
      }

      return result;
   }

   boolean trusted() {
      return this.trusted;
   }

   private int index(int x, int z) {
      int localX = x - this.minX;
      int localZ = z - this.minZ;
      return localX >= 0 && localZ >= 0 && localX < 17 && localZ < 17 ? localZ * 17 + localX : -1;
   }

   private void scan(int[] columns, int count) {
      NoiseGeneratorSettings settings = (NoiseGeneratorSettings)this.generator.generatorSettings().value();
      NoiseSettings noise = settings.noiseSettings().clampToHeightAccessor(this.chunk);
      int cellWidth = noise.getCellWidth();
      int cellHeight = noise.getCellHeight();
      int cellMinY = Math.floorDiv(noise.minY(), cellHeight);
      int cellCountY = Math.floorDiv(noise.height(), cellHeight);
      int[] surfaces = new int[289];
      int[] floors = new int[289];
      Arrays.fill(surfaces, Integer.MIN_VALUE);
      Arrays.fill(floors, Integer.MIN_VALUE);
      if (cellCountY > 0) {
         int minCellX = Integer.MAX_VALUE;
         int minCellZ = Integer.MAX_VALUE;
         int maxCellX = Integer.MIN_VALUE;
         int maxCellZ = Integer.MIN_VALUE;

         for (int k = 0; k < count; k++) {
            int cellX = Math.floorDiv(this.minX + columns[k] % 17, cellWidth);
            int cellZ = Math.floorDiv(this.minZ + columns[k] / 17, cellWidth);
            minCellX = Math.min(minCellX, cellX);
            maxCellX = Math.max(maxCellX, cellX);
            minCellZ = Math.min(minCellZ, cellZ);
            maxCellZ = Math.max(maxCellZ, cellZ);
         }

         int cellsX = maxCellX - minCellX + 1;
         int cellsZ = maxCellZ - minCellZ + 1;
         int[] remaining = new int[cellsX * cellsZ];
         boolean[] wanted = new boolean[289];

         for (int k = 0; k < count; k++) {
            int cellX = Math.floorDiv(this.minX + columns[k] % 17, cellWidth) - minCellX;
            int cellZ = Math.floorDiv(this.minZ + columns[k] / 17, cellWidth) - minCellZ;
            wanted[columns[k]] = true;
            remaining[cellX * cellsZ + cellZ]++;
         }

         int left = count;
         Predicate<BlockState> surfaceTest = Types.WORLD_SURFACE_WG.isOpaque();
         Predicate<BlockState> floorTest = Types.OCEAN_FLOOR_WG.isOpaque();
         BlockState defaultBlock = settings.defaultBlock();
         NoiseChunk noiseChunk = new NoiseChunk(
            Math.max(cellsX, cellsZ),
            this.randomState,
            minCellX * cellWidth,
            minCellZ * cellWidth,
            noise,
            NO_STRUCTURES,
            settings,
            ((NoiseGeneratorAccessor)this.generator).structblend$getGlobalFluidPicker().get(),
            Blender.empty()
         );
         NoiseChunkInvoker blocks = (NoiseChunkInvoker)noiseChunk;
         noiseChunk.initializeForFirstCellX();

         try {
            for (int cellX = 0; cellX < cellsX && left > 0; cellX++) {
               noiseChunk.advanceCellX(cellX);
               int blockX = (minCellX + cellX) * cellWidth;

               for (int cellZ = 0; cellZ < cellsZ; cellZ++) {
                  int cell = cellX * cellsZ + cellZ;
                  int blockZ = (minCellZ + cellZ) * cellWidth;

                  for (int cellY = cellCountY - 1; cellY >= 0 && remaining[cell] > 0; cellY--) {
                     noiseChunk.selectCellYZ(cellY, cellZ);

                     for (int inY = cellHeight - 1; inY >= 0 && remaining[cell] > 0; inY--) {
                        int y = (cellMinY + cellY) * cellHeight + inY;
                        noiseChunk.updateForY(y, (double)inY / cellHeight);

                        for (int inX = 0; inX < cellWidth; inX++) {
                           int localX = blockX + inX - this.minX;
                           if (localX >= 0 && localX < 17) {
                              boolean xUpdated = false;

                              for (int inZ = 0; inZ < cellWidth; inZ++) {
                                 int localZ = blockZ + inZ - this.minZ;
                                 if (localZ >= 0 && localZ < 17) {
                                    int index = localZ * 17 + localX;
                                    if (wanted[index]) {
                                       if (!xUpdated) {
                                          noiseChunk.updateForX(blockX + inX, (double)inX / cellWidth);
                                          xUpdated = true;
                                       }

                                       noiseChunk.updateForZ(blockZ + inZ, (double)inZ / cellWidth);
                                       BlockState state = blocks.structblend$getInterpolatedState();
                                       if (state == null) {
                                          state = defaultBlock;
                                       }

                                       if (surfaces[index] == Integer.MIN_VALUE && surfaceTest.test(state)) {
                                          surfaces[index] = y;
                                       }

                                       if (floors[index] == Integer.MIN_VALUE && floorTest.test(state)) {
                                          floors[index] = y;
                                       }

                                       if (surfaces[index] != Integer.MIN_VALUE && floors[index] != Integer.MIN_VALUE) {
                                          wanted[index] = false;
                                          remaining[cell]--;
                                          left--;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               noiseChunk.swapSlices();
            }
         } finally {
            noiseChunk.stopInterpolation();
         }
      }

      int absent = this.chunk.getMinBuildHeight() - 1;

      for (int k = 0; k < count; k++) {
         int index = columns[k];
         this.pairs[index] = SurfaceHeightCache.pack(
            surfaces[index] == Integer.MIN_VALUE ? absent : surfaces[index], floors[index] == Integer.MIN_VALUE ? absent : floors[index]
         );
         this.states[index] = 2;
      }
   }

   private void verify(int[] columns, int count) {
      ChunkHeightBatch.Guard guard = this.cache.batchGuard;
      long flush = guard.flushes.incrementAndGet();
      int checks = !VERIFY_ALL && flush > 8L ? (flush % 64L == 0L ? 1 : 0) : count;
      int mismatches = 0;

      for (int k = 0; k < checks; k++) {
         int index = columns[k];
         int x = this.minX + index % 17;
         int z = this.minZ + index / 17;
         long expected = this.cache.getPair(this.generator, this.randomState, this.chunk, x, z);
         if (expected != this.pairs[index]) {
            mismatches++;
            if (guard.reported.compareAndSet(false, true)) {
               LOGGER.error(
                  "[StructBlend heights] chunk-wide scan differs from public height query at {},{}: batch surface/floor={}/{} public={}/{}; falling back to per-column queries for this dimension.",
                  new Object[]{
                     x,
                     z,
                     SurfaceHeightCache.surface(this.pairs[index]),
                     SurfaceHeightCache.floor(this.pairs[index]),
                     SurfaceHeightCache.surface(expected),
                     SurfaceHeightCache.floor(expected)
                  }
               );
            }
         }
      }

      HeightQueryDiagnostics.batchVerified(checks, mismatches);
      if (mismatches > 0) {
         guard.disabled = true;
         this.trusted = false;
      }
   }

   private static BeardifierOrMarker noStructures() {
      try {
         Object[] constants = Class.forName(DensityFunctions.class.getName() + "$BeardifierMarker", true, DensityFunctions.class.getClassLoader())
            .getEnumConstants();
         if (constants != null && constants.length == 1 && constants[0] instanceof BeardifierOrMarker marker) {
            return marker;
         }

         LOGGER.warn("[StructBlend heights] unexpected BeardifierMarker shape; chunk-wide height scanning disabled");
      } catch (ReflectiveOperationException | RuntimeException | LinkageError failure) {
         LOGGER.warn("[StructBlend heights] BeardifierMarker unavailable; chunk-wide height scanning disabled", failure);
      }

      return null;
   }

   static List<String> foreignHeightHooks() {
      List<String> hooks = foreignHooks;
      if (hooks == null) {
         synchronized (HOOK_LOCK) {
            hooks = foreignHooks;
            if (hooks == null) {
               hooks = List.copyOf(scanForeignHooks());
               foreignHooks = hooks;
            }
         }
      }

      return hooks;
   }

   private static List<String> scanForeignHooks() {
      try {
         Set<String> sources = new LinkedHashSet<>();
         List<String> hooks = new ArrayList<>();

         for (Method method : NoiseBasedChunkGenerator.class.getDeclaredMethods()) {
            MixinMerged merged = method.getAnnotation(MixinMerged.class);
            String mixin = merged == null ? null : merged.mixin().replace('/', '.');
            if (mixin != null && !mixin.startsWith("cn.star.structblend.mixin.")) {
               if (HEIGHT_METHODS.contains(method.getName())) {
                  hooks.add(mixin + " (overwrites " + method.getName() + ")");
               }

               sources.add(mixin);
            }
         }

         for (String mixin : sources) {
            ClassNode node = mixinClass(mixin);
            if (node == null) {
               hooks.add(mixin + " (class bytes unavailable)");
            } else if (targetsHeight(node)) {
               hooks.add(mixin);
            }
         }

         return hooks;
      } catch (Throwable failure) {
         return List.of("<scan failed: " + failure + ">");
      }
   }

   private static ClassNode mixinClass(String name) {
      try {
         return MixinService.getService().getBytecodeProvider().getClassNode(name.replace('.', '/'));
      } catch (Exception | LinkageError var7) {
         try (InputStream in = NoiseBasedChunkGenerator.class.getClassLoader().getResourceAsStream(name.replace('.', '/') + ".class")) {
            if (in == null) {
               return null;
            }

            ClassNode node = new ClassNode();
            new ClassReader(in.readAllBytes()).accept(node, 1);
            return node;
         } catch (IOException | RuntimeException failure) {
            return null;
         }
      }
   }

   static boolean targetsHeight(ClassNode node) {
      for (MethodNode method : node.methods) {
         for (List<AnnotationNode> annotations : Arrays.asList(method.visibleAnnotations, method.invisibleAnnotations)) {
            if (annotations != null) {
               for (AnnotationNode annotation : annotations) {
                  if ("Lorg/spongepowered/asm/mixin/Overwrite;".equals(annotation.desc) && HEIGHT_METHODS.contains(method.name)) {
                     return true;
                  }

                  if (annotation.values != null) {
                     for (int i = 0; i + 1 < annotation.values.size(); i += 2) {
                        if ("method".equals(annotation.values.get(i)) && selectsHeight(annotation.values.get(i + 1))) {
                           return true;
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   static boolean selectsHeight(Object value) {
      if (value instanceof List) {
         for (Object entry : (List)value) {
            if (selectsHeight(entry)) {
               return true;
            }
         }

         return false;
      } else if (!(value instanceof String selector)) {
         return false;
      } else if (!selector.contains("*") && !selector.startsWith("/")) {
         String name = selector;
         int owner = name.indexOf(59);
         int descriptor = name.indexOf(40);
         if (name.startsWith("L") && owner >= 0 && (descriptor < 0 || owner < descriptor)) {
            name = name.substring(owner + 1);
            descriptor = name.indexOf(40);
         }

         return HEIGHT_METHODS.contains(descriptor >= 0 ? name.substring(0, descriptor) : name);
      } else {
         return true;
      }
   }

   static final class Guard {
      private final AtomicLong flushes = new AtomicLong();
      private final AtomicBoolean reported = new AtomicBoolean();
      volatile boolean disabled;

      long flushes() {
         return this.flushes.get();
      }
   }
}
