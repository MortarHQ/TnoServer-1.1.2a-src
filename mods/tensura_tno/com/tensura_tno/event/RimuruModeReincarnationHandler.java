package com.tensura_tno.event;

import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.TensuraTNOMod;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public final class RimuruModeReincarnationHandler {
   private static final ResourceLocation SLIME_RACE_ID = ResourceLocation.fromNamespaceAndPath("tensura", "slime");
   private static volatile boolean registered = false;

   private RimuruModeReincarnationHandler() {
   }

   public static void register() {
      if (!registered) {
         synchronized (RimuruModeReincarnationHandler.class) {
            if (!registered) {
               RaceEvents.SET_RACE.register((SetRaceEvent)(oldInstance, owner, newInstance, evolution, teleport, message) -> {
                  if (!(owner instanceof ServerPlayer player)) {
                     return EventResult.pass();
                  } else {
                     if (newInstance == null) {
                        return EventResult.pass();
                     }

                     ResourceLocation newRaceId = newInstance.getRace().getRegistryName();
                     if (newRaceId == null) {
                        return EventResult.pass();
                     }

                     if (!SLIME_RACE_ID.equals(newRaceId)) {
                        return EventResult.pass();
                     }

                     if (!player.getPersistentData().getBoolean("tensura_tno:pending_rimuru_mode")) {
                        return EventResult.pass();
                     }

                     MinecraftServer srv = player.getServer();
                     boolean dedicated = srv != null && srv.isDedicatedServer();
                     if (dedicated && !TensuraTNOCompatConfig.isRimuruModeCheckboxEnabled()) {
                        player.getPersistentData().remove("tensura_tno:pending_rimuru_mode");
                        return EventResult.pass();
                     }

                     try {
                        if (player.level().getGameRules().getBoolean(TensuraGameRules.RIMURU_MODE)) {
                           player.getPersistentData().remove("tensura_tno:pending_rimuru_mode");
                           return EventResult.pass();
                        }
                     } catch (Throwable var11) {
                     }

                     player.getPersistentData().remove("tensura_tno:pending_rimuru_mode");
                     grantRimuruSkills(player);
                     return EventResult.pass();
                  }
               });
               registered = true;
            }
         }
      }
   }

   private static void grantRimuruSkills(ServerPlayer player) {
      try {
         for (ManasSkill skill : ReincarnationMenu.RIMURU_SKILLS) {
            TensuraSkillInstance instance = new TensuraSkillInstance(skill);
            instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            if (SkillHelper.learnSkill(player, instance) && !ReincarnationMenu.RIMURU_UNIQUE_SKILLS.contains(skill)) {
               Races races = RaceAPI.getRaceFrom(player);
               Optional<ManasRaceInstance> optional = races.getRace();
               if (optional.isPresent()) {
                  optional.get().addIntrinsicSkill(skill);
                  optional.get().markDirty();
                  races.markDirty();
               }
            }
         }

         TensuraTNOMod.LOGGER.info("[TensuraTNO] Granted Rimuru-mode skills to {}", player.getGameProfile().getName());
      } catch (Throwable t) {
         TensuraTNOMod.LOGGER.error("[TensuraTNO] Failed to grant Rimuru-mode skills to {}: {}", player.getGameProfile().getName(), t.toString());
      }
   }
}
