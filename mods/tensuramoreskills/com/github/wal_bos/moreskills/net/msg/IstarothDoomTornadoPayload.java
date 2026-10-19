package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.IstarothClientDoomTornado;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record IstarothDoomTornadoPayload(UUID owner, double x, double y, double z, int ticks, float radius, boolean stop) implements CustomPacketPayload {
   public static final Type<IstarothDoomTornadoPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "istaroth_doom_tornado"));
   public static final StreamCodec<FriendlyByteBuf, IstarothDoomTornadoPayload> STREAM_CODEC = CustomPacketPayload.codec(
      IstarothDoomTornadoPayload::encode, IstarothDoomTornadoPayload::new
   );

   public IstarothDoomTornadoPayload(FriendlyByteBuf buf) {
      this(buf.readUUID(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readFloat(), buf.readBoolean());
   }

   public static IstarothDoomTornadoPayload start(UUID owner, Vec3 center, int ticks, float radius) {
      return new IstarothDoomTornadoPayload(owner, center.x, center.y, center.z, ticks, radius, false);
   }

   public static IstarothDoomTornadoPayload stop(UUID owner) {
      return new IstarothDoomTornadoPayload(owner, 0.0, 0.0, 0.0, 0, 0.0F, true);
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeUUID(this.owner);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeInt(this.ticks);
      buf.writeFloat(this.radius);
      buf.writeBoolean(this.stop);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> {
            if (this.stop) {
               IstarothClientDoomTornado.stop(this.owner);
            } else {
               IstarothClientDoomTornado.start(this.owner, new Vec3(this.x, this.y, this.z), this.ticks, this.radius);
            }
         });
      }
   }

   @NotNull
   public Type<IstarothDoomTornadoPayload> type() {
      return TYPE;
   }
}
