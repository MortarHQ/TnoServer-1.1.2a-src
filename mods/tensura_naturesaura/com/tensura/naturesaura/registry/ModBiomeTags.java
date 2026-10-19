package com.tensura.naturesaura.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class ModBiomeTags {
   public static final TagKey<Biome> AURA_RICH = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "aura_rich"));
   public static final TagKey<Biome> AURA_DRY = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "aura_dry"));
   public static final TagKey<Biome> AURA_BARREN = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "aura_barren"));
   public static final TagKey<Biome> AURA_TAINTED = TagKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "aura_tainted")
   );
   public static final TagKey<Biome> AURA_UNDERWORLD_SPIKES = TagKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "aura_underworld_spikes")
   );
   public static final TagKey<Biome> AURA_UNDERWORLD_RED_SANDS = TagKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "aura_underworld_red_sands")
   );

   private ModBiomeTags() {
   }
}
