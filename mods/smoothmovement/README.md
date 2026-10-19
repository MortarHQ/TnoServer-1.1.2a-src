# smoothmovement mod（`smoothmovement`）

> jar：`[服务端-优化]smoothmovement.jar` ｜ 版本：`2.7` ｜ 作者：Someaddon
> 规模：**26 个类 / 9 个包 / 1364 行** ｜ 事件处理类 1 ｜ Mixin 19 ｜ 注册/管理类 0

**依赖**：`minecraft`、`cupboard`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.smoothmovement.mixin.time` | 5 | `ClientLevelTimeDeltaMixin`、`GametimePacketListenerMixin`、`NeoforgeTimePacketMixin` |
| `com.smoothmovement.mixin.nonliving` | 5 | `ClientMinecartMixin`、`ServerFallingBlockMixin`、`ServerExperienceOrbMixin` |
| `com.smoothmovement.mixin.livingentity` | 4 | `ClientEntityMovementMixin`、`ServerLivingEntityMixin`、`ServerLookControlMixin` |
| `com.smoothmovement` | 3 | `SmoothMovement`、`Command`、`Compat` |
| `com.smoothmovement.time` | 3 | `ClientTime`、`ClientLevelDeltaTime`、`ServerTime` |
| `com.smoothmovement.mixin.item` | 2 | `ClientItemEntityMixin`、`ServerItemEntityMixin` |
| `com.smoothmovement.mixin.compat` | 2 | `BetterDaysServer`、`BetterDays` |
| `com.smoothmovement.mixin.player` | 1 | `ServerGamePacketListenerImplMixin` |
| `com.smoothmovement.config` | 1 | `CommonConfiguration` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 26）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SmoothMovement` | class | — | Mod | 2 | 38 | [SmoothMovement.java](com/smoothmovement/SmoothMovement.java) |
| `ClientItemEntityMixin` | class | Entity | Mixin | 4 | 92 | [ClientItemEntityMixin.java](com/smoothmovement/mixin/item/ClientItemEntityMixin.java) |
| `ClientEntityMovementMixin` | class | Entity | Mixin | 5 | 81 | [ClientEntityMovementMixin.java](com/smoothmovement/mixin/livingentity/ClientEntityMovementMixin.java) |
| `ServerItemEntityMixin` | class | Entity | Mixin | 5 | 80 | [ServerItemEntityMixin.java](com/smoothmovement/mixin/item/ServerItemEntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 2 | 30 | [ServerGamePacketListenerImplMixin.java](com/smoothmovement/mixin/player/ServerGamePacketListenerImplMixin.java) |
| `ClientLevelTimeDeltaMixin` | class | Level | Mixin | 8 | 160 | [ClientLevelTimeDeltaMixin.java](com/smoothmovement/mixin/time/ClientLevelTimeDeltaMixin.java) |
| `GametimePacketListenerMixin` | class | — | Mixin | 2 | 38 | [GametimePacketListenerMixin.java](com/smoothmovement/mixin/time/GametimePacketListenerMixin.java) |
| `NeoforgeTimePacketMixin` | class | — | Mixin | 1 | 23 | [NeoforgeTimePacketMixin.java](com/smoothmovement/mixin/time/NeoforgeTimePacketMixin.java) |
| `ServerLivingEntityMixin` | class | Entity | Mixin | 2 | 50 | [ServerLivingEntityMixin.java](com/smoothmovement/mixin/livingentity/ServerLivingEntityMixin.java) |
| `ClientMinecartMixin` | class | Entity | Mixin | 4 | 152 | [ClientMinecartMixin.java](com/smoothmovement/mixin/nonliving/ClientMinecartMixin.java) |
| `ServerFallingBlockMixin` | class | Entity | Mixin | 2 | 41 | [ServerFallingBlockMixin.java](com/smoothmovement/mixin/nonliving/ServerFallingBlockMixin.java) |
| `ServerLookControlMixin` | class | — | Mixin | 1 | 23 | [ServerLookControlMixin.java](com/smoothmovement/mixin/livingentity/ServerLookControlMixin.java) |
| `ServerMoveControlMixin` | class | — | Mixin | 2 | 28 | [ServerMoveControlMixin.java](com/smoothmovement/mixin/livingentity/ServerMoveControlMixin.java) |
| `ServerExperienceOrbMixin` | class | Entity | Mixin | 1 | 33 | [ServerExperienceOrbMixin.java](com/smoothmovement/mixin/nonliving/ServerExperienceOrbMixin.java) |
| `ServerProjectileMixin` | class | Entity | Mixin | 2 | 43 | [ServerProjectileMixin.java](com/smoothmovement/mixin/nonliving/ServerProjectileMixin.java) |
| `ServerMinecartMixin` | class | Entity | Mixin | 4 | 99 | [ServerMinecartMixin.java](com/smoothmovement/mixin/nonliving/ServerMinecartMixin.java) |
| `ServerLevelDayTimeMixin` | class | Level | Mixin | 2 | 48 | [ServerLevelDayTimeMixin.java](com/smoothmovement/mixin/time/ServerLevelDayTimeMixin.java) |
| `ServerTickMixin` | class | — | Mixin | 2 | 44 | [ServerTickMixin.java](com/smoothmovement/mixin/time/ServerTickMixin.java) |
| `BetterDaysServer` | class | — | Mixin | 1 | 21 | [BetterDaysServer.java](com/smoothmovement/mixin/compat/BetterDaysServer.java) |
| `BetterDays` | class | — | Mixin | 2 | 27 | [BetterDays.java](com/smoothmovement/mixin/compat/BetterDays.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 98 | [CommonConfiguration.java](com/smoothmovement/config/CommonConfiguration.java) |
| `Command` | class | — | — | 1 | 44 | [Command.java](com/smoothmovement/Command.java) |
| `Compat` | class | — | — | 0 | 6 | [Compat.java](com/smoothmovement/Compat.java) |
| `ClientTime` | class | — | — | 0 | 5 | [ClientTime.java](com/smoothmovement/time/ClientTime.java) |
| `ClientLevelDeltaTime` | interface | — | — | 0 | 12 | [ClientLevelDeltaTime.java](com/smoothmovement/time/ClientLevelDeltaTime.java) |
| `ServerTime` | class | — | — | 2 | 48 | [ServerTime.java](com/smoothmovement/time/ServerTime.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SmoothMovement` | 1 | `com.smoothmovement` | [SmoothMovement.java](com/smoothmovement/SmoothMovement.java) |

## Mixin（19 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientItemEntityMixin` | `ClientItemEntity` | [ClientItemEntityMixin.java](com/smoothmovement/mixin/item/ClientItemEntityMixin.java) |
| `ClientEntityMovementMixin` | `ClientEntityMovement` | [ClientEntityMovementMixin.java](com/smoothmovement/mixin/livingentity/ClientEntityMovementMixin.java) |
| `ServerItemEntityMixin` | `ServerItemEntity` | [ServerItemEntityMixin.java](com/smoothmovement/mixin/item/ServerItemEntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](com/smoothmovement/mixin/player/ServerGamePacketListenerImplMixin.java) |
| `ClientLevelTimeDeltaMixin` | `ClientLevelTimeDelta` | [ClientLevelTimeDeltaMixin.java](com/smoothmovement/mixin/time/ClientLevelTimeDeltaMixin.java) |
| `GametimePacketListenerMixin` | `GametimePacketListener` | [GametimePacketListenerMixin.java](com/smoothmovement/mixin/time/GametimePacketListenerMixin.java) |
| `NeoforgeTimePacketMixin` | `NeoforgeTimePacket` | [NeoforgeTimePacketMixin.java](com/smoothmovement/mixin/time/NeoforgeTimePacketMixin.java) |
| `ServerLivingEntityMixin` | `ServerLivingEntity` | [ServerLivingEntityMixin.java](com/smoothmovement/mixin/livingentity/ServerLivingEntityMixin.java) |
| `ClientMinecartMixin` | `ClientMinecart` | [ClientMinecartMixin.java](com/smoothmovement/mixin/nonliving/ClientMinecartMixin.java) |
| `ServerFallingBlockMixin` | `ServerFallingBlock` | [ServerFallingBlockMixin.java](com/smoothmovement/mixin/nonliving/ServerFallingBlockMixin.java) |
| `ServerLookControlMixin` | `ServerLookControl` | [ServerLookControlMixin.java](com/smoothmovement/mixin/livingentity/ServerLookControlMixin.java) |
| `ServerMoveControlMixin` | `ServerMoveControl` | [ServerMoveControlMixin.java](com/smoothmovement/mixin/livingentity/ServerMoveControlMixin.java) |
| `ServerExperienceOrbMixin` | `ServerExperienceOrb` | [ServerExperienceOrbMixin.java](com/smoothmovement/mixin/nonliving/ServerExperienceOrbMixin.java) |
| `ServerProjectileMixin` | `ServerProjectile` | [ServerProjectileMixin.java](com/smoothmovement/mixin/nonliving/ServerProjectileMixin.java) |
| `ServerMinecartMixin` | `ServerMinecart` | [ServerMinecartMixin.java](com/smoothmovement/mixin/nonliving/ServerMinecartMixin.java) |
| `ServerLevelDayTimeMixin` | `ServerLevelDayTime` | [ServerLevelDayTimeMixin.java](com/smoothmovement/mixin/time/ServerLevelDayTimeMixin.java) |
| `ServerTickMixin` | `ServerTick` | [ServerTickMixin.java](com/smoothmovement/mixin/time/ServerTickMixin.java) |
| `BetterDaysServer` | `BetterDaysServer` | [BetterDaysServer.java](com/smoothmovement/mixin/compat/BetterDaysServer.java) |
| `BetterDays` | `BetterDays` | [BetterDays.java](com/smoothmovement/mixin/compat/BetterDays.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CommonConfiguration` | — | [CommonConfiguration.java](com/smoothmovement/config/CommonConfiguration.java) |

## 全部类（26）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SmoothMovement` | class | — | Mod | 2 | 38 | [SmoothMovement.java](com/smoothmovement/SmoothMovement.java) |
| `ClientItemEntityMixin` | class | Entity | Mixin | 4 | 92 | [ClientItemEntityMixin.java](com/smoothmovement/mixin/item/ClientItemEntityMixin.java) |
| `ClientEntityMovementMixin` | class | Entity | Mixin | 5 | 81 | [ClientEntityMovementMixin.java](com/smoothmovement/mixin/livingentity/ClientEntityMovementMixin.java) |
| `ServerItemEntityMixin` | class | Entity | Mixin | 5 | 80 | [ServerItemEntityMixin.java](com/smoothmovement/mixin/item/ServerItemEntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 2 | 30 | [ServerGamePacketListenerImplMixin.java](com/smoothmovement/mixin/player/ServerGamePacketListenerImplMixin.java) |
| `ClientLevelTimeDeltaMixin` | class | Level | Mixin | 8 | 160 | [ClientLevelTimeDeltaMixin.java](com/smoothmovement/mixin/time/ClientLevelTimeDeltaMixin.java) |
| `GametimePacketListenerMixin` | class | — | Mixin | 2 | 38 | [GametimePacketListenerMixin.java](com/smoothmovement/mixin/time/GametimePacketListenerMixin.java) |
| `NeoforgeTimePacketMixin` | class | — | Mixin | 1 | 23 | [NeoforgeTimePacketMixin.java](com/smoothmovement/mixin/time/NeoforgeTimePacketMixin.java) |
| `ServerLivingEntityMixin` | class | Entity | Mixin | 2 | 50 | [ServerLivingEntityMixin.java](com/smoothmovement/mixin/livingentity/ServerLivingEntityMixin.java) |
| `ClientMinecartMixin` | class | Entity | Mixin | 4 | 152 | [ClientMinecartMixin.java](com/smoothmovement/mixin/nonliving/ClientMinecartMixin.java) |
| `ServerFallingBlockMixin` | class | Entity | Mixin | 2 | 41 | [ServerFallingBlockMixin.java](com/smoothmovement/mixin/nonliving/ServerFallingBlockMixin.java) |
| `ServerLookControlMixin` | class | — | Mixin | 1 | 23 | [ServerLookControlMixin.java](com/smoothmovement/mixin/livingentity/ServerLookControlMixin.java) |
| `ServerMoveControlMixin` | class | — | Mixin | 2 | 28 | [ServerMoveControlMixin.java](com/smoothmovement/mixin/livingentity/ServerMoveControlMixin.java) |
| `ServerExperienceOrbMixin` | class | Entity | Mixin | 1 | 33 | [ServerExperienceOrbMixin.java](com/smoothmovement/mixin/nonliving/ServerExperienceOrbMixin.java) |
| `ServerProjectileMixin` | class | Entity | Mixin | 2 | 43 | [ServerProjectileMixin.java](com/smoothmovement/mixin/nonliving/ServerProjectileMixin.java) |
| `ServerMinecartMixin` | class | Entity | Mixin | 4 | 99 | [ServerMinecartMixin.java](com/smoothmovement/mixin/nonliving/ServerMinecartMixin.java) |
| `ServerLevelDayTimeMixin` | class | Level | Mixin | 2 | 48 | [ServerLevelDayTimeMixin.java](com/smoothmovement/mixin/time/ServerLevelDayTimeMixin.java) |
| `ServerTickMixin` | class | — | Mixin | 2 | 44 | [ServerTickMixin.java](com/smoothmovement/mixin/time/ServerTickMixin.java) |
| `BetterDaysServer` | class | — | Mixin | 1 | 21 | [BetterDaysServer.java](com/smoothmovement/mixin/compat/BetterDaysServer.java) |
| `BetterDays` | class | — | Mixin | 2 | 27 | [BetterDays.java](com/smoothmovement/mixin/compat/BetterDays.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 98 | [CommonConfiguration.java](com/smoothmovement/config/CommonConfiguration.java) |
| `Command` | class | — | — | 1 | 44 | [Command.java](com/smoothmovement/Command.java) |
| `Compat` | class | — | — | 0 | 6 | [Compat.java](com/smoothmovement/Compat.java) |
| `ClientTime` | class | — | — | 0 | 5 | [ClientTime.java](com/smoothmovement/time/ClientTime.java) |
| `ClientLevelDeltaTime` | interface | — | — | 0 | 12 | [ClientLevelDeltaTime.java](com/smoothmovement/time/ClientLevelDeltaTime.java) |
| `ServerTime` | class | — | — | 2 | 48 | [ServerTime.java](com/smoothmovement/time/ServerTime.java) |
