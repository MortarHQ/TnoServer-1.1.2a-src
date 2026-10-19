package org.openzen.zenscript.codemodel.statement;

import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ReturnStatement extends Statement {
   public final Expression value;

   public ReturnStatement(CodePosition position, Expression value) {
      super(position, value == null ? null : value.thrownType);
      this.value = value;
   }

   @Override
   public TypeID getReturnType() {
      return this.value != null ? this.value.type : BasicTypeID.VOID;
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);
   }

   @Override
   public Statement withReturnType(TypeScope scope, TypeID returnType) {
      return new ReturnStatement(this.position, this.value == null ? null : this.value.castImplicit(this.position, scope, returnType));
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitReturn(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitReturn(context, this);
   }

   @Override
   public Statement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tValue = this.value == null ? null : this.value.transform(transformer);
      return tValue == this.value ? this : new ReturnStatement(this.position, tValue);
   }

   @Override
   public Statement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tValue = this.value == null ? null : this.value.transform(transformer);
      return tValue == this.value ? this : new ReturnStatement(this.position, tValue);
   }

   @Override
   public Statement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      return new ReturnStatement(this.position, this.value == null ? null : this.value.normalize(scope));
   }
}
