# YetAnotherConfigLib（`yet_another_config_lib_v3`）

> jar：`[前置]yetanotherconfiglib.jar` ｜ 版本：`3.7.1+1.21.1-neoforge` ｜ 作者：isXander
> 规模：**239 个类 / 30 个包 / 14658 行** ｜ 事件处理类 0 ｜ Mixin 7 ｜ 注册/管理类 10

**官方描述**：YetAnotherConfigLib (yacl) is just that. A builder-based configuration library for Minecraft.\n

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.isxander.yacl3.config.v2.api.autogen` | 28 | `ItemField`、`SimpleOptionFactory`、`AutoGenField` |
| `dev.isxander.yacl3.config.v2.impl.autogen` | 23 | `OptionFactoryRegistry`、`ItemFieldImpl`、`ListGroupImpl` |
| `dev.isxander.yacl3.api.controller` | 22 | `ItemControllerBuilder`、`NumberFieldControllerBuilder`、`IntegerFieldControllerBuilder` |
| `dev.isxander.yacl3.impl` | 18 | `ListOptionImpl`、`InstantStateManager`、`ConfigCategoryImpl` |
| `dev.isxander.yacl3.api` | 18 | `StateManager`、`ConfigCategory`、`YetAnotherConfigLib` |
| `dev.isxander.yacl3.impl.controller` | 18 | `ItemControllerBuilderImpl`、`LongFieldControllerBuilderImpl`、`FloatFieldControllerBuilderImpl` |
| `dev.isxander.yacl3.gui` | 13 | `OptionListWidget`、`YACLScreen`、`ElementListWidgetExt` |
| `dev.isxander.yacl3.gui.controllers` | 10 | `ColorPickerWidget`、`ColorController`、`LabelController` |
| `dev.isxander.yacl3.gui.controllers.dropdown` | 9 | `AbstractDropdownControllerElement`、`DropdownWidget`、`ItemController` |
| `dev.isxander.yacl3.config.v3` | 9 | `CodecConfig`、`AbstractConfigEntry`、`JsonFileCodecConfig` |
| `dev.isxander.yacl3.mixin` | 7 | `ContainerEventHandlerMixin`、`MinecraftMixin`、`GuiGraphicsMixin` |
| `dev.isxander.yacl3.config.v2.api` | 7 | `ConfigClassHandler`、`ConfigField`、`ConfigSerializer` |
| `dev.isxander.yacl3.gui.controllers.slider` | 6 | `SliderControllerElement`、`DoubleSliderController`、`FloatSliderController` |
| `dev.isxander.yacl3.gui.utils` | 5 | `ItemRegistryHelper`、`GuiUtils`、`MiscUtil` |
| `dev.isxander.yacl3.gui.controllers.string.number` | 5 | `IntegerFieldController`、`DoubleFieldController`、`FloatFieldController` |
| `dev.isxander.yacl3.config.v2.impl` | 4 | `ConfigClassHandlerImpl`、`ConfigFieldImpl`、`ReflectionFieldAccess` |
| `dev.isxander.yacl3.platform` | 4 | `PlatformEntrypoint`、`YACLConfig`、`YACLPlatform` |
| `dev.isxander.yacl3.gui.image` | 4 | `ImageRendererManager`、`YACLImageReloadListener`、`ImageRenderer` |
| `dev.isxander.yacl3.gui.controllers.cycling` | 4 | `CyclingListController`、`ICyclingController`、`CyclingControllerElement` |
| `dev.isxander.yacl3.gui.render` | 4 | `ColorGradientRenderState`、`GuiRenderStateSink`、`YACLGuiElementRenderState` |
| `dev.isxander.yacl3.gui.controllers.string` | 3 | `StringControllerElement`、`StringController`、`IStringController` |
| `dev.isxander.yacl3.config` | 3 | `GsonConfigInstance`、`ConfigInstance`、`ConfigEntry` |
| `dev.isxander.yacl3.gui.tab` | 3 | `ListHolderWidget`、`ScrollableNavigationBar`、`TabExt` |
| `dev.isxander.yacl3.gui.image.impl` | 3 | `AnimatedDynamicTextureImage`、`DynamicTextureImage`、`ResourceTextureImage` |
| `dev.isxander.yacl3.api.utils` | 3 | `OptionUtils`、`Dimension`、`MutableDimension` |
| `dev.isxander.yacl3.impl.utils` | 2 | `DimensionIntegerImpl`、`YACLConstants` |
| `dev.isxander.yacl3.config.v2.impl.serializer` | 1 | `GsonConfigSerializer` |
| `dev.isxander.yacl3.config.v2.api.serializer` | 1 | `GsonConfigSerializerBuilder` |
| `dev.isxander.yacl3.debug` | 1 | `DebugProperties` |
| `dev.isxander.yacl3.config.util` | 1 | `CodecSerializerAdapter` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ContainerEventHandlerMixin` | interface | — | Mixin | 0 | 30 | [ContainerEventHandlerMixin.java](dev/isxander/yacl3/mixin/ContainerEventHandlerMixin.java) |
| `ConfigClassHandlerImpl` | class | ConfigClassHandler<T> | — | 19 | 298 | [ConfigClassHandlerImpl.java](dev/isxander/yacl3/config/v2/impl/ConfigClassHandlerImpl.java) |
| `PlatformEntrypoint` | class | — | Mod | 0 | 15 | [PlatformEntrypoint.java](dev/isxander/yacl3/platform/PlatformEntrypoint.java) |
| `OptionListWidget` | class | ElementListWidgetExt | — | 56 | 601 | [OptionListWidget.java](dev/isxander/yacl3/gui/OptionListWidget.java) |
| `YACLScreen` | class | Screen | — | 27 | 441 | [YACLScreen.java](dev/isxander/yacl3/gui/YACLScreen.java) |
| `ItemRegistryHelper` | class | — | — | 4 | 62 | [ItemRegistryHelper.java](dev/isxander/yacl3/gui/utils/ItemRegistryHelper.java) |
| `ConfigClassHandler` | interface | — | — | 0 | 42 | [ConfigClassHandler.java](dev/isxander/yacl3/config/v2/api/ConfigClassHandler.java) |
| `ImageRendererManager` | class | Supplier<T> | — | 6 | 124 | [ImageRendererManager.java](dev/isxander/yacl3/gui/image/ImageRendererManager.java) |
| `ColorPickerWidget` | class | ControllerPopupWidget | — | 31 | 462 | [ColorPickerWidget.java](dev/isxander/yacl3/gui/controllers/ColorPickerWidget.java) |
| `StringControllerElement` | class | ControllerWidget | — | 30 | 466 | [StringControllerElement.java](dev/isxander/yacl3/gui/controllers/string/StringControllerElement.java) |
| `ListOptionImpl` | class | ListOption<T> | Internal | 53 | 486 | [ListOptionImpl.java](dev/isxander/yacl3/impl/ListOptionImpl.java) |
| `GsonConfigSerializer` | class | ConfigSerializer | — | 15 | 308 | [GsonConfigSerializer.java](dev/isxander/yacl3/config/v2/impl/serializer/GsonConfigSerializer.java) |
| `InstantStateManager` | class | StateManager<T> | — | 10 | 68 | [InstantStateManager.java](dev/isxander/yacl3/impl/InstantStateManager.java) |
| `ConfigCategoryImpl` | class | ConfigCategory | Internal | 17 | 173 | [ConfigCategoryImpl.java](dev/isxander/yacl3/impl/ConfigCategoryImpl.java) |
| `OptionImpl` | class | Option<T> | Internal | 36 | 353 | [OptionImpl.java](dev/isxander/yacl3/impl/OptionImpl.java) |
| `GsonConfigInstance` | class | ConfigInstance | — | 15 | 178 | [GsonConfigInstance.java](dev/isxander/yacl3/config/GsonConfigInstance.java) |
| `ColorController` | class | StringControllerElement | — | 29 | 347 | [ColorController.java](dev/isxander/yacl3/gui/controllers/ColorController.java) |
| `YetAnotherConfigLibImpl` | class | YetAnotherConfigLib | Internal | 11 | 122 | [YetAnotherConfigLibImpl.java](dev/isxander/yacl3/impl/YetAnotherConfigLibImpl.java) |
| `ButtonOptionImpl` | class | ButtonOption | Internal | 29 | 212 | [ButtonOptionImpl.java](dev/isxander/yacl3/impl/ButtonOptionImpl.java) |
| `ImmutableStateManager` | class | StateManager<T> | — | 9 | 53 | [ImmutableStateManager.java](dev/isxander/yacl3/impl/ImmutableStateManager.java) |
| `SimpleStateManager` | class | StateManager<T> | — | 9 | 66 | [SimpleStateManager.java](dev/isxander/yacl3/impl/SimpleStateManager.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 18 | [MinecraftMixin.java](dev/isxander/yacl3/mixin/MinecraftMixin.java) |
| `GuiGraphicsMixin` | class | GuiRenderStateSink | Mixin | 0 | 22 | [GuiGraphicsMixin.java](dev/isxander/yacl3/mixin/GuiGraphicsMixin.java) |
| `AbstractSelectionListAccessor` | interface | — | Mixin | 0 | 9 | [AbstractSelectionListAccessor.java](dev/isxander/yacl3/mixin/AbstractSelectionListAccessor.java) |
| `OptionInstanceAccessor` | interface | — | Internal、Mixin | 0 | 14 | [OptionInstanceAccessor.java](dev/isxander/yacl3/mixin/OptionInstanceAccessor.java) |
| `TabNavigationBarAccessor` | interface | — | Mixin | 0 | 29 | [TabNavigationBarAccessor.java](dev/isxander/yacl3/mixin/TabNavigationBarAccessor.java) |
| `AbstractSelectionListMixin` | class | — | Mixin | 2 | 24 | [AbstractSelectionListMixin.java](dev/isxander/yacl3/mixin/AbstractSelectionListMixin.java) |
| `OptionFactoryRegistry` | class | — | — | 2 | 77 | [OptionFactoryRegistry.java](dev/isxander/yacl3/config/v2/impl/autogen/OptionFactoryRegistry.java) |
| `ElementListWidgetExt` | class | LayoutElement | — | 23 | 214 | [ElementListWidgetExt.java](dev/isxander/yacl3/gui/ElementListWidgetExt.java) |
| `AbstractDropdownControllerElement` | class | StringControllerElement | — | 22 | 220 | [AbstractDropdownControllerElement.java](dev/isxander/yacl3/gui/controllers/dropdown/AbstractDropdownControllerElement.java) |
| `DropdownWidget` | class | ControllerPopupWidget | — | 22 | 216 | [DropdownWidget.java](dev/isxander/yacl3/gui/controllers/dropdown/DropdownWidget.java) |
| `ListOptionEntryImpl` | class | ListOptionEntry<T> | Internal | 25 | 168 | [ListOptionEntryImpl.java](dev/isxander/yacl3/impl/ListOptionEntryImpl.java) |
| `ConfigInstance` | class | — | — | 6 | 43 | [ConfigInstance.java](dev/isxander/yacl3/config/ConfigInstance.java) |
| `CodecConfig` | class | EntryAddable | Experimental | 8 | 71 | [CodecConfig.java](dev/isxander/yacl3/config/v3/CodecConfig.java) |
| `AbstractConfigEntry` | class | AbstractReadonlyConfigEntry | Experimental | 5 | 47 | [AbstractConfigEntry.java](dev/isxander/yacl3/config/v3/AbstractConfigEntry.java) |
| `JsonFileCodecConfig` | class | — | Experimental | 5 | 93 | [JsonFileCodecConfig.java](dev/isxander/yacl3/config/v3/JsonFileCodecConfig.java) |
| `ConfigFieldImpl` | class | ConfigField<T> | — | 6 | 83 | [ConfigFieldImpl.java](dev/isxander/yacl3/config/v2/impl/ConfigFieldImpl.java) |
| `StateManager` | interface | — | — | 0 | 71 | [StateManager.java](dev/isxander/yacl3/api/StateManager.java) |
| `GuiUtils` | class | — | — | 17 | 151 | [GuiUtils.java](dev/isxander/yacl3/gui/utils/GuiUtils.java) |
| `LabelController` | class | AbstractWidget | — | 19 | 210 | [LabelController.java](dev/isxander/yacl3/gui/controllers/LabelController.java) |
| `PopupControllerScreen` | class | Screen | — | 11 | 76 | [PopupControllerScreen.java](dev/isxander/yacl3/gui/controllers/PopupControllerScreen.java) |
| `ControllerWidget` | class | — | — | 22 | 157 | [ControllerWidget.java](dev/isxander/yacl3/gui/controllers/ControllerWidget.java) |
| `HiddenNameListOptionEntry` | class | ListOptionEntry<T> | — | 20 | 134 | [HiddenNameListOptionEntry.java](dev/isxander/yacl3/impl/HiddenNameListOptionEntry.java) |
| `DimensionIntegerImpl` | class | MutableDimension<Integer> | — | 22 | 116 | [DimensionIntegerImpl.java](dev/isxander/yacl3/impl/utils/DimensionIntegerImpl.java) |
| `ConfigEntry` | @interface | — | Retention、Target | 0 | 13 | [ConfigEntry.java](dev/isxander/yacl3/config/ConfigEntry.java) |
| `AbstractReadonlyConfigEntry` | class | ReadonlyConfigEntry<T> | Experimental | 4 | 35 | [AbstractReadonlyConfigEntry.java](dev/isxander/yacl3/config/v3/AbstractReadonlyConfigEntry.java) |
| `CodecConfigEntryImpl` | class | AbstractConfigEntry | Experimental | 2 | 41 | [CodecConfigEntryImpl.java](dev/isxander/yacl3/config/v3/CodecConfigEntryImpl.java) |
| `ReadonlyConfigEntry` | interface | — | Experimental | 0 | 28 | [ReadonlyConfigEntry.java](dev/isxander/yacl3/config/v3/ReadonlyConfigEntry.java) |
| `ConfigEntry` | interface | — | Experimental | 0 | 36 | [ConfigEntry.java](dev/isxander/yacl3/config/v3/ConfigEntry.java) |
| `ChildConfigEntryImpl` | class | — | Experimental | 3 | 44 | [ChildConfigEntryImpl.java](dev/isxander/yacl3/config/v3/ChildConfigEntryImpl.java) |
| `ConfigField` | interface | — | — | 0 | 17 | [ConfigField.java](dev/isxander/yacl3/config/v2/api/ConfigField.java) |
| `ConfigSerializer` | class | — | — | 3 | 31 | [ConfigSerializer.java](dev/isxander/yacl3/config/v2/api/ConfigSerializer.java) |
| `GsonConfigSerializerBuilder` | interface | — | — | 0 | 28 | [GsonConfigSerializerBuilder.java](dev/isxander/yacl3/config/v2/api/serializer/GsonConfigSerializerBuilder.java) |
| `YACLConfig` | class | — | — | 0 | 17 | [YACLConfig.java](dev/isxander/yacl3/platform/YACLConfig.java) |
| `ConfigCategory` | interface | — | — | 0 | 69 | [ConfigCategory.java](dev/isxander/yacl3/api/ConfigCategory.java) |
| `YetAnotherConfigLib` | interface | — | — | 0 | 83 | [YetAnotherConfigLib.java](dev/isxander/yacl3/api/YetAnotherConfigLib.java) |
| `OptionDescriptionWidget` | class | net.minecraft.client.gui.components. | — | 10 | 207 | [OptionDescriptionWidget.java](dev/isxander/yacl3/gui/OptionDescriptionWidget.java) |
| `ListHolderWidget` | class | ContainerEventHandler | — | 16 | 101 | [ListHolderWidget.java](dev/isxander/yacl3/gui/tab/ListHolderWidget.java) |
| `SliderControllerElement` | class | ControllerWidget | — | 17 | 164 | [SliderControllerElement.java](dev/isxander/yacl3/gui/controllers/slider/SliderControllerElement.java) |
| `ItemController` | class | AbstractDropdownController | — | 6 | 48 | [ItemController.java](dev/isxander/yacl3/gui/controllers/dropdown/ItemController.java) |

## Mixin（7 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ContainerEventHandlerMixin` | `ContainerEventHandler` | [ContainerEventHandlerMixin.java](dev/isxander/yacl3/mixin/ContainerEventHandlerMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](dev/isxander/yacl3/mixin/MinecraftMixin.java) |
| `GuiGraphicsMixin` | `GuiGraphics` | [GuiGraphicsMixin.java](dev/isxander/yacl3/mixin/GuiGraphicsMixin.java) |
| `AbstractSelectionListAccessor` | `AbstractSelectionListAccessor` | [AbstractSelectionListAccessor.java](dev/isxander/yacl3/mixin/AbstractSelectionListAccessor.java) |
| `OptionInstanceAccessor` | `OptionInstanceAccessor` | [OptionInstanceAccessor.java](dev/isxander/yacl3/mixin/OptionInstanceAccessor.java) |
| `TabNavigationBarAccessor` | `TabNavigationBarAccessor` | [TabNavigationBarAccessor.java](dev/isxander/yacl3/mixin/TabNavigationBarAccessor.java) |
| `AbstractSelectionListMixin` | `AbstractSelectionList` | [AbstractSelectionListMixin.java](dev/isxander/yacl3/mixin/AbstractSelectionListMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigClassHandlerImpl` | — | [ConfigClassHandlerImpl.java](dev/isxander/yacl3/config/v2/impl/ConfigClassHandlerImpl.java) |
| `ConfigClassHandler` | — | [ConfigClassHandler.java](dev/isxander/yacl3/config/v2/api/ConfigClassHandler.java) |
| `GsonConfigSerializer` | — | [GsonConfigSerializer.java](dev/isxander/yacl3/config/v2/impl/serializer/GsonConfigSerializer.java) |
| `ConfigCategoryImpl` | Internal | [ConfigCategoryImpl.java](dev/isxander/yacl3/impl/ConfigCategoryImpl.java) |
| `GsonConfigInstance` | — | [GsonConfigInstance.java](dev/isxander/yacl3/config/GsonConfigInstance.java) |
| `YetAnotherConfigLibImpl` | Internal | [YetAnotherConfigLibImpl.java](dev/isxander/yacl3/impl/YetAnotherConfigLibImpl.java) |
| `ConfigInstance` | — | [ConfigInstance.java](dev/isxander/yacl3/config/ConfigInstance.java) |
| `CodecConfig` | Experimental | [CodecConfig.java](dev/isxander/yacl3/config/v3/CodecConfig.java) |
| `AbstractConfigEntry` | Experimental | [AbstractConfigEntry.java](dev/isxander/yacl3/config/v3/AbstractConfigEntry.java) |
| `JsonFileCodecConfig` | Experimental | [JsonFileCodecConfig.java](dev/isxander/yacl3/config/v3/JsonFileCodecConfig.java) |
| `ConfigFieldImpl` | — | [ConfigFieldImpl.java](dev/isxander/yacl3/config/v2/impl/ConfigFieldImpl.java) |
| `ConfigEntry` | Retention;Target | [ConfigEntry.java](dev/isxander/yacl3/config/ConfigEntry.java) |
| `AbstractReadonlyConfigEntry` | Experimental | [AbstractReadonlyConfigEntry.java](dev/isxander/yacl3/config/v3/AbstractReadonlyConfigEntry.java) |
| `CodecConfigEntryImpl` | Experimental | [CodecConfigEntryImpl.java](dev/isxander/yacl3/config/v3/CodecConfigEntryImpl.java) |
| `ReadonlyConfigEntry` | Experimental | [ReadonlyConfigEntry.java](dev/isxander/yacl3/config/v3/ReadonlyConfigEntry.java) |
| `ConfigEntry` | Experimental | [ConfigEntry.java](dev/isxander/yacl3/config/v3/ConfigEntry.java) |
| `ChildConfigEntryImpl` | Experimental | [ChildConfigEntryImpl.java](dev/isxander/yacl3/config/v3/ChildConfigEntryImpl.java) |
| `ConfigField` | — | [ConfigField.java](dev/isxander/yacl3/config/v2/api/ConfigField.java) |
| `ConfigSerializer` | — | [ConfigSerializer.java](dev/isxander/yacl3/config/v2/api/ConfigSerializer.java) |
| `GsonConfigSerializerBuilder` | — | [GsonConfigSerializerBuilder.java](dev/isxander/yacl3/config/v2/api/serializer/GsonConfigSerializerBuilder.java) |
| `YACLConfig` | — | [YACLConfig.java](dev/isxander/yacl3/platform/YACLConfig.java) |
| `ConfigCategory` | — | [ConfigCategory.java](dev/isxander/yacl3/api/ConfigCategory.java) |
| `YetAnotherConfigLib` | — | [YetAnotherConfigLib.java](dev/isxander/yacl3/api/YetAnotherConfigLib.java) |

## 按包浏览

- `dev.isxander.yacl3.api`（18 类）
- `dev.isxander.yacl3.api.controller`（22 类）
- `dev.isxander.yacl3.api.utils`（3 类）
- `dev.isxander.yacl3.config`（3 类）
- `dev.isxander.yacl3.config.util`（1 类）
- `dev.isxander.yacl3.config.v2.api`（7 类）
- `dev.isxander.yacl3.config.v2.api.autogen`（28 类）
- `dev.isxander.yacl3.config.v2.api.serializer`（1 类）
- `dev.isxander.yacl3.config.v2.impl`（4 类）
- `dev.isxander.yacl3.config.v2.impl.autogen`（23 类）
- `dev.isxander.yacl3.config.v2.impl.serializer`（1 类）
- `dev.isxander.yacl3.config.v3`（9 类）
- `dev.isxander.yacl3.debug`（1 类）
- `dev.isxander.yacl3.gui`（13 类）
- `dev.isxander.yacl3.gui.controllers`（10 类）
- `dev.isxander.yacl3.gui.controllers.cycling`（4 类）
- `dev.isxander.yacl3.gui.controllers.dropdown`（9 类）
- `dev.isxander.yacl3.gui.controllers.slider`（6 类）
- `dev.isxander.yacl3.gui.controllers.string`（3 类）
- `dev.isxander.yacl3.gui.controllers.string.number`（5 类）
- `dev.isxander.yacl3.gui.image`（4 类）
- `dev.isxander.yacl3.gui.image.impl`（3 类）
- `dev.isxander.yacl3.gui.render`（4 类）
- `dev.isxander.yacl3.gui.tab`（3 类）
- `dev.isxander.yacl3.gui.utils`（5 类）
- `dev.isxander.yacl3.impl`（18 类）
- `dev.isxander.yacl3.impl.controller`（18 类）
- `dev.isxander.yacl3.impl.utils`（2 类）
- `dev.isxander.yacl3.mixin`（7 类）
- `dev.isxander.yacl3.platform`（4 类）
