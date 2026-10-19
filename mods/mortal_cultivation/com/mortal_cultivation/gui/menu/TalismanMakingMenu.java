package com.mortal_cultivation.gui.menu;

import com.mortal_cultivation.data.TalismanMakingData;
import com.mortal_cultivation.data.recipe.TalismanRecipe;
import com.mortal_cultivation.init.MortalCultivationModMenus;
import com.mortal_cultivation.registry.RecipeRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class TalismanMakingMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
   public static final HashMap<String, Object> guistate = new HashMap<>();
   private static final int INPUT_SLOT_COUNT = 7;
   private static final int OUTPUT_SLOT = 7;
   private static final int TOOL_SLOT = 8;
   private static final int MENU_SLOT_COUNT = 9;
   private static final int PLAYER_INVENTORY_START = 9;
   private static final int PLAYER_INVENTORY_END = 36;
   private static final int HOTBAR_END = 45;
   private static final int[] INPUT_SLOT_X = new int[]{67, 94, 121, 133, 112, 74, 53};
   private static final int[] INPUT_SLOT_Y = new int[]{29, 20, 29, 57, 83, 83, 57};
   private static final int OUTPUT_SLOT_X = 93;
   private static final int OUTPUT_SLOT_Y = 55;
   private static final int TOOL_SLOT_X = 31;
   private static final int TOOL_SLOT_Y = 103;
   public final Level world;
   public final Player entity;
   public int x;
   public int y;
   public int z;
   private IItemHandler internal;
   private final Map<Integer, Slot> customSlots = new HashMap<>();
   private boolean bound = false;
   private int currentMaxCraftAmount = 0;
   private final Container craftingContainer = new SimpleContainer(7) {
      public void setChanged() {
         super.setChanged();
         TalismanMakingMenu.this.slotsChanged(this);
      }
   };

   public TalismanMakingMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
      super((MenuType)MortalCultivationModMenus.TALISMAN_MAKING.get(), id);
      this.entity = inv.player;
      this.world = inv.player.level();
      this.internal = new ItemStackHandler(9);
      BlockPos pos = null;
      if (extraData != null) {
         pos = extraData.readBlockPos();
         this.x = pos.getX();
         this.y = pos.getY();
         this.z = pos.getZ();
      }

      if (pos != null) {
         if (extraData.readableBytes() == 1) {
            byte hand = extraData.readByte();
            ItemStack itemstack;
            if (hand == 0) {
               itemstack = this.entity.getMainHandItem();
            } else {
               itemstack = this.entity.getOffhandItem();
            }

            IItemHandler capability = (IItemHandler)itemstack.getCapability(ItemHandler.ITEM);
            if (capability != null) {
               this.internal = capability;
               this.bound = true;
            }
         } else if (extraData.readableBytes() > 1) {
            extraData.readByte();
            Entity entity = this.world.getEntity(extraData.readVarInt());
            if (entity != null) {
               IItemHandler capability = (IItemHandler)entity.getCapability(ItemHandler.ENTITY);
               if (capability != null) {
                  this.internal = capability;
                  this.bound = true;
               }
            }
         } else {
            BlockEntity ent = inv.player != null ? inv.player.level().getBlockEntity(pos) : null;
            if (ent != null) {
               IItemHandler capability = (IItemHandler)this.world.getCapability(ItemHandler.BLOCK, pos, null);
               if (capability != null) {
                  this.internal = capability;
                  this.bound = true;
               }
            }
         }
      }

      for (int slotIndex = 0; slotIndex < 7; slotIndex++) {
         int inputSlot = slotIndex;
         this.customSlots.put(inputSlot, this.addSlot(new SlotItemHandler(this.internal, inputSlot, INPUT_SLOT_X[inputSlot], INPUT_SLOT_Y[inputSlot]) {
            public void setChanged() {
               super.setChanged();
               TalismanMakingMenu.this.updateCraftingContainer();
            }
         }));
      }

      this.customSlots.put(7, this.addSlot(new SlotItemHandler(this.internal, 7, 93, 55) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }

         public void onTake(Player player, ItemStack stack) {
            TalismanMakingMenu.this.consumeIngredients();
            super.onTake(player, stack);
         }
      }));
      this.customSlots.put(8, this.addSlot(new SlotItemHandler(this.internal, 8, 31, 103) {
         public boolean mayPlace(ItemStack stack) {
            return stack.is((Item)CultivationItems.BRUSH_AND_INK.get());
         }

         public void setChanged() {
            super.setChanged();
            TalismanMakingMenu.this.slotsChanged(TalismanMakingMenu.this.craftingContainer);
         }
      }));
      if (this.entity.level() instanceof ServerLevel serverLevel) {
         TalismanMakingData data = TalismanMakingData.get(serverLevel);
         ItemStack savedTool = data.getTool();
         if (savedTool.is((Item)CultivationItems.BRUSH_AND_INK.get()) && this.internal.getStackInSlot(8).isEmpty()) {
            this.internal.insertItem(8, savedTool.copy(), false);
         }

         for (ItemStack legacyTool : data.takeLegacyTools()) {
            inv.placeItemBackInInventory(legacyTool);
         }
      }

      for (int si = 0; si < 3; si++) {
         for (int sj = 0; sj < 9; sj++) {
            this.addSlot(new Slot(inv, sj + (si + 1) * 9, 13 + sj * 18, 128 + si * 18));
         }
      }

      for (int si = 0; si < 9; si++) {
         this.addSlot(new Slot(inv, si, 13 + si * 18, 186));
      }

      this.updateCraftingContainer();
   }

   public TalismanMakingMenu(int id, Inventory inv, Player player) {
      this(id, inv, (FriendlyByteBuf)null);
   }

   private void updateCraftingContainer() {
      for (int i = 0; i < 7; i++) {
         ItemStack stackInSlot = this.internal.getStackInSlot(i);
         if (!stackInSlot.isEmpty()) {
            ItemStack singleItem = stackInSlot.copy();
            singleItem.setCount(1);
            this.craftingContainer.setItem(i, singleItem);
         } else {
            this.craftingContainer.setItem(i, ItemStack.EMPTY);
         }
      }
   }

   private Optional<TalismanRecipe> getCurrentRecipe() {
      return this.world
         .getRecipeManager()
         .getRecipeFor((RecipeType)RecipeRegistry.TALISMAN_TYPE.get(), new TalismanRecipe.Input(this.craftingContainer), this.world)
         .map(RecipeHolder::value);
   }

   private int getMaxCraftAmount() {
      int maxAmount = Integer.MAX_VALUE;
      Optional<TalismanRecipe> optionalRecipe = this.getCurrentRecipe();
      if (optionalRecipe.isEmpty()) {
         return 0;
      }

      for (int i = 0; i < 7; i++) {
         ItemStack stack = this.internal.getStackInSlot(i);
         if (!((Ingredient)optionalRecipe.get().getIngredients().get(i)).test(stack)) {
            return 0;
         }

         if (!stack.isEmpty()) {
            maxAmount = Math.min(maxAmount, stack.getCount());
         } else if (!((Ingredient)optionalRecipe.get().getIngredients().get(i)).isEmpty()) {
            return 0;
         }
      }

      return maxAmount;
   }

   private void consumeIngredients() {
      if (!this.world.isClientSide()) {
         Optional<TalismanRecipe> optionalRecipe = this.getCurrentRecipe();
         if (!optionalRecipe.isEmpty()) {
            TalismanRecipe recipe = optionalRecipe.get();

            for (int i = 0; i < 7; i++) {
               if (!((Ingredient)recipe.getIngredients().get(i)).isEmpty() && this.internal.getStackInSlot(i).isEmpty()) {
                  return;
               }
            }

            for (int i = 0; i < 7; i++) {
               if (!((Ingredient)recipe.getIngredients().get(i)).isEmpty()) {
                  this.internal.extractItem(i, 1, false);
               }
            }

            if (recipe.consumesToolDurability()) {
               this.damageTool(1);
            }

            this.updateCraftingContainer();
         }
      }
   }

   private void damageTool(int amount) {
      ItemStack tool = this.internal.getStackInSlot(8);
      if (amount > 0 && tool.is((Item)CultivationItems.BRUSH_AND_INK.get()) && tool.isDamageableItem()) {
         int damage = tool.getDamageValue() + amount;
         if (damage >= tool.getMaxDamage()) {
            this.internal.extractItem(8, 1, false);
         } else {
            tool.setDamageValue(damage);
         }
      }
   }

   private ItemStack craftItems(int amount) {
      if (!this.world.isClientSide() && amount > 0) {
         Optional<TalismanRecipe> optionalRecipe = this.getCurrentRecipe();
         if (optionalRecipe.isEmpty()) {
            return ItemStack.EMPTY;
         }

         TalismanRecipe recipe = optionalRecipe.get();
         int maxPossible = this.getMaxCraftAmount();
         int actualAmount = Math.min(amount, maxPossible);
         if (actualAmount <= 0) {
            return ItemStack.EMPTY;
         }

         ItemStack result = recipe.getOutput().copy();
         result.setCount(result.getCount() * actualAmount);

         for (int i = 0; i < 7; i++) {
            if (!((Ingredient)recipe.getIngredients().get(i)).isEmpty()) {
               this.internal.extractItem(i, actualAmount, false);
            }
         }

         if (recipe.consumesToolDurability()) {
            this.damageTool(actualAmount);
         }

         this.updateCraftingContainer();
         return result;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public int getCurrentMaxCraftAmount() {
      return this.currentMaxCraftAmount;
   }

   public void slotsChanged(Container container) {
      if (!this.world.isClientSide()) {
         ItemStack tool = this.internal.getStackInSlot(8);
         if (tool.is((Item)CultivationItems.BRUSH_AND_INK.get()) && (!tool.isDamageableItem() || tool.getDamageValue() < tool.getMaxDamage())) {
            Optional<TalismanRecipe> optionalRecipe = this.getCurrentRecipe();
            if (optionalRecipe.isPresent()) {
               TalismanRecipe recipe = optionalRecipe.get();
               ItemStack output = recipe.getOutput().copy();
               this.currentMaxCraftAmount = this.getMaxCraftAmount();
               this.internal.extractItem(7, this.internal.getStackInSlot(7).getCount(), false);
               this.internal.insertItem(7, output, false);
            } else {
               this.internal.extractItem(7, this.internal.getStackInSlot(7).getCount(), false);
               this.currentMaxCraftAmount = 0;
            }
         } else {
            this.internal.extractItem(7, this.internal.getStackInSlot(7).getCount(), false);
            this.currentMaxCraftAmount = 0;
         }
      }
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      if (playerIn.level() instanceof ServerLevel serverLevel) {
         TalismanMakingData data = TalismanMakingData.get(serverLevel);
         data.setTool(this.internal.getStackInSlot(8));
      }

      if (!this.bound && playerIn instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.isAlive() && !serverPlayer.hasDisconnected()) {
            for (int i = 0; i < this.internal.getSlots(); i++) {
               if (i != 7 && i != 8) {
                  playerIn.getInventory().placeItemBackInInventory(this.internal.extractItem(i, this.internal.getStackInSlot(i).getCount(), false));
               }
            }
         } else {
            for (int j = 0; j < this.internal.getSlots(); j++) {
               if (j != 7 && j != 8) {
                  playerIn.drop(this.internal.extractItem(j, this.internal.getStackInSlot(j).getCount(), false), false);
               }
            }
         }
      }
   }

   public boolean clickMenuButton(Player player, int buttonId) {
      if (buttonId == 0) {
         ItemStack resultItem = this.internal.getStackInSlot(7);
         if (!resultItem.isEmpty()) {
            this.consumeIngredients();
            if (!player.getInventory().add(resultItem.copy())) {
               player.drop(resultItem.copy(), false);
            }

            this.internal.extractItem(7, this.internal.getStackInSlot(7).getCount(), false);
            return true;
         }
      } else if (buttonId == 1) {
         int maxAmount = this.currentMaxCraftAmount;
         if (maxAmount > 0) {
            ItemStack crafted = this.craftItems(maxAmount);
            if (!crafted.isEmpty()) {
               if (!player.getInventory().add(crafted)) {
                  player.drop(crafted, false);
               }

               return true;
            }
         }
      }

      return false;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player playerIn, int index) {
      ItemStack original = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack source = slot.getItem();
         original = source.copy();
         if (index == 7) {
            int maxAmount = this.currentMaxCraftAmount;
            if (maxAmount > 1 && playerIn.isShiftKeyDown()) {
               ItemStack crafted = this.craftItems(maxAmount);
               if (!crafted.isEmpty()) {
                  if (!this.moveItemStackTo(crafted, 9, this.slots.size(), true) && !playerIn.getInventory().add(crafted)) {
                     playerIn.drop(crafted, false);
                  }

                  this.internal.extractItem(7, this.internal.getStackInSlot(7).getCount(), false);
                  return ItemStack.EMPTY;
               }
            }

            if (!this.moveItemStackTo(source, 9, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(source, original);
         } else if (index < 9) {
            if (!this.moveItemStackTo(source, 9, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(source, original);
         } else {
            boolean movedToMenu;
            if (source.is((Item)CultivationItems.BRUSH_AND_INK.get())) {
               movedToMenu = this.moveItemStackTo(source, 8, 9, false);
            } else {
               movedToMenu = this.moveItemStackTo(source, 0, 7, false);
            }

            if (!movedToMenu) {
               if (index < 36) {
                  if (!this.moveItemStackTo(source, 36, 45, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (!this.moveItemStackTo(source, 9, 36, false)) {
                  return ItemStack.EMPTY;
               }
            }
         }

         if (source.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (source.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, source);
      }

      return original;
   }

   protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
      boolean flag = false;
      int i = p_38905_;
      if (p_38907_) {
         i = p_38906_ - 1;
      }

      if (p_38904_.isStackable()) {
         while (!p_38904_.isEmpty() && (p_38907_ ? i >= p_38905_ : i < p_38906_)) {
            Slot slot = (Slot)this.slots.get(i);
            ItemStack itemstack = slot.getItem();
            if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameComponents(p_38904_, itemstack)) {
               int j = itemstack.getCount() + p_38904_.getCount();
               int maxSize = Math.min(slot.getMaxStackSize(), p_38904_.getMaxStackSize());
               if (j <= maxSize) {
                  p_38904_.setCount(0);
                  itemstack.setCount(j);
                  slot.set(itemstack);
                  flag = true;
               } else if (itemstack.getCount() < maxSize) {
                  p_38904_.shrink(maxSize - itemstack.getCount());
                  itemstack.setCount(maxSize);
                  slot.set(itemstack);
                  flag = true;
               }
            }

            if (p_38907_) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (!p_38904_.isEmpty()) {
         if (p_38907_) {
            i = p_38906_ - 1;
         } else {
            i = p_38905_;
         }

         while (p_38907_ ? i >= p_38905_ : i < p_38906_) {
            Slot slot1 = (Slot)this.slots.get(i);
            ItemStack itemstack1 = slot1.getItem();
            if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
               if (p_38904_.getCount() > slot1.getMaxStackSize()) {
                  slot1.set(p_38904_.split(slot1.getMaxStackSize()));
               } else {
                  slot1.set(p_38904_.split(p_38904_.getCount()));
               }

               slot1.setChanged();
               flag = true;
               break;
            }

            if (p_38907_) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   public Map<Integer, Slot> get() {
      return this.customSlots;
   }
}
