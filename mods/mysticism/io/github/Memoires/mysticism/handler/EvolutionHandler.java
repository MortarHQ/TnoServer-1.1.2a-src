package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import io.github.Memoires.mysticism.config.race.AngelConfig;
import io.github.Memoires.mysticism.config.race.PhantomConfig;
import io.github.Memoires.mysticism.entity.monster.OkamiEntity;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingTickEvent;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.entity.human.golem.BoneGolemEntity;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.PossessionEvent;
import io.github.manasmods.tensura.race.RaceHelper;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class EvolutionHandler {
   public static void init() {
      EntityEvents.LIVING_POST_TICK.register((LivingTickEvent)target -> {
         if (target instanceof Player player && player instanceof ServerPlayer serverPlayer) {
            Races playerRace = RaceAPI.getRaceFrom(player);
            Optional<ManasRaceInstance> raceInstanceOpt = playerRace.getRace();
            int demonEssenceUsed = serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DAEMON_ESSENCE.get()));
            int cryptidEssenceUsed = serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)MysticismMobDropItems.CRYPTID_ESSENCE.get()));
            IExistence existence = TensuraStorages.getExistenceFrom(serverPlayer);
            if (raceInstanceOpt.isPresent()) {
               ManasRaceInstance raceInstance = raceInstanceOpt.get();
               if (raceInstance.getRace() == MysticismRaces.LESSER_ANGEL.get()) {
                  AngelConfig.FallenLesserAngel CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).FallenLesserAngel;
                  PhantomConfig.Phantom CONFIGP = ((PhantomConfig)ConfigRegistry.getConfig(PhantomConfig.class)).Phantom;
                  if (demonEssenceUsed == CONFIG.essenceRequired || existence.getAlignment().equals(Alignment.MAJIN)) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN_LESSER_ANGEL.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_angel_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }

                  if (cryptidEssenceUsed == CONFIGP.essenceRequired) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.PHANTOM.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.phantom_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }
               } else if (raceInstance.getRace() == MysticismRaces.GREATER_ANGEL.get()) {
                  AngelConfig.FallenGreaterAngel CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).FallenGreaterAngel;
                  PhantomConfig.FieldOfficer CONFIGP = ((PhantomConfig)ConfigRegistry.getConfig(PhantomConfig.class)).FieldOfficer;
                  if (demonEssenceUsed == CONFIG.essenceRequired || existence.getAlignment().equals(Alignment.MAJIN)) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN_GREATER_ANGEL.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_angel_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }

                  if (cryptidEssenceUsed == CONFIGP.essenceRequired) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FIELD_OFFICER.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.phantom_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }
               } else if (raceInstance.getRace() == MysticismRaces.ARCH_ANGEL.get()) {
                  AngelConfig.FallenArchAngel CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).FallenArchAngel;
                  PhantomConfig.General CONFIGP = ((PhantomConfig)ConfigRegistry.getConfig(PhantomConfig.class)).General;
                  if (demonEssenceUsed == CONFIG.essenceRequired || existence.getAlignment().equals(Alignment.MAJIN)) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN_ARCH_ANGEL.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_angel_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }

                  if (cryptidEssenceUsed == CONFIGP.essenceRequired) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.GENERAL.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.phantom_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }
               } else if (raceInstance.getRace() == MysticismRaces.CHERUB.get()) {
                  AngelConfig.FallenCherub CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).FallenCherub;
                  PhantomConfig.StaffOfficer CONFIGP = ((PhantomConfig)ConfigRegistry.getConfig(PhantomConfig.class)).StaffOfficer;
                  if (demonEssenceUsed == CONFIG.essenceRequired || existence.getAlignment().equals(Alignment.MAJIN)) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN_CHERUB.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_angel_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }

                  if (cryptidEssenceUsed == CONFIGP.essenceRequired) {
                     existence.setAlignment(Alignment.CHAOS);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.STAFF_OFFICER.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.staff_officer_evolution").withStyle(ChatFormatting.DARK_RED));
                  }
               } else if (raceInstance.getRace() == MysticismRaces.SERAPH.get()) {
                  AngelConfig.FallenSeraph CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).FallenSeraph;
                  PhantomConfig.MysticAngel CONFIGP = ((PhantomConfig)ConfigRegistry.getConfig(PhantomConfig.class)).MysticAngel;
                  if (demonEssenceUsed == CONFIG.essenceRequired || existence.getAlignment().equals(Alignment.MAJIN)) {
                     existence.setAlignment(Alignment.MAJIN);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN_SERAPH.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_angel_evolution").withStyle(ChatFormatting.LIGHT_PURPLE));
                  }

                  if (cryptidEssenceUsed == CONFIGP.essenceRequired) {
                     existence.setAlignment(Alignment.CHAOS);
                     RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.MYSTIC_ANGEL.get(), true, true);
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.staff_officer_evolution").withStyle(ChatFormatting.DARK_RED));
                  }
               }
            }
         }
      });
      TensuraEntityEvents.POSSESSION_EVENT
         .register(
            (PossessionEvent)(target, user) -> {
               if (target instanceof BoneGolemEntity && user instanceof LivingEntity entity) {
                  SkillHelper.learnSkill(entity, (ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get(), -2);
               }

               if (user instanceof Player player
                  && player instanceof ServerPlayer serverPlayer
                  && target instanceof LivingEntity livingEntity
                  && livingEntity instanceof OkamiEntity) {
                  Races playerRace = RaceAPI.getRaceFrom(player);
                  Optional<ManasRaceInstance> raceInstanceOpt = playerRace.getRace();
                  if (raceInstanceOpt.isPresent()) {
                     ManasRaceInstance raceInstance = raceInstanceOpt.get();
                     if (raceInstance.getRace() == MysticismRaces.GREATER_ANGEL.get() || raceInstance.getRace() == MysticismRaces.ARCH_ANGEL.get()) {
                        RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.TENGU.get(), true, true);
                        serverPlayer.sendSystemMessage(Component.translatable("mysticism.tengu_evolution").withStyle(ChatFormatting.YELLOW));
                     }
                  }
               }

               return EventResult.pass();
            }
         );
   }
}
