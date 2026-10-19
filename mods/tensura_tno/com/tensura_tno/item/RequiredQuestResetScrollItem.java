package com.tensura_tno.item;

import com.tensura_tno.ftb.STExtrasHelper;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class RequiredQuestResetScrollItem extends Item {
   public RequiredQuestResetScrollItem() {
      super(new Properties().stacksTo(16));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("item.tensura_tno.required_quest_reset_scroll.tooltip"));
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 10000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (player.getCooldowns().isOnCooldown(stack.getItem()) && !player.hasInfiniteMaterials()) {
         return InteractionResultHolder.fail(stack);
      }

      player.startUsingItem(hand);
      return InteractionResultHolder.consume(stack);
   }

   public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
      if (remainingUseDuration % 4 == 0) {
         TensuraParticleHelper.spawnEnchantingTableParticle(level, entity.getEyePosition().add(0.0, 0.5, 0.0), ParticleTypes.ENCHANT, 8);
      }
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
      if (!level.isClientSide()) {
         int useTicks = this.getUseDuration(stack, entity) - timeLeft;
         if (useTicks >= 10) {
            if (entity instanceof ServerPlayer serverPlayer) {
               if (!serverPlayer.isSpectator()) {
                  int prestige = TensuraStorages.getPlayerDataFrom(serverPlayer).getResetCounter();
                  if (!STExtrasHelper.refreshRequiredQuests(serverPlayer, prestige)) {
                     serverPlayer.displayClientMessage(
                        Component.translatable("tensura_tno.required_quest_reset_scroll.unavailable").withStyle(ChatFormatting.RED), false
                     );
                  } else {
                     CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
                     entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                     TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.TOTEM_OF_UNDYING, 1.0);
                     TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.TOTEM_OF_UNDYING, 2.0);
                     TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.FLASH, 1.0);
                     if (!serverPlayer.hasInfiniteMaterials()) {
                        serverPlayer.getCooldowns().addCooldown(stack.getItem(), 100);
                        stack.shrink(1);
                     }
                  }
               }
            }
         }
      }
   }
}
