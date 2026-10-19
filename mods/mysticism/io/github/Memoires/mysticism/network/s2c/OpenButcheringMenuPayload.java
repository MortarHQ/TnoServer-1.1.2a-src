package io.github.Memoires.mysticism.network.s2c;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record OpenButcheringMenuPayload(int entityId, ResourceLocation skill, int targetId) implements CustomPacketPayload {
   public static final Type<OpenButcheringMenuPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "open_butchering_menu"));
   public static final StreamCodec<FriendlyByteBuf, OpenButcheringMenuPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenButcheringMenuPayload::encode, OpenButcheringMenuPayload::new
   );

   public OpenButcheringMenuPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readResourceLocation(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeResourceLocation(this.skill);
      buf.writeInt(this.targetId);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientAccess.handleOpenButcheringMenuPayload(this));
      }
   }

   public Type<OpenButcheringMenuPayload> type() {
      return TYPE;
   }
}
