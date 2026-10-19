package twilightforest.world.components.structures.courtyard;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import twilightforest.TwilightForestMod;
import twilightforest.enums.Diagonals;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponent;

public abstract class StructureMazeGenerator extends TFStructureComponent {
   protected final int[][] maze;
   private final int[][] cornerClipping = new int[4][2];
   private final int widthInCellCount;
   private final int heightInCellCount;
   protected final StructureTemplateManager structureManager;
   protected BoundingBox sizeConstraints;

   public StructureMazeGenerator(StructureTemplateManager structureManager, StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
      this.widthInCellCount = nbt.getInt("mazeWidth");
      this.heightInCellCount = nbt.getInt("mazeHeight");
      this.maze = new int[this.widthInCellCount - 1][this.heightInCellCount - 1];
      ListTag mazeX = nbt.getList("maze", 9);

      for (int x = 0; x < this.widthInCellCount - 1; x++) {
         Tag mazeY = mazeX.get(x);
         if (mazeY instanceof ListTag) {
            for (int y = 0; y < this.heightInCellCount - 1; y++) {
               this.maze[x][y] = ((ListTag)mazeY).getInt(y);
            }
         }
      }

      this.sizeConstraints = BoundingBoxUtils.NBTToBoundingBox(nbt.getCompound("constraints"));
      this.structureManager = structureManager;
   }

   StructureMazeGenerator(
      StructurePieceType type,
      RandomSource rand,
      int i,
      int widthInCellCount,
      int heightInCellCount,
      int x,
      int y,
      int z,
      StructureTemplateManager structureManager
   ) {
      super(type, i, x, y, z);
      this.widthInCellCount = widthInCellCount;
      this.heightInCellCount = heightInCellCount;
      this.structureManager = structureManager;
      this.maze = new int[widthInCellCount - 1][heightInCellCount - 1];
      generateMaze(this.maze, this.cornerClipping, rand, this.widthInCellCount, this.heightInCellCount, 2);
      this.sizeConstraints = this.getBoundingBox();
   }

   public void addChildren(StructurePiece structureComponent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(structureComponent, list, random);
      int offset = 6;
      Rotation[] rotations = Rotation.values();
      this.processInnerWallsAndFloor(structureComponent, list, random, 6, rotations);
      this.processOuterWalls(structureComponent, list, random, 6, rotations);
   }

