package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;

public class ThornsBlock extends ConnectableRotatedPillarBlock implements SimpleWaterloggedBlock {
   protected static final VoxelShape BASE_SHAPE = Block.box(3.0, 3.0, 3.0, 13.0, 13.0, 13.0);
   protected static final VoxelShape WEST_SHAPE = Block.box(0.0, 3.0, 3.0, 3.0, 13.0, 13.0);
   protected static final VoxelShape EAST_SHAPE = Block.box(13.0, 3.0, 3.0, 16.0, 13.0, 13.0);
   protected static final VoxelShape DOWN_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0);
   protected static final VoxelShape UP_SHAPE = Block.box(3.0, 13.0, 3.0, 13.0, 16.0, 13.0);
   protected static final VoxelShape NORTH_SHAPE = Block.box(3.0, 3.0, 0.0, 13.0, 13.0, 3.0);
   protected static final VoxelShape SOUTH_SHAPE = Block.box(3.0, 3.0, 13.0, 13.0, 13.0, 16.0);
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final float THORN_DAMAGE = 4.0F;

   public ThornsBlock(Properties properties) {
      super(properties, 10.0);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any())
                                 .setValue(WATERLOGGED, false))
                              .setValue(AXIS, Axis.Y))
                           .setValue(DOWN, false))
                        .setValue(UP, false))
                     .setValue(NORTH, false))
                  .setValue(SOUTH, false))
               .setValue(WEST, false))
            .setValue(EAST, false)
      );
   }

   @Override
   public boolean canConnectTo(Axis thisAxis, Direction facing, BlockState facingState, boolean solidSide) {
      return facingState.getBlock() instanceof ThornsBlock
         || facingState.getBlock().equals(TFBlocks.THORN_ROSE.get())
         || facingState.getBlock().equals(TFBlocks.THORN_LEAVES.get())
         || facingState.getBlock().equals(TFBlocks.WEATHERED_DEADROCK.get());
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape shape = BASE_SHAPE;
      if ((Boolean)state.getValue(DOWN)) {
         shape = Shapes.or(shape, DOWN_SHAPE);
      }

      if ((Boolean)state.getValue(UP)) {
         shape = Shapes.or(shape, UP_SHAPE);
      }

      if ((Boolean)state.getValue(NORTH)) {
         shape = Shapes.or(shape, NORTH_SHAPE);
      }

      if ((Boolean)state.getValue(SOUTH)) {
         shape = Shapes.or(shape, SOUTH_SHAPE);
      }

      if ((Boolean)state.getValue(WEST)) {
         shape = Shapes.or(shape, WEST_SHAPE);
      }

      if ((Boolean)state.getValue(EAST)) {
         shape = Shapes.or(shape, EAST_SHAPE);
      }

      return shape;
   }

   @Nullable
   public PathType getBlockPathType(BlockState state, BlockGetter getter, BlockPos pos, @Nullable Mob entity) {
      return PathType.DAMAGE_OTHER;
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (!(entity instanceof ItemEntity item && item.getItem().is(ItemTagGenerator.IMMUNE_TO_THORNS))) {
         entity.hurt(TFDamageTypes.getDamageSource(level, TFDamageTypes.THORNS), 4.0F);
      }
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (state.getBlock() instanceof ThornsBlock && state.getValue(AXIS) == Axis.Y) {
         this.entityInside(state, level, pos, entity);
      }

      super.stepOn(level, pos, state, entity);
   }

   public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
      if (!player.getAbilities().instabuild) {
         if (!level.isClientSide()) {
            this.doThornBurst(level, pos, state);
         }

         return false;
      } else {
         return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
      }
   }

   private void doThornBurst(Level level, BlockPos pos, BlockState state) {
      switch ((Axis)state.getValue(AXIS)) {
         case Y:
            this.growThorns(level, pos, Direction.UP);
            this.growThorns(level, pos, Direction.DOWN);
            break;
         case X:
            this.growThorns(level, pos, Direction.EAST);
            this.growThorns(level, pos, Direction.WEST);
            break;
         case Z:
            this.growThorns(level, pos, Direction.NORTH);
            this.growThorns(level, pos, Direction.SOUTH);
      }

      this.growThorns(level, pos, Direction.getRandom(level.getRandom()));
      this.growThorns(level, pos, Direction.getRandom(level.getRandom()));
      this.growThorns(level, pos, Direction.getRandom(level.getRandom()));
   }

   private void growThorns(Level level, BlockPos pos, Direction dir) {
      int length = 1 + level.getRandom().nextInt(3);

      for (int i = 1; i < length; i++) {
         BlockPos dPos = pos.relative(dir, i);
         if (!level.isEmptyBlock(dPos)) {
            break;
         }

         level.setBlock(dPos, (BlockState)((Block)TFBlocks.GREEN_THORNS.get()).defaultBlockState().setValue(AXIS, dir.getAxis()), 2);
      }
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Axis axis = context.getClickedFace().getAxis();
      BlockState state = (BlockState)this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
      BlockPos pos = context.getClickedPos();

      for (Direction direction : Direction.values()) {
         BlockPos relativePos = pos.relative(direction);
         BlockState relativeState = context.getLevel().getBlockState(relativePos);
         state = (BlockState)state.setValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction), this.canConnectTo(axis, direction, relativeState, true));
      }

      return (BlockState)state.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
   }

   @Override
   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return facingState.is(Blocks.AIR) ? state : super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{WATERLOGGED}));
   }
}
