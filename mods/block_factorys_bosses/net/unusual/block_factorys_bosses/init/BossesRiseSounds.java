package net.unusual.block_factorys_bosses.init;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseSounds {
   public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, "block_factorys_bosses");
   public static final List<ResourceLocation> CANCELABLE_SOUNDS = new ArrayList<>();
   public static final Holder<SoundEvent> MUSIC_SKOR = registerMusic("skor");
   public static final Holder<SoundEvent> YETI_INTRO = registerMusic("yeti_intro");
   public static final Holder<SoundEvent> YETI_FOOTSTEP = registerEntitySound("yeti.footstep");
   public static final Holder<SoundEvent> YETI_DEATH = registerEntitySound("yeti.death");
   public static final Holder<SoundEvent> YETI_GROUNDSMASH = registerEntitySound("yeti.groundsmash");
   public static final Holder<SoundEvent> YETI_HANDSWIPE = registerEntitySound("yeti.handswipe");
   public static final Holder<SoundEvent> YETI_HANDSWIPE2 = registerEntitySound("yeti.handswipe2");
   public static final Holder<SoundEvent> YETI_HIT = registerEntitySound("yeti.hit");
   public static final Holder<SoundEvent> YETI_PUNCH = registerEntitySound("yeti.punch");
   public static final Holder<SoundEvent> YETI_ENRAGE = registerEntitySound("yeti.enrage");
   public static final Holder<SoundEvent> YETI_SPAWN = registerEntitySound("yeti.spawn");
   public static final Holder<SoundEvent> YETI_JUMP = registerEntitySound("yeti.jump");
   public static final Holder<SoundEvent> YETI_LAND = registerEntitySound("yeti.land");
   public static final Holder<SoundEvent> YETI_RANGED_CONTROL = registerEntitySound("yeti.ranged_control");
   public static final Holder<SoundEvent> YETI_RANGED_CONTROL_ICE_BLOCK = registerEntitySound("yeti.ranged_control_ice_block");
   public static final Holder<SoundEvent> YETI_ICE_BARRAGE = registerEntitySound("yeti.ice_barrage");
   public static final Holder<SoundEvent> ICE_BARRAGE_WHIRLPOOL = registerEntitySound("yeti.ice_barrage_whirlpool");
   public static final Holder<SoundEvent> ICE_GAUNTLET_PUNCH = registerItemSound("ice_gauntlet.punch");
   public static final Holder<SoundEvent> ICE_GAUNTLET_BLAST_1 = registerItemSound("ice_gauntlet.blast_1");
   public static final Holder<SoundEvent> ICE_GAUNTLET_BLAST_2 = registerItemSound("ice_gauntlet.blast_2");
   public static final Holder<SoundEvent> THROW_ICICLE = registerItemSound("ice_gauntlet.throw_icicle");
   public static final Holder<SoundEvent> ICICLE_PICKUP = registerItemSound("ice_gauntlet.icicle_pickup");
   public static final Holder<SoundEvent> ICICLE_SPAWN = registerEntitySound("ice_gauntlet.icicle_spawning");
   public static final Holder<SoundEvent> ICICLE_HURT = registerEntitySound("ice_gauntlet.icicle_hurt");
   public static final Holder<SoundEvent> ICICLE_BREAK = registerEntitySound("ice_gauntlet.icicle_break");
   public static final Holder<SoundEvent> MUSIC_ASHLORD = registerMusic("ashlord");
   public static final Holder<SoundEvent> DRAGON_FOOTSTEP = registerEntitySound("dragon.footstep");
   public static final Holder<SoundEvent> DRAGON_BITE = registerEntitySound("dragon.bite");
   public static final Holder<SoundEvent> DRAGON_CLAW = registerEntitySound("dragon.claw");
   public static final Holder<SoundEvent> DRAGON_TAILSWIPE = registerEntitySound("dragon.tailswipe");
   public static final Holder<SoundEvent> DRAGON_WING = registerEntitySound("dragon.wing");
   public static final Holder<SoundEvent> DRAGON_HIT = registerEntitySound("dragon.hit");
   public static final Holder<SoundEvent> DRAGON_SPAWN = registerEntitySound("dragon.spawn");
   public static final Holder<SoundEvent> DRAGON_FIRELAYER = registerEntitySound("dragon.firelayer");
   public static final Holder<SoundEvent> DRAGON_TRANSITION = registerEntitySound("dragon.phase_transition");
   public static final Holder<SoundEvent> DRAGON_ROAR = registerEntitySound("dragon.roar");
   public static final Holder<SoundEvent> DRAGON_FIREBALLS = registerEntitySound("dragon.fireballs");
   public static final Holder<SoundEvent> DRAGON_DEATH = registerEntitySound("dragon.death");
   public static final Holder<SoundEvent> FIRE_SMELTED_ITEM = registerEntitySound("dragon.fire_smelted_item");
   public static final Holder<SoundEvent> MUSIC_HELVAR = registerMusic("helvar");
   public static final Holder<SoundEvent> KNIGHT_FOOTSTEP = registerEntitySound("knight.footstep");
   public static final Holder<SoundEvent> KNIGHT_INTRO = registerEntitySound("knight.intro");
   public static final Holder<SoundEvent> KNIGHT_HURT = registerEntitySound("knight.hurt");
   public static final Holder<SoundEvent> KNIGHT_BLOCK = registerEntitySound("knight.block");
   public static final Holder<SoundEvent> KNIGHT_ATTACK_VOICELINE = registerEntitySound("knight.attack_voiceline");
   public static final Holder<SoundEvent> KNIGHT_JUMPSPIN_1 = registerEntitySound("knight.jumpspin_1", true);
   public static final Holder<SoundEvent> KNIGHT_JUMPSPIN_2 = registerEntitySound("knight.jumpspin_2", true);
   public static final Holder<SoundEvent> KNIGHT_JUMPSPIN_3 = registerEntitySound("knight.jumpspin_3", true);
   public static final Holder<SoundEvent> KNIGHT_INTRODUCTION_ATTACK = registerEntitySound("knight.introduction_attack", true);
   public static final Holder<SoundEvent> KNIGHT_VERTICAL_SLASH = registerEntitySound("knight.vertical_slash", true);
   public static final Holder<SoundEvent> KNIGHT_VERTICAL_SLASH_TRANS = registerEntitySound("knight.vertical_slash_transition", true);
   public static final Holder<SoundEvent> KNIGHT_HORIZONTAL_SLASH = registerEntitySound("knight.horizontal_slash", true);
   public static final Holder<SoundEvent> KNIGHT_HORIZONTAL_SLASH_TRANS = registerEntitySound("knight.horizontal_slash_transition", true);
   public static final Holder<SoundEvent> KNIGHT_DODGE = registerEntitySound("knight.dodge");
   public static final Holder<SoundEvent> KNIGHT_KNOCKED_DOWN = registerEntitySound("knight.knocked_down");
   public static final Holder<SoundEvent> KNIGHT_COMBO_1 = registerEntitySound("knight.combo1", true);
   public static final Holder<SoundEvent> KNIGHT_COMBO_2 = registerEntitySound("knight.combo2", true);
   public static final Holder<SoundEvent> KNIGHT_STACK_REMOVE = registerEntitySound("knight.stack_remove", true);
   public static final Holder<SoundEvent> KNIGHT_THRUST = registerEntitySound("knight.thrust", true);
   public static final Holder<SoundEvent> KNIGHT_LIGHT_ATTACK_1 = registerEntitySound("knight.light_attack_1", true);
   public static final Holder<SoundEvent> KNIGHT_LIGHT_ATTACK_2 = registerEntitySound("knight.light_attack_2", true);
   public static final Holder<SoundEvent> KNIGHT_HEAVY_ATTACK = registerEntitySound("knight.heavy_attack", true);
   public static final Holder<SoundEvent> KNIGHT_LIGHT_HEAVY_ATTACK = registerEntitySound("knight.light_heavy_attack", true);
   public static final Holder<SoundEvent> KNIGHT_RESURRECT = registerEntitySound("knight.resurrection");
   public static final Holder<SoundEvent> KNIGHT_REVENGE_ATTACK = registerEntitySound("knight.revenge_attack", true);
   public static final Holder<SoundEvent> KNIGHT_KNOCK_OFF = registerEntitySound("knight.knock_off");
   public static final Holder<SoundEvent> KNIGHT_DEATH = registerEntitySound("knight.death");
   public static final Holder<SoundEvent> KNIGHT_FAKE_DEATH = registerEntitySound("knight.fake_death");
   public static final Holder<SoundEvent> WISP_EXPLODE = registerEntitySound("knight.wisp_explode");
   public static final Holder<SoundEvent> WISP_EXPLODE_LAST = registerEntitySound("knight.wisp_explode_last");
   public static final Holder<SoundEvent> RIFT_OPEN = registerEntitySound("barrage_rift.rift_open");
   public static final Holder<SoundEvent> RIFT_IDLE = registerEntitySound("barrage_rift.rift_idle");
   public static final Holder<SoundEvent> RIFT_CLOSE = registerEntitySound("barrage_rift.closing");
   public static final Holder<SoundEvent> RIFT_SLASH = registerEntitySound("barrage_rift.slash_air");
   public static final Holder<SoundEvent> RIFT_JUMP = registerEntitySound("barrage_rift.jump");
   public static final Holder<SoundEvent> RIFT_FIREBALL = registerEntitySound("barrage_rift.rift_fireball");
   public static final Holder<SoundEvent> UNDERWORLD_ARENA_DOOR_OPEN = registerBlockSound("underworld_arena_door.open");
   public static final Holder<SoundEvent> UNDERWORLD_ARENA_DOOR_KEY_INSERTION = registerBlockSound("underworld_arena_door.key_insertion");
   public static final Holder<SoundEvent> UNDERWORLD_ARENA_DOOR_CLOSE = registerBlockSound("underworld_arena_door.close");
   public static final Holder<SoundEvent> MUSIC_SIROK = registerMusic("sirok");
   public static final Holder<SoundEvent> SAND_COLUMN_ERUPTION = registerEntitySound("sand_column.eruption");
   public static final Holder<SoundEvent> SANDWORM_AMBIENT = registerEntitySound("sandworm.ambient");
   public static final Holder<SoundEvent> SANDWORM_BITE = registerEntitySound("sandworm.bite");
   public static final Holder<SoundEvent> SANDWORM_BODY_IMPACT = registerEntitySound("sandworm.impact");
   public static final Holder<SoundEvent> SANDWORM_CHARGE = registerEntitySound("sandworm.charge");
   public static final Holder<SoundEvent> SANDWORM_DEATH = registerEntitySound("sandworm.death");
   public static final Holder<SoundEvent> SANDWORM_DIVE = registerEntitySound("sandworm.dive");
   public static final Holder<SoundEvent> SANDWORM_DIVE_LONG = registerEntitySound("sandworm.dive_long");
   public static final Holder<SoundEvent> SANDWORM_DIVE_SHORT = registerEntitySound("sandworm.dive_short");
   public static final Holder<SoundEvent> SANDWORM_EMERGE = registerEntitySound("sandworm.emerge");
   public static final Holder<SoundEvent> SANDWORM_HURT = registerEntitySound("sandworm.hurt");
   public static final Holder<SoundEvent> SANDWORM_POISON_SPIT = registerEntitySound("sandworm.poison_spit");
   public static final Holder<SoundEvent> SANDWORM_SCREECH = registerEntitySound("sandworm.screech");
   public static final Holder<SoundEvent> SANDWORM_TAIL_IMPACT = registerEntitySound("sandworm.tail_impact");
   public static final Holder<SoundEvent> SANDWORM_GAUNTLET_POISON_BARRAGE_RELEASE = registerItemSound("sandworm_gauntlet.poison_barrage_release");
   public static final Holder<SoundEvent> MUSIC_NERAKYSS = registerMusic("nerakyss");
   public static final Holder<SoundEvent> KRAKEN_INTRO = registerEntitySound("kraken.intro");
   public static final Holder<SoundEvent> KRAKEN_DEATH = registerEntitySound("kraken.death");
   public static final Holder<SoundEvent> KRAKEN_DEATH_FINAL = registerEntitySound("kraken.death_final");
   public static final Holder<SoundEvent> KRAKEN_GRAB_AND_THROW = registerEntitySound("kraken.grab_and_throw");
   public static final Holder<SoundEvent> KRAKEN_GROWLING = registerEntitySound("kraken.growling");
   public static final Holder<SoundEvent> KRAKEN_IDLE = registerEntitySound("kraken.idle");
   public static final Holder<SoundEvent> KRAKEN_LIGHT_SLAM = registerEntitySound("kraken.light_slam");
   public static final Holder<SoundEvent> KRAKEN_STRONG_SLAM = registerEntitySound("kraken.strong_slam");
   public static final Holder<SoundEvent> KRAKEN_PHASE_TRANS = registerEntitySound("kraken.phase_transition");
   public static final Holder<SoundEvent> KRAKEN_WARNING = registerEntitySound("kraken.warning");
   public static final Holder<SoundEvent> KRAKEN_TENTACLE_DEATH = registerEntitySound("kraken.tentacle_death");
   public static final Holder<SoundEvent> KRAKEN_WATER_MOVEMENT = registerEntitySound("kraken.water_movement");
   public static final Holder<SoundEvent> CRATE_LAND = registerEntitySound("crate.land");
   public static final Holder<SoundEvent> CANNON_FIRE = registerEntitySound("cannon.fire");
   public static final Holder<SoundEvent> CANNON_DISMOUNT = registerEntitySound("cannon.get_out");
   public static final Holder<SoundEvent> CANNON_IMPACT = registerEntitySound("cannon.impact");
   public static final Holder<SoundEvent> CANNON_RELOAD = registerEntitySound("cannon.reload");
   public static final Holder<SoundEvent> CANNON_SEAT = registerEntitySound("cannon.seat");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_THROW_BACK = registerItemSound("undying_tentacle.throw_back");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_THROW_FORTH = registerItemSound("undying_tentacle.throw_forth");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_THROW_GRAB = registerItemSound("undying_tentacle.throw_grab");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_CHARGING = registerItemSound("undying_tentacle.charging");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_DISAPPEARING = registerItemSound("undying_tentacle.disappearing");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_SMASH_GROUND = registerItemSound("undying_tentacle.smash_ground");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_SPAWN_TENTACLES = registerItemSound("undying_tentacle.spawn_tentacles");
   public static final Holder<SoundEvent> UNDYING_TENTACLE_TENTACLE_STRIKE_THE_GROUND = registerItemSound("undying_tentacle.tentacle_strike_the_ground");
   public static final Holder<SoundEvent> SILENCE = registerEntitySound("silence");
   public static final Holder<SoundEvent> LARGE_SWORD_SWING = registerItemSound("large_sword_swing");
   public static final Holder<SoundEvent> KNIGHT_SWORD_SWING = registerItemSound("knight_sword_swing");
   public static final Holder<SoundEvent> DAGGER_STAB = registerItemSound("dagger_stab");
   public static final Holder<SoundEvent> WARRIOR_SWORD_SWING = registerItemSound("warrior_sword_swing");
   public static final Holder<SoundEvent> ROLL = registerEntitySound("roll");

   private static Holder<SoundEvent> registerMusic(String name) {
      String fullName = prefix("music", name);
      return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent(BossesRise.prefix(fullName)));
   }

   private static Holder<SoundEvent> registerItemSound(String name) {
      String fullName = prefix("item", name);
      return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent(BossesRise.prefix(fullName)));
   }

   private static Holder<SoundEvent> registerBlockSound(String name) {
      String fullName = prefix("block", name);
      return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent(BossesRise.prefix(fullName)));
   }

   private static Holder<SoundEvent> registerEntitySound(String name) {
      return registerEntitySound(name, false);
   }

   private static Holder<SoundEvent> registerEntitySound(String name, boolean cancelable) {
      String fullName = prefix("entity", name);
      if (cancelable) {
         CANCELABLE_SOUNDS.add(BossesRise.prefix(fullName));
      }

      return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent(BossesRise.prefix(fullName)));
   }

   private static String prefix(String soundType, String soundName) {
      return soundType + "." + soundName;
   }
}
