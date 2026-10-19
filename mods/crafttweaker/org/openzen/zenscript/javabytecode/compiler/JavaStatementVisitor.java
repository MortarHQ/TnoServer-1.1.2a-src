package org.openzen.zenscript.javabytecode.compiler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.openzen.zenscript.codemodel.statement.BlockStatement;
import org.openzen.zenscript.codemodel.statement.BreakStatement;
import org.openzen.zenscript.codemodel.statement.CatchClause;
import org.openzen.zenscript.codemodel.statement.ContinueStatement;
import org.openzen.zenscript.codemodel.statement.DoWhileStatement;
import org.openzen.zenscript.codemodel.statement.EmptyStatement;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.ForeachStatement;
import org.openzen.zenscript.codemodel.statement.IfStatement;
import org.openzen.zenscript.codemodel.statement.InvalidStatement;
import org.openzen.zenscript.codemodel.statement.LockStatement;
import org.openzen.zenscript.codemodel.statement.ReturnStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.statement.StatementVisitor;
import org.openzen.zenscript.codemodel.statement.SwitchCase;
import org.openzen.zenscript.codemodel.statement.SwitchStatement;
import org.openzen.zenscript.codemodel.statement.ThrowStatement;
import org.openzen.zenscript.codemodel.statement.TryCatchStatement;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.statement.WhileStatement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javabytecode.JavaLocalVariableInfo;
import org.openzen.zenscript.javashared.JavaCompiledModule;

public class JavaStatementVisitor implements StatementVisitor<Boolean> {
   public final JavaExpressionVisitor expressionVisitor;
   public final JavaNonPushingExpressionVisitor nonPushingExpressionVisitor;
   final JavaBytecodeContext context;
   private final JavaWriter javaWriter;

   public JavaStatementVisitor(JavaBytecodeContext context, JavaCompiledModule module, JavaWriter javaWriter) {
      this.javaWriter = javaWriter;
      this.context = context;
      this.expressionVisitor = new JavaExpressionVisitor(context, module, javaWriter);
      this.nonPushingExpressionVisitor = new JavaNonPushingExpressionVisitor(context, module, javaWriter, this.expressionVisitor);
   }

   public JavaStatementVisitor(JavaBytecodeContext context, JavaExpressionVisitor expressionVisitor) {
      this.javaWriter = expressionVisitor.getJavaWriter();
      this.context = context;
      this.expressionVisitor = expressionVisitor;
      this.nonPushingExpressionVisitor = new JavaNonPushingExpressionVisitor(
         expressionVisitor.context, expressionVisitor.module, expressionVisitor.javaWriter, expressionVisitor
      );
   }

   public Boolean visitBlock(BlockStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      Boolean returns = false;

      for (Statement statement1 : statement.statements) {
         returns = statement1.accept(this);
      }

      return returns;
   }

   public Boolean visitBreak(BreakStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      this.javaWriter.goTo(this.javaWriter.getNamedLabel(statement.target.label + "_end"));
      return false;
   }

   public Boolean visitContinue(ContinueStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      this.javaWriter.goTo(this.javaWriter.getNamedLabel(statement.target.label + "_start"));
      return false;
   }

   public Boolean visitDoWhile(DoWhileStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      Label start = new Label();
      Label end = new Label();
      if (statement.label == null) {
         statement.label = this.javaWriter.createLabelName() + "DoWhile";
      }

      this.javaWriter.putNamedLabel(start, statement.label + "_start");
      this.javaWriter.putNamedLabel(end, statement.label + "_end");
      this.javaWriter.label(start);
      statement.content.accept(this);
      statement.condition.accept(this.expressionVisitor);
      this.javaWriter.ifNE(start);
      this.javaWriter.label(end);
      return false;
   }

   public Boolean visitEmpty(EmptyStatement statement) {
      return false;
   }

   public Boolean visitExpression(ExpressionStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      statement.expression.accept(this.nonPushingExpressionVisitor);
      return false;
   }

