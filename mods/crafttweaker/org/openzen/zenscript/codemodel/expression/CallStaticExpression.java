package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class CallStaticExpression extends Expression {
   public final FunctionalMemberRef member;
   public final TypeID target;
   public final CallArguments arguments;
   public final FunctionHeader instancedHeader;

   public CallStaticExpression(CodePosition position, TypeID target, FunctionalMemberRef member, FunctionHeader instancedHeader, CallArguments arguments) {
      super(position, instancedHeader.getReturnType(), multiThrow(position, arguments.arguments));
      this.member = member;
      this.target = target;
      this.arguments = arguments;
      this.instancedHeader = instancedHeader;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitCallStatic(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitCallStatic(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      CallArguments tArguments = this.arguments.transform(transformer);
      return this.arguments == tArguments ? this : new CallStaticExpression(this.position, this.target, this.member, this.instancedHeader, tArguments);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new CallStaticExpression(
         this.position,
         this.target.getNormalized(),
         this.member,
         this.instancedHeader.normalize(scope.getTypeRegistry()),
         this.arguments.normalize(this.position, scope, this.instancedHeader)
      );
   }
}
