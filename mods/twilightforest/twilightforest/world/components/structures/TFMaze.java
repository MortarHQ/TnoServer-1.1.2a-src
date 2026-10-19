package twilightforest.world.components.structures;

import java.util.Arrays;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece.BlockSelector;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFConfiguredFeatures;

public class TFMaze {
   public final int width;
   public final int depth;
   public int oddBias = 3;
   public final int evenBias = 1;
   public int tall = 3;
   public int head = 0;
   public int roots = 0;
   public int type;
   @Nullable
   public BlockSelector wallBlocks;
   public BlockState wallBlockState = ((Block)TFBlocks.CUT_MAZESTONE.get()).defaultBlockState();
   public BlockState headBlockState;
   public BlockState rootBlockState = ((Block)TFBlocks.MAZESTONE.get()).defaultBlockState();
   public BlockState pillarBlockState;
   public BlockState doorBlockState;
   public float doorRarity;
   public BlockState torchBlockState = Blocks.TORCH.defaultBlockState();
   public float torchRarity;
   protected final int rawWidth;
   protected final int rawDepth;
   protected final int[] storage;
   public static final int OUT_OF_BOUNDS = Integer.MIN_VALUE;
   public static final int OOB = Integer.MIN_VALUE;
   public static final int ROOM = 5;
   public static final int DOOR = 6;

   public TFMaze(int cellsWidth, int cellsDepth, RandomSource random) {
      this.pillarBlockState = Blocks.AIR.defaultBlockState();
      this.headBlockState = Blocks.AIR.defaultBlockState();
      this.doorBlockState = Blocks.AIR.defaultBlockState();
      this.torchRarity = 0.75F;
      this.doorRarity = 0.0F;
      this.width = cellsWidth;
      this.depth = cellsDepth;
      this.rawWidth = this.width * 2 + 1;
      this.rawDepth = this.depth * 2 + 1;
      this.storage = new int[this.rawWidth * this.rawDepth];
   }

   public int getCell(int x, int z) {
      return this.getRaw(x * 2 + 1, z * 2 + 1);
   }

   private void putCell(int x, int z, int value) {
      this.putRaw(x * 2 + 1, z * 2 + 1, value);
   }

   private boolean cellEquals(int x, int z, int value) {
      return this.getCell(x, z) == value;
   }

   private int getWall(int sx, int sz, int dx, int dz) {
      if (dx == sx + 1 && dz == sz) {
         return this.getRaw(sx * 2 + 2, sz * 2 + 1);
      }

      if (dx == sx - 1 && dz == sz) {
         return this.getRaw(sx * 2, sz * 2 + 1);
      }

      if (dx == sx && dz == sz + 1) {
         return this.getRaw(sx * 2 + 1, sz * 2 + 2);
      }

      if (dx == sx && dz == sz - 1) {
         return this.getRaw(sx * 2 + 1, sz * 2);
      }

      TwilightForestMod.LOGGER.info("Wall check out of bounds; s = {}, {}; d = {}, {}", sx, sz, dx, dz);
      return Integer.MIN_VALUE;
   }

   public void putWall(int sx, int sz, int dx, int dz, int value) {
      if (dx == sx + 1 && dz == sz) {
         this.putRaw(sx * 2 + 2, sz * 2 + 1, value);
      }

      if (dx == sx - 1 && dz == sz) {
         this.putRaw(sx * 2, sz * 2 + 1, value);
      }

      if (dx == sx && dz == sz + 1) {
         this.putRaw(sx * 2 + 1, sz * 2 + 2, value);
      }

      if (dx == sx && dz == sz - 1) {
         this.putRaw(sx * 2 + 1, sz * 2, value);
      }
   }

   public boolean isWall(int sx, int sz, int dx, int dz) {
      return this.getWall(sx, sz, dx, dz) == 0;
   }

   public void putRaw(int rawx, int rawz, int value) {
      if (rawx >= 0 && rawx < this.rawWidth && rawz >= 0 && rawz < this.rawDepth) {
         this.storage[rawz * this.rawWidth + rawx] = value;
      }
   }

   private int getRaw(int rawx, int rawz) {
      return rawx >= 0 && rawx < this.rawWidth && rawz >= 0 && rawz < this.rawDepth ? this.storage[rawz * this.rawWidth + rawx] : Integer.MIN_VALUE;
   }

