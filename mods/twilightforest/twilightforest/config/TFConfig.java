package twilightforest.config;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.TranslatableEnum;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.network.SyncUncraftingTableConfigPacket;
import twilightforest.util.PlayerHelper;

public class TFConfig {
   public static final String CONFIG_ID = "config.twilightforest.";
   @Nullable
   private static ResourceLocation portalLockingAdvancement;
   private static final List<Holder<Biome>> VALID_AURORA_BIOMES = new ArrayList<>();
   public static final List<GameProfile> GAME_PROFILES = new ArrayList<>();
   public static boolean silentCicadas = false;
   public static boolean silentCicadasOnHead = false;
   public static boolean firstPersonEffects = true;
   public static boolean rotateTrophyHeadsGui = true;
   public static boolean disableOptifineNagScreen = false;
   public static boolean disableLockedBiomeToasts = false;
   public static boolean showQuestRamCrosshairIndicator = true;
   public static boolean showFortificationShieldIndicator = true;
   public static boolean showFortificationShieldIndicatorInCreative = false;
   private static int clientCloudBlockPrecipitationDistance = 32;
   public static boolean prettifyOreMeterGui = true;
   public static boolean spawnCharmAnimationAsTotem = false;
   public static boolean manualTravellersWingsGradualGlideDefault = true;
   public static boolean firstPersonGloveOverlay = true;
   public static int itemDisplayXOffs = 4;
   public static int itemDisplayYOffs = 4;
   public static double itemDisplayScale = 1.0;
   public static boolean clock24HourFormat = use24HourTimeDefault();
   public static boolean casketUUIDLocking = false;
   public static boolean disableSkullCandles = false;
   public static boolean defaultItemEnchants = true;
   public static boolean bossDropChests = true;
   public static TFConfig.MultiplayerFightAdjuster multiplayerFightAdjuster = TFConfig.MultiplayerFightAdjuster.NONE;
   public static int commonCloudBlockPrecipitationDistance = 32;
   public static boolean newPlayersSpawnInTF = false;
   public static boolean portalForNewPlayerSpawn = true;
   public static String originDimension = Level.OVERWORLD.location().toString();
   public static boolean allowPortalsInOtherDimensions = false;
   public static int portalCreationPermission = 0;
   public static boolean disablePortalCreation = false;
   public static boolean checkPortalPlacement = true;
   public static boolean destructivePortalLightning = true;
   public static boolean shouldReturnPortalBeUsable = true;
   public static int maxPortalSize = 64;
   public static double uncraftingXpCostMultiplier = 1.0;
   public static double repairingXpCostMultiplier = 1.0;
   public static boolean allowShapelessUncrafting = false;
   public static boolean disableIngredientSwitching = false;
   public static List<? extends String> disableUncraftingRecipes = new ArrayList<>();
   public static boolean reverseRecipeBlacklist = false;
   public static List<? extends String> blacklistedUncraftingModIds = new ArrayList<>();
   public static boolean flipUncraftingModIdList = false;
   public static boolean disableUncraftingOnly = false;
   public static boolean disableEntireTable = false;
   public static boolean disableTimeCore = false;
   public static int timeCoreRange = 16;
   public static boolean disableTransformationCore = false;
   public static int transformationCoreRange = 16;
   public static boolean disableMiningCore = false;
   public static int miningCoreRange = 16;
   public static boolean disableSortingCore = false;
   public static int sortingCoreRange = 16;
   public static boolean parryNonTwilightAttacks = false;
   public static int shieldParryTicks = 40;

   public static int getClientCloudBlockPrecipitationDistance() {
      return clientCloudBlockPrecipitationDistance == -1 ? commonCloudBlockPrecipitationDistance : clientCloudBlockPrecipitationDistance;
   }

   @Nullable
   public static ResourceLocation getPortalLockingAdvancement(Player player) {
      if (portalLockingAdvancement == null && !((String)ConfigSetup.COMMON_CONFIG.PORTAL.portalAdvancementLock.get()).isEmpty()) {
         ResourceLocation lock = ResourceLocation.tryParse((String)ConfigSetup.COMMON_CONFIG.PORTAL.portalAdvancementLock.get());
         if (lock != null && PlayerHelper.getAdvancement(player, lock) != null) {
            portalLockingAdvancement = ResourceLocation.tryParse((String)ConfigSetup.COMMON_CONFIG.PORTAL.portalAdvancementLock.get());
            TwilightForestMod.LOGGER.debug("Portal locking advancement reloaded. Current advancement to check for is: {}", portalLockingAdvancement);
         } else {
            TwilightForestMod.LOGGER.fatal("The portal locking advancement is not a valid advancement! Setting to null!");
            ConfigSetup.COMMON_CONFIG.PORTAL.portalAdvancementLock.set("");
         }
      }

      return portalLockingAdvancement;
   }

