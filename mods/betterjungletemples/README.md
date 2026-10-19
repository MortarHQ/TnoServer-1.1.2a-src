# YUNG's Better Jungle Temples（`betterjungletemples`）

> jar：`[YUNG的丛林神庙优化]YungsBetterJungleTemples.jar` ｜ 版本：`1.21.1-NeoForge-3.1.2` ｜ 作者：YUNGNICKYOUNG, Tera
> 规模：**30 个类 / 9 个包 / 1102 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 0

**官方描述**：A complete redesign of Minecraft's Jungle Temples!

**依赖**：`minecraft`、`neoforge`、`yungsapi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.betterjungletemples.world.processor` | 8 | `BlockReplaceProcessor`、`ItemFrameProcessor`、`TorchProcessor` |
| `com.yungnickyoung.minecraft.betterjungletemples.services` | 7 | `NeoForgePlatformHelper`、`IPlatformHelper`、`IModulesLoader` |
| `com.yungnickyoung.minecraft.betterjungletemples.module` | 6 | `ConfigModule`、`ConfigModuleNeoForge`、`CompatModule` |
| `com.yungnickyoung.minecraft.betterjungletemples` | 2 | `BetterJungleTemplesNeoForge`、`BetterJungleTemplesCommon` |
| `com.yungnickyoung.minecraft.betterjungletemples.mixin` | 2 | `LocateVanillaJungleTempleCommandMixin`、`DisableVanillaJungleTempleMixin` |
| `com.yungnickyoung.minecraft.betterjungletemples.config` | 2 | `BJTConfigNeoForge`、`ConfigGeneralNeoForge` |
| `com.yungnickyoung.minecraft.betterjungletemples.mixin.accessor` | 1 | `ChunkGeneratorStructureStateAccessor` |
| `com.yungnickyoung.minecraft.betterjungletemples.world.util` | 1 | `ArrowData` |
| `com.yungnickyoung.minecraft.betterjungletemples.world.placement` | 1 | `BetterJungleTemplePlacement` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterJungleTemplesNeoForge` | class | — | Mod | 0 | 18 | [BetterJungleTemplesNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/BetterJungleTemplesNeoForge.java) |
| `LocateVanillaJungleTempleCommandMixin` | class | — | Mixin | 1 | 39 | [LocateVanillaJungleTempleCommandMixin.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/LocateVanillaJungleTempleCommandMixin.java) |
| `DisableVanillaJungleTempleMixin` | class | — | Mixin | 0 | 41 | [DisableVanillaJungleTempleMixin.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/DisableVanillaJungleTempleMixin.java) |
| `ChunkGeneratorStructureStateAccessor` | interface | — | Mixin | 0 | 13 | [ChunkGeneratorStructureStateAccessor.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/accessor/ChunkGeneratorStructureStateAccessor.java) |
| `BJTConfigNeoForge` | class | — | — | 0 | 16 | [BJTConfigNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/config/BJTConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 15 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 14 | [ConfigModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | class | — | — | 4 | 33 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/module/ConfigModuleNeoForge.java) |
| `BlockReplaceProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 168 | [BlockReplaceProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/BlockReplaceProcessor.java) |
| `ItemFrameProcessor` | class | StructureProcessor | — | 3 | 52 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/ItemFrameProcessor.java) |
| `CompatModule` | class | — | AutoRegister | 1 | 11 | [CompatModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/CompatModule.java) |
| `ArrowData` | class | — | — | 4 | 47 | [ArrowData.java](com/yungnickyoung/minecraft/betterjungletemples/world/util/ArrowData.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterjungletemples/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterjungletemples/services/IPlatformHelper.java) |
| `BetterJungleTemplesCommon` | class | — | — | 1 | 19 | [BetterJungleTemplesCommon.java](com/yungnickyoung/minecraft/betterjungletemples/BetterJungleTemplesCommon.java) |
| `BetterJungleTemplePlacement` | class | RandomSpreadStructurePlacement | — | 4 | 101 | [BetterJungleTemplePlacement.java](com/yungnickyoung/minecraft/betterjungletemples/world/placement/BetterJungleTemplePlacement.java) |
| `TorchProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 44 | [TorchProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/TorchProcessor.java) |
| `BlastFurnaceProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 52 | [BlastFurnaceProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/BlastFurnaceProcessor.java) |
| `FireballDispenserProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 62 | [FireballDispenserProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/FireballDispenserProcessor.java) |
| `EmptyDispenserProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 64 | [EmptyDispenserProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/EmptyDispenserProcessor.java) |
| `CaveVineDecorationProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 60 | [CaveVineDecorationProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/CaveVineDecorationProcessor.java) |
| `PillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 88 | [PillarProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/PillarProcessor.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterjungletemples/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterjungletemples/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterjungletemples/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 1 | 13 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterjungletemples/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 9 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterjungletemples/services/IProcessorProvider.java) |
| `TagModule` | class | — | — | 0 | 13 | [TagModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/TagModule.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/StructurePlacementTypeModule.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 33 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/StructureProcessorTypeModule.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LocateVanillaJungleTempleCommandMixin` | `LocateVanillaJungleTempleCommand` | [LocateVanillaJungleTempleCommandMixin.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/LocateVanillaJungleTempleCommandMixin.java) |
| `DisableVanillaJungleTempleMixin` | `DisableVanillaJungleTemple` | [DisableVanillaJungleTempleMixin.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/DisableVanillaJungleTempleMixin.java) |
| `ChunkGeneratorStructureStateAccessor` | `ChunkGeneratorStructureStateAccessor` | [ChunkGeneratorStructureStateAccessor.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/accessor/ChunkGeneratorStructureStateAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BJTConfigNeoForge` | — | [BJTConfigNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/config/BJTConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | — | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | — | [ConfigModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | — | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/module/ConfigModuleNeoForge.java) |

## 全部类（30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BetterJungleTemplesNeoForge` | class | — | Mod | 0 | 18 | [BetterJungleTemplesNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/BetterJungleTemplesNeoForge.java) |
| `LocateVanillaJungleTempleCommandMixin` | class | — | Mixin | 1 | 39 | [LocateVanillaJungleTempleCommandMixin.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/LocateVanillaJungleTempleCommandMixin.java) |
| `DisableVanillaJungleTempleMixin` | class | — | Mixin | 0 | 41 | [DisableVanillaJungleTempleMixin.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/DisableVanillaJungleTempleMixin.java) |
| `ChunkGeneratorStructureStateAccessor` | interface | — | Mixin | 0 | 13 | [ChunkGeneratorStructureStateAccessor.java](com/yungnickyoung/minecraft/betterjungletemples/mixin/accessor/ChunkGeneratorStructureStateAccessor.java) |
| `BJTConfigNeoForge` | class | — | — | 0 | 16 | [BJTConfigNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/config/BJTConfigNeoForge.java) |
| `ConfigGeneralNeoForge` | class | — | — | 0 | 15 | [ConfigGeneralNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/config/ConfigGeneralNeoForge.java) |
| `ConfigModule` | class | — | — | 0 | 14 | [ConfigModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | class | — | — | 4 | 33 | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/betterjungletemples/module/ConfigModuleNeoForge.java) |
| `BlockReplaceProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 168 | [BlockReplaceProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/BlockReplaceProcessor.java) |
| `ItemFrameProcessor` | class | StructureProcessor | — | 3 | 52 | [ItemFrameProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/ItemFrameProcessor.java) |
| `CompatModule` | class | — | AutoRegister | 1 | 11 | [CompatModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/CompatModule.java) |
| `ArrowData` | class | — | — | 4 | 47 | [ArrowData.java](com/yungnickyoung/minecraft/betterjungletemples/world/util/ArrowData.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/betterjungletemples/services/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/betterjungletemples/services/IPlatformHelper.java) |
| `BetterJungleTemplesCommon` | class | — | — | 1 | 19 | [BetterJungleTemplesCommon.java](com/yungnickyoung/minecraft/betterjungletemples/BetterJungleTemplesCommon.java) |
| `BetterJungleTemplePlacement` | class | RandomSpreadStructurePlacement | — | 4 | 101 | [BetterJungleTemplePlacement.java](com/yungnickyoung/minecraft/betterjungletemples/world/placement/BetterJungleTemplePlacement.java) |
| `TorchProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 44 | [TorchProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/TorchProcessor.java) |
| `BlastFurnaceProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 52 | [BlastFurnaceProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/BlastFurnaceProcessor.java) |
| `FireballDispenserProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 62 | [FireballDispenserProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/FireballDispenserProcessor.java) |
| `EmptyDispenserProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 64 | [EmptyDispenserProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/EmptyDispenserProcessor.java) |
| `CaveVineDecorationProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 60 | [CaveVineDecorationProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/CaveVineDecorationProcessor.java) |
| `PillarProcessor` | class | StructureProcessor | MethodsReturnNonnullByDefault、Parameters | 2 | 88 | [PillarProcessor.java](com/yungnickyoung/minecraft/betterjungletemples/world/processor/PillarProcessor.java) |
| `IModulesLoader` | interface | — | — | 0 | 7 | [IModulesLoader.java](com/yungnickyoung/minecraft/betterjungletemples/services/IModulesLoader.java) |
| `Services` | class | — | — | 1 | 17 | [Services.java](com/yungnickyoung/minecraft/betterjungletemples/services/Services.java) |
| `NeoForgeModulesLoader` | class | IModulesLoader | — | 1 | 9 | [NeoForgeModulesLoader.java](com/yungnickyoung/minecraft/betterjungletemples/services/NeoForgeModulesLoader.java) |
| `NeoForgeProcessorProvider` | class | IProcessorProvider | — | 1 | 13 | [NeoForgeProcessorProvider.java](com/yungnickyoung/minecraft/betterjungletemples/services/NeoForgeProcessorProvider.java) |
| `IProcessorProvider` | interface | — | — | 0 | 9 | [IProcessorProvider.java](com/yungnickyoung/minecraft/betterjungletemples/services/IProcessorProvider.java) |
| `TagModule` | class | — | — | 0 | 13 | [TagModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/TagModule.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/StructurePlacementTypeModule.java) |
| `StructureProcessorTypeModule` | class | — | AutoRegister | 0 | 33 | [StructureProcessorTypeModule.java](com/yungnickyoung/minecraft/betterjungletemples/module/StructureProcessorTypeModule.java) |
