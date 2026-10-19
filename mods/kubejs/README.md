# KubeJS（`kubejs`）

> jar：`[魔改]kubejs.jar` ｜ 版本：`2101.7.2-build.348` ｜ 作者：latvian.dev
> 规模：**848 个类 / 71 个包 / 61182 行** ｜ 事件处理类 13 ｜ Mixin 88 ｜ 注册/管理类 94

**依赖**：`neoforge`、`rhino`、`jei`、`roughlyenoughitems`、`emi`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.latvian.mods.kubejs.core.mixin` | 86 | `RecipeManagerMixin`、`IItemHandlerMixin`、`EntityMixin` |
| `dev.latvian.mods.kubejs.core` | 59 | `RecipeManagerKJS`、`RegistryObjectKJS`、`TagManagerKJS` |
| `dev.latvian.mods.kubejs.util` | 52 | `ScheduledEvents`、`RegistryAccessContainer`、`RegistryOpsContainer` |
| `dev.latvian.mods.kubejs.recipe.component` | 39 | `CustomObjectRecipeComponent`、`RecipeComponentTypeRegistry`、`ListRecipeComponent` |
| `dev.latvian.mods.kubejs.client` | 32 | `KubeJSClientEventHandler`、`BlockEntityRendererRegistryKubeEvent`、`EntityRendererRegistryKubeEvent` |
| `dev.latvian.mods.kubejs.item` | 29 | `KubeJSItemEventHandler`、`ItemBuilder`、`ItemHandlerUtils` |
| `dev.latvian.mods.kubejs.script` | 26 | `TypeWrapperRegistry`、`ConsoleJS`、`ScriptManager` |
| `dev.latvian.mods.kubejs.plugin.builtin.wrapper` | 25 | `RegistryWrapper`、`BlockWrapper`、`ItemWrapper` |
| `dev.latvian.mods.kubejs.block` | 23 | `KubeJSBlockEventHandler`、`BlockBuilder`、`BlockModificationKubeEvent` |
| `dev.latvian.mods.kubejs.net` | 20 | `KubeJSNet`、`NetworkKubeEvent`、`SendDataFromClientPayload` |
| `dev.latvian.mods.kubejs.recipe` | 18 | `RecipesKubeEvent`、`KubeRecipe`、`RecipeSchemaProvider` |
| `dev.latvian.mods.kubejs.block.custom` | 17 | `BasicKubeBlock`、`CropBlockBuilder`、`CardinalBlockBuilder` |
| `dev.latvian.mods.kubejs.recipe.viewer.server` | 17 | `RemoteRecipeViewerDataUpdatedEvent`、`ServerRemoveRecipesKubeEvent`、`ServerRemoveItemEntriesKubeEvent` |
| `dev.latvian.mods.kubejs.block.entity` | 16 | `BlockEntityAttachmentRegistry`、`BlockEntityAttachmentHandler`、`BlockEntityTickKubeEvent` |
| `dev.latvian.mods.kubejs.recipe.schema` | 16 | `RecipeMappingRegistry`、`RecipeSchemaRegistry`、`RecipeFactoryRegistry` |
| `dev.latvian.mods.kubejs.registry` | 15 | `RegistryEventHandler`、`BuilderTypeRegistryHandler`、`RegistryKubeEvent` |
| `dev.latvian.mods.kubejs.player` | 14 | `KubeJSPlayerEventHandler`、`PlayerStatsJS`、`PlayerChatReceivedKubeEvent` |
| `dev.latvian.mods.kubejs.event` | 14 | `EventHandler`、`TargetedEventHandler`、`EventGroupRegistry` |
| `dev.latvian.mods.kubejs.recipe.filter` | 14 | `RecipeFilterParseEvent`、`RecipeMatchContext`、`RecipeFilter` |
| `dev.latvian.mods.kubejs.entity` | 12 | `KubeJSEntityEventHandler`、`CheckLivingEntitySpawnKubeEvent`、`LivingEntityDropsKubeEvent` |
| `dev.latvian.mods.kubejs.integration.rei` | 12 | `REIRemoveRecipeKubeEvent`、`KubeJSREIPlugin`、`REIRegisterItemSubtypesKubeEvent` |
| `dev.latvian.mods.kubejs.fluid` | 12 | `FluidTypeBuilder`、`FluidBuilder`、`FluidBucketItemBuilder` |
| `dev.latvian.mods.kubejs.misc` | 12 | `SoundEventBuilder`、`MobEffectBuilder`、`PoiTypeBuilder` |
| `dev.latvian.mods.kubejs.plugin.builtin.event` | 11 | `RecipeViewerEvents`、`NetworkEvents`、`ItemEvents` |
| `dev.latvian.mods.kubejs.recipe.schema.function` | 11 | `RecipeSchemaFunctionRegistry`、`CustomRecipeSchemaFunctionRegistry`、`RecipeSchemaJSFunction` |
| `dev.latvian.mods.kubejs.command` | 10 | `CommandRegistryKubeEvent`、`KubeJSCommands`、`KubeJSClientCommands` |
| `dev.latvian.mods.kubejs.web` | 10 | `LocalWebServerAPIRegistry`、`LocalWebServerRegistry`、`KJSHTTPRequest` |
| `dev.latvian.mods.kubejs.block.callback` | 10 | `BlockStateModifyPlacementCallback`、`EntityFallenOnBlockCallback`、`BlockStateModifyCallback` |
| `dev.latvian.mods.kubejs.error` | 10 | `EmptyRecipeComponentException`、`InvalidRecipeComponentValueException`、`InvalidRecipeComponentException` |
| `dev.latvian.mods.kubejs.text.action` | 10 | `TextActionBuilder`、`RemoveTextTextAction`、`RemoveExactTextTextAction` |
| `dev.latvian.mods.kubejs.server` | 9 | `KubeJSServerEventHandler`、`ServerScriptManager`、`BasicCommandKubeEvent` |
| `dev.latvian.mods.kubejs` | 9 | `KubeJSModEventHandler`、`KubeJS`、`StartupScriptManager` |
| `dev.latvian.mods.kubejs.recipe.viewer` | 9 | `RemoveRecipesKubeEvent`、`RecipeViewerEntryType`、`GroupEntriesKubeEvent` |
| `dev.latvian.mods.kubejs.integration.jei` | 9 | `JEIRemoveRecipesKubeEvent`、`KubeJSJEIPlugin`、`JEIRemoveEntriesKubeEvent` |
| `dev.latvian.mods.kubejs.recipe.ingredientaction` | 9 | `IngredientActionTypeRegistry`、`IngredientActionType`、`ConsumeAction` |
| `dev.latvian.mods.kubejs.level` | 8 | `KubeJSWorldEventHandler`、`ExplosionKubeEvent`、`CachedLevelBlock` |
| `dev.latvian.mods.kubejs.util.registrypredicate` | 8 | `RegistryTagKeyPredicate`、`RegistryTagIDPredicate`、`RegistryPredicate` |
| `dev.latvian.mods.kubejs.script.data` | 8 | `KubeFileResourcePack`、`VirtualDataMapFile`、`VirtualResourcePack` |
| `dev.latvian.mods.kubejs.item.custom` | 7 | `ItemToolTierRegistryKubeEvent`、`SmithingTemplateItemBuilder`、`ArmorItemBuilder` |
| `dev.latvian.mods.kubejs.gui.chest` | 7 | `ChestMenuClickHandler`、`ChestMenuInventoryClickEvent`、`ChestMenuClickEvent` |
| … | | 其余 31 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `KubeJSClientEventHandler` | class | — | EventBusSubscriber | 31 | 562 | [KubeJSClientEventHandler.java](dev/latvian/mods/kubejs/client/KubeJSClientEventHandler.java) |
| `RegistryEventHandler` | class | — | EventBusSubscriber | 3 | 77 | [RegistryEventHandler.java](dev/latvian/mods/kubejs/registry/RegistryEventHandler.java) |
| `KubeJSEntityEventHandler` | class | — | EventBusSubscriber | 6 | 81 | [KubeJSEntityEventHandler.java](dev/latvian/mods/kubejs/entity/KubeJSEntityEventHandler.java) |
| `KubeJSBlockEventHandler` | class | — | EventBusSubscriber | 6 | 74 | [KubeJSBlockEventHandler.java](dev/latvian/mods/kubejs/block/KubeJSBlockEventHandler.java) |
| `KubeJSItemEventHandler` | class | — | EventBusSubscriber | 9 | 109 | [KubeJSItemEventHandler.java](dev/latvian/mods/kubejs/item/KubeJSItemEventHandler.java) |
| `KubeJSPlayerEventHandler` | class | — | EventBusSubscriber | 12 | 174 | [KubeJSPlayerEventHandler.java](dev/latvian/mods/kubejs/player/KubeJSPlayerEventHandler.java) |
| `KubeJSServerEventHandler` | class | — | EventBusSubscriber | 10 | 185 | [KubeJSServerEventHandler.java](dev/latvian/mods/kubejs/server/KubeJSServerEventHandler.java) |
| `KubeJSModEventHandler` | class | ICapabilityProvider<KubeBlockEntity | EventBusSubscriber | 5 | 133 | [KubeJSModEventHandler.java](dev/latvian/mods/kubejs/KubeJSModEventHandler.java) |
| `KubeJSWorldEventHandler` | class | — | EventBusSubscriber | 5 | 51 | [KubeJSWorldEventHandler.java](dev/latvian/mods/kubejs/level/KubeJSWorldEventHandler.java) |
| `RecipesKubeEvent` | class | KubeEvent | — | 33 | 676 | [RecipesKubeEvent.java](dev/latvian/mods/kubejs/recipe/RecipesKubeEvent.java) |
| `GameStagesIntegration` | class | — | EventBusSubscriber | 1 | 13 | [GameStagesIntegration.java](dev/latvian/mods/kubejs/integration/gamestages/GameStagesIntegration.java) |
| `RecipeManagerMixin` | class | RecipeManagerKJS | Mixin | 3 | 123 | [RecipeManagerMixin.java](dev/latvian/mods/kubejs/core/mixin/RecipeManagerMixin.java) |
| `BlockEntityRendererRegistryKubeEvent` | class | ClientKubeEvent | — | 1 | 18 | [BlockEntityRendererRegistryKubeEvent.java](dev/latvian/mods/kubejs/client/BlockEntityRendererRegistryKubeEvent.java) |
| `KubeJSNet` | interface | — | EventBusSubscriber | 1 | 75 | [KubeJSNet.java](dev/latvian/mods/kubejs/net/KubeJSNet.java) |
| `IItemHandlerMixin` | interface | — | Mixin | 0 | 66 | [IItemHandlerMixin.java](dev/latvian/mods/kubejs/core/mixin/IItemHandlerMixin.java) |
| `EntityMixin` | class | EntityKJS | Mixin、RemapPrefixForJS | 26 | 167 | [EntityMixin.java](dev/latvian/mods/kubejs/core/mixin/EntityMixin.java) |
| `ItemStackHandlerMixin` | class | — | Mixin | 0 | 17 | [ItemStackHandlerMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemStackHandlerMixin.java) |
| `KubeRecipe` | class | RecipeLikeKJS | — | 43 | 606 | [KubeRecipe.java](dev/latvian/mods/kubejs/recipe/KubeRecipe.java) |
| `CommandRegistryKubeEvent` | class | KubeEvent | — | 7 | 53 | [CommandRegistryKubeEvent.java](dev/latvian/mods/kubejs/command/CommandRegistryKubeEvent.java) |
| `BuilderTypeRegistryHandler` | record | BuilderTypeRegistry | — | 10 | 133 | [BuilderTypeRegistryHandler.java](dev/latvian/mods/kubejs/registry/BuilderTypeRegistryHandler.java) |
| `EntityRendererRegistryKubeEvent` | class | ClientKubeEvent | — | 1 | 18 | [EntityRendererRegistryKubeEvent.java](dev/latvian/mods/kubejs/client/EntityRendererRegistryKubeEvent.java) |
| `MenuScreenRegistryKubeEvent` | class | ClientKubeEvent | — | 1 | 18 | [MenuScreenRegistryKubeEvent.java](dev/latvian/mods/kubejs/client/MenuScreenRegistryKubeEvent.java) |
| `BlockBuilder` | class | ModelledBuilderBase | ReturnsSelf | 79 | 810 | [BlockBuilder.java](dev/latvian/mods/kubejs/block/BlockBuilder.java) |
| `EventHandler` | class | BaseFunction | — | 18 | 254 | [EventHandler.java](dev/latvian/mods/kubejs/event/EventHandler.java) |
| `RecipeViewerEvents` | interface | — | — | 0 | 32 | [RecipeViewerEvents.java](dev/latvian/mods/kubejs/plugin/builtin/event/RecipeViewerEvents.java) |
| `ClientPacketListenerMixin` | class | ClientPacketListenerKJS | Mixin | 0 | 19 | [ClientPacketListenerMixin.java](dev/latvian/mods/kubejs/core/mixin/ClientPacketListenerMixin.java) |
| `ItemMixin` | class | ItemKJS | Mixin、RemapPrefixForJS | 12 | 241 | [ItemMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemMixin.java) |
| `KeyboardHandlerMixin` | class | — | Mixin | 1 | 25 | [KeyboardHandlerMixin.java](dev/latvian/mods/kubejs/core/mixin/KeyboardHandlerMixin.java) |
| `TagManagerMixin` | class | TagManagerKJS | Mixin | 0 | 70 | [TagManagerMixin.java](dev/latvian/mods/kubejs/core/mixin/TagManagerMixin.java) |
| `RecipeSchemaProvider` | class | DataProvider | Experimental | 31 | 283 | [RecipeSchemaProvider.java](dev/latvian/mods/kubejs/recipe/RecipeSchemaProvider.java) |
| `ItemToolTierRegistryKubeEvent` | record | KubeStartupEvent | Info | 3 | 30 | [ItemToolTierRegistryKubeEvent.java](dev/latvian/mods/kubejs/item/custom/ItemToolTierRegistryKubeEvent.java) |
| `NetworkEvents` | interface | — | — | 0 | 12 | [NetworkEvents.java](dev/latvian/mods/kubejs/plugin/builtin/event/NetworkEvents.java) |
| `KeybindRegistryKubeEvent` | class | ClientKubeEvent | — | 13 | 93 | [KeybindRegistryKubeEvent.java](dev/latvian/mods/kubejs/client/KeybindRegistryKubeEvent.java) |
| `KubeJS` | class | — | Mod | 3 | 137 | [KubeJS.java](dev/latvian/mods/kubejs/KubeJS.java) |
| `ItemEntityMixin` | class | ItemEntityKJS | Mixin、RemapPrefixForJS | 0 | 12 | [ItemEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemEntityMixin.java) |
| `ItemStackClientMixin` | class | — | Mixin | 0 | 16 | [ItemStackClientMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemStackClientMixin.java) |
| `FallingBlockEntityMixin` | class | — | Mixin | 0 | 74 | [FallingBlockEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/FallingBlockEntityMixin.java) |
| `RecipeHolderMixin` | class | RecipeHolderKJS | Mixin | 0 | 25 | [RecipeHolderMixin.java](dev/latvian/mods/kubejs/core/mixin/RecipeHolderMixin.java) |
| `RecipeInputMixin` | interface | — | Mixin | 0 | 10 | [RecipeInputMixin.java](dev/latvian/mods/kubejs/core/mixin/RecipeInputMixin.java) |
| `WorldLoaderPackConfigMixin` | class | — | Mixin | 0 | 21 | [WorldLoaderPackConfigMixin.java](dev/latvian/mods/kubejs/core/mixin/WorldLoaderPackConfigMixin.java) |
| `ItemFrameEntityMixin` | class | ItemFrameEntityKJS | Mixin、RemapPrefixForJS | 0 | 12 | [ItemFrameEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemFrameEntityMixin.java) |
| `LootDataTypeMixin` | class | — | Mixin | 0 | 38 | [LootDataTypeMixin.java](dev/latvian/mods/kubejs/core/mixin/LootDataTypeMixin.java) |
| `BlockEntityAttachmentRegistry` | interface | — | — | 0 | 14 | [BlockEntityAttachmentRegistry.java](dev/latvian/mods/kubejs/block/entity/BlockEntityAttachmentRegistry.java) |
| `BlockStatePredicate` | interface | BlockStatePredicate | — | 42 | 581 | [BlockStatePredicate.java](dev/latvian/mods/kubejs/block/state/BlockStatePredicate.java) |
| `RecipeTypeRegistryContext` | record | — | — | 2 | 18 | [RecipeTypeRegistryContext.java](dev/latvian/mods/kubejs/recipe/RecipeTypeRegistryContext.java) |
| `SpecialRecipeSerializerManager` | class | Event | — | 7 | 70 | [SpecialRecipeSerializerManager.java](dev/latvian/mods/kubejs/recipe/special/SpecialRecipeSerializerManager.java) |
| `CustomObjectRecipeComponent` | class | RecipeComponent<List<CustomObjectRec | — | 25 | 335 | [CustomObjectRecipeComponent.java](dev/latvian/mods/kubejs/recipe/component/CustomObjectRecipeComponent.java) |
| `RecipeComponentTypeRegistry` | interface | — | — | 0 | 7 | [RecipeComponentTypeRegistry.java](dev/latvian/mods/kubejs/recipe/component/RecipeComponentTypeRegistry.java) |
| `RecipeMappingRegistry` | class | KubeEvent | — | 2 | 23 | [RecipeMappingRegistry.java](dev/latvian/mods/kubejs/recipe/schema/RecipeMappingRegistry.java) |
| `RecipeSchemaRegistry` | class | KubeEvent | — | 3 | 25 | [RecipeSchemaRegistry.java](dev/latvian/mods/kubejs/recipe/schema/RecipeSchemaRegistry.java) |
| `RecipeFactoryRegistry` | class | — | — | 2 | 22 | [RecipeFactoryRegistry.java](dev/latvian/mods/kubejs/recipe/schema/RecipeFactoryRegistry.java) |
| `RecipePostProcessorTypeRegistry` | interface | — | — | 0 | 7 | [RecipePostProcessorTypeRegistry.java](dev/latvian/mods/kubejs/recipe/schema/postprocessing/RecipePostProcessorTypeRegistry.java) |
| `RecipeSchemaFunctionRegistry` | interface | — | — | 0 | 7 | [RecipeSchemaFunctionRegistry.java](dev/latvian/mods/kubejs/recipe/schema/function/RecipeSchemaFunctionRegistry.java) |
| `CustomRecipeSchemaFunctionRegistry` | interface | — | — | 0 | 9 | [CustomRecipeSchemaFunctionRegistry.java](dev/latvian/mods/kubejs/recipe/schema/function/CustomRecipeSchemaFunctionRegistry.java) |
| `TargetedEventHandler` | class | EventHandler | — | 11 | 132 | [TargetedEventHandler.java](dev/latvian/mods/kubejs/event/TargetedEventHandler.java) |
| `RegistryKubeEvent` | class | KubeStartupEvent | — | 6 | 115 | [RegistryKubeEvent.java](dev/latvian/mods/kubejs/registry/RegistryKubeEvent.java) |
| `ItemEvents` | interface | — | — | 0 | 50 | [ItemEvents.java](dev/latvian/mods/kubejs/plugin/builtin/event/ItemEvents.java) |
| `ClientEvents` | interface | — | — | 0 | 38 | [ClientEvents.java](dev/latvian/mods/kubejs/plugin/builtin/event/ClientEvents.java) |
| `EntityEvents` | interface | — | — | 0 | 32 | [EntityEvents.java](dev/latvian/mods/kubejs/plugin/builtin/event/EntityEvents.java) |
| `BlockEvents` | interface | — | — | 0 | 55 | [BlockEvents.java](dev/latvian/mods/kubejs/plugin/builtin/event/BlockEvents.java) |

## 事件处理（`@SubscribeEvent`，13 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `KubeJSClientEventHandler` | 22 | `dev.latvian.mods.kubejs.client` | [KubeJSClientEventHandler.java](dev/latvian/mods/kubejs/client/KubeJSClientEventHandler.java) |
| `KubeJSPlayerEventHandler` | 12 | `dev.latvian.mods.kubejs.player` | [KubeJSPlayerEventHandler.java](dev/latvian/mods/kubejs/player/KubeJSPlayerEventHandler.java) |
| `KubeJSServerEventHandler` | 10 | `dev.latvian.mods.kubejs.server` | [KubeJSServerEventHandler.java](dev/latvian/mods/kubejs/server/KubeJSServerEventHandler.java) |
| `KubeJSItemEventHandler` | 9 | `dev.latvian.mods.kubejs.item` | [KubeJSItemEventHandler.java](dev/latvian/mods/kubejs/item/KubeJSItemEventHandler.java) |
| `KubeJSEntityEventHandler` | 6 | `dev.latvian.mods.kubejs.entity` | [KubeJSEntityEventHandler.java](dev/latvian/mods/kubejs/entity/KubeJSEntityEventHandler.java) |
| `KubeJSBlockEventHandler` | 6 | `dev.latvian.mods.kubejs.block` | [KubeJSBlockEventHandler.java](dev/latvian/mods/kubejs/block/KubeJSBlockEventHandler.java) |
| `KubeJSWorldEventHandler` | 5 | `dev.latvian.mods.kubejs.level` | [KubeJSWorldEventHandler.java](dev/latvian/mods/kubejs/level/KubeJSWorldEventHandler.java) |
| `KubeJSModEventHandler` | 3 | `dev.latvian.mods.kubejs` | [KubeJSModEventHandler.java](dev/latvian/mods/kubejs/KubeJSModEventHandler.java) |
| `RegistryEventHandler` | 2 | `dev.latvian.mods.kubejs.registry` | [RegistryEventHandler.java](dev/latvian/mods/kubejs/registry/RegistryEventHandler.java) |
| `GameStagesIntegration` | 1 | `dev.latvian.mods.kubejs.integration.gamestages` | [GameStagesIntegration.java](dev/latvian/mods/kubejs/integration/gamestages/GameStagesIntegration.java) |
| `KubeJSNet` | 1 | `dev.latvian.mods.kubejs.net` | [KubeJSNet.java](dev/latvian/mods/kubejs/net/KubeJSNet.java) |
| `KubeJSREIPlugin` | 1 | `dev.latvian.mods.kubejs.integration.rei` | [KubeJSREIPlugin.java](dev/latvian/mods/kubejs/integration/rei/KubeJSREIPlugin.java) |
| `KubeJSJEIPlugin` | 1 | `dev.latvian.mods.kubejs.integration.jei` | [KubeJSJEIPlugin.java](dev/latvian/mods/kubejs/integration/jei/KubeJSJEIPlugin.java) |

## Mixin（88 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `RecipeManagerMixin` | `RecipeManager` | [RecipeManagerMixin.java](dev/latvian/mods/kubejs/core/mixin/RecipeManagerMixin.java) |
| `IItemHandlerMixin` | `IItemHandler` | [IItemHandlerMixin.java](dev/latvian/mods/kubejs/core/mixin/IItemHandlerMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](dev/latvian/mods/kubejs/core/mixin/EntityMixin.java) |
| `ItemStackHandlerMixin` | `ItemStackHandler` | [ItemStackHandlerMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemStackHandlerMixin.java) |
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](dev/latvian/mods/kubejs/core/mixin/ClientPacketListenerMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemMixin.java) |
| `KeyboardHandlerMixin` | `KeyboardHandler` | [KeyboardHandlerMixin.java](dev/latvian/mods/kubejs/core/mixin/KeyboardHandlerMixin.java) |
| `TagManagerMixin` | `TagManager` | [TagManagerMixin.java](dev/latvian/mods/kubejs/core/mixin/TagManagerMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemEntityMixin.java) |
| `ItemStackClientMixin` | `ItemStackClient` | [ItemStackClientMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemStackClientMixin.java) |
| `FallingBlockEntityMixin` | `FallingBlockEntity` | [FallingBlockEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/FallingBlockEntityMixin.java) |
| `RecipeHolderMixin` | `RecipeHolder` | [RecipeHolderMixin.java](dev/latvian/mods/kubejs/core/mixin/RecipeHolderMixin.java) |
| `RecipeInputMixin` | `RecipeInput` | [RecipeInputMixin.java](dev/latvian/mods/kubejs/core/mixin/RecipeInputMixin.java) |
| `WorldLoaderPackConfigMixin` | `WorldLoaderPackConfig` | [WorldLoaderPackConfigMixin.java](dev/latvian/mods/kubejs/core/mixin/WorldLoaderPackConfigMixin.java) |
| `ItemFrameEntityMixin` | `ItemFrameEntity` | [ItemFrameEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemFrameEntityMixin.java) |
| `LootDataTypeMixin` | `LootDataType` | [LootDataTypeMixin.java](dev/latvian/mods/kubejs/core/mixin/LootDataTypeMixin.java) |
| `CommandSourceStackMixin` | `CommandSourceStack` | [CommandSourceStackMixin.java](dev/latvian/mods/kubejs/core/mixin/CommandSourceStackMixin.java) |
| `ClickEventMixin` | `ClickEvent` | [ClickEventMixin.java](dev/latvian/mods/kubejs/core/mixin/ClickEventMixin.java) |
| `MinecraftClientMixin` | `MinecraftClient` | [MinecraftClientMixin.java](dev/latvian/mods/kubejs/core/mixin/MinecraftClientMixin.java) |
| `EntityTypeMixin` | `EntityType` | [EntityTypeMixin.java](dev/latvian/mods/kubejs/core/mixin/EntityTypeMixin.java) |
| `LevelRendererMixin` | `LevelRenderer` | [LevelRendererMixin.java](dev/latvian/mods/kubejs/core/mixin/LevelRendererMixin.java) |
| `MenuTypeMixin` | `MenuType` | [MenuTypeMixin.java](dev/latvian/mods/kubejs/core/mixin/MenuTypeMixin.java) |
| `EntityGetterMixin` | `EntityGetter` | [EntityGetterMixin.java](dev/latvian/mods/kubejs/core/mixin/EntityGetterMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/LivingEntityMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](dev/latvian/mods/kubejs/core/mixin/ItemStackMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](dev/latvian/mods/kubejs/core/mixin/GameRendererMixin.java) |
| `IBlockStateExtensionMixin` | `IBlockStateExtension` | [IBlockStateExtensionMixin.java](dev/latvian/mods/kubejs/core/mixin/IBlockStateExtensionMixin.java) |
| `BlockBehaviourMixin` | `BlockBehaviour` | [BlockBehaviourMixin.java](dev/latvian/mods/kubejs/core/mixin/BlockBehaviourMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](dev/latvian/mods/kubejs/core/mixin/BlockMixin.java) |
| `ScreenMixin` | `Screen` | [ScreenMixin.java](dev/latvian/mods/kubejs/core/mixin/ScreenMixin.java) |
| `BlockStateBaseMixin` | `BlockStateBase` | [BlockStateBaseMixin.java](dev/latvian/mods/kubejs/core/mixin/BlockStateBaseMixin.java) |
| `ClientLevelMixin` | `ClientLevel` | [ClientLevelMixin.java](dev/latvian/mods/kubejs/core/mixin/ClientLevelMixin.java) |
| `DiggerItemMixin` | `DiggerItem` | [DiggerItemMixin.java](dev/latvian/mods/kubejs/core/mixin/DiggerItemMixin.java) |
| `CreativeModeInventoryScreenMixin` | `CreativeModeInventoryScreen` | [CreativeModeInventoryScreenMixin.java](dev/latvian/mods/kubejs/core/mixin/CreativeModeInventoryScreenMixin.java) |
| `FireworkRocketEntityMixin` | `FireworkRocketEntity` | [FireworkRocketEntityMixin.java](dev/latvian/mods/kubejs/core/mixin/FireworkRocketEntityMixin.java) |
| `AbstractClientPlayerMixin` | `AbstractClientPlayer` | [AbstractClientPlayerMixin.java](dev/latvian/mods/kubejs/core/mixin/AbstractClientPlayerMixin.java) |
| `MutableDataComponentHolderMixin` | `MutableDataComponentHolder` | [MutableDataComponentHolderMixin.java](dev/latvian/mods/kubejs/core/mixin/MutableDataComponentHolderMixin.java) |
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](dev/latvian/mods/kubejs/core/mixin/MinecraftServerMixin.java) |
| `UtilMixin` | `Util` | [UtilMixin.java](dev/latvian/mods/kubejs/core/mixin/UtilMixin.java) |
| `DataComponentsMixin` | `DataComponents` | [DataComponentsMixin.java](dev/latvian/mods/kubejs/core/mixin/DataComponentsMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `WorldLoaderPackConfigMixin` | Mixin | [WorldLoaderPackConfigMixin.java](dev/latvian/mods/kubejs/core/mixin/WorldLoaderPackConfigMixin.java) |

## 按包浏览

- `dev.latvian.mods.kubejs`（9 类）
- `dev.latvian.mods.kubejs.block`（23 类）
- `dev.latvian.mods.kubejs.block.callback`（10 类）
- `dev.latvian.mods.kubejs.block.custom`（17 类）
- `dev.latvian.mods.kubejs.block.drop`（2 类）
- `dev.latvian.mods.kubejs.block.entity`（16 类）
- `dev.latvian.mods.kubejs.block.predicate`（4 类）
- `dev.latvian.mods.kubejs.block.state`（1 类）
- `dev.latvian.mods.kubejs.client`（32 类）
- `dev.latvian.mods.kubejs.client.editor`（2 类）
- `dev.latvian.mods.kubejs.client.highlight`（3 类）
- `dev.latvian.mods.kubejs.client.icon`（7 类）
- `dev.latvian.mods.kubejs.codec`（3 类）
- `dev.latvian.mods.kubejs.color`（4 类）
- `dev.latvian.mods.kubejs.command`（10 类）
- `dev.latvian.mods.kubejs.component`（5 类）
- `dev.latvian.mods.kubejs.core`（59 类）
- `dev.latvian.mods.kubejs.core.mixin`（86 类）
- `dev.latvian.mods.kubejs.core.mixin.mod`（2 类）
- `dev.latvian.mods.kubejs.entity`（12 类）
- `dev.latvian.mods.kubejs.error`（10 类）
- `dev.latvian.mods.kubejs.event`（14 类）
- `dev.latvian.mods.kubejs.fluid`（12 类）
- `dev.latvian.mods.kubejs.generator`（3 类）
- `dev.latvian.mods.kubejs.gui`（5 类）
- `dev.latvian.mods.kubejs.gui.chest`（7 类）
- `dev.latvian.mods.kubejs.holder`（4 类）
- `dev.latvian.mods.kubejs.ingredient`（6 类）
- `dev.latvian.mods.kubejs.integration.architectury`（1 类）
- `dev.latvian.mods.kubejs.integration.emi`（5 类）
- `dev.latvian.mods.kubejs.integration.gamestages`（2 类）
- `dev.latvian.mods.kubejs.integration.jei`（9 类）
- `dev.latvian.mods.kubejs.integration.rei`（12 类）
- `dev.latvian.mods.kubejs.item`（29 类）
- `dev.latvian.mods.kubejs.item.creativetab`（7 类）
- `dev.latvian.mods.kubejs.item.custom`（7 类）
- `dev.latvian.mods.kubejs.level`（8 类）
- `dev.latvian.mods.kubejs.level.ruletest`（5 类）
- `dev.latvian.mods.kubejs.misc`（12 类）
- `dev.latvian.mods.kubejs.net`（20 类）
- `dev.latvian.mods.kubejs.player`（14 类）
- `dev.latvian.mods.kubejs.plugin`（4 类）
- `dev.latvian.mods.kubejs.plugin.builtin`（2 类）
- `dev.latvian.mods.kubejs.plugin.builtin.event`（11 类）
- `dev.latvian.mods.kubejs.plugin.builtin.wrapper`（25 类）
- `dev.latvian.mods.kubejs.recipe`（18 类）
- `dev.latvian.mods.kubejs.recipe.component`（39 类）
- `dev.latvian.mods.kubejs.recipe.filter`（14 类）
- `dev.latvian.mods.kubejs.recipe.ingredientaction`（9 类）
- `dev.latvian.mods.kubejs.recipe.match`（5 类）
- `dev.latvian.mods.kubejs.recipe.schema`（16 类）
- `dev.latvian.mods.kubejs.recipe.schema.function`（11 类）
- `dev.latvian.mods.kubejs.recipe.schema.minecraft`（2 类）
- `dev.latvian.mods.kubejs.recipe.schema.postprocessing`（4 类）
- `dev.latvian.mods.kubejs.recipe.special`（5 类）
- `dev.latvian.mods.kubejs.recipe.viewer`（9 类）
- `dev.latvian.mods.kubejs.recipe.viewer.server`（17 类）
- `dev.latvian.mods.kubejs.registry`（15 类）
- `dev.latvian.mods.kubejs.script`（26 类）
- `dev.latvian.mods.kubejs.script.data`（8 类）
- `dev.latvian.mods.kubejs.server`（9 类）
- `dev.latvian.mods.kubejs.server.tag`（5 类）
- `dev.latvian.mods.kubejs.stages`（5 类）
- `dev.latvian.mods.kubejs.text.action`（10 类）
- `dev.latvian.mods.kubejs.text.tooltip`（2 类）
- `dev.latvian.mods.kubejs.typings`（2 类）
- `dev.latvian.mods.kubejs.util`（52 类）
- `dev.latvian.mods.kubejs.util.registrypredicate`（8 类）
- `dev.latvian.mods.kubejs.web`（10 类）
- `dev.latvian.mods.kubejs.web.local`（2 类）
- `dev.latvian.mods.kubejs.web.local.client`（4 类）
