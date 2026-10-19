package noobanidus.mods.lootr.neoforge.network.toClient;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import noobanidus.mods.lootr.neoforge.Lootr;
import noobanidus.mods.lootr.neoforge.network.ILootrNeoForgePacket;
import noobanidus.mods.lootr.neoforge.network.client.ClientHandlers;

public record PacketCloseContainer(BlockPos position) implements ILootrNeoForgePacket {
   public static final Type<PacketCloseContainer> TYPE = new Type(Lootr.rl("close_container"));
   public static final StreamCodec<FriendlyByteBuf, PacketCloseContainer> STREAM_CODEC = StreamCodec.composite(
      BlockPos.STREAM_CODEC, PacketCloseContainer::position, PacketCloseContainer::new
   );

   @Override
   public void handle(IPayloadContext context) {
      ClientHandlers.handleCloseContainer(this.position);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
