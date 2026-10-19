package mezz.jei.library.plugins.debug.ingredients;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DebugIngredientHelper implements IIngredientHelper<DebugIngredient> {
   @Override
   public IIngredientType<DebugIngredient> getIngredientType() {
      return DebugIngredient.TYPE;
   }

   public String getDisplayName(DebugIngredient ingredient) {
      return "JEI Debug Item #" + ingredient.number();
   }

   public String getUniqueId(DebugIngredient ingredient, UidContext context) {
      return "JEI_debug_" + ingredient.number();
   }

   public Object getUid(DebugIngredient ingredient, UidContext context) {
      return ingredient.number();
   }

   public String getWildcardId(DebugIngredient ingredient) {
      return "JEI_debug";
   }

   public Object getGroupingUid(DebugIngredient ingredient) {
      return DebugIngredient.class;
   }

   public ResourceLocation getResourceLocation(DebugIngredient ingredient) {
      return ResourceLocation.fromNamespaceAndPath("jei", "debug_" + ingredient.number());
   }

   public DebugIngredient copyIngredient(DebugIngredient ingredient) {
      return ingredient.copy();
   }

   public String getErrorInfo(@Nullable DebugIngredient ingredient) {
      return ingredient == null ? "debug ingredient: null" : this.getDisplayName(ingredient);
   }
}
