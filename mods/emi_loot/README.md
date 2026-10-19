# EMI Loot（`emi_loot`）

> jar：`[EMI附属]emi_loot.jar` ｜ 版本：`0.7.9+1.21+neoforge` ｜ 作者：fzzyhmstrs, lxly9 (art), unilock (ports)
> 规模：**188 个类 / 18 个包 / 11096 行** ｜ 事件处理类 3 ｜ Mixin 40 ｜ 注册/管理类 4

**依赖**：`neoforge`、`minecraft`、`emi`、`fzzy_config`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `fzzyhmstrs.emi_loot.mixins` | 40 | `SetItemLootFunctionAccessor`、`SetCustomDataLootFunctionAccessor`、`SetLootTableLootFunctionAccessor` |
| `fzzyhmstrs.emi_loot.parser.function` | 32 | `SetLootTableFunctionParser`、`SetItemFunctionParser`、`SetCustomDataFunctionParser` |
| `fzzyhmstrs.emi_loot.parser` | 19 | `LootTableParser`、`BlockPredicateParser`、`EntityFlagsPredicateParser` |
| `fzzyhmstrs.emi_loot.parser.condition` | 18 | `RandomChanceWithLootingConditionParser`、`EntityPropertiesConditionParser`、`BlockStatePropertyConditionParser` |
| `fzzyhmstrs.emi_loot.client` | 13 | `ClientBlockLootTable`、`ClientLootTablesReceiver`、`AbstractTextKeyParsingClientLootTable` |
| `fzzyhmstrs.emi_loot.server` | 13 | `BlockLootTableSender`、`MobLootTableSender`、`NestedLootTableResults` |
| `fzzyhmstrs.emi_loot.util` | 11 | `TextKey`、`WidgetRowBuilder`、`QuantityListEmiIngredient` |
| `fzzyhmstrs.emi_loot.util.stack` | 8 | `EntityEmiStack`、`BlockLootEmiStack`、`ArchaeologyLootEmiStack` |
| `fzzyhmstrs.emi_loot.emi` | 7 | `BlockLootRecipe`、`MobLootRecipe`、`ChestLootRecipe` |
| `fzzyhmstrs.emi_loot.networking` | 6 | `BlockLootPayload`、`GameplayLootPayload`、`MobLootPayload` |
| `fzzyhmstrs.emi_loot` | 4 | `EMILootClientAgnos`、`EMILoot`、`EMILootAgnos` |
| `fzzyhmstrs.emi_loot.neoforge.events` | 3 | `EMILootClientGameEvents`、`EMILootClientModEvents`、`EMILootGameEvents` |
| `fzzyhmstrs.emi_loot.neoforge` | 3 | `EMILootNeoForge`、`EMILootClientAgnosNeoForge`、`EMILootAgnosNeoForge` |
| `fzzyhmstrs.emi_loot.neoforge.util` | 3 | `BlockRendererImpl`、`IconGroupEmiWidgetImpl`、`IconEmiWidgetImpl` |
| `fzzyhmstrs.emi_loot.server.condition` | 3 | `BlownUpByCreeperLootCondition`、`KilledByWitherLootCondition`、`MobSpawnedWithLootCondition` |
| `fzzyhmstrs.emi_loot.server.function` | 2 | `SetAnyDamageLootFunction`、`OminousBannerLootFunction` |
| `fzzyhmstrs.emi_loot.parser.processor` | 2 | `NumberProcessors`、`ListProcessors` |
| `fzzyhmstrs.emi_loot.parser.registry` | 1 | `LootParserRegistry` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EMILootClientGameEvents` | class | — | EventBusSubscriber | 1 | 17 | [EMILootClientGameEvents.java](fzzyhmstrs/emi_loot/neoforge/events/EMILootClientGameEvents.java) |
| `EMILootClientModEvents` | class | — | EventBusSubscriber | 2 | 24 | [EMILootClientModEvents.java](fzzyhmstrs/emi_loot/neoforge/events/EMILootClientModEvents.java) |
| `EMILootGameEvents` | class | — | EventBusSubscriber | 1 | 16 | [EMILootGameEvents.java](fzzyhmstrs/emi_loot/neoforge/events/EMILootGameEvents.java) |
| `EMILootNeoForge` | class | — | Mod | 0 | 42 | [EMILootNeoForge.java](fzzyhmstrs/emi_loot/neoforge/EMILootNeoForge.java) |
| `BlockLootRecipe` | class | EmiRecipe | — | 15 | 235 | [BlockLootRecipe.java](fzzyhmstrs/emi_loot/emi/BlockLootRecipe.java) |
| `LootTableParser` | class | — | — | 32 | 947 | [LootTableParser.java](fzzyhmstrs/emi_loot/parser/LootTableParser.java) |
| `SetItemLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetItemLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetItemLootFunctionAccessor.java) |
| `SetCustomDataLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [SetCustomDataLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetCustomDataLootFunctionAccessor.java) |
| `MobLootRecipe` | class | EmiRecipe | — | 13 | 308 | [MobLootRecipe.java](fzzyhmstrs/emi_loot/emi/MobLootRecipe.java) |
| `LootParserRegistry` | class | — | — | 4 | 228 | [LootParserRegistry.java](fzzyhmstrs/emi_loot/parser/registry/LootParserRegistry.java) |
| `SetLootTableLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetLootTableLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetLootTableLootFunctionAccessor.java) |
| `SetComponentsLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [SetComponentsLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetComponentsLootFunctionAccessor.java) |
| `CopyNameLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [CopyNameLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/CopyNameLootFunctionAccessor.java) |
| `SetDamageLootFunctionAccessor` | interface | — | Mixin | 0 | 16 | [SetDamageLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetDamageLootFunctionAccessor.java) |
| `SetEnchantmentsLootFunctionAccessor` | interface | — | Mixin | 0 | 19 | [SetEnchantmentsLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetEnchantmentsLootFunctionAccessor.java) |
| `ExplorationMapLootFunctionAccessor` | interface | — | Mixin | 0 | 19 | [ExplorationMapLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ExplorationMapLootFunctionAccessor.java) |
| `FilteredLootFunctionAccessor` | interface | — | Mixin | 0 | 17 | [FilteredLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/FilteredLootFunctionAccessor.java) |
| `ApplyBonusLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [ApplyBonusLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ApplyBonusLootFunctionAccessor.java) |
| `SetNameLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetNameLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetNameLootFunctionAccessor.java) |
| `SetPotionLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetPotionLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetPotionLootFunctionAccessor.java) |
| `ReferenceLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [ReferenceLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ReferenceLootFunctionAccessor.java) |
| `LootPoolEntryAccessor` | interface | — | Mixin | 0 | 14 | [LootPoolEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/LootPoolEntryAccessor.java) |
| `LootTableAccessor` | interface | — | Mixin | 0 | 14 | [LootTableAccessor.java](fzzyhmstrs/emi_loot/mixins/LootTableAccessor.java) |
| `LimitCountLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [LimitCountLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/LimitCountLootFunctionAccessor.java) |
| `SetCountLootFunctionAccessor` | interface | — | Mixin | 0 | 16 | [SetCountLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetCountLootFunctionAccessor.java) |
| `SetBookCoverLootFunctionAccessor` | interface | — | Mixin | 0 | 25 | [SetBookCoverLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetBookCoverLootFunctionAccessor.java) |
| `LootContextTypesAccessor` | interface | — | Mixin | 0 | 17 | [LootContextTypesAccessor.java](fzzyhmstrs/emi_loot/mixins/LootContextTypesAccessor.java) |
| `ConditionalLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [ConditionalLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ConditionalLootFunctionAccessor.java) |
| `AlternativeLootConditionAccessor` | interface | — | Mixin | 0 | 14 | [AlternativeLootConditionAccessor.java](fzzyhmstrs/emi_loot/mixins/AlternativeLootConditionAccessor.java) |
| `AndLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [AndLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/AndLootFunctionAccessor.java) |
| `SetInstrumentLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetInstrumentLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetInstrumentLootFunctionAccessor.java) |
| `SetOminousBottleAmplifierLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [SetOminousBottleAmplifierLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetOminousBottleAmplifierLootFunctionAccessor.java) |
| `LootPoolAccessor` | interface | — | Mixin | 0 | 26 | [LootPoolAccessor.java](fzzyhmstrs/emi_loot/mixins/LootPoolAccessor.java) |
| `LootTableEntryAccessor` | interface | — | Mixin | 0 | 15 | [LootTableEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/LootTableEntryAccessor.java) |
| `SetFireworksLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [SetFireworksLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetFireworksLootFunctionAccessor.java) |
| `SetStewEffectLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetStewEffectLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetStewEffectLootFunctionAccessor.java) |
| `SetFireworkExplosionLootFunctionAccessor` | interface | — | Mixin | 0 | 32 | [SetFireworkExplosionLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetFireworkExplosionLootFunctionAccessor.java) |
| `ModifyContentsLootFunctionAccessor` | interface | — | Mixin | 0 | 13 | [ModifyContentsLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ModifyContentsLootFunctionAccessor.java) |
| `SetAttributesLootFunctionAccessor` | interface | — | Mixin | 0 | 14 | [SetAttributesLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetAttributesLootFunctionAccessor.java) |
| `ChestLootRecipe` | class | EmiRecipe | — | 12 | 186 | [ChestLootRecipe.java](fzzyhmstrs/emi_loot/emi/ChestLootRecipe.java) |
| `GameplayLootRecipe` | class | EmiRecipe | — | 13 | 185 | [GameplayLootRecipe.java](fzzyhmstrs/emi_loot/emi/GameplayLootRecipe.java) |
| `ArchaeologyLootRecipe` | class | EmiRecipe | — | 10 | 160 | [ArchaeologyLootRecipe.java](fzzyhmstrs/emi_loot/emi/ArchaeologyLootRecipe.java) |
| `TextKey` | record | — | — | 32 | 636 | [TextKey.java](fzzyhmstrs/emi_loot/util/TextKey.java) |
| `ItemEntryAccessor` | interface | — | Mixin | 0 | 14 | [ItemEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/ItemEntryAccessor.java) |
| `DataPackContentsMixin` | class | — | Mixin | 1 | 17 | [DataPackContentsMixin.java](fzzyhmstrs/emi_loot/mixins/DataPackContentsMixin.java) |
| `ClientBlockLootTable` | class | AbstractTextKeyParsingClientLootTabl | — | 2 | 138 | [ClientBlockLootTable.java](fzzyhmstrs/emi_loot/client/ClientBlockLootTable.java) |
| `EMILootClientAgnos` | class | — | — | 6 | 44 | [EMILootClientAgnos.java](fzzyhmstrs/emi_loot/EMILootClientAgnos.java) |
| `EMILoot` | class | Config | — | 10 | 254 | [EMILoot.java](fzzyhmstrs/emi_loot/EMILoot.java) |
| `EnchantmentsPredicateAccessor` | interface | — | Mixin | 0 | 14 | [EnchantmentsPredicateAccessor.java](fzzyhmstrs/emi_loot/mixins/EnchantmentsPredicateAccessor.java) |
| `ReloadableRegistriesMixin` | class | — | Mixin | 1 | 31 | [ReloadableRegistriesMixin.java](fzzyhmstrs/emi_loot/mixins/ReloadableRegistriesMixin.java) |
| `BoundedIntUnaryOperatorAccessor` | interface | — | Mixin | 0 | 16 | [BoundedIntUnaryOperatorAccessor.java](fzzyhmstrs/emi_loot/mixins/BoundedIntUnaryOperatorAccessor.java) |
| `LeafEntryAccessor` | interface | — | Mixin | 0 | 17 | [LeafEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/LeafEntryAccessor.java) |
| `TagEntryAccessor` | interface | — | Mixin | 0 | 14 | [TagEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/TagEntryAccessor.java) |
| `VariantPredicateAccessor` | interface | — | Mixin | 0 | 12 | [VariantPredicateAccessor.java](fzzyhmstrs/emi_loot/mixins/VariantPredicateAccessor.java) |
| `CombinedEntryAccessor` | interface | — | Mixin | 0 | 14 | [CombinedEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/CombinedEntryAccessor.java) |
| `BlockLootTableSender` | class | LootSender<ComplexLootPoolBuilder> | — | 5 | 97 | [BlockLootTableSender.java](fzzyhmstrs/emi_loot/server/BlockLootTableSender.java) |
| `ClientLootTablesReceiver` | class | — | — | 5 | 36 | [ClientLootTablesReceiver.java](fzzyhmstrs/emi_loot/client/ClientLootTablesReceiver.java) |
| `AbstractTextKeyParsingClientLootTable` | class | LootReceiver | — | 2 | 272 | [AbstractTextKeyParsingClientLootTable.java](fzzyhmstrs/emi_loot/client/AbstractTextKeyParsingClientLootTable.java) |
| `EMILootAgnos` | class | — | — | 12 | 62 | [EMILootAgnos.java](fzzyhmstrs/emi_loot/EMILootAgnos.java) |
| `EMILootClient` | class | — | — | 1 | 7 | [EMILootClient.java](fzzyhmstrs/emi_loot/EMILootClient.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EMILootClientModEvents` | 2 | `fzzyhmstrs.emi_loot.neoforge.events` | [EMILootClientModEvents.java](fzzyhmstrs/emi_loot/neoforge/events/EMILootClientModEvents.java) |
| `EMILootClientGameEvents` | 1 | `fzzyhmstrs.emi_loot.neoforge.events` | [EMILootClientGameEvents.java](fzzyhmstrs/emi_loot/neoforge/events/EMILootClientGameEvents.java) |
| `EMILootGameEvents` | 1 | `fzzyhmstrs.emi_loot.neoforge.events` | [EMILootGameEvents.java](fzzyhmstrs/emi_loot/neoforge/events/EMILootGameEvents.java) |

