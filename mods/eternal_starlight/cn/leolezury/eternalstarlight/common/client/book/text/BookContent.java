package cn.leolezury.eternalstarlight.common.client.book.text;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class BookContent {
   public static final Codec<BookContent> CODEC = BookText.LIST_CODEC.xmap(BookContent::new, content -> content.texts);
   private final List<BookText> texts;
   private MutableComponent cachedText = null;

   public BookContent(List<BookText> texts) {
      this.texts = texts;
   }

   public Component toComponent() {
      if (this.cachedText == null) {
         this.cachedText = Component.empty();
         StringBuilder merged = new StringBuilder();

         for (BookText text : this.texts) {
            merged.append(text.getString());
         }

         BookText.parse(merged.toString()).forEach(component -> this.cachedText.append(component));
      }

      return this.cachedText;
   }
}
