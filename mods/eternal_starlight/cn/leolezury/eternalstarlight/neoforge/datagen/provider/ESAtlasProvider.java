package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

public class ESAtlasProvider extends SpriteSourceProvider {
   public ESAtlasProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void gather() {
      this.atlas(SHIELD_PATTERNS_ATLAS).addSource(new DirectoryLister("entity/shields", "entity/shields/"));
   }
}
