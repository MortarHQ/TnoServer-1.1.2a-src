# foxablazeaqzl's Built-in Tensura Skill Wiki（`foxablazeaqzl_wiki`）

> jar：`[转生附属]foxablazeaqzl_wiki.jar` ｜ 版本：`1.0.2.6` ｜ 作者：?
> 规模：**20 个类 / 5 个包 / 2281 行** ｜ 事件处理类 0 ｜ Mixin 5 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.foxablazeaqzl.wiki.client` | 12 | `FoxWikiClientEvents`、`FoxItemWikiScreen`、`FoxWikiScreen` |
| `com.foxablazeaqzl.wiki.mixin.client` | 5 | `SubAbilityModeSelectionScreenMixin`、`AbilitySelectionScreenMixin`、`EvolutionScreenMixin` |
| `com.foxablazeaqzl.wiki.neoforge` | 1 | `FoxWikiNeoForge` |
| `com.foxablazeaqzl.wiki.neoforge.client` | 1 | `FoxWikiNeoForgeClient` |
| `com.foxablazeaqzl.wiki` | 1 | `FoxAblazeAqzlWiki` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 20）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FoxWikiNeoForge` | class | — | Mod | 0 | 16 | [FoxWikiNeoForge.java](com/foxablazeaqzl/wiki/neoforge/FoxWikiNeoForge.java) |
| `FoxWikiClientEvents` | class | — | — | 1 | 19 | [FoxWikiClientEvents.java](com/foxablazeaqzl/wiki/client/FoxWikiClientEvents.java) |
| `FoxItemWikiScreen` | class | SimpleScreen | — | 27 | 380 | [FoxItemWikiScreen.java](com/foxablazeaqzl/wiki/client/FoxItemWikiScreen.java) |
| `FoxWikiScreen` | class | SimpleScreen | — | 36 | 551 | [FoxWikiScreen.java](com/foxablazeaqzl/wiki/client/FoxWikiScreen.java) |
| `SubAbilityModeSelectionScreenMixin` | class | SimpleScreen | Mixin | 0 | 84 | [SubAbilityModeSelectionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/SubAbilityModeSelectionScreenMixin.java) |
| `AbilitySelectionScreenMixin` | class | SimpleScreen | Mixin | 0 | 75 | [AbilitySelectionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/AbilitySelectionScreenMixin.java) |
| `EvolutionScreenMixin` | class | SimpleScreen | Mixin | 0 | 24 | [EvolutionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/EvolutionScreenMixin.java) |
| `MainScreenMixin` | class | SimpleScreen | Mixin | 0 | 24 | [MainScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/MainScreenMixin.java) |
| `TrBeyondTensuraScreenMixin` | class | SimpleScreen | Mixin、Pseudo | 0 | 34 | [TrBeyondTensuraScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/TrBeyondTensuraScreenMixin.java) |
| `FoxItemWikiData` | class | — | — | 18 | 199 | [FoxItemWikiData.java](com/foxablazeaqzl/wiki/client/FoxItemWikiData.java) |
| `FoxWikiCommands` | class | — | — | 9 | 113 | [FoxWikiCommands.java](com/foxablazeaqzl/wiki/client/FoxWikiCommands.java) |
| `FoxItemWikiUnlockState` | class | — | — | 8 | 142 | [FoxItemWikiUnlockState.java](com/foxablazeaqzl/wiki/client/FoxItemWikiUnlockState.java) |
| `FoxWikiData` | class | — | — | 10 | 141 | [FoxWikiData.java](com/foxablazeaqzl/wiki/client/FoxWikiData.java) |
| `FoxWikiNeoForgeClient` | class | — | — | 3 | 28 | [FoxWikiNeoForgeClient.java](com/foxablazeaqzl/wiki/neoforge/client/FoxWikiNeoForgeClient.java) |
| `FoxWikiUnreadState` | class | — | — | 11 | 189 | [FoxWikiUnreadState.java](com/foxablazeaqzl/wiki/client/FoxWikiUnreadState.java) |
| `FoxWikiTabButtons` | class | — | — | 11 | 122 | [FoxWikiTabButtons.java](com/foxablazeaqzl/wiki/client/FoxWikiTabButtons.java) |
| `FoxAblazeAqzlWiki` | class | — | — | 0 | 9 | [FoxAblazeAqzlWiki.java](com/foxablazeaqzl/wiki/FoxAblazeAqzlWiki.java) |
| `FoxWikiTopTabs` | class | — | — | 4 | 73 | [FoxWikiTopTabs.java](com/foxablazeaqzl/wiki/client/FoxWikiTopTabs.java) |
| `FoxWikiButton` | class | SimpleButton | — | 1 | 21 | [FoxWikiButton.java](com/foxablazeaqzl/wiki/client/FoxWikiButton.java) |
| `FoxWikiMarker` | class | — | — | 3 | 37 | [FoxWikiMarker.java](com/foxablazeaqzl/wiki/client/FoxWikiMarker.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `SubAbilityModeSelectionScreenMixin` | `SubAbilityModeSelectionScreen` | [SubAbilityModeSelectionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/SubAbilityModeSelectionScreenMixin.java) |
| `AbilitySelectionScreenMixin` | `AbilitySelectionScreen` | [AbilitySelectionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/AbilitySelectionScreenMixin.java) |
| `EvolutionScreenMixin` | `EvolutionScreen` | [EvolutionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/EvolutionScreenMixin.java) |
| `MainScreenMixin` | `MainScreen` | [MainScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/MainScreenMixin.java) |
| `TrBeyondTensuraScreenMixin` | `TrBeyondTensuraScreen` | [TrBeyondTensuraScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/TrBeyondTensuraScreenMixin.java) |

## 全部类（20）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FoxWikiNeoForge` | class | — | Mod | 0 | 16 | [FoxWikiNeoForge.java](com/foxablazeaqzl/wiki/neoforge/FoxWikiNeoForge.java) |
| `FoxWikiClientEvents` | class | — | — | 1 | 19 | [FoxWikiClientEvents.java](com/foxablazeaqzl/wiki/client/FoxWikiClientEvents.java) |
| `FoxItemWikiScreen` | class | SimpleScreen | — | 27 | 380 | [FoxItemWikiScreen.java](com/foxablazeaqzl/wiki/client/FoxItemWikiScreen.java) |
| `FoxWikiScreen` | class | SimpleScreen | — | 36 | 551 | [FoxWikiScreen.java](com/foxablazeaqzl/wiki/client/FoxWikiScreen.java) |
| `SubAbilityModeSelectionScreenMixin` | class | SimpleScreen | Mixin | 0 | 84 | [SubAbilityModeSelectionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/SubAbilityModeSelectionScreenMixin.java) |
| `AbilitySelectionScreenMixin` | class | SimpleScreen | Mixin | 0 | 75 | [AbilitySelectionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/AbilitySelectionScreenMixin.java) |
| `EvolutionScreenMixin` | class | SimpleScreen | Mixin | 0 | 24 | [EvolutionScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/EvolutionScreenMixin.java) |
| `MainScreenMixin` | class | SimpleScreen | Mixin | 0 | 24 | [MainScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/MainScreenMixin.java) |
| `TrBeyondTensuraScreenMixin` | class | SimpleScreen | Mixin、Pseudo | 0 | 34 | [TrBeyondTensuraScreenMixin.java](com/foxablazeaqzl/wiki/mixin/client/TrBeyondTensuraScreenMixin.java) |
| `FoxItemWikiData` | class | — | — | 18 | 199 | [FoxItemWikiData.java](com/foxablazeaqzl/wiki/client/FoxItemWikiData.java) |
| `FoxWikiCommands` | class | — | — | 9 | 113 | [FoxWikiCommands.java](com/foxablazeaqzl/wiki/client/FoxWikiCommands.java) |
| `FoxItemWikiUnlockState` | class | — | — | 8 | 142 | [FoxItemWikiUnlockState.java](com/foxablazeaqzl/wiki/client/FoxItemWikiUnlockState.java) |
| `FoxWikiData` | class | — | — | 10 | 141 | [FoxWikiData.java](com/foxablazeaqzl/wiki/client/FoxWikiData.java) |
| `FoxWikiNeoForgeClient` | class | — | — | 3 | 28 | [FoxWikiNeoForgeClient.java](com/foxablazeaqzl/wiki/neoforge/client/FoxWikiNeoForgeClient.java) |
| `FoxWikiUnreadState` | class | — | — | 11 | 189 | [FoxWikiUnreadState.java](com/foxablazeaqzl/wiki/client/FoxWikiUnreadState.java) |
| `FoxWikiTabButtons` | class | — | — | 11 | 122 | [FoxWikiTabButtons.java](com/foxablazeaqzl/wiki/client/FoxWikiTabButtons.java) |
| `FoxAblazeAqzlWiki` | class | — | — | 0 | 9 | [FoxAblazeAqzlWiki.java](com/foxablazeaqzl/wiki/FoxAblazeAqzlWiki.java) |
| `FoxWikiTopTabs` | class | — | — | 4 | 73 | [FoxWikiTopTabs.java](com/foxablazeaqzl/wiki/client/FoxWikiTopTabs.java) |
| `FoxWikiButton` | class | SimpleButton | — | 1 | 21 | [FoxWikiButton.java](com/foxablazeaqzl/wiki/client/FoxWikiButton.java) |
| `FoxWikiMarker` | class | — | — | 3 | 37 | [FoxWikiMarker.java](com/foxablazeaqzl/wiki/client/FoxWikiMarker.java) |
