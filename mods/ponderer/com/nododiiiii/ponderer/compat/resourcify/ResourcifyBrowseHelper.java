package com.nododiiiii.ponderer.compat.resourcify;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;

final class ResourcifyBrowseHelper {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String PROJECT_TYPE_CLASS = "dev.dediamondpro.resourcify.services.ProjectType";
   private static final String SERVICE_REGISTRY_CLASS = "dev.dediamondpro.resourcify.services.ServiceRegistry";
   private static final String BROWSE_SCREEN_CLASS = "dev.dediamondpro.resourcify.gui.browsepage.BrowseScreen";

   private ResourcifyBrowseHelper() {
   }

   static void open(String initialQuery) {
      File resourcepacksDir = Minecraft.getInstance().gameDirectory.toPath().resolve("resourcepacks").toFile();

      Object screen;
      try {
         Class<?> projectTypeClass = Class.forName("dev.dediamondpro.resourcify.services.ProjectType");
         Object resourcePackType = projectTypeClass.getField("RESOURCE_PACK").get(null);
         Class<?> serviceRegistryClass = Class.forName("dev.dediamondpro.resourcify.services.ServiceRegistry");
         Object serviceRegistry = serviceRegistryClass.getField("INSTANCE").get(null);
         Method getDefaultService = serviceRegistryClass.getMethod("getDefaultService", projectTypeClass);
         Object defaultService = getDefaultService.invoke(serviceRegistry, resourcePackType);
         Class<?> browseScreenClass = Class.forName("dev.dediamondpro.resourcify.gui.browsepage.BrowseScreen");
         Object screenInstance = browseScreenClass.getConstructor(
               projectTypeClass,
               File.class,
               defaultService.getClass().getInterfaces().length > 0 ? defaultService.getClass().getInterfaces()[0] : defaultService.getClass()
            )
            .newInstance(resourcePackType, resourcepacksDir, defaultService);
         screen = screenInstance;
      } catch (Exception constructorError) {
         try {
            Class<?> browseScreenClass = Class.forName("dev.dediamondpro.resourcify.gui.browsepage.BrowseScreen");
            Class<?> projectTypeClass = Class.forName("dev.dediamondpro.resourcify.services.ProjectType");
            Object resourcePackType = projectTypeClass.getField("RESOURCE_PACK").get(null);
            Class<?> serviceRegistryClass = Class.forName("dev.dediamondpro.resourcify.services.ServiceRegistry");
            Object serviceRegistry = serviceRegistryClass.getField("INSTANCE").get(null);
            Method getDefaultService = serviceRegistryClass.getMethod("getDefaultService", projectTypeClass);
            Object defaultService = getDefaultService.invoke(serviceRegistry, resourcePackType);
            Object screenInstance = null;

            for (Constructor<?> constructor : browseScreenClass.getConstructors()) {
               if (constructor.getParameterCount() == 3) {
                  screenInstance = constructor.newInstance(resourcePackType, resourcepacksDir, defaultService);
                  break;
               }
            }

            if (screenInstance == null) {
               throw new IllegalStateException("No compatible BrowseScreen constructor found");
            }

            screen = screenInstance;
         } catch (Exception fallbackError) {
            throw new RuntimeException("Failed to construct Resourcify BrowseScreen", fallbackError);
         }

         LOGGER.debug("BrowseScreen constructor signature changed, fallback reflection path used", constructorError);
      }

      if (initialQuery != null && !initialQuery.isEmpty()) {
         try {
            Field searchBoxField = screen.getClass().getDeclaredField("searchBox");
            searchBoxField.setAccessible(true);
            Object searchBox = searchBoxField.get(screen);
            if (searchBox != null) {
               Method setText = searchBox.getClass().getMethod("setText", String.class);
               setText.invoke(searchBox, initialQuery);
            }
         } catch (Exception e) {
            LOGGER.warn("Failed to set initial search query in Resourcify BrowseScreen", e);
         }
      }

      Minecraft.getInstance().setScreen((Screen)screen);
   }
}
