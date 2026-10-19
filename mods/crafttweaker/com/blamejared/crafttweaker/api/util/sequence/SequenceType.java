package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/sequence/SequenceType")
@ZenCodeType.Name("crafttweaker.api.util.sequence.SequenceType")
public final class SequenceType {
   @ZenCodeType.Field
   public static final SequenceType SERVER_THREAD_LEVEL = new SequenceType("server_thread_level");
   @ZenCodeType.Field
   public static final SequenceType CLIENT_THREAD_LEVEL = new SequenceType("client_thread_level");
   private final String name;

   public SequenceType(String name) {
      this.name = name;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter
   public String name() {
      return this.name;
   }
}
