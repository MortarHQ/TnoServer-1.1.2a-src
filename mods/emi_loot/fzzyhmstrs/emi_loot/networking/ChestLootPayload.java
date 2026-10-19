package fzzyhmstrs.emi_loot.networking;

import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import org.jetbrains.annotations.NotNull;

public record ChestLootPayload(FriendlyByteBuf buf) implements CustomPacketPayload {
   public static final Type<ChestLootPayload> TYPE = new Type(EMILoot.identity("chest"));
   public static final StreamCodec<FriendlyByteBuf, ChestLootPayload> CODEC = StreamCodec.ofMember(ChestLootPayload::write, ChestLootPayload::new);

   public ChestLootPayload(FriendlyByteBuf buf) {
      this.buf = new FriendlyByteBuf(buf.readBytes(buf.readableBytes()));
   }

   private void write(@NotNull FriendlyByteBuf packetByteBuf) {
      packetByteBuf.writeBytes(this.buf, this.buf.readerIndex(), this.buf.readableBytes());
      if (this.buf.refCnt() > 1) {
         this.buf.release();
      }
   }

   public void release() {
      if (this.buf.refCnt() > 1) {
         this.buf.release();
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
