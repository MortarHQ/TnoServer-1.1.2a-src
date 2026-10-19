package com.blackgear.platform.neoforge;

import com.blackgear.platform.Platform;
import net.neoforged.fml.common.Mod;

@Mod("platform")
public final class PlatformForge {
   public PlatformForge() {
      Platform.bootstrap();
   }
}
