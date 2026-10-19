# Tensura: Mysticism（`mysticism`）

> jar：`[转生附属]mysticism.jar` ｜ 版本：`2.2.1` ｜ 作者：Memoires, Dranyas, Zykestheone, Baderormous, HVN Bael
> 规模：**627 个类 / 173 个包 / 74790 行** ｜ 事件处理类 3 ｜ Mixin 45 ｜ 注册/管理类 37

**依赖**：`neoforge`、`minecraft`、`architectury`、`tensura`、`manascore_config`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `io.github.Memoires.mysticism.ability.skill.unique` | 31 | `PhaserSkill`、`HiddenRulerSkill`、`MelancholySkill` |
| `io.github.Memoires.mysticism.effect.ability` | 24 | `AuraHealingEffect`、`TailedBeastCloakEffect`、`PayItForwardEffect` |
| `io.github.Memoires.mysticism.ability.skill.intrinsic` | 16 | `RelapseSkill`、`ExoskeletonSkill`、`ShadowEntitySkill` |
| `io.github.Memoires.mysticism.client.model` | 16 | `EnkiduEntityModel`、`BubbleShieldBarrierEntityModel`、`ShadowImpEntityModel` |
| `io.github.Memoires.mysticism.client.renderer.entity.skill` | 15 | `ShadowImpEntityRenderer`、`EnkiduEntityRenderer`、`GateEntityRenderer` |
| `io.github.Memoires.mysticism.handler` | 14 | `LootHandler`、`AbilityHandler`、`AttributeHandler` |
| `io.github.Memoires.mysticism.handler.skill` | 12 | `RestrictedHandler`、`GardenerHandler`、`SubjugatorHandler` |
| `io.github.Memoires.mysticism.mixin.abilities` | 12 | `MixinDemonDominateMagic`、`MixinAirFlightArt`、`MixinFlameManipulationSkill` |
| `io.github.Memoires.mysticism.entity.skill` | 11 | `SentryEntity`、`KamuiPortalEntity`、`DispenserEntity` |
| `io.github.Memoires.mysticism.mixin` | 10 | `MixinAbilityGrantCommand`、`MixinSkillClientUtils`、`MixinReincarnationMenu` |
| `io.github.Memoires.mysticism.ability.skill.extra` | 10 | `ProfanedProminenceSkill`、`MithrilStrengthSkill`、`LightManipulationSkill` |
| `io.github.Memoires.mysticism.config.race` | 9 | `WyrmConfig`、`SculkConfig`、`AngelConfig` |
| `io.github.Memoires.mysticism.entity.projectile` | 9 | `BlueFlareProjectile`、`TailedBeastBombProjectile`、`SentryMissileProjectile` |
| `io.github.Memoires.mysticism.world.features` | 9 | `FireBlockBlobFeature`、`WaterBlobBlockFeature`、`WaterPodzolLakeFeature` |
| `io.github.Memoires.mysticism.neoforge.data.tag` | 8 | `MysticismBlockTagProvider`、`MysticismEntityTagProvider`、`MysticismItemTagProvider` |
| `io.github.Memoires.mysticism.neoforge.data` | 7 | `MysticismLanguageProvider`、`MysticismRegistryProvider`、`MysticismRecipeProvider` |
| `io.github.Memoires.mysticism.network.s2c` | 7 | `SyncLieModePacket`、`ClientAccess`、`OpenRupturingMenuPayload` |
| `io.github.Memoires.mysticism.race.elemental` | 7 | `LesserElementalRace`、`DivineMajinElementalRace`、`ElementalLordRace` |
| `io.github.Memoires.mysticism.ability.subclass` | 7 | `IBookStorage`、`IFlarefrostStorehouse`、`IProjectileEjection` |
| `io.github.Memoires.mysticism.world.biome.elemental_realm` | 7 | `LightBiome`、`SpatialBiome`、`FireBiome` |
| `io.github.Memoires.mysticism.config.race.insect` | 6 | `WaspConfig`、`CentipedeConfig`、`AntConfig` |
| `io.github.Memoires.mysticism.mixin.client` | 6 | `MixinMouseHandler`、`MixinScreenEffectRenderer`、`MixinEntityRenderDispatcher` |
| `io.github.Memoires.mysticism.mixin.entities` | 6 | `MixinEntity`、`MixinBeastGnomeEntity`、`MixinAquaFrogEntity` |
| `io.github.Memoires.mysticism.client.screen` | 6 | `RupturingScreen`、`BookStorageScreen`、`ButcheringScreen` |
| `io.github.Memoires.mysticism.client.renderer` | 6 | `OkamiRenderer`、`BlackHoleRenderer`、`MagicBoltRenderer` |
| `io.github.Memoires.mysticism.race.daemon_doll` | 6 | `GreaterDollRace`、`ArchDollRace`、`ChaosDollRace` |
| `io.github.Memoires.mysticism.item.client` | 6 | `AxiomRenderer`、`WaltzRenderer`、`RitualScytheRenderer` |
| `io.github.Memoires.mysticism.menu` | 5 | `BookStorageMenu`、`WeaponStorageMenu`、`FlarefrostStorehouseMenu` |
| `io.github.Memoires.mysticism.race.insect.wasp.queen` | 5 | `QueenWaspRace`、`DivineQueenWaspRace`、`QueenWaspSavantRace` |
| `io.github.Memoires.mysticism.race.insect.wasp.army` | 5 | `ArmyWaspRace`、`DivineArmyWaspRace`、`WindSoulInsectRace` |
| `io.github.Memoires.mysticism.race.insect.beetle.drone` | 5 | `DroneBeetleRace`、`DivineDroneBeetleRace`、`DroneBeetleInsectarRace` |
| `io.github.Memoires.mysticism.race.fallen` | 5 | `LesserFallenRace`、`FallenRace`、`GreaterFallenRace` |
| `io.github.Memoires.mysticism.race.phantom` | 5 | `StaffOfficerRace`、`PhantomRace`、`GeneralRace` |
| `io.github.Memoires.mysticism.race.fallen_angel` | 5 | `FallenLesserAngelRace`、`FallenCherubRace`、`FallenSeraphRace` |
| `io.github.Memoires.mysticism.data` | 5 | `MysticismEntityTags`、`MysticismItemTags`、`MysticismSkillTags` |
| `io.github.Memoires.mysticism.registry.item` | 5 | `MysticismMaterialItems`、`MysticismMobDropItems`、`MysticismWeaponItems` |
| `io.github.Memoires.mysticism.race.insect.mantis.lixivant` | 5 | `LixivantMantisRace`、`CorrosionSoulInsectRace`、`LixivantMantisSavantRace` |
| `io.github.Memoires.mysticism.race.insect.mantis.preying` | 5 | `PreyingMantisInsectarRace`、`PreyingMantisSavantRace`、`SteelSoulInsectRace` |
| `io.github.Memoires.mysticism.race.insect.ant.fire` | 5 | `FireAntRace`、`FireAntInsectarRace`、`FireAntSavantRace` |
| `io.github.Memoires.mysticism.race.insect.ant.hardshell` | 5 | `HardshellAntSavantRace`、`HardshellAntInsectarRace`、`HardshellAntRace` |
| … | | 其余 133 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientSetupHandlers` | class | — | EventBusSubscriber、OnlyIn | 2 | 40 | [ClientSetupHandlers.java](io/github/Memoires/mysticism/neoforge/handler/client/ClientSetupHandlers.java) |
| `CrasherHandler` | class | — | EventBusSubscriber | 1 | 59 | [CrasherHandler.java](io/github/Memoires/mysticism/neoforge/handler/CrasherHandler.java) |
| `MalleableHandler` | class | — | EventBusSubscriber | 3 | 91 | [MalleableHandler.java](io/github/Memoires/mysticism/neoforge/handler/MalleableHandler.java) |
| `WyrmConfig` | class | ManasConfig | SyncToClient | 177 | 1253 | [WyrmConfig.java](io/github/Memoires/mysticism/config/race/WyrmConfig.java) |
| `SculkConfig` | class | ManasConfig | SyncToClient | 163 | 1122 | [SculkConfig.java](io/github/Memoires/mysticism/config/race/SculkConfig.java) |
| `AngelConfig` | class | ManasConfig | SyncToClient | 225 | 1535 | [AngelConfig.java](io/github/Memoires/mysticism/config/race/AngelConfig.java) |
| `WaspConfig` | class | ManasConfig | SyncToClient | 158 | 1028 | [WaspConfig.java](io/github/Memoires/mysticism/config/race/insect/WaspConfig.java) |
| `CentipedeConfig` | class | ManasConfig | SyncToClient | 214 | 1406 | [CentipedeConfig.java](io/github/Memoires/mysticism/config/race/insect/CentipedeConfig.java) |
| `AntConfig` | class | ManasConfig | SyncToClient | 153 | 1020 | [AntConfig.java](io/github/Memoires/mysticism/config/race/insect/AntConfig.java) |
| `DirewolfConfig` | class | ManasConfig | SyncToClient | 260 | 1848 | [DirewolfConfig.java](io/github/Memoires/mysticism/config/race/direwolf/DirewolfConfig.java) |
| `SpecialDirewolfConfig` | class | ManasConfig | — | 235 | 1794 | [SpecialDirewolfConfig.java](io/github/Memoires/mysticism/config/race/direwolf/SpecialDirewolfConfig.java) |
| `MantisConfig` | class | ManasConfig | SyncToClient | 147 | 964 | [MantisConfig.java](io/github/Memoires/mysticism/config/race/insect/MantisConfig.java) |
| `BeetleConfig` | class | ManasConfig | SyncToClient | 147 | 977 | [BeetleConfig.java](io/github/Memoires/mysticism/config/race/insect/BeetleConfig.java) |
| `ScorpionConfig` | class | ManasConfig | SyncToClient | 147 | 969 | [ScorpionConfig.java](io/github/Memoires/mysticism/config/race/insect/ScorpionConfig.java) |
| `ForgottenConfig` | class | ManasConfig | — | 97 | 658 | [ForgottenConfig.java](io/github/Memoires/mysticism/config/race/ForgottenConfig.java) |
| `ElementalConfig` | class | ManasConfig | SyncToClient | 98 | 625 | [ElementalConfig.java](io/github/Memoires/mysticism/config/race/ElementalConfig.java) |
| `MixinOverlayHandler` | class | — | Mixin | 7 | 111 | [MixinOverlayHandler.java](io/github/Memoires/mysticism/mixin/handler/client/MixinOverlayHandler.java) |
| `DaemonDollConfig` | class | ManasConfig | SyncToClient | 76 | 492 | [DaemonDollConfig.java](io/github/Memoires/mysticism/config/race/DaemonDollConfig.java) |
| `PhantomConfig` | class | ManasConfig | SyncToClient | 63 | 437 | [PhantomConfig.java](io/github/Memoires/mysticism/config/race/PhantomConfig.java) |
| `MemoiresEntity` | class | OtherworlderEntity | — | 60 | 732 | [MemoiresEntity.java](io/github/Memoires/mysticism/entity/boss/memoires/MemoiresEntity.java) |
| `MysticismLanguageProvider` | class | LanguageProvider | — | 39 | 1466 | [MysticismLanguageProvider.java](io/github/Memoires/mysticism/neoforge/data/MysticismLanguageProvider.java) |
| `MixinClientPacketListener` | class | — | Mixin | 3 | 69 | [MixinClientPacketListener.java](io/github/Memoires/mysticism/mixin/network/MixinClientPacketListener.java) |
| `MixinMouseHandler` | class | — | Mixin | 0 | 29 | [MixinMouseHandler.java](io/github/Memoires/mysticism/mixin/client/MixinMouseHandler.java) |
| `RestrictedHumanConfig` | class | ManasConfig | SyncToClient | 53 | 356 | [RestrictedHumanConfig.java](io/github/Memoires/mysticism/config/race/RestrictedHumanConfig.java) |
| `OkamiEntity` | class | PlayerLikeEntity | — | 39 | 432 | [OkamiEntity.java](io/github/Memoires/mysticism/entity/monster/OkamiEntity.java) |
| `SentryEntity` | class | TensuraTamableEntity | — | 44 | 570 | [SentryEntity.java](io/github/Memoires/mysticism/entity/skill/SentryEntity.java) |
| `BlackHoleEntity` | class | Entity | — | 47 | 573 | [BlackHoleEntity.java](io/github/Memoires/mysticism/entity/magic/misc/BlackHoleEntity.java) |
| `MysticismNeoForge` | class | — | Mod | 1 | 63 | [MysticismNeoForge.java](io/github/Memoires/mysticism/neoforge/MysticismNeoForge.java) |
| `MixinPrayingPathBlockEntity` | class | — | Mixin | 1 | 39 | [MixinPrayingPathBlockEntity.java](io/github/Memoires/mysticism/mixin/block/MixinPrayingPathBlockEntity.java) |
| `MixinScreenEffectRenderer` | class | — | Mixin | 1 | 34 | [MixinScreenEffectRenderer.java](io/github/Memoires/mysticism/mixin/client/MixinScreenEffectRenderer.java) |
| `ShadowImpEntity` | class | TensuraTamableEntity | — | 36 | 360 | [ShadowImpEntity.java](io/github/Memoires/mysticism/entity/monster/ShadowImpEntity.java) |
| `MixinAbilityGrantCommand` | class | — | Mixin | 0 | 56 | [MixinAbilityGrantCommand.java](io/github/Memoires/mysticism/mixin/MixinAbilityGrantCommand.java) |
| `MixinSkillClientUtils` | class | — | Mixin | 0 | 33 | [MixinSkillClientUtils.java](io/github/Memoires/mysticism/mixin/MixinSkillClientUtils.java) |
| `MixinServerGamePacketListenerImpl` | class | — | Mixin | 0 | 55 | [MixinServerGamePacketListenerImpl.java](io/github/Memoires/mysticism/mixin/network/MixinServerGamePacketListenerImpl.java) |
| `RelapseSkill` | class | Skill | — | 36 | 667 | [RelapseSkill.java](io/github/Memoires/mysticism/ability/skill/intrinsic/RelapseSkill.java) |
| `PhaserSkill` | class | Skill | — | 29 | 830 | [PhaserSkill.java](io/github/Memoires/mysticism/ability/skill/unique/PhaserSkill.java) |
| `DragonoidConfig` | class | ManasConfig | — | 25 | 192 | [DragonoidConfig.java](io/github/Memoires/mysticism/config/race/DragonoidConfig.java) |
| `UniqueMysticismSkillConfig` | class | ManasConfig | — | 1 | 1198 | [UniqueMysticismSkillConfig.java](io/github/Memoires/mysticism/config/ability/skill/UniqueMysticismSkillConfig.java) |
| `MysticismEvolutionRequirement` | class | EvolutionRequirement | — | 31 | 403 | [MysticismEvolutionRequirement.java](io/github/Memoires/mysticism/race/template/MysticismEvolutionRequirement.java) |
| `LootHandler` | class | — | — | 1 | 82 | [LootHandler.java](io/github/Memoires/mysticism/handler/LootHandler.java) |
| `MixinReincarnationMenu` | class | — | Mixin | 0 | 56 | [MixinReincarnationMenu.java](io/github/Memoires/mysticism/mixin/MixinReincarnationMenu.java) |
| `MixinAnvilMenu` | class | — | Mixin | 0 | 37 | [MixinAnvilMenu.java](io/github/Memoires/mysticism/mixin/MixinAnvilMenu.java) |
| `MixinResetScrollItem` | class | — | Mixin | 0 | 64 | [MixinResetScrollItem.java](io/github/Memoires/mysticism/mixin/item/MixinResetScrollItem.java) |
| `MixinEntityRenderDispatcher` | class | — | Mixin | 2 | 70 | [MixinEntityRenderDispatcher.java](io/github/Memoires/mysticism/mixin/client/MixinEntityRenderDispatcher.java) |
| `MixinDeathScreen` | class | — | Mixin | 0 | 59 | [MixinDeathScreen.java](io/github/Memoires/mysticism/mixin/client/MixinDeathScreen.java) |
| `MixinPlayerRenderer` | class | — | Mixin | 2 | 67 | [MixinPlayerRenderer.java](io/github/Memoires/mysticism/mixin/client/MixinPlayerRenderer.java) |
| `MixinMainScreen` | class | SimpleScreen | Mixin | 0 | 47 | [MixinMainScreen.java](io/github/Memoires/mysticism/mixin/client/MixinMainScreen.java) |
| `MixinEntity` | class | — | Mixin | 1 | 46 | [MixinEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinEntity.java) |
| `MixinBeastGnomeEntity` | class | TensuraTamableEntity | Mixin | 1 | 48 | [MixinBeastGnomeEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinBeastGnomeEntity.java) |
| `MixinAquaFrogEntity` | class | TensuraTamableEntity | Mixin | 1 | 48 | [MixinAquaFrogEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinAquaFrogEntity.java) |
| `MixinSalamanderEntity` | class | TensuraTamableEntity | Mixin | 2 | 62 | [MixinSalamanderEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinSalamanderEntity.java) |
| `MixinFeatheredSerpentEntity` | class | TensuraTamableEntity | Mixin | 2 | 62 | [MixinFeatheredSerpentEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinFeatheredSerpentEntity.java) |
| `MixinWingedCatEntity` | class | TensuraTamableEntity | Mixin | 2 | 62 | [MixinWingedCatEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinWingedCatEntity.java) |
| `HiddenRulerSkill` | class | Skill | — | 25 | 712 | [HiddenRulerSkill.java](io/github/Memoires/mysticism/ability/skill/unique/HiddenRulerSkill.java) |
| `BookStorageMenu` | class | AbstractContainerMenu | — | 23 | 526 | [BookStorageMenu.java](io/github/Memoires/mysticism/menu/BookStorageMenu.java) |
| `MysticismSoundEvents` | class | — | — | 2 | 32 | [MysticismSoundEvents.java](io/github/Memoires/mysticism/registry/sound/MysticismSoundEvents.java) |
| `KamuiPortalEntity` | class | TensuraProjectile | — | 23 | 215 | [KamuiPortalEntity.java](io/github/Memoires/mysticism/entity/skill/KamuiPortalEntity.java) |
| `DispenserEntity` | class | TensuraTamableEntity | — | 24 | 358 | [DispenserEntity.java](io/github/Memoires/mysticism/entity/skill/DispenserEntity.java) |
| `MixinSkillUtils` | class | — | Mixin | 0 | 41 | [MixinSkillUtils.java](io/github/Memoires/mysticism/mixin/MixinSkillUtils.java) |
| `MixinMagicUtils` | class | — | Mixin | 0 | 19 | [MixinMagicUtils.java](io/github/Memoires/mysticism/mixin/MixinMagicUtils.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientSetupHandlers` | 2 | `io.github.Memoires.mysticism.neoforge.handler.client` | [ClientSetupHandlers.java](io/github/Memoires/mysticism/neoforge/handler/client/ClientSetupHandlers.java) |
| `CrasherHandler` | 1 | `io.github.Memoires.mysticism.neoforge.handler` | [CrasherHandler.java](io/github/Memoires/mysticism/neoforge/handler/CrasherHandler.java) |
| `MalleableHandler` | 1 | `io.github.Memoires.mysticism.neoforge.handler` | [MalleableHandler.java](io/github/Memoires/mysticism/neoforge/handler/MalleableHandler.java) |

