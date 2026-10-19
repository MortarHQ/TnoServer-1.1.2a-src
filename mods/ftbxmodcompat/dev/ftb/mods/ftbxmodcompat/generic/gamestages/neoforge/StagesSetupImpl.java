package dev.ftb.mods.ftbxmodcompat.generic.gamestages.neoforge;

import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;

public class StagesSetupImpl {
   public static void addKubeJSProvider() {
      StageHelper.getInstance().setProviderImpl(new KubeJSStageProvider());
   }
}
