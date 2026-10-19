package com.faboslav.variantsandventures.common.network.packet;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.api.ZombieApi;
import com.faboslav.variantsandventures.common.network.MessageHandler;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;

public record SyncZombieIsShakingPacketFromServer(int zombieId, boolean isShaking) implements Packet<SyncZombieIsShakingPacketFromServer> {
   public static final ResourceLocation ID = VariantsAndVentures.makeID("sync_horse_owner_uuid_from_server");
   public static final ClientboundPacketType<SyncZombieIsShakingPacketFromServer> TYPE = new SyncZombieIsShakingPacketFromServer.Handler();

   public static void sendToClient(Entity entity, int zombieId, boolean isShaking) {
      MessageHandler.DEFAULT_CHANNEL.sendToPlayersInLevel(new SyncZombieIsShakingPacketFromServer(zombieId, isShaking), entity.level());
   }

   public PacketType<SyncZombieIsShakingPacketFromServer> type() {
      return TYPE;
   }

   private static final class Handler implements ClientboundPacketType<SyncZombieIsShakingPacketFromServer> {
      public void encode(SyncZombieIsShakingPacketFromServer message, RegistryFriendlyByteBuf buffer) {
         buffer.writeVarInt(message.zombieId());
         buffer.writeBoolean(message.isShaking());
      }

      public SyncZombieIsShakingPacketFromServer decode(RegistryFriendlyByteBuf buffer) {
         return new SyncZombieIsShakingPacketFromServer(buffer.readVarInt(), buffer.readBoolean());
      }

      public Runnable handle(SyncZombieIsShakingPacketFromServer packet) {
         return () -> {
            if (Minecraft.getInstance().level.getEntity(packet.zombieId()) instanceof Zombie zombie) {
               ((ZombieApi)zombie).variantsandventures$setFreezeConverting(packet.isShaking());
            }
         };
      }

      public ResourceLocation id() {
         return SyncZombieIsShakingPacketFromServer.ID;
      }
   }
}
