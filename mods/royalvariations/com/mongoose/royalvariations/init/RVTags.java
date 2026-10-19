package com.mongoose.royalvariations.init;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class RVTags {
   public static class Biomes {
      public static final TagKey<Biome> COMMON_BLACKLIST = tag("mob_spawn/common_blacklist");
      public static final TagKey<Biome> ROYAL_ZOMBIE_SPAWN = tag("mob_spawn/royal_zombie");
      public static final TagKey<Biome> ROYAL_SKELETON_SPAWN = tag("mob_spawn/royal_skeleton");
      public static final TagKey<Biome> ROYAL_CREEPER_SPAWN = tag("mob_spawn/royal_creeper");
      public static final TagKey<Biome> ROYAL_ENDERMAN_SPAWN = tag("mob_spawn/royal_enderman");

      private static TagKey<Biome> tag(String name) {
         return create(RoyalVariations.location(name));
      }

      private static TagKey<Biome> create(ResourceLocation p_215874_) {
         return TagKey.create(Registries.BIOME, p_215874_);
      }
   }
}
