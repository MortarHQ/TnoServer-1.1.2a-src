package com.blackgear.platform.client.v2.render.neoforge;

import com.blackgear.platform.client.v2.render.BuiltinItemRendererRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class BuiltinItemRendererRegistryImpl extends BuiltinItemRendererRegistry {
   public static final Map<Item, BuiltinItemRendererRegistry.DynamicItemRenderer> RENDERERS = new HashMap<>();
   private static final BuiltinItemRendererRegistry INSTANCE = new BuiltinItemRendererRegistryImpl();

   public static BuiltinItemRendererRegistry getInstance() {
      return INSTANCE;
   }

   @Override
   public void register(ItemLike item, BuiltinItemRendererRegistry.DynamicItemRenderer renderer) {
      RENDERERS.putIfAbsent(item.asItem(), renderer);
   }

   @Override
   public BuiltinItemRendererRegistry.DynamicItemRenderer get(ItemLike item) {
      return RENDERERS.get(item.asItem());
   }
}
