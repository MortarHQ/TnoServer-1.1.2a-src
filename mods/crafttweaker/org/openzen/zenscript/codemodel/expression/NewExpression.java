package org.openzen.zenscript.codemodel.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class NewExpression extends Expression {
   public final FunctionalMemberRef constructor;
   public final CallArguments arguments;
   public final FunctionHeader instancedHeader;

   public NewExpression(CodePosition position, TypeID type, FunctionalMemberRef constructor, CallArguments arguments) {
      this(position, type, constructor, arguments, constructor.getHeader());
   }

   public NewExpression(CodePosition position, TypeID type, FunctionalMemberRef constructor, CallArguments arguments, FunctionHeader instancedHeader) {
      super(position, type, binaryThrow(position, constructor.getHeader().thrownType, multiThrow(position, arguments.arguments)));
      this.constructor = constructor;
      this.arguments = arguments;
      this.instancedHeader = instancedHeader;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitNew(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitNew(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      CallArguments tArguments = this.arguments.transform(transformer);
      return tArguments == this.arguments ? this : new NewExpression(this.position, this.type, this.constructor, tArguments, this.instancedHeader);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new NewExpression(
         this.position, this.type.getNormalized(), this.constructor, this.arguments.normalize(this.position, scope, this.instancedHeader), this.instancedHeader
      );
   }
}
