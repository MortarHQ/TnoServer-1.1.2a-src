package org.openzen.zenscript.formatter;

import org.openzen.zenscript.formattershared.FormattingSettings;

public class ScriptFormattingSettings extends FormattingSettings {
   public final boolean showAnyInFunctionHeaders;
   public final boolean useSingleQuotesForStrings;
   public final boolean spaceBeforeLabelColon;
   public final boolean spaceAfterLabelColon;
   public final boolean bracketsAroundConditions;
   public final boolean ifElseForceBrackets;
   public final boolean ifElseAvoidBrackets;
   public final boolean loopForceBrackets;
   public final boolean loopAvoidBrackets;
   public final boolean tryCatchForceBrackets;
   public final boolean tryCatchAvoidBrackets;
   public final boolean ifSingleLineOnSameLine;
   public final boolean elseSingleLineOnSameLine;
   public final boolean loopSingleLineOnSameLine;
   public final boolean ifBracketOnSameLine;
   public final boolean elseBracketOnSameLine;
   public final boolean loopBracketOnSameLine;
   public final boolean tryCatchNewLine;
   public final boolean tryCatchBracketOnSameLine;
   public final boolean classBracketOnSameLine;
   public final boolean functionBracketOnSameLine;
   public final boolean lambdaMethodOnSameLine;

   private ScriptFormattingSettings(ScriptFormattingSettings.Builder builder) {
      super(builder);
      this.showAnyInFunctionHeaders = builder.showAnyInFunctionHeaders;
      this.useSingleQuotesForStrings = builder.useSingleQuotesForStrings;
      this.spaceBeforeLabelColon = builder.spaceBeforeLabelColon;
      this.spaceAfterLabelColon = builder.spaceAfterLabelColon;
      this.bracketsAroundConditions = builder.bracketsAroundConditions;
      this.ifElseForceBrackets = builder.ifElseForceBrackets;
      this.ifElseAvoidBrackets = builder.ifElseAvoidBrackets;
      this.loopForceBrackets = builder.loopForceBrackets;
      this.loopAvoidBrackets = builder.loopAvoidBrackets;
      this.tryCatchForceBrackets = builder.tryCatchForceBrackets;
      this.tryCatchAvoidBrackets = builder.tryCatchAvoidBrackets;
      this.ifSingleLineOnSameLine = builder.ifSingleLineOnSameLine;
      this.elseSingleLineOnSameLine = builder.elseSingleLineOnSameLine;
      this.loopSingleLineOnSameLine = builder.loopSingleLineOnSameLine;
      this.ifBracketOnSameLine = builder.ifBracketOnSameLine;
      this.elseBracketOnSameLine = builder.elseBracketOnSameLine;
      this.loopBracketOnSameLine = builder.loopBracketOnSameLine;
      this.tryCatchNewLine = builder.tryCatchNewLine;
      this.tryCatchBracketOnSameLine = builder.tryCatchBracketOnSameLine;
      this.classBracketOnSameLine = builder.classBracketOnSameLine;
      this.functionBracketOnSameLine = builder.functionBracketOnSameLine;
      this.lambdaMethodOnSameLine = builder.lambdaMethodOnSameLine;
   }

   public String getSingleLineSeparator(String indent, ParentStatementType position) {
      switch (position) {
         case NONE:
            return "\n" + indent;
         case IF:
         case IF_WITH_ELSE:
            if (this.ifSingleLineOnSameLine) {
               return " ";
            }

            return "\n" + indent + this.indent;
         case ELSE:
            if (this.elseSingleLineOnSameLine) {
               return " ";
            }

            return "\n" + indent + this.indent;
         case LOOP:
            if (this.loopSingleLineOnSameLine) {
               return " ";
            }

            return "\n" + indent + this.indent;
         case TRY:
         case CATCH:
         case FINALLY:
            return "\n" + indent + this.indent;
         default:
            return "\n" + indent + this.indent;
      }
   }

   public String getBlockSeparator(String indent, ParentStatementType position) {
      switch (position) {
         case NONE:
            return "\n" + indent + "{";
         case IF:
         case IF_WITH_ELSE:
            if (this.ifBracketOnSameLine) {
               return " {";
            }

            return "\n" + indent + "{";
         case ELSE:
            if (this.elseBracketOnSameLine) {
               return " {";
            }

            return "\n" + indent + "{";
         case LOOP:
            if (this.loopBracketOnSameLine) {
               return " {";
            }

            return "\n" + indent + "{";
         case TRY:
         case CATCH:
         case FINALLY:
            if (this.tryCatchBracketOnSameLine) {
               return " {";
            }

            return "\n" + indent + "{";
         default:
            return "\n" + indent + this.indent;
      }
   }

