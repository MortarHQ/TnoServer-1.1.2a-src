package com.finderfeed.fdlib.shunting_yard.functions;

import com.finderfeed.fdlib.shunting_yard.sy_base.SYFunction;
import java.util.List;
import net.minecraft.util.Mth;

public class ClampFunction extends SYFunction {
   public ClampFunction() {
      super(3);
   }

   @Override
   public float compute(List<Float> args) {
      return Mth.clamp(args.get(0), args.get(1), args.get(2));
   }
}
