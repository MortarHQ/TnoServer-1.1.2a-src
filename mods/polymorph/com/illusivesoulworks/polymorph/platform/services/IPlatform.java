package com.illusivesoulworks.polymorph.platform.services;

import com.illusivesoulworks.polymorph.api.common.base.IPolymorphNetwork;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.api.common.capability.IPlayerRecipeData;
import java.nio.file.Path;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPlatform {
   Path getGameDir();

   Path getConfigDir();

   boolean isModLoaded(String var1);

   boolean isModFileLoaded(String var1);

   boolean isShaped(Recipe<?> var1);

   boolean isSameShape(Recipe<?> var1, Recipe<?> var2);

   IPlayerRecipeData getRecipeData(Player var1);

   IBlockEntityRecipeData getRecipeData(BlockEntity var1);

   IPolymorphNetwork getPacketDistributor();
}
