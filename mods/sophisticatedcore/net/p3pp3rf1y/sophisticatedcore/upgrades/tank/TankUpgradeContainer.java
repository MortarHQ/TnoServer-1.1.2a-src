package net.p3pp3rf1y.sophisticatedcore.upgrades.tank;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.client.gui.INameableEmptySlot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public class TankUpgradeContainer extends UpgradeContainerBase<TankUpgradeWrapper, TankUpgradeContainer> {
   public static final ResourceLocation EMPTY_TANK_INPUT_SLOT_BACKGROUND = SophisticatedCore.getRL("item/empty_tank_input_slot");
   public static final ResourceLocation EMPTY_TANK_OUTPUT_SLOT_BACKGROUND = SophisticatedCore.getRL("item/empty_tank_output_slot");

   public TankUpgradeContainer(
      Player player, int upgradeContainerId, TankUpgradeWrapper upgradeWrapper, UpgradeContainerType<TankUpgradeWrapper, TankUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      this.slots
         .add(
            new TankUpgradeContainer.TankIOSlot(
                  () -> this.upgradeWrapper.getInventory(), 0, -100, -100, TranslationHelper.INSTANCE.translUpgradeSlotTooltip("tank_input")
               )
               .setBackground(InventoryMenu.BLOCK_ATLAS, EMPTY_TANK_INPUT_SLOT_BACKGROUND)
         );
      this.slots
         .add(
            new TankUpgradeContainer.TankIOSlot(
                  () -> this.upgradeWrapper.getInventory(), 1, -100, -100, TranslationHelper.INSTANCE.translUpgradeSlotTooltip("tank_output")
               )
               .setBackground(InventoryMenu.BLOCK_ATLAS, EMPTY_TANK_OUTPUT_SLOT_BACKGROUND)
         );
      this.slots.add(new TankUpgradeContainer.TakeOnlySlot(() -> this.upgradeWrapper.getInventory(), 2, -100, -100));
      this.slots.add(new TankUpgradeContainer.TakeOnlySlot(() -> this.upgradeWrapper.getInventory(), 3, -100, -100));
   }

   @Override
   public void handlePacket(CompoundTag data) {
   }

   public FluidStack getContents() {
      return this.upgradeWrapper.getContents();
   }

   public int getTankCapacity() {
      return this.upgradeWrapper.getTankCapacity();
   }

   private static class TakeOnlySlot extends SlotSuppliedHandler {
      public TakeOnlySlot(Supplier<TankUpgradeWrapper.TankComponentItemHandler> itemHandlerSupplier, int slot, int xPosition, int yPosition) {
         super(itemHandlerSupplier::get, slot, xPosition, yPosition);
      }

      @Override
      public boolean mayPlace(ItemStack stack) {
         return false;
      }
   }

   private static class TankIOSlot extends SlotSuppliedHandler implements INameableEmptySlot {
      private final Supplier<TankUpgradeWrapper.TankComponentItemHandler> itemHandlerSupplier;
      private final Component emptyTooltip;

      public TankIOSlot(
         Supplier<TankUpgradeWrapper.TankComponentItemHandler> itemHandlerSupplier, int slot, int xPosition, int yPosition, Component emptyTooltip
      ) {
         super(itemHandlerSupplier::get, slot, xPosition, yPosition);
         this.itemHandlerSupplier = itemHandlerSupplier;
         this.emptyTooltip = emptyTooltip;
      }

      @Override
      public boolean hasEmptyTooltip() {
         return true;
      }

      @Override
      public Component getEmptyTooltip() {
         return this.emptyTooltip;
      }

      @Override
      public boolean mayPlace(ItemStack stack) {
         return this.itemHandlerSupplier.get().isItemValid(this.getSlotIndex(), stack);
      }

      public int getMaxStackSize(ItemStack stack) {
         return Math.min(stack.getMaxStackSize(), this.getMaxStackSize());
      }
   }
}
