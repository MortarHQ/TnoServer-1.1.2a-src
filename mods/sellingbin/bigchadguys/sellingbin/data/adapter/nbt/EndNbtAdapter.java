package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;

public class EndNbtAdapter extends NbtAdapter<EndTag> {
   public EndNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public EndNbtAdapter asNullable() {
      return new EndNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(EndTag value, BitBuffer buffer) {
   }

   protected EndTag readTagBits(BitBuffer buffer) {
      return EndTag.INSTANCE;
   }

   protected void writeTagBytes(EndTag value, ByteBuf buffer) {
   }

   protected EndTag readTagBytes(ByteBuf buffer) {
      return EndTag.INSTANCE;
   }

   protected void writeTagData(EndTag value, DataOutput data) throws IOException {
   }

   protected EndTag readTagData(DataInput data) throws IOException {
      return EndTag.INSTANCE;
   }

   protected Tag writeTagNbt(EndTag value) {
      return value;
   }

   protected EndTag readTagNbt(Tag nbt) {
      return nbt instanceof EndTag tag ? tag : null;
   }

   protected JsonElement writeTagJson(EndTag value) {
      return JsonNull.INSTANCE;
   }

   protected EndTag readTagJson(JsonElement json) {
      return EndTag.INSTANCE;
   }
}
