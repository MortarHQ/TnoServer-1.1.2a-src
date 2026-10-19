package com.tom.storagemod.polymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class PolymorphHelper {
   public static Optional<RecipeHolder<CraftingRecipe>> getRecipe(Player pl, RecipeType<CraftingRecipe> crafting, CraftingInput input, Level level) {
      return PolymorphApi.getInstance().getRecipeManager().getPlayerRecipe(pl.containerMenu, crafting, input, level, pl);
   }
}
