# Inventory Profiles Next（`inventoryprofilesnext`）

> jar：`[一键背包整理Next]InventoryProfilesNext.jar` ｜ 版本：`2.1.11` ｜ 作者：?
> 规模：**751 个类 / 49 个包 / 64351 行** ｜ 事件处理类 1 ｜ Mixin 15 ｜ 注册/管理类 86

**依赖**：`neoforge`、`minecraft`、`libipn`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `org.anti_ad.a.b.a.a.a.a` | 83 | `Y`、`E`、`c` |
| `org.anti_ad.mc.ipnext.item.rule.natives` | 51 | `DefinedNativeRulesKt`、`TypeBasedRuleProvider`、`ByNbtRule` |
| `org.anti_ad.a.b.a.a.a` | 47 | `D`、`z`、`K` |
| `org.anti_ad.mc.ipnext.config` | 40 | `ModSettings`、`AutoRefillSettings`、`Hotkeys` |
| `org.anti_ad.mc.ipnext.event` | 37 | `LockSlotsHandler`、`CutterCraftingHandlerBase`、`ClientEventHandler` |
| `org.anti_ad.mc.ipnext.inventory` | 37 | `GeneralInventoryActions`、`AreaTypes`、`ItemArea` |
| `org.anti_ad.mc.ipnext.item` | 36 | `ItemTypeExtensionsKt`、`ItemType`、`ItemStackExtensionsKt` |
| `org.anti_ad.mc.ipnext.gui` | 33 | `GUIDEEditorScreen`、`GUIDEEditorScreen`、`DebugScreen` |
| `org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator` | 31 | `SimpleDiffCalculatorInstance`、`SingleType`、`GenericDiffCalculatorInstance` |
| `org.anti_ad.mc.common.gen` | 31 | `ProfilesParser`、`ProfilesParserBaseListener`、`RulesParser` |
| `org.anti_ad.mc.ipnext.gui.inject` | 26 | `ContainerScreenEventHandler`、`ScreenEventHandler`、`ScreenEventHandler` |
| `org.anti_ad.mc.ipnext.parser` | 22 | `SyntaxErrorException`、`ProfilesLoader`、`RuleParser` |
| `org.anti_ad.mc.ipnext.item.rule.parameter` | 21 | `NativeParametersKt`、`ItemTypeMatcher`、`ItemNameArgumentType` |
| `org.anti_ad.mc.ipnext` | 17 | `IPNInfoManager`、`NotificationManager`、`IPNInfoManager` |
| `org.anti_ad.a.b.a.a.a.c` | 17 | `b`、`l`、`f` |
| `org.anti_ad.mc.ipnext.mixin` | 15 | `MixinScreenHandler`、`MixinClientPacketListener`、`MixinVirtualMouseHandler` |
| `org.anti_ad.mc.ipnext.integration` | 15 | `HintsManagerNG`、`HintClassData`、`HintsManagerNGKt` |
| `org.anti_ad.mc.ipnext.event.autorefill` | 13 | `AutoRefillHandler`、`AutoRefillHandler`、`AutoRefillHandler` |
| `org.anti_ad.mc.ipnext.input` | 13 | `CancellableInputHandler`、`InventoryInputHandler`、`InventoryInputHandler` |
| `org.anti_ad.mc.ipnext.inventory.data` | 13 | `MutableItemBucket`、`MutableItemTracker`、`ItemStat` |
| `org.anti_ad.mc.ipnext.event.villagers` | 12 | `VillagerTradeManager`、`VillagerDataManager`、`Config` |
| `org.anti_ad.a.b.a.a.a.d` | 12 | `j`、`a`、`f` |
| `org.anti_ad.mc.ipnext.item.rule.file` | 11 | `RuleFilePreprocessManagerKt`、`RuleFileRegister`、`SubRuleDefinition` |
| `org.anti_ad.mc.ipnext.profiles.config` | 10 | `ProfilesConfigParserKt`、`ProfileItemData`、`ProfilesConfig` |
| `org.anti_ad.mc.ipnext.inventory.action` | 10 | `SubTrackerActionsKt`、`GroupInColumnsCalculator`、`MiscKt` |
| `org.anti_ad.mc.ipnext.gui.inject.base` | 10 | `TexturedButtonWidget`、`SettingsWidget`、`SortButtonWidget` |
| `org.anti_ad.mc.ipnext.item.rule` | 10 | `ArgumentMap`、`BaseRule`、`CountSource` |
| `org.anti_ad.mc.ipnext.debug` | 9 | `BlocksWithScreensUtilsKt`、`AbstractBlockScreenScriptGenerator`、`BlocksWithScreensUtilsKt` |
| `org.anti_ad.a.b.a.a.a.d.a` | 9 | `f`、`h`、`b` |
| `org.anti_ad.mc.ipn.api` | 7 | `IPNIgnore`、`IPNStorageDimensionsHint`、`IPNPlayerSideOnly` |
| `org.anti_ad.mc.ipnext.neoforge` | 5 | `ForgeEventHandler`、`IPNForgeModInit`、`KotlinClientInit` |
| `org.anti_ad.mc.ipn.events.api` | 5 | `ActionExecutor`、`PredicateActionGroup`、`ActionExecutor` |
| `org.anti_ad.a.b.a.a.a.b` | 5 | `b`、`d`、`a` |
| `org.anti_ad.mc.ipnext.inventory.sandbox` | 4 | `ItemPlanner`、`SandboxClick`、`ContainerSandbox` |
| `org.anti_ad.mc.ipnext.access` | 4 | `PContainerClicker`、`IPNImpl`、`IPNImpl` |
| `org.anti_ad.mc.ipn.api.access` | 4 | `IPN`、`IPN`、`IPN` |
| `org.anti_ad.a.a.a.a` | 4 | `b`、`d`、`a` |
| `org.anti_ad.mc.ipn.features.scrolling` | 3 | `ScrollingUtils`、`ScrollingUtils`、`ScrollDirection` |
| `org.anti_ad.mc.ipnext.gui.widgets` | 3 | `Hintable`、`Hintable`、`Hintable` |
| `org.anti_ad.mc.ipnext.gui.base` | 3 | `InventoryOverlay`、`InventoryOverlay`、`InventoryOverlay` |
| … | | 其余 9 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `VillagerTradeManager` | class | IInputHandler | SourceDebugExtension | 39 | 1375 | [VillagerTradeManager.java](org/anti_ad/mc/ipnext/event/villagers/VillagerTradeManager.java) |
| `AutoRefillHandler` | class | — | SourceDebugExtension | 30 | 1142 | [AutoRefillHandler$ItemSlotMonitor.java](org/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor.java) |
| `ForgeEventHandler` | class | — | — | 12 | 113 | [ForgeEventHandler.java](org/anti_ad/mc/ipnext/neoforge/ForgeEventHandler.java) |
| `LockSlotsHandler` | class | InventoryOverlay | SourceDebugExtension | 50 | 681 | [LockSlotsHandler.java](org/anti_ad/mc/ipnext/event/LockSlotsHandler.java) |
| `ContainerScreenEventHandler` | class | — | SourceDebugExtension | 11 | 207 | [ContainerScreenEventHandler.java](org/anti_ad/mc/ipnext/gui/inject/ContainerScreenEventHandler.java) |
| `MixinScreenHandler` | class | — | Mixin | 2 | 50 | [MixinScreenHandler.java](org/anti_ad/mc/ipnext/mixin/MixinScreenHandler.java) |
| `VillagerDataManager` | class | — | SourceDebugExtension | 29 | 443 | [VillagerDataManager.java](org/anti_ad/mc/ipnext/event/villagers/VillagerDataManager.java) |
| `AutoRefillHandler` | class | InventoryOverlay | SourceDebugExtension | 37 | 510 | [AutoRefillHandler.java](org/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler.java) |
| `ItemTypeExtensionsKt` | class | — | SourceDebugExtension | 42 | 1092 | [ItemTypeExtensionsKt.java](org/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt.java) |
| `ScreenEventHandler` | class | — | SourceDebugExtension | 6 | 77 | [ScreenEventHandler.java](org/anti_ad/mc/ipnext/gui/inject/ScreenEventHandler.java) |
| `MixinClientPacketListener` | class | — | Mixin | 1 | 27 | [MixinClientPacketListener.java](org/anti_ad/mc/ipnext/mixin/MixinClientPacketListener.java) |
| `MixinVirtualMouseHandler` | class | — | Mixin | 0 | 36 | [MixinVirtualMouseHandler.java](org/anti_ad/mc/ipnext/mixin/MixinVirtualMouseHandler.java) |
| `CutterCraftingHandlerBase` | class | — | SourceDebugExtension | 24 | 428 | [CutterCraftingHandlerBase.java](org/anti_ad/mc/ipnext/event/CutterCraftingHandlerBase.java) |
| `ClientEventHandler` | class | PClientEventHandler | — | 8 | 101 | [ClientEventHandler.java](org/anti_ad/mc/ipnext/event/ClientEventHandler.java) |
| `SlotHighlightHandler` | class | InventoryOverlay | SourceDebugExtension | 24 | 424 | [SlotHighlightHandler.java](org/anti_ad/mc/ipnext/event/SlotHighlightHandler.java) |
| `HintsManagerNG` | class | — | SourceDebugExtension | 16 | 644 | [HintsManagerNG.java](org/anti_ad/mc/ipnext/integration/HintsManagerNG.java) |
| `Y` | class | j | — | 30 | 824 | [Y.java](org/anti_ad/a/b/a/a/a/a/Y.java) |
| `ScreenEventHandler` | class | — | SourceDebugExtension | 1 | 29 | [ScreenEventHandler$special$$inlined$detectable$1.java](org/anti_ad/mc/ipnext/gui/inject/ScreenEventHandler$special$$inlined$detectable$1.java) |
| `IPNForgeModInit` | class | — | Mod | 0 | 33 | [IPNForgeModInit.java](org/anti_ad/mc/ipnext/neoforge/IPNForgeModInit.java) |
| `ProfileSwitchHandler` | class | IInputHandler | SourceDebugExtension | 19 | 431 | [ProfileSwitchHandler.java](org/anti_ad/mc/ipnext/event/ProfileSwitchHandler.java) |
| `AutoRefillHandler` | class | SpecificItemSlotMonitor | SourceDebugExtension | 7 | 870 | [AutoRefillHandler$ItemSlotMonitor$Companion.java](org/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion.java) |
| `ModSettings` | class | ConfigDeclaration | — | 49 | 866 | [ModSettings.java](org/anti_ad/mc/ipnext/config/ModSettings.java) |
| `PClientEventHandler` | class | — | SourceDebugExtension | 1 | 77 | [PClientEventHandler$DefaultImpls.java](org/anti_ad/mc/ipnext/specific/event/PClientEventHandler$DefaultImpls.java) |
| `PClientEventHandler` | interface | — | — | 0 | 11 | [PClientEventHandler.java](org/anti_ad/mc/ipnext/specific/event/PClientEventHandler.java) |
| `ScrollingUtils` | class | — | SourceDebugExtension | 22 | 1551 | [ScrollingUtils.java](org/anti_ad/mc/ipn/features/scrolling/ScrollingUtils.java) |
| `MixinServerBoundInteractPacket` | class | — | Mixin | 1 | 32 | [MixinServerBoundInteractPacket.java](org/anti_ad/mc/ipnext/mixin/MixinServerBoundInteractPacket.java) |
| `Config` | class | — | Serializable、SourceDebugExtension | 23 | 354 | [Config.java](org/anti_ad/mc/ipnext/event/villagers/Config.java) |
| `AutoRefillSettings` | class | ConfigDeclaration | — | 36 | 676 | [AutoRefillSettings.java](org/anti_ad/mc/ipnext/config/AutoRefillSettings.java) |
| `D` | class | K | — | 61 | 555 | [D.java](org/anti_ad/a/b/a/a/a/D.java) |
| `IPNInfoManager` | class | InfoManagerBase | — | 17 | 177 | [IPNInfoManager.java](org/anti_ad/mc/ipnext/IPNInfoManager.java) |
| `GeneralInventoryActions` | class | — | SourceDebugExtension | 20 | 825 | [GeneralInventoryActions.java](org/anti_ad/mc/ipnext/inventory/GeneralInventoryActions.java) |
| `SimpleDiffCalculatorInstance` | class | DiffCalculatorInstance | SourceDebugExtension | 29 | 686 | [SimpleDiffCalculatorInstance.java](org/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SimpleDiffCalculatorInstance.java) |
| `InsertWidgetHandler` | class | ScreenEventListener | SourceDebugExtension | 16 | 180 | [InsertWidgetHandler.java](org/anti_ad/mc/ipnext/gui/inject/InsertWidgetHandler.java) |
| `MixinAbstractContainerScreen` | class | — | Mixin | 2 | 46 | [MixinAbstractContainerScreen.java](org/anti_ad/mc/ipnext/mixin/MixinAbstractContainerScreen.java) |
| `MixinTraderTileEntityBase` | class | — | Mixin | 1 | 23 | [MixinTraderTileEntityBase.java](org/anti_ad/mc/ipnext/mixin/MixinTraderTileEntityBase.java) |
| `MixinMerchantScreen` | class | — | Mixin | 0 | 54 | [MixinMerchantScreen.java](org/anti_ad/mc/ipnext/mixin/MixinMerchantScreen.java) |
| `MixinItemGroup` | class | IMixinItemGroup | Mixin | 2 | 23 | [MixinItemGroup.java](org/anti_ad/mc/ipnext/mixin/MixinItemGroup.java) |
| `MixinMinecraftClient` | class | — | Mixin | 3 | 61 | [MixinMinecraftClient.java](org/anti_ad/mc/ipnext/mixin/MixinMinecraftClient.java) |
| `MixinAnvilMenu` | class | — | Mixin | 2 | 36 | [MixinAnvilMenu.java](org/anti_ad/mc/ipnext/mixin/MixinAnvilMenu.java) |
| `AnvilHandler` | class | — | SourceDebugExtension | 10 | 228 | [AnvilHandler.java](org/anti_ad/mc/ipnext/event/AnvilHandler.java) |
| `HintClassData` | class | — | Serializable、SourceDebugExtension | 33 | 340 | [HintClassData.java](org/anti_ad/mc/ipnext/integration/HintClassData.java) |
| `Hotkeys` | class | ConfigDeclaration | — | 35 | 547 | [Hotkeys.java](org/anti_ad/mc/ipnext/config/Hotkeys.java) |
| `GuiSettings` | class | ConfigDeclaration | — | 33 | 579 | [GuiSettings.java](org/anti_ad/mc/ipnext/config/GuiSettings.java) |
| `DefinedNativeRulesKt` | class | — | SourceDebugExtension | 27 | 725 | [DefinedNativeRulesKt.java](org/anti_ad/mc/ipnext/item/rule/natives/DefinedNativeRulesKt.java) |
| `z` | class | K | — | 38 | 324 | [z.java](org/anti_ad/a/b/a/a/a/z.java) |
| `MixinPlayerInventory` | class | — | Mixin | 5 | 86 | [MixinPlayerInventory.java](org/anti_ad/mc/ipnext/mixin/MixinPlayerInventory.java) |
| `ContinuousCraftingHandler` | class | ContinuousCraftingHandler$IMonitor | SourceDebugExtension | 7 | 372 | [ContinuousCraftingHandler$Monitor.java](org/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$Monitor.java) |
| `ClientInitHandler` | class | — | SourceDebugExtension | 4 | 51 | [ClientInitHandler.java](org/anti_ad/mc/ipnext/event/ClientInitHandler.java) |
| `LockedSlotKeeper` | class | — | SourceDebugExtension | 22 | 765 | [LockedSlotKeeper.java](org/anti_ad/mc/ipnext/event/LockedSlotKeeper.java) |
| `ContinuousCraftingHandler` | class | ContinuousCraftingHandler$IMonitor | SourceDebugExtension | 7 | 279 | [ContinuousCraftingHandler$MonitorP.java](org/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$MonitorP.java) |
| `VillagerDataManager` | class | Function1 | — | 5 | 58 | [VillagerDataManager$saveIfDirty$task$1.java](org/anti_ad/mc/ipnext/event/villagers/VillagerDataManager$saveIfDirty$task$1.java) |
| `ConfigScreenSettings` | class | BaseConfigScreenSettings | SourceDebugExtension | 9 | 144 | [ConfigScreenSettings.java](org/anti_ad/mc/ipnext/config/ConfigScreenSettings.java) |
| `ItemType` | class | CountSink | — | 21 | 235 | [ItemType.java](org/anti_ad/mc/ipnext/item/ItemType.java) |
| `RuleFilePreprocessManagerKt` | class | — | SourceDebugExtension | 7 | 218 | [RuleFilePreprocessManagerKt.java](org/anti_ad/mc/ipnext/item/rule/file/RuleFilePreprocessManagerKt.java) |
| `b` | class | Set | — | 24 | 429 | [b.java](org/anti_ad/a/b/a/a/a/c/b.java) |
| `SingleType` | class | Comparable | SourceDebugExtension | 28 | 345 | [SingleType$Node.java](org/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SingleType$Node.java) |
| `BlocksWithScreensUtilsKt` | class | — | SourceDebugExtension | 4 | 354 | [BlocksWithScreensUtilsKt.java](org/anti_ad/mc/ipnext/debug/BlocksWithScreensUtilsKt.java) |
| `AbstractBlockScreenScriptGenerator` | class | ConfigButtonInfo | SourceDebugExtension | 10 | 113 | [AbstractBlockScreenScriptGenerator.java](org/anti_ad/mc/ipnext/debug/AbstractBlockScreenScriptGenerator.java) |
| `ProfilesConfigParserKt` | class | — | SourceDebugExtension | 5 | 245 | [ProfilesConfigParserKt.java](org/anti_ad/mc/ipnext/profiles/config/ProfilesConfigParserKt.java) |
| `MixinPlayerController` | class | — | Mixin | 2 | 23 | [MixinPlayerController.java](org/anti_ad/mc/ipnext/mixin/MixinPlayerController.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ForgeEventHandler` | 12 | `org.anti_ad.mc.ipnext.neoforge` | [ForgeEventHandler.java](org/anti_ad/mc/ipnext/neoforge/ForgeEventHandler.java) |

## Mixin（15 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinScreenHandler` | `ScreenHandler` | [MixinScreenHandler.java](org/anti_ad/mc/ipnext/mixin/MixinScreenHandler.java) |
| `MixinClientPacketListener` | `ClientPacketListener` | [MixinClientPacketListener.java](org/anti_ad/mc/ipnext/mixin/MixinClientPacketListener.java) |
| `MixinVirtualMouseHandler` | `VirtualMouseHandler` | [MixinVirtualMouseHandler.java](org/anti_ad/mc/ipnext/mixin/MixinVirtualMouseHandler.java) |
| `MixinServerBoundInteractPacket` | `ServerBoundInteractPacket` | [MixinServerBoundInteractPacket.java](org/anti_ad/mc/ipnext/mixin/MixinServerBoundInteractPacket.java) |
| `MixinAbstractContainerScreen` | `AbstractContainerScreen` | [MixinAbstractContainerScreen.java](org/anti_ad/mc/ipnext/mixin/MixinAbstractContainerScreen.java) |
| `MixinTraderTileEntityBase` | `TraderTileEntityBase` | [MixinTraderTileEntityBase.java](org/anti_ad/mc/ipnext/mixin/MixinTraderTileEntityBase.java) |
| `MixinMerchantScreen` | `MerchantScreen` | [MixinMerchantScreen.java](org/anti_ad/mc/ipnext/mixin/MixinMerchantScreen.java) |
| `MixinItemGroup` | `ItemGroup` | [MixinItemGroup.java](org/anti_ad/mc/ipnext/mixin/MixinItemGroup.java) |
| `MixinMinecraftClient` | `MinecraftClient` | [MixinMinecraftClient.java](org/anti_ad/mc/ipnext/mixin/MixinMinecraftClient.java) |
| `MixinAnvilMenu` | `AnvilMenu` | [MixinAnvilMenu.java](org/anti_ad/mc/ipnext/mixin/MixinAnvilMenu.java) |
| `MixinPlayerInventory` | `PlayerInventory` | [MixinPlayerInventory.java](org/anti_ad/mc/ipnext/mixin/MixinPlayerInventory.java) |
| `MixinPlayerController` | `PlayerController` | [MixinPlayerController.java](org/anti_ad/mc/ipnext/mixin/MixinPlayerController.java) |
| `MixinMultiPlayerGameMode` | `MultiPlayerGameMode` | [MixinMultiPlayerGameMode.java](org/anti_ad/mc/ipnext/mixin/MixinMultiPlayerGameMode.java) |
| `MixinInGameHud` | `InGameHud` | [MixinInGameHud.java](org/anti_ad/mc/ipnext/mixin/MixinInGameHud.java) |
| `IMixinKeyBinding` | `IKeyBinding` | [IMixinKeyBinding.java](org/anti_ad/mc/ipnext/mixin/IMixinKeyBinding.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | Serializable;SourceDebugExtension | [Config.java](org/anti_ad/mc/ipnext/event/villagers/Config.java) |
| `ConfigScreenSettings` | SourceDebugExtension | [ConfigScreenSettings.java](org/anti_ad/mc/ipnext/config/ConfigScreenSettings.java) |
| `ProfilesConfigParserKt` | SourceDebugExtension | [ProfilesConfigParserKt.java](org/anti_ad/mc/ipnext/profiles/config/ProfilesConfigParserKt.java) |
| `ConfigScreenSettingsKt` | — | [ConfigScreenSettingsKt.java](org/anti_ad/mc/ipnext/config/ConfigScreenSettingsKt.java) |
| `ProfilesConfig` | — | [ProfilesConfig$getProfiles$2.java](org/anti_ad/mc/ipnext/profiles/config/ProfilesConfig$getProfiles$2.java) |
| `ProfilesConfig` | — | [ProfilesConfig$getProfiles$1.java](org/anti_ad/mc/ipnext/profiles/config/ProfilesConfig$getProfiles$1.java) |
| `ProfilesConfig` | SourceDebugExtension | [ProfilesConfig.java](org/anti_ad/mc/ipnext/profiles/config/ProfilesConfig.java) |
| `Config` | — | [Config$$serializer.java](org/anti_ad/mc/ipnext/event/villagers/Config$$serializer.java) |
| `Config` | — | [Config$Companion.java](org/anti_ad/mc/ipnext/event/villagers/Config$Companion.java) |
| `ConfigEnumsExtKt` | — | [ConfigEnumsExtKt$WhenMappings.java](org/anti_ad/mc/ipnext/config/ConfigEnumsExtKt$WhenMappings.java) |
| `DefaultDelegatedConfigButtonInfo` | — | [DefaultDelegatedConfigButtonInfo.java](org/anti_ad/mc/ipnext/config/DefaultDelegatedConfigButtonInfo.java) |
| `ConfigEnumsExtKt` | SourceDebugExtension | [ConfigEnumsExtKt.java](org/anti_ad/mc/ipnext/config/ConfigEnumsExtKt.java) |
| `ConfigEnumsKt` | — | [ConfigEnumsKt.java](org/anti_ad/mc/ipnext/config/ConfigEnumsKt.java) |
| `ConfigsKt` | — | [ConfigsKt.java](org/anti_ad/mc/ipnext/config/ConfigsKt.java) |
| `OpenConfigFolderButtonInfo` | — | [OpenConfigFolderButtonInfo.java](org/anti_ad/mc/ipnext/config/OpenConfigFolderButtonInfo.java) |
| `CustomConfigButtonKt` | — | [CustomConfigButtonKt.java](org/anti_ad/mc/ipnext/config/CustomConfigButtonKt.java) |
| `OpenProfilesConfigButtonInfo` | — | [OpenProfilesConfigButtonInfo.java](org/anti_ad/mc/ipnext/config/OpenProfilesConfigButtonInfo.java) |

## 按包浏览

- `org.anti_ad.a.a.a.a`（4 类）
- `org.anti_ad.a.b.a.a.a`（47 类）
- `org.anti_ad.a.b.a.a.a.a`（83 类）
- `org.anti_ad.a.b.a.a.a.b`（5 类）
- `org.anti_ad.a.b.a.a.a.c`（17 类）
- `org.anti_ad.a.b.a.a.a.d`（12 类）
- `org.anti_ad.a.b.a.a.a.d.a`（9 类）
- `org.anti_ad.mc.common.gen`（31 类）
- `org.anti_ad.mc.common.vanilla.accessors.menu`（1 类）
- `org.anti_ad.mc.ipn.api`（7 类）
- `org.anti_ad.mc.ipn.api.access`（4 类）
- `org.anti_ad.mc.ipn.events.api`（5 类）
- `org.anti_ad.mc.ipn.features.scrolling`（3 类）
- `org.anti_ad.mc.ipnext`（17 类）
- `org.anti_ad.mc.ipnext.access`（4 类）
- `org.anti_ad.mc.ipnext.compat.integrations`（1 类）
- `org.anti_ad.mc.ipnext.config`（40 类）
- `org.anti_ad.mc.ipnext.config.defaults`（1 类）
- `org.anti_ad.mc.ipnext.container`（1 类）
- `org.anti_ad.mc.ipnext.debug`（9 类）
- `org.anti_ad.mc.ipnext.event`（37 类）
- `org.anti_ad.mc.ipnext.event.autorefill`（13 类）
- `org.anti_ad.mc.ipnext.event.villagers`（12 类）
- `org.anti_ad.mc.ipnext.gui`（33 类）
- `org.anti_ad.mc.ipnext.gui.base`（3 类）
- `org.anti_ad.mc.ipnext.gui.inject`（26 类）
- `org.anti_ad.mc.ipnext.gui.inject.base`（10 类）
- `org.anti_ad.mc.ipnext.gui.widgets`（3 类）
- `org.anti_ad.mc.ipnext.ingame`（3 类）
- `org.anti_ad.mc.ipnext.input`（13 类）
- `org.anti_ad.mc.ipnext.integration`（15 类）
- `org.anti_ad.mc.ipnext.inventory`（37 类）
- `org.anti_ad.mc.ipnext.inventory.action`（10 类）
- `org.anti_ad.mc.ipnext.inventory.data`（13 类）
- `org.anti_ad.mc.ipnext.inventory.sandbox`（4 类）
- `org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator`（31 类）
- `org.anti_ad.mc.ipnext.item`（36 类）
- `org.anti_ad.mc.ipnext.item.rule`（10 类）
- `org.anti_ad.mc.ipnext.item.rule.file`（11 类）
- `org.anti_ad.mc.ipnext.item.rule.natives`（51 类）
- `org.anti_ad.mc.ipnext.item.rule.parameter`（21 类）
- `org.anti_ad.mc.ipnext.mixin`（15 类）
- `org.anti_ad.mc.ipnext.mixinhelpers`（1 类）
- `org.anti_ad.mc.ipnext.neoforge`（5 类）
- `org.anti_ad.mc.ipnext.parser`（22 类）
- `org.anti_ad.mc.ipnext.profiles.config`（10 类）
- `org.anti_ad.mc.ipnext.specific`（1 类）
- `org.anti_ad.mc.ipnext.specific.event`（2 类）
- `org.anti_ad.mc.ipnext.util`（2 类）
