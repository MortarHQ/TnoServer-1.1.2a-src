package com.blorb.morerelics.effects;

import com.blorb.morerelics.MoreRelics;
import java.util.Random;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "morerelics")
public class Dazed extends MobEffect {
   public Dazed() {
      super(MobEffectCategory.HARMFUL, 12395775);
   }

   @SubscribeEvent
   public static void onPlayerAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         MobEffectInstance effect = player.getEffect(MoreRelics.DAZED.getDelegate());
         if (effect != null) {
            Random rand = new Random();
            if (rand.nextFloat(1.0F) < (effect.getAmplifier() + 1) * 0.2) {
               player.level().playSound(null, player.position().x, player.position().y, player.position().z, MoreRelics.MISS, SoundSource.MASTER, 1.0F, 1.0F);
               event.setCanceled(true);
            }
         }
      }
   }
}
