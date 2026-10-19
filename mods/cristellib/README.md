# Cristel Lib（`cristellib`）

> jar：`[前置]cristellib.jar` ｜ 版本：`3.0.3` ｜ 作者：Cristelknight
> 规模：**59 个类 / 22 个包 / 4167 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 4

**依赖**：`neoforge`、`minecraft`、`t_and_t`、`expanded_ecosphere`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `de.cristelknight.cristellib.util` | 6 | `Util`、`RuntimePackUtil`、`JanksonUtil` |
| `de.cristelknight.cristellib` | 5 | `StructureConfig`、`CristelLibRegistry`、`ModLoadingUtil` |
| `de.cristelknight.cristellib.builtinpacks` | 5 | `RuntimePack`、`BuiltInPackConfig`、`BuiltInDataPackLoader` |
| `de.cristelknight.cristellib.util.jankson` | 4 | `JanksonOps`、`CommentsMap`、`CommentArray` |
| `de.cristelknight.cristellib.config.client` | 4 | `ClientStructureConfig`、`ClientEDConfig`、`ScreenBuilder` |
| `de.cristelknight.cristellib.data.codec` | 4 | `StructureSetData`、`CopyFileData`、`BuiltInPackDataWrapper` |
| `de.cristelknight.cristellib.config.client.simple` | 3 | `ClientConfigRegistry`、`SimpleConfigScreen`、`SimpleScreenBuilder` |
| `de.cristelknight.cristellib.config.simple` | 3 | `ConfigRegistry`、`ConfigHolder`、`ConfigSettings` |
| `de.cristelknight.cristellib.neoforge` | 3 | `CristelLibForge`、`ModLoadingUtilImpl`、`CristelLibExpectPlatformImpl` |
| `de.cristelknight.cristellib.autoconfig` | 3 | `ACConfig`、`ACInfoData`、`ModFinder` |
| `de.cristelknight.cristellib.config.serialize.ed` | 3 | `NestedEDConfig`、`EDConfigTransformer`、`EDConfig` |
| `de.cristelknight.cristellib.data` | 3 | `ReadData`、`PathFinder`、`Conditions` |
| `de.cristelknight.cristellib.api` | 3 | `CristelPlugin`、`CristelLibAPI`、`BuiltInAPI` |
| `de.cristelknight.cristellib.config` | 2 | `ConfigManager`、`ConfigType` |
| `de.cristelknight.cristellib.mixin` | 1 | `PackSelectionScreenMixin` |
| `de.cristelknight.cristellib.config.serialize.placement` | 1 | `PlacementConfig` |
| `de.cristelknight.cristellib.neoforge.client` | 1 | `NeoForgeClient` |
| `de.cristelknight.cristellib.config.simple.datafixer` | 1 | `DataFixer` |
| `architectury_inject_cristellib_common_93834e7f941d405b9ad4c702e5132722_28e7872050579be46e64fe50e9512544ad86d970c20f1f075074963c2f954c40cristellibcommon1211303devjar` | 1 | `PlatformMethods` |
| `de.cristelknight.cristellib.neoforge.extrapackutil` | 1 | `RepositorySourceMaker` |
| `de.cristelknight.cristellib.neoforge.extraapiutil` | 1 | `APIFinder` |
| `de.cristelknight.cristellib.registry` | 1 | `ReadStructureSets` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 59）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientConfigRegistry` | class | — | OnlyIn | 5 | 42 | [ClientConfigRegistry.java](de/cristelknight/cristellib/config/client/simple/ClientConfigRegistry.java) |
| `ConfigManager` | class | — | — | 12 | 187 | [ConfigManager.java](de/cristelknight/cristellib/config/ConfigManager.java) |
| `ConfigRegistry` | class | — | — | 8 | 66 | [ConfigRegistry.java](de/cristelknight/cristellib/config/simple/ConfigRegistry.java) |
| `CristelLibForge` | class | — | Mod | 1 | 31 | [CristelLibForge.java](de/cristelknight/cristellib/neoforge/CristelLibForge.java) |
| `StructureConfig` | class | — | — | 20 | 243 | [StructureConfig.java](de/cristelknight/cristellib/StructureConfig.java) |
| `PackSelectionScreenMixin` | class | — | Mixin | 0 | 33 | [PackSelectionScreenMixin.java](de/cristelknight/cristellib/mixin/PackSelectionScreenMixin.java) |
| `JanksonOps` | record | AbstractStringBuilder | — | 41 | 431 | [JanksonOps.java](de/cristelknight/cristellib/util/jankson/JanksonOps.java) |
| `CristelLibRegistry` | class | — | — | 4 | 44 | [CristelLibRegistry.java](de/cristelknight/cristellib/CristelLibRegistry.java) |
| `RuntimePack` | class | PackResources | — | 26 | 308 | [RuntimePack.java](de/cristelknight/cristellib/builtinpacks/RuntimePack.java) |
| `ClientStructureConfig` | record | — | OnlyIn | 0 | 14 | [ClientStructureConfig.java](de/cristelknight/cristellib/config/client/ClientStructureConfig.java) |
| `ClientEDConfig` | record | — | OnlyIn | 1 | 17 | [ClientEDConfig.java](de/cristelknight/cristellib/config/client/ClientEDConfig.java) |
| `ScreenBuilder` | class | — | OnlyIn | 18 | 346 | [ScreenBuilder.java](de/cristelknight/cristellib/config/client/ScreenBuilder.java) |
| `ClientPlacementConfig` | record | — | OnlyIn | 1 | 15 | [ClientPlacementConfig.java](de/cristelknight/cristellib/config/client/ClientPlacementConfig.java) |
| `SimpleConfigScreen` | record | — | OnlyIn | 0 | 9 | [SimpleConfigScreen.java](de/cristelknight/cristellib/config/client/simple/SimpleConfigScreen.java) |
| `BuiltInPackConfig` | record | — | — | 6 | 79 | [BuiltInPackConfig.java](de/cristelknight/cristellib/builtinpacks/BuiltInPackConfig.java) |
| `ACConfig` | record | — | — | 6 | 104 | [ACConfig.java](de/cristelknight/cristellib/autoconfig/ACConfig.java) |
| `ConfigHolder` | class | — | — | 9 | 70 | [ConfigHolder.java](de/cristelknight/cristellib/config/simple/ConfigHolder.java) |
| `ConfigType` | enum | StringRepresentable | — | 1 | 23 | [ConfigType.java](de/cristelknight/cristellib/config/ConfigType.java) |
| `PlacementConfig` | record | — | — | 0 | 30 | [PlacementConfig.java](de/cristelknight/cristellib/config/serialize/placement/PlacementConfig.java) |
| `NestedEDConfig` | record | — | — | 4 | 32 | [NestedEDConfig.java](de/cristelknight/cristellib/config/serialize/ed/NestedEDConfig.java) |
| `EDConfigTransformer` | class | — | — | 4 | 91 | [EDConfigTransformer.java](de/cristelknight/cristellib/config/serialize/ed/EDConfigTransformer.java) |
| `EDConfig` | record | — | — | 2 | 14 | [EDConfig.java](de/cristelknight/cristellib/config/serialize/ed/EDConfig.java) |
| `ConfigSettings` | interface | — | — | 0 | 27 | [ConfigSettings.java](de/cristelknight/cristellib/config/simple/ConfigSettings.java) |
| `SimpleScreenBuilder` | class | — | OnlyIn | 6 | 141 | [SimpleScreenBuilder.java](de/cristelknight/cristellib/config/client/simple/SimpleScreenBuilder.java) |
| `Util` | class | — | — | 10 | 117 | [Util.java](de/cristelknight/cristellib/util/Util.java) |
| `BuiltInDataPackLoader` | class | — | — | 8 | 87 | [BuiltInDataPackLoader.java](de/cristelknight/cristellib/builtinpacks/BuiltInDataPackLoader.java) |
| `ModLoadingUtilImpl` | class | — | — | 5 | 60 | [ModLoadingUtilImpl.java](de/cristelknight/cristellib/neoforge/ModLoadingUtilImpl.java) |
| `NeoForgeClient` | class | — | — | 3 | 44 | [NeoForgeClient.java](de/cristelknight/cristellib/neoforge/client/NeoForgeClient.java) |
| `ReadData` | class | — | — | 7 | 125 | [ReadData.java](de/cristelknight/cristellib/data/ReadData.java) |
| `ModLoadingUtil` | class | — | — | 2 | 21 | [ModLoadingUtil.java](de/cristelknight/cristellib/ModLoadingUtil.java) |
| `CristelLibExpectPlatform` | class | — | — | 9 | 72 | [CristelLibExpectPlatform.java](de/cristelknight/cristellib/CristelLibExpectPlatform.java) |
| `ACInfoData` | record | — | — | 2 | 28 | [ACInfoData.java](de/cristelknight/cristellib/autoconfig/ACInfoData.java) |
| `CristelLibExpectPlatformImpl` | class | — | — | 9 | 155 | [CristelLibExpectPlatformImpl.java](de/cristelknight/cristellib/neoforge/CristelLibExpectPlatformImpl.java) |
| `DataFixer` | class | — | — | 3 | 52 | [DataFixer.java](de/cristelknight/cristellib/config/simple/datafixer/DataFixer.java) |
| `StructureSetData` | record | — | — | 1 | 24 | [StructureSetData.java](de/cristelknight/cristellib/data/codec/StructureSetData.java) |
| `CopyFileData` | record | — | — | 0 | 21 | [CopyFileData.java](de/cristelknight/cristellib/data/codec/CopyFileData.java) |
| `BuiltInPackDataWrapper` | record | — | — | 0 | 13 | [BuiltInPackDataWrapper.java](de/cristelknight/cristellib/data/codec/BuiltInPackDataWrapper.java) |
| `BuiltInPackData` | record | — | — | 0 | 23 | [BuiltInPackData.java](de/cristelknight/cristellib/data/codec/BuiltInPackData.java) |
| `RuntimePackUtil` | class | — | — | 4 | 54 | [RuntimePackUtil.java](de/cristelknight/cristellib/util/RuntimePackUtil.java) |
| `JanksonUtil` | class | — | — | 3 | 104 | [JanksonUtil.java](de/cristelknight/cristellib/util/JanksonUtil.java) |
| `ModVersionComparator` | enum | — | — | 7 | 49 | [ModVersionComparator.java](de/cristelknight/cristellib/util/ModVersionComparator.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_cristellib_common_93834e7f941d405b9ad4c702e5132722_28e7872050579be46e64fe50e9512544ad86d970c20f1f075074963c2f954c40cristellibcommon1211303devjar/PlatformMethods.java) |
| `CristelLib` | class | — | — | 4 | 68 | [CristelLib.java](de/cristelknight/cristellib/CristelLib.java) |
| `BuiltinResourcePackSource` | record | PackSource | — | 2 | 18 | [BuiltinResourcePackSource.java](de/cristelknight/cristellib/builtinpacks/BuiltinResourcePackSource.java) |
| `BuiltInPack` | record | — | — | 0 | 9 | [BuiltInPack.java](de/cristelknight/cristellib/builtinpacks/BuiltInPack.java) |
| `ModFinder` | class | — | — | 4 | 136 | [ModFinder.java](de/cristelknight/cristellib/autoconfig/ModFinder.java) |
| `RepositorySourceMaker` | class | RepositorySource | — | 1 | 14 | [RepositorySourceMaker.java](de/cristelknight/cristellib/neoforge/extrapackutil/RepositorySourceMaker.java) |
| `APIFinder` | class | — | — | 1 | 46 | [APIFinder.java](de/cristelknight/cristellib/neoforge/extraapiutil/APIFinder.java) |
| `PathFinder` | class | — | — | 4 | 101 | [PathFinder.java](de/cristelknight/cristellib/data/PathFinder.java) |
| `Conditions` | class | — | — | 3 | 61 | [Conditions.java](de/cristelknight/cristellib/data/Conditions.java) |
| `CristelPlugin` | @interface | — | Retention、Target | 0 | 12 | [CristelPlugin.java](de/cristelknight/cristellib/api/CristelPlugin.java) |
| `CristelLibAPI` | interface | — | — | 0 | 17 | [CristelLibAPI.java](de/cristelknight/cristellib/api/CristelLibAPI.java) |
| `BuiltInAPI` | class | CristelLibAPI | CristelPlugin | 3 | 68 | [BuiltInAPI.java](de/cristelknight/cristellib/api/BuiltInAPI.java) |
| `ReadStructureSets` | class | — | — | 3 | 74 | [ReadStructureSets.java](de/cristelknight/cristellib/registry/ReadStructureSets.java) |
| `UnsafeByteArrayOutputStream` | class | OutputStream | — | 4 | 52 | [UnsafeByteArrayOutputStream.java](de/cristelknight/cristellib/util/UnsafeByteArrayOutputStream.java) |
| `Platform` | enum | — | — | 0 | 7 | [Platform.java](de/cristelknight/cristellib/util/Platform.java) |
| `CommentsMap` | interface | — | — | 0 | 7 | [CommentsMap.java](de/cristelknight/cristellib/util/jankson/CommentsMap.java) |
| `CommentArray` | class | JsonArray | — | 1 | 12 | [CommentArray.java](de/cristelknight/cristellib/util/jankson/CommentArray.java) |
| `Comments` | interface | — | — | 0 | 11 | [Comments.java](de/cristelknight/cristellib/util/jankson/Comments.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PackSelectionScreenMixin` | `PackSelectionScreen` | [PackSelectionScreenMixin.java](de/cristelknight/cristellib/mixin/PackSelectionScreenMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ClientConfigRegistry` | OnlyIn | [ClientConfigRegistry.java](de/cristelknight/cristellib/config/client/simple/ClientConfigRegistry.java) |
| `ConfigManager` | — | [ConfigManager.java](de/cristelknight/cristellib/config/ConfigManager.java) |
| `ConfigRegistry` | — | [ConfigRegistry.java](de/cristelknight/cristellib/config/simple/ConfigRegistry.java) |
| `StructureConfig` | — | [StructureConfig.java](de/cristelknight/cristellib/StructureConfig.java) |
| `ClientStructureConfig` | OnlyIn | [ClientStructureConfig.java](de/cristelknight/cristellib/config/client/ClientStructureConfig.java) |
| `ClientEDConfig` | OnlyIn | [ClientEDConfig.java](de/cristelknight/cristellib/config/client/ClientEDConfig.java) |
| `ClientPlacementConfig` | OnlyIn | [ClientPlacementConfig.java](de/cristelknight/cristellib/config/client/ClientPlacementConfig.java) |
| `SimpleConfigScreen` | OnlyIn | [SimpleConfigScreen.java](de/cristelknight/cristellib/config/client/simple/SimpleConfigScreen.java) |
| `BuiltInPackConfig` | — | [BuiltInPackConfig.java](de/cristelknight/cristellib/builtinpacks/BuiltInPackConfig.java) |
| `ACConfig` | — | [ACConfig.java](de/cristelknight/cristellib/autoconfig/ACConfig.java) |
| `ConfigHolder` | — | [ConfigHolder.java](de/cristelknight/cristellib/config/simple/ConfigHolder.java) |
| `ConfigType` | — | [ConfigType.java](de/cristelknight/cristellib/config/ConfigType.java) |
| `PlacementConfig` | — | [PlacementConfig.java](de/cristelknight/cristellib/config/serialize/placement/PlacementConfig.java) |
| `NestedEDConfig` | — | [NestedEDConfig.java](de/cristelknight/cristellib/config/serialize/ed/NestedEDConfig.java) |
| `EDConfigTransformer` | — | [EDConfigTransformer.java](de/cristelknight/cristellib/config/serialize/ed/EDConfigTransformer.java) |
| `EDConfig` | — | [EDConfig.java](de/cristelknight/cristellib/config/serialize/ed/EDConfig.java) |
| `ConfigSettings` | — | [ConfigSettings.java](de/cristelknight/cristellib/config/simple/ConfigSettings.java) |

## 全部类（59）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientConfigRegistry` | class | — | OnlyIn | 5 | 42 | [ClientConfigRegistry.java](de/cristelknight/cristellib/config/client/simple/ClientConfigRegistry.java) |
| `ConfigManager` | class | — | — | 12 | 187 | [ConfigManager.java](de/cristelknight/cristellib/config/ConfigManager.java) |
| `ConfigRegistry` | class | — | — | 8 | 66 | [ConfigRegistry.java](de/cristelknight/cristellib/config/simple/ConfigRegistry.java) |
| `CristelLibForge` | class | — | Mod | 1 | 31 | [CristelLibForge.java](de/cristelknight/cristellib/neoforge/CristelLibForge.java) |
| `StructureConfig` | class | — | — | 20 | 243 | [StructureConfig.java](de/cristelknight/cristellib/StructureConfig.java) |
| `PackSelectionScreenMixin` | class | — | Mixin | 0 | 33 | [PackSelectionScreenMixin.java](de/cristelknight/cristellib/mixin/PackSelectionScreenMixin.java) |
| `JanksonOps` | record | AbstractStringBuilder | — | 41 | 431 | [JanksonOps.java](de/cristelknight/cristellib/util/jankson/JanksonOps.java) |
| `CristelLibRegistry` | class | — | — | 4 | 44 | [CristelLibRegistry.java](de/cristelknight/cristellib/CristelLibRegistry.java) |
| `RuntimePack` | class | PackResources | — | 26 | 308 | [RuntimePack.java](de/cristelknight/cristellib/builtinpacks/RuntimePack.java) |
| `ClientStructureConfig` | record | — | OnlyIn | 0 | 14 | [ClientStructureConfig.java](de/cristelknight/cristellib/config/client/ClientStructureConfig.java) |
| `ClientEDConfig` | record | — | OnlyIn | 1 | 17 | [ClientEDConfig.java](de/cristelknight/cristellib/config/client/ClientEDConfig.java) |
| `ScreenBuilder` | class | — | OnlyIn | 18 | 346 | [ScreenBuilder.java](de/cristelknight/cristellib/config/client/ScreenBuilder.java) |
| `ClientPlacementConfig` | record | — | OnlyIn | 1 | 15 | [ClientPlacementConfig.java](de/cristelknight/cristellib/config/client/ClientPlacementConfig.java) |
| `SimpleConfigScreen` | record | — | OnlyIn | 0 | 9 | [SimpleConfigScreen.java](de/cristelknight/cristellib/config/client/simple/SimpleConfigScreen.java) |
| `BuiltInPackConfig` | record | — | — | 6 | 79 | [BuiltInPackConfig.java](de/cristelknight/cristellib/builtinpacks/BuiltInPackConfig.java) |
| `ACConfig` | record | — | — | 6 | 104 | [ACConfig.java](de/cristelknight/cristellib/autoconfig/ACConfig.java) |
| `ConfigHolder` | class | — | — | 9 | 70 | [ConfigHolder.java](de/cristelknight/cristellib/config/simple/ConfigHolder.java) |
| `ConfigType` | enum | StringRepresentable | — | 1 | 23 | [ConfigType.java](de/cristelknight/cristellib/config/ConfigType.java) |
| `PlacementConfig` | record | — | — | 0 | 30 | [PlacementConfig.java](de/cristelknight/cristellib/config/serialize/placement/PlacementConfig.java) |
| `NestedEDConfig` | record | — | — | 4 | 32 | [NestedEDConfig.java](de/cristelknight/cristellib/config/serialize/ed/NestedEDConfig.java) |
| `EDConfigTransformer` | class | — | — | 4 | 91 | [EDConfigTransformer.java](de/cristelknight/cristellib/config/serialize/ed/EDConfigTransformer.java) |
| `EDConfig` | record | — | — | 2 | 14 | [EDConfig.java](de/cristelknight/cristellib/config/serialize/ed/EDConfig.java) |
| `ConfigSettings` | interface | — | — | 0 | 27 | [ConfigSettings.java](de/cristelknight/cristellib/config/simple/ConfigSettings.java) |
| `SimpleScreenBuilder` | class | — | OnlyIn | 6 | 141 | [SimpleScreenBuilder.java](de/cristelknight/cristellib/config/client/simple/SimpleScreenBuilder.java) |
| `Util` | class | — | — | 10 | 117 | [Util.java](de/cristelknight/cristellib/util/Util.java) |
| `BuiltInDataPackLoader` | class | — | — | 8 | 87 | [BuiltInDataPackLoader.java](de/cristelknight/cristellib/builtinpacks/BuiltInDataPackLoader.java) |
| `ModLoadingUtilImpl` | class | — | — | 5 | 60 | [ModLoadingUtilImpl.java](de/cristelknight/cristellib/neoforge/ModLoadingUtilImpl.java) |
| `NeoForgeClient` | class | — | — | 3 | 44 | [NeoForgeClient.java](de/cristelknight/cristellib/neoforge/client/NeoForgeClient.java) |
| `ReadData` | class | — | — | 7 | 125 | [ReadData.java](de/cristelknight/cristellib/data/ReadData.java) |
| `ModLoadingUtil` | class | — | — | 2 | 21 | [ModLoadingUtil.java](de/cristelknight/cristellib/ModLoadingUtil.java) |
| `CristelLibExpectPlatform` | class | — | — | 9 | 72 | [CristelLibExpectPlatform.java](de/cristelknight/cristellib/CristelLibExpectPlatform.java) |
| `ACInfoData` | record | — | — | 2 | 28 | [ACInfoData.java](de/cristelknight/cristellib/autoconfig/ACInfoData.java) |
| `CristelLibExpectPlatformImpl` | class | — | — | 9 | 155 | [CristelLibExpectPlatformImpl.java](de/cristelknight/cristellib/neoforge/CristelLibExpectPlatformImpl.java) |
| `DataFixer` | class | — | — | 3 | 52 | [DataFixer.java](de/cristelknight/cristellib/config/simple/datafixer/DataFixer.java) |
| `StructureSetData` | record | — | — | 1 | 24 | [StructureSetData.java](de/cristelknight/cristellib/data/codec/StructureSetData.java) |
| `CopyFileData` | record | — | — | 0 | 21 | [CopyFileData.java](de/cristelknight/cristellib/data/codec/CopyFileData.java) |
| `BuiltInPackDataWrapper` | record | — | — | 0 | 13 | [BuiltInPackDataWrapper.java](de/cristelknight/cristellib/data/codec/BuiltInPackDataWrapper.java) |
| `BuiltInPackData` | record | — | — | 0 | 23 | [BuiltInPackData.java](de/cristelknight/cristellib/data/codec/BuiltInPackData.java) |
| `RuntimePackUtil` | class | — | — | 4 | 54 | [RuntimePackUtil.java](de/cristelknight/cristellib/util/RuntimePackUtil.java) |
| `JanksonUtil` | class | — | — | 3 | 104 | [JanksonUtil.java](de/cristelknight/cristellib/util/JanksonUtil.java) |
| `ModVersionComparator` | enum | — | — | 7 | 49 | [ModVersionComparator.java](de/cristelknight/cristellib/util/ModVersionComparator.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_cristellib_common_93834e7f941d405b9ad4c702e5132722_28e7872050579be46e64fe50e9512544ad86d970c20f1f075074963c2f954c40cristellibcommon1211303devjar/PlatformMethods.java) |
| `CristelLib` | class | — | — | 4 | 68 | [CristelLib.java](de/cristelknight/cristellib/CristelLib.java) |
| `BuiltinResourcePackSource` | record | PackSource | — | 2 | 18 | [BuiltinResourcePackSource.java](de/cristelknight/cristellib/builtinpacks/BuiltinResourcePackSource.java) |
| `BuiltInPack` | record | — | — | 0 | 9 | [BuiltInPack.java](de/cristelknight/cristellib/builtinpacks/BuiltInPack.java) |
| `ModFinder` | class | — | — | 4 | 136 | [ModFinder.java](de/cristelknight/cristellib/autoconfig/ModFinder.java) |
| `RepositorySourceMaker` | class | RepositorySource | — | 1 | 14 | [RepositorySourceMaker.java](de/cristelknight/cristellib/neoforge/extrapackutil/RepositorySourceMaker.java) |
| `APIFinder` | class | — | — | 1 | 46 | [APIFinder.java](de/cristelknight/cristellib/neoforge/extraapiutil/APIFinder.java) |
| `PathFinder` | class | — | — | 4 | 101 | [PathFinder.java](de/cristelknight/cristellib/data/PathFinder.java) |
| `Conditions` | class | — | — | 3 | 61 | [Conditions.java](de/cristelknight/cristellib/data/Conditions.java) |
| `CristelPlugin` | @interface | — | Retention、Target | 0 | 12 | [CristelPlugin.java](de/cristelknight/cristellib/api/CristelPlugin.java) |
| `CristelLibAPI` | interface | — | — | 0 | 17 | [CristelLibAPI.java](de/cristelknight/cristellib/api/CristelLibAPI.java) |
| `BuiltInAPI` | class | CristelLibAPI | CristelPlugin | 3 | 68 | [BuiltInAPI.java](de/cristelknight/cristellib/api/BuiltInAPI.java) |
| `ReadStructureSets` | class | — | — | 3 | 74 | [ReadStructureSets.java](de/cristelknight/cristellib/registry/ReadStructureSets.java) |
| `UnsafeByteArrayOutputStream` | class | OutputStream | — | 4 | 52 | [UnsafeByteArrayOutputStream.java](de/cristelknight/cristellib/util/UnsafeByteArrayOutputStream.java) |
| `Platform` | enum | — | — | 0 | 7 | [Platform.java](de/cristelknight/cristellib/util/Platform.java) |
| `CommentsMap` | interface | — | — | 0 | 7 | [CommentsMap.java](de/cristelknight/cristellib/util/jankson/CommentsMap.java) |
| `CommentArray` | class | JsonArray | — | 1 | 12 | [CommentArray.java](de/cristelknight/cristellib/util/jankson/CommentArray.java) |
| `Comments` | interface | — | — | 0 | 11 | [Comments.java](de/cristelknight/cristellib/util/jankson/Comments.java) |
