# Tensura Enigmatic（`tensuraenigmatic`）

> jar：`[转生附属]tensuraenigmatic.jar` ｜ 版本：`1.0.1` ｜ 作者：AblazeAQZL, b4ndithelps
> 规模：**27 个类 / 8 个包 / 1191 行** ｜ 事件处理类 11 ｜ Mixin 0 ｜ 注册/管理类 12

**依赖**：`neoforge`、`minecraft`、`curios`、`tensura`、`enigmaticlegacyplus`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.b4ndithelps.tensuraenigmatic.handler` | 13 | `TitleClearHandler`、`CursedItemTooltipHandler`、`RingOfTheElvesHandler` |
| `com.github.b4ndithelps.tensuraenigmatic.items` | 4 | `RingOfTheElves`、`RingOfArcaneSource`、`PowerOfForceRing` |
| `com.github.b4ndithelps.tensuraenigmatic.registry` | 3 | `TensuraEnigmaticLoots`、`TensuraEnigmaticItems`、`TensuraEnigmaticTabs` |
| `com.github.b4ndithelps.tensuraenigmatic.compat` | 2 | `EnigmaticClientCompat`、`EnigmaticCompat` |
| `com.github.b4ndithelps.tensuraenigmatic.util` | 2 | `TensuraEnergyHelper`、`CurioHelper` |
| `com.github.b4ndithelps.tensuraenigmatic` | 1 | `TensuraEnigmatic` |
| `com.github.b4ndithelps.tensuraenigmatic.config` | 1 | `TensuraEnigmaticConfig` |
| `com.github.b4ndithelps.tensuraenigmatic.loot` | 1 | `RingOfArcaneSourceLootModifier` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 27）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TitleClearHandler` | class | — | EventBusSubscriber | 2 | 37 | [TitleClearHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TitleClearHandler.java) |
| `CursedItemTooltipHandler` | class | — | — | 2 | 30 | [CursedItemTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedItemTooltipHandler.java) |
| `TensuraEnigmatic` | class | — | Mod | 1 | 48 | [TensuraEnigmatic.java](com/github/b4ndithelps/tensuraenigmatic/TensuraEnigmatic.java) |
| `RingOfTheElvesHandler` | class | — | — | 6 | 96 | [RingOfTheElvesHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingOfTheElvesHandler.java) |
| `ElfPrayerHandler` | class | — | — | 6 | 75 | [ElfPrayerHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/ElfPrayerHandler.java) |
| `CursedRingTooltipHandler` | class | — | — | 2 | 45 | [CursedRingTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedRingTooltipHandler.java) |
| `PowerOfForceHandler` | class | — | — | 2 | 48 | [PowerOfForceHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/PowerOfForceHandler.java) |
| `RingOfArcaneSourceHandler` | class | — | — | 1 | 20 | [RingOfArcaneSourceHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingOfArcaneSourceHandler.java) |
| `ManaRingHandler` | class | — | — | 1 | 20 | [ManaRingHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/ManaRingHandler.java) |
| `CursedRingEnhancementHandler` | class | — | — | 2 | 26 | [CursedRingEnhancementHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedRingEnhancementHandler.java) |
| `TheCubeEnhancementHandler` | class | — | — | 2 | 27 | [TheCubeEnhancementHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TheCubeEnhancementHandler.java) |
| `TheCubeTooltipHandler` | class | — | — | 2 | 41 | [TheCubeTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TheCubeTooltipHandler.java) |
| `LootTableHandler` | class | — | — | 0 | 7 | [LootTableHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/LootTableHandler.java) |
| `TensuraEnigmaticConfig` | class | — | — | 1 | 57 | [TensuraEnigmaticConfig.java](com/github/b4ndithelps/tensuraenigmatic/config/TensuraEnigmaticConfig.java) |
| `EnigmaticClientCompat` | class | — | — | 0 | 18 | [EnigmaticClientCompat.java](com/github/b4ndithelps/tensuraenigmatic/compat/EnigmaticClientCompat.java) |
| `RingOfArcaneSourceLootModifier` | class | LootModifier | — | 2 | 61 | [RingOfArcaneSourceLootModifier.java](com/github/b4ndithelps/tensuraenigmatic/loot/RingOfArcaneSourceLootModifier.java) |
| `TensuraEnigmaticLoots` | class | — | — | 0 | 21 | [TensuraEnigmaticLoots.java](com/github/b4ndithelps/tensuraenigmatic/registry/TensuraEnigmaticLoots.java) |
| `RingOfTheElves` | class | Item | — | 10 | 108 | [RingOfTheElves.java](com/github/b4ndithelps/tensuraenigmatic/items/RingOfTheElves.java) |
| `EnigmaticCompat` | class | — | — | 1 | 23 | [EnigmaticCompat.java](com/github/b4ndithelps/tensuraenigmatic/compat/EnigmaticCompat.java) |
| `TensuraEnigmaticItems` | class | — | — | 0 | 21 | [TensuraEnigmaticItems.java](com/github/b4ndithelps/tensuraenigmatic/registry/TensuraEnigmaticItems.java) |
| `TensuraEnergyHelper` | class | — | — | 5 | 72 | [TensuraEnergyHelper.java](com/github/b4ndithelps/tensuraenigmatic/util/TensuraEnergyHelper.java) |
| `RingReceivedData` | class | SavedData | — | 3 | 43 | [RingReceivedData.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingReceivedData.java) |
| `RingOfArcaneSource` | class | Item | — | 7 | 74 | [RingOfArcaneSource.java](com/github/b4ndithelps/tensuraenigmatic/items/RingOfArcaneSource.java) |
| `PowerOfForceRing` | class | Item | — | 5 | 59 | [PowerOfForceRing.java](com/github/b4ndithelps/tensuraenigmatic/items/PowerOfForceRing.java) |
| `ManaRing` | class | Item | — | 6 | 62 | [ManaRing.java](com/github/b4ndithelps/tensuraenigmatic/items/ManaRing.java) |
| `CurioHelper` | class | — | — | 1 | 15 | [CurioHelper.java](com/github/b4ndithelps/tensuraenigmatic/util/CurioHelper.java) |
| `TensuraEnigmaticTabs` | class | — | — | 1 | 37 | [TensuraEnigmaticTabs.java](com/github/b4ndithelps/tensuraenigmatic/registry/TensuraEnigmaticTabs.java) |

