package com.finderfeed.fdlib.systems.hud.bossbars;

import com.finderfeed.fdlib.systems.hud.bossbars.packets.AddPlayerToBossBarPacket;
import com.finderfeed.fdlib.systems.hud.bossbars.packets.BossBarEventPacket;
import com.finderfeed.fdlib.systems.hud.bossbars.packets.RemovePlayerFromBossBarPacket;
import com.finderfeed.fdlib.systems.hud.bossbars.packets.SetBossBarProgressPacket;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredHolder;

public class FDServerBossBar {
   private DeferredHolder<FDBossBarFactory<?>, ?> bossBarHolder;
   private UUID uuid;
   private Entity entity;
   private Set<ServerPlayer> players = new HashSet<>();
   private float percentage = 0.0F;

   public FDServerBossBar(DeferredHolder<FDBossBarFactory<?>, ?> holder, Entity entity) {
      this.uuid = entity.getUUID();
      this.entity = entity;
      this.bossBarHolder = holder;
   }

   public FDServerBossBar(DeferredHolder<FDBossBarFactory<?>, ?> holder) {
      this.uuid = UUID.randomUUID();
      this.entity = null;
      this.bossBarHolder = holder;
   }

   public void setPercentage(float percentage) {
      this.percentage = percentage;

      for (ServerPlayer player : this.players) {
         PacketDistributor.sendToPlayer(player, new SetBossBarProgressPacket(this, percentage), new CustomPacketPayload[0]);
      }
   }

   public void addPlayer(ServerPlayer player) {
      this.players.add(player);
      PacketDistributor.sendToPlayer(player, new AddPlayerToBossBarPacket(this), new CustomPacketPayload[0]);
   }

   public void removePlayer(ServerPlayer player) {
      this.players.remove(player);
      PacketDistributor.sendToPlayer(player, new RemovePlayerFromBossBarPacket(this), new CustomPacketPayload[0]);
   }

   public void broadcastEvent(int eventId, int data) {
      for (ServerPlayer player : this.players) {
         PacketDistributor.sendToPlayer(player, new BossBarEventPacket(this, eventId, data), new CustomPacketPayload[0]);
      }
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public float getPercentage() {
      return this.percentage;
   }

   public DeferredHolder<FDBossBarFactory<?>, ?> getBossBarHolder() {
      return this.bossBarHolder;
   }
}
