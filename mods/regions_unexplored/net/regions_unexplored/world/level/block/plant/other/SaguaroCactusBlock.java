package net.regions_unexplored.world.level.block.plant.other;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;
import net.regions_unexplored.world.level.block.state.properties.SaguaroCactusShape;

public class SaguaroCactusBlock extends Block {
   public static final EnumProperty<SaguaroCactusShape> SHAPE = RuBlockStateProperties.CACTUS_SHAPE;

   public SaguaroCactusBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
      stateBuilder.add(new Property[]{SHAPE});
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
      SaguaroCactusShape shape = (SaguaroCactusShape)state.getValue(SHAPE);
      if (level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
         if (level.getBlockState(pos.below()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
            if (state.getValue(SHAPE) == SaguaroCactusShape.NORTH_UP) {
               if (level.getBlockState(pos.north()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)) {
                  if (level.getBlockState(pos.east()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.EAST_UP;
                  }

                  if (level.getBlockState(pos.south()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.SOUTH_UP;
                  }

                  if (level.getBlockState(pos.west()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.WEST_UP;
                  }
               }
            } else if (state.getValue(SHAPE) == SaguaroCactusShape.EAST_UP) {
               if (level.getBlockState(pos.east()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)) {
                  if (level.getBlockState(pos.north()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.NORTH_UP;
                  }

                  if (level.getBlockState(pos.south()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.SOUTH_UP;
                  }

                  if (level.getBlockState(pos.west()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.WEST_UP;
                  }
               }
            } else if (state.getValue(SHAPE) == SaguaroCactusShape.SOUTH_UP) {
               if (level.getBlockState(pos.south()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)) {
                  if (level.getBlockState(pos.north()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.NORTH_UP;
                  }

                  if (level.getBlockState(pos.east()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.EAST_UP;
                  }

                  if (level.getBlockState(pos.west()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
                     && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                     shape = SaguaroCactusShape.WEST_UP;
                  }
               }
            } else if (state.getValue(SHAPE) == SaguaroCactusShape.WEST_UP
               && level.getBlockState(pos.west()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)) {
               if (level.getBlockState(pos.north()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
                  && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                  shape = SaguaroCactusShape.NORTH_UP;
               }

               if (level.getBlockState(pos.east()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
                  && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                  shape = SaguaroCactusShape.EAST_UP;
               }

               if (level.getBlockState(pos.south()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
                  && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
                  shape = SaguaroCactusShape.SOUTH_UP;
               }
            }
         }

         if (level.getBlockState(pos.north()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
            && level.getBlockState(pos.south()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
            && level.getBlockState(pos.east()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
            && level.getBlockState(pos.west()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)) {
            shape = SaguaroCactusShape.UP_DOWN;
         }
      }

      if (level.getBlockState(pos.below()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
         if (level.getBlockState(pos.north()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
            && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)
            && shape != SaguaroCactusShape.SOUTH_UP
            && shape != SaguaroCactusShape.EAST_UP
            && shape != SaguaroCactusShape.WEST_UP) {
            shape = SaguaroCactusShape.NORTH_UP;
         }

         if (level.getBlockState(pos.east()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
            && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)
            && shape != SaguaroCactusShape.NORTH_UP
            && shape != SaguaroCactusShape.SOUTH_UP
            && shape != SaguaroCactusShape.WEST_UP) {
            shape = SaguaroCactusShape.EAST_UP;
         }

         if (level.getBlockState(pos.south()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
            && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)
            && shape != SaguaroCactusShape.NORTH_UP
            && shape != SaguaroCactusShape.EAST_UP
            && shape != SaguaroCactusShape.WEST_UP) {
            shape = SaguaroCactusShape.SOUTH_UP;
         }

         if (level.getBlockState(pos.west()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST)
            && level.getBlockState(pos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)
            && shape != SaguaroCactusShape.NORTH_UP
            && shape != SaguaroCactusShape.EAST_UP
            && shape != SaguaroCactusShape.SOUTH_UP) {
            shape = SaguaroCactusShape.WEST_UP;
         }
      }

      if (level.getBlockState(pos.above()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN)) {
         if (shape == SaguaroCactusShape.NORTH_UP || shape == SaguaroCactusShape.SOUTH_UP) {
            shape = SaguaroCactusShape.NORTH_SOUTH;
         } else if (shape == SaguaroCactusShape.EAST_UP || shape == SaguaroCactusShape.WEST_UP) {
            shape = SaguaroCactusShape.EAST_WEST;
         }
      }

      return (BlockState)state.setValue(SHAPE, shape);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos blockpos = context.getClickedPos();
      Direction direction = context.getClickedFace();
      if (context.getLevel().getBlockState(blockpos.below()) != RUBlocks.SAGUARO_CACTUS.get().defaultBlockState()) {
         if (direction == Direction.NORTH && context.getLevel().getBlockState(blockpos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState()) {
            return (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.SOUTH_UP);
         }

         if (direction == Direction.SOUTH && context.getLevel().getBlockState(blockpos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState()) {
            return (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_UP);
         }

         if (direction == Direction.EAST && context.getLevel().getBlockState(blockpos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState()) {
            return (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.WEST_UP);
         }

         if (direction == Direction.WEST && context.getLevel().getBlockState(blockpos.above()) == RUBlocks.SAGUARO_CACTUS.get().defaultBlockState()) {
            return (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_UP);
         }
      }

      if (context.getClickedFace().getAxis().equals(Axis.X)) {
         return (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.EAST_WEST);
      } else if (context.getClickedFace().getAxis().equals(Axis.Y)) {
         return (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN);
      } else {
         return context.getClickedFace().getAxis().equals(Axis.Z)
            ? (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.NORTH_SOUTH)
            : (BlockState)this.defaultBlockState().setValue(SHAPE, SaguaroCactusShape.UP_DOWN);
      }
   }
}
