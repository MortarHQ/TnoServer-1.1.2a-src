# TrashSlot（`trashslot`）

> jar：`[垃圾槽]trashslot.jar` ｜ 版本：`21.1.9` ｜ 作者：BlayTheNinth
> 规模：**38 个类 / 10 个包 / 2091 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`balm`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.blay09.mods.trashslot.api` | 9 | `ItemUntrashedEvent`、`ItemTrashedEvent`、`TrashSlotEmptiedEvent` |
| `net.blay09.mods.trashslot.client` | 8 | `NeoForgeTrashSlotClient`、`TrashSlotGuiHandler`、`LayoutManager` |
| `net.blay09.mods.trashslot` | 7 | `NeoForgeTrashSlot`、`TrashHelper`、`TrashSlotSaveState` |
| `net.blay09.mods.trashslot.network` | 4 | `ModNetworking`、`MessageDeleteFromSlot`、`MessageTrashSlotClick` |
| `net.blay09.mods.trashslot.client.deletion` | 3 | `DefaultDeletionProvider`、`CreativeDeletionProvider`、`DeletionProvider` |
| `net.blay09.mods.trashslot.config` | 2 | `TrashSlotConfigData`、`TrashSlotConfig` |
| `net.blay09.mods.trashslot.client.gui.layout` | 2 | `SimpleGuiContainerLayout`、`ChestContainerLayout` |
| `net.blay09.mods.trashslot.client.gui` | 1 | `TrashSlotComponent` |
| `net.blay09.mods.trashslot.tag` | 1 | `ModItemTags` |
| `net.blay09.mods.trashslot.compat` | 1 | `JEIAddon` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 38）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeTrashSlotClient` | class | — | Mod | 0 | 16 | [NeoForgeTrashSlotClient.java](net/blay09/mods/trashslot/client/NeoForgeTrashSlotClient.java) |
| `TrashSlotConfigData` | class | BalmConfigData | Config | 0 | 26 | [TrashSlotConfigData.java](net/blay09/mods/trashslot/config/TrashSlotConfigData.java) |
| `NeoForgeTrashSlot` | class | — | Mod | 1 | 21 | [NeoForgeTrashSlot.java](net/blay09/mods/trashslot/NeoForgeTrashSlot.java) |
| `TrashSlotGuiHandler` | class | — | — | 13 | 347 | [TrashSlotGuiHandler.java](net/blay09/mods/trashslot/client/TrashSlotGuiHandler.java) |
| `ItemUntrashedEvent` | class | BalmEvent | — | 2 | 24 | [ItemUntrashedEvent.java](net/blay09/mods/trashslot/api/ItemUntrashedEvent.java) |
| `ItemTrashedEvent` | class | BalmEvent | — | 2 | 36 | [ItemTrashedEvent.java](net/blay09/mods/trashslot/api/ItemTrashedEvent.java) |
| `LayoutManager` | class | — | — | 2 | 21 | [LayoutManager.java](net/blay09/mods/trashslot/client/LayoutManager.java) |
| `TrashSlotConfig` | class | — | — | 4 | 39 | [TrashSlotConfig.java](net/blay09/mods/trashslot/config/TrashSlotConfig.java) |
| `ModNetworking` | class | — | — | 1 | 23 | [ModNetworking.java](net/blay09/mods/trashslot/network/ModNetworking.java) |
| `TrashSlotEmptiedEvent` | class | BalmEvent | — | 2 | 24 | [TrashSlotEmptiedEvent.java](net/blay09/mods/trashslot/api/TrashSlotEmptiedEvent.java) |
| `TrashSlotComponent` | class | — | — | 10 | 283 | [TrashSlotComponent.java](net/blay09/mods/trashslot/client/gui/TrashSlotComponent.java) |
| `SimpleGuiContainerLayout` | class | IGuiContainerLayout | — | 15 | 186 | [SimpleGuiContainerLayout.java](net/blay09/mods/trashslot/client/gui/layout/SimpleGuiContainerLayout.java) |
| `ModItemTags` | class | — | — | 0 | 11 | [ModItemTags.java](net/blay09/mods/trashslot/tag/ModItemTags.java) |
| `TrashSlotClient` | class | — | — | 2 | 31 | [TrashSlotClient.java](net/blay09/mods/trashslot/client/TrashSlotClient.java) |
| `ContainerSettings` | class | — | — | 14 | 79 | [ContainerSettings.java](net/blay09/mods/trashslot/client/ContainerSettings.java) |
| `TrashSlotSlot` | class | Slot | — | 14 | 86 | [TrashSlotSlot.java](net/blay09/mods/trashslot/client/TrashSlotSlot.java) |
| `TrashHelper` | class | — | — | 3 | 35 | [TrashHelper.java](net/blay09/mods/trashslot/TrashHelper.java) |
| `TrashSlotSaveState` | class | — | — | 5 | 82 | [TrashSlotSaveState.java](net/blay09/mods/trashslot/TrashSlotSaveState.java) |
| `MessageDeleteFromSlot` | class | CustomPacketPayload | — | 5 | 97 | [MessageDeleteFromSlot.java](net/blay09/mods/trashslot/network/MessageDeleteFromSlot.java) |
| `SlotRenderStyle` | enum | — | — | 8 | 75 | [SlotRenderStyle.java](net/blay09/mods/trashslot/api/SlotRenderStyle.java) |
| `DefaultDeletionProvider` | class | DeletionProvider | — | 5 | 63 | [DefaultDeletionProvider.java](net/blay09/mods/trashslot/client/deletion/DefaultDeletionProvider.java) |
| `Hints` | class | — | — | 0 | 12 | [Hints.java](net/blay09/mods/trashslot/Hints.java) |
| `PlatformBindings` | class | — | — | 1 | 8 | [PlatformBindings.java](net/blay09/mods/trashslot/PlatformBindings.java) |
| `InternalMethodsImpl` | class | InternalMethods | — | 2 | 23 | [InternalMethodsImpl.java](net/blay09/mods/trashslot/InternalMethodsImpl.java) |
| `TrashSlot` | class | — | — | 2 | 43 | [TrashSlot.java](net/blay09/mods/trashslot/TrashSlot.java) |
| `MessageTrashSlotClick` | class | CustomPacketPayload | — | 4 | 72 | [MessageTrashSlotClick.java](net/blay09/mods/trashslot/network/MessageTrashSlotClick.java) |
| `MessageTrashSlotContent` | class | CustomPacketPayload | — | 4 | 36 | [MessageTrashSlotContent.java](net/blay09/mods/trashslot/network/MessageTrashSlotContent.java) |
| `JEIAddon` | class | IModPlugin | JeiPlugin | 3 | 29 | [JEIAddon.java](net/blay09/mods/trashslot/compat/JEIAddon.java) |
| `IGuiContainerLayout` | interface | — | — | 0 | 28 | [IGuiContainerLayout.java](net/blay09/mods/trashslot/api/IGuiContainerLayout.java) |
| `TrashSlotAPI` | class | — | — | 3 | 20 | [TrashSlotAPI.java](net/blay09/mods/trashslot/api/TrashSlotAPI.java) |
| `Snap` | class | — | — | 3 | 32 | [Snap.java](net/blay09/mods/trashslot/api/Snap.java) |
| `InternalMethods` | interface | — | — | 0 | 10 | [InternalMethods.java](net/blay09/mods/trashslot/api/InternalMethods.java) |
| `ISimpleGuiContainerLayout` | interface | — | — | 0 | 18 | [ISimpleGuiContainerLayout.java](net/blay09/mods/trashslot/api/ISimpleGuiContainerLayout.java) |
| `Hint` | class | — | — | 3 | 45 | [Hint.java](net/blay09/mods/trashslot/client/Hint.java) |
| `ModKeyMappings` | class | — | — | 1 | 35 | [ModKeyMappings.java](net/blay09/mods/trashslot/client/ModKeyMappings.java) |
| `ChestContainerLayout` | class | SimpleGuiContainerLayout | — | 2 | 26 | [ChestContainerLayout.java](net/blay09/mods/trashslot/client/gui/layout/ChestContainerLayout.java) |
| `CreativeDeletionProvider` | class | DefaultDeletionProvider | — | 4 | 30 | [CreativeDeletionProvider.java](net/blay09/mods/trashslot/client/deletion/CreativeDeletionProvider.java) |
| `DeletionProvider` | interface | — | — | 0 | 19 | [DeletionProvider.java](net/blay09/mods/trashslot/client/deletion/DeletionProvider.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TrashSlotConfigData` | Config | [TrashSlotConfigData.java](net/blay09/mods/trashslot/config/TrashSlotConfigData.java) |
| `TrashSlotConfig` | — | [TrashSlotConfig.java](net/blay09/mods/trashslot/config/TrashSlotConfig.java) |

## 全部类（38）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeTrashSlotClient` | class | — | Mod | 0 | 16 | [NeoForgeTrashSlotClient.java](net/blay09/mods/trashslot/client/NeoForgeTrashSlotClient.java) |
| `TrashSlotConfigData` | class | BalmConfigData | Config | 0 | 26 | [TrashSlotConfigData.java](net/blay09/mods/trashslot/config/TrashSlotConfigData.java) |
| `NeoForgeTrashSlot` | class | — | Mod | 1 | 21 | [NeoForgeTrashSlot.java](net/blay09/mods/trashslot/NeoForgeTrashSlot.java) |
| `TrashSlotGuiHandler` | class | — | — | 13 | 347 | [TrashSlotGuiHandler.java](net/blay09/mods/trashslot/client/TrashSlotGuiHandler.java) |
| `ItemUntrashedEvent` | class | BalmEvent | — | 2 | 24 | [ItemUntrashedEvent.java](net/blay09/mods/trashslot/api/ItemUntrashedEvent.java) |
| `ItemTrashedEvent` | class | BalmEvent | — | 2 | 36 | [ItemTrashedEvent.java](net/blay09/mods/trashslot/api/ItemTrashedEvent.java) |
| `LayoutManager` | class | — | — | 2 | 21 | [LayoutManager.java](net/blay09/mods/trashslot/client/LayoutManager.java) |
| `TrashSlotConfig` | class | — | — | 4 | 39 | [TrashSlotConfig.java](net/blay09/mods/trashslot/config/TrashSlotConfig.java) |
| `ModNetworking` | class | — | — | 1 | 23 | [ModNetworking.java](net/blay09/mods/trashslot/network/ModNetworking.java) |
| `TrashSlotEmptiedEvent` | class | BalmEvent | — | 2 | 24 | [TrashSlotEmptiedEvent.java](net/blay09/mods/trashslot/api/TrashSlotEmptiedEvent.java) |
| `TrashSlotComponent` | class | — | — | 10 | 283 | [TrashSlotComponent.java](net/blay09/mods/trashslot/client/gui/TrashSlotComponent.java) |
| `SimpleGuiContainerLayout` | class | IGuiContainerLayout | — | 15 | 186 | [SimpleGuiContainerLayout.java](net/blay09/mods/trashslot/client/gui/layout/SimpleGuiContainerLayout.java) |
| `ModItemTags` | class | — | — | 0 | 11 | [ModItemTags.java](net/blay09/mods/trashslot/tag/ModItemTags.java) |
| `TrashSlotClient` | class | — | — | 2 | 31 | [TrashSlotClient.java](net/blay09/mods/trashslot/client/TrashSlotClient.java) |
| `ContainerSettings` | class | — | — | 14 | 79 | [ContainerSettings.java](net/blay09/mods/trashslot/client/ContainerSettings.java) |
| `TrashSlotSlot` | class | Slot | — | 14 | 86 | [TrashSlotSlot.java](net/blay09/mods/trashslot/client/TrashSlotSlot.java) |
| `TrashHelper` | class | — | — | 3 | 35 | [TrashHelper.java](net/blay09/mods/trashslot/TrashHelper.java) |
| `TrashSlotSaveState` | class | — | — | 5 | 82 | [TrashSlotSaveState.java](net/blay09/mods/trashslot/TrashSlotSaveState.java) |
| `MessageDeleteFromSlot` | class | CustomPacketPayload | — | 5 | 97 | [MessageDeleteFromSlot.java](net/blay09/mods/trashslot/network/MessageDeleteFromSlot.java) |
| `SlotRenderStyle` | enum | — | — | 8 | 75 | [SlotRenderStyle.java](net/blay09/mods/trashslot/api/SlotRenderStyle.java) |
| `DefaultDeletionProvider` | class | DeletionProvider | — | 5 | 63 | [DefaultDeletionProvider.java](net/blay09/mods/trashslot/client/deletion/DefaultDeletionProvider.java) |
| `Hints` | class | — | — | 0 | 12 | [Hints.java](net/blay09/mods/trashslot/Hints.java) |
| `PlatformBindings` | class | — | — | 1 | 8 | [PlatformBindings.java](net/blay09/mods/trashslot/PlatformBindings.java) |
| `InternalMethodsImpl` | class | InternalMethods | — | 2 | 23 | [InternalMethodsImpl.java](net/blay09/mods/trashslot/InternalMethodsImpl.java) |
| `TrashSlot` | class | — | — | 2 | 43 | [TrashSlot.java](net/blay09/mods/trashslot/TrashSlot.java) |
| `MessageTrashSlotClick` | class | CustomPacketPayload | — | 4 | 72 | [MessageTrashSlotClick.java](net/blay09/mods/trashslot/network/MessageTrashSlotClick.java) |
| `MessageTrashSlotContent` | class | CustomPacketPayload | — | 4 | 36 | [MessageTrashSlotContent.java](net/blay09/mods/trashslot/network/MessageTrashSlotContent.java) |
| `JEIAddon` | class | IModPlugin | JeiPlugin | 3 | 29 | [JEIAddon.java](net/blay09/mods/trashslot/compat/JEIAddon.java) |
| `IGuiContainerLayout` | interface | — | — | 0 | 28 | [IGuiContainerLayout.java](net/blay09/mods/trashslot/api/IGuiContainerLayout.java) |
| `TrashSlotAPI` | class | — | — | 3 | 20 | [TrashSlotAPI.java](net/blay09/mods/trashslot/api/TrashSlotAPI.java) |
| `Snap` | class | — | — | 3 | 32 | [Snap.java](net/blay09/mods/trashslot/api/Snap.java) |
| `InternalMethods` | interface | — | — | 0 | 10 | [InternalMethods.java](net/blay09/mods/trashslot/api/InternalMethods.java) |
| `ISimpleGuiContainerLayout` | interface | — | — | 0 | 18 | [ISimpleGuiContainerLayout.java](net/blay09/mods/trashslot/api/ISimpleGuiContainerLayout.java) |
| `Hint` | class | — | — | 3 | 45 | [Hint.java](net/blay09/mods/trashslot/client/Hint.java) |
| `ModKeyMappings` | class | — | — | 1 | 35 | [ModKeyMappings.java](net/blay09/mods/trashslot/client/ModKeyMappings.java) |
| `ChestContainerLayout` | class | SimpleGuiContainerLayout | — | 2 | 26 | [ChestContainerLayout.java](net/blay09/mods/trashslot/client/gui/layout/ChestContainerLayout.java) |
| `CreativeDeletionProvider` | class | DefaultDeletionProvider | — | 4 | 30 | [CreativeDeletionProvider.java](net/blay09/mods/trashslot/client/deletion/CreativeDeletionProvider.java) |
| `DeletionProvider` | interface | — | — | 0 | 19 | [DeletionProvider.java](net/blay09/mods/trashslot/client/deletion/DeletionProvider.java) |
