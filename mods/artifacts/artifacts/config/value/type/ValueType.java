package artifacts.config.value.type;

import artifacts.Artifacts;
import artifacts.config.screen.ConfigEntries;
import artifacts.config.value.Value;
import artifacts.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public abstract class ValueType<T, C> {
   public abstract boolean isCorrect(T var1);

   public abstract String makeError(T var1);

   public abstract String getAllowedValuesComment();

   public abstract T read(C var1);

   public abstract C write(T var1);

   public final Codec<Value<T>> codec() {
      return ModCodecs.xorAlternative(
         this.configCodec()
            .flatXmap(
               DataResult::success,
               value -> value instanceof Value.ConfigValue<T> configValue && Artifacts.CONFIG.items.getValues(this).containsKey(configValue.getId())
                  ? DataResult.success(configValue)
                  : DataResult.error(() -> "Not a valid config value: %s".formatted(value))
            ),
         this.constantCodec()
      );
   }

   protected abstract Codec<T> valueCodec();

   public final Codec<Value<T>> constantCodec() {
      return this.valueCodec()
         .comapFlatMap(value -> this.isCorrect((T)value) ? DataResult.success(value) : DataResult.error(() -> this.makeError((T)value)), Function.identity())
         .xmap(Value.Constant::new, Supplier::get);
   }

   public final Codec<Value.ConfigValue<T>> configCodec() {
      return StringRepresentable.fromValues(this::getConfigValues);
   }

   public final StreamCodec<ByteBuf, Value<T>> streamCodec() {
      return ByteBufCodecs.BOOL
         .dispatch(
            value -> value instanceof Value.ConfigValue,
            b -> b ? this.configReferenceStreamCodec() : this.valueStreamCodec().map(Value.Constant::new, Supplier::get)
         );
   }

   public abstract StreamCodec<ByteBuf, T> valueStreamCodec();

   public final StreamCodec<ByteBuf, Value.ConfigValue<T>> configReferenceStreamCodec() {
      Value.ConfigValue<T>[] values = this.getConfigValues();
      return ByteBufCodecs.idMapper(i -> values[i], Util.createIndexLookup(Arrays.asList(values)));
   }

   public final StreamCodec<ByteBuf, Value.ConfigValue<T>> directConfigStreamCodec(String id) {
      return this.valueStreamCodec().map(v -> new Value.ConfigValue<>(this, id, (T)v), Value.ConfigValue::get);
   }

   private Value.ConfigValue<T>[] getConfigValues() {
      List<Value.ConfigValue<T>> values = List.copyOf(Artifacts.CONFIG.items.getValues(this).values());
      Value.ConfigValue<T>[] result = (Value.ConfigValue<T>[])Array.newInstance(values.getFirst().getClass(), values.size());

      for (int i = 0; i < values.size(); i++) {
         result[i] = values.get(i);
      }

      return result;
   }

   public abstract ConfigEntries.ConfigEntryFactory<T> getConfigEntryFactory();
}
