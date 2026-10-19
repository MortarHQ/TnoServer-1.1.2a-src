package cc.sighs.oelib.config;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.api.ConfigEvents;
import cc.sighs.oelib.config.api.IConfigPermissionChecker;
import cc.sighs.oelib.config.model.ConfigMeta;
import cc.sighs.oelib.config.model.ConfigSide;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import cc.sighs.oelib.config.net.ConfigSyncPacket;
import cc.sighs.oelib.config.net.ConfigUpdateRequestPacket;
import cc.sighs.oelib.config.util.ConfigSerializationUtil;
import cc.sighs.oelib.data.DataManager;
import cc.sighs.oelib.network.api.NetworkManager;
import cc.sighs.oelib.platform.Platform;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DataResult.Error;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ServerConfigManager implements ResourceManagerReloadListener {
   private static final Map<ResourceLocation, ConfigUnit<?>> CONFIGS = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, IConfigPermissionChecker> PERMISSIONS = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, String> LAST_BROADCAST = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, String> CLIENT_KNOWN_SERVER = new ConcurrentHashMap<>();

   static void registerUnit(ConfigUnit<?> unit, IConfigPermissionChecker permissionChecker) {
      ConfigMeta meta = unit.meta();
      if (meta.side() != ConfigSide.SERVER) {
         OELib.LOGGER.warn("Registering non-server config {} into ServerConfigManager (side={})", meta.id(), meta.side());
      }

      ConfigUnit<?> existing = CONFIGS.put(unit.id(), unit);
      if (existing != null) {
         OELib.LOGGER.warn("Duplicate server config registration for {}, replacing previous unit", unit.id());
      }

      if (permissionChecker != null) {
         PERMISSIONS.put(unit.id(), permissionChecker);
      }

      unit.applyAutoMigrationOnRegister();
   }

   static Optional<ConfigUnit<?>> get(ResourceLocation id) {
      return Optional.ofNullable(CONFIGS.get(id));
   }

   public static Map<ResourceLocation, ConfigUnit<?>> all() {
      return Map.copyOf(CONFIGS);
   }

   public static Optional<IConfigPermissionChecker> getPermissionChecker(ResourceLocation id) {
      return Optional.ofNullable(PERMISSIONS.get(id));
   }

   public static void recordBroadcast(ResourceLocation id, String payload) {
      LAST_BROADCAST.put(id, payload);
      CLIENT_KNOWN_SERVER.put(id, payload);
   }

   public static Optional<String> getLastBroadcast(ResourceLocation id) {
      return Optional.ofNullable(LAST_BROADCAST.get(id));
   }

   public static void recordClientKnownServer(ResourceLocation id, String payload) {
      CLIENT_KNOWN_SERVER.put(id, payload);
   }

   public static Optional<String> getClientKnownServer(ResourceLocation id) {
      return Optional.ofNullable(CLIENT_KNOWN_SERVER.get(id));
   }

   static void reloadAll() {
      for (ConfigUnit<?> unit : CONFIGS.values()) {
         unit.reload();
         if (Platform.isServer()) {
            ResourceLocation id = unit.id();
            Optional<ConfigManager.EncodedPayload> payloadOpt = encodeToString(id);
            payloadOpt.ifPresent(encodedx -> {
               recordBroadcast(unit.id(), encodedx.payload());
               if (DataManager.getServer() != null && !Platform.getAllPlayers(DataManager.getServer()).isEmpty()) {
                  NetworkManager.sendToAll(new ConfigSyncPacket(unit.id(), encodedx.payload(), encodedx.format()));
               } else {
                  OELib.LOGGER.debug("Skipping config sync for {}: No players online or server starting.", unit.id());
               }
            });
         } else {
            ResourceLocation id = unit.id();
            ConfigStorageFormat format = unit.meta().format();
            ConfigUnit<Object> cast = (ConfigUnit<Object>)unit;
            Optional<String> encoded = ConfigSerializationUtil.encodeToString(cast.get(), format, cast.codec().codec(), cast.codec().fields());
            if (encoded.isPresent()) {
               Optional<String> known = getClientKnownServer(id);
               if ((known.isEmpty() || !known.get().equals(encoded.get()))
                  && DataManager.getServer() != null
                  && !Platform.getAllPlayers(DataManager.getServer()).isEmpty()) {
                  NetworkManager.sendToServer(new ConfigUpdateRequestPacket(id, encoded.get(), format, true));
               }
            }
         }
      }
   }

   static Optional<ConfigManager.EncodedPayload> encodeToString(ResourceLocation id) {
      ConfigUnit<?> unit = CONFIGS.get(id);
      if (unit == null) {
         return Optional.empty();
      }

      ConfigStorageFormat format = unit.meta().format();
      ConfigUnit<Object> cast = (ConfigUnit<Object>)unit;
      Optional<String> encoded = ConfigSerializationUtil.encodeToString(cast.get(), format, cast.codec().codec(), cast.codec().fields());
      return encoded.map(s -> new ConfigManager.EncodedPayload(format, s));
   }

   static void applyRemoteUpdate(ResourceLocation id, String payload, ConfigStorageFormat format) {
      ConfigUnit<?> unit = CONFIGS.get(id);
      if (unit != null) {
         DataResult<?> result = ConfigSerializationUtil.parse(payload, format, unit.codec().codec());
         if (result.error().isPresent()) {
            OELib.LOGGER.error("Failed to apply remote server config {}: {}", unit.id(), ((Error)result.error().get()).message());
         } else {
            result.result().ifPresent(v -> {
               ConfigUnit<Object> cast = (ConfigUnit<Object>)unit;
               OELib.LOGGER.info("Applying remote update for server config {} with format {}", unit.id(), format);
               cast.setValue(v);
               ConfigEvents.onSync(cast, v, true);
               OELib.LOGGER.info("Applied remote update for server config {}", unit.id());
            });
         }
      }
   }

   public void onResourceManagerReload(ResourceManager resourceManager) {
      reloadAll();
      OELib.LOGGER.info("Successfully reload {} server config(s).", CONFIGS.size());
   }
}
