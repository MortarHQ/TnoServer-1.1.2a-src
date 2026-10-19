package net.unusual.block_factorys_bosses.data.gen.loot;

import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.unusual.block_factorys_bosses.block.PrisonDoorBlock;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDoubleBlock;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractStackableBlock;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseLootBlockGen extends BlockLootSubProvider {
   public BossesRiseLootBlockGen(Provider provider) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
   }

   protected void generate() {
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_BROKEN);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_CRACKED);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_STRAIGHT);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_VARIATION);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_WET);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_WET_BROKEN);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_WET_CRACKED);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_WET_STRAIGHT);
      this.dropSelf(BossesRiseBlocks.BIG_OAK_PLANKS_WET_VARIATION);
      this.dropSelf(BossesRiseBlocks.GUARDRAIL);
      this.dropSelf(BossesRiseBlocks.GUARDRAIL_DIAGONAL);
      this.dropSelf(BossesRiseBlocks.ROPE_ROLL);
      this.dropSelf(BossesRiseBlocks.SHIP_STEERING_WHEEL);
      this.dropSelf(BossesRiseBlocks.CANNONBALL);
      this.dropSelf(BossesRiseBlocks.NET);
      this.dropSelf(BossesRiseBlocks.VASE);
      this.dropSelf(BossesRiseBlocks.WALL_TORCH);
      this.dropSelf(BossesRiseBlocks.UNDERWOLD_WALL_TORCH);
      this.dropIfIntValue(BossesRiseBlocks.TALL_VASE, AbstractDoubleBlock.BLOCK_HALF, 2);
      this.dropSelf(BossesRiseBlocks.CANDLES);
      this.dropSelf(BossesRiseBlocks.UNDERWOLD_CANDLES);
      this.dropIfIntValue(BossesRiseBlocks.TALL_CANDLES, AbstractDoubleBlock.BLOCK_HALF, 2);
      this.dropIfIntValue(BossesRiseBlocks.UNDERWORLD_TALL_CANDLES, AbstractDoubleBlock.BLOCK_HALF, 2);
      this.dropSelf(BossesRiseBlocks.BONE_REMAINS);
      this.dropIfIntValue(BossesRiseBlocks.CORPSE, AbstractDoubleBlock.BLOCK_HALF, 2);
      this.dropSelf(BossesRiseBlocks.BONE_REMAINS_LEGS);
      this.dropSelf(BossesRiseBlocks.BONE_REMAINS_RIB_CAGE);
      this.dropIfIntValue(BossesRiseBlocks.TALL_CANDLES_CROSS, AbstractDoubleBlock.BLOCK_HALF, 2);
      this.dropIfIntValue(BossesRiseBlocks.UNDER_WORLD_TALL_CANDLE_CROSS, AbstractDoubleBlock.BLOCK_HALF, 2);
      this.dropSelf(BossesRiseBlocks.BIG_CHAIN);
      this.dropIfIntValue(BossesRiseBlocks.PRISON_DOOR, PrisonDoorBlock.DOOR_PART, 0);
      this.dropIfIntValue(BossesRiseBlocks.RUSTY_PRISON_DOOR, PrisonDoorBlock.DOOR_PART, 0);
      this.dropSelf(BossesRiseBlocks.ICE_BLOCK_PARTICULES);
      this.dropSelf(BossesRiseBlocks.DRAGON_BANNER);
      this.dropSelf(BossesRiseBlocks.DRAGON_SKULL);
      this.dropOther((Block)BossesRiseBlocks.BOSS_SPAWNER.get(), Items.AIR);
      this.dropOther((Block)BossesRiseBlocks.KRAKEN_SPAWNER.get(), Items.AIR);
      this.dropSelf(BossesRiseBlocks.UNDERWORLD_ARENA_DOOR);
      this.dropSelf(BossesRiseBlocks.SHIP_LANTERN);
      this.createStackableBlockLootTable(BossesRiseBlocks.COIN_PILE);
      this.createStackableBlockLootTable(BossesRiseBlocks.PLANK);
   }

   protected void dropSelf(DeferredBlock<Block> registryObject) {
      this.dropSelf((Block)registryObject.get());
   }

   protected void dropIfIntValue(DeferredBlock<Block> registryObject, IntegerProperty property, Integer value) {
      this.add((Block)registryObject.get(), block -> this.createSingleIntConditionTable(block, property, value));
   }

   protected Builder createSingleIntConditionTable(Block block, IntegerProperty property, Integer value) {
      return LootTable.lootTable()
         .withPool(
            (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
               block,
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(block)
                        .when(
                           LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                              .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(property, value))
                        )
                  )
            )
         );
   }

   protected <T extends AbstractStackableBlock> void createStackableBlockLootTable(DeferredBlock<T> registryObject) {
      T stackableBlock = (T)registryObject.get();
      this.add(
         (Block)registryObject.get(),
         block -> this.createCountBasedLootTable(
            stackableBlock, stackableBlock.getStackableProperty(), stackableBlock.getMinStack(), stackableBlock.getMaxStack()
         )
      );
   }

   protected Builder createCountBasedLootTable(Block block, IntegerProperty property, Integer min, Integer max) {
      net.minecraft.world.level.storage.loot.LootPool.Builder builder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));

      for (int i = min; i <= max; i++) {
         builder = builder.add(
            LootItem.lootTableItem(block)
               .apply(SetItemCountFunction.setCount(ConstantValue.exactly(i)))
               .when(
                  LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                     .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(property, i))
               )
         );
      }

      return LootTable.lootTable().withPool((net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(block, builder));
   }

   protected Iterable<Block> getKnownBlocks() {
      return BossesRiseBlocks.REGISTRY.getEntries().stream().<Block>map(DeferredHolder::value).collect(Collectors.toList());
   }
}
