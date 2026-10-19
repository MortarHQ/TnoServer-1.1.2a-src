package dev.ftb.mods.ftbfiltersystem.filter;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractSmartFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.NoSuchElementException;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemFilter extends AbstractSmartFilter {
   public static final ResourceLocation ID = FTBFilterSystemAPI.rl("item");
   private final Item matchItem;

   public ItemFilter(SmartFilter.Compound parent) {
      this(parent, Items.STONE);
   }

   public ItemFilter(SmartFilter.Compound parent, Item matchItem) {
      super(parent);
      this.matchItem = matchItem;
   }

   public Item getMatchItem() {
      return this.matchItem;
   }

   public boolean test(ItemStack stack) {
      return stack.is(this.matchItem);
   }

   @Override
   public String getStringArg(Provider registryAccess) {
      return this.matchItem.arch$registryName().toString();
   }

   public static ItemFilter fromString(SmartFilter.Compound parent, String str, Provider registryAccess) {
      try {
         Reference<Item> item = ((RegistryLookup)registryAccess.lookup(Registries.ITEM).orElseThrow())
            .getOrThrow(ResourceKey.create(Registries.ITEM, ResourceLocation.tryParse(str)));
         return new ItemFilter(parent, (Item)item.value());
      } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
         throw new FilterException(e.getMessage(), e);
      }
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }
}
