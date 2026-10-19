package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.entity.SlideBlock;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;

public class SliderBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
   public static final IntegerProperty DELAY = IntegerProperty.create("delay", 0, 3);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final int TICK_TIME = 80;
   private static final int OFFSET_TIME = 20;
   private static final int PLAYER_RANGE = 32;
   private static final float BLOCK_DAMAGE = 5.0F;
   private static final VoxelShape Y_BB = Shapes.create(new AABB(0.3125, 0.0, 0.3125, 0.6875, 1.0, 0.6875));
   private static final VoxelShape Z_BB = Shapes.create(new AABB(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 1.0));
   private static final VoxelShape X_BB = Shapes.create(new AABB(0.0, 0.3125, 0.3125, 1.0, 0.6875, 0.6875));

   public SliderBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(AXIS, Axis.Y)).setValue(DELAY, 0))
            .setValue(WATERLOGGED, false)
      );
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      boolean flag = fluidstate.getType() == Fluids.WATER;
      return (BlockState)super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{DELAY, WATERLOGGED}));
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch ((Axis)state.getValue(AXIS)) {
         case X -> X_BB;
         case Z -> Z_BB;
         default -> Y_BB;
      };
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.isClientSide() && this.isConnectedInRange(level, pos)) {
         SlideBlock slideBlock = new SlideBlock(
            (EntityType<? extends SlideBlock>)TFEntities.SLIDER.get(), level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state
         );
         level.addFreshEntity(slideBlock);
      }

      this.scheduleBlockUpdate(level, pos);
   }

   public boolean isConnectedInRange(Level level, BlockPos pos) {
      Axis axis = (Axis)level.getBlockState(pos).getValue(AXIS);

      return switch (axis) {
         case X -> this.anyPlayerInRange(level, pos)
            || this.isConnectedInRangeRecursive(level, pos, Direction.WEST)
            || this.isConnectedInRangeRecursive(level, pos, Direction.EAST);
         case Z -> this.anyPlayerInRange(level, pos)
            || this.isConnectedInRangeRecursive(level, pos, Direction.NORTH)
            || this.isConnectedInRangeRecursive(level, pos, Direction.SOUTH);
         case Y -> this.anyPlayerInRange(level, pos)
            || this.isConnectedInRangeRecursive(level, pos, Direction.UP)
            || this.isConnectedInRangeRecursive(level, pos, Direction.DOWN);
         default -> throw new MatchException(null, null);
      };
   }

   private boolean isConnectedInRangeRecursive(Level level, BlockPos pos, Direction dir) {
      BlockPos dPos = pos.relative(dir);
      return level.getBlockState(pos) != level.getBlockState(dPos)
         ? false
         : this.anyPlayerInRange(level, dPos) || this.isConnectedInRangeRecursive(level, dPos, dir);
   }

   private boolean anyPlayerInRange(Level level, BlockPos pos) {
      return level.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32.0, false) != null;
   }

   public void scheduleBlockUpdate(Level level, BlockPos pos) {
      int offset = (Integer)level.getBlockState(pos).getValue(DELAY);
      int update = 80 - (int)(level.getGameTime() - offset * 20) % 80;
      level.scheduleTick(pos, this, update);
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      this.scheduleBlockUpdate(level, pos);
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      entity.hurt(TFDamageTypes.getDamageSource(level, TFDamageTypes.SLIDER), 5.0F);
      if (entity instanceof LivingEntity living) {
         double kx = (pos.getX() + 0.5 - entity.getX()) * 2.0;
         double kz = (pos.getZ() + 0.5 - entity.getZ()) * 2.0;
         living.knockback(2.0, kx, kz);
      }
   }
}
