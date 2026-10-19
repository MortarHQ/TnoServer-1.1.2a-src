package de.cristelknight.cristellib.neoforge;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.cristellib.CristelLib;
import de.cristelknight.cristellib.CristelLibRegistry;
import de.cristelknight.cristellib.StructureConfig;
import de.cristelknight.cristellib.api.CristelLibAPI;
import de.cristelknight.cristellib.autoconfig.ModFinder;
import de.cristelknight.cristellib.builtinpacks.BuiltinResourcePackSource;
import de.cristelknight.cristellib.neoforge.extraapiutil.APIFinder;
import de.cristelknight.cristellib.util.Platform;
import de.cristelknight.cristellib.util.Util;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.locating.IModFile;

public class CristelLibExpectPlatformImpl {
   public static Path getConfigDirectory() {
      return FMLPaths.CONFIGDIR.get();
   }

   public static PackResources registerBuiltinResourcePack(ResourceLocation id, Component displayName) {
      String modID = id.getNamespace();
      String path = id.getPath();
      Path totalPath = getResourceDirectory(modID, id.getPath());
      if (totalPath != null) {
         PackLocationInfo metadata = new PackLocationInfo(
            id.toString(),
            displayName,
            new BuiltinResourcePackSource(),
            Optional.of(new KnownPack("cristellib", id.toString(), ModList.get().getModFileById(modID).versionString()))
         );
         return new PathPackResources(metadata, totalPath);
      } else {
         CristelLib.LOGGER.debug("Couldn't find path: {} in container for modID: {} for pack with display name: {}", path, modID, displayName);
         return null;
      }
   }

   @Nullable
   public static Path getResourceDirectory(String modId, String subPath) {
      ModList modList = ModList.get();
      IModFile file;
      if (modList == null) {
         ModInfo info = ModLoadingUtilImpl.getPreLoadedModInfo(modId);
         if (info == null) {
            CristelLib.LOGGER.warn("Mod info for modId: {} is null", modId);
            return null;
         }

         file = info.getOwningFile().getFile();
      } else {
         ModContainer container = (ModContainer)modList.getModContainerById(modId).orElse(null);
         if (container == null) {
            CristelLib.LOGGER.warn("Mod container for modId: {} is null", modId);
            return null;
         }

         file = container.getModInfo().getOwningFile().getFile();
      }

      Path path = file.findResource(new String[]{subPath});
      if (path == null) {
         CristelLib.LOGGER.warn("Path for subPath: {} in modId: {} is null", subPath, modId);
      }

      return path;
   }

   public static Map<String, Set<StructureConfig>> getConfigs(CristelLibRegistry registry) {
      Map<String, Set<StructureConfig>> configs = new HashMap<>();

      for (Pair<List<String>, CristelLibAPI> apiPair : APIFinder.scanForAPIs()) {
         CristelLibAPI api = (CristelLibAPI)apiPair.getSecond();
         String modID = (String)((List)apiPair.getFirst()).getFirst();
         CristelLib.readAPI(registry, modID, api, configs);
      }

      Util.readData(configs);
      ModFinder.addConfigs(configs, registry);
      return configs;
   }

   public static List<String> getModIds() {
      ModList modList = ModList.get();
      List<String> modIds = new ArrayList<>();
      if (modList != null) {
         for (IModInfo modInfo : modList.getMods()) {
            modIds.add(modInfo.getModId());
         }
      } else {
         for (IModInfo modInfo : LoadingModList.get().getMods()) {
            modIds.add(modInfo.getModId());
         }
      }

      return modIds;
   }

   public static List<Path> getRootPaths(String modId) {
      ModList modList = ModList.get();
      List<Path> paths = new ArrayList<>();
      IModFile file;
      if (modList == null) {
         ModInfo info = ModLoadingUtilImpl.getPreLoadedModInfo(modId);
         if (info == null) {
            return paths;
         }

         file = info.getOwningFile().getFile();
      } else {
         ModContainer container = (ModContainer)modList.getModContainerById(modId).orElse(null);
         if (container == null) {
            return paths;
         }

         file = container.getModInfo().getOwningFile().getFile();
      }

      return Collections.singletonList(file.getSecureJar().getRootPath());
   }

   public static Platform getPlatform() {
      return Platform.FORGE;
   }

   public static String getModDisplayName(String modId) {
      return ModList.get().getModContainerById(modId).map(container -> container.getModInfo().getDisplayName()).orElse(modId);
   }

   public static boolean isClient() {
      return FMLEnvironment.dist.isClient();
   }
}
