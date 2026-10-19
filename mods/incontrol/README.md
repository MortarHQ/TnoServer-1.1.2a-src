# InControl（`incontrol`）

> jar：`[生物生成控制]incontrol.jar` ｜ 版本：`${file.jarVersion}` ｜ 作者：McJty
> 规模：**91 个类 / 16 个包 / 13104 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 9

**依赖**：`neoforge`、`lostcities`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `mcjty.incontrol.commands` | 17 | `ModCommands`、`Counter`、`CmdClearStats` |
| `mcjty.incontrol.events` | 13 | `EventsSystem`、`EventsRule`、`EventsConditions` |
| `mcjty.incontrol.rules` | 10 | `RulesManager`、`SpawnRule`、`LootRule` |
| `mcjty.incontrol.compat` | 7 | `ModRuleCompatibilityLayer`、`LostCitySupport`、`BaublesSupport` |
| `mcjty.incontrol.rules.support` | 6 | `GenericRuleEvaluator`、`RuleCache`、`CountInfo` |
| `mcjty.incontrol.tools.rules` | 5 | `RuleBase`、`TestingTools`、`TestingBlockTools` |
| `mcjty.incontrol.spawner` | 5 | `SpawnerConditions`、`SpawnerSystem`、`SpawnerRule` |
| `mcjty.incontrol.tools.typed` | 5 | `AttributeMap`、`Key`、`Attribute` |
| `mcjty.incontrol` | 4 | `ForgeEventHandlers`、`InControl`、`ModBusHandler` |
| `mcjty.incontrol.tools.varia` | 4 | `JSonTools`、`Tools`、`LookAtTools` |
| `mcjty.incontrol.setup` | 3 | `Registration`、`Config`、`ModSetup` |
| `mcjty.incontrol.data` | 3 | `DataStorage`、`PhaseTools`、`Statistics` |
| `mcjty.incontrol.areas` | 3 | `Area`、`AreaSystem`、`AreaParser` |
| `mcjty.incontrol.ai` | 2 | `AISystem`、`Goals` |
| `mcjty.incontrol.mob` | 2 | `CNPCMob`、`DefaultMob` |
| `mcjty.incontrol.tools.cache` | 2 | `StructureCache`、`StructureCacheEntry` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ForgeEventHandlers` | class | — | — | 18 | 505 | [ForgeEventHandlers.java](mcjty/incontrol/ForgeEventHandlers.java) |
| `EventsSystem` | class | — | — | 21 | 260 | [EventsSystem.java](mcjty/incontrol/events/EventsSystem.java) |
| `EventsRule` | class | — | — | 15 | 203 | [EventsRule.java](mcjty/incontrol/events/EventsRule.java) |
| `GenericRuleEvaluator` | class | — | — | 93 | 1386 | [GenericRuleEvaluator.java](mcjty/incontrol/rules/support/GenericRuleEvaluator.java) |
| `InControl` | class | — | Mod | 0 | 26 | [InControl.java](mcjty/incontrol/InControl.java) |
| `EventsConditions` | class | — | — | 12 | 155 | [EventsConditions.java](mcjty/incontrol/events/EventsConditions.java) |
| `RuleBase` | class | — | — | 46 | 982 | [RuleBase.java](mcjty/incontrol/tools/rules/RuleBase.java) |
| `RulesManager` | class | — | — | 19 | 230 | [RulesManager.java](mcjty/incontrol/rules/RulesManager.java) |
| `EventsParser` | class | — | — | 2 | 34 | [EventsParser.java](mcjty/incontrol/events/EventsParser.java) |
| `SpawnRule` | class | RuleBase | — | 66 | 516 | [SpawnRule.java](mcjty/incontrol/rules/SpawnRule.java) |
| `SpawnerConditions` | class | — | — | 48 | 498 | [SpawnerConditions.java](mcjty/incontrol/spawner/SpawnerConditions.java) |
| `ModRuleCompatibilityLayer` | class | IModRuleCompatibilityLayer | — | 31 | 165 | [ModRuleCompatibilityLayer.java](mcjty/incontrol/compat/ModRuleCompatibilityLayer.java) |
| `LootRule` | class | RuleBase | — | 19 | 306 | [LootRule.java](mcjty/incontrol/rules/LootRule.java) |
| `RuleCache` | class | — | — | 31 | 291 | [RuleCache.java](mcjty/incontrol/rules/support/RuleCache.java) |
| `SpawnerSystem` | class | — | — | 20 | 492 | [SpawnerSystem.java](mcjty/incontrol/spawner/SpawnerSystem.java) |
| `SpawnerRule` | class | — | — | 29 | 356 | [SpawnerRule.java](mcjty/incontrol/spawner/SpawnerRule.java) |
| `Registration` | class | — | — | 1 | 23 | [Registration.java](mcjty/incontrol/setup/Registration.java) |
| `TestingTools` | class | — | — | 20 | 446 | [TestingTools.java](mcjty/incontrol/tools/rules/TestingTools.java) |
| `ModBusHandler` | class | — | — | 1 | 9 | [ModBusHandler.java](mcjty/incontrol/ModBusHandler.java) |
| `ErrorHandler` | class | — | — | 3 | 36 | [ErrorHandler.java](mcjty/incontrol/ErrorHandler.java) |
| `EventTypeBlockBroken` | class | EventType | — | 3 | 35 | [EventTypeBlockBroken.java](mcjty/incontrol/events/EventTypeBlockBroken.java) |
| `RightClickRule` | class | RuleBase | — | 18 | 232 | [RightClickRule.java](mcjty/incontrol/rules/RightClickRule.java) |
| `PlaceRule` | class | RuleBase | — | 18 | 234 | [PlaceRule.java](mcjty/incontrol/rules/PlaceRule.java) |
| `LeftClickRule` | class | RuleBase | — | 18 | 232 | [LeftClickRule.java](mcjty/incontrol/rules/LeftClickRule.java) |
| `EffectRule` | class | RuleBase | — | 17 | 214 | [EffectRule.java](mcjty/incontrol/rules/EffectRule.java) |
| `HarvestRule` | class | RuleBase | — | 18 | 233 | [HarvestRule.java](mcjty/incontrol/rules/HarvestRule.java) |
| `Config` | class | — | — | 1 | 25 | [Config.java](mcjty/incontrol/setup/Config.java) |
| `LostCitySupport` | class | Function<ILostCities | — | 18 | 254 | [LostCitySupport.java](mcjty/incontrol/compat/LostCitySupport.java) |
| `TestingBlockTools` | class | — | — | 7 | 227 | [TestingBlockTools.java](mcjty/incontrol/tools/rules/TestingBlockTools.java) |
| `EventTypeCustom` | class | EventType | — | 3 | 23 | [EventTypeCustom.java](mcjty/incontrol/events/EventTypeCustom.java) |
| `EventType` | interface | — | — | 1 | 20 | [EventType.java](mcjty/incontrol/events/EventType.java) |
| `EventTypeMobKilled` | class | EventType | — | 4 | 69 | [EventTypeMobKilled.java](mcjty/incontrol/events/EventTypeMobKilled.java) |
| `SpawnEventAction` | record | — | — | 1 | 103 | [SpawnEventAction.java](mcjty/incontrol/events/SpawnEventAction.java) |
| `CommandAction` | record | — | — | 0 | 34 | [CommandAction.java](mcjty/incontrol/events/CommandAction.java) |
| `InControlLootModifier` | class | LootModifier | — | 2 | 105 | [InControlLootModifier.java](mcjty/incontrol/events/InControlLootModifier.java) |
| `CountInfo` | class | — | — | 12 | 273 | [CountInfo.java](mcjty/incontrol/rules/support/CountInfo.java) |
| `PositionCheck` | class | — | — | 5 | 444 | [PositionCheck.java](mcjty/incontrol/spawner/PositionCheck.java) |
| `DataStorage` | class | SavedData | — | 13 | 183 | [DataStorage.java](mcjty/incontrol/data/DataStorage.java) |
| `ModCommands` | class | — | — | 1 | 34 | [ModCommands.java](mcjty/incontrol/commands/ModCommands.java) |
| `IEventQuery` | interface | — | — | 0 | 29 | [IEventQuery.java](mcjty/incontrol/tools/rules/IEventQuery.java) |
| `AttributeMap` | class | — | — | 16 | 119 | [AttributeMap.java](mcjty/incontrol/tools/typed/AttributeMap.java) |
| `Area` | record | — | — | 12 | 153 | [Area.java](mcjty/incontrol/areas/Area.java) |
| `AISystem` | class | — | — | 9 | 246 | [AISystem.java](mcjty/incontrol/ai/AISystem.java) |
| `ExperienceRule` | class | RuleBase | — | 14 | 194 | [ExperienceRule.java](mcjty/incontrol/rules/ExperienceRule.java) |
| `PhaseRule` | class | — | — | 12 | 101 | [PhaseRule.java](mcjty/incontrol/rules/PhaseRule.java) |
| `JSonTools` | class | — | — | 11 | 168 | [JSonTools.java](mcjty/incontrol/tools/varia/JSonTools.java) |
| `Tools` | class | — | — | 9 | 221 | [Tools.java](mcjty/incontrol/tools/varia/Tools.java) |
| `IModRuleCompatibilityLayer` | interface | — | — | 0 | 71 | [IModRuleCompatibilityLayer.java](mcjty/incontrol/tools/rules/IModRuleCompatibilityLayer.java) |
| `CNPCMob` | class | DefaultMob | — | 9 | 75 | [CNPCMob.java](mcjty/incontrol/mob/CNPCMob.java) |
| `NumberAction` | record | — | — | 6 | 118 | [NumberAction.java](mcjty/incontrol/events/NumberAction.java) |
| `BaublesSupport` | class | — | — | 8 | 41 | [BaublesSupport.java](mcjty/incontrol/compat/BaublesSupport.java) |
| `DefaultMob` | class | — | — | 4 | 36 | [DefaultMob.java](mcjty/incontrol/mob/DefaultMob.java) |
| `AreaSystem` | class | — | — | 4 | 35 | [AreaSystem.java](mcjty/incontrol/areas/AreaSystem.java) |
| `AreaParser` | class | — | — | 2 | 35 | [AreaParser.java](mcjty/incontrol/areas/AreaParser.java) |
| `PhaseAction` | record | — | — | 0 | 54 | [PhaseAction.java](mcjty/incontrol/events/PhaseAction.java) |
| `Goals` | class | — | — | 0 | 5 | [Goals.java](mcjty/incontrol/ai/Goals.java) |
| `ICResult` | enum | — | — | 0 | 9 | [ICResult.java](mcjty/incontrol/rules/support/ICResult.java) |
| `SpawnWhen` | enum | — | — | 1 | 19 | [SpawnWhen.java](mcjty/incontrol/rules/support/SpawnWhen.java) |
| `RuleKeys` | interface | — | — | 0 | 185 | [RuleKeys.java](mcjty/incontrol/rules/support/RuleKeys.java) |
| `SpawnerParser` | class | — | — | 2 | 34 | [SpawnerParser.java](mcjty/incontrol/spawner/SpawnerParser.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ForgeEventHandlers` | 17 | `mcjty.incontrol` | [ForgeEventHandlers.java](mcjty/incontrol/ForgeEventHandlers.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](mcjty/incontrol/setup/Config.java) |

