package noobanidus.mods.lootr.common.api.data.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import noobanidus.mods.lootr.common.api.MenuBuilder;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;

public interface ILootrInventory extends Container, MenuProvider {
   ILootrInfo getInfo();

   void setInfo(ILootrSavedData var1);

   default Component getDisplayName() {
      Component name = this.getInfo().getInfoDisplayName();
      return (Component)(name == null ? Component.empty() : name);
   }

   NonNullList<ItemStack> getInventoryContents();

   void setMenuBuilder(MenuBuilder var1);

   CompoundTag saveToTag(Provider var1);
}
