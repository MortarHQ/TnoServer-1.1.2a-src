package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.data.MysticismBiomeTags;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MysticismBiomeTagProvider extends BiomeTagsProvider {
   public MysticismBiomeTagProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, completableFuture, "mysticism", existingFileHelper);
   }

   protected void addTags(Provider arg) {
      this.tag(MysticismBiomeTags.EntitySpawn.OKAMI).addTag(BiomeTags.IS_MOUNTAIN);
      this.tag(MysticismBiomeTags.EntitySpawn.FIRE_BIOME).add(new ResourceKey[]{MysticismBiomes.FIRE_BIOME});
      this.tag(MysticismBiomeTags.EntitySpawn.WIND_BIOME).add(new ResourceKey[]{MysticismBiomes.WIND_BIOME});
      this.tag(MysticismBiomeTags.EntitySpawn.EARTH_BIOME).add(new ResourceKey[]{MysticismBiomes.EARTH_BIOME});
      this.tag(MysticismBiomeTags.EntitySpawn.SPACE_BIOME).add(new ResourceKey[]{MysticismBiomes.SPACE_BIOME});
      this.tag(MysticismBiomeTags.EntitySpawn.DARKNESS_BIOME).add(new ResourceKey[]{MysticismBiomes.DARKNESS_BIOME});
      this.tag(MysticismBiomeTags.EntitySpawn.ELEMENTAL_REALM)
         .add(
            new ResourceKey[]{
               MysticismBiomes.DARKNESS_BIOME,
               MysticismBiomes.WIND_BIOME,
               MysticismBiomes.WATER_BIOME,
               MysticismBiomes.FIRE_BIOME,
               MysticismBiomes.LIGHT_BIOME,
               MysticismBiomes.SPACE_BIOME,
               MysticismBiomes.EARTH_BIOME
            }
         );
      this.tag(MysticismBiomeTags.OreSpawn.ICE_SPIKES).add(new ResourceKey[]{Biomes.ICE_SPIKES});
      this.tag(MysticismBiomeTags.EntitySpawn.MEMOIRES).addTag(MysticismBiomeTags.EntitySpawn.ELEMENTAL_REALM);
   }
}
