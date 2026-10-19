# Cupboard mod（`cupboard`）

> jar：`[前置]cupboard.jar` ｜ 版本：`2.9` ｜ 作者：Someaddon
> 规模：**16 个类 / 5 个包 / 684 行** ｜ 事件处理类 2 ｜ Mixin 5 ｜ 注册/管理类 3

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.cupboard.mixin` | 5 | `EntityLoadMixin`、`CommandExceptionLoggingMixin`、`ServerAddEntityMixin` |
| `com.cupboard.event` | 3 | `ClientEventHandler`、`EventHandler`、`ModEventHandler` |
| `com.cupboard.config` | 3 | `CupboardConfig`、`ICommonConfig`、`CommonConfiguration` |
| `com.cupboard.util` | 3 | `BlockSearch`、`MathUtil`、`VectorUtil` |
| `com.cupboard` | 2 | `Cupboard`、`CupboardClient` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 16）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | — | — | 2 | 26 | [ClientEventHandler.java](com/cupboard/event/ClientEventHandler.java) |
| `EventHandler` | class | — | — | 2 | 19 | [EventHandler.java](com/cupboard/event/EventHandler.java) |
| `Cupboard` | class | — | Mod | 1 | 46 | [Cupboard.java](com/cupboard/Cupboard.java) |
| `EntityLoadMixin` | class | — | Mixin | 10 | 71 | [EntityLoadMixin.java](com/cupboard/mixin/EntityLoadMixin.java) |
| `CommandExceptionLoggingMixin` | class | — | Mixin | 0 | 17 | [CommandExceptionLoggingMixin.java](com/cupboard/mixin/CommandExceptionLoggingMixin.java) |
| `ServerAddEntityMixin` | class | — | Mixin | 2 | 53 | [ServerAddEntityMixin.java](com/cupboard/mixin/ServerAddEntityMixin.java) |
| `ModEventHandler` | class | — | — | 0 | 5 | [ModEventHandler.java](com/cupboard/event/ModEventHandler.java) |
| `ChunkLoadDebug` | class | — | Mixin | 0 | 28 | [ChunkLoadDebug.java](com/cupboard/mixin/ChunkLoadDebug.java) |
| `ThreadingDetectorMixin` | class | — | Mixin | 0 | 19 | [ThreadingDetectorMixin.java](com/cupboard/mixin/ThreadingDetectorMixin.java) |
| `CupboardConfig` | class | — | — | 10 | 197 | [CupboardConfig.java](com/cupboard/config/CupboardConfig.java) |
| `ICommonConfig` | interface | — | — | 0 | 10 | [ICommonConfig.java](com/cupboard/config/ICommonConfig.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 56 | [CommonConfiguration.java](com/cupboard/config/CommonConfiguration.java) |
| `CupboardClient` | class | — | — | 1 | 12 | [CupboardClient.java](com/cupboard/CupboardClient.java) |
| `BlockSearch` | class | — | — | 1 | 83 | [BlockSearch.java](com/cupboard/util/BlockSearch.java) |
| `MathUtil` | class | — | — | 6 | 28 | [MathUtil.java](com/cupboard/util/MathUtil.java) |
| `VectorUtil` | class | — | — | 2 | 14 | [VectorUtil.java](com/cupboard/util/VectorUtil.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventHandler` | 2 | `com.cupboard.event` | [ClientEventHandler.java](com/cupboard/event/ClientEventHandler.java) |
| `EventHandler` | 2 | `com.cupboard.event` | [EventHandler.java](com/cupboard/event/EventHandler.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `EntityLoadMixin` | `EntityLoad` | [EntityLoadMixin.java](com/cupboard/mixin/EntityLoadMixin.java) |
| `CommandExceptionLoggingMixin` | `CommandExceptionLogging` | [CommandExceptionLoggingMixin.java](com/cupboard/mixin/CommandExceptionLoggingMixin.java) |
| `ServerAddEntityMixin` | `ServerAddEntity` | [ServerAddEntityMixin.java](com/cupboard/mixin/ServerAddEntityMixin.java) |
| `ChunkLoadDebug` | `ChunkLoadDebug` | [ChunkLoadDebug.java](com/cupboard/mixin/ChunkLoadDebug.java) |
| `ThreadingDetectorMixin` | `ThreadingDetector` | [ThreadingDetectorMixin.java](com/cupboard/mixin/ThreadingDetectorMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CupboardConfig` | — | [CupboardConfig.java](com/cupboard/config/CupboardConfig.java) |
| `ICommonConfig` | — | [ICommonConfig.java](com/cupboard/config/ICommonConfig.java) |
| `CommonConfiguration` | — | [CommonConfiguration.java](com/cupboard/config/CommonConfiguration.java) |

## 全部类（16）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | — | — | 2 | 26 | [ClientEventHandler.java](com/cupboard/event/ClientEventHandler.java) |
| `EventHandler` | class | — | — | 2 | 19 | [EventHandler.java](com/cupboard/event/EventHandler.java) |
| `Cupboard` | class | — | Mod | 1 | 46 | [Cupboard.java](com/cupboard/Cupboard.java) |
| `EntityLoadMixin` | class | — | Mixin | 10 | 71 | [EntityLoadMixin.java](com/cupboard/mixin/EntityLoadMixin.java) |
| `CommandExceptionLoggingMixin` | class | — | Mixin | 0 | 17 | [CommandExceptionLoggingMixin.java](com/cupboard/mixin/CommandExceptionLoggingMixin.java) |
| `ServerAddEntityMixin` | class | — | Mixin | 2 | 53 | [ServerAddEntityMixin.java](com/cupboard/mixin/ServerAddEntityMixin.java) |
| `ModEventHandler` | class | — | — | 0 | 5 | [ModEventHandler.java](com/cupboard/event/ModEventHandler.java) |
| `ChunkLoadDebug` | class | — | Mixin | 0 | 28 | [ChunkLoadDebug.java](com/cupboard/mixin/ChunkLoadDebug.java) |
| `ThreadingDetectorMixin` | class | — | Mixin | 0 | 19 | [ThreadingDetectorMixin.java](com/cupboard/mixin/ThreadingDetectorMixin.java) |
| `CupboardConfig` | class | — | — | 10 | 197 | [CupboardConfig.java](com/cupboard/config/CupboardConfig.java) |
| `ICommonConfig` | interface | — | — | 0 | 10 | [ICommonConfig.java](com/cupboard/config/ICommonConfig.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 56 | [CommonConfiguration.java](com/cupboard/config/CommonConfiguration.java) |
| `CupboardClient` | class | — | — | 1 | 12 | [CupboardClient.java](com/cupboard/CupboardClient.java) |
| `BlockSearch` | class | — | — | 1 | 83 | [BlockSearch.java](com/cupboard/util/BlockSearch.java) |
| `MathUtil` | class | — | — | 6 | 28 | [MathUtil.java](com/cupboard/util/MathUtil.java) |
| `VectorUtil` | class | — | — | 2 | 14 | [VectorUtil.java](com/cupboard/util/VectorUtil.java) |
