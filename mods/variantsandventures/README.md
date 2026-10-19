# Variants&Ventures（`variantsandventures`）

> jar：`[怪物变种]variantsandventures.jar` ｜ 版本：`1.0.23` ｜ 作者：Faboslav
> 规模：**84 个类 / 25 个包 / 3569 行** ｜ 事件处理类 0 ｜ Mixin 19 ｜ 注册/管理类 4

**官方描述**：Adds multiple new mob variants seamlessly integrated into your Minecraft world.

**依赖**：`neoforge`、`minecraft`、`yet_another_config_lib_v3`、`resourcefullib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.faboslav.variantsandventures.common.mixin` | 19 | `ZombieLivingEntityRendererMixin`、`SpawnEggItemAccessor`、`ZombieLivingEntityMixin` |
| `com.faboslav.variantsandventures.common.init` | 8 | `VariantsAndVenturesSoundEvents`、`VariantsAndVenturesEntityRenderers`、`VariantsAndVenturesItemGroups` |
| `com.faboslav.variantsandventures.common.entity.event` | 8 | `GelidOnSnowballHitEvent`、`StrayOnEntitySpawn`、`OnEntitySpawn` |
| `com.faboslav.variantsandventures.common.versions` | 7 | `VersionedEntitySpawnReason`、`VersionedEntityTypeResourceId`、`VersionedBlockPathType` |
| `com.faboslav.variantsandventures.common.entity.mob` | 4 | `MurkEntity`、`VerdantEntity`、`ThicketEntity` |
| `com.faboslav.variantsandventures.common.events.lifecycle` | 4 | `RegisterEntityAttributesEvent`、`RegisterEntitySpawnRestrictionsEvent`、`AddSpawnBiomeModificationsEvent` |
| `com.faboslav.variantsandventures.common.client.render.entity` | 4 | `GelidEntityRenderer`、`ThicketEntityRenderer`、`MurkEntityRenderer` |
| `com.faboslav.variantsandventures.common.client.model` | 4 | `MurkEntityModel`、`ThicketEntityModel`、`VerdantEntityModel` |
| `com.faboslav.variantsandventures.common.entity.ai.goal` | 3 | `LeaveWaterGoal`、`TargetAboveWaterGoal`、`WanderAroundOnSurfaceGoal` |
| `com.faboslav.variantsandventures.neoforge` | 2 | `VariantsAndVenturesNeoForge`、`VariantsAndVenturesClientNeoForge` |
| `com.faboslav.variantsandventures.common.events.base` | 2 | `CancellableEventHandler`、`EventHandler` |
| `com.faboslav.variantsandventures.common.events.client` | 2 | `RegisterEntityRenderersEvent`、`RegisterEntityLayersEvent` |
| `com.faboslav.variantsandventures.common.events.entity` | 2 | `EntitySpawnEvent`、`ProjectileHitEvent` |
| `com.faboslav.variantsandventures.common` | 2 | `VariantsAndVenturesClient`、`VariantsAndVentures` |
| `com.faboslav.variantsandventures.common.client.render.entity.feature` | 2 | `GelidOverlayFeatureRenderer`、`ThicketOverlayFeatureRenderer` |
| `com.faboslav.variantsandventures.common.util` | 2 | `AdvancementHelper`、`LayerDefinitionFactory` |
| `com.faboslav.variantsandventures.common.network` | 1 | `MessageHandler` |
| `com.faboslav.variantsandventures.common.events` | 1 | `AddItemGroupEntriesEvent` |
| `com.faboslav.variantsandventures.common.events.item` | 1 | `RegisterItemColorEvent` |
| `com.faboslav.variantsandventures.common.network.packet` | 1 | `SyncZombieIsShakingPacketFromServer` |
| `com.faboslav.variantsandventures.common.config` | 1 | `VariantsAndVenturesConfig` |
| `com.faboslav.variantsandventures.common.entity.ai` | 1 | `GelidSnowballRangedAttackGoal` |
| `com.faboslav.variantsandventures.common.mixin.plugin` | 1 | `VariantsAndVenturesMixinPlugin` |
| `com.faboslav.variantsandventures.common.api` | 1 | `ZombieApi` |
| `com.faboslav.variantsandventures.common.tag` | 1 | `VariantsAndVenturesTags` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `VariantsAndVenturesNeoForge` | class | — | Mod | 8 | 94 | [VariantsAndVenturesNeoForge.java](com/faboslav/variantsandventures/neoforge/VariantsAndVenturesNeoForge.java) |
| `MurkEntity` | class | Skeleton | — | 44 | 434 | [MurkEntity.java](com/faboslav/variantsandventures/common/entity/mob/MurkEntity.java) |
| `ZombieLivingEntityRendererMixin` | class | — | Mixin | 0 | 23 | [ZombieLivingEntityRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieLivingEntityRendererMixin.java) |
| `CancellableEventHandler` | class | — | — | 7 | 65 | [CancellableEventHandler.java](com/faboslav/variantsandventures/common/events/base/CancellableEventHandler.java) |
| `SpawnEggItemAccessor` | interface | — | Mixin | 0 | 17 | [SpawnEggItemAccessor.java](com/faboslav/variantsandventures/common/mixin/SpawnEggItemAccessor.java) |
| `ZombieLivingEntityMixin` | class | Entity | Mixin | 0 | 22 | [ZombieLivingEntityMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieLivingEntityMixin.java) |
| `ZombieAbstractZombieRendererMixin` | class | — | Mixin | 0 | 15 | [ZombieAbstractZombieRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieAbstractZombieRendererMixin.java) |
| `ZombieMobRendererMixin` | class | — | Mixin | 0 | 15 | [ZombieMobRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMobRendererMixin.java) |
| `ProjectileEntityMixin` | class | Entity | Mixin | 0 | 25 | [ProjectileEntityMixin.java](com/faboslav/variantsandventures/common/mixin/ProjectileEntityMixin.java) |
| `ZombieRendererMixin` | class | ZombieAbstractZombieRendererMixin | Mixin | 0 | 23 | [ZombieRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieRendererMixin.java) |
| `ZombieHumanoidMobRendererMixin` | class | — | Mixin | 0 | 15 | [ZombieHumanoidMobRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieHumanoidMobRendererMixin.java) |
| `EventHandler` | class | — | — | 3 | 24 | [EventHandler.java](com/faboslav/variantsandventures/common/events/base/EventHandler.java) |
| `VariantsAndVenturesSoundEvents` | class | — | — | 1 | 46 | [VariantsAndVenturesSoundEvents.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesSoundEvents.java) |
| `RegisterEntityRenderersEvent` | record | — | — | 1 | 20 | [RegisterEntityRenderersEvent.java](com/faboslav/variantsandventures/common/events/client/RegisterEntityRenderersEvent.java) |
| `AbstractArrowMixin` | class | AbstractArrowProjectileMixin | Mixin | 0 | 16 | [AbstractArrowMixin.java](com/faboslav/variantsandventures/common/mixin/AbstractArrowMixin.java) |
| `ZombieMobMixin` | class | ZombieLivingEntityMixin | Mixin | 2 | 23 | [ZombieMobMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMobMixin.java) |
| `DungeonFeatureMixin` | class | — | Mixin | 0 | 79 | [DungeonFeatureMixin.java](com/faboslav/variantsandventures/common/mixin/DungeonFeatureMixin.java) |
| `ZombieMixin` | class | ZombieMonsterMixin | Mixin | 0 | 94 | [ZombieMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMixin.java) |
| `ZombieRenderStateMixin` | class | — | Mixin | 0 | 9 | [ZombieRenderStateMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieRenderStateMixin.java) |
| `MobAccessor` | interface | — | Mixin | 0 | 13 | [MobAccessor.java](com/faboslav/variantsandventures/common/mixin/MobAccessor.java) |
| `AbstractArrowProjectileMixin` | class | — | Mixin | 1 | 13 | [AbstractArrowProjectileMixin.java](com/faboslav/variantsandventures/common/mixin/AbstractArrowProjectileMixin.java) |
| `IntegratedServerLoaderMixin` | class | — | Mixin | 1 | 24 | [IntegratedServerLoaderMixin.java](com/faboslav/variantsandventures/common/mixin/IntegratedServerLoaderMixin.java) |
| `ZombiePathfinderMobMixin` | class | ZombieMobMixin | Mixin | 0 | 14 | [ZombiePathfinderMobMixin.java](com/faboslav/variantsandventures/common/mixin/ZombiePathfinderMobMixin.java) |
| `ZombieMonsterMixin` | class | ZombiePathfinderMobMixin | Mixin | 0 | 14 | [ZombieMonsterMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMonsterMixin.java) |
| `JigsawStructureAccessor` | interface | — | Mixin | 0 | 17 | [JigsawStructureAccessor.java](com/faboslav/variantsandventures/common/mixin/JigsawStructureAccessor.java) |
| `MessageHandler` | class | — | — | 1 | 14 | [MessageHandler.java](com/faboslav/variantsandventures/common/network/MessageHandler.java) |
| `AddItemGroupEntriesEvent` | record | — | — | 3 | 72 | [AddItemGroupEntriesEvent.java](com/faboslav/variantsandventures/common/events/AddItemGroupEntriesEvent.java) |
| `RegisterEntityAttributesEvent` | record | — | — | 1 | 16 | [RegisterEntityAttributesEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/RegisterEntityAttributesEvent.java) |
| `RegisterEntitySpawnRestrictionsEvent` | record | — | — | 1 | 25 | [RegisterEntitySpawnRestrictionsEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/RegisterEntitySpawnRestrictionsEvent.java) |
| `EntitySpawnEvent` | record | — | — | 0 | 11 | [EntitySpawnEvent.java](com/faboslav/variantsandventures/common/events/entity/EntitySpawnEvent.java) |
| `RegisterItemColorEvent` | record | — | — | 1 | 15 | [RegisterItemColorEvent.java](com/faboslav/variantsandventures/common/events/item/RegisterItemColorEvent.java) |
| `RegisterEntityLayersEvent` | record | — | — | 1 | 16 | [RegisterEntityLayersEvent.java](com/faboslav/variantsandventures/common/events/client/RegisterEntityLayersEvent.java) |
| `SyncZombieIsShakingPacketFromServer` | record | Packet<SyncZombieIsShakingPacketFrom | — | 6 | 50 | [SyncZombieIsShakingPacketFromServer.java](com/faboslav/variantsandventures/common/network/packet/SyncZombieIsShakingPacketFromServer.java) |
| `VerdantEntity` | class | Skeleton | — | 13 | 105 | [VerdantEntity.java](com/faboslav/variantsandventures/common/entity/mob/VerdantEntity.java) |
| `ThicketEntity` | class | Zombie | — | 10 | 80 | [ThicketEntity.java](com/faboslav/variantsandventures/common/entity/mob/ThicketEntity.java) |
| `GelidEntity` | class | Zombie | — | 11 | 110 | [GelidEntity.java](com/faboslav/variantsandventures/common/entity/mob/GelidEntity.java) |
| `VariantsAndVenturesConfig` | class | — | — | 1 | 193 | [VariantsAndVenturesConfig.java](com/faboslav/variantsandventures/common/config/VariantsAndVenturesConfig.java) |
| `GelidEntityRenderer` | class | AbstractZombieRenderer | — | 3 | 46 | [GelidEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/GelidEntityRenderer.java) |
| `ThicketEntityRenderer` | class | AbstractZombieRenderer | — | 2 | 41 | [ThicketEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/ThicketEntityRenderer.java) |
| `MurkEntityRenderer` | class | HumanoidMobRenderer | — | 1 | 41 | [MurkEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/MurkEntityRenderer.java) |
| `VerdantEntityRenderer` | class | HumanoidMobRenderer | — | 1 | 31 | [VerdantEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/VerdantEntityRenderer.java) |
| `VariantsAndVenturesEntityRenderers` | class | — | — | 1 | 20 | [VariantsAndVenturesEntityRenderers.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesEntityRenderers.java) |
| `VariantsAndVenturesClientNeoForge` | class | — | — | 5 | 48 | [VariantsAndVenturesClientNeoForge.java](com/faboslav/variantsandventures/neoforge/VariantsAndVenturesClientNeoForge.java) |
| `GelidOnSnowballHitEvent` | class | — | — | 2 | 29 | [GelidOnSnowballHitEvent.java](com/faboslav/variantsandventures/common/entity/event/GelidOnSnowballHitEvent.java) |
| `AddSpawnBiomeModificationsEvent` | record | — | — | 1 | 21 | [AddSpawnBiomeModificationsEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/AddSpawnBiomeModificationsEvent.java) |
| `SetupEvent` | record | — | — | 1 | 13 | [SetupEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/SetupEvent.java) |
| `ProjectileHitEvent` | record | — | — | 0 | 10 | [ProjectileHitEvent.java](com/faboslav/variantsandventures/common/events/entity/ProjectileHitEvent.java) |
| `MurkEntityModel` | class | SkeletonModel | — | 5 | 72 | [MurkEntityModel.java](com/faboslav/variantsandventures/common/client/model/MurkEntityModel.java) |
| `VariantsAndVenturesClient` | class | — | — | 2 | 23 | [VariantsAndVenturesClient.java](com/faboslav/variantsandventures/common/VariantsAndVenturesClient.java) |
| `StrayOnEntitySpawn` | class | — | — | 1 | 21 | [StrayOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/StrayOnEntitySpawn.java) |
| `OnEntitySpawn` | class | — | — | 1 | 95 | [OnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/OnEntitySpawn.java) |
| `ThicketOnEntitySpawn` | class | — | — | 1 | 22 | [ThicketOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/ThicketOnEntitySpawn.java) |
| `GelidOnEntitySpawn` | class | — | — | 1 | 22 | [GelidOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/GelidOnEntitySpawn.java) |
| `HuskOnEntitySpawn` | class | — | — | 1 | 21 | [HuskOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/HuskOnEntitySpawn.java) |
| `BoggedOnEntitySpawn` | class | — | — | 1 | 21 | [BoggedOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/BoggedOnEntitySpawn.java) |
| `VerdantOnEntitySpawn` | class | — | — | 1 | 22 | [VerdantOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/VerdantOnEntitySpawn.java) |
| `VersionedEntitySpawnReason` | class | — | — | 0 | 12 | [VersionedEntitySpawnReason.java](com/faboslav/variantsandventures/common/versions/VersionedEntitySpawnReason.java) |
| `VersionedEntityTypeResourceId` | class | — | — | 1 | 8 | [VersionedEntityTypeResourceId.java](com/faboslav/variantsandventures/common/versions/VersionedEntityTypeResourceId.java) |
| `VersionedBlockPathType` | class | — | — | 0 | 8 | [VersionedBlockPathType.java](com/faboslav/variantsandventures/common/versions/VersionedBlockPathType.java) |
| `GelidOverlayFeatureRenderer` | class | RenderLayer | — | 1 | 53 | [GelidOverlayFeatureRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/feature/GelidOverlayFeatureRenderer.java) |

