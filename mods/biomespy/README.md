# BiomeSpy（`biomespy`）

> jar：`[优化]biomespy.jar` ｜ 版本：`1.3.3` ｜ 作者：MoePus
> 规模：**24 个类 / 8 个包 / 1112 行** ｜ 事件处理类 0 ｜ Mixin 8 ｜ 注册/管理类 1

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.moepus.biomespy.mixin` | 6 | `StructureManagerAccessor`、`StructureCheckMixin`、`MultiNoiseBiomeSourceMixin` |
| `com.moepus.biomespy.biome` | 5 | `LazyBiomeNoiseChecker`、`BiomeFinder`、`BiomeEnvelopeSelector` |
| `com.moepus.biomespy` | 4 | `Biomespy`、`MixinPlugin`、`Constants` |
| `com.moepus.biomespy.compat.terrablender` | 3 | `TerrablenderCompat`、`IParameterListExtendedInfo`、`TerraBiome` |
| `com.moepus.biomespy.mixin.compat.terrablender` | 2 | `LevelUtilsMixin`、`ParameterListMixin` |
| `com.moepus.biomespy.platform` | 2 | `NeoForgePlatformHelper`、`Services` |
| `com.moepus.biomespy.platform.services` | 1 | `IPlatformHelper` |
| `com.moepus.biomespy.structure` | 1 | `StructureChecker` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `StructureManagerAccessor` | interface | — | Mixin | 0 | 13 | [StructureManagerAccessor.java](com/moepus/biomespy/mixin/StructureManagerAccessor.java) |
| `Biomespy` | class | — | Mod | 0 | 12 | [Biomespy.java](com/moepus/biomespy/Biomespy.java) |
| `LevelUtilsMixin` | class | — | Mixin | 1 | 43 | [LevelUtilsMixin.java](com/moepus/biomespy/mixin/compat/terrablender/LevelUtilsMixin.java) |
| `StructureCheckMixin` | class | — | Mixin | 0 | 38 | [StructureCheckMixin.java](com/moepus/biomespy/mixin/StructureCheckMixin.java) |
| `MultiNoiseBiomeSourceMixin` | class | BiomeSource | Mixin | 1 | 24 | [MultiNoiseBiomeSourceMixin.java](com/moepus/biomespy/mixin/MultiNoiseBiomeSourceMixin.java) |
| `MultiNoiseBiomeSourceAccessor` | interface | — | Mixin | 0 | 15 | [MultiNoiseBiomeSourceAccessor.java](com/moepus/biomespy/mixin/MultiNoiseBiomeSourceAccessor.java) |
| `ChunkGeneratorMixin` | class | — | Mixin | 1 | 88 | [ChunkGeneratorMixin.java](com/moepus/biomespy/mixin/ChunkGeneratorMixin.java) |
| `StructureCheckAccessor` | interface | — | Mixin | 0 | 51 | [StructureCheckAccessor.java](com/moepus/biomespy/mixin/StructureCheckAccessor.java) |
| `ParameterListMixin` | class | IParameterListExtendedInfo | Mixin | 1 | 108 | [ParameterListMixin.java](com/moepus/biomespy/mixin/compat/terrablender/ParameterListMixin.java) |
| `TerrablenderCompat` | class | — | — | 0 | 12 | [TerrablenderCompat.java](com/moepus/biomespy/compat/terrablender/TerrablenderCompat.java) |
| `LazyBiomeNoiseChecker` | class | — | — | 10 | 168 | [LazyBiomeNoiseChecker.java](com/moepus/biomespy/biome/LazyBiomeNoiseChecker.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 8 | 39 | [MixinPlugin.java](com/moepus/biomespy/MixinPlugin.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 4 | 29 | [NeoForgePlatformHelper.java](com/moepus/biomespy/platform/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 33 | [IPlatformHelper.java](com/moepus/biomespy/platform/services/IPlatformHelper.java) |
| `Constants` | class | — | — | 0 | 11 | [Constants.java](com/moepus/biomespy/Constants.java) |
| `BiomeSpyCommon` | class | — | — | 1 | 7 | [BiomeSpyCommon.java](com/moepus/biomespy/BiomeSpyCommon.java) |
| `StructureChecker` | class | — | — | 3 | 110 | [StructureChecker.java](com/moepus/biomespy/structure/StructureChecker.java) |
| `IParameterListExtendedInfo` | interface | — | — | 0 | 21 | [IParameterListExtendedInfo.java](com/moepus/biomespy/compat/terrablender/IParameterListExtendedInfo.java) |
| `TerraBiome` | class | — | — | 2 | 20 | [TerraBiome.java](com/moepus/biomespy/compat/terrablender/TerraBiome.java) |
| `BiomeFinder` | class | — | — | 1 | 70 | [BiomeFinder.java](com/moepus/biomespy/biome/BiomeFinder.java) |
| `BiomeEnvelopeSelector` | class | — | — | 3 | 76 | [BiomeEnvelopeSelector.java](com/moepus/biomespy/biome/BiomeEnvelopeSelector.java) |
| `BiomeNoiseCheckState` | class | — | — | 3 | 27 | [BiomeNoiseCheckState.java](com/moepus/biomespy/biome/BiomeNoiseCheckState.java) |
| `BiomeEnvelope` | class | — | — | 3 | 81 | [BiomeEnvelope.java](com/moepus/biomespy/biome/BiomeEnvelope.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/moepus/biomespy/platform/Services.java) |

## Mixin（8 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StructureManagerAccessor` | `StructureManagerAccessor` | [StructureManagerAccessor.java](com/moepus/biomespy/mixin/StructureManagerAccessor.java) |
| `LevelUtilsMixin` | `LevelUtils` | [LevelUtilsMixin.java](com/moepus/biomespy/mixin/compat/terrablender/LevelUtilsMixin.java) |
| `StructureCheckMixin` | `StructureCheck` | [StructureCheckMixin.java](com/moepus/biomespy/mixin/StructureCheckMixin.java) |
| `MultiNoiseBiomeSourceMixin` | `MultiNoiseBiomeSource` | [MultiNoiseBiomeSourceMixin.java](com/moepus/biomespy/mixin/MultiNoiseBiomeSourceMixin.java) |
| `MultiNoiseBiomeSourceAccessor` | `MultiNoiseBiomeSourceAccessor` | [MultiNoiseBiomeSourceAccessor.java](com/moepus/biomespy/mixin/MultiNoiseBiomeSourceAccessor.java) |
| `ChunkGeneratorMixin` | `ChunkGenerator` | [ChunkGeneratorMixin.java](com/moepus/biomespy/mixin/ChunkGeneratorMixin.java) |
| `StructureCheckAccessor` | `StructureCheckAccessor` | [StructureCheckAccessor.java](com/moepus/biomespy/mixin/StructureCheckAccessor.java) |
| `ParameterListMixin` | `ParameterList` | [ParameterListMixin.java](com/moepus/biomespy/mixin/compat/terrablender/ParameterListMixin.java) |

