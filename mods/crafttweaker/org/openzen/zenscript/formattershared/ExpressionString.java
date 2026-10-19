package org.openzen.zenscript.formattershared;

public class ExpressionString {
   public final String value;
   public final FormattableOperator priority;

   public ExpressionString(String value, FormattableOperator priority) {
      this.value = value;
      this.priority = priority;
   }

   public static ExpressionString binary(ExpressionString left, ExpressionString right, FormattableOperator operator) {
      String value = left.wrapLeft(operator) + operator.getOperatorString() + right.wrapRight(operator);
      return new ExpressionString(value, operator);
   }

   public String wrapLeft(FormattableOperator outer) {
      return FormattableOperator.shouldWrapLeft(this.priority, outer) ? "(" + this.value + ")" : this.value;
   }

   public String wrapRight(FormattableOperator outer) {
      return FormattableOperator.shouldWrapRight(this.priority, outer) ? "(" + this.value + ")" : this.value;
   }

   @Override
   public String toString() {
      return this.value;
   }

   public ExpressionString unaryPrefix(FormattableOperator operator) {
      return new ExpressionString(operator.getOperatorString() + this.wrapRight(operator), operator);
   }

   public ExpressionString unaryPostfix(FormattableOperator operator) {
      return new ExpressionString(this.wrapLeft(operator) + operator.getOperatorString(), operator);
   }

   public ExpressionString unaryPrefix(FormattableOperator operator, String operatorString) {
      return new ExpressionString(operatorString + this.wrapRight(operator), operator);
   }

   public ExpressionString unaryPostfix(FormattableOperator operator, String operatorString) {
      return new ExpressionString(this.wrapLeft(operator) + operatorString, operator);
   }
}
