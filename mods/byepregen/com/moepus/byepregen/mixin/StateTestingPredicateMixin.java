package com.moepus.byepregen.mixin;

import com.moepus.byepregen.Feature.FastBlockPredicateOptimizer;
import com.moepus.byepregen.Feature.FastStateTestingPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.StateTestingPredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = StateTestingPredicate.class, priority = 1100, remap = false)
public abstract class StateTestingPredicateMixin implements FastStateTestingPredicate {
   @Shadow
   @Final
   protected Vec3i offset;

   @Shadow
   protected abstract boolean test(BlockState var1);

   @Override
   public final Vec3i bpg$getOffset() {
      return this.offset;
   }

   @Override
   public final boolean bpg$testState(BlockState state) {
      return this.test(state);
   }

   @Overwrite
   public final boolean test(WorldGenLevel level, BlockPos pos) {
      return this.test(FastBlockPredicateOptimizer.getState(level, pos, this.offset));
   }
}