   public static List<Holder<Biome>> getValidAuroraBiomes(RegistryAccess access) {
      if (VALID_AURORA_BIOMES.isEmpty() && !((List)ConfigSetup.CLIENT_CONFIG.auroraBiomes.get()).isEmpty()) {
         ((List)ConfigSetup.CLIENT_CONFIG.auroraBiomes.get())
            .forEach(
               s -> {
                  Optional<Holder<Biome>> holder = Optional.ofNullable(ResourceLocation.tryParse(s))
                     .flatMap(key -> access.registryOrThrow(Registries.BIOME).getHolder(key));
                  if (holder.isEmpty()) {
                     TwilightForestMod.LOGGER.warn("Biome {} in Twilight Forest's validAuroraBiomes config option is not a valid biome. Skipping!", s);
                  } else {
                     VALID_AURORA_BIOMES.add(holder.get());
                  }
               }
            );
      }

      return VALID_AURORA_BIOMES;
   }

   protected static void rebakeCommonOptions(TFCommonConfig config) {
      casketUUIDLocking = (Boolean)config.casketUUIDLocking.get();
      disableSkullCandles = (Boolean)config.disableSkullCandles.get();
      defaultItemEnchants = (Boolean)config.defaultItemEnchants.get();
      bossDropChests = (Boolean)config.bossDropChests.get();
      commonCloudBlockPrecipitationDistance = (Integer)config.cloudBlockPrecipitationDistance.get();
      multiplayerFightAdjuster = (TFConfig.MultiplayerFightAdjuster)config.multiplayerFightAdjuster.get();
      newPlayersSpawnInTF = (Boolean)config.DIMENSION.newPlayersSpawnInTF.get();
      portalForNewPlayerSpawn = (Boolean)config.DIMENSION.portalForNewPlayerSpawn.get();
      originDimension = (String)config.PORTAL.originDimension.get();
      allowPortalsInOtherDimensions = (Boolean)config.PORTAL.allowPortalsInOtherDimensions.get();
      portalCreationPermission = (Integer)config.PORTAL.portalCreationPermission.get();
      disablePortalCreation = (Boolean)config.PORTAL.disablePortalCreation.get();
      checkPortalPlacement = (Boolean)config.PORTAL.checkPortalPlacement.get();
      destructivePortalLightning = (Boolean)config.PORTAL.destructivePortalLightning.get();
      shouldReturnPortalBeUsable = (Boolean)config.PORTAL.shouldReturnPortalBeUsable.get();
      maxPortalSize = (Integer)config.PORTAL.maxPortalSize.get();
      uncraftingXpCostMultiplier = (Double)config.UNCRAFTING_STUFFS.uncraftingXpCostMultiplier.get();
      repairingXpCostMultiplier = (Double)config.UNCRAFTING_STUFFS.repairingXpCostMultiplier.get();
      allowShapelessUncrafting = (Boolean)config.UNCRAFTING_STUFFS.allowShapelessUncrafting.get();
      disableIngredientSwitching = (Boolean)config.UNCRAFTING_STUFFS.disableIngredientSwitching.get();
      disableUncraftingOnly = (Boolean)config.UNCRAFTING_STUFFS.disableUncraftingOnly.get();
      disableEntireTable = (Boolean)config.UNCRAFTING_STUFFS.disableEntireTable.get();
      disableUncraftingRecipes = (List<? extends String>)config.UNCRAFTING_STUFFS.disableUncraftingRecipes.get();
      reverseRecipeBlacklist = (Boolean)config.UNCRAFTING_STUFFS.reverseRecipeBlacklist.get();
      blacklistedUncraftingModIds = (List<? extends String>)config.UNCRAFTING_STUFFS.blacklistedUncraftingModIds.get();
      flipUncraftingModIdList = (Boolean)config.UNCRAFTING_STUFFS.flipUncraftingModIdList.get();
      disableTimeCore = (Integer)config.MAGIC_TREES.timeRange.get() <= 0;
      timeCoreRange = (Integer)config.MAGIC_TREES.timeRange.get();
      disableTransformationCore = (Integer)config.MAGIC_TREES.transformationRange.get() <= 0;
      transformationCoreRange = (Integer)config.MAGIC_TREES.transformationRange.get();
      disableMiningCore = (Integer)config.MAGIC_TREES.miningRange.get() <= 0;
      miningCoreRange = (Integer)config.MAGIC_TREES.miningRange.get();
      disableSortingCore = (Integer)config.MAGIC_TREES.sortingRange.get() <= 0;
      sortingCoreRange = (Integer)config.MAGIC_TREES.sortingRange.get();
      parryNonTwilightAttacks = (Boolean)config.SHIELD_INTERACTIONS.parryNonTwilightAttacks.get();
      shieldParryTicks = (Integer)config.SHIELD_INTERACTIONS.shieldParryTicks.get();
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server != null && server.isDedicatedServer()) {
         PacketDistributor.sendToAllPlayers(
            new SyncUncraftingTableConfigPacket(
               uncraftingXpCostMultiplier,
               repairingXpCostMultiplier,
               allowShapelessUncrafting,
               disableIngredientSwitching,
               disableUncraftingOnly,
               disableEntireTable,
               disableUncraftingRecipes,
               reverseRecipeBlacklist,
               blacklistedUncraftingModIds,
               flipUncraftingModIdList
            ),
            new CustomPacketPayload[0]
         );
      }

