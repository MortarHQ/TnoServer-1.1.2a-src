package net.darkhax.bookshelf.common.api.util;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataHelper {
   public static <T> HolderSet<T> getTagOrEmpty(@Nullable Provider provider, ResourceKey<Registry<T>> registryKey, TagKey<T> tag) {
      if (provider != null) {
         Optional<Named<T>> optional = provider.lookupOrThrow(registryKey).get(tag);
         if (optional.isPresent()) {
            return (HolderSet<T>)optional.get();
         }
      }

      return HolderSet.direct(new Holder[0]);
   }

   public static ListTag subList(ListTag list, int from, int to) {
      if (list == null) {
         throw new IllegalStateException("The input list must not be null!");
      }

      if (from >= 0 && to <= list.size() && from <= to) {
         ListTag subList = new ListTag();

         for (int i = from; i < to; i++) {
            subList.add(list.get(i));
         }

         return subList;
      } else {
         throw new IndexOutOfBoundsException("Invalid range! from=" + from + " to=" + to + " size=" + list.size());
      }
   }

   public static ListTag containerSubList(ListTag list, Predicate<Integer> slots) {
      if (list == null) {
         throw new IllegalStateException("The input list must not be null!");
      }

      ListTag subList = new ListTag();

      for (Tag tag : list) {
         if (tag instanceof CompoundTag entry && entry.contains("Slot", 1) && slots.test(entry.getInt("Slot"))) {
            subList.add(tag);
         }
      }

      return subList;
   }

   public static <B extends ByteBuf, V> StreamCodec<B, Optional<V>> optionalStream(StreamCodec<B, V> streamCodec) {
      return StreamCodec.of((buf, val) -> {
         buf.writeBoolean(val.isPresent());
         val.ifPresent(v -> streamCodec.encode(buf, v));
      }, buf -> {
         if (buf.readBoolean()) {
            V val = (V)streamCodec.decode(buf);
            return Optional.of(val);
         } else {
            return Optional.empty();
         }
      });
   }

   public static <T extends Recipe<?>> RecipeSerializer<T> recipeSerializer(final MapCodec<T> codec, final StreamCodec<RegistryFriendlyByteBuf, T> stream) {
      return new RecipeSerializer<T>() {
         @NotNull
         public MapCodec<T> codec() {
            return codec;
         }

         @NotNull
         public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return stream;
         }
      };
   }
}
