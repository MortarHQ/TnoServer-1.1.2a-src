# Jaden's Nether Expansion（`netherexp`）

> jar：`[下界扩展]Jadens-Nether-Expansion.jar` ｜ 版本：`2.4.1` ｜ 作者：ThatJadenXgamer/ThatMaidenJaden
> 规模：**284 个类 / 52 个包 / 34923 行** ｜ 事件处理类 3 ｜ Mixin 32 ｜ 注册/管理类 7

**依赖**：`neoforge`、`minecraft`、`elysium_api`、`lodestone`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.jadenxgamer.netherexp.core.block` | 62 | `BlackIcicleBlock`、`PetrifiedSwirlsBlock`、`DiscernmentGlassBlock` |
| `net.jadenxgamer.netherexp.core.entity` | 19 | `EctoSlab`、`Stampede`、`Carcass` |
| `net.jadenxgamer.netherexp.registry` | 18 | `JNESoundEvents`、`JNEBlocks`、`JNEBlockEntityType` |
| `net.jadenxgamer.netherexp.client.particle` | 17 | `FallingParticle`、`JNEDripParticle`、`BurstParticle` |
| `net.jadenxgamer.netherexp.client.rendering.entity` | 16 | `VesselRenderer`、`StampedeRenderer`、`CarcassRenderer` |
| `net.jadenxgamer.netherexp.core.item` | 14 | `PumpChargeShotgunItem`、`ShotgunFistItem`、`SanctumCompassItem` |
| `net.jadenxgamer.netherexp.mixin.client` | 12 | `SplashManagerMixin`、`LevelRendererMixin`、`MagmaCubeRendererMixin` |
| `net.jadenxgamer.netherexp.util` | 9 | `RegistryHelper`、`BlockCrackTracker`、`CompatUtil` |
| `net.jadenxgamer.netherexp.mixin.block` | 7 | `WeepingVinesBlockMixin`、`TwistingVinesBlockMixin`、`StructureVoidBlockMixin` |
| `net.jadenxgamer.netherexp.core.block.entity` | 7 | `CiergeOfTreacheryBlockEntity`、`BrazierChestBlockEntity`、`PetrifiedSwirlsBlockEntity` |
| `net.jadenxgamer.netherexp.core.worldgen.feature` | 7 | `JNEConfiguredFeatures`、`BlackIceClusterFeature`、`NotGarbageLargeDripstoneFeature` |
| `net.jadenxgamer.netherexp.datagen` | 6 | `DataGenerators`、`JNEBlockLootTableProvider`、`JNEBlockTagProvider` |
| `net.jadenxgamer.netherexp.core.keys` | 6 | `JNETags`、`JNEEnchantments`、`JNEDamageSources` |
| `net.jadenxgamer.netherexp.core.effect` | 6 | `ImmunityEffect`、`DeterrentEffect`、`SoulSpeedEffect` |
| `net.jadenxgamer.netherexp.core.datadriven` | 6 | `OnDeathGroundConversion`、`ApparitionAggressions`、`Antidote` |
| `net.jadenxgamer.netherexp.mixin.entity` | 5 | `BlockEntityTypeMixin`、`LivingEntityMixin`、`AbstractArrowMixin` |
| `net.jadenxgamer.netherexp.core.misc` | 5 | `JNESoundType`、`JNEFoods`、`JNEEnumExtensions` |
| `net.jadenxgamer.netherexp.mixin.item` | 4 | `BlockItemMixin`、`PotionItemMixin`、`CrossbowItemMixin` |
| `net.jadenxgamer.netherexp.client.rendering.keyframe` | 4 | `ItemAnimationState`、`ItemHierarchicalModel`、`ItemKeyframeAnimations` |
| `net.jadenxgamer.netherexp.registry.compat` | 4 | `RubinatedNetherCompat`、`GardensOfTheDeadCompat`、`CavernsAndChasmsCompat` |
| `net.jadenxgamer.netherexp.client.rendering.block_entity` | 3 | `DiscernmentGlassBlockRenderer`、`SuspiciousSoulSandBlockRenderer`、`JNECampfireRenderer` |
| `net.jadenxgamer.netherexp.core.block.interfaces` | 3 | `LiquidloggedBlock`、`EctoplasmWaterloggedBlock`、`GlowsporesApplicable` |
| `net.jadenxgamer.netherexp.event` | 2 | `JNEEvents`、`JNEClientEvents` |
| `net.jadenxgamer.netherexp` | 2 | `NetherExpClient`、`NetherExp` |
| `net.jadenxgamer.netherexp.config` | 2 | `JNEConfigImpl`、`JNEConfigs` |
| `net.jadenxgamer.netherexp.client.assetdriven.managers` | 2 | `BurnPalettesManager`、`FireParticlesManager` |
| `net.jadenxgamer.netherexp.core.misc.neoforge.conditional_data` | 2 | `StartupConfigCondition`、`ConfigCondition` |
| `net.jadenxgamer.netherexp.config.enums` | 2 | `ProfanityConfig`、`BansheeRedirectConfig` |
| `net.jadenxgamer.netherexp.client` | 2 | `JNEItemRenderer`、`JNEFogRenderer` |
| `net.jadenxgamer.netherexp.core.fluid` | 2 | `EctoplasmLiquidBlock`、`EctoplasmFluidType` |
| `net.jadenxgamer.netherexp.client.rendering.item` | 2 | `PumpChargeShotgunModel`、`ShotgunFistModel` |
| `net.jadenxgamer.netherexp.client.rendering.extensions` | 2 | `JNEItemExtensions`、`JNEFluidExtensions` |
| `net.jadenxgamer.netherexp.core.item.components` | 2 | `AntidoteContents`、`LocatorCompass` |
| `net.jadenxgamer.netherexp.client.shader` | 2 | `NetherHeatDistortionPostprocessor`、`SoulGlassPostProcessor` |
| `net.jadenxgamer.netherexp.client.assetdriven` | 2 | `FireParticles`、`BurnPalettes` |
| `net.jadenxgamer.netherexp.client.rendering` | 2 | `JNEShaders`、`JNERenderType` |
| `net.jadenxgamer.netherexp.mixin.entity.ai` | 1 | `NearestAttackableTargetGoalMixin` |
| `net.jadenxgamer.netherexp.mixin.worldgen` | 1 | `NetherWorldCarverMixin` |
| `net.jadenxgamer.netherexp.mixin.compat` | 1 | `HugeFlatFungusFeatureMixin` |
| `net.jadenxgamer.netherexp.mixin.misc` | 1 | `PortalForcerMixin` |
| … | | 其余 12 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `JNEEvents` | class | — | EventBusSubscriber | 19 | 207 | [JNEEvents.java](net/jadenxgamer/netherexp/event/JNEEvents.java) |
| `JNEClientEvents` | class | — | EventBusSubscriber | 14 | 165 | [JNEClientEvents.java](net/jadenxgamer/netherexp/event/JNEClientEvents.java) |
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 37 | [DataGenerators.java](net/jadenxgamer/netherexp/datagen/DataGenerators.java) |
| `NetherExpClient` | class | — | Mod | 7 | 245 | [NetherExpClient.java](net/jadenxgamer/netherexp/NetherExpClient.java) |
| `EctoSlab` | class | PossessedMob | — | 92 | 1602 | [EctoSlab.java](net/jadenxgamer/netherexp/core/entity/EctoSlab.java) |
| `SplashManagerMixin` | class | — | Mixin | 1 | 61 | [SplashManagerMixin.java](net/jadenxgamer/netherexp/mixin/client/SplashManagerMixin.java) |
| `NetherExp` | class | — | Mod | 4 | 71 | [NetherExp.java](net/jadenxgamer/netherexp/NetherExp.java) |
| `Stampede` | class | PossessedMob | — | 83 | 937 | [Stampede.java](net/jadenxgamer/netherexp/core/entity/Stampede.java) |
| `BlockEntityTypeMixin` | class | — | Mixin | 1 | 28 | [BlockEntityTypeMixin.java](net/jadenxgamer/netherexp/mixin/entity/BlockEntityTypeMixin.java) |
| `BlockItemMixin` | class | — | Mixin | 1 | 45 | [BlockItemMixin.java](net/jadenxgamer/netherexp/mixin/item/BlockItemMixin.java) |
| `WeepingVinesBlockMixin` | class | GrowingPlantHeadBlock | Mixin | 6 | 102 | [WeepingVinesBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/WeepingVinesBlockMixin.java) |
| `TwistingVinesBlockMixin` | class | GrowingPlantHeadBlock | Mixin | 6 | 102 | [TwistingVinesBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/TwistingVinesBlockMixin.java) |
| `JNEConfigImpl` | class | — | — | 10 | 623 | [JNEConfigImpl.java](net/jadenxgamer/netherexp/config/JNEConfigImpl.java) |
| `RegistryHelper` | class | — | — | 12 | 81 | [RegistryHelper.java](net/jadenxgamer/netherexp/util/RegistryHelper.java) |
| `Carcass` | class | PathfinderMob | — | 41 | 421 | [Carcass.java](net/jadenxgamer/netherexp/core/entity/Carcass.java) |
| `Apparition` | class | ExorcismMob | — | 48 | 518 | [Apparition.java](net/jadenxgamer/netherexp/core/entity/Apparition.java) |
| `Vessel` | class | PossessedMob | — | 32 | 492 | [Vessel.java](net/jadenxgamer/netherexp/core/entity/Vessel.java) |
| `BlackIcicleBlock` | class | PointedDripstoneBlock | — | 28 | 344 | [BlackIcicleBlock.java](net/jadenxgamer/netherexp/core/block/BlackIcicleBlock.java) |
| `CiergeOfTreacheryBlockEntity` | class | BlockEntity | — | 17 | 310 | [CiergeOfTreacheryBlockEntity.java](net/jadenxgamer/netherexp/core/block/entity/CiergeOfTreacheryBlockEntity.java) |
| `LivingEntityMixin` | class | — | Mixin | 0 | 37 | [LivingEntityMixin.java](net/jadenxgamer/netherexp/mixin/entity/LivingEntityMixin.java) |
| `StructureVoidBlockMixin` | class | — | Mixin | 0 | 26 | [StructureVoidBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/StructureVoidBlockMixin.java) |
| `BrewingStandBlockMixin` | class | — | Mixin | 1 | 28 | [BrewingStandBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/BrewingStandBlockMixin.java) |
| `BaseFireBlockMixin` | class | Block | Mixin | 0 | 78 | [BaseFireBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/BaseFireBlockMixin.java) |
| `StemBlockMixin` | class | — | Mixin | 0 | 22 | [StemBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/StemBlockMixin.java) |
| `NetherPortalBlockMixin` | class | — | Mixin | 2 | 74 | [NetherPortalBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/NetherPortalBlockMixin.java) |
| `PotionItemMixin` | class | — | Mixin | 0 | 34 | [PotionItemMixin.java](net/jadenxgamer/netherexp/mixin/item/PotionItemMixin.java) |
| `CrossbowItemMixin` | class | — | Mixin | 0 | 40 | [CrossbowItemMixin.java](net/jadenxgamer/netherexp/mixin/item/CrossbowItemMixin.java) |
| `LevelRendererMixin` | class | — | Mixin | 0 | 59 | [LevelRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/LevelRendererMixin.java) |
| `MagmaCubeRendererMixin` | class | — | Mixin | 0 | 27 | [MagmaCubeRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/MagmaCubeRendererMixin.java) |
| `SplashRendererMixin` | class | — | Mixin | 0 | 31 | [SplashRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/SplashRendererMixin.java) |
| `EntityRenderDispatcherMixin` | class | — | Mixin | 0 | 33 | [EntityRenderDispatcherMixin.java](net/jadenxgamer/netherexp/mixin/client/EntityRenderDispatcherMixin.java) |
| `IClientFluidTypeExtensionsMixin` | interface | — | Mixin | 0 | 26 | [IClientFluidTypeExtensionsMixin.java](net/jadenxgamer/netherexp/mixin/client/IClientFluidTypeExtensionsMixin.java) |
| `ClientLevelMixin` | class | — | Mixin | 0 | 19 | [ClientLevelMixin.java](net/jadenxgamer/netherexp/mixin/client/ClientLevelMixin.java) |
| `FogRendererMixin` | class | — | Mixin | 0 | 34 | [FogRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/FogRendererMixin.java) |
| `JNESoundEvents` | class | — | — | 2 | 199 | [JNESoundEvents.java](net/jadenxgamer/netherexp/registry/JNESoundEvents.java) |
| `BurnPalettesManager` | class | SimpleJsonResourceReloadListener | — | 10 | 290 | [BurnPalettesManager.java](net/jadenxgamer/netherexp/client/assetdriven/managers/BurnPalettesManager.java) |
| `VesselRenderer` | class | MobRenderer | — | 10 | 857 | [VesselRenderer.java](net/jadenxgamer/netherexp/client/rendering/entity/VesselRenderer.java) |
| `StampedeRenderer` | class | MobRenderer | — | 11 | 859 | [StampedeRenderer.java](net/jadenxgamer/netherexp/client/rendering/entity/StampedeRenderer.java) |
| `WillOWisp` | class | ThrowableItemProjectile | — | 27 | 448 | [WillOWisp.java](net/jadenxgamer/netherexp/core/entity/WillOWisp.java) |
| `Wisp` | class | ExorcismMob | — | 45 | 384 | [Wisp.java](net/jadenxgamer/netherexp/core/entity/Wisp.java) |
| `BrazierChestBlockEntity` | class | RandomizableContainerBlockEntity | — | 18 | 197 | [BrazierChestBlockEntity.java](net/jadenxgamer/netherexp/core/block/entity/BrazierChestBlockEntity.java) |
| `PetrifiedSwirlsBlockEntity` | class | BlockEntity | — | 16 | 190 | [PetrifiedSwirlsBlockEntity.java](net/jadenxgamer/netherexp/core/block/entity/PetrifiedSwirlsBlockEntity.java) |
| `PumpChargeShotgunItem` | class | ProjectileWeaponItem | — | 21 | 375 | [PumpChargeShotgunItem.java](net/jadenxgamer/netherexp/core/item/PumpChargeShotgunItem.java) |
| `JNEBlocks` | class | — | — | 2 | 1249 | [JNEBlocks.java](net/jadenxgamer/netherexp/registry/JNEBlocks.java) |
| `CarcassRenderer` | class | MobRenderer | — | 7 | 956 | [CarcassRenderer.java](net/jadenxgamer/netherexp/client/rendering/entity/CarcassRenderer.java) |
| `EctoSlabRenderer` | class | MobRenderer | — | 17 | 448 | [EctoSlabRenderer.java](net/jadenxgamer/netherexp/client/rendering/entity/EctoSlabRenderer.java) |
| `ApparitionRenderer` | class | MobRenderer | — | 8 | 919 | [ApparitionRenderer.java](net/jadenxgamer/netherexp/client/rendering/entity/ApparitionRenderer.java) |
| `Banshee` | class | PossessedMob | — | 28 | 359 | [Banshee.java](net/jadenxgamer/netherexp/core/entity/Banshee.java) |
| `PetrifiedSwirlsBlock` | class | BaseEntityBlock | — | 19 | 287 | [PetrifiedSwirlsBlock.java](net/jadenxgamer/netherexp/core/block/PetrifiedSwirlsBlock.java) |
| `ShotgunFistItem` | class | ProjectileWeaponItem | — | 19 | 294 | [ShotgunFistItem.java](net/jadenxgamer/netherexp/core/item/ShotgunFistItem.java) |
| `AbstractArrowMixin` | class | — | Mixin | 1 | 28 | [AbstractArrowMixin.java](net/jadenxgamer/netherexp/mixin/entity/AbstractArrowMixin.java) |
| `ProjectileMixin` | class | — | Mixin | 0 | 53 | [ProjectileMixin.java](net/jadenxgamer/netherexp/mixin/entity/ProjectileMixin.java) |
| `StrayMixin` | class | — | Mixin | 0 | 27 | [StrayMixin.java](net/jadenxgamer/netherexp/mixin/entity/StrayMixin.java) |
| `NearestAttackableTargetGoalMixin` | class | TargetGoal | Mixin | 0 | 35 | [NearestAttackableTargetGoalMixin.java](net/jadenxgamer/netherexp/mixin/entity/ai/NearestAttackableTargetGoalMixin.java) |
| `NetherWorldCarverMixin` | class | CaveWorldCarver | Mixin | 0 | 56 | [NetherWorldCarverMixin.java](net/jadenxgamer/netherexp/mixin/worldgen/NetherWorldCarverMixin.java) |
| `HugeFlatFungusFeatureMixin` | class | — | Mixin、Pseudo | 0 | 21 | [HugeFlatFungusFeatureMixin.java](net/jadenxgamer/netherexp/mixin/compat/HugeFlatFungusFeatureMixin.java) |
| `PortalForcerMixin` | class | — | Mixin | 0 | 28 | [PortalForcerMixin.java](net/jadenxgamer/netherexp/mixin/misc/PortalForcerMixin.java) |
| `DecoratedPotPatternsMixin` | class | — | Mixin | 0 | 35 | [DecoratedPotPatternsMixin.java](net/jadenxgamer/netherexp/mixin/item/DecoratedPotPatternsMixin.java) |
| `FlameParticleSmallProviderMixin` | class | — | Mixin | 1 | 44 | [FlameParticleSmallProviderMixin.java](net/jadenxgamer/netherexp/mixin/client/FlameParticleSmallProviderMixin.java) |
| `HumanoidModelMixin` | class | — | Mixin | 0 | 49 | [HumanoidModelMixin.java](net/jadenxgamer/netherexp/mixin/client/HumanoidModelMixin.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `JNEEvents` | 19 | `net.jadenxgamer.netherexp.event` | [JNEEvents.java](net/jadenxgamer/netherexp/event/JNEEvents.java) |
| `JNEClientEvents` | 14 | `net.jadenxgamer.netherexp.event` | [JNEClientEvents.java](net/jadenxgamer/netherexp/event/JNEClientEvents.java) |
| `DataGenerators` | 1 | `net.jadenxgamer.netherexp.datagen` | [DataGenerators.java](net/jadenxgamer/netherexp/datagen/DataGenerators.java) |

## Mixin（32 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `SplashManagerMixin` | `SplashManager` | [SplashManagerMixin.java](net/jadenxgamer/netherexp/mixin/client/SplashManagerMixin.java) |
| `BlockEntityTypeMixin` | `BlockEntityType` | [BlockEntityTypeMixin.java](net/jadenxgamer/netherexp/mixin/entity/BlockEntityTypeMixin.java) |
| `BlockItemMixin` | `BlockItem` | [BlockItemMixin.java](net/jadenxgamer/netherexp/mixin/item/BlockItemMixin.java) |
| `WeepingVinesBlockMixin` | `WeepingVinesBlock` | [WeepingVinesBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/WeepingVinesBlockMixin.java) |
| `TwistingVinesBlockMixin` | `TwistingVinesBlock` | [TwistingVinesBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/TwistingVinesBlockMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](net/jadenxgamer/netherexp/mixin/entity/LivingEntityMixin.java) |
| `StructureVoidBlockMixin` | `StructureVoidBlock` | [StructureVoidBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/StructureVoidBlockMixin.java) |
| `BrewingStandBlockMixin` | `BrewingStandBlock` | [BrewingStandBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/BrewingStandBlockMixin.java) |
| `BaseFireBlockMixin` | `BaseFireBlock` | [BaseFireBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/BaseFireBlockMixin.java) |
| `StemBlockMixin` | `StemBlock` | [StemBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/StemBlockMixin.java) |
| `NetherPortalBlockMixin` | `NetherPortalBlock` | [NetherPortalBlockMixin.java](net/jadenxgamer/netherexp/mixin/block/NetherPortalBlockMixin.java) |
| `PotionItemMixin` | `PotionItem` | [PotionItemMixin.java](net/jadenxgamer/netherexp/mixin/item/PotionItemMixin.java) |
| `CrossbowItemMixin` | `CrossbowItem` | [CrossbowItemMixin.java](net/jadenxgamer/netherexp/mixin/item/CrossbowItemMixin.java) |
| `LevelRendererMixin` | `LevelRenderer` | [LevelRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/LevelRendererMixin.java) |
| `MagmaCubeRendererMixin` | `MagmaCubeRenderer` | [MagmaCubeRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/MagmaCubeRendererMixin.java) |
| `SplashRendererMixin` | `SplashRenderer` | [SplashRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/SplashRendererMixin.java) |
| `EntityRenderDispatcherMixin` | `EntityRenderDispatcher` | [EntityRenderDispatcherMixin.java](net/jadenxgamer/netherexp/mixin/client/EntityRenderDispatcherMixin.java) |
| `IClientFluidTypeExtensionsMixin` | `IClientFluidTypeExtensions` | [IClientFluidTypeExtensionsMixin.java](net/jadenxgamer/netherexp/mixin/client/IClientFluidTypeExtensionsMixin.java) |
| `ClientLevelMixin` | `ClientLevel` | [ClientLevelMixin.java](net/jadenxgamer/netherexp/mixin/client/ClientLevelMixin.java) |
| `FogRendererMixin` | `FogRenderer` | [FogRendererMixin.java](net/jadenxgamer/netherexp/mixin/client/FogRendererMixin.java) |
| `AbstractArrowMixin` | `AbstractArrow` | [AbstractArrowMixin.java](net/jadenxgamer/netherexp/mixin/entity/AbstractArrowMixin.java) |
| `ProjectileMixin` | `Projectile` | [ProjectileMixin.java](net/jadenxgamer/netherexp/mixin/entity/ProjectileMixin.java) |
| `StrayMixin` | `Stray` | [StrayMixin.java](net/jadenxgamer/netherexp/mixin/entity/StrayMixin.java) |
| `NearestAttackableTargetGoalMixin` | `NearestAttackableTargetGoal` | [NearestAttackableTargetGoalMixin.java](net/jadenxgamer/netherexp/mixin/entity/ai/NearestAttackableTargetGoalMixin.java) |
| `NetherWorldCarverMixin` | `NetherWorldCarver` | [NetherWorldCarverMixin.java](net/jadenxgamer/netherexp/mixin/worldgen/NetherWorldCarverMixin.java) |
| `HugeFlatFungusFeatureMixin` | `HugeFlatFungusFeature` | [HugeFlatFungusFeatureMixin.java](net/jadenxgamer/netherexp/mixin/compat/HugeFlatFungusFeatureMixin.java) |
| `PortalForcerMixin` | `PortalForcer` | [PortalForcerMixin.java](net/jadenxgamer/netherexp/mixin/misc/PortalForcerMixin.java) |
| `DecoratedPotPatternsMixin` | `DecoratedPotPatterns` | [DecoratedPotPatternsMixin.java](net/jadenxgamer/netherexp/mixin/item/DecoratedPotPatternsMixin.java) |
| `FlameParticleSmallProviderMixin` | `FlameParticleSmallProvider` | [FlameParticleSmallProviderMixin.java](net/jadenxgamer/netherexp/mixin/client/FlameParticleSmallProviderMixin.java) |
| `HumanoidModelMixin` | `HumanoidModel` | [HumanoidModelMixin.java](net/jadenxgamer/netherexp/mixin/client/HumanoidModelMixin.java) |
| `ParticleEngineMixin` | `ParticleEngine` | [ParticleEngineMixin.java](net/jadenxgamer/netherexp/mixin/client/ParticleEngineMixin.java) |
| `FlameParticleProviderMixin` | `FlameParticleProvider` | [FlameParticleProviderMixin.java](net/jadenxgamer/netherexp/mixin/client/FlameParticleProviderMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `JNEConfigImpl` | — | [JNEConfigImpl.java](net/jadenxgamer/netherexp/config/JNEConfigImpl.java) |
| `JNEConfigs` | — | [JNEConfigs.java](net/jadenxgamer/netherexp/config/JNEConfigs.java) |
| `JNEConfiguredFeatures` | — | [JNEConfiguredFeatures.java](net/jadenxgamer/netherexp/core/worldgen/feature/JNEConfiguredFeatures.java) |
| `StartupConfigCondition` | — | [StartupConfigCondition.java](net/jadenxgamer/netherexp/core/misc/neoforge/conditional_data/StartupConfigCondition.java) |
| `ConfigCondition` | — | [ConfigCondition.java](net/jadenxgamer/netherexp/core/misc/neoforge/conditional_data/ConfigCondition.java) |
| `ProfanityConfig` | — | [ProfanityConfig.java](net/jadenxgamer/netherexp/config/enums/ProfanityConfig.java) |
| `BansheeRedirectConfig` | — | [BansheeRedirectConfig.java](net/jadenxgamer/netherexp/config/enums/BansheeRedirectConfig.java) |

## 按包浏览

- `net.jadenxgamer.netherexp`（2 类）
- `net.jadenxgamer.netherexp.client`（2 类）
- `net.jadenxgamer.netherexp.client.assetdriven`（2 类）
- `net.jadenxgamer.netherexp.client.assetdriven.managers`（2 类）
- `net.jadenxgamer.netherexp.client.gui`（1 类）
- `net.jadenxgamer.netherexp.client.gui.hud`（1 类）
- `net.jadenxgamer.netherexp.client.particle`（17 类）
- `net.jadenxgamer.netherexp.client.rendering`（2 类）
- `net.jadenxgamer.netherexp.client.rendering.block_entity`（3 类）
- `net.jadenxgamer.netherexp.client.rendering.entity`（16 类）
- `net.jadenxgamer.netherexp.client.rendering.extensions`（2 类）
- `net.jadenxgamer.netherexp.client.rendering.item`（2 类）
- `net.jadenxgamer.netherexp.client.rendering.keyframe`（4 类）
- `net.jadenxgamer.netherexp.client.rendering.layer`（1 类）
- `net.jadenxgamer.netherexp.client.rendering.particle`（1 类）
- `net.jadenxgamer.netherexp.client.shader`（2 类）
- `net.jadenxgamer.netherexp.client.sound`（1 类）
- `net.jadenxgamer.netherexp.config`（2 类）
- `net.jadenxgamer.netherexp.config.enums`（2 类）
- `net.jadenxgamer.netherexp.core.block`（62 类）
- `net.jadenxgamer.netherexp.core.block.entity`（7 类）
- `net.jadenxgamer.netherexp.core.block.enums`（1 类）
- `net.jadenxgamer.netherexp.core.block.interfaces`（3 类）
- `net.jadenxgamer.netherexp.core.datadriven`（6 类）
- `net.jadenxgamer.netherexp.core.effect`（6 类）
- `net.jadenxgamer.netherexp.core.entity`（19 类）
- `net.jadenxgamer.netherexp.core.entity.ai`（1 类）
- `net.jadenxgamer.netherexp.core.entity.interfaces`（1 类）
- `net.jadenxgamer.netherexp.core.fluid`（2 类）
- `net.jadenxgamer.netherexp.core.item`（14 类）
- `net.jadenxgamer.netherexp.core.item.components`（2 类）
- `net.jadenxgamer.netherexp.core.keys`（6 类）
- `net.jadenxgamer.netherexp.core.misc`（5 类）
- `net.jadenxgamer.netherexp.core.misc.neoforge.conditional_data`（2 类）
- `net.jadenxgamer.netherexp.core.misc.neoforge.glm`（1 类）
- `net.jadenxgamer.netherexp.core.worldgen`（1 类）
- `net.jadenxgamer.netherexp.core.worldgen.carver`（1 类）
- `net.jadenxgamer.netherexp.core.worldgen.feature`（7 类）
- `net.jadenxgamer.netherexp.datagen`（6 类）
- `net.jadenxgamer.netherexp.event`（2 类）
- `net.jadenxgamer.netherexp.mixin`（1 类）
- `net.jadenxgamer.netherexp.mixin.block`（7 类）
- `net.jadenxgamer.netherexp.mixin.client`（12 类）
- `net.jadenxgamer.netherexp.mixin.compat`（1 类）
- `net.jadenxgamer.netherexp.mixin.entity`（5 类）
- `net.jadenxgamer.netherexp.mixin.entity.ai`（1 类）
- `net.jadenxgamer.netherexp.mixin.item`（4 类）
- `net.jadenxgamer.netherexp.mixin.misc`（1 类）
- `net.jadenxgamer.netherexp.mixin.worldgen`（1 类）
- `net.jadenxgamer.netherexp.registry`（18 类）
- `net.jadenxgamer.netherexp.registry.compat`（4 类）
- `net.jadenxgamer.netherexp.util`（9 类）