   private static void generateMaze(
      int[][] maze, int[][] cornerClippings, RandomSource random, int widthInCellCount, int heightInCellCount, int maximumClipping
   ) {
      StructureMazeGenerator.WallFacing[][] rotations = new StructureMazeGenerator.WallFacing[maze.length][maze[0].length];

      for (int x = 0; x < widthInCellCount - 1; x++) {
         for (int y = 0; y < heightInCellCount - 1; y++) {
            rotations[x][y] = StructureMazeGenerator.WallFacing.values()[random.nextInt(StructureMazeGenerator.WallFacing.values().length)];
            maze[x][y] = maze[x][y] | rotations[x][y].BYTE;
         }
      }

      int[][] mazeLocal = (int[][])maze.clone();
      int halfWayPointX = widthInCellCount / 2 - 1;
      int halfWayPointY = heightInCellCount / 2 - 1;

      for (int y = 0; y < heightInCellCount - 1; y++) {
         for (int x = 0; x < widthInCellCount - 1; x++) {
            if (x != halfWayPointX || y != halfWayPointY) {
               if (rotations[x][y] == StructureMazeGenerator.WallFacing.WEST && x > 0) {
                  if (!rotations[x][y].unpackAndTest(maze[x - 1][y])) {
                     maze[x - 1][y] = maze[x - 1][y] | rotations[x][y].OPPOSITE;
                  } else {
                     maze[x][y] = maze[x][y] & rotations[x][y].INVERTED;
                     maze[x - 1][y] = maze[x - 1][y] & rotations[x - 1][y].INVERTED_OPPOSITE;
                  }
               }

               if (rotations[x][y] == StructureMazeGenerator.WallFacing.NORTH && y > 0) {
                  if (!rotations[x][y].unpackAndTest(maze[x][y - 1])) {
                     maze[x][y - 1] = maze[x][y - 1] | rotations[x][y].OPPOSITE;
                  } else {
                     maze[x][y] = maze[x][y] & rotations[x][y].INVERTED;
                     maze[x][y - 1] = maze[x][y - 1] & rotations[x][y - 1].INVERTED_OPPOSITE;
                  }
               }

               if (rotations[x][y] == StructureMazeGenerator.WallFacing.EAST && x < widthInCellCount - 2) {
                  if (!rotations[x][y].unpackAndTest(maze[x + 1][y])) {
                     maze[x + 1][y] = maze[x + 1][y] | rotations[x][y].OPPOSITE;
                  } else {
                     maze[x][y] = maze[x][y] & rotations[x][y].INVERTED;
                     maze[x + 1][y] = maze[x + 1][y] & rotations[x + 1][y].INVERTED_OPPOSITE;
                  }
               }

               if (rotations[x][y] == StructureMazeGenerator.WallFacing.SOUTH && y < heightInCellCount - 2) {
                  if (!rotations[x][y].unpackAndTest(maze[x][y + 1])) {
                     maze[x][y + 1] = maze[x][y + 1] | rotations[x][y].OPPOSITE;
                  } else {
                     maze[x][y] = maze[x][y] & rotations[x][y].INVERTED;
                     maze[x][y + 1] = maze[x][y + 1] & rotations[x][y + 1].INVERTED_OPPOSITE;
                  }
               }
            }
         }
      }

      for (StructureMazeGenerator.WallFacing facing : StructureMazeGenerator.WallFacing.values()) {
         maze[halfWayPointX + facing.xOffset][halfWayPointY + facing.zOffset] = maze[halfWayPointX + facing.xOffset][halfWayPointY + facing.zOffset]
            & facing.INVERTED_OPPOSITE;
      }

      maze[halfWayPointX][halfWayPointY] = 16;

      for (int x = 1; x < maze.length; x++) {
         for (int y = 1; y < maze[x].length; y++) {
            if (mazeLocal[x][y] == 0) {
               if (mazeLocal[x - 1][y] == 0) {
                  maze[x][y] = maze[x][y] | StructureMazeGenerator.WallFacing.WEST.BYTE;
                  maze[x - 1][y] = maze[x - 1][y] | StructureMazeGenerator.WallFacing.WEST.OPPOSITE;
               }

               if (mazeLocal[x][y - 1] == 0) {
                  maze[x][y] = maze[x][y] | StructureMazeGenerator.WallFacing.NORTH.BYTE;
                  maze[x][y - 1] = maze[x][y - 1] | StructureMazeGenerator.WallFacing.NORTH.OPPOSITE;
               }
            }
         }
      }

      for (Diagonals diagonals : Diagonals.values()) {
         cornerClippings[diagonals.ordinal()][0] = random.nextInt(maximumClipping) + 1;
         cornerClippings[diagonals.ordinal()][1] = random.nextInt(maximumClipping) + 1;

         for (int y = 0; y < cornerClippings[diagonals.ordinal()][0]; y++) {
            for (int x = 0; x < cornerClippings[diagonals.ordinal()][1]; x++) {
               maze[diagonals.operationX.convert(x, widthInCellCount - 2)][diagonals.operationY.convert(y, heightInCellCount - 2)] |= 16;
            }
         }
      }
   }

