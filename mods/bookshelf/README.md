# Bookshelf（`bookshelf`）

> jar：`[前置]bookshelf-neoforge-1.21.1-21.1.68.jar` ｜ 版本：`21.1.67` ｜ 作者：Darkhax
> 规模：**169 个类 / 69 个包 / 7396 行** ｜ 事件处理类 0 ｜ Mixin 53 ｜ 注册/管理类 9

**官方描述**：Bookshelf is a library mod that provides code, frameworks, and utilities for other mods. Many mods make use of Bookshelf and are powered by its code.

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.darkhax.bookshelf.common.api.registry.register` | 18 | `RegisterBlockEntityRenderer`、`RegisterLootPoolAdditions`、`RegisterMenuScreen` |
| `net.darkhax.bookshelf.common.api.util` | 9 | `TextHelper`、`CommandHelper`、`MathsHelper` |
| `net.darkhax.bookshelf.common.mixin.access.loot` | 8 | `AccessorLootItem`、`AccessorLootTable`、`AccessorNestedLootTable` |
| `net.darkhax.bookshelf.common.impl.command` | 8 | `BlockTagToItemTagCommand`、`FontCommand`、`DebugCommands` |
| `net.darkhax.bookshelf.common.impl.data.conditions` | 6 | `RegistryContains`、`OnPlatform`、`Not` |
| `net.darkhax.bookshelf.common.api.function` | 6 | `ReloadableCache`、`CachedSupplier`、`SidedReloadableCache` |
| `net.darkhax.bookshelf.common.mixin.patch.loot` | 5 | `MixinLootItemConditions`、`MixinLootItemFunctions`、`MixinLootDataType` |
| `net.darkhax.bookshelf.common.impl.data.ingredient` | 5 | `BlockTagIngredient`、`FalseIngredient`、`ModIdIngredient` |
| `net.darkhax.bookshelf.common.mixin.access.block` | 4 | `AccessorBlockEntityRenderers`、`AccessorBaseContainerBlockEntity`、`AccessorBannerBlockEntity` |
| `net.darkhax.bookshelf.common.mixin.patch.block` | 4 | `MixinBlockEntityType`、`MixinItemBlockRenderTypes`、`MixinBlock` |
| `net.darkhax.bookshelf.common.mixin.patch.item` | 4 | `MixinRecipeSerializer`、`MixinRecipeType`、`MixinItems` |
| `net.darkhax.bookshelf.common.api.network` | 4 | `INetworkHandler`、`IPacket`、`AbstractPacket` |
| `net.darkhax.bookshelf.common.api.commands.args` | 4 | `FontArgument`、`ArgumentSerializer`、`TagArgument` |
| `net.darkhax.bookshelf.common.impl.data.loot.modifiers` | 3 | `LootModificationHandler`、`ILootPoolHooks`、`FingerprintCodec` |
| `net.darkhax.bookshelf.common.mixin.patch.advancement` | 3 | `MixinItemSubPredicates`、`MixinPlayerAdvancements`、`MixinCriteriaTriggers` |
| `net.darkhax.bookshelf.common.api.data.codecs.map` | 3 | `RegistryMapCodecHelper`、`MapCodecHelper`、`MapCodecs` |
| `net.darkhax.bookshelf.neoforge.impl.util` | 3 | `NeoForgePlatformHelper`、`NeoForgeGameplayHelper`、`NeoForgeRenderHelper` |
| `net.darkhax.bookshelf.common.impl` | 3 | `BookshelfContent`、`Constants`、`BookshelfMod` |
| `net.darkhax.bookshelf.common.api.entity.villager.trades` | 3 | `VillagerSells`、`VillagerBuys`、`VillagerOffers` |
| `net.darkhax.bookshelf.common.api.data.conditions` | 3 | `ConditionType`、`LoadConditions`、`ILoadCondition` |
| `net.darkhax.bookshelf.common.mixin.patch.level` | 2 | `MixinRecipeManager`、`MixinWalkNodeEvaluator` |
| `net.darkhax.bookshelf.common.mixin.access.client` | 2 | `AccessorFontManager`、`AccessorMinecraft` |
| `net.darkhax.bookshelf.neoforge.impl` | 2 | `NeoForgeMod`、`NeoForgeModClient` |
| `net.darkhax.bookshelf.common.mixin.patch.commands` | 2 | `MixinCommands`、`MixinArgumentTypeInfos` |
| `net.darkhax.bookshelf.common.mixin.patch.entity` | 2 | `MixinEntityType`、`MixinAttributes` |
| `net.darkhax.bookshelf.common.mixin.patch.potions` | 2 | `MixinPotions`、`MixinPotionBrewing` |
| `net.darkhax.bookshelf.common.api.loot` | 2 | `LootPoolEntryDescriptions`、`LootPoolEntryDescriber` |
| `net.darkhax.bookshelf.common.api.commands` | 2 | `IEnumCommand`、`PermissionLevel` |
| `net.darkhax.bookshelf.common.api` | 2 | `PhysicalSide`、`ModEntry` |
| `net.darkhax.bookshelf.common.api.text.unit` | 2 | `IUnit`、`Units` |
| `net.darkhax.bookshelf.common.api.text.font` | 2 | `BuiltinFonts`、`IFontEntry` |
| `net.darkhax.bookshelf.common.api.text.format` | 2 | `IPropertyFormat`、`PropertyFormat` |
| `net.darkhax.bookshelf.common.api.menu.slot` | 2 | `OutputSlot`、`InputSlot` |
| `net.darkhax.bookshelf.common.api.annotation` | 2 | `OnlyFor`、`InternalUse` |
| `net.darkhax.bookshelf.common.mixin.access.level` | 1 | `AccessorRecipeManager` |
| `net.darkhax.bookshelf.common.mixin.patch.client` | 1 | `MixinClientPacketListener` |
| `net.darkhax.bookshelf.neoforge.mixin.access.gui.screen` | 1 | `AccessorMenuScreens` |
| `net.darkhax.bookshelf.common.mixin.patch.gui.screens` | 1 | `MixinMenuScreens` |
| `net.darkhax.bookshelf.neoforge.impl.network` | 1 | `NeoForgeNetworkHandler` |
| `net.darkhax.bookshelf.common.api.data` | 1 | `ISidedRecipeManager` |
| … | | 其余 29 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinRecipeManager` | class | ISidedRecipeManager | Mixin | 2 | 54 | [MixinRecipeManager.java](net/darkhax/bookshelf/common/mixin/patch/level/MixinRecipeManager.java) |
| `AccessorRecipeManager` | interface | — | Mixin | 0 | 15 | [AccessorRecipeManager.java](net/darkhax/bookshelf/common/mixin/access/level/AccessorRecipeManager.java) |
| `AccessorBlockEntityRenderers` | interface | — | Mixin | 0 | 15 | [AccessorBlockEntityRenderers.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorBlockEntityRenderers.java) |
| `MixinLootItemConditions` | class | — | Mixin | 1 | 29 | [MixinLootItemConditions.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootItemConditions.java) |
| `MixinLootItemFunctions` | class | — | Mixin | 1 | 29 | [MixinLootItemFunctions.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootItemFunctions.java) |
| `MixinClientPacketListener` | class | — | Mixin | 1 | 29 | [MixinClientPacketListener.java](net/darkhax/bookshelf/common/mixin/patch/client/MixinClientPacketListener.java) |
| `AccessorLootItem` | interface | — | Mixin | 0 | 23 | [AccessorLootItem.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootItem.java) |
| `AccessorFontManager` | interface | — | Mixin | 0 | 15 | [AccessorFontManager.java](net/darkhax/bookshelf/common/mixin/access/client/AccessorFontManager.java) |
| `NeoForgeMod` | class | — | Mod | 3 | 90 | [NeoForgeMod.java](net/darkhax/bookshelf/neoforge/impl/NeoForgeMod.java) |
| `AccessorMenuScreens` | interface | — | Mixin | 0 | 19 | [AccessorMenuScreens.java](net/darkhax/bookshelf/neoforge/mixin/access/gui/screen/AccessorMenuScreens.java) |
| `MixinMenuScreens` | class | — | Mixin | 1 | 19 | [MixinMenuScreens.java](net/darkhax/bookshelf/common/mixin/patch/gui/screens/MixinMenuScreens.java) |
| `MixinBlockEntityType` | class | — | Mixin | 1 | 26 | [MixinBlockEntityType.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinBlockEntityType.java) |
| `MixinItemBlockRenderTypes` | class | — | Mixin | 0 | 24 | [MixinItemBlockRenderTypes.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinItemBlockRenderTypes.java) |
| `MixinLootDataType` | class | — | Mixin | 1 | 55 | [MixinLootDataType.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootDataType.java) |
| `MixinRecipeSerializer` | interface | — | Mixin | 1 | 26 | [MixinRecipeSerializer.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinRecipeSerializer.java) |
| `MixinRecipeType` | interface | — | Mixin | 1 | 27 | [MixinRecipeType.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinRecipeType.java) |
| `AccessorBaseContainerBlockEntity` | interface | — | Mixin | 0 | 13 | [AccessorBaseContainerBlockEntity.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorBaseContainerBlockEntity.java) |
| `AccessorBannerBlockEntity` | interface | — | Mixin | 0 | 13 | [AccessorBannerBlockEntity.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorBannerBlockEntity.java) |
| `NeoForgeNetworkHandler` | class | INetworkHandler | — | 5 | 85 | [NeoForgeNetworkHandler.java](net/darkhax/bookshelf/neoforge/impl/network/NeoForgeNetworkHandler.java) |
| `MixinLootPool` | class | ILootPoolHooks | Mixin | 1 | 40 | [MixinLootPool.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootPool.java) |
| `MixinLootPoolEntries` | class | — | Mixin | 1 | 30 | [MixinLootPoolEntries.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootPoolEntries.java) |
| `MixinCommands` | class | — | Mixin | 1 | 27 | [MixinCommands.java](net/darkhax/bookshelf/common/mixin/patch/commands/MixinCommands.java) |
| `AccessorLootTable` | interface | — | Mixin | 0 | 23 | [AccessorLootTable.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootTable.java) |
| `AccessorNestedLootTable` | interface | — | Mixin | 0 | 15 | [AccessorNestedLootTable.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorNestedLootTable.java) |
| `AccessorLootPoolSingletonContainer` | interface | — | Mixin | 0 | 15 | [AccessorLootPoolSingletonContainer.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootPoolSingletonContainer.java) |
| `AccessorLootPool` | interface | — | Mixin | 0 | 34 | [AccessorLootPool.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootPool.java) |
| `AccessorDynamicLoot` | interface | — | Mixin | 0 | 13 | [AccessorDynamicLoot.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorDynamicLoot.java) |
| `ISidedRecipeManager` | interface | — | — | 0 | 8 | [ISidedRecipeManager.java](net/darkhax/bookshelf/common/api/data/ISidedRecipeManager.java) |
| `LootModificationHandler` | class | — | — | 3 | 92 | [LootModificationHandler.java](net/darkhax/bookshelf/common/impl/data/loot/modifiers/LootModificationHandler.java) |
| `MixinEntityType` | class | — | Mixin | 1 | 26 | [MixinEntityType.java](net/darkhax/bookshelf/common/mixin/patch/entity/MixinEntityType.java) |
| `MixinMenuType` | class | — | Mixin | 1 | 18 | [MixinMenuType.java](net/darkhax/bookshelf/common/mixin/patch/gui/MixinMenuType.java) |
| `MixinClientLanguage` | class | — | Mixin | 2 | 26 | [MixinClientLanguage.java](net/darkhax/bookshelf/common/mixin/patch/locale/MixinClientLanguage.java) |
| `MixinBlock` | class | — | Mixin | 1 | 26 | [MixinBlock.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinBlock.java) |
| `MixinItemSubPredicates` | class | — | Mixin | 1 | 30 | [MixinItemSubPredicates.java](net/darkhax/bookshelf/common/mixin/patch/advancement/MixinItemSubPredicates.java) |
| `MixinItems` | class | — | Mixin | 1 | 22 | [MixinItems.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinItems.java) |
| `AccessorEntity` | interface | — | Mixin | 0 | 13 | [AccessorEntity.java](net/darkhax/bookshelf/common/mixin/access/entity/AccessorEntity.java) |
| `AccessorCropBlock` | interface | — | Mixin | 0 | 13 | [AccessorCropBlock.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorCropBlock.java) |
| `INetworkHandler` | interface | — | — | 0 | 24 | [INetworkHandler.java](net/darkhax/bookshelf/common/api/network/INetworkHandler.java) |
| `TextHelper` | class | — | — | 30 | 266 | [TextHelper.java](net/darkhax/bookshelf/common/api/util/TextHelper.java) |
| `BlockTagToItemTagCommand` | class | — | — | 2 | 61 | [BlockTagToItemTagCommand.java](net/darkhax/bookshelf/common/impl/command/BlockTagToItemTagCommand.java) |
| `MixinDataComponents` | class | — | Mixin | 4 | 42 | [MixinDataComponents.java](net/darkhax/bookshelf/common/mixin/patch/component/MixinDataComponents.java) |
| `RegistryMapCodecHelper` | class | MapCodecHelper | — | 3 | 30 | [RegistryMapCodecHelper.java](net/darkhax/bookshelf/common/api/data/codecs/map/RegistryMapCodecHelper.java) |
| `LootItemStack` | class | LootPoolSingletonContainer | — | 5 | 55 | [LootItemStack.java](net/darkhax/bookshelf/common/impl/data/loot/entries/LootItemStack.java) |
| `RegistryContains` | class | ILoadCondition | — | 4 | 57 | [RegistryContains.java](net/darkhax/bookshelf/common/impl/data/conditions/RegistryContains.java) |
| `MixinCatVariant` | class | — | Mixin | 1 | 22 | [MixinCatVariant.java](net/darkhax/bookshelf/common/mixin/patch/MixinCatVariant.java) |
| `MixinAttributes` | class | — | Mixin | 1 | 26 | [MixinAttributes.java](net/darkhax/bookshelf/common/mixin/patch/entity/MixinAttributes.java) |
| `MixinDecoratedPotPatterns` | class | — | Mixin | 2 | 43 | [MixinDecoratedPotPatterns.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinDecoratedPotPatterns.java) |
| `MixinPlayerAdvancements` | class | — | Mixin | 2 | 29 | [MixinPlayerAdvancements.java](net/darkhax/bookshelf/common/mixin/patch/advancement/MixinPlayerAdvancements.java) |
| `MixinCriteriaTriggers` | class | — | Mixin | 1 | 23 | [MixinCriteriaTriggers.java](net/darkhax/bookshelf/common/mixin/patch/advancement/MixinCriteriaTriggers.java) |
| `MixinReloadableServerResources` | class | — | Mixin | 1 | 29 | [MixinReloadableServerResources.java](net/darkhax/bookshelf/common/mixin/patch/server/MixinReloadableServerResources.java) |
| `MixinWalkNodeEvaluator` | class | — | Mixin | 1 | 32 | [MixinWalkNodeEvaluator.java](net/darkhax/bookshelf/common/mixin/patch/level/MixinWalkNodeEvaluator.java) |
| `MixinCreativeModeTab` | class | — | Mixin | 1 | 66 | [MixinCreativeModeTab.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinCreativeModeTab.java) |
| `MixinBuiltInRegistries` | class | — | Mixin | 1 | 32 | [MixinBuiltInRegistries.java](net/darkhax/bookshelf/common/mixin/patch/registries/MixinBuiltInRegistries.java) |
| `MixinSimpleJsonResourceReloadListener` | class | — | Mixin | 1 | 26 | [MixinSimpleJsonResourceReloadListener.java](net/darkhax/bookshelf/common/mixin/patch/packs/MixinSimpleJsonResourceReloadListener.java) |
| `MixinPotions` | class | — | Mixin | 1 | 24 | [MixinPotions.java](net/darkhax/bookshelf/common/mixin/patch/potions/MixinPotions.java) |
| `MixinPotionBrewing` | class | — | Mixin | 1 | 18 | [MixinPotionBrewing.java](net/darkhax/bookshelf/common/mixin/patch/potions/MixinPotionBrewing.java) |
| `MixinArgumentTypeInfos` | class | — | Mixin | 3 | 38 | [MixinArgumentTypeInfos.java](net/darkhax/bookshelf/common/mixin/patch/commands/MixinArgumentTypeInfos.java) |
| `MixinEffects` | class | — | Mixin | 1 | 26 | [MixinEffects.java](net/darkhax/bookshelf/common/mixin/patch/effect/MixinEffects.java) |
| `AccessorCompositeEntryBase` | interface | — | Mixin | 0 | 14 | [AccessorCompositeEntryBase.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorCompositeEntryBase.java) |
| `AccessorTagEntry` | interface | — | Mixin | 0 | 17 | [AccessorTagEntry.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorTagEntry.java) |

