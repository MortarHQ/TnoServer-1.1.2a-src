package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.reg.IColorProvidingBlock;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class BlockGoldPowder extends BlockImpl implements IColorProvidingBlock, ICustomBlockState, ICustomItemModel {
   public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
   public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
   public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
   public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{
      Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0),
      Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
      Block.box(0.0, 0.0, 3.0, 13.0, 1.0, 13.0),
      Block.box(0.0, 0.0, 3.0, 13.0, 1.0, 16.0),
      Block.box(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
      Block.box(3.0, 0.0, 0.0, 13.0, 1.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 13.0, 1.0, 13.0),
      Block.box(0.0, 0.0, 0.0, 13.0, 1.0, 16.0),
      Block.box(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
      Block.box(3.0, 0.0, 3.0, 16.0, 1.0, 16.0),
      Block.box(0.0, 0.0, 3.0, 16.0, 1.0, 13.0),
      Block.box(0.0, 0.0, 3.0, 16.0, 1.0, 16.0),
      Block.box(3.0, 0.0, 0.0, 16.0, 1.0, 13.0),
      Block.box(3.0, 0.0, 0.0, 16.0, 1.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 13.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
   };

   public BlockGoldPowder() {
      super("gold_powder", Properties.ofFullCopy(Blocks.REDSTONE_WIRE));
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(NORTH, RedstoneSide.NONE)).setValue(EAST, RedstoneSide.NONE))
               .setValue(SOUTH, RedstoneSide.NONE))
            .setValue(WEST, RedstoneSide.NONE)
      );
   }

   private static int getShapeIndex(BlockState state) {
      int i = 0;
      boolean n = state.getValue(NORTH) != RedstoneSide.NONE;
      boolean e = state.getValue(EAST) != RedstoneSide.NONE;
      boolean s = state.getValue(SOUTH) != RedstoneSide.NONE;
      boolean w = state.getValue(WEST) != RedstoneSide.NONE;
      if (n || s && !n && !e && !w) {
         i |= 1 << Direction.NORTH.get2DDataValue();
      }

      if (e || w && !n && !e && !s) {
         i |= 1 << Direction.EAST.get2DDataValue();
      }

      if (s || n && !e && !s && !w) {
         i |= 1 << Direction.SOUTH.get2DDataValue();
      }

      if (w || e && !n && !s && !w) {
         i |= 1 << Direction.WEST.get2DDataValue();
      }

      return i;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{NORTH, EAST, SOUTH, WEST});
   }

   @Override
   public BlockColor getBlockColor() {
      return (state, levelIn, pos, tintIndex) -> 16042818;
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return SHAPES[getShapeIndex(state)];
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockGetter iblockreader = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      return (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(WEST, this.getSide(iblockreader, blockpos, Direction.WEST)))
               .setValue(EAST, this.getSide(iblockreader, blockpos, Direction.EAST)))
            .setValue(NORTH, this.getSide(iblockreader, blockpos, Direction.NORTH)))
         .setValue(SOUTH, this.getSide(iblockreader, blockpos, Direction.SOUTH));
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == Direction.DOWN) {
         return stateIn;
      } else {
         return facing == Direction.UP
            ? (BlockState)((BlockState)((BlockState)((BlockState)stateIn.setValue(WEST, this.getSide(levelIn, currentPos, Direction.WEST)))
                     .setValue(EAST, this.getSide(levelIn, currentPos, Direction.EAST)))
                  .setValue(NORTH, this.getSide(levelIn, currentPos, Direction.NORTH)))
               .setValue(SOUTH, this.getSide(levelIn, currentPos, Direction.SOUTH))
            : (BlockState)stateIn.setValue((Property)RedStoneWireBlock.PROPERTY_BY_DIRECTION.get(facing), this.getSide(levelIn, currentPos, facing));
      }
   }

   private RedstoneSide getSide(BlockGetter levelIn, BlockPos pos, Direction face) {
      BlockPos blockpos = pos.relative(face);
      BlockState blockstate = levelIn.getBlockState(blockpos);
      BlockPos blockpos1 = pos.above();
      BlockState blockstate1 = levelIn.getBlockState(blockpos1);
      if (!blockstate1.isCollisionShapeFullBlock(levelIn, blockpos1)) {
         boolean flag = blockstate.isFaceSturdy(levelIn, blockpos, Direction.UP) || blockstate.getBlock() == Blocks.HOPPER;
         if (flag && this.canConnectTo(levelIn.getBlockState(blockpos.above()))) {
            if (blockstate.isCollisionShapeFullBlock(levelIn, blockpos)) {
               return RedstoneSide.UP;
            }

            return RedstoneSide.SIDE;
         }
      }

      return this.canConnectTo(blockstate)
            || !blockstate.isCollisionShapeFullBlock(levelIn, blockpos) && this.canConnectTo(levelIn.getBlockState(blockpos.below()))
         ? RedstoneSide.SIDE
         : RedstoneSide.NONE;
   }

   protected boolean canConnectTo(BlockState blockState) {
      Block block = blockState.getBlock();
      return block == this;
   }

   public boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos pos) {
      BlockPos blockpos = pos.below();
      BlockState blockstate = levelIn.getBlockState(blockpos);
      return blockstate.isFaceSturdy(levelIn, blockpos, Direction.UP) || blockstate.getBlock() == Blocks.HOPPER;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public void onPlace(BlockState state, Level levelIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (oldState.getBlock() != state.getBlock() && !levelIn.isClientSide) {
         for (Direction direction : Plane.VERTICAL) {
            levelIn.updateNeighborsAt(pos.relative(direction), this);
         }

         for (Direction direction1 : Plane.HORIZONTAL) {
            this.notifyWireNeighborsOfStateChange(levelIn, pos.relative(direction1));
         }

         for (Direction direction2 : Plane.HORIZONTAL) {
            BlockPos blockpos = pos.relative(direction2);
            if (levelIn.getBlockState(blockpos).isCollisionShapeFullBlock(levelIn, blockpos)) {
               this.notifyWireNeighborsOfStateChange(levelIn, blockpos.above());
            } else {
               this.notifyWireNeighborsOfStateChange(levelIn, blockpos.below());
            }
         }
      }
   }

   public void onRemove(BlockState state, Level levelIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!isMoving && state.getBlock() != newState.getBlock()) {
         super.onRemove(state, levelIn, pos, newState, isMoving);
         if (!levelIn.isClientSide) {
            for (Direction direction : Direction.values()) {
               levelIn.updateNeighborsAt(pos.relative(direction), this);
            }

            for (Direction direction1 : Plane.HORIZONTAL) {
               this.notifyWireNeighborsOfStateChange(levelIn, pos.relative(direction1));
            }

            for (Direction direction2 : Plane.HORIZONTAL) {
               BlockPos blockpos = pos.relative(direction2);
               if (levelIn.getBlockState(blockpos).isCollisionShapeFullBlock(levelIn, blockpos)) {
                  this.notifyWireNeighborsOfStateChange(levelIn, blockpos.above());
               } else {
                  this.notifyWireNeighborsOfStateChange(levelIn, blockpos.below());
               }
            }
         }
      }
   }

   public void neighborChanged(BlockState state, Level levelIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!levelIn.isClientSide && !state.canSurvive(levelIn, pos)) {
         Block.dropResources(state, levelIn, pos);
         levelIn.removeBlock(pos, false);
      }
   }

   public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor levelIn, BlockPos pos, int flags, int recursionLeft) {
      MutableBlockPos pool = new MutableBlockPos();

      for (Direction direction : Plane.HORIZONTAL) {
         RedstoneSide redstoneside = (RedstoneSide)state.getValue((Property)RedStoneWireBlock.PROPERTY_BY_DIRECTION.get(direction));
         if (redstoneside != RedstoneSide.NONE && levelIn.getBlockState(pool.set(pos).move(direction)).getBlock() != this) {
            pool.move(Direction.DOWN);
            BlockState blockstate = levelIn.getBlockState(pool);
            if (blockstate.getBlock() != Blocks.OBSERVER) {
               BlockPos blockpos = pool.relative(direction.getOpposite());
               BlockState blockstate1 = blockstate.updateShape(direction.getOpposite(), levelIn.getBlockState(blockpos), levelIn, pool, blockpos);
               Block.updateOrDestroy(blockstate, blockstate1, levelIn, pool, flags);
            }

            pool.set(pos).move(direction).move(Direction.UP);
            BlockState blockstate3 = levelIn.getBlockState(pool);
            if (blockstate3.getBlock() != Blocks.OBSERVER) {
               BlockPos blockpos1 = pool.relative(direction.getOpposite());
               BlockState blockstate2 = blockstate3.updateShape(direction.getOpposite(), levelIn.getBlockState(blockpos1), levelIn, pool, blockpos1);
               Block.updateOrDestroy(blockstate3, blockstate2, levelIn, pool, flags);
            }
         }
      }
   }

   private void notifyWireNeighborsOfStateChange(Level levelIn, BlockPos pos) {
      if (levelIn.getBlockState(pos).getBlock() == this) {
         levelIn.updateNeighborsAt(pos, this);

         for (Direction direction : Direction.values()) {
            levelIn.updateNeighborsAt(pos.relative(direction), this);
         }
      }
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/generated")).texture("layer0", "item/" + this.getBaseName());
   }
}
