# Twilight Tweaks（`twilighttweaks`）

> jar：`[暮色魔改]twilighttweaks.jar` ｜ 版本：`1.3` ｜ 作者：jodlodi
> 规模：**10 个类 / 4 个包 / 619 行** ｜ 事件处理类 3 ｜ Mixin 4 ｜ 注册/管理类 0

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `io.github.jodlodi.twilighttweaks.mixin` | 4 | `FinalBossSpawnerBlockEntityMixin`、`BossSpawnerBlockEntityMixin`、`BossSpawnerBlockEntityAccessor` |
| `io.github.jodlodi.twilighttweaks.event` | 2 | `ClientEventSubscriber`、`ServerEventSubscriber` |
| `io.github.jodlodi.twilighttweaks` | 2 | `TweakConfig`、`TwilightTweaks` |
| `io.github.jodlodi.twilighttweaks.spawner_remnant` | 2 | `BossSpawnerRemnantBlockEntity`、`BossSpawnerRemnantBlock` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventSubscriber` | class | — | EventBusSubscriber、ParametersAreNonnullB | 1 | 22 | [ClientEventSubscriber.java](io/github/jodlodi/twilighttweaks/event/ClientEventSubscriber.java) |
| `ServerEventSubscriber` | class | — | EventBusSubscriber、ParametersAreNonnullB | 1 | 30 | [ServerEventSubscriber.java](io/github/jodlodi/twilighttweaks/event/ServerEventSubscriber.java) |
| `TweakConfig` | class | — | EventBusSubscriber | 0 | 39 | [TweakConfig.java](io/github/jodlodi/twilighttweaks/TweakConfig.java) |
| `FinalBossSpawnerBlockEntityMixin` | class | BossSpawnerBlockEntity | MethodsReturnNonnullByDefault、Mixin、Para | 7 | 69 | [FinalBossSpawnerBlockEntityMixin.java](io/github/jodlodi/twilighttweaks/mixin/FinalBossSpawnerBlockEntityMixin.java) |
| `TwilightTweaks` | class | — | Mod、ParametersAreNonnullByDefault | 1 | 60 | [TwilightTweaks.java](io/github/jodlodi/twilighttweaks/TwilightTweaks.java) |
| `BossSpawnerBlockEntityMixin` | class | BlockEntity | MethodsReturnNonnullByDefault、Mixin、Para | 1 | 52 | [BossSpawnerBlockEntityMixin.java](io/github/jodlodi/twilighttweaks/mixin/BossSpawnerBlockEntityMixin.java) |
| `BossSpawnerBlockEntityAccessor` | interface | — | Mixin | 0 | 15 | [BossSpawnerBlockEntityAccessor.java](io/github/jodlodi/twilighttweaks/mixin/BossSpawnerBlockEntityAccessor.java) |
| `FinalCastleBossGazeboComponentMixin` | class | TFStructureComponentOld | MethodsReturnNonnullByDefault、Mixin、Para | 2 | 61 | [FinalCastleBossGazeboComponentMixin.java](io/github/jodlodi/twilighttweaks/mixin/FinalCastleBossGazeboComponentMixin.java) |
| `BossSpawnerRemnantBlockEntity` | class | BlockEntity | MethodsReturnNonnullByDefault、Parameters | 7 | 153 | [BossSpawnerRemnantBlockEntity.java](io/github/jodlodi/twilighttweaks/spawner_remnant/BossSpawnerRemnantBlockEntity.java) |
| `BossSpawnerRemnantBlock` | class | BaseEntityBlock | MethodsReturnNonnullByDefault、Parameters | 9 | 118 | [BossSpawnerRemnantBlock.java](io/github/jodlodi/twilighttweaks/spawner_remnant/BossSpawnerRemnantBlock.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventSubscriber` | 1 | `io.github.jodlodi.twilighttweaks.event` | [ClientEventSubscriber.java](io/github/jodlodi/twilighttweaks/event/ClientEventSubscriber.java) |
| `ServerEventSubscriber` | 1 | `io.github.jodlodi.twilighttweaks.event` | [ServerEventSubscriber.java](io/github/jodlodi/twilighttweaks/event/ServerEventSubscriber.java) |
| `TweakConfig` | 1 | `io.github.jodlodi.twilighttweaks` | [TweakConfig.java](io/github/jodlodi/twilighttweaks/TweakConfig.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `FinalBossSpawnerBlockEntityMixin` | `FinalBossSpawnerBlockEntity` | [FinalBossSpawnerBlockEntityMixin.java](io/github/jodlodi/twilighttweaks/mixin/FinalBossSpawnerBlockEntityMixin.java) |
| `BossSpawnerBlockEntityMixin` | `BossSpawnerBlockEntity` | [BossSpawnerBlockEntityMixin.java](io/github/jodlodi/twilighttweaks/mixin/BossSpawnerBlockEntityMixin.java) |
| `BossSpawnerBlockEntityAccessor` | `BossSpawnerBlockEntityAccessor` | [BossSpawnerBlockEntityAccessor.java](io/github/jodlodi/twilighttweaks/mixin/BossSpawnerBlockEntityAccessor.java) |
| `FinalCastleBossGazeboComponentMixin` | `FinalCastleBossGazeboComponent` | [FinalCastleBossGazeboComponentMixin.java](io/github/jodlodi/twilighttweaks/mixin/FinalCastleBossGazeboComponentMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TweakConfig` | EventBusSubscriber | [TweakConfig.java](io/github/jodlodi/twilighttweaks/TweakConfig.java) |

## 全部类（10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventSubscriber` | class | — | EventBusSubscriber、ParametersAreNonnullB | 1 | 22 | [ClientEventSubscriber.java](io/github/jodlodi/twilighttweaks/event/ClientEventSubscriber.java) |
| `ServerEventSubscriber` | class | — | EventBusSubscriber、ParametersAreNonnullB | 1 | 30 | [ServerEventSubscriber.java](io/github/jodlodi/twilighttweaks/event/ServerEventSubscriber.java) |
| `TweakConfig` | class | — | EventBusSubscriber | 0 | 39 | [TweakConfig.java](io/github/jodlodi/twilighttweaks/TweakConfig.java) |
| `FinalBossSpawnerBlockEntityMixin` | class | BossSpawnerBlockEntity | MethodsReturnNonnullByDefault、Mixin、Para | 7 | 69 | [FinalBossSpawnerBlockEntityMixin.java](io/github/jodlodi/twilighttweaks/mixin/FinalBossSpawnerBlockEntityMixin.java) |
| `TwilightTweaks` | class | — | Mod、ParametersAreNonnullByDefault | 1 | 60 | [TwilightTweaks.java](io/github/jodlodi/twilighttweaks/TwilightTweaks.java) |
| `BossSpawnerBlockEntityMixin` | class | BlockEntity | MethodsReturnNonnullByDefault、Mixin、Para | 1 | 52 | [BossSpawnerBlockEntityMixin.java](io/github/jodlodi/twilighttweaks/mixin/BossSpawnerBlockEntityMixin.java) |
| `BossSpawnerBlockEntityAccessor` | interface | — | Mixin | 0 | 15 | [BossSpawnerBlockEntityAccessor.java](io/github/jodlodi/twilighttweaks/mixin/BossSpawnerBlockEntityAccessor.java) |
| `FinalCastleBossGazeboComponentMixin` | class | TFStructureComponentOld | MethodsReturnNonnullByDefault、Mixin、Para | 2 | 61 | [FinalCastleBossGazeboComponentMixin.java](io/github/jodlodi/twilighttweaks/mixin/FinalCastleBossGazeboComponentMixin.java) |
| `BossSpawnerRemnantBlockEntity` | class | BlockEntity | MethodsReturnNonnullByDefault、Parameters | 7 | 153 | [BossSpawnerRemnantBlockEntity.java](io/github/jodlodi/twilighttweaks/spawner_remnant/BossSpawnerRemnantBlockEntity.java) |
| `BossSpawnerRemnantBlock` | class | BaseEntityBlock | MethodsReturnNonnullByDefault、Parameters | 9 | 118 | [BossSpawnerRemnantBlock.java](io/github/jodlodi/twilighttweaks/spawner_remnant/BossSpawnerRemnantBlock.java) |
