# Smoothchunk mod（`smoothchunk`）

> jar：`[平滑区块保存]smoothchunk.jar` ｜ 版本：`4.1` ｜ 作者：Someaddon
> 规模：**9 个类 / 4 个包 / 323 行** ｜ 事件处理类 0 ｜ Mixin 5 ｜ 注册/管理类 1

**依赖**：`minecraft`、`cupboard`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.smoothchunk.mixin` | 5 | `PersistentEntitySectionManagerMixin`、`ChunkMapSlowUnload`、`ChunkAccessMixin` |
| `com.smoothchunk.world` | 2 | `PosTimeEntry`、`IChunkTimeSave` |
| `com.smoothchunk` | 1 | `SmoothchunkMod` |
| `com.smoothchunk.config` | 1 | `CommonConfiguration` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 9）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PersistentEntitySectionManagerMixin` | class | — | Mixin | 2 | 24 | [PersistentEntitySectionManagerMixin.java](com/smoothchunk/mixin/PersistentEntitySectionManagerMixin.java) |
| `SmoothchunkMod` | class | — | Mod | 0 | 22 | [SmoothchunkMod.java](com/smoothchunk/SmoothchunkMod.java) |
| `ChunkMapSlowUnload` | class | — | Mixin | 5 | 57 | [ChunkMapSlowUnload.java](com/smoothchunk/mixin/ChunkMapSlowUnload.java) |
| `ChunkAccessMixin` | class | IChunkTimeSave | Mixin | 0 | 23 | [ChunkAccessMixin.java](com/smoothchunk/mixin/ChunkAccessMixin.java) |
| `ServerChunkCacheMixin` | class | — | Mixin | 1 | 23 | [ServerChunkCacheMixin.java](com/smoothchunk/mixin/ServerChunkCacheMixin.java) |
| `ChunkMapMixin` | class | — | Mixin | 3 | 112 | [ChunkMapMixin.java](com/smoothchunk/mixin/ChunkMapMixin.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 40 | [CommonConfiguration.java](com/smoothchunk/config/CommonConfiguration.java) |
| `PosTimeEntry` | class | — | — | 0 | 14 | [PosTimeEntry.java](com/smoothchunk/world/PosTimeEntry.java) |
| `IChunkTimeSave` | interface | — | — | 0 | 8 | [IChunkTimeSave.java](com/smoothchunk/world/IChunkTimeSave.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PersistentEntitySectionManagerMixin` | `PersistentEntitySectionManager` | [PersistentEntitySectionManagerMixin.java](com/smoothchunk/mixin/PersistentEntitySectionManagerMixin.java) |
| `ChunkMapSlowUnload` | `ChunkMapSlowUnload` | [ChunkMapSlowUnload.java](com/smoothchunk/mixin/ChunkMapSlowUnload.java) |
| `ChunkAccessMixin` | `ChunkAccess` | [ChunkAccessMixin.java](com/smoothchunk/mixin/ChunkAccessMixin.java) |
| `ServerChunkCacheMixin` | `ServerChunkCache` | [ServerChunkCacheMixin.java](com/smoothchunk/mixin/ServerChunkCacheMixin.java) |
| `ChunkMapMixin` | `ChunkMap` | [ChunkMapMixin.java](com/smoothchunk/mixin/ChunkMapMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CommonConfiguration` | — | [CommonConfiguration.java](com/smoothchunk/config/CommonConfiguration.java) |

## 全部类（9）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PersistentEntitySectionManagerMixin` | class | — | Mixin | 2 | 24 | [PersistentEntitySectionManagerMixin.java](com/smoothchunk/mixin/PersistentEntitySectionManagerMixin.java) |
| `SmoothchunkMod` | class | — | Mod | 0 | 22 | [SmoothchunkMod.java](com/smoothchunk/SmoothchunkMod.java) |
| `ChunkMapSlowUnload` | class | — | Mixin | 5 | 57 | [ChunkMapSlowUnload.java](com/smoothchunk/mixin/ChunkMapSlowUnload.java) |
| `ChunkAccessMixin` | class | IChunkTimeSave | Mixin | 0 | 23 | [ChunkAccessMixin.java](com/smoothchunk/mixin/ChunkAccessMixin.java) |
| `ServerChunkCacheMixin` | class | — | Mixin | 1 | 23 | [ServerChunkCacheMixin.java](com/smoothchunk/mixin/ServerChunkCacheMixin.java) |
| `ChunkMapMixin` | class | — | Mixin | 3 | 112 | [ChunkMapMixin.java](com/smoothchunk/mixin/ChunkMapMixin.java) |
| `CommonConfiguration` | class | ICommonConfig | — | 2 | 40 | [CommonConfiguration.java](com/smoothchunk/config/CommonConfiguration.java) |
| `PosTimeEntry` | class | — | — | 0 | 14 | [PosTimeEntry.java](com/smoothchunk/world/PosTimeEntry.java) |
| `IChunkTimeSave` | interface | — | — | 0 | 8 | [IChunkTimeSave.java](com/smoothchunk/world/IChunkTimeSave.java) |
