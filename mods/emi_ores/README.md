# EMI Ores（`emi_ores`）

> jar：`[EMI附属]emi_ores.jar` ｜ 版本：`1.2+1.21.1+neoforge` ｜ 作者：Abbie
> 规模：**31 个类 / 12 个包 / 1323 行** ｜ 事件处理类 0 ｜ Mixin 13 ｜ 注册/管理类 0

**官方描述**：Easily view ore generation information from inside EMI.

**依赖**：`neoforge`、`minecraft`、`emi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `cc.abbie.emi_ores.mixin.accessor` | 13 | `RandomBlockMatchTestAccessor`、`BlockStateMatchTestAccessor`、`BlockMatchTestAccessor` |
| `cc.abbie.emi_ores.compat.emi.recipe` | 3 | `AbstractPlacedFeatureEmiRecipe`、`GeodeGenEmiRecipe`、`OreGenEmiRecipe` |
| `cc.abbie.emi_ores.neoforge` | 2 | `EmiOresNeoForge`、`PlatformImpl` |
| `cc.abbie.emi_ores.compat.emi` | 2 | `EmiOresRecipeCategories`、`EmiOresEmiPlugin` |
| `cc.abbie.emi_ores.client` | 2 | `EmiOresClient`、`FeaturesReciever` |
| `cc.abbie.emi_ores` | 2 | `EmiOres`、`Platform` |
| `cc.abbie.emi_ores.networking.payload` | 2 | `S2CSendFeaturesPayload`、`S2CSendBiomeInfoPayload` |
| `cc.abbie.emi_ores.neoforge.client` | 1 | `EmiOresNeoForgeClient` |
| `cc.abbie.emi_ores.client.config` | 1 | `EmiOresClientConfig` |
| `cc.abbie.emi_ores.compat.emi.stack` | 1 | `BiomeEmiStack` |
| `cc.abbie.emi_ores.networking` | 1 | `FeaturesSender` |
| `architectury_inject_emiores_xplat_57fb3f3fbb124178ada672119b09f53e_e7b928d7b449bb31da3d2e6a0b8f9e2647f21bb596ef1cfe70fc9de164ae1989emi_ores121211xplatdevjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 31）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EmiOresNeoForgeClient` | class | — | Mod | 0 | 18 | [EmiOresNeoForgeClient.java](cc/abbie/emi_ores/neoforge/client/EmiOresNeoForgeClient.java) |
| `EmiOresNeoForge` | class | — | Mod | 2 | 43 | [EmiOresNeoForge.java](cc/abbie/emi_ores/neoforge/EmiOresNeoForge.java) |
| `RandomBlockMatchTestAccessor` | interface | — | Mixin | 0 | 16 | [RandomBlockMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/RandomBlockMatchTestAccessor.java) |
| `BlockStateMatchTestAccessor` | interface | — | Mixin | 0 | 13 | [BlockStateMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/BlockStateMatchTestAccessor.java) |
| `BlockMatchTestAccessor` | interface | — | Mixin | 0 | 13 | [BlockMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/BlockMatchTestAccessor.java) |
| `RandomBlockStateMatchTestAccessor` | interface | — | Mixin | 0 | 16 | [RandomBlockStateMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/RandomBlockStateMatchTestAccessor.java) |
| `TagMatchTestAccessor` | interface | — | Mixin | 0 | 14 | [TagMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/TagMatchTestAccessor.java) |
| `TrapezoidHeightAccessor` | interface | — | Mixin | 0 | 19 | [TrapezoidHeightAccessor.java](cc/abbie/emi_ores/mixin/accessor/TrapezoidHeightAccessor.java) |
| `WeightedStateProviderAccessor` | interface | — | Mixin | 0 | 14 | [WeightedStateProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/WeightedStateProviderAccessor.java) |
| `SimpleStateProviderAccessor` | interface | — | Mixin | 0 | 13 | [SimpleStateProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/SimpleStateProviderAccessor.java) |
| `UniformHeightAccessor` | interface | — | Mixin | 0 | 16 | [UniformHeightAccessor.java](cc/abbie/emi_ores/mixin/accessor/UniformHeightAccessor.java) |
| `RarityFilterAccessor` | interface | — | Mixin | 0 | 12 | [RarityFilterAccessor.java](cc/abbie/emi_ores/mixin/accessor/RarityFilterAccessor.java) |
| `CountPlacementAccessor` | interface | — | Mixin | 0 | 13 | [CountPlacementAccessor.java](cc/abbie/emi_ores/mixin/accessor/CountPlacementAccessor.java) |
| `NoiseProviderAccessor` | interface | — | Mixin | 0 | 14 | [NoiseProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/NoiseProviderAccessor.java) |
| `HeightRangePlacementAccessor` | interface | — | Mixin | 0 | 13 | [HeightRangePlacementAccessor.java](cc/abbie/emi_ores/mixin/accessor/HeightRangePlacementAccessor.java) |
| `AbstractPlacedFeatureEmiRecipe` | class | EmiRecipe | — | 7 | 207 | [AbstractPlacedFeatureEmiRecipe.java](cc/abbie/emi_ores/compat/emi/recipe/AbstractPlacedFeatureEmiRecipe.java) |
| `EmiOresClientConfig` | record | — | — | 4 | 54 | [EmiOresClientConfig.java](cc/abbie/emi_ores/client/config/EmiOresClientConfig.java) |
| `GeodeGenEmiRecipe` | class | AbstractPlacedFeatureEmiRecipe | — | 9 | 159 | [GeodeGenEmiRecipe.java](cc/abbie/emi_ores/compat/emi/recipe/GeodeGenEmiRecipe.java) |
| `OreGenEmiRecipe` | class | AbstractPlacedFeatureEmiRecipe | — | 8 | 190 | [OreGenEmiRecipe.java](cc/abbie/emi_ores/compat/emi/recipe/OreGenEmiRecipe.java) |
| `EmiOresRecipeCategories` | class | — | — | 0 | 12 | [EmiOresRecipeCategories.java](cc/abbie/emi_ores/compat/emi/EmiOresRecipeCategories.java) |
| `BiomeEmiStack` | class | EmiStack | — | 13 | 117 | [BiomeEmiStack.java](cc/abbie/emi_ores/compat/emi/stack/BiomeEmiStack.java) |
| `EmiOresClient` | class | — | — | 1 | 10 | [EmiOresClient.java](cc/abbie/emi_ores/client/EmiOresClient.java) |
| `FeaturesReciever` | class | — | — | 5 | 39 | [FeaturesReciever.java](cc/abbie/emi_ores/client/FeaturesReciever.java) |
| `EmiOres` | class | — | — | 2 | 18 | [EmiOres.java](cc/abbie/emi_ores/EmiOres.java) |
| `Platform` | class | — | — | 1 | 15 | [Platform.java](cc/abbie/emi_ores/Platform.java) |
| `FeaturesSender` | class | — | — | 2 | 79 | [FeaturesSender.java](cc/abbie/emi_ores/networking/FeaturesSender.java) |
| `S2CSendFeaturesPayload` | record | CustomPacketPayload | — | 3 | 56 | [S2CSendFeaturesPayload.java](cc/abbie/emi_ores/networking/payload/S2CSendFeaturesPayload.java) |
| `S2CSendBiomeInfoPayload` | record | CustomPacketPayload | — | 3 | 44 | [S2CSendBiomeInfoPayload.java](cc/abbie/emi_ores/networking/payload/S2CSendBiomeInfoPayload.java) |
| `PlatformImpl` | class | — | — | 1 | 11 | [PlatformImpl.java](cc/abbie/emi_ores/neoforge/PlatformImpl.java) |
| `EmiOresEmiPlugin` | class | EmiPlugin | EmiEntrypoint | 2 | 57 | [EmiOresEmiPlugin.java](cc/abbie/emi_ores/compat/emi/EmiOresEmiPlugin.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_emiores_xplat_57fb3f3fbb124178ada672119b09f53e_e7b928d7b449bb31da3d2e6a0b8f9e2647f21bb596ef1cfe70fc9de164ae1989emi_ores121211xplatdevjar/PlatformMethods.java) |

## Mixin（13 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `RandomBlockMatchTestAccessor` | `RandomBlockMatchTestAccessor` | [RandomBlockMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/RandomBlockMatchTestAccessor.java) |
| `BlockStateMatchTestAccessor` | `BlockStateMatchTestAccessor` | [BlockStateMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/BlockStateMatchTestAccessor.java) |
| `BlockMatchTestAccessor` | `BlockMatchTestAccessor` | [BlockMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/BlockMatchTestAccessor.java) |
| `RandomBlockStateMatchTestAccessor` | `RandomBlockStateMatchTestAccessor` | [RandomBlockStateMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/RandomBlockStateMatchTestAccessor.java) |
| `TagMatchTestAccessor` | `TagMatchTestAccessor` | [TagMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/TagMatchTestAccessor.java) |
| `TrapezoidHeightAccessor` | `TrapezoidHeightAccessor` | [TrapezoidHeightAccessor.java](cc/abbie/emi_ores/mixin/accessor/TrapezoidHeightAccessor.java) |
| `WeightedStateProviderAccessor` | `WeightedStateProviderAccessor` | [WeightedStateProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/WeightedStateProviderAccessor.java) |
| `SimpleStateProviderAccessor` | `SimpleStateProviderAccessor` | [SimpleStateProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/SimpleStateProviderAccessor.java) |
| `UniformHeightAccessor` | `UniformHeightAccessor` | [UniformHeightAccessor.java](cc/abbie/emi_ores/mixin/accessor/UniformHeightAccessor.java) |
| `RarityFilterAccessor` | `RarityFilterAccessor` | [RarityFilterAccessor.java](cc/abbie/emi_ores/mixin/accessor/RarityFilterAccessor.java) |
| `CountPlacementAccessor` | `CountPlacementAccessor` | [CountPlacementAccessor.java](cc/abbie/emi_ores/mixin/accessor/CountPlacementAccessor.java) |
| `NoiseProviderAccessor` | `NoiseProviderAccessor` | [NoiseProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/NoiseProviderAccessor.java) |
| `HeightRangePlacementAccessor` | `HeightRangePlacementAccessor` | [HeightRangePlacementAccessor.java](cc/abbie/emi_ores/mixin/accessor/HeightRangePlacementAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `EmiOresClientConfig` | — | [EmiOresClientConfig.java](cc/abbie/emi_ores/client/config/EmiOresClientConfig.java) |

## 全部类（31）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EmiOresNeoForgeClient` | class | — | Mod | 0 | 18 | [EmiOresNeoForgeClient.java](cc/abbie/emi_ores/neoforge/client/EmiOresNeoForgeClient.java) |
| `EmiOresNeoForge` | class | — | Mod | 2 | 43 | [EmiOresNeoForge.java](cc/abbie/emi_ores/neoforge/EmiOresNeoForge.java) |
| `RandomBlockMatchTestAccessor` | interface | — | Mixin | 0 | 16 | [RandomBlockMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/RandomBlockMatchTestAccessor.java) |
| `BlockStateMatchTestAccessor` | interface | — | Mixin | 0 | 13 | [BlockStateMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/BlockStateMatchTestAccessor.java) |
| `BlockMatchTestAccessor` | interface | — | Mixin | 0 | 13 | [BlockMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/BlockMatchTestAccessor.java) |
| `RandomBlockStateMatchTestAccessor` | interface | — | Mixin | 0 | 16 | [RandomBlockStateMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/RandomBlockStateMatchTestAccessor.java) |
| `TagMatchTestAccessor` | interface | — | Mixin | 0 | 14 | [TagMatchTestAccessor.java](cc/abbie/emi_ores/mixin/accessor/TagMatchTestAccessor.java) |
| `TrapezoidHeightAccessor` | interface | — | Mixin | 0 | 19 | [TrapezoidHeightAccessor.java](cc/abbie/emi_ores/mixin/accessor/TrapezoidHeightAccessor.java) |
| `WeightedStateProviderAccessor` | interface | — | Mixin | 0 | 14 | [WeightedStateProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/WeightedStateProviderAccessor.java) |
| `SimpleStateProviderAccessor` | interface | — | Mixin | 0 | 13 | [SimpleStateProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/SimpleStateProviderAccessor.java) |
| `UniformHeightAccessor` | interface | — | Mixin | 0 | 16 | [UniformHeightAccessor.java](cc/abbie/emi_ores/mixin/accessor/UniformHeightAccessor.java) |
| `RarityFilterAccessor` | interface | — | Mixin | 0 | 12 | [RarityFilterAccessor.java](cc/abbie/emi_ores/mixin/accessor/RarityFilterAccessor.java) |
| `CountPlacementAccessor` | interface | — | Mixin | 0 | 13 | [CountPlacementAccessor.java](cc/abbie/emi_ores/mixin/accessor/CountPlacementAccessor.java) |
| `NoiseProviderAccessor` | interface | — | Mixin | 0 | 14 | [NoiseProviderAccessor.java](cc/abbie/emi_ores/mixin/accessor/NoiseProviderAccessor.java) |
| `HeightRangePlacementAccessor` | interface | — | Mixin | 0 | 13 | [HeightRangePlacementAccessor.java](cc/abbie/emi_ores/mixin/accessor/HeightRangePlacementAccessor.java) |
| `AbstractPlacedFeatureEmiRecipe` | class | EmiRecipe | — | 7 | 207 | [AbstractPlacedFeatureEmiRecipe.java](cc/abbie/emi_ores/compat/emi/recipe/AbstractPlacedFeatureEmiRecipe.java) |
| `EmiOresClientConfig` | record | — | — | 4 | 54 | [EmiOresClientConfig.java](cc/abbie/emi_ores/client/config/EmiOresClientConfig.java) |
| `GeodeGenEmiRecipe` | class | AbstractPlacedFeatureEmiRecipe | — | 9 | 159 | [GeodeGenEmiRecipe.java](cc/abbie/emi_ores/compat/emi/recipe/GeodeGenEmiRecipe.java) |
| `OreGenEmiRecipe` | class | AbstractPlacedFeatureEmiRecipe | — | 8 | 190 | [OreGenEmiRecipe.java](cc/abbie/emi_ores/compat/emi/recipe/OreGenEmiRecipe.java) |
| `EmiOresRecipeCategories` | class | — | — | 0 | 12 | [EmiOresRecipeCategories.java](cc/abbie/emi_ores/compat/emi/EmiOresRecipeCategories.java) |
| `BiomeEmiStack` | class | EmiStack | — | 13 | 117 | [BiomeEmiStack.java](cc/abbie/emi_ores/compat/emi/stack/BiomeEmiStack.java) |
| `EmiOresClient` | class | — | — | 1 | 10 | [EmiOresClient.java](cc/abbie/emi_ores/client/EmiOresClient.java) |
| `FeaturesReciever` | class | — | — | 5 | 39 | [FeaturesReciever.java](cc/abbie/emi_ores/client/FeaturesReciever.java) |
| `EmiOres` | class | — | — | 2 | 18 | [EmiOres.java](cc/abbie/emi_ores/EmiOres.java) |
| `Platform` | class | — | — | 1 | 15 | [Platform.java](cc/abbie/emi_ores/Platform.java) |
| `FeaturesSender` | class | — | — | 2 | 79 | [FeaturesSender.java](cc/abbie/emi_ores/networking/FeaturesSender.java) |
| `S2CSendFeaturesPayload` | record | CustomPacketPayload | — | 3 | 56 | [S2CSendFeaturesPayload.java](cc/abbie/emi_ores/networking/payload/S2CSendFeaturesPayload.java) |
| `S2CSendBiomeInfoPayload` | record | CustomPacketPayload | — | 3 | 44 | [S2CSendBiomeInfoPayload.java](cc/abbie/emi_ores/networking/payload/S2CSendBiomeInfoPayload.java) |
| `PlatformImpl` | class | — | — | 1 | 11 | [PlatformImpl.java](cc/abbie/emi_ores/neoforge/PlatformImpl.java) |
| `EmiOresEmiPlugin` | class | EmiPlugin | EmiEntrypoint | 2 | 57 | [EmiOresEmiPlugin.java](cc/abbie/emi_ores/compat/emi/EmiOresEmiPlugin.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_emiores_xplat_57fb3f3fbb124178ada672119b09f53e_e7b928d7b449bb31da3d2e6a0b8f9e2647f21bb596ef1cfe70fc9de164ae1989emi_ores121211xplatdevjar/PlatformMethods.java) |
