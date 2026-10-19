package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import java.util.function.BiPredicate;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import org.jetbrains.annotations.Nullable;

public class NearbyAdultSensor<E extends AgeableMob> extends EntityFilteringSensor<AgeableMob, E> {
   @Override
   public MemoryModuleType<AgeableMob> getMemory() {
      return MemoryModuleType.NEAREST_VISIBLE_ADULT;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_ADULT.get();
   }

   @Override
   protected BiPredicate<LivingEntity, E> predicate() {
      return (target, entity) -> target.getType() == entity.getType() && !target.isBaby();
   }

   @Nullable
   protected AgeableMob findMatches(E entity, NearestVisibleLivingEntities matcher) {
      return (AgeableMob)matcher.findClosest(target -> this.predicate().test(target, entity)).orElse(null);
   }
}
