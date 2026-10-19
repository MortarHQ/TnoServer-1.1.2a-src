package net.regions_unexplored.block.type.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import net.regions_unexplored.client.color.RuColors;

public class PrismaglassBlock extends TransparentBlock implements BeaconBeamBlock, IBlockExtension {
   public PrismaglassBlock(Properties properties) {
      super(properties);
   }

   public DyeColor getColor() {
      return DyeColor.WHITE;
   }

   public Integer getBeaconColorMultiplier(BlockState state, LevelReader levelReader, BlockPos pos, BlockPos beaconPos) {
      return levelReader instanceof Level level
         ? RuColors.getRainbowColor(pos.getX(), pos.getZ() + (float)level.getGameTime() / 8.0F)
         : DyeColor.WHITE.getTextureDiffuseColor();
   }
}
