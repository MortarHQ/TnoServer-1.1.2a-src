package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Queue;
import java.util.function.Supplier;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/sequence/Sequence")
@ZenCodeType.Name("crafttweaker.api.util.sequence.Sequence")
public class Sequence<T, U> {
   private final Supplier<T> actor;
   private final Queue<ISequenceTask<T, U>> timeline;
   private boolean stopped;
   private final SequenceContext<T, U> context;

   public Sequence(Supplier<T> actor, U data, Queue<ISequenceTask<T, U>> timeline) {
      this.actor = actor;
      this.timeline = timeline;
      this.stopped = false;
      this.context = new SequenceContext<>(this, data);
   }

   @ZenCodeType.Method
   public void tick() {
      if (!this.timeline.isEmpty()) {
         ISequenceTask<T, U> task = this.timeline.peek();
         task.tick(this.actor.get(), this.context);
         if (task.isComplete(this.actor.get(), this.context)) {
            this.timeline.remove();
         }
      }
   }

   @ZenCodeType.Method
   public void stop() {
      this.stopped = true;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("stopped")
   public boolean isStopped() {
      return this.stopped;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isComplete")
   public boolean isComplete() {
      return this.timeline.isEmpty();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("context")
   public SequenceContext<T, U> getContext() {
      return this.context;
   }
}
