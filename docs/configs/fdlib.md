---
页面: configs/fdlib
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `fdlib` 配置项

> **2 个文件 / 8 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/fdlib](../../mods/fdlib/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `fdlib/test.json` | `sausage` | `minecraft:acacia_leaves` | str |  | `fdlib:TestConfig`、`fdlib:TestConfignEntry` |
| `fdlib/test.json` | `testField1` | `1` | int |  | `fdlib:TestConfig` |
| `fdlib/test.json` | `_comment_testField2` | `Testim fignyu` | str |  | — |
| `fdlib/test.json` | `testField2` | `LINEAR` | str |  | `fdlib:TestConfig` |
| `fdlib/test.json` | `testEntry.sausage` | `minecraft:acacia_boat` | str |  | `fdlib:TestConfig`、`fdlib:TestConfignEntry` |
| `fdlib/test.json` | `testEntry.testEntry3` | `23.54` | float |  | `fdlib:TestConfignEntry` |
| `fdlib/fdlib_client.json` | `_comment_impactFramesEnabled` | `Impact Frames - a fast flash of black-an` | str |  | — |
| `fdlib/fdlib_client.json` | `impactFramesEnabled` | `True` | bool |  | `fdlib:FDClientConfig`、`fdlib:ImpactFramesHandler` |
