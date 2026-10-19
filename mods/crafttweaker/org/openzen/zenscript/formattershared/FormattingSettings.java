package org.openzen.zenscript.formattershared;

import stdlib.Chars;

public class FormattingSettings {
   public final boolean useTabs;
   public final int spacesPerTab;
   public final String indent;
   public final CommentFormatter commentFormatter;

   protected FormattingSettings(FormattingSettings.Builder builder) {
      this.useTabs = builder.useTabs;
      this.spacesPerTab = builder.spacesPerTab;
      this.commentFormatter = builder.commentFormatter;
      if (this.useTabs) {
         this.indent = "\t";
      } else {
         this.indent = Chars.times(' ', this.spacesPerTab);
      }
   }

   public static class Builder<T extends FormattingSettings.Builder<T>> {
      private final CommentFormatter commentFormatter;
      protected T instance;
      private boolean useTabs = false;
      private int spacesPerTab = 4;

      public Builder(CommentFormatter commentFormatter) {
         this.commentFormatter = commentFormatter;
      }

      public T useTabs(boolean tabs) {
         this.useTabs = tabs;
         return this.instance;
      }

      public T spacesPerTabs(int spaces) {
         this.spacesPerTab = spaces;
         return this.instance;
      }

      public FormattingSettings build() {
         return new FormattingSettings(this);
      }
   }
}
