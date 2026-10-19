# Controlling（`controlling`）

> jar：`[键位冲突显示] Controlling-neoforge-1.21.1-19.0.5.jar` ｜ 版本：`19.0.5` ｜ 作者：Jaredlll08
> 规模：**37 个类 / 8 个包 / 1666 行** ｜ 事件处理类 1 ｜ Mixin 5 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`searchables`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.blamejared.controlling.api.events` | 12 | `KeyEntryRenderEvent`、`KeyEntryMouseReleasedEvent`、`KeyEntryMouseClickedEvent` |
| `com.blamejared.controlling.mixin` | 5 | `AccessKeyBindsScreen`、`AccessOptionsSubScreen`、`AccessKeyBindsScreenNeoForge` |
| `com.blamejared.controlling.platform` | 5 | `NeoForgeEventHandler`、`IEventHelper`、`NeoForgePlatformHelper` |
| `com.blamejared.controlling.client` | 5 | `NewKeyBindsScreen`、`NewKeyBindsList`、`FreeKeysList` |
| `com.blamejared.controlling` | 3 | `Controlling`、`ControllingCommon`、`ControllingConstants` |
| `com.blamejared.controlling.api` | 3 | `ISort`、`SortOrder`、`DisplayMode` |
| `com.blamejared.controlling.api.entries` | 3 | `ICategoryEntry`、`IKeyEntry`、`IInputEntry` |
| `com.blamejared.controlling.events` | 1 | `ClientEventHandler` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | — | — | 1 | 22 | [ClientEventHandler.java](com/blamejared/controlling/events/ClientEventHandler.java) |
| `Controlling` | class | — | Mod | 1 | 20 | [Controlling.java](com/blamejared/controlling/Controlling.java) |
| `AccessKeyBindsScreen` | interface | — | Mixin | 0 | 23 | [AccessKeyBindsScreen.java](com/blamejared/controlling/mixin/AccessKeyBindsScreen.java) |
| `AccessOptionsSubScreen` | interface | — | Mixin | 0 | 13 | [AccessOptionsSubScreen.java](com/blamejared/controlling/mixin/AccessOptionsSubScreen.java) |
| `AccessKeyBindsScreenNeoForge` | interface | — | Mixin | 0 | 34 | [AccessKeyBindsScreenNeoForge.java](com/blamejared/controlling/mixin/AccessKeyBindsScreenNeoForge.java) |
| `NeoForgeEventHandler` | class | IEventHelper | — | 4 | 55 | [NeoForgeEventHandler.java](com/blamejared/controlling/platform/NeoForgeEventHandler.java) |
| `AccessInputConstantsKey` | interface | — | Mixin | 0 | 15 | [AccessInputConstantsKey.java](com/blamejared/controlling/mixin/AccessInputConstantsKey.java) |
| `AccessKeyMapping` | interface | — | Mixin | 0 | 13 | [AccessKeyMapping.java](com/blamejared/controlling/mixin/AccessKeyMapping.java) |
| `NewKeyBindsScreen` | class | KeyBindsScreen | — | 18 | 285 | [NewKeyBindsScreen.java](com/blamejared/controlling/client/NewKeyBindsScreen.java) |
| `KeyEntryRenderEvent` | class | Event | — | 11 | 101 | [KeyEntryRenderEvent.java](com/blamejared/controlling/api/events/KeyEntryRenderEvent.java) |
| `NewKeyBindsList` | class | CustomList | — | 20 | 281 | [NewKeyBindsList.java](com/blamejared/controlling/client/NewKeyBindsList.java) |
| `IEventHelper` | interface | — | — | 0 | 25 | [IEventHelper.java](com/blamejared/controlling/platform/IEventHelper.java) |
| `KeyEntryMouseReleasedEvent` | class | Event | — | 6 | 50 | [KeyEntryMouseReleasedEvent.java](com/blamejared/controlling/api/events/KeyEntryMouseReleasedEvent.java) |
| `KeyEntryMouseClickedEvent` | class | Event | — | 6 | 50 | [KeyEntryMouseClickedEvent.java](com/blamejared/controlling/api/events/KeyEntryMouseClickedEvent.java) |
| `IKeyEntryMouseReleasedEvent` | interface | — | — | 0 | 18 | [IKeyEntryMouseReleasedEvent.java](com/blamejared/controlling/api/events/IKeyEntryMouseReleasedEvent.java) |
| `IKeyEntryRenderEvent` | interface | — | — | 0 | 29 | [IKeyEntryRenderEvent.java](com/blamejared/controlling/api/events/IKeyEntryRenderEvent.java) |
| `KeyEntryListenersEvent` | class | Event | — | 2 | 30 | [KeyEntryListenersEvent.java](com/blamejared/controlling/api/events/KeyEntryListenersEvent.java) |
| `ISetToDefaultEvent` | interface | — | — | 0 | 11 | [ISetToDefaultEvent.java](com/blamejared/controlling/api/events/ISetToDefaultEvent.java) |
| `IKeyEntryListenersEvent` | interface | — | — | 0 | 12 | [IKeyEntryListenersEvent.java](com/blamejared/controlling/api/events/IKeyEntryListenersEvent.java) |
| `IHasConflictingModifierEvent` | interface | — | — | 0 | 10 | [IHasConflictingModifierEvent.java](com/blamejared/controlling/api/events/IHasConflictingModifierEvent.java) |
| `IKeyEntryMouseClickedEvent` | interface | — | — | 0 | 18 | [IKeyEntryMouseClickedEvent.java](com/blamejared/controlling/api/events/IKeyEntryMouseClickedEvent.java) |
| `ISetKeyEvent` | interface | — | — | 0 | 14 | [ISetKeyEvent.java](com/blamejared/controlling/api/events/ISetKeyEvent.java) |
| `IIsKeyCodeModifierEvent` | interface | — | — | 0 | 8 | [IIsKeyCodeModifierEvent.java](com/blamejared/controlling/api/events/IIsKeyCodeModifierEvent.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 6 | 55 | [NeoForgePlatformHelper.java](com/blamejared/controlling/platform/NeoForgePlatformHelper.java) |
| `FreeKeysList` | class | CustomList | — | 13 | 141 | [FreeKeysList.java](com/blamejared/controlling/client/FreeKeysList.java) |
| `IPlatformHelper` | interface | — | — | 0 | 50 | [IPlatformHelper.java](com/blamejared/controlling/platform/IPlatformHelper.java) |
| `DisplayableBoolean` | class | — | — | 6 | 41 | [DisplayableBoolean.java](com/blamejared/controlling/client/DisplayableBoolean.java) |
| `ControllingCommon` | class | — | — | 0 | 9 | [ControllingCommon.java](com/blamejared/controlling/ControllingCommon.java) |
| `ControllingConstants` | class | — | — | 0 | 46 | [ControllingConstants.java](com/blamejared/controlling/ControllingConstants.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/blamejared/controlling/platform/Services.java) |
| `ISort` | interface | — | — | 0 | 9 | [ISort.java](com/blamejared/controlling/api/ISort.java) |
| `SortOrder` | enum | — | — | 3 | 49 | [SortOrder.java](com/blamejared/controlling/api/SortOrder.java) |
| `DisplayMode` | enum | — | — | 1 | 37 | [DisplayMode.java](com/blamejared/controlling/api/DisplayMode.java) |
| `ICategoryEntry` | interface | — | — | 0 | 8 | [ICategoryEntry.java](com/blamejared/controlling/api/entries/ICategoryEntry.java) |
| `IKeyEntry` | interface | — | — | 0 | 29 | [IKeyEntry.java](com/blamejared/controlling/api/entries/IKeyEntry.java) |
| `IInputEntry` | interface | — | — | 0 | 8 | [IInputEntry.java](com/blamejared/controlling/api/entries/IInputEntry.java) |
| `CustomList` | class | KeyBindsList | — | 2 | 31 | [CustomList.java](com/blamejared/controlling/client/CustomList.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventHandler` | 1 | `com.blamejared.controlling.events` | [ClientEventHandler.java](com/blamejared/controlling/events/ClientEventHandler.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AccessKeyBindsScreen` | `AccessKeyBindsScreen` | [AccessKeyBindsScreen.java](com/blamejared/controlling/mixin/AccessKeyBindsScreen.java) |
| `AccessOptionsSubScreen` | `AccessOptionsSubScreen` | [AccessOptionsSubScreen.java](com/blamejared/controlling/mixin/AccessOptionsSubScreen.java) |
| `AccessKeyBindsScreenNeoForge` | `AccessKeyBindsScreenNeoForge` | [AccessKeyBindsScreenNeoForge.java](com/blamejared/controlling/mixin/AccessKeyBindsScreenNeoForge.java) |
| `AccessInputConstantsKey` | `AccessInputConstantsKey` | [AccessInputConstantsKey.java](com/blamejared/controlling/mixin/AccessInputConstantsKey.java) |
| `AccessKeyMapping` | `AccessKeyMapping` | [AccessKeyMapping.java](com/blamejared/controlling/mixin/AccessKeyMapping.java) |

## 全部类（37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | — | — | 1 | 22 | [ClientEventHandler.java](com/blamejared/controlling/events/ClientEventHandler.java) |
| `Controlling` | class | — | Mod | 1 | 20 | [Controlling.java](com/blamejared/controlling/Controlling.java) |
| `AccessKeyBindsScreen` | interface | — | Mixin | 0 | 23 | [AccessKeyBindsScreen.java](com/blamejared/controlling/mixin/AccessKeyBindsScreen.java) |
| `AccessOptionsSubScreen` | interface | — | Mixin | 0 | 13 | [AccessOptionsSubScreen.java](com/blamejared/controlling/mixin/AccessOptionsSubScreen.java) |
| `AccessKeyBindsScreenNeoForge` | interface | — | Mixin | 0 | 34 | [AccessKeyBindsScreenNeoForge.java](com/blamejared/controlling/mixin/AccessKeyBindsScreenNeoForge.java) |
| `NeoForgeEventHandler` | class | IEventHelper | — | 4 | 55 | [NeoForgeEventHandler.java](com/blamejared/controlling/platform/NeoForgeEventHandler.java) |
| `AccessInputConstantsKey` | interface | — | Mixin | 0 | 15 | [AccessInputConstantsKey.java](com/blamejared/controlling/mixin/AccessInputConstantsKey.java) |
| `AccessKeyMapping` | interface | — | Mixin | 0 | 13 | [AccessKeyMapping.java](com/blamejared/controlling/mixin/AccessKeyMapping.java) |
| `NewKeyBindsScreen` | class | KeyBindsScreen | — | 18 | 285 | [NewKeyBindsScreen.java](com/blamejared/controlling/client/NewKeyBindsScreen.java) |
| `KeyEntryRenderEvent` | class | Event | — | 11 | 101 | [KeyEntryRenderEvent.java](com/blamejared/controlling/api/events/KeyEntryRenderEvent.java) |
| `NewKeyBindsList` | class | CustomList | — | 20 | 281 | [NewKeyBindsList.java](com/blamejared/controlling/client/NewKeyBindsList.java) |
| `IEventHelper` | interface | — | — | 0 | 25 | [IEventHelper.java](com/blamejared/controlling/platform/IEventHelper.java) |
| `KeyEntryMouseReleasedEvent` | class | Event | — | 6 | 50 | [KeyEntryMouseReleasedEvent.java](com/blamejared/controlling/api/events/KeyEntryMouseReleasedEvent.java) |
| `KeyEntryMouseClickedEvent` | class | Event | — | 6 | 50 | [KeyEntryMouseClickedEvent.java](com/blamejared/controlling/api/events/KeyEntryMouseClickedEvent.java) |
| `IKeyEntryMouseReleasedEvent` | interface | — | — | 0 | 18 | [IKeyEntryMouseReleasedEvent.java](com/blamejared/controlling/api/events/IKeyEntryMouseReleasedEvent.java) |
| `IKeyEntryRenderEvent` | interface | — | — | 0 | 29 | [IKeyEntryRenderEvent.java](com/blamejared/controlling/api/events/IKeyEntryRenderEvent.java) |
| `KeyEntryListenersEvent` | class | Event | — | 2 | 30 | [KeyEntryListenersEvent.java](com/blamejared/controlling/api/events/KeyEntryListenersEvent.java) |
| `ISetToDefaultEvent` | interface | — | — | 0 | 11 | [ISetToDefaultEvent.java](com/blamejared/controlling/api/events/ISetToDefaultEvent.java) |
| `IKeyEntryListenersEvent` | interface | — | — | 0 | 12 | [IKeyEntryListenersEvent.java](com/blamejared/controlling/api/events/IKeyEntryListenersEvent.java) |
| `IHasConflictingModifierEvent` | interface | — | — | 0 | 10 | [IHasConflictingModifierEvent.java](com/blamejared/controlling/api/events/IHasConflictingModifierEvent.java) |
| `IKeyEntryMouseClickedEvent` | interface | — | — | 0 | 18 | [IKeyEntryMouseClickedEvent.java](com/blamejared/controlling/api/events/IKeyEntryMouseClickedEvent.java) |
| `ISetKeyEvent` | interface | — | — | 0 | 14 | [ISetKeyEvent.java](com/blamejared/controlling/api/events/ISetKeyEvent.java) |
| `IIsKeyCodeModifierEvent` | interface | — | — | 0 | 8 | [IIsKeyCodeModifierEvent.java](com/blamejared/controlling/api/events/IIsKeyCodeModifierEvent.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 6 | 55 | [NeoForgePlatformHelper.java](com/blamejared/controlling/platform/NeoForgePlatformHelper.java) |
| `FreeKeysList` | class | CustomList | — | 13 | 141 | [FreeKeysList.java](com/blamejared/controlling/client/FreeKeysList.java) |
| `IPlatformHelper` | interface | — | — | 0 | 50 | [IPlatformHelper.java](com/blamejared/controlling/platform/IPlatformHelper.java) |
| `DisplayableBoolean` | class | — | — | 6 | 41 | [DisplayableBoolean.java](com/blamejared/controlling/client/DisplayableBoolean.java) |
| `ControllingCommon` | class | — | — | 0 | 9 | [ControllingCommon.java](com/blamejared/controlling/ControllingCommon.java) |
| `ControllingConstants` | class | — | — | 0 | 46 | [ControllingConstants.java](com/blamejared/controlling/ControllingConstants.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/blamejared/controlling/platform/Services.java) |
| `ISort` | interface | — | — | 0 | 9 | [ISort.java](com/blamejared/controlling/api/ISort.java) |
| `SortOrder` | enum | — | — | 3 | 49 | [SortOrder.java](com/blamejared/controlling/api/SortOrder.java) |
| `DisplayMode` | enum | — | — | 1 | 37 | [DisplayMode.java](com/blamejared/controlling/api/DisplayMode.java) |
| `ICategoryEntry` | interface | — | — | 0 | 8 | [ICategoryEntry.java](com/blamejared/controlling/api/entries/ICategoryEntry.java) |
| `IKeyEntry` | interface | — | — | 0 | 29 | [IKeyEntry.java](com/blamejared/controlling/api/entries/IKeyEntry.java) |
| `IInputEntry` | interface | — | — | 0 | 8 | [IInputEntry.java](com/blamejared/controlling/api/entries/IInputEntry.java) |
| `CustomList` | class | KeyBindsList | — | 2 | 31 | [CustomList.java](com/blamejared/controlling/client/CustomList.java) |
