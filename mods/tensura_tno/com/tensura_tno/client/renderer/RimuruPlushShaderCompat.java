package com.tensura_tno.client.renderer;

import com.mojang.logging.LogUtils;
import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

final class RimuruPlushShaderCompat {
   private static final Logger LOGGER = LogUtils.getLogger();

   private RimuruPlushShaderCompat() {
   }

   static boolean useVanillaRenderType() {
      return RimuruPlushShaderCompat.CheckHolder.CHECK.getAsBoolean();
   }

   private static BooleanSupplier findPackCheck() {
      if (!ModList.get().isLoaded("iris") && !ModList.get().isLoaded("oculus")) {
         return () -> false;
      }

      for (String name : new String[]{"net.irisshaders.iris.api.v0.IrisApi", "net.coderbot.iris.api.v0.IrisApi"}) {
         try {
            return createPackCheck(Class.forName(name));
         } catch (ReflectiveOperationException | RuntimeException | LinkageError var5) {
         }
      }

      LOGGER.warn("Iris/Oculus API unavailable; using vanilla plush rendering for compatibility");
      return () -> true;
   }

   static BooleanSupplier createPackCheck(Class<?> apiClass) throws ReflectiveOperationException {
      final Object api = apiClass.getMethod("getInstance").invoke(null);
      final Method isInUse = apiClass.getMethod("isShaderPackInUse");
      return new BooleanSupplier() {
         private boolean unavailable;

         @Override
         public boolean getAsBoolean() {
            if (this.unavailable) {
               return true;
            }

            try {
               return Boolean.TRUE.equals(isInUse.invoke(api));
            } catch (ReflectiveOperationException | RuntimeException | LinkageError error) {
               this.unavailable = true;
               RimuruPlushShaderCompat.LOGGER.warn("Cannot query Iris/Oculus shader state; using vanilla plush rendering", error);
               return true;
            }
         }
      };
   }

   private static final class CheckHolder {
      private static final BooleanSupplier CHECK = RimuruPlushShaderCompat.findPackCheck();
   }
}
