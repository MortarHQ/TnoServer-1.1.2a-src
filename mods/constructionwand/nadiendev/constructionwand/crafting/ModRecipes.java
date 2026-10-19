package nadiendev.constructionwand.crafting;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
      BuiltInRegistries.RECIPE_SERIALIZER, "constructionwand"
   );
   public static final Supplier<SimpleCraftingRecipeSerializer<RecipeWandUpgrade>> WAND_UPGRADE = RECIPE_SERIALIZERS.register(
      "wand_upgrade", () -> new SimpleCraftingRecipeSerializer(RecipeWandUpgrade::new)
   );
}
