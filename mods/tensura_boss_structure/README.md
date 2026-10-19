# Tensura Boss Structure（`tensura_boss_structure`）

> jar：`[转生附属]tensura_boss_structure.jar` ｜ 版本：`1.0.3.3` ｜ 作者：AblazeAQZL
> 规模：**30 个类 / 15 个包 / 1736 行** ｜ 事件处理类 4 ｜ Mixin 6 ｜ 注册/管理类 3

**依赖**：`minecraft`、`tensura`、`tensura_neb`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tensurabossstructure.mixin` | 6 | `StructureTemplatePoolAccessor`、`OtherworlderPoolElementMixin`、`AccessorStructureProcessor` |
| `com.tensurabossstructure.registry` | 6 | `ModLootModifiers`、`ModBlockEntities`、`ModBlocks` |
| `com.tensurabossstructure.event` | 3 | `VillagerTradeHandler`、`SpawnerRespawnHandler`、`ServerStartingHandler` |
| `com.tensurabossstructure.item` | 2 | `StructureMapItem`、`SlimeKingSummonItem` |
| `com.tensurabossstructure.world.processor` | 2 | `SmartGrassProcessor`、`GroundLevelProcessor` |
| `com.tensurabossstructure.world.structure` | 2 | `JigsawMinHeightStructure`、`WaterloggingFixProcessor` |
| `com.tensurabossstructure.client` | 1 | `TensuraBossStructureClient` |
| `com.tensurabossstructure` | 1 | `TensuraBossStructure` |
| `com.tensurabossstructure.block.entity` | 1 | `BossSpawnerBlockEntity` |
| `com.tensurabossstructure.loot` | 1 | `AddItemLootModifier` |
| `com.tensurabossstructure.config` | 1 | `BossConfig` |
| `com.tensurabossstructure.block` | 1 | `BossSpawnerBlock` |
| `com.tensurabossstructure.world` | 1 | `SpawnerRespawnData` |
| `com.tensurabossstructure.compat` | 1 | `ElementalColossusSpawnerSupport` |
| `com.tensurabossstructure.init` | 1 | `ModStructures` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `VillagerTradeHandler` | class | ItemListing | EventBusSubscriber | 2 | 93 | [VillagerTradeHandler.java](com/tensurabossstructure/event/VillagerTradeHandler.java) |
| `SpawnerRespawnHandler` | class | — | EventBusSubscriber | 3 | 92 | [SpawnerRespawnHandler.java](com/tensurabossstructure/event/SpawnerRespawnHandler.java) |
| `ServerStartingHandler` | class | — | EventBusSubscriber | 2 | 67 | [ServerStartingHandler.java](com/tensurabossstructure/event/ServerStartingHandler.java) |
| `TensuraBossStructureClient` | class | — | EventBusSubscriber | 1 | 27 | [TensuraBossStructureClient.java](com/tensurabossstructure/client/TensuraBossStructureClient.java) |
| `TensuraBossStructure` | class | — | Mod | 0 | 37 | [TensuraBossStructure.java](com/tensurabossstructure/TensuraBossStructure.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 28 | [StructureTemplatePoolAccessor.java](com/tensurabossstructure/mixin/StructureTemplatePoolAccessor.java) |
| `OtherworlderPoolElementMixin` | class | — | Mixin | 0 | 80 | [OtherworlderPoolElementMixin.java](com/tensurabossstructure/mixin/OtherworlderPoolElementMixin.java) |
| `AccessorStructureProcessor` | interface | — | Mixin | 0 | 13 | [AccessorStructureProcessor.java](com/tensurabossstructure/mixin/AccessorStructureProcessor.java) |
| `MixinStructureTemplate` | class | — | Mixin | 1 | 37 | [MixinStructureTemplate.java](com/tensurabossstructure/mixin/MixinStructureTemplate.java) |
| `ElementalColossusSpawnerMixin` | class | — | Mixin、Pseudo | 0 | 29 | [ElementalColossusSpawnerMixin.java](com/tensurabossstructure/mixin/ElementalColossusSpawnerMixin.java) |
| `JigsawPlacementPlacerMixin` | class | — | Mixin | 2 | 43 | [JigsawPlacementPlacerMixin.java](com/tensurabossstructure/mixin/JigsawPlacementPlacerMixin.java) |
| `BossSpawnerBlockEntity` | class | BlockEntity | — | 5 | 162 | [BossSpawnerBlockEntity.java](com/tensurabossstructure/block/entity/BossSpawnerBlockEntity.java) |
| `AddItemLootModifier` | class | LootModifier | — | 2 | 41 | [AddItemLootModifier.java](com/tensurabossstructure/loot/AddItemLootModifier.java) |
| `BossConfig` | class | — | — | 0 | 23 | [BossConfig.java](com/tensurabossstructure/config/BossConfig.java) |
| `ModLootModifiers` | class | — | — | 1 | 23 | [ModLootModifiers.java](com/tensurabossstructure/registry/ModLootModifiers.java) |
| `BossSpawnerBlock` | class | BaseEntityBlock | — | 4 | 48 | [BossSpawnerBlock.java](com/tensurabossstructure/block/BossSpawnerBlock.java) |
| `StructureMapItem` | class | Item | — | 1 | 66 | [StructureMapItem.java](com/tensurabossstructure/item/StructureMapItem.java) |
| `SlimeKingSummonItem` | class | Item | — | 3 | 85 | [SlimeKingSummonItem.java](com/tensurabossstructure/item/SlimeKingSummonItem.java) |
| `SpawnerRespawnData` | class | SavedData | — | 5 | 123 | [SpawnerRespawnData.java](com/tensurabossstructure/world/SpawnerRespawnData.java) |
| `ModBlockEntities` | class | — | — | 1 | 39 | [ModBlockEntities.java](com/tensurabossstructure/registry/ModBlockEntities.java) |
| `ModBlocks` | class | — | — | 1 | 73 | [ModBlocks.java](com/tensurabossstructure/registry/ModBlocks.java) |
| `ModItems` | class | — | — | 2 | 94 | [ModItems.java](com/tensurabossstructure/registry/ModItems.java) |
| `ElementalColossusSpawnerSupport` | class | — | — | 4 | 49 | [ElementalColossusSpawnerSupport.java](com/tensurabossstructure/compat/ElementalColossusSpawnerSupport.java) |
| `SmartGrassProcessor` | class | StructureProcessor | — | 2 | 74 | [SmartGrassProcessor.java](com/tensurabossstructure/world/processor/SmartGrassProcessor.java) |
| `GroundLevelProcessor` | class | StructureProcessor | — | 2 | 38 | [GroundLevelProcessor.java](com/tensurabossstructure/world/processor/GroundLevelProcessor.java) |
| `JigsawMinHeightStructure` | class | Structure | — | 3 | 129 | [JigsawMinHeightStructure.java](com/tensurabossstructure/world/structure/JigsawMinHeightStructure.java) |
| `WaterloggingFixProcessor` | class | StructureProcessor | — | 2 | 46 | [WaterloggingFixProcessor.java](com/tensurabossstructure/world/structure/WaterloggingFixProcessor.java) |
| `ModProcessors` | class | — | — | 1 | 32 | [ModProcessors.java](com/tensurabossstructure/registry/ModProcessors.java) |
| `ModStructureTypes` | class | — | — | 1 | 22 | [ModStructureTypes.java](com/tensurabossstructure/registry/ModStructureTypes.java) |
| `ModStructures` | class | — | — | 2 | 23 | [ModStructures.java](com/tensurabossstructure/init/ModStructures.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SpawnerRespawnHandler` | 2 | `com.tensurabossstructure.event` | [SpawnerRespawnHandler.java](com/tensurabossstructure/event/SpawnerRespawnHandler.java) |
| `VillagerTradeHandler` | 1 | `com.tensurabossstructure.event` | [VillagerTradeHandler.java](com/tensurabossstructure/event/VillagerTradeHandler.java) |
| `ServerStartingHandler` | 1 | `com.tensurabossstructure.event` | [ServerStartingHandler.java](com/tensurabossstructure/event/ServerStartingHandler.java) |
| `TensuraBossStructureClient` | 1 | `com.tensurabossstructure.client` | [TensuraBossStructureClient.java](com/tensurabossstructure/client/TensuraBossStructureClient.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StructureTemplatePoolAccessor` | `StructureTemplatePoolAccessor` | [StructureTemplatePoolAccessor.java](com/tensurabossstructure/mixin/StructureTemplatePoolAccessor.java) |
| `OtherworlderPoolElementMixin` | `OtherworlderPoolElement` | [OtherworlderPoolElementMixin.java](com/tensurabossstructure/mixin/OtherworlderPoolElementMixin.java) |
| `AccessorStructureProcessor` | `AccessorStructureProcessor` | [AccessorStructureProcessor.java](com/tensurabossstructure/mixin/AccessorStructureProcessor.java) |
| `MixinStructureTemplate` | `StructureTemplate` | [MixinStructureTemplate.java](com/tensurabossstructure/mixin/MixinStructureTemplate.java) |
| `ElementalColossusSpawnerMixin` | `ElementalColossusSpawner` | [ElementalColossusSpawnerMixin.java](com/tensurabossstructure/mixin/ElementalColossusSpawnerMixin.java) |
| `JigsawPlacementPlacerMixin` | `JigsawPlacementPlacer` | [JigsawPlacementPlacerMixin.java](com/tensurabossstructure/mixin/JigsawPlacementPlacerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BossConfig` | — | [BossConfig.java](com/tensurabossstructure/config/BossConfig.java) |

## 全部类（30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `VillagerTradeHandler` | class | ItemListing | EventBusSubscriber | 2 | 93 | [VillagerTradeHandler.java](com/tensurabossstructure/event/VillagerTradeHandler.java) |
| `SpawnerRespawnHandler` | class | — | EventBusSubscriber | 3 | 92 | [SpawnerRespawnHandler.java](com/tensurabossstructure/event/SpawnerRespawnHandler.java) |
| `ServerStartingHandler` | class | — | EventBusSubscriber | 2 | 67 | [ServerStartingHandler.java](com/tensurabossstructure/event/ServerStartingHandler.java) |
| `TensuraBossStructureClient` | class | — | EventBusSubscriber | 1 | 27 | [TensuraBossStructureClient.java](com/tensurabossstructure/client/TensuraBossStructureClient.java) |
| `TensuraBossStructure` | class | — | Mod | 0 | 37 | [TensuraBossStructure.java](com/tensurabossstructure/TensuraBossStructure.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 28 | [StructureTemplatePoolAccessor.java](com/tensurabossstructure/mixin/StructureTemplatePoolAccessor.java) |
| `OtherworlderPoolElementMixin` | class | — | Mixin | 0 | 80 | [OtherworlderPoolElementMixin.java](com/tensurabossstructure/mixin/OtherworlderPoolElementMixin.java) |
| `AccessorStructureProcessor` | interface | — | Mixin | 0 | 13 | [AccessorStructureProcessor.java](com/tensurabossstructure/mixin/AccessorStructureProcessor.java) |
| `MixinStructureTemplate` | class | — | Mixin | 1 | 37 | [MixinStructureTemplate.java](com/tensurabossstructure/mixin/MixinStructureTemplate.java) |
| `ElementalColossusSpawnerMixin` | class | — | Mixin、Pseudo | 0 | 29 | [ElementalColossusSpawnerMixin.java](com/tensurabossstructure/mixin/ElementalColossusSpawnerMixin.java) |
| `JigsawPlacementPlacerMixin` | class | — | Mixin | 2 | 43 | [JigsawPlacementPlacerMixin.java](com/tensurabossstructure/mixin/JigsawPlacementPlacerMixin.java) |
| `BossSpawnerBlockEntity` | class | BlockEntity | — | 5 | 162 | [BossSpawnerBlockEntity.java](com/tensurabossstructure/block/entity/BossSpawnerBlockEntity.java) |
| `AddItemLootModifier` | class | LootModifier | — | 2 | 41 | [AddItemLootModifier.java](com/tensurabossstructure/loot/AddItemLootModifier.java) |
| `BossConfig` | class | — | — | 0 | 23 | [BossConfig.java](com/tensurabossstructure/config/BossConfig.java) |
| `ModLootModifiers` | class | — | — | 1 | 23 | [ModLootModifiers.java](com/tensurabossstructure/registry/ModLootModifiers.java) |
| `BossSpawnerBlock` | class | BaseEntityBlock | — | 4 | 48 | [BossSpawnerBlock.java](com/tensurabossstructure/block/BossSpawnerBlock.java) |
| `StructureMapItem` | class | Item | — | 1 | 66 | [StructureMapItem.java](com/tensurabossstructure/item/StructureMapItem.java) |
| `SlimeKingSummonItem` | class | Item | — | 3 | 85 | [SlimeKingSummonItem.java](com/tensurabossstructure/item/SlimeKingSummonItem.java) |
| `SpawnerRespawnData` | class | SavedData | — | 5 | 123 | [SpawnerRespawnData.java](com/tensurabossstructure/world/SpawnerRespawnData.java) |
| `ModBlockEntities` | class | — | — | 1 | 39 | [ModBlockEntities.java](com/tensurabossstructure/registry/ModBlockEntities.java) |
| `ModBlocks` | class | — | — | 1 | 73 | [ModBlocks.java](com/tensurabossstructure/registry/ModBlocks.java) |
| `ModItems` | class | — | — | 2 | 94 | [ModItems.java](com/tensurabossstructure/registry/ModItems.java) |
| `ElementalColossusSpawnerSupport` | class | — | — | 4 | 49 | [ElementalColossusSpawnerSupport.java](com/tensurabossstructure/compat/ElementalColossusSpawnerSupport.java) |
| `SmartGrassProcessor` | class | StructureProcessor | — | 2 | 74 | [SmartGrassProcessor.java](com/tensurabossstructure/world/processor/SmartGrassProcessor.java) |
| `GroundLevelProcessor` | class | StructureProcessor | — | 2 | 38 | [GroundLevelProcessor.java](com/tensurabossstructure/world/processor/GroundLevelProcessor.java) |
| `JigsawMinHeightStructure` | class | Structure | — | 3 | 129 | [JigsawMinHeightStructure.java](com/tensurabossstructure/world/structure/JigsawMinHeightStructure.java) |
| `WaterloggingFixProcessor` | class | StructureProcessor | — | 2 | 46 | [WaterloggingFixProcessor.java](com/tensurabossstructure/world/structure/WaterloggingFixProcessor.java) |
| `ModProcessors` | class | — | — | 1 | 32 | [ModProcessors.java](com/tensurabossstructure/registry/ModProcessors.java) |
| `ModStructureTypes` | class | — | — | 1 | 22 | [ModStructureTypes.java](com/tensurabossstructure/registry/ModStructureTypes.java) |
| `ModStructures` | class | — | — | 2 | 23 | [ModStructures.java](com/tensurabossstructure/init/ModStructures.java) |