   public Boolean visitForeach(ForeachStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      Label start = new Label();
      Label end = new Label();
      if (statement.label == null) {
         statement.label = this.javaWriter.createLabelName() + "ForEach";
      }

      this.javaWriter.putNamedLabel(start, statement.label + "_start");
      this.javaWriter.putNamedLabel(end, statement.label + "_end");
      statement.list.accept(this.expressionVisitor);

      for (VarStatement variable : statement.loopVariables) {
         Type type = this.context.getType(variable.type);
         Label variableStart = new Label();
         JavaLocalVariableInfo info = new JavaLocalVariableInfo(type, this.javaWriter.local(type), variableStart, variable.name);
         info.end = end;
         this.javaWriter.setLocalVariable(variable.variable, info);
         this.javaWriter.addVariableInfo(info);
      }

      JavaForeachWriter iteratorWriter = new JavaForeachWriter(this, statement, start, end);
      if (statement.iterator.target.getBuiltin() == null) {
         iteratorWriter.visitCustomIterator();
      } else {
         switch (statement.iterator.target.getBuiltin()) {
            case ITERATOR_INT_RANGE:
               iteratorWriter.visitIntRange((RangeTypeID)statement.iterator.getOwnerType());
               break;
            case ITERATOR_ARRAY_VALUES:
               iteratorWriter.visitArrayValueIterator();
               break;
            case ITERATOR_ARRAY_KEY_VALUES:
               iteratorWriter.visitArrayKeyValueIterator();
               break;
            case ITERATOR_ASSOC_KEYS:
               iteratorWriter.visitAssocKeyIterator();
               break;
            case ITERATOR_ASSOC_KEY_VALUES:
               iteratorWriter.visitAssocKeyValueIterator();
               break;
            case ITERATOR_STRING_CHARS:
               iteratorWriter.visitStringCharacterIterator();
               break;
            case ITERATOR_ITERABLE:
               iteratorWriter.visitIteratorIterator(this.context.getType(statement.loopVariables[0].type));
               break;
            default:
               throw new IllegalArgumentException("Invalid iterator: " + statement.iterator.target.getBuiltin());
         }
      }

      this.javaWriter.goTo(start);
      this.javaWriter.label(end);
      this.javaWriter.pop();
      return false;
   }

   public Boolean visitIf(IfStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      statement.condition.accept(this.expressionVisitor);
      Label onElse = null;
      Label end = new Label();
      boolean hasElse = statement.onElse != null;
      if (hasElse) {
         onElse = new Label();
         this.javaWriter.ifEQ(onElse);
      } else {
         this.javaWriter.ifEQ(end);
      }

      statement.onThen.accept(this);
      if (hasElse) {
         this.javaWriter.goTo(end);
         this.javaWriter.label(onElse);
         statement.onElse.accept(this);
      }

      this.javaWriter.label(end);
      return false;
   }

   public Boolean visitLock(LockStatement statement) {
      return false;
   }

   public Boolean visitInvalid(InvalidStatement statement) {
      throw new UnsupportedOperationException("Invalid Statement: " + statement.message);
   }

   public Boolean visitReturn(ReturnStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      if (statement.value == null) {
         this.javaWriter.ret();
      } else {
         statement.value.accept(this.expressionVisitor);
         this.javaWriter.returnType(this.context.getType(statement.value.type));
      }

      return true;
   }

   public Boolean visitSwitch(SwitchStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      Label start = new Label();
      Label end = new Label();
      if (statement.label == null) {
         statement.label = this.javaWriter.createLabelName() + "Switch";
      }

      this.javaWriter.putNamedLabel(start, statement.label + "_start");
      this.javaWriter.putNamedLabel(end, statement.label + "_end");
      this.javaWriter.label(start);
      statement.value.accept(this.expressionVisitor);
      if (statement.value.type == BasicTypeID.STRING) {
         this.javaWriter.invokeVirtual(JavaExpressionVisitor.OBJECT_HASHCODE);
      }

      if (statement.value.type.isEnum()) {
         this.javaWriter.invokeVirtual(JavaExpressionVisitor.ENUM_ORDINAL);
      }

      boolean out = false;
      boolean hasNoDefault = this.hasNoDefault(statement);
      List<SwitchCase> cases = statement.cases;
      JavaSwitchLabel[] switchLabels = new JavaSwitchLabel[hasNoDefault ? cases.size() : cases.size() - 1];
      Label defaultLabel = new Label();
      int i = 0;

      for (SwitchCase switchCase : cases) {
         if (switchCase.value != null) {
            switchLabels[i++] = new JavaSwitchLabel(CompilerUtils.getKeyForSwitch(switchCase.value), new Label());
         }
      }

      JavaSwitchLabel[] sortedSwitchLabels = Arrays.copyOf(switchLabels, switchLabels.length);
      Arrays.sort(sortedSwitchLabels, Comparator.comparingInt(a -> a.key));
      this.javaWriter.lookupSwitch(defaultLabel, sortedSwitchLabels);
      i = 0;

      for (SwitchCase switchCase : cases) {
         if (!hasNoDefault && switchCase.value == null) {
            this.javaWriter.label(defaultLabel);
         } else {
            this.javaWriter.label(switchLabels[i++].label);
         }

         for (Statement statement1 : switchCase.statements) {
            out |= statement1.accept(this);
         }
      }

      if (hasNoDefault) {
         this.javaWriter.label(defaultLabel);
      }

      this.javaWriter.label(end);
      return out;
   }

