package dev.emi.emi.handler;

import com.google.common.collect.Lists;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import java.util.List;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public class CraftingRecipeHandler implements StandardRecipeHandler<CraftingMenu> {
   public List<Slot> getInputSources(CraftingMenu handler) {
      List<Slot> list = Lists.newArrayList();

      for (int i = 1; i < 10; i++) {
         list.add(handler.getSlot(i));
      }

      int invStart = 10;

      for (int i = invStart; i < invStart + 36; i++) {
         list.add(handler.getSlot(i));
      }

      return list;
   }

   public List<Slot> getCraftingSlots(CraftingMenu handler) {
      List<Slot> list = Lists.newArrayList();

      for (int i = 1; i < 10; i++) {
         list.add(handler.getSlot(i));
      }

      return list;
   }

   @Nullable
   public Slot getOutputSlot(CraftingMenu handler) {
      return (Slot)handler.slots.get(0);
   }

   @Override
   public boolean supportsRecipe(EmiRecipe recipe) {
      return recipe.getCategory() == VanillaEmiRecipeCategories.CRAFTING && recipe.supportsRecipeTree();
   }
}
