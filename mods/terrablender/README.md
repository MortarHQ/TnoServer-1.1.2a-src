# TerraBlender（`terrablender`）

> jar：`[前置]TerraBlender-neoforge-1.21.1-4.1.0.8.jar` ｜ 版本：`${file.jarVersion}` ｜ 作者：Adubbz
> 规模：**49 个类 / 10 个包 / 3067 行** ｜ 事件处理类 0 ｜ Mixin 11 ｜ 注册/管理类 3

**官方描述**：A library mod for adding biomes in a simple and compatible manner with Minecraft's new biome/terrain system.

**依赖**：`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `terrablender.mixin` | 11 | `MixinParameterList`、`MixinPrimaryLevelData`、`MultiNoiseBiomeSourceAccess` |
| `terrablender.worldgen.noise` | 11 | `LayeredNoiseUtil`、`ZoomLayer`、`AreaContext` |
| `terrablender.api` | 9 | `EndBiomeRegistry`、`ParameterUtils`、`SurfaceRuleManager` |
| `terrablender.worldgen` | 9 | `TBSurfaceRuleData`、`RegionUtils`、`IExtendedTheEndBiomeSource` |
| `terrablender.core` | 2 | `TerraBlenderNeoForge`、`TerraBlender` |
| `terrablender.config` | 2 | `Config`、`TerraBlenderConfig` |
| `terrablender.util` | 2 | `LevelUtils`、`WeightedRandomList` |
| `terrablender.handler` | 1 | `InitializationHandler` |
| `terrablender` | 1 | `DimensionTypeTags` |
| `terrablender.worldgen.surface` | 1 | `NamespacedSurfaceRuleSource` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 49）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TerraBlenderNeoForge` | class | — | Mod | 0 | 19 | [TerraBlenderNeoForge.java](terrablender/core/TerraBlenderNeoForge.java) |
| `Config` | class | UnmodifiableConfig | — | 34 | 186 | [Config.java](terrablender/config/Config.java) |
| `MixinParameterList` | class | IExtendedParameterList<T> | Mixin | 10 | 133 | [MixinParameterList.java](terrablender/mixin/MixinParameterList.java) |
| `MixinPrimaryLevelData` | class | — | Mixin | 1 | 17 | [MixinPrimaryLevelData.java](terrablender/mixin/MixinPrimaryLevelData.java) |
| `EndBiomeRegistry` | class | — | — | 8 | 58 | [EndBiomeRegistry.java](terrablender/api/EndBiomeRegistry.java) |
| `ParameterUtils` | class | — | — | 28 | 303 | [ParameterUtils.java](terrablender/api/ParameterUtils.java) |
| `MultiNoiseBiomeSourceAccess` | interface | — | Mixin | 0 | 22 | [MultiNoiseBiomeSourceAccess.java](terrablender/mixin/MultiNoiseBiomeSourceAccess.java) |
| `MixinNoiseBasedChunkGenerator` | class | — | Mixin | 1 | 37 | [MixinNoiseBasedChunkGenerator.java](terrablender/mixin/MixinNoiseBasedChunkGenerator.java) |
| `MixinMultiNoiseBiomeSource` | class | IExtendedMultiNoiseBiomeSource | Mixin | 4 | 49 | [MixinMultiNoiseBiomeSource.java](terrablender/mixin/MixinMultiNoiseBiomeSource.java) |
| `MixinTheEndBiomeSource` | class | IExtendedTheEndBiomeSource | Mixin | 4 | 120 | [MixinTheEndBiomeSource.java](terrablender/mixin/MixinTheEndBiomeSource.java) |
| `MixinBiomeSource` | class | BiomeResolver | Mixin | 1 | 36 | [MixinBiomeSource.java](terrablender/mixin/MixinBiomeSource.java) |
| `MixinChunkGenerator` | class | — | Mixin | 1 | 16 | [MixinChunkGenerator.java](terrablender/mixin/MixinChunkGenerator.java) |
| `MixinWorldOpenFlows` | class | — | Mixin | 1 | 20 | [MixinWorldOpenFlows.java](terrablender/mixin/MixinWorldOpenFlows.java) |
| `MixinNoiseGeneratorSettings` | class | IExtendedNoiseGeneratorSettings | Mixin | 2 | 39 | [MixinNoiseGeneratorSettings.java](terrablender/mixin/MixinNoiseGeneratorSettings.java) |
| `MixinBuiltInRegistries` | class | — | Mixin | 2 | 40 | [MixinBuiltInRegistries.java](terrablender/mixin/MixinBuiltInRegistries.java) |
| `SurfaceRuleManager` | class | — | — | 7 | 89 | [SurfaceRuleManager.java](terrablender/api/SurfaceRuleManager.java) |
| `InitializationHandler` | class | — | — | 1 | 11 | [InitializationHandler.java](terrablender/handler/InitializationHandler.java) |
| `TBSurfaceRuleData` | class | — | — | 7 | 501 | [TBSurfaceRuleData.java](terrablender/worldgen/TBSurfaceRuleData.java) |
| `TerraBlenderConfig` | class | Config | — | 1 | 49 | [TerraBlenderConfig.java](terrablender/config/TerraBlenderConfig.java) |
| `LayeredNoiseUtil` | class | — | — | 6 | 55 | [LayeredNoiseUtil.java](terrablender/worldgen/noise/LayeredNoiseUtil.java) |
| `LevelUtils` | class | — | — | 5 | 96 | [LevelUtils.java](terrablender/util/LevelUtils.java) |
| `RegionUtils` | class | — | — | 1 | 42 | [RegionUtils.java](terrablender/worldgen/RegionUtils.java) |
| `ZoomLayer` | enum | AreaTransformer1 | — | 5 | 73 | [ZoomLayer.java](terrablender/worldgen/noise/ZoomLayer.java) |
| `AreaContext` | class | — | — | 8 | 67 | [AreaContext.java](terrablender/worldgen/noise/AreaContext.java) |
| `VanillaParameterOverlayBuilder` | class | — | — | 7 | 179 | [VanillaParameterOverlayBuilder.java](terrablender/api/VanillaParameterOverlayBuilder.java) |
| `Region` | class | — | — | 9 | 99 | [Region.java](terrablender/api/Region.java) |
| `Regions` | class | — | — | 7 | 79 | [Regions.java](terrablender/api/Regions.java) |
| `ModifiedVanillaOverworldBuilder` | class | — | — | 5 | 61 | [ModifiedVanillaOverworldBuilder.java](terrablender/api/ModifiedVanillaOverworldBuilder.java) |
| `DimensionTypeTags` | class | — | — | 2 | 19 | [DimensionTypeTags.java](terrablender/DimensionTypeTags.java) |
| `TerraBlender` | class | — | — | 1 | 16 | [TerraBlender.java](terrablender/core/TerraBlender.java) |
| `IExtendedTheEndBiomeSource` | interface | — | — | 0 | 8 | [IExtendedTheEndBiomeSource.java](terrablender/worldgen/IExtendedTheEndBiomeSource.java) |
| `DefaultNetherRegion` | class | Region | — | 1 | 81 | [DefaultNetherRegion.java](terrablender/worldgen/DefaultNetherRegion.java) |
| `IExtendedParameterList` | interface | — | — | 0 | 29 | [IExtendedParameterList.java](terrablender/worldgen/IExtendedParameterList.java) |
| `IExtendedMultiNoiseBiomeSource` | interface | — | — | 0 | 8 | [IExtendedMultiNoiseBiomeSource.java](terrablender/worldgen/IExtendedMultiNoiseBiomeSource.java) |
| `IExtendedNoiseGeneratorSettings` | interface | — | — | 0 | 8 | [IExtendedNoiseGeneratorSettings.java](terrablender/worldgen/IExtendedNoiseGeneratorSettings.java) |
| `IExtendedBiomeSource` | interface | — | — | 0 | 10 | [IExtendedBiomeSource.java](terrablender/worldgen/IExtendedBiomeSource.java) |
| `DefaultOverworldRegion` | class | Region | — | 1 | 26 | [DefaultOverworldRegion.java](terrablender/worldgen/DefaultOverworldRegion.java) |
| `AreaTransformer1` | interface | — | — | 0 | 16 | [AreaTransformer1.java](terrablender/worldgen/noise/AreaTransformer1.java) |
| `BiomeInitialLayer` | class | WeightedRandomLayer | — | 3 | 37 | [BiomeInitialLayer.java](terrablender/worldgen/noise/BiomeInitialLayer.java) |
| `PixelTransformer` | interface | — | — | 0 | 6 | [PixelTransformer.java](terrablender/worldgen/noise/PixelTransformer.java) |
| `InitialLayer` | class | WeightedRandomLayer | — | 2 | 37 | [InitialLayer.java](terrablender/worldgen/noise/InitialLayer.java) |
| `WeightedRandomLayer` | class | AreaTransformer0 | — | 3 | 25 | [WeightedRandomLayer.java](terrablender/worldgen/noise/WeightedRandomLayer.java) |
| `AreaTransformer0` | interface | — | — | 0 | 13 | [AreaTransformer0.java](terrablender/worldgen/noise/AreaTransformer0.java) |
| `AreaFactory` | interface | — | — | 0 | 6 | [AreaFactory.java](terrablender/worldgen/noise/AreaFactory.java) |
| `Area` | class | — | — | 4 | 56 | [Area.java](terrablender/worldgen/noise/Area.java) |
| `NamespacedSurfaceRuleSource` | record | RuleSource | — | 3 | 53 | [NamespacedSurfaceRuleSource.java](terrablender/worldgen/surface/NamespacedSurfaceRuleSource.java) |
| `RegionType` | enum | — | — | 0 | 7 | [RegionType.java](terrablender/api/RegionType.java) |
| `TerrablenderOverworldBiomeBuilder` | class | OverworldBiomeBuilder | — | 3 | 69 | [TerrablenderOverworldBiomeBuilder.java](terrablender/api/TerrablenderOverworldBiomeBuilder.java) |
| `WeightedRandomList` | class | — | — | 4 | 41 | [WeightedRandomList.java](terrablender/util/WeightedRandomList.java) |

