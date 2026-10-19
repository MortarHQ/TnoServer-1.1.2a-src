package com.blorb.morerelics.dataComponents;

public record IntegerCounter(int current) {
   public IntegerCounter getUpdatedCounter(int newValue) {
      return new IntegerCounter(newValue);
   }
}
