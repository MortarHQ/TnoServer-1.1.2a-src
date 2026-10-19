# Mar Mar's Enhanced Ore Variety（`enhanced_ore_variety`）

> jar：`[矿]enhanced_ore_variety.jar` ｜ 版本：`1.21.1-neoforge-2.2.0` ｜ 作者：Mar Mar
> 规模：**19 个类 / 9 个包 / 1816 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 0

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.marmar.enhanced_ore_variety.world` | 4 | `EOVConfiguredFeatures`、`EOVBiomeModifiers`、`EOVOrePlacement` |
| `net.marmar.enhanced_ore_variety.data` | 3 | `DataGenerators`、`EOVTabs`、`WorldGenProvider` |
| `net.marmar.enhanced_ore_variety.data.tag` | 3 | `EOVBlockTagGenerator`、`EOVItemTagGenerator`、`EOVTags` |
| `net.marmar.enhanced_ore_variety.data.lang` | 3 | `EnglishLangProvider`、`Spanish1LangProvider`、`Spanish2LangProvider` |
| `net.marmar.enhanced_ore_variety.data.loot` | 2 | `EOVBlockLootTables`、`EOVLootTableProvider` |
| `net.marmar.enhanced_ore_variety` | 1 | `EnhancedOreVariety` |
| `net.marmar.enhanced_ore_variety.data.recipe` | 1 | `EOVRecipeProvider` |
| `net.marmar.enhanced_ore_variety.block` | 1 | `EOVBlocks` |
| `net.marmar.enhanced_ore_variety.data.model` | 1 | `EOVBlockstateProvider` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 19）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 47 | [DataGenerators.java](net/marmar/enhanced_ore_variety/data/DataGenerators.java) |
| `EnhancedOreVariety` | class | — | Mod | 0 | 17 | [EnhancedOreVariety.java](net/marmar/enhanced_ore_variety/EnhancedOreVariety.java) |
| `EOVBlockLootTables` | class | BlockLootSubProvider | — | 8 | 107 | [EOVBlockLootTables.java](net/marmar/enhanced_ore_variety/data/loot/EOVBlockLootTables.java) |
| `EOVRecipeProvider` | class | RecipeProvider | — | 2 | 45 | [EOVRecipeProvider.java](net/marmar/enhanced_ore_variety/data/recipe/EOVRecipeProvider.java) |
| `EOVConfiguredFeatures` | class | — | — | 3 | 156 | [EOVConfiguredFeatures.java](net/marmar/enhanced_ore_variety/world/EOVConfiguredFeatures.java) |
| `EOVLootTableProvider` | class | — | — | 1 | 19 | [EOVLootTableProvider.java](net/marmar/enhanced_ore_variety/data/loot/EOVLootTableProvider.java) |
| `EOVBlockTagGenerator` | class | BlockTagsProvider | — | 1 | 204 | [EOVBlockTagGenerator.java](net/marmar/enhanced_ore_variety/data/tag/EOVBlockTagGenerator.java) |
| `EOVBlocks` | class | — | — | 3 | 164 | [EOVBlocks.java](net/marmar/enhanced_ore_variety/block/EOVBlocks.java) |
| `EOVItemTagGenerator` | class | ItemTagsProvider | — | 1 | 110 | [EOVItemTagGenerator.java](net/marmar/enhanced_ore_variety/data/tag/EOVItemTagGenerator.java) |
| `EOVBlockstateProvider` | class | BlockStateProvider | — | 2 | 63 | [EOVBlockstateProvider.java](net/marmar/enhanced_ore_variety/data/model/EOVBlockstateProvider.java) |
| `EOVBiomeModifiers` | class | — | — | 4 | 402 | [EOVBiomeModifiers.java](net/marmar/enhanced_ore_variety/world/EOVBiomeModifiers.java) |
| `EOVTabs` | class | — | — | 1 | 70 | [EOVTabs.java](net/marmar/enhanced_ore_variety/data/EOVTabs.java) |
| `WorldGenProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 25 | [WorldGenProvider.java](net/marmar/enhanced_ore_variety/data/WorldGenProvider.java) |
| `EnglishLangProvider` | class | LanguageProvider | — | 1 | 57 | [EnglishLangProvider.java](net/marmar/enhanced_ore_variety/data/lang/EnglishLangProvider.java) |
| `Spanish1LangProvider` | class | LanguageProvider | — | 1 | 57 | [Spanish1LangProvider.java](net/marmar/enhanced_ore_variety/data/lang/Spanish1LangProvider.java) |
| `Spanish2LangProvider` | class | LanguageProvider | — | 1 | 57 | [Spanish2LangProvider.java](net/marmar/enhanced_ore_variety/data/lang/Spanish2LangProvider.java) |
| `EOVTags` | class | — | — | 1 | 17 | [EOVTags.java](net/marmar/enhanced_ore_variety/data/tag/EOVTags.java) |
| `EOVOrePlacement` | class | — | — | 3 | 23 | [EOVOrePlacement.java](net/marmar/enhanced_ore_variety/world/EOVOrePlacement.java) |
| `EOVPlacedFeatures` | class | — | — | 3 | 176 | [EOVPlacedFeatures.java](net/marmar/enhanced_ore_variety/world/EOVPlacedFeatures.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `DataGenerators` | 1 | `net.marmar.enhanced_ore_variety.data` | [DataGenerators.java](net/marmar/enhanced_ore_variety/data/DataGenerators.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `EOVConfiguredFeatures` | — | [EOVConfiguredFeatures.java](net/marmar/enhanced_ore_variety/world/EOVConfiguredFeatures.java) |

## 全部类（19）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 47 | [DataGenerators.java](net/marmar/enhanced_ore_variety/data/DataGenerators.java) |
| `EnhancedOreVariety` | class | — | Mod | 0 | 17 | [EnhancedOreVariety.java](net/marmar/enhanced_ore_variety/EnhancedOreVariety.java) |
| `EOVBlockLootTables` | class | BlockLootSubProvider | — | 8 | 107 | [EOVBlockLootTables.java](net/marmar/enhanced_ore_variety/data/loot/EOVBlockLootTables.java) |
| `EOVRecipeProvider` | class | RecipeProvider | — | 2 | 45 | [EOVRecipeProvider.java](net/marmar/enhanced_ore_variety/data/recipe/EOVRecipeProvider.java) |
| `EOVConfiguredFeatures` | class | — | — | 3 | 156 | [EOVConfiguredFeatures.java](net/marmar/enhanced_ore_variety/world/EOVConfiguredFeatures.java) |
| `EOVLootTableProvider` | class | — | — | 1 | 19 | [EOVLootTableProvider.java](net/marmar/enhanced_ore_variety/data/loot/EOVLootTableProvider.java) |
| `EOVBlockTagGenerator` | class | BlockTagsProvider | — | 1 | 204 | [EOVBlockTagGenerator.java](net/marmar/enhanced_ore_variety/data/tag/EOVBlockTagGenerator.java) |
| `EOVBlocks` | class | — | — | 3 | 164 | [EOVBlocks.java](net/marmar/enhanced_ore_variety/block/EOVBlocks.java) |
| `EOVItemTagGenerator` | class | ItemTagsProvider | — | 1 | 110 | [EOVItemTagGenerator.java](net/marmar/enhanced_ore_variety/data/tag/EOVItemTagGenerator.java) |
| `EOVBlockstateProvider` | class | BlockStateProvider | — | 2 | 63 | [EOVBlockstateProvider.java](net/marmar/enhanced_ore_variety/data/model/EOVBlockstateProvider.java) |
| `EOVBiomeModifiers` | class | — | — | 4 | 402 | [EOVBiomeModifiers.java](net/marmar/enhanced_ore_variety/world/EOVBiomeModifiers.java) |
| `EOVTabs` | class | — | — | 1 | 70 | [EOVTabs.java](net/marmar/enhanced_ore_variety/data/EOVTabs.java) |
| `WorldGenProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 25 | [WorldGenProvider.java](net/marmar/enhanced_ore_variety/data/WorldGenProvider.java) |
| `EnglishLangProvider` | class | LanguageProvider | — | 1 | 57 | [EnglishLangProvider.java](net/marmar/enhanced_ore_variety/data/lang/EnglishLangProvider.java) |
| `Spanish1LangProvider` | class | LanguageProvider | — | 1 | 57 | [Spanish1LangProvider.java](net/marmar/enhanced_ore_variety/data/lang/Spanish1LangProvider.java) |
| `Spanish2LangProvider` | class | LanguageProvider | — | 1 | 57 | [Spanish2LangProvider.java](net/marmar/enhanced_ore_variety/data/lang/Spanish2LangProvider.java) |
| `EOVTags` | class | — | — | 1 | 17 | [EOVTags.java](net/marmar/enhanced_ore_variety/data/tag/EOVTags.java) |
| `EOVOrePlacement` | class | — | — | 3 | 23 | [EOVOrePlacement.java](net/marmar/enhanced_ore_variety/world/EOVOrePlacement.java) |
| `EOVPlacedFeatures` | class | — | — | 3 | 176 | [EOVPlacedFeatures.java](net/marmar/enhanced_ore_variety/world/EOVPlacedFeatures.java) |
