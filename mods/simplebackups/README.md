# Simple Backups（`simplebackups`）

> jar：`SimpleBackups-1.21-4.0.30.jar` ｜ 版本：`1.21-4.0.30` ｜ 作者：MelanX
> 规模：**20 个类 / 7 个包 / 1966 行** ｜ 事件处理类 2 ｜ Mixin 0 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `de.melanx.simplebackups` | 7 | `SimpleBackups`、`EventListener`、`BackupThread` |
| `de.melanx.simplebackups.config` | 4 | `CommonConfig`、`ExperimentalConfig`、`ServerConfig` |
| `de.melanx.simplebackups.commands` | 3 | `MergeCommand`、`BackupCommand`、`PauseCommand` |
| `de.melanx.simplebackups.client` | 2 | `ClientEventHandler`、`ClientInit` |
| `de.melanx.simplebackups.compat` | 2 | `Mc2DiscordCompat`、`CherishedWorldsCompat` |
| `de.melanx.simplebackups.network` | 1 | `Pause` |
| `de.melanx.simplebackups.exception` | 1 | `NotEnoughDiskSpaceException` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 20）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | — | — | 3 | 36 | [ClientEventHandler.java](de/melanx/simplebackups/client/ClientEventHandler.java) |
| `SimpleBackups` | class | — | Mod | 2 | 51 | [SimpleBackups.java](de/melanx/simplebackups/SimpleBackups.java) |
| `CommonConfig` | class | — | — | 25 | 295 | [CommonConfig.java](de/melanx/simplebackups/config/CommonConfig.java) |
| `EventListener` | class | — | — | 5 | 118 | [EventListener.java](de/melanx/simplebackups/EventListener.java) |
| `BackupThread` | class | Thread | — | 30 | 574 | [BackupThread.java](de/melanx/simplebackups/BackupThread.java) |
| `BackupChainManager` | class | — | — | 9 | 102 | [BackupChainManager.java](de/melanx/simplebackups/BackupChainManager.java) |
| `ExperimentalConfig` | class | — | — | 5 | 62 | [ExperimentalConfig.java](de/melanx/simplebackups/config/ExperimentalConfig.java) |
| `BackupData` | class | SavedData | — | 19 | 119 | [BackupData.java](de/melanx/simplebackups/BackupData.java) |
| `ServerConfig` | class | — | — | 3 | 36 | [ServerConfig.java](de/melanx/simplebackups/config/ServerConfig.java) |
| `MergeCommand` | class | Thread | — | 6 | 199 | [MergeCommand.java](de/melanx/simplebackups/commands/MergeCommand.java) |
| `BackupCommand` | class | Command<CommandSourceStack> | — | 2 | 35 | [BackupCommand.java](de/melanx/simplebackups/commands/BackupCommand.java) |
| `PauseCommand` | class | Command<CommandSourceStack> | — | 2 | 33 | [PauseCommand.java](de/melanx/simplebackups/commands/PauseCommand.java) |
| `BackupChain` | class | — | — | 12 | 151 | [BackupChain.java](de/melanx/simplebackups/BackupChain.java) |
| `Mc2DiscordCompat` | class | — | — | 2 | 14 | [Mc2DiscordCompat.java](de/melanx/simplebackups/compat/Mc2DiscordCompat.java) |
| `CherishedWorldsCompat` | class | — | — | 2 | 16 | [CherishedWorldsCompat.java](de/melanx/simplebackups/compat/CherishedWorldsCompat.java) |
| `ClientInit` | class | — | — | 1 | 15 | [ClientInit.java](de/melanx/simplebackups/client/ClientInit.java) |
| `StorageSize` | enum | — | — | 4 | 51 | [StorageSize.java](de/melanx/simplebackups/StorageSize.java) |
| `Pause` | record | CustomPacketPayload | — | 2 | 27 | [Pause.java](de/melanx/simplebackups/network/Pause.java) |
| `BackupType` | enum | — | — | 0 | 8 | [BackupType.java](de/melanx/simplebackups/config/BackupType.java) |
| `NotEnoughDiskSpaceException` | class | IOException | — | 0 | 24 | [NotEnoughDiskSpaceException.java](de/melanx/simplebackups/exception/NotEnoughDiskSpaceException.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EventListener` | 4 | `de.melanx.simplebackups` | [EventListener.java](de/melanx/simplebackups/EventListener.java) |
| `ClientEventHandler` | 1 | `de.melanx.simplebackups.client` | [ClientEventHandler.java](de/melanx/simplebackups/client/ClientEventHandler.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CommonConfig` | — | [CommonConfig.java](de/melanx/simplebackups/config/CommonConfig.java) |
| `ExperimentalConfig` | — | [ExperimentalConfig.java](de/melanx/simplebackups/config/ExperimentalConfig.java) |
| `ServerConfig` | — | [ServerConfig.java](de/melanx/simplebackups/config/ServerConfig.java) |

## 全部类（20）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | — | — | 3 | 36 | [ClientEventHandler.java](de/melanx/simplebackups/client/ClientEventHandler.java) |
| `SimpleBackups` | class | — | Mod | 2 | 51 | [SimpleBackups.java](de/melanx/simplebackups/SimpleBackups.java) |
| `CommonConfig` | class | — | — | 25 | 295 | [CommonConfig.java](de/melanx/simplebackups/config/CommonConfig.java) |
| `EventListener` | class | — | — | 5 | 118 | [EventListener.java](de/melanx/simplebackups/EventListener.java) |
| `BackupThread` | class | Thread | — | 30 | 574 | [BackupThread.java](de/melanx/simplebackups/BackupThread.java) |
| `BackupChainManager` | class | — | — | 9 | 102 | [BackupChainManager.java](de/melanx/simplebackups/BackupChainManager.java) |
| `ExperimentalConfig` | class | — | — | 5 | 62 | [ExperimentalConfig.java](de/melanx/simplebackups/config/ExperimentalConfig.java) |
| `BackupData` | class | SavedData | — | 19 | 119 | [BackupData.java](de/melanx/simplebackups/BackupData.java) |
| `ServerConfig` | class | — | — | 3 | 36 | [ServerConfig.java](de/melanx/simplebackups/config/ServerConfig.java) |
| `MergeCommand` | class | Thread | — | 6 | 199 | [MergeCommand.java](de/melanx/simplebackups/commands/MergeCommand.java) |
| `BackupCommand` | class | Command<CommandSourceStack> | — | 2 | 35 | [BackupCommand.java](de/melanx/simplebackups/commands/BackupCommand.java) |
| `PauseCommand` | class | Command<CommandSourceStack> | — | 2 | 33 | [PauseCommand.java](de/melanx/simplebackups/commands/PauseCommand.java) |
| `BackupChain` | class | — | — | 12 | 151 | [BackupChain.java](de/melanx/simplebackups/BackupChain.java) |
| `Mc2DiscordCompat` | class | — | — | 2 | 14 | [Mc2DiscordCompat.java](de/melanx/simplebackups/compat/Mc2DiscordCompat.java) |
| `CherishedWorldsCompat` | class | — | — | 2 | 16 | [CherishedWorldsCompat.java](de/melanx/simplebackups/compat/CherishedWorldsCompat.java) |
| `ClientInit` | class | — | — | 1 | 15 | [ClientInit.java](de/melanx/simplebackups/client/ClientInit.java) |
| `StorageSize` | enum | — | — | 4 | 51 | [StorageSize.java](de/melanx/simplebackups/StorageSize.java) |
| `Pause` | record | CustomPacketPayload | — | 2 | 27 | [Pause.java](de/melanx/simplebackups/network/Pause.java) |
| `BackupType` | enum | — | — | 0 | 8 | [BackupType.java](de/melanx/simplebackups/config/BackupType.java) |
| `NotEnoughDiskSpaceException` | class | IOException | — | 0 | 24 | [NotEnoughDiskSpaceException.java](de/melanx/simplebackups/exception/NotEnoughDiskSpaceException.java) |