      portalLockingAdvancement = null;
   }

   protected static void rebakeClientOptions(TFClientConfig config) {
      reloadGiantSkins(config);
      VALID_AURORA_BIOMES.clear();
      silentCicadas = (Boolean)config.silentCicadas.get();
      silentCicadasOnHead = (Boolean)config.silentCicadasOnHead.get();
      firstPersonEffects = (Boolean)config.firstPersonEffects.get();
      rotateTrophyHeadsGui = (Boolean)config.rotateTrophyHeadsGui.get();
      disableOptifineNagScreen = (Boolean)config.disableOptifineNagScreen.get();
      disableLockedBiomeToasts = (Boolean)config.disableLockedBiomeToasts.get();
      showFortificationShieldIndicator = (Boolean)config.showFortificationShieldIndicator.get();
      showFortificationShieldIndicatorInCreative = (Boolean)config.showFortificationShieldIndicatorInCreative.get();
      showQuestRamCrosshairIndicator = (Boolean)config.showQuestRamCrosshairIndicator.get();
      clientCloudBlockPrecipitationDistance = (Integer)config.cloudBlockPrecipitationDistance.get();
      prettifyOreMeterGui = (Boolean)config.prettifyOreMeterGui.get();
      spawnCharmAnimationAsTotem = (Boolean)config.spawnCharmAnimationAsTotem.get();
      manualTravellersWingsGradualGlideDefault = (Boolean)config.manualTravellersWingsGradualGlide.get();
      firstPersonGloveOverlay = (Boolean)config.firstPersonGloveOverlay.get();
      itemDisplayXOffs = (Integer)config.ITEM_DISPLAY.screenOffsetX.get();
      itemDisplayYOffs = (Integer)config.ITEM_DISPLAY.screenOffsetY.get();
      itemDisplayScale = (Double)config.ITEM_DISPLAY.screenScale.get();
      clock24HourFormat = (Boolean)config.ITEM_DISPLAY.twentyFourHourFormat.get();
   }

   private static void reloadGiantSkins(final TFClientConfig config) {
      if (!((List)config.giantSkinUUIDs.get()).isEmpty()) {
         (new Thread() {
            @Override
            public void run() {
               TFConfig.GAME_PROFILES.clear();
               YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
               MinecraftSessionService session = service.createMinecraftSessionService();

               for (String stringUUID : (List)config.giantSkinUUIDs.get()) {
                  try {
                     ProfileResult result = session.fetchProfile(UUID.fromString(stringUUID), false);
                     if (result != null) {
                        TFConfig.GAME_PROFILES.add(result.profile());
                     }
                  } catch (IllegalArgumentException e) {
                     TwilightForestMod.LOGGER.error("\"{}\" is not a valid UUID!", stringUUID);
                  }
               }

               super.run();
            }
         }).start();
      }
   }

   public static boolean use24HourTimeDefault() {
      try {
         String pattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
            FormatStyle.SHORT, FormatStyle.SHORT, IsoChronology.INSTANCE, Locale.getDefault()
         );
         return !pattern.contains("a");
      } catch (Throwable throwable) {
         return true;
      }
   }

   public enum MultiplayerFightAdjuster implements TranslatableEnum {
      NONE(false, false),
      MORE_LOOT(true, false),
      MORE_HEALTH(false, true),
      MORE_LOOT_AND_HEALTH(true, true);

      private final boolean moreLoot;
      private final boolean moreHealth;

      MultiplayerFightAdjuster(boolean loot, boolean health) {
         this.moreLoot = loot;
         this.moreHealth = health;
      }

      public boolean adjustsLootRolls() {
         return this.moreLoot;
      }

      public boolean adjustsHealth() {
         return this.moreHealth;
      }

      public Component getTranslatedName() {
         return Component.translatable("config.twilightforest.multiplayer_fight_adjuster." + this.name().toLowerCase(Locale.ROOT));
      }
   }
}
