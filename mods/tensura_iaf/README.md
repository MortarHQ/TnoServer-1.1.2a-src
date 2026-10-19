# TenSura Compat: Ice and Fire（`tensura_iaf`）

> jar：`[转生附属]tensura_iaf.jar` ｜ 版本：`2.0.0.1` ｜ 作者：MinhEragon
> 规模：**24 个类 / 7 个包 / 1191 行** ｜ 事件处理类 0 ｜ Mixin 11 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`architectury`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `io.github.manasmods.tensura_iaf.mixin` | 11 | `MixinIceSpikesBlock`、`MixinTensuraDamageHelper`、`MixinItemChain` |
| `io.github.manasmods.tensura_iaf.neoforge.data` | 4 | `IafRegistryProvider`、`IafEntityTypeTagProvider`、`IafBlockTagProvider` |
| `io.github.manasmods.tensura_iaf.data` | 4 | `IafEntityExistenceData`、`TensuraIafEntityTags`、`IafGearExistenceData` |
| `io.github.manasmods.tensura_iaf` | 2 | `IafHandler`、`TensuraIaf` |
| `io.github.manasmods.tensura_iaf.neoforge` | 1 | `TensuraIafNeoForge` |
| `io.github.manasmods.tensura_iaf.registry` | 1 | `IafConfig` |
| `architectury_inject_tensura_iaf_common_fdefc01789f74833ab5377392f462488_6b03dcb7ac1149bdfb6c80f6f6a4a770b8d8d5c610f4741ff418958700babd7ccommon2001devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraIafNeoForge` | class | — | Mod | 1 | 38 | [TensuraIafNeoForge.java](io/github/manasmods/tensura_iaf/neoforge/TensuraIafNeoForge.java) |
| `MixinIceSpikesBlock` | class | — | Mixin | 1 | 38 | [MixinIceSpikesBlock.java](io/github/manasmods/tensura_iaf/mixin/MixinIceSpikesBlock.java) |
| `MixinTensuraDamageHelper` | class | — | Mixin | 5 | 51 | [MixinTensuraDamageHelper.java](io/github/manasmods/tensura_iaf/mixin/MixinTensuraDamageHelper.java) |
| `MixinItemChain` | class | — | Mixin | 1 | 29 | [MixinItemChain.java](io/github/manasmods/tensura_iaf/mixin/MixinItemChain.java) |
| `MixinSirenEntity` | class | — | Mixin | 1 | 24 | [MixinSirenEntity.java](io/github/manasmods/tensura_iaf/mixin/MixinSirenEntity.java) |
| `MixinEntityDragonBase` | class | TamableAnimal | Mixin | 1 | 35 | [MixinEntityDragonBase.java](io/github/manasmods/tensura_iaf/mixin/MixinEntityDragonBase.java) |
| `MixinEntityGorgon` | class | — | Mixin | 1 | 27 | [MixinEntityGorgon.java](io/github/manasmods/tensura_iaf/mixin/MixinEntityGorgon.java) |
| `MixinRaceUtils` | class | — | Mixin | 1 | 22 | [MixinRaceUtils.java](io/github/manasmods/tensura_iaf/mixin/MixinRaceUtils.java) |
| `IafRegistryProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 24 | [IafRegistryProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafRegistryProvider.java) |
| `MixinThunderBreathProjectile` | class | — | Mixin | 1 | 36 | [MixinThunderBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinThunderBreathProjectile.java) |
| `MixinIceBreathProjectile` | class | — | Mixin | 2 | 56 | [MixinIceBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinIceBreathProjectile.java) |
| `MixinColdNullification` | class | — | Mixin | 1 | 30 | [MixinColdNullification.java](io/github/manasmods/tensura_iaf/mixin/MixinColdNullification.java) |
| `MixinFlameBreathProjectile` | class | — | Mixin | 1 | 36 | [MixinFlameBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinFlameBreathProjectile.java) |
| `IafHandler` | class | — | — | 1 | 51 | [IafHandler.java](io/github/manasmods/tensura_iaf/IafHandler.java) |
| `IafConfig` | class | ManasConfig | — | 1 | 18 | [IafConfig.java](io/github/manasmods/tensura_iaf/registry/IafConfig.java) |
| `IafEntityTypeTagProvider` | class | EntityTypeTagsProvider | — | 1 | 246 | [IafEntityTypeTagProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafEntityTypeTagProvider.java) |
| `IafEntityExistenceData` | class | — | — | 2 | 94 | [IafEntityExistenceData.java](io/github/manasmods/tensura_iaf/data/IafEntityExistenceData.java) |
| `IafBlockTagProvider` | class | BlockTagsProvider | — | 1 | 67 | [IafBlockTagProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafBlockTagProvider.java) |
| `IafItemTagProvider` | class | ItemTagsProvider | — | 1 | 46 | [IafItemTagProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafItemTagProvider.java) |
| `TensuraIafEntityTags` | class | — | — | 0 | 19 | [TensuraIafEntityTags.java](io/github/manasmods/tensura_iaf/data/TensuraIafEntityTags.java) |
| `IafGearExistenceData` | class | — | — | 2 | 152 | [IafGearExistenceData.java](io/github/manasmods/tensura_iaf/data/IafGearExistenceData.java) |
| `TensuraIaf` | class | — | — | 1 | 14 | [TensuraIaf.java](io/github/manasmods/tensura_iaf/TensuraIaf.java) |
| `IafLevelMagiculeModifiers` | class | — | — | 2 | 30 | [IafLevelMagiculeModifiers.java](io/github/manasmods/tensura_iaf/data/IafLevelMagiculeModifiers.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_iaf_common_fdefc01789f74833ab5377392f462488_6b03dcb7ac1149bdfb6c80f6f6a4a770b8d8d5c610f4741ff418958700babd7ccommon2001devjar/PlatformMethods.java) |

## Mixin（11 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinIceSpikesBlock` | `IceSpikesBlock` | [MixinIceSpikesBlock.java](io/github/manasmods/tensura_iaf/mixin/MixinIceSpikesBlock.java) |
| `MixinTensuraDamageHelper` | `TensuraDamageHelper` | [MixinTensuraDamageHelper.java](io/github/manasmods/tensura_iaf/mixin/MixinTensuraDamageHelper.java) |
| `MixinItemChain` | `ItemChain` | [MixinItemChain.java](io/github/manasmods/tensura_iaf/mixin/MixinItemChain.java) |
| `MixinSirenEntity` | `SirenEntity` | [MixinSirenEntity.java](io/github/manasmods/tensura_iaf/mixin/MixinSirenEntity.java) |
| `MixinEntityDragonBase` | `EntityDragonBase` | [MixinEntityDragonBase.java](io/github/manasmods/tensura_iaf/mixin/MixinEntityDragonBase.java) |
| `MixinEntityGorgon` | `EntityGorgon` | [MixinEntityGorgon.java](io/github/manasmods/tensura_iaf/mixin/MixinEntityGorgon.java) |
| `MixinRaceUtils` | `RaceUtils` | [MixinRaceUtils.java](io/github/manasmods/tensura_iaf/mixin/MixinRaceUtils.java) |
| `MixinThunderBreathProjectile` | `ThunderBreathProjectile` | [MixinThunderBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinThunderBreathProjectile.java) |
| `MixinIceBreathProjectile` | `IceBreathProjectile` | [MixinIceBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinIceBreathProjectile.java) |
| `MixinColdNullification` | `ColdNullification` | [MixinColdNullification.java](io/github/manasmods/tensura_iaf/mixin/MixinColdNullification.java) |
| `MixinFlameBreathProjectile` | `FlameBreathProjectile` | [MixinFlameBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinFlameBreathProjectile.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `IafConfig` | — | [IafConfig.java](io/github/manasmods/tensura_iaf/registry/IafConfig.java) |

## 全部类（24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraIafNeoForge` | class | — | Mod | 1 | 38 | [TensuraIafNeoForge.java](io/github/manasmods/tensura_iaf/neoforge/TensuraIafNeoForge.java) |
| `MixinIceSpikesBlock` | class | — | Mixin | 1 | 38 | [MixinIceSpikesBlock.java](io/github/manasmods/tensura_iaf/mixin/MixinIceSpikesBlock.java) |
| `MixinTensuraDamageHelper` | class | — | Mixin | 5 | 51 | [MixinTensuraDamageHelper.java](io/github/manasmods/tensura_iaf/mixin/MixinTensuraDamageHelper.java) |
| `MixinItemChain` | class | — | Mixin | 1 | 29 | [MixinItemChain.java](io/github/manasmods/tensura_iaf/mixin/MixinItemChain.java) |
| `MixinSirenEntity` | class | — | Mixin | 1 | 24 | [MixinSirenEntity.java](io/github/manasmods/tensura_iaf/mixin/MixinSirenEntity.java) |
| `MixinEntityDragonBase` | class | TamableAnimal | Mixin | 1 | 35 | [MixinEntityDragonBase.java](io/github/manasmods/tensura_iaf/mixin/MixinEntityDragonBase.java) |
| `MixinEntityGorgon` | class | — | Mixin | 1 | 27 | [MixinEntityGorgon.java](io/github/manasmods/tensura_iaf/mixin/MixinEntityGorgon.java) |
| `MixinRaceUtils` | class | — | Mixin | 1 | 22 | [MixinRaceUtils.java](io/github/manasmods/tensura_iaf/mixin/MixinRaceUtils.java) |
| `IafRegistryProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 24 | [IafRegistryProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafRegistryProvider.java) |
| `MixinThunderBreathProjectile` | class | — | Mixin | 1 | 36 | [MixinThunderBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinThunderBreathProjectile.java) |
| `MixinIceBreathProjectile` | class | — | Mixin | 2 | 56 | [MixinIceBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinIceBreathProjectile.java) |
| `MixinColdNullification` | class | — | Mixin | 1 | 30 | [MixinColdNullification.java](io/github/manasmods/tensura_iaf/mixin/MixinColdNullification.java) |
| `MixinFlameBreathProjectile` | class | — | Mixin | 1 | 36 | [MixinFlameBreathProjectile.java](io/github/manasmods/tensura_iaf/mixin/MixinFlameBreathProjectile.java) |
| `IafHandler` | class | — | — | 1 | 51 | [IafHandler.java](io/github/manasmods/tensura_iaf/IafHandler.java) |
| `IafConfig` | class | ManasConfig | — | 1 | 18 | [IafConfig.java](io/github/manasmods/tensura_iaf/registry/IafConfig.java) |
| `IafEntityTypeTagProvider` | class | EntityTypeTagsProvider | — | 1 | 246 | [IafEntityTypeTagProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafEntityTypeTagProvider.java) |
| `IafEntityExistenceData` | class | — | — | 2 | 94 | [IafEntityExistenceData.java](io/github/manasmods/tensura_iaf/data/IafEntityExistenceData.java) |
| `IafBlockTagProvider` | class | BlockTagsProvider | — | 1 | 67 | [IafBlockTagProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafBlockTagProvider.java) |
| `IafItemTagProvider` | class | ItemTagsProvider | — | 1 | 46 | [IafItemTagProvider.java](io/github/manasmods/tensura_iaf/neoforge/data/IafItemTagProvider.java) |
| `TensuraIafEntityTags` | class | — | — | 0 | 19 | [TensuraIafEntityTags.java](io/github/manasmods/tensura_iaf/data/TensuraIafEntityTags.java) |
| `IafGearExistenceData` | class | — | — | 2 | 152 | [IafGearExistenceData.java](io/github/manasmods/tensura_iaf/data/IafGearExistenceData.java) |
| `TensuraIaf` | class | — | — | 1 | 14 | [TensuraIaf.java](io/github/manasmods/tensura_iaf/TensuraIaf.java) |
| `IafLevelMagiculeModifiers` | class | — | — | 2 | 30 | [IafLevelMagiculeModifiers.java](io/github/manasmods/tensura_iaf/data/IafLevelMagiculeModifiers.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_iaf_common_fdefc01789f74833ab5377392f462488_6b03dcb7ac1149bdfb6c80f6f6a4a770b8d8d5c610f4741ff418958700babd7ccommon2001devjar/PlatformMethods.java) |
