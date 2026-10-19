package com.teamresourceful.resourcefullib.common.nbt;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Experimental;

@Experimental
public class TagReader<I, T extends Tag> implements NbtReader<I, T> {
   private final byte type;
   private final Function<I, T> writer;
   private final BiConsumer<I, T> reader;
   @Nullable
   private final Supplier<T> defaultCreator;

   public TagReader(byte type, Function<I, T> writer, BiConsumer<I, T> reader, @Nullable Supplier<T> defaultCreator) {
      this.type = type;
      this.writer = writer;
      this.reader = reader;
      this.defaultCreator = defaultCreator;
   }

   public static <I> TagReader<I, ByteTag> byteTag(Function<I, Byte> writer, BiConsumer<I, Byte> reader) {
      return new TagReader<>((byte)1, i -> (T)ByteTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsByte()), null);
   }

   public static <I> TagReader<I, ByteTag> byteTag(Function<I, Byte> writer, BiConsumer<I, Byte> reader, byte defaultValue) {
      return new TagReader<>(
         (byte)1, i -> (T)ByteTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsByte()), () -> (T)ByteTag.valueOf(defaultValue)
      );
   }

   public static <I> TagReader<I, ShortTag> shortTag(Function<I, Short> writer, BiConsumer<I, Short> reader) {
      return new TagReader<>((byte)2, i -> (T)ShortTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsShort()), null);
   }

   public static <I> TagReader<I, ShortTag> shortTag(Function<I, Short> writer, BiConsumer<I, Short> reader, short defaultValue) {
      return new TagReader<>(
         (byte)2, i -> (T)ShortTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsShort()), () -> (T)ShortTag.valueOf(defaultValue)
      );
   }

   public static <I> TagReader<I, IntTag> intTag(Function<I, Integer> writer, BiConsumer<I, Integer> reader) {
      return new TagReader<>((byte)3, i -> (T)IntTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsInt()), null);
   }

   public static <I> TagReader<I, IntTag> intTag(Function<I, Integer> writer, BiConsumer<I, Integer> reader, int defaultValue) {
      return new TagReader<>((byte)3, i -> (T)IntTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsInt()), () -> (T)IntTag.valueOf(defaultValue));
   }

   public static <I> TagReader<I, LongTag> longTag(Function<I, Long> writer, BiConsumer<I, Long> reader) {
      return new TagReader<>((byte)4, i -> (T)LongTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsLong()), null);
   }

   public static <I> TagReader<I, LongTag> longTag(Function<I, Long> writer, BiConsumer<I, Long> reader, long defaultValue) {
      return new TagReader<>(
         (byte)4, i -> (T)LongTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsLong()), () -> (T)LongTag.valueOf(defaultValue)
      );
   }

   public static <I> TagReader<I, FloatTag> floatTag(Function<I, Float> writer, BiConsumer<I, Float> reader) {
      return new TagReader<>((byte)5, i -> (T)FloatTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsFloat()), null);
   }

   public static <I> TagReader<I, FloatTag> floatTag(Function<I, Float> writer, BiConsumer<I, Float> reader, float defaultValue) {
      return new TagReader<>(
         (byte)5, i -> (T)FloatTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsFloat()), () -> (T)FloatTag.valueOf(defaultValue)
      );
   }

   public static <I> TagReader<I, DoubleTag> doubleTag(Function<I, Double> writer, BiConsumer<I, Double> reader) {
      return new TagReader<>((byte)6, i -> (T)DoubleTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsDouble()), null);
   }

   public static <I> TagReader<I, DoubleTag> doubleTag(Function<I, Double> writer, BiConsumer<I, Double> reader, double defaultValue) {
      return new TagReader<>(
         (byte)6, i -> (T)DoubleTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsDouble()), () -> (T)DoubleTag.valueOf(defaultValue)
      );
   }

   public static <I> TagReader<I, StringTag> stringTag(Function<I, String> writer, BiConsumer<I, String> reader) {
      return new TagReader<>((byte)8, i -> (T)StringTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsString()), null);
   }

   public static <I> TagReader<I, StringTag> stringTag(Function<I, String> writer, BiConsumer<I, String> reader, String defaultValue) {
      return new TagReader<>(
         (byte)8, i -> (T)StringTag.valueOf(writer.apply(i)), (i, t) -> reader.accept(i, t.getAsString()), () -> (T)StringTag.valueOf(defaultValue)
      );
   }

   @Override
   public byte type() {
      return this.type;
   }

   @Override
   public void setDefault(I instance) {
      if (this.defaultCreator != null) {
         this.reader.accept(instance, this.defaultCreator.get());
      }
   }

   @Override
   public void read(I instance, T tag) {
      this.reader.accept(instance, tag);
   }

   @Override
   public T write(I instance) {
      return this.writer.apply(instance);
   }
}
