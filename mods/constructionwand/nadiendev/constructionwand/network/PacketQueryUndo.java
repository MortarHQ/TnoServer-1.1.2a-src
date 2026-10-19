package nadiendev.constructionwand.network;

import nadiendev.constructionwand.ConstructionWand;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketQueryUndo(boolean undoPressed) implements CustomPacketPayload {
   public static final StreamCodec<FriendlyByteBuf, PacketQueryUndo> CODEC = CustomPacketPayload.codec(PacketQueryUndo::encode, PacketQueryUndo::new);
   public static final Type<PacketQueryUndo> ID = new Type(ResourceLocation.fromNamespaceAndPath("constructionwand", "query_undo"));

   public PacketQueryUndo(FriendlyByteBuf buf) {
      this(buf.readBoolean());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeBoolean(this.undoPressed);
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }

   public static class Handler {
      public static void handle(PacketQueryUndo msg, IPayloadContext ctx) {
         ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer player) {
               ConstructionWand.instance.undoHistory.updateClient(player, msg.undoPressed);
            }
         }).exceptionally(e -> {
            ctx.disconnect(Component.translatable("constructionwand.networking.query_undo.failed", new Object[]{e.getMessage()}));
            return null;
         });
      }
   }
}
