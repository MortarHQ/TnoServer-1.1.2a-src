package com.moepus.byepregen.mixin;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.CombiningPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "net.minecraft.world.level.levelgen.blockpredicates.AnyOfPredicate", remap = false)
public abstract class AnyOfPredicateMixin extends CombiningPredicate {
   protected AnyOfPredicateMixin(List<BlockPredicate> p_190455_) {
      super(p_190455_);
   }

   @Overwrite
   public boolean test(WorldGenLevel level, BlockPos pos) {
      List<BlockPredicate> predicates = this.predicates;
      int i = 0;

      for (int size = predicates.size(); i < size; i++) {
         if (predicates.get(i).test(level, pos)) {
            return true;
         }
      }

      return false;
   }
}
