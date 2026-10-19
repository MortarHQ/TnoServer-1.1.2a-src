package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import org.jetbrains.annotations.Nullable;

public class NearbyBabySensor<E extends LivingEntity> extends EntityFilteringSensor<List<LivingEntity>, E> {
   @Override
   public MemoryModuleType<List<LivingEntity>> getMemory() {
      return MemoryModuleType.VISIBLE_VILLAGER_BABIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_BABY.get();
   }

   @Override
   protected BiPredicate<LivingEntity, E> predicate() {
      return (target, entity) -> target.getType() == entity.getType() && target.isBaby();
   }

   @Nullable
   protected List<LivingEntity> findMatches(E entity, NearestVisibleLivingEntities matcher) {
      return ImmutableList.copyOf(matcher.findAll(target -> this.predicate().test(target, entity)));
   }
}
