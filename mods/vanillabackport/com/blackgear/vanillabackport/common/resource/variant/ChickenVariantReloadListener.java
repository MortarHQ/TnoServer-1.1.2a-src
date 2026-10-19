package com.blackgear.vanillabackport.common.resource.variant;

import com.blackgear.platform.common.resource.RegistryAwareJsonReloadListener;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public class ChickenVariantReloadListener extends RegistryAwareJsonReloadListener {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final String DIRECTORY = "chicken_variant";

   public ChickenVariantReloadListener() {
      super(GSON, "chicken_variant");
   }

   public void parse(Map<ResourceLocation, JsonElement> resources, RegistryAccess registryAccess, ResourceManager manager, ProfilerFiller profiler) {
      profiler.push("Loading chicken variants");
      ModBuiltinRegistries.CHICKEN_VARIANTS.clearDataDrivenEntries();
      DynamicOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

      for (Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
         ResourceLocation name = entry.getKey();
         JsonElement element = entry.getValue();

         try {
            ChickenVariant.CODEC
               .parse(ops, element)
               .resultOrPartial(error -> VanillaBackport.LOGGER.error("Failed to parse chicken variant {}: {}", name, error))
               .ifPresent(variant -> ModBuiltinRegistries.CHICKEN_VARIANTS.registerDataDriven(name, variant));
         } catch (JsonParseException exception) {
            VanillaBackport.LOGGER.error("Failed to parse chicken variant JSON {}: {}", new Object[]{name, exception.getMessage(), exception});
         } catch (Exception exception) {
            VanillaBackport.LOGGER.error("Unexpected error processing chicken variant {}", name, exception);
         }
      }

      profiler.pop();
   }
}
