package gardensofthedead.neoforge.datagen;

import gardensofthedead.registry.ModBiomes;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {
   public BiomeTagsProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(packOutput, lookupProvider, "gardens_of_the_dead", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      for (ResourceKey<Biome> biome : List.of(ModBiomes.SOULBLIGHT_FOREST, ModBiomes.WHISTLING_WOODS)) {
         this.tag(BiomeTags.IS_NETHER).add(biome);
         this.tag(BiomeTags.SNOW_GOLEM_MELTS);
         this.tag(Biomes.IS_DRY_NETHER).add(biome);
         this.tag(Biomes.IS_HOT_NETHER).add(biome);
      }

      this.tag(BiomeTags.HAS_BASTION_REMNANT).add(new ResourceKey[]{ModBiomes.SOULBLIGHT_FOREST, ModBiomes.WHISTLING_WOODS});
      this.tag(BiomeTags.HAS_NETHER_FOSSIL).add(ModBiomes.SOULBLIGHT_FOREST);
      this.tag(Biomes.IS_DENSE_VEGETATION_NETHER).add(new ResourceKey[]{ModBiomes.SOULBLIGHT_FOREST, ModBiomes.WHISTLING_WOODS});
      this.tag(Biomes.IS_NETHER_FOREST).add(new ResourceKey[]{ModBiomes.SOULBLIGHT_FOREST, ModBiomes.WHISTLING_WOODS});
   }
}
