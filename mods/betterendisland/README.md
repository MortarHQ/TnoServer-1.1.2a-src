# YUNG's Better End Island（`betterendisland`）

> jar：`[YUNG的末地岛屿优化]YungsBetterEndIsland.jar` ｜ 版本：`1.21.1-NeoForge-3.1.2` ｜ 作者：YUNGNICKYOUNG, Acarii
> 规模：**40 个类 / 11 个包 / 2697 行** ｜ 事件处理类 0 ｜ Mixin 10 ｜ 注册/管理类 0

**官方描述**：A complete redesign of the End island, making the Ender Dragon fight much more fun and interesting!

**依赖**：`minecraft`、`neoforge`、`yungsapi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.betterendisland.mixin` | 9 | `EndDragonFightMixin`、`TheEndGatewayBlockEntityMixin`、`PrimaryLevelDataMixin` |
| `com.yungnickyoung.minecraft.betterendisland.world` | 6 | `DragonRespawnStage`、`ExtraFightData`、`IPrimaryLevelData` |
| `com.yungnickyoung.minecraft.betterendisland.services` | 5 | `NeoForgePlatformHelper`、`IPlatformHelper`、`IModulesLoader` |
| `com.yungnickyoung.minecraft.betterendisland.module` | 4 | `ConfigModule`、`ConfigModuleNeoForge`、`CommandModule` |
| `com.yungnickyoung.minecraft.betterendisland.world.util` | 4 | `ExitPortalUtils`、`EndCrystalUtils`、`WorldgenUtils` |
| `com.yungnickyoung.minecraft.betterendisland.world.feature` | 4 | `BetterSpikeFeature`、`BetterEndSpawnPlatformFeature`、`BetterEndGatewayFeature` |
| `com.yungnickyoung.minecraft.betterendisland.world.processor` | 3 | `BlockReplaceProcessor`、`ObsidianProcessor`、`DragonEggProcessor` |
| `com.yungnickyoung.minecraft.betterendisland` | 2 | `BetterEndIslandNeoForge`、`BetterEndIslandCommon` |
| `com.yungnickyoung.minecraft.betterendisland.mixin.accessor` | 1 | `EndDragonFightAccessor` |
| `com.yungnickyoung.minecraft.betterendisland.config` | 1 | `BEIConfigNeoForge` |
| `com.yungnickyoung.minecraft.betterendisland.command` | 1 | `EndIslandCommand` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 40）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EndDragonFightMixin` | class | IBetterDragonFight | Mixin | 32 | 534 | [EndDragonFightMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndDragonFightMixin.java) |
| `BetterEndIslandNeoForge` | class | — | Mod | 0 | 18 | [BetterEndIslandNeoForge.java](com/yungnickyoung/minecraft/betterendisland/BetterEndIslandNeoForge.java) |
| `TheEndGatewayBlockEntityMixin` | class | — | Mixin | 2 | 76 | [TheEndGatewayBlockEntityMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/TheEndGatewayBlockEntityMixin.java) |
| `PrimaryLevelDataMixin` | class | IPrimaryLevelData | Mixin | 4 | 58 | [PrimaryLevelDataMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/PrimaryLevelDataMixin.java) |
| `EndergeticExpansionMixins` | class | Level | Mixin | 1 | 70 | [EndergeticExpansionMixins.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndergeticExpansionMixins.java) |
| `ServerLevelMixin` | class | Level | Mixin | 4 | 122 | [ServerLevelMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/ServerLevelMixin.java) |
| `EndSpikeMixin` | class | IEndSpike | Mixin | 4 | 57 | [EndSpikeMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndSpikeMixin.java) |
| `EndPlatformFeatureMixin` | class | — | Mixin | 1 | 23 | [EndPlatformFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndPlatformFeatureMixin.java) |
| `EndGatewayFeatureMixin` | class | — | Mixin | 1 | 22 | [EndGatewayFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndGatewayFeatureMixin.java) |
| `SpikeFeatureMixin` | class | — | Mixin | 2 | 31 | [SpikeFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/SpikeFeatureMixin.java) |
| `EndDragonFightAccessor` | interface | — | Mixin | 0 | 35 | [EndDragonFightAccessor.java](com/yungnickyoung/minecraft/betterendisland/mixin/accessor/EndDragonFightAccessor.java) |
| `BEIConfigNeoForge` | class | — | — | 0 | 40 | [BEIConfigNeoForge.java](com/yungnickyoung/minecraft/betterendisland/config/BEIConfigNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 11 | [ConfigModule.java](com/yungnickyoung/minecraft/betterendisland/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | class | — | — | 4 | 38 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterendisland/module/ConfigModuleNeoForge.java) |
| `EndIslandCommand` | class | — | — | 2 | 45 | [EndIslandCommand.java](com/yungnickyoung/minecraft/betterendisland/command/EndIslandCommand.java) |
| `DragonRespawnStage` | enum | StringRepresentable | — | 11 | 290 | [DragonRespawnStage.java](com/yungnickyoung/minecraft/betterendisland/world/DragonRespawnStage.java) |
| `CommandModule` | class | — | AutoRegister | 0 | 12 | [CommandModule.java](com/yungnickyoung/minecraft/betterendisland/module/CommandModule.java) |
| `ExtraFightData` | class | — | — | 6 | 51 | [ExtraFightData.java](com/yungnickyoung/minecraft/betterendisland/world/ExtraFightData.java) |
| `BlockReplaceProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 168 | [BlockReplaceProcessor.java](com/yungnickyoung/minecraft/betterendisland/world/processor/BlockReplaceProcessor.java) |
| `IPrimaryLevelData` | interface | — | — | 0 | 8 | [IPrimaryLevelData.java](com/yungnickyoung/minecraft/betterendisland/world/IPrimaryLevelData.java) |
| `ExitPortalUtils` | class | — | — | 3 | 87 | [ExitPortalUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/ExitPortalUtils.java) |
| `EndCrystalUtils` | class | — | — | 2 | 58 | [EndCrystalUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/EndCrystalUtils.java) |
| `WorldgenUtils` | class | — | — | 3 | 40 | [WorldgenUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/WorldgenUtils.java) |
| `EndSpikeUtils` | class | — | — | 2 | 142 | [EndSpikeUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/EndSpikeUtils.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterendisland/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterendisland/services/IPlatformHelper.java) |
| `BetterEndIslandCommon` | class | — | — | 1 | 25 | [BetterEndIslandCommon.java](com/yungnickyoung/minecraft/betterendisland/BetterEndIslandCommon.java) |
| `IBetterDragonFight` | interface | — | — | 0 | 28 | [IBetterDragonFight.java](com/yungnickyoung/minecraft/betterendisland/world/IBetterDragonFight.java) |
| `IEndSpike` | interface | — | — | 0 | 8 | [IEndSpike.java](com/yungnickyoung/minecraft/betterendisland/world/IEndSpike.java) |
| `SpikeCacheLoader` | class | CacheLoader | — | 1 | 35 | [SpikeCacheLoader.java](com/yungnickyoung/minecraft/betterendisland/world/SpikeCacheLoader.java) |
| `ObsidianProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 60 | [ObsidianProcessor.java](com/yungnickyoung/minecraft/betterendisland/world/processor/ObsidianProcessor.java) |
| `DragonEggProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [DragonEggProcessor.java](com/yungnickyoung/minecraft/betterendisland/world/processor/DragonEggProcessor.java) |
| `BetterSpikeFeature` | class | — | — | 4 | 139 | [BetterSpikeFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterSpikeFeature.java) |
| `BetterEndSpawnPlatformFeature` | class | — | — | 2 | 54 | [BetterEndSpawnPlatformFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterEndSpawnPlatformFeature.java) |
| `BetterEndGatewayFeature` | class | — | — | 2 | 73 | [BetterEndGatewayFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterEndGatewayFeature.java) |
| `BetterEndPodiumFeature` | class | Feature | — | 3 | 117 | [BetterEndPodiumFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterEndPodiumFeature.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterendisland/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/yungnickyoung/minecraft/betterendisland/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterendisland/services/NeoForgeModulesLoader.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 18 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterendisland/module/StructureProcessorTypeModule.java) |

