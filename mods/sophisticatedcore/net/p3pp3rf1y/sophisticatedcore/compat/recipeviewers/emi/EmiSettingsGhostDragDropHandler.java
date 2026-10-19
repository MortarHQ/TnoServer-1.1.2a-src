package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi;

import dev.emi.emi.api.EmiDragDropHandler.SlotBased;
import dev.emi.emi.api.stack.EmiStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.SetMemorySlotPayload;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsTab;

public class EmiSettingsGhostDragDropHandler<T extends SettingsScreen> extends SlotBased<T> {
   public EmiSettingsGhostDragDropHandler() {
      super(screen -> {
         List<Slot> slots = new ArrayList<>();
         screen.getSettingsTabControl().getOpenTab().ifPresent(tab -> {
            if (tab instanceof MemorySettingsTab) {
               ((SettingsContainerMenu)screen.getMenu()).getStorageInventorySlots().forEach(s -> {
                  if (s.getItem().isEmpty()) {
                     slots.add(s);
                  }
               });
            }
         });
         return slots;
      }, (screen, slot, ingredient) -> {
         List<EmiStack> stacks = ingredient.getEmiStacks();
         if (stacks.size() == 1) {
            ItemStack stack = stacks.getFirst().getItemStack();
            if (slot.mayPlace(stack)) {
               PacketDistributor.sendToServer(new SetMemorySlotPayload(stack, slot.index), new CustomPacketPayload[0]);
            }
         }
      });
   }
}
