package com.blamejared.crafttweaker.api.ingredient;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweakerBase;
import com.blamejared.crafttweaker.api.ingredient.type.TagIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Arrays;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.item.crafting.Ingredient.Value;

public class IngredientConverter {
   public static IIngredient fromIngredient(Ingredient ingredient) {
      if (ingredient == Ingredient.EMPTY) {
         return empty();
      } else if (ingredient instanceof IngredientCraftTweakerBase base) {
         return base.getCrTIngredient();
      } else {
         return Services.PLATFORM.isCustomIngredient(ingredient)
            ? new IIngredientList(Services.PLATFORM.getCustomIngredientItems(ingredient).map(IItemStack::of).toArray(IIngredient[]::new))
            : fromIItemLists(((AccessIngredient)ingredient).crafttweaker$getValues());
      }
   }

   private static IIngredient fromIItemLists(Value... itemLists) {
      IIngredient[] ingredients = Arrays.stream(itemLists)
         .map(IngredientConverter::fromIItemList)
         .filter(IngredientConverter::notEmptyStack)
         .toArray(IIngredient[]::new);
      return mergeIngredients(ingredients);
   }

   private static IIngredient fromIItemList(Value value) {
      if (value instanceof TagValue) {
         return fromTagList((TagValue)value);
      }

      IItemStack[] ingredients = value.getItems().stream().filter(stack -> !stack.isEmpty()).map(IItemStack::of).toArray(IItemStack[]::new);
      return mergeIngredients(ingredients);
   }

   private static IIngredient fromTagList(TagValue value) {
      TagKey<Item> key = value.tag();
      return new TagIngredient(key);
   }

   private static IIngredient empty() {
      return IIngredientEmpty.INSTANCE;
   }

   private static IIngredient mergeIngredients(IIngredient... ingredients) {
      if (ingredients.length == 0) {
         return empty();
      } else {
         return ingredients.length == 1 ? ingredients[0] : new IIngredientList(ingredients);
      }
   }

   private static boolean notEmptyStack(IIngredient iIngredient) {
      return !(iIngredient instanceof IItemStack) || !iIngredient.isEmpty();
   }
}
