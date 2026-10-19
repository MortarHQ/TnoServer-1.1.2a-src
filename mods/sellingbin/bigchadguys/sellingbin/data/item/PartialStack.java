package bigchadguys.sellingbin.data.item;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.data.nbt.PartialCompoundNbt;
import bigchadguys.sellingbin.util.ItemUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class PartialStack implements ItemPlacement<PartialStack> {
   protected PartialItem item;
   protected PartialCompoundNbt nbt;

   protected PartialStack(PartialItem item, PartialCompoundNbt nbt) {
      this.item = item;
      this.nbt = nbt;
   }

   public static PartialStack of(PartialItem item, PartialCompoundNbt nbt) {
      return new PartialStack(item, nbt);
   }

   public static PartialStack of(ItemStack stack) {
      return new PartialStack(PartialItem.of(stack), PartialCompoundNbt.of(stack));
   }

   public PartialItem getItem() {
      return this.item;
   }

   public PartialCompoundNbt getNbt() {
      return this.nbt;
   }

   public void setItem(PartialItem item) {
      this.item = item;
   }

   public void setNbt(PartialCompoundNbt nbt) {
      this.nbt = nbt;
   }

   public boolean isSubsetOf(PartialStack other) {
      return !this.item.isSubsetOf(other.item) ? false : this.nbt.isSubsetOf(other.nbt);
   }

   @Override
   public boolean isSubsetOf(ItemStack stack) {
      return !this.item.isSubsetOf(stack) ? false : this.nbt.isSubsetOf(stack);
   }

   public void fillInto(PartialStack other) {
      this.item.fillInto(other.item);
      this.nbt.fillInto(other.nbt);
   }

   @Override
   public Optional<ItemStack> generate(int count) {
      return this.item.generate(count).map(stack -> {
         ItemUtils.setNbt(stack, this.nbt.asWhole().orElse(null));
         return (ItemStack)stack;
      });
   }

   @Override
   public boolean test(PartialItem item, PartialCompoundNbt nbt) {
      return this.isSubsetOf(of(item, nbt));
   }

   @Override
   public void validate(String path) {
      this.item.validate(path);
      this.nbt.validate(path);
   }

   public PartialStack copy() {
      return new PartialStack(this.item.copy(), this.nbt.copy());
   }

   @Override
   public String toString() {
      return this.item.toString() + this.nbt.toString();
   }

   public static Optional<PartialStack> parse(String string, boolean logErrors) {
      try {
         return Optional.of(parse(new StringReader(string)));
      } catch (CommandSyntaxException | IllegalArgumentException e) {
         if (logErrors) {
            e.printStackTrace();
         }

         return Optional.empty();
      }
   }

   public static PartialStack parse(String string) throws CommandSyntaxException {
      return parse(new StringReader(string));
   }

   public static PartialStack parse(StringReader reader) throws CommandSyntaxException {
      return of(PartialItem.parse(reader), PartialCompoundNbt.parse(reader));
   }

   public static class Adapter implements ISimpleAdapter<PartialStack, Tag, JsonElement> {
      public void writeBits(PartialStack value, BitBuffer buffer) {
         buffer.writeBoolean(value != null);
         if (value != null) {
            Adapters.PARTIAL_ITEM.writeBits(value.item, buffer);
            Adapters.PARTIAL_NBT.writeBits(value.nbt, buffer);
         }
      }

      @Override
      public Optional<PartialStack> readBits(BitBuffer buffer) {
         return buffer.readBoolean()
            ? Optional.of(new PartialStack(Adapters.PARTIAL_ITEM.readBits(buffer).orElse(null), Adapters.PARTIAL_NBT.readBits(buffer).orElse(null)))
            : Optional.empty();
      }

      public Optional<Tag> writeNbt(PartialStack value) {
         if (value == null) {
            return Optional.empty();
         }

         CompoundTag nbt = new CompoundTag();
         Adapters.PARTIAL_ITEM.writeNbt(value.item).ifPresent(tag -> nbt.put("item", tag));
         Adapters.PARTIAL_NBT.writeNbt(value.nbt).ifPresent(tag -> nbt.put("nbt", tag));
         return Optional.of(nbt);
      }

      @Override
      public Optional<PartialStack> readNbt(Tag nbt) {
         if (nbt instanceof CompoundTag compound) {
            PartialItem item = Adapters.PARTIAL_ITEM.readNbt(compound.get("item")).orElseThrow();
            PartialCompoundNbt tag = Adapters.PARTIAL_NBT.readNbt(compound.get("nbt")).orElseGet(PartialCompoundNbt::empty);
            return Optional.of(PartialStack.of(item, tag));
         } else {
            return Optional.empty();
         }
      }

      public Optional<JsonElement> writeJson(PartialStack value) {
         if (value == null) {
            return Optional.empty();
         }

         JsonObject json = new JsonObject();
         Adapters.PARTIAL_ITEM.writeJson(value.item).ifPresent(tag -> json.add("item", tag));
         Adapters.PARTIAL_NBT.writeJson(value.nbt).ifPresent(tag -> json.add("nbt", tag));
         return Optional.of(json);
      }

      @Override
      public Optional<PartialStack> readJson(JsonElement json) {
         if (json instanceof JsonObject object) {
            PartialItem item = Adapters.PARTIAL_ITEM.readJson(object.get("item")).orElseThrow();
            PartialCompoundNbt tag = Adapters.PARTIAL_NBT.readJson(object.get("nbt")).orElseGet(PartialCompoundNbt::empty);
            return Optional.of(PartialStack.of(item, tag));
         } else {
            return Optional.empty();
         }
      }
   }
}
