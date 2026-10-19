# CraftTweaker（`crafttweaker`）

> jar：`[魔改]CraftTweaker.jar` ｜ 版本：`21.0.38` ｜ 作者：Jaredlll08, Kindlich, StanHebben and TheSilkMiner
> 规模：**1754 个类 / 308 个包 / 126391 行** ｜ 事件处理类 3 ｜ Mixin 46 ｜ 注册/管理类 130

**官方描述**：Customize your minecraft experience!

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `org.openzen.zenscript.codemodel.expression` | 82 | `Expression`、`InvalidExpression`、`VariantValueExpression` |
| `org.openzen.zenscript.parser.expression` | 41 | `ParsedExpression`、`ParsedCallArguments`、`ParsedExpressionIndex` |
| `com.blamejared.crafttweaker.natives.item.component` | 32 | `ExpandItemEnchantments`、`ExpandBlockItemStateProperties`、`ExpandItemAttributeModifiers` |
| `org.openzen.zenscript.javashared` | 29 | `JavaContext`、`JavaCompiledModule`、`JavaMappingWriter` |
| `com.blamejared.crafttweaker.natives.world` | 28 | `ExpandLevel`、`ExpandBlockContainerSingleItem`、`ExpandLevelEventConstants` |
| `com.blamejared.crafttweaker.natives.predicate` | 27 | `ExpandItemPredicate`、`ExpandEntityPredicate`、`ExpandEntityTypePredicate` |
| `live` | 25 | `SortedLiveList`、`LivePrefixedList`、`LiveConcatList` |
| `org.openzen.zenscript.codemodel.statement` | 24 | `BlockStatement`、`WhileStatement`、`VarStatement` |
| `org.openzen.zenscript.codemodel.member` | 23 | `InnerDefinitionMember`、`GetterMember`、`IteratorMember` |
| `com.blamejared.crafttweaker.natives.loot.condition` | 21 | `ExpandLootItemEntityPropertyCondition`、`ExpandLootItemBlockStatePropertyCondition`、`ExpandLootItemKilledByPlayerCondition` |
| `org.openzen.zenscript.parser.statements` | 21 | `ParsedStatementBlock`、`ParsedStatement`、`ParsedStatementSwitch` |
| `org.openzen.zenscript.lexer` | 20 | `NFA`、`LLParserTokenStream`、`WhitespaceFilteringParser` |
| `com.blamejared.crafttweaker.natives.event.entity.player` | 20 | `ExpandAttackEntityEvent`、`ExpandPlayerSleepInBedEvent`、`ExpandCriticalHitEvent` |
| `com.blamejared.crafttweaker.impl.plugin.core` | 19 | `RecipeComponentRegistrationHandler`、`RecipeHandlerRegistrationHandler`、`ScriptRunModuleConfiguratorRegistrationHandler` |
| `org.openzen.zenscript.codemodel.type` | 19 | `GlobalTypeRegistry`、`InferenceBlockingTypeParameterVisitor`、`DefinitionTypeID` |
| `org.openzen.zenscript.javabytecode.compiler` | 17 | `JavaExpressionVisitor`、`JavaWriter`、`JavaNonPushingExpressionVisitor` |
| `org.openzen.zenscript.codemodel` | 17 | `FunctionHeader`、`HighLevelDefinition`、`Modifiers` |
| `org.openzen.zenscript.codemodel.annotations` | 17 | `AnnotationProcessor`、`PreconditionAnnotationDefinition`、`NativeAnnotationDefinition` |
| `org.openzen.zenscript.parser.definitions` | 17 | `ParsedEnum`、`BaseParsedDefinition`、`ParsedAlias` |
| `com.blamejared.crafttweaker.natives.util.math` | 16 | `ExpandMatrix3f`、`ExpandBlockPos`、`ExpandAABB` |
| `org.openzen.zenscript.parser.member` | 16 | `ParsedDefinitionMember`、`ParsedImplementation`、`ParsedInnerDefinition` |
| `zsynthetic` | 16 | `ArrayHelpers`、`FunctionStringStringToVoid`、`FunctionBoolBoolToVoid` |
| `com.blamejared.crafttweaker.api.plugin` | 15 | `IScriptRunModuleConfiguratorRegistrationHandler`、`IRecipeHandlerRegistrationHandler`、`IRecipeComponentRegistrationHandler` |
| `com.blamejared.crafttweaker.api.data` | 15 | `ShortData`、`IntData`、`LongData` |
| `com.blamejared.crafttweaker.natives.event.entity.living` | 15 | `ExpandShieldBlockEvent`、`ExpandBabyEntitySpawnEvent`、`ExpandLivingDestroyBlockEvent` |
| `com.blamejared.crafttweaker.natives.entity` | 14 | `ExpandEntity`、`ExpandLivingEntity`、`ExpandEntityType` |
| `com.blamejared.crafttweaker.api.zencode.expand` | 14 | `IDataRewrites`、`ExpandBool`、`ExpandDouble` |
| `com.blamejared.crafttweaker.api.util` | 13 | `RecipeUtil`、`ItemStackUtil`、`HandleUtil` |
| `org.openzen.zenscript.codemodel.scope` | 13 | `BlockScope`、`ExpressionScope`、`FileScope` |
| `com.blamejared.crafttweaker.natives.util.valueprovider` | 13 | `ExpandSampledFloat`、`ExpandBiasedToBottomInt`、`ExpandClampedInt` |
| `com.blamejared.crafttweaker.impl.script.scriptrun` | 12 | `ScriptRunManager`、`SuspiciousAwarePathList`、`DefaultScriptRunModuleConfigurator` |
| `org.openzen.zenscript.formatter` | 12 | `ExpressionFormatter`、`FormattingUtils`、`ScriptFormattingSettings` |
| `com.blamejared.crafttweaker.api.event.bus` | 12 | `PhasedEventBus`、`IHandlerToken`、`CommonAdaptingEventBusWire` |
| `com.blamejared.crafttweaker.natives.recipe.type` | 12 | `ExpandSingleItemRecipe`、`ExpandRecipe`、`ExpandBlastingRecipe` |
| `stdlib` | 12 | `Arrays`、`Result`、`StringBuildable` |
| `com.blamejared.crafttweaker.natives.predicate.builder` | 12 | `ExpandEntityPredicateBuilder`、`ExpandItemPredicateBuilder`、`ExpandPlayerPredicateBuilder` |
| `org.openzen.zenscript.codemodel.member.ref` | 12 | `FunctionalMemberRef`、`CasterMemberRef`、`PropertyRef` |
| `org.openzen.zencode.shared` | 12 | `StringExpansion`、`CharacterEntity`、`CodePosition` |
| `org.openzen.zenscript.codemodel.definition` | 12 | `ZSPackage`、`AliasDefinition`、`EnumDefinition` |
| `org.openzen.zenscript.parser` | 12 | `ParsedFile`、`ParsedAnnotation`、`ParsedImport` |
| … | | 其余 268 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CTCommonEventHandler` | class | — | EventBusSubscriber | 11 | 128 | [CTCommonEventHandler.java](com/blamejared/crafttweaker/impl/event/CTCommonEventHandler.java) |
| `CTClientEventHandler` | class | — | EventBusSubscriber | 3 | 60 | [CTClientEventHandler.java](com/blamejared/crafttweaker/impl/event/CTClientEventHandler.java) |
| `CTModEventHandler` | class | — | EventBusSubscriber | 3 | 57 | [CTModEventHandler.java](com/blamejared/crafttweaker/impl/event/CTModEventHandler.java) |
| `RecipeHandlerRegistry` | class | IRecipeHandlerRegistry | — | 4 | 53 | [RecipeHandlerRegistry.java](com/blamejared/crafttweaker/impl/registry/recipe/RecipeHandlerRegistry.java) |
| `RecipeComponentRegistrationHandler` | class | IRecipeComponentRegistrationHandler | — | 1 | 34 | [RecipeComponentRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/RecipeComponentRegistrationHandler.java) |
| `RecipeHandlerRegistrationHandler` | class | IRecipeHandlerRegistrationHandler | — | 1 | 44 | [RecipeHandlerRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/RecipeHandlerRegistrationHandler.java) |
| `ScriptRunModuleConfiguratorRegistrationHandler` | class | IScriptRunModuleConfiguratorRegistra | — | 1 | 32 | [ScriptRunModuleConfiguratorRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/ScriptRunModuleConfiguratorRegistrationHandler.java) |
| `EventRegistrationManager` | class | — | — | 7 | 90 | [EventRegistrationManager.java](com/blamejared/crafttweaker/impl/plugin/crafttweaker/EventRegistrationManager.java) |
| `BracketParserRegistrationManager` | class | — | — | 15 | 215 | [BracketParserRegistrationManager.java](com/blamejared/crafttweaker/impl/plugin/crafttweaker/BracketParserRegistrationManager.java) |
| `AccessRecipeManager` | interface | — | Mixin | 0 | 26 | [AccessRecipeManager.java](com/blamejared/crafttweaker/mixin/common/access/recipe/AccessRecipeManager.java) |
| `MixinClientConfigurationPacketListenerImpl` | class | — | Mixin | 0 | 42 | [MixinClientConfigurationPacketListenerImpl.java](com/blamejared/crafttweaker/mixin/client/transform/multiplayer/MixinClientConfigurationPacketListenerImpl.java) |
| `IRecipeHandlerRegistry` | interface | — | — | 0 | 20 | [IRecipeHandlerRegistry.java](com/blamejared/crafttweaker/api/recipe/handler/IRecipeHandlerRegistry.java) |
| `IScriptRunModuleConfiguratorRegistrationHandler` | interface | — | — | 0 | 8 | [IScriptRunModuleConfiguratorRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IScriptRunModuleConfiguratorRegistrationHandler.java) |
| `IRecipeHandlerRegistrationHandler` | interface | — | — | 0 | 9 | [IRecipeHandlerRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IRecipeHandlerRegistrationHandler.java) |
| `IRecipeComponentRegistrationHandler` | interface | — | — | 0 | 8 | [IRecipeComponentRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IRecipeComponentRegistrationHandler.java) |
| `EventRegistrationHandler` | class | IEventRegistrationHandler | — | 1 | 32 | [EventRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/EventRegistrationHandler.java) |
| `CommandRegistrationHandler` | class | ICommandRegistrationHandler | — | 4 | 38 | [CommandRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/CommandRegistrationHandler.java) |
| `AccessLootModifierManager` | interface | — | Mixin | 0 | 18 | [AccessLootModifierManager.java](com/blamejared/crafttweaker/mixin/common/access/loot/AccessLootModifierManager.java) |
| `IEventRegistrationHandler` | interface | — | — | 0 | 9 | [IEventRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IEventRegistrationHandler.java) |
| `ICommandRegistrationHandler` | interface | — | — | 0 | 22 | [ICommandRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/ICommandRegistrationHandler.java) |
| `JavaNativeIntegrationRegistrationHandler` | class | IJavaNativeIntegrationRegistrationHa | — | 4 | 86 | [JavaNativeIntegrationRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/JavaNativeIntegrationRegistrationHandler.java) |
| `IJavaNativeIntegrationRegistrationHandler` | interface | — | — | 0 | 16 | [IJavaNativeIntegrationRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IJavaNativeIntegrationRegistrationHandler.java) |
| `ZenClassRegistrationManager` | class | — | — | 9 | 130 | [ZenClassRegistrationManager.java](com/blamejared/crafttweaker/impl/plugin/crafttweaker/ZenClassRegistrationManager.java) |
| `AccessRegistrySynchronization` | interface | — | Mixin | 0 | 17 | [AccessRegistrySynchronization.java](com/blamejared/crafttweaker/mixin/common/access/registry/AccessRegistrySynchronization.java) |
| `AccessMappedRegistry` | interface | — | Mixin | 0 | 15 | [AccessMappedRegistry.java](com/blamejared/crafttweaker/mixin/common/access/registry/AccessMappedRegistry.java) |
| `GenericRecipesManager` | class | — | Document、ZenCodeType.Name、ZenRegister | 16 | 191 | [GenericRecipesManager.java](com/blamejared/crafttweaker/api/recipe/manager/GenericRecipesManager.java) |
| `RecipeTypeBracketHandler` | class | BracketExpressionParser | ZenCodeType.Name、ZenRegister | 13 | 203 | [RecipeTypeBracketHandler.java](com/blamejared/crafttweaker/api/bracket/custom/RecipeTypeBracketHandler.java) |
| `ExpandEntity` | class | — | Document、NativeTypeRegistration、ZenRegis | 159 | 947 | [ExpandEntity.java](com/blamejared/crafttweaker/natives/entity/ExpandEntity.java) |
| `TypeMemberBuilder` | class | TypeVisitorWithContext<Void | — | 90 | 1372 | [TypeMemberBuilder.java](org/openzen/zenscript/codemodel/type/member/TypeMemberBuilder.java) |
| `JavaExpressionVisitor` | class | ExpressionVisitor<Void> | — | 104 | 3600 | [JavaExpressionVisitor.java](org/openzen/zenscript/javabytecode/compiler/JavaExpressionVisitor.java) |
| `JavaWriter` | class | — | — | 157 | 1354 | [JavaWriter.java](org/openzen/zenscript/javabytecode/compiler/JavaWriter.java) |
| `RecipePath` | class | Path | — | 28 | 506 | [RecipePath.java](com/blamejared/crafttweaker/impl/script/recipefs/RecipePath.java) |
| `RecipeFileSystemProvider` | class | FileSystemProvider | — | 30 | 258 | [RecipeFileSystemProvider.java](com/blamejared/crafttweaker/impl/script/recipefs/RecipeFileSystemProvider.java) |
| `ForwardingLogger` | class | Logger | — | 384 | 2498 | [ForwardingLogger.java](com/blamejared/crafttweaker/impl/logging/ForwardingLogger.java) |
| `SystemLogger` | class | Logger | — | 384 | 1584 | [SystemLogger.java](com/blamejared/crafttweaker/impl/logging/SystemLogger.java) |
| `ListenerRegistrationHandler` | class | IListenerRegistrationHandler | — | 3 | 51 | [ListenerRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/ListenerRegistrationHandler.java) |
| `LoadSourceRegistrationHandler` | class | IScriptLoadSourceRegistrationHandler | — | 1 | 33 | [LoadSourceRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/LoadSourceRegistrationHandler.java) |
| `LoaderRegistrationHandler` | class | ILoaderRegistrationHandler | — | 4 | 76 | [LoaderRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/LoaderRegistrationHandler.java) |
| `VillagerTradeConverterRegistrationHandler` | class | IVillagerTradeRegistrationHandler | — | 1 | 24 | [VillagerTradeConverterRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/VillagerTradeConverterRegistrationHandler.java) |
| `BracketParserRegistrationHandler` | class | IBracketParserRegistrationHandler | — | 4 | 68 | [BracketParserRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/BracketParserRegistrationHandler.java) |
| `ReplacerComponentsRegistrationHandler` | class | IReplacerComponentRegistrationHandle | — | 2 | 47 | [ReplacerComponentsRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/ReplacerComponentsRegistrationHandler.java) |
| `TaggableElementsRegistrationHandler` | class | ITaggableElementRegistrationHandler | — | 2 | 50 | [TaggableElementsRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/TaggableElementsRegistrationHandler.java) |
| `TaggableElementsRegistrationManager` | class | — | — | 4 | 66 | [TaggableElementsRegistrationManager.java](com/blamejared/crafttweaker/impl/plugin/crafttweaker/TaggableElementsRegistrationManager.java) |
| `EnumBracketParserRegistrationManager` | class | — | — | 3 | 48 | [EnumBracketParserRegistrationManager.java](com/blamejared/crafttweaker/impl/plugin/crafttweaker/EnumBracketParserRegistrationManager.java) |
| `MixinTagManager` | class | — | Mixin | 0 | 56 | [MixinTagManager.java](com/blamejared/crafttweaker/mixin/common/transform/tags/MixinTagManager.java) |
| `MixinItemAttributeModifierEvent` | class | ItemAttributeModifierBase | Mixin | 0 | 10 | [MixinItemAttributeModifierEvent.java](com/blamejared/crafttweaker/mixin/common/transform/item/attribute/MixinItemAttributeModifierEvent.java) |
| `AccessNeoForgeInternalHandler` | interface | — | Mixin | 0 | 22 | [AccessNeoForgeInternalHandler.java](com/blamejared/crafttweaker/mixin/common/access/neoforge/AccessNeoForgeInternalHandler.java) |
| `AccessTagManager` | interface | — | Mixin | 0 | 13 | [AccessTagManager.java](com/blamejared/crafttweaker/mixin/common/access/tag/AccessTagManager.java) |
| `MixinClientPacketListener` | class | — | Mixin | 1 | 30 | [MixinClientPacketListener.java](com/blamejared/crafttweaker/mixin/client/transform/multiplayer/MixinClientPacketListener.java) |
| `ILoaderRegistrationHandler` | interface | — | — | 0 | 6 | [ILoaderRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/ILoaderRegistrationHandler.java) |
| `IBracketParserRegistrationHandler` | interface | — | — | 0 | 38 | [IBracketParserRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IBracketParserRegistrationHandler.java) |
| `IReplacerComponentRegistrationHandler` | interface | — | — | 0 | 12 | [IReplacerComponentRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IReplacerComponentRegistrationHandler.java) |
| `IScriptLoadSourceRegistrationHandler` | interface | — | — | 0 | 8 | [IScriptLoadSourceRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IScriptLoadSourceRegistrationHandler.java) |
| `IListenerRegistrationHandler` | interface | — | — | 0 | 13 | [IListenerRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IListenerRegistrationHandler.java) |
| `IVillagerTradeRegistrationHandler` | interface | — | — | 0 | 10 | [IVillagerTradeRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IVillagerTradeRegistrationHandler.java) |
| `ITaggableElementRegistrationHandler` | interface | — | — | 0 | 13 | [ITaggableElementRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/ITaggableElementRegistrationHandler.java) |
| `ExpandLivingEntity` | class | — | Document、NativeTypeRegistration、ZenRegis | 98 | 637 | [ExpandLivingEntity.java](com/blamejared/crafttweaker/natives/entity/ExpandLivingEntity.java) |
| `ExpandMatrix3f` | class | — | Document、NativeTypeRegistration、ZenRegis | 285 | 1173 | [ExpandMatrix3f.java](com/blamejared/crafttweaker/natives/util/math/ExpandMatrix3f.java) |
| `ExpressionValidator` | class | ExpressionVisitor<Void> | — | 77 | 855 | [ExpressionValidator.java](org/openzen/zenscript/validator/visitors/ExpressionValidator.java) |
| `CraftTweakerNeoForge` | class | — | Mod | 1 | 22 | [CraftTweakerNeoForge.java](com/blamejared/crafttweaker/CraftTweakerNeoForge.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CTCommonEventHandler` | 10 | `com.blamejared.crafttweaker.impl.event` | [CTCommonEventHandler.java](com/blamejared/crafttweaker/impl/event/CTCommonEventHandler.java) |
| `CTClientEventHandler` | 3 | `com.blamejared.crafttweaker.impl.event` | [CTClientEventHandler.java](com/blamejared/crafttweaker/impl/event/CTClientEventHandler.java) |
| `CTModEventHandler` | 3 | `com.blamejared.crafttweaker.impl.event` | [CTModEventHandler.java](com/blamejared/crafttweaker/impl/event/CTModEventHandler.java) |

