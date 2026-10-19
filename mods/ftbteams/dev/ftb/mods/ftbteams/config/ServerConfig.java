package dev.ftb.mods.ftbteams.config;

import dev.ftb.mods.ftblibrary.snbt.config.IntValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import java.util.OptionalInt;

public interface ServerConfig {
   String KEY = "ftbteams-server";
   SNBTConfig CONFIG = (SNBTConfig)SNBTConfig.create("ftbteams-server")
      .comment(
         new String[]{
            "Server-specific configuration for FTB Teams",
            "Modpack defaults should be defined in <instance>/config/ftbteams-server.snbt",
            "  (may be overwritten on modpack update)",
            "Server admins may locally override this by copying into <instance>/world/serverconfig/ftbteams-server.snbt",
            "  (will NOT be overwritten on modpack update)"
         }
      );
   IntValue LIMITED_LIVES = (IntValue)CONFIG.addInt("limited_lives", 0, 0, Integer.MAX_VALUE)
      .comment(
         new String[]{
            "If >0, party teams have this many limited lives:",
            "When members die, a party life is lost.",
            "If a member dies when the party has no lives remaining, they are kicked from the party.",
            "If the party owner dies, a random member is promoted to owner (officers have priority).",
            "Parties with no lives remaining cannot invite new members."
         }
      );

   static OptionalInt limitedLives() {
      return LIMITED_LIVES.get() > 0 ? OptionalInt.of((Integer)LIMITED_LIVES.get()) : OptionalInt.empty();
   }
}
