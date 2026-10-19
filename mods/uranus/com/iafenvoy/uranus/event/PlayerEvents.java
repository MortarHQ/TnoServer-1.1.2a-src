package com.iafenvoy.uranus.event;

import net.minecraft.world.entity.player.Player;

public class PlayerEvents {
   public static final Event<PlayerEvents.PlayerLoggedInOrOut> LOGGED_OUT = new Event<>(callbacks -> player -> {
      for (PlayerEvents.PlayerLoggedInOrOut e : callbacks) {
         e.handleConnection(player);
      }
   });

   @FunctionalInterface
   public interface PlayerLoggedInOrOut {
      void handleConnection(Player var1);
   }
}
