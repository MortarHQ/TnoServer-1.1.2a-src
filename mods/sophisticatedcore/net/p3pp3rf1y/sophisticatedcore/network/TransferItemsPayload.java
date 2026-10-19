package net.p3pp3rf1y.sophisticatedcore.network;

import io.netty.buffer.ByteBuf;
import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleInserter;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public record TransferItemsPayload(boolean transferToInventory, boolean filterByContents) implements CustomPacketPayload {
   public static final Type<TransferItemsPayload> TYPE = new Type(SophisticatedCore.getRL("transfer_items"));
   public static final StreamCodec<ByteBuf, TransferItemsPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, TransferItemsPayload::transferToInventory, ByteBufCodecs.BOOL, TransferItemsPayload::filterByContents, TransferItemsPayload::new
   );

   public static void handlePayload(TransferItemsPayload payload, IPayloadContext context) {
      Player player = context.player();
      if (player.containerMenu instanceof StorageContainerMenuBase<?> storageMenu) {
         IStorageWrapper var5 = storageMenu.getStorageWrapper();
         if (payload.transferToInventory) {
            if (payload.filterByContents) {
               mergeToPlayersInventoryFiltered(player, var5);
            } else {
               mergeToPlayersInventory(var5, player);
            }
         } else {
            InventoryHelper.transfer(
               new TransferItemsPayload.PlayerMainInvWithoutHotbarWrapper(player.getInventory()),
               new TransferItemsPayload.FilteredStorageItemHandler(var5, payload.filterByContents),
               s -> {}
            );
         }
      }
   }

   private static void mergeToPlayersInventory(IStorageWrapper storageWrapper, Player player) {
      InventoryHelper.iterate(storageWrapper.getInventoryHandler(), (slot, stack) -> {
         if (!stack.isEmpty()) {
            ItemStack result = InventoryHelper.mergeIntoPlayerInventory(player, stack, 9);
            if (result.getCount() != stack.getCount()) {
               storageWrapper.getInventoryHandler().setStackInSlot(slot, result);
            }
         }
      });
   }

   private static void mergeToPlayersInventoryFiltered(Player player, IStorageWrapper storageWrapper) {
      Set<ItemStackKey> uniqueStacks = InventoryHelper.getUniqueStacks(new PlayerMainInvWrapper(player.getInventory()));
      InventoryHelper.iterate(storageWrapper.getInventoryHandler(), (slot, stack) -> {
         if (!stack.isEmpty() && uniqueStacks.contains(ItemStackKey.of(stack))) {
            ItemStack result = InventoryHelper.mergeIntoPlayerInventory(player, stack, 0);
            if (result.getCount() != stack.getCount()) {
               storageWrapper.getInventoryHandler().setStackInSlot(slot, result);
            }
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   private static class FilteredItemHandler<T extends IItemHandler> implements IItemHandler {
      protected final T itemHandler;
      protected final boolean matchContents;
      private final Set<ItemStackKey> uniqueStacks;

      public FilteredItemHandler(T itemHandler, boolean matchContents) {
         this.itemHandler = itemHandler;
         this.matchContents = matchContents;
         this.uniqueStacks = this.getUniqueStacks(itemHandler);
      }

      protected Set<ItemStackKey> getUniqueStacks(T itemHandler) {
         return InventoryHelper.getUniqueStacks(itemHandler);
      }

      public int getSlots() {
         return this.itemHandler.getSlots();
      }

      @Nonnull
      public ItemStack getStackInSlot(int slot) {
         return this.itemHandler.getStackInSlot(slot);
      }

      @Nonnull
      public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
         return this.matchContents && !this.matchesFilter(stack) ? stack : this.itemHandler.insertItem(slot, stack, simulate);
      }

      protected boolean matchesFilter(ItemStack stack) {
         return this.uniqueStacks.contains(ItemStackKey.of(stack));
      }

      @Nonnull
      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         return this.itemHandler.extractItem(slot, amount, simulate);
      }

      public int getSlotLimit(int slot) {
         return this.itemHandler.getSlotLimit(slot);
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return this.itemHandler.isItemValid(slot, stack);
      }
   }

   private static class FilteredStorageItemHandler
      extends TransferItemsPayload.FilteredItemHandler<ITrackedContentsItemHandler>
      implements IItemHandlerSimpleInserter {
      private final IStorageWrapper storageWrapper;

      public FilteredStorageItemHandler(IStorageWrapper storageWrapper, boolean smart) {
         super(storageWrapper.getInventoryHandler(), smart);
         this.storageWrapper = storageWrapper;
      }

      protected Set<ItemStackKey> getUniqueStacks(ITrackedContentsItemHandler itemHandler) {
         return itemHandler.getTrackedStacks();
      }

      @Override
      protected boolean matchesFilter(ItemStack stack) {
         return super.matchesFilter(stack) || this.storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class).matchesFilter(stack);
      }

      @Nonnull
      @Override
      public ItemStack insertItem(ItemStack stack, boolean simulate) {
         return this.matchContents && !this.matchesFilter(stack) ? stack : this.itemHandler.insertItem(stack, simulate);
      }

      public void setStackInSlot(int slot, ItemStack stack) {
         this.itemHandler.setStackInSlot(slot, stack);
      }
   }

   private static class PlayerMainInvWithoutHotbarWrapper extends RangedWrapper {
      private final Inventory inventoryPlayer;

      public PlayerMainInvWithoutHotbarWrapper(Inventory inv) {
         super(new InvWrapper(inv), 9, inv.items.size());
         this.inventoryPlayer = inv;
      }

      public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
         ItemStack rest = super.insertItem(slot, stack, simulate);
         if (rest.getCount() != stack.getCount()) {
            ItemStack inSlot = this.getStackInSlot(slot);
            if (!inSlot.isEmpty()) {
               if (this.getInventoryPlayer().player.level().isClientSide) {
                  inSlot.setPopTime(5);
               } else if (this.getInventoryPlayer().player instanceof ServerPlayer) {
                  this.getInventoryPlayer().player.containerMenu.broadcastChanges();
               }
            }
         }

         return rest;
      }

      public Inventory getInventoryPlayer() {
         return this.inventoryPlayer;
      }
   }
}
