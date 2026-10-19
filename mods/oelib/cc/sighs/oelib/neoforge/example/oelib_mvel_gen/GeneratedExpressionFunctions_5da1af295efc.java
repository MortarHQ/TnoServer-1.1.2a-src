package cc.sighs.oelib.neoforge.example.oelib_mvel_gen;

import cc.sighs.oelib.data.mvel.gen.ExpressionFunctionRegistry;
import cc.sighs.oelib.data.mvel.gen.ExpressionFunctionsRegistrar;
import cc.sighs.oelib.neoforge.example.CoreFunctions;
import java.util.Set;

public final class GeneratedExpressionFunctions_5da1af295efc implements ExpressionFunctionsRegistrar {
   @Override
   public void register(ExpressionFunctionRegistry registry, Set<String> requiredFunctions) {
      if (requiredFunctions == null || requiredFunctions.contains("isModLoaded")) {
         registry.register("isModLoaded", CoreFunctions.class, "isModLoaded", String.class);
      }
   }
}
