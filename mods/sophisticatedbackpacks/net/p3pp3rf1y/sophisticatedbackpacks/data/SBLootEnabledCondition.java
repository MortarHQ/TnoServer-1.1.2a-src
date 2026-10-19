package net.p3pp3rf1y.sophisticatedbackpacks.data;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class SBLootEnabledCondition implements LootItemCondition {
   private static final SBLootEnabledCondition INSTANCE = new SBLootEnabledCondition();
   public static final MapCodec<SBLootEnabledCondition> CODEC = MapCodec.unit(INSTANCE).stable();

   private SBLootEnabledCondition() {
   }

   public LootItemConditionType getType() {
      return ModItems.LOOT_ENABLED_CONDITION.get();
   }

   public boolean test(LootContext lootContext) {
      return Boolean.TRUE.equals(Config.COMMON.chestLootEnabled.get());
   }

   public static SBLootEnabledCondition.Builder builder() {
      return new SBLootEnabledCondition.Builder();
   }

   public static class Builder implements net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder {
      public LootItemCondition build() {
         return new SBLootEnabledCondition();
      }
   }
}
