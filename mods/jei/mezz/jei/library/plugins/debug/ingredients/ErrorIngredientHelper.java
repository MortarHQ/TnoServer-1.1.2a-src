package mezz.jei.library.plugins.debug.ingredients;

import java.util.Locale;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ErrorIngredientHelper implements IIngredientHelper<ErrorIngredient> {
   @Override
   public IIngredientType<ErrorIngredient> getIngredientType() {
      return ErrorIngredient.TYPE;
   }

   public String getDisplayName(ErrorIngredient ingredient) {
      return "JEI Error Item #" + ingredient.crashType();
   }

   public String getUniqueId(ErrorIngredient ingredient, UidContext context) {
      return "JEI_error_" + ingredient.crashType();
   }

   public Object getUid(ErrorIngredient ingredient, UidContext context) {
      return ingredient.crashType();
   }

   public String getWildcardId(ErrorIngredient ingredient) {
      return "JEI_error";
   }

   public Object getGroupingUid(ErrorIngredient ingredient) {
      return IIngredientHelper.super.getGroupingUid(ingredient);
   }

   public ResourceLocation getResourceLocation(ErrorIngredient ingredient) {
      return ResourceLocation.fromNamespaceAndPath("jei", "error_" + ingredient.crashType().toString().toLowerCase(Locale.ROOT));
   }

   public ErrorIngredient copyIngredient(ErrorIngredient ingredient) {
      return ingredient;
   }

   public String getErrorInfo(@Nullable ErrorIngredient ingredient) {
      return ingredient == null ? "error ingredient: null" : this.getDisplayName(ingredient);
   }
}
