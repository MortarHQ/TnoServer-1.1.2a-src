package com.natamus.collective_common_fabric.events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_fabric.check.RegisterMod;
import com.natamus.collective_common_fabric.config.CollectiveConfigHandler;
import com.natamus.collective_common_fabric.data.Constants;
import com.natamus.collective_common_fabric.data.GlobalVariables;
import com.natamus.collective_common_fabric.features.PlayerHeadCacheFeature;
import com.natamus.collective_common_fabric.functions.BlockPosFunctions;
import com.natamus.collective_common_fabric.functions.EntityFunctions;
import com.natamus.collective_common_fabric.functions.HeadFunctions;
import com.natamus.collective_common_fabric.functions.SpawnEntityFunctions;
import com.natamus.collective_common_fabric.objects.SAMObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1268;
import net.minecraft.class_1296;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1496;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2484;
import net.minecraft.class_2549;
import net.minecraft.class_2586;
import net.minecraft.class_2631;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3738;
import net.minecraft.class_9296;
import net.minecraft.class_1297.class_5529;
import net.minecraft.server.MinecraftServer;

public class CollectiveEvents {
   public static WeakHashMap<class_3218, List<class_1297>> entitiesToSpawn = new WeakHashMap<>();
   public static WeakHashMap<class_3218, WeakHashMap<class_1297, class_1297>> entitiesToRide = new WeakHashMap<>();
   public static CopyOnWriteArrayList<Pair<Integer, Runnable>> scheduledServerRunnables = new CopyOnWriteArrayList<>();

   public static void onWorldLoad(class_1937 level) {
      Constants.initConstantData(level);
   }

   public static void onWorldTick(class_3218 serverLevel) {
      if (!entitiesToSpawn.computeIfAbsent(serverLevel, k -> new ArrayList<>()).isEmpty()) {
         class_1297 tospawn = entitiesToSpawn.get(serverLevel).getFirst();
         serverLevel.method_30771(tospawn);
         if (entitiesToRide.computeIfAbsent(serverLevel, k -> new WeakHashMap<>()).containsKey(tospawn)) {
            class_1297 rider = entitiesToRide.get(serverLevel).get(tospawn);
            rider.method_5804(tospawn);
            entitiesToRide.get(serverLevel).remove(tospawn);
         }

         entitiesToSpawn.get(serverLevel).removeFirst();
      }
   }

   public static void onServerTick(MinecraftServer minecraftServer) {
      int serverTickCount = minecraftServer.method_3780();

      for (Pair<Integer, Runnable> pair : scheduledServerRunnables) {
         if ((Integer)pair.getFirst() <= serverTickCount) {
            minecraftServer.execute(new class_3738(serverTickCount, (Runnable)pair.getSecond()));
            scheduledServerRunnables.remove(pair);
         }
      }
   }

