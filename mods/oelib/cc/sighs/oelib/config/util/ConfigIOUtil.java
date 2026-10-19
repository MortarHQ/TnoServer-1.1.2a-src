package cc.sighs.oelib.config.util;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.ServerConfigManager;
import cc.sighs.oelib.config.api.IConfigPermissionChecker;
import cc.sighs.oelib.config.datafix.ConfigFixRegistry;
import cc.sighs.oelib.config.model.ConfigMeta;
import cc.sighs.oelib.config.model.ConfigSide;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import cc.sighs.oelib.config.net.ConfigSyncPacket;
import cc.sighs.oelib.network.api.NetworkManager;
import cc.sighs.oelib.platform.Platform;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DataResult.Error;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class ConfigIOUtil {
   private ConfigIOUtil() {
   }

   public static <T> void applyUpdate(ConfigUnit<T> unit, ServerPlayer player, String payload, ConfigStorageFormat format, boolean save) {
      if (unit.meta().side() != ConfigSide.SERVER) {
         OELib.LOGGER.warn("Rejected update for non-server config {}", unit.id());
      } else {
         Optional<IConfigPermissionChecker> checkerOpt = ServerConfigManager.getPermissionChecker(unit.id());
         if (checkerOpt.isEmpty()) {
            OELib.LOGGER.warn("No permission checker for server config {}; rejecting client update", unit.id());
         } else {
            IConfigPermissionChecker checker = checkerOpt.get();
            if (!checker.canUpdate(player)) {
               OELib.LOGGER.warn("Player {} not permitted to update config {}", player.getGameProfile().getName(), unit.id());
            } else {
               try {
                  unit.reload();
                  Optional<ConfigManager.EncodedPayload> currentEncodedOpt = ConfigManager.encodeToString(unit.id());
                  Optional<String> lastBroadcastOpt = ServerConfigManager.getLastBroadcast(unit.id());
                  if (currentEncodedOpt.isPresent() && lastBroadcastOpt.isPresent() && !currentEncodedOpt.get().payload().equals(lastBroadcastOpt.get())) {
                     OELib.LOGGER.info("Server-side change detected for config {}, ignoring client update and broadcasting server state", unit.id());
                     NetworkManager.sendToAll(new ConfigSyncPacket(unit.id(), currentEncodedOpt.get().payload(), currentEncodedOpt.get().format()));
                     ServerConfigManager.recordBroadcast(unit.id(), currentEncodedOpt.get().payload());
                     return;
                  }

                  DataResult<T> result = ConfigSerializationUtil.parse(payload, format, unit.codec().codec());
                  if (result.error().isPresent()) {
                     OELib.LOGGER.error("Failed to parse update for config {}: {}", unit.id(), ((Error)result.error().get()).message());
                     return;
                  }

                  result.result()
                     .ifPresent(
                        v -> {
                           OELib.LOGGER
                              .info(
                                 "Server applying update for config {} requested by {} with format {} (save={})",
                                 new Object[]{unit.id(), player.getGameProfile().getName(), format, save}
                              );
                           unit.setValue((T)v);
                           if (save) {
                              unit.save();
                           }

                           ConfigManager.encodeToString(unit.id()).ifPresent(encoded -> {
                              OELib.LOGGER.info("Broadcasting config {} to clients", unit.id());
                              NetworkManager.sendToAll(new ConfigSyncPacket(unit.id(), encoded.payload(), encoded.format()));
                              ServerConfigManager.recordBroadcast(unit.id(), encoded.payload());
                           });
                        }
                     );
               } catch (Exception e) {
                  OELib.LOGGER.error("Exception while applying server config update {}", unit.id(), e);
               }
            }
         }
      }
   }

   public static <T> void initializeIfMissing(ConfigUnit<T> unit) {
      Path path = resolveSavePath(unit.meta());

      try {
         if (!Files.exists(path)) {
            ConfigMeta meta = unit.meta();
            Optional<ConfigFixRegistry.Chain> chainOpt = ConfigFixRegistry.get(meta.id());
            int version = chainOpt.map(ConfigFixRegistry.Chain::currentVersion).orElse(0);
            Optional<String> content = ConfigSerializationUtil.encodeToStringWithVersion(
               unit.getDefaultValue(), version, meta.format(), unit.codec().codec(), unit.codec().fields()
            );
            boolean success = content.isPresent();
            if (success) {
               Path parent = path.getParent();
               if (parent != null) {
                  Files.createDirectories(parent);
               }

               Files.writeString(path, content.get(), StandardCharsets.UTF_8);
            }

            if (success) {
               OELib.LOGGER.info("Initialized config {} at {}", meta.id(), path);
            } else {
               OELib.LOGGER.error("Failed to initialize config {} at {}", meta.id(), path);
            }
         }
      } catch (Exception e) {
         OELib.LOGGER.error("Exception during config initialization {}", unit.meta().id(), e);
      }
   }

   public static <T> void applyAutoMigrationOnRegister(ConfigUnit<T> unit) {
      ConfigMeta meta = unit.meta();

      try {
         Path existing = findExistingPathAnyFormat(meta);
         Path target = resolveSavePath(meta);
         if (existing == null && !Files.exists(target)) {
            initializeIfMissing(unit);
            return;
         }

         Path source = existing != null ? existing : target;
         String raw = Files.readString(source, StandardCharsets.UTF_8);
         ConfigStorageFormat formatSource = detectFormatFromPath(source, meta.format());
         Dynamic<?> dyn = ConfigSerializationUtil.parseToDynamic(raw, formatSource);
         int inputVersion = dyn.get("__cfg_version").asInt(0);
         Optional<ConfigFixRegistry.Chain> chainOpt = ConfigFixRegistry.get(meta.id());
         Dynamic<?> migrated = dyn;
         int currentVersion = 0;
         if (chainOpt.isPresent()) {
            ConfigFixRegistry.Chain chain = chainOpt.get();
            currentVersion = chain.currentVersion();
            migrated = chain.apply(dyn, inputVersion);
         }

         DataResult<T> result = unit.codec().codec().parse(migrated);
         T value = (T)result.result().orElse(unit.getDefaultValue());
         Optional<String> encoded = ConfigSerializationUtil.encodeToStringWithVersion(
            value, currentVersion, meta.format(), unit.codec().codec(), unit.codec().fields()
         );
         if (encoded.isEmpty()) {
            return;
         }

         String newContent = encoded.get();
         boolean changed = !normalize(raw).equals(normalize(newContent)) || existing != null && !existing.equals(target);
         if (changed) {
            Path parent = target.getParent();
            if (parent != null) {
               Files.createDirectories(parent);
            }

            Files.writeString(target, newContent, StandardCharsets.UTF_8);
            if (existing != null && !existing.equals(target) && Files.exists(existing)) {
               try {
                  Files.delete(existing);
               } catch (Exception var19) {
               }
            }

            OELib.LOGGER.info("Migrated and rewrote config {} to {}", meta.id(), target);
            unit.reload();
         } else {
            unit.reload();
         }
      } catch (Exception e) {
         OELib.LOGGER.error("Exception during auto migration for {}", meta.id(), e);
      }
   }

   public static Path resolveBaseDirectory(ConfigMeta meta) {
      Path base = Platform.getConfigPath();
      if (meta.directory() != null && !meta.directory().isEmpty()) {
         base = base.resolve(meta.directory());
      }

      return base;
   }

   public static Path resolveLoadPath(ConfigMeta meta) {
      Path base = resolveBaseDirectory(meta);
      String name = meta.fileName();
      if (name.contains(".")) {
         return base.resolve(name);
      }

      String ext = meta.format().name().toLowerCase();
      Path extPath = base.resolve(name + "." + ext);
      if (Files.exists(extPath)) {
         return extPath;
      }

      Path directPath = base.resolve(name);
      return Files.exists(directPath) ? directPath : extPath;
   }

   public static Path resolveSavePath(ConfigMeta meta) {
      Path base = resolveBaseDirectory(meta);
      String name = meta.fileName();
      if (name.contains(".")) {
         return base.resolve(name);
      }

      String ext = meta.format().name().toLowerCase();
      return base.resolve(name + "." + ext);
   }

   public static Path findExistingPathAnyFormat(ConfigMeta meta) {
      Path base = resolveBaseDirectory(meta);
      String name = meta.fileName();
      if (name.contains(".")) {
         Path p = base.resolve(name);
         return Files.exists(p) ? p : null;
      }

      for (ConfigStorageFormat fmt : ConfigStorageFormat.values()) {
         String ext = fmt.name().toLowerCase();
         Path p = base.resolve(name + "." + ext);
         if (Files.exists(p)) {
            return p;
         }
      }

      Path direct = base.resolve(name);
      return Files.exists(direct) ? direct : null;
   }

   public static ConfigStorageFormat detectFormatFromPath(Path path, ConfigStorageFormat fallback) {
      String fn = path.getFileName().toString().toLowerCase();
      if (fn.endsWith(".json")) {
         return ConfigStorageFormat.JSON;
      } else if (fn.endsWith(".json5")) {
         return ConfigStorageFormat.JSON5;
      } else {
         return fn.endsWith(".toml") ? ConfigStorageFormat.TOML : fallback;
      }
   }

   private static String normalize(String s) {
      return s.replace("\r\n", "\n").trim();
   }
}
