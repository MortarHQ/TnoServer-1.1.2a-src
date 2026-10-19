package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;

// $VF: synthetic class
final class GenericDiffCalculatorInstance$run$1 extends FunctionReferenceImpl implements Function2 {
   public static final GenericDiffCalculatorInstance$run$1 INSTANCE = new GenericDiffCalculatorInstance$run$1();

   GenericDiffCalculatorInstance$run$1() {
      super(
         2,
         SimpleDiffCalculatorInstance.class,
         "<init>",
         "<init>(Lorg/anti_ad/mc/ipnext/inventory/sandbox/ContainerSandbox;Lorg/anti_ad/mc/ipnext/inventory/data/ItemTracker;)V",
         0
      );
   }

   public final SimpleDiffCalculatorInstance invoke(ContainerSandbox var1, ItemTracker var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return new SimpleDiffCalculatorInstance(var1, var2);
   }
}
