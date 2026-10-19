# Mowzie's Mobs（`mowziesmobs`）

> jar：`[Mowzie的生物]mowziesmobs.jar` ｜ 版本：`1.8.2` ｜ 作者：BobMowzie, Wadoo, Vakypanda, Noonyez, pau101
> 规模：**450 个类 / 78 个包 / 71039 行** ｜ 事件处理类 11 ｜ Mixin 4 ｜ 注册/管理类 35

**依赖**：`neoforge`、`minecraft`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.bobmowzie.mowziesmobs.client.render.entity` | 31 | `FrozenRenderHandler`、`MowzieGeoEntityRenderer`、`RenderFissurePiece` |
| `com.bobmowzie.mowziesmobs.client.model.entity` | 27 | `ModelNaga`、`ModelElokosa`、`ModelFrostmaw` |
| `com.bobmowzie.mowziesmobs.server.item` | 25 | `ItemHandler`、`ItemUmvuthanaMask`、`MaterialHandler` |
| `com.bobmowzie.mowziesmobs.client.render.entity.layer` | 21 | `ItemLayer`、`ItemLayerSculptorStaff`、`GeckoPlayerItemInHandLayer` |
| `com.bobmowzie.mowziesmobs.server.ai` | 19 | `ElokosaLeapGoal`、`EntityAIAvoidEntity`、`MMEntityMoveHelper` |
| `com.bobmowzie.mowziesmobs.server.entity` | 16 | `EntityHandler`、`MowzieEntity`、`MowzieGeckoEntity` |
| `com.bobmowzie.mowziesmobs.server.ai.animation` | 15 | `AnimationFWNAttackAI`、`AnimationBlockAI`、`AnimationAI` |
| `com.bobmowzie.mowziesmobs.server.message` | 13 | `NetworkHandler`、`MessageJumpToAbilitySectionServerToClient`、`MessageSunblockEffect` |
| `com.bobmowzie.mowziesmobs.client.sound` | 13 | `SunblockSound`、`SculptorBossMusic`、`BossMusicSound` |
| `com.bobmowzie.mowziesmobs.server.entity.effects` | 11 | `EntityBlockSwapper`、`EntitySolarBeam`、`EntitySunstrike` |
| `com.bobmowzie.mowziesmobs.server.potion` | 11 | `EffectHandler`、`PotionTypeHandler`、`EffectSunblock` |
| `com.bobmowzie.mowziesmobs.client.particle` | 10 | `ParticleHandler`、`ParticleRibbon`、`ParticleOrb` |
| `com.bobmowzie.mowziesmobs.server.entity.umvuthana` | 10 | `EntityUmvuthi`、`EntityUmvuthana`、`EntityUmvuthanaMinion` |
| `com.bobmowzie.mowziesmobs.server.entity.effects.geomancy` | 10 | `EntityBoulderSculptor`、`EntityPillar`、`EntityBoulderProjectile` |
| `com.bobmowzie.mowziesmobs.client.model.tools` | 10 | `IntermittentAnimation`、`ControlledAnimation`、`RigUtils` |
| `com.bobmowzie.mowziesmobs.datagen` | 9 | `DataGenerators`、`RegistryDataGenerator`、`StructureHandler` |
| `com.bobmowzie.mowziesmobs.server.world.feature.structure` | 9 | `UmvuthanaGrovePieces`、`StructureTypeHandler`、`MowzieStructure` |
| `com.bobmowzie.mowziesmobs.server.inventory` | 9 | `ContainerHandler`、`InventoryUmvuthana`、`InventoryOneInput` |
| `com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy` | 8 | `TunnelingAbility`、`SpawnBoulderAbility`、`SpawnPillarAbility` |
| `com.bobmowzie.mowziesmobs.server.world.feature.structure.processor` | 8 | `ProcessorHandler`、`BlockSwapProcessor`、`BlockStateRandomizer` |
| `com.bobmowzie.mowziesmobs.server.ability` | 7 | `AbilityCommonEventHandler`、`AbilityClientEventHandler`、`PlayerAbility` |
| `com.ilexiconn.llibrary.client.model.tools` | 7 | `AdvancedModelRenderer`、`BasicModelRenderer`、`ControlledAnimation` |
| `com.bobmowzie.mowziesmobs.server.advancement` | 7 | `AdvancementHandler`、`GrottolKillSilkTouchTrigger`、`StealIceCrystalTrigger` |
| `com.bobmowzie.mowziesmobs.client.render.item` | 7 | `RenderUmvuthanaMaskItem`、`RenderSolVisageItem`、`RenderSolVisageArmor` |
| `com.bobmowzie.mowziesmobs.server.ability.abilities.player` | 6 | `IceBreathAbility`、`FireballAbility`、`SimpleAnimationAbility` |
| `com.bobmowzie.mowziesmobs.client` | 5 | `ClientEventHandler`、`ClientEventBusSubscriber`、`ClientLayerRegistry` |
| `com.bobmowzie.mowziesmobs.server.entity.elokosa` | 5 | `EntityElokosa`、`EntityElokosaFollower`、`EntityElokosaHowler` |
| `com.bobmowzie.mowziesmobs.server.block` | 5 | `RakedSandBlock`、`GongBlock`、`BlockHandler` |
| `com.ilexiconn.llibrary.server.util` | 5 | `WeakIdentityHashMap`、`ExceptionlessFunctions`、`Tuple3` |
| `com.bobmowzie.mowziesmobs.server.capability` | 5 | `PlayerData`、`FrozenData`、`DataHandler` |
| `com.bobmowzie.mowziesmobs.server.loot` | 5 | `LootTableHandler`、`LootConditionMoonPhase`、`LootConditionFrostmawHasCrystal` |
| `com.ilexiconn.llibrary.server.animation` | 5 | `AnimationHandler`、`IAnimatedEntity`、`Animation` |
| `com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy` | 5 | `SupernovaAbility`、`SolarFlareAbility`、`SunstrikeAbility` |
| `com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw` | 4 | `MowzieJigsawManager`、`JigsawHandler`、`MowziePoolElement` |
| `com.bobmowzie.mowziesmobs.client.particle.util` | 4 | `AdvancedParticleBase`、`ParticleComponent`、`RibbonComponent` |
| `com.bobmowzie.mowziesmobs.client.gui` | 4 | `CustomBossBar`、`GuiSculptorTrade`、`GuiUmvuthiTrade` |
| `com.bobmowzie.mowziesmobs.server.ability.abilities.mob` | 4 | `BlockAbility`、`MeleeAttackAbility`、`HurtAbility` |
| `com.bobmowzie.mowziesmobs.client.particle.types` | 4 | `AdvancedParticleType`、`RibbonParticleType`、`TerrainParticleType` |
| `com.bobmowzie.mowziesmobs.client.model.armor` | 4 | `ModelGeomancerArmor`、`UmvuthanaMaskModel`、`SolVisageModel` |
| `com.bobmowzie.mowziesmobs.server.message.mouse` | 4 | `MessageRightMouseDown`、`MessageRightMouseUp`、`MessageLeftMouseUp` |
| … | | 其余 38 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EntityHandler` | class | — | EventBusSubscriber | 28 | 419 | [EntityHandler.java](com/bobmowzie/mowziesmobs/server/entity/EntityHandler.java) |
| `ClientEventHandler` | class | — | EventBusSubscriber | 14 | 354 | [ClientEventHandler.java](com/bobmowzie/mowziesmobs/client/ClientEventHandler.java) |
| `NetworkHandler` | class | — | EventBusSubscriber | 16 | 330 | [NetworkHandler.java](com/bobmowzie/mowziesmobs/server/message/NetworkHandler.java) |
| `ParticleHandler` | class | — | EventBusSubscriber | 23 | 220 | [ParticleHandler.java](com/bobmowzie/mowziesmobs/client/particle/ParticleHandler.java) |
| `ServerEventHandler` | class | — | — | 35 | 1016 | [ServerEventHandler.java](com/bobmowzie/mowziesmobs/server/ServerEventHandler.java) |
| `ClientEventBusSubscriber` | class | — | EventBusSubscriber | 3 | 120 | [ClientEventBusSubscriber.java](com/bobmowzie/mowziesmobs/client/ClientEventBusSubscriber.java) |
| `LayerHandler` | class | — | EventBusSubscriber | 2 | 28 | [LayerHandler.java](com/bobmowzie/mowziesmobs/client/model/LayerHandler.java) |
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 30 | [DataGenerators.java](com/bobmowzie/mowziesmobs/datagen/DataGenerators.java) |
| `AbilityCommonEventHandler` | class | — | — | 10 | 138 | [AbilityCommonEventHandler.java](com/bobmowzie/mowziesmobs/server/ability/AbilityCommonEventHandler.java) |
| `ConfigHandler` | class | — | — | 2 | 1045 | [ConfigHandler.java](com/bobmowzie/mowziesmobs/server/config/ConfigHandler.java) |
| `EntityFrostmaw` | class | MowzieLLibraryEntity | — | 48 | 1203 | [EntityFrostmaw.java](com/bobmowzie/mowziesmobs/server/entity/frostmaw/EntityFrostmaw.java) |
| `EntityElokosa` | class | MowzieGeckoEntity | — | 145 | 2234 | [EntityElokosa.java](com/bobmowzie/mowziesmobs/server/entity/elokosa/EntityElokosa.java) |
| `EntitySculptor` | class | MowzieGeckoEntity | — | 142 | 1921 | [EntitySculptor.java](com/bobmowzie/mowziesmobs/server/entity/sculptor/EntitySculptor.java) |
| `EntityUmvuthi` | class | MowzieGeckoEntity | — | 87 | 1943 | [EntityUmvuthi.java](com/bobmowzie/mowziesmobs/server/entity/umvuthana/EntityUmvuthi.java) |
| `EntityUmvuthana` | class | MowzieGeckoEntity | — | 78 | 1513 | [EntityUmvuthana.java](com/bobmowzie/mowziesmobs/server/entity/umvuthana/EntityUmvuthana.java) |
| `EntityNaga` | class | MowzieLLibraryEntity | — | 70 | 1276 | [EntityNaga.java](com/bobmowzie/mowziesmobs/server/entity/naga/EntityNaga.java) |
| `MMClient` | class | — | Mod | 2 | 69 | [MMClient.java](com/bobmowzie/mowziesmobs/MMClient.java) |
| `EntityBlockSwapper` | class | Entity | — | 37 | 492 | [EntityBlockSwapper.java](com/bobmowzie/mowziesmobs/server/entity/effects/EntityBlockSwapper.java) |
| `ClientLayerRegistry` | class | — | — | 2 | 54 | [ClientLayerRegistry.java](com/bobmowzie/mowziesmobs/client/ClientLayerRegistry.java) |
| `AdvancedModelRenderer` | class | BasicModelRenderer | — | 45 | 676 | [AdvancedModelRenderer.java](com/ilexiconn/llibrary/client/model/tools/AdvancedModelRenderer.java) |
| `MMCommon` | class | — | Mod | 4 | 225 | [MMCommon.java](com/bobmowzie/mowziesmobs/MMCommon.java) |
| `MowzieEntity` | class | PathfinderMob | — | 55 | 618 | [MowzieEntity.java](com/bobmowzie/mowziesmobs/server/entity/MowzieEntity.java) |
| `EntityWroughtnaut` | class | MowzieLLibraryEntity | — | 43 | 645 | [EntityWroughtnaut.java](com/bobmowzie/mowziesmobs/server/entity/wroughtnaut/EntityWroughtnaut.java) |
| `EntityBluff` | class | MowzieGeckoEntity | — | 35 | 645 | [EntityBluff.java](com/bobmowzie/mowziesmobs/server/entity/bluff/EntityBluff.java) |
| `EntityBoulderSculptor` | class | EntityBoulderProjectile | — | 30 | 536 | [EntityBoulderSculptor.java](com/bobmowzie/mowziesmobs/server/entity/effects/geomancy/EntityBoulderSculptor.java) |
| `EntityGrottol` | class | MowzieLLibraryEntity | — | 43 | 489 | [EntityGrottol.java](com/bobmowzie/mowziesmobs/server/entity/grottol/EntityGrottol.java) |
| `RakedSandBlock` | class | ColoredFallingBlock | — | 33 | 496 | [RakedSandBlock.java](com/bobmowzie/mowziesmobs/server/block/RakedSandBlock.java) |
| `GongBlock` | class | BaseEntityBlock | — | 39 | 429 | [GongBlock.java](com/bobmowzie/mowziesmobs/server/block/GongBlock.java) |
| `AbilityClientEventHandler` | class | — | — | 1 | 21 | [AbilityClientEventHandler.java](com/bobmowzie/mowziesmobs/server/ability/AbilityClientEventHandler.java) |
| `MowzieJigsawManager` | class | MowzieJigsawManager.Placer | — | 6 | 919 | [MowzieJigsawManager.java](com/bobmowzie/mowziesmobs/server/world/feature/structure/jigsaw/MowzieJigsawManager.java) |
| `WeakIdentityHashMap` | class | WeakReference | — | 36 | 246 | [WeakIdentityHashMap.java](com/ilexiconn/llibrary/server/util/WeakIdentityHashMap.java) |
| `EntitySolarBeam` | class | Entity | — | 29 | 512 | [EntitySolarBeam.java](com/bobmowzie/mowziesmobs/server/entity/effects/EntitySolarBeam.java) |
| `EntityBabyFoliaath` | class | MowzieLLibraryEntity | — | 30 | 315 | [EntityBabyFoliaath.java](com/bobmowzie/mowziesmobs/server/entity/foliaath/EntityBabyFoliaath.java) |
| `EntityFoliaath` | class | MowzieLLibraryEntity | — | 30 | 362 | [EntityFoliaath.java](com/bobmowzie/mowziesmobs/server/entity/foliaath/EntityFoliaath.java) |
| `BlockEntityHandler` | class | — | — | 0 | 17 | [BlockEntityHandler.java](com/bobmowzie/mowziesmobs/server/block/entity/BlockEntityHandler.java) |
| `PlayerData` | class | INBTSerializable<CompoundTag> | — | 46 | 545 | [PlayerData.java](com/bobmowzie/mowziesmobs/server/capability/PlayerData.java) |
| `AdvancedParticleBase` | class | TextureSheetParticle | — | 35 | 676 | [AdvancedParticleBase.java](com/bobmowzie/mowziesmobs/client/particle/util/AdvancedParticleBase.java) |
| `ParticleComponent` | class | ParticleComponent | — | 35 | 680 | [ParticleComponent.java](com/bobmowzie/mowziesmobs/client/particle/util/ParticleComponent.java) |
| `BlockBehaviourMixin` | class | ICopiedBlockProperties | Mixin | 1 | 38 | [BlockBehaviourMixin.java](com/bobmowzie/mowziesmobs/mixin/BlockBehaviourMixin.java) |
| `EntitySunstrike` | class | Entity | — | 27 | 288 | [EntitySunstrike.java](com/bobmowzie/mowziesmobs/server/entity/effects/EntitySunstrike.java) |
| `EntityPillar` | class | EntityGeomancyBase | — | 28 | 352 | [EntityPillar.java](com/bobmowzie/mowziesmobs/server/entity/effects/geomancy/EntityPillar.java) |
| `EntityUmvuthanaMinion` | class | EntityUmvuthana | — | 25 | 291 | [EntityUmvuthanaMinion.java](com/bobmowzie/mowziesmobs/server/entity/umvuthana/EntityUmvuthanaMinion.java) |
| `EntityLantern` | class | MowzieLLibraryEntity | — | 21 | 401 | [EntityLantern.java](com/bobmowzie/mowziesmobs/server/entity/lantern/EntityLantern.java) |
| `FrozenData` | class | INBTSerializable<CompoundTag> | — | 33 | 338 | [FrozenData.java](com/bobmowzie/mowziesmobs/server/capability/FrozenData.java) |
| `LootTableHandler` | class | — | — | 1 | 51 | [LootTableHandler.java](com/bobmowzie/mowziesmobs/server/loot/LootTableHandler.java) |
| `ItemHandler` | class | — | — | 2 | 284 | [ItemHandler.java](com/bobmowzie/mowziesmobs/server/item/ItemHandler.java) |
| `BasicModelRenderer` | class | — | — | 19 | 532 | [BasicModelRenderer.java](com/ilexiconn/llibrary/client/model/tools/BasicModelRenderer.java) |
| `EntityFallingBlock` | class | Entity | — | 15 | 173 | [EntityFallingBlock.java](com/bobmowzie/mowziesmobs/server/entity/effects/EntityFallingBlock.java) |
| `EntityBoulderProjectile` | class | EntityBoulderBase | — | 22 | 373 | [EntityBoulderProjectile.java](com/bobmowzie/mowziesmobs/server/entity/effects/geomancy/EntityBoulderProjectile.java) |
| `EntityGeomancyBase` | class | EntityMagicEffect | — | 24 | 327 | [EntityGeomancyBase.java](com/bobmowzie/mowziesmobs/server/entity/effects/geomancy/EntityGeomancyBase.java) |
| `EntityUmvuthanaCrane` | class | EntityUmvuthanaMinion | — | 20 | 337 | [EntityUmvuthanaCrane.java](com/bobmowzie/mowziesmobs/server/entity/umvuthana/EntityUmvuthanaCrane.java) |
| `EntityUmvuthanaFollowerToPlayer` | class | EntityUmvuthanaFollower | — | 21 | 223 | [EntityUmvuthanaFollowerToPlayer.java](com/bobmowzie/mowziesmobs/server/entity/umvuthana/EntityUmvuthanaFollowerToPlayer.java) |
| `BlockHandler` | class | — | — | 2 | 59 | [BlockHandler.java](com/bobmowzie/mowziesmobs/server/block/BlockHandler.java) |
| `PlayerAbility` | class | Ability | — | 30 | 207 | [PlayerAbility.java](com/bobmowzie/mowziesmobs/server/ability/PlayerAbility.java) |
| `Ability` | class | — | — | 48 | 323 | [Ability.java](com/bobmowzie/mowziesmobs/server/ability/Ability.java) |
| `RegistryDataGenerator` | class | DatapackBuiltinEntriesProvider | — | 0 | 20 | [RegistryDataGenerator.java](com/bobmowzie/mowziesmobs/datagen/RegistryDataGenerator.java) |
| `MMModels` | class | — | — | 19 | 139 | [MMModels.java](com/bobmowzie/mowziesmobs/client/MMModels.java) |
| `FrozenRenderHandler` | class | GeoRenderLayer | — | 8 | 208 | [FrozenRenderHandler.java](com/bobmowzie/mowziesmobs/client/render/entity/FrozenRenderHandler.java) |
| `ModelNaga` | class | MowzieEntityModel | — | 10 | 1391 | [ModelNaga.java](com/bobmowzie/mowziesmobs/client/model/entity/ModelNaga.java) |
| `MowzieGeoBone` | class | GeoBone | — | 44 | 258 | [MowzieGeoBone.java](com/bobmowzie/mowziesmobs/client/model/tools/geckolib/MowzieGeoBone.java) |

