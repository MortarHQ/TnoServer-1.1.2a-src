package artifacts.util;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class ModCodecs {
   public static <T> StreamCodec<ByteBuf, TagKey<T>> tagKeyStreamCodec(ResourceKey<? extends Registry<T>> registry) {
      return ResourceLocation.STREAM_CODEC.map(id -> TagKey.create(registry, id), TagKey::location);
   }

   public static <T> Codec<T> xorAlternative(Codec<T> codec, Codec<T> alternative) {
      return new ModCodecs.XorAlternativeCodec<>(codec, alternative);
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
      StreamCodec<? super B, T1> codec1,
      Function<C, T1> getter1,
      StreamCodec<? super B, T2> codec2,
      Function<C, T2> getter2,
      StreamCodec<? super B, T3> codec3,
      Function<C, T3> getter3,
      StreamCodec<? super B, T4> codec4,
      Function<C, T4> getter4,
      StreamCodec<? super B, T5> codec5,
      Function<C, T5> getter5,
      StreamCodec<? super B, T6> codec6,
      Function<C, T6> getter6,
      StreamCodec<? super B, T7> codec7,
      Function<C, T7> getter7,
      Function7<T1, T2, T3, T4, T5, T6, T7, C> f
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B b) {
            T1 t1 = (T1)codec1.decode(b);
            T2 t2 = (T2)codec2.decode(b);
            T3 t3 = (T3)codec3.decode(b);
            T4 t4 = (T4)codec4.decode(b);
            T5 t5 = (T5)codec5.decode(b);
            T6 t6 = (T6)codec6.decode(b);
            T7 t7 = (T7)codec7.decode(b);
            return (C)f.apply(t1, t2, t3, t4, t5, t6, t7);
         }

         public void encode(B b, C c) {
            codec1.encode(b, getter1.apply(c));
            codec2.encode(b, getter2.apply(c));
            codec3.encode(b, getter3.apply(c));
            codec4.encode(b, getter4.apply(c));
            codec5.encode(b, getter5.apply(c));
            codec6.encode(b, getter6.apply(c));
            codec7.encode(b, getter7.apply(c));
         }
      };
   }

   private record XorAlternativeCodec<T>(Codec<T> codec, Codec<T> alternative) implements Codec<T> {
      public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
         DataResult<Pair<T, T1>> result = this.codec.decode(ops, input);
         return result.error().isEmpty() ? result : this.alternative.decode(ops, input);
      }

      public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
         DataResult<T1> result = this.codec.encode(input, ops, prefix);
         return result.error().isEmpty() ? result : this.alternative.encode(input, ops, prefix);
      }

      @Override
      public String toString() {
         return "Alternative[" + this.codec + ", " + this.alternative + "]";
      }
   }
}
