package artifacts.loot;

import artifacts.registry.ModLootConditions;
import com.mojang.serialization.MapCodec;
import java.time.LocalDate;
import java.time.Month;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public record IsAprilFools() implements LootItemCondition {
   public static final MapCodec<IsAprilFools> CODEC = MapCodec.unit(new IsAprilFools());
   private static final boolean IS_APRIL_FOOLS = LocalDate.now().getMonth() == Month.APRIL && LocalDate.now().getDayOfMonth() == 1;

   public LootItemConditionType getType() {
      return (LootItemConditionType)ModLootConditions.IS_APRIL_FOOLS.value();
   }

   public boolean test(LootContext lootContext) {
      return IS_APRIL_FOOLS;
   }

   public static Builder builder() {
      return IsAprilFools::new;
   }
}
