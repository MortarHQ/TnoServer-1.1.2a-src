package net.blay09.mods.trashslot.client.deletion;

import net.blay09.mods.trashslot.client.TrashSlotSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public interface DeletionProvider {
   void undeleteLast(Player var1, TrashSlotSlot var2, boolean var3);

   boolean canUndeleteLast();

   void deleteMouseItem(Player var1, ItemStack var2, TrashSlotSlot var3, boolean var4);

   void deleteContainerItem(Player var1, AbstractContainerMenu var2, int var3, boolean var4, TrashSlotSlot var5);

   void emptyTrashSlot(Player var1, TrashSlotSlot var2);
}
