package com.tom.storagemod.rei;

import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.screen.AbstractStorageTerminalScreen;
import com.tom.storagemod.util.IAutoFillTerminal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler.Context;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler.Result;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ReiTransferHandler implements TransferHandler {
   private CategoryIdentifier<?> CRAFTING = CategoryIdentifier.of("minecraft", "plugins/crafting");

   public Result handle(Context context) {
      if (!(context.getMenu() instanceof IAutoFillTerminal)) {
         return Result.createNotApplicable();
      }

      if (context.getDisplay().getCategoryIdentifier().equals(this.CRAFTING) && context.getMinecraft().screen != context.getContainerScreen()) {
         if (context.getContainerScreen() instanceof AbstractStorageTerminalScreen scr && !context.isActuallyCrafting() && !scr.isSmartItemSearchOn()) {
            return Result.createSuccessful().blocksFurtherHandling();
         } else {
            Display recipe = context.getDisplay();
            ItemStack[][] stacks = recipe.getInputEntries()
               .stream()
               .map(
                  l -> l.stream()
                     .filter(es -> es.getDefinition().getValueType() == ItemStack.class)
                     .map(es -> es.getValue())
                     .filter(e -> e != null)
                     .toArray(ItemStack[]::new)
               )
               .toArray(ItemStack[][]::new);
            IAutoFillTerminal term = (IAutoFillTerminal)context.getMenu();
            List<Integer> missing = new ArrayList<>();
            int width = recipe instanceof SimpleGridMenuDisplay ? ((SimpleGridMenuDisplay)recipe).getWidth() : Integer.MAX_VALUE;
            Set<StoredItemStack> stored = new HashSet<>(term.getStoredItems());
            int i = 0;

            for (ItemStack[] list : stacks) {
               if (list.length > 0) {
                  boolean found = false;

                  for (ItemStack stack : list) {
                     if (stack != null && context.getMinecraft().player.getInventory().findSlotMatchingItem(stack) != -1) {
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
                     missing.add(width == 1 ? i * 3 : (width == 2 ? i % 2 + i / 2 * 3 : i));
                  }
               }

               i++;
            }

            if (context.isActuallyCrafting()) {
               ResourceLocation recipeId = this.getRecipe(recipe);
               if (recipeId != null && !Minecraft.getInstance().level.getRecipeManager().byKey(recipeId).isEmpty()) {
                  CompoundTag compound = new CompoundTag();
                  compound.putString("fill", recipeId.toString());
                  term.sendMessage(compound);
                  return Result.createSuccessful().blocksFurtherHandling();
               }
            }

            return !missing.isEmpty()
               ? Result.createSuccessful().color(1739237888).blocksFurtherHandling(false).renderer((gr, mouseX, mouseY, delta, widgets, bounds, d) -> {
                  int ix = 0;

                  for (Widget widget : widgets) {
                     if (widget instanceof Slot && ((Slot)widget).getNoticeMark() == 1 && missing.contains(ix++)) {
                        gr.pose().pushPose();
                        gr.pose().translate(0.0F, 0.0F, 400.0F);
                        Rectangle innerBounds = ((Slot)widget).getInnerBounds();
                        gr.fill(innerBounds.x, innerBounds.y, innerBounds.getMaxX(), innerBounds.getMaxY(), 1090453504);
                        gr.pose().popPose();
                     }
                  }
               })
               : Result.createSuccessful().blocksFurtherHandling();
         }
      } else {
         return Result.createNotApplicable();
      }
   }

   private ResourceLocation getRecipe(Display display) {
      return DisplayRegistry.getInstance().getDisplayOrigin(display) instanceof RecipeHolder<?> holder ? holder.id() : null;
   }
}
