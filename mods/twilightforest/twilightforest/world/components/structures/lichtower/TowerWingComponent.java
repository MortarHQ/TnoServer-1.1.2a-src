package twilightforest.world.components.structures.lichtower;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.RotationUtil;
import twilightforest.util.TFStructureHelper;
import twilightforest.util.entities.EntityUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class TowerWingComponent extends TFStructureComponentOld {
   public int size;
   protected int height;
   protected Class<? extends TowerRoofComponent> roofType;
   protected ArrayList<BlockPos> openings = new ArrayList<>();
   protected int highestOpening;
   protected boolean[] openingTowards = new boolean[]{false, false, true, false};

   public TowerWingComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFLTWin.get(), nbt);
   }

   public TowerWingComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.size = nbt.getInt("towerSize");
      this.height = nbt.getInt("towerHeight");
      this.readOpeningsFromArray(nbt.getIntArray("doorInts"));
      this.highestOpening = nbt.getInt("highestOpening");
      this.openingTowards[0] = nbt.getBoolean("openingTowards0");
      this.openingTowards[1] = nbt.getBoolean("openingTowards1");
      this.openingTowards[2] = nbt.getBoolean("openingTowards2");
      this.openingTowards[3] = nbt.getBoolean("openingTowards3");
   }

   protected TowerWingComponent(StructurePieceType type, int i, int x, int y, int z) {
      super(type, i, x, y, z);
      this.highestOpening = 0;
   }

   protected TowerWingComponent(StructurePieceType type, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super(type, i, x, y, z);
      this.size = pSize;
      this.height = pHeight;
      this.setOrientation(direction);
      this.highestOpening = 0;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, direction, false);
   }

   private int[] getDoorsAsIntArray() {
      IntBuffer ibuffer = IntBuffer.allocate(this.openings.size() * 3);

      for (BlockPos door : this.openings) {
         ibuffer.put(door.getX());
         ibuffer.put(door.getY());
         ibuffer.put(door.getZ());
      }

      return ibuffer.array();
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("towerSize", this.size);
      tagCompound.putInt("towerHeight", this.height);
      tagCompound.putIntArray("doorInts", this.getDoorsAsIntArray());
      tagCompound.putInt("highestOpening", this.highestOpening);
      tagCompound.putBoolean("openingTowards0", this.openingTowards[0]);
      tagCompound.putBoolean("openingTowards1", this.openingTowards[1]);
      tagCompound.putBoolean("openingTowards2", this.openingTowards[2]);
      tagCompound.putBoolean("openingTowards3", this.openingTowards[3]);
   }

   private void readOpeningsFromArray(int[] intArray) {
      for (int i = 0; i < intArray.length; i += 3) {
         BlockPos door = new BlockPos(intArray[i], intArray[i + 1], intArray[i + 2]);
         this.openings.add(door);
      }
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      this.makeARoof(parent, list, rand);
      this.makeABeard(parent, list, rand);
      if (this.size > 4) {
         for (Rotation towerRotation : RotationUtil.ROTATIONS) {
            if (towerRotation != Rotation.CLOCKWISE_180) {
               int[] dest = this.getValidOpening(rand, towerRotation);
               if (!this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], this.size - 2, this.height - 4, towerRotation)
                  && this.size > 8
                  && !this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], this.size - 4, this.height - 6, towerRotation)) {
                  this.makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], this.size - 6, this.height - 12, towerRotation);
               }
            }
         }
      }
   }

   public boolean makeTowerWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      if (wingHeight < 6) {
         return false;
      } else {
         Direction direction = this.getStructureRelativeRotation(rotation);
         int[] dx = this.offsetTowerCoords(x, y, z, wingSize, direction);
         if (rand.nextInt(6) == 0) {
            return this.makeBridge(list, rand, index, x, y, z, wingSize, wingHeight, rotation);
         } else {
            TowerWingComponent wing = new TowerWingComponent(
               (StructurePieceType)TFStructurePieceTypes.TFLTWin.get(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction
            );
            StructurePiece intersect = list.findCollisionPiece(wing.boundingBox);
            if (intersect == null || intersect == this) {
               list.addPiece(wing);
               wing.addChildren(this, list, rand);
               this.addOpening(x, y, z, rotation);
               return true;
            } else {
               return rand.nextInt(3) > 0 ? this.makeBridge(list, rand, index, x, y, z, wingSize, wingHeight, rotation) : false;
            }
         }
      }
   }

   protected boolean makeBridge(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 3, direction);
      if (wingSize == 3 && wingHeight > 10) {
         wingHeight = 6 + rand.nextInt(5);
      }

      TowerBridgeComponent bridge = new TowerBridgeComponent(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
      StructurePiece intersect = list.findCollisionPiece(bridge.boundingBox);
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

   public void addOpening(int dx, int dy, int dz, Rotation direction) {
      this.openingTowards[direction.ordinal()] = true;
      if (dy > this.highestOpening) {
         this.highestOpening = dy;
      }

      this.openings.add(new BlockPos(dx, dy, dz));
   }

   public void addOpening(int dx, int dy, int dz, Direction facing) {
      this.addOpening(dx, dy, dz, RotationUtil.getRelativeRotation(this.getOrientation(), facing));
   }

   public void makeABeard(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      boolean attached = parent.getBoundingBox().minY() < this.boundingBox.minY();
      int index = this.getGenDepth();
      TowerBeardComponent beard;
      if (attached) {
         beard = new TowerBeardAttachedComponent(
            index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
      } else {
         beard = new TowerBeardComponent(
            (StructurePieceType)TFStructurePieceTypes.TFLTBea.get(),
            index + 1,
            this,
            this.getLocatorPosition().getX(),
            this.getLocatorPosition().getY(),
            this.getLocatorPosition().getZ()
         );
      }

      list.addPiece(beard);
      beard.addChildren(this, list, rand);
   }

   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      boolean attached = parent.getBoundingBox().maxY() > this.boundingBox.maxY();
      if (attached) {
         this.makeAttachedRoof(list, rand);
      } else {
         this.makeFreestandingRoof(list, rand);
      }
   }

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

   protected void tryToFitRoof(StructurePieceAccessor list, RandomSource rand, TowerRoofComponent roof) {
      if (roof.fits(this, list)) {
         list.addPiece(roof);
         roof.addChildren(this, list, rand);
         this.roofType = (Class<? extends TowerRoofComponent>)roof.getClass();
      }
   }

   protected void makeFreestandingRoof(StructurePieceAccessor list, RandomSource rand) {
      int index = this.getGenDepth();
      if (this.roofType == null && rand.nextInt(8) != 0) {
         TowerRoofComponent roof = new TowerRoofPointyOverhangComponent(
            index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         this.tryToFitRoof(list, rand, roof);
      }

      if (this.roofType == null) {
         TowerRoofComponent roof = new TowerRoofStairsOverhangComponent(
            index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         this.tryToFitRoof(list, rand, roof);
      }

      if (this.roofType == null) {
         TowerRoofComponent roof = new TowerRoofStairsComponent(
            index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
         );
         this.tryToFitRoof(list, rand, roof);
      }

      if (this.roofType == null && rand.nextInt(53) != 0) {
         TowerRoofComponent roof = new TowerRoofSlabComponent(
            (StructurePieceType)TFStructurePieceTypes.TFLTRS.get(),
            index + 1,
            this,
            this.getLocatorPosition().getX(),
            this.getLocatorPosition().getY(),
            this.getLocatorPosition().getZ()
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

   public void postProcess(
      WorldGenLevel worldIn, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(worldIn, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, false, rand, TFStructureComponentOld.getStrongholdStones());
      this.generateAirBox(worldIn, sbb, 1, 1, 1, this.size - 2, this.height - 2, this.size - 2);
      if (this.highestOpening > 1) {
         this.makeStairs(worldIn, rand, sbb);
      }

      this.decorateThisTower(worldIn, generator, sbb);
      this.makeWindows(worldIn, sbb, this.size < 4);
      this.makeOpenings(worldIn, sbb);
   }

   protected void makeOpeningMarkers(WorldGenLevel world, RandomSource rand, int numMarkers, BoundingBox sbb) {
      if (this.size > 4) {
         BlockState woolWhite = Blocks.WHITE_WOOL.defaultBlockState();
         BlockState woolOrange = Blocks.ORANGE_WOOL.defaultBlockState();
         BlockState woolMagenta = Blocks.MAGENTA_WOOL.defaultBlockState();
         BlockState woolLightBlue = Blocks.LIGHT_BLUE_WOOL.defaultBlockState();

         for (int i = 0; i < numMarkers; i++) {
            int[] spot = this.getValidOpening(rand, Rotation.NONE);
            this.placeBlock(world, woolWhite, spot[0], spot[1], spot[2], sbb);
         }

         for (int i = 0; i < numMarkers; i++) {
            int[] spot = this.getValidOpening(rand, Rotation.CLOCKWISE_90);
            this.placeBlock(world, woolOrange, spot[0], spot[1], spot[2], sbb);
         }

         for (int i = 0; i < numMarkers; i++) {
            int[] spot = this.getValidOpening(rand, Rotation.CLOCKWISE_180);
            this.placeBlock(world, woolMagenta, spot[0], spot[1], spot[2], sbb);
         }

         for (int i = 0; i < numMarkers; i++) {
            int[] spot = this.getValidOpening(rand, Rotation.COUNTERCLOCKWISE_90);
            this.placeBlock(world, woolLightBlue, spot[0], spot[1], spot[2], sbb);
         }
      }
   }

   protected void decorateThisTower(WorldGenLevel world, ChunkGenerator generator, BoundingBox sbb) {
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L * this.boundingBox.minZ() * 756839L);
      if (this.size > 3) {
         if (this.isDeadEnd()) {
            this.decorateDeadEnd(world, decoRNG, sbb);
         } else {
            this.decorateStairTower(world, generator, decoRNG, sbb);
         }
      }
   }

   protected void decorateDeadEnd(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      BlockState birchPlanks = Blocks.BIRCH_PLANKS.defaultBlockState();
      int floors = (this.height - 1) / 5;
      int floorHeight = this.height / floors;

      for (int i = 1; i < floors; i++) {
         for (int x = 1; x < this.size - 1; x++) {
            for (int z = 1; z < this.size - 1; z++) {
               this.placeBlock(world, birchPlanks, x, i * floorHeight, z, sbb);
            }
         }
      }

      if (floors > 1) {
         Rotation ladderDir = Rotation.COUNTERCLOCKWISE_90;
         this.decorateFloor(world, rand, 0, 1, floorHeight, ladderDir, null, sbb);

         for (int i = 1; i < floors - 1; i++) {
            int bottom = 1 + floorHeight * i;
            int top = floorHeight * (i + 1);
            Rotation downLadderDir = ladderDir;
            ladderDir = ladderDir.getRotated(Rotation.CLOCKWISE_90);
            this.decorateFloor(world, rand, i, bottom, top, ladderDir, downLadderDir, sbb);
         }

         this.decorateFloor(world, rand, floors, 1 + floorHeight * (floors - 1), this.height - 1, null, ladderDir, sbb);
      } else {
         this.decorateFloor(world, rand, 0, 1, this.height - 1, null, null, sbb);
      }
   }

   protected void decorateFloor(
      WorldGenLevel world, RandomSource rand, int floor, int bottom, int top, @Nullable Rotation ladderUpDir, @Nullable Rotation ladderDownDir, BoundingBox sbb
   ) {
      BlockState ladder = Blocks.LADDER.defaultBlockState();
      if (ladderUpDir != null) {
         BlockState ladderUp = (BlockState)ladder.setValue(LadderBlock.FACING, ladderUpDir.rotate(Direction.EAST));
         int dx = this.getLadderX(ladderUpDir);
         int dz = this.getLadderZ(ladderUpDir);

         for (int dy = bottom; dy < top; dy++) {
            this.placeBlock(world, ladderUp, dx, dy, dz, sbb);
         }
      }

      if (ladderDownDir != null) {
         BlockState ladderDown = (BlockState)ladder.setValue(LadderBlock.FACING, ladderDownDir.rotate(Direction.EAST));
         int dx = this.getLadderX(ladderDownDir);
         int dz = this.getLadderZ(ladderDownDir);

         for (int dy = bottom - 1; dy < bottom + 2; dy++) {
            this.placeBlock(world, ladderDown, dx, dy, dz, sbb);
         }
      }

      if (rand.nextInt(7) == 0 && ladderDownDir == null) {
         this.decorateWell(world, rand, bottom, sbb);
      } else if (rand.nextInt(7) == 0 && ladderDownDir == null) {
         this.decorateSkeletonRoom(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
      } else if (rand.nextInt(6) == 0 && ladderDownDir == null) {
         this.decorateZombieRoom(world, rand, bottom, ladderUpDir, ladderDownDir, sbb);
      } else if (rand.nextInt(5) == 0 && ladderDownDir == null) {
         this.decorateCactusRoom(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
      } else if (rand.nextInt(4) == 0 && ladderDownDir != null) {
         this.decorateTreasureChest(world, bottom, top, sbb);
      } else if (rand.nextInt(5) == 0) {
         this.decorateSpiderWebs(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
      } else if (rand.nextInt(12) == 0 && ladderDownDir != null) {
         this.decorateSolidRock(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
      } else if (rand.nextInt(3) == 0) {
         this.decorateFullLibrary(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
      } else {
         this.decorateLibrary(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
      }
   }

   protected void decorateWell(WorldGenLevel world, RandomSource rand, int bottom, BoundingBox sbb) {
      int cx = this.size / 2;
      BlockState waterOrLava = rand.nextInt(4) == 0 ? Blocks.LAVA.defaultBlockState() : Blocks.WATER.defaultBlockState();
      if (this.size > 5) {
         BlockState stoneBricks = Blocks.STONE_BRICKS.defaultBlockState();
         BlockState stoneSlabs = TFStructureHelper.stoneSlab;
         this.placeBlock(world, stoneBricks, cx - 1, bottom, cx - 1, sbb);
         this.placeBlock(world, stoneSlabs, cx - 1, bottom + 1, cx - 1, sbb);
         this.placeBlock(world, stoneBricks, cx, bottom, cx - 1, sbb);
         this.placeBlock(world, stoneBricks, cx + 1, bottom, cx - 1, sbb);
         this.placeBlock(world, stoneSlabs, cx + 1, bottom + 1, cx - 1, sbb);
         this.placeBlock(world, stoneBricks, cx - 1, bottom, cx, sbb);
         this.placeBlock(world, waterOrLava, cx, bottom, cx, sbb);
         this.placeBlock(world, stoneBricks, cx + 1, bottom, cx, sbb);
         this.placeBlock(world, stoneBricks, cx - 1, bottom, cx + 1, sbb);
         this.placeBlock(world, stoneSlabs, cx - 1, bottom + 1, cx + 1, sbb);
         this.placeBlock(world, stoneBricks, cx, bottom, cx + 1, sbb);
         this.placeBlock(world, stoneBricks, cx + 1, bottom, cx + 1, sbb);
         this.placeBlock(world, stoneSlabs, cx + 1, bottom + 1, cx + 1, sbb);
      }

      this.placeBlock(world, waterOrLava, cx, bottom - 1, cx, sbb);
   }

   protected void decorateSkeletonRoom(
      WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb
   ) {
      this.setSpawner(world, this.size / 2, bottom + 2, this.size / 2, sbb, EntityType.SKELETON);
      ArrayList<BlockPos> chainList = new ArrayList<>();
      chainList.add(new BlockPos(this.size / 2, bottom + 2, this.size / 2));

      for (int i = 0; i < this.size + 2; i++) {
         BlockPos chain = new BlockPos(2 + rand.nextInt(this.size - 4), this.height - 2, 2 + rand.nextInt(this.size - 4));
         if (!this.chainCollides(chain, chainList)) {
            for (int dy = bottom; dy < top; dy++) {
               this.placeBlock(world, Blocks.IRON_BARS.defaultBlockState(), chain.getX(), dy, chain.getZ(), sbb);
            }

            chainList.add(chain);
         }
      }

      for (int dx = 1; dx <= this.size - 2; dx++) {
         for (int dz = 1; dz <= this.size - 2; dz++) {
            if ((dx == 1 || dx == this.size - 2 || dz == 1 || dz == this.size - 2)
               && !this.isWindowPos(dx, dz)
               && !this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
               this.placeBlock(world, Blocks.COBWEB.defaultBlockState(), dx, top - 1, dz, sbb);
            }
         }
      }
   }

   protected void decorateZombieRoom(WorldGenLevel world, RandomSource rand, int bottom, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb) {
      this.setSpawner(world, this.size / 2, bottom + 2, this.size / 2, sbb, EntityType.ZOMBIE);
      BlockState ironBars = Blocks.IRON_BARS.defaultBlockState();
      BlockState soulSand = Blocks.SOUL_SAND.defaultBlockState();
      BlockState brownMushroom = Blocks.BROWN_MUSHROOM.defaultBlockState();

      for (int dx = 1; dx <= this.size - 2; dx++) {
         for (int dz = 1; dz <= this.size - 2; dz++) {
            if (!this.isWindowPos(dx, dz) && !this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir) && rand.nextInt(5) == 0) {
               this.placeBlock(world, brownMushroom, dx, bottom, dz, sbb);
            }
         }
      }

      ArrayList<BlockPos> slabList = new ArrayList<>();
      slabList.add(new BlockPos(this.size / 2, bottom + 2, this.size / 2));

      for (int i = 0; i < this.size - 1; i++) {
         BlockPos slab = new BlockPos(2 + rand.nextInt(this.size - 4), this.height - 2, 2 + rand.nextInt(this.size - 4));
         if (!this.chainCollides(slab, slabList)) {
            this.placeBlock(world, ironBars, slab.getX(), bottom, slab.getZ(), sbb);
            this.placeBlock(world, TFStructureHelper.birchSlab, slab.getX(), bottom + 1, slab.getZ(), sbb);
            this.placeBlock(world, soulSand, slab.getX(), bottom + 2, slab.getZ(), sbb);
            slabList.add(slab);
         }
      }
   }

   protected void decorateCactusRoom(WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb) {
      for (int dx = 1; dx <= this.size - 2; dx++) {
         for (int dz = 1; dz <= this.size - 2; dz++) {
            this.placeBlock(world, Blocks.SAND.defaultBlockState(), dx, bottom - 1, dz, sbb);
            if (!this.isWindowPos(dx, dz) && !this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir) && rand.nextInt(4) == 0) {
               this.placeBlock(world, Blocks.DEAD_BUSH.defaultBlockState(), dx, bottom, dz, sbb);
            }
         }
      }

      ArrayList<BlockPos> cactusList = new ArrayList<>();
      cactusList.add(new BlockPos(this.size / 2, bottom + 2, this.size / 2));

      for (int i = 0; i < this.size + 12; i++) {
         BlockPos cactus = new BlockPos(2 + rand.nextInt(this.size - 4), this.height - 2, 2 + rand.nextInt(this.size - 4));
         if (!this.chainCollides(cactus, cactusList)) {
            for (int dy = bottom; dy < top; dy++) {
               this.placeBlock(world, Blocks.CACTUS.defaultBlockState(), cactus.getX(), dy, cactus.getZ(), sbb);
            }

            cactusList.add(cactus);
         }
      }
   }

   protected void decorateTreasureChest(WorldGenLevel world, int bottom, int top, BoundingBox sbb) {
      int cx = this.size / 2;
      BlockState stoneBrick = Blocks.STONE_BRICKS.defaultBlockState();
      BlockState stoneBrickStairs = Blocks.STONE_BRICK_STAIRS.defaultBlockState();
      BlockState topStoneBrickStairs = (BlockState)stoneBrickStairs.setValue(StairBlock.HALF, Half.TOP);
      this.placeBlock(world, stoneBrick, cx, bottom, cx, sbb);
      this.placeBlock(world, stoneBrick, cx, top - 1, cx, sbb);
      if (this.size < 6) {
         this.surroundBlockCardinalRotated(world, stoneBrickStairs, cx, bottom, cx, sbb);
         this.surroundBlockCardinalRotated(world, topStoneBrickStairs, cx, top - 1, cx, sbb);
      } else {
         this.surroundBlockCardinalRotated(world, stoneBrickStairs, cx, bottom, cx, sbb);
         this.surroundBlockCorners(world, stoneBrick, cx, bottom, cx, sbb);

         for (int cy = bottom + 1; cy < top - 1; cy++) {
            this.surroundBlockCorners(world, stoneBrick, cx, cy, cx, sbb);
         }

         this.surroundBlockCardinalRotated(world, topStoneBrickStairs, cx, top - 1, cx, sbb);
         this.surroundBlockCorners(world, stoneBrick, cx, top - 1, cx, sbb);
      }

      this.placeTreasureAtCurrentPosition(world, cx, bottom + 1, cx, TFLootTables.TOWER_ROOM, sbb);
   }

   protected void decorateSpiderWebs(WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb) {
      for (int dy = bottom; dy < top; dy++) {
         int chance = top - dy + 2;

         for (int dx = 1; dx <= this.size - 2; dx++) {
            for (int dz = 1; dz <= this.size - 2; dz++) {
               if (!this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir) && rand.nextInt(chance) == 0) {
                  this.placeBlock(world, Blocks.COBWEB.defaultBlockState(), dx, dy, dz, sbb);
               }
            }
         }
      }

      if (rand.nextInt(5) == 0) {
         EntityType<?> spiderName = switch (rand.nextInt(4)) {
            case 1 -> (EntityType)TFEntities.HEDGE_SPIDER.get();
            case 2 -> (EntityType)TFEntities.SWARM_SPIDER.get();
            case 3 -> EntityType.CAVE_SPIDER;
            default -> EntityType.SPIDER;
         };
         this.setSpawner(world, this.size / 2, bottom + 2, this.size / 2, sbb, spiderName);
      } else {
         this.decorateFurniture(world, rand, bottom, this.size - 2, sbb);
      }
   }

   protected void decorateFurniture(WorldGenLevel world, RandomSource rand, int bottom, int freeSpace, BoundingBox sbb) {
      if (rand.nextInt(3) > 0) {
         this.placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), this.size / 2, bottom, this.size / 2, sbb);
         this.placeBlock(world, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), this.size / 2, bottom + 1, this.size / 2, sbb);
      }

      BlockState spruceStairs = Blocks.SPRUCE_STAIRS.defaultBlockState();
      if (rand.nextInt(3) == 0 && freeSpace > 1) {
         this.placeBlock(world, (BlockState)spruceStairs.setValue(StairBlock.FACING, Direction.WEST), this.size / 2 + 1, bottom, this.size / 2, sbb);
      }

      if (rand.nextInt(3) == 0 && freeSpace > 1) {
         this.placeBlock(world, (BlockState)spruceStairs.setValue(StairBlock.FACING, Direction.NORTH), this.size / 2, bottom, this.size / 2 + 1, sbb);
      }

      if (rand.nextInt(3) == 0 && freeSpace > 1) {
         this.placeBlock(world, (BlockState)spruceStairs.setValue(StairBlock.FACING, Direction.EAST), this.size / 2 - 1, bottom, this.size / 2, sbb);
      }

      if (rand.nextInt(3) == 0 && freeSpace > 1) {
         this.placeBlock(world, (BlockState)spruceStairs.setValue(StairBlock.FACING, Direction.SOUTH), this.size / 2, bottom, this.size / 2 - 1, sbb);
      }
   }

   protected void decorateSolidRock(WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb) {
      for (int dy = bottom; dy < top; dy++) {
         for (int dx = 1; dx <= this.size - 2; dx++) {
            for (int dz = 1; dz <= this.size - 2; dz++) {
               if (!this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir) && rand.nextInt(9) != 0) {
                  this.placeBlock(world, Blocks.STONE.defaultBlockState(), dx, dy, dz, sbb);
               }
            }
         }
      }
   }

   protected void decorateLibrary(WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb) {
      for (int dx = 1; dx <= this.size - 2; dx++) {
         for (int dz = 1; dz <= this.size - 2; dz++) {
            for (int dy = bottom; dy < top - 1; dy++) {
               if ((dx == 1 || dx == this.size - 2 || dz == 1 || dz == this.size - 2)
                  && !this.isWindowPos(dx, dz)
                  && !this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
                  this.placeBlock(world, Blocks.BOOKSHELF.defaultBlockState(), dx, dy, dz, sbb);
               }
            }
         }
      }

      if (rand.nextInt(2) == 0 && this.size > 5) {
         this.decorateLibraryTreasure(world, rand, top, ladderUpDir, ladderDownDir, sbb);
      }

      if (rand.nextInt(2) == 0 && this.size > 5) {
         this.decorateFurniture(world, rand, bottom, this.size - 2, sbb);
      }
   }

   protected void decorateLibraryTreasure(WorldGenLevel world, RandomSource rand, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb) {
      switch (rand.nextInt(4)) {
         case 0:
         default:
            if (!this.isLadderPos(2, 1, ladderUpDir, ladderDownDir)) {
               this.placeTreasureAtCurrentPosition(world, 2, top - 2, 1, TFLootTables.TOWER_LIBRARY, sbb);
               break;
            }
         case 1:
            if (!this.isLadderPos(this.size - 2, 2, ladderUpDir, ladderDownDir)) {
               this.placeTreasureAtCurrentPosition(world, this.size - 2, top - 2, 2, TFLootTables.TOWER_LIBRARY, sbb);
               break;
            }
         case 2:
            if (!this.isLadderPos(this.size - 3, this.size - 2, ladderUpDir, ladderDownDir)) {
               this.placeTreasureAtCurrentPosition(world, this.size - 3, top - 2, this.size - 2, TFLootTables.TOWER_LIBRARY, sbb);
               break;
            }
         case 3:
            if (!this.isLadderPos(1, this.size - 3, ladderUpDir, ladderDownDir)) {
               this.placeTreasureAtCurrentPosition(world, 1, top - 2, this.size - 3, TFLootTables.TOWER_LIBRARY, sbb);
            }
      }
   }

   protected void decorateFullLibrary(
      WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb
   ) {
      for (int dx = 1; dx <= this.size - 2; dx++) {
         for (int dz = 1; dz <= this.size - 2; dz++) {
            for (int dy = bottom; dy < top; dy++) {
               if ((
                     dx % 2 != 0 && (dz >= dx && dz <= this.size - dx - 1 || dz >= this.size - dx - 1 && dz <= dx)
                        || dz % 2 != 0 && (dx >= dz && dx <= this.size - dz - 1 || dx >= this.size - dz - 1 && dx <= dz)
                  )
                  && !this.isWindowPos(dx, dy, dz)
                  && !this.isOpeningPos(dx, dy, dz)
                  && !this.isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
                  this.placeBlock(world, Blocks.BOOKSHELF.defaultBlockState(), dx, dy, dz, sbb);
               }
            }
         }
      }

      if (rand.nextInt(2) == 0 && this.size > 5) {
         this.decorateLibraryTreasure(world, rand, top, ladderUpDir, ladderDownDir, sbb);
      }
   }

   protected void decorateTrap(WorldGenLevel world, int bottom, int top, BoundingBox sbb) {
      for (int dx = 2; dx <= this.size - 3; dx++) {
         for (int dz = 2; dz <= this.size - 3; dz++) {
            if (dx == 2 || dx == this.size - 3 || dz == 2 || dz == this.size - 3) {
               this.placeBlock(world, Blocks.TNT.defaultBlockState(), dx, -1, dz, sbb);
            }
         }
      }

      for (int dy = bottom - 2; dy < top - 2; dy++) {
         this.placeBlock(world, Blocks.TNT.defaultBlockState(), 1, dy, 1, sbb);
         this.placeBlock(world, Blocks.TNT.defaultBlockState(), 1, dy, this.size - 2, sbb);
         this.placeBlock(world, Blocks.TNT.defaultBlockState(), this.size - 2, dy, 1, sbb);
         this.placeBlock(world, Blocks.TNT.defaultBlockState(), this.size - 2, dy, this.size - 2, sbb);
      }
   }

   protected boolean isWindowPos(int x, int z) {
      if (x == 1 && z == this.size / 2) {
         return true;
      } else if (x == this.size - 2 && z == this.size / 2) {
         return true;
      } else {
         return x == this.size / 2 && z == 1 ? true : x == this.size / 2 && z == this.size - 2;
      }
   }

   protected boolean isWindowPos(int x, int y, int z) {
      int checkYDir = -1;
      if (x == 1 && z == this.size / 2) {
         checkYDir = 2;
      } else if (x == this.size - 2 && z == this.size / 2) {
         checkYDir = 0;
      } else if (x == this.size / 2 && z == 1) {
         checkYDir = 3;
      } else if (x == this.size / 2 && z == this.size - 2) {
         checkYDir = 1;
      }

      return checkYDir <= -1
         ? false
         : !this.openingTowards[checkYDir] && (y == 2 || y == 3 || this.height > 8 && (y == this.height - 3 || y == this.height - 4));
   }

   protected boolean isOpeningPos(int x, int y, int z) {
      for (BlockPos door : this.openings) {
         MutableBlockPos inside = new MutableBlockPos(door.getX(), door.getY(), door.getZ());
         if (inside.getX() == 0) {
            inside.move(Direction.EAST);
         } else if (inside.getX() == this.size - 1) {
            inside.move(Direction.WEST);
         } else if (inside.getZ() == 0) {
            inside.move(Direction.SOUTH);
         } else if (inside.getZ() == this.size - 1) {
            inside.move(Direction.NORTH);
         }

         if (inside.getX() == x && inside.getZ() == z && (inside.getY() == y || inside.getY() + 1 == y)) {
            return true;
         }
      }

      return false;
   }

   protected boolean isLadderPos(int x, int z, Rotation ladderUpDir, Rotation ladderDownDir) {
      return ladderUpDir != null && x == this.getLadderX(ladderUpDir) && z == this.getLadderZ(ladderUpDir)
         ? true
         : ladderDownDir != null && x == this.getLadderX(ladderDownDir) && z == this.getLadderZ(ladderDownDir);
   }

   protected int getLadderX(Rotation ladderDir) {
      return switch (ladderDir) {
         case NONE -> this.size - 2;
         case CLOCKWISE_90 -> this.size / 2 + 1;
         case CLOCKWISE_180 -> 1;
         case COUNTERCLOCKWISE_90 -> this.size / 2 - 1;
         default -> throw new MatchException(null, null);
      };
   }

   protected int getLadderZ(Rotation ladderDir) {
      return switch (ladderDir) {
         case NONE -> this.size / 2 - 1;
         case CLOCKWISE_90 -> this.size - 2;
         case CLOCKWISE_180 -> this.size / 2 + 1;
         case COUNTERCLOCKWISE_90 -> 1;
         default -> throw new MatchException(null, null);
      };
   }

   protected void decorateStairTower(WorldGenLevel world, ChunkGenerator generator, RandomSource rand, BoundingBox sbb) {
      if (this.height - this.highestOpening > 8) {
         int base = this.highestOpening + 3;
         int floors = (this.height - base) / 5;
         int floorHeight = (this.height - base) / floors;

         for (int i = 0; i < floors; i++) {
            for (int x = 1; x < this.size - 1; x++) {
               for (int z = 1; z < this.size - 1; z++) {
                  this.placeBlock(world, TFStructureHelper.birchPlanks, x, i * floorHeight + base, z, sbb);
               }
            }
         }

         Rotation ladderDir = Rotation.NONE;
         int dx = this.getLadderX(ladderDir);
         int dz = this.getLadderZ(ladderDir);
         BlockState defaultState = (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, ladderDir.rotate(Direction.EAST));

         for (int dy = 1; dy < 3; dy++) {
            this.placeBlock(world, defaultState, dx, base - dy, dz, sbb);
         }

         for (int i = 0; i < floors - 1; i++) {
            int bottom = base + 1 + floorHeight * i;
            int top = base + floorHeight * (i + 1);
            Rotation downLadderDir = ladderDir;
            ladderDir = ladderDir.getRotated(Rotation.CLOCKWISE_90);
            this.decorateFloor(world, rand, i, bottom, top, ladderDir, downLadderDir, sbb);
         }

         this.decorateFloor(world, rand, floors, base + 1 + floorHeight * (floors - 1), this.height - 1, null, ladderDir, sbb);
         if (base > 8) {
            switch (rand.nextInt(4)) {
               case 0:
                  this.decorateChandelier(world, rand, base + 1, sbb);
                  break;
               case 1:
                  this.decorateHangingChains(world, rand, base + 1, sbb);
                  break;
               case 2:
                  this.decorateFloatingBooks(world, rand, base + 1, sbb);
                  break;
               case 3:
                  this.decorateFloatingVines(world, rand, base + 1, sbb);
            }
         }
      } else if (this.size > 5) {
         switch (rand.nextInt(4)) {
            case 0:
               this.decorateChandelier(world, rand, this.height, sbb);
               break;
            case 1:
               this.decorateHangingChains(world, rand, this.height, sbb);
               break;
            case 2:
               this.decorateFloatingBooks(world, rand, this.height, sbb);
               break;
            case 3:
               this.decorateFloatingVines(world, rand, this.height, sbb);
         }
      } else if (this.size > 3) {
         switch (rand.nextInt(3)) {
            case 0:
               this.decorateHangingChains(world, rand, this.height, sbb);
               break;
            case 1:
               this.decorateFloatingBooks(world, rand, this.height, sbb);
               break;
            case 2:
               this.decorateFloatingVines(world, rand, this.height, sbb);
         }
      }

      this.decorateStairFloor(world, generator, rand, sbb);
   }

   protected void decorateStairFloor(WorldGenLevel world, ChunkGenerator generator, RandomSource rand, BoundingBox sbb) {
      if (this.size > 5) {
         if (rand.nextInt(3) == 0) {
            this.decorateStairWell(world, rand, sbb);
         } else if (rand.nextInt(3) > 0 || this.size >= 15) {
            this.decoratePlanter(world, generator, rand, sbb);
         }
      }
   }

   protected void decorateChandelier(WorldGenLevel world, RandomSource rand, int decoTop, BoundingBox sbb) {
      if (decoTop >= 8 && this.size >= 8) {
         int cx = this.size / 2;
         int cy = decoTop - rand.nextInt(decoTop - 7) - 2;
         int cz = this.size / 2;
         BlockState oakFence = Blocks.OAK_FENCE.defaultBlockState();
         this.surroundBlockCardinal(world, oakFence, cx, cy, cz, sbb);
         this.surroundBlockCardinal(world, oakFence, cx, cy + 1, cz, sbb);

         for (int y = cy; y < decoTop - 1; y++) {
            this.placeBlock(world, oakFence, cx, y, cz, sbb);
         }
      }
   }

   protected void decorateHangingChains(WorldGenLevel world, RandomSource rand, int decoTop, BoundingBox sbb) {
      ArrayList<BlockPos> chainList = new ArrayList<>();

      for (int i = 0; i < this.size + 2; i++) {
         int filled = this.size < 15 ? 2 : 4;
         BlockPos chain = new BlockPos(filled + rand.nextInt(this.size - filled * 2), decoTop - 2, filled + rand.nextInt(this.size - filled * 2));
         if (!this.chainCollides(chain, chainList)) {
            int length = 1 + rand.nextInt(decoTop - 7);
            this.decorateOneChain(world, rand, chain.getX(), decoTop, length, chain.getZ(), sbb);
            chainList.add(chain);
         }
      }
   }

   protected boolean chainCollides(BlockPos coords, List<BlockPos> list) {
      for (BlockPos existing : list) {
         if (coords.getZ() == existing.getZ() && Math.abs(coords.getX() - existing.getX()) <= 1) {
            return true;
         }

         if (coords.getX() == existing.getX() && Math.abs(coords.getZ() - existing.getZ()) <= 1) {
            return true;
         }
      }

      return false;
   }

   protected void decorateOneChain(WorldGenLevel world, RandomSource rand, int dx, int decoTop, int length, int dz, BoundingBox sbb) {
      for (int y = 1; y <= length; y++) {
         this.placeBlock(world, Blocks.IRON_BARS.defaultBlockState(), dx, decoTop - y - 1, dz, sbb);
      }
      BlockState ballBlock = switch (rand.nextInt(10)) {
         case 0 -> Blocks.IRON_BLOCK.defaultBlockState();
         case 1 -> Blocks.BOOKSHELF.defaultBlockState();
         case 2 -> Blocks.NETHERRACK.defaultBlockState();
         case 3 -> Blocks.SOUL_SAND.defaultBlockState();
         case 4 -> Blocks.GLASS.defaultBlockState();
         case 5 -> Blocks.LAPIS_BLOCK.defaultBlockState();
         case 6 -> Blocks.INFESTED_STONE_BRICKS.defaultBlockState();
         default -> Blocks.GLOWSTONE.defaultBlockState();
      };
      this.placeBlock(world, ballBlock, dx, decoTop - length - 2, dz, sbb);
   }

   protected void decorateFloatingBooks(WorldGenLevel world, RandomSource rand, int decoTop, BoundingBox sbb) {
      ArrayList<BlockPos> shelfList = new ArrayList<>();

      for (int i = 0; i < this.size + 2; i++) {
         int filled = this.size < 15 ? 2 : 4;
         BlockPos shelf = new BlockPos(filled + rand.nextInt(this.size - filled * 2), decoTop - 2, filled + rand.nextInt(this.size - filled * 2));
         if (!this.chainCollides(shelf, shelfList)) {
            int bottom = 2 + rand.nextInt(decoTop - 7);
            int top = rand.nextInt(bottom - 1) + 2;

            for (int y = top; y <= bottom; y++) {
               this.placeBlock(world, Blocks.BOOKSHELF.defaultBlockState(), shelf.getX(), decoTop - y, shelf.getZ(), sbb);
            }

            shelfList.add(shelf);
         }
      }
   }

   protected void decorateFloatingVines(WorldGenLevel world, RandomSource rand, int decoTop, BoundingBox sbb) {
      BlockState mossyCobbleStone = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
      BlockState vine = Blocks.VINE.defaultBlockState();
      BlockState vineNorth = (BlockState)vine.setValue(VineBlock.NORTH, true);
      BlockState vineSouth = (BlockState)vine.setValue(VineBlock.SOUTH, true);
      BlockState vineEast = (BlockState)vine.setValue(VineBlock.EAST, true);
      BlockState vineWest = (BlockState)vine.setValue(VineBlock.WEST, true);
      ArrayList<BlockPos> mossList = new ArrayList<>();

      for (int i = 0; i < this.size + 2; i++) {
         int filled = this.size < 15 ? 2 : 4;
         BlockPos moss = new BlockPos(filled + rand.nextInt(this.size - filled * 2), decoTop - 2, filled + rand.nextInt(this.size - filled * 2));
         if (!this.chainCollides(moss, mossList)) {
            int bottom = 2 + rand.nextInt(decoTop - 7);
            int top = rand.nextInt(bottom - 1) + 2;

            for (int y = top; y <= bottom; y++) {
               this.placeBlock(world, mossyCobbleStone, moss.getX(), decoTop - y, moss.getZ(), sbb);
               this.placeBlock(world, vineEast, moss.getX() + 1, decoTop - y, moss.getZ(), sbb);
               this.placeBlock(world, vineWest, moss.getX() - 1, decoTop - y, moss.getZ(), sbb);
               this.placeBlock(world, vineSouth, moss.getX(), decoTop - y, moss.getZ() + 1, sbb);
               this.placeBlock(world, vineNorth, moss.getX(), decoTop - y, moss.getZ() - 1, sbb);
            }

            mossList.add(moss);
         }
      }

      for (int y = this.highestOpening + 3; y < decoTop - 1; y++) {
         for (int x = 1; x < this.size - 1; x++) {
            if (rand.nextInt(3) == 0) {
               this.placeBlock(world, vineSouth, x, y, 1, sbb);
            }

            if (rand.nextInt(3) == 0) {
               this.placeBlock(world, vineNorth, x, y, this.size - 2, sbb);
            }
         }

         for (int z = 1; z < this.size - 1; z++) {
            if (rand.nextInt(3) == 0) {
               this.placeBlock(world, vineEast, 1, y, z, sbb);
            }

            if (rand.nextInt(3) == 0) {
               this.placeBlock(world, vineWest, this.size - 2, y, z, sbb);
            }
         }
      }
   }

   protected void decoratePlanter(WorldGenLevel world, ChunkGenerator generator, RandomSource rand, BoundingBox sbb) {
      int cx = this.size / 2;
      this.surroundBlockCardinal(world, TFStructureHelper.stoneSlab, cx, 1, cx, sbb);
      if (this.size > 7) {
         this.surroundBlockCorners(world, TFStructureHelper.stoneSlabDouble, cx, 1, cx, sbb);
      }

      this.placeBlock(world, Blocks.GRASS_BLOCK.defaultBlockState(), cx, 1, cx, sbb);
      int i = rand.nextInt(6);
      BlockState plant = TFStructureHelper.randomPlant(i);
      BlockPos pos = this.getBlockPosWithOffset(cx, 2, cx);
      if (i > 4
         && !((ConfiguredFeature)world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(TFStructureHelper.randomTree(rand.nextInt(4))))
            .place(world, generator, world.getRandom(), pos)) {
         this.placeBlock(world, plant, cx, 2, cx, sbb);
      } else {
         this.placeBlock(world, plant, cx, 2, cx, sbb);
      }
   }

   protected void decorateStairWell(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      int cx = this.size / 2;
      int cy = 1;
      BlockState waterOrLava = rand.nextInt(4) == 0 ? Blocks.LAVA.defaultBlockState() : Blocks.WATER.defaultBlockState();
      BlockState stoneSlab = Blocks.SMOOTH_STONE_SLAB.defaultBlockState();
      BlockState stoneBrick = Blocks.STONE_BRICKS.defaultBlockState();
      if (this.size > 7) {
         this.placeBlock(world, stoneBrick, cx - 1, cy, cx - 1, sbb);
         this.placeBlock(world, stoneSlab, cx - 1, cy + 1, cx - 1, sbb);
         this.placeBlock(world, stoneBrick, cx, cy, cx - 1, sbb);
         this.placeBlock(world, stoneBrick, cx + 1, cy, cx - 1, sbb);
         this.placeBlock(world, stoneSlab, cx + 1, cy + 1, cx - 1, sbb);
         this.placeBlock(world, stoneBrick, cx - 1, cy, cx, sbb);
         this.placeBlock(world, waterOrLava, cx, cy, cx, sbb);
         this.placeBlock(world, stoneBrick, cx + 1, cy, cx, sbb);
         this.placeBlock(world, stoneBrick, cx - 1, cy, cx + 1, sbb);
         this.placeBlock(world, stoneSlab, cx - 1, cy + 1, cx + 1, sbb);
         this.placeBlock(world, stoneBrick, cx, cy, cx + 1, sbb);
         this.placeBlock(world, stoneBrick, cx + 1, cy, cx + 1, sbb);
         this.placeBlock(world, stoneSlab, cx + 1, cy + 1, cx + 1, sbb);
      }

      this.placeBlock(world, waterOrLava, cx, 0, cx, sbb);
   }

   public boolean isDeadEnd() {
      return this.openings.size() == 1;
   }

   protected void makeOpenings(WorldGenLevel world, BoundingBox sbb) {
      for (BlockPos door : this.openings) {
         this.makeDoorOpening(world, door.getX(), door.getY(), door.getZ(), sbb);
      }
   }

   protected void makeDoorOpening(WorldGenLevel world, int dx, int dy, int dz, BoundingBox sbb) {
      this.placeBlock(world, AIR, dx, dy, dz, sbb);
      this.placeBlock(world, AIR, dx, dy + 1, dz, sbb);
      if (this.getBlock(world, dx, dy + 2, dz, sbb).getBlock() != Blocks.AIR) {
         BlockState state = TFStructureHelper.stoneSlabDouble;
         this.placeBlock(world, state, dx, dy + 2, dz, sbb);
      }
   }

   public int[] getValidOpening(RandomSource rand, Rotation direction) {
      int wLength = this.size - 2;
      int offset = 1;
      if (this.size == 15) {
         wLength = 11;
         offset = 2;
      }

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

   protected int getYByStairs(int rx, RandomSource rand, Rotation direction) {
      int rise = 1;
      int base = 0;
      if (this.size == 15) {
         rise = 10;
         base = direction != Rotation.NONE && direction != Rotation.CLOCKWISE_180 ? 28 : 23;
      }

      if (this.size == 9) {
         rise = 6;
         base = direction != Rotation.NONE && direction != Rotation.CLOCKWISE_180 ? 5 : 2;
      }

      if (this.size == 7) {
         rise = 4;
         base = direction != Rotation.NONE && direction != Rotation.CLOCKWISE_180 ? 4 : 2;
      }

      if (this.size == 5) {
         rise = 4;

         base = switch (direction) {
            case NONE -> 3;
            case CLOCKWISE_90 -> 2;
            case CLOCKWISE_180 -> 5;
            case COUNTERCLOCKWISE_90 -> 4;
            default -> throw new MatchException(null, null);
         };
      }

      int flights = (this.height - 6 - base) / rise + 1;
      if (base > 0 && flights > 0) {
         int flightChosen = rand.nextInt(flights);
         int dy = flightChosen * rise + base;
         if (this.size == 15) {
            dy -= direction != Rotation.NONE && direction != Rotation.COUNTERCLOCKWISE_90 ? (this.size - rx - 3) / 2 : (rx - 2) / 2;
         } else {
            dy -= direction != Rotation.NONE && direction != Rotation.COUNTERCLOCKWISE_90 ? (this.size - rx - 2) / 2 : (rx - 1) / 2;
         }

         if (dy < 1) {
            dy = 1;
         }

         return dy;
      } else {
         return 0;
      }
   }

   protected void makeWindows(WorldGenLevel world, BoundingBox sbb, boolean real) {
      for (Rotation rotation : RotationUtil.ROTATIONS) {
         boolean realWindows = real && !this.openingTowards[rotation.ordinal()];
         this.makeWindowBlock(world, this.size - 1, 2, this.size / 2, rotation, sbb, realWindows);
         this.makeWindowBlock(world, this.size - 1, 3, this.size / 2, rotation, sbb, realWindows);
         this.makeWindowBase(world, this.size - 1, 1, this.size / 2, rotation, sbb);
         if (this.height > 8) {
            this.makeWindowBlock(world, this.size - 1, this.height - 3, this.size / 2, rotation, sbb, realWindows);
            this.makeWindowBlock(world, this.size - 1, this.height - 4, this.size / 2, rotation, sbb, realWindows);
            this.makeWindowBase(world, this.size - 1, this.height - 5, this.size / 2, rotation, sbb);
         }
      }
   }

   protected void makeWindowBlock(WorldGenLevel world, int x, int y, int z, Rotation rotation, BoundingBox sbb, boolean realWindows) {
      Direction temp = this.getOrientation();
      this.setOrientation(rotation.rotate(temp));
      Block outside = this.getBlock(world, x + 1, y, z, sbb).getBlock();
      Block inside = this.getBlock(world, x - 1, y, z, sbb).getBlock();
      if (realWindows && inside == Blocks.AIR && outside == Blocks.AIR) {
         this.placeBlock(world, Blocks.GLASS_PANE.defaultBlockState(), x, y, z, sbb);
      } else {
         this.placeBlock(world, Blocks.COBBLESTONE.defaultBlockState(), x, y, z, sbb);
      }

      this.setOrientation(temp);
   }

   protected void makeWindowBase(WorldGenLevel world, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      Direction temp = this.getOrientation();
      this.setOrientation(rotation.rotate(temp));
      BlockState state = TFStructureHelper.stoneSlabDouble;
      this.placeBlock(world, state, x, y, z, sbb);
      this.setOrientation(temp);
   }

   protected boolean makeStairs(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      if (this.size == 15) {
         return this.makeStairs15(world, rand, sbb);
      } else if (this.size == 9) {
         return this.makeStairs9(world, rand, sbb);
      } else if (this.size == 7) {
         return this.makeStairs7(world, rand, sbb);
      } else {
         return this.size == 5 ? this.makeStairs5(world, rand, sbb) : false;
      }
   }

   protected boolean makeStairs5(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      int rise = 1;
      int numFlights = this.highestOpening / rise;

      for (int i = 0; i < numFlights; i++) {
         this.makeStairs5flight(world, sbb, i * rise, this.getRotation(Rotation.NONE, i * 3), true);
      }

      return true;
   }

   protected void makeStairs5flight(WorldGenLevel world, BoundingBox sbb, int height, Rotation rotation, boolean useBirchWood) {
      Direction temp = this.getOrientation();
      this.setOrientation(rotation.rotate(temp));
      BlockState bottomSlab = useBirchWood ? TFStructureHelper.birchSlab : TFStructureHelper.stoneSlab;
      BlockState topSlab = useBirchWood ? TFStructureHelper.birchSlabTop : TFStructureHelper.stoneSlabTop;
      this.placeBlock(world, bottomSlab, 2, 1 + height, 3, sbb);
      this.placeBlock(world, topSlab, 3, 1 + height, 3, sbb);
      this.setOrientation(temp);
   }

   protected boolean makeStairs7(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      this.placeBlock(world, TFStructureHelper.birchSlab, 1, 1, 4, sbb);
      this.placeBlock(world, TFStructureHelper.birchSlabTop, 1, 1, 5, sbb);
      this.placeBlock(world, TFStructureHelper.stoneSlab, 5, 1, 2, sbb);
      this.placeBlock(world, TFStructureHelper.stoneSlabTop, 5, 1, 1, sbb);
      int rise = 2;
      int numFlights = this.highestOpening / rise;

      for (int i = 0; i < numFlights; i++) {
         this.makeStairs7flight(world, sbb, 1 + i * rise, this.getRotation(Rotation.NONE, i * 3), true);
         this.makeStairs7flight(world, sbb, 1 + i * rise, this.getRotation(Rotation.CLOCKWISE_180, i * 3), false);
      }

      return true;
   }

   protected void makeStairs7flight(WorldGenLevel world, BoundingBox sbb, int height, Rotation rotation, boolean useBirchWood) {
      Direction temp = this.getOrientation();
      this.setOrientation(rotation.rotate(temp));
      BlockState slabBottom = useBirchWood ? TFStructureHelper.birchSlab : TFStructureHelper.stoneSlab;
      BlockState slabTop = useBirchWood ? TFStructureHelper.birchSlabTop : TFStructureHelper.stoneSlabTop;
      this.placeBlock(world, slabBottom, 2, 1 + height, 5, sbb);
      this.placeBlock(world, slabTop, 3, 1 + height, 5, sbb);
      this.placeBlock(world, slabBottom, 4, 2 + height, 5, sbb);
      this.placeBlock(world, slabTop, 5, 2 + height, 5, sbb);
      this.setOrientation(temp);
   }

   protected boolean makeStairs9(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      this.placeBlock(world, TFStructureHelper.birchSlab, 1, 1, 6, sbb);
      this.placeBlock(world, TFStructureHelper.birchSlabTop, 1, 1, 7, sbb);
      this.placeBlock(world, TFStructureHelper.stoneSlab, 7, 1, 2, sbb);
      this.placeBlock(world, TFStructureHelper.stoneSlabTop, 7, 1, 1, sbb);
      int rise = 3;
      int numFlights = this.highestOpening / rise;

      for (int i = 0; i < numFlights; i++) {
         this.makeStairs9flight(world, sbb, 1 + i * rise, this.getRotation(Rotation.NONE, i * 3), true);
         this.makeStairs9flight(world, sbb, 1 + i * rise, this.getRotation(Rotation.CLOCKWISE_180, i * 3), false);
      }

      return true;
   }

   protected void makeStairs9flight(WorldGenLevel world, BoundingBox sbb, int height, Rotation rotation, boolean useBirchWood) {
      Direction temp = this.getOrientation();
      this.setOrientation(rotation.rotate(temp));
      BlockState slabBot = useBirchWood ? TFStructureHelper.birchSlab : TFStructureHelper.stoneSlab;
      BlockState slabTop = useBirchWood ? TFStructureHelper.birchSlabTop : TFStructureHelper.stoneSlabTop;
      this.placeBlock(world, slabBot, 2, 1 + height, 7, sbb);
      this.placeBlock(world, slabTop, 3, 1 + height, 7, sbb);
      this.placeBlock(world, slabBot, 4, 2 + height, 7, sbb);
      this.placeBlock(world, slabTop, 5, 2 + height, 7, sbb);
      this.placeBlock(world, slabBot, 6, 3 + height, 7, sbb);
      this.placeBlock(world, slabTop, 7, 3 + height, 7, sbb);
      this.setOrientation(temp);
   }

   protected boolean makeStairs15(WorldGenLevel world, RandomSource rand, BoundingBox sbb) {
      BlockState planks = Blocks.BIRCH_PLANKS.defaultBlockState();
      BlockState oakFence = Blocks.OAK_FENCE.defaultBlockState();
      BlockState birchSlab = TFStructureHelper.birchSlab;
      BlockState stoneSlab = TFStructureHelper.stoneSlab;
      BlockState doubleStoneSlab = TFStructureHelper.stoneSlabDouble;
      this.placeBlock(world, birchSlab, 1, 1, 9, sbb);
      this.placeBlock(world, birchSlab, 2, 1, 9, sbb);
      this.placeBlock(world, planks, 1, 1, 10, sbb);
      this.placeBlock(world, planks, 2, 1, 10, sbb);
      this.placeBlock(world, birchSlab, 1, 2, 11, sbb);
      this.placeBlock(world, birchSlab, 2, 2, 11, sbb);
      this.placeBlock(world, planks, 1, 2, 12, sbb);
      this.placeBlock(world, planks, 2, 2, 12, sbb);
      this.placeBlock(world, planks, 1, 2, 13, sbb);
      this.placeBlock(world, planks, 2, 2, 13, sbb);
      this.placeBlock(world, planks, 3, 2, 11, sbb);
      this.placeBlock(world, oakFence, 3, 3, 11, sbb);
      this.placeBlock(world, oakFence, 3, 4, 11, sbb);
      this.placeBlock(world, planks, 3, 1, 10, sbb);
      this.placeBlock(world, oakFence, 3, 2, 10, sbb);
      this.placeBlock(world, oakFence, 3, 3, 10, sbb);
      this.placeBlock(world, planks, 3, 1, 9, sbb);
      this.placeBlock(world, oakFence, 3, 2, 9, sbb);
      this.placeBlock(world, stoneSlab, 13, 1, 5, sbb);
      this.placeBlock(world, stoneSlab, 12, 1, 5, sbb);
      this.placeBlock(world, doubleStoneSlab, 13, 1, 4, sbb);
      this.placeBlock(world, doubleStoneSlab, 12, 1, 4, sbb);
      this.placeBlock(world, stoneSlab, 13, 2, 3, sbb);
      this.placeBlock(world, stoneSlab, 12, 2, 3, sbb);
      this.placeBlock(world, doubleStoneSlab, 13, 2, 2, sbb);
      this.placeBlock(world, doubleStoneSlab, 12, 2, 2, sbb);
      this.placeBlock(world, doubleStoneSlab, 13, 2, 1, sbb);
      this.placeBlock(world, doubleStoneSlab, 12, 2, 1, sbb);
      this.placeBlock(world, doubleStoneSlab, 11, 2, 3, sbb);
      this.placeBlock(world, oakFence, 11, 3, 3, sbb);
      this.placeBlock(world, oakFence, 11, 4, 3, sbb);
      this.placeBlock(world, doubleStoneSlab, 11, 1, 4, sbb);
      this.placeBlock(world, oakFence, 11, 2, 4, sbb);
      this.placeBlock(world, oakFence, 11, 3, 4, sbb);
      this.placeBlock(world, doubleStoneSlab, 11, 1, 5, sbb);
      this.placeBlock(world, oakFence, 11, 2, 5, sbb);
      int rise = 5;
      int numFlights = this.highestOpening / rise;

      for (int i = 0; i < numFlights; i++) {
         this.makeStairs15flight(world, rand, sbb, 2 + i * rise, this.getRotation(Rotation.NONE, i * 3), true);
         this.makeStairs15flight(world, rand, sbb, 2 + i * rise, this.getRotation(Rotation.CLOCKWISE_180, i * 3), false);
      }

      return true;
   }

   private Rotation getRotation(Rotation startRotation, int rotations) {
      int totalIncrements = startRotation.ordinal() + rotations;
      return RotationUtil.ROTATIONS[totalIncrements & 3];
   }

   protected void makeStairs15flight(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int height, Rotation rotation, boolean useBirchWood) {
      Direction temp = this.getOrientation();
      this.setOrientation(rotation.rotate(temp));
      BlockState oakFence = Blocks.OAK_FENCE.defaultBlockState();
      BlockState slabBot = useBirchWood ? TFStructureHelper.birchSlab : TFStructureHelper.stoneSlab;
      BlockState slabTop = useBirchWood ? TFStructureHelper.birchSlabTop : TFStructureHelper.stoneSlabTop;
      BlockState slabDoub = useBirchWood ? TFStructureHelper.birchPlanks : TFStructureHelper.stoneSlabDouble;
      this.placeBlock(world, slabBot, 3, 1 + height, 13, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 4, 1 + height, 13, slabTop);
      this.placeBlock(world, slabBot, 5, 2 + height, 13, sbb);
      this.placeBlock(world, slabTop, 6, 2 + height, 13, sbb);
      this.placeBlock(world, slabBot, 7, 3 + height, 13, sbb);
      this.placeBlock(world, slabTop, 8, 3 + height, 13, sbb);
      this.placeBlock(world, slabBot, 9, 4 + height, 13, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 10, 4 + height, 13, slabTop);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 11, 5 + height, 13, slabBot);
      this.placeBlock(world, slabTop, 12, 5 + height, 13, sbb);
      this.placeBlock(world, slabTop, 13, 5 + height, 13, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 3, 1 + height, 12, slabBot);
      this.placeBlock(world, slabTop, 4, 1 + height, 12, sbb);
      this.placeBlock(world, slabBot, 5, 2 + height, 12, sbb);
      this.placeBlock(world, slabTop, 6, 2 + height, 12, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 7, 3 + height, 12, slabBot);
      this.placeBlock(world, slabTop, 8, 3 + height, 12, sbb);
      this.placeBlock(world, slabBot, 9, 4 + height, 12, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 10, 4 + height, 12, slabTop);
      this.placeBlock(world, slabBot, 11, 5 + height, 12, sbb);
      this.placeBlock(world, slabTop, 12, 5 + height, 12, sbb);
      this.placeBlock(world, slabTop, 13, 5 + height, 12, sbb);
      this.placeBlock(world, slabDoub, 4, 1 + height, 11, sbb);
      this.placeBlock(world, slabDoub, 5, 2 + height, 11, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 6, 2 + height, 11, slabTop);
      this.placeBlock(world, slabDoub, 7, 3 + height, 11, sbb);
      this.maybeGenerateBlock(world, sbb, rand, 0.9F, 8, 3 + height, 11, slabTop);
      this.placeBlock(world, slabDoub, 9, 4 + height, 11, sbb);
      this.placeBlock(world, slabTop, 10, 4 + height, 11, sbb);
      this.placeBlock(world, slabDoub, 11, 5 + height, 11, sbb);
      this.placeBlock(world, oakFence, 4, 2 + height, 11, sbb);
      this.placeBlock(world, oakFence, 5, 3 + height, 11, sbb);
      this.placeBlock(world, oakFence, 6, 3 + height, 11, sbb);
      this.placeBlock(world, oakFence, 7, 4 + height, 11, sbb);
      this.placeBlock(world, oakFence, 8, 4 + height, 11, sbb);
      this.placeBlock(world, oakFence, 9, 5 + height, 11, sbb);
      this.placeBlock(world, oakFence, 10, 5 + height, 11, sbb);
      this.placeBlock(world, oakFence, 11, 6 + height, 11, sbb);
      this.placeBlock(world, oakFence, 4, 3 + height, 11, sbb);
      this.placeBlock(world, oakFence, 6, 4 + height, 11, sbb);
      this.placeBlock(world, oakFence, 8, 5 + height, 11, sbb);
      this.placeBlock(world, oakFence, 10, 6 + height, 11, sbb);
      this.placeBlock(world, oakFence, 11, 7 + height, 11, sbb);
      this.setOrientation(temp);
   }

   protected void generatePaintingsOnWall(
      WorldGenLevel world, RandomSource rand, int howMany, int floorLevel, Direction direction, int minSize, BoundingBox sbb
   ) {
      for (int i = 0; i < howMany; i++) {
         BlockPos pCoords = this.getRandomWallSpot(rand, floorLevel, direction, sbb);
         if (pCoords != null) {
            EntityUtil.tryHangPainting(world, pCoords, direction, EntityUtil.getPaintingOfSize(world, rand, minSize));
         }
      }
   }

   @Nullable
   protected BlockPos getRandomWallSpot(RandomSource rand, int floorLevel, Direction direction, BoundingBox sbb) {
      int minX = this.boundingBox.minX() + 2;
      int maxX = this.boundingBox.maxX() - 2;
      int minY = this.boundingBox.minY() + floorLevel + 2;
      int maxY = this.boundingBox.maxY() - 2;
      int minZ = this.boundingBox.minZ() + 2;
      int maxZ = this.boundingBox.maxZ() - 2;
      if (direction == Direction.SOUTH) {
         minZ = this.boundingBox.minZ();
         maxZ = this.boundingBox.minZ();
      } else if (direction == Direction.WEST) {
         maxX = this.boundingBox.maxX();
         minX = this.boundingBox.maxX();
      } else if (direction == Direction.NORTH) {
         maxZ = this.boundingBox.maxZ();
         minZ = this.boundingBox.maxZ();
      } else if (direction == Direction.EAST) {
         minX = this.boundingBox.minX();
         maxX = this.boundingBox.minX();
      }

      for (int i = 0; i < 30; i++) {
         int cx = minX + (maxX > minX ? rand.nextInt(maxX - minX) : 0);
         int cy = minY + (maxY > minY ? rand.nextInt(maxY - minY) : 0);
         int cz = minZ + (maxZ > minZ ? rand.nextInt(maxZ - minZ) : 0);
         BlockPos blockPos = new BlockPos(cx, cy, cz).relative(direction);
         if (sbb.isInside(blockPos)) {
            return blockPos;
         }
      }

      return null;
   }

   protected void makeGlyphBranches(WorldGenLevel world, RandomSource rand, BlockState colour, BoundingBox sbb) {
      Rotation rotation = RotationUtil.ROTATIONS[rand.nextInt(4)];
      int startHeight = this.height > 1 ? rand.nextInt((int)(this.height * 0.66F)) : this.height;
      int startZ = 3 + rand.nextInt(this.size - 6);
      int dx = this.getXWithOffsetRotated(0, startZ, rotation);
      int dz = this.getZWithOffsetRotated(0, startZ, rotation);
      if (sbb.isInside(new BlockPos(dx, this.boundingBox.minY() + 1, dz))) {
         for (int dy = this.getWorldY(startHeight); dy > 0; dy--) {
            BlockPos pos = new BlockPos(dx, dy, dz);
            if (!world.getBlockState(pos).is(BlockTagGenerator.CASTLE_BLOCKS) || !world.getBlockState(pos).isRedstoneConductor(world, pos)) {
               break;
            }

            world.setBlock(pos, colour, 2);
         }
      }

      int leftOffset = startZ - (1 + rand.nextInt(3));
      int leftHeight = rand.nextInt(Math.max(this.height - startHeight, 1));
      if (leftOffset >= 0) {
         for (int z = startZ; z > leftOffset; z--) {
            this.setBlockStateRotated(world, colour, 0, startHeight, z, rotation, sbb);
         }

         for (int y = startHeight; y < startHeight + leftHeight; y++) {
            this.setBlockStateRotated(world, colour, 0, y, leftOffset, rotation, sbb);
         }
      }

      int rightOffset = startZ + 1 + rand.nextInt(3);
      int rightHeight = rand.nextInt(Math.max(this.height - startHeight, 1));
      if (rightOffset < this.size - 1) {
         for (int z = startZ; z < rightOffset; z++) {
            this.setBlockStateRotated(world, colour, 0, startHeight, z, rotation, sbb);
         }

         for (int y = startHeight; y < startHeight + rightHeight; y++) {
            this.setBlockStateRotated(world, colour, 0, y, rightOffset, rotation, sbb);
         }
      }
   }

   @Override
   public int getGroundLevelDelta() {
      return 1;
   }
}
