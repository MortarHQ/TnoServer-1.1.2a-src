package net.tslat.smartbrainlib.api.core.sensor;

import java.util.function.BiPredicate;
import net.minecraft.world.entity.LivingEntity;

public abstract class PredicateSensor<P, E extends LivingEntity> extends ExtendedSensor<E> {
   private BiPredicate<P, E> predicate;

   public PredicateSensor() {
      this((obj, entity) -> true);
   }

   public PredicateSensor(BiPredicate<P, E> predicate) {
      this.predicate = predicate;
   }

   public PredicateSensor<P, E> setPredicate(BiPredicate<P, E> predicate) {
      this.predicate = predicate;
      return this;
   }

   protected BiPredicate<P, E> predicate() {
      return this.predicate;
   }
}
