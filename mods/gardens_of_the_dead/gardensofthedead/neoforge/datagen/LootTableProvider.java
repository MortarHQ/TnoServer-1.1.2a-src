package gardensofthedead.neoforge.datagen;

import gardensofthedead.block.WallHangingSignBlock;
import gardensofthedead.block.WallSignBlock;
import gardensofthedead.loot.MatchShears;
import gardensofthedead.registry.ModBlocks;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {
   private final List<SubProviderEntry> lootTables = new ArrayList<>();
   private final Set<Block> blocksWithLootAdded = new HashSet<>();

   public LootTableProvider(PackOutput packOutput, CompletableFuture<Provider> provider) {
      super(packOutput, Set.of(), List.of(), provider);
   }

   public List<SubProviderEntry> getTables() {
      this.lootTables.clear();
      this.blocksWithLootAdded.clear();
      BuiltInRegistries.BLOCK
         .keySet()
         .stream()
         .filter(k -> k.getNamespace().equals("gardens_of_the_dead"))
         .<Block>map(BuiltInRegistries.BLOCK::get)
         .filter(blockx -> blockx instanceof WallSignBlock || blockx instanceof WallHangingSignBlock)
         .forEach(xva$0 -> this.noLoot(xva$0));
      this.addShearHarvestables(
         (Block)ModBlocks.SOUL_SPORE.get(),
         (Block)ModBlocks.GLOWING_SOUL_SPORE.get(),
         (Block)ModBlocks.SOULBLIGHT_SPROUTS.get(),
         (Block)ModBlocks.BLISTERCROWN.get(),
         (Block)ModBlocks.TALL_BLISTERCROWN.get()
      );
      this.addDefaultDrops((Block)ModBlocks.WHISTLECANE.get());

      for (Block block : BuiltInRegistries.BLOCK.stream().toList()) {
         if (!this.blocksWithLootAdded.contains(block) && BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals("gardens_of_the_dead")) {
            switch (block) {
               case FlowerPotBlock pottedPlant:
                  this.addPottedPlants(pottedPlant);
                  break;
               case DoorBlock doorBlock:
                  this.addDoor(doorBlock);
                  break;
               case SlabBlock slabBlock:
                  this.addSlab(slabBlock);
                  break;
               default:
                  this.addDefaultDrops(block);
            }
         }
      }

      return this.lootTables;
   }

   private void addShearHarvestables(Block... blocks) {
      for (Block block : blocks) {
         this.addBlockLootTable(block, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block).when(MatchShears.matchShears()))));
      }
   }

   private void addPottedPlants(FlowerPotBlock... blocks) {
      for (FlowerPotBlock block : blocks) {
         Block emptyPot = block.getEmptyPot();
         Block content = block.getPotted();
         this.addBlockLootTable(
            block,
            LootTable.lootTable()
               .withPool(LootPool.lootPool().add(LootItem.lootTableItem(emptyPot)).when(ExplosionCondition.survivesExplosion()))
               .withPool(LootPool.lootPool().add(LootItem.lootTableItem(content)).when(ExplosionCondition.survivesExplosion()))
         );
      }
   }

   private void addDoor(DoorBlock block) {
      this.addBlockLootTable(
         block,
         LootTable.lootTable()
            .withPool(
               this.defaultDrops(block)
                  .when(
                     LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(Builder.properties().hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER))
                  )
            )
      );
   }

   private void addSlab(SlabBlock block) {
      this.addBlockLootTable(
         block,
         LootTable.lootTable()
            .withPool(
               this.defaultDrops(block)
                  .apply(
                     SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                        .when(
                           LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                              .setProperties(Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))
                        )
                  )
            )
      );
   }

   private void addDefaultDrops(Block block) {
      this.addDefaultDrops(block, block);
   }

   private void addDefaultDrops(Block block, ItemLike loot) {
      this.addBlockLootTable(block, LootTable.lootTable().withPool(this.defaultDrops(loot)));
   }

   private net.minecraft.world.level.storage.loot.LootPool.Builder defaultDrops(ItemLike itemProvider) {
      return LootPool.lootPool().when(ExplosionCondition.survivesExplosion()).add(LootItem.lootTableItem(itemProvider));
   }

   private void addBlockLootTable(Block block, net.minecraft.world.level.storage.loot.LootTable.Builder lootTable) {
      this.blocksWithLootAdded.add(block);
      this.lootTables.add(new SubProviderEntry(provider -> lootBuilder -> lootBuilder.accept(block.getLootTable(), lootTable), LootContextParamSets.BLOCK));
   }

   private void noLoot(Block... blocks) {
      this.blocksWithLootAdded.addAll(Set.of(blocks));
   }
}
