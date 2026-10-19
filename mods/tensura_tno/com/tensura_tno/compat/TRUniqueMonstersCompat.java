package com.tensura_tno.compat;

import com.mojang.logging.LogUtils;
import java.lang.reflect.Method;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.slf4j.Logger;

public class TRUniqueMonstersCompat {
   private static final Logger LOGGER = LogUtils.getLogger();
   public static volatile boolean allowInit = false;

   @Internal
   public static void triggerDeferredInit() {
      if (ModList.get().isLoaded("tr_unique_monsters")) {
         try {
            allowInit = true;
            Class<?> extraSkillsClass = Class.forName("net.crypticmc.tr_unique_monsters.registry.skill.ExtraSkills");
            Method initMethod = extraSkillsClass.getMethod("init");
            initMethod.invoke(null);
         } catch (ClassNotFoundException var6) {
         } catch (Exception e) {
            LOGGER.error(
               "[tensura_tno] Failed to defer ExtraSkills.init() — tr_unique_monsters features may be unavailable. This is a non-fatal compat error.", e
            );
         } finally {
            allowInit = false;
         }
      }
   }
}
