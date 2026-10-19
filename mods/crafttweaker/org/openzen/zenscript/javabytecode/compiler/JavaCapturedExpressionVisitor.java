package org.openzen.zenscript.javabytecode.compiler;

import org.openzen.zenscript.codemodel.expression.CapturedClosureExpression;
import org.openzen.zenscript.codemodel.expression.CapturedDirectExpression;
import org.openzen.zenscript.codemodel.expression.CapturedExpressionVisitor;
import org.openzen.zenscript.codemodel.expression.CapturedLocalVariableExpression;
import org.openzen.zenscript.codemodel.expression.CapturedParameterExpression;
import org.openzen.zenscript.codemodel.expression.CapturedThisExpression;
import org.openzen.zenscript.codemodel.expression.ExpressionVisitor;
import org.openzen.zenscript.codemodel.expression.GetFunctionParameterExpression;
import org.openzen.zenscript.codemodel.expression.GetLocalVariableExpression;

public class JavaCapturedExpressionVisitor implements CapturedExpressionVisitor<Void> {
   public final ExpressionVisitor<Void> expressionVisitor;

   public JavaCapturedExpressionVisitor(ExpressionVisitor<Void> expressionVisitor) {
      this.expressionVisitor = expressionVisitor;
   }

   public Void visitCapturedThis(CapturedThisExpression expression) {
      return null;
   }

   public Void visitCapturedParameter(CapturedParameterExpression expression) {
      return new GetFunctionParameterExpression(expression.position, expression.parameter).accept(this.expressionVisitor);
   }

   public Void visitCapturedLocal(CapturedLocalVariableExpression expression) {
      return new GetLocalVariableExpression(expression.position, expression.variable).accept(this.expressionVisitor);
   }

   public Void visitCapturedDirect(CapturedDirectExpression expression) {
      return expression.value.accept(this.expressionVisitor);
   }

   public Void visitRecaptured(CapturedClosureExpression expression) {
      return expression.value.accept(this);
   }
}
