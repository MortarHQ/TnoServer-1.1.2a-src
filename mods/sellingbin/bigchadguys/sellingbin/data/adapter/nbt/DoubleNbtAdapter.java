package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;

public class DoubleNbtAdapter extends NbtAdapter<DoubleTag> {
   public DoubleNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public DoubleNbtAdapter asNullable() {
      return new DoubleNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(DoubleTag value, BitBuffer buffer) {
      Adapters.DOUBLE.writeBits(value.getAsDouble(), buffer);
   }

   protected DoubleTag readTagBits(BitBuffer buffer) {
      return DoubleTag.valueOf(Adapters.DOUBLE.readBits(buffer).orElseThrow());
   }

   protected void writeTagBytes(DoubleTag value, ByteBuf buffer) {
      Adapters.DOUBLE.writeBytes(value.getAsDouble(), buffer);
   }

   protected DoubleTag readTagBytes(ByteBuf buffer) {
      return DoubleTag.valueOf(Adapters.DOUBLE.readBytes(buffer).orElseThrow());
   }

   protected void writeTagData(DoubleTag value, DataOutput data) throws IOException {
      Adapters.DOUBLE.writeData(value.getAsDouble(), data);
   }

   protected DoubleTag readTagData(DataInput data) throws IOException {
      return DoubleTag.valueOf(Adapters.DOUBLE.readData(data).orElseThrow());
   }

   protected Tag writeTagNbt(DoubleTag value) {
      return value;
   }

   protected DoubleTag readTagNbt(Tag nbt) {
      return nbt instanceof DoubleTag tag ? tag : null;
   }

   protected JsonElement writeTagJson(DoubleTag value) {
      return Adapters.DOUBLE.writeJson(value.getAsDouble()).orElseThrow();
   }

   protected DoubleTag readTagJson(JsonElement json) {
      return Adapters.DOUBLE.readJson(json).<DoubleTag>map(DoubleTag::valueOf).orElse(null);
   }
}
