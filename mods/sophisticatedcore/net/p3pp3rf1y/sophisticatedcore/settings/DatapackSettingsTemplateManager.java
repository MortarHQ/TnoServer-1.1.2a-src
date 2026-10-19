package net.p3pp3rf1y.sophisticatedcore.settings;

import com.google.common.collect.Maps;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import org.apache.commons.io.IOUtils;

public class DatapackSettingsTemplateManager {
   private static final Map<String, Map<String, CompoundTag>> TEMPLATES = Maps.newHashMap();

   private DatapackSettingsTemplateManager() {
   }

   public static void putTemplate(String datapackName, String templateName, CompoundTag tag) {
      templateName = templateName.replace('_', ' ');
      templateName = capitalizeFirstLetterOfEachWord(templateName);
      TEMPLATES.computeIfAbsent(datapackName, n -> Maps.newTreeMap()).put(templateName, tag);
   }

   private static String capitalizeFirstLetterOfEachWord(String input) {
      String[] words = input.split("\\s+");
      StringBuilder builder = new StringBuilder();

      for (String word : words) {
         if (!word.isEmpty()) {
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(capitalizedWord).append(" ");
         }
      }

      return builder.toString().trim();
   }

   public static Map<String, Map<String, CompoundTag>> getTemplates() {
      return TEMPLATES;
   }

   public static Optional<CompoundTag> getTemplateNbt(String datapackName, String templateName) {
      Map<String, CompoundTag> datapackTemplates = TEMPLATES.get(datapackName);
      return datapackTemplates == null ? Optional.empty() : Optional.ofNullable(datapackTemplates.get(templateName));
   }

   public static class Loader extends SimplePreparableReloadListener<Map<ResourceLocation, CompoundTag>> {
      public static final DatapackSettingsTemplateManager.Loader INSTANCE = new DatapackSettingsTemplateManager.Loader();
      private static final String DIRECTORY = "sophisticated_settingstemplates";
      private static final String SUFFIX = ".snbt";
      private static final int PATH_SUFFIX_LENGTH = ".snbt".length();

      private Loader() {
      }

      protected Map<ResourceLocation, CompoundTag> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
         Map<ResourceLocation, CompoundTag> map = Maps.newHashMap();
         int i = "sophisticated_settingstemplates".length() + 1;
         resourceManager.listResources("sophisticated_settingstemplates", fileName -> fileName.getPath().endsWith(".snbt"))
            .forEach(
               (resourcelocation, resource) -> {
                  String s = resourcelocation.getPath();
                  ResourceLocation resourceLocationWithoutSuffix = ResourceLocation.fromNamespaceAndPath(
                     resourcelocation.getNamespace(), s.substring(i, s.length() - PATH_SUFFIX_LENGTH)
                  );

                  try (
                     InputStream inputstream = resource.open();
                     Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                  ) {
                     String fileContents = IOUtils.toString(reader);
                     CompoundTag tag = TagParser.parseTag(fileContents);
                     if (map.put(resourceLocationWithoutSuffix, tag) != null) {
                        throw new IllegalStateException("Duplicate data file ignored with ID " + resourceLocationWithoutSuffix);
                     }
                  } catch (IllegalArgumentException | IOException | CommandSyntaxException ex) {
                     SophisticatedCore.LOGGER.error("Couldn't parse data file {} from {}", resourceLocationWithoutSuffix, resourcelocation, ex);
                  }
               }
            );
         return map;
      }

      protected void apply(Map<ResourceLocation, CompoundTag> templates, ResourceManager resourceManager, ProfilerFiller profiler) {
         templates.forEach((resourceLocation, tag) -> {
            String datapackName = resourceLocation.getNamespace();
            String templateName = resourceLocation.getPath().substring(resourceLocation.getPath().lastIndexOf(47) + 1);
            DatapackSettingsTemplateManager.putTemplate(datapackName, templateName, tag);
         });
      }
   }
}
