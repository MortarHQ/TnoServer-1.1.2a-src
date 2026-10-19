package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import org.jetbrains.annotations.Nullable;

public class NearbyHostileSensor<E extends LivingEntity> extends EntityFilteringSensor<LivingEntity, E> {
   private final Map<EntityType<?>, Float> hostileDistanceMap = new Object2FloatOpenHashMap(11);

   public NearbyHostileSensor() {
      this.setHostiles(
         Pair.of(EntityType.DROWNED, 8.0F),
         Pair.of(EntityType.HUSK, 8.0F),
         Pair.of(EntityType.VEX, 8.0F),
         Pair.of(EntityType.ZOMBIE, 8.0F),
         Pair.of(EntityType.ZOMBIE_VILLAGER, 8.0F),
         Pair.of(EntityType.VINDICATOR, 10.0F),
         Pair.of(EntityType.ZOGLIN, 10.0F),
         Pair.of(EntityType.EVOKER, 12.0F),
         Pair.of(EntityType.ILLUSIONER, 12.0F),
         Pair.of(EntityType.RAVAGER, 12.0F),
         Pair.of(EntityType.PILLAGER, 15.0F)
      );
   }

   public NearbyHostileSensor<E> setHostiles(Pair<EntityType<?>, Float>... entries) {
      this.hostileDistanceMap.clear();

      for (Pair<EntityType<?>, Float> entry : entries) {
         this.hostileDistanceMap.put((EntityType<?>)entry.getFirst(), (Float)entry.getSecond());
      }

      return this;
   }

   public NearbyHostileSensor<E> addHostile(Pair<EntityType<?>, Float> entry) {
      this.hostileDistanceMap.put((EntityType<?>)entry.getFirst(), (Float)entry.getSecond());
      return this;
   }

   @Override
   public MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_HOSTILE;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_HOSTILE.get();
   }

   @Override
   protected BiPredicate<LivingEntity, E> predicate() {
      return (target, entity) -> {
         Float distance = this.hostileDistanceMap.get(target.getType());
         return distance != null && target.distanceToSqr(entity) <= distance * distance;
      };
   }

   @Nullable
   protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
      return (LivingEntity)matcher.findClosest(target -> this.predicate().test(target, entity)).orElse(null);
   }
}
