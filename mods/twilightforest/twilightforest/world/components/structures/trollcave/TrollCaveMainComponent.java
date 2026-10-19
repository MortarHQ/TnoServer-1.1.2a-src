package twilightforest.world.components.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFConfiguredFeatures;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.init.custom.StructureSpeleothemConfigs;
import twilightforest.loot.TFLootTables;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.feature.BlockSpikeFeature;
import twilightforest.world.components.structures.StructureSpeleothemConfig;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class TrollCaveMainComponent extends TFStructureComponentOld {
   protected int size;
   protected int height;
   protected final StructureSpeleothemConfig speleothemConfig;
   protected final Reference<StructureSpeleothemConfig> speleothemConfigHolder;

   public TrollCaveMainComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFTCMai.get(), ctx, nbt);
   }

   public TrollCaveMainComponent(StructurePieceType piece, StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super(piece, nbt);
      this.size = nbt.getInt("size");
      this.height = nbt.getInt("height");
      this.speleothemConfigHolder = StructureSpeleothemConfigs.getConfigHolder(ctx.registryAccess(), nbt.getString("config_id"));
      this.speleothemConfig = (StructureSpeleothemConfig)this.speleothemConfigHolder.value();
   }

   public TrollCaveMainComponent(StructurePieceType type, int i, int x, int y, int z, Reference<StructureSpeleothemConfig> speleothemConfig) {
      super(type, i, x, y, z);
      this.setOrientation(Direction.SOUTH);
      this.size = 30;
      this.height = 20;
      int radius = this.size / 2;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(
         x, y, z, -radius, -this.height, -radius, this.size, this.height, this.size, Direction.SOUTH, false
      );
      this.speleothemConfigHolder = speleothemConfig;
      this.speleothemConfig = (StructureSpeleothemConfig)speleothemConfig.value();
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("size", this.size);
      tagCompound.putInt("height", this.height);
      tagCompound.putString("config_id", this.speleothemConfigHolder.key().location().toString());
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      for (Rotation caveRotation : RotationUtil.ROTATIONS) {
         BlockPos dest = this.getValidOpening(rand, caveRotation);
         this.makeSmallerCave(list, rand, this.getGenDepth() + 1, dest.getX(), dest.getY(), dest.getZ(), 18, 15, caveRotation);
      }

      TrollVaultComponent vault = new TrollVaultComponent(
         this.getGenDepth() + 1,
         this.boundingBox.minX() + (this.boundingBox.maxX() - this.boundingBox.minX()) / 2,
         this.boundingBox.minY(),
         this.boundingBox.minZ() + (this.boundingBox.maxZ() - this.boundingBox.minZ()) / 2
      );
      list.addPiece(vault);
      vault.addChildren(this, list, rand);
   }

   protected boolean makeSmallerCave(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int caveSize, int caveHeight, Rotation rotation
   ) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      BlockPos dest = this.offsetTowerCCoords(x, y, z, caveSize, direction);
      TrollCaveConnectComponent cave = new TrollCaveConnectComponent(
         index, dest.getX(), dest.getY(), dest.getZ(), caveSize, caveHeight, direction, this.speleothemConfigHolder
      );
      StructurePiece intersect = list.findCollisionPiece(cave.getBoundingBox());
      if (intersect != null && intersect != this) {
         return false;
      }

      list.addPiece(cave);
      cave.addChildren(this, list, rand);
      return true;
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      this.hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
      this.placeSpeleothems(world, rand, sbb, decoRNG);

      for (int i = 0; i < 32; i++) {
         BlockPos dest = this.getCoordsInCave(decoRNG);
         this.generateAtSurface(world, generator, TFConfiguredFeatures.UBEROUS_SOIL_PATCH_BIG, decoRNG, dest.getX(), dest.getZ(), sbb);
      }
   }

   protected void placeSpeleothems(WorldGenLevel world, RandomSource rand, BoundingBox sbb, RandomSource decoRNG) {
      decoRNG.setSeed(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      int ceilingY = this.getWorldY(this.height);
      int floorY = this.getWorldY(0);

      for (BlockPos pos : this.speleothemConfig.latticeIterator(BoundingBoxUtils.getIntersectionOfSBBs(sbb, this.boundingBox), ceilingY)) {
         if (!world.getBlockState(pos.above()).isAir() && this.speleothemConfig.shouldDoAStalactite(rand)) {
            BlockSpikeFeature.startSpike(world, pos, this.speleothemConfig.getStalactite(decoRNG), decoRNG, true);
         }

         if (this.speleothemConfig.shouldDoAStalagmite(rand)) {
            BlockSpikeFeature.startSpike(world, pos.atY(floorY), this.speleothemConfig.getStalagmite(decoRNG), decoRNG, false);
         }
      }
   }

   protected MutableBlockPos getCoordsInCave(RandomSource rand) {
      return new MutableBlockPos(rand.nextInt(this.size - 1), rand.nextInt(this.height - 1), rand.nextInt(this.size - 1));
   }

   protected void hollowCaveMiddle(WorldGenLevel world, BoundingBox boundingBox, RandomSource rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      int threshold = this.size / 5;

      for (int y = minY; y <= maxY; y++) {
         for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
               int ex = Math.min(x - minX, maxX - x);
               int ey = Math.min((y - minY) * 2, maxY - y);
               int ez = Math.min(z - minZ, maxZ - z);
               double dist = Math.sqrt(ex * ey * ez);
               if (!this.getBlock(world, x, y, z, boundingBox).is(BlockTagGenerator.CANNOT_TROLL_CAVE_HOLLOW)) {
                  if (dist > threshold) {
                     this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, boundingBox);
                  } else if (dist == threshold && rand.nextInt(4) == 0 && this.getBlock(world, x, y, z, boundingBox).is(BlockTags.BASE_STONE_OVERWORLD)) {
                     this.placeBlock(world, ((Block)TFBlocks.TROLLSTEINN.get()).defaultBlockState(), x, y, z, boundingBox);
                  }
               }
            }
         }
      }
   }

   @Nullable
   public BlockPos getValidOpening(RandomSource rand, Rotation direction) {
      int offset = this.size / 4;
      int wLength = this.size - offset * 2;
      if (direction != Rotation.NONE && direction != Rotation.CLOCKWISE_180) {
         if (direction != Rotation.CLOCKWISE_90 && direction != Rotation.COUNTERCLOCKWISE_90) {
            return null;
         }

         int rx = offset + rand.nextInt(wLength);
         int rz = direction == Rotation.CLOCKWISE_90 ? this.size - 1 : 0;
         int ry = rand.nextInt(offset) - rand.nextInt(offset);
         return new BlockPos(rx, ry, rz);
      } else {
         int rx = direction == Rotation.NONE ? this.size - 1 : 0;
         int rz = offset + rand.nextInt(wLength);
         int ry = 0;
         return new BlockPos(rx, ry, rz);
      }
   }

   @Override
   protected BlockPos offsetTowerCCoords(int x, int y, int z, int towerSize, Direction direction) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      if (direction == Direction.SOUTH) {
         return new BlockPos(dx - 1, dy - 1, dz - towerSize / 2);
      } else if (direction == Direction.WEST) {
         return new BlockPos(dx + towerSize / 2, dy - 1, dz - 1);
      } else if (direction == Direction.NORTH) {
         return new BlockPos(dx + 1, dy - 1, dz + towerSize / 2);
      } else {
         return direction == Direction.EAST ? new BlockPos(dx - towerSize / 2, dy - 1, dz + 1) : new BlockPos(x, y, z);
      }
   }

   protected void generateAtSurface(
      WorldGenLevel world, ChunkGenerator generator, ResourceKey<ConfiguredFeature<?, ?>> feature, RandomSource rand, int x, int z, BoundingBox sbb
   ) {
      int dx = this.getWorldX(x, z);
      int dz = this.getWorldZ(x, z);
      MutableBlockPos pos = new MutableBlockPos(dx, generator.getSeaLevel() + 15, dz);

      for (int i = 0; i < 15; i++) {
         pos.move(0, 1, 0);
         if (sbb.isInside(pos) && world.getBlockState(pos.above()).isAir()) {
            ((ConfiguredFeature)world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(feature)).place(world, generator, rand, pos);
            break;
         }
      }
   }

   protected void makeTreasureCrate(WorldGenLevel world, BoundingBox sbb) {
      int mid = this.size / 2;
      this.generateBox(world, sbb, mid - 2, 0, mid - 2, mid + 1, 3, mid + 1, Blocks.OBSIDIAN.defaultBlockState(), Blocks.OBSIDIAN.defaultBlockState(), false);
      this.generateAirBox(world, sbb, mid - 1, 1, mid - 1, mid, 2, mid);
      this.placeTreasureAtCurrentPosition(world, mid, 1, mid, TFLootTables.TROLL_GARDEN, false, sbb);
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.BURY;
   }
}
