package com.tensura_tno.event;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Player.BedSleepingProblem;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

public final class GhoulDaySleepHandler {
   private GhoulDaySleepHandler() {
   }

   private static boolean isSunWeakRace(Player player) {
      Optional<ManasRaceInstance> opt = RaceAPI.getRaceFrom(player).getRace();
      if (opt.isEmpty()) {
         return false;
      }

      ManasRace race = opt.get().getRace();
      return race == TensuraRaces.GHOUL.get()
         || race == TensuraRaces.VAMPIRE.get()
         || race == TensuraRaces.VAMPIRE_OVERCOMER.get()
         || race == TensuraRaces.VAMPIRE_LORD.get()
         || race == TensuraRaces.DIVINE_VAMPIRE.get()
         || race == TensuraRaces.WIGHT.get()
         || race == TensuraRaces.WIGHT_KING.get();
   }

   public static void onCanPlayerSleep(CanPlayerSleepEvent event) {
      if (event.getProblem() == BedSleepingProblem.NOT_POSSIBLE_NOW) {
         if (isSunWeakRace(event.getEntity())) {
            event.setProblem(null);
         }
      }
   }

   public static void onCanContinueSleeping(CanContinueSleepingEvent event) {
      if (event.getProblem() == BedSleepingProblem.NOT_POSSIBLE_NOW) {
         if (event.getEntity() instanceof Player player) {
            if (isSunWeakRace(player)) {
               event.setContinueSleeping(true);
            }
         }
      }
   }

   public static void onSleepFinished(SleepFinishedTimeEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         long dayTime = level.getDayTime();
         long dayOffset = dayTime % 24000L;
         if (dayOffset < 13000L) {
            long nightTarget = dayTime - dayOffset + 13000L;
            event.setTimeAddition(nightTarget);
         }
      }
   }
}
