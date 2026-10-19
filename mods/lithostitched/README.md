# Lithostitched（`lithostitched`）

> jar：`[前置]lithostitched.jar` ｜ 版本：`1.8.0` ｜ 作者：Apollo, UHQ_Games
> 规模：**303 个类 / 85 个包 / 19460 行** ｜ 事件处理类 0 ｜ Mixin 59 ｜ 注册/管理类 9

**官方描述**：A stack of new biomes spread across the Overworld and Nether!

**依赖**：`minecraft`、`apollib`、`lithostitched`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.worldgen.lithostitched.mixin.common` | 28 | `MappedRegistryAccessor`、`TreeConfigurationAccessor`、`RandomStateAccessor` |
| `dev.worldgen.lithostitched.worldgen.modifier` | 15 | `WrapDensityFunctionModifier`、`SetPoolElementProcessorsModifier`、`NoOpModifier` |
| `dev.worldgen.lithostitched.impl.worldgen.densityfunction` | 13 | `CellularDensityFunction`、`SelectDensityFunction`、`FastNoiseDensityFunction` |
| `dev.worldgen.lithostitched.worldgen.placementcondition` | 11 | `SampleNoiseRouterPlacementCondition`、`NotPlacementCondition`、`MultipleOfPlacementCondition` |
| `dev.worldgen.lithostitched.impl.worldgen.modifier` | 10 | `ModifierManager`、`BiomeEffectsBuilderImpl`、`AddFeaturesModifier` |
| `dev.worldgen.lithostitched.worldgen.feature.config` | 10 | `CompositeConfig`、`WeightedSelectorConfig`、`OreConfig` |
| `dev.worldgen.lithostitched.worldgen.feature` | 10 | `LargeDripstoneFeature`、`WeightedSelectorFeature`、`DungeonFeature` |
| `dev.worldgen.lithostitched.mixin.common.processor` | 9 | `OceanRuinPieceMixin`、`NetherFossilPieceMixin`、`EndCityPieceMixin` |
| `dev.worldgen.lithostitched.duck` | 8 | `RegistryHolder`、`ContextBiomeAccessor`、`ContextAccessor` |
| `dev.worldgen.lithostitched.impl.worldgen.processor` | 8 | `SetBlockProcessor`、`BlockSwapStructureProcessor`、`UnboundReferenceProcessor` |
| `dev.worldgen.lithostitched.worldgen.processor.condition` | 8 | `MatchingBlocks`、`RandomChance`、`True` |
| `dev.worldgen.lithostitched.impl.predicate` | 8 | `AnyOfPredicate`、`LoaderPredicate`、`ModLoadedPredicate` |
| `dev.worldgen.lithostitched.worldgen.structure` | 7 | `AlternateJigsawGenerator`、`DelegatingConfig`、`AlternateJigsawConfig` |
| `dev.worldgen.lithostitched.worldgen.blockpredicate` | 7 | `BlockStatePredicate`、`GridPredicate`、`MultipleOfPredicate` |
| `dev.worldgen.lithostitched.api.util` | 5 | `WeightedList`、`StatePropertiesPredicate`、`Weighted` |
| `dev.worldgen.lithostitched.worldgen.poolelement` | 5 | `LithostitchedFeatureConfig`、`DelegatingConfig`、`DelegatingPoolElement` |
| `dev.worldgen.lithostitched.api.worldgen.util` | 5 | `WeightedSpawnerData`、`BiomeClimate`、`DensityFunctionWrapper` |
| `dev.worldgen.lithostitched.impl.worldgen.biomeinjector` | 5 | `DispatchAlternateLayout`、`ReplacePartially`、`AddPoints` |
| `dev.worldgen.lithostitched.impl.worldgen.surface.condition` | 5 | `AllOfCondition`、`SlopeCondition`、`SampleDensityCondition` |
| `dev.worldgen.lithostitched.mixin.common.template.mansion` | 4 | `PiecePlacerMixin`、`WoodlandMansionStructureMixin`、`WoodlandMansionPieceMixin` |
| `dev.worldgen.lithostitched.impl.worldgen.biomeinjector.internal` | 4 | `BiomeInjectorManager`、`InjectorBiomeSource`、`ParameterBuilderImpl` |
| `dev.worldgen.lithostitched.impl.worldgen.densityfunction.marker` | 4 | `MergedDensityFunction`、`WrappedMarkerDensityFunction`、`MarkerFunction` |
| `dev.worldgen.lithostitched.api.tag` | 4 | `LithostitchedProcessorListTags`、`LithostitchedBiomeSourceTags`、`LithostitchedTemplatePoolTags` |
| `dev.worldgen.lithostitched.platform.neoforge` | 3 | `LithostitchedNeoforgeClient`、`LithostitchedNeoforge`、`LithostitchedRegistrations` |
| `dev.worldgen.lithostitched.api.worldgen.densityfunction.fastnoise` | 3 | `FNL`、`FastNoiseConfig`、`LithostitchedFastNoiseConfigs` |
| `dev.worldgen.lithostitched.mixin.common.template.mansion.floor` | 3 | `SecondFloorMixin`、`FirstFloorMixin`、`ThirdFloorMixin` |
| `dev.worldgen.lithostitched.mixin.common.mnbs` | 3 | `ParameterListAccessor`、`MNBSMixin`、`MNBSPLMixin` |
| `dev.worldgen.lithostitched.mixin.common.template` | 3 | `RuinedPortalStructureMixin`、`ShipwreckStructureMixin`、`NetherFossilStructureMixin` |
| `dev.worldgen.lithostitched.api.event` | 3 | `AddWorldgenModifiersEvent`、`AddRegionsEvent`、`AddBiomeInjectorsEvent` |
| `dev.worldgen.lithostitched.api.worldgen.processor.enums` | 3 | `BlockType`、`RandomMode`、`ProcessorPosition` |
| `dev.worldgen.lithostitched.worldgen.placementmodifier` | 3 | `OffsetPlacement`、`NoiseSlopePlacement`、`ConditionPlacement` |
| `dev.worldgen.lithostitched.impl.worldgen.fastnoise` | 3 | `CellularNoiseType`、`SimplexNoiseType`、`PerlinNoiseType` |
| `dev.worldgen.lithostitched.impl.worldgen.bandlands.band` | 3 | `WrappedBand`、`RepeatingBand`、`BaseBand` |
| `dev.worldgen.lithostitched.impl.worldgen.surface.rule` | 3 | `TransientMergedRule`、`ReferenceRule`、`BandlandsRule` |
| `dev.worldgen.lithostitched.api.worldgen.processor` | 3 | `LithostitchedProcessorLists`、`RandomSettings`、`LithostitchedProcessors` |
| `dev.worldgen.lithostitched.api.worldgen.structure` | 3 | `MaxDistanceFromCenter`、`LithostitchedStructures`、`SurfaceSnap` |
| `dev.worldgen.lithostitched.api.worldgen.modifier` | 2 | `WorldgenModifier`、`BiomeEffectsBuilder` |
| `dev.worldgen.lithostitched.impl.worldgen.biomeinjector.region` | 2 | `RegionManager`、`Region` |
| `dev.worldgen.lithostitched.mixin.common.bandlands` | 2 | `NoiseBasedChunkGeneratorMixin`、`SurfaceSystemMixin` |
| `dev.worldgen.lithostitched.registry` | 2 | `LithostitchedRegistryKeys`、`LithostitchedBuiltInRegistries` |
| … | | 其余 45 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RegistryDataLoaderMixin` | class | — | Mixin | 1 | 49 | [RegistryDataLoaderMixin.java](dev/worldgen/lithostitched/mixin/common/predicate/RegistryDataLoaderMixin.java) |
| `MappedRegistryAccessor` | interface | — | Mixin | 0 | 21 | [MappedRegistryAccessor.java](dev/worldgen/lithostitched/mixin/common/MappedRegistryAccessor.java) |
| `LithostitchedNeoforgeClient` | class | — | Mod | 0 | 12 | [LithostitchedNeoforgeClient.java](dev/worldgen/lithostitched/platform/neoforge/LithostitchedNeoforgeClient.java) |
| `FNL` | class | StringRepresentable | — | 72 | 3916 | [FNL.java](dev/worldgen/lithostitched/api/worldgen/densityfunction/fastnoise/FNL.java) |
| `TreeConfigurationAccessor` | interface | — | Mixin | 0 | 16 | [TreeConfigurationAccessor.java](dev/worldgen/lithostitched/mixin/common/TreeConfigurationAccessor.java) |
| `LithostitchedNeoforge` | class | — | Mod | 1 | 23 | [LithostitchedNeoforge.java](dev/worldgen/lithostitched/platform/neoforge/LithostitchedNeoforge.java) |
| `PiecePlacerMixin` | class | RegistryHolder | Mixin | 5 | 58 | [PiecePlacerMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/PiecePlacerMixin.java) |
| `SecondFloorMixin` | class | MansionRoomDuck | Mixin | 7 | 68 | [SecondFloorMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/floor/SecondFloorMixin.java) |
| `FirstFloorMixin` | class | MansionRoomDuck | Mixin | 7 | 68 | [FirstFloorMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/floor/FirstFloorMixin.java) |
| `WorldgenModifier` | interface | — | — | 44 | 383 | [WorldgenModifier.java](dev/worldgen/lithostitched/api/worldgen/modifier/WorldgenModifier.java) |
| `RegistryHolder` | interface | — | — | 0 | 10 | [RegistryHolder.java](dev/worldgen/lithostitched/duck/RegistryHolder.java) |
| `RegionManager` | class | — | — | 6 | 84 | [RegionManager.java](dev/worldgen/lithostitched/impl/worldgen/biomeinjector/region/RegionManager.java) |
| `DedicatedServerMixin` | class | — | Mixin | 1 | 23 | [DedicatedServerMixin.java](dev/worldgen/lithostitched/mixin/server/DedicatedServerMixin.java) |
| `RandomStateAccessor` | interface | — | Mixin | 0 | 15 | [RandomStateAccessor.java](dev/worldgen/lithostitched/mixin/common/RandomStateAccessor.java) |
| `MultiNoiseBiomeSourceAccessor` | interface | — | Mixin | 0 | 20 | [MultiNoiseBiomeSourceAccessor.java](dev/worldgen/lithostitched/mixin/common/MultiNoiseBiomeSourceAccessor.java) |
| `RandomStateMixin` | class | SeedAccessor | Mixin | 2 | 29 | [RandomStateMixin.java](dev/worldgen/lithostitched/mixin/common/RandomStateMixin.java) |
| `GameTestServerMixin` | class | — | Mixin | 1 | 19 | [GameTestServerMixin.java](dev/worldgen/lithostitched/mixin/common/GameTestServerMixin.java) |
| `NoiseBasedChunkGeneratorMixin` | class | — | Mixin | 1 | 26 | [NoiseBasedChunkGeneratorMixin.java](dev/worldgen/lithostitched/mixin/common/NoiseBasedChunkGeneratorMixin.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 30 | [StructureTemplatePoolAccessor.java](dev/worldgen/lithostitched/mixin/common/StructureTemplatePoolAccessor.java) |
| `StructureTemplatePoolMixin` | class | StructurePoolAccess | Mixin | 2 | 32 | [StructureTemplatePoolMixin.java](dev/worldgen/lithostitched/mixin/common/StructureTemplatePoolMixin.java) |
| `StructureSetAccessor` | interface | — | Mixin | 0 | 16 | [StructureSetAccessor.java](dev/worldgen/lithostitched/mixin/common/StructureSetAccessor.java) |
| `BiomeGenerationSettingsAccessor` | interface | — | Mixin | 0 | 26 | [BiomeGenerationSettingsAccessor.java](dev/worldgen/lithostitched/mixin/common/BiomeGenerationSettingsAccessor.java) |
| `NoiseBasedChunkGeneratorAccessor` | interface | — | Mixin | 0 | 16 | [NoiseBasedChunkGeneratorAccessor.java](dev/worldgen/lithostitched/mixin/common/NoiseBasedChunkGeneratorAccessor.java) |
| `StructureProcessorListAccessor` | interface | — | Mixin | 0 | 16 | [StructureProcessorListAccessor.java](dev/worldgen/lithostitched/mixin/common/StructureProcessorListAccessor.java) |
| `PoolAliasLookupMixin` | interface | — | Mixin | 0 | 20 | [PoolAliasLookupMixin.java](dev/worldgen/lithostitched/mixin/common/PoolAliasLookupMixin.java) |
| `NoiseChunkAccessor` | interface | — | Mixin | 0 | 13 | [NoiseChunkAccessor.java](dev/worldgen/lithostitched/mixin/common/NoiseChunkAccessor.java) |
| `SinglePoolElementAccessor` | interface | — | Mixin | 0 | 27 | [SinglePoolElementAccessor.java](dev/worldgen/lithostitched/mixin/common/SinglePoolElementAccessor.java) |
| `ChunkGeneratorAccessor` | interface | — | Mixin | 0 | 32 | [ChunkGeneratorAccessor.java](dev/worldgen/lithostitched/mixin/common/ChunkGeneratorAccessor.java) |
| `SurfaceRulesContextMixin` | class | ContextAccessor | Mixin | 0 | 87 | [SurfaceRulesContextMixin.java](dev/worldgen/lithostitched/mixin/common/SurfaceRulesContextMixin.java) |
| `NoiseGeneratorSettingsAccessor` | interface | — | Mixin | 0 | 23 | [NoiseGeneratorSettingsAccessor.java](dev/worldgen/lithostitched/mixin/common/NoiseGeneratorSettingsAccessor.java) |
| `MobSpawnSettingsAccessor` | interface | — | Mixin | 0 | 27 | [MobSpawnSettingsAccessor.java](dev/worldgen/lithostitched/mixin/common/MobSpawnSettingsAccessor.java) |
| `HolderReferenceAccessor` | interface | — | Mixin | 0 | 12 | [HolderReferenceAccessor.java](dev/worldgen/lithostitched/mixin/common/HolderReferenceAccessor.java) |
| `ChunkMapMixin` | class | — | Mixin | 1 | 73 | [ChunkMapMixin.java](dev/worldgen/lithostitched/mixin/common/ChunkMapMixin.java) |
| `BeardifierMixin` | class | — | Mixin | 1 | 37 | [BeardifierMixin.java](dev/worldgen/lithostitched/mixin/common/BeardifierMixin.java) |
| `PlacedFeatureAccessor` | interface | — | Mixin | 0 | 16 | [PlacedFeatureAccessor.java](dev/worldgen/lithostitched/mixin/common/PlacedFeatureAccessor.java) |
| `JigsawStructureMixin` | class | — | Mixin | 1 | 78 | [JigsawStructureMixin.java](dev/worldgen/lithostitched/mixin/common/JigsawStructureMixin.java) |
| `JigsawStructureAccessor` | interface | — | Mixin | 0 | 19 | [JigsawStructureAccessor.java](dev/worldgen/lithostitched/mixin/common/JigsawStructureAccessor.java) |
| `BiomeAccessor` | interface | — | Mixin | 0 | 37 | [BiomeAccessor.java](dev/worldgen/lithostitched/mixin/common/BiomeAccessor.java) |
| `BiomeSourceInvoker` | interface | — | Mixin | 0 | 13 | [BiomeSourceInvoker.java](dev/worldgen/lithostitched/mixin/common/BiomeSourceInvoker.java) |
| `DensityFunctionsMapAllMixin` | class | — | Mixin | 0 | 38 | [DensityFunctionsMapAllMixin.java](dev/worldgen/lithostitched/mixin/common/density/DensityFunctionsMapAllMixin.java) |
| `OceanRuinPieceMixin` | class | — | Mixin | 1 | 20 | [OceanRuinPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/OceanRuinPieceMixin.java) |
| `NetherFossilPieceMixin` | class | — | Mixin | 1 | 18 | [NetherFossilPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/NetherFossilPieceMixin.java) |
| `EndCityPieceMixin` | class | — | Mixin | 1 | 18 | [EndCityPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/EndCityPieceMixin.java) |
| `WoodlandMansionPieceMixin` | class | — | Mixin | 1 | 18 | [WoodlandMansionPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/WoodlandMansionPieceMixin.java) |
| `IglooPieceMixin` | class | — | Mixin | 1 | 18 | [IglooPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/IglooPieceMixin.java) |
| `StructurePieceMixin` | class | ReferencePosDuck | Mixin | 2 | 86 | [StructurePieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/StructurePieceMixin.java) |
| `ShipwreckPieceMixin` | class | — | Mixin | 1 | 18 | [ShipwreckPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/ShipwreckPieceMixin.java) |
| `StructureStartMixin` | class | — | Mixin | 1 | 42 | [StructureStartMixin.java](dev/worldgen/lithostitched/mixin/common/processor/StructureStartMixin.java) |
| `RuinedPortalPieceMixin` | class | — | Mixin | 1 | 21 | [RuinedPortalPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/RuinedPortalPieceMixin.java) |
| `ServerLifecycleHooksMixin` | class | — | Mixin | 0 | 33 | [ServerLifecycleHooksMixin.java](dev/worldgen/lithostitched/mixin/common/neoforge/ServerLifecycleHooksMixin.java) |
| `NoiseBasedChunkGeneratorMixin` | class | — | Mixin | 2 | 57 | [NoiseBasedChunkGeneratorMixin.java](dev/worldgen/lithostitched/mixin/common/compat/terrablender/NoiseBasedChunkGeneratorMixin.java) |
| `NoiseBasedChunkGeneratorMixin` | class | — | Mixin | 1 | 33 | [NoiseBasedChunkGeneratorMixin.java](dev/worldgen/lithostitched/mixin/common/bandlands/NoiseBasedChunkGeneratorMixin.java) |
| `SurfaceSystemMixin` | class | SurfaceSystemAccessor | Mixin | 2 | 30 | [SurfaceSystemMixin.java](dev/worldgen/lithostitched/mixin/common/bandlands/SurfaceSystemMixin.java) |
| `ParameterListAccessor` | interface | — | Mixin | 0 | 25 | [ParameterListAccessor.java](dev/worldgen/lithostitched/mixin/common/mnbs/ParameterListAccessor.java) |
| `MNBSMixin` | class | MNBSDuck | Mixin | 1 | 82 | [MNBSMixin.java](dev/worldgen/lithostitched/mixin/common/mnbs/MNBSMixin.java) |
| `MNBSPLMixin` | class | MNBSPLDuck | Mixin | 1 | 74 | [MNBSPLMixin.java](dev/worldgen/lithostitched/mixin/common/mnbs/MNBSPLMixin.java) |
| `RuinedPortalStructureMixin` | class | — | Mixin | 2 | 42 | [RuinedPortalStructureMixin.java](dev/worldgen/lithostitched/mixin/common/template/RuinedPortalStructureMixin.java) |
| `ShipwreckStructureMixin` | class | — | Mixin | 1 | 49 | [ShipwreckStructureMixin.java](dev/worldgen/lithostitched/mixin/common/template/ShipwreckStructureMixin.java) |
| `NetherFossilStructureMixin` | class | — | Mixin | 1 | 32 | [NetherFossilStructureMixin.java](dev/worldgen/lithostitched/mixin/common/template/NetherFossilStructureMixin.java) |
| `WoodlandMansionStructureMixin` | class | — | Mixin | 1 | 35 | [WoodlandMansionStructureMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/WoodlandMansionStructureMixin.java) |

## Mixin（59 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `RegistryDataLoaderMixin` | `RegistryDataLoader` | [RegistryDataLoaderMixin.java](dev/worldgen/lithostitched/mixin/common/predicate/RegistryDataLoaderMixin.java) |
| `MappedRegistryAccessor` | `MappedRegistryAccessor` | [MappedRegistryAccessor.java](dev/worldgen/lithostitched/mixin/common/MappedRegistryAccessor.java) |
| `TreeConfigurationAccessor` | `TreeConfigurationAccessor` | [TreeConfigurationAccessor.java](dev/worldgen/lithostitched/mixin/common/TreeConfigurationAccessor.java) |
| `PiecePlacerMixin` | `PiecePlacer` | [PiecePlacerMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/PiecePlacerMixin.java) |
| `SecondFloorMixin` | `SecondFloor` | [SecondFloorMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/floor/SecondFloorMixin.java) |
| `FirstFloorMixin` | `FirstFloor` | [FirstFloorMixin.java](dev/worldgen/lithostitched/mixin/common/template/mansion/floor/FirstFloorMixin.java) |
| `DedicatedServerMixin` | `DedicatedServer` | [DedicatedServerMixin.java](dev/worldgen/lithostitched/mixin/server/DedicatedServerMixin.java) |
| `RandomStateAccessor` | `RandomStateAccessor` | [RandomStateAccessor.java](dev/worldgen/lithostitched/mixin/common/RandomStateAccessor.java) |
| `MultiNoiseBiomeSourceAccessor` | `MultiNoiseBiomeSourceAccessor` | [MultiNoiseBiomeSourceAccessor.java](dev/worldgen/lithostitched/mixin/common/MultiNoiseBiomeSourceAccessor.java) |
| `RandomStateMixin` | `RandomState` | [RandomStateMixin.java](dev/worldgen/lithostitched/mixin/common/RandomStateMixin.java) |
| `GameTestServerMixin` | `GameTestServer` | [GameTestServerMixin.java](dev/worldgen/lithostitched/mixin/common/GameTestServerMixin.java) |
| `NoiseBasedChunkGeneratorMixin` | `NoiseBasedChunkGenerator` | [NoiseBasedChunkGeneratorMixin.java](dev/worldgen/lithostitched/mixin/common/NoiseBasedChunkGeneratorMixin.java) |
| `StructureTemplatePoolAccessor` | `StructureTemplatePoolAccessor` | [StructureTemplatePoolAccessor.java](dev/worldgen/lithostitched/mixin/common/StructureTemplatePoolAccessor.java) |
| `StructureTemplatePoolMixin` | `StructureTemplatePool` | [StructureTemplatePoolMixin.java](dev/worldgen/lithostitched/mixin/common/StructureTemplatePoolMixin.java) |
| `StructureSetAccessor` | `StructureSetAccessor` | [StructureSetAccessor.java](dev/worldgen/lithostitched/mixin/common/StructureSetAccessor.java) |
| `BiomeGenerationSettingsAccessor` | `BiomeGenerationSettingsAccessor` | [BiomeGenerationSettingsAccessor.java](dev/worldgen/lithostitched/mixin/common/BiomeGenerationSettingsAccessor.java) |
| `NoiseBasedChunkGeneratorAccessor` | `NoiseBasedChunkGeneratorAccessor` | [NoiseBasedChunkGeneratorAccessor.java](dev/worldgen/lithostitched/mixin/common/NoiseBasedChunkGeneratorAccessor.java) |
| `StructureProcessorListAccessor` | `StructureProcessorListAccessor` | [StructureProcessorListAccessor.java](dev/worldgen/lithostitched/mixin/common/StructureProcessorListAccessor.java) |
| `PoolAliasLookupMixin` | `PoolAliasLookup` | [PoolAliasLookupMixin.java](dev/worldgen/lithostitched/mixin/common/PoolAliasLookupMixin.java) |
| `NoiseChunkAccessor` | `NoiseChunkAccessor` | [NoiseChunkAccessor.java](dev/worldgen/lithostitched/mixin/common/NoiseChunkAccessor.java) |
| `SinglePoolElementAccessor` | `SinglePoolElementAccessor` | [SinglePoolElementAccessor.java](dev/worldgen/lithostitched/mixin/common/SinglePoolElementAccessor.java) |
| `ChunkGeneratorAccessor` | `ChunkGeneratorAccessor` | [ChunkGeneratorAccessor.java](dev/worldgen/lithostitched/mixin/common/ChunkGeneratorAccessor.java) |
| `SurfaceRulesContextMixin` | `SurfaceRulesContext` | [SurfaceRulesContextMixin.java](dev/worldgen/lithostitched/mixin/common/SurfaceRulesContextMixin.java) |
| `NoiseGeneratorSettingsAccessor` | `NoiseGeneratorSettingsAccessor` | [NoiseGeneratorSettingsAccessor.java](dev/worldgen/lithostitched/mixin/common/NoiseGeneratorSettingsAccessor.java) |
| `MobSpawnSettingsAccessor` | `MobSpawnSettingsAccessor` | [MobSpawnSettingsAccessor.java](dev/worldgen/lithostitched/mixin/common/MobSpawnSettingsAccessor.java) |
| `HolderReferenceAccessor` | `HolderReferenceAccessor` | [HolderReferenceAccessor.java](dev/worldgen/lithostitched/mixin/common/HolderReferenceAccessor.java) |
| `ChunkMapMixin` | `ChunkMap` | [ChunkMapMixin.java](dev/worldgen/lithostitched/mixin/common/ChunkMapMixin.java) |
| `BeardifierMixin` | `Beardifier` | [BeardifierMixin.java](dev/worldgen/lithostitched/mixin/common/BeardifierMixin.java) |
| `PlacedFeatureAccessor` | `PlacedFeatureAccessor` | [PlacedFeatureAccessor.java](dev/worldgen/lithostitched/mixin/common/PlacedFeatureAccessor.java) |
| `JigsawStructureMixin` | `JigsawStructure` | [JigsawStructureMixin.java](dev/worldgen/lithostitched/mixin/common/JigsawStructureMixin.java) |
| `JigsawStructureAccessor` | `JigsawStructureAccessor` | [JigsawStructureAccessor.java](dev/worldgen/lithostitched/mixin/common/JigsawStructureAccessor.java) |
| `BiomeAccessor` | `BiomeAccessor` | [BiomeAccessor.java](dev/worldgen/lithostitched/mixin/common/BiomeAccessor.java) |
| `BiomeSourceInvoker` | `BiomeSourceInvoker` | [BiomeSourceInvoker.java](dev/worldgen/lithostitched/mixin/common/BiomeSourceInvoker.java) |
| `DensityFunctionsMapAllMixin` | `DensityFunctionsMapAll` | [DensityFunctionsMapAllMixin.java](dev/worldgen/lithostitched/mixin/common/density/DensityFunctionsMapAllMixin.java) |
| `OceanRuinPieceMixin` | `OceanRuinPiece` | [OceanRuinPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/OceanRuinPieceMixin.java) |
| `NetherFossilPieceMixin` | `NetherFossilPiece` | [NetherFossilPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/NetherFossilPieceMixin.java) |
| `EndCityPieceMixin` | `EndCityPiece` | [EndCityPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/EndCityPieceMixin.java) |
| `WoodlandMansionPieceMixin` | `WoodlandMansionPiece` | [WoodlandMansionPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/WoodlandMansionPieceMixin.java) |
| `IglooPieceMixin` | `IglooPiece` | [IglooPieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/IglooPieceMixin.java) |
| `StructurePieceMixin` | `StructurePiece` | [StructurePieceMixin.java](dev/worldgen/lithostitched/mixin/common/processor/StructurePieceMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TreeConfigurationAccessor` | Mixin | [TreeConfigurationAccessor.java](dev/worldgen/lithostitched/mixin/common/TreeConfigurationAccessor.java) |
| `FastNoiseConfig` | — | [FastNoiseConfig.java](dev/worldgen/lithostitched/api/worldgen/densityfunction/fastnoise/FastNoiseConfig.java) |
| `DelegatingConfig` | — | [DelegatingConfig.java](dev/worldgen/lithostitched/worldgen/structure/DelegatingConfig.java) |
| `AlternateJigsawConfig` | — | [AlternateJigsawConfig.java](dev/worldgen/lithostitched/worldgen/structure/AlternateJigsawConfig.java) |
| `CompositeConfig` | — | [CompositeConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/CompositeConfig.java) |
| `WeightedSelectorConfig` | — | [WeightedSelectorConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/WeightedSelectorConfig.java) |
| `OreConfig` | — | [OreConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/OreConfig.java) |
| `LargeDripstoneConfig` | — | [LargeDripstoneConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/LargeDripstoneConfig.java) |
| `VinesConfig` | — | [VinesConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/VinesConfig.java) |
| `SelectConfig` | — | [SelectConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/SelectConfig.java) |
| `StructureTemplateConfig` | — | [StructureTemplateConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/StructureTemplateConfig.java) |
| `DungeonConfig` | — | [DungeonConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/DungeonConfig.java) |
| `SimplePlacedConfig` | — | [SimplePlacedConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/SimplePlacedConfig.java) |
| `WellConfig` | — | [WellConfig.java](dev/worldgen/lithostitched/worldgen/feature/config/WellConfig.java) |
| `LithostitchedFeatureConfig` | — | [LithostitchedFeatureConfig.java](dev/worldgen/lithostitched/worldgen/poolelement/LithostitchedFeatureConfig.java) |
| `DelegatingConfig` | — | [DelegatingConfig.java](dev/worldgen/lithostitched/worldgen/poolelement/DelegatingConfig.java) |
| `ConfigState` | — | [ConfigState.java](dev/worldgen/lithostitched/config/ConfigState.java) |
| `LithostitchedFastNoiseConfigs` | — | [LithostitchedFastNoiseConfigs.java](dev/worldgen/lithostitched/api/worldgen/densityfunction/fastnoise/LithostitchedFastNoiseConfigs.java) |

## 按包浏览

- `dev.worldgen.lithostitched`（1 类）
- `dev.worldgen.lithostitched.api.event`（3 类）
- `dev.worldgen.lithostitched.api.predicate`（1 类）
- `dev.worldgen.lithostitched.api.registry`（2 类）
- `dev.worldgen.lithostitched.api.tag`（4 类）
- `dev.worldgen.lithostitched.api.util`（5 类）
- `dev.worldgen.lithostitched.api.worldgen.bandlands`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.biomeinjector`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.blockentitymodifier`（1 类）
- `dev.worldgen.lithostitched.api.worldgen.blockpredicate`（1 类）
- `dev.worldgen.lithostitched.api.worldgen.densityfunction`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.densityfunction.fastnoise`（3 类）
- `dev.worldgen.lithostitched.api.worldgen.feature`（1 类）
- `dev.worldgen.lithostitched.api.worldgen.modifier`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.placementcondition`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.placementmodifier`（1 类）
- `dev.worldgen.lithostitched.api.worldgen.poolelement`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.processor`（3 类）
- `dev.worldgen.lithostitched.api.worldgen.processor.enums`（3 类）
- `dev.worldgen.lithostitched.api.worldgen.processorcondition`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.stateprovider`（1 类）
- `dev.worldgen.lithostitched.api.worldgen.structure`（3 类）
- `dev.worldgen.lithostitched.api.worldgen.surface`（2 类）
- `dev.worldgen.lithostitched.api.worldgen.util`（5 类）
- `dev.worldgen.lithostitched.config`（1 类）
- `dev.worldgen.lithostitched.duck`（8 类）
- `dev.worldgen.lithostitched.duck.mnbs`（2 类）
- `dev.worldgen.lithostitched.impl`（1 类）
- `dev.worldgen.lithostitched.impl.duck`（1 类）
- `dev.worldgen.lithostitched.impl.event`（1 类）
- `dev.worldgen.lithostitched.impl.predicate`（8 类）
- `dev.worldgen.lithostitched.impl.registry`（1 类）
- `dev.worldgen.lithostitched.impl.worldgen.bandlands`（2 类）
- `dev.worldgen.lithostitched.impl.worldgen.bandlands.band`（3 类）
- `dev.worldgen.lithostitched.impl.worldgen.biomeinjector`（5 类）
- `dev.worldgen.lithostitched.impl.worldgen.biomeinjector.internal`（4 类）
- `dev.worldgen.lithostitched.impl.worldgen.biomeinjector.region`（2 类）
- `dev.worldgen.lithostitched.impl.worldgen.densityfunction`（13 类）
- `dev.worldgen.lithostitched.impl.worldgen.densityfunction.marker`（4 类）
- `dev.worldgen.lithostitched.impl.worldgen.fastnoise`（3 类）
- `dev.worldgen.lithostitched.impl.worldgen.modifier`（10 类）
- `dev.worldgen.lithostitched.impl.worldgen.modifier.util`（1 类）
- `dev.worldgen.lithostitched.impl.worldgen.processor`（8 类）
- `dev.worldgen.lithostitched.impl.worldgen.surface.condition`（5 类）
- `dev.worldgen.lithostitched.impl.worldgen.surface.rule`（3 类）
- `dev.worldgen.lithostitched.impl.worldgen.tree.foliageplacer`（2 类）
- `dev.worldgen.lithostitched.impl.worldgen.tree.rootplacer`（1 类）
- `dev.worldgen.lithostitched.mixin.client`（1 类）
- `dev.worldgen.lithostitched.mixin.common`（28 类）
- `dev.worldgen.lithostitched.mixin.common.bandlands`（2 类）
- `dev.worldgen.lithostitched.mixin.common.compat.terrablender`（1 类）
- `dev.worldgen.lithostitched.mixin.common.density`（1 类）
- `dev.worldgen.lithostitched.mixin.common.mnbs`（3 类）
- `dev.worldgen.lithostitched.mixin.common.neoforge`（1 类）
- `dev.worldgen.lithostitched.mixin.common.predicate`（1 类）
- `dev.worldgen.lithostitched.mixin.common.processor`（9 类）
- `dev.worldgen.lithostitched.mixin.common.template`（3 类）
- `dev.worldgen.lithostitched.mixin.common.template.mansion`（4 类）
- `dev.worldgen.lithostitched.mixin.common.template.mansion.floor`（3 类）
- `dev.worldgen.lithostitched.mixin.common.villager`（1 类）
- `dev.worldgen.lithostitched.mixin.server`（1 类）
- `dev.worldgen.lithostitched.platform`（1 类）
- `dev.worldgen.lithostitched.platform.neoforge`（3 类）
- `dev.worldgen.lithostitched.platform.neoforge.resource`（1 类）
- `dev.worldgen.lithostitched.platform.neoforge.worldgen`（1 类）
- `dev.worldgen.lithostitched.registry`（2 类）
- `dev.worldgen.lithostitched.util`（2 类）
- `dev.worldgen.lithostitched.worldgen`（2 类）
- `dev.worldgen.lithostitched.worldgen.blockentitymodifier`（2 类）
- `dev.worldgen.lithostitched.worldgen.blockpredicate`（7 类）
- `dev.worldgen.lithostitched.worldgen.feature`（10 类）
- `dev.worldgen.lithostitched.worldgen.feature.config`（10 类）
- `dev.worldgen.lithostitched.worldgen.feature.util`（1 类）
- `dev.worldgen.lithostitched.worldgen.modifier`（15 类）
- `dev.worldgen.lithostitched.worldgen.modifier.internal`（2 类）
- `dev.worldgen.lithostitched.worldgen.modifier.template`（2 类）
- `dev.worldgen.lithostitched.worldgen.placementcondition`（11 类）
- `dev.worldgen.lithostitched.worldgen.placementmodifier`（3 类）
- `dev.worldgen.lithostitched.worldgen.poolalias`（1 类）
- `dev.worldgen.lithostitched.worldgen.poolelement`（5 类）
- `dev.worldgen.lithostitched.worldgen.poolelement.legacy`（2 类）
- `dev.worldgen.lithostitched.worldgen.processor.condition`（8 类）
- `dev.worldgen.lithostitched.worldgen.stateprovider`（2 类）
- `dev.worldgen.lithostitched.worldgen.structure`（7 类）
- `dev.worldgen.lithostitched.worldgen.surface`（1 类）
