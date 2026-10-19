package com.foxablazeultimate.ability.skill.yogsothoth;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.entity.YogTimeStopWaveEntity;
import com.foxablazeultimate.network.YogTimeStopStatePayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateEntityTypes;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate", bus = Bus.GAME)
public final class YogTimeStopManager {
   public static final ResourceLocation YOG_SOTHOTH_ID = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "yog_sothoth");
   private static final Map<MinecraftServer, YogTimeStopManager.ActiveStop> ACTIVE_STOPS = Collections.synchronizedMap(new WeakHashMap<>());
   private static volatile boolean clientActive;
   private static volatile UUID clientOwnerId;

   private YogTimeStopManager() {
   }

   public static boolean toggle(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server == null) {
         return false;
      }

      YogTimeStopManager.ActiveStop current = ACTIVE_STOPS.get(server);
      if (current != null) {
         if (!current.ownerId.equals(player.getUUID())) {
            return false;
         }

         stop(server, player);
         return true;
      } else {
         if (isMultiplayerServer(server)) {
            return false;
         }

         if (!player.isAlive()) {
            return false;
         }

         if (!hasPermanentYogSothoth(player)) {
            return false;
         }

         ServerTickRateManager tickRate = server.tickRateManager();
         boolean previouslyFrozen = tickRate.isFrozen();
         int previousStepTicks = tickRate.frozenTicksToRun();
         if (!drainMagicules(player)) {
            return fail(player, "tensura.skill.lack_magicule");
         }

         tickRate.stopSprinting();
         Vec3 centre = centreOf(player);
         YogTimeStopManager.ActiveStop active = new YogTimeStopManager.ActiveStop(
            player.getUUID(), previouslyFrozen, previousStepTicks, player.level().dimension(), centre
         );
         ACTIVE_STOPS.put(server, active);

         try {
            tickRate.setFrozen(true);
            tickRate.stopStepping();
         } catch (RuntimeException failure) {
            if (!tickRate.isFrozen()) {
               ACTIVE_STOPS.remove(server, active);
               refundMagicules(player);
               FoxAblazeUltimateMod.LOGGER.warn("Failed to freeze the server for Yog-Sothoth time stop", failure);
               return false;
            }

            FoxAblazeUltimateMod.LOGGER.warn("Yog-Sothoth time-stop state packet failed during activation", failure);
         }

         try {
            broadcast(server, true);
         } catch (RuntimeException failure) {
            FoxAblazeUltimateMod.LOGGER.warn("Failed to synchronize Yog-Sothoth time-stop state", failure);
         }

         try {
            spawnWave(player.serverLevel(), centre, false);
         } catch (RuntimeException failure) {
            FoxAblazeUltimateMod.LOGGER.warn("Failed to spawn Yog-Sothoth time-stop start wave", failure);
         }

         return true;
      }
   }

   private static boolean fail(ServerPlayer player, String translationKey) {
      try {
         player.displayClientMessage(Component.translatable(translationKey), true);
      } catch (RuntimeException failure) {
         FoxAblazeUltimateMod.LOGGER.debug("Failed to report Yog-Sothoth time-stop activation failure", failure);
      }

      return false;
   }

   public static void stopIfOwner(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server != null) {
         YogTimeStopManager.ActiveStop active = ACTIVE_STOPS.get(server);
         if (active != null && active.ownerId.equals(player.getUUID())) {
            stop(server, player);
         }
      }
   }

   public static boolean isActive(MinecraftServer server) {
      return server != null && ACTIVE_STOPS.containsKey(server);
   }

   public static boolean isActiveForOwner(ServerPlayer player) {
      if (player == null) {
         return false;
      }

      MinecraftServer server = player.getServer();
      YogTimeStopManager.ActiveStop active = server == null ? null : ACTIVE_STOPS.get(server);
      return active != null && active.ownerId.equals(player.getUUID());
   }

   public static boolean isTimeStopActive(Entity entity) {
      if (entity == null) {
         return false;
      }

      if (entity.level().isClientSide()) {
         return clientActive;
      }

      MinecraftServer server = entity.level().getServer();
      return server != null && isActive(server);
   }

   public static boolean isClientActive() {
      return clientActive;
   }

   public static void setClientActive(boolean active) {
      setClientState(active, null);
   }

   public static void setClientState(boolean active, UUID ownerId) {
      clientOwnerId = active ? ownerId : null;
      clientActive = active;
   }

   public static boolean shouldTickDuringTimeStop(Entity entity) {
      if (entity instanceof YogTimeStopWaveEntity) {
         return true;
      }

      if (entity.level().isClientSide()) {
         return !clientActive ? false : isOwnedBy(entity, clientOwnerId);
      }

      MinecraftServer server = entity.level().getServer();
      YogTimeStopManager.ActiveStop stop = server == null ? null : ACTIVE_STOPS.get(server);
      return stop != null && isOwnedBy(entity, stop.ownerId);
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         MinecraftServer server = player.getServer();
         if (server != null) {
            YogTimeStopManager.ActiveStop active = ACTIVE_STOPS.get(server);
            if (active != null && active.ownerId.equals(player.getUUID())) {
               if (isMultiplayerServer(server)) {
                  stop(server, player);
               } else {
                  active.dimension = player.level().dimension();
                  active.lastCentre = centreOf(player);
                  if (player.isAlive() && !player.isRemoved() && hasPermanentYogSothoth(player)) {
                     if (!server.tickRateManager().isFrozen()) {
                        server.tickRateManager().setFrozen(true);
                     }

                     active.ticksUntilDrain--;
                     if (active.ticksUntilDrain <= 0) {
                        active.ticksUntilDrain = YogSothothSkill.timeStopDrainIntervalTicks();
                        if (!drainMagicules(player)) {
                           stop(server, player);
                        }
                     }
                  } else {
                     stop(server, player);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         NetworkManager.sendToPlayer(player, new YogTimeStopStatePayload(isActive(player.getServer()), activeOwner(player.getServer())));
      }
   }

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         stopIfOwner(player);
      } else {
         setClientState(false, null);
      }
   }

   @SubscribeEvent
   public static void onServerStopping(ServerStoppingEvent event) {
      stop(event.getServer(), null);
   }

   private static void stop(MinecraftServer server, ServerPlayer owner) {
      YogTimeStopManager.ActiveStop active = ACTIVE_STOPS.remove(server);
      if (active != null) {
         Vec3 centre = owner != null ? centreOf(owner) : active.lastCentre;
         ServerLevel level = owner != null ? owner.serverLevel() : server.getLevel(active.dimension);
         if (owner != null) {
            try {
               broadcast(server, false);
            } catch (RuntimeException failure) {
               FoxAblazeUltimateMod.LOGGER.warn("Failed to broadcast Yog-Sothoth time-stop end", failure);
            }
         }

         if (owner != null && level != null) {
            try {
               spawnWave(level, centre, true);
            } catch (RuntimeException failure) {
               FoxAblazeUltimateMod.LOGGER.warn("Failed to spawn Yog-Sothoth time-stop end wave", failure);
            }
         }

         restoreTickRate(server, active);
      }
   }

   private static boolean drainMagicules(ServerPlayer player) {
      if (player.isCreative()) {
         return true;
      }

      double cost = YogSothothSkill.timeStopMagiculeCostPerSecond();

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         if (existence == null) {
            return false;
         }

         double available = existence.getMagicule();
         if (!Double.isFinite(available) || available + 1.0E-7 < cost) {
            return false;
         }

         existence.setMagicule(Math.max(0.0, available - cost));
         existence.markDirty();
      } catch (RuntimeException ignored) {
         return false;
      }

      try {
         ((StorageHolder)player).manasCore$sync(player);
      } catch (RuntimeException failure) {
         FoxAblazeUltimateMod.LOGGER.warn("Failed to synchronize magicules for Yog-Sothoth time stop", failure);
      }

      return true;
   }

   private static void refundMagicules(ServerPlayer player) {
      if (!player.isCreative()) {
         double cost = YogSothothSkill.timeStopMagiculeCostPerSecond();

         try {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            if (existence == null) {
               return;
            }

            double available = existence.getMagicule();
            if (!Double.isFinite(available)) {
               return;
            }

            existence.setMagicule(available + cost);
            existence.markDirty();
         } catch (RuntimeException failure) {
            FoxAblazeUltimateMod.LOGGER.warn("Failed to refund magicules after Yog-Sothoth activation failure", failure);
            return;
         }

         try {
            ((StorageHolder)player).manasCore$sync(player);
         } catch (RuntimeException failure) {
            FoxAblazeUltimateMod.LOGGER.warn("Failed to synchronize Yog-Sothoth magicule refund", failure);
         }
      }
   }

   private static void restoreTickRate(MinecraftServer server, YogTimeStopManager.ActiveStop active) {
      if (server != null && active != null) {
         try {
            ServerTickRateManager tickRate = server.tickRateManager();
            tickRate.setFrozen(active.previouslyFrozen);
            if (active.previouslyFrozen && active.previousStepTicks > 0) {
               tickRate.stepGameIfPaused(active.previousStepTicks);
            }
         } catch (RuntimeException failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to restore tick rate after Yog-Sothoth time stop", failure);
         }
      }
   }

   private static boolean hasPermanentYogSothoth(ServerPlayer player) {
      try {
         SkillStorage storage = SkillAPI.getSkillsFrom(player);
         ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill(YOG_SOTHOTH_ID).orElse(null);
         if (isPermanent(instance)) {
            return true;
         }

         for (ManasSkillInstance learned : storage.getLearnedSkills()) {
            if (learned != null && YOG_SOTHOTH_ID.equals(learned.getSkillId()) && isPermanent(learned)) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException ignored) {
         return false;
      }
   }

   private static boolean isPermanent(ManasSkillInstance instance) {
      return instance != null && !instance.isTemporarySkill() && !instance.isSubInstance();
   }

   private static void broadcast(MinecraftServer server, boolean active) {
      YogTimeStopManager.ActiveStop current = ACTIVE_STOPS.get(server);
      YogTimeStopStatePayload payload = new YogTimeStopStatePayload(active, active && current != null ? current.ownerId : null);

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static UUID activeOwner(MinecraftServer server) {
      YogTimeStopManager.ActiveStop active = server == null ? null : ACTIVE_STOPS.get(server);
      return active == null ? null : active.ownerId;
   }

   private static boolean isMultiplayerServer(MinecraftServer server) {
      return server != null && (server.isDedicatedServer() || server.isPublished() || server.getPlayerList().getPlayers().size() > 1);
   }

   private static void spawnWave(ServerLevel level, Vec3 centre, boolean contracting) {
      YogTimeStopWaveEntity wave = (YogTimeStopWaveEntity)((EntityType)FoxAblazeUltimateEntityTypes.YOG_TIME_STOP_WAVE.get()).create(level);
      if (wave != null) {
         wave.initialize(centre, contracting);
         level.addFreshEntity(wave);
      }
   }

   private static Vec3 centreOf(ServerPlayer player) {
      return player.position().add(0.0, player.getBbHeight() * 0.5, 0.0);
   }

   private static boolean isOwnedBy(Entity entity, UUID ownerId) {
      Entity current = entity;

      for (int depth = 0; depth < 4 && current != null; depth++) {
         if (current instanceof Player player) {
            return ownerId != null && ownerId.equals(player.getUUID());
         }

         Entity owner = null;

         try {
            if (current instanceof Projectile projectile) {
               owner = projectile.getOwner();
            }

            if (owner == null && current instanceof OwnableEntity ownable) {
               owner = ownable.getOwner();
            }
         } catch (RuntimeException ignored) {
            owner = null;
         }

         if (owner == null && current instanceof LivingEntity living) {
            try {
               IExistence existence = TensuraStorages.getExistenceFrom(living);
               if (existence != null) {
                  UUID summoner = existence.getSummoner();
                  UUID permanent = existence.getPermanentOwner();
                  UUID temporary = existence.getTemporaryOwner();
                  if (ownerId != null && (ownerId.equals(summoner) || ownerId.equals(permanent) || ownerId.equals(temporary))) {
                     return true;
                  }
               }
            } catch (RuntimeException var11) {
            }
         }

         if (owner == current) {
            return false;
         }

         current = owner;
      }

      return false;
   }

   private static final class ActiveStop {
      private final UUID ownerId;
      private final boolean previouslyFrozen;
      private final int previousStepTicks;
      private ResourceKey<Level> dimension;
      private Vec3 lastCentre;
      private int ticksUntilDrain = YogSothothSkill.timeStopDrainIntervalTicks();

      private ActiveStop(UUID ownerId, boolean previouslyFrozen, int previousStepTicks, ResourceKey<Level> dimension, Vec3 lastCentre) {
         this.ownerId = ownerId;
         this.previouslyFrozen = previouslyFrozen;
         this.previousStepTicks = previousStepTicks;
         this.dimension = dimension;
         this.lastCentre = lastCentre;
      }
   }
}
