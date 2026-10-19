package fzzyhmstrs.emi_loot.server.condition;

import com.mojang.serialization.MapCodec;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class KilledByWitherLootCondition implements LootItemCondition {
   public static final KilledByWitherLootCondition INSTANCE = new KilledByWitherLootCondition();
   public static final MapCodec<KilledByWitherLootCondition> CODEC = MapCodec.unit(INSTANCE);

   public LootItemConditionType getType() {
      return EMILoot.WITHER_KILL.get();
   }

   public boolean test(LootContext lootContext) {
      return false;
   }
}
