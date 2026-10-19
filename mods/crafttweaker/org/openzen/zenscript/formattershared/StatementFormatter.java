package org.openzen.zenscript.formattershared;

import java.util.List;
import org.openzen.zenscript.codemodel.WhitespaceInfo;
import org.openzen.zenscript.codemodel.statement.BlockStatement;
import org.openzen.zenscript.codemodel.statement.BreakStatement;
import org.openzen.zenscript.codemodel.statement.ContinueStatement;
import org.openzen.zenscript.codemodel.statement.DoWhileStatement;
import org.openzen.zenscript.codemodel.statement.EmptyStatement;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.ForeachStatement;
import org.openzen.zenscript.codemodel.statement.IfStatement;
import org.openzen.zenscript.codemodel.statement.LockStatement;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.ReturnStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.statement.StatementVisitor;
import org.openzen.zenscript.codemodel.statement.SwitchStatement;
import org.openzen.zenscript.codemodel.statement.ThrowStatement;
import org.openzen.zenscript.codemodel.statement.TryCatchStatement;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.statement.WhileStatement;

public class StatementFormatter implements StatementVisitor<Void>, StatementFormattingTarget {
   private final StringBuilder output;
   private final StatementFormatter.Formatter formatter;
   private final String indent;
   private final FormattingSettings settings;
   private final LoopStatement innerLoop;

   public StatementFormatter(StringBuilder output, FormattingSettings settings, StatementFormatter.Formatter formatter, String indent, LoopStatement innerLoop) {
      this.output = output;
      this.formatter = formatter;
      this.indent = indent;
      this.settings = settings;
      this.innerLoop = innerLoop;
   }

   public Void visitBlock(BlockStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginBlock(whitespace);
      this.formatter.formatBlock(this, statement);
      this.endBlock(whitespace);
      return null;
   }

