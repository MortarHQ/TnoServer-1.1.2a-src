# FTB Quests（`ftbquests`）

> jar：`[FTB任务]ftb-quests.jar` ｜ 版本：`2101.1.27` ｜ 作者：FTB Team
> 规模：**294 个类 / 32 个包 / 31595 行** ｜ 事件处理类 1 ｜ Mixin 1 ｜ 注册/管理类 6

**依赖**：`neoforge`、`minecraft`、`architectury`、`ftblibrary`、`ftbteams`、`ftbxmodcompat`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftbquests.net` | 65 | `BlockConfigResponseMessage`、`BlockConfigRequestMessage`、`FTBQuestsNetHandler` |
| `dev.ftb.mods.ftbquests.client.gui.quests` | 24 | `QuestScreen`、`ViewQuestPanel`、`ValidItemsScreen` |
| `dev.ftb.mods.ftbquests.quest` | 20 | `Quest`、`TeamData`、`BaseQuestFile` |
| `dev.ftb.mods.ftbquests.quest.task` | 20 | `ItemTask`、`Task`、`KillTask` |
| `dev.ftb.mods.ftbquests.client.gui` | 19 | `MultilineTextEditorScreen`、`QuestsClientConfigScreen`、`EditRewardTableScreen` |
| `dev.ftb.mods.ftbquests.quest.reward` | 18 | `Reward`、`ItemReward`、`LootReward` |
| `dev.ftb.mods.ftbquests.client` | 15 | `FTBQuestsClientEventHandler`、`ClientQuestFile`、`FTBQuestsNetClient` |
| `dev.ftb.mods.ftbquests.block.entity` | 10 | `TaskScreenBlockEntity`、`BaseBarrierBlockEntity`、`LootCrateOpenerBlockEntity` |
| `dev.ftb.mods.ftbquests.util` | 10 | `ConfigQuestObject`、`NetUtils`、`QuestKey` |
| `dev.ftb.mods.ftbquests.quest.theme.selector` | 10 | `TagSelector`、`NotSelector`、`IndirectParentSelector` |
| `dev.ftb.mods.ftbquests.block.neoforge` | 9 | `NeoForgeTaskScreenBlockEntity`、`NeoForgeLootCrateOpenerBlockEntity`、`NeoForgeTaskScreenAuxBlockEntity` |
| `dev.ftb.mods.ftbquests.item` | 8 | `TaskScreenConfiguratorItem`、`ScreenBlockItem`、`LootCrateItem` |
| `dev.ftb.mods.ftbquests.events` | 8 | `QuestProgressEventData`、`ObjectProgressEvent`、`ObjectCompletedEvent` |
| `dev.ftb.mods.ftbquests.quest.theme.property` | 7 | `ThemeProperties`、`ThemeProperty`、`ColorProperty` |
| `dev.ftb.mods.ftbquests` | 6 | `FTBQuestsEventHandler`、`FTBQuests`、`MethodsReturnNonnullByDefault` |
| `dev.ftb.mods.ftbquests.block` | 5 | `TaskScreenBlock`、`LootCrateOpenerBlock`、`QuestBarrierBlock` |
| `dev.ftb.mods.ftbquests.neoforge` | 4 | `FTBQuestsNeoForge`、`NeoForgeTaskScreenRenderer`、`ClientSetup` |
| `dev.ftb.mods.ftbquests.quest.loot` | 4 | `RewardTable`、`LootCrate`、`EntityWeight` |
| `dev.ftb.mods.ftbquests.registry` | 4 | `ModBlockEntityTypes`、`ModBlocks`、`ModItems` |
| `dev.ftb.mods.ftbquests.api` | 4 | `ItemFilterAdapter`、`QuestFile`、`FTBQuestsAPI` |
| `dev.ftb.mods.ftbquests.client.neoforge` | 3 | `ModelBakeEventHandler`、`FTBQuestsClientEventHandlerImpl`、`CamouflagingModel` |
| `dev.ftb.mods.ftbquests.quest.translation` | 3 | `TranslationManager`、`TranslationTable`、`TranslationKey` |
| `dev.ftb.mods.ftbquests.command` | 3 | `FTBQuestsCommands`、`QuestObjectArgument`、`ChangeProgressArgument` |
| `dev.ftb.mods.ftbquests.quest.theme` | 3 | `QuestTheme`、`ThemeLoader`、`SelectorProperties` |
| `dev.ftb.mods.ftbquests.integration` | 2 | `RecipeModHelper`、`PermissionsHelper` |
| `dev.ftb.mods.ftbquests.client.config` | 2 | `LocaleConfig`、`LocaleValue` |
| `dev.ftb.mods.ftbquests.integration.item_filtering` | 2 | `ItemMatchingSystem`、`DisplayStacksCache` |
| `dev.ftb.mods.ftbquests.quest.task.neoforge` | 2 | `ForgeEnergyTaskClientData`、`ForgeEnergyTask` |
| `dev.ftb.mods.ftbquests.mixin` | 1 | `ServerGamePacketListenerImplMixin` |
| `dev.ftb.mods.ftbquests.api.event` | 1 | `CustomFilterDisplayItemsEvent` |
| `dev.ftb.mods.ftbquests.util.neoforge` | 1 | `InventoryUtilImpl` |
| `architectury_inject_FTBQuests121_common_7ec019af26a340b5a1f3cee652736bef_af872a887f47c8c57d4602820202f50b6596622159f243ccfcced99c2969c6abftbquests2101127devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TaskScreenBlockEntity` | class | EditableBlockEntity | — | 31 | 319 | [TaskScreenBlockEntity.java](dev/ftb/mods/ftbquests/block/entity/TaskScreenBlockEntity.java) |
| `FTBQuestsClientEventHandler` | class | — | — | 14 | 239 | [FTBQuestsClientEventHandler.java](dev/ftb/mods/ftbquests/client/FTBQuestsClientEventHandler.java) |
| `FTBQuestsEventHandler` | enum | — | — | 17 | 230 | [FTBQuestsEventHandler.java](dev/ftb/mods/ftbquests/FTBQuestsEventHandler.java) |
| `QuestScreen` | class | BaseScreen | — | 56 | 854 | [QuestScreen.java](dev/ftb/mods/ftbquests/client/gui/quests/QuestScreen.java) |
| `ModelBakeEventHandler` | class | — | — | 2 | 37 | [ModelBakeEventHandler.java](dev/ftb/mods/ftbquests/client/neoforge/ModelBakeEventHandler.java) |
| `BaseBarrierBlockEntity` | class | EditableBlockEntity | — | 40 | 383 | [BaseBarrierBlockEntity.java](dev/ftb/mods/ftbquests/block/entity/BaseBarrierBlockEntity.java) |
| `NeoForgeTaskScreenBlockEntity` | class | TaskScreenBlockEntity | — | 23 | 218 | [NeoForgeTaskScreenBlockEntity.java](dev/ftb/mods/ftbquests/block/neoforge/NeoForgeTaskScreenBlockEntity.java) |
| `MultilineTextEditorScreen` | class | BaseScreen | — | 52 | 684 | [MultilineTextEditorScreen.java](dev/ftb/mods/ftbquests/client/gui/MultilineTextEditorScreen.java) |
| `ViewQuestPanel` | class | ModalPanel | — | 59 | 1180 | [ViewQuestPanel.java](dev/ftb/mods/ftbquests/client/gui/quests/ViewQuestPanel.java) |
| `Quest` | class | QuestObject | — | 104 | 1193 | [Quest.java](dev/ftb/mods/ftbquests/quest/Quest.java) |
| `TeamData` | class | — | — | 71 | 946 | [TeamData.java](dev/ftb/mods/ftbquests/quest/TeamData.java) |
| `BaseQuestFile` | class | QuestObject | — | 113 | 1567 | [BaseQuestFile.java](dev/ftb/mods/ftbquests/quest/BaseQuestFile.java) |
| `FTBQuestsNeoForge` | class | — | Mod | 3 | 114 | [FTBQuestsNeoForge.java](dev/ftb/mods/ftbquests/neoforge/FTBQuestsNeoForge.java) |
| `LootCrateOpenerBlockEntity` | class | BlockEntity | — | 15 | 188 | [LootCrateOpenerBlockEntity.java](dev/ftb/mods/ftbquests/block/entity/LootCrateOpenerBlockEntity.java) |
| `FTBQuestsClientEventHandlerImpl` | class | — | — | 1 | 12 | [FTBQuestsClientEventHandlerImpl.java](dev/ftb/mods/ftbquests/client/neoforge/FTBQuestsClientEventHandlerImpl.java) |
| `TranslationManager` | class | — | — | 22 | 226 | [TranslationManager.java](dev/ftb/mods/ftbquests/quest/translation/TranslationManager.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 1 | 33 | [ServerGamePacketListenerImplMixin.java](dev/ftb/mods/ftbquests/mixin/ServerGamePacketListenerImplMixin.java) |
| `TaskScreenConfiguratorItem` | class | Item | — | 7 | 115 | [TaskScreenConfiguratorItem.java](dev/ftb/mods/ftbquests/item/TaskScreenConfiguratorItem.java) |
| `FTBQuestsCommands` | class | — | — | 21 | 586 | [FTBQuestsCommands.java](dev/ftb/mods/ftbquests/command/FTBQuestsCommands.java) |
| `TaskScreenBlock` | class | BaseEntityBlock | — | 17 | 226 | [TaskScreenBlock.java](dev/ftb/mods/ftbquests/block/TaskScreenBlock.java) |
| `TaskScreenAuxBlockEntity` | class | BlockEntity | — | 10 | 98 | [TaskScreenAuxBlockEntity.java](dev/ftb/mods/ftbquests/block/entity/TaskScreenAuxBlockEntity.java) |
| `NeoForgeLootCrateOpenerBlockEntity` | class | LootCrateOpenerBlockEntity | — | 7 | 50 | [NeoForgeLootCrateOpenerBlockEntity.java](dev/ftb/mods/ftbquests/block/neoforge/NeoForgeLootCrateOpenerBlockEntity.java) |
| `ClientQuestFile` | class | BaseQuestFile | — | 26 | 265 | [ClientQuestFile.java](dev/ftb/mods/ftbquests/client/ClientQuestFile.java) |
| `FTBQuestsNetClient` | class | — | — | 28 | 322 | [FTBQuestsNetClient.java](dev/ftb/mods/ftbquests/client/FTBQuestsNetClient.java) |
| `QuestsClientConfigScreen` | class | EditConfigScreen | — | 3 | 55 | [QuestsClientConfigScreen.java](dev/ftb/mods/ftbquests/client/gui/QuestsClientConfigScreen.java) |
| `QuestObjectBase` | class | Comparable<QuestObjectBase> | — | 62 | 494 | [QuestObjectBase.java](dev/ftb/mods/ftbquests/quest/QuestObjectBase.java) |
| `Chapter` | class | QuestObject | — | 54 | 573 | [Chapter.java](dev/ftb/mods/ftbquests/quest/Chapter.java) |
| `ItemTask` | class | Task | — | 25 | 345 | [ItemTask.java](dev/ftb/mods/ftbquests/quest/task/ItemTask.java) |
| `RewardTable` | class | QuestObjectBase | — | 36 | 519 | [RewardTable.java](dev/ftb/mods/ftbquests/quest/loot/RewardTable.java) |
| `EditRewardTableScreen` | class | AbstractButtonListScreen | — | 23 | 285 | [EditRewardTableScreen.java](dev/ftb/mods/ftbquests/client/gui/EditRewardTableScreen.java) |
| `EmergencyItemsScreen` | class | BaseScreen | — | 15 | 158 | [EmergencyItemsScreen.java](dev/ftb/mods/ftbquests/client/gui/EmergencyItemsScreen.java) |
| `RewardTablesScreen` | class | AbstractButtonListScreen | — | 21 | 325 | [RewardTablesScreen.java](dev/ftb/mods/ftbquests/client/gui/RewardTablesScreen.java) |
| `ValidItemsScreen` | class | BaseScreen | — | 18 | 166 | [ValidItemsScreen.java](dev/ftb/mods/ftbquests/client/gui/quests/ValidItemsScreen.java) |
| `ChapterPanel` | class | Panel | — | 27 | 509 | [ChapterPanel.java](dev/ftb/mods/ftbquests/client/gui/quests/ChapterPanel.java) |
| `ChapterImage` | class | Movable | — | 35 | 367 | [ChapterImage.java](dev/ftb/mods/ftbquests/quest/ChapterImage.java) |
| `QuestLink` | class | QuestObject | — | 30 | 231 | [QuestLink.java](dev/ftb/mods/ftbquests/quest/QuestLink.java) |
| `Task` | class | QuestObject | — | 46 | 339 | [Task.java](dev/ftb/mods/ftbquests/quest/task/Task.java) |
| `Reward` | class | QuestObjectBase | — | 35 | 310 | [Reward.java](dev/ftb/mods/ftbquests/quest/reward/Reward.java) |
| `LootCrateOpenerBlock` | class | BaseEntityBlock | — | 6 | 67 | [LootCrateOpenerBlock.java](dev/ftb/mods/ftbquests/block/LootCrateOpenerBlock.java) |
| `NeoForgeTaskScreenAuxBlockEntity` | class | TaskScreenAuxBlockEntity | — | 3 | 27 | [NeoForgeTaskScreenAuxBlockEntity.java](dev/ftb/mods/ftbquests/block/neoforge/NeoForgeTaskScreenAuxBlockEntity.java) |
| `QuestProgressEventData` | class | — | — | 10 | 68 | [QuestProgressEventData.java](dev/ftb/mods/ftbquests/events/QuestProgressEventData.java) |
| `BlockConfigResponseMessage` | record | CustomPacketPayload | — | 2 | 39 | [BlockConfigResponseMessage.java](dev/ftb/mods/ftbquests/net/BlockConfigResponseMessage.java) |
| `BlockConfigRequestMessage` | record | CustomPacketPayload | — | 2 | 44 | [BlockConfigRequestMessage.java](dev/ftb/mods/ftbquests/net/BlockConfigRequestMessage.java) |
| `ScreenBlockItem` | class | BlockItem | — | 2 | 37 | [ScreenBlockItem.java](dev/ftb/mods/ftbquests/item/ScreenBlockItem.java) |
| `LootCrateItem` | class | Item | — | 6 | 117 | [LootCrateItem.java](dev/ftb/mods/ftbquests/item/LootCrateItem.java) |
| `TaskScreenRenderer` | class | BlockEntityRenderer<TaskScreenBlockE | — | 5 | 201 | [TaskScreenRenderer.java](dev/ftb/mods/ftbquests/client/TaskScreenRenderer.java) |
| `ConfigIconItemStack` | class | ItemStackConfig | — | 4 | 114 | [ConfigIconItemStack.java](dev/ftb/mods/ftbquests/client/ConfigIconItemStack.java) |
| `FTBQuestsClientConfig` | interface | — | — | 0 | 52 | [FTBQuestsClientConfig.java](dev/ftb/mods/ftbquests/client/FTBQuestsClientConfig.java) |
| `FTBQuestsClient` | class | — | — | 18 | 226 | [FTBQuestsClient.java](dev/ftb/mods/ftbquests/client/FTBQuestsClient.java) |
| `RewardSelectorScreen` | class | AbstractGroupedButtonListScreen | — | 18 | 273 | [RewardSelectorScreen.java](dev/ftb/mods/ftbquests/client/gui/RewardSelectorScreen.java) |
| `QuestPanel` | class | Panel | — | 18 | 643 | [QuestPanel.java](dev/ftb/mods/ftbquests/client/gui/quests/QuestPanel.java) |
| `ChapterGroup` | class | QuestObject | — | 28 | 211 | [ChapterGroup.java](dev/ftb/mods/ftbquests/quest/ChapterGroup.java) |
| `LootCrate` | class | — | — | 16 | 174 | [LootCrate.java](dev/ftb/mods/ftbquests/quest/loot/LootCrate.java) |
| `ItemReward` | class | Reward | — | 15 | 202 | [ItemReward.java](dev/ftb/mods/ftbquests/quest/reward/ItemReward.java) |
| `QuestBarrierBlock` | class | BaseEntityBlock | — | 19 | 197 | [QuestBarrierBlock.java](dev/ftb/mods/ftbquests/block/QuestBarrierBlock.java) |
| `QuestBarrierBlockEntity` | class | BaseBarrierBlockEntity | — | 6 | 61 | [QuestBarrierBlockEntity.java](dev/ftb/mods/ftbquests/block/entity/QuestBarrierBlockEntity.java) |
| `DetectorBlockEntity` | class | BlockEntity | — | 5 | 58 | [DetectorBlockEntity.java](dev/ftb/mods/ftbquests/block/entity/DetectorBlockEntity.java) |
| `LootCrateOpenerBlockImpl` | class | — | — | 1 | 10 | [LootCrateOpenerBlockImpl.java](dev/ftb/mods/ftbquests/block/neoforge/LootCrateOpenerBlockImpl.java) |
| `RecipeModHelper` | interface | RecipeModHelper | — | 4 | 47 | [RecipeModHelper.java](dev/ftb/mods/ftbquests/integration/RecipeModHelper.java) |
| `FTBQuestsNetHandler` | class | — | — | 1 | 74 | [FTBQuestsNetHandler.java](dev/ftb/mods/ftbquests/net/FTBQuestsNetHandler.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ModelBakeEventHandler` | 1 | `dev.ftb.mods.ftbquests.client.neoforge` | [ModelBakeEventHandler.java](dev/ftb/mods/ftbquests/client/neoforge/ModelBakeEventHandler.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](dev/ftb/mods/ftbquests/mixin/ServerGamePacketListenerImplMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TaskScreenConfiguratorItem` | — | [TaskScreenConfiguratorItem.java](dev/ftb/mods/ftbquests/item/TaskScreenConfiguratorItem.java) |
| `QuestsClientConfigScreen` | — | [QuestsClientConfigScreen.java](dev/ftb/mods/ftbquests/client/gui/QuestsClientConfigScreen.java) |
| `BlockConfigResponseMessage` | — | [BlockConfigResponseMessage.java](dev/ftb/mods/ftbquests/net/BlockConfigResponseMessage.java) |
| `BlockConfigRequestMessage` | — | [BlockConfigRequestMessage.java](dev/ftb/mods/ftbquests/net/BlockConfigRequestMessage.java) |
| `ConfigIconItemStack` | — | [ConfigIconItemStack.java](dev/ftb/mods/ftbquests/client/ConfigIconItemStack.java) |
| `FTBQuestsClientConfig` | — | [FTBQuestsClientConfig.java](dev/ftb/mods/ftbquests/client/FTBQuestsClientConfig.java) |
| `LocaleConfig` | — | [LocaleConfig.java](dev/ftb/mods/ftbquests/client/config/LocaleConfig.java) |
| `ConfigQuestObject` | — | [ConfigQuestObject.java](dev/ftb/mods/ftbquests/util/ConfigQuestObject.java) |

## 按包浏览

- `architectury_inject_FTBQuests121_common_7ec019af26a340b5a1f3cee652736bef_af872a887f47c8c57d4602820202f50b6596622159f243ccfcced99c2969c6abftbquests2101127devjar`（1 类）
- `dev.ftb.mods.ftbquests`（6 类）
- `dev.ftb.mods.ftbquests.api`（4 类）
- `dev.ftb.mods.ftbquests.api.event`（1 类）
- `dev.ftb.mods.ftbquests.block`（5 类）
- `dev.ftb.mods.ftbquests.block.entity`（10 类）
- `dev.ftb.mods.ftbquests.block.neoforge`（9 类）
- `dev.ftb.mods.ftbquests.client`（15 类）
- `dev.ftb.mods.ftbquests.client.config`（2 类）
- `dev.ftb.mods.ftbquests.client.gui`（19 类）
- `dev.ftb.mods.ftbquests.client.gui.quests`（24 类）
- `dev.ftb.mods.ftbquests.client.neoforge`（3 类）
- `dev.ftb.mods.ftbquests.command`（3 类）
- `dev.ftb.mods.ftbquests.events`（8 类）
- `dev.ftb.mods.ftbquests.integration`（2 类）
- `dev.ftb.mods.ftbquests.integration.item_filtering`（2 类）
- `dev.ftb.mods.ftbquests.item`（8 类）
- `dev.ftb.mods.ftbquests.mixin`（1 类）
- `dev.ftb.mods.ftbquests.neoforge`（4 类）
- `dev.ftb.mods.ftbquests.net`（65 类）
- `dev.ftb.mods.ftbquests.quest`（20 类）
- `dev.ftb.mods.ftbquests.quest.loot`（4 类）
- `dev.ftb.mods.ftbquests.quest.reward`（18 类）
- `dev.ftb.mods.ftbquests.quest.task`（20 类）
- `dev.ftb.mods.ftbquests.quest.task.neoforge`（2 类）
- `dev.ftb.mods.ftbquests.quest.theme`（3 类）
- `dev.ftb.mods.ftbquests.quest.theme.property`（7 类）
- `dev.ftb.mods.ftbquests.quest.theme.selector`（10 类）
- `dev.ftb.mods.ftbquests.quest.translation`（3 类）
- `dev.ftb.mods.ftbquests.registry`（4 类）
- `dev.ftb.mods.ftbquests.util`（10 类）
- `dev.ftb.mods.ftbquests.util.neoforge`（1 类）
