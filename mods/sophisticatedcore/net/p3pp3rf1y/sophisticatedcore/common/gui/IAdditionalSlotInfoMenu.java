package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.util.Map;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public interface IAdditionalSlotInfoMenu {
   void updateAdditionalSlotInfo(Set<Integer> var1, Map<Integer, Integer> var2, Set<Integer> var3, Map<Integer, Holder<Item>> var4);

   void updateEmptySlotIcons(Map<ResourceLocation, Set<Integer>> var1);
}
