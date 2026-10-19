# Eternal Starlight（`eternal_starlight`）

> jar：`[永恒星光]eternalstarlight.jar` ｜ 版本：`0.8.1+1.21.1+neoforge` ｜ 作者：LeoLezury
> 规模：**1062 个类 / 137 个包 / 133921 行** ｜ 事件处理类 6 ｜ Mixin 68 ｜ 注册/管理类 20

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `cn.leolezury.eternalstarlight.common.block` | 130 | `AlloyFurnaceBlock`、`LootChestBlock`、`CarvedLunarisCactusFruitBlock` |
| `cn.leolezury.eternalstarlight.common.client.renderer.entity` | 68 | `ESFallingBlockRenderer`、`TheGatekeeperRenderer`、`WhipRenderer` |
| `cn.leolezury.eternalstarlight.common.item.combat` | 53 | `SpearItem`、`SeedsLauncherItem`、`ScytheItem` |
| `cn.leolezury.eternalstarlight.common.client.model.entity` | 43 | `TheGatekeeperModel`、`PermafrostModel`、`LunarMonstrosityModel` |
| `cn.leolezury.eternalstarlight.common.mixin` | 39 | `LivingEntityMixin`、`ItemEntityMixin`、`EntityMixin` |
| `cn.leolezury.eternalstarlight.common.registry` | 36 | `ESSoundEvents`、`ESParticles`、`ESBlocks` |
| `cn.leolezury.eternalstarlight.common.entity.projectile` | 31 | `ChainOfSouls`、`AethersentMeteor`、`AetherstrikeRocketEntity` |
| `cn.leolezury.eternalstarlight.common.block.entity` | 29 | `LootChestBlockEntity`、`StarfireBirdNestBlockEntity`、`AlloyFurnaceBlockEntity` |
| `cn.leolezury.eternalstarlight.common.client.model.animation.definition` | 26 | `StarlightGolemAnimation`、`TheGatekeeperAnimation`、`LunarMonstrosityAnimation` |
| `cn.leolezury.eternalstarlight.common.data` | 25 | `ESConfiguredFeatures`、`ESBiomes`、`ESBiomeBuilder` |
| `cn.leolezury.eternalstarlight.common.util` | 24 | `FastNoise`、`Color`、`ESMathUtil` |
| `cn.leolezury.eternalstarlight.common.client.particle.effect` | 24 | `EnergyParticle`、`ESGlowParticle`、`StarfireParticle` |
| `cn.leolezury.eternalstarlight.common.mixin.client` | 21 | `ItemInHandRendererMixin`、`ClientRecipeBookMixin`、`LivingEntityRendererMixin` |
| `cn.leolezury.eternalstarlight.common.world.gen.feature` | 21 | `BlockPatchFeature`、`UnderwaterSimpleBlockFeature`、`IcicleFeature` |
| `cn.leolezury.eternalstarlight.common.client.renderer.layer` | 18 | `NightfallSpiderEyesLayer`、`StarlightGolemGlowLayer`、`AstralGolemArmorLayer` |
| `cn.leolezury.eternalstarlight.common.network` | 17 | `TriggerEntityEventPacket`、`ClientMountPacket`、`ClientDismountPacket` |
| `cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper` | 16 | `TheGatekeeper`、`GatekeeperEatPhase`、`GatekeeperStepBackPhase` |
| `cn.leolezury.eternalstarlight.common.entity.living.animal` | 15 | `StarfireBird`、`CrystallizedMoth`、`Yeti` |
| `cn.leolezury.eternalstarlight.common.entity.living.goal` | 15 | `BoarwarfChatGoal`、`ChargeAttackGoal`、`BoarwarfMoveToBedGoal` |
| `cn.leolezury.eternalstarlight.common.item.misc` | 14 | `LootBagItem`、`ContainerBlockItem`、`GalacticQuiverItem` |
| `cn.leolezury.eternalstarlight.common.spell` | 14 | `AbstractSpell`、`SpellCastData`、`MergedFireballSpell` |
| `cn.leolezury.eternalstarlight.common.entity.living.monster` | 13 | `Stranghoul`、`TangledSkull`、`ThirstWalker` |
| `cn.leolezury.eternalstarlight.common.entity.living.boss.golem` | 13 | `StarlightGolem`、`Permafrost`、`StarlightGolemChargePhase` |
| `cn.leolezury.eternalstarlight.common.client.renderer.blockentity` | 13 | `LootChestRenderer`、`LunarVineRenderer`、`SolarEggRenderer` |
| `cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags` | 12 | `ESItemTagsProvider`、`ESBlockTagsProvider`、`ESEntityTypeTagsProvider` |
| `cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity` | 10 | `LunarMonstrosity`、`LunarMonstrositySporePhase`、`LunarMonstrosityBitePhase` |
| `cn.leolezury.eternalstarlight.common.entity.attack` | 10 | `Whip`、`TangledHusk`、`CrystalCluster` |
| `cn.leolezury.eternalstarlight.common.particle` | 10 | `GeyserParticleOptions`、`GatheringTrailParticleOptions`、`ESSmokeParticleOptions` |
| `cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot` | 8 | `ESBlockLootSubProvider`、`ESEntityLootSubProvider`、`ESBossLootSubProvider` |
| `cn.leolezury.eternalstarlight.common.item.recipe` | 8 | `AlloyRecipe`、`AlloyRecipeBuilder`、`DryingRecipe` |
| `cn.leolezury.eternalstarlight.common.entity.misc` | 8 | `CrestEntity`、`ESChestBoat`、`ESFallingBlock` |
| `cn.leolezury.eternalstarlight.common.client.book.component` | 8 | `BookComponentRegistry`、`ConfiguredBookComponent`、`DisplayBookComponent` |
| `cn.leolezury.eternalstarlight.common.item.menu` | 7 | `CrystalbornCatalystMenu`、`AlloyFurnaceMenu`、`CrateMenu` |
| `cn.leolezury.eternalstarlight.common.item.armor` | 7 | `ThermalSpringstoneArmorItem`、`DeepsilverArmorItem`、`AethersentArmorItem` |
| `cn.leolezury.eternalstarlight.common.world.gen.feature.tree` | 7 | `BouldershroomFeature`、`JinglestemFeature`、`HugeShiningMushroomFeature` |
| `cn.leolezury.eternalstarlight.common.client.gui.screen` | 6 | `BookScreen`、`CrestSelectionScreen`、`GatekeeperDialogueScreen` |
| `cn.leolezury.eternalstarlight.common.client.gui.screen.widget` | 6 | `CrestButton`、`NpcDialogueTextWidget`、`BookHistoryButton` |
| `cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator` | 6 | `TrunkBerriesDecorator`、`StarfireBirdNestDecorator`、`TrunkCobwebDecorator` |
| `cn.leolezury.eternalstarlight.common.world.gen.valuemap` | 6 | `ValueMapProvider`、`CylinderProvider`、`SpikeProvider` |
| `cn.leolezury.eternalstarlight.neoforge.datagen.provider` | 5 | `ESRecipeProvider`、`ESRegistryProvider`、`ESSoundProvider` |
| … | | 其余 97 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientSetupEvents` | class | — | EventBusSubscriber、OnlyIn | 27 | 354 | [ClientSetupEvents.java](cn/leolezury/eternalstarlight/neoforge/client/event/ClientSetupEvents.java) |
| `CommonEvents` | class | — | EventBusSubscriber | 30 | 268 | [CommonEvents.java](cn/leolezury/eternalstarlight/neoforge/event/CommonEvents.java) |
| `ClientEvents` | class | — | EventBusSubscriber、OnlyIn | 7 | 88 | [ClientEvents.java](cn/leolezury/eternalstarlight/neoforge/client/event/ClientEvents.java) |
| `ESNeoNetworkHandler` | class | — | EventBusSubscriber | 4 | 40 | [ESNeoNetworkHandler.java](cn/leolezury/eternalstarlight/neoforge/network/ESNeoNetworkHandler.java) |
| `CommonSetupEvents` | class | — | EventBusSubscriber | 4 | 69 | [CommonSetupEvents.java](cn/leolezury/eternalstarlight/neoforge/event/CommonSetupEvents.java) |
| `ESClientHandler` | class | — | — | 36 | 985 | [ESClientHandler.java](cn/leolezury/eternalstarlight/common/client/handler/ESClientHandler.java) |
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 66 | [DataGenerators.java](cn/leolezury/eternalstarlight/neoforge/datagen/DataGenerators.java) |
| `ESRecipeProvider` | class | RecipeProvider | — | 74 | 4439 | [ESRecipeProvider.java](cn/leolezury/eternalstarlight/neoforge/datagen/provider/ESRecipeProvider.java) |
| `ESCommonHandler` | class | — | — | 29 | 1249 | [ESCommonHandler.java](cn/leolezury/eternalstarlight/common/handler/ESCommonHandler.java) |
| `ESClientSetupHandler` | class | — | — | 17 | 1534 | [ESClientSetupHandler.java](cn/leolezury/eternalstarlight/common/client/handler/ESClientSetupHandler.java) |
| `LivingEntityMixin` | class | — | Mixin | 27 | 397 | [LivingEntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/LivingEntityMixin.java) |
| `ESBlockLootSubProvider` | class | BlockLootSubProvider | — | 17 | 1601 | [ESBlockLootSubProvider.java](cn/leolezury/eternalstarlight/neoforge/datagen/provider/loot/ESBlockLootSubProvider.java) |
| `ESItemModelProvider` | class | ItemModelProvider | — | 36 | 1340 | [ESItemModelProvider.java](cn/leolezury/eternalstarlight/neoforge/datagen/provider/model/ESItemModelProvider.java) |
| `ESBlockStateProvider` | class | BlockStateProvider | — | 87 | 3080 | [ESBlockStateProvider.java](cn/leolezury/eternalstarlight/neoforge/datagen/provider/model/ESBlockStateProvider.java) |
| `ItemInHandRendererMixin` | class | — | Mixin | 11 | 280 | [ItemInHandRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ItemInHandRendererMixin.java) |
| `IBlockEntityRendererExtensionMixin` | interface | — | Mixin | 0 | 35 | [IBlockEntityRendererExtensionMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/client/IBlockEntityRendererExtensionMixin.java) |
| `LootChestBlockEntity` | class | BlockEntity | — | 21 | 389 | [LootChestBlockEntity.java](cn/leolezury/eternalstarlight/common/block/entity/LootChestBlockEntity.java) |
| `StarfireBirdNestBlockEntity` | class | SimpleContainerBlockEntity | — | 40 | 495 | [StarfireBirdNestBlockEntity.java](cn/leolezury/eternalstarlight/common/block/entity/StarfireBirdNestBlockEntity.java) |
| `ClientRecipeBookMixin` | class | — | Mixin | 1 | 27 | [ClientRecipeBookMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ClientRecipeBookMixin.java) |
| `Stranghoul` | class | Monster | — | 98 | 1334 | [Stranghoul.java](cn/leolezury/eternalstarlight/common/entity/living/monster/Stranghoul.java) |
| `AlloyFurnaceBlockEntity` | class | BaseContainerBlockEntity | — | 26 | 490 | [AlloyFurnaceBlockEntity.java](cn/leolezury/eternalstarlight/common/block/entity/AlloyFurnaceBlockEntity.java) |
| `ItemEntityMixin` | class | — | Mixin | 5 | 94 | [ItemEntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/ItemEntityMixin.java) |
| `LivingEntityRendererMixin` | class | — | Mixin | 5 | 97 | [LivingEntityRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/LivingEntityRendererMixin.java) |
| `MusicManagerMixin` | class | — | Mixin | 2 | 23 | [MusicManagerMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/MusicManagerMixin.java) |
| `FastNoise` | class | — | — | 71 | 3904 | [FastNoise.java](cn/leolezury/eternalstarlight/common/util/FastNoise.java) |
| `ESNeoEntrypoint` | class | — | Mod | 2 | 46 | [ESNeoEntrypoint.java](cn/leolezury/eternalstarlight/neoforge/ESNeoEntrypoint.java) |
| `ESCommonSetupHandler` | class | — | — | 15 | 485 | [ESCommonSetupHandler.java](cn/leolezury/eternalstarlight/common/handler/ESCommonSetupHandler.java) |
| `TheGatekeeper` | class | ESBoss | — | 64 | 990 | [TheGatekeeper.java](cn/leolezury/eternalstarlight/common/entity/living/boss/gatekeeper/TheGatekeeper.java) |
| `EntityMixin` | class | — | Mixin | 12 | 160 | [EntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/EntityMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 11 | 139 | [ItemStackMixin.java](cn/leolezury/eternalstarlight/common/mixin/ItemStackMixin.java) |
| `PlayerMixin` | class | SpellCaster | Mixin | 17 | 239 | [PlayerMixin.java](cn/leolezury/eternalstarlight/common/mixin/PlayerMixin.java) |
| `ScreenEffectRendererMixin` | class | — | Mixin | 2 | 78 | [ScreenEffectRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ScreenEffectRendererMixin.java) |
| `SkullBlockRendererMixin` | class | — | Mixin | 1 | 34 | [SkullBlockRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/SkullBlockRendererMixin.java) |
| `ItemRendererMixin` | class | — | Mixin | 1 | 39 | [ItemRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ItemRendererMixin.java) |
| `BookScreen` | class | Screen | — | 30 | 459 | [BookScreen.java](cn/leolezury/eternalstarlight/common/client/gui/screen/BookScreen.java) |
| `StarfireBird` | class | Animal | — | 69 | 712 | [StarfireBird.java](cn/leolezury/eternalstarlight/common/entity/living/animal/StarfireBird.java) |
| `StarlightGolem` | class | ESBoss | — | 36 | 606 | [StarlightGolem.java](cn/leolezury/eternalstarlight/common/entity/living/boss/golem/StarlightGolem.java) |
| `AlloyFurnaceBlock` | class | BaseEntityBlock | — | 28 | 460 | [AlloyFurnaceBlock.java](cn/leolezury/eternalstarlight/common/block/AlloyFurnaceBlock.java) |
| `ESConfiguredFeatures` | class | — | — | 3 | 1553 | [ESConfiguredFeatures.java](cn/leolezury/eternalstarlight/common/data/ESConfiguredFeatures.java) |
| `ESBiomes` | class | — | — | 39 | 671 | [ESBiomes.java](cn/leolezury/eternalstarlight/common/data/ESBiomes.java) |
| `AxeItemMixin` | class | — | Mixin | 1 | 35 | [AxeItemMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/AxeItemMixin.java) |
| `ExtendedBlockMixin` | interface | — | Mixin | 0 | 25 | [ExtendedBlockMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/ExtendedBlockMixin.java) |
| `ProjectileWeaponItemMixin` | class | — | Mixin | 1 | 27 | [ProjectileWeaponItemMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/ProjectileWeaponItemMixin.java) |
| `ESNeoPlatform` | class | ESPlatform | AutoService | 49 | 452 | [ESNeoPlatform.java](cn/leolezury/eternalstarlight/neoforge/platform/ESNeoPlatform.java) |
| `TriggerEntityEventPacket` | record | CustomPacketPayload | — | 2 | 29 | [TriggerEntityEventPacket.java](cn/leolezury/eternalstarlight/common/network/TriggerEntityEventPacket.java) |
| `LunarMonstrosity` | class | ESBoss | — | 33 | 546 | [LunarMonstrosity.java](cn/leolezury/eternalstarlight/common/entity/living/boss/monstrosity/LunarMonstrosity.java) |
| `Boarwarf` | class | PathfinderMob | — | 44 | 491 | [Boarwarf.java](cn/leolezury/eternalstarlight/common/entity/living/npc/boarwarf/Boarwarf.java) |
| `ChainOfSouls` | class | Projectile | — | 32 | 402 | [ChainOfSouls.java](cn/leolezury/eternalstarlight/common/entity/projectile/ChainOfSouls.java) |
| `CrystalbornCatalystBlockEntity` | class | BaseContainerBlockEntity | — | 18 | 294 | [CrystalbornCatalystBlockEntity.java](cn/leolezury/eternalstarlight/common/block/entity/CrystalbornCatalystBlockEntity.java) |
| `FarmBlockMixin` | class | — | Mixin | 2 | 47 | [FarmBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/FarmBlockMixin.java) |
| `BubbleColumnBlockMixin` | class | — | Mixin | 2 | 49 | [BubbleColumnBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/BubbleColumnBlockMixin.java) |
| `SpawnEggItemMixin` | class | — | Mixin | 1 | 21 | [SpawnEggItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/SpawnEggItemMixin.java) |
| `AxeItemMixin` | class | — | Mixin | 1 | 37 | [AxeItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/AxeItemMixin.java) |
| `FrostedIceBlockMixin` | class | — | Mixin | 1 | 29 | [FrostedIceBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/FrostedIceBlockMixin.java) |
| `ServerEntityMixin` | class | — | Mixin | 1 | 45 | [ServerEntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/ServerEntityMixin.java) |
| `EntityFlagsPredicateMixin` | class | — | Mixin | 1 | 21 | [EntityFlagsPredicateMixin.java](cn/leolezury/eternalstarlight/common/mixin/EntityFlagsPredicateMixin.java) |
| `SignBlockMixin` | class | — | Mixin | 1 | 27 | [SignBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/SignBlockMixin.java) |
| `WallHangingSignBlockMixin` | class | — | Mixin | 1 | 27 | [WallHangingSignBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/WallHangingSignBlockMixin.java) |
| `ItemMixin` | class | — | Mixin | 2 | 47 | [ItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/ItemMixin.java) |
| `AbstractSkullBlockMixin` | class | — | Mixin | 1 | 27 | [AbstractSkullBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/AbstractSkullBlockMixin.java) |

## 事件处理（`@SubscribeEvent`，6 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CommonEvents` | 26 | `cn.leolezury.eternalstarlight.neoforge.event` | [CommonEvents.java](cn/leolezury/eternalstarlight/neoforge/event/CommonEvents.java) |
| `ClientSetupEvents` | 18 | `cn.leolezury.eternalstarlight.neoforge.client.event` | [ClientSetupEvents.java](cn/leolezury/eternalstarlight/neoforge/client/event/ClientSetupEvents.java) |
| `ClientEvents` | 7 | `cn.leolezury.eternalstarlight.neoforge.client.event` | [ClientEvents.java](cn/leolezury/eternalstarlight/neoforge/client/event/ClientEvents.java) |
| `CommonSetupEvents` | 3 | `cn.leolezury.eternalstarlight.neoforge.event` | [CommonSetupEvents.java](cn/leolezury/eternalstarlight/neoforge/event/CommonSetupEvents.java) |
| `ESNeoNetworkHandler` | 1 | `cn.leolezury.eternalstarlight.neoforge.network` | [ESNeoNetworkHandler.java](cn/leolezury/eternalstarlight/neoforge/network/ESNeoNetworkHandler.java) |
| `DataGenerators` | 1 | `cn.leolezury.eternalstarlight.neoforge.datagen` | [DataGenerators.java](cn/leolezury/eternalstarlight/neoforge/datagen/DataGenerators.java) |

