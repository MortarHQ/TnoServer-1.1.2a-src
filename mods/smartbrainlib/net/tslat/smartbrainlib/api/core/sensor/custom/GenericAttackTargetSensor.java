package net.tslat.smartbrainlib.api.core.sensor.custom;

import java.util.function.BiPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.SensoryUtils;
import org.jetbrains.annotations.Nullable;

public class GenericAttackTargetSensor<E extends LivingEntity> extends EntityFilteringSensor<LivingEntity, E> {
   @Override
   protected MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_ATTACKABLE;
   }

   @Override
   protected BiPredicate<LivingEntity, E> predicate() {
      return (target, entity) -> SensoryUtils.isEntityAttackable(entity, target);
   }

   @Nullable
   protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
      return (LivingEntity)matcher.findClosest(target -> this.predicate().test(target, entity)).orElse(null);
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.GENERIC_ATTACK_TARGET.get();
   }
}
