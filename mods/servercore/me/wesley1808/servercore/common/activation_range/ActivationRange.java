package me.wesley1808.servercore.common.activation_range;

import java.util.function.Predicate;
import me.wesley1808.servercore.common.config.Config;
import me.wesley1808.servercore.common.config.data.activation_range.ActivationRangeConfig;
import me.wesley1808.servercore.common.config.data.activation_range.ActivationType;
import me.wesley1808.servercore.common.config.data.activation_range.CustomActivationType;
import me.wesley1808.servercore.common.utils.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Bee.BeeGoToHiveGoal;
import net.minecraft.world.entity.animal.Bee.BeeGoToKnownFlowerGoal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ActivationRange {
   private static final double MINIMUM_MOVEMENT = 0.001;
   private static final Predicate<Goal> BEE_GOAL_IMMUNITIES = goal -> goal instanceof BeeGoToKnownFlowerGoal || goal instanceof BeeGoToHiveGoal;
   private static final Activity[] VILLAGER_PANIC_IMMUNITIES = new Activity[]{Activity.HIDE, Activity.PRE_RAID, Activity.RAID, Activity.PANIC};

   public static ActivationType initializeEntityActivationType(Entity entity) {
      ActivationRangeConfig config = Config.get().activationRange();

      for (CustomActivationType type : config.activationTypes()) {
         for (EntityTypeTest<? super Entity, ?> matcher : type.matchers()) {
            if (matcher.tryCast(entity) != null) {
               return type;
            }
         }
      }

      return config.defaultActivationType();
   }

   public static boolean isExcluded(Entity entity) {
      ActivationType type = entity.servercore$getActivationType();
      int tickInterval = type.tickInterval();
      return tickInterval == 0
         || tickInterval == 1
         || type.activationRange() <= 0
         || entity instanceof Player
         || entity instanceof ThrowableItemProjectile
         || entity instanceof EnderDragon
         || entity instanceof EnderDragonPart
         || entity instanceof WitherBoss
         || entity instanceof Fireball
         || entity instanceof LightningBolt
         || entity instanceof PrimedTnt
         || entity instanceof EndCrystal
         || entity instanceof FireworkRocketEntity
         || entity instanceof EyeOfEnder
         || entity instanceof ThrownTrident
         || Config.get().activationRange().excludedEntityTypes().contains(entity.getType());
   }

   public static void activateEntities(ServerLevel level, int currentTick) {
      ActivationRangeConfig config = Config.get().activationRange();
      if (config.enabled()) {
         int maxRange = Integer.MIN_VALUE;

         for (CustomActivationType type : config.activationTypes()) {
            maxRange = Math.max(type.activationRange(), maxRange);
         }

         maxRange = Math.min((level.getServer().getPlayerList().getViewDistance() << 4) - 8, maxRange);

         for (ServerPlayer player : level.players()) {
            if (!player.isSpectator()) {
               AABB maxBB = player.getBoundingBox().inflate(maxRange, 256.0, maxRange);

               for (Entity entity : level.getEntities(player, maxBB)) {
                  activateEntity(player, entity, currentTick, config);
               }
            }
         }
      }
   }

   private static void activateEntity(ServerPlayer player, Entity entity, int currentTick, ActivationRangeConfig config) {
      if (currentTick > entity.servercore$getActivatedTick() && (entity.servercore$isExcluded() || isWithinRange(player, entity, config))) {
         entity.servercore$setActivatedTick(currentTick + 19);
      }
   }

   private static boolean isWithinRange(ServerPlayer player, Entity entity, ActivationRangeConfig config) {
      ActivationType type = entity.servercore$getActivationType();
      int range = type.activationRange();
      int chessboardDistance = Math.max(Math.abs(player.getBlockX() - entity.getBlockX()), Math.abs(player.getBlockZ() - entity.getBlockZ()));
      if (chessboardDistance > range) {
         return false;
      }

      if (!config.useVerticalRange()) {
         return true;
      }

      int deltaY = entity.getBlockY() - player.getBlockY();
      return deltaY <= range && deltaY >= -range || deltaY > 0 && type.extraHeightUp() || deltaY < 0 && type.extraHeightDown();
   }

   public static int checkEntityImmunities(Entity entity, int currentTick, ActivationRangeConfig config) {
      int inactiveWakeUpImmunity = checkInactiveWakeup(entity, currentTick);
      if (inactiveWakeUpImmunity > -1) {
         return inactiveWakeUpImmunity;
      }

      if (entity.getRemainingFireTicks() > 0) {
         return 2;
      }

      if (entity.servercore$getActivatedImmunityTick() >= currentTick) {
         return 1;
      }

      if (!entity.isAlive()) {
         return 40;
      }

      if (entity.isInWater() && entity.isPushedByFluid() && !(entity instanceof AgeableMob) && !(entity instanceof Villager) && !(entity instanceof Boat)) {
         return 100;
      }

      if (entity instanceof ItemEntity || entity instanceof ExperienceOrb) {
         Vec3 movement = entity.getDeltaMovement();
         if (Math.abs(movement.x) > 0.001 || Math.abs(movement.z) > 0.001 || movement.y > 0.001) {
            return 20;
         }
      }

      if (entity instanceof AbstractArrow projectile) {
         if (!projectile.inGround) {
            return 1;
         }
      } else if (!entity.onGround() && !entity.isInWater() && !(entity instanceof FlyingMob) && !(entity instanceof Bat)) {
         return 10;
      }

      if (entity instanceof LivingEntity living) {
         if (living.jumping || !living.getActiveEffects().isEmpty() || living.onClimbable()) {
            return 1;
         }

         if (living instanceof Mob mob) {
            if (mob.getTarget() != null || mob.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
               return 20;
            }

            if (mob instanceof Bee bee && (bee.beePollinateGoal.isPollinating() || bee.isAngry() || Util.hasTasks(bee.goalSelector, BEE_GOAL_IMMUNITIES))) {
               return 20;
            }

            if (mob instanceof Villager villager) {
               Brain<Villager> brain = villager.getBrain();
               if (config.villagerTickPanic()) {
                  for (Activity activity : VILLAGER_PANIC_IMMUNITIES) {
                     if (brain.isActive(activity)) {
                        return 100;
                     }
                  }
               }

               int immunityAfter = config.villagerWorkImmunityAfter();
               if (immunityAfter > 0 && currentTick - mob.servercore$getActivatedTick() >= immunityAfter && brain.isActive(Activity.WORK)) {
                  return config.villagerWorkImmunityFor();
               }
            }

            if (mob instanceof Llama llama && llama.inCaravan()) {
               return 1;
            }

            if (mob instanceof Animal animal) {
               if (animal.isBaby() || animal.isInLove()) {
                  return 5;
               }

               if (mob instanceof Sheep sheep && sheep.isSheared()) {
                  return 1;
               }
            }

            if (mob instanceof Creeper creeper && creeper.isIgnited()) {
               return 20;
            }

            if (Util.hasTasks(mob.targetSelector)) {
               return 0;
            }
         }
      }

      return -1;
   }

   public static boolean checkIfActive(Entity entity, int currentTick) {
      ActivationRangeConfig config = Config.get().activationRange();
      if (shouldTick(entity, config)) {
         entity.servercore$setActivatedTick(currentTick);
         return true;
      }

      boolean active = entity.servercore$getActivatedTick() >= currentTick;
      if (!active) {
         int inactiveTicks = currentTick - entity.servercore$getActivatedTick() - 1;
         if (inactiveTicks % 20 == 0) {
            int immunity = checkEntityImmunities(entity, currentTick, config);
            if (immunity >= 0) {
               entity.servercore$setActivatedTick(currentTick + immunity);
               return true;
            }
         }

         int tickInterval = entity.servercore$getActivationType().tickInterval();
         if (tickInterval > 0 && inactiveTicks % tickInterval == 0) {
            return true;
         }
      } else if (config.skipNonImmune() && entity.servercore$getFullTickCount() % 4 == 0 && checkEntityImmunities(entity, currentTick, config) < 0) {
         return false;
      }

      return active;
   }

   private static boolean shouldTick(Entity entity, ActivationRangeConfig config) {
      return !config.enabled()
         || entity.servercore$isExcluded()
         || entity.isOnPortalCooldown()
         || entity.portalProcess != null && entity.portalProcess.isInsidePortalThisTick()
         || entity.tickCount < 200 && (entity.servercore$getActivationType() == config.defaultActivationType() || config.tickNewEntities())
         || entity instanceof Leashable leashable && leashable.getLeashData() != null && leashable.getLeashData().leashHolder instanceof Player
         || entity instanceof LivingEntity living && living.hurtTime > 0;
   }

   private static int checkInactiveWakeup(Entity entity, int currentTick) {
      int wakeupInterval = entity.servercore$getActivationType().wakeupInterval();
      return wakeupInterval > 0 && currentTick - entity.servercore$getActivatedTick() >= wakeupInterval * 20L ? 100 : -1;
   }
}
