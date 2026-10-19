# Eldergrove（`eldergrove`）

> jar：`[群系]eldergrove.jar` ｜ 版本：`0.1.0` ｜ 作者：NOSFERATU
> 规模：**29 个类 / 3 个包 / 2369 行** ｜ 事件处理类 1 ｜ Mixin 1 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `nosferatu.eldergrove` | 27 | `Eldergrove`、`GreatwoodTreeGenerator`、`TaintedHeartBlock` |
| `nosferatu.eldergrove.client` | 1 | `EldergroveClient` |
| `nosferatu.eldergrove.mixin` | 1 | `OverworldBiomeBuilderMixin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 29）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EldergroveClient` | class | — | EventBusSubscriber | 7 | 130 | [EldergroveClient.java](nosferatu/eldergrove/client/EldergroveClient.java) |
| `Eldergrove` | class | — | Mod | 0 | 18 | [Eldergrove.java](nosferatu/eldergrove/Eldergrove.java) |
| `OverworldBiomeBuilderMixin` | class | — | Mixin | 1 | 58 | [OverworldBiomeBuilderMixin.java](nosferatu/eldergrove/mixin/OverworldBiomeBuilderMixin.java) |
| `GreatwoodTreeGenerator` | class | — | — | 22 | 384 | [GreatwoodTreeGenerator.java](nosferatu/eldergrove/GreatwoodTreeGenerator.java) |
| `TaintedHeartBlock` | class | Block | — | 7 | 119 | [TaintedHeartBlock.java](nosferatu/eldergrove/TaintedHeartBlock.java) |
| `EldergroveOakTreeGenerator` | class | — | — | 13 | 244 | [EldergroveOakTreeGenerator.java](nosferatu/eldergrove/EldergroveOakTreeGenerator.java) |
| `EldergroveSpreadingCoreBlock` | class | Block | — | 5 | 64 | [EldergroveSpreadingCoreBlock.java](nosferatu/eldergrove/EldergroveSpreadingCoreBlock.java) |
| `EldergroveTreeGenerator` | class | — | — | 11 | 230 | [EldergroveTreeGenerator.java](nosferatu/eldergrove/EldergroveTreeGenerator.java) |
| `EldergroveBlocks` | class | — | — | 1 | 196 | [EldergroveBlocks.java](nosferatu/eldergrove/EldergroveBlocks.java) |
| `GreatwoodSaplingBlock` | class | SaplingBlock | — | 3 | 57 | [GreatwoodSaplingBlock.java](nosferatu/eldergrove/GreatwoodSaplingBlock.java) |
| `EldergroveItems` | class | — | — | 1 | 49 | [EldergroveItems.java](nosferatu/eldergrove/EldergroveItems.java) |
| `ElderwoodSaplingBlock` | class | SaplingBlock | — | 1 | 20 | [ElderwoodSaplingBlock.java](nosferatu/eldergrove/ElderwoodSaplingBlock.java) |
| `EldergrovePlantBlock` | class | BushBlock | — | 2 | 34 | [EldergrovePlantBlock.java](nosferatu/eldergrove/EldergrovePlantBlock.java) |
| `VishroomBlock` | class | EldergrovePlantBlock | — | 1 | 25 | [VishroomBlock.java](nosferatu/eldergrove/VishroomBlock.java) |
| `EldergroveSaplingBlock` | class | SaplingBlock | — | 1 | 19 | [EldergroveSaplingBlock.java](nosferatu/eldergrove/EldergroveSaplingBlock.java) |
| `EldergroveGrassBlock` | class | GrassBlock | — | 1 | 18 | [EldergroveGrassBlock.java](nosferatu/eldergrove/EldergroveGrassBlock.java) |
| `EldergroveGroundPlants` | class | — | — | 5 | 62 | [EldergroveGroundPlants.java](nosferatu/eldergrove/EldergroveGroundPlants.java) |
| `EldergroveBiomeSpreader` | class | — | — | 6 | 84 | [EldergroveBiomeSpreader.java](nosferatu/eldergrove/EldergroveBiomeSpreader.java) |
| `TaintedDeadTreeGenerator` | class | — | — | 9 | 158 | [TaintedDeadTreeGenerator.java](nosferatu/eldergrove/TaintedDeadTreeGenerator.java) |
| `TaintedTotemFeature` | class | Feature | — | 5 | 103 | [TaintedTotemFeature.java](nosferatu/eldergrove/TaintedTotemFeature.java) |
| `FallenLogFeature` | class | Feature | — | 3 | 73 | [FallenLogFeature.java](nosferatu/eldergrove/FallenLogFeature.java) |
| `ElderwoodTreeFeature` | class | Feature | — | 1 | 17 | [ElderwoodTreeFeature.java](nosferatu/eldergrove/ElderwoodTreeFeature.java) |
| `TaintedDeadTreeFeature` | class | Feature | — | 1 | 17 | [TaintedDeadTreeFeature.java](nosferatu/eldergrove/TaintedDeadTreeFeature.java) |
| `EldergroveFeatures` | class | — | — | 1 | 41 | [EldergroveFeatures.java](nosferatu/eldergrove/EldergroveFeatures.java) |
| `EldergroveOakTreeFeature` | class | Feature | — | 1 | 17 | [EldergroveOakTreeFeature.java](nosferatu/eldergrove/EldergroveOakTreeFeature.java) |
| `EldergroveCreativeTabs` | class | — | — | 1 | 56 | [EldergroveCreativeTabs.java](nosferatu/eldergrove/EldergroveCreativeTabs.java) |
| `TaintedSurfaceFeature` | class | Feature | — | 1 | 42 | [TaintedSurfaceFeature.java](nosferatu/eldergrove/TaintedSurfaceFeature.java) |
| `EldergroveBiomes` | class | — | — | 0 | 17 | [EldergroveBiomes.java](nosferatu/eldergrove/EldergroveBiomes.java) |
| `GreatwoodTreeFeature` | class | Feature | — | 1 | 17 | [GreatwoodTreeFeature.java](nosferatu/eldergrove/GreatwoodTreeFeature.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EldergroveClient` | 2 | `nosferatu.eldergrove.client` | [EldergroveClient.java](nosferatu/eldergrove/client/EldergroveClient.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `OverworldBiomeBuilderMixin` | `OverworldBiomeBuilder` | [OverworldBiomeBuilderMixin.java](nosferatu/eldergrove/mixin/OverworldBiomeBuilderMixin.java) |

## 全部类（29）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EldergroveClient` | class | — | EventBusSubscriber | 7 | 130 | [EldergroveClient.java](nosferatu/eldergrove/client/EldergroveClient.java) |
| `Eldergrove` | class | — | Mod | 0 | 18 | [Eldergrove.java](nosferatu/eldergrove/Eldergrove.java) |
| `OverworldBiomeBuilderMixin` | class | — | Mixin | 1 | 58 | [OverworldBiomeBuilderMixin.java](nosferatu/eldergrove/mixin/OverworldBiomeBuilderMixin.java) |
| `GreatwoodTreeGenerator` | class | — | — | 22 | 384 | [GreatwoodTreeGenerator.java](nosferatu/eldergrove/GreatwoodTreeGenerator.java) |
| `TaintedHeartBlock` | class | Block | — | 7 | 119 | [TaintedHeartBlock.java](nosferatu/eldergrove/TaintedHeartBlock.java) |
| `EldergroveOakTreeGenerator` | class | — | — | 13 | 244 | [EldergroveOakTreeGenerator.java](nosferatu/eldergrove/EldergroveOakTreeGenerator.java) |
| `EldergroveSpreadingCoreBlock` | class | Block | — | 5 | 64 | [EldergroveSpreadingCoreBlock.java](nosferatu/eldergrove/EldergroveSpreadingCoreBlock.java) |
| `EldergroveTreeGenerator` | class | — | — | 11 | 230 | [EldergroveTreeGenerator.java](nosferatu/eldergrove/EldergroveTreeGenerator.java) |
| `EldergroveBlocks` | class | — | — | 1 | 196 | [EldergroveBlocks.java](nosferatu/eldergrove/EldergroveBlocks.java) |
| `GreatwoodSaplingBlock` | class | SaplingBlock | — | 3 | 57 | [GreatwoodSaplingBlock.java](nosferatu/eldergrove/GreatwoodSaplingBlock.java) |
| `EldergroveItems` | class | — | — | 1 | 49 | [EldergroveItems.java](nosferatu/eldergrove/EldergroveItems.java) |
| `ElderwoodSaplingBlock` | class | SaplingBlock | — | 1 | 20 | [ElderwoodSaplingBlock.java](nosferatu/eldergrove/ElderwoodSaplingBlock.java) |
| `EldergrovePlantBlock` | class | BushBlock | — | 2 | 34 | [EldergrovePlantBlock.java](nosferatu/eldergrove/EldergrovePlantBlock.java) |
| `VishroomBlock` | class | EldergrovePlantBlock | — | 1 | 25 | [VishroomBlock.java](nosferatu/eldergrove/VishroomBlock.java) |
| `EldergroveSaplingBlock` | class | SaplingBlock | — | 1 | 19 | [EldergroveSaplingBlock.java](nosferatu/eldergrove/EldergroveSaplingBlock.java) |
| `EldergroveGrassBlock` | class | GrassBlock | — | 1 | 18 | [EldergroveGrassBlock.java](nosferatu/eldergrove/EldergroveGrassBlock.java) |
| `EldergroveGroundPlants` | class | — | — | 5 | 62 | [EldergroveGroundPlants.java](nosferatu/eldergrove/EldergroveGroundPlants.java) |
| `EldergroveBiomeSpreader` | class | — | — | 6 | 84 | [EldergroveBiomeSpreader.java](nosferatu/eldergrove/EldergroveBiomeSpreader.java) |
| `TaintedDeadTreeGenerator` | class | — | — | 9 | 158 | [TaintedDeadTreeGenerator.java](nosferatu/eldergrove/TaintedDeadTreeGenerator.java) |
| `TaintedTotemFeature` | class | Feature | — | 5 | 103 | [TaintedTotemFeature.java](nosferatu/eldergrove/TaintedTotemFeature.java) |
| `FallenLogFeature` | class | Feature | — | 3 | 73 | [FallenLogFeature.java](nosferatu/eldergrove/FallenLogFeature.java) |
| `ElderwoodTreeFeature` | class | Feature | — | 1 | 17 | [ElderwoodTreeFeature.java](nosferatu/eldergrove/ElderwoodTreeFeature.java) |
| `TaintedDeadTreeFeature` | class | Feature | — | 1 | 17 | [TaintedDeadTreeFeature.java](nosferatu/eldergrove/TaintedDeadTreeFeature.java) |
| `EldergroveFeatures` | class | — | — | 1 | 41 | [EldergroveFeatures.java](nosferatu/eldergrove/EldergroveFeatures.java) |
| `EldergroveOakTreeFeature` | class | Feature | — | 1 | 17 | [EldergroveOakTreeFeature.java](nosferatu/eldergrove/EldergroveOakTreeFeature.java) |
| `EldergroveCreativeTabs` | class | — | — | 1 | 56 | [EldergroveCreativeTabs.java](nosferatu/eldergrove/EldergroveCreativeTabs.java) |
| `TaintedSurfaceFeature` | class | Feature | — | 1 | 42 | [TaintedSurfaceFeature.java](nosferatu/eldergrove/TaintedSurfaceFeature.java) |
| `EldergroveBiomes` | class | — | — | 0 | 17 | [EldergroveBiomes.java](nosferatu/eldergrove/EldergroveBiomes.java) |
| `GreatwoodTreeFeature` | class | Feature | — | 1 | 17 | [GreatwoodTreeFeature.java](nosferatu/eldergrove/GreatwoodTreeFeature.java) |
