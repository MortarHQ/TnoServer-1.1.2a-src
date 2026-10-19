package com.natamus.collective_common_forge.functions;

import com.natamus.collective_common_forge.services.Services;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import org.apache.commons.lang3.ArrayUtils;

public class DataFunctions {
   public static String getCurrentMinecraftVersion() {
      return "1.21.1";
   }

   public static String getGameDirectory() {
      return Services.MODLOADER.getGameDirectory();
   }

   public static Path getGameDirectoryPath() {
      return Path.of(getGameDirectory());
   }

   public static String getModDirectory() {
      return getGameDirectory() + File.separator + "mods";
   }

   public static Path getModDirectoryPath() {
      return Path.of(getModDirectory());
   }

   public static String getConfigDirectory() {
      return getGameDirectory() + File.separator + "config";
   }

   public static Path getConfigDirectoryPath() {
      return Path.of(getConfigDirectory());
   }

   public static List<String> getInstalledModJars() {
      List<String> installedmods = new ArrayList<>();
      File mainFolder = new File(getModDirectory());
      File[] listOfMainFiles = mainFolder.listFiles();
      File subFolder = new File(getModDirectory() + File.separator + getCurrentMinecraftVersion());
      File[] listOfSubFiles = subFolder.listFiles();

      for (File file : (File[])ArrayUtils.addAll(listOfMainFiles, listOfSubFiles)) {
         if (file.isFile()) {
            String filename = file.getName().replaceAll(" +\\([0-9]+\\)", "");
            installedmods.add(filename);
         }
      }

      return installedmods;
   }

   public static byte setBit(byte b, int i, boolean bo) {
      if (bo) {
         b = (byte)(b | i);
      } else {
         b = (byte)(b & ~i);
      }

      return b;
   }

   public static InputStream getDataInputStream(
      MinecraftServer minecraftServer, String modid, String folder, String fileNameWithoutExtension, String fileExtension
   ) {
      return getDataInputStream(minecraftServer, modid, folder, fileNameWithoutExtension + fileExtension);
   }

   public static InputStream getDataInputStream(MinecraftServer minecraftServer, String modid, String folder, String fileName) {
      folder = folder.replace("\\", "/").strip();
      if (!folder.endsWith("/")) {
         folder = folder + "/";
      }

      try {
         Optional<Resource> resourceOptional = minecraftServer.getResourceManager()
            .getResource(ResourceLocation.fromNamespaceAndPath(modid, folder + fileName));
         if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            return resource.open();
         }
      } catch (IOException var6) {
      }

      return null;
   }
}
