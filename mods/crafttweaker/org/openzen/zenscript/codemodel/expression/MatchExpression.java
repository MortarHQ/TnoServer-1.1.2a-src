package org.openzen.zenscript.codemodel.expression;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.expression.switchvalue.SwitchValue;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.statement.BreakStatement;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.statement.SwitchCase;
import org.openzen.zenscript.codemodel.statement.SwitchStatement;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.statement.VariableID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class MatchExpression extends Expression {
   public final Expression value;
   public final MatchExpression.Case[] cases;

   public MatchExpression(CodePosition position, Expression value, TypeID type, MatchExpression.Case[] cases) {
      super(position, type, binaryThrow(position, value.thrownType, getThrownType(position, cases)));
      this.value = value;
      this.cases = cases;
   }

   private static TypeID getThrownType(CodePosition position, MatchExpression.Case[] cases) {
      if (cases.length == 0) {
         return null;
      }

      TypeID result = cases[0].value.thrownType;

      for (int i = 1; i < cases.length; i++) {
         result = binaryThrow(position, result, cases[i].value.thrownType);
      }

      return result;
   }

   @Override
   public <T> T accept(ExpressionVisitor<T> visitor) {
      return visitor.visitMatch(this);
   }

   @Override
   public <C, R> R accept(C context, ExpressionVisitorWithContext<C, R> visitor) {
      return visitor.visitMatch(context, this);
   }

   @Override
   public Expression transform(ExpressionTransformer transformer) {
      Expression tValue = this.value.transform(transformer);
      MatchExpression.Case[] tCases = new MatchExpression.Case[this.cases.length];
      boolean unmodified = true;

      for (int i = 0; i < tCases.length; i++) {
         tCases[i] = this.cases[i].transform(transformer);
         unmodified &= tCases[i] == this.cases[i];
      }

      return unmodified && tValue == this.value ? this : new MatchExpression(this.position, tValue, this.type, tCases);
   }

   @Override
   public Expression normalize(TypeScope scope) {
      MatchExpression.Case[] normalizedCases = new MatchExpression.Case[this.cases.length];

      for (int i = 0; i < this.cases.length; i++) {
         normalizedCases[i] = this.cases[i].normalize(scope);
      }

      return new MatchExpression(this.position, this.value.normalize(scope), this.type, normalizedCases);
   }

   public MatchExpression.SwitchedMatch convertToSwitch(String tempVariable) {
      VarStatement result = new VarStatement(this.position, new VariableID(), tempVariable, this.type, null, false);
      SwitchStatement switchStatement = new SwitchStatement(this.position, null, this.value);
      boolean hasDefault = false;

      for (MatchExpression.Case matchCase : this.cases) {
         boolean reachable = true;
         Expression caseExpression;
         if (!(matchCase.value instanceof ThrowExpression) && !(matchCase.value instanceof PanicExpression)) {
            caseExpression = new SetLocalVariableExpression(matchCase.value.position, result, matchCase.value);
         } else {
            caseExpression = matchCase.value;
            reachable = false;
         }

         List<Statement> statements = new ArrayList<>();
         statements.add(new ExpressionStatement(matchCase.value.position, caseExpression));
         if (reachable) {
            statements.add(new BreakStatement(matchCase.value.position, switchStatement));
         }

         SwitchCase switchCase = new SwitchCase(matchCase.key, statements.toArray(new Statement[statements.size()]));
         switchStatement.cases.add(switchCase);
         if (matchCase.key == null) {
            hasDefault = true;
         }
      }

      if (!hasDefault) {
         Statement defaultCase = new ExpressionStatement(
            this.position, new PanicExpression(this.position, BasicTypeID.VOID, new ConstantStringExpression(this.position, "Missing case"))
         );
         switchStatement.cases.add(new SwitchCase(null, new Statement[]{defaultCase}));
      }

      return new MatchExpression.SwitchedMatch(result, switchStatement);
   }

   public static class Case {
      public final SwitchValue key;
      public final Expression value;

      public Case(SwitchValue key, Expression value) {
         this.key = key;
         this.value = value;
      }

      public MatchExpression.Case transform(ExpressionTransformer transformer) {
         Expression tValue = this.value.transform(transformer);
         return tValue == this.value ? this : new MatchExpression.Case(this.key, tValue);
      }

      public MatchExpression.Case normalize(TypeScope scope) {
         return new MatchExpression.Case(this.key, this.value.normalize(scope));
      }
   }

   public static class SwitchedMatch {
      public final VarStatement result;
      public final SwitchStatement switchStatement;

      public SwitchedMatch(VarStatement temp, SwitchStatement switchStatement) {
         this.result = temp;
         this.switchStatement = switchStatement;
      }
   }
}
