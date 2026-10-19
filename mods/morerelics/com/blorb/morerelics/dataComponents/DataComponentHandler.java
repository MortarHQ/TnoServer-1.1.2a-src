package com.blorb.morerelics.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

public class DataComponentHandler {
   public static final Codec<IntegerCounter> INTEGER_COUNTER_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.INT.fieldOf("current").forGetter(IntegerCounter::current)).apply(instance, IntegerCounter::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, IntegerCounter> INTEGER_COUNTER_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, IntegerCounter::current, IntegerCounter::new
   );
   public static final Codec<VectorDequeHolder> VEC_DEQUE_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.INT.fieldOf("length").forGetter(VectorDequeHolder::getLength)).apply(instance, VectorDequeHolder::new)
   );
   public static final Codec<BoolHolder> BOOL_HOLDER_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.BOOL.fieldOf("bool").forGetter(BoolHolder::getBool)).apply(instance, BoolHolder::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, BoolHolder> BOOL_HOLDER_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, BoolHolder::getBool, BoolHolder::new
   );
   public static final Codec<StarCounter> STAR_COUNTER_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("count").forGetter(StarCounter::count), Codec.INT.fieldOf("timerCurrent").forGetter(StarCounter::timerCurrent)
         )
         .apply(instance, StarCounter::new)
   );
   public static final StreamCodec<ByteBuf, StarCounter> STAR_COUNTER_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, StarCounter::count, ByteBufCodecs.INT, StarCounter::timerCurrent, StarCounter::new
   );
   public static final Codec<LocationHolder> LOCATION_HOLDER_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(LocationHolder::getX),
            Codec.DOUBLE.fieldOf("y").forGetter(LocationHolder::getY),
            Codec.DOUBLE.fieldOf("z").forGetter(LocationHolder::getZ)
         )
         .apply(instance, LocationHolder::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, LocationHolder> LOCATION_HOLDER_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.DOUBLE, LocationHolder::getX, ByteBufCodecs.DOUBLE, LocationHolder::getY, ByteBufCodecs.DOUBLE, LocationHolder::getZ, LocationHolder::new
   );
   public static final Codec<DoubleCounter> DOUBLE_HOLDER_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.DOUBLE.fieldOf("x").forGetter(DoubleCounter::getCurrent)).apply(instance, DoubleCounter::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, DoubleCounter> DOUBLE_HOLDER_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.DOUBLE, DoubleCounter::getCurrent, DoubleCounter::new
   );
   public static final DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "morerelics");
   public static final Supplier<DataComponentType<LocationHolder>> LOCATION_HOLDER = REGISTRAR.registerComponentType(
      "location", builder -> builder.persistent(LOCATION_HOLDER_CODEC).networkSynchronized(LOCATION_HOLDER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<BoolHolder>> BOOL_HOLDER = REGISTRAR.registerComponentType(
      "bool_holder", builder -> builder.persistent(BOOL_HOLDER_CODEC).networkSynchronized(BOOL_HOLDER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<VectorDequeHolder>> VECTOR_DEQUE_HOLDER = REGISTRAR.registerComponentType(
      "vector_deque_holder", builder -> builder.persistent(VEC_DEQUE_CODEC)
   );
   public static final Supplier<DataComponentType<IntegerCounter>> INT_COUNTER = REGISTRAR.registerComponentType(
      "int_counter", builder -> builder.persistent(INTEGER_COUNTER_CODEC).networkSynchronized(INTEGER_COUNTER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<DoubleCounter>> DOUBLE_COUNTER = REGISTRAR.registerComponentType(
      "double_holder", builder -> builder.persistent(DOUBLE_HOLDER_CODEC).networkSynchronized(DOUBLE_HOLDER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<IntegerCounter>> ALT_INT_COUNTER = REGISTRAR.registerComponentType(
      "alt_int_counter", builder -> builder.persistent(INTEGER_COUNTER_CODEC).networkSynchronized(INTEGER_COUNTER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<IntegerCounter>> ALT_ALT_INT_COUNTER = REGISTRAR.registerComponentType(
      "alt_alt_int_counter", builder -> builder.persistent(INTEGER_COUNTER_CODEC).networkSynchronized(INTEGER_COUNTER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<IntegerCounter>> ALT_ALT_ALT_INT_COUNTER = REGISTRAR.registerComponentType(
      "alt_alt_alt_int_counter", builder -> builder.persistent(INTEGER_COUNTER_CODEC).networkSynchronized(INTEGER_COUNTER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<LocationHolder>> THREE_INT_HOLDER = REGISTRAR.registerComponentType(
      "three_int_holder", builder -> builder.persistent(LOCATION_HOLDER_CODEC).networkSynchronized(LOCATION_HOLDER_STREAM_CODEC)
   );
   public static final Supplier<DataComponentType<StarCounter>> STAR_COUNTER = REGISTRAR.registerComponentType(
      "star", builder -> builder.persistent(STAR_COUNTER_CODEC).networkSynchronized(STAR_COUNTER_STREAM_CODEC)
   );

   public static BoolHolder getBoolHolder(ItemStack stack) {
      BoolHolder dataComp = (BoolHolder)stack.get(BOOL_HOLDER.get());
      if (dataComp == null) {
         BoolHolder holder = new BoolHolder(false);
         stack.set(BOOL_HOLDER.get(), holder);
         return holder;
      } else {
         return dataComp;
      }
   }

   public static VectorDequeHolder getVecDeque(ItemStack stack) {
      VectorDequeHolder dataComp = (VectorDequeHolder)stack.get(VECTOR_DEQUE_HOLDER.get());
      if (dataComp == null) {
         VectorDequeHolder holder = new VectorDequeHolder(10);
         stack.set(VECTOR_DEQUE_HOLDER.get(), holder);
         return holder;
      } else {
         return dataComp;
      }
   }

   public static IntegerCounter getIntegerCounter(ItemStack stack) {
      IntegerCounter dataComp = (IntegerCounter)stack.get(INT_COUNTER.get());
      if (dataComp == null) {
         IntegerCounter counter = new IntegerCounter(0);
         stack.set(INT_COUNTER.get(), counter);
         return counter;
      } else {
         return dataComp;
      }
   }

   public static IntegerCounter getAltIntegerCounter(ItemStack stack) {
      IntegerCounter dataComp = (IntegerCounter)stack.get(ALT_INT_COUNTER.get());
      if (dataComp == null) {
         IntegerCounter counter = new IntegerCounter(0);
         stack.set(ALT_INT_COUNTER.get(), counter);
         return counter;
      } else {
         return dataComp;
      }
   }

   public static DoubleCounter getDoubleCounter(ItemStack stack) {
      DoubleCounter dataComp = (DoubleCounter)stack.get(DOUBLE_COUNTER.get());
      if (dataComp == null) {
         DoubleCounter counter = new DoubleCounter(0.0);
         stack.set(DOUBLE_COUNTER.get(), counter);
         return counter;
      } else {
         return dataComp;
      }
   }

   public static IntegerCounter getAltAltIntegerCounter(ItemStack stack) {
      IntegerCounter dataComp = (IntegerCounter)stack.get(ALT_ALT_INT_COUNTER.get());
      if (dataComp == null) {
         IntegerCounter counter = new IntegerCounter(0);
         stack.set(ALT_ALT_INT_COUNTER.get(), counter);
         return counter;
      } else {
         return dataComp;
      }
   }

   public static IntegerCounter getAltAltAltIntegerCounter(ItemStack stack) {
      IntegerCounter dataComp = (IntegerCounter)stack.get(ALT_ALT_ALT_INT_COUNTER.get());
      if (dataComp == null) {
         IntegerCounter counter = new IntegerCounter(0);
         stack.set(ALT_ALT_ALT_INT_COUNTER.get(), counter);
         return counter;
      } else {
         return dataComp;
      }
   }

   public static StarCounter getStarCounter(ItemStack stack) {
      StarCounter dataComp = (StarCounter)stack.get(STAR_COUNTER.get());
      if (dataComp == null) {
         StarCounter counter = new StarCounter(0, 0);
         stack.set(STAR_COUNTER.get(), counter);
         return counter;
      } else {
         return dataComp;
      }
   }

   public static LocationHolder getRandomLocation(ItemStack stack) {
      LocationHolder dataComp = (LocationHolder)stack.get(LOCATION_HOLDER.get());
      if (dataComp == null) {
         Random rand = new Random();
         LocationHolder holder = new LocationHolder(rand.nextInt(2001) - 1000, rand.nextInt(20) + 300, rand.nextInt(2001) - 1000);
         stack.set(LOCATION_HOLDER.get(), holder);
         return holder;
      } else {
         return dataComp;
      }
   }

   public static LocationHolder getLocation(ItemStack stack) {
      LocationHolder dataComp = (LocationHolder)stack.get(LOCATION_HOLDER.get());
      if (dataComp == null) {
         new Random();
         LocationHolder holder = new LocationHolder(0.0, 0.0, 0.0);
         stack.set(LOCATION_HOLDER.get(), holder);
         return holder;
      } else {
         return dataComp;
      }
   }

   public static LocationHolder getTripleStorage(ItemStack stack) {
      LocationHolder dataComp = (LocationHolder)stack.get(THREE_INT_HOLDER.get());
      if (dataComp == null) {
         new Random();
         LocationHolder holder = new LocationHolder(0.0, 0.0, 0.0);
         stack.set(THREE_INT_HOLDER.get(), holder);
         return holder;
      } else {
         return dataComp;
      }
   }
}
