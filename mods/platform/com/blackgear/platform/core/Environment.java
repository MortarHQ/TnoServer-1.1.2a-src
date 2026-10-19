package com.blackgear.platform.core;

import com.blackgear.platform.core.neoforge.EnvironmentImpl;
import com.blackgear.platform.core.util.config.ConfigBuilder;
import com.blackgear.platform.core.util.config.IConfigSpec;
import com.blackgear.platform.core.util.config.ModConfig;
import com.blackgear.platform.core.util.config.SimpleConfigBuilder;
import com.blackgear.platform.core.util.config.SimpleConfigSpec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.thread.BlockableEventLoop;
import org.apache.commons.lang3.tuple.Pair;

public class Environment {
   @ExpectPlatform
   @Transformed
   public static boolean isClientSide() {
      return EnvironmentImpl.isClientSide();
   }

   @ExpectPlatform
   @Transformed
   public static boolean isProduction() {
      return EnvironmentImpl.isProduction();
   }

   @ExpectPlatform
   @Transformed
   public static boolean hasModLoaded(String modId) {
      return EnvironmentImpl.hasModLoaded(modId);
   }

   @ExpectPlatform
   @Transformed
   public static String getModVersion(String modId) {
      return EnvironmentImpl.getModVersion(modId);
   }

   @ExpectPlatform
   @Transformed
   public static Optional<MinecraftServer> getCurrentServer() {
      return EnvironmentImpl.getCurrentServer();
   }

   @ExpectPlatform
   @Transformed
   public static BlockableEventLoop<?> getGameExecutor() {
      return EnvironmentImpl.getGameExecutor();
   }

   @ExpectPlatform
   @Transformed
   public static Path getGameDir() {
      return EnvironmentImpl.getGameDir();
   }

   @ExpectPlatform
   @Transformed
   public static Path getConfigDir() {
      return EnvironmentImpl.getConfigDir();
   }

   public static <T> T registerConfig(String modId, ModConfig.Type type, String fileName, Function<ConfigBuilder, T> spec) {
      Pair<T, SimpleConfigSpec> pair = new SimpleConfigBuilder().configure(spec);
      new ModConfig(type, (IConfigSpec<?>)pair.getRight(), modId, fileName);
      return (T)pair.getLeft();
   }

   public static <T> T registerConfig(String modId, ModConfig.Type type, Function<ConfigBuilder, T> spec) {
      return registerConfig(modId, type, defaultConfigName(type, modId), spec);
   }

   @Deprecated(forRemoval = true)
   public static <T> T registerSafeConfig(String modId, ModConfig.Type type, Function<ConfigBuilder, T> spec) {
      return registerConfig(modId, type, spec);
   }

   @Deprecated(forRemoval = true)
   public static <T> T registerSafeConfig(String modId, ModConfig.Type type, String fileName, Function<ConfigBuilder, T> spec) {
      return registerConfig(modId, type, fileName, spec);
   }

   @Deprecated(forRemoval = true)
   public static <T> T registerUnsafeConfig(String modId, ModConfig.Type type, String fileName, Function<ConfigBuilder, T> spec) {
      return registerConfig(modId, type, fileName, spec);
   }

   @Deprecated(forRemoval = true)
   public static <T> T registerUnsafeConfig(String modId, ModConfig.Type type, Function<ConfigBuilder, T> spec) {
      return registerConfig(modId, type, spec);
   }

   private static String defaultConfigName(ModConfig.Type type, String modId) {
      return String.format("%s-%s.toml", modId, type.extension());
   }

   @ExpectPlatform
   @Transformed
   public static Environment.Loader getLoader() {
      return EnvironmentImpl.getLoader();
   }

   public static boolean isForge() {
      return getLoader() == Environment.Loader.FORGE;
   }

   public static boolean isFabric() {
      return getLoader() == Environment.Loader.FABRIC;
   }

   public enum Loader {
      FORGE,
      FABRIC;
   }
}
