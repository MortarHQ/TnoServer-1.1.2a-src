package com.github.wal_bos.moreskills.world.entity.beretta;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import io.github.manasmods.tensura.ability.SkillUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;

public final class BerettaArenaHandler {
   private static final Map<UUID, BerettaArenaHandler.FlightState> FLIGHT_STATES = new HashMap<>();
   private static boolean initialized;

   private BerettaArenaHandler() {
   }

   private static EntityConfigs.BerettaConfig cfg() {
      return EntityConfigs.ENTITY.beretta;
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaArenaHandler::onBlockPlaced);
         NeoForge.EVENT_BUS.addListener(BerettaArenaHandler::onHeal);
      }
   }

   public static void tick(BerettaEntity boss) {
      if (boss.level() instanceof ServerLevel level) {
         int var10 = Math.max(1, (Integer)cfg().arenaCheckInterval.get());
         if (boss.tickCount % var10 == 0) {
            double radius = (Double)cfg().arenaRadius.get();
            double radiusSqr = radius * radius;
            AABB area = boss.getBoundingBox().inflate(radius, radius, radius);

            for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area, playerx -> !playerx.isSpectator())) {
               if (!(player.distanceToSqr(boss) > radiusSqr)) {
                  suppressFlight(player);
               }
            }

            if (boss.tickCount % 10 == 0) {
               cleanup(level.getServer(), null);
            }
         }
      }
   }

   public static void release(BerettaEntity boss) {
      if (boss.level() instanceof ServerLevel level) {
         cleanup(level.getServer(), boss);
      }
   }

   public static boolean isInsideArena(Player player) {
      return isInsideArena(player, null);
   }

   private static boolean isInsideArena(Player player, BerettaEntity ignoredBoss) {
      if (player.level() instanceof ServerLevel level) {
         double radius = (Double)cfg().arenaRadius.get();
         double radiusSqr = radius * radius;
         AABB area = player.getBoundingBox().inflate(radius, radius, radius);
         return !level.getEntitiesOfClass(BerettaEntity.class, area, boss -> boss != ignoredBoss && boss.isAlive() && boss.distanceToSqr(player) <= radiusSqr)
            .isEmpty();
      } else {
         return false;
      }
   }

   private static void onHeal(LivingHealEvent event) {
      if (event.getEntity() instanceof Player player && isInsideArena(player)) {
         event.setCanceled(true);
      }
   }

   private static void onBlockPlaced(EntityPlaceEvent event) {
      if (event.getEntity() instanceof Player player && isInsideArena(player)) {
         event.setCanceled(true);
      }
   }

   private static void suppressFlight(ServerPlayer player) {
      if (!player.isCreative() && !player.isSpectator()) {
         FLIGHT_STATES.computeIfAbsent(player.getUUID(), id -> new BerettaArenaHandler.FlightState(player.getAbilities().mayfly));
         boolean changed = false;
         if (player.getAbilities().flying) {
            player.getAbilities().flying = false;
            changed = true;
         }

         if (player.getAbilities().mayfly) {
            player.getAbilities().mayfly = false;
            changed = true;
         }

         if (player.isFallFlying()) {
            player.stopFallFlying();
            changed = true;
         }

         if (player.isNoGravity()) {
            player.setNoGravity(false);
            changed = true;
         }

         if (changed) {
            player.onUpdateAbilities();
         }
      }
   }

   private static void cleanup(MinecraftServer server, BerettaEntity ignoredBoss) {
      Iterator<Entry<UUID, BerettaArenaHandler.FlightState>> iterator = FLIGHT_STATES.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, BerettaArenaHandler.FlightState> entry = iterator.next();
         ServerPlayer player = server.getPlayerList().getPlayer(entry.getKey());
         if (player == null) {
            iterator.remove();
         } else if (player.isCreative() || player.isSpectator() || !isInsideArena(player, ignoredBoss)) {
            if (!player.isCreative() && !player.isSpectator()) {
               boolean canFly = entry.getValue().hadMayfly;

               try {
                  canFly = canFly || SkillUtils.canFlyLegit(player);
               } catch (Throwable var7) {
               }

               player.getAbilities().mayfly = canFly;
               player.getAbilities().flying = false;
               player.setNoGravity(false);
               player.onUpdateAbilities();
            }

            iterator.remove();
         }
      }
   }

   private static final class FlightState {
      private final boolean hadMayfly;

      private FlightState(boolean hadMayfly) {
         this.hadMayfly = hadMayfly;
      }
   }
}
