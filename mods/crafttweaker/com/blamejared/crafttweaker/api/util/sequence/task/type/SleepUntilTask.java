package com.blamejared.crafttweaker.api.util.sequence.task.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/sequence/task/type/SleepUntilTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.type.SleepUntilTask")
public class SleepUntilTask<T, U> implements ISequenceTask<T, U> {
   private final BiPredicate<T, SequenceContext<T, U>> condition;
   private boolean complete = false;

   @ZenCodeType.Constructor
   public SleepUntilTask(Predicate<T> condition) {
      this.condition = (actor, context) -> condition.test(actor);
   }

   @ZenCodeType.Constructor
   public SleepUntilTask(BiPredicate<T, SequenceContext<T, U>> condition) {
      this.condition = condition;
   }

   @Override
   public void tick(T actor, SequenceContext<T, U> data) {
      this.complete = this.complete || this.condition.test(actor, data);
   }

   @Override
   public boolean isComplete(T actor, SequenceContext<T, U> data) {
      return this.complete;
   }
}
