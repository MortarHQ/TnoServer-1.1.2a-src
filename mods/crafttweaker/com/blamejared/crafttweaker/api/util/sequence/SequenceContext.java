package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/sequence/SequenceContext")
@ZenCodeType.Name("crafttweaker.api.util.sequence.SequenceContext")
public class SequenceContext<T, U> {
   private final Sequence<T, U> sequence;
   private final U data;

   @ZenCodeType.Constructor
   public SequenceContext(Sequence<T, U> sequence, U data) {
      this.sequence = sequence;
      this.data = data;
   }

   @ZenCodeType.Method
   public void stop() {
      this.sequence.stop();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("data")
   public U getData() {
      if (this.data == null) {
         throw new IllegalStateException("Unable to call 'getData' as data is null!");
      } else {
         return this.data;
      }
   }
}
