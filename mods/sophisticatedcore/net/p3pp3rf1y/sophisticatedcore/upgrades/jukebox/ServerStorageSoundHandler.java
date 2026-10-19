package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;

public class ServerStorageSoundHandler {
   private static final int KEEP_ALIVE_CHECK_INTERVAL = 10;
   private static final Map<ResourceKey<Level>, Long> lastWorldCheck = new HashMap<>();
   private static final Map<ResourceKey<Level>, Map<UUID, ServerStorageSoundHandler.SoundInfo>> worldStorageSoundInfos = new HashMap<>();

   private ServerStorageSoundHandler() {
   }

   public static void tick(Post event) {
      if (event.getLevel() instanceof ServerLevel serverLevel) {
         ResourceKey var3 = serverLevel.dimension();
         if (worldStorageSoundInfos.containsKey(var3)) {
            removeFinished(serverLevel, var3);
            if (lastWorldCheck.computeIfAbsent(var3, key -> serverLevel.getGameTime()) <= serverLevel.getGameTime() - 10L) {
               lastWorldCheck.put(var3, serverLevel.getGameTime());
               worldStorageSoundInfos.get(var3).entrySet().removeIf(entry -> {
                  if (entry.getValue().getLastKeepAliveTime() < serverLevel.getGameTime() - 10L) {
                     sendStopMessage(serverLevel, entry.getValue().getLastPosition(), entry.getKey());
                     return true;
                  } else {
                     return false;
                  }
               });
            }
         }
      }
   }

   private static void removeFinished(ServerLevel serverLevel, ResourceKey<Level> dim) {
      List<UUID> storageIdsToRemove = new ArrayList<>();
      worldStorageSoundInfos.get(dim).forEach((storageId, soundInfo) -> {
         if (soundInfo.getFinishTime() <= serverLevel.getGameTime()) {
            storageIdsToRemove.add(storageId);
         }
      });
      storageIdsToRemove.forEach(storageId -> removeSoundInfo(serverLevel, storageId, true));
   }

   public static void updateKeepAlive(UUID storageUuid, Level level, Vec3 position, Runnable onNoLongerRunning) {
      ResourceKey<Level> dim = level.dimension();
      if (worldStorageSoundInfos.containsKey(dim) && worldStorageSoundInfos.get(dim).containsKey(storageUuid)) {
         if (worldStorageSoundInfos.get(dim).containsKey(storageUuid)) {
            worldStorageSoundInfos.get(dim).get(storageUuid).update(level.getGameTime(), position);
         }
      } else {
         onNoLongerRunning.run();
      }
   }

   public static void startPlayingDisc(ServerLevel serverLevel, BlockPos position, UUID storageUuid, Holder<JukeboxSong> song, Runnable onFinishedHandler) {
      Vec3 pos = Vec3.atCenterOf(position);
      PacketDistributor.sendToPlayersNear(
         serverLevel, null, pos.x, pos.y, pos.z, 128.0, new PlayDiscPayload(storageUuid, song, position), new CustomPacketPayload[0]
      );
      putSoundInfo(serverLevel, storageUuid, onFinishedHandler, pos, serverLevel.getGameTime() + ((JukeboxSong)song.value()).lengthInTicks());
   }

   public static void startPlayingDisc(ServerLevel serverLevel, Vec3 position, UUID storageUuid, int entityId, Holder<JukeboxSong> song, Runnable onStopHandler) {
      PacketDistributor.sendToPlayersNear(
         serverLevel, null, position.x(), position.y(), position.z(), 128.0, new PlayDiscPayload(storageUuid, song, entityId), new CustomPacketPayload[0]
      );
      putSoundInfo(serverLevel, storageUuid, onStopHandler, position, serverLevel.getGameTime() + ((JukeboxSong)song.value()).lengthInTicks());
   }

   public static void putSoundInfo(ServerLevel serverLevel, UUID storageUuid, Runnable onFinishedHandler, Vec3 pos, long finishTime) {
      worldStorageSoundInfos.computeIfAbsent(serverLevel.dimension(), dim -> new HashMap<>())
         .put(storageUuid, new ServerStorageSoundHandler.SoundInfo(onFinishedHandler, serverLevel.getGameTime(), pos, finishTime));
   }

   public static void stopPlayingDisc(Level level, Vec3 position, UUID storageUuid) {
      removeSoundInfo(level, storageUuid, false);
      sendStopMessage(level, position, storageUuid);
   }

   private static void removeSoundInfo(Level level, UUID storageUuid, boolean finished) {
      ResourceKey<Level> dim = level.dimension();
      if (worldStorageSoundInfos.containsKey(dim) && worldStorageSoundInfos.get(dim).containsKey(storageUuid)) {
         ServerStorageSoundHandler.SoundInfo soundInfo = worldStorageSoundInfos.get(dim).remove(storageUuid);
         if (finished) {
            soundInfo.runOnFinished();
         }
      }
   }

   private static void sendStopMessage(Level level, Vec3 position, UUID storageUuid) {
      if (level instanceof ServerLevel serverLevel) {
         PacketDistributor.sendToPlayersNear(
            serverLevel, null, position.x(), position.y(), position.z(), 128.0, new StopDiscPlaybackPayload(storageUuid), new CustomPacketPayload[0]
         );
      }
   }

   public static void onWorldUnload(Unload evt) {
      if (evt.getLevel() instanceof ServerLevel serverLevel) {
         worldStorageSoundInfos.remove(serverLevel.dimension());
         lastWorldCheck.remove(serverLevel.dimension());
      }
   }

   private static class SoundInfo {
      private final WeakReference<Runnable> onFinishedHandler;
      private long lastKeepAliveTime;
      private Vec3 lastPosition;
      private final long finishTime;

      private SoundInfo(Runnable onFinishedHandler, long lastKeepAliveTime, Vec3 lastPosition, long finishTime) {
         this.onFinishedHandler = new WeakReference<>(onFinishedHandler);
         this.lastKeepAliveTime = lastKeepAliveTime;
         this.lastPosition = lastPosition;
         this.finishTime = finishTime;
      }

      public long getLastKeepAliveTime() {
         return this.lastKeepAliveTime;
      }

      public Vec3 getLastPosition() {
         return this.lastPosition;
      }

      public long getFinishTime() {
         return this.finishTime;
      }

      public void update(long gameTime, Vec3 position) {
         this.lastKeepAliveTime = gameTime;
         this.lastPosition = position;
      }

      public void runOnFinished() {
         Runnable handler = this.onFinishedHandler.get();
         if (handler != null) {
            handler.run();
         }
      }
   }
}
