package team.creative.creativecore.client.render.text;

import java.util.Arrays;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.StringSplitter.WidthProvider;
import net.minecraft.network.chat.Style;
import team.creative.creativecore.common.util.text.content.AdvancedContent;
import team.creative.creativecore.common.util.text.content.FormattedSingleSink;
import team.creative.creativecore.mixin.StringSplitterAccessor;

public class WidthLimitedCharSink implements FormattedSingleSink {
   private final WidthProvider widthProvider;
   private float maxWidth;
   private int position;
   private final int[] lastPositions = new int[Linebreaker.values().length];

   public WidthLimitedCharSink(float maxWidth, StringSplitter splitter) {
      this.maxWidth = maxWidth;
      this.widthProvider = ((StringSplitterAccessor)splitter).getWidthProvider();
      this.resetPosition();
   }

   @Override
   public boolean accept(int pos, Style style, int character) {
      this.maxWidth = this.maxWidth - this.widthProvider.getWidth(character, style);

      for (int i = 0; i < this.lastPositions.length; i++) {
         if (Linebreaker.values()[i].predicate.test((char)character)) {
            this.lastPositions[i] = this.position;
         }
      }

      if (this.maxWidth >= 0.0F) {
         this.position = pos + Character.charCount(character);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean accept(Style style, AdvancedContent content) {
      this.maxWidth = this.maxWidth - content.width(this.widthProvider, style);
      return this.maxWidth >= 0.0F;
   }

   public int getPosition() {
      return this.position;
   }

   public Linebreaker lastBreaker() {
      for (int i = 0; i < this.lastPositions.length; i++) {
         if (this.lastPositions[i] != -1) {
            return Linebreaker.values()[i];
         }
      }

      return null;
   }

   public int lastBreakerPos() {
      for (int i = 0; i < this.lastPositions.length; i++) {
         if (this.lastPositions[i] != -1) {
            return this.lastPositions[i];
         }
      }

      return this.position;
   }

   public void resetPosition() {
      this.position = 0;
      Arrays.fill(this.lastPositions, -1);
   }
}
