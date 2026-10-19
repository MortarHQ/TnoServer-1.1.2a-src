package twilightforest.loot.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import twilightforest.config.TFConfig;
import twilightforest.init.TFLoot;

public class UncraftingTableEnabledCondition implements LootItemCondition {
   private static final UncraftingTableEnabledCondition INSTANCE = new UncraftingTableEnabledCondition();
   public static final MapCodec<UncraftingTableEnabledCondition> CODEC = MapCodec.unit(INSTANCE);

   public LootItemConditionType getType() {
      return (LootItemConditionType)TFLoot.UNCRAFTING_TABLE_ENABLED.get();
   }

   public boolean test(LootContext context) {
      return !TFConfig.disableEntireTable;
   }

   public static Builder uncraftingTableEnabled() {
      return UncraftingTableEnabledCondition::new;
   }
}
