package nadiendev.constructionwand.network;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.WandUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketWandUndo() implements CustomPacketPayload {
   public static final StreamCodec<FriendlyByteBuf, PacketWandUndo> CODEC = CustomPacketPayload.codec(PacketWandUndo::encode, PacketWandUndo::new);
   public static final Type<PacketWandUndo> ID = new Type(ResourceLocation.fromNamespaceAndPath("constructionwand", "wand_undo"));

   public PacketWandUndo(FriendlyByteBuf buffer) {
      this();
   }

   public void encode(FriendlyByteBuf buffer) {
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }

   public static class Handler {
      public static void handle(PacketWandUndo msg, IPayloadContext ctx) {
         ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer player) {
               ItemStack wand = WandUtil.holdingWand(player);
               if (wand != null) {
                  boolean success = ConstructionWand.instance.undoHistory.undoLast(player, player.level());
                  if (success) {
                     player.displayClientMessage(Component.translatable("constructionwand.undo.success").withStyle(ChatFormatting.GREEN), true);
                  } else {
                     player.displayClientMessage(Component.translatable("constructionwand.undo.nothing").withStyle(ChatFormatting.GRAY), true);
                  }
               }
            }
         }).exceptionally(e -> {
            ctx.disconnect(Component.translatable("constructionwand.networking.wand_undo.failed", new Object[]{e.getMessage()}));
            return null;
         });
      }
   }
}
