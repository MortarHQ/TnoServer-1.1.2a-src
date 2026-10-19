package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweakerBase;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientIItemStackSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class IngredientIItemStack implements CraftTweakerVanillaIngredient, IngredientCraftTweakerBase {
   private final IItemStack internal;

   public static IngredientIItemStack of(IItemStack crtIngredient) {
      return new IngredientIItemStack(crtIngredient);
   }

   public static Ingredient ingredient(IItemStack internal) {
      return Services.PLATFORM.getIItemStackIngredient(internal);
   }

   private IngredientIItemStack(IItemStack internal) {
      this.internal = internal;
   }

   @Override
   public boolean test(@Nullable ItemStack stack) {
      return IngredientCraftTweakerBase.super.test(stack);
   }

   public IItemStack getCrTIngredient() {
      return this.internal;
   }

   @Override
   public List<ItemStack> getMatchingStacks() {
      return List.of(this.internal.getImmutableInternal());
   }

   @Override
   public boolean requiresTesting() {
      return true;
   }

   @Override
   public boolean isEmpty() {
      return this.getMatchingStacks().isEmpty();
   }

   public IngredientIItemStackSerializer serializer() {
      return IngredientIItemStackSerializer.INSTANCE;
   }

   @Override
   public String toString() {
      return "IngredientIItemStack{internal=" + this.internal + "}";
   }
}
