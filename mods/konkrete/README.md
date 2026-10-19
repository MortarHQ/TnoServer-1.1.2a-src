# Konkrete（`konkrete`）

> jar：`[前置]konkrete.jar` ｜ 版本：`1.9.9` ｜ 作者：Keksuccino
> 规模：**237 个类 / 52 个包 / 24895 行** ｜ 事件处理类 0 ｜ Mixin 12 ｜ 注册/管理类 9

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `de.keksuccino.konkrete.json.jsonpath` | 20 | `Configuration`、`Criteria`、`JsonPath` |
| `de.keksuccino.konkrete.json.jsonpath.internal.path` | 18 | `PathCompiler`、`PathToken`、`EvaluationContextImpl` |
| `de.keksuccino.konkrete.json.minidev.json.writer` | 12 | `CollectionMapper`、`ArraysMapper`、`CompessorMapper` |
| `de.keksuccino.konkrete.mixin.mixins.client` | 11 | `IMixinClientPacketListener`、`IMixinMouseHandler`、`MixinMouseHandler` |
| `de.keksuccino.konkrete.json.jsonpath.internal.filter` | 11 | `ValueNodes`、`ValueNode`、`EvaluatorFactory` |
| `de.keksuccino.konkrete.json.minidev.json` | 11 | `JSONNavi`、`JSONValue`、`JSONObject` |
| `de.keksuccino.konkrete.json.jsonpath.internal` | 10 | `PathRef`、`CharacterIndex`、`JsonContext` |
| `de.keksuccino.konkrete.json.minidev.asm` | 10 | `BeansAccessConfig`、`DefaultConverter`、`ASMUtil` |
| `de.keksuccino.konkrete.objecthunter.exp4j.tokenizer` | 10 | `Tokenizer`、`UnknownFunctionOrVariableException`、`VariableToken` |
| `de.keksuccino.konkrete.gui.content` | 9 | `ContextMenu`、`AdvancedButton`、`AdvancedButtonHandler` |
| `de.keksuccino.konkrete.json.minidev.json.parser` | 9 | `JSONParserBase`、`JSONParser`、`JSONParserByteArray` |
| `de.keksuccino.konkrete.input` | 6 | `KeyboardHandler`、`CharacterFilter`、`MouseInput` |
| `de.keksuccino.konkrete.json.minidev.json.reader` | 6 | `JsonWriter`、`BeansWriterASMRemap`、`JsonWriterI` |
| `de.keksuccino.konkrete.gui.screens.popup` | 6 | `PopupHandler`、`FilePickerPopup`、`TextInputPopup` |
| `de.keksuccino.konkrete.json.jsonpath.internal.function.numeric` | 6 | `Max`、`Min`、`AbstractAggregation` |
| `de.keksuccino.konkrete.resources` | 5 | `TextureHandler`、`ExternalTextureResourceLocation`、`WebTextureResourceLocation` |
| `de.keksuccino.konkrete.json.jsonpath.internal.function` | 5 | `Parameter`、`PassthruPathFunction`、`PathFunction` |
| `de.keksuccino.konkrete` | 4 | `KonkreteNeoForge`、`PostClientInitTaskExecutor`、`Konkrete` |
| `de.keksuccino.konkrete.rendering.animation` | 4 | `ExternalGifAnimationRenderer`、`ExternalTextureAnimationRenderer`、`AnimationRenderer` |
| `de.keksuccino.konkrete.objecthunter.exp4j` | 4 | `Expression`、`ExpressionBuilder`、`ValidationResult` |
| `de.keksuccino.konkrete.json.jsonpath.spi.cache` | 4 | `LRUCache`、`CacheProvider`、`NOOPCache` |
| `de.keksuccino.konkrete.json.jsonpath.spi.json` | 3 | `GsonJsonProvider`、`AbstractJsonProvider`、`JsonProvider` |
| `de.keksuccino.konkrete.gui.content.scrollarea` | 3 | `ScrollArea`、`LegacyScrollAreaCompat`、`ScrollAreaEntry` |
| `de.keksuccino.konkrete.platform` | 3 | `NeoForgeCompatibilityLayer`、`NeoForgePlatformHelper`、`Services` |
| `de.keksuccino.konkrete.properties` | 3 | `PropertiesSection`、`PropertiesSerializer`、`PropertiesSet` |
| `de.keksuccino.konkrete.localization` | 3 | `LocalizationPackage`、`LocaleUtils`、`Locals` |
| `de.keksuccino.konkrete.json.jsonpath.internal.function.latebinding` | 3 | `JsonLateBindingValue`、`ILateBindingValue`、`PathLateBindingValue` |
| `de.keksuccino.konkrete.json.jsonpath.spi.mapper` | 3 | `GsonMappingProvider`、`MappingProvider`、`MappingException` |
| `de.keksuccino.konkrete.config` | 2 | `Config`、`ConfigEntry` |
| `de.keksuccino.konkrete.objecthunter.exp4j.function` | 2 | `Functions`、`Function` |
| `de.keksuccino.konkrete.gui.content.handling` | 2 | `AdvancedWidgetsHandler`、`IAdvancedWidgetBase` |
| `de.keksuccino.konkrete.rendering` | 2 | `GifDecoder`、`RenderUtils` |
| `de.keksuccino.konkrete.command` | 2 | `CommandUtils`、`ClientExecutor` |
| `de.keksuccino.konkrete.platform.services` | 2 | `IPlatformCompatibilityLayer`、`IPlatformHelper` |
| `de.keksuccino.konkrete.objecthunter.exp4j.operator` | 2 | `Operators`、`Operator` |
| `de.keksuccino.konkrete.json.jsonpath.internal.function.text` | 2 | `Concatenate`、`Length` |
| `de.keksuccino.konkrete.json.jsonpath.internal.function.json` | 2 | `Append`、`KeySetFunction` |
| `de.keksuccino.konkrete.json.minidev.json.annotate` | 2 | `JsonSmartAnnotation`、`JsonIgnore` |
| `de.keksuccino.konkrete.json.minidev.asm.ex` | 2 | `NoSuchFieldException`、`ConvertException` |
| `de.keksuccino.konkrete.gui.screens` | 1 | `ConfigScreen` |
| … | | 其余 12 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigScreen` | class | Screen | — | 33 | 506 | [ConfigScreen.java](de/keksuccino/konkrete/gui/screens/ConfigScreen.java) |
| `Config` | class | — | — | 48 | 760 | [Config.java](de/keksuccino/konkrete/config/Config.java) |
| `IMixinClientPacketListener` | interface | — | Mixin | 0 | 17 | [IMixinClientPacketListener.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinClientPacketListener.java) |
| `IMixinMouseHandler` | interface | — | Mixin | 0 | 12 | [IMixinMouseHandler.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinMouseHandler.java) |
| `MixinMouseHandler` | class | — | Mixin | 2 | 66 | [MixinMouseHandler.java](de/keksuccino/konkrete/mixin/mixins/client/MixinMouseHandler.java) |
| `MixinKeyboardHandler` | class | — | Mixin | 2 | 46 | [MixinKeyboardHandler.java](de/keksuccino/konkrete/mixin/mixins/client/MixinKeyboardHandler.java) |
| `KonkreteNeoForge` | class | — | Mod | 0 | 12 | [KonkreteNeoForge.java](de/keksuccino/konkrete/KonkreteNeoForge.java) |
| `ContextMenu` | class | IMenu | — | 32 | 327 | [ContextMenu.java](de/keksuccino/konkrete/gui/content/ContextMenu.java) |
| `Configuration` | class | — | — | 23 | 201 | [Configuration.java](de/keksuccino/konkrete/json/jsonpath/Configuration.java) |
| `ValueNodes` | interface | ValueNode | — | 88 | 729 | [ValueNodes.java](de/keksuccino/konkrete/json/jsonpath/internal/filter/ValueNodes.java) |
| `JSONParserBase` | class | — | — | 24 | 1049 | [JSONParserBase.java](de/keksuccino/konkrete/json/minidev/json/parser/JSONParserBase.java) |
| `AdvancedButton` | class | Button | — | 43 | 477 | [AdvancedButton.java](de/keksuccino/konkrete/gui/content/AdvancedButton.java) |
| `JSONNavi` | class | — | — | 55 | 579 | [JSONNavi.java](de/keksuccino/konkrete/json/minidev/json/JSONNavi.java) |
| `SoundHandler` | class | — | — | 13 | 200 | [SoundHandler.java](de/keksuccino/konkrete/sound/SoundHandler.java) |
| `MixinGameRenderer` | class | — | Mixin | 1 | 26 | [MixinGameRenderer.java](de/keksuccino/konkrete/mixin/mixins/neoforge/client/MixinGameRenderer.java) |
| `IMixinClientLanguage` | interface | — | Mixin | 0 | 13 | [IMixinClientLanguage.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinClientLanguage.java) |
| `IMixinScreen` | interface | — | Mixin | 0 | 30 | [IMixinScreen.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinScreen.java) |
| `ExternalGifAnimationRenderer` | class | IAnimationRenderer | — | 28 | 305 | [ExternalGifAnimationRenderer.java](de/keksuccino/konkrete/rendering/animation/ExternalGifAnimationRenderer.java) |
| `ExternalTextureAnimationRenderer` | class | IAnimationRenderer | — | 29 | 347 | [ExternalTextureAnimationRenderer.java](de/keksuccino/konkrete/rendering/animation/ExternalTextureAnimationRenderer.java) |
| `Criteria` | class | Predicate | — | 37 | 266 | [Criteria.java](de/keksuccino/konkrete/json/jsonpath/Criteria.java) |
| `JsonPath` | class | — | — | 39 | 353 | [JsonPath.java](de/keksuccino/konkrete/json/jsonpath/JsonPath.java) |
| `PathRef` | class | PathRef | — | 48 | 332 | [PathRef.java](de/keksuccino/konkrete/json/jsonpath/internal/PathRef.java) |
| `CharacterIndex` | class | — | — | 43 | 319 | [CharacterIndex.java](de/keksuccino/konkrete/json/jsonpath/internal/CharacterIndex.java) |
| `ValueNode` | class | — | — | 41 | 238 | [ValueNode.java](de/keksuccino/konkrete/json/jsonpath/internal/filter/ValueNode.java) |
| `EvaluatorFactory` | class | Evaluator | — | 25 | 404 | [EvaluatorFactory.java](de/keksuccino/konkrete/json/jsonpath/internal/filter/EvaluatorFactory.java) |
| `JSONValue` | class | — | — | 41 | 321 | [JSONValue.java](de/keksuccino/konkrete/json/minidev/json/JSONValue.java) |
| `KeyboardHandler` | class | — | — | 14 | 83 | [KeyboardHandler.java](de/keksuccino/konkrete/input/KeyboardHandler.java) |
| `Functions` | class | — | — | 32 | 312 | [Functions.java](de/keksuccino/konkrete/objecthunter/exp4j/function/Functions.java) |
| `MixinMinecraft` | class | — | Mixin | 5 | 58 | [MixinMinecraft.java](de/keksuccino/konkrete/mixin/mixins/client/MixinMinecraft.java) |
| `AnimationRenderer` | class | IAnimationRenderer | — | 26 | 166 | [AnimationRenderer.java](de/keksuccino/konkrete/rendering/animation/AnimationRenderer.java) |
| `AdvancedWidgetsHandler` | class | — | — | 5 | 57 | [AdvancedWidgetsHandler.java](de/keksuccino/konkrete/gui/content/handling/AdvancedWidgetsHandler.java) |
| `JsonContext` | class | DocumentContext | — | 27 | 218 | [JsonContext.java](de/keksuccino/konkrete/json/jsonpath/internal/JsonContext.java) |
| `FilterCompiler` | class | Filter | — | 24 | 440 | [FilterCompiler.java](de/keksuccino/konkrete/json/jsonpath/internal/filter/FilterCompiler.java) |
| `JsonWriter` | class | — | — | 29 | 342 | [JsonWriter.java](de/keksuccino/konkrete/json/minidev/json/reader/JsonWriter.java) |
| `TextureHandler` | class | — | — | 5 | 78 | [TextureHandler.java](de/keksuccino/konkrete/resources/TextureHandler.java) |
| `IMixinDynamicTexture` | interface | — | Mixin | 0 | 16 | [IMixinDynamicTexture.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinDynamicTexture.java) |
| `IMixinLocalPlayer` | interface | — | Mixin | 0 | 12 | [IMixinLocalPlayer.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinLocalPlayer.java) |
| `IMixinEditBox` | interface | — | Mixin | 0 | 22 | [IMixinEditBox.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinEditBox.java) |
| `IMixinAbstractWidget` | interface | — | Mixin | 0 | 12 | [IMixinAbstractWidget.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinAbstractWidget.java) |
| `AdvancedButtonHandler` | class | — | — | 4 | 76 | [AdvancedButtonHandler.java](de/keksuccino/konkrete/gui/content/AdvancedButtonHandler.java) |
| `PopupHandler` | class | — | — | 3 | 19 | [PopupHandler.java](de/keksuccino/konkrete/gui/screens/popup/PopupHandler.java) |
| `Utils` | class | — | — | 17 | 315 | [Utils.java](de/keksuccino/konkrete/json/jsonpath/internal/Utils.java) |
| `PathCompiler` | class | — | — | 16 | 571 | [PathCompiler.java](de/keksuccino/konkrete/json/jsonpath/internal/path/PathCompiler.java) |
| `GsonJsonProvider` | class | AbstractJsonProvider | — | 22 | 250 | [GsonJsonProvider.java](de/keksuccino/konkrete/json/jsonpath/spi/json/GsonJsonProvider.java) |
| `CollectionMapper` | class | JsonReaderI | — | 20 | 242 | [CollectionMapper.java](de/keksuccino/konkrete/json/minidev/json/writer/CollectionMapper.java) |
| `ArraysMapper` | class | JsonReaderI | — | 22 | 323 | [ArraysMapper.java](de/keksuccino/konkrete/json/minidev/json/writer/ArraysMapper.java) |
| `ConfigEntry` | class | — | — | 8 | 67 | [ConfigEntry.java](de/keksuccino/konkrete/config/ConfigEntry.java) |
| `GifDecoder` | class | — | — | 17 | 526 | [GifDecoder.java](de/keksuccino/konkrete/rendering/GifDecoder.java) |
| `DropdownMenu` | class | IMenu | — | 13 | 135 | [DropdownMenu.java](de/keksuccino/konkrete/gui/content/DropdownMenu.java) |
| `PathToken` | class | — | — | 15 | 208 | [PathToken.java](de/keksuccino/konkrete/json/jsonpath/internal/path/PathToken.java) |
| `JSONObject` | class | HashMap | — | 20 | 184 | [JSONObject.java](de/keksuccino/konkrete/json/minidev/json/JSONObject.java) |
| `JSONStyle` | class | — | — | 20 | 139 | [JSONStyle.java](de/keksuccino/konkrete/json/minidev/json/JSONStyle.java) |
| `BeansAccessConfig` | class | — | — | 1 | 27 | [BeansAccessConfig.java](de/keksuccino/konkrete/json/minidev/asm/BeansAccessConfig.java) |
| `DefaultConverter` | class | — | — | 16 | 222 | [DefaultConverter.java](de/keksuccino/konkrete/json/minidev/asm/DefaultConverter.java) |
| `Tokenizer` | class | — | — | 16 | 286 | [Tokenizer.java](de/keksuccino/konkrete/objecthunter/exp4j/tokenizer/Tokenizer.java) |
| `CommandUtils` | class | — | — | 1 | 14 | [CommandUtils.java](de/keksuccino/konkrete/command/CommandUtils.java) |
| `FileUtils` | class | — | — | 10 | 291 | [FileUtils.java](de/keksuccino/konkrete/file/FileUtils.java) |
| `RenderUtils` | class | — | — | 17 | 150 | [RenderUtils.java](de/keksuccino/konkrete/rendering/RenderUtils.java) |
| `ScrollArea` | class | — | — | 12 | 207 | [ScrollArea.java](de/keksuccino/konkrete/gui/content/scrollarea/ScrollArea.java) |
| `EvaluationContextImpl` | class | EvaluationContext | — | 16 | 183 | [EvaluationContextImpl.java](de/keksuccino/konkrete/json/jsonpath/internal/path/EvaluationContextImpl.java) |

## Mixin（12 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `IMixinClientPacketListener` | `IClientPacketListener` | [IMixinClientPacketListener.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinClientPacketListener.java) |
| `IMixinMouseHandler` | `IMouseHandler` | [IMixinMouseHandler.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinMouseHandler.java) |
| `MixinMouseHandler` | `MouseHandler` | [MixinMouseHandler.java](de/keksuccino/konkrete/mixin/mixins/client/MixinMouseHandler.java) |
| `MixinKeyboardHandler` | `KeyboardHandler` | [MixinKeyboardHandler.java](de/keksuccino/konkrete/mixin/mixins/client/MixinKeyboardHandler.java) |
| `MixinGameRenderer` | `GameRenderer` | [MixinGameRenderer.java](de/keksuccino/konkrete/mixin/mixins/neoforge/client/MixinGameRenderer.java) |
| `IMixinClientLanguage` | `IClientLanguage` | [IMixinClientLanguage.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinClientLanguage.java) |
| `IMixinScreen` | `IScreen` | [IMixinScreen.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinScreen.java) |
| `MixinMinecraft` | `Minecraft` | [MixinMinecraft.java](de/keksuccino/konkrete/mixin/mixins/client/MixinMinecraft.java) |
| `IMixinDynamicTexture` | `IDynamicTexture` | [IMixinDynamicTexture.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinDynamicTexture.java) |
| `IMixinLocalPlayer` | `ILocalPlayer` | [IMixinLocalPlayer.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinLocalPlayer.java) |
| `IMixinEditBox` | `IEditBox` | [IMixinEditBox.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinEditBox.java) |
| `IMixinAbstractWidget` | `IAbstractWidget` | [IMixinAbstractWidget.java](de/keksuccino/konkrete/mixin/mixins/client/IMixinAbstractWidget.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigScreen` | — | [ConfigScreen.java](de/keksuccino/konkrete/gui/screens/ConfigScreen.java) |
| `Config` | — | [Config.java](de/keksuccino/konkrete/config/Config.java) |
| `Configuration` | — | [Configuration.java](de/keksuccino/konkrete/json/jsonpath/Configuration.java) |
| `ConfigEntry` | — | [ConfigEntry.java](de/keksuccino/konkrete/config/ConfigEntry.java) |
| `BeansAccessConfig` | — | [BeansAccessConfig.java](de/keksuccino/konkrete/json/minidev/asm/BeansAccessConfig.java) |

