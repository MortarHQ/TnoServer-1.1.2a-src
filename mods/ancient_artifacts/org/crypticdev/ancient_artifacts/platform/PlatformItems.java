package org.crypticdev.ancient_artifacts.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.platform.neoforge.PlatformItemsImpl;

public final class PlatformItems {
   private PlatformItems() {
   }

   @ExpectPlatform
   @Transformed
   public static Item createArtifactItem(ArtifactDefinition definition, Properties properties) {
      return PlatformItemsImpl.createArtifactItem(definition, properties);
   }

   @ExpectPlatform
   @Transformed
   public static Item get(ArtifactDefinition definition, Properties properties) {
      return PlatformItemsImpl.get(definition, properties);
   }
}
