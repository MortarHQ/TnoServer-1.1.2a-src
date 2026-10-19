package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

public class NearestItemSensor<E extends Mob> extends PredicateSensor<ItemEntity, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM});
   protected SquareRadius radius = new SquareRadius(32.0, 16.0);

   public NearestItemSensor() {
      super((item, entity) -> entity.wantsToPickUp(item.getItem()) && entity.hasLineOfSight(item));
   }

   public NearestItemSensor<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public NearestItemSensor<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEAREST_ITEM.get();
   }

   protected void doTick(ServerLevel level, E entity) {
      BrainUtils.setMemory(
         entity,
         MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
         (ItemEntity)EntityRetrievalUtil.getNearestEntity(
               entity, this.radius.xzRadius(), this.radius.yRadius(), this.radius.xzRadius(), ItemEntity.class, item -> this.predicate().test(item, entity)
            )
            .orElse(null)
      );
   }
}
