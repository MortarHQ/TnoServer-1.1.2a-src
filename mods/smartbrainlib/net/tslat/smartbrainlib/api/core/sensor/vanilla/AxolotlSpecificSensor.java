package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class AxolotlSpecificSensor<E extends LivingEntity> extends EntityFilteringSensor<LivingEntity, E> {
   @Override
   public MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_ATTACKABLE;
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return List.of(this.getMemory(), MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.AXOLOTL_SPECIFIC.get();
   }

   @Override
   protected BiPredicate<LivingEntity, E> predicate() {
      return (target, entity) -> {
         if (target.distanceToSqr(entity) > 64.0) {
            return false;
         } else if (!target.isInWaterOrBubble()) {
            return false;
         } else {
            return target.getType().is(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES)
                  || !BrainUtils.hasMemory(target, MemoryModuleType.HAS_HUNTING_COOLDOWN) && target.getType().is(EntityTypeTags.AXOLOTL_HUNT_TARGETS)
               ? Sensor.isEntityAttackable(entity, target)
               : false;
         }
      };
   }

   @Nullable
   protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
      return (LivingEntity)matcher.findClosest(target -> this.predicate().test(target, entity)).orElse(null);
   }
}
