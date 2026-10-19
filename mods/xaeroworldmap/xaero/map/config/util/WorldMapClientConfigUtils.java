package xaero.map.config.util;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.profile.ConfigProfile;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.mcworld.WorldMapClientWorldData;
import xaero.map.mcworld.WorldMapClientWorldDataHelper;
import xaero.map.mods.SupportMods;

public class WorldMapClientConfigUtils {
   public static boolean isFairPlay() {
      boolean defaultValue = false;
      if (SupportMods.minimap() && SupportMods.xaeroMinimap.isFairPlay()) {
         defaultValue = true;
      }

      WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
      if (worldmapSession == null) {
         return defaultValue;
      } else {
         return !worldmapSession.getMapProcessor().fairplayMessageWasReceived()
            ? defaultValue
            : !worldmapSession.getMapProcessor().isConsideringNetherFairPlay()
               || worldmapSession.getMapProcessor().getMapWorld().getCurrentDimensionId() != Level.NETHER;
      }
   }

   public static boolean isCaveModeDisabledLegacy() {
      if (WorldMap.INSTANCE.getConfigs().getClientConfigManager().getServerSynced().isChannelPresentOnServer()) {
         return false;
      }

      if (Minecraft.getInstance().level == null) {
         return false;
      }

      WorldMapClientWorldData clientData = WorldMapClientWorldDataHelper.getCurrentWorldData();
      return !clientData.getSyncedRules().allowCaveModeOnServer && Minecraft.getInstance().level.dimension() != Level.NETHER
         || !clientData.getSyncedRules().allowNetherCaveModeOnServer && Minecraft.getInstance().level.dimension() == Level.NETHER;
   }

   public static boolean getEffectiveCaveModeAllowed() {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      if (!(Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED)) {
         return false;
      }

      if (Minecraft.getInstance().level == null) {
         return true;
      }

      ResourceLocation currentDimension = Minecraft.getInstance().level.dimension().location();
      Set<ResourceLocation> localCaveModeDimensions = (Set<ResourceLocation>)configManager.getEffective(
         WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED_DIMENSIONS
      );
      if (!localCaveModeDimensions.isEmpty() && !localCaveModeDimensions.contains(currentDimension)) {
         return false;
      }

      Set<ResourceLocation> serverCaveModeDimensions = (Set<ResourceLocation>)configManager.getServerSynced()
         .getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED_DIMENSIONS);
      return serverCaveModeDimensions != null && !serverCaveModeDimensions.isEmpty() ? serverCaveModeDimensions.contains(currentDimension) : true;
   }

   public static boolean getDebug() {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      return (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
   }

   public static void togglePrimaryOption(ConfigOption<Boolean> option) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      primaryConfigManager.getConfig().set(option, !(Boolean)primaryConfigManager.getConfig().get(option));
      WorldMap.INSTANCE.getConfigs().getPrimaryClientConfigManagerIO().save();
   }

   public static void tryTogglingCurrentProfileOption(ConfigOption<Boolean> option) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      if (configManager.getServerSynced().getEffective(option) == null) {
         ConfigProfile currentProfile = configManager.getCurrentProfile();
         currentProfile.set(option, !(Boolean)currentProfile.get(option));
         WorldMap.INSTANCE.getConfigs().getClientConfigProfileIO().save(currentProfile);
      }
   }

   public static boolean isOptionServerEnforced(ConfigOption<Boolean> option) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      return configManager.getServerSynced().getEffective(option) != null;
   }
}
