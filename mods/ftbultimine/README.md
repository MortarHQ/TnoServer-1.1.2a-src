# FTB Ultimine（`ftbultimine`）

> jar：`[连锁]ftb-ultimine.jar` ｜ 版本：`2101.1.15` ｜ 作者：FTB Team
> 规模：**76 个类 / 30 个包 / 3580 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 18

**依赖**：`neoforge`、`minecraft`、`architectury`、`ftblibrary`、`ftbezcrystals`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftbultimine.shape` | 10 | `ShapeRegistry`、`BlockMatchers`、`BlockMatcher` |
| `dev.ftb.mods.ftbultimine` | 8 | `RestrictionHandlerRegistry`、`BlockSelectionRegistry`、`BlockBreakingRegistry` |
| `dev.ftb.mods.ftbultimine.client` | 6 | `FTBUltimineClient`、`ClientPlatformUtil`、`ShapeScreen` |
| `dev.ftb.mods.ftbultimine.net` | 5 | `ModeChangedPacket`、`SyncUltimineTimePacket`、`KeyPressedPacket` |
| `dev.ftb.mods.ftbultimine.rightclick` | 5 | `AxeStripping`、`RightClickDispatcher`、`FarmlandConversion` |
| `dev.ftb.mods.ftbultimine.utils` | 4 | `ShapeMerger`、`XPUtils`、`EntityDistanceComparator` |
| `dev.ftb.mods.ftbultimine.api.shape` | 3 | `RegisterShapeEvent`、`ShapeContext`、`Shape` |
| `dev.ftb.mods.ftbultimine.neoforge` | 2 | `FTBUltimineNeoForgeClient`、`FTBUltimineNeoForge` |
| `dev.ftb.mods.ftbultimine.api.blockbreaking` | 2 | `RegisterBlockBreakHandlerEvent`、`BlockBreakHandler` |
| `dev.ftb.mods.ftbultimine.api.blockselection` | 2 | `RegisterBlockSelectionHandlerEvent`、`BlockSelectionHandler` |
| `dev.ftb.mods.ftbultimine.api.rightclick` | 2 | `RegisterRightClickHandlerEvent`、`RightClickHandler` |
| `dev.ftb.mods.ftbultimine.api.restriction` | 2 | `RegisterRestrictionHandlerEvent`、`RestrictionHandler` |
| `dev.ftb.mods.ftbultimine.crops` | 2 | `CropLikeRegistry`、`VanillaCropLikeHandler` |
| `dev.ftb.mods.ftbultimine.config` | 2 | `FTBUltimineClientConfig`、`FTBUltimineServerConfig` |
| `dev.ftb.mods.ftbultimine.integration.agricraft` | 2 | `AgriCraftCropLikeHandler`、`AgricraftSetup` |
| `dev.ftb.mods.ftbultimine.integration.ezcrystals.neoforge` | 2 | `EZCrystalsHandler`、`EZCrystalsImpl` |
| `dev.ftb.mods.ftbultimine.api.crop` | 2 | `CropLikeHandler`、`RegisterCropLikeEvent` |
| `dev.ftb.mods.ftbultimine.api.util` | 2 | `ItemCollector`、`CanUltimineResult` |
| `dev.ftb.mods.ftbultimine.integration.acceldecay` | 2 | `LogBreakTracker`、`AcceleratedDecay` |
| `dev.ftb.mods.ftbultimine.mixin` | 1 | `BlockMixin` |
| `dev.ftb.mods.ftbultimine.integration` | 1 | `IntegrationHandler` |
| `dev.ftb.mods.ftbultimine.event` | 1 | `LevelRenderLastEvent` |
| `dev.ftb.mods.ftbultimine.integration.ranks` | 1 | `FTBRanksIntegration` |
| `dev.ftb.mods.ftbultimine.client.neoforge` | 1 | `ClientPlatformUtilImpl` |
| `dev.ftb.mods.ftbultimine.utils.neoforge` | 1 | `PlatformUtilImpl` |
| `architectury_inject_FTBUltimine121_common_6cccbc998cb64d97a350f3347bd335fa_765eda5a25eca4ba0731519c9df059f6c394719705c84e5a9479ca4737a03d6bftbultimine2101115devjar` | 1 | `PlatformMethods` |
| `dev.ftb.mods.ftbultimine.neoforge.plugin.losttrinkets` | 1 | `LostTrinketsFTBUltiminePlugin` |
| `dev.ftb.mods.ftbultimine.integration.ezcrystals` | 1 | `EZCrystals` |
| `dev.ftb.mods.ftbultimine.api` | 1 | `FTBUltimineAPI` |
| `dev.ftb.mods.ftbultimine.registry` | 1 | `ModAttributes` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FTBUltimineNeoForgeClient` | class | — | Mod | 0 | 21 | [FTBUltimineNeoForgeClient.java](dev/ftb/mods/ftbultimine/neoforge/FTBUltimineNeoForgeClient.java) |
| `RestrictionHandlerRegistry` | enum | RegisterRestrictionHandlerEvent.Regi | — | 2 | 30 | [RestrictionHandlerRegistry.java](dev/ftb/mods/ftbultimine/RestrictionHandlerRegistry.java) |
| `FTBUltimineNeoForge` | class | — | Mod | 1 | 25 | [FTBUltimineNeoForge.java](dev/ftb/mods/ftbultimine/neoforge/FTBUltimineNeoForge.java) |
| `RegisterBlockBreakHandlerEvent` | interface | — | — | 0 | 17 | [RegisterBlockBreakHandlerEvent.java](dev/ftb/mods/ftbultimine/api/blockbreaking/RegisterBlockBreakHandlerEvent.java) |
| `RegisterBlockSelectionHandlerEvent` | interface | — | — | 0 | 16 | [RegisterBlockSelectionHandlerEvent.java](dev/ftb/mods/ftbultimine/api/blockselection/RegisterBlockSelectionHandlerEvent.java) |
| `BlockSelectionRegistry` | enum | RegisterBlockSelectionHandlerEvent.D | — | 4 | 31 | [BlockSelectionRegistry.java](dev/ftb/mods/ftbultimine/BlockSelectionRegistry.java) |
| `BlockBreakingRegistry` | enum | RegisterBlockBreakHandlerEvent.Regis | — | 4 | 31 | [BlockBreakingRegistry.java](dev/ftb/mods/ftbultimine/BlockBreakingRegistry.java) |
| `BlockMixin` | class | — | Mixin | 1 | 27 | [BlockMixin.java](dev/ftb/mods/ftbultimine/mixin/BlockMixin.java) |
| `RegisterRightClickHandlerEvent` | interface | — | — | 0 | 17 | [RegisterRightClickHandlerEvent.java](dev/ftb/mods/ftbultimine/api/rightclick/RegisterRightClickHandlerEvent.java) |
| `RegisterRestrictionHandlerEvent` | interface | — | — | 0 | 15 | [RegisterRestrictionHandlerEvent.java](dev/ftb/mods/ftbultimine/api/restriction/RegisterRestrictionHandlerEvent.java) |
| `IntegrationHandler` | class | — | — | 1 | 32 | [IntegrationHandler.java](dev/ftb/mods/ftbultimine/integration/IntegrationHandler.java) |
| `BlockBreakHandler` | interface | — | — | 0 | 22 | [BlockBreakHandler.java](dev/ftb/mods/ftbultimine/api/blockbreaking/BlockBreakHandler.java) |
| `BlockSelectionHandler` | interface | — | — | 2 | 29 | [BlockSelectionHandler.java](dev/ftb/mods/ftbultimine/api/blockselection/BlockSelectionHandler.java) |
| `CropLikeRegistry` | enum | RegisterCropLikeEvent.Dispatcher | — | 6 | 62 | [CropLikeRegistry.java](dev/ftb/mods/ftbultimine/crops/CropLikeRegistry.java) |
| `FTBUltimine` | class | ResourceManagerReloadListener | — | 20 | 413 | [FTBUltimine.java](dev/ftb/mods/ftbultimine/FTBUltimine.java) |
| `ShapeRegistry` | enum | RegisterShapeEvent.Registry | — | 3 | 43 | [ShapeRegistry.java](dev/ftb/mods/ftbultimine/shape/ShapeRegistry.java) |
| `FTBUltimineClientConfig` | interface | — | — | 0 | 54 | [FTBUltimineClientConfig.java](dev/ftb/mods/ftbultimine/config/FTBUltimineClientConfig.java) |
| `VanillaCropLikeHandler` | enum | CropLikeHandler | — | 8 | 87 | [VanillaCropLikeHandler.java](dev/ftb/mods/ftbultimine/crops/VanillaCropLikeHandler.java) |
| `AgriCraftCropLikeHandler` | enum | CropLikeHandler | — | 3 | 31 | [AgriCraftCropLikeHandler.java](dev/ftb/mods/ftbultimine/integration/agricraft/AgriCraftCropLikeHandler.java) |
| `EZCrystalsHandler` | enum | RightClickHandler | — | 1 | 33 | [EZCrystalsHandler.java](dev/ftb/mods/ftbultimine/integration/ezcrystals/neoforge/EZCrystalsHandler.java) |
| `FTBUltimineServerConfig` | interface | — | — | 4 | 223 | [FTBUltimineServerConfig.java](dev/ftb/mods/ftbultimine/config/FTBUltimineServerConfig.java) |
| `RightClickHandler` | interface | — | — | 0 | 26 | [RightClickHandler.java](dev/ftb/mods/ftbultimine/api/rightclick/RightClickHandler.java) |
| `CropLikeHandler` | interface | — | — | 0 | 16 | [CropLikeHandler.java](dev/ftb/mods/ftbultimine/api/crop/CropLikeHandler.java) |
| `RestrictionHandler` | interface | — | — | 0 | 14 | [RestrictionHandler.java](dev/ftb/mods/ftbultimine/api/restriction/RestrictionHandler.java) |
| `FTBUltimineClient` | class | — | — | 13 | 359 | [FTBUltimineClient.java](dev/ftb/mods/ftbultimine/client/FTBUltimineClient.java) |
| `FTBUltimineCommands` | class | — | — | 1 | 49 | [FTBUltimineCommands.java](dev/ftb/mods/ftbultimine/FTBUltimineCommands.java) |
| `FTBUltiminePlayerData` | class | — | — | 14 | 147 | [FTBUltiminePlayerData.java](dev/ftb/mods/ftbultimine/FTBUltiminePlayerData.java) |
| `LevelRenderLastEvent` | interface | — | — | 0 | 12 | [LevelRenderLastEvent.java](dev/ftb/mods/ftbultimine/event/LevelRenderLastEvent.java) |
| `FTBRanksIntegration` | class | — | — | 7 | 66 | [FTBRanksIntegration.java](dev/ftb/mods/ftbultimine/integration/ranks/FTBRanksIntegration.java) |
| `ModeChangedPacket` | record | CustomPacketPayload | — | 2 | 27 | [ModeChangedPacket.java](dev/ftb/mods/ftbultimine/net/ModeChangedPacket.java) |
| `SyncUltimineTimePacket` | record | CustomPacketPayload | — | 2 | 45 | [SyncUltimineTimePacket.java](dev/ftb/mods/ftbultimine/net/SyncUltimineTimePacket.java) |
| `KeyPressedPacket` | record | CustomPacketPayload | — | 2 | 27 | [KeyPressedPacket.java](dev/ftb/mods/ftbultimine/net/KeyPressedPacket.java) |
| `SendShapePacket` | record | CustomPacketPayload | — | 4 | 41 | [SendShapePacket.java](dev/ftb/mods/ftbultimine/net/SendShapePacket.java) |
| `RegisterShapeEvent` | interface | — | — | 0 | 16 | [RegisterShapeEvent.java](dev/ftb/mods/ftbultimine/api/shape/RegisterShapeEvent.java) |
| `RegisterCropLikeEvent` | interface | — | — | 0 | 16 | [RegisterCropLikeEvent.java](dev/ftb/mods/ftbultimine/api/crop/RegisterCropLikeEvent.java) |
| `ClientPlatformUtil` | class | — | — | 2 | 20 | [ClientPlatformUtil.java](dev/ftb/mods/ftbultimine/client/ClientPlatformUtil.java) |
| `ClientPlatformUtilImpl` | class | — | — | 1 | 20 | [ClientPlatformUtilImpl.java](dev/ftb/mods/ftbultimine/client/neoforge/ClientPlatformUtilImpl.java) |
| `ShapeMerger` | class | — | — | 17 | 177 | [ShapeMerger.java](dev/ftb/mods/ftbultimine/utils/ShapeMerger.java) |
| `BlockMatchers` | interface | — | — | 0 | 32 | [BlockMatchers.java](dev/ftb/mods/ftbultimine/shape/BlockMatchers.java) |
| `BlockMatcher` | record | ShapeContext.Matcher | — | 4 | 36 | [BlockMatcher.java](dev/ftb/mods/ftbultimine/shape/BlockMatcher.java) |
| `ItemCollector` | class | — | — | 3 | 71 | [ItemCollector.java](dev/ftb/mods/ftbultimine/api/util/ItemCollector.java) |
| `ShapeScreen` | class | — | — | 0 | 5 | [ShapeScreen.java](dev/ftb/mods/ftbultimine/client/ShapeScreen.java) |
| `XPUtils` | class | — | — | 6 | 59 | [XPUtils.java](dev/ftb/mods/ftbultimine/utils/XPUtils.java) |
| `EntityDistanceComparator` | class | Comparator<BlockPos> | — | 2 | 28 | [EntityDistanceComparator.java](dev/ftb/mods/ftbultimine/utils/EntityDistanceComparator.java) |
| `CooldownTracker` | class | — | — | 6 | 60 | [CooldownTracker.java](dev/ftb/mods/ftbultimine/CooldownTracker.java) |
| `ShapelessShape` | class | Shape | — | 5 | 89 | [ShapelessShape.java](dev/ftb/mods/ftbultimine/shape/ShapelessShape.java) |
| `PlatformUtil` | class | — | — | 4 | 36 | [PlatformUtil.java](dev/ftb/mods/ftbultimine/utils/PlatformUtil.java) |
| `PlatformUtilImpl` | class | — | — | 4 | 26 | [PlatformUtilImpl.java](dev/ftb/mods/ftbultimine/utils/neoforge/PlatformUtilImpl.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_FTBUltimine121_common_6cccbc998cb64d97a350f3347bd335fa_765eda5a25eca4ba0731519c9df059f6c394719705c84e5a9479ca4737a03d6bftbultimine2101115devjar/PlatformMethods.java) |
| `FTBUltimineAPIImpl` | enum | FTBUltimineAPI.API | — | 2 | 36 | [FTBUltimineAPIImpl.java](dev/ftb/mods/ftbultimine/FTBUltimineAPIImpl.java) |
| `AxeStripping` | enum | RightClickHandler | — | 1 | 38 | [AxeStripping.java](dev/ftb/mods/ftbultimine/rightclick/AxeStripping.java) |
| `RightClickDispatcher` | enum | RegisterRightClickHandlerEvent.Dispa | — | 4 | 40 | [RightClickDispatcher.java](dev/ftb/mods/ftbultimine/rightclick/RightClickDispatcher.java) |
| `FarmlandConversion` | enum | RightClickHandler | — | 1 | 38 | [FarmlandConversion.java](dev/ftb/mods/ftbultimine/rightclick/FarmlandConversion.java) |
| `CropHarvesting` | enum | RightClickHandler | — | 1 | 41 | [CropHarvesting.java](dev/ftb/mods/ftbultimine/rightclick/CropHarvesting.java) |
| `ShovelFlattening` | enum | RightClickHandler | — | 1 | 38 | [ShovelFlattening.java](dev/ftb/mods/ftbultimine/rightclick/ShovelFlattening.java) |
| `MiningTunnelShape` | class | DiagonalTunnelShape | — | 2 | 19 | [MiningTunnelShape.java](dev/ftb/mods/ftbultimine/shape/MiningTunnelShape.java) |
| `EscapeTunnelShape` | class | DiagonalTunnelShape | — | 2 | 19 | [EscapeTunnelShape.java](dev/ftb/mods/ftbultimine/shape/EscapeTunnelShape.java) |
| `SmallSquareShape` | class | LargeTunnelShape | — | 2 | 19 | [SmallSquareShape.java](dev/ftb/mods/ftbultimine/shape/SmallSquareShape.java) |
| `SmallTunnelShape` | class | Shape | — | 2 | 35 | [SmallTunnelShape.java](dev/ftb/mods/ftbultimine/shape/SmallTunnelShape.java) |
| `LargeTunnelShape` | class | Shape | — | 3 | 62 | [LargeTunnelShape.java](dev/ftb/mods/ftbultimine/shape/LargeTunnelShape.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockMixin` | `Block` | [BlockMixin.java](dev/ftb/mods/ftbultimine/mixin/BlockMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FTBUltimineClientConfig` | — | [FTBUltimineClientConfig.java](dev/ftb/mods/ftbultimine/config/FTBUltimineClientConfig.java) |
| `FTBUltimineServerConfig` | — | [FTBUltimineServerConfig.java](dev/ftb/mods/ftbultimine/config/FTBUltimineServerConfig.java) |

## 全部类（76）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FTBUltimineNeoForgeClient` | class | — | Mod | 0 | 21 | [FTBUltimineNeoForgeClient.java](dev/ftb/mods/ftbultimine/neoforge/FTBUltimineNeoForgeClient.java) |
| `RestrictionHandlerRegistry` | enum | RegisterRestrictionHandlerEvent.Regi | — | 2 | 30 | [RestrictionHandlerRegistry.java](dev/ftb/mods/ftbultimine/RestrictionHandlerRegistry.java) |
| `FTBUltimineNeoForge` | class | — | Mod | 1 | 25 | [FTBUltimineNeoForge.java](dev/ftb/mods/ftbultimine/neoforge/FTBUltimineNeoForge.java) |
| `RegisterBlockBreakHandlerEvent` | interface | — | — | 0 | 17 | [RegisterBlockBreakHandlerEvent.java](dev/ftb/mods/ftbultimine/api/blockbreaking/RegisterBlockBreakHandlerEvent.java) |
| `RegisterBlockSelectionHandlerEvent` | interface | — | — | 0 | 16 | [RegisterBlockSelectionHandlerEvent.java](dev/ftb/mods/ftbultimine/api/blockselection/RegisterBlockSelectionHandlerEvent.java) |
| `BlockSelectionRegistry` | enum | RegisterBlockSelectionHandlerEvent.D | — | 4 | 31 | [BlockSelectionRegistry.java](dev/ftb/mods/ftbultimine/BlockSelectionRegistry.java) |
| `BlockBreakingRegistry` | enum | RegisterBlockBreakHandlerEvent.Regis | — | 4 | 31 | [BlockBreakingRegistry.java](dev/ftb/mods/ftbultimine/BlockBreakingRegistry.java) |
| `BlockMixin` | class | — | Mixin | 1 | 27 | [BlockMixin.java](dev/ftb/mods/ftbultimine/mixin/BlockMixin.java) |
| `RegisterRightClickHandlerEvent` | interface | — | — | 0 | 17 | [RegisterRightClickHandlerEvent.java](dev/ftb/mods/ftbultimine/api/rightclick/RegisterRightClickHandlerEvent.java) |
| `RegisterRestrictionHandlerEvent` | interface | — | — | 0 | 15 | [RegisterRestrictionHandlerEvent.java](dev/ftb/mods/ftbultimine/api/restriction/RegisterRestrictionHandlerEvent.java) |
| `IntegrationHandler` | class | — | — | 1 | 32 | [IntegrationHandler.java](dev/ftb/mods/ftbultimine/integration/IntegrationHandler.java) |
| `BlockBreakHandler` | interface | — | — | 0 | 22 | [BlockBreakHandler.java](dev/ftb/mods/ftbultimine/api/blockbreaking/BlockBreakHandler.java) |
| `BlockSelectionHandler` | interface | — | — | 2 | 29 | [BlockSelectionHandler.java](dev/ftb/mods/ftbultimine/api/blockselection/BlockSelectionHandler.java) |
| `CropLikeRegistry` | enum | RegisterCropLikeEvent.Dispatcher | — | 6 | 62 | [CropLikeRegistry.java](dev/ftb/mods/ftbultimine/crops/CropLikeRegistry.java) |
| `FTBUltimine` | class | ResourceManagerReloadListener | — | 20 | 413 | [FTBUltimine.java](dev/ftb/mods/ftbultimine/FTBUltimine.java) |
| `ShapeRegistry` | enum | RegisterShapeEvent.Registry | — | 3 | 43 | [ShapeRegistry.java](dev/ftb/mods/ftbultimine/shape/ShapeRegistry.java) |
| `FTBUltimineClientConfig` | interface | — | — | 0 | 54 | [FTBUltimineClientConfig.java](dev/ftb/mods/ftbultimine/config/FTBUltimineClientConfig.java) |
| `VanillaCropLikeHandler` | enum | CropLikeHandler | — | 8 | 87 | [VanillaCropLikeHandler.java](dev/ftb/mods/ftbultimine/crops/VanillaCropLikeHandler.java) |
| `AgriCraftCropLikeHandler` | enum | CropLikeHandler | — | 3 | 31 | [AgriCraftCropLikeHandler.java](dev/ftb/mods/ftbultimine/integration/agricraft/AgriCraftCropLikeHandler.java) |
| `EZCrystalsHandler` | enum | RightClickHandler | — | 1 | 33 | [EZCrystalsHandler.java](dev/ftb/mods/ftbultimine/integration/ezcrystals/neoforge/EZCrystalsHandler.java) |
| `FTBUltimineServerConfig` | interface | — | — | 4 | 223 | [FTBUltimineServerConfig.java](dev/ftb/mods/ftbultimine/config/FTBUltimineServerConfig.java) |
| `RightClickHandler` | interface | — | — | 0 | 26 | [RightClickHandler.java](dev/ftb/mods/ftbultimine/api/rightclick/RightClickHandler.java) |
| `CropLikeHandler` | interface | — | — | 0 | 16 | [CropLikeHandler.java](dev/ftb/mods/ftbultimine/api/crop/CropLikeHandler.java) |
| `RestrictionHandler` | interface | — | — | 0 | 14 | [RestrictionHandler.java](dev/ftb/mods/ftbultimine/api/restriction/RestrictionHandler.java) |
| `FTBUltimineClient` | class | — | — | 13 | 359 | [FTBUltimineClient.java](dev/ftb/mods/ftbultimine/client/FTBUltimineClient.java) |
| `FTBUltimineCommands` | class | — | — | 1 | 49 | [FTBUltimineCommands.java](dev/ftb/mods/ftbultimine/FTBUltimineCommands.java) |
| `FTBUltiminePlayerData` | class | — | — | 14 | 147 | [FTBUltiminePlayerData.java](dev/ftb/mods/ftbultimine/FTBUltiminePlayerData.java) |
| `LevelRenderLastEvent` | interface | — | — | 0 | 12 | [LevelRenderLastEvent.java](dev/ftb/mods/ftbultimine/event/LevelRenderLastEvent.java) |
| `FTBRanksIntegration` | class | — | — | 7 | 66 | [FTBRanksIntegration.java](dev/ftb/mods/ftbultimine/integration/ranks/FTBRanksIntegration.java) |
| `ModeChangedPacket` | record | CustomPacketPayload | — | 2 | 27 | [ModeChangedPacket.java](dev/ftb/mods/ftbultimine/net/ModeChangedPacket.java) |
| `SyncUltimineTimePacket` | record | CustomPacketPayload | — | 2 | 45 | [SyncUltimineTimePacket.java](dev/ftb/mods/ftbultimine/net/SyncUltimineTimePacket.java) |
| `KeyPressedPacket` | record | CustomPacketPayload | — | 2 | 27 | [KeyPressedPacket.java](dev/ftb/mods/ftbultimine/net/KeyPressedPacket.java) |
| `SendShapePacket` | record | CustomPacketPayload | — | 4 | 41 | [SendShapePacket.java](dev/ftb/mods/ftbultimine/net/SendShapePacket.java) |
| `RegisterShapeEvent` | interface | — | — | 0 | 16 | [RegisterShapeEvent.java](dev/ftb/mods/ftbultimine/api/shape/RegisterShapeEvent.java) |
| `RegisterCropLikeEvent` | interface | — | — | 0 | 16 | [RegisterCropLikeEvent.java](dev/ftb/mods/ftbultimine/api/crop/RegisterCropLikeEvent.java) |
| `ClientPlatformUtil` | class | — | — | 2 | 20 | [ClientPlatformUtil.java](dev/ftb/mods/ftbultimine/client/ClientPlatformUtil.java) |
| `ClientPlatformUtilImpl` | class | — | — | 1 | 20 | [ClientPlatformUtilImpl.java](dev/ftb/mods/ftbultimine/client/neoforge/ClientPlatformUtilImpl.java) |
| `ShapeMerger` | class | — | — | 17 | 177 | [ShapeMerger.java](dev/ftb/mods/ftbultimine/utils/ShapeMerger.java) |
| `BlockMatchers` | interface | — | — | 0 | 32 | [BlockMatchers.java](dev/ftb/mods/ftbultimine/shape/BlockMatchers.java) |
| `BlockMatcher` | record | ShapeContext.Matcher | — | 4 | 36 | [BlockMatcher.java](dev/ftb/mods/ftbultimine/shape/BlockMatcher.java) |
| `ItemCollector` | class | — | — | 3 | 71 | [ItemCollector.java](dev/ftb/mods/ftbultimine/api/util/ItemCollector.java) |
| `ShapeScreen` | class | — | — | 0 | 5 | [ShapeScreen.java](dev/ftb/mods/ftbultimine/client/ShapeScreen.java) |
| `XPUtils` | class | — | — | 6 | 59 | [XPUtils.java](dev/ftb/mods/ftbultimine/utils/XPUtils.java) |
| `EntityDistanceComparator` | class | Comparator<BlockPos> | — | 2 | 28 | [EntityDistanceComparator.java](dev/ftb/mods/ftbultimine/utils/EntityDistanceComparator.java) |
| `CooldownTracker` | class | — | — | 6 | 60 | [CooldownTracker.java](dev/ftb/mods/ftbultimine/CooldownTracker.java) |
| `ShapelessShape` | class | Shape | — | 5 | 89 | [ShapelessShape.java](dev/ftb/mods/ftbultimine/shape/ShapelessShape.java) |
| `PlatformUtil` | class | — | — | 4 | 36 | [PlatformUtil.java](dev/ftb/mods/ftbultimine/utils/PlatformUtil.java) |
| `PlatformUtilImpl` | class | — | — | 4 | 26 | [PlatformUtilImpl.java](dev/ftb/mods/ftbultimine/utils/neoforge/PlatformUtilImpl.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_FTBUltimine121_common_6cccbc998cb64d97a350f3347bd335fa_765eda5a25eca4ba0731519c9df059f6c394719705c84e5a9479ca4737a03d6bftbultimine2101115devjar/PlatformMethods.java) |
| `FTBUltimineAPIImpl` | enum | FTBUltimineAPI.API | — | 2 | 36 | [FTBUltimineAPIImpl.java](dev/ftb/mods/ftbultimine/FTBUltimineAPIImpl.java) |
| `AxeStripping` | enum | RightClickHandler | — | 1 | 38 | [AxeStripping.java](dev/ftb/mods/ftbultimine/rightclick/AxeStripping.java) |
| `RightClickDispatcher` | enum | RegisterRightClickHandlerEvent.Dispa | — | 4 | 40 | [RightClickDispatcher.java](dev/ftb/mods/ftbultimine/rightclick/RightClickDispatcher.java) |
| `FarmlandConversion` | enum | RightClickHandler | — | 1 | 38 | [FarmlandConversion.java](dev/ftb/mods/ftbultimine/rightclick/FarmlandConversion.java) |
| `CropHarvesting` | enum | RightClickHandler | — | 1 | 41 | [CropHarvesting.java](dev/ftb/mods/ftbultimine/rightclick/CropHarvesting.java) |
| `ShovelFlattening` | enum | RightClickHandler | — | 1 | 38 | [ShovelFlattening.java](dev/ftb/mods/ftbultimine/rightclick/ShovelFlattening.java) |
| `MiningTunnelShape` | class | DiagonalTunnelShape | — | 2 | 19 | [MiningTunnelShape.java](dev/ftb/mods/ftbultimine/shape/MiningTunnelShape.java) |
| `EscapeTunnelShape` | class | DiagonalTunnelShape | — | 2 | 19 | [EscapeTunnelShape.java](dev/ftb/mods/ftbultimine/shape/EscapeTunnelShape.java) |
| `SmallSquareShape` | class | LargeTunnelShape | — | 2 | 19 | [SmallSquareShape.java](dev/ftb/mods/ftbultimine/shape/SmallSquareShape.java) |
| `SmallTunnelShape` | class | Shape | — | 2 | 35 | [SmallTunnelShape.java](dev/ftb/mods/ftbultimine/shape/SmallTunnelShape.java) |
| `LargeTunnelShape` | class | Shape | — | 3 | 62 | [LargeTunnelShape.java](dev/ftb/mods/ftbultimine/shape/LargeTunnelShape.java) |
| `DiagonalTunnelShape` | class | Shape | — | 2 | 30 | [DiagonalTunnelShape.java](dev/ftb/mods/ftbultimine/shape/DiagonalTunnelShape.java) |
| `LostTrinketsFTBUltiminePlugin` | class | RestrictionHandler | — | 1 | 12 | [LostTrinketsFTBUltiminePlugin.java](dev/ftb/mods/ftbultimine/neoforge/plugin/losttrinkets/LostTrinketsFTBUltiminePlugin.java) |
| `AgricraftSetup` | class | — | — | 1 | 12 | [AgricraftSetup.java](dev/ftb/mods/ftbultimine/integration/agricraft/AgricraftSetup.java) |
| `LogBreakTracker` | enum | — | — | 2 | 22 | [LogBreakTracker.java](dev/ftb/mods/ftbultimine/integration/acceldecay/LogBreakTracker.java) |
| `AcceleratedDecay` | class | — | — | 1 | 10 | [AcceleratedDecay.java](dev/ftb/mods/ftbultimine/integration/acceldecay/AcceleratedDecay.java) |
| `EZCrystals` | class | — | — | 1 | 14 | [EZCrystals.java](dev/ftb/mods/ftbultimine/integration/ezcrystals/EZCrystals.java) |
| `EZCrystalsImpl` | class | — | — | 1 | 10 | [EZCrystalsImpl.java](dev/ftb/mods/ftbultimine/integration/ezcrystals/neoforge/EZCrystalsImpl.java) |
| `FTBUltimineNet` | class | — | — | 1 | 13 | [FTBUltimineNet.java](dev/ftb/mods/ftbultimine/net/FTBUltimineNet.java) |
| `FTBUltimineAPI` | class | — | — | 4 | 50 | [FTBUltimineAPI.java](dev/ftb/mods/ftbultimine/api/FTBUltimineAPI.java) |
| `ShapeContext` | record | — | — | 3 | 39 | [ShapeContext.java](dev/ftb/mods/ftbultimine/api/shape/ShapeContext.java) |
| `Shape` | interface | — | — | 0 | 29 | [Shape.java](dev/ftb/mods/ftbultimine/api/shape/Shape.java) |
| `CanUltimineResult` | interface | — | — | 0 | 35 | [CanUltimineResult.java](dev/ftb/mods/ftbultimine/api/util/CanUltimineResult.java) |
| `ModAttributes` | class | — | — | 2 | 42 | [ModAttributes.java](dev/ftb/mods/ftbultimine/registry/ModAttributes.java) |
| `EdgeCallback` | class | DoubleLineConsumer | — | 1 | 30 | [EdgeCallback.java](dev/ftb/mods/ftbultimine/client/EdgeCallback.java) |
| `CachedEdge` | record | — | — | 1 | 8 | [CachedEdge.java](dev/ftb/mods/ftbultimine/client/CachedEdge.java) |
| `UltimineRenderTypes` | class | RenderType | — | 0 | 52 | [UltimineRenderTypes.java](dev/ftb/mods/ftbultimine/client/UltimineRenderTypes.java) |
