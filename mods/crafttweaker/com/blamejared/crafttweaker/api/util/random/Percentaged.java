package com.blamejared.crafttweaker.api.util.random;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.Function;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.random.Percentaged")
@Document("vanilla/api/util/random/Percentaged")
public class Percentaged<T> implements CommandStringDisplayable {
   private final T data;
   private final double percentage;
   private final Function<T, String> commandStringFunc;

   public Percentaged(T data, double percentage, Function<T, String> commandStringFunc) {
      this.data = data;
      this.percentage = percentage;
      this.commandStringFunc = commandStringFunc;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("data")
   public T getData() {
      return this.data;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("percentage")
   public double getPercentage() {
      return this.percentage;
   }

   @Override
   public String getCommandString() {
      return this.commandStringFunc.apply(this.getData());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Percentaged<?> that = (Percentaged<?>)o;
         return Double.compare(that.getPercentage(), this.getPercentage()) != 0 ? false : this.getData().equals(that.getData());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.getData().hashCode();
      long temp = Double.doubleToLongBits(this.getPercentage());
      return 31 * result + (int)(temp ^ temp >>> 32);
   }
}
