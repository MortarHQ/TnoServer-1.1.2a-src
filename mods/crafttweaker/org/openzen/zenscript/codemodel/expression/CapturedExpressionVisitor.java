package org.openzen.zenscript.codemodel.expression;

public interface CapturedExpressionVisitor<T> {
   T visitCapturedThis(CapturedThisExpression var1);

   T visitCapturedParameter(CapturedParameterExpression var1);

   T visitCapturedLocal(CapturedLocalVariableExpression var1);

   T visitCapturedDirect(CapturedDirectExpression var1);

   T visitRecaptured(CapturedClosureExpression var1);
}
