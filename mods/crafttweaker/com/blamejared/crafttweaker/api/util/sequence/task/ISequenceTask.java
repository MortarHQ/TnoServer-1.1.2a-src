package com.blamejared.crafttweaker.api.util.sequence.task;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/sequence/task/ISequenceTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.ISequenceTask")
public interface ISequenceTask<T, U> {
   @ZenCodeType.Method
   void tick(T var1, SequenceContext<T, U> var2);

   @ZenCodeType.Method
   boolean isComplete(T var1, SequenceContext<T, U> var2);
}
