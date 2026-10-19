package de.cristelknight.cristellib.data;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.cristellib.CristelLib;
import de.cristelknight.cristellib.CristelLibExpectPlatform;
import de.cristelknight.cristellib.StructureConfig;
import de.cristelknight.cristellib.autoconfig.ACInfoData;
import de.cristelknight.cristellib.builtinpacks.BuiltInDataPackLoader;
import de.cristelknight.cristellib.config.ConfigManager;
import de.cristelknight.cristellib.data.codec.BuiltInPackData;
import de.cristelknight.cristellib.data.codec.BuiltInPackDataWrapper;
import de.cristelknight.cristellib.data.codec.CopyFileData;
import de.cristelknight.cristellib.util.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.FileUtils;

public class ReadData {
   public static void getAutoConfigSettings(String modId, Map<String, ACInfoData> data) {
      for (Path path : PathFinder.getPathsInDir(modId, "auto_config")) {
         ACInfoData acInfoData = ConfigManager.readFromJsonPath(
            String.format("Couldn't read %s, crashing instead. This file is corrupted!", path), path, ACInfoData.CODEC
         );
         if (data.containsKey(modId)) {
            CristelLib.LOGGER.warn("Overriding Auto Config data for modID: {} from path: {}", modId, path);
         }

         data.put(modId, acInfoData);
      }
   }

   public static void getStructureConfigs(String modId, Map<String, Set<StructureConfig>> modIdAndConfigs) {
      for (Path path : PathFinder.getPathsInDir(modId, "structure_config")) {
         StructureConfig config = ConfigManager.readFromJsonPath(
            String.format("Couldn't read %s, crashing instead. This file is corrupted!", path), path, StructureConfig.CODEC
         );
         if (!modId.equals("minecraft") || !checkForReplace(modIdAndConfigs, path, config)) {
            modIdAndConfigs.computeIfAbsent(modId, k -> new HashSet<>()).add(config);
         }
      }
   }

   private static boolean checkForReplace(Map<String, Set<StructureConfig>> modIdAndConfigs, Path path, StructureConfig config) {
      try {
         Pair<String, String> pair = Util.parseNamespaceAndPath(Util.fileName(path), '@');
         String namespace = (String)pair.getFirst();
         Set<StructureConfig> configs = modIdAndConfigs.computeIfAbsent(namespace, k -> new HashSet<>());
         Iterator<StructureConfig> it = configs.iterator();

         while (it.hasNext()) {
            StructureConfig old = it.next();
            if (Util.fileName(old.getPath()).equals(pair.getSecond())) {
               it.remove();
               configs.add(config);
               return true;
            }
         }

         configs.add(config);
         return true;
      } catch (ResourceLocationException ignored) {
         return false;
      }
   }

   public static void getBuiltInPacks(String modId) {
      for (Path path : PathFinder.getPathsInDir(modId, "data_pack")) {
         Either<BuiltInPackData, BuiltInPackDataWrapper> either = ConfigManager.readFromJsonPath(
            String.format("Couldn't read %s, crashing instead. This file is corrupted!", path), path, BuiltInPackData.PACKS_CODEC
         );
         either.left().ifPresent(ReadData::loadPack);
         either.right().ifPresent(wrapper -> {
            List<BuiltInPackData> packs = new ArrayList<>(wrapper.packs());
            Collections.reverse(packs);
            packs.forEach(ReadData::loadPack);
         });
      }
   }

   public static void loadPack(BuiltInPackData pack) {
      boolean bl = Conditions.readConditions(pack.conditions());
      BuiltInDataPackLoader.registerPack(pack.location(), Component.nullToEmpty(pack.displayName()), () -> bl);
   }

   public static void copyFile(String modId) {
      for (Path path : PathFinder.getPathsInDir(modId, "copy_file")) {
         CopyFileData copyFileData = ConfigManager.readFromJsonPath(
            String.format("Couldn't read %s, crashing instead. This file is corrupted!", path), path, CopyFileData.CODEC
         );
         if (Conditions.readConditions(copyFileData.conditions())) {
            copyFileFromJar(copyFileData.location(), copyFileData.destination());
         }
      }
   }

   public static void copyFileFromJar(ResourceLocation from, String to) {
      String modID = from.getNamespace();
      String location = from.getPath();

      for (Path p : CristelLibExpectPlatform.getRootPaths(modID)) {
         Path fromFile = p.resolve(location);
         File toFile = Util.pathFromString(to).toFile();
         if (fromFile != null && toFile != null && !toFile.exists()) {
            try {
               FileUtils.copyURLToFile(fromFile.toUri().toURL(), toFile);
            } catch (IOException e) {
               CristelLib.LOGGER.error("Couldn't copy file from: {} to: {}", fromFile, toFile, e);
            }
         }
      }
   }
}
