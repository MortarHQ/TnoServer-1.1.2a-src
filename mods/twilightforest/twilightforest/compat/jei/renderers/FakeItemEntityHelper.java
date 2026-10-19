package twilightforest.compat.jei.renderers;

import java.util.Objects;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.jei.FakeItemEntity;
import twilightforest.compat.jei.JEICompat;

public class FakeItemEntityHelper implements IIngredientHelper<FakeItemEntity> {
   public IIngredientType<FakeItemEntity> getIngredientType() {
      return JEICompat.FAKE_ITEM_ENTITY;
   }

   public String getDisplayName(FakeItemEntity ingredient) {
      return ingredient.stack().getItem().getDescription().toString();
   }

   public String getUniqueId(FakeItemEntity ingredient, UidContext context) {
      return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ingredient.stack().getItem())).toString();
   }

   public ResourceLocation getResourceLocation(FakeItemEntity ingredient) {
      return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ingredient.stack().getItem()));
   }

   public FakeItemEntity copyIngredient(FakeItemEntity ingredient) {
      return ingredient;
   }

   public String getErrorInfo(@Nullable FakeItemEntity ingredient) {
      return ingredient == null ? "null" : ingredient.stack().toString();
   }
}
