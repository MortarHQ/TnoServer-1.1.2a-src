---
页面: configs/lodestone
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `lodestone` 配置项

> **1 个文件 / 5 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/lodestone](../../mods/lodestone/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `lodestone-client.toml` | `graphics.particle.buffer_particles` | `True` | bool | Should particles render on the delayed buffer? This means th | `lodestone:ClientConfig` |
| `lodestone-client.toml` | `graphics.particle.screen_particles` | `True` | bool | Should screen particles be visible? | `fdlib:CutsceneCameraHandler`、`fdlib:FDScreenParticle`、`fdlib:FDScreenParticleEngine` |
| `lodestone-client.toml` | `graphics.fire.fire_overlay_offset` | `0.0` | float | Range: 0.0 ~ 1.0 | `lodestone:ClientConfig` |
| `lodestone-client.toml` | `screenshake.screenshake_intensity` | `1.0` | float | Range: 0.0 ~ 5.0 | `lodestone:ClientConfig`、`lodestone:LodestoneLangDatagen` |
| `lodestone-client.toml` | `model.lod_distance_multiplier` | `1.0` | float | Range: 0.1 ~ 10.0 | `lodestone:ClientConfig` |
