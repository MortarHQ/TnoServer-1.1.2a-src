---
页面: configs/vanillabackport
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `vanillabackport` 配置项

> **2 个文件 / 39 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/vanillabackport](../../mods/vanillabackport/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `vanillabackport-client.toml` | `Spring to Life.falling_leaves` | `True` | bool | allow falling leaves particles to generate | `vanillabackport:ClientConfig` |
| `vanillabackport-client.toml` | `Spring to Life.falling_leaves_frequency` | `0.01` | float | Range: 0.0 ~ 1.0 | `vanillabackport:ClientConfig` |
| `vanillabackport-client.toml` | `Spring to Life.use_legacy_spawn_eggs` | `False` | bool | use the legacy spawn egg textures | `vanillabackport:ClientConfig` |
| `vanillabackport-client.toml` | `Spring to Life.use_sheep_wool_undercoat` | `True` | bool | toggle the colored sheep wool undercoat | `vanillabackport:ClientConfig` |
| `vanillabackport-common.toml` | `The Garden Awakens.resin` | `True` | bool | allow resin to be obtainable from creaking hearts | `vanillabackport:CommonConfig`、`vanillabackport:ModSoundEvents`、`vanillabackport:ModTrimMaterials` |
| `vanillabackport-common.toml` | `The Garden Awakens.resin_loot` | `True` | bool | allow resin to appear on chests at woodland mansions | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `The Garden Awakens.creaking` | `True` | bool | allow creakings to spawn from creaking hearts | `vanillabackport:CommonConfig`、`vanillabackport:CommonSetup`、`vanillabackport:Creaking` |
| `vanillabackport-common.toml` | `The Garden Awakens.pale_garden` | `True` | bool | allow the pale garden to generate in the overworld | `vanillabackport:CommonConfig`、`vanillabackport:ModBiomes` |
| `vanillabackport-common.toml` | `The Garden Awakens.pale_trades` | `True` | bool | allow features from 'The Garden Awakens' to be obtainable th | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `The Garden Awakens.creaking_particle_color` | `6250335` | int | creaking heart trail particle color (gray by default) | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `The Garden Awakens.creaking_particle_reverse_color` | `16545810` | int | creaking heart trail particle reverse color (orange by defau | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `The Garden Awakens.do_creaking_hearts_work_on_day` | `False` | bool | allow Creaking Hearts to generate Creakings even during the  | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Bundles of Bravery.has_updated_bundles` | `True` | bool | toggle the updated controls and UI for bundles | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Bundles of Bravery.bundle_loot` | `True` | bool | allow bundles to appear on chests at villages | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.bushes` | `True` | bool | allow bushes to generate in the overworld | `regions_unexplored:RUCommonConfig`、`twilightforest:LangGenerator`、`vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.firefly_bushes` | `True` | bool | allow firefly bushes to generate in the overworld | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.wildflowers` | `True` | bool | allow wildflowers to generate in the overworld | `vanillabackport:CommonConfig`、`vanillabackport:ModBlocks` |
| `vanillabackport-common.toml` | `Spring to Life.dry_grass` | `True` | bool | allow dry grass to generate in the overworld | `vanillabackport:CommonConfig`、`vanillabackport:ModSoundEvents` |
| `vanillabackport-common.toml` | `Spring to Life.fallen_trees` | `True` | bool | allow fallen trees to generate in the overworld | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.leaf_litter` | `True` | bool | allow leaf litter to generate in the overworld | `regions_unexplored:RUSoundEvents`、`vanillabackport:ChunkSerializerMixin`、`vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.cactus_flowers` | `True` | bool | allow cactus flowers to generate | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.farm_animal_variants` | `True` | bool | allow variants for pigs, cows and chickens to generate | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.wolf_sound_variants` | `True` | bool | allow wolfs to have variants for their sounds | `vanillabackport:CommonConfig`、`vanillabackport:CommonSetup` |
| `vanillabackport-common.toml` | `Spring to Life.spring_trades` | `True` | bool | allow features from 'Spring to Life' to be obtainable throug | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.camel_spawns` | `True` | bool | allow camels to spawn outside of villages | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Spring to Life.lodestone_loot` | `True` | bool | allow lodestones to appear on chests at ruined portals | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chase the Skies.dried_ghasts` | `True` | bool | allow dried ghasts to be obtainable through nether fossils a | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chase the Skies.leash_drop_connections` | `True` | bool | toggle whether leashes drop their connections when boosting  | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chase the Skies.tears_music_disc` | `True` | bool | allows ghasts drop the tears music disc | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chase the Skies.happy_ghast_speed_modifier` | `1.0` | float | apply a modifier to the speed of happy ghasts when ridden, 1 | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Hot as Lava.lava_chicken_music_disc` | `True` | bool | allows chicken jockeys to drop the lava chicken music disc | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.has_sulfur_caves` | `True` | bool | allow the generation of Sulfur Cave biomes | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.has_sulfur_springs` | `True` | bool | allow the generation of Sulfur Springs on top of Sulfur Cave | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.do_geysers_erupt` | `True` | bool | allow Potent Sulfur Blocks to generate Geysers | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.has_sulfur_cubes` | `True` | bool | allow Sulfur Cubes to spawn on Sulfur Caves | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.do_sulfur_cubes_explode` | `True` | bool | allow Sulfur Cubes with exploding archetypes to explode | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.do_sulfur_cubes_damage` | `True` | bool | allow Sulfur Cubes with damaging archetypes to deal damage | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.do_merchant_trade_chaos_cubed_contents` | `True` | bool | allow Wanderer traders to sell contents from Chaos Cubed | `vanillabackport:CommonConfig` |
| `vanillabackport-common.toml` | `Chaos Cubed.has_bounce_music_disc` | `True` | bool | allows the bounce music disc to generate on abandoned minesh | `vanillabackport:CommonConfig` |
