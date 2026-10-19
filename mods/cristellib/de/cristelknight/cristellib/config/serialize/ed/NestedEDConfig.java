package de.cristelknight.cristellib.config.serialize.ed;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.Map;

public record NestedEDConfig(Map<String, NestedEDConfig.Entry> entries) {
   public static final Codec<NestedEDConfig> CODEC = Codec.unboundedMap(Codec.STRING, NestedEDConfig.Entry.CODEC)
      .xmap(NestedEDConfig::new, NestedEDConfig::entries);
   public static final Codec<Map<String, NestedEDConfig>> ED_CODEC = Codec.unboundedMap(Codec.STRING, CODEC);

   public record Entry(Boolean value, NestedEDConfig nested) {
      public static final Codec<NestedEDConfig.Entry> CODEC = Codec.either(Codec.BOOL, Codec.lazyInitialized(() -> NestedEDConfig.CODEC))
         .xmap(
            either -> (NestedEDConfig.Entry)either.map(b -> new NestedEDConfig.Entry(b, null), n -> new NestedEDConfig.Entry(null, n)),
            entry -> entry.isBoolean() ? Either.left(entry.value) : Either.right(entry.nested)
         );

      public boolean isBoolean() {
         return this.value != null;
      }

      public static NestedEDConfig.Entry ofBoolean(Boolean value) {
         return new NestedEDConfig.Entry(value, null);
      }

      public static NestedEDConfig.Entry ofNested(NestedEDConfig nested) {
         return new NestedEDConfig.Entry(null, nested);
      }
   }
}
