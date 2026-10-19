package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;

public class NearbyGolemSensor<E extends LivingEntity> extends PredicateSensor<LivingEntity, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{MemoryModuleType.GOLEM_DETECTED_RECENTLY});
   private int timeToRemember = 600;

   public NearbyGolemSensor() {
      this.setScanRate(entity -> 200);
      this.setPredicate((target, entity) -> target.getType() == EntityType.IRON_GOLEM && target.isAlive());
   }

   public NearbyGolemSensor<E> setMemoryTime(int ticks) {
      this.timeToRemember = ticks;
      return this;
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_GOLEM.get();
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      BrainUtils.withMemory(entity, MemoryModuleType.NEAREST_LIVING_ENTITIES, entityList -> {
         if (!entityList.isEmpty()) {
            for (LivingEntity target : entityList) {
               if (this.predicate().test(target, entity)) {
                  BrainUtils.setForgettableMemory(entity, MemoryModuleType.GOLEM_DETECTED_RECENTLY, true, this.timeToRemember);
                  return;
               }
            }
         }
      });
   }
}
