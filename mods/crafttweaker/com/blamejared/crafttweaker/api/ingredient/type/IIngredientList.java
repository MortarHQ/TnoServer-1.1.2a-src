package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IIngredientList")
@Document("vanilla/api/ingredient/type/IIngredientList")
public class IIngredientList implements IIngredient {
   public static final ResourceLocation ID = CraftTweakerConstants.rl("list");
   private final IIngredient[] ingredients;

   @ZenCodeType.Constructor
   public IIngredientList(IIngredient[] ingredients) {
      this.ingredients = flatten(ingredients);
   }

   private static IIngredient[] flatten(IIngredient[] ingredients) {
      if (ingredients.length < 2) {
         return ingredients;
      }

      IIngredient[] result = new IIngredient[ingredients.length];
      int actualLength = 0;

      for (IIngredient ingredient : ingredients) {
         if (ingredient instanceof IIngredientList list) {
            IIngredient[] within = list.ingredients;
            int length = actualLength + within.length;
            if (length != actualLength) {
               if (length > result.length) {
                  result = Arrays.copyOf(result, length);
               }

               System.arraycopy(within, 0, result, actualLength, within.length);
               actualLength = length;
            }
         } else if (!ingredient.isEmpty()) {
            result[actualLength++] = ingredient;
            if (actualLength == result.length) {
               result = Arrays.copyOf(result, actualLength * 3 / 2);
            }
         }
      }

      return Arrays.copyOf(result, actualLength);
   }

   @Override
   public boolean isEmpty() {
      return this.ingredients.length == 0 || IIngredient.super.isEmpty();
   }

   @Override
   public boolean matches(IItemStack stack) {
      for (IIngredient item : this.ingredients) {
         if (item.matches(stack)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public IIngredientList or(IIngredient other) {
      IIngredient[] newArray = Arrays.copyOf(this.ingredients, this.ingredients.length + 1);
      newArray[this.ingredients.length] = other;
      return new IIngredientList(newArray);
   }

   @Override
   public IIngredient transform(IIngredientTransformer transformer) {
      for (int i = 0; i < this.ingredients.length; i++) {
         this.ingredients[i] = this.ingredients[i].transform(transformer);
      }

      return this;
   }

   @Override
   public IIngredient condition(IIngredientCondition condition) {
      for (int i = 0; i < this.ingredients.length; i++) {
         this.ingredients[i] = this.ingredients[i].condition(condition);
      }

      return this;
   }

   @Override
   public IItemStack getRemainingItem(IItemStack stack) {
      for (IIngredient item : this.ingredients) {
         if (item.matches(stack)) {
            return item.getRemainingItem(stack);
         }
      }

      return IIngredient.super.getRemainingItem(stack);
   }

   @Override
   public IngredientTransformers transformers() {
      return IngredientTransformers.EMPTY;
   }

   @Override
   public IngredientConditions conditions() {
      return IngredientConditions.EMPTY;
   }

   @Override
   public Ingredient asVanillaIngredient() {
      return IngredientList.ingredient(Arrays.stream(this.ingredients).map(IIngredient::asVanillaIngredient).collect(Collectors.toList()));
   }

   @Override
   public String getCommandString() {
      return Arrays.stream(this.ingredients).map(IIngredient::getCommandString).collect(Collectors.joining(" | "));
   }

   @Override
   public IItemStack[] getItems() {
      List<IItemStack> stacks = new ArrayList<>();
      Arrays.stream(this.ingredients).map(ing -> Arrays.asList(ing.getItems())).forEach(stacks::addAll);
      return stacks.toArray(new IItemStack[0]);
   }

   public IIngredient[] getIngredients() {
      return this.ingredients;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IIngredientList that = (IIngredientList)o;
         return Arrays.equals(this.ingredients, that.ingredients);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.ingredients);
   }

   @Override
   public String toString() {
      return this.getCommandString();
   }
}
