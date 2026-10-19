package io.github.Memoires.mysticism.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MysticismBiomeTags {
   static TagKey<Biome> modTag(String name) {
      return create(ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }

   static TagKey<Biome> create(ResourceLocation name) {
      return TagKey.create(Registries.BIOME, name);
   }

   public static class EntitySpawn {
      public static TagKey<Biome> OKAMI = MysticismBiomeTags.modTag("okami_spawn");
      public static TagKey<Biome> MEMOIRES = MysticismBiomeTags.modTag("memoires_spawn");
      public static TagKey<Biome> FIRE_BIOME = MysticismBiomeTags.modTag("fire_biome_spawn");
      public static TagKey<Biome> WIND_BIOME = MysticismBiomeTags.modTag("wind_biome_spawn");
      public static TagKey<Biome> EARTH_BIOME = MysticismBiomeTags.modTag("earth_biome_spawn");
      public static TagKey<Biome> SPACE_BIOME = MysticismBiomeTags.modTag("space_biome_spawn");
      public static TagKey<Biome> DARKNESS_BIOME = MysticismBiomeTags.modTag("darkness_biome_spawn");
      public static TagKey<Biome> ELEMENTAL_REALM = MysticismBiomeTags.modTag("elemental_realm");
   }

   public static class OreSpawn {
      public static TagKey<Biome> ICE_SPIKES = MysticismBiomeTags.modTag("ice_spikes");
   }
}
