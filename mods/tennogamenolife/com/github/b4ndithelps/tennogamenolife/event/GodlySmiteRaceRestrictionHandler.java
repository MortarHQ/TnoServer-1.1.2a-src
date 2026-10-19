package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.race.Flugel.ArutoshuRace;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class GodlySmiteRaceRestrictionHandler {
   private GodlySmiteRaceRestrictionHandler() {
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         synchronizeSkill(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && player.tickCount % 100 == 0) {
         synchronizeSkill(player);
      }
   }

   private static void synchronizeSkill(ServerPlayer player) {
      ManasRaceInstance race = (ManasRaceInstance)RaceAPI.getRaceFrom(player).getRace().orElse(null);
      if (race != null) {
         ManasSkill godlySmite = (ManasSkill)TenreincarnationSkill.GODLY_SMITE.get();
         if (!(race.getRace() instanceof ArutoshuRace)) {
            SkillAPI.getSkillsFrom(player).getSkill(godlySmite).ifPresent(ignored -> SkillAPI.getSkillsFrom(player).forgetSkill(godlySmite));
         } else {
            if (SkillAPI.getSkillsFrom(player).getSkill(godlySmite).isEmpty()) {
               race.learnIntrinsicSkills(player);
            }

            if (SkillAPI.getSkillsFrom(player).getSkill(godlySmite).isPresent() && !race.getObtainedIntrinsicSkills().contains(godlySmite)) {
               race.addIntrinsicSkill(godlySmite);
               race.markDirty();
            }
         }
      }
   }
}
