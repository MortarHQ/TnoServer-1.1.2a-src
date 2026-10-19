# SlimeThroneExtras（`stextras`）

> jar：`[转生附属]SlimeThroneExtras.jar` ｜ 版本：`2.1.2.1` ｜ 作者：madtrent
> 规模：**192 个类 / 84 个包 / 22308 行** ｜ 事件处理类 0 ｜ Mixin 40 ｜ 注册/管理类 7

**依赖**：`neoforge`、`minecraft`、`architectury`、`tensura`、`ftbquests`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `org.crypticdev.stextras.quest.definition` | 10 | `QuestRewardItem`、`TensuraQuestTargetIds`、`TensuraMasteryTypeIds` |
| `org.crypticdev.stextras.mixin.world.inventory` | 8 | `ItemCombinerMenuMixin`、`EnchantmentMenuMixin`、`AnvilMenuMixin` |
| `org.crypticdev.stextras.integration.FTB.Quests.task` | 7 | `AbstractSTExtrasTask`、`RacePrestigeTask`、`STExtrasQuestCompleteTask` |
| `org.crypticdev.stextras.config` | 6 | `TensuraConfigPatcher`、`STGeneralConfig`、`STSkillLockConfig` |
| `org.crypticdev.stextras.quest.assignment` | 6 | `RacePrestigeAssignmentService`、`ScheduledQuestAssignmentService`、`PlayerQuestAssignmentService` |
| `org.crypticdev.stextras.mixin.tensura.menu` | 5 | `MiningStationMenuMixin`、`SmithingBenchMenuMixin`、`ReincarnationMenuMixin` |
| `org.crypticdev.stextras.race.kobold` | 5 | `KoboldChieftainRace`、`KoboldWarriorRace`、`KoboldRace` |
| `org.crypticdev.stextras.quest.schedule` | 4 | `QuestScheduleEvents`、`QuestScheduleService`、`QuestScheduleSavedData` |
| `org.crypticdev.stextras.utils` | 4 | `EntityTagUtil`、`VersionControl`、`PrestigeUtils` |
| `org.crypticdev.stextras.registary` | 4 | `STExtraRegistry`、`STExtrasLootTables`、`STExtrasCommands` |
| `org.crypticdev.stextras.integration.FTB.Quests` | 4 | `FTBQuestsEventBridge`、`FTBQuestsIntegration`、`STExtrasTaskTypes` |
| `org.crypticdev.stextras.net.C2S` | 4 | `OpenPrestigeScreenPacket`、`OpenSkillLockScreenPacket`、`SkillLockPacket` |
| `org.crypticdev.stextras.race.wispfolk` | 4 | `WispfolkRace`、`ZephyrWispRace`、`GaleWispRace` |
| `org.crypticdev.stextras.race.mystic_fox` | 4 | `MysticFoxRace`、`SoulBeastRace`、`DivineFoxRace` |
| `org.crypticdev.stextras.integration.FTB.Quests.reward` | 4 | `AbstractAmountReward`、`RequiredQuestRerollReward`、`SoulGradePointsReward` |
| `org.crypticdev.stextras.event` | 3 | `STPlayerEvents`、`MenuOutputTakeTracker`、`SkillInteractionSupport` |
| `org.crypticdev.stextras.config.race` | 3 | `KoboldConfig`、`WispfolkConfig`、`MysticFoxConfig` |
| `org.crypticdev.stextras.handler` | 3 | `HandlePrestigeListeners`、`HandleSkillLock`、`ServerHooks` |
| `org.crypticdev.stextras.command.quest` | 3 | `QuestCommands`、`RacePrestigeCommands`、`RequiredCommands` |
| `org.crypticdev.stextras.client.screen` | 3 | `STPrestigeScreens`、`STSkillLockScreen`、`ImitatorScreen` |
| `org.crypticdev.stextras.mixin.tensura.client.screen` | 3 | `MainScreenMixin`、`ScreenHelperMixin`、`AbilityCategoriesScreenMixin` |
| `org.crypticdev.stextras.mixin.access` | 3 | `ScreenInvoker`、`SimpleScreenAccessor`、`ScreenAccessor` |
| `org.crypticdev.stextras.command.system` | 3 | `ScheduleCommands`、`DebugCommands`、`WikiCommands` |
| `org.crypticdev.stextras.net` | 3 | `STNetworkCompat`、`STNetworkGuards`、`STExtrasQuestSync` |
| `org.crypticdev.stextras.quest.codec` | 3 | `QuestDefinitionJsonParser`、`QuestDefinitionNbtCodec`、`QuestDefinitionValidator` |
| `org.crypticdev.stextras.config.ability.skill` | 3 | `STExtrasUniqueSkillConfig`、`STExtrasExtraSkillConfig`、`STExtrasIntrinsicSkillConfig` |
| `org.crypticdev.stextras.net.S2C` | 3 | `SyncQuestDefinitionsPacket`、`STExtrasPlayerStorageSync`、`STExtrasBossKillsSync` |
| `org.crypticdev.stextras.support` | 3 | `LoadingOverlayHooks`、`WispRadarHelper`、`PlayerPhasingHooks` |
| `org.crypticdev.stextras.client.screen.skilllock` | 3 | `STSkillLockRules`、`STSkillLockTooltips`、`STSkillLockFilters` |
| `org.crypticdev.stextras.quest.race` | 3 | `RaceMessages`、`RacePrestigeMetadata`、`RacePrestigeText` |
| `org.crypticdev.stextras.registary.skill` | 3 | `STExtrasExtraSkills`、`STExtrasUniqueSkills`、`STExtrasIntrinsicSkills` |
| `org.crypticdev.stextras.neoforge` | 2 | `StextrasNeoForge`、`StextrasNeoForgeClient` |
| `org.crypticdev.stextras.mixin.client` | 2 | `ScreenEffectRendererMixin`、`GameRendererMixin` |
| `org.crypticdev.stextras.storage.quest` | 2 | `STExtrasQuestStorage`、`ISTExtrasQuests` |
| `org.crypticdev.stextras.client.screen.prestige` | 2 | `STPrestigeQuestFormatter`、`STPrestigeProgress` |
| `org.crypticdev.stextras.quest.registry` | 2 | `QuestRegistry`、`RacePrestigeMetadataRegistry` |
| `org.crypticdev.stextras.mixin.tensura.race` | 2 | `RaceHelperMixin`、`DwarfRaceMixin` |
| `org.crypticdev.stextras.command.support` | 2 | `CommandSupport`、`CommandText` |
| `org.crypticdev.stextras.integration.lootr` | 2 | `LootrIntegration`、`CharybdisLootProcessor` |
| `org.crypticdev.stextras.client.loadingScreen.texture` | 2 | `GifTextureRenderer`、`FileBasedTexture` |
| … | | 其余 44 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `STPlayerEvents` | class | — | — | 23 | 270 | [STPlayerEvents.java](org/crypticdev/stextras/event/STPlayerEvents.java) |
| `KoboldConfig` | class | ManasConfig | SyncToClient | 65 | 419 | [KoboldConfig.java](org/crypticdev/stextras/config/race/KoboldConfig.java) |
| `HandlePrestigeListeners` | class | — | — | 59 | 1150 | [HandlePrestigeListeners.java](org/crypticdev/stextras/handler/HandlePrestigeListeners.java) |
| `DeathHandlerMixin` | class | — | Mixin | 1 | 53 | [DeathHandlerMixin.java](org/crypticdev/stextras/mixin/tensura/handler/DeathHandlerMixin.java) |
| `WispfolkConfig` | class | ManasConfig | SyncToClient | 52 | 344 | [WispfolkConfig.java](org/crypticdev/stextras/config/race/WispfolkConfig.java) |
| `MysticFoxConfig` | class | ManasConfig | SyncToClient | 53 | 343 | [MysticFoxConfig.java](org/crypticdev/stextras/config/race/MysticFoxConfig.java) |
| `QuestCommands` | class | — | — | 37 | 541 | [QuestCommands.java](org/crypticdev/stextras/command/quest/QuestCommands.java) |
| `STPrestigeScreens` | class | SimpleScreen | — | 48 | 775 | [STPrestigeScreens.java](org/crypticdev/stextras/client/screen/STPrestigeScreens.java) |
| `StextrasNeoForge` | class | — | Mod | 2 | 35 | [StextrasNeoForge.java](org/crypticdev/stextras/neoforge/StextrasNeoForge.java) |
| `EventFactoryEventImplMixin` | class | Event<T> | Mixin | 6 | 65 | [EventFactoryEventImplMixin.java](org/crypticdev/stextras/mixin/architectury/event/EventFactoryEventImplMixin.java) |
| `ItemCombinerMenuMixin` | class | — | Mixin | 0 | 31 | [ItemCombinerMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/ItemCombinerMenuMixin.java) |
| `RequestSpellbindingPacketMixin` | class | — | Mixin | 0 | 208 | [RequestSpellbindingPacketMixin.java](org/crypticdev/stextras/mixin/tensura/network/RequestSpellbindingPacketMixin.java) |
| `ScreenEffectRendererMixin` | class | — | Mixin | 0 | 48 | [ScreenEffectRendererMixin.java](org/crypticdev/stextras/mixin/client/ScreenEffectRendererMixin.java) |
| `STExtrasQuestStorage` | class | — | — | 47 | 823 | [STExtrasQuestStorage.java](org/crypticdev/stextras/storage/quest/STExtrasQuestStorage.java) |
| `PlayerCommands` | class | — | — | 28 | 433 | [PlayerCommands.java](org/crypticdev/stextras/command/player/PlayerCommands.java) |
| `STPrestigeQuestFormatter` | class | — | — | 47 | 830 | [STPrestigeQuestFormatter.java](org/crypticdev/stextras/client/screen/prestige/STPrestigeQuestFormatter.java) |
| `QuestRegistry` | class | SimpleJsonResourceReloadListener | — | 18 | 209 | [QuestRegistry.java](org/crypticdev/stextras/quest/registry/QuestRegistry.java) |
| `GameRuleCommandMixin` | class | — | Mixin | 1 | 49 | [GameRuleCommandMixin.java](org/crypticdev/stextras/mixin/server/commands/GameRuleCommandMixin.java) |
| `MainScreenMixin` | class | — | Mixin | 0 | 299 | [MainScreenMixin.java](org/crypticdev/stextras/mixin/tensura/client/screen/MainScreenMixin.java) |
| `ScreenHelperMixin` | class | — | Mixin | 0 | 22 | [ScreenHelperMixin.java](org/crypticdev/stextras/mixin/tensura/client/screen/ScreenHelperMixin.java) |
| `STExtarsStorage` | class | — | — | 83 | 609 | [STExtarsStorage.java](org/crypticdev/stextras/storage/STExtarsStorage.java) |
| `STSkillLockScreen` | class | SimpleScreen | — | 28 | 543 | [STSkillLockScreen.java](org/crypticdev/stextras/client/screen/STSkillLockScreen.java) |
| `ServerPlayerMixin` | class | ISTExtrasPlayer | Mixin | 12 | 140 | [ServerPlayerMixin.java](org/crypticdev/stextras/mixin/server/level/ServerPlayerMixin.java) |
| `EnchantmentMenuMixin` | class | — | Mixin | 0 | 36 | [EnchantmentMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/EnchantmentMenuMixin.java) |
| `AnvilMenuMixin` | class | — | Mixin | 0 | 25 | [AnvilMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/AnvilMenuMixin.java) |
| `SmithingMenuMixin` | class | — | Mixin | 0 | 22 | [SmithingMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/SmithingMenuMixin.java) |
| `ItemMixin` | class | — | Mixin | 0 | 27 | [ItemMixin.java](org/crypticdev/stextras/mixin/world/item/ItemMixin.java) |
| `MiningStationMenuMixin` | class | — | Mixin | 0 | 41 | [MiningStationMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/MiningStationMenuMixin.java) |
| `SmithingBenchMenuMixin` | class | — | Mixin | 0 | 27 | [SmithingBenchMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/SmithingBenchMenuMixin.java) |
| `ReincarnationMenuMixin` | class | — | Mixin | 2 | 105 | [ReincarnationMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/ReincarnationMenuMixin.java) |
| `SmithingBenchMenu` | class | — | Mixin | 0 | 21 | [SmithingBenchMenu$1Mixin.java](org/crypticdev/stextras/mixin/tensura/menu/SmithingBenchMenu$1Mixin.java) |
| `WoodcutterMenuMixin` | class | — | Mixin | 0 | 41 | [WoodcutterMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/WoodcutterMenuMixin.java) |
| `ResetScrollItemMixin` | class | — | Mixin | 1 | 45 | [ResetScrollItemMixin.java](org/crypticdev/stextras/mixin/tensura/item/misc/ResetScrollItemMixin.java) |
| `AbilityCategoriesScreenMixin` | class | — | Mixin | 0 | 125 | [AbilityCategoriesScreenMixin.java](org/crypticdev/stextras/mixin/tensura/client/screen/AbilityCategoriesScreenMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 0 | 43 | [GameRendererMixin.java](org/crypticdev/stextras/mixin/client/GameRendererMixin.java) |
| `ScreenInvoker` | interface | — | Mixin | 0 | 15 | [ScreenInvoker.java](org/crypticdev/stextras/mixin/access/ScreenInvoker.java) |
| `SimpleScreenAccessor` | interface | — | Mixin | 0 | 15 | [SimpleScreenAccessor.java](org/crypticdev/stextras/mixin/access/SimpleScreenAccessor.java) |
| `ScreenAccessor` | interface | — | Mixin | 0 | 14 | [ScreenAccessor.java](org/crypticdev/stextras/mixin/access/ScreenAccessor.java) |
| `GifDecoder` | class | — | — | 25 | 621 | [GifDecoder.java](org/crypticdev/stextras/client/loadingScreen/util/GifDecoder.java) |
| `RacePrestigeMetadataRegistry` | class | — | — | 8 | 62 | [RacePrestigeMetadataRegistry.java](org/crypticdev/stextras/quest/registry/RacePrestigeMetadataRegistry.java) |
| `QuestScheduleEvents` | class | — | — | 0 | 18 | [QuestScheduleEvents.java](org/crypticdev/stextras/quest/schedule/QuestScheduleEvents.java) |
| `STExtrasSoundEvents` | class | — | — | 2 | 22 | [STExtrasSoundEvents.java](org/crypticdev/stextras/registary/sounds/STExtrasSoundEvents.java) |
| `SpawnPointHelperMixin` | class | — | Mixin | 0 | 24 | [SpawnPointHelperMixin.java](org/crypticdev/stextras/mixin/manascore/race/impl/SpawnPointHelperMixin.java) |
| `RaceHelperMixin` | class | — | Mixin | 1 | 22 | [RaceHelperMixin.java](org/crypticdev/stextras/mixin/tensura/race/RaceHelperMixin.java) |
| `SkillUtilsMixin` | class | — | Mixin | 0 | 20 | [SkillUtilsMixin.java](org/crypticdev/stextras/mixin/tensura/ability/SkillUtilsMixin.java) |
| `LocalPlayerMixin` | class | ClientSTExtrasPlayerStorage | Mixin | 8 | 78 | [LocalPlayerMixin.java](org/crypticdev/stextras/mixin/client/player/LocalPlayerMixin.java) |
| `CommandSupport` | class | — | — | 22 | 127 | [CommandSupport.java](org/crypticdev/stextras/command/support/CommandSupport.java) |
| `STExtrasClientConfig` | class | — | — | 9 | 115 | [STExtrasClientConfig.java](org/crypticdev/stextras/client/config/STExtrasClientConfig.java) |
| `PlayerMixin` | class | — | Mixin | 0 | 16 | [PlayerMixin.java](org/crypticdev/stextras/neoforge/mixin/world/entity/player/PlayerMixin.java) |
| `LoadingOverlayMixin` | class | LoadingOverlayHooks.Access | Mixin | 0 | 111 | [LoadingOverlayMixin.java](org/crypticdev/stextras/neoforge/mixin/client/gui/screen/LoadingOverlayMixin.java) |
| `MerchantResultSlotMixin` | class | — | Mixin | 0 | 42 | [MerchantResultSlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/MerchantResultSlotMixin.java) |
| `SlotMixin` | class | — | Mixin | 0 | 33 | [SlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/SlotMixin.java) |
| `ResultSlotMixin` | class | — | Mixin | 0 | 39 | [ResultSlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/ResultSlotMixin.java) |
| `FurnaceResultSlotMixin` | class | — | Mixin | 0 | 35 | [FurnaceResultSlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/FurnaceResultSlotMixin.java) |
| `AnimalMixin` | class | — | Mixin | 0 | 34 | [AnimalMixin.java](org/crypticdev/stextras/mixin/world/inventory/entity/animal/AnimalMixin.java) |
| `GameRulesMixin` | class | — | Mixin | 0 | 21 | [GameRulesMixin.java](org/crypticdev/stextras/mixin/world/level/GameRulesMixin.java) |
| `ManasSkillInstanceMixin` | class | — | Mixin | 0 | 144 | [ManasSkillInstanceMixin.java](org/crypticdev/stextras/mixin/manascore/skill/api/ManasSkillInstanceMixin.java) |
| `TensuraMixin` | class | — | Mixin | 0 | 22 | [TensuraMixin.java](org/crypticdev/stextras/mixin/tensura/TensuraMixin.java) |
| `DwarfRaceMixin` | class | — | Mixin | 1 | 22 | [DwarfRaceMixin.java](org/crypticdev/stextras/mixin/tensura/race/DwarfRaceMixin.java) |
| `TensuraPlayerStorageMixin` | class | — | Mixin | 0 | 64 | [TensuraPlayerStorageMixin.java](org/crypticdev/stextras/mixin/tensura/storage/player/TensuraPlayerStorageMixin.java) |

## Mixin（40 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `DeathHandlerMixin` | `DeathHandler` | [DeathHandlerMixin.java](org/crypticdev/stextras/mixin/tensura/handler/DeathHandlerMixin.java) |
| `EventFactoryEventImplMixin` | `EventFactoryEventImpl` | [EventFactoryEventImplMixin.java](org/crypticdev/stextras/mixin/architectury/event/EventFactoryEventImplMixin.java) |
| `ItemCombinerMenuMixin` | `ItemCombinerMenu` | [ItemCombinerMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/ItemCombinerMenuMixin.java) |
| `RequestSpellbindingPacketMixin` | `RequestSpellbindingPacket` | [RequestSpellbindingPacketMixin.java](org/crypticdev/stextras/mixin/tensura/network/RequestSpellbindingPacketMixin.java) |
| `ScreenEffectRendererMixin` | `ScreenEffectRenderer` | [ScreenEffectRendererMixin.java](org/crypticdev/stextras/mixin/client/ScreenEffectRendererMixin.java) |
| `GameRuleCommandMixin` | `GameRuleCommand` | [GameRuleCommandMixin.java](org/crypticdev/stextras/mixin/server/commands/GameRuleCommandMixin.java) |
| `MainScreenMixin` | `MainScreen` | [MainScreenMixin.java](org/crypticdev/stextras/mixin/tensura/client/screen/MainScreenMixin.java) |
| `ScreenHelperMixin` | `ScreenHelper` | [ScreenHelperMixin.java](org/crypticdev/stextras/mixin/tensura/client/screen/ScreenHelperMixin.java) |
| `ServerPlayerMixin` | `ServerPlayer` | [ServerPlayerMixin.java](org/crypticdev/stextras/mixin/server/level/ServerPlayerMixin.java) |
| `EnchantmentMenuMixin` | `EnchantmentMenu` | [EnchantmentMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/EnchantmentMenuMixin.java) |
| `AnvilMenuMixin` | `AnvilMenu` | [AnvilMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/AnvilMenuMixin.java) |
| `SmithingMenuMixin` | `SmithingMenu` | [SmithingMenuMixin.java](org/crypticdev/stextras/mixin/world/inventory/SmithingMenuMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](org/crypticdev/stextras/mixin/world/item/ItemMixin.java) |
| `MiningStationMenuMixin` | `MiningStationMenu` | [MiningStationMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/MiningStationMenuMixin.java) |
| `SmithingBenchMenuMixin` | `SmithingBenchMenu` | [SmithingBenchMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/SmithingBenchMenuMixin.java) |
| `ReincarnationMenuMixin` | `ReincarnationMenu` | [ReincarnationMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/ReincarnationMenuMixin.java) |
| `SmithingBenchMenu` | `SmithingBenchMenu` | [SmithingBenchMenu$1Mixin.java](org/crypticdev/stextras/mixin/tensura/menu/SmithingBenchMenu$1Mixin.java) |
| `WoodcutterMenuMixin` | `WoodcutterMenu` | [WoodcutterMenuMixin.java](org/crypticdev/stextras/mixin/tensura/menu/WoodcutterMenuMixin.java) |
| `ResetScrollItemMixin` | `ResetScrollItem` | [ResetScrollItemMixin.java](org/crypticdev/stextras/mixin/tensura/item/misc/ResetScrollItemMixin.java) |
| `AbilityCategoriesScreenMixin` | `AbilityCategoriesScreen` | [AbilityCategoriesScreenMixin.java](org/crypticdev/stextras/mixin/tensura/client/screen/AbilityCategoriesScreenMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](org/crypticdev/stextras/mixin/client/GameRendererMixin.java) |
| `ScreenInvoker` | `ScreenInvoker` | [ScreenInvoker.java](org/crypticdev/stextras/mixin/access/ScreenInvoker.java) |
| `SimpleScreenAccessor` | `SimpleScreenAccessor` | [SimpleScreenAccessor.java](org/crypticdev/stextras/mixin/access/SimpleScreenAccessor.java) |
| `ScreenAccessor` | `ScreenAccessor` | [ScreenAccessor.java](org/crypticdev/stextras/mixin/access/ScreenAccessor.java) |
| `SpawnPointHelperMixin` | `SpawnPointHelper` | [SpawnPointHelperMixin.java](org/crypticdev/stextras/mixin/manascore/race/impl/SpawnPointHelperMixin.java) |
| `RaceHelperMixin` | `RaceHelper` | [RaceHelperMixin.java](org/crypticdev/stextras/mixin/tensura/race/RaceHelperMixin.java) |
| `SkillUtilsMixin` | `SkillUtils` | [SkillUtilsMixin.java](org/crypticdev/stextras/mixin/tensura/ability/SkillUtilsMixin.java) |
| `LocalPlayerMixin` | `LocalPlayer` | [LocalPlayerMixin.java](org/crypticdev/stextras/mixin/client/player/LocalPlayerMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](org/crypticdev/stextras/neoforge/mixin/world/entity/player/PlayerMixin.java) |
| `LoadingOverlayMixin` | `LoadingOverlay` | [LoadingOverlayMixin.java](org/crypticdev/stextras/neoforge/mixin/client/gui/screen/LoadingOverlayMixin.java) |
| `MerchantResultSlotMixin` | `MerchantResultSlot` | [MerchantResultSlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/MerchantResultSlotMixin.java) |
| `SlotMixin` | `Slot` | [SlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/SlotMixin.java) |
| `ResultSlotMixin` | `ResultSlot` | [ResultSlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/ResultSlotMixin.java) |
| `FurnaceResultSlotMixin` | `FurnaceResultSlot` | [FurnaceResultSlotMixin.java](org/crypticdev/stextras/mixin/world/inventory/FurnaceResultSlotMixin.java) |
| `AnimalMixin` | `Animal` | [AnimalMixin.java](org/crypticdev/stextras/mixin/world/inventory/entity/animal/AnimalMixin.java) |
| `GameRulesMixin` | `GameRules` | [GameRulesMixin.java](org/crypticdev/stextras/mixin/world/level/GameRulesMixin.java) |
| `ManasSkillInstanceMixin` | `ManasSkillInstance` | [ManasSkillInstanceMixin.java](org/crypticdev/stextras/mixin/manascore/skill/api/ManasSkillInstanceMixin.java) |
| `TensuraMixin` | `Tensura` | [TensuraMixin.java](org/crypticdev/stextras/mixin/tensura/TensuraMixin.java) |
| `DwarfRaceMixin` | `DwarfRace` | [DwarfRaceMixin.java](org/crypticdev/stextras/mixin/tensura/race/DwarfRaceMixin.java) |
| `TensuraPlayerStorageMixin` | `TensuraPlayerStorage` | [TensuraPlayerStorageMixin.java](org/crypticdev/stextras/mixin/tensura/storage/player/TensuraPlayerStorageMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `KoboldConfig` | SyncToClient | [KoboldConfig.java](org/crypticdev/stextras/config/race/KoboldConfig.java) |
| `WispfolkConfig` | SyncToClient | [WispfolkConfig.java](org/crypticdev/stextras/config/race/WispfolkConfig.java) |
| `MysticFoxConfig` | SyncToClient | [MysticFoxConfig.java](org/crypticdev/stextras/config/race/MysticFoxConfig.java) |
| `STExtrasClientConfig` | — | [STExtrasClientConfig.java](org/crypticdev/stextras/client/config/STExtrasClientConfig.java) |
| `TensuraConfigPatcher` | — | [TensuraConfigPatcher.java](org/crypticdev/stextras/config/TensuraConfigPatcher.java) |
| `STGeneralConfig` | — | [STGeneralConfig.java](org/crypticdev/stextras/config/STGeneralConfig.java) |
| `STSkillLockConfig` | SyncToClient | [STSkillLockConfig.java](org/crypticdev/stextras/config/STSkillLockConfig.java) |
| `STExtarsConfigs` | — | [STExtarsConfigs.java](org/crypticdev/stextras/config/STExtarsConfigs.java) |
| `STPrestigeConfig` | SyncToClient | [STPrestigeConfig.java](org/crypticdev/stextras/config/STPrestigeConfig.java) |
| `TensuraConfigPatcherStateConfig` | — | [TensuraConfigPatcherStateConfig.java](org/crypticdev/stextras/config/TensuraConfigPatcherStateConfig.java) |
| `STExtrasUniqueSkillConfig` | SyncToClient | [STExtrasUniqueSkillConfig.java](org/crypticdev/stextras/config/ability/skill/STExtrasUniqueSkillConfig.java) |
| `STExtrasExtraSkillConfig` | SyncToClient | [STExtrasExtraSkillConfig.java](org/crypticdev/stextras/config/ability/skill/STExtrasExtraSkillConfig.java) |
| `STExtrasIntrinsicSkillConfig` | — | [STExtrasIntrinsicSkillConfig.java](org/crypticdev/stextras/config/ability/skill/STExtrasIntrinsicSkillConfig.java) |

## 按包浏览

- `architectury_inject_stextras_common_b993a920e43b45688422b684ead64371_7e28cd5ef37e51d054c631796de92fd3e54ca6c82d057bfd953b012a282d3dfeSlimeThroneExtrascommon2121devjar`（1 类）
- `org.crypticdev.stextras`（1 类）
- `org.crypticdev.stextras.ability.skill.extra`（1 类）
- `org.crypticdev.stextras.ability.skill.intrinsic`（1 类）
- `org.crypticdev.stextras.ability.skill.subclass`（1 类）
- `org.crypticdev.stextras.ability.skill.unique`（1 类）
- `org.crypticdev.stextras.client`（1 类）
- `org.crypticdev.stextras.client.config`（1 类）
- `org.crypticdev.stextras.client.loadingScreen`（1 类）
- `org.crypticdev.stextras.client.loadingScreen.texture`（2 类）
- `org.crypticdev.stextras.client.loadingScreen.util`（1 类）
- `org.crypticdev.stextras.client.render`（1 类）
- `org.crypticdev.stextras.client.screen`（3 类）
- `org.crypticdev.stextras.client.screen.prestige`（2 类）
- `org.crypticdev.stextras.client.screen.skilllock`（3 类）
- `org.crypticdev.stextras.command`（1 类）
- `org.crypticdev.stextras.command.player`（1 类）
- `org.crypticdev.stextras.command.quest`（3 类）
- `org.crypticdev.stextras.command.support`（2 类）
- `org.crypticdev.stextras.command.system`（3 类）
- `org.crypticdev.stextras.config`（6 类）
- `org.crypticdev.stextras.config.ability.skill`（3 类）
- `org.crypticdev.stextras.config.race`（3 类）
- `org.crypticdev.stextras.effect.skill`（1 类）
- `org.crypticdev.stextras.event`（3 类）
- `org.crypticdev.stextras.handler`（3 类）
- `org.crypticdev.stextras.integration.FTB.Quests`（4 类）
- `org.crypticdev.stextras.integration.FTB.Quests.reward`（4 类）
- `org.crypticdev.stextras.integration.FTB.Quests.task`（7 类）
- `org.crypticdev.stextras.integration.OPAC`（2 类）
- `org.crypticdev.stextras.integration.lootr`（2 类）
- `org.crypticdev.stextras.menu`（1 类）
- `org.crypticdev.stextras.mixin`（1 类）
- `org.crypticdev.stextras.mixin.access`（3 类）
- `org.crypticdev.stextras.mixin.architectury.event`（1 类）
- `org.crypticdev.stextras.mixin.client`（2 类）
- `org.crypticdev.stextras.mixin.client.player`（1 类）
- `org.crypticdev.stextras.mixin.manascore.race.impl`（1 类）
- `org.crypticdev.stextras.mixin.manascore.skill.api`（1 类）
- `org.crypticdev.stextras.mixin.server.commands`（1 类）
- `org.crypticdev.stextras.mixin.server.level`（1 类）
- `org.crypticdev.stextras.mixin.tensura`（1 类）
- `org.crypticdev.stextras.mixin.tensura.ability`（1 类）
- `org.crypticdev.stextras.mixin.tensura.client.screen`（3 类）
- `org.crypticdev.stextras.mixin.tensura.handler`（1 类）
- `org.crypticdev.stextras.mixin.tensura.item.misc`（1 类）
- `org.crypticdev.stextras.mixin.tensura.menu`（5 类）
- `org.crypticdev.stextras.mixin.tensura.network`（1 类）
- `org.crypticdev.stextras.mixin.tensura.race`（2 类）
- `org.crypticdev.stextras.mixin.tensura.storage.player`（1 类）
- `org.crypticdev.stextras.mixin.world.inventory`（8 类）
- `org.crypticdev.stextras.mixin.world.inventory.entity.animal`（1 类）
- `org.crypticdev.stextras.mixin.world.item`（1 类）
- `org.crypticdev.stextras.mixin.world.level`（1 类）
- `org.crypticdev.stextras.neoforge`（2 类）
- `org.crypticdev.stextras.neoforge.mixin.client.gui.screen`（1 类）
- `org.crypticdev.stextras.neoforge.mixin.world.entity.player`（1 类）
- `org.crypticdev.stextras.net`（3 类）
- `org.crypticdev.stextras.net.C2S`（4 类）
- `org.crypticdev.stextras.net.S2C`（3 类）
- `org.crypticdev.stextras.quest.assignment`（6 类）
- `org.crypticdev.stextras.quest.codec`（3 类）
- `org.crypticdev.stextras.quest.definition`（10 类）
- `org.crypticdev.stextras.quest.race`（3 类）
- `org.crypticdev.stextras.quest.registry`（2 类）
- `org.crypticdev.stextras.quest.reward`（1 类）
- `org.crypticdev.stextras.quest.schedule`（4 类）
- `org.crypticdev.stextras.race.kobold`（5 类）
- `org.crypticdev.stextras.race.mystic_fox`（4 类）
- `org.crypticdev.stextras.race.wispfolk`（4 类）
- `org.crypticdev.stextras.registary`（4 类）
- `org.crypticdev.stextras.registary.effects`（1 类）
- `org.crypticdev.stextras.registary.item`（1 类）
- `org.crypticdev.stextras.registary.menu`（1 类）
- `org.crypticdev.stextras.registary.race`（1 类）
- `org.crypticdev.stextras.registary.skill`（3 类）
- `org.crypticdev.stextras.registary.sounds`（1 类）
- `org.crypticdev.stextras.storage`（1 类）
- `org.crypticdev.stextras.storage.player`（2 类）
- `org.crypticdev.stextras.storage.player.client`（2 类）
- `org.crypticdev.stextras.storage.quest`（2 类）
- `org.crypticdev.stextras.storage.quest.client`（1 类）
- `org.crypticdev.stextras.support`（3 类）
- `org.crypticdev.stextras.utils`（4 类）
