package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESBiomeTagsProvider extends BiomeTagsProvider {
   public ESBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ESTags.Biomes.PERMAFROST).add(new ResourceKey[]{ESBiomes.STARLIGHT_PERMAFROST_FOREST, ESBiomes.PERMAFROST_PEAKS});
      this.tag(ESTags.Biomes.HAS_PORTAL_RUINS_COMMON).add(new ResourceKey[]{Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.SAVANNA_PLATEAU});
      this.tag(ESTags.Biomes.HAS_PORTAL_RUINS_FOREST)
         .add(
            new ResourceKey[]{
               Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.WINDSWEPT_FOREST
            }
         );
      this.tag(ESTags.Biomes.HAS_PORTAL_RUINS_DESERT).add(new ResourceKey[]{Biomes.DESERT, Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS});
      this.tag(ESTags.Biomes.HAS_PORTAL_RUINS_JUNGLE).add(new ResourceKey[]{Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE});
      this.tag(ESTags.Biomes.HAS_PORTAL_RUINS_COLD).add(new ResourceKey[]{Biomes.SNOWY_PLAINS, Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA, Biomes.FROZEN_PEAKS});
      this.tag(ESTags.Biomes.HAS_GOLEM_FORGE)
         .add(
            new ResourceKey[]{
               ESBiomes.STARLIGHT_FOREST,
               ESBiomes.STARLIGHT_DENSE_FOREST,
               ESBiomes.UMBRAL_PLAINS,
               ESBiomes.GLIMMER_SCRUBLAND,
               ESBiomes.STARLIGHT_PERMAFROST_FOREST,
               ESBiomes.PERMAFROST_PEAKS,
               ESBiomes.STARLIGHT_TAIGA,
               ESBiomes.SCARLET_FOREST
            }
         );
      this.tag(ESTags.Biomes.HAS_CURSED_GARDEN)
         .add(
            new ResourceKey[]{
               ESBiomes.STARLIGHT_FOREST,
               ESBiomes.STARLIGHT_DENSE_FOREST,
               ESBiomes.UMBRAL_PLAINS,
               ESBiomes.GLIMMER_SCRUBLAND,
               ESBiomes.STARLIGHT_TAIGA,
               ESBiomes.SCARLET_FOREST
            }
         );
      this.tag(ESTags.Biomes.HAS_STRANGHOUL_DEN).add(ESBiomes.DARK_SWAMP);
   }
}
