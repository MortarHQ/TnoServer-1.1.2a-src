package gardensofthedead.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import gardensofthedead.loot.MatchShears;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ModLootConditions {
   public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create("gardens_of_the_dead", Registries.LOOT_CONDITION_TYPE);
   public static final RegistrySupplier<LootItemConditionType> MATCH_SHEARS = LOOT_CONDITIONS.register(
      "match_shears", () -> new LootItemConditionType(MatchShears.CODEC)
   );
}