   public boolean allCellsNonZero() {
      return IntStream.range(0, this.width).allMatch(x -> IntStream.range(0, this.depth).allMatch(z -> this.getCell(x, z) != 0));
   }

   public void resetCells() {
      Arrays.fill(this.storage, 0);
   }

   public void copyToStructure(
      WorldGenLevel world,
      StructureManager manager,
      ChunkGenerator generator,
      int dx,
      int dy,
      int dz,
      TFStructureComponentOld component,
      BoundingBox sbb,
      RandomSource rand
   ) {
      for (int x = 0; x < this.rawWidth; x++) {
         for (int z = 0; z < this.rawDepth; z++) {
            if (this.getRaw(x, z) == 0) {
               int mdx = dx + x / 2 * (this.evenBias + this.oddBias);
               int mdz = dz + z / 2 * (this.evenBias + this.oddBias);
               if (this.evenBias > 1) {
                  mdx--;
                  mdz--;
               }

               if (this.isEven(x) && this.isEven(z)) {
                  if (this.type == 4 && this.shouldTree(x, z, rand)) {
                     this.putCanopyTree(world, generator, mdx, dy, mdz, component, sbb);
                  } else {
                     for (int even = 0; even < this.evenBias; even++) {
                        for (int even2 = 0; even2 < this.evenBias; even2++) {
                           for (int y = 0; y < this.head; y++) {
                              this.putHeadBlock(world, mdx + even, dy + this.tall + y, mdz + even2, component, sbb);
                           }

                           for (int y = 0; y < this.tall; y++) {
                              if (this.shouldPillar(x, z)) {
                                 this.putPillarBlock(world, mdx + even, dy + y, mdz + even2, component, sbb);
                              } else {
                                 this.putWallBlock(world, mdx + even, dy + y, mdz + even2, component, sbb);
                              }
                           }

                           for (int y = 1; y <= this.roots; y++) {
                              this.putRootBlock(world, mdx + even, dy - y, mdz + even2, component, sbb);
                           }
                        }
                     }
                  }
               }

               if (this.isEven(x) && !this.isEven(z)) {
                  for (int even = 0; even < this.evenBias; even++) {
                     for (int odd = 1; odd <= this.oddBias; odd++) {
                        this.makeWallThing(world, dy, component, sbb, mdx, mdz, even, odd);
                     }
                  }
               }

               if (!this.isEven(x) && this.isEven(z)) {
                  for (int even = 0; even < this.evenBias; even++) {
                     for (int odd = 1; odd <= this.oddBias; odd++) {
                        this.makeWallThing(world, dy, component, sbb, mdx, mdz, odd, even);
                     }
                  }
               }
            } else if (this.getRaw(x, z) == 6) {
               int mdx = dx + x / 2 * (this.evenBias + this.oddBias);
               int mdz = dz + z / 2 * (this.evenBias + this.oddBias);
               if (this.evenBias > 1) {
                  mdx--;
                  mdz--;
               }

               if (this.isEven(x) && !this.isEven(z)) {
                  for (int even = 0; even < this.evenBias; even++) {
                     for (int odd = 1; odd <= this.oddBias; odd++) {
                        for (int y = 0; y < this.head; y++) {
                           this.putHeadBlock(world, mdx + even, dy + this.tall + y, mdz + odd, component, sbb);
                        }

                        for (int y = 0; y < this.tall; y++) {
                           this.putDoorBlock(world, mdx + even, dy + y, mdz + odd, component, sbb);
                        }

                        for (int y = 1; y <= this.roots; y++) {
                           this.putRootBlock(world, mdx + even, dy - y, mdz + odd, component, sbb);
                        }
                     }
                  }
               }

               if (!this.isEven(x) && this.isEven(z)) {
                  for (int even = 0; even < this.evenBias; even++) {
                     for (int odd = 1; odd <= this.oddBias; odd++) {
                        for (int y = 0; y < this.head; y++) {
                           this.putHeadBlock(world, mdx + odd, dy + this.tall + y, mdz + even, component, sbb);
                        }

                        for (int y = 0; y < this.tall; y++) {
                           this.putDoorBlock(world, mdx + odd, dy + y, mdz + even, component, sbb);
                        }

                        for (int y = 1; y <= this.roots; y++) {
                           this.putRootBlock(world, mdx + odd, dy - y, mdz + even, component, sbb);
                        }
                     }
                  }
               }
            }
         }
      }

      for (int x = 0; x < this.rawWidth; x++) {
         for (int z = 0; z < this.rawDepth; z++) {
            if (this.getRaw(x, z) == 0) {
               int mdx = dx + x / 2 * (this.evenBias + this.oddBias);
               int mdy = dy + 1;
               int mdz = dz + z / 2 * (this.evenBias + this.oddBias);
               if (this.isEven(x)
                  && this.isEven(z)
                  && this.shouldTorch(x, z, rand)
                  && component.getBlock(world, mdx, mdy, mdz, sbb).getBlock() == this.wallBlockState.getBlock()) {
                  component.placeBlock(world, this.torchBlockState, mdx, mdy, mdz, sbb);
               }
            }
         }
      }
   }

