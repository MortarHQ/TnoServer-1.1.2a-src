package net.pixeldreamstudios.mythika.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.pixeldreamstudios.mythika.Mythika;

public final class TagRegistry {
   public static final TagKey<Biome> CYCLOPS_SPAWN_BIOMES = TagKey.create(Registries.BIOME, Mythika.id("cyclopes_spawn_in"));
   public static final TagKey<Biome> MINOTAUR_SPAWN_BIOMES = TagKey.create(Registries.BIOME, Mythika.id("minotaurs_spawn_in"));
   public static final TagKey<Biome> SATYR_SPAWN_BIOMES = TagKey.create(Registries.BIOME, Mythika.id("satyrs_spawn_in"));

   private TagRegistry() {
   }
}
