# The Twilight Forest（`twilightforest`）

> jar：`[暮色森林]twilightforest.jar` ｜ 版本：`4.8.3345` ｜ 作者：Benimatic, AtomicBlom, Drullkus, Killer_Demon, quadraxis, Tamaized, williewillus, Andromander, GizmoTheMoonPig, jodlodi,
> 规模：**1475 个类 / 160 个包 / 176600 行** ｜ 事件处理类 2 ｜ Mixin 0 ｜ 注册/管理类 31

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `twilightforest.block` | 115 | `CandelabraBlock`、`VerticalHollowLogBlock`、`AbstractSkullCandleBlock` |
| `twilightforest.item` | 60 | `MoonwormQueenItem`、`LifedrainScepterItem`、`BrittleFlaskItem` |
| `twilightforest.client.model.entity` | 60 | `HydraModel`、`QuestRamModel`、`SpikeBlockModel` |
| `twilightforest.client.renderer.entity` | 55 | `MagicPaintingRenderer`、`BlockChainGoblinRenderer`、`BlockChainRenderer` |
| `twilightforest.init` | 49 | `TFConfiguredFeatures`、`TFBlocks`、`TFPlacedFeatures` |
| `twilightforest.entity.ai.goal` | 43 | `AvoidAnyEntityGoal`、`PhantomUpdateFormationAndMoveGoal`、`NagaMovementPattern` |
| `twilightforest.entity.monster` | 42 | `BlockChainGoblin`、`Kobold`、`Wraith` |
| `twilightforest.world.components.structures.finalcastle` | 31 | `FinalCastleMazeTower13Component`、`FinalCastleMainComponent`、`FinalCastleDungeonRoom31Component` |
| `twilightforest.network` | 29 | `SyncUncraftingTableConfigPacket`、`SetMasonJarItemPacket`、`ParticlePacket` |
| `twilightforest.util` | 29 | `TFItemStackUtils`、`BoundingBoxUtils`、`BlockCapabilityDirectionalCache` |
| `twilightforest.world.components.structures.minotaurmaze` | 27 | `MinotaurMazeComponent`、`MazeDeadEndFountainLavaComponent`、`MazeDeadEndPaintingComponent` |
| `twilightforest.world.components.structures.stronghold` | 25 | `KnightStrongholdComponent`、`StrongholdPieceWeight`、`StrongholdBalconyRoomComponent` |
| `twilightforest.block.entity` | 23 | `MasonJarBlockEntity`、`DryingRackBlockEntity`、`SkullChestBlockEntity` |
| `twilightforest.client.particle` | 23 | `SortingParticle`、`CustomTextureParticle`、`LeafParticle` |
| `twilightforest.data` | 21 | `DataGenerators`、`BlockLootTables`、`BlockstateGenerator` |
| `twilightforest.world.components.feature` | 21 | `BlockSpikeFeature`、`FoundationFeature`、`FallenHollowLogFeature` |
| `twilightforest.world.components.structures.lichtowerrevamp` | 20 | `LichTowerWingRoom`、`LichTowerUtil`、`LichPerimeterFence` |
| `twilightforest.entity.projectile` | 20 | `ChainBlock`、`CubeOfAnnihilation`、`FallingIce` |
| `twilightforest.world.components.structures.type` | 20 | `HollowTreeStructure`、`FallenTrunkStructure`、`HollowHillStructure` |
| `twilightforest.entity.boss` | 19 | `Lich`、`HydraHeadContainer`、`Hydra` |
| `twilightforest.world.components.structures` | 18 | `TFStructureComponentOld`、`TFMaze`、`StructureSpeleothemConfig` |
| `twilightforest.world.components.structures.util` | 18 | `TemplateMarkerHandlerList`、`ConfigurableSpawns`、`TemplatePoolInstance` |
| `twilightforest.world.components.structures.courtyard` | 18 | `StructureMazeGenerator`、`NagaCourtyardHedgeCapComponent`、`CourtyardWallCornerInner` |
| `twilightforest.world.components.structures.darktower` | 17 | `DarkTowerMainComponent`、`DarkTowerWingComponent`、`DarkTowerEntranceComponent` |
| `twilightforest.world.components.structures.lichtower` | 16 | `TowerWingComponent`、`TowerMainComponent`、`TowerRoofComponent` |
| `twilightforest.command` | 16 | `CountLootCommand`、`MapBiomesCommand`、`SinisterSpawnerCommand` |
| `twilightforest.client.renderer.block` | 16 | `JarRenderer`、`SkullChestRenderer`、`ReactorDebrisRenderer` |
| `twilightforest.client` | 15 | `UncraftingScreen`、`MagicPaintingTextureManager`、`FoliageColorHandler` |
| `twilightforest.world.components.feature.trees` | 15 | `HollowTreeFeature`、`SnowTreeFeature`、`MegaCanopyTreeFeature` |
| `twilightforest.world.components.processors` | 15 | `InfestBlocksProcessor`、`SpawnerProcessor`、`SmartGrassProcessor` |
| `twilightforest.init.custom` | 14 | `TravellersModifiersManager`、`TemplateMarkerHandlers`、`StructureSpeleothemConfigs` |
| `twilightforest.entity.passive` | 13 | `QuestRam`、`DwarfRabbit`、`FlyingBird` |
| `twilightforest.entity` | 13 | `MagicPainting`、`SpikeBlock`、`SlideBlock` |
| `twilightforest.beanification` | 13 | `Configurable`、`BeanContext`、`BeanLifeCycle` |
| `twilightforest.world.components.structures.icetower.floordecorators` | 13 | `IFloorDecorator`、`FloorPillarPlatforms`、`FloorPillarParkour` |
| `twilightforest.block.entity.spawner` | 12 | `SinisterSpawnerBlockEntity`、`BossSpawnerBlockEntity`、`KnightPhantomSpawnerBlockEntity` |
| `twilightforest.compat.emi.recipes` | 12 | `EmiOminousFireRecipe`、`TFEmiRecipe`、`EmiEmperorsClothRecipe` |
| `twilightforest.data.custom` | 11 | `UncraftingRecipeBuilder`、`ScepterRecipeBuilder`、`CartesianShapedRecipeBuilder` |
| `twilightforest.item.recipe` | 11 | `NoTemplateSmithingRecipe`、`ScepterRepairRecipe`、`UncraftingRecipe` |
| `twilightforest.enums` | 10 | `BlockLoggingEnum`、`Diagonals`、`BrazierLight` |
| … | | 其余 120 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientRegistrationEvents` | class | — | Component | 22 | 1220 | [ClientRegistrationEvents.java](twilightforest/client/event/ClientRegistrationEvents.java) |
| `RegistrationEvents` | class | — | Component | 13 | 1080 | [RegistrationEvents.java](twilightforest/events/RegistrationEvents.java) |
| `TwilightForestMod` | class | — | Configurable、Mod | 5 | 158 | [TwilightForestMod.java](twilightforest/TwilightForestMod.java) |
| `EntityEvents` | class | EndermanTakeBlockGoal | Component | 29 | 592 | [EntityEvents.java](twilightforest/events/EntityEvents.java) |
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 89 | [DataGenerators.java](twilightforest/data/DataGenerators.java) |
| `ClientGameEvents` | class | — | twilightforest.beanification.Component | 17 | 405 | [ClientGameEvents.java](twilightforest/client/event/ClientGameEvents.java) |
| `TravellersGearEvents` | class | — | Component | 22 | 433 | [TravellersGearEvents.java](twilightforest/events/TravellersGearEvents.java) |
| `TravellersClientEvents` | class | — | Component | 17 | 303 | [TravellersClientEvents.java](twilightforest/client/event/TravellersClientEvents.java) |
| `HostileMountEvents` | class | — | Component | 7 | 88 | [HostileMountEvents.java](twilightforest/events/HostileMountEvents.java) |
| `BlockLootTables` | class | BlockLootSubProvider | — | 18 | 1172 | [BlockLootTables.java](twilightforest/data/BlockLootTables.java) |
| `BlockstateGenerator` | class | BlockModelBuilders | — | 52 | 3990 | [BlockstateGenerator.java](twilightforest/data/BlockstateGenerator.java) |
| `ItemModelGenerator` | class | ItemModelProvider | — | 39 | 1423 | [ItemModelGenerator.java](twilightforest/data/ItemModelGenerator.java) |
| `ProgressionEvents` | class | — | Component | 16 | 291 | [ProgressionEvents.java](twilightforest/events/ProgressionEvents.java) |
| `CapabilityEvents` | class | — | Component | 8 | 120 | [CapabilityEvents.java](twilightforest/events/CapabilityEvents.java) |
| `BlockModelBuilders` | class | BlockModelHelpers | — | 28 | 1655 | [BlockModelBuilders.java](twilightforest/data/helpers/BlockModelBuilders.java) |
| `Lich` | class | BaseTFBoss | — | 87 | 1193 | [Lich.java](twilightforest/entity/boss/Lich.java) |
| `CharmEvents` | class | — | Component | 12 | 337 | [CharmEvents.java](twilightforest/events/CharmEvents.java) |
| `ToolEvents` | class | — | Component | 11 | 259 | [ToolEvents.java](twilightforest/events/ToolEvents.java) |
| `LichTowerWingRoom` | class | TwilightJigsawPiece | — | 44 | 1089 | [LichTowerWingRoom.java](twilightforest/world/components/structures/lichtowerrevamp/LichTowerWingRoom.java) |
| `TowerWingComponent` | class | TFStructureComponentOld | — | 68 | 1484 | [TowerWingComponent.java](twilightforest/world/components/structures/lichtower/TowerWingComponent.java) |
| `DarkTowerMainComponent` | class | DarkTowerWingComponent | — | 40 | 1284 | [DarkTowerMainComponent.java](twilightforest/world/components/structures/darktower/DarkTowerMainComponent.java) |
| `UncraftingMenu` | class | RecipeBookMenu | — | 38 | 588 | [UncraftingMenu.java](twilightforest/inventory/UncraftingMenu.java) |
| `HydraHeadContainer` | class | — | — | 39 | 919 | [HydraHeadContainer.java](twilightforest/entity/boss/HydraHeadContainer.java) |
| `CandelabraBlock` | class | BaseEntityBlock | — | 33 | 492 | [CandelabraBlock.java](twilightforest/block/CandelabraBlock.java) |
| `EntityLootTables` | class | EntityLootSubProvider | — | 5 | 965 | [EntityLootTables.java](twilightforest/data/EntityLootTables.java) |
| `CraftingDataHelper` | class | RecipeProvider | — | 50 | 496 | [CraftingDataHelper.java](twilightforest/data/helpers/CraftingDataHelper.java) |
| `BlockModelHelpers` | class | BlockStateProvider | — | 22 | 725 | [BlockModelHelpers.java](twilightforest/data/helpers/BlockModelHelpers.java) |
| `DarkTowerWingComponent` | class | TowerWingComponent | — | 48 | 899 | [DarkTowerWingComponent.java](twilightforest/world/components/structures/darktower/DarkTowerWingComponent.java) |
| `IceTowerWingComponent` | class | TowerWingComponent | — | 35 | 844 | [IceTowerWingComponent.java](twilightforest/world/components/structures/icetower/IceTowerWingComponent.java) |
| `TravellersModifiersManager` | class | SimplePreparableReloadListener | — | 23 | 394 | [TravellersModifiersManager.java](twilightforest/init/custom/TravellersModifiersManager.java) |
| `Hydra` | class | BaseTFBoss | — | 53 | 727 | [Hydra.java](twilightforest/entity/boss/Hydra.java) |
| `Naga` | class | BaseTFBoss | — | 48 | 603 | [Naga.java](twilightforest/entity/boss/Naga.java) |
| `KnightPhantom` | class | BaseTFBoss | — | 57 | 766 | [KnightPhantom.java](twilightforest/entity/boss/KnightPhantom.java) |
| `VerticalHollowLogBlock` | class | Block | Configurable | 8 | 125 | [VerticalHollowLogBlock.java](twilightforest/block/VerticalHollowLogBlock.java) |
| `MasonJarBlockEntity` | class | JarBlockEntity | — | 20 | 209 | [MasonJarBlockEntity.java](twilightforest/block/entity/MasonJarBlockEntity.java) |
| `DryingRackBlockEntity` | class | BlockEntity | — | 21 | 226 | [DryingRackBlockEntity.java](twilightforest/block/entity/DryingRackBlockEntity.java) |
| `MiscEvents` | class | — | Component | 5 | 126 | [MiscEvents.java](twilightforest/events/MiscEvents.java) |
| `TFStructureComponentOld` | class | TFStructureComponent | — | 50 | 601 | [TFStructureComponentOld.java](twilightforest/world/components/structures/TFStructureComponentOld.java) |
| `BiomeHelper` | class | — | — | 49 | 560 | [BiomeHelper.java](twilightforest/world/registration/biomes/BiomeHelper.java) |
| `CloudEvents` | class | — | — | 2 | 305 | [CloudEvents.java](twilightforest/client/event/CloudEvents.java) |
| `OverlayHandler` | class | — | — | 18 | 363 | [OverlayHandler.java](twilightforest/client/event/OverlayHandler.java) |
| `TFConfiguredFeatures` | class | — | — | 3 | 1191 | [TFConfiguredFeatures.java](twilightforest/init/TFConfiguredFeatures.java) |
| `SnowQueen` | class | BaseTFBoss | — | 38 | 422 | [SnowQueen.java](twilightforest/entity/boss/SnowQueen.java) |
| `UrGhast` | class | BaseTFBoss | — | 51 | 545 | [UrGhast.java](twilightforest/entity/boss/UrGhast.java) |
| `SkullChestBlockEntity` | class | RandomizableContainerBlockEntity | — | 22 | 192 | [SkullChestBlockEntity.java](twilightforest/block/entity/SkullChestBlockEntity.java) |
| `ChestLootTables` | record | LootTableSubProvider | — | 1 | 1641 | [ChestLootTables.java](twilightforest/data/ChestLootTables.java) |
| `CraftingGenerator` | class | CraftingDataHelper | — | 19 | 1619 | [CraftingGenerator.java](twilightforest/data/CraftingGenerator.java) |
| `TFTeleporter` | class | — | — | 32 | 593 | [TFTeleporter.java](twilightforest/world/TFTeleporter.java) |
| `LootMarkerHandler` | record | TemplateMarkerHandler | — | 2 | 46 | [LootMarkerHandler.java](twilightforest/world/components/structures/markerhandler/LootMarkerHandler.java) |
| `ForceFieldModelBuilder` | class | — | — | 42 | 428 | [ForceFieldModelBuilder.java](twilightforest/client/model/block/forcefield/ForceFieldModelBuilder.java) |
| `TFBlocks` | class | — | — | 9 | 2955 | [TFBlocks.java](twilightforest/init/TFBlocks.java) |
| `SyncUncraftingTableConfigPacket` | record | CustomPacketPayload | — | 3 | 77 | [SyncUncraftingTableConfigPacket.java](twilightforest/network/SyncUncraftingTableConfigPacket.java) |
| `BlockChainGoblin` | class | Monster | — | 24 | 244 | [BlockChainGoblin.java](twilightforest/entity/monster/BlockChainGoblin.java) |
| `ChainBlock` | class | ThrowableProjectile | — | 21 | 335 | [ChainBlock.java](twilightforest/entity/projectile/ChainBlock.java) |
| `QuestRam` | class | Animal | — | 31 | 290 | [QuestRam.java](twilightforest/entity/passive/QuestRam.java) |
| `AlphaYeti` | class | BaseTFBoss | — | 36 | 346 | [AlphaYeti.java](twilightforest/entity/boss/AlphaYeti.java) |
| `BaseTFBoss` | class | Monster | — | 38 | 266 | [BaseTFBoss.java](twilightforest/entity/boss/BaseTFBoss.java) |
| `AbstractSkullCandleBlock` | class | BaseEntityBlock | — | 20 | 346 | [AbstractSkullCandleBlock.java](twilightforest/block/AbstractSkullCandleBlock.java) |
| `WroughtIronFenceBlock` | class | Block | — | 22 | 317 | [WroughtIronFenceBlock.java](twilightforest/block/WroughtIronFenceBlock.java) |
| `SkullChestBlock` | class | BaseEntityBlock | — | 20 | 235 | [SkullChestBlock.java](twilightforest/block/SkullChestBlock.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `DataGenerators` | 1 | `twilightforest.data` | [DataGenerators.java](twilightforest/data/DataGenerators.java) |
| `HostileMountEvents` | 1 | `twilightforest.events` | [HostileMountEvents.java](twilightforest/events/HostileMountEvents.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TwilightForestMod` | Configurable;Mod | [TwilightForestMod.java](twilightforest/TwilightForestMod.java) |
| `VerticalHollowLogBlock` | Configurable | [VerticalHollowLogBlock.java](twilightforest/block/VerticalHollowLogBlock.java) |
| `TFConfiguredFeatures` | — | [TFConfiguredFeatures.java](twilightforest/init/TFConfiguredFeatures.java) |
| `SyncUncraftingTableConfigPacket` | — | [SyncUncraftingTableConfigPacket.java](twilightforest/network/SyncUncraftingTableConfigPacket.java) |
| `TFConfig` | — | [TFConfig.java](twilightforest/config/TFConfig.java) |
| `DrinkFromFlaskTrigger` | Configurable | [DrinkFromFlaskTrigger.java](twilightforest/advancements/DrinkFromFlaskTrigger.java) |
| `TFClientConfig` | — | [TFClientConfig.java](twilightforest/config/TFClientConfig.java) |
| `StructureSpeleothemConfig` | — | [StructureSpeleothemConfig.java](twilightforest/world/components/structures/StructureSpeleothemConfig.java) |
| `TFCommonConfig` | — | [TFCommonConfig.java](twilightforest/config/TFCommonConfig.java) |
| `RuinedFoundationConfig` | — | [RuinedFoundationConfig.java](twilightforest/world/components/feature/config/RuinedFoundationConfig.java) |
| `TFTreeFeatureConfig` | — | [TFTreeFeatureConfig.java](twilightforest/world/components/feature/config/TFTreeFeatureConfig.java) |
| `TreeConfigurations` | — | [TreeConfigurations.java](twilightforest/world/registration/TreeConfigurations.java) |
| `StructureSpeleothemConfigs` | — | [StructureSpeleothemConfigs.java](twilightforest/init/custom/StructureSpeleothemConfigs.java) |
| `Configurable` | Inherited;Retention;Target | [Configurable.java](twilightforest/beanification/Configurable.java) |
| `BeanContextConfig` | — | [BeanContextConfig.java](twilightforest/beanification/internal/BeanContextConfig.java) |
| `ConfigSetup` | — | [ConfigSetup.java](twilightforest/config/ConfigSetup.java) |
| `ConfigComments` | — | [ConfigComments.java](twilightforest/config/ConfigComments.java) |
| `SpeleothemVarietyConfig` | — | [SpeleothemVarietyConfig.java](twilightforest/data/custom/stalactites/entry/SpeleothemVarietyConfig.java) |
| `ConfigurableSpawns` | — | [ConfigurableSpawns.java](twilightforest/world/components/structures/util/ConfigurableSpawns.java) |
| `BranchesConfig` | — | [BranchesConfig.java](twilightforest/world/components/feature/trees/treeplacers/BranchesConfig.java) |
| `BerryBushConfig` | — | [BerryBushConfig.java](twilightforest/world/components/feature/config/BerryBushConfig.java) |
| `ThornsConfig` | — | [ThornsConfig.java](twilightforest/world/components/feature/config/ThornsConfig.java) |
| `WeightedListFeatureConfig` | — | [WeightedListFeatureConfig.java](twilightforest/world/components/feature/config/WeightedListFeatureConfig.java) |
| `HollowLogConfig` | — | [HollowLogConfig.java](twilightforest/world/components/feature/config/HollowLogConfig.java) |
| `RootConfig` | — | [RootConfig.java](twilightforest/world/components/feature/config/RootConfig.java) |
| `SwizzleConfig` | — | [SwizzleConfig.java](twilightforest/world/components/feature/config/SwizzleConfig.java) |

