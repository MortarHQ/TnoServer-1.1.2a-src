package net.tslat.smartbrainlib.api.core.sensor.custom;

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
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

public class NearbyItemsSensor<E extends Mob> extends PredicateSensor<ItemEntity, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{SBLMemoryTypes.NEARBY_ITEMS.get()});
   protected SquareRadius radius = new SquareRadius(32.0, 16.0);

   public NearbyItemsSensor() {
      super((item, entity) -> entity.wantsToPickUp(item.getItem()) && entity.hasLineOfSight(item));
   }

   public NearbyItemsSensor<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public NearbyItemsSensor<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_ITEMS.get();
   }

   protected void doTick(ServerLevel level, E entity) {
      BrainUtils.setMemory(
         entity,
         SBLMemoryTypes.NEARBY_ITEMS.get(),
         EntityRetrievalUtil.getEntities(
            entity, this.radius.xzRadius(), this.radius.yRadius(), this.radius.xzRadius(), ItemEntity.class, item -> this.predicate().test(item, entity)
         )
      );
   }
}
