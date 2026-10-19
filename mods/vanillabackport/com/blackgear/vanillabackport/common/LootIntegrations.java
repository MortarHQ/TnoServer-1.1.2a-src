package com.blackgear.vanillabackport.common;

import com.blackgear.platform.common.data.LootModifier.LootTableContext;
import com.blackgear.platform.common.data.LootModifier.LootTableModifier;
import com.blackgear.platform.core.events.ServerLifecycleEvents;
import com.blackgear.vanillabackport.common.registries.ModBiomes;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModItems;
import com.blackgear.vanillabackport.core.VanillaBackport;
import java.util.List;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.advancements.critereon.DamageSourcePredicate.Builder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootIntegrations implements LootTableModifier {
   private static final List<ResourceKey<LootTable>> CONTAIN_BUNDLE = List.of(
      BuiltInLootTables.VILLAGE_WEAPONSMITH,
      BuiltInLootTables.VILLAGE_CARTOGRAPHER,
      BuiltInLootTables.VILLAGE_TANNERY,
      BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
      BuiltInLootTables.VILLAGE_TAIGA_HOUSE,
      BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
      BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
      BuiltInLootTables.VILLAGE_DESERT_HOUSE
   );

   public void modify(ResourceKey<LootTable> key, LootTableContext context, boolean builtin) {
      if (key.equals(EntityType.GHAST.getDefaultLootTable()) && (Boolean)VanillaBackport.COMMON_CONFIG.hasTearsMusicDisc.get()) {
         context.addPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(LootItem.lootTableItem((ItemLike)ModItems.MUSIC_DISC_TEARS.get()))
               .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
               .when(
                  DamageSourceCondition.hasDamageSource(
                     Builder.damageType()
                        .tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))
                        .direct(net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(EntityType.FIREBALL))
                  )
               )
               .when(LootItemKilledByPlayerCondition.killedByPlayer())
         );
      }

      if (key.equals(BuiltInLootTables.PIGLIN_BARTERING) && (Boolean)VanillaBackport.COMMON_CONFIG.hasDriedGhasts.get()) {
         context.addToPool(
            new LootPoolEntryContainer[]{
               LootItem.lootTableItem((ItemLike)ModBlocks.DRIED_GHAST.get())
                  .setWeight(10)
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                  .build()
            }
         );
      }

      if (key.equals(EntityType.ZOMBIE.getDefaultLootTable()) && (Boolean)VanillaBackport.COMMON_CONFIG.hasLavaChickenMusicDisc.get()) {
         context.addPool(
            LootPool.lootPool()
               .add(LootItem.lootTableItem((ItemLike)ModItems.MUSIC_DISC_LAVA_CHICKEN.get()))
               .when(LootItemKilledByPlayerCondition.killedByPlayer())
               .when(
                  LootItemEntityPropertyCondition.hasProperties(
                     EntityTarget.THIS,
                     net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                        .flags(net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags().setIsBaby(true))
                        .vehicle(net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(EntityType.CHICKEN))
                  )
               )
         );
      }

      if (key.equals(BuiltInLootTables.WOODLAND_MANSION) && (Boolean)VanillaBackport.COMMON_CONFIG.hasResinLoot.get()) {
         context.addToPool(
            1,
            new LootPoolEntryContainer[]{
               LootItem.lootTableItem((ItemLike)ModBlocks.RESIN_CLUMP.get())
                  .setWeight(50)
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                  .build()
            }
         );
      }

      if (CONTAIN_BUNDLE.contains(key) && (Boolean)VanillaBackport.COMMON_CONFIG.hasBundleLoot.get()) {
         context.addPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
               .add(EmptyLootItem.emptyItem().setWeight(2))
         );
      }

      if (key.equals(BuiltInLootTables.RUINED_PORTAL) && (Boolean)VanillaBackport.COMMON_CONFIG.hasLodestoneLoot.get()) {
         context.addPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(LootItem.lootTableItem(Items.LODESTONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
               .add(EmptyLootItem.emptyItem().setWeight(1))
         );
      }

      if (key.equals(BuiltInLootTables.ABANDONED_MINESHAFT) && (Boolean)VanillaBackport.COMMON_CONFIG.hasBounceMusicDisc.get()) {
         ServerLifecycleEvents.STARTING
            .register(
               (ServerLifecycleEvents)server -> {
                  RegistryLookup<Biome> lookup = server.registryAccess().lookupOrThrow(Registries.BIOME);
                  context.addToPool(
                     2,
                     new LootPoolEntryContainer[]{
                        ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                                 (ItemLike)ModItems.MUSIC_DISC_BOUNCE.get()
                              )
                              .when(
                                 LocationCheck.checkLocation(
                                    net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                                       .setBiomes(HolderSet.direct(new Holder[]{lookup.getOrThrow(ModBiomes.SULFUR_CAVES)}))
                                 )
                              ))
                           .setWeight(10)
                           .build()
                     }
                  );
               }
            );
      }
   }
}
