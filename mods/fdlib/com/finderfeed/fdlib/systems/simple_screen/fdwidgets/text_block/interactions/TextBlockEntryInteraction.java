package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions;

public class TextBlockEntryInteraction {
   private TextBlockClick onClick;
   private TextBlockScroll onScroll;
   private TextBlockHover onHover;

   public TextBlockEntryInteraction(TextBlockClick onClick, TextBlockScroll onScroll, TextBlockHover onHover) {
      this.onClick = onClick;
      this.onScroll = onScroll;
      this.onHover = onHover;
   }

   public static TextBlockEntryInteraction click(TextBlockClick onClick) {
      return new TextBlockEntryInteraction(onClick, null, null);
   }

   public static TextBlockEntryInteraction hoverOver(TextBlockHover onHover) {
      return new TextBlockEntryInteraction(null, null, onHover);
   }

   public static TextBlockEntryInteraction scroll(TextBlockScroll scroll) {
      return new TextBlockEntryInteraction(null, scroll, null);
   }

   public static TextBlockEntryInteraction empty() {
      return new TextBlockEntryInteraction(null, null, null);
   }

   public TextBlockClick getOnClick() {
      return this.onClick;
   }

   public TextBlockHover getOnHover() {
      return this.onHover;
   }

   public TextBlockScroll getOnScroll() {
      return this.onScroll;
   }

   public boolean isClick() {
      return this.onClick != null;
   }

   public boolean isHoverOver() {
      return this.onHover != null;
   }

   public boolean isScroll() {
      return this.onScroll != null;
   }
}
