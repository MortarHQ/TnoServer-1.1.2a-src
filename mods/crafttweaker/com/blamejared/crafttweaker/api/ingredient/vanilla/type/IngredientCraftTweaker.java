package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweakerBase;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientCraftTweakerSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class IngredientCraftTweaker<T extends IIngredient> implements CraftTweakerVanillaIngredient, IngredientCraftTweakerBase {
   private final T crtIngredient;

   public static <T extends IIngredient> IngredientCraftTweaker<T> of(T crtIngredient) {
      return new IngredientCraftTweaker<>(crtIngredient);
   }

   public static <T extends IIngredient> Ingredient ingredient(T internal) {
      return Services.PLATFORM.getCraftTweakerIngredient(internal);
   }

   private IngredientCraftTweaker(T crtIngredient) {
      this.crtIngredient = crtIngredient;
   }

   @Override
   public boolean test(@Nullable ItemStack stack) {
      return IngredientCraftTweakerBase.super.test(stack);
   }

   @Override
   public T getCrTIngredient() {
      return this.crtIngredient;
   }

   @Override
   public List<ItemStack> getMatchingStacks() {
      return Arrays.stream(this.getCrTIngredient().getItems()).map(IItemStack::getInternal).toList();
   }

   @Override
   public boolean requiresTesting() {
      return true;
   }

   @Override
   public boolean isEmpty() {
      return this.getMatchingStacks().isEmpty();
   }

   public IngredientCraftTweakerSerializer serializer() {
      return IngredientCraftTweakerSerializer.INSTANCE;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IngredientCraftTweaker<?> that = (IngredientCraftTweaker<?>)o;
         return Objects.equals(this.crtIngredient, that.crtIngredient);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.crtIngredient);
   }
}
