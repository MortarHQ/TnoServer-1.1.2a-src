package net.darkhax.bookshelf.common.api.registry.register;

import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public record RegisterPotPatterns(
   String owner, Map<Item, ResourceKey<DecoratedPotPattern>> itemMap, BiConsumer<ResourceLocation, ResourceKey<DecoratedPotPattern>> registerFunc
) {
   public ResourceKey<DecoratedPotPattern> add(String path) {
      return this.add(ResourceLocation.fromNamespaceAndPath(this.owner, path));
   }

   public ResourceKey<DecoratedPotPattern> add(ResourceLocation id) {
      ResourceKey<DecoratedPotPattern> key = ResourceKey.create(Registries.DECORATED_POT_PATTERN, id);
      this.registerFunc.accept(id, key);
      return key;
   }

   public void add(Item item, String path) {
      this.add(item, ResourceLocation.fromNamespaceAndPath(this.owner, path));
   }

   public void add(Item item, ResourceLocation id) {
      ResourceKey<DecoratedPotPattern> key = this.add(id);
      this.itemMap.put(item, key);
   }
}