## 事件处理（`@SubscribeEvent`，11 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ServerEventHandler` | 30 | `com.bobmowzie.mowziesmobs.server` | [ServerEventHandler.java](com/bobmowzie/mowziesmobs/server/ServerEventHandler.java) |
| `ClientEventHandler` | 13 | `com.bobmowzie.mowziesmobs.client` | [ClientEventHandler.java](com/bobmowzie/mowziesmobs/client/ClientEventHandler.java) |
| `AbilityCommonEventHandler` | 10 | `com.bobmowzie.mowziesmobs.server.ability` | [AbilityCommonEventHandler.java](com/bobmowzie/mowziesmobs/server/ability/AbilityCommonEventHandler.java) |
| `ClientEventBusSubscriber` | 3 | `com.bobmowzie.mowziesmobs.client` | [ClientEventBusSubscriber.java](com/bobmowzie/mowziesmobs/client/ClientEventBusSubscriber.java) |
| `EntityHandler` | 1 | `com.bobmowzie.mowziesmobs.server.entity` | [EntityHandler.java](com/bobmowzie/mowziesmobs/server/entity/EntityHandler.java) |
| `NetworkHandler` | 1 | `com.bobmowzie.mowziesmobs.server.message` | [NetworkHandler.java](com/bobmowzie/mowziesmobs/server/message/NetworkHandler.java) |
| `ParticleHandler` | 1 | `com.bobmowzie.mowziesmobs.client.particle` | [ParticleHandler.java](com/bobmowzie/mowziesmobs/client/particle/ParticleHandler.java) |
| `LayerHandler` | 1 | `com.bobmowzie.mowziesmobs.client.model` | [LayerHandler.java](com/bobmowzie/mowziesmobs/client/model/LayerHandler.java) |
| `DataGenerators` | 1 | `com.bobmowzie.mowziesmobs.datagen` | [DataGenerators.java](com/bobmowzie/mowziesmobs/datagen/DataGenerators.java) |
| `ClientLayerRegistry` | 1 | `com.bobmowzie.mowziesmobs.client` | [ClientLayerRegistry.java](com/bobmowzie/mowziesmobs/client/ClientLayerRegistry.java) |
| `MMModels` | 1 | `com.bobmowzie.mowziesmobs.client` | [MMModels.java](com/bobmowzie/mowziesmobs/client/MMModels.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockBehaviourMixin` | `BlockBehaviour` | [BlockBehaviourMixin.java](com/bobmowzie/mowziesmobs/mixin/BlockBehaviourMixin.java) |
| `ArmorMaterialMixin` | `ArmorMaterial` | [ArmorMaterialMixin.java](com/bobmowzie/mowziesmobs/mixin/ArmorMaterialMixin.java) |
| `HumanoidArmorLayerAccess` | `HumanoidArmorLayerAccess` | [HumanoidArmorLayerAccess.java](com/bobmowzie/mowziesmobs/mixin/client/HumanoidArmorLayerAccess.java) |
| `GeoModelMixin` | `GeoModel` | [GeoModelMixin.java](com/bobmowzie/mowziesmobs/mixin/client/GeoModelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigHandler` | — | [ConfigHandler.java](com/bobmowzie/mowziesmobs/server/config/ConfigHandler.java) |
| `ConfigurableArmorMaterial` | — | [ConfigurableArmorMaterial.java](com/bobmowzie/mowziesmobs/server/config/ConfigurableArmorMaterial.java) |

## 按包浏览

- `com.bobmowzie.mowziesmobs`（2 类）
- `com.bobmowzie.mowziesmobs.client`（5 类）
- `com.bobmowzie.mowziesmobs.client.gui`（4 类）
- `com.bobmowzie.mowziesmobs.client.model`（1 类）
- `com.bobmowzie.mowziesmobs.client.model.armor`（4 类）
- `com.bobmowzie.mowziesmobs.client.model.entity`（27 类）
- `com.bobmowzie.mowziesmobs.client.model.item`（2 类）
- `com.bobmowzie.mowziesmobs.client.model.tools`（10 类）
- `com.bobmowzie.mowziesmobs.client.model.tools.dynamics`（2 类）
- `com.bobmowzie.mowziesmobs.client.model.tools.geckolib`（3 类）
- `com.bobmowzie.mowziesmobs.client.particle`（10 类）
- `com.bobmowzie.mowziesmobs.client.particle.types`（4 类）
- `com.bobmowzie.mowziesmobs.client.particle.util`（4 类）
- `com.bobmowzie.mowziesmobs.client.render`（2 类）
- `com.bobmowzie.mowziesmobs.client.render.block`（2 类）
- `com.bobmowzie.mowziesmobs.client.render.entity`（31 类）
- `com.bobmowzie.mowziesmobs.client.render.entity.layer`（21 类）
- `com.bobmowzie.mowziesmobs.client.render.entity.player`（3 类）
- `com.bobmowzie.mowziesmobs.client.render.item`（7 类）
- `com.bobmowzie.mowziesmobs.client.sound`（13 类）
- `com.bobmowzie.mowziesmobs.datagen`（9 类）
- `com.bobmowzie.mowziesmobs.mixin`（2 类）
- `com.bobmowzie.mowziesmobs.mixin.client`（2 类）
- `com.bobmowzie.mowziesmobs.server`（2 类）
- `com.bobmowzie.mowziesmobs.server.ability`（7 类）
- `com.bobmowzie.mowziesmobs.server.ability.abilities.mob`（4 类）
- `com.bobmowzie.mowziesmobs.server.ability.abilities.player`（6 类）
- `com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy`（8 类）
- `com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy`（5 类）
- `com.bobmowzie.mowziesmobs.server.advancement`（7 类）
- `com.bobmowzie.mowziesmobs.server.ai`（19 类）
- `com.bobmowzie.mowziesmobs.server.ai.animation`（15 类）
- `com.bobmowzie.mowziesmobs.server.block`（5 类）
- `com.bobmowzie.mowziesmobs.server.block.entity`（2 类）
- `com.bobmowzie.mowziesmobs.server.bossinfo`（2 类）
- `com.bobmowzie.mowziesmobs.server.capability`（5 类）
- `com.bobmowzie.mowziesmobs.server.config`（2 类）
- `com.bobmowzie.mowziesmobs.server.creativetab`（1 类）
- `com.bobmowzie.mowziesmobs.server.damage`（1 类）
- `com.bobmowzie.mowziesmobs.server.entity`（16 类）
- `com.bobmowzie.mowziesmobs.server.entity.bluff`（1 类）
- `com.bobmowzie.mowziesmobs.server.entity.effects`（11 类）
- `com.bobmowzie.mowziesmobs.server.entity.effects.geomancy`（10 类）
- `com.bobmowzie.mowziesmobs.server.entity.elokosa`（5 类）
- `com.bobmowzie.mowziesmobs.server.entity.foliaath`（2 类）
- `com.bobmowzie.mowziesmobs.server.entity.frostmaw`（2 类）
- `com.bobmowzie.mowziesmobs.server.entity.grottol`（3 类）
- `com.bobmowzie.mowziesmobs.server.entity.grottol.ai`（1 类）
- `com.bobmowzie.mowziesmobs.server.entity.lantern`（1 类）
- `com.bobmowzie.mowziesmobs.server.entity.naga`（1 类）
- `com.bobmowzie.mowziesmobs.server.entity.sculptor`（1 类）
- `com.bobmowzie.mowziesmobs.server.entity.umvuthana`（10 类）
- `com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade`（2 类）
- `com.bobmowzie.mowziesmobs.server.entity.wroughtnaut`（1 类）
- `com.bobmowzie.mowziesmobs.server.inventory`（9 类）
- `com.bobmowzie.mowziesmobs.server.item`（25 类）
- `com.bobmowzie.mowziesmobs.server.loot`（5 类）
- `com.bobmowzie.mowziesmobs.server.message`（13 类）
- `com.bobmowzie.mowziesmobs.server.message.mouse`（4 类）
- `com.bobmowzie.mowziesmobs.server.potion`（11 类）
- `com.bobmowzie.mowziesmobs.server.power`（1 类）
- `com.bobmowzie.mowziesmobs.server.sound`（1 类）
- `com.bobmowzie.mowziesmobs.server.tag`（1 类）
- `com.bobmowzie.mowziesmobs.server.util`（3 类）
- `com.bobmowzie.mowziesmobs.server.world`（3 类）
- `com.bobmowzie.mowziesmobs.server.world.feature.structure`（9 类）
- `com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw`（4 类）
- `com.bobmowzie.mowziesmobs.server.world.feature.structure.processor`（8 类）
- `com.bobmowzie.mowziesmobs.server.world.spawn`（1 类）
- `com.ilexiconn.llibrary.client.model`（1 类）
- `com.ilexiconn.llibrary.client.model.tools`（7 类）
- `com.ilexiconn.llibrary.client.util`（2 类）
- `com.ilexiconn.llibrary.server.animation`（5 类）
- `com.ilexiconn.llibrary.server.entity`（1 类）
- `com.ilexiconn.llibrary.server.event`（1 类）
- `com.ilexiconn.llibrary.server.network`（1 类）
- `com.ilexiconn.llibrary.server.util`（5 类）
- `com.ilexiconn.llibrary.server.world`（1 类）
