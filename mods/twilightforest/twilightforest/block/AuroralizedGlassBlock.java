package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import twilightforest.util.ColorUtil;
import twilightforest.util.SimplexNoiseHelper;

public class AuroralizedGlassBlock extends TransparentBlock {
   public AuroralizedGlassBlock(Properties properties) {
      super(properties);
   }

   public Integer getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
      return ColorUtil.hsvToRGB(SimplexNoiseHelper.rippleFractalNoise(2, 128.0F, pos.above(128), 0.37F, 0.67F, 1.5F), 1.0F, 1.0F);
   }
}
