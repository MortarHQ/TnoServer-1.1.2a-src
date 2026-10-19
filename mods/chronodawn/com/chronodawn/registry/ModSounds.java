package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
   public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create("chronodawn", Registries.SOUND_EVENT);
   public static final RegistrySupplier<SoundEvent> GEAR_IMPACT = registerSound("gear_impact");
   public static final RegistrySupplier<SoundEvent> COLOSSUS_STOMP = registerSound("colossus_stomp");
   public static final RegistrySupplier<SoundEvent> BOSS_POWER_UP = registerSound("boss_power_up");
   public static final RegistrySupplier<SoundEvent> BOSS_TELEPORT = registerSound("boss_teleport");
   public static final RegistrySupplier<SoundEvent> BOSS_CAST_SPELL = registerSound("boss_cast_spell");
   public static final RegistrySupplier<SoundEvent> BOSS_PREPARE_SUMMON = registerSound("boss_prepare_summon");
   public static final RegistrySupplier<SoundEvent> ENTROPY_ATTACK = registerSound("entropy_attack");
   public static final RegistrySupplier<SoundEvent> WARDEN_ROAR = registerSound("warden_roar");
   public static final RegistrySupplier<SoundEvent> TYRANT_BELL = registerSound("tyrant_bell");
   public static final RegistrySupplier<SoundEvent> ENTROPY_WITHER_ANGER = registerSound("entropy_wither_anger");
   public static final RegistrySupplier<SoundEvent> TELEPORTER_WARP = registerSound("teleporter_warp");
   public static final RegistrySupplier<SoundEvent> TELEPORTER_CHARGE_START = registerSound("teleporter_charge_start");
   public static final RegistrySupplier<SoundEvent> TELEPORTER_CHARGING = registerSound("teleporter_charging");
   public static final RegistrySupplier<SoundEvent> COLOSSUS_ACTIVATE = registerSound("colossus_activate");
   public static final RegistrySupplier<SoundEvent> GEAR_LAUNCH = registerSound("gear_launch");
   public static final RegistrySupplier<SoundEvent> GEAR_GROUND_IMPACT = registerSound("gear_ground_impact");
   public static final RegistrySupplier<SoundEvent> ENTROPY_BURST = registerSound("entropy_burst");
   public static final RegistrySupplier<SoundEvent> PHANTOM_TELEPORT = registerSound("phantom_teleport");
   public static final RegistrySupplier<SoundEvent> TIME_NEEDLE_BREAK = registerSound("time_needle_break");
   public static final RegistrySupplier<SoundEvent> TIME_NEEDLE_PLACE = registerSound("time_needle_place");
   public static final RegistrySupplier<SoundEvent> TYRANT_PHASE_CHANGE = registerSound("tyrant_phase_change");
   public static final RegistrySupplier<SoundEvent> CHRONO_AEGIS_ACTIVATE = registerSound("chrono_aegis_activate");
   public static final RegistrySupplier<SoundEvent> PORTAL_STABILIZE_SPAWN = registerSound("portal_stabilize_spawn");
   public static final RegistrySupplier<SoundEvent> PORTAL_STABILIZE_ACTIVATE = registerSound("portal_stabilize_activate");
   public static final RegistrySupplier<SoundEvent> TIME_COMPASS_CHIME = registerSound("time_compass_chime");
   public static final RegistrySupplier<SoundEvent> TIME_COMPASS_UPDATE = registerSound("time_compass_update");
   public static final RegistrySupplier<SoundEvent> TIME_COMPASS_BREAK = registerSound("time_compass_break");
   public static final RegistrySupplier<SoundEvent> SHIELD_ECHO_GENERATE = registerSound("shield_echo_generate");
   public static final RegistrySupplier<SoundEvent> SHIELD_ECHO_ACTIVE_PULSE = registerSound("shield_echo_active_pulse");
   public static final RegistrySupplier<SoundEvent> SHIELD_ECHO_CONSUME = registerSound("shield_echo_consume");
   public static final RegistrySupplier<SoundEvent> MUSIC_CHRONODAWN_DIMENSION = registerSound("music.chronodawn_dimension");

   private static RegistrySupplier<SoundEvent> registerSound(String name) {
      ResourceLocation id = CompatResourceLocation.create("chronodawn", name);
      return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
   }

   public static void register() {
      SOUNDS.register();
      ChronoDawn.LOGGER.debug("Registered ModSounds");
   }
}
