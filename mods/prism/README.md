# Prism（`prism`）

> jar：`[前置]Prism.jar` ｜ 版本：`1.0.11` ｜ 作者：Grend
> 规模：**18 个类 / 9 个包 / 1266 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.anthonyhilyard.prism.util` | 6 | `ConfigHelper`、`ColorUtil`、`IColor` |
| `com.anthonyhilyard.prism.events` | 3 | `ToggleableEvent`、`EventFactory`、`Event` |
| `com.anthonyhilyard.prism.text` | 2 | `DynamicColor`、`TextColors` |
| `com.anthonyhilyard.prism.mixin` | 2 | `TextColorMixin`、`MinecraftMixin` |
| `com.anthonyhilyard.prism.neoforge` | 1 | `PrismNeoForge` |
| `com.anthonyhilyard.prism.events.client` | 1 | `RenderTickEvent` |
| `com.anthonyhilyard.prism.item` | 1 | `ItemColors` |
| `architectury_inject_Prism_common_7bfb1338128648d8914d12f486c06d25_84bf796f65b358287dc9fa8b6d5a1bcfba2413ac56b2bffcaf8698647f4c9cfdPrism1211common1011devjar` | 1 | `PlatformMethods` |
| `com.anthonyhilyard.prism` | 1 | `Prism` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 18）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PrismNeoForge` | class | — | Mod | 0 | 8 | [PrismNeoForge.java](com/anthonyhilyard/prism/neoforge/PrismNeoForge.java) |
| `ConfigHelper` | class | — | — | 10 | 340 | [ConfigHelper.java](com/anthonyhilyard/prism/util/ConfigHelper.java) |
| `DynamicColor` | class | TextColor | — | 32 | 216 | [DynamicColor.java](com/anthonyhilyard/prism/text/DynamicColor.java) |
| `TextColorMixin` | class | IColor | Mixin | 7 | 72 | [TextColorMixin.java](com/anthonyhilyard/prism/mixin/TextColorMixin.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 22 | [MinecraftMixin.java](com/anthonyhilyard/prism/mixin/MinecraftMixin.java) |
| `ToggleableEvent` | class | — | — | 5 | 46 | [ToggleableEvent.java](com/anthonyhilyard/prism/events/ToggleableEvent.java) |
| `EventFactory` | class | — | — | 2 | 24 | [EventFactory.java](com/anthonyhilyard/prism/events/EventFactory.java) |
| `Event` | class | — | — | 4 | 46 | [Event.java](com/anthonyhilyard/prism/events/Event.java) |
| `RenderTickEvent` | class | — | — | 0 | 19 | [RenderTickEvent.java](com/anthonyhilyard/prism/events/client/RenderTickEvent.java) |
| `ColorUtil` | class | — | — | 11 | 140 | [ColorUtil.java](com/anthonyhilyard/prism/util/ColorUtil.java) |
| `ItemColors` | class | FormattedCharSink | — | 3 | 88 | [ItemColors.java](com/anthonyhilyard/prism/item/ItemColors.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_Prism_common_7bfb1338128648d8914d12f486c06d25_84bf796f65b358287dc9fa8b6d5a1bcfba2413ac56b2bffcaf8698647f4c9cfdPrism1211common1011devjar/PlatformMethods.java) |
| `Prism` | class | — | — | 0 | 10 | [Prism.java](com/anthonyhilyard/prism/Prism.java) |
| `TextColors` | class | — | — | 1 | 29 | [TextColors.java](com/anthonyhilyard/prism/text/TextColors.java) |
| `IColor` | interface | — | — | 0 | 13 | [IColor.java](com/anthonyhilyard/prism/util/IColor.java) |
| `MinecraftColors` | class | — | — | 1 | 33 | [MinecraftColors.java](com/anthonyhilyard/prism/util/MinecraftColors.java) |
| `WebColors` | class | — | — | 4 | 57 | [WebColors.java](com/anthonyhilyard/prism/util/WebColors.java) |
| `ImageAnalysis` | class | — | — | 2 | 95 | [ImageAnalysis.java](com/anthonyhilyard/prism/util/ImageAnalysis.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `TextColorMixin` | `TextColor` | [TextColorMixin.java](com/anthonyhilyard/prism/mixin/TextColorMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/anthonyhilyard/prism/mixin/MinecraftMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigHelper` | — | [ConfigHelper.java](com/anthonyhilyard/prism/util/ConfigHelper.java) |

## 全部类（18）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PrismNeoForge` | class | — | Mod | 0 | 8 | [PrismNeoForge.java](com/anthonyhilyard/prism/neoforge/PrismNeoForge.java) |
| `ConfigHelper` | class | — | — | 10 | 340 | [ConfigHelper.java](com/anthonyhilyard/prism/util/ConfigHelper.java) |
| `DynamicColor` | class | TextColor | — | 32 | 216 | [DynamicColor.java](com/anthonyhilyard/prism/text/DynamicColor.java) |
| `TextColorMixin` | class | IColor | Mixin | 7 | 72 | [TextColorMixin.java](com/anthonyhilyard/prism/mixin/TextColorMixin.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 22 | [MinecraftMixin.java](com/anthonyhilyard/prism/mixin/MinecraftMixin.java) |
| `ToggleableEvent` | class | — | — | 5 | 46 | [ToggleableEvent.java](com/anthonyhilyard/prism/events/ToggleableEvent.java) |
| `EventFactory` | class | — | — | 2 | 24 | [EventFactory.java](com/anthonyhilyard/prism/events/EventFactory.java) |
| `Event` | class | — | — | 4 | 46 | [Event.java](com/anthonyhilyard/prism/events/Event.java) |
| `RenderTickEvent` | class | — | — | 0 | 19 | [RenderTickEvent.java](com/anthonyhilyard/prism/events/client/RenderTickEvent.java) |
| `ColorUtil` | class | — | — | 11 | 140 | [ColorUtil.java](com/anthonyhilyard/prism/util/ColorUtil.java) |
| `ItemColors` | class | FormattedCharSink | — | 3 | 88 | [ItemColors.java](com/anthonyhilyard/prism/item/ItemColors.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_Prism_common_7bfb1338128648d8914d12f486c06d25_84bf796f65b358287dc9fa8b6d5a1bcfba2413ac56b2bffcaf8698647f4c9cfdPrism1211common1011devjar/PlatformMethods.java) |
| `Prism` | class | — | — | 0 | 10 | [Prism.java](com/anthonyhilyard/prism/Prism.java) |
| `TextColors` | class | — | — | 1 | 29 | [TextColors.java](com/anthonyhilyard/prism/text/TextColors.java) |
| `IColor` | interface | — | — | 0 | 13 | [IColor.java](com/anthonyhilyard/prism/util/IColor.java) |
| `MinecraftColors` | class | — | — | 1 | 33 | [MinecraftColors.java](com/anthonyhilyard/prism/util/MinecraftColors.java) |
| `WebColors` | class | — | — | 4 | 57 | [WebColors.java](com/anthonyhilyard/prism/util/WebColors.java) |
| `ImageAnalysis` | class | — | — | 2 | 95 | [ImageAnalysis.java](com/anthonyhilyard/prism/util/ImageAnalysis.java) |
