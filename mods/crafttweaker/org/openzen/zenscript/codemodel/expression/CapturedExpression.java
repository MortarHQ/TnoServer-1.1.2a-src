package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public abstract class CapturedExpression extends Expression {
   public final LambdaClosure closure;

   public CapturedExpression(CodePosition position, TypeID type, LambdaClosure closure) {
      super(position, type, null);
      this.closure = closure;
   }

   public CapturedExpression capture(CodePosition position, LambdaClosure closure) {
      CapturedExpression result = new CapturedClosureExpression(position, this, closure);
      closure.add(result);
      return result;
   }

   public abstract CapturedExpression normalize(TypeScope var1);

   public abstract <T> T accept(CapturedExpressionVisitor<T> var1);
}
