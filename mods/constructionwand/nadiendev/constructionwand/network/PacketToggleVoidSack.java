package nadiendev.constructionwand.network;

import nadiendev.constructionwand.items.containeritems.MenuVoidSack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketToggleVoidSack(InteractionHand hand) implements CustomPacketPayload {
   public static final Type<PacketToggleVoidSack> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("constructionwand", "toggle_void_sack"));
   public static final StreamCodec<FriendlyByteBuf, PacketToggleVoidSack> CODEC = StreamCodec.composite(
      ByteBufCodecs.idMapper(id -> id == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, hand -> hand == InteractionHand.MAIN_HAND ? 0 : 1),
      PacketToggleVoidSack::hand,
      PacketToggleVoidSack::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(PacketToggleVoidSack packet, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (ctx.player() instanceof ServerPlayer serverPlayer && serverPlayer.containerMenu instanceof MenuVoidSack menu) {
            menu.toggleSendToContainer();
         }
      });
   }

   public static void send(InteractionHand hand) {
      PacketDistributor.sendToServer(new PacketToggleVoidSack(hand), new CustomPacketPayload[0]);
   }
}
