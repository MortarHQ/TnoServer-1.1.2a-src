package mezz.jei.library.load.registration;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.IExtendableCraftingRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.IExtendableSmithingRecipeCategory;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.library.runtime.JeiHelpers;

public class VanillaCategoryExtensionRegistration implements IVanillaCategoryExtensionRegistration {
   private final IExtendableCraftingRecipeCategory craftingCategory;
   private final IExtendableSmithingRecipeCategory smithingCategory;
   private final JeiHelpers jeiHelpers;

   public VanillaCategoryExtensionRegistration(
      IExtendableCraftingRecipeCategory craftingCategory, IExtendableSmithingRecipeCategory smithingCategory, JeiHelpers jeiHelpers
   ) {
      this.craftingCategory = craftingCategory;
      this.smithingCategory = smithingCategory;
      this.jeiHelpers = jeiHelpers;
   }

   @Override
   public IExtendableCraftingRecipeCategory getCraftingCategory() {
      return this.craftingCategory;
   }

   @Override
   public IExtendableSmithingRecipeCategory getSmithingCategory() {
      return this.smithingCategory;
   }

   @Override
   public IJeiHelpers getJeiHelpers() {
      return this.jeiHelpers;
   }
}
