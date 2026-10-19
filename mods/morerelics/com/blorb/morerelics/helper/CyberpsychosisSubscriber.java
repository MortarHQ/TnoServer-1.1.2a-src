package com.blorb.morerelics.helper;

import com.blorb.morerelics.MoreRelics;
import java.util.Random;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class CyberpsychosisSubscriber {
   @SubscribeEvent
   public static void OnKill(LivingDeathEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         double[] values = CyberwareHelper.GetCyberwareDominancy(player);
         if (values[0] != 0.0 && values[1] != 0.0) {
            Random rand = new Random();
            double chance = values[0];
            boolean willHappen = chance > rand.nextFloat(100.0F);
            if (willHappen) {
               player.addEffect(new MobEffectInstance(MoreRelics.CYBERPSYCHOSIS, (int)Math.round(values[1] * 20.0), 0));
            }
         }
      }
   }
}
