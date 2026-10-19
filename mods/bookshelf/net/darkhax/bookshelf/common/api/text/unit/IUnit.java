package net.darkhax.bookshelf.common.api.text.unit;

import net.darkhax.bookshelf.common.api.text.format.PropertyFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public interface IUnit {
   ResourceLocation unitKey();

   default MutableComponent unitName() {
      return Component.translatable("units." + this.unitKey().getNamespace() + "." + this.unitKey().getPath());
   }

   default MutableComponent plural() {
      return Component.translatable("units.bookshelf." + this.unitKey().getNamespace() + "." + this.unitKey() + ".plural");
   }

   default MutableComponent abbreviated() {
      return Component.translatable("units." + this.unitKey().getNamespace() + "." + this.unitKey() + ".abbreviated");
   }

   default MutableComponent format(int amount) {
      return this.format(amount, PropertyFormat.LEFT);
   }

   default MutableComponent format(int amount, PropertyFormat format) {
      return format.format(amount != 1 && amount != -1 ? this.plural() : this.unitName(), Component.literal(Integer.toString(amount)));
   }
}
