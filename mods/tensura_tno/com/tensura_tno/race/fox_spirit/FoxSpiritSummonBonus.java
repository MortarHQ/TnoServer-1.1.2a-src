package com.tensura_tno.race.fox_spirit;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public final class FoxSpiritSummonBonus {
   private static final int BASE_SUMMON_DURATION = 300;

   private FoxSpiritSummonBonus() {
   }

   public static float getBonusPercentage(Player player) {
      Optional<ManasRaceInstance> raceOpt = RaceAPI.getRaceFrom(player).getRace();
      if (raceOpt.isEmpty()) {
         return 0.0F;
      } else {
         ManasRace race = raceOpt.get().getRace();
         if (race == null) {
            return 0.0F;
         } else {
            ResourceLocation raceId = race.getRegistryName();
            if (raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "heavenly_fox_sovereign"))) {
               return 0.5F;
            } else if (raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "mystic_fox_master"))) {
               return 0.4F;
            } else if (raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_fox_contract_master"))) {
               return 0.25F;
            } else if (raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_spirit_envoy"))) {
               return 0.1F;
            } else {
               return raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "baby_spirit_fox")) ? 0.0F : 0.0F;
            }
         }
      }
   }

   public static boolean isFoxSpiritRace(Player player) {
      Optional<ManasRaceInstance> raceOpt = RaceAPI.getRaceFrom(player).getRace();
      if (raceOpt.isEmpty()) {
         return false;
      }

      ManasRace race = raceOpt.get().getRace();
      if (race == null) {
         return false;
      }

      ResourceLocation raceId = race.getRegistryName();
      ResourceLocation prefix = ResourceLocation.fromNamespaceAndPath("tensura_tno", "");
      return raceId.getNamespace().equals("tensura_tno")
         && (
            raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "baby_spirit_fox"))
               || raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_spirit_envoy"))
               || raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_fox_contract_master"))
               || raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "mystic_fox_master"))
               || raceId.equals(ResourceLocation.fromNamespaceAndPath("tensura_tno", "heavenly_fox_sovereign"))
         );
   }

   public static double applyBonus(double baseValue, float bonus) {
      return baseValue * (1.0 + bonus);
   }

   public static int getMaxSummons(Player player) {
      ResourceLocation raceId = getRaceId(player);
      if (raceId == null) {
         return 0;
      } else if (raceId.equals(RL("heavenly_fox_sovereign"))) {
         return 30;
      } else if (raceId.equals(RL("mystic_fox_master"))) {
         return 20;
      } else if (raceId.equals(RL("spirit_fox_contract_master"))) {
         return 14;
      } else if (raceId.equals(RL("fox_spirit_envoy"))) {
         return 8;
      } else {
         return raceId.equals(RL("baby_spirit_fox")) ? 3 : 0;
      }
   }

   public static int getSummonDurationSeconds(Player player) {
      ResourceLocation raceId = getRaceId(player);
      if (raceId == null) {
         return 300;
      } else if (raceId.equals(RL("heavenly_fox_sovereign"))) {
         return 1740;
      } else if (raceId.equals(RL("mystic_fox_master"))) {
         return 1020;
      } else if (raceId.equals(RL("spirit_fox_contract_master"))) {
         return 660;
      } else if (raceId.equals(RL("fox_spirit_envoy"))) {
         return 480;
      } else {
         return raceId.equals(RL("baby_spirit_fox")) ? 300 : 300;
      }
   }

   public static int getEvolutionLevel(Player player) {
      ResourceLocation raceId = getRaceId(player);
      if (raceId == null) {
         return -1;
      } else if (raceId.equals(RL("heavenly_fox_sovereign"))) {
         return 4;
      } else if (raceId.equals(RL("mystic_fox_master"))) {
         return 3;
      } else if (raceId.equals(RL("spirit_fox_contract_master"))) {
         return 2;
      } else if (raceId.equals(RL("fox_spirit_envoy"))) {
         return 1;
      } else {
         return raceId.equals(RL("baby_spirit_fox")) ? 0 : -1;
      }
   }

   @Nullable
   private static ResourceLocation getRaceId(Player player) {
      Optional<ManasRaceInstance> raceOpt = RaceAPI.getRaceFrom(player).getRace();
      if (raceOpt.isEmpty()) {
         return null;
      }

      ManasRace race = raceOpt.get().getRace();
      return race != null ? race.getRegistryName() : null;
   }

   private static ResourceLocation RL(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensura_tno", path);
   }
}
