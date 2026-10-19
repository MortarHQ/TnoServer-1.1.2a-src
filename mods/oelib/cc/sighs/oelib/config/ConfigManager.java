package cc.sighs.oelib.config;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.api.IConfigPermissionChecker;
import cc.sighs.oelib.config.model.ConfigMeta;
import cc.sighs.oelib.config.model.ConfigSide;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class ConfigManager {
   private static final ThreadLocal<Boolean> UPDATING_FROM_SERVER = ThreadLocal.withInitial(() -> Boolean.FALSE);

   private ConfigManager() {
   }

   public static void registerClient(ConfigUnit<?> unit) {
      registerUnit(unit);
   }

   public static void registerServer(ConfigUnit<?> unit, IConfigPermissionChecker permissionChecker) {
      registerUnitServer(unit, permissionChecker);
   }

   public static <T> ConfigUnit<T> register(ConfigCodec<T> codec, T defaultValue) {
      ConfigUnit<T> unit = ConfigUnit.of(codec, defaultValue);
      registerUnit(unit);
      return unit;
   }

   public static void registerUnit(ConfigUnit<?> unit) {
      ConfigMeta meta = unit.meta();
      if (meta.fileName() == null || meta.fileName().isBlank()) {
         OELib.LOGGER.error("Config {} has empty filename; please set meta.fileName()", meta.id());
      }

      if (meta.format() == null) {
         OELib.LOGGER.error("Config {} has null format; please set meta.format()", meta.id());
      }

      if (meta.side() == null) {
         OELib.LOGGER.error("Config {} has null side; please set meta.side()", meta.id());
      }

      OELib.LOGGER
         .info(
            "Registered config {} from mod {} side {} format {} filename {} directory {}",
            new Object[]{meta.id(), meta.id().getNamespace(), meta.side(), meta.format(), meta.fileName(), meta.directory()}
         );
      if (meta.side() == ConfigSide.CLIENT) {
         ClientConfigManager.registerUnit(unit);
      } else if (meta.side() == ConfigSide.SERVER) {
         registerUnitServer(unit, null);
      }
   }

   public static void registerUnitServer(ConfigUnit<?> unit, IConfigPermissionChecker permissionChecker) {
      ServerConfigManager.registerUnit(unit, permissionChecker);
   }

   private static <T> T deriveDefault(Codec<T> codec) {
      JsonObject element = new JsonObject();
      DataResult<T> res = codec.parse(JsonOps.INSTANCE, element);
      return (T)res.result().orElseThrow(() -> new IllegalStateException("Missing defaults for config; please specify defaultValue for all fields"));
   }

   public static Optional<ConfigUnit<?>> get(ResourceLocation id) {
      Optional<ConfigUnit<?>> server = ServerConfigManager.get(id);
      return server.isPresent() ? server : ClientConfigManager.get(id);
   }

   public static void reload(ResourceLocation id) {
      get(id).ifPresent(ConfigUnit::reload);
   }

   public static void reloadAll() {
      reloadAllServer();
      reloadAllClient();
   }

   public static void reloadAllServer() {
      ServerConfigManager.reloadAll();
   }

   public static void reloadAllClient() {
      ClientConfigManager.reloadAll();
   }

   public static void applyRemoteUpdate(ResourceLocation id, String payload, ConfigStorageFormat format) {
      ServerConfigManager.applyRemoteUpdate(id, payload, format);
   }

   public static boolean isUpdatingFromServer() {
      return UPDATING_FROM_SERVER.get();
   }

   public static void runWithServerUpdate(Runnable runnable) {
      boolean previous = UPDATING_FROM_SERVER.get();
      UPDATING_FROM_SERVER.set(Boolean.TRUE);

      try {
         runnable.run();
      } finally {
         UPDATING_FROM_SERVER.set(previous);
      }
   }

   public static <T> T callWithServerUpdate(Supplier<T> supplier) {
      boolean previous = UPDATING_FROM_SERVER.get();
      UPDATING_FROM_SERVER.set(Boolean.TRUE);

      try {
         return supplier.get();
      } finally {
         UPDATING_FROM_SERVER.set(previous);
      }
   }

   public static Optional<ConfigManager.EncodedPayload> encodeToString(ResourceLocation id) {
      return ServerConfigManager.encodeToString(id);
   }

   public record EncodedPayload(ConfigStorageFormat format, String payload) {
   }
}
