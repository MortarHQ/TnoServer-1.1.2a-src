package dev.foxboss.entity;

import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "foxboss")
public final class MilimCarrionHostility {
   private static final ResourceLocation CARRION = ResourceLocation.fromNamespaceAndPath("tensura_neb", "carrion");

   @SubscribeEvent
   public static void tick(Post event) {
      if (event.getEntity() instanceof Mob mob
         && !mob.level().isClientSide
         && (mob.tickCount + mob.getId()) % 20 == 0
         && (mob instanceof MilimEntity || isCarrion(mob))
         && available(mob)) {
         LivingEntity current = mob.getTarget();
         if (current == null || !current.isAlive() || !mob.canAttack(current)) {
            double range = Math.min(32.0, mob.getAttributeValue(Attributes.FOLLOW_RANGE));
            Mob nearest = null;
            double closest = range * range;

            for (Mob candidate : mob.level().getEntitiesOfClass(Mob.class, mob.getBoundingBox().inflate(range), candidatex -> isRival(mob, candidatex))) {
               double distance = mob.distanceToSqr(candidate);
               if (distance <= closest && canEngage(mob, candidate)) {
                  nearest = candidate;
                  closest = distance;
               }
            }

            if (nearest != null) {
               engage(mob, nearest);
            }
         }
      }
   }

   static boolean isCarrion(LivingEntity entity) {
      return CARRION.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
   }

   static boolean isRival(Mob first, Mob second) {
      return first instanceof MilimEntity && isCarrion(second) || isCarrion(first) && second instanceof MilimEntity;
   }

   private static boolean available(Mob mob) {
      if (mob.isAlive() && !mob.isNoAi() && !mob.isSpectator()) {
         return !(mob instanceof MilimEntity milim && (milim.isDemo() || milim.isDebugAnimation()))
            ? !(mob instanceof ISubordinate subordinate && (subordinate.isOrderedToSit() || subordinate.getBehaviour() == 1))
            : false;
      } else {
         return false;
      }
   }

   static boolean canEngage(Mob mob, Mob candidate) {
      return available(mob)
         && available(candidate)
         && mob != candidate
         && mob.level() == candidate.level()
         && !mob.isAlliedTo(candidate)
         && !candidate.isAlliedTo(mob)
         && mob.canAttack(candidate)
         && mob.hasLineOfSight(candidate)
         && !(mob instanceof ISubordinate subordinate && subordinate.shouldStopTarget(mob, candidate));
   }

   static void engage(Mob mob, Mob target) {
      mob.setTarget(target);
      if (mob.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED)) {
         mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
      }
   }

   private MilimCarrionHostility() {
   }
}
