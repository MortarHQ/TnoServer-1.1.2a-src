package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.phys.AABB;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.RotationUtil;
import twilightforest.util.entities.EntityUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class TowerMainComponent extends TowerWingComponent {
   public TowerMainComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTMai.get(), nbt);
   }

   public TowerMainComponent(RandomSource rand, int index, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTMai.get(), index, x, y + 1, z, 15, 55 + rand.nextInt(32), Direction.SOUTH);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      this.makeARoof(parent, list, rand);

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         int[] dest = this.getValidOpening(rand, rotation);
         if (dest[1] < this.height / 2) {
            dest[1] += 20;
         }

         int childHeight = Math.min(21 + rand.nextInt(10), this.height - dest[1] - 3);
         if (!this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 9, childHeight, rotation)) {
            this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 7, childHeight, rotation);
         }
      }

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         int[] dest = this.getValidOpening(rand, rotation);
         if (dest[1] < this.height / 2) {
            dest[1] += 10;
         }

         int childHeight = Math.min(21 + rand.nextInt(10), this.height - dest[1] - 3);
         if (!this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 9, childHeight, rotation)) {
            this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 7, childHeight, rotation);
         }
      }

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         int[] dest = this.getValidOpening(rand, rotation);
         int childHeight = Math.min(7 + rand.nextInt(6), this.height - dest[1] - 3);
         if (!this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 5, childHeight, rotation)) {
            this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 3, childHeight, rotation);
         }
      }

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         int[] dest = this.getOutbuildingOpening(rand, rotation);
         int childHeight = 11 + rand.nextInt(10);
         int childSize = 7 + rand.nextInt(2) * 2;
         this.makeTowerOutbuilding(list, rand, 1, dest[0], dest[1], dest[2], childSize, childHeight, rotation);
      }

      for (int i = 0; i < 4; i++) {
         for (Rotation towerRotation : RotationUtil.ROTATIONS) {
            int[] dest = this.getValidOpening(rand, towerRotation);
            int childHeight = 6 + rand.nextInt(5);
            if (rand.nextInt(3) == 0 || !this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 5, childHeight, towerRotation)) {
               this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 3, childHeight, towerRotation);
            }
         }
      }
   }

   public int[] getOutbuildingOpening(RandomSource rand, Rotation rotation) {
      int rx = 0;
      int ry = 1;
      int rz = 0;
      switch (rotation) {
         case NONE:
            rx = this.size - 1;
            rz = 6 + rand.nextInt(8);
            break;
         case CLOCKWISE_90:
            rx = 1 + rand.nextInt(11);
            rz = this.size - 1;
            break;
         case CLOCKWISE_180:
            rx = 0;
            rz = 1 + rand.nextInt(8);
            break;
         case COUNTERCLOCKWISE_90:
            rx = 3 + rand.nextInt(11);
            rz = 0;
      }

      return new int[]{rx, ry, rz};
   }

   public boolean makeTowerOutbuilding(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation
   ) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, wingSize, direction);
      TowerOutbuildingComponent outbuilding = new TowerOutbuildingComponent(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
      StructurePiece intersect = list.findCollisionPiece(outbuilding.getBoundingBox());
      if (intersect == null) {
         list.addPiece(outbuilding);
         outbuilding.addChildren(this, list, rand);
         this.addOpening(x, y, z, rotation);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, false, rand, TFStructureComponentOld.getStrongholdStones());
      this.generateAirBox(world, sbb, 1, 1, 1, this.size - 2, this.height - 2, this.size - 2);
      BlockState defaultState = Blocks.COBBLESTONE.defaultBlockState();

      for (int x = 0; x < this.size; x++) {
         for (int z = 0; z < this.size; z++) {
            this.fillColumnDown(world, defaultState, x, -1, z, sbb);
         }
      }

      if (this.height - this.highestOpening > 15) {
         this.highestOpening = this.height - 15;
      }

      this.makeStairs(world, rand, sbb);
      this.makeOpenings(world, sbb);
      this.decorateStairFloor(world, generator, rand, sbb);
      this.makeStairwayCrossings(world, rand, sbb);
      this.makeLichRoom(world, rand, sbb);
      this.makeTowerPaintings(world, rand, sbb);
   }

   protected void makeStairwayCrossings(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      int flights = this.highestOpening / 5 - 2;

      for (int i = 2 + rand.nextInt(2); i < flights; i += 1 + rand.nextInt(5)) {
         this.makeStairCrossing(world, rand, i, sbb);
      }
   }

   protected void makeStairCrossing(WorldGenLevel world, RandomSource rand, int flight, BoundingBox sbb) {
      Direction temp = this.getOrientation();
      if (flight % 2 == 0) {
         this.setOrientation(this.getStructureRelativeRotation(Rotation.CLOCKWISE_90));
      }

      int floorLevel = flight * 5;
      BlockState crossingfloor = rand.nextBoolean()
         ? (BlockState)Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)
         : Blocks.BIRCH_PLANKS.defaultBlockState();

      for (int dx = 6; dx <= 8; dx++) {
         for (int dz = 4; dz <= 10; dz++) {
            this.placeBlock(world, crossingfloor, dx, floorLevel, dz, sbb);
         }
      }

      floorLevel++;
      int dx = 6;

      for (int dz = 3; dz <= 11; dz++) {
         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), dx, floorLevel, dz, sbb);
      }

      dx++;

      for (int dz = 3; dz <= 11; dz++) {
         this.placeBlock(world, AIR, dx, floorLevel, dz, sbb);
      }

      dx++;

      for (int dz = 3; dz <= 11; dz++) {
         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), dx, floorLevel, dz, sbb);
      }

      this.placeBlock(world, crossingfloor, 6, floorLevel - 1, 11, sbb);
      this.placeBlock(world, crossingfloor, 8, floorLevel - 1, 3, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), 5, floorLevel, 11, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), 9, floorLevel, 3, sbb);

      EntityType<?> mobID = switch (rand.nextInt(4)) {
         case 2 -> EntityType.ZOMBIE;
         case 3 -> (EntityType)TFEntities.SWARM_SPIDER.get();
         default -> EntityType.SKELETON;
      };
      this.setSpawner(world, 7, floorLevel + 2, 7, sbb, mobID);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), 6, floorLevel + 1, 7, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), 8, floorLevel + 1, 7, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), 6, floorLevel + 2, 7, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), 8, floorLevel + 2, 7, sbb);
      this.setOrientation(temp);
   }

   protected void makeLichRoom(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      int floorLevel = 2 + this.highestOpening / 5 * 5;
      Rotation i = this.highestOpening / 5 % 2 == 0 ? Rotation.NONE : Rotation.CLOCKWISE_90;
      this.makeLichFloor(world, floorLevel, i, sbb);
      this.decorateLichChandelier(world, floorLevel, sbb);
      this.decoratePaintings(world, rand, floorLevel, sbb);
      this.decorateTorches(world, rand, floorLevel, sbb);
      this.placeBlock(world, ((Block)TFBlocks.LICH_BOSS_SPAWNER.get()).defaultBlockState(), this.size / 2, floorLevel + 2, this.size / 2, sbb);
   }

   protected void makeTowerPaintings(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      int howMany = 10;
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.WEST, 48, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.WEST, 32, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.WEST, 0, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.EAST, 48, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.EAST, 32, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.EAST, 0, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.NORTH, 48, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.NORTH, 32, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.NORTH, 0, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.SOUTH, 48, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.SOUTH, 32, sbb);
      this.generatePaintingsOnWall(world, rand, howMany, 0, Direction.SOUTH, 0, sbb);
   }

   protected void makeLichFloor(WorldGenLevel world, int floorLevel, Rotation rotation, BoundingBox sbb) {
      Direction temp = this.getOrientation();
      this.setOrientation(this.getStructureRelativeRotation(rotation));
      BlockState birchSlab = (BlockState)Blocks.BIRCH_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
      BlockState birchPlank = Blocks.BIRCH_PLANKS.defaultBlockState();

      for (int fx = 1; fx < 14; fx++) {
         for (int fz = 1; fz < 14; fz++) {
            if ((fx == 1 || fx == 2) && fz >= 6 && fz <= 12) {
               if (fz == 6) {
                  this.placeBlock(world, birchSlab, fx, floorLevel, fz, sbb);
               }
            } else if ((fx == 12 || fx == 13) && fz >= 3 && fz <= 8) {
               if (fz == 8) {
                  this.placeBlock(world, birchSlab, fx, floorLevel, fz, sbb);
               }
            } else if (fx >= 4 && fx <= 10 && fz >= 4 && fz <= 10) {
               if ((fx != 4 || fz != 4) && (fx != 10 || fz != 10)) {
                  this.placeBlock(world, Blocks.GLASS.defaultBlockState(), fx, floorLevel, fz, sbb);
               } else {
                  this.placeBlock(world, birchPlank, fx, floorLevel, fz, sbb);
               }
            } else if (fx != 2 && fx != 3 || fz != 2 && fz != 3) {
               if (fx != 11 && fx != 12 || fz != 11 && fz != 12) {
                  this.placeBlock(world, birchPlank, fx, floorLevel, fz, sbb);
               } else {
                  this.placeBlock(world, Blocks.GLASS.defaultBlockState(), fx, floorLevel, fz, sbb);
               }
            } else {
               this.placeBlock(world, Blocks.GLASS.defaultBlockState(), fx, floorLevel, fz, sbb);
            }
         }
      }

      this.placeBlock(world, AIR, 3, floorLevel + 1, 11, sbb);
      this.placeBlock(world, AIR, 3, floorLevel + 1, 10, sbb);
      this.placeBlock(world, AIR, 3, floorLevel + 2, 11, sbb);
      this.placeBlock(world, AIR, 11, floorLevel + 1, 3, sbb);
      this.placeBlock(world, AIR, 11, floorLevel + 1, 4, sbb);
      this.placeBlock(world, AIR, 11, floorLevel + 2, 3, sbb);
      this.setOrientation(temp);
   }

   protected void decorateLichChandelier(WorldGenLevel world, int floorLevel, BoundingBox sbb) {
      int cx = this.size / 2;
      int cy = floorLevel + 4;
      int cz = this.size / 2;
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx + 1, cy, cz, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx + 2, cy, cz, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx + 1, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, cy, cz + 2, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx - 1, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx - 1, cy, cz, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx - 2, cy, cz, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx - 1, cy, cz - 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, cy, cz - 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, cy, cz - 2, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx + 1, cy, cz - 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx + 1, ++cy, cz, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx + 2, cy, cz, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx + 1, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx, cy, cz + 2, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx - 1, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx - 1, cy, cz, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx - 2, cy, cz, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx - 1, cy, cz - 1, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, cy, cz - 1, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx, cy, cz - 2, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx + 1, cy, cz - 1, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx + 1, ++cy, cz, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx, cy, cz + 1, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx - 1, cy, cz, sbb);
      this.placeBlock(world, Blocks.TORCH.defaultBlockState(), cx, cy, cz - 1, sbb);

      for (int y = floorLevel + 5; y < this.height - 1; y++) {
         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), cx, y, cz, sbb);
      }
   }

   protected void decoratePaintings(WorldGenLevel world, RandomSource rand, int floorLevel, BoundingBox sbb) {
      int howMany = 25;

      for (Direction horizontal : Plane.HORIZONTAL) {
         this.generatePaintingsOnWall(world, rand, howMany, floorLevel, horizontal, 48, sbb);
         this.generatePaintingsOnWall(world, rand, howMany, floorLevel, horizontal, 32, sbb);
         this.generatePaintingsOnWall(world, rand, howMany, floorLevel, horizontal, 0, sbb);
      }
   }

   protected void decorateTorches(WorldGenLevel world, RandomSource rand, int floorLevel, BoundingBox sbb) {
      this.generateTorchesOnWall(world, rand, floorLevel, Direction.SOUTH, sbb);
      this.generateTorchesOnWall(world, rand, floorLevel, Direction.EAST, sbb);
      this.generateTorchesOnWall(world, rand, floorLevel, Direction.NORTH, sbb);
      this.generateTorchesOnWall(world, rand, floorLevel, Direction.WEST, sbb);
   }

   protected void generateTorchesOnWall(WorldGenLevel world, RandomSource rand, int floorLevel, Direction direction, BoundingBox sbb) {
      for (int i = 0; i < 5; i++) {
         BlockPos wCoords = this.getRandomWallSpot(rand, floorLevel, direction, sbb);
         if (wCoords != null) {
            MutableBlockPos tCoords = new MutableBlockPos(wCoords.getX(), wCoords.getY(), wCoords.getZ());
            BlockState blockState = world.getBlockState(tCoords);
            BlockState aboveBlockState = world.getBlockState(tCoords.above());
            if (blockState.isAir() && aboveBlockState.isAir() && EntityUtil.getEntitiesInAABB(world, new AABB(tCoords)).size() == 0) {
               world.setBlock(
                  tCoords,
                  (BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), true),
                  2
               );
               world.setBlock(tCoords.above(), Blocks.TORCH.defaultBlockState(), 2);
            }
         }
      }
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.BEARD_BOX;
   }
}
