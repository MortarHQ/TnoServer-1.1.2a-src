package net.tslat.smartbrainlib.api.core.behaviour;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class RepeatingBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
   protected final ExtendedBehaviour<E> child;
   protected Predicate<E> shouldRepeat = entity -> true;
   protected ToIntFunction<E> repeatCountProvider = entity -> Integer.MAX_VALUE;
   protected int repeats;

   public RepeatingBehaviour(ExtendedBehaviour<E> child) {
      this.child = child;

      for (Pair<MemoryModuleType<?>, MemoryStatus> memoryReq : this.getMemoryRequirements()) {
         this.entryCondition.put((MemoryModuleType)memoryReq.getFirst(), (MemoryStatus)memoryReq.getSecond());
      }

      this.noTimeout();
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return this.child == null ? List.of() : this.child.getMemoryRequirements();
   }

   public RepeatingBehaviour<E> repeatingWhen(Predicate<E> predicate) {
      this.shouldRepeat = predicate;
      return this;
   }

   public RepeatingBehaviour<E> repeatNTimes(int repeats) {
      return this.repeatNTimes(entity -> repeats);
   }

   public RepeatingBehaviour<E> repeatNTimes(ToIntFunction<E> function) {
      this.repeatCountProvider = function;
      return this;
   }

   @Override
   protected boolean doStartCheck(ServerLevel level, E entity, long gameTime) {
      return super.doStartCheck(level, entity, gameTime) && this.child.tryStart(level, entity, gameTime);
   }

   @Override
   protected void start(ServerLevel level, E entity, long gameTime) {
      super.start(level, entity, gameTime);
      this.repeats = this.repeatCountProvider.applyAsInt(entity);
   }

   @Override
   protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
      return (this.child.getStatus() != Status.STOPPED || this.repeats > 0) && !this.stopCondition.test(entity);
   }

   @Override
   protected void tick(ServerLevel level, E entity, long gameTime) {
      super.tick(level, entity, gameTime);
      if (this.child.getStatus() != Status.STOPPED) {
         this.child.tickOrStop(level, entity, gameTime);
      } else {
         if (this.repeats > 0 && this.shouldRepeat.test(entity) && this.child.tryStart(level, entity, gameTime)) {
            this.repeats--;
         }
      }
   }

   @Override
   protected void stop(ServerLevel level, E entity, long gameTime) {
      super.stop(level, entity, gameTime);
      if (this.child.getStatus() != Status.STOPPED) {
         this.child.doStop(level, entity, gameTime);
      }
   }

   public String toString() {
      return "(" + this.getClass().getSimpleName() + "): " + (this.child != null ? this.child.toString() : "{}");
   }

   public String debugString() {
      return this.getClass().getSimpleName() + " -> " + (this.child != null ? this.child.debugString() : "{}");
   }
}
