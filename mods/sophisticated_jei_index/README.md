# Sophisticated JEI Index（`sophisticated_jei_index`）

> jar：`[精妙背包：JEI 索引升级]sophisticated_jei.jar` ｜ 版本：`1.0.2` ｜ 作者：amicbeam
> 规模：**37 个类 / 14 个包 / 2357 行** ｜ 事件处理类 0 ｜ Mixin 18 ｜ 注册/管理类 6

**依赖**：`neoforge`、`minecraft`、`sophisticatedbackpacks`、`jei`、`emi`、`ae2`、`refinedstorage`、`toms_storage`、`beyonddimensions`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.sbjeiindex.mixin.bd` | 5 | `CraftMenuRecipeTransferHandlerMixin`、`CraftTerminalRecipeTransferHandlerMixin`、`NetRecipeHandlerMixin` |
| `com.sbjeiindex.jei` | 5 | `OffsetItemHandlerModifiable`、`BackpackSnapshotCache`、`JeiTransferConstants` |
| `com.sbjeiindex.mixin` | 4 | `BasicRecipeTransferHandlerMixin`、`PacketRecipeTransferMixin`、`TransferOperationMixin` |
| `com.sbjeiindex.mixin.emi` | 4 | `FillRecipeC2SPacketMixin`、`EmiRecipeFillerMixin`、`EmiClientMixin` |
| `com.sbjeiindex.emi.transfer` | 4 | `MenuSlotSource`、`EmiFillHelper`、`EmiInputSource` |
| `com.sbjeiindex.emi` | 3 | `EmiInputSourcesHelper`、`EmiTransferConstants`、`EmiBackpackSlots` |
| `com.sbjeiindex.mixin.ae2` | 2 | `FillCraftingGridFromRecipePacketMixin`、`CraftingTermMenuMixin` |
| `com.sbjeiindex.mixin.ts` | 2 | `CraftingTerminalTransferHandlerMixin`、`TerminalCraftingFillerMixin` |
| `com.sbjeiindex.mixin.rs` | 2 | `IngredientTrackerMixin`、`CraftingGridBehaviorMixin` |
| `com.sbjeiindex.util` | 2 | `BackpackExtraction`、`BackpackHelper` |
| `com.sbjeiindex` | 1 | `SBJEIIndex` |
| `com.sbjeiindex.config` | 1 | `SBJEIIndexConfig` |
| `com.sbjeiindex.upgrade` | 1 | `JEIIndexUpgradeItem` |
| `com.sbjeiindex.init` | 1 | `ModItems` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CraftMenuRecipeTransferHandlerMixin` | class | — | Mixin | 1 | 75 | [CraftMenuRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/bd/CraftMenuRecipeTransferHandlerMixin.java) |
| `BasicRecipeTransferHandlerMixin` | class | — | Mixin | 1 | 219 | [BasicRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/BasicRecipeTransferHandlerMixin.java) |
| `CraftTerminalRecipeTransferHandlerMixin` | class | — | Mixin | 1 | 75 | [CraftTerminalRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/bd/CraftTerminalRecipeTransferHandlerMixin.java) |
| `NetRecipeHandlerMixin` | class | — | Mixin | 1 | 42 | [NetRecipeHandlerMixin.java](com/sbjeiindex/mixin/bd/NetRecipeHandlerMixin.java) |
| `PacketRecipeTransferMixin` | class | — | Mixin | 2 | 137 | [PacketRecipeTransferMixin.java](com/sbjeiindex/mixin/PacketRecipeTransferMixin.java) |
| `FillRecipeC2SPacketMixin` | class | — | Mixin | 1 | 167 | [FillRecipeC2SPacketMixin.java](com/sbjeiindex/mixin/emi/FillRecipeC2SPacketMixin.java) |
| `FillCraftingGridFromRecipePacketMixin` | class | — | Mixin | 1 | 32 | [FillCraftingGridFromRecipePacketMixin.java](com/sbjeiindex/mixin/ae2/FillCraftingGridFromRecipePacketMixin.java) |
| `CraftingTerminalTransferHandlerMixin` | class | — | Mixin | 1 | 39 | [CraftingTerminalTransferHandlerMixin.java](com/sbjeiindex/mixin/ts/CraftingTerminalTransferHandlerMixin.java) |
| `SBJEIIndex` | class | — | Mod | 1 | 36 | [SBJEIIndex.java](com/sbjeiindex/SBJEIIndex.java) |
| `EmiRecipeFillerMixin` | class | — | Mixin | 1 | 26 | [EmiRecipeFillerMixin.java](com/sbjeiindex/mixin/emi/EmiRecipeFillerMixin.java) |
| `DimensionsCraftMenuMixin` | class | — | Mixin | 1 | 46 | [DimensionsCraftMenuMixin.java](com/sbjeiindex/mixin/bd/DimensionsCraftMenuMixin.java) |
| `EmiClientMixin` | class | — | Mixin | 1 | 26 | [EmiClientMixin.java](com/sbjeiindex/mixin/emi/EmiClientMixin.java) |
| `CraftingTermMenuMixin` | class | — | Mixin | 2 | 76 | [CraftingTermMenuMixin.java](com/sbjeiindex/mixin/ae2/CraftingTermMenuMixin.java) |
| `OffsetItemHandlerModifiable` | class | IItemHandlerModifiable | — | 8 | 55 | [OffsetItemHandlerModifiable.java](com/sbjeiindex/jei/OffsetItemHandlerModifiable.java) |
| `IngredientTrackerMixin` | class | — | Mixin | 5 | 79 | [IngredientTrackerMixin.java](com/sbjeiindex/mixin/rs/IngredientTrackerMixin.java) |
| `TransferOperationMixin` | class | — | Mixin | 4 | 37 | [TransferOperationMixin.java](com/sbjeiindex/mixin/TransferOperationMixin.java) |
| `AutoRefillResultSlotMixin` | class | — | Mixin | 1 | 65 | [AutoRefillResultSlotMixin.java](com/sbjeiindex/mixin/bd/AutoRefillResultSlotMixin.java) |
| `EmiPlayerInventoryMixin` | class | — | Mixin | 4 | 56 | [EmiPlayerInventoryMixin.java](com/sbjeiindex/mixin/emi/EmiPlayerInventoryMixin.java) |
| `CraftingGridBehaviorMixin` | class | — | Mixin | 1 | 67 | [CraftingGridBehaviorMixin.java](com/sbjeiindex/mixin/rs/CraftingGridBehaviorMixin.java) |
| `TerminalCraftingFillerMixin` | class | — | Mixin | 4 | 101 | [TerminalCraftingFillerMixin.java](com/sbjeiindex/mixin/ts/TerminalCraftingFillerMixin.java) |
| `SBJEIIndexConfig` | class | — | — | 0 | 25 | [SBJEIIndexConfig.java](com/sbjeiindex/config/SBJEIIndexConfig.java) |
| `JEIIndexUpgradeItem` | class | UpgradeItemBase | — | 5 | 48 | [JEIIndexUpgradeItem.java](com/sbjeiindex/upgrade/JEIIndexUpgradeItem.java) |
| `MenuSlotSource` | class | EmiInputSource | — | 4 | 49 | [MenuSlotSource.java](com/sbjeiindex/emi/transfer/MenuSlotSource.java) |
| `BackpackExtraction` | class | — | — | 12 | 190 | [BackpackExtraction.java](com/sbjeiindex/util/BackpackExtraction.java) |
| `BackpackHelper` | class | — | — | 7 | 120 | [BackpackHelper.java](com/sbjeiindex/util/BackpackHelper.java) |
| `ModItems` | class | — | — | 0 | 13 | [ModItems.java](com/sbjeiindex/init/ModItems.java) |
| `SBJEIIndexMixinPlugin` | class | IMixinConfigPlugin | — | 8 | 46 | [SBJEIIndexMixinPlugin.java](com/sbjeiindex/mixin/SBJEIIndexMixinPlugin.java) |
| `EmiInputSourcesHelper` | class | — | — | 1 | 43 | [EmiInputSourcesHelper.java](com/sbjeiindex/emi/EmiInputSourcesHelper.java) |
| `EmiFillHelper` | class | — | — | 1 | 53 | [EmiFillHelper.java](com/sbjeiindex/emi/transfer/EmiFillHelper.java) |
| `BackpackSnapshotCache` | class | — | — | 8 | 132 | [BackpackSnapshotCache.java](com/sbjeiindex/jei/BackpackSnapshotCache.java) |
| `EmiTransferConstants` | class | — | — | 0 | 10 | [EmiTransferConstants.java](com/sbjeiindex/emi/EmiTransferConstants.java) |
| `EmiBackpackSlots` | class | — | — | 1 | 46 | [EmiBackpackSlots.java](com/sbjeiindex/emi/EmiBackpackSlots.java) |
| `EmiInputSource` | interface | — | — | 0 | 17 | [EmiInputSource.java](com/sbjeiindex/emi/transfer/EmiInputSource.java) |
| `BackpackSlotSource` | class | EmiInputSource | — | 4 | 49 | [BackpackSlotSource.java](com/sbjeiindex/emi/transfer/BackpackSlotSource.java) |
| `JeiTransferConstants` | class | — | — | 0 | 10 | [JeiTransferConstants.java](com/sbjeiindex/jei/JeiTransferConstants.java) |
| `JEIPlugin` | class | IModPlugin | JeiPlugin | 2 | 19 | [JEIPlugin.java](com/sbjeiindex/jei/JEIPlugin.java) |
| `JeiSlotResolver` | class | — | — | 3 | 31 | [JeiSlotResolver.java](com/sbjeiindex/jei/JeiSlotResolver.java) |

