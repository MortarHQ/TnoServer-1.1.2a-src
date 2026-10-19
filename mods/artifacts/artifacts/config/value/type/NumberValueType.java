package artifacts.config.value.type;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

public abstract class NumberValueType<T extends Number & Comparable<T>> extends ValueType<T, Number> {
   private final T min;
   private final T max;
   private final Codec<T> valueCodec;
   private final StreamCodec<ByteBuf, T> valueStreamCodec;

   public NumberValueType(T min, T max, Codec<T> valueCodec, StreamCodec<ByteBuf, T> valueStreamCodec) {
      this.min = min;
      this.max = max;
      this.valueCodec = valueCodec;
      this.valueStreamCodec = valueStreamCodec;
   }

   public T getMin() {
      return this.min;
   }

   public T getMax() {
      return this.max;
   }

   @Override
   protected Codec<T> valueCodec() {
      return this.valueCodec;
   }

   @Override
   public StreamCodec<ByteBuf, T> valueStreamCodec() {
      return this.valueStreamCodec;
   }

   public boolean isCorrect(T value) {
      return value.compareTo(this.min) >= 0 && value.compareTo(this.max) <= 0;
   }

   public String makeError(T value) {
      return this.min.doubleValue() == 0.0 && this.max.intValue() == Integer.MAX_VALUE
         ? "Value must be non-negative: %s".formatted(value)
         : "Value must be within range [%s;%s]: %s".formatted(this.min, this.max, value);
   }

   @Override
   public String getAllowedValuesComment() {
      return "Range: %s ~ %s".formatted(this.getMin(), this.getMax()).replace("0.0 ~ Infinity", "> 0.0").replace("0 ~ 2147483647", "> 0");
   }

   public T write(T value) {
      return value;
   }
}
