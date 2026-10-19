package com.tno_tensura_extras.entity;

import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public final class DiabloTargeting {
   private static final ResourceLocation PROTECTED_RIMURU = ResourceLocation.fromNamespaceAndPath("tensura_neb", "rimuru_ogre_fight");
   private final DiabloEntity diablo;
   private final Map<UUID, Long> threats = new HashMap<>();

   DiabloTargeting(DiabloEntity diablo) {
      this.diablo = diablo;
   }

   public static boolean isProtectedRimuru(Entity entity) {
      return entity != null && PROTECTED_RIMURU.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
   }

   public static boolean isRival(Entity entity) {
      if (entity instanceof Mob && !isProtectedRimuru(entity)) {
         String namespace = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getNamespace();
         return namespace.equals("tensura_neb") || namespace.equals("tensura") && entity.getType().is(EntityTypes.BOSSES);
      } else {
         return false;
      }
   }

   private boolean eligible(LivingEntity target) {
      return target != null
         && target != this.diablo
         && target.isAlive()
         && !target.isSpectator()
         && !target.hasInfiniteMaterials()
         && !isProtectedRimuru(target)
         && !this.diablo.isAlliedTo(target)
         && !SubordinateHelper.isAlly(this.diablo, target)
         && !TensuraStorages.getExistenceFrom(target).isTargetNeutral(this.diablo.getUUID())
         && this.diablo.canAttack(target);
   }

   boolean canEngage(LivingEntity target) {
      if (!this.eligible(target)) {
         return false;
      } else if (isRival(target) || this.threats.getOrDefault(target.getUUID(), 0L) > this.diablo.level().getGameTime()) {
         return true;
      } else if (target == this.diablo.getLastHurtByMob() && this.diablo.tickCount - this.diablo.getLastHurtByMobTimestamp() <= 100) {
         return true;
      } else if (!(target instanceof Mob mob)) {
         return false;
      } else {
         LivingEntity victim = getAttackTarget(mob);
         return victim == this.diablo || isProtectedRimuru(victim);
      }
   }

   void rememberThreat(LivingEntity attacker, boolean protectRimuru) {
      if (this.eligible(attacker)) {
         long now = this.diablo.level().getGameTime();
         this.threats.entrySet().removeIf(entry -> entry.getValue() <= now);
         if (this.threats.size() < 64 || this.threats.containsKey(attacker.getUUID())) {
            this.threats.put(attacker.getUUID(), now + 600L);
         }

         if (this.canAct() && (protectRimuru || !this.canEngage(this.diablo.getTarget()))) {
            BrainUtils.setTargetOfEntity(this.diablo, attacker);
         }
      }
   }

   private boolean canAct() {
      return this.diablo.isAlive() && !this.diablo.isNoAi() && !this.diablo.isOrderedToSit() && !this.diablo.isSleeping() && this.diablo.getBehaviour() != 1;
   }

   void clearInvalidTarget() {
      LivingEntity target = this.diablo.getTarget();
      if (target != null && !this.canEngage(target)) {
         SubordinateHelper.removeTarget(this.diablo);
         this.diablo.getNavigation().stop();
      }
   }

   void tick() {
      this.clearInvalidTarget();
      if (this.canAct() && this.diablo.tickCount % 20 == 0) {
         long now = this.diablo.level().getGameTime();
         this.threats.entrySet().removeIf(entry -> entry.getValue() <= now);
         List<LivingEntity> nearby = this.diablo.getBrain().checkMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryStatus.REGISTERED)
            ? this.diablo.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(List.of())
            : List.of();
         LivingEntity defenderTarget = null;
         LivingEntity nearestRival = null;
         double range = this.diablo.getAttributeValue(Attributes.FOLLOW_RANGE);

         for (LivingEntity entity : nearby) {
            if (!(this.diablo.distanceToSqr(entity) > range * range)) {
               if (isProtectedRimuru(entity)) {
                  LivingEntity attacker = entity.getLastHurtByMob();
                  if (entity.tickCount - entity.getLastHurtByMobTimestamp() <= 100 && this.eligible(attacker)) {
                     this.rememberThreat(attacker, false);
                     defenderTarget = this.closer(defenderTarget, attacker);
                  }
               } else if (this.eligible(entity)) {
                  if (entity instanceof Mob mob) {
                     LivingEntity victim = getAttackTarget(mob);
                     if (isProtectedRimuru(victim)) {
                        this.rememberThreat(entity, false);
                        defenderTarget = this.closer(defenderTarget, entity);
                     }

                     if (isRival(mob)
                        && !mob.isNoAi()
                        && !mob.isSleeping()
                        && (victim == null || !victim.isAlive())
                        && mob.canAttack(this.diablo)
                        && !mob.isAlliedTo(this.diablo)
                        && mob.getSensing().hasLineOfSight(this.diablo)
                        && mob.distanceToSqr(this.diablo) <= Math.pow(mob.getAttributeValue(Attributes.FOLLOW_RANGE), 2.0)) {
                        setAttackTarget(mob, this.diablo);
                     }
                  }

                  if (this.canEngage(entity) && this.diablo.getSensing().hasLineOfSight(entity)) {
                     nearestRival = this.closer(nearestRival, entity);
                  }
               }
            }
         }

         if (defenderTarget != null) {
            BrainUtils.setTargetOfEntity(this.diablo, defenderTarget);
         } else if (!this.canEngage(this.diablo.getTarget()) && nearestRival != null) {
            BrainUtils.setTargetOfEntity(this.diablo, nearestRival);
         }
      }
   }

   private LivingEntity closer(LivingEntity current, LivingEntity candidate) {
      return current != null && !(this.diablo.distanceToSqr(candidate) < this.diablo.distanceToSqr(current)) ? current : candidate;
   }

   @Nullable
   private static LivingEntity getAttackTarget(Mob mob) {
      if (mob.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED)) {
         LivingEntity remembered = (LivingEntity)mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
         if (remembered != null) {
            return remembered;
         }
      }

      return mob.getTarget();
   }

   private static void setAttackTarget(Mob mob, LivingEntity target) {
      mob.setTarget(target);
      if (mob.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED)) {
         mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
      }
   }
}
