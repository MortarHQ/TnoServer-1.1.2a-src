# Chunk Activity Tracker（`chunkactivitytracker`）

> jar：`[优化前置前置]txnilib.jar` ｜ 版本：`1.0.1` ｜ 作者：Toni
> 规模：**6 个类 / 4 个包 / 521 行** ｜ 事件处理类 1 ｜ Mixin 1 ｜ 注册/管理类 0

**官方描述**：Library mod that stores a heatmap of player activity in chunks

**依赖**：`minecraft`、`txnilib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `toni.chunkactivitytracker.foundation.config` | 2 | `AllConfigs`、`CServer` |
| `toni.chunkactivitytracker.data` | 2 | `ChunkActivityMap`、`ChunkActivityInfo` |
| `toni.chunkactivitytracker` | 1 | `ChunkActivityTracker` |
| `toni.chunkactivitytracker.mixins` | 1 | `MinecraftServerMixin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 6）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AllConfigs` | class | — | EventBusSubscriber | 6 | 70 | [AllConfigs.java](toni/chunkactivitytracker/foundation/config/AllConfigs.java) |
| `ChunkActivityTracker` | class | — | Mod | 7 | 139 | [ChunkActivityTracker.java](toni/chunkactivitytracker/ChunkActivityTracker.java) |
| `MinecraftServerMixin` | class | — | Mixin | 1 | 19 | [MinecraftServerMixin.java](toni/chunkactivitytracker/mixins/MinecraftServerMixin.java) |
| `ChunkActivityMap` | class | Serializable | — | 13 | 184 | [ChunkActivityMap.java](toni/chunkactivitytracker/data/ChunkActivityMap.java) |
| `ChunkActivityInfo` | class | Serializable | — | 8 | 92 | [ChunkActivityInfo.java](toni/chunkactivitytracker/data/ChunkActivityInfo.java) |
| `CServer` | class | ConfigBase | — | 1 | 17 | [CServer.java](toni/chunkactivitytracker/foundation/config/CServer.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `AllConfigs` | 2 | `toni.chunkactivitytracker.foundation.config` | [AllConfigs.java](toni/chunkactivitytracker/foundation/config/AllConfigs.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](toni/chunkactivitytracker/mixins/MinecraftServerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `AllConfigs` | EventBusSubscriber | [AllConfigs.java](toni/chunkactivitytracker/foundation/config/AllConfigs.java) |

## 全部类（6）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AllConfigs` | class | — | EventBusSubscriber | 6 | 70 | [AllConfigs.java](toni/chunkactivitytracker/foundation/config/AllConfigs.java) |
| `ChunkActivityTracker` | class | — | Mod | 7 | 139 | [ChunkActivityTracker.java](toni/chunkactivitytracker/ChunkActivityTracker.java) |
| `MinecraftServerMixin` | class | — | Mixin | 1 | 19 | [MinecraftServerMixin.java](toni/chunkactivitytracker/mixins/MinecraftServerMixin.java) |
| `ChunkActivityMap` | class | Serializable | — | 13 | 184 | [ChunkActivityMap.java](toni/chunkactivitytracker/data/ChunkActivityMap.java) |
| `ChunkActivityInfo` | class | Serializable | — | 8 | 92 | [ChunkActivityInfo.java](toni/chunkactivitytracker/data/ChunkActivityInfo.java) |
| `CServer` | class | ConfigBase | — | 1 | 17 | [CServer.java](toni/chunkactivitytracker/foundation/config/CServer.java) |
