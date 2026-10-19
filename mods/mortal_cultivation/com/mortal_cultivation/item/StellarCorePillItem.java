package com.mortal_cultivation.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class StellarCorePillItem extends Item {
   private static final int COOLDOWN_TICKS = 12000;

   public StellarCorePillItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
      ItemStack resultStack = super.finishUsingItem(stack, level, livingEntity);
      if (!level.isClientSide && livingEntity instanceof Player player) {
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 2));
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0));
         player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 3600, 1));
         player.getCooldowns().addCooldown(this, 12000);
      }

      return resultStack;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.EAT;
   }
}
