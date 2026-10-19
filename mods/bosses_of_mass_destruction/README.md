# Bosses of Mass Destruction（`bosses_of_mass_destruction`）

> jar：`[祸乱鬼魅]BOMD.jar` ｜ 版本：`1.3.3` ｜ 作者：CerbonXD
> 规模：**264 个类 / 48 个包 / 15398 行** ｜ 事件处理类 3 ｜ Mixin 8 ｜ 注册/管理类 24

**依赖**：`neoforge`、`minecraft`、`cerbons_api`、`cloth_config`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom` | 24 | `ClientSporeEffectHandler`、`ClientDeathEffectHandler`、`VoidBlossomClientSpikeHandler` |
| `com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet` | 22 | `GauntletClientLaserHandler`、`GauntletClientEnergyShieldHandler`、`ClientGauntletDeathHandler` |
| `com.cerbon.bosses_of_mass_destruction.entity.custom.lich` | 18 | `LichParticleHandler`、`LichEntity`、`EternalNightRenderer` |
| `com.cerbon.bosses_of_mass_destruction.block.custom` | 18 | `MobWardBlock`、`MonolithBlock`、`VoidLilyBlockEntity` |
| `com.cerbon.bosses_of_mass_destruction.entity.util` | 17 | `IEntityEventHandler`、`CompositeEntityEventHandler`、`BaseEntity` |
| `com.cerbon.bosses_of_mass_destruction.client.render` | 17 | `BMDBlockEntityRenderer`、`SimpleEntityRenderer`、`BillboardRenderer` |
| `com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith` | 13 | `ObsidilithEntity`、`ObsidilithEffectHandler`、`ShieldDamageHandler` |
| `com.cerbon.bosses_of_mass_destruction.packet.custom` | 11 | `SendDeltaMovementS2CPacket`、`ChangeHitboxS2CPacket`、`SendParticleS2CPacket` |
| `com.cerbon.bosses_of_mass_destruction.item.custom` | 10 | `ChargedEnderPearlEntity`、`SoulStarEntity`、`SoulStarItem` |
| `com.cerbon.bosses_of_mass_destruction.entity.spawn` | 10 | `IEntityProvider`、`MobEntitySpawnPredicate`、`CompoundTagEntityProvider` |
| `com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern` | 8 | `BossBlockDecorator`、`SporeBlossomCaveDecorator`、`VoidBlossomArenaStructureFeature` |
| `com.cerbon.bosses_of_mass_destruction.util` | 7 | `VanillaCopies`、`AnimationUtils`、`VanillaCopiesServer` |
| `com.cerbon.bosses_of_mass_destruction.mixin` | 6 | `LivingEntityMixin`、`IceBlockMixin`、`EnderEyeUseMixin` |
| `com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox` | 5 | `NetworkedHitboxManager`、`VoidBlossomCompoundHitbox`、`VoidBlossomHitboxes` |
| `com.cerbon.bosses_of_mass_destruction.entity.damage` | 5 | `CompositeDamageHandler`、`IDamageHandler`、`StagedDamageHandler` |
| `com.cerbon.bosses_of_mass_destruction.structure.structure_repair` | 5 | `LichStructureRepair`、`ObsidilithStructureRepair`、`VoidBlossomStructureRepair` |
| `com.cerbon.bosses_of_mass_destruction.entity.ai` | 5 | `ISteering`、`ITargetSelector`、`VelocitySteering` |
| `com.cerbon.bosses_of_mass_destruction.entity.ai.action` | 5 | `IAction`、`CooldownAction`、`IActionWithCooldown` |
| `com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction` | 5 | `ValidDirectionOr`、`IValidDirection`、`InDesiredRange` |
| `com.cerbon.bosses_of_mass_destruction.config.mob` | 4 | `VoidBlossomConfig`、`GauntletConfig`、`ObsidilithConfig` |
| `com.cerbon.bosses_of_mass_destruction.projectile` | 4 | `BaseThrownItemProjectile`、`SporeBallProjectile`、`PetalBladeProjectile` |
| `com.cerbon.bosses_of_mass_destruction.entity.ai.goals` | 4 | `ActionGoal`、`CompositeGoal`、`FindTargetGoal` |
| `com.cerbon.bosses_of_mass_destruction.neoforge.event` | 3 | `BMDClientEventsNeo`、`NeoEvents`、`BMDEventsNeo` |
| `com.cerbon.bosses_of_mass_destruction.item` | 3 | `BMDItems`、`BMDCreativeModeTabs`、`BMDFoods` |
| `com.cerbon.bosses_of_mass_destruction.structure.util` | 3 | `CodeStructurePiece`、`IPieceGenerator`、`IStructurePiece` |
| `com.cerbon.bosses_of_mass_destruction.particle` | 3 | `ParticleFactories`、`BMDSimpleParticleType`、`BMDParticles` |
| `com.cerbon.bosses_of_mass_destruction.config` | 2 | `BMDConfig`、`GeneralConfig` |
| `com.cerbon.bosses_of_mass_destruction.block` | 2 | `BMDBlockEntities`、`BMDBlocks` |
| `com.cerbon.bosses_of_mass_destruction.capability` | 2 | `IChunkBlockCache`、`ChunkBlockCache` |
| `com.cerbon.bosses_of_mass_destruction.entity` | 2 | `GeoModel`、`BMDEntities` |
| `com.cerbon.bosses_of_mass_destruction.projectile.comet` | 2 | `CometProjectile`、`CometCodeAnimations` |
| `com.cerbon.bosses_of_mass_destruction.structure` | 2 | `StructureRegister`、`BMDStructures` |
| `com.cerbon.bosses_of_mass_destruction.animation` | 2 | `IAnimationTimer`、`PauseAnimationTimer` |
| `com.cerbon.bosses_of_mass_destruction.neoforge` | 1 | `BossesOfMassDestructionNeo` |
| `com.cerbon.bosses_of_mass_destruction.neoforge.mixin.test` | 1 | `TestMixin` |
| `com.cerbon.bosses_of_mass_destruction.mixin.test` | 1 | `TestMixin` |
| `com.cerbon.bosses_of_mass_destruction.neoforge.platform` | 1 | `NeoCapabilityHelper` |
| `com.cerbon.bosses_of_mass_destruction.platform.services` | 1 | `ICapabilityHelper` |
| `com.cerbon.bosses_of_mass_destruction.neoforge.attachment.saved_data` | 1 | `LevelChunkBlockCache` |
| `com.cerbon.bosses_of_mass_destruction.packet` | 1 | `BMDPackets` |
| … | | 其余 8 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BMDClientEventsNeo` | class | — | EventBusSubscriber | 2 | 272 | [BMDClientEventsNeo.java](com/cerbon/bosses_of_mass_destruction/neoforge/event/BMDClientEventsNeo.java) |
| `NeoEvents` | class | — | EventBusSubscriber | 3 | 56 | [NeoEvents.java](com/cerbon/bosses_of_mass_destruction/neoforge/event/NeoEvents.java) |
| `BMDEventsNeo` | class | — | EventBusSubscriber | 2 | 83 | [BMDEventsNeo.java](com/cerbon/bosses_of_mass_destruction/neoforge/event/BMDEventsNeo.java) |
| `IEntityEventHandler` | interface | — | — | 0 | 6 | [IEntityEventHandler.java](com/cerbon/bosses_of_mass_destruction/entity/util/IEntityEventHandler.java) |
| `CompositeEntityEventHandler` | class | IEntityEventHandler | — | 1 | 20 | [CompositeEntityEventHandler.java](com/cerbon/bosses_of_mass_destruction/entity/util/CompositeEntityEventHandler.java) |
| `BossesOfMassDestructionNeo` | class | — | Mod | 0 | 15 | [BossesOfMassDestructionNeo.java](com/cerbon/bosses_of_mass_destruction/neoforge/BossesOfMassDestructionNeo.java) |
| `BMDConfig` | class | ConfigData | Config | 1 | 59 | [BMDConfig.java](com/cerbon/bosses_of_mass_destruction/config/BMDConfig.java) |
| `LichParticleHandler` | class | IEntityEventHandler | — | 15 | 231 | [LichParticleHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/lich/LichParticleHandler.java) |
| `GauntletClientLaserHandler` | class | IEntityTick<Level> | — | 8 | 118 | [GauntletClientLaserHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/GauntletClientLaserHandler.java) |
| `NetworkedHitboxManager` | class | ICompoundHitbox | — | 3 | 36 | [NetworkedHitboxManager.java](com/cerbon/bosses_of_mass_destruction/entity/custom/void_blossom/hitbox/NetworkedHitboxManager.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 20 | [LivingEntityMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/LivingEntityMixin.java) |
| `IceBlockMixin` | class | — | Mixin | 1 | 48 | [IceBlockMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/IceBlockMixin.java) |
| `GauntletClientEnergyShieldHandler` | class | IDataAccessorHandler | — | 4 | 64 | [GauntletClientEnergyShieldHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/GauntletClientEnergyShieldHandler.java) |
| `ClientGauntletDeathHandler` | class | IEntityTick<Level> | — | 1 | 22 | [ClientGauntletDeathHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/ClientGauntletDeathHandler.java) |
| `ObsidilithEntity` | class | BaseEntity | — | 20 | 236 | [ObsidilithEntity.java](com/cerbon/bosses_of_mass_destruction/entity/custom/obsidilith/ObsidilithEntity.java) |
| `ClientSporeEffectHandler` | class | IEntityEventHandler | — | 2 | 41 | [ClientSporeEffectHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/void_blossom/ClientSporeEffectHandler.java) |
| `ClientDeathEffectHandler` | class | IEntityEventHandler | — | 1 | 42 | [ClientDeathEffectHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/void_blossom/ClientDeathEffectHandler.java) |
| `VoidBlossomClientSpikeHandler` | class | IEntityTick<Level> | — | 4 | 71 | [VoidBlossomClientSpikeHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/void_blossom/VoidBlossomClientSpikeHandler.java) |
| `BaseEntity` | class | PathfinderMob | — | 20 | 200 | [BaseEntity.java](com/cerbon/bosses_of_mass_destruction/entity/util/BaseEntity.java) |
| `GauntletGoalHandler` | class | INbtHandler | — | 7 | 88 | [GauntletGoalHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/GauntletGoalHandler.java) |
| `ObsidilithEffectHandler` | class | — | — | 7 | 150 | [ObsidilithEffectHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/obsidilith/ObsidilithEffectHandler.java) |
| `LichEntity` | class | BaseEntity | — | 15 | 209 | [LichEntity.java](com/cerbon/bosses_of_mass_destruction/entity/custom/lich/LichEntity.java) |
| `IDataAccessorHandler` | interface | — | — | 0 | 8 | [IDataAccessorHandler.java](com/cerbon/bosses_of_mass_destruction/entity/util/IDataAccessorHandler.java) |
| `CompositeDataAccessorHandler` | class | IDataAccessorHandler | — | 1 | 21 | [CompositeDataAccessorHandler.java](com/cerbon/bosses_of_mass_destruction/entity/util/CompositeDataAccessorHandler.java) |
| `TestMixin` | class | — | Mixin | 1 | 18 | [TestMixin.java](com/cerbon/bosses_of_mass_destruction/neoforge/mixin/test/TestMixin.java) |
| `MobWardBlock` | class | BaseEntityBlock | — | 18 | 254 | [MobWardBlock.java](com/cerbon/bosses_of_mass_destruction/block/custom/MobWardBlock.java) |
| `EnderEyeUseMixin` | class | — | Mixin | 1 | 19 | [EnderEyeUseMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/EnderEyeUseMixin.java) |
| `NaturalSpawnerMixin` | class | — | Mixin | 1 | 36 | [NaturalSpawnerMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/NaturalSpawnerMixin.java) |
| `ExplosionMixin` | class | — | Mixin | 1 | 39 | [ExplosionMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/ExplosionMixin.java) |
| `BossHealthOverlayMixin` | class | — | Mixin | 1 | 48 | [BossHealthOverlayMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/BossHealthOverlayMixin.java) |
| `TestMixin` | class | — | Mixin | 1 | 19 | [TestMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/test/TestMixin.java) |
| `ChargedEnderPearlEntity` | class | ThrowableItemProjectile | — | 17 | 202 | [ChargedEnderPearlEntity.java](com/cerbon/bosses_of_mass_destruction/item/custom/ChargedEnderPearlEntity.java) |
| `SoulStarEntity` | class | Entity | — | 16 | 209 | [SoulStarEntity.java](com/cerbon/bosses_of_mass_destruction/item/custom/SoulStarEntity.java) |
| `BMDBlockEntityRenderer` | class | GeoBlockRenderer | — | 2 | 64 | [BMDBlockEntityRenderer.java](com/cerbon/bosses_of_mass_destruction/client/render/BMDBlockEntityRenderer.java) |
| `GauntletEntity` | class | BaseEntity | — | 18 | 174 | [GauntletEntity.java](com/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/GauntletEntity.java) |
| `ServerGauntletDeathHandler` | class | IEntityTick<ServerLevel> | — | 3 | 91 | [ServerGauntletDeathHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/ServerGauntletDeathHandler.java) |
| `ShieldDamageHandler` | class | IDamageHandler | — | 3 | 42 | [ShieldDamageHandler.java](com/cerbon/bosses_of_mass_destruction/entity/custom/obsidilith/ShieldDamageHandler.java) |
| `VoidBlossomEntity` | class | BaseEntity | — | 16 | 185 | [VoidBlossomEntity.java](com/cerbon/bosses_of_mass_destruction/entity/custom/void_blossom/VoidBlossomEntity.java) |
| `IMoveHandler` | interface | — | — | 0 | 9 | [IMoveHandler.java](com/cerbon/bosses_of_mass_destruction/entity/util/IMoveHandler.java) |
| `INbtHandler` | interface | — | — | 0 | 10 | [INbtHandler.java](com/cerbon/bosses_of_mass_destruction/entity/util/INbtHandler.java) |
| `CompositeDamageHandler` | class | IDamageHandler | — | 3 | 41 | [CompositeDamageHandler.java](com/cerbon/bosses_of_mass_destruction/entity/damage/CompositeDamageHandler.java) |
| `IDamageHandler` | interface | — | — | 0 | 14 | [IDamageHandler.java](com/cerbon/bosses_of_mass_destruction/entity/damage/IDamageHandler.java) |
| `StagedDamageHandler` | class | IDamageHandler | — | 4 | 43 | [StagedDamageHandler.java](com/cerbon/bosses_of_mass_destruction/entity/damage/StagedDamageHandler.java) |
| `MonolithBlock` | class | BaseEntityBlock | — | 15 | 174 | [MonolithBlock.java](com/cerbon/bosses_of_mass_destruction/block/custom/MonolithBlock.java) |
| `VoidLilyBlockEntity` | class | BlockEntity | — | 5 | 101 | [VoidLilyBlockEntity.java](com/cerbon/bosses_of_mass_destruction/block/custom/VoidLilyBlockEntity.java) |
| `VoidBlossomBlock` | class | Block | — | 12 | 205 | [VoidBlossomBlock.java](com/cerbon/bosses_of_mass_destruction/block/custom/VoidBlossomBlock.java) |
| `LevitationBlockEntity` | class | ChunkCacheBlockEntity | — | 6 | 129 | [LevitationBlockEntity.java](com/cerbon/bosses_of_mass_destruction/block/custom/LevitationBlockEntity.java) |
| `NeoCapabilityHelper` | class | ICapabilityHelper | — | 2 | 24 | [NeoCapabilityHelper.java](com/cerbon/bosses_of_mass_destruction/neoforge/platform/NeoCapabilityHelper.java) |
| `VoidBlossomSummonBlockEntity` | class | BlockEntity | — | 1 | 43 | [VoidBlossomSummonBlockEntity.java](com/cerbon/bosses_of_mass_destruction/block/custom/VoidBlossomSummonBlockEntity.java) |
| `MonolithBlockEntity` | class | ChunkCacheBlockEntity | — | 0 | 15 | [MonolithBlockEntity.java](com/cerbon/bosses_of_mass_destruction/block/custom/MonolithBlockEntity.java) |
| `MobWardBlockEntity` | class | ChunkCacheBlockEntity | — | 0 | 15 | [MobWardBlockEntity.java](com/cerbon/bosses_of_mass_destruction/block/custom/MobWardBlockEntity.java) |
| `ChunkCacheBlockEntity` | class | BlockEntity | — | 2 | 38 | [ChunkCacheBlockEntity.java](com/cerbon/bosses_of_mass_destruction/block/custom/ChunkCacheBlockEntity.java) |
| `GeneralConfig` | class | — | — | 0 | 9 | [GeneralConfig.java](com/cerbon/bosses_of_mass_destruction/config/GeneralConfig.java) |
| `VoidBlossomConfig` | class | — | — | 0 | 13 | [VoidBlossomConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/VoidBlossomConfig.java) |
| `GauntletConfig` | class | — | — | 0 | 16 | [GauntletConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/GauntletConfig.java) |
| `ObsidilithConfig` | class | — | — | 0 | 15 | [ObsidilithConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/ObsidilithConfig.java) |
| `LichConfig` | class | — | — | 0 | 40 | [LichConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/LichConfig.java) |
| `ICapabilityHelper` | interface | — | — | 0 | 15 | [ICapabilityHelper.java](com/cerbon/bosses_of_mass_destruction/platform/services/ICapabilityHelper.java) |
| `SimpleEntityRenderer` | class | EntityRenderer | — | 3 | 38 | [SimpleEntityRenderer.java](com/cerbon/bosses_of_mass_destruction/client/render/SimpleEntityRenderer.java) |
| `ObsidilithArmorRenderer` | class | GeoEntityRenderer | — | 5 | 119 | [ObsidilithArmorRenderer.java](com/cerbon/bosses_of_mass_destruction/entity/custom/obsidilith/ObsidilithArmorRenderer.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoEvents` | 3 | `com.cerbon.bosses_of_mass_destruction.neoforge.event` | [NeoEvents.java](com/cerbon/bosses_of_mass_destruction/neoforge/event/NeoEvents.java) |
| `BMDClientEventsNeo` | 2 | `com.cerbon.bosses_of_mass_destruction.neoforge.event` | [BMDClientEventsNeo.java](com/cerbon/bosses_of_mass_destruction/neoforge/event/BMDClientEventsNeo.java) |
| `BMDEventsNeo` | 2 | `com.cerbon.bosses_of_mass_destruction.neoforge.event` | [BMDEventsNeo.java](com/cerbon/bosses_of_mass_destruction/neoforge/event/BMDEventsNeo.java) |

## Mixin（8 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/LivingEntityMixin.java) |
| `IceBlockMixin` | `IceBlock` | [IceBlockMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/IceBlockMixin.java) |
| `TestMixin` | `Test` | [TestMixin.java](com/cerbon/bosses_of_mass_destruction/neoforge/mixin/test/TestMixin.java) |
| `EnderEyeUseMixin` | `EnderEyeUse` | [EnderEyeUseMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/EnderEyeUseMixin.java) |
| `NaturalSpawnerMixin` | `NaturalSpawner` | [NaturalSpawnerMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/NaturalSpawnerMixin.java) |
| `ExplosionMixin` | `Explosion` | [ExplosionMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/ExplosionMixin.java) |
| `BossHealthOverlayMixin` | `BossHealthOverlay` | [BossHealthOverlayMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/BossHealthOverlayMixin.java) |
| `TestMixin` | `Test` | [TestMixin.java](com/cerbon/bosses_of_mass_destruction/mixin/test/TestMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BMDConfig` | Config | [BMDConfig.java](com/cerbon/bosses_of_mass_destruction/config/BMDConfig.java) |
| `GeneralConfig` | — | [GeneralConfig.java](com/cerbon/bosses_of_mass_destruction/config/GeneralConfig.java) |
| `VoidBlossomConfig` | — | [VoidBlossomConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/VoidBlossomConfig.java) |
| `GauntletConfig` | — | [GauntletConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/GauntletConfig.java) |
| `ObsidilithConfig` | — | [ObsidilithConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/ObsidilithConfig.java) |
| `LichConfig` | — | [LichConfig.java](com/cerbon/bosses_of_mass_destruction/config/mob/LichConfig.java) |

## 按包浏览

- `architectury_inject_BossesofMassDestruction_common_b7e1c6574ee14bd2a64c01b2183c9d59_dfa6cf23dc1196bb03a404274b29fbc74476d993a59326b8500cfe16dd85987cBOMDCommon121133devjar`（1 类）
- `com.cerbon.bosses_of_mass_destruction`（1 类）
- `com.cerbon.bosses_of_mass_destruction.animation`（2 类）
- `com.cerbon.bosses_of_mass_destruction.block`（2 类）
- `com.cerbon.bosses_of_mass_destruction.block.custom`（18 类）
- `com.cerbon.bosses_of_mass_destruction.capability`（2 类）
- `com.cerbon.bosses_of_mass_destruction.capability.util`（1 类）
- `com.cerbon.bosses_of_mass_destruction.client.render`（17 类）
- `com.cerbon.bosses_of_mass_destruction.config`（2 类）
- `com.cerbon.bosses_of_mass_destruction.config.mob`（4 类）
- `com.cerbon.bosses_of_mass_destruction.entity`（2 类）
- `com.cerbon.bosses_of_mass_destruction.entity.ai`（5 类）
- `com.cerbon.bosses_of_mass_destruction.entity.ai.action`（5 类）
- `com.cerbon.bosses_of_mass_destruction.entity.ai.goals`（4 类）
- `com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction`（5 类）
- `com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet`（22 类）
- `com.cerbon.bosses_of_mass_destruction.entity.custom.lich`（18 类）
- `com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith`（13 类）
- `com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom`（24 类）
- `com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox`（5 类）
- `com.cerbon.bosses_of_mass_destruction.entity.damage`（5 类）
- `com.cerbon.bosses_of_mass_destruction.entity.spawn`（10 类）
- `com.cerbon.bosses_of_mass_destruction.entity.util`（17 类）
- `com.cerbon.bosses_of_mass_destruction.entity.util.animation`（1 类）
- `com.cerbon.bosses_of_mass_destruction.item`（3 类）
- `com.cerbon.bosses_of_mass_destruction.item.custom`（10 类）
- `com.cerbon.bosses_of_mass_destruction.mixin`（6 类）
- `com.cerbon.bosses_of_mass_destruction.mixin.test`（1 类）
- `com.cerbon.bosses_of_mass_destruction.neoforge`（1 类）
- `com.cerbon.bosses_of_mass_destruction.neoforge.attachment`（1 类）
- `com.cerbon.bosses_of_mass_destruction.neoforge.attachment.saved_data`（1 类）
- `com.cerbon.bosses_of_mass_destruction.neoforge.event`（3 类）
- `com.cerbon.bosses_of_mass_destruction.neoforge.mixin.test`（1 类）
- `com.cerbon.bosses_of_mass_destruction.neoforge.platform`（1 类）
- `com.cerbon.bosses_of_mass_destruction.packet`（1 类）
- `com.cerbon.bosses_of_mass_destruction.packet.custom`（11 类）
- `com.cerbon.bosses_of_mass_destruction.particle`（3 类）
- `com.cerbon.bosses_of_mass_destruction.platform`（1 类）
- `com.cerbon.bosses_of_mass_destruction.platform.services`（1 类）
- `com.cerbon.bosses_of_mass_destruction.projectile`（4 类）
- `com.cerbon.bosses_of_mass_destruction.projectile.comet`（2 类）
- `com.cerbon.bosses_of_mass_destruction.projectile.util`（1 类）
- `com.cerbon.bosses_of_mass_destruction.sound`（1 类）
- `com.cerbon.bosses_of_mass_destruction.structure`（2 类）
- `com.cerbon.bosses_of_mass_destruction.structure.structure_repair`（5 类）
- `com.cerbon.bosses_of_mass_destruction.structure.util`（3 类）
- `com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern`（8 类）
- `com.cerbon.bosses_of_mass_destruction.util`（7 类）
