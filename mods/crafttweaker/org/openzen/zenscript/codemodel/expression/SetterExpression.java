package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.member.ref.SetterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class SetterExpression extends Expression {
   public final Expression target;
   public final SetterMemberRef setter;
   public final Expression value;

   public SetterExpression(CodePosition position, Expression target, SetterMemberRef setter, Expression value) {
      super(position, setter.getType(), value.thrownType);
      this.target = target;
      this.setter = setter;
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitSetter(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitSetter(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tTarget = this.target.transform(transformer);
      Expression tValue = this.value.transform(transformer);
      return tTarget == this.target && tValue == this.value ? this : new SetterExpression(this.position, tTarget, this.setter, tValue);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new SetterExpression(this.position, this.target.normalize(scope), this.setter, this.value.normalize(scope));
   }
}
