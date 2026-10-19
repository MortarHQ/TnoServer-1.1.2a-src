package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCItemStackMutable")
@Document("neoforge/api/item/MCItemStackMutable")
public class MCItemStackMutable implements NeoForgeItemStack {
   private final ItemStack internal;
   private final IngredientConditions conditions;
   private final IngredientTransformers transformers;

   public MCItemStackMutable(ItemStack internal) {
      this.internal = internal;
      this.conditions = new IngredientConditions();
      this.transformers = new IngredientTransformers();
   }

   public MCItemStackMutable(ItemStack internal, IngredientConditions conditions, IngredientTransformers transformers) {
      this.internal = internal;
      this.conditions = conditions;
      this.transformers = transformers;
   }

   @Override
   public IItemStack[] getItems() {
      return new IItemStack[]{this};
   }

   @Override
   public IngredientTransformers transformers() {
      return this.transformers;
   }

   @Override
   public IngredientConditions conditions() {
      return this.conditions;
   }

   @Override
   public IItemStack copy() {
      return new MCItemStackMutable(this.getImmutableInternal(), this.conditions.copy(), this.transformers.copy());
   }

   @Override
   public IItemStack asMutable() {
      return this;
   }

   @Override
   public IItemStack asImmutable() {
      return new MCItemStack(this.getInternal().copy(), this.conditions.copy(), this.transformers.copy());
   }

   @Override
   public boolean isImmutable() {
      return false;
   }

   @Override
   public ItemStack getInternal() {
      return this.internal;
   }

   @Override
   public IItemStack modify(Consumer<ItemStack> stackModifier) {
      stackModifier.accept(this.getInternal());
      return this;
   }

   @Override
   public IItemStack modifyThis(Consumer<IItemStack> modifier) {
      modifier.accept(this);
      return this;
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ItemStack thatStack = ((MCItemStackMutable)o).getInternal();
         ItemStack thisStack = this.getInternal();
         return ItemStack.matches(thisStack, thatStack);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return ItemStack.hashItemAndComponents(this.getInternal());
   }

   @Override
   public String toString() {
      return this.getCommandString();
   }
}
