# Gardens of the Dead（`gardens_of_the_dead`）

> jar：`[下界扩展]gardensofthedead.jar` ｜ 版本：`5.0.2` ｜ 作者：ochotonida
> 规模：**68 个类 / 21 个包 / 4641 行** ｜ 事件处理类 1 ｜ Mixin 3 ｜ 注册/管理类 3

**依赖**：`neoforge`、`minecraft`、`architectury`、`terrablender`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `gardensofthedead.registry` | 15 | `ModSoundEvents`、`ModBlocks`、`ModBlockEntityTypes` |
| `gardensofthedead.block` | 12 | `WhistlecaneBlock`、`SoulSporeBaseBlock`、`BlistercrownBlock` |
| `gardensofthedead.neoforge.datagen` | 11 | `BlockStateProvider`、`ConfiguredFeatureProvider`、`RecipeProvider` |
| `gardensofthedead.neoforge` | 3 | `GardensOfTheDeadData`、`GardensOfTheDeadNeoForge`、`GardensOfTheDeadNeoForgeClient` |
| `gardensofthedead.mixin` | 3 | `BlockSetTypeInvoker`、`WoodTypeInvoker`、`SimpleParticleTypeInvoker` |
| `gardensofthedead.feature` | 3 | `HugeFlatFungusFeature`、`SoulSporeColumnFeature`、`SoulblightForestVegetationFeature` |
| `gardensofthedead.network` | 2 | `NetworkHandler`、`WhistleEffectPacket` |
| `gardensofthedead.block.entity` | 2 | `HangingSignBlockEntity`、`SignBlockEntity` |
| `gardensofthedead.feature.configuration` | 2 | `HugeFlatFungusConfiguration`、`SoulSporeColumnConfiguration` |
| `gardensofthedead` | 2 | `GardensOfTheDeadClient`、`GardensOfTheDead` |
| `gardensofthedead.platform` | 2 | `PlatformHelper`、`PlatformServices` |
| `gardensofthedead.client.particle` | 2 | `SoulblightSporeProvider`、`WhistlecaneSmokeParticle` |
| `gardensofthedead.client` | 1 | `WhistleEventHandler` |
| `gardensofthedead.neoforge.block` | 1 | `StrippableLogBlock` |
| `gardensofthedead.neoforge.platform` | 1 | `NeoForgePlatformHelper` |
| `gardensofthedead.neoforge.datagen.registry` | 1 | `ModBlockFamilies` |
| `gardensofthedead.placementmodifier` | 1 | `CountOnEveryCeilingPlacement` |
| `architectury_inject_gardens_of_the_dead_common_afb4ff3fdd54426bb7efe79bfeb4aa58_f5e823c3215bbfb8156b40a6c87b3212fd56bcac7e085feef5e2fdccd952ac70gardensofthedeadcommon502devjar` | 1 | `PlatformMethods` |
| `gardensofthedead.region` | 1 | `GardensOfTheDeadRegion` |
| `gardensofthedead.neoforge.region` | 1 | `GardensOfTheDeadNeoForgeRegion` |
| `gardensofthedead.loot` | 1 | `MatchShears` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `GardensOfTheDeadData` | class | — | EventBusSubscriber | 3 | 78 | [GardensOfTheDeadData.java](gardensofthedead/neoforge/GardensOfTheDeadData.java) |
| `BlockStateProvider` | class | net.neoforged.neoforge.client.model. | — | 44 | 772 | [BlockStateProvider.java](gardensofthedead/neoforge/datagen/BlockStateProvider.java) |
| `GardensOfTheDeadNeoForge` | class | — | Mod | 1 | 33 | [GardensOfTheDeadNeoForge.java](gardensofthedead/neoforge/GardensOfTheDeadNeoForge.java) |
| `NetworkHandler` | class | — | — | 2 | 26 | [NetworkHandler.java](gardensofthedead/network/NetworkHandler.java) |
| `BlockSetTypeInvoker` | interface | — | Mixin | 0 | 14 | [BlockSetTypeInvoker.java](gardensofthedead/mixin/BlockSetTypeInvoker.java) |
| `ModSoundEvents` | class | — | — | 2 | 24 | [ModSoundEvents.java](gardensofthedead/registry/ModSoundEvents.java) |
| `WhistleEventHandler` | class | — | — | 4 | 67 | [WhistleEventHandler.java](gardensofthedead/client/WhistleEventHandler.java) |
| `ConfiguredFeatureProvider` | class | — | — | 7 | 248 | [ConfiguredFeatureProvider.java](gardensofthedead/neoforge/datagen/ConfiguredFeatureProvider.java) |
| `WoodTypeInvoker` | interface | — | Mixin | 0 | 14 | [WoodTypeInvoker.java](gardensofthedead/mixin/WoodTypeInvoker.java) |
| `SimpleParticleTypeInvoker` | interface | — | Mixin | 0 | 14 | [SimpleParticleTypeInvoker.java](gardensofthedead/mixin/SimpleParticleTypeInvoker.java) |
| `ModBlocks` | class | — | — | 17 | 225 | [ModBlocks.java](gardensofthedead/registry/ModBlocks.java) |
| `RecipeProvider` | class | net.minecraft.data.recipes.RecipePro | — | 5 | 62 | [RecipeProvider.java](gardensofthedead/neoforge/datagen/RecipeProvider.java) |
| `LootTableProvider` | class | net.minecraft.data.loot.LootTablePro | — | 10 | 152 | [LootTableProvider.java](gardensofthedead/neoforge/datagen/LootTableProvider.java) |
| `WhistlecaneBlock` | class | Block | — | 19 | 181 | [WhistlecaneBlock.java](gardensofthedead/block/WhistlecaneBlock.java) |
| `HangingSignBlockEntity` | class | net.minecraft.world.level.block.enti | — | 2 | 21 | [HangingSignBlockEntity.java](gardensofthedead/block/entity/HangingSignBlockEntity.java) |
| `SignBlockEntity` | class | net.minecraft.world.level.block.enti | — | 2 | 21 | [SignBlockEntity.java](gardensofthedead/block/entity/SignBlockEntity.java) |
| `HugeFlatFungusConfiguration` | record | FeatureConfiguration | — | 0 | 23 | [HugeFlatFungusConfiguration.java](gardensofthedead/feature/configuration/HugeFlatFungusConfiguration.java) |
| `SoulSporeColumnConfiguration` | record | FeatureConfiguration | — | 0 | 19 | [SoulSporeColumnConfiguration.java](gardensofthedead/feature/configuration/SoulSporeColumnConfiguration.java) |
| `ModBlockEntityTypes` | class | — | — | 0 | 41 | [ModBlockEntityTypes.java](gardensofthedead/registry/ModBlockEntityTypes.java) |
| `WhistleEffectPacket` | record | CustomPacketPayload | — | 1 | 34 | [WhistleEffectPacket.java](gardensofthedead/network/WhistleEffectPacket.java) |
| `SoulSporeBaseBlock` | class | Block | — | 6 | 71 | [SoulSporeBaseBlock.java](gardensofthedead/block/SoulSporeBaseBlock.java) |
| `BlistercrownBlock` | class | BushBlock | — | 6 | 55 | [BlistercrownBlock.java](gardensofthedead/block/BlistercrownBlock.java) |
| `SoulSporeBlock` | class | SoulSporeBaseBlock | — | 6 | 94 | [SoulSporeBlock.java](gardensofthedead/block/SoulSporeBlock.java) |
| `WhistlecaneFenceBlock` | class | CrossCollisionBlock | — | 9 | 110 | [WhistlecaneFenceBlock.java](gardensofthedead/block/WhistlecaneFenceBlock.java) |
| `ModBlockProperties` | class | — | — | 7 | 117 | [ModBlockProperties.java](gardensofthedead/registry/ModBlockProperties.java) |
| `ModLootConditions` | class | — | — | 0 | 15 | [ModLootConditions.java](gardensofthedead/registry/ModLootConditions.java) |
| `ModItems` | class | — | — | 6 | 111 | [ModItems.java](gardensofthedead/registry/ModItems.java) |
| `GardensOfTheDeadClient` | class | — | — | 1 | 11 | [GardensOfTheDeadClient.java](gardensofthedead/GardensOfTheDeadClient.java) |
| `GardensOfTheDeadNeoForgeClient` | class | — | — | 3 | 41 | [GardensOfTheDeadNeoForgeClient.java](gardensofthedead/neoforge/GardensOfTheDeadNeoForgeClient.java) |
| `StrippableLogBlock` | class | RotatedPillarBlock | — | 1 | 27 | [StrippableLogBlock.java](gardensofthedead/neoforge/block/StrippableLogBlock.java) |
| `NeoForgePlatformHelper` | class | PlatformHelper | — | 5 | 47 | [NeoForgePlatformHelper.java](gardensofthedead/neoforge/platform/NeoForgePlatformHelper.java) |
| `ItemTagsProvider` | class | net.minecraft.data.tags.ItemTagsProv | — | 1 | 44 | [ItemTagsProvider.java](gardensofthedead/neoforge/datagen/ItemTagsProvider.java) |
| `BlockTagsProvider` | class | net.neoforged.neoforge.common.data.B | — | 1 | 111 | [BlockTagsProvider.java](gardensofthedead/neoforge/datagen/BlockTagsProvider.java) |
| `ModBlockFamilies` | class | — | — | 2 | 62 | [ModBlockFamilies.java](gardensofthedead/neoforge/datagen/registry/ModBlockFamilies.java) |
| `WallSignBlock` | class | net.minecraft.world.level.block.Wall | — | 1 | 20 | [WallSignBlock.java](gardensofthedead/block/WallSignBlock.java) |
| `CeilingHangingSignBlock` | class | net.minecraft.world.level.block.Ceil | — | 1 | 20 | [CeilingHangingSignBlock.java](gardensofthedead/block/CeilingHangingSignBlock.java) |
| `TallBlistercrownBlock` | class | DoublePlantBlock | — | 2 | 31 | [TallBlistercrownBlock.java](gardensofthedead/block/TallBlistercrownBlock.java) |
| `SoulblightFungusBlock` | class | FungusBlock | — | 2 | 37 | [SoulblightFungusBlock.java](gardensofthedead/block/SoulblightFungusBlock.java) |
| `SoulblightSproutsBlock` | class | RootsBlock | — | 4 | 39 | [SoulblightSproutsBlock.java](gardensofthedead/block/SoulblightSproutsBlock.java) |
| `WallHangingSignBlock` | class | net.minecraft.world.level.block.Wall | — | 1 | 20 | [WallHangingSignBlock.java](gardensofthedead/block/WallHangingSignBlock.java) |
| `StandingSignBlock` | class | net.minecraft.world.level.block.Stan | — | 1 | 20 | [StandingSignBlock.java](gardensofthedead/block/StandingSignBlock.java) |
| `ModBlockSetTypes` | class | — | — | 3 | 77 | [ModBlockSetTypes.java](gardensofthedead/registry/ModBlockSetTypes.java) |
| `ModSoundTypes` | class | SoundType | — | 14 | 115 | [ModSoundTypes.java](gardensofthedead/registry/ModSoundTypes.java) |
| `CountOnEveryCeilingPlacement` | class | PlacementModifier | — | 6 | 92 | [CountOnEveryCeilingPlacement.java](gardensofthedead/placementmodifier/CountOnEveryCeilingPlacement.java) |
| `SoundDefinitionsProvider` | class | net.neoforged.neoforge.common.data.S | — | 5 | 47 | [SoundDefinitionsProvider.java](gardensofthedead/neoforge/datagen/SoundDefinitionsProvider.java) |
| `DataMapProvider` | class | net.neoforged.neoforge.common.data.D | — | 1 | 24 | [DataMapProvider.java](gardensofthedead/neoforge/datagen/DataMapProvider.java) |
| `HugeFlatFungusFeature` | class | Feature | — | 7 | 175 | [HugeFlatFungusFeature.java](gardensofthedead/feature/HugeFlatFungusFeature.java) |
| `PlatformHelper` | interface | — | — | 0 | 23 | [PlatformHelper.java](gardensofthedead/platform/PlatformHelper.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_gardens_of_the_dead_common_afb4ff3fdd54426bb7efe79bfeb4aa58_f5e823c3215bbfb8156b40a6c87b3212fd56bcac7e085feef5e2fdccd952ac70gardensofthedeadcommon502devjar/PlatformMethods.java) |
| `GardensOfTheDead` | class | — | — | 2 | 37 | [GardensOfTheDead.java](gardensofthedead/GardensOfTheDead.java) |
| `GardensOfTheDeadRegion` | interface | — | — | 0 | 25 | [GardensOfTheDeadRegion.java](gardensofthedead/region/GardensOfTheDeadRegion.java) |
| `GardensOfTheDeadNeoForgeRegion` | class | Region | — | 2 | 28 | [GardensOfTheDeadNeoForgeRegion.java](gardensofthedead/neoforge/region/GardensOfTheDeadNeoForgeRegion.java) |
| `BiomeTagsProvider` | class | net.minecraft.data.tags.BiomeTagsPro | — | 1 | 34 | [BiomeTagsProvider.java](gardensofthedead/neoforge/datagen/BiomeTagsProvider.java) |
| `BiomeProvider` | class | — | — | 4 | 158 | [BiomeProvider.java](gardensofthedead/neoforge/datagen/BiomeProvider.java) |
| `PlacedFeatureProvider` | class | — | — | 3 | 110 | [PlacedFeatureProvider.java](gardensofthedead/neoforge/datagen/PlacedFeatureProvider.java) |
| `SoulSporeColumnFeature` | class | Feature | — | 3 | 75 | [SoulSporeColumnFeature.java](gardensofthedead/feature/SoulSporeColumnFeature.java) |
| `SoulblightForestVegetationFeature` | class | Feature | — | 1 | 54 | [SoulblightForestVegetationFeature.java](gardensofthedead/feature/SoulblightForestVegetationFeature.java) |
| `PlatformServices` | class | — | — | 1 | 15 | [PlatformServices.java](gardensofthedead/platform/PlatformServices.java) |
| `MatchShears` | class | LootItemCondition | — | 4 | 32 | [MatchShears.java](gardensofthedead/loot/MatchShears.java) |
| `ModWoodTypes` | class | — | — | 4 | 61 | [ModWoodTypes.java](gardensofthedead/registry/ModWoodTypes.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `GardensOfTheDeadData` | 1 | `gardensofthedead.neoforge` | [GardensOfTheDeadData.java](gardensofthedead/neoforge/GardensOfTheDeadData.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockSetTypeInvoker` | `BlockSetTypeInvoker` | [BlockSetTypeInvoker.java](gardensofthedead/mixin/BlockSetTypeInvoker.java) |
| `WoodTypeInvoker` | `WoodTypeInvoker` | [WoodTypeInvoker.java](gardensofthedead/mixin/WoodTypeInvoker.java) |
| `SimpleParticleTypeInvoker` | `SimpleParticleTypeInvoker` | [SimpleParticleTypeInvoker.java](gardensofthedead/mixin/SimpleParticleTypeInvoker.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfiguredFeatureProvider` | — | [ConfiguredFeatureProvider.java](gardensofthedead/neoforge/datagen/ConfiguredFeatureProvider.java) |
| `HugeFlatFungusConfiguration` | — | [HugeFlatFungusConfiguration.java](gardensofthedead/feature/configuration/HugeFlatFungusConfiguration.java) |
| `SoulSporeColumnConfiguration` | — | [SoulSporeColumnConfiguration.java](gardensofthedead/feature/configuration/SoulSporeColumnConfiguration.java) |

## 全部类（68）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `GardensOfTheDeadData` | class | — | EventBusSubscriber | 3 | 78 | [GardensOfTheDeadData.java](gardensofthedead/neoforge/GardensOfTheDeadData.java) |
| `BlockStateProvider` | class | net.neoforged.neoforge.client.model. | — | 44 | 772 | [BlockStateProvider.java](gardensofthedead/neoforge/datagen/BlockStateProvider.java) |
| `GardensOfTheDeadNeoForge` | class | — | Mod | 1 | 33 | [GardensOfTheDeadNeoForge.java](gardensofthedead/neoforge/GardensOfTheDeadNeoForge.java) |
| `NetworkHandler` | class | — | — | 2 | 26 | [NetworkHandler.java](gardensofthedead/network/NetworkHandler.java) |
| `BlockSetTypeInvoker` | interface | — | Mixin | 0 | 14 | [BlockSetTypeInvoker.java](gardensofthedead/mixin/BlockSetTypeInvoker.java) |
| `ModSoundEvents` | class | — | — | 2 | 24 | [ModSoundEvents.java](gardensofthedead/registry/ModSoundEvents.java) |
| `WhistleEventHandler` | class | — | — | 4 | 67 | [WhistleEventHandler.java](gardensofthedead/client/WhistleEventHandler.java) |
| `ConfiguredFeatureProvider` | class | — | — | 7 | 248 | [ConfiguredFeatureProvider.java](gardensofthedead/neoforge/datagen/ConfiguredFeatureProvider.java) |
| `WoodTypeInvoker` | interface | — | Mixin | 0 | 14 | [WoodTypeInvoker.java](gardensofthedead/mixin/WoodTypeInvoker.java) |
| `SimpleParticleTypeInvoker` | interface | — | Mixin | 0 | 14 | [SimpleParticleTypeInvoker.java](gardensofthedead/mixin/SimpleParticleTypeInvoker.java) |
| `ModBlocks` | class | — | — | 17 | 225 | [ModBlocks.java](gardensofthedead/registry/ModBlocks.java) |
| `RecipeProvider` | class | net.minecraft.data.recipes.RecipePro | — | 5 | 62 | [RecipeProvider.java](gardensofthedead/neoforge/datagen/RecipeProvider.java) |
| `LootTableProvider` | class | net.minecraft.data.loot.LootTablePro | — | 10 | 152 | [LootTableProvider.java](gardensofthedead/neoforge/datagen/LootTableProvider.java) |
| `WhistlecaneBlock` | class | Block | — | 19 | 181 | [WhistlecaneBlock.java](gardensofthedead/block/WhistlecaneBlock.java) |
| `HangingSignBlockEntity` | class | net.minecraft.world.level.block.enti | — | 2 | 21 | [HangingSignBlockEntity.java](gardensofthedead/block/entity/HangingSignBlockEntity.java) |
| `SignBlockEntity` | class | net.minecraft.world.level.block.enti | — | 2 | 21 | [SignBlockEntity.java](gardensofthedead/block/entity/SignBlockEntity.java) |
| `HugeFlatFungusConfiguration` | record | FeatureConfiguration | — | 0 | 23 | [HugeFlatFungusConfiguration.java](gardensofthedead/feature/configuration/HugeFlatFungusConfiguration.java) |
| `SoulSporeColumnConfiguration` | record | FeatureConfiguration | — | 0 | 19 | [SoulSporeColumnConfiguration.java](gardensofthedead/feature/configuration/SoulSporeColumnConfiguration.java) |
| `ModBlockEntityTypes` | class | — | — | 0 | 41 | [ModBlockEntityTypes.java](gardensofthedead/registry/ModBlockEntityTypes.java) |
| `WhistleEffectPacket` | record | CustomPacketPayload | — | 1 | 34 | [WhistleEffectPacket.java](gardensofthedead/network/WhistleEffectPacket.java) |
| `SoulSporeBaseBlock` | class | Block | — | 6 | 71 | [SoulSporeBaseBlock.java](gardensofthedead/block/SoulSporeBaseBlock.java) |
| `BlistercrownBlock` | class | BushBlock | — | 6 | 55 | [BlistercrownBlock.java](gardensofthedead/block/BlistercrownBlock.java) |
| `SoulSporeBlock` | class | SoulSporeBaseBlock | — | 6 | 94 | [SoulSporeBlock.java](gardensofthedead/block/SoulSporeBlock.java) |
| `WhistlecaneFenceBlock` | class | CrossCollisionBlock | — | 9 | 110 | [WhistlecaneFenceBlock.java](gardensofthedead/block/WhistlecaneFenceBlock.java) |
| `ModBlockProperties` | class | — | — | 7 | 117 | [ModBlockProperties.java](gardensofthedead/registry/ModBlockProperties.java) |
| `ModLootConditions` | class | — | — | 0 | 15 | [ModLootConditions.java](gardensofthedead/registry/ModLootConditions.java) |
| `ModItems` | class | — | — | 6 | 111 | [ModItems.java](gardensofthedead/registry/ModItems.java) |
| `GardensOfTheDeadClient` | class | — | — | 1 | 11 | [GardensOfTheDeadClient.java](gardensofthedead/GardensOfTheDeadClient.java) |
| `GardensOfTheDeadNeoForgeClient` | class | — | — | 3 | 41 | [GardensOfTheDeadNeoForgeClient.java](gardensofthedead/neoforge/GardensOfTheDeadNeoForgeClient.java) |
| `StrippableLogBlock` | class | RotatedPillarBlock | — | 1 | 27 | [StrippableLogBlock.java](gardensofthedead/neoforge/block/StrippableLogBlock.java) |
| `NeoForgePlatformHelper` | class | PlatformHelper | — | 5 | 47 | [NeoForgePlatformHelper.java](gardensofthedead/neoforge/platform/NeoForgePlatformHelper.java) |
| `ItemTagsProvider` | class | net.minecraft.data.tags.ItemTagsProv | — | 1 | 44 | [ItemTagsProvider.java](gardensofthedead/neoforge/datagen/ItemTagsProvider.java) |
| `BlockTagsProvider` | class | net.neoforged.neoforge.common.data.B | — | 1 | 111 | [BlockTagsProvider.java](gardensofthedead/neoforge/datagen/BlockTagsProvider.java) |
| `ModBlockFamilies` | class | — | — | 2 | 62 | [ModBlockFamilies.java](gardensofthedead/neoforge/datagen/registry/ModBlockFamilies.java) |
| `WallSignBlock` | class | net.minecraft.world.level.block.Wall | — | 1 | 20 | [WallSignBlock.java](gardensofthedead/block/WallSignBlock.java) |
| `CeilingHangingSignBlock` | class | net.minecraft.world.level.block.Ceil | — | 1 | 20 | [CeilingHangingSignBlock.java](gardensofthedead/block/CeilingHangingSignBlock.java) |
| `TallBlistercrownBlock` | class | DoublePlantBlock | — | 2 | 31 | [TallBlistercrownBlock.java](gardensofthedead/block/TallBlistercrownBlock.java) |
| `SoulblightFungusBlock` | class | FungusBlock | — | 2 | 37 | [SoulblightFungusBlock.java](gardensofthedead/block/SoulblightFungusBlock.java) |
| `SoulblightSproutsBlock` | class | RootsBlock | — | 4 | 39 | [SoulblightSproutsBlock.java](gardensofthedead/block/SoulblightSproutsBlock.java) |
| `WallHangingSignBlock` | class | net.minecraft.world.level.block.Wall | — | 1 | 20 | [WallHangingSignBlock.java](gardensofthedead/block/WallHangingSignBlock.java) |
| `StandingSignBlock` | class | net.minecraft.world.level.block.Stan | — | 1 | 20 | [StandingSignBlock.java](gardensofthedead/block/StandingSignBlock.java) |
| `ModBlockSetTypes` | class | — | — | 3 | 77 | [ModBlockSetTypes.java](gardensofthedead/registry/ModBlockSetTypes.java) |
| `ModSoundTypes` | class | SoundType | — | 14 | 115 | [ModSoundTypes.java](gardensofthedead/registry/ModSoundTypes.java) |
| `CountOnEveryCeilingPlacement` | class | PlacementModifier | — | 6 | 92 | [CountOnEveryCeilingPlacement.java](gardensofthedead/placementmodifier/CountOnEveryCeilingPlacement.java) |
| `SoundDefinitionsProvider` | class | net.neoforged.neoforge.common.data.S | — | 5 | 47 | [SoundDefinitionsProvider.java](gardensofthedead/neoforge/datagen/SoundDefinitionsProvider.java) |
| `DataMapProvider` | class | net.neoforged.neoforge.common.data.D | — | 1 | 24 | [DataMapProvider.java](gardensofthedead/neoforge/datagen/DataMapProvider.java) |
| `HugeFlatFungusFeature` | class | Feature | — | 7 | 175 | [HugeFlatFungusFeature.java](gardensofthedead/feature/HugeFlatFungusFeature.java) |
| `PlatformHelper` | interface | — | — | 0 | 23 | [PlatformHelper.java](gardensofthedead/platform/PlatformHelper.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_gardens_of_the_dead_common_afb4ff3fdd54426bb7efe79bfeb4aa58_f5e823c3215bbfb8156b40a6c87b3212fd56bcac7e085feef5e2fdccd952ac70gardensofthedeadcommon502devjar/PlatformMethods.java) |
| `GardensOfTheDead` | class | — | — | 2 | 37 | [GardensOfTheDead.java](gardensofthedead/GardensOfTheDead.java) |
| `GardensOfTheDeadRegion` | interface | — | — | 0 | 25 | [GardensOfTheDeadRegion.java](gardensofthedead/region/GardensOfTheDeadRegion.java) |
| `GardensOfTheDeadNeoForgeRegion` | class | Region | — | 2 | 28 | [GardensOfTheDeadNeoForgeRegion.java](gardensofthedead/neoforge/region/GardensOfTheDeadNeoForgeRegion.java) |
| `BiomeTagsProvider` | class | net.minecraft.data.tags.BiomeTagsPro | — | 1 | 34 | [BiomeTagsProvider.java](gardensofthedead/neoforge/datagen/BiomeTagsProvider.java) |
| `BiomeProvider` | class | — | — | 4 | 158 | [BiomeProvider.java](gardensofthedead/neoforge/datagen/BiomeProvider.java) |
| `PlacedFeatureProvider` | class | — | — | 3 | 110 | [PlacedFeatureProvider.java](gardensofthedead/neoforge/datagen/PlacedFeatureProvider.java) |
| `SoulSporeColumnFeature` | class | Feature | — | 3 | 75 | [SoulSporeColumnFeature.java](gardensofthedead/feature/SoulSporeColumnFeature.java) |
| `SoulblightForestVegetationFeature` | class | Feature | — | 1 | 54 | [SoulblightForestVegetationFeature.java](gardensofthedead/feature/SoulblightForestVegetationFeature.java) |
| `PlatformServices` | class | — | — | 1 | 15 | [PlatformServices.java](gardensofthedead/platform/PlatformServices.java) |
| `MatchShears` | class | LootItemCondition | — | 4 | 32 | [MatchShears.java](gardensofthedead/loot/MatchShears.java) |
| `ModWoodTypes` | class | — | — | 4 | 61 | [ModWoodTypes.java](gardensofthedead/registry/ModWoodTypes.java) |
| `ModTags` | class | — | — | 2 | 28 | [ModTags.java](gardensofthedead/registry/ModTags.java) |
| `ModSurfaceRules` | class | — | — | 3 | 91 | [ModSurfaceRules.java](gardensofthedead/registry/ModSurfaceRules.java) |
| `ModBiomes` | class | — | — | 0 | 12 | [ModBiomes.java](gardensofthedead/registry/ModBiomes.java) |
| `ModParticleTypes` | class | — | — | 0 | 19 | [ModParticleTypes.java](gardensofthedead/registry/ModParticleTypes.java) |
| `ModPlacementModifiers` | class | — | — | 1 | 23 | [ModPlacementModifiers.java](gardensofthedead/registry/ModPlacementModifiers.java) |
| `ModFeatures` | class | — | — | 0 | 26 | [ModFeatures.java](gardensofthedead/registry/ModFeatures.java) |
| `SoulblightSporeProvider` | class | CrimsonSporeProvider | — | 1 | 32 | [SoulblightSporeProvider.java](gardensofthedead/client/particle/SoulblightSporeProvider.java) |
| `WhistlecaneSmokeParticle` | class | TextureSheetParticle | — | 3 | 48 | [WhistlecaneSmokeParticle.java](gardensofthedead/client/particle/WhistlecaneSmokeParticle.java) |
