package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ArrayExpression extends Expression {
   public final Expression[] expressions;
   public final ArrayTypeID arrayType;

   public ArrayExpression(CodePosition position, Expression[] expressions, TypeID type) {
      super(position, type, multiThrow(position, expressions));
      this.expressions = expressions;
      this.arrayType = (ArrayTypeID)type;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitArray(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitArray(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression[] tExpressions = Expression.transform(this.expressions, transformer);
      return tExpressions == this.expressions ? this : new ArrayExpression(this.position, tExpressions, this.type);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      Expression[] normalized = new Expression[this.expressions.length];

      for (int i = 0; i < normalized.length; i++) {
         normalized[i] = this.expressions[i].normalize(scope);
      }

      return new ArrayExpression(this.position, normalized, this.type.getNormalized());
   }
}
