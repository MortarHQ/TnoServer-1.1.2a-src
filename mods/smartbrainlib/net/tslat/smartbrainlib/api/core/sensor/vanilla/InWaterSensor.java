package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;

public class InWaterSensor<E extends LivingEntity> extends PredicateSensor<E, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{MemoryModuleType.IS_IN_WATER});

   public InWaterSensor() {
      super((entity2, entity) -> entity.isInWater());
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.IN_WATER.get();
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      if (this.predicate().test(entity, entity)) {
         BrainUtils.setMemory(entity, MemoryModuleType.IS_IN_WATER, Unit.INSTANCE);
      } else {
         BrainUtils.clearMemory(entity, MemoryModuleType.IS_IN_WATER);
      }
   }
}
