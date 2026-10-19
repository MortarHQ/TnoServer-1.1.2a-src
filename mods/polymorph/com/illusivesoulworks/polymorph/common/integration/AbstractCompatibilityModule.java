package com.illusivesoulworks.polymorph.common.integration;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AbstractCompatibilityModule {
   protected void setup() {
   }

   protected void clientSetup() {
   }

   protected boolean selectRecipe(AbstractContainerMenu containerMenu, RecipeHolder<?> recipe) {
      return false;
   }

   protected boolean selectRecipe(BlockEntity blockEntity, RecipeHolder<?> recipe) {
      return false;
   }

   protected boolean openContainer(AbstractContainerMenu containerMenu, ServerPlayer serverPlayerEntity) {
      return false;
   }

   protected void disable() {
   }
}
