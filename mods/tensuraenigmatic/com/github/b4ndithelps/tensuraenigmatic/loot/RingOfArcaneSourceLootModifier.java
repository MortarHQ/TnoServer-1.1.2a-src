package com.github.b4ndithelps.tensuraenigmatic.loot;

import com.github.b4ndithelps.tensuraenigmatic.config.TensuraEnigmaticConfig;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class RingOfArcaneSourceLootModifier extends LootModifier {
   public static final Supplier<MapCodec<RingOfArcaneSourceLootModifier>> CODEC = Suppliers.memoize(
      () -> RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, RingOfArcaneSourceLootModifier::new))
   );
   private static final Set<ResourceLocation> CHEST_TABLES = Set.of(
      BuiltInLootTables.SIMPLE_DUNGEON.location(),
      BuiltInLootTables.ABANDONED_MINESHAFT.location(),
      BuiltInLootTables.STRONGHOLD_CORRIDOR.location(),
      BuiltInLootTables.STRONGHOLD_CROSSING.location(),
      BuiltInLootTables.STRONGHOLD_LIBRARY.location(),
      BuiltInLootTables.DESERT_PYRAMID.location(),
      BuiltInLootTables.JUNGLE_TEMPLE.location(),
      BuiltInLootTables.WOODLAND_MANSION.location(),
      BuiltInLootTables.PILLAGER_OUTPOST.location(),
      BuiltInLootTables.BURIED_TREASURE.location(),
      BuiltInLootTables.SHIPWRECK_TREASURE.location(),
      BuiltInLootTables.UNDERWATER_RUIN_BIG.location(),
      BuiltInLootTables.UNDERWATER_RUIN_SMALL.location(),
      BuiltInLootTables.RUINED_PORTAL.location(),
      BuiltInLootTables.BASTION_TREASURE.location(),
      BuiltInLootTables.NETHER_BRIDGE.location(),
      BuiltInLootTables.END_CITY_TREASURE.location()
   );

   public RingOfArcaneSourceLootModifier(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      ResourceLocation tableId = context.getQueriedLootTableId();
      if ((CHEST_TABLES.contains(tableId) || tableId.getPath().contains("chests/"))
         && context.getRandom().nextFloat() < (Double)TensuraEnigmaticConfig.RING_OF_ARCANE_SOURCE_DROP_CHANCE.get()) {
         generatedLoot.add(TensuraEnigmaticItems.RING_OF_ARCANE_SOURCE.toStack());
      }

      return generatedLoot;
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return CODEC.get();
   }
}