## Mixin（53 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinRecipeManager` | `RecipeManager` | [MixinRecipeManager.java](net/darkhax/bookshelf/common/mixin/patch/level/MixinRecipeManager.java) |
| `AccessorRecipeManager` | `AccessorRecipeManager` | [AccessorRecipeManager.java](net/darkhax/bookshelf/common/mixin/access/level/AccessorRecipeManager.java) |
| `AccessorBlockEntityRenderers` | `AccessorBlockEntityRenderers` | [AccessorBlockEntityRenderers.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorBlockEntityRenderers.java) |
| `MixinLootItemConditions` | `LootItemConditions` | [MixinLootItemConditions.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootItemConditions.java) |
| `MixinLootItemFunctions` | `LootItemFunctions` | [MixinLootItemFunctions.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootItemFunctions.java) |
| `MixinClientPacketListener` | `ClientPacketListener` | [MixinClientPacketListener.java](net/darkhax/bookshelf/common/mixin/patch/client/MixinClientPacketListener.java) |
| `AccessorLootItem` | `AccessorLootItem` | [AccessorLootItem.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootItem.java) |
| `AccessorFontManager` | `AccessorFontManager` | [AccessorFontManager.java](net/darkhax/bookshelf/common/mixin/access/client/AccessorFontManager.java) |
| `AccessorMenuScreens` | `AccessorMenuScreens` | [AccessorMenuScreens.java](net/darkhax/bookshelf/neoforge/mixin/access/gui/screen/AccessorMenuScreens.java) |
| `MixinMenuScreens` | `MenuScreens` | [MixinMenuScreens.java](net/darkhax/bookshelf/common/mixin/patch/gui/screens/MixinMenuScreens.java) |
| `MixinBlockEntityType` | `BlockEntityType` | [MixinBlockEntityType.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinBlockEntityType.java) |
| `MixinItemBlockRenderTypes` | `ItemBlockRenderTypes` | [MixinItemBlockRenderTypes.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinItemBlockRenderTypes.java) |
| `MixinLootDataType` | `LootDataType` | [MixinLootDataType.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootDataType.java) |
| `MixinRecipeSerializer` | `RecipeSerializer` | [MixinRecipeSerializer.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinRecipeSerializer.java) |
| `MixinRecipeType` | `RecipeType` | [MixinRecipeType.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinRecipeType.java) |
| `AccessorBaseContainerBlockEntity` | `AccessorBaseContainerBlockEntity` | [AccessorBaseContainerBlockEntity.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorBaseContainerBlockEntity.java) |
| `AccessorBannerBlockEntity` | `AccessorBannerBlockEntity` | [AccessorBannerBlockEntity.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorBannerBlockEntity.java) |
| `MixinLootPool` | `LootPool` | [MixinLootPool.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootPool.java) |
| `MixinLootPoolEntries` | `LootPoolEntries` | [MixinLootPoolEntries.java](net/darkhax/bookshelf/common/mixin/patch/loot/MixinLootPoolEntries.java) |
| `MixinCommands` | `Commands` | [MixinCommands.java](net/darkhax/bookshelf/common/mixin/patch/commands/MixinCommands.java) |
| `AccessorLootTable` | `AccessorLootTable` | [AccessorLootTable.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootTable.java) |
| `AccessorNestedLootTable` | `AccessorNestedLootTable` | [AccessorNestedLootTable.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorNestedLootTable.java) |
| `AccessorLootPoolSingletonContainer` | `AccessorLootPoolSingletonContainer` | [AccessorLootPoolSingletonContainer.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootPoolSingletonContainer.java) |
| `AccessorLootPool` | `AccessorLootPool` | [AccessorLootPool.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorLootPool.java) |
| `AccessorDynamicLoot` | `AccessorDynamicLoot` | [AccessorDynamicLoot.java](net/darkhax/bookshelf/common/mixin/access/loot/AccessorDynamicLoot.java) |
| `MixinEntityType` | `EntityType` | [MixinEntityType.java](net/darkhax/bookshelf/common/mixin/patch/entity/MixinEntityType.java) |
| `MixinMenuType` | `MenuType` | [MixinMenuType.java](net/darkhax/bookshelf/common/mixin/patch/gui/MixinMenuType.java) |
| `MixinClientLanguage` | `ClientLanguage` | [MixinClientLanguage.java](net/darkhax/bookshelf/common/mixin/patch/locale/MixinClientLanguage.java) |
| `MixinBlock` | `Block` | [MixinBlock.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinBlock.java) |
| `MixinItemSubPredicates` | `ItemSubPredicates` | [MixinItemSubPredicates.java](net/darkhax/bookshelf/common/mixin/patch/advancement/MixinItemSubPredicates.java) |
| `MixinItems` | `Items` | [MixinItems.java](net/darkhax/bookshelf/common/mixin/patch/item/MixinItems.java) |
| `AccessorEntity` | `AccessorEntity` | [AccessorEntity.java](net/darkhax/bookshelf/common/mixin/access/entity/AccessorEntity.java) |
| `AccessorCropBlock` | `AccessorCropBlock` | [AccessorCropBlock.java](net/darkhax/bookshelf/common/mixin/access/block/AccessorCropBlock.java) |
| `MixinDataComponents` | `DataComponents` | [MixinDataComponents.java](net/darkhax/bookshelf/common/mixin/patch/component/MixinDataComponents.java) |
| `MixinCatVariant` | `CatVariant` | [MixinCatVariant.java](net/darkhax/bookshelf/common/mixin/patch/MixinCatVariant.java) |
| `MixinAttributes` | `Attributes` | [MixinAttributes.java](net/darkhax/bookshelf/common/mixin/patch/entity/MixinAttributes.java) |
| `MixinDecoratedPotPatterns` | `DecoratedPotPatterns` | [MixinDecoratedPotPatterns.java](net/darkhax/bookshelf/common/mixin/patch/block/MixinDecoratedPotPatterns.java) |
| `MixinPlayerAdvancements` | `PlayerAdvancements` | [MixinPlayerAdvancements.java](net/darkhax/bookshelf/common/mixin/patch/advancement/MixinPlayerAdvancements.java) |
| `MixinCriteriaTriggers` | `CriteriaTriggers` | [MixinCriteriaTriggers.java](net/darkhax/bookshelf/common/mixin/patch/advancement/MixinCriteriaTriggers.java) |
| `MixinReloadableServerResources` | `ReloadableServerResources` | [MixinReloadableServerResources.java](net/darkhax/bookshelf/common/mixin/patch/server/MixinReloadableServerResources.java) |

