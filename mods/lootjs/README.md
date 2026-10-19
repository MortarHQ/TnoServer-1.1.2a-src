# LootJS（`lootjs`）

> jar：`[战利品KUBEJS附属]lootjs.jar` ｜ 版本：`1.21.1-3.7.0` ｜ 作者：AlmostReliable
> 规模：**115 个类 / 16 个包 / 6755 行** ｜ 事件处理类 0 ｜ Mixin 20 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`kubejs`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.almostreliable.lootjs.mixin` | 18 | `LootItemConditionMixin`、`LootItemFunctionMixin`、`LootItemConditionalFunctionMixin` |
| `com.almostreliable.lootjs.loot` | 12 | `LootTableEvent`、`LootModificationEvent`、`LootEntryList` |
| `com.almostreliable.lootjs.core.entry` | 10 | `ItemLootEntry`、`LootEntry`、`CompositeLootEntry` |
| `com.almostreliable.lootjs.loot.condition` | 10 | `MatchAnyInventorySlot`、`MatchKillerDistance`、`MatchDimension` |
| `com.almostreliable.lootjs.loot.modifier.handler` | 9 | `ReplaceLootAction`、`LootPoolAction`、`AddLootAction` |
| `com.almostreliable.lootjs.loot.table` | 8 | `MutableLootTable`、`MutableLootPool`、`LootTableList` |
| `com.almostreliable.lootjs.kube.wrappers` | 8 | `LootEntryWrapper`、`ItemFilterWrapper`、`BasicWrapper` |
| `com.almostreliable.lootjs` | 7 | `LootJS`、`LootEvents`、`LootModificationsAPI` |
| `com.almostreliable.lootjs.util` | 7 | `LootContextUtils`、`LootObjectList`、`Utils` |
| `com.almostreliable.lootjs.loot.extension` | 6 | `LootItemFunctionExtension`、`LootPoolExtension`、`LootTableExtension` |
| `com.almostreliable.lootjs.core.filters` | 5 | `ItemFilterImpl`、`LootTableFilter`、`ItemFilterWrapper` |
| `com.almostreliable.lootjs.kube` | 5 | `LootModificationEventJS`、`LootTableEventJS`、`LootJSEvent` |
| `com.almostreliable.lootjs.core` | 4 | `LootBucket`、`LootContextInfo`、`LootType` |
| `com.almostreliable.lootjs.loot.modifier` | 3 | `GroupedLootAction`、`LootModifier`、`LootAction` |
| `com.almostreliable.lootjs.mixin.forge` | 2 | `LootModifierManagerMixin`、`CommonHooksMixin` |
| `com.almostreliable.lootjs.loot.condition.builder` | 1 | `DistancePredicateBuilder` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LootJS` | class | — | Mod | 5 | 54 | [LootJS.java](com/almostreliable/lootjs/LootJS.java) |
| `LootModifierManagerMixin` | class | — | Mixin | 0 | 41 | [LootModifierManagerMixin.java](com/almostreliable/lootjs/mixin/forge/LootModifierManagerMixin.java) |
| `LootEvents` | class | — | — | 4 | 50 | [LootEvents.java](com/almostreliable/lootjs/LootEvents.java) |
| `LootItemConditionMixin` | interface | — | Mixin、RemapPrefixForJS | 0 | 37 | [LootItemConditionMixin.java](com/almostreliable/lootjs/mixin/LootItemConditionMixin.java) |
| `LootItemFunctionMixin` | interface | — | Mixin | 0 | 18 | [LootItemFunctionMixin.java](com/almostreliable/lootjs/mixin/LootItemFunctionMixin.java) |
| `LootItemConditionalFunctionMixin` | class | LootItemFunctionExtension | Mixin | 0 | 37 | [LootItemConditionalFunctionMixin.java](com/almostreliable/lootjs/mixin/LootItemConditionalFunctionMixin.java) |
| `LootParamsBuilderMixin` | class | — | Mixin | 0 | 21 | [LootParamsBuilderMixin.java](com/almostreliable/lootjs/mixin/LootParamsBuilderMixin.java) |
| `LootTableMixin` | class | PostLootActionOwner | Mixin | 1 | 145 | [LootTableMixin.java](com/almostreliable/lootjs/mixin/LootTableMixin.java) |
| `LootParamsMixin` | class | LootParamsExtension | Mixin | 0 | 24 | [LootParamsMixin.java](com/almostreliable/lootjs/mixin/LootParamsMixin.java) |
| `LootContextMixin` | class | LootContextExtension | Mixin | 0 | 44 | [LootContextMixin.java](com/almostreliable/lootjs/mixin/LootContextMixin.java) |
| `LootPoolMixin` | class | LootPoolExtension | Mixin | 0 | 91 | [LootPoolMixin.java](com/almostreliable/lootjs/mixin/LootPoolMixin.java) |
| `LootTableEvent` | class | — | — | 16 | 152 | [LootTableEvent.java](com/almostreliable/lootjs/loot/LootTableEvent.java) |
| `EntityPredicateMixin` | class | — | Mixin | 1 | 22 | [EntityPredicateMixin.java](com/almostreliable/lootjs/mixin/EntityPredicateMixin.java) |
| `ItemPredicateMixin` | class | ItemFilter | Mixin | 0 | 10 | [ItemPredicateMixin.java](com/almostreliable/lootjs/mixin/ItemPredicateMixin.java) |
| `LootModificationEvent` | class | — | — | 13 | 129 | [LootModificationEvent.java](com/almostreliable/lootjs/loot/LootModificationEvent.java) |
| `LootEntryList` | class | ListHolder | — | 22 | 202 | [LootEntryList.java](com/almostreliable/lootjs/loot/LootEntryList.java) |
| `ItemFilterImpl` | class | ItemFilter | — | 20 | 227 | [ItemFilterImpl.java](com/almostreliable/lootjs/core/filters/ItemFilterImpl.java) |
| `MutableLootTable` | class | LootEntriesTransformer | — | 22 | 194 | [MutableLootTable.java](com/almostreliable/lootjs/loot/table/MutableLootTable.java) |
| `GroupedLootAction` | class | GroupedLootAction | — | 18 | 215 | [GroupedLootAction.java](com/almostreliable/lootjs/loot/modifier/GroupedLootAction.java) |
| `LootModificationEventJS` | class | LootModificationEvent | — | 1 | 31 | [LootModificationEventJS.java](com/almostreliable/lootjs/kube/LootModificationEventJS.java) |
| `LootTableEventJS` | class | LootTableEvent | — | 1 | 17 | [LootTableEventJS.java](com/almostreliable/lootjs/kube/LootTableEventJS.java) |
| `LootJSEvent` | interface | — | — | 0 | 11 | [LootJSEvent.java](com/almostreliable/lootjs/kube/LootJSEvent.java) |
| `LootBucket` | class | Iterable<ItemStack> | — | 17 | 154 | [LootBucket.java](com/almostreliable/lootjs/core/LootBucket.java) |
| `ItemLootEntry` | class | AbstractSimpleLootEntry | — | 7 | 105 | [ItemLootEntry.java](com/almostreliable/lootjs/core/entry/ItemLootEntry.java) |
| `CreeperMixin` | class | — | Mixin | 1 | 25 | [CreeperMixin.java](com/almostreliable/lootjs/mixin/CreeperMixin.java) |
| `ZombieMixin` | class | — | Mixin | 1 | 25 | [ZombieMixin.java](com/almostreliable/lootjs/mixin/ZombieMixin.java) |
| `ReloadableServerRegistriesMixin` | class | — | Mixin | 0 | 40 | [ReloadableServerRegistriesMixin.java](com/almostreliable/lootjs/mixin/ReloadableServerRegistriesMixin.java) |
| `SkeletonMixin` | class | — | Mixin | 1 | 25 | [SkeletonMixin.java](com/almostreliable/lootjs/mixin/SkeletonMixin.java) |
| `HolderSetCodecMixin` | class | — | Mixin | 0 | 72 | [HolderSetCodecMixin.java](com/almostreliable/lootjs/mixin/HolderSetCodecMixin.java) |
| `CompositeEntryBaseMixin` | class | CompositeEntryBaseExtension | Mixin | 1 | 52 | [CompositeEntryBaseMixin.java](com/almostreliable/lootjs/mixin/CompositeEntryBaseMixin.java) |
| `WitherBossMixin` | class | — | Mixin | 1 | 24 | [WitherBossMixin.java](com/almostreliable/lootjs/mixin/WitherBossMixin.java) |
| `SetComponentsFunctionAccessor` | interface | — | Mixin | 0 | 18 | [SetComponentsFunctionAccessor.java](com/almostreliable/lootjs/mixin/SetComponentsFunctionAccessor.java) |
| `CommonHooksMixin` | class | — | Mixin | 1 | 27 | [CommonHooksMixin.java](com/almostreliable/lootjs/mixin/forge/CommonHooksMixin.java) |
| `LootEntry` | interface | LootEntry | — | 6 | 237 | [LootEntry.java](com/almostreliable/lootjs/core/entry/LootEntry.java) |
| `CompositeLootEntry` | class | LootEntry | — | 11 | 101 | [CompositeLootEntry.java](com/almostreliable/lootjs/core/entry/CompositeLootEntry.java) |
| `LootFunctionList` | class | ListHolder | — | 11 | 112 | [LootFunctionList.java](com/almostreliable/lootjs/loot/LootFunctionList.java) |
| `LootConditionList` | class | ListHolder | — | 10 | 99 | [LootConditionList.java](com/almostreliable/lootjs/loot/LootConditionList.java) |
| `LootItemFunctionExtension` | interface | — | RemapPrefixForJS | 0 | 12 | [LootItemFunctionExtension.java](com/almostreliable/lootjs/loot/extension/LootItemFunctionExtension.java) |
| `MutableLootPool` | class | LootEntriesTransformer | — | 13 | 102 | [MutableLootPool.java](com/almostreliable/lootjs/loot/table/MutableLootPool.java) |
| `LootModificationsAPI` | class | — | — | 6 | 76 | [LootModificationsAPI.java](com/almostreliable/lootjs/LootModificationsAPI.java) |
| `BuildConfig` | class | — | — | 0 | 11 | [BuildConfig.java](com/almostreliable/lootjs/BuildConfig.java) |
| `LootJSPlugin` | class | KubeJSPlugin | — | 5 | 125 | [LootJSPlugin.java](com/almostreliable/lootjs/kube/LootJSPlugin.java) |
| `LootContextInfo` | class | — | — | 9 | 110 | [LootContextInfo.java](com/almostreliable/lootjs/core/LootContextInfo.java) |
| `TagLootEntry` | class | AbstractSimpleLootEntry | — | 7 | 59 | [TagLootEntry.java](com/almostreliable/lootjs/core/entry/TagLootEntry.java) |
| `AbstractSimpleLootEntry` | class | SimpleLootEntry | — | 9 | 86 | [AbstractSimpleLootEntry.java](com/almostreliable/lootjs/core/entry/AbstractSimpleLootEntry.java) |
| `LootConditionsContainer` | interface | — | — | 0 | 276 | [LootConditionsContainer.java](com/almostreliable/lootjs/loot/LootConditionsContainer.java) |
| `LootTableList` | class | LootEntriesTransformer | — | 9 | 64 | [LootTableList.java](com/almostreliable/lootjs/loot/table/LootTableList.java) |
| `LootTracker` | class | Consumer<ItemStack> | — | 5 | 43 | [LootTracker.java](com/almostreliable/lootjs/loot/table/LootTracker.java) |
| `LootModifier` | class | GroupedLootAction | — | 9 | 115 | [LootModifier.java](com/almostreliable/lootjs/loot/modifier/LootModifier.java) |
| `LootContextUtils` | class | — | — | 2 | 36 | [LootContextUtils.java](com/almostreliable/lootjs/util/LootContextUtils.java) |
| `LootJSConditions` | class | — | — | 1 | 55 | [LootJSConditions.java](com/almostreliable/lootjs/LootJSConditions.java) |
| `LootEntryWrapper` | class | — | — | 2 | 38 | [LootEntryWrapper.java](com/almostreliable/lootjs/kube/wrappers/LootEntryWrapper.java) |
| `LootType` | enum | — | — | 2 | 49 | [LootType.java](com/almostreliable/lootjs/core/LootType.java) |
| `LootConditionTypes` | class | — | — | 1 | 12 | [LootConditionTypes.java](com/almostreliable/lootjs/core/LootConditionTypes.java) |
| `LootTableFilter` | interface | LootTableFilter | — | 4 | 37 | [LootTableFilter.java](com/almostreliable/lootjs/core/filters/LootTableFilter.java) |
| `TableReferenceLootEntry` | class | AbstractSimpleLootEntry | — | 4 | 40 | [TableReferenceLootEntry.java](com/almostreliable/lootjs/core/entry/TableReferenceLootEntry.java) |
| `SimpleLootEntry` | interface | — | — | 0 | 57 | [SimpleLootEntry.java](com/almostreliable/lootjs/core/entry/SimpleLootEntry.java) |
| `DynamicLootEntry` | class | AbstractSimpleLootEntry | — | 3 | 25 | [DynamicLootEntry.java](com/almostreliable/lootjs/core/entry/DynamicLootEntry.java) |
| `EmptyLootEntry` | class | AbstractSimpleLootEntry | — | 3 | 34 | [EmptyLootEntry.java](com/almostreliable/lootjs/core/entry/EmptyLootEntry.java) |
| `SingleLootEntry` | interface | — | — | 0 | 5 | [SingleLootEntry.java](com/almostreliable/lootjs/core/entry/SingleLootEntry.java) |

