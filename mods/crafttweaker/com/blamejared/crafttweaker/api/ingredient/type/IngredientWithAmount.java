package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/ingredient/type/IngredientWithAmount")
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IngredientWithAmount")
public record IngredientWithAmount(IIngredient ingredient, int amount) implements IIngredientWithAmount {
   @Override
   public String getCommandString() {
      return this.amount == 1 ? this.ingredient.getCommandString() : String.format("(%s) * %d", this.ingredient.getCommandString(), this.amount);
   }
}
