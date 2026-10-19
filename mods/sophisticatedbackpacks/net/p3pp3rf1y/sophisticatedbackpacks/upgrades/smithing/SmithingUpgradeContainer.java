package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.smithing;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition.SlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;

public class SmithingUpgradeContainer extends UpgradeContainerBase<SmithingUpgradeWrapper, SmithingUpgradeContainer> implements ICraftingContainer {
   private static final String DATA_SHIFT_CLICK_INTO_STORAGE = "shiftClickIntoStorage";
   private final Slot resultSlot;
   private Runnable onResultChanged = () -> {};
   private final SmithingUpgradeContainer.PersistableSmithingMenu smithingMenuDelegate;

   public SmithingUpgradeContainer(
      Player player, int upgradeContainerId, SmithingUpgradeWrapper upgradeWrapper, UpgradeContainerType<SmithingUpgradeWrapper, SmithingUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      this.smithingMenuDelegate = new SmithingUpgradeContainer.PersistableSmithingMenu(new Inventory(player));
      this.slots.add(this.smithingMenuDelegate.getSlot(0));
      this.slots.add(this.smithingMenuDelegate.getSlot(1));
      this.slots.add(this.smithingMenuDelegate.getSlot(2));
      this.resultSlot = this.smithingMenuDelegate.getSlot(3);
      this.slots.add(this.resultSlot);
      this.smithingMenuDelegate.createResult();
   }

   public void setOnResultChangedHandler(Runnable onResultChanged) {
      this.onResultChanged = onResultChanged;
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("shiftClickIntoStorage")) {
         this.setShiftClickIntoStorage(data.getBoolean("shiftClickIntoStorage"));
      }
   }

   public void setUpgradeWrapper(IUpgradeWrapper updatedUpgradeWrapper) {
      super.setUpgradeWrapper(updatedUpgradeWrapper);
      this.smithingMenuDelegate.createResult();
   }

   public boolean shouldShiftClickIntoStorage() {
      return ((SmithingUpgradeWrapper)this.upgradeWrapper).shouldShiftClickIntoStorage();
   }

   public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
      ((SmithingUpgradeWrapper)this.upgradeWrapper).setShiftClickIntoStorage(shiftClickIntoStorage);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shiftClickIntoStorage", shiftClickIntoStorage));
   }

   public boolean mergeIntoStorageFirst(Slot slot) {
      return !(slot instanceof ResultSlot) || this.shouldShiftClickIntoStorage();
   }

   public boolean allowsPickupAll(Slot slot) {
      return slot != this.resultSlot;
   }

   public Slot getTemplateSlot() {
      return this.smithingMenuDelegate.getSlot(0);
   }

   public Slot getBaseSlot() {
      return this.smithingMenuDelegate.getSlot(1);
   }

   public Slot getAdditionalSlot() {
      return this.smithingMenuDelegate.getSlot(2);
   }

   public Slot getResultSlot() {
      return this.smithingMenuDelegate.getSlot(3);
   }

   public List<Slot> getRecipeSlots() {
      return List.of(this.getTemplateSlot(), this.getBaseSlot(), this.getAdditionalSlot());
   }

   public Container getCraftMatrix() {
      return this.smithingMenuDelegate.getInputSlots();
   }

   public void setRecipeUsed(ResourceLocation recipeId) {
      this.smithingMenuDelegate.setSelectedRecipe(recipeId);
   }

   public RecipeType<?> getRecipeType() {
      return RecipeType.SMITHING;
   }

   public boolean shouldRefillCraftingGrid() {
      return false;
   }

   private class PersistableSmithingMenu extends SmithingMenu {
      public PersistableSmithingMenu(Inventory playerInventory) {
         super(
            0,
            playerInventory,
            playerInventory.player.level().isClientSide()
               ? ContainerLevelAccess.NULL
               : ContainerLevelAccess.create(playerInventory.player.level(), playerInventory.player.blockPosition())
         );
      }

      protected void createInputSlots(ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
         for (final SlotDefinition slotDefinition : itemCombinerMenuSlotDefinition.getSlots()) {
            this.addSlot(
               new SlotSuppliedHandler(((SmithingUpgradeWrapper)SmithingUpgradeContainer.this.upgradeWrapper)::getInventory, slotDefinition.slotIndex(), 0, 0) {
                  public void setChanged() {
                     super.setChanged();
                     PersistableSmithingMenu.this.slotsChanged(PersistableSmithingMenu.this.inputSlots);
                  }

                  public boolean mayPlace(ItemStack p_267156_) {
                     return slotDefinition.mayPlace().test(p_267156_);
                  }
               }
            );
         }
      }

      protected SimpleContainer createContainer(int size) {
         return new SimpleContainer(size) {
            public void setChanged() {
               super.setChanged();
               PersistableSmithingMenu.this.slotsChanged(this);
            }

            public ItemStack getItem(int pIndex) {
               return ((SmithingUpgradeWrapper)SmithingUpgradeContainer.this.upgradeWrapper).getInventory().getStackInSlot(pIndex);
            }

            public void setItem(int pIndex, ItemStack pStack) {
               ((SmithingUpgradeWrapper)SmithingUpgradeContainer.this.upgradeWrapper).getInventory().setStackInSlot(pIndex, pStack);
            }
         };
      }

      protected void createResultSlot(ItemCombinerMenuSlotDefinition slotDefinition) {
         this.addSlot(
            new Slot(this.resultSlots, slotDefinition.getResultSlot().slotIndex(), slotDefinition.getResultSlot().x(), slotDefinition.getResultSlot().y()) {
               public boolean mayPlace(ItemStack stack) {
                  return false;
               }

               public boolean mayPickup(Player player) {
                  return PersistableSmithingMenu.this.mayPickup(player, this.hasItem());
               }

               public void onTake(Player player, ItemStack stack) {
                  PersistableSmithingMenu.this.onTake(player, stack);
               }

               public void setChanged() {
                  super.setChanged();
                  SmithingUpgradeContainer.this.onResultChanged.run();
               }
            }
         );
      }

      public void slotsChanged(Container pInventory) {
         this.createResult();
         SmithingUpgradeContainer.this.onResultChanged.run();
      }

      public Container getInputSlots() {
         return this.inputSlots;
      }

      public void setSelectedRecipe(ResourceLocation recipeId) {
         SmithingRecipeInput smithingRecipeInput = new SmithingRecipeInput(
            SmithingUpgradeContainer.this.getTemplateSlot().getItem(),
            SmithingUpgradeContainer.this.getBaseSlot().getItem(),
            SmithingUpgradeContainer.this.getAdditionalSlot().getItem()
         );
         RecipeHelper.safeGetRecipeFor(RecipeType.SMITHING, smithingRecipeInput, recipeId).ifPresent(recipe -> this.selectedRecipe = recipe);
      }
   }
}
