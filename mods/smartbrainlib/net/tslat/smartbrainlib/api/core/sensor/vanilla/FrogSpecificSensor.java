package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class FrogSpecificSensor<E extends LivingEntity> extends EntityFilteringSensor<LivingEntity, E> {
   @Override
   public MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_ATTACKABLE;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.FROG_SPECIFIC.get();
   }

   @Override
   protected BiPredicate<LivingEntity, E> predicate() {
      return (target, entity) -> {
         if (BrainUtils.hasMemory(entity, MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
            return false;
         }

         if (!Sensor.isEntityAttackable(entity, target)) {
            return false;
         }

         if (!Frog.canEat(target)) {
            return false;
         }

         if (!target.closerThan(entity, 10.0)) {
            return false;
         }

         List<UUID> unreachableTargets = BrainUtils.getMemory(entity, MemoryModuleType.UNREACHABLE_TONGUE_TARGETS);
         return unreachableTargets == null || !unreachableTargets.contains(target.getUUID());
      };
   }

   @Nullable
   protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
      return (LivingEntity)matcher.findClosest(target -> this.predicate().test(target, entity)).orElse(null);
   }
}
