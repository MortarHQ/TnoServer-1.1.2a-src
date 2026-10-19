package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JNEBlockStateProvider extends BlockStateProvider {
   public JNEBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
      super(output, "netherexp", exFileHelper);
   }

   protected void registerStatesAndModels() {
      this.simpleBlock(JNEBlocks.DAMP_SILTMARRAM.get(), this.models().getExistingFile(NetherExp.netherexpPath("block/damp_siltmarram")));
      this.simpleBlock(JNEBlocks.MOIST_SILTMARRAM.get(), this.models().getExistingFile(NetherExp.netherexpPath("block/moist_siltmarram")));
      this.simpleBlock(JNEBlocks.DRY_SILTMARRAM.get(), this.models().getExistingFile(NetherExp.netherexpPath("block/dry_siltmarram")));
   }
}
