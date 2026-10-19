package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class CallExpression extends Expression {
   public final Expression target;
   public final FunctionalMemberRef member;
   public final CallArguments arguments;
   public final FunctionHeader instancedHeader;

   public CallExpression(CodePosition position, Expression target, FunctionalMemberRef member, FunctionHeader instancedHeader, CallArguments arguments) {
      super(position, instancedHeader.getReturnType(), multiThrow(position, arguments.arguments));
      this.target = target;
      this.member = member;
      this.arguments = arguments;
      this.instancedHeader = instancedHeader;
   }

   public Expression getFirstArgument() {
      return this.arguments.arguments[0];
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitCall(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitCall(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tTarget = this.target.transform(transformer);
      CallArguments tArguments = this.arguments.transform(transformer);
      return tTarget == this.target && tArguments == this.arguments
         ? this
         : new CallExpression(this.position, tTarget, this.member, this.instancedHeader, tArguments);
   }

   @Override
   public String evaluateStringConstant() {
      if (this.member.getBuiltin() == null) {
         throw new UnsupportedOperationException("Cannot evaluate to a string constant!");
      }

      switch (this.member.getBuiltin()) {
         case STRING_ADD_STRING:
            return this.target.evaluateStringConstant() + this.arguments.arguments[0].evaluateStringConstant();
         default:
            throw new UnsupportedOperationException("Cannot evaluate to a string constant!");
      }
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new CallExpression(
         this.position,
         this.target.normalize(scope),
         this.member,
         this.instancedHeader.normalize(scope.getTypeRegistry()),
         this.arguments.normalize(this.position, scope, this.instancedHeader)
      );
   }
}