   private boolean hasNoDefault(SwitchStatement switchStatement) {
      for (SwitchCase switchCase : switchStatement.cases) {
         if (switchCase.value == null) {
            return false;
         }
      }

      return true;
   }

   public Boolean visitThrow(ThrowStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      statement.value.accept(this.expressionVisitor);
      this.javaWriter.aThrow();
      return false;
   }

   public Boolean visitTryCatch(TryCatchStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      Label tryCatchStart = new Label();
      Label tryFinish = new Label();
      Label tryCatchFinish = new Label();
      Label finallyStart = new Label();
      this.javaWriter.label(tryCatchStart);
      statement.content.accept(this);
      this.javaWriter.label(tryFinish);
      if (statement.finallyClause != null) {
         statement.finallyClause.accept(this);
      }

      this.javaWriter.goTo(tryCatchFinish);

      for (CatchClause catchClause : statement.catchClauses) {
         Label catchStart = new Label();
         this.javaWriter.label(catchStart);
         catchClause.exceptionVariable.accept(this);
         JavaLocalVariableInfo localVariable = this.javaWriter.getLocalVariable(catchClause.exceptionVariable.variable);
         this.javaWriter.store(localVariable.type, localVariable.local);
         catchClause.content.accept(this);
         Label catchFinish = new Label();
         this.javaWriter.label(catchFinish);
         if (statement.finallyClause != null) {
            statement.finallyClause.accept(this);
            this.javaWriter.tryCatch(catchStart, catchFinish, finallyStart, null);
         }

         this.javaWriter.tryCatch(tryCatchStart, tryFinish, catchStart, localVariable.type.getInternalName());
         this.javaWriter.goTo(tryCatchFinish);
      }

      if (statement.finallyClause != null) {
         this.javaWriter.label(finallyStart);
         int local = this.javaWriter.local(Object.class);
         this.javaWriter.storeObject(local);
         statement.finallyClause.accept(this);
         this.javaWriter.loadObject(local);
         this.javaWriter.aThrow();
         this.javaWriter.tryCatch(tryCatchStart, tryFinish, finallyStart, null);
      }

      this.javaWriter.label(tryCatchFinish);
      return false;
   }

   public Boolean visitVar(VarStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      if (statement.initializer != null) {
         statement.initializer.accept(this.expressionVisitor);
      }

      Type type = this.context.getType(statement.type);
      int local = this.javaWriter.local(type);
      if (statement.initializer != null) {
         this.javaWriter.store(type, local);
      }

      Label variableStart = new Label();
      this.javaWriter.label(variableStart);
      JavaLocalVariableInfo info = new JavaLocalVariableInfo(type, local, variableStart, statement.name);
      this.javaWriter.setLocalVariable(statement.variable, info);
      this.javaWriter.addVariableInfo(info);
      return false;
   }

   public Boolean visitWhile(WhileStatement statement) {
      this.javaWriter.position(statement.position.fromLine);
      Label start = new Label();
      Label end = new Label();
      if (statement.label == null) {
         statement.label = this.javaWriter.createLabelName() + "WhileDo";
      }

      this.javaWriter.putNamedLabel(start, statement.label + "_start");
      this.javaWriter.putNamedLabel(end, statement.label + "_end");
      this.javaWriter.label(start);
      statement.condition.accept(this.expressionVisitor);
      this.javaWriter.ifEQ(end);
      statement.content.accept(this);
      this.javaWriter.goTo(start);
      this.javaWriter.label(end);
      return false;
   }

   public void start() {
      this.javaWriter.start();
   }

   public void end() {
      this.javaWriter.ret();
      this.javaWriter.end();
   }

   public JavaWriter getJavaWriter() {
      return this.javaWriter;
   }
}
