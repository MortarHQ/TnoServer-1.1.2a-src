package it.hurts.octostudios.octolib.module.config.util;

import com.mojang.datafixers.util.Pair;
import it.hurts.octostudios.octolib.OctoLib;
import it.hurts.octostudios.octolib.module.config.ConfigManager;
import it.hurts.octostudios.octolib.module.config.annotation.registration.AnnotationConfigFactory;
import it.hurts.octostudios.octolib.module.config.annotation.registration.ConfigNameGetter;
import it.hurts.octostudios.octolib.module.config.impl.OctoConfig;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.apache.logging.log4j.util.Cast;

public final class ConfigUtils {
   public static void registerFieldConfig(Field field, String dir) {
      field.setAccessible(true);

      try {
         Object value = field.get(null);
         if (value == null) {
            OctoLib.LOGGER.warn("Failed to load config in " + dir + " directory: config is null.");
         }

         for (Annotation a : field.getAnnotations()) {
            Pair<AnnotationConfigFactory<?>, ConfigNameGetter<?>> fabric = ConfigManager.getConfigFactory(a.annotationType());
            if (fabric == null) {
               OctoLib.LOGGER.warn("Unsupported annotation {} for config initialization.", a);
            } else {
               OctoConfig octoConfig = ((AnnotationConfigFactory)fabric.getFirst()).create((Annotation)Cast.cast(a), value);
               String name = ((ConfigNameGetter)fabric.getSecond()).getName((Annotation)Cast.cast(a), value);
               String location = dir + "/" + name;
               ConfigManager.registerConfig(location, octoConfig);
            }
         }
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   private ConfigUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
