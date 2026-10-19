package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

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
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import org.apache.commons.io.IOUtils;

public class DatapackBackpackTemplateManager {
   private static final Map<ResourceLocation, CompoundTag> TEMPLATES = Maps.newTreeMap();

   private DatapackBackpackTemplateManager() {
   }

   private static void putBackpackTemplate(ResourceLocation templateName, CompoundTag tag) {
      TEMPLATES.put(templateName, tag);
   }

   public static Map<ResourceLocation, CompoundTag> getBackpackTemplates() {
      return TEMPLATES;
   }

   public static Optional<CompoundTag> getBackpackTemplate(ResourceLocation templateName) {
      return Optional.ofNullable(TEMPLATES.get(templateName));
   }

   public static class Loader extends SimplePreparableReloadListener<Map<ResourceLocation, CompoundTag>> {
      public static final DatapackBackpackTemplateManager.Loader INSTANCE = new DatapackBackpackTemplateManager.Loader();
      private static final String DIRECTORY = "sophisticatedbackpacks_templates";
      private static final String SUFFIX = ".snbt";
      private static final int PATH_SUFFIX_LENGTH = ".snbt".length();

      private Loader() {
      }

      protected Map<ResourceLocation, CompoundTag> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
         Map<ResourceLocation, CompoundTag> map = Maps.newHashMap();
         int i = "sophisticatedbackpacks_templates".length() + 1;
         resourceManager.listResources("sophisticatedbackpacks_templates", fileName -> fileName.getPath().endsWith(".snbt"))
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
                     SophisticatedBackpacks.LOGGER.error("Couldn't parse data file {} from {}", resourceLocationWithoutSuffix, resourcelocation, ex);
                  }
               }
            );
         return map;
      }

      protected void apply(Map<ResourceLocation, CompoundTag> templates, ResourceManager resourceManager, ProfilerFiller profiler) {
         templates.forEach(DatapackBackpackTemplateManager::putBackpackTemplate);
      }
   }
}
