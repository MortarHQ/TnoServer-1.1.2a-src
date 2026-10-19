package com.tensura_tno.recipe;

import com.tensura_tno.registry.TensuraTNORecipeSerializers;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import java.util.Arrays;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class NetheriteToMagisteel extends SmithingTransformRecipe {
   final Ingredient myTemplate;
   final Ingredient myBase;
   final Ingredient myAddition;
   final ItemStack myResult;
   final double minEp;
   final double maxEp;
   final double gain;
   final ResourceLocation evolution;

   public NetheriteToMagisteel(
      Ingredient template, Ingredient base, Ingredient addition, ItemStack result, double minEp, double maxEp, double gain, ResourceLocation evolution
   ) {
      super(template, base, addition, result);
      this.myTemplate = template;
      this.myBase = base;
      this.myAddition = addition;
      this.myResult = result;
      this.minEp = minEp;
      this.maxEp = maxEp;
      this.gain = gain;
      this.evolution = evolution;
   }

   public boolean matches(SmithingRecipeInput input, Level level) {
      ItemStack base = input.getItem(1);
      boolean baseMatches = !base.isEmpty() && Arrays.stream(this.myBase.getItems()).anyMatch(rep -> base.is(rep.getItem()));
      return this.myTemplate.test(input.getItem(0)) && baseMatches && this.myAddition.test(input.getItem(2));
   }

   public ItemStack assemble(SmithingRecipeInput input, Provider registries) {
      ItemStack result = super.assemble(input, registries);
      result.set((DataComponentType)TensuraDataComponents.MAX_EP.get(), this.maxEp);
      result.set((DataComponentType)TensuraDataComponents.EP.get(), this.minEp);
      result.set((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), this.minEp);
      result.set((DataComponentType)TensuraDataComponents.EP_GAIN.get(), this.gain);
      result.set((DataComponentType)TensuraDataComponents.EVOLUTION.get(), this.evolution);
      return result;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TensuraTNORecipeSerializers.NETHERITE_TO_MAGISTEEL.get();
   }
}
