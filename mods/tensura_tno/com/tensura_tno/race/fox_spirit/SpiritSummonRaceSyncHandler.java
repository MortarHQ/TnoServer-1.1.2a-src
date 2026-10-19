package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.ability.skill.SpiritSummonSkill;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class SpiritSummonRaceSyncHandler {
   private static final Set<ResourceLocation> FOX_SPIRIT_RACE_IDS = Set.of(
      id("baby_spirit_fox"), id("fox_spirit_envoy"), id("spirit_fox_contract_master"), id("mystic_fox_master"), id("heavenly_fox_sovereign")
   );
   private static volatile boolean registered = false;

   private SpiritSummonRaceSyncHandler() {
   }

   public static void register() {
      if (!registered) {
         synchronized (SpiritSummonRaceSyncHandler.class) {
            if (!registered) {
               RaceEvents.SET_RACE.register((SetRaceEvent)(oldInstance, owner, newInstance, evolution, teleport, message) -> {
                  if (owner instanceof ServerPlayer player) {
                     if (newInstance == null) {
                        return EventResult.pass();
                     }

                     ResourceLocation newRaceId = newInstance.getRace().getRegistryName();
                     if (newRaceId == null) {
                        return EventResult.pass();
                     }

                     if (!FOX_SPIRIT_RACE_IDS.contains(newRaceId)) {
                        return EventResult.pass();
                     }

                     int count = SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntities(player).size();
                     newInstance.getOrCreateTag().putInt("absorbedTypesCount", count);
                     return EventResult.pass();
                  } else {
                     return EventResult.pass();
                  }
               });
               registered = true;
            }
         }
      }
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensura_tno", path);
   }
}
