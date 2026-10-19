package com.mortal_cultivation.gui.menu;

import com.mortal_cultivation.block.AncientScholarTableBlock;
import com.mortal_cultivation.block.entity.AncientScholarTableBlockEntity;
import com.mortal_cultivation.data.recipe.TalismanRecipe;
import com.mortal_cultivation.init.MortalCultivationModMenus;
import com.mortal_cultivation.item.TalismanManualItem;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.RecipeRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AncientScholarTableMenu extends AbstractContainerMenu {
   public static final int PAGE_ONE_BUTTON = 0;
   public static final int DECIPHER_PAGE_BUTTON = 1;
   public static final int TALISMAN_PAGE_BUTTON = 2;
   public static final int ANALYSIS_INPUT_X = 53;
   public static final int ANALYSIS_INPUT_Y = 54;
   public static final int DECIPHER_BOOK_X = 23;
   public static final int DECIPHER_BOOK_Y = 72;
   public static final int MAIN_OUTPUT_X = 127;
   public static final int MAIN_OUTPUT_Y = 54;
   private static final int[] TALISMAN_INPUT_X = new int[]{76, 103, 130, 142, 121, 83, 62};
   private static final int[] TALISMAN_INPUT_Y = new int[]{29, 20, 29, 57, 83, 83, 57};
   private static final int TALISMAN_RESULT_X = 102;
   private static final int TALISMAN_RESULT_Y = 55;
   private static final int TALISMAN_TOOL_X = 48;
   private static final int TALISMAN_TOOL_Y = 84;
   public static final int ANALYSIS_INPUT_CONTAINER_SLOT = 0;
   public static final int MAIN_OUTPUT_CONTAINER_SLOT = 1;
   public static final int DECIPHER_BOOK_CONTAINER_SLOT = 2;
   public static final int TALISMAN_INPUT_CONTAINER_SLOT_START = 3;
   public static final int TALISMAN_INPUT_CONTAINER_SLOT_END = 10;
   public static final int TALISMAN_RESULT_CONTAINER_SLOT = 10;
   public static final int TALISMAN_TOOL_CONTAINER_SLOT = 11;
   private static final int ANALYSIS_INPUT_HANDLER_SLOT = 0;
   private static final int MAIN_OUTPUT_HANDLER_SLOT = 1;
   private static final int DECIPHER_BOOK_HANDLER_SLOT = 2;
   private static final int CONTAINER_SLOT_COUNT = 12;
   private static final int PLAYER_INVENTORY_START = 12;
   private static final int PLAYER_INVENTORY_END = 39;
   private static final int HOTBAR_START = 39;
   private static final int HOTBAR_END = 48;
   public static final int PAGE_DATA_INDEX = 2;
   private final Level level;
   private final BlockPos tablePos;
   private final ContainerData tableData;
   private final ContainerData data;
   private final IItemHandler tableHandler;
   private final ResultContainer talismanResult = new ResultContainer();
   private final Slot talismanResultSlot;
   private final Player player;
   private int selectedPage;

   public AncientScholarTableMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
      this(containerId, inventory, readTablePos(extraData));
   }

   public AncientScholarTableMenu(int containerId, Inventory inventory, AncientScholarTableBlockEntity table) {
      this(containerId, inventory, table.getBlockPos(), table.getItemHandler(), table.getContainerData());
   }

   private AncientScholarTableMenu(int containerId, Inventory inventory, BlockPos tablePos) {
      this(containerId, inventory, tablePos, resolveHandler(inventory, tablePos), resolveData(inventory, tablePos));
   }

   private AncientScholarTableMenu(int containerId, Inventory inventory, BlockPos tablePos, IItemHandler handler, ContainerData data) {
      super((MenuType)MortalCultivationModMenus.ANCIENT_SCHOLAR_TABLE.get(), containerId);
      this.player = inventory.player;
      this.level = inventory.player.level();
      this.tablePos = tablePos;
      this.tableData = data;
      this.selectedPage = normalizePage(data.get(2));
      if (this.selectedPage == 2 && !canAccessTalismanMaking(this.player)) {
         this.selectedPage = 0;
         if (!this.level.isClientSide) {
            this.tableData.set(2, 0);
         }
      }

      this.data = new ContainerData() {
         public int get(int index) {
            return index == 2 ? AncientScholarTableMenu.this.selectedPage : AncientScholarTableMenu.this.tableData.get(index);
         }

         public void set(int index, int value) {
            if (index == 2) {
               AncientScholarTableMenu.this.selectedPage = AncientScholarTableMenu.normalizePage(value);
            } else {
               AncientScholarTableMenu.this.tableData.set(index, value);
            }
         }

         public int getCount() {
            return AncientScholarTableMenu.this.tableData.getCount();
         }
      };
      this.tableHandler = handler;
      this.addSlot(new SlotItemHandler(handler, 0, 53, 54) {
         public boolean mayPlace(ItemStack stack) {
            return this.isActive() && stack.is((Item)CultivationItems.TORN_PAGE.get());
         }

         public boolean mayPickup(Player player) {
            return this.isActive() && super.mayPickup(player);
         }

         public boolean isActive() {
            return AncientScholarTableMenu.this.isAnalysisPage();
         }
      });
      this.addSlot(new SlotItemHandler(handler, 1, 127, 54) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }

         public boolean mayPickup(Player player) {
            return this.isActive() && super.mayPickup(player);
         }

         public boolean isActive() {
            return AncientScholarTableMenu.this.isAnalysisPage();
         }
      });
      this.addSlot(new SlotItemHandler(handler, 2, 23, 72) {
         public boolean mayPlace(ItemStack stack) {
            return this.isActive() && stack.is((Item)CultivationItems.ANCIENT_BOOK.get());
         }

         public boolean mayPickup(Player player) {
            return this.isActive() && super.mayPickup(player);
         }

         public boolean isActive() {
            return AncientScholarTableMenu.this.isDecipherPage();
         }

         public int getMaxStackSize() {
            return 1;
         }
      });

      for (int recipeSlot = 0; recipeSlot < 7; recipeSlot++) {
         int handlerSlot = 3 + recipeSlot;
         int x = TALISMAN_INPUT_X[recipeSlot];
         int y = TALISMAN_INPUT_Y[recipeSlot];
         this.addSlot(new SlotItemHandler(handler, handlerSlot, x, y) {
            public boolean mayPlace(ItemStack stack) {
               return this.isActive() && super.mayPlace(stack);
            }

            public boolean mayPickup(Player player) {
               return this.isActive() && super.mayPickup(player);
            }

            public boolean isActive() {
               return AncientScholarTableMenu.this.isTalismanPage();
            }

            public void setChanged() {
               super.setChanged();
               AncientScholarTableMenu.this.refreshTalismanResult();
            }
         });
      }

      this.talismanResultSlot = this.addSlot(
         new Slot(this.talismanResult, 0, 102, 55) {
            public boolean mayPlace(ItemStack stack) {
               return false;
            }

            public boolean mayPickup(Player player) {
               return this.isActive()
                  && AncientScholarTableMenu.this.canCarriedStackAcceptFullResult()
                  && (player.level().isClientSide || AncientScholarTableMenu.this.canCraftTalisman());
            }

            public boolean isActive() {
               return AncientScholarTableMenu.this.isTalismanPage();
            }

            public ItemStack remove(int amount) {
               ItemStack result = this.getItem();
               return result.isEmpty() ? ItemStack.EMPTY : super.remove(result.getCount());
            }

            public void onTake(Player player, ItemStack stack) {
               AncientScholarTableMenu.this.completeTalismanCraft(player, stack);
               super.onTake(player, stack);
            }
         }
      );
      this.addSlot(new SlotItemHandler(handler, 10, 48, 84) {
         public boolean mayPlace(ItemStack stack) {
            return this.isActive() && super.mayPlace(stack);
         }

         public boolean mayPickup(Player player) {
            return this.isActive() && super.mayPickup(player);
         }

         public boolean isActive() {
            return AncientScholarTableMenu.this.isTalismanPage();
         }

         public void setChanged() {
            super.setChanged();
            AncientScholarTableMenu.this.refreshTalismanResult();
         }
      });

      for (int row = 0; row < 3; row++) {
         for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column + (row + 1) * 9, 13 + column * 18, 128 + row * 18));
         }
      }

      for (int column = 0; column < 9; column++) {
         this.addSlot(new Slot(inventory, column, 13 + column * 18, 186));
      }

      this.addDataSlots(this.data);
      this.refreshTalismanResult();
   }

   public boolean stillValid(Player player) {
      BlockState state = this.level.getBlockState(this.tablePos);
      return state.is((Block)BlockRegistry.ANCIENT_SCHOLAR_TABLE.get())
         && state.getValue(AncientScholarTableBlock.PART) == AncientScholarTableBlock.TablePart.MAIN
         && player.distanceToSqr(this.tablePos.getX() + 0.5, this.tablePos.getY() + 0.5, this.tablePos.getZ() + 0.5) <= 64.0;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      if (index >= 0 && index < this.slots.size()) {
         Slot slot = (Slot)this.slots.get(index);
         if (slot.isActive() && slot.hasItem()) {
            ItemStack sourceStack = slot.getItem();
            ItemStack movedStack = sourceStack.copy();
            if (index == 10) {
               if (this.canCraftTalisman(movedStack)
                  && this.canFullyMoveToPlayerInventory(movedStack)
                  && this.moveItemStackTo(sourceStack, 12, 48, true)
                  && sourceStack.isEmpty()) {
                  slot.set(ItemStack.EMPTY);
                  slot.onTake(player, movedStack);
                  return movedStack;
               } else {
                  return ItemStack.EMPTY;
               }
            } else {
               if (index < 12) {
                  if (!this.moveItemStackTo(sourceStack, 12, 48, true)) {
                     return ItemStack.EMPTY;
                  }
               } else if (this.isAnalysisPage() && sourceStack.is((Item)CultivationItems.TORN_PAGE.get())) {
                  if (!this.moveItemStackTo(sourceStack, 0, 1, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (this.isDecipherPage() && sourceStack.is((Item)CultivationItems.ANCIENT_BOOK.get())) {
                  if (!this.moveItemStackTo(sourceStack, 2, 3, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (this.isTalismanPage() && sourceStack.is((Item)CultivationItems.BRUSH_AND_INK.get())) {
                  if (!this.moveItemStackTo(sourceStack, 11, 12, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (this.isTalismanPage()) {
                  if (!this.moveItemStackTo(sourceStack, 3, 10, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (index >= 12 && index < 39) {
                  if (!this.moveItemStackTo(sourceStack, 39, 48, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (index >= 39 && index < 48 && !this.moveItemStackTo(sourceStack, 12, 39, false)) {
                  return ItemStack.EMPTY;
               }

               if (sourceStack.isEmpty()) {
                  slot.set(ItemStack.EMPTY);
               } else {
                  slot.setChanged();
               }

               if (sourceStack.getCount() == movedStack.getCount()) {
                  return ItemStack.EMPTY;
               }

               slot.onTake(player, sourceStack);
               return movedStack;
            }
         } else {
            return ItemStack.EMPTY;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   private static IItemHandler resolveHandler(Inventory inventory, BlockPos tablePos) {
      return inventory.player.level().getBlockEntity(tablePos) instanceof AncientScholarTableBlockEntity table
         ? table.getItemHandler()
         : new ItemStackHandler(11);
   }

   private static ContainerData resolveData(Inventory inventory, BlockPos tablePos) {
      return (ContainerData)(inventory.player.level().getBlockEntity(tablePos) instanceof AncientScholarTableBlockEntity table
         ? table.getContainerData()
         : new SimpleContainerData(54));
   }

   public int getProgressScaled(int height) {
      int progress = this.data.get(0);
      int total = this.data.get(1);
      return progress > 0 && total > 0 ? Math.max(0, Math.min(progress * height / total, height)) : 0;
   }

   public boolean clickMenuButton(Player player, int id) {
      if (id != 0 && id != 1 && id != 2) {
         int direction = -1;
         int cell = id - 16;
         if (cell < 0 || cell >= 24) {
            direction = 1;
            cell = id - 40;
         }

         if (cell < 0 || cell >= 24) {
            return false;
         }

         if (!this.isDecipherPage()) {
            return false;
         }

         if (player.level().isClientSide) {
            return true;
         }

         int rotationDirection = direction;
         int rotatedCell = cell;
         return this.resolveTable().map(table -> table.rotateDecipherCell(player, rotatedCell, rotationDirection)).orElse(false);
      } else {
         if (id == 2 && !canAccessTalismanMaking(player)) {
            return false;
         }

         this.data.set(2, id);
         if (!player.level().isClientSide) {
            this.tableData.set(2, id);
            this.resolveTable().ifPresent(table -> table.setChanged());
         }

         return true;
      }
   }

   public boolean isAnalysisPage() {
      return this.data.get(2) == 0;
   }

   public boolean isDecipherPage() {
      return this.data.get(2) == 1;
   }

   public boolean isTalismanPage() {
      return this.data.get(2) == 2 && this.canAccessTalismanMaking();
   }

   public boolean canAccessTalismanMaking() {
      return canAccessTalismanMaking(this.player);
   }

   public static boolean canAccessTalismanMaking(Player player) {
      if (player == null) {
         return false;
      } else {
         return TalismanManualItem.hasUnlockedTalismanMaking(player)
            ? true
            : RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() == CultivationRaces.TALISMAN_MASTER_RACE.get()).orElse(false);
      }
   }

   public boolean hasAncientBook() {
      return this.resolveTable().map(AncientScholarTableBlockEntity::hasAncientBook).orElse(false);
   }

   public boolean isDecipherSolved() {
      return this.resolveTable().map(AncientScholarTableBlockEntity::isDecipherSolved).orElse(false);
   }

   public boolean isDecipherCompleted() {
      return this.data.get(51) != 0;
   }

   public boolean isDecipherActive() {
      return this.data.get(52) != 0;
   }

   public int getDecipherStatus() {
      return this.data.get(53);
   }

   public int getDecipherRotation(int cell) {
      return cell >= 0 && cell < 24 ? this.data.get(3 + cell) & 3 : 0;
   }

   public int getDecipherMask(int cell) {
      return AncientScholarTableBlockEntity.rotateMask(this.getDecipherSolutionMask(cell), this.getDecipherRotation(cell));
   }

   public int getDecipherSolutionMask(int cell) {
      if (cell >= 0 && cell < 24) {
         int mask = this.data.get(27 + cell) & 15;
         return mask != 0 ? mask : AncientScholarTableBlockEntity.getLegacyDecipherSolutionMask(cell);
      } else {
         return 0;
      }
   }

   public boolean isDecipherCellRotatable(int cell) {
      return AncientScholarTableBlockEntity.isDecipherMaskRotatable(this.getDecipherSolutionMask(cell));
   }

   public static int decipherButtonForCell(int cell, int direction) {
      return AncientScholarTableBlockEntity.decipherButtonForCell(cell, direction);
   }

   public void broadcastChanges() {
      if (!this.level.isClientSide) {
         this.refreshTalismanResult();
      }

      super.broadcastChanges();
   }

   public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
      return slot != this.talismanResultSlot && super.canTakeItemForPickAll(stack, slot);
   }

   public void removed(Player player) {
      this.talismanResult.clearContent();
      super.removed(player);
   }

   private Optional<RecipeHolder<TalismanRecipe>> getCurrentTalismanRecipe() {
      SimpleContainer input = this.createTalismanInput();
      return this.level.getRecipeManager().getRecipeFor((RecipeType)RecipeRegistry.TALISMAN_TYPE.get(), new TalismanRecipe.Input(input), this.level);
   }

   private SimpleContainer createTalismanInput() {
      SimpleContainer input = new SimpleContainer(7);

      for (int recipeSlot = 0; recipeSlot < 7; recipeSlot++) {
         ItemStack stack = this.tableHandler.getStackInSlot(3 + recipeSlot);
         input.setItem(recipeSlot, stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1));
      }

      return input;
   }

   private void refreshTalismanResult() {
      Optional<RecipeHolder<TalismanRecipe>> recipe = this.hasUsableTalismanTool() ? this.getCurrentTalismanRecipe() : Optional.empty();
      ItemStack result = recipe.<ItemStack>map(holder -> ((TalismanRecipe)holder.value()).getOutput().copy()).orElse(ItemStack.EMPTY);
      ItemStack current = this.talismanResult.getItem(0);
      if (!sameStackAndCount(current, result)) {
         this.talismanResult.setItem(0, result);
      }

      this.talismanResult.setRecipeUsed(recipe.orElse(null));
   }

   private boolean canCraftTalisman() {
      return this.canCraftTalisman(this.talismanResult.getItem(0));
   }

   private boolean canCarriedStackAcceptFullResult() {
      ItemStack result = this.talismanResult.getItem(0);
      ItemStack carried = this.getCarried();
      return !result.isEmpty() && !carried.isEmpty()
         ? ItemStack.isSameItemSameComponents(carried, result) && carried.getCount() + result.getCount() <= carried.getMaxStackSize()
         : true;
   }

   private boolean canCraftTalisman(ItemStack expectedResult) {
      if (!expectedResult.isEmpty() && this.hasUsableTalismanTool()) {
         ItemStack currentResult = this.getCurrentTalismanRecipe().map(holder -> ((TalismanRecipe)holder.value()).getOutput().copy()).orElse(ItemStack.EMPTY);
         return sameStackAndCount(expectedResult, currentResult);
      } else {
         return false;
      }
   }

   private boolean hasUsableTalismanTool() {
      ItemStack tool = this.tableHandler.getStackInSlot(10);
      return tool.is((Item)CultivationItems.BRUSH_AND_INK.get()) && (!tool.isDamageableItem() || tool.getDamageValue() < tool.getMaxDamage());
   }

   private void completeTalismanCraft(Player player, ItemStack craftedStack) {
      if (!player.level().isClientSide && this.isTalismanPage() && this.stillValid(player) && this.canCraftTalisman(craftedStack)) {
         Optional<RecipeHolder<TalismanRecipe>> optionalRecipe = this.getCurrentTalismanRecipe();
         if (!optionalRecipe.isEmpty()) {
            RecipeHolder<TalismanRecipe> recipeHolder = optionalRecipe.get();
            TalismanRecipe recipe = (TalismanRecipe)recipeHolder.value();
            ItemStack recipeResult = recipe.getOutput();
            if (sameStackAndCount(craftedStack, recipeResult)) {
               List<ItemStack> usedInputs = new ArrayList<>(7);

               for (int recipeSlot = 0; recipeSlot < 7; recipeSlot++) {
                  int handlerSlot = 3 + recipeSlot;
                  usedInputs.add(this.tableHandler.getStackInSlot(handlerSlot).copyWithCount(1));
                  if (!((Ingredient)recipe.getIngredients().get(recipeSlot)).isEmpty()) {
                     this.tableHandler.extractItem(handlerSlot, 1, false);
                  }
               }

               if (!recipeResult.is((Item)CultivationItems.TALISMAN_PAPER.get())) {
                  this.damageTalismanTool();
               }

               craftedStack.onCraftedBy(player.level(), player, craftedStack.getCount());
               this.talismanResult.setRecipeUsed(recipeHolder);
               this.talismanResult.awardUsedRecipes(player, usedInputs);
               this.refreshTalismanResult();
            }
         }
      }
   }

   private void damageTalismanTool() {
      int slot = 10;
      ItemStack tool = this.tableHandler.getStackInSlot(slot);
      if (tool.is((Item)CultivationItems.BRUSH_AND_INK.get()) && tool.isDamageableItem()) {
         ItemStack damagedTool = tool.copy();
         int damage = damagedTool.getDamageValue() + 1;
         if (this.tableHandler instanceof IItemHandlerModifiable modifiable) {
            if (damage >= damagedTool.getMaxDamage()) {
               modifiable.setStackInSlot(slot, ItemStack.EMPTY);
            } else {
               damagedTool.setDamageValue(damage);
               modifiable.setStackInSlot(slot, damagedTool);
            }
         }
      }
   }

   private boolean canFullyMoveToPlayerInventory(ItemStack stack) {
      int remaining = stack.getCount();

      for (int slotIndex = 47; slotIndex >= 12 && remaining > 0; slotIndex--) {
         Slot slot = (Slot)this.slots.get(slotIndex);
         ItemStack existing = slot.getItem();
         if (slot.mayPlace(stack)) {
            if (existing.isEmpty()) {
               remaining -= Math.min(slot.getMaxStackSize(stack), stack.getMaxStackSize());
            } else if (ItemStack.isSameItemSameComponents(existing, stack)) {
               int limit = Math.min(slot.getMaxStackSize(stack), stack.getMaxStackSize());
               remaining -= Math.max(0, limit - existing.getCount());
            }
         }
      }

      return remaining <= 0;
   }

   private static boolean sameStackAndCount(ItemStack first, ItemStack second) {
      return first.isEmpty() && second.isEmpty()
         || !first.isEmpty() && !second.isEmpty() && first.getCount() == second.getCount() && ItemStack.isSameItemSameComponents(first, second);
   }

   private static int normalizePage(int page) {
      return page != 1 && page != 2 ? 0 : page;
   }

   private Optional<AncientScholarTableBlockEntity> resolveTable() {
      return this.level.getBlockEntity(this.tablePos) instanceof AncientScholarTableBlockEntity table ? Optional.of(table) : Optional.empty();
   }

   private static BlockPos readTablePos(FriendlyByteBuf extraData) {
      return extraData != null && extraData.readableBytes() >= 8 ? extraData.readBlockPos() : BlockPos.ZERO;
   }
}