   public Void visitBreak(BreakStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatBreak(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitContinue(ContinueStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatContinue(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitDoWhile(DoWhileStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatDoWhile(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitEmpty(EmptyStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatEmpty(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitExpression(ExpressionStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatExpression(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitForeach(ForeachStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatForeach(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitIf(IfStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatIf(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitLock(LockStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatLock(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitReturn(ReturnStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatReturn(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitSwitch(SwitchStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatSwitch(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitThrow(ThrowStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatThrow(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitTryCatch(TryCatchStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatTryCatch(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitVar(VarStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatVar(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   public Void visitWhile(WhileStatement statement) {
      WhitespaceInfo whitespace = statement.getTag(WhitespaceInfo.class);
      this.beginSingleLine(whitespace);
      this.formatter.formatWhile(this, statement);
      this.endSingleLine(whitespace);
      return null;
   }

   @Override
   public LoopStatement getInnerLoop() {
      return this.innerLoop;
   }

   @Override
   public String getIndent() {
      return this.indent;
   }

   @Override
   public void writeLine(String line) {
      this.output.append('\n').append(this.indent).append(line);
   }

   @Override
   public void writeInner(String lineBefore, Statement contents, LoopStatement loop, String lineAfter) {
      this.output.append('\n').append(this.indent).append(lineBefore);
      contents.accept(
         new StatementFormatter(this.output, this.settings, this.formatter, this.indent + this.settings.indent, loop == null ? this.innerLoop : loop)
      );
      if (!lineAfter.isEmpty()) {
         this.output.append('\n').append(this.indent).append(lineAfter);
      }
   }

   @Override
   public void writeInner(String lineBefore, String[] inlineContents, Statement contents, LoopStatement loop, String lineAfter) {
      this.output.append('\n').append(this.indent).append(lineBefore);
      StatementFormatter innerFormatter = new StatementFormatter(
         this.output, this.settings, this.formatter.forLoop(loop), this.indent + this.settings.indent, loop == null ? this.innerLoop : loop
      );

      for (String inline : inlineContents) {
         this.output.append('\n').append(this.indent).append(this.settings.indent).append(inline);
      }

      if (contents instanceof BlockStatement) {
         for (Statement statement : ((BlockStatement)contents).statements) {
            statement.accept(innerFormatter);
         }
      } else {
         contents.accept(innerFormatter);
      }

      if (!lineAfter.isEmpty()) {
         this.output.append('\n').append(this.indent).append(lineAfter);
      }
   }

   @Override
   public void writeInnerMulti(String lineBefore, List<StatementFormattingSubBlock> contents, LoopStatement loop, String lineAfter) {
      this.output.append('\n').append(this.indent).append(lineBefore);
      String newIndent = this.indent + this.settings.indent + this.settings.indent;
      StatementFormatter inner = new StatementFormatter(this.output, this.settings, this.formatter.forLoop(loop), newIndent, this.innerLoop);

      for (StatementFormattingSubBlock subBlock : contents) {
         this.output.append('\n').append(this.indent).append(this.settings.indent).append(subBlock.header);

         for (String literal : subBlock.literalStatements) {
            this.output.append('\n').append(newIndent).append(literal);
         }

         for (Statement statement : subBlock.statements) {
            statement.accept(inner);
         }
      }

      if (!lineAfter.isEmpty()) {
         this.output.append('\n').append(this.indent).append(lineAfter);
      }
   }

   @Override
   public void writeBlock(String lineBefore, BlockStatement contents, String lineAfter) {
      this.output.append(' ').append(lineBefore);
      StatementFormatter inner = new StatementFormatter(this.output, this.settings, this.formatter, this.indent, this.innerLoop);

      for (Statement statement : contents.statements) {
         statement.accept(inner);
      }

      if (!lineAfter.isEmpty()) {
         this.output.append('\n').append(this.indent.substring(0, this.indent.length() - this.settings.indent.length())).append(lineAfter);
      }
   }

   private void beginBlock(WhitespaceInfo whitespace) {
      if (whitespace != null && whitespace.emptyLine) {
         this.output.append("\n").append(this.indent);
      }

      if (whitespace != null) {
         this.writeComments(whitespace.commentsBefore);
      }
   }

   private void endBlock(WhitespaceInfo whitespace) {
      if (whitespace != null && !whitespace.commentsAfter.isEmpty()) {
         this.output.append(' ').append(whitespace.commentsAfter);
      }
   }

   private void beginSingleLine(WhitespaceInfo whitespace) {
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
   }

   private void writeComments(String[] comments) {
      for (String comment : this.settings.commentFormatter.format(comments)) {
         this.output.append(comment).append("\n").append(this.indent);
      }
   }

   private void writePostComments(String[] comments) {
      for (String comment : this.settings.commentFormatter.format(comments)) {
         this.output.append("\n").append(this.indent).append(comment);
      }
   }

   public interface Formatter {
      StatementFormatter.Formatter forLoop(LoopStatement var1);

      void formatBlock(StatementFormattingTarget var1, BlockStatement var2);

      void formatBreak(StatementFormattingTarget var1, BreakStatement var2);

      void formatContinue(StatementFormattingTarget var1, ContinueStatement var2);

      void formatDoWhile(StatementFormattingTarget var1, DoWhileStatement var2);

      void formatEmpty(StatementFormattingTarget var1, EmptyStatement var2);

      void formatExpression(StatementFormattingTarget var1, ExpressionStatement var2);

      void formatForeach(StatementFormattingTarget var1, ForeachStatement var2);

      void formatIf(StatementFormattingTarget var1, IfStatement var2);

      void formatLock(StatementFormattingTarget var1, LockStatement var2);

      void formatReturn(StatementFormattingTarget var1, ReturnStatement var2);

      void formatSwitch(StatementFormattingTarget var1, SwitchStatement var2);

      void formatThrow(StatementFormattingTarget var1, ThrowStatement var2);

      void formatTryCatch(StatementFormattingTarget var1, TryCatchStatement var2);

      void formatVar(StatementFormattingTarget var1, VarStatement var2);

      void formatWhile(StatementFormattingTarget var1, WhileStatement var2);
   }
}