   private void processInnerWallsAndFloor(StructurePiece structureComponent, StructurePieceAccessor list, RandomSource random, int offset, Rotation[] rotations) {
      for (int x = 0; x < this.widthInCellCount - 1; x++) {
         for (int y = 0; y < this.heightInCellCount - 1; y++) {
            boolean xCenter = x == this.widthInCellCount / 2 - 1;
            boolean yCenter = y == this.heightInCellCount / 2 - 1;
            if (xCenter || yCenter || (this.maze[x][y] & 16) != 16) {
               int rotation = 0;
               int xBB = this.sizeConstraints.minX() + x * 12 + offset;
               int yBB = this.sizeConstraints.minY() + 1;
               int zBB = this.sizeConstraints.minZ() + y * 12 + offset;
               if (!xCenter || !yCenter) {
                  StructurePiece structure;
                  switch (this.maze[x][y] & 15) {
                     case 2:
                        rotation++;
                     case 1:
                        rotation++;
                     case 8:
                        rotation++;
                     case 4:
                        Rotation rotationCap = rotations[rotation];
                        if (random.nextBoolean()) {
                           structure = new NagaCourtyardHedgeCapComponent(this.structureManager, x * this.widthInCellCount + y, xBB, yBB, zBB, rotationCap);
                        } else {
                           structure = new NagaCourtyardHedgeCapPillarComponent(
                              this.structureManager, x * this.widthInCellCount + y, xBB, yBB, zBB, rotationCap
                           );
                        }
                        break;
                     case 9:
                        rotation++;
                     case 12:
                        rotation++;
                     case 6:
                        rotation++;
                     case 3:
                        Rotation rotationCorner = rotations[rotation];
                        structure = new NagaCourtyardHedgeCornerComponent(this.structureManager, this.maze[x][y], xBB, yBB, zBB, rotationCorner);
                        break;
                     case 10:
                        rotation++;
                     case 5:
                        Rotation rotationLine = rotations[rotation];
                        structure = new NagaCourtyardHedgeLineComponent(this.structureManager, this.maze[x][y], xBB, yBB, zBB, rotationLine);
                        break;
                     case 13:
                        rotation++;
                     case 14:
                        rotation++;
                     case 7:
                        rotation++;
                     case 11:
                        Rotation rotationT = rotations[rotation];
                        structure = new NagaCourtyardHedgeTJunctionComponent(this.structureManager, this.maze[x][y], xBB, yBB, zBB, rotationT);
                        break;
                     case 15:
                        structure = new NagaCourtyardHedgeIntersectionComponent(this.structureManager, this.maze[x][y], xBB, yBB, zBB, Rotation.NONE);
                        break;
                     default:
                        if (random.nextInt(150) == 0) {
                           structure = new CourtyardTerrace(
                              this.maze[x][y],
                              xBB - 6,
                              yBB - 3,
                              zBB - 6,
                              Rotation.NONE,
                              this.structureManager,
                              TwilightForestMod.prefix("courtyard/terrace_statue")
                           );
                        } else {
                           structure = switch (random.nextInt(5)) {
                              case 1 -> new CourtyardTerrace(
                                 this.maze[x][y],
                                 xBB - 6,
                                 yBB - 3,
                                 zBB - 6,
                                 Rotation.NONE,
                                 this.structureManager,
                                 TwilightForestMod.prefix("courtyard/terrace_duct")
                              );
                              case 2 -> new CourtyardTerrace(
                                 this.maze[x][y],
                                 xBB - 6,
                                 yBB - 3,
                                 zBB - 6,
                                 Rotation.NONE,
                                 this.structureManager,
                                 TwilightForestMod.prefix("courtyard/terrace_channel")
                              );
                              case 3 -> new CourtyardTerrace(
                                 this.maze[x][y],
                                 xBB - 6,
                                 yBB - 3,
                                 zBB - 6,
                                 Rotation.NONE,
                                 this.structureManager,
                                 TwilightForestMod.prefix("courtyard/terrace_reservoir")
                              );
                              case 4 -> new CourtyardTerraceHedge(this.maze[x][y], xBB - 6, yBB - 3, zBB - 6, Rotation.NONE, this.structureManager);
                              default -> new CourtyardTerrace(
                                 this.maze[x][y],
                                 xBB - 6,
                                 yBB - 3,
                                 zBB - 6,
                                 Rotation.NONE,
                                 this.structureManager,
                                 TwilightForestMod.prefix("courtyard/terrace_fire")
                              );
                           };
                        }
                  }

                  list.addPiece(structure);
                  structure.addChildren(structureComponent, list, random);
               }

               xBB = this.sizeConstraints.minX() + x * 12 + offset;
               zBB = this.sizeConstraints.minZ() + y * 12 + offset;
               boolean connectWest = StructureMazeGenerator.WallFacing.WEST.unpackAndTest(this.maze[x][y]);
               boolean connectNorth = StructureMazeGenerator.WallFacing.NORTH.unpackAndTest(this.maze[x][y]);
               boolean connectEast = StructureMazeGenerator.WallFacing.EAST.unpackAndTest(this.maze[x][y]);
               boolean connectSouth = StructureMazeGenerator.WallFacing.SOUTH.unpackAndTest(this.maze[x][y]);
               if (connectWest) {
                  NagaCourtyardHedgePadderComponent padding = new NagaCourtyardHedgePadderComponent(
                     this.structureManager, this.maze[x][y], xBB - 1, yBB, zBB, Rotation.NONE
                  );
                  list.addPiece(padding);
                  padding.addChildren(structureComponent, list, random);
                  if (x > 0 && (this.maze[x - 1][y] & 16) != 16) {
                     NagaCourtyardHedgePadderComponent padding2 = new NagaCourtyardHedgePadderComponent(
                        this.structureManager, this.maze[x][y], xBB - 7, yBB, zBB, Rotation.NONE
                     );
                     list.addPiece(padding2);
                     padding2.addChildren(structureComponent, list, random);
                  }

                  NagaCourtyardHedgeLineComponent structureLine = new NagaCourtyardHedgeLineComponent(
                     this.structureManager, this.maze[x][y], xBB - 6, yBB, zBB, Rotation.NONE
                  );
                  list.addPiece(structureLine);
                  structureLine.addChildren(structureComponent, list, random);
               }

               if (connectNorth) {
                  NagaCourtyardHedgePadderComponent padding = new NagaCourtyardHedgePadderComponent(
                     this.structureManager, this.maze[x][y], xBB + 4, yBB, zBB - 1, Rotation.CLOCKWISE_90
                  );
                  list.addPiece(padding);
                  padding.addChildren(structureComponent, list, random);
                  if (y > 0 && (this.maze[x][y - 1] & 16) != 16) {
                     NagaCourtyardHedgePadderComponent padding2 = new NagaCourtyardHedgePadderComponent(
                        this.structureManager, this.maze[x][y], xBB + 4, yBB, zBB - 7, Rotation.CLOCKWISE_90
                     );
                     list.addPiece(padding2);
                     padding2.addChildren(structureComponent, list, random);
                  }

                  NagaCourtyardHedgeLineComponent structureLine = new NagaCourtyardHedgeLineComponent(
                     this.structureManager, this.maze[x][y], xBB, yBB, zBB - 6, Rotation.CLOCKWISE_90
                  );
                  list.addPiece(structureLine);
                  structureLine.addChildren(structureComponent, list, random);
               }

               if ((x >= this.widthInCellCount - 2 || (this.maze[x + 1][y] & 16) == 16) && connectEast) {
                  NagaCourtyardHedgePadderComponent padding = new NagaCourtyardHedgePadderComponent(
                     this.structureManager, this.maze[x][y], xBB + 5, yBB, zBB, Rotation.NONE
                  );
                  list.addPiece(padding);
                  padding.addChildren(structureComponent, list, random);
                  NagaCourtyardHedgeLineComponent structureLine = new NagaCourtyardHedgeLineComponent(
                     this.structureManager, this.maze[x][y], xBB + 6, yBB, zBB, Rotation.NONE
                  );
                  list.addPiece(structureLine);
                  structureLine.addChildren(structureComponent, list, random);
               }

               if ((y >= this.heightInCellCount - 2 || (this.maze[x][y + 1] & 16) == 16) && connectSouth) {
                  NagaCourtyardHedgePadderComponent padding = new NagaCourtyardHedgePadderComponent(
                     this.structureManager, this.maze[x][y], xBB + 4, yBB, zBB + 5, Rotation.CLOCKWISE_90
                  );
                  list.addPiece(padding);
                  padding.addChildren(structureComponent, list, random);
                  NagaCourtyardHedgeLineComponent structureLine = new NagaCourtyardHedgeLineComponent(
                     this.structureManager, this.maze[x][y], xBB, yBB, zBB + 6, Rotation.CLOCKWISE_90
                  );
                  list.addPiece(structureLine);
                  structureLine.addChildren(structureComponent, list, random);
               }

               boolean hasNoTerrace = (this.maze[x][y] & 15) != 0;
               boolean westHasNoTerraceOrIsSafe = x == 0 || (this.maze[x - 1][y] & 16) == 16 || (this.maze[x - 1][y] & 15) != 0;
               boolean northHasNoTerraceOrIsSafe = y == 0 || (this.maze[x][y - 1] & 16) == 16 || (this.maze[x][y - 1] & 15) != 0;
               boolean eastHasNoTerraceOrIsSafe = x == this.widthInCellCount - 2 || (this.maze[x + 1][y] & 16) == 16;
               boolean southHasNoTerraceOrIsSafe = y == this.heightInCellCount - 2 || (this.maze[x][y + 1] & 16) == 16;
               boolean westNorthHasNoTerraceOrIsSafe = x == 0 || y == 0 || this.maze[x - 1][y - 1] != 0;
               boolean westSouthHasNoTerraceOrIsSafe = x == 0 || y >= this.heightInCellCount - 2 || this.maze[x - 1][y + 1] != 0;
               boolean eastNorthHasNoTerraceOrIsSafe = x >= this.widthInCellCount - 2 || y == 0 || this.maze[x + 1][y - 1] != 0;
               boolean eastSouthHasNoTerraceOrIsSafe = x >= this.widthInCellCount - 2 || y >= this.heightInCellCount - 2 || this.maze[x + 1][y + 1] != 0;
               if (xCenter && yCenter) {
                  CourtyardPathPiece path = new CourtyardPathPiece(this.maze[x][y], xBB - 1, yBB - 1, zBB - 1, this.structureManager);
                  list.addPiece(path);
                  path.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && westHasNoTerraceOrIsSafe && !connectWest) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB - 7, yBB - 1, zBB - 1, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && northHasNoTerraceOrIsSafe && !connectNorth) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB - 1, yBB - 1, zBB - 7, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && eastHasNoTerraceOrIsSafe) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB + 5, yBB - 1, zBB - 1, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && southHasNoTerraceOrIsSafe) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB - 1, yBB - 1, zBB + 5, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && westHasNoTerraceOrIsSafe && northHasNoTerraceOrIsSafe && westNorthHasNoTerraceOrIsSafe) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB - 7, yBB - 1, zBB - 7, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && westHasNoTerraceOrIsSafe && southHasNoTerraceOrIsSafe && westSouthHasNoTerraceOrIsSafe) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB - 7, yBB - 1, zBB + 5, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && eastHasNoTerraceOrIsSafe && northHasNoTerraceOrIsSafe && eastNorthHasNoTerraceOrIsSafe) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB + 5, yBB - 1, zBB - 7, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }

               if (hasNoTerrace && eastHasNoTerraceOrIsSafe && southHasNoTerraceOrIsSafe && eastSouthHasNoTerraceOrIsSafe) {
                  CourtyardPathPiece path2 = new CourtyardPathPiece(this.maze[x][y], xBB + 5, yBB - 1, zBB + 5, this.structureManager);
                  list.addPiece(path2);
                  path2.addChildren(structureComponent, list, random);
               }
            }
         }
      }
   }

   private void processOuterWalls(StructurePiece structureComponent, StructurePieceAccessor list, RandomSource random, int offset, Rotation[] rotations) {
      for (Diagonals diagonal : Diagonals.values()) {
         int zBoundX = diagonal.isTop()
            ? this.sizeConstraints.minZ() + this.cornerClipping[diagonal.ordinal()][0] * 12 - 3
            : this.sizeConstraints.maxZ() - this.cornerClipping[diagonal.ordinal()][0] * 12 + 1;
         CourtyardWallPadder paddingStartX = new CourtyardWallPadder(
            this.cornerClipping[diagonal.ordinal()][1] * 2 + 1,
            diagonal.isLeft() ? this.sizeConstraints.minX() + 2 : this.sizeConstraints.maxX() - 2,
            this.sizeConstraints.minY() + 1,
            zBoundX,
            Rotation.NONE,
            this.structureManager
         );
         list.addPiece(paddingStartX);
         paddingStartX.addChildren(structureComponent, list, random);
         int xPadOffset = diagonal.isLeft() ? 11 : -1;

         for (int i = 0; i < this.cornerClipping[diagonal.ordinal()][1] - 1; i++) {
            int xBound = diagonal.isLeft() ? this.sizeConstraints.minX() + i * 12 + 3 : this.sizeConstraints.maxX() - i * 12 - 13;
            CourtyardWall wall = new CourtyardWall(i * 2, xBound, this.sizeConstraints.minY() + 1, zBoundX, Rotation.NONE, this.structureManager);
            list.addPiece(wall);
            wall.addChildren(structureComponent, list, random);
            CourtyardWallPadder padding = new CourtyardWallPadder(
               i * 2 + 1, xBound + xPadOffset, this.sizeConstraints.minY() + 1, zBoundX, Rotation.NONE, this.structureManager
            );
            list.addPiece(padding);
            padding.addChildren(structureComponent, list, random);
         }

         int xBoundZ = diagonal.isLeft()
            ? this.sizeConstraints.minX() + this.cornerClipping[diagonal.ordinal()][1] * 12 - 1
            : this.sizeConstraints.maxX() - this.cornerClipping[diagonal.ordinal()][1] * 12 + 3;
         CourtyardWallPadder paddingStartZ = new CourtyardWallPadder(
            this.cornerClipping[diagonal.ordinal()][1] * 2 + 1,
            xBoundZ,
            this.sizeConstraints.minY() + 1,
            diagonal.isTop() ? this.sizeConstraints.minZ() + 2 : this.sizeConstraints.maxZ() - 2,
            Rotation.CLOCKWISE_90,
            this.structureManager
         );
         list.addPiece(paddingStartZ);
         paddingStartZ.addChildren(structureComponent, list, random);
         int zPadOffset = diagonal.isTop() ? 11 : -1;

         for (int i = 0; i < this.cornerClipping[diagonal.ordinal()][0] - 1; i++) {
            int zBound = diagonal.isTop() ? this.sizeConstraints.minZ() + i * 12 + 3 : this.sizeConstraints.maxZ() - i * 12 - 13;
            CourtyardWall wall = new CourtyardWall(i * 2, xBoundZ, this.sizeConstraints.minY() + 1, zBound, Rotation.CLOCKWISE_90, this.structureManager);
            list.addPiece(wall);
            wall.addChildren(structureComponent, list, random);
            CourtyardWallPadder padding = new CourtyardWallPadder(
               i * 2 + 1, xBoundZ, this.sizeConstraints.minY() + 1, zBound + zPadOffset, Rotation.CLOCKWISE_90, this.structureManager
            );
            list.addPiece(padding);
            padding.addChildren(structureComponent, list, random);
         }

         int wallCornerInnerX = this.sizeConstraints.minX()
            + diagonal.operationX.convert(this.cornerClipping[diagonal.ordinal()][1], this.widthInCellCount - 1) * 12;
         int wallCornerInnerZ = this.sizeConstraints.minZ()
            + diagonal.operationY.convert(this.cornerClipping[diagonal.ordinal()][0], this.heightInCellCount - 1) * 12;
         Rotation cornerRotation = rotations[diagonal.ordinal() % rotations.length];
         boolean shiftX = cornerRotation == Rotation.CLOCKWISE_180 || cornerRotation == Rotation.COUNTERCLOCKWISE_90;
         boolean shiftZ = cornerRotation == Rotation.CLOCKWISE_90 || cornerRotation == Rotation.CLOCKWISE_180;
         CourtyardWallCornerOuter corner1 = new CourtyardWallCornerOuter(
            diagonal.ordinal() * 3,
            wallCornerInnerX + (shiftZ ? (shiftX ? 1 : 7) : (shiftX ? -3 : 3)),
            this.sizeConstraints.minY() + 1,
            (diagonal.isTop() ? this.sizeConstraints.minZ() : this.sizeConstraints.maxZ() - 1) + (shiftZ ? (shiftX ? 4 : 0) : (shiftX ? 1 : -3)),
            cornerRotation,
            this.structureManager
         );
         list.addPiece(corner1);
         corner1.addChildren(structureComponent, list, random);
         CourtyardWallCornerOuter corner2 = new CourtyardWallCornerOuter(
            diagonal.ordinal() * 3 + 1,
            (diagonal.isLeft() ? this.sizeConstraints.minX() : this.sizeConstraints.maxX() - 1) + (shiftZ ? (shiftX ? 1 : 4) : (shiftX ? -3 : 0)),
            this.sizeConstraints.minY() + 1,
            wallCornerInnerZ + (shiftZ ? (shiftX ? 7 : 3) : (shiftX ? 1 : -3)),
            cornerRotation,
            this.structureManager
         );
         list.addPiece(corner2);
         corner2.addChildren(structureComponent, list, random);
         CourtyardWallCornerInner innerCorner = new CourtyardWallCornerInner(
            diagonal.ordinal() * 3 + 3,
            wallCornerInnerX + (shiftZ ? (shiftX ? -1 : 13) : (shiftX ? -9 : 5)),
            this.sizeConstraints.minY() + 1,
            wallCornerInnerZ + (shiftZ ? (shiftX ? 13 : 5) : (shiftX ? -1 : -9)),
            cornerRotation,
            this.structureManager
         );
         list.addPiece(innerCorner);
         innerCorner.addChildren(structureComponent, list, random);
      }

      for (int i = this.cornerClipping[3][1]; i < this.widthInCellCount - 1 - this.cornerClipping[0][1]; i++) {
         CourtyardWall wall = new CourtyardWall(
            i,
            this.sizeConstraints.minX() + i * 12 + offset - 3,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.minZ() - 3,
            Rotation.NONE,
            this.structureManager
         );
         list.addPiece(wall);
         wall.addChildren(structureComponent, list, random);
         CourtyardWallPadder padding = new CourtyardWallPadder(
            i,
            this.sizeConstraints.minX() + i * 12 + offset - 4,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.minZ() - 3,
            Rotation.NONE,
            this.structureManager
         );
         list.addPiece(padding);
         padding.addChildren(structureComponent, list, random);
      }

      CourtyardWallPadder padding2 = new CourtyardWallPadder(
         this.widthInCellCount - 1 - this.cornerClipping[0][1],
         this.sizeConstraints.minX() + (this.widthInCellCount - 1 - this.cornerClipping[0][1]) * 12 + offset - 4,
         this.sizeConstraints.minY() + 1,
         this.sizeConstraints.minZ() - 3,
         Rotation.NONE,
         this.structureManager
      );
      list.addPiece(padding2);
      padding2.addChildren(structureComponent, list, random);

      for (int i = this.cornerClipping[2][1]; i < this.widthInCellCount - 1 - this.cornerClipping[1][1]; i++) {
         CourtyardWall wall = new CourtyardWall(
            i,
            this.sizeConstraints.minX() + i * 12 + offset - 3,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.maxZ() + 1,
            Rotation.NONE,
            this.structureManager
         );
         list.addPiece(wall);
         wall.addChildren(structureComponent, list, random);
         CourtyardWallPadder padding = new CourtyardWallPadder(
            i,
            this.sizeConstraints.minX() + i * 12 + offset - 4,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.maxZ() + 1,
            Rotation.NONE,
            this.structureManager
         );
         list.addPiece(padding);
         padding.addChildren(structureComponent, list, random);
      }

      CourtyardWallPadder padding5 = new CourtyardWallPadder(
         this.widthInCellCount - 1 - this.cornerClipping[1][1],
         this.sizeConstraints.minX() + (this.widthInCellCount - 1 - this.cornerClipping[1][1]) * 12 + offset - 4,
         this.sizeConstraints.minY() + 1,
         this.sizeConstraints.maxZ() + 1,
         Rotation.NONE,
         this.structureManager
      );
      list.addPiece(padding5);
      padding5.addChildren(structureComponent, list, random);

      for (int i = this.cornerClipping[3][0]; i < this.heightInCellCount - 1 - this.cornerClipping[2][0]; i++) {
         CourtyardWall wall = new CourtyardWall(
            i,
            this.sizeConstraints.minX() - 1,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.minZ() + i * 12 + offset - 3,
            Rotation.CLOCKWISE_90,
            this.structureManager
         );
         list.addPiece(wall);
         wall.addChildren(structureComponent, list, random);
         CourtyardWallPadder padding = new CourtyardWallPadder(
            i,
            this.sizeConstraints.minX() - 1,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.minZ() + i * 12 + offset - 4,
            Rotation.CLOCKWISE_90,
            this.structureManager
         );
         list.addPiece(padding);
         padding.addChildren(structureComponent, list, random);
      }

      CourtyardWallPadder padding8 = new CourtyardWallPadder(
         this.heightInCellCount - 1 - this.cornerClipping[2][0],
         this.sizeConstraints.minX() - 1,
         this.sizeConstraints.minY() + 1,
         this.sizeConstraints.minZ() + (this.heightInCellCount - 1 - this.cornerClipping[2][0]) * 12 + offset - 4,
         Rotation.CLOCKWISE_90,
         this.structureManager
      );
      list.addPiece(padding8);
      padding8.addChildren(structureComponent, list, random);

      for (int i = this.cornerClipping[0][0]; i < this.heightInCellCount - 1 - this.cornerClipping[1][0]; i++) {
         CourtyardWall wall = new CourtyardWall(
            i,
            this.sizeConstraints.maxX() + 3,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.minZ() + i * 12 + offset - 3,
            Rotation.CLOCKWISE_90,
            this.structureManager
         );
         list.addPiece(wall);
         wall.addChildren(structureComponent, list, random);
         CourtyardWallPadder padding = new CourtyardWallPadder(
            i,
            this.sizeConstraints.maxX() + 3,
            this.sizeConstraints.minY() + 1,
            this.sizeConstraints.minZ() + i * 12 + offset - 4,
            Rotation.CLOCKWISE_90,
            this.structureManager
         );
         list.addPiece(padding);
         padding.addChildren(structureComponent, list, random);
      }

      CourtyardWallPadder padding11 = new CourtyardWallPadder(
         this.heightInCellCount - 1 - this.cornerClipping[1][0],
         this.sizeConstraints.maxX() + 3,
         this.sizeConstraints.minY() + 1,
         this.sizeConstraints.minZ() + (this.heightInCellCount - 1 - this.cornerClipping[1][0]) * 12 + offset - 4,
         Rotation.CLOCKWISE_90,
         this.structureManager
      );
      list.addPiece(padding11);
      padding11.addChildren(structureComponent, list, random);
   }

   private static String getStringFromFacings(int directions) {
      return switch (directions & 15) {
         case 1 -> " ╶─";
         case 2 -> " ╷ ";
         case 3 -> " ┌─";
         case 4 -> "─╴ ";
         case 5 -> "───";
         case 6 -> "─┐ ";
         case 7 -> "─┬─";
         case 8 -> " ╵ ";
         case 9 -> " └─";
         case 10 -> " │ ";
         case 11 -> " ├─";
         case 12 -> "─┘ ";
         case 13 -> "─┴─";
         case 14 -> "─┤ ";
         case 15 -> "─┼─";
         default -> " • ";
      };
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      ListTag mazeX = new ListTag();

      for (int x = 0; x < this.widthInCellCount - 1; x++) {
         ListTag mazeY = new ListTag();

         for (int y = 0; y < this.heightInCellCount - 1; y++) {
            mazeY.add(IntTag.valueOf(this.maze[x][y]));
         }

         mazeX.add(mazeY);
      }

      tagCompound.putInt("mazeWidth", this.widthInCellCount);
      tagCompound.putInt("mazeHeight", this.heightInCellCount);
      tagCompound.put("maze", mazeX);
      tagCompound.put("constraints", BoundingBoxUtils.boundingBoxToNBT(this.sizeConstraints));
   }

   protected enum WallFacing {
      EAST(1, 4, 14, 11, Direction.EAST, 1, 0),
      SOUTH(2, 8, 13, 7, Direction.SOUTH, 0, 1),
      WEST(4, 1, 11, 14, Direction.WEST, -1, 0),
      NORTH(8, 2, 7, 13, Direction.NORTH, 0, -1);

      private final int BYTE;
      private final int OPPOSITE;
      private final int INVERTED;
      private final int INVERTED_OPPOSITE;
      private final int xOffset;
      private final int zOffset;

      WallFacing(int bite, int oppositeBite, int inverted, int invertedOpposite, Direction enumFacing, int xOffset, int zOffset) {
         this.BYTE = bite;
         this.OPPOSITE = oppositeBite;
         this.INVERTED = inverted;
         this.INVERTED_OPPOSITE = invertedOpposite;
         this.xOffset = xOffset;
         this.zOffset = zOffset;
      }

      private boolean unpackAndTest(int directions) {
         return (this.BYTE & directions) == this.BYTE;
      }
   }
}
