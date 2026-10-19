# VanillaBackport（`vanillabackport`）

> jar：`[香草内容]vanillabackport.jar` ｜ 版本：`1.1.7.4` ｜ 作者：BlackGear
> 规模：**336 个类 / 89 个包 / 25134 行** ｜ 事件处理类 1 ｜ Mixin 71 ｜ 注册/管理类 13

**官方描述**：Backporting Minecraft Features to previous versions!

**依赖**：`neoforge`、`minecraft`、`platform`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.blackgear.vanillabackport.common.registries` | 22 | `ModBlocks`、`ModRecipeSerializers`、`ModEntityTypes` |
| `com.blackgear.vanillabackport.core.mixin.access` | 17 | `SoundManagerAccessor`、`EntityRendererAccessor`、`EntityAccessor` |
| `com.blackgear.vanillabackport.common.level.blocks` | 17 | `SpeleothemBlock`、`MossyCarpetBlock`、`DriedGhastBlock` |
| `com.blackgear.vanillabackport.core.mixin.common.entities` | 16 | `EntityMixin`、`ServerEntityMixin`、`LivingEntityMixin` |
| `com.blackgear.vanillabackport.common.level.features` | 14 | `WeightedRandomFeatureConfiguration`、`FallenTreeConfiguration`、`SpeleothemClusterConfiguration` |
| `com.blackgear.vanillabackport.common.level.entities.animal` | 13 | `FrogDataVariant`、`WolfDataVariant`、`SheepColorSpawnRules` |
| `com.blackgear.vanillabackport.core.util` | 10 | `BlockPosUtils`、`LootUtils`、`CollisionUtils` |
| `com.blackgear.vanillabackport.client.level.particles` | 10 | `FireflyParticle`、`SulfurCubeParticle`、`NoxiousGasCloudParticle` |
| `com.blackgear.vanillabackport.common.api.leash` | 9 | `LeashPhysics`、`InterpolationHandler`、`QuadLeashRegistry` |
| `com.blackgear.vanillabackport.core.mixin.client.entities.renderer` | 8 | `MobRendererMixin`、`CatRendererMixin`、`FrogRendererMixin` |
| `com.blackgear.vanillabackport.client.level.entities.layer` | 7 | `GhastHarnessHandler`、`LivingEntityEmissiveLayer`、`SulfurCubeInnerLayer` |
| `com.blackgear.vanillabackport.core.mixin.client` | 6 | `ClientBundleTooltipMixin`、`MusicManagerMixin`、`AbstractContainerScreenMixin` |
| `com.blackgear.vanillabackport.client.registries` | 6 | `ModSoundEvents`、`ModBundledTabs`、`ModModelLayers` |
| `com.blackgear.vanillabackport.client.api.renderer` | 6 | `AbstractVariantRenderer`、`ChickenVariantRenderer`、`PigVariantRenderer` |
| `com.blackgear.vanillabackport.common.resource.variant` | 6 | `FrogVariantReloadListener`、`WolfVariantReloadListener`、`CowVariantReloadListener` |
| `com.blackgear.vanillabackport.core.mixin.leash` | 5 | `EntityRendererMixin`、`FireworkRocketItemMixin`、`LeashDataMixin` |
| `com.blackgear.vanillabackport.core.mixin.common.blocks` | 5 | `DeadBushBlockMixin`、`CactusBlockMixin`、`ColoringFallingBlockMixin` |
| `com.blackgear.vanillabackport.client` | 5 | `ClientConfig`、`ClientSetup`、`CreativeTabIntegration` |
| `com.blackgear.vanillabackport.common.api.variant` | 5 | `VariantUtils`、`ClientAsset`、`VariantDataHolder` |
| `com.blackgear.vanillabackport.core.data.tags` | 5 | `ModItemTags`、`ModBlockTags`、`ModEntityTypeTags` |
| `com.blackgear.vanillabackport.client.level.entities.model` | 5 | `SulfurCubeModel`、`AdultAndBabyModelPair`、`HappyGhastModel` |
| `com.blackgear.vanillabackport.common.api.variant.spawn` | 5 | `SpawnConditions`、`SpawnPrioritySelectors`、`SpawnContext` |
| `com.blackgear.vanillabackport.common.level.entities.happyghast` | 4 | `HappyGhast`、`TemptGoal`、`HappyGhastAi` |
| `com.blackgear.vanillabackport.common.level.entities.sulfurcube` | 4 | `SulfurCube`、`AbstractCubeMob`、`SulfurCubeArchetypes` |
| `com.blackgear.vanillabackport.client.level.entities.renderer` | 4 | `PaleOakBoatRenderer`、`CreakingRenderer`、`HappyGhastRenderer` |
| `com.blackgear.vanillabackport.common.worldgen.treedecorators` | 4 | `AttachedToLogsDecorator`、`PaleMossDecorator`、`PlaceOnGroundDecorator` |
| `com.blackgear.vanillabackport.common.level.entities.creaking` | 3 | `Creaking`、`CreakingAi`、`CreakingMeleeAttack` |
| `com.blackgear.vanillabackport.core.mixin.common.item` | 3 | `BundleItemMixin`、`BundleContentsMixin`、`MutableBundleContentsMixin` |
| `com.blackgear.vanillabackport.common.worldgen.generation` | 3 | `FeatureManager`、`ChaosCubedFeatureManager`、`SpringToLifeFeatureManager` |
| `com.blackgear.vanillabackport.common.api.variant.vanilla` | 3 | `VanillaWolfVariantRegistry`、`VanillaWolfVariantUtils`、`VanillaWolfVariantSpawnSelector` |
| `com.blackgear.vanillabackport.common` | 3 | `LootIntegrations`、`CommonConfig`、`CommonSetup` |
| `com.blackgear.vanillabackport.client.api.bundle` | 3 | `ScrollWheelHandler`、`ItemSlotMouseAction`、`BundleMouseActions` |
| `com.blackgear.vanillabackport.core.data.tags.fabric` | 3 | `ConventionalItemTags`、`ConventionalBlockTags`、`FabricBiomeTags` |
| `com.blackgear.vanillabackport.core.registries` | 3 | `ModBuiltInLootTables`、`ModRegistries`、`ModBuiltinRegistries` |
| `com.blackgear.vanillabackport.common.level.items` | 3 | `SulfurCubeBucketItem`、`HarnessItem`、`PaleOakBoatItem` |
| `com.blackgear.vanillabackport.common.worldgen.features` | 3 | `SpringToLifeFeatures`、`ChaosCubedFeatures`、`TheGardenAwakensFeatures` |
| `com.blackgear.vanillabackport.common.api.extensions` | 3 | `MotionAwareEntity`、`PositionAwareEntity`、`AirborneMoverEntity` |
| `com.blackgear.vanillabackport.common.worldgen` | 3 | `ModSurfaceRuleData`、`WorldGeneration`、`BiomeGeneration` |
| `com.blackgear.vanillabackport.common.worldgen.placements` | 3 | `SpringToLifePlacements`、`ChaosCubedPlacements`、`TheGardenAwakensPlacements` |
| `com.blackgear.vanillabackport.core` | 3 | `VanillaBackport`、`ModChecker`、`FeatureFlag` |
| … | | 其余 49 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EmissiveModelHandler` | class | — | EventBusSubscriber | 2 | 54 | [EmissiveModelHandler.java](com/blackgear/vanillabackport/core/neoforge/emissive/EmissiveModelHandler.java) |
| `ClientBundleTooltipMixin` | class | ClientTooltipComponent | Mixin | 24 | 255 | [ClientBundleTooltipMixin.java](com/blackgear/vanillabackport/core/mixin/client/ClientBundleTooltipMixin.java) |
| `EntityMixin` | class | MotionAwareEntity | Mixin | 17 | 252 | [EntityMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/EntityMixin.java) |
| `MusicManagerMixin` | class | — | Mixin | 5 | 59 | [MusicManagerMixin.java](com/blackgear/vanillabackport/core/mixin/client/MusicManagerMixin.java) |
| `SoundManagerAccessor` | interface | — | Mixin | 0 | 13 | [SoundManagerAccessor.java](com/blackgear/vanillabackport/core/mixin/access/SoundManagerAccessor.java) |
| `VanillaBackportNeoForge` | class | — | Mod | 1 | 28 | [VanillaBackportNeoForge.java](com/blackgear/vanillabackport/core/neoforge/VanillaBackportNeoForge.java) |
| `EntityRendererMixin` | class | — | Mixin | 1 | 70 | [EntityRendererMixin.java](com/blackgear/vanillabackport/core/mixin/leash/EntityRendererMixin.java) |
| `EntityRendererAccessor` | interface | — | Mixin | 0 | 14 | [EntityRendererAccessor.java](com/blackgear/vanillabackport/core/mixin/access/EntityRendererAccessor.java) |
| `HappyGhast` | class | Animal | — | 80 | 884 | [HappyGhast.java](com/blackgear/vanillabackport/common/level/entities/happyghast/HappyGhast.java) |
| `SulfurCube` | class | AbstractCubeMob | — | 86 | 954 | [SulfurCube.java](com/blackgear/vanillabackport/common/level/entities/sulfurcube/SulfurCube.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 0 | 26 | [ServerGamePacketListenerImplMixin.java](com/blackgear/vanillabackport/core/mixin/common/ServerGamePacketListenerImplMixin.java) |
| `Creaking` | class | Monster | — | 61 | 635 | [Creaking.java](com/blackgear/vanillabackport/common/level/entities/creaking/Creaking.java) |
| `SpeleothemBlock` | class | Block | — | 35 | 398 | [SpeleothemBlock.java](com/blackgear/vanillabackport/common/level/blocks/SpeleothemBlock.java) |
| `SimpleBlockFeatureMixin` | class | — | Mixin | 1 | 40 | [SimpleBlockFeatureMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/feature/SimpleBlockFeatureMixin.java) |
| `BundleItemMixin` | class | — | Mixin | 2 | 172 | [BundleItemMixin.java](com/blackgear/vanillabackport/core/mixin/common/item/BundleItemMixin.java) |
| `ServerEntityMixin` | class | — | Mixin | 4 | 183 | [ServerEntityMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/ServerEntityMixin.java) |
| `LivingEntityMixin` | class | EntityMixin | Mixin | 2 | 109 | [LivingEntityMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/LivingEntityMixin.java) |
| `DeadBushBlockMixin` | class | BlockMixin | Mixin | 0 | 19 | [DeadBushBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/DeadBushBlockMixin.java) |
| `CactusBlockMixin` | class | Block | Mixin | 1 | 73 | [CactusBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/CactusBlockMixin.java) |
| `ColoringFallingBlockMixin` | class | Block | Mixin | 1 | 24 | [ColoringFallingBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/ColoringFallingBlockMixin.java) |
| `BlockMixin` | class | — | Mixin | 0 | 22 | [BlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/BlockMixin.java) |
| `LeavesBlockMixin` | class | — | Mixin、OnlyIn | 0 | 31 | [LeavesBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/LeavesBlockMixin.java) |
| `AbstractContainerScreenMixin` | class | Screen | Mixin | 0 | 72 | [AbstractContainerScreenMixin.java](com/blackgear/vanillabackport/core/mixin/client/AbstractContainerScreenMixin.java) |
| `MobRendererMixin` | class | — | Mixin | 0 | 34 | [MobRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/MobRendererMixin.java) |
| `CatRendererMixin` | class | MobRendererMixin | Mixin | 0 | 30 | [CatRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/CatRendererMixin.java) |
| `FrogRendererMixin` | class | MobRendererMixin | Mixin | 0 | 30 | [FrogRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/FrogRendererMixin.java) |
| `ChickenRendererMixin` | class | MobRendererMixin | Mixin | 2 | 56 | [ChickenRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/ChickenRendererMixin.java) |
| `SheepRendererMixin` | class | MobRendererMixin | Mixin | 1 | 29 | [SheepRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/SheepRendererMixin.java) |
| `PigRendererMixin` | class | MobRendererMixin | Mixin | 2 | 56 | [PigRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/PigRendererMixin.java) |
| `WolfRendererMixin` | class | MobRendererMixin | Mixin | 0 | 36 | [WolfRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/WolfRendererMixin.java) |
| `CowRendererMixin` | class | MobRendererMixin | Mixin | 2 | 56 | [CowRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/CowRendererMixin.java) |
| `FireworkRocketItemMixin` | class | — | Mixin | 0 | 33 | [FireworkRocketItemMixin.java](com/blackgear/vanillabackport/core/mixin/leash/FireworkRocketItemMixin.java) |
| `EntityAccessor` | interface | — | Mixin | 0 | 52 | [EntityAccessor.java](com/blackgear/vanillabackport/core/mixin/access/EntityAccessor.java) |
| `CreativeModeInventoryScreenAccessor` | interface | — | Mixin | 0 | 15 | [CreativeModeInventoryScreenAccessor.java](com/blackgear/vanillabackport/core/mixin/access/CreativeModeInventoryScreenAccessor.java) |
| `BlockRenderDispatcherAccessor` | interface | — | Mixin | 0 | 17 | [BlockRenderDispatcherAccessor.java](com/blackgear/vanillabackport/core/mixin/access/BlockRenderDispatcherAccessor.java) |
| `LivingEntityAccessor` | interface | — | Mixin | 0 | 22 | [LivingEntityAccessor.java](com/blackgear/vanillabackport/core/mixin/access/LivingEntityAccessor.java) |
| `PotentSulfurBlockEntity` | class | BlockEntity | — | 16 | 264 | [PotentSulfurBlockEntity.java](com/blackgear/vanillabackport/common/level/blockentities/PotentSulfurBlockEntity.java) |
| `CreakingHeartBlockEntity` | class | BlockEntity | — | 19 | 361 | [CreakingHeartBlockEntity.java](com/blackgear/vanillabackport/common/level/blockentities/CreakingHeartBlockEntity.java) |
| `AbstractCubeMob` | class | AgeableMob | — | 55 | 479 | [AbstractCubeMob.java](com/blackgear/vanillabackport/common/level/entities/sulfurcube/AbstractCubeMob.java) |
| `ModSoundEvents` | class | — | — | 0 | 152 | [ModSoundEvents.java](com/blackgear/vanillabackport/client/registries/ModSoundEvents.java) |
| `WolfMixin` | class | TamableAnimalMixin | Mixin | 6 | 158 | [WolfMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/WolfMixin.java) |
| `BiomeHelperMixin` | class | — | Mixin、Pseudo | 0 | 60 | [BiomeHelperMixin.java](com/blackgear/vanillabackport/core/mixin/compat/jer/BiomeHelperMixin.java) |
| `LeashDataMixin` | class | LeashDataAccess | Mixin | 0 | 23 | [LeashDataMixin.java](com/blackgear/vanillabackport/core/mixin/leash/LeashDataMixin.java) |
| `FeatureManager` | class | — | — | 11 | 84 | [FeatureManager.java](com/blackgear/vanillabackport/common/worldgen/generation/FeatureManager.java) |
| `MossyCarpetBlock` | class | Block | — | 22 | 294 | [MossyCarpetBlock.java](com/blackgear/vanillabackport/common/level/blocks/MossyCarpetBlock.java) |
| `VanillaWolfVariantRegistry` | class | — | — | 6 | 45 | [VanillaWolfVariantRegistry.java](com/blackgear/vanillabackport/common/api/variant/vanilla/VanillaWolfVariantRegistry.java) |
| `MusicFadeManager` | class | — | — | 11 | 134 | [MusicFadeManager.java](com/blackgear/vanillabackport/client/api/music/MusicFadeManager.java) |
| `BuildStateMixin` | class | — | Mixin | 0 | 15 | [BuildStateMixin.java](com/blackgear/vanillabackport/core/mixin/common/BuildStateMixin.java) |
| `NetherFossilPieceMixin` | class | TemplateStructurePiece | Mixin | 1 | 67 | [NetherFossilPieceMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/structure/NetherFossilPieceMixin.java) |
| `LakeFeatureMixin` | class | — | Mixin | 0 | 20 | [LakeFeatureMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/feature/LakeFeatureMixin.java) |
| `NoiseGeneratorSettingsMixin` | class | — | Mixin | 0 | 36 | [NoiseGeneratorSettingsMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/surface/NoiseGeneratorSettingsMixin.java) |
| `SurfaceContextMixin` | class | ContextNoiseSampler | Mixin | 3 | 61 | [SurfaceContextMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/surface/SurfaceContextMixin.java) |
| `ChunkSerializerMixin` | class | — | Mixin | 2 | 58 | [ChunkSerializerMixin.java](com/blackgear/vanillabackport/core/mixin/common/level/ChunkSerializerMixin.java) |
| `BundleContentsMixin` | class | IBundle | Mixin | 4 | 36 | [BundleContentsMixin.java](com/blackgear/vanillabackport/core/mixin/common/item/BundleContentsMixin.java) |
| `MutableBundleContentsMixin` | class | IBundle.Mutable | Mixin | 4 | 87 | [MutableBundleContentsMixin.java](com/blackgear/vanillabackport/core/mixin/common/item/MutableBundleContentsMixin.java) |
| `RangedAttributeMixin` | class | — | Mixin | 0 | 20 | [RangedAttributeMixin.java](com/blackgear/vanillabackport/core/mixin/common/attributes/RangedAttributeMixin.java) |
| `ServerPlayerMixin` | class | Player | Mixin | 1 | 29 | [ServerPlayerMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/ServerPlayerMixin.java) |
| `FrogMixin` | class | MobMixin | Mixin | 3 | 76 | [FrogMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/FrogMixin.java) |
| `SheepMixin` | class | MobMixin | Mixin | 0 | 32 | [SheepMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/SheepMixin.java) |
| `NaturalSpawnerMixin` | class | — | Mixin | 0 | 37 | [NaturalSpawnerMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/NaturalSpawnerMixin.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EmissiveModelHandler` | 2 | `com.blackgear.vanillabackport.core.neoforge.emissive` | [EmissiveModelHandler.java](com/blackgear/vanillabackport/core/neoforge/emissive/EmissiveModelHandler.java) |

## Mixin（71 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientBundleTooltipMixin` | `ClientBundleTooltip` | [ClientBundleTooltipMixin.java](com/blackgear/vanillabackport/core/mixin/client/ClientBundleTooltipMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/EntityMixin.java) |
| `MusicManagerMixin` | `MusicManager` | [MusicManagerMixin.java](com/blackgear/vanillabackport/core/mixin/client/MusicManagerMixin.java) |
| `SoundManagerAccessor` | `SoundManagerAccessor` | [SoundManagerAccessor.java](com/blackgear/vanillabackport/core/mixin/access/SoundManagerAccessor.java) |
| `EntityRendererMixin` | `EntityRenderer` | [EntityRendererMixin.java](com/blackgear/vanillabackport/core/mixin/leash/EntityRendererMixin.java) |
| `EntityRendererAccessor` | `EntityRendererAccessor` | [EntityRendererAccessor.java](com/blackgear/vanillabackport/core/mixin/access/EntityRendererAccessor.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](com/blackgear/vanillabackport/core/mixin/common/ServerGamePacketListenerImplMixin.java) |
| `SimpleBlockFeatureMixin` | `SimpleBlockFeature` | [SimpleBlockFeatureMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/feature/SimpleBlockFeatureMixin.java) |
| `BundleItemMixin` | `BundleItem` | [BundleItemMixin.java](com/blackgear/vanillabackport/core/mixin/common/item/BundleItemMixin.java) |
| `ServerEntityMixin` | `ServerEntity` | [ServerEntityMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/ServerEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/LivingEntityMixin.java) |
| `DeadBushBlockMixin` | `DeadBushBlock` | [DeadBushBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/DeadBushBlockMixin.java) |
| `CactusBlockMixin` | `CactusBlock` | [CactusBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/CactusBlockMixin.java) |
| `ColoringFallingBlockMixin` | `ColoringFallingBlock` | [ColoringFallingBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/ColoringFallingBlockMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/BlockMixin.java) |
| `LeavesBlockMixin` | `LeavesBlock` | [LeavesBlockMixin.java](com/blackgear/vanillabackport/core/mixin/common/blocks/LeavesBlockMixin.java) |
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](com/blackgear/vanillabackport/core/mixin/client/AbstractContainerScreenMixin.java) |
| `MobRendererMixin` | `MobRenderer` | [MobRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/MobRendererMixin.java) |
| `CatRendererMixin` | `CatRenderer` | [CatRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/CatRendererMixin.java) |
| `FrogRendererMixin` | `FrogRenderer` | [FrogRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/FrogRendererMixin.java) |
| `ChickenRendererMixin` | `ChickenRenderer` | [ChickenRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/ChickenRendererMixin.java) |
| `SheepRendererMixin` | `SheepRenderer` | [SheepRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/SheepRendererMixin.java) |
| `PigRendererMixin` | `PigRenderer` | [PigRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/PigRendererMixin.java) |
| `WolfRendererMixin` | `WolfRenderer` | [WolfRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/WolfRendererMixin.java) |
| `CowRendererMixin` | `CowRenderer` | [CowRendererMixin.java](com/blackgear/vanillabackport/core/mixin/client/entities/renderer/CowRendererMixin.java) |
| `FireworkRocketItemMixin` | `FireworkRocketItem` | [FireworkRocketItemMixin.java](com/blackgear/vanillabackport/core/mixin/leash/FireworkRocketItemMixin.java) |
| `EntityAccessor` | `EntityAccessor` | [EntityAccessor.java](com/blackgear/vanillabackport/core/mixin/access/EntityAccessor.java) |
| `CreativeModeInventoryScreenAccessor` | `CreativeModeInventoryScreenAccessor` | [CreativeModeInventoryScreenAccessor.java](com/blackgear/vanillabackport/core/mixin/access/CreativeModeInventoryScreenAccessor.java) |
| `BlockRenderDispatcherAccessor` | `BlockRenderDispatcherAccessor` | [BlockRenderDispatcherAccessor.java](com/blackgear/vanillabackport/core/mixin/access/BlockRenderDispatcherAccessor.java) |
| `LivingEntityAccessor` | `LivingEntityAccessor` | [LivingEntityAccessor.java](com/blackgear/vanillabackport/core/mixin/access/LivingEntityAccessor.java) |
| `WolfMixin` | `Wolf` | [WolfMixin.java](com/blackgear/vanillabackport/core/mixin/common/entities/WolfMixin.java) |
| `BiomeHelperMixin` | `BiomeHelper` | [BiomeHelperMixin.java](com/blackgear/vanillabackport/core/mixin/compat/jer/BiomeHelperMixin.java) |
| `LeashDataMixin` | `LeashData` | [LeashDataMixin.java](com/blackgear/vanillabackport/core/mixin/leash/LeashDataMixin.java) |
| `BuildStateMixin` | `BuildState` | [BuildStateMixin.java](com/blackgear/vanillabackport/core/mixin/common/BuildStateMixin.java) |
| `NetherFossilPieceMixin` | `NetherFossilPiece` | [NetherFossilPieceMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/structure/NetherFossilPieceMixin.java) |
| `LakeFeatureMixin` | `LakeFeature` | [LakeFeatureMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/feature/LakeFeatureMixin.java) |
| `NoiseGeneratorSettingsMixin` | `NoiseGeneratorSettings` | [NoiseGeneratorSettingsMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/surface/NoiseGeneratorSettingsMixin.java) |
| `SurfaceContextMixin` | `SurfaceContext` | [SurfaceContextMixin.java](com/blackgear/vanillabackport/core/mixin/common/worldgen/surface/SurfaceContextMixin.java) |
| `ChunkSerializerMixin` | `ChunkSerializer` | [ChunkSerializerMixin.java](com/blackgear/vanillabackport/core/mixin/common/level/ChunkSerializerMixin.java) |
| `BundleContentsMixin` | `BundleContents` | [BundleContentsMixin.java](com/blackgear/vanillabackport/core/mixin/common/item/BundleContentsMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ClientConfig` | — | [ClientConfig.java](com/blackgear/vanillabackport/client/ClientConfig.java) |
| `CommonConfig` | — | [CommonConfig.java](com/blackgear/vanillabackport/common/CommonConfig.java) |
| `WeightedRandomFeatureConfiguration` | — | [WeightedRandomFeatureConfiguration.java](com/blackgear/vanillabackport/common/level/features/WeightedRandomFeatureConfiguration.java) |
| `FallenTreeConfiguration` | — | [FallenTreeConfiguration.java](com/blackgear/vanillabackport/common/level/features/FallenTreeConfiguration.java) |
| `SpeleothemClusterConfiguration` | — | [SpeleothemClusterConfiguration.java](com/blackgear/vanillabackport/common/level/features/SpeleothemClusterConfiguration.java) |
| `TemplateFeatureConfiguration` | — | [TemplateFeatureConfiguration.java](com/blackgear/vanillabackport/common/level/features/TemplateFeatureConfiguration.java) |
| `SpeleothemConfiguration` | — | [SpeleothemConfiguration.java](com/blackgear/vanillabackport/common/level/features/SpeleothemConfiguration.java) |

## 按包浏览

- `architectury_inject_vanillabackport_common_7717ee3da841444dad65928487ac94b0_75047c0d1025ad505517c2b326b6fc9111098618235191cc70eac60573bdf4f2VanillaBackportcommon12111174devjar`（1 类）
- `com.blackgear.vanillabackport.client`（5 类）
- `com.blackgear.vanillabackport.client.api.bundle`（3 类）
- `com.blackgear.vanillabackport.client.api.color`（2 类）
- `com.blackgear.vanillabackport.client.api.music`（1 类）
- `com.blackgear.vanillabackport.client.api.renderer`（6 类）
- `com.blackgear.vanillabackport.client.api.tabs`（2 类）
- `com.blackgear.vanillabackport.client.level.entities.animation`（1 类）
- `com.blackgear.vanillabackport.client.level.entities.layer`（7 类）
- `com.blackgear.vanillabackport.client.level.entities.model`（5 类）
- `com.blackgear.vanillabackport.client.level.entities.model.chicken`（1 类）
- `com.blackgear.vanillabackport.client.level.entities.model.cow`（3 类）
- `com.blackgear.vanillabackport.client.level.entities.model.pig`（1 类）
- `com.blackgear.vanillabackport.client.level.entities.renderer`（4 类）
- `com.blackgear.vanillabackport.client.level.item`（2 类）
- `com.blackgear.vanillabackport.client.level.particles`（10 类）
- `com.blackgear.vanillabackport.client.level.particles.particleoptions`（3 类）
- `com.blackgear.vanillabackport.client.level.sound`（2 类）
- `com.blackgear.vanillabackport.client.registries`（6 类）
- `com.blackgear.vanillabackport.client.resources.color`（2 类）
- `com.blackgear.vanillabackport.client.util`（2 类）
- `com.blackgear.vanillabackport.common`（3 类）
- `com.blackgear.vanillabackport.common.api`（1 类）
- `com.blackgear.vanillabackport.common.api.block`（1 类）
- `com.blackgear.vanillabackport.common.api.bundle`（2 类）
- `com.blackgear.vanillabackport.common.api.extensions`（3 类）
- `com.blackgear.vanillabackport.common.api.interactions`（1 类）
- `com.blackgear.vanillabackport.common.api.leash`（9 类）
- `com.blackgear.vanillabackport.common.api.variant`（5 类）
- `com.blackgear.vanillabackport.common.api.variant.spawn`（5 类）
- `com.blackgear.vanillabackport.common.api.variant.spawn.check`（3 类）
- `com.blackgear.vanillabackport.common.api.variant.spawn.check.raw`（2 类）
- `com.blackgear.vanillabackport.common.api.variant.vanilla`（3 类）
- `com.blackgear.vanillabackport.common.api.wolf`（3 类）
- `com.blackgear.vanillabackport.common.api.worldgen`（1 类）
- `com.blackgear.vanillabackport.common.level.blockentities`（2 类）
- `com.blackgear.vanillabackport.common.level.blocks`（17 类）
- `com.blackgear.vanillabackport.common.level.blocks.properties`（1 类）
- `com.blackgear.vanillabackport.common.level.blocks.states`（2 类）
- `com.blackgear.vanillabackport.common.level.boat`（3 类）
- `com.blackgear.vanillabackport.common.level.crafting`（1 类）
- `com.blackgear.vanillabackport.common.level.dispenser`（1 类）
- `com.blackgear.vanillabackport.common.level.entities.animal`（13 类）
- `com.blackgear.vanillabackport.common.level.entities.animal.modules`（1 类）
- `com.blackgear.vanillabackport.common.level.entities.creaking`（3 类）
- `com.blackgear.vanillabackport.common.level.entities.happyghast`（4 类）
- `com.blackgear.vanillabackport.common.level.entities.sensortypes`（1 类）
- `com.blackgear.vanillabackport.common.level.entities.sulfurcube`（4 类）
- `com.blackgear.vanillabackport.common.level.features`（14 类）
- `com.blackgear.vanillabackport.common.level.items`（3 类）
- `com.blackgear.vanillabackport.common.registries`（22 类）
- `com.blackgear.vanillabackport.common.resource`（1 类）
- `com.blackgear.vanillabackport.common.resource.sound`（1 类）
- `com.blackgear.vanillabackport.common.resource.variant`（6 类）
- `com.blackgear.vanillabackport.common.worldgen`（3 类）
- `com.blackgear.vanillabackport.common.worldgen.biomes`（2 类）
- `com.blackgear.vanillabackport.common.worldgen.biomes.terrablender`（1 类）
- `com.blackgear.vanillabackport.common.worldgen.features`（3 类）
- `com.blackgear.vanillabackport.common.worldgen.generation`（3 类）
- `com.blackgear.vanillabackport.common.worldgen.placements`（3 类）
- `com.blackgear.vanillabackport.common.worldgen.surface`（2 类）
- `com.blackgear.vanillabackport.common.worldgen.treedecorators`（4 类）
- `com.blackgear.vanillabackport.core`（3 类）
- `com.blackgear.vanillabackport.core.data.tags`（5 类）
- `com.blackgear.vanillabackport.core.data.tags.create`（1 类）
- `com.blackgear.vanillabackport.core.data.tags.fabric`（3 类）
- `com.blackgear.vanillabackport.core.data.tags.forge`（1 类）
- `com.blackgear.vanillabackport.core.mixin.access`（17 类）
- `com.blackgear.vanillabackport.core.mixin.client`（6 类）
- `com.blackgear.vanillabackport.core.mixin.client.entities.renderer`（8 类）
- `com.blackgear.vanillabackport.core.mixin.common`（2 类）
- `com.blackgear.vanillabackport.core.mixin.common.attributes`（1 类）
- `com.blackgear.vanillabackport.core.mixin.common.blocks`（5 类）
- `com.blackgear.vanillabackport.core.mixin.common.entities`（16 类）
- `com.blackgear.vanillabackport.core.mixin.common.item`（3 类）
- `com.blackgear.vanillabackport.core.mixin.common.level`（1 类）
- `com.blackgear.vanillabackport.core.mixin.common.worldgen.feature`（2 类）
- `com.blackgear.vanillabackport.core.mixin.common.worldgen.structure`（1 类）
- `com.blackgear.vanillabackport.core.mixin.common.worldgen.surface`（2 类）
- `com.blackgear.vanillabackport.core.mixin.compat.jer`（1 类）
- `com.blackgear.vanillabackport.core.mixin.compat.mousetweaks`（1 类）
- `com.blackgear.vanillabackport.core.mixin.leash`（5 类）
- `com.blackgear.vanillabackport.core.neoforge`（2 类）
- `com.blackgear.vanillabackport.core.neoforge.compat`（1 类）
- `com.blackgear.vanillabackport.core.neoforge.emissive`（2 类）
- `com.blackgear.vanillabackport.core.network`（1 类）
- `com.blackgear.vanillabackport.core.registries`（3 类）
- `com.blackgear.vanillabackport.core.util`（10 类）
- `com.blackgear.vanillabackport.core.util.valueproviders`（1 类）
