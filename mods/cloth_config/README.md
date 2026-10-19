# Cloth Config v15 API（`cloth_config`）

> jar：`[前置]cloth-config.jar` ｜ 版本：`15.0.140` ｜ 作者：shedaniel
> 规模：**332 个类 / 51 个包 / 40122 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 7

**依赖**：`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `me.shedaniel.clothconfig2.gui.entries` | 30 | `DropdownBoxEntry`、`BaseListEntry`、`SubCategoryListEntry` |
| `me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml` | 30 | `Toml`、`Results`、`TomlWriter` |
| `me.shedaniel.clothconfig2.impl.builders` | 27 | `DropdownMenuBuilder`、`SubCategoryBuilder`、`FloatListBuilder` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.tokens` | 24 | `BlockSequenceStartToken`、`BlockMappingStartToken`、`BlockEntryToken` |
| `me.shedaniel.clothconfig2.api` | 23 | `AbstractConfigEntry`、`ConfigScreen`、`TabbedConfigScreen` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.events` | 15 | `CollectionStartEvent`、`ScalarEvent`、`Event` |
| `me.shedaniel.clothconfig2.api.animator` | 13 | `RecordValueAnimatorArgs`、`RecordValueAnimator`、`NumberAnimator` |
| `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.impl` | 11 | `MarshallerImpl`、`POJODeserializer`、`ElementParserContext` |
| `me.shedaniel.clothconfig2.impl` | 9 | `ConfigBuilderImpl`、`ConfigEntryBuilderImpl`、`ScissorsHandlerImpl` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.nodes` | 9 | `Node`、`CollectionNode`、`Tag` |
| `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson` | 8 | `JsonArray`、`JsonObject`、`Jankson` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.constructor` | 8 | `BaseConstructor`、`SafeConstructor`、`Constructor` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.introspector` | 8 | `PropertyUtils`、`PropertySubstitute`、`Property` |
| `me.shedaniel.math` | 7 | `Rectangle`、`FloatingRectangle`、`Color` |
| `me.shedaniel.autoconfig.serializer` | 7 | `GsonConfigSerializer`、`ConfigSerializer`、`Toml4jConfigSerializer` |
| `me.shedaniel.clothconfig2.gui` | 6 | `GlobalizedClothConfigScreen`、`AbstractConfigScreen`、`ClothConfigScreen` |
| `me.shedaniel.clothconfig2.gui.widget` | 6 | `DynamicEntryListWidget`、`DynamicElementListWidget`、`DynamicSmoothScrollingEntryListWidget` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.scanner` | 5 | `ScannerImpl`、`SimpleKey`、`Scanner` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.emitter` | 5 | `Emitter`、`ScalarAnalysis`、`EmitterState` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.parser` | 5 | `ParserImpl`、`Production`、`VersionTagsTuple` |
| `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation` | 5 | `Serializer`、`Deserializer`、`SerializedName` |
| `me.shedaniel.autoconfig` | 4 | `ConfigManager`、`ConfigData`、`ConfigHolder` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml` | 4 | `DumperOptions`、`Yaml`、`TypeDescription` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.representer` | 4 | `SafeRepresenter`、`BaseRepresenter`、`Representer` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.error` | 4 | `Mark`、`MarkedYAMLException`、`YAMLException` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.util` | 4 | `ArrayUtils`、`ArrayStack`、`UriEncoder` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.serializer` | 4 | `Serializer`、`NumberAnchorGenerator`、`SerializerException` |
| `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.api` | 4 | `Marshaller`、`SyntaxError`、`DeserializerFunction` |
| `me.shedaniel.autoconfig.gui.registry` | 3 | `GuiRegistry`、`DefaultGuiRegistryAccess`、`ComposedGuiRegistryAccess` |
| `me.shedaniel.autoconfig.gui` | 3 | `ConfigScreenProvider`、`DefaultGuiProviders`、`DefaultGuiTransformers` |
| `me.shedaniel.autoconfig.gui.registry.api` | 3 | `GuiRegistryAccess`、`GuiTransformer`、`GuiProvider` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.reader` | 3 | `StreamReader`、`UnicodeReader`、`ReaderException` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.extensions.compactnotation` | 3 | `CompactConstructor`、`CompactData`、`PackageCompactConstructor` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.external.com.google.gdata.util.common.base` | 3 | `UnicodeEscaper`、`Escaper`、`PercentEscaper` |
| `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.impl.serializer` | 3 | `CommentSerializer`、`InternalDeserializerFunction`、`DeserializerFunctionPool` |
| `me.shedaniel.clothconfig` | 2 | `ClothConfigForge`、`ClothConfigForgeDemo` |
| `me.shedaniel.autoconfig.example` | 2 | `ExampleConfig`、`ExampleInits` |
| `me.shedaniel.clothconfig2` | 2 | `ClothConfigDemo`、`ClothConfigInitializer` |
| `me.shedaniel.autoconfig.annotation` | 2 | `Config`、`ConfigEntry` |
| `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.composer` | 2 | `Composer`、`ComposerException` |
| … | | 其余 11 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `GlobalizedClothConfigScreen` | class | AbstractConfigScreen | — | 34 | 630 | [GlobalizedClothConfigScreen.java](me/shedaniel/clothconfig2/gui/GlobalizedClothConfigScreen.java) |
| `AbstractConfigScreen` | class | Screen | — | 35 | 452 | [AbstractConfigScreen.java](me/shedaniel/clothconfig2/gui/AbstractConfigScreen.java) |
| `ClothConfigForge` | class | — | Mod | 0 | 14 | [ClothConfigForge.java](me/shedaniel/clothconfig/ClothConfigForge.java) |
| `ClothConfigScreen` | class | AbstractTabbedConfigScreen | OnlyIn | 29 | 565 | [ClothConfigScreen.java](me/shedaniel/clothconfig2/gui/ClothConfigScreen.java) |
| `ScannerImpl` | class | Scanner | — | 73 | 1423 | [ScannerImpl.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/scanner/ScannerImpl.java) |
| `Emitter` | class | Emitable | — | 47 | 1525 | [Emitter.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/emitter/Emitter.java) |
| `ConfigManager` | class | ConfigHolder<T> | Internal | 10 | 127 | [ConfigManager.java](me/shedaniel/autoconfig/ConfigManager.java) |
| `ConfigBuilderImpl` | class | ConfigBuilder | Internal、OnlyIn | 30 | 245 | [ConfigBuilderImpl.java](me/shedaniel/clothconfig2/impl/ConfigBuilderImpl.java) |
| `RecordValueAnimatorArgs` | class | RecordValueAnimator.Arg<T> | Internal | 100 | 3003 | [RecordValueAnimatorArgs.java](me/shedaniel/clothconfig2/api/animator/RecordValueAnimatorArgs.java) |
| `ExampleConfig` | class | PartitioningSerializer.GlobalData | Config、Config.Gui.Background、Config.Gui. | 0 | 100 | [ExampleConfig.java](me/shedaniel/autoconfig/example/ExampleConfig.java) |
| `DropdownBoxEntry` | class | TooltipListEntry | OnlyIn | 95 | 895 | [DropdownBoxEntry.java](me/shedaniel/clothconfig2/gui/entries/DropdownBoxEntry.java) |
| `DropdownMenuBuilder` | class | FieldBuilder | OnlyIn | 79 | 577 | [DropdownMenuBuilder.java](me/shedaniel/clothconfig2/impl/builders/DropdownMenuBuilder.java) |
| `DynamicEntryListWidget` | class | AbstractList | OnlyIn | 79 | 765 | [DynamicEntryListWidget.java](me/shedaniel/clothconfig2/gui/widget/DynamicEntryListWidget.java) |
| `ConfigEntryBuilderImpl` | class | ConfigEntryBuilder | OnlyIn | 27 | 179 | [ConfigEntryBuilderImpl.java](me/shedaniel/clothconfig2/impl/ConfigEntryBuilderImpl.java) |
| `AbstractConfigEntry` | class | DynamicElementListWidget.ElementEntr | OnlyIn | 25 | 183 | [AbstractConfigEntry.java](me/shedaniel/clothconfig2/api/AbstractConfigEntry.java) |
| `JsonArray` | class | JsonElement | — | 56 | 498 | [JsonArray.java](me/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonArray.java) |
| `JsonObject` | class | JsonElement | — | 44 | 576 | [JsonObject.java](me/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonObject.java) |
| `BaseConstructor` | class | — | — | 42 | 474 | [BaseConstructor.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/constructor/BaseConstructor.java) |
| `ParserImpl` | class | Parser | — | 29 | 731 | [ParserImpl.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/parser/ParserImpl.java) |
| `GuiRegistry` | class | GuiRegistryAccess | OnlyIn | 11 | 132 | [GuiRegistry.java](me/shedaniel/autoconfig/gui/registry/GuiRegistry.java) |
| `BaseListEntry` | class | Expandable | OnlyIn | 35 | 445 | [BaseListEntry.java](me/shedaniel/clothconfig2/gui/entries/BaseListEntry.java) |
| `Rectangle` | class | Cloneable | — | 53 | 543 | [Rectangle.java](me/shedaniel/math/Rectangle.java) |
| `FloatingRectangle` | class | Cloneable | — | 49 | 519 | [FloatingRectangle.java](me/shedaniel/math/FloatingRectangle.java) |
| `Jankson` | class | — | — | 28 | 411 | [Jankson.java](me/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/Jankson.java) |
| `DumperOptions` | class | — | — | 53 | 357 | [DumperOptions.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/DumperOptions.java) |
| `Yaml` | class | Iterable<Event> | — | 44 | 399 | [Yaml.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/Yaml.java) |
| `SafeRepresenter` | class | BaseRepresenter | — | 27 | 459 | [SafeRepresenter.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/representer/SafeRepresenter.java) |
| `SafeConstructor` | class | BaseConstructor | — | 26 | 571 | [SafeConstructor.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/constructor/SafeConstructor.java) |
| `Toml` | class | Map.Entry<String | — | 31 | 280 | [Toml.java](me/shedaniel/cloth/clothconfig/shadowed/com/moandjiezana/toml/Toml.java) |
| `ConfigScreenProvider` | class | Supplier<Screen> | OnlyIn | 6 | 112 | [ConfigScreenProvider.java](me/shedaniel/autoconfig/gui/ConfigScreenProvider.java) |
| `ConfigSerializeEvent` | class | — | — | 0 | 21 | [ConfigSerializeEvent.java](me/shedaniel/autoconfig/event/ConfigSerializeEvent.java) |
| `ClothConfigDemo` | class | — | — | 5 | 412 | [ClothConfigDemo.java](me/shedaniel/clothconfig2/ClothConfigDemo.java) |
| `SubCategoryListEntry` | class | TooltipListEntry | OnlyIn | 31 | 294 | [SubCategoryListEntry.java](me/shedaniel/clothconfig2/gui/entries/SubCategoryListEntry.java) |
| `RecordValueAnimator` | class | ValueAnimator<T> | Internal | 25 | 412 | [RecordValueAnimator.java](me/shedaniel/clothconfig2/api/animator/RecordValueAnimator.java) |
| `TypeDescription` | class | — | — | 25 | 285 | [TypeDescription.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/TypeDescription.java) |
| `DefaultGuiRegistryAccess` | class | GuiRegistryAccess | — | 2 | 24 | [DefaultGuiRegistryAccess.java](me/shedaniel/autoconfig/gui/registry/DefaultGuiRegistryAccess.java) |
| `ComposedGuiRegistryAccess` | class | GuiRegistryAccess | OnlyIn | 2 | 41 | [ComposedGuiRegistryAccess.java](me/shedaniel/autoconfig/gui/registry/ComposedGuiRegistryAccess.java) |
| `GuiRegistryAccess` | interface | — | OnlyIn | 0 | 15 | [GuiRegistryAccess.java](me/shedaniel/autoconfig/gui/registry/api/GuiRegistryAccess.java) |
| `AbstractTabbedConfigScreen` | class | AbstractConfigScreen | — | 4 | 44 | [AbstractTabbedConfigScreen.java](me/shedaniel/clothconfig2/gui/AbstractTabbedConfigScreen.java) |
| `MultiElementListEntry` | class | TooltipListEntry | OnlyIn | 26 | 257 | [MultiElementListEntry.java](me/shedaniel/clothconfig2/gui/entries/MultiElementListEntry.java) |
| `ScissorsHandlerImpl` | class | ScissorsHandler | Internal、OnlyIn | 6 | 102 | [ScissorsHandlerImpl.java](me/shedaniel/clothconfig2/impl/ScissorsHandlerImpl.java) |
| `SubCategoryBuilder` | class | FieldBuilder | OnlyIn | 30 | 182 | [SubCategoryBuilder.java](me/shedaniel/clothconfig2/impl/builders/SubCategoryBuilder.java) |
| `ConfigScreen` | interface | — | — | 0 | 26 | [ConfigScreen.java](me/shedaniel/clothconfig2/api/ConfigScreen.java) |
| `TabbedConfigScreen` | interface | — | — | 0 | 13 | [TabbedConfigScreen.java](me/shedaniel/clothconfig2/api/TabbedConfigScreen.java) |
| `ReferenceBuildingConfigScreen` | interface | — | — | 0 | 6 | [ReferenceBuildingConfigScreen.java](me/shedaniel/clothconfig2/api/ReferenceBuildingConfigScreen.java) |
| `ScrollingContainer` | class | — | — | 25 | 200 | [ScrollingContainer.java](me/shedaniel/clothconfig2/api/scroll/ScrollingContainer.java) |
| `Constructor` | class | SafeConstructor | — | 17 | 564 | [Constructor.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/constructor/Constructor.java) |
| `ConfigData` | interface | Exception | — | 0 | 21 | [ConfigData.java](me/shedaniel/autoconfig/ConfigData.java) |
| `ClothConfigInitializer` | class | — | OnlyIn | 8 | 56 | [ClothConfigInitializer.java](me/shedaniel/clothconfig2/ClothConfigInitializer.java) |
| `ClothConfigTabButton` | class | AbstractButton | OnlyIn | 6 | 84 | [ClothConfigTabButton.java](me/shedaniel/clothconfig2/gui/ClothConfigTabButton.java) |
| `DynamicElementListWidget` | class | ContainerEventHandler | OnlyIn | 22 | 253 | [DynamicElementListWidget.java](me/shedaniel/clothconfig2/gui/widget/DynamicElementListWidget.java) |
| `ConfigCategoryImpl` | class | ConfigCategory | OnlyIn | 9 | 82 | [ConfigCategoryImpl.java](me/shedaniel/clothconfig2/impl/ConfigCategoryImpl.java) |
| `AbstractConfigListEntry` | class | AbstractConfigEntry | OnlyIn | 9 | 68 | [AbstractConfigListEntry.java](me/shedaniel/clothconfig2/api/AbstractConfigListEntry.java) |
| `ScissorsHandler` | interface | — | OnlyIn | 0 | 23 | [ScissorsHandler.java](me/shedaniel/clothconfig2/api/ScissorsHandler.java) |
| `NumberAnimator` | class | Number | — | 25 | 120 | [NumberAnimator.java](me/shedaniel/clothconfig2/api/animator/NumberAnimator.java) |
| `MarshallerImpl` | class | Marshaller | — | 12 | 421 | [MarshallerImpl.java](me/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/impl/MarshallerImpl.java) |
| `CollectionStartEvent` | class | NodeEvent | — | 5 | 48 | [CollectionStartEvent.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/events/CollectionStartEvent.java) |
| `ScalarEvent` | class | NodeEvent | — | 8 | 68 | [ScalarEvent.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/events/ScalarEvent.java) |
| `Event` | class | — | — | 8 | 60 | [Event.java](me/shedaniel/cloth/clothconfig/shadowed/org/yaml/snakeyaml/events/Event.java) |
| `ConfigHolder` | interface | — | NonExtendable | 0 | 31 | [ConfigHolder.java](me/shedaniel/autoconfig/ConfigHolder.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `GlobalizedClothConfigScreen` | — | [GlobalizedClothConfigScreen.java](me/shedaniel/clothconfig2/gui/GlobalizedClothConfigScreen.java) |
| `AbstractConfigScreen` | — | [AbstractConfigScreen.java](me/shedaniel/clothconfig2/gui/AbstractConfigScreen.java) |
| `ClothConfigForge` | Mod | [ClothConfigForge.java](me/shedaniel/clothconfig/ClothConfigForge.java) |
| `ClothConfigScreen` | OnlyIn | [ClothConfigScreen.java](me/shedaniel/clothconfig2/gui/ClothConfigScreen.java) |
| `ConfigManager` | Internal | [ConfigManager.java](me/shedaniel/autoconfig/ConfigManager.java) |
| `ConfigBuilderImpl` | Internal;OnlyIn | [ConfigBuilderImpl.java](me/shedaniel/clothconfig2/impl/ConfigBuilderImpl.java) |
| `ExampleConfig` | Config;Config.Gui.Background;Config.Gui. | [ExampleConfig.java](me/shedaniel/autoconfig/example/ExampleConfig.java) |
| `ConfigEntryBuilderImpl` | OnlyIn | [ConfigEntryBuilderImpl.java](me/shedaniel/clothconfig2/impl/ConfigEntryBuilderImpl.java) |
| `AbstractConfigEntry` | OnlyIn | [AbstractConfigEntry.java](me/shedaniel/clothconfig2/api/AbstractConfigEntry.java) |
| `ConfigScreenProvider` | OnlyIn | [ConfigScreenProvider.java](me/shedaniel/autoconfig/gui/ConfigScreenProvider.java) |
| `ConfigSerializeEvent` | — | [ConfigSerializeEvent.java](me/shedaniel/autoconfig/event/ConfigSerializeEvent.java) |
| `ClothConfigDemo` | — | [ClothConfigDemo.java](me/shedaniel/clothconfig2/ClothConfigDemo.java) |
| `AbstractTabbedConfigScreen` | — | [AbstractTabbedConfigScreen.java](me/shedaniel/clothconfig2/gui/AbstractTabbedConfigScreen.java) |
| `ConfigScreen` | — | [ConfigScreen.java](me/shedaniel/clothconfig2/api/ConfigScreen.java) |
| `TabbedConfigScreen` | — | [TabbedConfigScreen.java](me/shedaniel/clothconfig2/api/TabbedConfigScreen.java) |
| `ReferenceBuildingConfigScreen` | — | [ReferenceBuildingConfigScreen.java](me/shedaniel/clothconfig2/api/ReferenceBuildingConfigScreen.java) |
| `ConfigData` | — | [ConfigData.java](me/shedaniel/autoconfig/ConfigData.java) |
| `ClothConfigInitializer` | OnlyIn | [ClothConfigInitializer.java](me/shedaniel/clothconfig2/ClothConfigInitializer.java) |
| `ClothConfigTabButton` | OnlyIn | [ClothConfigTabButton.java](me/shedaniel/clothconfig2/gui/ClothConfigTabButton.java) |
| `ConfigCategoryImpl` | OnlyIn | [ConfigCategoryImpl.java](me/shedaniel/clothconfig2/impl/ConfigCategoryImpl.java) |
| `AbstractConfigListEntry` | OnlyIn | [AbstractConfigListEntry.java](me/shedaniel/clothconfig2/api/AbstractConfigListEntry.java) |
| `ConfigHolder` | NonExtendable | [ConfigHolder.java](me/shedaniel/autoconfig/ConfigHolder.java) |
| `AutoConfig` | — | [AutoConfig.java](me/shedaniel/autoconfig/AutoConfig.java) |
| `GsonConfigSerializer` | — | [GsonConfigSerializer.java](me/shedaniel/autoconfig/serializer/GsonConfigSerializer.java) |
| `ConfigSerializer` | — | [ConfigSerializer.java](me/shedaniel/autoconfig/serializer/ConfigSerializer.java) |
| `Toml4jConfigSerializer` | — | [Toml4jConfigSerializer.java](me/shedaniel/autoconfig/serializer/Toml4jConfigSerializer.java) |
| `YamlConfigSerializer` | — | [YamlConfigSerializer.java](me/shedaniel/autoconfig/serializer/YamlConfigSerializer.java) |
| `DummyConfigSerializer` | — | [DummyConfigSerializer.java](me/shedaniel/autoconfig/serializer/DummyConfigSerializer.java) |
| `JanksonConfigSerializer` | — | [JanksonConfigSerializer.java](me/shedaniel/autoconfig/serializer/JanksonConfigSerializer.java) |
| `Config` | Retention;Target | [Config.java](me/shedaniel/autoconfig/annotation/Config.java) |

## 按包浏览

- `architectury_inject_clothconfig_common_d551de04735c42148ec07a78c43007d1_c487a9cf12e7d5bea169da255b814f03602bdeb8b51cff67162252c24f285789clothconfig150140devjar`（1 类）
- `me.shedaniel.autoconfig`（4 类）
- `me.shedaniel.autoconfig.annotation`（2 类）
- `me.shedaniel.autoconfig.event`（1 类）
- `me.shedaniel.autoconfig.example`（2 类）
- `me.shedaniel.autoconfig.gui`（3 类）
- `me.shedaniel.autoconfig.gui.registry`（3 类）
- `me.shedaniel.autoconfig.gui.registry.api`（3 类）
- `me.shedaniel.autoconfig.serializer`（7 类）
- `me.shedaniel.autoconfig.util`（1 类）
- `me.shedaniel.autoconfig.util.neoforge`（1 类）
- `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson`（8 类）
- `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation`（5 类）
- `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.api`（4 类）
- `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.impl`（11 类）
- `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.impl.serializer`（3 类）
- `me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.magic`（1 类）
- `me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml`（30 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml`（4 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.composer`（2 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.constructor`（8 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.emitter`（5 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.env`（1 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.error`（4 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.events`（15 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.extensions.compactnotation`（3 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.external.biz.base64Coder`（1 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.external.com.google.gdata.util.common.base`（3 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.introspector`（8 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.nodes`（9 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.parser`（5 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.reader`（3 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.representer`（4 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.resolver`（2 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.scanner`（5 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.serializer`（4 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.tokens`（24 类）
- `me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.util`（4 类）
- `me.shedaniel.clothconfig`（2 类）
- `me.shedaniel.clothconfig2`（2 类）
- `me.shedaniel.clothconfig2.api`（23 类）
- `me.shedaniel.clothconfig2.api.animator`（13 类）
- `me.shedaniel.clothconfig2.api.scroll`（1 类）
- `me.shedaniel.clothconfig2.gui`（6 类）
- `me.shedaniel.clothconfig2.gui.entries`（30 类）
- `me.shedaniel.clothconfig2.gui.widget`（6 类）
- `me.shedaniel.clothconfig2.impl`（9 类）
- `me.shedaniel.clothconfig2.impl.builders`（27 类）
- `me.shedaniel.math`（7 类）
- `me.shedaniel.math.api`（1 类）
- `me.shedaniel.math.impl`（1 类）
