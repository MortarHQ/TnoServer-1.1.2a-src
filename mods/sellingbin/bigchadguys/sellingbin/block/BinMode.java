package bigchadguys.sellingbin.block;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;

public enum BinMode implements StringRepresentable {
   BLOCK_BOUND("block_bound"),
   PLAYER_BOUND("player_bound");

   public static final IntFunction<BinMode> ID_TO_VALUE = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
   public static final Codec<BinMode> CODEC = StringRepresentable.fromValues(BinMode::values);
   public static final StreamCodec<ByteBuf, BinMode> PACKET_CODEC = ByteBufCodecs.idMapper(ID_TO_VALUE, Enum::ordinal);
   private final String id;

   BinMode(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public String getSerializedName() {
      return this.id;
   }
}
