package com.finderfeed.fdlib.systems.music.music_areas;

import com.finderfeed.fdlib.systems.music.data.FDMusicData;
import com.finderfeed.fdlib.systems.music.music_areas.shapes.FDMusicAreaShape;
import com.finderfeed.fdlib.systems.music.packets.FDMusicAreaEnterPacket;
import com.finderfeed.fdlib.systems.music.packets.FDMusicEndPacket;
import com.finderfeed.fdlib.systems.music.packets.FDMusicFadeOutPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class FDMusicArea {
   private List<UUID> playersInside = new ArrayList<>();
   protected UUID uuid;
   private Vec3 position;
   private ResourceKey<Level> dimension;
   private FDMusicAreaShape shape;
   private FDMusicData musicData;
   private int autoDeletionTicker = 2400;
   private int maxDeletionTicker = this.autoDeletionTicker;
   private int playerDetectionFrequency = 5;

   public FDMusicArea(ResourceKey<Level> dimension, Vec3 position, FDMusicAreaShape shape, FDMusicData musicData) {
      this.dimension = dimension;
      this.position = position;
      this.shape = shape;
      this.musicData = musicData;
   }

   public void tick(ServerLevel level) {
      if (level.getGameTime() % this.playerDetectionFrequency == 0L) {
         List<ServerPlayer> playersInside = this.shape.getPlayersInside(level, this.position);
         List<UUID> uuidsInside = playersInside.stream().<UUID>map(Entity::getUUID).toList();

         for (ServerPlayer serverPlayer : playersInside) {
            UUID uuid = serverPlayer.getUUID();
            if (!this.playersInside.contains(uuid)) {
               PacketDistributor.sendToPlayer(serverPlayer, new FDMusicAreaEnterPacket(this.musicData, 20), new CustomPacketPayload[0]);
               this.playersInside.add(uuid);
            }
         }

         List<UUID> playersThatExitedTheArea = new ArrayList<>(this.playersInside);
         playersThatExitedTheArea.removeAll(uuidsInside);
         this.playersInside.removeAll(playersThatExitedTheArea);

         for (UUID uuid : playersThatExitedTheArea) {
            ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayer(uuid);
            if (serverPlayer != null) {
               PacketDistributor.sendToPlayer(serverPlayer, new FDMusicFadeOutPacket(this.musicData.getMusicSourceUUID(), 20), new CustomPacketPayload[0]);
            }
         }
      }
   }

   public void onRemoval(MinecraftServer server, int fadeOutTime) {
      for (UUID player : this.playersInside) {
         ServerPlayer actualPlayer = server.getPlayerList().getPlayer(player);
         if (actualPlayer != null) {
            PacketDistributor.sendToPlayer(actualPlayer, new FDMusicEndPacket(this.musicData.getMusicSourceUUID(), fadeOutTime), new CustomPacketPayload[0]);
         }
      }
   }

   protected void tickDeletionTicker(boolean ignorePlayersInside) {
      if (!this.playersInside.isEmpty() && !ignorePlayersInside) {
         this.autoDeletionTicker = this.maxDeletionTicker;
      } else {
         this.autoDeletionTicker--;
      }
   }

   public void setAutoDeletionTicker(int autoDeletionTicker) {
      this.autoDeletionTicker = autoDeletionTicker;
      this.maxDeletionTicker = autoDeletionTicker;
   }

   public boolean shouldBeDeleted() {
      return this.autoDeletionTicker < 0;
   }

   public void setPlayerDetectionFrequency(int playerDetectionFrequency) {
      this.playerDetectionFrequency = playerDetectionFrequency;
   }

   public UUID getUUID() {
      return (R)this.uuid;
   }

   public FDMusicAreaShape getShape() {
      return this.shape;
   }

   public ResourceKey<Level> getDimension() {
      return this.dimension;
   }

   public Vec3 getPosition() {
      return this.position;
   }

   public FDMusicData getMusicData() {
      return this.musicData;
   }

   public void setMusicData(FDMusicData musicData) {
      this.musicData = musicData;
   }
}
