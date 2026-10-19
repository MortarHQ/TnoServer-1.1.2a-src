package bigchadguys.sellingbin.data.adapter.nbt;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;

public class ShortNbtAdapter extends NbtAdapter<ShortTag> {
   public ShortNbtAdapter(boolean nullable) {
      super(nullable);
   }

   public ShortNbtAdapter asNullable() {
      return new ShortNbtAdapter(this.isNullable());
   }

   protected void writeTagBits(ShortTag value, BitBuffer buffer) {
      Adapters.SHORT.writeBits(value.getAsShort(), buffer);
   }

   protected ShortTag readTagBits(BitBuffer buffer) {
      return ShortTag.valueOf(Adapters.SHORT.readBits(buffer).orElseThrow());
   }

   protected void writeTagBytes(ShortTag value, ByteBuf buffer) {
      Adapters.SHORT.writeBytes(value.getAsShort(), buffer);
   }

   protected ShortTag readTagBytes(ByteBuf buffer) {
      return ShortTag.valueOf(Adapters.SHORT.readBytes(buffer).orElseThrow());
   }

   protected void writeTagData(ShortTag value, DataOutput data) throws IOException {
      Adapters.SHORT.writeData(value.getAsShort(), data);
   }

   protected ShortTag readTagData(DataInput data) throws IOException {
      return ShortTag.valueOf(Adapters.SHORT.readData(data).orElseThrow());
   }

   protected Tag writeTagNbt(ShortTag value) {
      return value;
   }

   protected ShortTag readTagNbt(Tag nbt) {
      return nbt instanceof ShortTag tag ? tag : null;
   }

   protected JsonElement writeTagJson(ShortTag value) {
      return Adapters.SHORT.writeJson(value.getAsShort()).orElseThrow();
   }

   protected ShortTag readTagJson(JsonElement json) {
      return Adapters.SHORT.readJson(json).<ShortTag>map(ShortTag::valueOf).orElse(null);
   }
}
