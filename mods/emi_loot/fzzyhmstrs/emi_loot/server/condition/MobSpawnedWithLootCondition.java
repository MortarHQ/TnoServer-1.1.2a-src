package fzzyhmstrs.emi_loot.server.condition;

import com.mojang.serialization.MapCodec;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class MobSpawnedWithLootCondition implements LootItemCondition {
   public static final MobSpawnedWithLootCondition INSTANCE = new MobSpawnedWithLootCondition();
   public static final MapCodec<MobSpawnedWithLootCondition> CODEC = MapCodec.unit(INSTANCE);

   public LootItemConditionType getType() {
      return EMILoot.SPAWNS_WITH.get();
   }

   public boolean test(LootContext lootContext) {
      return false;
   }
}
