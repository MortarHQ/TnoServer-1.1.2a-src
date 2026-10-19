package com.iafenvoy.uranus.util;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StackUnderflowError extends Error {
   public StackUnderflowError() {
   }

   public StackUnderflowError(String s) {
      super(s);
   }
}
