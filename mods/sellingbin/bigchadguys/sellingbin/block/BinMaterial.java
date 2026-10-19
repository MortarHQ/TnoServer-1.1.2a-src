package bigchadguys.sellingbin.block;

import bigchadguys.sellingbin.init.ModConfigs;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;

public enum BinMaterial implements StringRepresentable {
   WOOD("wood"),
   REDSTONE("redstone"),
   IRON("iron"),
   DIAMOND("diamond"),
   NETHERITE("netherite");

   public static final IntFunction<BinMaterial> ID_TO_VALUE = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
   public static final Codec<BinMaterial> CODEC = StringRepresentable.fromValues(BinMaterial::values);
   public static final StreamCodec<ByteBuf, BinMaterial> PACKET_CODEC = ByteBufCodecs.idMapper(ID_TO_VALUE, Enum::ordinal);
   private final String id;

   BinMaterial(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public BinSettings getSettings() {
      return ModConfigs.SELLING_BIN.getSettings(this);
   }

   public long getUpdateDelayTicks() {
      return this.getSettings().getUpdateDelayTicks();
   }

   public int getRows() {
      return this.getSettings().getInventoryRows();
   }

   public String getSerializedName() {
      return this.id;
   }

   public static BinMaterial of(String id) {
      for (BinMaterial value : values()) {
         if (value.id.equals(id)) {
            return value;
         }
      }

      return null;
   }
}
