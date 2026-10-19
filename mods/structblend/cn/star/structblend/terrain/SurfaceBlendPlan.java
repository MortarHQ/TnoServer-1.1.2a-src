package cn.star.structblend.terrain;

import cn.star.structblend.Config;
import cn.star.structblend.mixin.JigsawStructureAccessor;
import cn.star.structblend.mixin.PoolPieceAccessor;
import cn.star.structblend.mixin.SinglePoolElementInvoker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public final class SurfaceBlendPlan {
   public static final int RADIUS = 16;
   public static final int BROAD_SUPPORT_RADIUS = 24;
   public static final int REFERENCE_RADIUS = Math.max(16, 24);
   public static final int MAX_REFERENCE_RADIUS = Math.max(REFERENCE_RADIUS, 6);
   private static final int RADIUS_SQUARED = 256;
   private static final int WATER_SUPPORT_RADIUS = 6;
   private static final int WATER_SUPPORT_RADIUS_SQUARED = 36;
   private static final int CLEARANCE_RADIUS = 4;
   private static final int CLEARANCE_RADIUS_SQUARED = 16;
   private static final int GROUND_LEVEL_TOLERANCE = 4;
   private static final int SURFACE_ENTRANCE_TOLERANCE = 6;
   private static final int MAX_WATER_SUPPORT_DEPTH = 48;
   private static final int MAX_SUBMERGED_SEABED_GAP = 12;
   private static final int MAX_DEEP_SUPPORT_DEPTH = 48;
   private static final int MAX_PREDICTION_PASSES = 6;
   private static final SurfaceBlendPlan.Anchor NO_ANCHOR = new SurfaceBlendPlan.Anchor(false, 0, false, 0);
   private static final SurfaceBlendPlan.PyramidBase DRY_PYRAMID = new SurfaceBlendPlan.PyramidBase(false, 0);
   private static final Map<StructureStart, SurfaceBlendPlan.Anchor> ANCHORS = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureStart, Boolean> COMPACT_RIGID = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureStart, Boolean> MARINE_PLATFORMS = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureStart, SurfaceBlendPlan.PyramidBase> PYRAMID_BASES = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureTemplateManager, Map<SinglePoolElement, FoundationMask>> ELEMENT_MASKS = new WeakHashMap<>();
   private final int minX;
   private final int minZ;
   private final float[] natural = new float[256];
   private final float[] target = new float[256];
   private final boolean[] deepSupport = new boolean[256];
   private final boolean[] broadSupport = new boolean[256];
   private final float[] underpinStrength = new float[256];
   private final int[] underpinTop = new int[256];
   private final float[] clearanceStrength = new float[256];
   private final int[] clearanceFloor = new int[256];
   private final int[] contactTop = new int[256];
   static volatile boolean speculativeFineGrid = true;
   static volatile boolean clearBodyOnly = true;

   private SurfaceBlendPlan(ChunkPos pos) {
      this.minX = pos.getMinBlockX();
      this.minZ = pos.getMinBlockZ();
      Arrays.fill(this.target, Float.NaN);
      Arrays.fill(this.clearanceFloor, Integer.MAX_VALUE);
      Arrays.fill(this.contactTop, Integer.MIN_VALUE);
   }

   private SurfaceBlendPlan(SurfaceBlendPlan other) {
      this.minX = other.minX;
      this.minZ = other.minZ;
      System.arraycopy(other.natural, 0, this.natural, 0, 256);
      System.arraycopy(other.target, 0, this.target, 0, 256);
      System.arraycopy(other.deepSupport, 0, this.deepSupport, 0, 256);
      System.arraycopy(other.broadSupport, 0, this.broadSupport, 0, 256);
      System.arraycopy(other.underpinStrength, 0, this.underpinStrength, 0, 256);
      System.arraycopy(other.underpinTop, 0, this.underpinTop, 0, 256);
      System.arraycopy(other.clearanceStrength, 0, this.clearanceStrength, 0, 256);
      System.arraycopy(other.clearanceFloor, 0, this.clearanceFloor, 0, 256);
      System.arraycopy(other.contactTop, 0, this.contactTop, 0, 256);
   }

   private void assignSegments(List<SurfaceBlendPlan.Segment> segments, int[] bestDistance, int[] foundationY, boolean[] canUnderpin) {
      Arrays.fill(bestDistance, REFERENCE_RADIUS * REFERENCE_RADIUS + 1);

      for (SurfaceBlendPlan.Segment segment : segments) {
         int radius = segment.allowUnderpin && segment.broadFoundation ? 24 : 16;

         for (int z = Math.max(this.minZ, segment.minZ - radius); z <= Math.min(this.minZ + 15, segment.maxZ + radius); z++) {
            int dz = z < segment.minZ ? segment.minZ - z : Math.max(0, z - segment.maxZ);

            for (int x = Math.max(this.minX, segment.minX - radius); x <= Math.min(this.minX + 15, segment.maxX + radius); x++) {
               int dx = x < segment.minX ? segment.minX - x : Math.max(0, x - segment.maxX);
               int distance = dx * dx + dz * dz;
               if (distance < radius * radius) {
                  int index = z - this.minZ << 4 | x - this.minX;
                  if (distance < bestDistance[index] || distance == bestDistance[index] && segment.foundationY < foundationY[index]) {
                     bestDistance[index] = distance;
                     foundationY[index] = segment.foundationY;
                     this.deepSupport[index] = segment.deepSupport;
                     canUnderpin[index] = segment.allowUnderpin;
                     this.broadSupport[index] = segment.allowUnderpin && segment.broadFoundation;
                     this.underpinStrength[index] = this.broadSupport[index] ? 1.0F : 0.0F;
                  }
               }
            }
         }
      }
   }

   public static SurfaceBlendPlan prepare(ChunkAccess chunk, StructureManager manager, NoiseBasedChunkGenerator generator, RandomState randomState) {
      return prepare(chunk, manager, generator, randomState, null);
   }

   public static SurfaceBlendPlan prepare(
      ChunkAccess chunk, StructureManager manager, NoiseBasedChunkGenerator generator, RandomState randomState, ChunkHeightBatch batch
   ) {
      ChunkPos pos = chunk.getPos();
      int minX = pos.getMinBlockX();
      int minZ = pos.getMinBlockZ();
      List<SurfaceBlendPlan.Segment> segments = new ArrayList<>();
      List<SurfaceBlendPlan.ClearanceBox> clearanceBoxes = new ArrayList<>();

      for (StructureStart start : manager.startsForStructure(pos, structure -> true)) {
         if (isEligible(start, manager) && !shouldRejectMarinePlatform(start, generator, randomState, chunk)) {
            if (isDesertPyramid(start)) {
               appendDesertPyramid(segments, start, generator, chunk, randomState, minX, minZ);
            } else {
               SurfaceBlendPlan.Anchor anchor = anchorFor(start, generator, chunk, randomState);
               if (anchor.valid()) {
                  boolean surfaceEntrance = start.getStructure().step() == Decoration.UNDERGROUND_STRUCTURES;

                  for (StructurePiece piece : start.getPieces()) {
                     if (piece instanceof PoolElementStructurePiece poolPiece
                        && poolPiece.getElement().getProjection() == Projection.RIGID
                        && poolPiece.getElement() instanceof SinglePoolElement single) {
                        BoundingBox box = poolPiece.getBoundingBox();
                        if (box.intersects(minX - REFERENCE_RADIUS, minZ - REFERENCE_RADIUS, minX + 15 + REFERENCE_RADIUS, minZ + 15 + REFERENCE_RADIUS)) {
                           FoundationMask mask = maskFor(poolPiece, single);
                           if (!mask.isEmpty()
                              && (mask.hasVerticalBody() || anchor.wet() && mask.hasSolidBase() && mask.isBroad())
                              && (!mask.hasOpenBase() || anchor.wet())
                              && !isLooseWoodAtWaterSurface(mask, poolPiece, generator, chunk, randomState)) {
                              if (box.minY() >= anchor.foundationY() - 4 - 2 && box.maxY() >= anchor.foundationY() + 2) {
                                 int floorY = Math.max(anchor.groundY(), box.minY() - 2);
                                 if (clearBodyOnly) {
                                    appendClearance(clearanceBoxes, poolPiece, mask, floorY, minX, minZ);
                                 } else {
                                    clearanceBoxes.add(new SurfaceBlendPlan.ClearanceBox(box.minX(), box.maxX(), box.minZ(), box.maxZ(), floorY));
                                 }
                              }

                              boolean nearGround = Math.abs(poolPiece.getPosition().getY() - anchor.foundationY()) <= 11;
                              boolean lowerBody = surfaceEntrance
                                 && box.minY() < anchor.foundationY() - 4
                                 && box.minY() >= anchor.foundationY() - 48 - 4
                                 && box.maxY() >= anchor.foundationY() - 4;
                              if (nearGround || lowerBody) {
                                 appendSegments(segments, poolPiece, mask, anchor.foundationY(), lowerBody, !surfaceEntrance, anchor.wet(), minX, minZ);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (segments.isEmpty() && clearanceBoxes.isEmpty()) {
         return null;
      }

      SurfaceBlendPlan plan = new SurfaceBlendPlan(pos);
      int[] bestDistance = new int[256];
      int[] foundationY = new int[256];
      boolean[] canUnderpin = new boolean[256];
      plan.assignSegments(segments, bestDistance, foundationY, canUnderpin);

      for (SurfaceBlendPlan.ClearanceBox box : clearanceBoxes) {
         for (int z = Math.max(minZ, box.minZ - 4); z <= Math.min(minZ + 15, box.maxZ + 4); z++) {
            int dz = z < box.minZ ? box.minZ - z : Math.max(0, z - box.maxZ);

            for (int x = Math.max(minX, box.minX - 4); x <= Math.min(minX + 15, box.maxX + 4); x++) {
               int dx = x < box.minX ? box.minX - x : Math.max(0, x - box.maxX);
               int distance = dx * dx + dz * dz;
               if (distance < 16) {
                  double t = 1.0 - Math.sqrt(distance) / 4.0;
                  float strength = (float)(t * t * (3.0 - 2.0 * t));
                  int index = z - minZ << 4 | x - minX;
                  plan.clearanceStrength[index] = Math.max(plan.clearanceStrength[index], strength);
                  plan.clearanceFloor[index] = Math.min(plan.clearanceFloor[index], box.floorY);
               }
            }
         }
      }

      if (batch != null) {
         for (int pass = 0; pass < 6; pass++) {
            SurfaceBlendPlan.HeightSamples probe = new SurfaceBlendPlan.HeightSamples(generator, chunk, randomState, minX, minZ, batch, true);
            HeightQueryDiagnostics.withoutCounters(() -> columns(new SurfaceBlendPlan(plan), bestDistance, foundationY, canUnderpin, probe, generator));
            HeightQueryDiagnostics.predictionPass();
            if (pass == 0 && speculativeFineGrid) {
               probe.requestFineGrid();
            }

            if (!batch.flush()) {
               break;
            }
         }
      }

      return columns(
         plan, bestDistance, foundationY, canUnderpin, new SurfaceBlendPlan.HeightSamples(generator, chunk, randomState, minX, minZ, batch, false), generator
      );
   }

   static void clearMaskCachesForTest() {
      FoundationMask.clearCacheForTest();
      synchronized (ELEMENT_MASKS) {
         ELEMENT_MASKS.clear();
      }

      ANCHORS.clear();
      MARINE_PLATFORMS.clear();
   }

   private static SurfaceBlendPlan columns(
      SurfaceBlendPlan plan,
      int[] bestDistance,
      int[] foundationY,
      boolean[] canUnderpin,
      SurfaceBlendPlan.HeightSamples heights,
      NoiseBasedChunkGenerator generator
   ) {
      int seaLevel = generator.getSeaLevel();
      boolean any = false;

      for (int z = 0; z < 16; z++) {
         for (int x = 0; x < 16; x++) {
            int index = z << 4 | x;
            int distanceSquared = bestDistance[index];
            boolean broadUnderpin = plan.broadSupport[index];
            int blendRadius = broadUnderpin ? 24 : 16;
            int blendRadiusSquared = blendRadius * blendRadius;
            if (distanceSquared < blendRadiusSquared || plan.clearanceStrength[index] != 0.0F) {
               double surface = heights.at(x, z);
               double original = surface;
               int baseY = foundationY[index] - 1;
               boolean waterSupport = false;
               if (canUnderpin[index] && surface <= seaLevel + 1 && distanceSquared < (broadUnderpin ? blendRadiusSquared : 36)) {
                  double floor = heights.groundAt(x, z);
                  waterSupport = surface - floor > 2.0 && baseY >= floor - 1.0 && baseY - floor <= 48.0 && heights.wetAt(x, z, surface);
                  if (waterSupport) {
                     original = floor;
                  }
               }

               if (distanceSquared < blendRadiusSquared && (plan.deepSupport[index] || baseY > surface)) {
                  double floor = heights.groundAt(x, z);
                  if (floor != original) {
                     if (heights.wetAt(x, z, surface)) {
                        original = floor;
                     }
                  } else if (HeightQueryDiagnostics.ENABLED) {
                     HeightQueryDiagnostics.floorReplacementSkipped();
                  }
               }

               plan.natural[index] = (float)original;
               double fillWeight = distanceSquared >= blendRadiusSquared || !broadUnderpin && !(baseY > original)
                  ? 0.0
                  : SlopeVariation.fillWeight(1.0 - Math.sqrt(distanceSquared) / blendRadius, x, z);
               if (distanceSquared < blendRadiusSquared && (broadUnderpin || waterSupport)) {
                  int radius = broadUnderpin ? blendRadius : 6;
                  double t = 1.0 - Math.sqrt(distanceSquared) / radius;
                  plan.underpinStrength[index] = (float)(broadUnderpin ? fillWeight : t * t * (3.0 - 2.0 * t));
                  plan.underpinTop[index] = baseY;
                  any = true;
               }

               if (distanceSquared == 0 && !plan.deepSupport[index] && baseY >= original && baseY - original <= 2.0) {
                  plan.contactTop[index] = baseY;
                  any = true;
               }

               int terrainRadius = baseY >= original ? blendRadius : 16;
               if (distanceSquared < terrainRadius * terrainRadius && (!plan.deepSupport[index] || baseY > original)) {
                  double t = 1.0 - Math.sqrt(distanceSquared) / terrainRadius;
                  double weight = baseY > original ? fillWeight : t * t * (3.0 - 2.0 * t);
                  double desired = original + (baseY - original) * weight;
                  if (Math.abs(desired - original) >= 0.75) {
                     if (desired < original) {
                        if (original >= seaLevel) {
                           desired = Math.max(desired, seaLevel);
                        } else if (heights.exactSurfaceAt(x, z) >= seaLevel) {
                           desired = original;
                        }
                     }

                     if (Math.abs(desired - original) >= 0.75) {
                        plan.target[index] = (float)desired;
                        any = true;
                     }
                  } else if (HeightQueryDiagnostics.ENABLED && desired < original && original < seaLevel) {
                     HeightQueryDiagnostics.smallCutCheckSkipped();
                  }
               }

               if (plan.clearanceStrength[index] > 0.0F) {
                  plan.clearanceFloor[index] = Math.max(plan.clearanceFloor[index], seaLevel);
                  if (original > plan.clearanceFloor[index]) {
                     any = true;
                  } else {
                     plan.clearanceStrength[index] = 0.0F;
                  }
               }
            }
         }
      }

      return any ? plan : null;
   }

   public float natural(int worldX, int worldZ) {
      return this.natural[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public float target(int worldX, int worldZ) {
      return this.target[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public boolean deepSupport(int worldX, int worldZ) {
      return this.deepSupport[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public float underpinStrength(int worldX, int worldZ) {
      return this.underpinStrength[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public int underpinTop(int worldX, int worldZ) {
      return this.underpinTop[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public boolean broadSupport(int worldX, int worldZ) {
      return this.broadSupport[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public float clearanceStrength(int worldX, int worldZ) {
      return this.clearanceStrength[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public int clearanceFloor(int worldX, int worldZ) {
      return this.clearanceFloor[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public int contactTop(int worldX, int worldZ) {
      return this.contactTop[worldZ - this.minZ << 4 | worldX - this.minX];
   }

   public boolean contains(int worldX, int worldZ) {
      return worldX >= this.minX && worldX < this.minX + 16 && worldZ >= this.minZ && worldZ < this.minZ + 16;
   }

   public static boolean shouldRejectMarinePlatform(StructureStart start, ChunkGenerator generator, RandomState randomState, LevelHeightAccessor heightAccessor) {
      if (generator instanceof NoiseBasedChunkGenerator
         && start.isValid()
         && start.getStructure().step() == Decoration.SURFACE_STRUCTURES
         && start.getPieces().size() == 1
         && start.getPieces().get(0) instanceof PoolElementStructurePiece piece
         && piece.getElement().getProjection() == Projection.RIGID
         && piece.getElement() instanceof SinglePoolElement single) {
         BoundingBox box = piece.getBoundingBox();
         int spanX = box.maxX() - box.minX() + 1;
         int spanZ = box.maxZ() - box.minZ() + 1;
         if (spanX <= 24
            && spanZ <= 24
            && (long)spanX * spanZ <= 256L
            && Math.abs(box.minY() - generator.getSeaLevel()) <= 8
            && maskFor(piece, single).isSandstonePlatform()) {
            Boolean cached = MARINE_PLATFORMS.get(start);
            if (cached != null) {
               return cached;
            }

            boolean marine = isMarineSite(box, generator, randomState, heightAccessor);
            MARINE_PLATFORMS.put(start, marine);
            return marine;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean isMarineSite(BoundingBox box, ChunkGenerator generator, RandomState randomState, LevelHeightAccessor heightAccessor) {
      int centerX = (box.minX() + box.maxX()) / 2;
      int centerZ = (box.minZ() + box.maxZ()) / 2;
      if (isWaterColumn(generator, randomState, heightAccessor, centerX, centerZ)) {
         return true;
      }

      int wetCorners = 0;

      for (int x : new int[]{box.minX() - 2, box.maxX() + 2}) {
         for (int z : new int[]{box.minZ() - 2, box.maxZ() + 2}) {
            if (isWaterColumn(generator, randomState, heightAccessor, x, z)) {
               wetCorners++;
            }
         }
      }

      return wetCorners >= 2;
   }

   private static boolean isWaterColumn(ChunkGenerator generator, RandomState randomState, LevelHeightAccessor heightAccessor, int x, int z) {
      return generator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG, heightAccessor, randomState)
            - generator.getBaseHeight(x, z, Types.OCEAN_FLOOR_WG, heightAccessor, randomState)
         > 2;
   }

   public static boolean isFixedHeight(StructureStart start) {
      return start.getStructure() instanceof JigsawStructure jigsaw && ((JigsawStructureAccessor)jigsaw).structblend$getProjectStartToHeightmap().isEmpty();
   }

   public static boolean isEligible(StructureStart start, StructureManager manager) {
      if (isFixedHeight(start)) {
         return false;
      }

      if (DecorativeTemplateFilter.isDecoration(start)) {
         return false;
      }

      if (VillageUnderpinning.isVillage(start, manager)) {
         return false;
      }

      Decoration step = start.getStructure().step();
      if ((isDesertPyramid(start) || isCompactRigid(start))
         && (step == Decoration.SURFACE_STRUCTURES || step == Decoration.UNDERGROUND_STRUCTURES)
         && start.getStructure().terrainAdaptation() != TerrainAdjustment.BURY) {
         if (((List)Config.EXCLUDED_STRUCTURES.get()).isEmpty()) {
            return true;
         }

         ResourceLocation id = manager.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(start.getStructure());
         return id == null || !((List)Config.EXCLUDED_STRUCTURES.get()).contains(id.toString());
      } else {
         return false;
      }
   }

   private static boolean isDesertPyramid(StructureStart start) {
      return start.isValid() && start.getPieces().size() == 1 && start.getPieces().get(0) instanceof DesertPyramidPiece;
   }

   private static void appendDesertPyramid(
      List<SurfaceBlendPlan.Segment> output,
      StructureStart start,
      NoiseBasedChunkGenerator generator,
      ChunkAccess chunk,
      RandomState randomState,
      int chunkMinX,
      int chunkMinZ
   ) {
      BoundingBox box = ((StructurePiece)start.getPieces().get(0)).getBoundingBox();
      if (box.intersects(chunkMinX - REFERENCE_RADIUS, chunkMinZ - REFERENCE_RADIUS, chunkMinX + 15 + REFERENCE_RADIUS, chunkMinZ + 15 + REFERENCE_RADIUS)) {
         SurfaceBlendPlan.PyramidBase base = PYRAMID_BASES.get(start);
         if (base == null) {
            base = pyramidBase(box, generator, chunk, randomState);
            PYRAMID_BASES.put(start, base);
         }

         if (base.wet()) {
            for (int z = box.minZ(); z <= box.maxZ(); z++) {
               output.add(new SurfaceBlendPlan.Segment(box.minX(), box.maxX(), z, z, base.foundationY(), false, true, true));
            }
         }
      }
   }

   static SurfaceBlendPlan.PyramidBase pyramidBase(BoundingBox box, NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState) {
      SurfaceHeightCache heights = SurfaceHeightCache.forContext(generator, randomState);
      int lowest = Integer.MAX_VALUE;
      boolean wet = false;

      for (int z = box.minZ(); z <= box.maxZ(); z += 4) {
         for (int x = box.minX(); x <= box.maxX(); x += 4) {
            long pair = heights.getPair(generator, randomState, chunk, x, z);
            int surface = SurfaceHeightCache.surface(pair);
            lowest = Math.min(lowest, surface);
            wet |= surface - SurfaceHeightCache.floor(pair) > 2;
         }
      }

      return wet ? new SurfaceBlendPlan.PyramidBase(true, Math.max(generator.getSeaLevel(), lowest) + 1) : DRY_PYRAMID;
   }

   private static boolean isCompactRigid(StructureStart start) {
      Boolean cached = COMPACT_RIGID.get(start);
      if (cached != null) {
         return cached;
      }

      boolean supported = start.isValid() && !start.getPieces().isEmpty();
      if (supported) {
         int minX = Integer.MAX_VALUE;
         int minZ = Integer.MAX_VALUE;
         int maxX = Integer.MIN_VALUE;
         int maxZ = Integer.MIN_VALUE;
         int substantial = 0;
         int small = 0;
         int largestArea = 0;

         for (StructurePiece piece : start.getPieces()) {
            if (!(piece instanceof PoolElementStructurePiece poolPiece)
               || poolPiece.getElement().getProjection() != Projection.RIGID
               || !(poolPiece.getElement() instanceof SinglePoolElement)) {
               supported = false;
               break;
            }

            BoundingBox box = piece.getBoundingBox();
            int width = box.maxX() - box.minX() + 1;
            int depth = box.maxZ() - box.minZ() + 1;
            int area = width * depth;
            if (area >= 16) {
               substantial++;
               largestArea = Math.max(largestArea, area);
               if (area <= 256) {
                  small++;
               }

               minX = Math.min(minX, box.minX());
               minZ = Math.min(minZ, box.minZ());
               maxX = Math.max(maxX, box.maxX());
               maxZ = Math.max(maxZ, box.maxZ());
            }
         }

         int spanX = maxX - minX + 1;
         int spanZ = maxZ - minZ + 1;
         if (supported
            && substantial >= 6
            && small >= 4
            && small * 2 >= substantial
            && (spanX >= 48 && spanZ >= 32 || spanZ >= 48 && spanX >= 32)
            && largestArea * 3L < (long)spanX * spanZ) {
            supported = false;
         }
      }

      COMPACT_RIGID.put(start, supported);
      return supported;
   }

   private static SurfaceBlendPlan.Anchor anchorFor(StructureStart start, NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState) {
      SurfaceBlendPlan.Anchor cached = ANCHORS.get(start);
      if (cached != null) {
         return cached;
      }

      SurfaceBlendPlan.Anchor anchor = NO_ANCHOR;

      for (StructurePiece piece : start.getPieces()) {
         if (piece instanceof PoolElementStructurePiece poolPiece && poolPiece.getElement() instanceof SinglePoolElement single) {
            FoundationMask mask = maskFor(poolPiece, single);
            if (!mask.isEmpty()
               && (mask.hasVerticalBody() || mask.hasSolidBase() && mask.isBroad())
               && !isLooseWoodAtWaterSurface(mask, poolPiece, generator, chunk, randomState)) {
               BoundingBox box = poolPiece.getBoundingBox();
               int x = (box.minX() + box.maxX()) / 2;
               int z = (box.minZ() + box.maxZ()) / 2;
               SurfaceHeightCache heightCache = SurfaceHeightCache.forContext(generator, randomState);
               long pair = heightCache.getPair(generator, randomState, chunk, x, z);
               int waterSurface = SurfaceHeightCache.surface(pair);
               int groundSurface = SurfaceHeightCache.floor(pair);
               int foundationY = poolPiece.getPosition().getY() + mask.layer();
               int baseY = foundationY - 1;
               boolean wet = waterSurface - groundSurface > 2;
               if (!wet && mask.hasSolidBase()) {
                  for (int sampleX : new int[]{box.minX(), box.maxX()}) {
                     for (int sampleZ : new int[]{box.minZ(), box.maxZ()}) {
                        long corner = heightCache.getPair(generator, randomState, chunk, sampleX, sampleZ);
                        wet |= SurfaceHeightCache.surface(corner) - SurfaceHeightCache.floor(corner) > 2;
                     }
                  }
               }

               if ((mask.hasVerticalBody() || wet && mask.hasSolidBase() && mask.isBroad()) && (!mask.hasOpenBase() || wet)) {
                  boolean surfaceEntrance = start.getStructure().step() == Decoration.UNDERGROUND_STRUCTURES;
                  boolean nearSurface = Math.abs(baseY - waterSurface) <= 6;
                  boolean nearSeabed = baseY >= groundSurface - 2 && baseY - groundSurface <= 12;
                  boolean floating = wet && nearSurface && baseY >= waterSurface - 2 && baseY - groundSurface > 2 && mask.isFloatingHull();
                  if (!floating
                     && (
                        wet
                           ? nearSurface && baseY >= waterSurface - 2 && baseY - groundSurface <= 48 || nearSeabed
                           : Math.abs(baseY - groundSurface) <= (surfaceEntrance ? 6 : 24)
                     )) {
                     anchor = new SurfaceBlendPlan.Anchor(true, foundationY, wet, groundLift(poolPiece, mask));
                  }
                  break;
               }
            }
         }
      }

      ANCHORS.put(start, anchor);
      return anchor;
   }

   private static boolean isLooseWoodAtWaterSurface(
      FoundationMask mask, PoolElementStructurePiece piece, NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState
   ) {
      if (!mask.isLooseWood()) {
         return false;
      }

      BoundingBox box = piece.getBoundingBox();
      int x = (box.minX() + box.maxX()) / 2;
      int z = (box.minZ() + box.maxZ()) / 2;
      SurfaceHeightCache heights = SurfaceHeightCache.forContext(generator, randomState);
      long pair = heights.getPair(generator, randomState, chunk, x, z);
      int waterSurface = SurfaceHeightCache.surface(pair);
      int floor = SurfaceHeightCache.floor(pair);
      int baseY = piece.getPosition().getY() + mask.layer() - 1;
      return waterSurface - floor > 2 && baseY >= waterSurface - 2 && Math.abs(baseY - waterSurface) <= 6 && baseY - floor <= 48;
   }

   private static void appendSegments(
      List<SurfaceBlendPlan.Segment> output,
      PoolElementStructurePiece piece,
      FoundationMask mask,
      int anchorY,
      boolean allowDeepSupport,
      boolean allowUnderpin,
      boolean wetAnchor,
      int chunkMinX,
      int chunkMinZ
   ) {
      BlockPos origin = piece.getPosition();
      int foundationY = origin.getY() + mask.layer();
      BoundingBox footprint = piece.getBoundingBox();
      boolean broadFoundation = mask.isBroad()
         || allowUnderpin
            && wetAnchor
            && mask.hasSolidBase()
            && mask.hasVerticalBody()
            && !mask.hasOpenBase()
            && !mask.isFloatingHull()
            && footprint.maxX() - footprint.minX() >= 7
            && footprint.maxZ() - footprint.minZ() >= 7;
      boolean deepSupport = foundationY < anchorY - 4;
      if (deepSupport ? allowDeepSupport && foundationY >= anchorY - 48 : foundationY <= anchorY + 4) {
         int groundFoundationY = deepSupport ? foundationY : foundationY + groundLift(piece, mask);
         int radius = allowUnderpin && !deepSupport && broadFoundation ? 24 : 16;
         appendRuns(output, piece, mask, mask.runs(), groundFoundationY, deepSupport, allowUnderpin, broadFoundation, radius, chunkMinX, chunkMinZ);
         if (!wetAnchor && !deepSupport) {
            appendRuns(output, piece, mask, mask.yardRuns(), groundFoundationY, false, allowUnderpin, broadFoundation, radius, chunkMinX, chunkMinZ);
         }
      }
   }

   private static void appendClearance(
      List<SurfaceBlendPlan.ClearanceBox> output, PoolElementStructurePiece piece, FoundationMask mask, int floorY, int chunkMinX, int chunkMinZ
   ) {
      BlockPos origin = piece.getPosition();

      for (FoundationMask.BodyRun run : mask.bodyRuns()) {
         BlockPos first = StructureTemplate.transform(new BlockPos(run.minX(), mask.layer(), run.z()), Mirror.NONE, piece.getRotation(), BlockPos.ZERO)
            .offset(origin);
         BlockPos last = StructureTemplate.transform(new BlockPos(run.maxX(), mask.layer(), run.z()), Mirror.NONE, piece.getRotation(), BlockPos.ZERO)
            .offset(origin);
         int minX = Math.min(first.getX(), last.getX());
         int maxX = Math.max(first.getX(), last.getX());
         int minZ = Math.min(first.getZ(), last.getZ());
         int maxZ = Math.max(first.getZ(), last.getZ());
         if (maxX >= chunkMinX - 4 && minX <= chunkMinX + 15 + 4 && maxZ >= chunkMinZ - 4 && minZ <= chunkMinZ + 15 + 4) {
            output.add(new SurfaceBlendPlan.ClearanceBox(minX, maxX, minZ, maxZ, Math.max(floorY, origin.getY() + run.lowest() - 1)));
         }
      }
   }

   private static void appendRuns(
      List<SurfaceBlendPlan.Segment> output,
      PoolElementStructurePiece piece,
      FoundationMask mask,
      List<FoundationMask.Run> runs,
      int groundFoundationY,
      boolean deepSupport,
      boolean allowUnderpin,
      boolean broadFoundation,
      int radius,
      int chunkMinX,
      int chunkMinZ
   ) {
      BlockPos origin = piece.getPosition();

      for (FoundationMask.Run run : runs) {
         BlockPos first = StructureTemplate.transform(new BlockPos(run.minX(), mask.layer(), run.z()), Mirror.NONE, piece.getRotation(), BlockPos.ZERO)
            .offset(origin);
         BlockPos last = StructureTemplate.transform(new BlockPos(run.maxX(), mask.layer(), run.z()), Mirror.NONE, piece.getRotation(), BlockPos.ZERO)
            .offset(origin);
         int minX = Math.min(first.getX(), last.getX());
         int maxX = Math.max(first.getX(), last.getX());
         int minZ = Math.min(first.getZ(), last.getZ());
         int maxZ = Math.max(first.getZ(), last.getZ());
         if (maxX >= chunkMinX - radius && minX <= chunkMinX + 15 + radius && maxZ >= chunkMinZ - radius && minZ <= chunkMinZ + 15 + radius) {
            output.add(new SurfaceBlendPlan.Segment(minX, maxX, minZ, maxZ, groundFoundationY, deepSupport, allowUnderpin && !deepSupport, broadFoundation));
         }
      }
   }

   private static FoundationMask maskFor(PoolElementStructurePiece piece, SinglePoolElement single) {
      StructureTemplateManager templateManager = ((PoolPieceAccessor)piece).structblend$getTemplateManager();
      FoundationMask cached;
      synchronized (ELEMENT_MASKS) {
         Map<SinglePoolElement, FoundationMask> masks = ELEMENT_MASKS.get(templateManager);
         cached = masks == null ? null : masks.get(single);
      }

      if (cached != null) {
         return cached;
      }

      StructureTemplate template = ((SinglePoolElementInvoker)single).structblend$getTemplate(templateManager);
      FoundationMask mask = FoundationMask.get(template);
      synchronized (ELEMENT_MASKS) {
         ELEMENT_MASKS.computeIfAbsent(templateManager, ignored -> new WeakHashMap<>()).put(single, mask);
         return mask;
      }
   }

   static int groundLift(PoolElementStructurePiece piece, FoundationMask mask) {
      return Math.max(0, Math.min(1, piece.getGroundLevelDelta() - mask.layer()));
   }

   private record Anchor(boolean valid, int foundationY, boolean wet, int groundLift) {
      int groundY() {
         return this.foundationY - 1 + this.groundLift;
      }
   }

   private record ClearanceBox(int minX, int maxX, int minZ, int maxZ, int floorY) {
   }

   private static final class HeightSamples {
      private static final int MISSING = Integer.MIN_VALUE;
      private static final double PLACEHOLDER = 0.0;
      private final NoiseBasedChunkGenerator generator;
      private final ChunkAccess chunk;
      private final RandomState randomState;
      private final SurfaceHeightCache surfaceHeights;
      private final int minX;
      private final int minZ;
      private final int[] values = new int[25];
      private final int[] exactSurfaces = new int[256];
      private final byte[] wetColumns = new byte[256];
      private final int[] groundValues = new int[25];
      private final ChunkHeightBatch batch;
      private final boolean predicting;
      private boolean dirty;
      private boolean trusted;
      private final boolean[] touchedCoarse = new boolean[4];

      private HeightSamples(
         NoiseBasedChunkGenerator generator, ChunkAccess chunk, RandomState randomState, int minX, int minZ, ChunkHeightBatch batch, boolean predicting
      ) {
         this.generator = generator;
         this.chunk = chunk;
         this.randomState = randomState;
         this.surfaceHeights = SurfaceHeightCache.forContext(generator, randomState);
         this.minX = minX;
         this.minZ = minZ;
         this.batch = batch;
         this.predicting = predicting && batch != null;
         Arrays.fill(this.values, Integer.MIN_VALUE);
         Arrays.fill(this.groundValues, Integer.MIN_VALUE);
         Arrays.fill(this.exactSurfaces, Integer.MIN_VALUE);
      }

      private int surface(int worldX, int worldZ) {
         if (this.batch != null && this.batch.has(worldX, worldZ)) {
            return SurfaceHeightCache.surface(this.batch.pair(worldX, worldZ));
         }

         if (this.predicting) {
            return this.missing(worldX, worldZ);
         }

         this.fallback();
         return this.surfaceHeights.get(this.generator, this.randomState, this.chunk, worldX, worldZ);
      }

      private int floor(int worldX, int worldZ) {
         if (this.batch != null && this.batch.has(worldX, worldZ)) {
            return SurfaceHeightCache.floor(this.batch.pair(worldX, worldZ));
         }

         if (this.predicting) {
            return this.missing(worldX, worldZ);
         }

         this.fallback();
         return this.surfaceHeights.getOceanFloor(this.generator, this.randomState, this.chunk, worldX, worldZ);
      }

      private long pair(int worldX, int worldZ) {
         if (this.batch != null && this.batch.has(worldX, worldZ)) {
            return this.batch.pair(worldX, worldZ);
         } else if (this.predicting) {
            this.missing(worldX, worldZ);
            return Long.MIN_VALUE;
         } else {
            this.fallback();
            return this.surfaceHeights.getPair(this.generator, this.randomState, this.chunk, worldX, worldZ);
         }
      }

      private int missing(int worldX, int worldZ) {
         if (this.trusted) {
            this.batch.request(worldX, worldZ);
         }

         this.dirty = true;
         return Integer.MIN_VALUE;
      }

      private void fallback() {
         if (this.batch != null) {
            HeightQueryDiagnostics.batchFallback();
         }
      }

      private int exactSurfaceAt(int x, int z) {
         this.trusted = !this.dirty;
         int index = z << 4 | x;
         if (this.exactSurfaces[index] == Integer.MIN_VALUE) {
            int value = this.surface(this.minX + x, this.minZ + z);
            if (value == Integer.MIN_VALUE) {
               return Integer.MIN_VALUE;
            }

            this.exactSurfaces[index] = value;
         }

         return this.exactSurfaces[index];
      }

      private boolean wetAt(int x, int z, double interpolatedSurface) {
         this.trusted = !this.dirty;
         int index = z << 4 | x;
         if (this.wetColumns[index] == 0) {
            int dry = interpolatedSurface >= this.generator.getSeaLevel() + 8 ? this.clearlyDryCell(x, z) : 0;
            if (dry == Integer.MIN_VALUE) {
               return false;
            }

            if (dry == 1) {
               this.wetColumns[index] = 1;
            } else {
               int floor;
               if (this.exactSurfaces[index] == Integer.MIN_VALUE) {
                  long pair = this.pair(this.minX + x, this.minZ + z);
                  if (pair == Long.MIN_VALUE) {
                     return false;
                  }

                  this.exactSurfaces[index] = SurfaceHeightCache.surface(pair);
                  floor = SurfaceHeightCache.floor(pair);
               } else {
                  floor = this.floor(this.minX + x, this.minZ + z);
                  if (floor == Integer.MIN_VALUE) {
                     return false;
                  }
               }

               this.wetColumns[index] = (byte)(this.exactSurfaces[index] - floor > 2 ? 2 : 1);
            }
         }

         return this.wetColumns[index] == 2;
      }

      private int clearlyDryCell(int x, int z) {
         int gridX = x >> 2;
         int gridZ = z >> 2;
         int a = this.sample(gridX, gridZ);
         int b = this.sample(gridX + 1, gridZ);
         int c = this.sample(gridX, gridZ + 1);
         int d = this.sample(gridX + 1, gridZ + 1);
         if (a != Integer.MIN_VALUE && b != Integer.MIN_VALUE && c != Integer.MIN_VALUE && d != Integer.MIN_VALUE) {
            int lowest = Math.min(Math.min(a, b), Math.min(c, d));
            int highest = Math.max(Math.max(a, b), Math.max(c, d));
            return lowest >= this.generator.getSeaLevel() + 8 && highest - lowest <= 2 ? 1 : 0;
         } else {
            return Integer.MIN_VALUE;
         }
      }

      private int sample(int gridX, int gridZ) {
         int index = gridZ * 5 + gridX;
         if (this.values[index] == Integer.MIN_VALUE) {
            int value = this.surface(this.minX + gridX * 4, this.minZ + gridZ * 4);
            if (value == Integer.MIN_VALUE) {
               return Integer.MIN_VALUE;
            }

            this.values[index] = value;
         }

         return this.values[index];
      }

      private int groundSample(int gridX, int gridZ) {
         int index = gridZ * 5 + gridX;
         if (this.groundValues[index] == Integer.MIN_VALUE) {
            int surface = this.sample(gridX, gridZ);
            if (surface == Integer.MIN_VALUE) {
               return Integer.MIN_VALUE;
            }

            int value = surface > this.generator.getSeaLevel() ? surface : this.floor(this.minX + gridX * 4, this.minZ + gridZ * 4);
            if (value == Integer.MIN_VALUE) {
               return Integer.MIN_VALUE;
            }

            this.groundValues[index] = value;
         }

         return this.groundValues[index];
      }

      private double at(int x, int z) {
         this.dirty = false;
         this.trusted = true;
         return this.interpolatedAt(x, z, false);
      }

      private double groundAt(int x, int z) {
         this.trusted = !this.dirty;
         return this.interpolatedAt(x, z, true);
      }

      private void requestFineGrid() {
         for (int cell = 0; cell < 4; cell++) {
            if (this.touchedCoarse[cell]) {
               int gridX = (cell & 1) * 2;
               int gridZ = (cell >> 1) * 2;

               for (int dz = 0; dz <= 2; dz++) {
                  for (int dx = 0; dx <= 2; dx++) {
                     this.batch.request(this.minX + (gridX + dx) * 4, this.minZ + (gridZ + dz) * 4);
                  }
               }
            }
         }
      }

      private double interpolatedAt(int x, int z, boolean ground) {
         int coarseX = (x >> 3) * 2;
         int coarseZ = (z >> 3) * 2;
         this.touchedCoarse[(z >> 3) * 2 + (x >> 3)] = true;
         int a = ground ? this.groundSample(coarseX, coarseZ) : this.sample(coarseX, coarseZ);
         int b = ground ? this.groundSample(coarseX + 2, coarseZ) : this.sample(coarseX + 2, coarseZ);
         int c = ground ? this.groundSample(coarseX, coarseZ + 2) : this.sample(coarseX, coarseZ + 2);
         int d = ground ? this.groundSample(coarseX + 2, coarseZ + 2) : this.sample(coarseX + 2, coarseZ + 2);
         if (a != Integer.MIN_VALUE && b != Integer.MIN_VALUE && c != Integer.MIN_VALUE && d != Integer.MIN_VALUE) {
            if (Math.max(Math.max(a, b), Math.max(c, d)) - Math.min(Math.min(a, b), Math.min(c, d)) > 6) {
               int fineX = x >> 2;
               int fineZ = z >> 2;
               int fa = ground ? this.groundSample(fineX, fineZ) : this.sample(fineX, fineZ);
               int fb = ground ? this.groundSample(fineX + 1, fineZ) : this.sample(fineX + 1, fineZ);
               int fc = ground ? this.groundSample(fineX, fineZ + 1) : this.sample(fineX, fineZ + 1);
               int fd = ground ? this.groundSample(fineX + 1, fineZ + 1) : this.sample(fineX + 1, fineZ + 1);
               return fa != Integer.MIN_VALUE && fb != Integer.MIN_VALUE && fc != Integer.MIN_VALUE && fd != Integer.MIN_VALUE
                  ? interpolate(fa, fb, fc, fd, (x & 3) / 4.0, (z & 3) / 4.0)
                  : 0.0;
            } else {
               return interpolate(a, b, c, d, (x & 7) / 8.0, (z & 7) / 8.0);
            }
         } else {
            return 0.0;
         }
      }

      private static double interpolate(int a, int b, int c, int d, double x, double z) {
         return (a + (b - a) * x) * (1.0 - z) + (c + (d - c) * x) * z;
      }
   }

   record PyramidBase(boolean wet, int foundationY) {
   }

   private record Segment(int minX, int maxX, int minZ, int maxZ, int foundationY, boolean deepSupport, boolean allowUnderpin, boolean broadFoundation) {
   }
}
