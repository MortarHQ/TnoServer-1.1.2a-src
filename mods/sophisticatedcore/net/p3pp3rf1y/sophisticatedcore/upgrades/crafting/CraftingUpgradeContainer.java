package net.p3pp3rf1y.sophisticatedcore.upgrades.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.CraftingInput.Positioned;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;

public class CraftingUpgradeContainer extends UpgradeContainerBase<CraftingUpgradeWrapper, CraftingUpgradeContainer> implements ICraftingContainer {
   private static final String DATA_SHIFT_CLICK_INTO_STORAGE = "shiftClickIntoStorage";
   private static final String DATA_SELECT_RESULT = "selectResult";
   private static final String DATA_REFILL_CRAFTING_GRID = "refill_crafting_grid";
   private final ResultContainer craftResult = new ResultContainer();
   private final CraftingItemHandler craftMatrix;
   private final ResultSlot craftingResultSlot;
   @Nullable
   private RecipeHolder<CraftingRecipe> lastRecipe = null;
   private List<RecipeHolder<CraftingRecipe>> matchedCraftingRecipes = new ArrayList<>();
   private final List<ItemStack> matchedCraftingResults = new ArrayList<>();
   private int selectedCraftingResultIndex = 0;

   public CraftingUpgradeContainer(
      final Player player,
      int upgradeContainerId,
      final CraftingUpgradeWrapper upgradeWrapper,
      UpgradeContainerType<CraftingUpgradeWrapper, CraftingUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);

      int slot;
      for (slot = 0; slot < upgradeWrapper.getInventory().getSlots(); slot++) {
         this.slots
            .add(
               new SlotSuppliedHandler(upgradeWrapper::getInventory, slot, -100, -100) {
                  @Override
                  public void setChanged() {
                     super.setChanged();
                     CraftingUpgradeContainer.this.updateCraftingResult(
                        player.level(),
                        player,
                        CraftingUpgradeContainer.this.craftMatrix,
                        CraftingUpgradeContainer.this.craftResult,
                        CraftingUpgradeContainer.this.craftingResultSlot
                     );
                     CraftingUpgradeContainer.this.craftMatrix.setChanged();
                  }

                  public boolean mayPickup(Player playerx) {
                     return this.getItem().isEmpty() || super.mayPickup(player);
                  }
               }
            );
      }

