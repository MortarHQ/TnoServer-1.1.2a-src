# YUNG's Better Strongholds（`betterstrongholds`）

> jar：`[YUNG 的要塞优化]YungsBetterStrongholds.jar` ｜ 版本：`1.21.1-NeoForge-5.1.3` ｜ 作者：YUNGNICKYOUNG, Acarii
> 规模：**31 个类 / 8 个包 / 1455 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 0

**官方描述**：A complete redesign of Minecraft's strongholds!

**依赖**：`minecraft`、`neoforge`、`yungsapi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.betterstrongholds.world.processor` | 9 | `ItemFrameProcessor`、`RareBlockProcessor`、`BannerProcessor` |
| `com.yungnickyoung.minecraft.betterstrongholds.services` | 7 | `NeoForgePlatformHelper`、`IPlatformHelper`、`IModulesLoader` |
| `com.yungnickyoung.minecraft.betterstrongholds.module` | 4 | `ConfigModuleNeoForge`、`ConfigModule`、`StructurePlacementTypeModule` |
| `com.yungnickyoung.minecraft.betterstrongholds.world` | 4 | `RareBlockChances`、`ItemFrameChances`、`ArmorStandChances` |
| `com.yungnickyoung.minecraft.betterstrongholds` | 2 | `BetterStrongholdsNeoForge`、`BetterStrongholdsCommon` |
| `com.yungnickyoung.minecraft.betterstrongholds.mixin` | 2 | `LocateStrongholdCommandMixin`、`DisableVanillaStrongholdsMixin` |
| `com.yungnickyoung.minecraft.betterstrongholds.config` | 2 | `BSConfigNeoForge`、`ConfigGeneralNeoForge` |
| `com.yungnickyoung.minecraft.betterstrongholds.world.placement` | 1 | `BetterStrongholdsPlacement` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 31）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterStrongholdsNeoForge` | class | — | Mod | 0 | 18 | [BetterStrongholdsNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/BetterStrongholdsNeoForge.java) |
| `LocateStrongholdCommandMixin` | class | — | Mixin | 1 | 32 | [LocateStrongholdCommandMixin.java](com/yungnickyoung/minecraft/betterstrongholds/mixin/LocateStrongholdCommandMixin.java) |
| `ConfigModuleNeoForge` | class | — | — | 13 | 203 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/module/ConfigModuleNeoForge.java) |
| `DisableVanillaStrongholdsMixin` | class | — | Mixin | 0 | 39 | [DisableVanillaStrongholdsMixin.java](com/yungnickyoung/minecraft/betterstrongholds/mixin/DisableVanillaStrongholdsMixin.java) |
| `BSConfigNeoForge` | class | — | — | 0 | 16 | [BSConfigNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/config/BSConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 26 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 11 | [ConfigModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/ConfigModule.java) |
| `RareBlockChances` | class | — | — | 2 | 31 | [RareBlockChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/RareBlockChances.java) |
| `ItemFrameChances` | class | — | — | 3 | 54 | [ItemFrameChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/ItemFrameChances.java) |
| `ItemFrameProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 89 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/ItemFrameProcessor.java) |
| `RareBlockProcessor` | class | StructureProcessor | — | 2 | 41 | [RareBlockProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/RareBlockProcessor.java) |
| `ArmorStandChances` | class | — | — | 9 | 75 | [ArmorStandChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/ArmorStandChances.java) |
| `BetterStrongholdsPlacement` | class | RandomSpreadStructurePlacement | — | 5 | 89 | [BetterStrongholdsPlacement.java](com/yungnickyoung/minecraft/betterstrongholds/world/placement/BetterStrongholdsPlacement.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterstrongholds/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterstrongholds/services/IPlatformHelper.java) |
| `BetterStrongholdsCommon` | class | — | — | 1 | 19 | [BetterStrongholdsCommon.java](com/yungnickyoung/minecraft/betterstrongholds/BetterStrongholdsCommon.java) |
| `OreChances` | class | — | — | 2 | 34 | [OreChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/OreChances.java) |
| `BannerProcessor` | class | StructureProcessor | — | 4 | 89 | [BannerProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/BannerProcessor.java) |
| `RuinProcessor` | class | StructureProcessor | — | 2 | 58 | [RuinProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/RuinProcessor.java) |
| `OreProcessor` | class | StructureProcessor | — | 2 | 41 | [OreProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/OreProcessor.java) |
| `RedstoneProcessor` | class | StructureProcessor | — | 2 | 56 | [RedstoneProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/RedstoneProcessor.java) |
| `EndPortalFrameProcessor` | class | StructureProcessor | — | 2 | 45 | [EndPortalFrameProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/EndPortalFrameProcessor.java) |
| `ArmorStandProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 114 | [ArmorStandProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/ArmorStandProcessor.java) |
| `LegProcessor` | class | StructureProcessor | — | 2 | 132 | [LegProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/LegProcessor.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterstrongholds/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterstrongholds/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterstrongholds/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 2 | 19 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterstrongholds/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 11 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterstrongholds/services/IProcessorProvider.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/StructurePlacementTypeModule.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 36 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/StructureProcessorTypeModule.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LocateStrongholdCommandMixin` | `LocateStrongholdCommand` | [LocateStrongholdCommandMixin.java](com/yungnickyoung/minecraft/betterstrongholds/mixin/LocateStrongholdCommandMixin.java) |
| `DisableVanillaStrongholdsMixin` | `DisableVanillaStrongholds` | [DisableVanillaStrongholdsMixin.java](com/yungnickyoung/minecraft/betterstrongholds/mixin/DisableVanillaStrongholdsMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigModuleNeoForge` | — | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/module/ConfigModuleNeoForge.java) |
| `BSConfigNeoForge` | — | [BSConfigNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/config/BSConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | — | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | — | [ConfigModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/ConfigModule.java) |

## 全部类（31）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterStrongholdsNeoForge` | class | — | Mod | 0 | 18 | [BetterStrongholdsNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/BetterStrongholdsNeoForge.java) |
| `LocateStrongholdCommandMixin` | class | — | Mixin | 1 | 32 | [LocateStrongholdCommandMixin.java](com/yungnickyoung/minecraft/betterstrongholds/mixin/LocateStrongholdCommandMixin.java) |
| `ConfigModuleNeoForge` | class | — | — | 13 | 203 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/module/ConfigModuleNeoForge.java) |
| `DisableVanillaStrongholdsMixin` | class | — | Mixin | 0 | 39 | [DisableVanillaStrongholdsMixin.java](com/yungnickyoung/minecraft/betterstrongholds/mixin/DisableVanillaStrongholdsMixin.java) |
| `BSConfigNeoForge` | class | — | — | 0 | 16 | [BSConfigNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/config/BSConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 26 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterstrongholds/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 11 | [ConfigModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/ConfigModule.java) |
| `RareBlockChances` | class | — | — | 2 | 31 | [RareBlockChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/RareBlockChances.java) |
| `ItemFrameChances` | class | — | — | 3 | 54 | [ItemFrameChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/ItemFrameChances.java) |
| `ItemFrameProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 89 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/ItemFrameProcessor.java) |
| `RareBlockProcessor` | class | StructureProcessor | — | 2 | 41 | [RareBlockProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/RareBlockProcessor.java) |
| `ArmorStandChances` | class | — | — | 9 | 75 | [ArmorStandChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/ArmorStandChances.java) |
| `BetterStrongholdsPlacement` | class | RandomSpreadStructurePlacement | — | 5 | 89 | [BetterStrongholdsPlacement.java](com/yungnickyoung/minecraft/betterstrongholds/world/placement/BetterStrongholdsPlacement.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterstrongholds/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterstrongholds/services/IPlatformHelper.java) |
| `BetterStrongholdsCommon` | class | — | — | 1 | 19 | [BetterStrongholdsCommon.java](com/yungnickyoung/minecraft/betterstrongholds/BetterStrongholdsCommon.java) |
| `OreChances` | class | — | — | 2 | 34 | [OreChances.java](com/yungnickyoung/minecraft/betterstrongholds/world/OreChances.java) |
| `BannerProcessor` | class | StructureProcessor | — | 4 | 89 | [BannerProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/BannerProcessor.java) |
| `RuinProcessor` | class | StructureProcessor | — | 2 | 58 | [RuinProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/RuinProcessor.java) |
| `OreProcessor` | class | StructureProcessor | — | 2 | 41 | [OreProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/OreProcessor.java) |
| `RedstoneProcessor` | class | StructureProcessor | — | 2 | 56 | [RedstoneProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/RedstoneProcessor.java) |
| `EndPortalFrameProcessor` | class | StructureProcessor | — | 2 | 45 | [EndPortalFrameProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/EndPortalFrameProcessor.java) |
| `ArmorStandProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 114 | [ArmorStandProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/ArmorStandProcessor.java) |
| `LegProcessor` | class | StructureProcessor | — | 2 | 132 | [LegProcessor.java](com/yungnickyoung/minecraft/betterstrongholds/world/processor/LegProcessor.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterstrongholds/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterstrongholds/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterstrongholds/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 2 | 19 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterstrongholds/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 11 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterstrongholds/services/IProcessorProvider.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/StructurePlacementTypeModule.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 36 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterstrongholds/module/StructureProcessorTypeModule.java) |
