package net.jadenxgamer.netherexp.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BrewingStandBlock.class)
public class BrewingStandBlockMixin {
   @WrapMethod(method = "animateTick")
   private void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
      if ((Boolean)JNEConfigs.IMPROVED_BREWING_STAND_PARTICLES.get()) {
         double x = pos.getX() + 0.4 + random.nextFloat() * 0.2;
         double y = pos.getY() + 0.7 + random.nextFloat() * 0.3;
         double z = pos.getZ() + 0.4 + random.nextFloat() * 0.2;
         CommonParticles.brewingStandParticle(level, pos, random, x, y, z);
      } else {
         original.call(new Object[]{state, level, pos, random});
      }
   }
}
