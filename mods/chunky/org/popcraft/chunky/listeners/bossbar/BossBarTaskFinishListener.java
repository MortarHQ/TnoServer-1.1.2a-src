package org.popcraft.chunky.listeners.bossbar;

import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import org.popcraft.chunky.GenerationTask;
import org.popcraft.chunky.event.task.GenerationTaskFinishEvent;
import org.popcraft.chunky.platform.World;

public class BossBarTaskFinishListener implements Consumer<GenerationTaskFinishEvent> {
   private final Map<ResourceLocation, ServerBossEvent> bossBars;

   public BossBarTaskFinishListener(Map<ResourceLocation, ServerBossEvent> bossBars) {
      this.bossBars = bossBars;
   }

   public void accept(GenerationTaskFinishEvent event) {
      GenerationTask task = event.generationTask();
      World world = task.getSelection().world();
      ResourceLocation worldIdentifier = ResourceLocation.tryParse(world.getKey());
      if (worldIdentifier != null) {
         ServerBossEvent bossBar = this.bossBars.get(worldIdentifier);
         if (bossBar != null) {
            bossBar.removeAllPlayers();
            this.bossBars.remove(worldIdentifier);
         }
      }
   }
}
