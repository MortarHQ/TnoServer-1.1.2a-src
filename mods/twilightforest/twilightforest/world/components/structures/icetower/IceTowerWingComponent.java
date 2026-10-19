package twilightforest.world.components.structures.icetower;

import com.mojang.datafixers.util.Pair;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.RotationUtil;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.UtilityPiece;
import twilightforest.world.components.structures.icetower.floordecorators.FloorParts;
import twilightforest.world.components.structures.icetower.floordecorators.FloorTypesAuroraPalace;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class IceTowerWingComponent extends TowerWingComponent {
   protected static final int SIZE = 11;
   private static final int RANGE = 27;
   private static final int FLOOR_PLAN_MAX_TRIES = 10000;
   boolean hasBase = false;
   protected int treasureFloor = -1;

   public IceTowerWingComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFITWin.get(), nbt);
   }

   public IceTowerWingComponent(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.hasBase = nbt.getBoolean("hasBase");
      this.treasureFloor = nbt.getInt("treasureFloor");
   }

   protected IceTowerWingComponent(StructurePieceType piece, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super(piece, i, x, y, z, pSize, pHeight, direction);
      if (this.deco == null) {
         this.deco = new IceTowerDecorator();
      }
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putBoolean("hasBase", this.hasBase);
      tagCompound.putInt("treasureFloor", this.treasureFloor);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      this.hasBase = this.shouldHaveBase(list, rand);
      this.makeARoof(parent, list, rand);
      if (!this.hasBase) {
         this.makeABeard(parent, list, rand);
      } else if (list instanceof StructurePiecesBuilder structurePiecesBuilder) {
         UtilityPiece utilityPiece = new UtilityPiece(
            this.getGenDepth() + 1,
            new BoundingBox(
               this.boundingBox.minX(),
               structurePiecesBuilder.pieces.stream().mapToInt(piece -> piece.getBoundingBox().minY()).min().getAsInt(),
               this.boundingBox.minZ(),
               this.boundingBox.maxX(),
               this.boundingBox.minY(),
               this.boundingBox.maxZ()
            )
         );
         list.addPiece(utilityPiece);
      }

      if (this.getGenDepth() < 5) {
         Rotation dirOffset = RotationUtil.ROTATIONS[rand.nextInt(RotationUtil.ROTATIONS.length)];

         for (Rotation rotation : RotationUtil.ROTATIONS) {
            Rotation dir = dirOffset.getRotated(rotation);
            int[] dest = this.getValidOpening(rand, dir);
            if (this.getGenDepth() == 4 && parent instanceof IceTowerMainComponent && !((IceTowerMainComponent)parent).hasBossWing) {
               boolean hasBoss = this.makeBossTowerWing(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], 15, 41, dir);
               ((IceTowerMainComponent)parent).hasBossWing = hasBoss;
            } else {
               int childHeight = (rand.nextInt(2) + rand.nextInt(2) + 2) * 10 + 1;
               this.makeTowerWing(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], 11, childHeight, dir);
            }
         }
      }

      int floors = this.height / 10;
      if (this.treasureFloor == -1 && floors > 1) {
         this.treasureFloor = rand.nextInt(floors - 1);
      }
   }

   protected boolean shouldHaveBase(StructurePieceAccessor list, RandomSource rand) {
      if (list instanceof StructurePiecesBuilder start) {
         return start.pieces.stream().anyMatch(piece -> {
            BoundingBox pieceBox = piece.getBoundingBox();
            BoundingBox box = this.getBoundingBox();
            Rectangle rectanglePiece = new Rectangle(pieceBox.minX(), pieceBox.minZ(), pieceBox.maxX() - pieceBox.minX(), pieceBox.maxZ() - pieceBox.minZ());
            Rectangle rectangle = new Rectangle(box.minX(), box.minZ(), box.maxX() - box.minX(), box.maxZ() - box.minZ());
            return rectangle.intersects(rectanglePiece) && this != piece;
         }) ? false : this.getGenDepth() == 0 || rand.nextBoolean();
      } else {
         return false;
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
      if (list instanceof StructurePiecesBuilder start && (start.pieces.isEmpty() || !this.isOutOfRange((StructurePiece)start.pieces.get(0), dx[0], dx[2], 27))
         )
       {
         IceTowerWingComponent wing = new IceTowerWingComponent(
            (StructurePieceType)TFStructurePieceTypes.TFITWin.get(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction
         );
         BoundingBox sbb = wing.getBoundingBox();
         StructurePiece intersect = start.findCollisionPiece(
            new BoundingBox(
               sbb.minX(), sbb.minY() - Math.round(this.size * 1.414F), sbb.minZ(), sbb.maxX(), sbb.maxY() + Math.round(this.size * 1.414F), sbb.maxZ()
            )
         );
         if ((intersect == null || intersect == this)
            && !start.pieces
               .stream()
               .anyMatch(
                  piece -> {
                     BoundingBox pieceBox = piece.getBoundingBox();
                     BoundingBox box = this.getBoundingBox();
                     Rectangle rectanglePiece = new Rectangle(
                        pieceBox.minX(), pieceBox.minZ(), pieceBox.maxX() - pieceBox.minX(), pieceBox.maxZ() - pieceBox.minZ()
                     );
                     Rectangle rectangle = new Rectangle(box.minX(), box.minZ(), box.maxX() - box.minX(), box.maxZ() - box.minZ());
                     return (piece instanceof IceTowerEntranceComponent || piece instanceof IceTowerBridgeComponent)
                        && rectangle.intersects(rectanglePiece)
                        && this != piece;
                  }
               )) {
            list.addPiece(wing);
            wing.addChildren((StructurePiece)start.pieces.get(0), list, rand);
            this.addOpening(x, y, z, rotation);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean makeBossTowerWing(
      StructurePieceAccessor list, RandomSource rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation
   ) {
      Direction direction = this.getStructureRelativeRotation(rotation);
      int[] dx = this.offsetTowerCoords(x, y, z, wingSize, direction);
      IceTowerWingComponent wing = new IceTowerBossWingComponent(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
      StructurePiece intersect = list.findCollisionPiece(wing.getBoundingBox());
      if (intersect != null && intersect != this) {
         return false;
      }

      list.addPiece(wing);
      if (list instanceof StructurePiecesBuilder start) {
         wing.addChildren((StructurePiece)start.pieces.get(0), list, rand);
      }

      this.addOpening(x, y, z, rotation);
      return true;
   }

   @Override
   protected int getYByStairs(int rx, RandomSource rand, Rotation direction) {
      int floors = this.height / 10;
      return 11 + rand.nextInt(floors - 1) * 10;
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      this.generateBox(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, false, rand, this.deco.randomBlocks);
      this.generateAirBox(world, sbb, 1, 1, 1, this.size - 2, this.height - 2, this.size - 2);
      if (this.hasBase) {
         for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
               this.fillColumnDown(world, this.deco.blockState, x, -1, z, sbb);
            }
         }
      }

      this.makeFloorsForTower(world, decoRNG, sbb);
      this.makeOpenings(world, sbb);
   }

   protected void makeFloorsForTower(WorldGenLevel world, RandomSource decoRNG, BoundingBox sbb) {
      int floorHeight = 10;
      int floors = this.height / floorHeight;
      int topFloor = floors - 1;
      Rotation ladderDir = Rotation.COUNTERCLOCKWISE_90;
      Rotation downLadderDir = ladderDir.getRotated(Rotation.CLOCKWISE_90);
      this.decorateTopFloor(world, decoRNG, topFloor, topFloor * floorHeight, topFloor * floorHeight + floorHeight, ladderDir, downLadderDir, sbb);
      List<Pair<FloorTypesAuroraPalace, Integer>> floorPlan = this.createFloorPlan(floors - 1, decoRNG, sbb);

      for (int i = floors - 2; i >= 0; i--) {
         this.placeFloor(world, decoRNG, sbb, floorHeight, i);
         Pair<FloorTypesAuroraPalace, Integer> floor = floorPlan.get(floors - 2 - i);
         ladderDir = Rotation.COUNTERCLOCKWISE_90;

         for (int t = 0; t < floor.getSecond(); t++) {
            ladderDir = ladderDir.getRotated(Rotation.CLOCKWISE_90);
         }

         downLadderDir = ladderDir.getRotated(Rotation.CLOCKWISE_90);
         if (this instanceof IceTowerBossWingComponent) {
            this.decorateFloor(world, decoRNG, i, i * floorHeight, (i + 1) * floorHeight, ladderDir, downLadderDir, sbb);
         } else {
            this.decorateFloor(
               world, decoRNG, (FloorTypesAuroraPalace)floor.getFirst(), i, i * floorHeight, (i + 1) * floorHeight, ladderDir, downLadderDir, sbb
            );
         }
      }
   }

   private List<Pair<FloorTypesAuroraPalace, Integer>> createFloorPlan(int floorCount, RandomSource decoRNG, BoundingBox sbb) {
      List<Pair<FloorTypesAuroraPalace, Integer>> plan = new ArrayList<>();
      int tries = 0;
      Set<FloorParts> topBlockedPartsInit = new HashSet<>(FloorTypesAuroraPalace.TOP.getFloorWith3x3Map().getBlockedFloorParts());
      Set<FloorParts> doorBlockedPartsInit = this.getPartsBlockedByDoors(floorCount * 10, (floorCount + 1) * 10);
      topBlockedPartsInit.addAll(doorBlockedPartsInit);
      Set<FloorParts> bottomBlockedPartsInit = this.getPartsBlockedByDoors((floorCount - 1) * 10, floorCount * 10);
      Set<FloorParts> topBlockedParts = new HashSet<>(topBlockedPartsInit);
      Set<FloorParts> bottomBlockedParts = new HashSet<>(bottomBlockedPartsInit);

      while (plan.size() < floorCount) {
         if (tries > 10000) {
            topBlockedParts.clear();
            bottomBlockedParts.clear();
            TwilightForestMod.LOGGER
               .error(
                  "Unable to generate floor plan for aurora palace at {}. Please report mod version, coords, and seed to Twilight Forest devs", sbb.getCenter()
               );
         }

         Set<FloorParts> finalTopBlockedParts = topBlockedParts;
         Set<FloorParts> finalBottomBlockedParts = bottomBlockedParts;
         Map<FloorTypesAuroraPalace, List<Integer>> possibleFloors = Arrays.stream(FloorTypesAuroraPalace.values())
            .collect(
               Collectors.toMap(
                  floorType -> (FloorTypesAuroraPalace)floorType,
                  floorType -> this.getAllowedRotations(floorType, finalTopBlockedParts, finalBottomBlockedParts)
               )
            );
         possibleFloors.entrySet().removeIf(entry -> entry.getValue().isEmpty());
         possibleFloors.keySet().remove(FloorTypesAuroraPalace.TOP);
         if (possibleFloors.isEmpty()) {
            plan.clear();
            topBlockedParts = topBlockedPartsInit;
            bottomBlockedParts = bottomBlockedPartsInit;
            tries++;
         } else {
            FloorTypesAuroraPalace chosenType = WorldUtil.getRandomElementWithWeights(
               possibleFloors.keySet().stream().map(floorType -> new Pair(floorType, floorType.getWeight())).collect(Collectors.toList()), decoRNG
            );
            int chosenRotation = (Integer)Util.getRandom(possibleFloors.get(chosenType), decoRNG);
            plan.add(new Pair(chosenType, chosenRotation));
            topBlockedParts = chosenType.getFloorWith3x3Map()
               .getBlockedFloorParts()
               .stream()
               .map(part -> part.rotateClockwise(chosenRotation))
               .collect(Collectors.toSet());
            Set<FloorParts> doorBlockedParts = this.getPartsBlockedByDoors((floorCount - plan.size()) * 10, (floorCount - plan.size() + 1) * 10);
            topBlockedParts.addAll(doorBlockedParts);
            bottomBlockedParts = this.getPartsBlockedByDoors((floorCount - plan.size() - 1) * 10, (floorCount - plan.size()) * 10);
         }
      }

      return plan;
   }

   private List<Integer> getAllowedRotations(FloorTypesAuroraPalace floorType, Set<FloorParts> topBlockedParts, Set<FloorParts> bottomBlockedParts) {
      return IntStream.range(0, 4)
         .filter(rotation -> this.isRotationAllowed(floorType, rotation, topBlockedParts, bottomBlockedParts))
         .boxed()
         .collect(Collectors.toList());
   }

   private boolean isRotationAllowed(FloorTypesAuroraPalace floorType, int rotation, Set<FloorParts> topBlockedParts, Set<FloorParts> bottomBlockedParts) {
      Set<FloorParts> requiredParts = floorType.getFloorWith3x3Map()
         .getRequiredFloorParts()
         .stream()
         .map(part -> part.rotateClockwise(rotation))
         .collect(Collectors.toSet());
      Set<FloorParts> rotatedBlockedParts = floorType.getFloorWith3x3Map()
         .getBlockedFloorParts()
         .stream()
         .map(part -> part.rotateClockwise(rotation))
         .collect(Collectors.toSet());
      return Collections.disjoint(requiredParts, topBlockedParts) && Collections.disjoint(rotatedBlockedParts, new HashSet<>(bottomBlockedParts));
   }

   private Direction getChestDirection(Rotation rotation) {
      return rotation.rotate(this.getOrientation());
   }

   protected void placeFloor(WorldGenLevel world, RandomSource rand, BoundingBox sbb, int floorHeight, int floor) {
      for (int x = 1; x < this.size - 1; x++) {
         for (int z = 1; z < this.size - 1; z++) {
            this.placeBlock(world, this.deco.floorState, x, floor * floorHeight + floorHeight, z, sbb);
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

   protected void decorateFloor(
      WorldGenLevel world,
      RandomSource rand,
      FloorTypesAuroraPalace floorType,
      int floor,
      int bottom,
      int top,
      Rotation ladderUpDir,
      Rotation ladderDownDir,
      BoundingBox sbb
   ) {
      boolean hasTreasure = this.treasureFloor == floor;
      switch (floorType) {
         case WRAPAROUND_WALL_STEPS:
            this.decorateWraparoundWallSteps(world, bottom, top, ladderUpDir, hasTreasure, sbb);
            break;
         case FAR_WALL_STEPS:
            this.decorateFarWallSteps(world, bottom, top, ladderUpDir, hasTreasure, sbb);
            break;
         case WRAPAROUND_WALL_STEPS_PILLARS:
            this.decorateWraparoundWallStepsPillars(world, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
            break;
         case PLATFORM:
            this.decoratePlatform(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
            break;
         case PILLAR_PARKOUR:
            this.decoratePillarParkour(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
            break;
         case PILLAR_PLATFORMS:
            this.decoratePillarPlatforms(world, bottom, top, ladderUpDir, hasTreasure, sbb);
            break;
         case PILLAR_PLATFORMS_OUTSIDE:
            this.decoratePillarPlatformsOutside(world, bottom, top, ladderUpDir, hasTreasure, sbb);
            break;
         case QUAD_PILLAR_STAIRS:
            this.decorateQuadPillarStairs(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
      }
   }

   private boolean isNoDoorAreaRotated(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Rotation rotation) {
      boolean isClear = true;

      BoundingBox exclusionBox = switch (rotation) {
         case CLOCKWISE_90 -> new BoundingBox(this.size - 1 - maxZ, minY, minX, this.size - 1 - minZ, maxY, maxX);
         case CLOCKWISE_180 -> new BoundingBox(this.size - 1 - maxX, minY, this.size - 1 - maxZ, this.size - 1 - minX, maxY, this.size - 1 - minZ);
         case COUNTERCLOCKWISE_90 -> new BoundingBox(minZ, minY, this.size - 1 - maxX, maxZ, maxY, this.size - 1 - minX);
         default -> new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
      };

      for (BlockPos door : this.openings) {
         if (exclusionBox.isInside(door)) {
            isClear = false;
         }
      }

      return isClear;
   }

   private Set<FloorParts> getPartsBlockedByDoors(int bottom, int top) {
      List<FloorParts> parts = List.of(
         FloorParts.LEFT_BACK,
         FloorParts.LEFT,
         FloorParts.LEFT_FRONT,
         FloorParts.BACK,
         FloorParts.FRONT,
         FloorParts.RIGHT_BACK,
         FloorParts.RIGHT,
         FloorParts.RIGHT_FRONT
      );
      List<int[]> coordinates = List.of(
         new int[]{0, 0, 3, 3},
         new int[]{4, 0, 6, 3},
         new int[]{7, 0, 10, 3},
         new int[]{0, 4, 3, 6},
         new int[]{7, 4, 10, 6},
         new int[]{0, 7, 3, 10},
         new int[]{4, 7, 6, 10},
         new int[]{7, 7, 10, 10}
      );
      return parts.stream()
         .filter(
            part -> !this.isNoDoorAreaRotated(
               coordinates.get(parts.indexOf(part))[0],
               bottom,
               coordinates.get(parts.indexOf(part))[1],
               coordinates.get(parts.indexOf(part))[2],
               top,
               coordinates.get(parts.indexOf(part))[3],
               Rotation.NONE
            )
         )
         .collect(Collectors.toSet());
   }

   protected void decorateTopFloor(
      WorldGenLevel world, RandomSource rand, int floor, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, BoundingBox sbb
   ) {
      if (rand.nextBoolean()) {
         this.decoratePillarsCorners(world, rand, bottom, top, ladderDownDir, sbb);
      } else {
         this.decoratePillarsGrid(world, rand, bottom, top, ladderDownDir, sbb);
      }

      if (this.isDeadEnd()) {
         this.decorateTopFloorTreasure(world, bottom, ladderUpDir, sbb);
      }
   }

   private void decorateTopFloorTreasure(WorldGenLevel world, int bottom, Rotation rotation, BoundingBox sbb) {
      this.fillBlocksRotated(world, sbb, 5, bottom + 1, 5, 5, bottom + 4, 5, this.deco.pillarState, rotation);
      this.placeTreasureRotated(world, 5, bottom + 5, 5, this.getChestDirection(rotation), rotation, TFLootTables.AURORA_ROOM, false, sbb);
   }

   private void decoratePillars(WorldGenLevel world, int bottom, int top, Rotation rotation, BoundingBox sbb) {
      this.fillBlocksRotated(world, sbb, 3, bottom + 1, 3, 3, top - 1, 3, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 1, 3, 7, top - 1, 3, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 3, bottom + 1, 7, 3, top - 1, 7, this.deco.pillarState, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 1, 7, 7, top - 1, 7, this.deco.pillarState, rotation);
   }

   private void decoratePillarsGrid(WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation rotation, BoundingBox sbb) {
      BlockState pillarEW = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.Z);
      BlockState pillarNS = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.X);
      this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 9, bottom + 5, 3, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 5, 7, 9, bottom + 5, 7, pillarNS, rotation);
      this.decoratePillars(world, bottom, top, rotation, sbb);
   }

   private void decoratePillarsCorners(WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation rotation, BoundingBox sbb) {
      BlockState pillarEW = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.Z);
      BlockState pillarNS = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.X);
      this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 9, bottom + 5, 3, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 5, 7, 9, bottom + 5, 7, pillarNS, rotation);
      this.fillAirRotated(world, sbb, 3, bottom + 5, 3, 7, bottom + 5, 7, rotation);
      this.decoratePillars(world, bottom, top, rotation, sbb);
   }

   private void decorateFarWallSteps(WorldGenLevel world, int bottom, int top, Rotation ladderUpDir, boolean hasTreasure, BoundingBox sbb) {
      for (int z = 1; z < 10; z++) {
         int y = bottom + 10 - z / 2;
         this.setBlockStateRotated(world, z % 2 == 0 ? this.deco.pillarState : this.deco.platformState, 9, y, z, ladderUpDir, sbb);

         for (int by = bottom + 1; by < y; by++) {
            this.setBlockStateRotated(world, this.deco.pillarState, 9, by, z, ladderUpDir, sbb);
         }
      }

      for (int z = 1; z < 10; z++) {
         int y = bottom + 1 + z / 2;
         this.setBlockStateRotated(world, z % 2 == 0 ? this.deco.platformState : this.deco.pillarState, 8, y, z, ladderUpDir, sbb);

         for (int by = bottom + 1; by < y; by++) {
            this.setBlockStateRotated(world, this.deco.pillarState, 8, by, z, ladderUpDir, sbb);
         }
      }

      this.setBlockStateRotated(world, this.deco.platformState, 7, bottom + 1, 1, ladderUpDir, sbb);

      for (int z = 2; z < 7; z++) {
         this.setBlockStateRotated(world, AIR, 9, top, z, ladderUpDir, sbb);
      }

      BlockState pillarNS = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.X);
      if (hasTreasure) {
         this.placeTreasureRotated(
            world, 1, bottom + 8, 5, this.getChestDirection(ladderUpDir).getCounterClockWise(), ladderUpDir, TFLootTables.AURORA_CACHE, false, sbb
         );
         this.setBlockStateRotated(world, pillarNS, 1, bottom + 7, 5, ladderUpDir, sbb);
      }
   }

   private void decorateWraparoundWallSteps(WorldGenLevel world, int bottom, int top, Rotation ladderUpDir, boolean hasTreasure, BoundingBox sbb) {
      BlockState topPlatform = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.TOP);
      BlockState bottomPlatform = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.BOTTOM);

      for (int z = 1; z < 10; z++) {
         int y = bottom + 10 - z / 2;
         this.setBlockStateRotated(world, z % 2 == 0 ? topPlatform : bottomPlatform, 9, y, z, ladderUpDir, sbb);
      }

      for (int x = 1; x < 9; x++) {
         int y = bottom + 2 + (x - 1) / 2;
         this.setBlockStateRotated(world, x % 2 == 0 ? topPlatform : bottomPlatform, x, y, 9, ladderUpDir, sbb);
      }

      this.setBlockStateRotated(world, topPlatform, 1, bottom + 1, 8, ladderUpDir, sbb);
      this.setBlockStateRotated(world, this.deco.platformState, 1, bottom + 1, 7, ladderUpDir, sbb);

      for (int z = 2; z < 7; z++) {
         this.setBlockStateRotated(world, AIR, 9, top, z, ladderUpDir, sbb);
      }

      if (hasTreasure) {
         this.placeTreasureRotated(
            world, 1, bottom + 5, 5, this.getChestDirection(ladderUpDir).getCounterClockWise(), ladderUpDir, TFLootTables.AURORA_CACHE, false, sbb
         );
         BlockState pillarNS = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.X);
         this.setBlockStateRotated(world, pillarNS, 1, bottom + 4, 5, ladderUpDir, sbb);
      }
   }

   private void decorateWraparoundWallStepsPillars(
      WorldGenLevel world, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, boolean hasTreasure, BoundingBox sbb
   ) {
      Rotation rotation = ladderDownDir;
      BlockState pillarEW = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.Z);
      BlockState pillarNS = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.X);
      this.decorateWraparoundWallSteps(world, bottom, top, ladderUpDir, false, sbb);
      this.decoratePillars(world, bottom, top, rotation, sbb);
      this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 2, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 2, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 8, bottom + 1, 3, 9, bottom + 1, 3, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 8, bottom + 7, 7, 9, bottom + 7, 7, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 2, 3, 2, bottom + 2, 3, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 6, 3, 2, bottom + 6, 3, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 4, 7, 2, bottom + 4, 7, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 8, 7, 2, bottom + 8, 7, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 3, bottom + 6, 8, 3, bottom + 6, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 8, 8, 7, bottom + 8, 9, pillarEW, rotation);
      if (hasTreasure) {
         this.placeTreasureRotated(
            world, 7, bottom + 7, 1, this.getChestDirection(ladderUpDir).getClockWise(), ladderUpDir, TFLootTables.AURORA_CACHE, false, sbb
         );
      }
   }

   private void decoratePlatform(
      WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, boolean hasTreasure, BoundingBox sbb
   ) {
      BlockState topPlatform = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.TOP);
      BlockState bottomPlatform = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.BOTTOM);
      this.decoratePillars(world, bottom, top, ladderDownDir, sbb);
      this.fillBlocksRotated(world, sbb, 3, bottom + 5, 3, 7, bottom + 5, 7, this.deco.floorState, ladderDownDir);

      for (int z = 6; z < 10; z++) {
         int y = bottom - 2 + z / 2;
         this.setBlockStateRotated(world, z % 2 == 1 ? topPlatform : bottomPlatform, 1, y, z, ladderDownDir, sbb);
      }

      for (int x = 2; x < 6; x++) {
         int y = bottom + 2 + x / 2;
         this.setBlockStateRotated(world, x % 2 == 1 ? topPlatform : bottomPlatform, x, y, 9, ladderDownDir, sbb);
      }

      this.setBlockStateRotated(world, this.deco.platformState, 5, bottom + 5, 8, ladderDownDir, sbb);
      this.setBlockStateRotated(world, this.deco.platformState, 5, bottom + 6, 2, ladderUpDir, sbb);

      for (int x = 5; x < 10; x++) {
         int y = bottom + 4 + x / 2;
         this.setBlockStateRotated(world, x % 2 == 1 ? topPlatform : bottomPlatform, x, y, 1, ladderUpDir, sbb);
         if (x > 6) {
            this.setBlockStateRotated(world, AIR, x, top, 1, ladderUpDir, sbb);
         }
      }

      for (int z = 2; z < 5; z++) {
         int y = bottom + 8 + z / 2;
         this.setBlockStateRotated(world, AIR, 9, top, z, ladderUpDir, sbb);
         this.setBlockStateRotated(world, z % 2 == 1 ? topPlatform : bottomPlatform, 9, y, z, ladderUpDir, sbb);
      }

      if (hasTreasure) {
         this.placeTreasureRotated(
            world, 5, bottom + 6, 5, this.getChestDirection(ladderUpDir).getClockWise(), ladderDownDir, TFLootTables.AURORA_CACHE, false, sbb
         );
      }
   }

   private void decorateQuadPillarStairs(
      WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, boolean hasTreasure, BoundingBox sbb
   ) {
      this.decoratePillars(world, bottom, top, ladderDownDir, sbb);
      BlockState topPlatform = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.TOP);
      BlockState bottomPlatform = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.BOTTOM);

      for (int z = 6; z < 9; z++) {
         int y = bottom - 2 + z / 2;
         this.setBlockStateRotated(world, z % 2 == 1 ? topPlatform : bottomPlatform, 2, y, z, ladderDownDir, sbb);
      }

      for (int x = 3; x < 9; x++) {
         int y = bottom + 1 + x / 2;
         this.setBlockStateRotated(world, x % 2 == 1 ? topPlatform : bottomPlatform, x, y, 8, ladderDownDir, sbb);
      }

      for (int z = 7; z > 1; z--) {
         int y = top - 2 - (z - 1) / 2;
         if (z < 4) {
            this.setBlockStateRotated(world, AIR, 8, top, z, ladderDownDir, sbb);
         }

         this.setBlockStateRotated(world, z % 2 == 1 ? topPlatform : bottomPlatform, 8, y, z, ladderDownDir, sbb);
      }

      for (int x = 7; x > 3; x--) {
         int y = top + 1 - (x - 1) / 2;
         this.setBlockStateRotated(world, AIR, x, top, 2, ladderDownDir, sbb);
         this.setBlockStateRotated(world, x % 2 == 1 ? topPlatform : bottomPlatform, x, y, 2, ladderDownDir, sbb);
      }

      if (hasTreasure) {
         int treasureX = 3;
         int treasureY = bottom + 7;
         int treasureZ = 5;
         this.placeTreasureRotated(
            world, treasureX, treasureY, treasureZ, this.getChestDirection(ladderUpDir).getClockWise(), ladderUpDir, TFLootTables.AURORA_CACHE, false, sbb
         );
         Axis axis = this.getChestDirection(ladderUpDir).getClockWise().getAxis();
         this.fillBlocksRotated(
            world,
            sbb,
            treasureX,
            treasureY - 1,
            treasureZ - 1,
            treasureX,
            treasureY - 1,
            treasureZ + 1,
            (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, axis),
            ladderUpDir
         );
      }
   }

   private void decoratePillarPlatforms(WorldGenLevel world, int bottom, int top, Rotation ladderUpDir, boolean hasTreasure, BoundingBox sbb) {
      this.fillAirRotated(world, sbb, 2, top, 2, 8, top, 4, ladderUpDir);
      this.fillAirRotated(world, sbb, 2, top, 2, 4, top, 6, ladderUpDir);
      this.setBlockStateRotated(world, this.deco.platformState, 7, top, 3, ladderUpDir, sbb);
      this.setBlockStateRotated(world, this.deco.platformState, 3, top, 3, ladderUpDir, sbb);

      for (int y = 1; y < 10; y++) {
         int x = (y + 1) % 4 < 2 ? 3 : 7;
         int z = y % 4 < 2 ? 3 : 7;
         this.fillBlocksRotated(world, sbb, x - 1, bottom + y, z - 1, x + 1, bottom + y, z + 1, this.deco.floorState, ladderUpDir);
      }

      this.decoratePillars(world, bottom, top, ladderUpDir, sbb);
      if (hasTreasure) {
         this.placeTreasureRotated(
            world, 3, bottom + 5, 2, this.getChestDirection(ladderUpDir).getCounterClockWise(), ladderUpDir, TFLootTables.AURORA_CACHE, false, sbb
         );
      }
   }

   private void decoratePillarPlatformsOutside(WorldGenLevel world, int bottom, int top, Rotation ladderUpDir, boolean hasTreasure, BoundingBox sbb) {
      for (Rotation r : RotationUtil.ROTATIONS) {
         Rotation rotation = ladderUpDir.getRotated(r);
         this.fillBlocksRotated(world, sbb, 1, bottom, 1, 6, bottom, 3, this.deco.floorState, rotation);
         if (r != Rotation.COUNTERCLOCKWISE_90) {
            this.fillBlocksRotated(world, sbb, 1, bottom + 1, 1, 3, bottom + 1, 3, this.deco.platformState, rotation);
         }
      }

      this.buildStairway(world, bottom, top, ladderUpDir, sbb);
      this.decoratePillars(world, bottom, top, ladderUpDir, sbb);
      if (hasTreasure) {
         int treasureX = 3;
         int treasureY = bottom + 5;
         int treasureZ = 2;
         this.placeTreasureRotated(
            world,
            treasureX,
            treasureY,
            treasureZ,
            this.getChestDirection(ladderUpDir).getCounterClockWise(),
            ladderUpDir,
            TFLootTables.AURORA_CACHE,
            false,
            sbb
         );
         Axis axis = this.getChestDirection(ladderUpDir).getClockWise().getAxis();
         this.fillBlocksRotated(
            world,
            sbb,
            treasureX,
            treasureY - 1,
            treasureZ - 1,
            treasureX,
            treasureY - 1,
            treasureZ,
            (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, axis),
            ladderUpDir
         );
      }
   }

   public void buildStairway(WorldGenLevel world, int bottom, int top, Rotation ladderUpDir, BoundingBox sbb) {
      Rotation rotation = ladderUpDir.getRotated(Rotation.CLOCKWISE_180);
      this.fillAirRotated(world, sbb, 8, top, 1, 9, top + 2, 9, rotation);
      BlockState slabTypeTop = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.TOP);
      BlockState slabTypeBottom = (BlockState)this.deco.platformState.setValue(SlabBlock.TYPE, SlabType.BOTTOM);

      for (int step = 0; step < 22; step++) {
         BlockState slab = step % 2 == 0 ? slabTypeTop : slabTypeBottom;
         int firstDirection = Math.min(step, 7);
         int secondDirection = Math.min(step - firstDirection, 8);
         int thirdDirection = Math.min(step - firstDirection - secondDirection, 8);
         int firstWidthAdjustment = firstDirection < 7 ? 1 : 0;
         int secondWidthAdjustment = secondDirection > 0 && secondDirection < 7 ? 1 : 0;
         int thirdWidthAdjustment = thirdDirection > 0 ? 1 : 0;
         int heightAdjustment = (secondDirection > 0 ? 1 : 0) + (thirdDirection > 0 ? 1 : 0);
         this.fillBlocksRotated(
            world,
            sbb,
            9 - secondDirection - firstWidthAdjustment,
            top - step / 2 + heightAdjustment,
            8 - firstDirection + thirdDirection,
            9 - secondDirection + thirdWidthAdjustment,
            top - step / 2 + heightAdjustment,
            8 - firstDirection + thirdDirection + secondWidthAdjustment,
            slab,
            rotation
         );
      }

      this.placeFullBlocks(world, bottom, top, sbb, rotation);
   }

   private void placeFullBlocks(WorldGenLevel world, int bottom, int top, BoundingBox sbb, Rotation rotation) {
      this.fillBlocksRotated(world, sbb, 8, top, 8, 9, top, 9, this.deco.floorState, rotation);
      this.fillBlocksRotated(world, sbb, 8, top - 3, 1, 9, top - 3, 2, this.deco.floorState, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 4, 1, 2, bottom + 4, 2, this.deco.floorState, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 1, 7, 2, bottom + 1, 9, this.deco.floorState, rotation);
   }

   private void decoratePillarParkour(
      WorldGenLevel world, RandomSource rand, int bottom, int top, Rotation ladderUpDir, Rotation ladderDownDir, boolean hasTreasure, BoundingBox sbb
   ) {
      Rotation rotation = ladderDownDir;
      BlockState pillarEW = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.Z);
      BlockState pillarNS = (BlockState)this.deco.pillarState.setValue(RotatedPillarBlock.AXIS, Axis.X);
      this.decoratePillars(world, bottom, top, rotation, sbb);
      this.setBlockStateRotated(world, this.deco.pillarState, 5, bottom + 1, 5, rotation, sbb);
      this.fillBlocksRotated(world, sbb, 5, bottom + 2, 7, 5, bottom + 2, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 3, 7, 2, bottom + 3, 7, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 3, bottom + 3, 8, 3, bottom + 3, 9, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 7, 7, 2, bottom + 7, 7, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 3, bottom + 7, 8, 3, bottom + 7, 9, pillarEW, rotation);
      this.fillAirRotated(world, sbb, 3, bottom + 4, 7, 3, bottom + 6, 7, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 4, 5, 2, bottom + 4, 5, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 2, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 2, bottom + 5, 3, pillarNS, rotation);
      this.fillAirRotated(world, sbb, 3, bottom + 6, 3, 3, bottom + 8, 3, rotation);
      this.fillBlocksRotated(world, sbb, 5, bottom + 6, 1, 5, bottom + 6, 2, pillarEW, rotation);
      this.fillAirRotated(world, sbb, 7, bottom + 8, 3, 7, bottom + 10, 3, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 7, 1, 7, bottom + 7, 2, pillarEW, rotation);
      this.fillBlocksRotated(world, sbb, 8, bottom + 7, 3, 9, bottom + 7, 3, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 8, bottom + 8, 5, 9, bottom + 8, 5, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 8, bottom + 9, 7, 9, bottom + 9, 7, pillarNS, rotation);
      this.fillBlocksRotated(world, sbb, 7, bottom + 9, 8, 7, bottom + 9, 9, pillarEW, rotation);
      this.fillAirRotated(world, sbb, 6, top, 6, 8, top, 8, ladderUpDir);
      this.fillAirRotated(world, sbb, 2, top, 6, 5, top, 8, ladderUpDir);
      if (hasTreasure) {
         this.placeTreasureRotated(
            world, 8, bottom + 8, 7, this.getChestDirection(ladderUpDir).getOpposite(), ladderUpDir, TFLootTables.AURORA_CACHE, false, sbb
         );
      }
   }

   @Override
   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      int index = this.getGenDepth();
      this.tryToFitRoof(
         list,
         rand,
         new IceTowerRoofComponent(index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ())
      );
   }

   @Override
   public void makeABeard(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      int index = this.getGenDepth();
      IceTowerBeardComponent beard = new IceTowerBeardComponent(
         index + 1, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(beard);
      beard.addChildren(this, list, rand);
   }
}
