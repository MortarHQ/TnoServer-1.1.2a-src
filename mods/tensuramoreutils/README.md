# TensuraMoreUtils（`tensuramoreutils`）

> jar：`[转生附属]tensuramoreutils.jar` ｜ 版本：`1.8` ｜ 作者：wal_bos
> 规模：**14 个类 / 7 个包 / 2814 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.wal_bos.moreutils.data` | 4 | `MoreUtilsData`、`MoreUtilsLeaderboardData`、`SkillBanType` |
| `com.github.wal_bos.moreutils.command` | 3 | `MoreUtilsCommands`、`MoreUtilsCapCommands`、`MoreUtilsLeaderboardCommand` |
| `com.github.wal_bos.moreutils.handler` | 3 | `MoreUtilsLeaderboardHandler`、`MoreUtilsHooks`、`MoreUtilsSkillCap` |
| `com.github.wal_bos.moreutils` | 1 | `TensuraMoreUtils` |
| `com.github.wal_bos.moreutils.mixin` | 1 | `AbilityHandlerEarlySkillBanMixin` |
| `com.github.wal_bos.moreutils.util` | 1 | `MoreUtilsPurge` |
| `com.github.wal_bos.moreutils.registry` | 1 | `MoreUtilsGameRules` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 14）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraMoreUtils` | class | — | Mod | 0 | 30 | [TensuraMoreUtils.java](com/github/wal_bos/moreutils/TensuraMoreUtils.java) |
| `AbilityHandlerEarlySkillBanMixin` | class | — | Mixin | 0 | 59 | [AbilityHandlerEarlySkillBanMixin.java](com/github/wal_bos/moreutils/mixin/AbilityHandlerEarlySkillBanMixin.java) |
| `MoreUtilsCommands` | class | — | — | 28 | 577 | [MoreUtilsCommands.java](com/github/wal_bos/moreutils/command/MoreUtilsCommands.java) |
| `MoreUtilsData` | class | — | — | 45 | 504 | [MoreUtilsData.java](com/github/wal_bos/moreutils/data/MoreUtilsData.java) |
| `MoreUtilsCapCommands` | class | — | — | 21 | 399 | [MoreUtilsCapCommands.java](com/github/wal_bos/moreutils/command/MoreUtilsCapCommands.java) |
| `MoreUtilsLeaderboardCommand` | class | ChestMenu | — | 14 | 333 | [MoreUtilsLeaderboardCommand.java](com/github/wal_bos/moreutils/command/MoreUtilsLeaderboardCommand.java) |
| `MoreUtilsLeaderboardHandler` | class | — | — | 3 | 30 | [MoreUtilsLeaderboardHandler.java](com/github/wal_bos/moreutils/handler/MoreUtilsLeaderboardHandler.java) |
| `MoreUtilsPurge` | class | — | — | 18 | 357 | [MoreUtilsPurge.java](com/github/wal_bos/moreutils/util/MoreUtilsPurge.java) |
| `MoreUtilsHooks` | class | — | — | 9 | 255 | [MoreUtilsHooks.java](com/github/wal_bos/moreutils/handler/MoreUtilsHooks.java) |
| `MoreUtilsLeaderboardData` | class | SavedData | — | 9 | 113 | [MoreUtilsLeaderboardData.java](com/github/wal_bos/moreutils/data/MoreUtilsLeaderboardData.java) |
| `MoreUtilsSkillCap` | class | — | — | 6 | 71 | [MoreUtilsSkillCap.java](com/github/wal_bos/moreutils/handler/MoreUtilsSkillCap.java) |
| `SkillBanType` | enum | — | — | 5 | 33 | [SkillBanType.java](com/github/wal_bos/moreutils/data/SkillBanType.java) |
| `MoreUtilsGameRules` | class | — | — | 1 | 22 | [MoreUtilsGameRules.java](com/github/wal_bos/moreutils/registry/MoreUtilsGameRules.java) |
| `SkillCapType` | enum | — | — | 3 | 31 | [SkillCapType.java](com/github/wal_bos/moreutils/data/SkillCapType.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AbilityHandlerEarlySkillBanMixin` | `AbilityHandlerEarlySkillBan` | [AbilityHandlerEarlySkillBanMixin.java](com/github/wal_bos/moreutils/mixin/AbilityHandlerEarlySkillBanMixin.java) |

## 全部类（14）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraMoreUtils` | class | — | Mod | 0 | 30 | [TensuraMoreUtils.java](com/github/wal_bos/moreutils/TensuraMoreUtils.java) |
| `AbilityHandlerEarlySkillBanMixin` | class | — | Mixin | 0 | 59 | [AbilityHandlerEarlySkillBanMixin.java](com/github/wal_bos/moreutils/mixin/AbilityHandlerEarlySkillBanMixin.java) |
| `MoreUtilsCommands` | class | — | — | 28 | 577 | [MoreUtilsCommands.java](com/github/wal_bos/moreutils/command/MoreUtilsCommands.java) |
| `MoreUtilsData` | class | — | — | 45 | 504 | [MoreUtilsData.java](com/github/wal_bos/moreutils/data/MoreUtilsData.java) |
| `MoreUtilsCapCommands` | class | — | — | 21 | 399 | [MoreUtilsCapCommands.java](com/github/wal_bos/moreutils/command/MoreUtilsCapCommands.java) |
| `MoreUtilsLeaderboardCommand` | class | ChestMenu | — | 14 | 333 | [MoreUtilsLeaderboardCommand.java](com/github/wal_bos/moreutils/command/MoreUtilsLeaderboardCommand.java) |
| `MoreUtilsLeaderboardHandler` | class | — | — | 3 | 30 | [MoreUtilsLeaderboardHandler.java](com/github/wal_bos/moreutils/handler/MoreUtilsLeaderboardHandler.java) |
| `MoreUtilsPurge` | class | — | — | 18 | 357 | [MoreUtilsPurge.java](com/github/wal_bos/moreutils/util/MoreUtilsPurge.java) |
| `MoreUtilsHooks` | class | — | — | 9 | 255 | [MoreUtilsHooks.java](com/github/wal_bos/moreutils/handler/MoreUtilsHooks.java) |
| `MoreUtilsLeaderboardData` | class | SavedData | — | 9 | 113 | [MoreUtilsLeaderboardData.java](com/github/wal_bos/moreutils/data/MoreUtilsLeaderboardData.java) |
| `MoreUtilsSkillCap` | class | — | — | 6 | 71 | [MoreUtilsSkillCap.java](com/github/wal_bos/moreutils/handler/MoreUtilsSkillCap.java) |
| `SkillBanType` | enum | — | — | 5 | 33 | [SkillBanType.java](com/github/wal_bos/moreutils/data/SkillBanType.java) |
| `MoreUtilsGameRules` | class | — | — | 1 | 22 | [MoreUtilsGameRules.java](com/github/wal_bos/moreutils/registry/MoreUtilsGameRules.java) |
| `SkillCapType` | enum | — | — | 3 | 31 | [SkillCapType.java](com/github/wal_bos/moreutils/data/SkillCapType.java) |
