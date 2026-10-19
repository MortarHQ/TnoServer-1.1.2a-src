package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker.api.util.sequence.task.type.InstantTask;
import com.blamejared.crafttweaker.api.util.sequence.task.type.SleepTask;
import com.blamejared.crafttweaker.api.util.sequence.task.type.SleepUntilTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/sequence/SequenceBuilder")
@ZenCodeType.Name("crafttweaker.api.util.sequence.SequenceBuilder")
public class SequenceBuilder<T, U> {
   private final SequenceType type;
   private final Supplier<T> actor;
   private final U data;
   private final Queue<ISequenceTask<T, U>> timeline;

   public SequenceBuilder(SequenceType type, Supplier<T> actor, U data) {
      this.type = type;
      this.actor = actor;
      this.data = data;
      this.timeline = new LinkedList<>();
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> addTask(ISequenceTask<T, U> task) {
      this.timeline.offer(task);
      return this;
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> sleep(long ticks) {
      return this.addTask(new SleepTask<>(ticks));
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> sleepUntil(Predicate<T> condition) {
      return this.addTask(new SleepUntilTask<>((t, u) -> condition.test(t)));
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> sleepUntil(BiPredicate<T, SequenceContext<T, U>> condition) {
      return this.addTask(new SleepUntilTask<>(condition));
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> run(Consumer<T> function) {
      return this.addTask(new InstantTask<>((t, u) -> function.accept(t)));
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> run(BiConsumer<T, SequenceContext<T, U>> function) {
      return this.addTask(new InstantTask<>(function));
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> then(Consumer<T> function) {
      return this.run(function);
   }

   @ZenCodeType.Method
   public SequenceBuilder<T, U> then(BiConsumer<T, SequenceContext<T, U>> function) {
      return this.run(function);
   }

   @ZenCodeType.Method
   public Sequence<T, U> start() {
      Sequence<T, U> schedule = new Sequence<>(this.actor, this.data, this.timeline);
      SequenceManager.addSequence(this.type, schedule);
      return schedule;
   }
}
