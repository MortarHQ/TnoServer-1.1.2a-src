package de.ellpeck.naturesaura.potion;

import java.util.Random;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

public class PotionBreathless extends PotionImpl {
   private final Random random = new Random();

   public PotionBreathless() {
      super("breathless", MobEffectCategory.HARMFUL, 0);
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onHeal(LivingHealEvent event) {
      MobEffectInstance effect = event.getEntity().getEffect(ModPotions.BREATHLESS);
      if (effect != null) {
         float chance = (effect.getAmplifier() + 1) / 15.0F;
         if (this.random.nextFloat() <= chance) {
            event.setAmount(event.getAmount() / 4.0F);
         }
      }
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      int mod = 200 >> amplifier;
      return mod > 0 && duration % mod == 0 && this.random.nextBoolean();
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      entity.hurt(entity.damageSources().magic(), 1.0F);
      return true;
   }
}
