package bigchadguys.sellingbin.data.adapter.util;

import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.LongTag;

public class BlockPosAdapter implements ISimpleAdapter<BlockPos, LongTag, JsonPrimitive> {
   private final boolean nullable;

   public BlockPosAdapter(boolean nullable) {
      this.nullable = nullable;
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public BlockPosAdapter asNullable() {
      return new BlockPosAdapter(true);
   }

   public void writeBits(BlockPos value, BitBuffer buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         buffer.writeLong(value.asLong());
      }
   }

   @Override
   public Optional<BlockPos> readBits(BitBuffer buffer) {
      return this.nullable && buffer.readBoolean() ? Optional.empty() : Optional.of(BlockPos.of(buffer.readLong()));
   }

   public void writeBytes(BlockPos value, ByteBuf buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         buffer.writeLong(value.asLong());
      }
   }

   @Override
   public Optional<BlockPos> readBytes(ByteBuf buffer) {
      return this.nullable && buffer.readBoolean() ? Optional.empty() : Optional.of(BlockPos.of(buffer.readLong()));
   }

   public void writeData(BlockPos value, DataOutput data) throws IOException {
      if (this.nullable) {
         data.writeBoolean(value == null);
      }

      if (value != null) {
         data.writeLong(value.asLong());
      }
   }

   @Override
   public Optional<BlockPos> readData(DataInput data) throws IOException {
      return this.nullable && data.readBoolean() ? Optional.empty() : Optional.of(BlockPos.of(data.readLong()));
   }

   public Optional<LongTag> writeNbt(BlockPos value) {
      return value == null ? Optional.empty() : Optional.of(LongTag.valueOf(value.asLong()));
   }

   public Optional<BlockPos> readNbt(LongTag nbt) {
      return nbt != null ? Optional.of(BlockPos.of(nbt.getAsLong())) : Optional.empty();
   }

   public Optional<JsonPrimitive> writeJson(BlockPos value) {
      return value == null ? Optional.empty() : Optional.of(new JsonPrimitive(value.asLong()));
   }

   public Optional<BlockPos> readJson(JsonPrimitive json) {
      return json != null && json.isNumber() ? Optional.of(BlockPos.of(json.getAsLong())) : Optional.empty();
   }
}
