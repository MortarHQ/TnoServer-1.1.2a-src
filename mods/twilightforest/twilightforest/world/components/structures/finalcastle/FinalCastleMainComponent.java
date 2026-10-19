package twilightforest.world.components.structures.finalcastle;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class FinalCastleMainComponent extends TFStructureComponentOld {
   public FinalCastleMainComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCMain.get(), nbt);
   }

   public FinalCastleMainComponent(int i, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCMain.get(), i, x, y, z);
      this.setOrientation(Direction.SOUTH);
      this.spawnListIndex = 1;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, -24, 120, -24, 48, 40, 48, Direction.SOUTH, false);
      this.deco = new StructureTFDecoratorCastle();
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      FinalCastleFoundation48Component foundation = new FinalCastleFoundation48Component(
         4, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(foundation);
      foundation.addChildren(this, list, rand);
      TFStructureComponentOld roof = new FinalCastleRoof48CrenellatedComponent(
         4, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(roof);
      roof.addChildren(this, list, rand);
      TFStructureComponentOld gazebo = new FinalCastleBossGazeboComponent(
         5, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(gazebo);
      gazebo.addChildren(this, list, rand);
      FinalCastleStairTowerComponent tower0 = new FinalCastleStairTowerComponent(
         3, this.boundingBox.minX(), this.boundingBox.minY() + 3, this.boundingBox.minZ(), Direction.NORTH
      );
      list.addPiece(tower0);
      tower0.addChildren(this, list, rand);
      FinalCastleLargeTowerComponent tower1 = new FinalCastleLargeTowerComponent(
         3, this.boundingBox.maxX(), this.boundingBox.minY() + 3, this.boundingBox.minZ(), Direction.EAST
      );
      list.addPiece(tower1);
      tower1.addChildren(this, list, rand);
      FinalCastleStairTowerComponent tower2 = new FinalCastleStairTowerComponent(
         3, this.boundingBox.minX(), this.boundingBox.minY() + 3, this.boundingBox.maxZ(), Direction.WEST
      );
      list.addPiece(tower2);
      tower2.addChildren(this, list, rand);
      FinalCastleStairTowerComponent tower3 = new FinalCastleStairTowerComponent(
         3, this.boundingBox.maxX(), this.boundingBox.minY() + 3, this.boundingBox.maxZ(), Direction.SOUTH
      );
      list.addPiece(tower3);
      tower3.addChildren(this, list, rand);
      BlockPos dest = new BlockPos(this.boundingBox.minX() - 4, this.boundingBox.maxY(), this.boundingBox.minZ() - 24);
      this.buildTowerMaze(list, rand, 48, 0, 24, 60, Direction.SOUTH, ((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get()).defaultBlockState(), dest);
      dest = new BlockPos(this.boundingBox.maxX() + 4, this.boundingBox.minY(), this.boundingBox.maxZ() + 24);
      this.buildTowerMaze(list, rand, 0, 30, 24, 60, Direction.NORTH, ((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get()).defaultBlockState(), dest);
      FinalCastleDungeonStepsComponent steps0 = new FinalCastleDungeonStepsComponent(
         5, this.boundingBox.minX() + 18, this.boundingBox.minY() + 1, this.boundingBox.minZ() + 18, Direction.SOUTH
      );
      list.addPiece(steps0);
      steps0.addChildren(this, list, rand);
      FinalCastleDungeonStepsComponent steps1 = steps0.buildMoreStepsTowards(list, rand, Rotation.COUNTERCLOCKWISE_90);
      FinalCastleDungeonStepsComponent steps2 = steps1.buildMoreStepsTowards(list, rand, Rotation.COUNTERCLOCKWISE_90);
      FinalCastleDungeonStepsComponent steps3 = steps2.buildMoreStepsTowards(list, rand, Rotation.COUNTERCLOCKWISE_90);
      steps3.buildLevelUnder(list, rand, 1);
      BlockPos mc = this.offsetTowerCCoords(48, 23, 25, 1, Direction.SOUTH);
      FinalCastleMuralComponent mural0 = new FinalCastleMuralComponent(7, mc.getX(), mc.getY(), mc.getZ(), 35, 30, Direction.SOUTH);
      list.addPiece(mural0);
      mural0.addChildren(this, list, rand);
      BlockPos mc1 = this.offsetTowerCCoords(48, 33, 24, -1, Direction.SOUTH);
      FinalCastleMuralComponent mural1 = new FinalCastleMuralComponent(7, mc1.getX(), mc1.getY(), mc.getZ(), 19, 12, Direction.NORTH);
      list.addPiece(mural1);
      mural1.addChildren(this, list, rand);
   }

   private void buildTowerMaze(
      StructurePieceAccessor list, RandomSource rand, int x, int y, int z, int howFar, Direction direction, BlockState type, BlockPos dest
   ) {
      if (list instanceof StructurePiecesBuilder start) {
         boolean complete = false;
         int iterations = 0;

         while (!complete && iterations < 15) {
            iterations++;
            List<StructurePiece> before = new LinkedList<>(start.pieces);
            BlockPos tc = this.offsetTowerCCoords(x, y, z, howFar, direction);
            FinalCastleMazeTower13Component sTower = new FinalCastleMazeTower13Component(
               (StructurePieceType)TFStructurePieceTypes.TFFCSiTo.get(), rand, 3, tc.getX(), tc.getY(), tc.getZ(), type, direction
            );
            BlockPos bc = this.offsetTowerCCoords(x, y, z, 1, direction);
            FinalCastleBridgeComponent bridge = new FinalCastleBridgeComponent(this.getGenDepth() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, direction);
            list.addPiece(bridge);
            bridge.addChildren(this, list, rand);
            list.addPiece(sTower);
            sTower.buildTowards(this, list, rand, dest);
            if (this.isMazeComplete(list, type)) {
               complete = true;
            } else {
               start.pieces.clear();
               start.pieces.addAll(before);
            }
         }
      }
   }

   private boolean isMazeComplete(StructurePieceAccessor list, BlockState type) {
      if (list instanceof StructurePiecesBuilder start) {
         if (start.pieces.size() > 60) {
         }

         for (StructurePiece structurecomponent : start.pieces) {
            BoundingBox boundingBox = structurecomponent.getBoundingBox();
            int x = boundingBox.maxX() - boundingBox.minX() / 2 + boundingBox.minX();
            int y = boundingBox.maxY() - boundingBox.minY() / 2 + boundingBox.minY();
            int z = boundingBox.maxZ() - boundingBox.minZ() / 2 + boundingBox.minZ();
            if (type == ((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get()).defaultBlockState() && structurecomponent instanceof FinalCastleEntranceTowerComponent
               )
             {
               return true;
            }

            if (type == ((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get()).defaultBlockState() && structurecomponent instanceof FinalCastleBellTower21Component) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   protected BlockPos offsetTowerCCoords(int x, int y, int z, int howFar, Direction direction) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      switch (direction) {
         case SOUTH:
            dx += howFar;
            break;
         case WEST:
            dz += howFar;
            break;
         case NORTH:
            dx -= howFar;
            break;
         case EAST:
            dz -= howFar;
      }

      return new BlockPos(dx, dy, dz);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(world, sbb, 0, 0, 0, 48, 40, 48, false, rand, this.deco.randomBlocks);
      this.generateBox(world, sbb, 13, 30, 1, 47, 30, 12, false, rand, this.deco.randomBlocks);
      this.generateBox(world, sbb, 13, 31, 12, 36, 31, 12, this.deco.fenceState, this.deco.fenceState, false);
      this.generateBox(world, sbb, 13, 30, 36, 47, 30, 47, false, rand, this.deco.randomBlocks);
      this.generateBox(world, sbb, 13, 31, 36, 36, 31, 36, this.deco.fenceState, this.deco.fenceState, false);
      this.generateBox(world, sbb, 1, 30, 1, 12, 30, 47, false, rand, this.deco.randomBlocks);
      this.generateBox(world, sbb, 12, 31, 12, 12, 31, 36, this.deco.fenceState, this.deco.fenceState, false);
      this.generateBox(world, sbb, 38, 25, 13, 47, 25, 35, false, rand, this.deco.randomBlocks);

      for (int i = 0; i < 5; i++) {
         int y = 30 - i;
         this.makeMezzTopStairs(world, sbb, y, 10 + i, Direction.SOUTH);
         this.makeMezzTopStairs(world, sbb, y, 38 - i, Direction.NORTH);
         y = 25 - i;
         int x = 37 - i;
         BlockState stairState = getStairState(this.deco.stairState, Direction.WEST, false);
         this.generateBox(world, sbb, x, y, 14, x, y, 22, stairState, stairState, false);
         this.generateBox(world, sbb, x, y - 1, 14, x, y - 1, 22, this.deco.blockState, this.deco.blockState, false);
         this.generateBox(world, sbb, x, y, 26, x, y, 34, stairState, stairState, false);
         this.generateBox(world, sbb, x, y - 1, 26, x, y - 1, 34, this.deco.blockState, this.deco.blockState, false);
      }

      for (int x = 11; x < 47; x += 12) {
         for (int z = 11; z < 47; z += 12) {
            this.generateBox(world, sbb, x, 1, z, x + 2, 40, z + 2, this.deco.pillarState, this.deco.blockState, false);
            this.makePillarBase(world, sbb, x, z, 1, false);
            this.makePillarBase(world, sbb, x, z, 19, true);
            this.makePillarBase(world, sbb, x, z, 21, false);
            this.makePillarBase(world, sbb, x, z, 39, true);
         }
      }

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         for (int z = 11; z < 47; z += 12) {
            if (z != 23 || rotation != Rotation.NONE && rotation != Rotation.CLOCKWISE_180) {
               this.fillBlocksRotated(world, sbb, 1, 1, z, 1, 40, z + 2, this.deco.pillarState, rotation);
               this.makeHalfPillarBase(world, sbb, rotation, 1, z, false);
               this.makeHalfPillarBase(world, sbb, rotation, 19, z, true);
               this.makeHalfPillarBase(world, sbb, rotation, 21, z, false);
               this.makeHalfPillarBase(world, sbb, rotation, 39, z, true);
            }
         }
      }

      this.generateBox(world, sbb, 1, 20, 1, 47, 20, 47, false, rand, this.deco.randomBlocks);
      BlockState fieldBlock = ((Block)TFBlocks.PINK_FORCE_FIELD.get()).defaultBlockState();
      this.generateBox(world, sbb, 12, 1, 12, 24, 10, 12, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 12, 1, 12, 12, 10, 24, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 24, 1, 12, 24, 10, 24, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 12, 1, 24, 24, 10, 24, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 13, 10, 12, 23, 10, 24, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 12, 10, 12, 12, 10, 24, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 24, 10, 12, 24, 10, 24, fieldBlock, fieldBlock, false);
      this.generateBox(world, sbb, 17, 1, 12, 19, 4, 12, ((Block)TFBlocks.PINK_CASTLE_DOOR.get()).defaultBlockState(), AIR, false);
      this.generateBox(world, sbb, 17, 1, 24, 19, 4, 24, ((Block)TFBlocks.PINK_CASTLE_DOOR.get()).defaultBlockState(), AIR, false);
      this.makeSmallTowerStairs(world, sbb, Rotation.NONE);
      this.makeSmallTowerStairs(world, sbb, Rotation.CLOCKWISE_90);
      this.makeSmallTowerStairs(world, sbb, Rotation.COUNTERCLOCKWISE_90);
      this.makeLargeTowerStairs(world, sbb, Rotation.CLOCKWISE_180);
      this.generateBox(world, sbb, 48, 1, 23, 48, 4, 25, ((Block)TFBlocks.YELLOW_CASTLE_DOOR.get()).defaultBlockState(), AIR, false);
      this.generateBox(world, sbb, 0, 31, 23, 0, 34, 25, ((Block)TFBlocks.BLUE_CASTLE_DOOR.get()).defaultBlockState(), AIR, false);
   }

   private void makeSmallTowerStairs(WorldGenLevel world, BoundingBox sbb, Rotation rotation) {
      for (int y = 1; y < 4; y++) {
         int z = 40 + y;
         this.fillBlocksRotated(world, sbb, 1, 1, z, 4, y, z, this.deco.blockState, rotation);
         this.fillBlocksRotated(world, sbb, 2, y, z, 3, y, z, getStairState(this.deco.stairState, Direction.NORTH, false), rotation);
      }
   }

   private void makeLargeTowerStairs(WorldGenLevel world, BoundingBox sbb, Rotation rotation) {
      BlockState stairState = getStairState(this.deco.stairState, Direction.NORTH, false);

      for (int y = 1; y < 4; y++) {
         int z = 38 + y;
         this.fillBlocksRotated(world, sbb, 2, 1, z, 6, y, z, this.deco.blockState, rotation);
         this.fillBlocksRotated(world, sbb, 3, y, z, 5, y, z, stairState, rotation);
      }
   }

   private void makeMezzTopStairs(WorldGenLevel world, BoundingBox sbb, int y, int z, Direction stairMeta) {
      BlockState stairState = getStairState(this.deco.stairState, stairMeta, false);
      this.generateBox(world, sbb, 38, y, z, 46, y, z, stairState, stairState, false);
      this.generateBox(world, sbb, 38, y - 1, z, 46, y - 1, z, this.deco.blockState, this.deco.blockState, false);
      this.generateAirBox(world, sbb, 38, y + 1, z, 46, y + 3, z);
   }

   private void makeHalfPillarBase(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y, int z, boolean isFlipped) {
      this.fillBlocksRotated(world, sbb, 2, y, z - 1, 2, y, z + 3, getStairState(this.deco.stairState, Direction.EAST, isFlipped), rotation);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, isFlipped), 1, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, isFlipped), 1, y, z + 3, rotation, sbb);
   }

   private void makePillarBase(WorldGenLevel world, BoundingBox sbb, int x, int z, int y, boolean isFlipped) {
      this.generateBox(world, sbb, x, y, z + 3, x + 3, y, z + 3, getStairState(this.deco.stairState, Direction.SOUTH, isFlipped), AIR, false);
      this.generateBox(world, sbb, x - 1, y, z - 1, x + 2, y, z - 1, getStairState(this.deco.stairState, Direction.NORTH, isFlipped), AIR, false);
      this.generateBox(world, sbb, x + 3, y, z - 1, x + 3, y, z + 2, getStairState(this.deco.stairState, Direction.EAST, isFlipped), AIR, false);
      this.generateBox(world, sbb, x - 1, y, z, x - 1, y, z + 3, getStairState(this.deco.stairState, Direction.WEST, isFlipped), AIR, false);
   }
}
