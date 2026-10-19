package org.openzen.zenscript.codemodel.statement;

public interface StatementVisitor<T> {
   T visitBlock(BlockStatement var1);

   T visitBreak(BreakStatement var1);

   T visitContinue(ContinueStatement var1);

   T visitDoWhile(DoWhileStatement var1);

   T visitEmpty(EmptyStatement var1);

   T visitExpression(ExpressionStatement var1);

   T visitForeach(ForeachStatement var1);

   T visitIf(IfStatement var1);

   default T visitInvalid(InvalidStatement statement) {
      throw new UnsupportedOperationException("Invalid statement");
   }

   T visitLock(LockStatement var1);

   T visitReturn(ReturnStatement var1);

   T visitSwitch(SwitchStatement var1);

   T visitThrow(ThrowStatement var1);

   T visitTryCatch(TryCatchStatement var1);

   T visitVar(VarStatement var1);

   T visitWhile(WhileStatement var1);
}
