package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.member.ref.GetterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class GetterExpression extends Expression {
   public final Expression target;
   public final GetterMemberRef getter;

   public GetterExpression(CodePosition position, Expression target, GetterMemberRef getter) {
      super(position, getter.getType(), target.thrownType);
      this.target = target;
      this.getter = getter;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitGetter(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitGetter(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tTarget = this.target.transform(transformer);
      return this.target == tTarget ? this : new GetterExpression(this.position, tTarget, this.getter);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new GetterExpression(this.position, this.target.normalize(scope), this.getter);
   }

   @Override
   public Expression assign(CodePosition position, TypeScope scope, Expression value) throws CompileException {
      return scope.getTypeMembers(this.getter.getOwnerType()).getGroup(this.getter.member.name).setter(position, scope, this.target, value, false);
   }
}
