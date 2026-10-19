package cn.leolezury.eternalstarlight.common.world.gen.structure.garden;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.ESGrassBlock;
import cn.leolezury.eternalstarlight.common.block.LunarVineBlock;
import cn.leolezury.eternalstarlight.common.block.ShadegrieveBlock;
import cn.leolezury.eternalstarlight.common.block.spawner.LunarMonstrositySpawnerBlock;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePieceTypes;
import cn.leolezury.eternalstarlight.common.util.MazeGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class CursedGardenMazePiece extends StructurePiece {
   private static final int MAZE_SIZE = 37;
   private static final int STRUCTURE_SIZE = 111;
   private static final int STRUCTURE_HEIGHT = 12;
   private static final int CENTER_SIZE = 20;
   private static final int LUNAR_VINE_SIZE = 21;
   private static final Set<Direction> HORIZONTAL_DIRECTIONS = Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
   private StructureTemplate noConnection;
   private StructureTemplate singleConnection;
   private StructureTemplate doubleConnectionStraight;
   private StructureTemplate doubleConnectionCorner;
   private StructureTemplate tripleConnection;
   private StructureTemplate allConnection;
   private boolean[][] maze = new boolean[37][37];

   public CursedGardenMazePiece(StructureTemplateManager templateManager, long seed, int x, int y, int z) {
      super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), 0, new BoundingBox(x - 55, y - 1, z - 55, x + 55, y + 12, z + 55));
      this.setOrientation(Direction.SOUTH);
      this.loadTemplates(templateManager);
      MazeGenerator mazeGenerator = new MazeGenerator(37, RandomSource.create(seed + new BlockPos(x, y, z).asLong()));
      this.maze = mazeGenerator.generateMaze(1, 1);
      this.maze[0][18] = false;
      this.maze[1][18] = false;
      this.maze[18][0] = false;
      this.maze[18][1] = false;
      this.maze[36][18] = false;
      this.maze[35][18] = false;
      this.maze[18][36] = false;
      this.maze[18][35] = false;

      for (int i = 0; i < 37; i++) {
         for (int j = 0; j < 37; j++) {
            if (Math.pow(i * 3 + 1 - 55.5, 2.0) + Math.pow(j * 3 + 1 - 55.5, 2.0) < 400.0) {
               this.maze[i][j] = false;
            }
         }
      }
   }

   public CursedGardenMazePiece(StructurePieceSerializationContext context, CompoundTag tag) {
      super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), tag);
      this.setOrientation(Direction.SOUTH);
      this.loadTemplates(context.structureTemplateManager());
      ListTag xTag = tag.getList("maze", 9);

      for (int x = 0; x < 37; x++) {
         if (x < xTag.size() && xTag.get(x) instanceof ListTag yList) {
            for (int y = 0; y < 37; y++) {
               if (y < yList.size() && yList.get(y) instanceof ByteTag byteTag) {
                  this.maze[x][y] = byteTag.getAsByte() != 0;
               }
            }
         }
      }
   }

   private void loadTemplates(StructureTemplateManager templateManager) {
      this.noConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_no_connection"));
      this.singleConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_single_connection"));
      this.doubleConnectionStraight = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_double_connection_straight"));
      this.doubleConnectionCorner = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_double_connection_corner"));
      this.tripleConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_triple_connection"));
      this.allConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_all_connection"));
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      ListTag xTag = new ListTag();

      for (int x = 0; x < 37; x++) {
         ListTag yTag = new ListTag();

         for (int y = 0; y < 37; y++) {
            yTag.add(ByteTag.valueOf(this.maze[x][y]));
         }

         xTag.add(yTag);
      }

      tag.put("maze", xTag);
   }

   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator generator,
      RandomSource random,
      BoundingBox box,
      ChunkPos chunkPos,
      BlockPos blockPos
   ) {
      for (int x = 0; x < 37; x++) {
         for (int z = 0; z < 37; z++) {
            if (this.maze[x][z]) {
               BlockPos pillarPos = this.getWorldPos(x * 3, 1, z * 3);
               Set<Direction> connectedDirs = new HashSet<>();

               for (Direction direction : HORIZONTAL_DIRECTIONS) {
                  int connectedX = x + direction.getStepX();
                  int connectedZ = z + direction.getStepZ();
                  if (connectedX >= 0 && connectedX < 37 && connectedZ >= 0 && connectedZ < 37 && this.maze[connectedX][connectedZ]) {
                     connectedDirs.add(direction);
                  }
               }

               switch (connectedDirs.size()) {
                  case 0:
                     this.noConnection.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box), random, 2);
                     break;
                  case 1:
                     connectedDirs.stream()
                        .findFirst()
                        .ifPresent(
                           direction -> {
                              this.singleConnection
                                 .placeInWorld(
                                    level,
                                    pillarPos,
                                    pillarPos,
                                    new StructurePlaceSettings()
                                       .setBoundingBox(box)
                                       .setRotation(switch (Mth.wrapDegrees((int)(direction.toYRot() - Direction.EAST.toYRot()))) {
                                          case -180 -> Rotation.CLOCKWISE_180;
                                          case -90 -> Rotation.COUNTERCLOCKWISE_90;
                                          case 90 -> Rotation.CLOCKWISE_90;
                                          default -> Rotation.NONE;
                                       })
                                       .setRotationPivot(new BlockPos(1, 0, 1)),
                                    random,
                                    2
                                 );
                           }
                        );
                     break;
                  case 2:
                     connectedDirs.stream()
                        .findFirst()
                        .ifPresent(
                           direction -> {
                              if (connectedDirs.contains(direction.getOpposite())) {
                                 this.doubleConnectionStraight
                                    .placeInWorld(
                                       level,
                                       pillarPos,
                                       pillarPos,
                                       new StructurePlaceSettings()
                                          .setBoundingBox(box)
                                          .setRotation(switch (Mth.wrapDegrees((int)(direction.toYRot() - Direction.EAST.toYRot()))) {
                                             case -180 -> Rotation.CLOCKWISE_180;
                                             case -90 -> Rotation.COUNTERCLOCKWISE_90;
                                             case 90 -> Rotation.CLOCKWISE_90;
                                             default -> Rotation.NONE;
                                          })
                                          .setRotationPivot(new BlockPos(1, 0, 1)),
                                       random,
                                       2
                                    );
                              } else {
                                 connectedDirs.stream()
                                    .max((o1, o2) -> (int)Math.signum(Mth.degreesDifference(o1.toYRot(), o2.toYRot())))
                                    .ifPresent(
                                       dir -> {
                                          this.doubleConnectionCorner
                                             .placeInWorld(
                                                level,
                                                pillarPos,
                                                pillarPos,
                                                new StructurePlaceSettings()
                                                   .setBoundingBox(box)
                                                   .setRotation(switch (Mth.wrapDegrees((int)(dir.toYRot() - Direction.EAST.toYRot()))) {
                                                      case -180 -> Rotation.CLOCKWISE_180;
                                                      case -90 -> Rotation.COUNTERCLOCKWISE_90;
                                                      case 90 -> Rotation.CLOCKWISE_90;
                                                      default -> Rotation.NONE;
                                                   })
                                                   .setRotationPivot(new BlockPos(1, 0, 1)),
                                                random,
                                                2
                                             );
                                       }
                                    );
                              }
                           }
                        );
                     break;
                  case 3:
                     new HashSet<>(HORIZONTAL_DIRECTIONS)
                        .stream()
                        .filter(d -> !connectedDirs.contains(d))
                        .findFirst()
                        .ifPresent(
                           direction -> {
                              this.tripleConnection
                                 .placeInWorld(
                                    level,
                                    pillarPos,
                                    pillarPos,
                                    new StructurePlaceSettings()
                                       .setBoundingBox(box)
                                       .setRotation(switch (Mth.wrapDegrees((int)(direction.toYRot() - Direction.NORTH.toYRot()))) {
                                          case -180 -> Rotation.CLOCKWISE_180;
                                          case -90 -> Rotation.COUNTERCLOCKWISE_90;
                                          case 90 -> Rotation.CLOCKWISE_90;
                                          default -> Rotation.NONE;
                                       })
                                       .setRotationPivot(new BlockPos(1, 0, 1)),
                                    random,
                                    2
                                 );
                           }
                        );
                     break;
                  case 4:
                     this.allConnection.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box), random, 2);
               }
            }

            for (int blockX = x * 3; blockX < x * 3 + 3; blockX++) {
               for (int blockZ = z * 3; blockZ < z * 3 + 3; blockZ++) {
                  this.placeBlock(
                     level,
                     x != 0 && x != 36 && z != 0 && z != 36
                        ? ((ESGrassBlock)ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get()).defaultBlockState()
                        : ESBlocks.GRIMSTONE_BRICKS.get().defaultBlockState(),
                     blockX,
                     0,
                     blockZ,
                     box
                  );
                  if (!this.maze[x][z]) {
                     for (int y = 0; y < 12; y++) {
                        BlockState state = this.getBlock(level, blockX, y + 1, blockZ, box);
                        if (!state.is(BlockTags.LOGS) && !state.is(BlockTags.LEAVES) && !state.isAir()) {
                           this.placeBlock(level, Blocks.AIR.defaultBlockState(), blockX, y + 1, blockZ, box);
                        }
                     }
                  } else {
                     int leavesHeight = 3 + random.nextInt(3);

                     for (int y = 0; y < 11; y++) {
                        this.placeBlock(
                           level,
                           y <= leavesHeight && x != 0 && x != 36 && z != 0 && z != 36
                              ? (BlockState)(random.nextInt(3) == 0 ? ESBlocks.BLOOMING_SHADEGRIEVE.get() : ESBlocks.SHADEGRIEVE.get())
                                 .defaultBlockState()
                                 .setValue(ShadegrieveBlock.TOP, y == leavesHeight)
                              : (
                                 y == 10
                                    ? ((SlabBlock)ESBlocks.GRIMSTONE_TILE_SLAB.get()).defaultBlockState()
                                    : ESBlocks.GRIMSTONE_BRICKS.get().defaultBlockState()
                              ),
                           blockX,
                           y + 2,
                           blockZ,
                           box
                        );
                     }
                  }
               }
            }
         }
      }

      this.placeBlock(level, ((LunarMonstrositySpawnerBlock)ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get()).defaultBlockState(), 55, 1, 55, box);
      List<BlockPos> chestPositions = new ArrayList<>();

      for (int x = 1; x < 111; x++) {
         for (int z = 1; z < 111; z++) {
            if (!this.getBlock(level, x, 1, z, box).isAir()
               && !this.getBlock(level, x, 1, z, box).is(Blocks.VINE)
               && !this.getBlock(level, x, 1, z, box).is((Block)ESBlocks.LUNAR_VINE.get())) {
               for (Direction direction : Plane.HORIZONTAL.stream().toList()) {
                  int vineX = x + direction.getStepX();
                  int vineZ = z + direction.getStepZ();
                  if (this.getBlock(level, vineX, 1, vineZ, box).isAir()) {
                     double distSqr = Math.pow(vineX - 55.5, 2.0) + Math.pow(vineZ - 55.5, 2.0);
                     boolean lunarVines = distSqr < 441.0;
                     boolean bossRoom = distSqr < 400.0;
                     int yTo = 12 - random.nextInt(8) - 1;
                     int yFrom = random.nextInt(yTo - 3) + 2;
                     direction = direction.getAxis() == Axis.X ? direction.getOpposite() : direction;

                     for (int y = yFrom; y < yTo; y++) {
                        if (this.getBlock(level, vineX, y, vineZ, box).isAir()
                           && (
                              this.getBlock(level, x, y, z, box).is(ESBlocks.GRIMSTONE_BRICKS.get())
                                 || this.getBlock(level, x, y, z, box).is(ESBlocks.SHADEGRIEVE.get())
                           )) {
                           this.placeBlock(
                              level,
                              lunarVines
                                 ? (BlockState)((LunarVineBlock)ESBlocks.LUNAR_VINE.get()).defaultBlockState().setValue(LunarVineBlock.FACING, direction)
                                 : (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)VineBlock.PROPERTY_BY_DIRECTION.get(direction), true),
                              vineX,
                              y,
                              vineZ,
                              box
                           );
                        }
                     }

                     if (!bossRoom && random.nextInt(45) == 0 && chestPositions.stream().noneMatch(pos -> new BlockPos(vineX, 1, vineZ).distSqr(pos) <= 100.0)) {
                        chestPositions.add(new BlockPos(vineX, 1, vineZ));
                        this.createChest(level, box, random, vineX, 1, vineZ, ESLootTables.CHEST_CURSED_GARDEN);
                     }
                  }
               }
            }
         }
      }
   }
}
