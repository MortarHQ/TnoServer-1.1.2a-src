package org.openzen.zenscript.formatter;

import org.openzen.zenscript.codemodel.WhitespaceInfo;
import org.openzen.zenscript.codemodel.WhitespacePostComment;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.statement.BlockStatement;
import org.openzen.zenscript.codemodel.statement.BreakStatement;
import org.openzen.zenscript.codemodel.statement.CatchClause;
import org.openzen.zenscript.codemodel.statement.ContinueStatement;
import org.openzen.zenscript.codemodel.statement.DoWhileStatement;
import org.openzen.zenscript.codemodel.statement.EmptyStatement;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.ForeachStatement;
import org.openzen.zenscript.codemodel.statement.IfStatement;
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

public class StatementFormatter implements StatementVisitor<Void> {
   private final ScriptFormattingSettings settings;
   private final StringBuilder output;
   private final ExpressionFormatter expressionFormatter;
   private String indent;
   private ParentStatementType position = ParentStatementType.NONE;

   public StatementFormatter(StringBuilder output, String indent, ScriptFormattingSettings settings, ExpressionFormatter expressionFormatter) {
      this.output = output;
      this.indent = indent;
      this.settings = settings;
      this.expressionFormatter = expressionFormatter;
   }

   @Override
   public String toString() {
      return this.output.toString();
   }

   public Void visitBlock(BlockStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginBlock(whitespace);
      String oldIndent = this.indent;
      this.indent = oldIndent + this.settings.indent;

      for (Statement subStatement : statement.statements) {
         this.format(ParentStatementType.NONE, subStatement);
      }

      WhitespacePostComment postComment = statement.getTag(WhitespacePostComment.class);
      if (postComment != null) {
         this.writePostComments(postComment.comments);
      }

      this.indent = oldIndent;
      this.endBlock(whitespace);
      return null;
   }

