package org.openzen.zenscript.codemodel.statement;

import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class VarStatement extends Statement {
   public final String name;
   public final TypeID type;
   public final Expression initializer;
   public final VariableID variable;
   public final boolean isFinal;

   public VarStatement(CodePosition position, VariableID variable, String name, TypeID type, Expression initializer, boolean isFinal) {
      super(position, initializer == null ? null : initializer.thrownType);
      this.name = name;
      this.type = type;
      this.initializer = initializer;
      this.variable = variable;
      this.isFinal = isFinal;
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitVar(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitVar(context, this);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);
   }

   public VarStatement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tInitializer = this.initializer == null ? null : this.initializer.transform(transformer);
      return tInitializer == this.initializer ? this : new VarStatement(this.position, this.variable, this.name, this.type, tInitializer, this.isFinal);
   }

   public VarStatement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tInitializer = this.initializer == null ? null : this.initializer.transform(transformer);
      return tInitializer == this.initializer ? this : new VarStatement(this.position, this.variable, this.name, this.type, tInitializer, this.isFinal);
   }

   public VarStatement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      return new VarStatement(
         this.position, this.variable, this.name, this.type.getNormalized(), this.initializer == null ? null : this.initializer.normalize(scope), this.isFinal
      );
   }
}
