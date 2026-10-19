package com.mortal_cultivation.item;

import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class StrengthPillItem extends Item {
   private static final double AP_INCREASE = 500.0;

   public StrengthPillItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
      ItemStack resultStack = super.finishUsingItem(stack, level, livingEntity);
      if (!level.isClientSide && livingEntity instanceof Player player) {
         AttributeInstance aura = player.getAttribute(TensuraAttributes.MAX_AURA);
         if (aura != null) {
            aura.setBaseValue(aura.getBaseValue() + 500.0);
         }
      }

      return resultStack;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.EAT;
   }
}
