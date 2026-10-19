# FTB XMod Compat（`ftbxmodcompat`）

> jar：`[FTB附属]ftb-xmod-compat.jar` ｜ 版本：`21.1.9` ｜ 作者：FTB Team
> 规模：**84 个类 / 33 个包 / 3300 行** ｜ 事件处理类 1 ｜ Mixin 2 ｜ 注册/管理类 8

**依赖**：`neoforge`、`minecraft`、`architectury`、`ftblibrary`、`ftbquests`、`ftbchunks`、`ftbranks`、`jei`、`kubejs`、`luckperms`、`waystones`、`sg_economy`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.kubejs` | 11 | `FTBQuestsKubeJSEvents`、`CustomFilterItemKubeEvent`、`QuestObjectCompletedKubeEvent` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.rei` | 8 | `LootCrateDisplayGenerator`、`LootCrateDisplayCategory`、`FTBQuestsREIIntegration` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common` | 7 | `BaseRecipeHelper`、`LootCrateTextRenderer`、`WrappedLootCrateCache` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.jei` | 6 | `LootCrateRecipeManagerPlugin`、`QuestRecipeManagerPlugin`、`LootCrateCategory` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbteams.kubejs` | 4 | `FTBTeamsKubeJSEvents`、`PlayerTeamKubeEvent`、`FTBTeamsKubeJSPlugin` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbchunks.kubejs` | 4 | `FTBChunksKubeJSEvents`、`AfterEventJS`、`BeforeEventJS` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbfiltersystem.kubejs` | 3 | `FFSEvents`、`CustomFilterKubeEvent`、`FFSKubeJSPlugin` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.filtering` | 3 | `ItemFiltersSetup`、`ItemFilteringSetup`、`FFSSetup` |
| `dev.ftb.mods.ftbxmodcompat.ftbchunks.waystones` | 3 | `WaystoneData`、`WaystonesCommon`、`WaystoneMapIcon` |
| `dev.ftb.mods.ftbxmodcompat.generic.gamestages.neoforge` | 3 | `KubeJSStageProvider`、`StagesSetupImpl`、`GameStagesStageProviderImpl` |
| `dev.ftb.mods.ftbxmodcompat.generic.permissions` | 3 | `LuckPermsProvider`、`FTBRanksProvider`、`PermissionsSetup` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.patchouli` | 2 | `PatchouliClient`、`PatchouliCompat` |
| `dev.ftb.mods.ftbxmodcompat.mixin` | 2 | `BookmarkListAccessor`、`BookmarkOverlayAccessor` |
| `dev.ftb.mods.ftbxmodcompat` | 2 | `ClientUtil`、`FTBXModCompat` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests` | 2 | `QuestItems`、`FTBQuestsSetup` |
| `dev.ftb.mods.ftbxmodcompat.generic.gamestages` | 2 | `GameStagesStageProvider`、`StagesSetup` |
| `dev.ftb.mods.ftbxmodcompat.generic.currency` | 2 | `SGEconomyProvider`、`CurrencySetup` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.jade` | 2 | `BarrierComponentProvider`、`JadeQuestsSetup` |
| `dev.ftb.mods.ftbxmodcompat.neoforge` | 1 | `FTBXModCompatNeoForge` |
| `dev.ftb.mods.ftbxmodcompat.ftbchunks.ftbranks` | 1 | `FTBRanksEventHandler` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.gamestages` | 1 | `GameStagesEventHandlerQuests` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.jei.helper` | 1 | `JEIRecipeHelper` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.rei.helper` | 1 | `REIRecipeHelper` |
| `dev.ftb.mods.ftbxmodcompat.config` | 1 | `FTBXModConfig` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.guideme` | 1 | `GuideMECompat` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.rei` | 1 | `FTBQuestsREIIntegrationForge` |
| `dev.ftb.mods.ftbxmodcompat.neoforge.ftbchunks.waystones` | 1 | `WaystonesCompat` |
| `dev.ftb.mods.ftbxmodcompat.ftbquests.jei.neoforge` | 1 | `FTBQuestsJEIIntegrationImpl` |
| `dev.ftb.mods.ftbxmodcompat.ftbchunks.ftbessentials` | 1 | `EssentialsCompat` |
| `dev.ftb.mods.ftbxmodcompat.kubejs` | 1 | `KJSUtil` |
| `dev.ftb.mods.ftbxmodcompat.ftbchunks` | 1 | `FTBChunksSetup` |
| `dev.ftb.mods.ftbxmodcompat.generic.jade` | 1 | `JadePlugin` |
| `architectury_inject_FTBXModCompat121_common_5c1600c4bbbd40d6ab1b319d1241e313_82001b48165594a59d43c5312eae7c177cd0e3da3ae0896e5d8c5cb7a0ff32e1ftbxmodcompat2119devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LootCrateRecipeManagerPlugin` | enum | IRecipeManagerPlugin | — | 5 | 71 | [LootCrateRecipeManagerPlugin.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/LootCrateRecipeManagerPlugin.java) |
| `FTBXModCompatNeoForge` | class | — | Mod | 0 | 31 | [FTBXModCompatNeoForge.java](dev/ftb/mods/ftbxmodcompat/neoforge/FTBXModCompatNeoForge.java) |
| `QuestRecipeManagerPlugin` | enum | IRecipeManagerPlugin | — | 4 | 54 | [QuestRecipeManagerPlugin.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/QuestRecipeManagerPlugin.java) |
| `FTBRanksEventHandler` | class | — | — | 8 | 69 | [FTBRanksEventHandler.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/ftbranks/FTBRanksEventHandler.java) |
| `FTBTeamsKubeJSEvents` | interface | — | — | 0 | 11 | [FTBTeamsKubeJSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbteams/kubejs/FTBTeamsKubeJSEvents.java) |
| `GameStagesEventHandlerQuests` | class | — | — | 1 | 7 | [GameStagesEventHandlerQuests.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/gamestages/GameStagesEventHandlerQuests.java) |
| `FTBQuestsKubeJSEvents` | interface | — | — | 0 | 21 | [FTBQuestsKubeJSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSEvents.java) |
| `FFSEvents` | interface | — | — | 0 | 13 | [FFSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbfiltersystem/kubejs/FFSEvents.java) |
| `FTBChunksKubeJSEvents` | interface | — | — | 0 | 12 | [FTBChunksKubeJSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/FTBChunksKubeJSEvents.java) |
| `PatchouliClient` | class | — | — | 1 | 45 | [PatchouliClient.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/patchouli/PatchouliClient.java) |
| `BookmarkListAccessor` | interface | — | Mixin | 0 | 13 | [BookmarkListAccessor.java](dev/ftb/mods/ftbxmodcompat/mixin/BookmarkListAccessor.java) |
| `BookmarkOverlayAccessor` | interface | — | Mixin | 0 | 13 | [BookmarkOverlayAccessor.java](dev/ftb/mods/ftbxmodcompat/mixin/BookmarkOverlayAccessor.java) |
| `BaseRecipeHelper` | class | RecipeModHelper | — | 5 | 32 | [BaseRecipeHelper.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/BaseRecipeHelper.java) |
| `JEIRecipeHelper` | class | BaseRecipeHelper | — | 6 | 59 | [JEIRecipeHelper.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/helper/JEIRecipeHelper.java) |
| `CustomFilterItemKubeEvent` | class | KubeEvent | — | 2 | 23 | [CustomFilterItemKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/CustomFilterItemKubeEvent.java) |
| `LootCrateTextRenderer` | class | — | — | 2 | 55 | [LootCrateTextRenderer.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/LootCrateTextRenderer.java) |
| `REIRecipeHelper` | class | BaseRecipeHelper | — | 4 | 28 | [REIRecipeHelper.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/helper/REIRecipeHelper.java) |
| `LootCrateCategory` | class | IRecipeCategory<WrappedLootCrate> | — | 10 | 108 | [LootCrateCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/LootCrateCategory.java) |
| `QuestObjectCompletedKubeEvent` | class | ServerKubeEvent | — | 5 | 42 | [QuestObjectCompletedKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/QuestObjectCompletedKubeEvent.java) |
| `QuestObjectStartedKubeEvent` | class | ServerKubeEvent | — | 5 | 42 | [QuestObjectStartedKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/QuestObjectStartedKubeEvent.java) |
| `CustomTaskKubeEvent` | class | KubeEvent | — | 5 | 35 | [CustomTaskKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/CustomTaskKubeEvent.java) |
| `WrappedLootCrateCache` | class | — | — | 6 | 71 | [WrappedLootCrateCache.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedLootCrateCache.java) |
| `LootCrateDisplayGenerator` | enum | DynamicDisplayGenerator<LootCrateDis | — | 5 | 54 | [LootCrateDisplayGenerator.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/LootCrateDisplayGenerator.java) |
| `LootCrateDisplayCategory` | class | DisplayCategory<LootCrateDisplay> | — | 6 | 77 | [LootCrateDisplayCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/LootCrateDisplayCategory.java) |
| `FTBQuestsJEIIntegration` | class | IModPlugin | JeiPlugin | 11 | 99 | [FTBQuestsJEIIntegration.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/FTBQuestsJEIIntegration.java) |
| `JEIRecipeTypes` | class | — | — | 0 | 11 | [JEIRecipeTypes.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/JEIRecipeTypes.java) |
| `FTBXModConfig` | interface | — | — | 3 | 87 | [FTBXModConfig.java](dev/ftb/mods/ftbxmodcompat/config/FTBXModConfig.java) |
| `ClientUtil` | class | — | — | 1 | 11 | [ClientUtil.java](dev/ftb/mods/ftbxmodcompat/ClientUtil.java) |
| `PlayerTeamKubeEvent` | class | KubePlayerEvent | — | 3 | 31 | [PlayerTeamKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbteams/kubejs/PlayerTeamKubeEvent.java) |
| `FTBQuestsKubeJSTeamData` | class | — | — | 14 | 91 | [FTBQuestsKubeJSTeamData.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSTeamData.java) |
| `CustomRewardKubeEvent` | class | KubePlayerEvent | — | 3 | 27 | [CustomRewardKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/CustomRewardKubeEvent.java) |
| `CustomFilterKubeEvent` | record | KubeEvent | — | 2 | 15 | [CustomFilterKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbfiltersystem/kubejs/CustomFilterKubeEvent.java) |
| `AfterEventJS` | class | KubeEntityEvent | — | 2 | 28 | [AfterEventJS.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/AfterEventJS.java) |
| `BeforeEventJS` | class | AfterEventJS | — | 3 | 27 | [BeforeEventJS.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/BeforeEventJS.java) |
| `WrappedLootCrate` | class | — | — | 2 | 72 | [WrappedLootCrate.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedLootCrate.java) |
| `FTBQuestsREIIntegration` | class | REIClientPlugin | — | 7 | 67 | [FTBQuestsREIIntegration.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/FTBQuestsREIIntegration.java) |
| `LootCrateDisplay` | class | BasicDisplay | — | 2 | 24 | [LootCrateDisplay.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/LootCrateDisplay.java) |
| `FTBXModCompat` | class | — | — | 3 | 73 | [FTBXModCompat.java](dev/ftb/mods/ftbxmodcompat/FTBXModCompat.java) |
| `GuideMECompat` | class | — | — | 1 | 18 | [GuideMECompat.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/guideme/GuideMECompat.java) |
| `FTBQuestsREIIntegrationForge` | class | FTBQuestsREIIntegration | REIPluginClient | 0 | 9 | [FTBQuestsREIIntegrationForge.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/rei/FTBQuestsREIIntegrationForge.java) |
| `PatchouliCompat` | class | — | — | 1 | 14 | [PatchouliCompat.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/patchouli/PatchouliCompat.java) |
| `WaystonesCompat` | class | — | — | 3 | 45 | [WaystonesCompat.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/waystones/WaystonesCompat.java) |
| `QuestItems` | class | — | — | 2 | 31 | [QuestItems.java](dev/ftb/mods/ftbxmodcompat/ftbquests/QuestItems.java) |
| `ItemStackToListCache` | class | Strategy<ItemStack> | — | 4 | 43 | [ItemStackToListCache.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/ItemStackToListCache.java) |
| `ItemFiltersSetup` | class | — | — | 1 | 10 | [ItemFiltersSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/filtering/ItemFiltersSetup.java) |
| `ItemFilteringSetup` | class | — | — | 1 | 16 | [ItemFilteringSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/filtering/ItemFilteringSetup.java) |
| `QuestCategory` | class | IRecipeCategory<WrappedQuest> | — | 10 | 102 | [QuestCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/QuestCategory.java) |
| `FTBQuestsJEIIntegrationImpl` | class | — | — | 1 | 19 | [FTBQuestsJEIIntegrationImpl.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/neoforge/FTBQuestsJEIIntegrationImpl.java) |
| `EssentialsCompat` | class | — | — | 2 | 22 | [EssentialsCompat.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/ftbessentials/EssentialsCompat.java) |
| `FTBQuestsKubeJSTeamDataWrapper` | class | FTBQuestsKubeJSTeamData | — | 2 | 23 | [FTBQuestsKubeJSTeamDataWrapper.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSTeamDataWrapper.java) |
| `FTBQuestsKubeJSPlugin` | class | KubeJSPlugin | — | 9 | 92 | [FTBQuestsKubeJSPlugin.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSPlugin.java) |
| `FTBQuestsKubeJSPlayerData` | class | FTBQuestsKubeJSTeamData | — | 2 | 25 | [FTBQuestsKubeJSPlayerData.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSPlayerData.java) |
| `FTBQuestsKubeJSWrapper` | class | — | — | 8 | 70 | [FTBQuestsKubeJSWrapper.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSWrapper.java) |
| `FTBChunksKubeJSPlugin` | class | KubeJSPlugin | — | 5 | 50 | [FTBChunksKubeJSPlugin.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/FTBChunksKubeJSPlugin.java) |
| `WrappedQuest` | class | — | — | 5 | 127 | [WrappedQuest.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedQuest.java) |
| `WrappedQuestCache` | class | — | — | 5 | 59 | [WrappedQuestCache.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedQuestCache.java) |
| `QuestDisplayCategory` | class | DisplayCategory<QuestDisplay> | — | 5 | 67 | [QuestDisplayCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/QuestDisplayCategory.java) |
| `FFSSetup` | class | ItemFilterAdapter | — | 7 | 59 | [FFSSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/filtering/FFSSetup.java) |
| `WaystoneData` | record | — | — | 0 | 8 | [WaystoneData.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/waystones/WaystoneData.java) |
| `KJSUtil` | class | — | — | 2 | 25 | [KJSUtil.java](dev/ftb/mods/ftbxmodcompat/kubejs/KJSUtil.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `PatchouliClient` | 1 | `dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.patchouli` | [PatchouliClient.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/patchouli/PatchouliClient.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BookmarkListAccessor` | `BookmarkListAccessor` | [BookmarkListAccessor.java](dev/ftb/mods/ftbxmodcompat/mixin/BookmarkListAccessor.java) |
| `BookmarkOverlayAccessor` | `BookmarkOverlayAccessor` | [BookmarkOverlayAccessor.java](dev/ftb/mods/ftbxmodcompat/mixin/BookmarkOverlayAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FTBXModConfig` | — | [FTBXModConfig.java](dev/ftb/mods/ftbxmodcompat/config/FTBXModConfig.java) |

## 全部类（84）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LootCrateRecipeManagerPlugin` | enum | IRecipeManagerPlugin | — | 5 | 71 | [LootCrateRecipeManagerPlugin.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/LootCrateRecipeManagerPlugin.java) |
| `FTBXModCompatNeoForge` | class | — | Mod | 0 | 31 | [FTBXModCompatNeoForge.java](dev/ftb/mods/ftbxmodcompat/neoforge/FTBXModCompatNeoForge.java) |
| `QuestRecipeManagerPlugin` | enum | IRecipeManagerPlugin | — | 4 | 54 | [QuestRecipeManagerPlugin.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/QuestRecipeManagerPlugin.java) |
| `FTBRanksEventHandler` | class | — | — | 8 | 69 | [FTBRanksEventHandler.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/ftbranks/FTBRanksEventHandler.java) |
| `FTBTeamsKubeJSEvents` | interface | — | — | 0 | 11 | [FTBTeamsKubeJSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbteams/kubejs/FTBTeamsKubeJSEvents.java) |
| `GameStagesEventHandlerQuests` | class | — | — | 1 | 7 | [GameStagesEventHandlerQuests.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/gamestages/GameStagesEventHandlerQuests.java) |
| `FTBQuestsKubeJSEvents` | interface | — | — | 0 | 21 | [FTBQuestsKubeJSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSEvents.java) |
| `FFSEvents` | interface | — | — | 0 | 13 | [FFSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbfiltersystem/kubejs/FFSEvents.java) |
| `FTBChunksKubeJSEvents` | interface | — | — | 0 | 12 | [FTBChunksKubeJSEvents.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/FTBChunksKubeJSEvents.java) |
| `PatchouliClient` | class | — | — | 1 | 45 | [PatchouliClient.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/patchouli/PatchouliClient.java) |
| `BookmarkListAccessor` | interface | — | Mixin | 0 | 13 | [BookmarkListAccessor.java](dev/ftb/mods/ftbxmodcompat/mixin/BookmarkListAccessor.java) |
| `BookmarkOverlayAccessor` | interface | — | Mixin | 0 | 13 | [BookmarkOverlayAccessor.java](dev/ftb/mods/ftbxmodcompat/mixin/BookmarkOverlayAccessor.java) |
| `BaseRecipeHelper` | class | RecipeModHelper | — | 5 | 32 | [BaseRecipeHelper.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/BaseRecipeHelper.java) |
| `JEIRecipeHelper` | class | BaseRecipeHelper | — | 6 | 59 | [JEIRecipeHelper.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/helper/JEIRecipeHelper.java) |
| `CustomFilterItemKubeEvent` | class | KubeEvent | — | 2 | 23 | [CustomFilterItemKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/CustomFilterItemKubeEvent.java) |
| `LootCrateTextRenderer` | class | — | — | 2 | 55 | [LootCrateTextRenderer.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/LootCrateTextRenderer.java) |
| `REIRecipeHelper` | class | BaseRecipeHelper | — | 4 | 28 | [REIRecipeHelper.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/helper/REIRecipeHelper.java) |
| `LootCrateCategory` | class | IRecipeCategory<WrappedLootCrate> | — | 10 | 108 | [LootCrateCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/LootCrateCategory.java) |
| `QuestObjectCompletedKubeEvent` | class | ServerKubeEvent | — | 5 | 42 | [QuestObjectCompletedKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/QuestObjectCompletedKubeEvent.java) |
| `QuestObjectStartedKubeEvent` | class | ServerKubeEvent | — | 5 | 42 | [QuestObjectStartedKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/QuestObjectStartedKubeEvent.java) |
| `CustomTaskKubeEvent` | class | KubeEvent | — | 5 | 35 | [CustomTaskKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/CustomTaskKubeEvent.java) |
| `WrappedLootCrateCache` | class | — | — | 6 | 71 | [WrappedLootCrateCache.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedLootCrateCache.java) |
| `LootCrateDisplayGenerator` | enum | DynamicDisplayGenerator<LootCrateDis | — | 5 | 54 | [LootCrateDisplayGenerator.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/LootCrateDisplayGenerator.java) |
| `LootCrateDisplayCategory` | class | DisplayCategory<LootCrateDisplay> | — | 6 | 77 | [LootCrateDisplayCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/LootCrateDisplayCategory.java) |
| `FTBQuestsJEIIntegration` | class | IModPlugin | JeiPlugin | 11 | 99 | [FTBQuestsJEIIntegration.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/FTBQuestsJEIIntegration.java) |
| `JEIRecipeTypes` | class | — | — | 0 | 11 | [JEIRecipeTypes.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/JEIRecipeTypes.java) |
| `FTBXModConfig` | interface | — | — | 3 | 87 | [FTBXModConfig.java](dev/ftb/mods/ftbxmodcompat/config/FTBXModConfig.java) |
| `ClientUtil` | class | — | — | 1 | 11 | [ClientUtil.java](dev/ftb/mods/ftbxmodcompat/ClientUtil.java) |
| `PlayerTeamKubeEvent` | class | KubePlayerEvent | — | 3 | 31 | [PlayerTeamKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbteams/kubejs/PlayerTeamKubeEvent.java) |
| `FTBQuestsKubeJSTeamData` | class | — | — | 14 | 91 | [FTBQuestsKubeJSTeamData.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSTeamData.java) |
| `CustomRewardKubeEvent` | class | KubePlayerEvent | — | 3 | 27 | [CustomRewardKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/CustomRewardKubeEvent.java) |
| `CustomFilterKubeEvent` | record | KubeEvent | — | 2 | 15 | [CustomFilterKubeEvent.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbfiltersystem/kubejs/CustomFilterKubeEvent.java) |
| `AfterEventJS` | class | KubeEntityEvent | — | 2 | 28 | [AfterEventJS.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/AfterEventJS.java) |
| `BeforeEventJS` | class | AfterEventJS | — | 3 | 27 | [BeforeEventJS.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/BeforeEventJS.java) |
| `WrappedLootCrate` | class | — | — | 2 | 72 | [WrappedLootCrate.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedLootCrate.java) |
| `FTBQuestsREIIntegration` | class | REIClientPlugin | — | 7 | 67 | [FTBQuestsREIIntegration.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/FTBQuestsREIIntegration.java) |
| `LootCrateDisplay` | class | BasicDisplay | — | 2 | 24 | [LootCrateDisplay.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/LootCrateDisplay.java) |
| `FTBXModCompat` | class | — | — | 3 | 73 | [FTBXModCompat.java](dev/ftb/mods/ftbxmodcompat/FTBXModCompat.java) |
| `GuideMECompat` | class | — | — | 1 | 18 | [GuideMECompat.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/guideme/GuideMECompat.java) |
| `FTBQuestsREIIntegrationForge` | class | FTBQuestsREIIntegration | REIPluginClient | 0 | 9 | [FTBQuestsREIIntegrationForge.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/rei/FTBQuestsREIIntegrationForge.java) |
| `PatchouliCompat` | class | — | — | 1 | 14 | [PatchouliCompat.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/patchouli/PatchouliCompat.java) |
| `WaystonesCompat` | class | — | — | 3 | 45 | [WaystonesCompat.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/waystones/WaystonesCompat.java) |
| `QuestItems` | class | — | — | 2 | 31 | [QuestItems.java](dev/ftb/mods/ftbxmodcompat/ftbquests/QuestItems.java) |
| `ItemStackToListCache` | class | Strategy<ItemStack> | — | 4 | 43 | [ItemStackToListCache.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/ItemStackToListCache.java) |
| `ItemFiltersSetup` | class | — | — | 1 | 10 | [ItemFiltersSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/filtering/ItemFiltersSetup.java) |
| `ItemFilteringSetup` | class | — | — | 1 | 16 | [ItemFilteringSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/filtering/ItemFilteringSetup.java) |
| `QuestCategory` | class | IRecipeCategory<WrappedQuest> | — | 10 | 102 | [QuestCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/QuestCategory.java) |
| `FTBQuestsJEIIntegrationImpl` | class | — | — | 1 | 19 | [FTBQuestsJEIIntegrationImpl.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jei/neoforge/FTBQuestsJEIIntegrationImpl.java) |
| `EssentialsCompat` | class | — | — | 2 | 22 | [EssentialsCompat.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/ftbessentials/EssentialsCompat.java) |
| `FTBQuestsKubeJSTeamDataWrapper` | class | FTBQuestsKubeJSTeamData | — | 2 | 23 | [FTBQuestsKubeJSTeamDataWrapper.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSTeamDataWrapper.java) |
| `FTBQuestsKubeJSPlugin` | class | KubeJSPlugin | — | 9 | 92 | [FTBQuestsKubeJSPlugin.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSPlugin.java) |
| `FTBQuestsKubeJSPlayerData` | class | FTBQuestsKubeJSTeamData | — | 2 | 25 | [FTBQuestsKubeJSPlayerData.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSPlayerData.java) |
| `FTBQuestsKubeJSWrapper` | class | — | — | 8 | 70 | [FTBQuestsKubeJSWrapper.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbquests/kubejs/FTBQuestsKubeJSWrapper.java) |
| `FTBChunksKubeJSPlugin` | class | KubeJSPlugin | — | 5 | 50 | [FTBChunksKubeJSPlugin.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbchunks/kubejs/FTBChunksKubeJSPlugin.java) |
| `WrappedQuest` | class | — | — | 5 | 127 | [WrappedQuest.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedQuest.java) |
| `WrappedQuestCache` | class | — | — | 5 | 59 | [WrappedQuestCache.java](dev/ftb/mods/ftbxmodcompat/ftbquests/recipemod_common/WrappedQuestCache.java) |
| `QuestDisplayCategory` | class | DisplayCategory<QuestDisplay> | — | 5 | 67 | [QuestDisplayCategory.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/QuestDisplayCategory.java) |
| `FFSSetup` | class | ItemFilterAdapter | — | 7 | 59 | [FFSSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/filtering/FFSSetup.java) |
| `WaystoneData` | record | — | — | 0 | 8 | [WaystoneData.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/waystones/WaystoneData.java) |
| `KJSUtil` | class | — | — | 2 | 25 | [KJSUtil.java](dev/ftb/mods/ftbxmodcompat/kubejs/KJSUtil.java) |
| `GameStagesStageProvider` | class | StageProvider | — | 9 | 55 | [GameStagesStageProvider.java](dev/ftb/mods/ftbxmodcompat/generic/gamestages/GameStagesStageProvider.java) |
| `KubeJSStageProvider` | class | StageProvider | — | 5 | 35 | [KubeJSStageProvider.java](dev/ftb/mods/ftbxmodcompat/generic/gamestages/neoforge/KubeJSStageProvider.java) |
| `SGEconomyProvider` | enum | CurrencyProvider | — | 5 | 40 | [SGEconomyProvider.java](dev/ftb/mods/ftbxmodcompat/generic/currency/SGEconomyProvider.java) |
| `LuckPermsProvider` | class | PermissionProvider | — | 5 | 48 | [LuckPermsProvider.java](dev/ftb/mods/ftbxmodcompat/generic/permissions/LuckPermsProvider.java) |
| `FTBTeamsKubeJSPlugin` | class | KubeJSPlugin | — | 4 | 28 | [FTBTeamsKubeJSPlugin.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbteams/kubejs/FTBTeamsKubeJSPlugin.java) |
| `KJSTeamWrapper` | class | — | — | 3 | 25 | [KJSTeamWrapper.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbteams/kubejs/KJSTeamWrapper.java) |
| `FFSKubeJSPlugin` | class | KubeJSPlugin | — | 3 | 24 | [FFSKubeJSPlugin.java](dev/ftb/mods/ftbxmodcompat/neoforge/ftbfiltersystem/kubejs/FFSKubeJSPlugin.java) |
| `FTBQuestsSetup` | class | — | — | 1 | 21 | [FTBQuestsSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/FTBQuestsSetup.java) |
| `QuestDisplay` | class | BasicDisplay | — | 2 | 24 | [QuestDisplay.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/QuestDisplay.java) |
| `QuestDisplayGenerator` | enum | DynamicDisplayGenerator<QuestDisplay | — | 4 | 43 | [QuestDisplayGenerator.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/QuestDisplayGenerator.java) |
| `REICategories` | class | — | — | 0 | 9 | [REICategories.java](dev/ftb/mods/ftbxmodcompat/ftbquests/rei/REICategories.java) |
| `BarrierComponentProvider` | class | IComponentProvider<BlockAccessor> | — | 3 | 31 | [BarrierComponentProvider.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jade/BarrierComponentProvider.java) |
| `JadeQuestsSetup` | class | — | — | 1 | 31 | [JadeQuestsSetup.java](dev/ftb/mods/ftbxmodcompat/ftbquests/jade/JadeQuestsSetup.java) |
| `FTBChunksSetup` | class | — | — | 1 | 23 | [FTBChunksSetup.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/FTBChunksSetup.java) |
| `WaystonesCommon` | class | — | — | 4 | 39 | [WaystonesCommon.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/waystones/WaystonesCommon.java) |
| `WaystoneMapIcon` | class | StaticMapIcon | — | 2 | 37 | [WaystoneMapIcon.java](dev/ftb/mods/ftbxmodcompat/ftbchunks/waystones/WaystoneMapIcon.java) |
| `StagesSetup` | class | — | — | 2 | 42 | [StagesSetup.java](dev/ftb/mods/ftbxmodcompat/generic/gamestages/StagesSetup.java) |
| `StagesSetupImpl` | class | — | — | 1 | 10 | [StagesSetupImpl.java](dev/ftb/mods/ftbxmodcompat/generic/gamestages/neoforge/StagesSetupImpl.java) |
| `GameStagesStageProviderImpl` | class | — | — | 4 | 30 | [GameStagesStageProviderImpl.java](dev/ftb/mods/ftbxmodcompat/generic/gamestages/neoforge/GameStagesStageProviderImpl.java) |
| `CurrencySetup` | class | — | — | 1 | 32 | [CurrencySetup.java](dev/ftb/mods/ftbxmodcompat/generic/currency/CurrencySetup.java) |
| `JadePlugin` | class | IWailaPlugin | WailaPlugin | 1 | 17 | [JadePlugin.java](dev/ftb/mods/ftbxmodcompat/generic/jade/JadePlugin.java) |
| `FTBRanksProvider` | class | PermissionProvider | — | 4 | 24 | [FTBRanksProvider.java](dev/ftb/mods/ftbxmodcompat/generic/permissions/FTBRanksProvider.java) |
| `PermissionsSetup` | class | — | — | 2 | 41 | [PermissionsSetup.java](dev/ftb/mods/ftbxmodcompat/generic/permissions/PermissionsSetup.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_FTBXModCompat121_common_5c1600c4bbbd40d6ab1b319d1241e313_82001b48165594a59d43c5312eae7c177cd0e3da3ae0896e5d8c5cb7a0ff32e1ftbxmodcompat2119devjar/PlatformMethods.java) |
