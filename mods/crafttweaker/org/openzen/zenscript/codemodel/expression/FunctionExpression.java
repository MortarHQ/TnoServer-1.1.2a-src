package org.openzen.zenscript.codemodel.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.ReturnStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.TypeID;

public class FunctionExpression extends Expression {
   public final FunctionHeader header;
   public final LambdaClosure closure;
   public final Statement body;

   public FunctionExpression(CodePosition position, TypeID type, LambdaClosure closure, FunctionHeader header, Statement body) {
      super(position, type, body.thrownType);
      this.header = header;
      this.closure = closure;
      this.body = body;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitFunction(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitFunction(context, this);
   }

   public FunctionExpression transform(ExpressionTransformer transformer) {
      Statement tBody = this.body.transform(transformer, ConcatMap.empty(LoopStatement.class, LoopStatement.class));
      return tBody == this.body ? this : new FunctionExpression(this.position, this.type, this.closure, this.header, tBody);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      this.body.forEachStatement(consumer);
   }

   public boolean isSimple() {
      return this.body instanceof ReturnStatement || this.body instanceof ExpressionStatement;
   }

   public Expression asReturnExpression(Expression... arguments) {
      Map<FunctionParameter, Expression> filledArguments = new HashMap<>();
      return this.body instanceof ReturnStatement
         ? ((ReturnStatement)this.body).value.transform(new FunctionExpression.ReturnExpressionTransformer(this.closure, filledArguments))
         : null;
   }

   @Override
   public Expression normalize(TypeScope scope) {
      return new FunctionExpression(
         this.position, this.type, this.closure, this.header, this.body.normalize(scope, ConcatMap.empty(LoopStatement.class, LoopStatement.class))
      );
   }

   private static class ReturnExpressionTransformer implements ExpressionTransformer {
      private final LambdaClosure closure;
      private final Map<FunctionParameter, Expression> filledArguments;

      public ReturnExpressionTransformer(LambdaClosure closure, Map<FunctionParameter, Expression> filledArguments) {
         this.closure = closure;
         this.filledArguments = filledArguments;
      }

      @Override
      public Expression transform(Expression original) {
         return original instanceof GetFunctionParameterExpression getParameter && this.filledArguments.containsKey(getParameter.parameter)
            ? this.filledArguments.get(getParameter.parameter)
            : original;
      }
   }
}
