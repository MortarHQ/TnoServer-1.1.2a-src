package org.openzen.zenscript.formattershared;

public interface FormattableOperator {
   static boolean shouldWrapLeft(FormattableOperator inner, FormattableOperator outer) {
      return inner != outer && inner.getPriority() <= outer.getPriority();
   }

   static boolean shouldWrapRight(FormattableOperator inner, FormattableOperator outer) {
      return inner.getPriority() <= outer.getPriority();
   }

   int getPriority();

   String getOperatorString();
}
