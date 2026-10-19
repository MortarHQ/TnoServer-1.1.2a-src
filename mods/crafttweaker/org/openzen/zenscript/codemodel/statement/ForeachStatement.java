package org.openzen.zenscript.codemodel.statement;

import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.member.ref.IteratorMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ForeachStatement extends LoopStatement {
   public final VarStatement[] loopVariables;
   public final Expression list;
   public final IteratorMemberRef iterator;
   public Statement content;

   public ForeachStatement(CodePosition position, VarStatement[] loopVariables, IteratorMemberRef iterator, Expression list) {
      super(position, loopVariables[0].name, null);
      this.loopVariables = loopVariables;
      this.list = list;
      this.iterator = iterator;
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitForeach(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitForeach(context, this);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);
      this.content.forEachStatement(consumer);
   }

   @Override
   public Statement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tList = this.list.transform(transformer);
      Statement tContent = this.content.transform(transformer, modified);
      if (tList == this.list && tContent == this.content) {
         return this;
      }

      ForeachStatement result = new ForeachStatement(this.position, this.loopVariables, this.iterator, tList);
      result.content = this.content.transform(transformer, modified.concat(this, result));
      return result;
   }

   @Override
   public Statement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tList = this.list.transform(transformer);
      Statement tContent = this.content.transform(transformer, modified);
      if (tList == this.list && tContent == this.content) {
         return this;
      }

      ForeachStatement result = new ForeachStatement(this.position, this.loopVariables, this.iterator, tList);
      result.content = this.content.transform(transformer, modified.concat(this, result));
      return result;
   }

   @Override
   public Statement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      ForeachStatement result = new ForeachStatement(this.position, this.loopVariables, this.iterator, this.list.normalize(scope));
      result.content = this.content.normalize(scope, modified.concat(this, result));
      return result;
   }

   @Override
   public TypeID getReturnType() {
      return this.content.getReturnType();
   }
}
