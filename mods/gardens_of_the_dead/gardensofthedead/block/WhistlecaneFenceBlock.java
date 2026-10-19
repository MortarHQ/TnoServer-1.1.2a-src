package gardensofthedead.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WhistlecaneFenceBlock extends CrossCollisionBlock {
   private final VoxelShape[] occlusionByIndex;

   public WhistlecaneFenceBlock(Properties properties) {
      super(3.0F, 2.0F, 16.0F, 16.0F, 24.0F, properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NORTH, false)).setValue(EAST, false))
                  .setValue(SOUTH, false))
               .setValue(WEST, false))
            .setValue(WATERLOGGED, false)
      );
      this.occlusionByIndex = this.makeShapes(3.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   protected MapCodec<? extends CrossCollisionBlock> codec() {
      return MapCodec.unit(this);
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
      return this.occlusionByIndex[this.getAABBIndex(state)];
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return this.getShape(state, level, pos, context);
   }

   public boolean connectsTo(BlockState state, boolean isFaceSturdy, Direction direction) {
      Block block = state.getBlock();
      boolean isSame = this.isSameFence(state);
      boolean isFenceGate = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
      return !isExceptionForConnection(state) && isFaceSturdy || isSame || isFenceGate;
   }

   private boolean isSameFence(BlockState state) {
      return state.is(BlockTags.FENCES) && state.is(BlockTags.WOODEN_FENCES) == this.defaultBlockState().is(BlockTags.WOODEN_FENCES);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult
   ) {
      return player.getItemInHand(hand).is(Items.LEAD) ? ItemInteractionResult.FAIL : super.useItemOn(stack, state, level, pos, player, hand, blockHitResult);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockGetter level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      BlockPos northPos = pos.north();
      BlockPos eastPos = pos.east();
      BlockPos southPos = pos.south();
      BlockPos westPos = pos.west();
      BlockState northState = level.getBlockState(northPos);
      BlockState eastState = level.getBlockState(eastPos);
      BlockState southState = level.getBlockState(southPos);
      BlockState westState = level.getBlockState(westPos);
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.getStateForPlacement(context)
                     .setValue(NORTH, this.connectsTo(northState, northState.isFaceSturdy(level, northPos, Direction.SOUTH), Direction.SOUTH)))
                  .setValue(EAST, this.connectsTo(eastState, eastState.isFaceSturdy(level, eastPos, Direction.WEST), Direction.WEST)))
               .setValue(SOUTH, this.connectsTo(southState, southState.isFaceSturdy(level, southPos, Direction.NORTH), Direction.NORTH)))
            .setValue(WEST, this.connectsTo(westState, westState.isFaceSturdy(level, westPos, Direction.EAST), Direction.EAST)))
         .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos updatedPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return direction.getAxis().getPlane() == Plane.HORIZONTAL
         ? (BlockState)state.setValue(
            (Property)PROPERTY_BY_DIRECTION.get(direction),
            this.connectsTo(newState, newState.isFaceSturdy(level, updatedPos, direction.getOpposite()), direction.getOpposite())
         )
         : super.updateShape(state, direction, newState, level, pos, updatedPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{NORTH, EAST, WEST, SOUTH, WATERLOGGED});
   }
}
