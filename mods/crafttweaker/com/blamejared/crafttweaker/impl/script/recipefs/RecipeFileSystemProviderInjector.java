package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.HandleUtil;
import java.lang.invoke.VarHandle;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.Logger;

public final class RecipeFileSystemProviderInjector {
   private RecipeFileSystemProviderInjector() {
   }

   public static void inject(Logger logger) {
      if (!exists()) {
         try {
            VarHandle lock = HandleUtil.linkField(FileSystemProvider.class, HandleUtil.AccessType.STATIC, "lock", Object.class);
            VarHandle installedProviders = HandleUtil.linkField(FileSystemProvider.class, HandleUtil.AccessType.STATIC, "installedProviders", List.class);

            while (true) {
               synchronized ((Object)lock.get()) {
                  List<FileSystemProvider> providers = GenericUtil.uncheck((List)installedProviders.getVolatile());
                  List<FileSystemProvider> newProviders = new ArrayList<>(providers);
                  newProviders.add(new RecipeFileSystemProvider());
                  if (installedProviders.compareAndSet((List)providers, (List)Collections.unmodifiableList(newProviders))) {
                     break;
                  }
               }
            }

            if (!exists()) {
               throw new RuntimeException("Unable to inject file system provider: discovered " + FileSystemProvider.installedProviders());
            }

            logger.info("Successfully injected RecipeFS file system");
         } catch (Throwable e) {
            throw new RuntimeException("An error occurred when attempting to inject RecipeFS file system provider", e);
         }
      }
   }

   private static boolean exists() {
      return FileSystemProvider.installedProviders().stream().anyMatch(it -> "crt-recipe-fs".equals(it.getScheme()));
   }
}
