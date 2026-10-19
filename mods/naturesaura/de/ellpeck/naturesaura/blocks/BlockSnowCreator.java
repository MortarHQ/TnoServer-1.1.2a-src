package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntitySnowCreator;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockSnowCreator extends BlockContainerImpl implements IVisualizable, ICustomBlockState, IPickaxeBreakable {
   public BlockSnowCreator() {
      super("snow_creator", BlockEntitySnowCreator.class, Properties.ofFullCopy(Blocks.STONE_BRICKS));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      BlockEntity tile = level.getBlockEntity(pos);
      if (tile instanceof BlockEntitySnowCreator) {
         int radius = ((BlockEntitySnowCreator)tile).getRange();
         if (radius > 0) {
            return new AABB(pos).inflate(radius);
         }
      }

      return null;
   }

   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 14412287;
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_top"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }
}
