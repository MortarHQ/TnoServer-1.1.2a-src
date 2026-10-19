package io.github.Memoires.mysticism.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MysticismItemTags {
   public static final TagKey<Item> KAMUI_PROJECTILE = modTag("projectile");

   static TagKey<Item> modTag(String name) {
      return create(ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }

   static TagKey<Item> neoforgeTag(String name) {
      return create(ResourceLocation.fromNamespaceAndPath("neoforge", name));
   }

   static TagKey<Item> vanillaTag(String name) {
      return create(ResourceLocation.withDefaultNamespace(name));
   }

   static TagKey<Item> create(ResourceLocation name) {
      return TagKey.create(Registries.ITEM, name);
   }
}
