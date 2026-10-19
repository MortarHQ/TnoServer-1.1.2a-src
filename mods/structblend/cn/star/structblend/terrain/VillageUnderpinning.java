package cn.star.structblend.terrain;

import cn.star.structblend.Config;
import cn.star.structblend.mixin.PoolPieceAccessor;
import cn.star.structblend.mixin.StructureTemplateAccessor;
import com.google.common.collect.MapMaker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunctions.BeardifierOrMarker;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class VillageUnderpinning implements BeardifierOrMarker {
   private static final Map<StructureStart, Boolean> FALLBACK_VILLAGES = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureStart, VillageUnderpinning.Geometry> GEOMETRY = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureTemplate, List<VillageUnderpinning.LocalRun>> MASKS = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureTemplate, List<VillageUnderpinning.LocalRun>> ROOT_MASKS = Collections.synchronizedMap(new WeakHashMap<>());
   private static final ConcurrentMap<RegistryAccess, ConcurrentMap<StructurePoolElement, Optional<ResourceLocation>>> LOCATIONS = new MapMaker()
      .weakKeys()
      .makeMap();
   private final BeardifierOrMarker original;
   private final VillageSupportShape shape;

   public VillageUnderpinning(BeardifierOrMarker original, VillageSupportShape shape) {
      this.original = original;
      this.shape = shape;
   }

   public static boolean isVillage(StructureStart start, StructureManager manager) {
      if (!start.isValid()) {
         return false;
      }

      Registry<Structure> registry = manager.registryAccess().registryOrThrow(Registries.STRUCTURE);
      Optional<ResourceKey<Structure>> key = registry.getResourceKey(start.getStructure());
      return key.isPresent() && registry.getHolderOrThrow(key.get()).is(StructureTags.VILLAGE)
         ? true
         : FALLBACK_VILLAGES.computeIfAbsent(start, VillageUnderpinning::hasVillageLayout);
   }

   static boolean hasVillageLayout(StructureStart start) {
      List<int[]> buildings = new ArrayList<>();

      for (StructurePiece piece : start.getPieces()) {
         if (piece instanceof PoolElementStructurePiece pool && pool.getElement().getProjection() == Projection.RIGID && pool.getGroundLevelDelta() >= 0) {
            BoundingBox box = piece.getBoundingBox();
            int width = box.getXSpan();
            int depth = box.getZSpan();
            int height = box.getYSpan();
            if (height >= 4 && width >= 4 && depth >= 4 && Math.max(width, depth) <= 2 * Math.min(width, depth)) {
               buildings.add(new int[]{box.minX(), box.maxX(), box.minZ(), box.maxZ(), width * depth});
            }
         }
      }

      if (buildings.size() < 4) {
         return false;
      }

      int minX = Integer.MAX_VALUE;
      int maxX = Integer.MIN_VALUE;
      int minZ = Integer.MAX_VALUE;
      int maxZ = Integer.MIN_VALUE;
      int compact = 0;
      int maxArea = 0;

      for (int[] b : buildings) {
         minX = Math.min(minX, b[0]);
         maxX = Math.max(maxX, b[1]);
         minZ = Math.min(minZ, b[2]);
         maxZ = Math.max(maxZ, b[3]);
         maxArea = Math.max(maxArea, b[4]);
         if (b[4] <= 256) {
            compact++;
         }
      }

      int spanX = maxX - minX + 1;
      int spanZ = maxZ - minZ + 1;
      return compact >= 3 && Math.max(spanX, spanZ) >= 48 && Math.min(spanX, spanZ) >= 24 && maxArea * 3L < (long)spanX * spanZ;
   }

   public static boolean isTarget(StructureStart start, StructureManager manager) {
      if (SurfaceBlendPlan.isFixedHeight(start)) {
         return false;
      } else if (DecorativeTemplateFilter.isDecoration(start)) {
         return false;
      } else if (!isVillage(start, manager)) {
         return false;
      } else {
         Structure structure = start.getStructure();
         if (structure.step() == Decoration.SURFACE_STRUCTURES
            && (structure.terrainAdaptation() == TerrainAdjustment.BEARD_THIN || structure.terrainAdaptation() == TerrainAdjustment.BEARD_BOX)) {
            ResourceLocation id = manager.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
            return id != null && !((List)Config.EXCLUDED_STRUCTURES.get()).contains(id.toString());
         } else {
            return false;
         }
      }
   }

   public static VillageSupportShape prepare(ChunkAccess chunk, StructureManager manager, NoiseBasedChunkGenerator generator, RandomState randomState) {
      return prepare(chunk, manager, generator, randomState, null);
   }

   public static VillageSupportShape prepare(
      ChunkAccess chunk, StructureManager manager, NoiseBasedChunkGenerator generator, RandomState randomState, ChunkHeightBatch batch
   ) {
      List<StructureStart> starts = manager.startsForStructure(
         chunk.getPos(),
         structure -> structure.step() == Decoration.SURFACE_STRUCTURES
            && (structure.terrainAdaptation() == TerrainAdjustment.BEARD_THIN || structure.terrainAdaptation() == TerrainAdjustment.BEARD_BOX)
      );
      if (starts.isEmpty()) {
         return null;
      }

      List<VillageSupportShape.Patch> patches = new ArrayList<>();
      List<VillageSupportShape.Protection> protections = new ArrayList<>();
      int minX = chunk.getPos().getMinBlockX();
      int minZ = chunk.getPos().getMinBlockZ();

      for (StructureStart start : starts) {
         if (isTarget(start, manager)) {
            VillageUnderpinning.Geometry geometry = GEOMETRY.get(start);
            if (geometry == null) {
               synchronized (start) {
                  geometry = GEOMETRY.get(start);
                  if (geometry == null) {
                     geometry = geometry(start, manager);
                     GEOMETRY.put(start, geometry);
                  }
               }
            }

            for (VillageSupportShape.Patch patch : geometry.patches) {
               if (patch.maxX() + 6 >= minX && patch.minX() - 6 <= minX + 15 && patch.maxZ() + 6 >= minZ && patch.minZ() - 6 <= minZ + 15) {
                  patches.add(patch);
               }
            }

            protections.addAll(geometry.protections);
         }
      }

      if (patches.isEmpty()) {
         return null;
      }

      SurfaceHeightCache heights = SurfaceHeightCache.forContext(generator, randomState);
      int minY = chunk.getMinBuildHeight();
      if (batch != null) {
         VillageSupportShape.buildWithHeights(minX, minZ, minY, patches, protections, (x, z) -> {
            batch.request((int)x, (int)z);
            return 0L;
         });
         batch.flush();
      }

      return VillageSupportShape.buildWithHeights(minX, minZ, minY, patches, protections, (x, z) -> {
         if (batch != null && batch.has((int)x, (int)z)) {
            return batch.pair((int)x, (int)z);
         }

         if (batch != null) {
            HeightQueryDiagnostics.batchFallback();
         }

         return heights.getPair(generator, randomState, chunk, (int)x, (int)z);
      });
   }

   private static VillageUnderpinning.Geometry geometry(StructureStart start, StructureManager manager) {
      List<VillageSupportShape.Patch> patches = new ArrayList<>();
      List<VillageSupportShape.Protection> protections = new ArrayList<>();
      RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, manager.registryAccess());

      for (StructurePiece piece : start.getPieces()) {
         BoundingBox box = piece.getBoundingBox();
         protections.add(new VillageSupportShape.Protection(box.minX(), box.maxX(), box.minZ(), box.maxZ(), box.minY()));
         if (piece instanceof PoolElementStructurePiece pool && pool.getElement().getProjection() == Projection.RIGID && pool.getGroundLevelDelta() >= 0) {
            ResourceLocation location = templateLocation(pool.getElement(), manager.registryAccess(), ops);
            if (location != null) {
               Optional<StructureTemplate> template = ((PoolPieceAccessor)pool).structblend$getTemplateManager().get(location);
               if (!template.isEmpty()) {
                  List<VillageUnderpinning.LocalRun> runs = supportMask(template.get(), pool.getGroundLevelDelta());
                  int top = Math.min(pool.getPosition().getY(), box.minY()) - 1 + Math.min(1, pool.getGroundLevelDelta());

                  for (VillageUnderpinning.LocalRun run : runs) {
                     BlockPos a = StructureTemplate.transform(new BlockPos(run.minX, 0, run.z), Mirror.NONE, pool.getRotation(), BlockPos.ZERO)
                        .offset(pool.getPosition());
                     BlockPos b = StructureTemplate.transform(new BlockPos(run.maxX, 0, run.z), Mirror.NONE, pool.getRotation(), BlockPos.ZERO)
                        .offset(pool.getPosition());
                     patches.add(
                        new VillageSupportShape.Patch(
                           Math.min(a.getX(), b.getX()), Math.max(a.getX(), b.getX()), Math.min(a.getZ(), b.getZ()), Math.max(a.getZ(), b.getZ()), top
                        )
                     );
                  }
               }
            }
         }
      }

      return new VillageUnderpinning.Geometry(List.copyOf(patches), List.copyOf(protections));
   }

   static ResourceLocation templateLocation(StructurePoolElement element, RegistryAccess access, RegistryOps<Tag> ops) {
      ConcurrentMap<StructurePoolElement, Optional<ResourceLocation>> locations = LOCATIONS.computeIfAbsent(
         access, ignored -> new MapMaker().weakKeys().makeMap()
      );
      Optional<ResourceLocation> cached = locations.get(element);
      if (cached == null) {
         Optional<Tag> encoded = StructurePoolElement.CODEC.encodeStart(ops, element).result();
         cached = Optional.ofNullable(encoded.isPresent() && encoded.get() instanceof CompoundTag tag ? templateLocation(tag) : null);
         locations.put(element, cached);
      }

      return cached.orElse(null);
   }

   static ResourceLocation templateLocation(CompoundTag element) {
      for (int depth = 0; depth < 4 && element.getString("element_type").equals("lithostitched:limited"); depth++) {
         if (!element.contains("delegate", 10)) {
            return null;
         }

         element = element.getCompound("delegate");
      }

      String type = element.getString("element_type");
      if ((type.equals("minecraft:single_pool_element") || type.equals("minecraft:legacy_single_pool_element"))
         && element.getString("projection").equals("rigid")) {
         return element.contains("location", 8) && !element.getString("location").isBlank() ? ResourceLocation.tryParse(element.getString("location")) : null;
      } else {
         return null;
      }
   }

   static List<VillageUnderpinning.LocalRun> supportMask(StructureTemplate template, int groundDelta) {
      if (groundDelta >= 0 && !DecorativeTemplateFilter.isDecoration(template)) {
         List<VillageUnderpinning.LocalRun> earth = MASKS.computeIfAbsent(template, VillageUnderpinning::groundMask);
         if (!earth.isEmpty()) {
            return earth;
         } else {
            return groundDelta == 0 ? ROOT_MASKS.computeIfAbsent(template, VillageUnderpinning::rootedBaseMask) : List.of();
         }
      } else {
         return List.of();
      }
   }

   private static List<VillageUnderpinning.LocalRun> rootedBaseMask(StructureTemplate template) {
      Vec3i size = template.getSize();
      int area = size.getX() * size.getZ();
      int longest = Math.max(size.getX(), size.getZ());
      int shortest = Math.min(size.getX(), size.getZ());
      if (shortest >= 5 && longest <= 2 * shortest && size.getY() >= Math.max(12, longest)) {
         List<Palette> palettes = ((StructureTemplateAccessor)template).structblend$getPalettes();
         if (palettes.size() != 1) {
            return List.of();
         }

         int roots = 0;
         int body = 0;
         int minBodyY = Integer.MAX_VALUE;
         int maxBodyY = Integer.MIN_VALUE;

         for (StructureBlockInfo block : palettes.get(0).blocks()) {
            BlockPos p = block.pos();
            if (p.getX() >= 0 && p.getX() < size.getX() && p.getZ() >= 0 && p.getZ() < size.getZ()) {
               if (p.getY() == 0) {
                  if (block.state().is(BlockTags.LOGS)) {
                     roots++;
                  } else if (block.state().blocksMotion()) {
                     return List.of();
                  }
               }

               if (p.getY() > 0 && block.state().blocksMotion() && !block.state().is(BlockTags.LOGS) && !block.state().is(BlockTags.LEAVES)) {
                  body++;
                  minBodyY = Math.min(minBodyY, p.getY());
                  maxBodyY = Math.max(maxBodyY, p.getY());
               }
            }
         }

         if (roots >= 4 && roots <= area / 4 && body >= Math.max(32, area / 3) && maxBodyY - minBodyY >= 3) {
            List<VillageUnderpinning.LocalRun> rectangularBase = new ArrayList<>();

            for (int z = 0; z < size.getZ(); z++) {
               rectangularBase.add(new VillageUnderpinning.LocalRun(0, size.getX() - 1, z));
            }

            return List.copyOf(rectangularBase);
         } else {
            return List.of();
         }
      } else {
         return List.of();
      }
   }

   static List<VillageUnderpinning.LocalRun> groundMask(StructureTemplate template) {
      if (DecorativeTemplateFilter.isDecoration(template)) {
         return List.of();
      }

      Vec3i size = template.getSize();
      if (size.getX() >= 3 && size.getZ() >= 3 && size.getY() >= 3) {
         List<Palette> palettes = ((StructureTemplateAccessor)template).structblend$getPalettes();
         if (palettes.size() != 1) {
            return List.of();
         }

         boolean[] base = new boolean[size.getX() * size.getZ()];
         int count = 0;

         for (StructureBlockInfo block : palettes.get(0).blocks()) {
            BlockPos p = block.pos();
            if (p.getY() == 0 && p.getX() >= 0 && p.getX() < size.getX() && p.getZ() >= 0 && p.getZ() < size.getZ()) {
               BlockState s = block.state();
               boolean stone = s.is(BlockTags.DIRT)
                  || s.is(BlockTags.BASE_STONE_OVERWORLD)
                  || s.is(Blocks.STONE_BRICKS)
                  || s.is(Blocks.COBBLESTONE)
                  || s.is(Blocks.MOSSY_COBBLESTONE)
                  || s.is(Blocks.SANDSTONE)
                  || s.is(Blocks.CUT_SANDSTONE);
               int i = p.getZ() * size.getX() + p.getX();
               if (stone && !base[i]) {
                  base[i] = true;
                  count++;
               }
            }
         }

         return count >= 16 && count * 4 >= base.length ? runs(base, size.getX(), size.getZ()) : List.of();
      } else {
         return List.of();
      }
   }

   private static List<VillageUnderpinning.LocalRun> runs(boolean[] base, int width, int depth) {
      List<VillageUnderpinning.LocalRun> out = new ArrayList<>();

      for (int z = 0; z < depth; z++) {
         for (int x = 0; x < width; x++) {
            if (base[z * width + x]) {
               int end = x;

               while (end + 1 < width && base[z * width + end + 1]) {
                  end++;
               }

               out.add(new VillageUnderpinning.LocalRun(x, end, z));
               x = end;
            }
         }
      }

      return List.copyOf(out);
   }

   public double compute(FunctionContext c) {
      return this.shape.apply(this.original.compute(c), c.blockX(), c.blockY(), c.blockZ());
   }

   public double minValue() {
      return Double.NEGATIVE_INFINITY;
   }

   public double maxValue() {
      return Double.POSITIVE_INFINITY;
   }

   private record Geometry(List<VillageSupportShape.Patch> patches, List<VillageSupportShape.Protection> protections) {
   }

   record LocalRun(int minX, int maxX, int z) {
   }
}
