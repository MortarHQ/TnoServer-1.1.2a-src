package io.github.Memoires.mysticism.neoforge.data;

import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MysticismBlockStateProvider extends BlockStateProvider {
   public MysticismBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
      super(output, "mysticism", exFileHelper);
   }

   protected void registerStatesAndModels() {
      this.simpleBlock((Block)MysticismBlocks.ICE_ORE.get());
   }
}
