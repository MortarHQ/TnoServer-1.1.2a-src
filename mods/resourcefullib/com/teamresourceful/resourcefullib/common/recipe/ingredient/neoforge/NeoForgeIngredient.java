package com.teamresourceful.resourcefullib.common.recipe.ingredient.neoforge;

import com.teamresourceful.resourcefullib.common.recipe.ingredient.CodecIngredient;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

public record NeoForgeIngredient<T extends CodecIngredient<T>>(T ingredient) implements ICustomIngredient {
   public boolean test(@NotNull ItemStack arg) {
      return this.ingredient.test(arg);
   }

   @NotNull
   public Stream<ItemStack> getItems() {
      return this.ingredient.getStacks().stream();
   }

   public boolean isSimple() {
      return !this.ingredient.isComplex();
   }

   @NotNull
   public IngredientType<?> getType() {
      return Objects.requireNonNull((IngredientType<?>)NeoForgeRegistries.INGREDIENT_TYPES.get(this.ingredient.serializer().id()));
   }
}
