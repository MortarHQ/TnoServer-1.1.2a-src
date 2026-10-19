# The Afterdark（`the_afterdark`）

> jar：`[夜幕降临]theafterdark.jar` ｜ 版本：`1.0.3.1` ｜ 作者：DenisMasterHerobrine, VeiTr
> 规模：**56 个类 / 14 个包 / 6458 行** ｜ 事件处理类 2 ｜ Mixin 4 ｜ 注册/管理类 4

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.denismasterherobrine.afterdark.features` | 30 | `AnvilRockFeature`、`LargePrismarinePillarFeature`、`LargeLapisOrePillarFeature` |
| `dev.denismasterherobrine.afterdark.features.configuration` | 6 | `DoubleBlockConfiguration`、`VerticalBlobConfiguration`、`CatchingFallConfiguration` |
| `dev.denismasterherobrine.afterdark.mixin` | 4 | `MixinFireBlock`、`PlayerEntityNBTMixin`、`DripstoneHelperMixin` |
| `dev.denismasterherobrine.afterdark.neoforge.registry` | 3 | `DataGeneratorRegistry`、`AfterdarkNeoForgeRegistry`、`ModWorldgenProvider` |
| `dev.denismasterherobrine.afterdark.registry` | 2 | `AfterdarkRegistry`、`AfterdarkFeaturesRegistry` |
| `dev.denismasterherobrine.afterdark` | 2 | `Config`、`TheAfterdark` |
| `dev.denismasterherobrine.afterdark.util` | 2 | `PlayerEntityAccess`、`FireCheck` |
| `dev.denismasterherobrine.afterdark.neoforge` | 1 | `TheAfterdarkNeoForge` |
| `dev.denismasterherobrine.afterdark.neoforge.loot.lootTables` | 1 | `NeoForgeLootModifier` |
| `dev.denismasterherobrine.afterdark.blocks` | 1 | `TeleportBlock` |
| `dev.denismasterherobrine.afterdark.blocks.entity` | 1 | `TeleportBlockEntity` |
| `dev.denismasterherobrine.afterdark.loot.lootTables` | 1 | `CommonLootModifier` |
| `dev.denismasterherobrine.afterdark.items` | 1 | `TeleportCatalystItem` |
| `architectury_inject_the_afterdark_common_fed35e040a62461b82bc17425f5d79eb_4d2b02ec4925e11a06fb45134bc415a848487fab19c69cce13bb44f6f245162fthe_afterdark1211common1031devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 56）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DataGeneratorRegistry` | class | — | EventBusSubscriber | 1 | 24 | [DataGeneratorRegistry.java](dev/denismasterherobrine/afterdark/neoforge/registry/DataGeneratorRegistry.java) |
| `TheAfterdarkNeoForge` | class | — | Mod | 1 | 20 | [TheAfterdarkNeoForge.java](dev/denismasterherobrine/afterdark/neoforge/TheAfterdarkNeoForge.java) |
| `MixinFireBlock` | class | — | Mixin | 1 | 22 | [MixinFireBlock.java](dev/denismasterherobrine/afterdark/mixin/MixinFireBlock.java) |
| `PlayerEntityNBTMixin` | class | PlayerEntityAccess | Mixin | 2 | 39 | [PlayerEntityNBTMixin.java](dev/denismasterherobrine/afterdark/mixin/PlayerEntityNBTMixin.java) |
| `NeoForgeLootModifier` | class | — | — | 1 | 17 | [NeoForgeLootModifier.java](dev/denismasterherobrine/afterdark/neoforge/loot/lootTables/NeoForgeLootModifier.java) |
| `DripstoneHelperMixin` | interface | — | Mixin | 0 | 27 | [DripstoneHelperMixin.java](dev/denismasterherobrine/afterdark/mixin/DripstoneHelperMixin.java) |
| `AfterdarkNeoForgeRegistry` | class | — | — | 1 | 164 | [AfterdarkNeoForgeRegistry.java](dev/denismasterherobrine/afterdark/neoforge/registry/AfterdarkNeoForgeRegistry.java) |
| `DoubleBlockConfiguration` | class | FeatureConfiguration | — | 2 | 32 | [DoubleBlockConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/DoubleBlockConfiguration.java) |
| `MixinLavaFluid` | class | — | Mixin | 2 | 29 | [MixinLavaFluid.java](dev/denismasterherobrine/afterdark/mixin/MixinLavaFluid.java) |
| `AfterdarkRegistry` | class | — | — | 1 | 74 | [AfterdarkRegistry.java](dev/denismasterherobrine/afterdark/registry/AfterdarkRegistry.java) |
| `AfterdarkFeaturesRegistry` | class | — | — | 0 | 79 | [AfterdarkFeaturesRegistry.java](dev/denismasterherobrine/afterdark/registry/AfterdarkFeaturesRegistry.java) |
| `TeleportBlock` | class | BaseEntityBlock | — | 10 | 224 | [TeleportBlock.java](dev/denismasterherobrine/afterdark/blocks/TeleportBlock.java) |
| `TeleportBlockEntity` | class | BlockEntity | — | 5 | 46 | [TeleportBlockEntity.java](dev/denismasterherobrine/afterdark/blocks/entity/TeleportBlockEntity.java) |
| `Config` | class | — | — | 2 | 53 | [Config.java](dev/denismasterherobrine/afterdark/Config.java) |
| `VerticalBlobConfiguration` | class | FeatureConfiguration | — | 3 | 51 | [VerticalBlobConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/VerticalBlobConfiguration.java) |
| `CatchingFallConfiguration` | class | FeatureConfiguration | — | 0 | 66 | [CatchingFallConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/CatchingFallConfiguration.java) |
| `SpiralConfiguration` | class | FeatureConfiguration | — | 3 | 61 | [SpiralConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/SpiralConfiguration.java) |
| `WaterloggableRandomPatchConfiguration` | class | FeatureConfiguration | — | 0 | 31 | [WaterloggableRandomPatchConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/WaterloggableRandomPatchConfiguration.java) |
| `AnvilRockConfiguration` | class | FeatureConfiguration | — | 4 | 47 | [AnvilRockConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/AnvilRockConfiguration.java) |
| `AnvilRockFeature` | class | Feature | — | 12 | 317 | [AnvilRockFeature.java](dev/denismasterherobrine/afterdark/features/AnvilRockFeature.java) |
| `CommonLootModifier` | class | — | — | 2 | 43 | [CommonLootModifier.java](dev/denismasterherobrine/afterdark/loot/lootTables/CommonLootModifier.java) |
| `TeleportCatalystItem` | class | Item | — | 1 | 24 | [TeleportCatalystItem.java](dev/denismasterherobrine/afterdark/items/TeleportCatalystItem.java) |
| `LargePrismarinePillarFeature` | class | Feature | — | 6 | 218 | [LargePrismarinePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargePrismarinePillarFeature.java) |
| `LargeLapisOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeLapisOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeLapisOrePillarFeature.java) |
| `LargeDiamondOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeDiamondOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeDiamondOrePillarFeature.java) |
| `LargePackedIcePillarFeature` | class | Feature | — | 6 | 225 | [LargePackedIcePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargePackedIcePillarFeature.java) |
| `LargeSlimePillarFeature` | class | Feature | — | 6 | 218 | [LargeSlimePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeSlimePillarFeature.java) |
| `LargeRedstoneOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeRedstoneOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRedstoneOrePillarFeature.java) |
| `LargeRawGoldPillarFeature` | class | Feature | — | 6 | 216 | [LargeRawGoldPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRawGoldPillarFeature.java) |
| `LargeDarkPrismarinePillarFeature` | class | Feature | — | 6 | 218 | [LargeDarkPrismarinePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeDarkPrismarinePillarFeature.java) |
| `LargeEmeraldOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeEmeraldOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeEmeraldOrePillarFeature.java) |
| `LargeIcePillarFeature` | class | Feature | — | 6 | 218 | [LargeIcePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeIcePillarFeature.java) |
| `LargeObsidianPillarFeature` | class | Feature | — | 6 | 218 | [LargeObsidianPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeObsidianPillarFeature.java) |
| `LargeCoalOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeCoalOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeCoalOrePillarFeature.java) |
| `LargeRawIronPillarFeature` | class | Feature | — | 6 | 216 | [LargeRawIronPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRawIronPillarFeature.java) |
| `LargeRawCopperPillarFeature` | class | Feature | — | 6 | 216 | [LargeRawCopperPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRawCopperPillarFeature.java) |
| `LargeDeepslatePillarFeature` | class | Feature | — | 6 | 218 | [LargeDeepslatePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeDeepslatePillarFeature.java) |
| `PondFeature` | class | Feature | — | 6 | 215 | [PondFeature.java](dev/denismasterherobrine/afterdark/features/PondFeature.java) |
| `LargeBasaltPillarFeature` | class | Feature | — | 6 | 222 | [LargeBasaltPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeBasaltPillarFeature.java) |
| `LargeBlueIcePillarFeature` | class | Feature | — | 6 | 218 | [LargeBlueIcePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeBlueIcePillarFeature.java) |
| `LargeCalcitePillarFeature` | class | Feature | — | 6 | 218 | [LargeCalcitePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeCalcitePillarFeature.java) |
| `PlayerEntityAccess` | interface | — | — | 0 | 8 | [PlayerEntityAccess.java](dev/denismasterherobrine/afterdark/util/PlayerEntityAccess.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_the_afterdark_common_fed35e040a62461b82bc17425f5d79eb_4d2b02ec4925e11a06fb45134bc415a848487fab19c69cce13bb44f6f245162fthe_afterdark1211common1031devjar/PlatformMethods.java) |
| `TheAfterdark` | class | — | — | 1 | 11 | [TheAfterdark.java](dev/denismasterherobrine/afterdark/TheAfterdark.java) |
| `ModWorldgenProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 19 | [ModWorldgenProvider.java](dev/denismasterherobrine/afterdark/neoforge/registry/ModWorldgenProvider.java) |
| `CrystalSpikeFeature` | class | Feature | — | 1 | 89 | [CrystalSpikeFeature.java](dev/denismasterherobrine/afterdark/features/CrystalSpikeFeature.java) |
| `CaveKelpFeature` | class | Feature | — | 1 | 56 | [CaveKelpFeature.java](dev/denismasterherobrine/afterdark/features/CaveKelpFeature.java) |
| `AdditiveGroundBlobFeature` | class | Feature | — | 1 | 53 | [AdditiveGroundBlobFeature.java](dev/denismasterherobrine/afterdark/features/AdditiveGroundBlobFeature.java) |
| `SupportedDiskFeature` | class | Feature | — | 2 | 64 | [SupportedDiskFeature.java](dev/denismasterherobrine/afterdark/features/SupportedDiskFeature.java) |
| `SpiralFeature` | class | Feature | — | 3 | 138 | [SpiralFeature.java](dev/denismasterherobrine/afterdark/features/SpiralFeature.java) |
| `AdditiveBlobFeature` | class | Feature | — | 1 | 53 | [AdditiveBlobFeature.java](dev/denismasterherobrine/afterdark/features/AdditiveBlobFeature.java) |
| `WaterloggableRandomPatchFeature` | class | Feature | — | 2 | 67 | [WaterloggableRandomPatchFeature.java](dev/denismasterherobrine/afterdark/features/WaterloggableRandomPatchFeature.java) |
| `CatchingFallFeature` | class | Feature | — | 1 | 157 | [CatchingFallFeature.java](dev/denismasterherobrine/afterdark/features/CatchingFallFeature.java) |
| `CavePickleFeature` | class | Feature | — | 1 | 33 | [CavePickleFeature.java](dev/denismasterherobrine/afterdark/features/CavePickleFeature.java) |
| `CaveSeagrassFeature` | class | Feature | — | 1 | 49 | [CaveSeagrassFeature.java](dev/denismasterherobrine/afterdark/features/CaveSeagrassFeature.java) |
| `FireCheck` | class | — | — | 1 | 29 | [FireCheck.java](dev/denismasterherobrine/afterdark/util/FireCheck.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `DataGeneratorRegistry` | 1 | `dev.denismasterherobrine.afterdark.neoforge.registry` | [DataGeneratorRegistry.java](dev/denismasterherobrine/afterdark/neoforge/registry/DataGeneratorRegistry.java) |
| `NeoForgeLootModifier` | 1 | `dev.denismasterherobrine.afterdark.neoforge.loot.lootTables` | [NeoForgeLootModifier.java](dev/denismasterherobrine/afterdark/neoforge/loot/lootTables/NeoForgeLootModifier.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinFireBlock` | `FireBlock` | [MixinFireBlock.java](dev/denismasterherobrine/afterdark/mixin/MixinFireBlock.java) |
| `PlayerEntityNBTMixin` | `PlayerEntityNBT` | [PlayerEntityNBTMixin.java](dev/denismasterherobrine/afterdark/mixin/PlayerEntityNBTMixin.java) |
| `DripstoneHelperMixin` | `DripstoneHelper` | [DripstoneHelperMixin.java](dev/denismasterherobrine/afterdark/mixin/DripstoneHelperMixin.java) |
| `MixinLavaFluid` | `LavaFluid` | [MixinLavaFluid.java](dev/denismasterherobrine/afterdark/mixin/MixinLavaFluid.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `DoubleBlockConfiguration` | — | [DoubleBlockConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/DoubleBlockConfiguration.java) |
| `Config` | — | [Config.java](dev/denismasterherobrine/afterdark/Config.java) |
| `VerticalBlobConfiguration` | — | [VerticalBlobConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/VerticalBlobConfiguration.java) |
| `CatchingFallConfiguration` | — | [CatchingFallConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/CatchingFallConfiguration.java) |
| `SpiralConfiguration` | — | [SpiralConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/SpiralConfiguration.java) |
| `WaterloggableRandomPatchConfiguration` | — | [WaterloggableRandomPatchConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/WaterloggableRandomPatchConfiguration.java) |
| `AnvilRockConfiguration` | — | [AnvilRockConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/AnvilRockConfiguration.java) |

## 全部类（56）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DataGeneratorRegistry` | class | — | EventBusSubscriber | 1 | 24 | [DataGeneratorRegistry.java](dev/denismasterherobrine/afterdark/neoforge/registry/DataGeneratorRegistry.java) |
| `TheAfterdarkNeoForge` | class | — | Mod | 1 | 20 | [TheAfterdarkNeoForge.java](dev/denismasterherobrine/afterdark/neoforge/TheAfterdarkNeoForge.java) |
| `MixinFireBlock` | class | — | Mixin | 1 | 22 | [MixinFireBlock.java](dev/denismasterherobrine/afterdark/mixin/MixinFireBlock.java) |
| `PlayerEntityNBTMixin` | class | PlayerEntityAccess | Mixin | 2 | 39 | [PlayerEntityNBTMixin.java](dev/denismasterherobrine/afterdark/mixin/PlayerEntityNBTMixin.java) |
| `NeoForgeLootModifier` | class | — | — | 1 | 17 | [NeoForgeLootModifier.java](dev/denismasterherobrine/afterdark/neoforge/loot/lootTables/NeoForgeLootModifier.java) |
| `DripstoneHelperMixin` | interface | — | Mixin | 0 | 27 | [DripstoneHelperMixin.java](dev/denismasterherobrine/afterdark/mixin/DripstoneHelperMixin.java) |
| `AfterdarkNeoForgeRegistry` | class | — | — | 1 | 164 | [AfterdarkNeoForgeRegistry.java](dev/denismasterherobrine/afterdark/neoforge/registry/AfterdarkNeoForgeRegistry.java) |
| `DoubleBlockConfiguration` | class | FeatureConfiguration | — | 2 | 32 | [DoubleBlockConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/DoubleBlockConfiguration.java) |
| `MixinLavaFluid` | class | — | Mixin | 2 | 29 | [MixinLavaFluid.java](dev/denismasterherobrine/afterdark/mixin/MixinLavaFluid.java) |
| `AfterdarkRegistry` | class | — | — | 1 | 74 | [AfterdarkRegistry.java](dev/denismasterherobrine/afterdark/registry/AfterdarkRegistry.java) |
| `AfterdarkFeaturesRegistry` | class | — | — | 0 | 79 | [AfterdarkFeaturesRegistry.java](dev/denismasterherobrine/afterdark/registry/AfterdarkFeaturesRegistry.java) |
| `TeleportBlock` | class | BaseEntityBlock | — | 10 | 224 | [TeleportBlock.java](dev/denismasterherobrine/afterdark/blocks/TeleportBlock.java) |
| `TeleportBlockEntity` | class | BlockEntity | — | 5 | 46 | [TeleportBlockEntity.java](dev/denismasterherobrine/afterdark/blocks/entity/TeleportBlockEntity.java) |
| `Config` | class | — | — | 2 | 53 | [Config.java](dev/denismasterherobrine/afterdark/Config.java) |
| `VerticalBlobConfiguration` | class | FeatureConfiguration | — | 3 | 51 | [VerticalBlobConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/VerticalBlobConfiguration.java) |
| `CatchingFallConfiguration` | class | FeatureConfiguration | — | 0 | 66 | [CatchingFallConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/CatchingFallConfiguration.java) |
| `SpiralConfiguration` | class | FeatureConfiguration | — | 3 | 61 | [SpiralConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/SpiralConfiguration.java) |
| `WaterloggableRandomPatchConfiguration` | class | FeatureConfiguration | — | 0 | 31 | [WaterloggableRandomPatchConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/WaterloggableRandomPatchConfiguration.java) |
| `AnvilRockConfiguration` | class | FeatureConfiguration | — | 4 | 47 | [AnvilRockConfiguration.java](dev/denismasterherobrine/afterdark/features/configuration/AnvilRockConfiguration.java) |
| `AnvilRockFeature` | class | Feature | — | 12 | 317 | [AnvilRockFeature.java](dev/denismasterherobrine/afterdark/features/AnvilRockFeature.java) |
| `CommonLootModifier` | class | — | — | 2 | 43 | [CommonLootModifier.java](dev/denismasterherobrine/afterdark/loot/lootTables/CommonLootModifier.java) |
| `TeleportCatalystItem` | class | Item | — | 1 | 24 | [TeleportCatalystItem.java](dev/denismasterherobrine/afterdark/items/TeleportCatalystItem.java) |
| `LargePrismarinePillarFeature` | class | Feature | — | 6 | 218 | [LargePrismarinePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargePrismarinePillarFeature.java) |
| `LargeLapisOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeLapisOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeLapisOrePillarFeature.java) |
| `LargeDiamondOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeDiamondOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeDiamondOrePillarFeature.java) |
| `LargePackedIcePillarFeature` | class | Feature | — | 6 | 225 | [LargePackedIcePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargePackedIcePillarFeature.java) |
| `LargeSlimePillarFeature` | class | Feature | — | 6 | 218 | [LargeSlimePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeSlimePillarFeature.java) |
| `LargeRedstoneOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeRedstoneOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRedstoneOrePillarFeature.java) |
| `LargeRawGoldPillarFeature` | class | Feature | — | 6 | 216 | [LargeRawGoldPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRawGoldPillarFeature.java) |
| `LargeDarkPrismarinePillarFeature` | class | Feature | — | 6 | 218 | [LargeDarkPrismarinePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeDarkPrismarinePillarFeature.java) |
| `LargeEmeraldOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeEmeraldOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeEmeraldOrePillarFeature.java) |
| `LargeIcePillarFeature` | class | Feature | — | 6 | 218 | [LargeIcePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeIcePillarFeature.java) |
| `LargeObsidianPillarFeature` | class | Feature | — | 6 | 218 | [LargeObsidianPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeObsidianPillarFeature.java) |
| `LargeCoalOrePillarFeature` | class | Feature | — | 6 | 216 | [LargeCoalOrePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeCoalOrePillarFeature.java) |
| `LargeRawIronPillarFeature` | class | Feature | — | 6 | 216 | [LargeRawIronPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRawIronPillarFeature.java) |
| `LargeRawCopperPillarFeature` | class | Feature | — | 6 | 216 | [LargeRawCopperPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeRawCopperPillarFeature.java) |
| `LargeDeepslatePillarFeature` | class | Feature | — | 6 | 218 | [LargeDeepslatePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeDeepslatePillarFeature.java) |
| `PondFeature` | class | Feature | — | 6 | 215 | [PondFeature.java](dev/denismasterherobrine/afterdark/features/PondFeature.java) |
| `LargeBasaltPillarFeature` | class | Feature | — | 6 | 222 | [LargeBasaltPillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeBasaltPillarFeature.java) |
| `LargeBlueIcePillarFeature` | class | Feature | — | 6 | 218 | [LargeBlueIcePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeBlueIcePillarFeature.java) |
| `LargeCalcitePillarFeature` | class | Feature | — | 6 | 218 | [LargeCalcitePillarFeature.java](dev/denismasterherobrine/afterdark/features/LargeCalcitePillarFeature.java) |
| `PlayerEntityAccess` | interface | — | — | 0 | 8 | [PlayerEntityAccess.java](dev/denismasterherobrine/afterdark/util/PlayerEntityAccess.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_the_afterdark_common_fed35e040a62461b82bc17425f5d79eb_4d2b02ec4925e11a06fb45134bc415a848487fab19c69cce13bb44f6f245162fthe_afterdark1211common1031devjar/PlatformMethods.java) |
| `TheAfterdark` | class | — | — | 1 | 11 | [TheAfterdark.java](dev/denismasterherobrine/afterdark/TheAfterdark.java) |
| `ModWorldgenProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 19 | [ModWorldgenProvider.java](dev/denismasterherobrine/afterdark/neoforge/registry/ModWorldgenProvider.java) |
| `CrystalSpikeFeature` | class | Feature | — | 1 | 89 | [CrystalSpikeFeature.java](dev/denismasterherobrine/afterdark/features/CrystalSpikeFeature.java) |
| `CaveKelpFeature` | class | Feature | — | 1 | 56 | [CaveKelpFeature.java](dev/denismasterherobrine/afterdark/features/CaveKelpFeature.java) |
| `AdditiveGroundBlobFeature` | class | Feature | — | 1 | 53 | [AdditiveGroundBlobFeature.java](dev/denismasterherobrine/afterdark/features/AdditiveGroundBlobFeature.java) |
| `SupportedDiskFeature` | class | Feature | — | 2 | 64 | [SupportedDiskFeature.java](dev/denismasterherobrine/afterdark/features/SupportedDiskFeature.java) |
| `SpiralFeature` | class | Feature | — | 3 | 138 | [SpiralFeature.java](dev/denismasterherobrine/afterdark/features/SpiralFeature.java) |
| `AdditiveBlobFeature` | class | Feature | — | 1 | 53 | [AdditiveBlobFeature.java](dev/denismasterherobrine/afterdark/features/AdditiveBlobFeature.java) |
| `WaterloggableRandomPatchFeature` | class | Feature | — | 2 | 67 | [WaterloggableRandomPatchFeature.java](dev/denismasterherobrine/afterdark/features/WaterloggableRandomPatchFeature.java) |
| `CatchingFallFeature` | class | Feature | — | 1 | 157 | [CatchingFallFeature.java](dev/denismasterherobrine/afterdark/features/CatchingFallFeature.java) |
| `CavePickleFeature` | class | Feature | — | 1 | 33 | [CavePickleFeature.java](dev/denismasterherobrine/afterdark/features/CavePickleFeature.java) |
| `CaveSeagrassFeature` | class | Feature | — | 1 | 49 | [CaveSeagrassFeature.java](dev/denismasterherobrine/afterdark/features/CaveSeagrassFeature.java) |
| `FireCheck` | class | — | — | 1 | 29 | [FireCheck.java](dev/denismasterherobrine/afterdark/util/FireCheck.java) |
