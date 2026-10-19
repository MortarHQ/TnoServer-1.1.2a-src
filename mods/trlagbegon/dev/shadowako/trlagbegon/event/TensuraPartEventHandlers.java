package dev.shadowako.trlagbegon.event;

import dev.shadowako.trlagbegon.part.TensuraPartChunkIndex;
import io.github.manasmods.tensura.entity.template.TensuraPartEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "trlagbegon")
public final class TensuraPartEventHandlers {
   private TensuraPartEventHandlers() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onJoin(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof TensuraPartEntity part) {
         TensuraPartChunkIndex.onPartAdd(event.getLevel(), part);
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onLeave(EntityLeaveLevelEvent event) {
      if (event.getEntity() instanceof TensuraPartEntity part) {
         TensuraPartChunkIndex.onPartRemove(event.getLevel(), part);
      }
   }

   @SubscribeEvent
   public static void onLevelUnload(Unload event) {
      if (event.getLevel() instanceof Level level) {
         TensuraPartChunkIndex.clearLevel(level);
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onPartEntityTickPost(Post event) {
      if (event.getEntity() instanceof TensuraPartEntity part) {
         TensuraPartChunkIndex.onPartMaybeMoved(event.getEntity().level(), part);
      }
   }
}
