package org.openzen.zenscript.codemodel.statement;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class BlockStatement extends Statement {
   public final Statement[] statements;

   public BlockStatement(CodePosition position, Statement[] statements) {
      super(position, getThrownType(statements));
      this.statements = statements;
   }

   private static TypeID getThrownType(Statement[] statements) {
      TypeID result = null;

      for (Statement statement : statements) {
         result = Expression.binaryThrow(statement.position, result, statement.thrownType);
      }

      return result;
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitBlock(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitBlock(context, this);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);

      for (Statement s : this.statements) {
         s.forEachStatement(consumer);
      }
   }

   @Override
   public Statement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Statement[] tStatements = new Statement[this.statements.length];
      boolean unchanged = true;

      for (int i = 0; i < this.statements.length; i++) {
         Statement statement = this.statements[i];
         Statement tStatement = statement.transform(transformer, modified);
         unchanged &= statement == tStatement;
         tStatements[i] = statement;
      }

      return unchanged ? this : new BlockStatement(this.position, tStatements);
   }

   @Override
   public Statement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Statement[] tStatements = new Statement[this.statements.length];
      boolean unchanged = true;

      for (int i = 0; i < tStatements.length; i++) {
         Statement tStatement = this.statements[i].transform(transformer, modified);
         unchanged &= this.statements[i] == tStatement;
         tStatements[i] = tStatement;
      }

      return unchanged ? this : new BlockStatement(this.position, tStatements);
   }

   @Override
   public Statement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      Statement[] normalized = new Statement[this.statements.length];
      int i = 0;

      for (Statement statement : this.statements) {
         normalized[i++] = statement.normalize(scope, modified);
      }

      return new BlockStatement(this.position, normalized);
   }

   @Override
   public TypeID getReturnType() {
      List<TypeID> collect = Arrays.stream(this.statements).map(Statement::getReturnType).filter(Objects::nonNull).distinct().collect(Collectors.toList());
      if (collect.isEmpty()) {
         return super.getReturnType();
      } else if (collect.size() == 1) {
         return collect.get(0);
      } else {
         throw new IllegalStateException("More than one possible type: " + collect.size());
      }
   }
}
