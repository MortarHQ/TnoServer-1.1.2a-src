package com.tensura_tno.compat;

import com.mojang.logging.LogUtils;
import java.lang.reflect.Method;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.slf4j.Logger;

public class TrAddonCompat {
   private static final Logger LOGGER = LogUtils.getLogger();
   public static volatile boolean allowInit = false;

   @Internal
   public static void triggerDeferredInit() {
      if (ModList.get().isLoaded("traddon")) {
         try {
            allowInit = true;
            Class<?> trAddonClass = Class.forName("com.github.dominickwd04.traddon.TrAddon");
            Method initMethod = trAddonClass.getMethod("init");
            initMethod.invoke(null);
         } catch (ClassNotFoundException var6) {
         } catch (Exception e) {
            LOGGER.error("[tensura_tno] Failed to defer TrAddon.init() — traddon features may be unavailable. This is a non-fatal compat error.", e);
         } finally {
            allowInit = false;
         }
      }
   }
}
