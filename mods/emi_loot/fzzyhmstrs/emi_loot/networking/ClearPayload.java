package fzzyhmstrs.emi_loot.networking;

import fzzyhmstrs.emi_loot.EMILoot;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public class ClearPayload implements CustomPacketPayload {
   public static final ClearPayload INSTANCE = new ClearPayload();
   public static final Type<ClearPayload> TYPE = new Type(EMILoot.identity("clear_loot"));
   public static final StreamCodec<ByteBuf, ClearPayload> CODEC = StreamCodec.unit(INSTANCE);

   public Type<ClearPayload> type() {
      return TYPE;
   }
}
