package net.jadenxgamer.netherexp.core.block;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TreacherousCandleBlock extends AbstractJNECandleBlock {
   private static final List<Vec3> PARTICLE_OFFSET = ImmutableList.of(new Vec3(0.5, 1.2, 0.5));
   private static final VoxelShape SHAPE = Block.box(5.5, 0.0, 5.5, 10.5, 16.0, 10.5);

   public TreacherousCandleBlock(Properties properties) {
      super(JNEParticleTypes.TREACHEROUS_FLAME, JNESoundEvents.SOUL_CANDLE_AMBIENT, properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(LIT, false)).setValue(WATERLOGGED, false));
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Level level = context.getLevel();
      FluidState fluid = level.getFluidState(context.getClickedPos());
      BlockState belowState = level.getBlockState(context.getClickedPos().below());
      return (BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluid.is(Fluids.WATER)))
         .setValue(LIT, belowState.is(this) && (Boolean)belowState.getValue(LIT));
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   @Override
   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      BlockState connectedCandle = level.getBlockState(pos.above());
      if (connectedCandle.is(this)) {
         level.setBlock(pos, (BlockState)connectedCandle.setValue(LIT, false), 3);
      }

      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      BlockState connectedCandle = level.getBlockState(pos.below());
      if (connectedCandle.is(this) && (Boolean)state.getValue(LIT)) {
         level.setBlock(pos.below(), (BlockState)connectedCandle.setValue(LIT, true), 3);
      }

      super.onRemove(state, level, pos, newState, movedByPiston);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (player.getAbilities().mayBuild && stack.isEmpty() && (Boolean)state.getValue(LIT)) {
         this.extinguishCandle(level, state, pos);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      }

      if (!level.getBlockState(pos.above()).is(this) && this.canBeLit(state)) {
         boolean changeState = false;
         if (stack.is(Items.FLINT_AND_STEEL)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            if (!player.getAbilities().instabuild) {
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }

            changeState = true;
         } else if (stack.is(Items.FIRE_CHARGE)) {
            level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
               stack.shrink(1);
            }

            changeState = true;
         }

         if (changeState) {
            this.lightCandle(level, state, pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      }

      return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) ? true : super.canBeReplaced(state, context);
   }

   @Override
   protected Iterable<Vec3> getParticleOffsets(BlockState state) {
      return PARTICLE_OFFSET;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIT, WATERLOGGED});
   }
}
