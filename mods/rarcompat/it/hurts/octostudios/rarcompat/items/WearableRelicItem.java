package it.hurts.octostudios.rarcompat.items;

import artifacts.registry.ModItems;
import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ItemLike;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class WearableRelicItem extends RelicItem implements IRelicItem, ICurioItem {
   public void gatherCreativeTabContent(CreativeContentConstructor constructor) {
      constructor.entry((CreativeModeTab)CreativeTabRegistry.RELICS_TAB.get(), TabVisibility.PARENT_TAB_ONLY, new ItemLike[]{this});
      constructor.entry((CreativeModeTab)ModItems.CREATIVE_TAB.get(), TabVisibility.PARENT_AND_SEARCH_TABS, new ItemLike[]{this});
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      return new ArrayList<>();
   }

   public String getConfigRoute() {
      return "rarcompat";
   }
}
