package net.regions_unexplored.world.level.feature.tree.nether;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.block.wood.MagmaLogBlock;

public class TallBrimWillowFeature extends Feature<NoneFeatureConfiguration> {
   public TallBrimWillowFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      int height = context.random().nextInt(3) + 13;
      if (level.getBlockState(pos.north()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south().west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.south().east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.north().west()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.north().east()).getBlock() == RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma()) {
         return false;
      }

      if (level.getBlockState(pos.below()).getBlock() != RUBlocks.BRIMSPROUT_NYLIUM
         && level.getBlockState(pos.below()).getBlock() != Blocks.SCULK
         && !level.getBlockState(pos.below()).is(BlockTags.NYLIUM)
         && !level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
         return false;
      }

      if (!level.isEmptyBlock(pos)) {
         return false;
      }

      MutableBlockPos placePos = pos.mutable();

      for (int i = 0; i <= height; i++) {
         if (i == 1) {
            this.placeRoots(level, placePos);
         }

         if (i == height - 3) {
            this.placeBranches(level, placePos);
         }

         if (i == height - 5) {
            this.placeSpine(level, placePos);
         }

         if (isReplaceable(level, placePos)) {
            if (level.isOutsideBuildHeight(placePos)) {
               break;
            }

            if (i == 3) {
               level.setBlock(
                  placePos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma().defaultBlockState().setValue(MagmaLogBlock.TRANSITION_BLOCK, true), 2
               );
            } else if (i < 3) {
               level.setBlock(
                  placePos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma().defaultBlockState().setValue(MagmaLogBlock.TRANSITION_BLOCK, false), 2
               );
            } else if (i > 3) {
               level.setBlock(placePos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLog().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Axis.Y), 2);
            }
         }

         if (i == height) {
            this.placeLeavesUpsideDown(level, placePos);
         }

