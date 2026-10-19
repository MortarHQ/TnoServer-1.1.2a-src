package com.blamejared.crafttweaker.mixin.common.access.item;

import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.Value;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Ingredient.class)
public interface AccessIngredient {
   @Invoker("fromValues")
   static Ingredient crafttweaker$callFromValues(Stream<? extends Value> param0) {
      throw new UnsupportedOperationException();
   }

   @Accessor("values")
   Value[] crafttweaker$getValues();

   @Accessor("itemStacks")
   void crafttweaker$setItemStacks(ItemStack[] var1);
}
