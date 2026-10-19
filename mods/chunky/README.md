# Chunky（`chunky`）

> jar：`[服务端-区块预加载]Chunky.jar` ｜ 版本：`1.4.23` ｜ 作者：pop4959
> 规模：**121 个类 / 18 个包 / 8725 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 0

**官方描述**：Pre-generates chunks, quickly, efficiently, and safely

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `org.popcraft.chunky.command` | 23 | `TrimCommand`、`CommandArguments`、`QuietCommand` |
| `org.popcraft.chunky.nbt` | 16 | `CompoundTag`、`ListTag`、`EndTag` |
| `org.popcraft.chunky.shape` | 16 | `ShapeUtil`、`Square`、`AbstractShape` |
| `org.popcraft.chunky.util` | 15 | `Hilbert`、`Input`、`ChunkCoordinate` |
| `org.popcraft.chunky.platform` | 11 | `Config`、`NeoForgeWorld`、`NeoForgeServer` |
| `org.popcraft.chunky.iterator` | 9 | `RegionChunkIterator`、`WorldChunkIterator`、`Loop2ChunkIterator` |
| `org.popcraft.chunky` | 5 | `ChunkyNeoForge`、`Selection`、`GenerationTask` |
| `org.popcraft.chunky.nbt.util` | 4 | `Chunk`、`ChunkPos`、`RegionFile` |
| `org.popcraft.chunky.command.suggestion` | 4 | `SuggestionProviders`、`PatternSuggestionProvider`、`ShapeSuggestionProvider` |
| `org.popcraft.chunky.event` | 3 | `EventBus`、`Event`、`Cancellable` |
| `org.popcraft.chunky.platform.util` | 3 | `Location`、`Vector3`、`Vector2` |
| `org.popcraft.chunky.event.task` | 2 | `GenerationTaskFinishEvent`、`GenerationTaskUpdateEvent` |
| `org.popcraft.chunky.api.event.task` | 2 | `GenerationProgressEvent`、`GenerationCompleteEvent` |
| `org.popcraft.chunky.integration` | 2 | `Integration`、`BorderIntegration` |
| `org.popcraft.chunky.api` | 2 | `ChunkyAPIImpl`、`ChunkyAPI` |
| `org.popcraft.chunky.listeners.bossbar` | 2 | `BossBarTaskUpdateListener`、`BossBarTaskFinishListener` |
| `org.popcraft.chunky.platform.impl` | 1 | `GsonConfig` |
| `org.popcraft.chunky.event.command` | 1 | `ReloadCommandEvent` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ChunkyNeoForge` | class | — | Mod | 5 | 185 | [ChunkyNeoForge.java](org/popcraft/chunky/ChunkyNeoForge.java) |
| `GsonConfig` | class | Config | — | 43 | 250 | [GsonConfig.java](org/popcraft/chunky/platform/impl/GsonConfig.java) |
| `Selection` | class | — | — | 33 | 225 | [Selection.java](org/popcraft/chunky/Selection.java) |
| `ReloadCommandEvent` | record | Event | — | 0 | 7 | [ReloadCommandEvent.java](org/popcraft/chunky/event/command/ReloadCommandEvent.java) |
| `GenerationTask` | class | Runnable | — | 22 | 312 | [GenerationTask.java](org/popcraft/chunky/GenerationTask.java) |
| `TrimCommand` | class | ChunkyCommand | — | 8 | 331 | [TrimCommand.java](org/popcraft/chunky/command/TrimCommand.java) |
| `Hilbert` | class | — | — | 2 | 1042 | [Hilbert.java](org/popcraft/chunky/util/Hilbert.java) |
| `Chunky` | class | — | — | 20 | 196 | [Chunky.java](org/popcraft/chunky/Chunky.java) |
| `CompoundTag` | class | Tag | — | 22 | 142 | [CompoundTag.java](org/popcraft/chunky/nbt/CompoundTag.java) |
| `EventBus` | class | — | — | 5 | 59 | [EventBus.java](org/popcraft/chunky/event/EventBus.java) |
| `Config` | interface | — | — | 0 | 26 | [Config.java](org/popcraft/chunky/platform/Config.java) |
| `CommandArguments` | class | — | — | 7 | 48 | [CommandArguments.java](org/popcraft/chunky/command/CommandArguments.java) |
| `RegionChunkIterator` | class | ChunkIterator | — | 10 | 255 | [RegionChunkIterator.java](org/popcraft/chunky/iterator/RegionChunkIterator.java) |
| `Event` | interface | — | — | 0 | 5 | [Event.java](org/popcraft/chunky/event/Event.java) |
| `GenerationTaskFinishEvent` | record | Event | — | 0 | 8 | [GenerationTaskFinishEvent.java](org/popcraft/chunky/event/task/GenerationTaskFinishEvent.java) |
| `GenerationTaskUpdateEvent` | record | Event | — | 0 | 8 | [GenerationTaskUpdateEvent.java](org/popcraft/chunky/event/task/GenerationTaskUpdateEvent.java) |
| `Location` | class | — | — | 15 | 107 | [Location.java](org/popcraft/chunky/platform/util/Location.java) |
| `GenerationProgressEvent` | record | Event | — | 0 | 9 | [GenerationProgressEvent.java](org/popcraft/chunky/api/event/task/GenerationProgressEvent.java) |
| `GenerationCompleteEvent` | record | Event | — | 0 | 7 | [GenerationCompleteEvent.java](org/popcraft/chunky/api/event/task/GenerationCompleteEvent.java) |
| `QuietCommand` | class | ChunkyCommand | — | 2 | 33 | [QuietCommand.java](org/popcraft/chunky/command/QuietCommand.java) |
| `SpawnCommand` | class | ChunkyCommand | — | 2 | 28 | [SpawnCommand.java](org/popcraft/chunky/command/SpawnCommand.java) |
| `HelpCommand` | class | ChunkyCommand | — | 2 | 78 | [HelpCommand.java](org/popcraft/chunky/command/HelpCommand.java) |
| `WorldBorderCommand` | class | ChunkyCommand | — | 2 | 86 | [WorldBorderCommand.java](org/popcraft/chunky/command/WorldBorderCommand.java) |
| `CancelCommand` | class | ChunkyCommand | — | 2 | 74 | [CancelCommand.java](org/popcraft/chunky/command/CancelCommand.java) |
| `CornersCommand` | class | ChunkyCommand | — | 2 | 56 | [CornersCommand.java](org/popcraft/chunky/command/CornersCommand.java) |
| `ReloadCommand` | class | ChunkyCommand | — | 2 | 41 | [ReloadCommand.java](org/popcraft/chunky/command/ReloadCommand.java) |
| `PatternCommand` | class | ChunkyCommand | — | 2 | 42 | [PatternCommand.java](org/popcraft/chunky/command/PatternCommand.java) |
| `ProgressCommand` | class | ChunkyCommand | — | 2 | 36 | [ProgressCommand.java](org/popcraft/chunky/command/ProgressCommand.java) |
| `ChunkyCommand` | interface | — | — | 0 | 11 | [ChunkyCommand.java](org/popcraft/chunky/command/ChunkyCommand.java) |
| `SilentCommand` | class | ChunkyCommand | — | 2 | 27 | [SilentCommand.java](org/popcraft/chunky/command/SilentCommand.java) |
| `CommandLiteral` | class | — | — | 0 | 53 | [CommandLiteral.java](org/popcraft/chunky/command/CommandLiteral.java) |
| `ContinueCommand` | class | ChunkyCommand | — | 2 | 63 | [ContinueCommand.java](org/popcraft/chunky/command/ContinueCommand.java) |
| `ConfirmCommand` | class | ChunkyCommand | — | 2 | 30 | [ConfirmCommand.java](org/popcraft/chunky/command/ConfirmCommand.java) |
| `WorldCommand` | class | ChunkyCommand | — | 2 | 46 | [WorldCommand.java](org/popcraft/chunky/command/WorldCommand.java) |
| `PauseCommand` | class | ChunkyCommand | — | 2 | 52 | [PauseCommand.java](org/popcraft/chunky/command/PauseCommand.java) |
| `RadiusCommand` | class | ChunkyCommand | — | 2 | 61 | [RadiusCommand.java](org/popcraft/chunky/command/RadiusCommand.java) |
| `SelectionCommand` | class | ChunkyCommand | — | 2 | 38 | [SelectionCommand.java](org/popcraft/chunky/command/SelectionCommand.java) |
| `ShapeCommand` | class | ChunkyCommand | — | 2 | 35 | [ShapeCommand.java](org/popcraft/chunky/command/ShapeCommand.java) |
| `StartCommand` | class | ChunkyCommand | — | 2 | 115 | [StartCommand.java](org/popcraft/chunky/command/StartCommand.java) |
| `CenterCommand` | class | ChunkyCommand | — | 2 | 51 | [CenterCommand.java](org/popcraft/chunky/command/CenterCommand.java) |
| `ShapeUtil` | class | — | — | 5 | 58 | [ShapeUtil.java](org/popcraft/chunky/shape/ShapeUtil.java) |
| `Integration` | interface | — | — | 0 | 5 | [Integration.java](org/popcraft/chunky/integration/Integration.java) |
| `BorderIntegration` | interface | — | — | 0 | 10 | [BorderIntegration.java](org/popcraft/chunky/integration/BorderIntegration.java) |
| `NeoForgeWorld` | class | World | — | 14 | 196 | [NeoForgeWorld.java](org/popcraft/chunky/platform/NeoForgeWorld.java) |
| `Vector3` | class | — | — | 14 | 83 | [Vector3.java](org/popcraft/chunky/platform/util/Vector3.java) |
| `Vector2` | class | — | — | 12 | 69 | [Vector2.java](org/popcraft/chunky/platform/util/Vector2.java) |
| `Input` | class | — | — | 13 | 126 | [Input.java](org/popcraft/chunky/util/Input.java) |
| `ListTag` | class | Tag | — | 9 | 102 | [ListTag.java](org/popcraft/chunky/nbt/ListTag.java) |
| `EndTag` | class | Tag | — | 7 | 45 | [EndTag.java](org/popcraft/chunky/nbt/EndTag.java) |
| `Tag` | class | — | — | 7 | 106 | [Tag.java](org/popcraft/chunky/nbt/Tag.java) |
| `ShortTag` | class | Tag | — | 9 | 63 | [ShortTag.java](org/popcraft/chunky/nbt/ShortTag.java) |
| `LongTag` | class | Tag | — | 9 | 63 | [LongTag.java](org/popcraft/chunky/nbt/LongTag.java) |
| `FloatTag` | class | Tag | — | 9 | 63 | [FloatTag.java](org/popcraft/chunky/nbt/FloatTag.java) |
| `IntTag` | class | Tag | — | 9 | 63 | [IntTag.java](org/popcraft/chunky/nbt/IntTag.java) |
| `ByteTag` | class | Tag | — | 9 | 63 | [ByteTag.java](org/popcraft/chunky/nbt/ByteTag.java) |
| `StringTag` | class | Tag | — | 9 | 64 | [StringTag.java](org/popcraft/chunky/nbt/StringTag.java) |
| `ByteArrayTag` | class | Tag | — | 9 | 68 | [ByteArrayTag.java](org/popcraft/chunky/nbt/ByteArrayTag.java) |
| `LongArrayTag` | class | Tag | — | 9 | 75 | [LongArrayTag.java](org/popcraft/chunky/nbt/LongArrayTag.java) |
| `DoubleTag` | class | Tag | — | 9 | 63 | [DoubleTag.java](org/popcraft/chunky/nbt/DoubleTag.java) |
| `IntArrayTag` | class | Tag | — | 9 | 75 | [IntArrayTag.java](org/popcraft/chunky/nbt/IntArrayTag.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ChunkyNeoForge` | 3 | `org.popcraft.chunky` | [ChunkyNeoForge.java](org/popcraft/chunky/ChunkyNeoForge.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `GsonConfig` | — | [GsonConfig.java](org/popcraft/chunky/platform/impl/GsonConfig.java) |
| `Config` | — | [Config.java](org/popcraft/chunky/platform/Config.java) |

## 全部类（121）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ChunkyNeoForge` | class | — | Mod | 5 | 185 | [ChunkyNeoForge.java](org/popcraft/chunky/ChunkyNeoForge.java) |
| `GsonConfig` | class | Config | — | 43 | 250 | [GsonConfig.java](org/popcraft/chunky/platform/impl/GsonConfig.java) |
| `Selection` | class | — | — | 33 | 225 | [Selection.java](org/popcraft/chunky/Selection.java) |
| `ReloadCommandEvent` | record | Event | — | 0 | 7 | [ReloadCommandEvent.java](org/popcraft/chunky/event/command/ReloadCommandEvent.java) |
| `GenerationTask` | class | Runnable | — | 22 | 312 | [GenerationTask.java](org/popcraft/chunky/GenerationTask.java) |
| `TrimCommand` | class | ChunkyCommand | — | 8 | 331 | [TrimCommand.java](org/popcraft/chunky/command/TrimCommand.java) |
| `Hilbert` | class | — | — | 2 | 1042 | [Hilbert.java](org/popcraft/chunky/util/Hilbert.java) |
| `Chunky` | class | — | — | 20 | 196 | [Chunky.java](org/popcraft/chunky/Chunky.java) |
| `CompoundTag` | class | Tag | — | 22 | 142 | [CompoundTag.java](org/popcraft/chunky/nbt/CompoundTag.java) |
| `EventBus` | class | — | — | 5 | 59 | [EventBus.java](org/popcraft/chunky/event/EventBus.java) |
| `Config` | interface | — | — | 0 | 26 | [Config.java](org/popcraft/chunky/platform/Config.java) |
| `CommandArguments` | class | — | — | 7 | 48 | [CommandArguments.java](org/popcraft/chunky/command/CommandArguments.java) |
| `RegionChunkIterator` | class | ChunkIterator | — | 10 | 255 | [RegionChunkIterator.java](org/popcraft/chunky/iterator/RegionChunkIterator.java) |
| `Event` | interface | — | — | 0 | 5 | [Event.java](org/popcraft/chunky/event/Event.java) |
| `GenerationTaskFinishEvent` | record | Event | — | 0 | 8 | [GenerationTaskFinishEvent.java](org/popcraft/chunky/event/task/GenerationTaskFinishEvent.java) |
| `GenerationTaskUpdateEvent` | record | Event | — | 0 | 8 | [GenerationTaskUpdateEvent.java](org/popcraft/chunky/event/task/GenerationTaskUpdateEvent.java) |
| `Location` | class | — | — | 15 | 107 | [Location.java](org/popcraft/chunky/platform/util/Location.java) |
| `GenerationProgressEvent` | record | Event | — | 0 | 9 | [GenerationProgressEvent.java](org/popcraft/chunky/api/event/task/GenerationProgressEvent.java) |
| `GenerationCompleteEvent` | record | Event | — | 0 | 7 | [GenerationCompleteEvent.java](org/popcraft/chunky/api/event/task/GenerationCompleteEvent.java) |
| `QuietCommand` | class | ChunkyCommand | — | 2 | 33 | [QuietCommand.java](org/popcraft/chunky/command/QuietCommand.java) |
| `SpawnCommand` | class | ChunkyCommand | — | 2 | 28 | [SpawnCommand.java](org/popcraft/chunky/command/SpawnCommand.java) |
| `HelpCommand` | class | ChunkyCommand | — | 2 | 78 | [HelpCommand.java](org/popcraft/chunky/command/HelpCommand.java) |
| `WorldBorderCommand` | class | ChunkyCommand | — | 2 | 86 | [WorldBorderCommand.java](org/popcraft/chunky/command/WorldBorderCommand.java) |
| `CancelCommand` | class | ChunkyCommand | — | 2 | 74 | [CancelCommand.java](org/popcraft/chunky/command/CancelCommand.java) |
| `CornersCommand` | class | ChunkyCommand | — | 2 | 56 | [CornersCommand.java](org/popcraft/chunky/command/CornersCommand.java) |
| `ReloadCommand` | class | ChunkyCommand | — | 2 | 41 | [ReloadCommand.java](org/popcraft/chunky/command/ReloadCommand.java) |
| `PatternCommand` | class | ChunkyCommand | — | 2 | 42 | [PatternCommand.java](org/popcraft/chunky/command/PatternCommand.java) |
| `ProgressCommand` | class | ChunkyCommand | — | 2 | 36 | [ProgressCommand.java](org/popcraft/chunky/command/ProgressCommand.java) |
| `ChunkyCommand` | interface | — | — | 0 | 11 | [ChunkyCommand.java](org/popcraft/chunky/command/ChunkyCommand.java) |
| `SilentCommand` | class | ChunkyCommand | — | 2 | 27 | [SilentCommand.java](org/popcraft/chunky/command/SilentCommand.java) |
| `CommandLiteral` | class | — | — | 0 | 53 | [CommandLiteral.java](org/popcraft/chunky/command/CommandLiteral.java) |
| `ContinueCommand` | class | ChunkyCommand | — | 2 | 63 | [ContinueCommand.java](org/popcraft/chunky/command/ContinueCommand.java) |
| `ConfirmCommand` | class | ChunkyCommand | — | 2 | 30 | [ConfirmCommand.java](org/popcraft/chunky/command/ConfirmCommand.java) |
| `WorldCommand` | class | ChunkyCommand | — | 2 | 46 | [WorldCommand.java](org/popcraft/chunky/command/WorldCommand.java) |
| `PauseCommand` | class | ChunkyCommand | — | 2 | 52 | [PauseCommand.java](org/popcraft/chunky/command/PauseCommand.java) |
| `RadiusCommand` | class | ChunkyCommand | — | 2 | 61 | [RadiusCommand.java](org/popcraft/chunky/command/RadiusCommand.java) |
| `SelectionCommand` | class | ChunkyCommand | — | 2 | 38 | [SelectionCommand.java](org/popcraft/chunky/command/SelectionCommand.java) |
| `ShapeCommand` | class | ChunkyCommand | — | 2 | 35 | [ShapeCommand.java](org/popcraft/chunky/command/ShapeCommand.java) |
| `StartCommand` | class | ChunkyCommand | — | 2 | 115 | [StartCommand.java](org/popcraft/chunky/command/StartCommand.java) |
| `CenterCommand` | class | ChunkyCommand | — | 2 | 51 | [CenterCommand.java](org/popcraft/chunky/command/CenterCommand.java) |
| `ShapeUtil` | class | — | — | 5 | 58 | [ShapeUtil.java](org/popcraft/chunky/shape/ShapeUtil.java) |
| `Integration` | interface | — | — | 0 | 5 | [Integration.java](org/popcraft/chunky/integration/Integration.java) |
| `BorderIntegration` | interface | — | — | 0 | 10 | [BorderIntegration.java](org/popcraft/chunky/integration/BorderIntegration.java) |
| `NeoForgeWorld` | class | World | — | 14 | 196 | [NeoForgeWorld.java](org/popcraft/chunky/platform/NeoForgeWorld.java) |
| `Vector3` | class | — | — | 14 | 83 | [Vector3.java](org/popcraft/chunky/platform/util/Vector3.java) |
| `Vector2` | class | — | — | 12 | 69 | [Vector2.java](org/popcraft/chunky/platform/util/Vector2.java) |
| `Input` | class | — | — | 13 | 126 | [Input.java](org/popcraft/chunky/util/Input.java) |
| `ListTag` | class | Tag | — | 9 | 102 | [ListTag.java](org/popcraft/chunky/nbt/ListTag.java) |
| `EndTag` | class | Tag | — | 7 | 45 | [EndTag.java](org/popcraft/chunky/nbt/EndTag.java) |
| `Tag` | class | — | — | 7 | 106 | [Tag.java](org/popcraft/chunky/nbt/Tag.java) |
| `ShortTag` | class | Tag | — | 9 | 63 | [ShortTag.java](org/popcraft/chunky/nbt/ShortTag.java) |
| `LongTag` | class | Tag | — | 9 | 63 | [LongTag.java](org/popcraft/chunky/nbt/LongTag.java) |
| `FloatTag` | class | Tag | — | 9 | 63 | [FloatTag.java](org/popcraft/chunky/nbt/FloatTag.java) |
| `IntTag` | class | Tag | — | 9 | 63 | [IntTag.java](org/popcraft/chunky/nbt/IntTag.java) |
| `ByteTag` | class | Tag | — | 9 | 63 | [ByteTag.java](org/popcraft/chunky/nbt/ByteTag.java) |
| `StringTag` | class | Tag | — | 9 | 64 | [StringTag.java](org/popcraft/chunky/nbt/StringTag.java) |
| `ByteArrayTag` | class | Tag | — | 9 | 68 | [ByteArrayTag.java](org/popcraft/chunky/nbt/ByteArrayTag.java) |
| `LongArrayTag` | class | Tag | — | 9 | 75 | [LongArrayTag.java](org/popcraft/chunky/nbt/LongArrayTag.java) |
| `DoubleTag` | class | Tag | — | 9 | 63 | [DoubleTag.java](org/popcraft/chunky/nbt/DoubleTag.java) |
| `IntArrayTag` | class | Tag | — | 9 | 75 | [IntArrayTag.java](org/popcraft/chunky/nbt/IntArrayTag.java) |
| `Chunk` | class | — | — | 8 | 50 | [Chunk.java](org/popcraft/chunky/nbt/util/Chunk.java) |
| `WorldChunkIterator` | class | ChunkIterator | — | 5 | 177 | [WorldChunkIterator.java](org/popcraft/chunky/iterator/WorldChunkIterator.java) |
| `NeoForgeServer` | class | Server | — | 8 | 80 | [NeoForgeServer.java](org/popcraft/chunky/platform/NeoForgeServer.java) |
| `NeoForgeSender` | class | Sender | — | 6 | 50 | [NeoForgeSender.java](org/popcraft/chunky/platform/NeoForgeSender.java) |
| `NeoForgePlayer` | class | NeoForgeSender | — | 9 | 62 | [NeoForgePlayer.java](org/popcraft/chunky/platform/NeoForgePlayer.java) |
| `ChunkyAPIImpl` | class | ChunkyAPI | — | 8 | 112 | [ChunkyAPIImpl.java](org/popcraft/chunky/api/ChunkyAPIImpl.java) |
| `ChunkCoordinate` | record | Comparable<ChunkCoordinate> | — | 5 | 50 | [ChunkCoordinate.java](org/popcraft/chunky/util/ChunkCoordinate.java) |
| `Parameter` | class | — | — | 5 | 46 | [Parameter.java](org/popcraft/chunky/util/Parameter.java) |
| `TaskLoader` | class | — | — | 9 | 182 | [TaskLoader.java](org/popcraft/chunky/util/TaskLoader.java) |
| `Version` | class | Comparable<Version> | — | 9 | 90 | [Version.java](org/popcraft/chunky/util/Version.java) |
| `Translator` | class | — | — | 6 | 66 | [Translator.java](org/popcraft/chunky/util/Translator.java) |
| `ChunkyProvider` | class | — | — | 1 | 26 | [ChunkyProvider.java](org/popcraft/chunky/ChunkyProvider.java) |
| `BossBarTaskUpdateListener` | class | Consumer<GenerationTaskUpdateEvent> | — | 3 | 92 | [BossBarTaskUpdateListener.java](org/popcraft/chunky/listeners/bossbar/BossBarTaskUpdateListener.java) |
| `BossBarTaskFinishListener` | class | Consumer<GenerationTaskFinishEvent> | — | 1 | 31 | [BossBarTaskFinishListener.java](org/popcraft/chunky/listeners/bossbar/BossBarTaskFinishListener.java) |
| `TagType` | class | — | — | 0 | 21 | [TagType.java](org/popcraft/chunky/nbt/TagType.java) |
| `NBT` | class | — | — | 4 | 41 | [NBT.java](org/popcraft/chunky/nbt/NBT.java) |
| `ChunkPos` | record | — | — | 1 | 8 | [ChunkPos.java](org/popcraft/chunky/nbt/util/ChunkPos.java) |
| `RegionFile` | class | — | — | 2 | 113 | [RegionFile.java](org/popcraft/chunky/nbt/util/RegionFile.java) |
| `ChunkFilter` | class | — | — | 3 | 24 | [ChunkFilter.java](org/popcraft/chunky/nbt/util/ChunkFilter.java) |
| `Loop2ChunkIterator` | class | ChunkIterator | — | 4 | 76 | [Loop2ChunkIterator.java](org/popcraft/chunky/iterator/Loop2ChunkIterator.java) |
| `ConcentricChunkIterator` | class | ChunkIterator | — | 4 | 110 | [ConcentricChunkIterator.java](org/popcraft/chunky/iterator/ConcentricChunkIterator.java) |
| `CsvChunkIterator` | class | ChunkIterator | — | 4 | 81 | [CsvChunkIterator.java](org/popcraft/chunky/iterator/CsvChunkIterator.java) |
| `ChunkIterator` | interface | — | — | 0 | 15 | [ChunkIterator.java](org/popcraft/chunky/iterator/ChunkIterator.java) |
| `ChunkIteratorFactory` | class | — | — | 2 | 32 | [ChunkIteratorFactory.java](org/popcraft/chunky/iterator/ChunkIteratorFactory.java) |
| `SpiralChunkIterator` | class | ChunkIterator | — | 4 | 149 | [SpiralChunkIterator.java](org/popcraft/chunky/iterator/SpiralChunkIterator.java) |
| `PatternType` | class | — | — | 0 | 17 | [PatternType.java](org/popcraft/chunky/iterator/PatternType.java) |
| `Square` | class | AbstractPolygon | — | 3 | 41 | [Square.java](org/popcraft/chunky/shape/Square.java) |
| `AbstractShape` | class | Shape | — | 1 | 36 | [AbstractShape.java](org/popcraft/chunky/shape/AbstractShape.java) |
| `Star` | class | AbstractPolygon | — | 3 | 95 | [Star.java](org/popcraft/chunky/shape/Star.java) |
| `AbstractEllipse` | class | AbstractShape | — | 1 | 13 | [AbstractEllipse.java](org/popcraft/chunky/shape/AbstractEllipse.java) |
| `ShapeFactory` | class | — | — | 4 | 43 | [ShapeFactory.java](org/popcraft/chunky/shape/ShapeFactory.java) |
| `ShapeType` | class | — | — | 1 | 34 | [ShapeType.java](org/popcraft/chunky/shape/ShapeType.java) |
| `Hexagon` | class | AbstractPolygon | — | 3 | 54 | [Hexagon.java](org/popcraft/chunky/shape/Hexagon.java) |
| `Circle` | class | AbstractEllipse | — | 3 | 26 | [Circle.java](org/popcraft/chunky/shape/Circle.java) |
| `Rectangle` | class | AbstractPolygon | — | 3 | 41 | [Rectangle.java](org/popcraft/chunky/shape/Rectangle.java) |
| `Ellipse` | class | AbstractEllipse | — | 3 | 26 | [Ellipse.java](org/popcraft/chunky/shape/Ellipse.java) |
| `Triangle` | class | AbstractPolygon | — | 3 | 41 | [Triangle.java](org/popcraft/chunky/shape/Triangle.java) |
| `Pentagon` | class | AbstractPolygon | — | 3 | 50 | [Pentagon.java](org/popcraft/chunky/shape/Pentagon.java) |
| `Diamond` | class | AbstractPolygon | — | 3 | 45 | [Diamond.java](org/popcraft/chunky/shape/Diamond.java) |
| `Shape` | interface | — | — | 0 | 11 | [Shape.java](org/popcraft/chunky/shape/Shape.java) |
| `AbstractPolygon` | class | AbstractShape | — | 1 | 14 | [AbstractPolygon.java](org/popcraft/chunky/shape/AbstractPolygon.java) |
| `Cancellable` | class | Event | — | 2 | 14 | [Cancellable.java](org/popcraft/chunky/event/Cancellable.java) |
| `NeoForgeBorder` | class | Border | — | 4 | 33 | [NeoForgeBorder.java](org/popcraft/chunky/platform/NeoForgeBorder.java) |
| `Server` | interface | — | — | 0 | 26 | [Server.java](org/popcraft/chunky/platform/Server.java) |
| `Sender` | interface | — | — | 0 | 26 | [Sender.java](org/popcraft/chunky/platform/Sender.java) |
| `World` | interface | — | — | 0 | 48 | [World.java](org/popcraft/chunky/platform/World.java) |
| `Player` | interface | — | — | 0 | 13 | [Player.java](org/popcraft/chunky/platform/Player.java) |
| `Border` | interface | — | — | 0 | 14 | [Border.java](org/popcraft/chunky/platform/Border.java) |
| `ChunkyAPI` | interface | — | — | 0 | 24 | [ChunkyAPI.java](org/popcraft/chunky/api/ChunkyAPI.java) |
| `SuggestionProviders` | class | — | — | 0 | 14 | [SuggestionProviders.java](org/popcraft/chunky/command/suggestion/SuggestionProviders.java) |
| `PatternSuggestionProvider` | class | SuggestionProvider<CommandSourceStac | — | 1 | 27 | [PatternSuggestionProvider.java](org/popcraft/chunky/command/suggestion/PatternSuggestionProvider.java) |
| `ShapeSuggestionProvider` | class | SuggestionProvider<CommandSourceStac | — | 1 | 27 | [ShapeSuggestionProvider.java](org/popcraft/chunky/command/suggestion/ShapeSuggestionProvider.java) |
| `TrimModeSuggestionProvider` | class | SuggestionProvider<CommandSourceStac | — | 1 | 29 | [TrimModeSuggestionProvider.java](org/popcraft/chunky/command/suggestion/TrimModeSuggestionProvider.java) |
| `Disk` | class | — | — | 2 | 39 | [Disk.java](org/popcraft/chunky/util/Disk.java) |
| `RegionCache` | class | — | — | 4 | 48 | [RegionCache.java](org/popcraft/chunky/util/RegionCache.java) |
| `Formatting` | class | — | — | 3 | 39 | [Formatting.java](org/popcraft/chunky/util/Formatting.java) |
| `TaskScheduler` | class | — | — | 2 | 40 | [TaskScheduler.java](org/popcraft/chunky/util/TaskScheduler.java) |
| `PendingAction` | class | — | — | 2 | 22 | [PendingAction.java](org/popcraft/chunky/util/PendingAction.java) |
| `Pair` | record | — | — | 1 | 8 | [Pair.java](org/popcraft/chunky/util/Pair.java) |
| `TranslationKey` | class | — | — | 0 | 108 | [TranslationKey.java](org/popcraft/chunky/util/TranslationKey.java) |
| `ChunkMath` | class | — | — | 2 | 17 | [ChunkMath.java](org/popcraft/chunky/util/ChunkMath.java) |
