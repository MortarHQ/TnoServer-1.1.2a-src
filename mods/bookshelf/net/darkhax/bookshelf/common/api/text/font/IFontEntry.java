package net.darkhax.bookshelf.common.api.text.font;

import net.darkhax.bookshelf.common.api.util.TextHelper;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public interface IFontEntry {
   ResourceLocation identifier();

   default MutableComponent displayName() {
      return TextHelper.fromResourceLocation("font", null, this.identifier());
   }

   default MutableComponent description() {
      return TextHelper.fromResourceLocation("font", "desc", this.identifier());
   }

   default MutableComponent preview() {
      return TextHelper.fromResourceLocation("font", "preview", this.identifier());
   }
}