## 事件处理（`@SubscribeEvent`，11 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `PowerOfForceHandler` | 2 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [PowerOfForceHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/PowerOfForceHandler.java) |
| `TitleClearHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [TitleClearHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TitleClearHandler.java) |
| `CursedItemTooltipHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [CursedItemTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedItemTooltipHandler.java) |
| `RingOfTheElvesHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [RingOfTheElvesHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingOfTheElvesHandler.java) |
| `ElfPrayerHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [ElfPrayerHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/ElfPrayerHandler.java) |
| `CursedRingTooltipHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [CursedRingTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedRingTooltipHandler.java) |
| `RingOfArcaneSourceHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [RingOfArcaneSourceHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingOfArcaneSourceHandler.java) |
| `ManaRingHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [ManaRingHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/ManaRingHandler.java) |
| `CursedRingEnhancementHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [CursedRingEnhancementHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedRingEnhancementHandler.java) |
| `TheCubeEnhancementHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [TheCubeEnhancementHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TheCubeEnhancementHandler.java) |
| `TheCubeTooltipHandler` | 1 | `com.github.b4ndithelps.tensuraenigmatic.handler` | [TheCubeTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TheCubeTooltipHandler.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TensuraEnigmaticConfig` | — | [TensuraEnigmaticConfig.java](com/github/b4ndithelps/tensuraenigmatic/config/TensuraEnigmaticConfig.java) |

