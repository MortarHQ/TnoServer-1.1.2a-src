package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.member.ref.ImplementationMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class InterfaceCastExpression extends Expression {
   public final Expression value;
   public final ImplementationMemberRef implementation;

   public InterfaceCastExpression(CodePosition position, Expression value, ImplementationMemberRef implementation) {
      super(position, implementation.implementsType, value.thrownType);
      this.value = value;
      this.implementation = implementation;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitInterfaceCast(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitInterfaceCast(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      return this.value == tValue ? this : new InterfaceCastExpression(this.position, tValue, this.implementation);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new InterfaceCastExpression(this.position, this.value.normalize(scope), this.implementation);
   }
}
