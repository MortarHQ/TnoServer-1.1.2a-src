# Illager Invasion（`illagerinvasion`）

> jar：`[灾厄侵扰]IllagerInvasion.jar` ｜ 版本：`21.1.6` ｜ 作者：Dricky, Safro, Zeckemith, Fuzs
> 规模：**98 个类 / 31 个包 / 10315 行** ｜ 事件处理类 0 ｜ Mixin 6 ｜ 注册/管理类 6

**官方描述**：The illagers are back! Be ready to fight new foes. A port of Illager Expansion.

**依赖**：`neoforge`、`minecraft`、`puzzleslib`、`extensibleenums`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `fuzs.illagerinvasion.client.render.entity` | 15 | `InvokerFangsRenderer`、`MagmaEntityRender`、`InquisitorRender` |
| `fuzs.illagerinvasion.world.entity.monster` | 12 | `Invoker`、`Surrendered`、`Archivist` |
| `fuzs.illagerinvasion.client.model` | 8 | `HatIllagerEntityModel`、`ArmoredIllagerEntityModel`、`IllagerWithStaffEntityModel` |
| `fuzs.illagerinvasion.data.tags` | 6 | `ModBlockTagProvider`、`ModItemTagProvider`、`ModEntityTypeTagProvider` |
| `fuzs.illagerinvasion.world.item` | 5 | `HatchetItem`、`IllusionaryDustItem`、`MagicalFireChargeItem` |
| `fuzs.illagerinvasion.util` | 5 | `FireworksShootingHelper`、`EnchantToolUtil`、`SetMagicFireUtil` |
| `fuzs.illagerinvasion.init` | 4 | `ModSoundEvents`、`ModRegistry`、`ModEntityTypes` |
| `fuzs.illagerinvasion.mixin` | 4 | `IllusionerMixin`、`FoodDataMixin`、`WoodlandMansionPieceMixin` |
| `fuzs.illagerinvasion.data.loot` | 4 | `ModEntityTypeLootProvider`、`ModChestLootProvider`、`ModBlockLootProvider` |
| `fuzs.illagerinvasion.world.entity.projectile` | 3 | `Hatchet`、`SkullBolt`、`FlyingMagma` |
| `fuzs.illagerinvasion.neoforge.data.client` | 3 | `ModSoundDefinitionProvider`、`ModSpriteSourceProvider`、`ModParticleDescriptionProvider` |
| `fuzs.illagerinvasion.client.render.entity.layers` | 3 | `InvokerShieldLayer`、`NecromancerShieldLayer`、`InvokerGoldLayer` |
| `fuzs.illagerinvasion.config` | 2 | `RaidWavesConfigHelper`、`ServerConfig` |
| `fuzs.illagerinvasion.handler` | 2 | `VillagerGoalHandler`、`PlatinumTrimHandler` |
| `fuzs.illagerinvasion.data` | 2 | `ModRecipeProvider`、`ModTrimMaterialDataProvider` |
| `fuzs.illagerinvasion.world.level.block` | 2 | `MagicFireBlock`、`ImbuingTableBlock` |
| `fuzs.illagerinvasion.world.entity.ai.goal` | 2 | `PotionBowAttackGoal`、`HatchetAttackGoal` |
| `fuzs.illagerinvasion.data.client` | 2 | `ModModelProvider`、`ModLanguageProvider` |
| `fuzs.illagerinvasion.world.level.levelgen.structure.pools` | 2 | `SingleNoLiquidPoolElement`、`LegacySingleNoLiquidPoolElement` |
| `fuzs.illagerinvasion.neoforge.client` | 1 | `IllagerInvasionNeoForgeClient` |
| `fuzs.illagerinvasion.neoforge` | 1 | `IllagerInvasionNeoForge` |
| `fuzs.illagerinvasion.client.init` | 1 | `ClientModRegistry` |
| `fuzs.illagerinvasion.mixin.accessor` | 1 | `VillagerHostilesSensorAccessor` |
| `fuzs.illagerinvasion.mixin.client` | 1 | `IllagerModelMixin` |
| `fuzs.illagerinvasion.world.inventory` | 1 | `ImbuingMenu` |
| `fuzs.illagerinvasion.client.handler` | 1 | `EnchantmentTooltipHandler` |
| `fuzs.illagerinvasion` | 1 | `IllagerInvasion` |
| `fuzs.illagerinvasion.client` | 1 | `IllagerInvasionClient` |
| `fuzs.illagerinvasion.client.gui.screens.inventory` | 1 | `ImbuingScreen` |
| `architectury_inject_IllagerInvasion1211_Common_faac820a26784c08bea233f384981aa6_9ea9c62487e09fbb1f2c317d387eac3b25e4038fce2c0afd5d89088ad45d8ee2IllagerInvasionv21161211Commondevjar` | 1 | `PlatformMethods` |
| `fuzs.illagerinvasion.world.level.levelgen.structure.structures` | 1 | `LabyrinthStructure` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IllagerInvasionNeoForgeClient` | class | — | Mod | 0 | 27 | [IllagerInvasionNeoForgeClient.java](fuzs/illagerinvasion/neoforge/client/IllagerInvasionNeoForgeClient.java) |
| `IllagerInvasionNeoForge` | class | — | Mod | 0 | 44 | [IllagerInvasionNeoForge.java](fuzs/illagerinvasion/neoforge/IllagerInvasionNeoForge.java) |
| `Invoker` | class | SpellcasterIllager | — | 77 | 729 | [Invoker.java](fuzs/illagerinvasion/world/entity/monster/Invoker.java) |
| `ClientModRegistry` | class | — | — | 1 | 79 | [ClientModRegistry.java](fuzs/illagerinvasion/client/init/ClientModRegistry.java) |
| `ModSoundEvents` | class | — | — | 2 | 61 | [ModSoundEvents.java](fuzs/illagerinvasion/init/ModSoundEvents.java) |
| `IllusionerMixin` | class | SpellcasterIllager | Mixin | 5 | 85 | [IllusionerMixin.java](fuzs/illagerinvasion/mixin/IllusionerMixin.java) |
| `FoodDataMixin` | class | — | Mixin | 2 | 45 | [FoodDataMixin.java](fuzs/illagerinvasion/mixin/FoodDataMixin.java) |
| `RaidWavesConfigHelper` | class | — | — | 11 | 59 | [RaidWavesConfigHelper.java](fuzs/illagerinvasion/config/RaidWavesConfigHelper.java) |
| `Surrendered` | class | Skeleton | — | 36 | 358 | [Surrendered.java](fuzs/illagerinvasion/world/entity/monster/Surrendered.java) |
| `Archivist` | class | SpellcasterIllager | — | 31 | 285 | [Archivist.java](fuzs/illagerinvasion/world/entity/monster/Archivist.java) |
| `Firecaller` | class | SpellcasterIllager | — | 31 | 270 | [Firecaller.java](fuzs/illagerinvasion/world/entity/monster/Firecaller.java) |
| `Necromancer` | class | SpellcasterIllager | — | 36 | 333 | [Necromancer.java](fuzs/illagerinvasion/world/entity/monster/Necromancer.java) |
| `VillagerGoalHandler` | class | — | — | 5 | 81 | [VillagerGoalHandler.java](fuzs/illagerinvasion/handler/VillagerGoalHandler.java) |
| `PlatinumTrimHandler` | class | — | — | 5 | 74 | [PlatinumTrimHandler.java](fuzs/illagerinvasion/handler/PlatinumTrimHandler.java) |
| `WoodlandMansionPieceMixin` | class | — | Mixin | 1 | 40 | [WoodlandMansionPieceMixin.java](fuzs/illagerinvasion/mixin/WoodlandMansionPieceMixin.java) |
| `PatrolSpawnerMixin` | class | — | Mixin | 1 | 21 | [PatrolSpawnerMixin.java](fuzs/illagerinvasion/mixin/PatrolSpawnerMixin.java) |
| `VillagerHostilesSensorAccessor` | interface | — | Mixin | 0 | 23 | [VillagerHostilesSensorAccessor.java](fuzs/illagerinvasion/mixin/accessor/VillagerHostilesSensorAccessor.java) |
| `IllagerModelMixin` | class | HierarchicalModel | Mixin | 1 | 80 | [IllagerModelMixin.java](fuzs/illagerinvasion/mixin/client/IllagerModelMixin.java) |
| `ModEntityTypeLootProvider` | class | EntityTypes | — | 1 | 323 | [ModEntityTypeLootProvider.java](fuzs/illagerinvasion/data/loot/ModEntityTypeLootProvider.java) |
| `Inquisitor` | class | AbstractIllager | — | 26 | 299 | [Inquisitor.java](fuzs/illagerinvasion/world/entity/monster/Inquisitor.java) |
| `Provoker` | class | SpellcasterIllager | — | 25 | 216 | [Provoker.java](fuzs/illagerinvasion/world/entity/monster/Provoker.java) |
| `Sorcerer` | class | SpellcasterIllager | — | 27 | 226 | [Sorcerer.java](fuzs/illagerinvasion/world/entity/monster/Sorcerer.java) |
| `ModRegistry` | class | — | — | 2 | 124 | [ModRegistry.java](fuzs/illagerinvasion/init/ModRegistry.java) |
| `ModChestLootProvider` | class | Simple | — | 1 | 547 | [ModChestLootProvider.java](fuzs/illagerinvasion/data/loot/ModChestLootProvider.java) |
| `ModBlockLootProvider` | class | Blocks | — | 1 | 18 | [ModBlockLootProvider.java](fuzs/illagerinvasion/data/loot/ModBlockLootProvider.java) |
| `ModEntityInjectLootProvider` | class | Simple | — | 1 | 95 | [ModEntityInjectLootProvider.java](fuzs/illagerinvasion/data/loot/ModEntityInjectLootProvider.java) |
| `ImbuingMenu` | class | AbstractContainerMenu | — | 13 | 204 | [ImbuingMenu.java](fuzs/illagerinvasion/world/inventory/ImbuingMenu.java) |
| `Alchemist` | class | AbstractIllager | — | 20 | 244 | [Alchemist.java](fuzs/illagerinvasion/world/entity/monster/Alchemist.java) |
| `Basher` | class | AbstractIllager | — | 22 | 238 | [Basher.java](fuzs/illagerinvasion/world/entity/monster/Basher.java) |
| `EnchantmentTooltipHandler` | class | — | — | 1 | 49 | [EnchantmentTooltipHandler.java](fuzs/illagerinvasion/client/handler/EnchantmentTooltipHandler.java) |
| `ServerConfig` | class | ConfigCore | — | 1 | 83 | [ServerConfig.java](fuzs/illagerinvasion/config/ServerConfig.java) |
| `ModRecipeProvider` | class | AbstractRecipeProvider | — | 1 | 58 | [ModRecipeProvider.java](fuzs/illagerinvasion/data/ModRecipeProvider.java) |
| `HatchetItem` | class | Item | — | 11 | 120 | [HatchetItem.java](fuzs/illagerinvasion/world/item/HatchetItem.java) |
| `HatIllagerEntityModel` | class | HierarchicalModel | — | 7 | 207 | [HatIllagerEntityModel.java](fuzs/illagerinvasion/client/model/HatIllagerEntityModel.java) |
| `ArmoredIllagerEntityModel` | class | HierarchicalModel | — | 7 | 250 | [ArmoredIllagerEntityModel.java](fuzs/illagerinvasion/client/model/ArmoredIllagerEntityModel.java) |
| `IllagerWithStaffEntityModel` | class | HierarchicalModel | — | 7 | 200 | [IllagerWithStaffEntityModel.java](fuzs/illagerinvasion/client/model/IllagerWithStaffEntityModel.java) |
| `InvokerEntityModel` | class | HierarchicalModel | — | 7 | 200 | [InvokerEntityModel.java](fuzs/illagerinvasion/client/model/InvokerEntityModel.java) |
| `IllagerInvasion` | class | ModConstructor | — | 11 | 273 | [IllagerInvasion.java](fuzs/illagerinvasion/IllagerInvasion.java) |
| `Marauder` | class | AbstractIllager | — | 16 | 147 | [Marauder.java](fuzs/illagerinvasion/world/entity/monster/Marauder.java) |
| `Hatchet` | class | AbstractArrow | — | 15 | 137 | [Hatchet.java](fuzs/illagerinvasion/world/entity/projectile/Hatchet.java) |
| `MagicFireBlock` | class | BaseFireBlock | — | 8 | 69 | [MagicFireBlock.java](fuzs/illagerinvasion/world/level/block/MagicFireBlock.java) |
| `IllagerInvasionClient` | class | ClientModConstructor | — | 8 | 114 | [IllagerInvasionClient.java](fuzs/illagerinvasion/client/IllagerInvasionClient.java) |
| `BrimmedHatIllagerEntityModel` | class | HierarchicalModel | — | 7 | 193 | [BrimmedHatIllagerEntityModel.java](fuzs/illagerinvasion/client/model/BrimmedHatIllagerEntityModel.java) |
| `CapedIllagerEntityModel` | class | HierarchicalModel | — | 7 | 191 | [CapedIllagerEntityModel.java](fuzs/illagerinvasion/client/model/CapedIllagerEntityModel.java) |
| `ModBlockTagProvider` | class | AbstractTagProvider | — | 1 | 21 | [ModBlockTagProvider.java](fuzs/illagerinvasion/data/tags/ModBlockTagProvider.java) |
| `ModItemTagProvider` | class | AbstractTagProvider | — | 1 | 21 | [ModItemTagProvider.java](fuzs/illagerinvasion/data/tags/ModItemTagProvider.java) |
| `ModEntityTypeTagProvider` | class | AbstractTagProvider | — | 1 | 62 | [ModEntityTypeTagProvider.java](fuzs/illagerinvasion/data/tags/ModEntityTypeTagProvider.java) |
| `InvokerFangs` | class | Entity | — | 10 | 159 | [InvokerFangs.java](fuzs/illagerinvasion/world/entity/monster/InvokerFangs.java) |
| `ImbuingTableBlock` | class | Block | — | 4 | 66 | [ImbuingTableBlock.java](fuzs/illagerinvasion/world/level/block/ImbuingTableBlock.java) |
| `IllusionaryDustItem` | class | Item | — | 1 | 41 | [IllusionaryDustItem.java](fuzs/illagerinvasion/world/item/IllusionaryDustItem.java) |
| `MagicalFireChargeItem` | class | FireChargeItem | — | 2 | 48 | [MagicalFireChargeItem.java](fuzs/illagerinvasion/world/item/MagicalFireChargeItem.java) |
| `LostCandleItem` | class | Item | — | 2 | 78 | [LostCandleItem.java](fuzs/illagerinvasion/world/item/LostCandleItem.java) |
| `HornOfSightItem` | class | InstrumentItem | — | 1 | 39 | [HornOfSightItem.java](fuzs/illagerinvasion/world/item/HornOfSightItem.java) |
| `FireworksShootingHelper` | class | — | — | 5 | 56 | [FireworksShootingHelper.java](fuzs/illagerinvasion/util/FireworksShootingHelper.java) |
| `ImbuingScreen` | class | AbstractContainerScreen | — | 2 | 36 | [ImbuingScreen.java](fuzs/illagerinvasion/client/gui/screens/inventory/ImbuingScreen.java) |
| `InvokerFangsRenderer` | class | EntityRenderer | — | 2 | 52 | [InvokerFangsRenderer.java](fuzs/illagerinvasion/client/render/entity/InvokerFangsRenderer.java) |
| `MagmaEntityRender` | class | EntityRenderer | — | 2 | 38 | [MagmaEntityRender.java](fuzs/illagerinvasion/client/render/entity/MagmaEntityRender.java) |
| `ModEntityTypes` | class | — | — | 1 | 58 | [ModEntityTypes.java](fuzs/illagerinvasion/init/ModEntityTypes.java) |
| `ModItems` | class | — | — | 1 | 68 | [ModItems.java](fuzs/illagerinvasion/init/ModItems.java) |
| `ModTrimMaterialDataProvider` | class | AbstractRegistriesDatapackGenerator | — | 1 | 21 | [ModTrimMaterialDataProvider.java](fuzs/illagerinvasion/data/ModTrimMaterialDataProvider.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `IllusionerMixin` | `Illusioner` | [IllusionerMixin.java](fuzs/illagerinvasion/mixin/IllusionerMixin.java) |
| `FoodDataMixin` | `FoodData` | [FoodDataMixin.java](fuzs/illagerinvasion/mixin/FoodDataMixin.java) |
| `WoodlandMansionPieceMixin` | `WoodlandMansionPiece` | [WoodlandMansionPieceMixin.java](fuzs/illagerinvasion/mixin/WoodlandMansionPieceMixin.java) |
| `PatrolSpawnerMixin` | `PatrolSpawner` | [PatrolSpawnerMixin.java](fuzs/illagerinvasion/mixin/PatrolSpawnerMixin.java) |
| `VillagerHostilesSensorAccessor` | `VillagerHostilesSensorAccessor` | [VillagerHostilesSensorAccessor.java](fuzs/illagerinvasion/mixin/accessor/VillagerHostilesSensorAccessor.java) |
| `IllagerModelMixin` | `IllagerModel` | [IllagerModelMixin.java](fuzs/illagerinvasion/mixin/client/IllagerModelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `RaidWavesConfigHelper` | — | [RaidWavesConfigHelper.java](fuzs/illagerinvasion/config/RaidWavesConfigHelper.java) |
| `ServerConfig` | — | [ServerConfig.java](fuzs/illagerinvasion/config/ServerConfig.java) |

## 全部类（98）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IllagerInvasionNeoForgeClient` | class | — | Mod | 0 | 27 | [IllagerInvasionNeoForgeClient.java](fuzs/illagerinvasion/neoforge/client/IllagerInvasionNeoForgeClient.java) |
| `IllagerInvasionNeoForge` | class | — | Mod | 0 | 44 | [IllagerInvasionNeoForge.java](fuzs/illagerinvasion/neoforge/IllagerInvasionNeoForge.java) |
| `Invoker` | class | SpellcasterIllager | — | 77 | 729 | [Invoker.java](fuzs/illagerinvasion/world/entity/monster/Invoker.java) |
| `ClientModRegistry` | class | — | — | 1 | 79 | [ClientModRegistry.java](fuzs/illagerinvasion/client/init/ClientModRegistry.java) |
| `ModSoundEvents` | class | — | — | 2 | 61 | [ModSoundEvents.java](fuzs/illagerinvasion/init/ModSoundEvents.java) |
| `IllusionerMixin` | class | SpellcasterIllager | Mixin | 5 | 85 | [IllusionerMixin.java](fuzs/illagerinvasion/mixin/IllusionerMixin.java) |
| `FoodDataMixin` | class | — | Mixin | 2 | 45 | [FoodDataMixin.java](fuzs/illagerinvasion/mixin/FoodDataMixin.java) |
| `RaidWavesConfigHelper` | class | — | — | 11 | 59 | [RaidWavesConfigHelper.java](fuzs/illagerinvasion/config/RaidWavesConfigHelper.java) |
| `Surrendered` | class | Skeleton | — | 36 | 358 | [Surrendered.java](fuzs/illagerinvasion/world/entity/monster/Surrendered.java) |
| `Archivist` | class | SpellcasterIllager | — | 31 | 285 | [Archivist.java](fuzs/illagerinvasion/world/entity/monster/Archivist.java) |
| `Firecaller` | class | SpellcasterIllager | — | 31 | 270 | [Firecaller.java](fuzs/illagerinvasion/world/entity/monster/Firecaller.java) |
| `Necromancer` | class | SpellcasterIllager | — | 36 | 333 | [Necromancer.java](fuzs/illagerinvasion/world/entity/monster/Necromancer.java) |
| `VillagerGoalHandler` | class | — | — | 5 | 81 | [VillagerGoalHandler.java](fuzs/illagerinvasion/handler/VillagerGoalHandler.java) |
| `PlatinumTrimHandler` | class | — | — | 5 | 74 | [PlatinumTrimHandler.java](fuzs/illagerinvasion/handler/PlatinumTrimHandler.java) |
| `WoodlandMansionPieceMixin` | class | — | Mixin | 1 | 40 | [WoodlandMansionPieceMixin.java](fuzs/illagerinvasion/mixin/WoodlandMansionPieceMixin.java) |
| `PatrolSpawnerMixin` | class | — | Mixin | 1 | 21 | [PatrolSpawnerMixin.java](fuzs/illagerinvasion/mixin/PatrolSpawnerMixin.java) |
| `VillagerHostilesSensorAccessor` | interface | — | Mixin | 0 | 23 | [VillagerHostilesSensorAccessor.java](fuzs/illagerinvasion/mixin/accessor/VillagerHostilesSensorAccessor.java) |
| `IllagerModelMixin` | class | HierarchicalModel | Mixin | 1 | 80 | [IllagerModelMixin.java](fuzs/illagerinvasion/mixin/client/IllagerModelMixin.java) |
| `ModEntityTypeLootProvider` | class | EntityTypes | — | 1 | 323 | [ModEntityTypeLootProvider.java](fuzs/illagerinvasion/data/loot/ModEntityTypeLootProvider.java) |
| `Inquisitor` | class | AbstractIllager | — | 26 | 299 | [Inquisitor.java](fuzs/illagerinvasion/world/entity/monster/Inquisitor.java) |
| `Provoker` | class | SpellcasterIllager | — | 25 | 216 | [Provoker.java](fuzs/illagerinvasion/world/entity/monster/Provoker.java) |
| `Sorcerer` | class | SpellcasterIllager | — | 27 | 226 | [Sorcerer.java](fuzs/illagerinvasion/world/entity/monster/Sorcerer.java) |
| `ModRegistry` | class | — | — | 2 | 124 | [ModRegistry.java](fuzs/illagerinvasion/init/ModRegistry.java) |
| `ModChestLootProvider` | class | Simple | — | 1 | 547 | [ModChestLootProvider.java](fuzs/illagerinvasion/data/loot/ModChestLootProvider.java) |
| `ModBlockLootProvider` | class | Blocks | — | 1 | 18 | [ModBlockLootProvider.java](fuzs/illagerinvasion/data/loot/ModBlockLootProvider.java) |
| `ModEntityInjectLootProvider` | class | Simple | — | 1 | 95 | [ModEntityInjectLootProvider.java](fuzs/illagerinvasion/data/loot/ModEntityInjectLootProvider.java) |
| `ImbuingMenu` | class | AbstractContainerMenu | — | 13 | 204 | [ImbuingMenu.java](fuzs/illagerinvasion/world/inventory/ImbuingMenu.java) |
| `Alchemist` | class | AbstractIllager | — | 20 | 244 | [Alchemist.java](fuzs/illagerinvasion/world/entity/monster/Alchemist.java) |
| `Basher` | class | AbstractIllager | — | 22 | 238 | [Basher.java](fuzs/illagerinvasion/world/entity/monster/Basher.java) |
| `EnchantmentTooltipHandler` | class | — | — | 1 | 49 | [EnchantmentTooltipHandler.java](fuzs/illagerinvasion/client/handler/EnchantmentTooltipHandler.java) |
| `ServerConfig` | class | ConfigCore | — | 1 | 83 | [ServerConfig.java](fuzs/illagerinvasion/config/ServerConfig.java) |
| `ModRecipeProvider` | class | AbstractRecipeProvider | — | 1 | 58 | [ModRecipeProvider.java](fuzs/illagerinvasion/data/ModRecipeProvider.java) |
| `HatchetItem` | class | Item | — | 11 | 120 | [HatchetItem.java](fuzs/illagerinvasion/world/item/HatchetItem.java) |
| `HatIllagerEntityModel` | class | HierarchicalModel | — | 7 | 207 | [HatIllagerEntityModel.java](fuzs/illagerinvasion/client/model/HatIllagerEntityModel.java) |
| `ArmoredIllagerEntityModel` | class | HierarchicalModel | — | 7 | 250 | [ArmoredIllagerEntityModel.java](fuzs/illagerinvasion/client/model/ArmoredIllagerEntityModel.java) |
| `IllagerWithStaffEntityModel` | class | HierarchicalModel | — | 7 | 200 | [IllagerWithStaffEntityModel.java](fuzs/illagerinvasion/client/model/IllagerWithStaffEntityModel.java) |
| `InvokerEntityModel` | class | HierarchicalModel | — | 7 | 200 | [InvokerEntityModel.java](fuzs/illagerinvasion/client/model/InvokerEntityModel.java) |
| `IllagerInvasion` | class | ModConstructor | — | 11 | 273 | [IllagerInvasion.java](fuzs/illagerinvasion/IllagerInvasion.java) |
| `Marauder` | class | AbstractIllager | — | 16 | 147 | [Marauder.java](fuzs/illagerinvasion/world/entity/monster/Marauder.java) |
| `Hatchet` | class | AbstractArrow | — | 15 | 137 | [Hatchet.java](fuzs/illagerinvasion/world/entity/projectile/Hatchet.java) |
| `MagicFireBlock` | class | BaseFireBlock | — | 8 | 69 | [MagicFireBlock.java](fuzs/illagerinvasion/world/level/block/MagicFireBlock.java) |
| `IllagerInvasionClient` | class | ClientModConstructor | — | 8 | 114 | [IllagerInvasionClient.java](fuzs/illagerinvasion/client/IllagerInvasionClient.java) |
| `BrimmedHatIllagerEntityModel` | class | HierarchicalModel | — | 7 | 193 | [BrimmedHatIllagerEntityModel.java](fuzs/illagerinvasion/client/model/BrimmedHatIllagerEntityModel.java) |
| `CapedIllagerEntityModel` | class | HierarchicalModel | — | 7 | 191 | [CapedIllagerEntityModel.java](fuzs/illagerinvasion/client/model/CapedIllagerEntityModel.java) |
| `ModBlockTagProvider` | class | AbstractTagProvider | — | 1 | 21 | [ModBlockTagProvider.java](fuzs/illagerinvasion/data/tags/ModBlockTagProvider.java) |
| `ModItemTagProvider` | class | AbstractTagProvider | — | 1 | 21 | [ModItemTagProvider.java](fuzs/illagerinvasion/data/tags/ModItemTagProvider.java) |
| `ModEntityTypeTagProvider` | class | AbstractTagProvider | — | 1 | 62 | [ModEntityTypeTagProvider.java](fuzs/illagerinvasion/data/tags/ModEntityTypeTagProvider.java) |
| `InvokerFangs` | class | Entity | — | 10 | 159 | [InvokerFangs.java](fuzs/illagerinvasion/world/entity/monster/InvokerFangs.java) |
| `ImbuingTableBlock` | class | Block | — | 4 | 66 | [ImbuingTableBlock.java](fuzs/illagerinvasion/world/level/block/ImbuingTableBlock.java) |
| `IllusionaryDustItem` | class | Item | — | 1 | 41 | [IllusionaryDustItem.java](fuzs/illagerinvasion/world/item/IllusionaryDustItem.java) |
| `MagicalFireChargeItem` | class | FireChargeItem | — | 2 | 48 | [MagicalFireChargeItem.java](fuzs/illagerinvasion/world/item/MagicalFireChargeItem.java) |
| `LostCandleItem` | class | Item | — | 2 | 78 | [LostCandleItem.java](fuzs/illagerinvasion/world/item/LostCandleItem.java) |
| `HornOfSightItem` | class | InstrumentItem | — | 1 | 39 | [HornOfSightItem.java](fuzs/illagerinvasion/world/item/HornOfSightItem.java) |
| `FireworksShootingHelper` | class | — | — | 5 | 56 | [FireworksShootingHelper.java](fuzs/illagerinvasion/util/FireworksShootingHelper.java) |
| `ImbuingScreen` | class | AbstractContainerScreen | — | 2 | 36 | [ImbuingScreen.java](fuzs/illagerinvasion/client/gui/screens/inventory/ImbuingScreen.java) |
| `InvokerFangsRenderer` | class | EntityRenderer | — | 2 | 52 | [InvokerFangsRenderer.java](fuzs/illagerinvasion/client/render/entity/InvokerFangsRenderer.java) |
| `MagmaEntityRender` | class | EntityRenderer | — | 2 | 38 | [MagmaEntityRender.java](fuzs/illagerinvasion/client/render/entity/MagmaEntityRender.java) |
| `ModEntityTypes` | class | — | — | 1 | 58 | [ModEntityTypes.java](fuzs/illagerinvasion/init/ModEntityTypes.java) |
| `ModItems` | class | — | — | 1 | 68 | [ModItems.java](fuzs/illagerinvasion/init/ModItems.java) |
| `ModTrimMaterialDataProvider` | class | AbstractRegistriesDatapackGenerator | — | 1 | 21 | [ModTrimMaterialDataProvider.java](fuzs/illagerinvasion/data/ModTrimMaterialDataProvider.java) |
| `PotionBowAttackGoal` | class | Goal | — | 8 | 186 | [PotionBowAttackGoal.java](fuzs/illagerinvasion/world/entity/ai/goal/PotionBowAttackGoal.java) |
| `SkullBolt` | class | AbstractHurtingProjectile | — | 6 | 70 | [SkullBolt.java](fuzs/illagerinvasion/world/entity/projectile/SkullBolt.java) |
| `FlyingMagma` | class | AbstractHurtingProjectile | — | 7 | 80 | [FlyingMagma.java](fuzs/illagerinvasion/world/entity/projectile/FlyingMagma.java) |
| `EnchantToolUtil` | class | — | — | 4 | 52 | [EnchantToolUtil.java](fuzs/illagerinvasion/util/EnchantToolUtil.java) |
| `SetMagicFireUtil` | class | — | — | 2 | 26 | [SetMagicFireUtil.java](fuzs/illagerinvasion/util/SetMagicFireUtil.java) |
| `SpellParticleUtil` | class | — | — | 1 | 17 | [SpellParticleUtil.java](fuzs/illagerinvasion/util/SpellParticleUtil.java) |
| `TeleportUtil` | class | — | — | 2 | 35 | [TeleportUtil.java](fuzs/illagerinvasion/util/TeleportUtil.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_IllagerInvasion1211_Common_faac820a26784c08bea233f384981aa6_9ea9c62487e09fbb1f2c317d387eac3b25e4038fce2c0afd5d89088ad45d8ee2IllagerInvasionv21161211Commondevjar/PlatformMethods.java) |
| `ModSoundDefinitionProvider` | class | AbstractSoundDefinitionProvider | — | 1 | 134 | [ModSoundDefinitionProvider.java](fuzs/illagerinvasion/neoforge/data/client/ModSoundDefinitionProvider.java) |
| `ModSpriteSourceProvider` | class | AbstractSpriteSourceProvider | — | 1 | 18 | [ModSpriteSourceProvider.java](fuzs/illagerinvasion/neoforge/data/client/ModSpriteSourceProvider.java) |
| `ModParticleDescriptionProvider` | class | AbstractParticleDescriptionProvider | — | 1 | 18 | [ModParticleDescriptionProvider.java](fuzs/illagerinvasion/neoforge/data/client/ModParticleDescriptionProvider.java) |
| `ModInstrumentTagProvider` | class | AbstractTagProvider | — | 1 | 19 | [ModInstrumentTagProvider.java](fuzs/illagerinvasion/data/tags/ModInstrumentTagProvider.java) |
| `ModBiomeTagProvider` | class | AbstractTagProvider | — | 1 | 34 | [ModBiomeTagProvider.java](fuzs/illagerinvasion/data/tags/ModBiomeTagProvider.java) |
| `ModEnchantmentTagProvider` | class | AbstractTagProvider | — | 1 | 52 | [ModEnchantmentTagProvider.java](fuzs/illagerinvasion/data/tags/ModEnchantmentTagProvider.java) |
| `ModModelProvider` | class | AbstractModelProvider | — | 2 | 61 | [ModModelProvider.java](fuzs/illagerinvasion/data/client/ModModelProvider.java) |
| `ModLanguageProvider` | class | AbstractLanguageProvider | — | 1 | 121 | [ModLanguageProvider.java](fuzs/illagerinvasion/data/client/ModLanguageProvider.java) |
| `HatchetAttackGoal` | class | RangedAttackGoal | — | 4 | 96 | [HatchetAttackGoal.java](fuzs/illagerinvasion/world/entity/ai/goal/HatchetAttackGoal.java) |
| `LabyrinthStructure` | class | JigsawStructure | — | 2 | 80 | [LabyrinthStructure.java](fuzs/illagerinvasion/world/level/levelgen/structure/structures/LabyrinthStructure.java) |
| `SingleNoLiquidPoolElement` | class | SinglePoolElement | — | 3 | 47 | [SingleNoLiquidPoolElement.java](fuzs/illagerinvasion/world/level/levelgen/structure/pools/SingleNoLiquidPoolElement.java) |
| `LegacySingleNoLiquidPoolElement` | class | LegacySinglePoolElement | — | 3 | 44 | [LegacySingleNoLiquidPoolElement.java](fuzs/illagerinvasion/world/level/levelgen/structure/pools/LegacySingleNoLiquidPoolElement.java) |
| `InquisitorRender` | class | MobRenderer | — | 2 | 30 | [InquisitorRender.java](fuzs/illagerinvasion/client/render/entity/InquisitorRender.java) |
| `ArchivistRender` | class | IllagerRenderer | — | 2 | 76 | [ArchivistRender.java](fuzs/illagerinvasion/client/render/entity/ArchivistRender.java) |
| `HatchetRender` | class | ThrownItemRenderer | — | 1 | 36 | [HatchetRender.java](fuzs/illagerinvasion/client/render/entity/HatchetRender.java) |
| `SurrenderedRender` | class | HumanoidMobRenderer | — | 3 | 49 | [SurrenderedRender.java](fuzs/illagerinvasion/client/render/entity/SurrenderedRender.java) |
| `MarauderRender` | class | IllagerRenderer | — | 1 | 24 | [MarauderRender.java](fuzs/illagerinvasion/client/render/entity/MarauderRender.java) |
| `FirecallerRender` | class | MobRenderer | — | 2 | 28 | [FirecallerRender.java](fuzs/illagerinvasion/client/render/entity/FirecallerRender.java) |
| `BasherRender` | class | IllagerRenderer | — | 2 | 46 | [BasherRender.java](fuzs/illagerinvasion/client/render/entity/BasherRender.java) |
| `SorcererRender` | class | MobRenderer | — | 2 | 28 | [SorcererRender.java](fuzs/illagerinvasion/client/render/entity/SorcererRender.java) |
| `ProvokerRender` | class | IllagerRenderer | — | 2 | 38 | [ProvokerRender.java](fuzs/illagerinvasion/client/render/entity/ProvokerRender.java) |
| `NecromancerRender` | class | IllagerRenderer | — | 2 | 49 | [NecromancerRender.java](fuzs/illagerinvasion/client/render/entity/NecromancerRender.java) |
| `InvokerRender` | class | MobRenderer | — | 2 | 38 | [InvokerRender.java](fuzs/illagerinvasion/client/render/entity/InvokerRender.java) |
| `SkullBoltRender` | class | EntityRenderer | — | 3 | 46 | [SkullBoltRender.java](fuzs/illagerinvasion/client/render/entity/SkullBoltRender.java) |
| `AlchemistRender` | class | MobRenderer | — | 3 | 51 | [AlchemistRender.java](fuzs/illagerinvasion/client/render/entity/AlchemistRender.java) |
| `InvokerShieldLayer` | class | EnergySwirlLayer | — | 3 | 34 | [InvokerShieldLayer.java](fuzs/illagerinvasion/client/render/entity/layers/InvokerShieldLayer.java) |
| `NecromancerShieldLayer` | class | EnergySwirlLayer | — | 3 | 34 | [NecromancerShieldLayer.java](fuzs/illagerinvasion/client/render/entity/layers/NecromancerShieldLayer.java) |
| `InvokerGoldLayer` | class | EyesLayer | — | 1 | 23 | [InvokerGoldLayer.java](fuzs/illagerinvasion/client/render/entity/layers/InvokerGoldLayer.java) |
| `InvokerFangsModel` | class | HierarchicalModel | — | 3 | 84 | [InvokerFangsModel.java](fuzs/illagerinvasion/client/model/InvokerFangsModel.java) |
| `SurrenderedModel` | class | SkeletonModel | — | 1 | 20 | [SurrenderedModel.java](fuzs/illagerinvasion/client/model/SurrenderedModel.java) |
