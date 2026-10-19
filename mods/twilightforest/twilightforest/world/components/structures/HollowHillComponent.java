package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CommonLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.init.custom.StructureSpeleothemConfigs;
import twilightforest.loot.TFLootTables;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.feature.BlockSpikeFeature;

public class HollowHillComponent extends TFStructureComponentOld {
   private static final float CHEST_SPAWN_CHANCE = 0.025F;
   private static final float SPAWNER_SPAWN_CHANCE = 0.025F;
   private static final float SPECIAL_SPAWN_CHANCE = 0.05F;
   private final int hillSize;
   final int radius;
   final int hdiam;
   protected final StructureSpeleothemConfig speleothemConfig;
   protected final ResourceLocation speleothemConfigId;

   public HollowHillComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this(ctx, (StructurePieceType)TFStructurePieceTypes.TFHill.get(), nbt);
   }

   public HollowHillComponent(StructurePieceSerializationContext ctx, StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.hillSize = nbt.getInt("hillSize");
      this.radius = (this.hillSize * 2 + 1) * 8 - 6;
      this.hdiam = (this.hillSize * 2 + 1) * 16;
      Reference<StructureSpeleothemConfig> configHolder = StructureSpeleothemConfigs.getConfigHolder(ctx.registryAccess(), nbt.getString("config_id"));
      this.speleothemConfig = (StructureSpeleothemConfig)configHolder.value();
      this.speleothemConfigId = configHolder.key().location();
   }

   public HollowHillComponent(StructurePieceType piece, int i, int size, int x, int y, int z, Reference<StructureSpeleothemConfig> speleothemConfig) {
      super(piece, i, x, y, z);
      this.setOrientation(Direction.SOUTH);
      this.hillSize = size;
      this.radius = (this.hillSize * 2 + 1) * 8 - 6;
      this.hdiam = (this.hillSize * 2 + 1) * 16;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(
         x,
         y,
         z,
         -this.radius,
         -(3 + this.hillSize),
         -this.radius,
         this.radius * 2,
         this.radius / (this.hillSize == 1 ? 2 : this.hillSize),
         this.radius * 2,
         Direction.SOUTH,
         true
      );
      this.speleothemConfigId = ((ResourceKey)speleothemConfig.unwrapKey().get()).location();
      this.speleothemConfig = (StructureSpeleothemConfig)speleothemConfig.value();
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("hillSize", this.hillSize);
      tagCompound.putString("config_id", this.speleothemConfigId.toString());
   }

   public void postProcess(
      WorldGenLevel world,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource rand,
      BoundingBox writeableBounds,
      ChunkPos chunkPosIn,
      BlockPos blockPos
   ) {
      BlockPos center = this.boundingBox.getCenter();
      float shortenedRadiusSq = this.radius * this.radius * 0.85F;
      float drainRadius = this.hillSize * 16.5F;
      drainWater(
         generator,
         writeableBounds,
         this.boundingBox,
         world,
         this.hillSize * 3 + 2,
         Blocks.CAVE_AIR.defaultBlockState(),
         center.getX(),
         center.getZ(),
         drainRadius * drainRadius,
         Blocks.STONE.defaultBlockState()
      );

      for (MutableBlockPos latticePos : this.speleothemConfig.latticeIterator(writeableBounds, 0)) {
         float distSq = getDistSqFromCenter(center, latticePos);
         if (!(distSq > shortenedRadiusSq)) {
            this.setFeatures(world, rand, writeableBounds, latticePos, distSq);
         }
      }
   }

   public static void drainWater(
      ChunkGenerator generator,
      BoundingBox chunkBox,
      BoundingBox structureBox,
      CommonLevelAccessor level,
      int maxDepth,
      BlockState airState,
      int xCenter,
      int zCenter,
      double radiusSq,
      BlockState undergroundBlock
   ) {
      BoundingBox bounds = BoundingBoxUtils.getIntersectionOfSBBs(chunkBox, structureBox);
      if (bounds != null) {
         int seaLevel = generator.getSeaLevel();
         int minY = seaLevel - maxDepth;

         for (int z = bounds.minZ(); z <= bounds.maxZ(); z++) {
            int dZ = zCenter - z;

            for (int x = bounds.minX(); x <= bounds.maxX(); x++) {
               int dX = xCenter - x;
               float distSq = dX * dX + dZ * dZ;
               if (!(distSq >= radiusSq)) {
                  int maxY = Math.min(seaLevel, level.getHeight(Types.OCEAN_FLOOR_WG, x, z) - 1);
                  boolean crossedFloor = false;

                  for (int y = maxY; y >= minY; y--) {
                     BlockPos posChecked = new BlockPos(x, y, z);
                     BlockState stateAt = level.getBlockState(posChecked);
                     if (stateAt.getFluidState().is(FluidTags.WATER)) {
                        level.setBlock(posChecked, airState, 3);
                     } else {
                        crossedFloor = true;
                     }

                     if (crossedFloor && (stateAt.is(Blocks.DIRT) || stateAt.is(Blocks.SAND))) {
                        level.setBlock(posChecked, undergroundBlock, 3);
                     }
                  }
               }
            }
         }
      }
   }

   private void setFeatures(WorldGenLevel world, RandomSource rand, BoundingBox writeableBounds, MutableBlockPos pos, float distSq) {
      rand.setSeed(rand.nextLong() ^ pos.asLong());
      this.placeCeilingFeature(world, rand, pos, distSq);
      this.placeFloorFeature(world, rand, writeableBounds, pos, distSq);
   }

   private void placeFloorFeature(WorldGenLevel world, RandomSource rand, BoundingBox writeableBounds, MutableBlockPos pos, float distSq) {
      int floorY = this.getFloorY(distSq);
      float floatChance = rand.nextFloat();
      if (floatChance < 0.05F) {
         float angle = rand.nextFloat() * (float) (Math.PI * 2);
         int x = Math.round(Mth.cos(angle) * Mth.SQRT_OF_TWO) + pos.getX();
         int z = Math.round(Mth.sin(angle) * Mth.SQRT_OF_TWO) + pos.getZ();
         pos.set(x, floorY, z);
         if (floatChance < 0.025F) {
            setSpawnerInWorld(world, writeableBounds, this.getMobID(rand), v -> {}, pos.above());
         } else {
            this.placeTreasureAtWorldPosition(world, this.getTreasureType(), false, writeableBounds, pos.above());
         }

         world.setBlock(pos.below(), Blocks.COBBLESTONE.defaultBlockState(), 50);
         world.setBlock(pos, Blocks.COBBLESTONE.defaultBlockState(), 50);
      } else if (this.speleothemConfig.shouldDoAStalagmite(rand)) {
         pos.setY(floorY);
         int ceilingY = this.getCeilingY(distSq);
         int forcedMaxHeight = ceilingY - floorY + 4;
         BlockSpikeFeature.startSpike(world, pos, this.speleothemConfig.getStalagmite(rand), rand, false, forcedMaxHeight);
      }
   }

   private void placeCeilingFeature(WorldGenLevel world, RandomSource rand, MutableBlockPos pos, float distSq) {
      if (this.speleothemConfig.shouldDoAStalactite(rand)) {
         BlockPos ceiling = pos.atY(this.getCeilingY(distSq));
         BlockSpikeFeature.startSpike(world, ceiling, this.speleothemConfig.getStalactite(rand), rand, true);
      }
   }

   private int getCeilingY(float distSq) {
      return this.getWorldY(Mth.ceil(this.getCeilingHeight(Mth.sqrt(distSq))));
   }

   private int getFloorY(float distSq) {
      return this.getWorldY(Mth.floor(this.getFloorHeight(Mth.sqrt(distSq)) + 0.25F));
   }

   @NotNull
   private ResourceKey<LootTable> getTreasureType() {
      return this.hillSize == 3 ? TFLootTables.LARGE_HOLLOW_HILL : (this.hillSize == 2 ? TFLootTables.MEDIUM_HOLLOW_HILL : TFLootTables.SMALL_HOLLOW_HILL);
   }

   protected void generateSpeleothem(WorldGenLevel world, BlockPos pos, BoundingBox sbb, boolean hanging) {
      if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != Blocks.SPAWNER) {
         RandomSource stalRNG = RandomSource.create(world.getSeed() + (long)pos.getX() * pos.getZ());
         BlockSpikeFeature.startSpike(world, pos, this.speleothemConfig.getSpeleothem(hanging, stalRNG), stalRNG, hanging);
      }
   }

   private float getFloorHeight(float dist) {
      return this.hillSize * 2 - Mth.cos(dist / this.hdiam * (float) Math.PI) * (this.hdiam / 20.0F) + 1.0F;
   }

   private float getCeilingHeight(float dist) {
      return Mth.cos(dist / this.hdiam * (float) Math.PI) * (this.hdiam / 4.0F);
   }

   protected EntityType<?> getMobID(RandomSource rand) {
      return this.getMobID(rand, this.hillSize);
   }

   protected EntityType<?> getMobID(RandomSource rand, int level) {
      if (level == 1) {
         return this.getLevel1Mob(rand);
      } else if (level == 2) {
         return this.getLevel2Mob(rand);
      } else {
         return level == 3 ? this.getLevel3Mob(rand) : EntityType.SPIDER;
      }
   }

   public EntityType<?> getLevel1Mob(RandomSource rand) {
      return switch (rand.nextInt(10)) {
         case 3, 4, 5 -> EntityType.SPIDER;
         case 6, 7 -> EntityType.ZOMBIE;
         case 8 -> EntityType.SILVERFISH;
         case 9 -> (EntityType)TFEntities.REDCAP.get();
         default -> (EntityType)TFEntities.SWARM_SPIDER.get();
      };
   }

   public EntityType<?> getLevel2Mob(RandomSource rand) {
      return switch (rand.nextInt(10)) {
         case 3, 4, 5 -> EntityType.ZOMBIE;
         case 6, 7 -> EntityType.SKELETON;
         case 8 -> (EntityType)TFEntities.SWARM_SPIDER.get();
         case 9 -> EntityType.CAVE_SPIDER;
         default -> (EntityType)TFEntities.REDCAP.get();
      };
   }

   public EntityType<?> getLevel3Mob(RandomSource rand) {
      return switch (rand.nextInt(11)) {
         case 0 -> (EntityType)TFEntities.SLIME_BEETLE.get();
         case 1 -> (EntityType)TFEntities.FIRE_BEETLE.get();
         case 2 -> (EntityType)TFEntities.PINCH_BEETLE.get();
         case 3, 4, 5 -> EntityType.SKELETON;
         case 6, 7, 8 -> EntityType.CAVE_SPIDER;
         case 9 -> EntityType.CREEPER;
         default -> (EntityType)TFEntities.WRAITH.get();
      };
   }

   public static float getDistSqFromCenter(BlockPos center, BlockPos to) {
      float x = to.getX() - center.getX() - 0.5F;
      float z = to.getZ() - center.getZ() - 0.5F;
      return x * x + z * z;
   }
}