## Mixin（18 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `CraftMenuRecipeTransferHandlerMixin` | `CraftMenuRecipeTransferHandler` | [CraftMenuRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/bd/CraftMenuRecipeTransferHandlerMixin.java) |
| `BasicRecipeTransferHandlerMixin` | `BasicRecipeTransferHandler` | [BasicRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/BasicRecipeTransferHandlerMixin.java) |
| `CraftTerminalRecipeTransferHandlerMixin` | `CraftTerminalRecipeTransferHandler` | [CraftTerminalRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/bd/CraftTerminalRecipeTransferHandlerMixin.java) |
| `NetRecipeHandlerMixin` | `NetRecipeHandler` | [NetRecipeHandlerMixin.java](com/sbjeiindex/mixin/bd/NetRecipeHandlerMixin.java) |
| `PacketRecipeTransferMixin` | `PacketRecipeTransfer` | [PacketRecipeTransferMixin.java](com/sbjeiindex/mixin/PacketRecipeTransferMixin.java) |
| `FillRecipeC2SPacketMixin` | `FillRecipeC2SPacket` | [FillRecipeC2SPacketMixin.java](com/sbjeiindex/mixin/emi/FillRecipeC2SPacketMixin.java) |
| `FillCraftingGridFromRecipePacketMixin` | `FillCraftingGridFromRecipePacket` | [FillCraftingGridFromRecipePacketMixin.java](com/sbjeiindex/mixin/ae2/FillCraftingGridFromRecipePacketMixin.java) |
| `CraftingTerminalTransferHandlerMixin` | `CraftingTerminalTransferHandler` | [CraftingTerminalTransferHandlerMixin.java](com/sbjeiindex/mixin/ts/CraftingTerminalTransferHandlerMixin.java) |
| `EmiRecipeFillerMixin` | `EmiRecipeFiller` | [EmiRecipeFillerMixin.java](com/sbjeiindex/mixin/emi/EmiRecipeFillerMixin.java) |
| `DimensionsCraftMenuMixin` | `DimensionsCraftMenu` | [DimensionsCraftMenuMixin.java](com/sbjeiindex/mixin/bd/DimensionsCraftMenuMixin.java) |
| `EmiClientMixin` | `EmiClient` | [EmiClientMixin.java](com/sbjeiindex/mixin/emi/EmiClientMixin.java) |
| `CraftingTermMenuMixin` | `CraftingTermMenu` | [CraftingTermMenuMixin.java](com/sbjeiindex/mixin/ae2/CraftingTermMenuMixin.java) |
| `IngredientTrackerMixin` | `IngredientTracker` | [IngredientTrackerMixin.java](com/sbjeiindex/mixin/rs/IngredientTrackerMixin.java) |
| `TransferOperationMixin` | `TransferOperation` | [TransferOperationMixin.java](com/sbjeiindex/mixin/TransferOperationMixin.java) |
| `AutoRefillResultSlotMixin` | `AutoRefillResultSlot` | [AutoRefillResultSlotMixin.java](com/sbjeiindex/mixin/bd/AutoRefillResultSlotMixin.java) |
| `EmiPlayerInventoryMixin` | `EmiPlayerInventory` | [EmiPlayerInventoryMixin.java](com/sbjeiindex/mixin/emi/EmiPlayerInventoryMixin.java) |
| `CraftingGridBehaviorMixin` | `CraftingGridBehavior` | [CraftingGridBehaviorMixin.java](com/sbjeiindex/mixin/rs/CraftingGridBehaviorMixin.java) |
| `TerminalCraftingFillerMixin` | `TerminalCraftingFiller` | [TerminalCraftingFillerMixin.java](com/sbjeiindex/mixin/ts/TerminalCraftingFillerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `SBJEIIndexConfig` | — | [SBJEIIndexConfig.java](com/sbjeiindex/config/SBJEIIndexConfig.java) |

## 全部类（37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CraftMenuRecipeTransferHandlerMixin` | class | — | Mixin | 1 | 75 | [CraftMenuRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/bd/CraftMenuRecipeTransferHandlerMixin.java) |
| `BasicRecipeTransferHandlerMixin` | class | — | Mixin | 1 | 219 | [BasicRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/BasicRecipeTransferHandlerMixin.java) |
| `CraftTerminalRecipeTransferHandlerMixin` | class | — | Mixin | 1 | 75 | [CraftTerminalRecipeTransferHandlerMixin.java](com/sbjeiindex/mixin/bd/CraftTerminalRecipeTransferHandlerMixin.java) |
| `NetRecipeHandlerMixin` | class | — | Mixin | 1 | 42 | [NetRecipeHandlerMixin.java](com/sbjeiindex/mixin/bd/NetRecipeHandlerMixin.java) |
| `PacketRecipeTransferMixin` | class | — | Mixin | 2 | 137 | [PacketRecipeTransferMixin.java](com/sbjeiindex/mixin/PacketRecipeTransferMixin.java) |
| `FillRecipeC2SPacketMixin` | class | — | Mixin | 1 | 167 | [FillRecipeC2SPacketMixin.java](com/sbjeiindex/mixin/emi/FillRecipeC2SPacketMixin.java) |
| `FillCraftingGridFromRecipePacketMixin` | class | — | Mixin | 1 | 32 | [FillCraftingGridFromRecipePacketMixin.java](com/sbjeiindex/mixin/ae2/FillCraftingGridFromRecipePacketMixin.java) |
| `CraftingTerminalTransferHandlerMixin` | class | — | Mixin | 1 | 39 | [CraftingTerminalTransferHandlerMixin.java](com/sbjeiindex/mixin/ts/CraftingTerminalTransferHandlerMixin.java) |
| `SBJEIIndex` | class | — | Mod | 1 | 36 | [SBJEIIndex.java](com/sbjeiindex/SBJEIIndex.java) |
| `EmiRecipeFillerMixin` | class | — | Mixin | 1 | 26 | [EmiRecipeFillerMixin.java](com/sbjeiindex/mixin/emi/EmiRecipeFillerMixin.java) |
| `DimensionsCraftMenuMixin` | class | — | Mixin | 1 | 46 | [DimensionsCraftMenuMixin.java](com/sbjeiindex/mixin/bd/DimensionsCraftMenuMixin.java) |
| `EmiClientMixin` | class | — | Mixin | 1 | 26 | [EmiClientMixin.java](com/sbjeiindex/mixin/emi/EmiClientMixin.java) |
| `CraftingTermMenuMixin` | class | — | Mixin | 2 | 76 | [CraftingTermMenuMixin.java](com/sbjeiindex/mixin/ae2/CraftingTermMenuMixin.java) |
| `OffsetItemHandlerModifiable` | class | IItemHandlerModifiable | — | 8 | 55 | [OffsetItemHandlerModifiable.java](com/sbjeiindex/jei/OffsetItemHandlerModifiable.java) |
| `IngredientTrackerMixin` | class | — | Mixin | 5 | 79 | [IngredientTrackerMixin.java](com/sbjeiindex/mixin/rs/IngredientTrackerMixin.java) |
| `TransferOperationMixin` | class | — | Mixin | 4 | 37 | [TransferOperationMixin.java](com/sbjeiindex/mixin/TransferOperationMixin.java) |
| `AutoRefillResultSlotMixin` | class | — | Mixin | 1 | 65 | [AutoRefillResultSlotMixin.java](com/sbjeiindex/mixin/bd/AutoRefillResultSlotMixin.java) |
| `EmiPlayerInventoryMixin` | class | — | Mixin | 4 | 56 | [EmiPlayerInventoryMixin.java](com/sbjeiindex/mixin/emi/EmiPlayerInventoryMixin.java) |
| `CraftingGridBehaviorMixin` | class | — | Mixin | 1 | 67 | [CraftingGridBehaviorMixin.java](com/sbjeiindex/mixin/rs/CraftingGridBehaviorMixin.java) |
| `TerminalCraftingFillerMixin` | class | — | Mixin | 4 | 101 | [TerminalCraftingFillerMixin.java](com/sbjeiindex/mixin/ts/TerminalCraftingFillerMixin.java) |
| `SBJEIIndexConfig` | class | — | — | 0 | 25 | [SBJEIIndexConfig.java](com/sbjeiindex/config/SBJEIIndexConfig.java) |
| `JEIIndexUpgradeItem` | class | UpgradeItemBase | — | 5 | 48 | [JEIIndexUpgradeItem.java](com/sbjeiindex/upgrade/JEIIndexUpgradeItem.java) |
| `MenuSlotSource` | class | EmiInputSource | — | 4 | 49 | [MenuSlotSource.java](com/sbjeiindex/emi/transfer/MenuSlotSource.java) |
| `BackpackExtraction` | class | — | — | 12 | 190 | [BackpackExtraction.java](com/sbjeiindex/util/BackpackExtraction.java) |
| `BackpackHelper` | class | — | — | 7 | 120 | [BackpackHelper.java](com/sbjeiindex/util/BackpackHelper.java) |
| `ModItems` | class | — | — | 0 | 13 | [ModItems.java](com/sbjeiindex/init/ModItems.java) |
| `SBJEIIndexMixinPlugin` | class | IMixinConfigPlugin | — | 8 | 46 | [SBJEIIndexMixinPlugin.java](com/sbjeiindex/mixin/SBJEIIndexMixinPlugin.java) |
| `EmiInputSourcesHelper` | class | — | — | 1 | 43 | [EmiInputSourcesHelper.java](com/sbjeiindex/emi/EmiInputSourcesHelper.java) |
| `EmiFillHelper` | class | — | — | 1 | 53 | [EmiFillHelper.java](com/sbjeiindex/emi/transfer/EmiFillHelper.java) |
| `BackpackSnapshotCache` | class | — | — | 8 | 132 | [BackpackSnapshotCache.java](com/sbjeiindex/jei/BackpackSnapshotCache.java) |
| `EmiTransferConstants` | class | — | — | 0 | 10 | [EmiTransferConstants.java](com/sbjeiindex/emi/EmiTransferConstants.java) |
| `EmiBackpackSlots` | class | — | — | 1 | 46 | [EmiBackpackSlots.java](com/sbjeiindex/emi/EmiBackpackSlots.java) |
| `EmiInputSource` | interface | — | — | 0 | 17 | [EmiInputSource.java](com/sbjeiindex/emi/transfer/EmiInputSource.java) |
| `BackpackSlotSource` | class | EmiInputSource | — | 4 | 49 | [BackpackSlotSource.java](com/sbjeiindex/emi/transfer/BackpackSlotSource.java) |
| `JeiTransferConstants` | class | — | — | 0 | 10 | [JeiTransferConstants.java](com/sbjeiindex/jei/JeiTransferConstants.java) |
| `JEIPlugin` | class | IModPlugin | JeiPlugin | 2 | 19 | [JEIPlugin.java](com/sbjeiindex/jei/JEIPlugin.java) |
| `JeiSlotResolver` | class | — | — | 3 | 31 | [JeiSlotResolver.java](com/sbjeiindex/jei/JeiSlotResolver.java) |
