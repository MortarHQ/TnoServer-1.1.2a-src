# YUNG's Better Desert Temples（`betterdeserttemples`）

> jar：`[YUNG的沙漠神殿优化]YungsBetterDesertTemples.jar` ｜ 版本：`1.21.1-NeoForge-4.1.5` ｜ 作者：YUNGNICKYOUNG, Tera
> 规模：**61 个类 / 13 个包 / 3187 行** ｜ 事件处理类 0 ｜ Mixin 11 ｜ 注册/管理类 0

**官方描述**：A complete redesign of Minecraft's desert temples!

**依赖**：`minecraft`、`neoforge`、`yungsapi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.betterdeserttemples.world.processor` | 26 | `ItemFrameProcessor`、`BoneBlockProcessor`、`RedWoolProcessor` |
| `com.yungnickyoung.minecraft.betterdeserttemples.services` | 7 | `NeoForgePlatformHelper`、`IPlatformHelper`、`IModulesLoader` |
| `com.yungnickyoung.minecraft.betterdeserttemples.module` | 5 | `ConfigModuleNeoForge`、`ConfigModule`、`TagModule` |
| `com.yungnickyoung.minecraft.betterdeserttemples.mixin` | 4 | `LocateVanillaPyramidCommandMixin`、`DisableVanillaPyramidsMixin`、`ServerLevelMixin` |
| `com.yungnickyoung.minecraft.betterdeserttemples.mixin.pharaoh` | 4 | `EntityMixin`、`LivingEntityMixin`、`HuskMixin` |
| `com.yungnickyoung.minecraft.betterdeserttemples.mixin.accessor` | 3 | `StructureProcessorAccessor`、`BoundingBoxAccessor`、`ChunkGeneratorStructureStateAccessor` |
| `com.yungnickyoung.minecraft.betterdeserttemples.world.state` | 3 | `ITempleStateCacheProvider`、`TempleStateRegion`、`TempleStateCache` |
| `com.yungnickyoung.minecraft.betterdeserttemples` | 2 | `BetterDesertTemplesNeoForge`、`BetterDesertTemplesCommon` |
| `com.yungnickyoung.minecraft.betterdeserttemples.config` | 2 | `BDTConfigNeoForge`、`ConfigGeneralNeoForge` |
| `com.yungnickyoung.minecraft.betterdeserttemples.world` | 2 | `ItemFrameChances`、`ArmorStandChances` |
| `com.yungnickyoung.minecraft.betterdeserttemples.util` | 1 | `PharaohUtil` |
| `com.yungnickyoung.minecraft.betterdeserttemples.entity` | 1 | `IPharaohData` |
| `com.yungnickyoung.minecraft.betterdeserttemples.world.placement` | 1 | `BetterDesertTemplePlacement` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterDesertTemplesNeoForge` | class | — | Mod | 0 | 18 | [BetterDesertTemplesNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/BetterDesertTemplesNeoForge.java) |
| `LocateVanillaPyramidCommandMixin` | class | — | Mixin | 1 | 39 | [LocateVanillaPyramidCommandMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/LocateVanillaPyramidCommandMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 27 | [EntityMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 1 | 30 | [LivingEntityMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/LivingEntityMixin.java) |
| `DisableVanillaPyramidsMixin` | class | — | Mixin | 0 | 41 | [DisableVanillaPyramidsMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/DisableVanillaPyramidsMixin.java) |
| `ServerLevelMixin` | class | Level | Mixin | 2 | 75 | [ServerLevelMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/ServerLevelMixin.java) |
| `ServerPlayerTickMixin` | class | Player | Mixin | 2 | 88 | [ServerPlayerTickMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/ServerPlayerTickMixin.java) |
| `StructureProcessorAccessor` | interface | — | Mixin | 0 | 13 | [StructureProcessorAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/StructureProcessorAccessor.java) |
| `BoundingBoxAccessor` | interface | — | Mixin | 0 | 27 | [BoundingBoxAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/BoundingBoxAccessor.java) |
| `ChunkGeneratorStructureStateAccessor` | interface | — | Mixin | 0 | 13 | [ChunkGeneratorStructureStateAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/ChunkGeneratorStructureStateAccessor.java) |
| `HuskMixin` | class | IPharaohData | Mixin | 2 | 26 | [HuskMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/HuskMixin.java) |
| `ZombieMixin` | class | — | Mixin | 2 | 48 | [ZombieMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/ZombieMixin.java) |
| `ConfigModuleNeoForge` | class | — | — | 11 | 151 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/module/ConfigModuleNeoForge.java) |
| `BDTConfigNeoForge` | class | — | — | 0 | 16 | [BDTConfigNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/config/BDTConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 26 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 11 | [ConfigModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/ConfigModule.java) |
| `ItemFrameChances` | class | — | — | 3 | 51 | [ItemFrameChances.java](com/yungnickyoung/minecraft/betterdeserttemples/world/ItemFrameChances.java) |
| `ItemFrameProcessor` | class | StructureProcessor | — | 3 | 91 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/ItemFrameProcessor.java) |
| `BoneBlockProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 49 | [BoneBlockProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/BoneBlockProcessor.java) |
| `ITempleStateCacheProvider` | interface | — | — | 0 | 6 | [ITempleStateCacheProvider.java](com/yungnickyoung/minecraft/betterdeserttemples/world/state/ITempleStateCacheProvider.java) |
| `PharaohUtil` | class | — | — | 5 | 162 | [PharaohUtil.java](com/yungnickyoung/minecraft/betterdeserttemples/util/PharaohUtil.java) |
| `IPharaohData` | interface | — | — | 0 | 10 | [IPharaohData.java](com/yungnickyoung/minecraft/betterdeserttemples/entity/IPharaohData.java) |
| `ArmorStandChances` | class | — | — | 9 | 66 | [ArmorStandChances.java](com/yungnickyoung/minecraft/betterdeserttemples/world/ArmorStandChances.java) |
| `TempleStateRegion` | class | — | — | 6 | 90 | [TempleStateRegion.java](com/yungnickyoung/minecraft/betterdeserttemples/world/state/TempleStateRegion.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterdeserttemples/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterdeserttemples/services/IPlatformHelper.java) |
| `BetterDesertTemplesCommon` | class | — | — | 1 | 20 | [BetterDesertTemplesCommon.java](com/yungnickyoung/minecraft/betterdeserttemples/BetterDesertTemplesCommon.java) |
| `BetterDesertTemplePlacement` | class | RandomSpreadStructurePlacement | — | 2 | 82 | [BetterDesertTemplePlacement.java](com/yungnickyoung/minecraft/betterdeserttemples/world/placement/BetterDesertTemplePlacement.java) |
| `RedWoolProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [RedWoolProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/RedWoolProcessor.java) |
| `OrangeStainedGlassProcessor` | class | StructureProcessor | — | 2 | 62 | [OrangeStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/OrangeStainedGlassProcessor.java) |
| `PharaohProcessor` | class | StructureProcessor | — | 3 | 51 | [PharaohProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PharaohProcessor.java) |
| `PolishedDioriteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 45 | [PolishedDioriteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PolishedDioriteProcessor.java) |
| `TorchProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 41 | [TorchProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/TorchProcessor.java) |
| `YellowConcreteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [YellowConcreteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/YellowConcreteProcessor.java) |
| `PotProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 3 | 66 | [PotProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PotProcessor.java) |
| `PurpurPillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 61 | [PurpurPillarProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PurpurPillarProcessor.java) |
| `LimeBannerProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 4 | 159 | [LimeBannerProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/LimeBannerProcessor.java) |
| `YellowStainedGlassProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 60 | [YellowStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/YellowStainedGlassProcessor.java) |
| `AcaciaWoodProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 84 | [AcaciaWoodProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/AcaciaWoodProcessor.java) |
| `WhiteStainedGlassProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 44 | [WhiteStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/WhiteStainedGlassProcessor.java) |
| `BlueConcreteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [BlueConcreteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/BlueConcreteProcessor.java) |
| `EndStoneBrickWallProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 51 | [EndStoneBrickWallProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/EndStoneBrickWallProcessor.java) |
| `YellowWoolProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 146 | [YellowWoolProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/YellowWoolProcessor.java) |
| `LitCampfireProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 46 | [LitCampfireProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/LitCampfireProcessor.java) |
| `RedStainedGlassProcessor` | class | StructureProcessor | — | 2 | 55 | [RedStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/RedStainedGlassProcessor.java) |
| `QuartzPillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 52 | [QuartzPillarProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/QuartzPillarProcessor.java) |
| `RedBannerProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 4 | 182 | [RedBannerProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/RedBannerProcessor.java) |
| `ArmorStandProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 110 | [ArmorStandProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/ArmorStandProcessor.java) |
| `InfestedCrackedStoneBricksProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 45 | [InfestedCrackedStoneBricksProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/InfestedCrackedStoneBricksProcessor.java) |
| `SpongeProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 3 | 72 | [SpongeProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/SpongeProcessor.java) |
| `DioriteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 55 | [DioriteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/DioriteProcessor.java) |
| `GravelProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 45 | [GravelProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/GravelProcessor.java) |
| `TempleStateCache` | class | — | — | 4 | 46 | [TempleStateCache.java](com/yungnickyoung/minecraft/betterdeserttemples/world/state/TempleStateCache.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterdeserttemples/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterdeserttemples/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterdeserttemples/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 3 | 25 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterdeserttemples/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 13 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterdeserttemples/services/IProcessorProvider.java) |
| `TagModule` | class | — | — | 0 | 13 | [TagModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/TagModule.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/StructurePlacementTypeModule.java) |

## Mixin（11 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LocateVanillaPyramidCommandMixin` | `LocateVanillaPyramidCommand` | [LocateVanillaPyramidCommandMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/LocateVanillaPyramidCommandMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/LivingEntityMixin.java) |
| `DisableVanillaPyramidsMixin` | `DisableVanillaPyramids` | [DisableVanillaPyramidsMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/DisableVanillaPyramidsMixin.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/ServerLevelMixin.java) |
| `ServerPlayerTickMixin` | `ServerPlayerTick` | [ServerPlayerTickMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/ServerPlayerTickMixin.java) |
| `StructureProcessorAccessor` | `StructureProcessorAccessor` | [StructureProcessorAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/StructureProcessorAccessor.java) |
| `BoundingBoxAccessor` | `BoundingBoxAccessor` | [BoundingBoxAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/BoundingBoxAccessor.java) |
| `ChunkGeneratorStructureStateAccessor` | `ChunkGeneratorStructureStateAccessor` | [ChunkGeneratorStructureStateAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/ChunkGeneratorStructureStateAccessor.java) |
| `HuskMixin` | `Husk` | [HuskMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/HuskMixin.java) |
| `ZombieMixin` | `Zombie` | [ZombieMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/ZombieMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigModuleNeoForge` | — | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/module/ConfigModuleNeoForge.java) |
| `BDTConfigNeoForge` | — | [BDTConfigNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/config/BDTConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | — | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | — | [ConfigModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/ConfigModule.java) |

## 全部类（61）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterDesertTemplesNeoForge` | class | — | Mod | 0 | 18 | [BetterDesertTemplesNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/BetterDesertTemplesNeoForge.java) |
| `LocateVanillaPyramidCommandMixin` | class | — | Mixin | 1 | 39 | [LocateVanillaPyramidCommandMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/LocateVanillaPyramidCommandMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 27 | [EntityMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 1 | 30 | [LivingEntityMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/LivingEntityMixin.java) |
| `DisableVanillaPyramidsMixin` | class | — | Mixin | 0 | 41 | [DisableVanillaPyramidsMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/DisableVanillaPyramidsMixin.java) |
| `ServerLevelMixin` | class | Level | Mixin | 2 | 75 | [ServerLevelMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/ServerLevelMixin.java) |
| `ServerPlayerTickMixin` | class | Player | Mixin | 2 | 88 | [ServerPlayerTickMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/ServerPlayerTickMixin.java) |
| `StructureProcessorAccessor` | interface | — | Mixin | 0 | 13 | [StructureProcessorAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/StructureProcessorAccessor.java) |
| `BoundingBoxAccessor` | interface | — | Mixin | 0 | 27 | [BoundingBoxAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/BoundingBoxAccessor.java) |
| `ChunkGeneratorStructureStateAccessor` | interface | — | Mixin | 0 | 13 | [ChunkGeneratorStructureStateAccessor.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/accessor/ChunkGeneratorStructureStateAccessor.java) |
| `HuskMixin` | class | IPharaohData | Mixin | 2 | 26 | [HuskMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/HuskMixin.java) |
| `ZombieMixin` | class | — | Mixin | 2 | 48 | [ZombieMixin.java](com/yungnickyoung/minecraft/betterdeserttemples/mixin/pharaoh/ZombieMixin.java) |
| `ConfigModuleNeoForge` | class | — | — | 11 | 151 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/module/ConfigModuleNeoForge.java) |
| `BDTConfigNeoForge` | class | — | — | 0 | 16 | [BDTConfigNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/config/BDTConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 26 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterdeserttemples/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 11 | [ConfigModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/ConfigModule.java) |
| `ItemFrameChances` | class | — | — | 3 | 51 | [ItemFrameChances.java](com/yungnickyoung/minecraft/betterdeserttemples/world/ItemFrameChances.java) |
| `ItemFrameProcessor` | class | StructureProcessor | — | 3 | 91 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/ItemFrameProcessor.java) |
| `BoneBlockProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 49 | [BoneBlockProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/BoneBlockProcessor.java) |
| `ITempleStateCacheProvider` | interface | — | — | 0 | 6 | [ITempleStateCacheProvider.java](com/yungnickyoung/minecraft/betterdeserttemples/world/state/ITempleStateCacheProvider.java) |
| `PharaohUtil` | class | — | — | 5 | 162 | [PharaohUtil.java](com/yungnickyoung/minecraft/betterdeserttemples/util/PharaohUtil.java) |
| `IPharaohData` | interface | — | — | 0 | 10 | [IPharaohData.java](com/yungnickyoung/minecraft/betterdeserttemples/entity/IPharaohData.java) |
| `ArmorStandChances` | class | — | — | 9 | 66 | [ArmorStandChances.java](com/yungnickyoung/minecraft/betterdeserttemples/world/ArmorStandChances.java) |
| `TempleStateRegion` | class | — | — | 6 | 90 | [TempleStateRegion.java](com/yungnickyoung/minecraft/betterdeserttemples/world/state/TempleStateRegion.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterdeserttemples/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterdeserttemples/services/IPlatformHelper.java) |
| `BetterDesertTemplesCommon` | class | — | — | 1 | 20 | [BetterDesertTemplesCommon.java](com/yungnickyoung/minecraft/betterdeserttemples/BetterDesertTemplesCommon.java) |
| `BetterDesertTemplePlacement` | class | RandomSpreadStructurePlacement | — | 2 | 82 | [BetterDesertTemplePlacement.java](com/yungnickyoung/minecraft/betterdeserttemples/world/placement/BetterDesertTemplePlacement.java) |
| `RedWoolProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [RedWoolProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/RedWoolProcessor.java) |
| `OrangeStainedGlassProcessor` | class | StructureProcessor | — | 2 | 62 | [OrangeStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/OrangeStainedGlassProcessor.java) |
| `PharaohProcessor` | class | StructureProcessor | — | 3 | 51 | [PharaohProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PharaohProcessor.java) |
| `PolishedDioriteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 45 | [PolishedDioriteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PolishedDioriteProcessor.java) |
| `TorchProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 41 | [TorchProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/TorchProcessor.java) |
| `YellowConcreteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [YellowConcreteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/YellowConcreteProcessor.java) |
| `PotProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 3 | 66 | [PotProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PotProcessor.java) |
| `PurpurPillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 61 | [PurpurPillarProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/PurpurPillarProcessor.java) |
| `LimeBannerProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 4 | 159 | [LimeBannerProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/LimeBannerProcessor.java) |
| `YellowStainedGlassProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 60 | [YellowStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/YellowStainedGlassProcessor.java) |
| `AcaciaWoodProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 84 | [AcaciaWoodProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/AcaciaWoodProcessor.java) |
| `WhiteStainedGlassProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 44 | [WhiteStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/WhiteStainedGlassProcessor.java) |
| `BlueConcreteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 40 | [BlueConcreteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/BlueConcreteProcessor.java) |
| `EndStoneBrickWallProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 51 | [EndStoneBrickWallProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/EndStoneBrickWallProcessor.java) |
| `YellowWoolProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 146 | [YellowWoolProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/YellowWoolProcessor.java) |
| `LitCampfireProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 46 | [LitCampfireProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/LitCampfireProcessor.java) |
| `RedStainedGlassProcessor` | class | StructureProcessor | — | 2 | 55 | [RedStainedGlassProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/RedStainedGlassProcessor.java) |
| `QuartzPillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 52 | [QuartzPillarProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/QuartzPillarProcessor.java) |
| `RedBannerProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 4 | 182 | [RedBannerProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/RedBannerProcessor.java) |
| `ArmorStandProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 110 | [ArmorStandProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/ArmorStandProcessor.java) |
| `InfestedCrackedStoneBricksProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 45 | [InfestedCrackedStoneBricksProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/InfestedCrackedStoneBricksProcessor.java) |
| `SpongeProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 3 | 72 | [SpongeProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/SpongeProcessor.java) |
| `DioriteProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 55 | [DioriteProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/DioriteProcessor.java) |
| `GravelProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 45 | [GravelProcessor.java](com/yungnickyoung/minecraft/betterdeserttemples/world/processor/GravelProcessor.java) |
| `TempleStateCache` | class | — | — | 4 | 46 | [TempleStateCache.java](com/yungnickyoung/minecraft/betterdeserttemples/world/state/TempleStateCache.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterdeserttemples/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterdeserttemples/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterdeserttemples/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 3 | 25 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterdeserttemples/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 13 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterdeserttemples/services/IProcessorProvider.java) |
| `TagModule` | class | — | — | 0 | 13 | [TagModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/TagModule.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/StructurePlacementTypeModule.java) |
| `StructureProcessorModule` | class | — | AutoRegister | 0 | 85 | [StructureProcessorModule.java](com/yungnickyoung/minecraft/betterdeserttemples/module/StructureProcessorModule.java) |
