package com.notunanancyowen.spears;

import com.mojang.datafixers.util.Function10;
import java.util.function.Function;
import net.minecraft.network.codec.StreamCodec;

public class PacketCodecHelper {
   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> StreamCodec<B, C> tuple(
      StreamCodec<? super B, T1> codec1,
      Function<C, T1> from1,
      StreamCodec<? super B, T2> codec2,
      Function<C, T2> from2,
      StreamCodec<? super B, T3> codec3,
      Function<C, T3> from3,
      StreamCodec<? super B, T4> codec4,
      Function<C, T4> from4,
      StreamCodec<? super B, T5> codec5,
      Function<C, T5> from5,
      StreamCodec<? super B, T6> codec6,
      Function<C, T6> from6,
      StreamCodec<? super B, T7> codec7,
      Function<C, T7> from7,
      StreamCodec<? super B, T8> codec8,
      Function<C, T8> from8,
      StreamCodec<? super B, T9> codec9,
      Function<C, T9> from9,
      StreamCodec<? super B, T10> codec10,
      Function<C, T10> from10,
      Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, C> to
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B object) {
            T1 object2 = (T1)codec1.decode(object);
            T2 object3 = (T2)codec2.decode(object);
            T3 object4 = (T3)codec3.decode(object);
            T4 object5 = (T4)codec4.decode(object);
            T5 object6 = (T5)codec5.decode(object);
            T6 object7 = (T6)codec6.decode(object);
            T7 object8 = (T7)codec7.decode(object);
            T8 object9 = (T8)codec8.decode(object);
            T9 object10 = (T9)codec9.decode(object);
            T10 object11 = (T10)codec10.decode(object);
            return (C)to.apply(object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
         }

         public void encode(B object, C object2) {
            codec1.encode(object, from1.apply(object2));
            codec2.encode(object, from2.apply(object2));
            codec3.encode(object, from3.apply(object2));
            codec4.encode(object, from4.apply(object2));
            codec5.encode(object, from5.apply(object2));
            codec6.encode(object, from6.apply(object2));
            codec7.encode(object, from7.apply(object2));
            codec8.encode(object, from8.apply(object2));
            codec9.encode(object, from9.apply(object2));
            codec10.encode(object, from10.apply(object2));
         }
      };
   }
}
