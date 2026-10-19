package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record ESBossLootSubProvider(Provider registries) implements LootTableSubProvider {
   public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
      RegistryLookup<PaintingVariant> paintings = this.registries.lookupOrThrow(Registries.PAINTING_VARIANT);
      consumer.accept(
         ESLootTables.BOSS_COMMON,
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(UniformGenerator.between(5.0F, 8.0F))
                  .add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(75))
                  .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(50))
                  .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(50))
                  .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(32.0F, 64.0F))).setWeight(50))
                  .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(32.0F, 64.0F))).setWeight(50))
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.AMARAMBER_ARROW.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(20.0F, 32.0F)))
                        .setWeight(35)
                  )
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.SEEKING_EYE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .setWeight(75)
                  )
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.LUNAR_BERRIES.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .setWeight(75)
                  )
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.DEEPSILVER_INGOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .setWeight(30)
                  )
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.THERMAL_SPRINGSTONE_INGOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .setWeight(30)
                  )
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.GLACITE_SHARD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .setWeight(30)
                  )
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.AETHERSENT_INGOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .setWeight(15)
                  )
            )
      );
      consumer.accept(
         ESLootTables.BOSS_THE_GATEKEEPER,
         LootTable.lootTable()
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.BOOK.get())))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.ORB_OF_PROPHECY.get())))
            .withPool(
               LootPool.lootPool()
                  .add(LootItem.lootTableItem((ItemLike)ESItems.GLISTERING_SWORD.get()).when(LootItemRandomChanceCondition.randomChance(0.3F)))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.GLISTERING_GREATSWORD.get()).when(LootItemRandomChanceCondition.randomChance(0.3F)))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.GLISTERING_MORNING_STAR.get()).when(LootItemRandomChanceCondition.randomChance(0.3F)))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.GLISTERING_BOW.get()).when(LootItemRandomChanceCondition.randomChance(0.3F)))
            )
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.MUSIC_DISC_OPTIMIZED_OPTION.get())))
            .withPool(
               LootPool.lootPool()
                  .setRolls(UniformGenerator.between(5.0F, 8.0F))
                  .add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(75))
                  .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(50))
                  .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(50))
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.SEEKING_EYE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F)))
                        .setWeight(75)
                  )
            )
      );
      consumer.accept(
         ESLootTables.BOSS_PERMAFROST,
         LootTable.lootTable()
            .withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.COLDSNAP.get())))
            .withPool(
               LootPool.lootPool()
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.OXIDIZED_GOLEM_STEEL_NUGGET.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 12.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .add(LootItem.lootTableItem((ItemLike)ESItems.FROZEN_TUBE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(12.0F, 18.0F))))
            )
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.5F))
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.STARLIT_PAINTING.get())
                        .apply(
                           SetComponentsFunction.setComponent(
                              DataComponents.ENTITY_DATA,
                              ((CustomData)CustomData.EMPTY
                                    .update(
                                       this.registries.createSerializationContext(NbtOps.INSTANCE),
                                       Painting.VARIANT_MAP_CODEC,
                                       paintings.getOrThrow(ESPaintingVariants.ABSOLUTE_ZERO)
                                    )
                                    .getOrThrow())
                                 .update(compoundTag -> compoundTag.putString("id", "eternal_starlight:painting"))
                           )
                        )
                  )
            )
      );
      consumer.accept(
         ESLootTables.BOSS_STARLIGHT_GOLEM,
         LootTable.lootTable()
            .withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
            .withPool(
               LootPool.lootPool()
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 10.0F)))
                  )
            )
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get())))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.OXIDIZED_ALLOY_FURNACE.get())))
            .withPool(
               LootPool.lootPool().when(LootItemRandomChanceCondition.randomChance(0.75F)).add(LootItem.lootTableItem((ItemLike)ESItems.ENERGY_SWORD.get()))
            )
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.5F))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.MUSIC_DISC_MECHANICAL_FOSSIL.get()))
            )
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.5F))
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.STARLIT_PAINTING.get())
                        .apply(
                           SetComponentsFunction.setComponent(
                              DataComponents.ENTITY_DATA,
                              ((CustomData)CustomData.EMPTY
                                    .update(
                                       this.registries.createSerializationContext(NbtOps.INSTANCE),
                                       Painting.VARIANT_MAP_CODEC,
                                       paintings.getOrThrow(ESPaintingVariants.ENERGIZED)
                                    )
                                    .getOrThrow())
                                 .update(compoundTag -> compoundTag.putString("id", "eternal_starlight:painting"))
                           )
                        )
                  )
            )
      );
      consumer.accept(
         ESLootTables.BOSS_LUNAR_MONSTROSITY,
         LootTable.lootTable()
            .withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.MOONRING_BOW.get())))
            .withPool(
               LootPool.lootPool()
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.TENACIOUS_PETAL.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 15.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.TENACIOUS_VINE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 15.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .add(LootItem.lootTableItem((ItemLike)ESItems.SOUL_DEW.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))))
            )
            .withPool(
               LootPool.lootPool()
                  .add(LootItem.lootTableItem((ItemLike)ESItems.TANGLED_SKULL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 8.0F))))
            )
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.75F))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.WAND_OF_TELEPORTATION.get()))
            )
            .withPool(
               LootPool.lootPool().when(LootItemRandomChanceCondition.randomChance(0.75F)).add(LootItem.lootTableItem((ItemLike)ESItems.CRESCENT_SPEAR.get()))
            )
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.CRESCENT_PENDANT.get())))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem((ItemLike)ESItems.TWINING_ARMOR_TRIM_SMITHING_TEMPLATE.get())))
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.5F))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.MUSIC_DISC_MOONLIGHT.get()))
            )
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.5F))
                  .add(LootItem.lootTableItem((ItemLike)ESItems.MUSIC_DISC_FAKE_LIGHT.get()))
            )
            .withPool(
               LootPool.lootPool()
                  .when(LootItemRandomChanceCondition.randomChance(0.5F))
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.STARLIT_PAINTING.get())
                        .apply(
                           SetComponentsFunction.setComponent(
                              DataComponents.ENTITY_DATA,
                              ((CustomData)CustomData.EMPTY
                                    .update(
                                       this.registries.createSerializationContext(NbtOps.INSTANCE),
                                       Painting.VARIANT_MAP_CODEC,
                                       paintings.getOrThrow(ESPaintingVariants.MONSTROUS)
                                    )
                                    .getOrThrow())
                                 .update(compoundTag -> compoundTag.putString("id", "eternal_starlight:painting"))
                           )
                        )
                  )
            )
      );
   }
}
