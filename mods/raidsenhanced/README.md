# Raids: Enhanced（`raidsenhanced`）

> jar：`[更多袭击掠夺者]raidsenhanced.jar` ｜ 版本：`1.0.2` ｜ 作者：FINDERFEED
> 规模：**65 个类 / 27 个包 / 7490 行** ｜ 事件处理类 4 ｜ Mixin 4 ｜ 注册/管理类 3

**依赖**：`minecraft`、`fdlib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.finderfeed.raids_enhanced.init` | 8 | `REEntities`、`REConfigs`、`REParticles` |
| `com.finderfeed.raids_enhanced` | 7 | `REClientEvents`、`RaidsEnhanced`、`REClientMixinHandler` |
| `com.finderfeed.raids_enhanced.mixin` | 4 | `ClientPacketListenerMixin`、`LocalPlayerMixin`、`RaidMixin` |
| `com.finderfeed.raids_enhanced.content.entities.raid_blimp.cannons` | 4 | `RaidBlimpCannon`、`RaidBlimpCannonProjectile`、`RaidBlimpCannonsController` |
| `com.finderfeed.raids_enhanced.content.entities.player_blimp` | 3 | `PlayerBlimpEntity`、`PlayerBlimpRotatingPacket`、`FDVehicle` |
| `com.finderfeed.raids_enhanced.content.entities.engineer` | 3 | `ZapperIllager`、`ZapperIllagerRenderer`、`ElectromancerMoveControl` |
| `com.finderfeed.raids_enhanced.content.items` | 3 | `PlayerBlimpItem`、`ItemWithDescription`、`REDebugStick` |
| `com.finderfeed.raids_enhanced.content.entities.raid_blimp.raid_airship_parts` | 3 | `RaidBlimpPartRenderer`、`RaidBlimpPart`、`RaidBlimpPartDefinition` |
| `com.finderfeed.raids_enhanced.content.entities.raid_drill` | 2 | `RaidDrill`、`RaidDrillRenderer` |
| `com.finderfeed.raids_enhanced.content.entities.raid_blimp` | 2 | `RaidBlimp`、`RaiderBomb` |
| `com.finderfeed.raids_enhanced.content.items.handcannon` | 2 | `HandCannonClientItemExtensions`、`HandCannon` |
| `com.finderfeed.raids_enhanced.content.entities.falling_block` | 2 | `REFallingBlock`、`REFallingBlockRenderer` |
| `com.finderfeed.raids_enhanced.content.items.electromancer_staff` | 2 | `ZapperStaffCastEntity`、`ZapperStaff` |
| `com.finderfeed.raids_enhanced.content.entities.ball_lightning` | 2 | `BallLightningEntity`、`BallLightningRenderer` |
| `com.finderfeed.raids_enhanced.content.entities.vertical_lightning_strike` | 2 | `VerticalLightningStrikeAttackRenderer`、`VerticalLightningStrikeAttack` |
| `com.finderfeed.raids_enhanced.content.entities.raid_blimp.navigation` | 2 | `RaidBlimpPathNavigation`、`RaidBlimpMoveControl` |
| `com.finderfeed.raids_enhanced.content.particles` | 2 | `SimpleTexturedParticleOptions`、`AnimatedSpriteParticle` |
| `com.finderfeed.raids_enhanced.content.particles.lightning_strike` | 2 | `LightningStrikeParticleOptions`、`LightningStrikeParticle` |
| `com.finderfeed.raids_enhanced.content.particles.slash_particle` | 2 | `SlashParticleOptions`、`SlashParticle` |
| `com.finderfeed.raids_enhanced.content.entities.golem_of_last_resort` | 1 | `GolemOfLastResort` |
| `com.finderfeed.raids_enhanced.content.packets` | 1 | `REPosEventPacket` |
| `com.finderfeed.raids_enhanced.content.entities` | 1 | `FDRaider` |
| `com.finderfeed.raids_enhanced.content.util` | 1 | `HorizontalCircleRandomDirections` |
| `com.finderfeed.raids_enhanced.content.particles.vertical_lightning` | 1 | `VerticalLightningParticle` |
| `com.finderfeed.raids_enhanced.content.particles.ball_lightning_explosion` | 1 | `BallLightningExplosionParticle` |
| `com.finderfeed.raids_enhanced.content.particles.lightning_explosion` | 1 | `LightningExplosionParticle` |
| `com.finderfeed.raids_enhanced.content.particles.explosion_particle` | 1 | `RExplosionParticle` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `REClientEvents` | class | — | EventBusSubscriber | 3 | 199 | [REClientEvents.java](com/finderfeed/raids_enhanced/REClientEvents.java) |
| `PlayerBlimpEntity` | class | FDVehicle | — | 36 | 516 | [PlayerBlimpEntity.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/PlayerBlimpEntity.java) |
| `REEntities` | class | — | EventBusSubscriber | 1 | 112 | [REEntities.java](com/finderfeed/raids_enhanced/init/REEntities.java) |
| `RaidDrill` | class | FDRaider | — | 29 | 418 | [RaidDrill.java](com/finderfeed/raids_enhanced/content/entities/raid_drill/RaidDrill.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 28 | [ClientPacketListenerMixin.java](com/finderfeed/raids_enhanced/mixin/ClientPacketListenerMixin.java) |
| `RaidsEnhanced` | class | — | Mod | 1 | 38 | [RaidsEnhanced.java](com/finderfeed/raids_enhanced/RaidsEnhanced.java) |
| `ZapperIllager` | class | FDRaider | — | 59 | 882 | [ZapperIllager.java](com/finderfeed/raids_enhanced/content/entities/engineer/ZapperIllager.java) |
| `GolemOfLastResort` | class | FDRaider | — | 36 | 766 | [GolemOfLastResort.java](com/finderfeed/raids_enhanced/content/entities/golem_of_last_resort/GolemOfLastResort.java) |
| `RaidBlimp` | class | FDRaider | — | 42 | 592 | [RaidBlimp.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/RaidBlimp.java) |
| `REClientMixinHandler` | class | — | — | 1 | 23 | [REClientMixinHandler.java](com/finderfeed/raids_enhanced/REClientMixinHandler.java) |
| `REPosEventPacket` | class | FDPacket | RegisterFDPacket | 3 | 42 | [REPosEventPacket.java](com/finderfeed/raids_enhanced/content/packets/REPosEventPacket.java) |
| `LocalPlayerMixin` | class | — | Mixin | 1 | 25 | [LocalPlayerMixin.java](com/finderfeed/raids_enhanced/mixin/LocalPlayerMixin.java) |
| `RaidMixin` | class | — | Mixin | 1 | 27 | [RaidMixin.java](com/finderfeed/raids_enhanced/mixin/RaidMixin.java) |
| `PlayerMixin` | class | — | Mixin | 2 | 30 | [PlayerMixin.java](com/finderfeed/raids_enhanced/mixin/PlayerMixin.java) |
| `REMixinHandler` | class | — | — | 1 | 51 | [REMixinHandler.java](com/finderfeed/raids_enhanced/REMixinHandler.java) |
| `REConfig` | class | ReflectiveJsonConfig | — | 1 | 44 | [REConfig.java](com/finderfeed/raids_enhanced/REConfig.java) |
| `REClientUtil` | class | — | — | 6 | 159 | [REClientUtil.java](com/finderfeed/raids_enhanced/REClientUtil.java) |
| `HandCannonClientItemExtensions` | class | IClientItemExtensions | — | 1 | 16 | [HandCannonClientItemExtensions.java](com/finderfeed/raids_enhanced/content/items/handcannon/HandCannonClientItemExtensions.java) |
| `RaidBlimpCannon` | class | — | — | 17 | 258 | [RaidBlimpCannon.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannon.java) |
| `REFallingBlock` | class | FDProjectile | — | 13 | 116 | [REFallingBlock.java](com/finderfeed/raids_enhanced/content/entities/falling_block/REFallingBlock.java) |
| `REFallingBlockRenderer` | class | EntityRenderer | — | 2 | 35 | [REFallingBlockRenderer.java](com/finderfeed/raids_enhanced/content/entities/falling_block/REFallingBlockRenderer.java) |
| `REConfigs` | class | — | — | 0 | 13 | [REConfigs.java](com/finderfeed/raids_enhanced/init/REConfigs.java) |
| `ZapperStaffCastEntity` | class | Entity | — | 9 | 116 | [ZapperStaffCastEntity.java](com/finderfeed/raids_enhanced/content/items/electromancer_staff/ZapperStaffCastEntity.java) |
| `PlayerBlimpRotatingPacket` | class | FDPacket | RegisterFDPacket | 3 | 40 | [PlayerBlimpRotatingPacket.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/PlayerBlimpRotatingPacket.java) |
| `BallLightningEntity` | class | Entity | — | 8 | 162 | [BallLightningEntity.java](com/finderfeed/raids_enhanced/content/entities/ball_lightning/BallLightningEntity.java) |
| `REUtil` | class | — | — | 5 | 39 | [REUtil.java](com/finderfeed/raids_enhanced/REUtil.java) |
| `PlayerBlimpItem` | class | ItemWithDescription | — | 1 | 52 | [PlayerBlimpItem.java](com/finderfeed/raids_enhanced/content/items/PlayerBlimpItem.java) |
| `ItemWithDescription` | class | Item | — | 1 | 24 | [ItemWithDescription.java](com/finderfeed/raids_enhanced/content/items/ItemWithDescription.java) |
| `RaidBlimpPartRenderer` | class | EntityRenderer | — | 3 | 73 | [RaidBlimpPartRenderer.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/raid_airship_parts/RaidBlimpPartRenderer.java) |
| `RaidBlimpCannonProjectile` | class | FDProjectile | — | 10 | 134 | [RaidBlimpCannonProjectile.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannonProjectile.java) |
| `RaidBlimpCannonsController` | class | — | — | 11 | 140 | [RaidBlimpCannonsController.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannonsController.java) |
| `VerticalLightningStrikeAttackRenderer` | class | EntityRenderer | — | 2 | 39 | [VerticalLightningStrikeAttackRenderer.java](com/finderfeed/raids_enhanced/content/entities/vertical_lightning_strike/VerticalLightningStrikeAttackRenderer.java) |
| `ZapperIllagerRenderer` | class | FDFreeEntityRenderer<ZapperIllager> | — | 1 | 102 | [ZapperIllagerRenderer.java](com/finderfeed/raids_enhanced/content/entities/engineer/ZapperIllagerRenderer.java) |
| `RaidDrillRenderer` | class | FDFreeEntityRenderer<RaidDrill> | — | 2 | 91 | [RaidDrillRenderer.java](com/finderfeed/raids_enhanced/content/entities/raid_drill/RaidDrillRenderer.java) |
| `BallLightningRenderer` | class | EntityRenderer | — | 2 | 48 | [BallLightningRenderer.java](com/finderfeed/raids_enhanced/content/entities/ball_lightning/BallLightningRenderer.java) |
| `REParticles` | class | — | — | 12 | 83 | [REParticles.java](com/finderfeed/raids_enhanced/init/REParticles.java) |
| `REItems` | class | — | — | 0 | 42 | [REItems.java](com/finderfeed/raids_enhanced/init/REItems.java) |
| `FDRaider` | class | Raider | — | 5 | 43 | [FDRaider.java](com/finderfeed/raids_enhanced/content/entities/FDRaider.java) |
| `RaiderBomb` | class | FDEntity | — | 8 | 174 | [RaiderBomb.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/RaiderBomb.java) |
| `RaidBlimpPart` | class | Entity | — | 8 | 134 | [RaidBlimpPart.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/raid_airship_parts/RaidBlimpPart.java) |
| `RaidBlimpPathNavigation` | class | FlyingPathNavigation | — | 5 | 109 | [RaidBlimpPathNavigation.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/navigation/RaidBlimpPathNavigation.java) |
| `FDVehicle` | class | VehicleEntity | — | 5 | 41 | [FDVehicle.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/FDVehicle.java) |
| `VerticalLightningStrikeAttack` | class | Entity | — | 6 | 130 | [VerticalLightningStrikeAttack.java](com/finderfeed/raids_enhanced/content/entities/vertical_lightning_strike/VerticalLightningStrikeAttack.java) |
| `REDebugStick` | class | Item | — | 1 | 34 | [REDebugStick.java](com/finderfeed/raids_enhanced/content/items/REDebugStick.java) |
| `HandCannon` | class | ItemWithDescription | — | 1 | 56 | [HandCannon.java](com/finderfeed/raids_enhanced/content/items/handcannon/HandCannon.java) |
| `ZapperStaff` | class | ItemWithDescription | — | 4 | 180 | [ZapperStaff.java](com/finderfeed/raids_enhanced/content/items/electromancer_staff/ZapperStaff.java) |
| `HorizontalCircleRandomDirections` | class | Iterable<Vec3> | — | 3 | 44 | [HorizontalCircleRandomDirections.java](com/finderfeed/raids_enhanced/content/util/HorizontalCircleRandomDirections.java) |
| `RaidBlimpPartDefinition` | class | — | — | 0 | 15 | [RaidBlimpPartDefinition.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/raid_airship_parts/RaidBlimpPartDefinition.java) |
| `RaidBlimpCannonBonesController` | class | BoneTransformationController<RaidBli | — | 2 | 73 | [RaidBlimpCannonBonesController.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannonBonesController.java) |
| `RaidBlimpMoveControl` | class | FlyingMoveControl | — | 1 | 63 | [RaidBlimpMoveControl.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/navigation/RaidBlimpMoveControl.java) |
| `ElectromancerMoveControl` | class | MoveControl | — | 1 | 16 | [ElectromancerMoveControl.java](com/finderfeed/raids_enhanced/content/entities/engineer/ElectromancerMoveControl.java) |
| `SimpleTexturedParticleOptions` | class | ParticleOptions | — | 3 | 44 | [SimpleTexturedParticleOptions.java](com/finderfeed/raids_enhanced/content/particles/SimpleTexturedParticleOptions.java) |
| `AnimatedSpriteParticle` | class | TextureSheetParticle | — | 1 | 31 | [AnimatedSpriteParticle.java](com/finderfeed/raids_enhanced/content/particles/AnimatedSpriteParticle.java) |
| `LightningStrikeParticleOptions` | class | SimpleTexturedParticleOptions | — | 2 | 46 | [LightningStrikeParticleOptions.java](com/finderfeed/raids_enhanced/content/particles/lightning_strike/LightningStrikeParticleOptions.java) |
| `LightningStrikeParticle` | class | AnimatedSpriteParticle | — | 4 | 97 | [LightningStrikeParticle.java](com/finderfeed/raids_enhanced/content/particles/lightning_strike/LightningStrikeParticle.java) |
| `VerticalLightningParticle` | class | AnimatedSpriteParticle | — | 4 | 54 | [VerticalLightningParticle.java](com/finderfeed/raids_enhanced/content/particles/vertical_lightning/VerticalLightningParticle.java) |
| `BallLightningExplosionParticle` | class | AnimatedSpriteParticle | — | 3 | 49 | [BallLightningExplosionParticle.java](com/finderfeed/raids_enhanced/content/particles/ball_lightning_explosion/BallLightningExplosionParticle.java) |
| `LightningExplosionParticle` | class | AnimatedSpriteParticle | — | 2 | 45 | [LightningExplosionParticle.java](com/finderfeed/raids_enhanced/content/particles/lightning_explosion/LightningExplosionParticle.java) |
| `SlashParticleOptions` | class | ParticleOptions | — | 3 | 65 | [SlashParticleOptions.java](com/finderfeed/raids_enhanced/content/particles/slash_particle/SlashParticleOptions.java) |
| `SlashParticle` | class | TextureSheetParticle | — | 4 | 94 | [SlashParticle.java](com/finderfeed/raids_enhanced/content/particles/slash_particle/SlashParticle.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `REClientEvents` | 3 | `com.finderfeed.raids_enhanced` | [REClientEvents.java](com/finderfeed/raids_enhanced/REClientEvents.java) |
| `RaidDrill` | 2 | `com.finderfeed.raids_enhanced.content.entities.raid_drill` | [RaidDrill.java](com/finderfeed/raids_enhanced/content/entities/raid_drill/RaidDrill.java) |
| `PlayerBlimpEntity` | 1 | `com.finderfeed.raids_enhanced.content.entities.player_blimp` | [PlayerBlimpEntity.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/PlayerBlimpEntity.java) |
| `REEntities` | 1 | `com.finderfeed.raids_enhanced.init` | [REEntities.java](com/finderfeed/raids_enhanced/init/REEntities.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](com/finderfeed/raids_enhanced/mixin/ClientPacketListenerMixin.java) |
| `LocalPlayerMixin` | `LocalPlayer` | [LocalPlayerMixin.java](com/finderfeed/raids_enhanced/mixin/LocalPlayerMixin.java) |
| `RaidMixin` | `Raid` | [RaidMixin.java](com/finderfeed/raids_enhanced/mixin/RaidMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](com/finderfeed/raids_enhanced/mixin/PlayerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `REConfig` | — | [REConfig.java](com/finderfeed/raids_enhanced/REConfig.java) |
| `REConfigs` | — | [REConfigs.java](com/finderfeed/raids_enhanced/init/REConfigs.java) |

## 全部类（65）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `REClientEvents` | class | — | EventBusSubscriber | 3 | 199 | [REClientEvents.java](com/finderfeed/raids_enhanced/REClientEvents.java) |
| `PlayerBlimpEntity` | class | FDVehicle | — | 36 | 516 | [PlayerBlimpEntity.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/PlayerBlimpEntity.java) |
| `REEntities` | class | — | EventBusSubscriber | 1 | 112 | [REEntities.java](com/finderfeed/raids_enhanced/init/REEntities.java) |
| `RaidDrill` | class | FDRaider | — | 29 | 418 | [RaidDrill.java](com/finderfeed/raids_enhanced/content/entities/raid_drill/RaidDrill.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 28 | [ClientPacketListenerMixin.java](com/finderfeed/raids_enhanced/mixin/ClientPacketListenerMixin.java) |
| `RaidsEnhanced` | class | — | Mod | 1 | 38 | [RaidsEnhanced.java](com/finderfeed/raids_enhanced/RaidsEnhanced.java) |
| `ZapperIllager` | class | FDRaider | — | 59 | 882 | [ZapperIllager.java](com/finderfeed/raids_enhanced/content/entities/engineer/ZapperIllager.java) |
| `GolemOfLastResort` | class | FDRaider | — | 36 | 766 | [GolemOfLastResort.java](com/finderfeed/raids_enhanced/content/entities/golem_of_last_resort/GolemOfLastResort.java) |
| `RaidBlimp` | class | FDRaider | — | 42 | 592 | [RaidBlimp.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/RaidBlimp.java) |
| `REClientMixinHandler` | class | — | — | 1 | 23 | [REClientMixinHandler.java](com/finderfeed/raids_enhanced/REClientMixinHandler.java) |
| `REPosEventPacket` | class | FDPacket | RegisterFDPacket | 3 | 42 | [REPosEventPacket.java](com/finderfeed/raids_enhanced/content/packets/REPosEventPacket.java) |
| `LocalPlayerMixin` | class | — | Mixin | 1 | 25 | [LocalPlayerMixin.java](com/finderfeed/raids_enhanced/mixin/LocalPlayerMixin.java) |
| `RaidMixin` | class | — | Mixin | 1 | 27 | [RaidMixin.java](com/finderfeed/raids_enhanced/mixin/RaidMixin.java) |
| `PlayerMixin` | class | — | Mixin | 2 | 30 | [PlayerMixin.java](com/finderfeed/raids_enhanced/mixin/PlayerMixin.java) |
| `REMixinHandler` | class | — | — | 1 | 51 | [REMixinHandler.java](com/finderfeed/raids_enhanced/REMixinHandler.java) |
| `REConfig` | class | ReflectiveJsonConfig | — | 1 | 44 | [REConfig.java](com/finderfeed/raids_enhanced/REConfig.java) |
| `REClientUtil` | class | — | — | 6 | 159 | [REClientUtil.java](com/finderfeed/raids_enhanced/REClientUtil.java) |
| `HandCannonClientItemExtensions` | class | IClientItemExtensions | — | 1 | 16 | [HandCannonClientItemExtensions.java](com/finderfeed/raids_enhanced/content/items/handcannon/HandCannonClientItemExtensions.java) |
| `RaidBlimpCannon` | class | — | — | 17 | 258 | [RaidBlimpCannon.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannon.java) |
| `REFallingBlock` | class | FDProjectile | — | 13 | 116 | [REFallingBlock.java](com/finderfeed/raids_enhanced/content/entities/falling_block/REFallingBlock.java) |
| `REFallingBlockRenderer` | class | EntityRenderer | — | 2 | 35 | [REFallingBlockRenderer.java](com/finderfeed/raids_enhanced/content/entities/falling_block/REFallingBlockRenderer.java) |
| `REConfigs` | class | — | — | 0 | 13 | [REConfigs.java](com/finderfeed/raids_enhanced/init/REConfigs.java) |
| `ZapperStaffCastEntity` | class | Entity | — | 9 | 116 | [ZapperStaffCastEntity.java](com/finderfeed/raids_enhanced/content/items/electromancer_staff/ZapperStaffCastEntity.java) |
| `PlayerBlimpRotatingPacket` | class | FDPacket | RegisterFDPacket | 3 | 40 | [PlayerBlimpRotatingPacket.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/PlayerBlimpRotatingPacket.java) |
| `BallLightningEntity` | class | Entity | — | 8 | 162 | [BallLightningEntity.java](com/finderfeed/raids_enhanced/content/entities/ball_lightning/BallLightningEntity.java) |
| `REUtil` | class | — | — | 5 | 39 | [REUtil.java](com/finderfeed/raids_enhanced/REUtil.java) |
| `PlayerBlimpItem` | class | ItemWithDescription | — | 1 | 52 | [PlayerBlimpItem.java](com/finderfeed/raids_enhanced/content/items/PlayerBlimpItem.java) |
| `ItemWithDescription` | class | Item | — | 1 | 24 | [ItemWithDescription.java](com/finderfeed/raids_enhanced/content/items/ItemWithDescription.java) |
| `RaidBlimpPartRenderer` | class | EntityRenderer | — | 3 | 73 | [RaidBlimpPartRenderer.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/raid_airship_parts/RaidBlimpPartRenderer.java) |
| `RaidBlimpCannonProjectile` | class | FDProjectile | — | 10 | 134 | [RaidBlimpCannonProjectile.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannonProjectile.java) |
| `RaidBlimpCannonsController` | class | — | — | 11 | 140 | [RaidBlimpCannonsController.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannonsController.java) |
| `VerticalLightningStrikeAttackRenderer` | class | EntityRenderer | — | 2 | 39 | [VerticalLightningStrikeAttackRenderer.java](com/finderfeed/raids_enhanced/content/entities/vertical_lightning_strike/VerticalLightningStrikeAttackRenderer.java) |
| `ZapperIllagerRenderer` | class | FDFreeEntityRenderer<ZapperIllager> | — | 1 | 102 | [ZapperIllagerRenderer.java](com/finderfeed/raids_enhanced/content/entities/engineer/ZapperIllagerRenderer.java) |
| `RaidDrillRenderer` | class | FDFreeEntityRenderer<RaidDrill> | — | 2 | 91 | [RaidDrillRenderer.java](com/finderfeed/raids_enhanced/content/entities/raid_drill/RaidDrillRenderer.java) |
| `BallLightningRenderer` | class | EntityRenderer | — | 2 | 48 | [BallLightningRenderer.java](com/finderfeed/raids_enhanced/content/entities/ball_lightning/BallLightningRenderer.java) |
| `REParticles` | class | — | — | 12 | 83 | [REParticles.java](com/finderfeed/raids_enhanced/init/REParticles.java) |
| `REItems` | class | — | — | 0 | 42 | [REItems.java](com/finderfeed/raids_enhanced/init/REItems.java) |
| `FDRaider` | class | Raider | — | 5 | 43 | [FDRaider.java](com/finderfeed/raids_enhanced/content/entities/FDRaider.java) |
| `RaiderBomb` | class | FDEntity | — | 8 | 174 | [RaiderBomb.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/RaiderBomb.java) |
| `RaidBlimpPart` | class | Entity | — | 8 | 134 | [RaidBlimpPart.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/raid_airship_parts/RaidBlimpPart.java) |
| `RaidBlimpPathNavigation` | class | FlyingPathNavigation | — | 5 | 109 | [RaidBlimpPathNavigation.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/navigation/RaidBlimpPathNavigation.java) |
| `FDVehicle` | class | VehicleEntity | — | 5 | 41 | [FDVehicle.java](com/finderfeed/raids_enhanced/content/entities/player_blimp/FDVehicle.java) |
| `VerticalLightningStrikeAttack` | class | Entity | — | 6 | 130 | [VerticalLightningStrikeAttack.java](com/finderfeed/raids_enhanced/content/entities/vertical_lightning_strike/VerticalLightningStrikeAttack.java) |
| `REDebugStick` | class | Item | — | 1 | 34 | [REDebugStick.java](com/finderfeed/raids_enhanced/content/items/REDebugStick.java) |
| `HandCannon` | class | ItemWithDescription | — | 1 | 56 | [HandCannon.java](com/finderfeed/raids_enhanced/content/items/handcannon/HandCannon.java) |
| `ZapperStaff` | class | ItemWithDescription | — | 4 | 180 | [ZapperStaff.java](com/finderfeed/raids_enhanced/content/items/electromancer_staff/ZapperStaff.java) |
| `HorizontalCircleRandomDirections` | class | Iterable<Vec3> | — | 3 | 44 | [HorizontalCircleRandomDirections.java](com/finderfeed/raids_enhanced/content/util/HorizontalCircleRandomDirections.java) |
| `RaidBlimpPartDefinition` | class | — | — | 0 | 15 | [RaidBlimpPartDefinition.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/raid_airship_parts/RaidBlimpPartDefinition.java) |
| `RaidBlimpCannonBonesController` | class | BoneTransformationController<RaidBli | — | 2 | 73 | [RaidBlimpCannonBonesController.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/cannons/RaidBlimpCannonBonesController.java) |
| `RaidBlimpMoveControl` | class | FlyingMoveControl | — | 1 | 63 | [RaidBlimpMoveControl.java](com/finderfeed/raids_enhanced/content/entities/raid_blimp/navigation/RaidBlimpMoveControl.java) |
| `ElectromancerMoveControl` | class | MoveControl | — | 1 | 16 | [ElectromancerMoveControl.java](com/finderfeed/raids_enhanced/content/entities/engineer/ElectromancerMoveControl.java) |
| `SimpleTexturedParticleOptions` | class | ParticleOptions | — | 3 | 44 | [SimpleTexturedParticleOptions.java](com/finderfeed/raids_enhanced/content/particles/SimpleTexturedParticleOptions.java) |
| `AnimatedSpriteParticle` | class | TextureSheetParticle | — | 1 | 31 | [AnimatedSpriteParticle.java](com/finderfeed/raids_enhanced/content/particles/AnimatedSpriteParticle.java) |
| `LightningStrikeParticleOptions` | class | SimpleTexturedParticleOptions | — | 2 | 46 | [LightningStrikeParticleOptions.java](com/finderfeed/raids_enhanced/content/particles/lightning_strike/LightningStrikeParticleOptions.java) |
| `LightningStrikeParticle` | class | AnimatedSpriteParticle | — | 4 | 97 | [LightningStrikeParticle.java](com/finderfeed/raids_enhanced/content/particles/lightning_strike/LightningStrikeParticle.java) |
| `VerticalLightningParticle` | class | AnimatedSpriteParticle | — | 4 | 54 | [VerticalLightningParticle.java](com/finderfeed/raids_enhanced/content/particles/vertical_lightning/VerticalLightningParticle.java) |
| `BallLightningExplosionParticle` | class | AnimatedSpriteParticle | — | 3 | 49 | [BallLightningExplosionParticle.java](com/finderfeed/raids_enhanced/content/particles/ball_lightning_explosion/BallLightningExplosionParticle.java) |
| `LightningExplosionParticle` | class | AnimatedSpriteParticle | — | 2 | 45 | [LightningExplosionParticle.java](com/finderfeed/raids_enhanced/content/particles/lightning_explosion/LightningExplosionParticle.java) |
| `SlashParticleOptions` | class | ParticleOptions | — | 3 | 65 | [SlashParticleOptions.java](com/finderfeed/raids_enhanced/content/particles/slash_particle/SlashParticleOptions.java) |
| `SlashParticle` | class | TextureSheetParticle | — | 4 | 94 | [SlashParticle.java](com/finderfeed/raids_enhanced/content/particles/slash_particle/SlashParticle.java) |
| `RExplosionParticle` | class | AnimatedSpriteParticle | — | 3 | 49 | [RExplosionParticle.java](com/finderfeed/raids_enhanced/content/particles/explosion_particle/RExplosionParticle.java) |
| `REModels` | class | — | — | 0 | 33 | [REModels.java](com/finderfeed/raids_enhanced/init/REModels.java) |
| `RESounds` | class | — | — | 0 | 57 | [RESounds.java](com/finderfeed/raids_enhanced/init/RESounds.java) |
| `RECreativeTabs` | class | — | — | 0 | 31 | [RECreativeTabs.java](com/finderfeed/raids_enhanced/init/RECreativeTabs.java) |
| `REAnimations` | class | — | — | 0 | 108 | [REAnimations.java](com/finderfeed/raids_enhanced/init/REAnimations.java) |
