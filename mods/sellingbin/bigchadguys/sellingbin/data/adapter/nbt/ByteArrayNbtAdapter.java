package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.Tag;

public class ByteArrayNbtAdapter extends NbtAdapter<ByteArrayTag> {
   public ByteArrayNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public ByteArrayNbtAdapter asNullable() {
      return new ByteArrayNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(ByteArrayTag value, BitBuffer buffer) {
      Adapters.BYTE_ARRAY.writeBits(value.getAsByteArray(), buffer);
   }

   protected ByteArrayTag readTagBits(BitBuffer buffer) {
      return new ByteArrayTag(Adapters.BYTE_ARRAY.readBits(buffer).orElseThrow());
   }

   protected void writeTagBytes(ByteArrayTag value, ByteBuf buffer) {
      Adapters.BYTE_ARRAY.writeBytes(value.getAsByteArray(), buffer);
   }

   protected ByteArrayTag readTagBytes(ByteBuf buffer) {
      return new ByteArrayTag(Adapters.BYTE_ARRAY.readBytes(buffer).orElseThrow());
   }

   protected void writeTagData(ByteArrayTag value, DataOutput data) throws IOException {
      Adapters.BYTE_ARRAY.writeData(value.getAsByteArray(), data);
   }

   protected ByteArrayTag readTagData(DataInput data) throws IOException {
      return new ByteArrayTag(Adapters.BYTE_ARRAY.readData(data).orElseThrow());
   }

   protected Tag writeTagNbt(ByteArrayTag value) {
      return value.copy();
   }

   protected ByteArrayTag readTagNbt(Tag nbt) {
      return nbt instanceof ByteArrayTag tag ? (ByteArrayTag)tag.copy() : null;
   }

   protected JsonElement writeTagJson(ByteArrayTag value) {
      return (JsonElement)Adapters.BYTE_ARRAY.writeJson(value.getAsByteArray()).map(array -> {
         JsonArray copy = new JsonArray();
         copy.add("B");

         for (int i = 1; i < array.size(); i++) {
            copy.add(array.get(i));
         }

         return copy;
      }).orElse(null);
   }

   protected ByteArrayTag readTagJson(JsonElement json) {
      if (!(json instanceof JsonArray array)) {
         return null;
      } else {
         if (array.size() > 0 && array.get(0) instanceof JsonPrimitive primitive && primitive.getAsString().equals("B")) {
            JsonArray copy = new JsonArray();

            for (int i = 1; i < array.size(); i++) {
               copy.add(array.get(i));
            }

            array = copy;
         }

         return Adapters.BYTE_ARRAY.readJson(array).<ByteArrayTag>map(ByteArrayTag::new).orElse(null);
      }
   }
}
