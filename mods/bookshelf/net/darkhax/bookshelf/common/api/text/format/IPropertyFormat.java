package net.darkhax.bookshelf.common.api.text.format;

import net.darkhax.bookshelf.common.api.util.TextHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public interface IPropertyFormat {
   ResourceLocation formatKey();

   default MutableComponent format(Component property, Component value) {
      return TextHelper.fromResourceLocation("format", null, this.formatKey(), property, value);
   }
}