   private void makeWallThing(WorldGenLevel world, int dy, TFStructureComponentOld component, BoundingBox sbb, int mdx, int mdz, int even, int odd) {
      for (int y = 0; y < this.head; y++) {
         this.putHeadBlock(world, mdx + even, dy + this.tall + y, mdz + odd, component, sbb);
      }

      for (int y = 0; y < this.tall; y++) {
         this.putWallBlock(world, mdx + even, dy + y, mdz + odd, component, sbb);
      }

      for (int y = 1; y <= this.roots; y++) {
         this.putRootBlock(world, mdx + even, dy - y, mdz + odd, component, sbb);
      }
   }

   private void putPillarBlock(WorldGenLevel world, int x, int y, int z, TFStructureComponentOld component, BoundingBox sbb) {
      component.placeBlock(world, this.pillarBlockState, x, y, z, sbb);
   }

   private void putWallBlock(WorldGenLevel world, int x, int y, int z) {
      world.setBlock(new BlockPos(x, y, z), this.wallBlockState, 2);
   }

   private void putWallBlock(WorldGenLevel world, int x, int y, int z, TFStructureComponentOld component, BoundingBox sbb) {
      if (this.wallBlocks != null) {
         this.wallBlocks.next(world.getRandom(), x, y, z, true);
         component.placeBlock(world, this.wallBlocks.getNext(), x, y, z, sbb);
      } else {
         component.placeBlock(world, this.wallBlockState, x, y, z, sbb);
      }
   }

   private void putDoorBlock(WorldGenLevel world, int x, int y, int z, TFStructureComponentOld component, BoundingBox sbb) {
      component.placeBlock(world, this.doorBlockState, x, y, z, sbb);
   }

   private void putHeadBlock(WorldGenLevel world, int x, int y, int z, TFStructureComponentOld component, BoundingBox sbb) {
      component.placeBlock(world, this.headBlockState, x, y, z, sbb);
   }

   private void putRootBlock(WorldGenLevel world, int x, int y, int z, TFStructureComponentOld component, BoundingBox sbb) {
      component.placeBlock(world, this.rootBlockState, x, y, z, sbb);
   }

   private void putCanopyTree(WorldGenLevel world, ChunkGenerator generator, int x, int y, int z, TFStructureComponentOld component, BoundingBox sbb) {
      BlockPos pos = component.getBlockPosWithOffset(x, y, z);
      if (sbb.isInside(pos)
         && !((ConfiguredFeature)world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(TFConfiguredFeatures.CANOPY_TREE))
            .place(world, generator, world.getRandom(), pos)) {
         this.makeWallThing(world, y, component, sbb, x, z, 0, 0);
      }
   }

   private boolean isEven(int n) {
      return n % 2 == 0;
   }

   public boolean shouldTorch(int rx, int rz, RandomSource rand) {
      if (this.getRaw(rx + 1, rz) != Integer.MIN_VALUE
         && this.getRaw(rx - 1, rz) != Integer.MIN_VALUE
         && this.getRaw(rx, rz + 1) != Integer.MIN_VALUE
         && this.getRaw(rx, rz - 1) != Integer.MIN_VALUE) {
         return (this.getRaw(rx + 1, rz) != 0 || this.getRaw(rx - 1, rz) != 0) && (this.getRaw(rx, rz + 1) != 0 || this.getRaw(rx, rz - 1) != 0)
            ? rand.nextFloat() <= this.torchRarity
            : false;
      } else {
         return false;
      }
   }

