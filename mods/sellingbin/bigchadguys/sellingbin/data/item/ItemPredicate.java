package bigchadguys.sellingbin.data.item;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.adapter.array.ArrayAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.data.nbt.PartialCompoundNbt;
import bigchadguys.sellingbin.data.tile.OrItemPredicate;
import bigchadguys.sellingbin.util.ItemUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Optional;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ItemPredicate {
   ItemPredicate FALSE = (item, nbt) -> false;
   ItemPredicate TRUE = (item, nbt) -> true;

   boolean test(PartialItem var1, PartialCompoundNbt var2);

   default void validate(String path) {
   }

   default boolean test(PartialStack stack) {
      return this.test(stack.getItem(), stack.getNbt());
   }

   default boolean test(ItemStack stack) {
      return this.test(PartialItem.of(stack.getItem()), PartialCompoundNbt.of(ItemUtils.getNbt(stack)));
   }

   static Optional<ItemPredicate> of(String string, boolean logErrors) {
      if (string.isEmpty()) {
         return Optional.of(TRUE);
      }

      return (switch (string.charAt(0)) {
         case '#' -> PartialItemTag.parse(string, logErrors);
         case '@' -> PartialItemGroup.parse(string, logErrors);
         default -> PartialStack.parse(string, logErrors);
      }).map(o -> (ItemPredicate)o);
   }

   class Adapter implements ISimpleAdapter<ItemPredicate, Tag, JsonElement> {
      private static ArrayAdapter<ItemPredicate> LIST = Adapters.ofArray(ItemPredicate[]::new, new ItemPredicate.Adapter());

      public void writeBits(ItemPredicate value, BitBuffer buffer) {
         buffer.writeBoolean(value == null);
         if (value != null) {
            if (value instanceof OrItemPredicate or) {
               buffer.writeBoolean(true);
               LIST.writeBits(or.getChildren(), buffer);
            } else {
               buffer.writeBoolean(false);
               Adapters.UTF_8.writeBits(value.toString(), buffer);
            }
         }
      }

      @Override
      public final Optional<ItemPredicate> readBits(BitBuffer buffer) {
         if (buffer.readBoolean()) {
            return Optional.empty();
         } else {
            return buffer.readBoolean()
               ? LIST.readBits(buffer).map(OrItemPredicate::new)
               : Adapters.UTF_8.readBits(buffer).map(string -> ItemPredicate.of(string, true).orElse(ItemPredicate.FALSE));
         }
      }

      public Optional<Tag> writeNbt(ItemPredicate value) {
         if (value == null) {
            return Optional.empty();
         } else {
            return value instanceof OrItemPredicate or ? LIST.writeNbt(or.getChildren()) : Optional.of(StringTag.valueOf(value.toString()));
         }
      }

      @Override
      public Optional<ItemPredicate> readNbt(Tag nbt) {
         if (nbt == null) {
            return Optional.empty();
         } else if (nbt instanceof ListTag list) {
            return LIST.readNbt(list).map(OrItemPredicate::new);
         } else {
            return nbt instanceof StringTag string ? Optional.of(ItemPredicate.of(string.getAsString(), true).orElse(ItemPredicate.FALSE)) : Optional.empty();
         }
      }

      public Optional<JsonElement> writeJson(ItemPredicate value) {
         if (value == null) {
            return Optional.empty();
         } else {
            return value instanceof OrItemPredicate or ? LIST.writeJson(or.getChildren()) : Optional.of(new JsonPrimitive(value.toString()));
         }
      }

      @Override
      public Optional<ItemPredicate> readJson(JsonElement json) {
         if (json == null) {
            return Optional.empty();
         } else if (json instanceof JsonArray array) {
            return LIST.readJson(array).map(OrItemPredicate::new);
         } else {
            return json instanceof JsonPrimitive primitive && primitive.isString()
               ? Optional.of(ItemPredicate.of(json.getAsString(), true).orElse(ItemPredicate.FALSE))
               : Optional.empty();
         }
      }
   }
}
