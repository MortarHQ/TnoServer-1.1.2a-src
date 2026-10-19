package com.mortal_cultivation.event;

import com.mortal_cultivation.ability.skill.unique.QiFlightSkill;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class QiFlightCleanupHandler {
   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      Player player = event.getEntity();
      QiFlightSkill.clearQiFlightState(player);
   }

   @SubscribeEvent
   public static void onPlayerDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player player) {
         QiFlightSkill.clearQiFlightState(player);
      }
   }
}
