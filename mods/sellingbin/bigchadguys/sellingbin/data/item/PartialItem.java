package bigchadguys.sellingbin.data.item;

import bigchadguys.sellingbin.SellingBinMod;
import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.data.nbt.PartialCompoundNbt;
import com.google.gson.JsonElement;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PartialItem implements ItemPlacement<PartialItem> {
   protected ResourceLocation id;

   protected PartialItem(ResourceLocation id) {
      this.id = id;
   }

   public static PartialItem empty() {
      return new PartialItem(null);
   }

   public static PartialItem of(ResourceLocation id) {
      return new PartialItem(id);
   }

   public static PartialItem of(Item item) {
      return new PartialItem(BuiltInRegistries.ITEM.getKey(item));
   }

   public static PartialItem of(ItemStack stack) {
      return new PartialItem(BuiltInRegistries.ITEM.getKey(stack.getItem()));
   }

   public boolean isSubsetOf(PartialItem other) {
      return this.id == null || this.id.equals(other.id);
   }

   @Override
   public boolean isSubsetOf(ItemStack stack) {
      return this.isSubsetOf(of(stack));
   }

   public void fillInto(PartialItem other) {
      if (this.id != null) {
         other.id = this.id;
      }
   }

   @Override
   public Optional<ItemStack> generate(int count) {
      return this.asWhole().map(item -> new ItemStack(item, count));
   }

   @Override
   public boolean test(PartialItem item, PartialCompoundNbt nbt) {
      return this.isSubsetOf(item);
   }

   @Override
   public void validate(String path) {
      if (this.asWhole().isEmpty()) {
         SellingBinMod.LOGGER.error("%s: Unregistered item <%s>".formatted(path, this.id));
      }
   }

   public Optional<Item> asWhole() {
      return BuiltInRegistries.ITEM.getOptional(this.id);
   }

   public PartialItem copy() {
      return new PartialItem(this.id);
   }

   @Override
   public String toString() {
      return this.id == null ? "" : this.id.toString();
   }

   public static Optional<PartialItem> parse(String string, boolean logErrors) {
      try {
         return Optional.of(parse(new StringReader(string)));
      } catch (CommandSyntaxException | IllegalArgumentException e) {
         if (logErrors) {
            e.printStackTrace();
         }

         return Optional.empty();
      }
   }

   public static PartialItem parse(String string) throws CommandSyntaxException {
      return parse(new StringReader(string));
   }

   public static PartialItem parse(StringReader reader) throws CommandSyntaxException {
      if (reader.canRead() && isCharValid(reader.peek())) {
         int cursor = reader.getCursor();

         while (reader.canRead() && isCharValid(reader.peek())) {
            reader.skip();
         }

         String string = reader.getString().substring(cursor, reader.getCursor());

         try {
            return of(ResourceLocation.parse(string));
         } catch (ResourceLocationException e) {
            reader.setCursor(cursor);
            throw new IllegalArgumentException("Invalid item identifier '" + string + "' in stack '" + reader.getString() + "'");
         }
      } else {
         return empty();
      }
   }

   protected static boolean isCharValid(char c) {
      return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
   }

   public static class Adapter implements ISimpleAdapter<PartialItem, Tag, JsonElement> {
      public void writeBits(PartialItem value, BitBuffer buffer) {
         buffer.writeBoolean(value != null);
         if (value != null) {
            Adapters.IDENTIFIER.asNullable().writeBits(value.id, buffer);
         }
      }

      @Override
      public Optional<PartialItem> readBits(BitBuffer buffer) {
         return buffer.readBoolean() ? Adapters.IDENTIFIER.asNullable().readBits(buffer).map(PartialItem::of) : Optional.empty();
      }

      public Optional<Tag> writeNbt(PartialItem value) {
         return value == null ? Optional.empty() : Adapters.IDENTIFIER.writeNbt(value.id);
      }

      @Override
      public Optional<PartialItem> readNbt(Tag nbt) {
         return nbt == null ? Optional.empty() : Adapters.IDENTIFIER.readNbt(nbt).map(PartialItem::of);
      }

      public Optional<JsonElement> writeJson(PartialItem value) {
         return value == null ? Optional.empty() : Adapters.IDENTIFIER.writeJson(value.id);
      }

      @Override
      public Optional<PartialItem> readJson(JsonElement json) {
         return json == null ? Optional.empty() : Adapters.IDENTIFIER.readJson(json).map(PartialItem::of);
      }
   }
}
