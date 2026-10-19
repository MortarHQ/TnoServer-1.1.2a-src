package com.blorb.morerelics.dataComponents;

import java.util.Objects;

public class BoolHolder {
   public boolean bool = false;

   public BoolHolder(boolean bool) {
      this.bool = bool;
   }

   public boolean getBool() {
      return this.bool;
   }

   public void setBool(boolean bool) {
      this.bool = bool;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.bool);
   }

   @Override
   public boolean equals(Object obj) {
      return obj == this ? true : obj instanceof BoolHolder ex && this.bool == ex.bool;
   }
}
