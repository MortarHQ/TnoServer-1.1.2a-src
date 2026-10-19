package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.CompareType;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;

public class CompareExpression extends Expression {
   public final Expression left;
   public final Expression right;
   public final FunctionalMemberRef operator;
   public final CompareType comparison;

   public CompareExpression(CodePosition position, Expression left, Expression right, FunctionalMemberRef operator, CompareType comparison) {
      super(position, BasicTypeID.BOOL, binaryThrow(position, left.thrownType, right.thrownType));
      this.left = left;
      this.right = right;
      this.operator = operator;
      this.comparison = comparison;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitCompare(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitCompare(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tLeft = this.left.transform(transformer);
      Expression tRight = this.right.transform(transformer);
      return this.left == tLeft && this.right == tRight ? this : new CompareExpression(this.position, tLeft, tRight, this.operator, this.comparison);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new CompareExpression(
         this.position,
         this.left.normalize(scope),
         this.right.normalize(scope).castImplicit(this.position, scope, this.operator.getHeader().parameters[0].type),
         this.operator,
         this.comparison
      );
   }
}
