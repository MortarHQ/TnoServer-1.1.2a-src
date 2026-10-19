package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class TryRethrowAsExceptionExpression extends Expression {
   public final Expression value;

   public TryRethrowAsExceptionExpression(CodePosition position, TypeID type, Expression value, TypeID thrownType) {
      super(position, type, thrownType);
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitTryRethrowAsException(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitTryRethrowAsException(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      return tValue == this.value ? this : new TryRethrowAsExceptionExpression(this.position, this.type, tValue, this.thrownType);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new TryRethrowAsExceptionExpression(this.position, this.type, this.value.normalize(scope), this.thrownType.getNormalized());
   }
}
