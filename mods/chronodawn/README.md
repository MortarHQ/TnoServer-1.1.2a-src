# Chrono Dawn（`chronodawn`）

> jar：`[时序]chronodawn.jar` ｜ 版本：`0.8.0` ｜ 作者：ksoichiro
> 规模：**484 个类 / 65 个包 / 36771 行** ｜ 事件处理类 5 ｜ Mixin 18 ｜ 注册/管理类 32

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.chronodawn.blocks` | 145 | `BossRoomBoundaryMarkerBlockEntity`、`BossRoomDoorBlockEntity`、`TemporalPointedDripstoneBlock` |
| `com.chronodawn.client.model` | 28 | `ChronoUrsidModel`、`TimeTyrantModel`、`SecondhandArcherModel` |
| `com.chronodawn.client.renderer.mobs` | 26 | `ChronoUrsidRenderer`、`GlideFishRenderer`、`TimeKeeperRenderer` |
| `com.chronodawn.entities.mobs` | 21 | `TimeboundRabbitEntity`、`FloqEntity`、`TimeKeeperEntity` |
| `com.chronodawn.items.equipment` | 20 | `TemporalAmberArmorItem`、`ClockstoneHoeItem`、`EnhancedClockstonePickaxeItem` |
| `com.chronodawn.items.consumables` | 19 | `ClockworkCookieItem`、`TimelessMushroomItem`、`ChronoMelonSliceItem` |
| `com.chronodawn.registry` | 17 | `ModItems`、`ModBlockEntityId`、`ModBlockId` |
| `com.chronodawn.items` | 15 | `TimeCompassItem`、`PortalStabilizerItem`、`DeferredSpawnEggItem` |
| `com.chronodawn.compat` | 13 | `BlockEntityDataHandler`、`ItemDataHandler121`、`ItemDataHandler` |
| `com.chronodawn.mixin` | 12 | `FreezeTopLayerMixin`、`AnvilMenuMixin`、`AnvilMenuAccessor` |
| `com.chronodawn.gametest` | 11 | `RegistryDrivenTestGenerator`、`RegistryDrivenTestData`、`RegistryConsistencyTests` |
| `com.chronodawn.client.renderer` | 11 | `TimeGuardianRenderer`、`TimeBlastRenderer`、`ClockworkColossusRenderer` |
| `com.chronodawn.worldgen.spawning` | 9 | `PhantomCatacombsBossRoomPlacer`、`MasterClockBossRoomPlacer`、`ClockworkColossusSpawner` |
| `com.chronodawn.data` | 8 | `PortalRegistryData`、`BossSpawnData`、`PlayerProgressData` |
| `com.chronodawn.items.artifacts` | 8 | `UnstablePocketWatchSwapHandler`、`ChronobladeAISkipHandler`、`ChronobladeItem` |
| `com.chronodawn.entities.bosses` | 7 | `TimeTyrantEntity`、`TimeGuardianEntity`、`ClockworkColossusEntity` |
| `com.chronodawn.events` | 6 | `BlockEventHandler`、`EntityEventHandler`、`PlayerEventHandler` |
| `com.chronodawn.core.portal` | 6 | `PortalTeleportHandler`、`PortalRegistry`、`PortalPersistenceManager` |
| `com.chronodawn.client` | 6 | `PortalEffectHandler`、`PortalFadeHandler`、`TemporalGrassEdgeTint` |
| `com.chronodawn.config` | 5 | `ConfigLoader`、`ChronoDawnConfig`、`ConfigDefaults` |
| `com.chronodawn.worldgen.features` | 5 | `NbtTemplateConfiguration`、`FruitOfTimeTreeFeature`、`AncientTimeWoodTreeFeature` |
| `com.chronodawn.gui.data` | 5 | `ChronicleData`、`Entry`、`Category` |
| `com.chronodawn.worldgen.runtime` | 5 | `OverlayPackBootstrap`、`OverlayPackPlatform`、`RuntimeStructureOverlay` |
| `com.chronodawn.mixin.client` | 4 | `ClientAdvancementsAccessor`、`FogRendererMixin`、`SkyColorMixin` |
| `com.chronodawn.items.base` | 4 | `EnhancedClockstoneItem`、`FragmentOfStasisCoreItem`、`TimeCrystalItem` |
| `com.chronodawn.neoforge.event` | 3 | `BlockProtectionEventHandler`、`FuelEventHandler`、`OverlayPackFinder` |
| `com.chronodawn.items.shield` | 3 | `ChronoShieldEffectHandler`、`ChronoShieldMarker`、`ChronoShieldTier` |
| `com.chronodawn.neoforge.registry` | 3 | `ModLootModifiers`、`ModParticles`、`ModFluidTypes` |
| `com.chronodawn.entities.projectiles` | 3 | `GearProjectileEntity`、`TimeBlastEntity`、`TimeArrowEntity` |
| `com.chronodawn.core.time` | 3 | `ReversedResonance`、`TimeDistortionEffect`、`MobAICanceller` |
| `com.chronodawn.core.dimension` | 3 | `ChronoDawnDimension`、`DimensionStabilizer`、`ChronoDawnBiomeProvider` |
| `com.chronodawn.entities.boats` | 3 | `ChronoDawnBoat`、`ChronoDawnChestBoat`、`ChronoDawnBoatType` |
| `com.chronodawn.neoforge.client` | 2 | `ChronoDawnClientNeoForge`、`VersionSpecificClientHelper` |
| `com.chronodawn.neoforge` | 2 | `ChronoDawnNeoForge`、`VersionSpecificServerHelper` |
| `com.chronodawn.neoforge.mixin` | 2 | `StructureTemplateBlockAttachedFixMixin`、`GuiPortalOverlayMixin` |
| `com.chronodawn.worldgen.protection` | 2 | `BlockProtectionHandler`、`PermanentProtectionHandler` |
| `com.chronodawn.gui.widgets` | 2 | `EntryPageWidget`、`CategoryListWidget` |
| `com.chronodawn.network` | 2 | `ModNetworking`、`S2CShieldCooldownPayload` |
| `com.chronodawn.items.tools` | 2 | `SpatiallyLinkedPickaxeItem`、`TimeClockItem` |
| `com.chronodawn.worldgen.processors` | 2 | `BossRoomProtectionProcessor`、`CopyFluidLevelProcessor` |
| … | | 其余 25 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BlockProtectionEventHandler` | class | — | EventBusSubscriber | 2 | 67 | [BlockProtectionEventHandler.java](com/chronodawn/neoforge/event/BlockProtectionEventHandler.java) |
| `AnvilRepairEventHandler` | class | — | EventBusSubscriber | 1 | 28 | [AnvilRepairEventHandler.java](com/chronodawn/neoforge/anvil/AnvilRepairEventHandler.java) |
| `FuelEventHandler` | class | — | EventBusSubscriber | 2 | 54 | [FuelEventHandler.java](com/chronodawn/neoforge/event/FuelEventHandler.java) |
| `ChronoDawnClientNeoForge` | class | — | EventBusSubscriber | 20 | 411 | [ChronoDawnClientNeoForge.java](com/chronodawn/neoforge/client/ChronoDawnClientNeoForge.java) |
| `RegistryDrivenTestGenerator` | class | — | — | 30 | 416 | [RegistryDrivenTestGenerator.java](com/chronodawn/gametest/RegistryDrivenTestGenerator.java) |
| `ChronoDawnGameTestsNeoForge` | class | — | EventBusSubscriber | 3 | 55 | [ChronoDawnGameTestsNeoForge.java](com/chronodawn/neoforge/gametest/ChronoDawnGameTestsNeoForge.java) |
| `BlockEventHandler` | class | — | — | 11 | 366 | [BlockEventHandler.java](com/chronodawn/events/BlockEventHandler.java) |
| `ChronoDawnNeoForge` | class | — | Mod | 6 | 293 | [ChronoDawnNeoForge.java](com/chronodawn/neoforge/ChronoDawnNeoForge.java) |
| `PhantomCatacombsBossRoomPlacer` | class | — | — | 31 | 1309 | [PhantomCatacombsBossRoomPlacer.java](com/chronodawn/worldgen/spawning/PhantomCatacombsBossRoomPlacer.java) |
| `EntityEventHandler` | class | — | — | 6 | 167 | [EntityEventHandler.java](com/chronodawn/events/EntityEventHandler.java) |
| `TimeTyrantEntity` | class | Monster | — | 33 | 620 | [TimeTyrantEntity.java](com/chronodawn/entities/bosses/TimeTyrantEntity.java) |
| `RegistryDrivenTestData` | class | — | — | 13 | 261 | [RegistryDrivenTestData.java](com/chronodawn/gametest/RegistryDrivenTestData.java) |
| `PortalTeleportHandler` | class | — | — | 18 | 409 | [PortalTeleportHandler.java](com/chronodawn/core/portal/PortalTeleportHandler.java) |
| `MasterClockBossRoomPlacer` | class | — | — | 27 | 1356 | [MasterClockBossRoomPlacer.java](com/chronodawn/worldgen/spawning/MasterClockBossRoomPlacer.java) |
| `PlayerEventHandler` | class | — | — | 8 | 211 | [PlayerEventHandler.java](com/chronodawn/events/PlayerEventHandler.java) |
| `BlockEntityDataHandler` | interface | — | — | 0 | 10 | [BlockEntityDataHandler.java](com/chronodawn/compat/BlockEntityDataHandler.java) |
| `FreezeTopLayerMixin` | class | — | Mixin | 10 | 306 | [FreezeTopLayerMixin.java](com/chronodawn/mixin/FreezeTopLayerMixin.java) |
| `TimeDistortionEventHandler` | class | — | — | 7 | 111 | [TimeDistortionEventHandler.java](com/chronodawn/events/TimeDistortionEventHandler.java) |
| `ItemDataHandler121` | class | ItemDataHandler | — | 7 | 73 | [ItemDataHandler121.java](com/chronodawn/compat/ItemDataHandler121.java) |
| `TimeboundRabbitEntity` | class | Animal | — | 30 | 287 | [TimeboundRabbitEntity.java](com/chronodawn/entities/mobs/TimeboundRabbitEntity.java) |
| `PortalRegistry` | class | — | — | 13 | 148 | [PortalRegistry.java](com/chronodawn/core/portal/PortalRegistry.java) |
| `StructureTemplateBlockAttachedFixMixin` | class | — | Mixin | 0 | 47 | [StructureTemplateBlockAttachedFixMixin.java](com/chronodawn/neoforge/mixin/StructureTemplateBlockAttachedFixMixin.java) |
| `BlockProtectionHandler` | class | — | — | 6 | 70 | [BlockProtectionHandler.java](com/chronodawn/worldgen/protection/BlockProtectionHandler.java) |
| `AnvilMenuMixin` | class | ItemCombinerMenu | Mixin | 1 | 40 | [AnvilMenuMixin.java](com/chronodawn/mixin/AnvilMenuMixin.java) |
| `AnvilMenuAccessor` | interface | — | Mixin | 0 | 16 | [AnvilMenuAccessor.java](com/chronodawn/mixin/AnvilMenuAccessor.java) |
| `ChronoShieldBlockingMixin` | class | — | Mixin | 0 | 26 | [ChronoShieldBlockingMixin.java](com/chronodawn/mixin/ChronoShieldBlockingMixin.java) |
| `StructureTemplateBlockAttachedFixMixin` | class | — | Mixin | 0 | 54 | [StructureTemplateBlockAttachedFixMixin.java](com/chronodawn/mixin/StructureTemplateBlockAttachedFixMixin.java) |
| `ClientAdvancementsAccessor` | interface | — | Mixin | 0 | 15 | [ClientAdvancementsAccessor.java](com/chronodawn/mixin/client/ClientAdvancementsAccessor.java) |
| `FogRendererMixin` | class | — | Mixin | 1 | 41 | [FogRendererMixin.java](com/chronodawn/mixin/client/FogRendererMixin.java) |
| `ChronoDawnEvents` | class | — | — | 1 | 15 | [ChronoDawnEvents.java](com/chronodawn/events/ChronoDawnEvents.java) |
| `ItemDataHandler` | interface | — | — | 0 | 22 | [ItemDataHandler.java](com/chronodawn/compat/ItemDataHandler.java) |
| `PortalRegistryData` | class | ChronoDawnWorldData | — | 5 | 37 | [PortalRegistryData.java](com/chronodawn/data/PortalRegistryData.java) |
| `TimeGuardianEntity` | class | Monster | — | 27 | 393 | [TimeGuardianEntity.java](com/chronodawn/entities/bosses/TimeGuardianEntity.java) |
| `RegistryConsistencyTests` | class | — | — | 5 | 177 | [RegistryConsistencyTests.java](com/chronodawn/gametest/RegistryConsistencyTests.java) |
| `TeleporterChargingHandler` | class | — | — | 8 | 211 | [TeleporterChargingHandler.java](com/chronodawn/core/teleport/TeleporterChargingHandler.java) |
| `StructureStartMixin` | class | — | Mixin | 4 | 203 | [StructureStartMixin.java](com/chronodawn/mixin/StructureStartMixin.java) |
| `CompatHandlers` | class | — | — | 0 | 10 | [CompatHandlers.java](com/chronodawn/compat/CompatHandlers.java) |
| `ItemDurabilityHandler` | interface | — | — | 0 | 24 | [ItemDurabilityHandler.java](com/chronodawn/compat/ItemDurabilityHandler.java) |
| `ItemDurabilityHandler121` | class | ItemDurabilityHandler | — | 1 | 13 | [ItemDurabilityHandler121.java](com/chronodawn/compat/ItemDurabilityHandler121.java) |
| `ModItems` | class | — | — | 3 | 1271 | [ModItems.java](com/chronodawn/registry/ModItems.java) |
| `FloqEntity` | class | Monster | — | 21 | 226 | [FloqEntity.java](com/chronodawn/entities/mobs/FloqEntity.java) |
| `TimeKeeperEntity` | class | AbstractVillager | — | 21 | 335 | [TimeKeeperEntity.java](com/chronodawn/entities/mobs/TimeKeeperEntity.java) |
| `TickingSheepEntity` | class | Animal | — | 24 | 222 | [TickingSheepEntity.java](com/chronodawn/entities/mobs/TickingSheepEntity.java) |
| `ClockworkColossusEntity` | class | Monster | — | 24 | 305 | [ClockworkColossusEntity.java](com/chronodawn/entities/bosses/ClockworkColossusEntity.java) |
| `TemporalPhantomEntity` | class | Monster | — | 21 | 313 | [TemporalPhantomEntity.java](com/chronodawn/entities/bosses/TemporalPhantomEntity.java) |
| `ChronosWardenEntity` | class | Monster | — | 24 | 280 | [ChronosWardenEntity.java](com/chronodawn/entities/bosses/ChronosWardenEntity.java) |
| `ChronoShieldEffectHandler` | class | — | — | 9 | 168 | [ChronoShieldEffectHandler.java](com/chronodawn/items/shield/ChronoShieldEffectHandler.java) |
| `GuiPortalOverlayMixin` | class | — | Mixin | 1 | 80 | [GuiPortalOverlayMixin.java](com/chronodawn/neoforge/mixin/GuiPortalOverlayMixin.java) |
| `ChronoShieldDamageMixin` | class | — | Mixin | 1 | 46 | [ChronoShieldDamageMixin.java](com/chronodawn/mixin/ChronoShieldDamageMixin.java) |
| `ServerLevelSetTimeMixin` | class | — | Mixin | 1 | 26 | [ServerLevelSetTimeMixin.java](com/chronodawn/mixin/ServerLevelSetTimeMixin.java) |
| `SleepMixin` | class | — | Mixin | 1 | 100 | [SleepMixin.java](com/chronodawn/mixin/SleepMixin.java) |
| `PackRepositoryOverlayMixin` | class | — | Mixin | 0 | 54 | [PackRepositoryOverlayMixin.java](com/chronodawn/mixin/PackRepositoryOverlayMixin.java) |
| `ChronoShieldEffectInterceptorMixin` | class | — | Mixin | 0 | 23 | [ChronoShieldEffectInterceptorMixin.java](com/chronodawn/mixin/ChronoShieldEffectInterceptorMixin.java) |
| `LevelGetTimeMixin` | class | ChronoDawnTimeHolder | Mixin | 4 | 75 | [LevelGetTimeMixin.java](com/chronodawn/mixin/LevelGetTimeMixin.java) |
| `SkyColorMixin` | class | — | Mixin | 2 | 74 | [SkyColorMixin.java](com/chronodawn/mixin/client/SkyColorMixin.java) |
| `GuiGraphicsShieldCdMixin` | class | — | Mixin | 0 | 47 | [GuiGraphicsShieldCdMixin.java](com/chronodawn/mixin/client/GuiGraphicsShieldCdMixin.java) |
| `CompatBlockEntity` | class | BlockEntity | — | 4 | 31 | [CompatBlockEntity.java](com/chronodawn/compat/CompatBlockEntity.java) |
| `SavedDataHandler` | interface | — | — | 0 | 10 | [SavedDataHandler.java](com/chronodawn/compat/SavedDataHandler.java) |
| `BossSpawnData` | class | CompatSavedData | — | 24 | 269 | [BossSpawnData.java](com/chronodawn/data/BossSpawnData.java) |
| `EntropyKeeperEntity` | class | Monster | — | 19 | 269 | [EntropyKeeperEntity.java](com/chronodawn/entities/bosses/EntropyKeeperEntity.java) |

