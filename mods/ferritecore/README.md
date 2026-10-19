# Ferrite Core（`ferritecore`）

> jar：`[铁氧体磁芯] ferritecore-7.0.3-neoforge.jar` ｜ 版本：`7.0.3` ｜ 作者：malte0811
> 规模：**62 个类 / 19 个包 / 2989 行** ｜ 事件处理类 1 ｜ Mixin 20 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `malte0811.ferritecore.mixin.accessors` | 7 | `BitSetDVSAccess`、`ArrayVSAccess`、`SliceShapeAccess` |
| `malte0811.ferritecore.impl` | 6 | `BlockStateCacheImpl`、`FastMapEntryMap`、`Deduplicator` |
| `malte0811.ferritecore.fastmap` | 5 | `PropertyIndexer`、`FastMap`、`FastMapKey` |
| `malte0811.ferritecore.hash` | 5 | `VoxelShapeHash`、`LambdaBasedHash`、`SliceShapeHash` |
| `malte0811.ferritecore` | 4 | `ModClientForge`、`ModMainForge`、`IPlatformHooks` |
| `malte0811.ferritecore.mixin.predicates` | 4 | `AndConditionMixin`、`OrConditionMixin`、`KeyValueConditionMixin` |
| `malte0811.ferritecore.mixin.blockstatecache` | 3 | `BlockStateCacheMixin`、`BlockStateBaseMixin`、`Config` |
| `malte0811.ferritecore.mixin.config` | 3 | `FerriteMixinConfig`、`FerriteConfig`、`IPlatformConfigHooks` |
| `malte0811.ferritecore.mixin.dedupmultipart` | 3 | `MixinMultipartBuilder`、`MixinMultipartModel`、`Config` |
| `malte0811.ferritecore.fastmap.table` | 3 | `FastmapNeighborTable`、`CrashNeighborTable`、`NeighborTableBase` |
| `malte0811.ferritecore.ducks` | 3 | `BlockStateCacheAccess`、`FastMapStateHolder`、`SmallThreadDetectable` |
| `malte0811.ferritecore.util` | 3 | `PredicateHelper`、`SmallThreadingDetector`、`Constants` |
| `malte0811.ferritecore.mixin.fastmap` | 2 | `FastMapStateHolderMixin`、`Config` |
| `malte0811.ferritecore.mixin.datacomponents` | 2 | `PatchedDataComponentMapMixin`、`Config` |
| `malte0811.ferritecore.mixin.mrl` | 2 | `ModelResourceLocationMixin`、`Config` |
| `malte0811.ferritecore.mixin.modelsides` | 2 | `SimpleBakedModelMixin`、`Config` |
| `malte0811.ferritecore.mixin.threaddetec` | 2 | `PalettedContainerMixin`、`Config` |
| `malte0811.ferritecore.mixin.dedupbakedquad` | 2 | `SimpleModelBuilderMixin`、`Config` |
| `malte0811.ferritecore.mixin.platform` | 1 | `ConfigFileHandler` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ModClientForge` | class | — | EventBusSubscriber | 1 | 16 | [ModClientForge.java](malte0811/ferritecore/ModClientForge.java) |
| `ModMainForge` | class | — | Mod | 0 | 8 | [ModMainForge.java](malte0811/ferritecore/ModMainForge.java) |
| `BlockStateCacheMixin` | class | BlockStateCacheAccess | Mixin | 6 | 57 | [BlockStateCacheMixin.java](malte0811/ferritecore/mixin/blockstatecache/BlockStateCacheMixin.java) |
| `ConfigFileHandler` | class | IPlatformConfigHooks | — | 3 | 69 | [ConfigFileHandler.java](malte0811/ferritecore/mixin/platform/ConfigFileHandler.java) |
| `BlockStateBaseMixin` | class | — | Mixin | 3 | 27 | [BlockStateBaseMixin.java](malte0811/ferritecore/mixin/blockstatecache/BlockStateBaseMixin.java) |
| `FastMapStateHolderMixin` | class | FastMapStateHolder<S> | Mixin | 10 | 84 | [FastMapStateHolderMixin.java](malte0811/ferritecore/mixin/fastmap/FastMapStateHolderMixin.java) |
| `FerriteMixinConfig` | class | IMixinConfigPlugin | — | 11 | 148 | [FerriteMixinConfig.java](malte0811/ferritecore/mixin/config/FerriteMixinConfig.java) |
| `PatchedDataComponentMapMixin` | class | — | Mixin | 2 | 35 | [PatchedDataComponentMapMixin.java](malte0811/ferritecore/mixin/datacomponents/PatchedDataComponentMapMixin.java) |
| `BitSetDVSAccess` | interface | — | Mixin | 0 | 31 | [BitSetDVSAccess.java](malte0811/ferritecore/mixin/accessors/BitSetDVSAccess.java) |
| `ArrayVSAccess` | interface | — | Mixin | 0 | 32 | [ArrayVSAccess.java](malte0811/ferritecore/mixin/accessors/ArrayVSAccess.java) |
| `SliceShapeAccess` | interface | — | Mixin | 0 | 17 | [SliceShapeAccess.java](malte0811/ferritecore/mixin/accessors/SliceShapeAccess.java) |
| `DiscreteVSAccess` | interface | — | Mixin | 0 | 18 | [DiscreteVSAccess.java](malte0811/ferritecore/mixin/accessors/DiscreteVSAccess.java) |
| `VoxelShapeAccess` | interface | — | Mixin | 0 | 26 | [VoxelShapeAccess.java](malte0811/ferritecore/mixin/accessors/VoxelShapeAccess.java) |
| `BakedQuadAccess` | interface | — | Mixin | 0 | 14 | [BakedQuadAccess.java](malte0811/ferritecore/mixin/accessors/BakedQuadAccess.java) |
| `SubShapeAccess` | interface | — | Mixin | 0 | 31 | [SubShapeAccess.java](malte0811/ferritecore/mixin/accessors/SubShapeAccess.java) |
| `ModelResourceLocationMixin` | class | — | Mixin | 1 | 26 | [ModelResourceLocationMixin.java](malte0811/ferritecore/mixin/mrl/ModelResourceLocationMixin.java) |
| `SimpleBakedModelMixin` | class | — | Mixin | 1 | 34 | [SimpleBakedModelMixin.java](malte0811/ferritecore/mixin/modelsides/SimpleBakedModelMixin.java) |
| `PalettedContainerMixin` | class | SmallThreadDetectable | Mixin | 3 | 56 | [PalettedContainerMixin.java](malte0811/ferritecore/mixin/threaddetec/PalettedContainerMixin.java) |
| `SimpleModelBuilderMixin` | class | — | Mixin | 2 | 24 | [SimpleModelBuilderMixin.java](malte0811/ferritecore/mixin/dedupbakedquad/SimpleModelBuilderMixin.java) |
| `AndConditionMixin` | class | — | Mixin | 1 | 27 | [AndConditionMixin.java](malte0811/ferritecore/mixin/predicates/AndConditionMixin.java) |
| `OrConditionMixin` | class | — | Mixin | 1 | 27 | [OrConditionMixin.java](malte0811/ferritecore/mixin/predicates/OrConditionMixin.java) |
| `KeyValueConditionMixin` | class | — | Mixin | 1 | 32 | [KeyValueConditionMixin.java](malte0811/ferritecore/mixin/predicates/KeyValueConditionMixin.java) |
| `MixinMultipartBuilder` | class | — | Mixin | 1 | 22 | [MixinMultipartBuilder.java](malte0811/ferritecore/mixin/dedupmultipart/MixinMultipartBuilder.java) |
| `MixinMultipartModel` | class | — | Mixin | 2 | 33 | [MixinMultipartModel.java](malte0811/ferritecore/mixin/dedupmultipart/MixinMultipartModel.java) |
| `FerriteConfig` | class | — | — | 8 | 158 | [FerriteConfig.java](malte0811/ferritecore/mixin/config/FerriteConfig.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/mrl/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/datacomponents/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/modelsides/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/threaddetec/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/dedupbakedquad/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/predicates/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/blockstatecache/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/fastmap/Config.java) |
| `IPlatformConfigHooks` | interface | — | — | 0 | 24 | [IPlatformConfigHooks.java](malte0811/ferritecore/mixin/config/IPlatformConfigHooks.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/dedupmultipart/Config.java) |
| `FastmapNeighborTable` | class | NeighborTableBase | — | 16 | 219 | [FastmapNeighborTable.java](malte0811/ferritecore/fastmap/table/FastmapNeighborTable.java) |
| `BlockStateCacheImpl` | class | — | — | 8 | 144 | [BlockStateCacheImpl.java](malte0811/ferritecore/impl/BlockStateCacheImpl.java) |
| `CrashNeighborTable` | class | NeighborTableBase | — | 17 | 87 | [CrashNeighborTable.java](malte0811/ferritecore/fastmap/table/CrashNeighborTable.java) |
| `FastMapEntryMap` | class | — | — | 13 | 107 | [FastMapEntryMap.java](malte0811/ferritecore/impl/FastMapEntryMap.java) |
| `PropertyIndexer` | class | PropertyIndexer | — | 11 | 176 | [PropertyIndexer.java](malte0811/ferritecore/fastmap/PropertyIndexer.java) |
| `FastMap` | class | — | — | 11 | 136 | [FastMap.java](malte0811/ferritecore/fastmap/FastMap.java) |
| `BlockStateCacheAccess` | interface | — | — | 0 | 19 | [BlockStateCacheAccess.java](malte0811/ferritecore/ducks/BlockStateCacheAccess.java) |
| `VoxelShapeHash` | class | Strategy<VoxelShape> | — | 5 | 55 | [VoxelShapeHash.java](malte0811/ferritecore/hash/VoxelShapeHash.java) |
| `Deduplicator` | class | — | — | 9 | 86 | [Deduplicator.java](malte0811/ferritecore/impl/Deduplicator.java) |
| `KeyValueConditionImpl` | class | — | — | 5 | 89 | [KeyValueConditionImpl.java](malte0811/ferritecore/impl/KeyValueConditionImpl.java) |
| `FastMapKey` | class | — | — | 5 | 36 | [FastMapKey.java](malte0811/ferritecore/fastmap/FastMapKey.java) |
| `BinaryFastMapKey` | class | — | — | 5 | 56 | [BinaryFastMapKey.java](malte0811/ferritecore/fastmap/BinaryFastMapKey.java) |
| `NeighborTableBase` | class | — | — | 5 | 33 | [NeighborTableBase.java](malte0811/ferritecore/fastmap/table/NeighborTableBase.java) |
| `PredicateHelper` | class | — | — | 4 | 55 | [PredicateHelper.java](malte0811/ferritecore/util/PredicateHelper.java) |
| `SmallThreadingDetector` | class | — | — | 9 | 152 | [SmallThreadingDetector.java](malte0811/ferritecore/util/SmallThreadingDetector.java) |
| `IPlatformHooks` | interface | — | — | 0 | 8 | [IPlatformHooks.java](malte0811/ferritecore/IPlatformHooks.java) |
| `PlatformHooks` | class | IPlatformHooks | — | 2 | 14 | [PlatformHooks.java](malte0811/ferritecore/PlatformHooks.java) |
| `LambdaBasedHash` | class | Strategy<T> | — | 2 | 24 | [LambdaBasedHash.java](malte0811/ferritecore/hash/LambdaBasedHash.java) |
| `SliceShapeHash` | class | Strategy<SliceShapeAccess> | — | 2 | 28 | [SliceShapeHash.java](malte0811/ferritecore/hash/SliceShapeHash.java) |
| `ArrayVoxelShapeHash` | class | Strategy<ArrayVSAccess> | — | 2 | 27 | [ArrayVoxelShapeHash.java](malte0811/ferritecore/hash/ArrayVoxelShapeHash.java) |
| `DiscreteVSHash` | class | Strategy<DiscreteVoxelShape> | — | 4 | 82 | [DiscreteVSHash.java](malte0811/ferritecore/hash/DiscreteVSHash.java) |
| `StateHolderImpl` | class | — | — | 1 | 49 | [StateHolderImpl.java](malte0811/ferritecore/impl/StateHolderImpl.java) |
| `ModelSidesImpl` | class | — | — | 2 | 38 | [ModelSidesImpl.java](malte0811/ferritecore/impl/ModelSidesImpl.java) |
| `CompactFastMapKey` | class | — | — | 4 | 38 | [CompactFastMapKey.java](malte0811/ferritecore/fastmap/CompactFastMapKey.java) |
| `Constants` | class | — | — | 0 | 19 | [Constants.java](malte0811/ferritecore/util/Constants.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ModClientForge` | 1 | `malte0811.ferritecore` | [ModClientForge.java](malte0811/ferritecore/ModClientForge.java) |

