package com.blorb.morerelics.dataComponents;

import java.util.Objects;

public class DoubleCounter {
   public double current = 0.0;

   public DoubleCounter(double current) {
      this.current = current;
   }

   public double getCurrent() {
      return this.current;
   }

   public void setCurrent(double current) {
      this.current = current;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.current);
   }

   @Override
   public boolean equals(Object obj) {
      return obj == this ? true : obj instanceof DoubleCounter ex && this.current == ex.current;
   }
}
