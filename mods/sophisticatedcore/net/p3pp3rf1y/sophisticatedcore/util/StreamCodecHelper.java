package net.p3pp3rf1y.sophisticatedcore.util;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StreamCodecHelper {
   public static final StreamCodec<ByteBuf, Vec3> VEC3 = new StreamCodec<ByteBuf, Vec3>() {
      public Vec3 decode(ByteBuf buffer) {
         return new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
      }

      public void encode(ByteBuf buffer, Vec3 value) {
         buffer.writeDouble(value.x);
         buffer.writeDouble(value.y);
         buffer.writeDouble(value.z);
      }
   };
   public static final StreamCodec<ByteBuf, BlockState> BLOCKSTATE = new StreamCodec<ByteBuf, BlockState>() {
      public void encode(ByteBuf buffer, BlockState state) {
         VarInt.write(buffer, Block.getId(state));
      }

      public BlockState decode(ByteBuf pBuffer) {
         return Block.stateById(VarInt.read(pBuffer));
      }
   };

   private StreamCodecHelper() {
   }

   public static <B extends FriendlyByteBuf, T> StreamCodec<B, TagKey<T>> ofTagkey(final ResourceKey<? extends Registry<T>> registry) {
      return new StreamCodec<B, TagKey<T>>() {
         public void encode(B buffer, TagKey<T> value) {
            buffer.writeResourceLocation(value.location());
         }

         public TagKey<T> decode(B buffer) {
            return TagKey.create(registry, buffer.readResourceLocation());
         }
      };
   }

   public static <B extends ByteBuf, V> StreamCodec<B, V> ofNullable(final StreamCodec<B, V> streamCodec) {
      return new StreamCodec<B, V>() {
         @Nullable
         public V decode(B buf) {
            return (V)(buf.readBoolean() ? streamCodec.decode(buf) : null);
         }

         public void encode(B buf, @Nullable V value) {
            buf.writeBoolean(value != null);
            if (value != null) {
               streamCodec.encode(buf, value);
            }
         }
      };
   }

   public static <B extends ByteBuf, V> StreamCodec<B, V> singleton(final Supplier<V> instantiator) {
      return new StreamCodec<B, V>() {
         public V decode(B p_320376_) {
            return instantiator.get();
         }

         public void encode(B p_320158_, V p_320396_) {
         }
      };
   }

   public static <B extends ByteBuf, E, V extends Collection<E>> StreamCodec<B, V> ofCollection(
      final StreamCodec<B, E> elementStreamCodec, final Supplier<V> instantiator
   ) {
      return new StreamCodec<B, V>() {
         public V decode(B buf) {
            int size = buf.readInt();
            V collection = instantiator.get();

            for (int i = 0; i < size; i++) {
               collection.add((E)elementStreamCodec.decode(buf));
            }

            return collection;
         }

         public void encode(B buf, V collection) {
            buf.writeInt(collection.size());

            for (E element : collection) {
               elementStreamCodec.encode(buf, element);
            }
         }
      };
   }

   public static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> ofMap(
      final StreamCodec<? super B, K> keyStreamCodec, final StreamCodec<? super B, V> valueStreamCodec, final Supplier<M> instantiator
   ) {
      return new StreamCodec<B, M>() {
         public M decode(B buf) {
            int size = buf.readInt();
            M map = instantiator.get();

            for (int i = 0; i < size; i++) {
               map.put((K)keyStreamCodec.decode(buf), (V)valueStreamCodec.decode(buf));
            }

            return map;
         }

         public void encode(B buf, M map) {
            buf.writeInt(map.size());
            map.forEach((k, v) -> {
               keyStreamCodec.encode(buf, k);
               valueStreamCodec.encode(buf, v);
            });
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> pCodec1,
      final Function<C, T1> pGetter1,
      final StreamCodec<? super B, T2> pCodec2,
      final Function<C, T2> pGetter2,
      final StreamCodec<? super B, T3> pCodec3,
      final Function<C, T3> pGetter3,
      final StreamCodec<? super B, T4> pCodec4,
      final Function<C, T4> pGetter4,
      final StreamCodec<? super B, T5> pCodec5,
      final Function<C, T5> pGetter5,
      final StreamCodec<? super B, T6> pCodec6,
      final Function<C, T6> pGetter6,
      final StreamCodec<? super B, T7> pCodec7,
      final Function<C, T7> pGetter7,
      final Function7<T1, T2, T3, T4, T5, T6, T7, C> pFactory
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B buffer) {
            T1 t1 = (T1)pCodec1.decode(buffer);
            T2 t2 = (T2)pCodec2.decode(buffer);
            T3 t3 = (T3)pCodec3.decode(buffer);
            T4 t4 = (T4)pCodec4.decode(buffer);
            T5 t5 = (T5)pCodec5.decode(buffer);
            T6 t6 = (T6)pCodec6.decode(buffer);
            T7 t7 = (T7)pCodec7.decode(buffer);
            return (C)pFactory.apply(t1, t2, t3, t4, t5, t6, t7);
         }

         public void encode(B buffer, C value) {
            pCodec1.encode(buffer, pGetter1.apply(value));
            pCodec2.encode(buffer, pGetter2.apply(value));
            pCodec3.encode(buffer, pGetter3.apply(value));
            pCodec4.encode(buffer, pGetter4.apply(value));
            pCodec5.encode(buffer, pGetter5.apply(value));
            pCodec6.encode(buffer, pGetter6.apply(value));
            pCodec7.encode(buffer, pGetter7.apply(value));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> pCodec1,
      final Function<C, T1> pGetter1,
      final StreamCodec<? super B, T2> pCodec2,
      final Function<C, T2> pGetter2,
      final StreamCodec<? super B, T3> pCodec3,
      final Function<C, T3> pGetter3,
      final StreamCodec<? super B, T4> pCodec4,
      final Function<C, T4> pGetter4,
      final StreamCodec<? super B, T5> pCodec5,
      final Function<C, T5> pGetter5,
      final StreamCodec<? super B, T6> pCodec6,
      final Function<C, T6> pGetter6,
      final StreamCodec<? super B, T7> pCodec7,
      final Function<C, T7> pGetter7,
      final StreamCodec<? super B, T8> pCodec8,
      final Function<C, T8> pGetter8,
      final Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> pFactory
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B buffer) {
            T1 t1 = (T1)pCodec1.decode(buffer);
            T2 t2 = (T2)pCodec2.decode(buffer);
            T3 t3 = (T3)pCodec3.decode(buffer);
            T4 t4 = (T4)pCodec4.decode(buffer);
            T5 t5 = (T5)pCodec5.decode(buffer);
            T6 t6 = (T6)pCodec6.decode(buffer);
            T7 t7 = (T7)pCodec7.decode(buffer);
            T8 t8 = (T8)pCodec8.decode(buffer);
            return (C)pFactory.apply(t1, t2, t3, t4, t5, t6, t7, t8);
         }

         public void encode(B buffer, C value) {
            pCodec1.encode(buffer, pGetter1.apply(value));
            pCodec2.encode(buffer, pGetter2.apply(value));
            pCodec3.encode(buffer, pGetter3.apply(value));
            pCodec4.encode(buffer, pGetter4.apply(value));
            pCodec5.encode(buffer, pGetter5.apply(value));
            pCodec6.encode(buffer, pGetter6.apply(value));
            pCodec7.encode(buffer, pGetter7.apply(value));
            pCodec8.encode(buffer, pGetter8.apply(value));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> pCodec1,
      final Function<C, T1> pGetter1,
      final StreamCodec<? super B, T2> pCodec2,
      final Function<C, T2> pGetter2,
      final StreamCodec<? super B, T3> pCodec3,
      final Function<C, T3> pGetter3,
      final StreamCodec<? super B, T4> pCodec4,
      final Function<C, T4> pGetter4,
      final StreamCodec<? super B, T5> pCodec5,
      final Function<C, T5> pGetter5,
      final StreamCodec<? super B, T6> pCodec6,
      final Function<C, T6> pGetter6,
      final StreamCodec<? super B, T7> pCodec7,
      final Function<C, T7> pGetter7,
      final StreamCodec<? super B, T8> pCodec8,
      final Function<C, T8> pGetter8,
      final StreamCodec<? super B, T9> pCodec9,
      final Function<C, T9> pGetter9,
      final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> pFactory
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B buffer) {
            T1 t1 = (T1)pCodec1.decode(buffer);
            T2 t2 = (T2)pCodec2.decode(buffer);
            T3 t3 = (T3)pCodec3.decode(buffer);
            T4 t4 = (T4)pCodec4.decode(buffer);
            T5 t5 = (T5)pCodec5.decode(buffer);
            T6 t6 = (T6)pCodec6.decode(buffer);
            T7 t7 = (T7)pCodec7.decode(buffer);
            T8 t8 = (T8)pCodec8.decode(buffer);
            T9 t9 = (T9)pCodec9.decode(buffer);
            return (C)pFactory.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
         }

         public void encode(B buffer, C value) {
            pCodec1.encode(buffer, pGetter1.apply(value));
            pCodec2.encode(buffer, pGetter2.apply(value));
            pCodec3.encode(buffer, pGetter3.apply(value));
            pCodec4.encode(buffer, pGetter4.apply(value));
            pCodec5.encode(buffer, pGetter5.apply(value));
            pCodec6.encode(buffer, pGetter6.apply(value));
            pCodec7.encode(buffer, pGetter7.apply(value));
            pCodec8.encode(buffer, pGetter8.apply(value));
            pCodec9.encode(buffer, pGetter9.apply(value));
         }
      };
   }
}
