package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.member.ref.FieldMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class SetStaticFieldExpression extends Expression {
   public final FieldMemberRef field;
   public final Expression value;

   public SetStaticFieldExpression(CodePosition position, FieldMemberRef field, Expression value) {
      super(position, field.getType(), value.thrownType);
      this.field = field;
      this.value = value;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitSetStaticField(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitSetStaticField(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      return this.value == tValue ? this : new SetStaticFieldExpression(this.position, this.field, tValue);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new SetStaticFieldExpression(this.position, this.field, this.value.normalize(scope));
   }
}
