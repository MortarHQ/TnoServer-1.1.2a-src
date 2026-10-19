package org.openzen.zenscript.codemodel.statement;

public interface StatementVisitorWithContext<C, R> {
   R visitBlock(C var1, BlockStatement var2);

   R visitBreak(C var1, BreakStatement var2);

   R visitContinue(C var1, ContinueStatement var2);

   R visitDoWhile(C var1, DoWhileStatement var2);

   R visitEmpty(C var1, EmptyStatement var2);

   R visitExpression(C var1, ExpressionStatement var2);

   R visitForeach(C var1, ForeachStatement var2);

   R visitIf(C var1, IfStatement var2);

   default R visitInvalid(C context, InvalidStatement statement) {
      throw new UnsupportedOperationException("Invalid statement");
   }

   R visitLock(C var1, LockStatement var2);

   R visitReturn(C var1, ReturnStatement var2);

   R visitSwitch(C var1, SwitchStatement var2);

   R visitThrow(C var1, ThrowStatement var2);

   R visitTryCatch(C var1, TryCatchStatement var2);

   R visitVar(C var1, VarStatement var2);

   R visitWhile(C var1, WhileStatement var2);
}