## Mixin（19 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ZombieLivingEntityRendererMixin` | `ZombieLivingEntityRenderer` | [ZombieLivingEntityRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieLivingEntityRendererMixin.java) |
| `SpawnEggItemAccessor` | `SpawnEggItemAccessor` | [SpawnEggItemAccessor.java](com/faboslav/variantsandventures/common/mixin/SpawnEggItemAccessor.java) |
| `ZombieLivingEntityMixin` | `ZombieLivingEntity` | [ZombieLivingEntityMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieLivingEntityMixin.java) |
| `ZombieAbstractZombieRendererMixin` | `ZombieAbstractZombieRenderer` | [ZombieAbstractZombieRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieAbstractZombieRendererMixin.java) |
| `ZombieMobRendererMixin` | `ZombieMobRenderer` | [ZombieMobRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMobRendererMixin.java) |
| `ProjectileEntityMixin` | `ProjectileEntity` | [ProjectileEntityMixin.java](com/faboslav/variantsandventures/common/mixin/ProjectileEntityMixin.java) |
| `ZombieRendererMixin` | `ZombieRenderer` | [ZombieRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieRendererMixin.java) |
| `ZombieHumanoidMobRendererMixin` | `ZombieHumanoidMobRenderer` | [ZombieHumanoidMobRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieHumanoidMobRendererMixin.java) |
| `AbstractArrowMixin` | `AbstractArrow` | [AbstractArrowMixin.java](com/faboslav/variantsandventures/common/mixin/AbstractArrowMixin.java) |
| `ZombieMobMixin` | `ZombieMob` | [ZombieMobMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMobMixin.java) |
| `DungeonFeatureMixin` | `DungeonFeature` | [DungeonFeatureMixin.java](com/faboslav/variantsandventures/common/mixin/DungeonFeatureMixin.java) |
| `ZombieMixin` | `Zombie` | [ZombieMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMixin.java) |
| `ZombieRenderStateMixin` | `ZombieRenderState` | [ZombieRenderStateMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieRenderStateMixin.java) |
| `MobAccessor` | `MobAccessor` | [MobAccessor.java](com/faboslav/variantsandventures/common/mixin/MobAccessor.java) |
| `AbstractArrowProjectileMixin` | `AbstractArrowProjectile` | [AbstractArrowProjectileMixin.java](com/faboslav/variantsandventures/common/mixin/AbstractArrowProjectileMixin.java) |
| `IntegratedServerLoaderMixin` | `IntegratedServerLoader` | [IntegratedServerLoaderMixin.java](com/faboslav/variantsandventures/common/mixin/IntegratedServerLoaderMixin.java) |
| `ZombiePathfinderMobMixin` | `ZombiePathfinderMob` | [ZombiePathfinderMobMixin.java](com/faboslav/variantsandventures/common/mixin/ZombiePathfinderMobMixin.java) |
| `ZombieMonsterMixin` | `ZombieMonster` | [ZombieMonsterMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMonsterMixin.java) |
| `JigsawStructureAccessor` | `JigsawStructureAccessor` | [JigsawStructureAccessor.java](com/faboslav/variantsandventures/common/mixin/JigsawStructureAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `VariantsAndVenturesConfig` | — | [VariantsAndVenturesConfig.java](com/faboslav/variantsandventures/common/config/VariantsAndVenturesConfig.java) |

## 全部类（84）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `VariantsAndVenturesNeoForge` | class | — | Mod | 8 | 94 | [VariantsAndVenturesNeoForge.java](com/faboslav/variantsandventures/neoforge/VariantsAndVenturesNeoForge.java) |
| `MurkEntity` | class | Skeleton | — | 44 | 434 | [MurkEntity.java](com/faboslav/variantsandventures/common/entity/mob/MurkEntity.java) |
| `ZombieLivingEntityRendererMixin` | class | — | Mixin | 0 | 23 | [ZombieLivingEntityRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieLivingEntityRendererMixin.java) |
| `CancellableEventHandler` | class | — | — | 7 | 65 | [CancellableEventHandler.java](com/faboslav/variantsandventures/common/events/base/CancellableEventHandler.java) |
| `SpawnEggItemAccessor` | interface | — | Mixin | 0 | 17 | [SpawnEggItemAccessor.java](com/faboslav/variantsandventures/common/mixin/SpawnEggItemAccessor.java) |
| `ZombieLivingEntityMixin` | class | Entity | Mixin | 0 | 22 | [ZombieLivingEntityMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieLivingEntityMixin.java) |
| `ZombieAbstractZombieRendererMixin` | class | — | Mixin | 0 | 15 | [ZombieAbstractZombieRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieAbstractZombieRendererMixin.java) |
| `ZombieMobRendererMixin` | class | — | Mixin | 0 | 15 | [ZombieMobRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMobRendererMixin.java) |
| `ProjectileEntityMixin` | class | Entity | Mixin | 0 | 25 | [ProjectileEntityMixin.java](com/faboslav/variantsandventures/common/mixin/ProjectileEntityMixin.java) |
| `ZombieRendererMixin` | class | ZombieAbstractZombieRendererMixin | Mixin | 0 | 23 | [ZombieRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieRendererMixin.java) |
| `ZombieHumanoidMobRendererMixin` | class | — | Mixin | 0 | 15 | [ZombieHumanoidMobRendererMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieHumanoidMobRendererMixin.java) |
| `EventHandler` | class | — | — | 3 | 24 | [EventHandler.java](com/faboslav/variantsandventures/common/events/base/EventHandler.java) |
| `VariantsAndVenturesSoundEvents` | class | — | — | 1 | 46 | [VariantsAndVenturesSoundEvents.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesSoundEvents.java) |
| `RegisterEntityRenderersEvent` | record | — | — | 1 | 20 | [RegisterEntityRenderersEvent.java](com/faboslav/variantsandventures/common/events/client/RegisterEntityRenderersEvent.java) |
| `AbstractArrowMixin` | class | AbstractArrowProjectileMixin | Mixin | 0 | 16 | [AbstractArrowMixin.java](com/faboslav/variantsandventures/common/mixin/AbstractArrowMixin.java) |
| `ZombieMobMixin` | class | ZombieLivingEntityMixin | Mixin | 2 | 23 | [ZombieMobMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMobMixin.java) |
| `DungeonFeatureMixin` | class | — | Mixin | 0 | 79 | [DungeonFeatureMixin.java](com/faboslav/variantsandventures/common/mixin/DungeonFeatureMixin.java) |
| `ZombieMixin` | class | ZombieMonsterMixin | Mixin | 0 | 94 | [ZombieMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMixin.java) |
| `ZombieRenderStateMixin` | class | — | Mixin | 0 | 9 | [ZombieRenderStateMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieRenderStateMixin.java) |
| `MobAccessor` | interface | — | Mixin | 0 | 13 | [MobAccessor.java](com/faboslav/variantsandventures/common/mixin/MobAccessor.java) |
| `AbstractArrowProjectileMixin` | class | — | Mixin | 1 | 13 | [AbstractArrowProjectileMixin.java](com/faboslav/variantsandventures/common/mixin/AbstractArrowProjectileMixin.java) |
| `IntegratedServerLoaderMixin` | class | — | Mixin | 1 | 24 | [IntegratedServerLoaderMixin.java](com/faboslav/variantsandventures/common/mixin/IntegratedServerLoaderMixin.java) |
| `ZombiePathfinderMobMixin` | class | ZombieMobMixin | Mixin | 0 | 14 | [ZombiePathfinderMobMixin.java](com/faboslav/variantsandventures/common/mixin/ZombiePathfinderMobMixin.java) |
| `ZombieMonsterMixin` | class | ZombiePathfinderMobMixin | Mixin | 0 | 14 | [ZombieMonsterMixin.java](com/faboslav/variantsandventures/common/mixin/ZombieMonsterMixin.java) |
| `JigsawStructureAccessor` | interface | — | Mixin | 0 | 17 | [JigsawStructureAccessor.java](com/faboslav/variantsandventures/common/mixin/JigsawStructureAccessor.java) |
| `MessageHandler` | class | — | — | 1 | 14 | [MessageHandler.java](com/faboslav/variantsandventures/common/network/MessageHandler.java) |
| `AddItemGroupEntriesEvent` | record | — | — | 3 | 72 | [AddItemGroupEntriesEvent.java](com/faboslav/variantsandventures/common/events/AddItemGroupEntriesEvent.java) |
| `RegisterEntityAttributesEvent` | record | — | — | 1 | 16 | [RegisterEntityAttributesEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/RegisterEntityAttributesEvent.java) |
| `RegisterEntitySpawnRestrictionsEvent` | record | — | — | 1 | 25 | [RegisterEntitySpawnRestrictionsEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/RegisterEntitySpawnRestrictionsEvent.java) |
| `EntitySpawnEvent` | record | — | — | 0 | 11 | [EntitySpawnEvent.java](com/faboslav/variantsandventures/common/events/entity/EntitySpawnEvent.java) |
| `RegisterItemColorEvent` | record | — | — | 1 | 15 | [RegisterItemColorEvent.java](com/faboslav/variantsandventures/common/events/item/RegisterItemColorEvent.java) |
| `RegisterEntityLayersEvent` | record | — | — | 1 | 16 | [RegisterEntityLayersEvent.java](com/faboslav/variantsandventures/common/events/client/RegisterEntityLayersEvent.java) |
| `SyncZombieIsShakingPacketFromServer` | record | Packet<SyncZombieIsShakingPacketFrom | — | 6 | 50 | [SyncZombieIsShakingPacketFromServer.java](com/faboslav/variantsandventures/common/network/packet/SyncZombieIsShakingPacketFromServer.java) |
| `VerdantEntity` | class | Skeleton | — | 13 | 105 | [VerdantEntity.java](com/faboslav/variantsandventures/common/entity/mob/VerdantEntity.java) |
| `ThicketEntity` | class | Zombie | — | 10 | 80 | [ThicketEntity.java](com/faboslav/variantsandventures/common/entity/mob/ThicketEntity.java) |
| `GelidEntity` | class | Zombie | — | 11 | 110 | [GelidEntity.java](com/faboslav/variantsandventures/common/entity/mob/GelidEntity.java) |
| `VariantsAndVenturesConfig` | class | — | — | 1 | 193 | [VariantsAndVenturesConfig.java](com/faboslav/variantsandventures/common/config/VariantsAndVenturesConfig.java) |
| `GelidEntityRenderer` | class | AbstractZombieRenderer | — | 3 | 46 | [GelidEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/GelidEntityRenderer.java) |
| `ThicketEntityRenderer` | class | AbstractZombieRenderer | — | 2 | 41 | [ThicketEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/ThicketEntityRenderer.java) |
| `MurkEntityRenderer` | class | HumanoidMobRenderer | — | 1 | 41 | [MurkEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/MurkEntityRenderer.java) |
| `VerdantEntityRenderer` | class | HumanoidMobRenderer | — | 1 | 31 | [VerdantEntityRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/VerdantEntityRenderer.java) |
| `VariantsAndVenturesEntityRenderers` | class | — | — | 1 | 20 | [VariantsAndVenturesEntityRenderers.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesEntityRenderers.java) |
| `VariantsAndVenturesClientNeoForge` | class | — | — | 5 | 48 | [VariantsAndVenturesClientNeoForge.java](com/faboslav/variantsandventures/neoforge/VariantsAndVenturesClientNeoForge.java) |
| `GelidOnSnowballHitEvent` | class | — | — | 2 | 29 | [GelidOnSnowballHitEvent.java](com/faboslav/variantsandventures/common/entity/event/GelidOnSnowballHitEvent.java) |
| `AddSpawnBiomeModificationsEvent` | record | — | — | 1 | 21 | [AddSpawnBiomeModificationsEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/AddSpawnBiomeModificationsEvent.java) |
| `SetupEvent` | record | — | — | 1 | 13 | [SetupEvent.java](com/faboslav/variantsandventures/common/events/lifecycle/SetupEvent.java) |
| `ProjectileHitEvent` | record | — | — | 0 | 10 | [ProjectileHitEvent.java](com/faboslav/variantsandventures/common/events/entity/ProjectileHitEvent.java) |
| `MurkEntityModel` | class | SkeletonModel | — | 5 | 72 | [MurkEntityModel.java](com/faboslav/variantsandventures/common/client/model/MurkEntityModel.java) |
| `VariantsAndVenturesClient` | class | — | — | 2 | 23 | [VariantsAndVenturesClient.java](com/faboslav/variantsandventures/common/VariantsAndVenturesClient.java) |
| `StrayOnEntitySpawn` | class | — | — | 1 | 21 | [StrayOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/StrayOnEntitySpawn.java) |
| `OnEntitySpawn` | class | — | — | 1 | 95 | [OnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/OnEntitySpawn.java) |
| `ThicketOnEntitySpawn` | class | — | — | 1 | 22 | [ThicketOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/ThicketOnEntitySpawn.java) |
| `GelidOnEntitySpawn` | class | — | — | 1 | 22 | [GelidOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/GelidOnEntitySpawn.java) |
| `HuskOnEntitySpawn` | class | — | — | 1 | 21 | [HuskOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/HuskOnEntitySpawn.java) |
| `BoggedOnEntitySpawn` | class | — | — | 1 | 21 | [BoggedOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/BoggedOnEntitySpawn.java) |
| `VerdantOnEntitySpawn` | class | — | — | 1 | 22 | [VerdantOnEntitySpawn.java](com/faboslav/variantsandventures/common/entity/event/VerdantOnEntitySpawn.java) |
| `VersionedEntitySpawnReason` | class | — | — | 0 | 12 | [VersionedEntitySpawnReason.java](com/faboslav/variantsandventures/common/versions/VersionedEntitySpawnReason.java) |
| `VersionedEntityTypeResourceId` | class | — | — | 1 | 8 | [VersionedEntityTypeResourceId.java](com/faboslav/variantsandventures/common/versions/VersionedEntityTypeResourceId.java) |
| `VersionedBlockPathType` | class | — | — | 0 | 8 | [VersionedBlockPathType.java](com/faboslav/variantsandventures/common/versions/VersionedBlockPathType.java) |
| `GelidOverlayFeatureRenderer` | class | RenderLayer | — | 1 | 53 | [GelidOverlayFeatureRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/feature/GelidOverlayFeatureRenderer.java) |
| `ThicketOverlayFeatureRenderer` | class | RenderLayer | — | 1 | 53 | [ThicketOverlayFeatureRenderer.java](com/faboslav/variantsandventures/common/client/render/entity/feature/ThicketOverlayFeatureRenderer.java) |
| `ThicketEntityModel` | class | ZombieModel | — | 4 | 36 | [ThicketEntityModel.java](com/faboslav/variantsandventures/common/client/model/ThicketEntityModel.java) |
| `VerdantEntityModel` | class | SkeletonModel | — | 0 | 12 | [VerdantEntityModel.java](com/faboslav/variantsandventures/common/client/model/VerdantEntityModel.java) |
| `GelidEntityModel` | class | ZombieModel | — | 4 | 36 | [GelidEntityModel.java](com/faboslav/variantsandventures/common/client/model/GelidEntityModel.java) |
| `VariantsAndVenturesItemGroups` | class | — | — | 1 | 61 | [VariantsAndVenturesItemGroups.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesItemGroups.java) |
| `VariantsAndVenturesEntityTypes` | class | — | — | 3 | 91 | [VariantsAndVenturesEntityTypes.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesEntityTypes.java) |
| `VariantsAndVenturesBlocks` | class | — | — | 0 | 14 | [VariantsAndVenturesBlocks.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesBlocks.java) |
| `VariantsAndVenturesItems` | class | — | — | 3 | 61 | [VariantsAndVenturesItems.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesItems.java) |
| `VariantsAndVentures` | class | — | — | 9 | 89 | [VariantsAndVentures.java](com/faboslav/variantsandventures/common/VariantsAndVentures.java) |
| `GelidSnowballRangedAttackGoal` | class | Goal | — | 5 | 71 | [GelidSnowballRangedAttackGoal.java](com/faboslav/variantsandventures/common/entity/ai/GelidSnowballRangedAttackGoal.java) |
| `LeaveWaterGoal` | class | MoveToBlockGoal | — | 5 | 40 | [LeaveWaterGoal.java](com/faboslav/variantsandventures/common/entity/ai/goal/LeaveWaterGoal.java) |
| `TargetAboveWaterGoal` | class | Goal | — | 5 | 50 | [TargetAboveWaterGoal.java](com/faboslav/variantsandventures/common/entity/ai/goal/TargetAboveWaterGoal.java) |
| `VariantsAndVenturesMixinPlugin` | class | IMixinConfigPlugin | — | 8 | 41 | [VariantsAndVenturesMixinPlugin.java](com/faboslav/variantsandventures/common/mixin/plugin/VariantsAndVenturesMixinPlugin.java) |
| `AdvancementHelper` | class | — | — | 1 | 33 | [AdvancementHelper.java](com/faboslav/variantsandventures/common/util/AdvancementHelper.java) |
| `WanderAroundOnSurfaceGoal` | class | Goal | — | 4 | 73 | [WanderAroundOnSurfaceGoal.java](com/faboslav/variantsandventures/common/entity/ai/goal/WanderAroundOnSurfaceGoal.java) |
| `VersionedNbt` | class | — | — | 2 | 14 | [VersionedNbt.java](com/faboslav/variantsandventures/common/versions/VersionedNbt.java) |
| `VersionedGameRulesProvider` | class | — | — | 1 | 11 | [VersionedGameRulesProvider.java](com/faboslav/variantsandventures/common/versions/VersionedGameRulesProvider.java) |
| `VersionedInteractionResult` | class | — | — | 1 | 11 | [VersionedInteractionResult.java](com/faboslav/variantsandventures/common/versions/VersionedInteractionResult.java) |
| `VersionedSpawnPlacementType` | class | — | — | 0 | 9 | [VersionedSpawnPlacementType.java](com/faboslav/variantsandventures/common/versions/VersionedSpawnPlacementType.java) |
| `ZombieApi` | interface | — | — | 0 | 8 | [ZombieApi.java](com/faboslav/variantsandventures/common/api/ZombieApi.java) |
| `LayerDefinitionFactory` | class | — | — | 2 | 19 | [LayerDefinitionFactory.java](com/faboslav/variantsandventures/common/util/LayerDefinitionFactory.java) |
| `VariantsAndVenturesTags` | class | — | — | 2 | 24 | [VariantsAndVenturesTags.java](com/faboslav/variantsandventures/common/tag/VariantsAndVenturesTags.java) |
| `VariantsAndVenturesStructurePoolAliases` | class | — | — | 2 | 99 | [VariantsAndVenturesStructurePoolAliases.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesStructurePoolAliases.java) |
| `VariantsAndVenturesModelLayers` | class | — | — | 1 | 52 | [VariantsAndVenturesModelLayers.java](com/faboslav/variantsandventures/common/init/VariantsAndVenturesModelLayers.java) |
