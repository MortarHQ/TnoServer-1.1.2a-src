package org.openzen.zenscript.validator.visitors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.FieldMember;
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
import org.openzen.zenscript.validator.ValidationLogEntry;
import org.openzen.zenscript.validator.Validator;
import org.openzen.zenscript.validator.analysis.ExpressionScope;
import org.openzen.zenscript.validator.analysis.StatementScope;

public class StatementValidator implements StatementVisitor<Void> {
   private final Validator validator;
   private final StatementScope scope;
   private final StatementValidator.VariableSet variables;
   public boolean constructorForwarded = false;
   private boolean firstStatement = true;

   public StatementValidator(Validator validator, StatementScope scope) {
      this(validator, scope, null);
   }

   public StatementValidator(Validator validator, StatementScope scope, StatementValidator.VariableSet variableSet) {
      this.validator = validator;
      this.scope = scope;
      this.variables = new StatementValidator.VariableSet(variableSet);
   }

   public Void visitBlock(BlockStatement block) {
      this.validateInnerBlock(block.statements);
      this.firstStatement = false;
      return null;
   }

   public Void visitBreak(BreakStatement statement) {
      this.firstStatement = false;
      return null;
   }

   public Void visitContinue(ContinueStatement statement) {
      this.firstStatement = false;
      return null;
   }

   public Void visitDoWhile(DoWhileStatement statement) {
      if (statement.condition.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_CONDITION_TYPE, statement.position, "condition must be a boolean expression");
      }

