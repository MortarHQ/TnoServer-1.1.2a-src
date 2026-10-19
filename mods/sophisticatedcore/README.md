# Sophisticated Core（`sophisticatedcore`）

> jar：`[精妙核心]sophisticatedcore.jar` ｜ 版本：`1.4.18` ｜ 作者：P3pp3rF1y
> 规模：**423 个类 / 65 个包 / 41707 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 43

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.p3pp3rf1y.sophisticatedcore.upgrades` | 36 | `UpgradeHandler`、`FilterLogicContainerBase`、`FilterLogicControlBase` |
| `net.p3pp3rf1y.sophisticatedcore.util` | 34 | `RecipeHelper`、`InventoryHelper`、`ClientRegistryHelper` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.cooking` | 22 | `CookingLogic`、`CookingUpgradeConfig`、`AutoCookingUpgradeConfig` |
| `net.p3pp3rf1y.sophisticatedcore.common.gui` | 19 | `StorageContainerMenuBase`、`SettingsContainerMenu`、`TemplatePersistanceContainer` |
| `net.p3pp3rf1y.sophisticatedcore.inventory` | 17 | `InventoryHandler`、`InventoryHandlerSlotTracker`、`FilteredItemHandler` |
| `net.p3pp3rf1y.sophisticatedcore.client.gui` | 17 | `StorageScreenBase`、`SettingsScreen`、`UpgradeGuiManager` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox` | 16 | `DiscHandlerRegistry`、`ServerStorageSoundHandler`、`StorageSoundHandler` |
| `net.p3pp3rf1y.sophisticatedcore.client.gui.controls` | 15 | `ColorPicker`、`TextBox`、`WidgetBase` |
| `net.p3pp3rf1y.sophisticatedcore.network` | 12 | `TransferItemsPayload`、`SyncContainerClientDataPayload`、`SyncBlockHighlightsPayload` |
| `net.p3pp3rf1y.sophisticatedcore.settings` | 11 | `SettingsHandler`、`DatapackSettingsTemplateManager`、`SettingsManager` |
| `net.p3pp3rf1y.sophisticatedcore.crafting` | 9 | `ShapeBasedRecipeBuilder`、`UpgradeNextTierRecipe`、`HoldingRecipeOutput` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.pump` | 8 | `PumpUpgradeWrapper`、`PumpUpgradeConfig`、`PumpUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.compat.create` | 8 | `MountedStorageContainerMenuBase`、`MountedStorageBase`、`MountedStorageData` |
| `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei` | 8 | `ReiStorageGhostIngredientHandler`、`ReiCraftingContainerTransferHandler`、`ReiSettingsGhostIngredientHandler` |
| `net.p3pp3rf1y.sophisticatedcore.api` | 8 | `IDiscHandler`、`IStorageFluidHandler`、`IStashStorageItem` |
| `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei` | 7 | `JeiCraftingContainerRecipeTransferHandlerBase`、`JeiClientRecipeHelper`、`JeiStorageGhostIngredientHandler` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.tank` | 7 | `TankUpgradeWrapper`、`TankUpgradeConfig`、`TankUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy` | 7 | `AlchemyUpgradeWrapper`、`AlchemyUpgradeConfig`、`AlchemyUpgradeContainer` |
| `net.p3pp3rf1y.sophisticatedcore.client.gui.utils` | 7 | `GuiHelper`、`TranslationHelper`、`TextureBlitData` |
| `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi` | 7 | `EmiClientRecipeHelper`、`EmiSettingsGhostDragDropHandler`、`EmiStorageGhostDragDropHandler` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter` | 6 | `BlockConverterRecipeContainer`、`BlockConverterRecipeControl`、`BlockConverterUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.compat.chipped` | 6 | `BlockTransformationRecipeContainer`、`BlockTransformationRecipeControl`、`BlockTransformationUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.crafting` | 6 | `CraftingItemHandler`、`CraftingUpgradeContainer`、`CraftingUpgradeTab` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.battery` | 6 | `BatteryUpgradeWrapper`、`BatteryUpgradeConfig`、`BatteryUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.xppump` | 6 | `XpPumpUpgradeWrapper`、`XpPumpUpgradeConfig`、`XpPumpUpgradeTab` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.magnet` | 6 | `MagnetUpgradeWrapper`、`MagnetUpgradeConfig`、`IMagnetPreventionChecker` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.voiding` | 6 | `VoidUpgradeConfig`、`VoidUpgradeWrapper`、`VoidUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.init` | 6 | `ModRecipes`、`ModCoreDataComponents`、`ModCompat` |
| `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common` | 5 | `CraftingContainerRecipeTransferHandlerServer`、`ClientRecipeHelper`、`SetMemorySlotPayload` |
| `net.p3pp3rf1y.sophisticatedcore.client.render` | 5 | `ClientStorageContentsTooltipBase`、`UpgradeRenderRegistry`、`BlockHighlightRenderer` |
| `net.p3pp3rf1y.sophisticatedcore.renderdata` | 5 | `RenderInfo`、`UpgradeRenderDataType`、`IUpgradeRenderData` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.stonecutter` | 5 | `StonecutterRecipeControl`、`StonecutterRecipeContainer`、`StonecutterUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.compat.sawmill` | 5 | `SawmillRecipeContainer`、`SawmillRecipeControl`、`SawmillUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedcore.settings.main` | 5 | `MainSettingsContainer`、`ContextButton`、`MainSettingsCategory` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.filter` | 5 | `FilterUpgradeItem`、`FilterUpgradeWrapper`、`FilterUpgradeTab` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.feeding` | 5 | `FeedingUpgradeItem`、`FeedingUpgradeWrapper`、`FeedingUpgradeContainer` |
| `net.p3pp3rf1y.sophisticatedcore.controller` | 4 | `ControllerBlockEntityBase`、`IControllableStorage`、`ILinkable` |
| `net.p3pp3rf1y.sophisticatedcore.compat` | 4 | `CompatRegistry`、`ICompat`、`CompatModIds` |
| `net.p3pp3rf1y.sophisticatedcore.upgrades.compacting` | 4 | `CompactingUpgradeWrapper`、`CompactingUpgradeItem`、`CompactingUpgradeContainer` |
| `net.p3pp3rf1y.sophisticatedcore.compat.craftingtweaks` | 4 | `CraftingUpgradeTweakProvider`、`CraftingTweaksCompatClient`、`CraftingTweaksCompat` |
| … | | 其余 25 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ControllerBlockEntityBase` | class | BlockEntity | — | 84 | 1040 | [ControllerBlockEntityBase.java](net/p3pp3rf1y/sophisticatedcore/controller/ControllerBlockEntityBase.java) |
| `ClientEventHandler` | class | IKeyConflictContext | — | 22 | 290 | [ClientEventHandler.java](net/p3pp3rf1y/sophisticatedcore/client/ClientEventHandler.java) |
| `InventoryHandler` | class | ItemStackHandler | — | 66 | 602 | [InventoryHandler.java](net/p3pp3rf1y/sophisticatedcore/inventory/InventoryHandler.java) |
| `InventoryHandlerSlotTracker` | class | ISlotTracker | — | 34 | 558 | [InventoryHandlerSlotTracker.java](net/p3pp3rf1y/sophisticatedcore/inventory/InventoryHandlerSlotTracker.java) |
| `StorageContainerMenuBase` | class | AbstractContainerMenu | — | 139 | 1979 | [StorageContainerMenuBase.java](net/p3pp3rf1y/sophisticatedcore/common/gui/StorageContainerMenuBase.java) |
| `RecipeHelper` | class | — | — | 46 | 530 | [RecipeHelper.java](net/p3pp3rf1y/sophisticatedcore/util/RecipeHelper.java) |
| `StorageScreenBase` | class | Button | — | 90 | 1333 | [StorageScreenBase.java](net/p3pp3rf1y/sophisticatedcore/client/gui/StorageScreenBase.java) |
| `UpgradeHandler` | class | ItemStackHandler | — | 36 | 371 | [UpgradeHandler.java](net/p3pp3rf1y/sophisticatedcore/upgrades/UpgradeHandler.java) |
| `DiscHandlerRegistry` | class | — | — | 6 | 63 | [DiscHandlerRegistry.java](net/p3pp3rf1y/sophisticatedcore/upgrades/jukebox/DiscHandlerRegistry.java) |
| `CraftingContainerRecipeTransferHandlerServer` | class | — | — | 11 | 289 | [CraftingContainerRecipeTransferHandlerServer.java](net/p3pp3rf1y/sophisticatedcore/compat/recipeviewers/common/CraftingContainerRecipeTransferHandlerServer.java) |
| `SophisticatedCore` | class | — | Mod | 8 | 96 | [SophisticatedCore.java](net/p3pp3rf1y/sophisticatedcore/SophisticatedCore.java) |
| `BlockConverterRecipeContainer` | class | Slot | — | 22 | 216 | [BlockConverterRecipeContainer.java](net/p3pp3rf1y/sophisticatedcore/upgrades/blockconverter/BlockConverterRecipeContainer.java) |
| `JeiCraftingContainerRecipeTransferHandlerBase` | class | IRecipeTransferHandler<C | — | 9 | 234 | [JeiCraftingContainerRecipeTransferHandlerBase.java](net/p3pp3rf1y/sophisticatedcore/compat/recipeviewers/jei/JeiCraftingContainerRecipeTransferHandlerBase.java) |
| `FilteredItemHandler` | class | FilteredItemHandler | — | 18 | 176 | [FilteredItemHandler.java](net/p3pp3rf1y/sophisticatedcore/inventory/FilteredItemHandler.java) |
| `BlockConverterRecipeControl` | class | — | — | 18 | 238 | [BlockConverterRecipeControl.java](net/p3pp3rf1y/sophisticatedcore/upgrades/blockconverter/BlockConverterRecipeControl.java) |
| `SettingsContainerMenu` | class | AbstractContainerMenu | — | 44 | 401 | [SettingsContainerMenu.java](net/p3pp3rf1y/sophisticatedcore/common/gui/SettingsContainerMenu.java) |
| `BlockTransformationRecipeContainer` | class | Slot | — | 17 | 218 | [BlockTransformationRecipeContainer.java](net/p3pp3rf1y/sophisticatedcore/compat/chipped/BlockTransformationRecipeContainer.java) |
| `BlockTransformationRecipeControl` | class | WidgetBase | — | 17 | 225 | [BlockTransformationRecipeControl.java](net/p3pp3rf1y/sophisticatedcore/compat/chipped/BlockTransformationRecipeControl.java) |
| `StatefulComponentItemHandler` | class | IItemHandlerModifiable | — | 13 | 137 | [StatefulComponentItemHandler.java](net/p3pp3rf1y/sophisticatedcore/inventory/StatefulComponentItemHandler.java) |
| `TankUpgradeWrapper` | class | UpgradeWrapperBase | — | 44 | 611 | [TankUpgradeWrapper.java](net/p3pp3rf1y/sophisticatedcore/upgrades/tank/TankUpgradeWrapper.java) |
| `CraftingItemHandler` | class | TransientCraftingContainer | — | 11 | 94 | [CraftingItemHandler.java](net/p3pp3rf1y/sophisticatedcore/upgrades/crafting/CraftingItemHandler.java) |
| `AlchemyUpgradeWrapper` | class | UpgradeWrapperBase | — | 37 | 652 | [AlchemyUpgradeWrapper.java](net/p3pp3rf1y/sophisticatedcore/upgrades/alchemy/AlchemyUpgradeWrapper.java) |
| `ItemDisplaySettingsCategory` | class | ISettingsCategory<ItemDisplaySetting | — | 31 | 361 | [ItemDisplaySettingsCategory.java](net/p3pp3rf1y/sophisticatedcore/settings/itemdisplay/ItemDisplaySettingsCategory.java) |
| `InventoryHelper` | class | — | — | 37 | 591 | [InventoryHelper.java](net/p3pp3rf1y/sophisticatedcore/util/InventoryHelper.java) |
| `ClientRegistryHelper` | class | — | — | 1 | 14 | [ClientRegistryHelper.java](net/p3pp3rf1y/sophisticatedcore/util/ClientRegistryHelper.java) |
| `SettingsScreen` | class | AbstractContainerScreen | — | 39 | 368 | [SettingsScreen.java](net/p3pp3rf1y/sophisticatedcore/client/gui/SettingsScreen.java) |
| `ClientStorageContentsTooltipBase` | class | ClientTooltipComponent | — | 30 | 322 | [ClientStorageContentsTooltipBase.java](net/p3pp3rf1y/sophisticatedcore/client/render/ClientStorageContentsTooltipBase.java) |
| `CookingLogic` | class | ComponentItemHandler | — | 48 | 479 | [CookingLogic.java](net/p3pp3rf1y/sophisticatedcore/upgrades/cooking/CookingLogic.java) |
| `ServerStorageSoundHandler` | class | — | — | 15 | 158 | [ServerStorageSoundHandler.java](net/p3pp3rf1y/sophisticatedcore/upgrades/jukebox/ServerStorageSoundHandler.java) |
| `MixinAbstractContraptionEntity` | class | — | Mixin | 1 | 30 | [MixinAbstractContraptionEntity.java](net/p3pp3rf1y/sophisticatedcore/mixin/MixinAbstractContraptionEntity.java) |
| `CommonEventHandler` | class | — | — | 2 | 37 | [CommonEventHandler.java](net/p3pp3rf1y/sophisticatedcore/common/CommonEventHandler.java) |
| `TemplatePersistanceContainer` | class | TemplatePersistanceContainer.NamedPe | — | 49 | 576 | [TemplatePersistanceContainer.java](net/p3pp3rf1y/sophisticatedcore/common/gui/TemplatePersistanceContainer.java) |
| `CompatRegistry` | class | — | — | 3 | 39 | [CompatRegistry.java](net/p3pp3rf1y/sophisticatedcore/compat/CompatRegistry.java) |
| `TrashSlotScreenRegistry` | class | — | — | 2 | 18 | [TrashSlotScreenRegistry.java](net/p3pp3rf1y/sophisticatedcore/compat/trashslot/TrashSlotScreenRegistry.java) |
| `SettingsHandler` | class | — | — | 15 | 107 | [SettingsHandler.java](net/p3pp3rf1y/sophisticatedcore/settings/SettingsHandler.java) |
| `FilterItemStackHandler` | class | ItemStackHandler | — | 7 | 43 | [FilterItemStackHandler.java](net/p3pp3rf1y/sophisticatedcore/util/FilterItemStackHandler.java) |
| `GuiHelper` | class | — | — | 26 | 493 | [GuiHelper.java](net/p3pp3rf1y/sophisticatedcore/client/gui/utils/GuiHelper.java) |
| `RenderInfo` | class | — | — | 50 | 512 | [RenderInfo.java](net/p3pp3rf1y/sophisticatedcore/renderdata/RenderInfo.java) |
| `IItemHandlerSimpleExtractor` | interface | — | — | 0 | 8 | [IItemHandlerSimpleExtractor.java](net/p3pp3rf1y/sophisticatedcore/inventory/IItemHandlerSimpleExtractor.java) |
| `IItemHandlerSimpleInserter` | interface | — | — | 0 | 9 | [IItemHandlerSimpleInserter.java](net/p3pp3rf1y/sophisticatedcore/inventory/IItemHandlerSimpleInserter.java) |
| `ITrackedContentsItemHandler` | interface | — | — | 0 | 15 | [ITrackedContentsItemHandler.java](net/p3pp3rf1y/sophisticatedcore/inventory/ITrackedContentsItemHandler.java) |
| `IInventoryPartHandler` | interface | IInventoryPartHandler | — | 11 | 171 | [IInventoryPartHandler.java](net/p3pp3rf1y/sophisticatedcore/inventory/IInventoryPartHandler.java) |
| `FilterLogicContainerBase` | class | Slot | — | 40 | 291 | [FilterLogicContainerBase.java](net/p3pp3rf1y/sophisticatedcore/upgrades/FilterLogicContainerBase.java) |
| `FilterLogicControlBase` | class | Button | — | 26 | 453 | [FilterLogicControlBase.java](net/p3pp3rf1y/sophisticatedcore/upgrades/FilterLogicControlBase.java) |
| `FilterLogic` | class | FilterItemStackHandler | — | 33 | 296 | [FilterLogic.java](net/p3pp3rf1y/sophisticatedcore/upgrades/FilterLogic.java) |
| `FilterSlotItemHandler` | class | SlotSuppliedHandler | — | 2 | 21 | [FilterSlotItemHandler.java](net/p3pp3rf1y/sophisticatedcore/common/gui/FilterSlotItemHandler.java) |
| `EmiClientRecipeHelper` | class | — | — | 2 | 29 | [EmiClientRecipeHelper.java](net/p3pp3rf1y/sophisticatedcore/compat/recipeviewers/emi/EmiClientRecipeHelper.java) |
| `ClientRecipeHelper` | class | — | — | 4 | 73 | [ClientRecipeHelper.java](net/p3pp3rf1y/sophisticatedcore/compat/recipeviewers/common/ClientRecipeHelper.java) |
| `JeiClientRecipeHelper` | class | — | — | 2 | 19 | [JeiClientRecipeHelper.java](net/p3pp3rf1y/sophisticatedcore/compat/recipeviewers/jei/JeiClientRecipeHelper.java) |
| `DatapackSettingsTemplateManager` | class | SimplePreparableReloadListener | — | 6 | 103 | [DatapackSettingsTemplateManager.java](net/p3pp3rf1y/sophisticatedcore/settings/DatapackSettingsTemplateManager.java) |
| `MemorySettingsCategory` | class | ISettingsCategory<MemorySettingsCate | — | 35 | 374 | [MemorySettingsCategory.java](net/p3pp3rf1y/sophisticatedcore/settings/memory/MemorySettingsCategory.java) |
| `RegistryHelper` | class | — | — | 4 | 43 | [RegistryHelper.java](net/p3pp3rf1y/sophisticatedcore/util/RegistryHelper.java) |
| `NoopStorageWrapper` | class | IStorageWrapper | — | 34 | 211 | [NoopStorageWrapper.java](net/p3pp3rf1y/sophisticatedcore/util/NoopStorageWrapper.java) |
| `StreamCodecHelper` | class | — | — | 28 | 271 | [StreamCodecHelper.java](net/p3pp3rf1y/sophisticatedcore/util/StreamCodecHelper.java) |
| `InventoryPartRegistry` | class | — | — | 2 | 29 | [InventoryPartRegistry.java](net/p3pp3rf1y/sophisticatedcore/inventory/InventoryPartRegistry.java) |
| `CachedFailedInsertInventoryHandler` | class | IItemHandlerModifiable | — | 8 | 72 | [CachedFailedInsertInventoryHandler.java](net/p3pp3rf1y/sophisticatedcore/inventory/CachedFailedInsertInventoryHandler.java) |
| `PumpUpgradeWrapper` | class | UpgradeWrapperBase | — | 29 | 324 | [PumpUpgradeWrapper.java](net/p3pp3rf1y/sophisticatedcore/upgrades/pump/PumpUpgradeWrapper.java) |
| `BatteryUpgradeWrapper` | class | UpgradeWrapperBase | — | 26 | 218 | [BatteryUpgradeWrapper.java](net/p3pp3rf1y/sophisticatedcore/upgrades/battery/BatteryUpgradeWrapper.java) |
| `StorageSoundHandler` | class | — | — | 7 | 78 | [StorageSoundHandler.java](net/p3pp3rf1y/sophisticatedcore/upgrades/jukebox/StorageSoundHandler.java) |
| `VanillaDiscHandler` | class | IDiscHandler<Holder<JukeboxSong>> | — | 9 | 96 | [VanillaDiscHandler.java](net/p3pp3rf1y/sophisticatedcore/upgrades/jukebox/VanillaDiscHandler.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinAbstractContraptionEntity` | `AbstractContraptionEntity` | [MixinAbstractContraptionEntity.java](net/p3pp3rf1y/sophisticatedcore/mixin/MixinAbstractContraptionEntity.java) |
| `MixinVanillaInventoryCodeHooks` | `VanillaInventoryCodeHooks` | [MixinVanillaInventoryCodeHooks.java](net/p3pp3rf1y/sophisticatedcore/mixin/MixinVanillaInventoryCodeHooks.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](net/p3pp3rf1y/sophisticatedcore/Config.java) |
| `FilteredUpgradeConfigBase` | — | [FilteredUpgradeConfigBase.java](net/p3pp3rf1y/sophisticatedcore/upgrades/FilteredUpgradeConfigBase.java) |
| `FilteredUpgradeConfig` | — | [FilteredUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/FilteredUpgradeConfig.java) |
| `IUpgradeCountLimitConfig` | — | [IUpgradeCountLimitConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/IUpgradeCountLimitConfig.java) |
| `TankUpgradeConfig` | — | [TankUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/tank/TankUpgradeConfig.java) |
| `XpPumpUpgradeConfig` | — | [XpPumpUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/xppump/XpPumpUpgradeConfig.java) |
| `StackUpgradeConfig` | — | [StackUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/stack/StackUpgradeConfig.java) |
| `PumpUpgradeConfig` | — | [PumpUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/pump/PumpUpgradeConfig.java) |
| `BatteryUpgradeConfig` | — | [BatteryUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/battery/BatteryUpgradeConfig.java) |
| `VoidUpgradeConfig` | — | [VoidUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/voiding/VoidUpgradeConfig.java) |
| `AlchemyUpgradeConfig` | — | [AlchemyUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/alchemy/AlchemyUpgradeConfig.java) |
| `CookingUpgradeConfig` | — | [CookingUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/cooking/CookingUpgradeConfig.java) |
| `AutoCookingUpgradeConfig` | — | [AutoCookingUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/cooking/AutoCookingUpgradeConfig.java) |
| `MagnetUpgradeConfig` | — | [MagnetUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/magnet/MagnetUpgradeConfig.java) |
| `JukeboxUpgradeConfig` | — | [JukeboxUpgradeConfig.java](net/p3pp3rf1y/sophisticatedcore/upgrades/jukebox/JukeboxUpgradeConfig.java) |

