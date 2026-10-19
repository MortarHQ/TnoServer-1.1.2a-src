package net.jadenxgamer.elysium_api.impl.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;

public record ConditionalBoolean(boolean defaultValue, List<ConditionalBoolean.BooleanRule> rules) {
   public static final Codec<ConditionalBoolean> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.BOOL.fieldOf("default").forGetter(ConditionalBoolean::defaultValue),
            ConditionalBoolean.BooleanRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(ConditionalBoolean::rules)
         )
         .apply(instance, ConditionalBoolean::new)
   );

   public boolean getValue(BlockState state) {
      for (ConditionalBoolean.BooleanRule rule : this.rules) {
         if (rule.condition().matches(state)) {
            return rule.value();
         }
      }

      return this.defaultValue;
   }

   public record BooleanRule(BlockStatePropertiesCondition condition, boolean value) {
      public static final Codec<ConditionalBoolean.BooleanRule> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BlockStatePropertiesCondition.CODEC.fieldOf("when").forGetter(ConditionalBoolean.BooleanRule::condition),
               Codec.BOOL.fieldOf("value").forGetter(ConditionalBoolean.BooleanRule::value)
            )
            .apply(instance, ConditionalBoolean.BooleanRule::new)
      );
   }
}
