package com.tensura_tno.event;

import com.tensura_tno.TensuraTNOMod;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class ShizuFinalKillAdvancementHandler {
   private static final ResourceLocation ADVANCEMENT_ID = ResourceLocation.fromNamespaceAndPath("tensura_tno", "farewell_shizu");
   private static final ResourceLocation SHIZU_ID = ResourceLocation.fromNamespaceAndPath("tensura", "shizu");
   private static final ResourceLocation IFRIT_ID = ResourceLocation.fromNamespaceAndPath("tensura", "ifrit");
   private static final String CRITERION_NAME = "trigger";
   private static final String SHIZU_HOST_TAG = "ShizuNBT";
   private static final String KILLER_TAG = "tensura_tno.farewell_shizu_killer";
   private static final int PENDING_TTL_TICKS = 200;
   private static final double MATCH_DISTANCE_SQ = 16.0;
   private static final Map<ResourceKey<Level>, List<ShizuFinalKillAdvancementHandler.PendingKill>> PENDING_KILLS = new HashMap<>();

   private ShizuFinalKillAdvancementHandler() {
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide()) {
         ResourceLocation entityId = entityId(entity);
         if (IFRIT_ID.equals(entityId)) {
            rememberIfritKill(event);
         } else if (SHIZU_ID.equals(entityId)) {
            grantForFinalShizuDeath(entity, event.getSource());
         }
      }
   }

   @SubscribeEvent
   public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (event.getEntity() instanceof LivingEntity entity) {
            if (SHIZU_ID.equals(entityId(entity))) {
               if (isFinalRestoredShizu(entity)) {
                  ShizuFinalKillAdvancementHandler.PendingKill pending = takeMatchingPendingKill(level, entity.position());
                  if (pending != null) {
                     entity.getPersistentData().putUUID("tensura_tno.farewell_shizu_killer", pending.playerId());
                  }
               }
            }
         }
      }
   }

   private static void rememberIfritKill(LivingDeathEvent event) {
      LivingEntity ifrit = event.getEntity();
      if (hasShizuHost(ifrit)) {
         if (ifrit.level() instanceof ServerLevel level) {
            ServerPlayer player = resolvePlayer(event.getSource(), ifrit);
            if (player != null) {
               ResourceKey<Level> dimension = level.dimension();
               prune(level, dimension);
               PENDING_KILLS.computeIfAbsent(dimension, ignored -> new ArrayList<>())
                  .add(new ShizuFinalKillAdvancementHandler.PendingKill(player.getUUID(), ifrit.position(), level.getGameTime()));
            }
         }
      }
   }

   private static void grantForFinalShizuDeath(LivingEntity shizu, DamageSource source) {
      ServerPlayer player = null;
      CompoundTag data = shizu.getPersistentData();
      if (data.hasUUID("tensura_tno.farewell_shizu_killer")) {
         UUID playerId = data.getUUID("tensura_tno.farewell_shizu_killer");
         MinecraftServer server = shizu.getServer();
         if (server != null) {
            player = server.getPlayerList().getPlayer(playerId);
         }

         data.remove("tensura_tno.farewell_shizu_killer");
      }

      if (player == null && isFinalRestoredShizu(shizu)) {
         player = resolvePlayer(source, shizu);
      }

      if (player != null) {
         grant(player);
      }
   }

   private static ShizuFinalKillAdvancementHandler.PendingKill takeMatchingPendingKill(ServerLevel level, Vec3 position) {
      ResourceKey<Level> dimension = level.dimension();
      prune(level, dimension);
      List<ShizuFinalKillAdvancementHandler.PendingKill> pending = PENDING_KILLS.get(dimension);
      if (pending != null && !pending.isEmpty()) {
         int bestIndex = -1;
         double bestDistance = 16.0;

         for (int i = 0; i < pending.size(); i++) {
            double distance = pending.get(i).position().distanceToSqr(position);
            if (distance <= bestDistance) {
               bestDistance = distance;
               bestIndex = i;
            }
         }

         if (bestIndex < 0) {
            return null;
         }

         ShizuFinalKillAdvancementHandler.PendingKill result = pending.remove(bestIndex);
         if (pending.isEmpty()) {
            PENDING_KILLS.remove(dimension);
         }

         return result;
      } else {
         return null;
      }
   }

   private static void prune(ServerLevel level, ResourceKey<Level> dimension) {
      List<ShizuFinalKillAdvancementHandler.PendingKill> pending = PENDING_KILLS.get(dimension);
      if (pending != null) {
         long now = level.getGameTime();
         pending.removeIf(kill -> now - kill.gameTime() > 200L);
         if (pending.isEmpty()) {
            PENDING_KILLS.remove(dimension);
         }
      }
   }

   private static boolean hasShizuHost(LivingEntity entity) {
      try {
         CompoundTag saved = entity.saveWithoutId(new CompoundTag());
         return saved.contains("ShizuNBT", 10);
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean isFinalRestoredShizu(LivingEntity entity) {
      try {
         Method isDying = entity.getClass().getMethod("isDying");
         return Boolean.TRUE.equals(isDying.invoke(entity));
      } catch (Exception ignored) {
         return false;
      }
   }

   private static ServerPlayer resolvePlayer(DamageSource source, LivingEntity victim) {
      Entity attacker = source.getEntity();
      if (attacker instanceof ServerPlayer player) {
         return player;
      } else {
         if (attacker instanceof OwnableEntity ownable) {
            Entity owner = ownable.getOwner();
            if (owner instanceof ServerPlayer player) {
               return player;
            }

            UUID ownerId = ownable.getOwnerUUID();
            ServerPlayer player = getOnlinePlayer(victim, ownerId);
            if (player != null) {
               return player;
            }
         }

         if (attacker instanceof LivingEntity living) {
            ServerPlayer summoner = resolveSummoner(victim, living);
            if (summoner != null) {
               return summoner;
            }
         }

         LivingEntity lastAttacker = victim.getLastAttacker();
         if (lastAttacker instanceof ServerPlayer player) {
            return player;
         } else {
            return lastAttacker != null && lastAttacker != attacker ? resolveSummoner(victim, lastAttacker) : null;
         }
      }
   }

   private static ServerPlayer resolveSummoner(LivingEntity context, LivingEntity entity) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         return getOnlinePlayer(context, existence.getSummoner());
      } catch (Exception ignored) {
         return null;
      }
   }

   private static ServerPlayer getOnlinePlayer(LivingEntity context, UUID playerId) {
      if (playerId == null) {
         return null;
      }

      MinecraftServer server = context.getServer();
      return server == null ? null : server.getPlayerList().getPlayer(playerId);
   }

   private static void grant(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server != null) {
         ServerAdvancementManager manager = server.getAdvancements();
         AdvancementHolder holder = manager.get(ADVANCEMENT_ID);
         if (holder == null) {
            TensuraTNOMod.LOGGER
               .warn("[TensuraTNO] Advancement {} not found, cannot grant farewell Shizu to {}", ADVANCEMENT_ID, player.getGameProfile().getName());
         } else {
            PlayerAdvancements progress = player.getAdvancements();
            if (!progress.getOrStartProgress(holder).isDone()) {
               progress.award(holder, "trigger");
            }
         }
      }
   }

   private static ResourceLocation entityId(Entity entity) {
      return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
   }

   private record PendingKill(UUID playerId, Vec3 position, long gameTime) {
   }
}
