package net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IServerUpdater;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.CraftingItemHandler;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;

public abstract class BlockConverterRecipeContainer<R extends SingleItemRecipe, W extends BlockConverterUpgradeWrapper<?, ?>, RC extends BlockConverterRecipeContainer<R, W, RC, C>, C extends BlockConverterUpgradeContainer<R, W, C, RC>> {
   private static final String DATA_SELECTED_RECIPE_INDEX = "selectedRecipeIndex";
   private final Slot inputSlot;
   private final IServerUpdater serverUpdater;
   protected final Level level;
   private final Slot outputSlot;
   private final ResultContainer resultInventory = new ResultContainer();
   private List<RecipeHolder<R>> recipes = Lists.newArrayList();
   private final DataSlot selectedRecipe = DataSlot.standalone();
   private Item inputItem = Items.AIR;
   private final CraftingItemHandler inputInventory;
   private Runnable inventoryUpdateListener = () -> {};
   private final Supplier<Optional<ResourceLocation>> getLastSelectedRecipeId;
   private final Consumer<ResourceLocation> setLastSelectedRecipeId;
   private long lastOnTake = -1L;
   private final SoundEvent craftSound;

   public BlockConverterRecipeContainer(
      C upgradeContainer, Consumer<Slot> addSlot, IServerUpdater serverUpdater, ContainerLevelAccess worldPosCallable, Level level, SoundEvent craftSound
   ) {
      this.level = level;
      this.inputSlot = new SlotSuppliedHandler(upgradeContainer.getUpgradeWrapper()::getInputInventory, 0, -1, -1) {
         private boolean countIncreased = false;

         @Override
         public void setChanged() {
            super.setChanged();
            BlockConverterRecipeContainer.this.onCraftMatrixChanged(BlockConverterRecipeContainer.this.inputInventory, this.countIncreased);
         }

         public ItemStack safeInsert(ItemStack stack, int increment) {
            this.countIncreased = increment > 0;
            return super.safeInsert(stack, increment);
         }

         public ItemStack remove(int amount) {
            ItemStack ret = super.remove(amount);
            this.setChanged();
            this.countIncreased = false;
            return ret;
         }
      };
      this.craftSound = craftSound;
      this.serverUpdater = serverUpdater;
      addSlot.accept(this.inputSlot);
      this.inputInventory = new CraftingItemHandler(
         upgradeContainer.getUpgradeWrapper()::getInputInventory, inventory -> this.onCraftMatrixChanged(inventory, false)
      );
      this.outputSlot = new BlockConverterRecipeContainer.ResultSlot(worldPosCallable);
      addSlot.accept(this.outputSlot);
      this.getLastSelectedRecipeId = upgradeContainer.getUpgradeWrapper()::getRecipeId;
      this.setLastSelectedRecipeId = upgradeContainer.getUpgradeWrapper()::setRecipeId;
      this.onCraftMatrixChanged(this.inputInventory, false);
   }

   protected abstract RecipeType<R> getRecipeType();

   protected abstract List<RecipeHolder<R>> filterAndSortRecipes(List<RecipeHolder<R>> var1);

   private void onCraftMatrixChanged(Container inventory, boolean countIncreased) {
      ItemStack itemstack = this.inputSlot.getItem();
      if (this.shouldRefreshRecipes(itemstack, countIncreased)) {
         this.inputItem = itemstack.getItem();
         this.updateAvailableRecipes(inventory, itemstack);
      }

      this.inventoryUpdateListener.run();
   }

   protected boolean shouldRefreshRecipes(ItemStack itemstack, boolean countIncreased) {
      return itemstack.getItem() != this.inputItem;
   }

   private void updateAvailableRecipes(Container inventory, ItemStack stack) {
      this.recipes.clear();
      this.selectedRecipe.set(-1);
      this.outputSlot.set(ItemStack.EMPTY);
      if (!stack.isEmpty()) {
         this.recipes = RecipeHelper.getRecipesOfType(this.getRecipeType(), new SingleRecipeInput(inventory.getItem(0)));
         this.recipes = this.filterAndSortRecipes(this.recipes);
         this.getLastSelectedRecipeId.get().ifPresent(id -> {
            for (int i = 0; i < this.recipes.size(); i++) {
               if (this.recipes.get(i).id().equals(id)) {
                  this.selectedRecipe.set(i);
                  this.updateRecipeResultSlot();
               }
            }
         });
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

   public List<RecipeHolder<R>> getRecipeList() {
      return this.recipes;
   }

   public int getSelectedRecipe() {
      return this.selectedRecipe.get();
   }

   public boolean hasItemsInInputSlot() {
      return this.inputSlot.hasItem() && !this.recipes.isEmpty();
   }

   public boolean selectRecipe(int recipeIndex) {
      if (this.isIndexInRecipeBounds(recipeIndex)) {
         this.selectedRecipe.set(recipeIndex);
         this.setLastSelectedRecipeId.accept(this.recipes.get(recipeIndex).id());
         this.updateRecipeResultSlot();
         this.serverUpdater.sendDataToServer(() -> NBTHelper.putInt(new CompoundTag(), "selectedRecipeIndex", recipeIndex));
      }

      return true;
   }

   private boolean isIndexInRecipeBounds(int index) {
      return index >= 0 && index < this.recipes.size();
   }

   private void updateRecipeResultSlot() {
      if (!this.recipes.isEmpty() && this.isIndexInRecipeBounds(this.selectedRecipe.get())) {
         RecipeHolder<R> recipe = this.recipes.get(this.selectedRecipe.get());
         this.resultInventory.setRecipeUsed(recipe);
         this.outputSlot.set(((SingleItemRecipe)recipe.value()).assemble(new SingleRecipeInput(this.inputInventory.getItem(0)), this.level.registryAccess()));
      } else {
         this.outputSlot.set(ItemStack.EMPTY);
      }
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("selectedRecipeIndex")) {
         this.selectRecipe(data.getInt("selectedRecipeIndex"));
      }
   }

   public boolean isNotResultSlot(Slot slot) {
      return slot != this.outputSlot;
   }

   protected abstract int getInputCount();

   private class ResultSlot extends Slot {
      private final ContainerLevelAccess worldPosCallable;

      public ResultSlot(ContainerLevelAccess worldPosCallable) {
         super(BlockConverterRecipeContainer.this.resultInventory, 1, -1, -1);
         this.worldPosCallable = worldPosCallable;
      }

      public boolean mayPlace(ItemStack stack) {
         return false;
      }

      public void onTake(Player player, ItemStack stack) {
         stack.onCraftedBy(player.level(), player, stack.getCount());
         BlockConverterRecipeContainer.this.resultInventory.awardUsedRecipes(player, List.of(BlockConverterRecipeContainer.this.inputSlot.getItem()));
         ItemStack itemstack = BlockConverterRecipeContainer.this.inputSlot.remove(BlockConverterRecipeContainer.this.getInputCount());
         if (!itemstack.isEmpty()) {
            BlockConverterRecipeContainer.this.updateRecipeResultSlot();
         }

         this.worldPosCallable.execute((world, pos) -> {
            long l = world.getGameTime();
            if (BlockConverterRecipeContainer.this.lastOnTake != l) {
               world.playSound(null, pos, BlockConverterRecipeContainer.this.craftSound, SoundSource.BLOCKS, 1.0F, 1.0F);
               BlockConverterRecipeContainer.this.lastOnTake = l;
            }
         });
         super.onTake(player, stack);
      }
   }
}
