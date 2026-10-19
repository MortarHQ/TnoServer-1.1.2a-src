package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Arrays;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.WrappingIIngredient")
@Document("vanilla/api/ingredient/type/WrappingIIngredient")
public class WrappingIIngredient implements IIngredient {
   private final Ingredient ingredient;
   private final String commandString;
   private final IngredientConditions conditions = new IngredientConditions();
   private final IngredientTransformers transformers = new IngredientTransformers();

   public WrappingIIngredient(Ingredient ingredient, String commandString) {
      this.ingredient = ingredient;
      this.commandString = commandString;
   }

   @Override
   public boolean isEmpty() {
      return this.ingredient.isEmpty();
   }

   @Override
   public boolean matches(IItemStack stack) {
      return this.ingredient.test(stack.getInternal()) && this.conditions.test(stack);
   }

   @Override
   public Ingredient asVanillaIngredient() {
      return this.ingredient;
   }

   @Override
   public String getCommandString() {
      return this.commandString;
   }

   @Override
   public IItemStack[] getItems() {
      return Arrays.stream(this.ingredient.getItems())
         .map(itemStack -> IItemStack.of(itemStack, this.conditions(), this.transformers()))
         .toArray(IItemStack[]::new);
   }

   @Override
   public IngredientTransformers transformers() {
      return this.transformers;
   }

   @Override
   public IngredientConditions conditions() {
      return this.conditions;
   }
}
