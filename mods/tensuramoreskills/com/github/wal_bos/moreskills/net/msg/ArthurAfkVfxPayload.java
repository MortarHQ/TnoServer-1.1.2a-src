package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.arthur.ArthurAfkGoldenOrbsVfxClient;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ArthurAfkVfxPayload(int casterId, boolean active, long seed) implements CustomPacketPayload {
   public static final Type<ArthurAfkVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "arthur_afk_vfx"));
   public static final StreamCodec<FriendlyByteBuf, ArthurAfkVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      ArthurAfkVfxPayload::encode, ArthurAfkVfxPayload::new
   );

   public ArthurAfkVfxPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readBoolean(), buf.readLong());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.casterId);
      buf.writeBoolean(this.active);
      buf.writeLong(this.seed);
   }

   public static void send(ServerPlayer source, boolean active) {
      if (source.level() instanceof ServerLevel level) {
         Vec3 var11 = source.position();
         ArthurAfkVfxPayload payload = new ArthurAfkVfxPayload(source.getId(), active, level.random.nextLong());
         double range = 192.0;
         double rangeSqr = range * range;

         for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(var11) <= rangeSqr) {
               NetworkManager.sendToPlayer(player, payload);
            }
         }
      }
   }

   public static void handle(ArthurAfkVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ArthurAfkGoldenOrbsVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
