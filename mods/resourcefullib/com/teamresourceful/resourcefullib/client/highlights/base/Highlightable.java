package com.teamresourceful.resourcefullib.client.highlights.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public interface Highlightable {
   @Nullable
   Highlight getHighlight(Level var1, BlockPos var2, BlockState var3);
}
