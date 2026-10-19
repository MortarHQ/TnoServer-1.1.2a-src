# TensuraKumodesu（`tensura_kumodesu`）

> jar：`[转生附属]tensura_kumodesu.jar` ｜ 版本：`2.0.3-1.21.1-HOTFIX2` ｜ 作者：?
> 规模：**145 个类 / 40 个包 / 12798 行** ｜ 事件处理类 3 ｜ Mixin 10 ｜ 注册/管理类 4

**依赖**：`neoforge`、`minecraft`、`architectury`、`manascore_attribute`、`manascore_command`、`manascore_config`、`manascore_inventory`、`manascore_keybind`、`manascore_network`、`manascore_race`、`manascore_skill`、`manascore_storage`、`geckolib`、`smartbrainlib`、`terrablender`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.shin.tensura_kumodesu.entity.taratect` | 17 | `SmallLesserTaratectEntity`、`SmallTaratectEntity`、`QueenTaratectEntity` |
| `com.shin.tensura_kumodesu.entity.renderer` | 17 | `ArchTaratectRenderer`、`QueenTaratectRenderer`、`LesserPoisonTaratectRenderer` |
| `com.shin.tensura_kumodesu.entity.model.geo` | 17 | `ArchTaratectModel`、`QueenTaratectModel`、`EdeSaineTaratectModel` |
| `com.shin.tensura_kumodesu.mixin` | 10 | `LivingEntityRendererMixin`、`PlayerLikeRendererMixin`、`EntityMixin` |
| `com.shin.tensura_kumodesu.ability.skill.intrinsic` | 7 | `DivineMagicReleaseSkill`、`SpiderMovementSkill`、`TabooSkill` |
| `com.shin.tensura_kumodesu.races.monster` | 6 | `QueenTaratectRace`、`TaratectRace`、`GreatTaratectRace` |
| `com.shin.tensura_kumodesu.ability.skill.unique` | 6 | `DeterminationSkill`、`ResolveSkill`、`PerseveranceSkill` |
| `com.shin.tensura_kumodesu.effects` | 6 | `ResolveEffect`、`DeadlyPoisonEffect`、`DeadlySpiderPoisonEffect` |
| `com.shin.tensura_kumodesu.races.spec` | 5 | `ZanaHorowaRace`、`ZoaEleRace`、`ArachneRace` |
| `com.shin.tensura_kumodesu.races.ogre` | 5 | `OgreGeneralRace`、`WrathOniRace`、`OgreShamanRace` |
| `com.shin.tensura_kumodesu.registry.skill` | 5 | `KumoExtraSkills`、`KumoResistanceSkills`、`KumoUniqueSkills` |
| `com.shin.tensura_kumodesu.races.poison` | 4 | `PoisonTaratectRace`、`LesserTaratectRace`、`LesserPoisonTaratectRace` |
| `com.shin.tensura_kumodesu.neoforge` | 3 | `KumodesuModelsRegistry`、`KumodesuClientLayers`、`TensuraKumodesuNeoForge` |
| `com.shin.tensura_kumodesu.config.skill` | 3 | `KumodesuExtraSkillConfig`、`KumodesuIntrinsicSkillConfig`、`KumodesuUniqueSkillConfig` |
| `com.shin.tensura_kumodesu.config.race` | 2 | `TaratectConfig`、`OgreConfig` |
| `com.shin.tensura_kumodesu.neoforge.data` | 2 | `KumodesuRegistryProvider`、`TensuraKumodesuLanguageProvider` |
| `com.shin.tensura_kumodesu.registry` | 2 | `KumodesuModelsRegistry`、`KumodesuRegistry` |
| `com.shin.tensura_kumodesu.config` | 2 | `KumodesuGeneralConfig`、`KumodesuConfig` |
| `com.shin.tensura_kumodesu.races.other` | 2 | `OrthocadinahtRace`、`SmallLesserTaratectRace` |
| `com.shin.tensura_kumodesu.entity.model` | 2 | `WrathOni`、`CustomTensuraModelFormat` |
| `com.shin.tensura_kumodesu.damage` | 2 | `KumodesuDamageTypes`、`KumodesuDamageHelper` |
| `com.shin.tensura_kumodesu.client.renderer.layers` | 2 | `OniHornLayer`、`TaratectLayer` |
| `com.shin.tensura_kumodesu.blocks` | 1 | `TaratectEggBlock` |
| `com.shin.tensura_kumodesu.client.renderer` | 1 | `KumodesuEntityRenderer` |
| `com.shin.tensura_kumodesu.helper` | 1 | `TensuraKumodesuLanguage` |
| `com.shin.tensura_kumodesu.data.existence` | 1 | `KumodesuEntityExistenceData` |
| `com.shin.tensura_kumodesu.registry.items` | 1 | `KumodesuItems` |
| `com.shin.tensura_kumodesu.registry.blocks` | 1 | `KumodesuBlocks` |
| `com.shin.tensura_kumodesu.client` | 1 | `KumodesuClient` |
| `com.shin.tensura_kumodesu.wrapper` | 1 | `PreviewRacesWrapper` |
| `com.shin.tensura_kumodesu.registry.entities` | 1 | `KumodesuEntities` |
| `architectury_inject_tensura_kumodesu_common_544bda9e1a9043f3b9ebd2e5a5bd598b_804550890fa8ea7f71cbbbe3ef38175d5d7104f0d1ad3904f8d47c95a0f66708tensura_kumodesucommon2031211HOTFIX2devjar` | 1 | `PlatformMethods` |
| `com.shin.tensura_kumodesu` | 1 | `TensuraKumodesu` |
| `com.shin.tensura_kumodesu.ability.skill.extra` | 1 | `DeadlyPoisonSkill` |
| `com.shin.tensura_kumodesu.enums` | 1 | `TaratectTextureEnum` |
| `com.shin.tensura_kumodesu.bridge` | 1 | `KumodesuAttributeRegister` |
| `com.shin.tensura_kumodesu.bridge.neoforge` | 1 | `KumodesuAttributeRegisterImpl` |
| `com.shin.tensura_kumodesu.registry.race` | 1 | `KumodesuRaces` |
| `com.shin.tensura_kumodesu.registry.effects` | 1 | `KumodesuEffects` |
| `com.shin.tensura_kumodesu.registry.attributes` | 1 | `KumodesuAttributes` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `KumodesuModelsRegistry` | class | — | EventBusSubscriber | 1 | 16 | [KumodesuModelsRegistry.java](com/shin/tensura_kumodesu/neoforge/KumodesuModelsRegistry.java) |
| `KumodesuClientLayers` | class | — | EventBusSubscriber | 1 | 14 | [KumodesuClientLayers.java](com/shin/tensura_kumodesu/neoforge/KumodesuClientLayers.java) |
| `TaratectConfig` | class | ManasConfig | — | 296 | 1883 | [TaratectConfig.java](com/shin/tensura_kumodesu/config/race/TaratectConfig.java) |
| `TensuraKumodesuNeoForge` | class | — | Mod | 4 | 105 | [TensuraKumodesuNeoForge.java](com/shin/tensura_kumodesu/neoforge/TensuraKumodesuNeoForge.java) |
| `OgreConfig` | class | ManasConfig | — | 61 | 405 | [OgreConfig.java](com/shin/tensura_kumodesu/config/race/OgreConfig.java) |
| `LivingEntityRendererMixin` | class | — | Mixin | 0 | 51 | [LivingEntityRendererMixin.java](com/shin/tensura_kumodesu/mixin/LivingEntityRendererMixin.java) |
| `SmallLesserTaratectEntity` | class | Monster | — | 32 | 258 | [SmallLesserTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/SmallLesserTaratectEntity.java) |
| `PlayerLikeRendererMixin` | class | — | Mixin | 0 | 46 | [PlayerLikeRendererMixin.java](com/shin/tensura_kumodesu/mixin/PlayerLikeRendererMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 27 | [EntityMixin.java](com/shin/tensura_kumodesu/mixin/EntityMixin.java) |
| `CloneEntityMixin` | class | — | Mixin | 0 | 9 | [CloneEntityMixin.java](com/shin/tensura_kumodesu/mixin/CloneEntityMixin.java) |
| `ItemInHandRenderMixin` | class | — | Mixin | 1 | 61 | [ItemInHandRenderMixin.java](com/shin/tensura_kumodesu/mixin/ItemInHandRenderMixin.java) |
| `ReincarnationScreenAccessor` | interface | — | Mixin | 0 | 13 | [ReincarnationScreenAccessor.java](com/shin/tensura_kumodesu/mixin/ReincarnationScreenAccessor.java) |
| `SmallTaratectEntity` | class | SmallLesserTaratectEntity | — | 23 | 191 | [SmallTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/SmallTaratectEntity.java) |
| `KumodesuRegistryProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 19 | [KumodesuRegistryProvider.java](com/shin/tensura_kumodesu/neoforge/data/KumodesuRegistryProvider.java) |
| `RaceAPIMixin` | class | — | Mixin | 0 | 37 | [RaceAPIMixin.java](com/shin/tensura_kumodesu/mixin/RaceAPIMixin.java) |
| `PlayerRenderMixin` | class | LivingEntityRenderer | Mixin | 2 | 63 | [PlayerRenderMixin.java](com/shin/tensura_kumodesu/mixin/PlayerRenderMixin.java) |
| `PlayerMixin` | class | LivingEntity | Mixin | 1 | 39 | [PlayerMixin.java](com/shin/tensura_kumodesu/mixin/PlayerMixin.java) |
| `RenderArmourMixin` | class | — | Mixin | 2 | 71 | [RenderArmourMixin.java](com/shin/tensura_kumodesu/mixin/RenderArmourMixin.java) |
| `KumodesuModelsRegistry` | class | — | — | 2 | 20 | [KumodesuModelsRegistry.java](com/shin/tensura_kumodesu/registry/KumodesuModelsRegistry.java) |
| `KumodesuRegistry` | class | — | — | 1 | 24 | [KumodesuRegistry.java](com/shin/tensura_kumodesu/registry/KumodesuRegistry.java) |
| `QueenTaratectEntity` | class | ArchTaratectEntity | — | 19 | 179 | [QueenTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/QueenTaratectEntity.java) |
| `SmallPoisonTaratectEntity` | class | SmallTaratectEntity | — | 19 | 179 | [SmallPoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/SmallPoisonTaratectEntity.java) |
| `LesserPoisonTaratectEntity` | class | LesserTaratectEntity | — | 19 | 179 | [LesserPoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/LesserPoisonTaratectEntity.java) |
| `ArchTaratectEntity` | class | GreaterTaratectEntity | — | 19 | 179 | [ArchTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ArchTaratectEntity.java) |
| `OrthocadinahtTaratectEntity` | class | PoisonTaratectEntity | — | 19 | 179 | [OrthocadinahtTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/OrthocadinahtTaratectEntity.java) |
| `LesserTaratectEntity` | class | SmallLesserTaratectEntity | — | 19 | 179 | [LesserTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/LesserTaratectEntity.java) |
| `ZoaEleTaratectEntity` | class | SmallPoisonTaratectEntity | — | 19 | 178 | [ZoaEleTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ZoaEleTaratectEntity.java) |
| `GreatTaratectEntity` | class | TaratectEntity | — | 19 | 179 | [GreatTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/GreatTaratectEntity.java) |
| `EdeSaineTaratectEntity` | class | ZoaEleTaratectEntity | — | 19 | 178 | [EdeSaineTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/EdeSaineTaratectEntity.java) |
| `GreatPoisonTaratectEntity` | class | PoisonTaratectEntity | — | 19 | 179 | [GreatPoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/GreatPoisonTaratectEntity.java) |
| `ZanaHorowaTaratectEntity` | class | EdeSaineTaratectEntity | — | 19 | 178 | [ZanaHorowaTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ZanaHorowaTaratectEntity.java) |
| `TaratectEntity` | class | SmallTaratectEntity | — | 19 | 178 | [TaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/TaratectEntity.java) |
| `GreaterTaratectEntity` | class | GreatTaratectEntity | — | 19 | 179 | [GreaterTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/GreaterTaratectEntity.java) |
| `ArachneTaratectEntity` | class | ZanaHorowaTaratectEntity | — | 19 | 179 | [ArachneTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ArachneTaratectEntity.java) |
| `PoisonTaratectEntity` | class | LesserPoisonTaratectEntity | — | 19 | 179 | [PoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/PoisonTaratectEntity.java) |
| `TaratectEggBlock` | class | Block | — | 16 | 171 | [TaratectEggBlock.java](com/shin/tensura_kumodesu/blocks/TaratectEggBlock.java) |
| `KumodesuGeneralConfig` | class | ManasConfig | — | 1 | 17 | [KumodesuGeneralConfig.java](com/shin/tensura_kumodesu/config/KumodesuGeneralConfig.java) |
| `KumodesuConfig` | class | — | — | 3 | 50 | [KumodesuConfig.java](com/shin/tensura_kumodesu/config/KumodesuConfig.java) |
| `KumodesuExtraSkillConfig` | class | ManasConfig | — | 1 | 21 | [KumodesuExtraSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuExtraSkillConfig.java) |
| `KumodesuIntrinsicSkillConfig` | class | ManasConfig | — | 1 | 41 | [KumodesuIntrinsicSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuIntrinsicSkillConfig.java) |
| `KumodesuUniqueSkillConfig` | class | ManasConfig | — | 1 | 53 | [KumodesuUniqueSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuUniqueSkillConfig.java) |
| `KumodesuEntityRenderer` | class | — | — | 2 | 47 | [KumodesuEntityRenderer.java](com/shin/tensura_kumodesu/client/renderer/KumodesuEntityRenderer.java) |
| `TensuraKumodesuLanguage` | class | — | — | 17 | 143 | [TensuraKumodesuLanguage.java](com/shin/tensura_kumodesu/helper/TensuraKumodesuLanguage.java) |
| `KumodesuEntityExistenceData` | class | — | — | 3 | 158 | [KumodesuEntityExistenceData.java](com/shin/tensura_kumodesu/data/existence/KumodesuEntityExistenceData.java) |
| `QueenTaratectRace` | class | ArchTaratectRace | — | 16 | 134 | [QueenTaratectRace.java](com/shin/tensura_kumodesu/races/monster/QueenTaratectRace.java) |
| `OrthocadinahtRace` | class | PoisonTaratectRace | — | 15 | 143 | [OrthocadinahtRace.java](com/shin/tensura_kumodesu/races/other/OrthocadinahtRace.java) |
| `ArchTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [ArchTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/ArchTaratectRenderer.java) |
| `QueenTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [QueenTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/QueenTaratectRenderer.java) |
| `LesserPoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [LesserPoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/LesserPoisonTaratectRenderer.java) |
| `GreaterTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [GreaterTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/GreaterTaratectRenderer.java) |
| `GreatPoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [GreatPoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/GreatPoisonTaratectRenderer.java) |
| `ZoaEleTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [ZoaEleTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/ZoaEleTaratectRenderer.java) |
| `LesserTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [LesserTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/LesserTaratectRenderer.java) |
| `SmallTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [SmallTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/SmallTaratectRenderer.java) |
| `OrthocadinahtTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [OrthocadinahtTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/OrthocadinahtTaratectRenderer.java) |
| `PoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [PoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/PoisonTaratectRenderer.java) |
| `SmallPoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [SmallPoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/SmallPoisonTaratectRenderer.java) |
| `SmallLesserTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [SmallLesserTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/SmallLesserTaratectRenderer.java) |
| `EdeSaineTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [EdeSaineTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/EdeSaineTaratectRenderer.java) |
| `GreatTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [GreatTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/GreatTaratectRenderer.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `TensuraKumodesuNeoForge` | 2 | `com.shin.tensura_kumodesu.neoforge` | [TensuraKumodesuNeoForge.java](com/shin/tensura_kumodesu/neoforge/TensuraKumodesuNeoForge.java) |
| `KumodesuModelsRegistry` | 1 | `com.shin.tensura_kumodesu.neoforge` | [KumodesuModelsRegistry.java](com/shin/tensura_kumodesu/neoforge/KumodesuModelsRegistry.java) |
| `KumodesuClientLayers` | 1 | `com.shin.tensura_kumodesu.neoforge` | [KumodesuClientLayers.java](com/shin/tensura_kumodesu/neoforge/KumodesuClientLayers.java) |

## Mixin（10 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityRendererMixin` | `LivingEntityRenderer` | [LivingEntityRendererMixin.java](com/shin/tensura_kumodesu/mixin/LivingEntityRendererMixin.java) |
| `PlayerLikeRendererMixin` | `PlayerLikeRenderer` | [PlayerLikeRendererMixin.java](com/shin/tensura_kumodesu/mixin/PlayerLikeRendererMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/shin/tensura_kumodesu/mixin/EntityMixin.java) |
| `CloneEntityMixin` | `CloneEntity` | [CloneEntityMixin.java](com/shin/tensura_kumodesu/mixin/CloneEntityMixin.java) |
| `ItemInHandRenderMixin` | `ItemInHandRender` | [ItemInHandRenderMixin.java](com/shin/tensura_kumodesu/mixin/ItemInHandRenderMixin.java) |
| `ReincarnationScreenAccessor` | `ReincarnationScreenAccessor` | [ReincarnationScreenAccessor.java](com/shin/tensura_kumodesu/mixin/ReincarnationScreenAccessor.java) |
| `RaceAPIMixin` | `RaceAPI` | [RaceAPIMixin.java](com/shin/tensura_kumodesu/mixin/RaceAPIMixin.java) |
| `PlayerRenderMixin` | `PlayerRender` | [PlayerRenderMixin.java](com/shin/tensura_kumodesu/mixin/PlayerRenderMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](com/shin/tensura_kumodesu/mixin/PlayerMixin.java) |
| `RenderArmourMixin` | `RenderArmour` | [RenderArmourMixin.java](com/shin/tensura_kumodesu/mixin/RenderArmourMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TaratectConfig` | — | [TaratectConfig.java](com/shin/tensura_kumodesu/config/race/TaratectConfig.java) |
| `OgreConfig` | — | [OgreConfig.java](com/shin/tensura_kumodesu/config/race/OgreConfig.java) |
| `KumodesuGeneralConfig` | — | [KumodesuGeneralConfig.java](com/shin/tensura_kumodesu/config/KumodesuGeneralConfig.java) |
| `KumodesuConfig` | — | [KumodesuConfig.java](com/shin/tensura_kumodesu/config/KumodesuConfig.java) |
| `KumodesuExtraSkillConfig` | — | [KumodesuExtraSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuExtraSkillConfig.java) |
| `KumodesuIntrinsicSkillConfig` | — | [KumodesuIntrinsicSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuIntrinsicSkillConfig.java) |
| `KumodesuUniqueSkillConfig` | — | [KumodesuUniqueSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuUniqueSkillConfig.java) |

## 全部类（145）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `KumodesuModelsRegistry` | class | — | EventBusSubscriber | 1 | 16 | [KumodesuModelsRegistry.java](com/shin/tensura_kumodesu/neoforge/KumodesuModelsRegistry.java) |
| `KumodesuClientLayers` | class | — | EventBusSubscriber | 1 | 14 | [KumodesuClientLayers.java](com/shin/tensura_kumodesu/neoforge/KumodesuClientLayers.java) |
| `TaratectConfig` | class | ManasConfig | — | 296 | 1883 | [TaratectConfig.java](com/shin/tensura_kumodesu/config/race/TaratectConfig.java) |
| `TensuraKumodesuNeoForge` | class | — | Mod | 4 | 105 | [TensuraKumodesuNeoForge.java](com/shin/tensura_kumodesu/neoforge/TensuraKumodesuNeoForge.java) |
| `OgreConfig` | class | ManasConfig | — | 61 | 405 | [OgreConfig.java](com/shin/tensura_kumodesu/config/race/OgreConfig.java) |
| `LivingEntityRendererMixin` | class | — | Mixin | 0 | 51 | [LivingEntityRendererMixin.java](com/shin/tensura_kumodesu/mixin/LivingEntityRendererMixin.java) |
| `SmallLesserTaratectEntity` | class | Monster | — | 32 | 258 | [SmallLesserTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/SmallLesserTaratectEntity.java) |
| `PlayerLikeRendererMixin` | class | — | Mixin | 0 | 46 | [PlayerLikeRendererMixin.java](com/shin/tensura_kumodesu/mixin/PlayerLikeRendererMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 27 | [EntityMixin.java](com/shin/tensura_kumodesu/mixin/EntityMixin.java) |
| `CloneEntityMixin` | class | — | Mixin | 0 | 9 | [CloneEntityMixin.java](com/shin/tensura_kumodesu/mixin/CloneEntityMixin.java) |
| `ItemInHandRenderMixin` | class | — | Mixin | 1 | 61 | [ItemInHandRenderMixin.java](com/shin/tensura_kumodesu/mixin/ItemInHandRenderMixin.java) |
| `ReincarnationScreenAccessor` | interface | — | Mixin | 0 | 13 | [ReincarnationScreenAccessor.java](com/shin/tensura_kumodesu/mixin/ReincarnationScreenAccessor.java) |
| `SmallTaratectEntity` | class | SmallLesserTaratectEntity | — | 23 | 191 | [SmallTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/SmallTaratectEntity.java) |
| `KumodesuRegistryProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 19 | [KumodesuRegistryProvider.java](com/shin/tensura_kumodesu/neoforge/data/KumodesuRegistryProvider.java) |
| `RaceAPIMixin` | class | — | Mixin | 0 | 37 | [RaceAPIMixin.java](com/shin/tensura_kumodesu/mixin/RaceAPIMixin.java) |
| `PlayerRenderMixin` | class | LivingEntityRenderer | Mixin | 2 | 63 | [PlayerRenderMixin.java](com/shin/tensura_kumodesu/mixin/PlayerRenderMixin.java) |
| `PlayerMixin` | class | LivingEntity | Mixin | 1 | 39 | [PlayerMixin.java](com/shin/tensura_kumodesu/mixin/PlayerMixin.java) |
| `RenderArmourMixin` | class | — | Mixin | 2 | 71 | [RenderArmourMixin.java](com/shin/tensura_kumodesu/mixin/RenderArmourMixin.java) |
| `KumodesuModelsRegistry` | class | — | — | 2 | 20 | [KumodesuModelsRegistry.java](com/shin/tensura_kumodesu/registry/KumodesuModelsRegistry.java) |
| `KumodesuRegistry` | class | — | — | 1 | 24 | [KumodesuRegistry.java](com/shin/tensura_kumodesu/registry/KumodesuRegistry.java) |
| `QueenTaratectEntity` | class | ArchTaratectEntity | — | 19 | 179 | [QueenTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/QueenTaratectEntity.java) |
| `SmallPoisonTaratectEntity` | class | SmallTaratectEntity | — | 19 | 179 | [SmallPoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/SmallPoisonTaratectEntity.java) |
| `LesserPoisonTaratectEntity` | class | LesserTaratectEntity | — | 19 | 179 | [LesserPoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/LesserPoisonTaratectEntity.java) |
| `ArchTaratectEntity` | class | GreaterTaratectEntity | — | 19 | 179 | [ArchTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ArchTaratectEntity.java) |
| `OrthocadinahtTaratectEntity` | class | PoisonTaratectEntity | — | 19 | 179 | [OrthocadinahtTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/OrthocadinahtTaratectEntity.java) |
| `LesserTaratectEntity` | class | SmallLesserTaratectEntity | — | 19 | 179 | [LesserTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/LesserTaratectEntity.java) |
| `ZoaEleTaratectEntity` | class | SmallPoisonTaratectEntity | — | 19 | 178 | [ZoaEleTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ZoaEleTaratectEntity.java) |
| `GreatTaratectEntity` | class | TaratectEntity | — | 19 | 179 | [GreatTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/GreatTaratectEntity.java) |
| `EdeSaineTaratectEntity` | class | ZoaEleTaratectEntity | — | 19 | 178 | [EdeSaineTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/EdeSaineTaratectEntity.java) |
| `GreatPoisonTaratectEntity` | class | PoisonTaratectEntity | — | 19 | 179 | [GreatPoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/GreatPoisonTaratectEntity.java) |
| `ZanaHorowaTaratectEntity` | class | EdeSaineTaratectEntity | — | 19 | 178 | [ZanaHorowaTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ZanaHorowaTaratectEntity.java) |
| `TaratectEntity` | class | SmallTaratectEntity | — | 19 | 178 | [TaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/TaratectEntity.java) |
| `GreaterTaratectEntity` | class | GreatTaratectEntity | — | 19 | 179 | [GreaterTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/GreaterTaratectEntity.java) |
| `ArachneTaratectEntity` | class | ZanaHorowaTaratectEntity | — | 19 | 179 | [ArachneTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/ArachneTaratectEntity.java) |
| `PoisonTaratectEntity` | class | LesserPoisonTaratectEntity | — | 19 | 179 | [PoisonTaratectEntity.java](com/shin/tensura_kumodesu/entity/taratect/PoisonTaratectEntity.java) |
| `TaratectEggBlock` | class | Block | — | 16 | 171 | [TaratectEggBlock.java](com/shin/tensura_kumodesu/blocks/TaratectEggBlock.java) |
| `KumodesuGeneralConfig` | class | ManasConfig | — | 1 | 17 | [KumodesuGeneralConfig.java](com/shin/tensura_kumodesu/config/KumodesuGeneralConfig.java) |
| `KumodesuConfig` | class | — | — | 3 | 50 | [KumodesuConfig.java](com/shin/tensura_kumodesu/config/KumodesuConfig.java) |
| `KumodesuExtraSkillConfig` | class | ManasConfig | — | 1 | 21 | [KumodesuExtraSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuExtraSkillConfig.java) |
| `KumodesuIntrinsicSkillConfig` | class | ManasConfig | — | 1 | 41 | [KumodesuIntrinsicSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuIntrinsicSkillConfig.java) |
| `KumodesuUniqueSkillConfig` | class | ManasConfig | — | 1 | 53 | [KumodesuUniqueSkillConfig.java](com/shin/tensura_kumodesu/config/skill/KumodesuUniqueSkillConfig.java) |
| `KumodesuEntityRenderer` | class | — | — | 2 | 47 | [KumodesuEntityRenderer.java](com/shin/tensura_kumodesu/client/renderer/KumodesuEntityRenderer.java) |
| `TensuraKumodesuLanguage` | class | — | — | 17 | 143 | [TensuraKumodesuLanguage.java](com/shin/tensura_kumodesu/helper/TensuraKumodesuLanguage.java) |
| `KumodesuEntityExistenceData` | class | — | — | 3 | 158 | [KumodesuEntityExistenceData.java](com/shin/tensura_kumodesu/data/existence/KumodesuEntityExistenceData.java) |
| `QueenTaratectRace` | class | ArchTaratectRace | — | 16 | 134 | [QueenTaratectRace.java](com/shin/tensura_kumodesu/races/monster/QueenTaratectRace.java) |
| `OrthocadinahtRace` | class | PoisonTaratectRace | — | 15 | 143 | [OrthocadinahtRace.java](com/shin/tensura_kumodesu/races/other/OrthocadinahtRace.java) |
| `ArchTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [ArchTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/ArchTaratectRenderer.java) |
| `QueenTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [QueenTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/QueenTaratectRenderer.java) |
| `LesserPoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [LesserPoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/LesserPoisonTaratectRenderer.java) |
| `GreaterTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [GreaterTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/GreaterTaratectRenderer.java) |
| `GreatPoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [GreatPoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/GreatPoisonTaratectRenderer.java) |
| `ZoaEleTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [ZoaEleTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/ZoaEleTaratectRenderer.java) |
| `LesserTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [LesserTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/LesserTaratectRenderer.java) |
| `SmallTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [SmallTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/SmallTaratectRenderer.java) |
| `OrthocadinahtTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [OrthocadinahtTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/OrthocadinahtTaratectRenderer.java) |
| `PoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [PoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/PoisonTaratectRenderer.java) |
| `SmallPoisonTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [SmallPoisonTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/SmallPoisonTaratectRenderer.java) |
| `SmallLesserTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [SmallLesserTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/SmallLesserTaratectRenderer.java) |
| `EdeSaineTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [EdeSaineTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/EdeSaineTaratectRenderer.java) |
| `GreatTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [GreatTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/GreatTaratectRenderer.java) |
| `ZanaHorowaTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [ZanaHorowaTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/ZanaHorowaTaratectRenderer.java) |
| `TaratectRenderer` | class | GeoEntityRenderer | — | 2 | 66 | [TaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/TaratectRenderer.java) |
| `ArachneTaratectRenderer` | class | GeoEntityRenderer | — | 2 | 67 | [ArachneTaratectRenderer.java](com/shin/tensura_kumodesu/entity/renderer/ArachneTaratectRenderer.java) |
| `KumodesuItems` | class | — | — | 1 | 22 | [KumodesuItems.java](com/shin/tensura_kumodesu/registry/items/KumodesuItems.java) |
| `KumodesuBlocks` | class | — | — | 1 | 22 | [KumodesuBlocks.java](com/shin/tensura_kumodesu/registry/blocks/KumodesuBlocks.java) |
| `PoisonTaratectRace` | class | SmallPoisonTaratectRace | — | 12 | 115 | [PoisonTaratectRace.java](com/shin/tensura_kumodesu/races/poison/PoisonTaratectRace.java) |
| `LesserTaratectRace` | class | SmallLesserTaratectRace | — | 10 | 84 | [LesserTaratectRace.java](com/shin/tensura_kumodesu/races/poison/LesserTaratectRace.java) |
| `LesserPoisonTaratectRace` | class | LesserTaratectRace | — | 12 | 101 | [LesserPoisonTaratectRace.java](com/shin/tensura_kumodesu/races/poison/LesserPoisonTaratectRace.java) |
| `GreatPoisonTaratectRace` | class | PoisonTaratectRace | — | 12 | 103 | [GreatPoisonTaratectRace.java](com/shin/tensura_kumodesu/races/poison/GreatPoisonTaratectRace.java) |
| `TaratectRace` | class | SmallTaratectRace | — | 12 | 98 | [TaratectRace.java](com/shin/tensura_kumodesu/races/monster/TaratectRace.java) |
| `GreatTaratectRace` | class | TaratectRace | — | 12 | 98 | [GreatTaratectRace.java](com/shin/tensura_kumodesu/races/monster/GreatTaratectRace.java) |
| `GreaterTaratectRace` | class | GreatTaratectRace | — | 12 | 101 | [GreaterTaratectRace.java](com/shin/tensura_kumodesu/races/monster/GreaterTaratectRace.java) |
| `SmallTaratectRace` | class | SmallLesserTaratectRace | — | 12 | 93 | [SmallTaratectRace.java](com/shin/tensura_kumodesu/races/monster/SmallTaratectRace.java) |
| `ArchTaratectRace` | class | SmallTaratectRace | — | 14 | 109 | [ArchTaratectRace.java](com/shin/tensura_kumodesu/races/monster/ArchTaratectRace.java) |
| `ZanaHorowaRace` | class | ZoaEleRace | — | 14 | 128 | [ZanaHorowaRace.java](com/shin/tensura_kumodesu/races/spec/ZanaHorowaRace.java) |
| `ZoaEleRace` | class | SmallTaratectRace | — | 13 | 123 | [ZoaEleRace.java](com/shin/tensura_kumodesu/races/spec/ZoaEleRace.java) |
| `ArachneRace` | class | ZanaHorowaRace | — | 13 | 113 | [ArachneRace.java](com/shin/tensura_kumodesu/races/spec/ArachneRace.java) |
| `SmallPoisonTaratectRace` | class | SmallTaratectRace | — | 12 | 100 | [SmallPoisonTaratectRace.java](com/shin/tensura_kumodesu/races/spec/SmallPoisonTaratectRace.java) |
| `EdeSaineRace` | class | ZoaEleRace | — | 12 | 116 | [EdeSaineRace.java](com/shin/tensura_kumodesu/races/spec/EdeSaineRace.java) |
| `KumodesuClient` | class | — | — | 2 | 67 | [KumodesuClient.java](com/shin/tensura_kumodesu/client/KumodesuClient.java) |
| `WrathOni` | class | HierarchicalModel | — | 5 | 163 | [WrathOni.java](com/shin/tensura_kumodesu/entity/model/WrathOni.java) |
| `PreviewRacesWrapper` | class | Races | — | 7 | 46 | [PreviewRacesWrapper.java](com/shin/tensura_kumodesu/wrapper/PreviewRacesWrapper.java) |
| `DivineMagicReleaseSkill` | class | Skill | — | 8 | 106 | [DivineMagicReleaseSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/DivineMagicReleaseSkill.java) |
| `SpiderMovementSkill` | class | Skill | — | 5 | 36 | [SpiderMovementSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/SpiderMovementSkill.java) |
| `DeterminationSkill` | class | Skill | — | 6 | 56 | [DeterminationSkill.java](com/shin/tensura_kumodesu/ability/skill/unique/DeterminationSkill.java) |
| `ResolveSkill` | class | Skill | — | 6 | 56 | [ResolveSkill.java](com/shin/tensura_kumodesu/ability/skill/unique/ResolveSkill.java) |
| `PerseveranceSkill` | class | Skill | — | 7 | 72 | [PerseveranceSkill.java](com/shin/tensura_kumodesu/ability/skill/unique/PerseveranceSkill.java) |
| `KumodesuEntities` | class | — | — | 1 | 207 | [KumodesuEntities.java](com/shin/tensura_kumodesu/registry/entities/KumodesuEntities.java) |
| `SmallLesserTaratectRace` | class | DefaultRace | — | 8 | 86 | [SmallLesserTaratectRace.java](com/shin/tensura_kumodesu/races/other/SmallLesserTaratectRace.java) |
| `OgreGeneralRace` | class | HighOgreRace | — | 6 | 59 | [OgreGeneralRace.java](com/shin/tensura_kumodesu/races/ogre/OgreGeneralRace.java) |
| `WrathOniRace` | class | OgreKingRace | — | 6 | 62 | [WrathOniRace.java](com/shin/tensura_kumodesu/races/ogre/WrathOniRace.java) |
| `OgreShamanRace` | class | OgreRace | — | 9 | 82 | [OgreShamanRace.java](com/shin/tensura_kumodesu/races/ogre/OgreShamanRace.java) |
| `OgreKingRace` | class | HighOgreRace | — | 7 | 67 | [OgreKingRace.java](com/shin/tensura_kumodesu/races/ogre/OgreKingRace.java) |
| `HighOgreRace` | class | OgreShamanRace | — | 6 | 59 | [HighOgreRace.java](com/shin/tensura_kumodesu/races/ogre/HighOgreRace.java) |
| `KumodesuDamageTypes` | class | — | — | 5 | 37 | [KumodesuDamageTypes.java](com/shin/tensura_kumodesu/damage/KumodesuDamageTypes.java) |
| `KumodesuDamageHelper` | class | — | — | 2 | 23 | [KumodesuDamageHelper.java](com/shin/tensura_kumodesu/damage/KumodesuDamageHelper.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_kumodesu_common_544bda9e1a9043f3b9ebd2e5a5bd598b_804550890fa8ea7f71cbbbe3ef38175d5d7104f0d1ad3904f8d47c95a0f66708tensura_kumodesucommon2031211HOTFIX2devjar/PlatformMethods.java) |
| `TensuraKumodesu` | class | — | — | 3 | 33 | [TensuraKumodesu.java](com/shin/tensura_kumodesu/TensuraKumodesu.java) |
| `CustomTensuraModelFormat` | interface | — | — | 0 | 14 | [CustomTensuraModelFormat.java](com/shin/tensura_kumodesu/entity/model/CustomTensuraModelFormat.java) |
| `ArchTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [ArchTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/ArchTaratectModel.java) |
| `QueenTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [QueenTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/QueenTaratectModel.java) |
| `EdeSaineTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [EdeSaineTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/EdeSaineTaratectModel.java) |
| `GreaterTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [GreaterTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/GreaterTaratectModel.java) |
| `GreatTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [GreatTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/GreatTaratectModel.java) |
| `SmallPoisonTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [SmallPoisonTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/SmallPoisonTaratectModel.java) |
| `ArachneTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [ArachneTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/ArachneTaratectModel.java) |
| `LesserTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [LesserTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/LesserTaratectModel.java) |
| `OrthocadinahtTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [OrthocadinahtTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/OrthocadinahtTaratectModel.java) |
| `PoisonTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [PoisonTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/PoisonTaratectModel.java) |
| `SmallTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [SmallTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/SmallTaratectModel.java) |
| `ZanaHorowaTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [ZanaHorowaTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/ZanaHorowaTaratectModel.java) |
| `LesserPoisonTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [LesserPoisonTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/LesserPoisonTaratectModel.java) |
| `SmallLesserTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [SmallLesserTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/SmallLesserTaratectModel.java) |
| `ZoaEleTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [ZoaEleTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/ZoaEleTaratectModel.java) |
| `GreatPoisonTaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [GreatPoisonTaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/GreatPoisonTaratectModel.java) |
| `TaratectModel` | class | DefaultedEntityGeoModel | — | 2 | 35 | [TaratectModel.java](com/shin/tensura_kumodesu/entity/model/geo/TaratectModel.java) |
| `TensuraKumodesuLanguageProvider` | class | LanguageProvider | — | 1 | 16 | [TensuraKumodesuLanguageProvider.java](com/shin/tensura_kumodesu/neoforge/data/TensuraKumodesuLanguageProvider.java) |
| `ResolveEffect` | class | TensuraMobEffect | — | 0 | 22 | [ResolveEffect.java](com/shin/tensura_kumodesu/effects/ResolveEffect.java) |
| `DeadlyPoisonEffect` | class | TensuraMobEffect | — | 2 | 53 | [DeadlyPoisonEffect.java](com/shin/tensura_kumodesu/effects/DeadlyPoisonEffect.java) |
| `DeadlySpiderPoisonEffect` | class | TensuraMobEffect | — | 2 | 53 | [DeadlySpiderPoisonEffect.java](com/shin/tensura_kumodesu/effects/DeadlySpiderPoisonEffect.java) |
| `FatalSpiderPoisonEffect` | class | TensuraMobEffect | — | 2 | 57 | [FatalSpiderPoisonEffect.java](com/shin/tensura_kumodesu/effects/FatalSpiderPoisonEffect.java) |
| `DeterminationEffect` | class | TensuraMobEffect | — | 0 | 23 | [DeterminationEffect.java](com/shin/tensura_kumodesu/effects/DeterminationEffect.java) |
| `PerseveranceEffect` | class | TensuraMobEffect | — | 1 | 27 | [PerseveranceEffect.java](com/shin/tensura_kumodesu/effects/PerseveranceEffect.java) |
| `TabooSkill` | class | Skill | — | 1 | 16 | [TabooSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/TabooSkill.java) |
| `FatalSpiderPoisonSkill` | class | Skill | — | 4 | 94 | [FatalSpiderPoisonSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/FatalSpiderPoisonSkill.java) |
| `DeadlySpiderPoisonSkill` | class | Skill | — | 4 | 97 | [DeadlySpiderPoisonSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/DeadlySpiderPoisonSkill.java) |
| `EggLayingSkill` | class | Skill | — | 1 | 16 | [EggLayingSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/EggLayingSkill.java) |
| `HumanificationSkill` | class | Skill | — | 1 | 16 | [HumanificationSkill.java](com/shin/tensura_kumodesu/ability/skill/intrinsic/HumanificationSkill.java) |
| `WeaponCreationSkill` | class | Skill | — | 1 | 16 | [WeaponCreationSkill.java](com/shin/tensura_kumodesu/ability/skill/unique/WeaponCreationSkill.java) |
| `HeightOfOccultismSkill` | class | Skill | — | 1 | 16 | [HeightOfOccultismSkill.java](com/shin/tensura_kumodesu/ability/skill/unique/HeightOfOccultismSkill.java) |
| `WondrousWeaponCreationSkill` | class | Skill | — | 1 | 16 | [WondrousWeaponCreationSkill.java](com/shin/tensura_kumodesu/ability/skill/unique/WondrousWeaponCreationSkill.java) |
| `DeadlyPoisonSkill` | class | Skill | — | 4 | 92 | [DeadlyPoisonSkill.java](com/shin/tensura_kumodesu/ability/skill/extra/DeadlyPoisonSkill.java) |
| `TaratectTextureEnum` | enum | — | — | 3 | 46 | [TaratectTextureEnum.java](com/shin/tensura_kumodesu/enums/TaratectTextureEnum.java) |
| `KumodesuAttributeRegister` | class | — | — | 2 | 30 | [KumodesuAttributeRegister.java](com/shin/tensura_kumodesu/bridge/KumodesuAttributeRegister.java) |
| `KumodesuAttributeRegisterImpl` | class | — | — | 2 | 31 | [KumodesuAttributeRegisterImpl.java](com/shin/tensura_kumodesu/bridge/neoforge/KumodesuAttributeRegisterImpl.java) |
| `KumodesuRaces` | class | — | — | 2 | 62 | [KumodesuRaces.java](com/shin/tensura_kumodesu/registry/race/KumodesuRaces.java) |
| `KumodesuEffects` | class | — | — | 1 | 27 | [KumodesuEffects.java](com/shin/tensura_kumodesu/registry/effects/KumodesuEffects.java) |
| `KumoExtraSkills` | class | — | — | 2 | 20 | [KumoExtraSkills.java](com/shin/tensura_kumodesu/registry/skill/KumoExtraSkills.java) |
| `KumoResistanceSkills` | class | — | — | 2 | 17 | [KumoResistanceSkills.java](com/shin/tensura_kumodesu/registry/skill/KumoResistanceSkills.java) |
| `KumoUniqueSkills` | class | — | — | 2 | 32 | [KumoUniqueSkills.java](com/shin/tensura_kumodesu/registry/skill/KumoUniqueSkills.java) |
| `KumoIntrinsicSkills` | class | — | — | 2 | 32 | [KumoIntrinsicSkills.java](com/shin/tensura_kumodesu/registry/skill/KumoIntrinsicSkills.java) |
| `KumoCommonSkills` | class | — | — | 2 | 17 | [KumoCommonSkills.java](com/shin/tensura_kumodesu/registry/skill/KumoCommonSkills.java) |
| `KumodesuAttributes` | class | — | — | 1 | 23 | [KumodesuAttributes.java](com/shin/tensura_kumodesu/registry/attributes/KumodesuAttributes.java) |
| `OniHornLayer` | class | — | — | 1 | 83 | [OniHornLayer.java](com/shin/tensura_kumodesu/client/renderer/layers/OniHornLayer.java) |
| `TaratectLayer` | class | RenderLayer | — | 4 | 154 | [TaratectLayer.java](com/shin/tensura_kumodesu/client/renderer/layers/TaratectLayer.java) |
