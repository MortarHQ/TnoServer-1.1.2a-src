# Tensura: Reincarnated（`tensura`）

> jar：`[转生]tensura.jar` ｜ 版本：`2.0.1.3` ｜ 作者：MinhEragon, MrProfessor, ItsJustArthur, Borniuus
> 规模：**1936 个类 / 247 个包 / 319133 行** ｜ 事件处理类 5 ｜ Mixin 89 ｜ 注册/管理类 34

**依赖**：`neoforge`、`minecraft`、`manascore_storage`、`manascore_skill`、`manascore_race`、`architectury`、`geckolib`、`smartbrainlib`、`terrablender`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `io.github.manasmods.tensura.client.entity.monster` | 101 | `OrcDisasterRenderer`、`HellCaterpillarRenderer`、`OrcLordRenderer` |
| `io.github.manasmods.tensura.ability.skill.extra` | 62 | `BodyDoubleSkill`、`BlackLightningSkill`、`SpatialDominationSkill` |
| `io.github.manasmods.tensura.ability.skill.unique` | 59 | `PrideSkill`、`GreatSageSkill`、`UnyieldingSkill` |
| `io.github.manasmods.tensura.entity.monster` | 55 | `DirewolfEntity`、`ArchDaemonEntity`、`SlimeEntity` |
| `io.github.manasmods.tensura.mixin` | 49 | `MixinLivingEntity`、`MixinBedBlockEntity`、`MixinBaseContainerBlockEntity` |
| `io.github.manasmods.tensura.entity.projectile.magic` | 46 | `ChaosEaterProjectile`、`WindTornadoProjectile`、`IceLanceProjectile` |
| `io.github.manasmods.tensura.ability.skill.resist` | 45 | `ResistSkill`、`SpatialAttackNullification`、`MagicResistance` |
| `io.github.manasmods.tensura.effect.ability` | 35 | `DragonModeEffect`、`BeastTransformationEffect`、`InstantRegenerationEffect` |
| `io.github.manasmods.tensura.block` | 33 | `KilnBlock`、`WarpPadBlock`、`ToolRackBlock` |
| `io.github.manasmods.tensura.ability.skill.intrinsic` | 32 | `PossessionSkill`、`GiantificationSkill`、`TitanificationSkill` |
| `io.github.manasmods.tensura.client.screen` | 27 | `SpatialMovementScreen`、`IllusionItemScreen`、`AbilitySelectionScreen` |
| `io.github.manasmods.tensura.mixin.client` | 24 | `MixinLivingEntityRenderer`、`MixinItemRenderer`、`MixinScreenEffectRenderer` |
| `io.github.manasmods.tensura.entity.variant` | 23 | `DaemonVariant`、`DwarfVariant`、`GoblinVariant` |
| `io.github.manasmods.tensura.command.argument` | 22 | `BossFightPlayerHandlerArg`、`BossFightPlayerHandlerArgument`、`EntityTypeArg` |
| `io.github.manasmods.tensura.entity.template.subclass` | 22 | `ILivingPartEntity`、`ISubordinate`、`IDigging` |
| `io.github.manasmods.tensura.client.entity.misc` | 21 | `TensuraBoatEntityRenderer`、`LightningBoltRenderer`、`ChaosEaterRenderer` |
| `io.github.manasmods.tensura.entity.magic.barrier` | 20 | `BarrierEntity`、`MegiddoBubbleEntity`、`DisintegrationEntity` |
| `io.github.manasmods.tensura.item.armor.custom` | 20 | `BatGliderItem`、`PierrotMaskItem`、`AntiMagicMaskItem` |
| `io.github.manasmods.tensura.menu` | 19 | `ResearcherEnchantingMenu`、`ResearcherStorageMenu`、`SynthesisSeparationMenu` |
| `io.github.manasmods.tensura.client.entity.projectile.magic` | 19 | `ItemProjectileRenderer`、`AuraBulletRenderer`、`MagicSphereRenderer` |
| `io.github.manasmods.tensura.effect` | 19 | `RampageEffect`、`InfectionEffect`、`FrostEffect` |
| `io.github.manasmods.tensura.item.misc` | 18 | `ResetScrollItem`、`PouchItem`、`OrcDisasterHeadItem` |
| `io.github.manasmods.tensura.client.entity.layer` | 18 | `DwarfLayer`、`GoblinLayer`、`ArchDaemonLayer` |
| `io.github.manasmods.tensura.config.race` | 16 | `OgreConfig`、`DaemonConfig`、`OrcConfig` |
| `io.github.manasmods.tensura.neoforge.data.tag` | 16 | `TensuraBlockTagProvider`、`TensuraItemTagProvider`、`TensuraEntityTypeTagProvider` |
| `io.github.manasmods.tensura.ability.skill.common` | 16 | `ThoughtCommunicationSkill`、`WaterCurrentControlSkill`、`FarsightSkill` |
| `io.github.manasmods.tensura.particle.type` | 16 | `TensuraDripParticle`、`SimpleFireParticle`、`SkyShotParticle` |
| `io.github.manasmods.tensura.effect.debuff` | 16 | `EnergyBlockadeEffect`、`SpatialBlockadeEffect`、`LustEmbracementEffect` |
| `io.github.manasmods.tensura.entity.human` | 15 | `GazelDwargoEntity`、`HinataSakaguchiEntity`、`DwarfEntity` |
| `io.github.manasmods.tensura.network.s2c` | 15 | `ClientAccess`、`OpenIllusionItemScreenPayload`、`OpenSpatialMovementMenuPayload` |
| `io.github.manasmods.tensura.client.entity.barrier` | 14 | `DisintegrationRenderer`、`MegiddoBubbleRenderer`、`BarrierRenderer` |
| `io.github.manasmods.tensura.item.weapon` | 14 | `SimpleSpearItem`、`SimpleKatanaItem`、`SimpleKodachiItem` |
| `io.github.manasmods.tensura.client.entity.field` | 14 | `HellFlareRenderer`、`MagicExplosionRenderer`、`MagicHandsRenderer` |
| `io.github.manasmods.tensura.entity.projectile` | 13 | `ThrownItemProjectile`、`TensuraFlyingProjectile`、`SevererBladeProjectile` |
| `io.github.manasmods.tensura.entity.ai.behaviour.path` | 13 | `SetRandomDigTarget`、`SetWalkTargetFromBlockMemory`、`SetWalkTargetToSpecificBlock` |
| `io.github.manasmods.tensura.entity.ai.behaviour.misc` | 13 | `InteractWithEntity`、`VillagerLikeBreed`、`FindNearestBlock` |
| `io.github.manasmods.tensura.client.entity.human` | 13 | `PlayerLikeSkeletonRenderer`、`PlayerLikeRenderer`、`CloneRenderer` |
| `io.github.manasmods.tensura.entity.merchant.trade` | 13 | `OneForTaggedItemTrade`、`TaggedItemForOneTrade`、`OneForEnchantedItemTrade` |
| `io.github.manasmods.tensura.network.c2s` | 12 | `RequestAbilityModeChangePacket`、`RequestNamingMenuPacket`、`RequestSpatialActionPacket` |
| `io.github.manasmods.tensura.block.template` | 12 | `AllSidesDirectionalBlock`、`SidewayDirectionalBlock`、`LooseBlock` |
| … | | 其余 207 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RegistryHandlers` | class | — | EventBusSubscriber | 7 | 65 | [RegistryHandlers.java](io/github/manasmods/tensura/neoforge/handler/RegistryHandlers.java) |
| `ClientEventHandlers` | class | — | EventBusSubscriber、OnlyIn | 6 | 106 | [ClientEventHandlers.java](io/github/manasmods/tensura/neoforge/handler/client/ClientEventHandlers.java) |
| `CommonEventHandlers` | class | — | EventBusSubscriber | 10 | 175 | [CommonEventHandlers.java](io/github/manasmods/tensura/neoforge/handler/CommonEventHandlers.java) |
| `ClientSetupHandlers` | class | — | EventBusSubscriber、OnlyIn | 5 | 114 | [ClientSetupHandlers.java](io/github/manasmods/tensura/neoforge/handler/client/ClientSetupHandlers.java) |
| `AbilityHandlers` | class | — | EventBusSubscriber | 6 | 133 | [AbilityHandlers.java](io/github/manasmods/tensura/neoforge/handler/AbilityHandlers.java) |
| `OverlayHandler` | class | — | — | 49 | 1629 | [OverlayHandler.java](io/github/manasmods/tensura/handler/client/OverlayHandler.java) |
| `TensuraRecipeProvider` | class | RecipeProvider | — | 69 | 6027 | [TensuraRecipeProvider.java](io/github/manasmods/tensura/neoforge/data/TensuraRecipeProvider.java) |
| `OgreConfig` | class | ManasConfig | SyncToClient | 117 | 971 | [OgreConfig.java](io/github/manasmods/tensura/config/race/OgreConfig.java) |
| `BossFightCommand` | class | — | Command | 52 | 1323 | [BossFightCommand.java](io/github/manasmods/tensura/command/world/BossFightCommand.java) |
| `StatCommand` | class | — | Command、Permission | 41 | 1017 | [StatCommand.java](io/github/manasmods/tensura/command/edit/StatCommand.java) |
| `GazelDwargoEntity` | class | PlayerLikeEntity | — | 54 | 1268 | [GazelDwargoEntity.java](io/github/manasmods/tensura/entity/human/GazelDwargoEntity.java) |
| `HinataSakaguchiEntity` | class | OtherworlderEntity | — | 65 | 1541 | [HinataSakaguchiEntity.java](io/github/manasmods/tensura/entity/human/HinataSakaguchiEntity.java) |
| `DirewolfEntity` | class | TensuraMountEntity | — | 82 | 1253 | [DirewolfEntity.java](io/github/manasmods/tensura/entity/monster/DirewolfEntity.java) |
| `ArchDaemonEntity` | class | PlayerLikeEntity | — | 113 | 1504 | [ArchDaemonEntity.java](io/github/manasmods/tensura/entity/monster/ArchDaemonEntity.java) |
| `SlimeEntity` | class | TensuraMountEntity | — | 87 | 1132 | [SlimeEntity.java](io/github/manasmods/tensura/entity/monster/SlimeEntity.java) |
| `TensuraItemModelProvider` | class | ItemModelProvider | — | 47 | 1273 | [TensuraItemModelProvider.java](io/github/manasmods/tensura/neoforge/data/model/TensuraItemModelProvider.java) |
| `KilnBlockEntity` | class | BaseContainerBlockEntity | — | 63 | 775 | [KilnBlockEntity.java](io/github/manasmods/tensura/block/entity/KilnBlockEntity.java) |
| `DaemonConfig` | class | ManasConfig | SyncToClient | 68 | 631 | [DaemonConfig.java](io/github/manasmods/tensura/config/race/DaemonConfig.java) |
| `OrcConfig` | class | ManasConfig | SyncToClient | 78 | 650 | [OrcConfig.java](io/github/manasmods/tensura/config/race/OrcConfig.java) |
| `TensuraBlocks` | class | — | — | 37 | 1442 | [TensuraBlocks.java](io/github/manasmods/tensura/registry/block/TensuraBlocks.java) |
| `SpatialMovementScreen` | class | SimpleScreen | — | 30 | 1026 | [SpatialMovementScreen.java](io/github/manasmods/tensura/client/screen/SpatialMovementScreen.java) |
| `DwarfEntity` | class | TensuraMerchantEntity | — | 74 | 890 | [DwarfEntity.java](io/github/manasmods/tensura/entity/human/DwarfEntity.java) |
| `CharybdisEntity` | class | TensuraMountEntity | — | 70 | 942 | [CharybdisEntity.java](io/github/manasmods/tensura/entity/monster/CharybdisEntity.java) |
| `GreaterDaemonEntity` | class | PlayerLikeEntity | — | 48 | 939 | [GreaterDaemonEntity.java](io/github/manasmods/tensura/entity/monster/GreaterDaemonEntity.java) |
| `LizardmanEntity` | class | TensuraMerchantEntity | — | 82 | 978 | [LizardmanEntity.java](io/github/manasmods/tensura/entity/monster/LizardmanEntity.java) |
| `ElementalColossusEntity` | class | TensuraMountEntity | — | 59 | 842 | [ElementalColossusEntity.java](io/github/manasmods/tensura/entity/monster/ElementalColossusEntity.java) |
| `OrcEntity` | class | PlayerLikeEntity | — | 68 | 839 | [OrcEntity.java](io/github/manasmods/tensura/entity/monster/OrcEntity.java) |
| `OrcLordEntity` | class | OrcEntity | — | 48 | 875 | [OrcLordEntity.java](io/github/manasmods/tensura/entity/monster/OrcLordEntity.java) |
| `MixinLivingEntity` | class | — | Mixin | 16 | 217 | [MixinLivingEntity.java](io/github/manasmods/tensura/mixin/MixinLivingEntity.java) |
| `RefiningRecipe` | class | Recipe<RefiningRecipeInput> | — | 39 | 403 | [RefiningRecipe.java](io/github/manasmods/tensura/recipe/RefiningRecipe.java) |
| `SmithingBenchRecipe` | class | Recipe<SmithingBenchRecipeInput> | — | 46 | 433 | [SmithingBenchRecipe.java](io/github/manasmods/tensura/recipe/SmithingBenchRecipe.java) |
| `LizardmanConfig` | class | ManasConfig | SyncToClient | 58 | 478 | [LizardmanConfig.java](io/github/manasmods/tensura/config/race/LizardmanConfig.java) |
| `ElfConfig` | class | ManasConfig | SyncToClient | 53 | 443 | [ElfConfig.java](io/github/manasmods/tensura/config/race/ElfConfig.java) |
| `HumanConfig` | class | ManasConfig | SyncToClient | 53 | 443 | [HumanConfig.java](io/github/manasmods/tensura/config/race/HumanConfig.java) |
| `BeastfolkConfig` | class | ManasConfig | SyncToClient | 53 | 443 | [BeastfolkConfig.java](io/github/manasmods/tensura/config/race/BeastfolkConfig.java) |
| `MerfolkConfig` | class | ManasConfig | SyncToClient | 57 | 471 | [MerfolkConfig.java](io/github/manasmods/tensura/config/race/MerfolkConfig.java) |
| `SlimeConfig` | class | ManasConfig | SyncToClient | 67 | 543 | [SlimeConfig.java](io/github/manasmods/tensura/config/race/SlimeConfig.java) |
| `GoblinConfig` | class | ManasConfig | SyncToClient | 53 | 443 | [GoblinConfig.java](io/github/manasmods/tensura/config/race/GoblinConfig.java) |
| `HarpyConfig` | class | ManasConfig | SyncToClient | 64 | 520 | [HarpyConfig.java](io/github/manasmods/tensura/config/race/HarpyConfig.java) |
| `VampireConfig` | class | ManasConfig | SyncToClient | 66 | 550 | [VampireConfig.java](io/github/manasmods/tensura/config/race/VampireConfig.java) |
| `DwarfConfig` | class | ManasConfig | SyncToClient | 53 | 443 | [DwarfConfig.java](io/github/manasmods/tensura/config/race/DwarfConfig.java) |
| `WightConfig` | class | ManasConfig | SyncToClient | 52 | 436 | [WightConfig.java](io/github/manasmods/tensura/config/race/WightConfig.java) |
| `StatGetCommand` | class | — | Command | 31 | 621 | [StatGetCommand.java](io/github/manasmods/tensura/command/get/StatGetCommand.java) |
| `IllusionItemScreen` | class | SimpleScreen | — | 33 | 473 | [IllusionItemScreen.java](io/github/manasmods/tensura/client/screen/IllusionItemScreen.java) |
| `CloneEntity` | class | PlayerLikeEntity | — | 60 | 726 | [CloneEntity.java](io/github/manasmods/tensura/entity/human/CloneEntity.java) |
| `WarGnomeEntity` | class | GreaterSpiritEntity | — | 36 | 637 | [WarGnomeEntity.java](io/github/manasmods/tensura/entity/monster/WarGnomeEntity.java) |
| `IfritEntity` | class | GreaterSpiritEntity | — | 38 | 792 | [IfritEntity.java](io/github/manasmods/tensura/entity/monster/IfritEntity.java) |
| `BeastGnomeEntity` | class | TensuraRideableEntity | — | 44 | 621 | [BeastGnomeEntity.java](io/github/manasmods/tensura/entity/monster/BeastGnomeEntity.java) |
| `PhantasporeEntity` | class | TensuraMountEntity | — | 43 | 601 | [PhantasporeEntity.java](io/github/manasmods/tensura/entity/monster/PhantasporeEntity.java) |
| `SalamanderEntity` | class | TensuraTamableEntity | — | 51 | 623 | [SalamanderEntity.java](io/github/manasmods/tensura/entity/monster/SalamanderEntity.java) |
| `LesserDaemonEntity` | class | PlayerLikeEntity | — | 45 | 746 | [LesserDaemonEntity.java](io/github/manasmods/tensura/entity/monster/LesserDaemonEntity.java) |
| `UndineEntity` | class | GreaterSpiritEntity | — | 31 | 611 | [UndineEntity.java](io/github/manasmods/tensura/entity/monster/UndineEntity.java) |
| `BlackSpiderEntity` | class | TensuraMountEntity | — | 53 | 617 | [BlackSpiderEntity.java](io/github/manasmods/tensura/entity/monster/BlackSpiderEntity.java) |
| `HellCaterpillarEntity` | class | TensuraTamableEntity | — | 61 | 698 | [HellCaterpillarEntity.java](io/github/manasmods/tensura/entity/monster/HellCaterpillarEntity.java) |
| `GoblinEntity` | class | TensuraMerchantEntity | — | 67 | 670 | [GoblinEntity.java](io/github/manasmods/tensura/entity/monster/GoblinEntity.java) |
| `SylphideEntity` | class | GreaterSpiritEntity | — | 30 | 656 | [SylphideEntity.java](io/github/manasmods/tensura/entity/monster/SylphideEntity.java) |
| `ThrownItemProjectile` | class | AbstractArrow | — | 39 | 672 | [ThrownItemProjectile.java](io/github/manasmods/tensura/entity/projectile/ThrownItemProjectile.java) |
| `TensuraTamableEntity` | class | TamableAnimal | — | 74 | 715 | [TensuraTamableEntity.java](io/github/manasmods/tensura/entity/template/TensuraTamableEntity.java) |
| `PlayerLikeEntity` | class | TensuraHumanoidEntity | — | 46 | 687 | [PlayerLikeEntity.java](io/github/manasmods/tensura/entity/template/PlayerLikeEntity.java) |
| `TensuraMerchantEntity` | class | PlayerLikeEntity | — | 74 | 692 | [TensuraMerchantEntity.java](io/github/manasmods/tensura/entity/template/TensuraMerchantEntity.java) |

## 事件处理（`@SubscribeEvent`，5 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CommonEventHandlers` | 10 | `io.github.manasmods.tensura.neoforge.handler` | [CommonEventHandlers.java](io/github/manasmods/tensura/neoforge/handler/CommonEventHandlers.java) |
| `ClientEventHandlers` | 6 | `io.github.manasmods.tensura.neoforge.handler.client` | [ClientEventHandlers.java](io/github/manasmods/tensura/neoforge/handler/client/ClientEventHandlers.java) |
| `AbilityHandlers` | 6 | `io.github.manasmods.tensura.neoforge.handler` | [AbilityHandlers.java](io/github/manasmods/tensura/neoforge/handler/AbilityHandlers.java) |
| `ClientSetupHandlers` | 5 | `io.github.manasmods.tensura.neoforge.handler.client` | [ClientSetupHandlers.java](io/github/manasmods/tensura/neoforge/handler/client/ClientSetupHandlers.java) |
| `RegistryHandlers` | 3 | `io.github.manasmods.tensura.neoforge.handler` | [RegistryHandlers.java](io/github/manasmods/tensura/neoforge/handler/RegistryHandlers.java) |

