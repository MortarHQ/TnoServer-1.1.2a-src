package twilightforest.item.recipe.travellers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import twilightforest.TFRegistries;
import twilightforest.init.TFRecipes;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;

public class TravellersGearModifierShapedRecipe extends TravellersGearModifierRecipe {
   protected final ShapedRecipePattern pattern;
   protected final boolean isRotated;

   public TravellersGearModifierShapedRecipe(ShapedRecipePattern pattern, ResourceKey<TravellersModifier> travellersModifier, boolean isRotated) {
      super(travellersModifier);
      this.pattern = pattern;
      this.isRotated = isRotated;
   }

   @Override
   public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
      return !super.matches(input, level) ? false : this.pattern.matches(input);
   }

   public boolean canCraftInDimensions(int width, int height) {
      return this.getHeight() <= height && this.getWidth() <= width;
   }

   @Override
   public int getWidth() {
      return this.pattern.width();
   }

   @Override
   public int getHeight() {
      return this.pattern.height();
   }

   @Override
   public boolean isShapeless() {
      return false;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.pattern.ingredients();
   }

   @Override
   public ResourceLocation getId() {
      return super.getId().withSuffix(this.isRotated ? "_rotated" : "");
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.MODIFIER_SHAPED_RECIPE_SERIALIZER.get();
   }

   public static class Serializer extends TravellersGearModifierRecipe.AbstractModifierRecipeSerializer<TravellersGearModifierShapedRecipe> {
      public Serializer() {
         super(
            RecordCodecBuilder.mapCodec(
               instance -> instance.group(
                     ShapedRecipePattern.MAP_CODEC.fieldOf("pattern").forGetter(recipe -> recipe.pattern),
                     ResourceKey.codec(TFRegistries.Keys.TRAVELLERS_MODIFIERS).fieldOf("modifier_key").forGetter(recipe -> recipe.travellersModifierKey),
                     Codec.BOOL.fieldOf("is_rotated").forGetter(recipe -> recipe.isRotated)
                  )
                  .apply(instance, TravellersGearModifierShapedRecipe::new)
            )
         );
      }
   }
}
