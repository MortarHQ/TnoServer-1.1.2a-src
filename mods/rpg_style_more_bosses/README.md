# RPG Style More Bosses（`rpg_style_more_bosses`）

> jar：`[铠甲猫女]RPG_Style_More_Bosses.jar` ｜ 版本：`1.0.3` ｜ 作者：zrikon
> 规模：**92 个类 / 13 个包 / 8466 行** ｜ 事件处理类 11 ｜ Mixin 0 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.mcreator.rpgstylemorebosses.procedures` | 27 | `PlayerJoinProcedure`、`NotAdventureProcedure`、`BlockBarrierOnInitialEntitySpawnProcedure` |
| `net.mcreator.rpgstylemorebosses.entity` | 12 | `BlockBarrierEntity`、`BossCounterEntity`、`ExoframeBeamEntity` |
| `net.mcreator.rpgstylemorebosses.client.renderer` | 12 | `BlockBarrierRenderer`、`RunicSpiderRenderer`、`BeaconActiveRenderer` |
| `net.mcreator.rpgstylemorebosses.entity.model` | 12 | `BlockBarrierModel`、`TheExoframeSpawnModel`、`TheExoframeVFXModel` |
| `net.mcreator.rpgstylemorebosses.entity.layer` | 10 | `BlockBarrierLayer`、`BossCounterLayer`、`ExoframeBeamLayer` |
| `net.mcreator.rpgstylemorebosses.init` | 8 | `RpgStyleMoreBossesModEntityRenderers`、`EntityAnimationFactory`、`RpgStyleMoreBossesModEntities` |
| `net.mcreator.rpgstylemorebosses.world.features.treedecorators` | 3 | `DungeonVoidLeaveDecorator`、`DungeonVoidTrunkDecorator`、`DungeonVoidFruitDecorator` |
| `net.mcreator.rpgstylemorebosses.item` | 3 | `TheExoframeKeyItem`、`ExoframePartItem`、`PizdabobraItem` |
| `net.mcreator.rpgstylemorebosses.network` | 1 | `RpgStyleMoreBossesModVariables` |
| `net.mcreator.rpgstylemorebosses` | 1 | `RpgStyleMoreBossesMod` |
| `net.mcreator.rpgstylemorebosses.world.dimension` | 1 | `SecondDimentionDimension` |
| `net.mcreator.rpgstylemorebosses.block` | 1 | `DungeonBricksCubesBlock` |
| `net.mcreator.rpgstylemorebosses.potion` | 1 | `StunMobEffect` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RpgStyleMoreBossesModVariables` | class | SavedData | EventBusSubscriber | 24 | 315 | [RpgStyleMoreBossesModVariables.java](net/mcreator/rpgstylemorebosses/network/RpgStyleMoreBossesModVariables.java) |
| `RpgStyleMoreBossesModEntityRenderers` | class | — | EventBusSubscriber | 1 | 40 | [RpgStyleMoreBossesModEntityRenderers.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModEntityRenderers.java) |
| `EntityAnimationFactory` | class | — | EventBusSubscriber | 1 | 122 | [EntityAnimationFactory.java](net/mcreator/rpgstylemorebosses/init/EntityAnimationFactory.java) |
| `DungeonVoidLeaveDecorator` | class | LeaveVineDecorator | EventBusSubscriber | 5 | 88 | [DungeonVoidLeaveDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidLeaveDecorator.java) |
| `RpgStyleMoreBossesMod` | class | — | Mod | 4 | 91 | [RpgStyleMoreBossesMod.java](net/mcreator/rpgstylemorebosses/RpgStyleMoreBossesMod.java) |
| `PlayerJoinProcedure` | class | — | EventBusSubscriber | 3 | 55 | [PlayerJoinProcedure.java](net/mcreator/rpgstylemorebosses/procedures/PlayerJoinProcedure.java) |
| `NotAdventureProcedure` | class | — | EventBusSubscriber | 4 | 53 | [NotAdventureProcedure.java](net/mcreator/rpgstylemorebosses/procedures/NotAdventureProcedure.java) |
| `SecondDimentionDimension` | class | — | EventBusSubscriber | 4 | 50 | [SecondDimentionDimension.java](net/mcreator/rpgstylemorebosses/world/dimension/SecondDimentionDimension.java) |
| `DungeonVoidTrunkDecorator` | class | TrunkVineDecorator | EventBusSubscriber | 4 | 74 | [DungeonVoidTrunkDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidTrunkDecorator.java) |
| `DungeonVoidFruitDecorator` | class | CocoaDecorator | EventBusSubscriber | 4 | 68 | [DungeonVoidFruitDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidFruitDecorator.java) |
| `RpgStyleMoreBossesModEntities` | class | — | EventBusSubscriber | 3 | 174 | [RpgStyleMoreBossesModEntities.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModEntities.java) |
| `BlockBarrierEntity` | class | PathfinderMob | — | 29 | 247 | [BlockBarrierEntity.java](net/mcreator/rpgstylemorebosses/entity/BlockBarrierEntity.java) |
| `BossCounterEntity` | class | PathfinderMob | — | 30 | 248 | [BossCounterEntity.java](net/mcreator/rpgstylemorebosses/entity/BossCounterEntity.java) |
| `ExoframeBeamEntity` | class | PathfinderMob | — | 31 | 258 | [ExoframeBeamEntity.java](net/mcreator/rpgstylemorebosses/entity/ExoframeBeamEntity.java) |
| `BeaconEntity` | class | PathfinderMob | — | 29 | 252 | [BeaconEntity.java](net/mcreator/rpgstylemorebosses/entity/BeaconEntity.java) |
| `BeaconActiveEntity` | class | PathfinderMob | — | 29 | 241 | [BeaconActiveEntity.java](net/mcreator/rpgstylemorebosses/entity/BeaconActiveEntity.java) |
| `TheExoframeVFX2Entity` | class | PathfinderMob | — | 28 | 237 | [TheExoframeVFX2Entity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeVFX2Entity.java) |
| `TheExoframeSpawnEntity` | class | Monster | — | 25 | 235 | [TheExoframeSpawnEntity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeSpawnEntity.java) |
| `TheExoframeVFXEntity` | class | PathfinderMob | — | 28 | 237 | [TheExoframeVFXEntity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeVFXEntity.java) |
| `KriStrarzEntity` | class | Monster | — | 23 | 208 | [KriStrarzEntity.java](net/mcreator/rpgstylemorebosses/entity/KriStrarzEntity.java) |
| `TheExoframeEntity` | class | Monster | — | 24 | 225 | [TheExoframeEntity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeEntity.java) |
| `RunicSpiderEntity` | class | Monster | — | 23 | 208 | [RunicSpiderEntity.java](net/mcreator/rpgstylemorebosses/entity/RunicSpiderEntity.java) |
| `ExoframeTickEntity` | class | Monster | — | 20 | 194 | [ExoframeTickEntity.java](net/mcreator/rpgstylemorebosses/entity/ExoframeTickEntity.java) |
| `BlockBarrierOnInitialEntitySpawnProcedure` | class | — | — | 1 | 18 | [BlockBarrierOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BlockBarrierOnInitialEntitySpawnProcedure.java) |
| `BlockBarrierRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BlockBarrierRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BlockBarrierRenderer.java) |
| `TheExoframeOnEntityTickUpdateProcedure` | class | — | — | 2 | 320 | [TheExoframeOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeOnEntityTickUpdateProcedure.java) |
| `ExoframeTickOnEntityTickUpdateProcedure` | class | — | — | 1 | 268 | [ExoframeTickOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ExoframeTickOnEntityTickUpdateProcedure.java) |
| `KriStrarzOnEntityTickUpdateProcedure` | class | — | — | 1 | 217 | [KriStrarzOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/KriStrarzOnEntityTickUpdateProcedure.java) |
| `BlockBarrierLayer` | class | GeoRenderLayer | — | 1 | 48 | [BlockBarrierLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/BlockBarrierLayer.java) |
| `BlockBarrierModel` | class | GeoModel | — | 3 | 20 | [BlockBarrierModel.java](net/mcreator/rpgstylemorebosses/entity/model/BlockBarrierModel.java) |
| `DungeonBricksCubesBlock` | class | Block | — | 1 | 20 | [DungeonBricksCubesBlock.java](net/mcreator/rpgstylemorebosses/block/DungeonBricksCubesBlock.java) |
| `SetBlockProcedure` | class | — | — | 1 | 103 | [SetBlockProcedure.java](net/mcreator/rpgstylemorebosses/procedures/SetBlockProcedure.java) |
| `BeaconRightClickedOnEntityProcedure` | class | — | — | 1 | 105 | [BeaconRightClickedOnEntityProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BeaconRightClickedOnEntityProcedure.java) |
| `ExoframeBeamOnInitialEntitySpawnProcedure` | class | — | — | 1 | 130 | [ExoframeBeamOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ExoframeBeamOnInitialEntitySpawnProcedure.java) |
| `TheExoframeEntityIsHurtProcedure` | class | — | — | 1 | 38 | [TheExoframeEntityIsHurtProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeEntityIsHurtProcedure.java) |
| `BossCounterOnEntityTickUpdateProcedure` | class | — | — | 1 | 78 | [BossCounterOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BossCounterOnEntityTickUpdateProcedure.java) |
| `TheExoframeOnInitialEntitySpawnProcedure` | class | — | — | 1 | 26 | [TheExoframeOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeOnInitialEntitySpawnProcedure.java) |
| `BeaconActivePlayerCollidesWithThisEntityProcedure` | class | — | — | 1 | 59 | [BeaconActivePlayerCollidesWithThisEntityProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BeaconActivePlayerCollidesWithThisEntityProcedure.java) |
| `TheExoframeSpawnOnInitialEntitySpawnProcedure` | class | — | — | 1 | 121 | [TheExoframeSpawnOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeSpawnOnInitialEntitySpawnProcedure.java) |
| `ExoframeTickOnInitialEntitySpawnProcedure` | class | — | — | 1 | 29 | [ExoframeTickOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ExoframeTickOnInitialEntitySpawnProcedure.java) |
| `ChargeProcedure` | class | — | — | 1 | 413 | [ChargeProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ChargeProcedure.java) |
| `RunicSpiderOnEntityTickUpdateProcedure` | class | — | — | 1 | 24 | [RunicSpiderOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/RunicSpiderOnEntityTickUpdateProcedure.java) |
| `TheExoframeVFXOnInitialEntitySpawnProcedure` | class | — | — | 1 | 18 | [TheExoframeVFXOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeVFXOnInitialEntitySpawnProcedure.java) |
| `TheExoframeKeyItem` | class | Item | — | 1 | 25 | [TheExoframeKeyItem.java](net/mcreator/rpgstylemorebosses/item/TheExoframeKeyItem.java) |
| `ExoframePartItem` | class | Item | — | 0 | 12 | [ExoframePartItem.java](net/mcreator/rpgstylemorebosses/item/ExoframePartItem.java) |
| `PizdabobraItem` | class | Item | — | 1 | 24 | [PizdabobraItem.java](net/mcreator/rpgstylemorebosses/item/PizdabobraItem.java) |
| `RunicSpiderRenderer` | class | GeoEntityRenderer | — | 2 | 42 | [RunicSpiderRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/RunicSpiderRenderer.java) |
| `BeaconActiveRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BeaconActiveRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BeaconActiveRenderer.java) |
| `ExoframeTickRenderer` | class | GeoEntityRenderer | — | 3 | 48 | [ExoframeTickRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/ExoframeTickRenderer.java) |
| `TheExoframeSpawnRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [TheExoframeSpawnRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeSpawnRenderer.java) |
| `KriStrarzRenderer` | class | GeoEntityRenderer | — | 2 | 42 | [KriStrarzRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/KriStrarzRenderer.java) |
| `BeaconRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BeaconRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BeaconRenderer.java) |
| `TheExoframeVFXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [TheExoframeVFXRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeVFXRenderer.java) |
| `TheExoframeRenderer` | class | GeoEntityRenderer | — | 3 | 48 | [TheExoframeRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeRenderer.java) |
| `BossCounterRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BossCounterRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BossCounterRenderer.java) |
| `ExoframeBeamRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [ExoframeBeamRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/ExoframeBeamRenderer.java) |
| `TheExoframeVFX2Renderer` | class | GeoEntityRenderer | — | 2 | 44 | [TheExoframeVFX2Renderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeVFX2Renderer.java) |
| `RpgStyleMoreBossesModItems` | class | — | — | 1 | 30 | [RpgStyleMoreBossesModItems.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModItems.java) |
| `RpgStyleMoreBossesModBlocks` | class | — | — | 0 | 13 | [RpgStyleMoreBossesModBlocks.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModBlocks.java) |
| `Punch2Procedure` | class | — | — | 1 | 252 | [Punch2Procedure.java](net/mcreator/rpgstylemorebosses/procedures/Punch2Procedure.java) |

## 事件处理（`@SubscribeEvent`，11 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `RpgStyleMoreBossesModVariables` | 7 | `net.mcreator.rpgstylemorebosses.network` | [RpgStyleMoreBossesModVariables.java](net/mcreator/rpgstylemorebosses/network/RpgStyleMoreBossesModVariables.java) |
| `SecondDimentionDimension` | 2 | `net.mcreator.rpgstylemorebosses.world.dimension` | [SecondDimentionDimension.java](net/mcreator/rpgstylemorebosses/world/dimension/SecondDimentionDimension.java) |
| `RpgStyleMoreBossesModEntities` | 2 | `net.mcreator.rpgstylemorebosses.init` | [RpgStyleMoreBossesModEntities.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModEntities.java) |
| `RpgStyleMoreBossesModEntityRenderers` | 1 | `net.mcreator.rpgstylemorebosses.init` | [RpgStyleMoreBossesModEntityRenderers.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModEntityRenderers.java) |
| `EntityAnimationFactory` | 1 | `net.mcreator.rpgstylemorebosses.init` | [EntityAnimationFactory.java](net/mcreator/rpgstylemorebosses/init/EntityAnimationFactory.java) |
| `DungeonVoidLeaveDecorator` | 1 | `net.mcreator.rpgstylemorebosses.world.features.treedecorators` | [DungeonVoidLeaveDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidLeaveDecorator.java) |
| `RpgStyleMoreBossesMod` | 1 | `net.mcreator.rpgstylemorebosses` | [RpgStyleMoreBossesMod.java](net/mcreator/rpgstylemorebosses/RpgStyleMoreBossesMod.java) |
| `PlayerJoinProcedure` | 1 | `net.mcreator.rpgstylemorebosses.procedures` | [PlayerJoinProcedure.java](net/mcreator/rpgstylemorebosses/procedures/PlayerJoinProcedure.java) |
| `NotAdventureProcedure` | 1 | `net.mcreator.rpgstylemorebosses.procedures` | [NotAdventureProcedure.java](net/mcreator/rpgstylemorebosses/procedures/NotAdventureProcedure.java) |
| `DungeonVoidTrunkDecorator` | 1 | `net.mcreator.rpgstylemorebosses.world.features.treedecorators` | [DungeonVoidTrunkDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidTrunkDecorator.java) |
| `DungeonVoidFruitDecorator` | 1 | `net.mcreator.rpgstylemorebosses.world.features.treedecorators` | [DungeonVoidFruitDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidFruitDecorator.java) |

## 全部类（92）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RpgStyleMoreBossesModVariables` | class | SavedData | EventBusSubscriber | 24 | 315 | [RpgStyleMoreBossesModVariables.java](net/mcreator/rpgstylemorebosses/network/RpgStyleMoreBossesModVariables.java) |
| `RpgStyleMoreBossesModEntityRenderers` | class | — | EventBusSubscriber | 1 | 40 | [RpgStyleMoreBossesModEntityRenderers.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModEntityRenderers.java) |
| `EntityAnimationFactory` | class | — | EventBusSubscriber | 1 | 122 | [EntityAnimationFactory.java](net/mcreator/rpgstylemorebosses/init/EntityAnimationFactory.java) |
| `DungeonVoidLeaveDecorator` | class | LeaveVineDecorator | EventBusSubscriber | 5 | 88 | [DungeonVoidLeaveDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidLeaveDecorator.java) |
| `RpgStyleMoreBossesMod` | class | — | Mod | 4 | 91 | [RpgStyleMoreBossesMod.java](net/mcreator/rpgstylemorebosses/RpgStyleMoreBossesMod.java) |
| `PlayerJoinProcedure` | class | — | EventBusSubscriber | 3 | 55 | [PlayerJoinProcedure.java](net/mcreator/rpgstylemorebosses/procedures/PlayerJoinProcedure.java) |
| `NotAdventureProcedure` | class | — | EventBusSubscriber | 4 | 53 | [NotAdventureProcedure.java](net/mcreator/rpgstylemorebosses/procedures/NotAdventureProcedure.java) |
| `SecondDimentionDimension` | class | — | EventBusSubscriber | 4 | 50 | [SecondDimentionDimension.java](net/mcreator/rpgstylemorebosses/world/dimension/SecondDimentionDimension.java) |
| `DungeonVoidTrunkDecorator` | class | TrunkVineDecorator | EventBusSubscriber | 4 | 74 | [DungeonVoidTrunkDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidTrunkDecorator.java) |
| `DungeonVoidFruitDecorator` | class | CocoaDecorator | EventBusSubscriber | 4 | 68 | [DungeonVoidFruitDecorator.java](net/mcreator/rpgstylemorebosses/world/features/treedecorators/DungeonVoidFruitDecorator.java) |
| `RpgStyleMoreBossesModEntities` | class | — | EventBusSubscriber | 3 | 174 | [RpgStyleMoreBossesModEntities.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModEntities.java) |
| `BlockBarrierEntity` | class | PathfinderMob | — | 29 | 247 | [BlockBarrierEntity.java](net/mcreator/rpgstylemorebosses/entity/BlockBarrierEntity.java) |
| `BossCounterEntity` | class | PathfinderMob | — | 30 | 248 | [BossCounterEntity.java](net/mcreator/rpgstylemorebosses/entity/BossCounterEntity.java) |
| `ExoframeBeamEntity` | class | PathfinderMob | — | 31 | 258 | [ExoframeBeamEntity.java](net/mcreator/rpgstylemorebosses/entity/ExoframeBeamEntity.java) |
| `BeaconEntity` | class | PathfinderMob | — | 29 | 252 | [BeaconEntity.java](net/mcreator/rpgstylemorebosses/entity/BeaconEntity.java) |
| `BeaconActiveEntity` | class | PathfinderMob | — | 29 | 241 | [BeaconActiveEntity.java](net/mcreator/rpgstylemorebosses/entity/BeaconActiveEntity.java) |
| `TheExoframeVFX2Entity` | class | PathfinderMob | — | 28 | 237 | [TheExoframeVFX2Entity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeVFX2Entity.java) |
| `TheExoframeSpawnEntity` | class | Monster | — | 25 | 235 | [TheExoframeSpawnEntity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeSpawnEntity.java) |
| `TheExoframeVFXEntity` | class | PathfinderMob | — | 28 | 237 | [TheExoframeVFXEntity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeVFXEntity.java) |
| `KriStrarzEntity` | class | Monster | — | 23 | 208 | [KriStrarzEntity.java](net/mcreator/rpgstylemorebosses/entity/KriStrarzEntity.java) |
| `TheExoframeEntity` | class | Monster | — | 24 | 225 | [TheExoframeEntity.java](net/mcreator/rpgstylemorebosses/entity/TheExoframeEntity.java) |
| `RunicSpiderEntity` | class | Monster | — | 23 | 208 | [RunicSpiderEntity.java](net/mcreator/rpgstylemorebosses/entity/RunicSpiderEntity.java) |
| `ExoframeTickEntity` | class | Monster | — | 20 | 194 | [ExoframeTickEntity.java](net/mcreator/rpgstylemorebosses/entity/ExoframeTickEntity.java) |
| `BlockBarrierOnInitialEntitySpawnProcedure` | class | — | — | 1 | 18 | [BlockBarrierOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BlockBarrierOnInitialEntitySpawnProcedure.java) |
| `BlockBarrierRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BlockBarrierRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BlockBarrierRenderer.java) |
| `TheExoframeOnEntityTickUpdateProcedure` | class | — | — | 2 | 320 | [TheExoframeOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeOnEntityTickUpdateProcedure.java) |
| `ExoframeTickOnEntityTickUpdateProcedure` | class | — | — | 1 | 268 | [ExoframeTickOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ExoframeTickOnEntityTickUpdateProcedure.java) |
| `KriStrarzOnEntityTickUpdateProcedure` | class | — | — | 1 | 217 | [KriStrarzOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/KriStrarzOnEntityTickUpdateProcedure.java) |
| `BlockBarrierLayer` | class | GeoRenderLayer | — | 1 | 48 | [BlockBarrierLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/BlockBarrierLayer.java) |
| `BlockBarrierModel` | class | GeoModel | — | 3 | 20 | [BlockBarrierModel.java](net/mcreator/rpgstylemorebosses/entity/model/BlockBarrierModel.java) |
| `DungeonBricksCubesBlock` | class | Block | — | 1 | 20 | [DungeonBricksCubesBlock.java](net/mcreator/rpgstylemorebosses/block/DungeonBricksCubesBlock.java) |
| `SetBlockProcedure` | class | — | — | 1 | 103 | [SetBlockProcedure.java](net/mcreator/rpgstylemorebosses/procedures/SetBlockProcedure.java) |
| `BeaconRightClickedOnEntityProcedure` | class | — | — | 1 | 105 | [BeaconRightClickedOnEntityProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BeaconRightClickedOnEntityProcedure.java) |
| `ExoframeBeamOnInitialEntitySpawnProcedure` | class | — | — | 1 | 130 | [ExoframeBeamOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ExoframeBeamOnInitialEntitySpawnProcedure.java) |
| `TheExoframeEntityIsHurtProcedure` | class | — | — | 1 | 38 | [TheExoframeEntityIsHurtProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeEntityIsHurtProcedure.java) |
| `BossCounterOnEntityTickUpdateProcedure` | class | — | — | 1 | 78 | [BossCounterOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BossCounterOnEntityTickUpdateProcedure.java) |
| `TheExoframeOnInitialEntitySpawnProcedure` | class | — | — | 1 | 26 | [TheExoframeOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeOnInitialEntitySpawnProcedure.java) |
| `BeaconActivePlayerCollidesWithThisEntityProcedure` | class | — | — | 1 | 59 | [BeaconActivePlayerCollidesWithThisEntityProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BeaconActivePlayerCollidesWithThisEntityProcedure.java) |
| `TheExoframeSpawnOnInitialEntitySpawnProcedure` | class | — | — | 1 | 121 | [TheExoframeSpawnOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeSpawnOnInitialEntitySpawnProcedure.java) |
| `ExoframeTickOnInitialEntitySpawnProcedure` | class | — | — | 1 | 29 | [ExoframeTickOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ExoframeTickOnInitialEntitySpawnProcedure.java) |
| `ChargeProcedure` | class | — | — | 1 | 413 | [ChargeProcedure.java](net/mcreator/rpgstylemorebosses/procedures/ChargeProcedure.java) |
| `RunicSpiderOnEntityTickUpdateProcedure` | class | — | — | 1 | 24 | [RunicSpiderOnEntityTickUpdateProcedure.java](net/mcreator/rpgstylemorebosses/procedures/RunicSpiderOnEntityTickUpdateProcedure.java) |
| `TheExoframeVFXOnInitialEntitySpawnProcedure` | class | — | — | 1 | 18 | [TheExoframeVFXOnInitialEntitySpawnProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TheExoframeVFXOnInitialEntitySpawnProcedure.java) |
| `TheExoframeKeyItem` | class | Item | — | 1 | 25 | [TheExoframeKeyItem.java](net/mcreator/rpgstylemorebosses/item/TheExoframeKeyItem.java) |
| `ExoframePartItem` | class | Item | — | 0 | 12 | [ExoframePartItem.java](net/mcreator/rpgstylemorebosses/item/ExoframePartItem.java) |
| `PizdabobraItem` | class | Item | — | 1 | 24 | [PizdabobraItem.java](net/mcreator/rpgstylemorebosses/item/PizdabobraItem.java) |
| `RunicSpiderRenderer` | class | GeoEntityRenderer | — | 2 | 42 | [RunicSpiderRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/RunicSpiderRenderer.java) |
| `BeaconActiveRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BeaconActiveRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BeaconActiveRenderer.java) |
| `ExoframeTickRenderer` | class | GeoEntityRenderer | — | 3 | 48 | [ExoframeTickRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/ExoframeTickRenderer.java) |
| `TheExoframeSpawnRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [TheExoframeSpawnRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeSpawnRenderer.java) |
| `KriStrarzRenderer` | class | GeoEntityRenderer | — | 2 | 42 | [KriStrarzRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/KriStrarzRenderer.java) |
| `BeaconRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BeaconRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BeaconRenderer.java) |
| `TheExoframeVFXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [TheExoframeVFXRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeVFXRenderer.java) |
| `TheExoframeRenderer` | class | GeoEntityRenderer | — | 3 | 48 | [TheExoframeRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeRenderer.java) |
| `BossCounterRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [BossCounterRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/BossCounterRenderer.java) |
| `ExoframeBeamRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [ExoframeBeamRenderer.java](net/mcreator/rpgstylemorebosses/client/renderer/ExoframeBeamRenderer.java) |
| `TheExoframeVFX2Renderer` | class | GeoEntityRenderer | — | 2 | 44 | [TheExoframeVFX2Renderer.java](net/mcreator/rpgstylemorebosses/client/renderer/TheExoframeVFX2Renderer.java) |
| `RpgStyleMoreBossesModItems` | class | — | — | 1 | 30 | [RpgStyleMoreBossesModItems.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModItems.java) |
| `RpgStyleMoreBossesModBlocks` | class | — | — | 0 | 13 | [RpgStyleMoreBossesModBlocks.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModBlocks.java) |
| `Punch2Procedure` | class | — | — | 1 | 252 | [Punch2Procedure.java](net/mcreator/rpgstylemorebosses/procedures/Punch2Procedure.java) |
| `BossCounterLayer` | class | GeoRenderLayer | — | 1 | 48 | [BossCounterLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/BossCounterLayer.java) |
| `ExoframeBeamLayer` | class | GeoRenderLayer | — | 1 | 48 | [ExoframeBeamLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/ExoframeBeamLayer.java) |
| `BeaconActiveLayer` | class | GeoRenderLayer | — | 1 | 48 | [BeaconActiveLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/BeaconActiveLayer.java) |
| `TheExoframeVFX2Layer` | class | GeoRenderLayer | — | 1 | 48 | [TheExoframeVFX2Layer.java](net/mcreator/rpgstylemorebosses/entity/layer/TheExoframeVFX2Layer.java) |
| `BeaconLayer` | class | GeoRenderLayer | — | 1 | 48 | [BeaconLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/BeaconLayer.java) |
| `TheExoframeLayer` | class | GeoRenderLayer | — | 1 | 48 | [TheExoframeLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/TheExoframeLayer.java) |
| `TheExoframeSpawnLayer` | class | GeoRenderLayer | — | 1 | 48 | [TheExoframeSpawnLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/TheExoframeSpawnLayer.java) |
| `ExoframeTickLayer` | class | GeoRenderLayer | — | 1 | 48 | [ExoframeTickLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/ExoframeTickLayer.java) |
| `TheExoframeVFXLayer` | class | GeoRenderLayer | — | 1 | 48 | [TheExoframeVFXLayer.java](net/mcreator/rpgstylemorebosses/entity/layer/TheExoframeVFXLayer.java) |
| `TheExoframeSpawnModel` | class | GeoModel | — | 3 | 20 | [TheExoframeSpawnModel.java](net/mcreator/rpgstylemorebosses/entity/model/TheExoframeSpawnModel.java) |
| `TheExoframeVFXModel` | class | GeoModel | — | 3 | 20 | [TheExoframeVFXModel.java](net/mcreator/rpgstylemorebosses/entity/model/TheExoframeVFXModel.java) |
| `ExoframeTickModel` | class | GeoModel | — | 3 | 20 | [ExoframeTickModel.java](net/mcreator/rpgstylemorebosses/entity/model/ExoframeTickModel.java) |
| `BeaconModel` | class | GeoModel | — | 3 | 20 | [BeaconModel.java](net/mcreator/rpgstylemorebosses/entity/model/BeaconModel.java) |
| `RunicSpiderModel` | class | GeoModel | — | 3 | 20 | [RunicSpiderModel.java](net/mcreator/rpgstylemorebosses/entity/model/RunicSpiderModel.java) |
| `BeaconActiveModel` | class | GeoModel | — | 3 | 20 | [BeaconActiveModel.java](net/mcreator/rpgstylemorebosses/entity/model/BeaconActiveModel.java) |
| `ExoframeBeamModel` | class | GeoModel | — | 3 | 20 | [ExoframeBeamModel.java](net/mcreator/rpgstylemorebosses/entity/model/ExoframeBeamModel.java) |
| `TheExoframeVFX2Model` | class | GeoModel | — | 3 | 20 | [TheExoframeVFX2Model.java](net/mcreator/rpgstylemorebosses/entity/model/TheExoframeVFX2Model.java) |
| `KriStrarzModel` | class | GeoModel | — | 3 | 20 | [KriStrarzModel.java](net/mcreator/rpgstylemorebosses/entity/model/KriStrarzModel.java) |
| `TheExoframeModel` | class | GeoModel | — | 3 | 20 | [TheExoframeModel.java](net/mcreator/rpgstylemorebosses/entity/model/TheExoframeModel.java) |
| `BossCounterModel` | class | GeoModel | — | 3 | 20 | [BossCounterModel.java](net/mcreator/rpgstylemorebosses/entity/model/BossCounterModel.java) |
| `PunchProcedure` | class | — | — | 1 | 163 | [PunchProcedure.java](net/mcreator/rpgstylemorebosses/procedures/PunchProcedure.java) |
| `HealProcedure` | class | — | — | 1 | 66 | [HealProcedure.java](net/mcreator/rpgstylemorebosses/procedures/HealProcedure.java) |
| `PizdabobraRightclickedProcedure` | class | — | — | 1 | 15 | [PizdabobraRightclickedProcedure.java](net/mcreator/rpgstylemorebosses/procedures/PizdabobraRightclickedProcedure.java) |
| `JumpKickProcedure` | class | — | — | 1 | 133 | [JumpKickProcedure.java](net/mcreator/rpgstylemorebosses/procedures/JumpKickProcedure.java) |
| `BackdashProcedure` | class | — | — | 1 | 61 | [BackdashProcedure.java](net/mcreator/rpgstylemorebosses/procedures/BackdashProcedure.java) |
| `DashPunchProcedure` | class | — | — | 1 | 163 | [DashPunchProcedure.java](net/mcreator/rpgstylemorebosses/procedures/DashPunchProcedure.java) |
| `TickSummonProcedure` | class | — | — | 1 | 94 | [TickSummonProcedure.java](net/mcreator/rpgstylemorebosses/procedures/TickSummonProcedure.java) |
| `SecondDimentionPlayerEntersDimensionProcedure` | class | — | — | 2 | 119 | [SecondDimentionPlayerEntersDimensionProcedure.java](net/mcreator/rpgstylemorebosses/procedures/SecondDimentionPlayerEntersDimensionProcedure.java) |
| `StunMobEffect` | class | MobEffect | — | 1 | 29 | [StunMobEffect.java](net/mcreator/rpgstylemorebosses/potion/StunMobEffect.java) |
| `RpgStyleMoreBossesModTabs` | class | — | — | 0 | 28 | [RpgStyleMoreBossesModTabs.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModTabs.java) |
| `RpgStyleMoreBossesModMobEffects` | class | — | — | 0 | 13 | [RpgStyleMoreBossesModMobEffects.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModMobEffects.java) |
| `RpgStyleMoreBossesModSounds` | class | — | — | 0 | 67 | [RpgStyleMoreBossesModSounds.java](net/mcreator/rpgstylemorebosses/init/RpgStyleMoreBossesModSounds.java) |
