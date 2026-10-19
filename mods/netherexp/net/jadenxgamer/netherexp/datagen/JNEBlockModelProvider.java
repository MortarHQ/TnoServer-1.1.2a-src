package net.jadenxgamer.netherexp.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JNEBlockModelProvider extends BlockModelProvider {
   public JNEBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "netherexp", existingFileHelper);
   }

   protected void registerModels() {
   }
}
