# Tensura NO GAME NO LIFE（`tennogamenolife`）

> jar：`[转生附属]tennogamenolife.jar` ｜ 版本：`1.1.4.3` ｜ 作者：AblazeAQZL
> 规模：**131 个类 / 39 个包 / 15279 行** ｜ 事件处理类 21 ｜ Mixin 6 ｜ 注册/管理类 15

**依赖**：`minecraft`、`manascore`、`tensura`、`jade`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.b4ndithelps.tennogamenolife.race.ExMachina` | 14 | `ExMachinaDecorativeManager`、`ExMachinaDecorativeData`、`HorouRace` |
| `com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate` | 11 | `TengekiProgressiveDestruction`、`DragonRoarStrikeSequence`、`TengekiWitherZone` |
| `com.github.b4ndithelps.tennogamenolife.event` | 10 | `FlugelRaceEventHandler`、`FourthProtectionEvents`、`ExMachinaRaceEventHandler` |
| `com.github.b4ndithelps.tennogamenolife.client.model` | 9 | `XiubiArmorModel`、`AngelWings`、`AngelWingsModel` |
| `com.github.b4ndithelps.tennogamenolife.race.Doragonia` | 7 | `ReginreivuRace`、`DragoniaRace`、`AranreivuRace` |
| `com.github.b4ndithelps.tennogamenolife.race.Flugel` | 7 | `ArutoshuRace`、`AzrilRace`、`JibrilRace` |
| `com.github.b4ndithelps.tennogamenolife.client` | 6 | `ClientEvents`、`ClientModEvents`、`DragonRoarChargeLockClient` |
| `com.github.b4ndithelps.tennogamenolife.client.renderer` | 6 | `DragonRoarCircleRenderer`、`SickleRenderer`、`AngelWingsRenderer` |
| `com.github.b4ndithelps.tennogamenolife.config.race` | 5 | `ExMachinaRaceConfig`、`DragoniaRaceConfig`、`DwarfRaceConfig` |
| `com.github.b4ndithelps.tennogamenolife.item` | 5 | `ExMachinasWeaponItem`、`MagItem`、`SickleItem` |
| `com.github.b4ndithelps.tennogamenolife.race.Dwarf` | 5 | `DwarfRace`、`NiiTiruviruguRace`、`VeiguDorauvuniruRace` |
| `com.github.b4ndithelps.tennogamenolife.client.renderer.layer` | 5 | `PlayerExMachinaArmorLayer`、`SickleBladeGlowLayer`、`PlayerFlugelWingsLayer` |
| `com.github.b4ndithelps.tennogamenolife.mixin` | 4 | `ServerboundMovePlayerPacketAccessor`、`ServerGamePacketListenerFourthProtectionMixin`、`EntityFourthProtectionMovementMixin` |
| `com.github.b4ndithelps.tennogamenolife.config` | 3 | `TenreincarnationConfig`、`TNOConfigInjector`、`TenreincarnationGeneralConfig` |
| `com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic` | 3 | `FourthProtectionManager`、`SpiritCircuitOverloadSkill`、`EternalFourthProtectionSkill` |
| `com.github.b4ndithelps.tennogamenolife.ability.skill.extra` | 3 | `HeavenlyShiftSkill`、`BarrierSkill`、`BlackArrowSkill` |
| `com.github.b4ndithelps.tennogamenolife` | 2 | `TenreincarnationClient`、`Tenreincarnation` |
| `com.github.b4ndithelps.tennogamenolife.mixin.client` | 2 | `SickleGuiItemRendererMixin`、`GodlySmitePlayerModelMixin` |
| `com.github.b4ndithelps.tennogamenolife.command` | 2 | `JorCommand`、`LaserRainDebugCommand` |
| `com.github.b4ndithelps.tennogamenolife.registry.item` | 2 | `Tenreincarnationitems`、`TenreincarnationCreativeTabs` |
| `com.github.b4ndithelps.tennogamenolife.registry.sound` | 2 | `TenreincarnationJukeboxSongs`、`TenreincarnationSounds` |
| `com.github.b4ndithelps.tennogamenolife.client.render` | 1 | `ChargeBarRenderer` |
| `com.github.b4ndithelps.tennogamenolife.data` | 1 | `TennoDataGenerator` |
| `com.github.b4ndithelps.tennogamenolife.entity` | 1 | `DragonRoarCircle` |
| `com.github.b4ndithelps.tennogamenolife.entity.projectile` | 1 | `BlackRodProjectile` |
| `com.github.b4ndithelps.tennogamenolife.util` | 1 | `DamageTestManager` |
| `com.github.b4ndithelps.tennogamenolife.block` | 1 | `ChessBlock` |
| `com.github.b4ndithelps.tennogamenolife.registry.block` | 1 | `TenreincarnationBlocks` |
| `com.github.b4ndithelps.tennogamenolife.race.Flügel` | 1 | `HigherFlugelRace` |
| `com.github.b4ndithelps.tennogamenolife.race.Fantasuma` | 1 | `FantasumaRace` |
| `com.github.b4ndithelps.tennogamenolife.geckolib` | 1 | `PolyMeshBakedModelFactory` |
| `com.github.b4ndithelps.tennogamenolife.ability.skill.unique` | 1 | `MagicBeadsSkill` |
| `com.github.b4ndithelps.tennogamenolife.compat.jade` | 1 | `TennoGameNoLifeJadePlugin` |
| `com.github.b4ndithelps.tennogamenolife.registry` | 1 | `ModEffects` |
| `com.github.b4ndithelps.tennogamenolife.registry.race` | 1 | `Tenreincarnationraces` |
| `com.github.b4ndithelps.tennogamenolife.registry.entity` | 1 | `TenreincarnationEntities` |
| `com.github.b4ndithelps.tennogamenolife.registry.skill` | 1 | `TenreincarnationSkill` |
| `com.github.b4ndithelps.tennogamenolife.registry.effect` | 1 | `ModEffects` |
| `com.github.b4ndithelps.tennogamenolife.effect` | 1 | `WeakenEffect` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FlugelRaceEventHandler` | class | — | EventBusSubscriber | 31 | 433 | [FlugelRaceEventHandler.java](com/github/b4ndithelps/tennogamenolife/event/FlugelRaceEventHandler.java) |
| `FourthProtectionEvents` | class | — | EventBusSubscriber | 10 | 155 | [FourthProtectionEvents.java](com/github/b4ndithelps/tennogamenolife/event/FourthProtectionEvents.java) |
| `ClientEvents` | class | — | EventBusSubscriber | 1 | 29 | [ClientEvents.java](com/github/b4ndithelps/tennogamenolife/client/ClientEvents.java) |
| `ClientModEvents` | class | — | EventBusSubscriber | 2 | 19 | [ClientModEvents.java](com/github/b4ndithelps/tennogamenolife/client/ClientModEvents.java) |
| `TengekiProgressiveDestruction` | class | — | EventBusSubscriber | 34 | 613 | [TengekiProgressiveDestruction.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiProgressiveDestruction.java) |
| `ExMachinaRaceEventHandler` | class | — | EventBusSubscriber | 5 | 87 | [ExMachinaRaceEventHandler.java](com/github/b4ndithelps/tennogamenolife/event/ExMachinaRaceEventHandler.java) |
| `DragonRoarChargeLockEvents` | class | — | EventBusSubscriber | 2 | 74 | [DragonRoarChargeLockEvents.java](com/github/b4ndithelps/tennogamenolife/event/DragonRoarChargeLockEvents.java) |
| `SoulboundWeaponDeathHandler` | class | — | EventBusSubscriber | 13 | 142 | [SoulboundWeaponDeathHandler.java](com/github/b4ndithelps/tennogamenolife/event/SoulboundWeaponDeathHandler.java) |
| `DragonRoarRaceRestrictionHandler` | class | — | EventBusSubscriber | 4 | 59 | [DragonRoarRaceRestrictionHandler.java](com/github/b4ndithelps/tennogamenolife/event/DragonRoarRaceRestrictionHandler.java) |
| `GodlySmiteRaceRestrictionHandler` | class | — | EventBusSubscriber | 3 | 53 | [GodlySmiteRaceRestrictionHandler.java](com/github/b4ndithelps/tennogamenolife/event/GodlySmiteRaceRestrictionHandler.java) |
| `FinalEvolutionDiscRewardHandler` | class | — | EventBusSubscriber | 3 | 80 | [FinalEvolutionDiscRewardHandler.java](com/github/b4ndithelps/tennogamenolife/event/FinalEvolutionDiscRewardHandler.java) |
| `TenreincarnationConfig` | class | — | EventBusSubscriber | 7 | 142 | [TenreincarnationConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationConfig.java) |
| `DragonRoarChargeLockClient` | class | — | EventBusSubscriber | 6 | 105 | [DragonRoarChargeLockClient.java](com/github/b4ndithelps/tennogamenolife/client/DragonRoarChargeLockClient.java) |
| `TenreincarnationClient` | class | — | Mod | 1 | 30 | [TenreincarnationClient.java](com/github/b4ndithelps/tennogamenolife/TenreincarnationClient.java) |
| `TenreincarnationClientForge` | class | — | EventBusSubscriber | 1 | 52 | [TenreincarnationClientForge.java](com/github/b4ndithelps/tennogamenolife/client/TenreincarnationClientForge.java) |
| `TenreincarnationClientSetup` | class | — | EventBusSubscriber | 3 | 41 | [TenreincarnationClientSetup.java](com/github/b4ndithelps/tennogamenolife/client/TenreincarnationClientSetup.java) |
| `ChargeBarRenderer` | class | — | EventBusSubscriber | 4 | 63 | [ChargeBarRenderer.java](com/github/b4ndithelps/tennogamenolife/client/render/ChargeBarRenderer.java) |
| `Tenreincarnation` | class | — | Mod | 6 | 95 | [Tenreincarnation.java](com/github/b4ndithelps/tennogamenolife/Tenreincarnation.java) |
| `DragonRoarStrikeSequence` | class | — | EventBusSubscriber | 8 | 127 | [DragonRoarStrikeSequence.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/DragonRoarStrikeSequence.java) |
| `TengekiWitherZone` | class | — | EventBusSubscriber | 6 | 108 | [TengekiWitherZone.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiWitherZone.java) |
| `TennoDataGenerator` | class | — | EventBusSubscriber | 1 | 18 | [TennoDataGenerator.java](com/github/b4ndithelps/tennogamenolife/data/TennoDataGenerator.java) |
| `ExMachinaRaceConfig` | class | Default | — | 132 | 699 | [ExMachinaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/ExMachinaRaceConfig.java) |
| `DragonRoarCircleRenderer` | class | GeoEntityRenderer | — | 51 | 1114 | [DragonRoarCircleRenderer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/DragonRoarCircleRenderer.java) |
| `DragoniaRaceConfig` | class | Default | — | 84 | 447 | [DragoniaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/DragoniaRaceConfig.java) |
| `DwarfRaceConfig` | class | Default | — | 60 | 321 | [DwarfRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/DwarfRaceConfig.java) |
| `FlugelRaceConfig` | class | Default | — | 72 | 384 | [FlugelRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/FlugelRaceConfig.java) |
| `SickleGuiItemRendererMixin` | class | — | Mixin | 0 | 43 | [SickleGuiItemRendererMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/client/SickleGuiItemRendererMixin.java) |
| `ServerboundMovePlayerPacketAccessor` | interface | — | Mixin | 0 | 22 | [ServerboundMovePlayerPacketAccessor.java](com/github/b4ndithelps/tennogamenolife/mixin/ServerboundMovePlayerPacketAccessor.java) |
| `ServerGamePacketListenerFourthProtectionMixin` | class | — | Mixin | 0 | 41 | [ServerGamePacketListenerFourthProtectionMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/ServerGamePacketListenerFourthProtectionMixin.java) |
| `FourthProtectionManager` | class | — | — | 19 | 327 | [FourthProtectionManager.java](com/github/b4ndithelps/tennogamenolife/ability/skill/intrinsic/FourthProtectionManager.java) |
| `EntityFourthProtectionMovementMixin` | class | — | Mixin | 0 | 21 | [EntityFourthProtectionMovementMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/EntityFourthProtectionMovementMixin.java) |
| `ReisenUltimateSkill` | class | Skill | — | 28 | 626 | [ReisenUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/ReisenUltimateSkill.java) |
| `DamageTestEvents` | class | — | — | 0 | 5 | [DamageTestEvents.java](com/github/b4ndithelps/tennogamenolife/event/DamageTestEvents.java) |
| `ExMachinaDecorativeManager` | class | — | — | 11 | 125 | [ExMachinaDecorativeManager.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/ExMachinaDecorativeManager.java) |
| `DragonRoarCircle` | class | Entity | — | 40 | 335 | [DragonRoarCircle.java](com/github/b4ndithelps/tennogamenolife/entity/DragonRoarCircle.java) |
| `BlackRodProjectile` | class | Projectile | — | 26 | 402 | [BlackRodProjectile.java](com/github/b4ndithelps/tennogamenolife/entity/projectile/BlackRodProjectile.java) |
| `HumanRaceMixin` | class | — | Mixin | 0 | 9 | [HumanRaceMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/HumanRaceMixin.java) |
| `GodlySmitePlayerModelMixin` | class | — | Mixin | 1 | 40 | [GodlySmitePlayerModelMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/client/GodlySmitePlayerModelMixin.java) |
| `SpiritCircuitOverloadSkill` | class | Skill | — | 26 | 314 | [SpiritCircuitOverloadSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/intrinsic/SpiritCircuitOverloadSkill.java) |
| `FantasumaRaceConfig` | class | Default | — | 12 | 69 | [FantasumaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/FantasumaRaceConfig.java) |
| `ExMachinasWeaponItem` | class | Item | — | 16 | 305 | [ExMachinasWeaponItem.java](com/github/b4ndithelps/tennogamenolife/item/ExMachinasWeaponItem.java) |
| `HeavenlyShiftSkill` | class | Skill | — | 21 | 327 | [HeavenlyShiftSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/extra/HeavenlyShiftSkill.java) |
| `SkillAvailabilityHandler` | class | — | — | 2 | 50 | [SkillAvailabilityHandler.java](com/github/b4ndithelps/tennogamenolife/event/SkillAvailabilityHandler.java) |
| `DamageTestManager` | class | — | — | 2 | 18 | [DamageTestManager.java](com/github/b4ndithelps/tennogamenolife/util/DamageTestManager.java) |
| `ExMachinaDecorativeData` | class | — | — | 17 | 98 | [ExMachinaDecorativeData.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/ExMachinaDecorativeData.java) |
| `TengekiUltimateSkill` | class | Skill | — | 17 | 319 | [TengekiUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiUltimateSkill.java) |
| `GodlySmiteUltimateSkill` | class | Skill | — | 18 | 275 | [GodlySmiteUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/GodlySmiteUltimateSkill.java) |
| `TNOConfigInjector` | class | — | — | 1 | 66 | [TNOConfigInjector.java](com/github/b4ndithelps/tennogamenolife/config/TNOConfigInjector.java) |
| `TenreincarnationGeneralConfig` | class | ManasConfig | — | 1 | 19 | [TenreincarnationGeneralConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationGeneralConfig.java) |
| `XiubiArmorModel` | class | EntityModel | — | 11 | 490 | [XiubiArmorModel.java](com/github/b4ndithelps/tennogamenolife/client/model/XiubiArmorModel.java) |
| `ChessBlock` | class | HorizontalDirectionalBlock | — | 6 | 58 | [ChessBlock.java](com/github/b4ndithelps/tennogamenolife/block/ChessBlock.java) |
| `FarCryUltimateSkill` | class | Skill | — | 14 | 251 | [FarCryUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/FarCryUltimateSkill.java) |
| `MagItem` | class | Item | — | 5 | 87 | [MagItem.java](com/github/b4ndithelps/tennogamenolife/item/MagItem.java) |
| `SickleItem` | class | TwoHandedSwordItem | — | 7 | 64 | [SickleItem.java](com/github/b4ndithelps/tennogamenolife/item/SickleItem.java) |
| `JorCommand` | class | — | — | 2 | 37 | [JorCommand.java](com/github/b4ndithelps/tennogamenolife/command/JorCommand.java) |
| `LaserRainDebugCommand` | class | — | — | 4 | 101 | [LaserRainDebugCommand.java](com/github/b4ndithelps/tennogamenolife/command/LaserRainDebugCommand.java) |
| `BarrierSkill` | class | Skill | — | 13 | 147 | [BarrierSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/extra/BarrierSkill.java) |
| `MechanicalCoreItem` | class | Item | — | 2 | 41 | [MechanicalCoreItem.java](com/github/b4ndithelps/tennogamenolife/item/MechanicalCoreItem.java) |
| `TenreincarnationBlocks` | class | — | — | 3 | 35 | [TenreincarnationBlocks.java](com/github/b4ndithelps/tennogamenolife/registry/block/TenreincarnationBlocks.java) |
| `Tenreincarnationitems` | class | — | — | 1 | 34 | [Tenreincarnationitems.java](com/github/b4ndithelps/tennogamenolife/registry/item/Tenreincarnationitems.java) |

