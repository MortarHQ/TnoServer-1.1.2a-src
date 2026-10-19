package com.finderfeed.fdlib.util;

import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function11;
import com.mojang.datafixers.util.Function12;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class FDByteBufCodecs {
   public static final StreamCodec<? super FriendlyByteBuf, ResourceLocation> RESOURCE_LOCATION = StreamCodec.of(
      FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::readResourceLocation
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, UUID> UUID = new StreamCodec<RegistryFriendlyByteBuf, UUID>() {
      public UUID decode(RegistryFriendlyByteBuf buf) {
         return buf.readUUID();
      }

      public void encode(RegistryFriendlyByteBuf buf, UUID uuid) {
         buf.writeUUID(uuid);
      }
   };
   public static final StreamCodec<ByteBuf, Vec3> VEC3 = new StreamCodec<ByteBuf, Vec3>() {
      public Vec3 decode(ByteBuf buf) {
         return new Vec3(buf.readFloat(), buf.readFloat(), buf.readFloat());
      }

      public void encode(ByteBuf buf, Vec3 vec3) {
         buf.writeFloat((float)vec3.x);
         buf.writeFloat((float)vec3.y);
         buf.writeFloat((float)vec3.z);
      }
   };
   public static final StreamCodec<ByteBuf, BlockPos> BLOCK_POS = new StreamCodec<ByteBuf, BlockPos>() {
      public BlockPos decode(ByteBuf buf) {
         return new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
      }

      public void encode(ByteBuf buf, BlockPos pos) {
         buf.writeInt(pos.getX());
         buf.writeInt(pos.getY());
         buf.writeInt(pos.getZ());
      }
   };
   public static final StreamCodec<ByteBuf, FDColor> COLOR = new StreamCodec<ByteBuf, FDColor>() {
      public FDColor decode(ByteBuf buf) {
         return FDColor.decode(buf.readInt());
      }

      public void encode(ByteBuf buf, FDColor color) {
         buf.writeInt(color.encode());
      }
   };

   public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> c1,
      final Function<C, T1> f1,
      final StreamCodec<? super B, T2> c2,
      final Function<C, T2> f2,
      final StreamCodec<? super B, T3> c3,
      final Function<C, T3> f3,
      final StreamCodec<? super B, T4> c4,
      final Function<C, T4> f4,
      final StreamCodec<? super B, T5> c5,
      final Function<C, T5> f5,
      final StreamCodec<? super B, T6> c6,
      final Function<C, T6> f6,
      final StreamCodec<? super B, T7> c7,
      final Function<C, T7> f7,
      final Function7<T1, T2, T3, T4, T5, T6, T7, C> constructor
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B p_330310_) {
            T1 t1 = (T1)c1.decode(p_330310_);
            T2 t2 = (T2)c2.decode(p_330310_);
            T3 t3 = (T3)c3.decode(p_330310_);
            T4 t4 = (T4)c4.decode(p_330310_);
            T5 t5 = (T5)c5.decode(p_330310_);
            T6 t6 = (T6)c6.decode(p_330310_);
            T7 t7 = (T7)c7.decode(p_330310_);
            return (C)constructor.apply(t1, t2, t3, t4, t5, t6, t7);
         }

         public void encode(B buf, C p_331912_) {
            c1.encode(buf, f1.apply(p_331912_));
            c2.encode(buf, f2.apply(p_331912_));
            c3.encode(buf, f3.apply(p_331912_));
            c4.encode(buf, f4.apply(p_331912_));
            c5.encode(buf, f5.apply(p_331912_));
            c6.encode(buf, f6.apply(p_331912_));
            c7.encode(buf, f7.apply(p_331912_));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> c1,
      final Function<C, T1> f1,
      final StreamCodec<? super B, T2> c2,
      final Function<C, T2> f2,
      final StreamCodec<? super B, T3> c3,
      final Function<C, T3> f3,
      final StreamCodec<? super B, T4> c4,
      final Function<C, T4> f4,
      final StreamCodec<? super B, T5> c5,
      final Function<C, T5> f5,
      final StreamCodec<? super B, T6> c6,
      final Function<C, T6> f6,
      final StreamCodec<? super B, T7> c7,
      final Function<C, T7> f7,
      final StreamCodec<? super B, T8> c8,
      final Function<C, T8> f8,
      final Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> constructor
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B p_330310_) {
            T1 t1 = (T1)c1.decode(p_330310_);
            T2 t2 = (T2)c2.decode(p_330310_);
            T3 t3 = (T3)c3.decode(p_330310_);
            T4 t4 = (T4)c4.decode(p_330310_);
            T5 t5 = (T5)c5.decode(p_330310_);
            T6 t6 = (T6)c6.decode(p_330310_);
            T7 t7 = (T7)c7.decode(p_330310_);
            T8 t8 = (T8)c8.decode(p_330310_);
            return (C)constructor.apply(t1, t2, t3, t4, t5, t6, t7, t8);
         }

         public void encode(B buf, C p_331912_) {
            c1.encode(buf, f1.apply(p_331912_));
            c2.encode(buf, f2.apply(p_331912_));
            c3.encode(buf, f3.apply(p_331912_));
            c4.encode(buf, f4.apply(p_331912_));
            c5.encode(buf, f5.apply(p_331912_));
            c6.encode(buf, f6.apply(p_331912_));
            c7.encode(buf, f7.apply(p_331912_));
            c8.encode(buf, f8.apply(p_331912_));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> c1,
      final Function<C, T1> f1,
      final StreamCodec<? super B, T2> c2,
      final Function<C, T2> f2,
      final StreamCodec<? super B, T3> c3,
      final Function<C, T3> f3,
      final StreamCodec<? super B, T4> c4,
      final Function<C, T4> f4,
      final StreamCodec<? super B, T5> c5,
      final Function<C, T5> f5,
      final StreamCodec<? super B, T6> c6,
      final Function<C, T6> f6,
      final StreamCodec<? super B, T7> c7,
      final Function<C, T7> f7,
      final StreamCodec<? super B, T8> c8,
      final Function<C, T8> f8,
      final StreamCodec<? super B, T9> c9,
      final Function<C, T9> f9,
      final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> constructor
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B p_330310_) {
            T1 t1 = (T1)c1.decode(p_330310_);
            T2 t2 = (T2)c2.decode(p_330310_);
            T3 t3 = (T3)c3.decode(p_330310_);
            T4 t4 = (T4)c4.decode(p_330310_);
            T5 t5 = (T5)c5.decode(p_330310_);
            T6 t6 = (T6)c6.decode(p_330310_);
            T7 t7 = (T7)c7.decode(p_330310_);
            T8 t8 = (T8)c8.decode(p_330310_);
            T9 t9 = (T9)c9.decode(p_330310_);
            return (C)constructor.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
         }

         public void encode(B buf, C p_331912_) {
            c1.encode(buf, f1.apply(p_331912_));
            c2.encode(buf, f2.apply(p_331912_));
            c3.encode(buf, f3.apply(p_331912_));
            c4.encode(buf, f4.apply(p_331912_));
            c5.encode(buf, f5.apply(p_331912_));
            c6.encode(buf, f6.apply(p_331912_));
            c7.encode(buf, f7.apply(p_331912_));
            c8.encode(buf, f8.apply(p_331912_));
            c9.encode(buf, f9.apply(p_331912_));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> c1,
      final Function<C, T1> f1,
      final StreamCodec<? super B, T2> c2,
      final Function<C, T2> f2,
      final StreamCodec<? super B, T3> c3,
      final Function<C, T3> f3,
      final StreamCodec<? super B, T4> c4,
      final Function<C, T4> f4,
      final StreamCodec<? super B, T5> c5,
      final Function<C, T5> f5,
      final StreamCodec<? super B, T6> c6,
      final Function<C, T6> f6,
      final StreamCodec<? super B, T7> c7,
      final Function<C, T7> f7,
      final StreamCodec<? super B, T8> c8,
      final Function<C, T8> f8,
      final StreamCodec<? super B, T9> c9,
      final Function<C, T9> f9,
      final StreamCodec<? super B, T10> c10,
      final Function<C, T10> f10,
      final Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, C> constructor
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B p_330310_) {
            T1 t1 = (T1)c1.decode(p_330310_);
            T2 t2 = (T2)c2.decode(p_330310_);
            T3 t3 = (T3)c3.decode(p_330310_);
            T4 t4 = (T4)c4.decode(p_330310_);
            T5 t5 = (T5)c5.decode(p_330310_);
            T6 t6 = (T6)c6.decode(p_330310_);
            T7 t7 = (T7)c7.decode(p_330310_);
            T8 t8 = (T8)c8.decode(p_330310_);
            T9 t9 = (T9)c9.decode(p_330310_);
            T10 t10 = (T10)c10.decode(p_330310_);
            return (C)constructor.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
         }

         public void encode(B buf, C p_331912_) {
            c1.encode(buf, f1.apply(p_331912_));
            c2.encode(buf, f2.apply(p_331912_));
            c3.encode(buf, f3.apply(p_331912_));
            c4.encode(buf, f4.apply(p_331912_));
            c5.encode(buf, f5.apply(p_331912_));
            c6.encode(buf, f6.apply(p_331912_));
            c7.encode(buf, f7.apply(p_331912_));
            c8.encode(buf, f8.apply(p_331912_));
            c9.encode(buf, f9.apply(p_331912_));
            c10.encode(buf, f10.apply(p_331912_));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> c1,
      final Function<C, T1> f1,
      final StreamCodec<? super B, T2> c2,
      final Function<C, T2> f2,
      final StreamCodec<? super B, T3> c3,
      final Function<C, T3> f3,
      final StreamCodec<? super B, T4> c4,
      final Function<C, T4> f4,
      final StreamCodec<? super B, T5> c5,
      final Function<C, T5> f5,
      final StreamCodec<? super B, T6> c6,
      final Function<C, T6> f6,
      final StreamCodec<? super B, T7> c7,
      final Function<C, T7> f7,
      final StreamCodec<? super B, T8> c8,
      final Function<C, T8> f8,
      final StreamCodec<? super B, T9> c9,
      final Function<C, T9> f9,
      final StreamCodec<? super B, T10> c10,
      final Function<C, T10> f10,
      final StreamCodec<? super B, T11> c11,
      final Function<C, T11> f11,
      final Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, C> constructor
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B p_330310_) {
            T1 t1 = (T1)c1.decode(p_330310_);
            T2 t2 = (T2)c2.decode(p_330310_);
            T3 t3 = (T3)c3.decode(p_330310_);
            T4 t4 = (T4)c4.decode(p_330310_);
            T5 t5 = (T5)c5.decode(p_330310_);
            T6 t6 = (T6)c6.decode(p_330310_);
            T7 t7 = (T7)c7.decode(p_330310_);
            T8 t8 = (T8)c8.decode(p_330310_);
            T9 t9 = (T9)c9.decode(p_330310_);
            T10 t10 = (T10)c10.decode(p_330310_);
            T11 t11 = (T11)c11.decode(p_330310_);
            return (C)constructor.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
         }

         public void encode(B buf, C p_331912_) {
            c1.encode(buf, f1.apply(p_331912_));
            c2.encode(buf, f2.apply(p_331912_));
            c3.encode(buf, f3.apply(p_331912_));
            c4.encode(buf, f4.apply(p_331912_));
            c5.encode(buf, f5.apply(p_331912_));
            c6.encode(buf, f6.apply(p_331912_));
            c7.encode(buf, f7.apply(p_331912_));
            c8.encode(buf, f8.apply(p_331912_));
            c9.encode(buf, f9.apply(p_331912_));
            c10.encode(buf, f10.apply(p_331912_));
            c11.encode(buf, f11.apply(p_331912_));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> StreamCodec<B, C> composite(
      final StreamCodec<? super B, T1> c1,
      final Function<C, T1> f1,
      final StreamCodec<? super B, T2> c2,
      final Function<C, T2> f2,
      final StreamCodec<? super B, T3> c3,
      final Function<C, T3> f3,
      final StreamCodec<? super B, T4> c4,
      final Function<C, T4> f4,
      final StreamCodec<? super B, T5> c5,
      final Function<C, T5> f5,
      final StreamCodec<? super B, T6> c6,
      final Function<C, T6> f6,
      final StreamCodec<? super B, T7> c7,
      final Function<C, T7> f7,
      final StreamCodec<? super B, T8> c8,
      final Function<C, T8> f8,
      final StreamCodec<? super B, T9> c9,
      final Function<C, T9> f9,
      final StreamCodec<? super B, T10> c10,
      final Function<C, T10> f10,
      final StreamCodec<? super B, T11> c11,
      final Function<C, T11> f11,
      final StreamCodec<? super B, T12> c12,
      final Function<C, T12> f12,
      final Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, C> constructor
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B p_330310_) {
            T1 t1 = (T1)c1.decode(p_330310_);
            T2 t2 = (T2)c2.decode(p_330310_);
            T3 t3 = (T3)c3.decode(p_330310_);
            T4 t4 = (T4)c4.decode(p_330310_);
            T5 t5 = (T5)c5.decode(p_330310_);
            T6 t6 = (T6)c6.decode(p_330310_);
            T7 t7 = (T7)c7.decode(p_330310_);
            T8 t8 = (T8)c8.decode(p_330310_);
            T9 t9 = (T9)c9.decode(p_330310_);
            T10 t10 = (T10)c10.decode(p_330310_);
            T11 t11 = (T11)c11.decode(p_330310_);
            T12 t12 = (T12)c12.decode(p_330310_);
            return (C)constructor.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
         }

         public void encode(B buf, C p_331912_) {
            c1.encode(buf, f1.apply(p_331912_));
            c2.encode(buf, f2.apply(p_331912_));
            c3.encode(buf, f3.apply(p_331912_));
            c4.encode(buf, f4.apply(p_331912_));
            c5.encode(buf, f5.apply(p_331912_));
            c6.encode(buf, f6.apply(p_331912_));
            c7.encode(buf, f7.apply(p_331912_));
            c8.encode(buf, f8.apply(p_331912_));
            c9.encode(buf, f9.apply(p_331912_));
            c10.encode(buf, f10.apply(p_331912_));
            c11.encode(buf, f11.apply(p_331912_));
            c12.encode(buf, f12.apply(p_331912_));
         }
      };
   }
}
