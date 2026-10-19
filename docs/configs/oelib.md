---
页面: configs/oelib
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `oelib` 配置项

> **2 个文件 / 16 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/oelib](../../mods/oelib/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `oelib/dev_features.toml` | `__cfg_version` | `0` | int |  | `oelib:ConfigIOUtil`、`oelib:ConfigSerializationUtil` |
| `oelib/dev_features.toml` | `testList` | `["test1", "test2"]` | list | 测试 List 用 | `oelib:DevConfig` |
| `oelib/dev_features.toml` | `enableExampleContent` | `False` | bool | 是否启用示例/测试内容 | `oelib:BlessToastCommand`、`oelib:DevConfig`、`oelib:ExampleInit` |
| `oelib/dev_features.toml` | `testString` | `test` | str | 测试 String 用 | `oelib:DevConfig` |
| `oelib/dev_features.toml` | `testInt` | `50` | int | 测试 Int 用 | `oelib:DevConfig` |
| `oelib/dev_features.toml` | `testEnum` | `TEST` | str | 测试 Enum 用 | `oelib:DevConfig` |
| `oelib/dev_features.toml` | `testDouble` | `50.0` | float | 测试 Double 用 | `oelib:DevConfig` |
| `oelib/oelib_festivals.toml` | `__cfg_version` | `0` | int |  | `oelib:ConfigIOUtil`、`oelib:ConfigSerializationUtil` |
| `oelib/oelib_festivals.toml` | `chineseFestivalsOnlyForChineseLanguage` | `True` | bool | 仅在中文语言环境显示中文节日 | `oelib:BlessToastCommand`、`oelib:BlessUtil`、`oelib:FestivalToastConfig` |
| `oelib/oelib_festivals.toml` | `enabled` | `True` | bool | 是否启用节日祝福 | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `oelib/oelib_festivals.toml` | `festivals.new_year.enabled` | `True` | bool | 是否启用节日祝福 | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `oelib/oelib_festivals.toml` | `festivals.new_year.lastShownDate` | `` | str |  | `oelib:BlessUtil`、`oelib:FestivalToastConfig` |
| `oelib/oelib_festivals.toml` | `festivals.chongyang.enabled` | `True` | bool | 是否启用节日祝福 | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `oelib/oelib_festivals.toml` | `festivals.chongyang.lastShownDate` | `` | str |  | `oelib:BlessUtil`、`oelib:FestivalToastConfig` |
| `oelib/oelib_festivals.toml` | `festivals.valentine.enabled` | `True` | bool | 是否启用节日祝福 | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `oelib/oelib_festivals.toml` | `festivals.valentine.lastShownDate` | `2026-02-14` | str |  | `oelib:BlessUtil`、`oelib:FestivalToastConfig` |
