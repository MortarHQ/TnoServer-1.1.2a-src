# YUNG's Better Nether Fortresses（`betterfortresses`）

> jar：`[YUNG 的下界要塞优化]YungsBetterNetherFortresses.jar` ｜ 版本：`1.21.1-NeoForge-3.1.5` ｜ 作者：YUNGNICKYOUNG, Acarii
> 规模：**25 个类 / 7 个包 / 1183 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 0

**官方描述**：A complete redesign of Minecraft's Nether Fortresses!

**依赖**：`minecraft`、`neoforge`、`yungsapi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.betterfortresses.services` | 7 | `NeoForgePlatformHelper`、`IPlatformHelper`、`IModulesLoader` |
| `com.yungnickyoung.minecraft.betterfortresses.world.processor` | 6 | `LiquidBlockProcessor`、`BridgeArchProcessor`、`RedSandstoneStairsProcessor` |
| `com.yungnickyoung.minecraft.betterfortresses.mixin` | 3 | `LocateVanillaFortressCommandMixin`、`DisableVanillaFortressesMixin`、`FixMobSpawningMixin` |
| `com.yungnickyoung.minecraft.betterfortresses.module` | 3 | `ConfigModuleNeoForge`、`ConfigModule`、`StructureProcessorTypeModule` |
| `com.yungnickyoung.minecraft.betterfortresses` | 2 | `BetterFortressesNeoForge`、`BetterFortressesCommon` |
| `com.yungnickyoung.minecraft.betterfortresses.config` | 2 | `BNFConfigNeoForge`、`ConfigGeneralNeoForge` |
| `com.yungnickyoung.minecraft.betterfortresses.world` | 2 | `ItemFrameChances`、`ItemFrameProcessor` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 25）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterFortressesNeoForge` | class | — | Mod | 0 | 18 | [BetterFortressesNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/BetterFortressesNeoForge.java) |
| `LocateVanillaFortressCommandMixin` | class | — | Mixin | 1 | 39 | [LocateVanillaFortressCommandMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/LocateVanillaFortressCommandMixin.java) |
| `DisableVanillaFortressesMixin` | class | — | Mixin | 0 | 42 | [DisableVanillaFortressesMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/DisableVanillaFortressesMixin.java) |
| `FixMobSpawningMixin` | class | — | Mixin | 1 | 36 | [FixMobSpawningMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/FixMobSpawningMixin.java) |
| `ConfigModuleNeoForge` | class | — | — | 9 | 120 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/module/ConfigModuleNeoForge.java) |
| `BNFConfigNeoForge` | class | — | — | 0 | 16 | [BNFConfigNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/config/BNFConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 20 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 10 | [ConfigModule.java](com/yungnickyoung/minecraft/betterfortresses/module/ConfigModule.java) |
| `ItemFrameChances` | class | — | — | 6 | 68 | [ItemFrameChances.java](com/yungnickyoung/minecraft/betterfortresses/world/ItemFrameChances.java) |
| `ItemFrameProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 158 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/ItemFrameProcessor.java) |
| `LiquidBlockProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 63 | [LiquidBlockProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/LiquidBlockProcessor.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterfortresses/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterfortresses/services/IPlatformHelper.java) |
| `BetterFortressesCommon` | class | — | — | 2 | 24 | [BetterFortressesCommon.java](com/yungnickyoung/minecraft/betterfortresses/BetterFortressesCommon.java) |
| `BridgeArchProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 4 | 166 | [BridgeArchProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/BridgeArchProcessor.java) |
| `RedSandstoneStairsProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 69 | [RedSandstoneStairsProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/RedSandstoneStairsProcessor.java) |
| `PillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 88 | [PillarProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/PillarProcessor.java) |
| `StairPillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 84 | [StairPillarProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/StairPillarProcessor.java) |
| `NetherWartProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 44 | [NetherWartProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/NetherWartProcessor.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterfortresses/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterfortresses/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterfortresses/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 1 | 13 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterfortresses/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 9 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterfortresses/services/IProcessorProvider.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 31 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterfortresses/module/StructureProcessorTypeModule.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LocateVanillaFortressCommandMixin` | `LocateVanillaFortressCommand` | [LocateVanillaFortressCommandMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/LocateVanillaFortressCommandMixin.java) |
| `DisableVanillaFortressesMixin` | `DisableVanillaFortresses` | [DisableVanillaFortressesMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/DisableVanillaFortressesMixin.java) |
| `FixMobSpawningMixin` | `FixMobSpawning` | [FixMobSpawningMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/FixMobSpawningMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigModuleNeoForge` | — | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/module/ConfigModuleNeoForge.java) |
| `BNFConfigNeoForge` | — | [BNFConfigNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/config/BNFConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | — | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | — | [ConfigModule.java](com/yungnickyoung/minecraft/betterfortresses/module/ConfigModule.java) |

## 全部类（25）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterFortressesNeoForge` | class | — | Mod | 0 | 18 | [BetterFortressesNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/BetterFortressesNeoForge.java) |
| `LocateVanillaFortressCommandMixin` | class | — | Mixin | 1 | 39 | [LocateVanillaFortressCommandMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/LocateVanillaFortressCommandMixin.java) |
| `DisableVanillaFortressesMixin` | class | — | Mixin | 0 | 42 | [DisableVanillaFortressesMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/DisableVanillaFortressesMixin.java) |
| `FixMobSpawningMixin` | class | — | Mixin | 1 | 36 | [FixMobSpawningMixin.java](com/yungnickyoung/minecraft/betterfortresses/mixin/FixMobSpawningMixin.java) |
| `ConfigModuleNeoForge` | class | — | — | 9 | 120 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/module/ConfigModuleNeoForge.java) |
| `BNFConfigNeoForge` | class | — | — | 0 | 16 | [BNFConfigNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/config/BNFConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 20 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterfortresses/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 10 | [ConfigModule.java](com/yungnickyoung/minecraft/betterfortresses/module/ConfigModule.java) |
| `ItemFrameChances` | class | — | — | 6 | 68 | [ItemFrameChances.java](com/yungnickyoung/minecraft/betterfortresses/world/ItemFrameChances.java) |
| `ItemFrameProcessor` | class | StructureProcessor | ParametersAreNonnullByDefault | 3 | 158 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/ItemFrameProcessor.java) |
| `LiquidBlockProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 63 | [LiquidBlockProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/LiquidBlockProcessor.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterfortresses/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterfortresses/services/IPlatformHelper.java) |
| `BetterFortressesCommon` | class | — | — | 2 | 24 | [BetterFortressesCommon.java](com/yungnickyoung/minecraft/betterfortresses/BetterFortressesCommon.java) |
| `BridgeArchProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 4 | 166 | [BridgeArchProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/BridgeArchProcessor.java) |
| `RedSandstoneStairsProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 69 | [RedSandstoneStairsProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/RedSandstoneStairsProcessor.java) |
| `PillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 88 | [PillarProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/PillarProcessor.java) |
| `StairPillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 84 | [StairPillarProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/StairPillarProcessor.java) |
| `NetherWartProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 44 | [NetherWartProcessor.java](com/yungnickyoung/minecraft/betterfortresses/world/processor/NetherWartProcessor.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterfortresses/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterfortresses/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterfortresses/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 1 | 13 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterfortresses/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 9 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterfortresses/services/IProcessorProvider.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 31 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterfortresses/module/StructureProcessorTypeModule.java) |
