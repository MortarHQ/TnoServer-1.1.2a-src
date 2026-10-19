package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCItemStack")
@Document("neoforge/api/item/MCItemStack")
public class MCItemStack implements NeoForgeItemStack {
   private final ItemStack internal;
   private final IngredientConditions conditions;
   private final IngredientTransformers transformers;

   public MCItemStack(ItemStack internal) {
      this.internal = internal;
      this.conditions = new IngredientConditions();
      this.transformers = new IngredientTransformers();
   }

   public MCItemStack(ItemStack internal, IngredientConditions conditions, IngredientTransformers transformers) {
      this.internal = internal;
      this.conditions = conditions;
      this.transformers = transformers;
   }

   @Override
   public IItemStack[] getItems() {
      return new IItemStack[]{this.copy()};
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
      return new MCItemStack(this.getImmutableInternal(), this.conditions.copy(), this.transformers.copy());
   }

   @Override
   public IItemStack asMutable() {
      return this != IItemStack.empty() && this.getInternal() != ItemStack.EMPTY
         ? new MCItemStackMutable(this.getInternal(), this.conditions.copy(), this.transformers.copy())
         : new MCItemStackMutable(new ItemStack(Items.AIR));
   }

   @Override
   public IItemStack asImmutable() {
      return this;
   }

   @Override
   public boolean isImmutable() {
      return true;
   }

   @Override
   public ItemStack getInternal() {
      return this.internal;
   }

   @Override
   public IItemStack modify(Consumer<ItemStack> stackModifier) {
      ItemStack newStack = this.getInternal().copy();
      stackModifier.accept(newStack);
      return new MCItemStack(newStack);
   }

   @Override
   public IItemStack modifyThis(Consumer<IItemStack> modifier) {
      IItemStack newStack = this.copy();
      modifier.accept(newStack);
      return newStack;
   }

   @Override
   public int hashCode() {
      return ItemStack.hashItemAndComponents(this.getInternal());
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ItemStack thatStack = ((MCItemStack)o).getInternal();
         ItemStack thisStack = this.getInternal();
         return ItemStack.matches(thisStack, thatStack);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return this.getCommandString();
   }
}
