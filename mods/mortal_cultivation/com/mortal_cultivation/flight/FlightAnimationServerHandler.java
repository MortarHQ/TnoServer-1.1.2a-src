package com.mortal_cultivation.flight;

import com.mortal_cultivation.ability.skill.unique.GreatSageSkill;
import com.mortal_cultivation.ability.skill.unique.QiFlightSkill;
import com.mortal_cultivation.event.RaceFlightAbilityHandler;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StartTracking;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class FlightAnimationServerHandler {
   private static final int BRAKE_START_COOLDOWN_TICKS = 2;
   private static final Set<UUID> LAST_ACTIVE_PLAYERS = ConcurrentHashMap.newKeySet();
   private static final Set<UUID> BRAKE_HELD_PLAYERS = ConcurrentHashMap.newKeySet();
   private static final Map<UUID, Long> LAST_BRAKE_START_TICKS = new ConcurrentHashMap<>();

   private FlightAnimationServerHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         boolean active = isFlightAnimationActive(player);
         if (!active) {
            clearBrakeState(player.getUUID());
         }

         boolean wasActive = LAST_ACTIVE_PLAYERS.contains(player.getUUID());
         if (active != wasActive) {
            if (active) {
               LAST_ACTIVE_PLAYERS.add(player.getUUID());
            } else {
               LAST_ACTIVE_PLAYERS.remove(player.getUUID());
            }

            FlightAnimationNetwork.syncToTrackingAndSelf(player, active);
         }
      }
   }

   @SubscribeEvent
   public static void onStartTracking(StartTracking event) {
      if (event.getEntity() instanceof ServerPlayer observer && event.getTarget() instanceof ServerPlayer target) {
         boolean active = isFlightAnimationActive(target);
         FlightAnimationNetwork.syncToPlayer(observer, target, active);
         FlightAnimationNetwork.syncBrakeToPlayer(observer, target, active && BRAKE_HELD_PLAYERS.contains(target.getUUID()));
      }
   }

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      UUID playerId = event.getEntity().getUUID();
      LAST_ACTIVE_PLAYERS.remove(playerId);
      clearBrakeState(playerId);
   }

   @SubscribeEvent
   public static void onServerStopped(ServerStoppedEvent event) {
      LAST_ACTIVE_PLAYERS.clear();
      BRAKE_HELD_PLAYERS.clear();
      LAST_BRAKE_START_TICKS.clear();
   }

   static void updateBrakeState(ServerPlayer player, boolean brakeHeld, int sequence) {
      UUID playerId = player.getUUID();
      if (!isFlightAnimationActive(player)) {
         clearBrakeState(playerId);
         FlightAnimationNetwork.syncBrakeToTrackingAndSelf(player, false, sequence);
      } else if (!brakeHeld) {
         if (BRAKE_HELD_PLAYERS.remove(playerId)) {
            FlightAnimationNetwork.syncBrakeToTrackingAndSelf(player, false, sequence);
         } else {
            FlightAnimationNetwork.syncBrakeToTrackingAndSelf(player, false, sequence);
         }
      } else if (BRAKE_HELD_PLAYERS.contains(playerId)) {
         FlightAnimationNetwork.syncBrakeToTrackingAndSelf(player, true, sequence);
      } else {
         long gameTime = player.level().getGameTime();
         Long lastBrakeStart = LAST_BRAKE_START_TICKS.get(playerId);
         if (lastBrakeStart != null && gameTime >= lastBrakeStart && gameTime - lastBrakeStart < 2L) {
            FlightAnimationNetwork.syncBrakeToTrackingAndSelf(player, false, sequence);
         } else {
            LAST_BRAKE_START_TICKS.put(playerId, gameTime);
            BRAKE_HELD_PLAYERS.add(playerId);
            FlightAnimationNetwork.syncBrakeToTrackingAndSelf(player, true, sequence);
         }
      }
   }

   static boolean isFlightAnimationActive(ServerPlayer player) {
      return player.getAbilities().flying && !player.isFallFlying()
         ? RaceFlightAbilityHandler.isRaceFlightActive(player) || QiFlightSkill.isQiFlightActive(player) || GreatSageSkill.isCloudWalkerActive(player)
         : false;
   }

   private static void clearBrakeState(UUID playerId) {
      BRAKE_HELD_PLAYERS.remove(playerId);
      LAST_BRAKE_START_TICKS.remove(playerId);
   }
}
