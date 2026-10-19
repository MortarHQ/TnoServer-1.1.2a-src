package net.jadenxgamer.elysium_api.impl.networking.to_client;

import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.jadenxgamer.elysium_api.api.client.screen_flash.ScreenFlash;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ScreenFlashPayload(int fadeIn, int hold, int fadeOut, int color, boolean firstPersonOnly, boolean force) implements CustomPacketPayload {
   public static final Type<ScreenFlashPayload> TYPE = new Type(ElysiumAPI.elysiumPath("screen_flash"));
   public static final StreamCodec<FriendlyByteBuf, ScreenFlashPayload> CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      ScreenFlashPayload::fadeIn,
      ByteBufCodecs.INT,
      ScreenFlashPayload::hold,
      ByteBufCodecs.INT,
      ScreenFlashPayload::fadeOut,
      ByteBufCodecs.INT,
      ScreenFlashPayload::color,
      ByteBufCodecs.BOOL,
      ScreenFlashPayload::firstPersonOnly,
      ByteBufCodecs.BOOL,
      ScreenFlashPayload::force,
      ScreenFlashPayload::new
   );

   @NotNull
   public Type<ScreenFlashPayload> type() {
      return TYPE;
   }

   public void handleDataOnClient(IPayloadContext ignored) {
      ElysiumAPI.LOGGER.debug("Received ScreenFlashPayload: {}", this);
      ScreenFlash.triggerScreenFlash(this.fadeIn, this.hold, this.fadeOut, this.color, this.firstPersonOnly, this.force);
   }
}