## 全部类（91）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ForgeEventHandlers` | class | — | — | 18 | 505 | [ForgeEventHandlers.java](mcjty/incontrol/ForgeEventHandlers.java) |
| `EventsSystem` | class | — | — | 21 | 260 | [EventsSystem.java](mcjty/incontrol/events/EventsSystem.java) |
| `EventsRule` | class | — | — | 15 | 203 | [EventsRule.java](mcjty/incontrol/events/EventsRule.java) |
| `GenericRuleEvaluator` | class | — | — | 93 | 1386 | [GenericRuleEvaluator.java](mcjty/incontrol/rules/support/GenericRuleEvaluator.java) |
| `InControl` | class | — | Mod | 0 | 26 | [InControl.java](mcjty/incontrol/InControl.java) |
| `EventsConditions` | class | — | — | 12 | 155 | [EventsConditions.java](mcjty/incontrol/events/EventsConditions.java) |
| `RuleBase` | class | — | — | 46 | 982 | [RuleBase.java](mcjty/incontrol/tools/rules/RuleBase.java) |
| `RulesManager` | class | — | — | 19 | 230 | [RulesManager.java](mcjty/incontrol/rules/RulesManager.java) |
| `EventsParser` | class | — | — | 2 | 34 | [EventsParser.java](mcjty/incontrol/events/EventsParser.java) |
| `SpawnRule` | class | RuleBase | — | 66 | 516 | [SpawnRule.java](mcjty/incontrol/rules/SpawnRule.java) |
| `SpawnerConditions` | class | — | — | 48 | 498 | [SpawnerConditions.java](mcjty/incontrol/spawner/SpawnerConditions.java) |
| `ModRuleCompatibilityLayer` | class | IModRuleCompatibilityLayer | — | 31 | 165 | [ModRuleCompatibilityLayer.java](mcjty/incontrol/compat/ModRuleCompatibilityLayer.java) |
| `LootRule` | class | RuleBase | — | 19 | 306 | [LootRule.java](mcjty/incontrol/rules/LootRule.java) |
| `RuleCache` | class | — | — | 31 | 291 | [RuleCache.java](mcjty/incontrol/rules/support/RuleCache.java) |
| `SpawnerSystem` | class | — | — | 20 | 492 | [SpawnerSystem.java](mcjty/incontrol/spawner/SpawnerSystem.java) |
| `SpawnerRule` | class | — | — | 29 | 356 | [SpawnerRule.java](mcjty/incontrol/spawner/SpawnerRule.java) |
| `Registration` | class | — | — | 1 | 23 | [Registration.java](mcjty/incontrol/setup/Registration.java) |
| `TestingTools` | class | — | — | 20 | 446 | [TestingTools.java](mcjty/incontrol/tools/rules/TestingTools.java) |
| `ModBusHandler` | class | — | — | 1 | 9 | [ModBusHandler.java](mcjty/incontrol/ModBusHandler.java) |
| `ErrorHandler` | class | — | — | 3 | 36 | [ErrorHandler.java](mcjty/incontrol/ErrorHandler.java) |
| `EventTypeBlockBroken` | class | EventType | — | 3 | 35 | [EventTypeBlockBroken.java](mcjty/incontrol/events/EventTypeBlockBroken.java) |
| `RightClickRule` | class | RuleBase | — | 18 | 232 | [RightClickRule.java](mcjty/incontrol/rules/RightClickRule.java) |
| `PlaceRule` | class | RuleBase | — | 18 | 234 | [PlaceRule.java](mcjty/incontrol/rules/PlaceRule.java) |
| `LeftClickRule` | class | RuleBase | — | 18 | 232 | [LeftClickRule.java](mcjty/incontrol/rules/LeftClickRule.java) |
| `EffectRule` | class | RuleBase | — | 17 | 214 | [EffectRule.java](mcjty/incontrol/rules/EffectRule.java) |
| `HarvestRule` | class | RuleBase | — | 18 | 233 | [HarvestRule.java](mcjty/incontrol/rules/HarvestRule.java) |
| `Config` | class | — | — | 1 | 25 | [Config.java](mcjty/incontrol/setup/Config.java) |
| `LostCitySupport` | class | Function<ILostCities | — | 18 | 254 | [LostCitySupport.java](mcjty/incontrol/compat/LostCitySupport.java) |
| `TestingBlockTools` | class | — | — | 7 | 227 | [TestingBlockTools.java](mcjty/incontrol/tools/rules/TestingBlockTools.java) |
| `EventTypeCustom` | class | EventType | — | 3 | 23 | [EventTypeCustom.java](mcjty/incontrol/events/EventTypeCustom.java) |
| `EventType` | interface | — | — | 1 | 20 | [EventType.java](mcjty/incontrol/events/EventType.java) |
| `EventTypeMobKilled` | class | EventType | — | 4 | 69 | [EventTypeMobKilled.java](mcjty/incontrol/events/EventTypeMobKilled.java) |
| `SpawnEventAction` | record | — | — | 1 | 103 | [SpawnEventAction.java](mcjty/incontrol/events/SpawnEventAction.java) |
| `CommandAction` | record | — | — | 0 | 34 | [CommandAction.java](mcjty/incontrol/events/CommandAction.java) |
| `InControlLootModifier` | class | LootModifier | — | 2 | 105 | [InControlLootModifier.java](mcjty/incontrol/events/InControlLootModifier.java) |
| `CountInfo` | class | — | — | 12 | 273 | [CountInfo.java](mcjty/incontrol/rules/support/CountInfo.java) |
| `PositionCheck` | class | — | — | 5 | 444 | [PositionCheck.java](mcjty/incontrol/spawner/PositionCheck.java) |
| `DataStorage` | class | SavedData | — | 13 | 183 | [DataStorage.java](mcjty/incontrol/data/DataStorage.java) |
| `ModCommands` | class | — | — | 1 | 34 | [ModCommands.java](mcjty/incontrol/commands/ModCommands.java) |
| `IEventQuery` | interface | — | — | 0 | 29 | [IEventQuery.java](mcjty/incontrol/tools/rules/IEventQuery.java) |
| `AttributeMap` | class | — | — | 16 | 119 | [AttributeMap.java](mcjty/incontrol/tools/typed/AttributeMap.java) |
| `Area` | record | — | — | 12 | 153 | [Area.java](mcjty/incontrol/areas/Area.java) |
| `AISystem` | class | — | — | 9 | 246 | [AISystem.java](mcjty/incontrol/ai/AISystem.java) |
| `ExperienceRule` | class | RuleBase | — | 14 | 194 | [ExperienceRule.java](mcjty/incontrol/rules/ExperienceRule.java) |
| `PhaseRule` | class | — | — | 12 | 101 | [PhaseRule.java](mcjty/incontrol/rules/PhaseRule.java) |
| `JSonTools` | class | — | — | 11 | 168 | [JSonTools.java](mcjty/incontrol/tools/varia/JSonTools.java) |
| `Tools` | class | — | — | 9 | 221 | [Tools.java](mcjty/incontrol/tools/varia/Tools.java) |
| `IModRuleCompatibilityLayer` | interface | — | — | 0 | 71 | [IModRuleCompatibilityLayer.java](mcjty/incontrol/tools/rules/IModRuleCompatibilityLayer.java) |
| `CNPCMob` | class | DefaultMob | — | 9 | 75 | [CNPCMob.java](mcjty/incontrol/mob/CNPCMob.java) |
| `NumberAction` | record | — | — | 6 | 118 | [NumberAction.java](mcjty/incontrol/events/NumberAction.java) |
| `BaublesSupport` | class | — | — | 8 | 41 | [BaublesSupport.java](mcjty/incontrol/compat/BaublesSupport.java) |
| `DefaultMob` | class | — | — | 4 | 36 | [DefaultMob.java](mcjty/incontrol/mob/DefaultMob.java) |
| `AreaSystem` | class | — | — | 4 | 35 | [AreaSystem.java](mcjty/incontrol/areas/AreaSystem.java) |
| `AreaParser` | class | — | — | 2 | 35 | [AreaParser.java](mcjty/incontrol/areas/AreaParser.java) |
| `PhaseAction` | record | — | — | 0 | 54 | [PhaseAction.java](mcjty/incontrol/events/PhaseAction.java) |
| `Goals` | class | — | — | 0 | 5 | [Goals.java](mcjty/incontrol/ai/Goals.java) |
| `ICResult` | enum | — | — | 0 | 9 | [ICResult.java](mcjty/incontrol/rules/support/ICResult.java) |
| `SpawnWhen` | enum | — | — | 1 | 19 | [SpawnWhen.java](mcjty/incontrol/rules/support/SpawnWhen.java) |
| `RuleKeys` | interface | — | — | 0 | 185 | [RuleKeys.java](mcjty/incontrol/rules/support/RuleKeys.java) |
| `SpawnerParser` | class | — | — | 2 | 34 | [SpawnerParser.java](mcjty/incontrol/spawner/SpawnerParser.java) |
| `ModSetup` | class | — | — | 3 | 81 | [ModSetup.java](mcjty/incontrol/setup/ModSetup.java) |
| `EnigmaSupport` | class | — | — | 1 | 7 | [EnigmaSupport.java](mcjty/incontrol/compat/EnigmaSupport.java) |
| `GameStageSupport` | class | — | — | 3 | 16 | [GameStageSupport.java](mcjty/incontrol/compat/GameStageSupport.java) |
| `CustomNPCSupport` | class | — | — | 4 | 36 | [CustomNPCSupport.java](mcjty/incontrol/compat/CustomNPCSupport.java) |
| `SereneSeasonsSupport` | class | — | — | 4 | 29 | [SereneSeasonsSupport.java](mcjty/incontrol/compat/SereneSeasonsSupport.java) |
| `PhaseTools` | class | — | — | 1 | 25 | [PhaseTools.java](mcjty/incontrol/data/PhaseTools.java) |
| `Statistics` | class | — | — | 4 | 60 | [Statistics.java](mcjty/incontrol/data/Statistics.java) |
| `Counter` | class | — | — | 4 | 40 | [Counter.java](mcjty/incontrol/commands/Counter.java) |
| `CmdClearStats` | class | Command<CommandSourceStack> | — | 2 | 25 | [CmdClearStats.java](mcjty/incontrol/commands/CmdClearStats.java) |
| `CmdShowStats` | class | Command<CommandSourceStack> | — | 2 | 25 | [CmdShowStats.java](mcjty/incontrol/commands/CmdShowStats.java) |
| `CmdClearPhase` | class | — | — | 2 | 25 | [CmdClearPhase.java](mcjty/incontrol/commands/CmdClearPhase.java) |
| `CmdShowMobs` | class | Command<CommandSourceStack> | — | 2 | 31 | [CmdShowMobs.java](mcjty/incontrol/commands/CmdShowMobs.java) |
| `CmdNumbers` | class | — | — | 2 | 25 | [CmdNumbers.java](mcjty/incontrol/commands/CmdNumbers.java) |
| `CmdDays` | class | — | — | 3 | 33 | [CmdDays.java](mcjty/incontrol/commands/CmdDays.java) |
| `CmdSetPhase` | class | — | — | 2 | 25 | [CmdSetPhase.java](mcjty/incontrol/commands/CmdSetPhase.java) |
| `CmdKillMobs` | class | Command<CommandSourceStack> | — | 2 | 75 | [CmdKillMobs.java](mcjty/incontrol/commands/CmdKillMobs.java) |
| `CmdDebug` | class | Command<CommandSourceStack> | — | 2 | 36 | [CmdDebug.java](mcjty/incontrol/commands/CmdDebug.java) |
| `CmdReload` | class | Command<CommandSourceStack> | — | 2 | 46 | [CmdReload.java](mcjty/incontrol/commands/CmdReload.java) |
| `CmdInfo` | class | Command<CommandSourceStack> | — | 2 | 59 | [CmdInfo.java](mcjty/incontrol/commands/CmdInfo.java) |
| `CmdPhases` | class | — | — | 2 | 30 | [CmdPhases.java](mcjty/incontrol/commands/CmdPhases.java) |
| `CmdSetNumber` | class | — | — | 2 | 30 | [CmdSetNumber.java](mcjty/incontrol/commands/CmdSetNumber.java) |
| `CmdArea` | class | — | — | 2 | 30 | [CmdArea.java](mcjty/incontrol/commands/CmdArea.java) |
| `CmdList` | class | Command<CommandSourceStack> | — | 4 | 56 | [CmdList.java](mcjty/incontrol/commands/CmdList.java) |
| `StructureCache` | class | — | — | 3 | 81 | [StructureCache.java](mcjty/incontrol/tools/cache/StructureCache.java) |
| `StructureCacheEntry` | record | — | — | 0 | 9 | [StructureCacheEntry.java](mcjty/incontrol/tools/cache/StructureCacheEntry.java) |
| `LookAtTools` | class | — | — | 2 | 40 | [LookAtTools.java](mcjty/incontrol/tools/varia/LookAtTools.java) |
| `Box` | record | — | — | 4 | 61 | [Box.java](mcjty/incontrol/tools/varia/Box.java) |
| `Key` | record | — | — | 3 | 21 | [Key.java](mcjty/incontrol/tools/typed/Key.java) |
| `Attribute` | record | — | — | 2 | 12 | [Attribute.java](mcjty/incontrol/tools/typed/Attribute.java) |
| `Type` | record | — | — | 4 | 41 | [Type.java](mcjty/incontrol/tools/typed/Type.java) |
| `GenericAttributeMapFactory` | class | — | — | 3 | 153 | [GenericAttributeMapFactory.java](mcjty/incontrol/tools/typed/GenericAttributeMapFactory.java) |
