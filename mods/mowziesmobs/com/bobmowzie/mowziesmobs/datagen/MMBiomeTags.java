package com.bobmowzie.mowziesmobs.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MMBiomeTags extends BiomeTagsProvider {
   public static final TagKey<Biome> HAS_MOWZIE_STRUCTURE = key("has_structure/has_mowzie_structure");
   public static final TagKey<Biome> IS_MAGICAL = key("is_magical");

   public MMBiomeTags(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "mowziesmobs", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(HAS_MOWZIE_STRUCTURE).addTag(Biomes.IS_OVERWORLD);
      this.tag(IS_MAGICAL).add(net.minecraft.world.level.biome.Biomes.DARK_FOREST);
   }

   private static TagKey<Biome> key(String path) {
      return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mowziesmobs", path));
   }
}
