package com.illusivesoulworks.polymorph.api.common.capability;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IBlockEntityRecipeData extends IRecipeData<BlockEntity> {
   void tick();

   boolean isEmpty();
}