      this.craftMatrix = new CraftingItemHandler(upgradeWrapper::getInventory, this::onCraftMatrixChanged);
      this.craftingResultSlot = new ResultSlot(player, this.craftMatrix, this.craftResult, slot, -100, -100) {
         public void onTake(Player thePlayer, ItemStack stack) {
            if (!thePlayer.level().isClientSide()) {
               ItemStack remainingStack = this.getItem();
               this.checkTakeAchievements(stack);
               CommonHooks.setCraftingPlayer(thePlayer);
               List<ItemStack> remainingItems;
               if (CraftingUpgradeContainer.this.lastRecipe != null
                  && ((CraftingRecipe)CraftingUpgradeContainer.this.lastRecipe.value())
                     .matches(CraftingUpgradeContainer.this.craftMatrix.asCraftInput(), player.level())) {
                  remainingItems = ((CraftingRecipe)CraftingUpgradeContainer.this.lastRecipe.value())
                     .getRemainingItems(CraftingUpgradeContainer.this.craftMatrix.asCraftInput());
               } else {
                  remainingItems = NonNullList.withSize(CraftingUpgradeContainer.this.craftMatrix.getContainerSize(), ItemStack.EMPTY);
               }

               CommonHooks.setCraftingPlayer(null);
               Positioned craftingInput = CraftingUpgradeContainer.this.craftMatrix.asPositionedCraftInput();
               int remaininItemsIndex = 0;

               for (int row = craftingInput.top(); row < craftingInput.top() + craftingInput.input().height(); row++) {
                  for (int col = craftingInput.left(); col < craftingInput.left() + craftingInput.input().width(); col++) {
                     int i = row * CraftingUpgradeContainer.this.craftMatrix.getWidth() + col;
                     if (remaininItemsIndex >= 9) {
                        this.logErrorAndDropRemainingItems(remaininItemsIndex, remainingItems);
                        break;
                     }

                     ItemStack recipeInputStack = CraftingUpgradeContainer.this.craftMatrix.getItem(i);
                     ItemStack remainingItemStack = remainingItems.get(remaininItemsIndex);
                     if (!recipeInputStack.isEmpty()) {
                        if (remainingItemStack.isEmpty()
                           && CraftingUpgradeContainer.this.shouldRefillCraftingGrid()
                           && upgradeWrapper.extractFromStorageOrPlayer(player, recipeInputStack)) {
                           CraftingUpgradeContainer.this.onCraftMatrixChanged(CraftingUpgradeContainer.this.craftMatrix);
                        } else {
                           CraftingUpgradeContainer.this.craftMatrix.removeItem(i, 1);
                        }

                        recipeInputStack = CraftingUpgradeContainer.this.craftMatrix.getItem(i);
                     }

                     if (!remainingItemStack.isEmpty()) {
                        if (recipeInputStack.isEmpty()) {
                           CraftingUpgradeContainer.this.craftMatrix.setItem(i, remainingItemStack);
                        } else if (ItemStack.isSameItemSameComponents(recipeInputStack, remainingItemStack)) {
                           remainingItemStack.grow(recipeInputStack.getCount());
                           CraftingUpgradeContainer.this.craftMatrix.setItem(i, remainingItemStack);
                        } else if (!player.getInventory().add(remainingItemStack)) {
                           player.drop(remainingItemStack, false);
                        }
                     }

                     remaininItemsIndex++;
                  }
               }

               if (!remainingStack.isEmpty()) {
                  player.drop(remainingStack, false);
               }

               if (CraftingUpgradeContainer.this.matchedCraftingRecipes.isEmpty() && player.level().isClientSide()) {
                  CraftingUpgradeContainer.this.lastRecipe = null;
               }
            }
         }

         private void logErrorAndDropRemainingItems(int remaininItemsIndex, List<ItemStack> remainingItems) {
            for (int j = remaininItemsIndex; j < remainingItems.size(); j++) {
               ItemStack remaining = remainingItems.get(j);
               if (!remaining.isEmpty()) {
                  player.drop(remaining, false);
               }
            }

            SophisticatedCore.LOGGER
               .error(
                  "Recipe "
                     + (CraftingUpgradeContainer.this.lastRecipe != null ? CraftingUpgradeContainer.this.lastRecipe.id() : "[unknown]")
                     + " returned more than 9 remaining items, ignoring the rest!"
               );
         }

         public void setChanged() {
            super.setChanged();
            if (player.level().isClientSide()) {
               CraftingUpgradeContainer.this.matchedCraftingResults.clear();
               if (!this.getItem().isEmpty()) {
                  CraftingUpgradeContainer.this.matchedCraftingRecipes = RecipeHelper.safeGetRecipesFor(
                     RecipeType.CRAFTING, CraftingUpgradeContainer.this.craftMatrix.asCraftInput(), player.level()
                  );
                  int resultIndex = 0;

                  for (RecipeHolder<CraftingRecipe> craftingRecipe : CraftingUpgradeContainer.this.matchedCraftingRecipes) {
                     ItemStack result = ((CraftingRecipe)craftingRecipe.value())
                        .assemble(CraftingUpgradeContainer.this.craftMatrix.asCraftInput(), player.level().registryAccess());
                     CraftingUpgradeContainer.this.matchedCraftingResults.add(result);
                     if (ItemStack.isSameItemSameComponents(this.getItem(), result)) {
                        CraftingUpgradeContainer.this.selectedCraftingResultIndex = resultIndex;
                        CraftingUpgradeContainer.this.lastRecipe = craftingRecipe;
                     }

                     resultIndex++;
                  }
               } else {
                  CraftingUpgradeContainer.this.matchedCraftingRecipes = new ArrayList<>();
               }
            }
         }
      };
      this.slots.add(this.craftingResultSlot);
   }

   @Override
   public void onInit() {
      super.onInit();
      this.onCraftMatrixChanged(this.craftMatrix);
   }

   private void onCraftMatrixChanged(Container iInventory) {
      this.updateCraftingResult(this.player.level(), this.player, this.craftMatrix, this.craftResult, this.craftingResultSlot);
   }

   private void updateCraftingResult(Level level, Player player, CraftingContainer inventory, ResultContainer inventoryResult, ResultSlot craftingResultSlot) {
      if (!level.isClientSide) {
         ServerPlayer serverplayerentity = (ServerPlayer)player;
         ItemStack itemstack = ItemStack.EMPTY;
         CraftingInput craftInput = inventory.asCraftInput();
         if (!craftInput.isEmpty()) {
            if (this.lastRecipe != null && ((CraftingRecipe)this.lastRecipe.value()).matches(craftInput, level)) {
               itemstack = ((CraftingRecipe)this.lastRecipe.value()).assemble(craftInput, level.registryAccess());
            } else {
               List<RecipeHolder<CraftingRecipe>> recipes = RecipeHelper.safeGetRecipesFor(RecipeType.CRAFTING, craftInput, level);
               if (!recipes.isEmpty()) {
                  this.matchedCraftingRecipes = recipes;
                  this.matchedCraftingResults.clear();
                  this.selectedCraftingResultIndex = 0;
                  RecipeHolder<CraftingRecipe> craftingRecipe = this.matchedCraftingRecipes.get(0);
                  if (inventoryResult.setRecipeUsed(level, serverplayerentity, craftingRecipe)) {
                     this.lastRecipe = craftingRecipe;
                     itemstack = ((CraftingRecipe)this.lastRecipe.value()).assemble(craftInput, level.registryAccess());
                     this.matchedCraftingResults.add(itemstack.copy());
                  } else {
                     this.lastRecipe = null;
                  }

                  for (int i = 1; i < this.matchedCraftingRecipes.size(); i++) {
                     this.matchedCraftingResults.add(((CraftingRecipe)this.matchedCraftingRecipes.get(i).value()).assemble(craftInput, level.registryAccess()));
                  }
               }
            }
         }

         craftingResultSlot.set(itemstack);
      }
   }

   public List<ItemStack> getMatchedCraftingResults() {
      return this.matchedCraftingResults;
   }

   public void selectNextCraftingResult() {
      if (this.matchedCraftingResults.size() > 1) {
         this.selectCraftingResult((this.selectedCraftingResultIndex + 1) % this.matchedCraftingResults.size());
      }
   }

   public void selectPreviousCraftingResult() {
      if (this.matchedCraftingResults.size() > 1) {
         this.selectCraftingResult((this.selectedCraftingResultIndex + this.matchedCraftingResults.size() - 1) % this.matchedCraftingResults.size());
      }
   }

   public void selectCraftingResult(int resultIndex) {
      if (resultIndex >= 0 && resultIndex < this.matchedCraftingResults.size()) {
         this.selectedCraftingResultIndex = resultIndex;
         this.lastRecipe = this.matchedCraftingRecipes.get(resultIndex);
         if (this.player instanceof ServerPlayer serverPlayer) {
            ItemStack result = this.matchedCraftingResults.get(resultIndex).copy();
            this.craftingResultSlot.set(result);
            this.craftResult.setRecipeUsed(this.player.level(), serverPlayer, this.lastRecipe);
         } else {
            this.sendDataToServer(() -> NBTHelper.putInt(new CompoundTag(), "selectResult", resultIndex));
         }
      }
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("shiftClickIntoStorage")) {
         this.setShiftClickIntoStorage(data.getBoolean("shiftClickIntoStorage"));
      } else if (data.contains("selectResult")) {
         this.selectCraftingResult(data.getInt("selectResult"));
      }

      if (data.contains("refill_crafting_grid")) {
         this.setRefillCraftingGrid(data.getBoolean("refill_crafting_grid"));
      }
   }

   @Override
   public ItemStack getSlotStackToTransfer(Slot slot) {
      if (slot == this.craftingResultSlot) {
         ItemStack slotStack = slot.getItem();
         slotStack.getItem().onCraftedBy(slotStack, this.player.level(), this.player);
         return slotStack;
      } else {
         return super.getSlotStackToTransfer(slot);
      }
   }

   @Override
   public List<Slot> getRecipeSlots() {
      return this.slots.subList(0, 9);
   }

   @Override
   public Container getCraftMatrix() {
      return this.craftMatrix;
   }

   @Override
   public void setRecipeUsed(ResourceLocation recipeId) {
      if (this.lastRecipe == null || !this.lastRecipe.id().equals(recipeId)) {
         this.player
            .level()
            .getRecipeManager()
            .byKey(recipeId)
            .filter(r -> r.value().getType() == RecipeType.CRAFTING)
            .map(r -> (RecipeHolder)r)
            .ifPresent(recipe -> {
               this.lastRecipe = (RecipeHolder<CraftingRecipe>)recipe;

               for (int i = 0; i < this.matchedCraftingRecipes.size(); i++) {
                  if (this.matchedCraftingRecipes.get(i).id().equals(recipeId)) {
                     this.selectCraftingResult(i);
                     return;
                  }
               }
            });
      }
   }

   @Override
   public RecipeType<?> getRecipeType() {
      return RecipeType.CRAFTING;
   }

   public boolean shouldShiftClickIntoStorage() {
      return this.upgradeWrapper.shouldShiftClickIntoStorage();
   }

   public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
      this.upgradeWrapper.setShiftClickIntoStorage(shiftClickIntoStorage);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shiftClickIntoStorage", shiftClickIntoStorage));
   }

   @Override
   public boolean shouldRefillCraftingGrid() {
      return this.upgradeWrapper.shouldRefillCraftingGridNBT();
   }

   public void setRefillCraftingGrid(boolean replenish) {
      this.upgradeWrapper.setRefillCraftingGridNBT(replenish);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "refill_crafting_grid", replenish));
   }

   @Override
   public boolean mergeIntoStorageFirst(Slot slot) {
      return !(slot instanceof ResultSlot) || this.shouldShiftClickIntoStorage();
   }

   @Override
   public boolean allowsPickupAll(Slot slot) {
      return slot != this.craftingResultSlot;
   }
}
