package bigchadguys.sellingbin.data.adapter.number;

import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class ByteAdapter extends NumberAdapter<Byte> {
   public ByteAdapter(boolean nullable) {
      super(nullable);
   }

   public ByteAdapter asNullable() {
      return new ByteAdapter(true);
   }

   protected void writeNumberBits(Byte value, BitBuffer buffer) {
      buffer.writeByte(value);
   }

   protected Byte readNumberBits(BitBuffer buffer) {
      return buffer.readByte();
   }

   protected void writeNumberBytes(Byte value, ByteBuf buffer) {
      buffer.writeByte(value);
   }

   protected Byte readNumberBytes(ByteBuf buffer) {
      return buffer.readByte();
   }

   protected void writeNumberData(Byte value, DataOutput data) throws IOException {
      data.writeByte(value);
   }

   protected Byte readNumberData(DataInput data) throws IOException {
      return data.readByte();
   }

   protected Tag writeNumberNbt(Byte value) {
      return wrap(reduce(value));
   }

   protected Byte readNumberNbt(Tag nbt) {
      if (nbt instanceof NumericTag numeric) {
         return numeric.getAsByte();
      } else if (nbt instanceof ListTag list && list.size() == 1) {
         return this.readNumberNbt(list.get(0));
      } else {
         return nbt instanceof StringTag string ? parse(string.toString()).map(Number::byteValue).orElse(null) : null;
      }
   }

   protected JsonElement writeNumberJson(Byte value) {
      return new JsonPrimitive(value);
   }

   protected Byte readNumberJson(JsonElement json) {
      if (json instanceof JsonObject) {
         return null;
      } else if (json instanceof JsonArray array && array.size() == 1) {
         return this.readNumberJson(array.get(0));
      } else {
         if (json instanceof JsonPrimitive primitive) {
            if (primitive.isNumber()) {
               return primitive.getAsByte();
            }

            if (primitive.isString()) {
               return parse(primitive.getAsString()).map(Number::byteValue).orElse(null);
            }
         }

         return null;
      }
   }
}
