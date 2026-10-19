package com.blamejared.crafttweaker.api.util.sequence.task.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/sequence/task/type/SleepTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.type.SleepTask")
public class SleepTask<T, U> implements ISequenceTask<T, U> {
   private final long sleepTime;
   private long timeSlept;

   @ZenCodeType.Constructor
   public SleepTask(long sleepTime) {
      this.sleepTime = sleepTime;
      this.timeSlept = 0L;
   }

   @Override
   public void tick(T actor, SequenceContext<T, U> data) {
      this.timeSlept++;
   }

   @Override
   public boolean isComplete(T actor, SequenceContext<T, U> data) {
      return this.timeSlept >= this.sleepTime;
   }
}
