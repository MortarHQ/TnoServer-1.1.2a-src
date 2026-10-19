# Tensura Guild（`tensura_guild`）

> jar：`[转生附属]tensura_guild.jar` ｜ 版本：`1.0.1.2` ｜ 作者：AblazeAQZL
> 规模：**100 个类 / 18 个包 / 17595 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 7

**依赖**：`neoforge`、`minecraft`、`tensura`、`manascore_skill`、`manascore_race`、`manascore_storage`、`architectury`、`smartbrainlib`、`ftbquests`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tensura_guild.data` | 24 | `GuildTaskBoardData`、`GuildTaskService`、`DwarfTaskService` |
| `com.tensura_guild.network` | 21 | `GuildNetwork`、`BuyGuildShopItemPayload`、`SetGuildTitlePayload` |
| `com.tensura_guild.client.screen` | 10 | `GuildTaskBoardScreen`、`GuildTaskScreen`、`GuildShopScreen` |
| `com.tensura_guild.client` | 8 | `GuildClientEvents`、`DwarfTaskMarkers`、`GuildPartyClient` |
| `com.tensura_guild` | 7 | `TensuraGuild`、`GuildPlayerEvents`、`GuildYuukiAggroEvents` |
| `com.tensura_guild.compat.ftbquests` | 7 | `AbstractGuildEventTask`、`AdventureExperienceTask`、`CompleteGuildTask` |
| `com.tensura_guild.registry` | 7 | `GuildBlocks`、`GuildEntityTypes`、`GuildBlockEntities` |
| `com.tensura_guild.entity` | 2 | `LaplaceEntity`、`YuukiKagurazakaEntity` |
| `com.tensura_guild.dialogue` | 2 | `GuildDialogueManager`、`GuildDialogue` |
| `com.tensura_guild.item` | 2 | `GuildTaskItem`、`GuildStructureMapItem` |
| `com.tensura_guild.world.structure` | 2 | `JigsawMinHeightStructure`、`WaterloggingFixProcessor` |
| `com.tensura_guild.world.processor` | 2 | `SmartGrassProcessor`、`GroundLevelProcessor` |
| `com.tensura_guild.command` | 1 | `GuildCommands` |
| `com.tensura_guild.config` | 1 | `GuildConfig` |
| `com.tensura_guild.block.entity` | 1 | `YuukiSpawnerBlockEntity` |
| `com.tensura_guild.compat` | 1 | `GuildCompatHooks` |
| `com.tensura_guild.block` | 1 | `YuukiSpawnerBlock` |
| `com.tensura_guild.init` | 1 | `GuildStructures` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `GuildTaskBoardScreen` | class | Screen | — | 79 | 1257 | [GuildTaskBoardScreen.java](com/tensura_guild/client/screen/GuildTaskBoardScreen.java) |
| `GuildTaskScreen` | class | Screen | — | 64 | 967 | [GuildTaskScreen.java](com/tensura_guild/client/screen/GuildTaskScreen.java) |
| `LaplaceEntity` | class | OtherworlderEntity | — | 61 | 776 | [LaplaceEntity.java](com/tensura_guild/entity/LaplaceEntity.java) |
| `YuukiKagurazakaEntity` | class | OtherworlderEntity | — | 60 | 769 | [YuukiKagurazakaEntity.java](com/tensura_guild/entity/YuukiKagurazakaEntity.java) |
| `GuildTaskBoardData` | record | — | — | 59 | 887 | [GuildTaskBoardData.java](com/tensura_guild/data/GuildTaskBoardData.java) |
| `GuildTaskService` | class | — | — | 105 | 1474 | [GuildTaskService.java](com/tensura_guild/data/GuildTaskService.java) |
| `GuildClientEvents` | class | — | — | 7 | 105 | [GuildClientEvents.java](com/tensura_guild/client/GuildClientEvents.java) |
| `GuildShopScreen` | class | Screen | — | 53 | 641 | [GuildShopScreen.java](com/tensura_guild/client/screen/GuildShopScreen.java) |
| `AdventurerGuildScreen` | class | Screen | — | 53 | 684 | [AdventurerGuildScreen.java](com/tensura_guild/client/screen/AdventurerGuildScreen.java) |
| `TensuraGuild` | class | — | Mod | 3 | 109 | [TensuraGuild.java](com/tensura_guild/TensuraGuild.java) |
| `GuildPlayerEvents` | class | — | — | 12 | 107 | [GuildPlayerEvents.java](com/tensura_guild/GuildPlayerEvents.java) |
| `AdventurerGuildJoinListScreen` | class | Screen | — | 39 | 560 | [AdventurerGuildJoinListScreen.java](com/tensura_guild/client/screen/AdventurerGuildJoinListScreen.java) |
| `GuildYuukiAggroEvents` | class | — | — | 9 | 106 | [GuildYuukiAggroEvents.java](com/tensura_guild/GuildYuukiAggroEvents.java) |
| `GuildPartyFriendlyFireEvents` | class | — | — | 5 | 71 | [GuildPartyFriendlyFireEvents.java](com/tensura_guild/GuildPartyFriendlyFireEvents.java) |
| `DwarfTaskService` | class | — | — | 59 | 762 | [DwarfTaskService.java](com/tensura_guild/data/DwarfTaskService.java) |
| `GuildPartyScreen` | class | Screen | — | 27 | 434 | [GuildPartyScreen.java](com/tensura_guild/client/screen/GuildPartyScreen.java) |
| `GuildCharacterResetEvents` | class | — | — | 3 | 90 | [GuildCharacterResetEvents.java](com/tensura_guild/GuildCharacterResetEvents.java) |
| `GuildTaskEvents` | class | — | — | 3 | 63 | [GuildTaskEvents.java](com/tensura_guild/GuildTaskEvents.java) |
| `GuildReputationShopService` | class | — | — | 35 | 441 | [GuildReputationShopService.java](com/tensura_guild/data/GuildReputationShopService.java) |
| `YuukiDialogueScreen` | class | Screen | — | 29 | 341 | [YuukiDialogueScreen.java](com/tensura_guild/client/screen/YuukiDialogueScreen.java) |
| `GuildWorldData` | class | SavedData | — | 26 | 376 | [GuildWorldData.java](com/tensura_guild/data/GuildWorldData.java) |
| `GuildTaskDefinitions` | class | SimpleJsonResourceReloadListener | — | 27 | 464 | [GuildTaskDefinitions.java](com/tensura_guild/data/GuildTaskDefinitions.java) |
| `DwarfTaskDialogueScreen` | class | Screen | — | 21 | 248 | [DwarfTaskDialogueScreen.java](com/tensura_guild/client/screen/DwarfTaskDialogueScreen.java) |
| `GuildPartyService` | class | — | — | 28 | 371 | [GuildPartyService.java](com/tensura_guild/data/GuildPartyService.java) |
| `GuildCommands` | class | — | — | 14 | 337 | [GuildCommands.java](com/tensura_guild/command/GuildCommands.java) |
| `GuildTitleSettingsScreen` | class | Screen | — | 19 | 232 | [GuildTitleSettingsScreen.java](com/tensura_guild/client/screen/GuildTitleSettingsScreen.java) |
| `GuildDialogueManager` | class | SimpleJsonResourceReloadListener | — | 2 | 42 | [GuildDialogueManager.java](com/tensura_guild/dialogue/GuildDialogueManager.java) |
| `GuildConfig` | class | — | — | 8 | 82 | [GuildConfig.java](com/tensura_guild/config/GuildConfig.java) |
| `GuildLevelData` | record | — | — | 21 | 143 | [GuildLevelData.java](com/tensura_guild/data/GuildLevelData.java) |
| `DwarfTaskDefinitions` | class | SimpleJsonResourceReloadListener | — | 21 | 297 | [DwarfTaskDefinitions.java](com/tensura_guild/data/DwarfTaskDefinitions.java) |
| `YuukiSpawnerBlockEntity` | class | BlockEntity | — | 4 | 60 | [YuukiSpawnerBlockEntity.java](com/tensura_guild/block/entity/YuukiSpawnerBlockEntity.java) |
| `GuildMemberData` | record | — | — | 14 | 253 | [GuildMemberData.java](com/tensura_guild/data/GuildMemberData.java) |
| `DwarfTaskMarkers` | class | — | — | 15 | 246 | [DwarfTaskMarkers.java](com/tensura_guild/client/DwarfTaskMarkers.java) |
| `GuildOtherSettingsScreen` | class | Screen | — | 12 | 149 | [GuildOtherSettingsScreen.java](com/tensura_guild/client/screen/GuildOtherSettingsScreen.java) |
| `GuildNetwork` | class | — | — | 1 | 31 | [GuildNetwork.java](com/tensura_guild/network/GuildNetwork.java) |
| `GuildCompatHooks` | class | — | — | 7 | 63 | [GuildCompatHooks.java](com/tensura_guild/compat/GuildCompatHooks.java) |
| `AbstractGuildEventTask` | class | Task | — | 2 | 23 | [AbstractGuildEventTask.java](com/tensura_guild/compat/ftbquests/AbstractGuildEventTask.java) |
| `DwarfTaskOfferData` | record | — | — | 14 | 129 | [DwarfTaskOfferData.java](com/tensura_guild/data/DwarfTaskOfferData.java) |
| `GuildTaskItem` | class | Item | — | 9 | 183 | [GuildTaskItem.java](com/tensura_guild/item/GuildTaskItem.java) |
| `GuildPartyClient` | class | — | — | 9 | 63 | [GuildPartyClient.java](com/tensura_guild/client/GuildPartyClient.java) |
| `BuyGuildShopItemPayload` | record | CustomPacketPayload | — | 2 | 49 | [BuyGuildShopItemPayload.java](com/tensura_guild/network/BuyGuildShopItemPayload.java) |
| `YuukiSpawnerBlock` | class | BaseEntityBlock | — | 4 | 42 | [YuukiSpawnerBlock.java](com/tensura_guild/block/YuukiSpawnerBlock.java) |
| `AdventureExperienceTask` | class | Task | — | 14 | 102 | [AdventureExperienceTask.java](com/tensura_guild/compat/ftbquests/AdventureExperienceTask.java) |
| `CompleteGuildTask` | class | AbstractGuildEventTask | — | 11 | 106 | [CompleteGuildTask.java](com/tensura_guild/compat/ftbquests/CompleteGuildTask.java) |
| `DwarfTaskData` | record | — | — | 9 | 73 | [DwarfTaskData.java](com/tensura_guild/data/DwarfTaskData.java) |
| `GuildTitleData` | record | — | — | 6 | 99 | [GuildTitleData.java](com/tensura_guild/data/GuildTitleData.java) |
| `DwarfTaskProgressData` | record | — | — | 6 | 69 | [DwarfTaskProgressData.java](com/tensura_guild/data/DwarfTaskProgressData.java) |
| `GuildPartyWorldData` | class | SavedData | — | 6 | 127 | [GuildPartyWorldData.java](com/tensura_guild/data/GuildPartyWorldData.java) |
| `GuildStructureMapItem` | class | Item | — | 1 | 67 | [GuildStructureMapItem.java](com/tensura_guild/item/GuildStructureMapItem.java) |
| `JigsawMinHeightStructure` | class | Structure | — | 5 | 209 | [JigsawMinHeightStructure.java](com/tensura_guild/world/structure/JigsawMinHeightStructure.java) |
| `GuildBlocks` | class | — | — | 0 | 18 | [GuildBlocks.java](com/tensura_guild/registry/GuildBlocks.java) |
| `GuildEntityTypes` | class | — | — | 0 | 30 | [GuildEntityTypes.java](com/tensura_guild/registry/GuildEntityTypes.java) |
| `GuildBlockEntities` | class | — | — | 0 | 17 | [GuildBlockEntities.java](com/tensura_guild/registry/GuildBlockEntities.java) |
| `GuildItems` | class | — | — | 0 | 36 | [GuildItems.java](com/tensura_guild/registry/GuildItems.java) |
| `DwarfTaskClient` | class | — | — | 1 | 15 | [DwarfTaskClient.java](com/tensura_guild/client/DwarfTaskClient.java) |
| `GuildDialogueClient` | class | — | — | 1 | 21 | [GuildDialogueClient.java](com/tensura_guild/client/GuildDialogueClient.java) |
| `GuildTaskClient` | class | — | — | 2 | 22 | [GuildTaskClient.java](com/tensura_guild/client/GuildTaskClient.java) |
| `GuildShopClient` | class | — | — | 3 | 30 | [GuildShopClient.java](com/tensura_guild/client/GuildShopClient.java) |
| `GuildDialogue` | record | — | — | 5 | 79 | [GuildDialogue.java](com/tensura_guild/dialogue/GuildDialogue.java) |
| `SetGuildTitlePayload` | record | CustomPacketPayload | — | 5 | 58 | [SetGuildTitlePayload.java](com/tensura_guild/network/SetGuildTitlePayload.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `GuildConfig` | — | [GuildConfig.java](com/tensura_guild/config/GuildConfig.java) |

## 全部类（100）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `GuildTaskBoardScreen` | class | Screen | — | 79 | 1257 | [GuildTaskBoardScreen.java](com/tensura_guild/client/screen/GuildTaskBoardScreen.java) |
| `GuildTaskScreen` | class | Screen | — | 64 | 967 | [GuildTaskScreen.java](com/tensura_guild/client/screen/GuildTaskScreen.java) |
| `LaplaceEntity` | class | OtherworlderEntity | — | 61 | 776 | [LaplaceEntity.java](com/tensura_guild/entity/LaplaceEntity.java) |
| `YuukiKagurazakaEntity` | class | OtherworlderEntity | — | 60 | 769 | [YuukiKagurazakaEntity.java](com/tensura_guild/entity/YuukiKagurazakaEntity.java) |
| `GuildTaskBoardData` | record | — | — | 59 | 887 | [GuildTaskBoardData.java](com/tensura_guild/data/GuildTaskBoardData.java) |
| `GuildTaskService` | class | — | — | 105 | 1474 | [GuildTaskService.java](com/tensura_guild/data/GuildTaskService.java) |
| `GuildClientEvents` | class | — | — | 7 | 105 | [GuildClientEvents.java](com/tensura_guild/client/GuildClientEvents.java) |
| `GuildShopScreen` | class | Screen | — | 53 | 641 | [GuildShopScreen.java](com/tensura_guild/client/screen/GuildShopScreen.java) |
| `AdventurerGuildScreen` | class | Screen | — | 53 | 684 | [AdventurerGuildScreen.java](com/tensura_guild/client/screen/AdventurerGuildScreen.java) |
| `TensuraGuild` | class | — | Mod | 3 | 109 | [TensuraGuild.java](com/tensura_guild/TensuraGuild.java) |
| `GuildPlayerEvents` | class | — | — | 12 | 107 | [GuildPlayerEvents.java](com/tensura_guild/GuildPlayerEvents.java) |
| `AdventurerGuildJoinListScreen` | class | Screen | — | 39 | 560 | [AdventurerGuildJoinListScreen.java](com/tensura_guild/client/screen/AdventurerGuildJoinListScreen.java) |
| `GuildYuukiAggroEvents` | class | — | — | 9 | 106 | [GuildYuukiAggroEvents.java](com/tensura_guild/GuildYuukiAggroEvents.java) |
| `GuildPartyFriendlyFireEvents` | class | — | — | 5 | 71 | [GuildPartyFriendlyFireEvents.java](com/tensura_guild/GuildPartyFriendlyFireEvents.java) |
| `DwarfTaskService` | class | — | — | 59 | 762 | [DwarfTaskService.java](com/tensura_guild/data/DwarfTaskService.java) |
| `GuildPartyScreen` | class | Screen | — | 27 | 434 | [GuildPartyScreen.java](com/tensura_guild/client/screen/GuildPartyScreen.java) |
| `GuildCharacterResetEvents` | class | — | — | 3 | 90 | [GuildCharacterResetEvents.java](com/tensura_guild/GuildCharacterResetEvents.java) |
| `GuildTaskEvents` | class | — | — | 3 | 63 | [GuildTaskEvents.java](com/tensura_guild/GuildTaskEvents.java) |
| `GuildReputationShopService` | class | — | — | 35 | 441 | [GuildReputationShopService.java](com/tensura_guild/data/GuildReputationShopService.java) |
| `YuukiDialogueScreen` | class | Screen | — | 29 | 341 | [YuukiDialogueScreen.java](com/tensura_guild/client/screen/YuukiDialogueScreen.java) |
| `GuildWorldData` | class | SavedData | — | 26 | 376 | [GuildWorldData.java](com/tensura_guild/data/GuildWorldData.java) |
| `GuildTaskDefinitions` | class | SimpleJsonResourceReloadListener | — | 27 | 464 | [GuildTaskDefinitions.java](com/tensura_guild/data/GuildTaskDefinitions.java) |
| `DwarfTaskDialogueScreen` | class | Screen | — | 21 | 248 | [DwarfTaskDialogueScreen.java](com/tensura_guild/client/screen/DwarfTaskDialogueScreen.java) |
| `GuildPartyService` | class | — | — | 28 | 371 | [GuildPartyService.java](com/tensura_guild/data/GuildPartyService.java) |
| `GuildCommands` | class | — | — | 14 | 337 | [GuildCommands.java](com/tensura_guild/command/GuildCommands.java) |
| `GuildTitleSettingsScreen` | class | Screen | — | 19 | 232 | [GuildTitleSettingsScreen.java](com/tensura_guild/client/screen/GuildTitleSettingsScreen.java) |
| `GuildDialogueManager` | class | SimpleJsonResourceReloadListener | — | 2 | 42 | [GuildDialogueManager.java](com/tensura_guild/dialogue/GuildDialogueManager.java) |
| `GuildConfig` | class | — | — | 8 | 82 | [GuildConfig.java](com/tensura_guild/config/GuildConfig.java) |
| `GuildLevelData` | record | — | — | 21 | 143 | [GuildLevelData.java](com/tensura_guild/data/GuildLevelData.java) |
| `DwarfTaskDefinitions` | class | SimpleJsonResourceReloadListener | — | 21 | 297 | [DwarfTaskDefinitions.java](com/tensura_guild/data/DwarfTaskDefinitions.java) |
| `YuukiSpawnerBlockEntity` | class | BlockEntity | — | 4 | 60 | [YuukiSpawnerBlockEntity.java](com/tensura_guild/block/entity/YuukiSpawnerBlockEntity.java) |
| `GuildMemberData` | record | — | — | 14 | 253 | [GuildMemberData.java](com/tensura_guild/data/GuildMemberData.java) |
| `DwarfTaskMarkers` | class | — | — | 15 | 246 | [DwarfTaskMarkers.java](com/tensura_guild/client/DwarfTaskMarkers.java) |
| `GuildOtherSettingsScreen` | class | Screen | — | 12 | 149 | [GuildOtherSettingsScreen.java](com/tensura_guild/client/screen/GuildOtherSettingsScreen.java) |
| `GuildNetwork` | class | — | — | 1 | 31 | [GuildNetwork.java](com/tensura_guild/network/GuildNetwork.java) |
| `GuildCompatHooks` | class | — | — | 7 | 63 | [GuildCompatHooks.java](com/tensura_guild/compat/GuildCompatHooks.java) |
| `AbstractGuildEventTask` | class | Task | — | 2 | 23 | [AbstractGuildEventTask.java](com/tensura_guild/compat/ftbquests/AbstractGuildEventTask.java) |
| `DwarfTaskOfferData` | record | — | — | 14 | 129 | [DwarfTaskOfferData.java](com/tensura_guild/data/DwarfTaskOfferData.java) |
| `GuildTaskItem` | class | Item | — | 9 | 183 | [GuildTaskItem.java](com/tensura_guild/item/GuildTaskItem.java) |
| `GuildPartyClient` | class | — | — | 9 | 63 | [GuildPartyClient.java](com/tensura_guild/client/GuildPartyClient.java) |
| `BuyGuildShopItemPayload` | record | CustomPacketPayload | — | 2 | 49 | [BuyGuildShopItemPayload.java](com/tensura_guild/network/BuyGuildShopItemPayload.java) |
| `YuukiSpawnerBlock` | class | BaseEntityBlock | — | 4 | 42 | [YuukiSpawnerBlock.java](com/tensura_guild/block/YuukiSpawnerBlock.java) |
| `AdventureExperienceTask` | class | Task | — | 14 | 102 | [AdventureExperienceTask.java](com/tensura_guild/compat/ftbquests/AdventureExperienceTask.java) |
| `CompleteGuildTask` | class | AbstractGuildEventTask | — | 11 | 106 | [CompleteGuildTask.java](com/tensura_guild/compat/ftbquests/CompleteGuildTask.java) |
| `DwarfTaskData` | record | — | — | 9 | 73 | [DwarfTaskData.java](com/tensura_guild/data/DwarfTaskData.java) |
| `GuildTitleData` | record | — | — | 6 | 99 | [GuildTitleData.java](com/tensura_guild/data/GuildTitleData.java) |
| `DwarfTaskProgressData` | record | — | — | 6 | 69 | [DwarfTaskProgressData.java](com/tensura_guild/data/DwarfTaskProgressData.java) |
| `GuildPartyWorldData` | class | SavedData | — | 6 | 127 | [GuildPartyWorldData.java](com/tensura_guild/data/GuildPartyWorldData.java) |
| `GuildStructureMapItem` | class | Item | — | 1 | 67 | [GuildStructureMapItem.java](com/tensura_guild/item/GuildStructureMapItem.java) |
| `JigsawMinHeightStructure` | class | Structure | — | 5 | 209 | [JigsawMinHeightStructure.java](com/tensura_guild/world/structure/JigsawMinHeightStructure.java) |
| `GuildBlocks` | class | — | — | 0 | 18 | [GuildBlocks.java](com/tensura_guild/registry/GuildBlocks.java) |
| `GuildEntityTypes` | class | — | — | 0 | 30 | [GuildEntityTypes.java](com/tensura_guild/registry/GuildEntityTypes.java) |
| `GuildBlockEntities` | class | — | — | 0 | 17 | [GuildBlockEntities.java](com/tensura_guild/registry/GuildBlockEntities.java) |
| `GuildItems` | class | — | — | 0 | 36 | [GuildItems.java](com/tensura_guild/registry/GuildItems.java) |
| `DwarfTaskClient` | class | — | — | 1 | 15 | [DwarfTaskClient.java](com/tensura_guild/client/DwarfTaskClient.java) |
| `GuildDialogueClient` | class | — | — | 1 | 21 | [GuildDialogueClient.java](com/tensura_guild/client/GuildDialogueClient.java) |
| `GuildTaskClient` | class | — | — | 2 | 22 | [GuildTaskClient.java](com/tensura_guild/client/GuildTaskClient.java) |
| `GuildShopClient` | class | — | — | 3 | 30 | [GuildShopClient.java](com/tensura_guild/client/GuildShopClient.java) |
| `GuildDialogue` | record | — | — | 5 | 79 | [GuildDialogue.java](com/tensura_guild/dialogue/GuildDialogue.java) |
| `SetGuildTitlePayload` | record | CustomPacketPayload | — | 5 | 58 | [SetGuildTitlePayload.java](com/tensura_guild/network/SetGuildTitlePayload.java) |
| `GuildPartyActionPayload` | record | CustomPacketPayload | — | 9 | 86 | [GuildPartyActionPayload.java](com/tensura_guild/network/GuildPartyActionPayload.java) |
| `GuildFtbQuestProgress` | class | — | — | 5 | 48 | [GuildFtbQuestProgress.java](com/tensura_guild/compat/ftbquests/GuildFtbQuestProgress.java) |
| `GuildReputationShopDefinitions` | class | SimpleJsonResourceReloadListener | — | 5 | 112 | [GuildReputationShopDefinitions.java](com/tensura_guild/data/GuildReputationShopDefinitions.java) |
| `GuildMemberService` | class | — | — | 8 | 67 | [GuildMemberService.java](com/tensura_guild/data/GuildMemberService.java) |
| `GuildTaskTime` | class | — | — | 6 | 64 | [GuildTaskTime.java](com/tensura_guild/data/GuildTaskTime.java) |
| `GuildTaskRewardPenalty` | record | — | — | 5 | 71 | [GuildTaskRewardPenalty.java](com/tensura_guild/data/GuildTaskRewardPenalty.java) |
| `GuildPartyState` | record | — | — | 9 | 107 | [GuildPartyState.java](com/tensura_guild/data/GuildPartyState.java) |
| `GuildMemberPublicData` | record | — | — | 4 | 50 | [GuildMemberPublicData.java](com/tensura_guild/data/GuildMemberPublicData.java) |
| `GuildSignatureData` | record | — | — | 3 | 80 | [GuildSignatureData.java](com/tensura_guild/data/GuildSignatureData.java) |
| `GuildGameRules` | class | — | — | 4 | 38 | [GuildGameRules.java](com/tensura_guild/GuildGameRules.java) |
| `CompleteDialoguePayload` | record | CustomPacketPayload | — | 2 | 41 | [CompleteDialoguePayload.java](com/tensura_guild/network/CompleteDialoguePayload.java) |
| `DwarfTaskActionPayload` | record | CustomPacketPayload | — | 4 | 55 | [DwarfTaskActionPayload.java](com/tensura_guild/network/DwarfTaskActionPayload.java) |
| `DismissFailedGuildTaskPayload` | record | CustomPacketPayload | — | 2 | 29 | [DismissFailedGuildTaskPayload.java](com/tensura_guild/network/DismissFailedGuildTaskPayload.java) |
| `AcceptGuildTaskResultPayload` | record | CustomPacketPayload | — | 2 | 30 | [AcceptGuildTaskResultPayload.java](com/tensura_guild/network/AcceptGuildTaskResultPayload.java) |
| `OpenDwarfTaskDialoguePayload` | record | CustomPacketPayload | — | 4 | 69 | [OpenDwarfTaskDialoguePayload.java](com/tensura_guild/network/OpenDwarfTaskDialoguePayload.java) |
| `CompleteGuildTaskPayload` | record | CustomPacketPayload | — | 2 | 29 | [CompleteGuildTaskPayload.java](com/tensura_guild/network/CompleteGuildTaskPayload.java) |
| `CloseDialoguePayload` | record | CustomPacketPayload | — | 2 | 35 | [CloseDialoguePayload.java](com/tensura_guild/network/CloseDialoguePayload.java) |
| `GuildShopRefreshPayload` | record | CustomPacketPayload | — | 2 | 23 | [GuildShopRefreshPayload.java](com/tensura_guild/network/GuildShopRefreshPayload.java) |
| `GuildShopStatePayload` | record | CustomPacketPayload | — | 2 | 37 | [GuildShopStatePayload.java](com/tensura_guild/network/GuildShopStatePayload.java) |
| `RequestGuildShopStatePayload` | record | CustomPacketPayload | — | 3 | 60 | [RequestGuildShopStatePayload.java](com/tensura_guild/network/RequestGuildShopStatePayload.java) |
| `SaveGuildSignaturePayload` | record | CustomPacketPayload | — | 2 | 47 | [SaveGuildSignaturePayload.java](com/tensura_guild/network/SaveGuildSignaturePayload.java) |
| `AcceptGuildTaskPayload` | record | CustomPacketPayload | — | 2 | 31 | [AcceptGuildTaskPayload.java](com/tensura_guild/network/AcceptGuildTaskPayload.java) |
| `OpenGuildTaskContentPayload` | record | CustomPacketPayload | — | 2 | 24 | [OpenGuildTaskContentPayload.java](com/tensura_guild/network/OpenGuildTaskContentPayload.java) |
| `GuildPartyChatPayload` | record | CustomPacketPayload | — | 4 | 37 | [GuildPartyChatPayload.java](com/tensura_guild/network/GuildPartyChatPayload.java) |
| `GuildPartyStatePayload` | record | CustomPacketPayload | — | 4 | 32 | [GuildPartyStatePayload.java](com/tensura_guild/network/GuildPartyStatePayload.java) |
| `OpenDialoguePayload` | record | CustomPacketPayload | — | 2 | 33 | [OpenDialoguePayload.java](com/tensura_guild/network/OpenDialoguePayload.java) |
| `GuildShopLimitReachedPayload` | record | CustomPacketPayload | — | 2 | 30 | [GuildShopLimitReachedPayload.java](com/tensura_guild/network/GuildShopLimitReachedPayload.java) |
| `GuildFtbQuestTaskTypes` | class | — | — | 2 | 29 | [GuildFtbQuestTaskTypes.java](com/tensura_guild/compat/ftbquests/GuildFtbQuestTaskTypes.java) |
| `JoinGuildTask` | class | AbstractBooleanTask | — | 4 | 32 | [JoinGuildTask.java](com/tensura_guild/compat/ftbquests/JoinGuildTask.java) |
| `CompleteDwarfTask` | class | AbstractGuildEventTask | — | 1 | 15 | [CompleteDwarfTask.java](com/tensura_guild/compat/ftbquests/CompleteDwarfTask.java) |
| `GuildTitleFormatter` | class | — | — | 4 | 59 | [GuildTitleFormatter.java](com/tensura_guild/data/GuildTitleFormatter.java) |
| `GuildAdvancementService` | class | — | — | 3 | 37 | [GuildAdvancementService.java](com/tensura_guild/data/GuildAdvancementService.java) |
| `SmartGrassProcessor` | class | StructureProcessor | — | 2 | 74 | [SmartGrassProcessor.java](com/tensura_guild/world/processor/SmartGrassProcessor.java) |
| `GroundLevelProcessor` | class | StructureProcessor | — | 2 | 38 | [GroundLevelProcessor.java](com/tensura_guild/world/processor/GroundLevelProcessor.java) |
| `WaterloggingFixProcessor` | class | StructureProcessor | — | 2 | 46 | [WaterloggingFixProcessor.java](com/tensura_guild/world/structure/WaterloggingFixProcessor.java) |
| `GuildStructureTypes` | class | — | — | 1 | 22 | [GuildStructureTypes.java](com/tensura_guild/registry/GuildStructureTypes.java) |
| `GuildProcessors` | class | — | — | 1 | 30 | [GuildProcessors.java](com/tensura_guild/registry/GuildProcessors.java) |
| `GuildAttachments` | class | — | — | 0 | 114 | [GuildAttachments.java](com/tensura_guild/registry/GuildAttachments.java) |
| `DwarfTaskMarkerPredicate` | interface | — | — | 0 | 9 | [DwarfTaskMarkerPredicate.java](com/tensura_guild/client/DwarfTaskMarkerPredicate.java) |
| `GuildStructures` | class | — | — | 2 | 20 | [GuildStructures.java](com/tensura_guild/init/GuildStructures.java) |
