package com.faboslav.variantsandventures.common.events.item;

import com.faboslav.variantsandventures.common.events.base.EventHandler;
import java.util.function.BiConsumer;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

public record RegisterItemColorEvent(BiConsumer<ItemColor, ItemLike[]> colors) {
   public static final EventHandler<RegisterItemColorEvent> EVENT = new EventHandler<>();

   public void register(ItemColor color, ItemLike... items) {
      this.colors.accept(color, items);
   }
}
