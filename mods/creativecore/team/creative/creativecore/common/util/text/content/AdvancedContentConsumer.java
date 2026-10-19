package team.creative.creativecore.common.util.text.content;

import java.util.Optional;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.FormattedText.StyledContentConsumer;

public interface AdvancedContentConsumer<T> extends StyledContentConsumer<T> {
   Optional<T> accept(Style var1, AdvancedContent var2);

   Optional<T> accept(Style var1, String var2);
}