## Mixin（40 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `SetItemLootFunctionAccessor` | `SetItemLootFunctionAccessor` | [SetItemLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetItemLootFunctionAccessor.java) |
| `SetCustomDataLootFunctionAccessor` | `SetCustomDataLootFunctionAccessor` | [SetCustomDataLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetCustomDataLootFunctionAccessor.java) |
| `SetLootTableLootFunctionAccessor` | `SetLootTableLootFunctionAccessor` | [SetLootTableLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetLootTableLootFunctionAccessor.java) |
| `SetComponentsLootFunctionAccessor` | `SetComponentsLootFunctionAccessor` | [SetComponentsLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetComponentsLootFunctionAccessor.java) |
| `CopyNameLootFunctionAccessor` | `CopyNameLootFunctionAccessor` | [CopyNameLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/CopyNameLootFunctionAccessor.java) |
| `SetDamageLootFunctionAccessor` | `SetDamageLootFunctionAccessor` | [SetDamageLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetDamageLootFunctionAccessor.java) |
| `SetEnchantmentsLootFunctionAccessor` | `SetEnchantmentsLootFunctionAccessor` | [SetEnchantmentsLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetEnchantmentsLootFunctionAccessor.java) |
| `ExplorationMapLootFunctionAccessor` | `ExplorationMapLootFunctionAccessor` | [ExplorationMapLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ExplorationMapLootFunctionAccessor.java) |
| `FilteredLootFunctionAccessor` | `FilteredLootFunctionAccessor` | [FilteredLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/FilteredLootFunctionAccessor.java) |
| `ApplyBonusLootFunctionAccessor` | `ApplyBonusLootFunctionAccessor` | [ApplyBonusLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ApplyBonusLootFunctionAccessor.java) |
| `SetNameLootFunctionAccessor` | `SetNameLootFunctionAccessor` | [SetNameLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetNameLootFunctionAccessor.java) |
| `SetPotionLootFunctionAccessor` | `SetPotionLootFunctionAccessor` | [SetPotionLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetPotionLootFunctionAccessor.java) |
| `ReferenceLootFunctionAccessor` | `ReferenceLootFunctionAccessor` | [ReferenceLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ReferenceLootFunctionAccessor.java) |
| `LootPoolEntryAccessor` | `LootPoolEntryAccessor` | [LootPoolEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/LootPoolEntryAccessor.java) |
| `LootTableAccessor` | `LootTableAccessor` | [LootTableAccessor.java](fzzyhmstrs/emi_loot/mixins/LootTableAccessor.java) |
| `LimitCountLootFunctionAccessor` | `LimitCountLootFunctionAccessor` | [LimitCountLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/LimitCountLootFunctionAccessor.java) |
| `SetCountLootFunctionAccessor` | `SetCountLootFunctionAccessor` | [SetCountLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetCountLootFunctionAccessor.java) |
| `SetBookCoverLootFunctionAccessor` | `SetBookCoverLootFunctionAccessor` | [SetBookCoverLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetBookCoverLootFunctionAccessor.java) |
| `LootContextTypesAccessor` | `LootContextTypesAccessor` | [LootContextTypesAccessor.java](fzzyhmstrs/emi_loot/mixins/LootContextTypesAccessor.java) |
| `ConditionalLootFunctionAccessor` | `ConditionalLootFunctionAccessor` | [ConditionalLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ConditionalLootFunctionAccessor.java) |
| `AlternativeLootConditionAccessor` | `AlternativeLootConditionAccessor` | [AlternativeLootConditionAccessor.java](fzzyhmstrs/emi_loot/mixins/AlternativeLootConditionAccessor.java) |
| `AndLootFunctionAccessor` | `AndLootFunctionAccessor` | [AndLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/AndLootFunctionAccessor.java) |
| `SetInstrumentLootFunctionAccessor` | `SetInstrumentLootFunctionAccessor` | [SetInstrumentLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetInstrumentLootFunctionAccessor.java) |
| `SetOminousBottleAmplifierLootFunctionAccessor` | `SetOminousBottleAmplifierLootFunctionAccessor` | [SetOminousBottleAmplifierLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetOminousBottleAmplifierLootFunctionAccessor.java) |
| `LootPoolAccessor` | `LootPoolAccessor` | [LootPoolAccessor.java](fzzyhmstrs/emi_loot/mixins/LootPoolAccessor.java) |
| `LootTableEntryAccessor` | `LootTableEntryAccessor` | [LootTableEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/LootTableEntryAccessor.java) |
| `SetFireworksLootFunctionAccessor` | `SetFireworksLootFunctionAccessor` | [SetFireworksLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetFireworksLootFunctionAccessor.java) |
| `SetStewEffectLootFunctionAccessor` | `SetStewEffectLootFunctionAccessor` | [SetStewEffectLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetStewEffectLootFunctionAccessor.java) |
| `SetFireworkExplosionLootFunctionAccessor` | `SetFireworkExplosionLootFunctionAccessor` | [SetFireworkExplosionLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetFireworkExplosionLootFunctionAccessor.java) |
| `ModifyContentsLootFunctionAccessor` | `ModifyContentsLootFunctionAccessor` | [ModifyContentsLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/ModifyContentsLootFunctionAccessor.java) |
| `SetAttributesLootFunctionAccessor` | `SetAttributesLootFunctionAccessor` | [SetAttributesLootFunctionAccessor.java](fzzyhmstrs/emi_loot/mixins/SetAttributesLootFunctionAccessor.java) |
| `ItemEntryAccessor` | `ItemEntryAccessor` | [ItemEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/ItemEntryAccessor.java) |
| `DataPackContentsMixin` | `DataPackContents` | [DataPackContentsMixin.java](fzzyhmstrs/emi_loot/mixins/DataPackContentsMixin.java) |
| `EnchantmentsPredicateAccessor` | `EnchantmentsPredicateAccessor` | [EnchantmentsPredicateAccessor.java](fzzyhmstrs/emi_loot/mixins/EnchantmentsPredicateAccessor.java) |
| `ReloadableRegistriesMixin` | `ReloadableRegistries` | [ReloadableRegistriesMixin.java](fzzyhmstrs/emi_loot/mixins/ReloadableRegistriesMixin.java) |
| `BoundedIntUnaryOperatorAccessor` | `BoundedIntUnaryOperatorAccessor` | [BoundedIntUnaryOperatorAccessor.java](fzzyhmstrs/emi_loot/mixins/BoundedIntUnaryOperatorAccessor.java) |
| `LeafEntryAccessor` | `LeafEntryAccessor` | [LeafEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/LeafEntryAccessor.java) |
| `TagEntryAccessor` | `TagEntryAccessor` | [TagEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/TagEntryAccessor.java) |
| `VariantPredicateAccessor` | `VariantPredicateAccessor` | [VariantPredicateAccessor.java](fzzyhmstrs/emi_loot/mixins/VariantPredicateAccessor.java) |
| `CombinedEntryAccessor` | `CombinedEntryAccessor` | [CombinedEntryAccessor.java](fzzyhmstrs/emi_loot/mixins/CombinedEntryAccessor.java) |

