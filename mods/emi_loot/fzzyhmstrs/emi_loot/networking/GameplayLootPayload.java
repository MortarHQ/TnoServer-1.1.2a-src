package fzzyhmstrs.emi_loot.networking;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import org.jetbrains.annotations.NotNull;

public record GameplayLootPayload(FriendlyByteBuf buf) implements CustomPacketPayload {
   public static final Type<GameplayLootPayload> TYPE = new Type(EMILoot.identity("gameplay"));
   public static final StreamCodec<FriendlyByteBuf, GameplayLootPayload> CODEC = StreamCodec.ofMember(GameplayLootPayload::write, GameplayLootPayload::new);

   public GameplayLootPayload(FriendlyByteBuf buf) {
      this.buf = new FriendlyByteBuf(buf.readBytes(buf.readableBytes()));
   }

   private void write(@NotNull FriendlyByteBuf packetByteBuf) {
      packetByteBuf.writeBytes(this.buf, this.buf.readerIndex(), this.buf.readableBytes());
      EMILootAgnos.releaseBuffer(this.buf);
   }

   public void release() {
      EMILootAgnos.releaseBuffer(this.buf);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