         placePos.move(Direction.UP);
      }

      return true;
   }

   private void placeBrimwoodLog(LevelAccessor level, BlockPos pos, Axis axis) {
      if (!level.isOutsideBuildHeight(pos)) {
         if (isReplaceable(level, pos)) {
            level.setBlock(pos, (BlockState)RUBlocks.BRIMWOOD_WOOD_SET.getLog().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
         }
      }
   }

   private void placeBrimwoodLeaves(LevelAccessor level, BlockPos pos) {
      if (!level.isOutsideBuildHeight(pos)) {
         if (isReplaceable(level, pos)) {
            level.setBlock(pos, (BlockState)RUBlocks.BRIMWOOD_NATURAL_SET.getLeaves().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 2);
         }
      }
   }

   private void placeBranches(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos.north())) {
         if (isReplaceable(level, pos.north())) {
            this.placeBrimwoodLog(level, pos.north(), Axis.Z);
         }

         if (isReplaceable(level, pos.north().above().north())) {
            this.placeBrimwoodLog(level, pos.north().above().north(), Axis.Z);
         }

         if (isReplaceable(level, pos.north().above().north().north())) {
            this.placeBrimwoodLog(level, pos.north().above().north().north(), Axis.Z);
         }

         if (isReplaceable(level, pos.north().above().north().north().north())) {
            this.placeBrimwoodLog(level, pos.north().above().north().north().north(), Axis.Z);
            this.placeLeavesUpsideDown(level, pos.north().above().north().north().north());
         }
      }

      if (isReplaceable(level, pos.south())) {
         if (isReplaceable(level, pos.south())) {
            this.placeBrimwoodLog(level, pos.south(), Axis.Z);
         }

         if (isReplaceable(level, pos.south().above().south())) {
            this.placeBrimwoodLog(level, pos.south().above().south(), Axis.Z);
         }

         if (isReplaceable(level, pos.south().above().south().south())) {
            this.placeBrimwoodLog(level, pos.south().above().south().south(), Axis.Z);
         }

         if (isReplaceable(level, pos.south().above().south().south().south())) {
            this.placeBrimwoodLog(level, pos.south().above().south().south().south(), Axis.Z);
            this.placeLeavesUpsideDown(level, pos.south().above().south().south().south());
         }
      }

      if (isReplaceable(level, pos.east())) {
         if (isReplaceable(level, pos.east())) {
            this.placeBrimwoodLog(level, pos.east(), Axis.X);
         }

         if (isReplaceable(level, pos.east().above().east())) {
            this.placeBrimwoodLog(level, pos.east().above().east(), Axis.X);
         }

         if (isReplaceable(level, pos.east().above().east().east())) {
            this.placeBrimwoodLog(level, pos.east().above().east().east(), Axis.X);
         }

         if (isReplaceable(level, pos.east().above().east().east().east())) {
            this.placeBrimwoodLog(level, pos.east().above().east().east().east(), Axis.X);
            this.placeLeavesUpsideDown(level, pos.east().above().east().east().east());
         }
      }

      if (isReplaceable(level, pos.west())) {
         if (isReplaceable(level, pos.west())) {
            this.placeBrimwoodLog(level, pos.west(), Axis.X);
         }

         if (isReplaceable(level, pos.west().above().west())) {
            this.placeBrimwoodLog(level, pos.west().above().west(), Axis.X);
         }

         if (isReplaceable(level, pos.west().above().west().west())) {
            this.placeBrimwoodLog(level, pos.west().above().west().west(), Axis.X);
         }

         if (isReplaceable(level, pos.west().above().west().west().west())) {
            this.placeBrimwoodLog(level, pos.west().above().west().west().west(), Axis.X);
            this.placeLeavesUpsideDown(level, pos.west().above().west().west().west());
         }
      }
   }

   private void placeBranchesGiant(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos.north()) && isReplaceable(level, pos.north())) {
         this.placeBrimwoodLog(level, pos.north(), Axis.Z);
         if (isReplaceable(level, pos.north().north())) {
            this.placeBrimwoodLog(level, pos.north().north(), Axis.Z);
            if (isReplaceable(level, pos.north().north().north().above())) {
               this.placeBrimwoodLog(level, pos.north().north().north().above(), Axis.Z);
               if (isReplaceable(level, pos.north().north().north().north().above())) {
                  this.placeBrimwoodLog(level, pos.north().north().north().north().above(), Axis.Z);
                  if (isReplaceable(level, pos.north().north().north().north().above().north().above())) {
                     this.placeBrimwoodLog(level, pos.north().north().north().north().above().north().above(), Axis.Z);
                     this.placeLeavesUpsideDownGiant(level, pos.north().north().north().north().above().north().above());
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.south()) && isReplaceable(level, pos.south())) {
         this.placeBrimwoodLog(level, pos.south(), Axis.Z);
         if (isReplaceable(level, pos.south().south())) {
            this.placeBrimwoodLog(level, pos.south().south(), Axis.Z);
            if (isReplaceable(level, pos.south().south().south().above())) {
               this.placeBrimwoodLog(level, pos.south().south().south().above(), Axis.Z);
               if (isReplaceable(level, pos.south().south().south().south().above())) {
                  this.placeBrimwoodLog(level, pos.south().south().south().south().above(), Axis.Z);
                  if (isReplaceable(level, pos.south().south().south().south().above().south().above())) {
                     this.placeBrimwoodLog(level, pos.south().south().south().south().above().south().above(), Axis.Z);
                     this.placeLeavesUpsideDownGiant(level, pos.south().south().south().south().above().south().above());
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.east()) && isReplaceable(level, pos.east())) {
         this.placeBrimwoodLog(level, pos.east(), Axis.X);
         if (isReplaceable(level, pos.east().east())) {
            this.placeBrimwoodLog(level, pos.east().east(), Axis.X);
            if (isReplaceable(level, pos.east().east().east().above())) {
               this.placeBrimwoodLog(level, pos.east().east().east().above(), Axis.X);
               if (isReplaceable(level, pos.east().east().east().east().above())) {
                  this.placeBrimwoodLog(level, pos.east().east().east().east().above(), Axis.X);
                  if (isReplaceable(level, pos.east().east().east().east().above().east().above())) {
                     this.placeBrimwoodLog(level, pos.east().east().east().east().above().east().above(), Axis.X);
                     this.placeLeavesUpsideDownGiant(level, pos.east().east().east().east().above().east().above());
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.west()) && isReplaceable(level, pos.west())) {
         this.placeBrimwoodLog(level, pos.west(), Axis.X);
         if (isReplaceable(level, pos.west().west())) {
            this.placeBrimwoodLog(level, pos.west().west(), Axis.X);
            if (isReplaceable(level, pos.west().west().west().above())) {
               this.placeBrimwoodLog(level, pos.west().west().west().above(), Axis.X);
               if (isReplaceable(level, pos.west().west().west().west().above())) {
                  this.placeBrimwoodLog(level, pos.west().west().west().west().above(), Axis.X);
                  if (isReplaceable(level, pos.west().west().west().west().above().west().above())) {
                     this.placeBrimwoodLog(level, pos.west().west().west().west().above().west().above(), Axis.X);
                     this.placeLeavesUpsideDownGiant(level, pos.west().west().west().west().above().west().above());
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.north().west())) {
         this.placeBrimwoodLog(level, pos.north().west(), Axis.Z);
         if (isReplaceable(level, pos.north().west().north().west().above())) {
            this.placeBrimwoodLog(level, pos.north().west().north().west().above(), Axis.Z);
            if (isReplaceable(level, pos.north().west().north().west().north().west().above())) {
               this.placeBrimwoodLog(level, pos.north().west().north().west().north().west().above(), Axis.Z);
               if (isReplaceable(level, pos.north().west().north().west().north().west().above().north().west().above())) {
                  this.placeBrimwoodLog(level, pos.north().west().north().west().north().west().above().north().west().above(), Axis.Z);
                  if (isReplaceable(level, pos.north().west().north().west().north().west().above().north().west().above().north().west())) {
                     this.placeBrimwoodLog(level, pos.north().west().north().west().north().west().above().north().west().above().north().west(), Axis.Z);
                     if (isReplaceable(
                        level, pos.north().west().north().west().north().west().above().north().west().above().north().west().above().north().west()
                     )) {
                        this.placeBrimwoodLog(
                           level, pos.north().west().north().west().north().west().above().north().west().above().north().west().above().north().west(), Axis.Z
                        );
                        this.placeLeavesUpsideDownGiant(
                           level, pos.north().west().north().west().north().west().above().north().west().above().north().west().above().north().west()
                        );
                     }
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.south().west())) {
         this.placeBrimwoodLog(level, pos.south().west(), Axis.Z);
         if (isReplaceable(level, pos.south().west().south().west().above())) {
            this.placeBrimwoodLog(level, pos.south().west().south().west().above(), Axis.Z);
            if (isReplaceable(level, pos.south().west().south().west().south().west().above())) {
               this.placeBrimwoodLog(level, pos.south().west().south().west().south().west().above(), Axis.Z);
               if (isReplaceable(level, pos.south().west().south().west().south().west().above().south().west().above())) {
                  this.placeBrimwoodLog(level, pos.south().west().south().west().south().west().above().south().west().above(), Axis.Z);
                  if (isReplaceable(level, pos.south().west().south().west().south().west().above().south().west().above().south().west())) {
                     this.placeBrimwoodLog(level, pos.south().west().south().west().south().west().above().south().west().above().south().west(), Axis.Z);
                     if (isReplaceable(
                        level, pos.south().west().south().west().south().west().above().south().west().above().south().west().above().south().west()
                     )) {
                        this.placeBrimwoodLog(
                           level, pos.south().west().south().west().south().west().above().south().west().above().south().west().above().south().west(), Axis.Z
                        );
                        this.placeLeavesUpsideDownGiant(
                           level, pos.south().west().south().west().south().west().above().south().west().above().south().west().above().south().west()
                        );
                     }
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.north().east())) {
         this.placeBrimwoodLog(level, pos.north().east(), Axis.Z);
         if (isReplaceable(level, pos.north().east().north().east().above())) {
            this.placeBrimwoodLog(level, pos.north().east().north().east().above(), Axis.Z);
            if (isReplaceable(level, pos.north().east().north().east().north().east().above())) {
               this.placeBrimwoodLog(level, pos.north().east().north().east().north().east().above(), Axis.Z);
               if (isReplaceable(level, pos.north().east().north().east().north().east().above().north().east().above())) {
                  this.placeBrimwoodLog(level, pos.north().east().north().east().north().east().above().north().east().above(), Axis.Z);
                  if (isReplaceable(level, pos.north().east().north().east().north().east().above().north().east().above().north().east())) {
                     this.placeBrimwoodLog(level, pos.north().east().north().east().north().east().above().north().east().above().north().east(), Axis.Z);
                     if (isReplaceable(
                        level, pos.north().east().north().east().north().east().above().north().east().above().north().east().above().north().east()
                     )) {
                        this.placeBrimwoodLog(
                           level, pos.north().east().north().east().north().east().above().north().east().above().north().east().above().north().east(), Axis.Z
                        );
                        this.placeLeavesUpsideDownGiant(
                           level, pos.north().east().north().east().north().east().above().north().east().above().north().east().above().north().east()
                        );
                     }
                  }
               }
            }
         }
      }

      if (isReplaceable(level, pos.south().east())) {
         this.placeBrimwoodLog(level, pos.south().east(), Axis.Z);
         if (isReplaceable(level, pos.south().east().south().east().above())) {
            this.placeBrimwoodLog(level, pos.south().east().south().east().above(), Axis.Z);
            if (isReplaceable(level, pos.south().east().south().east().south().east().above())) {
               this.placeBrimwoodLog(level, pos.south().east().south().east().south().east().above(), Axis.Z);
               if (isReplaceable(level, pos.south().east().south().east().south().east().above().south().east().above())) {
                  this.placeBrimwoodLog(level, pos.south().east().south().east().south().east().above().south().east().above(), Axis.Z);
                  if (isReplaceable(level, pos.south().east().south().east().south().east().above().south().east().above().south().east())) {
                     this.placeBrimwoodLog(level, pos.south().east().south().east().south().east().above().south().east().above().south().east(), Axis.Z);
                     if (isReplaceable(
                        level, pos.south().east().south().east().south().east().above().south().east().above().south().east().above().south().east()
                     )) {
                        this.placeBrimwoodLog(
                           level, pos.south().east().south().east().south().east().above().south().east().above().south().east().above().south().east(), Axis.Z
                        );
                        this.placeLeavesUpsideDownGiant(
                           level, pos.south().east().south().east().south().east().above().south().east().above().south().east().above().south().east()
                        );
                     }
                  }
               }
            }
         }
      }
   }

   private void placeSpine(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos.north())) {
         this.placeBrimwoodLog(level, pos.north(), Axis.Z);
      }

      if (isReplaceable(level, pos.south())) {
         this.placeBrimwoodLog(level, pos.south(), Axis.Z);
      }

      if (isReplaceable(level, pos.east())) {
         this.placeBrimwoodLog(level, pos.east(), Axis.X);
      }

      if (isReplaceable(level, pos.west())) {
         this.placeBrimwoodLog(level, pos.west(), Axis.X);
      }
   }

   private void placeRoots(LevelAccessor level, BlockPos pos) {
      if (!level.isEmptyBlock(pos.north().below().north().below().below().below()) && isReplaceable(level, pos.north())) {
         if (isReplaceable(level, pos.north().north().north())) {
            this.placeBrimwoodLog(level, pos.north().north().north(), Axis.Z);
            if (isReplaceable(level, pos.north().north())) {
            }
         }

         if (isReplaceable(level, pos.north().below().north())) {
            this.placeBrimwoodLog(level, pos.north().below().north(), Axis.Y);
            if (isReplaceable(level, pos.north().below().north().below())) {
               this.placeBrimwoodLog(level, pos.north().below().north().below(), Axis.Y);
               if (isReplaceable(level, pos.north().below().north().below().below())) {
                  this.placeBrimwoodLog(level, pos.north().below().north().below().below(), Axis.Y);
                  if (isReplaceable(level, pos.north().below().north().below().below().below())) {
                     this.placeBrimwoodLog(level, pos.north().below().north().below().below().below(), Axis.Y);
                  }
               }
            }
         }
      }

      if (!level.isEmptyBlock(pos.south().below().south().below().below().below()) && isReplaceable(level, pos.south())) {
         if (isReplaceable(level, pos.south().south().south())) {
            this.placeBrimwoodLog(level, pos.south().south().south(), Axis.Z);
            if (isReplaceable(level, pos.south().south())) {
            }
         }

         if (isReplaceable(level, pos.south().below().south())) {
            this.placeBrimwoodLog(level, pos.south().below().south(), Axis.Y);
            if (isReplaceable(level, pos.south().below().south().below())) {
               this.placeBrimwoodLog(level, pos.south().below().south().below(), Axis.Y);
               if (isReplaceable(level, pos.south().below().south().below().below())) {
                  this.placeBrimwoodLog(level, pos.south().below().south().below().below(), Axis.Y);
                  if (isReplaceable(level, pos.south().below().south().below().below().below())) {
                     this.placeBrimwoodLog(level, pos.south().below().south().below().below().below(), Axis.Y);
                  }
               }
            }
         }
      }

      if (!level.isEmptyBlock(pos.east().below().east().below().below().below()) && isReplaceable(level, pos.east())) {
         if (isReplaceable(level, pos.east().east().east())) {
            this.placeBrimwoodLog(level, pos.east().east().east(), Axis.X);
            if (isReplaceable(level, pos.east().east())) {
            }
         }

         if (isReplaceable(level, pos.east().below().east())) {
            this.placeBrimwoodLog(level, pos.east().below().east(), Axis.Y);
            if (isReplaceable(level, pos.east().below().east().below())) {
               this.placeBrimwoodLog(level, pos.east().below().east().below(), Axis.Y);
               if (isReplaceable(level, pos.east().below().east().below().below())) {
                  this.placeBrimwoodLog(level, pos.east().below().east().below().below(), Axis.Y);
                  if (isReplaceable(level, pos.east().below().east().below().below().below())) {
                     this.placeBrimwoodLog(level, pos.east().below().east().below().below().below(), Axis.Y);
                  }
               }
            }
         }
      }

      if (!level.isEmptyBlock(pos.west().below().west().below().below().below()) && isReplaceable(level, pos.west())) {
         if (isReplaceable(level, pos.west().west().west())) {
            this.placeBrimwoodLog(level, pos.west().west().west(), Axis.X);
            if (isReplaceable(level, pos.west().west())) {
            }
         }

         if (isReplaceable(level, pos.west().below().west())) {
            this.placeBrimwoodLog(level, pos.west().below().west(), Axis.Y);
            if (isReplaceable(level, pos.west().below().west().below())) {
               this.placeBrimwoodLog(level, pos.west().below().west().below(), Axis.Y);
               if (isReplaceable(level, pos.west().below().west().below().below())) {
                  this.placeBrimwoodLog(level, pos.west().below().west().below().below(), Axis.Y);
                  if (isReplaceable(level, pos.west().below().west().below().below().below())) {
                     this.placeBrimwoodLog(level, pos.west().below().west().below().below().below(), Axis.Y);
                  }
               }
            }
         }
      }
   }

   private void placeLeavesUpsideDown(LevelAccessor level, BlockPos pos) {
      if (isReplaceable(level, pos)) {
         this.placeBrimwoodLeaves(level, pos);
      }

      if (isReplaceable(level, pos.above())) {
         this.placeBrimwoodLeaves(level, pos.above());
      }

      if (isReplaceable(level, pos.above().north())) {
         this.placeBrimwoodLeaves(level, pos.above().north());
      }

      if (isReplaceable(level, pos.above().north().east())) {
         this.placeBrimwoodLeaves(level, pos.above().north().east());
      }

      if (isReplaceable(level, pos.above().north().west())) {
         this.placeBrimwoodLeaves(level, pos.above().north().west());
      }

      if (isReplaceable(level, pos.above().south())) {
         this.placeBrimwoodLeaves(level, pos.above().south());
      }

      if (isReplaceable(level, pos.above().south().east())) {
         this.placeBrimwoodLeaves(level, pos.above().south().east());
      }

      if (isReplaceable(level, pos.above().south().west())) {
         this.placeBrimwoodLeaves(level, pos.above().south().west());
      }

      if (isReplaceable(level, pos.above().east())) {
         this.placeBrimwoodLeaves(level, pos.above().east());
      }

      if (isReplaceable(level, pos.above().west())) {
         this.placeBrimwoodLeaves(level, pos.above().west());
      }

      if (isReplaceable(level, pos.north())) {
         this.placeBrimwoodLeaves(level, pos.north());
      }

      if (isReplaceable(level, pos.north().north().above())) {
         this.placeBrimwoodLeaves(level, pos.north().north().above());
         this.addVine(level, pos.north().north().above());
      }

      if (isReplaceable(level, pos.north().west())) {
         this.placeBrimwoodLeaves(level, pos.north().west());
      }

      if (isReplaceable(level, pos.north().north().west().above())) {
         this.placeBrimwoodLeaves(level, pos.north().north().west().above());
         this.addVine(level, pos.north().north().west().above());
      }

      if (isReplaceable(level, pos.north().west().west().above())) {
         this.placeBrimwoodLeaves(level, pos.north().west().west().above());
         this.addVine(level, pos.north().west().west().above());
      }

      if (isReplaceable(level, pos.west())) {
         this.placeBrimwoodLeaves(level, pos.west());
      }

      if (isReplaceable(level, pos.west().west().above())) {
         this.placeBrimwoodLeaves(level, pos.west().west().above());
         this.addVine(level, pos.west().west().above());
      }

      if (isReplaceable(level, pos.south().west())) {
         this.placeBrimwoodLeaves(level, pos.south().west());
      }

      if (isReplaceable(level, pos.south().south().west().above())) {
         this.placeBrimwoodLeaves(level, pos.south().south().west().above());
         this.addVine(level, pos.south().south().west().above());
      }

      if (isReplaceable(level, pos.south().west().west().above())) {
         this.placeBrimwoodLeaves(level, pos.south().west().west().above());
         this.addVine(level, pos.south().west().west().above());
      }

      if (isReplaceable(level, pos.south())) {
         this.placeBrimwoodLeaves(level, pos.south());
      }

      if (isReplaceable(level, pos.south().south().above())) {
         this.placeBrimwoodLeaves(level, pos.south().south().above());
         this.addVine(level, pos.south().south().above());
      }

      if (isReplaceable(level, pos.south().east())) {
         this.placeBrimwoodLeaves(level, pos.south().east());
      }

      if (isReplaceable(level, pos.south().south().east().above())) {
         this.placeBrimwoodLeaves(level, pos.south().south().east().above());
         this.addVine(level, pos.south().south().east().above());
      }

      if (isReplaceable(level, pos.south().east().east().above())) {
         this.placeBrimwoodLeaves(level, pos.south().east().east().above());
         this.addVine(level, pos.south().east().east().above());
      }

      if (isReplaceable(level, pos.east())) {
         this.placeBrimwoodLeaves(level, pos.east());
      }

      if (isReplaceable(level, pos.east().east().above())) {
         this.placeBrimwoodLeaves(level, pos.east().east().above());
         this.addVine(level, pos.east().east().above());
      }

      if (isReplaceable(level, pos.north().east())) {
         this.placeBrimwoodLeaves(level, pos.north().east());
      }

      if (isReplaceable(level, pos.north().north().east().above())) {
         this.placeBrimwoodLeaves(level, pos.north().north().east().above());
         this.addVine(level, pos.north().north().east().above());
      }

      if (isReplaceable(level, pos.north().east().east().above())) {
         this.placeBrimwoodLeaves(level, pos.north().east().east().above());
         this.addVine(level, pos.north().east().east().above());
      }
   }

   private void placeLeavesUpsideDownGiant(LevelAccessor level, BlockPos pos) {
      BlockPos pos2 = pos.above();
      if (isReplaceable(level, pos)) {
         this.placeBrimwoodLeaves(level, pos);
      }

      if (!level.isOutsideBuildHeight(pos)) {
         if (isReplaceable(level, pos.north())) {
            this.placeBrimwoodLeaves(level, pos.north());
         }

         if (isReplaceable(level, pos.north().north())) {
            this.placeBrimwoodLeaves(level, pos.north().north());
         }

         if (isReplaceable(level, pos.north().west())) {
            this.placeBrimwoodLeaves(level, pos.north().west());
         }

         if (isReplaceable(level, pos.north().north().west())) {
            this.placeBrimwoodLeaves(level, pos.north().north().west());
         }

         if (isReplaceable(level, pos.north().west().west())) {
            this.placeBrimwoodLeaves(level, pos.north().west().west());
         }

         if (isReplaceable(level, pos.west())) {
            this.placeBrimwoodLeaves(level, pos.west());
         }

         if (isReplaceable(level, pos.west().west())) {
            this.placeBrimwoodLeaves(level, pos.west().west());
         }

         if (isReplaceable(level, pos.south().west())) {
            this.placeBrimwoodLeaves(level, pos.south().west());
         }

         if (isReplaceable(level, pos.south().south().west())) {
            this.placeBrimwoodLeaves(level, pos.south().south().west());
         }

         if (isReplaceable(level, pos.south().west().west())) {
            this.placeBrimwoodLeaves(level, pos.south().west().west());
         }

         if (isReplaceable(level, pos.south())) {
            this.placeBrimwoodLeaves(level, pos.south());
         }

         if (isReplaceable(level, pos.south().south())) {
            this.placeBrimwoodLeaves(level, pos.south().south());
         }

         if (isReplaceable(level, pos.south().east())) {
            this.placeBrimwoodLeaves(level, pos.south().east());
         }

         if (isReplaceable(level, pos.south().south().east())) {
            this.placeBrimwoodLeaves(level, pos.south().south().east());
         }

         if (isReplaceable(level, pos.south().east().east())) {
            this.placeBrimwoodLeaves(level, pos.south().east().east());
         }

         if (isReplaceable(level, pos.east())) {
            this.placeBrimwoodLeaves(level, pos.east());
         }

         if (isReplaceable(level, pos.east().east())) {
            this.placeBrimwoodLeaves(level, pos.east().east());
         }

         if (isReplaceable(level, pos.north().east())) {
            this.placeBrimwoodLeaves(level, pos.north().east());
         }

         if (isReplaceable(level, pos.north().north().east())) {
            this.placeBrimwoodLeaves(level, pos.north().north().east());
         }

         if (isReplaceable(level, pos.north().east().east())) {
            this.placeBrimwoodLeaves(level, pos.north().east().east());
         }
      }

      if (!level.isOutsideBuildHeight(pos2)) {
         if (isReplaceable(level, pos2.north())) {
            this.placeBrimwoodLeaves(level, pos2.north());
         }

         if (isReplaceable(level, pos2.north().north())) {
            this.placeBrimwoodLeaves(level, pos2.north().north());
         }

         if (isReplaceable(level, pos2.north().north().north())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().north());
            this.addVineGiant(level, pos2.north().north().north());
         }

         if (isReplaceable(level, pos2.east().north().north().north())) {
            this.placeBrimwoodLeaves(level, pos2.east().north().north().north());
            this.addVineGiant(level, pos2.east().north().north().north());
         }

         if (isReplaceable(level, pos2.east().east().north().north().north())) {
            this.placeBrimwoodLeaves(level, pos2.east().east().north().north().north());
            this.addVineGiant(level, pos2.east().east().north().north().north());
         }

         if (isReplaceable(level, pos2.west().north().north().north())) {
            this.placeBrimwoodLeaves(level, pos2.west().north().north().north());
            this.addVineGiant(level, pos2.west().north().north().north());
         }

         if (isReplaceable(level, pos2.west().west().north().north().north())) {
            this.placeBrimwoodLeaves(level, pos2.west().west().north().north().north());
            this.addVineGiant(level, pos2.west().west().north().north().north());
         }

         if (isReplaceable(level, pos2.north().west())) {
            this.placeBrimwoodLeaves(level, pos2.north().west());
         }

         if (isReplaceable(level, pos2.north().north().west())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().west());
            this.addVineGiant(level, pos2.north().north().west());
         }

         if (isReplaceable(level, pos2.north().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.north().west().west());
            this.addVineGiant(level, pos2.north().west().west());
         }

         if (isReplaceable(level, pos2.north().north().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().west().west());
            this.addVineGiant(level, pos2.north().north().west().west());
         }

         if (isReplaceable(level, pos2.west())) {
            this.placeBrimwoodLeaves(level, pos2.west());
         }

         if (isReplaceable(level, pos2.west().west())) {
            this.placeBrimwoodLeaves(level, pos2.west().west());
         }

         if (isReplaceable(level, pos2.west().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.west().west().west());
            this.addVineGiant(level, pos2.west().west().west());
         }

         if (isReplaceable(level, pos2.south().west().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.south().west().west().west());
            this.addVineGiant(level, pos2.south().west().west().west());
         }

         if (isReplaceable(level, pos2.south().south().west().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().west().west().west());
            this.addVineGiant(level, pos2.south().south().west().west().west());
         }

         if (isReplaceable(level, pos2.north().west().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.north().west().west().west());
            this.addVineGiant(level, pos2.north().west().west().west());
         }

         if (isReplaceable(level, pos2.north().north().west().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().west().west().west());
            this.addVineGiant(level, pos2.north().north().west().west().west());
         }

         if (isReplaceable(level, pos2.south().west())) {
            this.placeBrimwoodLeaves(level, pos2.south().west());
         }

         if (isReplaceable(level, pos2.south().south().west())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().west());
            this.addVineGiant(level, pos2.south().south().west());
         }

         if (isReplaceable(level, pos2.south().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.south().west().west());
            this.addVineGiant(level, pos2.south().west().west());
         }

         if (isReplaceable(level, pos2.south().south().west().west())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().west().west());
            this.addVineGiant(level, pos2.south().south().west().west());
         }

         if (isReplaceable(level, pos2.south())) {
            this.placeBrimwoodLeaves(level, pos2.south());
         }

         if (isReplaceable(level, pos2.south().south())) {
            this.placeBrimwoodLeaves(level, pos2.south().south());
         }

         if (isReplaceable(level, pos2.south().south().south())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().south());
            this.addVineGiant(level, pos2.south().south().south());
         }

         if (isReplaceable(level, pos2.east().south().south().south())) {
            this.placeBrimwoodLeaves(level, pos2.east().south().south().south());
            this.addVineGiant(level, pos2.east().south().south().south());
         }

         if (isReplaceable(level, pos2.east().east().south().south().south())) {
            this.placeBrimwoodLeaves(level, pos2.east().east().south().south().south());
            this.addVineGiant(level, pos2.east().east().south().south().south());
         }

         if (isReplaceable(level, pos2.west().south().south().south())) {
            this.placeBrimwoodLeaves(level, pos2.west().south().south().south());
            this.addVineGiant(level, pos2.west().south().south().south());
         }

         if (isReplaceable(level, pos2.west().west().south().south().south())) {
            this.placeBrimwoodLeaves(level, pos2.west().west().south().south().south());
            this.addVineGiant(level, pos2.west().west().south().south().south());
         }

         if (isReplaceable(level, pos2.south().east())) {
            this.placeBrimwoodLeaves(level, pos2.south().east());
         }

         if (isReplaceable(level, pos2.south().south().east())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().east());
            this.addVineGiant(level, pos2.south().south().east());
         }

         if (isReplaceable(level, pos2.south().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.south().east().east());
            this.addVineGiant(level, pos2.south().east().east());
         }

         if (isReplaceable(level, pos2.south().south().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().east().east());
            this.addVineGiant(level, pos2.south().south().east().east());
         }

         if (isReplaceable(level, pos2.east())) {
            this.placeBrimwoodLeaves(level, pos2.east());
         }

         if (isReplaceable(level, pos2.east().east())) {
            this.placeBrimwoodLeaves(level, pos2.east().east());
         }

         if (isReplaceable(level, pos2.east().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.east().east().east());
            this.addVineGiant(level, pos2.east().east().east());
         }

         if (isReplaceable(level, pos2.south().east().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.south().east().east().east());
            this.addVineGiant(level, pos2.south().east().east().east());
         }

         if (isReplaceable(level, pos2.south().south().east().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.south().south().east().east().east());
            this.addVineGiant(level, pos2.south().south().east().east().east());
         }

         if (isReplaceable(level, pos2.north().east().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.north().east().east().east());
            this.addVineGiant(level, pos2.north().east().east().east());
         }

         if (isReplaceable(level, pos2.north().north().east().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().east().east().east());
            this.addVineGiant(level, pos2.north().north().east().east().east());
         }

         if (isReplaceable(level, pos2.north().east())) {
            this.placeBrimwoodLeaves(level, pos2.north().east());
         }

         if (isReplaceable(level, pos2.north().north().east())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().east());
            this.addVineGiant(level, pos2.north().north().east());
         }

         if (isReplaceable(level, pos2.north().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.north().east().east());
            this.addVineGiant(level, pos2.north().east().east());
         }

         if (isReplaceable(level, pos2.north().north().east().east())) {
            this.placeBrimwoodLeaves(level, pos2.north().north().east().east());
            this.addVineGiant(level, pos2.north().north().east().east());
         }
      }
   }

   private void addVine(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      int size = random.nextInt(3);
      BlockPos place = pos.above();

      for (int i = 0; i <= size; i++) {
         if (!isAir(level, place)) {
            if (i != 0) {
               this.placeBrimwoodLeaves(level, place.below());
            }
            break;
         }

         if (i == size) {
            this.placeBrimwoodLeaves(level, place);
         } else {
            this.placeBrimwoodLeaves(level, place);
         }

         place = place.above();
      }
   }

   private void addVineGiant(LevelAccessor level, BlockPos pos) {
      Random random = new Random();
      int size = random.nextInt(4);
      BlockPos place = pos.above();

      for (int i = 0; i <= size; i++) {
         if (!isAir(level, place)) {
            if (i != 0) {
               this.placeBrimwoodLeaves(level, place.below());
            }
            break;
         }

         if (i == size) {
            this.placeBrimwoodLeaves(level, place);
         } else {
            this.placeBrimwoodLeaves(level, place);
         }

         place = place.above();
      }
   }

   public static boolean isAirBlock(BlockState state) {
      return state.is(Blocks.AIR) || state.is(Blocks.CAVE_AIR) || state.is(Blocks.VOID_AIR);
   }

   public static boolean isAir(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, TallBrimWillowFeature::isAirBlock);
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, TallBrimWillowFeature::isReplaceableBlock);
   }
}
