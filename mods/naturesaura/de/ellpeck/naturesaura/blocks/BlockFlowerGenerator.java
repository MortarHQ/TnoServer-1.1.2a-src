package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityFlowerGenerator;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.IAxeBreakable;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockFlowerGenerator extends BlockContainerImpl implements IVisualizable, ICustomBlockState, IAxeBreakable {
   public BlockFlowerGenerator() {
      super("flower_generator", BlockEntityFlowerGenerator.class, Properties.of().sound(SoundType.WOOD).strength(2.0F));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      return new AABB(pos).inflate(3.0, 1.0, 3.0);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 16772395;
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
