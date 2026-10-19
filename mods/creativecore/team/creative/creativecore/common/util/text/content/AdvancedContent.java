package team.creative.creativecore.common.util.text.content;

import java.util.Optional;
import net.minecraft.client.StringSplitter.WidthProvider;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

public interface AdvancedContent extends ComponentContents {
   default <T> Optional<T> visit(AdvancedContentConsumer<T> consumer, Style style) {
      return consumer.accept(style, this);
   }

   int width(WidthProvider var1, Style var2);

   int height();

   FormattedText asText();
}
