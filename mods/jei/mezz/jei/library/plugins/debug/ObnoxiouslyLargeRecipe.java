package mezz.jei.library.plugins.debug;

import net.minecraft.resources.ResourceLocation;

public class ObnoxiouslyLargeRecipe {
   private static int count = 0;
   private final ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath("jei", "number_" + count);

   public ObnoxiouslyLargeRecipe() {
      count++;
   }

   public ResourceLocation getRecipeId() {
      return this.recipeId;
   }
}
