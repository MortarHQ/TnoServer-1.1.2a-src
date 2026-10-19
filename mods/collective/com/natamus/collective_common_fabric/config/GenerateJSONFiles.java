package com.natamus.collective_common_fabric.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.natamus.collective_common_fabric.data.Constants;
import com.natamus.collective_common_fabric.functions.DataFunctions;
import com.natamus.collective_common_fabric.globalcallbacks.JSONCallback;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_3218;
import net.minecraft.server.MinecraftServer;

public class GenerateJSONFiles {
   private static final List<String> requestedJsonFiles = new ArrayList<>();

   public static void requestJSONFile(String modid, List<String> fileNames) {
      for (String fileName : fileNames) {
         requestJSONFile(modid, fileName);
      }
   }

   public static void requestJSONFile(String modid, String fileName) {
      if (!requestedJsonFiles.contains(fileName)) {
         requestedJsonFiles.add(fileName);
      }

      Constants.LOG.info("[Collective] JSON file '" + fileName + "' generation requested by mod '" + modid + "'.");
   }

   public static void initGeneration(class_3218 serverLevel) {
      initGeneration(serverLevel.method_8503());
   }

   public static void initGeneration(MinecraftServer minecraftServer) {
      if (!requestedJsonFiles.isEmpty()) {
         String dirpath = DataFunctions.getConfigDirectory() + File.separator + "collective";
         File dir = new File(dirpath);
         if (!dir.isDirectory()) {
            boolean var3 = dir.mkdirs();
         }

         for (String fileName : requestedJsonFiles) {
            boolean isCreated = false;
            JsonElement jsonElement = null;
            File file = new File(dirpath + File.separator + fileName);
            if (!file.isFile()) {
               InputStream inputStream = DataFunctions.getDataInputStream(minecraftServer, "collective", "json", fileName);
               if (inputStream != null) {
                  jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                  PrintWriter writer = null;

                  try {
                     writer = new PrintWriter(dirpath + File.separator + fileName, StandardCharsets.UTF_8);
                     writer.print(Constants.GSON.toJson(jsonElement));
                  } catch (IOException ex) {
                     Constants.LOG.warn("[Collective] Unable to write the JSON file: " + fileName);
                  }

                  if (writer != null) {
                     writer.close();
                  }
               } else {
                  Constants.LOG.warn("[Collective] Unable to get Input Stream for: " + fileName);
               }
            }

            JSONCallback.JSON_FILE_AVAILABLE.invoker().onJsonFileAvailable(dirpath, fileName, isCreated, jsonElement);
         }

         JSONCallback.ALL_JSON_FILES_AVAILABLE.invoker().onAllJsonFilesAvailable(dirpath);
      }
   }
}
