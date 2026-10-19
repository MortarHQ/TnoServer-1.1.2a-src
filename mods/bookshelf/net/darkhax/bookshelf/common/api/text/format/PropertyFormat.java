package net.darkhax.bookshelf.common.api.text.format;

import net.darkhax.bookshelf.common.impl.Constants;
import net.minecraft.resources.ResourceLocation;

public enum PropertyFormat implements IPropertyFormat {
   RIGHT("right"),
   CENTER("center"),
   LEFT("left"),
   SPACED("spaced"),
   NONE("none");

   private final ResourceLocation formatKey;

   PropertyFormat(String key) {
      this.formatKey = Constants.id(key);
   }

   @Override
   public ResourceLocation formatKey() {
      return this.formatKey;
   }
}
