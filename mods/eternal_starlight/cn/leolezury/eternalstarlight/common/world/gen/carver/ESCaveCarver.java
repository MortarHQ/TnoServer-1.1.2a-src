package cn.leolezury.eternalstarlight.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class ESCaveCarver extends CaveWorldCarver {
   public ESCaveCarver(Codec<CaveCarverConfiguration> codec) {
      super(codec);
   }

   protected boolean canReplaceBlock(CaveCarverConfiguration carverConfiguration, BlockState blockState) {
      return super.canReplaceBlock(carverConfiguration, blockState) && blockState.getFluidState().isEmpty();
   }

   public BlockState getCarveState(CarvingContext carvingContext, CaveCarverConfiguration carverConfiguration, BlockPos blockPos, Aquifer aquifer) {
      return blockPos.getY() <= carverConfiguration.lavaLevel.resolveY(carvingContext) ? LAVA.createLegacyBlock() : CAVE_AIR;
   }
}
