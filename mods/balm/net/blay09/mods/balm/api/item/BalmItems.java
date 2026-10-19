package net.blay09.mods.balm.api.item;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import net.blay09.mods.balm.api.DeferredObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public interface BalmItems {
   static Properties itemProperties(ResourceLocation identifier) {
      return new Properties();
   }

   static ResourceKey<Item> itemId(ResourceLocation identifier) {
      return ResourceKey.create(Registries.ITEM, identifier);
   }

   static BlockItem blockItem(Block block, ResourceLocation identifier) {
      return new BlockItem(block, itemProperties(identifier));
   }

   default DeferredObject<Item> registerItem(Function<ResourceLocation, Item> supplier, ResourceLocation identifier) {
      return this.registerItem(supplier, identifier, identifier.withPath(identifier.getNamespace()));
   }

   DeferredObject<Item> registerItem(Function<ResourceLocation, Item> var1, ResourceLocation var2, @Nullable ResourceLocation var3);

   DeferredObject<CreativeModeTab> registerCreativeModeTab(Supplier<ItemStack> var1, ResourceLocation var2);

   void addToCreativeModeTab(ResourceLocation var1, Supplier<ItemLike[]> var2);

   void setCreativeModeTabSorting(ResourceLocation var1, Comparator<ItemLike> var2);

   @Deprecated(forRemoval = true, since = "1.21.5")
   default Properties itemProperties() {
      return new Properties();
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default DeferredObject<Item> registerItem(Supplier<Item> supplier, ResourceLocation identifier) {
      return this.registerItem(supplier, identifier, identifier.withPath(identifier.getNamespace()));
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default DeferredObject<Item> registerItem(Supplier<Item> supplier, ResourceLocation identifier, @Nullable ResourceLocation creativeTab) {
      return this.registerItem(id -> supplier.get(), identifier, creativeTab);
   }

   BalmItems scoped(String var1);
}
