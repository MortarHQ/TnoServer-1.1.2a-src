# ByePregen（`byepregen`）

> jar：`[优化]byepregen.jar` ｜ 版本：`1.0.9.1` ｜ 作者：MoePus
> 规模：**173 个类 / 20 个包 / 14810 行** ｜ 事件处理类 0 ｜ Mixin 83 ｜ 注册/管理类 4

**依赖**：`minecraft`、`noisiumed`、`noisium`、`antixray`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.moepus.byepregen.yalight` | 28 | `YABlockLightEngine`、`YASkyLightEngine`、`YALightEngine` |
| `com.moepus.byepregen.mixin` | 27 | `LeavesBlockWorldgenTickMixin`、`MatchingBlocksPredicateMixin`、`ServerLevelWeatherTickMixin` |
| `com.moepus.byepregen.mixin.yalight` | 18 | `ClientboundLightUpdatePacketDataYALightMixin`、`ClientPacketListenerYALightMixin`、`ThreadedLevelLightEngineYAMixin` |
| `com.moepus.byepregen.mixin.accessor` | 15 | `PersistentEntitySectionManagerAccessor`、`ServerLevelEntityManagerAccessor`、`ArchitecturyEventImplAccessor` |
| `com.moepus.byepregen.gcfree` | 12 | `NbtWriter`、`ChunkDataSerializer`、`BlockStateNbtCache` |
| `com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs` | 10 | `StateImporter`、`SerializationScratch`、`NetworkWriter` |
| `com.moepus.byepregen.mixin.placement` | 10 | `HeightRangePlacementMixin`、`InSquarePlacementMixin`、`CarvingMaskPlacementMixin` |
| `com.moepus.byepregen.compat` | 10 | `NeoForgeEventBusCompat`、`DisabledHandler`、`C2MECompat` |
| `com.moepus.byepregen` | 7 | `Byepregen`、`Config`、`ConfigParser` |
| `com.moepus.byepregen.mixin.compat` | 6 | `C2MEHookCompatibilityMixin`、`C2MEServerBlockTickingMixin`、`VoxyWorldConversionFactoryMixin` |
| `com.moepus.byepregen.mixin.gcfree` | 5 | `LevelChunkWorldgenStateMixin`、`ChunkSerializerWorldgenStateMixin`、`ChunkStorageRawMixin` |
| `com.moepus.byepregen.Feature` | 5 | `FastPlacementContext`、`FastBlockPredicateOptimizer`、`FastStateTestingPredicate` |
| `com.moepus.byepregen.PaletteContainer.ArenaPelette` | 4 | `ArenaBlockStatePalettedContainer`、`ArenaBlockStateQueries`、`ArenaSectionMaterializer` |
| `com.moepus.byepregen.PaletteContainer` | 4 | `ByteStreamTag`、`BlockStatePackedDataBuilder`、`BlockStateRawIdAccess` |
| `com.moepus.byepregen.PaletteContainer.FastPalette` | 4 | `FastBlockStatePalettedContainer`、`FastBlockStateNbtReader`、`FastPalettedContainer` |
| `com.moepus.byepregen.PostProcess` | 3 | `PostProcessingSorter`、`PostProcessGenerationOptimizer`、`PostProcessingContext` |
| `com.moepus.byepregen.mixin.nbt` | 2 | `CompoundTagLoadSizingMixin`、`CompoundTagFastRuntimeMixin` |
| `com.moepus.byepregen.jfr` | 1 | `ByepregenJfrEvents` |
| `com.moepus.byepregen.optimization` | 1 | `FastNaturalSpawner` |
| `com.moepus.byepregen.bootstrap` | 1 | `TestWorldGenBootstrap` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PersistentEntitySectionManagerAccessor` | interface | — | Mixin | 0 | 14 | [PersistentEntitySectionManagerAccessor.java](com/moepus/byepregen/mixin/accessor/PersistentEntitySectionManagerAccessor.java) |
| `ServerLevelEntityManagerAccessor` | interface | — | Mixin | 0 | 14 | [ServerLevelEntityManagerAccessor.java](com/moepus/byepregen/mixin/accessor/ServerLevelEntityManagerAccessor.java) |
| `ClientboundLightUpdatePacketDataYALightMixin` | class | — | Mixin | 1 | 34 | [ClientboundLightUpdatePacketDataYALightMixin.java](com/moepus/byepregen/mixin/yalight/ClientboundLightUpdatePacketDataYALightMixin.java) |
| `ClientPacketListenerYALightMixin` | class | — | Mixin | 1 | 58 | [ClientPacketListenerYALightMixin.java](com/moepus/byepregen/mixin/yalight/ClientPacketListenerYALightMixin.java) |
| `Byepregen` | class | — | Mod | 1 | 30 | [Byepregen.java](com/moepus/byepregen/Byepregen.java) |
| `ArenaBlockStatePalettedContainer` | class | PalettedContainer | — | 43 | 527 | [ArenaBlockStatePalettedContainer.java](com/moepus/byepregen/PaletteContainer/ArenaPelette/ArenaBlockStatePalettedContainer.java) |
| `ArchitecturyEventImplAccessor` | interface | — | Mixin、MixinGate | 0 | 14 | [ArchitecturyEventImplAccessor.java](com/moepus/byepregen/mixin/accessor/ArchitecturyEventImplAccessor.java) |
| `ThreadedLevelLightEngineYAMixin` | class | — | Mixin | 10 | 270 | [ThreadedLevelLightEngineYAMixin.java](com/moepus/byepregen/mixin/yalight/ThreadedLevelLightEngineYAMixin.java) |
| `NbtWriter` | class | DataOutput | — | 60 | 410 | [NbtWriter.java](com/moepus/byepregen/gcfree/NbtWriter.java) |
| `ByepregenJfrEvents` | class | Event | — | 2 | 61 | [ByepregenJfrEvents.java](com/moepus/byepregen/jfr/ByepregenJfrEvents.java) |
| `YABlockLightEngine` | class | BlockLightEngine | — | 25 | 280 | [YABlockLightEngine.java](com/moepus/byepregen/yalight/YABlockLightEngine.java) |
| `LeavesBlockWorldgenTickMixin` | class | — | Mixin | 0 | 36 | [LeavesBlockWorldgenTickMixin.java](com/moepus/byepregen/mixin/LeavesBlockWorldgenTickMixin.java) |
| `MatchingBlocksPredicateMixin` | class | — | Mixin | 1 | 67 | [MatchingBlocksPredicateMixin.java](com/moepus/byepregen/mixin/MatchingBlocksPredicateMixin.java) |
| `EntitySectionAccessor` | interface | — | Mixin | 0 | 13 | [EntitySectionAccessor.java](com/moepus/byepregen/mixin/accessor/EntitySectionAccessor.java) |
| `EntitySectionStorageAccessor` | interface | — | Mixin | 0 | 15 | [EntitySectionStorageAccessor.java](com/moepus/byepregen/mixin/accessor/EntitySectionStorageAccessor.java) |
| `BlockLightEngineYASuperMixin` | class | — | Mixin | 0 | 24 | [BlockLightEngineYASuperMixin.java](com/moepus/byepregen/mixin/yalight/BlockLightEngineYASuperMixin.java) |
| `ClientChunkCacheYALightMixin` | class | YAImmediateChunkAccess | Mixin | 1 | 69 | [ClientChunkCacheYALightMixin.java](com/moepus/byepregen/mixin/yalight/ClientChunkCacheYALightMixin.java) |
| `LevelRendererYALightMixin` | class | — | Mixin | 2 | 94 | [LevelRendererYALightMixin.java](com/moepus/byepregen/mixin/yalight/LevelRendererYALightMixin.java) |
| `LevelLightEngineYAMixin` | class | YALightEngineHolder | Mixin | 13 | 131 | [LevelLightEngineYAMixin.java](com/moepus/byepregen/mixin/yalight/LevelLightEngineYAMixin.java) |
| `C2MEHookCompatibilityMixin` | class | — | Mixin、MixinGate | 1 | 16 | [C2MEHookCompatibilityMixin.java](com/moepus/byepregen/mixin/compat/C2MEHookCompatibilityMixin.java) |
| `C2MEServerBlockTickingMixin` | class | — | Mixin、MixinGate | 0 | 39 | [C2MEServerBlockTickingMixin.java](com/moepus/byepregen/mixin/compat/C2MEServerBlockTickingMixin.java) |
| `YASkyLightEngine` | class | SkyLightEngine | — | 30 | 239 | [YASkyLightEngine.java](com/moepus/byepregen/yalight/YASkyLightEngine.java) |
| `YALightEngine` | class | — | — | 34 | 284 | [YALightEngine.java](com/moepus/byepregen/yalight/YALightEngine.java) |
| `ServerLevelWeatherTickMixin` | class | Level | Mixin | 0 | 218 | [ServerLevelWeatherTickMixin.java](com/moepus/byepregen/mixin/ServerLevelWeatherTickMixin.java) |
| `ServerChunkCacheGetChunkMixin` | class | YAImmediateChunkAccess | Mixin | 4 | 231 | [ServerChunkCacheGetChunkMixin.java](com/moepus/byepregen/mixin/ServerChunkCacheGetChunkMixin.java) |
| `ServerChunkCacheTickChunksMixin` | class | — | Mixin、MixinGate | 3 | 222 | [ServerChunkCacheTickChunksMixin.java](com/moepus/byepregen/mixin/ServerChunkCacheTickChunksMixin.java) |
| `BlendingDataAccessor` | interface | — | Mixin | 0 | 16 | [BlendingDataAccessor.java](com/moepus/byepregen/mixin/accessor/BlendingDataAccessor.java) |
| `ImposterProtoChunkYALightDataMixin` | class | YAChunkLightAccess | Mixin | 0 | 38 | [ImposterProtoChunkYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/ImposterProtoChunkYALightDataMixin.java) |
| `EmptyLevelChunkYALightDataMixin` | class | YAChunkLightAccess | Mixin | 0 | 20 | [EmptyLevelChunkYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/EmptyLevelChunkYALightDataMixin.java) |
| `LevelChunkYALightDataMixin` | class | — | Mixin | 0 | 28 | [LevelChunkYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/LevelChunkYALightDataMixin.java) |
| `ChunkAccessYALightDataMixin` | class | YAChunkLightAccess | Mixin | 0 | 87 | [ChunkAccessYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/ChunkAccessYALightDataMixin.java) |
| `StateImporter` | class | StateImporter.RawIdSource | — | 28 | 449 | [StateImporter.java](com/moepus/byepregen/PaletteContainer/ArenaPelette/Codecs/StateImporter.java) |
| `ChunkDataSerializer` | class | — | — | 21 | 355 | [ChunkDataSerializer.java](com/moepus/byepregen/gcfree/ChunkDataSerializer.java) |
| `YANibbleArray` | class | DataLayer | — | 29 | 338 | [YANibbleArray.java](com/moepus/byepregen/yalight/YANibbleArray.java) |
| `LevelChunkArenaMixin` | class | — | Mixin | 0 | 31 | [LevelChunkArenaMixin.java](com/moepus/byepregen/mixin/LevelChunkArenaMixin.java) |
| `ChunkSerializerLowStatusHeightmapMixin` | class | — | Mixin | 0 | 33 | [ChunkSerializerLowStatusHeightmapMixin.java](com/moepus/byepregen/mixin/ChunkSerializerLowStatusHeightmapMixin.java) |
| `LevelChunkSectionAccessor` | interface | — | Mixin | 0 | 16 | [LevelChunkSectionAccessor.java](com/moepus/byepregen/mixin/LevelChunkSectionAccessor.java) |
| `ChunkStatusPostProcessingPreNormMixin` | class | — | Mixin、MixinGate | 0 | 31 | [ChunkStatusPostProcessingPreNormMixin.java](com/moepus/byepregen/mixin/ChunkStatusPostProcessingPreNormMixin.java) |
| `PlacedFeatureMixin` | class | — | Mixin、MixinGate | 1 | 39 | [PlacedFeatureMixin.java](com/moepus/byepregen/mixin/PlacedFeatureMixin.java) |
| `PalettedContainerNoLithiumMixin` | class | — | Mixin、MixinGate | 2 | 19 | [PalettedContainerNoLithiumMixin.java](com/moepus/byepregen/mixin/PalettedContainerNoLithiumMixin.java) |
| `AllOfPredicateMixin` | class | CombiningPredicate | Mixin | 1 | 31 | [AllOfPredicateMixin.java](com/moepus/byepregen/mixin/AllOfPredicateMixin.java) |
| `LevelChunkPostProcessMixin` | class | ChunkAccess | Mixin | 0 | 53 | [LevelChunkPostProcessMixin.java](com/moepus/byepregen/mixin/LevelChunkPostProcessMixin.java) |
| `ChunkMapAccessor` | interface | — | Mixin | 0 | 33 | [ChunkMapAccessor.java](com/moepus/byepregen/mixin/ChunkMapAccessor.java) |
| `StateTestingPredicateMixin` | class | FastStateTestingPredicate | Mixin | 2 | 39 | [StateTestingPredicateMixin.java](com/moepus/byepregen/mixin/StateTestingPredicateMixin.java) |
| `ChunkSerializerArenaReadMixin` | class | — | Mixin | 0 | 111 | [ChunkSerializerArenaReadMixin.java](com/moepus/byepregen/mixin/ChunkSerializerArenaReadMixin.java) |
| `CarvingMaskAccessor` | interface | — | Mixin | 0 | 16 | [CarvingMaskAccessor.java](com/moepus/byepregen/mixin/CarvingMaskAccessor.java) |
| `ChunkHolderAccessor` | interface | — | Mixin | 0 | 13 | [ChunkHolderAccessor.java](com/moepus/byepregen/mixin/ChunkHolderAccessor.java) |
| `LinearPaletteMixin` | class | PaletteRawIdAccess | Mixin | 0 | 26 | [LinearPaletteMixin.java](com/moepus/byepregen/mixin/LinearPaletteMixin.java) |
| `HashMapPaletteMixin` | class | PaletteRawIdAccess | Mixin | 0 | 26 | [HashMapPaletteMixin.java](com/moepus/byepregen/mixin/HashMapPaletteMixin.java) |
| `GlobalPaletteMixin` | class | PaletteRawIdAccess | Mixin | 0 | 14 | [GlobalPaletteMixin.java](com/moepus/byepregen/mixin/GlobalPaletteMixin.java) |
| `PalettedContainerMixin` | class | BlockStateRawIdAccess | Mixin | 1 | 44 | [PalettedContainerMixin.java](com/moepus/byepregen/mixin/PalettedContainerMixin.java) |
| `PlacementModifierMixin` | class | FastPlacementModifier | Mixin | 0 | 10 | [PlacementModifierMixin.java](com/moepus/byepregen/mixin/PlacementModifierMixin.java) |
| `ChunkAccessArenaMixin` | class | — | Mixin | 0 | 94 | [ChunkAccessArenaMixin.java](com/moepus/byepregen/mixin/ChunkAccessArenaMixin.java) |
| `WorldGenRegionSectionCacheMixin` | class | WorldGenRegionSectionCache | Mixin | 1 | 44 | [WorldGenRegionSectionCacheMixin.java](com/moepus/byepregen/mixin/WorldGenRegionSectionCacheMixin.java) |
| `SingleValuePaletteMixin` | class | PaletteRawIdAccess | Mixin | 0 | 24 | [SingleValuePaletteMixin.java](com/moepus/byepregen/mixin/SingleValuePaletteMixin.java) |
| `AnyOfPredicateMixin` | class | CombiningPredicate | Mixin | 1 | 31 | [AnyOfPredicateMixin.java](com/moepus/byepregen/mixin/AnyOfPredicateMixin.java) |
| `HeightRangePlacementMixin` | class | FastPlacementModifier | Mixin | 0 | 22 | [HeightRangePlacementMixin.java](com/moepus/byepregen/mixin/placement/HeightRangePlacementMixin.java) |
| `InSquarePlacementMixin` | class | FastPlacementModifier | Mixin | 0 | 15 | [InSquarePlacementMixin.java](com/moepus/byepregen/mixin/placement/InSquarePlacementMixin.java) |
| `CarvingMaskPlacementMixin` | class | FastPlacementModifier | Mixin | 0 | 37 | [CarvingMaskPlacementMixin.java](com/moepus/byepregen/mixin/placement/CarvingMaskPlacementMixin.java) |
| `HeightmapPlacementMixin` | class | FastPlacementModifier | Mixin | 0 | 25 | [HeightmapPlacementMixin.java](com/moepus/byepregen/mixin/placement/HeightmapPlacementMixin.java) |

