# Polymorph（`polymorph`）

> jar：`[多态合成]polymorph.jar` ｜ 版本：`1.1.0+1.21.1` ｜ 作者：Illusive Soulworks
> 规模：**90 个类 / 28 个包 / 4029 行** ｜ 事件处理类 2 ｜ Mixin 21 ｜ 注册/管理类 10

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.illusivesoulworks.polymorph.mixin.core` | 16 | `MixinRecipeManager`、`AccessorAbstractFurnaceBlockEntity`、`AccessorSmithingTransformRecipe` |
| `com.illusivesoulworks.polymorph.common.capability` | 7 | `AbstractBlockEntityRecipeData`、`AbstractRecipeData`、`PlayerRecipeData` |
| `com.illusivesoulworks.polymorph.common.network.server` | 6 | `ClientPacketHandler`、`SPacketHighlightRecipe`、`SPacketPlayerRecipeSync` |
| `com.illusivesoulworks.polymorph.common` | 5 | `CommonEventsListener`、`PolymorphRecipeManager`、`PolymorphCommonEvents` |
| `com.illusivesoulworks.polymorph.client` | 4 | `ClientEventsListener`、`PolymorphClientEvents`、`RecipesWidget` |
| `com.illusivesoulworks.polymorph.api.common.base` | 4 | `IPolymorphRecipeManager`、`IRecipeContext`、`IRecipePair` |
| `com.illusivesoulworks.polymorph.api.client.base` | 4 | `AbstractRecipesWidget`、`PersistentRecipesWidget`、`ITickingRecipesWidget` |
| `com.illusivesoulworks.polymorph.server.wrapper` | 4 | `CraftingRecipeWrapper`、`RecipeWrapper`、`SmithingRecipeWrapper` |
| `com.illusivesoulworks.polymorph.platform` | 4 | `NeoForgeClientPlatform`、`NeoForgeIntegrationPlatform`、`NeoForgePlatform` |
| `com.illusivesoulworks.polymorph` | 3 | `PolymorphNeoForgeMod`、`PolymorphCommonMod`、`PolymorphConstants` |
| `com.illusivesoulworks.polymorph.api.common.capability` | 3 | `IBlockEntityRecipeData`、`IRecipeData`、`IPlayerRecipeData` |
| `com.illusivesoulworks.polymorph.common.network` | 3 | `ClientPayloadHandler`、`ServerPayloadHandler`、`PolymorphNeoForgePacketDistributor` |
| `com.illusivesoulworks.polymorph.common.network.client` | 3 | `CPacketPlayerRecipeSelection`、`CPacketPersistentRecipeSelection`、`CPacketBlockEntityListener` |
| `com.illusivesoulworks.polymorph.api.client.widgets` | 3 | `CrafterRecipesWidget`、`FurnaceRecipesWidget`、`PlayerRecipesWidget` |
| `com.illusivesoulworks.polymorph.platform.services` | 3 | `IIntegrationPlatform`、`IClientPlatform`、`IPlatform` |
| `com.illusivesoulworks.polymorph.api.client.widgets.children` | 3 | `SelectionWidget`、`OutputWidget`、`OpenSelectionButton` |
| `com.illusivesoulworks.polymorph.mixin.integration.jei` | 2 | `MixinPacketRecipeTransfer`、`MixinRecipeTransferUtil` |
| `com.illusivesoulworks.polymorph.common.integration` | 2 | `PolymorphIntegrations`、`AbstractCompatibilityModule` |
| `com.illusivesoulworks.polymorph.common.util` | 2 | `BlockEntityTicker`、`RecipePair` |
| `com.illusivesoulworks.polymorph.mixin.integration.emi` | 1 | `MixinEmiRecipeFiller` |
| `com.illusivesoulworks.polymorph.mixin.integration.fastbench` | 1 | `MixinFastBenchUtil` |
| `com.illusivesoulworks.polymorph.mixin.integration.roughlyenoughitems` | 1 | `MixinInternalWidgets` |
| `com.illusivesoulworks.polymorph.common.integration.util` | 1 | `RecipeTransfer` |
| `com.illusivesoulworks.polymorph.server` | 1 | `PolymorphCommands` |
| `com.illusivesoulworks.polymorph.api` | 1 | `PolymorphApi` |
| `com.illusivesoulworks.polymorph.mixin` | 1 | `IntegratedMixinPlugin` |
| `com.illusivesoulworks.polymorph.api.client` | 1 | `PolymorphWidgets` |
| `com.illusivesoulworks.polymorph.common.integration.fastbench` | 1 | `FastBenchModule` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinRecipeManager` | class | IRecipeContext | Mixin | 0 | 56 | [MixinRecipeManager.java](com/illusivesoulworks/polymorph/mixin/core/MixinRecipeManager.java) |
| `ClientEventsListener` | class | — | — | 4 | 30 | [ClientEventsListener.java](com/illusivesoulworks/polymorph/client/ClientEventsListener.java) |
| `MixinPacketRecipeTransfer` | class | — | Mixin | 0 | 18 | [MixinPacketRecipeTransfer.java](com/illusivesoulworks/polymorph/mixin/integration/jei/MixinPacketRecipeTransfer.java) |
| `CommonEventsListener` | class | — | — | 4 | 34 | [CommonEventsListener.java](com/illusivesoulworks/polymorph/common/CommonEventsListener.java) |
| `MixinRecipeTransferUtil` | class | — | Mixin | 0 | 34 | [MixinRecipeTransferUtil.java](com/illusivesoulworks/polymorph/mixin/integration/jei/MixinRecipeTransferUtil.java) |
| `ClientPacketHandler` | class | — | — | 6 | 73 | [ClientPacketHandler.java](com/illusivesoulworks/polymorph/common/network/server/ClientPacketHandler.java) |
| `PolymorphNeoForgeMod` | class | — | Mod | 3 | 58 | [PolymorphNeoForgeMod.java](com/illusivesoulworks/polymorph/PolymorphNeoForgeMod.java) |
| `AccessorAbstractFurnaceBlockEntity` | interface | — | Mixin | 0 | 14 | [AccessorAbstractFurnaceBlockEntity.java](com/illusivesoulworks/polymorph/mixin/core/AccessorAbstractFurnaceBlockEntity.java) |
| `AccessorSmithingTransformRecipe` | interface | — | Mixin | 0 | 19 | [AccessorSmithingTransformRecipe.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingTransformRecipe.java) |
| `MixinRecipeCache` | class | — | Mixin | 1 | 34 | [MixinRecipeCache.java](com/illusivesoulworks/polymorph/mixin/core/MixinRecipeCache.java) |
| `AccessorSmithingTrimRecipe` | interface | — | Mixin | 0 | 19 | [AccessorSmithingTrimRecipe.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingTrimRecipe.java) |
| `MixinEmiRecipeFiller` | class | — | Mixin | 0 | 28 | [MixinEmiRecipeFiller.java](com/illusivesoulworks/polymorph/mixin/integration/emi/MixinEmiRecipeFiller.java) |
| `PolymorphClientEvents` | class | — | — | 4 | 39 | [PolymorphClientEvents.java](com/illusivesoulworks/polymorph/client/PolymorphClientEvents.java) |
| `PolymorphRecipeManager` | class | IPolymorphRecipeManager | — | 3 | 47 | [PolymorphRecipeManager.java](com/illusivesoulworks/polymorph/common/PolymorphRecipeManager.java) |
| `AbstractBlockEntityRecipeData` | class | AbstractRecipeData | — | 4 | 54 | [AbstractBlockEntityRecipeData.java](com/illusivesoulworks/polymorph/common/capability/AbstractBlockEntityRecipeData.java) |
| `IPolymorphRecipeManager` | interface | — | — | 0 | 28 | [IPolymorphRecipeManager.java](com/illusivesoulworks/polymorph/api/common/base/IPolymorphRecipeManager.java) |
| `IBlockEntityRecipeData` | interface | — | — | 0 | 10 | [IBlockEntityRecipeData.java](com/illusivesoulworks/polymorph/api/common/capability/IBlockEntityRecipeData.java) |
| `MixinCraftingMenu` | class | — | Mixin | 0 | 46 | [MixinCraftingMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinCraftingMenu.java) |
| `AccessorCrafterMenu` | interface | — | Mixin | 0 | 12 | [AccessorCrafterMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorCrafterMenu.java) |
| `MixinCrafterMenu` | class | — | Mixin | 0 | 41 | [MixinCrafterMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinCrafterMenu.java) |
| `AccessorCraftingMenu` | interface | — | Mixin | 0 | 21 | [AccessorCraftingMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorCraftingMenu.java) |
| `AccessorSmithingScreen` | interface | — | Mixin | 0 | 13 | [AccessorSmithingScreen.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingScreen.java) |
| `MixinSmithingMenu` | class | ItemCombinerMenu | Mixin | 1 | 61 | [MixinSmithingMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinSmithingMenu.java) |
| `AccessorInventoryMenu` | interface | — | Mixin | 0 | 21 | [AccessorInventoryMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorInventoryMenu.java) |
| `PolymorphCommonEvents` | class | — | — | 3 | 27 | [PolymorphCommonEvents.java](com/illusivesoulworks/polymorph/common/PolymorphCommonEvents.java) |
| `ClientPayloadHandler` | class | — | — | 7 | 45 | [ClientPayloadHandler.java](com/illusivesoulworks/polymorph/common/network/ClientPayloadHandler.java) |
| `AbstractRecipeData` | class | IRecipeData<E> | — | 15 | 186 | [AbstractRecipeData.java](com/illusivesoulworks/polymorph/common/capability/AbstractRecipeData.java) |
| `MixinFastBenchUtil` | class | — | Mixin | 0 | 36 | [MixinFastBenchUtil.java](com/illusivesoulworks/polymorph/mixin/integration/fastbench/MixinFastBenchUtil.java) |
| `SPacketHighlightRecipe` | record | CustomPacketPayload | — | 2 | 25 | [SPacketHighlightRecipe.java](com/illusivesoulworks/polymorph/common/network/server/SPacketHighlightRecipe.java) |
| `SPacketPlayerRecipeSync` | record | CustomPacketPayload | — | 2 | 34 | [SPacketPlayerRecipeSync.java](com/illusivesoulworks/polymorph/common/network/server/SPacketPlayerRecipeSync.java) |
| `SPacketRecipesList` | record | CustomPacketPayload | — | 2 | 34 | [SPacketRecipesList.java](com/illusivesoulworks/polymorph/common/network/server/SPacketRecipesList.java) |
| `SPacketRecipeHandshake` | record | CustomPacketPayload | — | 2 | 24 | [SPacketRecipeHandshake.java](com/illusivesoulworks/polymorph/common/network/server/SPacketRecipeHandshake.java) |
| `CPacketPlayerRecipeSelection` | record | CustomPacketPayload | — | 2 | 47 | [CPacketPlayerRecipeSelection.java](com/illusivesoulworks/polymorph/common/network/client/CPacketPlayerRecipeSelection.java) |
| `CPacketPersistentRecipeSelection` | record | CustomPacketPayload | — | 2 | 42 | [CPacketPersistentRecipeSelection.java](com/illusivesoulworks/polymorph/common/network/client/CPacketPersistentRecipeSelection.java) |
| `CPacketBlockEntityListener` | record | CustomPacketPayload | — | 2 | 51 | [CPacketBlockEntityListener.java](com/illusivesoulworks/polymorph/common/network/client/CPacketBlockEntityListener.java) |
| `MixinServerLevel` | class | Level | Mixin | 1 | 75 | [MixinServerLevel.java](com/illusivesoulworks/polymorph/mixin/core/MixinServerLevel.java) |
| `MixinPolymorphApi` | class | — | Mixin | 0 | 17 | [MixinPolymorphApi.java](com/illusivesoulworks/polymorph/mixin/core/MixinPolymorphApi.java) |
| `MixinLevelChunk` | class | — | Mixin | 0 | 46 | [MixinLevelChunk.java](com/illusivesoulworks/polymorph/mixin/core/MixinLevelChunk.java) |
| `MixinPolymorphWidgets` | class | — | Mixin | 0 | 17 | [MixinPolymorphWidgets.java](com/illusivesoulworks/polymorph/mixin/core/MixinPolymorphWidgets.java) |
| `MixinInternalWidgets` | class | — | Mixin | 0 | 22 | [MixinInternalWidgets.java](com/illusivesoulworks/polymorph/mixin/integration/roughlyenoughitems/MixinInternalWidgets.java) |
| `ServerPayloadHandler` | class | — | — | 5 | 48 | [ServerPayloadHandler.java](com/illusivesoulworks/polymorph/common/network/ServerPayloadHandler.java) |
| `PlayerRecipeData` | class | AbstractRecipeData | — | 7 | 105 | [PlayerRecipeData.java](com/illusivesoulworks/polymorph/common/capability/PlayerRecipeData.java) |
| `AbstractRecipesWidget` | class | IRecipesWidget | — | 12 | 145 | [AbstractRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/AbstractRecipesWidget.java) |
| `RecipeCache` | class | — | — | 7 | 126 | [RecipeCache.java](com/illusivesoulworks/polymorph/common/capability/RecipeCache.java) |
| `CrafterRecipeData` | class | AbstractBlockEntityRecipeData | — | 2 | 33 | [CrafterRecipeData.java](com/illusivesoulworks/polymorph/common/capability/CrafterRecipeData.java) |
| `FurnaceRecipeData` | class | AbstractBlockEntityRecipeData | — | 1 | 20 | [FurnaceRecipeData.java](com/illusivesoulworks/polymorph/common/capability/FurnaceRecipeData.java) |
| `IRecipeData` | interface | — | — | 0 | 48 | [IRecipeData.java](com/illusivesoulworks/polymorph/api/common/capability/IRecipeData.java) |
| `IPlayerRecipeData` | interface | — | — | 0 | 11 | [IPlayerRecipeData.java](com/illusivesoulworks/polymorph/api/common/capability/IPlayerRecipeData.java) |
| `CraftingRecipeWrapper` | class | RecipeWrapper | — | 3 | 27 | [CraftingRecipeWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/CraftingRecipeWrapper.java) |
| `RecipeWrapper` | class | — | — | 4 | 60 | [RecipeWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/RecipeWrapper.java) |
| `SmithingRecipeWrapper` | class | RecipeWrapper | — | 1 | 61 | [SmithingRecipeWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/SmithingRecipeWrapper.java) |
| `PolymorphNeoForgePacketDistributor` | class | IPolymorphNetwork | — | 9 | 76 | [PolymorphNeoForgePacketDistributor.java](com/illusivesoulworks/polymorph/common/network/PolymorphNeoForgePacketDistributor.java) |
| `PolymorphIntegrations` | class | — | — | 13 | 131 | [PolymorphIntegrations.java](com/illusivesoulworks/polymorph/common/integration/PolymorphIntegrations.java) |
| `RecipeTransfer` | class | — | — | 4 | 30 | [RecipeTransfer.java](com/illusivesoulworks/polymorph/common/integration/util/RecipeTransfer.java) |
| `BlockEntityTicker` | class | — | — | 4 | 61 | [BlockEntityTicker.java](com/illusivesoulworks/polymorph/common/util/BlockEntityTicker.java) |
| `RecipePair` | record | IRecipePair | — | 3 | 51 | [RecipePair.java](com/illusivesoulworks/polymorph/common/util/RecipePair.java) |
| `IRecipeContext` | interface | — | — | 0 | 11 | [IRecipeContext.java](com/illusivesoulworks/polymorph/api/common/base/IRecipeContext.java) |
| `IRecipePair` | interface | — | — | 0 | 11 | [IRecipePair.java](com/illusivesoulworks/polymorph/api/common/base/IRecipePair.java) |
| `PersistentRecipesWidget` | class | AbstractRecipesWidget | — | 1 | 17 | [PersistentRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/PersistentRecipesWidget.java) |
| `ITickingRecipesWidget` | interface | — | — | 0 | 6 | [ITickingRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/ITickingRecipesWidget.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventsListener` | 4 | `com.illusivesoulworks.polymorph.client` | [ClientEventsListener.java](com/illusivesoulworks/polymorph/client/ClientEventsListener.java) |
| `CommonEventsListener` | 4 | `com.illusivesoulworks.polymorph.common` | [CommonEventsListener.java](com/illusivesoulworks/polymorph/common/CommonEventsListener.java) |

## Mixin（21 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinRecipeManager` | `RecipeManager` | [MixinRecipeManager.java](com/illusivesoulworks/polymorph/mixin/core/MixinRecipeManager.java) |
| `MixinPacketRecipeTransfer` | `PacketRecipeTransfer` | [MixinPacketRecipeTransfer.java](com/illusivesoulworks/polymorph/mixin/integration/jei/MixinPacketRecipeTransfer.java) |
| `MixinRecipeTransferUtil` | `RecipeTransferUtil` | [MixinRecipeTransferUtil.java](com/illusivesoulworks/polymorph/mixin/integration/jei/MixinRecipeTransferUtil.java) |
| `AccessorAbstractFurnaceBlockEntity` | `AccessorAbstractFurnaceBlockEntity` | [AccessorAbstractFurnaceBlockEntity.java](com/illusivesoulworks/polymorph/mixin/core/AccessorAbstractFurnaceBlockEntity.java) |
| `AccessorSmithingTransformRecipe` | `AccessorSmithingTransformRecipe` | [AccessorSmithingTransformRecipe.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingTransformRecipe.java) |
| `MixinRecipeCache` | `RecipeCache` | [MixinRecipeCache.java](com/illusivesoulworks/polymorph/mixin/core/MixinRecipeCache.java) |
| `AccessorSmithingTrimRecipe` | `AccessorSmithingTrimRecipe` | [AccessorSmithingTrimRecipe.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingTrimRecipe.java) |
| `MixinEmiRecipeFiller` | `EmiRecipeFiller` | [MixinEmiRecipeFiller.java](com/illusivesoulworks/polymorph/mixin/integration/emi/MixinEmiRecipeFiller.java) |
| `MixinCraftingMenu` | `CraftingMenu` | [MixinCraftingMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinCraftingMenu.java) |
| `AccessorCrafterMenu` | `AccessorCrafterMenu` | [AccessorCrafterMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorCrafterMenu.java) |
| `MixinCrafterMenu` | `CrafterMenu` | [MixinCrafterMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinCrafterMenu.java) |
| `AccessorCraftingMenu` | `AccessorCraftingMenu` | [AccessorCraftingMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorCraftingMenu.java) |
| `AccessorSmithingScreen` | `AccessorSmithingScreen` | [AccessorSmithingScreen.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingScreen.java) |
| `MixinSmithingMenu` | `SmithingMenu` | [MixinSmithingMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinSmithingMenu.java) |
| `AccessorInventoryMenu` | `AccessorInventoryMenu` | [AccessorInventoryMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorInventoryMenu.java) |
| `MixinFastBenchUtil` | `FastBenchUtil` | [MixinFastBenchUtil.java](com/illusivesoulworks/polymorph/mixin/integration/fastbench/MixinFastBenchUtil.java) |
| `MixinServerLevel` | `ServerLevel` | [MixinServerLevel.java](com/illusivesoulworks/polymorph/mixin/core/MixinServerLevel.java) |
| `MixinPolymorphApi` | `PolymorphApi` | [MixinPolymorphApi.java](com/illusivesoulworks/polymorph/mixin/core/MixinPolymorphApi.java) |
| `MixinLevelChunk` | `LevelChunk` | [MixinLevelChunk.java](com/illusivesoulworks/polymorph/mixin/core/MixinLevelChunk.java) |
| `MixinPolymorphWidgets` | `PolymorphWidgets` | [MixinPolymorphWidgets.java](com/illusivesoulworks/polymorph/mixin/core/MixinPolymorphWidgets.java) |
| `MixinInternalWidgets` | `InternalWidgets` | [MixinInternalWidgets.java](com/illusivesoulworks/polymorph/mixin/integration/roughlyenoughitems/MixinInternalWidgets.java) |

## 全部类（90）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinRecipeManager` | class | IRecipeContext | Mixin | 0 | 56 | [MixinRecipeManager.java](com/illusivesoulworks/polymorph/mixin/core/MixinRecipeManager.java) |
| `ClientEventsListener` | class | — | — | 4 | 30 | [ClientEventsListener.java](com/illusivesoulworks/polymorph/client/ClientEventsListener.java) |
| `MixinPacketRecipeTransfer` | class | — | Mixin | 0 | 18 | [MixinPacketRecipeTransfer.java](com/illusivesoulworks/polymorph/mixin/integration/jei/MixinPacketRecipeTransfer.java) |
| `CommonEventsListener` | class | — | — | 4 | 34 | [CommonEventsListener.java](com/illusivesoulworks/polymorph/common/CommonEventsListener.java) |
| `MixinRecipeTransferUtil` | class | — | Mixin | 0 | 34 | [MixinRecipeTransferUtil.java](com/illusivesoulworks/polymorph/mixin/integration/jei/MixinRecipeTransferUtil.java) |
| `ClientPacketHandler` | class | — | — | 6 | 73 | [ClientPacketHandler.java](com/illusivesoulworks/polymorph/common/network/server/ClientPacketHandler.java) |
| `PolymorphNeoForgeMod` | class | — | Mod | 3 | 58 | [PolymorphNeoForgeMod.java](com/illusivesoulworks/polymorph/PolymorphNeoForgeMod.java) |
| `AccessorAbstractFurnaceBlockEntity` | interface | — | Mixin | 0 | 14 | [AccessorAbstractFurnaceBlockEntity.java](com/illusivesoulworks/polymorph/mixin/core/AccessorAbstractFurnaceBlockEntity.java) |
| `AccessorSmithingTransformRecipe` | interface | — | Mixin | 0 | 19 | [AccessorSmithingTransformRecipe.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingTransformRecipe.java) |
| `MixinRecipeCache` | class | — | Mixin | 1 | 34 | [MixinRecipeCache.java](com/illusivesoulworks/polymorph/mixin/core/MixinRecipeCache.java) |
| `AccessorSmithingTrimRecipe` | interface | — | Mixin | 0 | 19 | [AccessorSmithingTrimRecipe.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingTrimRecipe.java) |
| `MixinEmiRecipeFiller` | class | — | Mixin | 0 | 28 | [MixinEmiRecipeFiller.java](com/illusivesoulworks/polymorph/mixin/integration/emi/MixinEmiRecipeFiller.java) |
| `PolymorphClientEvents` | class | — | — | 4 | 39 | [PolymorphClientEvents.java](com/illusivesoulworks/polymorph/client/PolymorphClientEvents.java) |
| `PolymorphRecipeManager` | class | IPolymorphRecipeManager | — | 3 | 47 | [PolymorphRecipeManager.java](com/illusivesoulworks/polymorph/common/PolymorphRecipeManager.java) |
| `AbstractBlockEntityRecipeData` | class | AbstractRecipeData | — | 4 | 54 | [AbstractBlockEntityRecipeData.java](com/illusivesoulworks/polymorph/common/capability/AbstractBlockEntityRecipeData.java) |
| `IPolymorphRecipeManager` | interface | — | — | 0 | 28 | [IPolymorphRecipeManager.java](com/illusivesoulworks/polymorph/api/common/base/IPolymorphRecipeManager.java) |
| `IBlockEntityRecipeData` | interface | — | — | 0 | 10 | [IBlockEntityRecipeData.java](com/illusivesoulworks/polymorph/api/common/capability/IBlockEntityRecipeData.java) |
| `MixinCraftingMenu` | class | — | Mixin | 0 | 46 | [MixinCraftingMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinCraftingMenu.java) |
| `AccessorCrafterMenu` | interface | — | Mixin | 0 | 12 | [AccessorCrafterMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorCrafterMenu.java) |
| `MixinCrafterMenu` | class | — | Mixin | 0 | 41 | [MixinCrafterMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinCrafterMenu.java) |
| `AccessorCraftingMenu` | interface | — | Mixin | 0 | 21 | [AccessorCraftingMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorCraftingMenu.java) |
| `AccessorSmithingScreen` | interface | — | Mixin | 0 | 13 | [AccessorSmithingScreen.java](com/illusivesoulworks/polymorph/mixin/core/AccessorSmithingScreen.java) |
| `MixinSmithingMenu` | class | ItemCombinerMenu | Mixin | 1 | 61 | [MixinSmithingMenu.java](com/illusivesoulworks/polymorph/mixin/core/MixinSmithingMenu.java) |
| `AccessorInventoryMenu` | interface | — | Mixin | 0 | 21 | [AccessorInventoryMenu.java](com/illusivesoulworks/polymorph/mixin/core/AccessorInventoryMenu.java) |
| `PolymorphCommonEvents` | class | — | — | 3 | 27 | [PolymorphCommonEvents.java](com/illusivesoulworks/polymorph/common/PolymorphCommonEvents.java) |
| `ClientPayloadHandler` | class | — | — | 7 | 45 | [ClientPayloadHandler.java](com/illusivesoulworks/polymorph/common/network/ClientPayloadHandler.java) |
| `AbstractRecipeData` | class | IRecipeData<E> | — | 15 | 186 | [AbstractRecipeData.java](com/illusivesoulworks/polymorph/common/capability/AbstractRecipeData.java) |
| `MixinFastBenchUtil` | class | — | Mixin | 0 | 36 | [MixinFastBenchUtil.java](com/illusivesoulworks/polymorph/mixin/integration/fastbench/MixinFastBenchUtil.java) |
| `SPacketHighlightRecipe` | record | CustomPacketPayload | — | 2 | 25 | [SPacketHighlightRecipe.java](com/illusivesoulworks/polymorph/common/network/server/SPacketHighlightRecipe.java) |
| `SPacketPlayerRecipeSync` | record | CustomPacketPayload | — | 2 | 34 | [SPacketPlayerRecipeSync.java](com/illusivesoulworks/polymorph/common/network/server/SPacketPlayerRecipeSync.java) |
| `SPacketRecipesList` | record | CustomPacketPayload | — | 2 | 34 | [SPacketRecipesList.java](com/illusivesoulworks/polymorph/common/network/server/SPacketRecipesList.java) |
| `SPacketRecipeHandshake` | record | CustomPacketPayload | — | 2 | 24 | [SPacketRecipeHandshake.java](com/illusivesoulworks/polymorph/common/network/server/SPacketRecipeHandshake.java) |
| `CPacketPlayerRecipeSelection` | record | CustomPacketPayload | — | 2 | 47 | [CPacketPlayerRecipeSelection.java](com/illusivesoulworks/polymorph/common/network/client/CPacketPlayerRecipeSelection.java) |
| `CPacketPersistentRecipeSelection` | record | CustomPacketPayload | — | 2 | 42 | [CPacketPersistentRecipeSelection.java](com/illusivesoulworks/polymorph/common/network/client/CPacketPersistentRecipeSelection.java) |
| `CPacketBlockEntityListener` | record | CustomPacketPayload | — | 2 | 51 | [CPacketBlockEntityListener.java](com/illusivesoulworks/polymorph/common/network/client/CPacketBlockEntityListener.java) |
| `MixinServerLevel` | class | Level | Mixin | 1 | 75 | [MixinServerLevel.java](com/illusivesoulworks/polymorph/mixin/core/MixinServerLevel.java) |
| `MixinPolymorphApi` | class | — | Mixin | 0 | 17 | [MixinPolymorphApi.java](com/illusivesoulworks/polymorph/mixin/core/MixinPolymorphApi.java) |
| `MixinLevelChunk` | class | — | Mixin | 0 | 46 | [MixinLevelChunk.java](com/illusivesoulworks/polymorph/mixin/core/MixinLevelChunk.java) |
| `MixinPolymorphWidgets` | class | — | Mixin | 0 | 17 | [MixinPolymorphWidgets.java](com/illusivesoulworks/polymorph/mixin/core/MixinPolymorphWidgets.java) |
| `MixinInternalWidgets` | class | — | Mixin | 0 | 22 | [MixinInternalWidgets.java](com/illusivesoulworks/polymorph/mixin/integration/roughlyenoughitems/MixinInternalWidgets.java) |
| `ServerPayloadHandler` | class | — | — | 5 | 48 | [ServerPayloadHandler.java](com/illusivesoulworks/polymorph/common/network/ServerPayloadHandler.java) |
| `PlayerRecipeData` | class | AbstractRecipeData | — | 7 | 105 | [PlayerRecipeData.java](com/illusivesoulworks/polymorph/common/capability/PlayerRecipeData.java) |
| `AbstractRecipesWidget` | class | IRecipesWidget | — | 12 | 145 | [AbstractRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/AbstractRecipesWidget.java) |
| `RecipeCache` | class | — | — | 7 | 126 | [RecipeCache.java](com/illusivesoulworks/polymorph/common/capability/RecipeCache.java) |
| `CrafterRecipeData` | class | AbstractBlockEntityRecipeData | — | 2 | 33 | [CrafterRecipeData.java](com/illusivesoulworks/polymorph/common/capability/CrafterRecipeData.java) |
| `FurnaceRecipeData` | class | AbstractBlockEntityRecipeData | — | 1 | 20 | [FurnaceRecipeData.java](com/illusivesoulworks/polymorph/common/capability/FurnaceRecipeData.java) |
| `IRecipeData` | interface | — | — | 0 | 48 | [IRecipeData.java](com/illusivesoulworks/polymorph/api/common/capability/IRecipeData.java) |
| `IPlayerRecipeData` | interface | — | — | 0 | 11 | [IPlayerRecipeData.java](com/illusivesoulworks/polymorph/api/common/capability/IPlayerRecipeData.java) |
| `CraftingRecipeWrapper` | class | RecipeWrapper | — | 3 | 27 | [CraftingRecipeWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/CraftingRecipeWrapper.java) |
| `RecipeWrapper` | class | — | — | 4 | 60 | [RecipeWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/RecipeWrapper.java) |
| `SmithingRecipeWrapper` | class | RecipeWrapper | — | 1 | 61 | [SmithingRecipeWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/SmithingRecipeWrapper.java) |
| `PolymorphNeoForgePacketDistributor` | class | IPolymorphNetwork | — | 9 | 76 | [PolymorphNeoForgePacketDistributor.java](com/illusivesoulworks/polymorph/common/network/PolymorphNeoForgePacketDistributor.java) |
| `PolymorphIntegrations` | class | — | — | 13 | 131 | [PolymorphIntegrations.java](com/illusivesoulworks/polymorph/common/integration/PolymorphIntegrations.java) |
| `RecipeTransfer` | class | — | — | 4 | 30 | [RecipeTransfer.java](com/illusivesoulworks/polymorph/common/integration/util/RecipeTransfer.java) |
| `BlockEntityTicker` | class | — | — | 4 | 61 | [BlockEntityTicker.java](com/illusivesoulworks/polymorph/common/util/BlockEntityTicker.java) |
| `RecipePair` | record | IRecipePair | — | 3 | 51 | [RecipePair.java](com/illusivesoulworks/polymorph/common/util/RecipePair.java) |
| `IRecipeContext` | interface | — | — | 0 | 11 | [IRecipeContext.java](com/illusivesoulworks/polymorph/api/common/base/IRecipeContext.java) |
| `IRecipePair` | interface | — | — | 0 | 11 | [IRecipePair.java](com/illusivesoulworks/polymorph/api/common/base/IRecipePair.java) |
| `PersistentRecipesWidget` | class | AbstractRecipesWidget | — | 1 | 17 | [PersistentRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/PersistentRecipesWidget.java) |
| `ITickingRecipesWidget` | interface | — | — | 0 | 6 | [ITickingRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/ITickingRecipesWidget.java) |
| `IRecipesWidget` | interface | — | — | 0 | 31 | [IRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/base/IRecipesWidget.java) |
| `CrafterRecipesWidget` | class | PersistentRecipesWidget | — | 1 | 32 | [CrafterRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/widgets/CrafterRecipesWidget.java) |
| `FurnaceRecipesWidget` | class | PersistentRecipesWidget | — | 1 | 22 | [FurnaceRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/widgets/FurnaceRecipesWidget.java) |
| `PlayerRecipesWidget` | class | AbstractRecipesWidget | — | 2 | 36 | [PlayerRecipesWidget.java](com/illusivesoulworks/polymorph/api/client/widgets/PlayerRecipesWidget.java) |
| `RecipesWidget` | class | — | — | 4 | 61 | [RecipesWidget.java](com/illusivesoulworks/polymorph/client/RecipesWidget.java) |
| `PolymorphCommands` | class | — | — | 3 | 131 | [PolymorphCommands.java](com/illusivesoulworks/polymorph/server/PolymorphCommands.java) |
| `PolymorphNeoForgeCapabilities` | class | INBTSerializable<CompoundTag> | — | 4 | 59 | [PolymorphNeoForgeCapabilities.java](com/illusivesoulworks/polymorph/common/PolymorphNeoForgeCapabilities.java) |
| `SPacketUpdatePreview` | class | CustomPacketPayload | — | 2 | 24 | [SPacketUpdatePreview.java](com/illusivesoulworks/polymorph/common/network/server/SPacketUpdatePreview.java) |
| `PolymorphCapabilities` | class | — | — | 2 | 18 | [PolymorphCapabilities.java](com/illusivesoulworks/polymorph/common/capability/PolymorphCapabilities.java) |
| `AbstractCompatibilityModule` | class | — | — | 6 | 30 | [AbstractCompatibilityModule.java](com/illusivesoulworks/polymorph/common/integration/AbstractCompatibilityModule.java) |
| `IPolymorphNetwork` | interface | — | — | 0 | 26 | [IPolymorphNetwork.java](com/illusivesoulworks/polymorph/api/common/base/IPolymorphNetwork.java) |
| `PolymorphApiImpl` | class | PolymorphApi | — | 10 | 105 | [PolymorphApiImpl.java](com/illusivesoulworks/polymorph/common/PolymorphApiImpl.java) |
| `NeoForgeClientPlatform` | class | IClientPlatform | — | 2 | 17 | [NeoForgeClientPlatform.java](com/illusivesoulworks/polymorph/platform/NeoForgeClientPlatform.java) |
| `NeoForgeIntegrationPlatform` | class | IIntegrationPlatform | — | 1 | 18 | [NeoForgeIntegrationPlatform.java](com/illusivesoulworks/polymorph/platform/NeoForgeIntegrationPlatform.java) |
| `IIntegrationPlatform` | interface | — | — | 0 | 10 | [IIntegrationPlatform.java](com/illusivesoulworks/polymorph/platform/services/IIntegrationPlatform.java) |
| `IClientPlatform` | interface | — | — | 0 | 10 | [IClientPlatform.java](com/illusivesoulworks/polymorph/platform/services/IClientPlatform.java) |
| `PolymorphApi` | class | — | — | 11 | 53 | [PolymorphApi.java](com/illusivesoulworks/polymorph/api/PolymorphApi.java) |
| `SelectionWidget` | class | Renderable | — | 13 | 154 | [SelectionWidget.java](com/illusivesoulworks/polymorph/api/client/widgets/children/SelectionWidget.java) |
| `IntegratedMixinPlugin` | class | IMixinConfigPlugin | — | 9 | 73 | [IntegratedMixinPlugin.java](com/illusivesoulworks/polymorph/mixin/IntegratedMixinPlugin.java) |
| `NeoForgePlatform` | class | IPlatform | — | 9 | 74 | [NeoForgePlatform.java](com/illusivesoulworks/polymorph/platform/NeoForgePlatform.java) |
| `PolymorphWidgets` | class | — | — | 6 | 32 | [PolymorphWidgets.java](com/illusivesoulworks/polymorph/api/client/PolymorphWidgets.java) |
| `OutputWidget` | class | AbstractWidget | — | 7 | 70 | [OutputWidget.java](com/illusivesoulworks/polymorph/api/client/widgets/children/OutputWidget.java) |
| `PolymorphWidgetsImpl` | class | PolymorphWidgets | — | 6 | 88 | [PolymorphWidgetsImpl.java](com/illusivesoulworks/polymorph/client/PolymorphWidgetsImpl.java) |
| `PolymorphCommonMod` | class | — | — | 3 | 46 | [PolymorphCommonMod.java](com/illusivesoulworks/polymorph/PolymorphCommonMod.java) |
| `PolymorphConstants` | class | — | — | 0 | 11 | [PolymorphConstants.java](com/illusivesoulworks/polymorph/PolymorphConstants.java) |
| `IngredientWrapper` | class | — | — | 2 | 44 | [IngredientWrapper.java](com/illusivesoulworks/polymorph/server/wrapper/IngredientWrapper.java) |
| `FastBenchModule` | class | AbstractCompatibilityModule | — | 1 | 52 | [FastBenchModule.java](com/illusivesoulworks/polymorph/common/integration/fastbench/FastBenchModule.java) |
| `Services` | class | — | — | 1 | 20 | [Services.java](com/illusivesoulworks/polymorph/platform/Services.java) |
| `IPlatform` | interface | — | — | 0 | 30 | [IPlatform.java](com/illusivesoulworks/polymorph/platform/services/IPlatform.java) |
| `OpenSelectionButton` | class | ImageButton | — | 2 | 36 | [OpenSelectionButton.java](com/illusivesoulworks/polymorph/api/client/widgets/children/OpenSelectionButton.java) |
