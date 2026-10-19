package net.jadenxgamer.elysium_api.impl.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;

public record ConditionalInt(int defaultValue, List<ConditionalInt.IntRule> rules) {
   public static final Codec<ConditionalInt> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("default").forGetter(ConditionalInt::defaultValue),
            ConditionalInt.IntRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(ConditionalInt::rules)
         )
         .apply(instance, ConditionalInt::new)
   );

   public int getValue(BlockState state) {
      for (ConditionalInt.IntRule rule : this.rules) {
         if (rule.condition().matches(state)) {
            return rule.value();
         }
      }

      return this.defaultValue;
   }

   public record IntRule(BlockStatePropertiesCondition condition, int value) {
      public static final Codec<ConditionalInt.IntRule> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BlockStatePropertiesCondition.CODEC.fieldOf("when").forGetter(ConditionalInt.IntRule::condition),
               Codec.INT.fieldOf("value").forGetter(ConditionalInt.IntRule::value)
            )
            .apply(instance, ConditionalInt.IntRule::new)
      );
   }
}
