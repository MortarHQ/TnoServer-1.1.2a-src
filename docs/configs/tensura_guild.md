---
页面: configs/tensura_guild
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `tensura_guild` 配置项

> **1 个文件 / 6 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/tensura_guild](../../mods/tensura_guild/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `tensura_guild-common.toml` | `commissionTimeLimitTicks` | `24000` | int | Range: > 0 | `tensura_guild:GuildConfig`、`tensura_guild:GuildTaskBoardScreen`、`tensura_guild:GuildTaskItem` |
| `tensura_guild-common.toml` | `commissionAutoUpdateTicks` | `12000` | int | Range: > 0 | `tensura_guild:GuildConfig`、`tensura_guild:GuildTaskEvents` |
| `tensura_guild-common.toml` | `commissionBoardMaxTasks` | `9` | int | Range: 1 ~ 9 | `tensura_guild:GuildConfig`、`tensura_guild:GuildTaskService` |
| `tensura_guild-common.toml` | `commissionWithdrawTicks` | `168000` | int | Range: > 0 | `tensura_guild:GuildConfig`、`tensura_guild:GuildTaskService` |
| `tensura_guild-common.toml` | `dwarfTasksForGuildMap` | `3` | int | Range: > 1 | `tensura_guild:DwarfTaskService`、`tensura_guild:GuildConfig` |
| `tensura_guild-common.toml` | `shopRefreshDays` | `7` | int | Range: > 1 | `tensura_guild:GuildConfig`、`tensura_guild:GuildReputationShopService` |
