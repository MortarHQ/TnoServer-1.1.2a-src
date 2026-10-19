package de.ellpeck.naturesaura.data;

import de.ellpeck.naturesaura.reg.ICustomBlockState;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {
   public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
      super(output, "naturesaura", exFileHelper);
   }

   protected void registerStatesAndModels() {
      ModData.getAllModItems().forEach(i -> {
         if (i instanceof Block block) {
            if (block instanceof ICustomBlockState custom) {
               custom.generateCustomBlockState(this);
            } else {
               this.simpleBlock(block);
            }
         }
      });
   }
}
