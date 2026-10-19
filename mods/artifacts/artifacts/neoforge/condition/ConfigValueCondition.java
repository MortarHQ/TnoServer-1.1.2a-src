package artifacts.neoforge.condition;

import artifacts.Artifacts;
import artifacts.config.value.Value;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ICondition.IContext;

public record ConfigValueCondition(Value.ConfigValue<Boolean> value) implements ICondition {
   public static final MapCodec<ConfigValueCondition> CODEC = Artifacts.CONFIG
      .general
      .slots
      .codec()
      .xmap(ConfigValueCondition::new, ConfigValueCondition::value)
      .fieldOf("value");

   public MapCodec<ConfigValueCondition> codec() {
      return CODEC;
   }

   public boolean test(IContext context) {
      return this.value.get();
   }
}
