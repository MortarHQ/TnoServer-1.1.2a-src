# Friends&Foes（`friendsandfoes`）

> jar：`[遗物的生物]friendsandfoes.jar` ｜ 版本：`4.0.23` ｜ 作者：Faboslav
> 规模：**288 个类 / 68 个包 / 21292 行** ｜ 事件处理类 0 ｜ Mixin 50 ｜ 注册/管理类 6

**官方描述**：Adds outvoted and forgotten mobs from the mob vote, expanding on their original concepts and introducing new vanilla-like features.

**依赖**：`neoforge`、`minecraft`、`yet_another_config_lib_v3`、`resourcefullib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.faboslav.friendsandfoes.common.mixin` | 47 | `LightningRodBlockMixin`、`BlazeEntityMixin`、`CarvedPumpkinBlockMixin` |
| `com.faboslav.friendsandfoes.common.init` | 24 | `FriendsAndFoesSoundEvents`、`FriendsAndFoesBlockEntityTypes`、`FriendsAndFoesEntityRenderers` |
| `com.faboslav.friendsandfoes.common.entity` | 16 | `TuffGolemEntity`、`CopperGolemEntity`、`MaulerEntity` |
| `com.faboslav.friendsandfoes.common.client.render.entity.renderer` | 12 | `IllusionerEntityRenderer`、`GlareEntityRenderer`、`RascalEntityRenderer` |
| `com.faboslav.friendsandfoes.common.util` | 11 | `BasicItemTrade`、`TotemUtil`、`WaxableBlocksMap` |
| `com.faboslav.friendsandfoes.common.entity.animation` | 10 | `AnimatedEntity`、`AnimationState`、`AnimationDefinition` |
| `com.faboslav.friendsandfoes.common.client.render.entity.model` | 9 | `CrabEntityModel`、`GlareEntityModel`、`CopperGolemEntityModel` |
| `com.faboslav.friendsandfoes.common.events.lifecycle` | 8 | `RegisterEntityAttributesEvent`、`ClientSetupEvent`、`RegisterEntitySpawnRestrictionsEvent` |
| `com.faboslav.friendsandfoes.common.versions` | 8 | `VersionedEntity`、`VersionedEntitySpawnReason`、`VersionedEntityTypeResourceId` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare` | 8 | `GlareShakeGlowBerriesTask`、`GlareTravelToGlowBerriesTask`、`GlareLocateDarkSpotTask` |
| `com.faboslav.friendsandfoes.common.platform` | 7 | `EntitySerializers`、`PlatformCompat`、`PlatformHelper` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab` | 7 | `CrabWaveTask`、`CrabGoToHomePositionTask`、`CrabLayEggTask` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain` | 6 | `GlareBrain`、`WildfireBrain`、`CrabBrain` |
| `com.faboslav.friendsandfoes.common.block` | 6 | `CrabEggBlock`、`OxidizableLightningRodBlock`、`OxidizableButtonBlock` |
| `com.faboslav.friendsandfoes.common.client.render.entity.feature` | 6 | `TuffGolemHeldItemFeatureRenderer`、`GlareFlowerFeatureRenderer`、`TuffGolemClosedEyesRenderer` |
| `com.faboslav.friendsandfoes.neoforge.platform` | 5 | `EntitySerializers`、`PlatformCompat`、`PlatformHelper` |
| `com.faboslav.friendsandfoes.common.entity.ai.goal.mauler` | 5 | `MaulerLookAtEntityGoal`、`MaulerBurrowDownGoal`、`MaulerMeeleAttackGoal` |
| `com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json` | 4 | `AnimationTypeManager`、`AnimationLoader`、`AnimationParser` |
| `com.faboslav.friendsandfoes.common.events.client` | 4 | `RegisterEntityRenderersEvent`、`RegisterEntityLayersEvent`、`RegisterParticlesEvent` |
| `com.faboslav.friendsandfoes.common.world.processor` | 4 | `StructureEntityProcessor`、`IllusionerShackBrewingStandProcessor`、`CitadelBottomProcessor` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem` | 4 | `TuffGolemGoToHomePositionTask`、`TuffGolemLookAroundTask`、`TuffGolemWanderAroundTask` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem` | 4 | `CopperGolemSpinHeadTask`、`CopperGolemTravelToButtonTask`、`CopperGolemPressButtonTask` |
| `com.faboslav.friendsandfoes.common.world.structures` | 4 | `IllusionerTrainingGroundsStructure`、`IceologerCabinStructure`、`CitadelStructure` |
| `com.faboslav.friendsandfoes.common.network.packet` | 3 | `EntityAnimationsSyncPacket`、`MoobloomVariantsSyncPacket`、`TotemEffectPacket` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire` | 3 | `WildfireBarrageAttackTask`、`WildfireShockwaveAttackTask`、`WildfireSummonBlazeTask` |
| `com.faboslav.friendsandfoes.common.client.render.entity.animation.animator` | 3 | `ModelPartAnimator`、`ModelPartAnimationType`、`KeyframeAnimator` |
| `com.faboslav.friendsandfoes.neoforge` | 2 | `FriendsAndFoesNeoForge`、`FriendsAndFoesNeoForgeClient` |
| `com.faboslav.friendsandfoes.common.events.base` | 2 | `CancellableEventHandler`、`EventHandler` |
| `com.faboslav.friendsandfoes.common.mixin.datafix` | 2 | `DataFixersMixin`、`V1460Mixin` |
| `com.faboslav.friendsandfoes.common.api` | 2 | `MoobloomVariantManager`、`MoobloomVariant` |
| `com.faboslav.friendsandfoes.common.events.entity` | 2 | `EntitySpawnEvent`、`RegisterVillagerTradesEvent` |
| `com.faboslav.friendsandfoes.common.util.world.processor` | 2 | `IllusionerShackItemFrameProcessorHelper`、`IceologerCabinArmorStandProcessorHelper` |
| `com.faboslav.friendsandfoes.common.client.render.entity.model.animation` | 2 | `ModelPartModelAnimator`、`KeyframeModelAnimator` |
| `com.faboslav.friendsandfoes.neoforge.world.processor` | 2 | `IllusionerShackItemFrameProcessor`、`IceologerCabinArmorStandProcessor` |
| `com.faboslav.friendsandfoes.common` | 2 | `FriendsAndFoesClient`、`FriendsAndFoes` |
| `com.faboslav.friendsandfoes.common.entity.event` | 2 | `OnEntitySpawn`、`IllusionerOnEntitySpawn` |
| `com.faboslav.friendsandfoes.common.modcompat` | 2 | `ModCompat`、`ModChecker` |
| `com.faboslav.friendsandfoes.neoforge.datagen` | 2 | `StructureNbtUpdater`、`StructureNbtUpdaterDatagen` |
| `com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal` | 2 | `RascalWaitForPlayerTask`、`RascalFindInteractionTargetTask` |
| `com.faboslav.friendsandfoes.common.entity.animation.animator.context` | 2 | `KeyframeAnimationContext`、`AnimationContextTracker` |
| … | | 其余 28 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FriendsAndFoesNeoForge` | class | — | Mod | 13 | 167 | [FriendsAndFoesNeoForge.java](com/faboslav/friendsandfoes/neoforge/FriendsAndFoesNeoForge.java) |
| `TuffGolemEntity` | class | AbstractGolem | — | 86 | 855 | [TuffGolemEntity.java](com/faboslav/friendsandfoes/common/entity/TuffGolemEntity.java) |
| `CopperGolemEntity` | class | AbstractGolem | — | 73 | 842 | [CopperGolemEntity.java](com/faboslav/friendsandfoes/common/entity/CopperGolemEntity.java) |
| `MaulerEntity` | class | Animal | — | 62 | 619 | [MaulerEntity.java](com/faboslav/friendsandfoes/common/entity/MaulerEntity.java) |
| `GlareEntity` | class | TamableAnimal | — | 75 | 610 | [GlareEntity.java](com/faboslav/friendsandfoes/common/entity/GlareEntity.java) |
| `IllusionerEntity` | class | SpellcasterIllager | — | 40 | 405 | [IllusionerEntity.java](com/faboslav/friendsandfoes/common/entity/IllusionerEntity.java) |
| `WildfireEntity` | class | Monster | — | 53 | 454 | [WildfireEntity.java](com/faboslav/friendsandfoes/common/entity/WildfireEntity.java) |
| `CrabEntity` | class | Animal | — | 68 | 585 | [CrabEntity.java](com/faboslav/friendsandfoes/common/entity/CrabEntity.java) |
| `RascalEntity` | class | AgeableMob | — | 51 | 410 | [RascalEntity.java](com/faboslav/friendsandfoes/common/entity/RascalEntity.java) |
| `IceologerIceChunkEntity` | class | Entity | — | 32 | 289 | [IceologerIceChunkEntity.java](com/faboslav/friendsandfoes/common/entity/IceologerIceChunkEntity.java) |
| `LightningRodBlockMixin` | class | LightningRodBlockBlockMixin | Mixin | 7 | 191 | [LightningRodBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockMixin.java) |
| `BlazeEntityMixin` | class | BlazeLivingEntityMixin | Mixin | 7 | 74 | [BlazeEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/BlazeEntityMixin.java) |
| `CarvedPumpkinBlockMixin` | class | HorizontalDirectionalBlock | Mixin | 6 | 133 | [CarvedPumpkinBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/CarvedPumpkinBlockMixin.java) |
| `ZombieHorseEntityMixin` | class | ZombieHorseAbstractHorseEntityMixin | Mixin | 5 | 64 | [ZombieHorseEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ZombieHorseEntityMixin.java) |
| `CancellableEventHandler` | class | — | — | 7 | 65 | [CancellableEventHandler.java](com/faboslav/friendsandfoes/common/events/base/CancellableEventHandler.java) |
| `FireBlockAccessor` | interface | — | Mixin | 0 | 13 | [FireBlockAccessor.java](com/faboslav/friendsandfoes/neoforge/mixin/FireBlockAccessor.java) |
| `LightningRodBlockDegradableMixin` | class | ChangeOverTimeBlock | Mixin | 1 | 14 | [LightningRodBlockDegradableMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockDegradableMixin.java) |
| `BeeEntityAccessor` | interface | — | Mixin | 0 | 19 | [BeeEntityAccessor.java](com/faboslav/friendsandfoes/common/mixin/BeeEntityAccessor.java) |
| `SpawnEggItemAccessor` | interface | — | Mixin | 0 | 17 | [SpawnEggItemAccessor.java](com/faboslav/friendsandfoes/common/mixin/SpawnEggItemAccessor.java) |
| `ZombieHorseAnimalEntityMixin` | class | AgeableMob | Mixin | 2 | 28 | [ZombieHorseAnimalEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ZombieHorseAnimalEntityMixin.java) |
| `RabbitEntityMixin` | class | Animal | Mixin | 1 | 34 | [RabbitEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/RabbitEntityMixin.java) |
| `ZombieHorseAbstractHorseEntityMixin` | class | ZombieHorseAnimalEntityMixin | Mixin | 1 | 21 | [ZombieHorseAbstractHorseEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ZombieHorseAbstractHorseEntityMixin.java) |
| `BeeEntityMixin` | class | Animal | Mixin | 2 | 47 | [BeeEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/BeeEntityMixin.java) |
| `MannequinEntityMixin` | class | — | Mixin | 0 | 9 | [MannequinEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinEntityMixin.java) |
| `LightningRodBlockOxidizableMixin` | class | RodBlock | Mixin | 1 | 22 | [LightningRodBlockOxidizableMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockOxidizableMixin.java) |
| `PlayerEntityMixin` | class | LivingEntity | Mixin | 2 | 130 | [PlayerEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/PlayerEntityMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 2 | 41 | [LivingEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/LivingEntityMixin.java) |
| `EntityRenderDispatcherMixin` | class | — | Mixin | 0 | 45 | [EntityRenderDispatcherMixin.java](com/faboslav/friendsandfoes/common/mixin/EntityRenderDispatcherMixin.java) |
| `MannequinLivingEntityMixin` | class | — | Mixin | 0 | 9 | [MannequinLivingEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinLivingEntityMixin.java) |
| `LightningRodBlockBlockMixin` | class | LightningRodAbstractBlockMixin | Mixin | 0 | 9 | [LightningRodBlockBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockBlockMixin.java) |
| `LightningRodAbstractBlockMixin` | class | — | Mixin | 3 | 34 | [LightningRodAbstractBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodAbstractBlockMixin.java) |
| `LightningEntityMixin` | class | Entity | Mixin | 1 | 98 | [LightningEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningEntityMixin.java) |
| `ChickenEntityMixin` | class | Animal | Mixin | 1 | 34 | [ChickenEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ChickenEntityMixin.java) |
| `BlazeLivingEntityMixin` | class | Entity | Mixin | 3 | 32 | [BlazeLivingEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/BlazeLivingEntityMixin.java) |
| `SnowballEntityMixin` | class | ThrowableItemProjectile | Mixin | 0 | 29 | [SnowballEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/SnowballEntityMixin.java) |
| `EventHandler` | class | — | — | 3 | 24 | [EventHandler.java](com/faboslav/friendsandfoes/common/events/base/EventHandler.java) |
| `FriendsAndFoesSoundEvents` | class | — | — | 2 | 96 | [FriendsAndFoesSoundEvents.java](com/faboslav/friendsandfoes/common/init/FriendsAndFoesSoundEvents.java) |
| `AnimationTypeManager` | class | — | — | 10 | 99 | [AnimationTypeManager.java](com/faboslav/friendsandfoes/common/entity/animation/animator/loader/json/AnimationTypeManager.java) |
| `DataFixersMixin` | class | — | Mixin | 0 | 9 | [DataFixersMixin.java](com/faboslav/friendsandfoes/common/mixin/datafix/DataFixersMixin.java) |
| `RegisterBrewingRecipesEvent` | record | — | — | 0 | 12 | [RegisterBrewingRecipesEvent.java](com/faboslav/friendsandfoes/common/events/item/RegisterBrewingRecipesEvent.java) |
| `RegisterEntityRenderersEvent` | record | — | — | 1 | 20 | [RegisterEntityRenderersEvent.java](com/faboslav/friendsandfoes/common/events/client/RegisterEntityRenderersEvent.java) |
| `EntityAnimationsSyncPacket` | record | Packet<EntityAnimationsSyncPacket> | — | 6 | 103 | [EntityAnimationsSyncPacket.java](com/faboslav/friendsandfoes/common/network/packet/EntityAnimationsSyncPacket.java) |
| `IceologerEntity` | class | SpellcasterIllager | — | 22 | 166 | [IceologerEntity.java](com/faboslav/friendsandfoes/common/entity/IceologerEntity.java) |
| `MoobloomEntity` | class | Cow | — | 16 | 256 | [MoobloomEntity.java](com/faboslav/friendsandfoes/common/entity/MoobloomEntity.java) |
| `BeeHiveGoalMixin` | class | — | Mixin | 0 | 26 | [BeeHiveGoalMixin.java](com/faboslav/friendsandfoes/common/mixin/BeeHiveGoalMixin.java) |
| `MannequinAvatarMixin` | class | — | Mixin | 0 | 9 | [MannequinAvatarMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinAvatarMixin.java) |
| `StrongholdGeneratorMixin` | class | StructurePiece | Mixin | 0 | 115 | [StrongholdGeneratorMixin.java](com/faboslav/friendsandfoes/common/mixin/StrongholdGeneratorMixin.java) |
| `RaidMemberMixin` | class | — | Mixin | 1 | 28 | [RaidMemberMixin.java](com/faboslav/friendsandfoes/common/mixin/RaidMemberMixin.java) |
| `RaidMixin` | class | — | Mixin | 1 | 23 | [RaidMixin.java](com/faboslav/friendsandfoes/common/mixin/RaidMixin.java) |
| `ServerWorldAccessor` | interface | — | Mixin | 0 | 21 | [ServerWorldAccessor.java](com/faboslav/friendsandfoes/common/mixin/ServerWorldAccessor.java) |
| `AnimalMakeLoveAccessor` | interface | — | Mixin | 0 | 17 | [AnimalMakeLoveAccessor.java](com/faboslav/friendsandfoes/common/mixin/AnimalMakeLoveAccessor.java) |
| `MannequinMixin` | class | — | Mixin | 0 | 9 | [MannequinMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinMixin.java) |
| `VillagerHostilesSensorMixin` | class | — | Mixin | 0 | 37 | [VillagerHostilesSensorMixin.java](com/faboslav/friendsandfoes/common/mixin/VillagerHostilesSensorMixin.java) |
| `PointOfInterestTypesMixin` | class | — | Mixin | 1 | 27 | [PointOfInterestTypesMixin.java](com/faboslav/friendsandfoes/common/mixin/PointOfInterestTypesMixin.java) |
| `MobAccessor` | interface | — | Mixin | 0 | 13 | [MobAccessor.java](com/faboslav/friendsandfoes/common/mixin/MobAccessor.java) |
| `AddCustomSpawnGroupMixin` | class | — | Mixin | 2 | 52 | [AddCustomSpawnGroupMixin.java](com/faboslav/friendsandfoes/common/mixin/AddCustomSpawnGroupMixin.java) |
| `StructurePoolAccessor` | interface | — | Mixin | 0 | 28 | [StructurePoolAccessor.java](com/faboslav/friendsandfoes/common/mixin/StructurePoolAccessor.java) |
| `AddCustomRaidMemberMixin` | class | — | Mixin | 2 | 70 | [AddCustomRaidMemberMixin.java](com/faboslav/friendsandfoes/common/mixin/AddCustomRaidMemberMixin.java) |
| `LimbAnimatorAccessor` | interface | — | Mixin | 0 | 18 | [LimbAnimatorAccessor.java](com/faboslav/friendsandfoes/common/mixin/LimbAnimatorAccessor.java) |
| `BeeGoToKnownFlowerGoalMixin` | class | — | Mixin | 0 | 26 | [BeeGoToKnownFlowerGoalMixin.java](com/faboslav/friendsandfoes/common/mixin/BeeGoToKnownFlowerGoalMixin.java) |

## Mixin（50 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LightningRodBlockMixin` | `LightningRodBlock` | [LightningRodBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockMixin.java) |
| `BlazeEntityMixin` | `BlazeEntity` | [BlazeEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/BlazeEntityMixin.java) |
| `CarvedPumpkinBlockMixin` | `CarvedPumpkinBlock` | [CarvedPumpkinBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/CarvedPumpkinBlockMixin.java) |
| `ZombieHorseEntityMixin` | `ZombieHorseEntity` | [ZombieHorseEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ZombieHorseEntityMixin.java) |
| `FireBlockAccessor` | `FireBlockAccessor` | [FireBlockAccessor.java](com/faboslav/friendsandfoes/neoforge/mixin/FireBlockAccessor.java) |
| `LightningRodBlockDegradableMixin` | `LightningRodBlockDegradable` | [LightningRodBlockDegradableMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockDegradableMixin.java) |
| `BeeEntityAccessor` | `BeeEntityAccessor` | [BeeEntityAccessor.java](com/faboslav/friendsandfoes/common/mixin/BeeEntityAccessor.java) |
| `SpawnEggItemAccessor` | `SpawnEggItemAccessor` | [SpawnEggItemAccessor.java](com/faboslav/friendsandfoes/common/mixin/SpawnEggItemAccessor.java) |
| `ZombieHorseAnimalEntityMixin` | `ZombieHorseAnimalEntity` | [ZombieHorseAnimalEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ZombieHorseAnimalEntityMixin.java) |
| `RabbitEntityMixin` | `RabbitEntity` | [RabbitEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/RabbitEntityMixin.java) |
| `ZombieHorseAbstractHorseEntityMixin` | `ZombieHorseAbstractHorseEntity` | [ZombieHorseAbstractHorseEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ZombieHorseAbstractHorseEntityMixin.java) |
| `BeeEntityMixin` | `BeeEntity` | [BeeEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/BeeEntityMixin.java) |
| `MannequinEntityMixin` | `MannequinEntity` | [MannequinEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinEntityMixin.java) |
| `LightningRodBlockOxidizableMixin` | `LightningRodBlockOxidizable` | [LightningRodBlockOxidizableMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockOxidizableMixin.java) |
| `PlayerEntityMixin` | `PlayerEntity` | [PlayerEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/PlayerEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/LivingEntityMixin.java) |
| `EntityRenderDispatcherMixin` | `EntityRenderDispatcher` | [EntityRenderDispatcherMixin.java](com/faboslav/friendsandfoes/common/mixin/EntityRenderDispatcherMixin.java) |
| `MannequinLivingEntityMixin` | `MannequinLivingEntity` | [MannequinLivingEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinLivingEntityMixin.java) |
| `LightningRodBlockBlockMixin` | `LightningRodBlockBlock` | [LightningRodBlockBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodBlockBlockMixin.java) |
| `LightningRodAbstractBlockMixin` | `LightningRodAbstractBlock` | [LightningRodAbstractBlockMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningRodAbstractBlockMixin.java) |
| `LightningEntityMixin` | `LightningEntity` | [LightningEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/LightningEntityMixin.java) |
| `ChickenEntityMixin` | `ChickenEntity` | [ChickenEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/ChickenEntityMixin.java) |
| `BlazeLivingEntityMixin` | `BlazeLivingEntity` | [BlazeLivingEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/BlazeLivingEntityMixin.java) |
| `SnowballEntityMixin` | `SnowballEntity` | [SnowballEntityMixin.java](com/faboslav/friendsandfoes/common/mixin/SnowballEntityMixin.java) |
| `DataFixersMixin` | `DataFixers` | [DataFixersMixin.java](com/faboslav/friendsandfoes/common/mixin/datafix/DataFixersMixin.java) |
| `BeeHiveGoalMixin` | `BeeHiveGoal` | [BeeHiveGoalMixin.java](com/faboslav/friendsandfoes/common/mixin/BeeHiveGoalMixin.java) |
| `MannequinAvatarMixin` | `MannequinAvatar` | [MannequinAvatarMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinAvatarMixin.java) |
| `StrongholdGeneratorMixin` | `StrongholdGenerator` | [StrongholdGeneratorMixin.java](com/faboslav/friendsandfoes/common/mixin/StrongholdGeneratorMixin.java) |
| `RaidMemberMixin` | `RaidMember` | [RaidMemberMixin.java](com/faboslav/friendsandfoes/common/mixin/RaidMemberMixin.java) |
| `RaidMixin` | `Raid` | [RaidMixin.java](com/faboslav/friendsandfoes/common/mixin/RaidMixin.java) |
| `ServerWorldAccessor` | `ServerWorldAccessor` | [ServerWorldAccessor.java](com/faboslav/friendsandfoes/common/mixin/ServerWorldAccessor.java) |
| `AnimalMakeLoveAccessor` | `AnimalMakeLoveAccessor` | [AnimalMakeLoveAccessor.java](com/faboslav/friendsandfoes/common/mixin/AnimalMakeLoveAccessor.java) |
| `MannequinMixin` | `Mannequin` | [MannequinMixin.java](com/faboslav/friendsandfoes/common/mixin/MannequinMixin.java) |
| `VillagerHostilesSensorMixin` | `VillagerHostilesSensor` | [VillagerHostilesSensorMixin.java](com/faboslav/friendsandfoes/common/mixin/VillagerHostilesSensorMixin.java) |
| `PointOfInterestTypesMixin` | `PointOfInterestTypes` | [PointOfInterestTypesMixin.java](com/faboslav/friendsandfoes/common/mixin/PointOfInterestTypesMixin.java) |
| `MobAccessor` | `MobAccessor` | [MobAccessor.java](com/faboslav/friendsandfoes/common/mixin/MobAccessor.java) |
| `AddCustomSpawnGroupMixin` | `AddCustomSpawnGroup` | [AddCustomSpawnGroupMixin.java](com/faboslav/friendsandfoes/common/mixin/AddCustomSpawnGroupMixin.java) |
| `StructurePoolAccessor` | `StructurePoolAccessor` | [StructurePoolAccessor.java](com/faboslav/friendsandfoes/common/mixin/StructurePoolAccessor.java) |
| `AddCustomRaidMemberMixin` | `AddCustomRaidMember` | [AddCustomRaidMemberMixin.java](com/faboslav/friendsandfoes/common/mixin/AddCustomRaidMemberMixin.java) |
| `LimbAnimatorAccessor` | `LimbAnimatorAccessor` | [LimbAnimatorAccessor.java](com/faboslav/friendsandfoes/common/mixin/LimbAnimatorAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FriendsAndFoesConfig` | — | [FriendsAndFoesConfig.java](com/faboslav/friendsandfoes/common/config/FriendsAndFoesConfig.java) |

## 按包浏览

- `com.faboslav.friendsandfoes.common`（2 类）
- `com.faboslav.friendsandfoes.common.advancements.criterion`（1 类）
- `com.faboslav.friendsandfoes.common.api`（2 类）
- `com.faboslav.friendsandfoes.common.block`（6 类）
- `com.faboslav.friendsandfoes.common.client.particle`（2 类）
- `com.faboslav.friendsandfoes.common.client.render.entity.animation.animator`（3 类）
- `com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context`（1 类）
- `com.faboslav.friendsandfoes.common.client.render.entity.feature`（6 类）
- `com.faboslav.friendsandfoes.common.client.render.entity.model`（9 类）
- `com.faboslav.friendsandfoes.common.client.render.entity.model.animation`（2 类）
- `com.faboslav.friendsandfoes.common.client.render.entity.renderer`（12 类）
- `com.faboslav.friendsandfoes.common.config`（1 类）
- `com.faboslav.friendsandfoes.common.entity`（16 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain`（6 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.sensor`（2 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.beekeeper`（1 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem`（4 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab`（7 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare`（8 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal`（2 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem`（4 类）
- `com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire`（3 类）
- `com.faboslav.friendsandfoes.common.entity.ai.control`（1 类）
- `com.faboslav.friendsandfoes.common.entity.ai.goal.bee`（1 类）
- `com.faboslav.friendsandfoes.common.entity.ai.goal.mauler`（5 类）
- `com.faboslav.friendsandfoes.common.entity.ai.goal.zombiehorse`（1 类）
- `com.faboslav.friendsandfoes.common.entity.ai.pathing`（1 类）
- `com.faboslav.friendsandfoes.common.entity.animation`（10 类）
- `com.faboslav.friendsandfoes.common.entity.animation.animator`（1 类）
- `com.faboslav.friendsandfoes.common.entity.animation.animator.context`（2 类）
- `com.faboslav.friendsandfoes.common.entity.animation.animator.loader`（2 类）
- `com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json`（4 类）
- `com.faboslav.friendsandfoes.common.entity.effect`（1 类）
- `com.faboslav.friendsandfoes.common.entity.event`（2 类）
- `com.faboslav.friendsandfoes.common.entity.pose`（1 类）
- `com.faboslav.friendsandfoes.common.events`（1 类）
- `com.faboslav.friendsandfoes.common.events.base`（2 类）
- `com.faboslav.friendsandfoes.common.events.client`（4 类）
- `com.faboslav.friendsandfoes.common.events.entity`（2 类）
- `com.faboslav.friendsandfoes.common.events.item`（1 类）
- `com.faboslav.friendsandfoes.common.events.lifecycle`（8 类）
- `com.faboslav.friendsandfoes.common.init`（24 类）
- `com.faboslav.friendsandfoes.common.item`（1 类）
- `com.faboslav.friendsandfoes.common.mixin`（47 类）
- `com.faboslav.friendsandfoes.common.mixin.datafix`（2 类）
- `com.faboslav.friendsandfoes.common.mixin.plugin`（1 类）
- `com.faboslav.friendsandfoes.common.modcompat`（2 类）
- `com.faboslav.friendsandfoes.common.network`（1 类）
- `com.faboslav.friendsandfoes.common.network.packet`（3 类）
- `com.faboslav.friendsandfoes.common.platform`（7 类）
- `com.faboslav.friendsandfoes.common.tag`（1 类）
- `com.faboslav.friendsandfoes.common.util`（11 类）
- `com.faboslav.friendsandfoes.common.util.animation`（1 类）
- `com.faboslav.friendsandfoes.common.util.particle`（1 类）
- `com.faboslav.friendsandfoes.common.util.world.processor`（2 类）
- `com.faboslav.friendsandfoes.common.versions`（8 类）
- `com.faboslav.friendsandfoes.common.world.processor`（4 类）
- `com.faboslav.friendsandfoes.common.world.spawner`（2 类）
- `com.faboslav.friendsandfoes.common.world.structures`（4 类）
- `com.faboslav.friendsandfoes.neoforge`（2 类）
- `com.faboslav.friendsandfoes.neoforge.datagen`（2 类）
- `com.faboslav.friendsandfoes.neoforge.init`（1 类）
- `com.faboslav.friendsandfoes.neoforge.mixin`（1 类）
- `com.faboslav.friendsandfoes.neoforge.modcompat`（1 类）
- `com.faboslav.friendsandfoes.neoforge.modcompat.curios`（1 类）
- `com.faboslav.friendsandfoes.neoforge.platform`（5 类）
- `com.faboslav.friendsandfoes.neoforge.world.processor`（2 类）
- `com.faboslav.friendsandfoes.neoforge.worldgen`（1 类）
