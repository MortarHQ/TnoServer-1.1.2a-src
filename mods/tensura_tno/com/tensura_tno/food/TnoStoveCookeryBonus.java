package com.tensura_tno.food;

import com.tensura_tno.registry.TensuraTNOBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public final class TnoStoveCookeryBonus {
   private static final double EFFECT_SCALE = 0.7;

   private TnoStoveCookeryBonus() {
   }

   public static void giveCookeryResult(Level level, BlockPos cookwarePos, LivingEntity entity, ItemStack stack) {
      ItemStack result = stack;
      if (isTnoStoveHeatSource(level, cookwarePos)) {
         result = stack.copy();
         CookStamp.tryStampScaledWithStoveBonus(result, entity, 0.7);
      }

      giveItemToLivingEntity(entity, result);
   }

   private static boolean isTnoStoveHeatSource(Level level, BlockPos cookwarePos) {
      if (level != null && cookwarePos != null) {
         BlockState belowState = level.getBlockState(cookwarePos.below());
         return belowState.is((Block)TensuraTNOBlocks.TNO_STOVE.get())
            && belowState.hasProperty(BlockStateProperties.LIT)
            && (Boolean)belowState.getValue(BlockStateProperties.LIT);
      } else {
         return false;
      }
   }

   private static void giveItemToLivingEntity(LivingEntity entity, ItemStack stack) {
      if (!stack.isEmpty()) {
         if (entity.getMainHandItem().isEmpty()) {
            RandomSource random = entity.level().random;
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
            entity.playSound(SoundEvents.ITEM_PICKUP, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         } else if (entity instanceof Player player) {
            ItemHandlerHelper.giveItemToPlayer(player, stack);
         } else {
            ItemEntity dropItem = entity.spawnAtLocation(stack);
            if (dropItem != null) {
               dropItem.setPickUpDelay(0);
            }
         }
      }
   }
}
