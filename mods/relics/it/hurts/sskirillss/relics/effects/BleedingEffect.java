package it.hurts.sskirillss.relics.effects;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

public class BleedingEffect extends MobEffect {
   public BleedingEffect() {
      super(MobEffectCategory.HARMFUL, 6829738);
   }

   @EventBusSubscriber
   public static class Events {
      @SubscribeEvent
      public static void onLivingUpdate(Post event) {
         if (event.getEntity() instanceof LivingEntity entity && entity.tickCount % 20 == 0 && entity.hasEffect(EffectRegistry.BLEEDING)) {
            entity.hurt(entity.level().damageSources().magic(), Math.min(10.0F, entity.getHealth() * 0.05F));
         }
      }
   }
}