## Mixin（20 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LootModifierManagerMixin` | `LootModifierManager` | [LootModifierManagerMixin.java](com/almostreliable/lootjs/mixin/forge/LootModifierManagerMixin.java) |
| `LootItemConditionMixin` | `LootItemCondition` | [LootItemConditionMixin.java](com/almostreliable/lootjs/mixin/LootItemConditionMixin.java) |
| `LootItemFunctionMixin` | `LootItemFunction` | [LootItemFunctionMixin.java](com/almostreliable/lootjs/mixin/LootItemFunctionMixin.java) |
| `LootItemConditionalFunctionMixin` | `LootItemConditionalFunction` | [LootItemConditionalFunctionMixin.java](com/almostreliable/lootjs/mixin/LootItemConditionalFunctionMixin.java) |
| `LootParamsBuilderMixin` | `LootParamsBuilder` | [LootParamsBuilderMixin.java](com/almostreliable/lootjs/mixin/LootParamsBuilderMixin.java) |
| `LootTableMixin` | `LootTable` | [LootTableMixin.java](com/almostreliable/lootjs/mixin/LootTableMixin.java) |
| `LootParamsMixin` | `LootParams` | [LootParamsMixin.java](com/almostreliable/lootjs/mixin/LootParamsMixin.java) |
| `LootContextMixin` | `LootContext` | [LootContextMixin.java](com/almostreliable/lootjs/mixin/LootContextMixin.java) |
| `LootPoolMixin` | `LootPool` | [LootPoolMixin.java](com/almostreliable/lootjs/mixin/LootPoolMixin.java) |
| `EntityPredicateMixin` | `EntityPredicate` | [EntityPredicateMixin.java](com/almostreliable/lootjs/mixin/EntityPredicateMixin.java) |
| `ItemPredicateMixin` | `ItemPredicate` | [ItemPredicateMixin.java](com/almostreliable/lootjs/mixin/ItemPredicateMixin.java) |
| `CreeperMixin` | `Creeper` | [CreeperMixin.java](com/almostreliable/lootjs/mixin/CreeperMixin.java) |
| `ZombieMixin` | `Zombie` | [ZombieMixin.java](com/almostreliable/lootjs/mixin/ZombieMixin.java) |
| `ReloadableServerRegistriesMixin` | `ReloadableServerRegistries` | [ReloadableServerRegistriesMixin.java](com/almostreliable/lootjs/mixin/ReloadableServerRegistriesMixin.java) |
| `SkeletonMixin` | `Skeleton` | [SkeletonMixin.java](com/almostreliable/lootjs/mixin/SkeletonMixin.java) |
| `HolderSetCodecMixin` | `HolderSetCodec` | [HolderSetCodecMixin.java](com/almostreliable/lootjs/mixin/HolderSetCodecMixin.java) |
| `CompositeEntryBaseMixin` | `CompositeEntryBase` | [CompositeEntryBaseMixin.java](com/almostreliable/lootjs/mixin/CompositeEntryBaseMixin.java) |
| `WitherBossMixin` | `WitherBoss` | [WitherBossMixin.java](com/almostreliable/lootjs/mixin/WitherBossMixin.java) |
| `SetComponentsFunctionAccessor` | `SetComponentsFunctionAccessor` | [SetComponentsFunctionAccessor.java](com/almostreliable/lootjs/mixin/SetComponentsFunctionAccessor.java) |
| `CommonHooksMixin` | `CommonHooks` | [CommonHooksMixin.java](com/almostreliable/lootjs/mixin/forge/CommonHooksMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BuildConfig` | — | [BuildConfig.java](com/almostreliable/lootjs/BuildConfig.java) |

## 全部类（115）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LootJS` | class | — | Mod | 5 | 54 | [LootJS.java](com/almostreliable/lootjs/LootJS.java) |
| `LootModifierManagerMixin` | class | — | Mixin | 0 | 41 | [LootModifierManagerMixin.java](com/almostreliable/lootjs/mixin/forge/LootModifierManagerMixin.java) |
| `LootEvents` | class | — | — | 4 | 50 | [LootEvents.java](com/almostreliable/lootjs/LootEvents.java) |
| `LootItemConditionMixin` | interface | — | Mixin、RemapPrefixForJS | 0 | 37 | [LootItemConditionMixin.java](com/almostreliable/lootjs/mixin/LootItemConditionMixin.java) |
| `LootItemFunctionMixin` | interface | — | Mixin | 0 | 18 | [LootItemFunctionMixin.java](com/almostreliable/lootjs/mixin/LootItemFunctionMixin.java) |
| `LootItemConditionalFunctionMixin` | class | LootItemFunctionExtension | Mixin | 0 | 37 | [LootItemConditionalFunctionMixin.java](com/almostreliable/lootjs/mixin/LootItemConditionalFunctionMixin.java) |
| `LootParamsBuilderMixin` | class | — | Mixin | 0 | 21 | [LootParamsBuilderMixin.java](com/almostreliable/lootjs/mixin/LootParamsBuilderMixin.java) |
| `LootTableMixin` | class | PostLootActionOwner | Mixin | 1 | 145 | [LootTableMixin.java](com/almostreliable/lootjs/mixin/LootTableMixin.java) |
| `LootParamsMixin` | class | LootParamsExtension | Mixin | 0 | 24 | [LootParamsMixin.java](com/almostreliable/lootjs/mixin/LootParamsMixin.java) |
| `LootContextMixin` | class | LootContextExtension | Mixin | 0 | 44 | [LootContextMixin.java](com/almostreliable/lootjs/mixin/LootContextMixin.java) |
| `LootPoolMixin` | class | LootPoolExtension | Mixin | 0 | 91 | [LootPoolMixin.java](com/almostreliable/lootjs/mixin/LootPoolMixin.java) |
| `LootTableEvent` | class | — | — | 16 | 152 | [LootTableEvent.java](com/almostreliable/lootjs/loot/LootTableEvent.java) |
| `EntityPredicateMixin` | class | — | Mixin | 1 | 22 | [EntityPredicateMixin.java](com/almostreliable/lootjs/mixin/EntityPredicateMixin.java) |
| `ItemPredicateMixin` | class | ItemFilter | Mixin | 0 | 10 | [ItemPredicateMixin.java](com/almostreliable/lootjs/mixin/ItemPredicateMixin.java) |
| `LootModificationEvent` | class | — | — | 13 | 129 | [LootModificationEvent.java](com/almostreliable/lootjs/loot/LootModificationEvent.java) |
| `LootEntryList` | class | ListHolder | — | 22 | 202 | [LootEntryList.java](com/almostreliable/lootjs/loot/LootEntryList.java) |
| `ItemFilterImpl` | class | ItemFilter | — | 20 | 227 | [ItemFilterImpl.java](com/almostreliable/lootjs/core/filters/ItemFilterImpl.java) |
| `MutableLootTable` | class | LootEntriesTransformer | — | 22 | 194 | [MutableLootTable.java](com/almostreliable/lootjs/loot/table/MutableLootTable.java) |
| `GroupedLootAction` | class | GroupedLootAction | — | 18 | 215 | [GroupedLootAction.java](com/almostreliable/lootjs/loot/modifier/GroupedLootAction.java) |
| `LootModificationEventJS` | class | LootModificationEvent | — | 1 | 31 | [LootModificationEventJS.java](com/almostreliable/lootjs/kube/LootModificationEventJS.java) |
| `LootTableEventJS` | class | LootTableEvent | — | 1 | 17 | [LootTableEventJS.java](com/almostreliable/lootjs/kube/LootTableEventJS.java) |
| `LootJSEvent` | interface | — | — | 0 | 11 | [LootJSEvent.java](com/almostreliable/lootjs/kube/LootJSEvent.java) |
| `LootBucket` | class | Iterable<ItemStack> | — | 17 | 154 | [LootBucket.java](com/almostreliable/lootjs/core/LootBucket.java) |
| `ItemLootEntry` | class | AbstractSimpleLootEntry | — | 7 | 105 | [ItemLootEntry.java](com/almostreliable/lootjs/core/entry/ItemLootEntry.java) |
| `CreeperMixin` | class | — | Mixin | 1 | 25 | [CreeperMixin.java](com/almostreliable/lootjs/mixin/CreeperMixin.java) |
| `ZombieMixin` | class | — | Mixin | 1 | 25 | [ZombieMixin.java](com/almostreliable/lootjs/mixin/ZombieMixin.java) |
| `ReloadableServerRegistriesMixin` | class | — | Mixin | 0 | 40 | [ReloadableServerRegistriesMixin.java](com/almostreliable/lootjs/mixin/ReloadableServerRegistriesMixin.java) |
| `SkeletonMixin` | class | — | Mixin | 1 | 25 | [SkeletonMixin.java](com/almostreliable/lootjs/mixin/SkeletonMixin.java) |
| `HolderSetCodecMixin` | class | — | Mixin | 0 | 72 | [HolderSetCodecMixin.java](com/almostreliable/lootjs/mixin/HolderSetCodecMixin.java) |
| `CompositeEntryBaseMixin` | class | CompositeEntryBaseExtension | Mixin | 1 | 52 | [CompositeEntryBaseMixin.java](com/almostreliable/lootjs/mixin/CompositeEntryBaseMixin.java) |
| `WitherBossMixin` | class | — | Mixin | 1 | 24 | [WitherBossMixin.java](com/almostreliable/lootjs/mixin/WitherBossMixin.java) |
| `SetComponentsFunctionAccessor` | interface | — | Mixin | 0 | 18 | [SetComponentsFunctionAccessor.java](com/almostreliable/lootjs/mixin/SetComponentsFunctionAccessor.java) |
| `CommonHooksMixin` | class | — | Mixin | 1 | 27 | [CommonHooksMixin.java](com/almostreliable/lootjs/mixin/forge/CommonHooksMixin.java) |
| `LootEntry` | interface | LootEntry | — | 6 | 237 | [LootEntry.java](com/almostreliable/lootjs/core/entry/LootEntry.java) |
| `CompositeLootEntry` | class | LootEntry | — | 11 | 101 | [CompositeLootEntry.java](com/almostreliable/lootjs/core/entry/CompositeLootEntry.java) |
| `LootFunctionList` | class | ListHolder | — | 11 | 112 | [LootFunctionList.java](com/almostreliable/lootjs/loot/LootFunctionList.java) |
| `LootConditionList` | class | ListHolder | — | 10 | 99 | [LootConditionList.java](com/almostreliable/lootjs/loot/LootConditionList.java) |
| `LootItemFunctionExtension` | interface | — | RemapPrefixForJS | 0 | 12 | [LootItemFunctionExtension.java](com/almostreliable/lootjs/loot/extension/LootItemFunctionExtension.java) |
| `MutableLootPool` | class | LootEntriesTransformer | — | 13 | 102 | [MutableLootPool.java](com/almostreliable/lootjs/loot/table/MutableLootPool.java) |
| `LootModificationsAPI` | class | — | — | 6 | 76 | [LootModificationsAPI.java](com/almostreliable/lootjs/LootModificationsAPI.java) |
| `BuildConfig` | class | — | — | 0 | 11 | [BuildConfig.java](com/almostreliable/lootjs/BuildConfig.java) |
| `LootJSPlugin` | class | KubeJSPlugin | — | 5 | 125 | [LootJSPlugin.java](com/almostreliable/lootjs/kube/LootJSPlugin.java) |
| `LootContextInfo` | class | — | — | 9 | 110 | [LootContextInfo.java](com/almostreliable/lootjs/core/LootContextInfo.java) |
| `TagLootEntry` | class | AbstractSimpleLootEntry | — | 7 | 59 | [TagLootEntry.java](com/almostreliable/lootjs/core/entry/TagLootEntry.java) |
| `AbstractSimpleLootEntry` | class | SimpleLootEntry | — | 9 | 86 | [AbstractSimpleLootEntry.java](com/almostreliable/lootjs/core/entry/AbstractSimpleLootEntry.java) |
| `LootConditionsContainer` | interface | — | — | 0 | 276 | [LootConditionsContainer.java](com/almostreliable/lootjs/loot/LootConditionsContainer.java) |
| `LootTableList` | class | LootEntriesTransformer | — | 9 | 64 | [LootTableList.java](com/almostreliable/lootjs/loot/table/LootTableList.java) |
| `LootTracker` | class | Consumer<ItemStack> | — | 5 | 43 | [LootTracker.java](com/almostreliable/lootjs/loot/table/LootTracker.java) |
| `LootModifier` | class | GroupedLootAction | — | 9 | 115 | [LootModifier.java](com/almostreliable/lootjs/loot/modifier/LootModifier.java) |
| `LootContextUtils` | class | — | — | 2 | 36 | [LootContextUtils.java](com/almostreliable/lootjs/util/LootContextUtils.java) |
| `LootJSConditions` | class | — | — | 1 | 55 | [LootJSConditions.java](com/almostreliable/lootjs/LootJSConditions.java) |
| `LootEntryWrapper` | class | — | — | 2 | 38 | [LootEntryWrapper.java](com/almostreliable/lootjs/kube/wrappers/LootEntryWrapper.java) |
| `LootType` | enum | — | — | 2 | 49 | [LootType.java](com/almostreliable/lootjs/core/LootType.java) |
| `LootConditionTypes` | class | — | — | 1 | 12 | [LootConditionTypes.java](com/almostreliable/lootjs/core/LootConditionTypes.java) |
| `LootTableFilter` | interface | LootTableFilter | — | 4 | 37 | [LootTableFilter.java](com/almostreliable/lootjs/core/filters/LootTableFilter.java) |
| `TableReferenceLootEntry` | class | AbstractSimpleLootEntry | — | 4 | 40 | [TableReferenceLootEntry.java](com/almostreliable/lootjs/core/entry/TableReferenceLootEntry.java) |
| `SimpleLootEntry` | interface | — | — | 0 | 57 | [SimpleLootEntry.java](com/almostreliable/lootjs/core/entry/SimpleLootEntry.java) |
| `DynamicLootEntry` | class | AbstractSimpleLootEntry | — | 3 | 25 | [DynamicLootEntry.java](com/almostreliable/lootjs/core/entry/DynamicLootEntry.java) |
| `EmptyLootEntry` | class | AbstractSimpleLootEntry | — | 3 | 34 | [EmptyLootEntry.java](com/almostreliable/lootjs/core/entry/EmptyLootEntry.java) |
| `SingleLootEntry` | interface | — | — | 0 | 5 | [SingleLootEntry.java](com/almostreliable/lootjs/core/entry/SingleLootEntry.java) |
| `LootActionContainer` | interface | — | — | 0 | 102 | [LootActionContainer.java](com/almostreliable/lootjs/loot/LootActionContainer.java) |
| `AddAttributesFunction` | class | LootItemFunction | — | 15 | 177 | [AddAttributesFunction.java](com/almostreliable/lootjs/loot/AddAttributesFunction.java) |
| `LootCondition` | class | LootConditionsContainer<LootItemCond | — | 1 | 12 | [LootCondition.java](com/almostreliable/lootjs/loot/LootCondition.java) |
| `LootFunctionsContainer` | interface | — | — | 0 | 188 | [LootFunctionsContainer.java](com/almostreliable/lootjs/loot/LootFunctionsContainer.java) |
| `LootFunction` | record | LootFunctionsContainer<LootItemFunct | — | 1 | 10 | [LootFunction.java](com/almostreliable/lootjs/loot/LootFunction.java) |
| `LootPoolExtension` | interface | — | — | 0 | 41 | [LootPoolExtension.java](com/almostreliable/lootjs/loot/extension/LootPoolExtension.java) |
| `LootTableExtension` | interface | — | — | 0 | 35 | [LootTableExtension.java](com/almostreliable/lootjs/loot/extension/LootTableExtension.java) |
| `LootParamsExtension` | interface | — | RemapPrefixForJS | 0 | 12 | [LootParamsExtension.java](com/almostreliable/lootjs/loot/extension/LootParamsExtension.java) |
| `LootContextExtension` | interface | — | RemapPrefixForJS | 0 | 93 | [LootContextExtension.java](com/almostreliable/lootjs/loot/extension/LootContextExtension.java) |
| `LootEntryAppender` | interface | — | — | 0 | 17 | [LootEntryAppender.java](com/almostreliable/lootjs/loot/table/LootEntryAppender.java) |
| `LootEntriesTransformer` | interface | — | — | 0 | 92 | [LootEntriesTransformer.java](com/almostreliable/lootjs/loot/table/LootEntriesTransformer.java) |
| `PostLootAction` | interface | — | — | 0 | 10 | [PostLootAction.java](com/almostreliable/lootjs/loot/table/PostLootAction.java) |
| `PostLootActionOwner` | interface | — | — | 0 | 11 | [PostLootActionOwner.java](com/almostreliable/lootjs/loot/table/PostLootActionOwner.java) |
| `LootAction` | interface | — | — | 0 | 10 | [LootAction.java](com/almostreliable/lootjs/loot/modifier/LootAction.java) |
| `ReplaceLootAction` | record | LootAction | — | 1 | 17 | [ReplaceLootAction.java](com/almostreliable/lootjs/loot/modifier/handler/ReplaceLootAction.java) |
| `LootPoolAction` | record | LootAction | — | 1 | 14 | [LootPoolAction.java](com/almostreliable/lootjs/loot/modifier/handler/LootPoolAction.java) |
| `AddLootAction` | class | LootAction | — | 2 | 31 | [AddLootAction.java](com/almostreliable/lootjs/loot/modifier/handler/AddLootAction.java) |
| `ModifyLootAction` | record | LootAction | — | 1 | 20 | [ModifyLootAction.java](com/almostreliable/lootjs/loot/modifier/handler/ModifyLootAction.java) |
| `RemoveLootAction` | record | LootAction | — | 1 | 14 | [RemoveLootAction.java](com/almostreliable/lootjs/loot/modifier/handler/RemoveLootAction.java) |
| `LootObjectList` | class | ArrayList | — | 4 | 45 | [LootObjectList.java](com/almostreliable/lootjs/util/LootObjectList.java) |
| `Utils` | class | — | — | 10 | 72 | [Utils.java](com/almostreliable/lootjs/util/Utils.java) |
| `ItemFilterWrapper` | class | — | — | 2 | 70 | [ItemFilterWrapper.java](com/almostreliable/lootjs/kube/wrappers/ItemFilterWrapper.java) |
| `BasicWrapper` | class | — | — | 14 | 172 | [BasicWrapper.java](com/almostreliable/lootjs/kube/wrappers/BasicWrapper.java) |
| `ItemPredicateWrapper` | class | — | — | 1 | 42 | [ItemPredicateWrapper.java](com/almostreliable/lootjs/kube/wrappers/ItemPredicateWrapper.java) |
| `ItemFilterWrapper` | record | ItemSubPredicate | — | 2 | 21 | [ItemFilterWrapper.java](com/almostreliable/lootjs/core/filters/ItemFilterWrapper.java) |
| `ItemFilter` | interface | — | — | 0 | 123 | [ItemFilter.java](com/almostreliable/lootjs/core/filters/ItemFilter.java) |
| `ListHolder` | class | Iterable<W> | — | 13 | 70 | [ListHolder.java](com/almostreliable/lootjs/util/ListHolder.java) |
| `BlockFilter` | interface | — | — | 0 | 14 | [BlockFilter.java](com/almostreliable/lootjs/util/BlockFilter.java) |
| `IdFilter` | interface | IdFilter | — | 8 | 54 | [IdFilter.java](com/almostreliable/lootjs/core/filters/IdFilter.java) |
| `DistancePredicateBuilder` | class | — | — | 6 | 42 | [DistancePredicateBuilder.java](com/almostreliable/lootjs/loot/condition/builder/DistancePredicateBuilder.java) |
| `DebugInfo` | class | — | — | 5 | 42 | [DebugInfo.java](com/almostreliable/lootjs/util/DebugInfo.java) |
| `PlatformLoader` | class | — | — | 0 | 12 | [PlatformLoader.java](com/almostreliable/lootjs/PlatformLoader.java) |
| `LookupProvider` | class | Provider | — | 2 | 26 | [LookupProvider.java](com/almostreliable/lootjs/LookupProvider.java) |
| `KubeOps` | class | RegistryOps | — | 1 | 16 | [KubeOps.java](com/almostreliable/lootjs/kube/KubeOps.java) |
| `StatePropsPredicateWrapper` | class | — | — | 3 | 61 | [StatePropsPredicateWrapper.java](com/almostreliable/lootjs/kube/wrappers/StatePropsPredicateWrapper.java) |
| `MobEffectsPredicateWrapper` | class | — | — | 1 | 51 | [MobEffectsPredicateWrapper.java](com/almostreliable/lootjs/kube/wrappers/MobEffectsPredicateWrapper.java) |
| `MinMaxBoundsWrapper` | class | — | — | 3 | 58 | [MinMaxBoundsWrapper.java](com/almostreliable/lootjs/kube/wrappers/MinMaxBoundsWrapper.java) |
| `NumberProviderWrapper` | class | — | — | 3 | 21 | [NumberProviderWrapper.java](com/almostreliable/lootjs/kube/wrappers/NumberProviderWrapper.java) |
| `Predicates` | interface | — | — | 0 | 50 | [Predicates.java](com/almostreliable/lootjs/loot/Predicates.java) |
| `CompositeEntryBaseExtension` | interface | — | — | 0 | 13 | [CompositeEntryBaseExtension.java](com/almostreliable/lootjs/loot/extension/CompositeEntryBaseExtension.java) |
| `DropExperienceAction` | class | LootAction | — | 1 | 26 | [DropExperienceAction.java](com/almostreliable/lootjs/loot/modifier/handler/DropExperienceAction.java) |
| `LightningStrikeAction` | class | LootAction | — | 1 | 34 | [LightningStrikeAction.java](com/almostreliable/lootjs/loot/modifier/handler/LightningStrikeAction.java) |
| `CustomPlayerAction` | class | LootAction | — | 1 | 27 | [CustomPlayerAction.java](com/almostreliable/lootjs/loot/modifier/handler/CustomPlayerAction.java) |
| `ExplodeAction` | class | LootAction | — | 1 | 26 | [ExplodeAction.java](com/almostreliable/lootjs/loot/modifier/handler/ExplodeAction.java) |
| `MatchAnyInventorySlot` | record | LootItemCondition | — | 2 | 35 | [MatchAnyInventorySlot.java](com/almostreliable/lootjs/loot/condition/MatchAnyInventorySlot.java) |
| `MatchKillerDistance` | record | LootItemCondition | — | 2 | 24 | [MatchKillerDistance.java](com/almostreliable/lootjs/loot/condition/MatchKillerDistance.java) |
| `MatchDimension` | record | LootItemCondition | — | 3 | 30 | [MatchDimension.java](com/almostreliable/lootjs/loot/condition/MatchDimension.java) |
| `MatchStructure` | record | LootItemCondition | — | 2 | 44 | [MatchStructure.java](com/almostreliable/lootjs/loot/condition/MatchStructure.java) |
| `IsLightLevel` | record | LootItemCondition | — | 2 | 27 | [IsLightLevel.java](com/almostreliable/lootjs/loot/condition/IsLightLevel.java) |
| `MatchBiome` | record | LootItemCondition | — | 2 | 30 | [MatchBiome.java](com/almostreliable/lootjs/loot/condition/MatchBiome.java) |
| `MatchEquipmentSlot` | record | LootItemCondition | — | 2 | 22 | [MatchEquipmentSlot.java](com/almostreliable/lootjs/loot/condition/MatchEquipmentSlot.java) |
| `PlayerParamPredicate` | class | LootItemCondition | — | 2 | 29 | [PlayerParamPredicate.java](com/almostreliable/lootjs/loot/condition/PlayerParamPredicate.java) |
| `CustomParamPredicate` | class | LootItemCondition | — | 2 | 31 | [CustomParamPredicate.java](com/almostreliable/lootjs/loot/condition/CustomParamPredicate.java) |
| `MatchPlayer` | record | LootItemCondition | — | 2 | 24 | [MatchPlayer.java](com/almostreliable/lootjs/loot/condition/MatchPlayer.java) |
| `NullableFunction` | interface | — | — | 0 | 10 | [NullableFunction.java](com/almostreliable/lootjs/util/NullableFunction.java) |
