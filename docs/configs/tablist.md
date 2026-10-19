---
页面: configs/tablist
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `tablist` 配置项

> **1 个文件 / 11 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/tablist](../../mods/tablist/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `tablist.toml` | `appearance.server_name` | `Your Server` | str | Your server's name. Use #SERVERNAME in header/footer to inse | `tablist:TabListConfig` |
| `tablist.toml` | `appearance.header` | `["#N        &#FF5555&l#SERVERNAME       ` | list | Text shown above the player list. Multiple entries create an | `architectury:SplitPacketTransformer`、`controlling:NewKeyBindsScreen`、`crafttweaker:AnnotationProcessor` |
| `tablist.toml` | `appearance.footer` | `["&fOnline: &e#PLAYERCOUNT&7/&e#MAXPLAYE` | list | Text shown below the player list. Multiple entries create an | `ponderer:WebPageFetcher`、`regions_unexplored:RUConfigScreen`、`servercore:Audience` |
| `tablist.toml` | `appearance.display_name_format` | `{name} &7#AFK` | str | Display name format. Supports {name}, {rank}, {prefix}, {suf | `tablist:TabListConfig` |
| `tablist.toml` | `appearance.update_interval` | `500` | int | How often (ms) the tab list refreshes. Range: 1-10000. Defau | `tablist:TabListConfig` |
| `tablist.toml` | `appearance.animation_interval` | `4` | int | Update cycles between animation frame changes. Range: 1-200. | `tablist:TabListConfig` |
| `tablist.toml` | `appearance.name_formatting_provider` | `FTB` | str | Name formatting and rank sorting provider: NONE, FTB, or LP  | `tablist:TabListConfig` |
| `tablist.toml` | `sorting.sort_mode` | `NONE` | str | How to sort players: NONE, ALPHABETICAL, or RANK. | `tablist:TabListConfig` |
| `tablist.toml` | `afk.afk_enabled` | `True` | bool | Enable AFK detection. AFK players have greyed-out names. | `tablist:TabListConfig` |
| `tablist.toml` | `afk.afk_timeout` | `300` | int | Seconds of inactivity before AFK. Range: 10-3600. Default: 3 | `tablist:TabListConfig` |
| `tablist.toml` | `performance.metrics_provider` | `VANILLA` | str | Metrics provider: VANILLA or SPARK. SPARK falls back to vani | `tablist:TabListConfig` |