## Mixin（68 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/LivingEntityMixin.java) |
| `ItemInHandRendererMixin` | `ItemInHandRenderer` | [ItemInHandRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ItemInHandRendererMixin.java) |
| `IBlockEntityRendererExtensionMixin` | `IBlockEntityRendererExtension` | [IBlockEntityRendererExtensionMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/client/IBlockEntityRendererExtensionMixin.java) |
| `ClientRecipeBookMixin` | `ClientRecipeBook` | [ClientRecipeBookMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ClientRecipeBookMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/ItemEntityMixin.java) |
| `LivingEntityRendererMixin` | `LivingEntityRenderer` | [LivingEntityRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/LivingEntityRendererMixin.java) |
| `MusicManagerMixin` | `MusicManager` | [MusicManagerMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/MusicManagerMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/EntityMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](cn/leolezury/eternalstarlight/common/mixin/ItemStackMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](cn/leolezury/eternalstarlight/common/mixin/PlayerMixin.java) |
| `ScreenEffectRendererMixin` | `ScreenEffectRenderer` | [ScreenEffectRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ScreenEffectRendererMixin.java) |
| `SkullBlockRendererMixin` | `SkullBlockRenderer` | [SkullBlockRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/SkullBlockRendererMixin.java) |
| `ItemRendererMixin` | `ItemRenderer` | [ItemRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ItemRendererMixin.java) |
| `AxeItemMixin` | `AxeItem` | [AxeItemMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/AxeItemMixin.java) |
| `ExtendedBlockMixin` | `ExtendedBlock` | [ExtendedBlockMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/ExtendedBlockMixin.java) |
| `ProjectileWeaponItemMixin` | `ProjectileWeaponItem` | [ProjectileWeaponItemMixin.java](cn/leolezury/eternalstarlight/neoforge/mixin/ProjectileWeaponItemMixin.java) |
| `FarmBlockMixin` | `FarmBlock` | [FarmBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/FarmBlockMixin.java) |
| `BubbleColumnBlockMixin` | `BubbleColumnBlock` | [BubbleColumnBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/BubbleColumnBlockMixin.java) |
| `SpawnEggItemMixin` | `SpawnEggItem` | [SpawnEggItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/SpawnEggItemMixin.java) |
| `AxeItemMixin` | `AxeItem` | [AxeItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/AxeItemMixin.java) |
| `FrostedIceBlockMixin` | `FrostedIceBlock` | [FrostedIceBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/FrostedIceBlockMixin.java) |
| `ServerEntityMixin` | `ServerEntity` | [ServerEntityMixin.java](cn/leolezury/eternalstarlight/common/mixin/ServerEntityMixin.java) |
| `EntityFlagsPredicateMixin` | `EntityFlagsPredicate` | [EntityFlagsPredicateMixin.java](cn/leolezury/eternalstarlight/common/mixin/EntityFlagsPredicateMixin.java) |
| `SignBlockMixin` | `SignBlock` | [SignBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/SignBlockMixin.java) |
| `WallHangingSignBlockMixin` | `WallHangingSignBlock` | [WallHangingSignBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/WallHangingSignBlockMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/ItemMixin.java) |
| `AbstractSkullBlockMixin` | `AbstractSkullBlock` | [AbstractSkullBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/AbstractSkullBlockMixin.java) |
| `BlockStateBaseMixin` | `BlockStateBase` | [BlockStateBaseMixin.java](cn/leolezury/eternalstarlight/common/mixin/BlockStateBaseMixin.java) |
| `BaseFireBlockMixin` | `BaseFireBlock` | [BaseFireBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/BaseFireBlockMixin.java) |
| `TntBlockMixin` | `TntBlock` | [TntBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/TntBlockMixin.java) |
| `DiggerItemMixin` | `DiggerItem` | [DiggerItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/DiggerItemMixin.java) |
| `DirtPathBlockMixin` | `DirtPathBlock` | [DirtPathBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/DirtPathBlockMixin.java) |
| `CropBlockMixin` | `CropBlock` | [CropBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/CropBlockMixin.java) |
| `CeilingHangingSignBlockMixin` | `CeilingHangingSignBlock` | [CeilingHangingSignBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/CeilingHangingSignBlockMixin.java) |
| `ProjectileWeaponItemMixin` | `ProjectileWeaponItem` | [ProjectileWeaponItemMixin.java](cn/leolezury/eternalstarlight/common/mixin/ProjectileWeaponItemMixin.java) |
| `IceBlockMixin` | `IceBlock` | [IceBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/IceBlockMixin.java) |
| `CoralBlockMixin` | `CoralBlock` | [CoralBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/CoralBlockMixin.java) |
| `FireBlockMixin` | `FireBlock` | [FireBlockMixin.java](cn/leolezury/eternalstarlight/common/mixin/FireBlockMixin.java) |
| `PlayerRendererMixin` | `PlayerRenderer` | [PlayerRendererMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/PlayerRendererMixin.java) |
| `ItemInHandLayerMixin` | `ItemInHandLayer` | [ItemInHandLayerMixin.java](cn/leolezury/eternalstarlight/common/mixin/client/ItemInHandLayerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ESConfiguredFeatures` | — | [ESConfiguredFeatures.java](cn/leolezury/eternalstarlight/common/data/ESConfiguredFeatures.java) |
| `ESConfig` | — | [ESConfig.java](cn/leolezury/eternalstarlight/common/config/ESConfig.java) |
| `ConfiguredBookComponent` | — | [ConfiguredBookComponent.java](cn/leolezury/eternalstarlight/common/client/book/component/ConfiguredBookComponent.java) |
| `ESConfiguredWorldCarvers` | — | [ESConfiguredWorldCarvers.java](cn/leolezury/eternalstarlight/common/data/ESConfiguredWorldCarvers.java) |
| `BookComponentConfig` | — | [BookComponentConfig.java](cn/leolezury/eternalstarlight/common/client/book/component/BookComponentConfig.java) |

## 按包浏览

- `architectury_inject_eternalstarlight_common_e24df25eede340ec8f6d2b6ebdba3f31_2e73e6faedfcf1a2a9755e5d365b491bd038a2ad5f030410035c77401f962641eternalstarlight0811211commondevjar`（1 类）
- `cn.leolezury.eternalstarlight.common`（1 类）
- `cn.leolezury.eternalstarlight.common.block`（130 类）
- `cn.leolezury.eternalstarlight.common.block.entity`（29 类）
- `cn.leolezury.eternalstarlight.common.block.entity.spawner`（5 类）
- `cn.leolezury.eternalstarlight.common.block.flammable`（1 类）
- `cn.leolezury.eternalstarlight.common.block.fluid`（1 类）
- `cn.leolezury.eternalstarlight.common.block.spawner`（4 类）
- `cn.leolezury.eternalstarlight.common.client`（2 类）
- `cn.leolezury.eternalstarlight.common.client.book`（2 类）
- `cn.leolezury.eternalstarlight.common.client.book.component`（8 类）
- `cn.leolezury.eternalstarlight.common.client.book.text`（2 类）
- `cn.leolezury.eternalstarlight.common.client.gui.screen`（6 类）
- `cn.leolezury.eternalstarlight.common.client.gui.screen.widget`（6 类）
- `cn.leolezury.eternalstarlight.common.client.gui.toast`（1 类）
- `cn.leolezury.eternalstarlight.common.client.gui.tooltip`（1 类）
- `cn.leolezury.eternalstarlight.common.client.handler`（2 类）
- `cn.leolezury.eternalstarlight.common.client.helper`（3 类）
- `cn.leolezury.eternalstarlight.common.client.model`（1 类）
- `cn.leolezury.eternalstarlight.common.client.model.animation`（4 类）
- `cn.leolezury.eternalstarlight.common.client.model.animation.definition`（26 类）
- `cn.leolezury.eternalstarlight.common.client.model.armor`（4 类）
- `cn.leolezury.eternalstarlight.common.client.model.block`（1 类）
- `cn.leolezury.eternalstarlight.common.client.model.entity`（43 类）
- `cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf`（1 类）
- `cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession`（5 类）
- `cn.leolezury.eternalstarlight.common.client.model.item`（5 类）
- `cn.leolezury.eternalstarlight.common.client.particle.advanced`（4 类）
- `cn.leolezury.eternalstarlight.common.client.particle.effect`（24 类）
- `cn.leolezury.eternalstarlight.common.client.particle.environment`（4 类）
- `cn.leolezury.eternalstarlight.common.client.renderer`（1 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.blockentity`（13 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.effect`（2 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.entity`（68 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.layer`（18 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory`（5 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf`（1 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession`（5 类）
- `cn.leolezury.eternalstarlight.common.client.renderer.world`（1 类）
- `cn.leolezury.eternalstarlight.common.client.resource`（1 类）
- `cn.leolezury.eternalstarlight.common.client.shader`（1 类）
- `cn.leolezury.eternalstarlight.common.client.sound`（1 类）
- `cn.leolezury.eternalstarlight.common.client.visual`（5 类）
- `cn.leolezury.eternalstarlight.common.client.weather`（1 类）
- `cn.leolezury.eternalstarlight.common.command`（3 类）
- `cn.leolezury.eternalstarlight.common.compat.jei`（1 类）
- `cn.leolezury.eternalstarlight.common.compat.jei.category`（4 类）
- `cn.leolezury.eternalstarlight.common.compat.jei.recipe`（1 类）
- `cn.leolezury.eternalstarlight.common.config`（1 类）
- `cn.leolezury.eternalstarlight.common.crest`（1 类）
- `cn.leolezury.eternalstarlight.common.critereon`（1 类）
- `cn.leolezury.eternalstarlight.common.data`（25 类）
- `cn.leolezury.eternalstarlight.common.effect`（5 类）
- `cn.leolezury.eternalstarlight.common.enchantment.effect`（3 类）
- `cn.leolezury.eternalstarlight.common.entity.attack`（10 类）
- `cn.leolezury.eternalstarlight.common.entity.attack.ray`（3 类）
- `cn.leolezury.eternalstarlight.common.entity.interfaces`（5 类）
- `cn.leolezury.eternalstarlight.common.entity.living`（2 类）
- `cn.leolezury.eternalstarlight.common.entity.living.animal`（15 类）
- `cn.leolezury.eternalstarlight.common.entity.living.boss`（2 类）
- `cn.leolezury.eternalstarlight.common.entity.living.boss.creeper`（2 类）
- `cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper`（16 类）
- `cn.leolezury.eternalstarlight.common.entity.living.boss.golem`（13 类）
- `cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity`（10 类）
- `cn.leolezury.eternalstarlight.common.entity.living.goal`（15 类）
- `cn.leolezury.eternalstarlight.common.entity.living.monster`（13 类）
- `cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf`（3 类）
- `cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem`（2 类）
- `cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession`（5 类）
- `cn.leolezury.eternalstarlight.common.entity.living.npc.trade`（1 类）
- `cn.leolezury.eternalstarlight.common.entity.living.phase`（4 类）
- `cn.leolezury.eternalstarlight.common.entity.misc`（8 类）
- `cn.leolezury.eternalstarlight.common.entity.projectile`（31 类）
- `cn.leolezury.eternalstarlight.common.handler`（2 类）
- `cn.leolezury.eternalstarlight.common.item.armor`（7 类）
- `cn.leolezury.eternalstarlight.common.item.combat`（53 类）
- `cn.leolezury.eternalstarlight.common.item.component`（4 类）
- `cn.leolezury.eternalstarlight.common.item.dispenser`（2 类）
- `cn.leolezury.eternalstarlight.common.item.interfaces`（2 类）
- `cn.leolezury.eternalstarlight.common.item.magic`（3 类）
- `cn.leolezury.eternalstarlight.common.item.menu`（7 类）
- `cn.leolezury.eternalstarlight.common.item.misc`（14 类）
- `cn.leolezury.eternalstarlight.common.item.recipe`（8 类）
- `cn.leolezury.eternalstarlight.common.item.tooltip`（1 类）
- `cn.leolezury.eternalstarlight.common.mixin`（39 类）
- `cn.leolezury.eternalstarlight.common.mixin.client`（21 类）
- `cn.leolezury.eternalstarlight.common.network`（17 类）
- `cn.leolezury.eternalstarlight.common.particle`（10 类）
- `cn.leolezury.eternalstarlight.common.platform`（3 类）
- `cn.leolezury.eternalstarlight.common.platform.registry`（2 类）
- `cn.leolezury.eternalstarlight.common.registry`（36 类）
- `cn.leolezury.eternalstarlight.common.resource.gatekeeper`（1 类）
- `cn.leolezury.eternalstarlight.common.spell`（14 类）
- `cn.leolezury.eternalstarlight.common.util`（24 类）
- `cn.leolezury.eternalstarlight.common.vfx`（5 类）
- `cn.leolezury.eternalstarlight.common.weather`（5 类）
- `cn.leolezury.eternalstarlight.common.world`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.biome`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.biomesource`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.carver`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator`（2 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature`（21 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature.coral`（4 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature.placement`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature.tree`（7 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator`（6 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage`（3 类）
- `cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.structure`（2 类）
- `cn.leolezury.eternalstarlight.common.world.gen.structure.garden`（2 类）
- `cn.leolezury.eternalstarlight.common.world.gen.structure.placement`（2 类）
- `cn.leolezury.eternalstarlight.common.world.gen.structure.pool`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.surface`（1 类）
- `cn.leolezury.eternalstarlight.common.world.gen.valuemap`（6 类）
- `cn.leolezury.eternalstarlight.common.world.saved`（1 类）
- `cn.leolezury.eternalstarlight.neoforge`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.block`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.block.fluid`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.client`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.client.event`（2 类）
- `cn.leolezury.eternalstarlight.neoforge.client.model.item`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.client.renderer`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen.provider`（5 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen.provider.advancement`（2 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen.provider.book`（2 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot`（8 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen.provider.model`（2 类）
- `cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags`（12 类）
- `cn.leolezury.eternalstarlight.neoforge.event`（2 类）
- `cn.leolezury.eternalstarlight.neoforge.item.armor`（4 类）
- `cn.leolezury.eternalstarlight.neoforge.item.combat`（4 类）
- `cn.leolezury.eternalstarlight.neoforge.mixin`（4 类）
- `cn.leolezury.eternalstarlight.neoforge.mixin.client`（4 类）
- `cn.leolezury.eternalstarlight.neoforge.network`（1 类）
- `cn.leolezury.eternalstarlight.neoforge.platform`（2 类）
- `cn.leolezury.eternalstarlight.neoforge.registry`（2 类）
