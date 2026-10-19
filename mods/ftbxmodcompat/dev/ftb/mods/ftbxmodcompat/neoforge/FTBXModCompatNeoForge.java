package dev.ftb.mods.ftbxmodcompat.neoforge;

import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import dev.ftb.mods.ftbxmodcompat.neoforge.ftbchunks.waystones.WaystonesCompat;
import dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.gamestages.GameStagesEventHandlerQuests;
import dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.guideme.GuideMECompat;
import dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.patchouli.PatchouliCompat;
import net.neoforged.fml.common.Mod;

@Mod("ftbxmodcompat")
public class FTBXModCompatNeoForge {
   public FTBXModCompatNeoForge() {
      FTBXModCompat.init();
      if (FTBXModCompat.isFTBQuestsLoaded && FTBXModCompat.isGameStagesLoaded && !FTBXModCompat.isKubeJSLoaded) {
         GameStagesEventHandlerQuests.register();
      }

      if (FTBXModCompat.isGuideMeLoaded) {
         GuideMECompat.init();
      }

      if (FTBXModCompat.isPatchouliLoaded) {
         PatchouliCompat.init();
      }

      if (FTBXModCompat.isFTBChunksLoaded && FTBXModCompat.isWaystonesLoaded) {
         WaystonesCompat.init();
      }
   }
}