   public static class Builder extends FormattingSettings.Builder<ScriptFormattingSettings.Builder> {
      private boolean showAnyInFunctionHeaders = false;
      private boolean useSingleQuotesForStrings = true;
      private boolean useTabs = false;
      private int spacesPerTab = 4;
      private boolean spaceBeforeLabelColon = true;
      private boolean spaceAfterLabelColon = false;
      private boolean bracketsAroundConditions = false;
      private boolean ifElseForceBrackets = false;
      private boolean ifElseAvoidBrackets = true;
      private boolean loopForceBrackets = false;
      private boolean loopAvoidBrackets = true;
      private boolean tryCatchForceBrackets = true;
      private boolean tryCatchAvoidBrackets = false;
      private boolean ifSingleLineOnSameLine = false;
      private boolean elseSingleLineOnSameLine = false;
      private boolean loopSingleLineOnSameLine = false;
      private boolean ifBracketOnSameLine = true;
      private boolean elseBracketOnSameLine = true;
      private boolean loopBracketOnSameLine = true;
      private boolean tryCatchNewLine = true;
      private boolean tryCatchBracketOnSameLine = true;
      private boolean classBracketOnSameLine = false;
      private boolean functionBracketOnSameLine = false;
      private boolean lambdaMethodOnSameLine = false;

      public Builder() {
         super(CommentFormatter::format);
         this.instance = this;
      }

      public ScriptFormattingSettings.Builder showAnyInFunctionHeaders(boolean show) {
         this.showAnyInFunctionHeaders = show;
         return this;
      }

      public ScriptFormattingSettings.Builder useSingleQuotesForStrings(boolean single) {
         this.useSingleQuotesForStrings = single;
         return this;
      }

      public ScriptFormattingSettings.Builder spaceBeforeLabelColon(boolean space) {
         this.spaceBeforeLabelColon = space;
         return this;
      }

      public ScriptFormattingSettings.Builder spaceAfterLabelColon(boolean space) {
         this.spaceAfterLabelColon = space;
         return this;
      }

      public ScriptFormattingSettings.Builder bracketsAroundConditions(boolean brackets) {
         this.bracketsAroundConditions = brackets;
         return this;
      }

      public ScriptFormattingSettings.Builder ifElseForceBrackets(boolean force) {
         this.ifElseForceBrackets = force;
         return this;
      }

      public ScriptFormattingSettings.Builder ifElseAvoidBrackets(boolean avoid) {
         this.ifElseAvoidBrackets = avoid;
         return this;
      }

      public ScriptFormattingSettings.Builder loopForceBrackets(boolean force) {
         this.loopForceBrackets = force;
         return this;
      }

      public ScriptFormattingSettings.Builder loopAvoidBrackets(boolean avoid) {
         this.loopAvoidBrackets = avoid;
         return this;
      }

      public ScriptFormattingSettings.Builder tryCatchForceBrackets(boolean force) {
         this.tryCatchForceBrackets = force;
         return this;
      }

      public ScriptFormattingSettings.Builder tryCatchAvoidBrackets(boolean avoid) {
         this.tryCatchAvoidBrackets = avoid;
         return this;
      }

      public ScriptFormattingSettings.Builder ifSingleLineOnSameLine(boolean sameLine) {
         this.ifSingleLineOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder elseSingleLineOnSameLine(boolean sameLine) {
         this.elseSingleLineOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder loopSingleLineOnSameLine(boolean sameLine) {
         this.loopSingleLineOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder ifBracketOnSameLine(boolean sameLine) {
         this.ifBracketOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder elseBracketOnSameLine(boolean sameLine) {
         this.elseBracketOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder loopBracketOnSameLine(boolean sameLine) {
         this.loopBracketOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder tryCatchNewLine(boolean newLine) {
         this.tryCatchNewLine = newLine;
         return this;
      }

      public ScriptFormattingSettings.Builder tryCatchBracketOnSameLine(boolean sameLine) {
         this.tryCatchBracketOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder classBracketOnSameLine(boolean sameLine) {
         this.classBracketOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder functionBracketOnSameLine(boolean sameLine) {
         this.functionBracketOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings.Builder lambdaMethodOnSameLine(boolean sameLine) {
         this.lambdaMethodOnSameLine = sameLine;
         return this;
      }

      public ScriptFormattingSettings build() {
         return new ScriptFormattingSettings(this);
      }
   }
}
