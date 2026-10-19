package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class TryRethrowAsResultExpression extends Expression {
   public final Expression value;

   public TryRethrowAsResultExpression(CodePosition position, TypeID type, Expression value) {
      super(position, type, null);
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitTryRethrowAsResult(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitTryRethrowAsResult(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      return tValue == this.value ? this : new TryRethrowAsResultExpression(this.position, this.type, tValue);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new TryRethrowAsResultExpression(this.position, this.type, this.value.normalize(scope));
   }
}
