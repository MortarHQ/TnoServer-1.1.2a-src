package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.TwilightForestMod;
import twilightforest.client.MissingAdvancementToast;

public record MissingAdvancementToastPacket(Component title, ItemStack icon) implements CustomPacketPayload {
   public static final Type<MissingAdvancementToastPacket> TYPE = new Type(TwilightForestMod.prefix("missing_advancement_toast"));
   public static final StreamCodec<RegistryFriendlyByteBuf, MissingAdvancementToastPacket> STREAM_CODEC = StreamCodec.composite(
      ComponentSerialization.STREAM_CODEC,
      MissingAdvancementToastPacket::title,
      ItemStack.STREAM_CODEC,
      MissingAdvancementToastPacket::icon,
      MissingAdvancementToastPacket::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(final MissingAdvancementToastPacket packet, IPayloadContext ctx) {
      if (ctx.flow().isClientbound()) {
         ctx.enqueueWork(new Runnable() {
            @Override
            public void run() {
               Minecraft.getInstance().getToasts().addToast(new MissingAdvancementToast(packet.title(), packet.icon()));
            }
         });
      }
   }
}
