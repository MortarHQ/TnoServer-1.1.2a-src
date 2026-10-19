package com.tensura_tno.world.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface BiomeLookup {
   ResourceLocation biomeAt(BlockPos var1);
}
