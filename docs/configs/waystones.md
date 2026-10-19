---
页面: configs/waystones
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `waystones` 配置项

> **1 个文件 / 34 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/waystones](../../mods/waystones/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `waystones-common.toml` | `general.restrictedWaystones` | `["PLAYER"]` | list | List of waystone origins that should prevent others from edi | `waystones:WaystonePermissionManager`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `general.defaultVisibility` | `ACTIVATION` | str | Allowed Values: ACTIVATION, GLOBAL, SHARD_ONLY, ORANGE_SHARE | `lithium:MaskedList`、`lithium:ReferenceMaskedList`、`waystones:PlayerWaystoneManager` |
| `waystones-common.toml` | `general.allowedVisibilities` | `[]` | list | Add "GLOBAL" to allow every player to create global waystone | `waystones:WaystonePermissionManager`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `general.warpStoneUseTime` | `32` | int | The time in ticks that it takes to use a warp stone. This is | `waystones:WarpStoneItem`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `general.warpPlateUseTime` | `15` | int | The time in ticks that it takes to use a warp plate. This is | `waystones:WarpPlateBlockEntity`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `general.scrollUseTime` | `32` | int | The time in ticks it takes to use a scroll. This is the char | `waystones:BoundScrollItem`、`waystones:ScrollItemBase`、`waystones:WarpScrollItem` |
| `waystones-common.toml` | `inventoryButton.inventoryButton` | `` | str | Set to 'NONE' for no inventory button. Set to 'NEAREST' for  | `waystones:EditWaystoneMessage`、`waystones:InventoryButtonGuiHandler`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `inventoryButton.inventoryButtonX` | `58` | int | The x position of the inventory button in the inventory. | `waystones:InventoryButtonGuiHandler`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `inventoryButton.inventoryButtonY` | `60` | int | The y position of the inventory button in the inventory. | `waystones:InventoryButtonGuiHandler`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `inventoryButton.creativeInventoryButtonX` | `88` | int | The y position of the inventory button in the creative menu. | `waystones:InventoryButtonGuiHandler`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `inventoryButton.creativeInventoryButtonY` | `33` | int | The y position of the inventory button in the creative menu. | `waystones:InventoryButtonGuiHandler`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.wildWaystoneStyle` | `BIOME` | str | Allowed Values: DEFAULT, MOSSY, SANDY, BLACKSTONE, DEEPSLATE | `waystones:ModWorldGen`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.chunksBetweenWildWaystones` | `25` | int | Approximate chunk distance between wild waystones being gene | `waystones:WaystonePlacement`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.wildWaystonesDimensionAllowList` | `["minecraft:overworld", "minecraft:the_e` | list | List of dimensions that wild waystones are allowed to spawn  | `waystones:WaystonePlacement`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.wildWaystonesDimensionDenyList` | `[]` | list | List of dimensions that wild waystones are not allowed to sp | `waystones:WaystonePlacement`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.nameGenerationMode` | `PRESET_FIRST` | str | Allowed Values: PRESET_FIRST, RANDOM_ONLY, PRESET_ONLY, MIXE | `waystones:NameGeneratorManager`、`waystones:PlayerWaystoneManager`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.nameGenerationTemplate` | `{MrPork}` | str | The template to use when generating new names. Supported pla | `waystones:NameGeneratorManager`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.nameGenerationPresets` | `[]` | list | These names will be used for the PRESET name generation mode | `waystones:CustomNameGenerator`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `worldGen.spawnInVillages` | `REGULAR` | str | Allowed Values: DISABLED, REGULAR, FREQUENT | `waystones:ForceSpawnInVillagesCondition`、`waystones:JigsawPlacementPlacerMixin`、`waystones:ModWorldGen` |
| `waystones-common.toml` | `teleports.enableCosts` | `True` | bool | Set to false to simply disable all xp costs. See warpRequire | `waystones:RequirementRegistry`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `teleports.enableCooldowns` | `True` | bool | Set to false to simply disable all cooldowns. See warpRequir | `waystones:RequirementRegistry`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `teleports.warpRequirements` | `["[is_not_interdimensional] scaled_add_x` | list | List of warp requirements with comma-separated parameters in | `waystones:InternalMethodsImpl`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `teleports.transportPets` | `DISABLED` | str | Allowed Values: ENABLED, SAME_DIMENSION, DISABLED | `waystones:InternalMethodsImpl`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `teleports.transportLeashed` | `ENABLED` | str | Allowed Values: ENABLED, SAME_DIMENSION, DISABLED | `waystones:WaystoneTeleportManager`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `teleports.entityDenyList` | `["minecraft:wither"]` | list | List of entities that cannot be teleported, either as pet, l | `waystones:WaystonePermissionManager`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `teleports.enableModifiers` | `True` | bool | Set to true to enable warp modifier items for applying statu | `waystones:InternalMethodsImpl`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `client.disableTextGlow` | `False` | bool | If enabled, the text overlay on waystones will no longer alw | `waystones:PortstoneRenderer`、`waystones:SharestoneRenderer`、`waystones:WaystoneRenderer` |
| `waystones-common.toml` | `compatibility.journeyMap` | `True` | bool | If enabled, JourneyMap waypoints will be created for each ac | `waystones:JourneyMapIntegration`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `compatibility.preferJourneyMapIntegrationMod` | `True` | bool | If enabled, JourneyMap waypoints will only be created if the | `waystones:JourneyMapIntegration`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `compatibility.dynmap` | `True` | bool | If enabled, Waystones will add markers for waystones and sha | `waystones:DynmapIntegration`、`waystones:Waystones`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `blueMap.enabled` | `True` | bool | Controls whether Waystones' BlueMap integration should be lo | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `waystones-common.toml` | `blueMap.includeWaystones` | `True` | bool | If enabled, waystones will be tracked as markers on BlueMap | `waystones:BlueMapIntegration`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `blueMap.includeSharestones` | `True` | bool | If enabled, sharestones will be tracked as markers on BlueMa | `waystones:BlueMapIntegration`、`waystones:WaystonesConfigData` |
| `waystones-common.toml` | `blueMap.includeUndiscoveredWaystones` | `False` | bool | If enabled, undiscovered waystones will be tracked as marker | `waystones:BlueMapIntegration`、`waystones:WaystonesConfigData` |