## Mixin（10 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `EndDragonFightMixin` | `EndDragonFight` | [EndDragonFightMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndDragonFightMixin.java) |
| `TheEndGatewayBlockEntityMixin` | `TheEndGatewayBlockEntity` | [TheEndGatewayBlockEntityMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/TheEndGatewayBlockEntityMixin.java) |
| `PrimaryLevelDataMixin` | `PrimaryLevelData` | [PrimaryLevelDataMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/PrimaryLevelDataMixin.java) |
| `EndergeticExpansionMixins` | `EndergeticExpansions` | [EndergeticExpansionMixins.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndergeticExpansionMixins.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/ServerLevelMixin.java) |
| `EndSpikeMixin` | `EndSpike` | [EndSpikeMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndSpikeMixin.java) |
| `EndPlatformFeatureMixin` | `EndPlatformFeature` | [EndPlatformFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndPlatformFeatureMixin.java) |
| `EndGatewayFeatureMixin` | `EndGatewayFeature` | [EndGatewayFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndGatewayFeatureMixin.java) |
| `SpikeFeatureMixin` | `SpikeFeature` | [SpikeFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/SpikeFeatureMixin.java) |
| `EndDragonFightAccessor` | `EndDragonFightAccessor` | [EndDragonFightAccessor.java](com/yungnickyoung/minecraft/betterendisland/mixin/accessor/EndDragonFightAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BEIConfigNeoForge` | — | [BEIConfigNeoForge.java](com/yungnickyoung/minecraft/betterendisland/config/BEIConfigNeoForge.java) |
| `ConfigModule` | — | [ConfigModule.java](com/yungnickyoung/minecraft/betterendisland/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | — | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterendisland/module/ConfigModuleNeoForge.java) |

## 全部类（40）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EndDragonFightMixin` | class | IBetterDragonFight | Mixin | 32 | 534 | [EndDragonFightMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndDragonFightMixin.java) |
| `BetterEndIslandNeoForge` | class | — | Mod | 0 | 18 | [BetterEndIslandNeoForge.java](com/yungnickyoung/minecraft/betterendisland/BetterEndIslandNeoForge.java) |
| `TheEndGatewayBlockEntityMixin` | class | — | Mixin | 2 | 76 | [TheEndGatewayBlockEntityMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/TheEndGatewayBlockEntityMixin.java) |
| `PrimaryLevelDataMixin` | class | IPrimaryLevelData | Mixin | 4 | 58 | [PrimaryLevelDataMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/PrimaryLevelDataMixin.java) |
| `EndergeticExpansionMixins` | class | Level | Mixin | 1 | 70 | [EndergeticExpansionMixins.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndergeticExpansionMixins.java) |
| `ServerLevelMixin` | class | Level | Mixin | 4 | 122 | [ServerLevelMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/ServerLevelMixin.java) |
| `EndSpikeMixin` | class | IEndSpike | Mixin | 4 | 57 | [EndSpikeMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndSpikeMixin.java) |
| `EndPlatformFeatureMixin` | class | — | Mixin | 1 | 23 | [EndPlatformFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndPlatformFeatureMixin.java) |
| `EndGatewayFeatureMixin` | class | — | Mixin | 1 | 22 | [EndGatewayFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/EndGatewayFeatureMixin.java) |
| `SpikeFeatureMixin` | class | — | Mixin | 2 | 31 | [SpikeFeatureMixin.java](com/yungnickyoung/minecraft/betterendisland/mixin/SpikeFeatureMixin.java) |
| `EndDragonFightAccessor` | interface | — | Mixin | 0 | 35 | [EndDragonFightAccessor.java](com/yungnickyoung/minecraft/betterendisland/mixin/accessor/EndDragonFightAccessor.java) |
| `BEIConfigNeoForge` | class | — | — | 0 | 40 | [BEIConfigNeoForge.java](com/yungnickyoung/minecraft/betterendisland/config/BEIConfigNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 11 | [ConfigModule.java](com/yungnickyoung/minecraft/betterendisland/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | class | — | — | 4 | 38 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterendisland/module/ConfigModuleNeoForge.java) |
| `EndIslandCommand` | class | — | — | 2 | 45 | [EndIslandCommand.java](com/yungnickyoung/minecraft/betterendisland/command/EndIslandCommand.java) |
| `DragonRespawnStage` | enum | StringRepresentable | — | 11 | 290 | [DragonRespawnStage.java](com/yungnickyoung/minecraft/betterendisland/world/DragonRespawnStage.java) |
| `CommandModule` | class | — | AutoRegister | 0 | 12 | [CommandModule.java](com/yungnickyoung/minecraft/betterendisland/module/CommandModule.java) |
| `ExtraFightData` | class | — | — | 6 | 51 | [ExtraFightData.java](com/yungnickyoung/minecraft/betterendisland/world/ExtraFightData.java) |
| `BlockReplaceProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 168 | [BlockReplaceProcessor.java](com/yungnickyoung/minecraft/betterendisland/world/processor/BlockReplaceProcessor.java) |
| `IPrimaryLevelData` | interface | — | — | 0 | 8 | [IPrimaryLevelData.java](com/yungnickyoung/minecraft/betterendisland/world/IPrimaryLevelData.java) |
| `ExitPortalUtils` | class | — | — | 3 | 87 | [ExitPortalUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/ExitPortalUtils.java) |
| `EndCrystalUtils` | class | — | — | 2 | 58 | [EndCrystalUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/EndCrystalUtils.java) |
| `WorldgenUtils` | class | — | — | 3 | 40 | [WorldgenUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/WorldgenUtils.java) |
| `EndSpikeUtils` | class | — | — | 2 | 142 | [EndSpikeUtils.java](com/yungnickyoung/minecraft/betterendisland/world/util/EndSpikeUtils.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterendisland/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterendisland/services/IPlatformHelper.java) |
| `BetterEndIslandCommon` | class | — | — | 1 | 25 | [BetterEndIslandCommon.java](com/yungnickyoung/minecraft/betterendisland/BetterEndIslandCommon.java) |
| `IBetterDragonFight` | interface | — | — | 0 | 28 | [IBetterDragonFight.java](com/yungnickyoung/minecraft/betterendisland/world/IBetterDragonFight.java) |
| `IEndSpike` | interface | — | — | 0 | 8 | [IEndSpike.java](com/yungnickyoung/minecraft/betterendisland/world/IEndSpike.java) |
| `SpikeCacheLoader` | class | CacheLoader | — | 1 | 35 | [SpikeCacheLoader.java](com/yungnickyoung/minecraft/betterendisland/world/SpikeCacheLoader.java) |
| `ObsidianProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 60 | [ObsidianProcessor.java](com/yungnickyoung/minecraft/betterendisland/world/processor/ObsidianProcessor.java) |
| `DragonEggProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [DragonEggProcessor.java](com/yungnickyoung/minecraft/betterendisland/world/processor/DragonEggProcessor.java) |
| `BetterSpikeFeature` | class | — | — | 4 | 139 | [BetterSpikeFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterSpikeFeature.java) |
| `BetterEndSpawnPlatformFeature` | class | — | — | 2 | 54 | [BetterEndSpawnPlatformFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterEndSpawnPlatformFeature.java) |
| `BetterEndGatewayFeature` | class | — | — | 2 | 73 | [BetterEndGatewayFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterEndGatewayFeature.java) |
| `BetterEndPodiumFeature` | class | Feature | — | 3 | 117 | [BetterEndPodiumFeature.java](com/yungnickyoung/minecraft/betterendisland/world/feature/BetterEndPodiumFeature.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterendisland/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/yungnickyoung/minecraft/betterendisland/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterendisland/services/NeoForgeModulesLoader.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 18 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterendisland/module/StructureProcessorTypeModule.java) |
