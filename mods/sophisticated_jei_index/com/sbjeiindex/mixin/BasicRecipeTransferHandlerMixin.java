package com.sbjeiindex.mixin;

import com.sbjeiindex.jei.BackpackSnapshotCache;
import com.sbjeiindex.jei.JeiSlotResolver;
import com.sbjeiindex.jei.OffsetItemHandlerModifiable;
import com.sbjeiindex.util.BackpackHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.common.network.IConnectionToServer;
import mezz.jei.common.network.packets.PacketRecipeTransfer;
import mezz.jei.common.transfer.RecipeTransferOperationsResult;
import mezz.jei.common.transfer.RecipeTransferUtil;
import mezz.jei.library.transfer.BasicRecipeTransferHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BasicRecipeTransferHandler.class, remap = false)
public class BasicRecipeTransferHandlerMixin {
   @Shadow(remap = false)
   private IConnectionToServer serverConnection;
   @Shadow(remap = false)
   private IStackHelper stackHelper;
   @Shadow(remap = false)
   private IRecipeTransferHandlerHelper handlerHelper;
   @Shadow(remap = false)
   private IRecipeTransferInfo transferInfo;

   @Inject(method = "transferRecipe", at = @At("HEAD"), cancellable = true, remap = false)
   private void sbjeiindex_transferRecipe(
      AbstractContainerMenu container,
      Object recipe,
      IRecipeSlotsView recipeSlotsView,
      Player player,
      boolean maxTransfer,
      boolean doTransfer,
      CallbackInfoReturnable<IRecipeTransferError> cir
   ) {
      List<IItemHandlerModifiable> backpackHandlers = BackpackHelper.getEquippedBackpackItemHandlersWithJEIIndexUpgrade(player);
      if (!backpackHandlers.isEmpty()) {
         if (!this.serverConnection.isJeiOnServer()) {
            Component tooltipMessage = Component.translatable("jei.tooltip.error.recipe.transfer.no.server");
            cir.setReturnValue(this.handlerHelper.createUserErrorWithTooltip(tooltipMessage));
         } else if (!this.transferInfo.canHandle(container, recipe)) {
            IRecipeTransferError handlingError = this.transferInfo.getHandlingError(container, recipe);
            if (handlingError != null) {
               cir.setReturnValue(handlingError);
            } else {
               cir.setReturnValue(this.handlerHelper.createInternalError());
            }
         } else {
            List<Slot> craftingSlots = List.copyOf(this.transferInfo.getRecipeSlots(container, recipe));
            List<Slot> inventorySlotsRaw = List.copyOf(this.transferInfo.getInventorySlots(container, recipe));
            if (!BasicRecipeTransferHandler.validateTransferInfo(this.transferInfo, container, craftingSlots, inventorySlotsRaw)) {
               cir.setReturnValue(this.handlerHelper.createInternalError());
            } else {
               List<IRecipeSlotView> inputItemSlotViews = recipeSlotsView.getSlotViews(RecipeIngredientRole.INPUT);
               if (!BasicRecipeTransferHandler.validateRecipeView(this.transferInfo, container, craftingSlots, inputItemSlotViews)) {
                  cir.setReturnValue(this.handlerHelper.createInternalError());
               } else {
                  Set<Integer> craftingSlotIndexes = craftingSlots.stream().map(s -> s.index).collect(Collectors.toSet());
                  List<Slot> inventorySlots = inventorySlotsRaw.stream().filter(s -> !craftingSlotIndexes.contains(s.index)).toList();
                  BackpackSnapshotCache.BackpackSnapshot backpackSnapshot = null;
                  int backpackSlotCount;
                  if (!doTransfer) {
                     backpackSnapshot = BackpackSnapshotCache.getOrCreate(container, backpackHandlers);
                     backpackSlotCount = backpackSnapshot.totalBackpackSlots();
                  } else {
                     int count = 0;

                     for (IItemHandlerModifiable handler : backpackHandlers) {
                        count += handler.getSlots();
                     }

                     backpackSlotCount = count;
                  }

                  List<Slot> extendedInventorySlots = new ArrayList<>(inventorySlots.size() + backpackSlotCount);
                  extendedInventorySlots.addAll(inventorySlots);
                  Map<Slot, ItemStack> availableItemStacks = new HashMap<>();
                  int filledCraftSlotCount = 0;

                  for (Slot slot : craftingSlots) {
                     ItemStack stack = slot.getItem();
                     if (!stack.isEmpty()) {
                        if (!slot.mayPickup(player)) {
                           cir.setReturnValue(this.handlerHelper.createInternalError());
                           return;
                        }

                        if (slot.mayPlace(stack)) {
                           filledCraftSlotCount++;
                           if (slot.allowModification(player)) {
                              availableItemStacks.put(slot, stack.copy());
                           }
                        }
                     }
                  }

                  int emptySlots = 0;

                  for (Slot slot : inventorySlots) {
                     ItemStack stack = slot.getItem();
                     if (!stack.isEmpty()) {
                        if (!slot.mayPickup(player)) {
                           cir.setReturnValue(this.handlerHelper.createInternalError());
                           return;
                        }

                        if (slot.allowModification(player)) {
                           availableItemStacks.put(slot, stack.copy());
                        }
                     } else {
                        emptySlots++;
                     }
                  }

                  Map<Integer, Slot> extraSlots;
                  if (backpackSnapshot != null) {
                     extendedInventorySlots.addAll(backpackSnapshot.backpackSlots());
                     extraSlots = backpackSnapshot.extraSlots();
                     emptySlots += backpackSnapshot.emptyBackpackSlots();

                     for (Entry<Integer, ItemStack> e : backpackSnapshot.nonEmptyStacks().entrySet()) {
                        Slot slot = extraSlots.get(e.getKey());
                        if (slot != null) {
                           availableItemStacks.put(slot, e.getValue().copy());
                        }
                     }
                  } else {
                     extraSlots = new HashMap<>();

                     for (int backpackIndex = 0; backpackIndex < backpackHandlers.size(); backpackIndex++) {
                        IItemHandlerModifiable backpackHandler = backpackHandlers.get(backpackIndex);
                        int baseOffset = 10000 + backpackIndex * 10000;
                        OffsetItemHandlerModifiable offsetHandler = new OffsetItemHandlerModifiable(backpackHandler, baseOffset);

                        for (int i = 0; i < backpackHandler.getSlots(); i++) {
                           int slotId = baseOffset + i;
                           Slot slot = new SlotItemHandler(offsetHandler, slotId, 0, 0);
                           slot.index = slotId;
                           extendedInventorySlots.add(slot);
                           extraSlots.put(slotId, slot);
                           ItemStack stack = offsetHandler.getStackInSlot(slotId);
                           if (!stack.isEmpty()) {
                              availableItemStacks.put(slot, stack.copy());
                           } else {
                              emptySlots++;
                           }
                        }
                     }
                  }

                  int inputCount = inputItemSlotViews.size();
                  if (filledCraftSlotCount - inputCount > emptySlots) {
                     Component message = Component.translatable("jei.tooltip.error.recipe.transfer.inventory.full");
                     cir.setReturnValue(this.handlerHelper.createUserErrorWithTooltip(message));
                  } else {
                     RecipeTransferOperationsResult transferOperations = RecipeTransferUtil.getRecipeTransferOperations(
                        this.stackHelper, availableItemStacks, inputItemSlotViews, craftingSlots
                     );
                     if (!transferOperations.missingItems.isEmpty()) {
                        Component message = Component.translatable("jei.tooltip.error.recipe.transfer.missing");
                        cir.setReturnValue(this.handlerHelper.createUserErrorForMissingSlots(message, transferOperations.missingItems));
                     } else {
                        JeiSlotResolver.set(extraSlots);

                        label187: {
                           try {
                              if (RecipeTransferUtil.validateSlots(player, transferOperations.results, craftingSlots, extendedInventorySlots)) {
                                 if (doTransfer) {
                                    boolean requireCompleteSets = this.transferInfo.requireCompleteSets(container, recipe);
                                    PacketRecipeTransfer packet = PacketRecipeTransfer.fromSlots(
                                       transferOperations.results, craftingSlots, extendedInventorySlots, maxTransfer, requireCompleteSets
                                    );
                                    this.serverConnection.sendPacketToServer(packet);
                                 }
                                 break label187;
                              }

                              cir.setReturnValue(this.handlerHelper.createInternalError());
                           } finally {
                              JeiSlotResolver.clear();
                           }

                           return;
                        }

                        cir.setReturnValue(null);
                     }
                  }
               }
            }
         }
      }
   }
}
