package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.Function;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@ZenCodeType.Name("crafttweaker.api.util.Many")
@Document("vanilla/api/util/Many")
public class Many<T> implements CommandStringDisplayable {
   private final T data;
   private final int amount;
   private final Function<T, String> commandStringFunc;

   public Many(T data, int amount, Function<T, String> commandStringFunc) {
      this.data = data;
      this.amount = amount;
      this.commandStringFunc = commandStringFunc;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("data")
   public T getData() {
      return this.data;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("amount")
   public int getAmount() {
      return this.amount;
   }

   public Function<T, String> getCommandStringFunc() {
      return this.commandStringFunc;
   }

   @Override
   public String getCommandString() {
      return this.commandStringFunc.apply(this.getData());
   }
}
