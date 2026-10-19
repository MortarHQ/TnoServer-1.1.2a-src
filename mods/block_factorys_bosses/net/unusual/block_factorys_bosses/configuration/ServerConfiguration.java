package net.unusual.block_factorys_bosses.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class ServerConfiguration {
   public static final Builder BUILDER = new Builder();
   public static final ModConfigSpec SPEC = BUILDER.build();
   public static final ConfigValue<Double> SANDWORM_HEALTH = BUILDER.comment("Max Health : 1024").define("Sandworm Health", 200.0);
   public static final ConfigValue<Double> YETI_HEALTH = BUILDER.define("Yeti Health", 250.0);
   public static final ConfigValue<Double> DRAGON_HEALTH = BUILDER.define("Dragon Health", 250.0);
   public static final ConfigValue<Double> KNIGHT_HEALTH = BUILDER.define("Knight Health", 280.0);
   public static final ConfigValue<Double> KRAKEN_HEALTH = BUILDER.define("Kraken Health", 500.0);
   public static final ConfigValue<Double> SANDWORM_ARMOR = BUILDER.comment("Max Armor : 30").define("Sandworm Armor", 20.0);
   public static final ConfigValue<Double> YETI_ARMOR = BUILDER.define("Yeti Armor", 5.0);
   public static final ConfigValue<Double> DRAGON_ARMOR = BUILDER.define("Dragon Armor", 25.0);
   public static final ConfigValue<Double> KNIGHT_ARMOR = BUILDER.define("Knight Armor", 10.0);
   public static final ConfigValue<Double> KRAKEN_ARMOR = BUILDER.define("Kraken Armor", 20.0);
   public static final ConfigValue<Double> SANDWORM_ATK = BUILDER.comment("Not all damages sources depends on this variable !").define("Sandworm Damage", 10.0);
   public static final ConfigValue<Double> YETI_ATK = BUILDER.define("Yeti Damage", 18.0);
   public static final ConfigValue<Double> DRAGON_ATK = BUILDER.define("Dragon Damage", 15.0);
   public static final ConfigValue<Double> KNIGHT_ATK = BUILDER.define("Knight Damage", 13.0);
   public static final ConfigValue<Double> KRAKEN_ATK = BUILDER.define("Kraken Damage", 10.0);
   public static final ConfigValue<Boolean> CAN_USE_ROLL = BUILDER.define("Can players use the Roll ability", true);
   public static final ConfigValue<Integer> DEFAULT_ROLL_COUNT = BUILDER.define("How many roll charges does the player have by default?", 2);
   public static final ConfigValue<Integer> ROLL_COOLDOWN = BUILDER.define("How many ticks does it take for a roll to cooldown? (20 ticks is 1 second)", 40);
   public static final ConfigValue<Integer> ROLL_COOLDOWN_MORE = BUILDER.define(
      "How many additional ticks should each roll above 1 take to cooldown? (20 ticks is 1 second)", 20
   );

   static {
      BUILDER.push("Boss Health");
      BUILDER.pop();
      BUILDER.push("Boss Armor");
      BUILDER.pop();
      BUILDER.push("Base Boss Damages");
      BUILDER.pop();
      BUILDER.push("Gameplay Changes");
      BUILDER.pop();
   }
}
