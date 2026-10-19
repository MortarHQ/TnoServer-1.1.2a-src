package cc.sighs.oelib.config.field;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;

public interface FieldBuilder<T> {
   FieldBuilder<T> comment(String var1);

   FieldBuilder<T> tooltip();

   FieldBuilder<T> defaultValue(T var1);

   <O> RecordCodecBuilder<O, T> forGetter(Function<O, T> var1);
}
