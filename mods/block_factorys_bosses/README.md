# Bosses'Rise（`block_factorys_bosses`）

> jar：`[更多Boss]blockfactorysbosses.jar` ｜ 版本：`2.1.2` ｜ 作者：Block Factory
> 规模：**539 个类 / 78 个包 / 385005 行** ｜ 事件处理类 54 ｜ Mixin 26 ｜ 注册/管理类 12

**依赖**：`minecraft`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.unusual.block_factorys_bosses.procedures` | 33 | `BossCancelDie2Procedure`、`NoHitBossBattleTriggerProcedure`、`LootTableStickRightclickedOnBlockProcedure` |
| `net.unusual.block_factorys_bosses.client.particle` | 33 | `RedSparkParticle`、`ExplosionParticle`、`BlazingCloudParticle` |
| `net.unusual.block_factorys_bosses.client.model` | 31 | `ModelCustomModel`、`Modeldagger`、`Modelsoul_skeleton` |
| `net.unusual.block_factorys_bosses.item` | 27 | `SandwormGauntletItem`、`IceGauntletItem`、`KnightSwordItem` |
| `net.unusual.block_factorys_bosses.client.particle.knight` | 27 | `IntroductionAttackEvent`、`IntroductionAttackSoulOnce`、`IntroductionAttack2` |
| `net.unusual.block_factorys_bosses.block` | 25 | `HugeDoorBlock`、`DragonSkullBlock`、`BossSpawnerBlock` |
| `net.unusual.block_factorys_bosses.init` | 22 | `BossesRiseEntityRenderers`、`BossesRiseConfigs`、`BossesRiseEntities` |
| `net.unusual.block_factorys_bosses.client.renderer` | 19 | `KnightFirstPersonRenderer`、`DragonBonesFirstPersonRenderer`、`DaggerItemRenderer` |
| `net.unusual.block_factorys_bosses.geckolib` | 16 | `PlankBlockEntityRenderer`、`ServerAnimationPlayer`、`MatrixAnimationProcessorTwo` |
| `net.unusual.block_factorys_bosses.client.animation.yeti` | 16 | `yetiHandSwipe1`、`yetiThrowSnowball`、`yetiGroundSmash` |
| `net.unusual.block_factorys_bosses.mixins.client` | 13 | `MouseHandlerMixin`、`MusicManagerMixin`、`LevelRendererMixin` |
| `net.unusual.block_factorys_bosses.client.particle.yeti` | 13 | `YetiDeathSoft`、`IceWhirlpool2`、`IceWhirlpool4` |
| `net.unusual.block_factorys_bosses.state` | 12 | `StateRegistry`、`StateController`、`LambdaDataState` |
| `net.unusual.block_factorys_bosses.geckolib.util` | 10 | `CustomItemGeoModel`、`CustomEntityGeoModel`、`CustomBlockGeoModel` |
| `net.unusual.block_factorys_bosses.network` | 9 | `BlockFactorysBossesModVariables`、`ShutUpPacket`、`ParticleEventMessage` |
| `net.unusual.block_factorys_bosses.entity.projectile` | 9 | `PoisonSpitPrEntity`、`SwordWaveEntity`、`CannonballEntity` |
| `net.unusual.block_factorys_bosses.block.entity` | 9 | `KrakenSpawnerBlockEntity`、`BossSpawnerBlockEntity`、`PrisonDoorBlockEntity` |
| `net.unusual.block_factorys_bosses.util` | 8 | `BossHandling`、`LootUtil`、`ItemAttributeModifiersUtil` |
| `net.unusual.block_factorys_bosses.entity` | 8 | `SandColumnEntity`、`IParticleAttachment`、`FireAreaEntity` |
| `net.unusual.block_factorys_bosses.client.animation.sandworm` | 8 | `sandwormDeath`、`sandwormSpawn`、`sandwormIdleSpit` |
| `net.unusual.block_factorys_bosses.data.gen` | 7 | `BossesRiseLootGen`、`BossesRiseBlockModelGen`、`BossesRiseAdvancementGen` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock.appearance` | 7 | `ParticleUVFlipbook`、`ParticleAppearanceBillboard`、`FacingCameraMode` |
| `net.unusual.block_factorys_bosses.client.camera.renderer` | 6 | `CameraRenderer`、`ShakeRenderer`、`BlackRenderer` |
| `net.unusual.block_factorys_bosses.entity.boss.yeti` | 6 | `YetiEntity`、`IceSpikeClusterEntity`、`IceSpikeEntity` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock.event` | 6 | `EmitterLifetimeEvents`、`ParticleEventSequence`、`ParticleEventSoundEffect` |
| `net.unusual.block_factorys_bosses.client.particle.util` | 6 | `SubParticleEmitter`、`SubParticle`、`ColorCurve` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock` | 6 | `AbstractBedrockParticle`、`BedrockParticle`、`BedrockParticleEffect` |
| `net.unusual.block_factorys_bosses.geckolib.boss.kraken` | 6 | `KrakenTentacleRenderer`、`KrakenCinematicRenderer`、`KrakenCannonRenderer` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock.curve` | 6 | `BezierChainCurve`、`ColorGradient`、`ParticleCurve` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock.shape` | 6 | `EmitterShape`、`EmitterShapeSphere`、`EmitterShapeCustom` |
| `net.unusual.block_factorys_bosses.entity.decoration` | 5 | `CannonEntity`、`AnchorEntity`、`CageEntity` |
| `net.unusual.block_factorys_bosses.mixins.server` | 5 | `ServerBossEventMixin`、`EndDragonFightMixin`、`GeckolibMolangQueries` |
| `net.unusual.block_factorys_bosses.mixins` | 5 | `KrakenTridentTridentItemMixin`、`ControlMixin`、`RollMixin` |
| `net.unusual.block_factorys_bosses.data.gen.tag` | 5 | `BossesRiseEntityTagGen`、`BossesRiseItemTagGen`、`BossesRiseBlockTagGen` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock.vector` | 5 | `ParticleVector2`、`ParticleVector`、`OutwardParticleDirection` |
| `net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime` | 5 | `ParticleLifetimeExpression`、`EmitterLifetimeExpression`、`EmitterLifetimeLooping` |
| `net.unusual.block_factorys_bosses.attachment.entity` | 4 | `PlayerAnimationHandler`、`GauntletAttachment`、`RollAttachment` |
| `net.unusual.block_factorys_bosses.entity.boss.kraken` | 4 | `KrakenEntity`、`KrakenTentacleEntity`、`KrakenCinematicEntity` |
| `net.unusual.block_factorys_bosses.entity.boss.kraken.summons` | 4 | `CrossbowPirateEntity`、`PirateCaptainEntity`、`PirateRookEntity` |
| `net.unusual.block_factorys_bosses.client` | 4 | `ClientParticleHandler`、`CinematicEntity`、`AnimationTickHolder` |
| … | | 其余 38 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEvents` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 16 | 294 | [ClientEvents.java](net/unusual/block_factorys_bosses/event/ClientEvents.java) |
| `AbstractBossEntity` | class | Monster | EventBusSubscriber、MethodsReturnNonnullB | 69 | 576 | [AbstractBossEntity.java](net/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity.java) |
| `DragonArmorEvents` | class | — | EventBusSubscriber | 5 | 132 | [DragonArmorEvents.java](net/unusual/block_factorys_bosses/event/DragonArmorEvents.java) |
| `StructureDestructionEvents` | class | — | EventBusSubscriber | 6 | 106 | [StructureDestructionEvents.java](net/unusual/block_factorys_bosses/event/StructureDestructionEvents.java) |
| `BlockFactorysBossesModVariables` | class | SavedData | EventBusSubscriber | 21 | 281 | [BlockFactorysBossesModVariables.java](net/unusual/block_factorys_bosses/network/BlockFactorysBossesModVariables.java) |
| `SandwormGauntletItem` | class | Item | EventBusSubscriber、FieldsAreNonnullByDef | 24 | 302 | [SandwormGauntletItem.java](net/unusual/block_factorys_bosses/item/SandwormGauntletItem.java) |
| `PlayerAnimationHandler` | class | INBTSerializable<CompoundTag> | EventBusSubscriber、MethodsReturnNonnullB | 13 | 174 | [PlayerAnimationHandler.java](net/unusual/block_factorys_bosses/attachment/entity/PlayerAnimationHandler.java) |
| `IceGauntletItem` | class | ShieldItem | EventBusSubscriber、MethodsReturnNonnullB | 17 | 290 | [IceGauntletItem.java](net/unusual/block_factorys_bosses/item/IceGauntletItem.java) |
| `PoisonSpitPrEntity` | class | AbstractArrow | EventBusSubscriber | 14 | 211 | [PoisonSpitPrEntity.java](net/unusual/block_factorys_bosses/entity/projectile/PoisonSpitPrEntity.java) |
| `KnightSwordItem` | class | AnimatedSwordItem | EventBusSubscriber | 16 | 191 | [KnightSwordItem.java](net/unusual/block_factorys_bosses/item/KnightSwordItem.java) |
| `KnightItem` | class | ArmorItem | EventBusSubscriber | 12 | 237 | [KnightItem.java](net/unusual/block_factorys_bosses/item/KnightItem.java) |
| `BossesRiseEntityRenderers` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 8 | 199 | [BossesRiseEntityRenderers.java](net/unusual/block_factorys_bosses/init/BossesRiseEntityRenderers.java) |
| `DaggerItem` | class | AnimatedSwordItem | EventBusSubscriber | 11 | 107 | [DaggerItem.java](net/unusual/block_factorys_bosses/item/DaggerItem.java) |
| `WarriorSwordItem` | class | AnimatedSwordItem | EventBusSubscriber | 10 | 79 | [WarriorSwordItem.java](net/unusual/block_factorys_bosses/item/WarriorSwordItem.java) |
| `LargeSwordItem` | class | AnimatedSwordItem | EventBusSubscriber | 10 | 102 | [LargeSwordItem.java](net/unusual/block_factorys_bosses/item/LargeSwordItem.java) |
| `CameraRenderer` | class | CameraHandler | EventBusSubscriber | 10 | 194 | [CameraRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/CameraRenderer.java) |
| `GauntletAttachment` | class | INBTSerializable<CompoundTag> | EventBusSubscriber、MethodsReturnNonnullB | 14 | 464 | [GauntletAttachment.java](net/unusual/block_factorys_bosses/attachment/entity/GauntletAttachment.java) |
| `RollAttachment` | class | INBTSerializable<CompoundTag> | EventBusSubscriber、MethodsReturnNonnullB | 18 | 271 | [RollAttachment.java](net/unusual/block_factorys_bosses/attachment/entity/RollAttachment.java) |
| `BossesRiseConfigs` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 1 | 25 | [BossesRiseConfigs.java](net/unusual/block_factorys_bosses/init/BossesRiseConfigs.java) |
| `ShutUpPacket` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 3 | 56 | [ShutUpPacket.java](net/unusual/block_factorys_bosses/network/ShutUpPacket.java) |
| `ParticleEventMessage` | record | CustomPacketPayload | EventBusSubscriber、FieldsAreNonnullByDef | 3 | 62 | [ParticleEventMessage.java](net/unusual/block_factorys_bosses/network/ParticleEventMessage.java) |
| `YetiEntity` | class | AbstractBossEntity | MethodsReturnNonnullByDefault、Parameters | 47 | 2130 | [YetiEntity.java](net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.java) |
| `DragonBonesItem` | class | ArmorItem | EventBusSubscriber | 8 | 142 | [DragonBonesItem.java](net/unusual/block_factorys_bosses/item/DragonBonesItem.java) |
| `UnderworldArenaKeyItem` | class | Item | EventBusSubscriber | 7 | 74 | [UnderworldArenaKeyItem.java](net/unusual/block_factorys_bosses/item/UnderworldArenaKeyItem.java) |
| `PosDebugCommand` | class | — | EventBusSubscriber | 1 | 51 | [PosDebugCommand.java](net/unusual/block_factorys_bosses/command/PosDebugCommand.java) |
| `KrakenShipStructure` | class | Structure | EventBusSubscriber | 8 | 249 | [KrakenShipStructure.java](net/unusual/block_factorys_bosses/structures/KrakenShipStructure.java) |
| `KnightFirstPersonRenderer` | class | — | EventBusSubscriber | 2 | 53 | [KnightFirstPersonRenderer.java](net/unusual/block_factorys_bosses/client/renderer/KnightFirstPersonRenderer.java) |
| `DragonBonesFirstPersonRenderer` | class | — | EventBusSubscriber | 2 | 69 | [DragonBonesFirstPersonRenderer.java](net/unusual/block_factorys_bosses/client/renderer/DragonBonesFirstPersonRenderer.java) |
| `ShakeRenderer` | class | CameraHandler | EventBusSubscriber | 2 | 40 | [ShakeRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/ShakeRenderer.java) |
| `BlackRenderer` | class | CameraHandler | EventBusSubscriber | 3 | 48 | [BlackRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/BlackRenderer.java) |
| `BarsRenderer` | class | CameraHandler | EventBusSubscriber | 3 | 59 | [BarsRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/BarsRenderer.java) |
| `FovRenderer` | class | CameraHandler | EventBusSubscriber | 1 | 20 | [FovRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/FovRenderer.java) |
| `BossesRiseEntities` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 3 | 426 | [BossesRiseEntities.java](net/unusual/block_factorys_bosses/init/BossesRiseEntities.java) |
| `BossesRiseBlockEntities` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 2 | 69 | [BossesRiseBlockEntities.java](net/unusual/block_factorys_bosses/init/BossesRiseBlockEntities.java) |
| `BossesRise` | class | — | Mod | 7 | 160 | [BossesRise.java](net/unusual/block_factorys_bosses/BossesRise.java) |
| `BossesRiseDataGen` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 1 | 57 | [BossesRiseDataGen.java](net/unusual/block_factorys_bosses/data/BossesRiseDataGen.java) |
| `BossesRiseParticles` | class | — | EventBusSubscriber | 2 | 230 | [BossesRiseParticles.java](net/unusual/block_factorys_bosses/init/BossesRiseParticles.java) |
| `PlayerPushMessage` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 3 | 51 | [PlayerPushMessage.java](net/unusual/block_factorys_bosses/network/PlayerPushMessage.java) |
| `PlayerAnimationMessage` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 3 | 67 | [PlayerAnimationMessage.java](net/unusual/block_factorys_bosses/network/PlayerAnimationMessage.java) |
| `AttachmentDiedMessage` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 3 | 60 | [AttachmentDiedMessage.java](net/unusual/block_factorys_bosses/network/AttachmentDiedMessage.java) |
| `IceGauntletMessage` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 3 | 89 | [IceGauntletMessage.java](net/unusual/block_factorys_bosses/network/IceGauntletMessage.java) |
| `UpdateBossBarTypeMessage` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 3 | 53 | [UpdateBossBarTypeMessage.java](net/unusual/block_factorys_bosses/network/UpdateBossBarTypeMessage.java) |
| `DodgeRollMessage` | record | CustomPacketPayload | EventBusSubscriber、MethodsReturnNonnullB | 4 | 65 | [DodgeRollMessage.java](net/unusual/block_factorys_bosses/network/DodgeRollMessage.java) |
| `CannonEntity` | class | BlockAttachedEntity | MethodsReturnNonnullByDefault、Parameters | 41 | 438 | [CannonEntity.java](net/unusual/block_factorys_bosses/entity/decoration/CannonEntity.java) |
| `BossCancelDie2Procedure` | class | — | EventBusSubscriber | 3 | 82 | [BossCancelDie2Procedure.java](net/unusual/block_factorys_bosses/procedures/BossCancelDie2Procedure.java) |
| `NoHitBossBattleTriggerProcedure` | class | — | EventBusSubscriber | 3 | 76 | [NoHitBossBattleTriggerProcedure.java](net/unusual/block_factorys_bosses/procedures/NoHitBossBattleTriggerProcedure.java) |
| `BossesRiseAttributes` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 1 | 29 | [BossesRiseAttributes.java](net/unusual/block_factorys_bosses/init/BossesRiseAttributes.java) |
| `BossesRiseModels` | class | — | EventBusSubscriber | 1 | 76 | [BossesRiseModels.java](net/unusual/block_factorys_bosses/init/BossesRiseModels.java) |
| `BossesRiseKeyMappings` | class | — | EventBusSubscriber、MethodsReturnNonnullB | 3 | 53 | [BossesRiseKeyMappings.java](net/unusual/block_factorys_bosses/init/BossesRiseKeyMappings.java) |
| `KrakenEntity` | class | AbstractStateBossEntity | FieldsAreNonnullByDefault、MethodsReturnN | 78 | 1085 | [KrakenEntity.java](net/unusual/block_factorys_bosses/entity/boss/kraken/KrakenEntity.java) |
| `SandwormEntity` | class | AbstractStateBossEntity | MethodsReturnNonnullByDefault、Parameters | 92 | 1893 | [SandwormEntity.java](net/unusual/block_factorys_bosses/entity/boss/sandworm/SandwormEntity.java) |
| `InfernalDragonEntity` | class | AbstractBossEntity | MethodsReturnNonnullByDefault、Parameters | 87 | 1898 | [InfernalDragonEntity.java](net/unusual/block_factorys_bosses/entity/boss/dragon/boss/InfernalDragonEntity.java) |
| `UnderworldKnightEntity` | class | AbstractBossEntity | MethodsReturnNonnullByDefault、Parameters | 51 | 1585 | [UnderworldKnightEntity.java](net/unusual/block_factorys_bosses/entity/boss/knight/UnderworldKnightEntity.java) |
| `KrakenTentacleEntity` | class | Monster | FieldsAreNonnullByDefault、MethodsReturnN | 67 | 790 | [KrakenTentacleEntity.java](net/unusual/block_factorys_bosses/entity/boss/kraken/KrakenTentacleEntity.java) |
| `KrakenSpawnerBlockEntity` | class | BlockEntity | MethodsReturnNonnullByDefault、Parameters | 10 | 208 | [KrakenSpawnerBlockEntity.java](net/unusual/block_factorys_bosses/block/entity/KrakenSpawnerBlockEntity.java) |
| `AnimatableManagerAccessor` | interface | — | Mixin | 0 | 12 | [AnimatableManagerAccessor.java](net/unusual/block_factorys_bosses/mixins/accessor/AnimatableManagerAccessor.java) |
| `MouseHandlerMixin` | class | — | Mixin | 1 | 33 | [MouseHandlerMixin.java](net/unusual/block_factorys_bosses/mixins/client/MouseHandlerMixin.java) |
| `MusicManagerMixin` | class | — | Mixin | 1 | 58 | [MusicManagerMixin.java](net/unusual/block_factorys_bosses/mixins/client/MusicManagerMixin.java) |
| `AbstractStateBossEntity` | class | Monster | MethodsReturnNonnullByDefault、Parameters | 56 | 464 | [AbstractStateBossEntity.java](net/unusual/block_factorys_bosses/entity/boss/AbstractStateBossEntity.java) |
| `IceSpikeClusterEntity` | class | Entity | MethodsReturnNonnullByDefault、Parameters | 31 | 333 | [IceSpikeClusterEntity.java](net/unusual/block_factorys_bosses/entity/boss/yeti/IceSpikeClusterEntity.java) |

