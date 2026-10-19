# Swaying Garden（`swaying_garden`）

> jar：`[摇曳花园]swayinggarden.jar` ｜ 版本：`1.1.2.1` ｜ 作者：joe_vettek(jianzoushihu)
> 规模：**11 个类 / 8 个包 / 634 行** ｜ 事件处理类 2 ｜ Mixin 1 ｜ 注册/管理类 0

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `xueluoanping.swayinggarden.client.shader` | 3 | `IrisAttach`、`IrisHook`、`IAttach` |
| `xueluoanping.swayinggarden.mixin` | 2 | `MixinWorldRenderingSettings`、`SimpleMixinPlugin` |
| `xueluoanping.swayinggarden.client` | 1 | `ClientSetup` |
| `xueluoanping.swayinggarden.handler` | 1 | `AllListener` |
| `xueluoanping.swayinggarden` | 1 | `SwayingGarden` |
| `xueluoanping.swayinggarden.util` | 1 | `TagUtil` |
| `xueluoanping.swayinggarden.config` | 1 | `General` |
| `xueluoanping.swayinggarden.data` | 1 | `start` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 11）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientSetup` | class | — | EventBusSubscriber | 1 | 22 | [ClientSetup.java](xueluoanping/swayinggarden/client/ClientSetup.java) |
| `AllListener` | class | — | EventBusSubscriber | 1 | 26 | [AllListener.java](xueluoanping/swayinggarden/handler/AllListener.java) |
| `SwayingGarden` | class | — | Mod | 4 | 54 | [SwayingGarden.java](xueluoanping/swayinggarden/SwayingGarden.java) |
| `MixinWorldRenderingSettings` | class | IAttach | Mixin | 0 | 34 | [MixinWorldRenderingSettings.java](xueluoanping/swayinggarden/mixin/MixinWorldRenderingSettings.java) |
| `IrisAttach` | class | — | — | 8 | 208 | [IrisAttach.java](xueluoanping/swayinggarden/client/shader/IrisAttach.java) |
| `SimpleMixinPlugin` | class | IMixinConfigPlugin | — | 8 | 48 | [SimpleMixinPlugin.java](xueluoanping/swayinggarden/mixin/SimpleMixinPlugin.java) |
| `TagUtil` | class | — | — | 1 | 13 | [TagUtil.java](xueluoanping/swayinggarden/util/TagUtil.java) |
| `General` | class | — | — | 1 | 176 | [General.java](xueluoanping/swayinggarden/config/General.java) |
| `start` | class | — | — | 1 | 27 | [start.java](xueluoanping/swayinggarden/data/start.java) |
| `IrisHook` | class | — | — | 1 | 20 | [IrisHook.java](xueluoanping/swayinggarden/client/shader/IrisHook.java) |
| `IAttach` | interface | — | — | 0 | 6 | [IAttach.java](xueluoanping/swayinggarden/client/shader/IAttach.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientSetup` | 1 | `xueluoanping.swayinggarden.client` | [ClientSetup.java](xueluoanping/swayinggarden/client/ClientSetup.java) |
| `AllListener` | 1 | `xueluoanping.swayinggarden.handler` | [AllListener.java](xueluoanping/swayinggarden/handler/AllListener.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinWorldRenderingSettings` | `WorldRenderingSettings` | [MixinWorldRenderingSettings.java](xueluoanping/swayinggarden/mixin/MixinWorldRenderingSettings.java) |

## 全部类（11）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientSetup` | class | — | EventBusSubscriber | 1 | 22 | [ClientSetup.java](xueluoanping/swayinggarden/client/ClientSetup.java) |
| `AllListener` | class | — | EventBusSubscriber | 1 | 26 | [AllListener.java](xueluoanping/swayinggarden/handler/AllListener.java) |
| `SwayingGarden` | class | — | Mod | 4 | 54 | [SwayingGarden.java](xueluoanping/swayinggarden/SwayingGarden.java) |
| `MixinWorldRenderingSettings` | class | IAttach | Mixin | 0 | 34 | [MixinWorldRenderingSettings.java](xueluoanping/swayinggarden/mixin/MixinWorldRenderingSettings.java) |
| `IrisAttach` | class | — | — | 8 | 208 | [IrisAttach.java](xueluoanping/swayinggarden/client/shader/IrisAttach.java) |
| `SimpleMixinPlugin` | class | IMixinConfigPlugin | — | 8 | 48 | [SimpleMixinPlugin.java](xueluoanping/swayinggarden/mixin/SimpleMixinPlugin.java) |
| `TagUtil` | class | — | — | 1 | 13 | [TagUtil.java](xueluoanping/swayinggarden/util/TagUtil.java) |
| `General` | class | — | — | 1 | 176 | [General.java](xueluoanping/swayinggarden/config/General.java) |
| `start` | class | — | — | 1 | 27 | [start.java](xueluoanping/swayinggarden/data/start.java) |
| `IrisHook` | class | — | — | 1 | 20 | [IrisHook.java](xueluoanping/swayinggarden/client/shader/IrisHook.java) |
| `IAttach` | interface | — | — | 0 | 6 | [IAttach.java](xueluoanping/swayinggarden/client/shader/IAttach.java) |
