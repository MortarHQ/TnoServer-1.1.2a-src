package com.mortal_cultivation.event;

import com.mortal_cultivation.ability.skill.unique.GreatSageSkill;
import com.mortal_cultivation.ability.skill.unique.QiFlightSkill;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.RaceAbilityActivationEvent;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class RaceFlightAbilityHandler {
   private static final float RACE_FLIGHT_SPEED = 0.05F;
   private static final Set<UUID> ACTIVE_RACE_FLIGHT_PLAYERS = ConcurrentHashMap.newKeySet();
   private static final Set<ResourceLocation> FLIGHT_RACE_IDS = Set.of(
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
      id("sigil_master_race"),
      id("sigil_saint_race"),
      id("sigil_immortal_realm_race"),
      id("demonic_golden_core_race"),
      id("demonic_nascent_soul_race"),
      id("demonic_spirit_transformation_race"),
      id("demonic_void_refinement_race"),
      id("demonic_unity_race"),
      id("demonic_great_ascension_race"),
      id("demonic_tribulation_transcendence_race"),
      id("demonic_true_immortal_race"),
      id("demonic_void_return_race"),
      id("immortal_ape_race"),
      id("enlightened_stone_monkey_race"),
      id("six_eared_macaque_race"),
      id("sun_wukong_race"),
      id("soundwave_immortal_ape_race"),
      id("sun_wukong_heavenly_equal_race"),
      id("cosmic_mind_ape_race"),
      id("sun_wukong_victorious_buddha_race")
   );

   private RaceFlightAbilityHandler() {
   }

   public static void init() {
      RaceEvents.ACTIVATE_ABILITY.register((RaceAbilityActivationEvent)(instance, owner) -> {
         if (!(owner instanceof Player player && !owner.level().isClientSide())) {
            return EventResult.pass();
         } else {
            if (!supportsRaceFlight(instance.getRace())) {
               return EventResult.pass();
            }

            toggleRaceFlight(player);
            return EventResult.interruptFalse();
         }
      });
      RaceEvents.SET_RACE.register((SetRaceEvent)(oldRace, entity, newRace, evolution, teleport, message) -> {
         if (entity instanceof Player player) {
            syncRaceFlight(player, newRace.getRace());
         }

         return EventResult.pass();
      });
   }

   public static boolean supportsRaceFlight(Object race) {
      return race instanceof ManasRace manasRace ? supportsRaceFlight(manasRace) : false;
   }

   public static boolean isRaceFlightActive(Player player) {
      return ACTIVE_RACE_FLIGHT_PLAYERS.contains(player.getUUID());
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide()) {
         syncRaceFlight(player, getCurrentRace(player));
      }
   }

   private static void toggleRaceFlight(Player player) {
      UUID playerId = player.getUUID();
      if (ACTIVE_RACE_FLIGHT_PLAYERS.remove(playerId)) {
         revokeRaceFlight(player);
         player.displayClientMessage(Component.translatable("mortal_cultivation.ability.race_flight.disabled"), true);
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.6F, 1.2F);
      } else {
         ACTIVE_RACE_FLIGHT_PLAYERS.add(playerId);
         grantRaceFlight(player, true);
         player.displayClientMessage(Component.translatable("mortal_cultivation.ability.race_flight.enabled"), true);
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.6F, 1.0F);
         if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.serverLevel().sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.2, player.getZ(), 20, 0.3, 0.15, 0.3, 0.01);
         }
      }
   }

   private static void syncRaceFlight(Player player, ManasRace race) {
      UUID playerId = player.getUUID();
      boolean canUseRaceFlight = supportsRaceFlight(race);
      boolean raceFlightEnabled = ACTIVE_RACE_FLIGHT_PLAYERS.contains(playerId);
      if (canUseRaceFlight && raceFlightEnabled) {
         if (!player.getAbilities().mayfly && !hasOtherFlightSource(player)) {
            grantRaceFlight(player, false);
         }
      } else {
         if (!canUseRaceFlight) {
            ACTIVE_RACE_FLIGHT_PLAYERS.remove(playerId);
         }

         if (raceFlightEnabled) {
            revokeRaceFlight(player);
         }
      }
   }

   private static void grantRaceFlight(Player player, boolean startFlying) {
      if (!hasOtherFlightSource(player) && !player.isCreative() && !player.isSpectator()) {
         boolean changed = false;
         if (!player.getAbilities().mayfly) {
            player.getAbilities().mayfly = true;
            changed = true;
         }

         if (startFlying && !player.getAbilities().flying) {
            player.getAbilities().flying = true;
            changed = true;
         }

         if (changed) {
            player.getAbilities().setFlyingSpeed(0.05F);
            player.onUpdateAbilities();
         }
      }
   }

   private static void revokeRaceFlight(Player player) {
      if (!hasOtherFlightSource(player) && !player.isCreative() && !player.isSpectator()) {
         boolean changed = false;
         if (player.getAbilities().flying) {
            player.getAbilities().flying = false;
            changed = true;
         }

         if (player.getAbilities().mayfly) {
            player.getAbilities().mayfly = false;
            changed = true;
         }

         if (changed) {
            player.getAbilities().setFlyingSpeed(0.05F);
            player.onUpdateAbilities();
         }
      }
   }

   private static boolean hasOtherFlightSource(Player player) {
      return QiFlightSkill.isQiFlightActive(player) || GreatSageSkill.isCloudWalkerActive(player);
   }

   private static ManasRace getCurrentRace(Player player) {
      Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(player).getRace();
      return race.<ManasRace>map(ManasRaceInstance::getRace).orElse(null);
   }

   private static boolean supportsRaceFlight(ManasRace race) {
      return race != null && race.getRegistryName() != null ? FLIGHT_RACE_IDS.contains(race.getRegistryName()) : false;
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", path);
   }
}