## 事件处理（`@SubscribeEvent`，54 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEvents` | 13 | `net.unusual.block_factorys_bosses.event` | [ClientEvents.java](net/unusual/block_factorys_bosses/event/ClientEvents.java) |
| `BlockFactorysBossesModVariables` | 7 | `net.unusual.block_factorys_bosses.network` | [BlockFactorysBossesModVariables.java](net/unusual/block_factorys_bosses/network/BlockFactorysBossesModVariables.java) |
| `BossHandling` | 6 | `net.unusual.block_factorys_bosses.util` | [BossHandling.java](net/unusual/block_factorys_bosses/util/BossHandling.java) |
| `GauntletAttachment` | 4 | `net.unusual.block_factorys_bosses.attachment.entity` | [GauntletAttachment.java](net/unusual/block_factorys_bosses/attachment/entity/GauntletAttachment.java) |
| `RollAttachment` | 4 | `net.unusual.block_factorys_bosses.attachment.entity` | [RollAttachment.java](net/unusual/block_factorys_bosses/attachment/entity/RollAttachment.java) |
| `StructureDestructionEvents` | 3 | `net.unusual.block_factorys_bosses.event` | [StructureDestructionEvents.java](net/unusual/block_factorys_bosses/event/StructureDestructionEvents.java) |
| `CameraRenderer` | 3 | `net.unusual.block_factorys_bosses.client.camera.renderer` | [CameraRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/CameraRenderer.java) |
| `DragonArmorEvents` | 2 | `net.unusual.block_factorys_bosses.event` | [DragonArmorEvents.java](net/unusual/block_factorys_bosses/event/DragonArmorEvents.java) |
| `BlackRenderer` | 2 | `net.unusual.block_factorys_bosses.client.camera.renderer` | [BlackRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/BlackRenderer.java) |
| `BarsRenderer` | 2 | `net.unusual.block_factorys_bosses.client.camera.renderer` | [BarsRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/BarsRenderer.java) |
| `BossesRiseEntities` | 2 | `net.unusual.block_factorys_bosses.init` | [BossesRiseEntities.java](net/unusual/block_factorys_bosses/init/BossesRiseEntities.java) |
| `BossesRiseKeyMappings` | 2 | `net.unusual.block_factorys_bosses.init` | [BossesRiseKeyMappings.java](net/unusual/block_factorys_bosses/init/BossesRiseKeyMappings.java) |
| `AbstractBossEntity` | 1 | `net.unusual.block_factorys_bosses.entity.boss` | [AbstractBossEntity.java](net/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity.java) |
| `SandwormGauntletItem` | 1 | `net.unusual.block_factorys_bosses.item` | [SandwormGauntletItem.java](net/unusual/block_factorys_bosses/item/SandwormGauntletItem.java) |
| `PlayerAnimationHandler` | 1 | `net.unusual.block_factorys_bosses.attachment.entity` | [PlayerAnimationHandler.java](net/unusual/block_factorys_bosses/attachment/entity/PlayerAnimationHandler.java) |
| `IceGauntletItem` | 1 | `net.unusual.block_factorys_bosses.item` | [IceGauntletItem.java](net/unusual/block_factorys_bosses/item/IceGauntletItem.java) |
| `PoisonSpitPrEntity` | 1 | `net.unusual.block_factorys_bosses.entity.projectile` | [PoisonSpitPrEntity.java](net/unusual/block_factorys_bosses/entity/projectile/PoisonSpitPrEntity.java) |
| `KnightSwordItem` | 1 | `net.unusual.block_factorys_bosses.item` | [KnightSwordItem.java](net/unusual/block_factorys_bosses/item/KnightSwordItem.java) |
| `KnightItem` | 1 | `net.unusual.block_factorys_bosses.item` | [KnightItem.java](net/unusual/block_factorys_bosses/item/KnightItem.java) |
| `BossesRiseEntityRenderers` | 1 | `net.unusual.block_factorys_bosses.init` | [BossesRiseEntityRenderers.java](net/unusual/block_factorys_bosses/init/BossesRiseEntityRenderers.java) |
| `DaggerItem` | 1 | `net.unusual.block_factorys_bosses.item` | [DaggerItem.java](net/unusual/block_factorys_bosses/item/DaggerItem.java) |
| `WarriorSwordItem` | 1 | `net.unusual.block_factorys_bosses.item` | [WarriorSwordItem.java](net/unusual/block_factorys_bosses/item/WarriorSwordItem.java) |
| `LargeSwordItem` | 1 | `net.unusual.block_factorys_bosses.item` | [LargeSwordItem.java](net/unusual/block_factorys_bosses/item/LargeSwordItem.java) |
| `BossesRiseConfigs` | 1 | `net.unusual.block_factorys_bosses.init` | [BossesRiseConfigs.java](net/unusual/block_factorys_bosses/init/BossesRiseConfigs.java) |
| `ShutUpPacket` | 1 | `net.unusual.block_factorys_bosses.network` | [ShutUpPacket.java](net/unusual/block_factorys_bosses/network/ShutUpPacket.java) |
| `ParticleEventMessage` | 1 | `net.unusual.block_factorys_bosses.network` | [ParticleEventMessage.java](net/unusual/block_factorys_bosses/network/ParticleEventMessage.java) |
| `YetiEntity` | 1 | `net.unusual.block_factorys_bosses.entity.boss.yeti` | [YetiEntity.java](net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.java) |
| `DragonBonesItem` | 1 | `net.unusual.block_factorys_bosses.item` | [DragonBonesItem.java](net/unusual/block_factorys_bosses/item/DragonBonesItem.java) |
| `UnderworldArenaKeyItem` | 1 | `net.unusual.block_factorys_bosses.item` | [UnderworldArenaKeyItem.java](net/unusual/block_factorys_bosses/item/UnderworldArenaKeyItem.java) |
| `PosDebugCommand` | 1 | `net.unusual.block_factorys_bosses.command` | [PosDebugCommand.java](net/unusual/block_factorys_bosses/command/PosDebugCommand.java) |
| `KrakenShipStructure` | 1 | `net.unusual.block_factorys_bosses.structures` | [KrakenShipStructure.java](net/unusual/block_factorys_bosses/structures/KrakenShipStructure.java) |
| `KnightFirstPersonRenderer` | 1 | `net.unusual.block_factorys_bosses.client.renderer` | [KnightFirstPersonRenderer.java](net/unusual/block_factorys_bosses/client/renderer/KnightFirstPersonRenderer.java) |
| `DragonBonesFirstPersonRenderer` | 1 | `net.unusual.block_factorys_bosses.client.renderer` | [DragonBonesFirstPersonRenderer.java](net/unusual/block_factorys_bosses/client/renderer/DragonBonesFirstPersonRenderer.java) |
| `ShakeRenderer` | 1 | `net.unusual.block_factorys_bosses.client.camera.renderer` | [ShakeRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/ShakeRenderer.java) |
| `FovRenderer` | 1 | `net.unusual.block_factorys_bosses.client.camera.renderer` | [FovRenderer.java](net/unusual/block_factorys_bosses/client/camera/renderer/FovRenderer.java) |
| `BossesRiseBlockEntities` | 1 | `net.unusual.block_factorys_bosses.init` | [BossesRiseBlockEntities.java](net/unusual/block_factorys_bosses/init/BossesRiseBlockEntities.java) |
| `BossesRise` | 1 | `net.unusual.block_factorys_bosses` | [BossesRise.java](net/unusual/block_factorys_bosses/BossesRise.java) |
| `BossesRiseDataGen` | 1 | `net.unusual.block_factorys_bosses.data` | [BossesRiseDataGen.java](net/unusual/block_factorys_bosses/data/BossesRiseDataGen.java) |
| `BossesRiseParticles` | 1 | `net.unusual.block_factorys_bosses.init` | [BossesRiseParticles.java](net/unusual/block_factorys_bosses/init/BossesRiseParticles.java) |
| `PlayerPushMessage` | 1 | `net.unusual.block_factorys_bosses.network` | [PlayerPushMessage.java](net/unusual/block_factorys_bosses/network/PlayerPushMessage.java) |