## Mixin（11 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinParameterList` | `ParameterList` | [MixinParameterList.java](terrablender/mixin/MixinParameterList.java) |
| `MixinPrimaryLevelData` | `PrimaryLevelData` | [MixinPrimaryLevelData.java](terrablender/mixin/MixinPrimaryLevelData.java) |
| `MultiNoiseBiomeSourceAccess` | `MultiNoiseBiomeSourceAccess` | [MultiNoiseBiomeSourceAccess.java](terrablender/mixin/MultiNoiseBiomeSourceAccess.java) |
| `MixinNoiseBasedChunkGenerator` | `NoiseBasedChunkGenerator` | [MixinNoiseBasedChunkGenerator.java](terrablender/mixin/MixinNoiseBasedChunkGenerator.java) |
| `MixinMultiNoiseBiomeSource` | `MultiNoiseBiomeSource` | [MixinMultiNoiseBiomeSource.java](terrablender/mixin/MixinMultiNoiseBiomeSource.java) |
| `MixinTheEndBiomeSource` | `TheEndBiomeSource` | [MixinTheEndBiomeSource.java](terrablender/mixin/MixinTheEndBiomeSource.java) |
| `MixinBiomeSource` | `BiomeSource` | [MixinBiomeSource.java](terrablender/mixin/MixinBiomeSource.java) |
| `MixinChunkGenerator` | `ChunkGenerator` | [MixinChunkGenerator.java](terrablender/mixin/MixinChunkGenerator.java) |
| `MixinWorldOpenFlows` | `WorldOpenFlows` | [MixinWorldOpenFlows.java](terrablender/mixin/MixinWorldOpenFlows.java) |
| `MixinNoiseGeneratorSettings` | `NoiseGeneratorSettings` | [MixinNoiseGeneratorSettings.java](terrablender/mixin/MixinNoiseGeneratorSettings.java) |
| `MixinBuiltInRegistries` | `BuiltInRegistries` | [MixinBuiltInRegistries.java](terrablender/mixin/MixinBuiltInRegistries.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](terrablender/config/Config.java) |
| `TerraBlenderConfig` | — | [TerraBlenderConfig.java](terrablender/config/TerraBlenderConfig.java) |

