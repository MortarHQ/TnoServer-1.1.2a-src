package fzzyhmstrs.emi_loot.util;

import me.fzzyhmstrs.symbols_n_stuff.text.SymbolTextContent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class SymbolText {
   public static MutableComponent of(int key, Component text) {
      return SymbolTextContent.of(new String[]{TextKey.symbolKey(key)}).setStyle(text.getStyle()).append(" ").append(text);
   }
}
