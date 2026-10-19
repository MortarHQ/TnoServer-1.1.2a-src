package com.mortal_cultivation.event;

import com.mortal_cultivation.dimension.MindscapeDimension;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class MindscapeChallengeManager {
   private static final int MISSING_ENTITY_RESPAWN_TICKS = 20;
   private static final String TAG_CHALLENGE_SKELETON = "mortal_cultivation_mindscape_challenge";
   private static final String TAG_CHALLENGE_OWNER = "mortal_cultivation_mindscape_owner";
   private static final Map<UUID, Integer> activeChallenges = new HashMap<>();
   private static final Map<UUID, Integer> missingChallengeEntityTicks = new HashMap<>();

   public static void startChallenge(ServerPlayer player, ServerLevel level) {
      cleanupChallenge(player.getUUID(), level);
      activeChallenges.remove(player.getUUID());
      missingChallengeEntityTicks.remove(player.getUUID());
      spawnChallengeSkeleton(player, level);
   }

   private static boolean spawnChallengeSkeleton(ServerPlayer player, ServerLevel level) {
      cleanupChallengeSkeletons(player.getUUID(), level);
      Integer skeletonId = createChallengeSkeleton(player, level);
      if (skeletonId == null) {
         return false;
      }

      activeChallenges.put(player.getUUID(), skeletonId);
      missingChallengeEntityTicks.remove(player.getUUID());
      return true;
   }

   private static Integer createChallengeSkeleton(ServerPlayer player, ServerLevel level) {
      WitherSkeleton skeleton = (WitherSkeleton)EntityType.WITHER_SKELETON.create(level);
      if (skeleton == null) {
         return null;
      }

      skeleton.setPos(0.5, 65.0, 5.5);
      skeleton.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60.0);
      skeleton.setHealth(60.0F);
      skeleton.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
      skeleton.setTarget(player);
      skeleton.setPersistenceRequired();
      skeleton.getPersistentData().putBoolean("mortal_cultivation_mindscape_challenge", true);
      skeleton.getPersistentData().putUUID("mortal_cultivation_mindscape_owner", player.getUUID());
      return !level.addFreshEntity(skeleton) ? null : skeleton.getId();
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if (!activeChallenges.isEmpty()) {
         Iterator<Entry<UUID, Integer>> it = activeChallenges.entrySet().iterator();

         while (it.hasNext()) {
            Entry<UUID, Integer> entry = it.next();
            UUID playerUUID = entry.getKey();
            int skeletonId = entry.getValue();
            ServerLevel mindscape = null;

            for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
               if (level.dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
                  mindscape = level;
                  break;
               }
            }

            if (mindscape == null) {
               it.remove();
               missingChallengeEntityTicks.remove(playerUUID);
            } else {
               ServerPlayer player = findPlayerInMindscape(playerUUID);
               if (player == null) {
                  cleanupChallenge(playerUUID, mindscape);
                  it.remove();
               } else {
                  Entity entity = mindscape.getEntity(skeletonId);
                  if (!(entity instanceof WitherSkeleton)) {
                     WitherSkeleton taggedSkeleton = findChallengeSkeleton(mindscape, playerUUID);
                     if (taggedSkeleton != null) {
                        entity = taggedSkeleton;
                        entry.setValue(taggedSkeleton.getId());
                     }
                  }

                  if (entity instanceof WitherSkeleton skeleton && skeleton.isAlive() && !skeleton.isRemoved()) {
                     missingChallengeEntityTicks.remove(playerUUID);
                     double x = skeleton.getX();
                     double z = skeleton.getZ();
                     boolean outOfBounds = x < -10.0 || x > 10.0 || z < -10.0 || z > 10.0;
                     if (outOfBounds) {
                        skeleton.teleportTo(0.5, 65.0, 5.5);
                     }

                     if (skeleton.getY() < 60.0) {
                        skeleton.teleportTo(0.5, 65.0, 5.5);
                     }

                     if (skeleton.getTarget() != player) {
                        skeleton.setTarget(player);
                     }
                  } else {
                     int missingTicks = missingChallengeEntityTicks.merge(playerUUID, 1, Integer::sum);
                     if (missingTicks >= 20 && spawnChallengeSkeleton(player, mindscape)) {
                        entry.setValue(activeChallenges.get(playerUUID));
                        missingChallengeEntityTicks.remove(playerUUID);
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof WitherSkeleton skeleton && skeleton.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
         UUID playerUUID = findChallengeOwner(skeleton.getId());
         if (playerUUID != null) {
            completeVictory(playerUUID);
         }
      }

      if (event.getEntity() instanceof ServerPlayer player && player.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
         event.setCanceled(true);
         player.setHealth(player.getMaxHealth() / 2.0F);
         cleanupChallenge(player.getUUID(), (ServerLevel)player.level());
         activeChallenges.remove(player.getUUID());
         missingChallengeEntityTicks.remove(player.getUUID());
         onDefeat(player);
      }
   }

   @SubscribeEvent
   public static void onLivingDamagePost(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event) {
      if (event.getEntity() instanceof WitherSkeleton skeleton) {
         if (skeleton.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            if (!(skeleton.getHealth() > 0.0F)) {
               UUID playerUUID = findChallengeOwner(skeleton.getId());
               if (playerUUID != null) {
                  completeVictory(playerUUID);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
      if (event.getEntity() instanceof WitherSkeleton skeleton) {
         if (event.getLevel().dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            if (skeleton.getRemovalReason() == RemovalReason.KILLED) {
               UUID playerUUID = findChallengeOwner(skeleton.getId());
               if (playerUUID != null) {
                  completeVictory(playerUUID);
               }
            }
         }
      }
   }

   private static void onVictory(ServerPlayer player) {
      Races raceData = RaceAPI.getRaceFrom(player);
      raceData.setRace((ManasRace)TensuraRaces.HUMAN.get(), false);
      raceData.markDirty();
      MindscapeSleepHandler.returnToOrigin(player, true);
   }

   private static void completeVictory(UUID playerUUID) {
      Integer skeletonId = activeChallenges.remove(playerUUID);
      missingChallengeEntityTicks.remove(playerUUID);
      ServerLevel mindscape = null;

      for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
         if (level.dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            mindscape = level;
            break;
         }
      }

      if (mindscape != null && skeletonId != null) {
         Entity entity = mindscape.getEntity(skeletonId);
         if (entity != null && !entity.isRemoved()) {
            entity.discard();
         }

         cleanupChallengeSkeletons(playerUUID, mindscape);
      }

      ServerPlayer player = findPlayerInMindscape(playerUUID);
      if (player != null) {
         onVictory(player);
      }
   }

   private static void onDefeat(ServerPlayer player) {
      MindscapeSleepHandler.returnToOrigin(player, false);
   }

   private static void cleanupChallenge(UUID playerUUID, ServerLevel level) {
      Integer skeletonId = activeChallenges.get(playerUUID);
      if (skeletonId != null) {
         Entity entity = level.getEntity(skeletonId);
         if (entity != null) {
            entity.discard();
         }
      }

      cleanupChallengeSkeletons(playerUUID, level);
      missingChallengeEntityTicks.remove(playerUUID);
   }

   public static void cleanupForPlayer(UUID playerUUID, ServerLevel level) {
      cleanupChallenge(playerUUID, level);
      activeChallenges.remove(playerUUID);
      missingChallengeEntityTicks.remove(playerUUID);
   }

   private static UUID findChallengeOwner(int skeletonId) {
      for (Entry<UUID, Integer> entry : activeChallenges.entrySet()) {
         if (entry.getValue() == skeletonId) {
            return entry.getKey();
         }
      }

      return null;
   }

   private static WitherSkeleton findChallengeSkeleton(ServerLevel level, UUID playerUUID) {
      for (Entity entity : level.getAllEntities()) {
         if (isChallengeSkeleton(entity, playerUUID)) {
            return (WitherSkeleton)entity;
         }
      }

      return null;
   }

   private static void cleanupChallengeSkeletons(UUID playerUUID, ServerLevel level) {
      List<Entity> toDiscard = new ArrayList<>();

      for (Entity entity : level.getAllEntities()) {
         if (isChallengeSkeleton(entity, playerUUID) && !entity.isRemoved()) {
            toDiscard.add(entity);
         }
      }

      for (Entity entity : toDiscard) {
         entity.discard();
      }
   }

   private static boolean isChallengeSkeleton(Entity entity, UUID playerUUID) {
      if (!(entity instanceof WitherSkeleton)) {
         return false;
      } else if (!entity.getPersistentData().getBoolean("mortal_cultivation_mindscape_challenge")) {
         return false;
      } else {
         return !entity.getPersistentData().hasUUID("mortal_cultivation_mindscape_owner")
            ? false
            : entity.getPersistentData().getUUID("mortal_cultivation_mindscape_owner").equals(playerUUID);
      }
   }

   private static ServerPlayer findPlayerInMindscape(UUID uuid) {
      for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
         if (level.dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            return (ServerPlayer)level.getPlayerByUUID(uuid);
         }
      }

      return null;
   }

   public static boolean isInChallenge(UUID uuid) {
      return activeChallenges.containsKey(uuid);
   }
}
