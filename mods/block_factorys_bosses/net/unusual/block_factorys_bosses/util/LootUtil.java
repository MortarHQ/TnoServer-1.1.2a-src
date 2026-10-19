package net.unusual.block_factorys_bosses.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LootUtil {
   public static void saveItemsToTag(NonNullList<ItemStack> items, RegistryAccess access, CompoundTag tag) {
      tag.putBoolean("has_saved_items", !items.isEmpty());
      if (!items.isEmpty()) {
         ListTag listtag = new ListTag();

         for (int i = 0; i < items.size(); i++) {
            ItemStack itemstack = (ItemStack)items.get(i);
            if (!itemstack.isEmpty()) {
               CompoundTag compoundTag = new CompoundTag();
               compoundTag.putByte("slot", (byte)i);
               listtag.add(itemstack.save(access, compoundTag));
            }
         }

         tag.put("saved_items", listtag);
      }
   }

   public static void loadItemsFromTag(NonNullList<ItemStack> items, RegistryAccess access, CompoundTag tag) {
      if (tag.getBoolean("has_saved_items")) {
         ListTag listtag = tag.getList("saved_items", 10);

         for (int i = 0; i < listtag.size(); i++) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("slot") & 255;
            items.set(j, ItemStack.parse(access, compoundtag).orElse(ItemStack.EMPTY));
         }
      }
   }
}
