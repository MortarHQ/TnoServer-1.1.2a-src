# Iceberg（`iceberg`）

> jar：`[冰山]Iceberg.jar` ｜ 版本：`1.3.2` ｜ 作者：Grend
> 规模：**78 个类 / 24 个包 / 6103 行** ｜ 事件处理类 5 ｜ Mixin 23 ｜ 注册/管理类 5

**官方描述**：A library containing events, helpers, and utilities to make modding easier.

**依赖**：`neoforge`、`minecraft`、`advancementplaques`、`legendarytooltips`、`equipmentcompare`、`merchantmarkers`、`itemborders`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.anthonyhilyard.iceberg.mixin` | 13 | `ClientPacketListenerMixin`、`ClientTextTooltipMixin`、`LivingEntityMixin` |
| `com.anthonyhilyard.iceberg.util` | 11 | `EntityCollector`、`Tooltips`、`ItemUtil` |
| `com.anthonyhilyard.iceberg.services` | 7 | `IIcebergConfigSpecBuilder`、`Services`、`IPlatformHelper` |
| `com.anthonyhilyard.iceberg.neoforge.services` | 6 | `NeoForgeKeyMappingRegistrar`、`NeoForgeReloadListenerRegistrar`、`NeoForgeBufferSourceFactory` |
| `com.anthonyhilyard.iceberg.events.client` | 5 | `RenderTooltipEvents`、`RenderTickEvents`、`ItemTooltipEvent` |
| `com.anthonyhilyard.iceberg.mixin.azurelib` | 4 | `GeoArmorRendererMixin`、`ItemArmorGeoLayerMixin`、`HumanoidArmorLayerMixin` |
| `com.anthonyhilyard.iceberg.mixin.azurelibarmor` | 4 | `GeoArmorRendererMixin`、`ItemArmorGeoLayerMixin`、`HumanoidArmorLayerMixin` |
| `com.anthonyhilyard.iceberg.events` | 4 | `ToggleableEvent`、`EventFactory`、`Event` |
| `com.anthonyhilyard.iceberg.events.common` | 3 | `ConfigEvents`、`LevelEvents`、`CriterionEvent` |
| `com.anthonyhilyard.iceberg.renderer` | 3 | `CustomItemRenderer`、`CheckedBufferSource`、`VertexCollector` |
| `com.anthonyhilyard.iceberg.neoforge.config` | 2 | `NeoForgeIcebergConfigSpec`、`ConfigValueWrapper` |
| `com.anthonyhilyard.iceberg.neoforge.mixin` | 2 | `ClientHooksMixin`、`TooltipRenderUtilMixin` |
| `com.anthonyhilyard.iceberg.config` | 2 | `IcebergConfig`、`IIcebergConfigSpec` |
| `com.anthonyhilyard.iceberg.component` | 2 | `IExtendedText`、`TitleBreakComponent` |
| `com.anthonyhilyard.iceberg.neoforge` | 1 | `IcebergNeoForge` |
| `com.anthonyhilyard.iceberg.mixin.geckolib` | 1 | `GeoArmorRendererMixin` |
| `com.anthonyhilyard.iceberg.neoforge.client` | 1 | `IcebergNeoForgeClient` |
| `com.anthonyhilyard.iceberg.compat` | 1 | `EMIHandler` |
| `com.anthonyhilyard.iceberg.neoforge.server` | 1 | `IcebergNeoForgeServer` |
| `com.anthonyhilyard.iceberg.neoforge.common` | 1 | `IcebergNeoForgeCommon` |
| `com.anthonyhilyard.iceberg.events.server` | 1 | `PlayerLoginEvent` |
| `com.anthonyhilyard.iceberg.client` | 1 | `IcebergClient` |
| `architectury_inject_Iceberg_common_2684b2a2577b40d29157a0302d02334e_dc66f61b145a2ccb9871c0644dd2fbc0cbbf2fba1692f3b7b20b45f1c66269d1Iceberg1211common132devjar` | 1 | `PlatformMethods` |
| `com.anthonyhilyard.iceberg` | 1 | `Iceberg` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeIcebergConfigSpec` | class | net.neoforged.neoforge.common.ModCon | — | 56 | 612 | [NeoForgeIcebergConfigSpec.java](com/anthonyhilyard/iceberg/neoforge/config/NeoForgeIcebergConfigSpec.java) |
| `ConfigEvents` | class | — | — | 0 | 43 | [ConfigEvents.java](com/anthonyhilyard/iceberg/events/common/ConfigEvents.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 32 | [ClientPacketListenerMixin.java](com/anthonyhilyard/iceberg/mixin/ClientPacketListenerMixin.java) |
| `IcebergNeoForge` | class | — | Mod | 1 | 49 | [IcebergNeoForge.java](com/anthonyhilyard/iceberg/neoforge/IcebergNeoForge.java) |
| `ClientTextTooltipMixin` | class | IExtendedText | Mixin | 12 | 118 | [ClientTextTooltipMixin.java](com/anthonyhilyard/iceberg/mixin/ClientTextTooltipMixin.java) |
| `CustomItemRenderer` | class | ItemRenderer | — | 19 | 654 | [CustomItemRenderer.java](com/anthonyhilyard/iceberg/renderer/CustomItemRenderer.java) |
| `EntityCollector` | class | Level | — | 53 | 367 | [EntityCollector.java](com/anthonyhilyard/iceberg/util/EntityCollector.java) |
| `ClientHooksMixin` | class | — | Mixin | 1 | 39 | [ClientHooksMixin.java](com/anthonyhilyard/iceberg/neoforge/mixin/ClientHooksMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 22 | [LivingEntityMixin.java](com/anthonyhilyard/iceberg/mixin/LivingEntityMixin.java) |
| `TooltipRenderUtilMixin` | class | — | Mixin | 6 | 112 | [TooltipRenderUtilMixin.java](com/anthonyhilyard/iceberg/mixin/TooltipRenderUtilMixin.java) |
| `ItemMixin` | class | — | Mixin | 1 | 31 | [ItemMixin.java](com/anthonyhilyard/iceberg/mixin/ItemMixin.java) |
| `ScreenMixin` | class | — | Mixin | 1 | 35 | [ScreenMixin.java](com/anthonyhilyard/iceberg/mixin/ScreenMixin.java) |
| `GeoArmorRendererMixin` | class | — | Mixin | 2 | 58 | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/GeoArmorRendererMixin.java) |
| `ItemArmorGeoLayerMixin` | class | — | Mixin | 1 | 51 | [ItemArmorGeoLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/ItemArmorGeoLayerMixin.java) |
| `GeoArmorRendererMixin` | class | — | Mixin | 2 | 56 | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/geckolib/GeoArmorRendererMixin.java) |
| `GeoArmorRendererMixin` | class | — | Mixin | 2 | 57 | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/GeoArmorRendererMixin.java) |
| `ItemArmorGeoLayerMixin` | class | — | Mixin | 1 | 51 | [ItemArmorGeoLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/ItemArmorGeoLayerMixin.java) |
| `LevelEvents` | class | — | — | 0 | 32 | [LevelEvents.java](com/anthonyhilyard/iceberg/events/common/LevelEvents.java) |
| `RenderTooltipEvents` | class | — | — | 3 | 126 | [RenderTooltipEvents.java](com/anthonyhilyard/iceberg/events/client/RenderTooltipEvents.java) |
| `RenderTickEvents` | class | — | — | 0 | 19 | [RenderTickEvents.java](com/anthonyhilyard/iceberg/events/client/RenderTickEvents.java) |
| `Tooltips` | class | ClientTooltipPositioner | — | 32 | 503 | [Tooltips.java](com/anthonyhilyard/iceberg/util/Tooltips.java) |
| `TooltipRenderUtilMixin` | class | — | Mixin | 3 | 98 | [TooltipRenderUtilMixin.java](com/anthonyhilyard/iceberg/neoforge/mixin/TooltipRenderUtilMixin.java) |
| `IcebergNeoForgeClient` | class | — | — | 8 | 159 | [IcebergNeoForgeClient.java](com/anthonyhilyard/iceberg/neoforge/client/IcebergNeoForgeClient.java) |
| `GuiGraphicsMixin` | class | ITooltipAccess | Mixin | 8 | 197 | [GuiGraphicsMixin.java](com/anthonyhilyard/iceberg/mixin/GuiGraphicsMixin.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 21 | [MinecraftMixin.java](com/anthonyhilyard/iceberg/mixin/MinecraftMixin.java) |
| `GuiMixin` | class | — | Mixin | 1 | 19 | [GuiMixin.java](com/anthonyhilyard/iceberg/mixin/GuiMixin.java) |
| `GuiGraphicsInvoker` | interface | — | Mixin | 0 | 16 | [GuiGraphicsInvoker.java](com/anthonyhilyard/iceberg/mixin/GuiGraphicsInvoker.java) |
| `PlayerAdvancementsMixin` | class | — | Mixin | 1 | 26 | [PlayerAdvancementsMixin.java](com/anthonyhilyard/iceberg/mixin/PlayerAdvancementsMixin.java) |
| `TextColorMixin` | class | — | Mixin | 3 | 48 | [TextColorMixin.java](com/anthonyhilyard/iceberg/mixin/TextColorMixin.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 55 | [HumanoidArmorLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/HumanoidArmorLayerMixin.java) |
| `AzArmorModelMixin` | class | — | Mixin | 2 | 66 | [AzArmorModelMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/AzArmorModelMixin.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 55 | [HumanoidArmorLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/HumanoidArmorLayerMixin.java) |
| `AzArmorModelMixin` | class | — | Mixin | 2 | 65 | [AzArmorModelMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/AzArmorModelMixin.java) |
| `NeoForgeKeyMappingRegistrar` | class | IKeyMappingRegistrar | — | 5 | 53 | [NeoForgeKeyMappingRegistrar.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeKeyMappingRegistrar.java) |
| `MixinConfig` | class | IMixinConfigPlugin | — | 7 | 44 | [MixinConfig.java](com/anthonyhilyard/iceberg/mixin/MixinConfig.java) |
| `ItemTooltipEvent` | interface | — | — | 0 | 20 | [ItemTooltipEvent.java](com/anthonyhilyard/iceberg/events/client/ItemTooltipEvent.java) |
| `NewItemPickupEvent` | interface | — | — | 0 | 17 | [NewItemPickupEvent.java](com/anthonyhilyard/iceberg/events/client/NewItemPickupEvent.java) |
| `IcebergConfig` | class | — | — | 6 | 114 | [IcebergConfig.java](com/anthonyhilyard/iceberg/config/IcebergConfig.java) |
| `EMIHandler` | class | — | — | 1 | 36 | [EMIHandler.java](com/anthonyhilyard/iceberg/compat/EMIHandler.java) |
| `IcebergNeoForgeServer` | class | — | — | 1 | 16 | [IcebergNeoForgeServer.java](com/anthonyhilyard/iceberg/neoforge/server/IcebergNeoForgeServer.java) |
| `IcebergNeoForgeCommon` | class | — | — | 2 | 20 | [IcebergNeoForgeCommon.java](com/anthonyhilyard/iceberg/neoforge/common/IcebergNeoForgeCommon.java) |
| `ConfigValueWrapper` | class | Supplier<T> | — | 3 | 102 | [ConfigValueWrapper.java](com/anthonyhilyard/iceberg/neoforge/config/ConfigValueWrapper.java) |
| `NeoForgeReloadListenerRegistrar` | class | IReloadListenerRegistrar | — | 3 | 37 | [NeoForgeReloadListenerRegistrar.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeReloadListenerRegistrar.java) |
| `ToggleableEvent` | class | — | — | 5 | 46 | [ToggleableEvent.java](com/anthonyhilyard/iceberg/events/ToggleableEvent.java) |
| `IIcebergConfigSpec` | interface | — | — | 0 | 6 | [IIcebergConfigSpec.java](com/anthonyhilyard/iceberg/config/IIcebergConfigSpec.java) |
| `IIcebergConfigSpecBuilder` | interface | — | — | 0 | 55 | [IIcebergConfigSpecBuilder.java](com/anthonyhilyard/iceberg/services/IIcebergConfigSpecBuilder.java) |
| `NeoForgeBufferSourceFactory` | class | IBufferSourceFactory | — | 18 | 110 | [NeoForgeBufferSourceFactory.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeBufferSourceFactory.java) |
| `EventFactory` | class | — | — | 3 | 30 | [EventFactory.java](com/anthonyhilyard/iceberg/events/EventFactory.java) |
| `Event` | class | — | — | 4 | 46 | [Event.java](com/anthonyhilyard/iceberg/events/Event.java) |
| `TypeTrackedEvent` | class | Event | — | 3 | 28 | [TypeTrackedEvent.java](com/anthonyhilyard/iceberg/events/TypeTrackedEvent.java) |
| `PlayerLoginEvent` | interface | — | — | 0 | 16 | [PlayerLoginEvent.java](com/anthonyhilyard/iceberg/events/server/PlayerLoginEvent.java) |
| `CriterionEvent` | interface | — | — | 0 | 16 | [CriterionEvent.java](com/anthonyhilyard/iceberg/events/common/CriterionEvent.java) |
| `RegisterTooltipComponentFactoryEvent` | interface | — | — | 0 | 24 | [RegisterTooltipComponentFactoryEvent.java](com/anthonyhilyard/iceberg/events/client/RegisterTooltipComponentFactoryEvent.java) |
| `ItemUtil` | class | — | — | 2 | 19 | [ItemUtil.java](com/anthonyhilyard/iceberg/util/ItemUtil.java) |
| `DynamicResourcePack` | class | PackResources | — | 15 | 130 | [DynamicResourcePack.java](com/anthonyhilyard/iceberg/util/DynamicResourcePack.java) |
| `CheckedBufferSource` | class | MultiBufferSource | — | 10 | 80 | [CheckedBufferSource.java](com/anthonyhilyard/iceberg/renderer/CheckedBufferSource.java) |
| `GuiHelper` | class | — | — | 6 | 92 | [GuiHelper.java](com/anthonyhilyard/iceberg/util/GuiHelper.java) |
| `UnsafeUtil` | class | — | — | 8 | 55 | [UnsafeUtil.java](com/anthonyhilyard/iceberg/util/UnsafeUtil.java) |
| `Selectors` | class | — | — | 5 | 302 | [Selectors.java](com/anthonyhilyard/iceberg/util/Selectors.java) |
| `ItemColor` | class | FormattedCharSink | — | 4 | 100 | [ItemColor.java](com/anthonyhilyard/iceberg/util/ItemColor.java) |

## 事件处理（`@SubscribeEvent`，5 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `IcebergNeoForgeClient` | 7 | `com.anthonyhilyard.iceberg.neoforge.client` | [IcebergNeoForgeClient.java](com/anthonyhilyard/iceberg/neoforge/client/IcebergNeoForgeClient.java) |
| `IcebergNeoForgeCommon` | 2 | `com.anthonyhilyard.iceberg.neoforge.common` | [IcebergNeoForgeCommon.java](com/anthonyhilyard/iceberg/neoforge/common/IcebergNeoForgeCommon.java) |
| `NeoForgeKeyMappingRegistrar` | 1 | `com.anthonyhilyard.iceberg.neoforge.services` | [NeoForgeKeyMappingRegistrar.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeKeyMappingRegistrar.java) |
| `IcebergNeoForgeServer` | 1 | `com.anthonyhilyard.iceberg.neoforge.server` | [IcebergNeoForgeServer.java](com/anthonyhilyard/iceberg/neoforge/server/IcebergNeoForgeServer.java) |
| `NeoForgeReloadListenerRegistrar` | 1 | `com.anthonyhilyard.iceberg.neoforge.services` | [NeoForgeReloadListenerRegistrar.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeReloadListenerRegistrar.java) |

## Mixin（23 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](com/anthonyhilyard/iceberg/mixin/ClientPacketListenerMixin.java) |
| `ClientTextTooltipMixin` | `ClientTextTooltip` | [ClientTextTooltipMixin.java](com/anthonyhilyard/iceberg/mixin/ClientTextTooltipMixin.java) |
| `ClientHooksMixin` | `ClientHooks` | [ClientHooksMixin.java](com/anthonyhilyard/iceberg/neoforge/mixin/ClientHooksMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/anthonyhilyard/iceberg/mixin/LivingEntityMixin.java) |
| `TooltipRenderUtilMixin` | `TooltipRenderUtil` | [TooltipRenderUtilMixin.java](com/anthonyhilyard/iceberg/mixin/TooltipRenderUtilMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](com/anthonyhilyard/iceberg/mixin/ItemMixin.java) |
| `ScreenMixin` | `Screen` | [ScreenMixin.java](com/anthonyhilyard/iceberg/mixin/ScreenMixin.java) |
| `GeoArmorRendererMixin` | `GeoArmorRenderer` | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/GeoArmorRendererMixin.java) |
| `ItemArmorGeoLayerMixin` | `ItemArmorGeoLayer` | [ItemArmorGeoLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/ItemArmorGeoLayerMixin.java) |
| `GeoArmorRendererMixin` | `GeoArmorRenderer` | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/geckolib/GeoArmorRendererMixin.java) |
| `GeoArmorRendererMixin` | `GeoArmorRenderer` | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/GeoArmorRendererMixin.java) |
| `ItemArmorGeoLayerMixin` | `ItemArmorGeoLayer` | [ItemArmorGeoLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/ItemArmorGeoLayerMixin.java) |
| `TooltipRenderUtilMixin` | `TooltipRenderUtil` | [TooltipRenderUtilMixin.java](com/anthonyhilyard/iceberg/neoforge/mixin/TooltipRenderUtilMixin.java) |
| `GuiGraphicsMixin` | `GuiGraphics` | [GuiGraphicsMixin.java](com/anthonyhilyard/iceberg/mixin/GuiGraphicsMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/anthonyhilyard/iceberg/mixin/MinecraftMixin.java) |
| `GuiMixin` | `Gui` | [GuiMixin.java](com/anthonyhilyard/iceberg/mixin/GuiMixin.java) |
| `GuiGraphicsInvoker` | `GuiGraphicsInvoker` | [GuiGraphicsInvoker.java](com/anthonyhilyard/iceberg/mixin/GuiGraphicsInvoker.java) |
| `PlayerAdvancementsMixin` | `PlayerAdvancements` | [PlayerAdvancementsMixin.java](com/anthonyhilyard/iceberg/mixin/PlayerAdvancementsMixin.java) |
| `TextColorMixin` | `TextColor` | [TextColorMixin.java](com/anthonyhilyard/iceberg/mixin/TextColorMixin.java) |
| `HumanoidArmorLayerMixin` | `HumanoidArmorLayer` | [HumanoidArmorLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/HumanoidArmorLayerMixin.java) |
| `AzArmorModelMixin` | `AzArmorModel` | [AzArmorModelMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/AzArmorModelMixin.java) |
| `HumanoidArmorLayerMixin` | `HumanoidArmorLayer` | [HumanoidArmorLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/HumanoidArmorLayerMixin.java) |
| `AzArmorModelMixin` | `AzArmorModel` | [AzArmorModelMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/AzArmorModelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `NeoForgeIcebergConfigSpec` | — | [NeoForgeIcebergConfigSpec.java](com/anthonyhilyard/iceberg/neoforge/config/NeoForgeIcebergConfigSpec.java) |
| `ConfigEvents` | — | [ConfigEvents.java](com/anthonyhilyard/iceberg/events/common/ConfigEvents.java) |
| `MixinConfig` | — | [MixinConfig.java](com/anthonyhilyard/iceberg/mixin/MixinConfig.java) |
| `IcebergConfig` | — | [IcebergConfig.java](com/anthonyhilyard/iceberg/config/IcebergConfig.java) |
| `ConfigValueWrapper` | — | [ConfigValueWrapper.java](com/anthonyhilyard/iceberg/neoforge/config/ConfigValueWrapper.java) |
| `IIcebergConfigSpec` | — | [IIcebergConfigSpec.java](com/anthonyhilyard/iceberg/config/IIcebergConfigSpec.java) |
| `IIcebergConfigSpecBuilder` | — | [IIcebergConfigSpecBuilder.java](com/anthonyhilyard/iceberg/services/IIcebergConfigSpecBuilder.java) |

## 全部类（78）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeIcebergConfigSpec` | class | net.neoforged.neoforge.common.ModCon | — | 56 | 612 | [NeoForgeIcebergConfigSpec.java](com/anthonyhilyard/iceberg/neoforge/config/NeoForgeIcebergConfigSpec.java) |
| `ConfigEvents` | class | — | — | 0 | 43 | [ConfigEvents.java](com/anthonyhilyard/iceberg/events/common/ConfigEvents.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 32 | [ClientPacketListenerMixin.java](com/anthonyhilyard/iceberg/mixin/ClientPacketListenerMixin.java) |
| `IcebergNeoForge` | class | — | Mod | 1 | 49 | [IcebergNeoForge.java](com/anthonyhilyard/iceberg/neoforge/IcebergNeoForge.java) |
| `ClientTextTooltipMixin` | class | IExtendedText | Mixin | 12 | 118 | [ClientTextTooltipMixin.java](com/anthonyhilyard/iceberg/mixin/ClientTextTooltipMixin.java) |
| `CustomItemRenderer` | class | ItemRenderer | — | 19 | 654 | [CustomItemRenderer.java](com/anthonyhilyard/iceberg/renderer/CustomItemRenderer.java) |
| `EntityCollector` | class | Level | — | 53 | 367 | [EntityCollector.java](com/anthonyhilyard/iceberg/util/EntityCollector.java) |
| `ClientHooksMixin` | class | — | Mixin | 1 | 39 | [ClientHooksMixin.java](com/anthonyhilyard/iceberg/neoforge/mixin/ClientHooksMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 22 | [LivingEntityMixin.java](com/anthonyhilyard/iceberg/mixin/LivingEntityMixin.java) |
| `TooltipRenderUtilMixin` | class | — | Mixin | 6 | 112 | [TooltipRenderUtilMixin.java](com/anthonyhilyard/iceberg/mixin/TooltipRenderUtilMixin.java) |
| `ItemMixin` | class | — | Mixin | 1 | 31 | [ItemMixin.java](com/anthonyhilyard/iceberg/mixin/ItemMixin.java) |
| `ScreenMixin` | class | — | Mixin | 1 | 35 | [ScreenMixin.java](com/anthonyhilyard/iceberg/mixin/ScreenMixin.java) |
| `GeoArmorRendererMixin` | class | — | Mixin | 2 | 58 | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/GeoArmorRendererMixin.java) |
| `ItemArmorGeoLayerMixin` | class | — | Mixin | 1 | 51 | [ItemArmorGeoLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/ItemArmorGeoLayerMixin.java) |
| `GeoArmorRendererMixin` | class | — | Mixin | 2 | 56 | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/geckolib/GeoArmorRendererMixin.java) |
| `GeoArmorRendererMixin` | class | — | Mixin | 2 | 57 | [GeoArmorRendererMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/GeoArmorRendererMixin.java) |
| `ItemArmorGeoLayerMixin` | class | — | Mixin | 1 | 51 | [ItemArmorGeoLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/ItemArmorGeoLayerMixin.java) |
| `LevelEvents` | class | — | — | 0 | 32 | [LevelEvents.java](com/anthonyhilyard/iceberg/events/common/LevelEvents.java) |
| `RenderTooltipEvents` | class | — | — | 3 | 126 | [RenderTooltipEvents.java](com/anthonyhilyard/iceberg/events/client/RenderTooltipEvents.java) |
| `RenderTickEvents` | class | — | — | 0 | 19 | [RenderTickEvents.java](com/anthonyhilyard/iceberg/events/client/RenderTickEvents.java) |
| `Tooltips` | class | ClientTooltipPositioner | — | 32 | 503 | [Tooltips.java](com/anthonyhilyard/iceberg/util/Tooltips.java) |
| `TooltipRenderUtilMixin` | class | — | Mixin | 3 | 98 | [TooltipRenderUtilMixin.java](com/anthonyhilyard/iceberg/neoforge/mixin/TooltipRenderUtilMixin.java) |
| `IcebergNeoForgeClient` | class | — | — | 8 | 159 | [IcebergNeoForgeClient.java](com/anthonyhilyard/iceberg/neoforge/client/IcebergNeoForgeClient.java) |
| `GuiGraphicsMixin` | class | ITooltipAccess | Mixin | 8 | 197 | [GuiGraphicsMixin.java](com/anthonyhilyard/iceberg/mixin/GuiGraphicsMixin.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 21 | [MinecraftMixin.java](com/anthonyhilyard/iceberg/mixin/MinecraftMixin.java) |
| `GuiMixin` | class | — | Mixin | 1 | 19 | [GuiMixin.java](com/anthonyhilyard/iceberg/mixin/GuiMixin.java) |
| `GuiGraphicsInvoker` | interface | — | Mixin | 0 | 16 | [GuiGraphicsInvoker.java](com/anthonyhilyard/iceberg/mixin/GuiGraphicsInvoker.java) |
| `PlayerAdvancementsMixin` | class | — | Mixin | 1 | 26 | [PlayerAdvancementsMixin.java](com/anthonyhilyard/iceberg/mixin/PlayerAdvancementsMixin.java) |
| `TextColorMixin` | class | — | Mixin | 3 | 48 | [TextColorMixin.java](com/anthonyhilyard/iceberg/mixin/TextColorMixin.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 55 | [HumanoidArmorLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/HumanoidArmorLayerMixin.java) |
| `AzArmorModelMixin` | class | — | Mixin | 2 | 66 | [AzArmorModelMixin.java](com/anthonyhilyard/iceberg/mixin/azurelib/AzArmorModelMixin.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 55 | [HumanoidArmorLayerMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/HumanoidArmorLayerMixin.java) |
| `AzArmorModelMixin` | class | — | Mixin | 2 | 65 | [AzArmorModelMixin.java](com/anthonyhilyard/iceberg/mixin/azurelibarmor/AzArmorModelMixin.java) |
| `NeoForgeKeyMappingRegistrar` | class | IKeyMappingRegistrar | — | 5 | 53 | [NeoForgeKeyMappingRegistrar.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeKeyMappingRegistrar.java) |
| `MixinConfig` | class | IMixinConfigPlugin | — | 7 | 44 | [MixinConfig.java](com/anthonyhilyard/iceberg/mixin/MixinConfig.java) |
| `ItemTooltipEvent` | interface | — | — | 0 | 20 | [ItemTooltipEvent.java](com/anthonyhilyard/iceberg/events/client/ItemTooltipEvent.java) |
| `NewItemPickupEvent` | interface | — | — | 0 | 17 | [NewItemPickupEvent.java](com/anthonyhilyard/iceberg/events/client/NewItemPickupEvent.java) |
| `IcebergConfig` | class | — | — | 6 | 114 | [IcebergConfig.java](com/anthonyhilyard/iceberg/config/IcebergConfig.java) |
| `EMIHandler` | class | — | — | 1 | 36 | [EMIHandler.java](com/anthonyhilyard/iceberg/compat/EMIHandler.java) |
| `IcebergNeoForgeServer` | class | — | — | 1 | 16 | [IcebergNeoForgeServer.java](com/anthonyhilyard/iceberg/neoforge/server/IcebergNeoForgeServer.java) |
| `IcebergNeoForgeCommon` | class | — | — | 2 | 20 | [IcebergNeoForgeCommon.java](com/anthonyhilyard/iceberg/neoforge/common/IcebergNeoForgeCommon.java) |
| `ConfigValueWrapper` | class | Supplier<T> | — | 3 | 102 | [ConfigValueWrapper.java](com/anthonyhilyard/iceberg/neoforge/config/ConfigValueWrapper.java) |
| `NeoForgeReloadListenerRegistrar` | class | IReloadListenerRegistrar | — | 3 | 37 | [NeoForgeReloadListenerRegistrar.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeReloadListenerRegistrar.java) |
| `ToggleableEvent` | class | — | — | 5 | 46 | [ToggleableEvent.java](com/anthonyhilyard/iceberg/events/ToggleableEvent.java) |
| `IIcebergConfigSpec` | interface | — | — | 0 | 6 | [IIcebergConfigSpec.java](com/anthonyhilyard/iceberg/config/IIcebergConfigSpec.java) |
| `IIcebergConfigSpecBuilder` | interface | — | — | 0 | 55 | [IIcebergConfigSpecBuilder.java](com/anthonyhilyard/iceberg/services/IIcebergConfigSpecBuilder.java) |
| `NeoForgeBufferSourceFactory` | class | IBufferSourceFactory | — | 18 | 110 | [NeoForgeBufferSourceFactory.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeBufferSourceFactory.java) |
| `EventFactory` | class | — | — | 3 | 30 | [EventFactory.java](com/anthonyhilyard/iceberg/events/EventFactory.java) |
| `Event` | class | — | — | 4 | 46 | [Event.java](com/anthonyhilyard/iceberg/events/Event.java) |
| `TypeTrackedEvent` | class | Event | — | 3 | 28 | [TypeTrackedEvent.java](com/anthonyhilyard/iceberg/events/TypeTrackedEvent.java) |
| `PlayerLoginEvent` | interface | — | — | 0 | 16 | [PlayerLoginEvent.java](com/anthonyhilyard/iceberg/events/server/PlayerLoginEvent.java) |
| `CriterionEvent` | interface | — | — | 0 | 16 | [CriterionEvent.java](com/anthonyhilyard/iceberg/events/common/CriterionEvent.java) |
| `RegisterTooltipComponentFactoryEvent` | interface | — | — | 0 | 24 | [RegisterTooltipComponentFactoryEvent.java](com/anthonyhilyard/iceberg/events/client/RegisterTooltipComponentFactoryEvent.java) |
| `ItemUtil` | class | — | — | 2 | 19 | [ItemUtil.java](com/anthonyhilyard/iceberg/util/ItemUtil.java) |
| `DynamicResourcePack` | class | PackResources | — | 15 | 130 | [DynamicResourcePack.java](com/anthonyhilyard/iceberg/util/DynamicResourcePack.java) |
| `CheckedBufferSource` | class | MultiBufferSource | — | 10 | 80 | [CheckedBufferSource.java](com/anthonyhilyard/iceberg/renderer/CheckedBufferSource.java) |
| `GuiHelper` | class | — | — | 6 | 92 | [GuiHelper.java](com/anthonyhilyard/iceberg/util/GuiHelper.java) |
| `UnsafeUtil` | class | — | — | 8 | 55 | [UnsafeUtil.java](com/anthonyhilyard/iceberg/util/UnsafeUtil.java) |
| `Selectors` | class | — | — | 5 | 302 | [Selectors.java](com/anthonyhilyard/iceberg/util/Selectors.java) |
| `ItemColor` | class | FormattedCharSink | — | 4 | 100 | [ItemColor.java](com/anthonyhilyard/iceberg/util/ItemColor.java) |
| `IcebergClient` | class | — | — | 2 | 42 | [IcebergClient.java](com/anthonyhilyard/iceberg/client/IcebergClient.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 4 | 62 | [NeoForgePlatformHelper.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgePlatformHelper.java) |
| `IExtendedText` | interface | — | — | 5 | 63 | [IExtendedText.java](com/anthonyhilyard/iceberg/component/IExtendedText.java) |
| `VertexCollector` | class | MultiBufferSource | — | 9 | 83 | [VertexCollector.java](com/anthonyhilyard/iceberg/renderer/VertexCollector.java) |
| `Easing` | class | — | — | 8 | 101 | [Easing.java](com/anthonyhilyard/iceberg/util/Easing.java) |
| `Services` | class | — | — | 9 | 60 | [Services.java](com/anthonyhilyard/iceberg/services/Services.java) |
| `IPlatformHelper` | interface | — | — | 0 | 14 | [IPlatformHelper.java](com/anthonyhilyard/iceberg/services/IPlatformHelper.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_Iceberg_common_2684b2a2577b40d29157a0302d02334e_dc66f61b145a2ccb9871c0644dd2fbc0cbbf2fba1692f3b7b20b45f1c66269d1Iceberg1211common132devjar/PlatformMethods.java) |
| `Iceberg` | class | — | — | 0 | 10 | [Iceberg.java](com/anthonyhilyard/iceberg/Iceberg.java) |
| `VertexConsumerSodium` | interface | — | — | 0 | 8 | [VertexConsumerSodium.java](com/anthonyhilyard/iceberg/neoforge/services/VertexConsumerSodium.java) |
| `NeoForgeFontLookup` | class | IFontLookup | — | 1 | 16 | [NeoForgeFontLookup.java](com/anthonyhilyard/iceberg/neoforge/services/NeoForgeFontLookup.java) |
| `TitleBreakComponent` | class | TooltipComponent | — | 3 | 22 | [TitleBreakComponent.java](com/anthonyhilyard/iceberg/component/TitleBreakComponent.java) |
| `ITooltipAccess` | interface | — | — | 0 | 10 | [ITooltipAccess.java](com/anthonyhilyard/iceberg/util/ITooltipAccess.java) |
| `StringRecomposer` | class | FormattedCharSink | — | 3 | 48 | [StringRecomposer.java](com/anthonyhilyard/iceberg/util/StringRecomposer.java) |
| `IFontLookup` | interface | — | — | 0 | 10 | [IFontLookup.java](com/anthonyhilyard/iceberg/services/IFontLookup.java) |
| `IKeyMappingRegistrar` | interface | — | — | 0 | 17 | [IKeyMappingRegistrar.java](com/anthonyhilyard/iceberg/services/IKeyMappingRegistrar.java) |
| `IReloadListenerRegistrar` | interface | — | — | 0 | 12 | [IReloadListenerRegistrar.java](com/anthonyhilyard/iceberg/services/IReloadListenerRegistrar.java) |
| `IBufferSourceFactory` | interface | — | — | 0 | 11 | [IBufferSourceFactory.java](com/anthonyhilyard/iceberg/services/IBufferSourceFactory.java) |
