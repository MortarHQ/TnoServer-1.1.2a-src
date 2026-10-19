package net.unusual.block_factorys_bosses.data.gen.tag;

import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseBiomeTagGen extends BiomeTagsProvider {
   public BossesRiseBiomeTagGen(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "block_factorys_bosses", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BossesRiseTags.Biomes.DRAGON_TOWER)
         .add(
            new ResourceKey[]{
               Biomes.PLAINS,
               Biomes.SUNFLOWER_PLAINS,
               Biomes.JUNGLE,
               Biomes.SPARSE_JUNGLE,
               Biomes.BAMBOO_JUNGLE,
               Biomes.SWAMP,
               Biomes.MANGROVE_SWAMP,
               Biomes.SAVANNA,
               Biomes.WINDSWEPT_GRAVELLY_HILLS,
               Biomes.WINDSWEPT_FOREST
            }
         );
      this.tag(BossesRiseTags.Biomes.SANDWORM_NEST).add(Biomes.DESERT);
      this.tag(BossesRiseTags.Biomes.UNDERWORLD_ARENA).add(new ResourceKey[]{Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY});
      this.tag(BossesRiseTags.Biomes.YETI_HIDEOUT).add(Biomes.SNOWY_PLAINS);
      this.tag(BossesRiseTags.Biomes.KRAKEN_SHIP).add(new ResourceKey[]{Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN});
   }
}