## 全部类（27）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TitleClearHandler` | class | — | EventBusSubscriber | 2 | 37 | [TitleClearHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TitleClearHandler.java) |
| `CursedItemTooltipHandler` | class | — | — | 2 | 30 | [CursedItemTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedItemTooltipHandler.java) |
| `TensuraEnigmatic` | class | — | Mod | 1 | 48 | [TensuraEnigmatic.java](com/github/b4ndithelps/tensuraenigmatic/TensuraEnigmatic.java) |
| `RingOfTheElvesHandler` | class | — | — | 6 | 96 | [RingOfTheElvesHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingOfTheElvesHandler.java) |
| `ElfPrayerHandler` | class | — | — | 6 | 75 | [ElfPrayerHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/ElfPrayerHandler.java) |
| `CursedRingTooltipHandler` | class | — | — | 2 | 45 | [CursedRingTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedRingTooltipHandler.java) |
| `PowerOfForceHandler` | class | — | — | 2 | 48 | [PowerOfForceHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/PowerOfForceHandler.java) |
| `RingOfArcaneSourceHandler` | class | — | — | 1 | 20 | [RingOfArcaneSourceHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingOfArcaneSourceHandler.java) |
| `ManaRingHandler` | class | — | — | 1 | 20 | [ManaRingHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/ManaRingHandler.java) |
| `CursedRingEnhancementHandler` | class | — | — | 2 | 26 | [CursedRingEnhancementHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/CursedRingEnhancementHandler.java) |
| `TheCubeEnhancementHandler` | class | — | — | 2 | 27 | [TheCubeEnhancementHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TheCubeEnhancementHandler.java) |
| `TheCubeTooltipHandler` | class | — | — | 2 | 41 | [TheCubeTooltipHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/TheCubeTooltipHandler.java) |
| `LootTableHandler` | class | — | — | 0 | 7 | [LootTableHandler.java](com/github/b4ndithelps/tensuraenigmatic/handler/LootTableHandler.java) |
| `TensuraEnigmaticConfig` | class | — | — | 1 | 57 | [TensuraEnigmaticConfig.java](com/github/b4ndithelps/tensuraenigmatic/config/TensuraEnigmaticConfig.java) |
| `EnigmaticClientCompat` | class | — | — | 0 | 18 | [EnigmaticClientCompat.java](com/github/b4ndithelps/tensuraenigmatic/compat/EnigmaticClientCompat.java) |
| `RingOfArcaneSourceLootModifier` | class | LootModifier | — | 2 | 61 | [RingOfArcaneSourceLootModifier.java](com/github/b4ndithelps/tensuraenigmatic/loot/RingOfArcaneSourceLootModifier.java) |
| `TensuraEnigmaticLoots` | class | — | — | 0 | 21 | [TensuraEnigmaticLoots.java](com/github/b4ndithelps/tensuraenigmatic/registry/TensuraEnigmaticLoots.java) |
| `RingOfTheElves` | class | Item | — | 10 | 108 | [RingOfTheElves.java](com/github/b4ndithelps/tensuraenigmatic/items/RingOfTheElves.java) |
| `EnigmaticCompat` | class | — | — | 1 | 23 | [EnigmaticCompat.java](com/github/b4ndithelps/tensuraenigmatic/compat/EnigmaticCompat.java) |
| `TensuraEnigmaticItems` | class | — | — | 0 | 21 | [TensuraEnigmaticItems.java](com/github/b4ndithelps/tensuraenigmatic/registry/TensuraEnigmaticItems.java) |
| `TensuraEnergyHelper` | class | — | — | 5 | 72 | [TensuraEnergyHelper.java](com/github/b4ndithelps/tensuraenigmatic/util/TensuraEnergyHelper.java) |
| `RingReceivedData` | class | SavedData | — | 3 | 43 | [RingReceivedData.java](com/github/b4ndithelps/tensuraenigmatic/handler/RingReceivedData.java) |
| `RingOfArcaneSource` | class | Item | — | 7 | 74 | [RingOfArcaneSource.java](com/github/b4ndithelps/tensuraenigmatic/items/RingOfArcaneSource.java) |
| `PowerOfForceRing` | class | Item | — | 5 | 59 | [PowerOfForceRing.java](com/github/b4ndithelps/tensuraenigmatic/items/PowerOfForceRing.java) |
| `ManaRing` | class | Item | — | 6 | 62 | [ManaRing.java](com/github/b4ndithelps/tensuraenigmatic/items/ManaRing.java) |
| `CurioHelper` | class | — | — | 1 | 15 | [CurioHelper.java](com/github/b4ndithelps/tensuraenigmatic/util/CurioHelper.java) |
| `TensuraEnigmaticTabs` | class | — | — | 1 | 37 | [TensuraEnigmaticTabs.java](com/github/b4ndithelps/tensuraenigmatic/registry/TensuraEnigmaticTabs.java) |