## 按包浏览

- `net.p3pp3rf1y.sophisticatedcore`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.api`（8 类）
- `net.p3pp3rf1y.sophisticatedcore.client`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.client.gui`（17 类）
- `net.p3pp3rf1y.sophisticatedcore.client.gui.controls`（15 类）
- `net.p3pp3rf1y.sophisticatedcore.client.gui.utils`（7 类）
- `net.p3pp3rf1y.sophisticatedcore.client.init`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.client.render`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.common`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.common.gui`（19 类）
- `net.p3pp3rf1y.sophisticatedcore.compat`（4 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.accessories`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.chipped`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.craftingtweaks`（4 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.create`（8 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.curios`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.ftbchunks`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.inventorysorter`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.itemborders`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.mousetweaks`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.openpartiesandclaims`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi`（7 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.comparison`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei`（7 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei.subtypes`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei`（8 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei.comparator`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.reliquary`（1 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.sawmill`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.compat.trashslot`（3 类）
- `net.p3pp3rf1y.sophisticatedcore.controller`（4 类）
- `net.p3pp3rf1y.sophisticatedcore.crafting`（9 类）
- `net.p3pp3rf1y.sophisticatedcore.data`（3 类）
- `net.p3pp3rf1y.sophisticatedcore.init`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.inventory`（17 类）
- `net.p3pp3rf1y.sophisticatedcore.mixin`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.network`（12 类）
- `net.p3pp3rf1y.sophisticatedcore.renderdata`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.settings`（11 类）
- `net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay`（3 类）
- `net.p3pp3rf1y.sophisticatedcore.settings.main`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.settings.memory`（3 类）
- `net.p3pp3rf1y.sophisticatedcore.settings.nosort`（3 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades`（36 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy`（7 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.battery`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.compacting`（4 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.cooking`（22 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.crafting`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.feeding`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.filter`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.infinity`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox`（16 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.magnet`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.pickup`（3 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.pump`（8 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.stack`（2 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.stonecutter`（5 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.tank`（7 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.voiding`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.upgrades.xppump`（6 类）
- `net.p3pp3rf1y.sophisticatedcore.util`（34 类）
