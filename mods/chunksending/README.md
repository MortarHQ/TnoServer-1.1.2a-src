# chunksending mod（`chunksending`）

> jar：`[服务端-优化]chunksending.jar` ｜ 版本：`2.8` ｜ 作者：Someaddon
> 规模：**7 个类 / 3 个包 / 238 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 0

**依赖**：`minecraft`、`cupboard`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.chunksending` | 3 | `ChunkSending`、`IBatchedUpdateSender`、`IChunksendingPlayer` |
| `com.chunksending.mixin` | 3 | `ChunkHolderMixin`、`ServerPlayerChunkSending`、`PlayerChunkSenderMixin` |
| `com.chunksending.config` | 1 | `CommonConfiguration` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 7）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ChunkSending` | class | — | Mod | 1 | 28 | [ChunkSending.java](com/chunksending/ChunkSending.java) |
| `ChunkHolderMixin` | class | GenerationChunkHolder | Mixin | 0 | 39 | [ChunkHolderMixin.java](com/chunksending/mixin/ChunkHolderMixin.java) |
| `ServerPlayerChunkSending` | class | Player | Mixin | 0 | 36 | [ServerPlayerChunkSending.java](com/chunksending/mixin/ServerPlayerChunkSending.java) |
| `PlayerChunkSenderMixin` | class | IBatchedUpdateSender | Mixin | 3 | 79 | [PlayerChunkSenderMixin.java](com/chunksending/mixin/PlayerChunkSenderMixin.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 33 | [CommonConfiguration.java](com/chunksending/config/CommonConfiguration.java) |
| `IBatchedUpdateSender` | interface | — | — | 0 | 14 | [IBatchedUpdateSender.java](com/chunksending/IBatchedUpdateSender.java) |
| `IChunksendingPlayer` | interface | — | — | 0 | 9 | [IChunksendingPlayer.java](com/chunksending/IChunksendingPlayer.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ChunkHolderMixin` | `ChunkHolder` | [ChunkHolderMixin.java](com/chunksending/mixin/ChunkHolderMixin.java) |
| `ServerPlayerChunkSending` | `ServerPlayerChunkSending` | [ServerPlayerChunkSending.java](com/chunksending/mixin/ServerPlayerChunkSending.java) |
| `PlayerChunkSenderMixin` | `PlayerChunkSender` | [PlayerChunkSenderMixin.java](com/chunksending/mixin/PlayerChunkSenderMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CommonConfiguration` | — | [CommonConfiguration.java](com/chunksending/config/CommonConfiguration.java) |

## 全部类（7）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ChunkSending` | class | — | Mod | 1 | 28 | [ChunkSending.java](com/chunksending/ChunkSending.java) |
| `ChunkHolderMixin` | class | GenerationChunkHolder | Mixin | 0 | 39 | [ChunkHolderMixin.java](com/chunksending/mixin/ChunkHolderMixin.java) |
| `ServerPlayerChunkSending` | class | Player | Mixin | 0 | 36 | [ServerPlayerChunkSending.java](com/chunksending/mixin/ServerPlayerChunkSending.java) |
| `PlayerChunkSenderMixin` | class | IBatchedUpdateSender | Mixin | 3 | 79 | [PlayerChunkSenderMixin.java](com/chunksending/mixin/PlayerChunkSenderMixin.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 33 | [CommonConfiguration.java](com/chunksending/config/CommonConfiguration.java) |
| `IBatchedUpdateSender` | interface | — | — | 0 | 14 | [IBatchedUpdateSender.java](com/chunksending/IBatchedUpdateSender.java) |
| `IChunksendingPlayer` | interface | — | — | 0 | 9 | [IChunksendingPlayer.java](com/chunksending/IChunksendingPlayer.java) |
