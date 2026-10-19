package de.melanx.simplebackups.network;

import de.melanx.simplebackups.client.ClientEventHandler;
import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record Pause(boolean pause) implements CustomPacketPayload {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("simplebackups", "pause");
   public static final Type<Pause> TYPE = new Type(ID);
   public static final StreamCodec<FriendlyByteBuf, Pause> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, Pause::pause, Pause::new);

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext context) {
      ClientEventHandler.setPaused(this.pause);
   }
}
