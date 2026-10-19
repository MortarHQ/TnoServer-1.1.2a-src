package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.platform.Services;
import java.nio.file.Path;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public final class CraftTweakerConstants {
   public static final String MOD_ID = "crafttweaker";
   public static final String MOD_NAME = "CraftTweaker";
   private static final String SCRIPTS_DIRECTORY = "./scripts";
   public static final String LOG_NAME = "CRT_LOG_FILE";
   public static final String LOG_PATH = "logs/crafttweaker.log";
   public static final String ALL_LOADERS_MARKER = "*";
   public static final String INIT_LOADER_NAME = "initialize";
   public static final String DEFAULT_LOADER_NAME = "crafttweaker";
   public static final String TAGS_LOADER_NAME = "tags";
   public static final int NETWORK_VERSION = 1;
   public static final String NETWORK_VERSION_STRING = String.valueOf(1);
   public static final UUID CRAFTTWEAKER_UUID = UUID.nameUUIDFromBytes("crafttweaker".getBytes());
   public static final ResourceLocation RELOAD_LISTENER_SOURCE_ID = rl("reload_listener");
   public static final ResourceLocation CLIENT_RECIPES_UPDATED_SOURCE_ID = rl("client_recipes_updated");
   public static final String ENV_FORWARD_LOG_TO_LATEST_LOG = "crafttweaker.logger.forward_to_latest_log";
   public static final String ENV_SCRIPTS_DIRECTORY = "crafttweaker.scripts.directory";

   public static ResourceLocation rl(String path) {
      return ResourceLocation.fromNamespaceAndPath("crafttweaker", path);
   }

   public static Path scriptsDir() {
      return Services.PLATFORM.isDevelopmentEnvironment() && System.getenv().containsKey("crafttweaker.scripts.directory")
         ? Path.of(System.getenv("crafttweaker.scripts.directory"))
         : PathUtil.findFromGameDirectory("./scripts");
   }
}