## 事件处理（`@SubscribeEvent`，5 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ChronoDawnClientNeoForge` | 10 | `com.chronodawn.neoforge.client` | [ChronoDawnClientNeoForge.java](com/chronodawn/neoforge/client/ChronoDawnClientNeoForge.java) |
| `BlockProtectionEventHandler` | 2 | `com.chronodawn.neoforge.event` | [BlockProtectionEventHandler.java](com/chronodawn/neoforge/event/BlockProtectionEventHandler.java) |
| `AnvilRepairEventHandler` | 1 | `com.chronodawn.neoforge.anvil` | [AnvilRepairEventHandler.java](com/chronodawn/neoforge/anvil/AnvilRepairEventHandler.java) |
| `FuelEventHandler` | 1 | `com.chronodawn.neoforge.event` | [FuelEventHandler.java](com/chronodawn/neoforge/event/FuelEventHandler.java) |
| `ChronoDawnGameTestsNeoForge` | 1 | `com.chronodawn.neoforge.gametest` | [ChronoDawnGameTestsNeoForge.java](com/chronodawn/neoforge/gametest/ChronoDawnGameTestsNeoForge.java) |

## Mixin（18 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `FreezeTopLayerMixin` | `FreezeTopLayer` | [FreezeTopLayerMixin.java](com/chronodawn/mixin/FreezeTopLayerMixin.java) |
| `StructureTemplateBlockAttachedFixMixin` | `StructureTemplateBlockAttachedFix` | [StructureTemplateBlockAttachedFixMixin.java](com/chronodawn/neoforge/mixin/StructureTemplateBlockAttachedFixMixin.java) |
| `AnvilMenuMixin` | `AnvilMenu` | [AnvilMenuMixin.java](com/chronodawn/mixin/AnvilMenuMixin.java) |
| `AnvilMenuAccessor` | `AnvilMenuAccessor` | [AnvilMenuAccessor.java](com/chronodawn/mixin/AnvilMenuAccessor.java) |
| `ChronoShieldBlockingMixin` | `ChronoShieldBlocking` | [ChronoShieldBlockingMixin.java](com/chronodawn/mixin/ChronoShieldBlockingMixin.java) |
| `StructureTemplateBlockAttachedFixMixin` | `StructureTemplateBlockAttachedFix` | [StructureTemplateBlockAttachedFixMixin.java](com/chronodawn/mixin/StructureTemplateBlockAttachedFixMixin.java) |
| `ClientAdvancementsAccessor` | `ClientAdvancementsAccessor` | [ClientAdvancementsAccessor.java](com/chronodawn/mixin/client/ClientAdvancementsAccessor.java) |
| `FogRendererMixin` | `FogRenderer` | [FogRendererMixin.java](com/chronodawn/mixin/client/FogRendererMixin.java) |
| `StructureStartMixin` | `StructureStart` | [StructureStartMixin.java](com/chronodawn/mixin/StructureStartMixin.java) |
| `GuiPortalOverlayMixin` | `GuiPortalOverlay` | [GuiPortalOverlayMixin.java](com/chronodawn/neoforge/mixin/GuiPortalOverlayMixin.java) |
| `ChronoShieldDamageMixin` | `ChronoShieldDamage` | [ChronoShieldDamageMixin.java](com/chronodawn/mixin/ChronoShieldDamageMixin.java) |
| `ServerLevelSetTimeMixin` | `ServerLevelSetTime` | [ServerLevelSetTimeMixin.java](com/chronodawn/mixin/ServerLevelSetTimeMixin.java) |
| `SleepMixin` | `Sleep` | [SleepMixin.java](com/chronodawn/mixin/SleepMixin.java) |
| `PackRepositoryOverlayMixin` | `PackRepositoryOverlay` | [PackRepositoryOverlayMixin.java](com/chronodawn/mixin/PackRepositoryOverlayMixin.java) |
| `ChronoShieldEffectInterceptorMixin` | `ChronoShieldEffectInterceptor` | [ChronoShieldEffectInterceptorMixin.java](com/chronodawn/mixin/ChronoShieldEffectInterceptorMixin.java) |
| `LevelGetTimeMixin` | `LevelGetTime` | [LevelGetTimeMixin.java](com/chronodawn/mixin/LevelGetTimeMixin.java) |
| `SkyColorMixin` | `SkyColor` | [SkyColorMixin.java](com/chronodawn/mixin/client/SkyColorMixin.java) |
| `GuiGraphicsShieldCdMixin` | `GuiGraphicsShieldCd` | [GuiGraphicsShieldCdMixin.java](com/chronodawn/mixin/client/GuiGraphicsShieldCdMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigLoader` | — | [ConfigLoader.java](com/chronodawn/config/ConfigLoader.java) |
| `ChronoDawnConfig` | — | [ChronoDawnConfig.java](com/chronodawn/config/ChronoDawnConfig.java) |
| `NbtTemplateConfiguration` | — | [NbtTemplateConfiguration.java](com/chronodawn/worldgen/features/NbtTemplateConfiguration.java) |
| `ConfigDefaults` | — | [ConfigDefaults.java](com/chronodawn/config/ConfigDefaults.java) |
| `OresConfig` | — | [OresConfig.java](com/chronodawn/config/OresConfig.java) |

## 按包浏览

- `architectury_inject_chronodawn_common1211_fe5d4f7360fb48b9953b4b6344c4294f_64ba00bd7e4de6ec622ee38d6e047d626a336c54076576f12ea4a12f61a487f1common1211080devjar`（1 类）
- `com.chronodawn`（1 类）
- `com.chronodawn.anvil`（1 类）
- `com.chronodawn.api`（1 类）
- `com.chronodawn.blocks`（145 类）
- `com.chronodawn.client`（6 类）
- `com.chronodawn.client.model`（28 类）
- `com.chronodawn.client.particle`（2 类）
- `com.chronodawn.client.renderer`（11 类）
- `com.chronodawn.client.renderer.mobs`（26 类）
- `com.chronodawn.client.shield`（1 类）
- `com.chronodawn.compat`（13 类）
- `com.chronodawn.config`（5 类）
- `com.chronodawn.core.dimension`（3 类）
- `com.chronodawn.core.portal`（6 类）
- `com.chronodawn.core.teleport`（1 类）
- `com.chronodawn.core.time`（3 类）
- `com.chronodawn.data`（8 类）
- `com.chronodawn.effects`（2 类）
- `com.chronodawn.entities.ai`（2 类）
- `com.chronodawn.entities.boats`（3 类）
- `com.chronodawn.entities.bosses`（7 类）
- `com.chronodawn.entities.mobs`（21 类）
- `com.chronodawn.entities.projectiles`（3 类）
- `com.chronodawn.events`（6 类）
- `com.chronodawn.fluids`（2 类）
- `com.chronodawn.fluids.neoforge`（1 类）
- `com.chronodawn.gametest`（11 类）
- `com.chronodawn.gametest.boss`（1 类）
- `com.chronodawn.gui`（1 类）
- `com.chronodawn.gui.data`（5 类）
- `com.chronodawn.gui.widgets`（2 类）
- `com.chronodawn.items`（15 类）
- `com.chronodawn.items.artifacts`（8 类）
- `com.chronodawn.items.base`（4 类）
- `com.chronodawn.items.boats`（1 类）
- `com.chronodawn.items.boss`（2 类）
- `com.chronodawn.items.combat`（1 类）
- `com.chronodawn.items.consumables`（19 类）
- `com.chronodawn.items.equipment`（20 类）
- `com.chronodawn.items.quest`（1 类）
- `com.chronodawn.items.shield`（3 类）
- `com.chronodawn.items.tools`（2 类）
- `com.chronodawn.mixin`（12 类）
- `com.chronodawn.mixin.client`（4 类）
- `com.chronodawn.neoforge`（2 类）
- `com.chronodawn.neoforge.anvil`（1 类）
- `com.chronodawn.neoforge.client`（2 类）
- `com.chronodawn.neoforge.event`（3 类）
- `com.chronodawn.neoforge.gametest`（1 类）
- `com.chronodawn.neoforge.loot`（1 类）
- `com.chronodawn.neoforge.mixin`（2 类）
- `com.chronodawn.neoforge.platform`（1 类）
- `com.chronodawn.neoforge.registry`（3 类）
- `com.chronodawn.network`（2 类）
- `com.chronodawn.platform`（1 类）
- `com.chronodawn.registry`（17 类）
- `com.chronodawn.version`（2 类）
- `com.chronodawn.worldgen.decorators`（1 类）
- `com.chronodawn.worldgen.features`（5 类）
- `com.chronodawn.worldgen.processors`（2 类）
- `com.chronodawn.worldgen.protection`（2 类）
- `com.chronodawn.worldgen.runtime`（5 类）
- `com.chronodawn.worldgen.runtime.neoforge`（1 类）
- `com.chronodawn.worldgen.spawning`（9 类）
