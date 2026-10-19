package org.crypticdev.stextras.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;

public class STGeneralConfig extends ManasConfig {
   @Comment("If true, dedicated servers require clients to report the same STExtras version before joining.")
   public boolean versionControl = true;
   @Comment("How long the server waits for a client-initiated version reply before disconnecting them, in milliseconds.")
   public long versionCheckTimeOut = 30000L;

   public String getFileName() {
      return "stextras/general_config";
   }
}