## Mixin（20 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockStateCacheMixin` | `BlockStateCache` | [BlockStateCacheMixin.java](malte0811/ferritecore/mixin/blockstatecache/BlockStateCacheMixin.java) |
| `BlockStateBaseMixin` | `BlockStateBase` | [BlockStateBaseMixin.java](malte0811/ferritecore/mixin/blockstatecache/BlockStateBaseMixin.java) |
| `FastMapStateHolderMixin` | `FastMapStateHolder` | [FastMapStateHolderMixin.java](malte0811/ferritecore/mixin/fastmap/FastMapStateHolderMixin.java) |
| `PatchedDataComponentMapMixin` | `PatchedDataComponentMap` | [PatchedDataComponentMapMixin.java](malte0811/ferritecore/mixin/datacomponents/PatchedDataComponentMapMixin.java) |
| `BitSetDVSAccess` | `BitSetDVSAccess` | [BitSetDVSAccess.java](malte0811/ferritecore/mixin/accessors/BitSetDVSAccess.java) |
| `ArrayVSAccess` | `ArrayVSAccess` | [ArrayVSAccess.java](malte0811/ferritecore/mixin/accessors/ArrayVSAccess.java) |
| `SliceShapeAccess` | `SliceShapeAccess` | [SliceShapeAccess.java](malte0811/ferritecore/mixin/accessors/SliceShapeAccess.java) |
| `DiscreteVSAccess` | `DiscreteVSAccess` | [DiscreteVSAccess.java](malte0811/ferritecore/mixin/accessors/DiscreteVSAccess.java) |
| `VoxelShapeAccess` | `VoxelShapeAccess` | [VoxelShapeAccess.java](malte0811/ferritecore/mixin/accessors/VoxelShapeAccess.java) |
| `BakedQuadAccess` | `BakedQuadAccess` | [BakedQuadAccess.java](malte0811/ferritecore/mixin/accessors/BakedQuadAccess.java) |
| `SubShapeAccess` | `SubShapeAccess` | [SubShapeAccess.java](malte0811/ferritecore/mixin/accessors/SubShapeAccess.java) |
| `ModelResourceLocationMixin` | `ModelResourceLocation` | [ModelResourceLocationMixin.java](malte0811/ferritecore/mixin/mrl/ModelResourceLocationMixin.java) |
| `SimpleBakedModelMixin` | `SimpleBakedModel` | [SimpleBakedModelMixin.java](malte0811/ferritecore/mixin/modelsides/SimpleBakedModelMixin.java) |
| `PalettedContainerMixin` | `PalettedContainer` | [PalettedContainerMixin.java](malte0811/ferritecore/mixin/threaddetec/PalettedContainerMixin.java) |
| `SimpleModelBuilderMixin` | `SimpleModelBuilder` | [SimpleModelBuilderMixin.java](malte0811/ferritecore/mixin/dedupbakedquad/SimpleModelBuilderMixin.java) |
| `AndConditionMixin` | `AndCondition` | [AndConditionMixin.java](malte0811/ferritecore/mixin/predicates/AndConditionMixin.java) |
| `OrConditionMixin` | `OrCondition` | [OrConditionMixin.java](malte0811/ferritecore/mixin/predicates/OrConditionMixin.java) |
| `KeyValueConditionMixin` | `KeyValueCondition` | [KeyValueConditionMixin.java](malte0811/ferritecore/mixin/predicates/KeyValueConditionMixin.java) |
| `MixinMultipartBuilder` | `MultipartBuilder` | [MixinMultipartBuilder.java](malte0811/ferritecore/mixin/dedupmultipart/MixinMultipartBuilder.java) |
| `MixinMultipartModel` | `MultipartModel` | [MixinMultipartModel.java](malte0811/ferritecore/mixin/dedupmultipart/MixinMultipartModel.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigFileHandler` | — | [ConfigFileHandler.java](malte0811/ferritecore/mixin/platform/ConfigFileHandler.java) |
| `FerriteMixinConfig` | — | [FerriteMixinConfig.java](malte0811/ferritecore/mixin/config/FerriteMixinConfig.java) |
| `FerriteConfig` | — | [FerriteConfig.java](malte0811/ferritecore/mixin/config/FerriteConfig.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/mrl/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/datacomponents/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/modelsides/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/threaddetec/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/dedupbakedquad/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/predicates/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/blockstatecache/Config.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/fastmap/Config.java) |
| `IPlatformConfigHooks` | — | [IPlatformConfigHooks.java](malte0811/ferritecore/mixin/config/IPlatformConfigHooks.java) |
| `Config` | — | [Config.java](malte0811/ferritecore/mixin/dedupmultipart/Config.java) |

## 全部类（62）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ModClientForge` | class | — | EventBusSubscriber | 1 | 16 | [ModClientForge.java](malte0811/ferritecore/ModClientForge.java) |
| `ModMainForge` | class | — | Mod | 0 | 8 | [ModMainForge.java](malte0811/ferritecore/ModMainForge.java) |
| `BlockStateCacheMixin` | class | BlockStateCacheAccess | Mixin | 6 | 57 | [BlockStateCacheMixin.java](malte0811/ferritecore/mixin/blockstatecache/BlockStateCacheMixin.java) |
| `ConfigFileHandler` | class | IPlatformConfigHooks | — | 3 | 69 | [ConfigFileHandler.java](malte0811/ferritecore/mixin/platform/ConfigFileHandler.java) |
| `BlockStateBaseMixin` | class | — | Mixin | 3 | 27 | [BlockStateBaseMixin.java](malte0811/ferritecore/mixin/blockstatecache/BlockStateBaseMixin.java) |
| `FastMapStateHolderMixin` | class | FastMapStateHolder<S> | Mixin | 10 | 84 | [FastMapStateHolderMixin.java](malte0811/ferritecore/mixin/fastmap/FastMapStateHolderMixin.java) |
| `FerriteMixinConfig` | class | IMixinConfigPlugin | — | 11 | 148 | [FerriteMixinConfig.java](malte0811/ferritecore/mixin/config/FerriteMixinConfig.java) |
| `PatchedDataComponentMapMixin` | class | — | Mixin | 2 | 35 | [PatchedDataComponentMapMixin.java](malte0811/ferritecore/mixin/datacomponents/PatchedDataComponentMapMixin.java) |
| `BitSetDVSAccess` | interface | — | Mixin | 0 | 31 | [BitSetDVSAccess.java](malte0811/ferritecore/mixin/accessors/BitSetDVSAccess.java) |
| `ArrayVSAccess` | interface | — | Mixin | 0 | 32 | [ArrayVSAccess.java](malte0811/ferritecore/mixin/accessors/ArrayVSAccess.java) |
| `SliceShapeAccess` | interface | — | Mixin | 0 | 17 | [SliceShapeAccess.java](malte0811/ferritecore/mixin/accessors/SliceShapeAccess.java) |
| `DiscreteVSAccess` | interface | — | Mixin | 0 | 18 | [DiscreteVSAccess.java](malte0811/ferritecore/mixin/accessors/DiscreteVSAccess.java) |
| `VoxelShapeAccess` | interface | — | Mixin | 0 | 26 | [VoxelShapeAccess.java](malte0811/ferritecore/mixin/accessors/VoxelShapeAccess.java) |
| `BakedQuadAccess` | interface | — | Mixin | 0 | 14 | [BakedQuadAccess.java](malte0811/ferritecore/mixin/accessors/BakedQuadAccess.java) |
| `SubShapeAccess` | interface | — | Mixin | 0 | 31 | [SubShapeAccess.java](malte0811/ferritecore/mixin/accessors/SubShapeAccess.java) |
| `ModelResourceLocationMixin` | class | — | Mixin | 1 | 26 | [ModelResourceLocationMixin.java](malte0811/ferritecore/mixin/mrl/ModelResourceLocationMixin.java) |
| `SimpleBakedModelMixin` | class | — | Mixin | 1 | 34 | [SimpleBakedModelMixin.java](malte0811/ferritecore/mixin/modelsides/SimpleBakedModelMixin.java) |
| `PalettedContainerMixin` | class | SmallThreadDetectable | Mixin | 3 | 56 | [PalettedContainerMixin.java](malte0811/ferritecore/mixin/threaddetec/PalettedContainerMixin.java) |
| `SimpleModelBuilderMixin` | class | — | Mixin | 2 | 24 | [SimpleModelBuilderMixin.java](malte0811/ferritecore/mixin/dedupbakedquad/SimpleModelBuilderMixin.java) |
| `AndConditionMixin` | class | — | Mixin | 1 | 27 | [AndConditionMixin.java](malte0811/ferritecore/mixin/predicates/AndConditionMixin.java) |
| `OrConditionMixin` | class | — | Mixin | 1 | 27 | [OrConditionMixin.java](malte0811/ferritecore/mixin/predicates/OrConditionMixin.java) |
| `KeyValueConditionMixin` | class | — | Mixin | 1 | 32 | [KeyValueConditionMixin.java](malte0811/ferritecore/mixin/predicates/KeyValueConditionMixin.java) |
| `MixinMultipartBuilder` | class | — | Mixin | 1 | 22 | [MixinMultipartBuilder.java](malte0811/ferritecore/mixin/dedupmultipart/MixinMultipartBuilder.java) |
| `MixinMultipartModel` | class | — | Mixin | 2 | 33 | [MixinMultipartModel.java](malte0811/ferritecore/mixin/dedupmultipart/MixinMultipartModel.java) |
| `FerriteConfig` | class | — | — | 8 | 158 | [FerriteConfig.java](malte0811/ferritecore/mixin/config/FerriteConfig.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/mrl/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/datacomponents/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/modelsides/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/threaddetec/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/dedupbakedquad/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/predicates/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/blockstatecache/Config.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/fastmap/Config.java) |
| `IPlatformConfigHooks` | interface | — | — | 0 | 24 | [IPlatformConfigHooks.java](malte0811/ferritecore/mixin/config/IPlatformConfigHooks.java) |
| `Config` | class | FerriteMixinConfig | — | 0 | 11 | [Config.java](malte0811/ferritecore/mixin/dedupmultipart/Config.java) |
| `FastmapNeighborTable` | class | NeighborTableBase | — | 16 | 219 | [FastmapNeighborTable.java](malte0811/ferritecore/fastmap/table/FastmapNeighborTable.java) |
| `BlockStateCacheImpl` | class | — | — | 8 | 144 | [BlockStateCacheImpl.java](malte0811/ferritecore/impl/BlockStateCacheImpl.java) |
| `CrashNeighborTable` | class | NeighborTableBase | — | 17 | 87 | [CrashNeighborTable.java](malte0811/ferritecore/fastmap/table/CrashNeighborTable.java) |
| `FastMapEntryMap` | class | — | — | 13 | 107 | [FastMapEntryMap.java](malte0811/ferritecore/impl/FastMapEntryMap.java) |
| `PropertyIndexer` | class | PropertyIndexer | — | 11 | 176 | [PropertyIndexer.java](malte0811/ferritecore/fastmap/PropertyIndexer.java) |
| `FastMap` | class | — | — | 11 | 136 | [FastMap.java](malte0811/ferritecore/fastmap/FastMap.java) |
| `BlockStateCacheAccess` | interface | — | — | 0 | 19 | [BlockStateCacheAccess.java](malte0811/ferritecore/ducks/BlockStateCacheAccess.java) |
| `VoxelShapeHash` | class | Strategy<VoxelShape> | — | 5 | 55 | [VoxelShapeHash.java](malte0811/ferritecore/hash/VoxelShapeHash.java) |
| `Deduplicator` | class | — | — | 9 | 86 | [Deduplicator.java](malte0811/ferritecore/impl/Deduplicator.java) |
| `KeyValueConditionImpl` | class | — | — | 5 | 89 | [KeyValueConditionImpl.java](malte0811/ferritecore/impl/KeyValueConditionImpl.java) |
| `FastMapKey` | class | — | — | 5 | 36 | [FastMapKey.java](malte0811/ferritecore/fastmap/FastMapKey.java) |
| `BinaryFastMapKey` | class | — | — | 5 | 56 | [BinaryFastMapKey.java](malte0811/ferritecore/fastmap/BinaryFastMapKey.java) |
| `NeighborTableBase` | class | — | — | 5 | 33 | [NeighborTableBase.java](malte0811/ferritecore/fastmap/table/NeighborTableBase.java) |
| `PredicateHelper` | class | — | — | 4 | 55 | [PredicateHelper.java](malte0811/ferritecore/util/PredicateHelper.java) |
| `SmallThreadingDetector` | class | — | — | 9 | 152 | [SmallThreadingDetector.java](malte0811/ferritecore/util/SmallThreadingDetector.java) |
| `IPlatformHooks` | interface | — | — | 0 | 8 | [IPlatformHooks.java](malte0811/ferritecore/IPlatformHooks.java) |
| `PlatformHooks` | class | IPlatformHooks | — | 2 | 14 | [PlatformHooks.java](malte0811/ferritecore/PlatformHooks.java) |
| `LambdaBasedHash` | class | Strategy<T> | — | 2 | 24 | [LambdaBasedHash.java](malte0811/ferritecore/hash/LambdaBasedHash.java) |
| `SliceShapeHash` | class | Strategy<SliceShapeAccess> | — | 2 | 28 | [SliceShapeHash.java](malte0811/ferritecore/hash/SliceShapeHash.java) |
| `ArrayVoxelShapeHash` | class | Strategy<ArrayVSAccess> | — | 2 | 27 | [ArrayVoxelShapeHash.java](malte0811/ferritecore/hash/ArrayVoxelShapeHash.java) |
| `DiscreteVSHash` | class | Strategy<DiscreteVoxelShape> | — | 4 | 82 | [DiscreteVSHash.java](malte0811/ferritecore/hash/DiscreteVSHash.java) |
| `StateHolderImpl` | class | — | — | 1 | 49 | [StateHolderImpl.java](malte0811/ferritecore/impl/StateHolderImpl.java) |
| `ModelSidesImpl` | class | — | — | 2 | 38 | [ModelSidesImpl.java](malte0811/ferritecore/impl/ModelSidesImpl.java) |
| `CompactFastMapKey` | class | — | — | 4 | 38 | [CompactFastMapKey.java](malte0811/ferritecore/fastmap/CompactFastMapKey.java) |
| `Constants` | class | — | — | 0 | 19 | [Constants.java](malte0811/ferritecore/util/Constants.java) |
| `FastMapStateHolder` | interface | — | — | 0 | 25 | [FastMapStateHolder.java](malte0811/ferritecore/ducks/FastMapStateHolder.java) |
| `SmallThreadDetectable` | interface | — | — | 0 | 12 | [SmallThreadDetectable.java](malte0811/ferritecore/ducks/SmallThreadDetectable.java) |
