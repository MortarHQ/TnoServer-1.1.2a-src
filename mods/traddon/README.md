# TR Addon（`traddon`）

> jar：`[转生附属]traddon.jar` ｜ 版本：`2.0.1` ｜ 作者：blocker315
> 规模：**37 个类 / 19 个包 / 3135 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 3

**依赖**：`neoforge`、`minecraft`、`architectury`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.dominickwd04.traddon.ability.skill.unique` | 8 | `NecromancerSkill`、`DevastatorSkill`、`OriginSkill` |
| `com.github.dominickwd04.traddon.race.lithoid` | 4 | `DiamondSovereignRace`、`GeodeGolemRace`、`PrismKnightRace` |
| `com.github.dominickwd04.traddon.effect` | 4 | `DespairEffect`、`PostMortemSurgeEffect`、`AtomicAnnihilationEffect` |
| `com.github.dominickwd04.traddon.ability.skill.intrinsic` | 3 | `PhotonBladeSkill`、`CrystalDustSkill`、`RefractiveShellSkill` |
| `com.github.dominickwd04.traddon.handler` | 2 | `DeathHandler`、`TrAddonHandlers` |
| `com.github.dominickwd04.traddon.config` | 2 | `TrAddonConfigs`、`TrAddonGeneral` |
| `com.github.dominickwd04.traddon.registry.skill` | 2 | `TrAddonIntrinsicSkills`、`TrAddonUniqueSkills` |
| `com.github.dominickwd04.traddon.config.race` | 1 | `ShardlingConfig` |
| `com.github.dominickwd04.neoforge` | 1 | `TrAddonNeoForge` |
| `com.github.dominickwd04.traddon.registry` | 1 | `TrAddonRegistry` |
| `com.github.dominickwd04.traddon.config.ability.skill` | 1 | `TrAddonSkillConfig` |
| `com.github.dominickwd04.traddon.registry.entity` | 1 | `TrAddonMiscEntityTypes` |
| `com.github.dominickwd04.traddon.client` | 1 | `TrAddonClient` |
| `com.github.dominickwd04.traddon.entity.magic.field.cloud` | 1 | `CrystalDustCloud` |
| `com.github.dominickwd04.traddon.ability.skill` | 1 | `ExampleSkill` |
| `architectury_inject_traddon_common_656dd314cc114e91936432e81c19e003_59dbd9276cfdfa5d73cf8067f4a1eb84dafc459a794754b2a74a889c4c244bedtraddoncommon201devjar` | 1 | `PlatformMethods` |
| `com.github.dominickwd04.traddon` | 1 | `TrAddon` |
| `com.github.dominickwd04.traddon.registry.race` | 1 | `TrAddonRaces` |
| `com.github.dominickwd04.traddon.registry.effect` | 1 | `TrAddonMobEffects` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ShardlingConfig` | class | ManasConfig | SyncToClient | 52 | 337 | [ShardlingConfig.java](com/github/dominickwd04/traddon/config/race/ShardlingConfig.java) |
| `TrAddonNeoForge` | class | — | Mod | 0 | 12 | [TrAddonNeoForge.java](com/github/dominickwd04/neoforge/TrAddonNeoForge.java) |
| `TrAddonRegistry` | class | — | — | 1 | 18 | [TrAddonRegistry.java](com/github/dominickwd04/traddon/registry/TrAddonRegistry.java) |
| `DeathHandler` | class | — | — | 1 | 39 | [DeathHandler.java](com/github/dominickwd04/traddon/handler/DeathHandler.java) |
| `TrAddonHandlers` | class | — | — | 1 | 8 | [TrAddonHandlers.java](com/github/dominickwd04/traddon/handler/TrAddonHandlers.java) |
| `NecromancerSkill` | class | Skill | — | 21 | 274 | [NecromancerSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/NecromancerSkill.java) |
| `TrAddonSkillConfig` | class | ManasConfig | — | 1 | 227 | [TrAddonSkillConfig.java](com/github/dominickwd04/traddon/config/ability/skill/TrAddonSkillConfig.java) |
| `DevastatorSkill` | class | Skill | — | 19 | 220 | [DevastatorSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/DevastatorSkill.java) |
| `TrAddonConfigs` | class | — | — | 3 | 83 | [TrAddonConfigs.java](com/github/dominickwd04/traddon/config/TrAddonConfigs.java) |
| `OriginSkill` | class | ResistSkill | — | 12 | 214 | [OriginSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/OriginSkill.java) |
| `DeviatorSkill` | class | PossessionSkill | — | 10 | 214 | [DeviatorSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/DeviatorSkill.java) |
| `TotalReflectionSkill` | class | Skill | — | 14 | 222 | [TotalReflectionSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/TotalReflectionSkill.java) |
| `PhotonBladeSkill` | class | Skill | — | 10 | 76 | [PhotonBladeSkill.java](com/github/dominickwd04/traddon/ability/skill/intrinsic/PhotonBladeSkill.java) |
| `ResonantCoreSkill` | class | ResistSkill | — | 12 | 118 | [ResonantCoreSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/ResonantCoreSkill.java) |
| `TrAddonMiscEntityTypes` | class | — | — | 1 | 21 | [TrAddonMiscEntityTypes.java](com/github/dominickwd04/traddon/registry/entity/TrAddonMiscEntityTypes.java) |
| `TrAddonClient` | class | — | — | 1 | 12 | [TrAddonClient.java](com/github/dominickwd04/traddon/client/TrAddonClient.java) |
| `DiamondSovereignRace` | class | PrismKnightRace | — | 7 | 69 | [DiamondSovereignRace.java](com/github/dominickwd04/traddon/race/lithoid/DiamondSovereignRace.java) |
| `GeodeGolemRace` | class | ShardlingRace | — | 7 | 65 | [GeodeGolemRace.java](com/github/dominickwd04/traddon/race/lithoid/GeodeGolemRace.java) |
| `PrismKnightRace` | class | GeodeGolemRace | — | 7 | 67 | [PrismKnightRace.java](com/github/dominickwd04/traddon/race/lithoid/PrismKnightRace.java) |
| `ShardlingRace` | class | DefaultRace | — | 7 | 62 | [ShardlingRace.java](com/github/dominickwd04/traddon/race/lithoid/ShardlingRace.java) |
| `CrystalDustCloud` | class | AreaCloud | — | 5 | 75 | [CrystalDustCloud.java](com/github/dominickwd04/traddon/entity/magic/field/cloud/CrystalDustCloud.java) |
| `ExampleSkill` | class | Skill | — | 6 | 37 | [ExampleSkill.java](com/github/dominickwd04/traddon/ability/skill/ExampleSkill.java) |
| `CrystalDustSkill` | class | Skill | — | 6 | 72 | [CrystalDustSkill.java](com/github/dominickwd04/traddon/ability/skill/intrinsic/CrystalDustSkill.java) |
| `RefractiveShellSkill` | class | ResistSkill | — | 9 | 96 | [RefractiveShellSkill.java](com/github/dominickwd04/traddon/ability/skill/intrinsic/RefractiveShellSkill.java) |
| `SilentiumSkill` | class | ResistSkill | — | 6 | 67 | [SilentiumSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/SilentiumSkill.java) |
| `SpatialSamuraiSkill` | class | Skill | — | 9 | 117 | [SpatialSamuraiSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/SpatialSamuraiSkill.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_traddon_common_656dd314cc114e91936432e81c19e003_59dbd9276cfdfa5d73cf8067f4a1eb84dafc459a794754b2a74a889c4c244bedtraddoncommon201devjar/PlatformMethods.java) |
| `TrAddon` | class | — | — | 1 | 23 | [TrAddon.java](com/github/dominickwd04/traddon/TrAddon.java) |
| `TrAddonGeneral` | class | ManasConfig | — | 1 | 19 | [TrAddonGeneral.java](com/github/dominickwd04/traddon/config/TrAddonGeneral.java) |
| `TrAddonRaces` | class | — | — | 1 | 23 | [TrAddonRaces.java](com/github/dominickwd04/traddon/registry/race/TrAddonRaces.java) |
| `TrAddonIntrinsicSkills` | class | — | — | 1 | 21 | [TrAddonIntrinsicSkills.java](com/github/dominickwd04/traddon/registry/skill/TrAddonIntrinsicSkills.java) |
| `TrAddonUniqueSkills` | class | — | — | 1 | 21 | [TrAddonUniqueSkills.java](com/github/dominickwd04/traddon/registry/skill/TrAddonUniqueSkills.java) |
| `TrAddonMobEffects` | class | — | — | 2 | 31 | [TrAddonMobEffects.java](com/github/dominickwd04/traddon/registry/effect/TrAddonMobEffects.java) |
| `DespairEffect` | class | TensuraMobEffect | — | 2 | 59 | [DespairEffect.java](com/github/dominickwd04/traddon/effect/DespairEffect.java) |
| `PostMortemSurgeEffect` | class | TensuraMobEffect | — | 0 | 22 | [PostMortemSurgeEffect.java](com/github/dominickwd04/traddon/effect/PostMortemSurgeEffect.java) |
| `AtomicAnnihilationEffect` | class | TensuraMobEffect | — | 0 | 18 | [AtomicAnnihilationEffect.java](com/github/dominickwd04/traddon/effect/AtomicAnnihilationEffect.java) |
| `PhotonBladeEffect` | class | TensuraMobEffect | — | 1 | 68 | [PhotonBladeEffect.java](com/github/dominickwd04/traddon/effect/PhotonBladeEffect.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ShardlingConfig` | SyncToClient | [ShardlingConfig.java](com/github/dominickwd04/traddon/config/race/ShardlingConfig.java) |
| `TrAddonSkillConfig` | — | [TrAddonSkillConfig.java](com/github/dominickwd04/traddon/config/ability/skill/TrAddonSkillConfig.java) |
| `TrAddonConfigs` | — | [TrAddonConfigs.java](com/github/dominickwd04/traddon/config/TrAddonConfigs.java) |

## 全部类（37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ShardlingConfig` | class | ManasConfig | SyncToClient | 52 | 337 | [ShardlingConfig.java](com/github/dominickwd04/traddon/config/race/ShardlingConfig.java) |
| `TrAddonNeoForge` | class | — | Mod | 0 | 12 | [TrAddonNeoForge.java](com/github/dominickwd04/neoforge/TrAddonNeoForge.java) |
| `TrAddonRegistry` | class | — | — | 1 | 18 | [TrAddonRegistry.java](com/github/dominickwd04/traddon/registry/TrAddonRegistry.java) |
| `DeathHandler` | class | — | — | 1 | 39 | [DeathHandler.java](com/github/dominickwd04/traddon/handler/DeathHandler.java) |
| `TrAddonHandlers` | class | — | — | 1 | 8 | [TrAddonHandlers.java](com/github/dominickwd04/traddon/handler/TrAddonHandlers.java) |
| `NecromancerSkill` | class | Skill | — | 21 | 274 | [NecromancerSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/NecromancerSkill.java) |
| `TrAddonSkillConfig` | class | ManasConfig | — | 1 | 227 | [TrAddonSkillConfig.java](com/github/dominickwd04/traddon/config/ability/skill/TrAddonSkillConfig.java) |
| `DevastatorSkill` | class | Skill | — | 19 | 220 | [DevastatorSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/DevastatorSkill.java) |
| `TrAddonConfigs` | class | — | — | 3 | 83 | [TrAddonConfigs.java](com/github/dominickwd04/traddon/config/TrAddonConfigs.java) |
| `OriginSkill` | class | ResistSkill | — | 12 | 214 | [OriginSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/OriginSkill.java) |
| `DeviatorSkill` | class | PossessionSkill | — | 10 | 214 | [DeviatorSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/DeviatorSkill.java) |
| `TotalReflectionSkill` | class | Skill | — | 14 | 222 | [TotalReflectionSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/TotalReflectionSkill.java) |
| `PhotonBladeSkill` | class | Skill | — | 10 | 76 | [PhotonBladeSkill.java](com/github/dominickwd04/traddon/ability/skill/intrinsic/PhotonBladeSkill.java) |
| `ResonantCoreSkill` | class | ResistSkill | — | 12 | 118 | [ResonantCoreSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/ResonantCoreSkill.java) |
| `TrAddonMiscEntityTypes` | class | — | — | 1 | 21 | [TrAddonMiscEntityTypes.java](com/github/dominickwd04/traddon/registry/entity/TrAddonMiscEntityTypes.java) |
| `TrAddonClient` | class | — | — | 1 | 12 | [TrAddonClient.java](com/github/dominickwd04/traddon/client/TrAddonClient.java) |
| `DiamondSovereignRace` | class | PrismKnightRace | — | 7 | 69 | [DiamondSovereignRace.java](com/github/dominickwd04/traddon/race/lithoid/DiamondSovereignRace.java) |
| `GeodeGolemRace` | class | ShardlingRace | — | 7 | 65 | [GeodeGolemRace.java](com/github/dominickwd04/traddon/race/lithoid/GeodeGolemRace.java) |
| `PrismKnightRace` | class | GeodeGolemRace | — | 7 | 67 | [PrismKnightRace.java](com/github/dominickwd04/traddon/race/lithoid/PrismKnightRace.java) |
| `ShardlingRace` | class | DefaultRace | — | 7 | 62 | [ShardlingRace.java](com/github/dominickwd04/traddon/race/lithoid/ShardlingRace.java) |
| `CrystalDustCloud` | class | AreaCloud | — | 5 | 75 | [CrystalDustCloud.java](com/github/dominickwd04/traddon/entity/magic/field/cloud/CrystalDustCloud.java) |
| `ExampleSkill` | class | Skill | — | 6 | 37 | [ExampleSkill.java](com/github/dominickwd04/traddon/ability/skill/ExampleSkill.java) |
| `CrystalDustSkill` | class | Skill | — | 6 | 72 | [CrystalDustSkill.java](com/github/dominickwd04/traddon/ability/skill/intrinsic/CrystalDustSkill.java) |
| `RefractiveShellSkill` | class | ResistSkill | — | 9 | 96 | [RefractiveShellSkill.java](com/github/dominickwd04/traddon/ability/skill/intrinsic/RefractiveShellSkill.java) |
| `SilentiumSkill` | class | ResistSkill | — | 6 | 67 | [SilentiumSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/SilentiumSkill.java) |
| `SpatialSamuraiSkill` | class | Skill | — | 9 | 117 | [SpatialSamuraiSkill.java](com/github/dominickwd04/traddon/ability/skill/unique/SpatialSamuraiSkill.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_traddon_common_656dd314cc114e91936432e81c19e003_59dbd9276cfdfa5d73cf8067f4a1eb84dafc459a794754b2a74a889c4c244bedtraddoncommon201devjar/PlatformMethods.java) |
| `TrAddon` | class | — | — | 1 | 23 | [TrAddon.java](com/github/dominickwd04/traddon/TrAddon.java) |
| `TrAddonGeneral` | class | ManasConfig | — | 1 | 19 | [TrAddonGeneral.java](com/github/dominickwd04/traddon/config/TrAddonGeneral.java) |
| `TrAddonRaces` | class | — | — | 1 | 23 | [TrAddonRaces.java](com/github/dominickwd04/traddon/registry/race/TrAddonRaces.java) |
| `TrAddonIntrinsicSkills` | class | — | — | 1 | 21 | [TrAddonIntrinsicSkills.java](com/github/dominickwd04/traddon/registry/skill/TrAddonIntrinsicSkills.java) |
| `TrAddonUniqueSkills` | class | — | — | 1 | 21 | [TrAddonUniqueSkills.java](com/github/dominickwd04/traddon/registry/skill/TrAddonUniqueSkills.java) |
| `TrAddonMobEffects` | class | — | — | 2 | 31 | [TrAddonMobEffects.java](com/github/dominickwd04/traddon/registry/effect/TrAddonMobEffects.java) |
| `DespairEffect` | class | TensuraMobEffect | — | 2 | 59 | [DespairEffect.java](com/github/dominickwd04/traddon/effect/DespairEffect.java) |
| `PostMortemSurgeEffect` | class | TensuraMobEffect | — | 0 | 22 | [PostMortemSurgeEffect.java](com/github/dominickwd04/traddon/effect/PostMortemSurgeEffect.java) |
| `AtomicAnnihilationEffect` | class | TensuraMobEffect | — | 0 | 18 | [AtomicAnnihilationEffect.java](com/github/dominickwd04/traddon/effect/AtomicAnnihilationEffect.java) |
| `PhotonBladeEffect` | class | TensuraMobEffect | — | 1 | 68 | [PhotonBladeEffect.java](com/github/dominickwd04/traddon/effect/PhotonBladeEffect.java) |
