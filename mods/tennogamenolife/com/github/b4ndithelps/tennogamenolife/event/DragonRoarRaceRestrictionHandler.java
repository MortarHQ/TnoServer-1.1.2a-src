package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.race.Doragonia.AranreivuRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.HartyleifRace;
import com.github.b4ndithelps.tennogamenolife.race.Doragonia.ReginreivuRace;
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
public final class DragonRoarRaceRestrictionHandler {
   private DragonRoarRaceRestrictionHandler() {
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
         ManasSkill farCry = (ManasSkill)TenreincarnationSkill.FAR_CRY.get();
         if (!isFinalDragonRace(race)) {
            SkillAPI.getSkillsFrom(player).getSkill(farCry).ifPresent(ignored -> SkillAPI.getSkillsFrom(player).forgetSkill(farCry));
         } else {
            if (SkillAPI.getSkillsFrom(player).getSkill(farCry).isEmpty()) {
               race.learnIntrinsicSkills(player);
            }

            if (SkillAPI.getSkillsFrom(player).getSkill(farCry).isPresent() && !race.getObtainedIntrinsicSkills().contains(farCry)) {
               race.addIntrinsicSkill(farCry);
               race.markDirty();
            }
         }
      }
   }

   private static boolean isFinalDragonRace(ManasRaceInstance race) {
      return race.getRace() instanceof AranreivuRace || race.getRace() instanceof HartyleifRace || race.getRace() instanceof ReginreivuRace;
   }
}
