package org.crypticdev.ancient_artifacts.platform.neoforge;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.neoforge.integration.curios.ArtifactCurioItem;

public final class PlatformItemsImpl {
   private PlatformItemsImpl() {
   }

   public static Item createArtifactItem(ArtifactDefinition definition, Properties properties) {
      return new ArtifactCurioItem(definition, properties);
   }
}
