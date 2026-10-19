package net.tslat.smartbrainlib.api.core.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public abstract class ExtendedBehaviour<E extends LivingEntity> extends Behavior<E> {
   protected Predicate<E> startCondition = entity -> true;
   protected Predicate<E> stopCondition = entity -> false;
   protected Consumer<E> taskStartCallback = entity -> {};
   protected Consumer<E> taskStopCallback = entity -> {};
   protected Function<E, Integer> runtimeProvider = entity -> 60;
   protected Function<E, Integer> cooldownProvider = entity -> 0;
   protected long cooldownFinishedAt = 0L;

   public ExtendedBehaviour() {
      super(new Object2ObjectOpenHashMap());

      for (Pair<MemoryModuleType<?>, MemoryStatus> memoryReq : this.getMemoryRequirements()) {
         this.entryCondition.put((MemoryModuleType)memoryReq.getFirst(), (MemoryStatus)memoryReq.getSecond());
      }
   }

   public final ExtendedBehaviour<E> whenStarting(Consumer<E> callback) {
      this.taskStartCallback = callback;
      return this;
   }

   public final ExtendedBehaviour<E> whenStopping(Consumer<E> callback) {
      this.taskStopCallback = callback;
      return this;
   }

   public final ExtendedBehaviour<E> runFor(Function<E, Integer> timeProvider) {
      this.runtimeProvider = timeProvider;
      return this;
   }

   public final ExtendedBehaviour<E> runForBetween(int minTicks, int maxTicks) {
      this.runtimeProvider = entity -> entity.getRandom().nextIntBetweenInclusive(minTicks, maxTicks);
      return this;
   }

   public final ExtendedBehaviour<E> cooldownFor(Function<E, Integer> timeProvider) {
      this.cooldownProvider = timeProvider;
      return this;
   }

   public final ExtendedBehaviour<E> cooldownForBetween(int minTicks, int maxTicks) {
      this.cooldownProvider = entity -> entity.getRandom().nextIntBetweenInclusive(minTicks, maxTicks);
      return this;
   }

   public final ExtendedBehaviour<E> startCondition(Predicate<E> predicate) {
      this.startCondition = predicate;
      return this;
   }

   public final ExtendedBehaviour<E> stopIf(Predicate<E> predicate) {
      this.stopCondition = predicate;
      return this;
   }

   public final ExtendedBehaviour<E> noTimeout() {
      return this.runFor(entity -> Integer.MAX_VALUE);
   }

   public final boolean tryStart(ServerLevel level, E entity, long gameTime) {
      if (!this.doStartCheck(level, entity, gameTime)) {
         return false;
      }

      this.status = Status.RUNNING;
      this.endTimestamp = gameTime + this.runtimeProvider.apply(entity).intValue();
      this.start(level, entity, gameTime);
      return true;
   }

   protected boolean doStartCheck(ServerLevel level, E entity, long gameTime) {
      return this.cooldownFinishedAt <= gameTime
         && this.hasRequiredMemories(entity)
         && this.startCondition.test(entity)
         && this.checkExtraStartConditions(level, entity);
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return true;
   }

   protected void start(ServerLevel level, E entity, long gameTime) {
      this.taskStartCallback.accept(entity);
      this.start(entity);
   }

   protected void start(E entity) {
   }

   protected void stop(ServerLevel level, E entity, long gameTime) {
      this.cooldownFinishedAt = gameTime + this.cooldownProvider.apply(entity).intValue();
      this.taskStopCallback.accept(entity);
      this.stop(entity);
   }

   protected void stop(E entity) {
   }

   protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
      return this.shouldKeepRunning(entity) && !this.stopCondition.test(entity);
   }

   protected boolean shouldKeepRunning(E entity) {
      return false;
   }

   protected void tick(ServerLevel level, E entity, long gameTime) {
      this.tick(entity);
   }

   protected void tick(E entity) {
   }

   protected boolean timedOut(long gameTime) {
      return super.timedOut(gameTime);
   }

   public final boolean hasRequiredMemories(E entity) {
      Brain<?> brain = entity.getBrain();

      for (Pair<MemoryModuleType<?>, MemoryStatus> memoryPair : this.getMemoryRequirements()) {
         if (!brain.checkMemory((MemoryModuleType)memoryPair.getFirst(), (MemoryStatus)memoryPair.getSecond())) {
            return false;
         }
      }

      return true;
   }

   protected abstract List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements();
}
