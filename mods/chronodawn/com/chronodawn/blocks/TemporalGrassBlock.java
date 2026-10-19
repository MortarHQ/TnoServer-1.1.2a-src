package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockProperties;
import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public class TemporalGrassBlock extends SpreadingSnowyDirtBlock {
   public static final MapCodec<TemporalGrassBlock> CODEC = simpleCodec(TemporalGrassBlock::new);

   public TemporalGrassBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return CompatBlockProperties.ofFullCopy(Blocks.GRASS_BLOCK);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if (stack.getItem() instanceof HoeItem) {
         if (!level.isClientSide()) {
            level.playSound(null, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, ((Block)ModBlocks.TEMPORAL_FARMLAND.get()).defaultBlockState());
            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
         }

         return ItemInteractionResult.SUCCESS;
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      }
   }

   private static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos above = pos.above();
      BlockState aboveState = level.getBlockState(above);
      if (aboveState.is(Blocks.SNOW) && (Integer)aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
         return true;
      } else {
         return aboveState.getFluidState().getAmount() == 8 ? false : level.getRawBrightness(above, 0) >= 1;
      }
   }

   private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos above = pos.above();
      return canBeGrass(state, level, pos) && !level.getFluidState(above).is(FluidTags.WATER);
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!canBeGrass(state, level, pos)) {
         level.setBlockAndUpdate(pos, ((Block)ModBlocks.TEMPORAL_DIRT.get()).defaultBlockState());
      } else if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
         BlockState grassState = this.defaultBlockState();

         for (int i = 0; i < 4; i++) {
            BlockPos target = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if (level.getBlockState(target).is((Block)ModBlocks.TEMPORAL_DIRT.get()) && canPropagate(grassState, level, target)) {
               level.setBlockAndUpdate(target, (BlockState)grassState.setValue(SNOWY, level.getBlockState(target.above()).is(Blocks.SNOW)));
            }
         }
      }
   }
}
