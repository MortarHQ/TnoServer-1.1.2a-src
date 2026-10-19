package io.github.Memoires.mysticism.data;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class MysticismRaceTags {
   public static final TagKey<ManasRace> DIREWOLF_RACE = modTag("direwolf");

   static TagKey<ManasRace> modTag(String name) {
      return create(ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }

   static TagKey<ManasRace> create(ResourceLocation name) {
      return TagKey.create(RaceAPI.getRaceRegistryKey(), name);
   }
}
