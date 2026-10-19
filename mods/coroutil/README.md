# CoroUtil（`coroutil`）

> jar：`[前置]coroutil.jar` ｜ 版本：`1.21.0-1.3.8` ｜ 作者：Corosus
> 规模：**36 个类 / 6 个包 / 2230 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 2

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.corosus.coroutil.util` | 17 | `CoroUtilEntOrParticle`、`CoroUtilCompatibility`、`CoroUtilBlock` |
| `com.corosus.modconfig` | 10 | `CoroConfigRegistry`、`ModConfigData`、`ConfigMod` |
| `com.corosus.coroutil.loader.neoforge` | 3 | `ConfigModNeoForge`、`ClientEvents`、`ModConfigDataNeoForge` |
| `com.corosus.coroutil.repack.de.androidpit.colorthief` | 3 | `MMCQ`、`RGBUtil`、`ColorThief` |
| `com.corosus.coroutil.command` | 2 | `CommandCoroConfigClient`、`CommandCoroConfig` |
| `com.corosus.coroutil.config` | 1 | `ConfigCoroUtil` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 36）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigModNeoForge` | class | ConfigMod | Mod | 5 | 57 | [ConfigModNeoForge.java](com/corosus/coroutil/loader/neoforge/ConfigModNeoForge.java) |
| `CoroConfigRegistry` | class | — | — | 13 | 140 | [CoroConfigRegistry.java](com/corosus/modconfig/CoroConfigRegistry.java) |
| `ClientEvents` | class | — | — | 2 | 17 | [ClientEvents.java](com/corosus/coroutil/loader/neoforge/ClientEvents.java) |
| `CommandCoroConfigClient` | class | — | — | 2 | 29 | [CommandCoroConfigClient.java](com/corosus/coroutil/command/CommandCoroConfigClient.java) |
| `CommandCoroConfig` | class | — | — | 9 | 137 | [CommandCoroConfig.java](com/corosus/coroutil/command/CommandCoroConfig.java) |
| `ModConfigData` | class | — | — | 16 | 182 | [ModConfigData.java](com/corosus/modconfig/ModConfigData.java) |
| `ConfigCoroUtil` | class | IConfigCategory | — | 5 | 37 | [ConfigCoroUtil.java](com/corosus/coroutil/config/ConfigCoroUtil.java) |
| `CoroUtilEntOrParticle` | class | — | — | 26 | 140 | [CoroUtilEntOrParticle.java](com/corosus/coroutil/util/CoroUtilEntOrParticle.java) |
| `ModConfigDataNeoForge` | class | ModConfigData | — | 7 | 111 | [ModConfigDataNeoForge.java](com/corosus/coroutil/loader/neoforge/ModConfigDataNeoForge.java) |
| `MMCQ` | class | — | — | 19 | 423 | [MMCQ.java](com/corosus/coroutil/repack/de/androidpit/colorthief/MMCQ.java) |
| `ConfigMod` | class | — | — | 6 | 37 | [ConfigMod.java](com/corosus/modconfig/ConfigMod.java) |
| `CoroUtilCompatibility` | class | — | — | 6 | 70 | [CoroUtilCompatibility.java](com/corosus/coroutil/util/CoroUtilCompatibility.java) |
| `IConfigInstance` | interface | — | — | 0 | 8 | [IConfigInstance.java](com/corosus/modconfig/IConfigInstance.java) |
| `IConfigCategory` | interface | — | — | 0 | 14 | [IConfigCategory.java](com/corosus/modconfig/IConfigCategory.java) |
| `ConfigAddQueue` | class | — | — | 0 | 12 | [ConfigAddQueue.java](com/corosus/modconfig/ConfigAddQueue.java) |
| `ConfigEntryInfo` | class | — | — | 0 | 17 | [ConfigEntryInfo.java](com/corosus/modconfig/ConfigEntryInfo.java) |
| `ConfigComment` | @interface | — | Retention、Target | 0 | 13 | [ConfigComment.java](com/corosus/modconfig/ConfigComment.java) |
| `ConfigComparatorName` | class | Comparator<ConfigEntryInfo> | — | 1 | 10 | [ConfigComparatorName.java](com/corosus/modconfig/ConfigComparatorName.java) |
| `ConfigParams` | @interface | — | Retention、Target | 0 | 17 | [ConfigParams.java](com/corosus/modconfig/ConfigParams.java) |
| `CoroUtilBlock` | class | — | — | 3 | 21 | [CoroUtilBlock.java](com/corosus/coroutil/util/CoroUtilBlock.java) |
| `CoroUtilEntity` | class | — | — | 2 | 24 | [CoroUtilEntity.java](com/corosus/coroutil/util/CoroUtilEntity.java) |
| `ChunkCoordinatesBlock` | class | BlockPos | — | 0 | 19 | [ChunkCoordinatesBlock.java](com/corosus/coroutil/util/ChunkCoordinatesBlock.java) |
| `ReflectionHelper` | class | RuntimeException | — | 5 | 122 | [ReflectionHelper.java](com/corosus/coroutil/util/ReflectionHelper.java) |
| `CoroUtilWorldTime` | class | — | — | 6 | 32 | [CoroUtilWorldTime.java](com/corosus/coroutil/util/CoroUtilWorldTime.java) |
| `MultiLoaderUtil` | class | — | — | 6 | 98 | [MultiLoaderUtil.java](com/corosus/coroutil/util/MultiLoaderUtil.java) |
| `RGBUtil` | class | — | — | 4 | 36 | [RGBUtil.java](com/corosus/coroutil/repack/de/androidpit/colorthief/RGBUtil.java) |
| `ColorThief` | class | — | — | 8 | 121 | [ColorThief.java](com/corosus/coroutil/repack/de/androidpit/colorthief/ColorThief.java) |
| `CoroUtilMisc` | class | — | — | 2 | 28 | [CoroUtilMisc.java](com/corosus/coroutil/util/CoroUtilMisc.java) |
| `CoroUtilPath` | class | — | — | 2 | 95 | [CoroUtilPath.java](com/corosus/coroutil/util/CoroUtilPath.java) |
| `CoroUtilPhysics` | class | — | — | 4 | 61 | [CoroUtilPhysics.java](com/corosus/coroutil/util/CoroUtilPhysics.java) |
| `CoroUtilAttributes` | class | — | — | 0 | 8 | [CoroUtilAttributes.java](com/corosus/coroutil/util/CoroUtilAttributes.java) |
| `OldUtil` | class | — | — | 2 | 22 | [OldUtil.java](com/corosus/coroutil/util/OldUtil.java) |
| `CoroUtilParticle` | class | — | — | 1 | 27 | [CoroUtilParticle.java](com/corosus/coroutil/util/CoroUtilParticle.java) |
| `CoroUtilColor` | class | — | — | 0 | 5 | [CoroUtilColor.java](com/corosus/coroutil/util/CoroUtilColor.java) |
| `CU` | class | — | — | 1 | 12 | [CU.java](com/corosus/coroutil/util/CU.java) |
| `CULog` | class | — | — | 3 | 28 | [CULog.java](com/corosus/coroutil/util/CULog.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigModNeoForge` | Mod | [ConfigModNeoForge.java](com/corosus/coroutil/loader/neoforge/ConfigModNeoForge.java) |
| `CoroConfigRegistry` | — | [CoroConfigRegistry.java](com/corosus/modconfig/CoroConfigRegistry.java) |
| `CommandCoroConfigClient` | — | [CommandCoroConfigClient.java](com/corosus/coroutil/command/CommandCoroConfigClient.java) |
| `CommandCoroConfig` | — | [CommandCoroConfig.java](com/corosus/coroutil/command/CommandCoroConfig.java) |
| `ModConfigData` | — | [ModConfigData.java](com/corosus/modconfig/ModConfigData.java) |
| `ConfigCoroUtil` | — | [ConfigCoroUtil.java](com/corosus/coroutil/config/ConfigCoroUtil.java) |
| `ModConfigDataNeoForge` | — | [ModConfigDataNeoForge.java](com/corosus/coroutil/loader/neoforge/ModConfigDataNeoForge.java) |
| `ConfigMod` | — | [ConfigMod.java](com/corosus/modconfig/ConfigMod.java) |
| `IConfigInstance` | — | [IConfigInstance.java](com/corosus/modconfig/IConfigInstance.java) |
| `IConfigCategory` | — | [IConfigCategory.java](com/corosus/modconfig/IConfigCategory.java) |
| `ConfigAddQueue` | — | [ConfigAddQueue.java](com/corosus/modconfig/ConfigAddQueue.java) |
| `ConfigEntryInfo` | — | [ConfigEntryInfo.java](com/corosus/modconfig/ConfigEntryInfo.java) |
| `ConfigComment` | Retention;Target | [ConfigComment.java](com/corosus/modconfig/ConfigComment.java) |
| `ConfigComparatorName` | — | [ConfigComparatorName.java](com/corosus/modconfig/ConfigComparatorName.java) |
| `ConfigParams` | Retention;Target | [ConfigParams.java](com/corosus/modconfig/ConfigParams.java) |

## 全部类（36）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigModNeoForge` | class | ConfigMod | Mod | 5 | 57 | [ConfigModNeoForge.java](com/corosus/coroutil/loader/neoforge/ConfigModNeoForge.java) |
| `CoroConfigRegistry` | class | — | — | 13 | 140 | [CoroConfigRegistry.java](com/corosus/modconfig/CoroConfigRegistry.java) |
| `ClientEvents` | class | — | — | 2 | 17 | [ClientEvents.java](com/corosus/coroutil/loader/neoforge/ClientEvents.java) |
| `CommandCoroConfigClient` | class | — | — | 2 | 29 | [CommandCoroConfigClient.java](com/corosus/coroutil/command/CommandCoroConfigClient.java) |
| `CommandCoroConfig` | class | — | — | 9 | 137 | [CommandCoroConfig.java](com/corosus/coroutil/command/CommandCoroConfig.java) |
| `ModConfigData` | class | — | — | 16 | 182 | [ModConfigData.java](com/corosus/modconfig/ModConfigData.java) |
| `ConfigCoroUtil` | class | IConfigCategory | — | 5 | 37 | [ConfigCoroUtil.java](com/corosus/coroutil/config/ConfigCoroUtil.java) |
| `CoroUtilEntOrParticle` | class | — | — | 26 | 140 | [CoroUtilEntOrParticle.java](com/corosus/coroutil/util/CoroUtilEntOrParticle.java) |
| `ModConfigDataNeoForge` | class | ModConfigData | — | 7 | 111 | [ModConfigDataNeoForge.java](com/corosus/coroutil/loader/neoforge/ModConfigDataNeoForge.java) |
| `MMCQ` | class | — | — | 19 | 423 | [MMCQ.java](com/corosus/coroutil/repack/de/androidpit/colorthief/MMCQ.java) |
| `ConfigMod` | class | — | — | 6 | 37 | [ConfigMod.java](com/corosus/modconfig/ConfigMod.java) |
| `CoroUtilCompatibility` | class | — | — | 6 | 70 | [CoroUtilCompatibility.java](com/corosus/coroutil/util/CoroUtilCompatibility.java) |
| `IConfigInstance` | interface | — | — | 0 | 8 | [IConfigInstance.java](com/corosus/modconfig/IConfigInstance.java) |
| `IConfigCategory` | interface | — | — | 0 | 14 | [IConfigCategory.java](com/corosus/modconfig/IConfigCategory.java) |
| `ConfigAddQueue` | class | — | — | 0 | 12 | [ConfigAddQueue.java](com/corosus/modconfig/ConfigAddQueue.java) |
| `ConfigEntryInfo` | class | — | — | 0 | 17 | [ConfigEntryInfo.java](com/corosus/modconfig/ConfigEntryInfo.java) |
| `ConfigComment` | @interface | — | Retention、Target | 0 | 13 | [ConfigComment.java](com/corosus/modconfig/ConfigComment.java) |
| `ConfigComparatorName` | class | Comparator<ConfigEntryInfo> | — | 1 | 10 | [ConfigComparatorName.java](com/corosus/modconfig/ConfigComparatorName.java) |
| `ConfigParams` | @interface | — | Retention、Target | 0 | 17 | [ConfigParams.java](com/corosus/modconfig/ConfigParams.java) |
| `CoroUtilBlock` | class | — | — | 3 | 21 | [CoroUtilBlock.java](com/corosus/coroutil/util/CoroUtilBlock.java) |
| `CoroUtilEntity` | class | — | — | 2 | 24 | [CoroUtilEntity.java](com/corosus/coroutil/util/CoroUtilEntity.java) |
| `ChunkCoordinatesBlock` | class | BlockPos | — | 0 | 19 | [ChunkCoordinatesBlock.java](com/corosus/coroutil/util/ChunkCoordinatesBlock.java) |
| `ReflectionHelper` | class | RuntimeException | — | 5 | 122 | [ReflectionHelper.java](com/corosus/coroutil/util/ReflectionHelper.java) |
| `CoroUtilWorldTime` | class | — | — | 6 | 32 | [CoroUtilWorldTime.java](com/corosus/coroutil/util/CoroUtilWorldTime.java) |
| `MultiLoaderUtil` | class | — | — | 6 | 98 | [MultiLoaderUtil.java](com/corosus/coroutil/util/MultiLoaderUtil.java) |
| `RGBUtil` | class | — | — | 4 | 36 | [RGBUtil.java](com/corosus/coroutil/repack/de/androidpit/colorthief/RGBUtil.java) |
| `ColorThief` | class | — | — | 8 | 121 | [ColorThief.java](com/corosus/coroutil/repack/de/androidpit/colorthief/ColorThief.java) |
| `CoroUtilMisc` | class | — | — | 2 | 28 | [CoroUtilMisc.java](com/corosus/coroutil/util/CoroUtilMisc.java) |
| `CoroUtilPath` | class | — | — | 2 | 95 | [CoroUtilPath.java](com/corosus/coroutil/util/CoroUtilPath.java) |
| `CoroUtilPhysics` | class | — | — | 4 | 61 | [CoroUtilPhysics.java](com/corosus/coroutil/util/CoroUtilPhysics.java) |
| `CoroUtilAttributes` | class | — | — | 0 | 8 | [CoroUtilAttributes.java](com/corosus/coroutil/util/CoroUtilAttributes.java) |
| `OldUtil` | class | — | — | 2 | 22 | [OldUtil.java](com/corosus/coroutil/util/OldUtil.java) |
| `CoroUtilParticle` | class | — | — | 1 | 27 | [CoroUtilParticle.java](com/corosus/coroutil/util/CoroUtilParticle.java) |
| `CoroUtilColor` | class | — | — | 0 | 5 | [CoroUtilColor.java](com/corosus/coroutil/util/CoroUtilColor.java) |
| `CU` | class | — | — | 1 | 12 | [CU.java](com/corosus/coroutil/util/CU.java) |
| `CULog` | class | — | — | 3 | 28 | [CULog.java](com/corosus/coroutil/util/CULog.java) |
