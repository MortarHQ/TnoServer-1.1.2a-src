package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class ListNbtAdapter extends NbtAdapter<ListTag> {
   protected static final String[] ID_TO_KEY = new String[]{
      "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE_ARRAY", "STRING", "LIST", "COMPOUND", "INT_ARRAY", "LONG_ARRAY"
   };
   protected static final Object2IntMap<String> KEY_TO_ID = new Object2IntOpenHashMap();

   public ListNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public ListNbtAdapter asNullable() {
      return new ListNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(ListTag value, BitBuffer buffer) {
      GenericNbtAdapter.NBT_ID.writeBits(value.getElementType(), buffer);
      Adapters.INT_SEGMENTED_3.writeBits(value.size(), buffer);

      for (Tag element : value) {
         Adapters.NBT[value.getElementType()].writeBits(element, buffer);
      }
   }

   protected ListTag readTagBits(BitBuffer buffer) {
      ListTag list = new ListTag();
      byte id = GenericNbtAdapter.NBT_ID.readBits(buffer).orElseThrow();
      int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

      for (int i = 0; i < size; i++) {
         list.add((Tag)Adapters.NBT[id].readBits(buffer).orElseThrow());
      }

      return list;
   }

   protected void writeTagBytes(ListTag value, ByteBuf buffer) {
      GenericNbtAdapter.NBT_ID.writeBytes(value.getElementType(), buffer);
      Adapters.INT_SEGMENTED_3.writeBytes(value.size(), buffer);

      for (Tag element : value) {
         Adapters.NBT[value.getElementType()].writeBytes(element, buffer);
      }
   }

   protected ListTag readTagBytes(ByteBuf buffer) {
      ListTag list = new ListTag();
      byte id = GenericNbtAdapter.NBT_ID.readBytes(buffer).orElseThrow();
      int size = Adapters.INT_SEGMENTED_3.readBytes(buffer).orElseThrow();

      for (int i = 0; i < size; i++) {
         list.add((Tag)Adapters.NBT[id].readBytes(buffer).orElseThrow());
      }

      return list;
   }

   protected void writeTagData(ListTag value, DataOutput data) throws IOException {
      GenericNbtAdapter.NBT_ID.writeData(value.getElementType(), data);
      Adapters.INT_SEGMENTED_3.writeData(value.size(), data);

      for (Tag element : value) {
         Adapters.NBT[value.getElementType()].writeData(element, data);
      }
   }

   protected ListTag readTagData(DataInput data) throws IOException {
      ListTag list = new ListTag();
      byte id = GenericNbtAdapter.NBT_ID.readData(data).orElseThrow();
      int size = Adapters.INT_SEGMENTED_3.readData(data).orElseThrow();

      for (int i = 0; i < size; i++) {
         list.add((Tag)Adapters.NBT[id].readData(data).orElseThrow());
      }

      return list;
   }

   protected Tag writeTagNbt(ListTag value) {
      return value.copy();
   }

   protected ListTag readTagNbt(Tag nbt) {
      return nbt instanceof ListTag tag ? tag.copy() : null;
   }

   protected JsonElement writeTagJson(ListTag value) {
      JsonArray array = new JsonArray();
      array.add(KEY_TO_ID.getInt(value.getElementType()));

      for (Tag tag : value) {
         Adapters.NBT[value.getElementType()].writeJson(tag).ifPresent(o -> array.add(o));
      }

      return array;
   }

   protected ListTag readTagJson(JsonElement json) {
      if (json instanceof JsonArray array && !array.isEmpty()) {
         ListTag list = new ListTag();
         int id;
         if (array.get(0) instanceof JsonPrimitive primitive && primitive.isString() && (id = KEY_TO_ID.getInt(primitive.getAsString())) >= 0) {
            for (int i = 1; i < array.size(); i++) {
               Adapters.NBT[id].readJson(array.get(i)).ifPresent(tag -> list.add(tag));
            }
         } else {
            for (int i = 0; i < array.size(); i++) {
               JsonElement element = array.get(i);

               try {
                  Adapters.GENERIC_NBT.readJson(element).ifPresent(list::add);
               } catch (UnsupportedOperationException exception) {
                  return null;
               }
            }
         }

         return list;
      } else {
         return null;
      }
   }

   static {
      for (int i = 0; i < ID_TO_KEY.length; i++) {
         KEY_TO_ID.put(ID_TO_KEY[i], i);
      }

      KEY_TO_ID.defaultReturnValue(-1);
   }
}
