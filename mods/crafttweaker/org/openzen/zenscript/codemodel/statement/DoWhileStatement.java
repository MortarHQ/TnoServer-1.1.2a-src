package org.openzen.zenscript.codemodel.statement;

import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class DoWhileStatement extends LoopStatement {
   public final Expression condition;
   public Statement content;

   public DoWhileStatement(CodePosition position, String label, Expression condition) {
      super(position, label, null);
      this.condition = condition;
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitDoWhile(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitDoWhile(context, this);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);
      this.content.forEachStatement(consumer);
   }

   @Override
   public Statement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tCondition = this.condition.transform(transformer);
      Statement tContent = this.content.transform(transformer, modified);
      if (tCondition == this.condition && tContent == this.content) {
         return this;
      }

      DoWhileStatement result = new DoWhileStatement(this.position, this.label, this.condition);
      result.content = this.content.transform(transformer, modified.concat(this, result));
      return result;
   }

   @Override
   public Statement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tCondition = this.condition.transform(transformer);
      Statement tContent = this.content.transform(transformer, modified);
      if (tCondition == this.condition && tContent == this.content) {
         return this;
      }

      DoWhileStatement result = new DoWhileStatement(this.position, this.label, tCondition);
      result.content = this.content.transform(transformer, modified.concat(this, result));
      return result;
   }

   @Override
   public Statement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      DoWhileStatement result = new DoWhileStatement(this.position, this.label, this.condition.normalize(scope));
      result.content = this.content.normalize(scope, modified.concat(this, result));
      return result;
   }

   @Override
   public TypeID getReturnType() {
      return this.content.getReturnType();
   }
}
