package gardensofthedead.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import gardensofthedead.GardensOfTheDead;
import gardensofthedead.client.WhistleEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record WhistleEffectPacket(BlockPos pos, ResourceKey<Level> dimension) implements CustomPacketPayload {
   public static final Type<WhistleEffectPacket> TYPE = new Type(GardensOfTheDead.id("whistle_effect"));
   public static final StreamCodec<FriendlyByteBuf, WhistleEffectPacket> CODEC = StreamCodec.composite(
      BlockPos.STREAM_CODEC, WhistleEffectPacket::pos, ResourceKey.streamCodec(Registries.DIMENSION), WhistleEffectPacket::dimension, WhistleEffectPacket::new
   );

   void apply(PacketContext context) {
      context.queue(() -> {
         if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension().equals(this.dimension)) {
            WhistleEventHandler.add(this.pos);
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
