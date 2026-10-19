---
页面: configs/balm
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `balm` 配置项

> **2 个文件 / 20 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/balm](../../mods/balm/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `balm-common.toml` | `exampleEnum` | `Hello` | str | Allowed Values: Hello, World | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleInt` | `42` | int | This is an example int property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleIntList` | `[12, 24]` | list | This is an example int list property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleBoolean` | `True` | bool | This is an example boolean property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleString` | `Hello World` | str | This is an example string property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleStringList` | `["Hello", "World"]` | list | This is an example string list property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleEnumList` | `["Hello", "World"]` | list | This is an example enum list property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleResourceLocationSet` | `["minecraft:dirt", "minecraft:diamond"]` | list | This is an example resource location set property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleCategory.innerField` | `I am inside` | str | This is an example string inside a category | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-common.toml` | `exampleCategory.exampleFloat` | `42.84000015258789` | float | This is an example float inside a category | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleEnum` | `Hello` | str | Allowed Values: Hello, World | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleInt` | `42` | int | This is an example int property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleIntList` | `[12, 24]` | list | This is an example int list property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleBoolean` | `True` | bool | This is an example boolean property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleString` | `Hello World` | str | This is an example string property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleStringList` | `["Hello", "World"]` | list | This is an example string list property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleEnumList` | `["Hello", "World"]` | list | This is an example enum list property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleResourceLocationSet` | `["minecraft:dirt", "minecraft:diamond"]` | list | This is an example resource location set property | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleCategory.innerField` | `I am inside` | str | This is an example string inside a category | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
| `balm-client.toml` | `exampleCategory.exampleFloat` | `42.84000015258789` | float | This is an example float inside a category | `balm:ExampleDeclarativeConfig`、`balm:ExampleReflectionConfig` |
