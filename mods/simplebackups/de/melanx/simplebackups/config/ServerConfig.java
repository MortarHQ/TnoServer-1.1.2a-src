package de.melanx.simplebackups.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public class ServerConfig {
   public static final ModConfigSpec CONFIG = ServerConfig.BUILDER.build();
   private static final Builder BUILDER = new Builder();
   private static BooleanValue commandsCheatsDisabled;
   private static BooleanValue messagesForEveryone;

   public static void init(Builder builder) {
      commandsCheatsDisabled = builder.comment(
            "Should commands work without cheats enabled? Mainly recommended for single player, otherwise all users on servers can trigger commands."
         )
         .define("commandsCheatsDisabled", false);
      messagesForEveryone = builder.comment(
            "Should all users receive the backup message? Disable to only have users with permission level 2 and higher receive them."
         )
         .define("messagesForEveryone", true);
   }

   public static boolean commandsCheatsDisabled() {
      return (Boolean)commandsCheatsDisabled.get();
   }

   public static boolean messagesForEveryone() {
      return (Boolean)messagesForEveryone.get();
   }

   static {
      init(BUILDER);
   }
}
