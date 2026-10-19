package com.chronodawn.items.equipment;

import com.chronodawn.registry.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;

public class EntropyCrystalSwordItem extends SwordItem {
   private static final int ENTROPY_DURATION_TICKS = 100;
   private static final int ENTROPY_AMPLIFIER = 0;

   public EntropyCrystalSwordItem(Properties properties) {
      super(EntropyCrystalTier.INSTANCE, properties.attributes(SwordItem.createAttributes(EntropyCrystalTier.INSTANCE, 3, -2.4F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(450);
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      Holder<MobEffect> effectHolder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.ENTROPY.get());
      target.addEffect(new MobEffectInstance(effectHolder, 100, 0, false, true));
      return super.hurtEnemy(stack, target, attacker);
   }
}