   public Void visitBreak(BreakStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("break");
      if (statement.target.label != null) {
         this.output.append(' ').append(statement.target.label);
      }

      this.output.append(";");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitContinue(ContinueStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("continue");
      if (statement.target.label != null) {
         this.output.append(' ').append(statement.target.label);
      }

      this.output.append(";");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitDoWhile(DoWhileStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("do");
      if (statement.label != null) {
         if (this.settings.spaceBeforeLabelColon) {
            this.output.append(' ');
         }

         this.output.append(':');
         if (this.settings.spaceAfterLabelColon) {
            this.output.append(' ');
         }

         this.output.append(statement.label);
      }

      this.format(ParentStatementType.LOOP, statement.content);
      this.output.append(" while ");
      this.appendCondition(statement.condition);
      this.output.append(";");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitEmpty(EmptyStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append(";\n");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitExpression(ExpressionStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      String value = statement.expression.accept(this.expressionFormatter).value;
      this.output.append(value).append(";");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitForeach(ForeachStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("for ");

      for (int i = 0; i < statement.loopVariables.length; i++) {
         if (i > 0) {
            this.output.append(", ");
         }

         this.output.append(statement.loopVariables[i].name);
      }

      this.output.append(" in ");
      this.output.append(statement.list.accept(this.expressionFormatter).value);
      this.format(ParentStatementType.LOOP, statement.content);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitIf(IfStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      ParentStatementType position = this.position;
      this.beginSingleLine(whitespace);
      this.output.append("if ");
      this.appendCondition(statement.condition);
      this.format(statement.onElse == null ? ParentStatementType.IF : ParentStatementType.IF_WITH_ELSE, statement.onThen);
      if (statement.onElse != null) {
         this.output.append("else");
         this.format(ParentStatementType.ELSE, statement.onElse);
      }

      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitLock(LockStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("lock ");
      this.output.append(statement.object.accept(this.expressionFormatter).value);
      statement.content.accept(this);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitReturn(ReturnStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("return");
      if (statement.value != null) {
         this.output.append(' ');
         this.output.append(statement.value.accept(this.expressionFormatter).value);
      }

      this.output.append(";");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitSwitch(SwitchStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("switch ");
      if (statement.label != null) {
         this.output.append(':').append(statement.label);
      }

      this.output.append(this.settings.getBlockSeparator(this.indent, this.position));
      StatementFormatter innerFormatter = new StatementFormatter(
         this.output, this.indent + this.settings.indent + this.settings.indent, this.settings, this.expressionFormatter
      );

      for (SwitchCase switchCase : statement.cases) {
         if (switchCase.value == null) {
            this.output.append(this.indent).append(this.settings.indent).append("default:\n");
         } else {
            this.output
               .append(this.indent)
               .append(this.settings.indent)
               .append("case ")
               .append(switchCase.value.accept(new SwitchValueFormatter(this.settings)))
               .append(":\n");
         }

         for (Statement s : switchCase.statements) {
            s.accept(innerFormatter);
         }
      }

      this.output.append("\n").append(this.indent).append("}");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitThrow(ThrowStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("throw ").append(statement.value.accept(this.expressionFormatter));
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitTryCatch(TryCatchStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("try");
      if (statement.resource != null) {
         this.output.append(' ').append(statement.resource.name);
         this.output.append(" = ");
         this.output.append(statement.resource.initializer.accept(this.expressionFormatter).value);
      }

      this.format(ParentStatementType.TRY, statement.content);

      for (CatchClause catchClause : statement.catchClauses) {
         this.output.append(this.indent).append("catch ");
         if (catchClause.exceptionVariable != null) {
            this.output
               .append(catchClause.exceptionVariable.name)
               .append(" as ")
               .append(this.expressionFormatter.typeFormatter.format(catchClause.exceptionVariable.type));
         }

         this.format(ParentStatementType.CATCH, catchClause.content);
      }

      if (statement.finallyClause != null) {
         this.output.append(this.indent).append("finally ");
         this.format(ParentStatementType.FINALLY, statement.finallyClause);
      }

      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitVar(VarStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append(statement.isFinal ? "val " : "var ");
      this.output.append(statement.name);
      if (statement.initializer == null || statement.initializer.type != statement.type) {
         this.output.append(" as ");
         this.output.append(this.expressionFormatter.typeFormatter.format(statement.type));
      }

      if (statement.initializer != null) {
         this.output.append(" = ");
         String value = statement.initializer.accept(this.expressionFormatter).value;
         this.output.append(value);
      }

      this.output.append(";");
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitWhile(WhileStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.output.append("while");
      if (statement.label != null) {
         if (this.settings.spaceBeforeLabelColon) {
            this.output.append(' ');
         }

         this.output.append(':');
         if (this.settings.spaceAfterLabelColon) {
            this.output.append(' ');
         }

         this.output.append(statement.label);
      }

      this.output.append(' ');
      this.appendCondition(statement.condition);
      this.format(ParentStatementType.LOOP, statement.content);
      this.endSingleLine(whitespace);
      return null;
   }

   private void format(ParentStatementType position, Statement statement) {
      ParentStatementType oldPosition = this.position;
      this.position = position;
      statement.accept(this);
      this.position = oldPosition;
   }

   private void appendCondition(Expression condition) {
      if (this.settings.bracketsAroundConditions) {
         this.output.append('(');
      }

      this.output.append(condition.accept(this.expressionFormatter).value);
      if (this.settings.bracketsAroundConditions) {
         this.output.append(')');
      }
   }

   private void beginBlock(WhitespaceInfo whitespace) {
      if (whitespace != null && whitespace.emptyLine) {
         this.output.append("\n").append(this.indent);
      }

      String separator = this.settings.getBlockSeparator(this.indent, this.position);
      this.output.append(separator);
      if (whitespace != null) {
         this.writeComments(whitespace.commentsBefore);
      }
   }

   private void endBlock(WhitespaceInfo whitespace) {
      if (whitespace != null && !whitespace.commentsAfter.isEmpty()) {
         this.output.append(' ').append(whitespace.commentsAfter);
      }

      this.output.append("\n").append(this.indent).append("}");
      if (this.position == ParentStatementType.IF_WITH_ELSE) {
         if (this.settings.elseBracketOnSameLine) {
            this.output.append(" ");
         } else {
            this.output.append("\n").append(this.indent);
         }
      }
   }

   private void beginSingleLine(WhitespaceInfo whitespace) {
      String separator = this.settings.getSingleLineSeparator(this.indent, this.position);
      this.output.append(separator);
      if (whitespace != null) {
         if (whitespace.emptyLine) {
            this.output.append("\n").append(this.indent);
         }

         this.writeComments(whitespace.commentsBefore);
      }
   }

   private void endSingleLine(WhitespaceInfo whitespace) {
      if (whitespace != null && !whitespace.commentsAfter.isEmpty()) {
         this.output.append(' ').append(whitespace.commentsAfter);
      }

      if (this.position == ParentStatementType.IF_WITH_ELSE) {
         this.output.append("\n").append(this.indent);
      }
   }

   private void writeComments(String[] comments) {
      for (String comment : CommentFormatter.format(comments)) {
         this.output.append(comment).append("\n").append(this.indent);
      }
   }

   private void writePostComments(String[] comments) {
      for (String comment : CommentFormatter.format(comments)) {
         this.output.append("\n").append(this.indent).append(comment);
      }
   }
}
