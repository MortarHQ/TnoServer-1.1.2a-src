package com.blorb.morerelics.render;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;

public class AfterImageTracker {
   private static final int MAX_STATES = 3;
   private static final Map<UUID, Deque<PlayerAfterImageState>> STATES = new HashMap<>();

   public static void record(Player player) {
      STATES.computeIfAbsent(player.getUUID(), id -> new ArrayDeque<>());
      Deque<PlayerAfterImageState> deque = STATES.get(player.getUUID());
      deque.addFirst(
         new PlayerAfterImageState(
            player.position(),
            player.getYRot(),
            player.yBodyRot,
            player.getYHeadRot(),
            player.getXRot(),
            player.walkAnimation.position(),
            player.walkAnimation.speed()
         )
      );

      while (deque.size() > 3) {
         deque.removeLast();
      }
   }

   public static void clear(Player player) {
      if (player != null) {
         Deque<PlayerAfterImageState> state = STATES.get(player.getUUID());
         if (state != null) {
            state.clear();
         }
      }
   }

   public static Deque<PlayerAfterImageState> get(Player player) {
      return STATES.getOrDefault(player.getUUID(), new ArrayDeque<>());
   }
}
