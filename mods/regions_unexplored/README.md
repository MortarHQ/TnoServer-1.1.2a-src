# Regions Unexplored（`regions_unexplored`）

> jar：`[群系]regionsunexplored.jar` ｜ 版本：`0.6.2` ｜ 作者：?
> 规模：**310 个类 / 73 个包 / 36668 行** ｜ 事件处理类 2 ｜ Mixin 9 ｜ 注册/管理类 5

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.regions_unexplored.world.level.feature.tree` | 28 | `SakuraTreeFeature`、`StrippedPineTreeFeature`、`GiantCypressTreeFeature` |
| `net.regions_unexplored.world.level.feature` | 20 | `RockPillarFeature`、`SeaRockFeature`、`NetherRockFeature` |
| `net.regions_unexplored.registry` | 15 | `RUSoundEvents`、`RUBlocks`、`RUEntityTypes` |
| `net.regions_unexplored.registry.data` | 12 | `RUConfiguredFeatures`、`RUEntityTypeIds`、`RUBlockIds` |
| `net.regions_unexplored.world.level.block.plant.tall` | 12 | `RuDoubleFlowerBlock`、`MangroveShrubBlock`、`CattailBlock` |
| `net.regions_unexplored.world.level.block.plant.other` | 11 | `SpanishMossBlock`、`DropleafBlock`、`KapokVinesBlock` |
| `net.regions_unexplored.worldgen.foliageplacer` | 10 | `RUFoliagePlacerUtils`、`SakuraFoliagePlacer`、`FancyPineFoliagePlacer` |
| `net.regions_unexplored.world.level.block.plant.grass` | 8 | `RuSandyPlantBlock`、`AshenGrassBlock`、`RuPlantBlock` |
| `net.regions_unexplored.world.level.feature.configuration` | 8 | `HyacinthStockConfiguration`、`SmallBushConfiguration`、`RUTreeConfiguration` |
| `net.regions_unexplored.world.level.block.cave` | 7 | `PointedRedstoneBlock`、`HangingPrismariteBlock`、`PrismariteBlock` |
| `net.regions_unexplored.mixin` | 7 | `NetherrackBlockMixin`、`EatBlockGoalMixin`、`VillagerProfessionAccessor` |
| `net.regions_unexplored.world.level.block.other` | 7 | `IcicleBlock`、`AshBlock`、`HyacinthLampBlock` |
| `net.regions_unexplored.module.platform` | 7 | `ParticleRegistry`、`ConfigHelper`、`BlockHelper` |
| `net.regions_unexplored.world.level.block.plant.nether` | 7 | `DorcelPlantBlock`、`NetherGroundCoverBlock`、`HangingEarlightBlock` |
| `net.regions_unexplored.world.level.block.wood` | 6 | `BambooLogBlock`、`SmallOakLogBlock`、`StrippedBambooLogBlock` |
| `net.regions_unexplored.block.type.leaves` | 6 | `HangingVinesBlock`、`AppleLeavesBlock`、`JoshuaLeavesBlock` |
| `net.regions_unexplored.worldgen.treedecorator` | 6 | `WillowTrunkDecorator`、`HangingVinesDecorator`、`AttachedToLogsDecorator` |
| `net.regions_unexplored.block.type.dirt` | 5 | `RUDirtPathBlock`、`RUGrassBlock`、`RUFarmlandBlock` |
| `net.regions_unexplored.world.level.block.plant.sapling` | 5 | `BaobabSaplingBlock`、`RuUltraFromSuperSaplingBlock`、`RuCactusSaplingBlock` |
| `net.regions_unexplored.client.particle` | 5 | `RULeavesParticle`、`RUParticleProviders`、`MycotoxicSporeParticle` |
| `net.regions_unexplored` | 4 | `RegionsUnexploredNeo`、`RegionsUnexploredNeoClient`、`RUConfigCondition` |
| `net.regions_unexplored.block` | 4 | `RUBlockUtils`、`BlockFactory`、`RuBlockSetType` |
| `net.regions_unexplored.block.set` | 4 | `WoodSet`、`NaturalSet`、`ColoredSet` |
| `net.regions_unexplored.block.compat` | 4 | `BlockToolCompat`、`FlammableBlocks`、`CompostableBlocks` |
| `net.regions_unexplored.world.level.block.plant.aquatic` | 4 | `FloweringLilyBlock`、`GiantLilyBlock`、`TallHyacinthStockBlock` |
| `net.regions_unexplored.world.level.block.plant.flower` | 4 | `GroundCoverBlock`、`RULeafLitterBlock`、`CaveFlowerBlock` |
| `net.regions_unexplored.world.level.feature.bioshroom` | 4 | `GiantBlueBioshroomFeature`、`GiantYellowBioshroomFeature`、`GiantGreenBioshroomFeature` |
| `net.regions_unexplored.world.level.block.state.properties` | 4 | `RuBlockStateProperties`、`SaguaroCactusShape`、`HangingPrismariteShape` |
| `net.regions_unexplored.registry.tag` | 4 | `BackportedBlockTags`、`RUItemTags`、`RUBlockTags` |
| `net.regions_unexplored.worldgen.trunkplacer` | 4 | `RedwoodTrunkPlacer`、`RUTrunkPlacer`、`MagnoliaTrunkPlacer` |
| `net.regions_unexplored.client` | 3 | `ParticleRegistration`、`TintRegistration`、`RegionsUnexploredClient` |
| `net.regions_unexplored.client.gui` | 3 | `ConfigList`、`RUConfigScreen`、`ConfigListBuilder` |
| `net.regions_unexplored.config.json5` | 3 | `Json5Ops`、`CommentedMapCodec`、`CommentedUnboundedMapCodec` |
| `net.regions_unexplored.util` | 3 | `BlockCompatUtil`、`RUUtils`、`TrunkPlacerDirtUtil` |
| `net.regions_unexplored.worldgen.feature.config` | 3 | `FallenTreeConfig`、`RockFeatureConfig`、`CarvedLimitedPoolFeatureConfig` |
| `net.regions_unexplored.block.sapling` | 3 | `RuUltraFromMegaTreeGrower`、`RuUltraFromSuperTreeGrower`、`RUTreeGrowers` |
| `net.regions_unexplored.worldgen.feature` | 3 | `RUFallenTreeFeature`、`CarvedLimitedPoolFeature`、`RURockFeature` |
| `net.regions_unexplored.client.particle.spore` | 3 | `GroundSporeParticle`、`FloatingSporeParticle`、`FallingSporeParticle` |
| `net.regions_unexplored.config.state.common` | 2 | `RUCommonConfig`、`BiomeTarget` |
| `net.regions_unexplored.world.level.feature.tree.nether` | 2 | `TallBrimWillowFeature`、`BrimWillowFeature` |
| … | | 其余 33 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ParticleRegistration` | class | — | EventBusSubscriber | 1 | 23 | [ParticleRegistration.java](net/regions_unexplored/client/ParticleRegistration.java) |
| `RegionsUnexploredNeo` | class | — | Mod | 5 | 86 | [RegionsUnexploredNeo.java](net/regions_unexplored/RegionsUnexploredNeo.java) |
| `RegionsUnexploredNeoClient` | class | — | Mod | 4 | 63 | [RegionsUnexploredNeoClient.java](net/regions_unexplored/RegionsUnexploredNeoClient.java) |
| `RUBlockUtils` | class | — | — | 34 | 229 | [RUBlockUtils.java](net/regions_unexplored/block/RUBlockUtils.java) |
| `RUConfigHandler` | class | — | — | 5 | 120 | [RUConfigHandler.java](net/regions_unexplored/config/RUConfigHandler.java) |
| `PointedRedstoneBlock` | class | Block | — | 44 | 510 | [PointedRedstoneBlock.java](net/regions_unexplored/world/level/block/cave/PointedRedstoneBlock.java) |
| `SakuraTreeFeature` | class | Feature | — | 20 | 1002 | [SakuraTreeFeature.java](net/regions_unexplored/world/level/feature/tree/SakuraTreeFeature.java) |
| `ConfigList` | class | ContainerObjectSelectionList | — | 21 | 210 | [ConfigList.java](net/regions_unexplored/client/gui/ConfigList.java) |
| `NetherrackBlockMixin` | class | — | Mixin | 0 | 38 | [NetherrackBlockMixin.java](net/regions_unexplored/mixin/NetherrackBlockMixin.java) |
| `EatBlockGoalMixin` | class | — | Mixin | 0 | 72 | [EatBlockGoalMixin.java](net/regions_unexplored/mixin/EatBlockGoalMixin.java) |
| `Json5Ops` | class | AbstractStringBuilder | — | 39 | 417 | [Json5Ops.java](net/regions_unexplored/config/json5/Json5Ops.java) |
| `IcicleBlock` | class | Block | — | 27 | 327 | [IcicleBlock.java](net/regions_unexplored/world/level/block/other/IcicleBlock.java) |
| `StrippedPineTreeFeature` | class | Feature | — | 25 | 606 | [StrippedPineTreeFeature.java](net/regions_unexplored/world/level/feature/tree/StrippedPineTreeFeature.java) |
| `RUSoundEvents` | interface | — | — | 0 | 26 | [RUSoundEvents.java](net/regions_unexplored/registry/RUSoundEvents.java) |
| `WoodSet` | class | — | — | 32 | 289 | [WoodSet.java](net/regions_unexplored/block/set/WoodSet.java) |
| `RUCommonConfig` | class | StringRepresentable | — | 7 | 453 | [RUCommonConfig.java](net/regions_unexplored/config/state/common/RUCommonConfig.java) |
| `GiantCypressTreeFeature` | class | Feature | — | 20 | 672 | [GiantCypressTreeFeature.java](net/regions_unexplored/world/level/feature/tree/GiantCypressTreeFeature.java) |
| `LushPineTreeFeature` | class | Feature | — | 24 | 600 | [LushPineTreeFeature.java](net/regions_unexplored/world/level/feature/tree/LushPineTreeFeature.java) |
| `KapokTreeFeature` | class | Feature | — | 20 | 670 | [KapokTreeFeature.java](net/regions_unexplored/world/level/feature/tree/KapokTreeFeature.java) |
| `TallBrimWillowFeature` | class | Feature | — | 15 | 986 | [TallBrimWillowFeature.java](net/regions_unexplored/world/level/feature/tree/nether/TallBrimWillowFeature.java) |
| `RUBlocks` | interface | — | — | 0 | 1270 | [RUBlocks.java](net/regions_unexplored/registry/RUBlocks.java) |
| `RUConfigScreen` | class | Screen | — | 6 | 56 | [RUConfigScreen.java](net/regions_unexplored/client/gui/RUConfigScreen.java) |
| `VillagerProfessionAccessor` | interface | — | Mixin | 0 | 18 | [VillagerProfessionAccessor.java](net/regions_unexplored/mixin/VillagerProfessionAccessor.java) |
| `CropPlacerMixin` | class | — | Mixin | 0 | 26 | [CropPlacerMixin.java](net/regions_unexplored/mixin/CropPlacerMixin.java) |
| `TrunkPlacerDirtMixin` | class | — | Mixin | 0 | 40 | [TrunkPlacerDirtMixin.java](net/regions_unexplored/mixin/TrunkPlacerDirtMixin.java) |
| `WorldCarverMixin` | class | — | Mixin | 2 | 34 | [WorldCarverMixin.java](net/regions_unexplored/mixin/WorldCarverMixin.java) |
| `BiomeMixin` | class | — | Mixin | 2 | 31 | [BiomeMixin.java](net/regions_unexplored/mixin/BiomeMixin.java) |
| `HolderLookupMixin` | interface | — | Mixin | 1 | 23 | [HolderLookupMixin.java](net/regions_unexplored/mixin/removals/HolderLookupMixin.java) |
| `BiomeListMixin` | class | — | Mixin | 1 | 24 | [BiomeListMixin.java](net/regions_unexplored/mixin/removals/BiomeListMixin.java) |
| `RUClientConfig` | class | — | — | 0 | 74 | [RUClientConfig.java](net/regions_unexplored/config/state/client/RUClientConfig.java) |
| `BlackwoodTreeFeature` | class | Feature | — | 23 | 585 | [BlackwoodTreeFeature.java](net/regions_unexplored/world/level/feature/tree/BlackwoodTreeFeature.java) |
| `LarchTreeFeature` | class | Feature | — | 23 | 595 | [LarchTreeFeature.java](net/regions_unexplored/world/level/feature/tree/LarchTreeFeature.java) |
| `TintRegistration` | class | — | — | 2 | 26 | [TintRegistration.java](net/regions_unexplored/client/TintRegistration.java) |
| `ParticleRegistry` | class | — | — | 1 | 13 | [ParticleRegistry.java](net/regions_unexplored/module/platform/ParticleRegistry.java) |
| `BambooLogBlock` | class | Block | — | 15 | 180 | [BambooLogBlock.java](net/regions_unexplored/world/level/block/wood/BambooLogBlock.java) |
| `BaseDuskTrapBlock` | class | DoublePlantBlock | — | 15 | 153 | [BaseDuskTrapBlock.java](net/regions_unexplored/world/level/block/plant/dusktrap/BaseDuskTrapBlock.java) |
| `CypressTreeFeature` | class | Feature | — | 18 | 472 | [CypressTreeFeature.java](net/regions_unexplored/world/level/feature/tree/CypressTreeFeature.java) |
| `MegaBaobabTreeFeature` | class | Feature | — | 17 | 494 | [MegaBaobabTreeFeature.java](net/regions_unexplored/world/level/feature/tree/MegaBaobabTreeFeature.java) |
| `EucalyptusTreeFeature` | class | Feature | — | 18 | 486 | [EucalyptusTreeFeature.java](net/regions_unexplored/world/level/feature/tree/EucalyptusTreeFeature.java) |
| `UltraBaobabTreeFeature` | class | Feature | — | 17 | 525 | [UltraBaobabTreeFeature.java](net/regions_unexplored/world/level/feature/tree/UltraBaobabTreeFeature.java) |
| `BlockCompatUtil` | class | — | — | 4 | 36 | [BlockCompatUtil.java](net/regions_unexplored/util/BlockCompatUtil.java) |
| `ConfigHelper` | class | — | — | 1 | 11 | [ConfigHelper.java](net/regions_unexplored/module/platform/ConfigHelper.java) |
| `RUConfigCondition` | record | ICondition | — | 2 | 25 | [RUConfigCondition.java](net/regions_unexplored/RUConfigCondition.java) |
| `HangingVinesBlock` | class | Block | — | 13 | 111 | [HangingVinesBlock.java](net/regions_unexplored/block/type/leaves/HangingVinesBlock.java) |
| `RUDirtPathBlock` | class | Block | — | 10 | 94 | [RUDirtPathBlock.java](net/regions_unexplored/block/type/dirt/RUDirtPathBlock.java) |
| `RUGrassBlock` | class | SnowyDirtBlock | — | 11 | 160 | [RUGrassBlock.java](net/regions_unexplored/block/type/dirt/RUGrassBlock.java) |
| `RUFarmlandBlock` | class | FarmBlock | — | 10 | 110 | [RUFarmlandBlock.java](net/regions_unexplored/block/type/dirt/RUFarmlandBlock.java) |
| `BlockToolCompat` | class | — | — | 1 | 47 | [BlockToolCompat.java](net/regions_unexplored/block/compat/BlockToolCompat.java) |
| `NaturalSet` | class | — | — | 22 | 164 | [NaturalSet.java](net/regions_unexplored/block/set/NaturalSet.java) |
| `FallenTreeConfig` | record | FeatureConfiguration | — | 2 | 46 | [FallenTreeConfig.java](net/regions_unexplored/worldgen/feature/config/FallenTreeConfig.java) |
| `RockFeatureConfig` | record | FeatureConfiguration | — | 4 | 37 | [RockFeatureConfig.java](net/regions_unexplored/worldgen/feature/config/RockFeatureConfig.java) |
| `CarvedLimitedPoolFeatureConfig` | record | FeatureConfiguration | — | 0 | 25 | [CarvedLimitedPoolFeatureConfig.java](net/regions_unexplored/worldgen/feature/config/CarvedLimitedPoolFeatureConfig.java) |
| `ConfigCondition` | record | ProcessorCondition | — | 2 | 26 | [ConfigCondition.java](net/regions_unexplored/worldgen/processorcondition/ConfigCondition.java) |
| `ConfigRuleSource` | record | RuleSource | — | 2 | 32 | [ConfigRuleSource.java](net/regions_unexplored/worldgen/rulesource/ConfigRuleSource.java) |
| `ConfigPredicate` | record | LoadPredicate | — | 2 | 19 | [ConfigPredicate.java](net/regions_unexplored/lithostitched/ConfigPredicate.java) |
| `BiomeTarget` | class | — | — | 17 | 256 | [BiomeTarget.java](net/regions_unexplored/config/state/common/BiomeTarget.java) |
| `SmallOakLogBlock` | class | Block | — | 11 | 138 | [SmallOakLogBlock.java](net/regions_unexplored/world/level/block/wood/SmallOakLogBlock.java) |
| `FloweringLilyBlock` | class | WaterlilyBlock | — | 10 | 180 | [FloweringLilyBlock.java](net/regions_unexplored/world/level/block/plant/aquatic/FloweringLilyBlock.java) |
| `GiantLilyBlock` | class | HorizontalDirectionalBlock | — | 12 | 190 | [GiantLilyBlock.java](net/regions_unexplored/world/level/block/plant/aquatic/GiantLilyBlock.java) |
| `TallHyacinthStockBlock` | class | Block | — | 10 | 130 | [TallHyacinthStockBlock.java](net/regions_unexplored/world/level/block/plant/aquatic/TallHyacinthStockBlock.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ParticleRegistration` | 1 | `net.regions_unexplored.client` | [ParticleRegistration.java](net/regions_unexplored/client/ParticleRegistration.java) |
| `RegionsUnexploredNeo` | 1 | `net.regions_unexplored` | [RegionsUnexploredNeo.java](net/regions_unexplored/RegionsUnexploredNeo.java) |

## Mixin（9 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `NetherrackBlockMixin` | `NetherrackBlock` | [NetherrackBlockMixin.java](net/regions_unexplored/mixin/NetherrackBlockMixin.java) |
| `EatBlockGoalMixin` | `EatBlockGoal` | [EatBlockGoalMixin.java](net/regions_unexplored/mixin/EatBlockGoalMixin.java) |
| `VillagerProfessionAccessor` | `VillagerProfessionAccessor` | [VillagerProfessionAccessor.java](net/regions_unexplored/mixin/VillagerProfessionAccessor.java) |
| `CropPlacerMixin` | `CropPlacer` | [CropPlacerMixin.java](net/regions_unexplored/mixin/CropPlacerMixin.java) |
| `TrunkPlacerDirtMixin` | `TrunkPlacerDirt` | [TrunkPlacerDirtMixin.java](net/regions_unexplored/mixin/TrunkPlacerDirtMixin.java) |
| `WorldCarverMixin` | `WorldCarver` | [WorldCarverMixin.java](net/regions_unexplored/mixin/WorldCarverMixin.java) |
| `BiomeMixin` | `Biome` | [BiomeMixin.java](net/regions_unexplored/mixin/BiomeMixin.java) |
| `HolderLookupMixin` | `HolderLookup` | [HolderLookupMixin.java](net/regions_unexplored/mixin/removals/HolderLookupMixin.java) |
| `BiomeListMixin` | `BiomeList` | [BiomeListMixin.java](net/regions_unexplored/mixin/removals/BiomeListMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `RUConfigHandler` | — | [RUConfigHandler.java](net/regions_unexplored/config/RUConfigHandler.java) |
| `ConfigList` | — | [ConfigList.java](net/regions_unexplored/client/gui/ConfigList.java) |
| `RUCommonConfig` | — | [RUCommonConfig.java](net/regions_unexplored/config/state/common/RUCommonConfig.java) |
| `RUConfigScreen` | — | [RUConfigScreen.java](net/regions_unexplored/client/gui/RUConfigScreen.java) |
| `RUClientConfig` | — | [RUClientConfig.java](net/regions_unexplored/config/state/client/RUClientConfig.java) |
| `ConfigHelper` | — | [ConfigHelper.java](net/regions_unexplored/module/platform/ConfigHelper.java) |
| `RUConfigCondition` | — | [RUConfigCondition.java](net/regions_unexplored/RUConfigCondition.java) |
| `FallenTreeConfig` | — | [FallenTreeConfig.java](net/regions_unexplored/worldgen/feature/config/FallenTreeConfig.java) |
| `RockFeatureConfig` | — | [RockFeatureConfig.java](net/regions_unexplored/worldgen/feature/config/RockFeatureConfig.java) |
| `CarvedLimitedPoolFeatureConfig` | — | [CarvedLimitedPoolFeatureConfig.java](net/regions_unexplored/worldgen/feature/config/CarvedLimitedPoolFeatureConfig.java) |
| `ConfigCondition` | — | [ConfigCondition.java](net/regions_unexplored/worldgen/processorcondition/ConfigCondition.java) |
| `ConfigRuleSource` | — | [ConfigRuleSource.java](net/regions_unexplored/worldgen/rulesource/ConfigRuleSource.java) |
| `ConfigPredicate` | — | [ConfigPredicate.java](net/regions_unexplored/lithostitched/ConfigPredicate.java) |
| `HyacinthStockConfiguration` | — | [HyacinthStockConfiguration.java](net/regions_unexplored/world/level/feature/configuration/HyacinthStockConfiguration.java) |
| `SmallBushConfiguration` | — | [SmallBushConfiguration.java](net/regions_unexplored/world/level/feature/configuration/SmallBushConfiguration.java) |
| `RUTreeConfiguration` | — | [RUTreeConfiguration.java](net/regions_unexplored/world/level/feature/configuration/RUTreeConfiguration.java) |
| `PointedRedstoneConfiguration` | — | [PointedRedstoneConfiguration.java](net/regions_unexplored/world/level/feature/configuration/PointedRedstoneConfiguration.java) |
| `GiantBioshroomConfiguration` | — | [GiantBioshroomConfiguration.java](net/regions_unexplored/world/level/feature/configuration/GiantBioshroomConfiguration.java) |
| `SeaRockConfiguration` | — | [SeaRockConfiguration.java](net/regions_unexplored/world/level/feature/configuration/SeaRockConfiguration.java) |
| `PointedRedstoneClusterConfiguration` | — | [PointedRedstoneClusterConfiguration.java](net/regions_unexplored/world/level/feature/configuration/PointedRedstoneClusterConfiguration.java) |
| `RUConfiguredFeatures` | — | [RUConfiguredFeatures.java](net/regions_unexplored/registry/data/RUConfiguredFeatures.java) |
| `ConfigListBuilder` | — | [ConfigListBuilder.java](net/regions_unexplored/client/gui/ConfigListBuilder.java) |

## 按包浏览

- `net.regions_unexplored`（4 类）
- `net.regions_unexplored.block`（4 类）
- `net.regions_unexplored.block.compat`（4 类）
- `net.regions_unexplored.block.properties`（1 类）
- `net.regions_unexplored.block.sapling`（3 类）
- `net.regions_unexplored.block.set`（4 类）
- `net.regions_unexplored.block.type.dirt`（5 类）
- `net.regions_unexplored.block.type.flower`（2 类）
- `net.regions_unexplored.block.type.leaves`（6 类）
- `net.regions_unexplored.block.type.misc`（1 类）
- `net.regions_unexplored.block.type.wood`（2 类）
- `net.regions_unexplored.client`（3 类）
- `net.regions_unexplored.client.color`（1 类）
- `net.regions_unexplored.client.entity.model`（1 类）
- `net.regions_unexplored.client.entity.renderer`（2 类）
- `net.regions_unexplored.client.entity.renderer.layer`（1 类）
- `net.regions_unexplored.client.gui`（3 类）
- `net.regions_unexplored.client.gui.widget`（1 类）
- `net.regions_unexplored.client.particle`（5 类）
- `net.regions_unexplored.client.particle.spore`（3 类）
- `net.regions_unexplored.client.renderer`（2 类）
- `net.regions_unexplored.client.renderer.block`（2 类）
- `net.regions_unexplored.client.util`（1 类）
- `net.regions_unexplored.config`（1 类）
- `net.regions_unexplored.config.json5`（3 类）
- `net.regions_unexplored.config.state.client`（1 类）
- `net.regions_unexplored.config.state.common`（2 类）
- `net.regions_unexplored.entity.ashen`（1 类）
- `net.regions_unexplored.entity.client`（1 类）
- `net.regions_unexplored.entity.custom`（2 类）
- `net.regions_unexplored.item`（2 类）
- `net.regions_unexplored.item.behaviour`（1 类）
- `net.regions_unexplored.item.type`（2 类）
- `net.regions_unexplored.lithostitched`（2 类）
- `net.regions_unexplored.mixin`（7 类）
- `net.regions_unexplored.mixin.removals`（2 类）
- `net.regions_unexplored.module.platform`（7 类）
- `net.regions_unexplored.module.version`（2 类）
- `net.regions_unexplored.registry`（15 类）
- `net.regions_unexplored.registry.data`（12 类）
- `net.regions_unexplored.registry.tag`（4 类）
- `net.regions_unexplored.sound`（2 类）
- `net.regions_unexplored.util`（3 类）
- `net.regions_unexplored.world.level.block`（1 类）
- `net.regions_unexplored.world.level.block.cave`（7 类）
- `net.regions_unexplored.world.level.block.nether`（2 类）
- `net.regions_unexplored.world.level.block.other`（7 类）
- `net.regions_unexplored.world.level.block.plant.aquatic`（4 类）
- `net.regions_unexplored.world.level.block.plant.dusktrap`（2 类）
- `net.regions_unexplored.world.level.block.plant.flower`（4 类）
- `net.regions_unexplored.world.level.block.plant.food`（2 类）
- `net.regions_unexplored.world.level.block.plant.grass`（8 类）
- `net.regions_unexplored.world.level.block.plant.nether`（7 类）
- `net.regions_unexplored.world.level.block.plant.other`（11 类）
- `net.regions_unexplored.world.level.block.plant.sapling`（5 类）
- `net.regions_unexplored.world.level.block.plant.tall`（12 类）
- `net.regions_unexplored.world.level.block.state.properties`（4 类）
- `net.regions_unexplored.world.level.block.wood`（6 类）
- `net.regions_unexplored.world.level.feature`（20 类）
- `net.regions_unexplored.world.level.feature.bioshroom`（4 类）
- `net.regions_unexplored.world.level.feature.configuration`（8 类）
- `net.regions_unexplored.world.level.feature.tree`（28 类）
- `net.regions_unexplored.world.level.feature.tree.nether`（2 类）
- `net.regions_unexplored.world.surface`（1 类）
- `net.regions_unexplored.worldgen.feature`（3 类）
- `net.regions_unexplored.worldgen.feature.config`（3 类）
- `net.regions_unexplored.worldgen.foliageplacer`（10 类）
- `net.regions_unexplored.worldgen.processorcondition`（2 类）
- `net.regions_unexplored.worldgen.rootplacer`（2 类）
- `net.regions_unexplored.worldgen.rulesource`（1 类）
- `net.regions_unexplored.worldgen.stateprovider`（1 类）
- `net.regions_unexplored.worldgen.treedecorator`（6 类）
- `net.regions_unexplored.worldgen.trunkplacer`（4 类）
