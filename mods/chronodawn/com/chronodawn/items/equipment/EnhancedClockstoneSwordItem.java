package com.chronodawn.items.equipment;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;

public class EnhancedClockstoneSwordItem extends SwordItem {
   private static final float FREEZE_CHANCE = 0.25F;
   private static final int FREEZE_DURATION = 40;
   private static final int FREEZE_AMPLIFIER = 9;

   public EnhancedClockstoneSwordItem(Properties properties) {
      super(EnhancedClockstoneTier.INSTANCE, properties.attributes(SwordItem.createAttributes(EnhancedClockstoneTier.INSTANCE, 3, -2.4F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1200);
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (attacker.level().getRandom().nextFloat() < 0.25F) {
         target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 9, false, true));
      }

      return super.hurtEnemy(stack, target, attacker);
   }
}
