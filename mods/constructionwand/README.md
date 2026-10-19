# Construction Wands Revived（`constructionwand`）

> jar：`[建筑权杖]ConstructionWandsRevived.jar` ｜ 版本：`4.2.2` ｜ 作者：thetadev, SrNadien
> 规模：**84 个类 / 27 个包 / 7687 行** ｜ 事件处理类 8 ｜ Mixin 0 ｜ 注册/管理类 17

**官方描述**：Processes main resources.

**依赖**：`neoforge`、`minecraft`、`jei`、`guideme`、`ae2`、`refinedstorage`、`sophisticatedcore`、`sophisticatedbackpacks`、`curios`、`ae2wtlib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `nadiendev.constructionwand.data` | 8 | `ModData`、`LanguageGenerator`、`RecipeGenerator` |
| `nadiendev.constructionwand.containers.handlers` | 8 | `HandlerWirelessGrid`、`HandlerCapability`、`HandlerPortableCell` |
| `nadiendev.constructionwand.network` | 7 | `PacketUndoBlocks`、`PacketQueryUndo`、`PacketWandUndo` |
| `nadiendev.constructionwand.basics` | 6 | `CommonEvents`、`ReplacementRegistry`、`ConfigClient` |
| `nadiendev.constructionwand.basics.option` | 6 | `WandUpgradesSelectable`、`WandOptions`、`OptionEnum` |
| `nadiendev.constructionwand.client` | 5 | `ClientHandler`、`ClientEvents`、`KeybindHandler` |
| `nadiendev.constructionwand.api` | 5 | `IContainerHandler`、`IWandAction`、`IWandSupplier` |
| `nadiendev.constructionwand.wand.undo` | 4 | `UndoHistory`、`DestroySnapshot`、`PlaceSnapshot` |
| `nadiendev.constructionwand.items.core` | 4 | `ItemCoreAngel`、`ItemCoreDestruction`、`ItemCore` |
| `nadiendev.constructionwand.containers` | 3 | `ContainerManager`、`ContainerRegistrar`、`ContainerTrace` |
| `nadiendev.constructionwand.wand.action` | 3 | `ActionDestruction`、`ActionConstruction`、`ActionAngel` |
| `nadiendev.constructionwand.items.wand` | 3 | `ItemWand`、`ItemWandInfinity`、`ItemWandBasic` |
| `nadiendev.constructionwand.basics.pool` | 3 | `RandomPool`、`OrderedPool`、`IPool` |
| `nadiendev.constructionwand.events` | 2 | `VoidSackPickupHandler`、`VoidSackDropHandler` |
| `nadiendev.constructionwand.items.containeritems` | 2 | `ItemVoidSack`、`MenuVoidSack` |
| `nadiendev.constructionwand.crafting` | 2 | `ModRecipes`、`RecipeWandUpgrade` |
| `nadiendev.constructionwand.wand` | 2 | `WandItemUseContext`、`WandJob` |
| `nadiendev.constructionwand.wand.supplier` | 2 | `SupplierInventory`、`SupplierRandom` |
| `nadiendev.constructionwand` | 1 | `ConstructionWand` |
| `nadiendev.constructionwand.client.screen` | 1 | `ScreenVoidSack` |
| `nadiendev.constructionwand.items` | 1 | `ModItems` |
| `nadiendev.constructionwand.registry` | 1 | `ModMenuTypes` |
| `nadiendev.constructionwand.integrations.curios` | 1 | `CuriosIntegration` |
| `nadiendev.constructionwand.component` | 1 | `ModDataComponents` |
| `nadiendev.constructionwand.creative` | 1 | `ModCreativeTabs` |
| `nadiendev.constructionwand.client.property` | 1 | `SelectWandCore` |
| `nadiendev.constructionwand.integrations.jei` | 1 | `ConstructionWandJeiPlugin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CommonEvents` | class | — | EventBusSubscriber | 2 | 25 | [CommonEvents.java](nadiendev/constructionwand/basics/CommonEvents.java) |
| `ClientHandler` | class | — | EventBusSubscriber | 3 | 37 | [ClientHandler.java](nadiendev/constructionwand/client/ClientHandler.java) |
| `ModData` | class | — | EventBusSubscriber | 1 | 45 | [ModData.java](nadiendev/constructionwand/data/ModData.java) |
| `ClientEvents` | class | — | — | 8 | 96 | [ClientEvents.java](nadiendev/constructionwand/client/ClientEvents.java) |
| `ConstructionWand` | class | — | Mod | 3 | 77 | [ConstructionWand.java](nadiendev/constructionwand/ConstructionWand.java) |
| `VoidSackPickupHandler` | class | — | — | 2 | 53 | [VoidSackPickupHandler.java](nadiendev/constructionwand/events/VoidSackPickupHandler.java) |
| `VoidSackDropHandler` | class | — | — | 1 | 37 | [VoidSackDropHandler.java](nadiendev/constructionwand/events/VoidSackDropHandler.java) |
| `KeybindHandler` | class | — | — | 3 | 48 | [KeybindHandler.java](nadiendev/constructionwand/client/KeybindHandler.java) |
| `LanguageGenerator` | class | LanguageProvider | — | 15 | 1700 | [LanguageGenerator.java](nadiendev/constructionwand/data/LanguageGenerator.java) |
| `HandlerWirelessGrid` | class | IContainerHandler | — | 11 | 208 | [HandlerWirelessGrid.java](nadiendev/constructionwand/containers/handlers/HandlerWirelessGrid.java) |
| `HandlerCapability` | class | IContainerHandler | — | 3 | 57 | [HandlerCapability.java](nadiendev/constructionwand/containers/handlers/HandlerCapability.java) |
| `ItemVoidSack` | class | Item | — | 21 | 302 | [ItemVoidSack.java](nadiendev/constructionwand/items/containeritems/ItemVoidSack.java) |
| `PacketUndoBlocks` | record | CustomPacketPayload | — | 5 | 66 | [PacketUndoBlocks.java](nadiendev/constructionwand/network/PacketUndoBlocks.java) |
| `ReplacementRegistry` | class | — | — | 3 | 71 | [ReplacementRegistry.java](nadiendev/constructionwand/basics/ReplacementRegistry.java) |
| `ConfigClient` | class | — | — | 0 | 33 | [ConfigClient.java](nadiendev/constructionwand/basics/ConfigClient.java) |
| `WandUtil` | class | — | — | 21 | 258 | [WandUtil.java](nadiendev/constructionwand/basics/WandUtil.java) |
| `RenderBlockPreview` | class | — | — | 3 | 78 | [RenderBlockPreview.java](nadiendev/constructionwand/client/RenderBlockPreview.java) |
| `HandlerPortableCell` | class | IContainerHandler | — | 7 | 121 | [HandlerPortableCell.java](nadiendev/constructionwand/containers/handlers/HandlerPortableCell.java) |
| `HandlerWirelessTerminalAE2WTLib` | class | IContainerHandler | — | 9 | 146 | [HandlerWirelessTerminalAE2WTLib.java](nadiendev/constructionwand/containers/handlers/HandlerWirelessTerminalAE2WTLib.java) |
| `HandlerBundle` | class | IContainerHandler | — | 5 | 56 | [HandlerBundle.java](nadiendev/constructionwand/containers/handlers/HandlerBundle.java) |
| `HandlerWirelessTerminal` | class | IContainerHandler | — | 7 | 137 | [HandlerWirelessTerminal.java](nadiendev/constructionwand/containers/handlers/HandlerWirelessTerminal.java) |
| `HandlerShulkerbox` | class | IContainerHandler | — | 5 | 77 | [HandlerShulkerbox.java](nadiendev/constructionwand/containers/handlers/HandlerShulkerbox.java) |
| `ConfigServer` | class | — | — | 6 | 147 | [ConfigServer.java](nadiendev/constructionwand/basics/ConfigServer.java) |
| `RecipeGenerator` | class | RecipeProvider | — | 5 | 87 | [RecipeGenerator.java](nadiendev/constructionwand/data/RecipeGenerator.java) |
| `IContainerHandler` | interface | — | — | 0 | 18 | [IContainerHandler.java](nadiendev/constructionwand/api/IContainerHandler.java) |
| `ContainerManager` | class | — | — | 4 | 51 | [ContainerManager.java](nadiendev/constructionwand/containers/ContainerManager.java) |
| `HandlerSophisticatedBackpack` | class | IContainerHandler | — | 3 | 75 | [HandlerSophisticatedBackpack.java](nadiendev/constructionwand/containers/handlers/HandlerSophisticatedBackpack.java) |
| `ModRecipes` | class | — | — | 0 | 17 | [ModRecipes.java](nadiendev/constructionwand/crafting/ModRecipes.java) |
| `RecipeWandUpgrade` | class | CustomRecipe | — | 4 | 79 | [RecipeWandUpgrade.java](nadiendev/constructionwand/crafting/RecipeWandUpgrade.java) |
| `INoItemBlock` | interface | — | — | 0 | 5 | [INoItemBlock.java](nadiendev/constructionwand/data/INoItemBlock.java) |
| `PacketQueryUndo` | record | CustomPacketPayload | — | 3 | 42 | [PacketQueryUndo.java](nadiendev/constructionwand/network/PacketQueryUndo.java) |
| `PacketWandUndo` | record | CustomPacketPayload | — | 3 | 52 | [PacketWandUndo.java](nadiendev/constructionwand/network/PacketWandUndo.java) |
| `PacketToggleVoidSackActive` | record | CustomPacketPayload | — | 3 | 62 | [PacketToggleVoidSackActive.java](nadiendev/constructionwand/network/PacketToggleVoidSackActive.java) |
| `PacketWandOption` | record | CustomPacketPayload | — | 3 | 68 | [PacketWandOption.java](nadiendev/constructionwand/network/PacketWandOption.java) |
| `PacketToggleVoidSack` | record | CustomPacketPayload | — | 3 | 39 | [PacketToggleVoidSack.java](nadiendev/constructionwand/network/PacketToggleVoidSack.java) |
| `ActionDestruction` | class | IWandAction | — | 12 | 228 | [ActionDestruction.java](nadiendev/constructionwand/wand/action/ActionDestruction.java) |
| `ItemWand` | class | Item | — | 9 | 156 | [ItemWand.java](nadiendev/constructionwand/items/wand/ItemWand.java) |
| `MenuVoidSack` | class | AbstractContainerMenu | — | 8 | 123 | [MenuVoidSack.java](nadiendev/constructionwand/items/containeritems/MenuVoidSack.java) |
| `ScreenWand` | class | Screen | — | 9 | 91 | [ScreenWand.java](nadiendev/constructionwand/client/ScreenWand.java) |
| `ScreenVoidSack` | class | AbstractContainerScreen | — | 6 | 78 | [ScreenVoidSack.java](nadiendev/constructionwand/client/screen/ScreenVoidSack.java) |
| `WandItemUseContext` | class | BlockPlaceContext | — | 2 | 35 | [WandItemUseContext.java](nadiendev/constructionwand/wand/WandItemUseContext.java) |
| `SupplierInventory` | class | IWandSupplier | — | 8 | 213 | [SupplierInventory.java](nadiendev/constructionwand/wand/supplier/SupplierInventory.java) |
| `UndoHistory` | class | — | — | 10 | 170 | [UndoHistory.java](nadiendev/constructionwand/wand/undo/UndoHistory.java) |
| `WandUpgradesSelectable` | class | WandUpgrades | — | 12 | 92 | [WandUpgradesSelectable.java](nadiendev/constructionwand/basics/option/WandUpgradesSelectable.java) |
| `WandOptions` | class | StringRepresentable | — | 11 | 147 | [WandOptions.java](nadiendev/constructionwand/basics/option/WandOptions.java) |
| `ModItems` | class | — | — | 0 | 31 | [ModItems.java](nadiendev/constructionwand/items/ModItems.java) |
| `ItemCoreAngel` | class | ItemCore | — | 3 | 29 | [ItemCoreAngel.java](nadiendev/constructionwand/items/core/ItemCoreAngel.java) |
| `ItemCoreDestruction` | class | ItemCore | — | 3 | 29 | [ItemCoreDestruction.java](nadiendev/constructionwand/items/core/ItemCoreDestruction.java) |
| `ItemCore` | class | Item | — | 3 | 35 | [ItemCore.java](nadiendev/constructionwand/items/core/ItemCore.java) |
| `ItemWandInfinity` | class | ItemWand | — | 0 | 10 | [ItemWandInfinity.java](nadiendev/constructionwand/items/wand/ItemWandInfinity.java) |
| `ItemWandBasic` | class | ItemWand | — | 3 | 31 | [ItemWandBasic.java](nadiendev/constructionwand/items/wand/ItemWandBasic.java) |
| `ICustomItemModel` | interface | — | — | 0 | 6 | [ICustomItemModel.java](nadiendev/constructionwand/data/ICustomItemModel.java) |
| `ItemModelGenerator` | class | ItemModelProvider | — | 2 | 37 | [ItemModelGenerator.java](nadiendev/constructionwand/data/ItemModelGenerator.java) |
| `ModMenuTypes` | class | — | — | 1 | 25 | [ModMenuTypes.java](nadiendev/constructionwand/registry/ModMenuTypes.java) |
| `CuriosIntegration` | class | — | — | 3 | 93 | [CuriosIntegration.java](nadiendev/constructionwand/integrations/curios/CuriosIntegration.java) |
| `WandJob` | class | — | — | 5 | 118 | [WandJob.java](nadiendev/constructionwand/wand/WandJob.java) |
| `DestroySnapshot` | class | ISnapshot | — | 8 | 92 | [DestroySnapshot.java](nadiendev/constructionwand/wand/undo/DestroySnapshot.java) |
| `PlaceSnapshot` | class | ISnapshot | — | 9 | 132 | [PlaceSnapshot.java](nadiendev/constructionwand/wand/undo/PlaceSnapshot.java) |
| `OptionEnum` | class | IOption<E> | — | 8 | 77 | [OptionEnum.java](nadiendev/constructionwand/basics/option/OptionEnum.java) |
| `OptionBoolean` | class | IOption<Boolean> | — | 8 | 66 | [OptionBoolean.java](nadiendev/constructionwand/basics/option/OptionBoolean.java) |

## 事件处理（`@SubscribeEvent`，8 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEvents` | 4 | `nadiendev.constructionwand.client` | [ClientEvents.java](nadiendev/constructionwand/client/ClientEvents.java) |
| `ClientHandler` | 3 | `nadiendev.constructionwand.client` | [ClientHandler.java](nadiendev/constructionwand/client/ClientHandler.java) |
| `CommonEvents` | 2 | `nadiendev.constructionwand.basics` | [CommonEvents.java](nadiendev/constructionwand/basics/CommonEvents.java) |
| `ModData` | 1 | `nadiendev.constructionwand.data` | [ModData.java](nadiendev/constructionwand/data/ModData.java) |
| `VoidSackPickupHandler` | 1 | `nadiendev.constructionwand.events` | [VoidSackPickupHandler.java](nadiendev/constructionwand/events/VoidSackPickupHandler.java) |
| `VoidSackDropHandler` | 1 | `nadiendev.constructionwand.events` | [VoidSackDropHandler.java](nadiendev/constructionwand/events/VoidSackDropHandler.java) |
| `KeybindHandler` | 1 | `nadiendev.constructionwand.client` | [KeybindHandler.java](nadiendev/constructionwand/client/KeybindHandler.java) |
| `RenderBlockPreview` | 1 | `nadiendev.constructionwand.client` | [RenderBlockPreview.java](nadiendev/constructionwand/client/RenderBlockPreview.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigClient` | — | [ConfigClient.java](nadiendev/constructionwand/basics/ConfigClient.java) |
| `ConfigServer` | — | [ConfigServer.java](nadiendev/constructionwand/basics/ConfigServer.java) |

## 全部类（84）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CommonEvents` | class | — | EventBusSubscriber | 2 | 25 | [CommonEvents.java](nadiendev/constructionwand/basics/CommonEvents.java) |
| `ClientHandler` | class | — | EventBusSubscriber | 3 | 37 | [ClientHandler.java](nadiendev/constructionwand/client/ClientHandler.java) |
| `ModData` | class | — | EventBusSubscriber | 1 | 45 | [ModData.java](nadiendev/constructionwand/data/ModData.java) |
| `ClientEvents` | class | — | — | 8 | 96 | [ClientEvents.java](nadiendev/constructionwand/client/ClientEvents.java) |
| `ConstructionWand` | class | — | Mod | 3 | 77 | [ConstructionWand.java](nadiendev/constructionwand/ConstructionWand.java) |
| `VoidSackPickupHandler` | class | — | — | 2 | 53 | [VoidSackPickupHandler.java](nadiendev/constructionwand/events/VoidSackPickupHandler.java) |
| `VoidSackDropHandler` | class | — | — | 1 | 37 | [VoidSackDropHandler.java](nadiendev/constructionwand/events/VoidSackDropHandler.java) |
| `KeybindHandler` | class | — | — | 3 | 48 | [KeybindHandler.java](nadiendev/constructionwand/client/KeybindHandler.java) |
| `LanguageGenerator` | class | LanguageProvider | — | 15 | 1700 | [LanguageGenerator.java](nadiendev/constructionwand/data/LanguageGenerator.java) |
| `HandlerWirelessGrid` | class | IContainerHandler | — | 11 | 208 | [HandlerWirelessGrid.java](nadiendev/constructionwand/containers/handlers/HandlerWirelessGrid.java) |
| `HandlerCapability` | class | IContainerHandler | — | 3 | 57 | [HandlerCapability.java](nadiendev/constructionwand/containers/handlers/HandlerCapability.java) |
| `ItemVoidSack` | class | Item | — | 21 | 302 | [ItemVoidSack.java](nadiendev/constructionwand/items/containeritems/ItemVoidSack.java) |
| `PacketUndoBlocks` | record | CustomPacketPayload | — | 5 | 66 | [PacketUndoBlocks.java](nadiendev/constructionwand/network/PacketUndoBlocks.java) |
| `ReplacementRegistry` | class | — | — | 3 | 71 | [ReplacementRegistry.java](nadiendev/constructionwand/basics/ReplacementRegistry.java) |
| `ConfigClient` | class | — | — | 0 | 33 | [ConfigClient.java](nadiendev/constructionwand/basics/ConfigClient.java) |
| `WandUtil` | class | — | — | 21 | 258 | [WandUtil.java](nadiendev/constructionwand/basics/WandUtil.java) |
| `RenderBlockPreview` | class | — | — | 3 | 78 | [RenderBlockPreview.java](nadiendev/constructionwand/client/RenderBlockPreview.java) |
| `HandlerPortableCell` | class | IContainerHandler | — | 7 | 121 | [HandlerPortableCell.java](nadiendev/constructionwand/containers/handlers/HandlerPortableCell.java) |
| `HandlerWirelessTerminalAE2WTLib` | class | IContainerHandler | — | 9 | 146 | [HandlerWirelessTerminalAE2WTLib.java](nadiendev/constructionwand/containers/handlers/HandlerWirelessTerminalAE2WTLib.java) |
| `HandlerBundle` | class | IContainerHandler | — | 5 | 56 | [HandlerBundle.java](nadiendev/constructionwand/containers/handlers/HandlerBundle.java) |
| `HandlerWirelessTerminal` | class | IContainerHandler | — | 7 | 137 | [HandlerWirelessTerminal.java](nadiendev/constructionwand/containers/handlers/HandlerWirelessTerminal.java) |
| `HandlerShulkerbox` | class | IContainerHandler | — | 5 | 77 | [HandlerShulkerbox.java](nadiendev/constructionwand/containers/handlers/HandlerShulkerbox.java) |
| `ConfigServer` | class | — | — | 6 | 147 | [ConfigServer.java](nadiendev/constructionwand/basics/ConfigServer.java) |
| `RecipeGenerator` | class | RecipeProvider | — | 5 | 87 | [RecipeGenerator.java](nadiendev/constructionwand/data/RecipeGenerator.java) |
| `IContainerHandler` | interface | — | — | 0 | 18 | [IContainerHandler.java](nadiendev/constructionwand/api/IContainerHandler.java) |
| `ContainerManager` | class | — | — | 4 | 51 | [ContainerManager.java](nadiendev/constructionwand/containers/ContainerManager.java) |
| `HandlerSophisticatedBackpack` | class | IContainerHandler | — | 3 | 75 | [HandlerSophisticatedBackpack.java](nadiendev/constructionwand/containers/handlers/HandlerSophisticatedBackpack.java) |
| `ModRecipes` | class | — | — | 0 | 17 | [ModRecipes.java](nadiendev/constructionwand/crafting/ModRecipes.java) |
| `RecipeWandUpgrade` | class | CustomRecipe | — | 4 | 79 | [RecipeWandUpgrade.java](nadiendev/constructionwand/crafting/RecipeWandUpgrade.java) |
| `INoItemBlock` | interface | — | — | 0 | 5 | [INoItemBlock.java](nadiendev/constructionwand/data/INoItemBlock.java) |
| `PacketQueryUndo` | record | CustomPacketPayload | — | 3 | 42 | [PacketQueryUndo.java](nadiendev/constructionwand/network/PacketQueryUndo.java) |
| `PacketWandUndo` | record | CustomPacketPayload | — | 3 | 52 | [PacketWandUndo.java](nadiendev/constructionwand/network/PacketWandUndo.java) |
| `PacketToggleVoidSackActive` | record | CustomPacketPayload | — | 3 | 62 | [PacketToggleVoidSackActive.java](nadiendev/constructionwand/network/PacketToggleVoidSackActive.java) |
| `PacketWandOption` | record | CustomPacketPayload | — | 3 | 68 | [PacketWandOption.java](nadiendev/constructionwand/network/PacketWandOption.java) |
| `PacketToggleVoidSack` | record | CustomPacketPayload | — | 3 | 39 | [PacketToggleVoidSack.java](nadiendev/constructionwand/network/PacketToggleVoidSack.java) |
| `ActionDestruction` | class | IWandAction | — | 12 | 228 | [ActionDestruction.java](nadiendev/constructionwand/wand/action/ActionDestruction.java) |
| `ItemWand` | class | Item | — | 9 | 156 | [ItemWand.java](nadiendev/constructionwand/items/wand/ItemWand.java) |
| `MenuVoidSack` | class | AbstractContainerMenu | — | 8 | 123 | [MenuVoidSack.java](nadiendev/constructionwand/items/containeritems/MenuVoidSack.java) |
| `ScreenWand` | class | Screen | — | 9 | 91 | [ScreenWand.java](nadiendev/constructionwand/client/ScreenWand.java) |
| `ScreenVoidSack` | class | AbstractContainerScreen | — | 6 | 78 | [ScreenVoidSack.java](nadiendev/constructionwand/client/screen/ScreenVoidSack.java) |
| `WandItemUseContext` | class | BlockPlaceContext | — | 2 | 35 | [WandItemUseContext.java](nadiendev/constructionwand/wand/WandItemUseContext.java) |
| `SupplierInventory` | class | IWandSupplier | — | 8 | 213 | [SupplierInventory.java](nadiendev/constructionwand/wand/supplier/SupplierInventory.java) |
| `UndoHistory` | class | — | — | 10 | 170 | [UndoHistory.java](nadiendev/constructionwand/wand/undo/UndoHistory.java) |
| `WandUpgradesSelectable` | class | WandUpgrades | — | 12 | 92 | [WandUpgradesSelectable.java](nadiendev/constructionwand/basics/option/WandUpgradesSelectable.java) |
| `WandOptions` | class | StringRepresentable | — | 11 | 147 | [WandOptions.java](nadiendev/constructionwand/basics/option/WandOptions.java) |
| `ModItems` | class | — | — | 0 | 31 | [ModItems.java](nadiendev/constructionwand/items/ModItems.java) |
| `ItemCoreAngel` | class | ItemCore | — | 3 | 29 | [ItemCoreAngel.java](nadiendev/constructionwand/items/core/ItemCoreAngel.java) |
| `ItemCoreDestruction` | class | ItemCore | — | 3 | 29 | [ItemCoreDestruction.java](nadiendev/constructionwand/items/core/ItemCoreDestruction.java) |
| `ItemCore` | class | Item | — | 3 | 35 | [ItemCore.java](nadiendev/constructionwand/items/core/ItemCore.java) |
| `ItemWandInfinity` | class | ItemWand | — | 0 | 10 | [ItemWandInfinity.java](nadiendev/constructionwand/items/wand/ItemWandInfinity.java) |
| `ItemWandBasic` | class | ItemWand | — | 3 | 31 | [ItemWandBasic.java](nadiendev/constructionwand/items/wand/ItemWandBasic.java) |
| `ICustomItemModel` | interface | — | — | 0 | 6 | [ICustomItemModel.java](nadiendev/constructionwand/data/ICustomItemModel.java) |
| `ItemModelGenerator` | class | ItemModelProvider | — | 2 | 37 | [ItemModelGenerator.java](nadiendev/constructionwand/data/ItemModelGenerator.java) |
| `ModMenuTypes` | class | — | — | 1 | 25 | [ModMenuTypes.java](nadiendev/constructionwand/registry/ModMenuTypes.java) |
| `CuriosIntegration` | class | — | — | 3 | 93 | [CuriosIntegration.java](nadiendev/constructionwand/integrations/curios/CuriosIntegration.java) |
| `WandJob` | class | — | — | 5 | 118 | [WandJob.java](nadiendev/constructionwand/wand/WandJob.java) |
| `DestroySnapshot` | class | ISnapshot | — | 8 | 92 | [DestroySnapshot.java](nadiendev/constructionwand/wand/undo/DestroySnapshot.java) |
| `PlaceSnapshot` | class | ISnapshot | — | 9 | 132 | [PlaceSnapshot.java](nadiendev/constructionwand/wand/undo/PlaceSnapshot.java) |
| `OptionEnum` | class | IOption<E> | — | 8 | 77 | [OptionEnum.java](nadiendev/constructionwand/basics/option/OptionEnum.java) |
| `OptionBoolean` | class | IOption<Boolean> | — | 8 | 66 | [OptionBoolean.java](nadiendev/constructionwand/basics/option/OptionBoolean.java) |
| `WandUpgrades` | class | — | — | 5 | 83 | [WandUpgrades.java](nadiendev/constructionwand/basics/option/WandUpgrades.java) |
| `RandomPool` | class | IPool<T> | — | 5 | 64 | [RandomPool.java](nadiendev/constructionwand/basics/pool/RandomPool.java) |
| `ModDataComponents` | class | — | — | 0 | 36 | [ModDataComponents.java](nadiendev/constructionwand/component/ModDataComponents.java) |
| `AdvancementGenerator` | class | AdvancementProvider | — | 8 | 110 | [AdvancementGenerator.java](nadiendev/constructionwand/data/AdvancementGenerator.java) |
| `ModMessages` | class | — | — | 3 | 31 | [ModMessages.java](nadiendev/constructionwand/network/ModMessages.java) |
| `ActionConstruction` | class | IWandAction | — | 3 | 132 | [ActionConstruction.java](nadiendev/constructionwand/wand/action/ActionConstruction.java) |
| `ActionAngel` | class | IWandAction | — | 3 | 72 | [ActionAngel.java](nadiendev/constructionwand/wand/action/ActionAngel.java) |
| `SupplierRandom` | class | SupplierInventory | — | 1 | 29 | [SupplierRandom.java](nadiendev/constructionwand/wand/supplier/SupplierRandom.java) |
| `ISnapshot` | interface | — | — | 0 | 25 | [ISnapshot.java](nadiendev/constructionwand/wand/undo/ISnapshot.java) |
| `ModStats` | class | — | — | 0 | 13 | [ModStats.java](nadiendev/constructionwand/basics/ModStats.java) |
| `IOption` | interface | — | — | 0 | 38 | [IOption.java](nadiendev/constructionwand/basics/option/IOption.java) |
| `OrderedPool` | class | IPool<T> | — | 4 | 41 | [OrderedPool.java](nadiendev/constructionwand/basics/pool/OrderedPool.java) |
| `IPool` | interface | — | — | 0 | 15 | [IPool.java](nadiendev/constructionwand/basics/pool/IPool.java) |
| `CoreDefault` | class | IWandCore | — | 3 | 25 | [CoreDefault.java](nadiendev/constructionwand/items/core/CoreDefault.java) |
| `ModCreativeTabs` | class | — | — | 1 | 37 | [ModCreativeTabs.java](nadiendev/constructionwand/creative/ModCreativeTabs.java) |
| `Inp` | class | — | — | 2 | 30 | [Inp.java](nadiendev/constructionwand/data/Inp.java) |
| `IWandAction` | interface | — | — | 0 | 21 | [IWandAction.java](nadiendev/constructionwand/api/IWandAction.java) |
| `IWandSupplier` | interface | — | — | 0 | 20 | [IWandSupplier.java](nadiendev/constructionwand/api/IWandSupplier.java) |
| `IWandCore` | interface | — | — | 0 | 8 | [IWandCore.java](nadiendev/constructionwand/api/IWandCore.java) |
| `IWandUpgrade` | interface | — | — | 0 | 8 | [IWandUpgrade.java](nadiendev/constructionwand/api/IWandUpgrade.java) |
| `SelectWandCore` | class | — | — | 1 | 37 | [SelectWandCore.java](nadiendev/constructionwand/client/property/SelectWandCore.java) |
| `ConstructionWandJeiPlugin` | class | IModPlugin | JeiPlugin | 3 | 92 | [ConstructionWandJeiPlugin.java](nadiendev/constructionwand/integrations/jei/ConstructionWandJeiPlugin.java) |
| `ContainerRegistrar` | class | — | — | 4 | 71 | [ContainerRegistrar.java](nadiendev/constructionwand/containers/ContainerRegistrar.java) |
| `ContainerTrace` | class | — | — | 1 | 35 | [ContainerTrace.java](nadiendev/constructionwand/containers/ContainerTrace.java) |
