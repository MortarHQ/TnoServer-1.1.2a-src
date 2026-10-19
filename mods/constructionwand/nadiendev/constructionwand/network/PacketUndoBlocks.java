package nadiendev.constructionwand.network;

import java.util.HashSet;
import java.util.Set;
import nadiendev.constructionwand.ConstructionWand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketUndoBlocks(HashSet<BlockPos> undoBlocks) implements CustomPacketPayload {
   public static final StreamCodec<FriendlyByteBuf, PacketUndoBlocks> CODEC = CustomPacketPayload.codec(PacketUndoBlocks::encode, PacketUndoBlocks::new);
   public static final Type<PacketUndoBlocks> ID = new Type(ResourceLocation.fromNamespaceAndPath("constructionwand", "undo_blocks"));

   public PacketUndoBlocks(FriendlyByteBuf buffer) {
      this(getSet(buffer));
   }

   public PacketUndoBlocks(Set<BlockPos> undoBlocks) {
      this(new HashSet<>(undoBlocks));
   }

   private static HashSet<BlockPos> getSet(FriendlyByteBuf buffer) {
      HashSet<BlockPos> undoBlocks = new HashSet<>();

      while (buffer.isReadable()) {
         undoBlocks.add(buffer.readBlockPos());
      }

      return undoBlocks;
   }

   public void encode(FriendlyByteBuf buffer) {
      for (BlockPos pos : this.undoBlocks) {
         buffer.writeBlockPos(pos);
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }

   public static PacketUndoBlocks decode(FriendlyByteBuf buffer) {
      HashSet<BlockPos> undoBlocks = new HashSet<>();

      while (buffer.isReadable()) {
         undoBlocks.add(buffer.readBlockPos());
      }

      return new PacketUndoBlocks(undoBlocks);
   }

   public static class Handler {
      public static void handle(PacketUndoBlocks msg, IPayloadContext ctx) {
         ctx.enqueueWork(() -> ConstructionWand.instance.renderBlockPreview.undoBlocks = msg.undoBlocks).exceptionally(e -> {
            ctx.disconnect(Component.translatable("constructionwand.networking.undo_blocks.failed", new Object[]{e.getMessage()}));
            return null;
         });
      }
   }
}