   public static boolean onEntityJoinLevel(class_1937 level, class_1297 entity) {
      if (!(entity instanceof class_1309)) {
         return true;
      }

      if (entity instanceof class_1657 player) {
         if (RegisterMod.shouldDoCheck) {
            RegisterMod.joinWorldProcess(level, player);
         }

         if (PlayerHeadCacheFeature.isHeadCachingEnabled()) {
            PlayerHeadCacheFeature.cachePlayer(player);
         }
      }

      if (entity.method_31481()) {
         return true;
      }

      if (GlobalVariables.globalSAMs.isEmpty()) {
         return true;
      }

      Set<String> tags = entity.method_5752();
      if (tags.contains("collective.checked")) {
         return true;
      }

      entity.method_5780("collective.checked");
      class_1299<?> entityType = entity.method_5864();
      if (!GlobalVariables.activeSAMEntityTypes.contains(entityType)) {
         return true;
      }

      boolean isFromSpawner = tags.contains("collective.fromspawner");
      List<SAMObject> possibles = new ArrayList<>();

      for (SAMObject sam : GlobalVariables.globalSAMs) {
         if (sam != null
            && sam.fromEntityType != null
            && sam.fromEntityType.equals(entityType)
            && (!sam.onlyFromSpawner || isFromSpawner)
            && (sam.onlyFromSpawner || !isFromSpawner)) {
            possibles.add(sam);
         }
      }

      int size = possibles.size();
      if (size == 0) {
         return true;
      }

      class_243 eVec = entity.method_19538();
      boolean ageable = entity instanceof class_1296;
      boolean isOnSurface = BlockPosFunctions.isOnSurface(level, eVec);

      for (SAMObject sam : possibles) {
         double num = GlobalVariables.random.nextDouble();
         if (!(num > sam.changeChance)
            && (sam.onlyOnSurface ? isOnSurface : !sam.onlyBelowSurface || !isOnSurface)
            && (!sam.onlyBelowSpecificY || !(eVec.field_1351 >= sam.specificY))) {
            class_1297 to = sam.toEntityType.method_5883(level);
            if (to == null) {
               return true;
            }

            to.method_5814(eVec.field_1352, eVec.field_1351, eVec.field_1350);
            if (ageable && to instanceof class_1296 am) {
               am.method_5614(((class_1296)entity).method_5618());
               to = am;
            }

            boolean ignoreMainhand = false;
            if (sam.itemToHold != null && to instanceof class_1309 le && !le.method_6047().method_7909().equals(sam.itemToHold)) {
               le.method_6122(class_1268.field_5808, new class_1799(sam.itemToHold, 1));
               ignoreMainhand = true;
            }

            boolean ride = false;
            if (EntityFunctions.isHorse(to) && sam.rideNotReplace) {
               class_1496 ah = (class_1496)to;
               ah.method_6766(true);
               ride = true;
            } else if (CollectiveConfigHandler.transferItemsBetweenReplacedEntities) {
               EntityFunctions.transferItemsBetweenEntities(entity, to, ignoreMainhand);
            }

            if (level instanceof class_3218 serverLevel) {
               if (ride) {
                  SpawnEntityFunctions.startRidingEntityOnNextTick(serverLevel, to, entity);
               } else {
                  entity.method_5650(class_5529.field_26999);
               }

               to.method_5780("collective.checked");
               SpawnEntityFunctions.spawnEntityOnNextTick(serverLevel, to);
               return ride;
            } else {
               return true;
            }
         }
      }

      return true;
   }

   public static boolean onBlockBreak(class_1937 level, class_1657 player, class_2338 pos, class_2680 state, class_2586 blockEntity) {
      if (level.field_9236) {
         return true;
      }

      class_2248 block = state.method_26204();
      if (block instanceof class_2484 || block instanceof class_2549) {
         if (player.method_7337()) {
            return true;
         }

         if (!(level.method_8321(pos) instanceof class_2631 skullBlockEntity)) {
            return true;
         }

         class_9296 resolvableProfile = skullBlockEntity.method_11334();
         if (resolvableProfile == null) {
            return true;
         }

         GameProfile gameProfile = resolvableProfile.comp_2413();
         UUID uuid = gameProfile.getId();
         if (uuid.toString().startsWith("ffffffff")) {
            return true;
         }

         class_1799 namedHeadStack = null;
         if (!gameProfile.getName().isEmpty()) {
            namedHeadStack = HeadFunctions.getNewPlayerHead(gameProfile, 1);
         }

         if (namedHeadStack == null) {
            GameProfile uuidGameProfile;
            if (PlayerHeadCacheFeature.cachedGameProfileMap.containsKey(uuid)) {
               uuidGameProfile = PlayerHeadCacheFeature.cachedGameProfileMap.get(uuid);
            } else {
               MinecraftSessionService minecraftSessionService = ((class_3218)level).method_8503().method_3844();
               ProfileResult uuidProfileResult = minecraftSessionService.fetchProfile(uuid, false);
               if (uuidProfileResult == null) {
                  return true;
               }

               uuidGameProfile = uuidProfileResult.profile();
               if (uuidGameProfile.getName().isEmpty()) {
                  return true;
               }

               PlayerHeadCacheFeature.cachedGameProfileMap.put(uuid, uuidGameProfile);
            }

            namedHeadStack = HeadFunctions.getNewPlayerHead(uuidGameProfile, 1);
         }

         if (namedHeadStack != null) {
            level.method_22352(pos, false);
            level.method_8649(new class_1542(level, pos.method_10263(), pos.method_10264() + 0.5, pos.method_10260(), namedHeadStack));
            return false;
         }
      }

      return true;
   }
}
