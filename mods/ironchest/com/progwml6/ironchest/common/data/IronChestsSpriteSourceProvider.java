package com.progwml6.ironchest.common.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

public class IronChestsSpriteSourceProvider extends SpriteSourceProvider {
   public IronChestsSpriteSourceProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<Provider> lookupProvider) {
      super(output, lookupProvider, "ironchest", fileHelper);
   }

   protected void gather() {
      this.atlas(CHESTS_ATLAS).addSource(new DirectoryLister("model", "model/"));
   }
}
