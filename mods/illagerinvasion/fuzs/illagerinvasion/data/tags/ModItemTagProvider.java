package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {
   public ModItemTagProvider(DataProviderContext context) {
      super(Registries.ITEM, context);
   }

   public void addTags(Provider provider) {
      this.add(ItemTags.TRIM_MATERIALS).add((Item)ModItems.PLATINUM_SHEET_ITEM.value());
      this.add(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.PLATINUM_INFUSED_HATCHET_ITEM);
   }
}
