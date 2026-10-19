package com.finderfeed.fdlib.shunting_yard.sy_base;

public class SYStaticValue extends SYVariable {
   private float value;

   public SYStaticValue(float val) {
      this.value = val;
   }

   @Override
   public float getValue(ExpressionContext context) {
      return this.value;
   }

   @Override
   public String toString() {
      return this.value + "";
   }
}
