# Iron Chests（`ironchest`）

> jar：`[更多箱子]ironchest.jar` ｜ 版本：`1.21-neoforge-16.0.7` ｜ 作者：cpw, alexbegt, progwml6
> 规模：**62 个类 / 21 个包 / 3787 行** ｜ 事件处理类 2 ｜ Mixin 0 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.progwml6.ironchest.common.block.regular` | 8 | `AbstractIronChestBlock`、`CopperChestBlock`、`DiamondChestBlock` |
| `com.progwml6.ironchest.common.block.regular.entity` | 8 | `AbstractIronChestBlockEntity`、`CrystalChestBlockEntity`、`DirtChestBlockEntity` |
| `com.progwml6.ironchest.common.block.trapped.entity` | 8 | `TrappedCrystalChestBlockEntity`、`TrappedDirtChestBlockEntity`、`TrappedObsidianChestBlockEntity` |
| `com.progwml6.ironchest.common.block.trapped` | 8 | `TrappedIronChestBlock`、`TrappedDirtChestBlock`、`TrappedGoldChestBlock` |
| `com.progwml6.ironchest.common.data` | 4 | `IronChestsRecipeProvider`、`IronChestsBlockTags`、`IronChestsSpriteSourceProvider` |
| `com.progwml6.ironchest.common.item` | 4 | `IronChestBlockItem`、`ChestUpgradeItem`、`IronChestsItems` |
| `com.progwml6.ironchest.common.inventory` | 3 | `IronChestMenu`、`IronChestsMenuTypes`、`DirtChestSlot` |
| `com.progwml6.ironchest.common.ai` | 2 | `CatsSitOnChestsHandler`、`IronChestCatSitOnBlockGoal` |
| `com.progwml6.ironchest.common.data.loot` | 2 | `IronChestsBlockLoot`、`IronChestsLootTableProvider` |
| `com.progwml6.ironchest.common.block.entity` | 2 | `IronChestsBlockEntityTypes`、`ICrystalChest` |
| `com.progwml6.ironchest.client.model.inventory` | 2 | `IronChestItemStackRenderer`、`ModelItem` |
| `com.progwml6.ironchest.common.block` | 2 | `IronChestsBlocks`、`IronChestsTypes` |
| `com.progwml6.ironchest.client` | 1 | `IronChestsClientRegistration` |
| `com.progwml6.ironchest` | 1 | `IronChests` |
| `com.progwml6.ironchest.common.network` | 1 | `TopStacksSyncPacket` |
| `com.progwml6.ironchest.client.render` | 1 | `IronChestRenderer` |
| `com.progwml6.ironchest.client.screen` | 1 | `IronChestScreen` |
| `com.progwml6.ironchest.common` | 1 | `Util` |
| `com.progwml6.ironchest.common.datacomponents` | 1 | `IronChestsDataComponents` |
| `com.progwml6.ironchest.common.creativetabs` | 1 | `IronChestsCreativeTabs` |
| `com.progwml6.ironchest.client.model` | 1 | `IronChestsModels` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IronChestsClientRegistration` | class | — | EventBusSubscriber | 3 | 57 | [IronChestsClientRegistration.java](com/progwml6/ironchest/client/IronChestsClientRegistration.java) |
| `CatsSitOnChestsHandler` | class | — | EventBusSubscriber | 0 | 32 | [CatsSitOnChestsHandler.java](com/progwml6/ironchest/common/ai/CatsSitOnChestsHandler.java) |
| `IronChests` | class | — | Mod | 3 | 88 | [IronChests.java](com/progwml6/ironchest/IronChests.java) |
| `AbstractIronChestBlock` | class | BaseEntityBlock | — | 38 | 300 | [AbstractIronChestBlock.java](com/progwml6/ironchest/common/block/regular/AbstractIronChestBlock.java) |
| `AbstractIronChestBlockEntity` | class | RandomizableContainerBlockEntity | — | 21 | 184 | [AbstractIronChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/AbstractIronChestBlockEntity.java) |
| `CrystalChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 11 | 85 | [CrystalChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/CrystalChestBlockEntity.java) |
| `TrappedCrystalChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 11 | 90 | [TrappedCrystalChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedCrystalChestBlockEntity.java) |
| `IronChestsRecipeProvider` | class | RecipeProvider | — | 6 | 226 | [IronChestsRecipeProvider.java](com/progwml6/ironchest/common/data/IronChestsRecipeProvider.java) |
| `IronChestMenu` | class | AbstractContainerMenu | — | 19 | 183 | [IronChestMenu.java](com/progwml6/ironchest/common/inventory/IronChestMenu.java) |
| `DirtChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 6 | 62 | [DirtChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/DirtChestBlockEntity.java) |
| `TrappedDirtChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 6 | 68 | [TrappedDirtChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedDirtChestBlockEntity.java) |
| `IronChestsBlockLoot` | class | BlockLootSubProvider | — | 4 | 75 | [IronChestsBlockLoot.java](com/progwml6/ironchest/common/data/loot/IronChestsBlockLoot.java) |
| `IronChestsBlockEntityTypes` | class | — | — | 1 | 76 | [IronChestsBlockEntityTypes.java](com/progwml6/ironchest/common/block/entity/IronChestsBlockEntityTypes.java) |
| `DiamondChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 24 | [DiamondChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/DiamondChestBlockEntity.java) |
| `GoldChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 22 | [GoldChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/GoldChestBlockEntity.java) |
| `ObsidianChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 28 | [ObsidianChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/ObsidianChestBlockEntity.java) |
| `CopperChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 22 | [CopperChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/CopperChestBlockEntity.java) |
| `IronChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 22 | [IronChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/IronChestBlockEntity.java) |
| `TrappedObsidianChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedObsidianChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedObsidianChestBlockEntity.java) |
| `TrappedGoldChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedGoldChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedGoldChestBlockEntity.java) |
| `TrappedCopperChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedCopperChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedCopperChestBlockEntity.java) |
| `TrappedDiamondChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedDiamondChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedDiamondChestBlockEntity.java) |
| `AbstractTrappedIronChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 29 | [AbstractTrappedIronChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/AbstractTrappedIronChestBlockEntity.java) |
| `TrappedIronChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedIronChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedIronChestBlockEntity.java) |
| `IronChestBlockItem` | class | BlockItem | — | 2 | 139 | [IronChestBlockItem.java](com/progwml6/ironchest/common/item/IronChestBlockItem.java) |
| `IronChestItemStackRenderer` | class | BlockEntityWithoutLevelRenderer | OnlyIn | 1 | 32 | [IronChestItemStackRenderer.java](com/progwml6/ironchest/client/model/inventory/IronChestItemStackRenderer.java) |
| `TopStacksSyncPacket` | class | CustomPacketPayload | — | 3 | 66 | [TopStacksSyncPacket.java](com/progwml6/ironchest/common/network/TopStacksSyncPacket.java) |
| `IronChestsLootTableProvider` | class | LootTableProvider | — | 0 | 17 | [IronChestsLootTableProvider.java](com/progwml6/ironchest/common/data/loot/IronChestsLootTableProvider.java) |
| `IronChestRenderer` | class | BlockEntityRenderer<T> | OnlyIn | 5 | 173 | [IronChestRenderer.java](com/progwml6/ironchest/client/render/IronChestRenderer.java) |
| `IronChestsMenuTypes` | class | — | — | 0 | 33 | [IronChestsMenuTypes.java](com/progwml6/ironchest/common/inventory/IronChestsMenuTypes.java) |
| `IronChestsBlocks` | class | — | — | 4 | 97 | [IronChestsBlocks.java](com/progwml6/ironchest/common/block/IronChestsBlocks.java) |
| `CopperChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [CopperChestBlock.java](com/progwml6/ironchest/common/block/regular/CopperChestBlock.java) |
| `DiamondChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [DiamondChestBlock.java](com/progwml6/ironchest/common/block/regular/DiamondChestBlock.java) |
| `GoldChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [GoldChestBlock.java](com/progwml6/ironchest/common/block/regular/GoldChestBlock.java) |
| `IronChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [IronChestBlock.java](com/progwml6/ironchest/common/block/regular/IronChestBlock.java) |
| `DirtChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [DirtChestBlock.java](com/progwml6/ironchest/common/block/regular/DirtChestBlock.java) |
| `ObsidianChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [ObsidianChestBlock.java](com/progwml6/ironchest/common/block/regular/ObsidianChestBlock.java) |
| `CrystalChestBlock` | class | AbstractIronChestBlock | — | 3 | 42 | [CrystalChestBlock.java](com/progwml6/ironchest/common/block/regular/CrystalChestBlock.java) |
| `TrappedIronChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedIronChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedIronChestBlock.java) |
| `TrappedDirtChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedDirtChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedDirtChestBlock.java) |
| `TrappedGoldChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedGoldChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedGoldChestBlock.java) |
| `AbstractTrappedIronChestBlock` | class | AbstractIronChestBlock | — | 4 | 43 | [AbstractTrappedIronChestBlock.java](com/progwml6/ironchest/common/block/trapped/AbstractTrappedIronChestBlock.java) |
| `TrappedDiamondChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedDiamondChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedDiamondChestBlock.java) |
| `TrappedObsidianChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedObsidianChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedObsidianChestBlock.java) |
| `TrappedCrystalChestBlock` | class | AbstractTrappedIronChestBlock | — | 3 | 42 | [TrappedCrystalChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedCrystalChestBlock.java) |
| `TrappedCopperChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedCopperChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedCopperChestBlock.java) |
| `IronChestCatSitOnBlockGoal` | class | CatSitOnBlockGoal | — | 1 | 27 | [IronChestCatSitOnBlockGoal.java](com/progwml6/ironchest/common/ai/IronChestCatSitOnBlockGoal.java) |
| `IronChestsBlockTags` | class | BlockTagsProvider | — | 1 | 66 | [IronChestsBlockTags.java](com/progwml6/ironchest/common/data/IronChestsBlockTags.java) |
| `ChestUpgradeItem` | class | Item | — | 1 | 157 | [ChestUpgradeItem.java](com/progwml6/ironchest/common/item/ChestUpgradeItem.java) |
| `IronChestsItems` | class | — | — | 1 | 31 | [IronChestsItems.java](com/progwml6/ironchest/common/item/IronChestsItems.java) |
| `IronChestScreen` | class | AbstractContainerScreen | OnlyIn | 3 | 50 | [IronChestScreen.java](com/progwml6/ironchest/client/screen/IronChestScreen.java) |
| `ModelItem` | class | — | — | 4 | 41 | [ModelItem.java](com/progwml6/ironchest/client/model/inventory/ModelItem.java) |
| `Util` | class | — | — | 3 | 43 | [Util.java](com/progwml6/ironchest/common/Util.java) |
| `IronChestsDataComponents` | class | — | — | 0 | 15 | [IronChestsDataComponents.java](com/progwml6/ironchest/common/datacomponents/IronChestsDataComponents.java) |
| `IronChestsTypes` | enum | StringRepresentable | — | 7 | 124 | [IronChestsTypes.java](com/progwml6/ironchest/common/block/IronChestsTypes.java) |
| `DirtChestSlot` | class | Slot | — | 1 | 17 | [DirtChestSlot.java](com/progwml6/ironchest/common/inventory/DirtChestSlot.java) |
| `ICrystalChest` | interface | — | — | 0 | 139 | [ICrystalChest.java](com/progwml6/ironchest/common/block/entity/ICrystalChest.java) |
| `IronChestsCreativeTabs` | class | — | — | 0 | 32 | [IronChestsCreativeTabs.java](com/progwml6/ironchest/common/creativetabs/IronChestsCreativeTabs.java) |
| `IronChestsSpriteSourceProvider` | class | SpriteSourceProvider | — | 1 | 19 | [IronChestsSpriteSourceProvider.java](com/progwml6/ironchest/common/data/IronChestsSpriteSourceProvider.java) |
| `IronChestsLanguageProvider` | class | LanguageProvider | — | 2 | 66 | [IronChestsLanguageProvider.java](com/progwml6/ironchest/common/data/IronChestsLanguageProvider.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `IronChestsClientRegistration` | 3 | `com.progwml6.ironchest.client` | [IronChestsClientRegistration.java](com/progwml6/ironchest/client/IronChestsClientRegistration.java) |
| `CatsSitOnChestsHandler` | 1 | `com.progwml6.ironchest.common.ai` | [CatsSitOnChestsHandler.java](com/progwml6/ironchest/common/ai/CatsSitOnChestsHandler.java) |

## 全部类（62）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IronChestsClientRegistration` | class | — | EventBusSubscriber | 3 | 57 | [IronChestsClientRegistration.java](com/progwml6/ironchest/client/IronChestsClientRegistration.java) |
| `CatsSitOnChestsHandler` | class | — | EventBusSubscriber | 0 | 32 | [CatsSitOnChestsHandler.java](com/progwml6/ironchest/common/ai/CatsSitOnChestsHandler.java) |
| `IronChests` | class | — | Mod | 3 | 88 | [IronChests.java](com/progwml6/ironchest/IronChests.java) |
| `AbstractIronChestBlock` | class | BaseEntityBlock | — | 38 | 300 | [AbstractIronChestBlock.java](com/progwml6/ironchest/common/block/regular/AbstractIronChestBlock.java) |
| `AbstractIronChestBlockEntity` | class | RandomizableContainerBlockEntity | — | 21 | 184 | [AbstractIronChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/AbstractIronChestBlockEntity.java) |
| `CrystalChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 11 | 85 | [CrystalChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/CrystalChestBlockEntity.java) |
| `TrappedCrystalChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 11 | 90 | [TrappedCrystalChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedCrystalChestBlockEntity.java) |
| `IronChestsRecipeProvider` | class | RecipeProvider | — | 6 | 226 | [IronChestsRecipeProvider.java](com/progwml6/ironchest/common/data/IronChestsRecipeProvider.java) |
| `IronChestMenu` | class | AbstractContainerMenu | — | 19 | 183 | [IronChestMenu.java](com/progwml6/ironchest/common/inventory/IronChestMenu.java) |
| `DirtChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 6 | 62 | [DirtChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/DirtChestBlockEntity.java) |
| `TrappedDirtChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 6 | 68 | [TrappedDirtChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedDirtChestBlockEntity.java) |
| `IronChestsBlockLoot` | class | BlockLootSubProvider | — | 4 | 75 | [IronChestsBlockLoot.java](com/progwml6/ironchest/common/data/loot/IronChestsBlockLoot.java) |
| `IronChestsBlockEntityTypes` | class | — | — | 1 | 76 | [IronChestsBlockEntityTypes.java](com/progwml6/ironchest/common/block/entity/IronChestsBlockEntityTypes.java) |
| `DiamondChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 24 | [DiamondChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/DiamondChestBlockEntity.java) |
| `GoldChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 22 | [GoldChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/GoldChestBlockEntity.java) |
| `ObsidianChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 28 | [ObsidianChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/ObsidianChestBlockEntity.java) |
| `CopperChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 22 | [CopperChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/CopperChestBlockEntity.java) |
| `IronChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 22 | [IronChestBlockEntity.java](com/progwml6/ironchest/common/block/regular/entity/IronChestBlockEntity.java) |
| `TrappedObsidianChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedObsidianChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedObsidianChestBlockEntity.java) |
| `TrappedGoldChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedGoldChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedGoldChestBlockEntity.java) |
| `TrappedCopperChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedCopperChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedCopperChestBlockEntity.java) |
| `TrappedDiamondChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedDiamondChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedDiamondChestBlockEntity.java) |
| `AbstractTrappedIronChestBlockEntity` | class | AbstractIronChestBlockEntity | — | 1 | 29 | [AbstractTrappedIronChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/AbstractTrappedIronChestBlockEntity.java) |
| `TrappedIronChestBlockEntity` | class | AbstractTrappedIronChestBlockEntity | — | 1 | 28 | [TrappedIronChestBlockEntity.java](com/progwml6/ironchest/common/block/trapped/entity/TrappedIronChestBlockEntity.java) |
| `IronChestBlockItem` | class | BlockItem | — | 2 | 139 | [IronChestBlockItem.java](com/progwml6/ironchest/common/item/IronChestBlockItem.java) |
| `IronChestItemStackRenderer` | class | BlockEntityWithoutLevelRenderer | OnlyIn | 1 | 32 | [IronChestItemStackRenderer.java](com/progwml6/ironchest/client/model/inventory/IronChestItemStackRenderer.java) |
| `TopStacksSyncPacket` | class | CustomPacketPayload | — | 3 | 66 | [TopStacksSyncPacket.java](com/progwml6/ironchest/common/network/TopStacksSyncPacket.java) |
| `IronChestsLootTableProvider` | class | LootTableProvider | — | 0 | 17 | [IronChestsLootTableProvider.java](com/progwml6/ironchest/common/data/loot/IronChestsLootTableProvider.java) |
| `IronChestRenderer` | class | BlockEntityRenderer<T> | OnlyIn | 5 | 173 | [IronChestRenderer.java](com/progwml6/ironchest/client/render/IronChestRenderer.java) |
| `IronChestsMenuTypes` | class | — | — | 0 | 33 | [IronChestsMenuTypes.java](com/progwml6/ironchest/common/inventory/IronChestsMenuTypes.java) |
| `IronChestsBlocks` | class | — | — | 4 | 97 | [IronChestsBlocks.java](com/progwml6/ironchest/common/block/IronChestsBlocks.java) |
| `CopperChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [CopperChestBlock.java](com/progwml6/ironchest/common/block/regular/CopperChestBlock.java) |
| `DiamondChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [DiamondChestBlock.java](com/progwml6/ironchest/common/block/regular/DiamondChestBlock.java) |
| `GoldChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [GoldChestBlock.java](com/progwml6/ironchest/common/block/regular/GoldChestBlock.java) |
| `IronChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [IronChestBlock.java](com/progwml6/ironchest/common/block/regular/IronChestBlock.java) |
| `DirtChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [DirtChestBlock.java](com/progwml6/ironchest/common/block/regular/DirtChestBlock.java) |
| `ObsidianChestBlock` | class | AbstractIronChestBlock | — | 2 | 30 | [ObsidianChestBlock.java](com/progwml6/ironchest/common/block/regular/ObsidianChestBlock.java) |
| `CrystalChestBlock` | class | AbstractIronChestBlock | — | 3 | 42 | [CrystalChestBlock.java](com/progwml6/ironchest/common/block/regular/CrystalChestBlock.java) |
| `TrappedIronChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedIronChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedIronChestBlock.java) |
| `TrappedDirtChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedDirtChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedDirtChestBlock.java) |
| `TrappedGoldChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedGoldChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedGoldChestBlock.java) |
| `AbstractTrappedIronChestBlock` | class | AbstractIronChestBlock | — | 4 | 43 | [AbstractTrappedIronChestBlock.java](com/progwml6/ironchest/common/block/trapped/AbstractTrappedIronChestBlock.java) |
| `TrappedDiamondChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedDiamondChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedDiamondChestBlock.java) |
| `TrappedObsidianChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedObsidianChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedObsidianChestBlock.java) |
| `TrappedCrystalChestBlock` | class | AbstractTrappedIronChestBlock | — | 3 | 42 | [TrappedCrystalChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedCrystalChestBlock.java) |
| `TrappedCopperChestBlock` | class | AbstractTrappedIronChestBlock | — | 2 | 30 | [TrappedCopperChestBlock.java](com/progwml6/ironchest/common/block/trapped/TrappedCopperChestBlock.java) |
| `IronChestCatSitOnBlockGoal` | class | CatSitOnBlockGoal | — | 1 | 27 | [IronChestCatSitOnBlockGoal.java](com/progwml6/ironchest/common/ai/IronChestCatSitOnBlockGoal.java) |
| `IronChestsBlockTags` | class | BlockTagsProvider | — | 1 | 66 | [IronChestsBlockTags.java](com/progwml6/ironchest/common/data/IronChestsBlockTags.java) |
| `ChestUpgradeItem` | class | Item | — | 1 | 157 | [ChestUpgradeItem.java](com/progwml6/ironchest/common/item/ChestUpgradeItem.java) |
| `IronChestsItems` | class | — | — | 1 | 31 | [IronChestsItems.java](com/progwml6/ironchest/common/item/IronChestsItems.java) |
| `IronChestScreen` | class | AbstractContainerScreen | OnlyIn | 3 | 50 | [IronChestScreen.java](com/progwml6/ironchest/client/screen/IronChestScreen.java) |
| `ModelItem` | class | — | — | 4 | 41 | [ModelItem.java](com/progwml6/ironchest/client/model/inventory/ModelItem.java) |
| `Util` | class | — | — | 3 | 43 | [Util.java](com/progwml6/ironchest/common/Util.java) |
| `IronChestsDataComponents` | class | — | — | 0 | 15 | [IronChestsDataComponents.java](com/progwml6/ironchest/common/datacomponents/IronChestsDataComponents.java) |
| `IronChestsTypes` | enum | StringRepresentable | — | 7 | 124 | [IronChestsTypes.java](com/progwml6/ironchest/common/block/IronChestsTypes.java) |
| `DirtChestSlot` | class | Slot | — | 1 | 17 | [DirtChestSlot.java](com/progwml6/ironchest/common/inventory/DirtChestSlot.java) |
| `ICrystalChest` | interface | — | — | 0 | 139 | [ICrystalChest.java](com/progwml6/ironchest/common/block/entity/ICrystalChest.java) |
| `IronChestsCreativeTabs` | class | — | — | 0 | 32 | [IronChestsCreativeTabs.java](com/progwml6/ironchest/common/creativetabs/IronChestsCreativeTabs.java) |
| `IronChestsSpriteSourceProvider` | class | SpriteSourceProvider | — | 1 | 19 | [IronChestsSpriteSourceProvider.java](com/progwml6/ironchest/common/data/IronChestsSpriteSourceProvider.java) |
| `IronChestsLanguageProvider` | class | LanguageProvider | — | 2 | 66 | [IronChestsLanguageProvider.java](com/progwml6/ironchest/common/data/IronChestsLanguageProvider.java) |
| `IronChestsUpgradeType` | enum | — | — | 1 | 28 | [IronChestsUpgradeType.java](com/progwml6/ironchest/common/item/IronChestsUpgradeType.java) |
| `IronChestsModels` | class | — | — | 2 | 75 | [IronChestsModels.java](com/progwml6/ironchest/client/model/IronChestsModels.java) |
