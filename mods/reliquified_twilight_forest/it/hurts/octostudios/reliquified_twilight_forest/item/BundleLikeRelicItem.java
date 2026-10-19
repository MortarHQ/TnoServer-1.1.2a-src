package it.hurts.octostudios.reliquified_twilight_forest.item;

import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.misc.ICreativeTabContent;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ItemLike;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class BundleLikeRelicItem extends BundleLikeItem implements ICurioItem, IRelicItem, ICreativeTabContent {
   public boolean canEquip(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public void gatherCreativeTabContent(CreativeContentConstructor creativeContentConstructor) {
      creativeContentConstructor.entry((CreativeModeTab)CreativeTabRegistry.RELICS_TAB.get(), TabVisibility.PARENT_AND_SEARCH_TABS, new ItemLike[]{this});
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }
}