      statement.condition.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      statement.content.accept(this);
      this.firstStatement = false;
      return null;
   }

   public Void visitEmpty(EmptyStatement statement) {
      this.firstStatement = false;
      return null;
   }

   public Void visitExpression(ExpressionStatement statement) {
      statement.expression.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      this.firstStatement = false;
      return null;
   }

   public Void visitForeach(ForeachStatement statement) {
      statement.list.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      this.validateInnerBlock(
         statement.content,
         Arrays.stream(statement.loopVariables).peek(it -> this.validateUniqueVariableName(it.position, it.name)).map(it -> it.name).toArray(String[]::new)
      );
      this.firstStatement = false;
      return null;
   }

   public Void visitIf(IfStatement statement) {
      this.validateCondition(statement.condition);
      statement.onThen.accept(this);
      if (statement.onElse != null) {
         statement.onElse.accept(this);
      }

      this.firstStatement = false;
      return null;
   }

   public Void visitInvalid(InvalidStatement statement) {
      this.validator.logError(ValidationLogEntry.Code.INVALID_STATEMENT, statement.position, statement.message);
      return null;
   }

   public Void visitLock(LockStatement statement) {
      statement.object.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      statement.content.accept(this);
      this.firstStatement = false;
      return null;
   }

   public Void visitReturn(ReturnStatement statement) {
      if (this.scope.getFunctionHeader() == null) {
         this.validator.logError(ValidationLogEntry.Code.SCRIPT_CANNOT_RETURN, statement.position, "Cannot return from a script");
         return null;
      }

      if (statement.value != null) {
         statement.value.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
         if (this.scope.getFunctionHeader().getReturnType() == BasicTypeID.VOID) {
            this.validator.logError(ValidationLogEntry.Code.INVALID_RETURN_TYPE, statement.position, "Function return type is void; cannot return a value");
         } else if (!statement.value.type.equals(this.scope.getFunctionHeader().getReturnType())) {
            this.validator.logError(ValidationLogEntry.Code.INVALID_RETURN_TYPE, statement.position, "Invalid return type: " + statement.value.type);
         }
      } else if (this.scope.getFunctionHeader().getReturnType() != BasicTypeID.VOID) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_RETURN_TYPE, statement.position, "Missing return value");
      }

      this.firstStatement = false;
      return null;
   }

   public Void visitSwitch(SwitchStatement statement) {
      statement.value.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));

      for (SwitchCase switchCase : statement.cases) {
         for (Statement caseStatement : switchCase.statements) {
            caseStatement.accept(this);
         }
      }

      return null;
   }

   public Void visitThrow(ThrowStatement statement) {
      statement.value.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      this.firstStatement = false;
      return null;
   }

   public Void visitTryCatch(TryCatchStatement statement) {
      if (statement.resource != null) {
         this.validateUniqueVariableName(statement.position, statement.resource.name);
         if (statement.resource.initializer == null) {
            this.validator
               .logError(ValidationLogEntry.Code.TRY_CATCH_RESOURCE_REQUIRES_INITIALIZER, statement.position, "try with resource requires initializer");
         }
      }

      statement.content.accept(this);

      for (CatchClause catchClause : statement.catchClauses) {
         this.validateUniqueVariableName(catchClause.exceptionVariable.position, catchClause.exceptionVariable.name);
         this.validateInnerBlock(catchClause.content, catchClause.exceptionVariable.name);
      }

      this.firstStatement = false;
      return null;
   }

   public Void visitVar(VarStatement statement) {
      this.validateUniqueVariableName(statement.position, statement.name);
      this.variables.trackVariable(statement.name);
      if (statement.initializer != null) {
         statement.initializer.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      }

      this.firstStatement = false;
      return null;
   }

   public Void visitWhile(WhileStatement statement) {
      this.validateCondition(statement.condition);
      statement.content.accept(this);
      this.firstStatement = false;
      return null;
   }

   private void validateCondition(Expression condition) {
      condition.accept(new ExpressionValidator(this.validator, new StatementValidator.StatementExpressionScope()));
      if (condition.type != BasicTypeID.BOOL) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_CONDITION_TYPE, condition.position, "condition must be a boolean expression");
      }
   }

   private void validateInnerBlock(Statement statement, String... variablesToTrack) {
      this.validateInnerBlock(new Statement[]{statement}, variablesToTrack);
   }

   private void validateInnerBlock(Statement[] statements, String... variablesToTrack) {
      StatementValidator innerValidator = new StatementValidator(this.validator, this.scope, this.variables);
      Arrays.stream(variablesToTrack).forEach(innerValidator.variables::trackVariable);
      Arrays.stream(statements).forEach(it -> it.accept(innerValidator));
      this.constructorForwarded = this.constructorForwarded | innerValidator.constructorForwarded;
   }

   private void validateUniqueVariableName(CodePosition pos, String varName) {
      if (this.variables.hasVariable(varName)) {
         this.validator.logError(ValidationLogEntry.Code.DUPLICATE_VARIABLE_NAME, pos, "Duplicate variable name: " + varName);
      }
   }

   private class StatementExpressionScope implements ExpressionScope {
      @Override
      public boolean isConstructor() {
         return StatementValidator.this.scope.isConstructor();
      }

      @Override
      public boolean isFirstStatement() {
         return StatementValidator.this.firstStatement;
      }

      @Override
      public boolean hasThis() {
         return !StatementValidator.this.scope.isStatic();
      }

      @Override
      public boolean isFieldInitialized(FieldMember field) {
         return true;
      }

      @Override
      public void markConstructorForwarded() {
         StatementValidator.this.constructorForwarded = true;
      }

      @Override
      public boolean isEnumConstantInitialized(EnumConstantMember member) {
         return true;
      }

      @Override
      public boolean isLocalVariableInitialized(VarStatement variable) {
         return true;
      }

      @Override
      public boolean isStaticInitializer() {
         return StatementValidator.this.scope.isStaticInitializer();
      }

      @Override
      public HighLevelDefinition getDefinition() {
         return StatementValidator.this.scope.getDefinition();
      }

      @Override
      public AccessScope getAccessScope() {
         return StatementValidator.this.scope.getAccessScope();
      }
   }

   private static final class VariableSet {
      private final StatementValidator.VariableSet parent;
      private final Set<String> currentScope;

      private VariableSet(StatementValidator.VariableSet parent) {
         this.parent = parent;
         this.currentScope = new HashSet<>();
      }

      public void trackVariable(String variable) {
         this.currentScope.add(variable);
      }

      public boolean hasVariable(String variable) {
         return this.currentScope.contains(variable) || this.parent != null && this.parent.hasVariable(variable);
      }
   }
}
