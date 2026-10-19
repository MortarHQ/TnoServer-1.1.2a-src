# Ice And Fire Community Edition（`iceandfire`）

> jar：`[冰火龙]IceAndFireCE.jar` ｜ 版本：`2.0-beta.15` ｜ 作者：Alexthe666 (Origin Author), IAFEnvoy, xiaowu, uoay
> 规模：**606 个类 / 63 个包 / 68853 行** ｜ 事件处理类 3 ｜ Mixin 10 ｜ 注册/管理类 19

**依赖**：`neoforge`、`minecraft`、`uranus`、`jupiter`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.iafenvoy.iceandfire.entity.ai` | 68 | `PixieAIPickupItemGoal`、`CockatriceAITargetItemsGoal`、`HippogryphAITargetItemsGoal` |
| `com.iafenvoy.iceandfire.entity` | 56 | `DragonBaseEntity`、`HippogryphEntity`、`AmphithereEntity` |
| `com.iafenvoy.iceandfire.render.entity` | 40 | `CockatriceEntityRenderer`、`DragonSkullEntityRenderer`、`MobSkullEntityRenderer` |
| `com.iafenvoy.iceandfire.render.model` | 36 | `GorgonModel`、`HippogryphModel`、`IFChainBuffer` |
| `com.iafenvoy.iceandfire.item.block` | 35 | `DragonForgeBrickBlock`、`DragonForgeCoreBlock`、`DragonForgeInputBlock` |
| `com.iafenvoy.iceandfire.registry` | 34 | `IafItems`、`IafScreenHandlers`、`IafRegistryKeys` |
| `com.iafenvoy.iceandfire.item` | 29 | `DragonHornItem`、`PixieWandItem`、`SummoningCrystalItem` |
| `com.iafenvoy.iceandfire.entity.util` | 21 | `IafEntityUtil`、`BlockLaunchExplosion`、`IMultipartEntity` |
| `com.iafenvoy.iceandfire.item.tool` | 18 | `AmphithereArrowItem`、`HippogryphSwordItem`、`SeaSerpentArrowItem` |
| `com.iafenvoy.iceandfire.world.structure` | 16 | `DragonRoostStructure`、`DragonCaveStructure`、`HydraCaveStructure` |
| `com.iafenvoy.iceandfire.render.entity.feature` | 16 | `PixieItemFeatureRenderer`、`DragonRiderFeatureRenderer`、`HydraHeadFeatureRenderer` |
| `com.iafenvoy.iceandfire.item.ability` | 16 | `LightningDragonBloodToolAbility`、`DragonsteelIceToolAbility`、`SummonLightningAbility` |
| `com.iafenvoy.iceandfire.particle` | 13 | `DragonFrostParticle`、`PixieDustParticle`、`DragonParticleType` |
| `com.iafenvoy.iceandfire.item.block.entity` | 11 | `DragonForgeBlockEntity`、`LecternBlockEntity`、`PodiumBlockEntity` |
| `com.iafenvoy.iceandfire.render.model.armor` | 11 | `DragonSteelIceArmorModel`、`DragonSteelLightningArmorModel`、`IceDragonScaleArmorModel` |
| `com.iafenvoy.iceandfire.mixin` | 10 | `LivingEntityRendererMixin`、`PlayerEntityRendererMixin`、`TitleScreenMixin` |
| `com.iafenvoy.iceandfire.network.payload` | 10 | `DragonSetBurnBlockS2CPayload`、`StartRidingMobS2CPayload`、`DragonControlC2SPayload` |
| `com.iafenvoy.iceandfire.data` | 9 | `TrollType`、`HippogryphType`、`SeaSerpentType` |
| `com.iafenvoy.iceandfire.entity.util.dragon` | 8 | `IafDragonFlightManager`、`IafDragonDestructionManager`、`DragonUtils` |
| `com.iafenvoy.iceandfire.render.model.util` | 8 | `LegSolver`、`DragonPoses`、`IEnumDragonModelTypes` |
| `com.iafenvoy.iceandfire.screen.handler` | 7 | `HippogryphScreenHandler`、`HippocampusScreenHandler`、`LecternScreenHandler` |
| `com.iafenvoy.iceandfire.render.block` | 7 | `EggInIceBlockEntityRenderer`、`DreadSpawnerBlockEntityRenderer`、`DreadPortalBlockEntityRenderer` |
| `com.iafenvoy.iceandfire.registry.tag` | 7 | `CommonBlockTags`、`CommonItemTags`、`IafItemTags` |
| `com.iafenvoy.iceandfire.screen.gui` | 6 | `LecternScreen`、`HippocampusScreen`、`PodiumScreen` |
| `com.iafenvoy.iceandfire.render.model.animator` | 6 | `DragonTabulaModelAnimator`、`IceAndFireTabulaModelAnimator`、`SeaSerpentTabulaModelAnimator` |
| `com.iafenvoy.iceandfire.item.armor` | 6 | `BlindfoldItem`、`SeaSerpentArmorItem`、`EarPlugsArmorItem` |
| `com.iafenvoy.iceandfire.world.feature` | 6 | `HippocampusSpawnFeature`、`SeaSerpentSpawnFeature`、`DeathWormSpawnFeature` |
| `com.iafenvoy.iceandfire.util` | 5 | `RestrictWorldAccess`、`ItemRandomizer`、`Color4i` |
| `com.iafenvoy.iceandfire.render.misc` | 5 | `LightningBoltData`、`LightningRenderer`、`ChainRenderer` |
| `com.iafenvoy.iceandfire.compat.jei` | 5 | `IceDragonForgeRecipeCategory`、`LightningDragonForgeRecipeCategory`、`DragonForgeRecipeCategory` |
| `com.iafenvoy.iceandfire.render.item` | 5 | `TideTridentItemRenderer`、`MiscItemRenderer`、`GorgonHeadRenderer` |
| `com.iafenvoy.iceandfire.entity.pathfinding` | 5 | `DeathWormLandNavigation`、`DeathWormSandNavigation`、`DeathWormNodeMaker` |
| `com.iafenvoy.iceandfire.compat.jade` | 5 | `DragonEggEntityComponentProvider`、`DragonEggBlockComponentProvider`、`IceAndFireJadePlugin` |
| `com.iafenvoy.iceandfire.event` | 4 | `ServerEvents`、`ClientEvents`、`IafEvents` |
| `com.iafenvoy.iceandfire.data.component` | 4 | `MiscData`、`PortalData`、`ChickenData` |
| `com.iafenvoy.iceandfire.item.component` | 4 | `DragonHornComponent`、`StoneStatusComponent`、`DragonSkullComponent` |
| `com.iafenvoy.iceandfire.world.processor` | 4 | `VillageHouseProcessor`、`DreadPortalProcessor`、`GraveyardProcessor` |
| `com.iafenvoy.iceandfire.neoforge` | 3 | `IceAndFireNeoForgeClient`、`IceAndFireNeoForge`、`IafAttachments` |
| `com.iafenvoy.iceandfire.screen.gui.bestiary` | 3 | `BestiaryScreen`、`ChangePageButton`、`IndexPageButton` |
| `com.iafenvoy.iceandfire.item.block.util` | 3 | `DreadBlock`、`WallBlock`、`DragonProof` |
| … | | 其余 23 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IceAndFireNeoForgeClient` | class | — | EventBusSubscriber | 3 | 57 | [IceAndFireNeoForgeClient.java](com/iafenvoy/iceandfire/neoforge/IceAndFireNeoForgeClient.java) |
| `IceAndFireNeoForge` | class | — | EventBusSubscriber、Mod | 2 | 43 | [IceAndFireNeoForge.java](com/iafenvoy/iceandfire/neoforge/IceAndFireNeoForge.java) |
| `IafAttachments` | class | — | EventBusSubscriber | 3 | 57 | [IafAttachments.java](com/iafenvoy/iceandfire/neoforge/IafAttachments.java) |
| `DragonBaseEntity` | class | TamableAnimal | — | 234 | 3013 | [DragonBaseEntity.java](com/iafenvoy/iceandfire/entity/DragonBaseEntity.java) |
| `HippogryphEntity` | class | TamableAnimal | — | 84 | 1177 | [HippogryphEntity.java](com/iafenvoy/iceandfire/entity/HippogryphEntity.java) |
| `AmphithereEntity` | class | TamableAnimal | — | 90 | 1303 | [AmphithereEntity.java](com/iafenvoy/iceandfire/entity/AmphithereEntity.java) |
| `SeaSerpentEntity` | class | Animal | — | 75 | 935 | [SeaSerpentEntity.java](com/iafenvoy/iceandfire/entity/SeaSerpentEntity.java) |
| `DeathWormEntity` | class | TamableAnimal | — | 81 | 889 | [DeathWormEntity.java](com/iafenvoy/iceandfire/entity/DeathWormEntity.java) |
| `CockatriceEntity` | class | TamableAnimal | — | 64 | 856 | [CockatriceEntity.java](com/iafenvoy/iceandfire/entity/CockatriceEntity.java) |
| `ServerEvents` | class | — | — | 13 | 454 | [ServerEvents.java](com/iafenvoy/iceandfire/event/ServerEvents.java) |
| `DragonForgeBlockEntity` | class | BaseContainerBlockEntity | — | 50 | 462 | [DragonForgeBlockEntity.java](com/iafenvoy/iceandfire/item/block/entity/DragonForgeBlockEntity.java) |
| `IceDragonEntity` | class | DragonBaseEntity | — | 36 | 603 | [IceDragonEntity.java](com/iafenvoy/iceandfire/entity/IceDragonEntity.java) |
| `HippocampusEntity` | class | TamableAnimal | — | 72 | 757 | [HippocampusEntity.java](com/iafenvoy/iceandfire/entity/HippocampusEntity.java) |
| `SirenEntity` | class | Monster | — | 49 | 653 | [SirenEntity.java](com/iafenvoy/iceandfire/entity/SirenEntity.java) |
| `LecternBlockEntity` | class | BaseContainerBlockEntity | — | 32 | 241 | [LecternBlockEntity.java](com/iafenvoy/iceandfire/item/block/entity/LecternBlockEntity.java) |
| `CyclopsEntity` | class | Monster | — | 34 | 448 | [CyclopsEntity.java](com/iafenvoy/iceandfire/entity/CyclopsEntity.java) |
| `GhostEntity` | class | Monster | — | 44 | 401 | [GhostEntity.java](com/iafenvoy/iceandfire/entity/GhostEntity.java) |
| `StymphalianBirdEntity` | class | Monster | — | 41 | 529 | [StymphalianBirdEntity.java](com/iafenvoy/iceandfire/entity/StymphalianBirdEntity.java) |
| `TrollEntity` | class | Monster | — | 36 | 471 | [TrollEntity.java](com/iafenvoy/iceandfire/entity/TrollEntity.java) |
| `PixieEntity` | class | TamableAnimal | — | 34 | 497 | [PixieEntity.java](com/iafenvoy/iceandfire/entity/PixieEntity.java) |
| `HydraEntity` | class | Monster | — | 38 | 497 | [HydraEntity.java](com/iafenvoy/iceandfire/entity/HydraEntity.java) |
| `LightningDragonEntity` | class | DragonBaseEntity | — | 34 | 516 | [LightningDragonEntity.java](com/iafenvoy/iceandfire/entity/LightningDragonEntity.java) |
| `HippogryphScreenHandler` | class | AbstractContainerMenu | — | 16 | 159 | [HippogryphScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/HippogryphScreenHandler.java) |
| `LivingEntityRendererMixin` | class | — | Mixin | 1 | 23 | [LivingEntityRendererMixin.java](com/iafenvoy/iceandfire/mixin/LivingEntityRendererMixin.java) |
| `PlayerEntityRendererMixin` | class | — | Mixin | 1 | 49 | [PlayerEntityRendererMixin.java](com/iafenvoy/iceandfire/mixin/PlayerEntityRendererMixin.java) |
| `ClientEvents` | class | — | OnlyIn | 3 | 106 | [ClientEvents.java](com/iafenvoy/iceandfire/event/ClientEvents.java) |
| `StoneStatueEntity` | class | LivingEntity | — | 31 | 223 | [StoneStatueEntity.java](com/iafenvoy/iceandfire/entity/StoneStatueEntity.java) |
| `DreadLichEntity` | class | DreadMobEntity | — | 30 | 353 | [DreadLichEntity.java](com/iafenvoy/iceandfire/entity/DreadLichEntity.java) |
| `MultipartPartEntity` | class | Entity | — | 30 | 283 | [MultipartPartEntity.java](com/iafenvoy/iceandfire/entity/MultipartPartEntity.java) |
| `FireDragonEntity` | class | DragonBaseEntity | — | 25 | 491 | [FireDragonEntity.java](com/iafenvoy/iceandfire/entity/FireDragonEntity.java) |
| `HippocampusScreenHandler` | class | AbstractContainerMenu | — | 11 | 149 | [HippocampusScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/HippocampusScreenHandler.java) |
| `BestiaryScreen` | class | AbstractContainerScreen | — | 13 | 1033 | [BestiaryScreen.java](com/iafenvoy/iceandfire/screen/gui/bestiary/BestiaryScreen.java) |
| `PodiumBlockEntity` | class | BaseContainerBlockEntity | — | 28 | 183 | [PodiumBlockEntity.java](com/iafenvoy/iceandfire/item/block/entity/PodiumBlockEntity.java) |
| `DragonSkullEntity` | class | Animal | — | 30 | 195 | [DragonSkullEntity.java](com/iafenvoy/iceandfire/entity/DragonSkullEntity.java) |
| `DreadBeastEntity` | class | DreadMobEntity | — | 25 | 238 | [DreadBeastEntity.java](com/iafenvoy/iceandfire/entity/DreadBeastEntity.java) |
| `DragonEggEntity` | class | LivingEntity | — | 28 | 327 | [DragonEggEntity.java](com/iafenvoy/iceandfire/entity/DragonEggEntity.java) |
| `DreadThrallEntity` | class | DreadMobEntity | — | 28 | 286 | [DreadThrallEntity.java](com/iafenvoy/iceandfire/entity/DreadThrallEntity.java) |
| `GorgonEntity` | class | Monster | — | 28 | 360 | [GorgonEntity.java](com/iafenvoy/iceandfire/entity/GorgonEntity.java) |
| `DreadScuttlerEntity` | class | DreadMobEntity | — | 27 | 254 | [DreadScuttlerEntity.java](com/iafenvoy/iceandfire/entity/DreadScuttlerEntity.java) |
| `DreadGhoulEntity` | class | DreadMobEntity | — | 26 | 263 | [DreadGhoulEntity.java](com/iafenvoy/iceandfire/entity/DreadGhoulEntity.java) |
| `IafDragonFlightManager` | class | MoveControl | — | 13 | 333 | [IafDragonFlightManager.java](com/iafenvoy/iceandfire/entity/util/dragon/IafDragonFlightManager.java) |
| `IafDragonDestructionManager` | class | — | — | 11 | 385 | [IafDragonDestructionManager.java](com/iafenvoy/iceandfire/entity/util/dragon/IafDragonDestructionManager.java) |
| `TitleScreenRenderManager` | class | — | — | 5 | 155 | [TitleScreenRenderManager.java](com/iafenvoy/iceandfire/screen/TitleScreenRenderManager.java) |
| `LecternScreenHandler` | class | AbstractContainerMenu | — | 8 | 182 | [LecternScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/LecternScreenHandler.java) |
| `DragonForgeScreenHandler` | class | AbstractContainerMenu | — | 5 | 120 | [DragonForgeScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/DragonForgeScreenHandler.java) |
| `TitleScreenMixin` | class | Screen | Mixin | 2 | 51 | [TitleScreenMixin.java](com/iafenvoy/iceandfire/mixin/TitleScreenMixin.java) |
| `WorldRendererMixin` | class | — | Mixin、OnlyIn | 1 | 57 | [WorldRendererMixin.java](com/iafenvoy/iceandfire/mixin/WorldRendererMixin.java) |
| `RotatingCubeMapRendererMixin` | class | — | Mixin | 1 | 32 | [RotatingCubeMapRendererMixin.java](com/iafenvoy/iceandfire/mixin/RotatingCubeMapRendererMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 4 | 45 | [LivingEntityMixin.java](com/iafenvoy/iceandfire/mixin/LivingEntityMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 2 | 59 | [GameRendererMixin.java](com/iafenvoy/iceandfire/mixin/GameRendererMixin.java) |
| `MobEntityMixin` | class | Entity | Mixin | 1 | 37 | [MobEntityMixin.java](com/iafenvoy/iceandfire/mixin/MobEntityMixin.java) |
| `IafEvents` | class | — | — | 0 | 51 | [IafEvents.java](com/iafenvoy/iceandfire/event/IafEvents.java) |
| `CommonEvents` | class | — | — | 0 | 10 | [CommonEvents.java](com/iafenvoy/iceandfire/event/CommonEvents.java) |
| `IafCommonConfig` | class | AutoInitConfigContainer | — | 0 | 886 | [IafCommonConfig.java](com/iafenvoy/iceandfire/config/IafCommonConfig.java) |
| `MobSkullEntity` | class | Animal | — | 25 | 157 | [MobSkullEntity.java](com/iafenvoy/iceandfire/entity/MobSkullEntity.java) |
| `DreadKnightEntity` | class | DreadMobEntity | — | 22 | 225 | [DreadKnightEntity.java](com/iafenvoy/iceandfire/entity/DreadKnightEntity.java) |
| `DragonUtils` | class | — | — | 25 | 382 | [DragonUtils.java](com/iafenvoy/iceandfire/entity/util/dragon/DragonUtils.java) |
| `DragonScreenHandler` | class | AbstractContainerMenu | — | 4 | 109 | [DragonScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/DragonScreenHandler.java) |
| `BestiaryScreenHandler` | class | AbstractContainerMenu | — | 3 | 45 | [BestiaryScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/BestiaryScreenHandler.java) |
| `PodiumScreenHandler` | class | AbstractContainerMenu | — | 3 | 71 | [PodiumScreenHandler.java](com/iafenvoy/iceandfire/screen/handler/PodiumScreenHandler.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `IceAndFireNeoForgeClient` | 3 | `com.iafenvoy.iceandfire.neoforge` | [IceAndFireNeoForgeClient.java](com/iafenvoy/iceandfire/neoforge/IceAndFireNeoForgeClient.java) |
| `IceAndFireNeoForge` | 2 | `com.iafenvoy.iceandfire.neoforge` | [IceAndFireNeoForge.java](com/iafenvoy/iceandfire/neoforge/IceAndFireNeoForge.java) |
| `IafAttachments` | 1 | `com.iafenvoy.iceandfire.neoforge` | [IafAttachments.java](com/iafenvoy/iceandfire/neoforge/IafAttachments.java) |

## Mixin（10 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityRendererMixin` | `LivingEntityRenderer` | [LivingEntityRendererMixin.java](com/iafenvoy/iceandfire/mixin/LivingEntityRendererMixin.java) |
| `PlayerEntityRendererMixin` | `PlayerEntityRenderer` | [PlayerEntityRendererMixin.java](com/iafenvoy/iceandfire/mixin/PlayerEntityRendererMixin.java) |
| `TitleScreenMixin` | `TitleScreen` | [TitleScreenMixin.java](com/iafenvoy/iceandfire/mixin/TitleScreenMixin.java) |
| `WorldRendererMixin` | `WorldRenderer` | [WorldRendererMixin.java](com/iafenvoy/iceandfire/mixin/WorldRendererMixin.java) |
| `RotatingCubeMapRendererMixin` | `RotatingCubeMapRenderer` | [RotatingCubeMapRendererMixin.java](com/iafenvoy/iceandfire/mixin/RotatingCubeMapRendererMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/iafenvoy/iceandfire/mixin/LivingEntityMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](com/iafenvoy/iceandfire/mixin/GameRendererMixin.java) |
| `MobEntityMixin` | `MobEntity` | [MobEntityMixin.java](com/iafenvoy/iceandfire/mixin/MobEntityMixin.java) |
| `ChunkRegionMixin` | `ChunkRegion` | [ChunkRegionMixin.java](com/iafenvoy/iceandfire/mixin/ChunkRegionMixin.java) |
| `InGameHudMixin` | `InGameHud` | [InGameHudMixin.java](com/iafenvoy/iceandfire/mixin/InGameHudMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `IafCommonConfig` | — | [IafCommonConfig.java](com/iafenvoy/iceandfire/config/IafCommonConfig.java) |
| `IafClientConfig` | — | [IafClientConfig.java](com/iafenvoy/iceandfire/config/IafClientConfig.java) |

## 按包浏览

- `architectury_inject_iceandfire_common_2208baeb528844f8af40d51d3d585f67_77f6f7e2401b2247d1fc30c8995f87b2662269f032a3c0258d580dc2c5692642IceAndFireCEcommon20beta15devjar`（1 类）
- `com.iafenvoy.iceandfire`（2 类）
- `com.iafenvoy.iceandfire.compat.delight`（1 类）
- `com.iafenvoy.iceandfire.compat.emi`（2 类）
- `com.iafenvoy.iceandfire.compat.jade`（5 类）
- `com.iafenvoy.iceandfire.compat.jei`（5 类）
- `com.iafenvoy.iceandfire.compat.ponder`（2 类）
- `com.iafenvoy.iceandfire.config`（2 类）
- `com.iafenvoy.iceandfire.data`（9 类）
- `com.iafenvoy.iceandfire.data.component`（4 类）
- `com.iafenvoy.iceandfire.effect`（2 类）
- `com.iafenvoy.iceandfire.entity`（56 类）
- `com.iafenvoy.iceandfire.entity.ai`（68 类）
- `com.iafenvoy.iceandfire.entity.pathfinding`（5 类）
- `com.iafenvoy.iceandfire.entity.util`（21 类）
- `com.iafenvoy.iceandfire.entity.util.dragon`（8 类）
- `com.iafenvoy.iceandfire.event`（4 类）
- `com.iafenvoy.iceandfire.impl`（2 类）
- `com.iafenvoy.iceandfire.impl.neoforge`（1 类）
- `com.iafenvoy.iceandfire.item`（29 类）
- `com.iafenvoy.iceandfire.item.ability`（16 类）
- `com.iafenvoy.iceandfire.item.armor`（6 类）
- `com.iafenvoy.iceandfire.item.block`（35 类）
- `com.iafenvoy.iceandfire.item.block.entity`（11 类）
- `com.iafenvoy.iceandfire.item.block.util`（3 类）
- `com.iafenvoy.iceandfire.item.component`（4 类）
- `com.iafenvoy.iceandfire.item.tool`（18 类）
- `com.iafenvoy.iceandfire.loot`（2 类）
- `com.iafenvoy.iceandfire.mixin`（10 类）
- `com.iafenvoy.iceandfire.neoforge`（3 类）
- `com.iafenvoy.iceandfire.neoforge.compat`（2 类）
- `com.iafenvoy.iceandfire.neoforge.compat.curios`（2 类）
- `com.iafenvoy.iceandfire.network`（2 类）
- `com.iafenvoy.iceandfire.network.payload`（10 类）
- `com.iafenvoy.iceandfire.particle`（13 类）
- `com.iafenvoy.iceandfire.recipe`（1 类）
- `com.iafenvoy.iceandfire.registry`（34 类）
- `com.iafenvoy.iceandfire.registry.tag`（7 类）
- `com.iafenvoy.iceandfire.render`（3 类）
- `com.iafenvoy.iceandfire.render.block`（7 类）
- `com.iafenvoy.iceandfire.render.entity`（40 类）
- `com.iafenvoy.iceandfire.render.entity.feature`（16 类）
- `com.iafenvoy.iceandfire.render.item`（5 类）
- `com.iafenvoy.iceandfire.render.item.armor`（2 类）
- `com.iafenvoy.iceandfire.render.misc`（5 类）
- `com.iafenvoy.iceandfire.render.model`（36 类）
- `com.iafenvoy.iceandfire.render.model.animator`（6 类）
- `com.iafenvoy.iceandfire.render.model.armor`（11 类）
- `com.iafenvoy.iceandfire.render.model.util`（8 类）
- `com.iafenvoy.iceandfire.render.texture`（1 类）
- `com.iafenvoy.iceandfire.screen`（1 类）
- `com.iafenvoy.iceandfire.screen.gui`（6 类）
- `com.iafenvoy.iceandfire.screen.gui.bestiary`（3 类）
- `com.iafenvoy.iceandfire.screen.handler`（7 类）
- `com.iafenvoy.iceandfire.screen.slot`（3 类）
- `com.iafenvoy.iceandfire.util`（5 类）
- `com.iafenvoy.iceandfire.util.attachment`（2 类）
- `com.iafenvoy.iceandfire.util.trade`（2 类）
- `com.iafenvoy.iceandfire.util.trade.factory`（1 类）
- `com.iafenvoy.iceandfire.world`（2 类）
- `com.iafenvoy.iceandfire.world.feature`（6 类）
- `com.iafenvoy.iceandfire.world.processor`（4 类）
- `com.iafenvoy.iceandfire.world.structure`（16 类）
