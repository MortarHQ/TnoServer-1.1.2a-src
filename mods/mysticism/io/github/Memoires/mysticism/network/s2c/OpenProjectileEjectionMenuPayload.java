package io.github.Memoires.mysticism.network.s2c;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record OpenProjectileEjectionMenuPayload(int containerId, int size, int stackSize, int entityId, ResourceLocation skill) implements CustomPacketPayload {
   public static final Type<OpenProjectileEjectionMenuPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("mysticism", "open_projectile_ejection_menu")
   );
   public static final StreamCodec<FriendlyByteBuf, OpenProjectileEjectionMenuPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenProjectileEjectionMenuPayload::encode, OpenProjectileEjectionMenuPayload::new
   );

   public OpenProjectileEjectionMenuPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readResourceLocation());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.containerId);
      buf.writeInt(this.size);
      buf.writeInt(this.stackSize);
      buf.writeInt(this.entityId);
      buf.writeResourceLocation(this.skill);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientAccess.handleOpenProjectileEjectionMenuPayload(this));
      }
   }

   public Type<OpenProjectileEjectionMenuPayload> type() {
      return TYPE;
   }
}
