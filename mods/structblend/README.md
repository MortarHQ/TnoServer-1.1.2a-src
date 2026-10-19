# StructBlend（`structblend`）

> jar：`[融构]structblend.jar` ｜ 版本：`1.0.0` ｜ 作者：TheFlareStar
> 规模：**25 个类 / 3 个包 / 3805 行** ｜ 事件处理类 0 ｜ Mixin 9 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `cn.star.structblend.terrain` | 14 | `SurfaceBlendPlan`、`VillageUnderpinning`、`ChunkHeightBatch` |
| `cn.star.structblend.mixin` | 9 | `NoiseBasedChunkGeneratorMixin`、`ChunkGeneratorMixin`、`NoiseGeneratorAccessor` |
| `cn.star.structblend` | 2 | `Structblend`、`Config` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 25）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SurfaceBlendPlan` | class | — | — | 45 | 1015 | [SurfaceBlendPlan.java](cn/star/structblend/terrain/SurfaceBlendPlan.java) |
| `Structblend` | class | — | Mod | 2 | 41 | [Structblend.java](cn/star/structblend/Structblend.java) |
| `NoiseBasedChunkGeneratorMixin` | class | — | Mixin | 0 | 64 | [NoiseBasedChunkGeneratorMixin.java](cn/star/structblend/mixin/NoiseBasedChunkGeneratorMixin.java) |
| `ChunkGeneratorMixin` | class | — | Mixin | 0 | 87 | [ChunkGeneratorMixin.java](cn/star/structblend/mixin/ChunkGeneratorMixin.java) |
| `NoiseGeneratorAccessor` | interface | — | Mixin | 0 | 14 | [NoiseGeneratorAccessor.java](cn/star/structblend/mixin/NoiseGeneratorAccessor.java) |
| `PairedHeightMixin` | class | — | Mixin | 1 | 70 | [PairedHeightMixin.java](cn/star/structblend/mixin/PairedHeightMixin.java) |
| `SinglePoolElementInvoker` | interface | — | Mixin | 0 | 20 | [SinglePoolElementInvoker.java](cn/star/structblend/mixin/SinglePoolElementInvoker.java) |
| `NoiseChunkInvoker` | interface | — | Mixin | 0 | 13 | [NoiseChunkInvoker.java](cn/star/structblend/mixin/NoiseChunkInvoker.java) |
| `StructureTemplateAccessor` | interface | — | Mixin | 0 | 18 | [StructureTemplateAccessor.java](cn/star/structblend/mixin/StructureTemplateAccessor.java) |
| `JigsawStructureAccessor` | interface | — | Mixin | 0 | 14 | [JigsawStructureAccessor.java](cn/star/structblend/mixin/JigsawStructureAccessor.java) |
| `PoolPieceAccessor` | interface | — | Mixin | 0 | 13 | [PoolPieceAccessor.java](cn/star/structblend/mixin/PoolPieceAccessor.java) |
| `Config` | class | — | — | 0 | 20 | [Config.java](cn/star/structblend/Config.java) |
| `VillageUnderpinning` | class | BeardifierOrMarker | — | 11 | 419 | [VillageUnderpinning.java](cn/star/structblend/terrain/VillageUnderpinning.java) |
| `ChunkHeightBatch` | class | — | — | 8 | 489 | [ChunkHeightBatch.java](cn/star/structblend/terrain/ChunkHeightBatch.java) |
| `DecorativeTemplateFilter` | class | — | — | 8 | 276 | [DecorativeTemplateFilter.java](cn/star/structblend/terrain/DecorativeTemplateFilter.java) |
| `PairedHeightQuery` | class | AutoCloseable | — | 8 | 118 | [PairedHeightQuery.java](cn/star/structblend/terrain/PairedHeightQuery.java) |
| `VillageSupportShape` | class | — | — | 9 | 144 | [VillageSupportShape.java](cn/star/structblend/terrain/VillageSupportShape.java) |
| `HeightQueryDiagnostics` | class | — | — | 2 | 227 | [HeightQueryDiagnostics.java](cn/star/structblend/terrain/HeightQueryDiagnostics.java) |
| `FoundationMask` | class | — | — | 3 | 314 | [FoundationMask.java](cn/star/structblend/terrain/FoundationMask.java) |
| `ColumnHeightScan` | class | Predicate<T> | — | 5 | 59 | [ColumnHeightScan.java](cn/star/structblend/terrain/ColumnHeightScan.java) |
| `TreeTemplateFilter` | class | — | — | 2 | 78 | [TreeTemplateFilter.java](cn/star/structblend/terrain/TreeTemplateFilter.java) |
| `SlopeVariation` | class | — | — | 4 | 51 | [SlopeVariation.java](cn/star/structblend/terrain/SlopeVariation.java) |
| `SurfaceHeightCache` | class | — | — | 4 | 146 | [SurfaceHeightCache.java](cn/star/structblend/terrain/SurfaceHeightCache.java) |
| `ReferenceExpansion` | class | — | — | 1 | 15 | [ReferenceExpansion.java](cn/star/structblend/terrain/ReferenceExpansion.java) |
| `TerrainBeardifier` | class | BeardifierOrMarker | — | 4 | 80 | [TerrainBeardifier.java](cn/star/structblend/terrain/TerrainBeardifier.java) |

## Mixin（9 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `NoiseBasedChunkGeneratorMixin` | `NoiseBasedChunkGenerator` | [NoiseBasedChunkGeneratorMixin.java](cn/star/structblend/mixin/NoiseBasedChunkGeneratorMixin.java) |
| `ChunkGeneratorMixin` | `ChunkGenerator` | [ChunkGeneratorMixin.java](cn/star/structblend/mixin/ChunkGeneratorMixin.java) |
| `NoiseGeneratorAccessor` | `NoiseGeneratorAccessor` | [NoiseGeneratorAccessor.java](cn/star/structblend/mixin/NoiseGeneratorAccessor.java) |
| `PairedHeightMixin` | `PairedHeight` | [PairedHeightMixin.java](cn/star/structblend/mixin/PairedHeightMixin.java) |
| `SinglePoolElementInvoker` | `SinglePoolElementInvoker` | [SinglePoolElementInvoker.java](cn/star/structblend/mixin/SinglePoolElementInvoker.java) |
| `NoiseChunkInvoker` | `NoiseChunkInvoker` | [NoiseChunkInvoker.java](cn/star/structblend/mixin/NoiseChunkInvoker.java) |
| `StructureTemplateAccessor` | `StructureTemplateAccessor` | [StructureTemplateAccessor.java](cn/star/structblend/mixin/StructureTemplateAccessor.java) |
| `JigsawStructureAccessor` | `JigsawStructureAccessor` | [JigsawStructureAccessor.java](cn/star/structblend/mixin/JigsawStructureAccessor.java) |
| `PoolPieceAccessor` | `PoolPieceAccessor` | [PoolPieceAccessor.java](cn/star/structblend/mixin/PoolPieceAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](cn/star/structblend/Config.java) |

## 全部类（25）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SurfaceBlendPlan` | class | — | — | 45 | 1015 | [SurfaceBlendPlan.java](cn/star/structblend/terrain/SurfaceBlendPlan.java) |
| `Structblend` | class | — | Mod | 2 | 41 | [Structblend.java](cn/star/structblend/Structblend.java) |
| `NoiseBasedChunkGeneratorMixin` | class | — | Mixin | 0 | 64 | [NoiseBasedChunkGeneratorMixin.java](cn/star/structblend/mixin/NoiseBasedChunkGeneratorMixin.java) |
| `ChunkGeneratorMixin` | class | — | Mixin | 0 | 87 | [ChunkGeneratorMixin.java](cn/star/structblend/mixin/ChunkGeneratorMixin.java) |
| `NoiseGeneratorAccessor` | interface | — | Mixin | 0 | 14 | [NoiseGeneratorAccessor.java](cn/star/structblend/mixin/NoiseGeneratorAccessor.java) |
| `PairedHeightMixin` | class | — | Mixin | 1 | 70 | [PairedHeightMixin.java](cn/star/structblend/mixin/PairedHeightMixin.java) |
| `SinglePoolElementInvoker` | interface | — | Mixin | 0 | 20 | [SinglePoolElementInvoker.java](cn/star/structblend/mixin/SinglePoolElementInvoker.java) |
| `NoiseChunkInvoker` | interface | — | Mixin | 0 | 13 | [NoiseChunkInvoker.java](cn/star/structblend/mixin/NoiseChunkInvoker.java) |
| `StructureTemplateAccessor` | interface | — | Mixin | 0 | 18 | [StructureTemplateAccessor.java](cn/star/structblend/mixin/StructureTemplateAccessor.java) |
| `JigsawStructureAccessor` | interface | — | Mixin | 0 | 14 | [JigsawStructureAccessor.java](cn/star/structblend/mixin/JigsawStructureAccessor.java) |
| `PoolPieceAccessor` | interface | — | Mixin | 0 | 13 | [PoolPieceAccessor.java](cn/star/structblend/mixin/PoolPieceAccessor.java) |
| `Config` | class | — | — | 0 | 20 | [Config.java](cn/star/structblend/Config.java) |
| `VillageUnderpinning` | class | BeardifierOrMarker | — | 11 | 419 | [VillageUnderpinning.java](cn/star/structblend/terrain/VillageUnderpinning.java) |
| `ChunkHeightBatch` | class | — | — | 8 | 489 | [ChunkHeightBatch.java](cn/star/structblend/terrain/ChunkHeightBatch.java) |
| `DecorativeTemplateFilter` | class | — | — | 8 | 276 | [DecorativeTemplateFilter.java](cn/star/structblend/terrain/DecorativeTemplateFilter.java) |
| `PairedHeightQuery` | class | AutoCloseable | — | 8 | 118 | [PairedHeightQuery.java](cn/star/structblend/terrain/PairedHeightQuery.java) |
| `VillageSupportShape` | class | — | — | 9 | 144 | [VillageSupportShape.java](cn/star/structblend/terrain/VillageSupportShape.java) |
| `HeightQueryDiagnostics` | class | — | — | 2 | 227 | [HeightQueryDiagnostics.java](cn/star/structblend/terrain/HeightQueryDiagnostics.java) |
| `FoundationMask` | class | — | — | 3 | 314 | [FoundationMask.java](cn/star/structblend/terrain/FoundationMask.java) |
| `ColumnHeightScan` | class | Predicate<T> | — | 5 | 59 | [ColumnHeightScan.java](cn/star/structblend/terrain/ColumnHeightScan.java) |
| `TreeTemplateFilter` | class | — | — | 2 | 78 | [TreeTemplateFilter.java](cn/star/structblend/terrain/TreeTemplateFilter.java) |
| `SlopeVariation` | class | — | — | 4 | 51 | [SlopeVariation.java](cn/star/structblend/terrain/SlopeVariation.java) |
| `SurfaceHeightCache` | class | — | — | 4 | 146 | [SurfaceHeightCache.java](cn/star/structblend/terrain/SurfaceHeightCache.java) |
| `ReferenceExpansion` | class | — | — | 1 | 15 | [ReferenceExpansion.java](cn/star/structblend/terrain/ReferenceExpansion.java) |
| `TerrainBeardifier` | class | BeardifierOrMarker | — | 4 | 80 | [TerrainBeardifier.java](cn/star/structblend/terrain/TerrainBeardifier.java) |
