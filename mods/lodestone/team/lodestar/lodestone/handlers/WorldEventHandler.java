package team.lodestar.lodestone.handlers;

import java.util.Iterator;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;
import team.lodestar.lodestone.attachment.WorldEventAttachment;
import team.lodestar.lodestone.events.types.worldevent.WorldEventCreationEvent;
import team.lodestar.lodestone.events.types.worldevent.WorldEventDiscardEvent;
import team.lodestar.lodestone.events.types.worldevent.WorldEventTickEvent;
import team.lodestar.lodestone.network.worldevent.UpdateWorldEventPayload;
import team.lodestar.lodestone.registry.common.LodestoneAttachmentTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;

public class WorldEventHandler {
   public static <T extends WorldEventInstance> T addWorldEvent(Level level, T instance) {
      return addWorldEvent(level, true, instance);
   }

   public static <T extends WorldEventInstance> T addWorldEvent(Level level, boolean shouldStart, T instance) {
      NeoForge.EVENT_BUS.post(new WorldEventCreationEvent(instance, level));
      WorldEventAttachment worldData = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
      worldData.inboundWorldEvents.add(instance);
      if (shouldStart) {
         instance.start(level);
      }

      instance.sync(level);
      return instance;
   }

   public static void playerJoin(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof Player player && player.level() instanceof ServerLevel level) {
         WorldEventAttachment worldData = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
         if (player instanceof ServerPlayer serverPlayer) {
            for (WorldEventInstance instance : worldData.activeWorldEvents) {
               if (instance.type.isClientSynced()) {
                  WorldEventInstance.sync(instance, serverPlayer);
               }
            }
         }
      }
   }

   public static void worldTick(Post event) {
      if (!event.getLevel().isClientSide) {
         tick(event.getLevel());
      }
   }

   public static void tick(Level level) {
      WorldEventAttachment c = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
      c.activeWorldEvents.addAll(c.inboundWorldEvents);
      c.inboundWorldEvents.clear();
      Iterator<WorldEventInstance> iterator = c.activeWorldEvents.iterator();

      while (iterator.hasNext()) {
         WorldEventInstance instance = iterator.next();
         if (instance.discarded) {
            NeoForge.EVENT_BUS.post(new WorldEventDiscardEvent(instance, level));
            iterator.remove();
         } else {
            if (!instance.isFrozen()) {
               NeoForge.EVENT_BUS.post(new WorldEventTickEvent(instance, level));
               instance.tick(level);
            }

            if (instance.dirty) {
               PacketDistributor.sendToAllPlayers(new UpdateWorldEventPayload(instance), new CustomPacketPayload[0]);
               instance.dirty = false;
            }
         }
      }
   }
}
