# FTB Library（`ftblibrary`）

> jar：`[前置]ftb-library.jar` ｜ 版本：`2101.1.33` ｜ 作者：FTB Team
> 规模：**247 个类 / 35 个包 / 23341 行** ｜ 事件处理类 0 ｜ Mixin 5 ｜ 注册/管理类 6

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftblibrary.ui` | 41 | `BaseScreen`、`TextBox`、`Panel` |
| `dev.ftb.mods.ftblibrary.icon` | 27 | `Color4I`、`Icon`、`EntityIconLoader` |
| `dev.ftb.mods.ftblibrary.config` | 25 | `ConfigGroup`、`ConfigValue`、`EntityFaceConfig` |
| `dev.ftb.mods.ftblibrary.util` | 25 | `NetworkHelper`、`StringUtils`、`Vec2d` |
| `dev.ftb.mods.ftblibrary.snbt.config` | 14 | `SNBTConfig`、`ConfigUtil`、`BaseValue` |
| `dev.ftb.mods.ftblibrary.config.ui.resource` | 12 | `ResourceSelectorScreen`、`SelectEntityFaceScreen`、`SelectItemStackScreen` |
| `dev.ftb.mods.ftblibrary.net` | 10 | `EditConfigChoicePacket`、`SyncConfigToServerPacket`、`EditConfigPacket` |
| `dev.ftb.mods.ftblibrary.ui.misc` | 10 | `AbstractThreePanelScreen`、`AbstractGroupedButtonListScreen`、`KeyReferenceScreen` |
| `dev.ftb.mods.ftblibrary.snbt` | 10 | `SNBTParser`、`SNBT`、`SNBTCompoundTag` |
| `dev.ftb.mods.ftblibrary.sidebar` | 6 | `SidebarButtonManager`、`SidebarGroupGuiButton`、`RegisteredSidebarButton` |
| `dev.ftb.mods.ftblibrary.math` | 6 | `MathUtils`、`Bits`、`PixelBuffer` |
| `dev.ftb.mods.ftblibrary.config.ui` | 5 | `EditConfigScreen`、`EditConfigListScreen`、`ChooseConfigScreen` |
| `dev.ftb.mods.ftblibrary.core.mixin.common` | 5 | `ItemMixin`、`ResourceLocationMixin`、`CompoundContainerMixin` |
| `dev.ftb.mods.ftblibrary.util.neoforge` | 5 | `NetworkHelperImpl`、`ItemKey`、`TextComponentUtilsImpl` |
| `dev.ftb.mods.ftblibrary` | 4 | `FTBLibraryCommands`、`FTBLibraryClient`、`FTBLibrary` |
| `dev.ftb.mods.ftblibrary.util.client` | 4 | `ClientUtils`、`ImageComponent`、`ClientTextComponentUtils` |
| `dev.ftb.mods.ftblibrary.integration` | 3 | `JEIIntegration`、`EMIIntegration`、`REIIntegration` |
| `dev.ftb.mods.ftblibrary.integration.stages` | 3 | `EntityTagStageProvider`、`StageHelper`、`StageProvider` |
| `dev.ftb.mods.ftblibrary.api.sidebar` | 3 | `SidebarButtonCreatedEvent`、`SidebarButton`、`ButtonOverlayRender` |
| `dev.ftb.mods.ftblibrary.ui.input` | 3 | `Key`、`KeyModifiers`、`MouseButton` |
| `dev.ftb.mods.ftblibrary.integration.currency` | 3 | `FallbackCurrencyProvider`、`CurrencyHelper`、`CurrencyProvider` |
| `dev.ftb.mods.ftblibrary.integration.permissions` | 3 | `PermissionHelper`、`PermissionProvider`、`FallbackPermissionProvider` |
| `dev.ftb.mods.ftblibrary.util.text` | 3 | `CustomTextColor`、`ExtendableTextColor`、`RainbowTextColor` |
| `dev.ftb.mods.ftblibrary.config.manager` | 2 | `ConfigManager`、`ConfigManagerClient` |
| `dev.ftb.mods.ftblibrary.nbtedit` | 2 | `NBTEditorScreen`、`NBTEditResponseHandlers` |
| `dev.ftb.mods.ftblibrary.integration.docsmod` | 2 | `DocsModRegistry`、`DocsMod` |
| `dev.ftb.mods.ftblibrary.core` | 2 | `ItemFTBL`、`CompoundContainerFTBL` |
| `dev.ftb.mods.ftblibrary.integration.neoforge` | 2 | `JEIIntegrationImpl`、`REINeoforgePluginStub` |
| `dev.ftb.mods.ftblibrary.neoforge` | 1 | `FTBLibraryNeoForge` |
| `dev.ftb.mods.ftblibrary.api.color` | 1 | `RegisterCustomColorEvent` |
| `dev.ftb.mods.ftblibrary.util.client.neoforge` | 1 | `ClientUtilsImpl` |
| `dev.ftb.mods.ftblibrary.items` | 1 | `ModItems` |
| `dev.ftb.mods.ftblibrary.api.client` | 1 | `FTBLibraryClientApi` |
| `dev.ftb.mods.ftblibrary.ui.input.neoforge` | 1 | `KeyImpl` |
| `architectury_inject_FTBLibrary121_common_e8b82fb0f9004e28b3411b262ca0462f_a3e23a2f926a73d7d787c024e4805d09672c0f804894df4d7750775bc31ac2e6ftblibrary2101133devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EditConfigScreen` | class | AbstractThreePanelScreen | — | 27 | 408 | [EditConfigScreen.java](dev/ftb/mods/ftblibrary/config/ui/EditConfigScreen.java) |
| `ConfigManager` | enum | — | — | 15 | 206 | [ConfigManager.java](dev/ftb/mods/ftblibrary/config/manager/ConfigManager.java) |
| `NBTEditorScreen` | class | AbstractThreePanelScreen | — | 78 | 1076 | [NBTEditorScreen.java](dev/ftb/mods/ftblibrary/nbtedit/NBTEditorScreen.java) |
| `EditConfigListScreen` | class | SimpleButton | — | 25 | 318 | [EditConfigListScreen.java](dev/ftb/mods/ftblibrary/config/ui/EditConfigListScreen.java) |
| `ConfigGroup` | class | Comparable<ConfigGroup> | — | 34 | 219 | [ConfigGroup.java](dev/ftb/mods/ftblibrary/config/ConfigGroup.java) |
| `ConfigManagerClient` | class | — | — | 4 | 31 | [ConfigManagerClient.java](dev/ftb/mods/ftblibrary/config/manager/ConfigManagerClient.java) |
| `BaseScreen` | class | Panel | — | 69 | 562 | [BaseScreen.java](dev/ftb/mods/ftblibrary/ui/BaseScreen.java) |
| `FTBLibraryNeoForge` | class | — | Mod | 0 | 12 | [FTBLibraryNeoForge.java](dev/ftb/mods/ftblibrary/neoforge/FTBLibraryNeoForge.java) |
| `ConfigValue` | class | Comparable<ConfigValue<T>> | — | 30 | 173 | [ConfigValue.java](dev/ftb/mods/ftblibrary/config/ConfigValue.java) |
| `ResourceSelectorScreen` | class | AbstractThreePanelScreen | — | 48 | 523 | [ResourceSelectorScreen.java](dev/ftb/mods/ftblibrary/config/ui/resource/ResourceSelectorScreen.java) |
| `Color4I` | class | Icon | — | 51 | 794 | [Color4I.java](dev/ftb/mods/ftblibrary/icon/Color4I.java) |
| `SNBTConfig` | class | BaseValue | — | 25 | 192 | [SNBTConfig.java](dev/ftb/mods/ftblibrary/snbt/config/SNBTConfig.java) |
| `ItemMixin` | class | ItemFTBL | Mixin | 1 | 16 | [ItemMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/ItemMixin.java) |
| `TextBox` | class | Widget | — | 42 | 510 | [TextBox.java](dev/ftb/mods/ftblibrary/ui/TextBox.java) |
| `Panel` | class | Widget | — | 47 | 530 | [Panel.java](dev/ftb/mods/ftblibrary/ui/Panel.java) |
| `ColorSelectorPanel` | class | ModalPanel | — | 33 | 468 | [ColorSelectorPanel.java](dev/ftb/mods/ftblibrary/ui/ColorSelectorPanel.java) |
| `EditConfigChoicePacket` | record | CustomPacketPayload | — | 5 | 72 | [EditConfigChoicePacket.java](dev/ftb/mods/ftblibrary/net/EditConfigChoicePacket.java) |
| `NetworkHelper` | class | — | — | 18 | 211 | [NetworkHelper.java](dev/ftb/mods/ftblibrary/util/NetworkHelper.java) |
| `Widget` | class | IScreenWrapper | — | 58 | 304 | [Widget.java](dev/ftb/mods/ftblibrary/ui/Widget.java) |
| `MenuScreenWrapper` | class | AbstractContainerScreen | — | 16 | 175 | [MenuScreenWrapper.java](dev/ftb/mods/ftblibrary/ui/MenuScreenWrapper.java) |
| `MultilineTextBox` | class | Widget | — | 34 | 329 | [MultilineTextBox.java](dev/ftb/mods/ftblibrary/ui/MultilineTextBox.java) |
| `ContextMenuItem` | class | Comparable<ContextMenuItem> | — | 17 | 118 | [ContextMenuItem.java](dev/ftb/mods/ftblibrary/ui/ContextMenuItem.java) |
| `AbstractThreePanelScreen` | class | BaseScreen | — | 24 | 215 | [AbstractThreePanelScreen.java](dev/ftb/mods/ftblibrary/ui/misc/AbstractThreePanelScreen.java) |
| `AbstractGroupedButtonListScreen` | class | AbstractButtonListScreen | — | 23 | 240 | [AbstractGroupedButtonListScreen.java](dev/ftb/mods/ftblibrary/ui/misc/AbstractGroupedButtonListScreen.java) |
| `EntityFaceConfig` | class | ResourceConfigValue | — | 7 | 65 | [EntityFaceConfig.java](dev/ftb/mods/ftblibrary/config/EntityFaceConfig.java) |
| `ItemStackConfig` | class | ResourceConfigValue | — | 9 | 84 | [ItemStackConfig.java](dev/ftb/mods/ftblibrary/config/ItemStackConfig.java) |
| `ChooseConfigScreen` | class | BaseScreen | — | 7 | 96 | [ChooseConfigScreen.java](dev/ftb/mods/ftblibrary/config/ui/ChooseConfigScreen.java) |
| `SyncConfigToServerPacket` | record | CustomPacketPayload | — | 3 | 51 | [SyncConfigToServerPacket.java](dev/ftb/mods/ftblibrary/net/SyncConfigToServerPacket.java) |
| `EditConfigPacket` | record | CustomPacketPayload | — | 2 | 26 | [EditConfigPacket.java](dev/ftb/mods/ftblibrary/net/EditConfigPacket.java) |
| `SyncConfigFromServerPacket` | record | CustomPacketPayload | — | 3 | 37 | [SyncConfigFromServerPacket.java](dev/ftb/mods/ftblibrary/net/SyncConfigFromServerPacket.java) |
| `StringUtils` | class | — | — | 25 | 334 | [StringUtils.java](dev/ftb/mods/ftblibrary/util/StringUtils.java) |
| `FTBLibraryCommands` | class | — | — | 12 | 265 | [FTBLibraryCommands.java](dev/ftb/mods/ftblibrary/FTBLibraryCommands.java) |
| `Icon` | class | Drawable | — | 27 | 309 | [Icon.java](dev/ftb/mods/ftblibrary/icon/Icon.java) |
| `ResourceLocationMixin` | class | — | Mixin | 2 | 21 | [ResourceLocationMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/ResourceLocationMixin.java) |
| `CompoundContainerMixin` | class | CompoundContainerFTBL | Mixin | 2 | 19 | [CompoundContainerMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/CompoundContainerMixin.java) |
| `MultilineTextFieldAccess` | interface | — | Mixin | 0 | 14 | [MultilineTextFieldAccess.java](dev/ftb/mods/ftblibrary/core/mixin/common/MultilineTextFieldAccess.java) |
| `TextColorMixin` | class | — | Mixin | 1 | 26 | [TextColorMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/TextColorMixin.java) |
| `KeyReferenceScreen` | class | BaseScreen | — | 15 | 202 | [KeyReferenceScreen.java](dev/ftb/mods/ftblibrary/ui/misc/KeyReferenceScreen.java) |
| `DocsModRegistry` | enum | — | — | 2 | 22 | [DocsModRegistry.java](dev/ftb/mods/ftblibrary/integration/docsmod/DocsModRegistry.java) |
| `EnumConfig` | class | ConfigWithVariants | — | 11 | 143 | [EnumConfig.java](dev/ftb/mods/ftblibrary/config/EnumConfig.java) |
| `FTBLibraryClientConfig` | interface | — | — | 2 | 63 | [FTBLibraryClientConfig.java](dev/ftb/mods/ftblibrary/config/FTBLibraryClientConfig.java) |
| `ResourceConfigValue` | class | ConfigValue | — | 13 | 68 | [ResourceConfigValue.java](dev/ftb/mods/ftblibrary/config/ResourceConfigValue.java) |
| `EditStringConfigOverlay` | class | ModalPanel | — | 14 | 178 | [EditStringConfigOverlay.java](dev/ftb/mods/ftblibrary/config/ui/EditStringConfigOverlay.java) |
| `SidebarButtonManager` | class | SimpleJsonResourceReloadListener | — | 8 | 161 | [SidebarButtonManager.java](dev/ftb/mods/ftblibrary/sidebar/SidebarButtonManager.java) |
| `ScreenWrapper` | class | Screen | — | 17 | 160 | [ScreenWrapper.java](dev/ftb/mods/ftblibrary/ui/ScreenWrapper.java) |
| `Theme` | class | — | — | 27 | 199 | [Theme.java](dev/ftb/mods/ftblibrary/ui/Theme.java) |
| `GuiHelper` | class | — | — | 19 | 271 | [GuiHelper.java](dev/ftb/mods/ftblibrary/ui/GuiHelper.java) |
| `ThemeManager` | enum | — | — | 1 | 10 | [ThemeManager.java](dev/ftb/mods/ftblibrary/ui/ThemeManager.java) |
| `ButtonListBaseScreen` | class | BaseScreen | — | 16 | 148 | [ButtonListBaseScreen.java](dev/ftb/mods/ftblibrary/ui/misc/ButtonListBaseScreen.java) |
| `AbstractButtonListScreen` | class | AbstractThreePanelScreen | — | 17 | 146 | [AbstractButtonListScreen.java](dev/ftb/mods/ftblibrary/ui/misc/AbstractButtonListScreen.java) |
| `NameMap` | class | Iterable<E> | — | 27 | 177 | [NameMap.java](dev/ftb/mods/ftblibrary/config/NameMap.java) |
| `ConfigFromString` | class | ConfigValue | — | 7 | 53 | [ConfigFromString.java](dev/ftb/mods/ftblibrary/config/ConfigFromString.java) |
| `ImageResourceConfig` | class | ResourceConfigValue | — | 7 | 67 | [ImageResourceConfig.java](dev/ftb/mods/ftblibrary/config/ImageResourceConfig.java) |
| `FluidConfig` | class | ResourceConfigValue | — | 8 | 78 | [FluidConfig.java](dev/ftb/mods/ftblibrary/config/FluidConfig.java) |
| `ListConfig` | class | — | — | 7 | 88 | [ListConfig.java](dev/ftb/mods/ftblibrary/config/ListConfig.java) |
| `NumberConfig` | class | ConfigFromString | — | 6 | 47 | [NumberConfig.java](dev/ftb/mods/ftblibrary/config/NumberConfig.java) |
| `NBTConfig` | class | ConfigFromString | — | 5 | 45 | [NBTConfig.java](dev/ftb/mods/ftblibrary/config/NBTConfig.java) |
| `EditMultilineStringConfigOverlay` | class | ModalPanel | — | 9 | 112 | [EditMultilineStringConfigOverlay.java](dev/ftb/mods/ftblibrary/config/ui/EditMultilineStringConfigOverlay.java) |
| `NBTEditResponseHandlers` | enum | — | — | 4 | 94 | [NBTEditResponseHandlers.java](dev/ftb/mods/ftblibrary/nbtedit/NBTEditResponseHandlers.java) |
| `ClientUtils` | class | — | — | 10 | 169 | [ClientUtils.java](dev/ftb/mods/ftblibrary/util/client/ClientUtils.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemMixin` | `Item` | [ItemMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/ItemMixin.java) |
| `ResourceLocationMixin` | `ResourceLocation` | [ResourceLocationMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/ResourceLocationMixin.java) |
| `CompoundContainerMixin` | `CompoundContainer` | [CompoundContainerMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/CompoundContainerMixin.java) |
| `MultilineTextFieldAccess` | `MultilineTextFieldAccess` | [MultilineTextFieldAccess.java](dev/ftb/mods/ftblibrary/core/mixin/common/MultilineTextFieldAccess.java) |
| `TextColorMixin` | `TextColor` | [TextColorMixin.java](dev/ftb/mods/ftblibrary/core/mixin/common/TextColorMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `EditConfigScreen` | — | [EditConfigScreen.java](dev/ftb/mods/ftblibrary/config/ui/EditConfigScreen.java) |
| `ConfigManager` | — | [ConfigManager.java](dev/ftb/mods/ftblibrary/config/manager/ConfigManager.java) |
| `EditConfigListScreen` | — | [EditConfigListScreen.java](dev/ftb/mods/ftblibrary/config/ui/EditConfigListScreen.java) |
| `ConfigGroup` | — | [ConfigGroup.java](dev/ftb/mods/ftblibrary/config/ConfigGroup.java) |
| `ConfigManagerClient` | — | [ConfigManagerClient.java](dev/ftb/mods/ftblibrary/config/manager/ConfigManagerClient.java) |
| `ConfigValue` | — | [ConfigValue.java](dev/ftb/mods/ftblibrary/config/ConfigValue.java) |
| `SNBTConfig` | — | [SNBTConfig.java](dev/ftb/mods/ftblibrary/snbt/config/SNBTConfig.java) |
| `EditConfigChoicePacket` | — | [EditConfigChoicePacket.java](dev/ftb/mods/ftblibrary/net/EditConfigChoicePacket.java) |
| `EntityFaceConfig` | — | [EntityFaceConfig.java](dev/ftb/mods/ftblibrary/config/EntityFaceConfig.java) |
| `ItemStackConfig` | — | [ItemStackConfig.java](dev/ftb/mods/ftblibrary/config/ItemStackConfig.java) |
| `ChooseConfigScreen` | — | [ChooseConfigScreen.java](dev/ftb/mods/ftblibrary/config/ui/ChooseConfigScreen.java) |
| `SyncConfigToServerPacket` | — | [SyncConfigToServerPacket.java](dev/ftb/mods/ftblibrary/net/SyncConfigToServerPacket.java) |
| `EditConfigPacket` | — | [EditConfigPacket.java](dev/ftb/mods/ftblibrary/net/EditConfigPacket.java) |
| `SyncConfigFromServerPacket` | — | [SyncConfigFromServerPacket.java](dev/ftb/mods/ftblibrary/net/SyncConfigFromServerPacket.java) |
| `EnumConfig` | — | [EnumConfig.java](dev/ftb/mods/ftblibrary/config/EnumConfig.java) |
| `FTBLibraryClientConfig` | — | [FTBLibraryClientConfig.java](dev/ftb/mods/ftblibrary/config/FTBLibraryClientConfig.java) |
| `ResourceConfigValue` | — | [ResourceConfigValue.java](dev/ftb/mods/ftblibrary/config/ResourceConfigValue.java) |
| `EditStringConfigOverlay` | — | [EditStringConfigOverlay.java](dev/ftb/mods/ftblibrary/config/ui/EditStringConfigOverlay.java) |
| `ConfigFromString` | — | [ConfigFromString.java](dev/ftb/mods/ftblibrary/config/ConfigFromString.java) |
| `ImageResourceConfig` | — | [ImageResourceConfig.java](dev/ftb/mods/ftblibrary/config/ImageResourceConfig.java) |
| `FluidConfig` | — | [FluidConfig.java](dev/ftb/mods/ftblibrary/config/FluidConfig.java) |
| `ListConfig` | — | [ListConfig.java](dev/ftb/mods/ftblibrary/config/ListConfig.java) |
| `NumberConfig` | — | [NumberConfig.java](dev/ftb/mods/ftblibrary/config/NumberConfig.java) |
| `NBTConfig` | — | [NBTConfig.java](dev/ftb/mods/ftblibrary/config/NBTConfig.java) |
| `EditMultilineStringConfigOverlay` | — | [EditMultilineStringConfigOverlay.java](dev/ftb/mods/ftblibrary/config/ui/EditMultilineStringConfigOverlay.java) |
| `ConfigUtil` | — | [ConfigUtil.java](dev/ftb/mods/ftblibrary/snbt/config/ConfigUtil.java) |
| `ConfigCallback` | — | [ConfigCallback.java](dev/ftb/mods/ftblibrary/config/ConfigCallback.java) |
| `LongConfig` | — | [LongConfig.java](dev/ftb/mods/ftblibrary/config/LongConfig.java) |
| `StringConfig` | — | [StringConfig.java](dev/ftb/mods/ftblibrary/config/StringConfig.java) |
| `BooleanConfig` | — | [BooleanConfig.java](dev/ftb/mods/ftblibrary/config/BooleanConfig.java) |

## 按包浏览

- `architectury_inject_FTBLibrary121_common_e8b82fb0f9004e28b3411b262ca0462f_a3e23a2f926a73d7d787c024e4805d09672c0f804894df4d7750775bc31ac2e6ftblibrary2101133devjar`（1 类）
- `dev.ftb.mods.ftblibrary`（4 类）
- `dev.ftb.mods.ftblibrary.api.client`（1 类）
- `dev.ftb.mods.ftblibrary.api.color`（1 类）
- `dev.ftb.mods.ftblibrary.api.sidebar`（3 类）
- `dev.ftb.mods.ftblibrary.config`（25 类）
- `dev.ftb.mods.ftblibrary.config.manager`（2 类）
- `dev.ftb.mods.ftblibrary.config.ui`（5 类）
- `dev.ftb.mods.ftblibrary.config.ui.resource`（12 类）
- `dev.ftb.mods.ftblibrary.core`（2 类）
- `dev.ftb.mods.ftblibrary.core.mixin.common`（5 类）
- `dev.ftb.mods.ftblibrary.icon`（27 类）
- `dev.ftb.mods.ftblibrary.integration`（3 类）
- `dev.ftb.mods.ftblibrary.integration.currency`（3 类）
- `dev.ftb.mods.ftblibrary.integration.docsmod`（2 类）
- `dev.ftb.mods.ftblibrary.integration.neoforge`（2 类）
- `dev.ftb.mods.ftblibrary.integration.permissions`（3 类）
- `dev.ftb.mods.ftblibrary.integration.stages`（3 类）
- `dev.ftb.mods.ftblibrary.items`（1 类）
- `dev.ftb.mods.ftblibrary.math`（6 类）
- `dev.ftb.mods.ftblibrary.nbtedit`（2 类）
- `dev.ftb.mods.ftblibrary.neoforge`（1 类）
- `dev.ftb.mods.ftblibrary.net`（10 类）
- `dev.ftb.mods.ftblibrary.sidebar`（6 类）
- `dev.ftb.mods.ftblibrary.snbt`（10 类）
- `dev.ftb.mods.ftblibrary.snbt.config`（14 类）
- `dev.ftb.mods.ftblibrary.ui`（41 类）
- `dev.ftb.mods.ftblibrary.ui.input`（3 类）
- `dev.ftb.mods.ftblibrary.ui.input.neoforge`（1 类）
- `dev.ftb.mods.ftblibrary.ui.misc`（10 类）
- `dev.ftb.mods.ftblibrary.util`（25 类）
- `dev.ftb.mods.ftblibrary.util.client`（4 类）
- `dev.ftb.mods.ftblibrary.util.client.neoforge`（1 类）
- `dev.ftb.mods.ftblibrary.util.neoforge`（5 类）
- `dev.ftb.mods.ftblibrary.util.text`（3 类）
