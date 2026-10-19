package com.moepus.byepregen.mixin.placement;

import com.moepus.byepregen.Feature.FastPlacementContext;
import com.moepus.byepregen.Feature.FastPlacementModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RepeatingPlacement.class, remap = false)
public abstract class RepeatingPlacementMixin implements FastPlacementModifier {
   @Shadow
   protected abstract int count(RandomSource var1, BlockPos var2);

   @Override
   public void byepregen$collectPositions(FastPlacementContext context, int x, int y, int z, int nextIndex) {
      BlockPos pos = context.modifierPos(x, y, z);
      int i = 0;

      for (int count = this.count(context.random(), pos); i < count; i++) {
         context.apply(nextIndex, x, y, z);
      }
   }
}