## 按包浏览

- `twilightforest`（3 类）
- `twilightforest.advancements`（7 类）
- `twilightforest.advancements.predicate`（1 类）
- `twilightforest.asmhooks`（9 类）
- `twilightforest.beanification`（13 类）
- `twilightforest.beanification.internal`（5 类）
- `twilightforest.beanification.processors`（2 类）
- `twilightforest.beanification.processors.construct`（1 类）
- `twilightforest.beanification.processors.finalize`（1 类）
- `twilightforest.beanification.processors.gather`（2 类）
- `twilightforest.beanification.processors.inject`（2 类）
- `twilightforest.beanification.processors.inspect`（2 类）
- `twilightforest.beanification.processors.staticinject`（2 类）
- `twilightforest.beanification.processors.validate`（1 类）
- `twilightforest.block`（115 类）
- `twilightforest.block.entity`（23 类）
- `twilightforest.block.entity.bookshelf`（2 类）
- `twilightforest.block.entity.spawner`（12 类）
- `twilightforest.client`（15 类）
- `twilightforest.client.event`（8 类）
- `twilightforest.client.model`（1 类）
- `twilightforest.client.model.armor`（8 类）
- `twilightforest.client.model.block`（3 类）
- `twilightforest.client.model.block.aurorablock`（4 类）
- `twilightforest.client.model.block.carpet`（4 类）
- `twilightforest.client.model.block.connected`（5 类）
- `twilightforest.client.model.block.forcefield`（4 类）
- `twilightforest.client.model.block.giantblock`（4 类）
- `twilightforest.client.model.block.patch`（3 类）
- `twilightforest.client.model.entity`（60 类）
- `twilightforest.client.model.item`（2 类）
- `twilightforest.client.overlay`（4 类）
- `twilightforest.client.overlay.display`（6 类）
- `twilightforest.client.particle`（23 类）
- `twilightforest.client.particle.data`（1 类）
- `twilightforest.client.renderer`（3 类）
- `twilightforest.client.renderer.armor`（2 类）
- `twilightforest.client.renderer.block`（16 类）
- `twilightforest.client.renderer.entity`（55 类）
- `twilightforest.client.renderer.entity.layers`（2 类）
- `twilightforest.client.renderer.map`（2 类）
- `twilightforest.client.renderer.tooltip`（3 类）
- `twilightforest.command`（16 类）
- `twilightforest.compat`（2 类）
- `twilightforest.compat.common`（1 类）
- `twilightforest.compat.curios`（1 类）
- `twilightforest.compat.curios.model`（1 类）
- `twilightforest.compat.curios.renderer`（3 类）
- `twilightforest.compat.emi`（3 类）
- `twilightforest.compat.emi.recipes`（12 类）
- `twilightforest.compat.emi.widget`（4 类）
- `twilightforest.compat.jade`（4 类）
- `twilightforest.compat.jei`（3 类）
- `twilightforest.compat.jei.categories`（5 类）
- `twilightforest.compat.jei.extension`（7 类）
- `twilightforest.compat.jei.renderers`（4 类）
- `twilightforest.compat.jei.subtype`（1 类）
- `twilightforest.compat.jei.util`（4 类）
- `twilightforest.compat.rei`（2 类）
- `twilightforest.compat.rei.categories`（5 类）
- `twilightforest.compat.rei.displays`（6 类）
- `twilightforest.compat.rei.entries`（5 类）
- `twilightforest.compat.rei.fillers`（2 类）
- `twilightforest.compat.rei.filter`（2 类）
- `twilightforest.compat.top`（4 类）
- `twilightforest.components.block`（1 类）
- `twilightforest.components.entity`（10 类）
- `twilightforest.components.item`（7 类）
- `twilightforest.config`（5 类）
- `twilightforest.data`（21 类）
- `twilightforest.data.custom`（11 类）
- `twilightforest.data.custom.stalactites`（2 类）
- `twilightforest.data.custom.stalactites.entry`（3 类）
- `twilightforest.data.custom.structuredefinitions`（5 类）
- `twilightforest.data.helpers`（8 类）
- `twilightforest.data.tags`（8 类）
- `twilightforest.data.tags.compat`（3 类）
- `twilightforest.dispenser`（6 类）
- `twilightforest.enchantment`（3 类）
- `twilightforest.entity`（13 类）
- `twilightforest.entity.ai.control`（2 类）
- `twilightforest.entity.ai.goal`（43 类）
- `twilightforest.entity.boss`（19 类）
- `twilightforest.entity.boss.bar`（2 类）
- `twilightforest.entity.monster`（42 类）
- `twilightforest.entity.passive`（13 类）
- `twilightforest.entity.passive.quest`（1 类）
- `twilightforest.entity.passive.quest.ram`（2 类）
- `twilightforest.entity.projectile`（20 类）
- `twilightforest.enums`（10 类）
- `twilightforest.enums.extensions`（5 类）
- `twilightforest.events`（9 类）
- `twilightforest.init`（49 类）
- `twilightforest.init.custom`（14 类）
- `twilightforest.inventory`（5 类）
- `twilightforest.inventory.slot`（3 类）
- `twilightforest.item`（60 类）
- `twilightforest.item.mapdata`（2 类）
- `twilightforest.item.recipe`（11 类）
- `twilightforest.item.recipe.travellers`（4 类）
- `twilightforest.item.travellers_gear`（4 类）
- `twilightforest.item.travellers_gear.modifiers`（9 类）
- `twilightforest.item.travellers_gear.modifiers.display`（1 类）
- `twilightforest.loot`（4 类）
- `twilightforest.loot.conditions`（4 类）
- `twilightforest.loot.modifiers`（2 类）
- `twilightforest.network`（29 类）
- `twilightforest.potions`（1 类）
- `twilightforest.util`（29 类）
- `twilightforest.util.datamaps`（4 类）
- `twilightforest.util.entities`（4 类）
- `twilightforest.util.features`（3 类）
- `twilightforest.util.iterators`（5 类）
- `twilightforest.util.jigsaw`（3 类）
- `twilightforest.util.landmarks`（2 类）
- `twilightforest.util.multiparts`（2 类）
- `twilightforest.util.woods`（2 类）
- `twilightforest.world`（3 类）
- `twilightforest.world.components`（2 类）
- `twilightforest.world.components.biomesources`（1 类）
- `twilightforest.world.components.chunkblanketing`（4 类）
- `twilightforest.world.components.chunkgenerators`（9 类）
- `twilightforest.world.components.feature`（21 类）
- `twilightforest.world.components.feature.config`（8 类）
- `twilightforest.world.components.feature.templates`（7 类）
- `twilightforest.world.components.feature.trees`（15 类）
- `twilightforest.world.components.feature.trees.growers`（2 类）
- `twilightforest.world.components.feature.trees.treeplacers`（8 类）
- `twilightforest.world.components.feature.trollcave`（3 类）
- `twilightforest.world.components.layer`（10 类）
- `twilightforest.world.components.layer.vanillalegacy`（5 类）
- `twilightforest.world.components.layer.vanillalegacy.area`（1 类）
- `twilightforest.world.components.layer.vanillalegacy.context`（4 类）
- `twilightforest.world.components.layer.vanillalegacy.traits`（7 类）
- `twilightforest.world.components.placements`（3 类）
- `twilightforest.world.components.processors`（15 类）
- `twilightforest.world.components.structures`（18 类）
- `twilightforest.world.components.structures.camp`（1 类）
- `twilightforest.world.components.structures.courtyard`（18 类）
- `twilightforest.world.components.structures.darktower`（17 类）
- `twilightforest.world.components.structures.fallentrunk`（3 类）
- `twilightforest.world.components.structures.finalcastle`（31 类）
- `twilightforest.world.components.structures.hollowtree`（7 类）
- `twilightforest.world.components.structures.icetower`（9 类）
- `twilightforest.world.components.structures.icetower.floordecorators`（13 类）
- `twilightforest.world.components.structures.lichtower`（16 类）
- `twilightforest.world.components.structures.lichtowerrevamp`（20 类）
- `twilightforest.world.components.structures.markerhandler`（8 类）
- `twilightforest.world.components.structures.minotaurmaze`（27 类）
- `twilightforest.world.components.structures.mushroomtower`（6 类）
- `twilightforest.world.components.structures.placements`（2 类）
- `twilightforest.world.components.structures.selectors`（6 类）
- `twilightforest.world.components.structures.start`（1 类）
- `twilightforest.world.components.structures.stronghold`（25 类）
- `twilightforest.world.components.structures.trollcave`（8 类）
- `twilightforest.world.components.structures.type`（20 类）
- `twilightforest.world.components.structures.util`（18 类）
- `twilightforest.world.registration`（2 类）
- `twilightforest.world.registration.biomes`（2 类）
- `twilightforest.world.registration.surface_rules`（1 类）
