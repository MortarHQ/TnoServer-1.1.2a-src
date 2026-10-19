package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.blocks.tiles.BlockEntityPotionGenerator;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockPotionGenerator extends BlockContainerImpl implements ICustomBlockState, IPickaxeBreakable {
   public BlockPotionGenerator() {
      super("potion_generator", BlockEntityPotionGenerator.class, Properties.of().strength(5.0F));
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_bottom"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }
}
