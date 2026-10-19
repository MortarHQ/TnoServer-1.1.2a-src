# Moog's Structure Lib（`moogs_structures`）

> jar：`[前置]moogs_structures.jar` ｜ 版本：`2.0.2` ｜ 作者：FinnDog
> 规模：**68 个类 / 21 个包 / 5073 行** ｜ 事件处理类 1 ｜ Mixin 11 ｜ 注册/管理类 20

**依赖**：`moogs_structures`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.finndog.moogs_structures.mixins.structures` | 9 | `StructureTemplateManagerAccessor`、`LocateCommandMixin`、`StructureProcessorAccessor` |
| `com.finndog.moogs_structures.modinit.registry` | 9 | `ResourcefulRegistryChild`、`EnumResourcefulRegistryChild`、`ResourcefulRegistry` |
| `com.finndog.moogs_structures.modinit` | 7 | `MoogsStructuresConditionsRegistry`、`MoogsStructuresStructurePlacementType`、`MoogsStructuresStructurePieces` |
| `com.finndog.moogs_structures.utils` | 6 | `GeneralUtils`、`OpenSimplex2F`、`VersionResolver` |
| `com.finndog.moogs_structures.events.lifecycle` | 6 | `ServerGoingToStartEvent`、`ServerGoingToStopEvent`、`TagsUpdatedEvent` |
| `com.finndog.moogs_structures.world.structures.pieces` | 4 | `PieceLimitedJigsawManager`、`VersionAwareSinglePoolElement`、`MirroringSingleJigsawPiece` |
| `com.finndog.moogs_structures.modinit.registry.neoforge` | 4 | `NeoForgeCustomRegistry`、`NeoForgeResourcefulRegistry`、`NeoForgeRegistryEntry` |
| `com.finndog.moogs_structures.events.base` | 3 | `CancellableEventHandler`、`ReturnableEventHandler`、`EventHandler` |
| `com.finndog.moogs_structures.misc.structurepiececounter` | 3 | `StructurePieceCountsManager`、`StructurePieceCountsObj`、`StructurePieceCountsAdditionsMerger` |
| `com.finndog.moogs_structures.world.placements` | 3 | `UnlimitedCountPlacement`、`SnapToLowerNonAirPlacement`、`MinusEightPlacement` |
| `com.finndog.moogs_structures.datagen` | 2 | `StructureNbtUpdaterDatagen`、`StructureNbtUpdater` |
| `com.finndog.moogs_structures.platform` | 2 | `IRegistryPlatform`、`Services` |
| `com.finndog.moogs_structures.world.structures` | 2 | `GenericJigsawStructure`、`GenericNetherJigsawStructure` |
| `com.finndog.moogs_structures.neoforge` | 1 | `MoogsStructuresNeoforge` |
| `com.finndog.moogs_structures.mixins.resources` | 1 | `NamespaceResourceManagerAccessor` |
| `com.finndog.moogs_structures.mixins.neoforge.structures` | 1 | `StructurePoolMixin` |
| `com.finndog.moogs_structures.commands` | 1 | `DebugCommand` |
| `com.finndog.moogs_structures.world.structures.placements` | 1 | `AdvancedRandomSpread` |
| `com.finndog.moogs_structures` | 1 | `MoogsStructuresCommon` |
| `com.finndog.moogs_structures.world.structures.codecs` | 1 | `YRangeAllowance` |
| `com.finndog.moogs_structures.utils.neoforge` | 1 | `PlatformHooksImpl` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `StructureNbtUpdaterDatagen` | class | — | EventBusSubscriber | 1 | 23 | [StructureNbtUpdaterDatagen.java](com/finndog/moogs_structures/datagen/StructureNbtUpdaterDatagen.java) |
| `MoogsStructuresNeoforge` | class | — | Mod | 5 | 58 | [MoogsStructuresNeoforge.java](com/finndog/moogs_structures/neoforge/MoogsStructuresNeoforge.java) |
| `StructureTemplateManagerAccessor` | interface | — | Mixin | 0 | 13 | [StructureTemplateManagerAccessor.java](com/finndog/moogs_structures/mixins/structures/StructureTemplateManagerAccessor.java) |
| `NamespaceResourceManagerAccessor` | interface | — | Mixin | 0 | 14 | [NamespaceResourceManagerAccessor.java](com/finndog/moogs_structures/mixins/resources/NamespaceResourceManagerAccessor.java) |
| `LocateCommandMixin` | class | — | Mixin | 1 | 69 | [LocateCommandMixin.java](com/finndog/moogs_structures/mixins/structures/LocateCommandMixin.java) |
| `CancellableEventHandler` | class | — | — | 7 | 65 | [CancellableEventHandler.java](com/finndog/moogs_structures/events/base/CancellableEventHandler.java) |
| `ReturnableEventHandler` | class | — | — | 6 | 54 | [ReturnableEventHandler.java](com/finndog/moogs_structures/events/base/ReturnableEventHandler.java) |
| `PieceLimitedJigsawManager` | class | — | — | 5 | 639 | [PieceLimitedJigsawManager.java](com/finndog/moogs_structures/world/structures/pieces/PieceLimitedJigsawManager.java) |
| `EventHandler` | class | — | — | 3 | 24 | [EventHandler.java](com/finndog/moogs_structures/events/base/EventHandler.java) |
| `NeoForgeCustomRegistry` | class | CustomRegistryLookup<T> | — | 7 | 57 | [NeoForgeCustomRegistry.java](com/finndog/moogs_structures/modinit/registry/neoforge/NeoForgeCustomRegistry.java) |
| `MoogsStructuresConditionsRegistry` | class | — | — | 0 | 23 | [MoogsStructuresConditionsRegistry.java](com/finndog/moogs_structures/modinit/MoogsStructuresConditionsRegistry.java) |
| `ResourcefulRegistryChild` | class | ResourcefulRegistry<T> | — | 3 | 28 | [ResourcefulRegistryChild.java](com/finndog/moogs_structures/modinit/registry/ResourcefulRegistryChild.java) |
| `EnumResourcefulRegistryChild` | class | — | — | 2 | 22 | [EnumResourcefulRegistryChild.java](com/finndog/moogs_structures/modinit/registry/EnumResourcefulRegistryChild.java) |
| `ResourcefulRegistry` | interface | — | — | 0 | 18 | [ResourcefulRegistry.java](com/finndog/moogs_structures/modinit/registry/ResourcefulRegistry.java) |
| `CustomRegistry` | interface | — | — | 2 | 52 | [CustomRegistry.java](com/finndog/moogs_structures/modinit/registry/CustomRegistry.java) |
| `RegistryEntries` | class | — | — | 2 | 19 | [RegistryEntries.java](com/finndog/moogs_structures/modinit/registry/RegistryEntries.java) |
| `CustomRegistryLookup` | interface | — | — | 0 | 22 | [CustomRegistryLookup.java](com/finndog/moogs_structures/modinit/registry/CustomRegistryLookup.java) |
| `RegistryEntry` | interface | — | — | 0 | 12 | [RegistryEntry.java](com/finndog/moogs_structures/modinit/registry/RegistryEntry.java) |
| `BasicRegistryEntry` | class | RegistryEntry<T> | — | 2 | 25 | [BasicRegistryEntry.java](com/finndog/moogs_structures/modinit/registry/BasicRegistryEntry.java) |
| `NeoForgeResourcefulRegistry` | class | ResourcefulRegistry<T> | — | 3 | 40 | [NeoForgeResourcefulRegistry.java](com/finndog/moogs_structures/modinit/registry/neoforge/NeoForgeResourcefulRegistry.java) |
| `NeoForgeRegistryEntry` | class | RegistryEntry<T> | — | 2 | 24 | [NeoForgeRegistryEntry.java](com/finndog/moogs_structures/modinit/registry/neoforge/NeoForgeRegistryEntry.java) |
| `StructureProcessorAccessor` | interface | — | Mixin | 0 | 13 | [StructureProcessorAccessor.java](com/finndog/moogs_structures/mixins/structures/StructureProcessorAccessor.java) |
| `PoolElementStructurePieceAccessor` | interface | — | Mixin | 0 | 19 | [PoolElementStructurePieceAccessor.java](com/finndog/moogs_structures/mixins/structures/PoolElementStructurePieceAccessor.java) |
| `TemplateAccessor` | interface | — | Mixin | 0 | 14 | [TemplateAccessor.java](com/finndog/moogs_structures/mixins/structures/TemplateAccessor.java) |
| `StructurePieceAccessor` | interface | — | Mixin | 0 | 13 | [StructurePieceAccessor.java](com/finndog/moogs_structures/mixins/structures/StructurePieceAccessor.java) |
| `StructurePoolAccessor` | interface | — | Mixin | 0 | 36 | [StructurePoolAccessor.java](com/finndog/moogs_structures/mixins/structures/StructurePoolAccessor.java) |
| `ListPoolElementAccessor` | interface | — | Mixin | 0 | 14 | [ListPoolElementAccessor.java](com/finndog/moogs_structures/mixins/structures/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | interface | — | Mixin | 0 | 25 | [SinglePoolElementAccessor.java](com/finndog/moogs_structures/mixins/structures/SinglePoolElementAccessor.java) |
| `StructurePoolMixin` | class | — | Mixin | 1 | 22 | [StructurePoolMixin.java](com/finndog/moogs_structures/mixins/neoforge/structures/StructurePoolMixin.java) |
| `IRegistryPlatform` | interface | — | — | 0 | 17 | [IRegistryPlatform.java](com/finndog/moogs_structures/platform/IRegistryPlatform.java) |
| `StructurePieceCountsManager` | class | SimpleJsonResourceReloadListener | — | 8 | 143 | [StructurePieceCountsManager.java](com/finndog/moogs_structures/misc/structurepiececounter/StructurePieceCountsManager.java) |
| `GeneralUtils` | class | — | — | 15 | 267 | [GeneralUtils.java](com/finndog/moogs_structures/utils/GeneralUtils.java) |
| `OpenSimplex2F` | class | — | — | 13 | 760 | [OpenSimplex2F.java](com/finndog/moogs_structures/utils/OpenSimplex2F.java) |
| `DebugCommand` | class | — | — | 5 | 51 | [DebugCommand.java](com/finndog/moogs_structures/commands/DebugCommand.java) |
| `ServerGoingToStartEvent` | record | — | — | 1 | 13 | [ServerGoingToStartEvent.java](com/finndog/moogs_structures/events/lifecycle/ServerGoingToStartEvent.java) |
| `ServerGoingToStopEvent` | class | — | — | 0 | 12 | [ServerGoingToStopEvent.java](com/finndog/moogs_structures/events/lifecycle/ServerGoingToStopEvent.java) |
| `TagsUpdatedEvent` | record | — | — | 0 | 9 | [TagsUpdatedEvent.java](com/finndog/moogs_structures/events/lifecycle/TagsUpdatedEvent.java) |
| `SetupEvent` | record | — | — | 1 | 13 | [SetupEvent.java](com/finndog/moogs_structures/events/lifecycle/SetupEvent.java) |
| `FinalSetupEvent` | record | — | — | 1 | 13 | [FinalSetupEvent.java](com/finndog/moogs_structures/events/lifecycle/FinalSetupEvent.java) |
| `RegisterReloadListenerEvent` | record | — | — | 1 | 15 | [RegisterReloadListenerEvent.java](com/finndog/moogs_structures/events/lifecycle/RegisterReloadListenerEvent.java) |
| `GenericJigsawStructure` | class | Structure | — | 8 | 466 | [GenericJigsawStructure.java](com/finndog/moogs_structures/world/structures/GenericJigsawStructure.java) |
| `AdvancedRandomSpread` | class | RandomSpreadStructurePlacement | — | 10 | 192 | [AdvancedRandomSpread.java](com/finndog/moogs_structures/world/structures/placements/AdvancedRandomSpread.java) |
| `VersionResolver` | class | Comparable<VersionResolver.VersionNu | — | 14 | 195 | [VersionResolver.java](com/finndog/moogs_structures/utils/VersionResolver.java) |
| `BoxOctree` | class | — | — | 8 | 222 | [BoxOctree.java](com/finndog/moogs_structures/utils/BoxOctree.java) |
| `AsyncLocator` | class | — | — | 12 | 116 | [AsyncLocator.java](com/finndog/moogs_structures/utils/AsyncLocator.java) |
| `MoogsStructuresCommon` | class | — | — | 5 | 49 | [MoogsStructuresCommon.java](com/finndog/moogs_structures/MoogsStructuresCommon.java) |
| `ResourcefulRegistriesImpl` | class | IRegistryPlatform | — | 7 | 73 | [ResourcefulRegistriesImpl.java](com/finndog/moogs_structures/modinit/registry/neoforge/ResourcefulRegistriesImpl.java) |
| `VersionAwareSinglePoolElement` | class | SinglePoolElement | — | 8 | 139 | [VersionAwareSinglePoolElement.java](com/finndog/moogs_structures/world/structures/pieces/VersionAwareSinglePoolElement.java) |
| `MirroringSingleJigsawPiece` | class | SinglePoolElement | — | 8 | 141 | [MirroringSingleJigsawPiece.java](com/finndog/moogs_structures/world/structures/pieces/MirroringSingleJigsawPiece.java) |
| `StructureNbtUpdater` | class | DataProvider | — | 5 | 99 | [StructureNbtUpdater.java](com/finndog/moogs_structures/datagen/StructureNbtUpdater.java) |
| `MoogsStructuresStructurePlacementType` | class | — | — | 0 | 18 | [MoogsStructuresStructurePlacementType.java](com/finndog/moogs_structures/modinit/MoogsStructuresStructurePlacementType.java) |
| `MoogsStructuresStructurePieces` | class | — | — | 0 | 30 | [MoogsStructuresStructurePieces.java](com/finndog/moogs_structures/modinit/MoogsStructuresStructurePieces.java) |
| `MoogsStructuresTags` | class | — | — | 1 | 16 | [MoogsStructuresTags.java](com/finndog/moogs_structures/modinit/MoogsStructuresTags.java) |
| `MoogsStructuresPlacements` | class | — | — | 0 | 26 | [MoogsStructuresPlacements.java](com/finndog/moogs_structures/modinit/MoogsStructuresPlacements.java) |
| `MoogsStructuresProcessors` | class | — | — | 0 | 13 | [MoogsStructuresProcessors.java](com/finndog/moogs_structures/modinit/MoogsStructuresProcessors.java) |
| `MoogsStructuresStructures` | class | — | — | 0 | 20 | [MoogsStructuresStructures.java](com/finndog/moogs_structures/modinit/MoogsStructuresStructures.java) |
| `ResourcefulRegistries` | class | — | — | 3 | 24 | [ResourcefulRegistries.java](com/finndog/moogs_structures/modinit/registry/ResourcefulRegistries.java) |
| `Services` | class | — | — | 1 | 12 | [Services.java](com/finndog/moogs_structures/platform/Services.java) |
| `StructurePieceCountsObj` | class | — | — | 0 | 33 | [StructurePieceCountsObj.java](com/finndog/moogs_structures/misc/structurepiececounter/StructurePieceCountsObj.java) |
| `StructurePieceCountsAdditionsMerger` | class | — | — | 1 | 34 | [StructurePieceCountsAdditionsMerger.java](com/finndog/moogs_structures/misc/structurepiececounter/StructurePieceCountsAdditionsMerger.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `StructureNbtUpdaterDatagen` | 1 | `com.finndog.moogs_structures.datagen` | [StructureNbtUpdaterDatagen.java](com/finndog/moogs_structures/datagen/StructureNbtUpdaterDatagen.java) |

## Mixin（11 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StructureTemplateManagerAccessor` | `StructureTemplateManagerAccessor` | [StructureTemplateManagerAccessor.java](com/finndog/moogs_structures/mixins/structures/StructureTemplateManagerAccessor.java) |
| `NamespaceResourceManagerAccessor` | `NamespaceResourceManagerAccessor` | [NamespaceResourceManagerAccessor.java](com/finndog/moogs_structures/mixins/resources/NamespaceResourceManagerAccessor.java) |
| `LocateCommandMixin` | `LocateCommand` | [LocateCommandMixin.java](com/finndog/moogs_structures/mixins/structures/LocateCommandMixin.java) |
| `StructureProcessorAccessor` | `StructureProcessorAccessor` | [StructureProcessorAccessor.java](com/finndog/moogs_structures/mixins/structures/StructureProcessorAccessor.java) |
| `PoolElementStructurePieceAccessor` | `PoolElementStructurePieceAccessor` | [PoolElementStructurePieceAccessor.java](com/finndog/moogs_structures/mixins/structures/PoolElementStructurePieceAccessor.java) |
| `TemplateAccessor` | `TemplateAccessor` | [TemplateAccessor.java](com/finndog/moogs_structures/mixins/structures/TemplateAccessor.java) |
| `StructurePieceAccessor` | `StructurePieceAccessor` | [StructurePieceAccessor.java](com/finndog/moogs_structures/mixins/structures/StructurePieceAccessor.java) |
| `StructurePoolAccessor` | `StructurePoolAccessor` | [StructurePoolAccessor.java](com/finndog/moogs_structures/mixins/structures/StructurePoolAccessor.java) |
| `ListPoolElementAccessor` | `ListPoolElementAccessor` | [ListPoolElementAccessor.java](com/finndog/moogs_structures/mixins/structures/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | `SinglePoolElementAccessor` | [SinglePoolElementAccessor.java](com/finndog/moogs_structures/mixins/structures/SinglePoolElementAccessor.java) |
| `StructurePoolMixin` | `StructurePool` | [StructurePoolMixin.java](com/finndog/moogs_structures/mixins/neoforge/structures/StructurePoolMixin.java) |

## 全部类（68）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `StructureNbtUpdaterDatagen` | class | — | EventBusSubscriber | 1 | 23 | [StructureNbtUpdaterDatagen.java](com/finndog/moogs_structures/datagen/StructureNbtUpdaterDatagen.java) |
| `MoogsStructuresNeoforge` | class | — | Mod | 5 | 58 | [MoogsStructuresNeoforge.java](com/finndog/moogs_structures/neoforge/MoogsStructuresNeoforge.java) |
| `StructureTemplateManagerAccessor` | interface | — | Mixin | 0 | 13 | [StructureTemplateManagerAccessor.java](com/finndog/moogs_structures/mixins/structures/StructureTemplateManagerAccessor.java) |
| `NamespaceResourceManagerAccessor` | interface | — | Mixin | 0 | 14 | [NamespaceResourceManagerAccessor.java](com/finndog/moogs_structures/mixins/resources/NamespaceResourceManagerAccessor.java) |
| `LocateCommandMixin` | class | — | Mixin | 1 | 69 | [LocateCommandMixin.java](com/finndog/moogs_structures/mixins/structures/LocateCommandMixin.java) |
| `CancellableEventHandler` | class | — | — | 7 | 65 | [CancellableEventHandler.java](com/finndog/moogs_structures/events/base/CancellableEventHandler.java) |
| `ReturnableEventHandler` | class | — | — | 6 | 54 | [ReturnableEventHandler.java](com/finndog/moogs_structures/events/base/ReturnableEventHandler.java) |
| `PieceLimitedJigsawManager` | class | — | — | 5 | 639 | [PieceLimitedJigsawManager.java](com/finndog/moogs_structures/world/structures/pieces/PieceLimitedJigsawManager.java) |
| `EventHandler` | class | — | — | 3 | 24 | [EventHandler.java](com/finndog/moogs_structures/events/base/EventHandler.java) |
| `NeoForgeCustomRegistry` | class | CustomRegistryLookup<T> | — | 7 | 57 | [NeoForgeCustomRegistry.java](com/finndog/moogs_structures/modinit/registry/neoforge/NeoForgeCustomRegistry.java) |
| `MoogsStructuresConditionsRegistry` | class | — | — | 0 | 23 | [MoogsStructuresConditionsRegistry.java](com/finndog/moogs_structures/modinit/MoogsStructuresConditionsRegistry.java) |
| `ResourcefulRegistryChild` | class | ResourcefulRegistry<T> | — | 3 | 28 | [ResourcefulRegistryChild.java](com/finndog/moogs_structures/modinit/registry/ResourcefulRegistryChild.java) |
| `EnumResourcefulRegistryChild` | class | — | — | 2 | 22 | [EnumResourcefulRegistryChild.java](com/finndog/moogs_structures/modinit/registry/EnumResourcefulRegistryChild.java) |
| `ResourcefulRegistry` | interface | — | — | 0 | 18 | [ResourcefulRegistry.java](com/finndog/moogs_structures/modinit/registry/ResourcefulRegistry.java) |
| `CustomRegistry` | interface | — | — | 2 | 52 | [CustomRegistry.java](com/finndog/moogs_structures/modinit/registry/CustomRegistry.java) |
| `RegistryEntries` | class | — | — | 2 | 19 | [RegistryEntries.java](com/finndog/moogs_structures/modinit/registry/RegistryEntries.java) |
| `CustomRegistryLookup` | interface | — | — | 0 | 22 | [CustomRegistryLookup.java](com/finndog/moogs_structures/modinit/registry/CustomRegistryLookup.java) |
| `RegistryEntry` | interface | — | — | 0 | 12 | [RegistryEntry.java](com/finndog/moogs_structures/modinit/registry/RegistryEntry.java) |
| `BasicRegistryEntry` | class | RegistryEntry<T> | — | 2 | 25 | [BasicRegistryEntry.java](com/finndog/moogs_structures/modinit/registry/BasicRegistryEntry.java) |
| `NeoForgeResourcefulRegistry` | class | ResourcefulRegistry<T> | — | 3 | 40 | [NeoForgeResourcefulRegistry.java](com/finndog/moogs_structures/modinit/registry/neoforge/NeoForgeResourcefulRegistry.java) |
| `NeoForgeRegistryEntry` | class | RegistryEntry<T> | — | 2 | 24 | [NeoForgeRegistryEntry.java](com/finndog/moogs_structures/modinit/registry/neoforge/NeoForgeRegistryEntry.java) |
| `StructureProcessorAccessor` | interface | — | Mixin | 0 | 13 | [StructureProcessorAccessor.java](com/finndog/moogs_structures/mixins/structures/StructureProcessorAccessor.java) |
| `PoolElementStructurePieceAccessor` | interface | — | Mixin | 0 | 19 | [PoolElementStructurePieceAccessor.java](com/finndog/moogs_structures/mixins/structures/PoolElementStructurePieceAccessor.java) |
| `TemplateAccessor` | interface | — | Mixin | 0 | 14 | [TemplateAccessor.java](com/finndog/moogs_structures/mixins/structures/TemplateAccessor.java) |
| `StructurePieceAccessor` | interface | — | Mixin | 0 | 13 | [StructurePieceAccessor.java](com/finndog/moogs_structures/mixins/structures/StructurePieceAccessor.java) |
| `StructurePoolAccessor` | interface | — | Mixin | 0 | 36 | [StructurePoolAccessor.java](com/finndog/moogs_structures/mixins/structures/StructurePoolAccessor.java) |
| `ListPoolElementAccessor` | interface | — | Mixin | 0 | 14 | [ListPoolElementAccessor.java](com/finndog/moogs_structures/mixins/structures/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | interface | — | Mixin | 0 | 25 | [SinglePoolElementAccessor.java](com/finndog/moogs_structures/mixins/structures/SinglePoolElementAccessor.java) |
| `StructurePoolMixin` | class | — | Mixin | 1 | 22 | [StructurePoolMixin.java](com/finndog/moogs_structures/mixins/neoforge/structures/StructurePoolMixin.java) |
| `IRegistryPlatform` | interface | — | — | 0 | 17 | [IRegistryPlatform.java](com/finndog/moogs_structures/platform/IRegistryPlatform.java) |
| `StructurePieceCountsManager` | class | SimpleJsonResourceReloadListener | — | 8 | 143 | [StructurePieceCountsManager.java](com/finndog/moogs_structures/misc/structurepiececounter/StructurePieceCountsManager.java) |
| `GeneralUtils` | class | — | — | 15 | 267 | [GeneralUtils.java](com/finndog/moogs_structures/utils/GeneralUtils.java) |
| `OpenSimplex2F` | class | — | — | 13 | 760 | [OpenSimplex2F.java](com/finndog/moogs_structures/utils/OpenSimplex2F.java) |
| `DebugCommand` | class | — | — | 5 | 51 | [DebugCommand.java](com/finndog/moogs_structures/commands/DebugCommand.java) |
| `ServerGoingToStartEvent` | record | — | — | 1 | 13 | [ServerGoingToStartEvent.java](com/finndog/moogs_structures/events/lifecycle/ServerGoingToStartEvent.java) |
| `ServerGoingToStopEvent` | class | — | — | 0 | 12 | [ServerGoingToStopEvent.java](com/finndog/moogs_structures/events/lifecycle/ServerGoingToStopEvent.java) |
| `TagsUpdatedEvent` | record | — | — | 0 | 9 | [TagsUpdatedEvent.java](com/finndog/moogs_structures/events/lifecycle/TagsUpdatedEvent.java) |
| `SetupEvent` | record | — | — | 1 | 13 | [SetupEvent.java](com/finndog/moogs_structures/events/lifecycle/SetupEvent.java) |
| `FinalSetupEvent` | record | — | — | 1 | 13 | [FinalSetupEvent.java](com/finndog/moogs_structures/events/lifecycle/FinalSetupEvent.java) |
| `RegisterReloadListenerEvent` | record | — | — | 1 | 15 | [RegisterReloadListenerEvent.java](com/finndog/moogs_structures/events/lifecycle/RegisterReloadListenerEvent.java) |
| `GenericJigsawStructure` | class | Structure | — | 8 | 466 | [GenericJigsawStructure.java](com/finndog/moogs_structures/world/structures/GenericJigsawStructure.java) |
| `AdvancedRandomSpread` | class | RandomSpreadStructurePlacement | — | 10 | 192 | [AdvancedRandomSpread.java](com/finndog/moogs_structures/world/structures/placements/AdvancedRandomSpread.java) |
| `VersionResolver` | class | Comparable<VersionResolver.VersionNu | — | 14 | 195 | [VersionResolver.java](com/finndog/moogs_structures/utils/VersionResolver.java) |
| `BoxOctree` | class | — | — | 8 | 222 | [BoxOctree.java](com/finndog/moogs_structures/utils/BoxOctree.java) |
| `AsyncLocator` | class | — | — | 12 | 116 | [AsyncLocator.java](com/finndog/moogs_structures/utils/AsyncLocator.java) |
| `MoogsStructuresCommon` | class | — | — | 5 | 49 | [MoogsStructuresCommon.java](com/finndog/moogs_structures/MoogsStructuresCommon.java) |
| `ResourcefulRegistriesImpl` | class | IRegistryPlatform | — | 7 | 73 | [ResourcefulRegistriesImpl.java](com/finndog/moogs_structures/modinit/registry/neoforge/ResourcefulRegistriesImpl.java) |
| `VersionAwareSinglePoolElement` | class | SinglePoolElement | — | 8 | 139 | [VersionAwareSinglePoolElement.java](com/finndog/moogs_structures/world/structures/pieces/VersionAwareSinglePoolElement.java) |
| `MirroringSingleJigsawPiece` | class | SinglePoolElement | — | 8 | 141 | [MirroringSingleJigsawPiece.java](com/finndog/moogs_structures/world/structures/pieces/MirroringSingleJigsawPiece.java) |
| `StructureNbtUpdater` | class | DataProvider | — | 5 | 99 | [StructureNbtUpdater.java](com/finndog/moogs_structures/datagen/StructureNbtUpdater.java) |
| `MoogsStructuresStructurePlacementType` | class | — | — | 0 | 18 | [MoogsStructuresStructurePlacementType.java](com/finndog/moogs_structures/modinit/MoogsStructuresStructurePlacementType.java) |
| `MoogsStructuresStructurePieces` | class | — | — | 0 | 30 | [MoogsStructuresStructurePieces.java](com/finndog/moogs_structures/modinit/MoogsStructuresStructurePieces.java) |
| `MoogsStructuresTags` | class | — | — | 1 | 16 | [MoogsStructuresTags.java](com/finndog/moogs_structures/modinit/MoogsStructuresTags.java) |
| `MoogsStructuresPlacements` | class | — | — | 0 | 26 | [MoogsStructuresPlacements.java](com/finndog/moogs_structures/modinit/MoogsStructuresPlacements.java) |
| `MoogsStructuresProcessors` | class | — | — | 0 | 13 | [MoogsStructuresProcessors.java](com/finndog/moogs_structures/modinit/MoogsStructuresProcessors.java) |
| `MoogsStructuresStructures` | class | — | — | 0 | 20 | [MoogsStructuresStructures.java](com/finndog/moogs_structures/modinit/MoogsStructuresStructures.java) |
| `ResourcefulRegistries` | class | — | — | 3 | 24 | [ResourcefulRegistries.java](com/finndog/moogs_structures/modinit/registry/ResourcefulRegistries.java) |
| `Services` | class | — | — | 1 | 12 | [Services.java](com/finndog/moogs_structures/platform/Services.java) |
| `StructurePieceCountsObj` | class | — | — | 0 | 33 | [StructurePieceCountsObj.java](com/finndog/moogs_structures/misc/structurepiececounter/StructurePieceCountsObj.java) |
| `StructurePieceCountsAdditionsMerger` | class | — | — | 1 | 34 | [StructurePieceCountsAdditionsMerger.java](com/finndog/moogs_structures/misc/structurepiececounter/StructurePieceCountsAdditionsMerger.java) |
| `GenericNetherJigsawStructure` | class | GenericJigsawStructure | — | 4 | 166 | [GenericNetherJigsawStructure.java](com/finndog/moogs_structures/world/structures/GenericNetherJigsawStructure.java) |
| `LegacyOceanBottomSinglePoolElement` | class | SinglePoolElement | — | 3 | 53 | [LegacyOceanBottomSinglePoolElement.java](com/finndog/moogs_structures/world/structures/pieces/LegacyOceanBottomSinglePoolElement.java) |
| `YRangeAllowance` | class | — | — | 0 | 23 | [YRangeAllowance.java](com/finndog/moogs_structures/world/structures/codecs/YRangeAllowance.java) |
| `UnlimitedCountPlacement` | class | RepeatingPlacement | — | 4 | 38 | [UnlimitedCountPlacement.java](com/finndog/moogs_structures/world/placements/UnlimitedCountPlacement.java) |
| `SnapToLowerNonAirPlacement` | class | PlacementModifier | — | 3 | 36 | [SnapToLowerNonAirPlacement.java](com/finndog/moogs_structures/world/placements/SnapToLowerNonAirPlacement.java) |
| `MinusEightPlacement` | class | PlacementModifier | — | 3 | 28 | [MinusEightPlacement.java](com/finndog/moogs_structures/world/placements/MinusEightPlacement.java) |
| `DebugFlags` | class | — | — | 3 | 23 | [DebugFlags.java](com/finndog/moogs_structures/utils/DebugFlags.java) |
| `PlatformHooksImpl` | class | — | — | 2 | 18 | [PlatformHooksImpl.java](com/finndog/moogs_structures/utils/neoforge/PlatformHooksImpl.java) |