## Mixin（46 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AccessRecipeManager` | `AccessRecipeManager` | [AccessRecipeManager.java](com/blamejared/crafttweaker/mixin/common/access/recipe/AccessRecipeManager.java) |
| `MixinClientConfigurationPacketListenerImpl` | `ClientConfigurationPacketListenerImpl` | [MixinClientConfigurationPacketListenerImpl.java](com/blamejared/crafttweaker/mixin/client/transform/multiplayer/MixinClientConfigurationPacketListenerImpl.java) |
| `AccessLootModifierManager` | `AccessLootModifierManager` | [AccessLootModifierManager.java](com/blamejared/crafttweaker/mixin/common/access/loot/AccessLootModifierManager.java) |
| `AccessRegistrySynchronization` | `AccessRegistrySynchronization` | [AccessRegistrySynchronization.java](com/blamejared/crafttweaker/mixin/common/access/registry/AccessRegistrySynchronization.java) |
| `AccessMappedRegistry` | `AccessMappedRegistry` | [AccessMappedRegistry.java](com/blamejared/crafttweaker/mixin/common/access/registry/AccessMappedRegistry.java) |
| `MixinTagManager` | `TagManager` | [MixinTagManager.java](com/blamejared/crafttweaker/mixin/common/transform/tags/MixinTagManager.java) |
| `MixinItemAttributeModifierEvent` | `ItemAttributeModifierEvent` | [MixinItemAttributeModifierEvent.java](com/blamejared/crafttweaker/mixin/common/transform/item/attribute/MixinItemAttributeModifierEvent.java) |
| `AccessNeoForgeInternalHandler` | `AccessNeoForgeInternalHandler` | [AccessNeoForgeInternalHandler.java](com/blamejared/crafttweaker/mixin/common/access/neoforge/AccessNeoForgeInternalHandler.java) |
| `AccessTagManager` | `AccessTagManager` | [AccessTagManager.java](com/blamejared/crafttweaker/mixin/common/access/tag/AccessTagManager.java) |
| `MixinClientPacketListener` | `ClientPacketListener` | [MixinClientPacketListener.java](com/blamejared/crafttweaker/mixin/client/transform/multiplayer/MixinClientPacketListener.java) |
| `AccessFallingBlockEntity` | `AccessFallingBlockEntity` | [AccessFallingBlockEntity.java](com/blamejared/crafttweaker/mixin/common/access/entity/AccessFallingBlockEntity.java) |
| `AccessSmithingTransformRecipe` | `AccessSmithingTransformRecipe` | [AccessSmithingTransformRecipe.java](com/blamejared/crafttweaker/mixin/common/access/recipe/AccessSmithingTransformRecipe.java) |
| `AccessSmithingTrimRecipe` | `AccessSmithingTrimRecipe` | [AccessSmithingTrimRecipe.java](com/blamejared/crafttweaker/mixin/common/access/recipe/AccessSmithingTrimRecipe.java) |
| `AccessLootContextParamSets` | `AccessLootContextParamSets` | [AccessLootContextParamSets.java](com/blamejared/crafttweaker/mixin/AccessLootContextParamSets.java) |
| `MixinLootTable` | `LootTable` | [MixinLootTable.java](com/blamejared/crafttweaker/mixin/common/transform/loot/MixinLootTable.java) |
| `MixinLootContext` | `LootContext` | [MixinLootContext.java](com/blamejared/crafttweaker/mixin/common/transform/loot/MixinLootContext.java) |
| `AccessLootContextBuilder` | `AccessLootContextBuilder` | [AccessLootContextBuilder.java](com/blamejared/crafttweaker/mixin/common/access/loot/AccessLootContextBuilder.java) |
| `AccessLootTable` | `AccessLootTable` | [AccessLootTable.java](com/blamejared/crafttweaker/mixin/common/access/loot/AccessLootTable.java) |
| `AccessLootContext` | `AccessLootContext` | [AccessLootContext.java](com/blamejared/crafttweaker/mixin/common/access/loot/AccessLootContext.java) |
| `AccessTagNetworkSerialization` | `AccessTagNetworkSerialization` | [AccessTagNetworkSerialization.java](com/blamejared/crafttweaker/mixin/common/access/tag/AccessTagNetworkSerialization.java) |
| `AccessAbstractCauldronBlock` | `AccessAbstractCauldronBlock` | [AccessAbstractCauldronBlock.java](com/blamejared/crafttweaker/mixin/common/access/block/AccessAbstractCauldronBlock.java) |
| `AccessBlockBehaviour` | `AccessBlockBehaviour` | [AccessBlockBehaviour.java](com/blamejared/crafttweaker/mixin/common/access/block/AccessBlockBehaviour.java) |
| `AccessBlockStateBase` | `AccessBlockStateBase` | [AccessBlockStateBase.java](com/blamejared/crafttweaker/mixin/common/access/block/AccessBlockStateBase.java) |
| `AccessItem` | `AccessItem` | [AccessItem.java](com/blamejared/crafttweaker/mixin/common/access/item/AccessItem.java) |
| `AccessEnchantedItemForEmeralds` | `AccessEnchantedItemForEmeralds` | [AccessEnchantedItemForEmeralds.java](com/blamejared/crafttweaker/mixin/common/access/villager/AccessEnchantedItemForEmeralds.java) |
| `AccessItemsForEmeralds` | `AccessItemsForEmeralds` | [AccessItemsForEmeralds.java](com/blamejared/crafttweaker/mixin/common/access/villager/AccessItemsForEmeralds.java) |
| `AccessItemsAndEmeraldsToItems` | `AccessItemsAndEmeraldsToItems` | [AccessItemsAndEmeraldsToItems.java](com/blamejared/crafttweaker/mixin/common/access/villager/AccessItemsAndEmeraldsToItems.java) |
| `AccessEmeraldForItems` | `AccessEmeraldForItems` | [AccessEmeraldForItems.java](com/blamejared/crafttweaker/mixin/common/access/villager/AccessEmeraldForItems.java) |
| `AccessTippedArrowForItemsAndEmeralds` | `AccessTippedArrowForItemsAndEmeralds` | [AccessTippedArrowForItemsAndEmeralds.java](com/blamejared/crafttweaker/mixin/common/access/villager/AccessTippedArrowForItemsAndEmeralds.java) |
| `AccessDataMapLoaderResult` | `AccessDataMapLoaderResult` | [AccessDataMapLoaderResult.java](com/blamejared/crafttweaker/mixin/common/access/neoforge/AccessDataMapLoaderResult.java) |
| `AccessDataMapLoader` | `AccessDataMapLoader` | [AccessDataMapLoader.java](com/blamejared/crafttweaker/mixin/common/access/neoforge/AccessDataMapLoader.java) |
| `MixinIngredient` | `Ingredient` | [MixinIngredient.java](com/blamejared/crafttweaker/mixin/common/transform/item/MixinIngredient.java) |
| `MixinIngredientTagValue` | `IngredientTagValue` | [MixinIngredientTagValue.java](com/blamejared/crafttweaker/mixin/common/transform/item/MixinIngredientTagValue.java) |
| `MixinServerLevel` | `ServerLevel` | [MixinServerLevel.java](com/blamejared/crafttweaker/mixin/common/transform/world/level/MixinServerLevel.java) |
| `MixinBasicTrade` | `BasicTrade` | [MixinBasicTrade.java](com/blamejared/crafttweaker/mixin/common/transform/villager/MixinBasicTrade.java) |
| `AccessFakePlayerFactory` | `AccessFakePlayerFactory` | [AccessFakePlayerFactory.java](com/blamejared/crafttweaker/mixin/common/access/entity/AccessFakePlayerFactory.java) |
| `AccessLightningBolt` | `AccessLightningBolt` | [AccessLightningBolt.java](com/blamejared/crafttweaker/mixin/common/access/entity/AccessLightningBolt.java) |
| `AccessPotionBrewing` | `AccessPotionBrewing` | [AccessPotionBrewing.java](com/blamejared/crafttweaker/mixin/common/access/brewing/AccessPotionBrewing.java) |
| `AccessReloadableServerResources` | `AccessReloadableServerResources` | [AccessReloadableServerResources.java](com/blamejared/crafttweaker/mixin/common/access/server/AccessReloadableServerResources.java) |
| `AccessMinecraftServer` | `AccessMinecraftServer` | [AccessMinecraftServer.java](com/blamejared/crafttweaker/mixin/common/access/server/AccessMinecraftServer.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ScriptRunModuleConfiguratorRegistrationHandler` | — | [ScriptRunModuleConfiguratorRegistrationHandler.java](com/blamejared/crafttweaker/impl/plugin/core/ScriptRunModuleConfiguratorRegistrationHandler.java) |
| `MixinClientConfigurationPacketListenerImpl` | Mixin | [MixinClientConfigurationPacketListenerImpl.java](com/blamejared/crafttweaker/mixin/client/transform/multiplayer/MixinClientConfigurationPacketListenerImpl.java) |
| `IScriptRunModuleConfiguratorRegistrationHandler` | — | [IScriptRunModuleConfiguratorRegistrationHandler.java](com/blamejared/crafttweaker/api/plugin/IScriptRunModuleConfiguratorRegistrationHandler.java) |
| `ScriptRunModuleConfiguratorRegistry` | — | [ScriptRunModuleConfiguratorRegistry.java](com/blamejared/crafttweaker/impl/registry/ScriptRunModuleConfiguratorRegistry.java) |
| `DefaultScriptRunModuleConfigurator` | — | [DefaultScriptRunModuleConfigurator.java](com/blamejared/crafttweaker/impl/script/scriptrun/DefaultScriptRunModuleConfigurator.java) |
| `ScriptRunConfiguration` | — | [ScriptRunConfiguration.java](com/blamejared/crafttweaker/api/zencode/scriptrun/ScriptRunConfiguration.java) |
| `ScriptDiscoveryConfiguration` | — | [ScriptDiscoveryConfiguration.java](com/blamejared/crafttweaker/api/zencode/scriptrun/ScriptDiscoveryConfiguration.java) |
| `IScriptRunModuleConfigurator` | — | [IScriptRunModuleConfigurator.java](com/blamejared/crafttweaker/api/zencode/scriptrun/IScriptRunModuleConfigurator.java) |

## 按包浏览

- `com.blamejared.crafttweaker`（3 类）
- `com.blamejared.crafttweaker.api`（3 类）
- `com.blamejared.crafttweaker.api.action.base`（3 类）
- `com.blamejared.crafttweaker.api.action.block`（1 类）
- `com.blamejared.crafttweaker.api.action.entity`（1 类）
- `com.blamejared.crafttweaker.api.action.event`（1 类）
- `com.blamejared.crafttweaker.api.action.internal`（1 类）
- `com.blamejared.crafttweaker.api.action.item`（3 类）
- `com.blamejared.crafttweaker.api.action.item.tooltip`（7 类）
- `com.blamejared.crafttweaker.api.action.loot`（3 类）
- `com.blamejared.crafttweaker.api.action.misc`（2 类）
- `com.blamejared.crafttweaker.api.action.network`（1 类）
- `com.blamejared.crafttweaker.api.action.recipe`（9 类）
- `com.blamejared.crafttweaker.api.action.recipe.generic`（8 类）
- `com.blamejared.crafttweaker.api.action.recipe.replace`（2 类）
- `com.blamejared.crafttweaker.api.action.tag`（1 类）
- `com.blamejared.crafttweaker.api.action.tag.known`（6 类）
- `com.blamejared.crafttweaker.api.action.tag.unknown`（6 类）
- `com.blamejared.crafttweaker.api.action.villager`（5 类）
- `com.blamejared.crafttweaker.api.annotation`（6 类）
- `com.blamejared.crafttweaker.api.block`（1 类）
- `com.blamejared.crafttweaker.api.bracket`（8 类）
- `com.blamejared.crafttweaker.api.bracket.custom`（5 类）
- `com.blamejared.crafttweaker.api.capability`（3 类）
- `com.blamejared.crafttweaker.api.command`（1 类）
- `com.blamejared.crafttweaker.api.command.argument`（2 类）
- `com.blamejared.crafttweaker.api.command.type`（1 类）
- `com.blamejared.crafttweaker.api.component`（1 类）
- `com.blamejared.crafttweaker.api.data`（15 类）
- `com.blamejared.crafttweaker.api.data.converter`（2 类）
- `com.blamejared.crafttweaker.api.data.converter.tag`（2 类）
- `com.blamejared.crafttweaker.api.data.op`（9 类）
- `com.blamejared.crafttweaker.api.data.visitor`（4 类）
- `com.blamejared.crafttweaker.api.entity`（3 类）
- `com.blamejared.crafttweaker.api.event`（5 类）
- `com.blamejared.crafttweaker.api.event.bus`（12 类）
- `com.blamejared.crafttweaker.api.fluid`（5 类）
- `com.blamejared.crafttweaker.api.game`（1 类）
- `com.blamejared.crafttweaker.api.ingredient`（5 类）
- `com.blamejared.crafttweaker.api.ingredient.condition`（3 类）
- `com.blamejared.crafttweaker.api.ingredient.condition.serializer`（5 类）
- `com.blamejared.crafttweaker.api.ingredient.condition.type`（5 类）
- `com.blamejared.crafttweaker.api.ingredient.transformer`（3 类）
- `com.blamejared.crafttweaker.api.ingredient.transformer.serializer`（4 类）
- `com.blamejared.crafttweaker.api.ingredient.transformer.type`（4 类）
- `com.blamejared.crafttweaker.api.ingredient.type`（7 类）
- `com.blamejared.crafttweaker.api.ingredient.vanilla`（2 类）
- `com.blamejared.crafttweaker.api.ingredient.vanilla.serializer`（5 类）
- `com.blamejared.crafttweaker.api.ingredient.vanilla.type`（5 类）
- `com.blamejared.crafttweaker.api.item`（6 类）
- `com.blamejared.crafttweaker.api.item.attribute`（1 类）
- `com.blamejared.crafttweaker.api.item.tooltip`（1 类）
- `com.blamejared.crafttweaker.api.level`（2 类）
- `com.blamejared.crafttweaker.api.logging`（2 类）
- `com.blamejared.crafttweaker.api.loot`（1 类）
- `com.blamejared.crafttweaker.api.loot.condition`（1 类）
- `com.blamejared.crafttweaker.api.loot.modifier`（3 类）
- `com.blamejared.crafttweaker.api.loot.table`（1 类）
- `com.blamejared.crafttweaker.api.misc`（3 类）
- `com.blamejared.crafttweaker.api.mod`（3 类）
- `com.blamejared.crafttweaker.api.natives`（4 类）
- `com.blamejared.crafttweaker.api.network`（3 类）
- `com.blamejared.crafttweaker.api.plugin`（15 类）
- `com.blamejared.crafttweaker.api.recipe`（2 类）
- `com.blamejared.crafttweaker.api.recipe.component`（7 类）
- `com.blamejared.crafttweaker.api.recipe.fun`（3 类）
- `com.blamejared.crafttweaker.api.recipe.handler`（2 类）
- `com.blamejared.crafttweaker.api.recipe.handler.helper`（1 类）
- `com.blamejared.crafttweaker.api.recipe.manager`（9 类）
- `com.blamejared.crafttweaker.api.recipe.manager.base`（2 类）
- `com.blamejared.crafttweaker.api.recipe.replacement`（8 类）
- `com.blamejared.crafttweaker.api.recipe.replacement.type`（6 类）
- `com.blamejared.crafttweaker.api.recipe.serializer`（2 类）
- `com.blamejared.crafttweaker.api.recipe.type`（2 类）
- `com.blamejared.crafttweaker.api.registry`（1 类）
- `com.blamejared.crafttweaker.api.tag`（3 类）
- `com.blamejared.crafttweaker.api.tag.expand`（8 类）
- `com.blamejared.crafttweaker.api.tag.manager`（2 类）
- `com.blamejared.crafttweaker.api.tag.manager.factory`（1 类）
- `com.blamejared.crafttweaker.api.tag.manager.type`（3 类）
- `com.blamejared.crafttweaker.api.tag.type`（2 类）
- `com.blamejared.crafttweaker.api.util`（13 类）
- `com.blamejared.crafttweaker.api.util.random`（1 类）
- `com.blamejared.crafttweaker.api.util.sequence`（5 类）
- `com.blamejared.crafttweaker.api.util.sequence.task`（1 类）
- `com.blamejared.crafttweaker.api.util.sequence.task.type`（3 类）
- `com.blamejared.crafttweaker.api.villager`（3 类）
- `com.blamejared.crafttweaker.api.villager.trade.type`（3 类）
- `com.blamejared.crafttweaker.api.zencode`（6 类）
- `com.blamejared.crafttweaker.api.zencode.expand`（14 类）
- `com.blamejared.crafttweaker.api.zencode.scriptrun`（8 类）
- `com.blamejared.crafttweaker.api.zencode.util`（2 类）
- `com.blamejared.crafttweaker.gametest`（1 类）
- `com.blamejared.crafttweaker.gametest.logging.appender`（1 类）
- `com.blamejared.crafttweaker.gametest.util`（2 类）
- `com.blamejared.crafttweaker.impl`（1 类）
- `com.blamejared.crafttweaker.impl.command`（2 类）
- `com.blamejared.crafttweaker.impl.command.type`（7 类）
- `com.blamejared.crafttweaker.impl.command.type.conflict`（3 类）
- `com.blamejared.crafttweaker.impl.command.type.script`（1 类）
- `com.blamejared.crafttweaker.impl.datamap`（1 类）
- `com.blamejared.crafttweaker.impl.event`（5 类）
- `com.blamejared.crafttweaker.impl.helper`（4 类）
- `com.blamejared.crafttweaker.impl.logging`（6 类）
- `com.blamejared.crafttweaker.impl.loot`（10 类）
- `com.blamejared.crafttweaker.impl.loot.condition`（2 类）
- `com.blamejared.crafttweaker.impl.mod`（1 类）
- `com.blamejared.crafttweaker.impl.network.packet`（5 类）
- `com.blamejared.crafttweaker.impl.plugin`（1 类）
- `com.blamejared.crafttweaker.impl.plugin.core`（19 类）
- `com.blamejared.crafttweaker.impl.plugin.crafttweaker`（11 类）
- `com.blamejared.crafttweaker.impl.preprocessor`（12 类）
- `com.blamejared.crafttweaker.impl.preprocessor.onlyif`（6 类）
- `com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter`（7 类）
- `com.blamejared.crafttweaker.impl.recipe.handler.helper`（1 类）
- `com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker`（2 类）
- `com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla`（6 类）
- `com.blamejared.crafttweaker.impl.recipe.replacement`（3 类）
- `com.blamejared.crafttweaker.impl.registry`（7 类）
- `com.blamejared.crafttweaker.impl.registry.natives`（4 类）
- `com.blamejared.crafttweaker.impl.registry.recipe`（3 类）
- `com.blamejared.crafttweaker.impl.registry.zencode`（7 类）
- `com.blamejared.crafttweaker.impl.script`（5 类）
- `com.blamejared.crafttweaker.impl.script.recipefs`（10 类）
- `com.blamejared.crafttweaker.impl.script.scriptrun`（12 类）
- `com.blamejared.crafttweaker.impl.script.scriptrun.natives`（6 类）
- `com.blamejared.crafttweaker.impl.script.scriptrun.runner`（8 类）
- `com.blamejared.crafttweaker.impl.service`（1 类）
- `com.blamejared.crafttweaker.mixin`（1 类）
- `com.blamejared.crafttweaker.mixin.client.transform.multiplayer`（2 类）
- `com.blamejared.crafttweaker.mixin.common.access.block`（3 类）
- `com.blamejared.crafttweaker.mixin.common.access.brewing`（1 类）
- `com.blamejared.crafttweaker.mixin.common.access.entity`（3 类）
- `com.blamejared.crafttweaker.mixin.common.access.item`（2 类）
- `com.blamejared.crafttweaker.mixin.common.access.level`（1 类）
- `com.blamejared.crafttweaker.mixin.common.access.level.damage`（1 类）
- `com.blamejared.crafttweaker.mixin.common.access.loot`（4 类）
- `com.blamejared.crafttweaker.mixin.common.access.neoforge`（3 类）
- `com.blamejared.crafttweaker.mixin.common.access.predicate`（1 类）
- `com.blamejared.crafttweaker.mixin.common.access.recipe`（3 类）
- `com.blamejared.crafttweaker.mixin.common.access.registry`（2 类）
- `com.blamejared.crafttweaker.mixin.common.access.server`（2 类）
- `com.blamejared.crafttweaker.mixin.common.access.tag`（2 类）
- `com.blamejared.crafttweaker.mixin.common.access.villager`（7 类）
- `com.blamejared.crafttweaker.mixin.common.transform.item`（2 类）
- `com.blamejared.crafttweaker.mixin.common.transform.item.attribute`（1 类）
- `com.blamejared.crafttweaker.mixin.common.transform.loot`（2 类）
- `com.blamejared.crafttweaker.mixin.common.transform.tags`（1 类）
- `com.blamejared.crafttweaker.mixin.common.transform.villager`（1 类）
- `com.blamejared.crafttweaker.mixin.common.transform.world.level`（1 类）
- `com.blamejared.crafttweaker.natives.advancement`（11 类）
- `com.blamejared.crafttweaker.natives.attachment`（3 类）
- `com.blamejared.crafttweaker.natives.block`（2 类）
- `com.blamejared.crafttweaker.natives.block.entity`（4 类）
- `com.blamejared.crafttweaker.natives.block.entity.type`（2 类）
- `com.blamejared.crafttweaker.natives.block.material`（1 类）
- `com.blamejared.crafttweaker.natives.block.type.cauldron`（4 类）
- `com.blamejared.crafttweaker.natives.block.type.falling`（2 类）
- `com.blamejared.crafttweaker.natives.block.type.piston`（2 类）
- `com.blamejared.crafttweaker.natives.capability`（9 类）
- `com.blamejared.crafttweaker.natives.command`（1 类）
- `com.blamejared.crafttweaker.natives.component`（7 类）
- `com.blamejared.crafttweaker.natives.entity`（14 类）
- `com.blamejared.crafttweaker.natives.entity.attribute`（4 类）
- `com.blamejared.crafttweaker.natives.entity.effect`（5 类）
- `com.blamejared.crafttweaker.natives.entity.equipment`（3 类）
- `com.blamejared.crafttweaker.natives.entity.type.animal`（2 类）
- `com.blamejared.crafttweaker.natives.entity.type.item`（1 类）
- `com.blamejared.crafttweaker.natives.entity.type.misc`（6 类）
- `com.blamejared.crafttweaker.natives.entity.type.player`（5 类）
- `com.blamejared.crafttweaker.natives.entity.type.projectile`（8 类）
- `com.blamejared.crafttweaker.natives.entity.type.projectile.arrow`（2 类）
- `com.blamejared.crafttweaker.natives.entity.type.projectile.fireball`（3 类）
- `com.blamejared.crafttweaker.natives.entity.type.villager`（2 类）
- `com.blamejared.crafttweaker.natives.event`（5 类）
- `com.blamejared.crafttweaker.natives.event.anvil`（2 类）
- `com.blamejared.crafttweaker.natives.event.block`（7 类）
- `com.blamejared.crafttweaker.natives.event.block.crop`（1 类）
- `com.blamejared.crafttweaker.natives.event.block.fluid`（2 类）
- `com.blamejared.crafttweaker.natives.event.block.piston`（2 类）
- `com.blamejared.crafttweaker.natives.event.brewing`（2 类）
- `com.blamejared.crafttweaker.natives.event.enchantment`（1 类）
- `com.blamejared.crafttweaker.natives.event.entity`（8 类）
- `com.blamejared.crafttweaker.natives.event.entity.advancement`（4 类）
- `com.blamejared.crafttweaker.natives.event.entity.arrow`（2 类）
- `com.blamejared.crafttweaker.natives.event.entity.conversion`（1 类）
- `com.blamejared.crafttweaker.natives.event.entity.living`（15 类）
- `com.blamejared.crafttweaker.natives.event.entity.living.effect`（5 类）
- `com.blamejared.crafttweaker.natives.event.entity.living.spawn`（3 类）
- `com.blamejared.crafttweaker.natives.event.entity.living.target`（3 类）
- `com.blamejared.crafttweaker.natives.event.entity.player`（20 类）
- `com.blamejared.crafttweaker.natives.event.entity.teleport`（6 类）
- `com.blamejared.crafttweaker.natives.event.entity.use`（5 类）
- `com.blamejared.crafttweaker.natives.event.grindstone`（1 类）
- `com.blamejared.crafttweaker.natives.event.interact`（8 类）
- `com.blamejared.crafttweaker.natives.event.item`（9 类）
- `com.blamejared.crafttweaker.natives.event.level`（2 类）
- `com.blamejared.crafttweaker.natives.event.tick`（5 类）
- `com.blamejared.crafttweaker.natives.event.xp`（5 类）
- `com.blamejared.crafttweaker.natives.fluid`（1 类）
- `com.blamejared.crafttweaker.natives.food`（2 类）
- `com.blamejared.crafttweaker.natives.game`（3 类）
- `com.blamejared.crafttweaker.natives.ingredient`（6 类）
- `com.blamejared.crafttweaker.natives.item`（6 类）
- `com.blamejared.crafttweaker.natives.item.alchemy`（1 类）
- `com.blamejared.crafttweaker.natives.item.armortrim`（3 类）
- `com.blamejared.crafttweaker.natives.item.component`（32 类）
- `com.blamejared.crafttweaker.natives.item.enchantment`（3 类）
- `com.blamejared.crafttweaker.natives.item.enchantment.provider`（1 类）
- `com.blamejared.crafttweaker.natives.item.enchantment.provider.type`（3 类）
- `com.blamejared.crafttweaker.natives.item.property`（1 类）
- `com.blamejared.crafttweaker.natives.item.type.armor`（4 类）
- `com.blamejared.crafttweaker.natives.item.type.block`（1 类）
- `com.blamejared.crafttweaker.natives.item.type.projectileweapon`（3 类）
- `com.blamejared.crafttweaker.natives.item.type.sword`（1 类）
- `com.blamejared.crafttweaker.natives.item.type.tiered`（3 类）
- `com.blamejared.crafttweaker.natives.level`（1 类）
- `com.blamejared.crafttweaker.natives.loot`（5 类）
- `com.blamejared.crafttweaker.natives.loot.condition`（21 类）
- `com.blamejared.crafttweaker.natives.loot.condition.builder`（7 类）
- `com.blamejared.crafttweaker.natives.loot.modifier`（4 类）
- `com.blamejared.crafttweaker.natives.loot.param`（7 类）
- `com.blamejared.crafttweaker.natives.loot.table`（1 类）
- `com.blamejared.crafttweaker.natives.predicate`（27 类）
- `com.blamejared.crafttweaker.natives.predicate.builder`（12 类）
- `com.blamejared.crafttweaker.natives.recipe`（1 类）
- `com.blamejared.crafttweaker.natives.recipe.category`（2 类）
- `com.blamejared.crafttweaker.natives.recipe.input`（1 类）
- `com.blamejared.crafttweaker.natives.recipe.input.type`（3 类）
- `com.blamejared.crafttweaker.natives.recipe.type`（12 类）
- `com.blamejared.crafttweaker.natives.resource`（2 类）
- `com.blamejared.crafttweaker.natives.server`（2 类）
- `com.blamejared.crafttweaker.natives.sound`（3 类）
- `com.blamejared.crafttweaker.natives.text`（7 类）
- `com.blamejared.crafttweaker.natives.text.content`（1 类）
- `com.blamejared.crafttweaker.natives.text.content.type`（7 类）
- `com.blamejared.crafttweaker.natives.tooltip`（3 类）
- `com.blamejared.crafttweaker.natives.util`（11 类）
- `com.blamejared.crafttweaker.natives.util.collection`（1 类）
- `com.blamejared.crafttweaker.natives.util.direction`（4 类）
- `com.blamejared.crafttweaker.natives.util.math`（16 类）
- `com.blamejared.crafttweaker.natives.util.valueprovider`（13 类）
- `com.blamejared.crafttweaker.natives.villager`（5 类）
- `com.blamejared.crafttweaker.natives.villager.trade`（1 类）
- `com.blamejared.crafttweaker.natives.villager.trade.type`（10 类）
- `com.blamejared.crafttweaker.natives.world`（28 类）
- `com.blamejared.crafttweaker.natives.world.biome`（1 类）
- `com.blamejared.crafttweaker.natives.world.clip`（3 类）
- `com.blamejared.crafttweaker.natives.world.damage`（8 类）
- `com.blamejared.crafttweaker.natives.world.data`（3 类）
- `com.blamejared.crafttweaker.natives.world.map`（1 类）
- `com.blamejared.crafttweaker.platform`（4 类）
- `com.blamejared.crafttweaker.platform.client`（1 类）
- `com.blamejared.crafttweaker.platform.event`（1 类）
- `com.blamejared.crafttweaker.platform.helper`（3 类）
- `com.blamejared.crafttweaker.platform.helper.inventory`（2 类）
- `com.blamejared.crafttweaker.platform.network`（1 类）
- `com.blamejared.crafttweaker.platform.registry`（2 类）
- `com.blamejared.crafttweaker.platform.services`（7 类）
- `com.blamejared.crafttweaker.platform.sides`（1 类）
- `com.blamejared.crafttweaker_annotations.annotations`（7 类）
- `compactio`（4 类）
- `listeners`（3 类）
- `live`（25 类）
- `org.openzen.zencode.java`（4 类）
- `org.openzen.zencode.java.logger`（2 类）
- `org.openzen.zencode.java.module`（3 类）
- `org.openzen.zencode.java.module.converters`（10 类）
- `org.openzen.zencode.shared`（12 类）
- `org.openzen.zencode.shared.logging`（3 类）
- `org.openzen.zenscript.codemodel`（17 类）
- `org.openzen.zenscript.codemodel.annotations`（17 类）
- `org.openzen.zenscript.codemodel.context`（9 类）
- `org.openzen.zenscript.codemodel.definition`（12 类）
- `org.openzen.zenscript.codemodel.expression`（82 类）
- `org.openzen.zenscript.codemodel.expression.switchvalue`（8 类）
- `org.openzen.zenscript.codemodel.generic`（6 类）
- `org.openzen.zenscript.codemodel.member`（23 类）
- `org.openzen.zenscript.codemodel.member.ref`（12 类）
- `org.openzen.zenscript.codemodel.partial`（8 类）
- `org.openzen.zenscript.codemodel.scope`（13 类）
- `org.openzen.zenscript.codemodel.serialization`（5 类）
- `org.openzen.zenscript.codemodel.statement`（24 类）
- `org.openzen.zenscript.codemodel.type`（19 类）
- `org.openzen.zenscript.codemodel.type.member`（8 类）
- `org.openzen.zenscript.formatter`（12 类）
- `org.openzen.zenscript.formattershared`（8 类）
- `org.openzen.zenscript.javabytecode`（6 类）
- `org.openzen.zenscript.javabytecode.compiler`（17 类）
- `org.openzen.zenscript.javabytecode.compiler.definitions`（4 类）
- `org.openzen.zenscript.javashared`（29 类）
- `org.openzen.zenscript.javashared.expressions`（1 类）
- `org.openzen.zenscript.javashared.prepare`（4 类）
- `org.openzen.zenscript.javashared.types`（1 类）
- `org.openzen.zenscript.lexer`（20 类）
- `org.openzen.zenscript.parser`（12 类）
- `org.openzen.zenscript.parser.definitions`（17 类）
- `org.openzen.zenscript.parser.expression`（41 类）
- `org.openzen.zenscript.parser.logger`（1 类）
- `org.openzen.zenscript.parser.member`（16 类）
- `org.openzen.zenscript.parser.statements`（21 类）
- `org.openzen.zenscript.parser.type`（9 类）
- `org.openzen.zenscript.validator`（4 类）
- `org.openzen.zenscript.validator.analysis`（2 类）
- `org.openzen.zenscript.validator.logger`（2 类）
- `org.openzen.zenscript.validator.visitors`（8 类）
- `stdlib`（12 类）
- `zsynthetic`（16 类）
