package com.tom.storagemod.jei;

import com.tom.storagemod.Content;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.menu.CraftingTerminalMenu;
import com.tom.storagemod.util.IAutoFillTerminal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferError.Type;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class CraftingTerminalTransferHandler<C extends AbstractContainerMenu & IAutoFillTerminal>
   implements IRecipeTransferHandler<C, RecipeHolder<CraftingRecipe>> {
   private final Class<C> containerClass;
   private final IRecipeTransferHandlerHelper helper;
   private static final IRecipeTransferError ERROR_INSTANCE = new IRecipeTransferError() {
      public Type getType() {
         return Type.INTERNAL;
      }
   };

   public CraftingTerminalTransferHandler(Class<C> containerClass, IRecipeTransferHandlerHelper helper) {
      this.containerClass = containerClass;
      this.helper = helper;
   }

   public Class<C> getContainerClass() {
      return this.containerClass;
   }

   @Nullable
   public IRecipeTransferError transferRecipe(
      C container, RecipeHolder<CraftingRecipe> recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer
   ) {
      if (!(container instanceof IAutoFillTerminal term)) {
         return ERROR_INSTANCE;
      } else {
         if (!term.isSmartSearch() && !doTransfer) {
            return null;
         }

         List<IRecipeSlotView> missing = new ArrayList<>();
         List<IRecipeSlotView> views = recipeSlots.getSlotViews();
         List<ItemStack[]> inputs = new ArrayList<>();
         Set<StoredItemStack> stored = new HashSet<>(term.getStoredItems());

         for (IRecipeSlotView view : views) {
            if (view.getRole() == RecipeIngredientRole.INPUT || view.getRole() == RecipeIngredientRole.CATALYST) {
               ItemStack[] list = view.getIngredients(VanillaTypes.ITEM_STACK).toArray(ItemStack[]::new);
               if (list.length == 0) {
                  inputs.add(null);
               } else {
                  inputs.add(list);
                  boolean found = false;

                  for (ItemStack stack : list) {
                     if (stack != null && player.getInventory().findSlotMatchingItem(stack) != -1) {
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     for (ItemStack stack : list) {
                        TerminalItemStack s = new TerminalItemStack(stack);
                        if (stored.contains(s)) {
                           found = true;
                           break;
                        }
                     }
                  }

                  if (!found) {
                     missing.add(view);
                  }
               }
            }
         }

         if (doTransfer) {
            ResourceLocation recipeId = recipe.id();
            if (recipeId != null && !player.level().getRecipeManager().byKey(recipeId).isEmpty()) {
               CompoundTag compound = new CompoundTag();
               compound.putString("fill", recipeId.toString());
               term.sendMessage(compound);
            }
         }

         return !missing.isEmpty()
            ? new CraftingTerminalTransferHandler.TransferWarning(
               this.helper.createUserErrorForMissingSlots(Component.translatable("tooltip.toms_storage.items_missing"), missing)
            )
            : null;
      }
   }

   public static void registerTransferHandlers(IRecipeTransferRegistration recipeTransferRegistry) {
      recipeTransferRegistry.addRecipeTransferHandler(
         new CraftingTerminalTransferHandler((Class<C>)CraftingTerminalMenu.class, recipeTransferRegistry.getTransferHelper()), RecipeTypes.CRAFTING
      );
   }

   public Optional<MenuType<C>> getMenuType() {
      return Optional.ofNullable((MenuType<C>)Content.craftingTerminalMenu.get());
   }

   public RecipeType<RecipeHolder<CraftingRecipe>> getRecipeType() {
      return RecipeTypes.CRAFTING;
   }

   private static class TransferWarning implements IRecipeTransferError {
      private final IRecipeTransferError parent;

      public TransferWarning(IRecipeTransferError parent) {
         this.parent = parent;
      }

      public Type getType() {
         return Type.COSMETIC;
      }

      public void showError(GuiGraphics guiGraphics, int mouseX, int mouseY, IRecipeSlotsView recipeSlotsView, int recipeX, int recipeY) {
         this.parent.showError(guiGraphics, mouseX, mouseY, recipeSlotsView, recipeX, recipeY);
      }
   }
}
