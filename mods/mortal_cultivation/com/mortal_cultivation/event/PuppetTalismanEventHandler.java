package com.mortal_cultivation.event;

import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class PuppetTalismanEventHandler {
   private static final ConcurrentHashMap<UUID, LivingEntity> playerAttackTargets = new ConcurrentHashMap<>();
   private static final int MAX_FOLLOWING_DISTANCE = 20;
   private static final int TELEPORT_DISTANCE = 30;

   @SubscribeEvent
   public static void onLivingAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player) {
         LivingEntity target = event.getEntity();
         if (isOwnedByPlayer(target, player)) {
            return;
         }

         recordPlayerAttackTarget(player, target);
         setPuppetsTarget(player, target);
      } else if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof LivingEntity attacker) {
         setPuppetsTarget(player, attacker);
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player player) {
         Level level = player.level();
         if (!level.isClientSide) {
            AABB searchArea = player.getBoundingBox().inflate(128.0);

            for (Mob mob : level.getEntitiesOfClass(Mob.class, searchArea)) {
               if (isOwnedByPlayer(mob, player)) {
                  releasePuppet(mob);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if (ServerLifecycleHooks.getCurrentServer() != null) {
         for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
            for (Player player : level.players()) {
               handlePuppetsFollowing(player);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity instanceof Player player && !player.level().isClientSide) {
         AABB searchArea = player.getBoundingBox().inflate(128.0);

         for (Mob mob : player.level().getEntitiesOfClass(Mob.class, searchArea)) {
            if (isOwnedByPlayer(mob, player)) {
               mob.setTarget(null);
            }
         }
      }
   }

   private static void recordPlayerAttackTarget(Player player, LivingEntity target) {
      UUID playerUUID = player.getUUID();
      if (target != null) {
         playerAttackTargets.put(playerUUID, target);
      } else {
         playerAttackTargets.remove(playerUUID);
      }
   }

   private static void setPuppetsTarget(Player player, LivingEntity target) {
      Level level = player.level();
      if (!level.isClientSide) {
         AABB searchArea = player.getBoundingBox().inflate(40.0);

         for (Mob mob : level.getEntitiesOfClass(Mob.class, searchArea)) {
            if (isOwnedByPlayer(mob, player) && mob != target && !isOwnedByPlayer(target, player)) {
               mob.setTarget(target);
            }
         }
      }
   }

   private static void handlePuppetsFollowing(Player player) {
      Level level = player.level();
      if (!level.isClientSide) {
         AABB searchArea = player.getBoundingBox().inflate(60.0);

         for (Mob mob : level.getEntitiesOfClass(Mob.class, searchArea)) {
            if (isOwnedByPlayer(mob, player)
               && !(
                  mob instanceof TensuraTamableEntity tamable
                     ? tamable.isOrderedToSit() || tamable.isWandering()
                     : mob instanceof TamableAnimal tamable && tamable.isOrderedToSit()
               )) {
               double distanceSquared = mob.distanceToSqr(player);
               if (mob.getTarget() == null && distanceSquared > 400.0) {
                  mob.getNavigation().moveTo(player, 1.0);
               }

               if (distanceSquared > 900.0) {
                  attemptTeleportToSafeLocation(mob, player);
               }
            }
         }
      }
   }

   private static void attemptTeleportToSafeLocation(Mob mob, Player player) {
      if (mob instanceof TensuraTamableEntity tamable) {
         if (tamable.isOrderedToSit() || tamable.isWandering()) {
            return;
         }
      } else if (mob instanceof TamableAnimal tamable && tamable.isOrderedToSit()) {
         return;
      }

      Level level = player.level();
      int searchRadius = 6;

      for (int i = 0; i < 15; i++) {
         double offsetX = player.getRandom().nextDouble() * (searchRadius * 2) - searchRadius;
         double offsetZ = player.getRandom().nextDouble() * (searchRadius * 2) - searchRadius;
         BlockPos targetBlockPos = new BlockPos((int)(player.getX() + offsetX), (int)player.getY(), (int)(player.getZ() + offsetZ));
         BlockPos safePos = findSafePositionNear(level, targetBlockPos, mob);
         if (safePos != null) {
            mob.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
            mob.getNavigation().moveTo(player, 1.0);
            return;
         }
      }
   }

   private static BlockPos findSafePositionNear(Level level, BlockPos pos, Mob mob) {
      for (int y = 0; y >= -5; y--) {
         BlockPos checkPos = pos.above(y);
         BlockState blockState = level.getBlockState(checkPos);
         if (!blockState.isAir() && !blockState.liquid() && blockState.isSolidRender(level, checkPos)) {
            BlockPos standPos = checkPos.above();
            boolean hasSpace = true;
            int entityHeight = (int)Math.ceil(mob.getBbHeight());

            for (int h = 0; h < entityHeight; h++) {
               BlockPos spacePos = standPos.above(h);
               BlockState spaceState = level.getBlockState(spacePos);
               if (!spaceState.isAir() && !spaceState.canBeReplaced()) {
                  hasSpace = false;
                  break;
               }
            }

            if (hasSpace) {
               return standPos;
            }
         }
      }

      return null;
   }

   private static void releasePuppet(Mob mob) {
      if (mob instanceof TamableAnimal tamable) {
         tamable.setOwnerUUID(null);
         tamable.setTame(false, false);
      } else {
         IExistence existence = TensuraStorages.getExistenceFrom(mob);
         existence.setPermanentOwner(null);
         existence.setTemporaryOwner(null);
         existence.markDirty();
      }

      mob.getPersistentData().remove("PuppetTalismanTamed");
      mob.setTarget(null);
   }

   private static boolean isOwnedByPlayer(LivingEntity entity, Player player) {
      if (!entity.getPersistentData().getBoolean("PuppetTalismanTamed")) {
         return false;
      } else {
         UUID playerUUID = player.getUUID();
         if (entity instanceof TamableAnimal tamable) {
            return tamable.isOwnedBy(player);
         } else {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            UUID permanentOwner = existence.getPermanentOwner();
            UUID temporaryOwner = existence.getTemporaryOwner();
            return permanentOwner != null && permanentOwner.equals(playerUUID) || temporaryOwner != null && temporaryOwner.equals(playerUUID);
         }
      }
   }
}
