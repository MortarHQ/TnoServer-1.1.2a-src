package com.nododiiiii.ponderer.platform.services;

import com.nododiiiii.ponderer.ponder.DslScene;
import java.nio.file.Path;
import java.util.function.Supplier;

public interface PlatformHelper {
   String getPlatformName();

   boolean isClient();

   boolean isDevelopmentEnvironment();

   boolean isModLoaded(String var1);

   Path getGameDir();

   Path getConfigDir();

   void executeOnClient(Supplier<Runnable> var1);

   default void showInterfaceStep(DslScene.DslStep step) {
   }

   default void clickInterfaceStep(DslScene.DslStep step) {
   }

   default void closeInterfaceStep(String reason) {
   }

   default boolean supportsEmbeddedInterfacePreview() {
      return false;
   }
}
