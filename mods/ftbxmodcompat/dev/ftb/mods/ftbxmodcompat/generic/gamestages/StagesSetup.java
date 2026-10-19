package dev.ftb.mods.ftbxmodcompat.generic.gamestages;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;
import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import dev.ftb.mods.ftbxmodcompat.config.FTBXModConfig;
import dev.ftb.mods.ftbxmodcompat.generic.gamestages.neoforge.StagesSetupImpl;

public class StagesSetup {
   public static void init() {
      FTBXModConfig.StageSelector sel = (FTBXModConfig.StageSelector)FTBXModConfig.STAGE_SELECTOR.get();
      if (sel != FTBXModConfig.StageSelector.DEFAULT && !sel.isUsable()) {
         FTBXModCompat.LOGGER.error("Stages implementation {} isn't available, falling back to default", sel);
         sel = FTBXModConfig.StageSelector.DEFAULT;
      }

      switch (sel) {
         case KUBEJS:
            addKubeJSProvider();
            break;
         case GAMESTAGES:
            StageHelper.getInstance().setProviderImpl(new GameStagesStageProvider());
         case VANILLA:
         default:
            break;
         case DEFAULT:
            if (FTBXModCompat.isKubeJSLoaded) {
               addKubeJSProvider();
            }
      }

      FTBXModCompat.LOGGER.info("Chose [{}] as the active game stages implementation", StageHelper.getInstance().getProvider().getName());
   }

   @ExpectPlatform
   @Transformed
   public static void addKubeJSProvider() {
      StagesSetupImpl.addKubeJSProvider();
   }
}
