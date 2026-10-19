package dev.ftb.mods.ftblibrary.config.manager;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.net.SyncConfigFromServerPacket;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.snbt.config.ConfigUtil;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.util.BooleanConsumer;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public enum ConfigManager {
   INSTANCE;

   final Map<String, ConfigManager.TrackedConfig> pendingClient = new HashMap<>();
   final Map<String, ConfigManager.TrackedConfig> pendingServer = new HashMap<>();
   private final Map<String, ConfigManager.TrackedConfig> trackedConfigs = new ConcurrentHashMap<>();
   private boolean inited = false;

   public static ConfigManager getInstance() {
      return INSTANCE;
   }

   public void init() {
      if (this.inited) {
         throw new IllegalStateException("already initialised!");
      }

      LifecycleEvent.SERVER_BEFORE_START.register(this::onServerStarting);
      PlayerEvent.PLAYER_JOIN.register(this::onPlayerLogin);
      this.inited = true;
   }

   public SNBTConfig registerClientConfig(SNBTConfig config, String groupPrefix) {
      return this.registerClientConfig(config, groupPrefix, ConfigManager.TrackedConfig.NO_ACTION);
   }

   public SNBTConfig registerClientConfig(SNBTConfig config, String groupPrefix, BooleanConsumer onEdited) {
      this.pendingClient
         .put(config.key, ConfigManager.TrackedConfig.createForRegistration(groupPrefix, ConfigManager.ConfigType.CLIENT, config, false, onEdited));
      return config;
   }

   public SNBTConfig registerServerConfig(SNBTConfig config, String groupPrefix, boolean sync) {
      return this.registerServerConfig(config, groupPrefix, sync, ConfigManager.TrackedConfig.NO_ACTION);
   }

   public SNBTConfig registerServerConfig(SNBTConfig config, String groupPrefix, boolean sync, BooleanConsumer onEdited) {
      this.pendingServer
         .put(config.key, ConfigManager.TrackedConfig.createForRegistration(groupPrefix, ConfigManager.ConfigType.SERVER, config, sync, onEdited));
      return config;
   }

   public SNBTConfig registerStartupConfig(SNBTConfig config, String groupPrefix) {
      ConfigManager.TrackedConfig tc = ConfigManager.TrackedConfig.createForRegistration(
         groupPrefix, ConfigManager.ConfigType.SERVER, config, false, ConfigManager.TrackedConfig.NO_ACTION
      );
      this.findAndLoad(config.key, tc, ConfigUtil.LOCAL_DIR::resolve);
      return config;
   }

   public void save(String key) {
      ConfigManager.TrackedConfig tc = this.trackedConfigs.get(key);
      if (tc == null) {
         throw new IllegalArgumentException("Unknown tracked config: " + key);
      }

      try {
         SNBT.tryWrite(tc.loadedFrom, (CompoundTag)Util.make(new SNBTCompoundTag(), tc.config::write));
         FTBLibrary.LOGGER.debug("saved config name={} path={}", key, tc.loadedFrom);
      } catch (IOException e) {
         FTBLibrary.LOGGER.error("failed to save config {}: {}", tc, e.getMessage());
      }
   }

   public void syncFromServer(String serverConfigName, SNBTCompoundTag tag) {
      ConfigManager.TrackedConfig tc = this.trackedConfigs.get(serverConfigName);
      if (tc != null) {
         tc.config.read(tag);
         tc.onEdited.accept(false);
         FTBLibrary.LOGGER.info("received server config settings for config: {}", serverConfigName);
      } else {
         FTBLibrary.LOGGER.error("received unknown config name {} from server!", serverConfigName);
      }
   }

   public void syncFromClient(String serverConfigName, SNBTCompoundTag tag, String playerName) {
      ConfigManager.TrackedConfig tc = this.trackedConfigs.get(serverConfigName);
      if (tc != null) {
         tc.config.read(tag);
         tc.onEdited.accept(true);
         this.save(serverConfigName);
         FTBLibrary.LOGGER.info("received client config settings from {} for config: {}", playerName, serverConfigName);
      } else {
         FTBLibrary.LOGGER.error("player {} tried to sync an unknown config {} from client!", playerName, serverConfigName);
      }
   }

   private void onServerStarting(MinecraftServer server) {
      this.pendingServer
         .forEach((key, config) -> this.findAndLoad(key, config, fileName -> server.getWorldPath(ConfigUtil.SERVER_CONFIG_DIR).resolve(fileName)));
      this.pendingServer.clear();
   }

   private void onPlayerLogin(ServerPlayer serverPlayer) {
      this.trackedConfigs.forEach((name, tc) -> {
         if (tc.synced) {
            NetworkHelper.sendTo(serverPlayer, SyncConfigFromServerPacket.create(tc.config));
         }
      });
   }

   void findAndLoad(String key, ConfigManager.TrackedConfig protoTc, Function<String, Path> overridePathSupplier) {
      String fileName = key + ".snbt";
      Path primaryPath = ConfigUtil.CONFIG_DIR.resolve(fileName);
      Path overridePath = overridePathSupplier.apply(fileName);
      if (!Files.exists(primaryPath) && Files.exists(overridePath)) {
         try {
            Files.move(overridePath, primaryPath);
            FTBLibrary.LOGGER.info("config migration: moved {} to {}", overridePath, primaryPath);
         } catch (IOException e) {
            FTBLibrary.LOGGER.error("can't move {} to {}: {}", overridePath, primaryPath, e.getMessage());
         }
      } else {
         checkForIdenticalConfigFiles(key, primaryPath, overridePath);
      }

      if (Files.exists(overridePath)) {
         protoTc.config.load(overridePath);
         this.track(key, protoTc.promoteToFull(overridePath));
      } else {
         protoTc.config.load(primaryPath);
         this.track(key, protoTc.promoteToFull(primaryPath));
      }
   }

   private static void checkForIdenticalConfigFiles(String key, Path primaryPath, Path overridePath) {
      try {
         if (Files.exists(primaryPath) && Files.exists(overridePath) && Files.mismatch(primaryPath, overridePath) == -1L) {
            FTBLibrary.LOGGER.info("{} and {} are identical; deleting {}", primaryPath, overridePath, overridePath);
            Files.delete(overridePath);
         }
      } catch (IOException e) {
         FTBLibrary.LOGGER.error("Caught exception while examining configs for {}: {}", key, e.getMessage());
      }
   }

   void track(String key, ConfigManager.TrackedConfig trackedConfig) {
      this.trackedConfigs.put(key, trackedConfig);
      FTBLibrary.LOGGER.debug("tracking config {}, loaded from {}", key, trackedConfig.loadedFrom);
   }

   public void editedOnClient(String key) {
      ConfigManager.TrackedConfig tc = this.trackedConfigs.get(key);
      if (tc != null) {
         tc.onEdited.accept(false);
      }
   }

   public Optional<ConfigGroup> createConfigGroup(String configName) {
      ConfigManager.TrackedConfig tc = this.trackedConfigs.get(configName);
      return tc != null && tc.configType != ConfigManager.ConfigType.STARTUP
         ? Optional.of(ConfigUtil.makeConfigEditGroup(tc.config, tc.groupPrefix, tc.configType == ConfigManager.ConfigType.SERVER))
         : Optional.empty();
   }

   enum ConfigType {
      SERVER,
      CLIENT,
      STARTUP;
   }

   record TrackedConfig(Path loadedFrom, ConfigManager.ConfigType configType, SNBTConfig config, boolean synced, BooleanConsumer onEdited, String groupPrefix) {
      static final BooleanConsumer NO_ACTION = isServer -> {};

      ConfigManager.TrackedConfig clientMirrorOfServerConfig() {
         return new ConfigManager.TrackedConfig(null, ConfigManager.ConfigType.SERVER, this.config, false, this.onEdited, this.groupPrefix);
      }

      static ConfigManager.TrackedConfig createForRegistration(
         String groupPrefix, ConfigManager.ConfigType configType, SNBTConfig config, boolean sync, BooleanConsumer onChanged
      ) {
         return new ConfigManager.TrackedConfig(null, configType, config, sync, onChanged, groupPrefix);
      }

      ConfigManager.TrackedConfig promoteToFull(Path path) {
         return new ConfigManager.TrackedConfig(path, this.configType, this.config, this.synced, this.onEdited, this.groupPrefix);
      }
   }
}
