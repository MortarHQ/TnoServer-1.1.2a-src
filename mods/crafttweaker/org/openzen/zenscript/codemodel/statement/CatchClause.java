package org.openzen.zenscript.codemodel.statement;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;

public class CatchClause {
   public final CodePosition position;
   public final Statement content;
   public final VarStatement exceptionVariable;

   public CatchClause(CodePosition position, VarStatement exceptionVariable, Statement content) {
      this.position = position;
      this.exceptionVariable = exceptionVariable;
      this.content = content;
   }

   public CatchClause transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Statement tContent = this.content.transform(transformer, modified);
      return this.content == tContent ? this : new CatchClause(this.position, this.exceptionVariable, tContent);
   }

   public CatchClause transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Statement tContent = this.content.transform(transformer, modified);
      return this.content == tContent ? this : new CatchClause(this.position, this.exceptionVariable, tContent);
   }

   public CatchClause normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      Statement tContent = this.content.normalize(scope, modified);
      return this.content == tContent ? this : new CatchClause(this.position, this.exceptionVariable, tContent);
   }
}
