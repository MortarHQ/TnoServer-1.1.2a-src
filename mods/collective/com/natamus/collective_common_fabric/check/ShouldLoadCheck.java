package com.natamus.collective_common_fabric.check;

import com.natamus.collective_common_fabric.services.Services;

public class ShouldLoadCheck {
   public static boolean shouldLoad(String modId) {
      return Services.MODLOADER.isJarJard(modId) ? Services.MODLOADER.isBundleModEnabled(modId) : true;
   }
}
