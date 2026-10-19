package com.tom.storagemod.menu;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.StorageTerminalBlockEntity;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.util.DataSlots;
import com.tom.storagemod.util.IDataReceiver;
import com.tom.storagemod.util.TerminalSyncManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class StorageTerminalMenu extends RecipeBookMenu<CraftingInput, CraftingRecipe> implements IDataReceiver, TerminalSyncManager.InteractHandler {
   protected StorageTerminalBlockEntity te;
   protected int playerSlotsStart;
   public List<StorageTerminalMenu.SlotStorage> storageSlotList = new ArrayList<>();
   public List<TerminalItemStack> itemList = Lists.newArrayList();
   public List<TerminalItemStack> itemListClient = Lists.newArrayList();
   public List<TerminalItemStack> itemListClientSorted = Lists.newArrayList();
   public List<StorageTerminalMenu.SlotData> slotData = new ArrayList<>();
   public TerminalSyncManager sync;
   private int lines;
   protected Inventory pinv;
   public Runnable onPacket;
   public int sorting;
   public int modes;
   public int searchType = -1;
   public int beaconLvl;
   public int changeCount;
   public int slotCount;
   public int freeCount;
   public String search;
   public boolean noSort;
   public boolean itemsLoaded = false;
   public Slot offhand;

   public StorageTerminalMenu(int id, Inventory inv, StorageTerminalBlockEntity te) {
      this(Content.storageTerminalMenu.get(), id, inv, te);
      this.addPlayerSlots(inv, 8, 120);
   }

   public StorageTerminalMenu(MenuType<?> type, int id, Inventory inv, StorageTerminalBlockEntity te) {
      super(type, id);
      this.te = te;
      this.pinv = inv;
      this.sync = new TerminalSyncManager(inv.player.registryAccess());
      this.addStorageSlots();
      this.addDataSlot(DataSlots.create(v -> this.sorting = v, () -> te != null ? te.getSorting() : 0).onUpdate(this::updateGui));
      this.addDataSlot(DataSlots.create(v -> this.modes = v, () -> te != null ? te.getModes() : 0).onUpdate(this::updateGui));
      this.addDataSlot(DataSlots.create(v -> this.searchType = v, () -> te != null ? te.getSearchType() : 0).onUpdate(this::updateGui));
      this.addDataSlot(DataSlots.create(v -> this.beaconLvl = v, () -> te != null ? te.getBeaconLevel() : -1));
      this.addDataSlot(DataSlots.create(v -> this.slotCount = v, () -> te != null ? te.getSlotCount() : -1));
      this.addDataSlot(DataSlots.create(v -> this.freeCount = v, () -> te != null ? te.getFreeCount() : -1));
   }

   private void updateGui() {
      if (this.onPacket != null) {
         this.onPacket.run();
      }
   }

   public StorageTerminalMenu(MenuType<?> type, int id, Inventory inv) {
      this(type, id, inv, null);
   }

   protected void addStorageSlots() {
      this.addStorageSlots(5, 8, 18);
   }

   public StorageTerminalMenu(int id, Inventory inv) {
      this(Content.storageTerminalMenu.get(), id, inv);
      this.addPlayerSlots(inv, 8, 120);
   }

   protected void addPlayerSlots(final Inventory playerInventory, int x, int y) {
      this.playerSlotsStart = this.slots.size() - 1;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlot(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, x + i * 18, y + 58));
      }

      this.offhand = this.addSlot(new Slot(playerInventory, playerInventory.items.size() + playerInventory.armor.size(), 0, y + 58) {
         public void setByPlayer(ItemStack p_270479_, ItemStack p_299920_) {
            playerInventory.player.onEquipItem(EquipmentSlot.OFFHAND, p_270479_, p_299920_);
            super.setByPlayer(p_270479_, p_299920_);
         }

         public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
         }
      });
   }

   protected Slot addSlot(Slot slotIn) {
      this.slotData.add(new StorageTerminalMenu.SlotData(slotIn));
      return super.addSlot(slotIn);
   }

   public void setOffset(int x, int y) {
      this.slotData.forEach(d -> d.setOffset(x, y));
   }

   public final void addStorageSlots(int lines, int x, int y) {
      this.storageSlotList.clear();
      this.lines = lines;

      for (int i = 0; i < lines; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new StorageTerminalMenu.SlotStorage(this.te, i * 9 + j, x + j * 18, y + i * 18));
         }
      }

      this.scrollTo(0.0F);
   }

   protected final void addSlotToContainer(StorageTerminalMenu.SlotStorage slotStorage) {
      this.storageSlotList.add(slotStorage);
   }

   public boolean stillValid(Player playerIn) {
      return this.te == null || this.te.canInteractWith(playerIn, true);
   }

   public final void scrollTo(float p_148329_1_) {
      int i = (this.itemListClientSorted.size() + 9 - 1) / 9 - this.lines;
      int j = (int)(p_148329_1_ * i + 0.5);
      if (j < 0) {
         j = 0;
      }

      for (int k = 0; k < this.lines; k++) {
         for (int l = 0; l < 9; l++) {
            int i1 = l + (k + j) * 9;
            if (i1 >= 0 && i1 < this.itemListClientSorted.size()) {
               this.setSlotContents(l + k * 9, this.itemListClientSorted.get(i1));
            } else {
               this.setSlotContents(l + k * 9, null);
            }
         }
      }
   }

   public final void setSlotContents(int id, TerminalItemStack stack) {
      this.storageSlotList.get(id).stack = stack;
   }

   public final StorageTerminalMenu.SlotStorage getSlotByID(int id) {
      return this.storageSlotList.get(id);
   }

   public void broadcastChanges() {
      if (this.te != null) {
         Map<StoredItemStack, TerminalItemStack> itemsCount = this.te.getStacks();
         this.sync.update(this.te.getChangeCount(), itemsCount, (ServerPlayer)this.pinv.player, !this.te.getLastSearch().equals(this.search) ? tag -> {
            this.search = this.te.getLastSearch();
            tag.putString("s", this.search);
         } : null);
         super.broadcastChanges();
      }
   }

   public final void receiveClientNBTPacket(CompoundTag message) {
      if (this.sync.receiveUpdate(this.pinv.player.registryAccess(), message)) {
         this.itemList = this.sync.getAsList();
         if (this.noSort) {
            this.itemListClient.forEach(s -> s.setCount(this.sync.getAmount(s)));
         } else {
            this.itemListClient = new ArrayList<>(this.itemList);
         }

         this.itemsLoaded = true;
         this.pinv.setChanged();
      }

      if (message.contains("s")) {
         this.search = message.getString("s");
      }

      if (this.onPacket != null) {
         this.onPacket.run();
      }
   }

   public final ItemStack quickMoveStack(Player playerIn, int index) {
      if (this.slots.size() > index) {
         if (index <= this.playerSlotsStart || this.te == null) {
            return this.shiftClickItems(playerIn, index);
         }

         if (this.slots.get(index) != null && ((Slot)this.slots.get(index)).hasItem()) {
            Slot slot = (Slot)this.slots.get(index);
            ItemStack slotStack = slot.getItem();
            StoredItemStack c = this.te.pushStack(new StoredItemStack(slotStack, slotStack.getCount()));
            ItemStack itemstack = c != null ? c.getActualStack() : ItemStack.EMPTY;
            slot.set(itemstack);
            if (!playerIn.level().isClientSide) {
               this.broadcastChanges();
            }
         }
      }

      return ItemStack.EMPTY;
   }

   protected ItemStack shiftClickItems(Player playerIn, int index) {
      return ItemStack.EMPTY;
   }

   public void fillCraftSlotsStackedContents(StackedContents itemHelperIn) {
   }

   public void clearCraftingContent() {
   }

   public int getResultSlotIndex() {
      return 0;
   }

   public int getGridWidth() {
      return 0;
   }

   public int getGridHeight() {
      return 0;
   }

   public int getSize() {
      return 0;
   }

   public void sendMessage(CompoundTag compound) {
      NetworkHandler.sendDataToServer(compound);
   }

   @Override
   public void receive(CompoundTag message) {
      if (!this.pinv.player.isSpectator()) {
         if (message.contains("s")) {
            this.te.setLastSearch(message.getString("s"));
         }

         this.sync.receiveInteract(message, this);
         if (message.contains("c")) {
            CompoundTag d = message.getCompound("c");
            this.te.setSorting(d.getInt("s"));
            this.te.setSearchType(d.getInt("st"));
            this.te.setModes(d.getInt("m"));
         }
      }
   }

   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   public boolean shouldMoveToInventory(int p_150635_) {
      return false;
   }

   @Override
   public void onInteract(StoredItemStack clicked, TerminalSyncManager.SlotAction act, boolean mod) {
      ServerPlayer player = (ServerPlayer)this.pinv.player;
      player.resetLastActionTime();
      if (act == TerminalSyncManager.SlotAction.SPACE_CLICK) {
         for (int i = this.playerSlotsStart + 1; i < this.playerSlotsStart + 28; i++) {
            this.quickMoveStack(player, i);
         }
      } else if (act == TerminalSyncManager.SlotAction.PULL_OR_PUSH_STACK) {
         ItemStack stack = this.getCarried();
         if (!stack.isEmpty()) {
            StoredItemStack rem = this.te.pushStack(new StoredItemStack(stack));
            ItemStack itemstack = rem == null ? ItemStack.EMPTY : rem.getActualStack();
            this.setCarried(itemstack);
         } else {
            if (clicked == null) {
               return;
            }

            StoredItemStack pulled = this.te.pullStack(clicked, clicked.getMaxStackSize());
            if (pulled != null) {
               this.setCarried(pulled.getActualStack());
            }
         }
      } else if (act == TerminalSyncManager.SlotAction.PULL_ONE) {
         ItemStack stack = this.getCarried();
         if (clicked == null) {
            return;
         }

         if (mod) {
            StoredItemStack pulled = this.te.pullStack(clicked, 1L);
            if (pulled != null) {
               ItemStack itemstack = pulled.getActualStack();
               this.moveItemStackTo(itemstack, this.playerSlotsStart + 1, this.slots.size(), true);
               if (itemstack.getCount() > 0) {
                  this.te.pushOrDrop(itemstack);
               }

               player.getInventory().setChanged();
            }
         } else if (!stack.isEmpty()) {
            if (ItemStack.isSameItemSameComponents(stack, clicked.getStack()) && stack.getCount() + 1 <= stack.getMaxStackSize()) {
               StoredItemStack pulled = this.te.pullStack(clicked, 1L);
               if (pulled != null) {
                  stack.grow(1);
               }
            }
         } else {
            StoredItemStack pulled = this.te.pullStack(clicked, 1L);
            if (pulled != null) {
               this.setCarried(pulled.getActualStack());
            }
         }
      } else if (act == TerminalSyncManager.SlotAction.GET_HALF) {
         ItemStack stack = this.getCarried();
         if (!stack.isEmpty()) {
            ItemStack stack1 = stack.split(Math.max(Math.min(stack.getCount(), stack.getMaxStackSize()) / 2, 1));
            ItemStack itemstack = this.te.pushStack(stack1);
            stack.grow(!itemstack.isEmpty() ? itemstack.getCount() : 0);
            this.setCarried(stack);
         } else {
            if (clicked == null) {
               return;
            }

            StoredItemStack pulled = this.te.pullStack(clicked, Math.max(Math.min(clicked.getQuantity(), clicked.getMaxStackSize()) / 2L, 1L));
            if (pulled != null) {
               this.setCarried(pulled.getActualStack());
            }
         }
      } else if (act == TerminalSyncManager.SlotAction.GET_QUARTER) {
         ItemStack stack = this.getCarried();
         if (!stack.isEmpty()) {
            ItemStack stack1 = stack.split(Math.max(Math.min(stack.getCount(), stack.getMaxStackSize()) / 4, 1));
            ItemStack itemstack = this.te.pushStack(stack1);
            stack.grow(!itemstack.isEmpty() ? itemstack.getCount() : 0);
            this.setCarried(stack);
         } else {
            if (clicked == null) {
               return;
            }

            StoredItemStack pulled = this.te.pullStack(clicked, Math.max(Math.min(clicked.getQuantity(), clicked.getMaxStackSize()) / 4L, 1L));
            if (pulled != null) {
               this.setCarried(pulled.getActualStack());
            }
         }
      } else {
         if (clicked == null) {
            return;
         }

         StoredItemStack pulled = this.te.pullStack(clicked, clicked.getMaxStackSize());
         if (pulled != null) {
            ItemStack itemstack = pulled.getActualStack();
            this.moveItemStackTo(itemstack, this.playerSlotsStart + 1, this.slots.size() - 1, true);
            if (itemstack.getCount() > 0) {
               this.te.pushOrDrop(itemstack);
            }

            player.getInventory().setChanged();
         }
      }
   }

   public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipeHolder) {
      return false;
   }

   public record SlotData(Slot slot, int x, int y) {
      public SlotData(Slot s) {
         this(s, s.x, s.y);
      }

      public void setOffset(int x, int y) {
         this.slot.x = this.x + x;
         this.slot.y = this.y + y;
      }
   }

   public static class SlotStorage {
      public int xDisplayPosition;
      public int yDisplayPosition;
      private final int slotIndex;
      public final StorageTerminalBlockEntity inventory;
      public TerminalItemStack stack;

      public SlotStorage(StorageTerminalBlockEntity inventory, int slotIndex, int xPosition, int yPosition) {
         this.xDisplayPosition = xPosition;
         this.yDisplayPosition = yPosition;
         this.slotIndex = slotIndex;
         this.inventory = inventory;
      }

      public ItemStack pullFromSlot(long max) {
         if (this.stack != null && max >= 1L && this.inventory != null) {
            StoredItemStack r = this.inventory.pullStack(this.stack, max);
            return r != null ? r.getActualStack() : ItemStack.EMPTY;
         } else {
            return ItemStack.EMPTY;
         }
      }

      public ItemStack pushStack(ItemStack pushStack) {
         if (this.inventory == null) {
            return pushStack;
         }

         StoredItemStack r = this.inventory.pushStack(new StoredItemStack(pushStack, pushStack.getCount()));
         return r != null ? r.getActualStack() : ItemStack.EMPTY;
      }

      public int getSlotIndex() {
         return this.slotIndex;
      }
   }
}
