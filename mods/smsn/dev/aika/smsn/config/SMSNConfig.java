package dev.aika.smsn.config;

import dev.aika.smsn.annotation.Category;
import dev.aika.smsn.annotation.LoaderSpecific;
import dev.aika.smsn.api.LoaderType;
import java.nio.file.Files;
import lombok.Generated;

public class SMSNConfig extends ModConfig {
   public boolean aetherMoaSkinsFeature;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean immersiveEngineeringSpecialRevolvers;
   public boolean ipnUpdateCheckAndUserTracking;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean petrolparkBadgeCheck;
   public boolean supplementariesCreditsCheck;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean bagusLibSupportersCheck;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean placeboTrails;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean placeboWings;
   @LoaderSpecific(LoaderType.FABRIC)
   public boolean irisUpdateCheck;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean actuallyAdditionsSpecialPeopleStuff;
   public boolean exposureGoldenCameraSkin;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean titaniumReward;
   public boolean ribbitsSupporterHat;
   public boolean xaeroMapVersionCheck;
   public boolean xaeroMapPatreonCheck;
   public boolean mruApi;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean projecteUUIDCheck;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean hexereiSupporter;
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean additionalStructuresSupporterCheck;
   public boolean tenshilibPatreon;
   @Category("qol")
   @LoaderSpecific(LoaderType.NEOFORGE)
   public boolean immersiveCavesDiscordMessage;

   public SMSNConfig() {
      this.aetherMoaSkinsFeature = SMSNConfigDefault.aetherMoaSkinsFeature;
      this.immersiveEngineeringSpecialRevolvers = SMSNConfigDefault.immersiveEngineeringSpecialRevolvers;
      this.ipnUpdateCheckAndUserTracking = SMSNConfigDefault.ipnUpdateCheckAndUserTracking;
      this.petrolparkBadgeCheck = SMSNConfigDefault.petrolparkBadgeCheck;
      this.supplementariesCreditsCheck = SMSNConfigDefault.supplementariesCreditsCheck;
      this.bagusLibSupportersCheck = SMSNConfigDefault.bagusLibSupportersCheck;
      this.placeboTrails = SMSNConfigDefault.placeboTrails;
      this.placeboWings = SMSNConfigDefault.placeboWings;
      this.irisUpdateCheck = SMSNConfigDefault.irisUpdateCheck;
      this.actuallyAdditionsSpecialPeopleStuff = SMSNConfigDefault.actuallyAdditionsSpecialPeopleStuff;
      this.exposureGoldenCameraSkin = SMSNConfigDefault.exposureGoldenCameraSkin;
      this.titaniumReward = SMSNConfigDefault.titaniumReward;
      this.ribbitsSupporterHat = SMSNConfigDefault.ribbitsSupporterHat;
      this.xaeroMapVersionCheck = SMSNConfigDefault.xaeroMapVersionCheck;
      this.xaeroMapPatreonCheck = SMSNConfigDefault.xaeroMapPatreonCheck;
      this.mruApi = SMSNConfigDefault.mruApi;
      this.projecteUUIDCheck = SMSNConfigDefault.projecteUUIDCheck;
      this.hexereiSupporter = SMSNConfigDefault.hexereiSupporter;
      this.additionalStructuresSupporterCheck = SMSNConfigDefault.additionalStructuresSupporterCheck;
      this.tenshilibPatreon = SMSNConfigDefault.tenshilibPatreon;
      this.immersiveCavesDiscordMessage = SMSNConfigDefault.immersiveCavesDiscordMessage;
   }

   public static SMSNConfig load() {
      try {
         if (!CONFIG_FILE.exists() && OLD_CONFIG_FILE.exists()) {
            Files.move(OLD_CONFIG_FILE.toPath(), CONFIG_FILE.toPath());
         }

         if (CONFIG_FILE.exists()) {
            return load(SMSNConfig.class);
         }

         SMSNConfig defaultConfig = new SMSNConfig();
         defaultConfig.save();
         return defaultConfig;
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   @Generated
   public boolean isAetherMoaSkinsFeature() {
      return this.aetherMoaSkinsFeature;
   }

   @Generated
   public boolean isImmersiveEngineeringSpecialRevolvers() {
      return this.immersiveEngineeringSpecialRevolvers;
   }

   @Generated
   public boolean isIpnUpdateCheckAndUserTracking() {
      return this.ipnUpdateCheckAndUserTracking;
   }

   @Generated
   public boolean isPetrolparkBadgeCheck() {
      return this.petrolparkBadgeCheck;
   }

   @Generated
   public boolean isSupplementariesCreditsCheck() {
      return this.supplementariesCreditsCheck;
   }

   @Generated
   public boolean isBagusLibSupportersCheck() {
      return this.bagusLibSupportersCheck;
   }

   @Generated
   public boolean isPlaceboTrails() {
      return this.placeboTrails;
   }

   @Generated
   public boolean isPlaceboWings() {
      return this.placeboWings;
   }

   @Generated
   public boolean isIrisUpdateCheck() {
      return this.irisUpdateCheck;
   }

   @Generated
   public boolean isActuallyAdditionsSpecialPeopleStuff() {
      return this.actuallyAdditionsSpecialPeopleStuff;
   }

   @Generated
   public boolean isExposureGoldenCameraSkin() {
      return this.exposureGoldenCameraSkin;
   }

   @Generated
   public boolean isTitaniumReward() {
      return this.titaniumReward;
   }

   @Generated
   public boolean isRibbitsSupporterHat() {
      return this.ribbitsSupporterHat;
   }

   @Generated
   public boolean isXaeroMapVersionCheck() {
      return this.xaeroMapVersionCheck;
   }

   @Generated
   public boolean isXaeroMapPatreonCheck() {
      return this.xaeroMapPatreonCheck;
   }

   @Generated
   public boolean isMruApi() {
      return this.mruApi;
   }

   @Generated
   public boolean isProjecteUUIDCheck() {
      return this.projecteUUIDCheck;
   }

   @Generated
   public boolean isHexereiSupporter() {
      return this.hexereiSupporter;
   }

   @Generated
   public boolean isAdditionalStructuresSupporterCheck() {
      return this.additionalStructuresSupporterCheck;
   }

   @Generated
   public boolean isTenshilibPatreon() {
      return this.tenshilibPatreon;
   }

   @Generated
   public boolean isImmersiveCavesDiscordMessage() {
      return this.immersiveCavesDiscordMessage;
   }
}
