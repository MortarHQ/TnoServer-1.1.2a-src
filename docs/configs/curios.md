---
页面: configs/curios
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `curios` 配置项

> **3 个文件 / 11 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/curios](../../mods/curios/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `curios-common.toml` | `slots` | `["id=charm;size=2;operation=ADD", "id=ri` | list |  | `artifacts:ConfigValueCondition`、`artifacts:GeneralConfig`、`balm:CombinedContainer` |
| `curios-server.toml` | `keepCurios` | `DEFAULT` | str | Allowed Values: ON, DEFAULT, OFF | `curios:CuriosConfig`、`curios:CuriosEventHandler`、`twilightforest:CuriosCompat` |
| `curios-server.toml` | `menu.minimumColumns` | `1` | int | Range: 1 ~ 8 | `curios:CuriosConfig`、`curios:CuriosContainer` |
| `curios-server.toml` | `menu.maxSlotsPerPage` | `48` | int | Range: 1 ~ 48 | `curios:CuriosConfig`、`curios:CuriosContainer` |
| `curios-client.toml` | `client.renderCurios` | `True` | bool | Set to true to enable rendering curios | `curios:CuriosClientConfig` |
| `curios-client.toml` | `client.enableButton` | `True` | bool | Set to true to enable the Curios GUI button | `curios:CuriosClientConfig`、`curios:CuriosScreen`、`curios:GuiEventHandler` |
| `curios-client.toml` | `client.buttonXOffset` | `0` | int | Range: -100 ~ 100 | `curios:CuriosClientConfig`、`curios:CuriosScreen` |
| `curios-client.toml` | `client.buttonYOffset` | `0` | int | Range: -100 ~ 100 | `curios:CuriosClientConfig`、`curios:CuriosScreen`、`sophisticatedcore:AlchemyUpgradeTab` |
| `curios-client.toml` | `client.creativeButtonXOffset` | `0` | int | Range: -100 ~ 100 | `curios:CuriosClientConfig`、`curios:CuriosScreen` |
| `curios-client.toml` | `client.creativeButtonYOffset` | `0` | int | Range: -100 ~ 100 | `curios:CuriosClientConfig`、`curios:CuriosScreen` |
| `curios-client.toml` | `client.buttonCorner` | `TOP_LEFT` | str | Allowed Values: TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIG | `curios:CuriosClientConfig`、`curios:CuriosScreen` |
