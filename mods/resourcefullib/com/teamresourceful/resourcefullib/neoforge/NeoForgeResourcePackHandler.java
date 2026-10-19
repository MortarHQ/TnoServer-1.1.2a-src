package com.teamresourceful.resourcefullib.neoforge;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources.PathResourcesSupplier;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Metadata;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.server.packs.repository.Pack.ResourcesSupplier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.Nullable;

public final class NeoForgeResourcePackHandler {
   private static final List<NeoForgeResourcePackHandler.ResourcePack> RESOURCE_PACKS = new ArrayList<>();
   private static final String RESOURCE_PACK_KEY = "resourcefullib:resourcepack";

   public static void load() {
      for (IModInfo mod : ModList.get().getMods()) {
         if (mod.getModProperties().containsKey("resourcefullib:resourcepack")) {
            try {
               initMod(mod, mod.getModProperties());
            } catch (Exception e) {
               Constants.LOGGER.error("Resourceful Lib failed to load resource pack for mod: " + mod.getDisplayName(), e);
            }
         }
      }
   }

   private static void initMod(IModInfo mod, Map<String, Object> metadata) {
      for (Object pack : (List)metadata.get("resourcefullib:resourcepack")) {
         loadPack(mod, pack);
      }
   }

   private static void loadPack(IModInfo mod, Object value) {
      if (value instanceof String string) {
         RESOURCE_PACKS.add(new NeoForgeResourcePackHandler.ResourcePack(mod, string, null, false));
      } else if (value instanceof UnmodifiableConfig config) {
         Map<String, Object> map = config.valueMap();
         String name = getOrThrow(map, "name");
         String description = (String)map.get("description");
         boolean required = (Boolean)map.getOrDefault("required", false);
         RESOURCE_PACKS.add(new NeoForgeResourcePackHandler.ResourcePack(mod, name, description, required));
      }
   }

   private static <T> T getOrThrow(Map<?, ?> map, String id) {
      if (!map.containsKey(id)) {
         throw new IllegalStateException("Missing key: " + id);
      } else {
         return (T)map.get(id);
      }
   }

   public static void onRegisterPackFinders(AddPackFindersEvent event) {
      for (NeoForgeResourcePackHandler.ResourcePack resourcePack : RESOURCE_PACKS) {
         try {
            Path path = resourcePack.mod().getOwningFile().getFile().findResource(new String[]{"resourcepacks/" + resourcePack.name()});
            if (Files.isDirectory(path.resolve(event.getPackType().getDirectory()))) {
               String id = ResourceLocation.fromNamespaceAndPath(resourcePack.mod().getModId(), resourcePack.name()).toString();
               ResourcesSupplier supplier = new PathResourcesSupplier(path);
               PackLocationInfo locationInfo = new PackLocationInfo(
                  id,
                  createDescription(resourcePack.description(), resourcePack.name()),
                  PackSource.create(PackSource.NO_DECORATION, resourcePack.required()),
                  Optional.empty()
               );
               Metadata info = getInfo(locationInfo, supplier, event.getPackType(), resourcePack.required());
               PackSelectionConfig config = new PackSelectionConfig(resourcePack.required(), Position.TOP, false);
               Pack pack = new Pack(locationInfo, supplier, info, config);
               event.addRepositorySource(source -> source.accept(pack));
            }
         } catch (Exception ignored) {
            Constants.LOGGER.error("Resourceful Lib failed to init resource pack for mod: " + resourcePack.mod().getDisplayName());
         }
      }
   }

   private static Metadata getInfo(PackLocationInfo locationInfo, ResourcesSupplier supplier, PackType type, boolean hidden) {
      if (!hidden) {
         Metadata info = Pack.readPackMetadata(locationInfo, supplier, SharedConstants.getCurrentVersion().getPackVersion(type));
         if (info != null) {
            return info;
         }
      }

      return new Metadata(CommonComponents.EMPTY, PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), hidden);
   }

   private static Component createDescription(@Nullable String description, String name) {
      return description != null ? Component.literal(description) : Component.translatableWithFallback("resourcefullib.resourcepack." + name, name);
   }

   private record ResourcePack(IModInfo mod, String name, @Nullable String description, boolean required) {
   }
}
