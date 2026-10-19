---
页面: configs/sophisticatedcore
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `sophisticatedcore` 配置项

> **2 个文件 / 4 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/sophisticatedcore](../../mods/sophisticatedcore/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `sophisticatedcore-common.toml` | `common.enabledItems` | `["sophisticatedbackpacks:void_upgrade/tr` | list | Disable / enable any items here (disables their recipes) | `sophisticatedbackpacks:BackpackItem`、`sophisticatedcore:BlockItemBase`、`sophisticatedcore:Config` |
| `sophisticatedcore-client.toml` | `client.sortButtonsPosition` | `TITLE_LINE_RIGHT` | str | Allowed Values: TITLE_LINE_RIGHT, BELOW_UPGRADES, BELOW_UPGR | `sophisticatedcore:Config`、`sophisticatedcore:StorageScreenBase` |
| `sophisticatedcore-client.toml` | `client.playButtonSound` | `True` | bool | Whether click sound should play when custom buttons are clic | `emi:RecipeButtonWidget`、`emi:RecipeDefaultButtonWidget`、`emi:RecipeFillButtonWidget` |
| `sophisticatedcore-client.toml` | `client.mouseTweaksScrollEnabled` | `True` | bool | Whether scrolling in inventory should be handled by Mouse Tw | `sophisticatedcore:Config`、`sophisticatedcore:MouseTweaksCompatClient` |
