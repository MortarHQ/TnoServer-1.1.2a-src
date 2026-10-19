package com.finderfeed.raids_enhanced.init;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.raids_enhanced.RaidsEnhanced;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class REAnimations {
   public static DeferredRegister<Animation> ANIMATIONS = DeferredRegister.create(FDRegistries.ANIMATIONS, "raidsenhanced");
   public static DeferredHolder<Animation, Animation> RAID_AIRSHIP_FLY = ANIMATIONS.register(
      "raid_airship_fly", () -> new Animation(RaidsEnhanced.location("raid_airship"))
   );
   public static DeferredHolder<Animation, Animation> RAID_AIRSHIP_IDLE = ANIMATIONS.register(
      "raid_airship_idle", () -> new Animation(RaidsEnhanced.location("raid_airship"))
   );
   public static DeferredHolder<Animation, Animation> RAID_AIRSHIP_ILLAGER_OBSERVE = ANIMATIONS.register(
      "raid_airship_illager_observe", () -> new Animation(RaidsEnhanced.location("raid_airship"))
   );
   public static DeferredHolder<Animation, Animation> RAID_AIRSHIP_THROW_BOMB = ANIMATIONS.register(
      "raid_airship_throw_bomb", () -> new Animation(RaidsEnhanced.location("raid_airship"))
   );
   public static DeferredHolder<Animation, Animation> RAID_AIRSHIP_DEATH = ANIMATIONS.register(
      "raid_airship_death", () -> new Animation(RaidsEnhanced.location("raid_airship"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_STRIKE_1 = ANIMATIONS.register(
      "illager_golem_strike_1", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_STRIKE_2 = ANIMATIONS.register(
      "illager_golem_strike_2", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_IDLE = ANIMATIONS.register(
      "illager_golem_idle", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_HEAVY_STRIKE = ANIMATIONS.register(
      "illager_golem_heavy_strike", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_WHIRLWIND = ANIMATIONS.register(
      "illager_golem_whirlwind", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_BOMBS = ANIMATIONS.register(
      "illager_golem_bombs", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_WALK = ANIMATIONS.register(
      "illager_golem_walk", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_WALK_NO_HANDS = ANIMATIONS.register(
      "illager_golem_walk_no_hands", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ILLAGER_GOLEM_DEATH = ANIMATIONS.register(
      "illager_golem_death", () -> new Animation(RaidsEnhanced.location("golem_of_last_resort"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_WALK_NO_HANDS = ANIMATIONS.register(
      "electromancer_walk_no_hands", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_WALK = ANIMATIONS.register(
      "electromancer_walk", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_ATTACK_1 = ANIMATIONS.register(
      "electromancer_attack_1", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_ATTACK_2 = ANIMATIONS.register(
      "electromancer_attack_2", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_IDLE = ANIMATIONS.register(
      "electromancer_idle", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_RAY_CHARGE = ANIMATIONS.register(
      "electromancer_ray_charge", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_RAY_CAST = ANIMATIONS.register(
      "electromancer_ray_cast", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_RAY_CAST_STOP = ANIMATIONS.register(
      "electromancer_ray_cast_stop", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_CHARGE_STICK = ANIMATIONS.register(
      "electromancer_charge_stick", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> ELECTROMANCER_LIGHTNINGS_CAST = ANIMATIONS.register(
      "electromancer_lightnings_cast", () -> new Animation(RaidsEnhanced.location("electromancer"))
   );
   public static DeferredHolder<Animation, Animation> PLAYER_BLIMP_PERPELLER = ANIMATIONS.register(
      "player_blimp_perpeller", () -> new Animation(RaidsEnhanced.location("player_blimp"))
   );
   public static DeferredHolder<Animation, Animation> PLAYER_BLIMP_TURN_LEFT = ANIMATIONS.register(
      "player_blimp_turn_left", () -> new Animation(RaidsEnhanced.location("player_blimp"))
   );
   public static DeferredHolder<Animation, Animation> PLAYER_BLIMP_TURN_RIGHT = ANIMATIONS.register(
      "player_blimp_turn_right", () -> new Animation(RaidsEnhanced.location("player_blimp"))
   );
   public static DeferredHolder<Animation, Animation> PLAYER_BLIMP_DANGLE_LIGHTS = ANIMATIONS.register(
      "player_blimp_dangle_lights", () -> new Animation(RaidsEnhanced.location("player_blimp"))
   );
   public static DeferredHolder<Animation, Animation> PLAYER_BLIMP_IDLE = ANIMATIONS.register(
      "player_blimp_air_idle", () -> new Animation(RaidsEnhanced.location("player_blimp"))
   );
   public static DeferredHolder<Animation, Animation> RAIDER_DRILL_BURROW = ANIMATIONS.register(
      "raider_drill_burrow", () -> new Animation(RaidsEnhanced.location("raider_drill"))
   );
   public static DeferredHolder<Animation, Animation> RAIDER_DRILL_UNBURROW = ANIMATIONS.register(
      "raider_drill_unburrow", () -> new Animation(RaidsEnhanced.location("raider_drill"))
   );
   public static DeferredHolder<Animation, Animation> RAIDER_DRILL_IDLE = ANIMATIONS.register(
      "raider_drill_idle", () -> new Animation(RaidsEnhanced.location("raider_drill"))
   );
}
