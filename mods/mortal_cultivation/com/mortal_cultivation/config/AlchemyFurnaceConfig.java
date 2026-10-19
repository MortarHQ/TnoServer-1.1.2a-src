package com.mortal_cultivation.config;

import java.util.List;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class AlchemyFurnaceConfig {
   public static final Builder BUILDER = new Builder();
   public static final ModConfigSpec SPEC = BUILDER.build();
   public static final ConfigValue<List<? extends String>> FUEL_SPEED_MODIFIERS = BUILDER.comment("格式: 'modid:item_id,speed_multiplier'")
      .defineList("fuel_speed_modifiers", getDefaultFuelModifiers(), obj -> {
         if (obj instanceof String str) {
            String[] parts = str.split(",");
            if (parts.length == 2) {
               try {
                  Double.parseDouble(parts[1].trim());
                  return true;
               } catch (NumberFormatException e) {
                  return false;
               }
            }
         }

         return false;
      });

   private static List<String> getDefaultFuelModifiers() {
      return List.of(
         "mortal_cultivation:mud_essence_charcoal,1.2",
         "mortal_cultivation:yellow_essence_charcoal,1.5",
         "mortal_cultivation:black_essence_charcoal,1.8",
         "mortal_cultivation:void_essence_charcoal,2.5",
         "mortal_cultivation:prime_essence_charcoal,5.0",
         "mortal_cultivation:crystal_of_combustion,10.0"
      );
   }

   public static void register(ModContainer modContainer) {
      modContainer.registerConfig(Type.COMMON, SPEC, "mortal_cultivation-alchemy_furnace.toml");
   }

   static {
      BUILDER.push("alchemy_furnace");
      BUILDER.comment("炼丹炉配置 - Alchemy Furnace Configuration");
      BUILDER.push("fuel_settings");
      BUILDER.comment("燃料设置 - Fuel Settings");
      BUILDER.pop();
      BUILDER.pop();
   }
}
