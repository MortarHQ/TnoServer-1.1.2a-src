package com.tensura_tno.event;

import com.tensura_tno.compat.IceAndFireEntityCompat;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingChangeTargetEvent;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

@EventBusSubscriber(modid = "tensura_tno")
public final class IceAndFireDragonRemainsHandler {
   private IceAndFireDragonRemainsHandler() {
   }

   public static void registerArchitecturyEvents() {
      EntityEvents.LIVING_CHANGE_TARGET
         .register(
            (LivingChangeTargetEvent)(entity, target) -> IceAndFireEntityCompat.shouldIgnoreDragonRemains(entity, (Entity)target.get())
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onChangeTarget(net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent event) {
      if (IceAndFireEntityCompat.shouldIgnoreDragonRemains(event.getEntity(), event.getNewAboutToBeSetTarget())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (IceAndFireEntityCompat.shouldBlockDragonRemainsDamage(event.getSource(), event.getEntity())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onEntityTick(Pre event) {
      if (event.getEntity() instanceof Mob mob && mob.level() instanceof ServerLevel level && IceAndFireEntityCompat.isTensuraCombatEntity(mob)) {
         Brain<?> brain = mob.getBrain();
         boolean hadCorpseTarget = IceAndFireEntityCompat.isDragonRemains(mob.getTarget());
         boolean hadCorpseAttackMemory = forgetDragonMemory(brain, MemoryModuleType.ATTACK_TARGET);
         if (hadCorpseTarget) {
            mob.setTarget(null);
         }

         boolean lostCombatTarget = (hadCorpseTarget || hadCorpseAttackMemory) && mob.getTarget() == null;
         if (lostCombatTarget) {
            mob.setAggressive(false);
         }

         if (IceAndFireEntityCompat.isDragonRemains(mob.getLastHurtByMob())) {
            mob.setLastHurtByMob(null);
         }

         if (IceAndFireEntityCompat.isDragonRemains(mob.getLastHurtMob())) {
            mob.setLastHurtMob(null);
         }

         forgetDragonMemory(brain, MemoryModuleType.HURT_BY_ENTITY);
         forgetDragonMemory(brain, MemoryModuleType.NEAREST_ATTACKABLE);
         boolean hadCorpseAvoidTarget = forgetDragonMemory(brain, MemoryModuleType.AVOID_TARGET);
         DamageSource lastDamage = getMemory(brain, MemoryModuleType.HURT_BY);
         if (lastDamage != null && IceAndFireEntityCompat.isDragonRemains(lastDamage.getEntity())) {
            brain.eraseMemory(MemoryModuleType.HURT_BY);
         }

         if (mob instanceof NeutralMob neutral && isDragonRemains(level, neutral.getPersistentAngerTarget())) {
            neutral.setPersistentAngerTarget(null);
            neutral.setRemainingPersistentAngerTime(0);
         }

         if (isDragonRemains(level, getMemory(brain, MemoryModuleType.ANGRY_AT))) {
            brain.eraseMemory(MemoryModuleType.ANGRY_AT);
         }

         WalkTarget walkTarget = getMemory(brain, MemoryModuleType.WALK_TARGET);
         if (lostCombatTarget || hadCorpseAvoidTarget && mob.getTarget() == null || walkTarget != null && tracksDragonRemains(walkTarget.getTarget())) {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
            brain.eraseMemory(MemoryModuleType.PATH);
            brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            mob.getNavigation().stop();
         }

         if (tracksDragonRemains(getMemory(brain, MemoryModuleType.LOOK_TARGET))) {
            brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
         }
      }
   }

   private static boolean isDragonRemains(ServerLevel level, UUID entityId) {
      return entityId != null && IceAndFireEntityCompat.isDragonRemains(level.getEntity(entityId));
   }

   private static boolean tracksDragonRemains(PositionTracker tracker) {
      return tracker instanceof EntityTracker entityTracker && IceAndFireEntityCompat.isDragonRemains(entityTracker.getEntity());
   }

   private static <T extends Entity> boolean forgetDragonMemory(Brain<?> brain, MemoryModuleType<T> type) {
      if (!IceAndFireEntityCompat.isDragonRemains(getMemory(brain, type))) {
         return false;
      }

      brain.eraseMemory(type);
      return true;
   }

   private static <T> T getMemory(Brain<?> brain, MemoryModuleType<T> type) {
      return (T)(brain.hasMemoryValue(type) ? brain.getMemory(type).orElse(null) : null);
   }
}
