package team.lodestar.lodestone.systems.datagen.providers;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class LodestoneBlockModelProvider extends BlockModelProvider {
   final Function<ResourceLocation, LodestoneBlockModelProvider.LodestoneBlockModelBuilder> factory;
   public static final HashMap<String, ResourceLocation> BLOCK_TEXTURE_CACHE = new HashMap<>();

   public LodestoneBlockModelProvider(LodestoneBlockStateProvider provider, PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
      super(output, modid, existingFileHelper);
      this.factory = l -> new LodestoneBlockModelProvider.LodestoneBlockModelBuilder(provider, l, existingFileHelper);
   }

   public CompletableFuture<?> run(CachedOutput cache) {
      return super.run(cache);
   }

   protected void registerModels() {
   }

   public BlockModelBuilder getBuilder(String path) {
      Preconditions.checkNotNull(path, "Path must not be null");
      ResourceLocation outputLoc = this.extendWithFolder(
         path.contains(":") ? ResourceLocation.parse(path) : ResourceLocation.fromNamespaceAndPath(this.modid, path)
      );
      this.existingFileHelper.trackGenerated(outputLoc, MODEL);
      return this.generatedModels.computeIfAbsent(outputLoc, this.factory);
   }

   public BlockModelBuilder nested() {
      return this.factory.apply(ResourceLocation.parse("dummy:dummy"));
   }

   public ResourceLocation extendWithFolder(ResourceLocation rl) {
      return rl.getPath().contains("/") ? rl : ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), this.folder + "/" + rl.getPath());
   }

   private static class LodestoneBlockModelBuilder extends BlockModelBuilder {
      public final LodestoneBlockStateProvider provider;

      public LodestoneBlockModelBuilder(LodestoneBlockStateProvider provider, ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
         super(outputLocation, existingFileHelper);
         this.provider = provider;
         LodestoneBlockModelProvider.BLOCK_TEXTURE_CACHE.clear();
      }

      public BlockModelBuilder texture(String key, ResourceLocation texture) {
         ResourceLocation actualLocation = texture;
         if (!texture.getNamespace().equals("minecraft") && !this.provider.staticTextures.contains(texture)) {
            String actualPath = texture.getPath().replace("block/", "block/" + LodestoneBlockStateProvider.getTexturePath());
            actualLocation = ResourceLocation.fromNamespaceAndPath(texture.getNamespace(), actualPath);
         }

         LodestoneBlockModelProvider.BLOCK_TEXTURE_CACHE.put(key, actualLocation);
         return (BlockModelBuilder)super.texture(key, actualLocation);
      }
   }
}
