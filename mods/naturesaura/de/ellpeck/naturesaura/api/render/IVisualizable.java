package de.ellpeck.naturesaura.api.render;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface IVisualizable {
   @OnlyIn(Dist.CLIENT)
   AABB getVisualizationBounds(Level var1, BlockPos var2);

   @OnlyIn(Dist.CLIENT)
   int getVisualizationColor(Level var1, BlockPos var2);
}
