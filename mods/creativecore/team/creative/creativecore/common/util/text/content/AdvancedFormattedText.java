package team.creative.creativecore.common.util.text.content;

import java.util.Optional;
import net.minecraft.client.StringSplitter.WidthProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.FormattedText.ContentConsumer;
import net.minecraft.network.chat.FormattedText.StyledContentConsumer;

public interface AdvancedFormattedText extends FormattedText {
   default <T> Optional<T> visit(ContentConsumer<T> consumer) {
      return Optional.empty();
   }

   default <T> Optional<T> visit(StyledContentConsumer<T> consumer, Style style) {
      return Optional.empty();
   }

   int width(WidthProvider var1, Style var2);

   int height();

   void render(GuiGraphics var1, int var2);
}