## 按包浏览

- `de.keksuccino.konkrete`（4 类）
- `de.keksuccino.konkrete.annotations`（1 类）
- `de.keksuccino.konkrete.command`（2 类）
- `de.keksuccino.konkrete.config`（2 类）
- `de.keksuccino.konkrete.config.exceptions`（1 类）
- `de.keksuccino.konkrete.file`（1 类）
- `de.keksuccino.konkrete.gui.content`（9 类）
- `de.keksuccino.konkrete.gui.content.handling`（2 类）
- `de.keksuccino.konkrete.gui.content.scrollarea`（3 类）
- `de.keksuccino.konkrete.gui.content.widget`（1 类）
- `de.keksuccino.konkrete.gui.screens`（1 类）
- `de.keksuccino.konkrete.gui.screens.popup`（6 类）
- `de.keksuccino.konkrete.input`（6 类）
- `de.keksuccino.konkrete.json`（1 类）
- `de.keksuccino.konkrete.json.jsonpath`（20 类）
- `de.keksuccino.konkrete.json.jsonpath.internal`（10 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.filter`（11 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.function`（5 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.function.json`（2 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.function.latebinding`（3 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.function.numeric`（6 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.function.text`（2 类）
- `de.keksuccino.konkrete.json.jsonpath.internal.path`（18 类）
- `de.keksuccino.konkrete.json.jsonpath.spi.cache`（4 类）
- `de.keksuccino.konkrete.json.jsonpath.spi.json`（3 类）
- `de.keksuccino.konkrete.json.jsonpath.spi.mapper`（3 类）
- `de.keksuccino.konkrete.json.minidev.asm`（10 类）
- `de.keksuccino.konkrete.json.minidev.asm.ex`（2 类）
- `de.keksuccino.konkrete.json.minidev.json`（11 类）
- `de.keksuccino.konkrete.json.minidev.json.annotate`（2 类）
- `de.keksuccino.konkrete.json.minidev.json.parser`（9 类）
- `de.keksuccino.konkrete.json.minidev.json.reader`（6 类）
- `de.keksuccino.konkrete.json.minidev.json.writer`（12 类）
- `de.keksuccino.konkrete.localization`（3 类）
- `de.keksuccino.konkrete.math`（1 类）
- `de.keksuccino.konkrete.mixin`（1 类）
- `de.keksuccino.konkrete.mixin.mixins.client`（11 类）
- `de.keksuccino.konkrete.mixin.mixins.neoforge.client`（1 类）
- `de.keksuccino.konkrete.objecthunter.exp4j`（4 类）
- `de.keksuccino.konkrete.objecthunter.exp4j.function`（2 类）
- `de.keksuccino.konkrete.objecthunter.exp4j.operator`（2 类）
- `de.keksuccino.konkrete.objecthunter.exp4j.shuntingyard`（1 类）
- `de.keksuccino.konkrete.objecthunter.exp4j.tokenizer`（10 类）
- `de.keksuccino.konkrete.platform`（3 类）
- `de.keksuccino.konkrete.platform.services`（2 类）
- `de.keksuccino.konkrete.properties`（3 类）
- `de.keksuccino.konkrete.reflection`（1 类）
- `de.keksuccino.konkrete.rendering`（2 类）
- `de.keksuccino.konkrete.rendering.animation`（4 类）
- `de.keksuccino.konkrete.resources`（5 类）
- `de.keksuccino.konkrete.sound`（1 类）
- `de.keksuccino.konkrete.web`（1 类）
