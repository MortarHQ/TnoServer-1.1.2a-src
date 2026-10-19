package com.illusivesoulworks.polymorph.api.common.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPolymorphRecipeManager {
   default <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> getPlayerRecipe(
      AbstractContainerMenu containerMenu, RecipeType<T> type, I inventory, Level level, Player player
   ) {
      return this.getPlayerRecipe(containerMenu, type, inventory, level, player, new ArrayList<>());
   }

   <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> getPlayerRecipe(
      AbstractContainerMenu var1, RecipeType<T> var2, I var3, Level var4, Player var5, List<RecipeHolder<T>> var6
   );

   <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> getBlockEntityRecipe(RecipeType<T> var1, I var2, Level var3, BlockEntity var4);
}
