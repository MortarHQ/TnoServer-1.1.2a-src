package com.mortal_cultivation.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MortalCultivationEmiCategory extends EmiRecipeCategory {
   private final Component name;

   public MortalCultivationEmiCategory(ResourceLocation id, EmiRenderable icon, Component name) {
      super(id, icon);
      this.name = name;
   }

   public Component getName() {
      return this.name;
   }
}
