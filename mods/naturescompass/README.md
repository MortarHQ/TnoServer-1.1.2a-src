# Nature's Compass（`naturescompass`）

> jar：`[自然罗盘]NaturesCompass.jar` ｜ 版本：`1.21.1-3.4.0-neoforge` ｜ 作者：ChaosTheDude
> 规模：**30 个类 / 9 个包 / 2663 行** ｜ 事件处理类 3 ｜ Mixin 0 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.chaosthedude.naturescompass.sorting` | 7 | `ISorting`、`NameSorting`、`TagsSorting` |
| `com.chaosthedude.naturescompass.util` | 6 | `BiomeUtils`、`BiomeSearchWorker`、`ItemUtils` |
| `com.chaosthedude.naturescompass.gui` | 6 | `NaturesCompassScreen`、`TransparentTextField`、`BiomeSearchList` |
| `com.chaosthedude.naturescompass.network` | 4 | `TeleportPacket`、`SearchPacket`、`SyncPacket` |
| `com.chaosthedude.naturescompass.client` | 3 | `NaturesCompassClient`、`NaturesCompassOverlay`、`OverlaySide` |
| `com.chaosthedude.naturescompass.registry` | 1 | `NaturesCompassRegistry` |
| `com.chaosthedude.naturescompass` | 1 | `NaturesCompass` |
| `com.chaosthedude.naturescompass.items` | 1 | `NaturesCompassItem` |
| `com.chaosthedude.naturescompass.config` | 1 | `ConfigHandler` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NaturesCompassRegistry` | class | — | EventBusSubscriber | 1 | 33 | [NaturesCompassRegistry.java](com/chaosthedude/naturescompass/registry/NaturesCompassRegistry.java) |
| `NaturesCompassClient` | class | — | EventBusSubscriber | 6 | 113 | [NaturesCompassClient.java](com/chaosthedude/naturescompass/client/NaturesCompassClient.java) |
| `NaturesCompass` | class | — | Mod | 4 | 123 | [NaturesCompass.java](com/chaosthedude/naturescompass/NaturesCompass.java) |
| `NaturesCompassItem` | class | Item | — | 31 | 292 | [NaturesCompassItem.java](com/chaosthedude/naturescompass/items/NaturesCompassItem.java) |
| `ConfigHandler` | class | — | — | 0 | 79 | [ConfigHandler.java](com/chaosthedude/naturescompass/config/ConfigHandler.java) |
| `BiomeUtils` | class | — | — | 23 | 320 | [BiomeUtils.java](com/chaosthedude/naturescompass/util/BiomeUtils.java) |
| `NaturesCompassScreen` | class | Screen | OnlyIn | 14 | 238 | [NaturesCompassScreen.java](com/chaosthedude/naturescompass/gui/NaturesCompassScreen.java) |
| `TeleportPacket` | record | CustomPacketPayload | — | 7 | 94 | [TeleportPacket.java](com/chaosthedude/naturescompass/network/TeleportPacket.java) |
| `SearchPacket` | record | CustomPacketPayload | — | 4 | 44 | [SearchPacket.java](com/chaosthedude/naturescompass/network/SearchPacket.java) |
| `SyncPacket` | record | CustomPacketPayload | — | 4 | 95 | [SyncPacket.java](com/chaosthedude/naturescompass/network/SyncPacket.java) |
| `SearchForNextPacket` | record | CustomPacketPayload | — | 4 | 41 | [SearchForNextPacket.java](com/chaosthedude/naturescompass/network/SearchForNextPacket.java) |
| `BiomeSearchWorker` | class | IWorker | — | 10 | 276 | [BiomeSearchWorker.java](com/chaosthedude/naturescompass/util/BiomeSearchWorker.java) |
| `ItemUtils` | class | — | — | 2 | 21 | [ItemUtils.java](com/chaosthedude/naturescompass/util/ItemUtils.java) |
| `TransparentTextField` | class | EditBox | OnlyIn | 11 | 178 | [TransparentTextField.java](com/chaosthedude/naturescompass/gui/TransparentTextField.java) |
| `BiomeSearchList` | class | ObjectSelectionList | OnlyIn | 8 | 109 | [BiomeSearchList.java](com/chaosthedude/naturescompass/gui/BiomeSearchList.java) |
| `BiomeSearchEntry` | class | Entry | OnlyIn | 5 | 142 | [BiomeSearchEntry.java](com/chaosthedude/naturescompass/gui/BiomeSearchEntry.java) |
| `RenderUtils` | class | — | OnlyIn | 4 | 50 | [RenderUtils.java](com/chaosthedude/naturescompass/util/RenderUtils.java) |
| `PlayerUtils` | class | — | — | 4 | 44 | [PlayerUtils.java](com/chaosthedude/naturescompass/util/PlayerUtils.java) |
| `TransparentButton` | class | Button | OnlyIn | 1 | 34 | [TransparentButton.java](com/chaosthedude/naturescompass/gui/TransparentButton.java) |
| `GuiWrapper` | class | — | — | 1 | 15 | [GuiWrapper.java](com/chaosthedude/naturescompass/gui/GuiWrapper.java) |
| `CompassState` | enum | — | — | 2 | 29 | [CompassState.java](com/chaosthedude/naturescompass/util/CompassState.java) |
| `NaturesCompassOverlay` | class | Layer | OnlyIn | 1 | 73 | [NaturesCompassOverlay.java](com/chaosthedude/naturescompass/client/NaturesCompassOverlay.java) |
| `OverlaySide` | enum | — | — | 1 | 11 | [OverlaySide.java](com/chaosthedude/naturescompass/client/OverlaySide.java) |
| `ISorting` | interface | — | OnlyIn | 0 | 18 | [ISorting.java](com/chaosthedude/naturescompass/sorting/ISorting.java) |
| `NameSorting` | class | ISorting<String> | — | 4 | 30 | [NameSorting.java](com/chaosthedude/naturescompass/sorting/NameSorting.java) |
| `TagsSorting` | class | ISorting<String> | — | 4 | 30 | [TagsSorting.java](com/chaosthedude/naturescompass/sorting/TagsSorting.java) |
| `TemperatureSorting` | class | ISorting<Float> | OnlyIn | 4 | 29 | [TemperatureSorting.java](com/chaosthedude/naturescompass/sorting/TemperatureSorting.java) |
| `SourceSorting` | class | ISorting<String> | OnlyIn | 4 | 33 | [SourceSorting.java](com/chaosthedude/naturescompass/sorting/SourceSorting.java) |
| `RainfallSorting` | class | ISorting<Float> | OnlyIn | 4 | 29 | [RainfallSorting.java](com/chaosthedude/naturescompass/sorting/RainfallSorting.java) |
| `DimensionSorting` | class | ISorting<String> | — | 4 | 40 | [DimensionSorting.java](com/chaosthedude/naturescompass/sorting/DimensionSorting.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NaturesCompassClient` | 2 | `com.chaosthedude.naturescompass.client` | [NaturesCompassClient.java](com/chaosthedude/naturescompass/client/NaturesCompassClient.java) |
| `NaturesCompassRegistry` | 1 | `com.chaosthedude.naturescompass.registry` | [NaturesCompassRegistry.java](com/chaosthedude/naturescompass/registry/NaturesCompassRegistry.java) |
| `NaturesCompass` | 1 | `com.chaosthedude.naturescompass` | [NaturesCompass.java](com/chaosthedude/naturescompass/NaturesCompass.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigHandler` | — | [ConfigHandler.java](com/chaosthedude/naturescompass/config/ConfigHandler.java) |

## 全部类（30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NaturesCompassRegistry` | class | — | EventBusSubscriber | 1 | 33 | [NaturesCompassRegistry.java](com/chaosthedude/naturescompass/registry/NaturesCompassRegistry.java) |
| `NaturesCompassClient` | class | — | EventBusSubscriber | 6 | 113 | [NaturesCompassClient.java](com/chaosthedude/naturescompass/client/NaturesCompassClient.java) |
| `NaturesCompass` | class | — | Mod | 4 | 123 | [NaturesCompass.java](com/chaosthedude/naturescompass/NaturesCompass.java) |
| `NaturesCompassItem` | class | Item | — | 31 | 292 | [NaturesCompassItem.java](com/chaosthedude/naturescompass/items/NaturesCompassItem.java) |
| `ConfigHandler` | class | — | — | 0 | 79 | [ConfigHandler.java](com/chaosthedude/naturescompass/config/ConfigHandler.java) |
| `BiomeUtils` | class | — | — | 23 | 320 | [BiomeUtils.java](com/chaosthedude/naturescompass/util/BiomeUtils.java) |
| `NaturesCompassScreen` | class | Screen | OnlyIn | 14 | 238 | [NaturesCompassScreen.java](com/chaosthedude/naturescompass/gui/NaturesCompassScreen.java) |
| `TeleportPacket` | record | CustomPacketPayload | — | 7 | 94 | [TeleportPacket.java](com/chaosthedude/naturescompass/network/TeleportPacket.java) |
| `SearchPacket` | record | CustomPacketPayload | — | 4 | 44 | [SearchPacket.java](com/chaosthedude/naturescompass/network/SearchPacket.java) |
| `SyncPacket` | record | CustomPacketPayload | — | 4 | 95 | [SyncPacket.java](com/chaosthedude/naturescompass/network/SyncPacket.java) |
| `SearchForNextPacket` | record | CustomPacketPayload | — | 4 | 41 | [SearchForNextPacket.java](com/chaosthedude/naturescompass/network/SearchForNextPacket.java) |
| `BiomeSearchWorker` | class | IWorker | — | 10 | 276 | [BiomeSearchWorker.java](com/chaosthedude/naturescompass/util/BiomeSearchWorker.java) |
| `ItemUtils` | class | — | — | 2 | 21 | [ItemUtils.java](com/chaosthedude/naturescompass/util/ItemUtils.java) |
| `TransparentTextField` | class | EditBox | OnlyIn | 11 | 178 | [TransparentTextField.java](com/chaosthedude/naturescompass/gui/TransparentTextField.java) |
| `BiomeSearchList` | class | ObjectSelectionList | OnlyIn | 8 | 109 | [BiomeSearchList.java](com/chaosthedude/naturescompass/gui/BiomeSearchList.java) |
| `BiomeSearchEntry` | class | Entry | OnlyIn | 5 | 142 | [BiomeSearchEntry.java](com/chaosthedude/naturescompass/gui/BiomeSearchEntry.java) |
| `RenderUtils` | class | — | OnlyIn | 4 | 50 | [RenderUtils.java](com/chaosthedude/naturescompass/util/RenderUtils.java) |
| `PlayerUtils` | class | — | — | 4 | 44 | [PlayerUtils.java](com/chaosthedude/naturescompass/util/PlayerUtils.java) |
| `TransparentButton` | class | Button | OnlyIn | 1 | 34 | [TransparentButton.java](com/chaosthedude/naturescompass/gui/TransparentButton.java) |
| `GuiWrapper` | class | — | — | 1 | 15 | [GuiWrapper.java](com/chaosthedude/naturescompass/gui/GuiWrapper.java) |
| `CompassState` | enum | — | — | 2 | 29 | [CompassState.java](com/chaosthedude/naturescompass/util/CompassState.java) |
| `NaturesCompassOverlay` | class | Layer | OnlyIn | 1 | 73 | [NaturesCompassOverlay.java](com/chaosthedude/naturescompass/client/NaturesCompassOverlay.java) |
| `OverlaySide` | enum | — | — | 1 | 11 | [OverlaySide.java](com/chaosthedude/naturescompass/client/OverlaySide.java) |
| `ISorting` | interface | — | OnlyIn | 0 | 18 | [ISorting.java](com/chaosthedude/naturescompass/sorting/ISorting.java) |
| `NameSorting` | class | ISorting<String> | — | 4 | 30 | [NameSorting.java](com/chaosthedude/naturescompass/sorting/NameSorting.java) |
| `TagsSorting` | class | ISorting<String> | — | 4 | 30 | [TagsSorting.java](com/chaosthedude/naturescompass/sorting/TagsSorting.java) |
| `TemperatureSorting` | class | ISorting<Float> | OnlyIn | 4 | 29 | [TemperatureSorting.java](com/chaosthedude/naturescompass/sorting/TemperatureSorting.java) |
| `SourceSorting` | class | ISorting<String> | OnlyIn | 4 | 33 | [SourceSorting.java](com/chaosthedude/naturescompass/sorting/SourceSorting.java) |
| `RainfallSorting` | class | ISorting<Float> | OnlyIn | 4 | 29 | [RainfallSorting.java](com/chaosthedude/naturescompass/sorting/RainfallSorting.java) |
| `DimensionSorting` | class | ISorting<String> | — | 4 | 40 | [DimensionSorting.java](com/chaosthedude/naturescompass/sorting/DimensionSorting.java) |
