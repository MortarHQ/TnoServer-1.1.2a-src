package twilightforest.world.components.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.HugeMushroomUtil;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.StructureSpeleothemConfig;

public class TrollCaveConnectComponent extends TrollCaveMainComponent {
   protected final boolean[] openingTowards = new boolean[]{false, false, true, false};

   public TrollCaveConnectComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFTCCon.get(), ctx, nbt);
      this.openingTowards[0] = nbt.getBoolean("openingTowards0");
      this.openingTowards[1] = nbt.getBoolean("openingTowards1");
      this.openingTowards[2] = nbt.getBoolean("openingTowards2");
      this.openingTowards[3] = nbt.getBoolean("openingTowards3");
   }

   public TrollCaveConnectComponent(
      int index, int x, int y, int z, int caveSize, int caveHeight, Direction direction, Reference<StructureSpeleothemConfig> speleothemConfig
   ) {
      super((StructurePieceType)TFStructurePieceTypes.TFTCCon.get(), index, x, y, z, speleothemConfig);
      this.size = caveSize;
      this.height = caveHeight;
      this.setOrientation(direction);
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, direction, false);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putBoolean("openingTowards0", this.openingTowards[0]);
      tagCompound.putBoolean("openingTowards1", this.openingTowards[1]);
      tagCompound.putBoolean("openingTowards2", this.openingTowards[2]);
      tagCompound.putBoolean("openingTowards3", this.openingTowards[3]);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (this.getGenDepth() < 3) {
         for (Rotation rotation : RotationUtil.ROTATIONS) {
            BlockPos dest = this.getValidOpening(rand, rotation);
            if (rand.nextBoolean() || !this.makeGardenCave(list, rand, this.getGenDepth() + 1, dest.getX(), dest.getY(), dest.getZ(), 30, 15, rotation)) {
               this.makeSmallerCave(list, rand, this.getGenDepth() + 1, dest.getX(), dest.getY(), dest.getZ(), 20, 15, rotation);
            }
         }
      }
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         if (!this.openingTowards[rotation.ordinal()]) {
            this.decorateWall(world, sbb, decoRNG, rotation);
         }
      }

      this.placeSpeleothems(world, rand, sbb, decoRNG);
      decoRNG.setSeed(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      if (this.countExits() == 1 && decoRNG.nextInt(3) == 0) {
         this.makeTreasureCrate(world, sbb);
      } else if (decoRNG.nextInt(3) == 0) {
         this.makeMonolith(world, decoRNG, sbb);
      }
   }

   protected void makeMonolith(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      int mid = this.size / 2;
      int height = 7 + rand.nextInt(8);
      Rotation rotation = RotationUtil.ROTATIONS[rand.nextInt(4)];
      this.fillBlocksRotated(world, sbb, mid - 1, 0, mid - 1, mid - 1, height, mid - 1, Blocks.OBSIDIAN.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, mid, 0, mid - 1, mid, height - 2, mid - 1, Blocks.OBSIDIAN.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, mid - 1, 0, mid, mid - 1, height - 2, mid, Blocks.OBSIDIAN.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, mid, 0, mid, mid, height - 4, mid, Blocks.OBSIDIAN.defaultBlockState(), rotation);
   }

   private int countExits() {
      int count = 0;

      for (boolean openingToward : this.openingTowards) {
         if (openingToward) {
            count++;
         }
      }

      return count;
   }

   private void decorateWall(WorldGenLevel world, BoundingBox sbb, RandomSource decoRNG, Rotation rotation) {
      if (decoRNG.nextBoolean()) {
         this.decorateBracketMushrooms(world, sbb, decoRNG, rotation);
      } else if (decoRNG.nextBoolean()) {
         this.decorateStoneFormation(world, sbb, decoRNG, rotation);
         this.decorateStoneFormation(world, sbb, decoRNG, rotation);
      }
   }

   private void decorateStoneFormation(WorldGenLevel world, BoundingBox sbb, RandomSource decoRNG, Rotation rotation) {
      int z = 5 + decoRNG.nextInt(7);
      int startY = 1 + decoRNG.nextInt(2);

      for (int y = startY; y < this.height; y += 2) {
         int width = 1;
         int depth = 1 + (decoRNG.nextInt(3) == 0 ? 1 : 0);
         this.makeSingleStoneFormation(world, sbb, decoRNG, rotation, z, y, width, depth);
         z += decoRNG.nextInt(4) - decoRNG.nextInt(4);
         if (z < 5 || z > this.size - 5) {
            z = 5 + decoRNG.nextInt(7);
         }
      }
   }

   private void makeSingleStoneFormation(WorldGenLevel world, BoundingBox sbb, RandomSource decoRNG, Rotation rotation, int z, int y, int width, int depth) {
      if (decoRNG.nextInt(8) == 0) {
         this.fillBlocksRotated(
            world, sbb, this.size - (depth + 1), y - width, z - width, this.size - 1, y + width, z + width, Blocks.OBSIDIAN.defaultBlockState(), rotation
         );
      } else if (decoRNG.nextInt(4) == 0) {
         this.fillBlocksRotated(
            world,
            sbb,
            this.size - (depth + 1),
            y - width,
            z - width,
            this.size - 1,
            y + width,
            z + width,
            ((Block)TFBlocks.TROLLSTEINN.get()).defaultBlockState(),
            rotation
         );
      } else {
         this.fillBlocksRotated(
            world, sbb, this.size - (depth + 1), y - width, z - width, this.size - 1, y + width, z + width, Blocks.STONE.defaultBlockState(), rotation
         );
      }
   }

   private void decorateStoneProjection(WorldGenLevel world, BoundingBox sbb, RandomSource decoRNG, Rotation rotation) {
      int z = 7 + decoRNG.nextInt(3) - decoRNG.nextInt(3);
      int y = 7 + decoRNG.nextInt(3) - decoRNG.nextInt(3);
      this.randomlyFillBlocksRotated(
         world,
         sbb,
         decoRNG,
         0.25F,
         this.size - 9,
         y,
         z,
         this.size - 2,
         y + 3,
         z + 3,
         ((Block)TFBlocks.TROLLSTEINN.get()).defaultBlockState(),
         Blocks.STONE.defaultBlockState(),
         rotation
      );
      if (decoRNG.nextBoolean()) {
         this.randomlyFillBlocksRotated(
            world,
            sbb,
            decoRNG,
            0.25F,
            this.size - 9,
            1,
            z,
            this.size - 6,
            y - 1,
            z + 3,
            ((Block)TFBlocks.TROLLSTEINN.get()).defaultBlockState(),
            Blocks.STONE.defaultBlockState(),
            rotation
         );
      } else {
         this.randomlyFillBlocksRotated(
            world,
            sbb,
            decoRNG,
            0.25F,
            this.size - 9,
            y + 4,
            z,
            this.size - 6,
            this.height - 2,
            z + 3,
            ((Block)TFBlocks.TROLLSTEINN.get()).defaultBlockState(),
            Blocks.STONE.defaultBlockState(),
            rotation
         );
      }
   }

   private void decorateBracketMushrooms(WorldGenLevel world, BoundingBox sbb, RandomSource decoRNG, Rotation rotation) {
      int z = 5 + decoRNG.nextInt(7);
      int startY = 1 + decoRNG.nextInt(4);

      for (int y = startY; y < this.height; y += 2) {
         int width = 1 + decoRNG.nextInt(2) + decoRNG.nextInt(2);
         int depth = 1 + decoRNG.nextInt(2) + decoRNG.nextInt(2);
         Block mushBlock = decoRNG.nextInt(3) == 0
            ? (Block)TFBlocks.HUGE_MUSHGLOOM.get()
            : (decoRNG.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK);
         this.makeSingleBracketMushroom(world, sbb, rotation, z, y, width, depth, mushBlock.defaultBlockState());
         z += decoRNG.nextInt(4) - decoRNG.nextInt(4);
         if (z < 5 || z > this.size - 5) {
            z = 5 + decoRNG.nextInt(7);
         }
      }
   }

   private void makeSingleBracketMushroom(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int z, int y, int width, int depth, BlockState mushBlock) {
      this.fillBlocksRotated(
         world,
         sbb,
         this.size - depth,
         y,
         z - (width - 1),
         this.size - 2,
         y,
         z + (width - 1),
         HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.CENTER, mushBlock),
         rotation
      );
      this.fillBlocksRotated(
         world,
         sbb,
         this.size - (depth + 1),
         y,
         z - (width - 1),
         this.size - (depth + 1),
         y,
         z + (width - 1),
         this.getMushroomState(mushBlock, HugeMushroomUtil.HugeMushroomType.EAST),
         rotation
      );
      BlockState northMushroom = this.getMushroomState(mushBlock, HugeMushroomUtil.HugeMushroomType.SOUTH);

      for (int d = 0; d < depth - 1; d++) {
         this.setBlockStateRotated(world, northMushroom, this.size - (2 + d), y, z - width, rotation, sbb);
      }

      BlockState northWestMushroom = this.getMushroomState(mushBlock, HugeMushroomUtil.HugeMushroomType.SOUTH_EAST);
      this.setBlockStateRotated(world, northWestMushroom, this.size - (depth + 1), y, z - width, rotation, sbb);
      BlockState southMushroom = this.getMushroomState(mushBlock, HugeMushroomUtil.HugeMushroomType.NORTH);

      for (int d = 0; d < depth - 1; d++) {
         this.setBlockStateRotated(world, southMushroom, this.size - (2 + d), y, z + width, rotation, sbb);
      }

      BlockState southWestMushroom = this.getMushroomState(mushBlock, HugeMushroomUtil.HugeMushroomType.NORTH_EAST);
      this.setBlockStateRotated(world, southWestMushroom, this.size - (depth + 1), y, z + width, rotation, sbb);
   }

   private BlockState getMushroomState(BlockState mushroomBlockState, HugeMushroomUtil.HugeMushroomType defaultRotation) {
      return HugeMushroomUtil.getState(defaultRotation, mushroomBlockState);
   }

   protected boolean makeGardenCave(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int caveSize, int caveHeight, Rotation rotation
   ) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      BlockPos dest = this.offsetTowerCCoords(x, y, z, caveSize, direction);
      TrollCaveMainComponent cave = new TrollCaveGardenComponent(
         index, dest.getX(), dest.getY(), dest.getZ(), caveSize, caveHeight, direction, this.speleothemConfigHolder
      );
      StructurePiece intersect = list.findCollisionPiece(cave.getBoundingBox());
      StructurePiece otherGarden = this.findNearbyGarden(list, cave.getBoundingBox());
      if ((intersect == null || intersect == this) && otherGarden == null) {
         list.addPiece(cave);
         cave.addChildren(this, list, rand);
         this.openingTowards[rotation.ordinal()] = true;
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   private StructurePiece findNearbyGarden(StructurePieceAccessor list, BoundingBox boundingBox) {
      BoundingBox largeBox = new BoundingBox(
         boundingBox.minX() - 30, boundingBox.minY() - 30, boundingBox.minZ() - 30, boundingBox.maxX() - 30, boundingBox.maxY() - 30, boundingBox.maxZ() - 30
      );
      if (list instanceof StructurePiecesBuilder start) {
         for (StructurePiece component : start.pieces) {
            if (component instanceof TrollCaveGardenComponent && component.getBoundingBox().intersects(largeBox)) {
               return component;
            }
         }
      }

      return null;
   }

   @Override
   protected boolean makeSmallerCave(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int caveSize, int caveHeight, Rotation rotation
   ) {
      if (super.makeSmallerCave(list, rand, index, x, y, z, caveSize, caveHeight, rotation)) {
         this.openingTowards[rotation.ordinal()] = true;
         return true;
      } else {
         return false;
      }
   }
}
