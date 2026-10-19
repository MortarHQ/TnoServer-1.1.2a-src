# Xaero's World Map（`xaeroworldmap`）

> jar：`[Xaero的世界地图] xaeroworldmap.jar` ｜ 版本：`1.40.11` ｜ 作者：xaero96
> 规模：**298 个类 / 88 个包 / 38276 行** ｜ 事件处理类 4 ｜ Mixin 14 ｜ 注册/管理类 33

**依赖**：`minecraft`、`openpartiesandclaims`、`xaerominimap`、`xaerobetterpvp`、`xaerolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `xaero.map.gui` | 21 | `GuiMap`、`GuiMapSwitching`、`EditDefaultTpCommandScreen` |
| `xaero.map.region` | 16 | `MapRegion`、`LeveledRegion`、`LayeredRegionManager` |
| `xaero.map.mixin` | 14 | `MixinClientPlayNetworkHandler`、`MixinForgeClientPacketListener`、`MixinChunkDeltaUpdateS2CPacketAccessor` |
| `xaero.map.element` | 13 | `MapElementMenuRenderer`、`MapElementRenderHandler`、`MapElementRenderer` |
| `xaero.map` | 12 | `WorldMapNeoForge`、`MapProcessor`、`MapWriter` |
| `xaero.map.radar.tracker` | 12 | `PlayerTrackerMenuRenderer`、`PlayerTrackerMapElementRenderer`、`PlayerTrackerMapElementReader` |
| `xaero.map.mods.gui` | 9 | `WaypointMenuRenderer`、`WaypointReader`、`WaypointRenderer` |
| `xaero.map.graphics` | 9 | `ImprovedFramebuffer`、`MapRenderHelper`、`TextureUpload` |
| `xaero.map.events` | 8 | `ClientEventsNeoForge`、`ModClientEventsNeoForge`、`ClientEvents` |
| `xaero.map.highlight` | 7 | `DimensionHighlighterHandler`、`HighlighterRegistry`、`AbstractHighlighter` |
| `xaero.map.misc` | 7 | `Misc`、`ConsistentBitArray`、`Area` |
| `xaero.map.region.texture` | 6 | `RegionTexture`、`LeafRegionTexture`、`BranchRegionTexture` |
| `xaero.map.file` | 5 | `MapSaveLoad`、`OldFormatSupport`、`RegionDetection` |
| `xaero.map.mods` | 5 | `SupportXaeroMinimap`、`SupportFramedBlocks`、`SupportModsNeoForge` |
| `xaero.map.core` | 5 | `IWorldMapClientPlayNetHandler`、`IWorldMapSMultiBlockChangePacket`、`XaeroWorldMapCore` |
| `xaero.map.element.render` | 5 | `ElementReader`、`ElementRenderer`、`ElementRenderProvider` |
| `xaero.map.server` | 5 | `MinecraftServerData`、`MineraftServerDataInitializer`、`WorldMapServerNeoForge` |
| `xaero.map.effects` | 5 | `NoCaveMapsEffect`、`WorldMapStatusEffect`、`NoWorldMapEffect` |
| `xaero.map.world` | 4 | `MapDimension`、`MapWorld`、`MapConnectionManager` |
| `xaero.map.mods.minimap.element` | 4 | `MinimapElementRendererWrapper`、`MinimapElementReaderWrapper`、`RadarRendererWrapperHelper` |
| `xaero.map.palette` | 4 | `FastIntPalette`、`Paletted2DFastBitArrayIntStorage`、`FastPalette` |
| `xaero.map.server.radar.tracker` | 4 | `SyncedPlayerTrackerSystemManager`、`SyncedTrackedPlayer`、`SyncedPlayerTracker` |
| `xaero.map.mods.pac.gui.claim.element` | 4 | `ClaimResultElementRenderer`、`ClaimResultElementRenderReader`、`ClaimResultElementRenderProvider` |
| `xaero.map.biome` | 4 | `BlockTintProvider`、`BiomeBlendCalculator`、`BiomeGetter` |
| `xaero.map.pool` | 4 | `TextureUploadPool`、`MapPool`、`MapTilePool` |
| `xaero.map.file.worldsave` | 3 | `WorldDataReader`、`WorldDataHandler`、`WorldDataChunkTileEntityLookup` |
| `xaero.map.file.worldsave.biome` | 3 | `WorldDataBiomeManager`、`WorldDataReaderSectionBiomeData`、`WorldDataReaderChunkBiomeData` |
| `xaero.map.controls` | 3 | `ControlsHandler`、`KeyEvent`、`ControlsRegister` |
| `xaero.map.icon` | 3 | `XaeroIconAtlasManager`、`XaeroIconAtlas`、`XaeroIcon` |
| `xaero.map.radar.tracker.system` | 3 | `PlayerTrackerSystemManager`、`ITrackedPlayerReader`、`IPlayerTrackerSystem` |
| `xaero.map.server.player` | 3 | `ServerPlayerTickHandler`、`ServerPlayerData`、`IServerPlayer` |
| `xaero.map.common.config` | 3 | `LegacyCommonConfigIO`、`LegacyCommonConfigInit`、`WorldMapConfigConstants` |
| `xaero.map.mcworld` | 3 | `WorldMapClientWorldDataHelper`、`WorldMapClientWorldData`、`IWorldMapClientWorld` |
| `xaero.map.cache` | 3 | `BrokenBlockTintCache`、`UnknownBlockStateCache`、`BlockStateShortShapeCache` |
| `xaero.map.file.export` | 3 | `PNGExporter`、`PNGExportResult`、`PNGExportResultType` |
| `xaero.map.animation` | 3 | `Animation`、`SlowingAnimation`、`SinAnimation` |
| `xaero.map.mods.pac.gui.claim` | 2 | `ClaimResultElementManager`、`ClaimResultElement` |
| `xaero.map.message.basic` | 2 | `ClientboundRulesPacket`、`HandshakePacket` |
| `xaero.map.message.tracker` | 2 | `ClientboundTrackedPlayerPacket`、`ClientboundPlayerTrackerResetPacket` |
| `xaero.map.mods.pac` | 2 | `SupportOpenPartiesAndClaims`、`ClientClaimChangeListener` |
| … | | 其余 48 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinClientPlayNetworkHandler` | class | IWorldMapClientPlayNetHandler | Mixin | 10 | 109 | [MixinClientPlayNetworkHandler.java](xaero/map/mixin/MixinClientPlayNetworkHandler.java) |
| `ClientEventsNeoForge` | class | ClientEvents | — | 6 | 61 | [ClientEventsNeoForge.java](xaero/map/events/ClientEventsNeoForge.java) |
| `ModClientEventsNeoForge` | class | ModClientEvents | — | 4 | 40 | [ModClientEventsNeoForge.java](xaero/map/events/ModClientEventsNeoForge.java) |
| `WorldMapConfigOptionClientHandlers` | class | — | — | 16 | 105 | [WorldMapConfigOptionClientHandlers.java](xaero/map/config/listener/handler/WorldMapConfigOptionClientHandlers.java) |
| `ClientEvents` | class | — | — | 13 | 243 | [ClientEvents.java](xaero/map/events/ClientEvents.java) |
| `CommonEventsNeoForge` | class | CommonEvents | — | 5 | 36 | [CommonEventsNeoForge.java](xaero/map/events/CommonEventsNeoForge.java) |
| `WorldMapChannelClientRegistryHandler` | class | IConfigChannelClientRegistryHandler | — | 4 | 30 | [WorldMapChannelClientRegistryHandler.java](xaero/map/config/channel/register/handler/WorldMapChannelClientRegistryHandler.java) |
| `WorldMapNeoForge` | class | WorldMap | Mod | 12 | 108 | [WorldMapNeoForge.java](xaero/map/WorldMapNeoForge.java) |
| `ModCommonEventsNeoForge` | class | ModCommonEvents | — | 1 | 30 | [ModCommonEventsNeoForge.java](xaero/map/events/ModCommonEventsNeoForge.java) |
| `MapProcessor` | class | — | — | 134 | 2118 | [MapProcessor.java](xaero/map/MapProcessor.java) |
| `GuiMap` | class | ScreenBase | — | 84 | 2715 | [GuiMap.java](xaero/map/gui/GuiMap.java) |
| `MixinForgeClientPacketListener` | class | — | Mixin | 1 | 26 | [MixinForgeClientPacketListener.java](xaero/map/mixin/MixinForgeClientPacketListener.java) |
| `WorldMapChannelCommonRegistryHandler` | class | IConfigChannelCommonRegistryHandler | — | 4 | 29 | [WorldMapChannelCommonRegistryHandler.java](xaero/map/common/config/channel/register/handler/WorldMapChannelCommonRegistryHandler.java) |
| `MapSaveLoad` | class | — | — | 53 | 1895 | [MapSaveLoad.java](xaero/map/file/MapSaveLoad.java) |
| `MapWriter` | class | — | — | 25 | 1428 | [MapWriter.java](xaero/map/MapWriter.java) |
| `MapRegion` | class | LeveledRegion | — | 104 | 816 | [MapRegion.java](xaero/map/region/MapRegion.java) |
| `LeveledRegion` | class | Comparable<LeveledRegion<T>> | — | 80 | 842 | [LeveledRegion.java](xaero/map/region/LeveledRegion.java) |
| `RegionTexture` | class | — | — | 69 | 890 | [RegionTexture.java](xaero/map/region/texture/RegionTexture.java) |
| `ModClientEvents` | class | — | — | 1 | 37 | [ModClientEvents.java](xaero/map/events/ModClientEvents.java) |
| `MapElementMenuRenderer` | class | — | — | 29 | 388 | [MapElementMenuRenderer.java](xaero/map/element/MapElementMenuRenderer.java) |
| `LayeredRegionManager` | class | — | — | 21 | 149 | [LayeredRegionManager.java](xaero/map/region/LayeredRegionManager.java) |
| `DimensionHighlighterHandler` | class | — | — | 15 | 259 | [DimensionHighlighterHandler.java](xaero/map/highlight/DimensionHighlighterHandler.java) |
| `MixinChunkDeltaUpdateS2CPacketAccessor` | class | IWorldMapSMultiBlockChangePacket | Mixin | 1 | 19 | [MixinChunkDeltaUpdateS2CPacketAccessor.java](xaero/map/mixin/MixinChunkDeltaUpdateS2CPacketAccessor.java) |
| `CommonEvents` | class | — | — | 6 | 82 | [CommonEvents.java](xaero/map/events/CommonEvents.java) |
| `WorldMapConfigOptionServerHandlers` | class | — | — | 1 | 9 | [WorldMapConfigOptionServerHandlers.java](xaero/map/common/config/listener/handler/WorldMapConfigOptionServerHandlers.java) |
| `MapDimension` | class | — | — | 65 | 709 | [MapDimension.java](xaero/map/world/MapDimension.java) |
| `WorldDataReader` | class | — | — | 16 | 1010 | [WorldDataReader.java](xaero/map/file/worldsave/WorldDataReader.java) |
| `SupportXaeroMinimap` | class | — | — | 61 | 730 | [SupportXaeroMinimap.java](xaero/map/mods/SupportXaeroMinimap.java) |
| `MapBlock` | class | MapPixel | — | 26 | 357 | [MapBlock.java](xaero/map/region/MapBlock.java) |
| `BranchLeveledRegion` | class | LeveledRegion | — | 34 | 550 | [BranchLeveledRegion.java](xaero/map/region/BranchLeveledRegion.java) |
| `MixinForgeMinecraftClient` | class | — | Mixin | 2 | 27 | [MixinForgeMinecraftClient.java](xaero/map/mixin/MixinForgeMinecraftClient.java) |
| `MixinClientWorld` | class | IWorldMapClientWorld | Mixin | 2 | 22 | [MixinClientWorld.java](xaero/map/mixin/MixinClientWorld.java) |
| `MixinMinecraftClient` | class | IWorldMapMinecraftClient | Mixin | 1 | 18 | [MixinMinecraftClient.java](xaero/map/mixin/MixinMinecraftClient.java) |
| `ModCommonEvents` | class | — | — | 0 | 5 | [ModCommonEvents.java](xaero/map/events/ModCommonEvents.java) |
| `ServerEvents` | class | — | — | 0 | 12 | [ServerEvents.java](xaero/map/server/events/ServerEvents.java) |
| `WorldMapClientConfigUtils` | class | — | — | 7 | 102 | [WorldMapClientConfigUtils.java](xaero/map/config/util/WorldMapClientConfigUtils.java) |
| `MapWorld` | class | — | — | 57 | 514 | [MapWorld.java](xaero/map/world/MapWorld.java) |
| `MapElementRenderHandler` | class | — | — | 10 | 351 | [MapElementRenderHandler.java](xaero/map/element/MapElementRenderHandler.java) |
| `WaypointMenuRenderer` | class | MapElementMenuRenderer | — | 15 | 339 | [WaypointMenuRenderer.java](xaero/map/mods/gui/WaypointMenuRenderer.java) |
| `IWorldMapClientPlayNetHandler` | interface | — | — | 0 | 10 | [IWorldMapClientPlayNetHandler.java](xaero/map/core/IWorldMapClientPlayNetHandler.java) |
| `MapTileChunk` | class | — | — | 33 | 395 | [MapTileChunk.java](xaero/map/region/MapTileChunk.java) |
| `PlayerTrackerMenuRenderer` | class | MapElementMenuRenderer | — | 15 | 166 | [PlayerTrackerMenuRenderer.java](xaero/map/radar/tracker/PlayerTrackerMenuRenderer.java) |
| `ClientSyncedTrackedPlayerManager` | class | — | — | 4 | 34 | [ClientSyncedTrackedPlayerManager.java](xaero/map/radar/tracker/synced/ClientSyncedTrackedPlayerManager.java) |
| `WorldDataHandler` | class | — | — | 7 | 125 | [WorldDataHandler.java](xaero/map/file/worldsave/WorldDataHandler.java) |
| `WorldDataBiomeManager` | class | NoiseBiomeSource | — | 6 | 80 | [WorldDataBiomeManager.java](xaero/map/file/worldsave/biome/WorldDataBiomeManager.java) |
| `WaypointReader` | class | MapElementReader | — | 34 | 204 | [WaypointReader.java](xaero/map/mods/gui/WaypointReader.java) |
| `WaypointRenderer` | class | MapElementRenderer | — | 17 | 427 | [WaypointRenderer.java](xaero/map/mods/gui/WaypointRenderer.java) |
| `MinimapElementRendererWrapper` | class | MapElementRenderer | — | 17 | 405 | [MinimapElementRendererWrapper.java](xaero/map/mods/minimap/element/MinimapElementRendererWrapper.java) |
| `ClaimResultElementManager` | class | — | — | 11 | 76 | [ClaimResultElementManager.java](xaero/map/mods/pac/gui/claim/ClaimResultElementManager.java) |
| `ImprovedFramebuffer` | class | TextureTarget | — | 28 | 299 | [ImprovedFramebuffer.java](xaero/map/graphics/ImprovedFramebuffer.java) |
| `ControlsHandler` | class | — | — | 7 | 143 | [ControlsHandler.java](xaero/map/controls/ControlsHandler.java) |
| `HighlighterRegistry` | class | — | — | 3 | 22 | [HighlighterRegistry.java](xaero/map/highlight/HighlighterRegistry.java) |
| `PlayerTrackerMapElementRenderer` | class | MapElementRenderer | — | 16 | 308 | [PlayerTrackerMapElementRenderer.java](xaero/map/radar/tracker/PlayerTrackerMapElementRenderer.java) |
| `MixinServerWorld` | class | IWorldMapServerLevel | Mixin | 2 | 22 | [MixinServerWorld.java](xaero/map/mixin/MixinServerWorld.java) |
| `MixinPlayerList` | class | — | Mixin | 1 | 19 | [MixinPlayerList.java](xaero/map/mixin/MixinPlayerList.java) |
| `MixinOptionalGui` | class | — | Mixin | 1 | 21 | [MixinOptionalGui.java](xaero/map/mixin/MixinOptionalGui.java) |
| `MixinChatListener` | class | — | Mixin | 2 | 32 | [MixinChatListener.java](xaero/map/mixin/MixinChatListener.java) |
| `MixinServerPlayer` | class | IServerPlayer | Mixin | 2 | 22 | [MixinServerPlayer.java](xaero/map/mixin/MixinServerPlayer.java) |
| `MixinLevelStorageAccess` | class | — | Mixin | 1 | 17 | [MixinLevelStorageAccess.java](xaero/map/mixin/MixinLevelStorageAccess.java) |
| `MixinWorldChunk` | class | — | Mixin | 0 | 10 | [MixinWorldChunk.java](xaero/map/mixin/MixinWorldChunk.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventsNeoForge` | 6 | `xaero.map.events` | [ClientEventsNeoForge.java](xaero/map/events/ClientEventsNeoForge.java) |
| `CommonEventsNeoForge` | 5 | `xaero.map.events` | [CommonEventsNeoForge.java](xaero/map/events/CommonEventsNeoForge.java) |
| `ModClientEventsNeoForge` | 4 | `xaero.map.events` | [ModClientEventsNeoForge.java](xaero/map/events/ModClientEventsNeoForge.java) |
| `ModCommonEventsNeoForge` | 1 | `xaero.map.events` | [ModCommonEventsNeoForge.java](xaero/map/events/ModCommonEventsNeoForge.java) |

## Mixin（14 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinClientPlayNetworkHandler` | `ClientPlayNetworkHandler` | [MixinClientPlayNetworkHandler.java](xaero/map/mixin/MixinClientPlayNetworkHandler.java) |
| `MixinForgeClientPacketListener` | `ForgeClientPacketListener` | [MixinForgeClientPacketListener.java](xaero/map/mixin/MixinForgeClientPacketListener.java) |
| `MixinChunkDeltaUpdateS2CPacketAccessor` | `ChunkDeltaUpdateS2CPacketAccessor` | [MixinChunkDeltaUpdateS2CPacketAccessor.java](xaero/map/mixin/MixinChunkDeltaUpdateS2CPacketAccessor.java) |
| `MixinForgeMinecraftClient` | `ForgeMinecraftClient` | [MixinForgeMinecraftClient.java](xaero/map/mixin/MixinForgeMinecraftClient.java) |
| `MixinClientWorld` | `ClientWorld` | [MixinClientWorld.java](xaero/map/mixin/MixinClientWorld.java) |
| `MixinMinecraftClient` | `MinecraftClient` | [MixinMinecraftClient.java](xaero/map/mixin/MixinMinecraftClient.java) |
| `MixinServerWorld` | `ServerWorld` | [MixinServerWorld.java](xaero/map/mixin/MixinServerWorld.java) |
| `MixinPlayerList` | `PlayerList` | [MixinPlayerList.java](xaero/map/mixin/MixinPlayerList.java) |
| `MixinOptionalGui` | `OptionalGui` | [MixinOptionalGui.java](xaero/map/mixin/MixinOptionalGui.java) |
| `MixinChatListener` | `ChatListener` | [MixinChatListener.java](xaero/map/mixin/MixinChatListener.java) |
| `MixinServerPlayer` | `ServerPlayer` | [MixinServerPlayer.java](xaero/map/mixin/MixinServerPlayer.java) |
| `MixinLevelStorageAccess` | `LevelStorageAccess` | [MixinLevelStorageAccess.java](xaero/map/mixin/MixinLevelStorageAccess.java) |
| `MixinWorldChunk` | `WorldChunk` | [MixinWorldChunk.java](xaero/map/mixin/MixinWorldChunk.java) |
| `MixinMinecraftServer` | `MinecraftServer` | [MixinMinecraftServer.java](xaero/map/mixin/MixinMinecraftServer.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `WorldMapConfigOptionClientHandlers` | — | [WorldMapConfigOptionClientHandlers.java](xaero/map/config/listener/handler/WorldMapConfigOptionClientHandlers.java) |
| `WorldMapConfigOptionServerHandlers` | — | [WorldMapConfigOptionServerHandlers.java](xaero/map/common/config/listener/handler/WorldMapConfigOptionServerHandlers.java) |
| `WorldMapClientConfigUtils` | — | [WorldMapClientConfigUtils.java](xaero/map/config/util/WorldMapClientConfigUtils.java) |
| `WorldMapPrimaryClientConfigOptions` | — | [WorldMapPrimaryClientConfigOptions.java](xaero/map/config/primary/option/WorldMapPrimaryClientConfigOptions.java) |
| `WorldMapConfigOptionClientRedirectors` | — | [WorldMapConfigOptionClientRedirectors.java](xaero/map/config/option/value/redirect/WorldMapConfigOptionClientRedirectors.java) |
| `WorldMapProfiledConfigOptions` | — | [WorldMapProfiledConfigOptions.java](xaero/map/common/config/option/WorldMapProfiledConfigOptions.java) |
| `WorldMapConfigUtils` | — | [WorldMapConfigUtils.java](xaero/map/common/config/util/WorldMapConfigUtils.java) |
| `MapUpdateFastConfig` | — | [MapUpdateFastConfig.java](xaero/map/region/MapUpdateFastConfig.java) |
| `LegacyCommonConfigIO` | — | [LegacyCommonConfigIO.java](xaero/map/common/config/LegacyCommonConfigIO.java) |
| `LegacyCommonConfigInit` | — | [LegacyCommonConfigInit.java](xaero/map/common/config/LegacyCommonConfigInit.java) |
| `WorldMapConfigConstants` | — | [WorldMapConfigConstants.java](xaero/map/common/config/WorldMapConfigConstants.java) |
| `WorldMapPrimaryCommonConfigOptions` | — | [WorldMapPrimaryCommonConfigOptions.java](xaero/map/common/config/primary/option/WorldMapPrimaryCommonConfigOptions.java) |
| `WorldMapConfigOptionServerRedirectors` | — | [WorldMapConfigOptionServerRedirectors.java](xaero/map/common/config/option/value/redirect/WorldMapConfigOptionServerRedirectors.java) |
| `WorldMapConfigOptionUIRegister` | — | [WorldMapConfigOptionUIRegister.java](xaero/map/config/option/ui/WorldMapConfigOptionUIRegister.java) |
| `WorldMapConfigOptionUITypes` | — | [WorldMapConfigOptionUITypes.java](xaero/map/config/option/ui/type/WorldMapConfigOptionUITypes.java) |

## 按包浏览

- `xaero.map`（12 类）
- `xaero.map.animation`（3 类）
- `xaero.map.biome`（4 类）
- `xaero.map.cache`（3 类）
- `xaero.map.cache.placeholder`（1 类）
- `xaero.map.capabilities`（2 类）
- `xaero.map.common.config`（3 类）
- `xaero.map.common.config.channel.register.handler`（1 类）
- `xaero.map.common.config.listener.handler`（1 类）
- `xaero.map.common.config.option`（1 类）
- `xaero.map.common.config.option.value.redirect`（1 类）
- `xaero.map.common.config.primary.option`（1 类）
- `xaero.map.common.config.util`（1 类）
- `xaero.map.config.channel.register.handler`（1 类）
- `xaero.map.config.listener.handler`（1 类）
- `xaero.map.config.option.ui`（1 类）
- `xaero.map.config.option.ui.type`（1 类）
- `xaero.map.config.option.value.redirect`（1 类）
- `xaero.map.config.primary.option`（1 类）
- `xaero.map.config.util`（1 类）
- `xaero.map.controls`（3 类）
- `xaero.map.core`（5 类）
- `xaero.map.deallocator`（1 类）
- `xaero.map.effects`（5 类）
- `xaero.map.element`（13 类）
- `xaero.map.element.render`（5 类）
- `xaero.map.entity.util`（1 类）
- `xaero.map.events`（8 类）
- `xaero.map.exception`（2 类）
- `xaero.map.executor`（1 类）
- `xaero.map.file`（5 类）
- `xaero.map.file.export`（3 类）
- `xaero.map.file.worldsave`（3 类）
- `xaero.map.file.worldsave.biome`（3 类）
- `xaero.map.graphics`（9 类）
- `xaero.map.graphics.renderer.multitexture`（2 类）
- `xaero.map.gui`（21 类）
- `xaero.map.gui.dropdown.rightclick`（2 类）
- `xaero.map.gui.message`（2 类）
- `xaero.map.gui.message.render`（1 类）
- `xaero.map.highlight`（7 类）
- `xaero.map.icon`（3 类）
- `xaero.map.mcworld`（3 类）
- `xaero.map.message`（2 类）
- `xaero.map.message.basic`（2 类）
- `xaero.map.message.tracker`（2 类）
- `xaero.map.minimap`（1 类）
- `xaero.map.misc`（7 类）
- `xaero.map.mixin`（14 类）
- `xaero.map.mixin.plugin`（1 类）
- `xaero.map.mods`（5 类）
- `xaero.map.mods.gui`（9 类）
- `xaero.map.mods.minimap.element`（4 类）
- `xaero.map.mods.minimap.tracker.system`（2 类）
- `xaero.map.mods.pac`（2 类）
- `xaero.map.mods.pac.gui.claim`（2 类）
- `xaero.map.mods.pac.gui.claim.element`（4 类）
- `xaero.map.mods.pac.gui.claim.result`（1 类）
- `xaero.map.mods.pac.highlight`（1 类）
- `xaero.map.mods.pac.party`（2 类）
- `xaero.map.palette`（4 类）
- `xaero.map.platform`（1 类）
- `xaero.map.platform.services`（2 类）
- `xaero.map.pool`（4 类）
- `xaero.map.pool.buffer`（2 类）
- `xaero.map.profile`（1 类）
- `xaero.map.radar.tracker`（12 类）
- `xaero.map.radar.tracker.synced`（1 类）
- `xaero.map.radar.tracker.system`（3 类）
- `xaero.map.radar.tracker.system.impl`（2 类）
- `xaero.map.region`（16 类）
- `xaero.map.region.state`（1 类）
- `xaero.map.region.texture`（6 类）
- `xaero.map.server`（5 类）
- `xaero.map.server.core`（1 类）
- `xaero.map.server.events`（1 类）
- `xaero.map.server.level`（2 类）
- `xaero.map.server.mods`（2 类）
- `xaero.map.server.mods.argonauts`（2 类）
- `xaero.map.server.mods.ftbteams`（2 类）
- `xaero.map.server.mods.opac`（2 类）
- `xaero.map.server.player`（3 类）
- `xaero.map.server.radar.tracker`（4 类）
- `xaero.map.settings`（1 类）
- `xaero.map.task`（1 类）
- `xaero.map.teleport`（1 类）
- `xaero.map.util.linked`（2 类）
- `xaero.map.world`（4 类）
