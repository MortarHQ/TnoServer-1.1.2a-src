---
页面: configs/xaerominimap
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `xaerominimap` 配置项

> **2 个文件 / 154 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/xaerominimap](../../mods/xaerominimap/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `xaerominimap_entities.json` | `hardInclude` | `anything` | str |  | `xaerominimap:EntityRadarCategoryData`、`xaerominimap:FilterObjectCategoryData`、`xaerominimap:FilterObjectCategorySerializationHandler` |
| `xaerominimap_entities.json` | `includeList` | `[]` | list |  | `xaerominimap:EditorEntityRadarCategorySettingsNode`、`xaerominimap:EditorFilterCategoryNodeConverter`、`xaerominimap:EditorFilterSettingsNode` |
| `xaerominimap_entities.json` | `includeListInSuperCategory` | `True` | bool |  | `xaerominimap:EntityRadarCategoryData`、`xaerominimap:FilterObjectCategoryData` |
| `xaerominimap_entities.json` | `excludeMode` | `ONLY` | str |  | `xaerominimap:EditorExcludeListNode`、`xaerominimap:EntityRadarCategory`、`xaerominimap:EntityRadarCategoryData` |
| `xaerominimap_entities.json` | `excludeList` | `["minecraft:glow_item_frame", "minecraft` | list |  | `xaerominimap:EditorEntityRadarCategorySettingsNode`、`xaerominimap:EditorFilterCategoryNodeConverter`、`xaerominimap:EditorFilterSettingsNode` |
| `xaerominimap_entities.json` | `name` | `gui.xaero_entity_category_root` | str |  | `ancient_artifacts:ArtifactAttributesRegistry`、`ancient_artifacts:ArtifactCurioItem`、`ancient_artifacts:ArtifactText` |
| `xaerominimap_entities.json` | `protection` | `True` | bool |  | `artifacts:Language`、`chronodawn:BlockProtectionEventHandler`、`chronodawn:BlockProtectionHandler` |
| `xaerominimap_entities.json` | `settingOverrides.displayed` | `True` | bool |  | `artifacts:ClientConfig`、`cloth_config:DynamicEntryListWidget`、`ftbchunks:FTBChunksClientConfig` |
| `xaerominimap_entities.json` | `settingOverrides.displayHeight` | `0.0` | float |  | `creativecore:GuiEventHandler`、`creativecore:IScaleableGuiScreen`、`xaerominimap:EntityRadarCategorySettings` |
| `xaerominimap_entities.json` | `settingOverrides.heightBasedFade` | `True` | bool |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:MinimapRadar`、`xaerominimap:MinimapSafeModeRenderer` |
| `xaerominimap_entities.json` | `settingOverrides.renderOrder` | `0.0` | float |  | `xaerominimap:EntityRadarCategorySettings` |
| `xaerominimap_entities.json` | `settingOverrides.color` | `13.0` | float |  | `architectury:BiomeHooks`、`architectury:BiomeModificationsImpl`、`architectury:ColorHandlerRegistry` |
| `xaerominimap_entities.json` | `settingOverrides.displayNameWhenIconFails` | `True` | bool |  | `xaerominimap:EntityRadarBackwardsCompatibilityConfig`、`xaerominimap:EntityRadarCategorySettings`、`xaerominimap:EntityRadarDefaultCategories` |
| `xaerominimap_entities.json` | `settingOverrides.entityNumber` | `1000.0` | float |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:RadarStateUpdater` |
| `xaerominimap_entities.json` | `settingOverrides.alwaysDisplayNametags` | `False` | bool |  | `xaerominimap:EntityRadarCategorySettings` |
| `xaerominimap_entities.json` | `settingOverrides.dotSize` | `2.0` | float |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:RadarRenderContext`、`xaerominimap:RadarRenderProvider` |
| `xaerominimap_entities.json` | `settingOverrides.startFadingAt` | `0.0` | float |  | `xaerominimap:EntityRadarCategorySettings`、`xaerominimap:MinimapRadar`、`xaerominimap:MinimapSafeModeRenderer` |
| `xaerominimap_entities.json` | `settingOverrides.renderOverMinimapFrame` | `1.0` | float |  | `xaerominimap:EntityRadarCategorySettings` |
| `xaerominimap_entities.json` | `settingOverrides.icons` | `1.0` | float |  | `artifacts:HeliumFlamingoOverlay`、`balm:BalmCommand`、`balm:IconExport` |
| `xaerominimap_entities.json` | `settingOverrides.heightLimit` | `20.0` | float |  | `eldergrove:GreatwoodTreeGenerator`、`xaerominimap:EntityRadarBackwardsCompatibilityConfig`、`xaerominimap:EntityRadarCategorySettings` |
| `xaerominimap_entities.json` | `settingOverrides.names` | `0.0` | float |  | `artifacts:ArtifactRenderer`、`artifacts:ArtifactsConfigScreen`、`artifacts:Language` |
| `xaerominimap_entities.json` | `settingOverrides.iconScale` | `1.0` | float |  | `ftbchunks:FTBChunksClient`、`ftbquests:Quest`、`tensura_guild:DwarfTaskMarkers` |
| `xaerominimap_entities.json` | `subCategories` | `[{"hardInclude": "living", "includeList"` | list |  | `artifacts:ArtifactsConfigScreen`、`xaerominimap:EditorCategoryNode`、`xaerominimap:EditorEntityRadarCategoryNode` |
| `xaerominimap.txt` | `ignoreUpdate` | `0` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `settingsButton` | `false` | string |  | `ftbchunks:ChunkScreen`、`ftbchunks:MapScreenButtonPanel`、`ftbquests:EditRewardTableScreen` |
| `xaerominimap.txt` | `allowWrongWorldTeleportation` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `differentiateByServerAddress` | `true` | string |  | `xaerominimap:MinimapWorldStateUpdater`、`xaerominimap:ModSettings`、`xaeroworldmap:MapProcessor` |
| `xaerominimap.txt` | `debugEntityIcons` | `false` | string |  | `xaerominimap:ModSettings`、`xaerominimap:RadarRenderer` |
| `xaerominimap.txt` | `debugEntityVariantIds` | `false` | string |  | `xaerominimap:ModSettings`、`xaerominimap:RadarIconManager`、`xaerominimap:RadarRenderer` |
| `xaerominimap.txt` | `radarHideInvisibleEntities` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `allowInternetAccess` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `updateNotification` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `minimap` | `true` | string |  | `ftbchunks:BiomeComponent`、`ftbchunks:DebugComponent`、`ftbchunks:FPSComponent` |
| `xaerominimap.txt` | `caveMaps` | `2` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `caveZoom` | `1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `showWaypoints` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `showIngameWaypoints` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `displayRedstone` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `deathpoints` | `true` | string |  | `ftbchunks:WaypointManagerImpl`、`xaerominimap:AbstractWaypointRenderProvider`、`xaerominimap:DestinationHandler` |
| `xaerominimap.txt` | `oldDeathpoints` | `true` | string |  | `xaerominimap:DeathpointHandler`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `distance` | `1` | string |  | `artifacts:ItemConfigs`、`artifacts:Language`、`artifacts:MimicEntity` |
| `xaerominimap.txt` | `lockNorth` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `zoom` | `0` | string |  | `cataclysm:ClientEvent`、`creativecore:GuiTimeline`、`emi:BoMScreen` |
| `xaerominimap.txt` | `minimapSize` | `0` | string |  | `xaerominimap:MinimapProcessor`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `chunkGrid` | `-1` | string |  | `ftbchunks:RenderMapImageTask`、`xaerominimap:MinimapTile`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `slimeChunks` | `false` | string |  | `xaerominimap:MinimapFBORenderer`、`xaerominimap:MinimapSafeModeRenderer`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `mapSafeMode` | `false` | string |  | `xaerominimap:GuiMinimapSettings`、`xaerominimap:Minimap`、`xaerominimap:MinimapFBORenderer` |
| `xaerominimap.txt` | `minimapOpacity` | `100.0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsIngameIconScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsIngameDistanceScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsIngameNameScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsIngameCloseScale` | `1.0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `antiAliasing` | `true` | string |  | `xaerominimap:MinimapFBORenderer`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `blockColours` | `0` | string |  | `xaerominimap:MinimapWriter`、`xaerominimap:ModSettings`、`xaeroworldmap:MapWriter` |
| `xaerominimap.txt` | `lighting` | `true` | string |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseCraftingGen`、`byepregen:BlockLightEngineYASuperMixin` |
| `xaerominimap.txt` | `dotsStyle` | `0` | string |  | `xaerominimap:ModSettings`、`xaerominimap:RadarRenderer` |
| `xaerominimap.txt` | `dotNameScale` | `1.0` | string |  | `xaerominimap:ModSettings`、`xaerominimap:RadarRenderer` |
| `xaerominimap.txt` | `compassOverEverything` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `showFlowers` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `keepWaypointNames` | `true` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `waypointsDistanceExp` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsDistanceMin` | `0.0` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `defaultWaypointTPCommandFormat` | `/tp @s {x} {y} {z}` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `defaultWaypointTPCommandRotationFormat` | `/tp @s {x} {y} {z} {yaw} ~` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `arrowScale` | `1.5` | string |  | `xaerominimap:MinimapRenderer`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `arrowColour` | `0` | string |  | `xaerominimap:MinimapRenderer`、`xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `smoothDots` | `true` | string |  | `xaerominimap:ModSettings`、`xaerominimap:RadarRenderer` |
| `xaerominimap.txt` | `worldMap` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `terrainDepth` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:MapPixel`、`xaeroworldmap:MapUpdateFastConfig` |
| `xaerominimap.txt` | `terrainSlopes` | `2` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:MapPixel`、`xaeroworldmap:MapUpdateFastConfig` |
| `xaerominimap.txt` | `mainEntityAs` | `0` | string |  | `xaerominimap:MinimapRenderer`、`xaerominimap:MinimapSafeModeRenderer`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `blockTransparency` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointOpacityIngame` | `80` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointOpacityMap` | `90` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `hideWorldNames` | `1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `openSlimeSettings` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `alwaysShowDistance` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `renderLayerIndex` | `1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `crossDimensionalTp` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `biomeColorsVanillaMode` | `false` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `lookingAtAngle` | `10` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `lookingAtAngleVertical` | `180` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `centeredEnlarged` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `zoomOnEnlarged` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `minimapTextAlign` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsMutualEdit` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `compassLocation` | `1` | string |  | `xaerominimap:MinimapRenderer`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `compassDirectionScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `caveMapsDepth` | `30` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `hideWaypointCoordinates` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `renderAllSets` | `false` | string |  | `xaerominimap:AbstractWaypointRenderProvider`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `playerArrowOpacity` | `100` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `waypointsBottom` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `minimapShape` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `lightOverlayType` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `lightOverlayMaxLight` | `7` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `lightOverlayMinLight` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `lightOverlayColor` | `13` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `uiScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `bossHealthPushBox` | `1` | string |  | `xaerominimap:ModSettings`、`xaerominimap:XaeroMinimapCore` |
| `xaerominimap.txt` | `potionEffectPushBox` | `1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `minimapFrame` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `minimapFrameColor` | `9` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `compassColor` | `9` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `northCompassColor` | `-1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `displayMultipleWaypointInfo` | `1` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `entityRadar` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `adjustHeightForCarpetLikeBlocks` | `true` | string |  | `xaerominimap:MinimapWriter`、`xaerominimap:ModSettings`、`xaeroworldmap:MapTileChunk` |
| `xaerominimap.txt` | `autoConvertWaypointDistanceToKmThreshold` | `10000` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `waypointDistancePrecision` | `1` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `mainDotSize` | `2` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `partialYTeleportation` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `deleteReachedDeathpoints` | `true` | string |  | `xaerominimap:AbstractWaypointRenderProvider`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `hideMinimapUnderScreen` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `hideMinimapUnderF3` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `manualCaveModeStartAuto` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `manualCaveModeStart` | `-1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `chunkGridLineWidth` | `1` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `temporaryWaypointsGlobal` | `true` | string |  | `xaerominimap:ModSettings`、`xaerominimap:WaypointMapRenderer`、`xaerominimap:WaypointWorldRenderer` |
| `xaerominimap.txt` | `keepUnlockedWhenEnlarged` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `enlargedMinimapAToggle` | `false` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `displayStainedGlass` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `waypointOnMapScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `switchToAutoOnDeath` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `infoDisplayBackgroundOpacity` | `40` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `caveModeToggleTimer` | `1000` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `legibleCaveMaps` | `false` | string |  | `xaerominimap:MinimapWriter`、`xaerominimap:ModSettings`、`xaeroworldmap:MapPixel` |
| `xaerominimap.txt` | `biomeBlending` | `true` | string |  | `xaerominimap:BiomeBlendCalculator`、`xaerominimap:ModSettings`、`xaeroworldmap:BiomeColorCalculator` |
| `xaerominimap.txt` | `displayTrackedPlayersOnMap` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `displayTrackedPlayersInWorld` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `dimensionScaledMaxWaypointDistance` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `trackedPlayerWorldIconScale` | `0` | string |  | `xaerominimap:ModSettings`、`xaerominimap:PlayerTrackerMinimapElementRenderer` |
| `xaerominimap.txt` | `trackedPlayerWorldNameScale` | `0` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `trackedPlayerMinimapIconScale` | `0` | string |  | `xaerominimap:ModSettings`、`xaerominimap:PlayerTrackerMinimapElementRenderer` |
| `xaerominimap.txt` | `displayClaims` | `true` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `displayCurrentClaim` | `true` | string |  | `xaerominimap:ModSettings` |
| `xaerominimap.txt` | `claimsFillOpacity` | `46` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `claimsBorderOpacity` | `80` | string |  | `xaerominimap:ModSettings`、`xaeroworldmap:ModSettings` |
| `xaerominimap.txt` | `infoDisplayOrder` | `coords:overworld_coords:chunk_coords:ang` | string |  | `xaerominimap:InfoDisplayIO`、`xaerominimap:ModSettings` |
| `xaerominimap.txt` | `infoDisplay` | `coords:true:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `overworld_coords:false:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `chunk_coords:false:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `angles:false:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `dimension:false:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `biome:false:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `weather:false:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `light_level:0:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `time:0:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `real_time:0:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `highlights:true:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `light_overlay_indicator:true:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `manual_cave_mode_indicator:true:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `infoDisplay` | `custom_sub_world:true:15:-1` | string |  | `xaerominimap:GuiInfoDisplayEdit`、`xaerominimap:InfoDisplayCompiler`、`xaerominimap:InfoDisplayConfigClientUtils` |
| `xaerominimap.txt` | `module;id` | `xaerominimap:minimap;active=true;x=-44;y` | string |  | — |
