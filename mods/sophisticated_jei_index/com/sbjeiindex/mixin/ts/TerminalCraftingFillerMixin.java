package com.sbjeiindex.mixin.ts;

import com.sbjeiindex.util.BackpackExtraction;
import com.sbjeiindex.util.BackpackHelper;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.tom.storagemod.menu.TerminalCraftingFiller", remap = false)
public class TerminalCraftingFillerMixin {
   @Shadow
   private Player player;
   private static final int BACKPACK_SLOT_ID = -2;
   private static final ThreadLocal<ItemStack> BACKPACK_TEMPLATE = new ThreadLocal<>();

   @Shadow
   public void accountStack(ItemStack st) {
   }

   @Inject(method = "placeRecipe", at = @At("HEAD"), remap = false)
   private void sbjeiindex_addBackpackItems(Recipe<?> recipe, CallbackInfo ci) {
      List<InventoryHandler> handlers = BackpackHelper.getEquippedBackpackInventoryHandlersWithJEIIndexUpgrade(this.player);
      if (!handlers.isEmpty()) {
         for (InventoryHandler handler : handlers) {
            if (handler != null) {
               int slots = handler.getSlots();

               for (int i = 0; i < slots; i++) {
                  ItemStack stack = handler.getStackInSlot(i);
                  if (!stack.isEmpty()) {
                     this.accountStack(stack);
                  }
               }
            }
         }
      }
   }

   @Redirect(
      method = "placeRecipe",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;findSlotMatchingItem(Lnet/minecraft/world/item/ItemStack;)I"),
      remap = false
   )
   private int sbjeiindex_findSlotMatchingItem(Inventory inventory, ItemStack stack) {
      int id = inventory.findSlotMatchingItem(stack);
      if (id != -1) {
         return id;
      }

      if (inventory != null && inventory.player != null && stack != null && !stack.isEmpty()) {
         List<InventoryHandler> handlers = BackpackHelper.getEquippedBackpackInventoryHandlersWithJEIIndexUpgrade(inventory.player);
         if (handlers.isEmpty()) {
            return -1;
         } else {
            ItemStack[] templates = new ItemStack[]{stack};
            if (BackpackExtraction.containsAnyTemplateMatch(handlers, templates)) {
               BACKPACK_TEMPLATE.set(stack);
               return -2;
            } else {
               return -1;
            }
         }
      } else {
         return -1;
      }
   }

   @Redirect(
      method = "placeRecipe",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(II)Lnet/minecraft/world/item/ItemStack;"),
      remap = false
   )
   private ItemStack sbjeiindex_removeItem(Inventory inventory, int slot, int count) {
      if (slot != -2) {
         return inventory.removeItem(slot, count);
      }

      if (inventory != null && inventory.player != null) {
         ItemStack template = BACKPACK_TEMPLATE.get();
         BACKPACK_TEMPLATE.remove();
         if (template != null && !template.isEmpty()) {
            List<InventoryHandler> handlers = BackpackHelper.getEquippedBackpackInventoryHandlersWithJEIIndexUpgrade(inventory.player);
            return handlers.isEmpty() ? ItemStack.EMPTY : BackpackExtraction.extractFirstTemplateMatch(handlers, new ItemStack[]{template});
         } else {
            return ItemStack.EMPTY;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }
}
