# Quest Language Selection（`ftbqlang`）

> jar：`[附属]ftbqlang.jar` ｜ 版本：`1.0.0` ｜ 作者：AblazeAQZL
> 规模：**22 个类 / 6 个包 / 1291 行** ｜ 事件处理类 1 ｜ Mixin 7 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`ftbquests`、`ftblibrary`、`ftbteams`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftbqlang.client` | 6 | `ClientEvents`、`LanguageSelectionPanel`、`QuestLanguageClient` |
| `dev.ftbqlang.mixin.client` | 6 | `BaseScreenMixin`、`SyncTranslationMessageToClientMixin`、`ClientQuestFileMixin` |
| `dev.ftbqlang.network` | 4 | `LanguageNetwork`、`QueryPayload`、`LanguageStatePayload` |
| `dev.ftbqlang.server` | 4 | `LanguagePreferences`、`LanguagePolicy`、`LanguageCatalog` |
| `dev.ftbqlang.mixin` | 1 | `TranslationManagerAccessor` |
| `dev.ftbqlang` | 1 | `FTBQLang` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 22）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEvents` | class | — | EventBusSubscriber | 3 | 30 | [ClientEvents.java](dev/ftbqlang/client/ClientEvents.java) |
| `TranslationManagerAccessor` | interface | — | Mixin | 0 | 14 | [TranslationManagerAccessor.java](dev/ftbqlang/mixin/TranslationManagerAccessor.java) |
| `FTBQLang` | class | — | Mod | 0 | 18 | [FTBQLang.java](dev/ftbqlang/FTBQLang.java) |
| `BaseScreenMixin` | class | — | Mixin | 0 | 20 | [BaseScreenMixin.java](dev/ftbqlang/mixin/client/BaseScreenMixin.java) |
| `SyncTranslationMessageToClientMixin` | class | — | Mixin | 0 | 22 | [SyncTranslationMessageToClientMixin.java](dev/ftbqlang/mixin/client/SyncTranslationMessageToClientMixin.java) |
| `ClientQuestFileMixin` | class | — | Mixin | 0 | 34 | [ClientQuestFileMixin.java](dev/ftbqlang/mixin/client/ClientQuestFileMixin.java) |
| `QuestScreenMixin` | class | — | Mixin | 0 | 20 | [QuestScreenMixin.java](dev/ftbqlang/mixin/client/QuestScreenMixin.java) |
| `SyncTranslationTableMessageMixin` | class | — | Mixin | 0 | 17 | [SyncTranslationTableMessageMixin.java](dev/ftbqlang/mixin/client/SyncTranslationTableMessageMixin.java) |
| `OtherButtonsPanelBottomMixin` | class | — | Mixin | 0 | 21 | [OtherButtonsPanelBottomMixin.java](dev/ftbqlang/mixin/client/OtherButtonsPanelBottomMixin.java) |
| `LanguageSelectionPanel` | class | ModalPanel | — | 24 | 346 | [LanguageSelectionPanel.java](dev/ftbqlang/client/LanguageSelectionPanel.java) |
| `QuestLanguageClient` | class | — | — | 16 | 199 | [QuestLanguageClient.java](dev/ftbqlang/client/QuestLanguageClient.java) |
| `LanguageNetwork` | class | — | — | 2 | 35 | [LanguageNetwork.java](dev/ftbqlang/network/LanguageNetwork.java) |
| `LanguageAnimation` | class | — | — | 12 | 94 | [LanguageAnimation.java](dev/ftbqlang/client/LanguageAnimation.java) |
| `LanguagePreferences` | class | SavedData | — | 5 | 69 | [LanguagePreferences.java](dev/ftbqlang/server/LanguagePreferences.java) |
| `LanguagePolicy` | class | — | — | 7 | 58 | [LanguagePolicy.java](dev/ftbqlang/server/LanguagePolicy.java) |
| `SelectorSession` | class | — | — | 7 | 40 | [SelectorSession.java](dev/ftbqlang/client/SelectorSession.java) |
| `QueryPayload` | record | CustomPacketPayload | — | 1 | 20 | [QueryPayload.java](dev/ftbqlang/network/QueryPayload.java) |
| `LanguageStatePayload` | record | CustomPacketPayload | — | 3 | 53 | [LanguageStatePayload.java](dev/ftbqlang/network/LanguageStatePayload.java) |
| `SelectLanguagePayload` | record | CustomPacketPayload | — | 3 | 25 | [SelectLanguagePayload.java](dev/ftbqlang/network/SelectLanguagePayload.java) |
| `LanguageCatalog` | record | — | — | 2 | 61 | [LanguageCatalog.java](dev/ftbqlang/server/LanguageCatalog.java) |
| `LanguageService` | class | — | — | 3 | 54 | [LanguageService.java](dev/ftbqlang/server/LanguageService.java) |
| `LanguageTabButton` | class | TabButton | — | 3 | 41 | [LanguageTabButton.java](dev/ftbqlang/client/LanguageTabButton.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEvents` | 3 | `dev.ftbqlang.client` | [ClientEvents.java](dev/ftbqlang/client/ClientEvents.java) |

## Mixin（7 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `TranslationManagerAccessor` | `TranslationManagerAccessor` | [TranslationManagerAccessor.java](dev/ftbqlang/mixin/TranslationManagerAccessor.java) |
| `BaseScreenMixin` | `BaseScreen` | [BaseScreenMixin.java](dev/ftbqlang/mixin/client/BaseScreenMixin.java) |
| `SyncTranslationMessageToClientMixin` | `SyncTranslationMessageToClient` | [SyncTranslationMessageToClientMixin.java](dev/ftbqlang/mixin/client/SyncTranslationMessageToClientMixin.java) |
| `ClientQuestFileMixin` | `ClientQuestFile` | [ClientQuestFileMixin.java](dev/ftbqlang/mixin/client/ClientQuestFileMixin.java) |
| `QuestScreenMixin` | `QuestScreen` | [QuestScreenMixin.java](dev/ftbqlang/mixin/client/QuestScreenMixin.java) |
| `SyncTranslationTableMessageMixin` | `SyncTranslationTableMessage` | [SyncTranslationTableMessageMixin.java](dev/ftbqlang/mixin/client/SyncTranslationTableMessageMixin.java) |
| `OtherButtonsPanelBottomMixin` | `OtherButtonsPanelBottom` | [OtherButtonsPanelBottomMixin.java](dev/ftbqlang/mixin/client/OtherButtonsPanelBottomMixin.java) |

## 全部类（22）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEvents` | class | — | EventBusSubscriber | 3 | 30 | [ClientEvents.java](dev/ftbqlang/client/ClientEvents.java) |
| `TranslationManagerAccessor` | interface | — | Mixin | 0 | 14 | [TranslationManagerAccessor.java](dev/ftbqlang/mixin/TranslationManagerAccessor.java) |
| `FTBQLang` | class | — | Mod | 0 | 18 | [FTBQLang.java](dev/ftbqlang/FTBQLang.java) |
| `BaseScreenMixin` | class | — | Mixin | 0 | 20 | [BaseScreenMixin.java](dev/ftbqlang/mixin/client/BaseScreenMixin.java) |
| `SyncTranslationMessageToClientMixin` | class | — | Mixin | 0 | 22 | [SyncTranslationMessageToClientMixin.java](dev/ftbqlang/mixin/client/SyncTranslationMessageToClientMixin.java) |
| `ClientQuestFileMixin` | class | — | Mixin | 0 | 34 | [ClientQuestFileMixin.java](dev/ftbqlang/mixin/client/ClientQuestFileMixin.java) |
| `QuestScreenMixin` | class | — | Mixin | 0 | 20 | [QuestScreenMixin.java](dev/ftbqlang/mixin/client/QuestScreenMixin.java) |
| `SyncTranslationTableMessageMixin` | class | — | Mixin | 0 | 17 | [SyncTranslationTableMessageMixin.java](dev/ftbqlang/mixin/client/SyncTranslationTableMessageMixin.java) |
| `OtherButtonsPanelBottomMixin` | class | — | Mixin | 0 | 21 | [OtherButtonsPanelBottomMixin.java](dev/ftbqlang/mixin/client/OtherButtonsPanelBottomMixin.java) |
| `LanguageSelectionPanel` | class | ModalPanel | — | 24 | 346 | [LanguageSelectionPanel.java](dev/ftbqlang/client/LanguageSelectionPanel.java) |
| `QuestLanguageClient` | class | — | — | 16 | 199 | [QuestLanguageClient.java](dev/ftbqlang/client/QuestLanguageClient.java) |
| `LanguageNetwork` | class | — | — | 2 | 35 | [LanguageNetwork.java](dev/ftbqlang/network/LanguageNetwork.java) |
| `LanguageAnimation` | class | — | — | 12 | 94 | [LanguageAnimation.java](dev/ftbqlang/client/LanguageAnimation.java) |
| `LanguagePreferences` | class | SavedData | — | 5 | 69 | [LanguagePreferences.java](dev/ftbqlang/server/LanguagePreferences.java) |
| `LanguagePolicy` | class | — | — | 7 | 58 | [LanguagePolicy.java](dev/ftbqlang/server/LanguagePolicy.java) |
| `SelectorSession` | class | — | — | 7 | 40 | [SelectorSession.java](dev/ftbqlang/client/SelectorSession.java) |
| `QueryPayload` | record | CustomPacketPayload | — | 1 | 20 | [QueryPayload.java](dev/ftbqlang/network/QueryPayload.java) |
| `LanguageStatePayload` | record | CustomPacketPayload | — | 3 | 53 | [LanguageStatePayload.java](dev/ftbqlang/network/LanguageStatePayload.java) |
| `SelectLanguagePayload` | record | CustomPacketPayload | — | 3 | 25 | [SelectLanguagePayload.java](dev/ftbqlang/network/SelectLanguagePayload.java) |
| `LanguageCatalog` | record | — | — | 2 | 61 | [LanguageCatalog.java](dev/ftbqlang/server/LanguageCatalog.java) |
| `LanguageService` | class | — | — | 3 | 54 | [LanguageService.java](dev/ftbqlang/server/LanguageService.java) |
| `LanguageTabButton` | class | TabButton | — | 3 | 41 | [LanguageTabButton.java](dev/ftbqlang/client/LanguageTabButton.java) |
