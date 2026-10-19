---
页面: configs/xaeroworldmap
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `xaeroworldmap` 配置项

> **3 个文件 / 86 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/xaeroworldmap](../../mods/xaeroworldmap/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `xaeroworldmap.txt` | `ignoreUpdate` | `0` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `updateNotification` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `allowInternetAccess` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `differentiateByServerAddress` | `true` | string |  | `xaerominimap:MinimapWorldStateUpdater`、`xaerominimap:ModSettings`、`xaeroworldmap:MapProcessor` |
| `xaeroworldmap.txt` | `caveMapsAllowed` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `debug` | `false` | string |  | `architectury:ArchitecturySpawnEggItem`、`artifacts:ConfigManager`、`artifacts:PlatformServices` |
| `xaeroworldmap.txt` | `lighting` | `true` | string |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseCraftingGen`、`byepregen:BlockLightEngineYASuperMixin` |
| `xaeroworldmap.txt` | `colours` | `0` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `loadChunks` | `true` | string |  | `naturesaura:BlockEntityChunkLoader`、`xaeroworldmap:MapWriter`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `updateChunks` | `true` | string |  | `xaeroworldmap:MapWriter`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `terrainSlopes` | `2` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:MapPixel`、`xaeroworldmap:MapUpdateFastConfig` |
| `xaeroworldmap.txt` | `terrainDepth` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:MapPixel`、`xaeroworldmap:MapUpdateFastConfig` |
| `xaeroworldmap.txt` | `footsteps` | `true` | string |  | `artifacts:SoundDefinitions`、`block_factorys_bosses:BossesRiseSoundProvider`、`tensuramoreskills:TensuraAiKnowledge` |
| `xaeroworldmap.txt` | `flowers` | `true` | string |  | `eternal_starlight:LunarVineBlockEntity`、`regions_unexplored:RUCommonConfig`、`regions_unexplored:RuUltraFromMegaTreeGrower` |
| `xaeroworldmap.txt` | `coordinates` | `true` | string |  | `bookshelf:FontCommand`、`bookshelf:StructureCommand`、`constructionwand:HandlerWirelessGrid` |
| `xaeroworldmap.txt` | `hoveredBiome` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `biomeColorsVanillaMode` | `false` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `waypoints` | `true` | string |  | `foxablazeultimate:CielTakeoverNavigator`、`ftbchunks:FTBChunksClientConfig`、`ftbchunks:FTBChunksWorldConfig` |
| `xaeroworldmap.txt` | `renderArrow` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `displayZoom` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `worldmapWaypointsScale` | `1.0` | string |  | `xaeroworldmap:ModSettings`、`xaeroworldmap:WaypointReader`、`xaeroworldmap:WaypointRenderContext` |
| `xaeroworldmap.txt` | `openMapAnimation` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `reloadVersion` | `0` | string |  | `xaeroworldmap:MapRegion`、`xaeroworldmap:ModSettings`、`xaeroworldmap:RegionDetection` |
| `xaeroworldmap.txt` | `reloadEverything` | `false` | string |  | `xaeroworldmap:GuiMap`、`xaeroworldmap:MapSaveLoad`、`xaeroworldmap:MinimapRenderListener` |
| `xaeroworldmap.txt` | `zoomButtons` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `waypointBackgrounds` | `true` | string |  | `xaeroworldmap:ModSettings`、`xaeroworldmap:WaypointReader`、`xaeroworldmap:WaypointRenderContext` |
| `xaeroworldmap.txt` | `detectAmbiguousY` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `showDisabledWaypoints` | `false` | string |  | `xaeroworldmap:ModSettings`、`xaeroworldmap:WaypointMenuRenderer`、`xaeroworldmap:WaypointReader` |
| `xaeroworldmap.txt` | `closeWaypointsWhenHopping` | `true` | string |  | `xaeroworldmap:GuiMap`、`xaeroworldmap:ModSettings`、`xaeroworldmap:WaypointMenuRenderer` |
| `xaeroworldmap.txt` | `adjustHeightForCarpetLikeBlocks` | `true` | string |  | `xaerominimap:MinimapWriter`、`xaerominimap:ModSettings`、`xaeroworldmap:MapTileChunk` |
| `xaeroworldmap.txt` | `onlyCurrentMapWaypoints` | `false` | string |  | `xaeroworldmap:ModSettings`、`xaeroworldmap:SupportXaeroMinimap`、`xaeroworldmap:WaypointMenuRenderer` |
| `xaeroworldmap.txt` | `minZoomForLocalWaypoints` | `0.0` | string |  | `xaeroworldmap:ModSettings`、`xaeroworldmap:WaypointReader`、`xaeroworldmap:WaypointRenderContext` |
| `xaeroworldmap.txt` | `arrowColour` | `-2` | string |  | `xaerominimap:MinimapRenderer`、`xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `minimapRadar` | `true` | string |  | `xaerominimap:RadarRenderer`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `renderWaypoints` | `true` | string |  | `xaeroworldmap:ModSettings`、`xaeroworldmap:WaypointMenuRenderer`、`xaeroworldmap:WaypointRenderer` |
| `xaeroworldmap.txt` | `partialYTeleportation` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `displayStainedGlass` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `caveModeDepth` | `30` | string |  | `xaeroworldmap:MapProcessor`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `caveModeStart` | `2147483647` | string |  | `xaerominimap:SupportXaeroWorldmap`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `autoCaveMode` | `-1` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `legibleCaveMaps` | `false` | string |  | `xaerominimap:MinimapWriter`、`xaerominimap:ModSettings`、`xaeroworldmap:MapPixel` |
| `xaeroworldmap.txt` | `displayCaveModeStart` | `true` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `caveModeToggleTimer` | `1000` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `defaultCaveModeType` | `1` | string |  | `xaerominimap:SupportXaeroWorldmap`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `biomeBlending` | `true` | string |  | `xaerominimap:BiomeBlendCalculator`、`xaerominimap:ModSettings`、`xaeroworldmap:BiomeColorCalculator` |
| `xaeroworldmap.txt` | `trackedPlayers` | `true` | string |  | `xaerominimap:ClientSyncedTrackedPlayerManager`、`xaeroworldmap:ClientSyncedTrackedPlayerManager`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `multipleImagesExport` | `false` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `nightExport` | `false` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `highlightsExport` | `false` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `exportScaleDownSquare` | `20` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `mapWritingDistance` | `-1` | string |  | `xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `displayClaims` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `claimsFillOpacity` | `46` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `claimsBorderOpacity` | `80` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaeroworldmap.txt` | `globalVersion` | `1` | string |  | `xaeroworldmap:BranchLeveledRegion`、`xaeroworldmap:GuiMap`、`xaeroworldmap:MapProcessor` |
| `xaero/minimap/default_radar_categories_client.json` | `hardInclude` | `anything` | str |  | `xaerominimap:EntityRadarCategoryData`、`xaerominimap:FilterObjectCategoryData`、`xaerominimap:FilterObjectCategorySerializationHandler` |
| `xaero/minimap/default_radar_categories_client.json` | `includeList` | `[]` | list |  | `xaerominimap:EditorEntityRadarCategorySettingsNode`、`xaerominimap:EditorFilterCategoryNodeConverter`、`xaerominimap:EditorFilterSettingsNode` |
| `xaero/minimap/default_radar_categories_client.json` | `includeListInSuperCategory` | `True` | bool |  | `xaerominimap:EntityRadarCategoryData`、`xaerominimap:FilterObjectCategoryData` |
| `xaero/minimap/default_radar_categories_client.json` | `excludeMode` | `ONLY` | str |  | `xaerominimap:EditorExcludeListNode`、`xaerominimap:EntityRadarCategory`、`xaerominimap:EntityRadarCategoryData` |
| `xaero/minimap/default_radar_categories_client.json` | `excludeList` | `["minecraft:glow_item_frame", "minecraft` | list |  | `xaerominimap:EditorEntityRadarCategorySettingsNode`、`xaerominimap:EditorFilterCategoryNodeConverter`、`xaerominimap:EditorFilterSettingsNode` |
| `xaero/minimap/default_radar_categories_client.json` | `name` | `gui.xaero_entity_category_root` | str |  | `ancient_artifacts:ArtifactAttributesRegistry`、`ancient_artifacts:ArtifactCurioItem`、`ancient_artifacts:ArtifactText` |
| `xaero/minimap/default_radar_categories_client.json` | `protection` | `True` | bool |  | `artifacts:Language`、`chronodawn:BlockProtectionEventHandler`、`chronodawn:BlockProtectionHandler` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.displayed` | `True` | bool |  | `artifacts:ClientConfig`、`cloth_config:DynamicEntryListWidget`、`ftbchunks:FTBChunksClientConfig` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.displayHeight` | `0.0` | float |  | `creativecore:GuiEventHandler`、`creativecore:IScaleableGuiScreen`、`xaerominimap:EntityRadarCategorySettings` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.heightBasedFade` | `True` | bool |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:MinimapRadar`、`xaerominimap:MinimapSafeModeRenderer` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.renderOrder` | `0.0` | float |  | `xaerominimap:EntityRadarCategorySettings` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.color` | `13.0` | float |  | `architectury:BiomeHooks`、`architectury:BiomeModificationsImpl`、`architectury:ColorHandlerRegistry` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.displayNameWhenIconFails` | `True` | bool |  | `xaerominimap:EntityRadarBackwardsCompatibilityConfig`、`xaerominimap:EntityRadarCategorySettings`、`xaerominimap:EntityRadarDefaultCategories` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.entityNumber` | `1000.0` | float |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:RadarStateUpdater` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.alwaysDisplayNametags` | `False` | bool |  | `xaerominimap:EntityRadarCategorySettings` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.startFadingAt` | `0.0` | float |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:MinimapRadar`、`xaerominimap:MinimapSafeModeRenderer` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.dotSize` | `2.0` | float |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:RadarRenderContext`、`xaerominimap:RadarRenderProvider` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.renderOverMinimapFrame` | `1.0` | float |  | `xaerominimap:EntityRadarCategorySettings` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.icons` | `1.0` | float |  | `artifacts:HeliumFlamingoOverlay`、`balm:BalmCommand`、`balm:IconExport` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.heightLimit` | `20.0` | float |  | `eldergrove:GreatwoodTreeGenerator`、`xaerominimap:EntityRadarBackwardsCompatibilityConfig`、`xaerominimap:EntityRadarCategorySettings` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.names` | `0.0` | float |  | `artifacts:ArtifactRenderer`、`artifacts:ArtifactsConfigScreen`、`artifacts:Language` |
| `xaero/minimap/default_radar_categories_client.json` | `settingOverrides.iconScale` | `1.0` | float |  | `ftbchunks:FTBChunksClient`、`ftbquests:Quest`、`tensura_guild:DwarfTaskMarkers` |
| `xaero/minimap/default_radar_categories_client.json` | `subCategories` | `[{"hardInclude": "living", "includeList"` | list |  | `artifacts:ArtifactsConfigScreen`、`xaerominimap:EditorCategoryNode`、`xaerominimap:EditorEntityRadarCategoryNode` |
| `xaero/minimap/default_radar_categories_server.json` | `hardInclude` | `anything` | str |  | `xaerominimap:EntityRadarCategoryData`、`xaerominimap:FilterObjectCategoryData`、`xaerominimap:FilterObjectCategorySerializationHandler` |
| `xaero/minimap/default_radar_categories_server.json` | `includeList` | `[]` | list |  | `xaerominimap:EditorEntityRadarCategorySettingsNode`、`xaerominimap:EditorFilterCategoryNodeConverter`、`xaerominimap:EditorFilterSettingsNode` |
| `xaero/minimap/default_radar_categories_server.json` | `includeListInSuperCategory` | `True` | bool |  | `xaerominimap:EntityRadarCategoryData`、`xaerominimap:FilterObjectCategoryData` |
| `xaero/minimap/default_radar_categories_server.json` | `excludeMode` | `ONLY` | str |  | `xaerominimap:EditorExcludeListNode`、`xaerominimap:EntityRadarCategory`、`xaerominimap:EntityRadarCategoryData` |
| `xaero/minimap/default_radar_categories_server.json` | `excludeList` | `[]` | list |  | `xaerominimap:EditorEntityRadarCategorySettingsNode`、`xaerominimap:EditorFilterCategoryNodeConverter`、`xaerominimap:EditorFilterSettingsNode` |
| `xaero/minimap/default_radar_categories_server.json` | `name` | `gui.xaero_entity_category_root` | str |  | `ancient_artifacts:ArtifactAttributesRegistry`、`ancient_artifacts:ArtifactCurioItem`、`ancient_artifacts:ArtifactText` |
| `xaero/minimap/default_radar_categories_server.json` | `protection` | `True` | bool |  | `artifacts:Language`、`chronodawn:BlockProtectionEventHandler`、`chronodawn:BlockProtectionHandler` |
| `xaero/minimap/default_radar_categories_server.json` | `subCategories` | `[{"hardInclude": "living", "includeList"` | list |  | `artifacts:ArtifactsConfigScreen`、`xaerominimap:EditorCategoryNode`、`xaerominimap:EditorEntityRadarCategoryNode` |
