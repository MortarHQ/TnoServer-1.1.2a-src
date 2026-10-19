# PrickleMC（`prickle`）

> jar：`[前置]prickle-neoforge-1.21.1-21.1.9.jar` ｜ 版本：`21.1.8` ｜ 作者：Darkhax
> 规模：**37 个类 / 11 个包 / 1643 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 1

**官方描述**：Bringing Prickle, the JSON based configuration file format to Minecraft.

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.darkhax.pricklemc.common.api.annotations` | 8 | `Array`、`RangedFloat`、`Regex` |
| `net.darkhax.pricklemc.common.api.config.property` | 7 | `ConfigObjectProperty`、`IConfigProperty`、`ObjectProperty` |
| `net.darkhax.pricklemc.common.api.config.comment` | 5 | `IComment`、`WrappedComment`、`ICommentResolver` |
| `net.darkhax.pricklemc.common.api.config.property.array` | 4 | `CollectionArrayProperty`、`AbstractArrayProperty`、`ArrayProperty` |
| `net.darkhax.pricklemc.common.api.config` | 3 | `ConfigManager`、`ConfigObjectSerializer`、`PropertyResolver` |
| `net.darkhax.pricklemc.common.api.util` | 3 | `NumberUtils`、`CachedSupplier`、`IPlatformHelper` |
| `net.darkhax.pricklemc.common.impl` | 2 | `PrickleMod`、`Constants` |
| `net.darkhax.pricklemc.common.impl.config.property` | 2 | `CodecProperty`、`MinecraftPropertyPlugin` |
| `net.darkhax.pricklemc.neoforge.impl` | 1 | `NeoForgeMod` |
| `net.darkhax.pricklemc.neoforge.impl.util` | 1 | `NeoForgePlatformHelper` |
| `net.darkhax.pricklemc.common.api.services` | 1 | `Services` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigManager` | class | — | — | 13 | 220 | [ConfigManager.java](net/darkhax/pricklemc/common/api/config/ConfigManager.java) |
| `NeoForgeMod` | class | — | Mod | 0 | 13 | [NeoForgeMod.java](net/darkhax/pricklemc/neoforge/impl/NeoForgeMod.java) |
| `ConfigObjectProperty` | class | ObjectProperty | — | 5 | 68 | [ConfigObjectProperty.java](net/darkhax/pricklemc/common/api/config/property/ConfigObjectProperty.java) |
| `ConfigObjectSerializer` | class | — | — | 4 | 95 | [ConfigObjectSerializer.java](net/darkhax/pricklemc/common/api/config/ConfigObjectSerializer.java) |
| `IConfigProperty` | interface | — | — | 0 | 18 | [IConfigProperty.java](net/darkhax/pricklemc/common/api/config/property/IConfigProperty.java) |
| `ObjectProperty` | class | IConfigProperty<T> | — | 12 | 124 | [ObjectProperty.java](net/darkhax/pricklemc/common/api/config/property/ObjectProperty.java) |
| `NumberUtils` | class | — | — | 6 | 51 | [NumberUtils.java](net/darkhax/pricklemc/common/api/util/NumberUtils.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 2 | 18 | [NeoForgePlatformHelper.java](net/darkhax/pricklemc/neoforge/impl/util/NeoForgePlatformHelper.java) |
| `RangedProperty` | class | ObjectProperty | — | 5 | 127 | [RangedProperty.java](net/darkhax/pricklemc/common/api/config/property/RangedProperty.java) |
| `CollectionArrayProperty` | class | — | — | 5 | 62 | [CollectionArrayProperty.java](net/darkhax/pricklemc/common/api/config/property/array/CollectionArrayProperty.java) |
| `AbstractArrayProperty` | class | ObjectProperty | — | 8 | 68 | [AbstractArrayProperty.java](net/darkhax/pricklemc/common/api/config/property/array/AbstractArrayProperty.java) |
| `ArrayProperty` | class | AbstractArrayProperty | — | 5 | 62 | [ArrayProperty.java](net/darkhax/pricklemc/common/api/config/property/array/ArrayProperty.java) |
| `CachedSupplier` | class | Supplier<T> | — | 9 | 64 | [CachedSupplier.java](net/darkhax/pricklemc/common/api/util/CachedSupplier.java) |
| `IPlatformHelper` | interface | — | — | 0 | 15 | [IPlatformHelper.java](net/darkhax/pricklemc/common/api/util/IPlatformHelper.java) |
| `PrickleMod` | class | — | — | 3 | 32 | [PrickleMod.java](net/darkhax/pricklemc/common/impl/PrickleMod.java) |
| `Constants` | class | — | — | 1 | 17 | [Constants.java](net/darkhax/pricklemc/common/impl/Constants.java) |
| `CodecProperty` | class | ObjectProperty | — | 4 | 80 | [CodecProperty.java](net/darkhax/pricklemc/common/impl/config/property/CodecProperty.java) |
| `MinecraftPropertyPlugin` | class | IDefaultPropertyAdapters | — | 1 | 20 | [MinecraftPropertyPlugin.java](net/darkhax/pricklemc/common/impl/config/property/MinecraftPropertyPlugin.java) |
| `PropertyResolver` | class | — | — | 4 | 81 | [PropertyResolver.java](net/darkhax/pricklemc/common/api/config/PropertyResolver.java) |
| `IComment` | interface | — | — | 0 | 6 | [IComment.java](net/darkhax/pricklemc/common/api/config/comment/IComment.java) |
| `WrappedComment` | class | Comment | — | 2 | 61 | [WrappedComment.java](net/darkhax/pricklemc/common/api/config/comment/WrappedComment.java) |
| `ICommentResolver` | interface | — | — | 0 | 16 | [ICommentResolver.java](net/darkhax/pricklemc/common/api/config/comment/ICommentResolver.java) |
| `CommentTypeAdapter` | class | JsonSerializer<IComment> | — | 2 | 53 | [CommentTypeAdapter.java](net/darkhax/pricklemc/common/api/config/comment/CommentTypeAdapter.java) |
| `Comment` | class | IComment | — | 1 | 15 | [Comment.java](net/darkhax/pricklemc/common/api/config/comment/Comment.java) |
| `IPropertyAdapter` | interface | — | — | 0 | 13 | [IPropertyAdapter.java](net/darkhax/pricklemc/common/api/config/property/IPropertyAdapter.java) |
| `RegexStringProperty` | class | ObjectProperty | — | 3 | 49 | [RegexStringProperty.java](net/darkhax/pricklemc/common/api/config/property/RegexStringProperty.java) |
| `IDefaultPropertyAdapters` | interface | — | — | 0 | 8 | [IDefaultPropertyAdapters.java](net/darkhax/pricklemc/common/api/config/property/IDefaultPropertyAdapters.java) |
| `ArraySettings` | class | — | — | 3 | 35 | [ArraySettings.java](net/darkhax/pricklemc/common/api/config/property/array/ArraySettings.java) |
| `Array` | @interface | — | Retention、Target | 0 | 17 | [Array.java](net/darkhax/pricklemc/common/api/annotations/Array.java) |
| `RangedFloat` | @interface | — | Retention、Target | 0 | 15 | [RangedFloat.java](net/darkhax/pricklemc/common/api/annotations/RangedFloat.java) |
| `Regex` | @interface | — | Retention、Target | 0 | 13 | [Regex.java](net/darkhax/pricklemc/common/api/annotations/Regex.java) |
| `RangedDouble` | @interface | — | Retention、Target | 0 | 15 | [RangedDouble.java](net/darkhax/pricklemc/common/api/annotations/RangedDouble.java) |
| `RangedInt` | @interface | — | Retention、Target | 0 | 15 | [RangedInt.java](net/darkhax/pricklemc/common/api/annotations/RangedInt.java) |
| `Adapter` | @interface | — | Retention、Target | 0 | 14 | [Adapter.java](net/darkhax/pricklemc/common/api/annotations/Adapter.java) |
| `RangedLong` | @interface | — | Retention、Target | 0 | 15 | [RangedLong.java](net/darkhax/pricklemc/common/api/annotations/RangedLong.java) |
| `Value` | @interface | — | Retention、Target | 0 | 19 | [Value.java](net/darkhax/pricklemc/common/api/annotations/Value.java) |
| `Services` | class | — | — | 2 | 29 | [Services.java](net/darkhax/pricklemc/common/api/services/Services.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigManager` | — | [ConfigManager.java](net/darkhax/pricklemc/common/api/config/ConfigManager.java) |
| `ConfigObjectProperty` | — | [ConfigObjectProperty.java](net/darkhax/pricklemc/common/api/config/property/ConfigObjectProperty.java) |
| `ConfigObjectSerializer` | — | [ConfigObjectSerializer.java](net/darkhax/pricklemc/common/api/config/ConfigObjectSerializer.java) |
| `IConfigProperty` | — | [IConfigProperty.java](net/darkhax/pricklemc/common/api/config/property/IConfigProperty.java) |

## 全部类（37）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigManager` | class | — | — | 13 | 220 | [ConfigManager.java](net/darkhax/pricklemc/common/api/config/ConfigManager.java) |
| `NeoForgeMod` | class | — | Mod | 0 | 13 | [NeoForgeMod.java](net/darkhax/pricklemc/neoforge/impl/NeoForgeMod.java) |
| `ConfigObjectProperty` | class | ObjectProperty | — | 5 | 68 | [ConfigObjectProperty.java](net/darkhax/pricklemc/common/api/config/property/ConfigObjectProperty.java) |
| `ConfigObjectSerializer` | class | — | — | 4 | 95 | [ConfigObjectSerializer.java](net/darkhax/pricklemc/common/api/config/ConfigObjectSerializer.java) |
| `IConfigProperty` | interface | — | — | 0 | 18 | [IConfigProperty.java](net/darkhax/pricklemc/common/api/config/property/IConfigProperty.java) |
| `ObjectProperty` | class | IConfigProperty<T> | — | 12 | 124 | [ObjectProperty.java](net/darkhax/pricklemc/common/api/config/property/ObjectProperty.java) |
| `NumberUtils` | class | — | — | 6 | 51 | [NumberUtils.java](net/darkhax/pricklemc/common/api/util/NumberUtils.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 2 | 18 | [NeoForgePlatformHelper.java](net/darkhax/pricklemc/neoforge/impl/util/NeoForgePlatformHelper.java) |
| `RangedProperty` | class | ObjectProperty | — | 5 | 127 | [RangedProperty.java](net/darkhax/pricklemc/common/api/config/property/RangedProperty.java) |
| `CollectionArrayProperty` | class | — | — | 5 | 62 | [CollectionArrayProperty.java](net/darkhax/pricklemc/common/api/config/property/array/CollectionArrayProperty.java) |
| `AbstractArrayProperty` | class | ObjectProperty | — | 8 | 68 | [AbstractArrayProperty.java](net/darkhax/pricklemc/common/api/config/property/array/AbstractArrayProperty.java) |
| `ArrayProperty` | class | AbstractArrayProperty | — | 5 | 62 | [ArrayProperty.java](net/darkhax/pricklemc/common/api/config/property/array/ArrayProperty.java) |
| `CachedSupplier` | class | Supplier<T> | — | 9 | 64 | [CachedSupplier.java](net/darkhax/pricklemc/common/api/util/CachedSupplier.java) |
| `IPlatformHelper` | interface | — | — | 0 | 15 | [IPlatformHelper.java](net/darkhax/pricklemc/common/api/util/IPlatformHelper.java) |
| `PrickleMod` | class | — | — | 3 | 32 | [PrickleMod.java](net/darkhax/pricklemc/common/impl/PrickleMod.java) |
| `Constants` | class | — | — | 1 | 17 | [Constants.java](net/darkhax/pricklemc/common/impl/Constants.java) |
| `CodecProperty` | class | ObjectProperty | — | 4 | 80 | [CodecProperty.java](net/darkhax/pricklemc/common/impl/config/property/CodecProperty.java) |
| `MinecraftPropertyPlugin` | class | IDefaultPropertyAdapters | — | 1 | 20 | [MinecraftPropertyPlugin.java](net/darkhax/pricklemc/common/impl/config/property/MinecraftPropertyPlugin.java) |
| `PropertyResolver` | class | — | — | 4 | 81 | [PropertyResolver.java](net/darkhax/pricklemc/common/api/config/PropertyResolver.java) |
| `IComment` | interface | — | — | 0 | 6 | [IComment.java](net/darkhax/pricklemc/common/api/config/comment/IComment.java) |
| `WrappedComment` | class | Comment | — | 2 | 61 | [WrappedComment.java](net/darkhax/pricklemc/common/api/config/comment/WrappedComment.java) |
| `ICommentResolver` | interface | — | — | 0 | 16 | [ICommentResolver.java](net/darkhax/pricklemc/common/api/config/comment/ICommentResolver.java) |
| `CommentTypeAdapter` | class | JsonSerializer<IComment> | — | 2 | 53 | [CommentTypeAdapter.java](net/darkhax/pricklemc/common/api/config/comment/CommentTypeAdapter.java) |
| `Comment` | class | IComment | — | 1 | 15 | [Comment.java](net/darkhax/pricklemc/common/api/config/comment/Comment.java) |
| `IPropertyAdapter` | interface | — | — | 0 | 13 | [IPropertyAdapter.java](net/darkhax/pricklemc/common/api/config/property/IPropertyAdapter.java) |
| `RegexStringProperty` | class | ObjectProperty | — | 3 | 49 | [RegexStringProperty.java](net/darkhax/pricklemc/common/api/config/property/RegexStringProperty.java) |
| `IDefaultPropertyAdapters` | interface | — | — | 0 | 8 | [IDefaultPropertyAdapters.java](net/darkhax/pricklemc/common/api/config/property/IDefaultPropertyAdapters.java) |
| `ArraySettings` | class | — | — | 3 | 35 | [ArraySettings.java](net/darkhax/pricklemc/common/api/config/property/array/ArraySettings.java) |
| `Array` | @interface | — | Retention、Target | 0 | 17 | [Array.java](net/darkhax/pricklemc/common/api/annotations/Array.java) |
| `RangedFloat` | @interface | — | Retention、Target | 0 | 15 | [RangedFloat.java](net/darkhax/pricklemc/common/api/annotations/RangedFloat.java) |
| `Regex` | @interface | — | Retention、Target | 0 | 13 | [Regex.java](net/darkhax/pricklemc/common/api/annotations/Regex.java) |
| `RangedDouble` | @interface | — | Retention、Target | 0 | 15 | [RangedDouble.java](net/darkhax/pricklemc/common/api/annotations/RangedDouble.java) |
| `RangedInt` | @interface | — | Retention、Target | 0 | 15 | [RangedInt.java](net/darkhax/pricklemc/common/api/annotations/RangedInt.java) |
| `Adapter` | @interface | — | Retention、Target | 0 | 14 | [Adapter.java](net/darkhax/pricklemc/common/api/annotations/Adapter.java) |
| `RangedLong` | @interface | — | Retention、Target | 0 | 15 | [RangedLong.java](net/darkhax/pricklemc/common/api/annotations/RangedLong.java) |
| `Value` | @interface | — | Retention、Target | 0 | 19 | [Value.java](net/darkhax/pricklemc/common/api/annotations/Value.java) |
| `Services` | class | — | — | 2 | 29 | [Services.java](net/darkhax/pricklemc/common/api/services/Services.java) |
