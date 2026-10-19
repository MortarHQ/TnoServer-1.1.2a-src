package twilightforest.world.components.structures.mushroomtower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerRoofComponent;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class MushroomTowerWingComponent extends TowerWingComponent {
   private static final int RANGE = 200;
   protected static final int FLOOR_HEIGHT = 4;
   protected static final int MAIN_SIZE = 15;
   boolean hasBase = false;
   public boolean isAscender = false;

   public MushroomTowerWingComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFMTWin.get(), nbt);
   }

   public MushroomTowerWingComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.hasBase = nbt.getBoolean("hasBase");
      this.isAscender = nbt.getBoolean("isAscender");
   }

   protected MushroomTowerWingComponent(StructurePieceType piece, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super(piece, i, x, y, z, pSize, pHeight, direction);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putBoolean("hasBase", this.hasBase);
      tagCompound.putBoolean("isAscender", this.isAscender);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      this.hasBase = this.size > 3;
      if (this.isAscender) {
         int[] dest = this.getValidOpening(rand, Rotation.CLOCKWISE_180);
         dest[1] = this.height - 3;
         int childHeight = (rand.nextInt(3) + rand.nextInt(3) + 2) * 4 + 1;
         boolean madeIt = this.makeMainBridge(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], childHeight, Rotation.CLOCKWISE_180);
         if (!madeIt) {
            TwilightForestMod.LOGGER.info("Did not make bridge back to new main");
         } else {
            TwilightForestMod.LOGGER.debug("Made bridge back to new main");
         }
      }

      if (this.getGenDepth() < 5 && this.size > 6) {
         for (Rotation i : RotationUtil.ROTATIONS) {
            if (this.size >= 15 || i != Rotation.CLOCKWISE_180) {
               int[] dest = this.getValidOpening(rand, i);
               int childHeight = (rand.nextInt(2) + rand.nextInt(2) + 2) * 4 + 1;
               this.makeBridge(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], this.size - 4, childHeight, i);
            }
         }
      }

      if (this.isHighest(this.boundingBox, this.size, list) || !this.hasBase) {
         this.makeARoof(parent, list, rand);
      }
   }

   private boolean isOutOfRange(StructurePiece parent, int nx, int nz, int range) {
      BoundingBox sbb = parent.getBoundingBox();
      int centerX = sbb.minX() + (sbb.maxX() - sbb.minX() + 1) / 2;
      int centerZ = sbb.minZ() + (sbb.maxZ() - sbb.minZ() + 1) / 2;
      return Math.abs(nx - centerX) > range || Math.abs(nz - centerZ) > range;
   }

   @Override
   public boolean makeTowerWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, wingSize, direction);
      if (this.isOutOfRange(this, dx[0], dx[2], 200)) {
         return false;
      }

      if (wingSize > 3) {
         dx = this.adjustCoordinates(dx[0], dx[1], dx[2], wingSize, direction, list);
      }

      MushroomTowerWingComponent wing = new MushroomTowerWingComponent(
         (StructurePieceType)TFStructurePieceTypes.TFMTWin.get(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction
      );
      StructurePiece intersect = list.findCollisionPiece(wing.getBoundingBox());
      if (intersect != null && intersect != this && !(intersect instanceof TowerRoofMushroomComponent)) {
         return false;
      }

      if (this instanceof MushroomTowerBridgeComponent && this.isAscender) {
         wing.isAscender = true;
      }

      list.addPiece(wing);
      wing.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }

   protected int[] adjustCoordinates(int x, int y, int z, int wingSize, Direction direction, StructurePieceAccessor list) {
      if (list instanceof StructurePiecesBuilder start) {
         for (StructurePiece obj : start.pieces) {
            if (obj instanceof TowerWingComponent otherWing
               && !(obj instanceof MushroomTowerBridgeComponent)
               && wingSize == otherWing.size
               && otherWing.getBoundingBox().intersects(x - 3, z - 3, x + 3, z + 3)) {
               return switch (direction) {
                  case SOUTH -> new int[]{otherWing.getBoundingBox().minX(), y, otherWing.getBoundingBox().minZ()};
                  case WEST -> new int[]{otherWing.getBoundingBox().maxX(), y, otherWing.getBoundingBox().minZ()};
                  case NORTH -> new int[]{otherWing.getBoundingBox().maxX(), y, otherWing.getBoundingBox().maxZ()};
                  case EAST -> new int[]{otherWing.getBoundingBox().minX(), y, otherWing.getBoundingBox().maxZ()};
                  default -> new int[]{x, y, z};
               };
            }
         }
      }

      return new int[]{x, y, z};
   }

   private boolean isHighest(BoundingBox boundingBox, int size, StructurePieceAccessor list) {
      BoundingBox boxAbove = new BoundingBox(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), 256, boundingBox.maxZ());
      if (list instanceof StructurePiecesBuilder start) {
         for (StructurePiece obj : start.pieces) {
            if (this != obj
               && obj instanceof TowerWingComponent otherWing
               && !(obj instanceof MushroomTowerBridgeComponent)
               && size == otherWing.size
               && otherWing.getBoundingBox().intersects(boxAbove)) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      TowerRoofComponent roof = new TowerRoofMushroomComponent(
         this.getGenDepth() + 1, this, 1.6F, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      if (!(list.findCollisionPiece(roof.getBoundingBox()) instanceof TowerRoofMushroomComponent)) {
         roof = new TowerRoofMushroomComponent(
            this.getGenDepth() + 1, this, 1.0F, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         if (!(list.findCollisionPiece(roof.getBoundingBox()) instanceof TowerRoofMushroomComponent)) {
            roof = new TowerRoofMushroomComponent(
               this.getGenDepth() + 1, this, 0.6F, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
            );
         }
      }

      list.addPiece(roof);
      roof.addChildren(this, list, rand);
   }

   @Override
   protected boolean makeBridge(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      return this.makeBridge(list, rand, index, x, y, z, wingSize, wingHeight, rotation, false);
   }

   protected boolean makeBridge(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation, boolean ascender
   ) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 3, direction);
      if (wingSize == 3) {
         wingHeight = 4;
      }

      MushroomTowerBridgeComponent bridge = new MushroomTowerBridgeComponent(
         (StructurePieceType)TFStructurePieceTypes.TFMTBri.get(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction
      );
      bridge.isAscender = ascender;
      StructurePiece intersect = list.findCollisionPiece(bridge.getBoundingBox());
      if (intersect != null && intersect != this) {
         return false;
      }

      intersect = list.findCollisionPiece(bridge.getWingBB());
      if (intersect != null && intersect != this) {
         return false;
      }

      list.addPiece(bridge);
      bridge.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }

   private boolean makeMainBridge(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingHeight, Rotation rotation) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 3, direction);
      MushroomTowerMainBridgeComponent bridge = new MushroomTowerMainBridgeComponent(index, dx[0], dx[1], dx[2], wingHeight, direction);
      list.addPiece(bridge);
      bridge.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }

   @Override
   public int[] getValidOpening(RandomSource rand, Rotation direction) {
      int wLength = Math.min(this.size / 3, 3);
      int offset = (this.size - wLength) / 2;
      if (direction != Rotation.NONE && direction != Rotation.CLOCKWISE_180) {
         if (direction != Rotation.CLOCKWISE_90 && direction != Rotation.COUNTERCLOCKWISE_90) {
            return new int[]{0, 0, 0};
         }

         int rx = offset + rand.nextInt(wLength);
         int rz = direction == Rotation.CLOCKWISE_90 ? this.size - 1 : 0;
         int ry = this.getYByStairs(rx, rand, direction);
         return new int[]{rx, ry, rz};
      } else {
         int rx = direction == Rotation.NONE ? this.size - 1 : 0;
         int rz = offset + rand.nextInt(wLength);
         int ry = this.getYByStairs(rz, rand, direction);
         return new int[]{rx, ry, rz};
      }
   }

   @Override
   protected int getYByStairs(int rx, RandomSource rand, Rotation direction) {
      int floors = this.height / 4;
      return 5 + rand.nextInt(floors - 1) * 4;
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.makeTrunk(world, sbb);
      this.makeFloorsForTower(world, sbb);
      this.makeOpenings(world, sbb);
   }

   private void makeTrunk(WorldGenLevel world, BoundingBox sbb) {
      int diameter = this.size / 2;
      int hollow = (int)(diameter * 0.8);

      for (int dx = -diameter; dx <= diameter; dx++) {
         for (int dz = -diameter; dz <= diameter; dz++) {
            int ax = Math.abs(dx);
            int az = Math.abs(dz);
            int dist = (int)(Math.max(ax, az) + Math.min(ax, az) * 0.4);
            if (dist <= diameter) {
               this.placeBlock(world, this.deco.floorState, dx + diameter, 0, dz + diameter, sbb);
               this.placeBlock(world, this.deco.floorState, dx + diameter, this.height, dz + diameter, sbb);
               if (dist > hollow) {
                  for (int dy = 0; dy <= this.height; dy++) {
                     this.placeBlock(world, this.deco.blockState, dx + diameter, dy, dz + diameter, sbb);
                  }
               } else {
                  for (int dy = 1; dy <= this.height - 1; dy++) {
                     this.placeBlock(world, AIR, dx + diameter, dy, dz + diameter, sbb);
                  }
               }

               if (this.hasBase) {
                  this.fillColumnDown(world, this.deco.blockState, dx + diameter, -1, dz + diameter, sbb);
               }
            }
         }
      }
   }

   private void makeFloorsForTower(WorldGenLevel world, BoundingBox sbb) {
      int floors = this.height / 4;

      for (int i = 0; i < floors; i++) {
         this.placeFloor(world, i * 4, sbb);
      }
   }

   private void placeFloor(WorldGenLevel world, int dy, BoundingBox sbb) {
      int diameter = this.size / 2;
      int hollow = (int)(diameter * 0.8);

      for (int dx = -diameter; dx <= diameter; dx++) {
         for (int dz = -diameter; dz <= diameter; dz++) {
            int ax = Math.abs(dx);
            int az = Math.abs(dz);
            int dist = (int)(Math.max(ax, az) + Math.min(ax, az) * 0.4);
            if (dist <= hollow) {
               this.placeBlock(world, this.isAscender ? Blocks.JUNGLE_PLANKS.defaultBlockState() : this.deco.floorState, dx + diameter, dy, dz + diameter, sbb);
            }
         }
      }
   }

   @Override
   protected void makeDoorOpening(WorldGenLevel world, int dx, int dy, int dz, BoundingBox sbb) {
      super.makeDoorOpening(world, dx, dy, dz, sbb);
      if (this.getBlock(world, dx, dy + 2, dz, sbb).getBlock() != Blocks.AIR) {
         this.placeBlock(world, this.deco.accentState, dx, dy + 2, dz, sbb);
      }
   }

   @Override
   protected void decorateFloor(
      WorldGenLevel world, RandomSource rand, int floor, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb
   ) {
   }
}