## 按包浏览

- `net.darkhax.bookshelf.common.api`（2 类）
- `net.darkhax.bookshelf.common.api.annotation`（2 类）
- `net.darkhax.bookshelf.common.api.block`（1 类）
- `net.darkhax.bookshelf.common.api.commands`（2 类）
- `net.darkhax.bookshelf.common.api.commands.args`（4 类）
- `net.darkhax.bookshelf.common.api.data`（1 类）
- `net.darkhax.bookshelf.common.api.data.codecs`（1 类）
- `net.darkhax.bookshelf.common.api.data.codecs.map`（3 类）
- `net.darkhax.bookshelf.common.api.data.codecs.stream`（1 类）
- `net.darkhax.bookshelf.common.api.data.conditions`（3 类）
- `net.darkhax.bookshelf.common.api.data.enchantment`（1 类）
- `net.darkhax.bookshelf.common.api.data.ingredient`（1 类）
- `net.darkhax.bookshelf.common.api.data.loot`（1 类）
- `net.darkhax.bookshelf.common.api.data.loot.modifiers`（1 类）
- `net.darkhax.bookshelf.common.api.entity.villager`（1 类）
- `net.darkhax.bookshelf.common.api.entity.villager.trades`（3 类）
- `net.darkhax.bookshelf.common.api.function`（6 类）
- `net.darkhax.bookshelf.common.api.item`（1 类）
- `net.darkhax.bookshelf.common.api.loot`（2 类）
- `net.darkhax.bookshelf.common.api.menu.data`（1 类）
- `net.darkhax.bookshelf.common.api.menu.slot`（2 类）
- `net.darkhax.bookshelf.common.api.network`（4 类）
- `net.darkhax.bookshelf.common.api.registry`（1 类）
- `net.darkhax.bookshelf.common.api.registry.register`（18 类）
- `net.darkhax.bookshelf.common.api.service`（1 类）
- `net.darkhax.bookshelf.common.api.text.font`（2 类）
- `net.darkhax.bookshelf.common.api.text.format`（2 类）
- `net.darkhax.bookshelf.common.api.text.unit`（2 类）
- `net.darkhax.bookshelf.common.api.util`（9 类）
- `net.darkhax.bookshelf.common.impl`（3 类）
- `net.darkhax.bookshelf.common.impl.command`（8 类）
- `net.darkhax.bookshelf.common.impl.data.conditions`（6 类）
- `net.darkhax.bookshelf.common.impl.data.criterion.item`（1 类）
- `net.darkhax.bookshelf.common.impl.data.criterion.trigger`（1 类）
- `net.darkhax.bookshelf.common.impl.data.ingredient`（5 类）
- `net.darkhax.bookshelf.common.impl.data.loot.entries`（1 类）
- `net.darkhax.bookshelf.common.impl.data.loot.modifiers`（3 类）
- `net.darkhax.bookshelf.common.impl.recipe`（1 类）
- `net.darkhax.bookshelf.common.impl.resources`（1 类）
- `net.darkhax.bookshelf.common.mixin.access.block`（4 类）
- `net.darkhax.bookshelf.common.mixin.access.client`（2 类）
- `net.darkhax.bookshelf.common.mixin.access.client.gui`（1 类）
- `net.darkhax.bookshelf.common.mixin.access.entity`（1 类）
- `net.darkhax.bookshelf.common.mixin.access.level`（1 类）
- `net.darkhax.bookshelf.common.mixin.access.loot`（8 类）
- `net.darkhax.bookshelf.common.mixin.access.particles`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.advancement`（3 类）
- `net.darkhax.bookshelf.common.mixin.patch.block`（4 类）
- `net.darkhax.bookshelf.common.mixin.patch.client`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.commands`（2 类）
- `net.darkhax.bookshelf.common.mixin.patch.component`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.effect`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.entity`（2 类）
- `net.darkhax.bookshelf.common.mixin.patch.gui`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.gui.screens`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.item`（4 类）
- `net.darkhax.bookshelf.common.mixin.patch.level`（2 类）
- `net.darkhax.bookshelf.common.mixin.patch.locale`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.loot`（5 类）
- `net.darkhax.bookshelf.common.mixin.patch.packs`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.potions`（2 类）
- `net.darkhax.bookshelf.common.mixin.patch.registries`（1 类）
- `net.darkhax.bookshelf.common.mixin.patch.server`（1 类）
- `net.darkhax.bookshelf.neoforge.impl`（2 类）
- `net.darkhax.bookshelf.neoforge.impl.data`（1 类）
- `net.darkhax.bookshelf.neoforge.impl.network`（1 类）
- `net.darkhax.bookshelf.neoforge.impl.util`（3 类）
- `net.darkhax.bookshelf.neoforge.mixin.access.gui.screen`（1 类）
