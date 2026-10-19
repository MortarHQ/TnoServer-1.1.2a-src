package team.creative.creativecore.common.util.text.content;

import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSink;

public interface FormattedSingleSink extends FormattedCharSink {
   boolean accept(int var1, Style var2, int var3);

   boolean accept(Style var1, AdvancedContent var2);
}
