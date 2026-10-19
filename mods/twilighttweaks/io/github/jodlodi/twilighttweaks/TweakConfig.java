package io.github.jodlodi.twilighttweaks;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

@EventBusSubscriber(modid = "twilighttweaks", bus = Bus.MOD)
public class TweakConfig {
   private static final Builder BUILDER = new Builder();
   private static final BooleanValue DISABLE_FLAG = BUILDER.comment(
         "The very top of the final castle now generates a jigsaw structure from \"final_castle/temp/gazebo\". (Twilight Forest does this, not Twilight Tweaks).\nFor sake of your convenience this mod disables that feature via this config option.\nThis is done due to the fact that the default jigsaw structure is a giant unbreakable forcefield cube, which makes the final spawner kinda hard to get to.\nIf you wish to re-enable this feature, disable this config option!!! Simple as that.\nHowever, if this is disabled, I also wont place the final boss spawner block manually, assuming that you'll do it yourself in your custom jigsaw structure. Don't be lazy."
      )
      .define("The final castle gazebo is empty: ", true);
   private static final ConfigValue<String> COMMAND_CUSTOM = BUILDER.comment(
         "Here you can set what command gets executed when the final boss spawner is activated.\nType in the command you want to happen the exact same way as you would in game.\nFor example, typing \"summon minecraft:skeleton ~ ~ ~\" will summon a normal skeleton and typing \"summon zombie ~ ~ ~ {Invulnerable:1,HandItems:[{Count:1,id:wooden_sword},{}]}\" will summon an invincible zombie that is holding a wooden sword.\nBy default, the spawner runs \"function twilighttweaks:final_boss_example\", which is a mcfunction made for the mod that runs several commands in a row. Mcfunctions are a vanilla feature and you can google how to make one of your own.\nAny command can be ran once, after which the spawner will break itself."
      )
      .define("The command that the spawner should run: ", "function twilighttweaks:final_boss_example");
   private static final BooleanValue REMNANT_FLAG = BUILDER.comment(
         "With this mod installed, boss spawners now leave remnants behind (usually, after the actual boss has been killed).\nAdditionally, killed hostile mobs in the Twilight Forest dimension have a chance (0.5%) to drop Time powder.\nUsing Time powder on boss spawner remnants brings the spawner back, allowing the boss to be fought again."
      )
      .define("The boss spawner feature is enabled: ", true);
   public static boolean disableFlag;
   public static String commandCustom;
   public static boolean remnantFlag;
   static final ModConfigSpec SPEC = BUILDER.build();

   @SubscribeEvent
   static void onLoad(ModConfigEvent event) {
      disableFlag = (Boolean)DISABLE_FLAG.get();
      commandCustom = (String)COMMAND_CUSTOM.get();
      remnantFlag = (Boolean)REMNANT_FLAG.get();
   }
}
