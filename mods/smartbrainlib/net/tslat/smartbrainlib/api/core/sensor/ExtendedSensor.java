package net.tslat.smartbrainlib.api.core.sensor;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

public abstract class ExtendedSensor<E extends LivingEntity> extends Sensor<E> {
   protected Function<E, Integer> scanRate = entity -> 20;
   protected Consumer<E> scanCallback = entity -> {};
   protected long nextTickTime = 0L;

   public ExtendedSensor<E> setScanRate(Function<E, Integer> function) {
      this.scanRate = function;
      return this;
   }

   public ExtendedSensor<E> afterScanning(Consumer<E> callback) {
      this.scanCallback = callback;
      return this;
   }

   public final void tick(ServerLevel level, E entity) {
      if (this.nextTickTime < level.getGameTime()) {
         this.nextTickTime = level.getGameTime() + this.scanRate.apply(entity).intValue();
         this.doTick(level, entity);
         this.scanCallback.accept(entity);
      }
   }

   protected void doTick(ServerLevel level, E entity) {
   }

   public abstract List<MemoryModuleType<?>> memoriesUsed();

   public abstract SensorType<? extends ExtendedSensor<?>> type();

   public final Set<MemoryModuleType<?>> requires() {
      return new ObjectOpenHashSet(this.memoriesUsed());
   }
}
