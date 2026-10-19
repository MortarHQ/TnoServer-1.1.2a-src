package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class SubtypeCastExpression extends Expression {
   public final Expression value;

   public SubtypeCastExpression(CodePosition position, Expression value, TypeID type) {
      super(position, type, value.thrownType);
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitSubtypeCast(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitSubtypeCast(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      return tValue == this.value ? this : new SubtypeCastExpression(this.position, tValue, this.type);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new SubtypeCastExpression(this.position, this.value.normalize(scope), this.type.getNormalized());
   }
}
