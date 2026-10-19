---
页面: configs/mortal_cultivation
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `mortal_cultivation` 配置项

> **2 个文件 / 2 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/mortal_cultivation](../../mods/mortal_cultivation/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `mortal_cultivation-alchemy_furnace.toml` | `alchemy_furnace.fuel_settings.fuel_speed_modifiers` | `["mortal_cultivation:mud_essence_charcoa` | list | 格式: 'modid:item_id,speed_multiplier' | `mortal_cultivation:AlchemyFurnaceConfig` |
| `mortal_cultivation/general.toml` | `General.refresh` | `False` | bool | Should the Tensura configurations add Mortal Cultivation's s | `cataclysm:LightningRender`、`eternal_starlight:BoltRenderer`、`ftbchunks:FTBChunksClientConfig` |
