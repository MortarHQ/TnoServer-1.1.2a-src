package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;

public class ByteNbtAdapter extends NbtAdapter<ByteTag> {
   public ByteNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public ByteNbtAdapter asNullable() {
      return new ByteNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(ByteTag value, BitBuffer buffer) {
      Adapters.BYTE.writeBits(value.getAsByte(), buffer);
   }

   protected ByteTag readTagBits(BitBuffer buffer) {
      return ByteTag.valueOf(Adapters.BYTE.readBits(buffer).orElseThrow());
   }

   protected void writeTagBytes(ByteTag value, ByteBuf buffer) {
      Adapters.BYTE.writeBytes(value.getAsByte(), buffer);
   }

   protected ByteTag readTagBytes(ByteBuf buffer) {
      return ByteTag.valueOf(Adapters.BYTE.readBytes(buffer).orElseThrow());
   }

   protected void writeTagData(ByteTag value, DataOutput data) throws IOException {
      Adapters.BYTE.writeData(value.getAsByte(), data);
   }

   protected ByteTag readTagData(DataInput data) throws IOException {
      return ByteTag.valueOf(Adapters.BYTE.readData(data).orElseThrow());
   }

   protected Tag writeTagNbt(ByteTag value) {
      return value;
   }

   protected ByteTag readTagNbt(Tag nbt) {
      return nbt instanceof ByteTag tag ? tag : null;
   }

   protected JsonElement writeTagJson(ByteTag value) {
      return Adapters.BYTE.writeJson(value.getAsByte()).orElseThrow();
   }

   protected ByteTag readTagJson(JsonElement json) {
      return Adapters.BYTE.readJson(json).<ByteTag>map(ByteTag::valueOf).orElse(null);
   }
}