## 按包浏览

- `fzzyhmstrs.emi_loot`（4 类）
- `fzzyhmstrs.emi_loot.client`（13 类）
- `fzzyhmstrs.emi_loot.emi`（7 类）
- `fzzyhmstrs.emi_loot.mixins`（40 类）
- `fzzyhmstrs.emi_loot.neoforge`（3 类）
- `fzzyhmstrs.emi_loot.neoforge.events`（3 类）
- `fzzyhmstrs.emi_loot.neoforge.util`（3 类）
- `fzzyhmstrs.emi_loot.networking`（6 类）
- `fzzyhmstrs.emi_loot.parser`（19 类）
- `fzzyhmstrs.emi_loot.parser.condition`（18 类）
- `fzzyhmstrs.emi_loot.parser.function`（32 类）
- `fzzyhmstrs.emi_loot.parser.processor`（2 类）
- `fzzyhmstrs.emi_loot.parser.registry`（1 类）
- `fzzyhmstrs.emi_loot.server`（13 类）
- `fzzyhmstrs.emi_loot.server.condition`（3 类）
- `fzzyhmstrs.emi_loot.server.function`（2 类）
- `fzzyhmstrs.emi_loot.util`（11 类）
- `fzzyhmstrs.emi_loot.util.stack`（8 类）
