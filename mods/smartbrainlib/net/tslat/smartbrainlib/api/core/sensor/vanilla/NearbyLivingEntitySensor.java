package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.object.FixedNearestVisibleLivingEntities;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;

public class NearbyLivingEntitySensor<E extends LivingEntity> extends PredicateSensor<LivingEntity, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
      new MemoryModuleType[]{MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES}
   );
   @Nullable
   protected SquareRadius radius = null;

   public NearbyLivingEntitySensor() {
      super((target, entity) -> target != entity && target.isAlive());
   }

   public NearbyLivingEntitySensor<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public NearbyLivingEntitySensor<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_LIVING_ENTITY.get();
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      SquareRadius radius = this.radius;
      if (radius == null) {
         double dist = entity.getAttributeValue(Attributes.FOLLOW_RANGE);
         radius = new SquareRadius(dist, dist);
      }

      List<LivingEntity> entities = EntityRetrievalUtil.getEntities(
         entity, radius.xzRadius(), radius.yRadius(), radius.xzRadius(), LivingEntity.class, livingEntity -> this.predicate().test(livingEntity, entity)
      );
      entities.sort(Comparator.comparingDouble(entity::distanceToSqr));
      BrainUtils.setMemory(entity, MemoryModuleType.NEAREST_LIVING_ENTITIES, entities);
      BrainUtils.setMemory(entity, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, new FixedNearestVisibleLivingEntities(entity, entities));
   }
}
