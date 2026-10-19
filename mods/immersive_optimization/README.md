# Immersive Optimization（`immersive_optimization`）

> jar：`[服务端-优化]immersive_optimization.jar` ｜ 版本：`0.1.4` ｜ 作者：Luke100000
> 规模：**15 个类 / 4 个包 / 700 行** ｜ 事件处理类 1 ｜ Mixin 7 ｜ 注册/管理类 1

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.conczin.immersive_optimization` | 6 | `NeoForgeBusEvents`、`ImmersiveOptimization`、`TickScheduler` |
| `net.conczin.immersive_optimization.mixin` | 5 | `EntityTickListAccessor`、`ServerLevelMixin`、`MinecraftServerMixin` |
| `net.conczin.immersive_optimization.mixin.client` | 2 | `LevelRendererMixin`、`DebugHudMixin` |
| `net.conczin.immersive_optimization.config` | 2 | `Config`、`JsonConfig` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeBusEvents` | class | — | — | 1 | 12 | [NeoForgeBusEvents.java](net/conczin/immersive_optimization/NeoForgeBusEvents.java) |
| `ImmersiveOptimization` | class | — | Mod | 0 | 14 | [ImmersiveOptimization.java](net/conczin/immersive_optimization/ImmersiveOptimization.java) |
| `EntityTickListAccessor` | interface | — | Mixin | 0 | 14 | [EntityTickListAccessor.java](net/conczin/immersive_optimization/mixin/EntityTickListAccessor.java) |
| `LevelRendererMixin` | class | — | Mixin | 0 | 26 | [LevelRendererMixin.java](net/conczin/immersive_optimization/mixin/client/LevelRendererMixin.java) |
| `ServerLevelMixin` | class | — | Mixin | 0 | 26 | [ServerLevelMixin.java](net/conczin/immersive_optimization/mixin/ServerLevelMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 0 | 17 | [MinecraftServerMixin.java](net/conczin/immersive_optimization/mixin/MinecraftServerMixin.java) |
| `ServerLevelAccessor` | interface | — | Mixin | 0 | 13 | [ServerLevelAccessor.java](net/conczin/immersive_optimization/mixin/ServerLevelAccessor.java) |
| `LevelMixin` | class | — | Mixin | 0 | 19 | [LevelMixin.java](net/conczin/immersive_optimization/mixin/LevelMixin.java) |
| `DebugHudMixin` | class | — | Mixin | 1 | 26 | [DebugHudMixin.java](net/conczin/immersive_optimization/mixin/client/DebugHudMixin.java) |
| `Config` | class | JsonConfig | — | 2 | 56 | [Config.java](net/conczin/immersive_optimization/config/Config.java) |
| `JsonConfig` | class | — | — | 3 | 62 | [JsonConfig.java](net/conczin/immersive_optimization/config/JsonConfig.java) |
| `TickScheduler` | class | Runnable | — | 12 | 286 | [TickScheduler.java](net/conczin/immersive_optimization/TickScheduler.java) |
| `Commands` | class | — | — | 4 | 111 | [Commands.java](net/conczin/immersive_optimization/Commands.java) |
| `Constants` | class | — | — | 0 | 11 | [Constants.java](net/conczin/immersive_optimization/Constants.java) |
| `CommonClass` | class | — | — | 1 | 7 | [CommonClass.java](net/conczin/immersive_optimization/CommonClass.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoForgeBusEvents` | 1 | `net.conczin.immersive_optimization` | [NeoForgeBusEvents.java](net/conczin/immersive_optimization/NeoForgeBusEvents.java) |

## Mixin（7 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `EntityTickListAccessor` | `EntityTickListAccessor` | [EntityTickListAccessor.java](net/conczin/immersive_optimization/mixin/EntityTickListAccessor.java) |
| `LevelRendererMixin` | `LevelRenderer` | [LevelRendererMixin.java](net/conczin/immersive_optimization/mixin/client/LevelRendererMixin.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](net/conczin/immersive_optimization/mixin/ServerLevelMixin.java) |
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](net/conczin/immersive_optimization/mixin/MinecraftServerMixin.java) |
| `ServerLevelAccessor` | `ServerLevelAccessor` | [ServerLevelAccessor.java](net/conczin/immersive_optimization/mixin/ServerLevelAccessor.java) |
| `LevelMixin` | `Level` | [LevelMixin.java](net/conczin/immersive_optimization/mixin/LevelMixin.java) |
| `DebugHudMixin` | `DebugHud` | [DebugHudMixin.java](net/conczin/immersive_optimization/mixin/client/DebugHudMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](net/conczin/immersive_optimization/config/Config.java) |
| `JsonConfig` | — | [JsonConfig.java](net/conczin/immersive_optimization/config/JsonConfig.java) |

## 全部类（15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeBusEvents` | class | — | — | 1 | 12 | [NeoForgeBusEvents.java](net/conczin/immersive_optimization/NeoForgeBusEvents.java) |
| `ImmersiveOptimization` | class | — | Mod | 0 | 14 | [ImmersiveOptimization.java](net/conczin/immersive_optimization/ImmersiveOptimization.java) |
| `EntityTickListAccessor` | interface | — | Mixin | 0 | 14 | [EntityTickListAccessor.java](net/conczin/immersive_optimization/mixin/EntityTickListAccessor.java) |
| `LevelRendererMixin` | class | — | Mixin | 0 | 26 | [LevelRendererMixin.java](net/conczin/immersive_optimization/mixin/client/LevelRendererMixin.java) |
| `ServerLevelMixin` | class | — | Mixin | 0 | 26 | [ServerLevelMixin.java](net/conczin/immersive_optimization/mixin/ServerLevelMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 0 | 17 | [MinecraftServerMixin.java](net/conczin/immersive_optimization/mixin/MinecraftServerMixin.java) |
| `ServerLevelAccessor` | interface | — | Mixin | 0 | 13 | [ServerLevelAccessor.java](net/conczin/immersive_optimization/mixin/ServerLevelAccessor.java) |
| `LevelMixin` | class | — | Mixin | 0 | 19 | [LevelMixin.java](net/conczin/immersive_optimization/mixin/LevelMixin.java) |
| `DebugHudMixin` | class | — | Mixin | 1 | 26 | [DebugHudMixin.java](net/conczin/immersive_optimization/mixin/client/DebugHudMixin.java) |
| `Config` | class | JsonConfig | — | 2 | 56 | [Config.java](net/conczin/immersive_optimization/config/Config.java) |
| `JsonConfig` | class | — | — | 3 | 62 | [JsonConfig.java](net/conczin/immersive_optimization/config/JsonConfig.java) |
| `TickScheduler` | class | Runnable | — | 12 | 286 | [TickScheduler.java](net/conczin/immersive_optimization/TickScheduler.java) |
| `Commands` | class | — | — | 4 | 111 | [Commands.java](net/conczin/immersive_optimization/Commands.java) |
| `Constants` | class | — | — | 0 | 11 | [Constants.java](net/conczin/immersive_optimization/Constants.java) |
| `CommonClass` | class | — | — | 1 | 7 | [CommonClass.java](net/conczin/immersive_optimization/CommonClass.java) |
