package org.openzen.zenscript.codemodel.statement;

import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zencode.shared.Taggable;
import org.openzen.zenscript.codemodel.annotations.StatementAnnotation;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public abstract class Statement extends Taggable {
   public final CodePosition position;
   public final TypeID thrownType;
   public StatementAnnotation[] annotations = StatementAnnotation.NONE;

   public Statement(CodePosition position, TypeID thrownType) {
      this.position = position;
      this.thrownType = thrownType;
   }

   public TypeID getReturnType() {
      return null;
   }

   public Statement withReturnType(TypeScope scope, TypeID returnType) {
      return this;
   }

   public abstract <T> T accept(StatementVisitor<T> var1);

   public abstract <C, R> R accept(C var1, StatementVisitorWithContext<C, R> var2);

   public abstract void forEachStatement(Consumer<Statement> var1);

   public abstract Statement normalize(TypeScope var1, ConcatMap<LoopStatement, LoopStatement> var2);

   public final Statement transform(StatementTransformer transformer) {
      return this.transform(transformer, ConcatMap.empty(LoopStatement.class, LoopStatement.class));
   }

   public abstract Statement transform(StatementTransformer var1, ConcatMap<LoopStatement, LoopStatement> var2);

   public abstract Statement transform(ExpressionTransformer var1, ConcatMap<LoopStatement, LoopStatement> var2);
}
