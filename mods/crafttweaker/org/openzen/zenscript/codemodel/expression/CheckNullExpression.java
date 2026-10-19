package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class CheckNullExpression extends Expression {
   public final Expression value;

   public CheckNullExpression(CodePosition position, Expression value) {
      super(position, value.type.withoutOptional(), value.thrownType);
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitCheckNull(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitCheckNull(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = transformer.transform(this.value);
      return this.value == tValue ? this : new CheckNullExpression(this.position, tValue);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new CheckNullExpression(this.position, this.value.normalize(scope));
   }
}
