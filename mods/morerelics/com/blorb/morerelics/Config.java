package com.blorb.morerelics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;

@EventBusSubscriber(modid = "morerelics")
public class Config {
   private static Map<String, String> cachedOpposites;
   public static final ModConfigSpec COMMON_SPEC;
   public static final ConfigValue<List<? extends String>> VERTEBRAX_EFFECT_EXCLUDE;
   public static final ConfigValue<List<? extends String>> VERTEBRAX_EFFECT_OPPOSITES;
   public static final ModConfigSpec CLIENT_SPEC;
   public static final EnumValue<Config.SilkGUICorner> SILK_BAR_CORNER;
   public static final BooleanValue CYBERPSYCHOSIS_DISGUISE_EFFECT;

   @SubscribeEvent
   static void onLoad(ModConfigEvent event) {
   }

   public static Map<String, String> getOpposites() {
      if (cachedOpposites == null) {
         cachedOpposites = new HashMap<>();

         for (String entry : (List)VERTEBRAX_EFFECT_OPPOSITES.get()) {
            String[] parts = entry.split("\\|");
            if (parts.length == 2) {
               cachedOpposites.put(parts[0].trim(), parts[1].trim());
            }
         }
      }

      return cachedOpposites;
   }

   static {
      Builder COMMON_BUILDER = new Builder();
      COMMON_BUILDER.push("VertebraX");
      VERTEBRAX_EFFECT_EXCLUDE = COMMON_BUILDER.comment("List of effects which can't be filtered/reduced by vertebrax.")
         .defineList("excludedEffects", () -> List.of("minecraft:levitation"), () -> "", s -> s instanceof String);
      VERTEBRAX_EFFECT_OPPOSITES = COMMON_BUILDER.comment(
            "Pairs of effects which will grant the opposite version upon filtration.\nFormat: 'modid:original_effect|modid:opposite_effect' Example: minecraft:weakness|minecraft:strength\nYou can include modded effects here but first effect should be harmful and second effect should be beneficial or it won't work."
         )
         .defineList(
            "oppositeEffectMappings",
            List.of(
               "minecraft:weakness|minecraft:strength",
               "minecraft:slowness|minecraft:speed",
               "minecraft:unluck|minecraft:luck",
               "minecraft:blindness|minecraft:night_vision",
               "minecraft:mining_fatigue|minecraft:haste",
               "morerelics:vulnerability|minecraft:resistance"
            ),
            () -> "",
            obj -> obj instanceof String && ((String)obj).contains("|")
         );
      COMMON_BUILDER.pop();
      COMMON_SPEC = COMMON_BUILDER.build();
      Builder CLIENT_BUILDER = new Builder();
      CLIENT_BUILDER.push("Weaver's Spool");
      SILK_BAR_CORNER = CLIENT_BUILDER.comment("The corner which the silk bar is rendered at.").defineEnum("silkBarCorner", Config.SilkGUICorner.BOTTOM_RIGHT);
      CLIENT_BUILDER.pop();
      CLIENT_BUILDER.push("Cyberpsychosis");
      CYBERPSYCHOSIS_DISGUISE_EFFECT = CLIENT_BUILDER.comment(
            "Disguises some passive and neutral mobs as hostile entities while the Cyberpsychosis effect is active."
         )
         .define("cyberpsychosisDisguiseEffect", true);
      CLIENT_BUILDER.pop();
      CLIENT_SPEC = CLIENT_BUILDER.build();
   }

   public enum SilkGUICorner {
      TOP_LEFT,
      TOP_RIGHT,
      BOTTOM_LEFT,
      BOTTOM_RIGHT;
   }
}
