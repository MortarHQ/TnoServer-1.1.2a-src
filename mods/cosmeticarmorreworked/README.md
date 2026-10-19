# CosmeticArmorReworked（`cosmeticarmorreworked`）

> jar：`[时装盔甲重置版]cosmeticarmorreworked.jar` ｜ 版本：`1.21.1-v1-neoforge` ｜ 作者：zlainsama
> 规模：**27 个类 / 11 个包 / 2065 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 5

**依赖**：`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `lain.mods.cos.impl.client.gui` | 6 | `GuiCosArmorInventory`、`InventoryScreenAccess`、`IShiftingWidget` |
| `lain.mods.cos.impl.network.payload` | 6 | `PayloadSetSkinArmor`、`PayloadOpenCosArmorInventory`、`PayloadSetHiddenFlags` |
| `lain.mods.cos.impl.client` | 4 | `InventoryManagerClient`、`PlayerRenderHandler`、`GuiHandler` |
| `lain.mods.cos.impl` | 3 | `InventoryManager`、`ModConfigs`、`ModObjects` |
| `lain.mods.cos.impl.inventory` | 2 | `ContainerCosArmor`、`InventoryCosArmor` |
| `lain.mods.cos.init.neoforge` | 1 | `NeoForgeCosmeticArmorReworked` |
| `lain.mods.cos.api.inventory` | 1 | `CAStacksBase` |
| `lain.mods.cos.impl.network` | 1 | `ModPayloads` |
| `lain.mods.cos.api` | 1 | `CosArmorAPI` |
| `lain.mods.cos.api.event` | 1 | `CosArmorDeathDrops` |
| `lain.mods.cos.init` | 1 | `ModConstants` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 27）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `InventoryManager` | class | — | — | 30 | 351 | [InventoryManager.java](lain/mods/cos/impl/InventoryManager.java) |
| `NeoForgeCosmeticArmorReworked` | class | — | Mod | 5 | 67 | [NeoForgeCosmeticArmorReworked.java](lain/mods/cos/init/neoforge/NeoForgeCosmeticArmorReworked.java) |
| `InventoryManagerClient` | class | InventoryManager | — | 5 | 43 | [InventoryManagerClient.java](lain/mods/cos/impl/client/InventoryManagerClient.java) |
| `PlayerRenderHandler` | enum | — | — | 11 | 160 | [PlayerRenderHandler.java](lain/mods/cos/impl/client/PlayerRenderHandler.java) |
| `ContainerCosArmor` | class | RecipeBookMenu | — | 24 | 279 | [ContainerCosArmor.java](lain/mods/cos/impl/inventory/ContainerCosArmor.java) |
| `GuiHandler` | enum | — | — | 4 | 134 | [GuiHandler.java](lain/mods/cos/impl/client/GuiHandler.java) |
| `KeyHandler` | enum | — | — | 3 | 36 | [KeyHandler.java](lain/mods/cos/impl/client/KeyHandler.java) |
| `ModConfigs` | class | — | — | 1 | 88 | [ModConfigs.java](lain/mods/cos/impl/ModConfigs.java) |
| `InventoryCosArmor` | class | CAStacksBase | — | 21 | 158 | [InventoryCosArmor.java](lain/mods/cos/impl/inventory/InventoryCosArmor.java) |
| `GuiCosArmorInventory` | class | EffectRenderingInventoryScreen | — | 15 | 172 | [GuiCosArmorInventory.java](lain/mods/cos/impl/client/gui/GuiCosArmorInventory.java) |
| `InventoryScreenAccess` | class | — | — | 5 | 66 | [InventoryScreenAccess.java](lain/mods/cos/impl/client/gui/InventoryScreenAccess.java) |
| `CAStacksBase` | class | ItemStackHandler | — | 8 | 126 | [CAStacksBase.java](lain/mods/cos/api/inventory/CAStacksBase.java) |
| `ModObjects` | class | — | — | 1 | 19 | [ModObjects.java](lain/mods/cos/impl/ModObjects.java) |
| `ModPayloads` | class | — | — | 1 | 47 | [ModPayloads.java](lain/mods/cos/impl/network/ModPayloads.java) |
| `PayloadSetSkinArmor` | record | CustomPacketPayload | — | 3 | 26 | [PayloadSetSkinArmor.java](lain/mods/cos/impl/network/payload/PayloadSetSkinArmor.java) |
| `PayloadOpenCosArmorInventory` | record | CustomPacketPayload | — | 3 | 26 | [PayloadOpenCosArmorInventory.java](lain/mods/cos/impl/network/payload/PayloadOpenCosArmorInventory.java) |
| `PayloadSetHiddenFlags` | record | CustomPacketPayload | — | 3 | 29 | [PayloadSetHiddenFlags.java](lain/mods/cos/impl/network/payload/PayloadSetHiddenFlags.java) |
| `PayloadSyncCosArmor` | record | CustomPacketPayload | — | 3 | 40 | [PayloadSyncCosArmor.java](lain/mods/cos/impl/network/payload/PayloadSyncCosArmor.java) |
| `PayloadSyncHiddenFlags` | record | CustomPacketPayload | — | 3 | 37 | [PayloadSyncHiddenFlags.java](lain/mods/cos/impl/network/payload/PayloadSyncHiddenFlags.java) |
| `PayloadOpenNormalInventory` | record | CustomPacketPayload | — | 3 | 26 | [PayloadOpenNormalInventory.java](lain/mods/cos/impl/network/payload/PayloadOpenNormalInventory.java) |
| `IShiftingWidget` | interface | — | — | 0 | 6 | [IShiftingWidget.java](lain/mods/cos/impl/client/gui/IShiftingWidget.java) |
| `GuiCosArmorButton` | class | Button | — | 3 | 46 | [GuiCosArmorButton.java](lain/mods/cos/impl/client/gui/GuiCosArmorButton.java) |
| `GuiCosArmorToggleButton` | class | Button | — | 2 | 30 | [GuiCosArmorToggleButton.java](lain/mods/cos/impl/client/gui/GuiCosArmorToggleButton.java) |
| `ICreativeInvWidget` | interface | — | — | 0 | 6 | [ICreativeInvWidget.java](lain/mods/cos/impl/client/gui/ICreativeInvWidget.java) |
| `CosArmorAPI` | class | — | — | 2 | 16 | [CosArmorAPI.java](lain/mods/cos/api/CosArmorAPI.java) |
| `CosArmorDeathDrops` | class | Event | — | 2 | 25 | [CosArmorDeathDrops.java](lain/mods/cos/api/event/CosArmorDeathDrops.java) |
| `ModConstants` | class | — | — | 0 | 6 | [ModConstants.java](lain/mods/cos/init/ModConstants.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ModConfigs` | — | [ModConfigs.java](lain/mods/cos/impl/ModConfigs.java) |

