package twilightforest.world.components.structures.stronghold;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.StructurePiece.BlockSelector;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.beanification.Autowired;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.selectors.KnightStonesRandomBlockSelectorFactory;
import twilightforest.world.components.structures.selectors.StrongholdStonesRandomBlockSelectorFactory;

public abstract class KnightStrongholdComponent extends TFStructureComponentOld {
   @Autowired
   private static StrongholdStonesRandomBlockSelectorFactory strongholdStones;
   @Autowired
   private static KnightStonesRandomBlockSelectorFactory knightStones;
   public final List<BlockPos> doors = new ArrayList<>();

   public KnightStrongholdComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.readOpeningsFromArray(nbt.getIntArray("doorInts"));
   }

   public KnightStrongholdComponent(StructurePieceType type, int i, Direction facing, int x, int y, int z) {
      super(type, i, x, y, z);
      this.boundingBox = this.generateBoundingBox(facing, x, y, z);
      this.setOrientation(facing);
      this.spawnListIndex = this.isComponentProtected() ? 0 : -1;
   }

   private int[] getDoorsAsIntArray() {
      IntBuffer ibuffer = IntBuffer.allocate(this.doors.size() * 3);

      for (BlockPos door : this.doors) {
         ibuffer.put(door.getX());
         ibuffer.put(door.getY());
         ibuffer.put(door.getZ());
      }

      return ibuffer.array();
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putIntArray("doorInts", this.getDoorsAsIntArray());
   }

   private void readOpeningsFromArray(int[] intArray) {
      for (int i = 0; i < intArray.length; i += 3) {
         BlockPos door = new BlockPos(intArray[i], intArray[i + 1], intArray[i + 2]);
         this.doors.add(door);
      }
   }

   public abstract BoundingBox generateBoundingBox(Direction var1, int var2, int var3, int var4);

   public static BoundingBox getComponentToAddBoundingBox(int x, int y, int z, int xOff, int yOff, int zOff, int xSize, int ySize, int zSize, Direction facing) {
      return switch (facing) {
         case WEST -> new BoundingBox(x - zSize + 1 + zOff, y + yOff, z + xOff, x + zOff, y + ySize - 1 + yOff, z + xSize - 1 + xOff);
         case NORTH -> new BoundingBox(x - xSize + 1 - xOff, y + yOff, z - zSize + 1 + zOff, x - xOff, y + ySize - 1 + yOff, z + zOff);
         case EAST -> new BoundingBox(x + zOff, y + yOff, z - xSize + 1 - xOff, x + zSize - 1 + zOff, y + ySize - 1 + yOff, z - xOff);
         default -> new BoundingBox(x + xOff, y + yOff, z + zOff, x + xSize - 1 + xOff, y + ySize - 1 + yOff, z + zSize - 1 + zOff);
      };
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }
   }

   protected void addNewComponent(StructurePiece entrance, StructurePieceAccessor list, RandomSource random, Rotation facing, int x, int y, int z) {
      int index = this.genDepth + 1;
      Direction nFacing = this.getStructureRelativeRotation(facing);
      int nx = this.getWorldX(x, z);
      int ny = this.getWorldY(y);
      int nz = this.getWorldZ(x, z);
      if (index <= 30 && !this.isOutOfRange(entrance, nx, nz, 75)) {
         KnightStrongholdComponent breakIn = (KnightStrongholdComponent)this.findBreakInComponent(list, nx, ny, nz);
         if (breakIn != null && breakIn.attemptToBreakIn(nx, ny, nz)) {
            this.addDoorwayTo(x, y, z, facing);
         } else {
            StrongholdPieces pieceList = ((StrongholdEntranceComponent)entrance).lowerPieces;
            StructurePiece nextComponent = pieceList.getNextComponent(entrance, list, random, index, nFacing, nx, ny, nz);
            if (nextComponent != null) {
               list.addPiece(nextComponent);
               nextComponent.addChildren(entrance, list, random);
               this.addDoorwayTo(x, y, z, facing);
            }
         }
      }
   }

   protected StructurePiece findBreakInComponent(StructurePieceAccessor list, int x, int y, int z) {
      BlockPos pos = new BlockPos(x, y, z);
      if (list instanceof StructurePiecesBuilder start) {
         for (StructurePiece component : start.pieces) {
            if (component.getBoundingBox() != null && component.getBoundingBox().isInside(pos)) {
               return component;
            }
         }
      }

      return null;
   }

   protected void addNewUpperComponent(StructurePiece parent, StructurePieceAccessor list, RandomSource random, Rotation facing, int x, int y, int z) {
      int index = this.genDepth + 1;
      Direction nFacing = this.getStructureRelativeRotation(facing);
      int nx = this.getWorldX(x, z);
      int ny = this.getWorldY(y);
      int nz = this.getWorldZ(x, z);
      if (index <= 100 && !this.isOutOfRange(parent, nx, nz, 48)) {
         KnightStrongholdComponent attempted = switch (random.nextInt(5)) {
            case 1 -> new StrongholdUpperLeftTurnComponent(index, nFacing, nx, ny, nz);
            case 2 -> new StrongholdUpperRightTurnComponent(index, nFacing, nx, ny, nz);
            case 3 -> new StrongholdUpperCorridorComponent(index, nFacing, nx, ny, nz);
            case 4 -> new StrongholdUpperAscenderComponent(index, nFacing, nx, ny, nz);
            default -> new StrongholdUpperTIntersectionComponent(index, nFacing, nx, ny, nz);
         };
         if (attempted != null && list.findCollisionPiece(attempted.getBoundingBox()) == null) {
            list.addPiece(attempted);
            attempted.addChildren(parent, list, random);
         }
      }
   }

   private boolean isOutOfRange(StructurePiece parent, int nx, int nz, int range) {
      return Math.abs(nx - parent.getBoundingBox().minX()) > range || Math.abs(nz - parent.getBoundingBox().minZ()) > range;
   }

   protected void placeDoorwayAt(WorldGenLevel world, int x, int y, int z, BoundingBox sbb) {
      if (x != 0 && x != this.getXSize()) {
         this.generateBox(world, sbb, x - 2, y, z, x + 2, y + 3, z, this.deco.fenceState, Blocks.AIR.defaultBlockState(), false);
         this.generateAirBox(world, sbb, x - 1, y, z, x + 1, y + 3, z);
      } else {
         this.generateBox(world, sbb, x, y, z - 2, x, y + 3, z + 2, this.deco.fenceState, Blocks.AIR.defaultBlockState(), false);
         this.generateAirBox(world, sbb, x, y, z - 1, x, y + 3, z + 1);
      }
   }

   protected int getXSize() {
      return switch (this.getOrientation()) {
         case WEST, EAST -> this.boundingBox.getZSpan() - 1;
         default -> this.boundingBox.getXSpan() - 1;
      };
   }

   protected void placeSmallDoorwayAt(WorldGenLevel world, int facing, int x, int y, int z, BoundingBox sbb) {
      if (facing != 0 && facing != 2) {
         this.generateBox(world, sbb, x, y, z - 1, x, y + 1, z + 1, Blocks.COBBLESTONE_WALL.defaultBlockState(), Blocks.AIR.defaultBlockState(), true);
      } else {
         this.generateBox(world, sbb, x - 1, y, z, x + 1, y + 1, z, Blocks.COBBLESTONE_WALL.defaultBlockState(), Blocks.AIR.defaultBlockState(), true);
      }

      this.generateAirBox(world, sbb, x, y, z, x, y + 1, z);
   }

   public void placeCornerStatue(WorldGenLevel world, int x, int y, int z, int facing, BoundingBox sbb) {
      int ox = 1;
      int oz = 1;
      Direction smx = Direction.EAST;
      Direction smz = Direction.SOUTH;
      switch (facing) {
         case 0:
         default:
            break;
         case 1:
            oz = -1;
            smz = Direction.SOUTH;
            break;
         case 2:
            ox = -1;
            smx = Direction.WEST;
            break;
         case 3:
            ox = -1;
            oz = -1;
            smx = Direction.WEST;
            smz = Direction.NORTH;
      }

      for (int sy = 0; sy < 5; sy++) {
         this.placeBlock(world, this.deco.pillarState, x, y + sy, z, sbb);
      }

      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, y + 4, z + oz, sbb);
      this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x + ox, y + 4, z, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smz, false), x, y + 3, z + oz, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smx, false), x + ox, y + 3, z, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smz, true), x, y + 2, z + oz, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smx, true), x + ox, y + 2, z, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smx, true), x + ox, y + 2, z + oz, sbb);
      this.placeBlock(world, Blocks.COBBLESTONE_WALL.defaultBlockState(), x + ox, y, z + oz, sbb);
      this.placeBlock(world, Blocks.COBBLESTONE_WALL.defaultBlockState(), x + ox, y + 1, z + oz, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smz, false), x, y, z + oz, sbb);
      this.placeBlock(world, getStairState(this.deco.stairState, smx, false), x + ox, y, z, sbb);
   }

   public void placeWallStatue(WorldGenLevel world, int x, int y, int z, Rotation facing, BoundingBox sbb) {
      int ox = 1;
      int oz = 1;

      for (int sy = 0; sy < 5; sy++) {
         this.placeBlock(world, this.deco.pillarState, x, y + sy, z, sbb);
      }

      if (facing != Rotation.NONE && facing != Rotation.CLOCKWISE_180) {
         if (facing == Rotation.COUNTERCLOCKWISE_90) {
            oz = -oz;
            ox = -ox;
         }

         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, y + 4, z - oz, sbb);
         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, y + 4, z + oz, sbb);
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.NONE).rotate(Direction.WEST), false), x, y + 3, z - oz, sbb);
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_180).rotate(Direction.WEST), false), x, y + 3, z + oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), false), x + ox, y + 3, z - oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), false), x + ox, y + 3, z + oz, sbb
         );
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.NONE).rotate(Direction.WEST), true), x, y + 2, z - oz, sbb);
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_180).rotate(Direction.WEST), true), x, y + 2, z + oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), true), x + oz, y + 2, z, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), true), x + ox, y + 2, z - oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), true), x + ox, y + 2, z + oz, sbb
         );
         this.placeBlock(world, Blocks.COBBLESTONE_WALL.defaultBlockState(), x + ox, y, z, sbb);
         this.placeBlock(world, Blocks.COBBLESTONE_WALL.defaultBlockState(), x + ox, y + 1, z, sbb);
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.NONE).rotate(Direction.WEST), false), x, y, z - ox, sbb);
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_180).rotate(Direction.WEST), false), x, y, z + ox, sbb);
      } else {
         if (facing == Rotation.CLOCKWISE_180) {
            ox = -ox;
            oz = -oz;
         }

         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x - ox, y + 4, z, sbb);
         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x + ox, y + 4, z, sbb);
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.NONE).rotate(Direction.WEST), false), x - ox, y + 3, z, sbb);
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_180).rotate(Direction.WEST), false), x + ox, y + 3, z, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), false), x - ox, y + 3, z - oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), false), x + ox, y + 3, z - oz, sbb
         );
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.NONE).rotate(Direction.WEST), true), x - ox, y + 2, z, sbb);
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_180).rotate(Direction.WEST), true), x + ox, y + 2, z, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), true), x, y + 2, z - oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), true), x - ox, y + 2, z - oz, sbb
         );
         this.placeBlock(
            world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_90).rotate(Direction.WEST), true), x + ox, y + 2, z - oz, sbb
         );
         this.placeBlock(world, Blocks.COBBLESTONE_WALL.defaultBlockState(), x, y, z - oz, sbb);
         this.placeBlock(world, Blocks.COBBLESTONE_WALL.defaultBlockState(), x, y + 1, z - oz, sbb);
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.NONE).rotate(Direction.WEST), false), x - ox, y, z, sbb);
         this.placeBlock(world, getStairState(this.deco.stairState, facing.getRotated(Rotation.CLOCKWISE_180).rotate(Direction.WEST), false), x + ox, y, z, sbb);
      }
   }

   public boolean attemptToBreakIn(int wx, int wy, int wz) {
      if (!this.isValidBreakInPoint(wx, wy, wz)) {
         return false;
      }

      int dx = this.getRelativeX(wx, wz);
      int dy = this.getRelativeY(wy);
      int dz = this.getRelativeZ(wx, wz);
      this.addDoor(dx, dy, dz);
      return true;
   }

   public void addDoorwayTo(int dx, int dy, int dz, Rotation facing) {
      switch (facing) {
         case NONE:
            this.addDoor(dx, dy, dz - 1);
            break;
         case CLOCKWISE_90:
            this.addDoor(dx + 1, dy, dz);
            break;
         case CLOCKWISE_180:
            this.addDoor(dx, dy, dz + 1);
            break;
         case COUNTERCLOCKWISE_90:
            this.addDoor(dx - 1, dy, dz);
      }
   }

   public void addDoor(int dx, int dy, int dz) {
      this.doors.add(new BlockPos(dx, dy, dz));
   }

   protected boolean isValidBreakInPoint(int wx, int wy, int wz) {
      if (wy < this.boundingBox.minY() || wy > this.boundingBox.maxY()) {
         return false;
      } else if (wx == this.boundingBox.minX() || wx == this.boundingBox.maxX()) {
         return wz > this.boundingBox.minZ() && wz < this.boundingBox.maxZ();
      } else {
         return wz != this.boundingBox.minZ() && wz != this.boundingBox.maxZ() ? false : wx > this.boundingBox.minX() && wx < this.boundingBox.maxX();
      }
   }

   protected int getRelativeX(int x, int z) {
      return switch (this.getOrientation()) {
         case WEST -> z - this.boundingBox.minZ();
         case NORTH -> this.boundingBox.maxX() - x;
         case EAST -> this.boundingBox.maxZ() - z;
         case SOUTH -> x - this.boundingBox.minX();
         default -> x;
      };
   }

   protected int getRelativeY(int y) {
      return y - this.boundingBox.minY();
   }

   protected int getRelativeZ(int x, int z) {
      return switch (this.getOrientation()) {
         case WEST -> this.boundingBox.maxX() - x;
         case NORTH -> this.boundingBox.maxZ() - z;
         case EAST -> x - this.boundingBox.minX();
         case SOUTH -> z - this.boundingBox.minZ();
         default -> z;
      };
   }

   public void placeDoors(WorldGenLevel world, BoundingBox sbb) {
      if (this.doors != null) {
         for (BlockPos doorCoords : this.doors) {
            this.placeDoorwayAt(world, doorCoords.getX(), doorCoords.getY(), doorCoords.getZ(), sbb);
         }
      }
   }

   protected void placeStrongholdWalls(
      WorldGenLevel world, BoundingBox sbb, int sx, int sy, int sz, int dx, int dy, int dz, RandomSource rand, BlockSelector randomBlocks
   ) {
      for (int y = sy; y <= dy; y++) {
         for (int x = sx; x <= dx; x++) {
            for (int z = sz; z <= dz; z++) {
               boolean wall = y == sy || y == dy || x == sx || x == dx || z == sz || z == dz;
               Block blockID = this.getBlock(world, x, y, z, sbb).getBlock();
               if (blockID == Blocks.AIR) {
                  if (wall) {
                     this.placeBlock(world, Blocks.COBBLESTONE.defaultBlockState(), x, y, z, sbb);
                  }
               } else if (y == sy || y == dy) {
                  BlockSelector strongBlocks = TFStructureComponentOld.getStrongholdStones();
                  strongBlocks.next(rand, x, y, z, wall);
                  this.placeBlock(world, strongBlocks.getNext(), x, y, z, sbb);
               } else if (!wall || blockID != Blocks.DIRT) {
                  randomBlocks.next(rand, x, y, z, wall);
                  this.placeBlock(world, randomBlocks.getNext(), x, y, z, sbb);
               }
            }
         }
      }
   }

   protected void placeUpperStrongholdWalls(
      WorldGenLevel world, BoundingBox sbb, int sx, int sy, int sz, int dx, int dy, int dz, RandomSource rand, BlockSelector randomBlocks
   ) {
      for (int y = sy; y <= dy; y++) {
         for (int x = sx; x <= dx; x++) {
            for (int z = sz; z <= dz; z++) {
               boolean wall = y == sy || y == dy || x == sx || x == dx || z == sz || z == dz;
               BlockState state = this.getBlock(world, x, y, z, sbb);
               BlockState stateBelow = this.getBlock(world, x, y - 1, z, sbb).getBlock().defaultBlockState();
               boolean isKnightStone = knightStones.make().getStates().contains(stateBelow);
               boolean isStrongholdStone = strongholdStones.make().getStates().contains(stateBelow);
               boolean isValidSurface = !state.isAir() && (state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.DIRT));
               boolean isAirWithChance = state.isAir() && rand.nextInt(3) == 0;
               boolean isStoneBelow = isKnightStone || isStrongholdStone;
               if (isValidSurface || isAirWithChance && isStoneBelow) {
                  if (y != sy && y != dy) {
                     randomBlocks.next(rand, x, y, z, wall);
                     this.placeBlock(world, randomBlocks.getNext(), x, y, z, sbb);
                  } else {
                     BlockSelector strongBlocks = TFStructureComponentOld.getStrongholdStones();
                     strongBlocks.next(rand, x, y, z, wall);
                     this.placeBlock(world, strongBlocks.getNext(), x, y, z, sbb);
                  }
               }
            }
         }
      }
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return this.isComponentProtected() ? TerrainAdjustment.BURY : TerrainAdjustment.NONE;
   }

   public interface Factory<T extends KnightStrongholdComponent> {
      T newInstance(int var1, Direction var2, int var3, int var4, int var5);
   }
}
