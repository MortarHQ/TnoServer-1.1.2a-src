package com.github.wal_bos.moreskills.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class PainFlightSuppressionHandler {
   private static final double NO_FLY_HALF_SIZE = 10.0;
   private static final Map<UUID, PainFlightSuppressionHandler.PushFlight> PUSH_FLIGHTS = new HashMap<>();
   private static final Map<UUID, PainFlightSuppressionHandler.Aura> AURAS = new HashMap<>();
   private static final Map<UUID, PainFlightSuppressionHandler.SuppressedFlight> SUPPRESSED = new HashMap<>();
   private static boolean initialized;

   private PainFlightSuppressionHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(PainFlightSuppressionHandler::serverTick);
      }
   }

   public static void startAlmightyPushFlight(LivingEntity entity) {
      init();
      if (entity instanceof ServerPlayer player) {
         UUID id = player.getUUID();
         PainFlightSuppressionHandler.PushFlight state = PUSH_FLIGHTS.get(id);
         if (state == null) {
            state = new PainFlightSuppressionHandler.PushFlight(
               player.level().dimension().location().toString(), player.getAbilities().mayfly, player.getAbilities().flying
            );
            PUSH_FLIGHTS.put(id, state);
         }

         applyPushFlight(player, true);
      }
   }

   public static void stopAlmightyPushFlight(LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         PainFlightSuppressionHandler.PushFlight state = PUSH_FLIGHTS.remove(player.getUUID());
         if (state != null) {
            player.getAbilities().mayfly = state.hadMayfly;
            player.getAbilities().flying = state.hadFlying && state.hadMayfly;
            player.onUpdateAbilities();
            player.resetFallDistance();
         }
      }
   }

   public static void startNoFlyAura(LivingEntity owner) {
      init();
      if (owner.level() instanceof ServerLevel level) {
         UUID var4 = owner.getUUID();
         PainFlightSuppressionHandler.Aura aura = AURAS.get(var4);
         if (aura == null) {
            aura = new PainFlightSuppressionHandler.Aura(level.dimension().location().toString());
            AURAS.put(var4, aura);
         } else {
            aura.dimension = level.dimension().location().toString();
         }
      }
   }

   public static void stopNoFlyAura(LivingEntity owner) {
      PainFlightSuppressionHandler.Aura aura = AURAS.remove(owner.getUUID());
      if (aura != null) {
         MinecraftServer server = owner.level().getServer();
         if (server != null) {
            for (UUID target : new HashSet<>(aura.affected)) {
               removeSuppression(server, target, owner.getUUID());
            }

            aura.affected.clear();
         }
      }
   }

   private static void serverTick(Post event) {
      MinecraftServer server = event.getServer();
      if (server != null) {
         tickPushFlights(server);
         tickAuras(server);
      }
   }

   private static void tickPushFlights(MinecraftServer server) {
      if (!PUSH_FLIGHTS.isEmpty()) {
         Iterator<Entry<UUID, PainFlightSuppressionHandler.PushFlight>> iterator = PUSH_FLIGHTS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, PainFlightSuppressionHandler.PushFlight> entry = iterator.next();
            ServerPlayer player = server.getPlayerList().getPlayer(entry.getKey());
            if (player != null && player.isAlive()) {
               applyPushFlight(player, player.tickCount % 5 == 0);
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickAuras(MinecraftServer server) {
      if (!AURAS.isEmpty()) {
         Iterator<Entry<UUID, PainFlightSuppressionHandler.Aura>> iterator = AURAS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, PainFlightSuppressionHandler.Aura> entry = iterator.next();
            UUID ownerId = entry.getKey();
            PainFlightSuppressionHandler.Aura aura = entry.getValue();
            LivingEntity owner = findOwner(server, ownerId, aura.dimension);
            if (owner != null && owner.isAlive()) {
               tickAura(server, owner, ownerId, aura);
            } else {
               for (UUID target : new HashSet<>(aura.affected)) {
                  removeSuppression(server, target, ownerId);
               }

               aura.affected.clear();
               iterator.remove();
            }
         }
      }
   }

   private static LivingEntity findOwner(MinecraftServer server, UUID ownerId, String dimension) {
      for (ServerLevel level : server.getAllLevels()) {
         if (level.dimension().location().toString().equals(dimension) && level.getEntity(ownerId) instanceof LivingEntity living) {
            return living;
         }
      }

      return null;
   }

   private static void tickAura(MinecraftServer server, LivingEntity owner, UUID ownerId, PainFlightSuppressionHandler.Aura aura) {
      if (owner.level() instanceof ServerLevel level) {
         AABB var10 = owner.getBoundingBox().inflate(10.0, 10.0, 10.0);
         HashSet current = new HashSet();

         for (Player raw : level.getEntitiesOfClass(Player.class, var10, playerx -> validNoFlyTarget(owner, playerx))) {
            if (raw instanceof ServerPlayer player) {
               current.add(player.getUUID());
               addSuppression(player, ownerId);
            }
         }

         for (UUID old : new HashSet<>(aura.affected)) {
            if (!current.contains(old)) {
               removeSuppression(server, old, ownerId);
            }
         }

         aura.affected.clear();
         aura.affected.addAll(current);
      }
   }

   private static boolean validNoFlyTarget(LivingEntity owner, Player player) {
      if (player == owner) {
         return false;
      } else if (!player.isAlive()) {
         return false;
      } else if (player.isAlliedTo(owner)) {
         return false;
      } else {
         return player.getAbilities().invulnerable ? false : player.distanceToSqr(owner) <= 100.0;
      }
   }

   private static void addSuppression(ServerPlayer player, UUID ownerId) {
      UUID targetId = player.getUUID();
      PainFlightSuppressionHandler.SuppressedFlight state = SUPPRESSED.get(targetId);
      if (state == null) {
         state = new PainFlightSuppressionHandler.SuppressedFlight(
            player.level().dimension().location().toString(), player.getAbilities().mayfly, player.getAbilities().flying
         );
         SUPPRESSED.put(targetId, state);
      }

      state.owners.add(ownerId);
      boolean changed = player.getAbilities().mayfly || player.getAbilities().flying;
      player.getAbilities().mayfly = false;
      player.getAbilities().flying = false;
      player.resetFallDistance();
      if (changed || player.tickCount % 10 == 0) {
         player.onUpdateAbilities();
      }
   }

   private static void removeSuppression(MinecraftServer server, UUID targetId, UUID ownerId) {
      PainFlightSuppressionHandler.SuppressedFlight state = SUPPRESSED.get(targetId);
      if (state != null) {
         state.owners.remove(ownerId);
         if (state.owners.isEmpty()) {
            SUPPRESSED.remove(targetId);
            ServerPlayer player = server.getPlayerList().getPlayer(targetId);
            if (player != null) {
               player.getAbilities().mayfly = state.hadMayfly;
               player.getAbilities().flying = state.hadFlying && state.hadMayfly;
               player.onUpdateAbilities();
               player.resetFallDistance();
            }
         }
      }
   }

   private static void applyPushFlight(ServerPlayer player, boolean sync) {
      boolean changed = !player.getAbilities().mayfly || !player.getAbilities().flying;
      player.getAbilities().mayfly = true;
      player.getAbilities().flying = true;
      player.resetFallDistance();
      player.hurtMarked = true;
      if (changed || sync) {
         player.onUpdateAbilities();
      }
   }

   private static final class Aura {
      String dimension;
      final Set<UUID> affected = new HashSet<>();

      Aura(String dimension) {
         this.dimension = dimension;
      }
   }

   private static final class PushFlight {
      final String dimension;
      final boolean hadMayfly;
      final boolean hadFlying;

      PushFlight(String dimension, boolean hadMayfly, boolean hadFlying) {
         this.dimension = dimension;
         this.hadMayfly = hadMayfly;
         this.hadFlying = hadFlying;
      }
   }

   private static final class SuppressedFlight {
      final String dimension;
      final boolean hadMayfly;
      final boolean hadFlying;
      final Set<UUID> owners = new HashSet<>();

      SuppressedFlight(String dimension, boolean hadMayfly, boolean hadFlying) {
         this.dimension = dimension;
         this.hadMayfly = hadMayfly;
         this.hadFlying = hadFlying;
      }
   }
}
