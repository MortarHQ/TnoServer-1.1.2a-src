---
页面: configs/ancient_artifacts
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `ancient_artifacts` 配置项

> **19 个文件 / 87 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/ancient_artifacts](../../mods/ancient_artifacts/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `ancient_artifacts/main_config.toml` | `loot_changes` | `["artifacts:artifact"]` | list |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:EffectConfigs` |
| `ancient_artifacts/main_config.toml` | `artifacts.random_refinement_upgrades` | `True` | bool |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:RefinementComponent` |
| `ancient_artifacts/main_config.toml` | `artifacts.max_grade_1_artifacts` | `-1` | int |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactEffects`、`ancient_artifacts:ArtifactText` |
| `ancient_artifacts/main_config.toml` | `grade1.EPGain` | `0.02` | float |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade1.MaxRefinement` | `3.0` | float |  | `ancient_artifacts:AncientArtifactsConfig` |
| `ancient_artifacts/main_config.toml` | `grade1.StartingEp` | `225000.0` | float |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade1.EffectUnlockEp` | `[225000.0, 1000000.0, 5000000.0]` | list |  | `ancient_artifacts:AmuletLayer`、`ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade2.EPGain` | `0.02` | float |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade2.MaxRefinement` | `5.0` | float |  | `ancient_artifacts:AncientArtifactsConfig` |
| `ancient_artifacts/main_config.toml` | `grade2.StartingEp` | `52000.0` | float |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade2.EffectUnlockEp` | `[52000.0, 225000.0]` | list |  | `ancient_artifacts:AmuletLayer`、`ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade3.EPGain` | `0.02` | float |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade3.MaxRefinement` | `10.0` | float |  | `ancient_artifacts:AncientArtifactsConfig` |
| `ancient_artifacts/main_config.toml` | `grade3.StartingEp` | `52000.0` | float |  | `ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/main_config.toml` | `grade3.EffectUnlockEp` | `[52000.0]` | list |  | `ancient_artifacts:AmuletLayer`、`ancient_artifacts:AncientArtifactsConfig`、`ancient_artifacts:ArtifactCurioItem` |
| `ancient_artifacts/loot/artifacts/artifact.toml` | `loot_id` | `artifacts:artifact` | str |  | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:LootInstanceConfig` |
| `ancient_artifacts/loot/artifacts/artifact.toml` | `chance` | `0.05` | float |  | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:LootInstanceConfig`、`architectury:FoodPropertiesHooks` |
| `ancient_artifacts/loot/artifacts/artifact.toml` | `itemWeights` | `["ancient_artifacts:shadowbind_bracelet:` | list |  | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:LootInstanceConfig` |
| `ancient_artifacts/effect/unyielding_stance1.toml` | `id` | `unyielding_stance1` | str |  | — |
| `ancient_artifacts/effect/unyielding_stance1.toml` | `baseValue` | `5.0` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/unyielding_stance1.toml` | `valuePerRefinement` | `0.5` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/unyielding_stance1.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/magicule_efficiency.toml` | `id` | `magicule_efficiency` | str |  | — |
| `ancient_artifacts/effect/magicule_efficiency.toml` | `baseValue` | `-0.1` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/magicule_efficiency.toml` | `valuePerRefinement` | `-0.01` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/magicule_efficiency.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/beast_aura.toml` | `max_refinements` | `-1` | int |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/beast_aura.toml` | `ap_max_usage_increase_per_refinement` | `2000` | int |  | `ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:RoaringLionPunchArtMixin` |
| `ancient_artifacts/effect/beast_aura.toml` | `ap_max_usage_mastered` | `10000` | int |  | `ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:RoaringLionPunchArtMixin` |
| `ancient_artifacts/effect/beast_aura.toml` | `ap_max_usage` | `6000` | int |  | `ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:RoaringLionPunchArtMixin` |
| `ancient_artifacts/effect/windborne_movement.toml` | `id` | `windborne_movement` | str |  | — |
| `ancient_artifacts/effect/windborne_movement.toml` | `baseValue` | `0.15` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/windborne_movement.toml` | `valuePerRefinement` | `0.05` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/windborne_movement.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/demonic_awareness.toml` | `id` | `demonic_awareness` | str |  | — |
| `ancient_artifacts/effect/demonic_awareness.toml` | `baseValue` | `2.0` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/demonic_awareness.toml` | `valuePerRefinement` | `1.0` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/demonic_awareness.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/quick_movement.toml` | `id` | `quick_movement` | str |  | — |
| `ancient_artifacts/effect/quick_movement.toml` | `baseValue` | `0.01` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/quick_movement.toml` | `valuePerRefinement` | `0.01` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/quick_movement.toml` | `maxRefinement` | `9` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/rising_inferno.toml` | `max_stack` | `0.2` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:RisingInfernoConfig` |
| `ancient_artifacts/effect/rising_inferno.toml` | `increase_per_stack` | `0.01` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:RisingInfernoConfig` |
| `ancient_artifacts/effect/rising_inferno.toml` | `time_till_next_stack` | `3.0` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:RisingInfernoConfig` |
| `ancient_artifacts/effect/rising_inferno.toml` | `time_reduction_per_refine` | `0.25` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:RisingInfernoConfig` |
| `ancient_artifacts/effect/rising_inferno.toml` | `max_refinements` | `2` | int | Max Refinement -1 is no max | `ancient_artifacts:Artifacts`、`ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/rapid_incantation.toml` | `id` | `rapid_incantation` | str |  | — |
| `ancient_artifacts/effect/rapid_incantation.toml` | `baseValue` | `0.1` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/rapid_incantation.toml` | `valuePerRefinement` | `0.02` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/rapid_incantation.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/ogres_wrath.toml` | `health_percent_for_activate` | `0.25` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:OgresWrathConfig` |
| `ancient_artifacts/effect/ogres_wrath.toml` | `base_value` | `0.15` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CurrentMasteryConfig`、`ancient_artifacts:OgresWrathConfig` |
| `ancient_artifacts/effect/ogres_wrath.toml` | `value_per_refinement` | `0.025` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CurrentMasteryConfig`、`ancient_artifacts:OgresWrathConfig` |
| `ancient_artifacts/effect/ogres_wrath.toml` | `max_refinements` | `2` | int | Max Refinement -1 is no max | `ancient_artifacts:Artifacts`、`ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/flow_of_mastery.toml` | `id` | `flow_of_mastery` | str |  | — |
| `ancient_artifacts/effect/flow_of_mastery.toml` | `baseValue` | `1.0` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/flow_of_mastery.toml` | `valuePerRefinement` | `0.1` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/flow_of_mastery.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/magic_bullet_acceleration.toml` | `id` | `magic_bullet_acceleration` | str |  | — |
| `ancient_artifacts/effect/magic_bullet_acceleration.toml` | `baseValue` | `3.0` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/magic_bullet_acceleration.toml` | `valuePerRefinement` | `1.0` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/magic_bullet_acceleration.toml` | `maxRefinement` | `2` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/current_mastery.toml` | `base_value` | `0.5` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CurrentMasteryConfig`、`ancient_artifacts:OgresWrathConfig` |
| `ancient_artifacts/effect/current_mastery.toml` | `value_per_refinement` | `0.05` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CurrentMasteryConfig`、`ancient_artifacts:OgresWrathConfig` |
| `ancient_artifacts/effect/current_mastery.toml` | `max_refinements` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:Artifacts`、`ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/unyielding_stance2.toml` | `id` | `unyielding_stance2` | str |  | — |
| `ancient_artifacts/effect/unyielding_stance2.toml` | `baseValue` | `0.25` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/unyielding_stance2.toml` | `valuePerRefinement` | `0.05` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/unyielding_stance2.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/commanders_controlled_rage.toml` | `bonusThresholdPerRefinement` | `0.05` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/commanders_controlled_rage.toml` | `magiculePercentForNextIncrease` | `0.25` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/commanders_controlled_rage.toml` | `magiculeCostReductionPerIncrease` | `0.1` | float |  | `ancient_artifacts:Artifacts`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/commanders_controlled_rage.toml` | `max_refinements` | `3` | int | Max Refinement -1 is no max | `ancient_artifacts:Artifacts`、`ancient_artifacts:BeastAuraConfig`、`ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/commanders_controlled_rage.toml` | `mastery_unlock` | `True` | bool |  | `ancient_artifacts:CommandersControlledRageConfig` |
| `ancient_artifacts/effect/prediction_evasion.toml` | `id` | `prediction_evasion` | str |  | — |
| `ancient_artifacts/effect/prediction_evasion.toml` | `baseValue` | `0.05` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/prediction_evasion.toml` | `valuePerRefinement` | `0.0133` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/prediction_evasion.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/hell_flare_amplification.toml` | `id` | `hell_flare_amplification` | str |  | — |
| `ancient_artifacts/effect/hell_flare_amplification.toml` | `baseValue` | `0.5` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/hell_flare_amplification.toml` | `valuePerRefinement` | `0.1` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/hell_flare_amplification.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
| `ancient_artifacts/effect/sovereign_wind.toml` | `id` | `sovereign_wind` | str |  | — |
| `ancient_artifacts/effect/sovereign_wind.toml` | `baseValue` | `0.25` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/sovereign_wind.toml` | `valuePerRefinement` | `0.05` | float |  | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:UnlockableAttributeArtifactEffect`、`ancient_artifacts:UnlockableEffectConfig` |
| `ancient_artifacts/effect/sovereign_wind.toml` | `maxRefinement` | `-1` | int | Max Refinement -1 is no max | `ancient_artifacts:ArtifactEffect`、`ancient_artifacts:DescriptionArtifactEffect`、`ancient_artifacts:DynamicAttributeArtifactEffect` |
