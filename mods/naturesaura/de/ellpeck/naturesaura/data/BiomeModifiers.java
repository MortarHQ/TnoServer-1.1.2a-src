package de.ellpeck.naturesaura.data;

import de.ellpeck.naturesaura.gen.ModFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags.Biomes;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class BiomeModifiers {
   public static final ResourceKey<BiomeModifier> AURA_BLOOM = createKey("aura_bloom");
   public static final ResourceKey<BiomeModifier> AURA_CACTUS = createKey("aura_cactus");
   public static final ResourceKey<BiomeModifier> WARPED_AURA_MUSHROOM = createKey("warped_aura_mushroom");
   public static final ResourceKey<BiomeModifier> CRIMSON_AURA_MUSHROOM = createKey("crimson_aura_mushroom");
   public static final ResourceKey<BiomeModifier> AURA_MUSHROOM = createKey("aura_mushroom");

   private static ResourceKey<BiomeModifier> createKey(String id) {
      return ResourceKey.create(Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath("naturesaura", id));
   }

   public static void bootstrap(BootstrapContext<BiomeModifier> context) {
      HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
      HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);
      context.register(
         AURA_BLOOM,
         new AddFeaturesBiomeModifier(
            biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(new Holder[]{placedGetter.getOrThrow(ModFeatures.Placed.AURA_BLOOM)}),
            Decoration.VEGETAL_DECORATION
         )
      );
      context.register(
         AURA_CACTUS,
         new AddFeaturesBiomeModifier(
            biomeGetter.getOrThrow(Biomes.IS_SANDY),
            HolderSet.direct(new Holder[]{placedGetter.getOrThrow(ModFeatures.Placed.AURA_CACTUS)}),
            Decoration.VEGETAL_DECORATION
         )
      );
      context.register(
         AURA_MUSHROOM,
         new AddFeaturesBiomeModifier(
            biomeGetter.getOrThrow(Biomes.IS_MUSHROOM),
            HolderSet.direct(new Holder[]{placedGetter.getOrThrow(ModFeatures.Placed.AURA_MUSHROOM)}),
            Decoration.VEGETAL_DECORATION
         )
      );
      context.register(
         CRIMSON_AURA_MUSHROOM,
         new AddFeaturesBiomeModifier(
            biomeGetter.getOrThrow(BiomeTags.IS_NETHER),
            HolderSet.direct(new Holder[]{placedGetter.getOrThrow(ModFeatures.Placed.CRIMSON_AURA_MUSHROOM)}),
            Decoration.VEGETAL_DECORATION
         )
      );
      context.register(
         WARPED_AURA_MUSHROOM,
         new AddFeaturesBiomeModifier(
            biomeGetter.getOrThrow(BiomeTags.IS_NETHER),
            HolderSet.direct(new Holder[]{placedGetter.getOrThrow(ModFeatures.Placed.WARPED_AURA_MUSHROOM)}),
            Decoration.VEGETAL_DECORATION
         )
      );
   }
}
