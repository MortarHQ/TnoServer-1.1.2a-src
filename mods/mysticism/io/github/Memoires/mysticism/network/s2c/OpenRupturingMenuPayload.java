package io.github.Memoires.mysticism.network.s2c;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record OpenRupturingMenuPayload(int entityId, ResourceLocation skill, List<Integer> targetIds) implements CustomPacketPayload {
   public static final Type<OpenRupturingMenuPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "open_rupturing_menu"));
   public static final StreamCodec<FriendlyByteBuf, OpenRupturingMenuPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenRupturingMenuPayload::encode, OpenRupturingMenuPayload::new
   );

   public OpenRupturingMenuPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readResourceLocation(), buf.readList(FriendlyByteBuf::readVarInt));
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeResourceLocation(this.skill);
      buf.writeCollection(this.targetIds, FriendlyByteBuf::writeVarInt);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientAccess.handleOpenRupturingMenuPayload(this));
      }
   }

   @NotNull
   public Type<OpenRupturingMenuPayload> type() {
      return TYPE;
   }
}
