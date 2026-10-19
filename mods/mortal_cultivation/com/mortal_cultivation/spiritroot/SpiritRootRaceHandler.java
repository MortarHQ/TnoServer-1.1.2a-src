package com.mortal_cultivation.spiritroot;

import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public final class SpiritRootRaceHandler {
   private static boolean initialized;

   private SpiritRootRaceHandler() {
   }

   public static void init() {
      if (!initialized) {
         RaceEvents.SET_RACE.register((SetRaceEvent)(oldRace, entity, newRace, evolution, teleport, message) -> {
            if (entity instanceof ServerPlayer serverPlayer && SpiritRootData.hasRoot(serverPlayer) && !shouldKeepRoot(newRace)) {
               SpiritRootData.clearRoot(serverPlayer, true);
            }

            return EventResult.pass();
         });
         initialized = true;
      }
   }

   public static boolean shouldKeepRoot(Player player) {
      return RaceAPI.getRaceFrom(player).getRace().map(SpiritRootRaceHandler::shouldKeepRoot).orElse(false);
   }

   public static boolean shouldKeepRoot(@Nullable ManasRaceInstance raceInstance) {
      if (raceInstance == null) {
         return false;
      }

      ResourceLocation raceId = raceInstance.getRace().getRegistryName();
      return raceId != null && "mortal_cultivation".equals(raceId.getNamespace());
   }
}