## 全部类（27）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `InventoryManager` | class | — | — | 30 | 351 | [InventoryManager.java](lain/mods/cos/impl/InventoryManager.java) |
| `NeoForgeCosmeticArmorReworked` | class | — | Mod | 5 | 67 | [NeoForgeCosmeticArmorReworked.java](lain/mods/cos/init/neoforge/NeoForgeCosmeticArmorReworked.java) |
| `InventoryManagerClient` | class | InventoryManager | — | 5 | 43 | [InventoryManagerClient.java](lain/mods/cos/impl/client/InventoryManagerClient.java) |
| `PlayerRenderHandler` | enum | — | — | 11 | 160 | [PlayerRenderHandler.java](lain/mods/cos/impl/client/PlayerRenderHandler.java) |
| `ContainerCosArmor` | class | RecipeBookMenu | — | 24 | 279 | [ContainerCosArmor.java](lain/mods/cos/impl/inventory/ContainerCosArmor.java) |
| `GuiHandler` | enum | — | — | 4 | 134 | [GuiHandler.java](lain/mods/cos/impl/client/GuiHandler.java) |
| `KeyHandler` | enum | — | — | 3 | 36 | [KeyHandler.java](lain/mods/cos/impl/client/KeyHandler.java) |
| `ModConfigs` | class | — | — | 1 | 88 | [ModConfigs.java](lain/mods/cos/impl/ModConfigs.java) |
| `InventoryCosArmor` | class | CAStacksBase | — | 21 | 158 | [InventoryCosArmor.java](lain/mods/cos/impl/inventory/InventoryCosArmor.java) |
| `GuiCosArmorInventory` | class | EffectRenderingInventoryScreen | — | 15 | 172 | [GuiCosArmorInventory.java](lain/mods/cos/impl/client/gui/GuiCosArmorInventory.java) |
| `InventoryScreenAccess` | class | — | — | 5 | 66 | [InventoryScreenAccess.java](lain/mods/cos/impl/client/gui/InventoryScreenAccess.java) |
| `CAStacksBase` | class | ItemStackHandler | — | 8 | 126 | [CAStacksBase.java](lain/mods/cos/api/inventory/CAStacksBase.java) |
| `ModObjects` | class | — | — | 1 | 19 | [ModObjects.java](lain/mods/cos/impl/ModObjects.java) |
| `ModPayloads` | class | — | — | 1 | 47 | [ModPayloads.java](lain/mods/cos/impl/network/ModPayloads.java) |
| `PayloadSetSkinArmor` | record | CustomPacketPayload | — | 3 | 26 | [PayloadSetSkinArmor.java](lain/mods/cos/impl/network/payload/PayloadSetSkinArmor.java) |
| `PayloadOpenCosArmorInventory` | record | CustomPacketPayload | — | 3 | 26 | [PayloadOpenCosArmorInventory.java](lain/mods/cos/impl/network/payload/PayloadOpenCosArmorInventory.java) |
| `PayloadSetHiddenFlags` | record | CustomPacketPayload | — | 3 | 29 | [PayloadSetHiddenFlags.java](lain/mods/cos/impl/network/payload/PayloadSetHiddenFlags.java) |
| `PayloadSyncCosArmor` | record | CustomPacketPayload | — | 3 | 40 | [PayloadSyncCosArmor.java](lain/mods/cos/impl/network/payload/PayloadSyncCosArmor.java) |
| `PayloadSyncHiddenFlags` | record | CustomPacketPayload | — | 3 | 37 | [PayloadSyncHiddenFlags.java](lain/mods/cos/impl/network/payload/PayloadSyncHiddenFlags.java) |
| `PayloadOpenNormalInventory` | record | CustomPacketPayload | — | 3 | 26 | [PayloadOpenNormalInventory.java](lain/mods/cos/impl/network/payload/PayloadOpenNormalInventory.java) |
| `IShiftingWidget` | interface | — | — | 0 | 6 | [IShiftingWidget.java](lain/mods/cos/impl/client/gui/IShiftingWidget.java) |
| `GuiCosArmorButton` | class | Button | — | 3 | 46 | [GuiCosArmorButton.java](lain/mods/cos/impl/client/gui/GuiCosArmorButton.java) |
| `GuiCosArmorToggleButton` | class | Button | — | 2 | 30 | [GuiCosArmorToggleButton.java](lain/mods/cos/impl/client/gui/GuiCosArmorToggleButton.java) |
| `ICreativeInvWidget` | interface | — | — | 0 | 6 | [ICreativeInvWidget.java](lain/mods/cos/impl/client/gui/ICreativeInvWidget.java) |
| `CosArmorAPI` | class | — | — | 2 | 16 | [CosArmorAPI.java](lain/mods/cos/api/CosArmorAPI.java) |
| `CosArmorDeathDrops` | class | Event | — | 2 | 25 | [CosArmorDeathDrops.java](lain/mods/cos/api/event/CosArmorDeathDrops.java) |
| `ModConstants` | class | — | — | 0 | 6 | [ModConstants.java](lain/mods/cos/init/ModConstants.java) |
