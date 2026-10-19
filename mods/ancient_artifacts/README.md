# Ancient Artifacts（`ancient_artifacts`）

> jar：`[转生附属]AncientArtifacts.jar` ｜ 版本：`1.0.0` ｜ 作者：?
> 规模：**86 个类 / 25 个包 / 5445 行** ｜ 事件处理类 2 ｜ Mixin 14 ｜ 注册/管理类 4

**依赖**：`neoforge`、`minecraft`、`architectury`、`curios`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `org.crypticdev.ancient_artifacts.artifact` | 12 | `ArtifactEffect`、`UnlockableAttributeArtifactEffect`、`ArtifactItem` |
| `org.crypticdev.ancient_artifacts.artifact.models` | 9 | `BootsModel`、`BraceletsModel`、`AmuletModel` |
| `org.crypticdev.ancient_artifacts.client` | 9 | `HornsLayer`、`TasselsLayer`、`FinLayer` |
| `org.crypticdev.ancient_artifacts.config.dynamic_effects` | 6 | `CommandersControlledRageConfig`、`OgresWrathConfig`、`KingsAwakeningConfig` |
| `org.crypticdev.ancient_artifacts.registry` | 6 | `ArtifactEffectsRegistry`、`ArtifactAttributesRegistry`、`ArtifactItems` |
| `org.crypticdev.ancient_artifacts.config` | 5 | `LootInstanceConfig`、`DefaultLootInstanceConfigs`、`SpecialEffectConfig` |
| `org.crypticdev.ancient_artifacts` | 5 | `EffectConfigs`、`AncientArtifactsClient`、`AncientArtifacts` |
| `org.crypticdev.ancient_artifacts.platform` | 4 | `PlatformItems`、`PlatformEquipment`、`PlatformCombatHelper` |
| `org.crypticdev.ancient_artifacts.platform.neoforge` | 4 | `PlatformItemsImpl`、`PlatformCombatHelperImpl`、`PlatformEquipmentImpl` |
| `org.crypticdev.ancient_artifacts.neoforge` | 3 | `AncientArtifactsNeoForgeClient`、`AncientArtifactsDataGen`、`AncientArtifactsNeoForge` |
| `org.crypticdev.ancient_artifacts.mixin.tensura.ability.skill.unique` | 3 | `CommanderSkillMixin`、`ShadowStrikerSkillMixin`、`RoyalBeastSkillMixin` |
| `org.crypticdev.ancient_artifacts.component` | 3 | `RefinementComponent`、`UnlockComponent`、`AttackerArmorComponent` |
| `org.crypticdev.ancient_artifacts.mixin.tensura.handler` | 2 | `DeathHandlerMixin`、`DamageHandlerMixin` |
| `org.crypticdev.ancient_artifacts.mixin.world.entity.player` | 2 | `EntityMixin`、`PlayerMixin` |
| `org.crypticdev.ancient_artifacts.mixin.tensura.ability.battlewill.projectile` | 2 | `MagicBulletArtMixin`、`MaximumMagicBulletArtMixin` |
| `org.crypticdev.ancient_artifacts.attachments` | 2 | `CombatTracker`、`CombatData` |
| `org.crypticdev.ancient_artifacts.mixin.world.inventory` | 1 | `AnvilMenuMixin` |
| `org.crypticdev.ancient_artifacts.mixin.tensura.ability.battlewill` | 1 | `RoaringLionPunchArtMixin` |
| `org.crypticdev.ancient_artifacts.mixin.tensura.ability.skill.extra` | 1 | `BlackFlameSkillMixin` |
| `org.crypticdev.ancient_artifacts.mixin.tensura.storage.ep` | 1 | `ExistenceStorageMixin` |
| `org.crypticdev.ancient_artifacts.mixin.fabric` | 1 | `PlayerFabricMixin` |
| `org.crypticdev.ancient_artifacts.neoforge.integration.curios` | 1 | `ArtifactCurioItem` |
| `architectury_inject_ancient_artifacts_common_a9051de710cb486d9467324da99222e3_fef0e0342f56de02fb4c18de405e14af3d873906f36f73f9d312670874150ca1AncientArtifactscommon100devjar` | 1 | `PlatformMethods` |
| `org.crypticdev.ancient_artifacts.artifact.animations` | 1 | `BeltAnimationsTest` |
| `org.crypticdev.ancient_artifacts.neoforge.datagen` | 1 | `AncientArtifactsModelProvider` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AncientArtifactsNeoForgeClient` | class | — | EventBusSubscriber | 2 | 33 | [AncientArtifactsNeoForgeClient.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsNeoForgeClient.java) |
| `AncientArtifactsDataGen` | class | — | EventBusSubscriber | 1 | 21 | [AncientArtifactsDataGen.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsDataGen.java) |
| `DeathHandlerMixin` | class | — | Mixin | 3 | 107 | [DeathHandlerMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/handler/DeathHandlerMixin.java) |
| `DamageHandlerMixin` | class | — | Mixin | 1 | 42 | [DamageHandlerMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/handler/DamageHandlerMixin.java) |
| `AncientArtifactsNeoForge` | class | — | Mod | 0 | 26 | [AncientArtifactsNeoForge.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsNeoForge.java) |
| `CommanderSkillMixin` | class | — | Mixin | 1 | 33 | [CommanderSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/CommanderSkillMixin.java) |
| `AnvilMenuMixin` | class | ItemCombinerMenu | Mixin | 1 | 80 | [AnvilMenuMixin.java](org/crypticdev/ancient_artifacts/mixin/world/inventory/AnvilMenuMixin.java) |
| `EntityMixin` | class | — | Mixin | 3 | 82 | [EntityMixin.java](org/crypticdev/ancient_artifacts/mixin/world/entity/player/EntityMixin.java) |
| `ShadowStrikerSkillMixin` | class | Skill | Mixin | 7 | 232 | [ShadowStrikerSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/ShadowStrikerSkillMixin.java) |
| `RoyalBeastSkillMixin` | class | Skill | Mixin | 5 | 242 | [RoyalBeastSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/RoyalBeastSkillMixin.java) |
| `PlayerMixin` | class | LivingEntity | Mixin | 7 | 158 | [PlayerMixin.java](org/crypticdev/ancient_artifacts/mixin/world/entity/player/PlayerMixin.java) |
| `LootInstanceConfig` | class | ManasConfig | — | 1 | 33 | [LootInstanceConfig.java](org/crypticdev/ancient_artifacts/config/LootInstanceConfig.java) |
| `DefaultLootInstanceConfigs` | class | — | — | 0 | 24 | [DefaultLootInstanceConfigs.java](org/crypticdev/ancient_artifacts/config/DefaultLootInstanceConfigs.java) |
| `CommandersControlledRageConfig` | class | SpecialEffectConfig | — | 2 | 23 | [CommandersControlledRageConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/CommandersControlledRageConfig.java) |
| `RoaringLionPunchArtMixin` | class | Battlewill | Mixin | 2 | 63 | [RoaringLionPunchArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/RoaringLionPunchArtMixin.java) |
| `MagicBulletArtMixin` | class | — | Mixin | 1 | 31 | [MagicBulletArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/projectile/MagicBulletArtMixin.java) |
| `MaximumMagicBulletArtMixin` | class | — | Mixin | 1 | 34 | [MaximumMagicBulletArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/projectile/MaximumMagicBulletArtMixin.java) |
| `BlackFlameSkillMixin` | class | — | Mixin | 2 | 38 | [BlackFlameSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/extra/BlackFlameSkillMixin.java) |
| `ExistenceStorageMixin` | class | — | Mixin | 1 | 63 | [ExistenceStorageMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/storage/ep/ExistenceStorageMixin.java) |
| `PlayerFabricMixin` | class | PlayerFabric | Mixin | 2 | 65 | [PlayerFabricMixin.java](org/crypticdev/ancient_artifacts/mixin/fabric/PlayerFabricMixin.java) |
| `ArtifactEffectsRegistry` | class | — | — | 0 | 8 | [ArtifactEffectsRegistry.java](org/crypticdev/ancient_artifacts/registry/ArtifactEffectsRegistry.java) |
| `ArtifactAttributesRegistry` | class | — | — | 2 | 27 | [ArtifactAttributesRegistry.java](org/crypticdev/ancient_artifacts/registry/ArtifactAttributesRegistry.java) |
| `EffectConfigs` | class | — | — | 1 | 33 | [EffectConfigs.java](org/crypticdev/ancient_artifacts/EffectConfigs.java) |
| `ArtifactEffect` | interface | — | — | 15 | 217 | [ArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/ArtifactEffect.java) |
| `SpecialEffectConfig` | class | ManasConfig | — | 1 | 8 | [SpecialEffectConfig.java](org/crypticdev/ancient_artifacts/config/SpecialEffectConfig.java) |
| `UnlockableEffectConfig` | class | ManasConfig | — | 1 | 28 | [UnlockableEffectConfig.java](org/crypticdev/ancient_artifacts/config/UnlockableEffectConfig.java) |
| `AncientArtifactsConfig` | class | ManasConfig | — | 2 | 46 | [AncientArtifactsConfig.java](org/crypticdev/ancient_artifacts/config/AncientArtifactsConfig.java) |
| `OgresWrathConfig` | class | SpecialEffectConfig | — | 2 | 22 | [OgresWrathConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/OgresWrathConfig.java) |
| `KingsAwakeningConfig` | class | SpecialEffectConfig | — | 2 | 15 | [KingsAwakeningConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/KingsAwakeningConfig.java) |
| `RisingInfernoConfig` | class | SpecialEffectConfig | — | 2 | 23 | [RisingInfernoConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/RisingInfernoConfig.java) |
| `BeastAuraConfig` | class | SpecialEffectConfig | — | 2 | 20 | [BeastAuraConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/BeastAuraConfig.java) |
| `CurrentMasteryConfig` | class | SpecialEffectConfig | — | 2 | 21 | [CurrentMasteryConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/CurrentMasteryConfig.java) |
| `ArtifactCurioItem` | class | Item | — | 5 | 84 | [ArtifactCurioItem.java](org/crypticdev/ancient_artifacts/neoforge/integration/curios/ArtifactCurioItem.java) |
| `ArtifactItems` | class | — | — | 5 | 63 | [ArtifactItems.java](org/crypticdev/ancient_artifacts/registry/ArtifactItems.java) |
| `AncientArtifactsClient` | class | — | — | 1 | 33 | [AncientArtifactsClient.java](org/crypticdev/ancient_artifacts/AncientArtifactsClient.java) |
| `UnlockableAttributeArtifactEffect` | record | ArtifactEffect | — | 6 | 207 | [UnlockableAttributeArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/UnlockableAttributeArtifactEffect.java) |
| `ArtifactItem` | interface | — | — | 0 | 44 | [ArtifactItem.java](org/crypticdev/ancient_artifacts/artifact/ArtifactItem.java) |
| `PlatformItems` | class | — | — | 2 | 26 | [PlatformItems.java](org/crypticdev/ancient_artifacts/platform/PlatformItems.java) |
| `PlatformItemsImpl` | class | — | — | 1 | 16 | [PlatformItemsImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformItemsImpl.java) |
| `ArtifactItemTab` | class | — | — | 1 | 24 | [ArtifactItemTab.java](org/crypticdev/ancient_artifacts/registry/ArtifactItemTab.java) |
| `ArtifactEffects` | class | — | — | 6 | 113 | [ArtifactEffects.java](org/crypticdev/ancient_artifacts/artifact/ArtifactEffects.java) |
| `ArtifactDefinition` | record | — | — | 8 | 82 | [ArtifactDefinition.java](org/crypticdev/ancient_artifacts/artifact/ArtifactDefinition.java) |
| `DynamicAttributeArtifactEffect` | record | ArtifactEffect | — | 6 | 96 | [DynamicAttributeArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/DynamicAttributeArtifactEffect.java) |
| `MultiArtifactEffect` | record | ArtifactEffect | — | 5 | 46 | [MultiArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/MultiArtifactEffect.java) |
| `BootsModel` | class | HierarchicalModel | — | 5 | 79 | [BootsModel.java](org/crypticdev/ancient_artifacts/artifact/models/BootsModel.java) |
| `BraceletsModel` | class | HierarchicalModel | — | 5 | 144 | [BraceletsModel.java](org/crypticdev/ancient_artifacts/artifact/models/BraceletsModel.java) |
| `CombatTracker` | class | — | — | 6 | 39 | [CombatTracker.java](org/crypticdev/ancient_artifacts/attachments/CombatTracker.java) |
| `CombatData` | class | — | — | 4 | 38 | [CombatData.java](org/crypticdev/ancient_artifacts/attachments/CombatData.java) |
| `PlatformEquipment` | class | — | — | 6 | 49 | [PlatformEquipment.java](org/crypticdev/ancient_artifacts/platform/PlatformEquipment.java) |
| `PlatformCombatHelper` | class | — | — | 4 | 33 | [PlatformCombatHelper.java](org/crypticdev/ancient_artifacts/platform/PlatformCombatHelper.java) |
| `PlatformCombatHelperImpl` | class | — | — | 4 | 24 | [PlatformCombatHelperImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformCombatHelperImpl.java) |
| `PlatformEquipmentImpl` | class | — | — | 6 | 83 | [PlatformEquipmentImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformEquipmentImpl.java) |
| `ArtifactDataComponents` | class | — | — | 1 | 31 | [ArtifactDataComponents.java](org/crypticdev/ancient_artifacts/registry/ArtifactDataComponents.java) |
| `Artifacts` | class | — | — | 0 | 282 | [Artifacts.java](org/crypticdev/ancient_artifacts/registry/Artifacts.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_ancient_artifacts_common_a9051de710cb486d9467324da99222e3_fef0e0342f56de02fb4c18de405e14af3d873906f36f73f9d312670874150ca1AncientArtifactscommon100devjar/PlatformMethods.java) |
| `AncientArtifacts` | class | — | — | 1 | 151 | [AncientArtifacts.java](org/crypticdev/ancient_artifacts/AncientArtifacts.java) |
| `IPlayerArmorTracker` | interface | — | — | 0 | 8 | [IPlayerArmorTracker.java](org/crypticdev/ancient_artifacts/IPlayerArmorTracker.java) |
| `PlayerFabric` | interface | — | — | 0 | 18 | [PlayerFabric.java](org/crypticdev/ancient_artifacts/PlayerFabric.java) |
| `ArtifactText` | class | — | — | 2 | 99 | [ArtifactText.java](org/crypticdev/ancient_artifacts/artifact/ArtifactText.java) |
| `ArtifactUnlockCondition` | class | — | — | 3 | 32 | [ArtifactUnlockCondition.java](org/crypticdev/ancient_artifacts/artifact/ArtifactUnlockCondition.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `AncientArtifactsNeoForgeClient` | 2 | `org.crypticdev.ancient_artifacts.neoforge` | [AncientArtifactsNeoForgeClient.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsNeoForgeClient.java) |
| `AncientArtifactsDataGen` | 1 | `org.crypticdev.ancient_artifacts.neoforge` | [AncientArtifactsDataGen.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsDataGen.java) |

## Mixin（14 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `DeathHandlerMixin` | `DeathHandler` | [DeathHandlerMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/handler/DeathHandlerMixin.java) |
| `DamageHandlerMixin` | `DamageHandler` | [DamageHandlerMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/handler/DamageHandlerMixin.java) |
| `CommanderSkillMixin` | `CommanderSkill` | [CommanderSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/CommanderSkillMixin.java) |
| `AnvilMenuMixin` | `AnvilMenu` | [AnvilMenuMixin.java](org/crypticdev/ancient_artifacts/mixin/world/inventory/AnvilMenuMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](org/crypticdev/ancient_artifacts/mixin/world/entity/player/EntityMixin.java) |
| `ShadowStrikerSkillMixin` | `ShadowStrikerSkill` | [ShadowStrikerSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/ShadowStrikerSkillMixin.java) |
| `RoyalBeastSkillMixin` | `RoyalBeastSkill` | [RoyalBeastSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/RoyalBeastSkillMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](org/crypticdev/ancient_artifacts/mixin/world/entity/player/PlayerMixin.java) |
| `RoaringLionPunchArtMixin` | `RoaringLionPunchArt` | [RoaringLionPunchArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/RoaringLionPunchArtMixin.java) |
| `MagicBulletArtMixin` | `MagicBulletArt` | [MagicBulletArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/projectile/MagicBulletArtMixin.java) |
| `MaximumMagicBulletArtMixin` | `MaximumMagicBulletArt` | [MaximumMagicBulletArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/projectile/MaximumMagicBulletArtMixin.java) |
| `BlackFlameSkillMixin` | `BlackFlameSkill` | [BlackFlameSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/extra/BlackFlameSkillMixin.java) |
| `ExistenceStorageMixin` | `ExistenceStorage` | [ExistenceStorageMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/storage/ep/ExistenceStorageMixin.java) |
| `PlayerFabricMixin` | `PlayerFabric` | [PlayerFabricMixin.java](org/crypticdev/ancient_artifacts/mixin/fabric/PlayerFabricMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `LootInstanceConfig` | — | [LootInstanceConfig.java](org/crypticdev/ancient_artifacts/config/LootInstanceConfig.java) |
| `DefaultLootInstanceConfigs` | — | [DefaultLootInstanceConfigs.java](org/crypticdev/ancient_artifacts/config/DefaultLootInstanceConfigs.java) |
| `CommandersControlledRageConfig` | — | [CommandersControlledRageConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/CommandersControlledRageConfig.java) |
| `EffectConfigs` | — | [EffectConfigs.java](org/crypticdev/ancient_artifacts/EffectConfigs.java) |
| `SpecialEffectConfig` | — | [SpecialEffectConfig.java](org/crypticdev/ancient_artifacts/config/SpecialEffectConfig.java) |
| `UnlockableEffectConfig` | — | [UnlockableEffectConfig.java](org/crypticdev/ancient_artifacts/config/UnlockableEffectConfig.java) |
| `AncientArtifactsConfig` | — | [AncientArtifactsConfig.java](org/crypticdev/ancient_artifacts/config/AncientArtifactsConfig.java) |
| `OgresWrathConfig` | — | [OgresWrathConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/OgresWrathConfig.java) |
| `KingsAwakeningConfig` | — | [KingsAwakeningConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/KingsAwakeningConfig.java) |
| `RisingInfernoConfig` | — | [RisingInfernoConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/RisingInfernoConfig.java) |
| `BeastAuraConfig` | — | [BeastAuraConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/BeastAuraConfig.java) |
| `CurrentMasteryConfig` | — | [CurrentMasteryConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/CurrentMasteryConfig.java) |

## 全部类（86）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AncientArtifactsNeoForgeClient` | class | — | EventBusSubscriber | 2 | 33 | [AncientArtifactsNeoForgeClient.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsNeoForgeClient.java) |
| `AncientArtifactsDataGen` | class | — | EventBusSubscriber | 1 | 21 | [AncientArtifactsDataGen.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsDataGen.java) |
| `DeathHandlerMixin` | class | — | Mixin | 3 | 107 | [DeathHandlerMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/handler/DeathHandlerMixin.java) |
| `DamageHandlerMixin` | class | — | Mixin | 1 | 42 | [DamageHandlerMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/handler/DamageHandlerMixin.java) |
| `AncientArtifactsNeoForge` | class | — | Mod | 0 | 26 | [AncientArtifactsNeoForge.java](org/crypticdev/ancient_artifacts/neoforge/AncientArtifactsNeoForge.java) |
| `CommanderSkillMixin` | class | — | Mixin | 1 | 33 | [CommanderSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/CommanderSkillMixin.java) |
| `AnvilMenuMixin` | class | ItemCombinerMenu | Mixin | 1 | 80 | [AnvilMenuMixin.java](org/crypticdev/ancient_artifacts/mixin/world/inventory/AnvilMenuMixin.java) |
| `EntityMixin` | class | — | Mixin | 3 | 82 | [EntityMixin.java](org/crypticdev/ancient_artifacts/mixin/world/entity/player/EntityMixin.java) |
| `ShadowStrikerSkillMixin` | class | Skill | Mixin | 7 | 232 | [ShadowStrikerSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/ShadowStrikerSkillMixin.java) |
| `RoyalBeastSkillMixin` | class | Skill | Mixin | 5 | 242 | [RoyalBeastSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/unique/RoyalBeastSkillMixin.java) |
| `PlayerMixin` | class | LivingEntity | Mixin | 7 | 158 | [PlayerMixin.java](org/crypticdev/ancient_artifacts/mixin/world/entity/player/PlayerMixin.java) |
| `LootInstanceConfig` | class | ManasConfig | — | 1 | 33 | [LootInstanceConfig.java](org/crypticdev/ancient_artifacts/config/LootInstanceConfig.java) |
| `DefaultLootInstanceConfigs` | class | — | — | 0 | 24 | [DefaultLootInstanceConfigs.java](org/crypticdev/ancient_artifacts/config/DefaultLootInstanceConfigs.java) |
| `CommandersControlledRageConfig` | class | SpecialEffectConfig | — | 2 | 23 | [CommandersControlledRageConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/CommandersControlledRageConfig.java) |
| `RoaringLionPunchArtMixin` | class | Battlewill | Mixin | 2 | 63 | [RoaringLionPunchArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/RoaringLionPunchArtMixin.java) |
| `MagicBulletArtMixin` | class | — | Mixin | 1 | 31 | [MagicBulletArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/projectile/MagicBulletArtMixin.java) |
| `MaximumMagicBulletArtMixin` | class | — | Mixin | 1 | 34 | [MaximumMagicBulletArtMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/battlewill/projectile/MaximumMagicBulletArtMixin.java) |
| `BlackFlameSkillMixin` | class | — | Mixin | 2 | 38 | [BlackFlameSkillMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/ability/skill/extra/BlackFlameSkillMixin.java) |
| `ExistenceStorageMixin` | class | — | Mixin | 1 | 63 | [ExistenceStorageMixin.java](org/crypticdev/ancient_artifacts/mixin/tensura/storage/ep/ExistenceStorageMixin.java) |
| `PlayerFabricMixin` | class | PlayerFabric | Mixin | 2 | 65 | [PlayerFabricMixin.java](org/crypticdev/ancient_artifacts/mixin/fabric/PlayerFabricMixin.java) |
| `ArtifactEffectsRegistry` | class | — | — | 0 | 8 | [ArtifactEffectsRegistry.java](org/crypticdev/ancient_artifacts/registry/ArtifactEffectsRegistry.java) |
| `ArtifactAttributesRegistry` | class | — | — | 2 | 27 | [ArtifactAttributesRegistry.java](org/crypticdev/ancient_artifacts/registry/ArtifactAttributesRegistry.java) |
| `EffectConfigs` | class | — | — | 1 | 33 | [EffectConfigs.java](org/crypticdev/ancient_artifacts/EffectConfigs.java) |
| `ArtifactEffect` | interface | — | — | 15 | 217 | [ArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/ArtifactEffect.java) |
| `SpecialEffectConfig` | class | ManasConfig | — | 1 | 8 | [SpecialEffectConfig.java](org/crypticdev/ancient_artifacts/config/SpecialEffectConfig.java) |
| `UnlockableEffectConfig` | class | ManasConfig | — | 1 | 28 | [UnlockableEffectConfig.java](org/crypticdev/ancient_artifacts/config/UnlockableEffectConfig.java) |
| `AncientArtifactsConfig` | class | ManasConfig | — | 2 | 46 | [AncientArtifactsConfig.java](org/crypticdev/ancient_artifacts/config/AncientArtifactsConfig.java) |
| `OgresWrathConfig` | class | SpecialEffectConfig | — | 2 | 22 | [OgresWrathConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/OgresWrathConfig.java) |
| `KingsAwakeningConfig` | class | SpecialEffectConfig | — | 2 | 15 | [KingsAwakeningConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/KingsAwakeningConfig.java) |
| `RisingInfernoConfig` | class | SpecialEffectConfig | — | 2 | 23 | [RisingInfernoConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/RisingInfernoConfig.java) |
| `BeastAuraConfig` | class | SpecialEffectConfig | — | 2 | 20 | [BeastAuraConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/BeastAuraConfig.java) |
| `CurrentMasteryConfig` | class | SpecialEffectConfig | — | 2 | 21 | [CurrentMasteryConfig.java](org/crypticdev/ancient_artifacts/config/dynamic_effects/CurrentMasteryConfig.java) |
| `ArtifactCurioItem` | class | Item | — | 5 | 84 | [ArtifactCurioItem.java](org/crypticdev/ancient_artifacts/neoforge/integration/curios/ArtifactCurioItem.java) |
| `ArtifactItems` | class | — | — | 5 | 63 | [ArtifactItems.java](org/crypticdev/ancient_artifacts/registry/ArtifactItems.java) |
| `AncientArtifactsClient` | class | — | — | 1 | 33 | [AncientArtifactsClient.java](org/crypticdev/ancient_artifacts/AncientArtifactsClient.java) |
| `UnlockableAttributeArtifactEffect` | record | ArtifactEffect | — | 6 | 207 | [UnlockableAttributeArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/UnlockableAttributeArtifactEffect.java) |
| `ArtifactItem` | interface | — | — | 0 | 44 | [ArtifactItem.java](org/crypticdev/ancient_artifacts/artifact/ArtifactItem.java) |
| `PlatformItems` | class | — | — | 2 | 26 | [PlatformItems.java](org/crypticdev/ancient_artifacts/platform/PlatformItems.java) |
| `PlatformItemsImpl` | class | — | — | 1 | 16 | [PlatformItemsImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformItemsImpl.java) |
| `ArtifactItemTab` | class | — | — | 1 | 24 | [ArtifactItemTab.java](org/crypticdev/ancient_artifacts/registry/ArtifactItemTab.java) |
| `ArtifactEffects` | class | — | — | 6 | 113 | [ArtifactEffects.java](org/crypticdev/ancient_artifacts/artifact/ArtifactEffects.java) |
| `ArtifactDefinition` | record | — | — | 8 | 82 | [ArtifactDefinition.java](org/crypticdev/ancient_artifacts/artifact/ArtifactDefinition.java) |
| `DynamicAttributeArtifactEffect` | record | ArtifactEffect | — | 6 | 96 | [DynamicAttributeArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/DynamicAttributeArtifactEffect.java) |
| `MultiArtifactEffect` | record | ArtifactEffect | — | 5 | 46 | [MultiArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/MultiArtifactEffect.java) |
| `BootsModel` | class | HierarchicalModel | — | 5 | 79 | [BootsModel.java](org/crypticdev/ancient_artifacts/artifact/models/BootsModel.java) |
| `BraceletsModel` | class | HierarchicalModel | — | 5 | 144 | [BraceletsModel.java](org/crypticdev/ancient_artifacts/artifact/models/BraceletsModel.java) |
| `CombatTracker` | class | — | — | 6 | 39 | [CombatTracker.java](org/crypticdev/ancient_artifacts/attachments/CombatTracker.java) |
| `CombatData` | class | — | — | 4 | 38 | [CombatData.java](org/crypticdev/ancient_artifacts/attachments/CombatData.java) |
| `PlatformEquipment` | class | — | — | 6 | 49 | [PlatformEquipment.java](org/crypticdev/ancient_artifacts/platform/PlatformEquipment.java) |
| `PlatformCombatHelper` | class | — | — | 4 | 33 | [PlatformCombatHelper.java](org/crypticdev/ancient_artifacts/platform/PlatformCombatHelper.java) |
| `PlatformCombatHelperImpl` | class | — | — | 4 | 24 | [PlatformCombatHelperImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformCombatHelperImpl.java) |
| `PlatformEquipmentImpl` | class | — | — | 6 | 83 | [PlatformEquipmentImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformEquipmentImpl.java) |
| `ArtifactDataComponents` | class | — | — | 1 | 31 | [ArtifactDataComponents.java](org/crypticdev/ancient_artifacts/registry/ArtifactDataComponents.java) |
| `Artifacts` | class | — | — | 0 | 282 | [Artifacts.java](org/crypticdev/ancient_artifacts/registry/Artifacts.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_ancient_artifacts_common_a9051de710cb486d9467324da99222e3_fef0e0342f56de02fb4c18de405e14af3d873906f36f73f9d312670874150ca1AncientArtifactscommon100devjar/PlatformMethods.java) |
| `AncientArtifacts` | class | — | — | 1 | 151 | [AncientArtifacts.java](org/crypticdev/ancient_artifacts/AncientArtifacts.java) |
| `IPlayerArmorTracker` | interface | — | — | 0 | 8 | [IPlayerArmorTracker.java](org/crypticdev/ancient_artifacts/IPlayerArmorTracker.java) |
| `PlayerFabric` | interface | — | — | 0 | 18 | [PlayerFabric.java](org/crypticdev/ancient_artifacts/PlayerFabric.java) |
| `ArtifactText` | class | — | — | 2 | 99 | [ArtifactText.java](org/crypticdev/ancient_artifacts/artifact/ArtifactText.java) |
| `ArtifactUnlockCondition` | class | — | — | 3 | 32 | [ArtifactUnlockCondition.java](org/crypticdev/ancient_artifacts/artifact/ArtifactUnlockCondition.java) |
| `DescriptionArtifactEffect` | record | ArtifactEffect | — | 3 | 36 | [DescriptionArtifactEffect.java](org/crypticdev/ancient_artifacts/artifact/DescriptionArtifactEffect.java) |
| `ArtifactSlot` | enum | — | — | 4 | 38 | [ArtifactSlot.java](org/crypticdev/ancient_artifacts/artifact/ArtifactSlot.java) |
| `ArtifactRefinement` | class | — | — | 2 | 74 | [ArtifactRefinement.java](org/crypticdev/ancient_artifacts/artifact/ArtifactRefinement.java) |
| `BeltAnimationsTest` | class | — | — | 0 | 27 | [BeltAnimationsTest.java](org/crypticdev/ancient_artifacts/artifact/animations/BeltAnimationsTest.java) |
| `AmuletModel` | class | HierarchicalModel | — | 4 | 85 | [AmuletModel.java](org/crypticdev/ancient_artifacts/artifact/models/AmuletModel.java) |
| `ChestEmblemModel` | class | HierarchicalModel | — | 4 | 111 | [ChestEmblemModel.java](org/crypticdev/ancient_artifacts/artifact/models/ChestEmblemModel.java) |
| `BeltModel` | class | HierarchicalModel | — | 4 | 60 | [BeltModel.java](org/crypticdev/ancient_artifacts/artifact/models/BeltModel.java) |
| `TasselsModel` | class | HierarchicalModel | — | 4 | 166 | [TasselsModel.java](org/crypticdev/ancient_artifacts/artifact/models/TasselsModel.java) |
| `FinModel` | class | HierarchicalModel | — | 4 | 66 | [FinModel.java](org/crypticdev/ancient_artifacts/artifact/models/FinModel.java) |
| `HornsModel` | class | HierarchicalModel | — | 4 | 71 | [HornsModel.java](org/crypticdev/ancient_artifacts/artifact/models/HornsModel.java) |
| `PendantModel` | class | HierarchicalModel | — | 4 | 76 | [PendantModel.java](org/crypticdev/ancient_artifacts/artifact/models/PendantModel.java) |
| `AncientArtifactsModelProvider` | class | ItemModelProvider | — | 2 | 25 | [AncientArtifactsModelProvider.java](org/crypticdev/ancient_artifacts/neoforge/datagen/AncientArtifactsModelProvider.java) |
| `RefinementComponent` | record | — | — | 1 | 80 | [RefinementComponent.java](org/crypticdev/ancient_artifacts/component/RefinementComponent.java) |
| `UnlockComponent` | record | — | — | 2 | 33 | [UnlockComponent.java](org/crypticdev/ancient_artifacts/component/UnlockComponent.java) |
| `AttackerArmorComponent` | record | — | — | 0 | 19 | [AttackerArmorComponent.java](org/crypticdev/ancient_artifacts/component/AttackerArmorComponent.java) |
| `PlatformAttributes` | class | — | — | 2 | 27 | [PlatformAttributes.java](org/crypticdev/ancient_artifacts/platform/PlatformAttributes.java) |
| `PlatformAttributesImpl` | class | — | — | 2 | 25 | [PlatformAttributesImpl.java](org/crypticdev/ancient_artifacts/platform/neoforge/PlatformAttributesImpl.java) |
| `HornsLayer` | class | — | — | 1 | 67 | [HornsLayer.java](org/crypticdev/ancient_artifacts/client/HornsLayer.java) |
| `TasselsLayer` | class | — | — | 1 | 68 | [TasselsLayer.java](org/crypticdev/ancient_artifacts/client/TasselsLayer.java) |
| `FinLayer` | class | — | — | 1 | 48 | [FinLayer.java](org/crypticdev/ancient_artifacts/client/FinLayer.java) |
| `AmuletLayer` | class | — | — | 1 | 67 | [AmuletLayer.java](org/crypticdev/ancient_artifacts/client/AmuletLayer.java) |
| `PendantLayer` | class | — | — | 1 | 68 | [PendantLayer.java](org/crypticdev/ancient_artifacts/client/PendantLayer.java) |
| `BootsLayer` | class | — | — | 1 | 53 | [BootsLayer.java](org/crypticdev/ancient_artifacts/client/BootsLayer.java) |
| `BraceletsLayer` | class | — | — | 1 | 81 | [BraceletsLayer.java](org/crypticdev/ancient_artifacts/client/BraceletsLayer.java) |
| `ChestEmblemLayer` | class | — | — | 1 | 76 | [ChestEmblemLayer.java](org/crypticdev/ancient_artifacts/client/ChestEmblemLayer.java) |
| `BeltLayer` | class | — | — | 1 | 48 | [BeltLayer.java](org/crypticdev/ancient_artifacts/client/BeltLayer.java) |
