# Royal Variations（`royalvariations`）

> jar：`[皇家怪物]royal-variations.jar` ｜ 版本：`2.0` ｜ 作者：Mongoose_artist, Polarice3
> 规模：**130 个类 / 30 个包 / 20151 行** ｜ 事件处理类 4 ｜ Mixin 5 ｜ 注册/管理类 5

**官方描述**：Adds enhanced royal variations of vanilla mobs with their own characteristics

**依赖**：`neoforge`、`minecraft`、`curios`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.mongoose.royalvariations.client.particles` | 22 | `RVParticleTypes`、`MarkParticle`、`RoyalGlowParticle` |
| `com.mongoose.royalvariations.common.items` | 13 | `RoyalBowItem`、`RoyalTelelocator`、`SpiritualCrownItem` |
| `com.mongoose.royalvariations.client.render.model` | 13 | `RoyalEndermanModel`、`RoyalSkeletonModel`、`RoyalZombieModel` |
| `com.mongoose.royalvariations.client.render` | 12 | `WearRenderer`、`RoyalCreeperRenderer`、`NetRenderer` |
| `com.mongoose.royalvariations.init` | 8 | `ClientInitEvents`、`ClientSideInit`、`RVDataComponents` |
| `com.mongoose.royalvariations.common.entities.projectile` | 7 | `TangleEntity`、`SpectralAnvil`、`RoyalBomb` |
| `com.mongoose.royalvariations.utils` | 7 | `ItemHelper`、`MobUtil`、`EventTask` |
| `com.mongoose.royalvariations.mixin` | 6 | `LivingEntityRendererMixin`、`LivingEntityMixin`、`EntityRendererMixin` |
| `com.mongoose.royalvariations.client.render.animation` | 5 | `RoyalEndermanAnimations`、`RoyalZombieAnimations`、`RoyalCreeperAnimations` |
| `com.mongoose.royalvariations.common.entities.hostile` | 4 | `RoyalSkeleton`、`RoyalCreeper`、`RoyalZombie` |
| `com.mongoose.royalvariations.common.attachments` | 4 | `RVCapHelper`、`RVCapUpdatePacket`、`RVAttachment` |
| `com.mongoose.royalvariations.common.network.server` | 3 | `SInstaLookPacket`、`SRemoveEffectPacket`、`SRepositionPacket` |
| `com.mongoose.royalvariations.common.events` | 2 | `RVEvents`、`TimedEvents` |
| `com.mongoose.royalvariations.common.world` | 2 | `RVLevelRegistry`、`RVMobSpawnBiomeModifier` |
| `com.mongoose.royalvariations.common.entities` | 2 | `RVEntityType`、`IRoyalInterface` |
| `com.mongoose.royalvariations.compat` | 2 | `OtherModCompat`、`ICompatable` |
| `com.mongoose.royalvariations.compat.curios` | 2 | `CuriosIntegration`、`CuriosLoaded` |
| `com.mongoose.royalvariations.common.items.armor` | 2 | `RoyalKnightArmor`、`RVArmorMaterials` |
| `com.mongoose.royalvariations.common.effects` | 2 | `RVBaseEffect`、`RVEffects` |
| `com.mongoose.royalvariations.common.entities.ai` | 2 | `GazeGoal`、`ChosenVictimGoal` |
| `com.mongoose.royalvariations.client.events` | 1 | `ClientEvents` |
| `com.mongoose.royalvariations` | 1 | `RoyalVariations` |
| `com.mongoose.royalvariations.common.entities.neutral` | 1 | `RoyalEnderman` |
| `com.mongoose.royalvariations.config` | 1 | `RVConfig` |
| `com.mongoose.royalvariations.client` | 1 | `ClientProxy` |
| `com.mongoose.royalvariations.client.audio` | 1 | `ItemLoopSound` |
| `com.mongoose.royalvariations.client.render.item` | 1 | `ArmorClientExtension` |
| `com.mongoose.royalvariations.common` | 1 | `CommonProxy` |
| `com.mongoose.royalvariations.common.network.client` | 1 | `CSetDeltaMovement` |
| `com.mongoose.royalvariations.common.items.component` | 1 | `TelelocatorTracker` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RVEvents` | class | EventTask | EventBusSubscriber | 14 | 435 | [RVEvents.java](com/mongoose/royalvariations/common/events/RVEvents.java) |
| `ClientEvents` | class | — | EventBusSubscriber | 3 | 64 | [ClientEvents.java](com/mongoose/royalvariations/client/events/ClientEvents.java) |
| `ClientInitEvents` | class | — | EventBusSubscriber | 4 | 122 | [ClientInitEvents.java](com/mongoose/royalvariations/init/ClientInitEvents.java) |
| `TimedEvents` | class | — | EventBusSubscriber | 4 | 61 | [TimedEvents.java](com/mongoose/royalvariations/common/events/TimedEvents.java) |
| `RoyalVariations` | class | — | Mod | 5 | 142 | [RoyalVariations.java](com/mongoose/royalvariations/RoyalVariations.java) |
| `LivingEntityRendererMixin` | class | — | Mixin | 2 | 29 | [LivingEntityRendererMixin.java](com/mongoose/royalvariations/mixin/LivingEntityRendererMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 11 | 98 | [LivingEntityMixin.java](com/mongoose/royalvariations/mixin/LivingEntityMixin.java) |
| `EntityRendererMixin` | class | — | Mixin | 1 | 21 | [EntityRendererMixin.java](com/mongoose/royalvariations/mixin/EntityRendererMixin.java) |
| `RoyalEnderman` | class | RoyalMonster | — | 51 | 710 | [RoyalEnderman.java](com/mongoose/royalvariations/common/entities/neutral/RoyalEnderman.java) |
| `EntityMixin` | class | — | Mixin | 2 | 36 | [EntityMixin.java](com/mongoose/royalvariations/mixin/EntityMixin.java) |
| `RoyalSkeleton` | class | RoyalMonster | — | 37 | 544 | [RoyalSkeleton.java](com/mongoose/royalvariations/common/entities/hostile/RoyalSkeleton.java) |
| `RoyalCreeper` | class | RoyalMonster | — | 44 | 546 | [RoyalCreeper.java](com/mongoose/royalvariations/common/entities/hostile/RoyalCreeper.java) |
| `RoyalZombie` | class | RoyalMonster | — | 38 | 528 | [RoyalZombie.java](com/mongoose/royalvariations/common/entities/hostile/RoyalZombie.java) |
| `AbstractArrowMixin` | class | — | Mixin | 1 | 16 | [AbstractArrowMixin.java](com/mongoose/royalvariations/mixin/AbstractArrowMixin.java) |
| `RVLevelRegistry` | class | — | — | 3 | 79 | [RVLevelRegistry.java](com/mongoose/royalvariations/common/world/RVLevelRegistry.java) |
| `TangleEntity` | class | Entity | — | 17 | 233 | [TangleEntity.java](com/mongoose/royalvariations/common/entities/projectile/TangleEntity.java) |
| `RoyalEndermanAnimations` | class | — | — | 0 | 2615 | [RoyalEndermanAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalEndermanAnimations.java) |
| `RoyalZombieAnimations` | class | — | — | 0 | 2088 | [RoyalZombieAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalZombieAnimations.java) |
| `RoyalCreeperAnimations` | class | — | — | 0 | 2073 | [RoyalCreeperAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalCreeperAnimations.java) |
| `RoyalSkeletonAnimations` | class | — | — | 0 | 2067 | [RoyalSkeletonAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalSkeletonAnimations.java) |
| `SpectralAnvil` | class | Entity | — | 15 | 244 | [SpectralAnvil.java](com/mongoose/royalvariations/common/entities/projectile/SpectralAnvil.java) |
| `RVConfig` | class | — | — | 1 | 67 | [RVConfig.java](com/mongoose/royalvariations/config/RVConfig.java) |
| `ItemHelper` | class | — | — | 6 | 64 | [ItemHelper.java](com/mongoose/royalvariations/utils/ItemHelper.java) |
| `SInstaLookPacket` | class | CustomPacketPayload | — | 3 | 58 | [SInstaLookPacket.java](com/mongoose/royalvariations/common/network/server/SInstaLookPacket.java) |
| `SRemoveEffectPacket` | class | CustomPacketPayload | — | 3 | 60 | [SRemoveEffectPacket.java](com/mongoose/royalvariations/common/network/server/SRemoveEffectPacket.java) |
| `SRepositionPacket` | class | CustomPacketPayload | — | 3 | 62 | [SRepositionPacket.java](com/mongoose/royalvariations/common/network/server/SRepositionPacket.java) |
| `RoyalBowItem` | class | BowItem | — | 5 | 45 | [RoyalBowItem.java](com/mongoose/royalvariations/common/items/RoyalBowItem.java) |
| `RoyalTelelocator` | class | Item | — | 13 | 243 | [RoyalTelelocator.java](com/mongoose/royalvariations/common/items/RoyalTelelocator.java) |
| `SpiritualCrownItem` | class | Item | — | 6 | 152 | [SpiritualCrownItem.java](com/mongoose/royalvariations/common/items/SpiritualCrownItem.java) |
| `RVCapHelper` | class | — | — | 12 | 62 | [RVCapHelper.java](com/mongoose/royalvariations/common/attachments/RVCapHelper.java) |
| `RVCapUpdatePacket` | class | CustomPacketPayload | — | 3 | 65 | [RVCapUpdatePacket.java](com/mongoose/royalvariations/common/attachments/RVCapUpdatePacket.java) |
| `RoyalBomb` | class | Mob | — | 15 | 157 | [RoyalBomb.java](com/mongoose/royalvariations/common/entities/projectile/RoyalBomb.java) |
| `WearRenderer` | record | ICurioRenderer | — | 7 | 89 | [WearRenderer.java](com/mongoose/royalvariations/client/render/WearRenderer.java) |
| `RoyalCreeperRenderer` | class | MobRenderer | — | 8 | 100 | [RoyalCreeperRenderer.java](com/mongoose/royalvariations/client/render/RoyalCreeperRenderer.java) |
| `MobUtil` | class | — | — | 10 | 117 | [MobUtil.java](com/mongoose/royalvariations/utils/MobUtil.java) |
| `EventTask` | interface | — | — | 0 | 15 | [EventTask.java](com/mongoose/royalvariations/utils/EventTask.java) |
| `RoyalBoneMealItem` | class | BoneMealItem | — | 4 | 132 | [RoyalBoneMealItem.java](com/mongoose/royalvariations/common/items/RoyalBoneMealItem.java) |
| `RoyalBoneItem` | class | Item | — | 1 | 49 | [RoyalBoneItem.java](com/mongoose/royalvariations/common/items/RoyalBoneItem.java) |
| `RoyalEnderPearlItem` | class | Item | — | 2 | 57 | [RoyalEnderPearlItem.java](com/mongoose/royalvariations/common/items/RoyalEnderPearlItem.java) |
| `RVItems` | class | Item | — | 2 | 74 | [RVItems.java](com/mongoose/royalvariations/common/items/RVItems.java) |
| `GloveItem` | class | Item | — | 4 | 54 | [GloveItem.java](com/mongoose/royalvariations/common/items/GloveItem.java) |
| `RoyalStaffItem` | class | SwordItem | — | 3 | 71 | [RoyalStaffItem.java](com/mongoose/royalvariations/common/items/RoyalStaffItem.java) |
| `RoyalArrowItem` | class | ArrowItem | — | 4 | 48 | [RoyalArrowItem.java](com/mongoose/royalvariations/common/items/RoyalArrowItem.java) |
| `RoyalBombItem` | class | Item | — | 3 | 61 | [RoyalBombItem.java](com/mongoose/royalvariations/common/items/RoyalBombItem.java) |
| `RVEntityType` | class | — | — | 1 | 59 | [RVEntityType.java](com/mongoose/royalvariations/common/entities/RVEntityType.java) |
| `NetEntity` | class | TangleEntity | — | 4 | 68 | [NetEntity.java](com/mongoose/royalvariations/common/entities/projectile/NetEntity.java) |
| `OtherModCompat` | class | — | — | 2 | 32 | [OtherModCompat.java](com/mongoose/royalvariations/compat/OtherModCompat.java) |
| `ICompatable` | interface | — | — | 0 | 6 | [ICompatable.java](com/mongoose/royalvariations/compat/ICompatable.java) |
| `CuriosIntegration` | class | ICompatable | — | 3 | 37 | [CuriosIntegration.java](com/mongoose/royalvariations/compat/curios/CuriosIntegration.java) |
| `ClientProxy` | class | RVProxy | — | 2 | 22 | [ClientProxy.java](com/mongoose/royalvariations/client/ClientProxy.java) |
| `ItemLoopSound` | class | AbstractTickableSoundInstance | — | 1 | 32 | [ItemLoopSound.java](com/mongoose/royalvariations/client/audio/ItemLoopSound.java) |
| `NetRenderer` | class | EntityRenderer | — | 2 | 41 | [NetRenderer.java](com/mongoose/royalvariations/client/render/NetRenderer.java) |
| `RoyalBombProjectileRenderer` | class | EntityRenderer | — | 2 | 47 | [RoyalBombProjectileRenderer.java](com/mongoose/royalvariations/client/render/RoyalBombProjectileRenderer.java) |
| `RoyalBombRenderer` | class | MobRenderer | — | 3 | 42 | [RoyalBombRenderer.java](com/mongoose/royalvariations/client/render/RoyalBombRenderer.java) |
| `RoyalEndermanRenderer` | class | MobRenderer | — | 2 | 37 | [RoyalEndermanRenderer.java](com/mongoose/royalvariations/client/render/RoyalEndermanRenderer.java) |
| `RoyalZombieRenderer` | class | MobRenderer | — | 3 | 57 | [RoyalZombieRenderer.java](com/mongoose/royalvariations/client/render/RoyalZombieRenderer.java) |
| `CuriosRenderer` | class | — | — | 3 | 30 | [CuriosRenderer.java](com/mongoose/royalvariations/client/render/CuriosRenderer.java) |
| `RoyalSkeletonRenderer` | class | MobRenderer | — | 1 | 21 | [RoyalSkeletonRenderer.java](com/mongoose/royalvariations/client/render/RoyalSkeletonRenderer.java) |
| `RoyalArrowRenderer` | class | ArrowRenderer | — | 1 | 20 | [RoyalArrowRenderer.java](com/mongoose/royalvariations/client/render/RoyalArrowRenderer.java) |
| `SpectralAnvilRenderer` | class | EntityRenderer | — | 3 | 51 | [SpectralAnvilRenderer.java](com/mongoose/royalvariations/client/render/SpectralAnvilRenderer.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `RVEvents` | 10 | `com.mongoose.royalvariations.common.events` | [RVEvents.java](com/mongoose/royalvariations/common/events/RVEvents.java) |
| `ClientInitEvents` | 4 | `com.mongoose.royalvariations.init` | [ClientInitEvents.java](com/mongoose/royalvariations/init/ClientInitEvents.java) |
| `ClientEvents` | 3 | `com.mongoose.royalvariations.client.events` | [ClientEvents.java](com/mongoose/royalvariations/client/events/ClientEvents.java) |
| `TimedEvents` | 3 | `com.mongoose.royalvariations.common.events` | [TimedEvents.java](com/mongoose/royalvariations/common/events/TimedEvents.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityRendererMixin` | `LivingEntityRenderer` | [LivingEntityRendererMixin.java](com/mongoose/royalvariations/mixin/LivingEntityRendererMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/mongoose/royalvariations/mixin/LivingEntityMixin.java) |
| `EntityRendererMixin` | `EntityRenderer` | [EntityRendererMixin.java](com/mongoose/royalvariations/mixin/EntityRendererMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/mongoose/royalvariations/mixin/EntityMixin.java) |
| `AbstractArrowMixin` | `AbstractArrow` | [AbstractArrowMixin.java](com/mongoose/royalvariations/mixin/AbstractArrowMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `RVConfig` | — | [RVConfig.java](com/mongoose/royalvariations/config/RVConfig.java) |

## 全部类（130）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RVEvents` | class | EventTask | EventBusSubscriber | 14 | 435 | [RVEvents.java](com/mongoose/royalvariations/common/events/RVEvents.java) |
| `ClientEvents` | class | — | EventBusSubscriber | 3 | 64 | [ClientEvents.java](com/mongoose/royalvariations/client/events/ClientEvents.java) |
| `ClientInitEvents` | class | — | EventBusSubscriber | 4 | 122 | [ClientInitEvents.java](com/mongoose/royalvariations/init/ClientInitEvents.java) |
| `TimedEvents` | class | — | EventBusSubscriber | 4 | 61 | [TimedEvents.java](com/mongoose/royalvariations/common/events/TimedEvents.java) |
| `RoyalVariations` | class | — | Mod | 5 | 142 | [RoyalVariations.java](com/mongoose/royalvariations/RoyalVariations.java) |
| `LivingEntityRendererMixin` | class | — | Mixin | 2 | 29 | [LivingEntityRendererMixin.java](com/mongoose/royalvariations/mixin/LivingEntityRendererMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 11 | 98 | [LivingEntityMixin.java](com/mongoose/royalvariations/mixin/LivingEntityMixin.java) |
| `EntityRendererMixin` | class | — | Mixin | 1 | 21 | [EntityRendererMixin.java](com/mongoose/royalvariations/mixin/EntityRendererMixin.java) |
| `RoyalEnderman` | class | RoyalMonster | — | 51 | 710 | [RoyalEnderman.java](com/mongoose/royalvariations/common/entities/neutral/RoyalEnderman.java) |
| `EntityMixin` | class | — | Mixin | 2 | 36 | [EntityMixin.java](com/mongoose/royalvariations/mixin/EntityMixin.java) |
| `RoyalSkeleton` | class | RoyalMonster | — | 37 | 544 | [RoyalSkeleton.java](com/mongoose/royalvariations/common/entities/hostile/RoyalSkeleton.java) |
| `RoyalCreeper` | class | RoyalMonster | — | 44 | 546 | [RoyalCreeper.java](com/mongoose/royalvariations/common/entities/hostile/RoyalCreeper.java) |
| `RoyalZombie` | class | RoyalMonster | — | 38 | 528 | [RoyalZombie.java](com/mongoose/royalvariations/common/entities/hostile/RoyalZombie.java) |
| `AbstractArrowMixin` | class | — | Mixin | 1 | 16 | [AbstractArrowMixin.java](com/mongoose/royalvariations/mixin/AbstractArrowMixin.java) |
| `RVLevelRegistry` | class | — | — | 3 | 79 | [RVLevelRegistry.java](com/mongoose/royalvariations/common/world/RVLevelRegistry.java) |
| `TangleEntity` | class | Entity | — | 17 | 233 | [TangleEntity.java](com/mongoose/royalvariations/common/entities/projectile/TangleEntity.java) |
| `RoyalEndermanAnimations` | class | — | — | 0 | 2615 | [RoyalEndermanAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalEndermanAnimations.java) |
| `RoyalZombieAnimations` | class | — | — | 0 | 2088 | [RoyalZombieAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalZombieAnimations.java) |
| `RoyalCreeperAnimations` | class | — | — | 0 | 2073 | [RoyalCreeperAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalCreeperAnimations.java) |
| `RoyalSkeletonAnimations` | class | — | — | 0 | 2067 | [RoyalSkeletonAnimations.java](com/mongoose/royalvariations/client/render/animation/RoyalSkeletonAnimations.java) |
| `SpectralAnvil` | class | Entity | — | 15 | 244 | [SpectralAnvil.java](com/mongoose/royalvariations/common/entities/projectile/SpectralAnvil.java) |
| `RVConfig` | class | — | — | 1 | 67 | [RVConfig.java](com/mongoose/royalvariations/config/RVConfig.java) |
| `ItemHelper` | class | — | — | 6 | 64 | [ItemHelper.java](com/mongoose/royalvariations/utils/ItemHelper.java) |
| `SInstaLookPacket` | class | CustomPacketPayload | — | 3 | 58 | [SInstaLookPacket.java](com/mongoose/royalvariations/common/network/server/SInstaLookPacket.java) |
| `SRemoveEffectPacket` | class | CustomPacketPayload | — | 3 | 60 | [SRemoveEffectPacket.java](com/mongoose/royalvariations/common/network/server/SRemoveEffectPacket.java) |
| `SRepositionPacket` | class | CustomPacketPayload | — | 3 | 62 | [SRepositionPacket.java](com/mongoose/royalvariations/common/network/server/SRepositionPacket.java) |
| `RoyalBowItem` | class | BowItem | — | 5 | 45 | [RoyalBowItem.java](com/mongoose/royalvariations/common/items/RoyalBowItem.java) |
| `RoyalTelelocator` | class | Item | — | 13 | 243 | [RoyalTelelocator.java](com/mongoose/royalvariations/common/items/RoyalTelelocator.java) |
| `SpiritualCrownItem` | class | Item | — | 6 | 152 | [SpiritualCrownItem.java](com/mongoose/royalvariations/common/items/SpiritualCrownItem.java) |
| `RVCapHelper` | class | — | — | 12 | 62 | [RVCapHelper.java](com/mongoose/royalvariations/common/attachments/RVCapHelper.java) |
| `RVCapUpdatePacket` | class | CustomPacketPayload | — | 3 | 65 | [RVCapUpdatePacket.java](com/mongoose/royalvariations/common/attachments/RVCapUpdatePacket.java) |
| `RoyalBomb` | class | Mob | — | 15 | 157 | [RoyalBomb.java](com/mongoose/royalvariations/common/entities/projectile/RoyalBomb.java) |
| `WearRenderer` | record | ICurioRenderer | — | 7 | 89 | [WearRenderer.java](com/mongoose/royalvariations/client/render/WearRenderer.java) |
| `RoyalCreeperRenderer` | class | MobRenderer | — | 8 | 100 | [RoyalCreeperRenderer.java](com/mongoose/royalvariations/client/render/RoyalCreeperRenderer.java) |
| `MobUtil` | class | — | — | 10 | 117 | [MobUtil.java](com/mongoose/royalvariations/utils/MobUtil.java) |
| `EventTask` | interface | — | — | 0 | 15 | [EventTask.java](com/mongoose/royalvariations/utils/EventTask.java) |
| `RoyalBoneMealItem` | class | BoneMealItem | — | 4 | 132 | [RoyalBoneMealItem.java](com/mongoose/royalvariations/common/items/RoyalBoneMealItem.java) |
| `RoyalBoneItem` | class | Item | — | 1 | 49 | [RoyalBoneItem.java](com/mongoose/royalvariations/common/items/RoyalBoneItem.java) |
| `RoyalEnderPearlItem` | class | Item | — | 2 | 57 | [RoyalEnderPearlItem.java](com/mongoose/royalvariations/common/items/RoyalEnderPearlItem.java) |
| `RVItems` | class | Item | — | 2 | 74 | [RVItems.java](com/mongoose/royalvariations/common/items/RVItems.java) |
| `GloveItem` | class | Item | — | 4 | 54 | [GloveItem.java](com/mongoose/royalvariations/common/items/GloveItem.java) |
| `RoyalStaffItem` | class | SwordItem | — | 3 | 71 | [RoyalStaffItem.java](com/mongoose/royalvariations/common/items/RoyalStaffItem.java) |
| `RoyalArrowItem` | class | ArrowItem | — | 4 | 48 | [RoyalArrowItem.java](com/mongoose/royalvariations/common/items/RoyalArrowItem.java) |
| `RoyalBombItem` | class | Item | — | 3 | 61 | [RoyalBombItem.java](com/mongoose/royalvariations/common/items/RoyalBombItem.java) |
| `RVEntityType` | class | — | — | 1 | 59 | [RVEntityType.java](com/mongoose/royalvariations/common/entities/RVEntityType.java) |
| `NetEntity` | class | TangleEntity | — | 4 | 68 | [NetEntity.java](com/mongoose/royalvariations/common/entities/projectile/NetEntity.java) |
| `OtherModCompat` | class | — | — | 2 | 32 | [OtherModCompat.java](com/mongoose/royalvariations/compat/OtherModCompat.java) |
| `ICompatable` | interface | — | — | 0 | 6 | [ICompatable.java](com/mongoose/royalvariations/compat/ICompatable.java) |
| `CuriosIntegration` | class | ICompatable | — | 3 | 37 | [CuriosIntegration.java](com/mongoose/royalvariations/compat/curios/CuriosIntegration.java) |
| `ClientProxy` | class | RVProxy | — | 2 | 22 | [ClientProxy.java](com/mongoose/royalvariations/client/ClientProxy.java) |
| `ItemLoopSound` | class | AbstractTickableSoundInstance | — | 1 | 32 | [ItemLoopSound.java](com/mongoose/royalvariations/client/audio/ItemLoopSound.java) |
| `NetRenderer` | class | EntityRenderer | — | 2 | 41 | [NetRenderer.java](com/mongoose/royalvariations/client/render/NetRenderer.java) |
| `RoyalBombProjectileRenderer` | class | EntityRenderer | — | 2 | 47 | [RoyalBombProjectileRenderer.java](com/mongoose/royalvariations/client/render/RoyalBombProjectileRenderer.java) |
| `RoyalBombRenderer` | class | MobRenderer | — | 3 | 42 | [RoyalBombRenderer.java](com/mongoose/royalvariations/client/render/RoyalBombRenderer.java) |
| `RoyalEndermanRenderer` | class | MobRenderer | — | 2 | 37 | [RoyalEndermanRenderer.java](com/mongoose/royalvariations/client/render/RoyalEndermanRenderer.java) |
| `RoyalZombieRenderer` | class | MobRenderer | — | 3 | 57 | [RoyalZombieRenderer.java](com/mongoose/royalvariations/client/render/RoyalZombieRenderer.java) |
| `CuriosRenderer` | class | — | — | 3 | 30 | [CuriosRenderer.java](com/mongoose/royalvariations/client/render/CuriosRenderer.java) |
| `RoyalSkeletonRenderer` | class | MobRenderer | — | 1 | 21 | [RoyalSkeletonRenderer.java](com/mongoose/royalvariations/client/render/RoyalSkeletonRenderer.java) |
| `RoyalArrowRenderer` | class | ArrowRenderer | — | 1 | 20 | [RoyalArrowRenderer.java](com/mongoose/royalvariations/client/render/RoyalArrowRenderer.java) |
| `SpectralAnvilRenderer` | class | EntityRenderer | — | 3 | 51 | [SpectralAnvilRenderer.java](com/mongoose/royalvariations/client/render/SpectralAnvilRenderer.java) |
| `ArmorClientExtension` | class | IClientItemExtensions | — | 1 | 44 | [ArmorClientExtension.java](com/mongoose/royalvariations/client/render/item/ArmorClientExtension.java) |
| `RVParticleTypes` | class | — | — | 10 | 92 | [RVParticleTypes.java](com/mongoose/royalvariations/client/particles/RVParticleTypes.java) |
| `MathHelper` | class | — | — | 7 | 32 | [MathHelper.java](com/mongoose/royalvariations/utils/MathHelper.java) |
| `ClientSideInit` | class | SidedInit | — | 2 | 47 | [ClientSideInit.java](com/mongoose/royalvariations/init/ClientSideInit.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 7 | 42 | [MixinPlugin.java](com/mongoose/royalvariations/mixin/MixinPlugin.java) |
| `RVTiers` | enum | Tier | — | 6 | 56 | [RVTiers.java](com/mongoose/royalvariations/common/items/RVTiers.java) |
| `RVAttachment` | class | — | — | 6 | 53 | [RVAttachment.java](com/mongoose/royalvariations/common/attachments/RVAttachment.java) |
| `RoyalMonster` | class | Monster | — | 6 | 81 | [RoyalMonster.java](com/mongoose/royalvariations/common/entities/hostile/RoyalMonster.java) |
| `ThrownRoyalEnderPearl` | class | ThrowableItemProjectile | — | 8 | 171 | [ThrownRoyalEnderPearl.java](com/mongoose/royalvariations/common/entities/projectile/ThrownRoyalEnderPearl.java) |
| `RoyalArrow` | class | Arrow | — | 9 | 148 | [RoyalArrow.java](com/mongoose/royalvariations/common/entities/projectile/RoyalArrow.java) |
| `RoyalBombProjectile` | class | ThrowableProjectile | — | 5 | 103 | [RoyalBombProjectile.java](com/mongoose/royalvariations/common/entities/projectile/RoyalBombProjectile.java) |
| `RVModelLayer` | class | — | — | 5 | 50 | [RVModelLayer.java](com/mongoose/royalvariations/client/render/RVModelLayer.java) |
| `RoyalEndermanModel` | class | HierarchicalModel | — | 4 | 242 | [RoyalEndermanModel.java](com/mongoose/royalvariations/client/render/model/RoyalEndermanModel.java) |
| `RoyalSkeletonModel` | class | HierarchicalModel | — | 4 | 282 | [RoyalSkeletonModel.java](com/mongoose/royalvariations/client/render/model/RoyalSkeletonModel.java) |
| `RoyalZombieModel` | class | HierarchicalModel | — | 4 | 242 | [RoyalZombieModel.java](com/mongoose/royalvariations/client/render/model/RoyalZombieModel.java) |
| `RoyalBombModel` | class | HierarchicalModel | — | 3 | 271 | [RoyalBombModel.java](com/mongoose/royalvariations/client/render/model/RoyalBombModel.java) |
| `MarkParticle` | class | TextureSheetParticle | — | 6 | 78 | [MarkParticle.java](com/mongoose/royalvariations/client/particles/MarkParticle.java) |
| `RoyalGlowParticle` | class | GroundCircleParticle | — | 5 | 81 | [RoyalGlowParticle.java](com/mongoose/royalvariations/client/particles/RoyalGlowParticle.java) |
| `RoyalGlowParticleOption` | class | ParticleOptions | — | 6 | 75 | [RoyalGlowParticleOption.java](com/mongoose/royalvariations/client/particles/RoyalGlowParticleOption.java) |
| `EndSplashParticle` | class | GroundCircleParticle | — | 5 | 74 | [EndSplashParticle.java](com/mongoose/royalvariations/client/particles/EndSplashParticle.java) |
| `DetonationTimerParticle` | class | TextureSheetParticle | — | 6 | 78 | [DetonationTimerParticle.java](com/mongoose/royalvariations/client/particles/DetonationTimerParticle.java) |
| `EndSplashParticleOption` | class | ParticleOptions | — | 5 | 57 | [EndSplashParticleOption.java](com/mongoose/royalvariations/client/particles/EndSplashParticleOption.java) |
| `ServerParticleUtil` | class | — | — | 1 | 39 | [ServerParticleUtil.java](com/mongoose/royalvariations/utils/ServerParticleUtil.java) |
| `RVDataComponents` | class | — | — | 2 | 34 | [RVDataComponents.java](com/mongoose/royalvariations/init/RVDataComponents.java) |
| `CommonProxy` | class | RVProxy | — | 2 | 21 | [CommonProxy.java](com/mongoose/royalvariations/common/CommonProxy.java) |
| `CSetDeltaMovement` | class | CustomPacketPayload | — | 3 | 57 | [CSetDeltaMovement.java](com/mongoose/royalvariations/common/network/client/CSetDeltaMovement.java) |
| `RoyalUpgrade` | class | SmithingTemplateItem | — | 3 | 64 | [RoyalUpgrade.java](com/mongoose/royalvariations/common/items/RoyalUpgrade.java) |
| `TelelocatorTracker` | record | — | — | 2 | 38 | [TelelocatorTracker.java](com/mongoose/royalvariations/common/items/component/TelelocatorTracker.java) |
| `RoyalKnightArmor` | class | ArmorItem | — | 3 | 50 | [RoyalKnightArmor.java](com/mongoose/royalvariations/common/items/armor/RoyalKnightArmor.java) |
| `RVArmorMaterials` | class | — | — | 1 | 46 | [RVArmorMaterials.java](com/mongoose/royalvariations/common/items/armor/RVArmorMaterials.java) |
| `RVBaseEffect` | class | MobEffect | — | 3 | 48 | [RVBaseEffect.java](com/mongoose/royalvariations/common/effects/RVBaseEffect.java) |
| `RVEffects` | class | — | — | 1 | 78 | [RVEffects.java](com/mongoose/royalvariations/common/effects/RVEffects.java) |
| `RVAttachments` | class | — | — | 0 | 14 | [RVAttachments.java](com/mongoose/royalvariations/common/attachments/RVAttachments.java) |
| `RVMobSpawnBiomeModifier` | class | BiomeModifier | — | 3 | 36 | [RVMobSpawnBiomeModifier.java](com/mongoose/royalvariations/common/world/RVMobSpawnBiomeModifier.java) |
| `IRoyalInterface` | interface | — | — | 0 | 28 | [IRoyalInterface.java](com/mongoose/royalvariations/common/entities/IRoyalInterface.java) |
| `GazeGoal` | class | NearestAttackableTargetGoal | — | 3 | 47 | [GazeGoal.java](com/mongoose/royalvariations/common/entities/ai/GazeGoal.java) |
| `ChosenVictimGoal` | class | NearestAttackableTargetGoal | — | 4 | 64 | [ChosenVictimGoal.java](com/mongoose/royalvariations/common/entities/ai/ChosenVictimGoal.java) |
| `CuriosLoaded` | enum | — | — | 1 | 18 | [CuriosLoaded.java](com/mongoose/royalvariations/compat/curios/CuriosLoaded.java) |
| `SpectralAnvilAnimations` | class | — | — | 0 | 83 | [SpectralAnvilAnimations.java](com/mongoose/royalvariations/client/render/animation/SpectralAnvilAnimations.java) |
| `NetModel` | class | HierarchicalModel | — | 3 | 73 | [NetModel.java](com/mongoose/royalvariations/client/render/model/NetModel.java) |
| `RoyalKnightBootsModel` | class | HumanoidModel | — | 2 | 56 | [RoyalKnightBootsModel.java](com/mongoose/royalvariations/client/render/model/RoyalKnightBootsModel.java) |
| `GloveModel` | class | HumanoidModel | — | 3 | 59 | [GloveModel.java](com/mongoose/royalvariations/client/render/model/GloveModel.java) |
| `RoyalKnightLeggingsModel` | class | HumanoidModel | — | 2 | 63 | [RoyalKnightLeggingsModel.java](com/mongoose/royalvariations/client/render/model/RoyalKnightLeggingsModel.java) |
| `RoyalKnightHelmetModel` | class | HumanoidModel | — | 3 | 51 | [RoyalKnightHelmetModel.java](com/mongoose/royalvariations/client/render/model/RoyalKnightHelmetModel.java) |
| `SpectralAnvilModel` | class | HierarchicalModel | — | 3 | 52 | [SpectralAnvilModel.java](com/mongoose/royalvariations/client/render/model/SpectralAnvilModel.java) |
| `RoyalKnightCuirassModel` | class | HumanoidModel | — | 2 | 75 | [RoyalKnightCuirassModel.java](com/mongoose/royalvariations/client/render/model/RoyalKnightCuirassModel.java) |
| `RoyalBombProjectileModel` | class | EntityModel | — | 4 | 55 | [RoyalBombProjectileModel.java](com/mongoose/royalvariations/client/render/model/RoyalBombProjectileModel.java) |
| `RoyalCreeperModel` | class | HierarchicalModel | — | 4 | 194 | [RoyalCreeperModel.java](com/mongoose/royalvariations/client/render/model/RoyalCreeperModel.java) |
| `EndPoofParticle` | class | ExplodeParticle | — | 2 | 42 | [EndPoofParticle.java](com/mongoose/royalvariations/client/particles/EndPoofParticle.java) |
| `RoyalExplosionParticle` | class | TextureSheetParticle | — | 4 | 67 | [RoyalExplosionParticle.java](com/mongoose/royalvariations/client/particles/RoyalExplosionParticle.java) |
| `ShineParticle` | class | TextureSheetParticle | — | 3 | 48 | [ShineParticle.java](com/mongoose/royalvariations/client/particles/ShineParticle.java) |
| `HolyParticle` | class | TextureSheetParticle | — | 4 | 57 | [HolyParticle.java](com/mongoose/royalvariations/client/particles/HolyParticle.java) |
| `RATraceParticle` | class | TextureSheetParticle | — | 4 | 55 | [RATraceParticle.java](com/mongoose/royalvariations/client/particles/RATraceParticle.java) |
| `GroundCircleParticle` | class | TextureSheetParticle | — | 3 | 70 | [GroundCircleParticle.java](com/mongoose/royalvariations/client/particles/GroundCircleParticle.java) |
| `SparkleParticle` | class | TextureSheetParticle | — | 4 | 55 | [SparkleParticle.java](com/mongoose/royalvariations/client/particles/SparkleParticle.java) |
| `RoyalExplosionParticleOption` | class | ParticleOptions | — | 4 | 43 | [RoyalExplosionParticleOption.java](com/mongoose/royalvariations/client/particles/RoyalExplosionParticleOption.java) |
| `RoyalSpiritParticle` | class | TextureSheetParticle | — | 4 | 55 | [RoyalSpiritParticle.java](com/mongoose/royalvariations/client/particles/RoyalSpiritParticle.java) |
| `ScrapsNetParticle` | class | TextureSheetParticle | — | 3 | 55 | [ScrapsNetParticle.java](com/mongoose/royalvariations/client/particles/ScrapsNetParticle.java) |
| `MarkParticleOption` | class | ParticleOptions | — | 2 | 32 | [MarkParticleOption.java](com/mongoose/royalvariations/client/particles/MarkParticleOption.java) |
| `DazedStarParticle` | class | TextureSheetParticle | — | 4 | 59 | [DazedStarParticle.java](com/mongoose/royalvariations/client/particles/DazedStarParticle.java) |
| `DTParticleOption` | class | ParticleOptions | — | 2 | 32 | [DTParticleOption.java](com/mongoose/royalvariations/client/particles/DTParticleOption.java) |
| `AuraOfProtectionParticle` | class | TextureSheetParticle | — | 3 | 45 | [AuraOfProtectionParticle.java](com/mongoose/royalvariations/client/particles/AuraOfProtectionParticle.java) |
| `GoldenTrailParticle` | class | TextureSheetParticle | — | 4 | 55 | [GoldenTrailParticle.java](com/mongoose/royalvariations/client/particles/GoldenTrailParticle.java) |
| `CuriosFinder` | class | — | — | 4 | 46 | [CuriosFinder.java](com/mongoose/royalvariations/utils/CuriosFinder.java) |
| `CustomExplosion` | class | — | — | 4 | 116 | [CustomExplosion.java](com/mongoose/royalvariations/utils/CustomExplosion.java) |
| `SidedInit` | class | — | — | 1 | 9 | [SidedInit.java](com/mongoose/royalvariations/init/SidedInit.java) |
| `RVProxy` | interface | — | — | 0 | 14 | [RVProxy.java](com/mongoose/royalvariations/init/RVProxy.java) |
| `RVSounds` | class | — | — | 1 | 26 | [RVSounds.java](com/mongoose/royalvariations/init/RVSounds.java) |
| `RVCreativeTab` | class | — | — | 0 | 47 | [RVCreativeTab.java](com/mongoose/royalvariations/init/RVCreativeTab.java) |
| `RVTags` | class | — | — | 2 | 26 | [RVTags.java](com/mongoose/royalvariations/init/RVTags.java) |
