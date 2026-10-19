package twilightforest.data.custom.structuredefinitions;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.world.components.structures.courtyard.CourtyardMain;

public class NagaCourtyardStructureDefinitionGenerator extends StructureTemplateDefinitionProvider {
   public NagaCourtyardStructureDefinitionGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "twilightforest", existingFileHelper, "Naga Courtyard");
   }

   @Override
   protected void generatePools() {
      this.add("courtyard/spawner", CourtyardMain.CENTER_POOL, 100);
   }
}