## Mixin（26 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AnimatableManagerAccessor` | `AnimatableManagerAccessor` | [AnimatableManagerAccessor.java](net/unusual/block_factorys_bosses/mixins/accessor/AnimatableManagerAccessor.java) |
| `MouseHandlerMixin` | `MouseHandler` | [MouseHandlerMixin.java](net/unusual/block_factorys_bosses/mixins/client/MouseHandlerMixin.java) |
| `MusicManagerMixin` | `MusicManager` | [MusicManagerMixin.java](net/unusual/block_factorys_bosses/mixins/client/MusicManagerMixin.java) |
| `ServerBossEventMixin` | `ServerBossEvent` | [ServerBossEventMixin.java](net/unusual/block_factorys_bosses/mixins/server/ServerBossEventMixin.java) |
| `KrakenTridentTridentItemMixin` | `KrakenTridentTridentItem` | [KrakenTridentTridentItemMixin.java](net/unusual/block_factorys_bosses/mixins/KrakenTridentTridentItemMixin.java) |
| `LevelRendererMixin` | `LevelRenderer` | [LevelRendererMixin.java](net/unusual/block_factorys_bosses/mixins/client/LevelRendererMixin.java) |
| `GameRendererNightVisionMixin` | `GameRendererNightVision` | [GameRendererNightVisionMixin.java](net/unusual/block_factorys_bosses/mixins/client/GameRendererNightVisionMixin.java) |
| `CameraMixin` | `Camera` | [CameraMixin.java](net/unusual/block_factorys_bosses/mixins/client/CameraMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](net/unusual/block_factorys_bosses/mixins/client/GameRendererMixin.java) |
| `PlayerAnimationMixin` | `PlayerAnimation` | [PlayerAnimationMixin.java](net/unusual/block_factorys_bosses/mixins/client/PlayerAnimationMixin.java) |
| `PoseStackMixin` | `PoseStack` | [PoseStackMixin.java](net/unusual/block_factorys_bosses/mixins/client/PoseStackMixin.java) |
| `ControlMixin` | `Control` | [ControlMixin.java](net/unusual/block_factorys_bosses/mixins/ControlMixin.java) |
| `RollMixin` | `Roll` | [RollMixin.java](net/unusual/block_factorys_bosses/mixins/RollMixin.java) |
| `BasaltColumnsFeatureMixin` | `BasaltColumnsFeature` | [BasaltColumnsFeatureMixin.java](net/unusual/block_factorys_bosses/mixins/BasaltColumnsFeatureMixin.java) |
| `PlacementBypassMixin` | `PlacementBypass` | [PlacementBypassMixin.java](net/unusual/block_factorys_bosses/mixins/PlacementBypassMixin.java) |
| `AnimationControllerAccessor` | `AnimationControllerAccessor` | [AnimationControllerAccessor.java](net/unusual/block_factorys_bosses/mixins/accessor/AnimationControllerAccessor.java) |
| `AnimationProcessorAccessor` | `AnimationProcessorAccessor` | [AnimationProcessorAccessor.java](net/unusual/block_factorys_bosses/mixins/accessor/AnimationProcessorAccessor.java) |
| `EndDragonFightMixin` | `EndDragonFight` | [EndDragonFightMixin.java](net/unusual/block_factorys_bosses/mixins/server/EndDragonFightMixin.java) |
| `GeckolibMolangQueries` | `GeckolibMolangQueries` | [GeckolibMolangQueries.java](net/unusual/block_factorys_bosses/mixins/server/GeckolibMolangQueries.java) |
| `WardenMixin` | `Warden` | [WardenMixin.java](net/unusual/block_factorys_bosses/mixins/server/WardenMixin.java) |
| `WitherBossMixin` | `WitherBoss` | [WitherBossMixin.java](net/unusual/block_factorys_bosses/mixins/server/WitherBossMixin.java) |
| `LocalPlayerMixin` | `LocalPlayer` | [LocalPlayerMixin.java](net/unusual/block_factorys_bosses/mixins/client/LocalPlayerMixin.java) |
| `ElytraLayerMixin` | `ElytraLayer` | [ElytraLayerMixin.java](net/unusual/block_factorys_bosses/mixins/client/ElytraLayerMixin.java) |
| `ParticleEngineMixin` | `ParticleEngine` | [ParticleEngineMixin.java](net/unusual/block_factorys_bosses/mixins/client/ParticleEngineMixin.java) |
| `CinematicEntitiesMixin` | `CinematicEntities` | [CinematicEntitiesMixin.java](net/unusual/block_factorys_bosses/mixins/client/CinematicEntitiesMixin.java) |
| `CapeLayerMixin` | `CapeLayer` | [CapeLayerMixin.java](net/unusual/block_factorys_bosses/mixins/client/CapeLayerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BossesRiseConfigs` | EventBusSubscriber;MethodsReturnNonnullB | [BossesRiseConfigs.java](net/unusual/block_factorys_bosses/init/BossesRiseConfigs.java) |
| `ClientConfiguration` | MethodsReturnNonnullByDefault;Parameters | [ClientConfiguration.java](net/unusual/block_factorys_bosses/configuration/ClientConfiguration.java) |
| `ServerConfiguration` | — | [ServerConfiguration.java](net/unusual/block_factorys_bosses/configuration/ServerConfiguration.java) |

## 按包浏览

- `net.unusual.block_factorys_bosses`（1 类）
- `net.unusual.block_factorys_bosses.attachment.entity`（4 类）
- `net.unusual.block_factorys_bosses.block`（25 类）
- `net.unusual.block_factorys_bosses.block.abstracts`（3 类）
- `net.unusual.block_factorys_bosses.block.entity`（9 类）
- `net.unusual.block_factorys_bosses.client`（4 类）
- `net.unusual.block_factorys_bosses.client.animation`（4 类）
- `net.unusual.block_factorys_bosses.client.animation.sandworm`（8 类）
- `net.unusual.block_factorys_bosses.client.animation.yeti`（16 类）
- `net.unusual.block_factorys_bosses.client.camera`（2 类）
- `net.unusual.block_factorys_bosses.client.camera.client`（1 类）
- `net.unusual.block_factorys_bosses.client.camera.internal`（1 类）
- `net.unusual.block_factorys_bosses.client.camera.renderer`（6 类）
- `net.unusual.block_factorys_bosses.client.model`（31 类）
- `net.unusual.block_factorys_bosses.client.particle`（33 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock`（6 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.appearance`（7 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.curve`（6 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.event`（6 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime`（5 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.motion`（4 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.rate`（3 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.shape`（6 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.simple`（2 类）
- `net.unusual.block_factorys_bosses.client.particle.bedrock.vector`（5 类）
- `net.unusual.block_factorys_bosses.client.particle.knight`（27 类）
- `net.unusual.block_factorys_bosses.client.particle.util`（6 类）
- `net.unusual.block_factorys_bosses.client.particle.yeti`（13 类）
- `net.unusual.block_factorys_bosses.client.renderer`（19 类）
- `net.unusual.block_factorys_bosses.client.renderer.block`（3 类）
- `net.unusual.block_factorys_bosses.command`（1 类）
- `net.unusual.block_factorys_bosses.configuration`（2 类）
- `net.unusual.block_factorys_bosses.data`（2 类）
- `net.unusual.block_factorys_bosses.data.gen`（7 类）
- `net.unusual.block_factorys_bosses.data.gen.loot`（1 类）
- `net.unusual.block_factorys_bosses.data.gen.tag`（5 类）
- `net.unusual.block_factorys_bosses.data.util`（3 类）
- `net.unusual.block_factorys_bosses.entity`（8 类）
- `net.unusual.block_factorys_bosses.entity.boss`（2 类）
- `net.unusual.block_factorys_bosses.entity.boss.dragon.boss`（1 类）
- `net.unusual.block_factorys_bosses.entity.boss.dragon.guardians`（3 类）
- `net.unusual.block_factorys_bosses.entity.boss.knight`（2 类）
- `net.unusual.block_factorys_bosses.entity.boss.kraken`（4 类）
- `net.unusual.block_factorys_bosses.entity.boss.kraken.goals`（3 类）
- `net.unusual.block_factorys_bosses.entity.boss.kraken.summons`（4 类）
- `net.unusual.block_factorys_bosses.entity.boss.part`（3 类）
- `net.unusual.block_factorys_bosses.entity.boss.sandworm`（2 类）
- `net.unusual.block_factorys_bosses.entity.boss.yeti`（6 类）
- `net.unusual.block_factorys_bosses.entity.boss.yeti.goals`（2 类）
- `net.unusual.block_factorys_bosses.entity.control`（3 类）
- `net.unusual.block_factorys_bosses.entity.decoration`（5 类）
- `net.unusual.block_factorys_bosses.entity.monster`（2 类）
- `net.unusual.block_factorys_bosses.entity.projectile`（9 类）
- `net.unusual.block_factorys_bosses.event`（3 类）
- `net.unusual.block_factorys_bosses.geckolib`（16 类）
- `net.unusual.block_factorys_bosses.geckolib.boneCache`（3 类）
- `net.unusual.block_factorys_bosses.geckolib.boss`（1 类）
- `net.unusual.block_factorys_bosses.geckolib.boss.dragon.boss`（1 类）
- `net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians`（3 类）
- `net.unusual.block_factorys_bosses.geckolib.boss.knight`（1 类）
- `net.unusual.block_factorys_bosses.geckolib.boss.kraken`（6 类）
- `net.unusual.block_factorys_bosses.geckolib.boss.worm`（4 类）
- `net.unusual.block_factorys_bosses.geckolib.boss.yeti`（1 类）
- `net.unusual.block_factorys_bosses.geckolib.item`（3 类）
- `net.unusual.block_factorys_bosses.geckolib.util`（10 类）
- `net.unusual.block_factorys_bosses.init`（22 类）
- `net.unusual.block_factorys_bosses.item`（27 类）
- `net.unusual.block_factorys_bosses.item.component`（1 类）
- `net.unusual.block_factorys_bosses.mixins`（5 类）
- `net.unusual.block_factorys_bosses.mixins.accessor`（3 类）
- `net.unusual.block_factorys_bosses.mixins.client`（13 类）
- `net.unusual.block_factorys_bosses.mixins.server`（5 类）
- `net.unusual.block_factorys_bosses.network`（9 类）
- `net.unusual.block_factorys_bosses.procedures`（33 类）
- `net.unusual.block_factorys_bosses.random`（1 类）
- `net.unusual.block_factorys_bosses.state`（12 类）
- `net.unusual.block_factorys_bosses.structures`（2 类）
- `net.unusual.block_factorys_bosses.util`（8 类）
