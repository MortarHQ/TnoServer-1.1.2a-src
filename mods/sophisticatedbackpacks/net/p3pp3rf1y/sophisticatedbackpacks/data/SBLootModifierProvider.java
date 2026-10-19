package net.p3pp3rf1y.sophisticatedbackpacks.data;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class SBLootModifierProvider extends GlobalLootModifierProvider {
   SBLootModifierProvider(PackOutput packOutput, CompletableFuture<Provider> registries) {
      super(packOutput, registries, "sophisticatedbackpacks");
   }

   protected void start() {
      this.addInjectLootTableModifier(SBInjectLootSubProvider.SIMPLE_DUNGEON, BuiltInLootTables.SIMPLE_DUNGEON);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.ABANDONED_MINESHAFT, BuiltInLootTables.ABANDONED_MINESHAFT);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.DESERT_PYRAMID, BuiltInLootTables.DESERT_PYRAMID);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.WOODLAND_MANSION, BuiltInLootTables.WOODLAND_MANSION);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.SHIPWRECK_TREASURE, BuiltInLootTables.SHIPWRECK_TREASURE);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.BASTION_TREASURE, BuiltInLootTables.BASTION_TREASURE);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.END_CITY_TREASURE, BuiltInLootTables.END_CITY_TREASURE);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.NETHER_BRIDGE, BuiltInLootTables.NETHER_BRIDGE);
      this.addInjectLootTableModifier(SBInjectLootSubProvider.SPAWN_BONUS_CHEST, BuiltInLootTables.SPAWN_BONUS_CHEST);
   }

   private void addInjectLootTableModifier(ResourceKey<LootTable> lootTable, ResourceKey<LootTable> lootTableToInjectInto) {
      this.add(lootTableToInjectInto.location().getPath(), new SBLootModifierProvider.InjectLootModifier(lootTable, lootTableToInjectInto), new ICondition[0]);
   }

   public static class InjectLootModifier extends LootModifier {
      public static final MapCodec<SBLootModifierProvider.InjectLootModifier> CODEC = RecordCodecBuilder.mapCodec(
         inst -> LootModifier.codecStart(inst)
            .and(
               inst.group(
                  ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(m -> m.lootTable),
                  ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table_to_inject_into").forGetter(m -> m.lootTableToInjectInto)
               )
            )
            .apply(inst, SBLootModifierProvider.InjectLootModifier::new)
      );
      private final ResourceKey<LootTable> lootTable;
      private final ResourceKey<LootTable> lootTableToInjectInto;

      protected InjectLootModifier(LootItemCondition[] conditions, ResourceKey<LootTable> lootTable, ResourceKey<LootTable> lootTableToInjectInto) {
         super(conditions);
         this.lootTable = lootTable;
         this.lootTableToInjectInto = lootTableToInjectInto;
      }

      protected InjectLootModifier(ResourceKey<LootTable> lootTable, ResourceKey<LootTable> lootTableToInjectInto) {
         this(
            new LootItemCondition[]{SBLootEnabledCondition.builder().build(), LootTableIdCondition.builder(lootTableToInjectInto.location()).build()},
            lootTable,
            lootTableToInjectInto
         );
      }

      protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
         context.getResolver()
            .get(Registries.LOOT_TABLE, this.lootTable)
            .ifPresent(
               extraTable -> ((LootTable)extraTable.value()).getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add))
            );
         return generatedLoot;
      }

      public MapCodec<? extends IGlobalLootModifier> codec() {
         return ModItems.INJECT_LOOT.get();
      }
   }
}
