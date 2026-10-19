# Mebahel Creatures Dwemer（`mebahelcreaturesdwarven`）

> jar：`[机械地牢]mebahelcreaturesdwarven.jar` ｜ 版本：`1.0.7-fabric-1.21.1` ｜ 作者：?
> 规模：**72 个类 / 29 个包 / 6303 行** ｜ 事件处理类 0 ｜ Mixin 4 ｜ 注册/管理类 1

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `mebahelcreaturesdwarven.item.weapon` | 7 | `DwarvenArrowItem`、`DwemerWarAxe`、`DwemerDagger` |
| `mebahelcreaturesdwarven.entity.custom` | 5 | `DwemerEntity`、`DwemerCenturionEntity`、`DwemerSphereEntity` |
| `mebahelcreaturesdwarven.entity.ai.dwarven_spider` | 5 | `DwarvenSpiderGuardianMeleeAttackGoal`、`MineOreGoal`、`DwarvenSpiderMeleeAttackGoal` |
| `mebahelcreaturesdwarven.sound` | 4 | `DwemerSphereWalkSoundHandler`、`DwemerBlockSoundClient`、`DwemerSphereWalkLoopSoundInstance` |
| `mebahelcreaturesdwarven.block.block.custom` | 4 | `DwarvenMetalPipeBlock`、`DwemerMetalPipeGearBlock`、`DwemerSpiderBlock` |
| `mebahelcreaturesdwarven.item` | 4 | `ModItems`、`ModItemGroups`、`ModToolMaterial` |
| `mebahelcreaturesdwarven.mixin` | 3 | `DwarvenJigsawStructureHeightLimitMixin`、`DwarvenJigsawStructureIntRangeWrapMixin`、`DwarvenJigsawStructureValidateMixin` |
| `mebahelcreaturesdwarven.block.entity.custom` | 3 | `DwemerChestBlockEntity`、`DwemerMetalPipeGearBlockEntity`、`DwemerSpiderBlockEntity` |
| `mebahelcreaturesdwarven` | 3 | `MebahelCreaturesDwarvenClient`、`MebahelCreaturesDwarvenDataGenerator`、`MebahelCreaturesDwarven` |
| `mebahelcreaturesdwarven.entity.ai.dwarven_centurion` | 2 | `DwarvenCenturionMeleeAttackGoal`、`DwarvenCenturionShootingGoal` |
| `mebahelcreaturesdwarven.entity.ai.dwarven_sphere` | 2 | `DwemerSphereMeleeAttackGoal`、`DwemerSphereShootingGoal` |
| `mebahelcreaturesdwarven.entity.projectile` | 2 | `SteamProjectileEntity`、`DwarvenArrowEntity` |
| `mebahelcreaturesdwarven.block.dwemer_spider_block` | 2 | `DwemerSpiderBlockRenderer`、`DwemerSpiderBlockModel` |
| `mebahelcreaturesdwarven.entity.dwarven_spider_guardian` | 2 | `DwarvenSpiderGuardianRenderer`、`DwarvenSpidersGuardianModel` |
| `mebahelcreaturesdwarven.entity.dwarven_centurion` | 2 | `DwarvenCenturionRenderer`、`DwarvenCenturionModel` |
| `mebahelcreaturesdwarven.entity.dwarven_sphere` | 2 | `DwarvenSphereRenderer`、`DwarvenSphereModel` |
| `mebahelcreaturesdwarven.entity.dwarven_spider` | 2 | `DwarvenSpiderRenderer`、`DwarvenSpidersModel` |
| `mebahelcreaturesdwarven.block.dwemer_chest` | 2 | `DwemerChestRenderer`、`DwemerChestModel` |
| `mebahelcreaturesdwarven.block.dwemer_metal_pipe_gear` | 2 | `DwarvenMetalPipeGearRenderer`、`DwarvenMetalPipeGearModel` |
| `mebahelcreaturesdwarven.util` | 2 | `ClientSoundBridge`、`ProjectileUtil` |
| `mebahelcreaturesdwarven.projectiles.dwemer_centurion` | 2 | `SteamProjectileRenderer`、`SteamProjectileModel` |
| `mebahelcreaturesdwarven.projectiles.dwarven_arrow` | 2 | `DwarvenArrowRenderer`、`DwarvenArrowModel` |
| `mebahelcreaturesdwarven.particle` | 2 | `ModParticles`、`SteamParticle` |
| `mebahelcreaturesdwarven.mixin.accessor` | 1 | `DwarvenJigsawStructureAccessor` |
| `mebahelcreaturesdwarven.block.entity` | 1 | `ModBlockEntities` |
| `mebahelcreaturesdwarven.block.block` | 1 | `ModBlocks` |
| `mebahelcreaturesdwarven.entity` | 1 | `ModEntities` |
| `mebahelcreaturesdwarven.entity.ai` | 1 | `CustomRevengeGoal` |
| `mebahelcreaturesdwarven.config` | 1 | `ModMultiplayerChest` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DwemerEntity` | class | class_1588 | — | 32 | 242 | [DwemerEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerEntity.java) |
| `DwemerCenturionEntity` | class | DwemerEntity | — | 35 | 327 | [DwemerCenturionEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerCenturionEntity.java) |
| `DwemerSphereEntity` | class | DwemerEntity | — | 35 | 383 | [DwemerSphereEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerSphereEntity.java) |
| `DwemerSpiderEntity` | class | DwemerEntity | — | 25 | 282 | [DwemerSpiderEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerSpiderEntity.java) |
| `DwemerSpiderGuardianEntity` | class | DwemerEntity | — | 23 | 230 | [DwemerSpiderGuardianEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerSpiderGuardianEntity.java) |
| `DwarvenCenturionMeleeAttackGoal` | class | class_1352 | — | 24 | 471 | [DwarvenCenturionMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_centurion/DwarvenCenturionMeleeAttackGoal.java) |
| `DwarvenJigsawStructureHeightLimitMixin` | class | — | Mixin | 0 | 52 | [DwarvenJigsawStructureHeightLimitMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureHeightLimitMixin.java) |
| `DwarvenJigsawStructureIntRangeWrapMixin` | class | — | Mixin | 0 | 19 | [DwarvenJigsawStructureIntRangeWrapMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureIntRangeWrapMixin.java) |
| `DwarvenJigsawStructureValidateMixin` | class | — | Mixin | 0 | 44 | [DwarvenJigsawStructureValidateMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureValidateMixin.java) |
| `DwarvenJigsawStructureAccessor` | interface | — | Mixin | 0 | 17 | [DwarvenJigsawStructureAccessor.java](mebahelcreaturesdwarven/mixin/accessor/DwarvenJigsawStructureAccessor.java) |
| `DwemerSphereMeleeAttackGoal` | class | class_1352 | — | 20 | 380 | [DwemerSphereMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_sphere/DwemerSphereMeleeAttackGoal.java) |
| `DwemerSphereWalkSoundHandler` | class | — | Environment | 3 | 58 | [DwemerSphereWalkSoundHandler.java](mebahelcreaturesdwarven/sound/DwemerSphereWalkSoundHandler.java) |
| `DwemerBlockSoundClient` | class | class_1102 | Environment | 5 | 96 | [DwemerBlockSoundClient.java](mebahelcreaturesdwarven/sound/DwemerBlockSoundClient.java) |
| `DwemerChestBlockEntity` | class | BaseChestBlockEntity | — | 5 | 40 | [DwemerChestBlockEntity.java](mebahelcreaturesdwarven/block/entity/custom/DwemerChestBlockEntity.java) |
| `DwemerMetalPipeGearBlockEntity` | class | class_2586 | — | 5 | 62 | [DwemerMetalPipeGearBlockEntity.java](mebahelcreaturesdwarven/block/entity/custom/DwemerMetalPipeGearBlockEntity.java) |
| `DwarvenMetalPipeBlock` | class | class_2248 | — | 11 | 260 | [DwarvenMetalPipeBlock.java](mebahelcreaturesdwarven/block/block/custom/DwarvenMetalPipeBlock.java) |
| `SteamProjectileEntity` | class | class_3857 | — | 10 | 143 | [SteamProjectileEntity.java](mebahelcreaturesdwarven/entity/projectile/SteamProjectileEntity.java) |
| `DwemerSpiderBlockEntity` | class | class_2586 | — | 2 | 27 | [DwemerSpiderBlockEntity.java](mebahelcreaturesdwarven/block/entity/custom/DwemerSpiderBlockEntity.java) |
| `DwemerMetalPipeGearBlock` | class | class_2318 | — | 11 | 109 | [DwemerMetalPipeGearBlock.java](mebahelcreaturesdwarven/block/block/custom/DwemerMetalPipeGearBlock.java) |
| `DwemerSpiderBlockRenderer` | class | GeoBlockRenderer | Environment | 0 | 15 | [DwemerSpiderBlockRenderer.java](mebahelcreaturesdwarven/block/dwemer_spider_block/DwemerSpiderBlockRenderer.java) |
| `DwarvenSpiderGuardianMeleeAttackGoal` | class | class_1352 | — | 13 | 285 | [DwarvenSpiderGuardianMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderGuardianMeleeAttackGoal.java) |
| `MineOreGoal` | class | class_1352 | — | 10 | 210 | [MineOreGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/MineOreGoal.java) |
| `DwarvenSpiderMeleeAttackGoal` | class | class_1352 | — | 14 | 279 | [DwarvenSpiderMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderMeleeAttackGoal.java) |
| `DwarvenArrowEntity` | class | class_1667 | — | 6 | 72 | [DwarvenArrowEntity.java](mebahelcreaturesdwarven/entity/projectile/DwarvenArrowEntity.java) |
| `DwemerSpiderBlock` | class | class_2318 | — | 8 | 132 | [DwemerSpiderBlock.java](mebahelcreaturesdwarven/block/block/custom/DwemerSpiderBlock.java) |
| `ModItems` | class | — | — | 8 | 76 | [ModItems.java](mebahelcreaturesdwarven/item/ModItems.java) |
| `MebahelCreaturesDwarvenClient` | class | ClientModInitializer | Environment | 1 | 41 | [MebahelCreaturesDwarvenClient.java](mebahelcreaturesdwarven/MebahelCreaturesDwarvenClient.java) |
| `DwarvenSpiderGuardianRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenSpiderGuardianRenderer.java](mebahelcreaturesdwarven/entity/dwarven_spider_guardian/DwarvenSpiderGuardianRenderer.java) |
| `DwarvenCenturionRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenCenturionRenderer.java](mebahelcreaturesdwarven/entity/dwarven_centurion/DwarvenCenturionRenderer.java) |
| `DwarvenSphereRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenSphereRenderer.java](mebahelcreaturesdwarven/entity/dwarven_sphere/DwarvenSphereRenderer.java) |
| `DwarvenSpiderRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenSpiderRenderer.java](mebahelcreaturesdwarven/entity/dwarven_spider/DwarvenSpiderRenderer.java) |
| `ModBlockEntities` | class | — | — | 1 | 36 | [ModBlockEntities.java](mebahelcreaturesdwarven/block/entity/ModBlockEntities.java) |
| `ModBlocks` | class | — | — | 3 | 48 | [ModBlocks.java](mebahelcreaturesdwarven/block/block/ModBlocks.java) |
| `DwemerChestBlock` | class | BaseChestBlock | — | 1 | 18 | [DwemerChestBlock.java](mebahelcreaturesdwarven/block/block/custom/DwemerChestBlock.java) |
| `DwemerSpiderBlockModel` | class | GeoModel | Environment | 3 | 30 | [DwemerSpiderBlockModel.java](mebahelcreaturesdwarven/block/dwemer_spider_block/DwemerSpiderBlockModel.java) |
| `DwemerChestRenderer` | class | GeoBlockRenderer | Environment | 0 | 15 | [DwemerChestRenderer.java](mebahelcreaturesdwarven/block/dwemer_chest/DwemerChestRenderer.java) |
| `DwarvenMetalPipeGearRenderer` | class | GeoBlockRenderer | Environment | 1 | 23 | [DwarvenMetalPipeGearRenderer.java](mebahelcreaturesdwarven/block/dwemer_metal_pipe_gear/DwarvenMetalPipeGearRenderer.java) |
| `ModItemGroups` | class | — | — | 1 | 47 | [ModItemGroups.java](mebahelcreaturesdwarven/item/ModItemGroups.java) |
| `DwarvenArrowItem` | class | class_1744 | — | 3 | 42 | [DwarvenArrowItem.java](mebahelcreaturesdwarven/item/weapon/DwarvenArrowItem.java) |
| `ClientSoundBridge` | class | — | — | 2 | 39 | [ClientSoundBridge.java](mebahelcreaturesdwarven/util/ClientSoundBridge.java) |
| `SteamProjectileRenderer` | class | GeoEntityRenderer | Environment | 2 | 46 | [SteamProjectileRenderer.java](mebahelcreaturesdwarven/projectiles/dwemer_centurion/SteamProjectileRenderer.java) |
| `DwarvenArrowRenderer` | class | GeoEntityRenderer | Environment | 2 | 44 | [DwarvenArrowRenderer.java](mebahelcreaturesdwarven/projectiles/dwarven_arrow/DwarvenArrowRenderer.java) |
| `MebahelCreaturesDwarvenDataGenerator` | class | DataGeneratorEntrypoint | Environment | 1 | 13 | [MebahelCreaturesDwarvenDataGenerator.java](mebahelcreaturesdwarven/MebahelCreaturesDwarvenDataGenerator.java) |
| `DwarvenCenturionShootingGoal` | class | class_1352 | — | 6 | 125 | [DwarvenCenturionShootingGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_centurion/DwarvenCenturionShootingGoal.java) |
| `DwemerSphereShootingGoal` | class | class_1352 | — | 8 | 191 | [DwemerSphereShootingGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_sphere/DwemerSphereShootingGoal.java) |
| `DwarvenSpiderGuardianJumpAttackGoal` | class | class_1352 | — | 5 | 115 | [DwarvenSpiderGuardianJumpAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderGuardianJumpAttackGoal.java) |
| `ModToolMaterial` | enum | class_1832 | — | 6 | 58 | [ModToolMaterial.java](mebahelcreaturesdwarven/item/ModToolMaterial.java) |
| `ProjectileUtil` | class | — | — | 1 | 21 | [ProjectileUtil.java](mebahelcreaturesdwarven/util/ProjectileUtil.java) |
| `MebahelCreaturesDwarven` | class | ModInitializer | — | 1 | 44 | [MebahelCreaturesDwarven.java](mebahelcreaturesdwarven/MebahelCreaturesDwarven.java) |
| `ModEntities` | class | — | — | 1 | 42 | [ModEntities.java](mebahelcreaturesdwarven/entity/ModEntities.java) |
| `DwarvenSpidersGuardianModel` | class | GeoModel | Environment | 4 | 46 | [DwarvenSpidersGuardianModel.java](mebahelcreaturesdwarven/entity/dwarven_spider_guardian/DwarvenSpidersGuardianModel.java) |
| `DwarvenCenturionModel` | class | GeoModel | Environment | 4 | 44 | [DwarvenCenturionModel.java](mebahelcreaturesdwarven/entity/dwarven_centurion/DwarvenCenturionModel.java) |
| `CustomRevengeGoal` | class | class_1399 | — | 2 | 28 | [CustomRevengeGoal.java](mebahelcreaturesdwarven/entity/ai/CustomRevengeGoal.java) |
| `DwarvenSpiderWanderFarGoal` | class | class_1379 | — | 3 | 55 | [DwarvenSpiderWanderFarGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderWanderFarGoal.java) |
| `DwarvenSphereModel` | class | GeoModel | Environment | 3 | 25 | [DwarvenSphereModel.java](mebahelcreaturesdwarven/entity/dwarven_sphere/DwarvenSphereModel.java) |
| `DwarvenSpidersModel` | class | GeoModel | Environment | 3 | 25 | [DwarvenSpidersModel.java](mebahelcreaturesdwarven/entity/dwarven_spider/DwarvenSpidersModel.java) |
| `DwemerSphereWalkLoopSoundInstance` | class | class_1101 | Environment | 3 | 53 | [DwemerSphereWalkLoopSoundInstance.java](mebahelcreaturesdwarven/sound/DwemerSphereWalkLoopSoundInstance.java) |
| `ModSounds` | class | — | — | 2 | 57 | [ModSounds.java](mebahelcreaturesdwarven/sound/ModSounds.java) |
| `DwemerChestModel` | class | GeoModel | Environment | 3 | 23 | [DwemerChestModel.java](mebahelcreaturesdwarven/block/dwemer_chest/DwemerChestModel.java) |
| `DwarvenMetalPipeGearModel` | class | GeoModel | Environment | 3 | 23 | [DwarvenMetalPipeGearModel.java](mebahelcreaturesdwarven/block/dwemer_metal_pipe_gear/DwarvenMetalPipeGearModel.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `DwarvenJigsawStructureHeightLimitMixin` | `DwarvenJigsawStructureHeightLimit` | [DwarvenJigsawStructureHeightLimitMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureHeightLimitMixin.java) |
| `DwarvenJigsawStructureIntRangeWrapMixin` | `DwarvenJigsawStructureIntRangeWrap` | [DwarvenJigsawStructureIntRangeWrapMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureIntRangeWrapMixin.java) |
| `DwarvenJigsawStructureValidateMixin` | `DwarvenJigsawStructureValidate` | [DwarvenJigsawStructureValidateMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureValidateMixin.java) |
| `DwarvenJigsawStructureAccessor` | `DwarvenJigsawStructureAccessor` | [DwarvenJigsawStructureAccessor.java](mebahelcreaturesdwarven/mixin/accessor/DwarvenJigsawStructureAccessor.java) |

## 全部类（72）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DwemerEntity` | class | class_1588 | — | 32 | 242 | [DwemerEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerEntity.java) |
| `DwemerCenturionEntity` | class | DwemerEntity | — | 35 | 327 | [DwemerCenturionEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerCenturionEntity.java) |
| `DwemerSphereEntity` | class | DwemerEntity | — | 35 | 383 | [DwemerSphereEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerSphereEntity.java) |
| `DwemerSpiderEntity` | class | DwemerEntity | — | 25 | 282 | [DwemerSpiderEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerSpiderEntity.java) |
| `DwemerSpiderGuardianEntity` | class | DwemerEntity | — | 23 | 230 | [DwemerSpiderGuardianEntity.java](mebahelcreaturesdwarven/entity/custom/DwemerSpiderGuardianEntity.java) |
| `DwarvenCenturionMeleeAttackGoal` | class | class_1352 | — | 24 | 471 | [DwarvenCenturionMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_centurion/DwarvenCenturionMeleeAttackGoal.java) |
| `DwarvenJigsawStructureHeightLimitMixin` | class | — | Mixin | 0 | 52 | [DwarvenJigsawStructureHeightLimitMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureHeightLimitMixin.java) |
| `DwarvenJigsawStructureIntRangeWrapMixin` | class | — | Mixin | 0 | 19 | [DwarvenJigsawStructureIntRangeWrapMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureIntRangeWrapMixin.java) |
| `DwarvenJigsawStructureValidateMixin` | class | — | Mixin | 0 | 44 | [DwarvenJigsawStructureValidateMixin.java](mebahelcreaturesdwarven/mixin/DwarvenJigsawStructureValidateMixin.java) |
| `DwarvenJigsawStructureAccessor` | interface | — | Mixin | 0 | 17 | [DwarvenJigsawStructureAccessor.java](mebahelcreaturesdwarven/mixin/accessor/DwarvenJigsawStructureAccessor.java) |
| `DwemerSphereMeleeAttackGoal` | class | class_1352 | — | 20 | 380 | [DwemerSphereMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_sphere/DwemerSphereMeleeAttackGoal.java) |
| `DwemerSphereWalkSoundHandler` | class | — | Environment | 3 | 58 | [DwemerSphereWalkSoundHandler.java](mebahelcreaturesdwarven/sound/DwemerSphereWalkSoundHandler.java) |
| `DwemerBlockSoundClient` | class | class_1102 | Environment | 5 | 96 | [DwemerBlockSoundClient.java](mebahelcreaturesdwarven/sound/DwemerBlockSoundClient.java) |
| `DwemerChestBlockEntity` | class | BaseChestBlockEntity | — | 5 | 40 | [DwemerChestBlockEntity.java](mebahelcreaturesdwarven/block/entity/custom/DwemerChestBlockEntity.java) |
| `DwemerMetalPipeGearBlockEntity` | class | class_2586 | — | 5 | 62 | [DwemerMetalPipeGearBlockEntity.java](mebahelcreaturesdwarven/block/entity/custom/DwemerMetalPipeGearBlockEntity.java) |
| `DwarvenMetalPipeBlock` | class | class_2248 | — | 11 | 260 | [DwarvenMetalPipeBlock.java](mebahelcreaturesdwarven/block/block/custom/DwarvenMetalPipeBlock.java) |
| `SteamProjectileEntity` | class | class_3857 | — | 10 | 143 | [SteamProjectileEntity.java](mebahelcreaturesdwarven/entity/projectile/SteamProjectileEntity.java) |
| `DwemerSpiderBlockEntity` | class | class_2586 | — | 2 | 27 | [DwemerSpiderBlockEntity.java](mebahelcreaturesdwarven/block/entity/custom/DwemerSpiderBlockEntity.java) |
| `DwemerMetalPipeGearBlock` | class | class_2318 | — | 11 | 109 | [DwemerMetalPipeGearBlock.java](mebahelcreaturesdwarven/block/block/custom/DwemerMetalPipeGearBlock.java) |
| `DwemerSpiderBlockRenderer` | class | GeoBlockRenderer | Environment | 0 | 15 | [DwemerSpiderBlockRenderer.java](mebahelcreaturesdwarven/block/dwemer_spider_block/DwemerSpiderBlockRenderer.java) |
| `DwarvenSpiderGuardianMeleeAttackGoal` | class | class_1352 | — | 13 | 285 | [DwarvenSpiderGuardianMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderGuardianMeleeAttackGoal.java) |
| `MineOreGoal` | class | class_1352 | — | 10 | 210 | [MineOreGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/MineOreGoal.java) |
| `DwarvenSpiderMeleeAttackGoal` | class | class_1352 | — | 14 | 279 | [DwarvenSpiderMeleeAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderMeleeAttackGoal.java) |
| `DwarvenArrowEntity` | class | class_1667 | — | 6 | 72 | [DwarvenArrowEntity.java](mebahelcreaturesdwarven/entity/projectile/DwarvenArrowEntity.java) |
| `DwemerSpiderBlock` | class | class_2318 | — | 8 | 132 | [DwemerSpiderBlock.java](mebahelcreaturesdwarven/block/block/custom/DwemerSpiderBlock.java) |
| `ModItems` | class | — | — | 8 | 76 | [ModItems.java](mebahelcreaturesdwarven/item/ModItems.java) |
| `MebahelCreaturesDwarvenClient` | class | ClientModInitializer | Environment | 1 | 41 | [MebahelCreaturesDwarvenClient.java](mebahelcreaturesdwarven/MebahelCreaturesDwarvenClient.java) |
| `DwarvenSpiderGuardianRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenSpiderGuardianRenderer.java](mebahelcreaturesdwarven/entity/dwarven_spider_guardian/DwarvenSpiderGuardianRenderer.java) |
| `DwarvenCenturionRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenCenturionRenderer.java](mebahelcreaturesdwarven/entity/dwarven_centurion/DwarvenCenturionRenderer.java) |
| `DwarvenSphereRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenSphereRenderer.java](mebahelcreaturesdwarven/entity/dwarven_sphere/DwarvenSphereRenderer.java) |
| `DwarvenSpiderRenderer` | class | GeoEntityRenderer | Environment | 2 | 28 | [DwarvenSpiderRenderer.java](mebahelcreaturesdwarven/entity/dwarven_spider/DwarvenSpiderRenderer.java) |
| `ModBlockEntities` | class | — | — | 1 | 36 | [ModBlockEntities.java](mebahelcreaturesdwarven/block/entity/ModBlockEntities.java) |
| `ModBlocks` | class | — | — | 3 | 48 | [ModBlocks.java](mebahelcreaturesdwarven/block/block/ModBlocks.java) |
| `DwemerChestBlock` | class | BaseChestBlock | — | 1 | 18 | [DwemerChestBlock.java](mebahelcreaturesdwarven/block/block/custom/DwemerChestBlock.java) |
| `DwemerSpiderBlockModel` | class | GeoModel | Environment | 3 | 30 | [DwemerSpiderBlockModel.java](mebahelcreaturesdwarven/block/dwemer_spider_block/DwemerSpiderBlockModel.java) |
| `DwemerChestRenderer` | class | GeoBlockRenderer | Environment | 0 | 15 | [DwemerChestRenderer.java](mebahelcreaturesdwarven/block/dwemer_chest/DwemerChestRenderer.java) |
| `DwarvenMetalPipeGearRenderer` | class | GeoBlockRenderer | Environment | 1 | 23 | [DwarvenMetalPipeGearRenderer.java](mebahelcreaturesdwarven/block/dwemer_metal_pipe_gear/DwarvenMetalPipeGearRenderer.java) |
| `ModItemGroups` | class | — | — | 1 | 47 | [ModItemGroups.java](mebahelcreaturesdwarven/item/ModItemGroups.java) |
| `DwarvenArrowItem` | class | class_1744 | — | 3 | 42 | [DwarvenArrowItem.java](mebahelcreaturesdwarven/item/weapon/DwarvenArrowItem.java) |
| `ClientSoundBridge` | class | — | — | 2 | 39 | [ClientSoundBridge.java](mebahelcreaturesdwarven/util/ClientSoundBridge.java) |
| `SteamProjectileRenderer` | class | GeoEntityRenderer | Environment | 2 | 46 | [SteamProjectileRenderer.java](mebahelcreaturesdwarven/projectiles/dwemer_centurion/SteamProjectileRenderer.java) |
| `DwarvenArrowRenderer` | class | GeoEntityRenderer | Environment | 2 | 44 | [DwarvenArrowRenderer.java](mebahelcreaturesdwarven/projectiles/dwarven_arrow/DwarvenArrowRenderer.java) |
| `MebahelCreaturesDwarvenDataGenerator` | class | DataGeneratorEntrypoint | Environment | 1 | 13 | [MebahelCreaturesDwarvenDataGenerator.java](mebahelcreaturesdwarven/MebahelCreaturesDwarvenDataGenerator.java) |
| `DwarvenCenturionShootingGoal` | class | class_1352 | — | 6 | 125 | [DwarvenCenturionShootingGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_centurion/DwarvenCenturionShootingGoal.java) |
| `DwemerSphereShootingGoal` | class | class_1352 | — | 8 | 191 | [DwemerSphereShootingGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_sphere/DwemerSphereShootingGoal.java) |
| `DwarvenSpiderGuardianJumpAttackGoal` | class | class_1352 | — | 5 | 115 | [DwarvenSpiderGuardianJumpAttackGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderGuardianJumpAttackGoal.java) |
| `ModToolMaterial` | enum | class_1832 | — | 6 | 58 | [ModToolMaterial.java](mebahelcreaturesdwarven/item/ModToolMaterial.java) |
| `ProjectileUtil` | class | — | — | 1 | 21 | [ProjectileUtil.java](mebahelcreaturesdwarven/util/ProjectileUtil.java) |
| `MebahelCreaturesDwarven` | class | ModInitializer | — | 1 | 44 | [MebahelCreaturesDwarven.java](mebahelcreaturesdwarven/MebahelCreaturesDwarven.java) |
| `ModEntities` | class | — | — | 1 | 42 | [ModEntities.java](mebahelcreaturesdwarven/entity/ModEntities.java) |
| `DwarvenSpidersGuardianModel` | class | GeoModel | Environment | 4 | 46 | [DwarvenSpidersGuardianModel.java](mebahelcreaturesdwarven/entity/dwarven_spider_guardian/DwarvenSpidersGuardianModel.java) |
| `DwarvenCenturionModel` | class | GeoModel | Environment | 4 | 44 | [DwarvenCenturionModel.java](mebahelcreaturesdwarven/entity/dwarven_centurion/DwarvenCenturionModel.java) |
| `CustomRevengeGoal` | class | class_1399 | — | 2 | 28 | [CustomRevengeGoal.java](mebahelcreaturesdwarven/entity/ai/CustomRevengeGoal.java) |
| `DwarvenSpiderWanderFarGoal` | class | class_1379 | — | 3 | 55 | [DwarvenSpiderWanderFarGoal.java](mebahelcreaturesdwarven/entity/ai/dwarven_spider/DwarvenSpiderWanderFarGoal.java) |
| `DwarvenSphereModel` | class | GeoModel | Environment | 3 | 25 | [DwarvenSphereModel.java](mebahelcreaturesdwarven/entity/dwarven_sphere/DwarvenSphereModel.java) |
| `DwarvenSpidersModel` | class | GeoModel | Environment | 3 | 25 | [DwarvenSpidersModel.java](mebahelcreaturesdwarven/entity/dwarven_spider/DwarvenSpidersModel.java) |
| `DwemerSphereWalkLoopSoundInstance` | class | class_1101 | Environment | 3 | 53 | [DwemerSphereWalkLoopSoundInstance.java](mebahelcreaturesdwarven/sound/DwemerSphereWalkLoopSoundInstance.java) |
| `ModSounds` | class | — | — | 2 | 57 | [ModSounds.java](mebahelcreaturesdwarven/sound/ModSounds.java) |
| `DwemerChestModel` | class | GeoModel | Environment | 3 | 23 | [DwemerChestModel.java](mebahelcreaturesdwarven/block/dwemer_chest/DwemerChestModel.java) |
| `DwarvenMetalPipeGearModel` | class | GeoModel | Environment | 3 | 23 | [DwarvenMetalPipeGearModel.java](mebahelcreaturesdwarven/block/dwemer_metal_pipe_gear/DwarvenMetalPipeGearModel.java) |
| `ModMultiplayerChest` | class | — | — | 2 | 61 | [ModMultiplayerChest.java](mebahelcreaturesdwarven/config/ModMultiplayerChest.java) |
| `ModSpawnEggs` | class | — | — | 2 | 43 | [ModSpawnEggs.java](mebahelcreaturesdwarven/item/ModSpawnEggs.java) |
| `DwemerWarAxe` | class | class_1743 | — | 2 | 38 | [DwemerWarAxe.java](mebahelcreaturesdwarven/item/weapon/DwemerWarAxe.java) |
| `DwemerDagger` | class | class_1829 | — | 2 | 38 | [DwemerDagger.java](mebahelcreaturesdwarven/item/weapon/DwemerDagger.java) |
| `DwemerMace` | class | class_1829 | — | 2 | 38 | [DwemerMace.java](mebahelcreaturesdwarven/item/weapon/DwemerMace.java) |
| `DwemerSword` | class | class_1829 | — | 2 | 38 | [DwemerSword.java](mebahelcreaturesdwarven/item/weapon/DwemerSword.java) |
| `DwemerAxe` | class | class_1743 | — | 2 | 38 | [DwemerAxe.java](mebahelcreaturesdwarven/item/weapon/DwemerAxe.java) |
| `DwemerWarhammer` | class | class_1829 | — | 2 | 38 | [DwemerWarhammer.java](mebahelcreaturesdwarven/item/weapon/DwemerWarhammer.java) |
| `ModParticles` | class | — | — | 1 | 18 | [ModParticles.java](mebahelcreaturesdwarven/particle/ModParticles.java) |
| `SteamParticle` | class | class_4003 | Environment | 4 | 67 | [SteamParticle.java](mebahelcreaturesdwarven/particle/SteamParticle.java) |
| `SteamProjectileModel` | class | GeoModel | Environment | 3 | 23 | [SteamProjectileModel.java](mebahelcreaturesdwarven/projectiles/dwemer_centurion/SteamProjectileModel.java) |
| `DwarvenArrowModel` | class | GeoModel | Environment | 3 | 23 | [DwarvenArrowModel.java](mebahelcreaturesdwarven/projectiles/dwarven_arrow/DwarvenArrowModel.java) |