## Mixin（83 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PersistentEntitySectionManagerAccessor` | `PersistentEntitySectionManagerAccessor` | [PersistentEntitySectionManagerAccessor.java](com/moepus/byepregen/mixin/accessor/PersistentEntitySectionManagerAccessor.java) |
| `ServerLevelEntityManagerAccessor` | `ServerLevelEntityManagerAccessor` | [ServerLevelEntityManagerAccessor.java](com/moepus/byepregen/mixin/accessor/ServerLevelEntityManagerAccessor.java) |
| `ClientboundLightUpdatePacketDataYALightMixin` | `ClientboundLightUpdatePacketDataYALight` | [ClientboundLightUpdatePacketDataYALightMixin.java](com/moepus/byepregen/mixin/yalight/ClientboundLightUpdatePacketDataYALightMixin.java) |
| `ClientPacketListenerYALightMixin` | `ClientPacketListenerYALight` | [ClientPacketListenerYALightMixin.java](com/moepus/byepregen/mixin/yalight/ClientPacketListenerYALightMixin.java) |
| `ArchitecturyEventImplAccessor` | `ArchitecturyEventImplAccessor` | [ArchitecturyEventImplAccessor.java](com/moepus/byepregen/mixin/accessor/ArchitecturyEventImplAccessor.java) |
| `ThreadedLevelLightEngineYAMixin` | `ThreadedLevelLightEngineYA` | [ThreadedLevelLightEngineYAMixin.java](com/moepus/byepregen/mixin/yalight/ThreadedLevelLightEngineYAMixin.java) |
| `LeavesBlockWorldgenTickMixin` | `LeavesBlockWorldgenTick` | [LeavesBlockWorldgenTickMixin.java](com/moepus/byepregen/mixin/LeavesBlockWorldgenTickMixin.java) |
| `MatchingBlocksPredicateMixin` | `MatchingBlocksPredicate` | [MatchingBlocksPredicateMixin.java](com/moepus/byepregen/mixin/MatchingBlocksPredicateMixin.java) |
| `EntitySectionAccessor` | `EntitySectionAccessor` | [EntitySectionAccessor.java](com/moepus/byepregen/mixin/accessor/EntitySectionAccessor.java) |
| `EntitySectionStorageAccessor` | `EntitySectionStorageAccessor` | [EntitySectionStorageAccessor.java](com/moepus/byepregen/mixin/accessor/EntitySectionStorageAccessor.java) |
| `BlockLightEngineYASuperMixin` | `BlockLightEngineYASuper` | [BlockLightEngineYASuperMixin.java](com/moepus/byepregen/mixin/yalight/BlockLightEngineYASuperMixin.java) |
| `ClientChunkCacheYALightMixin` | `ClientChunkCacheYALight` | [ClientChunkCacheYALightMixin.java](com/moepus/byepregen/mixin/yalight/ClientChunkCacheYALightMixin.java) |
| `LevelRendererYALightMixin` | `LevelRendererYALight` | [LevelRendererYALightMixin.java](com/moepus/byepregen/mixin/yalight/LevelRendererYALightMixin.java) |
| `LevelLightEngineYAMixin` | `LevelLightEngineYA` | [LevelLightEngineYAMixin.java](com/moepus/byepregen/mixin/yalight/LevelLightEngineYAMixin.java) |
| `C2MEHookCompatibilityMixin` | `C2MEHookCompatibility` | [C2MEHookCompatibilityMixin.java](com/moepus/byepregen/mixin/compat/C2MEHookCompatibilityMixin.java) |
| `C2MEServerBlockTickingMixin` | `C2MEServerBlockTicking` | [C2MEServerBlockTickingMixin.java](com/moepus/byepregen/mixin/compat/C2MEServerBlockTickingMixin.java) |
| `ServerLevelWeatherTickMixin` | `ServerLevelWeatherTick` | [ServerLevelWeatherTickMixin.java](com/moepus/byepregen/mixin/ServerLevelWeatherTickMixin.java) |
| `ServerChunkCacheGetChunkMixin` | `ServerChunkCacheGetChunk` | [ServerChunkCacheGetChunkMixin.java](com/moepus/byepregen/mixin/ServerChunkCacheGetChunkMixin.java) |
| `ServerChunkCacheTickChunksMixin` | `ServerChunkCacheTickChunks` | [ServerChunkCacheTickChunksMixin.java](com/moepus/byepregen/mixin/ServerChunkCacheTickChunksMixin.java) |
| `BlendingDataAccessor` | `BlendingDataAccessor` | [BlendingDataAccessor.java](com/moepus/byepregen/mixin/accessor/BlendingDataAccessor.java) |
| `ImposterProtoChunkYALightDataMixin` | `ImposterProtoChunkYALightData` | [ImposterProtoChunkYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/ImposterProtoChunkYALightDataMixin.java) |
| `EmptyLevelChunkYALightDataMixin` | `EmptyLevelChunkYALightData` | [EmptyLevelChunkYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/EmptyLevelChunkYALightDataMixin.java) |
| `LevelChunkYALightDataMixin` | `LevelChunkYALightData` | [LevelChunkYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/LevelChunkYALightDataMixin.java) |
| `ChunkAccessYALightDataMixin` | `ChunkAccessYALightData` | [ChunkAccessYALightDataMixin.java](com/moepus/byepregen/mixin/yalight/ChunkAccessYALightDataMixin.java) |
| `LevelChunkArenaMixin` | `LevelChunkArena` | [LevelChunkArenaMixin.java](com/moepus/byepregen/mixin/LevelChunkArenaMixin.java) |
| `ChunkSerializerLowStatusHeightmapMixin` | `ChunkSerializerLowStatusHeightmap` | [ChunkSerializerLowStatusHeightmapMixin.java](com/moepus/byepregen/mixin/ChunkSerializerLowStatusHeightmapMixin.java) |
| `LevelChunkSectionAccessor` | `LevelChunkSectionAccessor` | [LevelChunkSectionAccessor.java](com/moepus/byepregen/mixin/LevelChunkSectionAccessor.java) |
| `ChunkStatusPostProcessingPreNormMixin` | `ChunkStatusPostProcessingPreNorm` | [ChunkStatusPostProcessingPreNormMixin.java](com/moepus/byepregen/mixin/ChunkStatusPostProcessingPreNormMixin.java) |
| `PlacedFeatureMixin` | `PlacedFeature` | [PlacedFeatureMixin.java](com/moepus/byepregen/mixin/PlacedFeatureMixin.java) |
| `PalettedContainerNoLithiumMixin` | `PalettedContainerNoLithium` | [PalettedContainerNoLithiumMixin.java](com/moepus/byepregen/mixin/PalettedContainerNoLithiumMixin.java) |
| `AllOfPredicateMixin` | `AllOfPredicate` | [AllOfPredicateMixin.java](com/moepus/byepregen/mixin/AllOfPredicateMixin.java) |
| `LevelChunkPostProcessMixin` | `LevelChunkPostProcess` | [LevelChunkPostProcessMixin.java](com/moepus/byepregen/mixin/LevelChunkPostProcessMixin.java) |
| `ChunkMapAccessor` | `ChunkMapAccessor` | [ChunkMapAccessor.java](com/moepus/byepregen/mixin/ChunkMapAccessor.java) |
| `StateTestingPredicateMixin` | `StateTestingPredicate` | [StateTestingPredicateMixin.java](com/moepus/byepregen/mixin/StateTestingPredicateMixin.java) |
| `ChunkSerializerArenaReadMixin` | `ChunkSerializerArenaRead` | [ChunkSerializerArenaReadMixin.java](com/moepus/byepregen/mixin/ChunkSerializerArenaReadMixin.java) |
| `CarvingMaskAccessor` | `CarvingMaskAccessor` | [CarvingMaskAccessor.java](com/moepus/byepregen/mixin/CarvingMaskAccessor.java) |
| `ChunkHolderAccessor` | `ChunkHolderAccessor` | [ChunkHolderAccessor.java](com/moepus/byepregen/mixin/ChunkHolderAccessor.java) |
| `LinearPaletteMixin` | `LinearPalette` | [LinearPaletteMixin.java](com/moepus/byepregen/mixin/LinearPaletteMixin.java) |
| `HashMapPaletteMixin` | `HashMapPalette` | [HashMapPaletteMixin.java](com/moepus/byepregen/mixin/HashMapPaletteMixin.java) |
| `GlobalPaletteMixin` | `GlobalPalette` | [GlobalPaletteMixin.java](com/moepus/byepregen/mixin/GlobalPaletteMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](com/moepus/byepregen/Config.java) |
| `ConfigParser` | — | [ConfigParser.java](com/moepus/byepregen/ConfigParser.java) |

## 按包浏览

- `com.moepus.byepregen`（7 类）
- `com.moepus.byepregen.Feature`（5 类）
- `com.moepus.byepregen.PaletteContainer`（4 类）
- `com.moepus.byepregen.PaletteContainer.ArenaPelette`（4 类）
- `com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs`（10 类）
- `com.moepus.byepregen.PaletteContainer.FastPalette`（4 类）
- `com.moepus.byepregen.PostProcess`（3 类）
- `com.moepus.byepregen.bootstrap`（1 类）
- `com.moepus.byepregen.compat`（10 类）
- `com.moepus.byepregen.gcfree`（12 类）
- `com.moepus.byepregen.jfr`（1 类）
- `com.moepus.byepregen.mixin`（27 类）
- `com.moepus.byepregen.mixin.accessor`（15 类）
- `com.moepus.byepregen.mixin.compat`（6 类）
- `com.moepus.byepregen.mixin.gcfree`（5 类）
- `com.moepus.byepregen.mixin.nbt`（2 类）
- `com.moepus.byepregen.mixin.placement`（10 类）
- `com.moepus.byepregen.mixin.yalight`（18 类）
- `com.moepus.byepregen.optimization`（1 类）
- `com.moepus.byepregen.yalight`（28 类）
