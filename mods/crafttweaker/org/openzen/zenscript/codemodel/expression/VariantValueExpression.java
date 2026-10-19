package org.openzen.zenscript.codemodel.expression;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.member.ref.VariantOptionRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class VariantValueExpression extends Expression {
   public final VariantOptionRef option;
   public final Expression[] arguments;

   public VariantValueExpression(CodePosition position, TypeID variantType, VariantOptionRef option) {
      this(position, variantType, option, Expression.NONE);
   }

   public VariantValueExpression(CodePosition position, TypeID variantType, VariantOptionRef option, Expression[] arguments) {
      super(position, variantType, multiThrow(position, arguments));
      this.option = option;
      this.arguments = arguments;
   }

   public int getNumberOfArguments() {
      return this.arguments == null ? 0 : this.arguments.length;
   }

   @Override
   public Expression call(CodePosition position, TypeScope scope, List<TypeID> hints, CallArguments arguments) throws CompileException {
      return arguments != null
         ? super.call(position, scope, hints, arguments)
         : new VariantValueExpression(position, this.type, this.option, arguments.arguments);
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitVariantValue(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitVariantValue(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression[] tArguments = Expression.transform(this.arguments, transformer);
      return tArguments == this.arguments ? this : new VariantValueExpression(this.position, this.type, this.option, tArguments);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      Expression[] normalized = new Expression[this.arguments.length];

      for (int i = 0; i < normalized.length; i++) {
         normalized[i] = this.arguments[i].normalize(scope).castImplicit(this.position, scope, this.option.types[i]);
      }

      return new VariantValueExpression(this.position, this.type, this.option, normalized);
   }
}
