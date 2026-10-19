# Tom's Simple Storage Mod（`toms_storage`）

> jar：`[汤姆的简易存储]tomsstorage.jar` ｜ 版本：`2.4.2` ｜ 作者：tom5454
> 规模：**179 个类 / 24 个包 / 16389 行** ｜ 事件处理类 4 ｜ Mixin 0 ｜ 注册/管理类 14

**依赖**：`minecraft`、`sophisticatedstorage`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tom.storagemod.util` | 24 | `PopupMenuManager`、`TerminalSyncManager`、`InventoryConnectorConfigUtil` |
| `com.tom.storagemod.block` | 22 | `FramedInventoryCableBlock`、`InventoryCableConnectorBlock`、`LevelEmitterBlock` |
| `com.tom.storagemod.inventory` | 20 | `PlatformItemHandler`、`BlockFilter`、`MultiInventoryAccess` |
| `com.tom.storagemod.block.entity` | 14 | `StorageTerminalBlockEntity`、`InventoryCableConnectorBlockEntity`、`CraftingTerminalBlockEntity` |
| `com.tom.storagemod.screen` | 13 | `AbstractStorageTerminalScreen`、`InventoryConfiguratorScreen`、`InventoryLinkScreen` |
| `com.tom.storagemod.menu` | 11 | `StorageTerminalMenu`、`CraftingTerminalMenu`、`InventoryConfiguratorMenu` |
| `com.tom.storagemod.item` | 11 | `InventoryConfiguratorItem`、`PaintedBlockItem`、`WirelessTerminalItem` |
| `com.tom.storagemod.inventory.sorting` | 7 | `ComparatorName`、`ComparatorID`、`ComparatorAmount` |
| `com.tom.storagemod` | 6 | `ServerEvents`、`Config`、`StorageMod` |
| `com.tom.storagemod.inventory.filter` | 6 | `SimpleItemFilter`、`ItemPredicate`、`ItemFilter` |
| `com.tom.storagemod.screen.widget` | 5 | `ListWidget`、`ToggleButton`、`TerminalSearchModeButton` |
| `com.tom.storagemod.platform` | 5 | `PlatformBlockEntity`、`PlatformItem`、`NeoForgeMenu` |
| `com.tom.storagemod.jade` | 5 | `InventoryConfigProvider`、`JadeUtil`、`InventoryConnectorProvider` |
| `com.tom.storagemod.components` | 4 | `ConfiguratorComponent`、`SimpleItemFilterComponent`、`WorldPos` |
| `com.tom.storagemod.rei` | 4 | `ReiGhostIngredientHandler`、`ReiTransferHandler`、`REIPlugin` |
| `com.tom.storagemod.client` | 4 | `ClientUtil`、`BakedPaintedModel`、`ViveCraftHelper` |
| `com.tom.storagemod.network` | 3 | `NetworkHandler`、`DataPacket`、`OpenTerminalPacket` |
| `com.tom.storagemod.jei` | 3 | `JEIHandler`、`CraftingTerminalTransferHandler`、`JeiGhostIngredientHandler` |
| `com.tom.storagemod.emi` | 3 | `EmiGhostIngredientHandler`、`EmiTransferHandler`、`EMIPlugin` |
| `com.tom.storagemod.menu.slot` | 3 | `ItemFilterSlot`、`FilterSlot`、`PhantomSlot` |
| `com.tom.storagemod.api` | 2 | `EventHandler`、`MultiblockInventoryAPI` |
| `com.tom.storagemod.polymorph` | 2 | `PolymorphHelper`、`PolymorphTerminalWidget` |
| `com.tom.storagemod.top` | 1 | `TheOneProbeHandler` |
| `com.tom.storagemod.inventory.compat` | 1 | `SophisticatedDoubleBlocks` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ServerEvents` | class | — | EventBusSubscriber | 2 | 36 | [ServerEvents.java](com/tom/storagemod/ServerEvents.java) |
| `NetworkHandler` | class | — | — | 7 | 68 | [NetworkHandler.java](com/tom/storagemod/network/NetworkHandler.java) |
| `AbstractStorageTerminalScreen` | class | PlatformContainerScreen | — | 37 | 891 | [AbstractStorageTerminalScreen.java](com/tom/storagemod/screen/AbstractStorageTerminalScreen.java) |
| `Config` | class | — | — | 6 | 220 | [Config.java](com/tom/storagemod/Config.java) |
| `StorageMod` | class | — | Mod | 4 | 81 | [StorageMod.java](com/tom/storagemod/StorageMod.java) |
| `StorageTerminalBlockEntity` | class | PlatformBlockEntity | — | 25 | 270 | [StorageTerminalBlockEntity.java](com/tom/storagemod/block/entity/StorageTerminalBlockEntity.java) |
| `StorageTerminalMenu` | class | RecipeBookMenu | — | 34 | 444 | [StorageTerminalMenu.java](com/tom/storagemod/menu/StorageTerminalMenu.java) |
| `PopupMenuManager` | class | PopupMenuManager.PopupElement | — | 14 | 210 | [PopupMenuManager.java](com/tom/storagemod/util/PopupMenuManager.java) |
| `StorageModClient` | class | — | — | 13 | 146 | [StorageModClient.java](com/tom/storagemod/StorageModClient.java) |
| `PlatformItemHandler` | class | IItemHandler | — | 8 | 47 | [PlatformItemHandler.java](com/tom/storagemod/inventory/PlatformItemHandler.java) |
| `InventoryCableConnectorBlockEntity` | class | PaintedBlockEntity | — | 18 | 247 | [InventoryCableConnectorBlockEntity.java](com/tom/storagemod/block/entity/InventoryCableConnectorBlockEntity.java) |
| `CraftingTerminalBlockEntity` | class | StorageTerminalBlockEntity | — | 17 | 252 | [CraftingTerminalBlockEntity.java](com/tom/storagemod/block/entity/CraftingTerminalBlockEntity.java) |
| `ConfiguratorComponent` | record | — | — | 23 | 139 | [ConfiguratorComponent.java](com/tom/storagemod/components/ConfiguratorComponent.java) |
| `EventHandler` | class | — | — | 3 | 39 | [EventHandler.java](com/tom/storagemod/api/EventHandler.java) |
| `InventoryConfiguratorItem` | class | PlatformItem | — | 10 | 187 | [InventoryConfiguratorItem.java](com/tom/storagemod/item/InventoryConfiguratorItem.java) |
| `TerminalSyncManager` | class | — | — | 12 | 286 | [TerminalSyncManager.java](com/tom/storagemod/util/TerminalSyncManager.java) |
| `BlockFilter` | class | IFilter | — | 22 | 205 | [BlockFilter.java](com/tom/storagemod/inventory/BlockFilter.java) |
| `InventoryConfiguratorScreen` | class | PlatformContainerScreen | — | 6 | 140 | [InventoryConfiguratorScreen.java](com/tom/storagemod/screen/InventoryConfiguratorScreen.java) |
| `FramedInventoryCableBlock` | class | BaseEntityBlock | — | 20 | 214 | [FramedInventoryCableBlock.java](com/tom/storagemod/block/FramedInventoryCableBlock.java) |
| `InventoryCableConnectorBlock` | class | BaseEntityBlock | — | 23 | 328 | [InventoryCableConnectorBlock.java](com/tom/storagemod/block/InventoryCableConnectorBlock.java) |
| `InventoryConnectorBlockEntity` | class | PlatformBlockEntity | — | 14 | 223 | [InventoryConnectorBlockEntity.java](com/tom/storagemod/block/entity/InventoryConnectorBlockEntity.java) |
| `CraftingTerminalMenu` | class | StorageTerminalMenu | — | 23 | 273 | [CraftingTerminalMenu.java](com/tom/storagemod/menu/CraftingTerminalMenu.java) |
| `JEIHandler` | class | IModPlugin | JeiPlugin | 10 | 92 | [JEIHandler.java](com/tom/storagemod/jei/JEIHandler.java) |
| `TheOneProbeHandler` | class | Function<ITheOneProbe | — | 5 | 90 | [TheOneProbeHandler.java](com/tom/storagemod/top/TheOneProbeHandler.java) |
| `InventoryLinkScreen` | class | PlatformContainerScreen | — | 17 | 255 | [InventoryLinkScreen.java](com/tom/storagemod/screen/InventoryLinkScreen.java) |
| `TagItemFilterScreen` | class | AbstractFilteredScreen | — | 12 | 163 | [TagItemFilterScreen.java](com/tom/storagemod/screen/TagItemFilterScreen.java) |
| `CraftingTerminalScreen` | class | AbstractStorageTerminalScreen | — | 17 | 256 | [CraftingTerminalScreen.java](com/tom/storagemod/screen/CraftingTerminalScreen.java) |
| `ReiGhostIngredientHandler` | class | DraggableStackVisitor<AbstractFilter | — | 6 | 101 | [ReiGhostIngredientHandler.java](com/tom/storagemod/rei/ReiGhostIngredientHandler.java) |
| `LevelEmitterBlock` | class | BaseEntityBlock | — | 21 | 195 | [LevelEmitterBlock.java](com/tom/storagemod/block/LevelEmitterBlock.java) |
| `FilingCabinetBlockEntity` | class | BlockEntity | — | 10 | 79 | [FilingCabinetBlockEntity.java](com/tom/storagemod/block/entity/FilingCabinetBlockEntity.java) |
| `LevelEmitterBlockEntity` | class | PlatformBlockEntity | — | 13 | 143 | [LevelEmitterBlockEntity.java](com/tom/storagemod/block/entity/LevelEmitterBlockEntity.java) |
| `OpenCrateBlockEntity` | class | BlockEntity | — | 13 | 123 | [OpenCrateBlockEntity.java](com/tom/storagemod/block/entity/OpenCrateBlockEntity.java) |
| `PaintedBlockEntity` | class | PlatformBlockEntity | — | 11 | 98 | [PaintedBlockEntity.java](com/tom/storagemod/block/entity/PaintedBlockEntity.java) |
| `InventoryConfiguratorMenu` | class | AbstractContainerMenu | — | 4 | 125 | [InventoryConfiguratorMenu.java](com/tom/storagemod/menu/InventoryConfiguratorMenu.java) |
| `EmiGhostIngredientHandler` | class | EmiDragDropHandler<Screen> | — | 6 | 103 | [EmiGhostIngredientHandler.java](com/tom/storagemod/emi/EmiGhostIngredientHandler.java) |
| `EmiTransferHandler` | class | StandardRecipeHandler<CraftingTermin | — | 8 | 143 | [EmiTransferHandler.java](com/tom/storagemod/emi/EmiTransferHandler.java) |
| `ClientUtil` | class | — | — | 11 | 283 | [ClientUtil.java](com/tom/storagemod/client/ClientUtil.java) |
| `CraftingTerminalTransferHandler` | class | IRecipeTransferHandler<C | — | 8 | 151 | [CraftingTerminalTransferHandler.java](com/tom/storagemod/jei/CraftingTerminalTransferHandler.java) |
| `MultiInventoryAccess` | class | ArrayList | — | 21 | 345 | [MultiInventoryAccess.java](com/tom/storagemod/inventory/MultiInventoryAccess.java) |
| `ItemFilterScreen` | class | AbstractFilteredScreen | — | 5 | 68 | [ItemFilterScreen.java](com/tom/storagemod/screen/ItemFilterScreen.java) |
| `ListWidget` | class | AbstractWidget | — | 22 | 203 | [ListWidget.java](com/tom/storagemod/screen/widget/ListWidget.java) |
| `ReiTransferHandler` | class | TransferHandler | — | 2 | 120 | [ReiTransferHandler.java](com/tom/storagemod/rei/ReiTransferHandler.java) |
| `InventoryCableBlock` | class | PipeBlock | — | 15 | 189 | [InventoryCableBlock.java](com/tom/storagemod/block/InventoryCableBlock.java) |
| `AbstractStorageTerminalBlock` | class | BaseEntityBlock | — | 13 | 203 | [AbstractStorageTerminalBlock.java](com/tom/storagemod/block/AbstractStorageTerminalBlock.java) |
| `BasicInventoryHopperBlockEntity` | class | AbstractInventoryHopperBlockEntity | — | 6 | 164 | [BasicInventoryHopperBlockEntity.java](com/tom/storagemod/block/entity/BasicInventoryHopperBlockEntity.java) |
| `TagItemFilterMenu` | class | AbstractFilteredMenu | — | 6 | 129 | [TagItemFilterMenu.java](com/tom/storagemod/menu/TagItemFilterMenu.java) |
| `InventoryConnectorConfigUtil` | class | — | — | 1 | 29 | [InventoryConnectorConfigUtil.java](com/tom/storagemod/util/InventoryConnectorConfigUtil.java) |
| `JeiGhostIngredientHandler` | class | IGhostIngredientHandler<AbstractFilt | — | 4 | 67 | [JeiGhostIngredientHandler.java](com/tom/storagemod/jei/JeiGhostIngredientHandler.java) |
| `InventoryCableNetwork` | class | — | — | 5 | 85 | [InventoryCableNetwork.java](com/tom/storagemod/inventory/InventoryCableNetwork.java) |
| `NetworkInventory` | class | — | — | 5 | 66 | [NetworkInventory.java](com/tom/storagemod/inventory/NetworkInventory.java) |
| `StoredItemStack` | class | — | — | 10 | 152 | [StoredItemStack.java](com/tom/storagemod/inventory/StoredItemStack.java) |
| `PlatformFilteredInventoryAccess` | class | IInventoryAccess | — | 24 | 152 | [PlatformFilteredInventoryAccess.java](com/tom/storagemod/inventory/PlatformFilteredInventoryAccess.java) |
| `InventoryChangeTracker` | class | — | — | 16 | 276 | [InventoryChangeTracker.java](com/tom/storagemod/inventory/InventoryChangeTracker.java) |
| `DataPacket` | record | CustomPacketPayload | — | 2 | 27 | [DataPacket.java](com/tom/storagemod/network/DataPacket.java) |
| `RecipeBookButton` | class | ImageButton | — | 0 | 12 | [RecipeBookButton.java](com/tom/storagemod/screen/RecipeBookButton.java) |
| `InventoryProxyBlock` | class | BaseEntityBlock | — | 12 | 120 | [InventoryProxyBlock.java](com/tom/storagemod/block/InventoryProxyBlock.java) |
| `IConfiguratorHighlight` | interface | — | — | 0 | 13 | [IConfiguratorHighlight.java](com/tom/storagemod/block/IConfiguratorHighlight.java) |
| `InventoryConnectorBlock` | class | BaseEntityBlock | — | 10 | 105 | [InventoryConnectorBlock.java](com/tom/storagemod/block/InventoryConnectorBlock.java) |
| `FilingCabinetBlock` | class | BaseEntityBlock | — | 10 | 98 | [FilingCabinetBlock.java](com/tom/storagemod/block/FilingCabinetBlock.java) |
| `AbstractInventoryHopperBlock` | class | BaseEntityBlock | — | 13 | 119 | [AbstractInventoryHopperBlock.java](com/tom/storagemod/block/AbstractInventoryHopperBlock.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `StorageModClient` | 3 | `com.tom.storagemod` | [StorageModClient.java](com/tom/storagemod/StorageModClient.java) |
| `ServerEvents` | 2 | `com.tom.storagemod` | [ServerEvents.java](com/tom/storagemod/ServerEvents.java) |
| `Config` | 2 | `com.tom.storagemod` | [Config.java](com/tom/storagemod/Config.java) |
| `NetworkHandler` | 1 | `com.tom.storagemod.network` | [NetworkHandler.java](com/tom/storagemod/network/NetworkHandler.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](com/tom/storagemod/Config.java) |
| `ConfiguratorComponent` | — | [ConfiguratorComponent.java](com/tom/storagemod/components/ConfiguratorComponent.java) |
| `InventoryConfiguratorItem` | — | [InventoryConfiguratorItem.java](com/tom/storagemod/item/InventoryConfiguratorItem.java) |
| `InventoryConfiguratorScreen` | — | [InventoryConfiguratorScreen.java](com/tom/storagemod/screen/InventoryConfiguratorScreen.java) |
| `InventoryConfiguratorMenu` | — | [InventoryConfiguratorMenu.java](com/tom/storagemod/menu/InventoryConfiguratorMenu.java) |
| `InventoryConnectorConfigUtil` | — | [InventoryConnectorConfigUtil.java](com/tom/storagemod/util/InventoryConnectorConfigUtil.java) |
| `IConfiguratorHighlight` | — | [IConfiguratorHighlight.java](com/tom/storagemod/block/IConfiguratorHighlight.java) |
| `InventoryConfigProvider` | — | [InventoryConfigProvider.java](com/tom/storagemod/jade/InventoryConfigProvider.java) |

## 按包浏览

- `com.tom.storagemod`（6 类）
- `com.tom.storagemod.api`（2 类）
- `com.tom.storagemod.block`（22 类）
- `com.tom.storagemod.block.entity`（14 类）
- `com.tom.storagemod.client`（4 类）
- `com.tom.storagemod.components`（4 类）
- `com.tom.storagemod.emi`（3 类）
- `com.tom.storagemod.inventory`（20 类）
- `com.tom.storagemod.inventory.compat`（1 类）
- `com.tom.storagemod.inventory.filter`（6 类）
- `com.tom.storagemod.inventory.sorting`（7 类）
- `com.tom.storagemod.item`（11 类）
- `com.tom.storagemod.jade`（5 类）
- `com.tom.storagemod.jei`（3 类）
- `com.tom.storagemod.menu`（11 类）
- `com.tom.storagemod.menu.slot`（3 类）
- `com.tom.storagemod.network`（3 类）
- `com.tom.storagemod.platform`（5 类）
- `com.tom.storagemod.polymorph`（2 类）
- `com.tom.storagemod.rei`（4 类）
- `com.tom.storagemod.screen`（13 类）
- `com.tom.storagemod.screen.widget`（5 类）
- `com.tom.storagemod.top`（1 类）
- `com.tom.storagemod.util`（24 类）
