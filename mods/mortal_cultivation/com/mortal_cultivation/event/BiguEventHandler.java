package com.mortal_cultivation.event;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class BiguEventHandler {
   private static final Set<ResourceLocation> BIGU_RACE_IDS = Set.of(
      id("golden_core_race"),
      id("nascent_soul_race"),
      id("spirit_transformation_race"),
      id("void_refinement_race"),
      id("unity_race"),
      id("great_ascension_race"),
      id("tribulation_transcendence_race"),
      id("true_immortal_race"),
      id("heavenly_dao_race"),
      id("dao_breaking_race"),
      id("demonic_golden_core_race"),
      id("demonic_nascent_soul_race"),
      id("demonic_spirit_transformation_race"),
      id("demonic_void_refinement_race"),
      id("demonic_unity_race"),
      id("demonic_great_ascension_race"),
      id("demonic_tribulation_transcendence_race"),
      id("demonic_true_immortal_race"),
      id("demonic_void_return_race"),
      id("sigil_master_race"),
      id("sigil_saint_race"),
      id("sigil_immortal_realm_race")
   );

   private static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", name);
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide) {
         if (player.tickCount % 20 == 0) {
            if (isBiguRace(player)) {
               FoodData foodData = player.getFoodData();
               foodData.setFoodLevel(20);
               foodData.setSaturation(20.0F);
            }
         }
      }
   }

   private static boolean isBiguRace(Player player) {
      Optional<ManasRaceInstance> raceOpt = RaceAPI.getRaceFrom(player).getRace();
      return raceOpt.<Boolean>map(instance -> isBiguRace(instance.getRace())).orElse(false);
   }

   private static boolean isBiguRace(ManasRace race) {
      return race != null && race.getRegistryName() != null ? BIGU_RACE_IDS.contains(race.getRegistryName()) : false;
   }
}
