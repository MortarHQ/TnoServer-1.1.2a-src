---
页面: configs/tensura_tno
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `tensura_tno` 配置项

> **5 个文件 / 19 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/tensura_tno](../../mods/tensura_tno/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `tensura_tno-common.toml` | `compatibility_patches.npcWorkingDefaultDisabled` | `True` | bool | Change the default value of the npcWorking game rule from tr | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-common.toml` | `compatibility_patches.sophisticatedBackpacksEvolutionSlotRefresh` | `True` | bool | Refresh Sophisticated Backpacks slot components after Tensur | — |
| `tensura_tno-common.toml` | `compatibility_patches.soulGradePerPrestige` | `20` | int | Range: 0 ~ 100 | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-common.toml` | `performance.mowzieAvoidGoalOptimization` | `True` | bool | Eliminates ~2-5% server tick cost per BladeTiger / Tensura t | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-common.toml` | `gameplay.rimuruModeCheckboxEnabled` | `False` | bool | 'rimuruMode' game rule is OFF. | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-client.toml` | `compatibility_patches.vanillaBackportDelayedSheepLayer` | `True` | bool | Delay VanillaBackport sheep undercoat layer registration unt | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-client.toml` | `compatibility_patches.curiosSlotAttributeConcurrency` | `True` | bool | Make Curios SlotAttribute.getOrCreate thread-safe to avoid c | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-client.toml` | `rendering.racePlayerModels` | `True` | bool | Disable to use the normal player model for Slime, Lizardman, | `tensura_tno:TensuraTNOCompatConfig` |
| `tensura_tno-browser.properties` | `homepage` | `https://docs.qq.com/aio/p/scjg6678qvr4qq` | string |  | `puzzleslib:NeoForgeModContainer` |
| `tensura_tno-browser.properties` | `webview2.block_new_windows` | `true` | string |  | — |
| `tensura_tno-browser.properties` | `webview2.remove_workbench_titlebar` | `true` | string |  | — |
| `tensura_tno-browser.properties` | `webview2.remove_transition_space` | `true` | string |  | — |
| `tensura_tno-browser.properties` | `startup.force_first_open_url` | `true` | string |  | — |
| `tensura_tno-browser.properties` | `startup.first_open_url` | `https://docs.qq.com/aio/p/scjg6678qvr4qq` | string |  | — |
| `tensura_tno-spawn.toml` | `spawn.spawnBiomeBlacklistEnabled` | `True` | bool | left untouched regardless of the configured blacklist. | `tensura_tno:BiomeBlacklistConfig` |
| `tensura_tno-spawn.toml` | `spawn.spawnBiomeBlacklist` | `["tensura:ancient_forest", "tensura:dese` | list | poisoning to unprotected new players. | `tensura_tno:BiomeBlacklistConfig` |
| `tensura_tno-spawn.toml` | `spawn.spawnSafeSearchRadius` | `1024` | int | Range: 64 ~ 8192 | `tensura_tno:BiomeBlacklistConfig`、`tensura_tno:SafeSpawnFinder` |
| `tensura_tno-spawn.toml` | `spawn.spawnSafeSearchStep` | `64` | int | Range: 16 ~ 512 | `tensura_tno:BiomeBlacklistConfig`、`tensura_tno:SafeSpawnFinder` |
| `tensura_tno/general.toml` | `General.refresh` | `False` | bool | Should the Tensura configurations add Tensura TNO's skills a | `cataclysm:LightningRender`、`eternal_starlight:BoltRenderer`、`ftbchunks:FTBChunksClientConfig` |
