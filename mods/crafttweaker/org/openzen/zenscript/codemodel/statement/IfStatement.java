package org.openzen.zenscript.codemodel.statement;

import java.util.function.Consumer;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.ExpressionTransformer;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;

public class IfStatement extends Statement {
   public final Expression condition;
   public final Statement onThen;
   public final Statement onElse;

   public IfStatement(CodePosition position, Expression condition, Statement onThen, Statement onElse) {
      super(position, getThrownType(condition, onThen, onElse));
      this.condition = condition;
      this.onThen = onThen;
      this.onElse = onElse;
   }

   private static TypeID getThrownType(Expression condition, Statement onThen, Statement onElse) {
      TypeID result = Expression.binaryThrow(onThen.position, condition.thrownType, onThen.thrownType);
      if (onElse != null) {
         result = Expression.binaryThrow(onElse.position, result, onElse.thrownType);
      }

      return result;
   }

   @Override
   public <T> T accept(StatementVisitor<T> visitor) {
      return visitor.visitIf(this);
   }

   @Override
   public <C, R> R accept(C context, StatementVisitorWithContext<C, R> visitor) {
      return visitor.visitIf(context, this);
   }

   @Override
   public void forEachStatement(Consumer<Statement> consumer) {
      consumer.accept(this);
      this.onThen.forEachStatement(consumer);
      if (this.onElse != null) {
         this.onElse.forEachStatement(consumer);
      }
   }

   @Override
   public Statement transform(StatementTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tCondition = this.condition.transform(transformer);
      Statement tOnThen = this.onThen.transform(transformer, modified);
      Statement tOnElse = this.onElse == null ? null : this.onElse.transform(transformer, modified);
      return tCondition == this.condition && this.onThen == tOnThen && this.onElse == tOnElse
         ? this
         : new IfStatement(this.position, tCondition, tOnThen, tOnElse);
   }

   @Override
   public Statement transform(ExpressionTransformer transformer, ConcatMap<LoopStatement, LoopStatement> modified) {
      Expression tCondition = this.condition.transform(transformer);
      Statement tOnThen = this.onThen.transform(transformer, modified);
      Statement tOnElse = this.onElse == null ? null : this.onElse.transform(transformer, modified);
      return tCondition == this.condition && this.onThen == tOnThen && this.onElse == tOnElse
         ? this
         : new IfStatement(this.position, tCondition, tOnThen, tOnElse);
   }

   @Override
   public Statement normalize(TypeScope scope, ConcatMap<LoopStatement, LoopStatement> modified) {
      return new IfStatement(
         this.position,
         this.condition.normalize(scope),
         this.onThen.normalize(scope, modified),
         this.onElse == null ? null : this.onElse.normalize(scope, modified)
      );
   }

   @Override
   public TypeID getReturnType() {
      TypeID thenType = this.onThen.getReturnType();
      if (this.onElse == null) {
         return thenType;
      } else {
         TypeID elseType = this.onElse.getReturnType();
         if (thenType == elseType) {
            return thenType;
         } else {
            return thenType == null ? elseType : thenType;
         }
      }
   }
}
