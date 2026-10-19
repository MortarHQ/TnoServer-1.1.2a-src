# Explorer's Compass（`explorerscompass`）

> jar：`[探险者指南针]ExplorersCompass.jar` ｜ 版本：`1.21.1-3.4.0-neoforge` ｜ 作者：ChaosTheDude
> 规模：**33 个类 / 10 个包 / 3118 行** ｜ 事件处理类 2 ｜ Mixin 0 ｜ 注册/管理类 3

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.chaosthedude.explorerscompass.gui` | 6 | `ExplorersCompassScreen`、`StructureSearchList`、`TransparentTextField` |
| `com.chaosthedude.explorerscompass.sorting` | 6 | `XpLevelsSorting`、`ISorting`、`NameSorting` |
| `com.chaosthedude.explorerscompass.util` | 5 | `StructureUtils`、`ItemUtils`、`RenderUtils` |
| `com.chaosthedude.explorerscompass.worker` | 5 | `SearchWorkerManager`、`StructureSearchWorker`、`ConcentricRingsSearchWorker` |
| `com.chaosthedude.explorerscompass.network` | 4 | `TeleportPacket`、`SearchPacket`、`SyncPacket` |
| `com.chaosthedude.explorerscompass.client` | 3 | `ExplorersCompassClient`、`ExplorersCompassOverlay`、`OverlaySide` |
| `com.chaosthedude.explorerscompass.registry` | 1 | `ExplorersCompassRegistry` |
| `com.chaosthedude.explorerscompass` | 1 | `ExplorersCompass` |
| `com.chaosthedude.explorerscompass.items` | 1 | `ExplorersCompassItem` |
| `com.chaosthedude.explorerscompass.config` | 1 | `ConfigHandler` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 33）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ExplorersCompassRegistry` | class | — | EventBusSubscriber | 1 | 34 | [ExplorersCompassRegistry.java](com/chaosthedude/explorerscompass/registry/ExplorersCompassRegistry.java) |
| `ExplorersCompassClient` | class | — | EventBusSubscriber | 6 | 113 | [ExplorersCompassClient.java](com/chaosthedude/explorerscompass/client/ExplorersCompassClient.java) |
| `ExplorersCompass` | class | — | Mod | 3 | 117 | [ExplorersCompass.java](com/chaosthedude/explorerscompass/ExplorersCompass.java) |
| `ExplorersCompassItem` | class | Item | — | 34 | 355 | [ExplorersCompassItem.java](com/chaosthedude/explorerscompass/items/ExplorersCompassItem.java) |
| `ConfigHandler` | class | — | — | 0 | 76 | [ConfigHandler.java](com/chaosthedude/explorerscompass/config/ConfigHandler.java) |
| `StructureUtils` | class | — | — | 23 | 285 | [StructureUtils.java](com/chaosthedude/explorerscompass/util/StructureUtils.java) |
| `SearchWorkerManager` | class | — | — | 5 | 113 | [SearchWorkerManager.java](com/chaosthedude/explorerscompass/worker/SearchWorkerManager.java) |
| `ExplorersCompassScreen` | class | Screen | OnlyIn | 13 | 233 | [ExplorersCompassScreen.java](com/chaosthedude/explorerscompass/gui/ExplorersCompassScreen.java) |
| `TeleportPacket` | record | CustomPacketPayload | — | 7 | 94 | [TeleportPacket.java](com/chaosthedude/explorerscompass/network/TeleportPacket.java) |
| `SearchPacket` | record | CustomPacketPayload | — | 4 | 68 | [SearchPacket.java](com/chaosthedude/explorerscompass/network/SearchPacket.java) |
| `SyncPacket` | record | CustomPacketPayload | — | 4 | 138 | [SyncPacket.java](com/chaosthedude/explorerscompass/network/SyncPacket.java) |
| `SearchForNextPacket` | record | CustomPacketPayload | — | 4 | 42 | [SearchForNextPacket.java](com/chaosthedude/explorerscompass/network/SearchForNextPacket.java) |
| `ItemUtils` | class | — | — | 2 | 21 | [ItemUtils.java](com/chaosthedude/explorerscompass/util/ItemUtils.java) |
| `StructureSearchWorker` | class | IWorker | — | 12 | 212 | [StructureSearchWorker.java](com/chaosthedude/explorerscompass/worker/StructureSearchWorker.java) |
| `StructureSearchList` | class | ObjectSelectionList | OnlyIn | 10 | 124 | [StructureSearchList.java](com/chaosthedude/explorerscompass/gui/StructureSearchList.java) |
| `TransparentTextField` | class | EditBox | OnlyIn | 11 | 178 | [TransparentTextField.java](com/chaosthedude/explorerscompass/gui/TransparentTextField.java) |
| `StructureSearchEntry` | class | Entry | OnlyIn | 8 | 139 | [StructureSearchEntry.java](com/chaosthedude/explorerscompass/gui/StructureSearchEntry.java) |
| `RenderUtils` | class | — | OnlyIn | 4 | 50 | [RenderUtils.java](com/chaosthedude/explorerscompass/util/RenderUtils.java) |
| `PlayerUtils` | class | — | — | 3 | 35 | [PlayerUtils.java](com/chaosthedude/explorerscompass/util/PlayerUtils.java) |
| `ConcentricRingsSearchWorker` | class | StructureSearchWorker | — | 5 | 119 | [ConcentricRingsSearchWorker.java](com/chaosthedude/explorerscompass/worker/ConcentricRingsSearchWorker.java) |
| `TransparentButton` | class | Button | OnlyIn | 1 | 34 | [TransparentButton.java](com/chaosthedude/explorerscompass/gui/TransparentButton.java) |
| `GuiWrapper` | class | — | — | 1 | 16 | [GuiWrapper.java](com/chaosthedude/explorerscompass/gui/GuiWrapper.java) |
| `CompassState` | enum | — | — | 2 | 29 | [CompassState.java](com/chaosthedude/explorerscompass/util/CompassState.java) |
| `GenericSearchWorker` | class | StructureSearchWorker | — | 3 | 110 | [GenericSearchWorker.java](com/chaosthedude/explorerscompass/worker/GenericSearchWorker.java) |
| `RandomSpreadSearchWorker` | class | StructureSearchWorker | — | 4 | 121 | [RandomSpreadSearchWorker.java](com/chaosthedude/explorerscompass/worker/RandomSpreadSearchWorker.java) |
| `ExplorersCompassOverlay` | class | Layer | OnlyIn | 1 | 76 | [ExplorersCompassOverlay.java](com/chaosthedude/explorerscompass/client/ExplorersCompassOverlay.java) |
| `OverlaySide` | enum | — | — | 1 | 11 | [OverlaySide.java](com/chaosthedude/explorerscompass/client/OverlaySide.java) |
| `XpLevelsSorting` | class | ISorting | OnlyIn | 4 | 31 | [XpLevelsSorting.java](com/chaosthedude/explorerscompass/sorting/XpLevelsSorting.java) |
| `ISorting` | interface | — | OnlyIn | 0 | 18 | [ISorting.java](com/chaosthedude/explorerscompass/sorting/ISorting.java) |
| `NameSorting` | class | ISorting | OnlyIn | 4 | 31 | [NameSorting.java](com/chaosthedude/explorerscompass/sorting/NameSorting.java) |
| `GroupSorting` | class | ISorting | OnlyIn | 4 | 31 | [GroupSorting.java](com/chaosthedude/explorerscompass/sorting/GroupSorting.java) |
| `SourceSorting` | class | ISorting | OnlyIn | 4 | 31 | [SourceSorting.java](com/chaosthedude/explorerscompass/sorting/SourceSorting.java) |
| `DimensionSorting` | class | ISorting | OnlyIn | 4 | 33 | [DimensionSorting.java](com/chaosthedude/explorerscompass/sorting/DimensionSorting.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ExplorersCompassClient` | 2 | `com.chaosthedude.explorerscompass.client` | [ExplorersCompassClient.java](com/chaosthedude/explorerscompass/client/ExplorersCompassClient.java) |
| `ExplorersCompassRegistry` | 1 | `com.chaosthedude.explorerscompass.registry` | [ExplorersCompassRegistry.java](com/chaosthedude/explorerscompass/registry/ExplorersCompassRegistry.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigHandler` | — | [ConfigHandler.java](com/chaosthedude/explorerscompass/config/ConfigHandler.java) |

## 全部类（33）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ExplorersCompassRegistry` | class | — | EventBusSubscriber | 1 | 34 | [ExplorersCompassRegistry.java](com/chaosthedude/explorerscompass/registry/ExplorersCompassRegistry.java) |
| `ExplorersCompassClient` | class | — | EventBusSubscriber | 6 | 113 | [ExplorersCompassClient.java](com/chaosthedude/explorerscompass/client/ExplorersCompassClient.java) |
| `ExplorersCompass` | class | — | Mod | 3 | 117 | [ExplorersCompass.java](com/chaosthedude/explorerscompass/ExplorersCompass.java) |
| `ExplorersCompassItem` | class | Item | — | 34 | 355 | [ExplorersCompassItem.java](com/chaosthedude/explorerscompass/items/ExplorersCompassItem.java) |
| `ConfigHandler` | class | — | — | 0 | 76 | [ConfigHandler.java](com/chaosthedude/explorerscompass/config/ConfigHandler.java) |
| `StructureUtils` | class | — | — | 23 | 285 | [StructureUtils.java](com/chaosthedude/explorerscompass/util/StructureUtils.java) |
| `SearchWorkerManager` | class | — | — | 5 | 113 | [SearchWorkerManager.java](com/chaosthedude/explorerscompass/worker/SearchWorkerManager.java) |
| `ExplorersCompassScreen` | class | Screen | OnlyIn | 13 | 233 | [ExplorersCompassScreen.java](com/chaosthedude/explorerscompass/gui/ExplorersCompassScreen.java) |
| `TeleportPacket` | record | CustomPacketPayload | — | 7 | 94 | [TeleportPacket.java](com/chaosthedude/explorerscompass/network/TeleportPacket.java) |
| `SearchPacket` | record | CustomPacketPayload | — | 4 | 68 | [SearchPacket.java](com/chaosthedude/explorerscompass/network/SearchPacket.java) |
| `SyncPacket` | record | CustomPacketPayload | — | 4 | 138 | [SyncPacket.java](com/chaosthedude/explorerscompass/network/SyncPacket.java) |
| `SearchForNextPacket` | record | CustomPacketPayload | — | 4 | 42 | [SearchForNextPacket.java](com/chaosthedude/explorerscompass/network/SearchForNextPacket.java) |
| `ItemUtils` | class | — | — | 2 | 21 | [ItemUtils.java](com/chaosthedude/explorerscompass/util/ItemUtils.java) |
| `StructureSearchWorker` | class | IWorker | — | 12 | 212 | [StructureSearchWorker.java](com/chaosthedude/explorerscompass/worker/StructureSearchWorker.java) |
| `StructureSearchList` | class | ObjectSelectionList | OnlyIn | 10 | 124 | [StructureSearchList.java](com/chaosthedude/explorerscompass/gui/StructureSearchList.java) |
| `TransparentTextField` | class | EditBox | OnlyIn | 11 | 178 | [TransparentTextField.java](com/chaosthedude/explorerscompass/gui/TransparentTextField.java) |
| `StructureSearchEntry` | class | Entry | OnlyIn | 8 | 139 | [StructureSearchEntry.java](com/chaosthedude/explorerscompass/gui/StructureSearchEntry.java) |
| `RenderUtils` | class | — | OnlyIn | 4 | 50 | [RenderUtils.java](com/chaosthedude/explorerscompass/util/RenderUtils.java) |
| `PlayerUtils` | class | — | — | 3 | 35 | [PlayerUtils.java](com/chaosthedude/explorerscompass/util/PlayerUtils.java) |
| `ConcentricRingsSearchWorker` | class | StructureSearchWorker | — | 5 | 119 | [ConcentricRingsSearchWorker.java](com/chaosthedude/explorerscompass/worker/ConcentricRingsSearchWorker.java) |
| `TransparentButton` | class | Button | OnlyIn | 1 | 34 | [TransparentButton.java](com/chaosthedude/explorerscompass/gui/TransparentButton.java) |
| `GuiWrapper` | class | — | — | 1 | 16 | [GuiWrapper.java](com/chaosthedude/explorerscompass/gui/GuiWrapper.java) |
| `CompassState` | enum | — | — | 2 | 29 | [CompassState.java](com/chaosthedude/explorerscompass/util/CompassState.java) |
| `GenericSearchWorker` | class | StructureSearchWorker | — | 3 | 110 | [GenericSearchWorker.java](com/chaosthedude/explorerscompass/worker/GenericSearchWorker.java) |
| `RandomSpreadSearchWorker` | class | StructureSearchWorker | — | 4 | 121 | [RandomSpreadSearchWorker.java](com/chaosthedude/explorerscompass/worker/RandomSpreadSearchWorker.java) |
| `ExplorersCompassOverlay` | class | Layer | OnlyIn | 1 | 76 | [ExplorersCompassOverlay.java](com/chaosthedude/explorerscompass/client/ExplorersCompassOverlay.java) |
| `OverlaySide` | enum | — | — | 1 | 11 | [OverlaySide.java](com/chaosthedude/explorerscompass/client/OverlaySide.java) |
| `XpLevelsSorting` | class | ISorting | OnlyIn | 4 | 31 | [XpLevelsSorting.java](com/chaosthedude/explorerscompass/sorting/XpLevelsSorting.java) |
| `ISorting` | interface | — | OnlyIn | 0 | 18 | [ISorting.java](com/chaosthedude/explorerscompass/sorting/ISorting.java) |
| `NameSorting` | class | ISorting | OnlyIn | 4 | 31 | [NameSorting.java](com/chaosthedude/explorerscompass/sorting/NameSorting.java) |
| `GroupSorting` | class | ISorting | OnlyIn | 4 | 31 | [GroupSorting.java](com/chaosthedude/explorerscompass/sorting/GroupSorting.java) |
| `SourceSorting` | class | ISorting | OnlyIn | 4 | 31 | [SourceSorting.java](com/chaosthedude/explorerscompass/sorting/SourceSorting.java) |
| `DimensionSorting` | class | ISorting | OnlyIn | 4 | 33 | [DimensionSorting.java](com/chaosthedude/explorerscompass/sorting/DimensionSorting.java) |
