package twilightforest.world.components.structures.darktower;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFConfiguredFeatures;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFMaze;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.TFStructureDecorator;

public class DarkTowerMainComponent extends DarkTowerWingComponent {
   private boolean placedKeys = false;

   public DarkTowerMainComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTMai.get(), nbt);
   }

   public DarkTowerMainComponent(RandomSource rand, int index, int x, int y, int z) {
      this(rand, index, x + 10, y, z + 10, Direction.NORTH);
   }

   public DarkTowerMainComponent(RandomSource rand, int index, int x, int y, int z, Direction rotation) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTMai.get(), index, x, y, z, 19, 56 + rand.nextInt(32) / 5 * 5, rotation);
      if (this.boundingBox.maxY() > 245) {
         int amtToLower = (this.boundingBox.maxY() - 245) / 5 * 5 + 5;
         TwilightForestMod.LOGGER.info("Lowering Dark Tower max height by {} to be within world bounds", amtToLower);
         this.height -= amtToLower;
         this.boundingBox = this.boundingBox.moved(0, -amtToLower, 0);
      }

      if (this.deco == null) {
         this.deco = new StructureDecoratorDarkTower();
      }
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      if (this.getGenDepth() > 0) {
         this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      }

      Rotation mainDir = null;
      if (this.getGenDepth() < 2) {
         mainDir = RotationUtil.ROTATIONS[rand.nextInt(RotationUtil.ROTATIONS.length)];

         for (Rotation rotation : RotationUtil.ROTATIONS) {
            if (rotation != mainDir) {
               int[] dest = this.getValidOpening(rand, rotation);
               int childHeight = this.validateChildHeight(21 + rand.nextInt(10));
               this.makeTowerWing(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], 11, childHeight, rotation);
            }
         }
      } else {
         for (Rotation i : RotationUtil.ROTATIONS) {
            int[] dest = this.getValidOpening(rand, i);
            this.makeBossTrapWing(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], i);
         }
      }

      if (this.getGenDepth() > 0) {
         for (Rotation i : RotationUtil.ROTATIONS) {
            if (i != Rotation.CLOCKWISE_180) {
               int[] dest = this.getValidOpening(rand, i);
               dest[1] = 1;
               int childHeight = this.validateChildHeight(21 + rand.nextInt(10));
               this.makeTowerWing(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], 11, childHeight, i);
            }
         }

         this.makeABeard(parent, list, rand);
      } else {
         for (Rotation rotation : new Rotation[]{Rotation.NONE, Rotation.CLOCKWISE_180}) {
            int[] dest = this.getValidOpening(rand, rotation);
            dest[1] = 1;
            int childHeight = this.validateChildHeight(10 + rand.nextInt(5));
            this.makeEntranceTower(list, rand, 5, dest[0], dest[1], dest[2], 9, childHeight, rotation);
         }
      }

      if (mainDir != null) {
         int[] dest = this.getValidOpening(rand, mainDir);
         this.makeNewLargeTower(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], mainDir);
      }

      this.makeARoof(parent, list, rand);
      if (!this.placedKeys && this.getGenDepth() < 2) {
         ArrayList<DarkTowerWingComponent> possibleKeyTowers = new ArrayList<>();
         if (list instanceof StructurePiecesBuilder start) {
            for (StructurePiece piece : start.pieces) {
               if (piece instanceof DarkTowerWingComponent wing && wing.size == 9 && wing.getGenDepth() == this.getGenDepth()) {
                  possibleKeyTowers.add(wing);
               }
            }
         }

         for (int i = 0; i < 4; i++) {
            if (possibleKeyTowers.size() < 1) {
               TwilightForestMod.LOGGER.warn("Dark forest tower could not find four small towers to place keys in.");
               break;
            }

            int towerNum = rand.nextInt(possibleKeyTowers.size());
            possibleKeyTowers.get(towerNum).setKeyTower(true);
            possibleKeyTowers.remove(towerNum);
         }

         this.placedKeys = true;
      }
   }

   private boolean makeEntranceTower(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int childSize, int childHeight, Rotation rotation
   ) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 5, direction);
      DarkTowerBridgeComponent bridge = new DarkTowerEntranceBridgeComponent(index, dx[0], dx[1], dx[2], childSize, childHeight, direction);
      list.addPiece(bridge);
      bridge.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }

   private boolean makeNewLargeTower(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, Rotation rotation) {
      int wingSize = 15;
      int wingHeight = 56;
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 5, direction);
      DarkTowerMainBridgeComponent bridge = new DarkTowerMainBridgeComponent(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
      list.addPiece(bridge);
      bridge.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation, EnumDarkTowerDoor.LOCKED);
      return true;
   }

   private boolean makeBossTrapWing(StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, Rotation rotation) {
      int wingSize = 11;
      int wingHeight = 9;
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, 5, direction);
      DarkTowerBossBridgeComponent bridge = new DarkTowerBossBridgeComponent(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
      list.addPiece(bridge);
      bridge.addChildren(this, list, rand);
      this.addOpening(x, y, z, rotation);
      return true;
   }

   @Override
   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (this.getGenDepth() < 2) {
         super.makeARoof(parent, list, rand);
      }
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      this.makeEncasedWalls(world, rand, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
      this.generateAirBox(world, sbb, 1, 1, 1, this.size - 2, this.height - 2, this.size - 2);
      if (this.getGenDepth() == 0) {
         for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
               this.placeBlock(world, this.deco.accentState, x, -1, z, sbb);
            }
         }
      }

      int totalFloors = this.height / 5;
      boolean beamMaze = decoRNG.nextBoolean();
      int centerFloors = beamMaze ? 4 : totalFloors / 2;
      int bottomFloors = (totalFloors - centerFloors) / 2;
      int topFloorsStartY = this.height - (bottomFloors * 5 + 1);
      this.addThreeQuarterFloors(world, manager, generator, decoRNG, sbb, 0, bottomFloors * 5);
      if (this.getGenDepth() < 2) {
         this.addThreeQuarterFloors(world, manager, generator, decoRNG, sbb, topFloorsStartY, this.height - 1);
      } else {
         this.addThreeQuarterFloorsDecorateBoss(world, decoRNG, sbb, topFloorsStartY, this.height - 1);
         this.destroyTower(world, decoRNG, 12, this.height + 4, 3, 4, sbb);
         this.destroyTower(world, decoRNG, 3, this.height + 4, 12, 4, sbb);
         this.destroyTower(world, decoRNG, 3, this.height + 4, 3, 4, sbb);
         this.destroyTower(world, decoRNG, 12, this.height + 4, 12, 4, sbb);
         this.destroyTower(world, decoRNG, 8, this.height + 4, 8, 5, sbb);
         this.decorateBossSpawner(world, sbb, Rotation.NONE, this.height - 6);
      }

      if (beamMaze) {
         this.addTimberMaze(world, decoRNG, sbb, bottomFloors * 5, topFloorsStartY);
      } else {
         this.addBuilderPlatforms(world, decoRNG, sbb, bottomFloors * 5, topFloorsStartY);
      }

      this.makeOpenings(world, sbb);
   }

   protected void addThreeQuarterFloors(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource decoRNG, BoundingBox sbb, int bottom, int top
   ) {
      int spacing = 5;
      Rotation rotation = RotationUtil.ROTATIONS[Math.abs((this.boundingBox.minY() + bottom) % 4)];
      if (bottom == 0) {
         this.makeLargeStairsUp(world, sbb, rotation, 0);
         rotation = rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
         this.makeBottomEntrance(world, sbb, rotation, bottom);
         bottom += spacing;
      }

      for (int y = bottom; y < top; y += spacing) {
         boolean isBottomFloor = y == bottom && bottom != spacing;
         boolean isTopFloor = y >= top - spacing;
         boolean isTowerTopFloor = y >= this.height - spacing - 2;
         this.makeThreeQuarterFloor(world, sbb, rotation, y, isBottomFloor, isTowerTopFloor);
         if (!isTopFloor) {
            this.makeLargeStairsUp(world, sbb, rotation, y);
         }

         if (!isTopFloor || isTowerTopFloor) {
            this.decorateFloor(world, manager, generator, decoRNG, sbb, rotation, y, isBottomFloor, isTopFloor);
         }

         rotation = rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
      }
   }

   protected void addThreeQuarterFloorsDecorateBoss(WorldGenLevel world, RandomSource decoRNG, BoundingBox sbb, int bottom, int top) {
      int spacing = 5;
      Rotation rotation = RotationUtil.ROTATIONS[(this.boundingBox.minY() + bottom) % 4];
      if (bottom == 0) {
         this.makeLargeStairsUp(world, sbb, rotation, 0);
         rotation = rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
         bottom += spacing;
      }

      for (int y = bottom; y < top; y += spacing) {
         boolean isBottomFloor = y == bottom && bottom != spacing;
         boolean isTopFloor = y >= top - spacing;
         boolean isTowerTopFloor = y >= this.height - spacing - 2;
         this.makeThreeQuarterFloor(world, sbb, rotation, y, isBottomFloor, isTowerTopFloor);
         if (!isTopFloor) {
            this.makeLargeStairsUp(world, sbb, rotation, y);
            this.decorateExperiment(world, sbb, rotation, y);
         }

         rotation = rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
      }
   }

   private void decorateFloor(
      WorldGenLevel world,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource decoRNG,
      BoundingBox sbb,
      Rotation rotation,
      int y,
      boolean isBottom,
      boolean isTop
   ) {
      if (isTop) {
         switch (decoRNG.nextInt(3)) {
            case 1:
               this.decorateBotanical(world, generator, decoRNG, sbb, rotation, y);
               break;
            case 2:
               this.decorateNetherwart(world, decoRNG, sbb, rotation, y, isTop);
               break;
            default:
               this.decorateAquarium(world, sbb, rotation, y);
         }
      } else if (isBottom) {
         switch (decoRNG.nextInt(4)) {
            case 0:
            default:
               this.decorateAquarium(world, sbb, rotation, y);
               break;
            case 1:
               this.decorateBotanical(world, generator, decoRNG, sbb, rotation, y);
               break;
            case 2:
               if (y + this.boundingBox.minY() > 64) {
                  this.decorateNetherwart(world, decoRNG, sbb, rotation, y, isTop);
                  break;
               }
            case 3:
               this.decorateForge(world, decoRNG, sbb, rotation, y);
         }
      } else {
         switch (decoRNG.nextInt(8)) {
            case 0:
            case 1:
            default:
               this.decorateReappearingMaze(world, manager, generator, decoRNG, sbb, rotation, y);
               break;
            case 2:
               this.decorateUnbuilderMaze(world, decoRNG, sbb, rotation, y);
               break;
            case 3:
               this.decorateAquarium(world, sbb, rotation, y);
               break;
            case 4:
               this.decorateBotanical(world, generator, decoRNG, sbb, rotation, y);
               break;
            case 5:
               if (y + this.boundingBox.minY() > 64) {
                  this.decorateNetherwart(world, decoRNG, sbb, rotation, y, isTop);
                  break;
               }
            case 6:
               this.decorateLounge(world, generator, decoRNG, sbb, rotation, y);
               break;
            case 7:
               this.decorateForge(world, decoRNG, sbb, rotation, y);
         }
      }
   }

   protected void makeThreeQuarterFloor(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y, boolean isBottom, boolean isTowerTopFloor) {
      int half = this.size / 2;
      this.fillBlocksRotated(world, sbb, half + 1, y, 1, this.size - 2, y, half + 1, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 1, y, half + 1, this.size - 2, y, this.size - 2, this.deco.blockState, rotation);
      int startZ = isBottom ? 1 : 3;
      this.fillBlocksRotated(world, sbb, 1, y, half, half, y, half, this.deco.accentState, rotation);
      this.fillBlocksRotated(world, sbb, half, y, startZ, half, y, half, this.deco.accentState, rotation);
      this.fillBlocksRotated(world, sbb, 1, y + 1, half, half, y + 1, half, this.deco.fenceState, rotation);
      this.fillBlocksRotated(world, sbb, half, y + 1, startZ, half, y + 1, half, this.deco.fenceState, rotation);
      if (isTowerTopFloor) {
         this.fillBlocksRotated(world, sbb, 1, y, half - 2, 3, y, half, this.deco.accentState, rotation);
         this.fillBlocksRotated(world, sbb, 1, y + 1, half - 2, 3, y + 1, half, this.deco.fenceState, rotation);
         this.fillBlocksRotated(world, sbb, 1, y, half - 1, 2, y, half, this.deco.fenceState, rotation);
         this.fillBlocksRotated(world, sbb, 1, y + 1, half - 1, 2, y + 1, half, AIR, rotation);
      }
   }

   protected void makeLargeStairsUp(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      for (int i = 0; i < 5; i++) {
         int z = this.size / 2 - i + 4;
         int sy = y + i + 1;
         this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), 1, sy, z, rotation, sbb);
         this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), 2, sy, z, rotation, sbb);
         this.setBlockStateRotated(world, this.deco.blockState, 1, sy, z - 1, rotation, sbb);
         this.setBlockStateRotated(world, this.deco.blockState, 2, sy, z - 1, rotation, sbb);
         this.setBlockStateRotated(world, this.deco.blockState, 3, sy, z - 1, rotation, sbb);
         if (i > 0 && i < 4) {
            this.setBlockStateRotated(world, this.deco.accentState, 3, sy, z, rotation, sbb);
            this.setBlockStateRotated(world, this.deco.fenceState, 3, sy + 1, z, rotation, sbb);
            this.setBlockStateRotated(world, this.deco.fenceState, 3, sy + 2, z, rotation, sbb);
         } else if (i == 0) {
            this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, false), 3, sy, z, rotation, sbb);
         }
      }
   }

   private void decorateReappearingMaze(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource decoRNG, BoundingBox sbb, Rotation rotation, int y
   ) {
      int mazeSize = 6;
      decoRNG.setSeed(world.getSeed() + this.boundingBox.minX() * 90342903L + y * 90342903L ^ this.boundingBox.minZ());
      TFMaze maze = new TFMaze(mazeSize, mazeSize, decoRNG);

      for (int i = 0; i < 13; i++) {
         maze.putRaw(i, 0, 5);
         maze.putRaw(i, 12, 5);
         maze.putRaw(0, i, 5);
         maze.putRaw(12, i, 5);
      }

      maze.doorRarity = 0.3F;
      switch (rotation) {
         case NONE:
            for (int x = 1; x < 6; x++) {
               for (int z = 1; z < 6; z++) {
                  maze.putRaw(x, z, 5);
               }
            }

            maze.putRaw(1, 6, 5);
            maze.putRaw(1, 7, 5);
            maze.putRaw(1, 8, 5);
            maze.putRaw(1, 9, 5);
            maze.putRaw(1, 10, 6);
            maze.putRaw(6, 1, 5);
            maze.putRaw(7, 1, 5);
            maze.putRaw(8, 1, 6);
            maze.generateRecursiveBacktracker(0, 5, decoRNG);
            break;
         case CLOCKWISE_90:
            for (int x = 7; x < 12; x++) {
               for (int z = 1; z < 6; z++) {
                  maze.putRaw(x, z, 5);
               }
            }

            maze.putRaw(6, 1, 5);
            maze.putRaw(5, 1, 5);
            maze.putRaw(4, 1, 5);
            maze.putRaw(3, 1, 5);
            maze.putRaw(2, 1, 6);
            maze.putRaw(11, 6, 5);
            maze.putRaw(11, 7, 5);
            maze.putRaw(11, 8, 6);
            maze.generateRecursiveBacktracker(0, 0, decoRNG);
            break;
         case CLOCKWISE_180:
            for (int x = 7; x < 12; x++) {
               for (int z = 7; z < 12; z++) {
                  maze.putRaw(x, z, 5);
               }
            }

            maze.putRaw(11, 6, 5);
            maze.putRaw(11, 5, 5);
            maze.putRaw(11, 4, 5);
            maze.putRaw(11, 3, 5);
            maze.putRaw(11, 2, 6);
            maze.putRaw(6, 11, 5);
            maze.putRaw(5, 11, 5);
            maze.putRaw(4, 11, 6);
            maze.generateRecursiveBacktracker(5, 0, decoRNG);
            break;
         case COUNTERCLOCKWISE_90:
            for (int x = 1; x < 6; x++) {
               for (int z = 7; z < 12; z++) {
                  maze.putRaw(x, z, 5);
               }
            }

            maze.putRaw(6, 11, 5);
            maze.putRaw(7, 11, 5);
            maze.putRaw(8, 11, 5);
            maze.putRaw(9, 11, 5);
            maze.putRaw(10, 11, 6);
            maze.putRaw(1, 6, 5);
            maze.putRaw(1, 5, 5);
            maze.putRaw(1, 4, 6);
            maze.generateRecursiveBacktracker(5, 5, decoRNG);
      }

      maze.wallBlockState = this.deco.blockState;
      maze.headBlockState = this.deco.accentState;
      maze.pillarBlockState = this.deco.accentState;
      maze.doorBlockState = ((Block)TFBlocks.REAPPEARING_BLOCK.get()).defaultBlockState();
      maze.torchRarity = 0.0F;
      maze.tall = 3;
      maze.head = 1;
      maze.oddBias = 2;
      maze.copyToStructure(world, manager, generator, 0, y + 1, 0, this, sbb, decoRNG);
      this.decorateMazeDeadEnds(world, decoRNG, maze, y, rotation, sbb);
   }

   protected void decorateMazeDeadEnds(WorldGenLevel world, RandomSource decoRNG, TFMaze maze, int y, Rotation rotation, BoundingBox sbb) {
      for (int x = 0; x < maze.width; x++) {
         for (int z = 0; z < maze.depth; z++) {
            if (!maze.isWall(x, z, x - 1, z) && maze.isWall(x, z, x + 1, z) && maze.isWall(x, z, x, z - 1) && maze.isWall(x, z, x, z + 1)) {
               this.decorateDeadEnd(world, x, y, z, 3, sbb);
            }

            if (maze.isWall(x, z, x - 1, z) && !maze.isWall(x, z, x + 1, z) && maze.isWall(x, z, x, z - 1) && maze.isWall(x, z, x, z + 1)) {
               this.decorateDeadEnd(world, x, y, z, 1, sbb);
            }

            if (maze.isWall(x, z, x - 1, z) && maze.isWall(x, z, x + 1, z) && !maze.isWall(x, z, x, z - 1) && maze.isWall(x, z, x, z + 1)) {
               this.decorateDeadEnd(world, x, y, z, 0, sbb);
            }

            if (maze.isWall(x, z, x - 1, z) && maze.isWall(x, z, x + 1, z) && maze.isWall(x, z, x, z - 1) && !maze.isWall(x, z, x, z + 1)) {
               this.decorateDeadEnd(world, x, y, z, 2, sbb);
            }
         }
      }
   }

   private void decorateDeadEnd(WorldGenLevel world, int mx, int y, int mz, int facing, BoundingBox sbb) {
      int x = mx * 3 + 1;
      int z = mz * 3 + 1;
      switch (facing) {
         case 0:
            this.placeBlock(world, this.deco.accentState, x, y + 1, z + 1, sbb);
            this.placeBlock(world, this.deco.accentState, x + 1, y + 1, z + 1, sbb);
            this.setDoubleLootChest(world, x, y + 2, z + 1, x + 1, y + 2, z + 1, Direction.SOUTH, TFLootTables.DARKTOWER_CACHE, sbb, false);
            break;
         case 1:
            this.placeBlock(world, this.deco.accentState, x, y + 1, z, sbb);
            this.placeBlock(world, this.deco.accentState, x, y + 1, z + 1, sbb);
            this.setDoubleLootChest(world, x, y + 2, z, x, y + 2, z + 1, Direction.WEST, TFLootTables.DARKTOWER_CACHE, sbb, false);
            break;
         case 2:
            this.placeBlock(world, this.deco.accentState, x, y + 1, z, sbb);
            this.placeBlock(world, this.deco.accentState, x + 1, y + 1, z, sbb);
            this.setDoubleLootChest(world, x + 1, y + 2, z, x, y + 2, z, Direction.NORTH, TFLootTables.DARKTOWER_CACHE, sbb, false);
            break;
         case 3:
            this.placeBlock(world, this.deco.accentState, x + 1, y + 1, z, sbb);
            this.placeBlock(world, this.deco.accentState, x + 1, y + 1, z + 1, sbb);
            this.setDoubleLootChest(world, x + 1, y + 2, z + 1, x + 1, y + 2, z, Direction.EAST, TFLootTables.DARKTOWER_CACHE, sbb, false);
      }
   }

   private void decorateUnbuilderMaze(WorldGenLevel world, RandomSource decoRNG, BoundingBox sbb, Rotation rotation, int y) {
      for (int x = this.size / 2; x < this.size - 1; x++) {
         for (int z = 3; z < this.size - 1; z++) {
            if (x % 2 == 1 && z % 2 == 1) {
               for (int py = 1; py < 5; py++) {
                  this.setBlockStateRotated(world, this.deco.pillarState, x, y + py, z, rotation, sbb);
               }
            } else if (x % 2 == 1 || z % 2 == 1) {
               for (int py = 1; py < 5; py++) {
                  this.setBlockStateRotated(world, this.deco.fenceState, x, y + py, z, rotation, sbb);
               }

               if (x != this.size / 2 + 2 && x != this.size - 1 && z != this.size - 1) {
                  int ay = decoRNG.nextInt(4) + 1;
                  this.setBlockStateRotated(world, AIR, x, y + ay, z, rotation, sbb);
                  if (x > this.size - 7) {
                     ay = decoRNG.nextInt(3) + 1;
                     this.setBlockStateRotated(world, AIR, x, y + ay, z, rotation, sbb);
                  }
               }
            }
         }
      }

      BlockState antiBuilderBlockState = ((Block)TFBlocks.ANTIBUILDER.get()).defaultBlockState();
      this.setBlockStateRotated(world, antiBuilderBlockState, 15, y + 2, 7, rotation, sbb);
      this.setBlockStateRotated(world, antiBuilderBlockState, 11, y + 3, 7, rotation, sbb);
      this.setBlockStateRotated(world, antiBuilderBlockState, 15, y + 2, 13, rotation, sbb);
      this.setBlockStateRotated(world, antiBuilderBlockState, 11, y + 3, 13, rotation, sbb);
      this.setBlockStateRotated(world, antiBuilderBlockState, 5, y + 3, 13, rotation, sbb);
   }

   private void decorateLounge(WorldGenLevel world, ChunkGenerator generator, RandomSource decoRNG, BoundingBox sbb, Rotation rotation, int y) {
      this.fillBlocksRotated(world, sbb, 17, y + 1, 1, 17, y + 4, 6, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 1, 17, y + 4, 1, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 1, 2, 16, y + 1, 5, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 2, 12, y + 1, 6, getStairState(this.deco.stairState, Direction.WEST, false), rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 6, 16, y + 1, 6, getStairState(this.deco.stairState, Direction.SOUTH, false), rotation);
      this.makeDispenserPillar(world, this.deco, 13, y, 1, Direction.SOUTH, rotation, sbb);
      this.makeDispenserPillar(world, this.deco, 15, y, 1, Direction.SOUTH, rotation, sbb);
      this.makeDispenserPillar(world, this.deco, 17, y, 3, Direction.WEST, rotation, sbb);
      this.makeDispenserPillar(world, this.deco, 17, y, 5, Direction.WEST, rotation, sbb);
      this.makeStonePillar(world, this.deco, 12, y, 1, rotation, sbb);
      this.makeStonePillar(world, this.deco, 17, y, 6, rotation, sbb);
      this.setBlockStateRotated(world, Blocks.BREWING_STAND.defaultBlockState(), 13, y + 2, 5, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), 15, y + 2, 3, rotation, sbb
      );
      this.fillBlocksRotated(world, sbb, 10, y + 1, 17, 17, y + 4, 17, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 17, y + 1, 10, 17, y + 4, 17, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 11, y + 1, 17, 12, y + 4, 17, Blocks.BOOKSHELF.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, 14, y + 1, 17, 15, y + 4, 17, Blocks.BOOKSHELF.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, 17, y + 1, 11, 17, y + 4, 12, Blocks.BOOKSHELF.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, 17, y + 1, 14, 17, y + 4, 15, Blocks.BOOKSHELF.defaultBlockState(), rotation);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, true), 13, y + 1, 14, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, true), 14, y + 1, 14, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, true), 14, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, true), 13, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, false), 11, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), 13, y + 1, 11, rotation, sbb);
      this.setBlockStateRotated(world, Blocks.REDSTONE_LAMP.defaultBlockState(), 8, y + 3, 8, rotation, sbb);
      this.setBlockStateRotated(
         world,
         getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.CEILING, decoRNG.nextBoolean() ? Direction.EAST : Direction.NORTH, false),
         8,
         y + 2,
         8,
         rotation,
         sbb
      );
      this.placeTreePlanter(world, generator, decoRNG.nextInt(5), 6, y + 1, 12, rotation, sbb);
   }

   private void makeDispenserPillar(
      WorldGenLevel world, TFStructureDecorator forgeDeco, int x, int y, int z, Direction stairMeta, Rotation rotation, BoundingBox sbb
   ) {
      this.setBlockStateRotated(world, getStairState(forgeDeco.stairState, stairMeta, true), x, y + 2, z, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, stairMeta.getOpposite()), x, y + 3, z, rotation, sbb
      );
      this.setBlockStateRotated(world, getStairState(forgeDeco.stairState, stairMeta, false), x, y + 4, z, rotation, sbb);
   }

   private void decorateBossSpawner(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      this.setBlockStateRotated(world, ((Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get()).defaultBlockState(), 9, y + 4, 9, rotation, sbb);
   }

   private void decorateExperiment(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      BlockState obsidian = Blocks.OBSIDIAN.defaultBlockState();
      BlockState netherrack = Blocks.NETHERRACK.defaultBlockState();
      BlockState redstone = Blocks.REDSTONE_BLOCK.defaultBlockState();
      BlockState inactiveReactor = ((Block)TFBlocks.CARMINITE_REACTOR.get()).defaultBlockState();
      this.fillBlocksRotated(world, sbb, 17, y + 1, 1, 17, y + 4, 6, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 1, 17, y + 4, 1, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 1, 2, 16, y + 1, 5, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 2, 12, y + 1, 6, getStairState(this.deco.stairState, Direction.WEST, false), rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 6, 16, y + 1, 6, getStairState(this.deco.stairState, Direction.SOUTH, false), rotation);
      this.makeWoodPillar(world, 13, y, 1, rotation, sbb);
      this.makeWoodPillar(world, 15, y, 1, rotation, sbb);
      this.makeWoodPillar(world, 17, y, 3, rotation, sbb);
      this.makeWoodPillar(world, 17, y, 5, rotation, sbb);
      this.makeStonePillar(world, this.deco, 12, y, 1, rotation, sbb);
      this.makeStonePillar(world, this.deco, 17, y, 6, rotation, sbb);
      this.setBlockStateRotated(world, Blocks.CRAFTING_TABLE.defaultBlockState(), 14, y + 2, 4, rotation, sbb);
      this.placeItemFrameRotated(world, 13, y + 2, 1, rotation, Direction.SOUTH, new ItemStack((ItemLike)TFItems.BORER_ESSENCE.get()), sbb);
      this.placeItemFrameRotated(world, 14, y + 2, 1, rotation, Direction.SOUTH, new ItemStack(Items.REDSTONE), sbb);
      this.placeItemFrameRotated(world, 15, y + 2, 1, rotation, Direction.SOUTH, new ItemStack((ItemLike)TFItems.BORER_ESSENCE.get()), sbb);
      this.placeItemFrameRotated(world, 13, y + 3, 1, rotation, Direction.SOUTH, new ItemStack(Items.REDSTONE), sbb);
      this.placeItemFrameRotated(world, 14, y + 3, 1, rotation, Direction.SOUTH, new ItemStack(Items.GHAST_TEAR), sbb);
      this.placeItemFrameRotated(world, 15, y + 3, 1, rotation, Direction.SOUTH, new ItemStack(Items.REDSTONE), sbb);
      this.placeItemFrameRotated(world, 13, y + 4, 1, rotation, Direction.SOUTH, new ItemStack((ItemLike)TFItems.BORER_ESSENCE.get()), sbb);
      this.placeItemFrameRotated(world, 14, y + 4, 1, rotation, Direction.SOUTH, new ItemStack(Items.REDSTONE), sbb);
      this.placeItemFrameRotated(world, 15, y + 4, 1, rotation, Direction.SOUTH, new ItemStack((ItemLike)TFItems.BORER_ESSENCE.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 2, 3, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 2, 4, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 2, 5, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 3, 3, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 3, 4, rotation, Direction.WEST, new ItemStack((ItemLike)TFItems.CARMINITE.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 3, 5, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 4, 3, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 4, 4, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.TOWERWOOD.get()), sbb);
      this.placeItemFrameRotated(world, 17, y + 4, 5, rotation, Direction.WEST, new ItemStack((ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()), sbb);
      if (y < this.height - 13) {
         this.setBlockStateRotated(world, obsidian, 13, y + 1, 13, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 15, y + 1, 13, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 13, y + 1, 15, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 15, y + 1, 15, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 13, y + 1, 14, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 14, y + 1, 13, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 15, y + 1, 14, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 14, y + 1, 15, rotation, sbb);
         this.setBlockStateRotated(world, redstone, 14, y + 1, 14, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 13, y + 2, 13, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 15, y + 2, 13, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 13, y + 2, 15, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 15, y + 2, 15, rotation, sbb);
         this.setBlockStateRotated(world, inactiveReactor, 14, y + 2, 14, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 13, y + 3, 13, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 15, y + 3, 13, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 13, y + 3, 15, rotation, sbb);
         this.setBlockStateRotated(world, obsidian, 15, y + 3, 15, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 13, y + 3, 14, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 14, y + 3, 13, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 15, y + 3, 14, rotation, sbb);
         this.setBlockStateRotated(world, netherrack, 14, y + 3, 15, rotation, sbb);
         this.setBlockStateRotated(world, redstone, 14, y + 3, 14, rotation, sbb);
      }

      this.setBlockStateRotated(world, this.deco.accentState, 14, y + 1, 17, rotation, sbb);
      this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.EAST, false), 13, y + 1, 17, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH), 14, y + 2, 17, rotation, sbb
      );
      this.setBlockStateRotated(world, redstone, 14, y + 2, 16, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, 17, y + 1, 14, rotation, sbb);
      this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.SOUTH, false), 17, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST), 17, y + 2, 14, rotation, sbb
      );
      this.setBlockStateRotated(world, redstone, 16, y + 2, 14, rotation, sbb);
      this.setBlockStateRotated(world, redstone, 14, y + 2, 11, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, 14, y + 1, 11, rotation, sbb);
      this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.EAST, true), 13, y + 1, 11, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH), 14, y + 2, 10, rotation, sbb
      );
      this.setBlockStateRotated(world, this.deco.accentState, 14, y + 1, 9, rotation, sbb);
      this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.EAST, false), 13, y + 1, 9, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH), 14, y + 2, 9, rotation, sbb
      );
      this.setBlockStateRotated(world, redstone, 11, y + 2, 14, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, 11, y + 1, 14, rotation, sbb);
      this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.SOUTH, true), 11, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST), 10, y + 2, 14, rotation, sbb
      );
      this.setBlockStateRotated(world, this.deco.accentState, 9, y + 1, 14, rotation, sbb);
      this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.WALL, Direction.SOUTH, false), 9, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST), 9, y + 2, 14, rotation, sbb
      );
   }

   private void makeWoodPillar(WorldGenLevel world, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      BlockState log = ((RotatedPillarBlock)TFBlocks.DARK_LOG.get()).defaultBlockState();
      this.setBlockStateRotated(world, log, x, y + 2, z, rotation, sbb);
      this.setBlockStateRotated(world, log, x, y + 3, z, rotation, sbb);
      this.setBlockStateRotated(world, log, x, y + 4, z, rotation, sbb);
   }

   private void placeItemFrameRotated(WorldGenLevel world, int x, int y, int z, Rotation rotation, Direction direction, ItemStack itemStack, BoundingBox sbb) {
      int dx = this.getXWithOffsetRotated(x, z, rotation);
      int dy = this.getWorldY(y);
      int dz = this.getZWithOffsetRotated(x, z, rotation);
      Direction facing = this.rotation.getRotated(rotation).rotate(direction).getOpposite();
      BlockPos pos = new BlockPos(dx, dy, dz).relative(facing);
      if (sbb.isInside(pos)) {
         ItemFrame frame = new ItemFrame(world.getLevel(), pos, facing);
         if (!itemStack.isEmpty()) {
            frame.setItem(itemStack, false);
         }

         world.addFreshEntity(frame);
      }
   }

   private void decorateAquarium(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      this.makePillarFrame(world, sbb, this.deco, rotation, 12, y, 3, 4, 4, 13, false);
      this.fillBlocksRotated(world, sbb, 13, y + 4, 4, 14, y + 4, 14, Blocks.WATER.defaultBlockState(), rotation);
      this.makePillarFrame(world, sbb, this.deco, rotation, 6, y, 12, 4, 4, 4, false);
      this.fillBlocksRotated(world, sbb, 6, y + 5, 12, 9, y + 5, 15, this.deco.accentState, rotation);
      this.fillBlocksRotated(world, sbb, 7, y + 4, 13, 8, y + 5, 14, Blocks.WATER.defaultBlockState(), rotation);
   }

   private void decorateForge(WorldGenLevel world, RandomSource decoRNG, BoundingBox sbb, Rotation rotation, int y) {
      TFStructureDecorator forgeDeco = this.deco;
      this.fillBlocksRotated(world, sbb, 17, y + 1, 1, 17, y + 4, 6, forgeDeco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 1, 17, y + 4, 1, forgeDeco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 17, 17, y + 4, 17, forgeDeco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 17, y + 1, 12, 17, y + 4, 17, forgeDeco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 1, 2, 16, y + 1, 5, forgeDeco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 2, 12, y + 1, 6, getStairState(this.deco.stairState, Direction.WEST, false), rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 6, 16, y + 1, 6, getStairState(this.deco.stairState, Direction.SOUTH, false), rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 1, 13, 16, y + 1, 16, forgeDeco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 12, 12, y + 1, 16, getStairState(this.deco.stairState, Direction.WEST, false), rotation);
      this.fillBlocksRotated(world, sbb, 12, y + 1, 12, 16, y + 1, 12, getStairState(this.deco.stairState, Direction.NORTH, false), rotation);
      this.makeFurnacePillar(world, decoRNG, 13, y, 1, Direction.SOUTH, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 15, y, 1, Direction.SOUTH, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 17, y, 3, Direction.WEST, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 17, y, 5, Direction.WEST, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 13, y, 17, Direction.NORTH, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 15, y, 17, Direction.NORTH, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 17, y, 13, Direction.WEST, rotation, sbb);
      this.makeFurnacePillar(world, decoRNG, 17, y, 15, Direction.WEST, rotation, sbb);
      this.makeStonePillar(world, forgeDeco, 17, y, 6, rotation, sbb);
      this.makeStonePillar(world, forgeDeco, 12, y, 17, rotation, sbb);
      this.makeStonePillar(world, forgeDeco, 17, y, 12, rotation, sbb);
      this.makeStonePillar(world, forgeDeco, 17, y, 9, rotation, sbb);
      this.makeStonePillar(world, forgeDeco, 9, y, 17, rotation, sbb);
      this.setBlockStateRotated(
         world,
         (BlockState)List.of(Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL)
            .get(decoRNG.nextInt(3))
            .defaultBlockState()
            .setValue(AnvilBlock.FACING, Plane.HORIZONTAL.getRandomDirection(decoRNG)),
         13,
         y + 2,
         5,
         rotation,
         sbb
      );
      this.setBlockStateRotated(
         world,
         (BlockState)List.of(Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL)
            .get(decoRNG.nextInt(3))
            .defaultBlockState()
            .setValue(AnvilBlock.FACING, Plane.HORIZONTAL.getRandomDirection(decoRNG)),
         13,
         y + 2,
         13,
         rotation,
         sbb
      );
      this.makeFirePit(world, forgeDeco, 6, y + 1, 12, rotation, sbb);
   }

   private void makeFurnacePillar(WorldGenLevel world, RandomSource rand, int x, int y, int z, Direction direction, Rotation rotation, BoundingBox sbb) {
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, direction, true), x, y + 2, z, rotation, sbb);
      this.setBlockStateRotated(
         world, (BlockState)Blocks.FURNACE.defaultBlockState().setValue(AbstractFurnaceBlock.FACING, direction.getOpposite()), x, y + 3, z, rotation, sbb
      );
      int amount = rand.nextBoolean() ? rand.nextInt(5) + 4 : 0;
      if (amount > 0) {
         int dx = this.getXWithOffsetRotated(x, z, rotation);
         int dy = this.getWorldY(y + 3);
         int dz = this.getZWithOffsetRotated(x, z, rotation);
         BlockPos pos = new BlockPos(dx, dy, dz);
         if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() == Blocks.FURNACE) {
            Container inv = (Container)world.getBlockEntity(pos);
            inv.setItem(1, new ItemStack(Items.CHARCOAL, amount));
         }
      }

      this.setBlockStateRotated(world, getStairState(this.deco.stairState, direction, false), x, y + 4, z, rotation, sbb);
   }

   private void makeStonePillar(WorldGenLevel world, TFStructureDecorator forgeDeco, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      for (int py = 1; py <= 4; py++) {
         this.setBlockStateRotated(world, forgeDeco.pillarState, x, y + py, z, rotation, sbb);
      }
   }

   private void makeFirePit(WorldGenLevel world, TFStructureDecorator myDeco, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, false), x - 1, y, z, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, false), x + 1, y, z, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), x, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, false), x, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, myDeco.pillarState, x + 1, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, myDeco.pillarState, x + 1, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, myDeco.pillarState, x - 1, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, myDeco.pillarState, x - 1, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, Blocks.NETHERRACK.defaultBlockState(), x, y, z, rotation, sbb);
      this.setBlockStateRotated(world, Blocks.FIRE.defaultBlockState(), x, y + 1, z, rotation, sbb);
   }

   private void decorateNetherwart(WorldGenLevel world, RandomSource decoRNG, BoundingBox sbb, Rotation rotation, int y, boolean isTop) {
      TFStructureDecorator netherDeco = this.deco;
      this.makePillarFrame(world, sbb, netherDeco, rotation, 12, y, 9, 4, 4, 7, true);
      this.fillBlocksRotated(world, sbb, 13, y + 1, 10, 14, y + 1, 14, Blocks.SOUL_SAND.defaultBlockState(), rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 2, 10, 14, y + 2, 14, this.getNetherPlant(decoRNG), rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 4, 10, 14, y + 4, 14, Blocks.SOUL_SAND.defaultBlockState(), rotation);
      this.makePillarFrame(world, sbb, netherDeco, rotation, 5, y, 12, 3, isTop ? 4 : 9, 3, true);
      this.setBlockStateRotated(world, netherDeco.blockState, 6, y + 1, 13, rotation, sbb);
      this.setBlockStateRotated(world, netherDeco.blockState, 6, y + (isTop ? 4 : 9), 13, rotation, sbb);
      this.setSpawnerRotated(world, 6, y + 3, 13, rotation, EntityType.BLAZE, sbb);
      this.destroyTower(world, decoRNG, 12, y, 3, 2, sbb);
   }

   private void decorateBotanical(WorldGenLevel world, ChunkGenerator generator, RandomSource decoRNG, BoundingBox sbb, Rotation rotation, int y) {
      this.makePillarFrame(world, sbb, this.deco, rotation, 12, y, 12, 4, 4, 4, true);
      this.fillBlocksRotated(world, sbb, 13, y + 1, 13, 14, y + 1, 14, this.deco.blockState, rotation);
      this.fillBlocksRotated(world, sbb, 13, y + 4, 13, 14, y + 4, 14, this.deco.blockState, rotation);
      this.placeRandomPlant(world, decoRNG, 13, y + 2, 13, rotation, sbb);
      this.placeRandomPlant(world, decoRNG, 13, y + 2, 14, rotation, sbb);
      this.placeRandomPlant(world, decoRNG, 14, y + 2, 13, rotation, sbb);
      this.placeRandomPlant(world, decoRNG, 14, y + 2, 14, rotation, sbb);

      for (int py = 1; py <= 4; py++) {
         this.setBlockStateRotated(world, this.deco.pillarState, 12, y + py, 4, rotation, sbb);
         this.setBlockStateRotated(world, this.deco.pillarState, 15, y + py, 4, rotation, sbb);
      }

      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, true), 13, y + 1, 4, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, true), 14, y + 1, 4, rotation, sbb);
      this.placeTreasureRotated(world, 13, y + 2, 4, this.getOrientation(), rotation, TFLootTables.DARKTOWER_CACHE, sbb);
      this.setBlockStateRotated(world, Blocks.CRAFTING_TABLE.defaultBlockState(), 14, y + 2, 4, rotation, sbb);
      BlockState slab = (BlockState)Blocks.SPRUCE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, true), 12, y + 1, 7, rotation, sbb);
      this.setBlockStateRotated(world, slab, 13, y + 1, 7, rotation, sbb);
      this.setBlockStateRotated(world, slab, 14, y + 1, 7, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, true), 15, y + 1, 7, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, true), 12, y + 1, 10, rotation, sbb);
      this.setBlockStateRotated(world, slab, 13, y + 1, 10, rotation, sbb);
      this.setBlockStateRotated(world, slab, 14, y + 1, 10, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, true), 15, y + 1, 10, rotation, sbb);

      for (int x = 12; x <= 15; x++) {
         this.placeRandomPlant(world, decoRNG, x, y + 2, 7, rotation, sbb);
         this.placeRandomPlant(world, decoRNG, x, y + 2, 10, rotation, sbb);
      }

      this.placeTreePlanter(world, generator, decoRNG.nextInt(5), 6, y + 1, 12, rotation, sbb);
   }

   private void placeTreePlanter(WorldGenLevel world, ChunkGenerator generator, int treeNum, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      this.setBlockStateRotated(world, this.deco.pillarState, x + 1, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.pillarState, x + 1, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.pillarState, x - 1, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.pillarState, x - 1, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.WEST, false), x - 1, y, z, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.EAST, false), x + 1, y, z, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.SOUTH, false), x, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, getStairState(this.deco.stairState, Direction.NORTH, false), x, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, Blocks.DIRT.defaultBlockState(), x, y, z, rotation, sbb);
      int dx = this.getXWithOffsetRotated(x, z, rotation);
      int dy = this.getWorldY(y + 1);
      int dz = this.getZWithOffsetRotated(x, z, rotation);
      if (sbb.isInside(new BlockPos(dx, dy, dz))) {
         ResourceKey<ConfiguredFeature<?, ?>> treeGen = switch (treeNum) {
            case 1 -> TFConfiguredFeatures.SMALLER_JUNGLE_TREE;
            case 2 -> TreeFeatures.BIRCH;
            case 3 -> TFConfiguredFeatures.TWILIGHT_OAK_TREE;
            case 4 -> TFConfiguredFeatures.RAINBOW_OAK_TREE;
            default -> TreeFeatures.OAK;
         };
         int i = 0;

         while (
            i < 100
               && !((ConfiguredFeature)world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(treeGen))
                  .place(world, generator, world.getRandom(), new BlockPos(dx, dy, dz))
         ) {
            i++;
         }
      }
   }

   private void placeRandomPlant(WorldGenLevel world, RandomSource decoRNG, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      Optional<Block> optional = BuiltInRegistries.BLOCK
         .getTag(BlockTagGenerator.DARK_TOWER_ALLOWED_POTS)
         .flatMap(tag -> tag.getRandomElement(decoRNG))
         .map(Holder::value);
      this.setBlockStateRotated(
         world,
         decoRNG.nextInt(10) != 0 && optional.isPresent() ? optional.get().defaultBlockState() : Blocks.FLOWER_POT.defaultBlockState(),
         x,
         y,
         z,
         rotation,
         sbb
      );
   }

   private void makeBottomEntrance(WorldGenLevel world, BoundingBox sbb, Rotation rotation, int y) {
      this.makeFirePit(world, this.deco, 13, y + 1, 3, rotation, sbb);
      this.makeFirePit(world, this.deco, 3, y + 1, 13, rotation, sbb);
      this.makeFirePit(world, this.deco, 13, y + 1, 13, rotation, sbb);
      this.makePillarFrame(world, sbb, this.deco, rotation, 7, y, 7, 3, 4, 3, false);
   }

   protected void addTimberMaze(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int bottom, int top) {
      int spacing = 5;
      Rotation floorside = Rotation.NONE;
      if (bottom == 0) {
         bottom += spacing;
      }

      for (int y = bottom; y < top; y += spacing) {
         floorside = floorside.getRotated(Rotation.CLOCKWISE_90);
         this.makeTimberBeams(world, rand, sbb, floorside, y, y == bottom && bottom != spacing, y >= top - spacing, top);
      }
   }

   protected void makeTimberBeams(WorldGenLevel world, RandomSource rand, BoundingBox sbb, Rotation rotation, int y, boolean isBottom, boolean isTop, int top) {
      BlockState beamID = ((RotatedPillarBlock)TFBlocks.TWILIGHT_OAK_LOG.get()).defaultBlockState();
      BlockState beamStateNS = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.Z);
      BlockState beamStateUD = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.Y);
      BlockState beamStateEW = (BlockState)beamID.setValue(RotatedPillarBlock.AXIS, Axis.X);

      for (int z = 1; z < this.size - 1; z++) {
         this.setBlockStateRotated(world, beamStateNS, 4, y, z, rotation, sbb);
         this.setBlockStateRotated(world, beamStateNS, 9, y, z, rotation, sbb);
         this.setBlockStateRotated(world, beamStateNS, 14, y, z, rotation, sbb);
      }

      int z = this.pickBetweenExcluding(3, this.size - 3, rand, 4, 9, 14);

      for (int x = 5; x < 9; x++) {
         this.setBlockStateRotated(world, beamStateEW, x, y, z, rotation, sbb);
      }

      z = this.pickBetweenExcluding(3, this.size - 3, rand, 4, 9, 14);

      for (int x = 10; x < 14; x++) {
         this.setBlockStateRotated(world, beamStateEW, x, y, z, rotation, sbb);
      }

      int x1 = 4;
      int z1 = this.pickFrom(rand, 4, 9, 14);
      int x2 = 9;
      int z2 = this.pickFrom(rand, 4, 9, 14);
      int x3 = 14;
      int z3 = this.pickFrom(rand, 4, 9, 14);

      for (int by = 1; by < 5; by++) {
         if (!isBottom || this.checkPost(world, x1, y - 5, z1, rotation, sbb)) {
            this.setBlockStateRotated(world, beamStateUD, x1, y - by, z1, rotation, sbb);
            this.setBlockStateRotated(
               world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), x1 + 1, y - by, z1, rotation, sbb
            );
         }

         if (!isBottom || this.checkPost(world, x2, y - 5, z2, rotation, sbb)) {
            this.setBlockStateRotated(world, beamStateUD, x2, y - by, z2, rotation, sbb);
         }

         if (!isBottom || this.checkPost(world, x3, y - 5, z3, rotation, sbb)) {
            this.setBlockStateRotated(world, beamStateUD, x3, y - by, z3, rotation, sbb);
            this.setBlockStateRotated(
               world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.EAST), x3 - 1, y - by, z3, rotation, sbb
            );
         }
      }

      if (isTop) {
         Rotation topFloorRotation = RotationUtil.ROTATIONS[(this.boundingBox.minY() + top + 1) % 4];
         int ladderX = 4;
         int ladderZ = 10;

         for (int by = 1; by < 5; by++) {
            this.setBlockStateRotated(world, beamStateUD, ladderX, y + by, 9, topFloorRotation, sbb);
            this.setBlockStateRotated(
               world,
               (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.NORTH),
               ladderX,
               y + by,
               ladderZ,
               topFloorRotation,
               sbb
            );
         }

         this.setBlockStateRotated(world, AIR, ladderX, y + 6, 9, topFloorRotation, sbb);
         this.setBlockStateRotated(world, this.deco.fenceState, ladderX + 1, y + 5, ladderZ, topFloorRotation, sbb);
         this.setBlockStateRotated(world, this.deco.fenceState, ladderX - 1, y + 5, ladderZ, topFloorRotation, sbb);
         this.setBlockStateRotated(world, this.deco.fenceState, ladderX + 1, y + 6, ladderZ, topFloorRotation, sbb);
         this.setBlockStateRotated(world, this.deco.fenceState, ladderX - 1, y + 6, ladderZ, topFloorRotation, sbb);
      }

      if (!isBottom && !isTop) {
         int sx = this.pickFrom(rand, 6, 7, 11);
         int sz = this.pickFrom(rand, 6, 11, 12);
         this.makeMiniGhastSpawner(world, y, sx, sz, sbb);
      }

      int lx = this.pickFrom(rand, 2, 12, 16);
      int lz = 2 + rand.nextInt(15);
      this.setBlockStateRotated(world, Blocks.REDSTONE_LAMP.defaultBlockState(), lx, y + 2, lz, rotation, sbb);
      this.setBlockStateRotated(
         world,
         getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.CEILING, rand.nextBoolean() ? Direction.EAST : Direction.NORTH, false),
         lx,
         y + 1,
         lz,
         rotation,
         sbb
      );
   }

   private void makeMiniGhastSpawner(WorldGenLevel world, int y, int sx, int sz, BoundingBox sbb) {
      this.setSpawner(world, sx, y + 2, sz, sbb, (EntityType<?>)TFEntities.CARMINITE_GHASTLING.get(), spawner -> {
         BaseSpawner base = spawner.getSpawner();
         base.spawnRange = 16;
         base.maxNearbyEntities = 2;
         base.spawnCount = 1;
      });
   }

   protected void addBuilderPlatforms(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int bottom, int top) {
      int spacing = 5;
      Rotation floorside = Rotation.NONE;
      if (bottom == 0) {
         bottom += spacing;
      }

      for (int y = bottom; y < top - spacing; y += spacing) {
         this.makeBuilderPlatforms(world, rand, sbb, floorside, y);
         floorside = floorside.getRotated(Rotation.CLOCKWISE_90);
         floorside = floorside.getRotated(RotationUtil.ROTATIONS[rand.nextInt(3)]);
      }

      this.makeBuilderPlatform(world, rand, Rotation.CLOCKWISE_90, bottom, 5, true, sbb);
      this.makeBuilderPlatform(world, rand, Rotation.COUNTERCLOCKWISE_90, bottom, 5, true, sbb);

      for (int y = bottom - 4; y < bottom; y++) {
         this.setBlockStateRotated(
            world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), 1, y, 5, Rotation.CLOCKWISE_90, sbb
         );
         this.setBlockStateRotated(
            world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), 1, y, 5, Rotation.COUNTERCLOCKWISE_90, sbb
         );
      }

      this.addTopBuilderPlatform(world, rand, top, spacing, sbb);
   }

   protected void makeBuilderPlatforms(WorldGenLevel world, RandomSource rand, BoundingBox sbb, Rotation rotation, int y) {
      int z = this.size / 2 + rand.nextInt(5) - rand.nextInt(5);
      this.makeBuilderPlatform(world, rand, rotation, y, z, false, sbb);
      this.setBlockStateRotated(world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), 1, y + 1, z, rotation, sbb);
      this.setBlockStateRotated(world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), 1, y + 2, z, rotation, sbb);
      this.setBlockStateRotated(world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), 1, y + 3, z, rotation, sbb);
      this.setBlockStateRotated(world, (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.WEST), 1, y + 4, z, rotation, sbb);
      this.makeBuilderPlatform(world, rand, rotation, y + 5, z, true, sbb);
      if (y % 2 == 1) {
         int sx = this.pickFrom(rand, 5, 9, 13);
         int sz = sx == 9 ? (rand.nextBoolean() ? 5 : 13) : 9;
         BlockState antibuilder = ((Block)TFBlocks.ANTIBUILDER.get()).defaultBlockState();
         this.setBlockStateRotated(world, antibuilder, sx, y + 2, sz, rotation, sbb);
      } else {
         int sx = rand.nextBoolean() ? 5 : 13;
         int sz = rand.nextBoolean() ? 5 : 13;
         this.makeLampCluster(world, rand, sx, y, sz, rotation, sbb);
      }
   }

   private void addTopBuilderPlatform(WorldGenLevel world, RandomSource rand, int top, int spacing, BoundingBox sbb) {
      Rotation rotation = RotationUtil.ROTATIONS[(this.boundingBox.minY() + top + 1) % 4];
      this.fillBlocksRotated(world, sbb, 5, top - spacing, 9, 7, top - spacing, 11, this.deco.accentState, rotation);
      this.fillBlocksRotated(world, sbb, 6, top - spacing, 9, 6, top, 9, this.deco.accentState, rotation);
      this.fillBlocksRotated(
         world,
         sbb,
         6,
         top - spacing + 1,
         10,
         6,
         top - 1,
         10,
         (BlockState)Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.NORTH),
         rotation
      );
      this.setBlockStateRotated(world, AIR, 6, top + 1, 9, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.fenceState, 5, top, 10, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.fenceState, 7, top, 10, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.fenceState, 5, top + 1, 10, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.fenceState, 7, top + 1, 10, rotation, sbb);
      BlockState inactiveBuilder = ((Block)TFBlocks.CARMINITE_BUILDER.get()).defaultBlockState();
      this.setBlockStateRotated(world, inactiveBuilder, 7, top - spacing, 10, rotation, sbb);
      this.setBlockStateRotated(
         world,
         getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.FLOOR, rand.nextBoolean() ? Direction.EAST : Direction.NORTH, false),
         7,
         top - spacing + 1,
         11,
         rotation,
         sbb
      );
   }

   private void makeBuilderPlatform(WorldGenLevel world, RandomSource rand, Rotation rotation, int y, int z, boolean hole, BoundingBox sbb) {
      this.setBlockStateRotated(world, this.deco.accentState, 1, y, z - 1, rotation, sbb);
      if (!hole) {
         this.setBlockStateRotated(world, this.deco.accentState, 1, y, z, rotation, sbb);
      }

      this.setBlockStateRotated(world, this.deco.accentState, 1, y, z + 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, 2, y, z - 1, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, 2, y, z, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.accentState, 2, y, z + 1, rotation, sbb);
      BlockState inactiveBuilder = ((Block)TFBlocks.CARMINITE_BUILDER.get()).defaultBlockState();
      this.setBlockStateRotated(world, inactiveBuilder, 2, y, hole ? z + 1 : z - 1, rotation, sbb);
      this.setBlockStateRotated(
         world,
         getLeverState(Blocks.LEVER.defaultBlockState(), AttachFace.FLOOR, rand.nextBoolean() ? Direction.EAST : Direction.NORTH, false),
         2,
         y + 1,
         z,
         rotation,
         sbb
      );
   }

   private void makeLampCluster(WorldGenLevel world, RandomSource rand, int sx, int y, int sz, Rotation rotation, BoundingBox sbb) {
      int radius = 4;

      for (int i = 0; i < 5; i++) {
         int lx = sx;
         int ly = y;
         int lz = sz;

         for (int move = 0; move < 10; move++) {
            this.setBlockStateRotated(world, Blocks.REDSTONE_LAMP.defaultBlockState(), lx, ly, lz, rotation, sbb);
            int direction = rand.nextInt(8);
            if (direction > 5) {
               direction -= 2;
            }

            Direction facing = Direction.values()[direction];
            lx += facing.getStepX();
            ly += facing.getStepY();
            lz += facing.getStepZ();
            if (lx > sx + radius || lx < sx - radius || ly > y + radius || ly < y - radius || lz > sz + radius || lz < sz - radius) {
               break;
            }
         }
      }

      for (int i = 0; i < 5; i++) {
         int lx = sx;
         int ly = y;
         int lz = sz;
         Direction[] directions = new Direction[10];

         for (int move = 0; move < 10; move++) {
            int direction = rand.nextInt(8);
            if (direction > 5) {
               direction -= 2;
            }

            directions[move] = Direction.values()[direction];
         }

         for (int move = 0; move < 10; move++) {
            Direction direction = directions[move];
            lx += direction.getStepX();
            ly += direction.getStepY();
            lz += direction.getStepZ();
            if (lx > sx + radius || lx < sx - radius || ly > y + radius || ly < y - radius || lz > sz + radius || lz < sz - radius) {
               break;
            }

            if (this.getBlockStateFromPosRotated(world, lx, ly, lz, sbb, rotation).getBlock() != Blocks.REDSTONE_LAMP) {
               AttachFace face;
               Direction orientation;
               switch (direction) {
                  case NORTH:
                     face = AttachFace.WALL;
                     orientation = Direction.SOUTH;
                     break;
                  case SOUTH:
                     face = AttachFace.WALL;
                     orientation = Direction.NORTH;
                     break;
                  case EAST:
                     face = AttachFace.WALL;
                     orientation = Direction.WEST;
                     break;
                  case WEST:
                     face = AttachFace.WALL;
                     orientation = Direction.EAST;
                     break;
                  case UP:
                     face = AttachFace.FLOOR;
                     orientation = Direction.EAST;
                     break;
                  default:
                     face = AttachFace.CEILING;
                     orientation = Direction.NORTH;
               }

               this.setBlockStateRotated(world, getLeverState(Blocks.LEVER.defaultBlockState(), face, orientation, false), lx, ly, lz, rotation, sbb);
               break;
            }
         }
      }
   }

   private BlockState getNetherPlant(RandomSource random) {
      List<BlockState> blocks = List.of(
         Blocks.NETHER_WART.defaultBlockState(),
         ((Block)TFBlocks.BLIGHTBERRY_BUSH.get()).defaultBlockState(),
         ((Block)TFBlocks.DUSKBERRY_BUSH.get()).defaultBlockState(),
         ((Block)TFBlocks.SKYBERRY_BUSH.get()).defaultBlockState(),
         ((Block)TFBlocks.STINGBERRY_BUSH.get()).defaultBlockState()
      );
      return (BlockState)Util.getRandom(blocks, random);
   }
}
