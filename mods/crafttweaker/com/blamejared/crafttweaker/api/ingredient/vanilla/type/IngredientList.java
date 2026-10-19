package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class IngredientList implements CraftTweakerVanillaIngredient {
   private final List<Ingredient> children;
   private final boolean requiresTesting;

   public static IngredientList of(List<Ingredient> children) {
      return new IngredientList(children);
   }

   public static Ingredient ingredient(List<Ingredient> children) {
      return Services.PLATFORM.getIngredientList(children);
   }

   private IngredientList(List<Ingredient> children) {
      this.children = children;
      this.requiresTesting = children.stream().anyMatch(Services.PLATFORM::doesIngredientRequireTesting);
   }

   @Override
   public boolean test(@Nullable ItemStack stack) {
      return stack == null ? false : this.children.stream().anyMatch(child -> child.test(stack));
   }

   @Override
   public List<ItemStack> getMatchingStacks() {
      return this.getChildren().stream().map(Ingredient::getItems).flatMap(Arrays::stream).toList();
   }

   @Override
   public boolean requiresTesting() {
      return this.requiresTesting;
   }

   @Override
   public boolean isEmpty() {
      return this.getChildren().stream().allMatch(Ingredient::isEmpty);
   }

   public List<Ingredient> getChildren() {
      return this.children;
   }

   public IngredientListSerializer serializer() {
      return IngredientListSerializer.INSTANCE;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IngredientList that = (IngredientList)o;
         return this.requiresTesting == that.requiresTesting && Objects.equals(this.getChildren(), that.getChildren());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getChildren(), this.requiresTesting);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("IngredientList{");
      sb.append("children=").append(this.children);
      sb.append(", requiresTesting=").append(this.requiresTesting);
      sb.append('}');
      return sb.toString();
   }
}
