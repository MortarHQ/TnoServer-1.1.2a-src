package net.blay09.mods.trashslot;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.trashslot.config.TrashSlotConfig;
import net.blay09.mods.trashslot.tag.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TrashHelper {
   private static final String KEY = "TrashSlot";

   public static void setTrashItem(Player player, ItemStack itemStack) {
      CompoundTag entityData = Balm.getHooks().getPersistentData(player);
      entityData.put("TrashSlot", itemStack.saveOptional(player.registryAccess()));
   }

   public static boolean canDelete(ItemStack itemStack) {
      if (itemStack.is(ModItemTags.CANNOT_DELETE)) {
         return false;
      }

      ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
      return registryName == null || !TrashSlotConfig.getActive().deletionDenyList.contains(registryName.toString());
   }

   public static ItemStack getTrashItem(Player player) {
      CompoundTag entityData = Balm.getHooks().getPersistentData(player);
      CompoundTag trashSlot = entityData.getCompound("TrashSlot");
      return ItemStack.parseOptional(player.registryAccess(), trashSlot);
   }
}
