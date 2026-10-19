---
页面: configs/yigd
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `yigd` 配置项

> **1 个文件 / 131 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/yigd](../../mods/yigd/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `yigd.json` | `inventoryConfig.dropPlayerHead` | `False` | bool |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `inventoryConfig.itemLoss.affectStacks` | `False` | bool |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.usePercentRange` | `True` | bool |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.lossRangeFrom` | `0` | int |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.lossRangeTo` | `100` | int |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.weightedSelection` | `True` | bool |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.lossDropRule` | `DESTROY` | str |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.percentChanceOfLoss` | `50` | int |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.canLoseSoulbound` | `False` | bool |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.itemLoss.includeModdedInventories` | `True` | bool |  | `yigd:InventoryComponent`、`yigd:InventoryConfig` |
| `yigd.json` | `inventoryConfig.loseSoulboundLevelOnDeath` | `False` | bool |  | `yigd:InventoryConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `inventoryConfig.vanishingSlots` | `[]` | list |  | `yigd:InventoryConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `inventoryConfig.soulboundSlots` | `[]` | list |  | `yigd:InventoryConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `inventoryConfig.dropOnGroundSlots` | `[]` | list |  | `yigd:InventoryConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `expConfig.dropBehaviour` | `BEST_OF_BOTH` | str |  | `yigd:ExpComponent`、`yigd:ExpConfig` |
| `yigd.json` | `expConfig.dropPercentage` | `0` | int |  | `yigd:ExpComponent`、`yigd:ExpConfig` |
| `yigd.json` | `expConfig.keepPercentage` | `0` | int |  | `yigd:ExpComponent`、`yigd:ExpConfig` |
| `yigd.json` | `graveConfig.enabled` | `True` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `graveConfig.storeItems` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.storeXp` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.informGraveLocation` | `True` | bool |  | `tensura_tno:YigdCompat`、`yigd:GraveConfig`、`yigd:ServerEventHandler` |
| `yigd.json` | `graveConfig.requireItem` | `False` | bool |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.requiredItem` | `yigd:grave` | str |  | `tensura_guild:DwarfTaskDefinitions`、`tensura_guild:DwarfTaskService`、`tensura_guild:GuildTaskBoardScreen` |
| `yigd.json` | `graveConfig.requiredItemCount` | `1` | int |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.requireShovelToLoot` | `False` | bool |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.retrieveMethods.onClick` | `True` | bool |  | `cloth_config:ColorDisplayWidget`、`eternal_starlight:BookComponent`、`eternal_starlight:BookScreen` |
| `yigd.json` | `graveConfig.retrieveMethods.onBreak` | `False` | bool |  | `lodestone:ItemHolderBlockEntity`、`lodestone:LodestoneBlockEntity`、`lodestone:LodestoneEntityBlock` |
| `yigd.json` | `graveConfig.retrieveMethods.onSneak` | `False` | bool |  | `yigd:GraveBlock`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.retrieveMethods.onStand` | `False` | bool |  | `yigd:GraveBlock`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.mergeStacksOnRetrieve` | `True` | bool |  | `yigd:GraveConfig`、`yigd:InventoryComponent` |
| `yigd.json` | `graveConfig.dropOnRetrieve` | `IN_INVENTORY` | str |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.dropGraveBlock` | `False` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.generateEmptyGraves` | `False` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.overrideSpawnProtection` | `True` | bool |  | `yigd:DedicatedServerMixin`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.claimPriority` | `GRAVE` | str |  | `yigd:AbstractConfigScreenMixin`、`yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.graveRobbing.enabled` | `True` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `graveConfig.graveRobbing.killerSkipWaitTime` | `False` | bool |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.graveRobbing.afterTime` | `1` | int |  | `yigd:GraveBlockEntity`、`yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.graveRobbing.timeUnit` | `HOURS` | str |  | `resourcefullib:Scheduling`、`yigd:GraveBlockEntity`、`yigd:GraveComponent` |
| `yigd.json` | `graveConfig.graveRobbing.robPriority` | `INVENTORY` | str |  | `yigd:AbstractConfigScreenMixin`、`yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.graveRobbing.notifyWhenRobbed` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.graveRobbing.tellWhoRobbed` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig`、`yigd:ServerEventHandler` |
| `yigd.json` | `graveConfig.graveTimeout.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `graveConfig.graveTimeout.afterTime` | `5` | int |  | `yigd:GraveBlockEntity`、`yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.graveTimeout.timeUnit` | `HOURS` | str |  | `resourcefullib:Scheduling`、`yigd:GraveBlockEntity`、`yigd:GraveComponent` |
| `yigd.json` | `graveConfig.graveTimeout.dropContentsOnTimeout` | `True` | bool |  | `yigd:GraveBlockEntity`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.treatBindingCurse` | `True` | bool |  | `yigd:AccessoriesCompat`、`yigd:CuriosCompat`、`yigd:GraveComponent` |
| `yigd.json` | `graveConfig.generateGraveInVoid` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.minimumGraveYLevel` | `[{"key": "minecraft:overworld", "value":` | list |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.generateOnlyWithinBorder` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.ignoredDeathTypes` | `[]` | list |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.unlockable` | `True` | bool |  | `yigd:DeathScrollItem`、`yigd:GraveConfig`、`yigd:ServerPacketHandler` |
| `yigd.json` | `graveConfig.randomSpawn.percentSpawnChance` | `0` | int |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.randomSpawn.spawnEntity` | `minecraft:zombie` | str |  | `kubejs:LevelKJS`、`yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.randomSpawn.spawnNbt` | `{ArmorItems:[{},{},{},{id:"minecraft:pla` | str |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.generateOnLastGroundPos` | `False` | bool |  | `yigd:DeathHandler`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.tryGenerateOnGround` | `False` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.generationMaxDistance.x` | `5` | int |  | — |
| `yigd.json` | `graveConfig.generationMaxDistance.y` | `5` | int |  | — |
| `yigd.json` | `graveConfig.generationMaxDistance.z` | `5` | int |  | — |
| `yigd.json` | `graveConfig.useSoftBlockWhitelist` | `False` | bool |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.useStrictBlockBlacklist` | `True` | bool |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.replaceOldWhenClaimed` | `True` | bool |  | `yigd:GraveBlockEntity`、`yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.dropItemsIfDestroyed` | `False` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.notifyOwnerIfDestroyed` | `True` | bool |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.persistentGraves.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `graveConfig.persistentGraves.showDeathDay` | `True` | bool |  | `yigd:GraveBlock`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.persistentGraves.showDeathIrlTime` | `True` | bool |  | `yigd:GraveBlock`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.persistentGraves.useAmPm` | `True` | bool |  | `yigd:GraveBlock`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.dimensionBlacklist` | `[]` | list |  | `yigd:GraveConfig`、`yigd:YigdServerEventHandler` |
| `yigd.json` | `graveConfig.blockUnderGrave.enabled` | `True` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `graveConfig.blockUnderGrave.blockInDimensions` | `[{"key": "minecraft:overworld", "value":` | list |  | `yigd:GraveComponent`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.sellOutOfflinePeople` | `False` | bool |  | `yigd:GraveConfig`、`yigd:ServerEventHandler` |
| `yigd.json` | `graveConfig.maxBackupsPerPerson` | `100` | int |  | `yigd:DeathInfoManager`、`yigd:GraveConfig` |
| `yigd.json` | `graveConfig.dropFromOldestWhenDeleted` | `True` | bool |  | `yigd:DeathInfoManager`、`yigd:GraveConfig` |
| `yigd.json` | `respawnConfig.respawnEffects` | `[]` | list |  | `yigd:DeathInfoManager`、`yigd:EffectComponent`、`yigd:RespawnComponent` |
| `yigd.json` | `respawnConfig.respawnHealth` | `-1` | int |  | `yigd:EffectComponent`、`yigd:RespawnConfig` |
| `yigd.json` | `respawnConfig.resetHunger` | `True` | bool |  | `yigd:EffectComponent`、`yigd:RespawnConfig` |
| `yigd.json` | `respawnConfig.respawnHunger` | `-1` | int |  | `yigd:EffectComponent`、`yigd:RespawnConfig` |
| `yigd.json` | `respawnConfig.resetSaturation` | `True` | bool |  | `yigd:EffectComponent`、`yigd:RespawnConfig` |
| `yigd.json` | `respawnConfig.respawnSaturation` | `-1.0` | float |  | `yigd:EffectComponent`、`yigd:RespawnConfig` |
| `yigd.json` | `respawnConfig.extraItemDrops` | `[]` | list |  | `yigd:RespawnComponent`、`yigd:RespawnConfig` |
| `yigd.json` | `respawnConfig.treatBindingCurse` | `True` | bool |  | `yigd:AccessoriesCompat`、`yigd:CuriosCompat`、`yigd:GraveComponent` |
| `yigd.json` | `compatConfig.enableAccessoriesCompat` | `True` | bool |  | `artifacts:GeneralConfig`、`yigd:CompatConfig`、`yigd:InvModCompat` |
| `yigd.json` | `compatConfig.defaultAccessoriesDropRule` | `PUT_IN_GRAVE` | str |  | `yigd:AccessoriesCompat`、`yigd:CompatConfig` |
| `yigd.json` | `compatConfig.enableTravelersBackpackCompat` | `True` | bool |  | `yigd:CompatConfig`、`yigd:InvModCompat` |
| `yigd.json` | `compatConfig.defaultTravelersBackpackDropRule` | `PUT_IN_GRAVE` | str |  | `yigd:CompatConfig`、`yigd:TravelersBackpackCompat` |
| `yigd.json` | `compatConfig.enableCuriosCompat` | `True` | bool |  | `artifacts:GeneralConfig`、`yigd:CompatConfig`、`yigd:InvModCompat` |
| `yigd.json` | `compatConfig.defaultCuriosDropRule` | `PUT_IN_GRAVE` | str |  | `yigd:CompatConfig`、`yigd:CuriosCompat` |
| `yigd.json` | `compatConfig.enableCosmeticArmorCompat` | `True` | bool |  | `yigd:CompatConfig`、`yigd:InvModCompat` |
| `yigd.json` | `compatConfig.defaultCosmeticArmorDropRule` | `PUT_IN_GRAVE` | str |  | `yigd:CompatConfig`、`yigd:CosmeticArmorCompat` |
| `yigd.json` | `commandConfig.mainCommand` | `yigd` | str |  | `yigd:CommandConfig`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.basePermissionLevel` | `0` | int |  | `yigd:CommandConfig`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.viewLatestPermissionLevel` | `0` | int |  | `yigd:CommandConfig`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.viewSelfPermissionLevel` | `0` | int |  | `yigd:CommandConfig`、`yigd:ServerPacketHandler`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.viewUserPermissionLevel` | `2` | int |  | `yigd:CommandConfig`、`yigd:ServerPacketHandler`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.viewAllPermissionLevel` | `2` | int |  | `yigd:CommandConfig`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.restorePermissionLevel` | `2` | int |  | `yigd:CommandConfig`、`yigd:DeathScrollItem`、`yigd:ServerPacketHandler` |
| `yigd.json` | `commandConfig.robPermissionLevel` | `2` | int |  | `yigd:CommandConfig`、`yigd:DeathScrollItem`、`yigd:ServerPacketHandler` |
| `yigd.json` | `commandConfig.whitelistPermissionLevel` | `3` | int |  | `yigd:CommandConfig`、`yigd:YigdCommands` |
| `yigd.json` | `commandConfig.deletePermissionLevel` | `3` | int |  | `yigd:CommandConfig`、`yigd:DeathScrollItem`、`yigd:ServerPacketHandler` |
| `yigd.json` | `commandConfig.unlockPermissionLevel` | `0` | int |  | `yigd:CommandConfig`、`yigd:DeathScrollItem`、`yigd:ServerPacketHandler` |
| `yigd.json` | `graveRendering.useCustomFeatureRenderer` | `True` | bool |  | `yigd:GraveBlock`、`yigd:GraveBlockEntityRenderer`、`yigd:GraveRenderingConfig` |
| `yigd.json` | `graveRendering.useSkullRenderer` | `True` | bool |  | `yigd:GraveBlockEntityRenderer`、`yigd:GraveRenderingConfig` |
| `yigd.json` | `graveRendering.useTextRenderer` | `True` | bool |  | `yigd:GraveBlockEntityRenderer`、`yigd:GraveRenderingConfig` |
| `yigd.json` | `graveRendering.adaptRenderer` | `False` | bool |  | `yigd:GraveBlockEntityRenderer`、`yigd:GraveRenderingConfig` |
| `yigd.json` | `graveRendering.useGlowingEffect` | `True` | bool |  | `yigd:GraveBlockEntityRenderer`、`yigd:GraveRenderingConfig`、`yigd:ServerEventHandler` |
| `yigd.json` | `graveRendering.glowingDistance` | `15` | int |  | `yigd:GraveRenderingConfig`、`yigd:ServerEventHandler`、`yigd:YigdClientEventHandler` |
| `yigd.json` | `extraFeatures.enableSoulbound` | `True` | bool |  | `yigd:ExtraFeaturesConfig`、`yigd:RegistryDataLoaderMixin` |
| `yigd.json` | `extraFeatures.deathSightEnchant.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `extraFeatures.deathSightEnchant.range` | `64.0` | float |  | `ancient_artifacts:RoyalBeastSkillMixin`、`artifacts:MagnetismMobEffect`、`artifacts:MimicEntity` |
| `yigd.json` | `extraFeatures.deathSightEnchant.targets` | `PLAYER_GRAVES` | str |  | `artifacts:PhantomSweepAttackGoalMixin`、`block_factorys_bosses:KrakenTentacleEntity`、`byepregen:AllOfPredicateMixin` |
| `yigd.json` | `extraFeatures.graveKeys.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `extraFeatures.graveKeys.rebindable` | `True` | bool |  | `yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig`、`yigd:GraveKeyItem` |
| `yigd.json` | `extraFeatures.graveKeys.required` | `True` | bool |  | `architectury:AxeItemHooks`、`byepregen:MixinGateEvaluator`、`byepregen:NbtWriter` |
| `yigd.json` | `extraFeatures.graveKeys.receiveOnRespawn` | `True` | bool |  | `yigd:ExtraFeaturesConfig`、`yigd:RespawnComponent` |
| `yigd.json` | `extraFeatures.graveKeys.obtainableFromGui` | `False` | bool |  | `yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig`、`yigd:ServerPacketHandler` |
| `yigd.json` | `extraFeatures.graveKeys.targeting` | `PLAYER_GRAVE` | str |  | `ancient_artifacts:ShadowStrikerSkillMixin`、`artifacts:MimicEntity`、`block_factorys_bosses:AbstractBossEntity` |
| `yigd.json` | `extraFeatures.deathScroll.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `yigd.json` | `extraFeatures.deathScroll.rebindable` | `False` | bool |  | `yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig`、`yigd:GraveKeyItem` |
| `yigd.json` | `extraFeatures.deathScroll.receiveOnRespawn` | `False` | bool |  | `yigd:ExtraFeaturesConfig`、`yigd:RespawnComponent` |
| `yigd.json` | `extraFeatures.deathScroll.clickFunction` | `VIEW_CONTENTS` | str |  | `yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig` |
| `yigd.json` | `extraFeatures.deathScroll.consumeOnUse` | `False` | bool |  | `artifacts:ItemConfigs`、`yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig` |
| `yigd.json` | `extraFeatures.deathScroll.useTime` | `0` | int |  | `cataclysm:Cursed_bow`、`cataclysm:Wrath_of_the_desert`、`eternal_starlight:CrescentSpearItem` |
| `yigd.json` | `extraFeatures.deathScroll.useCooldown` | `0` | int |  | `yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig` |
| `yigd.json` | `extraFeatures.graveCompass.receiveOnRespawn` | `False` | bool |  | `yigd:ExtraFeaturesConfig`、`yigd:RespawnComponent` |
| `yigd.json` | `extraFeatures.graveCompass.consumeOnUse` | `True` | bool |  | `artifacts:ItemConfigs`、`yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig` |
| `yigd.json` | `extraFeatures.graveCompass.deleteWhenUnlinked` | `True` | bool |  | `yigd:CompassItemMixin`、`yigd:ExtraFeaturesConfig` |
| `yigd.json` | `extraFeatures.graveCompass.cloneRecoveryCompassWithGUI` | `False` | bool |  | `yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig`、`yigd:ServerPacketHandler` |
| `yigd.json` | `extraFeatures.graveCompass.pointToClosest` | `DISABLED` | str |  | `yigd:DeathInfoManager`、`yigd:ExtraFeaturesConfig`、`yigd:GraveCompassHelper` |
