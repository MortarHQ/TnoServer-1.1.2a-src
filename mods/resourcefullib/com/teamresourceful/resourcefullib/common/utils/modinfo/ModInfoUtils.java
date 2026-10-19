package com.teamresourceful.resourcefullib.common.utils.modinfo;

import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import com.teamresourceful.resourcefullib.common.utils.modinfo.neoforge.ModInfoUtilsImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class ModInfoUtils {
   private ModInfoUtils() throws UtilityClassException {
      throw new UtilityClassException();
   }

   @ExpectPlatform
   @Contract(pure = true)
   @Transformed
   public static boolean isModLoaded(String id) {
      return ModInfoUtilsImpl.isModLoaded(id);
   }

   @ExpectPlatform
   @Contract(pure = true)
   @Transformed
   public static boolean isMixinModLoaded(String id) {
      return ModInfoUtilsImpl.isMixinModLoaded(id);
   }

   @Nullable
   @ExpectPlatform
   @Transformed
   public static ModInfo getModInfo(String id) {
      return ModInfoUtilsImpl.getModInfo(id);
   }

   @ExpectPlatform
   @Transformed
   public static int getLoadedMods() {
      return ModInfoUtilsImpl.getLoadedMods();
   }
}
