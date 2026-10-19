package org.crypticdev.ancient_artifacts.neoforge.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.registry.ArtifactItems;
import org.crypticdev.ancient_artifacts.registry.Artifacts;

public class AncientArtifactsModelProvider extends ItemModelProvider {
   public AncientArtifactsModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
      super(output, modid, existingFileHelper);
   }

   public String getName() {
      return "";
   }

   protected void registerModels() {
      for (ArtifactDefinition artifactDefinition : Artifacts.ALL) {
         this.basicItem(ArtifactItems.get(artifactDefinition));
      }
   }
}
