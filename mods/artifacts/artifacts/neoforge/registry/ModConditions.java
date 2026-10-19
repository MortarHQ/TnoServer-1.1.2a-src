package artifacts.neoforge.registry;

import artifacts.neoforge.condition.ConfigValueCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class ModConditions {
   public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(Keys.CONDITION_CODECS, "artifacts");

   static {
      CONDITIONS.register("config_value", () -> ConfigValueCondition.CODEC);
   }
}
