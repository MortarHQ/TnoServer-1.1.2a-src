package com.sennecools.tablist.platform;

import java.nio.file.Path;
import net.minecraft.server.level.ServerPlayer;

public interface PlatformHelper {
   boolean isModLoaded(String var1);

   Path getConfigDir();

   void refreshDisplayName(ServerPlayer var1);
}
