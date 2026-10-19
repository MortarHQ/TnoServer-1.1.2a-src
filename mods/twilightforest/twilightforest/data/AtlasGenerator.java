package twilightforest.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import twilightforest.TwilightForestMod;
import twilightforest.client.MagicPaintingTextureManager;
import twilightforest.entity.MagicPaintingVariant;

public class AtlasGenerator extends SpriteSourceProvider {
   public static final Map<ResourceLocation, MagicPaintingVariant> MAGIC_PAINTING_HELPER = new HashMap<>();

   public AtlasGenerator(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
      super(output, provider, "twilightforest", helper);
   }

   protected void gather() {
      this.atlas(SHIELD_PATTERNS_ATLAS).addSource(new SingleFile(TwilightForestMod.prefix("entity/knightmetal_shield"), Optional.empty()));
      this.atlas(MagicPaintingTextureManager.ATLAS_INFO_LOCATION).addSource(new SingleFile(MagicPaintingTextureManager.BACK_SPRITE_LOCATION, Optional.empty()));
      MAGIC_PAINTING_HELPER.forEach((location, parallaxVariant) -> {
         location = location.withPrefix("magic_paintings/");

         for (MagicPaintingVariant.Layer layer : parallaxVariant.layers()) {
            this.atlas(MagicPaintingTextureManager.ATLAS_INFO_LOCATION).addSource(new SingleFile(location.withSuffix("/" + layer.path()), Optional.empty()));
         }
      });
   }
}
