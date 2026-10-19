package com.blorb.morerelics.effects;

import com.blorb.morerelics.MoreRelics;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class Charred extends MobEffect {
   public Charred() {
      super(MobEffectCategory.HARMFUL, 0);
   }

   @SubscribeEvent
   public static void OnHurt(LivingIncomingDamageEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide()) {
         MobEffectInstance charEffect = entity.getEffect(MoreRelics.CHARRED.getDelegate());
         if (charEffect != null) {
            event.setAmount(event.getAmount() + (charEffect.getAmplifier() + 1));
         }
      }
   }
}
