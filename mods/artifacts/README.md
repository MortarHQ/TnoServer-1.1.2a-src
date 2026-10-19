# Artifacts（`artifacts`）

> jar：`[奇异饰品]artifacts.jar` ｜ 版本：`13.1.0` ｜ 作者：ochotonida
> 规模：**220 个类 / 80 个包 / 14893 行** ｜ 事件处理类 0 ｜ Mixin 39 ｜ 注册/管理类 16

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `artifacts.component.ability` | 19 | `EntityCondition`、`AttributeModifiers`、`SwimInAir` |
| `artifacts.registry` | 16 | `RegistryHolder`、`ModSoundEvents`、`ModGameEvents` |
| `artifacts.neoforge.data` | 10 | `LootTables`、`LootModifiers`、`Language` |
| `artifacts.client.item.renderer` | 8 | `GloveArtifactRenderer`、`GlowingGloveArtifactRenderer`、`BootArtifactRenderer` |
| `artifacts.network` | 7 | `UpdateItemConfigPacket`、`NetworkHandler`、`ChorusTotemUsedPacket` |
| `artifacts.client.item.model` | 6 | `ArmsModel`、`BeltModel`、`HeadModel` |
| `artifacts.neoforge.data.tags` | 6 | `GameEventTags`、`EntityTypeTags`、`ItemTags` |
| `artifacts.config.value.type` | 6 | `ValueType`、`EnumValueType`、`BooleanValueType` |
| `artifacts.config` | 5 | `ConfigManager`、`ItemConfigs`、`ClientConfig` |
| `artifacts.integration.accessories` | 5 | `AccessoriesRenderingHandler`、`AccessoriesCompatClient`、`AccessoriesCompat` |
| `artifacts.client` | 5 | `ToggleKeyHandlers`、`CloudInABottleInputHandler`、`CooldownOverlayRenderer` |
| `artifacts.neoforge.integration.curios` | 5 | `CuriosRenderingHandler`、`CuriosCompatClient`、`CuriosCompat` |
| `artifacts.integration.trinkets` | 5 | `TrinketsRenderingHandler`、`TrinketsCompatClient`、`TrinketsCompat` |
| `artifacts.loot` | 5 | `ConfigValueChance`、`ConfigValueCondition`、`ReplaceWithLootTableFunction` |
| `artifacts.component.ability.retaliation` | 5 | `RetaliationEffect`、`FireEffect`、`ThornsEffect` |
| `artifacts.component.ability.mobeffect` | 5 | `MobEffectProvider`、`PostEatingEffects`、`PostDamageEffects` |
| `artifacts.mixin.ability.enchantment` | 4 | `LootItemRandomChanceWithEnchantedBonusConditionMixin`、`EnchantmentHelperMixin`、`ApplyBonusCountMixin` |
| `artifacts.item` | 4 | `UmbrellaItem`、`WearableArtifactItem`、`ArtifactItem` |
| `artifacts.neoforge.registry` | 4 | `ModLootModifiers`、`ModAttachmentTypes`、`NeoForgeRegister` |
| `artifacts.neoforge` | 3 | `ArtifactsNeoForge`、`ArtifactsNeoForgeClient`、`ArtifactsData` |
| `artifacts.mixin.accessors` | 3 | `EntityAccessor`、`LivingEntityAccessor`、`MobAccessor` |
| `artifacts.config.screen` | 3 | `ArtifactsConfigScreen`、`ConfigEntries`、`ItemSubCategoryListEntry` |
| `artifacts.equipment` | 3 | `EquipmentSlotManager`、`EquipmentHelper`、`EquipmentSlotProvider` |
| `artifacts.neoforge.client` | 3 | `ArmRenderHandler`、`UmbrellaArmPoseHandler`、`HeliumFlamingoOverlayRenderer` |
| `artifacts.util` | 3 | `TooltipHelper`、`ModCodecs`、`DamageSourceHelper` |
| `artifacts.client.item` | 3 | `RendererUtil`、`ArtifactRenderers`、`ArtifactLayers` |
| `artifacts.component` | 3 | `SwimData`、`HurtSound`、`ToggleIdentifier` |
| `artifacts.world.placement` | 3 | `CeilingHeightFilter`、`CampsiteCountPlacement`、`CampsiteHeightRangePlacement` |
| `artifacts.mixin.mobeffect.magnetism` | 2 | `ItemEntityMixin`、`PlayerMixin` |
| `artifacts.mixin.attribute` | 2 | `LivingEntityMixin`、`PlayerMixin` |
| `artifacts.mixin.item` | 2 | `ItemStackMixin`、`NaturalSpawnerMixin` |
| `artifacts.equipment.client` | 2 | `EquipmentRenderingManager`、`EquipmentRenderingHandler` |
| `artifacts.event` | 2 | `ArtifactHooks`、`SwimInAirInputHooks` |
| `artifacts.world` | 2 | `CampsiteFeatureConfiguration`、`CampsiteFeature` |
| `artifacts` | 2 | `ArtifactsClient`、`Artifacts` |
| `artifacts.client.mimic` | 2 | `MimicRenderer`、`MimicChestLayer` |
| `artifacts.config.value` | 2 | `Value`、`ValueTypes` |
| `artifacts.platform` | 2 | `PlatformHelper`、`PlatformServices` |
| `artifacts.client.mimic.model` | 2 | `MimicModel`、`MimicChestLayerModel` |
| `artifacts.entity` | 1 | `MimicEntity` |
| … | | 其余 40 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigManager` | class | Runnable | — | 29 | 273 | [ConfigManager.java](artifacts/config/ConfigManager.java) |
| `LootItemRandomChanceWithEnchantedBonusConditionMixin` | class | — | Mixin | 1 | 40 | [LootItemRandomChanceWithEnchantedBonusConditionMixin.java](artifacts/mixin/ability/enchantment/LootItemRandomChanceWithEnchantedBonusConditionMixin.java) |
| `MimicEntity` | class | Mob | — | 36 | 421 | [MimicEntity.java](artifacts/entity/MimicEntity.java) |
| `ArtifactsNeoForge` | class | — | Mod | 3 | 70 | [ArtifactsNeoForge.java](artifacts/neoforge/ArtifactsNeoForge.java) |
| `LivingEntityRendererAccessor` | interface | — | Mixin | 0 | 17 | [LivingEntityRendererAccessor.java](artifacts/mixin/accessors/client/LivingEntityRendererAccessor.java) |
| `ItemEntityMixin` | class | ItemEntityExtensions | Mixin | 0 | 25 | [ItemEntityMixin.java](artifacts/mixin/mobeffect/magnetism/ItemEntityMixin.java) |
| `UpdateItemConfigPacket` | record | CustomPacketPayload | — | 3 | 36 | [UpdateItemConfigPacket.java](artifacts/network/UpdateItemConfigPacket.java) |
| `NetworkHandler` | class | — | — | 6 | 74 | [NetworkHandler.java](artifacts/network/NetworkHandler.java) |
| `EntityMixin` | class | — | Mixin | 6 | 115 | [EntityMixin.java](artifacts/mixin/ability/fluidcollision/EntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 1 | 30 | [ServerGamePacketListenerImplMixin.java](artifacts/mixin/item/umbrella/server/ServerGamePacketListenerImplMixin.java) |
| `ItemConfigs` | class | ConfigManager | — | 5 | 419 | [ItemConfigs.java](artifacts/config/ItemConfigs.java) |
| `RegistryHolder` | class | Holder<R> | — | 18 | 104 | [RegistryHolder.java](artifacts/registry/RegistryHolder.java) |
| `NeoForgeNetworkHandler` | class | NetworkHandler.PayloadContext | — | 3 | 56 | [NeoForgeNetworkHandler.java](artifacts/neoforge/network/NeoForgeNetworkHandler.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 1 | 34 | [LivingEntityMixin.java](artifacts/neoforge/mixin/ability/slipresistance/LivingEntityMixin.java) |
| `UmbrellaItemMixin` | class | Item | Mixin | 1 | 23 | [UmbrellaItemMixin.java](artifacts/neoforge/mixin/item/UmbrellaItemMixin.java) |
| `LootTables` | class | LootTableProvider | — | 15 | 468 | [LootTables.java](artifacts/neoforge/data/LootTables.java) |
| `EntityAccessor` | interface | — | Mixin | 0 | 13 | [EntityAccessor.java](artifacts/mixin/accessors/EntityAccessor.java) |
| `LivingEntityAccessor` | interface | — | Mixin | 0 | 16 | [LivingEntityAccessor.java](artifacts/mixin/accessors/LivingEntityAccessor.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 23 | [LivingEntityMixin.java](artifacts/mixin/attribute/LivingEntityMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 2 | 39 | [LivingEntityMixin.java](artifacts/mixin/attribute/invincibilityticks/LivingEntityMixin.java) |
| `EntityMixin` | class | — | Mixin | 2 | 36 | [EntityMixin.java](artifacts/mixin/attribute/flatulence/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 3 | 58 | [LivingEntityMixin.java](artifacts/mixin/ability/LivingEntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 3 | 32 | [LivingEntityMixin.java](artifacts/mixin/ability/hurtsound/LivingEntityMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 3 | 25 | [LivingEntityMixin.java](artifacts/mixin/ability/hurtsound/client/LivingEntityMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 49 | [LivingEntityMixin.java](artifacts/mixin/ability/deathprotectionteleport/LivingEntityMixin.java) |
| `EnderPearlItemMixin` | class | Item | Mixin | 1 | 56 | [EnderPearlItemMixin.java](artifacts/mixin/ability/enderpearlhungercost/EnderPearlItemMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 1 | 30 | [GameRendererMixin.java](artifacts/mixin/ability/nightvision/client/GameRendererMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 25 | [EntityMixin.java](artifacts/mixin/ability/damageimmunity/EntityMixin.java) |
| `PowderSnowBlockMixin` | class | — | Mixin | 1 | 20 | [PowderSnowBlockMixin.java](artifacts/mixin/ability/walkonpowdersnow/PowderSnowBlockMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 2 | 84 | [ItemStackMixin.java](artifacts/mixin/item/ItemStackMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 17 | [EntityMixin.java](artifacts/mixin/item/umbrella/EntityMixin.java) |
| `LivingEntityMixin` | class | LivingEntityExtensions | Mixin | 1 | 48 | [LivingEntityMixin.java](artifacts/mixin/item/pocketpiston/client/LivingEntityMixin.java) |
| `ModSoundEvents` | class | — | — | 1 | 22 | [ModSoundEvents.java](artifacts/registry/ModSoundEvents.java) |
| `ModGameEvents` | class | — | — | 1 | 19 | [ModGameEvents.java](artifacts/registry/ModGameEvents.java) |
| `LootModifiers` | class | GlobalLootModifierProvider | — | 17 | 398 | [LootModifiers.java](artifacts/neoforge/data/LootModifiers.java) |
| `EnchantmentHelperMixin` | class | — | Mixin | 1 | 27 | [EnchantmentHelperMixin.java](artifacts/mixin/ability/retaliation/EnchantmentHelperMixin.java) |
| `EnchantmentHelperMixin` | class | — | Mixin | 2 | 41 | [EnchantmentHelperMixin.java](artifacts/mixin/ability/enchantment/EnchantmentHelperMixin.java) |
| `ArtifactsConfigScreen` | class | — | — | 7 | 126 | [ArtifactsConfigScreen.java](artifacts/config/screen/ArtifactsConfigScreen.java) |
| `PlayerMixin` | class | — | Mixin | 1 | 21 | [PlayerMixin.java](artifacts/neoforge/mixin/ability/sinking/PlayerMixin.java) |
| `TagLoaderMixin` | class | — | Mixin | 1 | 53 | [TagLoaderMixin.java](artifacts/mixin/debug/TagLoaderMixin.java) |
| `MobAccessor` | interface | — | Mixin | 0 | 13 | [MobAccessor.java](artifacts/mixin/accessors/MobAccessor.java) |
| `PlayerMixin` | class | — | Mixin | 1 | 23 | [PlayerMixin.java](artifacts/mixin/attribute/PlayerMixin.java) |
| `VillagerMixin` | class | — | Mixin | 1 | 20 | [VillagerMixin.java](artifacts/mixin/attribute/villagerreputation/VillagerMixin.java) |
| `PlayerMixin` | class | LivingEntity | Mixin | 1 | 32 | [PlayerMixin.java](artifacts/mixin/ability/posteatingeffects/PlayerMixin.java) |
| `ApplyBonusCountMixin` | class | — | Mixin | 1 | 41 | [ApplyBonusCountMixin.java](artifacts/mixin/ability/enchantment/ApplyBonusCountMixin.java) |
| `EnchantedCountIncreaseFunctionMixin` | class | — | Mixin | 1 | 41 | [EnchantedCountIncreaseFunctionMixin.java](artifacts/mixin/ability/enchantment/EnchantedCountIncreaseFunctionMixin.java) |
| `ThrownEnderPearlMixin` | class | — | Mixin | 1 | 26 | [ThrownEnderPearlMixin.java](artifacts/mixin/ability/enderpearldamageimmunity/ThrownEnderPearlMixin.java) |
| `PhantomSweepAttackGoalMixin` | class | — | Mixin | 2 | 45 | [PhantomSweepAttackGoalMixin.java](artifacts/mixin/ability/phantomrepellent/PhantomSweepAttackGoalMixin.java) |
| `PiglinAiMixin` | class | — | Mixin | 1 | 18 | [PiglinAiMixin.java](artifacts/mixin/ability/piglinloved/PiglinAiMixin.java) |
| `NaturalSpawnerMixin` | class | — | Mixin | 1 | 59 | [NaturalSpawnerMixin.java](artifacts/mixin/item/NaturalSpawnerMixin.java) |
| `HumanoidModelMixin` | class | — | Mixin | 1 | 42 | [HumanoidModelMixin.java](artifacts/mixin/item/umbrella/client/HumanoidModelMixin.java) |
| `PlayerMixin` | class | — | Mixin | 1 | 21 | [PlayerMixin.java](artifacts/mixin/mobeffect/magnetism/PlayerMixin.java) |
| `AccessoriesRenderingHandler` | class | EquipmentRenderingHandler | — | 5 | 85 | [AccessoriesRenderingHandler.java](artifacts/integration/accessories/AccessoriesRenderingHandler.java) |
| `ClientConfig` | class | ConfigManager | — | 0 | 31 | [ClientConfig.java](artifacts/config/ClientConfig.java) |
| `ToggleKeyHandlers` | class | — | — | 5 | 71 | [ToggleKeyHandlers.java](artifacts/client/ToggleKeyHandlers.java) |
| `EquipmentSlotManager` | class | — | — | 3 | 34 | [EquipmentSlotManager.java](artifacts/equipment/EquipmentSlotManager.java) |
| `EquipmentRenderingManager` | class | — | — | 4 | 45 | [EquipmentRenderingManager.java](artifacts/equipment/client/EquipmentRenderingManager.java) |
| `EquipmentRenderingHandler` | interface | — | — | 0 | 20 | [EquipmentRenderingHandler.java](artifacts/equipment/client/EquipmentRenderingHandler.java) |
| `CuriosRenderingHandler` | class | EquipmentRenderingHandler | — | 4 | 99 | [CuriosRenderingHandler.java](artifacts/neoforge/integration/curios/CuriosRenderingHandler.java) |
| `Language` | class | LanguageProvider | — | 24 | 388 | [Language.java](artifacts/neoforge/data/Language.java) |

## Mixin（39 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LootItemRandomChanceWithEnchantedBonusConditionMixin` | `LootItemRandomChanceWithEnchantedBonusCondition` | [LootItemRandomChanceWithEnchantedBonusConditionMixin.java](artifacts/mixin/ability/enchantment/LootItemRandomChanceWithEnchantedBonusConditionMixin.java) |
| `LivingEntityRendererAccessor` | `LivingEntityRendererAccessor` | [LivingEntityRendererAccessor.java](artifacts/mixin/accessors/client/LivingEntityRendererAccessor.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](artifacts/mixin/mobeffect/magnetism/ItemEntityMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](artifacts/mixin/ability/fluidcollision/EntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](artifacts/mixin/item/umbrella/server/ServerGamePacketListenerImplMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/neoforge/mixin/ability/slipresistance/LivingEntityMixin.java) |
| `UmbrellaItemMixin` | `UmbrellaItem` | [UmbrellaItemMixin.java](artifacts/neoforge/mixin/item/UmbrellaItemMixin.java) |
| `EntityAccessor` | `EntityAccessor` | [EntityAccessor.java](artifacts/mixin/accessors/EntityAccessor.java) |
| `LivingEntityAccessor` | `LivingEntityAccessor` | [LivingEntityAccessor.java](artifacts/mixin/accessors/LivingEntityAccessor.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/attribute/LivingEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/attribute/invincibilityticks/LivingEntityMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](artifacts/mixin/attribute/flatulence/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/ability/LivingEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/ability/hurtsound/LivingEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/ability/hurtsound/client/LivingEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/ability/deathprotectionteleport/LivingEntityMixin.java) |
| `EnderPearlItemMixin` | `EnderPearlItem` | [EnderPearlItemMixin.java](artifacts/mixin/ability/enderpearlhungercost/EnderPearlItemMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](artifacts/mixin/ability/nightvision/client/GameRendererMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](artifacts/mixin/ability/damageimmunity/EntityMixin.java) |
| `PowderSnowBlockMixin` | `PowderSnowBlock` | [PowderSnowBlockMixin.java](artifacts/mixin/ability/walkonpowdersnow/PowderSnowBlockMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](artifacts/mixin/item/ItemStackMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](artifacts/mixin/item/umbrella/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](artifacts/mixin/item/pocketpiston/client/LivingEntityMixin.java) |
| `EnchantmentHelperMixin` | `EnchantmentHelper` | [EnchantmentHelperMixin.java](artifacts/mixin/ability/retaliation/EnchantmentHelperMixin.java) |
| `EnchantmentHelperMixin` | `EnchantmentHelper` | [EnchantmentHelperMixin.java](artifacts/mixin/ability/enchantment/EnchantmentHelperMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](artifacts/neoforge/mixin/ability/sinking/PlayerMixin.java) |
| `TagLoaderMixin` | `TagLoader` | [TagLoaderMixin.java](artifacts/mixin/debug/TagLoaderMixin.java) |
| `MobAccessor` | `MobAccessor` | [MobAccessor.java](artifacts/mixin/accessors/MobAccessor.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](artifacts/mixin/attribute/PlayerMixin.java) |
| `VillagerMixin` | `Villager` | [VillagerMixin.java](artifacts/mixin/attribute/villagerreputation/VillagerMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](artifacts/mixin/ability/posteatingeffects/PlayerMixin.java) |
| `ApplyBonusCountMixin` | `ApplyBonusCount` | [ApplyBonusCountMixin.java](artifacts/mixin/ability/enchantment/ApplyBonusCountMixin.java) |
| `EnchantedCountIncreaseFunctionMixin` | `EnchantedCountIncreaseFunction` | [EnchantedCountIncreaseFunctionMixin.java](artifacts/mixin/ability/enchantment/EnchantedCountIncreaseFunctionMixin.java) |
| `ThrownEnderPearlMixin` | `ThrownEnderPearl` | [ThrownEnderPearlMixin.java](artifacts/mixin/ability/enderpearldamageimmunity/ThrownEnderPearlMixin.java) |
| `PhantomSweepAttackGoalMixin` | `PhantomSweepAttackGoal` | [PhantomSweepAttackGoalMixin.java](artifacts/mixin/ability/phantomrepellent/PhantomSweepAttackGoalMixin.java) |
| `PiglinAiMixin` | `PiglinAi` | [PiglinAiMixin.java](artifacts/mixin/ability/piglinloved/PiglinAiMixin.java) |
| `NaturalSpawnerMixin` | `NaturalSpawner` | [NaturalSpawnerMixin.java](artifacts/mixin/item/NaturalSpawnerMixin.java) |
| `HumanoidModelMixin` | `HumanoidModel` | [HumanoidModelMixin.java](artifacts/mixin/item/umbrella/client/HumanoidModelMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](artifacts/mixin/mobeffect/magnetism/PlayerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigManager` | — | [ConfigManager.java](artifacts/config/ConfigManager.java) |
| `UpdateItemConfigPacket` | — | [UpdateItemConfigPacket.java](artifacts/network/UpdateItemConfigPacket.java) |
| `ItemConfigs` | — | [ItemConfigs.java](artifacts/config/ItemConfigs.java) |
| `ArtifactsConfigScreen` | — | [ArtifactsConfigScreen.java](artifacts/config/screen/ArtifactsConfigScreen.java) |
| `ClientConfig` | — | [ClientConfig.java](artifacts/config/ClientConfig.java) |
| `ConfigValueChance` | — | [ConfigValueChance.java](artifacts/loot/ConfigValueChance.java) |
| `ConfiguredFeatures` | — | [ConfiguredFeatures.java](artifacts/neoforge/data/ConfiguredFeatures.java) |
| `ConfigValueCondition` | — | [ConfigValueCondition.java](artifacts/neoforge/condition/ConfigValueCondition.java) |
| `GeneralConfig` | — | [GeneralConfig.java](artifacts/config/GeneralConfig.java) |
| `ModConfig` | — | [ModConfig.java](artifacts/config/ModConfig.java) |
| `ConfigEntries` | — | [ConfigEntries.java](artifacts/config/screen/ConfigEntries.java) |
| `ConfigValueCondition` | — | [ConfigValueCondition.java](artifacts/loot/ConfigValueCondition.java) |
| `CampsiteFeatureConfiguration` | — | [CampsiteFeatureConfiguration.java](artifacts/world/CampsiteFeatureConfiguration.java) |

## 按包浏览

- `architectury_inject_artifacts_common_638dfd3c9fdf43ee8916e389bfe28a79_487fd965d10772ea4816547792075a5e5d2ba86646392567fc3fa58e12986fddartifactscommon1310devjar`（1 类）
- `artifacts`（2 类）
- `artifacts.attribute`（1 类）
- `artifacts.client`（5 类）
- `artifacts.client.item`（3 类）
- `artifacts.client.item.model`（6 类）
- `artifacts.client.item.renderer`（8 类）
- `artifacts.client.mimic`（2 类）
- `artifacts.client.mimic.model`（2 类）
- `artifacts.component`（3 类）
- `artifacts.component.ability`（19 类）
- `artifacts.component.ability.mobeffect`（5 类）
- `artifacts.component.ability.retaliation`（5 类）
- `artifacts.config`（5 类）
- `artifacts.config.screen`（3 类）
- `artifacts.config.value`（2 类）
- `artifacts.config.value.type`（6 类）
- `artifacts.effect`（1 类）
- `artifacts.entity`（1 类）
- `artifacts.equipment`（3 类）
- `artifacts.equipment.client`（2 类）
- `artifacts.event`（2 类）
- `artifacts.extensions.ability`（1 类）
- `artifacts.extensions.mobeffect.magnetism`（1 类）
- `artifacts.extensions.pocketpiston`（1 类）
- `artifacts.integration`（1 类）
- `artifacts.integration.accessories`（5 类）
- `artifacts.integration.lootr`（1 类）
- `artifacts.integration.minecraft`（1 类）
- `artifacts.integration.trinkets`（5 类）
- `artifacts.item`（4 类）
- `artifacts.loot`（5 类）
- `artifacts.mixin.ability`（1 类）
- `artifacts.mixin.ability.damageimmunity`（1 类）
- `artifacts.mixin.ability.deathprotectionteleport`（1 类）
- `artifacts.mixin.ability.enchantment`（4 类）
- `artifacts.mixin.ability.enderpearldamageimmunity`（1 类）
- `artifacts.mixin.ability.enderpearlhungercost`（1 类）
- `artifacts.mixin.ability.fluidcollision`（1 类）
- `artifacts.mixin.ability.hurtsound`（1 类）
- `artifacts.mixin.ability.hurtsound.client`（1 类）
- `artifacts.mixin.ability.nightvision.client`（1 类）
- `artifacts.mixin.ability.phantomrepellent`（1 类）
- `artifacts.mixin.ability.piglinloved`（1 类）
- `artifacts.mixin.ability.posteatingeffects`（1 类）
- `artifacts.mixin.ability.retaliation`（1 类）
- `artifacts.mixin.ability.walkonpowdersnow`（1 类）
- `artifacts.mixin.accessors`（3 类）
- `artifacts.mixin.accessors.client`（1 类）
- `artifacts.mixin.attribute`（2 类）
- `artifacts.mixin.attribute.flatulence`（1 类）
- `artifacts.mixin.attribute.invincibilityticks`（1 类）
- `artifacts.mixin.attribute.villagerreputation`（1 类）
- `artifacts.mixin.debug`（1 类）
- `artifacts.mixin.item`（2 类）
- `artifacts.mixin.item.pocketpiston.client`（1 类）
- `artifacts.mixin.item.umbrella`（1 类）
- `artifacts.mixin.item.umbrella.client`（1 类）
- `artifacts.mixin.item.umbrella.server`（1 类）
- `artifacts.mixin.mobeffect.magnetism`（2 类）
- `artifacts.neoforge`（3 类）
- `artifacts.neoforge.client`（3 类）
- `artifacts.neoforge.condition`（1 类）
- `artifacts.neoforge.data`（10 类）
- `artifacts.neoforge.data.tags`（6 类）
- `artifacts.neoforge.event`（1 类）
- `artifacts.neoforge.integration.curios`（5 类）
- `artifacts.neoforge.loot`（1 类）
- `artifacts.neoforge.mixin.ability.sinking`（1 类）
- `artifacts.neoforge.mixin.ability.slipresistance`（1 类）
- `artifacts.neoforge.mixin.item`（1 类）
- `artifacts.neoforge.network`（1 类）
- `artifacts.neoforge.platform`（1 类）
- `artifacts.neoforge.registry`（4 类）
- `artifacts.network`（7 类）
- `artifacts.platform`（2 类）
- `artifacts.registry`（16 类）
- `artifacts.util`（3 类）
- `artifacts.world`（2 类）
- `artifacts.world.placement`（3 类）
