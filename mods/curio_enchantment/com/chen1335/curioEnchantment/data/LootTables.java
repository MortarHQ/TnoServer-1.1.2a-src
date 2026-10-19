package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.data.enchantments.IronsSpellBooksEnchantments;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

public class LootTables extends LootTableProvider {
   public LootTables(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, CompletableFuture<Provider> registries) {
      super(output, requiredTables, List.of(new SubProviderEntry(LootTables.LootModifierLoot::new, LootContextParamSets.ENTITY)), registries);
   }

   private static ResourceKey<LootTable> register(String name) {
      return ResourceKey.create(Registries.LOOT_TABLE, CurioEnchantment.id(name));
   }

   public record LootModifierLoot(Provider registries) implements LootTableSubProvider {
      public static final ResourceKey<LootTable> DEAD_KING_LOOT_MODIFIER = LootTables.register("loot_modifier/entities/dead_king");

      public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, Builder> output) {
         RegistryLookup<Enchantment> enchantmentLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
         output.accept(
            DEAD_KING_LOOT_MODIFIER,
            LootTable.lootTable()
               .withPool(
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(
                        LootItem.lootTableItem(Items.BOOK)
                           .apply(
                              new net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction.Builder(false)
                                 .withEnchantment(enchantmentLookup.getOrThrow(IronsSpellBooksEnchantments.ANCIENT_WISDOM), ConstantValue.exactly(1.0F))
                           )
                           .setWeight(1)
                     )
               )
         );
      }
   }
}
