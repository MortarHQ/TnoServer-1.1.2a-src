package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class SupertypeCastExpression extends Expression {
   public final Expression value;

   public SupertypeCastExpression(CodePosition position, Expression value, TypeID type) {
      super(position, type, value.thrownType);
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitSupertypeCast(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitSupertypeCast(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      return tValue == this.value ? this : new SupertypeCastExpression(this.position, tValue, this.type);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new SupertypeCastExpression(this.position, this.value.normalize(scope), this.type.getNormalized());
   }
}
