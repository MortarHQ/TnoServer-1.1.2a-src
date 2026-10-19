package com.tom.storagemod.menu;

import com.google.common.collect.Lists;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.CraftingTerminalBlockEntity;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.platform.NeoForgeMenu;
import com.tom.storagemod.util.IAutoFillTerminal;
import com.tom.storagemod.util.IDataReceiver;
import java.util.List;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class CraftingTerminalMenu extends StorageTerminalMenu implements IAutoFillTerminal, IDataReceiver, NeoForgeMenu {
   private final CraftingContainer craftMatrix;
   private final ResultContainer craftResult;
   private Slot craftingResultSlot;
   private final List<ContainerListener> listeners = Lists.newArrayList();

   public void addSlotListener(ContainerListener listener) {
      super.addSlotListener(listener);
      this.listeners.add(listener);
   }

   public void removeSlotListener(ContainerListener listener) {
      super.removeSlotListener(listener);
      this.listeners.remove(listener);
   }

   public CraftingTerminalMenu(int id, Inventory inv, CraftingTerminalBlockEntity te) {
      super(Content.craftingTerminalMenu.get(), id, inv, te);
      this.craftMatrix = te.getCraftingInv();
      this.craftResult = te.getCraftResult();
      this.init();
      this.addPlayerSlots(inv, 8, 174);
      te.registerCrafting(this);
   }

   public CraftingTerminalMenu(int id, Inventory inv) {
      super(Content.craftingTerminalMenu.get(), id, inv);
      this.craftMatrix = new TransientCraftingContainer(this, 3, 3);
      this.craftResult = new ResultContainer();
      this.init();
      this.addPlayerSlots(inv, 8, 174);
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      if (this.te != null) {
         ((CraftingTerminalBlockEntity)this.te).unregisterCrafting(playerIn, this);
      }
   }

   private void init() {
      int x = -4;
      int y = 94;
      this.addSlot(this.craftingResultSlot = new CraftingTerminalMenu.Result(x + 124, y + 35));

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            this.addSlot(new CraftingTerminalMenu.SlotCrafting(this.craftMatrix, j + i * 3, x + 30 + j * 18, y + 17 + i * 18));
         }
      }
   }

   @Override
   protected void addStorageSlots() {
      this.addStorageSlots(5, 8, 18);
   }

   public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
      return slotIn.container != this.craftResult && super.canTakeItemForPickAll(stack, slotIn);
   }

   @Override
   public ItemStack shiftClickItems(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (index == 0) {
            if (this.te == null) {
               return ItemStack.EMPTY;
            }

            if (((CraftingTerminalBlockEntity)this.te).canCraft() && this.moveItemStackTo(itemstack1, 10, 46, true)) {
               slot.onQuickCraft(itemstack1, itemstack);
               if (itemstack1.isEmpty()) {
                  slot.set(ItemStack.EMPTY);
               } else {
                  slot.setChanged();
               }

               if (itemstack1.getCount() == itemstack.getCount()) {
                  return ItemStack.EMPTY;
               }

               slot.onTake(playerIn, itemstack1);
               if (index == 0) {
                  playerIn.drop(itemstack1, false);
               }

               return itemstack;
            }

            return ItemStack.EMPTY;
         }

         if (index > 0 && index < 10) {
            if (this.te == null) {
               return ItemStack.EMPTY;
            }

            ItemStack stack = this.te.pushStack(itemstack);
            slot.set(stack);
            if (!playerIn.level().isClientSide) {
               this.broadcastChanges();
            }
         }

         slot.onTake(playerIn, itemstack1);
      }

      return ItemStack.EMPTY;
   }

   public void onCraftMatrixChanged() {
      for (int i = 0; i < this.slots.size(); i++) {
         Slot slot = (Slot)this.slots.get(i);
         if (slot instanceof CraftingTerminalMenu.SlotCrafting || slot == this.craftingResultSlot) {
            for (ContainerListener listener : this.listeners) {
               if (listener instanceof ServerPlayer) {
                  ((ServerPlayer)listener).connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), i, slot.getItem()));
               }
            }
         }
      }
   }

   public boolean clickMenuButton(Player playerIn, int id) {
      if (this.te != null && id == 0) {
         ((CraftingTerminalBlockEntity)this.te).clear(playerIn);
      } else if (this.te != null && id == 1) {
         ((CraftingTerminalBlockEntity)this.te).polymorphUpdate(playerIn);
      } else {
         super.clickMenuButton(playerIn, id);
      }

      return false;
   }

   @Override
   public void fillCraftSlotsStackedContents(StackedContents itemHelperIn) {
      this.craftMatrix.fillStackedContents(itemHelperIn);
      if (this.te != null) {
         this.sync.fillStackedContents(itemHelperIn);
      } else {
         this.itemList.forEach(e -> itemHelperIn.accountSimpleStack(e.getActualStack()));
      }
   }

   @Override
   public void clearCraftingContent() {
      this.craftMatrix.clearContent();
      this.craftResult.clearContent();
   }

   @Override
   public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipeHolder) {
      return ((CraftingRecipe)recipeHolder.value()).matches(this.craftMatrix.asCraftInput(), this.pinv.player.level());
   }

   @Override
   public int getResultSlotIndex() {
      return 0;
   }

   @Override
   public int getGridWidth() {
      return this.craftMatrix.getWidth();
   }

   @Override
   public int getGridHeight() {
      return this.craftMatrix.getHeight();
   }

   @Override
   public int getSize() {
      return 10;
   }

   public List<RecipeBookCategories> getRecipeBookCategories() {
      return Lists.newArrayList(
         new RecipeBookCategories[]{
            RecipeBookCategories.CRAFTING_SEARCH,
            RecipeBookCategories.CRAFTING_EQUIPMENT,
            RecipeBookCategories.CRAFTING_BUILDING_BLOCKS,
            RecipeBookCategories.CRAFTING_MISC,
            RecipeBookCategories.CRAFTING_REDSTONE
         }
      );
   }

   public void handlePlacement(boolean p_40119_, RecipeHolder<?> p_300860_, ServerPlayer p_40121_) {
      new TerminalRecipePlacer(this, (CraftingTerminalBlockEntity)this.te, p_40121_).recipeClicked(p_40121_, p_300860_, p_40119_);
   }

   @Override
   public void receive(CompoundTag message) {
      super.receive(message);
      if (message.contains("fill")) {
         ResourceLocation id = ResourceLocation.tryParse(message.getString("fill"));
         if (id != null) {
            RecipeHolder<?> recipe = (RecipeHolder<?>)this.pinv.player.level().getRecipeManager().byKey(id).orElse(null);
            if (recipe != null) {
               new TerminalCraftingFiller((CraftingTerminalBlockEntity)this.te, this.pinv.player, this.sync).placeRecipe(recipe.value());
            }
         }
      }
   }

   @Override
   public List<TerminalItemStack> getStoredItems() {
      return this.itemList;
   }

   public Slot getCraftingResultSlot() {
      return this.craftingResultSlot;
   }

   @Override
   public boolean isSmartSearch() {
      return (this.searchType & 8) == 0;
   }

   private class Result extends ResultSlot {
      public Result(int x, int y) {
         super(CraftingTerminalMenu.this.pinv.player, CraftingTerminalMenu.this.craftMatrix, CraftingTerminalMenu.this.craftResult, 0, x, y);
      }

      public void onTake(Player thePlayer, ItemStack stack) {
         this.checkTakeAchievements(stack);
         if (!CraftingTerminalMenu.this.pinv.player.getCommandSenderWorld().isClientSide) {
            ((CraftingTerminalBlockEntity)CraftingTerminalMenu.this.te).craft(thePlayer);
         }
      }
   }

   public static class SlotCrafting extends Slot {
      public SlotCrafting(Container inventoryIn, int index, int xPosition, int yPosition) {
         super(inventoryIn, index, xPosition, yPosition);
      }
   }
}