## Mixin（89 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinLivingEntity` | `LivingEntity` | [MixinLivingEntity.java](io/github/manasmods/tensura/mixin/MixinLivingEntity.java) |
| `MixinBedBlockEntity` | `BedBlockEntity` | [MixinBedBlockEntity.java](io/github/manasmods/tensura/mixin/MixinBedBlockEntity.java) |
| `MixinBaseContainerBlockEntity` | `BaseContainerBlockEntity` | [MixinBaseContainerBlockEntity.java](io/github/manasmods/tensura/mixin/MixinBaseContainerBlockEntity.java) |
| `MixinItemEntity` | `ItemEntity` | [MixinItemEntity.java](io/github/manasmods/tensura/mixin/MixinItemEntity.java) |
| `MixinLivingEntityRenderer` | `LivingEntityRenderer` | [MixinLivingEntityRenderer.java](io/github/manasmods/tensura/mixin/client/MixinLivingEntityRenderer.java) |
| `MixinItemRenderer` | `ItemRenderer` | [MixinItemRenderer.java](io/github/manasmods/tensura/mixin/client/MixinItemRenderer.java) |
| `MixinScreenEffectRenderer` | `ScreenEffectRenderer` | [MixinScreenEffectRenderer.java](io/github/manasmods/tensura/mixin/client/MixinScreenEffectRenderer.java) |
| `MixinItemInHandRenderer` | `ItemInHandRenderer` | [MixinItemInHandRenderer.java](io/github/manasmods/tensura/mixin/client/MixinItemInHandRenderer.java) |
| `MixinGeoEntityRenderer` | `GeoEntityRenderer` | [MixinGeoEntityRenderer.java](io/github/manasmods/tensura/mixin/client/MixinGeoEntityRenderer.java) |
| `MixinSculkShriekerBlockEntity` | `SculkShriekerBlockEntity` | [MixinSculkShriekerBlockEntity.java](io/github/manasmods/tensura/mixin/warden/MixinSculkShriekerBlockEntity.java) |
| `MixinSculkSensorBlockEntity` | `SculkSensorBlockEntity` | [MixinSculkSensorBlockEntity.java](io/github/manasmods/tensura/mixin/warden/MixinSculkSensorBlockEntity.java) |
| `MixinEntity` | `Entity` | [MixinEntity.java](io/github/manasmods/tensura/mixin/MixinEntity.java) |
| `MixinItemStack` | `ItemStack` | [MixinItemStack.java](io/github/manasmods/tensura/mixin/MixinItemStack.java) |
| `MixinIBlockExtension` | `IBlockExtension` | [MixinIBlockExtension.java](io/github/manasmods/tensura/neoforge/mixin/MixinIBlockExtension.java) |
| `AxeItemAccessor` | `AxeItemAccessor` | [AxeItemAccessor.java](io/github/manasmods/tensura/neoforge/mixin/AxeItemAccessor.java) |
| `MixinItemStack` | `ItemStack` | [MixinItemStack.java](io/github/manasmods/tensura/neoforge/mixin/MixinItemStack.java) |
| `MixinTitleScreen` | `TitleScreen` | [MixinTitleScreen.java](io/github/manasmods/tensura/neoforge/mixin/client/MixinTitleScreen.java) |
| `MixinFireBlock` | `FireBlock` | [MixinFireBlock.java](io/github/manasmods/tensura/mixin/MixinFireBlock.java) |
| `MixinBrewingStandMenu` | `BrewingStandMenu` | [MixinBrewingStandMenu.java](io/github/manasmods/tensura/mixin/MixinBrewingStandMenu.java) |
| `MixinDragonEggBlock` | `DragonEggBlock` | [MixinDragonEggBlock.java](io/github/manasmods/tensura/mixin/MixinDragonEggBlock.java) |
| `MixinBlock` | `Block` | [MixinBlock.java](io/github/manasmods/tensura/mixin/MixinBlock.java) |
| `MixinPowderSnowBlock` | `PowderSnowBlock` | [MixinPowderSnowBlock.java](io/github/manasmods/tensura/mixin/MixinPowderSnowBlock.java) |
| `MixinItemFrame` | `ItemFrame` | [MixinItemFrame.java](io/github/manasmods/tensura/mixin/MixinItemFrame.java) |
| `MixinProjectileWeaponItem` | `ProjectileWeaponItem` | [MixinProjectileWeaponItem.java](io/github/manasmods/tensura/mixin/MixinProjectileWeaponItem.java) |
| `MixinFlowerPotBlock` | `FlowerPotBlock` | [MixinFlowerPotBlock.java](io/github/manasmods/tensura/mixin/MixinFlowerPotBlock.java) |
| `MixinItem` | `Item` | [MixinItem.java](io/github/manasmods/tensura/mixin/MixinItem.java) |
| `MixinGameRenderer` | `GameRenderer` | [MixinGameRenderer.java](io/github/manasmods/tensura/mixin/client/MixinGameRenderer.java) |
| `MixinEntityRenderDispatcher` | `EntityRenderDispatcher` | [MixinEntityRenderDispatcher.java](io/github/manasmods/tensura/mixin/client/MixinEntityRenderDispatcher.java) |
| `MixinFogRenderer` | `FogRenderer` | [MixinFogRenderer.java](io/github/manasmods/tensura/mixin/client/MixinFogRenderer.java) |
| `MixinPlayerRenderer` | `PlayerRenderer` | [MixinPlayerRenderer.java](io/github/manasmods/tensura/mixin/client/MixinPlayerRenderer.java) |
| `MixinLogoRenderer` | `LogoRenderer` | [MixinLogoRenderer.java](io/github/manasmods/tensura/mixin/client/MixinLogoRenderer.java) |
| `MixinItemInHandLayer` | `ItemInHandLayer` | [MixinItemInHandLayer.java](io/github/manasmods/tensura/mixin/client/MixinItemInHandLayer.java) |
| `MixinTitleScreen` | `TitleScreen` | [MixinTitleScreen.java](io/github/manasmods/tensura/mixin/client/MixinTitleScreen.java) |
| `MixinAbstractClientPlayer` | `AbstractClientPlayer` | [MixinAbstractClientPlayer.java](io/github/manasmods/tensura/mixin/client/MixinAbstractClientPlayer.java) |
| `MixinLevelRenderer` | `LevelRenderer` | [MixinLevelRenderer.java](io/github/manasmods/tensura/mixin/client/MixinLevelRenderer.java) |
| `MixinEffectRenderingInventoryScreen` | `EffectRenderingInventoryScreen` | [MixinEffectRenderingInventoryScreen.java](io/github/manasmods/tensura/mixin/client/MixinEffectRenderingInventoryScreen.java) |
| `MixinEnchantmentHelper` | `EnchantmentHelper` | [MixinEnchantmentHelper.java](io/github/manasmods/tensura/mixin/MixinEnchantmentHelper.java) |
| `MixinPlayer` | `Player` | [MixinPlayer.java](io/github/manasmods/tensura/mixin/MixinPlayer.java) |
| `MixinFoodData` | `FoodData` | [MixinFoodData.java](io/github/manasmods/tensura/mixin/MixinFoodData.java) |
| `MixinLevel` | `Level` | [MixinLevel.java](io/github/manasmods/tensura/mixin/MixinLevel.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `OgreConfig` | SyncToClient | [OgreConfig.java](io/github/manasmods/tensura/config/race/OgreConfig.java) |
| `DaemonConfig` | SyncToClient | [DaemonConfig.java](io/github/manasmods/tensura/config/race/DaemonConfig.java) |
| `OrcConfig` | SyncToClient | [OrcConfig.java](io/github/manasmods/tensura/config/race/OrcConfig.java) |
| `LizardmanConfig` | SyncToClient | [LizardmanConfig.java](io/github/manasmods/tensura/config/race/LizardmanConfig.java) |
| `ElfConfig` | SyncToClient | [ElfConfig.java](io/github/manasmods/tensura/config/race/ElfConfig.java) |
| `HumanConfig` | SyncToClient | [HumanConfig.java](io/github/manasmods/tensura/config/race/HumanConfig.java) |
| `BeastfolkConfig` | SyncToClient | [BeastfolkConfig.java](io/github/manasmods/tensura/config/race/BeastfolkConfig.java) |
| `MerfolkConfig` | SyncToClient | [MerfolkConfig.java](io/github/manasmods/tensura/config/race/MerfolkConfig.java) |
| `SlimeConfig` | SyncToClient | [SlimeConfig.java](io/github/manasmods/tensura/config/race/SlimeConfig.java) |
| `GoblinConfig` | SyncToClient | [GoblinConfig.java](io/github/manasmods/tensura/config/race/GoblinConfig.java) |
| `HarpyConfig` | SyncToClient | [HarpyConfig.java](io/github/manasmods/tensura/config/race/HarpyConfig.java) |
| `VampireConfig` | SyncToClient | [VampireConfig.java](io/github/manasmods/tensura/config/race/VampireConfig.java) |
| `DwarfConfig` | SyncToClient | [DwarfConfig.java](io/github/manasmods/tensura/config/race/DwarfConfig.java) |
| `WightConfig` | SyncToClient | [WightConfig.java](io/github/manasmods/tensura/config/race/WightConfig.java) |
| `GiantConfig` | SyncToClient | [GiantConfig.java](io/github/manasmods/tensura/config/race/GiantConfig.java) |
| `WorldRestrictionConfigManager` | — | [WorldRestrictionConfigManager.java](io/github/manasmods/tensura/storage/restriction/WorldRestrictionConfigManager.java) |
| `BossFightConfigManager` | — | [BossFightConfigManager.java](io/github/manasmods/tensura/storage/boss/BossFightConfigManager.java) |
| `UniqueSkillConfig` | — | [UniqueSkillConfig.java](io/github/manasmods/tensura/config/ability/skill/UniqueSkillConfig.java) |
| `AspectualMagicConfig` | — | [AspectualMagicConfig.java](io/github/manasmods/tensura/config/ability/magic/AspectualMagicConfig.java) |
| `ExtraSkillConfig` | — | [ExtraSkillConfig.java](io/github/manasmods/tensura/config/ability/skill/ExtraSkillConfig.java) |
| `SpiritualMagicConfig` | — | [SpiritualMagicConfig.java](io/github/manasmods/tensura/config/ability/magic/SpiritualMagicConfig.java) |
| `BlockConfig` | — | [BlockConfig.java](io/github/manasmods/tensura/config/BlockConfig.java) |
| `RaceConfig` | — | [RaceConfig.java](io/github/manasmods/tensura/config/race/RaceConfig.java) |
| `EntityConfig` | — | [EntityConfig.java](io/github/manasmods/tensura/config/entity/EntityConfig.java) |
| `MenuConfig` | — | [MenuConfig.java](io/github/manasmods/tensura/config/client/MenuConfig.java) |
| `MiscClientConfig` | — | [MiscClientConfig.java](io/github/manasmods/tensura/config/client/MiscClientConfig.java) |
| `ReincarnationConfig` | SyncToClient | [ReincarnationConfig.java](io/github/manasmods/tensura/config/ReincarnationConfig.java) |
| `BattlewillConfig` | — | [BattlewillConfig.java](io/github/manasmods/tensura/config/ability/BattlewillConfig.java) |
| `IntrinsicSkillConfig` | — | [IntrinsicSkillConfig.java](io/github/manasmods/tensura/config/ability/skill/IntrinsicSkillConfig.java) |
| `CommonSkillConfig` | — | [CommonSkillConfig.java](io/github/manasmods/tensura/config/ability/skill/CommonSkillConfig.java) |

## 按包浏览

- `architectury_inject_tensura_common_12a6574cc70b4a3aa61fb804c3191f15_5c8aa6bfef1caee43790293415445cb128b30cbc69e35e8c3fe665571bbf9cffcommon2013devjar`（1 类）
- `io.github.manasmods.tensura`（3 类）
- `io.github.manasmods.tensura.ability`（5 类）
- `io.github.manasmods.tensura.ability.battlewill`（1 类）
- `io.github.manasmods.tensura.ability.battlewill.melee`（8 类）
- `io.github.manasmods.tensura.ability.battlewill.projectile`（7 类）
- `io.github.manasmods.tensura.ability.battlewill.utility`（8 类）
- `io.github.manasmods.tensura.ability.magic`（3 类）
- `io.github.manasmods.tensura.ability.magic.aspectual`（1 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.barrier`（6 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.earth`（6 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.enhancement`（4 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.explosion`（2 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.fire`（5 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.gravity`（3 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.ice`（7 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.illusion`（5 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.lightning`（4 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.mental`（5 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.misc`（7 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.recovery`（5 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.space`（4 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.water`（6 类）
- `io.github.manasmods.tensura.ability.magic.aspectual.wind`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual`（1 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.darkness`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.earth`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.fire`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.light`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.necromancy`（6 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.space`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.water`（5 类）
- `io.github.manasmods.tensura.ability.magic.spiritual.wind`（5 类）
- `io.github.manasmods.tensura.ability.magic.summon`（9 类）
- `io.github.manasmods.tensura.ability.skill`（3 类）
- `io.github.manasmods.tensura.ability.skill.common`（16 类）
- `io.github.manasmods.tensura.ability.skill.extra`（62 类）
- `io.github.manasmods.tensura.ability.skill.intrinsic`（32 类）
- `io.github.manasmods.tensura.ability.skill.resist`（45 类）
- `io.github.manasmods.tensura.ability.skill.unique`（59 类）
- `io.github.manasmods.tensura.ability.subclass`（9 类）
- `io.github.manasmods.tensura.advancement`（5 类）
- `io.github.manasmods.tensura.block`（33 类）
- `io.github.manasmods.tensura.block.entity`（11 类）
- `io.github.manasmods.tensura.block.part`（3 类）
- `io.github.manasmods.tensura.block.template`（12 类）
- `io.github.manasmods.tensura.client`（5 类）
- `io.github.manasmods.tensura.client.armor`（9 类）
- `io.github.manasmods.tensura.client.armor.clothing`（2 类）
- `io.github.manasmods.tensura.client.block`（4 类）
- `io.github.manasmods.tensura.client.entity`（1 类）
- `io.github.manasmods.tensura.client.entity.barrier`（14 类）
- `io.github.manasmods.tensura.client.entity.beam`（5 类）
- `io.github.manasmods.tensura.client.entity.circle`（4 类）
- `io.github.manasmods.tensura.client.entity.field`（14 类）
- `io.github.manasmods.tensura.client.entity.human`（13 类）
- `io.github.manasmods.tensura.client.entity.human.golem`（2 类）
- `io.github.manasmods.tensura.client.entity.layer`（18 类）
- `io.github.manasmods.tensura.client.entity.misc`（21 类）
- `io.github.manasmods.tensura.client.entity.monster`（101 类）
- `io.github.manasmods.tensura.client.entity.multipart`（8 类）
- `io.github.manasmods.tensura.client.entity.projectile`（10 类）
- `io.github.manasmods.tensura.client.entity.projectile.magic`（19 类）
- `io.github.manasmods.tensura.client.entity.spike`（2 类）
- `io.github.manasmods.tensura.client.item`（2 类）
- `io.github.manasmods.tensura.client.layer`（3 类）
- `io.github.manasmods.tensura.client.layer.multi`（12 类）
- `io.github.manasmods.tensura.client.layer.player`（3 类）
- `io.github.manasmods.tensura.client.layer.template`（5 类）
- `io.github.manasmods.tensura.client.screen`（27 类）
- `io.github.manasmods.tensura.client.screen.templates`（4 类）
- `io.github.manasmods.tensura.client.screen.widgets`（7 类）
- `io.github.manasmods.tensura.command`（7 类）
- `io.github.manasmods.tensura.command.argument`（22 类）
- `io.github.manasmods.tensura.command.edit`（4 类）
- `io.github.manasmods.tensura.command.edit.ability`（7 类）
- `io.github.manasmods.tensura.command.edit.race`（3 类）
- `io.github.manasmods.tensura.command.get`（6 类）
- `io.github.manasmods.tensura.command.world`（6 类）
- `io.github.manasmods.tensura.compat`（7 类）
- `io.github.manasmods.tensura.config`（6 类）
- `io.github.manasmods.tensura.config.ability`（4 类）
- `io.github.manasmods.tensura.config.ability.magic`（3 类）
- `io.github.manasmods.tensura.config.ability.skill`（5 类）
- `io.github.manasmods.tensura.config.client`（3 类）
- `io.github.manasmods.tensura.config.entity`（7 类）
- `io.github.manasmods.tensura.config.race`（16 类）
- `io.github.manasmods.tensura.damage`（4 类）
- `io.github.manasmods.tensura.data`（7 类）
- `io.github.manasmods.tensura.data.chunk`（7 类）
- `io.github.manasmods.tensura.data.disolving`（2 类）
- `io.github.manasmods.tensura.data.existence`（2 类）
- `io.github.manasmods.tensura.data.existence.gear`（4 类）
- `io.github.manasmods.tensura.data.loot`（3 类）
- `io.github.manasmods.tensura.data.otherworlder`（2 类）
- `io.github.manasmods.tensura.data.recipe`（2 类）
- `io.github.manasmods.tensura.data.slotting`（4 类）
- `io.github.manasmods.tensura.data.template.critereon`（7 类）
- `io.github.manasmods.tensura.data.template.function`（2 类）
- `io.github.manasmods.tensura.effect`（19 类）
- `io.github.manasmods.tensura.effect.ability`（35 类）
- `io.github.manasmods.tensura.effect.debuff`（16 类）
- `io.github.manasmods.tensura.effect.template`（4 类）
- `io.github.manasmods.tensura.enchantment`（4 类）
- `io.github.manasmods.tensura.enchantment.effect`（9 类）
- `io.github.manasmods.tensura.enchantment.template`（2 类）
- `io.github.manasmods.tensura.entity`（1 类）
- `io.github.manasmods.tensura.entity.ai`（1 类）
- `io.github.manasmods.tensura.entity.ai.behaviour`（3 类）
- `io.github.manasmods.tensura.entity.ai.behaviour.attack`（11 类）
- `io.github.manasmods.tensura.entity.ai.behaviour.misc`（13 类）
- `io.github.manasmods.tensura.entity.ai.behaviour.movement`（7 类）
- `io.github.manasmods.tensura.entity.ai.behaviour.path`（13 类）
- `io.github.manasmods.tensura.entity.ai.behaviour.profession`（11 类）
- `io.github.manasmods.tensura.entity.ai.controller`（2 类）
- `io.github.manasmods.tensura.entity.ai.goal`（6 类）
- `io.github.manasmods.tensura.entity.ai.navigator`（6 类）
- `io.github.manasmods.tensura.entity.ai.sensor`（5 类）
- `io.github.manasmods.tensura.entity.human`（15 类）
- `io.github.manasmods.tensura.entity.human.golem`（2 类）
- `io.github.manasmods.tensura.entity.human.undead`（3 类）
- `io.github.manasmods.tensura.entity.magic`（2 类）
- `io.github.manasmods.tensura.entity.magic.barrier`（20 类）
- `io.github.manasmods.tensura.entity.magic.beam`（9 类）
- `io.github.manasmods.tensura.entity.magic.breath`（10 类）
- `io.github.manasmods.tensura.entity.magic.field`（10 类）
- `io.github.manasmods.tensura.entity.magic.field.beam`（1 类）
- `io.github.manasmods.tensura.entity.magic.field.cloud`（5 类）
- `io.github.manasmods.tensura.entity.magic.field.haki`（2 类）
- `io.github.manasmods.tensura.entity.magic.lightning`（3 类）
- `io.github.manasmods.tensura.entity.magic.misc`（8 类）
- `io.github.manasmods.tensura.entity.magic.shield`（2 类）
- `io.github.manasmods.tensura.entity.magic.spike`（8 类）
- `io.github.manasmods.tensura.entity.merchant.profession`（1 类）
- `io.github.manasmods.tensura.entity.merchant.trade`（13 类）
- `io.github.manasmods.tensura.entity.monster`（55 类）
- `io.github.manasmods.tensura.entity.multipart`（6 类）
- `io.github.manasmods.tensura.entity.projectile`（13 类）
- `io.github.manasmods.tensura.entity.projectile.magic`（46 类）
- `io.github.manasmods.tensura.entity.template`（10 类）
- `io.github.manasmods.tensura.entity.template.subclass`（22 类）
- `io.github.manasmods.tensura.entity.variant`（23 类）
- `io.github.manasmods.tensura.event`（6 类）
- `io.github.manasmods.tensura.handler`（11 类）
- `io.github.manasmods.tensura.handler.client`（3 类）
- `io.github.manasmods.tensura.item`（2 类）
- `io.github.manasmods.tensura.item.armor`（6 类）
- `io.github.manasmods.tensura.item.armor.custom`（20 类）
- `io.github.manasmods.tensura.item.consumable`（4 类）
- `io.github.manasmods.tensura.item.dispensor`（1 类）
- `io.github.manasmods.tensura.item.misc`（18 类）
- `io.github.manasmods.tensura.item.tool`（6 类）
- `io.github.manasmods.tensura.item.tool.custom`（4 类）
- `io.github.manasmods.tensura.item.weapon`（14 类）
- `io.github.manasmods.tensura.item.weapon.custom`（10 类）
- `io.github.manasmods.tensura.item.weapon.ranged`（10 类）
- `io.github.manasmods.tensura.item.weapon.spell`（2 类）
- `io.github.manasmods.tensura.menu`（19 类）
- `io.github.manasmods.tensura.menu.container`（5 类）
- `io.github.manasmods.tensura.menu.slot`（7 类）
- `io.github.manasmods.tensura.mixin`（49 类）
- `io.github.manasmods.tensura.mixin.accessor`（5 类）
- `io.github.manasmods.tensura.mixin.client`（24 类）
- `io.github.manasmods.tensura.mixin.warden`（4 类）
- `io.github.manasmods.tensura.neoforge`（2 类）
- `io.github.manasmods.tensura.neoforge.data`（6 类）
- `io.github.manasmods.tensura.neoforge.data.advancement`（2 类）
- `io.github.manasmods.tensura.neoforge.data.loot`（6 类）
- `io.github.manasmods.tensura.neoforge.data.model`（2 类）
- `io.github.manasmods.tensura.neoforge.data.tag`（16 类）
- `io.github.manasmods.tensura.neoforge.handler`（3 类）
- `io.github.manasmods.tensura.neoforge.handler.client`（2 类）
- `io.github.manasmods.tensura.neoforge.mixin`（5 类）
- `io.github.manasmods.tensura.neoforge.mixin.client`（2 类）
- `io.github.manasmods.tensura.neoforge.registry`（1 类）
- `io.github.manasmods.tensura.network`（1 类）
- `io.github.manasmods.tensura.network.c2s`（12 类）
- `io.github.manasmods.tensura.network.s2c`（15 类）
- `io.github.manasmods.tensura.particle`（4 类）
- `io.github.manasmods.tensura.particle.option`（9 类）
- `io.github.manasmods.tensura.particle.type`（16 类）
- `io.github.manasmods.tensura.race`（3 类）
- `io.github.manasmods.tensura.race.beastfolk`（4 类）
- `io.github.manasmods.tensura.race.demon`（5 类）
- `io.github.manasmods.tensura.race.dwarf`（4 类）
- `io.github.manasmods.tensura.race.elf`（4 类）
- `io.github.manasmods.tensura.race.giant`（3 类）
- `io.github.manasmods.tensura.race.goblin`（4 类）
- `io.github.manasmods.tensura.race.harpy`（4 类）
- `io.github.manasmods.tensura.race.human`（4 类）
- `io.github.manasmods.tensura.race.lizardman`（4 类）
- `io.github.manasmods.tensura.race.merfolk`（4 类）
- `io.github.manasmods.tensura.race.ogre`（9 类）
- `io.github.manasmods.tensura.race.orc`（6 类）
- `io.github.manasmods.tensura.race.slime`（4 类）
- `io.github.manasmods.tensura.race.template`（2 类）
- `io.github.manasmods.tensura.race.vampire`（5 类）
- `io.github.manasmods.tensura.race.wight`（4 类）
- `io.github.manasmods.tensura.recipe`（7 类）
- `io.github.manasmods.tensura.recipe.input`（4 类）
- `io.github.manasmods.tensura.recipe.misc`（2 类）
- `io.github.manasmods.tensura.registry`（2 类）
- `io.github.manasmods.tensura.registry.advancement`（1 类）
- `io.github.manasmods.tensura.registry.attribute`（3 类）
- `io.github.manasmods.tensura.registry.battlewill`（3 类）
- `io.github.manasmods.tensura.registry.block`（5 类）
- `io.github.manasmods.tensura.registry.data`（3 类）
- `io.github.manasmods.tensura.registry.dimension`（2 类）
- `io.github.manasmods.tensura.registry.effect`（2 类）
- `io.github.manasmods.tensura.registry.entity`（5 类）
- `io.github.manasmods.tensura.registry.entity.ai`（3 类）
- `io.github.manasmods.tensura.registry.item`（7 类）
- `io.github.manasmods.tensura.registry.item.misc`（6 类）
- `io.github.manasmods.tensura.registry.magic`（3 类）
- `io.github.manasmods.tensura.registry.menu`（1 类）
- `io.github.manasmods.tensura.registry.particle`（1 类）
- `io.github.manasmods.tensura.registry.race`（1 类）
- `io.github.manasmods.tensura.registry.recipe`（1 类）
- `io.github.manasmods.tensura.registry.skill`（5 类）
- `io.github.manasmods.tensura.registry.sound`（2 类）
- `io.github.manasmods.tensura.registry.world`（11 类）
- `io.github.manasmods.tensura.storage`（3 类）
- `io.github.manasmods.tensura.storage.ability`（4 类）
- `io.github.manasmods.tensura.storage.boss`（2 类）
- `io.github.manasmods.tensura.storage.boss.exit`（5 类）
- `io.github.manasmods.tensura.storage.boss.template`（2 类）
- `io.github.manasmods.tensura.storage.chunk`（2 类）
- `io.github.manasmods.tensura.storage.effect`（2 类）
- `io.github.manasmods.tensura.storage.ep`（2 类）
- `io.github.manasmods.tensura.storage.labyrinth`（2 类）
- `io.github.manasmods.tensura.storage.player`（3 类）
- `io.github.manasmods.tensura.storage.restriction`（2 类）
- `io.github.manasmods.tensura.storage.restriction.template`（2 类）
- `io.github.manasmods.tensura.storage.spirit`（2 类）
- `io.github.manasmods.tensura.storage.unique`（2 类）
- `io.github.manasmods.tensura.util`（9 类）
- `io.github.manasmods.tensura.util.client`（5 类）
- `io.github.manasmods.tensura.world`（2 类）
- `io.github.manasmods.tensura.world.biome`（4 类）
- `io.github.manasmods.tensura.world.biome.hell`（4 类）
- `io.github.manasmods.tensura.world.biome.overworld`（4 类）
- `io.github.manasmods.tensura.world.dimension`（3 类）
- `io.github.manasmods.tensura.world.features`（3 类）
- `io.github.manasmods.tensura.world.structure`（1 类）
- `io.github.manasmods.tensura.world.subclass`（1 类）
- `io.github.manasmods.tensura.world.tree.leaves`（1 类）
- `io.github.manasmods.tensura.world.tree.trunk`（1 类）