## 全部类（24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `StructureManagerAccessor` | interface | — | Mixin | 0 | 13 | [StructureManagerAccessor.java](com/moepus/biomespy/mixin/StructureManagerAccessor.java) |
| `Biomespy` | class | — | Mod | 0 | 12 | [Biomespy.java](com/moepus/biomespy/Biomespy.java) |
| `LevelUtilsMixin` | class | — | Mixin | 1 | 43 | [LevelUtilsMixin.java](com/moepus/biomespy/mixin/compat/terrablender/LevelUtilsMixin.java) |
| `StructureCheckMixin` | class | — | Mixin | 0 | 38 | [StructureCheckMixin.java](com/moepus/biomespy/mixin/StructureCheckMixin.java) |
| `MultiNoiseBiomeSourceMixin` | class | BiomeSource | Mixin | 1 | 24 | [MultiNoiseBiomeSourceMixin.java](com/moepus/biomespy/mixin/MultiNoiseBiomeSourceMixin.java) |
| `MultiNoiseBiomeSourceAccessor` | interface | — | Mixin | 0 | 15 | [MultiNoiseBiomeSourceAccessor.java](com/moepus/biomespy/mixin/MultiNoiseBiomeSourceAccessor.java) |
| `ChunkGeneratorMixin` | class | — | Mixin | 1 | 88 | [ChunkGeneratorMixin.java](com/moepus/biomespy/mixin/ChunkGeneratorMixin.java) |
| `StructureCheckAccessor` | interface | — | Mixin | 0 | 51 | [StructureCheckAccessor.java](com/moepus/biomespy/mixin/StructureCheckAccessor.java) |
| `ParameterListMixin` | class | IParameterListExtendedInfo | Mixin | 1 | 108 | [ParameterListMixin.java](com/moepus/biomespy/mixin/compat/terrablender/ParameterListMixin.java) |
| `TerrablenderCompat` | class | — | — | 0 | 12 | [TerrablenderCompat.java](com/moepus/biomespy/compat/terrablender/TerrablenderCompat.java) |
| `LazyBiomeNoiseChecker` | class | — | — | 10 | 168 | [LazyBiomeNoiseChecker.java](com/moepus/biomespy/biome/LazyBiomeNoiseChecker.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 8 | 39 | [MixinPlugin.java](com/moepus/biomespy/MixinPlugin.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 4 | 29 | [NeoForgePlatformHelper.java](com/moepus/biomespy/platform/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 33 | [IPlatformHelper.java](com/moepus/biomespy/platform/services/IPlatformHelper.java) |
| `Constants` | class | — | — | 0 | 11 | [Constants.java](com/moepus/biomespy/Constants.java) |
| `BiomeSpyCommon` | class | — | — | 1 | 7 | [BiomeSpyCommon.java](com/moepus/biomespy/BiomeSpyCommon.java) |
| `StructureChecker` | class | — | — | 3 | 110 | [StructureChecker.java](com/moepus/biomespy/structure/StructureChecker.java) |
| `IParameterListExtendedInfo` | interface | — | — | 0 | 21 | [IParameterListExtendedInfo.java](com/moepus/biomespy/compat/terrablender/IParameterListExtendedInfo.java) |
| `TerraBiome` | class | — | — | 2 | 20 | [TerraBiome.java](com/moepus/biomespy/compat/terrablender/TerraBiome.java) |
| `BiomeFinder` | class | — | — | 1 | 70 | [BiomeFinder.java](com/moepus/biomespy/biome/BiomeFinder.java) |
| `BiomeEnvelopeSelector` | class | — | — | 3 | 76 | [BiomeEnvelopeSelector.java](com/moepus/biomespy/biome/BiomeEnvelopeSelector.java) |
| `BiomeNoiseCheckState` | class | — | — | 3 | 27 | [BiomeNoiseCheckState.java](com/moepus/biomespy/biome/BiomeNoiseCheckState.java) |
| `BiomeEnvelope` | class | — | — | 3 | 81 | [BiomeEnvelope.java](com/moepus/biomespy/biome/BiomeEnvelope.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/moepus/biomespy/platform/Services.java) |
