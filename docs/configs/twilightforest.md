---
页面: configs/twilightforest
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `twilightforest` 配置项

> **2 个文件 / 53 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/twilightforest](../../mods/twilightforest/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `twilightforest-client.toml` | `silentCicadas` | `False` | bool | Makes Cicadas silent for those having sound library problems | `twilightforest:CicadaBlockEntity`、`twilightforest:CicadaJarBlock`、`twilightforest:MovingCicadaSoundInstance` |
| `twilightforest-client.toml` | `silentCicadasOnHead` | `False` | bool | Makes Cicadas silent when one is on your head. If the above  | `twilightforest:MovingCicadaSoundInstance`、`twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `screenShakingEffect` | `True` | bool | Controls whether the screen shakes when a Magic Bean is in t | `twilightforest:TFClientConfig` |
| `twilightforest-client.toml` | `rotateTrophyHeadsGui` | `True` | bool | Rotate Trophy heads on item model. Has no performance impact | `twilightforest:ISTER`、`twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `disableOptifineNagScreen` | `False` | bool | Disable the nag screen when OptiFine is installed. | `twilightforest:ClientGameEvents`、`twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `disableLockedBiomeToasts` | `False` | bool | Disables the toasts that appear when entering a locked biome | `twilightforest:LockedBiomeToastHandler`、`twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `questRamWoolIndicator` | `True` | bool | Renders a check mark or X above your crosshair while holding | `twilightforest:TFClientConfig` |
| `twilightforest-client.toml` | `fortificationShieldIndicator` | `True` | bool | "Turn this off if other mods render over/under it. | `twilightforest:TFClientConfig` |
| `twilightforest-client.toml` | `fortificationShieldIndicatorInCreative` | `False` | bool | Enables the fortification shield indicator in creative for d | `twilightforest:TFClientConfig` |
| `twilightforest-client.toml` | `cloudBlockPrecipitationDistance` | `-1` | int | Range: > -1 | `twilightforest:TFClientConfig`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `giantSkinUUIDs` | `[]` | list | List of player UUIDs whose skins the Giants should use. Leav | `twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `auroraBiomes` | `["twilightforest:glacier"]` | list | Defines which biomes the aurora shader effect will appear in | `twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `prettifyOreMeterGui` | `True` | bool | Lines up the dashes and percentages in the Ore Meter GUI. | `twilightforest:OverlayHandler`、`twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `totemCharmAnimation` | `False` | bool | Makes Charms when triggered display like the Totem of Undyin | `twilightforest:TFClientConfig` |
| `twilightforest-client.toml` | `travellersWingsGradualGlide` | `True` | bool | When this option is off, slow falling is the default. Holdin | `twilightforest:TFClientConfig`、`twilightforest:TravellersGearEvents`、`twilightforest:TravellersGearLogic` |
| `twilightforest-client.toml` | `firstPersonGloveOverlay` | `True` | bool | Allows Traveller's Gloves to render on your hand when in 1st | `twilightforest:TFClientConfig`、`twilightforest:TFConfig`、`twilightforest:TravellersClientEvents` |
| `twilightforest-client.toml` | `Item Display Modifier Settings.screenOffsetX` | `4` | int | Range: > -2147483648 | `twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `Item Display Modifier Settings.screenOffsetY` | `4` | int | Range: > -2147483648 | `twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-client.toml` | `Item Display Modifier Settings.screenScale` | `1.0` | float | Range: 0.1 ~ 10.0 | `twilightforest:TFClientConfig`、`twilightforest:TFConfig`、`xaerominimap:GuiEditMode` |
| `twilightforest-client.toml` | `Item Display Modifier Settings.twentyFourHourFormat` | `True` | bool | If ON, the clock upgrade displays time in 24-hour format ins | `twilightforest:TFClientConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `casketUUIDLocking` | `False` | bool | NOTE: server operators will still be able to open locked cas | `twilightforest:CharmEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `disableSkullCandleCreation` | `False` | bool | If true, disables the ability to make Skull Candles by right | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `showEnchantmentsOnItems` | `True` | bool | Please note that this doesn't affect the crafting recipes th | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `bossesSpawnDropChests` | `True` | bool | Note that the Knight Phantoms are not affected by this as th | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `cloudBlockPrecipitationDistance` | `32` | int | Range: > 0 | `twilightforest:TFClientConfig`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `multiplayerFightAdjuster` | `NONE` | str | Allowed Values: NONE, MORE_LOOT, MORE_HEALTH, MORE_LOOT_AND_ | `twilightforest:EntityEvents`、`twilightforest:MultiplayerBasedAdditionLootFunction`、`twilightforest:MultiplayerBasedNumberProvider` |
| `twilightforest-common.toml` | `Dimension Settings.newPlayersSpawnInTF` | `False` | bool | If true, players spawning for the first time will spawn in t | `twilightforest:CapabilityEvents`、`twilightforest:ConfigComments`、`twilightforest:LangGenerator` |
| `twilightforest-common.toml` | `Dimension Settings.portalForNewPlayer` | `False` | bool | If true, the return portal will spawn for new players that w | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Portal Settings.originDimension` | `minecraft:overworld` | str | The dimension you can always travel to the Twilight Forest f | `twilightforest:ProgressionEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Portal Settings.allowPortalsInOtherDimensions` | `False` | bool | Allow portals to the Twilight Forest to be made outside of t | `twilightforest:ProgressionEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Portal Settings.portalCreationPermission` | `0` | int | Range: 0 ~ 4 | `twilightforest:ProgressionEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Portal Settings.disablePortalCreation` | `False` | bool | Disable Twilight Forest portal creation entirely. Provided f | `twilightforest:ProgressionEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Portal Settings.checkPortalPlacement` | `True` | bool | Note that disabling this also reduces the rate at which port | `twilightforest:ProgressionEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Portal Settings.destructivePortalLightning` | `True` | bool | Set this to false if you want the lightning that zaps the po | `twilightforest:TFCommonConfig`、`twilightforest:TFConfig`、`twilightforest:TFPortalBlock` |
| `twilightforest-common.toml` | `Portal Settings.shouldReturnPortalBeUsable` | `True` | bool | If false, the return portal will require the activation item | `twilightforest:TFCommonConfig`、`twilightforest:TFConfig`、`twilightforest:TFTeleporter` |
| `twilightforest-common.toml` | `Portal Settings.portalUnlockedByAdvancement` | `` | str | Use a valid advancement resource location as a string. For e | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Portal Settings.maxPortalSize` | `64` | int | Range: > 4 | `twilightforest:TFCommonConfig`、`twilightforest:TFConfig`、`twilightforest:TFPortalBlock` |
| `twilightforest-common.toml` | `Uncrafting Table.uncraftingXpCostMultiplier` | `1.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `twilightforest:ConfigSetup`、`twilightforest:SyncUncraftingTableConfigPacket`、`twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.repairingXpCostMultiplier` | `1.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `twilightforest:ConfigSetup`、`twilightforest:SyncUncraftingTableConfigPacket`、`twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.disableUncraftingRecipes` | `["twilightforest:giant_log_to_oak_planks` | list | If you have a problematic ingredient, like infested towerwoo | `twilightforest:ConfigSetup`、`twilightforest:RecipeViewerConstants`、`twilightforest:SyncUncraftingTableConfigPacket` |
| `twilightforest-common.toml` | `Uncrafting Table.flipRecipeList` | `False` | bool | If true, this will invert the above uncrafting recipe list f | `twilightforest:SyncUncraftingTableConfigPacket`、`twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.blacklistedUncraftingModIds` | `[]` | list | Example: "twilightforest" will disable all uncrafting recipe | `twilightforest:ConfigSetup`、`twilightforest:RecipeViewerConstants`、`twilightforest:SyncUncraftingTableConfigPacket` |
| `twilightforest-common.toml` | `Uncrafting Table.flipIdList` | `False` | bool | If true, this will invert the above option from a blacklist  | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.enableShapelessCrafting` | `False` | bool | The table was originally intended to only take shaped recipe | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.disableIngredientSwitching` | `False` | bool | If you want to prevent certain ingredients from showing up i | `twilightforest:ConfigSetup`、`twilightforest:SyncUncraftingTableConfigPacket`、`twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.disableUncrafting` | `True` | bool | Do note that special uncrafting recipes are not disabled as  | `twilightforest:ConfigComments`、`twilightforest:LangGenerator`、`twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Uncrafting Table.disableUncraftingTable` | `True` | bool | If you are looking to just prevent normal crafting recipes f | `twilightforest:TFCommonConfig` |
| `twilightforest-common.toml` | `Magic Trees.timeCoreRange` | `16` | int | Range: 0 ~ 128 | `twilightforest:TFCommonConfig`、`twilightforest:TFConfig`、`twilightforest:TimeLogCoreBlock` |
| `twilightforest-common.toml` | `Magic Trees.transformationCoreRange` | `16` | int | Range: 0 ~ 128 | `twilightforest:TFCommonConfig`、`twilightforest:TFConfig`、`twilightforest:TransLogCoreBlock` |
| `twilightforest-common.toml` | `Magic Trees.miningCoreRange` | `16` | int | Range: 0 ~ 128 | `twilightforest:MineLogCoreBlock`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Magic Trees.sortingCoreRange` | `16` | int | Range: 0 ~ 128 | `twilightforest:SortLogCoreBlock`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Shield Parrying.parryNonTwilightAttacks` | `False` | bool | Set to true to parry non-Twilight projectiles. | `twilightforest:EntityEvents`、`twilightforest:TFCommonConfig`、`twilightforest:TFConfig` |
| `twilightforest-common.toml` | `Shield Parrying.shieldParryTicksArrow` | `40` | int | Range: > 0 | `twilightforest:TFCommonConfig` |