## 事件处理（`@SubscribeEvent`，21 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `FourthProtectionEvents` | 7 | `com.github.b4ndithelps.tennogamenolife.event` | [FourthProtectionEvents.java](com/github/b4ndithelps/tennogamenolife/event/FourthProtectionEvents.java) |
| `SoulboundWeaponDeathHandler` | 4 | `com.github.b4ndithelps.tennogamenolife.event` | [SoulboundWeaponDeathHandler.java](com/github/b4ndithelps/tennogamenolife/event/SoulboundWeaponDeathHandler.java) |
| `DragonRoarChargeLockClient` | 3 | `com.github.b4ndithelps.tennogamenolife.client` | [DragonRoarChargeLockClient.java](com/github/b4ndithelps/tennogamenolife/client/DragonRoarChargeLockClient.java) |
| `TenreincarnationClientSetup` | 3 | `com.github.b4ndithelps.tennogamenolife.client` | [TenreincarnationClientSetup.java](com/github/b4ndithelps/tennogamenolife/client/TenreincarnationClientSetup.java) |
| `Tenreincarnation` | 3 | `com.github.b4ndithelps.tennogamenolife` | [Tenreincarnation.java](com/github/b4ndithelps/tennogamenolife/Tenreincarnation.java) |
| `FlugelRaceEventHandler` | 2 | `com.github.b4ndithelps.tennogamenolife.event` | [FlugelRaceEventHandler.java](com/github/b4ndithelps/tennogamenolife/event/FlugelRaceEventHandler.java) |
| `ClientModEvents` | 2 | `com.github.b4ndithelps.tennogamenolife.client` | [ClientModEvents.java](com/github/b4ndithelps/tennogamenolife/client/ClientModEvents.java) |
| `TengekiProgressiveDestruction` | 2 | `com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate` | [TengekiProgressiveDestruction.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiProgressiveDestruction.java) |
| `ExMachinaRaceEventHandler` | 2 | `com.github.b4ndithelps.tennogamenolife.event` | [ExMachinaRaceEventHandler.java](com/github/b4ndithelps/tennogamenolife/event/ExMachinaRaceEventHandler.java) |
| `DragonRoarRaceRestrictionHandler` | 2 | `com.github.b4ndithelps.tennogamenolife.event` | [DragonRoarRaceRestrictionHandler.java](com/github/b4ndithelps/tennogamenolife/event/DragonRoarRaceRestrictionHandler.java) |
| `GodlySmiteRaceRestrictionHandler` | 2 | `com.github.b4ndithelps.tennogamenolife.event` | [GodlySmiteRaceRestrictionHandler.java](com/github/b4ndithelps/tennogamenolife/event/GodlySmiteRaceRestrictionHandler.java) |
| `DragonRoarStrikeSequence` | 2 | `com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate` | [DragonRoarStrikeSequence.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/DragonRoarStrikeSequence.java) |
| `TengekiWitherZone` | 2 | `com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate` | [TengekiWitherZone.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiWitherZone.java) |
| `ClientEvents` | 1 | `com.github.b4ndithelps.tennogamenolife.client` | [ClientEvents.java](com/github/b4ndithelps/tennogamenolife/client/ClientEvents.java) |
| `DragonRoarChargeLockEvents` | 1 | `com.github.b4ndithelps.tennogamenolife.event` | [DragonRoarChargeLockEvents.java](com/github/b4ndithelps/tennogamenolife/event/DragonRoarChargeLockEvents.java) |
| `FinalEvolutionDiscRewardHandler` | 1 | `com.github.b4ndithelps.tennogamenolife.event` | [FinalEvolutionDiscRewardHandler.java](com/github/b4ndithelps/tennogamenolife/event/FinalEvolutionDiscRewardHandler.java) |
| `TenreincarnationConfig` | 1 | `com.github.b4ndithelps.tennogamenolife.config` | [TenreincarnationConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationConfig.java) |
| `TenreincarnationClient` | 1 | `com.github.b4ndithelps.tennogamenolife` | [TenreincarnationClient.java](com/github/b4ndithelps/tennogamenolife/TenreincarnationClient.java) |
| `TenreincarnationClientForge` | 1 | `com.github.b4ndithelps.tennogamenolife.client` | [TenreincarnationClientForge.java](com/github/b4ndithelps/tennogamenolife/client/TenreincarnationClientForge.java) |
| `ChargeBarRenderer` | 1 | `com.github.b4ndithelps.tennogamenolife.client.render` | [ChargeBarRenderer.java](com/github/b4ndithelps/tennogamenolife/client/render/ChargeBarRenderer.java) |
| `TennoDataGenerator` | 1 | `com.github.b4ndithelps.tennogamenolife.data` | [TennoDataGenerator.java](com/github/b4ndithelps/tennogamenolife/data/TennoDataGenerator.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `SickleGuiItemRendererMixin` | `SickleGuiItemRenderer` | [SickleGuiItemRendererMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/client/SickleGuiItemRendererMixin.java) |
| `ServerboundMovePlayerPacketAccessor` | `ServerboundMovePlayerPacketAccessor` | [ServerboundMovePlayerPacketAccessor.java](com/github/b4ndithelps/tennogamenolife/mixin/ServerboundMovePlayerPacketAccessor.java) |
| `ServerGamePacketListenerFourthProtectionMixin` | `ServerGamePacketListenerFourthProtection` | [ServerGamePacketListenerFourthProtectionMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/ServerGamePacketListenerFourthProtectionMixin.java) |
| `EntityFourthProtectionMovementMixin` | `EntityFourthProtectionMovement` | [EntityFourthProtectionMovementMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/EntityFourthProtectionMovementMixin.java) |
| `HumanRaceMixin` | `HumanRace` | [HumanRaceMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/HumanRaceMixin.java) |
| `GodlySmitePlayerModelMixin` | `GodlySmitePlayerModel` | [GodlySmitePlayerModelMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/client/GodlySmitePlayerModelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TenreincarnationConfig` | EventBusSubscriber | [TenreincarnationConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationConfig.java) |
| `ExMachinaRaceConfig` | — | [ExMachinaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/ExMachinaRaceConfig.java) |
| `DragoniaRaceConfig` | — | [DragoniaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/DragoniaRaceConfig.java) |
| `DwarfRaceConfig` | — | [DwarfRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/DwarfRaceConfig.java) |
| `FlugelRaceConfig` | — | [FlugelRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/FlugelRaceConfig.java) |
| `FantasumaRaceConfig` | — | [FantasumaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/FantasumaRaceConfig.java) |
| `TNOConfigInjector` | — | [TNOConfigInjector.java](com/github/b4ndithelps/tennogamenolife/config/TNOConfigInjector.java) |
| `TenreincarnationGeneralConfig` | — | [TenreincarnationGeneralConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationGeneralConfig.java) |

## 全部类（131）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FlugelRaceEventHandler` | class | — | EventBusSubscriber | 31 | 433 | [FlugelRaceEventHandler.java](com/github/b4ndithelps/tennogamenolife/event/FlugelRaceEventHandler.java) |
| `FourthProtectionEvents` | class | — | EventBusSubscriber | 10 | 155 | [FourthProtectionEvents.java](com/github/b4ndithelps/tennogamenolife/event/FourthProtectionEvents.java) |
| `ClientEvents` | class | — | EventBusSubscriber | 1 | 29 | [ClientEvents.java](com/github/b4ndithelps/tennogamenolife/client/ClientEvents.java) |
| `ClientModEvents` | class | — | EventBusSubscriber | 2 | 19 | [ClientModEvents.java](com/github/b4ndithelps/tennogamenolife/client/ClientModEvents.java) |
| `TengekiProgressiveDestruction` | class | — | EventBusSubscriber | 34 | 613 | [TengekiProgressiveDestruction.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiProgressiveDestruction.java) |
| `ExMachinaRaceEventHandler` | class | — | EventBusSubscriber | 5 | 87 | [ExMachinaRaceEventHandler.java](com/github/b4ndithelps/tennogamenolife/event/ExMachinaRaceEventHandler.java) |
| `DragonRoarChargeLockEvents` | class | — | EventBusSubscriber | 2 | 74 | [DragonRoarChargeLockEvents.java](com/github/b4ndithelps/tennogamenolife/event/DragonRoarChargeLockEvents.java) |
| `SoulboundWeaponDeathHandler` | class | — | EventBusSubscriber | 13 | 142 | [SoulboundWeaponDeathHandler.java](com/github/b4ndithelps/tennogamenolife/event/SoulboundWeaponDeathHandler.java) |
| `DragonRoarRaceRestrictionHandler` | class | — | EventBusSubscriber | 4 | 59 | [DragonRoarRaceRestrictionHandler.java](com/github/b4ndithelps/tennogamenolife/event/DragonRoarRaceRestrictionHandler.java) |
| `GodlySmiteRaceRestrictionHandler` | class | — | EventBusSubscriber | 3 | 53 | [GodlySmiteRaceRestrictionHandler.java](com/github/b4ndithelps/tennogamenolife/event/GodlySmiteRaceRestrictionHandler.java) |
| `FinalEvolutionDiscRewardHandler` | class | — | EventBusSubscriber | 3 | 80 | [FinalEvolutionDiscRewardHandler.java](com/github/b4ndithelps/tennogamenolife/event/FinalEvolutionDiscRewardHandler.java) |
| `TenreincarnationConfig` | class | — | EventBusSubscriber | 7 | 142 | [TenreincarnationConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationConfig.java) |
| `DragonRoarChargeLockClient` | class | — | EventBusSubscriber | 6 | 105 | [DragonRoarChargeLockClient.java](com/github/b4ndithelps/tennogamenolife/client/DragonRoarChargeLockClient.java) |
| `TenreincarnationClient` | class | — | Mod | 1 | 30 | [TenreincarnationClient.java](com/github/b4ndithelps/tennogamenolife/TenreincarnationClient.java) |
| `TenreincarnationClientForge` | class | — | EventBusSubscriber | 1 | 52 | [TenreincarnationClientForge.java](com/github/b4ndithelps/tennogamenolife/client/TenreincarnationClientForge.java) |
| `TenreincarnationClientSetup` | class | — | EventBusSubscriber | 3 | 41 | [TenreincarnationClientSetup.java](com/github/b4ndithelps/tennogamenolife/client/TenreincarnationClientSetup.java) |
| `ChargeBarRenderer` | class | — | EventBusSubscriber | 4 | 63 | [ChargeBarRenderer.java](com/github/b4ndithelps/tennogamenolife/client/render/ChargeBarRenderer.java) |
| `Tenreincarnation` | class | — | Mod | 6 | 95 | [Tenreincarnation.java](com/github/b4ndithelps/tennogamenolife/Tenreincarnation.java) |
| `DragonRoarStrikeSequence` | class | — | EventBusSubscriber | 8 | 127 | [DragonRoarStrikeSequence.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/DragonRoarStrikeSequence.java) |
| `TengekiWitherZone` | class | — | EventBusSubscriber | 6 | 108 | [TengekiWitherZone.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiWitherZone.java) |
| `TennoDataGenerator` | class | — | EventBusSubscriber | 1 | 18 | [TennoDataGenerator.java](com/github/b4ndithelps/tennogamenolife/data/TennoDataGenerator.java) |
| `ExMachinaRaceConfig` | class | Default | — | 132 | 699 | [ExMachinaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/ExMachinaRaceConfig.java) |
| `DragonRoarCircleRenderer` | class | GeoEntityRenderer | — | 51 | 1114 | [DragonRoarCircleRenderer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/DragonRoarCircleRenderer.java) |
| `DragoniaRaceConfig` | class | Default | — | 84 | 447 | [DragoniaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/DragoniaRaceConfig.java) |
| `DwarfRaceConfig` | class | Default | — | 60 | 321 | [DwarfRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/DwarfRaceConfig.java) |
| `FlugelRaceConfig` | class | Default | — | 72 | 384 | [FlugelRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/FlugelRaceConfig.java) |
| `SickleGuiItemRendererMixin` | class | — | Mixin | 0 | 43 | [SickleGuiItemRendererMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/client/SickleGuiItemRendererMixin.java) |
| `ServerboundMovePlayerPacketAccessor` | interface | — | Mixin | 0 | 22 | [ServerboundMovePlayerPacketAccessor.java](com/github/b4ndithelps/tennogamenolife/mixin/ServerboundMovePlayerPacketAccessor.java) |
| `ServerGamePacketListenerFourthProtectionMixin` | class | — | Mixin | 0 | 41 | [ServerGamePacketListenerFourthProtectionMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/ServerGamePacketListenerFourthProtectionMixin.java) |
| `FourthProtectionManager` | class | — | — | 19 | 327 | [FourthProtectionManager.java](com/github/b4ndithelps/tennogamenolife/ability/skill/intrinsic/FourthProtectionManager.java) |
| `EntityFourthProtectionMovementMixin` | class | — | Mixin | 0 | 21 | [EntityFourthProtectionMovementMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/EntityFourthProtectionMovementMixin.java) |
| `ReisenUltimateSkill` | class | Skill | — | 28 | 626 | [ReisenUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/ReisenUltimateSkill.java) |
| `DamageTestEvents` | class | — | — | 0 | 5 | [DamageTestEvents.java](com/github/b4ndithelps/tennogamenolife/event/DamageTestEvents.java) |
| `ExMachinaDecorativeManager` | class | — | — | 11 | 125 | [ExMachinaDecorativeManager.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/ExMachinaDecorativeManager.java) |
| `DragonRoarCircle` | class | Entity | — | 40 | 335 | [DragonRoarCircle.java](com/github/b4ndithelps/tennogamenolife/entity/DragonRoarCircle.java) |
| `BlackRodProjectile` | class | Projectile | — | 26 | 402 | [BlackRodProjectile.java](com/github/b4ndithelps/tennogamenolife/entity/projectile/BlackRodProjectile.java) |
| `HumanRaceMixin` | class | — | Mixin | 0 | 9 | [HumanRaceMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/HumanRaceMixin.java) |
| `GodlySmitePlayerModelMixin` | class | — | Mixin | 1 | 40 | [GodlySmitePlayerModelMixin.java](com/github/b4ndithelps/tennogamenolife/mixin/client/GodlySmitePlayerModelMixin.java) |
| `SpiritCircuitOverloadSkill` | class | Skill | — | 26 | 314 | [SpiritCircuitOverloadSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/intrinsic/SpiritCircuitOverloadSkill.java) |
| `FantasumaRaceConfig` | class | Default | — | 12 | 69 | [FantasumaRaceConfig.java](com/github/b4ndithelps/tennogamenolife/config/race/FantasumaRaceConfig.java) |
| `ExMachinasWeaponItem` | class | Item | — | 16 | 305 | [ExMachinasWeaponItem.java](com/github/b4ndithelps/tennogamenolife/item/ExMachinasWeaponItem.java) |
| `HeavenlyShiftSkill` | class | Skill | — | 21 | 327 | [HeavenlyShiftSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/extra/HeavenlyShiftSkill.java) |
| `SkillAvailabilityHandler` | class | — | — | 2 | 50 | [SkillAvailabilityHandler.java](com/github/b4ndithelps/tennogamenolife/event/SkillAvailabilityHandler.java) |
| `DamageTestManager` | class | — | — | 2 | 18 | [DamageTestManager.java](com/github/b4ndithelps/tennogamenolife/util/DamageTestManager.java) |
| `ExMachinaDecorativeData` | class | — | — | 17 | 98 | [ExMachinaDecorativeData.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/ExMachinaDecorativeData.java) |
| `TengekiUltimateSkill` | class | Skill | — | 17 | 319 | [TengekiUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/TengekiUltimateSkill.java) |
| `GodlySmiteUltimateSkill` | class | Skill | — | 18 | 275 | [GodlySmiteUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/GodlySmiteUltimateSkill.java) |
| `TNOConfigInjector` | class | — | — | 1 | 66 | [TNOConfigInjector.java](com/github/b4ndithelps/tennogamenolife/config/TNOConfigInjector.java) |
| `TenreincarnationGeneralConfig` | class | ManasConfig | — | 1 | 19 | [TenreincarnationGeneralConfig.java](com/github/b4ndithelps/tennogamenolife/config/TenreincarnationGeneralConfig.java) |
| `XiubiArmorModel` | class | EntityModel | — | 11 | 490 | [XiubiArmorModel.java](com/github/b4ndithelps/tennogamenolife/client/model/XiubiArmorModel.java) |
| `ChessBlock` | class | HorizontalDirectionalBlock | — | 6 | 58 | [ChessBlock.java](com/github/b4ndithelps/tennogamenolife/block/ChessBlock.java) |
| `FarCryUltimateSkill` | class | Skill | — | 14 | 251 | [FarCryUltimateSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/FarCryUltimateSkill.java) |
| `MagItem` | class | Item | — | 5 | 87 | [MagItem.java](com/github/b4ndithelps/tennogamenolife/item/MagItem.java) |
| `SickleItem` | class | TwoHandedSwordItem | — | 7 | 64 | [SickleItem.java](com/github/b4ndithelps/tennogamenolife/item/SickleItem.java) |
| `JorCommand` | class | — | — | 2 | 37 | [JorCommand.java](com/github/b4ndithelps/tennogamenolife/command/JorCommand.java) |
| `LaserRainDebugCommand` | class | — | — | 4 | 101 | [LaserRainDebugCommand.java](com/github/b4ndithelps/tennogamenolife/command/LaserRainDebugCommand.java) |
| `BarrierSkill` | class | Skill | — | 13 | 147 | [BarrierSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/extra/BarrierSkill.java) |
| `MechanicalCoreItem` | class | Item | — | 2 | 41 | [MechanicalCoreItem.java](com/github/b4ndithelps/tennogamenolife/item/MechanicalCoreItem.java) |
| `TenreincarnationBlocks` | class | — | — | 3 | 35 | [TenreincarnationBlocks.java](com/github/b4ndithelps/tennogamenolife/registry/block/TenreincarnationBlocks.java) |
| `Tenreincarnationitems` | class | — | — | 1 | 34 | [Tenreincarnationitems.java](com/github/b4ndithelps/tennogamenolife/registry/item/Tenreincarnationitems.java) |
| `SickleRenderer` | class | GeoItemRenderer | — | 1 | 39 | [SickleRenderer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/SickleRenderer.java) |
| `AngelWingsRenderer` | class | GeoObjectRenderer | — | 3 | 42 | [AngelWingsRenderer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/AngelWingsRenderer.java) |
| `BlackRodRenderer` | class | GeoEntityRenderer | — | 3 | 35 | [BlackRodRenderer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/BlackRodRenderer.java) |
| `AngelHaloRenderer` | class | GeoObjectRenderer | — | 3 | 42 | [AngelHaloRenderer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/AngelHaloRenderer.java) |
| `HorouRace` | class | DefaultRace | — | 8 | 111 | [HorouRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/HorouRace.java) |
| `KampfRace` | class | DefaultRace | — | 8 | 99 | [KampfRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/KampfRace.java) |
| `ZeichenRace` | class | DefaultRace | — | 8 | 99 | [ZeichenRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/ZeichenRace.java) |
| `MachineRace` | class | DefaultRace | — | 8 | 96 | [MachineRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/MachineRace.java) |
| `PuraiyaRace` | class | HubieDoraRace | — | 5 | 73 | [PuraiyaRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/PuraiyaRace.java) |
| `EmirEinsRace` | class | DefaultRace | — | 8 | 103 | [EmirEinsRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/EmirEinsRace.java) |
| `HubieDoraRace` | class | DefaultRace | — | 8 | 107 | [HubieDoraRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/HubieDoraRace.java) |
| `EinzeigRace` | class | DefaultRace | — | 8 | 103 | [EinzeigRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/EinzeigRace.java) |
| `SeherRace` | class | DefaultRace | — | 8 | 99 | [SeherRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/SeherRace.java) |
| `BefehlerRace` | class | DefaultRace | — | 8 | 102 | [BefehlerRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/BefehlerRace.java) |
| `PrueferRace` | class | DefaultRace | — | 8 | 106 | [PrueferRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/PrueferRace.java) |
| `ReginreivuRace` | class | DefaultRace | — | 7 | 60 | [ReginreivuRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/ReginreivuRace.java) |
| `DragoniaRace` | class | DefaultRace | — | 6 | 59 | [DragoniaRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/DragoniaRace.java) |
| `AranreivuRace` | class | DefaultRace | — | 7 | 60 | [AranreivuRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/AranreivuRace.java) |
| `LesserDragoniaRace` | class | DefaultRace | — | 7 | 63 | [LesserDragoniaRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/LesserDragoniaRace.java) |
| `HigherDragoniaRace` | class | DefaultRace | — | 7 | 65 | [HigherDragoniaRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/HigherDragoniaRace.java) |
| `MiddleDragoniaRace` | class | DefaultRace | — | 7 | 61 | [MiddleDragoniaRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/MiddleDragoniaRace.java) |
| `HartyleifRace` | class | DefaultRace | — | 7 | 60 | [HartyleifRace.java](com/github/b4ndithelps/tennogamenolife/race/Doragonia/HartyleifRace.java) |
| `HigherFlugelRace` | class | TensuraRace | — | 7 | 66 | [HigherFlugelRace.java](com/github/b4ndithelps/tennogamenolife/race/Flügel/HigherFlugelRace.java) |
| `FantasumaRace` | class | DefaultRace | — | 7 | 60 | [FantasumaRace.java](com/github/b4ndithelps/tennogamenolife/race/Fantasuma/FantasumaRace.java) |
| `DwarfRace` | class | DefaultRace | — | 7 | 65 | [DwarfRace.java](com/github/b4ndithelps/tennogamenolife/race/Dwarf/DwarfRace.java) |
| `NiiTiruviruguRace` | class | DefaultRace | — | 7 | 68 | [NiiTiruviruguRace.java](com/github/b4ndithelps/tennogamenolife/race/Dwarf/NiiTiruviruguRace.java) |
| `VeiguDorauvuniruRace` | class | DefaultRace | — | 7 | 66 | [VeiguDorauvuniruRace.java](com/github/b4ndithelps/tennogamenolife/race/Dwarf/VeiguDorauvuniruRace.java) |
| `RoniDorauvuniruRace` | class | DefaultRace | — | 7 | 67 | [RoniDorauvuniruRace.java](com/github/b4ndithelps/tennogamenolife/race/Dwarf/RoniDorauvuniruRace.java) |
| `OcainRace` | class | DefaultRace | — | 7 | 66 | [OcainRace.java](com/github/b4ndithelps/tennogamenolife/race/Dwarf/OcainRace.java) |
| `ArutoshuRace` | class | DefaultRace | — | 8 | 106 | [ArutoshuRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/ArutoshuRace.java) |
| `AzrilRace` | class | DefaultRace | — | 8 | 103 | [AzrilRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/AzrilRace.java) |
| `JibrilRace` | class | DefaultRace | — | 8 | 103 | [JibrilRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/JibrilRace.java) |
| `FlugelRace` | class | DefaultRace | — | 8 | 98 | [FlugelRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/FlugelRace.java) |
| `HigherFlugelRace` | class | DefaultRace | — | 8 | 100 | [HigherFlugelRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/HigherFlugelRace.java) |
| `RaphaelRace` | class | DefaultRace | — | 8 | 102 | [RaphaelRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/RaphaelRace.java) |
| `PolyMeshBakedModelFactory` | class | BakedModelFactory | — | 5 | 129 | [PolyMeshBakedModelFactory.java](com/github/b4ndithelps/tennogamenolife/geckolib/PolyMeshBakedModelFactory.java) |
| `EternalFourthProtectionSkill` | class | Skill | — | 7 | 86 | [EternalFourthProtectionSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/intrinsic/EternalFourthProtectionSkill.java) |
| `MagicBeadsSkill` | class | Skill | — | 5 | 51 | [MagicBeadsSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/unique/MagicBeadsSkill.java) |
| `BlackArrowSkill` | class | Skill | — | 6 | 93 | [BlackArrowSkill.java](com/github/b4ndithelps/tennogamenolife/ability/skill/extra/BlackArrowSkill.java) |
| `GodlySmiteVfx` | class | — | — | 7 | 106 | [GodlySmiteVfx.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/GodlySmiteVfx.java) |
| `DragonRoarChargeLock` | class | — | — | 5 | 52 | [DragonRoarChargeLock.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/DragonRoarChargeLock.java) |
| `GodlySmiteAnimationState` | class | — | — | 5 | 54 | [GodlySmiteAnimationState.java](com/github/b4ndithelps/tennogamenolife/client/GodlySmiteAnimationState.java) |
| `PlayerExMachinaArmorLayer` | class | RenderLayer | — | 5 | 101 | [PlayerExMachinaArmorLayer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/layer/PlayerExMachinaArmorLayer.java) |
| `AngelWings` | class | GeoAnimatable | — | 6 | 49 | [AngelWings.java](com/github/b4ndithelps/tennogamenolife/client/model/AngelWings.java) |
| `IExMachinaDecorativeRace` | interface | — | — | 0 | 63 | [IExMachinaDecorativeRace.java](com/github/b4ndithelps/tennogamenolife/race/ExMachina/IExMachinaDecorativeRace.java) |
| `IFlugelRace` | interface | — | — | 0 | 5 | [IFlugelRace.java](com/github/b4ndithelps/tennogamenolife/race/Flugel/IFlugelRace.java) |
| `SkyStrikeVfx` | class | — | — | 1 | 27 | [SkyStrikeVfx.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/SkyStrikeVfx.java) |
| `DestructiveSkillSafety` | class | — | — | 4 | 41 | [DestructiveSkillSafety.java](com/github/b4ndithelps/tennogamenolife/ability/skill/ultimate/DestructiveSkillSafety.java) |
| `TennoGameNoLifeJadePlugin` | class | IWailaPlugin | WailaPlugin | 1 | 15 | [TennoGameNoLifeJadePlugin.java](com/github/b4ndithelps/tennogamenolife/compat/jade/TennoGameNoLifeJadePlugin.java) |
| `StarEssence` | class | Item | — | 0 | 14 | [StarEssence.java](com/github/b4ndithelps/tennogamenolife/item/StarEssence.java) |
| `ModEffects` | class | — | — | 0 | 13 | [ModEffects.java](com/github/b4ndithelps/tennogamenolife/registry/ModEffects.java) |
| `Tenreincarnationraces` | class | — | — | 3 | 82 | [Tenreincarnationraces.java](com/github/b4ndithelps/tennogamenolife/registry/race/Tenreincarnationraces.java) |
| `TenreincarnationEntities` | class | — | — | 1 | 38 | [TenreincarnationEntities.java](com/github/b4ndithelps/tennogamenolife/registry/entity/TenreincarnationEntities.java) |
| `TenreincarnationJukeboxSongs` | class | — | — | 1 | 18 | [TenreincarnationJukeboxSongs.java](com/github/b4ndithelps/tennogamenolife/registry/sound/TenreincarnationJukeboxSongs.java) |
| `TenreincarnationSounds` | class | — | — | 2 | 26 | [TenreincarnationSounds.java](com/github/b4ndithelps/tennogamenolife/registry/sound/TenreincarnationSounds.java) |
| `TenreincarnationCreativeTabs` | class | — | — | 1 | 36 | [TenreincarnationCreativeTabs.java](com/github/b4ndithelps/tennogamenolife/registry/item/TenreincarnationCreativeTabs.java) |
| `TenreincarnationSkill` | class | — | — | 1 | 40 | [TenreincarnationSkill.java](com/github/b4ndithelps/tennogamenolife/registry/skill/TenreincarnationSkill.java) |
| `ModEffects` | class | — | — | 0 | 13 | [ModEffects.java](com/github/b4ndithelps/tennogamenolife/registry/effect/ModEffects.java) |
| `TenreincarnationRenderTypes` | class | RenderType | — | 0 | 140 | [TenreincarnationRenderTypes.java](com/github/b4ndithelps/tennogamenolife/client/renderer/TenreincarnationRenderTypes.java) |
| `SickleBladeGlowLayer` | class | GeoRenderLayer | — | 1 | 36 | [SickleBladeGlowLayer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/layer/SickleBladeGlowLayer.java) |
| `PlayerFlugelWingsLayer` | class | RenderLayer | — | 4 | 98 | [PlayerFlugelWingsLayer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/layer/PlayerFlugelWingsLayer.java) |
| `PlayerExMachinaHaloLayer` | class | RenderLayer | — | 3 | 70 | [PlayerExMachinaHaloLayer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/layer/PlayerExMachinaHaloLayer.java) |
| `PlayerFlugelHaloLayer` | class | RenderLayer | — | 3 | 69 | [PlayerFlugelHaloLayer.java](com/github/b4ndithelps/tennogamenolife/client/renderer/layer/PlayerFlugelHaloLayer.java) |
| `AngelWingsModel` | class | GeoModel | — | 4 | 35 | [AngelWingsModel.java](com/github/b4ndithelps/tennogamenolife/client/model/AngelWingsModel.java) |
| `AngelHaloModel` | class | GeoModel | — | 3 | 23 | [AngelHaloModel.java](com/github/b4ndithelps/tennogamenolife/client/model/AngelHaloModel.java) |
| `DragonRoarCircleModel` | class | GeoModel | — | 3 | 33 | [DragonRoarCircleModel.java](com/github/b4ndithelps/tennogamenolife/client/model/DragonRoarCircleModel.java) |
| `BlackRodModel` | class | GeoModel | — | 3 | 24 | [BlackRodModel.java](com/github/b4ndithelps/tennogamenolife/client/model/BlackRodModel.java) |
| `XiubiHaloModel` | class | EntityModel | — | 3 | 75 | [XiubiHaloModel.java](com/github/b4ndithelps/tennogamenolife/client/model/XiubiHaloModel.java) |
| `SickleModel` | class | GeoModel | — | 4 | 30 | [SickleModel.java](com/github/b4ndithelps/tennogamenolife/client/model/SickleModel.java) |
| `AngelHalo` | class | GeoAnimatable | — | 4 | 32 | [AngelHalo.java](com/github/b4ndithelps/tennogamenolife/client/model/AngelHalo.java) |
| `WeakenEffect` | class | TensuraMobEffect | — | 0 | 17 | [WeakenEffect.java](com/github/b4ndithelps/tennogamenolife/effect/WeakenEffect.java) |
