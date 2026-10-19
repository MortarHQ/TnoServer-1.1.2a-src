package twilightforest.world.components.structures.darktower;

import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.StructurePiece.BlockSelector;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.TFStructureDecorator;
import twilightforest.world.components.structures.lichtower.TowerRoofAttachedSlabComponent;
import twilightforest.world.components.structures.lichtower.TowerRoofComponent;
import twilightforest.world.components.structures.lichtower.TowerRoofFenceComponent;
import twilightforest.world.components.structures.lichtower.TowerRoofGableForwardsComponent;
import twilightforest.world.components.structures.lichtower.TowerRoofSlabForwardsComponent;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class DarkTowerWingComponent extends TowerWingComponent {
   protected boolean keyTower = false;
   protected final ArrayList<EnumDarkTowerDoor> openingTypes = new ArrayList<>();

   public DarkTowerWingComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFDTWin.get(), nbt);
   }

   public DarkTowerWingComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.keyTower = nbt.getBoolean("keyTower");
      this.readDoorsTypesFromArray(nbt.getIntArray("doorTypeInts"));
   }

   protected DarkTowerWingComponent(StructurePieceType piece, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super(piece, i, x, y, z, pSize, pHeight, direction);
   }

   private int[] getDoorsTypesAsIntArray() {
      int[] ret = new int[this.openingTypes.size()];
      int idx = 0;

      for (EnumDarkTowerDoor doorType : this.openingTypes) {
         ret[idx++] = doorType.ordinal();
      }

      return ret;
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putBoolean("keyTower", this.keyTower);
      tagCompound.putIntArray("doorTypeInts", this.getDoorsTypesAsIntArray());
   }

   private void readDoorsTypesFromArray(int[] intArray) {
      for (int typeInt : intArray) {
         this.openingTypes.add(EnumDarkTowerDoor.values()[typeInt]);
      }
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      this.makeARoof(parent, list, rand);
      this.makeABeard(parent, list, rand);
      if (this.size > 10) {
         for (Rotation direction : RotationUtil.ROTATIONS) {
            int[] dest = this.getValidOpening(rand, direction);
            int childHeight = this.validateChildHeight(this.height - 4 + rand.nextInt(10) - rand.nextInt(10));
            boolean madeWing = this.makeTowerWing(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], this.size - 2, childHeight, direction);
            if (!madeWing && (direction == Rotation.CLOCKWISE_180 || rand.nextBoolean())) {
               this.makeTowerBalcony(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], direction);
            }
         }
      } else if (rand.nextInt(4) == 0 && !(this instanceof DarkTowerEntranceComponent)) {
         Rotation direction = RotationUtil.ROTATIONS[rand.nextInt(4)];
         int[] dest = this.getValidOpening(rand, direction);
         this.makeTowerBalcony(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], direction);
      }
   }

   protected int validateChildHeight(int childHeight) {
      return childHeight / 4 * 4 + 1;
   }

   @Override
   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      int index = this.getGenDepth();

      TowerRoofComponent roof = switch (rand.nextInt(5)) {
         case 2 -> new DarkTowerRoofCactusComponent(
            index, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         case 3 -> new DarkTowerRoofRingsComponent(
            index, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         case 4 -> new DarkTowerRoofFourPostComponent(
            index, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         default -> new DarkTowerRoofAntennaComponent(
            index, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
      };
      list.addPiece(roof);
      roof.addChildren(this, list, rand);
      this.roofType = (Class<? extends TowerRoofComponent>)roof.getClass();
   }

   @Override
   protected void makeAttachedRoof(StructurePieceAccessor list, RandomSource rand) {
      int index = this.getGenDepth();
      if (this.roofType == null && rand.nextInt(32) != 0) {
         this.tryToFitRoof(
            list,
            rand,
            new TowerRoofGableForwardsComponent(
               index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
            )
         );
      }

      if (this.roofType == null && rand.nextInt(8) != 0) {
         this.tryToFitRoof(
            list,
            rand,
            new TowerRoofSlabForwardsComponent(
               index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
            )
         );
      }

      if (this.roofType == null && rand.nextInt(32) != 0) {
         TowerRoofComponent roof = new TowerRoofAttachedSlabComponent(
            index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         this.tryToFitRoof(list, rand, roof);
      }

      if (this.roofType == null) {
         TowerRoofComponent roof = new TowerRoofFenceComponent(
            index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         this.tryToFitRoof(list, rand, roof);
      }
   }

   @Override
   public void makeABeard(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      DarkTowerBeardComponent beard = new DarkTowerBeardComponent(
         this.getGenDepth() + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(beard);
      beard.addChildren(this, list, rand);
   }

   @Override
   public boolean makeTowerWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      if (wingHeight < 8) {
         return false;
      }

      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 5, direction);
      if (dx[1] + wingHeight > 250) {
         return false;
      }

      DarkTowerBridgeComponent bridge = new DarkTowerBridgeComponent(
         (StructurePieceType)TFStructurePieceTypes.TFDTBri.get(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction
      );
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

   protected boolean makeTowerBalcony(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, Rotation rotation) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 5, direction);
      DarkTowerBalconyComponent balcony = new DarkTowerBalconyComponent(index, dx[0], dx[1], dx[2], direction);
      StructurePiece intersect = list.findCollisionPiece(balcony.getBoundingBox());
      if (intersect != null && intersect != this) {
         return false;
      }

      list.addPiece(balcony);
      balcony.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation, EnumDarkTowerDoor.REAPPEARING);
      return true;
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      this.makeEncasedWalls(world, rand, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
      this.generateAirBox(world, sbb, 1, 1, 1, this.size - 2, this.height - 2, this.size - 2);
      if (this.size > 9) {
         this.addHalfFloors(world, decoRNG, sbb, 4, this.height - 1);
      } else if (decoRNG.nextInt(3) == 0) {
         this.addSmallTimberBeams(world, decoRNG, sbb, 4, this.height - 1);
      } else {
         this.addHalfFloors(world, decoRNG, sbb, 4, this.height - 1);
      }

      this.makeOpenings(world, sbb);
      if (decoRNG.nextBoolean() && !this.isKeyTower() && this.height > 8) {
         int blobs = 1;
         if (this.size > 9 && decoRNG.nextBoolean()) {
            blobs++;
         }

         for (int i = 0; i < blobs; i++) {
            int x = decoRNG.nextInt(this.size);
            int y = decoRNG.nextInt(this.height - 7) + 2;
            int z = decoRNG.nextInt(this.size);
            this.destroyTower(world, decoRNG, x, y, z, 3, sbb);
         }
      }
   }

   protected void destroyTower(WorldGenLevel world, RandomSource decoRNG, int x, int y, int z, int amount, BoundingBox sbb) {
      int initialRadius = decoRNG.nextInt(amount) + amount;
      this.drawBlob(world, x, y, z, initialRadius, AIR, sbb);

      for (int i = 0; i < 3; i++) {
         int dx = x + (initialRadius - 1) * (decoRNG.nextBoolean() ? 1 : -1);
         int dy = y + (initialRadius - 1) * (decoRNG.nextBoolean() ? 1 : -1);
         int dz = z + (initialRadius - 1) * (decoRNG.nextBoolean() ? 1 : -1);
         this.netherTransformBlob(world, decoRNG, dx, dy, dz, initialRadius - 1, sbb);
         this.drawBlob(world, dx, dy, dz, initialRadius - 2, AIR, sbb);
      }
   }

   private void netherTransformBlob(WorldGenLevel world, RandomSource inRand, int sx, int sy, int sz, int rad, BoundingBox sbb) {
      RandomSource rand = RandomSource.create(inRand.nextLong());

      for (byte dx = 0; dx <= rad; dx++) {
         for (byte dy = 0; dy <= rad; dy++) {
            for (byte dz = 0; dz <= rad; dz++) {
               byte dist;
               if (dx >= dy && dx >= dz) {
                  dist = (byte)(dx + (byte)(Math.max(dy, dz) * 0.5 + Math.min(dy, dz) * 0.25));
               } else if (dy >= dx && dy >= dz) {
                  dist = (byte)(dy + (byte)(Math.max(dx, dz) * 0.5 + Math.min(dx, dz) * 0.25));
               } else {
                  dist = (byte)(dz + (byte)(Math.max(dx, dy) * 0.5 + Math.min(dx, dy) * 0.25));
               }

               if (dist <= rad) {
                  this.testAndChangeToNetherrack(world, rand, sx + dx, sy + dy, sz + dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx + dx, sy + dy, sz + dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx + dx, sy + dy, sz - dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx - dx, sy + dy, sz + dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx - dx, sy + dy, sz - dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx + dx, sy - dy, sz + dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx + dx, sy - dy, sz - dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx - dx, sy - dy, sz + dz, sbb);
                  this.testAndChangeToNetherrack(world, rand, sx - dx, sy - dy, sz - dz, sbb);
               }
            }
         }
      }
   }

   private void testAndChangeToNetherrack(WorldGenLevel world, RandomSource rand, int x, int y, int z, BoundingBox sbb) {
      if (this.getBlock(world, x, y, z, sbb).getBlock() != Blocks.AIR) {
         this.placeBlock(world, Blocks.NETHERRACK.defaultBlockState(), x, y, z, sbb);
         if (this.getBlock(world, x, y + 1, z, sbb).getBlock() == Blocks.AIR && rand.nextBoolean()) {
            this.placeBlock(world, Blocks.FIRE.defaultBlockState(), x, y + 1, z, sbb);
         }
      }
   }

   private void drawBlob(WorldGenLevel world, int sx, int sy, int sz, int rad, BlockState state, BoundingBox sbb) {
      for (byte dx = 0; dx <= rad; dx++) {
         for (byte dy = 0; dy <= rad; dy++) {
            for (byte dz = 0; dz <= rad; dz++) {
               byte dist;
               if (dx >= dy && dx >= dz) {
                  dist = (byte)(dx + (byte)(Math.max(dy, dz) * 0.5 + Math.min(dy, dz) * 0.25));
               } else if (dy >= dx && dy >= dz) {
                  dist = (byte)(dy + (byte)(Math.max(dx, dz) * 0.5 + Math.min(dx, dz) * 0.25));
               } else {
                  dist = (byte)(dz + (byte)(Math.max(dx, dy) * 0.5 + Math.min(dx, dy) * 0.25));
               }

               if (dist <= rad) {
                  this.placeBlock(world, state, sx + dx, sy + dy, sz + dz, sbb);
                  this.placeBlock(world, state, sx + dx, sy + dy, sz - dz, sbb);
                  this.placeBlock(world, state, sx - dx, sy + dy, sz + dz, sbb);
                  this.placeBlock(world, state, sx - dx, sy + dy, sz - dz, sbb);
                  this.placeBlock(world, state, sx + dx, sy - dy, sz + dz, sbb);
                  this.placeBlock(world, state, sx + dx, sy - dy, sz - dz, sbb);
                  this.placeBlock(world, state, sx - dx, sy - dy, sz + dz, sbb);
                  this.placeBlock(world, state, sx - dx, sy - dy, sz - dz, sbb);
               }
            }
         }
      }
   }

   private void addHalfFloors(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int bottom, int top) {
      int spacing = 4;
      Rotation rotation = RotationUtil.ROTATIONS[(this.boundingBox.minY() + bottom) % 3];
      if (bottom == 0) {
         bottom += spacing;
      }

      for (int y = bottom; y < top; y += spacing) {
         rotation = rotation.getRotated(Rotation.CLOCKWISE_180);
         if (y >= top - spacing) {
            this.makeFullFloor(world, sbb, y);
            if (this.isDeadEnd()) {
               this.decorateTreasureRoom(world, sbb, rotation, y, 4, this.deco);
            }
         } else {
            this.makeHalfFloor(world, sbb, rotation, y);
            switch (rand.nextInt(8)) {
               case 0:
                  if (this.size < 11) {
                     this.decorateReappearingFloor(world, sbb, rotation, y);
                     break;
                  }
               case 1:
                  this.decorateSpawner(world, rand, sbb, rotation, y);
                  break;
               case 2:
                  this.decorateLounge(world, sbb, rotation, y);
                  break;
               case 3:
                  this.decorateLibrary(world, sbb, rotation, y);
                  break;
               case 4:
                  this.decorateExperimentPulser(world, sbb, rotation, y);
                  break;
               case 5:
                  this.decorateExperimentLamp(world, sbb, rotation, y);
                  break;
               case 6:
                  this.decoratePuzzleChest(world, sbb, rotation, y);
            }
         }

         this.addStairsDown(world, sbb, rotation, y, this.size - 2, spacing);
         if (this.size > 9) {
            this.addStairsDown(world, sbb, rotation, y, this.size - 3, spacing);
         }
      }

      rotation = rotation.getRotated(Rotation.CLOCKWISE_180);
      this.addStairsDown(world, sbb, rotation, this.height - 1, this.size - 2, spacing);
   }

   protected void makeHalfFloor(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      this.fillBlocksRotated(world, sbb, this.size / 2, y, 1, this.size - 2, y, this.size - 2, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, this.size / 2 - 1, y, 1, this.size / 2 - 1, y, this.size - 2, this.deco.accentState, rotation);
   }

   protected void makeFullFloor(WorldGenLevel world, BoundingBox sbb, int y) {
      this.generateBox(world, sbb, 1, y, 1, this.size - 2, y, this.size - 2, this.deco.blockState, Blocks.AIR.defaultBlockState(), false);
      this.generateBox(world, sbb, this.size / 2, y, 1, this.size / 2, y, this.size - 2, this.deco.accentState, Blocks.AIR.defaultBlockState(), true);
   }

   protected void decorateTreasureRoom(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y, int spacing, TFStructureDecorator myDeco) {
      int x = this.size / 2;
      int z = this.size / 2;
      this.makePillarFrame(world, sbb, this.deco, rotation, x - 1, y, z - 1, true);
      this.setBlockStateRotated(world, myDeco.platformState, x, y + 1, z, rotation, sbb);
      this.placeTreasureAtCurrentPosition(world, x, y + 2, z, this.isKeyTower() ? TFLootTables.DARKTOWER_KEY : TFLootTables.DARKTOWER_CACHE, sbb);
   }

   private void decorateSpawner(WorldGenLevel world, RandomSource rand, BoundingBox sbb, Rotation rotation, int y) {
      int x = this.size > 9 ? 4 : 3;
      int z = this.size > 9 ? 5 : 4;
      EntityType<?> mobID;
      if (this.size > 9) {
         mobID = rand.nextBoolean() ? (EntityType)TFEntities.CARMINITE_GOLEM.get() : (EntityType)TFEntities.CARMINITE_BROODLING.get();
      } else {
         mobID = (EntityType<?>)TFEntities.CARMINITE_BROODLING.get();
      }

      this.makePillarFrame(world, sbb, this.deco, rotation, x, y, z, true);
      this.setSpawnerRotated(world, x + 1, y + 2, z + 1, rotation, mobID, sbb);
   }

   private void decorateLounge(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      int cx = this.size > 9 ? 9 : 7;
      int cz = this.size > 9 ? 4 : 3;
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), cx, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, false), cx, y + 1, cz + 1, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, false), cx, y + 1, cz + 2, rotation, sbb);
      cx = this.size > 9 ? 5 : 3;
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, true), cx, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, getSlabState(Blocks.SPRUCE_SLAB.defaultBlockState(), SlabType.TOP), cx, y + 1, cz + 1, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, true), cx, y + 1, cz + 2, rotation, sbb);
   }

   private void decorateReappearingFloor(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      BlockState inactiveReappearing = ((Block)TFBlocks.REAPPEARING_BLOCK.get()).defaultBlockState();
      BlockState woodenPressurePlate = Blocks.OAK_PRESSURE_PLATE.defaultBlockState();
      this.fillBlocksRotated(world, sbb, 4, y, 3, 7, y, 5, inactiveReappearing, rotation);
      this.fillBlocksRotated(world, sbb, 4, y + 1, 2, 7, y + 1, 2, woodenPressurePlate, rotation);
      this.fillBlocksRotated(world, sbb, 4, y + 1, 6, 7, y + 1, 6, woodenPressurePlate, rotation);
   }

   private void decorateExperimentLamp(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      int cx = this.size > 9 ? 5 : 3;
      int cz = this.size > 9 ? 5 : 4;
      BlockState redstoneLamp = Blocks.REDSTONE_LAMP.defaultBlockState();
      this.setBlockStateRotated(
         world, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.UP), cx, y + 1, cz, rotation, sbb
      );
      this.setBlockStateRotated(world, redstoneLamp, cx, y + 2, cz, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, cx, y + 1, cz + 1, rotation, sbb);
      this.setBlockStateRotated(
         world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.NORTH, false), cx, y + 1, cz + 2, rotation, sbb
      );
      this.setBlockStateRotated(world, this.deco.accentState, cx, y + 3, cz - 1, rotation, sbb);
      this.setBlockStateRotated(
         world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.SOUTH, true), cx, y + 3, cz - 2, rotation, sbb
      );
   }

   protected static BlockState getLeverState(BlockState initialState, AttachFace face, Direction direction, boolean isPowered) {
      switch (direction) {
         case UP:
         case DOWN:
         default:
            direction = Direction.NORTH;
         case NORTH:
         case SOUTH:
         case EAST:
         case WEST:
            return (BlockState)((BlockState)((BlockState)initialState.setValue(HorizontalDirectionalBlock.FACING, direction))
                  .setValue(FaceAttachedHorizontalDirectionalBlock.FACE, face))
               .setValue(LeverBlock.POWERED, isPowered);
      }
   }

   private void decorateExperimentPulser(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      int cx = this.size > 9 ? 6 : 5;
      int cz = this.size > 9 ? 4 : 3;
      BlockState redstoneWire = Blocks.REDSTONE_WIRE.defaultBlockState();
      BlockState woodenPressurePlate = Blocks.OAK_PRESSURE_PLATE.defaultBlockState();
      BlockState stickyPiston = (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH);
      BlockState unpoweredRepeater = (BlockState)((BlockState)((BlockState)Blocks.REPEATER.defaultBlockState().setValue(DiodeBlock.POWERED, false))
            .setValue(HorizontalDirectionalBlock.FACING, Direction.WEST))
         .setValue(RepeaterBlock.DELAY, 2);
      this.setBlockStateRotated(world, stickyPiston, cx, y + 1, cz + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, cx, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, redstoneWire, cx + 1, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, woodenPressurePlate, cx + 2, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, unpoweredRepeater, cx - 1, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, redstoneWire, cx - 2, y + 1, cz, rotation, sbb);
      this.setBlockStateRotated(world, redstoneWire, cx - 2, y + 1, cz + 1, rotation, sbb);
      this.setBlockStateRotated(world, redstoneWire, cx - 1, y + 1, cz + 1, rotation, sbb);
   }

   private void decorateLibrary(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      int bx = this.size > 9 ? 4 : 3;
      int bz = this.size > 9 ? 3 : 2;
      this.makeSmallBookshelf(world, sbb, rotation, y, bx, bz);
      bx = this.size > 9 ? 9 : 7;
      bz = this.size > 9 ? 3 : 2;
      this.makeSmallBookshelf(world, sbb, rotation, y, bx, bz);
   }

   protected void makeSmallBookshelf(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y, int bx, int bz) {
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, false), bx, y + 1, bz, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, true), bx, y + 2, bz, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), bx, y + 1, bz + 3, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, true), bx, y + 2, bz + 3, rotation, sbb);
      BlockState bookshelf = Blocks.BOOKSHELF.defaultBlockState();
      this.setBlockStateRotated(world, bookshelf, bx, y + 1, bz + 1, rotation, sbb);
      this.setBlockStateRotated(world, bookshelf, bx, y + 2, bz + 1, rotation, sbb);
      this.setBlockStateRotated(world, bookshelf, bx, y + 1, bz + 2, rotation, sbb);
      this.setBlockStateRotated(world, bookshelf, bx, y + 2, bz + 2, rotation, sbb);
   }

   private void decoratePuzzleChest(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      int x = this.size > 9 ? 4 : 3;
      int z = this.size > 9 ? 5 : 4;
      this.makePillarFrame(world, sbb, this.deco, rotation, x, y, z, true);
      this.setBlockStateRotated(world, this.deco.platformState, x + 1, y + 1, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x + 2, y + 1, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x, y + 1, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x + 1, y + 1, z + 2, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x + 1, y + 1, z, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x + 2, y + 3, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x, y + 3, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x + 1, y + 3, z + 2, rotation, sbb);
      this.setBlockStateRotated(world, AIR, x + 1, y + 3, z, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.blockState, x + 1, y + 3, z + 1, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH), x + 1, y + 3, z - 1, rotation, sbb
      );
      this.setBlockStateRotated(world, this.deco.accentState, x + 1, y + 3, z - 2, rotation, sbb);
      this.setBlockStateRotated(
         world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.WEST, false), x + 2, y + 3, z - 2, rotation, sbb
      );
      this.placeTreasureRotated(world, x + 1, y + 2, z + 1, this.getOrientation(), rotation, TFLootTables.DARKTOWER_CACHE, sbb);
   }

   protected void makePillarFrame(WorldGenLevel world, BoundingBox sbb, TFStructureDecorator myDeco, Rotation rotation, int x, int y, int z, boolean fenced) {
      this.makePillarFrame(world, sbb, myDeco, rotation, x, y, z, 3, 3, 3, fenced);
   }

   protected void makePillarFrame(
      WorldGenLevel world,
      BoundingBox sbb,
      TFStructureDecorator myDeco,
      Rotation rotation,
      int x,
      int y,
      int z,
      int width,
      int height,
      int length,
      boolean fenced
   ) {
      for (int dx = 0; dx < width; dx++) {
         for (int dz = 0; dz < length; dz++) {
            if ((dx % 3 == 0 || dx == width - 1) && (dz % 3 == 0 || dz == length - 1)) {
               for (int py = 1; py <= height; py++) {
                  this.setBlockStateRotated(world, myDeco.pillarState, x + dx, y + py, z + dz, rotation, sbb);
               }
            } else {
               if (dx == 0) {
                  BlockState southStairs = getStairState(this.deco.stairState, Direction.WEST, false);
                  this.setBlockStateRotated(world, southStairs, x + dx, y + 1, z + dz, rotation, sbb);
                  this.setBlockStateRotated(world, (BlockState)southStairs.setValue(StairBlock.HALF, Half.TOP), x + dx, y + height, z + dz, rotation, sbb);
               } else if (dx == width - 1) {
                  BlockState northStairs = getStairState(this.deco.stairState, Direction.EAST, false);
                  this.setBlockStateRotated(world, northStairs, x + dx, y + 1, z + dz, rotation, sbb);
                  this.setBlockStateRotated(world, (BlockState)northStairs.setValue(StairBlock.HALF, Half.TOP), x + dx, y + height, z + dz, rotation, sbb);
               } else if (dz == 0) {
                  BlockState westStairs = getStairState(this.deco.stairState, Direction.NORTH, false);
                  this.setBlockStateRotated(world, westStairs, x + dx, y + 1, z + dz, rotation, sbb);
                  this.setBlockStateRotated(world, (BlockState)westStairs.setValue(StairBlock.HALF, Half.TOP), x + dx, y + height, z + dz, rotation, sbb);
               } else if (dz == length - 1) {
                  BlockState eastStairs = getStairState(this.deco.stairState, Direction.SOUTH, false);
                  this.setBlockStateRotated(world, eastStairs, x + dx, y + 1, z + dz, rotation, sbb);
                  this.setBlockStateRotated(world, (BlockState)eastStairs.setValue(StairBlock.HALF, Half.TOP), x + dx, y + height, z + dz, rotation, sbb);
               }

               if (fenced && (dx == 0 || dx == width - 1 || dz == 0 || dz == length - 1)) {
                  for (int fy = 2; fy <= height - 1; fy++) {
                     this.setBlockStateRotated(world, myDeco.fenceState, x + dx, y + fy, z + dz, rotation, sbb);
                  }
               }
            }
         }
      }
   }

   protected void addStairsDown(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y, int sz, int spacing) {
      for (int i = 0; i < spacing; i++) {
         int sx = this.size - 3 - i;
         this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, false), sx, y - i, sz, rotation, sbb);
         this.setBlockStateRotated(world, this.deco.accentState, sx, y - 1 - i, sz, rotation, sbb);
         this.setBlockStateRotated(world, AIR, sx, y + 1 - i, sz, rotation, sbb);
         this.setBlockStateRotated(world, AIR, sx, y + 2 - i, sz, rotation, sbb);
         this.setBlockStateRotated(world, AIR, sx - 1, y + 2 - i, sz, rotation, sbb);
         this.setBlockStateRotated(world, AIR, sx, y + 3 - i, sz, rotation, sbb);
         this.setBlockStateRotated(world, AIR, sx - 1, y + 3 - i, sz, rotation, sbb);
      }
   }

   protected void addSmallTimberBeams(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int bottom, int top) {
      int spacing = 4;
      Rotation rotation = Rotation.NONE;
      if (bottom == 0) {
         bottom += spacing;
      }

      for (int y = bottom; y < top; y += spacing) {
         rotation = rotation.getRotated(Rotation.CLOCKWISE_90);
         if (y >= top - spacing && this.isDeadEnd()) {
            this.makeTimberFloor(world, sbb, rotation, y);
            TFStructureDecorator logDeco = new StructureDecoratorDarkTower();
            logDeco.pillarState = ((RotatedPillarBlock)TFBlocks.DARK_LOG.get()).defaultBlockState();
            logDeco.platformState = ((RotatedPillarBlock)TFBlocks.DARK_LOG.get()).defaultBlockState();
            this.decorateTreasureRoom(world, sbb, rotation, y, 4, logDeco);
         } else {
            this.makeSmallTimberBeams(world, rand, sbb, rotation, y, y == bottom && bottom != spacing);
         }
      }
   }

   protected void makeTimberFloor(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      BlockState beamID = ((RotatedPillarBlock)TFBlocks.DARK_LOG.get()).defaultBlockState();
      BlockState beamStateNS = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.Z);
      BlockState beamStateUD = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.Y);
      BlockState beamStateEW = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.X);

      for (int z = 1; z < this.size - 1; z++) {
         for (int x = 1; x < this.size - 1; x++) {
            if (x < z) {
               this.setBlockStateRotated(world, beamStateNS, x, y, z, rotation, sbb);
            } else {
               this.setBlockStateRotated(world, beamStateEW, x, y, z, rotation, sbb);
            }
         }
      }

      for (int by = 1; by < 4; by++) {
         BlockState ladder = Blocks.LADDER.defaultBlockState();
         this.setBlockStateRotated(world, beamStateUD, 2, y - by, 2, rotation, sbb);
         this.setBlockStateRotated(world, (BlockState)ladder.setValue(LadderBlock.FACING, Direction.WEST), 3, y - by, 2, rotation, sbb);
         this.setBlockStateRotated(world, beamStateUD, 6, y - by, 6, rotation, sbb);
         this.setBlockStateRotated(world, (BlockState)ladder.setValue(LadderBlock.FACING, Direction.EAST), 5, y - by, 6, rotation, sbb);
      }

      this.setBlockStateRotated(world, AIR, 3, y, 2, rotation, sbb);
      this.setBlockStateRotated(world, AIR, 5, y, 6, rotation, sbb);
   }

   protected void makeSmallTimberBeams(WorldGenLevel world, RandomSource rand, BoundingBox sbb, Rotation rotation, int y, boolean bottom) {
      BlockState beamID = ((RotatedPillarBlock)TFBlocks.DARK_LOG.get()).defaultBlockState();
      BlockState beamStateNS = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.X);
      BlockState beamStateUD = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.Y);
      BlockState beamStateEW = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.Z);

      for (int z = 1; z < this.size - 1; z++) {
         this.setBlockStateRotated(world, beamStateEW, 2, y, z, rotation, sbb);
         this.setBlockStateRotated(world, beamStateEW, 6, y, z, rotation, sbb);
      }

      int z = this.pickBetweenExcluding(3, this.size - 3, rand, 2, 2, 6);

      for (int x = 3; x < 6; x++) {
         this.setBlockStateRotated(world, beamStateNS, x, y, z, rotation, sbb);
      }

      int x1 = 2;
      int z1 = rand.nextBoolean() ? 2 : 6;
      int x3 = 6;
      int z3 = rand.nextBoolean() ? 2 : 6;

      for (int by = 1; by < 4; by++) {
         BlockState ladder = Blocks.LADDER.defaultBlockState();
         if (!bottom || this.checkPost(world, x1, y - 4, z1, rotation, sbb)) {
            this.setBlockStateRotated(world, beamStateUD, x1, y - by, z1, rotation, sbb);
            this.setBlockStateRotated(world, (BlockState)ladder.setValue(LadderBlock.FACING, Direction.WEST), x1 + 1, y - by, z1, rotation, sbb);
         }

         if (!bottom || this.checkPost(world, x3, y - 4, z3, rotation, sbb)) {
            this.setBlockStateRotated(world, beamStateUD, x3, y - by, z3, rotation, sbb);
            this.setBlockStateRotated(world, (BlockState)ladder.setValue(LadderBlock.FACING, Direction.EAST), x3 - 1, y - by, z3, rotation, sbb);
         }
      }
   }

   protected int pickBetweenExcluding(int low, int high, RandomSource rand, int k, int l, int m) {
      int result;
      do {
         result = rand.nextInt(high - low) + low;
      } while (result == k || result == l || result == m);

      return result;
   }

   protected int pickFrom(RandomSource rand, int i, int j, int k) {
      return switch (rand.nextInt(3)) {
         case 1 -> j;
         case 2 -> k;
         default -> i;
      };
   }

   protected boolean checkPost(WorldGenLevel world, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      int worldX = this.getXWithOffsetRotated(x, z, rotation);
      int worldY = this.getWorldY(y);
      int worldZ = this.getZWithOffsetRotated(x, z, rotation);
      BlockPos vec = new BlockPos(worldX, worldY, worldZ);
      if (!sbb.isInside(vec)) {
         return false;
      }

      BlockState blockState = world.getBlockState(vec);
      return blockState.getBlock() != Blocks.AIR && blockState != this.deco.accentState;
   }

   protected void makeEncasedWalls(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      for (int x = minX; x <= maxX; x++) {
         for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
               if (x == minX || x == maxX || y == minY || y == maxY || z == minZ || z == maxZ) {
                  if ((y != minY && y != maxY || x != minY && x != maxX && z != minZ && z != maxZ)
                     && (z != minZ && z != maxZ || x != minY && x != maxX && y != minY && y != maxY)) {
                     BlockSelector blocker = this.deco.randomBlocks;
                     blocker.next(rand, x, y, z, true);
                     this.placeBlock(world, blocker.getNext(), x, y, z, sbb);
                  } else {
                     this.placeBlock(world, this.deco.accentState, x, y, z, sbb);
                  }
               }
            }
         }
      }

      this.placeBlock(world, this.deco.accentState, minX + 1, minY + 1, minZ, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, minY + 1, maxZ, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, minY + 1, minZ, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, minY + 1, maxZ, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, maxY - 1, minZ, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, maxY - 1, maxZ, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, maxY - 1, minZ, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, maxY - 1, maxZ, sbb);
      this.placeBlock(world, this.deco.accentState, minX, minY + 1, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX, minY + 1, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX, minY + 1, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX, minY + 1, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX, maxY - 1, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX, maxY - 1, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX, maxY - 1, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX, maxY - 1, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, minY, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, minY, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, minY, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, minY, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, maxY, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, minX + 1, maxY, maxZ - 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, maxY, minZ + 1, sbb);
      this.placeBlock(world, this.deco.accentState, maxX - 1, maxY, maxZ - 1, sbb);
   }

   @Override
   public int[] getValidOpening(RandomSource rand, Rotation direction) {
      int verticalOffset = this.size == 19 ? 5 : 4;
      if (direction != Rotation.NONE && direction != Rotation.CLOCKWISE_180) {
         if (direction != Rotation.CLOCKWISE_90 && direction != Rotation.COUNTERCLOCKWISE_90) {
            return new int[]{0, 0, 0};
         }

         int rx = this.size / 2;
         int rz = direction == Rotation.CLOCKWISE_90 ? this.size - 1 : 0;
         int ry = this.height - verticalOffset;
         return new int[]{rx, ry, rz};
      } else {
         int rx = direction == Rotation.NONE ? this.size - 1 : 0;
         int rz = this.size / 2;
         int ry = this.height - verticalOffset;
         return new int[]{rx, ry, rz};
      }
   }

   @Override
   public void addOpening(int dx, int dy, int dz, Rotation direction) {
      this.addOpening(dx, dy, dz, direction, EnumDarkTowerDoor.VANISHING);
   }

   protected void addOpening(int dx, int dy, int dz, Rotation direction, EnumDarkTowerDoor type) {
      super.addOpening(dx, dy, dz, direction);
      this.openingTypes.add(this.openings.indexOf(new BlockPos(dx, dy, dz)), type);
   }

   @Override
   protected void makeOpenings(WorldGenLevel world, BoundingBox sbb) {
      for (int i = 0; i < this.openings.size(); i++) {
         BlockPos doorCoords = this.openings.get(i);
         EnumDarkTowerDoor doorType;
         if (this.openingTypes.size() > i) {
            doorType = this.openingTypes.get(i);
         } else {
            doorType = EnumDarkTowerDoor.VANISHING;
         }

         switch (doorType) {
            case REAPPEARING:
               this.makeReappearingDoorOpening(world, doorCoords.getX(), doorCoords.getY(), doorCoords.getZ(), sbb);
               break;
            case LOCKED:
               this.makeLockedDoorOpening(world, doorCoords.getX(), doorCoords.getY(), doorCoords.getZ(), sbb);
               break;
            default:
               this.makeDoorOpening(world, doorCoords.getX(), doorCoords.getY(), doorCoords.getZ(), sbb);
         }
      }
   }

   @Override
   protected void makeDoorOpening(WorldGenLevel world, int dx, int dy, int dz, BoundingBox sbb) {
      BlockState inactiveVanish = ((Block)TFBlocks.UNBREAKABLE_VANISHING_BLOCK.get()).defaultBlockState();
      if (dx == 0 || dx == this.size - 1) {
         this.generateBox(world, sbb, dx, dy - 1, dz - 2, dx, dy + 3, dz + 2, this.deco.accentState, AIR, false);
         this.generateBox(world, sbb, dx, dy, dz - 1, dx, dy + 2, dz + 1, inactiveVanish, AIR, false);
      }

      if (dz == 0 || dz == this.size - 1) {
         this.generateBox(world, sbb, dx - 2, dy - 1, dz, dx + 2, dy + 3, dz, this.deco.accentState, AIR, false);
         this.generateBox(world, sbb, dx - 1, dy, dz, dx + 1, dy + 2, dz, inactiveVanish, AIR, false);
      }
   }

   protected void makeReappearingDoorOpening(WorldGenLevel world, int dx, int dy, int dz, BoundingBox sbb) {
      BlockState inactiveReappearing = ((Block)TFBlocks.REAPPEARING_BLOCK.get()).defaultBlockState();
      if (dx == 0 || dx == this.size - 1) {
         this.generateBox(world, sbb, dx, dy - 1, dz - 2, dx, dy + 3, dz + 2, this.deco.accentState, AIR, false);
         this.generateBox(world, sbb, dx, dy, dz - 1, dx, dy + 2, dz + 1, inactiveReappearing, AIR, false);
      }

      if (dz == 0 || dz == this.size - 1) {
         this.generateBox(world, sbb, dx - 2, dy - 1, dz, dx + 2, dy + 3, dz, this.deco.accentState, AIR, false);
         this.generateBox(world, sbb, dx - 1, dy, dz, dx + 1, dy + 2, dz, inactiveReappearing, AIR, false);
      }
   }

   protected void makeLockedDoorOpening(WorldGenLevel world, int dx, int dy, int dz, BoundingBox sbb) {
      BlockState lockedVanish = ((Block)TFBlocks.LOCKED_VANISHING_BLOCK.get()).defaultBlockState();
      BlockState inactiveVanish = ((Block)TFBlocks.UNBREAKABLE_VANISHING_BLOCK.get()).defaultBlockState();
      if (dx == 0 || dx == this.size - 1) {
         this.generateBox(world, sbb, dx, dy - 1, dz - 2, dx, dy + 3, dz + 2, this.deco.accentState, AIR, false);
         this.generateBox(world, sbb, dx, dy, dz - 1, dx, dy + 2, dz + 1, inactiveVanish, AIR, false);
         this.placeBlock(world, lockedVanish, dx, dy, dz + 1, sbb);
         this.placeBlock(world, lockedVanish, dx, dy, dz - 1, sbb);
         this.placeBlock(world, lockedVanish, dx, dy + 2, dz + 1, sbb);
         this.placeBlock(world, lockedVanish, dx, dy + 2, dz - 1, sbb);
      }

      if (dz == 0 || dz == this.size - 1) {
         this.generateBox(world, sbb, dx - 2, dy - 1, dz, dx + 2, dy + 3, dz, this.deco.accentState, AIR, false);
         this.generateBox(world, sbb, dx - 1, dy, dz, dx + 1, dy + 2, dz, inactiveVanish, AIR, false);
         this.placeBlock(world, lockedVanish, dx + 1, dy, dz, sbb);
         this.placeBlock(world, lockedVanish, dx - 1, dy, dz, sbb);
         this.placeBlock(world, lockedVanish, dx + 1, dy + 2, dz, sbb);
         this.placeBlock(world, lockedVanish, dx - 1, dy + 2, dz, sbb);
      }
   }

   @Override
   public boolean isDeadEnd() {
      int nonBalconies = 0;

      for (EnumDarkTowerDoor type : this.openingTypes) {
         if (type != EnumDarkTowerDoor.REAPPEARING) {
            nonBalconies++;
         }
      }

      return nonBalconies <= 1;
   }

   public boolean isKeyTower() {
      return this.keyTower;
   }

   public void setKeyTower(boolean keyTower) {
      this.keyTower = keyTower;
   }
}
