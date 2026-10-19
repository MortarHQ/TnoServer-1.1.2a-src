package net.p3pp3rf1y.sophisticatedcore.compat.chipped;

import com.google.common.base.Suppliers;
import earth.terrarium.chipped.common.recipes.ChippedRecipe;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IServerUpdater;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.CraftingItemHandler;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;
import net.p3pp3rf1y.sophisticatedcore.util.SimpleItemContent;

public class BlockTransformationRecipeContainer {
   private static final String DATA_SELECTED_RECIPE_INDEX = "selectedRecipeIndex";
   private final RecipeType<ChippedRecipe> recipeType;
   private final Slot inputSlot;
   private final IServerUpdater serverUpdater;
   private final Slot outputSlot;
   private final ResultContainer resultInventory = new ResultContainer();
   @Nullable
   private RecipeHolder<ChippedRecipe> recipe = null;
   private Supplier<List<ItemStack>> results = Collections::emptyList;
   private final DataSlot selectedRecipe = DataSlot.standalone();
   private Item inputItem = Items.AIR;
   private final CraftingItemHandler inputInventory;
   private Runnable inventoryUpdateListener = () -> {};
   private final Supplier<Optional<SimpleItemContent>> getLastSelectedResult;
   private final Consumer<ItemStack> setLastSelectedResult;
   private long lastOnTake = -1L;

   public BlockTransformationRecipeContainer(
      BlockTransformationUpgradeContainer upgradeContainer,
      RecipeType<ChippedRecipe> recipeType,
      Consumer<Slot> addSlot,
      IServerUpdater serverUpdater,
      ContainerLevelAccess worldPosCallable
   ) {
      this.inputSlot = new SlotSuppliedHandler(upgradeContainer.getUpgradeWrapper()::getInputInventory, 0, -1, -1) {
         @Override
         public void setChanged() {
            super.setChanged();
            BlockTransformationRecipeContainer.this.onCraftMatrixChanged(BlockTransformationRecipeContainer.this.inputInventory);
         }

         public ItemStack remove(int amount) {
            ItemStack ret = super.remove(amount);
            if (this.getItem().isEmpty()) {
               this.setChanged();
            }

            return ret;
         }
      };
      this.recipeType = recipeType;
      this.serverUpdater = serverUpdater;
      addSlot.accept(this.inputSlot);
      this.inputInventory = new CraftingItemHandler(upgradeContainer.getUpgradeWrapper()::getInputInventory, this::onCraftMatrixChanged);
      this.outputSlot = new BlockTransformationRecipeContainer.ResultSlot(worldPosCallable);
      addSlot.accept(this.outputSlot);
      this.getLastSelectedResult = upgradeContainer.getUpgradeWrapper()::getResult;
      this.setLastSelectedResult = upgradeContainer.getUpgradeWrapper()::setResult;
      this.onCraftMatrixChanged(this.inputInventory);
   }

   private void onCraftMatrixChanged(Container inventoryIn) {
      ItemStack itemstack = this.inputSlot.getItem();
      if (itemstack.getItem() != this.inputItem) {
         this.inputItem = itemstack.getItem();
         this.updateRecipe(inventoryIn, itemstack);
      }

      this.inventoryUpdateListener.run();
   }

   private void updateRecipe(Container inventory, ItemStack stack) {
      this.recipe = null;
      this.selectedRecipe.set(-1);
      this.outputSlot.set(ItemStack.EMPTY);
      if (!stack.isEmpty()) {
         ItemStack inputStack = inventory.getItem(0);
         RecipeHelper.getRecipesOfType(this.recipeType, new SingleRecipeInput(inputStack)).stream().findFirst().ifPresent(r -> {
            this.recipe = (RecipeHolder<ChippedRecipe>)r;
            this.results = Suppliers.memoize(() -> ((ChippedRecipe)this.recipe.value()).getResults(inputStack).toList());
            this.getLastSelectedResult.get().ifPresent(lastSelectedResult -> {
               int i = 0;

               for (ItemStack result : this.results.get()) {
                  if (lastSelectedResult.isSameItemSameComponents(result)) {
                     this.selectedRecipe.set(i);
                     this.updateRecipeResultSlot();
                     return;
                  }

                  i++;
               }
            });
         });
      } else {
         this.results = Collections::emptyList;
      }
   }

   public Slot getInputSlot() {
      return this.inputSlot;
   }

   public Slot getOutputSlot() {
      return this.outputSlot;
   }

   public void setInventoryUpdateListener(Runnable listenerIn) {
      this.inventoryUpdateListener = listenerIn;
   }

   public List<ItemStack> getResults() {
      return this.results.get();
   }

   public int getSelectedRecipe() {
      return this.selectedRecipe.get();
   }

   public boolean hasItemsInInputSlot() {
      return this.inputSlot.hasItem() && this.recipe != null;
   }

   public boolean selectRecipeIndex(int recipeIndex) {
      if (this.recipe != null && this.isIndexInRecipeBounds(recipeIndex)) {
         this.selectedRecipe.set(recipeIndex);
         this.setLastSelectedResult.accept(this.results.get().get(recipeIndex));
         this.updateRecipeResultSlot();
         this.serverUpdater.sendDataToServer(() -> NBTHelper.putInt(new CompoundTag(), "selectedRecipeIndex", recipeIndex));
      }

      return true;
   }

   private boolean isIndexInRecipeBounds(int index) {
      return this.recipe != null && index >= 0 && index < ((ChippedRecipe)this.recipe.value()).getResults(this.inputInventory.getItem(0)).count();
   }

   private void updateRecipeResultSlot() {
      if (this.recipe != null && this.isIndexInRecipeBounds(this.selectedRecipe.get())) {
         ((ChippedRecipe)this.recipe.value())
            .getResults(this.inputInventory.getItem(0))
            .skip(this.selectedRecipe.get())
            .findFirst()
            .ifPresent(stack -> this.outputSlot.set(stack.copy()));
         this.resultInventory.setRecipeUsed(this.recipe);
      } else {
         this.outputSlot.set(ItemStack.EMPTY);
      }
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("selectedRecipeIndex")) {
         this.selectRecipeIndex(data.getInt("selectedRecipeIndex"));
      }
   }

   public boolean isNotResultSlot(Slot slot) {
      return slot != this.outputSlot;
   }

   private class ResultSlot extends Slot {
      private final ContainerLevelAccess worldPosCallable;

      public ResultSlot(ContainerLevelAccess worldPosCallable) {
         super(BlockTransformationRecipeContainer.this.resultInventory, 1, -1, -1);
         this.worldPosCallable = worldPosCallable;
      }

      public boolean mayPlace(ItemStack stack) {
         return false;
      }

      public void onTake(Player thePlayer, ItemStack stack) {
         stack.onCraftedBy(thePlayer.level(), thePlayer, stack.getCount());
         BlockTransformationRecipeContainer.this.resultInventory
            .awardUsedRecipes(thePlayer, List.of(BlockTransformationRecipeContainer.this.inputSlot.getItem()));
         ItemStack itemstack = BlockTransformationRecipeContainer.this.inputSlot.remove(1);
         if (!itemstack.isEmpty()) {
            BlockTransformationRecipeContainer.this.updateRecipeResultSlot();
         }

         this.worldPosCallable.execute((world, pos) -> {
            long l = world.getGameTime();
            if (BlockTransformationRecipeContainer.this.lastOnTake != l) {
               world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
               BlockTransformationRecipeContainer.this.lastOnTake = l;
            }
         });
         super.onTake(thePlayer, stack);
      }
   }
}
