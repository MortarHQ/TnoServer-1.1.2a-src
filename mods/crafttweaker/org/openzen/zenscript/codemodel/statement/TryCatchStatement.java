package org.openzen.zenscript.codemodel.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class TryCatchStatement extends Statement {
   public final VarStatement resource;
   public final Statement content;
   public final List<CatchClause> catchClauses;
   public final Statement finallyClause;

   public TryCatchStatement(CodePosition position, VarStatement resource, Statement content, List<CatchClause> catchClauses, Statement finallyClause) {
      super(position, null);
      this.resource = resource;
      this.content = content;
      this.catchClauses = catchClauses;
      this.finallyClause = finallyClause;
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitTryCatch(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitTryCatch(context, this);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);

      for (CatchClause catchClause : this.catchClauses) {
         catchClause.content.forEachStatement(consumer);
      }

      if (this.finallyClause != null) {
         this.finallyClause.forEachStatement(consumer);
      }
   }

   @Override
   public Statement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      VarStatement tResource = this.resource == null ? null : this.resource.transform(transformer, modified);
      Statement tContent = this.content.transform(transformer, modified);
      List<CatchClause> tCatchClauses = new ArrayList<>();

      for (CatchClause clause : this.catchClauses) {
         tCatchClauses.add(clause.transform(transformer, modified));
      }

      Statement tFinallyClause = this.finallyClause == null ? null : this.finallyClause.transform(transformer, modified);
      return new TryCatchStatement(this.position, tResource, tContent, tCatchClauses, tFinallyClause);
   }

   @Override
   public Statement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      VarStatement tResource = this.resource == null ? null : this.resource.transform(transformer, modified);
      Statement tContent = this.content.transform(transformer, modified);
      List<CatchClause> tCatchClauses = new ArrayList<>();

      for (CatchClause clause : this.catchClauses) {
         tCatchClauses.add(clause.transform(transformer, modified));
      }

      Statement tFinallyClause = this.finallyClause == null ? null : this.finallyClause.transform(transformer, modified);
      return new TryCatchStatement(this.position, tResource, tContent, tCatchClauses, tFinallyClause);
   }

   @Override
   public Statement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      VarStatement tResource = this.resource == null ? null : this.resource.normalize(scope, modified);
      Statement tContent = this.content.normalize(scope, modified);
      List<CatchClause> tCatchClauses = new ArrayList<>();

      for (CatchClause clause : this.catchClauses) {
         tCatchClauses.add(clause.normalize(scope, modified));
      }

      Statement tFinallyClause = this.finallyClause == null ? null : this.finallyClause.normalize(scope, modified);
      return new TryCatchStatement(this.position, tResource, tContent, tCatchClauses, tFinallyClause);
   }

   @Override
   public TypeID getReturnType() {
      return this.finallyClause != null && this.finallyClause.getReturnType() != null ? this.finallyClause.getReturnType() : this.content.getReturnType();
   }
}
