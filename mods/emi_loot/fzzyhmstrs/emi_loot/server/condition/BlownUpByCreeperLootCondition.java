package fzzyhmstrs.emi_loot.server.condition;

import com.mojang.serialization.MapCodec;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class BlownUpByCreeperLootCondition implements LootItemCondition {
   public static final BlownUpByCreeperLootCondition INSTANCE = new BlownUpByCreeperLootCondition();
   public static final MapCodec<BlownUpByCreeperLootCondition> CODEC = MapCodec.unit(INSTANCE);

   public LootItemConditionType getType() {
      return EMILoot.CREEPER.get();
   }

   public boolean test(LootContext lootContext) {
      return false;
   }
}
