package com.sbjeiindex.mixin.bd;

import com.sbjeiindex.util.BackpackHelper;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.wintercogs.beyonddimensions.common.menu.DimensionsCraftMenu", remap = false)
public class DimensionsCraftMenuMixin {
   @Inject(method = "extractFromInventory", at = @At("RETURN"), cancellable = true, remap = false)
   private void sbjeiindex_extractFromBackpacks(Inventory inventory, ItemStack template, int amount, CallbackInfoReturnable<Integer> cir) {
      int remaining = cir.getReturnValueI();
      if (remaining > 0 && template != null && !template.isEmpty() && inventory != null && inventory.player != null) {
         List<InventoryHandler> handlers = BackpackHelper.getEquippedBackpackInventoryHandlersWithJEIIndexUpgrade(inventory.player);
         if (!handlers.isEmpty()) {
            for (InventoryHandler handler : handlers) {
               if (handler != null) {
                  int slots = handler.getSlots();

                  for (int i = 0; i < slots && remaining > 0; i++) {
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

            cir.setReturnValue(remaining);
         }
      }
   }
}