   public boolean shouldPillar(int rx, int rz) {
      if (this.pillarBlockState.isAir()) {
         return false;
      } else {
         return this.getRaw(rx + 1, rz) != Integer.MIN_VALUE
               && this.getRaw(rx - 1, rz) != Integer.MIN_VALUE
               && this.getRaw(rx, rz + 1) != Integer.MIN_VALUE
               && this.getRaw(rx, rz - 1) != Integer.MIN_VALUE
            ? (this.getRaw(rx + 1, rz) != 0 || this.getRaw(rx - 1, rz) != 0) && (this.getRaw(rx, rz + 1) != 0 || this.getRaw(rx, rz - 1) != 0)
            : false;
      }
   }

   public boolean shouldTree(int rx, int rz, RandomSource rand) {
      if (rx != 0 && rx != this.rawWidth - 1 || this.getRaw(rx, rz + 1) == 0 && this.getRaw(rx, rz - 1) == 0) {
         return rz != 0 && rz != this.rawDepth - 1 || this.getRaw(rx + 1, rz) == 0 && this.getRaw(rx - 1, rz) == 0 ? rand.nextInt(50) == 0 : true;
      } else {
         return true;
      }
   }

   public void carveRoom1(int cx, int cz) {
      int rx = cx * 2 + 1;
      int rz = cz * 2 + 1;

      for (int i = -2; i <= 2; i++) {
         for (int j = -2; j <= 2; j++) {
            this.putRaw(rx + i, rz + j, 5);
         }
      }

      this.putCell(rx, rz + 1, 0);
      this.putCell(rx, rz - 1, 0);
      this.putCell(rx + 1, rz, 0);
      this.putCell(rx - 1, rz, 0);
      if (this.getRaw(rx, rz + 4) != Integer.MIN_VALUE) {
         this.putRaw(rx, rz + 3, 5);
      }

      if (this.getRaw(rx, rz - 4) != Integer.MIN_VALUE) {
         this.putRaw(rx, rz - 3, 5);
      }

      if (this.getRaw(rx + 4, rz) != Integer.MIN_VALUE) {
         this.putRaw(rx + 3, rz, 5);
      }

      if (this.getRaw(rx - 4, rz) != Integer.MIN_VALUE) {
         this.putRaw(rx - 3, rz, 5);
      }
   }

   public void add4Exits() {
      int hx = this.rawWidth / 2 + 1;
      int hz = this.rawDepth / 2 + 1;
      this.putRaw(hx, 0, 5);
      this.putRaw(hx, this.rawDepth - 1, 5);
      this.putRaw(0, hz, 5);
      this.putRaw(this.rawWidth - 1, hz, 5);
   }

   public void generateRecursiveBacktracker(int sx, int sz, RandomSource rand) {
      this.rbGen(sx, sz, rand);
   }

   public void rbGen(int sx, int sz, RandomSource rand) {
      this.putCell(sx, sz, 1);
      int unvisited = 0;
      if (this.cellEquals(sx + 1, sz, 0)) {
         unvisited++;
      }

      if (this.cellEquals(sx - 1, sz, 0)) {
         unvisited++;
      }

      if (this.cellEquals(sx, sz + 1, 0)) {
         unvisited++;
      }

      if (this.cellEquals(sx, sz - 1, 0)) {
         unvisited++;
      }

      if (unvisited != 0) {
         int rn = rand.nextInt(unvisited);
         int dz = 0;
         int dx = 0;
         if (this.cellEquals(sx + 1, sz, 0)) {
            if (rn == 0) {
               dx = sx + 1;
               dz = sz;
            }

            rn--;
         }

         if (this.cellEquals(sx - 1, sz, 0)) {
            if (rn == 0) {
               dx = sx - 1;
               dz = sz;
            }

            rn--;
         }

         if (this.cellEquals(sx, sz + 1, 0)) {
            if (rn == 0) {
               dx = sx;
               dz = sz + 1;
            }

            rn--;
         }

         if (this.cellEquals(sx, sz - 1, 0) && rn == 0) {
            dx = sx;
            dz = sz - 1;
         }

         if (rand.nextFloat() <= this.doorRarity) {
            this.putWall(sx, sz, dx, dz, 6);
         } else {
            this.putWall(sx, sz, dx, dz, 2);
         }

         this.rbGen(dx, dz, rand);
         this.rbGen(sx, sz, rand);
         this.rbGen(sx, sz, rand);
      }
   }
}
