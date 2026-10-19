package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;

public class LongNbtAdapter extends NbtAdapter<LongTag> {
   public LongNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public LongNbtAdapter asNullable() {
      return new LongNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(LongTag value, BitBuffer buffer) {
      Adapters.LONG.writeBits(value.getAsLong(), buffer);
   }

   protected LongTag readTagBits(BitBuffer buffer) {
      return LongTag.valueOf(Adapters.LONG.readBits(buffer).orElseThrow());
   }

   protected void writeTagBytes(LongTag value, ByteBuf buffer) {
      Adapters.LONG.writeBytes(value.getAsLong(), buffer);
   }

   protected LongTag readTagBytes(ByteBuf buffer) {
      return LongTag.valueOf(Adapters.LONG.readBytes(buffer).orElseThrow());
   }

   protected void writeTagData(LongTag value, DataOutput data) throws IOException {
      Adapters.LONG.writeData(value.getAsLong(), data);
   }

   protected LongTag readTagData(DataInput data) throws IOException {
      return LongTag.valueOf(Adapters.LONG.readData(data).orElseThrow());
   }

   protected Tag writeTagNbt(LongTag value) {
      return value;
   }

   protected LongTag readTagNbt(Tag nbt) {
      return nbt instanceof LongTag tag ? tag : null;
   }

   protected JsonElement writeTagJson(LongTag value) {
      return Adapters.LONG.writeJson(value.getAsLong()).orElseThrow();
   }

   protected LongTag readTagJson(JsonElement json) {
      return Adapters.LONG.readJson(json).<LongTag>map(LongTag::valueOf).orElse(null);
   }
}
