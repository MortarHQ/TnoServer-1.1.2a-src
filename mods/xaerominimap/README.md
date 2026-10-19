# Xaero's Minimap（`xaerominimap`）

> jar：`[Xaero的小地图] xaerominimap.jar` ｜ 版本：`25.3.10` ｜ 作者：xaero96
> 规模：**554 个类 / 194 个包 / 52108 行** ｜ 事件处理类 4 ｜ Mixin 24 ｜ 注册/管理类 65

**依赖**：`minecraft`、`openpartiesandclaims`、`xaeroworldmap`、`xaerolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `xaero.common.gui` | 39 | `GuiAddWaypoint`、`GuiWaypoints`、`GuiInfoDisplayEdit` |
| `xaero.common.mixin` | 24 | `MixinClientPlayNetworkHandler`、`MixinClientPlayerEntity`、`MixinAbstractClientPlayerEntity` |
| `xaero.hud.category.ui.entry` | 15 | `EditorListEntry`、`EditorListEntryWidget`、`EditorListEntryCategory` |
| `xaero.hud.minimap.controls.key.function` | 15 | `WaypointMenuFunction`、`ToggleSlimeChunksFunction`、`ZoomFunction` |
| `xaero.common.minimap.waypoints` | 11 | `WaypointsManager`、`Waypoint`、`WaypointWorldConnectionManager` |
| `xaero.common` | 11 | `HudMod`、`PlatformContextNeoForge`、`PlatformContextLoaderClientOnly` |
| `xaero.hud.minimap.waypoint.render` | 10 | `WaypointMapRenderer`、`WaypointMapRenderReader`、`WaypointsGuiRenderer` |
| `xaero.common.events` | 9 | `ClientEventsNeoForge`、`ModClientEventsNeoForge`、`ClientEvents` |
| `xaero.hud.minimap.waypoint` | 9 | `WaypointSharingHandler`、`DeathpointHandler`、`DestinationHandler` |
| `xaero.hud.minimap.radar.icon.cache.id.variant` | 9 | `RadarIconVariantHandler`、`IronGolemVariant`、`SaddleVariant` |
| `xaero.hud.category.ui.node` | 8 | `EditorSettingsNode`、`EditorCategoryNode`、`EditorFilterSettingsNode` |
| `xaero.hud.minimap.radar.category` | 7 | `EntityRadarCategoryManager`、`EntityRadarBackwardsCompatibilityConfig`、`EntityRadarCategoryConfigIO` |
| `xaero.hud.minimap.player.tracker` | 7 | `PlayerTrackerMinimapElementRenderer`、`PlayerTrackerMinimapElementReader`、`PlayerTrackerIconRenderer` |
| `xaero.hud.category.rule` | 7 | `ObjectCategoryListRule`、`ObjectCategoryHardRule`、`ObjectCategoryIncludeList` |
| `xaero.common.effect` | 7 | `NoCaveMapsEffect`、`NoRadarEffect`、`Effects` |
| `xaero.common.mods` | 6 | `SupportXaeroWorldmap`、`SupportFramedBlocks`、`SupportMods` |
| `xaero.hud.minimap.element.render` | 6 | `MinimapElementRendererHandler`、`MinimapElementReader`、`MinimapElementRenderer` |
| `xaero.common.core` | 6 | `IXaeroMinimapClientPlayNetHandler`、`XaeroMinimapCore`、`IXaeroMinimapSMultiBlockChangePacket` |
| `xaero.common.misc` | 6 | `Misc`、`Internet`、`CachedFunction` |
| `xaero.hud.category.ui.node.options` | 6 | `EditorSimpleButtonNode`、`EditorOptionsNode`、`EditorOptionNode` |
| `xaero.hud.category.serialization.data` | 6 | `FilterObjectCategoryData`、`ObjectCategoryData`、`ObjectCategoryDataNbtSerializer` |
| `xaero.hud.minimap.info` | 5 | `InfoDisplayManager`、`InfoDisplay`、`BuiltInInfoDisplays` |
| `xaero.common.minimap.element.render` | 5 | `MinimapElementRendererHandler`、`MinimapElementRenderer`、`MinimapElementReader` |
| `xaero.common.graphics` | 5 | `ImprovedFramebuffer`、`PixelBuffers`、`MinimapTexture` |
| `xaero.common.minimap.highlight` | 5 | `HighlighterRegistry`、`DimensionHighlighterHandler`、`AbstractHighlighter` |
| `xaero.hud.pushbox` | 5 | `PushboxHandler`、`PushboxManager`、`PushBox` |
| `xaero.hud.module` | 5 | `ModuleManager`、`ModuleSessionHandler`、`ModuleSession` |
| `xaero.common.minimap.waypoints.render` | 5 | `WaypointsGuiRenderer`、`WaypointsIngameRenderer`、`WaypointRenderProvider` |
| `xaero.minimap` | 4 | `XaeroMinimapNeoForge`、`XaeroMinimap`、`MinimapClientOnly` |
| `xaero.hud.category.ui` | 4 | `GuiCategoryEditor`、`EditorFilterCategoryNodeConverter`、`EditorCategoryNodeConverter` |
| `xaero.common.minimap.render` | 4 | `MinimapFBORenderer`、`MinimapRenderer`、`MinimapRendererHelper` |
| `xaero.hud.minimap.radar.render.element` | 4 | `RadarRenderer`、`RadarElementReader`、`RadarRenderProvider` |
| `xaero.common.minimap.render.radar.element` | 4 | `RadarRenderer`、`RadarElementReader`、`RadarRenderProvider` |
| `xaero.common.server.radar.tracker` | 4 | `SyncedPlayerTrackerSystemManager`、`SyncedTrackedPlayer`、`SyncedPlayerTracker` |
| `xaero.hud.minimap.waypoint.render.world` | 4 | `WaypointWorldRenderer`、`WaypointWorldRenderReader`、`WaypointWorldRenderProvider` |
| `xaero.hud.minimap.radar.icon.definition` | 4 | `RadarIconDefinitionManager`、`RadarIconDefinition`、`BuiltInRadarIconDefinitions` |
| `xaero.common.server` | 4 | `MinecraftServerData`、`MineraftServerDataInitializer`、`XaeroMinimapServer` |
| `xaero.hud.minimap.radar.icon.creator.render.form.model.custom` | 4 | `LayeredIconCustomPrerenderer`、`RenderTypeIconCustomPrerenderer`、`CustomModelIconCustomPrenderer` |
| `xaero.hud.category.ui.node.options.range.setting` | 4 | `EditorExpandingSettingNode`、`EditorCompactSettingNode`、`IEditorSettingNodeBuilder` |
| `xaero.hud.minimap.info.widget` | 4 | `InfoDisplayCycleButton`、`InfoDisplayCycleWidgetFactory`、`InfoDisplayCommonWidgetFactories` |
| … | | 其余 154 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinClientPlayNetworkHandler` | class | IXaeroMinimapClientPlayNetHandler | Mixin | 13 | 137 | [MixinClientPlayNetworkHandler.java](xaero/common/mixin/MixinClientPlayNetworkHandler.java) |
| `HudModConfigClientRegistryHandler` | class | IConfigChannelClientRegistryHandler | — | 4 | 30 | [HudModConfigClientRegistryHandler.java](xaero/hud/config/channel/register/HudModConfigClientRegistryHandler.java) |
| `ClientEventsNeoForge` | class | ClientEvents | — | 14 | 126 | [ClientEventsNeoForge.java](xaero/common/events/ClientEventsNeoForge.java) |
| `EntityRadarCategoryManager` | class | — | — | 32 | 400 | [EntityRadarCategoryManager.java](xaero/hud/minimap/radar/category/EntityRadarCategoryManager.java) |
| `HudModConfigCommonRegistryHandler` | class | IConfigChannelCommonRegistryHandler | — | 4 | 29 | [HudModConfigCommonRegistryHandler.java](xaero/hud/common/config/channel/register/HudModConfigCommonRegistryHandler.java) |
| `ModClientEventsNeoForge` | class | ModClientEvents | — | 4 | 44 | [ModClientEventsNeoForge.java](xaero/common/events/ModClientEventsNeoForge.java) |
| `ClientEvents` | class | — | — | 17 | 303 | [ClientEvents.java](xaero/common/events/ClientEvents.java) |
| `CommonEventsNeoForge` | class | CommonEvents | — | 5 | 41 | [CommonEventsNeoForge.java](xaero/common/events/CommonEventsNeoForge.java) |
| `MinimapConfigOptionClientHandlers` | class | — | — | 13 | 98 | [MinimapConfigOptionClientHandlers.java](xaero/hud/minimap/config/listener/handler/MinimapConfigOptionClientHandlers.java) |
| `WaypointsManager` | class | MinimapSession | — | 62 | 363 | [WaypointsManager.java](xaero/common/minimap/waypoints/WaypointsManager.java) |
| `ModCommonEventsNeoForge` | class | ModCommonEvents | — | 1 | 28 | [ModCommonEventsNeoForge.java](xaero/common/events/ModCommonEventsNeoForge.java) |
| `GuiAddWaypoint` | class | ScreenBase | — | 37 | 1311 | [GuiAddWaypoint.java](xaero/common/gui/GuiAddWaypoint.java) |
| `MinimapWriter` | class | — | — | 44 | 2511 | [MinimapWriter.java](xaero/common/minimap/write/MinimapWriter.java) |
| `InfoDisplayManagerConfigData` | class | — | — | 8 | 63 | [InfoDisplayManagerConfigData.java](xaero/hud/minimap/common/config/info/config/InfoDisplayManagerConfigData.java) |
| `XaeroMinimapNeoForge` | class | XaeroMinimap | Mod | 2 | 27 | [XaeroMinimapNeoForge.java](xaero/minimap/XaeroMinimapNeoForge.java) |
| `MixinClientPlayerEntity` | class | — | Mixin | 1 | 18 | [MixinClientPlayerEntity.java](xaero/common/mixin/MixinClientPlayerEntity.java) |
| `MixinAbstractClientPlayerEntity` | class | — | Mixin | 0 | 9 | [MixinAbstractClientPlayerEntity.java](xaero/common/mixin/MixinAbstractClientPlayerEntity.java) |
| `ModClientEvents` | class | — | — | 3 | 61 | [ModClientEvents.java](xaero/common/events/ModClientEvents.java) |
| `ClientEventsListener` | class | — | — | 4 | 22 | [ClientEventsListener.java](xaero/common/events/ClientEventsListener.java) |
| `WaypointSharingHandler` | class | — | — | 15 | 432 | [WaypointSharingHandler.java](xaero/hud/minimap/waypoint/WaypointSharingHandler.java) |
| `MinimapConfigClientUtils` | class | — | — | 16 | 188 | [MinimapConfigClientUtils.java](xaero/hud/minimap/config/util/MinimapConfigClientUtils.java) |
| `MinimapWorldManagerIO` | class | — | — | 19 | 467 | [MinimapWorldManagerIO.java](xaero/hud/minimap/world/io/MinimapWorldManagerIO.java) |
| `MinimapElementWorldRendererHandler` | class | MinimapElementRendererHandler | — | 11 | 242 | [MinimapElementWorldRendererHandler.java](xaero/hud/minimap/element/render/world/MinimapElementWorldRendererHandler.java) |
| `HudMod` | class | IXaeroMinimap | — | 78 | 642 | [HudMod.java](xaero/common/HudMod.java) |
| `GuiWaypoints` | class | ScreenBase | — | 35 | 786 | [GuiWaypoints.java](xaero/common/gui/GuiWaypoints.java) |
| `MixinChunkDeltaUpdateS2CPacket` | class | IXaeroMinimapSMultiBlockChangePacket | Mixin | 1 | 19 | [MixinChunkDeltaUpdateS2CPacket.java](xaero/common/mixin/MixinChunkDeltaUpdateS2CPacket.java) |
| `CommonEvents` | class | — | — | 5 | 98 | [CommonEvents.java](xaero/common/events/CommonEvents.java) |
| `ModSettings` | class | — | — | 39 | 739 | [ModSettings.java](xaero/common/settings/ModSettings.java) |
| `SupportXaeroWorldmap` | class | — | — | 45 | 626 | [SupportXaeroWorldmap.java](xaero/common/mods/SupportXaeroWorldmap.java) |
| `MinimapConfigOptionServerHandlers` | class | — | — | 1 | 9 | [MinimapConfigOptionServerHandlers.java](xaero/hud/minimap/common/config/server/listener/handler/MinimapConfigOptionServerHandlers.java) |
| `MinimapWorldManager` | class | — | — | 20 | 144 | [MinimapWorldManager.java](xaero/hud/minimap/world/MinimapWorldManager.java) |
| `RootConfig` | class | — | — | 26 | 128 | [RootConfig.java](xaero/hud/minimap/world/container/config/RootConfig.java) |
| `MinimapElementRendererHandler` | class | — | — | 12 | 184 | [MinimapElementRendererHandler.java](xaero/hud/minimap/element/render/MinimapElementRendererHandler.java) |
| `GuiInfoDisplayEdit` | class | ScreenBase | — | 37 | 583 | [GuiInfoDisplayEdit.java](xaero/common/gui/GuiInfoDisplayEdit.java) |
| `MixinRabbitEntityModel` | class | — | Mixin | 1 | 20 | [MixinRabbitEntityModel.java](xaero/common/mixin/MixinRabbitEntityModel.java) |
| `MixinCompositeEntityModel` | class | — | Mixin | 1 | 20 | [MixinCompositeEntityModel.java](xaero/common/mixin/MixinCompositeEntityModel.java) |
| `MixinClientWorld` | class | IXaeroMinimapClientWorld | Mixin | 2 | 22 | [MixinClientWorld.java](xaero/common/mixin/MixinClientWorld.java) |
| `MixinDragonEntityModel` | class | — | Mixin | 1 | 20 | [MixinDragonEntityModel.java](xaero/common/mixin/MixinDragonEntityModel.java) |
| `MixinSinglePartEntityModel` | class | — | Mixin | 1 | 20 | [MixinSinglePartEntityModel.java](xaero/common/mixin/MixinSinglePartEntityModel.java) |
| `MixinMinecraftClient` | class | IXaeroMinimapMinecraftClient | Mixin | 1 | 18 | [MixinMinecraftClient.java](xaero/common/mixin/MixinMinecraftClient.java) |
| `MixinLlamaEntityModel` | class | — | Mixin | 1 | 20 | [MixinLlamaEntityModel.java](xaero/common/mixin/MixinLlamaEntityModel.java) |
| `ModCommonEvents` | class | — | — | 0 | 12 | [ModCommonEvents.java](xaero/common/events/ModCommonEvents.java) |
| `ServerEvents` | class | — | — | 1 | 15 | [ServerEvents.java](xaero/common/server/events/ServerEvents.java) |
| `ClientSyncedTrackedPlayerManager` | class | — | — | 7 | 54 | [ClientSyncedTrackedPlayerManager.java](xaero/hud/minimap/player/tracker/synced/ClientSyncedTrackedPlayerManager.java) |
| `InfoDisplayManager` | class | — | — | 17 | 167 | [InfoDisplayManager.java](xaero/hud/minimap/info/InfoDisplayManager.java) |
| `EntityRadarBackwardsCompatibilityConfig` | class | — | — | 1 | 118 | [EntityRadarBackwardsCompatibilityConfig.java](xaero/hud/minimap/radar/category/EntityRadarBackwardsCompatibilityConfig.java) |
| `MinimapProfiledConfigOptions` | class | — | — | 2 | 832 | [MinimapProfiledConfigOptions.java](xaero/hud/minimap/common/config/option/MinimapProfiledConfigOptions.java) |
| `MinimapWorldConnectionManager` | class | — | — | 16 | 157 | [MinimapWorldConnectionManager.java](xaero/hud/minimap/world/connection/MinimapWorldConnectionManager.java) |
| `MinimapElementOverMapRendererHandler` | class | MinimapElementRendererHandler | — | 9 | 174 | [MinimapElementOverMapRendererHandler.java](xaero/hud/minimap/element/render/over/MinimapElementOverMapRendererHandler.java) |
| `MinimapElementMapRendererHandler` | class | MinimapElementRendererHandler | — | 8 | 94 | [MinimapElementMapRendererHandler.java](xaero/hud/minimap/element/render/map/MinimapElementMapRendererHandler.java) |
| `GuiCategoryEditor` | class | ObjectSelectionList | — | 53 | 531 | [GuiCategoryEditor.java](xaero/hud/category/ui/GuiCategoryEditor.java) |
| `HudEventHandler` | class | — | — | 2 | 26 | [HudEventHandler.java](xaero/hud/event/HudEventHandler.java) |
| `IXaeroMinimapClientPlayNetHandler` | interface | — | — | 0 | 10 | [IXaeroMinimapClientPlayNetHandler.java](xaero/common/core/IXaeroMinimapClientPlayNetHandler.java) |
| `XaeroMinimapCore` | class | — | — | 32 | 309 | [XaeroMinimapCore.java](xaero/common/core/XaeroMinimapCore.java) |
| `MinimapProcessor` | class | — | — | 45 | 362 | [MinimapProcessor.java](xaero/common/minimap/MinimapProcessor.java) |
| `Waypoint` | class | Comparable<Waypoint> | — | 54 | 354 | [Waypoint.java](xaero/common/minimap/waypoints/Waypoint.java) |
| `ClientSyncedTrackedPlayerManager` | class | xaero.hud.minimap.player.tracker.syn | — | 4 | 34 | [ClientSyncedTrackedPlayerManager.java](xaero/common/minimap/radar/tracker/synced/ClientSyncedTrackedPlayerManager.java) |
| `MinimapElementRendererHandler` | class | xaero.hud.minimap.element.render.Min | — | 1 | 22 | [MinimapElementRendererHandler.java](xaero/common/minimap/element/render/MinimapElementRendererHandler.java) |
| `MinimapElementOverMapRendererHandler` | class | xaero.hud.minimap.element.render.ove | — | 0 | 13 | [MinimapElementOverMapRendererHandler.java](xaero/common/minimap/element/render/over/MinimapElementOverMapRendererHandler.java) |
| `MinimapElementMapRendererHandler` | class | xaero.hud.minimap.element.render.map | — | 0 | 13 | [MinimapElementMapRendererHandler.java](xaero/common/minimap/element/render/map/MinimapElementMapRendererHandler.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventsNeoForge` | 14 | `xaero.common.events` | [ClientEventsNeoForge.java](xaero/common/events/ClientEventsNeoForge.java) |
| `CommonEventsNeoForge` | 5 | `xaero.common.events` | [CommonEventsNeoForge.java](xaero/common/events/CommonEventsNeoForge.java) |
| `ModClientEventsNeoForge` | 4 | `xaero.common.events` | [ModClientEventsNeoForge.java](xaero/common/events/ModClientEventsNeoForge.java) |
| `ModCommonEventsNeoForge` | 1 | `xaero.common.events` | [ModCommonEventsNeoForge.java](xaero/common/events/ModCommonEventsNeoForge.java) |

## Mixin（24 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinClientPlayNetworkHandler` | `ClientPlayNetworkHandler` | [MixinClientPlayNetworkHandler.java](xaero/common/mixin/MixinClientPlayNetworkHandler.java) |
| `MixinClientPlayerEntity` | `ClientPlayerEntity` | [MixinClientPlayerEntity.java](xaero/common/mixin/MixinClientPlayerEntity.java) |
| `MixinAbstractClientPlayerEntity` | `AbstractClientPlayerEntity` | [MixinAbstractClientPlayerEntity.java](xaero/common/mixin/MixinAbstractClientPlayerEntity.java) |
| `MixinChunkDeltaUpdateS2CPacket` | `ChunkDeltaUpdateS2CPacket` | [MixinChunkDeltaUpdateS2CPacket.java](xaero/common/mixin/MixinChunkDeltaUpdateS2CPacket.java) |
| `MixinRabbitEntityModel` | `RabbitEntityModel` | [MixinRabbitEntityModel.java](xaero/common/mixin/MixinRabbitEntityModel.java) |
| `MixinCompositeEntityModel` | `CompositeEntityModel` | [MixinCompositeEntityModel.java](xaero/common/mixin/MixinCompositeEntityModel.java) |
| `MixinClientWorld` | `ClientWorld` | [MixinClientWorld.java](xaero/common/mixin/MixinClientWorld.java) |
| `MixinDragonEntityModel` | `DragonEntityModel` | [MixinDragonEntityModel.java](xaero/common/mixin/MixinDragonEntityModel.java) |
| `MixinSinglePartEntityModel` | `SinglePartEntityModel` | [MixinSinglePartEntityModel.java](xaero/common/mixin/MixinSinglePartEntityModel.java) |
| `MixinMinecraftClient` | `MinecraftClient` | [MixinMinecraftClient.java](xaero/common/mixin/MixinMinecraftClient.java) |
| `MixinLlamaEntityModel` | `LlamaEntityModel` | [MixinLlamaEntityModel.java](xaero/common/mixin/MixinLlamaEntityModel.java) |
| `MixinOptionalInGameHud` | `OptionalInGameHud` | [MixinOptionalInGameHud.java](xaero/common/mixin/MixinOptionalInGameHud.java) |
| `MixinLocalPlayer` | `LocalPlayer` | [MixinLocalPlayer.java](xaero/common/mixin/MixinLocalPlayer.java) |
| `MixinBatchableBufferSource` | `BatchableBufferSource` | [MixinBatchableBufferSource.java](xaero/common/mixin/MixinBatchableBufferSource.java) |
| `MixinPlayerList` | `PlayerList` | [MixinPlayerList.java](xaero/common/mixin/MixinPlayerList.java) |
| `MixinBufferSource` | `BufferSource` | [MixinBufferSource.java](xaero/common/mixin/MixinBufferSource.java) |
| `MixinAnimalModel` | `AnimalModel` | [MixinAnimalModel.java](xaero/common/mixin/MixinAnimalModel.java) |
| `MixinNeoforgeToggleKeyMapping` | `NeoforgeToggleKeyMapping` | [MixinNeoforgeToggleKeyMapping.java](xaero/common/mixin/MixinNeoforgeToggleKeyMapping.java) |
| `MixinChatListener` | `ChatListener` | [MixinChatListener.java](xaero/common/mixin/MixinChatListener.java) |
| `MixinServerPlayer` | `ServerPlayer` | [MixinServerPlayer.java](xaero/common/mixin/MixinServerPlayer.java) |
| `MixinLevelStorageAccess` | `LevelStorageAccess` | [MixinLevelStorageAccess.java](xaero/common/mixin/MixinLevelStorageAccess.java) |
| `MixinModelPart` | `ModelPart` | [MixinModelPart.java](xaero/common/mixin/MixinModelPart.java) |
| `MixinWorldChunk` | `WorldChunk` | [MixinWorldChunk.java](xaero/common/mixin/MixinWorldChunk.java) |
| `MixinMinecraftServer` | `MinecraftServer` | [MixinMinecraftServer.java](xaero/common/mixin/MixinMinecraftServer.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `HudModConfigClientRegistryHandler` | — | [HudModConfigClientRegistryHandler.java](xaero/hud/config/channel/register/HudModConfigClientRegistryHandler.java) |
| `HudModConfigCommonRegistryHandler` | — | [HudModConfigCommonRegistryHandler.java](xaero/hud/common/config/channel/register/HudModConfigCommonRegistryHandler.java) |
| `MinimapConfigOptionClientHandlers` | — | [MinimapConfigOptionClientHandlers.java](xaero/hud/minimap/config/listener/handler/MinimapConfigOptionClientHandlers.java) |
| `InfoDisplayManagerConfigData` | — | [InfoDisplayManagerConfigData.java](xaero/hud/minimap/common/config/info/config/InfoDisplayManagerConfigData.java) |
| `MinimapConfigClientUtils` | — | [MinimapConfigClientUtils.java](xaero/hud/minimap/config/util/MinimapConfigClientUtils.java) |
| `MinimapConfigOptionServerHandlers` | — | [MinimapConfigOptionServerHandlers.java](xaero/hud/minimap/common/config/server/listener/handler/MinimapConfigOptionServerHandlers.java) |
| `RootConfig` | — | [RootConfig.java](xaero/hud/minimap/world/container/config/RootConfig.java) |
| `EntityRadarBackwardsCompatibilityConfig` | — | [EntityRadarBackwardsCompatibilityConfig.java](xaero/hud/minimap/radar/category/EntityRadarBackwardsCompatibilityConfig.java) |
| `MinimapProfiledConfigOptions` | — | [MinimapProfiledConfigOptions.java](xaero/hud/minimap/common/config/option/MinimapProfiledConfigOptions.java) |
| `InfoDisplayConfigClientUtils` | — | [InfoDisplayConfigClientUtils.java](xaero/hud/minimap/info/config/InfoDisplayConfigClientUtils.java) |
| `EntityRadarCategoryConfigIO` | — | [EntityRadarCategoryConfigIO.java](xaero/hud/minimap/radar/category/EntityRadarCategoryConfigIO.java) |
| `MinimapConfigOptionClientRedirectors` | — | [MinimapConfigOptionClientRedirectors.java](xaero/hud/minimap/config/option/value/redirect/MinimapConfigOptionClientRedirectors.java) |
| `MinimapConfigUtils` | — | [MinimapConfigUtils.java](xaero/hud/minimap/common/config/util/MinimapConfigUtils.java) |
| `MinimapPrimaryClientConfigOptions` | — | [MinimapPrimaryClientConfigOptions.java](xaero/hud/minimap/config/primary/option/MinimapPrimaryClientConfigOptions.java) |
| `InfoDisplayConfigData` | — | [InfoDisplayConfigData.java](xaero/hud/minimap/common/config/info/config/InfoDisplayConfigData.java) |
| `LegacyCommonConfigIO` | — | [LegacyCommonConfigIO.java](xaero/common/config/LegacyCommonConfigIO.java) |
| `LegacyCommonConfigInit` | — | [LegacyCommonConfigInit.java](xaero/common/config/LegacyCommonConfigInit.java) |
| `RadarIconModelConfig` | — | [RadarIconModelConfig.java](xaero/hud/minimap/radar/icon/definition/form/model/config/RadarIconModelConfig.java) |
| `MinimapConfigConstants` | — | [MinimapConfigConstants.java](xaero/hud/minimap/common/config/MinimapConfigConstants.java) |
| `MinimapPrimaryCommonConfigOptions` | — | [MinimapPrimaryCommonConfigOptions.java](xaero/hud/minimap/common/config/primary/option/MinimapPrimaryCommonConfigOptions.java) |
| `MinimapConfigValueTypes` | — | [MinimapConfigValueTypes.java](xaero/hud/minimap/common/config/option/value/type/MinimapConfigValueTypes.java) |
| `MinimapConfigOptionServerRedirectors` | — | [MinimapConfigOptionServerRedirectors.java](xaero/hud/minimap/common/config/option/value/redirect/MinimapConfigOptionServerRedirectors.java) |
| `MinimapConfigOptionUIRegister` | — | [MinimapConfigOptionUIRegister.java](xaero/hud/minimap/config/option/ui/MinimapConfigOptionUIRegister.java) |
| `MinimapConfigOptionUITypes` | — | [MinimapConfigOptionUITypes.java](xaero/hud/minimap/config/option/ui/type/MinimapConfigOptionUITypes.java) |
| `RootConfigIO` | — | [RootConfigIO.java](xaero/hud/minimap/world/container/config/io/RootConfigIO.java) |

## 按包浏览

- `xaero.common`（11 类）
- `xaero.common.anim`（1 类）
- `xaero.common.cache`（1 类）
- `xaero.common.cache.placeholder`（1 类）
- `xaero.common.config`（2 类）
- `xaero.common.controls`（1 类）
- `xaero.common.core`（6 类）
- `xaero.common.effect`（7 类）
- `xaero.common.events`（9 类）
- `xaero.common.exception`（2 类）
- `xaero.common.file`（1 类）
- `xaero.common.graphics`（5 类）
- `xaero.common.graphics.renderer.multitexture`（2 类）
- `xaero.common.gui`（39 类）
- `xaero.common.gui.component`（1 类）
- `xaero.common.icon`（3 类）
- `xaero.common.interfaces`（1 类）
- `xaero.common.interfaces.render`（1 类）
- `xaero.common.minimap`（2 类）
- `xaero.common.minimap.element.render`（5 类）
- `xaero.common.minimap.element.render.map`（1 类）
- `xaero.common.minimap.element.render.over`（1 类）
- `xaero.common.minimap.highlight`（5 类）
- `xaero.common.minimap.mcworld`（3 类）
- `xaero.common.minimap.radar`（3 类）
- `xaero.common.minimap.radar.tracker.synced`（1 类）
- `xaero.common.minimap.radar.tracker.system`（3 类）
- `xaero.common.minimap.radar.tracker.system.compat`（2 类）
- `xaero.common.minimap.region`（2 类）
- `xaero.common.minimap.render`（4 类）
- `xaero.common.minimap.render.radar`（1 类）
- `xaero.common.minimap.render.radar.element`（4 类）
- `xaero.common.minimap.waypoints`（11 类）
- `xaero.common.minimap.waypoints.render`（5 类）
- `xaero.common.minimap.write`（3 类）
- `xaero.common.minimap.write.biome`（1 类）
- `xaero.common.misc`（6 类）
- `xaero.common.mixin`（24 类）
- `xaero.common.mixin.plugin`（1 类）
- `xaero.common.mods`（6 类）
- `xaero.common.mods.pac`（2 类）
- `xaero.common.mods.pac.highlight`（1 类）
- `xaero.common.mods.pac.party`（2 类）
- `xaero.common.platform`（1 类）
- `xaero.common.platform.services`（2 类）
- `xaero.common.pool`（2 类）
- `xaero.common.server`（4 类）
- `xaero.common.server.core`（1 类）
- `xaero.common.server.events`（1 类）
- `xaero.common.server.level`（2 类）
- `xaero.common.server.mods`（2 类）
- `xaero.common.server.mods.argonauts`（2 类）
- `xaero.common.server.mods.ftbteams`（2 类）
- `xaero.common.server.mods.opac`（2 类）
- `xaero.common.server.player`（3 类）
- `xaero.common.server.radar.tracker`（4 类）
- `xaero.common.settings`（1 类）
- `xaero.common.validator`（3 类）
- `xaero.hud`（2 类）
- `xaero.hud.category`（2 类）
- `xaero.hud.category.rule`（7 类）
- `xaero.hud.category.rule.resolver`（1 类）
- `xaero.hud.category.serialization`（2 类）
- `xaero.hud.category.serialization.data`（6 类）
- `xaero.hud.category.setting`（2 类）
- `xaero.hud.category.ui`（4 类）
- `xaero.hud.category.ui.entry`（15 类）
- `xaero.hud.category.ui.entry.widget`（4 类）
- `xaero.hud.category.ui.node`（8 类）
- `xaero.hud.category.ui.node.options`（6 类）
- `xaero.hud.category.ui.node.options.list`（1 类）
- `xaero.hud.category.ui.node.options.range`（2 类）
- `xaero.hud.category.ui.node.options.range.setting`（4 类）
- `xaero.hud.category.ui.node.options.text`（2 类）
- `xaero.hud.category.ui.node.rule`（3 类）
- `xaero.hud.category.ui.node.tooltip`（1 类）
- `xaero.hud.category.ui.setting`（3 类）
- `xaero.hud.category.util`（1 类）
- `xaero.hud.common.config.channel.register`（1 类）
- `xaero.hud.compat`（1 类）
- `xaero.hud.compat.mods`（1 类）
- `xaero.hud.config.channel.register`（1 类）
- `xaero.hud.controls`（1 类）
- `xaero.hud.controls.key`（3 类）
- `xaero.hud.controls.key.function`（1 类）
- `xaero.hud.entity`（1 类）
- `xaero.hud.event`（1 类）
- `xaero.hud.gui.util`（1 类）
- `xaero.hud.gui.widget`（1 类）
- `xaero.hud.io`（1 类）
- `xaero.hud.minimap`（3 类）
- `xaero.hud.minimap.common.config`（1 类）
- `xaero.hud.minimap.common.config.info.config`（2 类）
- `xaero.hud.minimap.common.config.option`（1 类）
- `xaero.hud.minimap.common.config.option.value.redirect`（1 类）
- `xaero.hud.minimap.common.config.option.value.type`（1 类）
- `xaero.hud.minimap.common.config.option.value.type.io.serialization`（1 类）
- `xaero.hud.minimap.common.config.option.value.type.sync.serialization`（1 类）
- `xaero.hud.minimap.common.config.primary.option`（1 类）
- `xaero.hud.minimap.common.config.server.listener.handler`（1 类）
- `xaero.hud.minimap.common.config.util`（1 类）
- `xaero.hud.minimap.common.radar.category`（1 类）
- `xaero.hud.minimap.compass.render`（2 类）
- `xaero.hud.minimap.config.listener.handler`（1 类）
- `xaero.hud.minimap.config.option.ui`（1 类）
- `xaero.hud.minimap.config.option.ui.type`（1 类）
- `xaero.hud.minimap.config.option.value.redirect`（1 类）
- `xaero.hud.minimap.config.primary.option`（1 类）
- `xaero.hud.minimap.config.util`（1 类）
- `xaero.hud.minimap.controls.key`（1 类）
- `xaero.hud.minimap.controls.key.function`（15 类）
- `xaero.hud.minimap.element.render`（6 类）
- `xaero.hud.minimap.element.render.map`（1 类）
- `xaero.hud.minimap.element.render.over`（1 类）
- `xaero.hud.minimap.element.render.world`（1 类）
- `xaero.hud.minimap.info`（5 类）
- `xaero.hud.minimap.info.codec`（2 类）
- `xaero.hud.minimap.info.config`（1 类）
- `xaero.hud.minimap.info.render`（1 类）
- `xaero.hud.minimap.info.render.compile`（2 类）
- `xaero.hud.minimap.info.widget`（4 类）
- `xaero.hud.minimap.module`（2 类）
- `xaero.hud.minimap.player.tracker`（7 类）
- `xaero.hud.minimap.player.tracker.synced`（3 类）
- `xaero.hud.minimap.player.tracker.system`（3 类）
- `xaero.hud.minimap.radar`（1 类）
- `xaero.hud.minimap.radar.category`（7 类）
- `xaero.hud.minimap.radar.category.rule`（2 类）
- `xaero.hud.minimap.radar.category.serialization`（1 类）
- `xaero.hud.minimap.radar.category.serialization.data`（1 类）
- `xaero.hud.minimap.radar.category.setting`（1 类）
- `xaero.hud.minimap.radar.category.ui`（2 类）
- `xaero.hud.minimap.radar.category.ui.node`（2 类）
- `xaero.hud.minimap.radar.color`（2 类）
- `xaero.hud.minimap.radar.icon`（1 类）
- `xaero.hud.minimap.radar.icon.cache`（2 类）
- `xaero.hud.minimap.radar.icon.cache.id`（1 类）
- `xaero.hud.minimap.radar.icon.cache.id.armor`（2 类）
- `xaero.hud.minimap.radar.icon.cache.id.variant`（9 类）
- `xaero.hud.minimap.radar.icon.creator`（1 类）
- `xaero.hud.minimap.radar.icon.creator.entity`（1 类）
- `xaero.hud.minimap.radar.icon.creator.render.form`（1 类）
- `xaero.hud.minimap.radar.icon.creator.render.form.item`（1 类）
- `xaero.hud.minimap.radar.icon.creator.render.form.model`（2 类）
- `xaero.hud.minimap.radar.icon.creator.render.form.model.custom`（4 类）
- `xaero.hud.minimap.radar.icon.creator.render.form.model.part`（3 类）
- `xaero.hud.minimap.radar.icon.creator.render.form.model.resolver`（2 类）
- `xaero.hud.minimap.radar.icon.creator.render.form.sprite`（1 类）
- `xaero.hud.minimap.radar.icon.creator.render.trace`（3 类）
- `xaero.hud.minimap.radar.icon.definition`（4 类）
- `xaero.hud.minimap.radar.icon.definition.form`（2 类）
- `xaero.hud.minimap.radar.icon.definition.form.item`（1 类）
- `xaero.hud.minimap.radar.icon.definition.form.model`（1 类）
- `xaero.hud.minimap.radar.icon.definition.form.model.config`（1 类）
- `xaero.hud.minimap.radar.icon.definition.form.sprite`（1 类）
- `xaero.hud.minimap.radar.icon.definition.form.type`（3 类）
- `xaero.hud.minimap.radar.render.element`（4 类）
- `xaero.hud.minimap.radar.state`（3 类）
- `xaero.hud.minimap.radar.util`（1 类）
- `xaero.hud.minimap.waypoint`（9 类）
- `xaero.hud.minimap.waypoint.io`（2 类）
- `xaero.hud.minimap.waypoint.render`（10 类）
- `xaero.hud.minimap.waypoint.render.world`（4 类）
- `xaero.hud.minimap.waypoint.server`（1 类）
- `xaero.hud.minimap.waypoint.set`（1 类）
- `xaero.hud.minimap.world`（3 类）
- `xaero.hud.minimap.world.connection`（1 类）
- `xaero.hud.minimap.world.container`（3 类）
- `xaero.hud.minimap.world.container.config`（1 类）
- `xaero.hud.minimap.world.container.config.io`（1 类）
- `xaero.hud.minimap.world.io`（1 类）
- `xaero.hud.minimap.world.state`（2 类）
- `xaero.hud.module`（5 类）
- `xaero.hud.packet`（2 类）
- `xaero.hud.packet.basic`（2 类）
- `xaero.hud.packet.tracker`（2 类）
- `xaero.hud.path`（2 类）
- `xaero.hud.preset`（3 类）
- `xaero.hud.preset.action`（2 类）
- `xaero.hud.pushbox`（5 类）
- `xaero.hud.pushbox.boss`（3 类）
- `xaero.hud.pushbox.effect`（3 类）
- `xaero.hud.render`（2 类）
- `xaero.hud.render.module`（2 类）
- `xaero.hud.render.util`（1 类）
- `xaero.hud.util`（1 类）
- `xaero.hud.xminimap.controls`（1 类）
- `xaero.hud.xminimap.controls.key`（1 类）
- `xaero.hud.xminimap.controls.key.function`（2 类）
- `xaero.hud.xminimap.preset`（1 类）
- `xaero.minimap`（4 类）
- `xaero.minimap.controls`（2 类）
- `xaero.minimap.gui`（1 类）
- `xaero.minimap.mods`（1 类）