## 全部类（49）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TerraBlenderNeoForge` | class | — | Mod | 0 | 19 | [TerraBlenderNeoForge.java](terrablender/core/TerraBlenderNeoForge.java) |
| `Config` | class | UnmodifiableConfig | — | 34 | 186 | [Config.java](terrablender/config/Config.java) |
| `MixinParameterList` | class | IExtendedParameterList<T> | Mixin | 10 | 133 | [MixinParameterList.java](terrablender/mixin/MixinParameterList.java) |
| `MixinPrimaryLevelData` | class | — | Mixin | 1 | 17 | [MixinPrimaryLevelData.java](terrablender/mixin/MixinPrimaryLevelData.java) |
| `EndBiomeRegistry` | class | — | — | 8 | 58 | [EndBiomeRegistry.java](terrablender/api/EndBiomeRegistry.java) |
| `ParameterUtils` | class | — | — | 28 | 303 | [ParameterUtils.java](terrablender/api/ParameterUtils.java) |
| `MultiNoiseBiomeSourceAccess` | interface | — | Mixin | 0 | 22 | [MultiNoiseBiomeSourceAccess.java](terrablender/mixin/MultiNoiseBiomeSourceAccess.java) |
| `MixinNoiseBasedChunkGenerator` | class | — | Mixin | 1 | 37 | [MixinNoiseBasedChunkGenerator.java](terrablender/mixin/MixinNoiseBasedChunkGenerator.java) |
| `MixinMultiNoiseBiomeSource` | class | IExtendedMultiNoiseBiomeSource | Mixin | 4 | 49 | [MixinMultiNoiseBiomeSource.java](terrablender/mixin/MixinMultiNoiseBiomeSource.java) |
| `MixinTheEndBiomeSource` | class | IExtendedTheEndBiomeSource | Mixin | 4 | 120 | [MixinTheEndBiomeSource.java](terrablender/mixin/MixinTheEndBiomeSource.java) |
| `MixinBiomeSource` | class | BiomeResolver | Mixin | 1 | 36 | [MixinBiomeSource.java](terrablender/mixin/MixinBiomeSource.java) |
| `MixinChunkGenerator` | class | — | Mixin | 1 | 16 | [MixinChunkGenerator.java](terrablender/mixin/MixinChunkGenerator.java) |
| `MixinWorldOpenFlows` | class | — | Mixin | 1 | 20 | [MixinWorldOpenFlows.java](terrablender/mixin/MixinWorldOpenFlows.java) |
| `MixinNoiseGeneratorSettings` | class | IExtendedNoiseGeneratorSettings | Mixin | 2 | 39 | [MixinNoiseGeneratorSettings.java](terrablender/mixin/MixinNoiseGeneratorSettings.java) |
| `MixinBuiltInRegistries` | class | — | Mixin | 2 | 40 | [MixinBuiltInRegistries.java](terrablender/mixin/MixinBuiltInRegistries.java) |
| `SurfaceRuleManager` | class | — | — | 7 | 89 | [SurfaceRuleManager.java](terrablender/api/SurfaceRuleManager.java) |
| `InitializationHandler` | class | — | — | 1 | 11 | [InitializationHandler.java](terrablender/handler/InitializationHandler.java) |
| `TBSurfaceRuleData` | class | — | — | 7 | 501 | [TBSurfaceRuleData.java](terrablender/worldgen/TBSurfaceRuleData.java) |
| `TerraBlenderConfig` | class | Config | — | 1 | 49 | [TerraBlenderConfig.java](terrablender/config/TerraBlenderConfig.java) |
| `LayeredNoiseUtil` | class | — | — | 6 | 55 | [LayeredNoiseUtil.java](terrablender/worldgen/noise/LayeredNoiseUtil.java) |
| `LevelUtils` | class | — | — | 5 | 96 | [LevelUtils.java](terrablender/util/LevelUtils.java) |
| `RegionUtils` | class | — | — | 1 | 42 | [RegionUtils.java](terrablender/worldgen/RegionUtils.java) |
| `ZoomLayer` | enum | AreaTransformer1 | — | 5 | 73 | [ZoomLayer.java](terrablender/worldgen/noise/ZoomLayer.java) |
| `AreaContext` | class | — | — | 8 | 67 | [AreaContext.java](terrablender/worldgen/noise/AreaContext.java) |
| `VanillaParameterOverlayBuilder` | class | — | — | 7 | 179 | [VanillaParameterOverlayBuilder.java](terrablender/api/VanillaParameterOverlayBuilder.java) |
| `Region` | class | — | — | 9 | 99 | [Region.java](terrablender/api/Region.java) |
| `Regions` | class | — | — | 7 | 79 | [Regions.java](terrablender/api/Regions.java) |
| `ModifiedVanillaOverworldBuilder` | class | — | — | 5 | 61 | [ModifiedVanillaOverworldBuilder.java](terrablender/api/ModifiedVanillaOverworldBuilder.java) |
| `DimensionTypeTags` | class | — | — | 2 | 19 | [DimensionTypeTags.java](terrablender/DimensionTypeTags.java) |
| `TerraBlender` | class | — | — | 1 | 16 | [TerraBlender.java](terrablender/core/TerraBlender.java) |
| `IExtendedTheEndBiomeSource` | interface | — | — | 0 | 8 | [IExtendedTheEndBiomeSource.java](terrablender/worldgen/IExtendedTheEndBiomeSource.java) |
| `DefaultNetherRegion` | class | Region | — | 1 | 81 | [DefaultNetherRegion.java](terrablender/worldgen/DefaultNetherRegion.java) |
| `IExtendedParameterList` | interface | — | — | 0 | 29 | [IExtendedParameterList.java](terrablender/worldgen/IExtendedParameterList.java) |
| `IExtendedMultiNoiseBiomeSource` | interface | — | — | 0 | 8 | [IExtendedMultiNoiseBiomeSource.java](terrablender/worldgen/IExtendedMultiNoiseBiomeSource.java) |
| `IExtendedNoiseGeneratorSettings` | interface | — | — | 0 | 8 | [IExtendedNoiseGeneratorSettings.java](terrablender/worldgen/IExtendedNoiseGeneratorSettings.java) |
| `IExtendedBiomeSource` | interface | — | — | 0 | 10 | [IExtendedBiomeSource.java](terrablender/worldgen/IExtendedBiomeSource.java) |
| `DefaultOverworldRegion` | class | Region | — | 1 | 26 | [DefaultOverworldRegion.java](terrablender/worldgen/DefaultOverworldRegion.java) |
| `AreaTransformer1` | interface | — | — | 0 | 16 | [AreaTransformer1.java](terrablender/worldgen/noise/AreaTransformer1.java) |
| `BiomeInitialLayer` | class | WeightedRandomLayer | — | 3 | 37 | [BiomeInitialLayer.java](terrablender/worldgen/noise/BiomeInitialLayer.java) |
| `PixelTransformer` | interface | — | — | 0 | 6 | [PixelTransformer.java](terrablender/worldgen/noise/PixelTransformer.java) |
| `InitialLayer` | class | WeightedRandomLayer | — | 2 | 37 | [InitialLayer.java](terrablender/worldgen/noise/InitialLayer.java) |
| `WeightedRandomLayer` | class | AreaTransformer0 | — | 3 | 25 | [WeightedRandomLayer.java](terrablender/worldgen/noise/WeightedRandomLayer.java) |
| `AreaTransformer0` | interface | — | — | 0 | 13 | [AreaTransformer0.java](terrablender/worldgen/noise/AreaTransformer0.java) |
| `AreaFactory` | interface | — | — | 0 | 6 | [AreaFactory.java](terrablender/worldgen/noise/AreaFactory.java) |
| `Area` | class | — | — | 4 | 56 | [Area.java](terrablender/worldgen/noise/Area.java) |
| `NamespacedSurfaceRuleSource` | record | RuleSource | — | 3 | 53 | [NamespacedSurfaceRuleSource.java](terrablender/worldgen/surface/NamespacedSurfaceRuleSource.java) |
| `RegionType` | enum | — | — | 0 | 7 | [RegionType.java](terrablender/api/RegionType.java) |
| `TerrablenderOverworldBiomeBuilder` | class | OverworldBiomeBuilder | — | 3 | 69 | [TerrablenderOverworldBiomeBuilder.java](terrablender/api/TerrablenderOverworldBiomeBuilder.java) |
| `WeightedRandomList` | class | — | — | 4 | 41 | [WeightedRandomList.java](terrablender/util/WeightedRandomList.java) |
