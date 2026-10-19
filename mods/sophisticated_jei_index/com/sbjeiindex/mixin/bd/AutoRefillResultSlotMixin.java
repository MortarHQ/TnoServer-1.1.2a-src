package com.sbjeiindex.mixin.bd;

import com.sbjeiindex.util.BackpackHelper;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "com.wintercogs.beyonddimensions.common.menu.widget.slot.AutoRefillResultSlot", remap = false)
public class AutoRefillResultSlotMixin {
   @Redirect(
      method = "onTake",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CraftingContainer;removeItem(II)Lnet/minecraft/world/item/ItemStack;"),
      remap = false
   )
   private ItemStack sbjeiindex_extractFromBackpacksBeforeRemoving(
      CraftingContainer craftSlots, int slotIndex, int amount, Player player, ItemStack craftedStack
   ) {
      int remaining = amount;
      if (remaining > 0 && player != null) {
         ItemStack template = craftSlots.getItem(slotIndex);
         if (!template.isEmpty()) {
            remaining = sb_jei_index$extractFromBackpacks(player, template, remaining);
         }
      }

      return remaining <= 0 ? ItemStack.EMPTY : craftSlots.removeItem(slotIndex, remaining);
   }

   @Unique
   private static int sb_jei_index$extractFromBackpacks(Player player, ItemStack template, int amount) {
      List<InventoryHandler> handlers = BackpackHelper.getEquippedBackpackInventoryHandlersWithJEIIndexUpgrade(player);
      if (handlers.isEmpty()) {
         return amount;
      }

      int remaining = amount;

      for (InventoryHandler handler : handlers) {
         if (handler != null) {
            for (int i = 0; i < handler.getSlots() && remaining > 0; i++) {
               ItemStack stack = handler.getStackInSlot(i);
               if (!stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, template)) {
                  ItemStack extracted = handler.extractItem(i, remaining, false);
                  if (!extracted.isEmpty()) {
                     remaining -= extracted.getCount();
                  }
               }
            }

            if (remaining <= 0) {
               break;
            }
         }
      }

      return remaining;
   }
}
