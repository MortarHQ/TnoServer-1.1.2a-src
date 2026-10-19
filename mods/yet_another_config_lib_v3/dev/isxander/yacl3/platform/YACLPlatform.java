package dev.isxander.yacl3.platform;

import java.nio.file.Path;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

public final class YACLPlatform {
   public static ResourceLocation parseRl(String rl) {
      return ResourceLocation.parse(rl);
   }

   public static ResourceLocation rl(String path) {
      return rl("yet_another_config_lib_v3", path);
   }

   public static ResourceLocation mcRl(String path) {
      return rl("minecraft", path);
   }

   public static ResourceLocation rl(String namespace, String path) {
      return ResourceLocation.fromNamespaceAndPath(namespace, path);
   }

   public static Env getEnvironment() {
      return switch (FMLEnvironment.dist) {
         case CLIENT -> Env.CLIENT;
         case DEDICATED_SERVER -> Env.SERVER;
         default -> throw new MatchException(null, null);
      };
   }

   public static Path getConfigDir() {
      return FMLPaths.CONFIGDIR.get();
   }

   public static boolean isDevelopmentEnv() {
      return !FMLEnvironment.production;
   }
}
