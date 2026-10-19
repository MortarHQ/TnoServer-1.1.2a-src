package bigchadguys.sellingbin.trade;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.data.item.ItemPredicate;
import bigchadguys.sellingbin.data.item.PartialStack;
import bigchadguys.sellingbin.data.serializable.ISerializable;
import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class Trade implements ISerializable<CompoundTag, JsonObject> {
   private final Trade.Input input;
   private final Trade.Output output;

   public Trade() {
      this.input = new Trade.Input();
      this.output = new Trade.Output();
   }

   public Trade(Trade.Input input, Trade.Output output) {
      this.input = input;
      this.output = output;
   }

   public Trade.Input getInput() {
      return this.input;
   }

   public Trade.Output getOutput() {
      return this.output;
   }

   @Override
   public void writeBits(BitBuffer buffer) {
      this.input.writeBits(buffer);
      this.output.writeBits(buffer);
   }

   @Override
   public void readBits(BitBuffer buffer) {
      this.input.readBits(buffer);
      this.output.readBits(buffer);
   }

   @Override
   public Optional<CompoundTag> writeNbt() {
      CompoundTag nbt = new CompoundTag();
      this.input.writeNbt().ifPresent(value -> nbt.put("input", value));
      this.output.writeNbt().ifPresent(value -> nbt.put("output", value));
      return Optional.of(nbt);
   }

   public void readNbt(CompoundTag nbt) {
      this.input.readNbt(nbt.getCompound("input"));
      this.output.readNbt(nbt.getCompound("output"));
   }

   @Override
   public Optional<JsonObject> writeJson() {
      JsonObject json = new JsonObject();
      this.input.writeJson().ifPresent(value -> json.add("input", value));
      this.output.writeJson().ifPresent(value -> json.add("output", value));
      return Optional.of(json);
   }

   public void readJson(JsonObject json) {
      this.input.readJson(json.getAsJsonObject("input"));
      this.output.readJson(json.getAsJsonObject("output"));
   }

   public void validate(String path) {
      this.input.filter.validate(path + ".input");
      this.output.stack.validate(path + ".output");
   }

   public static class Input implements ISerializable<CompoundTag, JsonObject> {
      private ItemPredicate filter;
      private int count;

      public Input() {
      }

      public Input(ItemPredicate filter, int count) {
         this.filter = filter;
         this.count = count;
      }

      public ItemPredicate getFilter() {
         return this.filter;
      }

      public int getCount() {
         return this.count;
      }

      @Override
      public void writeBits(BitBuffer buffer) {
         Adapters.ITEM_PREDICATE.writeBits(this.filter, buffer);
         Adapters.INT_SEGMENTED_3.writeBits(this.count, buffer);
      }

      @Override
      public void readBits(BitBuffer buffer) {
         this.filter = Adapters.ITEM_PREDICATE.readBits(buffer).orElseThrow();
         this.count = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();
      }

      @Override
      public Optional<CompoundTag> writeNbt() {
         CompoundTag nbt = new CompoundTag();
         Adapters.ITEM_PREDICATE.writeNbt(this.filter).ifPresent(value -> nbt.put("filter", value));
         Adapters.INT_SEGMENTED_3.writeNbt(this.count).ifPresent(value -> nbt.put("count", value));
         return Optional.of(nbt);
      }

      public void readNbt(CompoundTag nbt) {
         this.filter = Adapters.ITEM_PREDICATE.readNbt(nbt.get("filter")).orElseThrow();
         this.count = Adapters.INT_SEGMENTED_3.readNbt(nbt.get("count")).orElseThrow();
      }

      @Override
      public Optional<JsonObject> writeJson() {
         JsonObject json = new JsonObject();
         Adapters.ITEM_PREDICATE.writeJson(this.filter).ifPresent(value -> json.add("filter", value));
         Adapters.INT_SEGMENTED_3.writeJson(this.count).ifPresent(value -> json.add("count", value));
         return Optional.of(json);
      }

      public void readJson(JsonObject json) {
         this.filter = Adapters.ITEM_PREDICATE.readJson(json.get("filter")).orElseThrow();
         this.count = Adapters.INT_SEGMENTED_3.readJson(json.get("count")).orElseThrow();
      }
   }

   public static class Output implements ISerializable<CompoundTag, JsonObject> {
      private PartialStack stack;
      private int count;

      public Output() {
      }

      public Output(PartialStack stack, int count) {
         this.stack = stack;
         this.count = count;
      }

      public PartialStack getStack() {
         return this.stack;
      }

      public int getCount() {
         return this.count;
      }

      public Optional<ItemStack> generate() {
         return this.stack.generate(this.count);
      }

      @Override
      public void writeBits(BitBuffer buffer) {
         Adapters.PARTIAL_STACK.writeBits(this.stack, buffer);
         Adapters.INT_SEGMENTED_3.writeBits(this.count, buffer);
      }

      @Override
      public void readBits(BitBuffer buffer) {
         this.stack = Adapters.PARTIAL_STACK.readBits(buffer).orElseThrow();
         this.count = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();
      }

      @Override
      public Optional<CompoundTag> writeNbt() {
         CompoundTag nbt = new CompoundTag();
         Adapters.PARTIAL_STACK.writeNbt(this.stack).ifPresent(value -> nbt.put("stack", value));
         Adapters.INT.writeNbt(this.count).ifPresent(value -> nbt.put("count", value));
         return Optional.of(nbt);
      }

      public void readNbt(CompoundTag nbt) {
         this.stack = Adapters.PARTIAL_STACK.readNbt(nbt.get("stack")).orElseThrow();
         this.count = Adapters.INT.readNbt(nbt.get("count")).orElseThrow();
      }

      @Override
      public Optional<JsonObject> writeJson() {
         return Adapters.PARTIAL_STACK.writeJson(this.stack).map(tag -> {
            if (tag instanceof JsonObject object) {
               Adapters.INT.writeJson(this.count).ifPresent(value -> object.add("count", value));
            }

            return (JsonObject)tag;
         });
      }

      public void readJson(JsonObject json) {
         this.stack = Adapters.PARTIAL_STACK.readJson(json).orElseThrow();
         this.count = Adapters.INT.readJson(json.get("count")).orElse(1);
      }
   }
}
