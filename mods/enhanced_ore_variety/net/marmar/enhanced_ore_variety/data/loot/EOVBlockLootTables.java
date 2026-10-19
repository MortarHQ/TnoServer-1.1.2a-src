package net.marmar.enhanced_ore_variety.data.loot;

import java.util.Set;
import net.marmar.enhanced_ore_variety.block.EOVBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EOVBlockLootTables extends BlockLootSubProvider {
   protected EOVBlockLootTables(Provider registries) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
   }

   protected void generate() {
      this.add((Block)EOVBlocks.DIORITE_COAL_ORE.get(), this::createCoalOreDrops);
      this.add((Block)EOVBlocks.DIORITE_COPPER_ORE.get(), x$0 -> this.createCopperOreDrops(x$0));
      this.add((Block)EOVBlocks.DIORITE_IRON_ORE.get(), this::createIronOreDrops);
      this.add((Block)EOVBlocks.DIORITE_GOLD_ORE.get(), this::createGoldOreDrops);
      this.add((Block)EOVBlocks.DIORITE_LAPIS_ORE.get(), x$0 -> this.createLapisOreDrops(x$0));
      this.add((Block)EOVBlocks.DIORITE_REDSTONE_ORE.get(), x$0 -> this.createRedstoneOreDrops(x$0));
      this.add((Block)EOVBlocks.DIORITE_EMERALD_ORE.get(), this::createEmeraldOreDrops);
      this.add((Block)EOVBlocks.DIORITE_DIAMOND_ORE.get(), this::createDiamondOreDrops);
      this.add((Block)EOVBlocks.ANDESITE_COAL_ORE.get(), this::createCoalOreDrops);
      this.add((Block)EOVBlocks.ANDESITE_COPPER_ORE.get(), x$0 -> this.createCopperOreDrops(x$0));
      this.add((Block)EOVBlocks.ANDESITE_IRON_ORE.get(), this::createIronOreDrops);
      this.add((Block)EOVBlocks.ANDESITE_GOLD_ORE.get(), this::createGoldOreDrops);
      this.add((Block)EOVBlocks.ANDESITE_LAPIS_ORE.get(), x$0 -> this.createLapisOreDrops(x$0));
      this.add((Block)EOVBlocks.ANDESITE_REDSTONE_ORE.get(), x$0 -> this.createRedstoneOreDrops(x$0));
      this.add((Block)EOVBlocks.ANDESITE_EMERALD_ORE.get(), this::createEmeraldOreDrops);
      this.add((Block)EOVBlocks.ANDESITE_DIAMOND_ORE.get(), this::createDiamondOreDrops);
      this.add((Block)EOVBlocks.GRANITE_COAL_ORE.get(), this::createCoalOreDrops);
      this.add((Block)EOVBlocks.GRANITE_COPPER_ORE.get(), x$0 -> this.createCopperOreDrops(x$0));
      this.add((Block)EOVBlocks.GRANITE_IRON_ORE.get(), this::createIronOreDrops);
      this.add((Block)EOVBlocks.GRANITE_GOLD_ORE.get(), this::createGoldOreDrops);
      this.add((Block)EOVBlocks.GRANITE_LAPIS_ORE.get(), x$0 -> this.createLapisOreDrops(x$0));
      this.add((Block)EOVBlocks.GRANITE_REDSTONE_ORE.get(), x$0 -> this.createRedstoneOreDrops(x$0));
      this.add((Block)EOVBlocks.GRANITE_EMERALD_ORE.get(), this::createEmeraldOreDrops);
      this.add((Block)EOVBlocks.GRANITE_DIAMOND_ORE.get(), this::createDiamondOreDrops);
      this.add((Block)EOVBlocks.TUFF_COAL_ORE.get(), this::createCoalOreDrops);
      this.add((Block)EOVBlocks.TUFF_COPPER_ORE.get(), x$0 -> this.createCopperOreDrops(x$0));
      this.add((Block)EOVBlocks.TUFF_IRON_ORE.get(), this::createIronOreDrops);
      this.add((Block)EOVBlocks.TUFF_GOLD_ORE.get(), this::createGoldOreDrops);
      this.add((Block)EOVBlocks.TUFF_LAPIS_ORE.get(), x$0 -> this.createLapisOreDrops(x$0));
      this.add((Block)EOVBlocks.TUFF_REDSTONE_ORE.get(), x$0 -> this.createRedstoneOreDrops(x$0));
      this.add((Block)EOVBlocks.TUFF_EMERALD_ORE.get(), this::createEmeraldOreDrops);
      this.add((Block)EOVBlocks.TUFF_DIAMOND_ORE.get(), this::createDiamondOreDrops);
      this.add((Block)EOVBlocks.BLACKSTONE_COAL_ORE.get(), this::createCoalOreDrops);
      this.add((Block)EOVBlocks.BLACKSTONE_COPPER_ORE.get(), x$0 -> this.createCopperOreDrops(x$0));
      this.add((Block)EOVBlocks.BLACKSTONE_IRON_ORE.get(), this::createIronOreDrops);
      this.add((Block)EOVBlocks.BLACKSTONE_GOLD_ORE.get(), this::createNetherGoldOreDrops);
      this.add((Block)EOVBlocks.BLACKSTONE_LAPIS_ORE.get(), x$0 -> this.createLapisOreDrops(x$0));
      this.add((Block)EOVBlocks.BLACKSTONE_REDSTONE_ORE.get(), x$0 -> this.createRedstoneOreDrops(x$0));
      this.add((Block)EOVBlocks.BLACKSTONE_EMERALD_ORE.get(), this::createEmeraldOreDrops);
      this.add((Block)EOVBlocks.BLACKSTONE_DIAMOND_ORE.get(), this::createDiamondOreDrops);
      this.add((Block)EOVBlocks.BLACKSTONE_QUARTZ_ORE.get(), block -> this.createOreDrop(block, Items.QUARTZ));
   }

   private Builder createCoalOreDrops(Block pBlock) {
      return this.createOreDrop(pBlock, Items.COAL);
   }

   private Builder createIronOreDrops(Block pBlock) {
      return this.createOreDrop(pBlock, Items.RAW_IRON);
   }

   private Builder createGoldOreDrops(Block pBlock) {
      return this.createOreDrop(pBlock, Items.RAW_GOLD);
   }

   private Builder createNetherGoldOreDrops(Block pBlock) {
      RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      return this.createSilkTouchDispatchTable(
         pBlock,
         (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
            pBlock,
            LootItem.lootTableItem(Items.GOLD_NUGGET)
               .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
               .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
         )
      );
   }

   private Builder createEmeraldOreDrops(Block pBlock) {
      return this.createOreDrop(pBlock, Items.EMERALD);
   }

   private Builder createDiamondOreDrops(Block pBlock) {
      return this.createOreDrop(pBlock, Items.DIAMOND);
   }

   protected Iterable<Block> getKnownBlocks() {
      return EOVBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
   }
}
