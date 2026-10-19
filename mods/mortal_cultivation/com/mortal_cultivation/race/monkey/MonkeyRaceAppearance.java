package com.mortal_cultivation.race.monkey;

import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public final class MonkeyRaceAppearance {
   private static final Set<ResourceLocation> RACES = Set.of(
      id("monkey_race"),
      id("spirit_monkey_race"),
      id("demon_monkey_race"),
      id("red_buttocked_horse_monkey_race"),
      id("immortal_ape_race"),
      id("enlightened_stone_monkey_race"),
      id("six_eared_macaque_race"),
      id("soundwave_immortal_ape_race"),
      id("cosmic_mind_ape_race"),
      id("sun_wukong_race"),
      id("sun_wukong_heavenly_equal_race"),
      id("sun_wukong_victorious_buddha_race")
   );

   private MonkeyRaceAppearance() {
   }

   public static boolean hasTail(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> RACES.contains(instance.getRace().getRegistryName())).orElse(false);
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", path);
   }
}
