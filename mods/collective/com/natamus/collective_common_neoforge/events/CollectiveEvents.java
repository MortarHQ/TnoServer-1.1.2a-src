package com.natamus.collective_common_neoforge.events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_neoforge.check.RegisterMod;
import com.natamus.collective_common_neoforge.config.CollectiveConfigHandler;
import com.natamus.collective_common_neoforge.data.Constants;
import com.natamus.collective_common_neoforge.data.GlobalVariables;
import com.natamus.collective_common_neoforge.features.PlayerHeadCacheFeature;
import com.natamus.collective_common_neoforge.functions.BlockPosFunctions;
import com.natamus.collective_common_neoforge.functions.EntityFunctions;
import com.natamus.collective_common_neoforge.functions.HeadFunctions;
import com.natamus.collective_common_neoforge.functions.SpawnEntityFunctions;
import com.natamus.collective_common_neoforge.objects.SAMObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CollectiveEvents {
   public static WeakHashMap<ServerLevel, List<Entity>> entitiesToSpawn = new WeakHashMap<>();
   public static WeakHashMap<ServerLevel, WeakHashMap<Entity, Entity>> entitiesToRide = new WeakHashMap<>();
   public static CopyOnWriteArrayList<Pair<Integer, Runnable>> scheduledServerRunnables = new CopyOnWriteArrayList<>();

   public static void onWorldLoad(Level level) {
      Constants.initConstantData(level);
   }

   public static void onWorldTick(ServerLevel serverLevel) {
      if (!entitiesToSpawn.computeIfAbsent(serverLevel, k -> new ArrayList<>()).isEmpty()) {
         Entity tospawn = entitiesToSpawn.get(serverLevel).getFirst();
         serverLevel.addFreshEntityWithPassengers(tospawn);
         if (entitiesToRide.computeIfAbsent(serverLevel, k -> new WeakHashMap<>()).containsKey(tospawn)) {
            Entity rider = entitiesToRide.get(serverLevel).get(tospawn);
            rider.startRiding(tospawn);
            entitiesToRide.get(serverLevel).remove(tospawn);
         }

         entitiesToSpawn.get(serverLevel).removeFirst();
      }
   }

   public static void onServerTick(MinecraftServer minecraftServer) {
      int serverTickCount = minecraftServer.getTickCount();

      for (Pair<Integer, Runnable> pair : scheduledServerRunnables) {
         if ((Integer)pair.getFirst() <= serverTickCount) {
            minecraftServer.execute(new TickTask(serverTickCount, (Runnable)pair.getSecond()));
            scheduledServerRunnables.remove(pair);
         }
      }
   }

   public static boolean onEntityJoinLevel(Level level, Entity entity) {
      if (!(entity instanceof LivingEntity)) {
         return true;
      }

      if (entity instanceof Player player) {
         if (RegisterMod.shouldDoCheck) {
            RegisterMod.joinWorldProcess(level, player);
         }

         if (PlayerHeadCacheFeature.isHeadCachingEnabled()) {
            PlayerHeadCacheFeature.cachePlayer(player);
         }
      }

      if (entity.isRemoved()) {
         return true;
      }

      if (GlobalVariables.globalSAMs.isEmpty()) {
         return true;
      }

      Set<String> tags = entity.getTags();
      if (tags.contains("collective.checked")) {
         return true;
      }

      entity.addTag("collective.checked");
      EntityType<?> entityType = entity.getType();
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

      Vec3 eVec = entity.position();
      boolean ageable = entity instanceof AgeableMob;
      boolean isOnSurface = BlockPosFunctions.isOnSurface(level, eVec);

      for (SAMObject sam : possibles) {
         double num = GlobalVariables.random.nextDouble();
         if (!(num > sam.changeChance)
            && (sam.onlyOnSurface ? isOnSurface : !sam.onlyBelowSurface || !isOnSurface)
            && (!sam.onlyBelowSpecificY || !(eVec.y >= sam.specificY))) {
            Entity to = sam.toEntityType.create(level);
            if (to == null) {
               return true;
            }

            to.setPos(eVec.x, eVec.y, eVec.z);
            if (ageable && to instanceof AgeableMob am) {
               am.setAge(((AgeableMob)entity).getAge());
               to = am;
            }

            boolean ignoreMainhand = false;
            if (sam.itemToHold != null && to instanceof LivingEntity le && !le.getMainHandItem().getItem().equals(sam.itemToHold)) {
               le.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(sam.itemToHold, 1));
               ignoreMainhand = true;
            }

            boolean ride = false;
            if (EntityFunctions.isHorse(to) && sam.rideNotReplace) {
               AbstractHorse ah = (AbstractHorse)to;
               ah.setTamed(true);
               ride = true;
            } else if (CollectiveConfigHandler.transferItemsBetweenReplacedEntities) {
               EntityFunctions.transferItemsBetweenEntities(entity, to, ignoreMainhand);
            }

            if (level instanceof ServerLevel serverLevel) {
               if (ride) {
                  SpawnEntityFunctions.startRidingEntityOnNextTick(serverLevel, to, entity);
               } else {
                  entity.remove(RemovalReason.DISCARDED);
               }

               to.addTag("collective.checked");
               SpawnEntityFunctions.spawnEntityOnNextTick(serverLevel, to);
               return ride;
            } else {
               return true;
            }
         }
      }

      return true;
   }

   public static boolean onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
      if (level.isClientSide) {
         return true;
      }

      Block block = state.getBlock();
      if (block instanceof SkullBlock || block instanceof WallSkullBlock) {
         if (player.isCreative()) {
            return true;
         }

         if (!(level.getBlockEntity(pos) instanceof SkullBlockEntity skullBlockEntity)) {
            return true;
         }

         ResolvableProfile resolvableProfile = skullBlockEntity.getOwnerProfile();
         if (resolvableProfile == null) {
            return true;
         }

         GameProfile gameProfile = resolvableProfile.gameProfile();
         UUID uuid = gameProfile.getId();
         if (uuid.toString().startsWith("ffffffff")) {
            return true;
         }

         ItemStack namedHeadStack = null;
         if (!gameProfile.getName().isEmpty()) {
            namedHeadStack = HeadFunctions.getNewPlayerHead(gameProfile, 1);
         }

         if (namedHeadStack == null) {
            GameProfile uuidGameProfile;
            if (PlayerHeadCacheFeature.cachedGameProfileMap.containsKey(uuid)) {
               uuidGameProfile = PlayerHeadCacheFeature.cachedGameProfileMap.get(uuid);
            } else {
               MinecraftSessionService minecraftSessionService = ((ServerLevel)level).getServer().getSessionService();
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
            level.destroyBlock(pos, false);
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), namedHeadStack));
            return false;
         }
      }

      return true;
   }
}
