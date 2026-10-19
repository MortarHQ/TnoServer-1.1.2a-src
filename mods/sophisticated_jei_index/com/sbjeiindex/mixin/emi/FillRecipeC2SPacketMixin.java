package com.sbjeiindex.mixin.emi;

import com.sbjeiindex.emi.transfer.BackpackSlotSource;
import com.sbjeiindex.emi.transfer.EmiFillHelper;
import com.sbjeiindex.emi.transfer.EmiInputSource;
import com.sbjeiindex.emi.transfer.MenuSlotSource;
import com.sbjeiindex.util.BackpackHelper;
import dev.emi.emi.network.FillRecipeC2SPacket;
import dev.emi.emi.runtime.EmiLog;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FillRecipeC2SPacket.class, remap = false)
public class FillRecipeC2SPacketMixin {
   @Shadow
   @Final
   private int syncId;
   @Shadow
   @Final
   private int action;
   @Shadow
   @Final
   private List<Integer> slots;
   @Shadow
   @Final
   private List<Integer> crafting;
   @Shadow
   @Final
   private int output;
   @Shadow
   @Final
   private List<ItemStack> stacks;

   @Overwrite
   public void apply(Player player) {
      if (this.slots != null && this.crafting != null) {
         AbstractContainerMenu menu = player.containerMenu;
         if (menu != null && menu.containerId == this.syncId) {
            List<IItemHandlerModifiable> backpackHandlers = null;
            List<EmiInputSource> inputSources = new ArrayList<>();

            for (int slotId : this.slots) {
               if (slotId < 0 || slotId >= menu.slots.size()) {
                  if (slotId >= 10000) {
                     if (backpackHandlers == null) {
                        backpackHandlers = BackpackHelper.getEquippedBackpackItemHandlersWithJEIIndexUpgrade(player);
                     }

                     int relative = slotId - 10000;
                     int backpackIndex = relative / 10000;
                     int backpackSlot = relative % 10000;
                     if (backpackIndex >= 0 && backpackIndex < backpackHandlers.size()) {
                        IItemHandlerModifiable handler = backpackHandlers.get(backpackIndex);
                        if (backpackSlot >= 0 && backpackSlot < handler.getSlots()) {
                           inputSources.add(new BackpackSlotSource(handler, backpackSlot));
                           continue;
                        }

                        EmiLog.warn("Client requested fill but passed input slots don't exist, aborting");
                        return;
                     }

                     EmiLog.warn("Client requested fill but passed input slots don't exist, aborting");
                     return;
                  }

                  EmiLog.warn("Client requested fill but passed input slots don't exist, aborting");
                  return;
               } else {
                  inputSources.add(new MenuSlotSource((Slot)menu.slots.get(slotId)));
               }
            }

            List<Slot> craftingSlots = new ArrayList<>();

            for (int slotId : this.crafting) {
               if (slotId >= 0 && slotId < menu.slots.size()) {
                  craftingSlots.add((Slot)menu.slots.get(slotId));
               } else {
                  craftingSlots.add(null);
               }
            }

            Slot outputSlot = null;
            if (this.output != -1 && this.output >= 0 && this.output < menu.slots.size()) {
               outputSlot = (Slot)menu.slots.get(this.output);
            }

            if (craftingSlots.size() >= this.stacks.size()) {
               List<ItemStack> cleared = new ArrayList<>();

               try {
                  for (Slot slot : craftingSlots) {
                     if (slot != null && slot.mayPickup(player)) {
                        ItemStack stack = slot.getItem();
                        if (!stack.isEmpty()) {
                           cleared.add(stack.copy());
                           slot.set(ItemStack.EMPTY);
                           slot.onTake(player, stack);
                        }
                     }
                  }

                  int i = 0;

                  while (true) {
                     if (i >= this.stacks.size()) {
                        if (outputSlot != null) {
                           if (this.action == 1) {
                              menu.clicked(outputSlot.index, 0, ClickType.PICKUP, player);
                           } else if (this.action == 2) {
                              menu.clicked(outputSlot.index, 0, ClickType.QUICK_MOVE, player);
                              return;
                           }

                           return;
                        }

                        return;
                     }

                     ItemStack stack = this.stacks.get(i);
                     if (!stack.isEmpty()) {
                        int grabbed = EmiFillHelper.grabMatching(player, inputSources, cleared, craftingSlots, stack);
                        if (grabbed != stack.getCount()) {
                           if (grabbed > 0) {
                              stack.shrink(grabbed);
                              player.getInventory().placeItemBackInInventory(stack);
                           }
                           break;
                        }

                        Slot dest = craftingSlots.get(i);
                        if (dest != null && dest.mayPlace(stack) && stack.getCount() <= dest.getMaxStackSize() && stack.getCount() <= stack.getMaxStackSize()) {
                           dest.set(stack);
                        } else {
                           player.getInventory().placeItemBackInInventory(stack);
                        }
                     }

                     i++;
                  }
               } finally {
                  for (ItemStack s : cleared) {
                     player.getInventory().placeItemBackInInventory(s);
                  }
               }
            }
         } else {
            EmiLog.warn("Client requested fill but screen handler has changed, aborting");
         }
      } else {
         EmiLog.warn("Client requested fill but passed input and crafting slot information was invalid, aborting");
      }
   }
}
