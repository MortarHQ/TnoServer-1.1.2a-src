# OneEnoughBlock（`oneenoughblock`）

> jar：`[方块替换]OneEnoughBlock.jar` ｜ 版本：`1.1.2-hotfix` ｜ 作者：Mafuyu404
> 规模：**19 个类 / 10 个包 / 1236 行** ｜ 事件处理类 2 ｜ Mixin 5 ｜ 注册/管理类 2

**依赖**：`minecraft`、`oneenoughitem`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.sighs.oneenoughblock.mixin` | 5 | `TagLoaderMixin`、`ChunkSerializerMixin`、`LevelMixin` |
| `com.sighs.oneenoughblock.init` | 3 | `BlockReplacementCache`、`OEBConfig`、`Utils` |
| `com.sighs.oneenoughblock.event` | 2 | `ClientEventHandler`、`ServerEventHandler` |
| `com.sighs.oneenoughblock` | 2 | `Oneenoughblock`、`OEBClient` |
| `com.sighs.oneenoughblock.client.gui` | 2 | `BlockSelectionScreen`、`BlockTagSelectionScreen` |
| `com.sighs.oneenoughblock.api.adapter` | 1 | `BlockDomainAdapter` |
| `com.sighs.oneenoughblock.data` | 1 | `BlockReplacementValidator` |
| `com.sighs.oneenoughblock.api.adapter.ui` | 1 | `BlockReplacementUiAdapter` |
| `com.sighs.oneenoughblock.client.gui.cache` | 1 | `GlobalBlockReplacementCache` |
| `com.sighs.oneenoughblock.api` | 1 | `IPalettedContainer` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 19）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 6 | 72 | [ClientEventHandler.java](com/sighs/oneenoughblock/event/ClientEventHandler.java) |
| `ServerEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 7 | 73 | [ServerEventHandler.java](com/sighs/oneenoughblock/event/ServerEventHandler.java) |
| `Oneenoughblock` | class | — | Mod | 0 | 28 | [Oneenoughblock.java](com/sighs/oneenoughblock/Oneenoughblock.java) |
| `OEBClient` | class | — | Mod | 0 | 16 | [OEBClient.java](com/sighs/oneenoughblock/OEBClient.java) |
| `BlockReplacementCache` | class | — | — | 26 | 261 | [BlockReplacementCache.java](com/sighs/oneenoughblock/init/BlockReplacementCache.java) |
| `TagLoaderMixin` | class | — | Mixin | 0 | 84 | [TagLoaderMixin.java](com/sighs/oneenoughblock/mixin/TagLoaderMixin.java) |
| `ChunkSerializerMixin` | class | — | Mixin | 1 | 30 | [ChunkSerializerMixin.java](com/sighs/oneenoughblock/mixin/ChunkSerializerMixin.java) |
| `LevelMixin` | class | — | Mixin | 2 | 32 | [LevelMixin.java](com/sighs/oneenoughblock/mixin/LevelMixin.java) |
| `PalettedContainerMixin` | class | IPalettedContainer | Implements、Mixin | 2 | 53 | [PalettedContainerMixin.java](com/sighs/oneenoughblock/mixin/PalettedContainerMixin.java) |
| `LevelChunkSectionMixin` | class | — | Mixin | 2 | 35 | [LevelChunkSectionMixin.java](com/sighs/oneenoughblock/mixin/LevelChunkSectionMixin.java) |
| `BlockDomainAdapter` | class | DomainAdapter | — | 20 | 130 | [BlockDomainAdapter.java](com/sighs/oneenoughblock/api/adapter/BlockDomainAdapter.java) |
| `BlockSelectionScreen` | class | BaseObjectSelectionScreen | — | 9 | 104 | [BlockSelectionScreen.java](com/sighs/oneenoughblock/client/gui/BlockSelectionScreen.java) |
| `BlockTagSelectionScreen` | class | BaseTagSelectionScreen | — | 1 | 29 | [BlockTagSelectionScreen.java](com/sighs/oneenoughblock/client/gui/BlockTagSelectionScreen.java) |
| `OEBConfig` | record | — | — | 2 | 65 | [OEBConfig.java](com/sighs/oneenoughblock/init/OEBConfig.java) |
| `BlockReplacementValidator` | class | BaseReplacementValidator | — | 4 | 41 | [BlockReplacementValidator.java](com/sighs/oneenoughblock/data/BlockReplacementValidator.java) |
| `BlockReplacementUiAdapter` | class | ReplacementUiAdapter | — | 4 | 48 | [BlockReplacementUiAdapter.java](com/sighs/oneenoughblock/api/adapter/ui/BlockReplacementUiAdapter.java) |
| `GlobalBlockReplacementCache` | class | AbstractGlobalReplacementCache | — | 2 | 20 | [GlobalBlockReplacementCache.java](com/sighs/oneenoughblock/client/gui/cache/GlobalBlockReplacementCache.java) |
| `Utils` | class | — | — | 6 | 109 | [Utils.java](com/sighs/oneenoughblock/init/Utils.java) |
| `IPalettedContainer` | interface | — | — | 0 | 6 | [IPalettedContainer.java](com/sighs/oneenoughblock/api/IPalettedContainer.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ServerEventHandler` | 2 | `com.sighs.oneenoughblock.event` | [ServerEventHandler.java](com/sighs/oneenoughblock/event/ServerEventHandler.java) |
| `ClientEventHandler` | 1 | `com.sighs.oneenoughblock.event` | [ClientEventHandler.java](com/sighs/oneenoughblock/event/ClientEventHandler.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `TagLoaderMixin` | `TagLoader` | [TagLoaderMixin.java](com/sighs/oneenoughblock/mixin/TagLoaderMixin.java) |
| `ChunkSerializerMixin` | `ChunkSerializer` | [ChunkSerializerMixin.java](com/sighs/oneenoughblock/mixin/ChunkSerializerMixin.java) |
| `LevelMixin` | `Level` | [LevelMixin.java](com/sighs/oneenoughblock/mixin/LevelMixin.java) |
| `PalettedContainerMixin` | `PalettedContainer` | [PalettedContainerMixin.java](com/sighs/oneenoughblock/mixin/PalettedContainerMixin.java) |
| `LevelChunkSectionMixin` | `LevelChunkSection` | [LevelChunkSectionMixin.java](com/sighs/oneenoughblock/mixin/LevelChunkSectionMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `OEBConfig` | — | [OEBConfig.java](com/sighs/oneenoughblock/init/OEBConfig.java) |

## 全部类（19）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 6 | 72 | [ClientEventHandler.java](com/sighs/oneenoughblock/event/ClientEventHandler.java) |
| `ServerEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 7 | 73 | [ServerEventHandler.java](com/sighs/oneenoughblock/event/ServerEventHandler.java) |
| `Oneenoughblock` | class | — | Mod | 0 | 28 | [Oneenoughblock.java](com/sighs/oneenoughblock/Oneenoughblock.java) |
| `OEBClient` | class | — | Mod | 0 | 16 | [OEBClient.java](com/sighs/oneenoughblock/OEBClient.java) |
| `BlockReplacementCache` | class | — | — | 26 | 261 | [BlockReplacementCache.java](com/sighs/oneenoughblock/init/BlockReplacementCache.java) |
| `TagLoaderMixin` | class | — | Mixin | 0 | 84 | [TagLoaderMixin.java](com/sighs/oneenoughblock/mixin/TagLoaderMixin.java) |
| `ChunkSerializerMixin` | class | — | Mixin | 1 | 30 | [ChunkSerializerMixin.java](com/sighs/oneenoughblock/mixin/ChunkSerializerMixin.java) |
| `LevelMixin` | class | — | Mixin | 2 | 32 | [LevelMixin.java](com/sighs/oneenoughblock/mixin/LevelMixin.java) |
| `PalettedContainerMixin` | class | IPalettedContainer | Implements、Mixin | 2 | 53 | [PalettedContainerMixin.java](com/sighs/oneenoughblock/mixin/PalettedContainerMixin.java) |
| `LevelChunkSectionMixin` | class | — | Mixin | 2 | 35 | [LevelChunkSectionMixin.java](com/sighs/oneenoughblock/mixin/LevelChunkSectionMixin.java) |
| `BlockDomainAdapter` | class | DomainAdapter | — | 20 | 130 | [BlockDomainAdapter.java](com/sighs/oneenoughblock/api/adapter/BlockDomainAdapter.java) |
| `BlockSelectionScreen` | class | BaseObjectSelectionScreen | — | 9 | 104 | [BlockSelectionScreen.java](com/sighs/oneenoughblock/client/gui/BlockSelectionScreen.java) |
| `BlockTagSelectionScreen` | class | BaseTagSelectionScreen | — | 1 | 29 | [BlockTagSelectionScreen.java](com/sighs/oneenoughblock/client/gui/BlockTagSelectionScreen.java) |
| `OEBConfig` | record | — | — | 2 | 65 | [OEBConfig.java](com/sighs/oneenoughblock/init/OEBConfig.java) |
| `BlockReplacementValidator` | class | BaseReplacementValidator | — | 4 | 41 | [BlockReplacementValidator.java](com/sighs/oneenoughblock/data/BlockReplacementValidator.java) |
| `BlockReplacementUiAdapter` | class | ReplacementUiAdapter | — | 4 | 48 | [BlockReplacementUiAdapter.java](com/sighs/oneenoughblock/api/adapter/ui/BlockReplacementUiAdapter.java) |
| `GlobalBlockReplacementCache` | class | AbstractGlobalReplacementCache | — | 2 | 20 | [GlobalBlockReplacementCache.java](com/sighs/oneenoughblock/client/gui/cache/GlobalBlockReplacementCache.java) |
| `Utils` | class | — | — | 6 | 109 | [Utils.java](com/sighs/oneenoughblock/init/Utils.java) |
| `IPalettedContainer` | interface | — | — | 0 | 6 | [IPalettedContainer.java](com/sighs/oneenoughblock/api/IPalettedContainer.java) |
