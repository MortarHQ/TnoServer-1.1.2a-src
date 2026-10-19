package com.iafenvoy.iceandfire.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class HydraHeartItem extends Item {
   public HydraHeartItem() {
      super(new Properties().stacksTo(1));
   }

   public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof Player player && itemSlot >= 0 && itemSlot <= 8) {
         double healthPercentage = player.getHealth() / Math.max(1.0F, player.getMaxHealth());
         if (healthPercentage < 1.0) {
            int level = 0;
            if (healthPercentage < 0.25) {
               level = 3;
            } else if (healthPercentage < 0.5) {
               level = 2;
            } else if (healthPercentage < 0.75) {
               level = 1;
            }

            if (!player.hasEffect(MobEffects.REGENERATION) || player.getEffect(MobEffects.REGENERATION).getAmplifier() < level) {
               player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, level, true, false));
            }
         }
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.hydra_heart.desc_0").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.hydra_heart.desc_1").withStyle(ChatFormatting.GRAY));
   }
}
