package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.member.ref.CasterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class CastExpression extends Expression {
   public final Expression target;
   public final CasterMemberRef member;
   public final boolean isImplicit;

   public CastExpression(CodePosition position, Expression target, CasterMemberRef member, boolean isImplicit) {
      super(position, member.toType, binaryThrow(position, target.thrownType, member.member.header.thrownType));
      this.target = target;
      this.member = member;
      this.isImplicit = isImplicit;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitCast(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitCast(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tTarget = this.target.transform(transformer);
      return this.target == tTarget ? this : new CastExpression(this.position, tTarget, this.member, this.isImplicit);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new CastExpression(this.position, this.target.normalize(scope), this.member, this.isImplicit);
   }
}
