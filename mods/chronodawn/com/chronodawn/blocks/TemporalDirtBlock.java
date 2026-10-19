package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockProperties;
import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public class TemporalDirtBlock extends Block {
   public static final MapCodec<TemporalDirtBlock> CODEC = simpleCodec(TemporalDirtBlock::new);

   public TemporalDirtBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<? extends Block> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return CompatBlockProperties.ofFullCopy(Blocks.DIRT);
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
}