## Mixin（45 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinOverlayHandler` | `OverlayHandler` | [MixinOverlayHandler.java](io/github/Memoires/mysticism/mixin/handler/client/MixinOverlayHandler.java) |
| `MixinClientPacketListener` | `ClientPacketListener` | [MixinClientPacketListener.java](io/github/Memoires/mysticism/mixin/network/MixinClientPacketListener.java) |
| `MixinMouseHandler` | `MouseHandler` | [MixinMouseHandler.java](io/github/Memoires/mysticism/mixin/client/MixinMouseHandler.java) |
| `MixinPrayingPathBlockEntity` | `PrayingPathBlockEntity` | [MixinPrayingPathBlockEntity.java](io/github/Memoires/mysticism/mixin/block/MixinPrayingPathBlockEntity.java) |
| `MixinScreenEffectRenderer` | `ScreenEffectRenderer` | [MixinScreenEffectRenderer.java](io/github/Memoires/mysticism/mixin/client/MixinScreenEffectRenderer.java) |
| `MixinAbilityGrantCommand` | `AbilityGrantCommand` | [MixinAbilityGrantCommand.java](io/github/Memoires/mysticism/mixin/MixinAbilityGrantCommand.java) |
| `MixinSkillClientUtils` | `SkillClientUtils` | [MixinSkillClientUtils.java](io/github/Memoires/mysticism/mixin/MixinSkillClientUtils.java) |
| `MixinServerGamePacketListenerImpl` | `ServerGamePacketListenerImpl` | [MixinServerGamePacketListenerImpl.java](io/github/Memoires/mysticism/mixin/network/MixinServerGamePacketListenerImpl.java) |
| `MixinReincarnationMenu` | `ReincarnationMenu` | [MixinReincarnationMenu.java](io/github/Memoires/mysticism/mixin/MixinReincarnationMenu.java) |
| `MixinAnvilMenu` | `AnvilMenu` | [MixinAnvilMenu.java](io/github/Memoires/mysticism/mixin/MixinAnvilMenu.java) |
| `MixinResetScrollItem` | `ResetScrollItem` | [MixinResetScrollItem.java](io/github/Memoires/mysticism/mixin/item/MixinResetScrollItem.java) |
| `MixinEntityRenderDispatcher` | `EntityRenderDispatcher` | [MixinEntityRenderDispatcher.java](io/github/Memoires/mysticism/mixin/client/MixinEntityRenderDispatcher.java) |
| `MixinDeathScreen` | `DeathScreen` | [MixinDeathScreen.java](io/github/Memoires/mysticism/mixin/client/MixinDeathScreen.java) |
| `MixinPlayerRenderer` | `PlayerRenderer` | [MixinPlayerRenderer.java](io/github/Memoires/mysticism/mixin/client/MixinPlayerRenderer.java) |
| `MixinMainScreen` | `MainScreen` | [MixinMainScreen.java](io/github/Memoires/mysticism/mixin/client/MixinMainScreen.java) |
| `MixinEntity` | `Entity` | [MixinEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinEntity.java) |
| `MixinBeastGnomeEntity` | `BeastGnomeEntity` | [MixinBeastGnomeEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinBeastGnomeEntity.java) |
| `MixinAquaFrogEntity` | `AquaFrogEntity` | [MixinAquaFrogEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinAquaFrogEntity.java) |
| `MixinSalamanderEntity` | `SalamanderEntity` | [MixinSalamanderEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinSalamanderEntity.java) |
| `MixinFeatheredSerpentEntity` | `FeatheredSerpentEntity` | [MixinFeatheredSerpentEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinFeatheredSerpentEntity.java) |
| `MixinWingedCatEntity` | `WingedCatEntity` | [MixinWingedCatEntity.java](io/github/Memoires/mysticism/mixin/entities/MixinWingedCatEntity.java) |
| `MixinSkillUtils` | `SkillUtils` | [MixinSkillUtils.java](io/github/Memoires/mysticism/mixin/MixinSkillUtils.java) |
| `MixinMagicUtils` | `MagicUtils` | [MixinMagicUtils.java](io/github/Memoires/mysticism/mixin/MixinMagicUtils.java) |
| `MixinEnergyHelper` | `EnergyHelper` | [MixinEnergyHelper.java](io/github/Memoires/mysticism/mixin/MixinEnergyHelper.java) |
| `MixinTensuraSkill` | `TensuraSkill` | [MixinTensuraSkill.java](io/github/Memoires/mysticism/mixin/MixinTensuraSkill.java) |
| `MixinBeamProjectile` | `BeamProjectile` | [MixinBeamProjectile.java](io/github/Memoires/mysticism/mixin/MixinBeamProjectile.java) |
| `MixinTensuraRace` | `TensuraRace` | [MixinTensuraRace.java](io/github/Memoires/mysticism/mixin/MixinTensuraRace.java) |
| `MixinExistenceStorage` | `ExistenceStorage` | [MixinExistenceStorage.java](io/github/Memoires/mysticism/mixin/storage/MixinExistenceStorage.java) |
| `MixinMetalSlime` | `MetalSlime` | [MixinMetalSlime.java](io/github/Memoires/mysticism/mixin/races/MixinMetalSlime.java) |
| `MixinGreaterDaemon` | `GreaterDaemon` | [MixinGreaterDaemon.java](io/github/Memoires/mysticism/mixin/races/MixinGreaterDaemon.java) |
| `MixinArchDaemon` | `ArchDaemon` | [MixinArchDaemon.java](io/github/Memoires/mysticism/mixin/races/MixinArchDaemon.java) |
| `MixinDemonDominateMagic` | `DemonDominateMagic` | [MixinDemonDominateMagic.java](io/github/Memoires/mysticism/mixin/abilities/MixinDemonDominateMagic.java) |
| `MixinAirFlightArt` | `AirFlightArt` | [MixinAirFlightArt.java](io/github/Memoires/mysticism/mixin/abilities/MixinAirFlightArt.java) |
| `MixinFlameManipulationSkill` | `FlameManipulationSkill` | [MixinFlameManipulationSkill.java](io/github/Memoires/mysticism/mixin/abilities/MixinFlameManipulationSkill.java) |
| `MixinUltraspeedRegenerationSkill` | `UltraspeedRegenerationSkill` | [MixinUltraspeedRegenerationSkill.java](io/github/Memoires/mysticism/mixin/abilities/MixinUltraspeedRegenerationSkill.java) |
| `MixinInfiniteRegenerationSkill` | `InfiniteRegenerationSkill` | [MixinInfiniteRegenerationSkill.java](io/github/Memoires/mysticism/mixin/abilities/MixinInfiniteRegenerationSkill.java) |
| `MixinSenseHeatSourceSkill` | `SenseHeatSourceSkill` | [MixinSenseHeatSourceSkill.java](io/github/Memoires/mysticism/mixin/abilities/MixinSenseHeatSourceSkill.java) |
| `MixinResistSkill` | `ResistSkill` | [MixinResistSkill.java](io/github/Memoires/mysticism/mixin/abilities/MixinResistSkill.java) |
| `MixinGreedSkill` | `GreedSkill` | [MixinGreedSkill.java](io/github/Memoires/mysticism/mixin/abilities/MixinGreedSkill.java) |
| `MixinDominateMagic` | `DominateMagic` | [MixinDominateMagic.java](io/github/Memoires/mysticism/mixin/abilities/MixinDominateMagic.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `WyrmConfig` | SyncToClient | [WyrmConfig.java](io/github/Memoires/mysticism/config/race/WyrmConfig.java) |
| `SculkConfig` | SyncToClient | [SculkConfig.java](io/github/Memoires/mysticism/config/race/SculkConfig.java) |
| `AngelConfig` | SyncToClient | [AngelConfig.java](io/github/Memoires/mysticism/config/race/AngelConfig.java) |
| `WaspConfig` | SyncToClient | [WaspConfig.java](io/github/Memoires/mysticism/config/race/insect/WaspConfig.java) |
| `CentipedeConfig` | SyncToClient | [CentipedeConfig.java](io/github/Memoires/mysticism/config/race/insect/CentipedeConfig.java) |
| `AntConfig` | SyncToClient | [AntConfig.java](io/github/Memoires/mysticism/config/race/insect/AntConfig.java) |
| `DirewolfConfig` | SyncToClient | [DirewolfConfig.java](io/github/Memoires/mysticism/config/race/direwolf/DirewolfConfig.java) |
| `SpecialDirewolfConfig` | — | [SpecialDirewolfConfig.java](io/github/Memoires/mysticism/config/race/direwolf/SpecialDirewolfConfig.java) |
| `MantisConfig` | SyncToClient | [MantisConfig.java](io/github/Memoires/mysticism/config/race/insect/MantisConfig.java) |
| `BeetleConfig` | SyncToClient | [BeetleConfig.java](io/github/Memoires/mysticism/config/race/insect/BeetleConfig.java) |
| `ScorpionConfig` | SyncToClient | [ScorpionConfig.java](io/github/Memoires/mysticism/config/race/insect/ScorpionConfig.java) |
| `ForgottenConfig` | — | [ForgottenConfig.java](io/github/Memoires/mysticism/config/race/ForgottenConfig.java) |
| `ElementalConfig` | SyncToClient | [ElementalConfig.java](io/github/Memoires/mysticism/config/race/ElementalConfig.java) |
| `DaemonDollConfig` | SyncToClient | [DaemonDollConfig.java](io/github/Memoires/mysticism/config/race/DaemonDollConfig.java) |
| `PhantomConfig` | SyncToClient | [PhantomConfig.java](io/github/Memoires/mysticism/config/race/PhantomConfig.java) |
| `RestrictedHumanConfig` | SyncToClient | [RestrictedHumanConfig.java](io/github/Memoires/mysticism/config/race/RestrictedHumanConfig.java) |
| `DragonoidConfig` | — | [DragonoidConfig.java](io/github/Memoires/mysticism/config/race/DragonoidConfig.java) |
| `UniqueMysticismSkillConfig` | — | [UniqueMysticismSkillConfig.java](io/github/Memoires/mysticism/config/ability/skill/UniqueMysticismSkillConfig.java) |
| `MysticismConfigs` | — | [MysticismConfigs.java](io/github/Memoires/mysticism/config/MysticismConfigs.java) |
| `IntrinsicMysticismSkillConfig` | — | [IntrinsicMysticismSkillConfig.java](io/github/Memoires/mysticism/config/ability/skill/IntrinsicMysticismSkillConfig.java) |
| `MysticismGeneralConfig` | — | [MysticismGeneralConfig.java](io/github/Memoires/mysticism/config/MysticismGeneralConfig.java) |
| `MysticismSpawnRateConfig` | — | [MysticismSpawnRateConfig.java](io/github/Memoires/mysticism/config/entity/MysticismSpawnRateConfig.java) |
| `ExtraMysticismSkillConfig` | — | [ExtraMysticismSkillConfig.java](io/github/Memoires/mysticism/config/ability/skill/ExtraMysticismSkillConfig.java) |
| `SpiritualMysticismMagicConfig` | SyncToClient | [SpiritualMysticismMagicConfig.java](io/github/Memoires/mysticism/config/ability/magic/SpiritualMysticismMagicConfig.java) |
| `EffectConfig` | — | [EffectConfig.java](io/github/Memoires/mysticism/config/effect/EffectConfig.java) |
| `MysticismConfiguredFeatures` | — | [MysticismConfiguredFeatures.java](io/github/Memoires/mysticism/registry/world/MysticismConfiguredFeatures.java) |

## 按包浏览

- `architectury_inject_mysticism_common_1a1b8f77b8024d1abdb30e7e901bc5fa_fd824f943de1975ab323f3f5c2069c19c0032ab765f9fceaeecb4875083d6efecommon221devjar`（1 类）
- `io.github.Memoires.mysticism`（1 类）
- `io.github.Memoires.mysticism.ability`（2 类）
- `io.github.Memoires.mysticism.ability.magic.spiritual.earth`（1 类）
- `io.github.Memoires.mysticism.ability.skill.extra`（10 类）
- `io.github.Memoires.mysticism.ability.skill.intrinsic`（16 类）
- `io.github.Memoires.mysticism.ability.skill.ultimate`（1 类）
- `io.github.Memoires.mysticism.ability.skill.unique`（31 类）
- `io.github.Memoires.mysticism.ability.subclass`（7 类）
- `io.github.Memoires.mysticism.block`（1 类）
- `io.github.Memoires.mysticism.bridge`（3 类）
- `io.github.Memoires.mysticism.bridge.neoforge`（3 类）
- `io.github.Memoires.mysticism.client`（1 类）
- `io.github.Memoires.mysticism.client.ability`（1 类）
- `io.github.Memoires.mysticism.client.layer.multi`（1 类）
- `io.github.Memoires.mysticism.client.model`（16 类）
- `io.github.Memoires.mysticism.client.renderer`（6 类）
- `io.github.Memoires.mysticism.client.renderer.entity.monster`（1 类）
- `io.github.Memoires.mysticism.client.renderer.entity.skill`（15 类）
- `io.github.Memoires.mysticism.client.screen`（6 类）
- `io.github.Memoires.mysticism.command`（1 类）
- `io.github.Memoires.mysticism.command.edit`（2 类）
- `io.github.Memoires.mysticism.command.get`（1 类）
- `io.github.Memoires.mysticism.config`（2 类）
- `io.github.Memoires.mysticism.config.ability.magic`（1 类）
- `io.github.Memoires.mysticism.config.ability.skill`（3 类）
- `io.github.Memoires.mysticism.config.effect`（1 类）
- `io.github.Memoires.mysticism.config.entity`（1 类）
- `io.github.Memoires.mysticism.config.race`（9 类）
- `io.github.Memoires.mysticism.config.race.direwolf`（2 类）
- `io.github.Memoires.mysticism.config.race.insect`（6 类）
- `io.github.Memoires.mysticism.damage`（2 类）
- `io.github.Memoires.mysticism.data`（5 类）
- `io.github.Memoires.mysticism.data.chunk`（2 类）
- `io.github.Memoires.mysticism.data.existence`（2 类）
- `io.github.Memoires.mysticism.data.otherworlder`（1 类）
- `io.github.Memoires.mysticism.effect.ability`（24 类）
- `io.github.Memoires.mysticism.effect.ability.constant`（3 类）
- `io.github.Memoires.mysticism.effect.debuff`（5 类）
- `io.github.Memoires.mysticism.enchantment`（1 类）
- `io.github.Memoires.mysticism.enchantment.effect`（1 类）
- `io.github.Memoires.mysticism.entity.boss.memoires`（1 类）
- `io.github.Memoires.mysticism.entity.goals`（2 类）
- `io.github.Memoires.mysticism.entity.golems`（1 类）
- `io.github.Memoires.mysticism.entity.item`（1 类）
- `io.github.Memoires.mysticism.entity.magic.field`（1 类）
- `io.github.Memoires.mysticism.entity.magic.misc`（1 类）
- `io.github.Memoires.mysticism.entity.magic.spike`（1 类）
- `io.github.Memoires.mysticism.entity.monster`（2 类）
- `io.github.Memoires.mysticism.entity.projectile`（9 类）
- `io.github.Memoires.mysticism.entity.skill`（11 类）
- `io.github.Memoires.mysticism.entity.variant`（1 类）
- `io.github.Memoires.mysticism.event`（3 类）
- `io.github.Memoires.mysticism.handler`（14 类）
- `io.github.Memoires.mysticism.handler.client`（2 类）
- `io.github.Memoires.mysticism.handler.skill`（12 类）
- `io.github.Memoires.mysticism.item.client`（6 类）
- `io.github.Memoires.mysticism.item.misc`（2 类）
- `io.github.Memoires.mysticism.item.weapon`（3 类）
- `io.github.Memoires.mysticism.menu`（5 类）
- `io.github.Memoires.mysticism.menu.container`（2 类）
- `io.github.Memoires.mysticism.menu.slot`（4 类）
- `io.github.Memoires.mysticism.mixin`（10 类）
- `io.github.Memoires.mysticism.mixin.abilities`（12 类）
- `io.github.Memoires.mysticism.mixin.block`（1 类）
- `io.github.Memoires.mysticism.mixin.client`（6 类）
- `io.github.Memoires.mysticism.mixin.effect`（2 类）
- `io.github.Memoires.mysticism.mixin.entities`（6 类）
- `io.github.Memoires.mysticism.mixin.handler.client`（1 类）
- `io.github.Memoires.mysticism.mixin.item`（1 类）
- `io.github.Memoires.mysticism.mixin.network`（2 类）
- `io.github.Memoires.mysticism.mixin.races`（3 类）
- `io.github.Memoires.mysticism.mixin.storage`（1 类）
- `io.github.Memoires.mysticism.neoforge`（1 类）
- `io.github.Memoires.mysticism.neoforge.data`（7 类）
- `io.github.Memoires.mysticism.neoforge.data.loot`（2 类）
- `io.github.Memoires.mysticism.neoforge.data.tag`（8 类）
- `io.github.Memoires.mysticism.neoforge.handler`（2 类）
- `io.github.Memoires.mysticism.neoforge.handler.client`（1 类）
- `io.github.Memoires.mysticism.network`（1 类）
- `io.github.Memoires.mysticism.network.c2s`（3 类）
- `io.github.Memoires.mysticism.network.s2c`（7 类）
- `io.github.Memoires.mysticism.race`（1 类）
- `io.github.Memoires.mysticism.race.angel`（5 类）
- `io.github.Memoires.mysticism.race.daemon_doll`（6 类）
- `io.github.Memoires.mysticism.race.direwolf`（2 类）
- `io.github.Memoires.mysticism.race.direwolf.black`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.blue`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.brown`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.dark`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.frost`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.green`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.guitar`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.light`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.purple`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.red`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.scorch`（3 类）
- `io.github.Memoires.mysticism.race.direwolf.tempest`（3 类）
- `io.github.Memoires.mysticism.race.dragonoid`（2 类）
- `io.github.Memoires.mysticism.race.elemental`（7 类）
- `io.github.Memoires.mysticism.race.elemental.pixie`（2 类）
- `io.github.Memoires.mysticism.race.elemental.pixie.dryad`（2 类）
- `io.github.Memoires.mysticism.race.fallen`（5 类）
- `io.github.Memoires.mysticism.race.fallen_angel`（5 类）
- `io.github.Memoires.mysticism.race.forgotten`（2 类）
- `io.github.Memoires.mysticism.race.forgotten.empty`（3 类）
- `io.github.Memoires.mysticism.race.forgotten.whole`（3 类）
- `io.github.Memoires.mysticism.race.insect`（1 类）
- `io.github.Memoires.mysticism.race.insect.ant`（1 类）
- `io.github.Memoires.mysticism.race.insect.ant.fire`（5 类）
- `io.github.Memoires.mysticism.race.insect.ant.hardshell`（5 类）
- `io.github.Memoires.mysticism.race.insect.beetle`（1 类）
- `io.github.Memoires.mysticism.race.insect.beetle.drone`（5 类）
- `io.github.Memoires.mysticism.race.insect.beetle.stag`（5 类）
- `io.github.Memoires.mysticism.race.insect.centipede`（1 类）
- `io.github.Memoires.mysticism.race.insect.centipede.blue`（5 类）
- `io.github.Memoires.mysticism.race.insect.centipede.purple`（5 类）
- `io.github.Memoires.mysticism.race.insect.centipede.yellow`（5 类）
- `io.github.Memoires.mysticism.race.insect.mantis`（1 类）
- `io.github.Memoires.mysticism.race.insect.mantis.lixivant`（5 类）
- `io.github.Memoires.mysticism.race.insect.mantis.preying`（5 类）
- `io.github.Memoires.mysticism.race.insect.scorpion`（1 类）
- `io.github.Memoires.mysticism.race.insect.scorpion.loxodrome`（5 类）
- `io.github.Memoires.mysticism.race.insect.scorpion.singularity`（5 类）
- `io.github.Memoires.mysticism.race.insect.wasp`（1 类）
- `io.github.Memoires.mysticism.race.insect.wasp.army`（5 类）
- `io.github.Memoires.mysticism.race.insect.wasp.queen`（5 类）
- `io.github.Memoires.mysticism.race.phantom`（5 类）
- `io.github.Memoires.mysticism.race.restricted_human`（4 类）
- `io.github.Memoires.mysticism.race.sculk`（1 类）
- `io.github.Memoires.mysticism.race.sculk.flame`（4 类）
- `io.github.Memoires.mysticism.race.sculk.lightning`（4 类）
- `io.github.Memoires.mysticism.race.sculk.normal`（4 类）
- `io.github.Memoires.mysticism.race.template`（1 类）
- `io.github.Memoires.mysticism.race.tengu`（3 类）
- `io.github.Memoires.mysticism.race.wyrm`（1 类）
- `io.github.Memoires.mysticism.race.wyrm.glacier`（2 类）
- `io.github.Memoires.mysticism.race.wyrm.glacier.drake`（2 类）
- `io.github.Memoires.mysticism.race.wyrm.glacier.serpent`（2 类）
- `io.github.Memoires.mysticism.race.wyrm.pyre`（2 类）
- `io.github.Memoires.mysticism.race.wyrm.pyre.scorch`（2 类）
- `io.github.Memoires.mysticism.race.wyrm.pyre.sun`（2 类）
- `io.github.Memoires.mysticism.registry`（2 类）
- `io.github.Memoires.mysticism.registry.attributes`（1 类）
- `io.github.Memoires.mysticism.registry.block`（1 类）
- `io.github.Memoires.mysticism.registry.component`（1 类）
- `io.github.Memoires.mysticism.registry.dimension`（2 类）
- `io.github.Memoires.mysticism.registry.effects`（1 类）
- `io.github.Memoires.mysticism.registry.entity`（4 类）
- `io.github.Memoires.mysticism.registry.item`（5 类）
- `io.github.Memoires.mysticism.registry.item.misc`（1 类）
- `io.github.Memoires.mysticism.registry.magic`（1 类）
- `io.github.Memoires.mysticism.registry.menu`（1 类）
- `io.github.Memoires.mysticism.registry.race`（1 类）
- `io.github.Memoires.mysticism.registry.skill`（4 类）
- `io.github.Memoires.mysticism.registry.sound`（1 类）
- `io.github.Memoires.mysticism.registry.world`（4 类）
- `io.github.Memoires.mysticism.storage`（1 类）
- `io.github.Memoires.mysticism.storage.effect`（2 类）
- `io.github.Memoires.mysticism.storage.element`（3 类）
- `io.github.Memoires.mysticism.storage.entity`（2 类）
- `io.github.Memoires.mysticism.storage.player`（2 类）
- `io.github.Memoires.mysticism.storage.soulEnergy`（2 类）
- `io.github.Memoires.mysticism.util`（4 类）
- `io.github.Memoires.mysticism.util.effect`（2 类）
- `io.github.Memoires.mysticism.util.fourth_wall`（1 类）
- `io.github.Memoires.mysticism.util.subjugator`（2 类）
- `io.github.Memoires.mysticism.world`（2 类）
- `io.github.Memoires.mysticism.world.biome.elemental_realm`（7 类）
- `io.github.Memoires.mysticism.world.biome.kamui_dimension`（1 类）
- `io.github.Memoires.mysticism.world.dimension`（4 类）
- `io.github.Memoires.mysticism.world.features`（9 类）
- `io.github.Memoires.mysticism.world.ledgers`（1 类）
