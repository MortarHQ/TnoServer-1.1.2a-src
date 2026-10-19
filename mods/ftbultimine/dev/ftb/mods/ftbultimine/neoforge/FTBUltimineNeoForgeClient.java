package dev.ftb.mods.ftbultimine.neoforge;

import dev.ftb.mods.ftbultimine.client.FTBUltimineClient;
import dev.ftb.mods.ftbultimine.event.LevelRenderLastEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = "ftbultimine", dist = Dist.CLIENT)
public class FTBUltimineNeoForgeClient {
   public FTBUltimineNeoForgeClient() {
      new FTBUltimineClient();
      NeoForge.EVENT_BUS.addListener(event -> {
         if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
            ((LevelRenderLastEvent)LevelRenderLastEvent.EVENT.invoker()).onRenderLast(event.getPoseStack());
         }
      });
   }
}
