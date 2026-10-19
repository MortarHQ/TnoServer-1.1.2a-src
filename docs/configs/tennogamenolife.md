---
页面: configs/tennogamenolife
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `tennogamenolife` 配置项

> **2 个文件 / 15 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/tennogamenolife](../../mods/tennogamenolife/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `tennogamenolife-common.toml` | `skills.enableSkillSpecialization` | `True` | bool | Enable skill specialization feature | `tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `skills.skillExperienceMultiplier` | `1.0` | float | Range: 0.1 ~ 10.0 | `tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `skills.maxSkillLevel` | `100` | int | Range: 1 ~ 1000 | `tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `skills.availability.barrier` | `True` | bool | Enable the Barrier skill | `eternal_starlight:ESLakeFeature`、`foxablazeultimate:AzathothCombat`、`foxablazeultimate:AzathothConfig` |
| `tennogamenolife-common.toml` | `skills.availability.magic_beads` | `True` | bool | Enable the Magic Beads skill | `tennogamenolife:MagicBeadsSkill`、`tennogamenolife:TenreincarnationConfig`、`tennogamenolife:TenreincarnationSkill` |
| `tennogamenolife-common.toml` | `skills.availability.tengeki` | `True` | bool | Enable the Tengeki skill | `tennogamenolife:ChargeBarRenderer`、`tennogamenolife:FlugelRaceEventHandler`、`tennogamenolife:TengekiUltimateSkill` |
| `tennogamenolife-common.toml` | `skills.availability.reisen` | `True` | bool | Enable the Reisen skill | `tennogamenolife:DragonRoarChargeLockClient`、`tennogamenolife:ReisenUltimateSkill`、`tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `skills.availability.far_cry` | `True` | bool | Enable the Far Cry skill | `tennogamenolife:ChargeBarRenderer`、`tennogamenolife:FarCryUltimateSkill`、`tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `skills.availability.black_arrow` | `True` | bool | Enable the Black Arrow skill | `tennogamenolife:BlackArrowSkill`、`tennogamenolife:TenreincarnationConfig`、`tennogamenolife:TenreincarnationSkill` |
| `tennogamenolife-common.toml` | `skills.availability.godly_smite` | `True` | bool | Enable the Godly Smite skill | `tennogamenolife:GodlySmiteUltimateSkill`、`tennogamenolife:LaserRainDebugCommand`、`tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `startingEquipment.giveExMachinaWeapon` | `True` | bool | 是否让机铠种玩家在开局时自动获得机铠炮。 | `tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `startingEquipment.giveFlugelSickle` | `True` | bool | 是否让天翼种玩家在开局时自动获得天翼之镰。 | `tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `safety.destructiveSkillsDisabled` | `False` | bool | Default: false (skills enabled). | `tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife-common.toml` | `safety.preventTerrainDamage` | `False` | bool | Recommended for hardcore PvP / survival servers that don't w | `tennogamenolife:ReisenUltimateSkill`、`tennogamenolife:TengekiUltimateSkill`、`tennogamenolife:TenreincarnationConfig` |
| `tennogamenolife/general.toml` | `General.refresh` | `False` | bool | Should the Tensura configurations add TenNoGameNoLife's skil | `cataclysm:LightningRender`、`eternal_starlight:BoltRenderer`、`ftbchunks:FTBChunksClientConfig` |
