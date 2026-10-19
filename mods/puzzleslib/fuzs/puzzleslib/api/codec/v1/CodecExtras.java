package fuzs.puzzleslib.api.codec.v1;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class CodecExtras {
   public static final Codec<NonNullList<ItemStack>> NON_NULL_ITEM_STACK_LIST_CODEC = nonNullList(
      ItemStack.CODEC, Predicate.not(ItemStack::isEmpty), ItemStack.EMPTY
   );

   private CodecExtras() {
   }

   public static <T> Codec<NonNullList<T>> nonNullList(Codec<T> codec, Predicate<T> filter, @Nullable T defaultValue) {
      return RecordCodecBuilder.create(
         instance -> instance.group(
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("size").forGetter(NonNullList::size),
               Codec.mapPair(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("slot"), codec.fieldOf("item"))
                  .codec()
                  .listOf()
                  .fieldOf("items")
                  .forGetter(
                     items -> IntStream.range(0, items.size())
                        .mapToObj(index -> new Pair(index, items.get(index)))
                        .filter(pair -> filter.test((T)pair.getSecond()))
                        .toList()
                  )
            )
            .apply(instance, (size, items) -> {
               NonNullList<T> nonNullList = defaultValue != null ? NonNullList.withSize(size, defaultValue) : NonNullList.createWithCapacity(size);

               for (Pair<Integer, T> pair : items) {
                  nonNullList.set((Integer)pair.getFirst(), pair.getSecond());
               }

               return nonNullList;
            })
      );
   }

   public static Function<Tag, DataResult<CompoundTag>> mapCompoundTag() {
      return tag -> tag instanceof CompoundTag compoundTag ? DataResult.success(compoundTag) : DataResult.error(() -> "Not a compound tag: " + tag);
   }
}
