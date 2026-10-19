package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.common.transfer.RecipeTransferOperationsResult;
import mezz.jei.common.transfer.RecipeTransferUtil;
import mezz.jei.common.transfer.TransferOperation;
import mezz.jei.common.util.StringUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import org.apache.logging.log4j.util.Supplier;

public abstract class JeiCraftingContainerRecipeTransferHandlerBase<C extends StorageContainerMenuBase<?>, R extends RecipeHolder<? extends Recipe<?>>>
   implements IRecipeTransferHandler<C, R> {
   private final IRecipeTransferHandlerHelper handlerHelper;
   private final IStackHelper stackHelper;

   protected JeiCraftingContainerRecipeTransferHandlerBase(IRecipeTransferHandlerHelper handlerHelper, IStackHelper stackHelper) {
      this.handlerHelper = handlerHelper;
      this.stackHelper = stackHelper;
   }

   public Optional<MenuType<C>> getMenuType() {
      return Optional.empty();
   }

   @Nullable
   public IRecipeTransferError transferRecipe(C container, R recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
      Optional<? extends UpgradeContainerBase<?, ?>> potentialCraftingContainer = container.getOpenOrFirstCraftingContainer(recipe.value().getType());
      if (potentialCraftingContainer.isEmpty()) {
         return this.handlerHelper.createInternalError();
      }

      UpgradeContainerBase<?, ?> openOrFirstCraftingContainer = (UpgradeContainerBase<?, ?>)potentialCraftingContainer.get();
      List<Slot> craftingSlots = Collections.unmodifiableList(
         openOrFirstCraftingContainer instanceof ICraftingContainer cc ? cc.getRecipeSlots() : Collections.emptyList()
      );
      List<Slot> inventorySlots = container.realInventorySlots.stream().filter(s -> s.mayPickup(player)).toList();
      if (!this.validateTransferInfo(container, craftingSlots, inventorySlots)) {
         return this.handlerHelper.createInternalError();
      }

      List<IRecipeSlotView> inputItemSlotViews = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);
      if (!this.validateRecipeView(container, craftingSlots, inputItemSlotViews)) {
         return this.handlerHelper.createInternalError();
      }

      JeiCraftingContainerRecipeTransferHandlerBase.InventoryState inventoryState = this.getInventoryState(craftingSlots, inventorySlots, player, container);
      if (inventoryState == null) {
         return this.handlerHelper.createInternalError();
      }

      int inputCount = inputItemSlotViews.size();
      if (!inventoryState.hasRoom(inputCount)) {
         Component message = Component.translatable("jei.tooltip.error.recipe.transfer.inventory.full");
         return this.handlerHelper.createUserErrorWithTooltip(message);
      }

      RecipeTransferOperationsResult transferOperations = RecipeTransferUtil.getRecipeTransferOperations(
         this.stackHelper, inventoryState.availableItemStacks, inputItemSlotViews, craftingSlots
      );
      if (!transferOperations.missingItems.isEmpty()) {
         Component message = Component.translatable("jei.tooltip.error.recipe.transfer.missing");
         return this.handlerHelper.createUserErrorForMissingSlots(message, transferOperations.missingItems);
      }

      if (!RecipeTransferUtil.validateSlots(player, transferOperations.results, craftingSlots, inventorySlots)) {
         return this.handlerHelper.createInternalError();
      }

      List<Integer> craftingSlotIndexes = craftingSlots.stream().map(s -> s.index).sorted().toList();
      List<Integer> inventorySlotIndexes = inventorySlots.stream().map(s -> s.index).sorted().toList();
      if (doTransfer) {
         if (!openOrFirstCraftingContainer.isOpen()) {
            container.getOpenContainer().ifPresent(c -> {
               c.setIsOpen(false);
               container.setOpenTabId(-1);
            });
            openOrFirstCraftingContainer.setIsOpen(true);
            container.setOpenTabId(openOrFirstCraftingContainer.getUpgradeContainerId());
         }

         ResourceLocation recipeTypeId = BuiltInRegistries.RECIPE_TYPE.getKey(recipe.value().getType());
         if (recipeTypeId != null) {
            JeiTransferRecipePayload packet = new JeiTransferRecipePayload(
               recipe.id(), recipeTypeId, this.toMap(transferOperations.results, container), craftingSlotIndexes, inventorySlotIndexes, maxTransfer
            );
            PacketDistributor.sendToServer(packet, new CustomPacketPayload[0]);
         }
      }

      return null;
   }

   private Map<Integer, Integer> toMap(List<TransferOperation> transferOperations, C container) {
      Map<Integer, Integer> ret = new HashMap<>();
      transferOperations.forEach(to -> ret.put(to.craftingSlot(container).index, to.inventorySlot(container).index));
      return ret;
   }

   private boolean validateTransferInfo(C container, List<Slot> craftingSlots, List<Slot> inventorySlots) {
      Collection<Integer> craftingSlotIndexes = this.slotIndexes(craftingSlots);
      Collection<Integer> inventorySlotIndexes = this.slotIndexes(inventorySlots);
      ArrayList<Slot> allSlots = new ArrayList<>(container.realInventorySlots);
      allSlots.addAll(container.upgradeSlots);
      Collection<Integer> containerSlotIndexes = this.slotIndexes(allSlots);
      if (!containerSlotIndexes.containsAll(craftingSlotIndexes)) {
         SophisticatedCore.LOGGER
            .error(
               "Recipe Transfer helper {} does not work for container {}. The Recipes Transfer Helper references crafting slot indexes [{}] that are not found in the inventory container slots [{}]",
               new Supplier[]{
                  this::getClass, container::getClass, () -> StringUtil.intsToString(craftingSlotIndexes), () -> StringUtil.intsToString(containerSlotIndexes)
               }
            );
         return false;
      } else if (!containerSlotIndexes.containsAll(inventorySlotIndexes)) {
         SophisticatedCore.LOGGER
            .error(
               "Recipe Transfer helper {} does not work for container {}. The Recipes Transfer Helper references inventory slot indexes [{}] that are not found in the inventory container slots [{}]",
               new Supplier[]{
                  this::getClass, container::getClass, () -> StringUtil.intsToString(inventorySlotIndexes), () -> StringUtil.intsToString(containerSlotIndexes)
               }
            );
         return false;
      } else {
         return true;
      }
   }

   private boolean validateRecipeView(C container, List<Slot> craftingSlots, List<IRecipeSlotView> inputSlots) {
      if (inputSlots.size() > craftingSlots.size()) {
         SophisticatedCore.LOGGER
            .error(
               "Recipe View {} does not work for container {}. The Recipe View has more input slots ({}) than the number of inventory crafting slots ({})",
               this.getClass(),
               container.getClass(),
               inputSlots.size(),
               craftingSlots.size()
            );
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   private JeiCraftingContainerRecipeTransferHandlerBase.InventoryState getInventoryState(
      Collection<Slot> craftingSlots, Collection<Slot> inventorySlots, Player player, C container
   ) {
      Map<Slot, ItemStack> availableItemStacks = new HashMap<>();
      int filledCraftSlotCount = 0;
      int emptySlotCount = 0;

      for (Slot slot : craftingSlots) {
         ItemStack stack = slot.getItem();
         if (!stack.isEmpty()) {
            if (!slot.mayPickup(player)) {
               SophisticatedCore.LOGGER
                  .error(
                     "Recipe Transfer helper {} does not work for container {}. The Player is not able to move items out of Crafting Slot number {}",
                     this.getClass(),
                     container.getClass(),
                     slot.index
                  );
               return null;
            }

            filledCraftSlotCount++;
            availableItemStacks.put(slot, stack.copy());
         }
      }

      for (Slot slot : inventorySlots) {
         ItemStack stack = slot.getItem();
         if (!stack.isEmpty()) {
            if (!slot.mayPickup(player)) {
               SophisticatedCore.LOGGER
                  .error(
                     "Recipe Transfer helper {} does not work for container {}. The Player is not able to move items out of Inventory Slot number {}",
                     this.getClass(),
                     container.getClass(),
                     slot.index
                  );
               return null;
            }

            availableItemStacks.put(slot, stack.copy());
         } else {
            emptySlotCount++;
         }
      }

      return new JeiCraftingContainerRecipeTransferHandlerBase.InventoryState(availableItemStacks, filledCraftSlotCount, emptySlotCount);
   }

   private Set<Integer> slotIndexes(Collection<Slot> slots) {
      return slots.stream().map(s -> s.index).collect(Collectors.toSet());
   }

   public record InventoryState(Map<Slot, ItemStack> availableItemStacks, int filledCraftSlotCount, int emptySlotCount) {
      public boolean hasRoom(int inputCount) {
         return this.filledCraftSlotCount - inputCount <= this.emptySlotCount;
      }
   }
}
