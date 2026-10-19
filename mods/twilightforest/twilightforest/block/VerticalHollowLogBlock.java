package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.Configurable;
import twilightforest.enums.HollowLogVariants;
import twilightforest.util.DirectionUtil;

@Configurable
public class VerticalHollowLogBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final VoxelShape HOLLOW_SHAPE = Shapes.join(Shapes.block(), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape COLLISION_SHAPE = Shapes.join(Shapes.block(), Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), BooleanOp.ONLY_FIRST);
   @Autowired
   private DirectionUtil directionUtil;
   private final Holder<Block> climbable;

   public VerticalHollowLogBlock(Properties properties, Holder<Block> climbable) {
      super(properties);
      this.climbable = climbable;
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(WATERLOGGED, false));
      BeanContext.injectInto(this);
   }

   private static boolean isInside(HitResult result, BlockPos pos) {
      Vec3 vec = result.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
      return 0.124 <= vec.x() && vec.x() <= 0.876 && 0.124 <= vec.z() && vec.z() <= 0.876;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return HOLLOW_SHAPE;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return COLLISION_SHAPE;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{WATERLOGGED}));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if (!isInside(hit, pos)) {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      } else if (stack.is(Blocks.VINE.asItem())) {
         level.setBlock(
            pos,
            (BlockState)((BlockState)((Block)this.climbable.value())
                  .defaultBlockState()
                  .setValue(ClimbableHollowLogBlock.VARIANT, HollowLogVariants.Climbable.VINE))
               .setValue(ClimbableHollowLogBlock.FACING, this.directionUtil.horizontalOrElse(hit.getDirection(), player.getDirection().getOpposite())),
            3
         );
         HorizontalHollowLogBlock.playPlaceSound(Blocks.VINE.defaultBlockState(), level, pos, player);
         stack.consume(1, player);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else if (stack.is(Blocks.LADDER.asItem())) {
         level.setBlock(
            pos,
            (BlockState)((BlockState)((Block)this.climbable.value())
                  .defaultBlockState()
                  .setValue(
                     ClimbableHollowLogBlock.VARIANT,
                     state.getValue(WATERLOGGED) ? HollowLogVariants.Climbable.LADDER_WATERLOGGED : HollowLogVariants.Climbable.LADDER
                  ))
               .setValue(ClimbableHollowLogBlock.FACING, this.directionUtil.horizontalOrElse(hit.getDirection(), player.getDirection().getOpposite())),
            3
         );
         HorizontalHollowLogBlock.playPlaceSound(Blocks.LADDER.defaultBlockState(), level, pos, player);
         stack.consume(1, player);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)super.getStateForPlacement(context)
         .setValue(WATERLOGGED, context.getLevel().getBlockState(context.getClickedPos()).getFluidState().getType() == Fluids.WATER);
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, neighborState, accessor, pos, neighborPos);
   }
}
