---
页面: configs/modernfix
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `modernfix` 配置项

> **2 个文件 / 6 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/modernfix](../../mods/modernfix/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `modernfix-mixins.properties` | `mixin.bugfix.fix_config_crashes` | `false` | string |  | `modernfix:ModernFixEarlyConfig` |
| `modernfix-mixins.properties` | `mixin.feature.integrated_server_watchdog` | `false` | string |  | `modernfix:ModernFixClient`、`modernfix:ModernFixEarlyConfig` |
| `modernfix-mixins.properties` | `mixin.feature.registry_event_progress` | `false` | string |  | `modernfix:GameDataMixin`、`modernfix:ModernFixEarlyConfig` |
| `modernfix-mixins.properties` | `mixin.feature.snapshot_easter_egg` | `false` | string |  | `modernfix:ModernFix`、`modernfix:ModernFixEarlyConfig` |
| `modernfix-mixins.properties` | `mixin.feature.warn_missing_perf_mods` | `false` | string |  | `modernfix:ModernFixEarlyConfig`、`modernfix:ModernFixForge` |
| `modernfix-common.toml` | `blacklist_async_jei_plugins` | `["jepb:jei_plugin"]` | list | These JEI plugins will be loaded on the main thread | `modernfix:ModernFixConfig` |
