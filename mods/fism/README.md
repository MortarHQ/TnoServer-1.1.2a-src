# Faster Iris Shadow Mapper（`fism`）

> jar：`[优化]fism.jar` ｜ 版本：`1.0.5` ｜ 作者：maDU59_
> 规模：**11 个类 / 4 个包 / 339 行** ｜ 事件处理类 0 ｜ Mixin 5 ｜ 注册/管理类 0

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `fr.madu59.fism.mixin` | 5 | `BannerRendererAccessor`、`MapRendererMixin`、`ItemStackMixin` |
| `fr.madu59.fism.compat` | 3 | `ModCompatPlugin`、`ModCompat`、`IrisCompat` |
| `fr.madu59.fism.compat.entityculling` | 2 | `EntityCullingCompat`、`Provider` |
| `fr.madu59.fism.platform` | 1 | `PlatformHelper` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 11）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BannerRendererAccessor` | interface | — | Mixin | 0 | 20 | [BannerRendererAccessor.java](fr/madu59/fism/mixin/BannerRendererAccessor.java) |
| `MapRendererMixin` | class | — | Mixin | 0 | 42 | [MapRendererMixin.java](fr/madu59/fism/mixin/MapRendererMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 0 | 19 | [ItemStackMixin.java](fr/madu59/fism/mixin/ItemStackMixin.java) |
| `BannerRendererMixin` | class | — | Mixin | 0 | 42 | [BannerRendererMixin.java](fr/madu59/fism/mixin/BannerRendererMixin.java) |
| `AbstractSignRendererMixin` | class | — | Mixin | 0 | 19 | [AbstractSignRendererMixin.java](fr/madu59/fism/mixin/AbstractSignRendererMixin.java) |
| `EntityCullingCompat` | class | — | — | 2 | 25 | [EntityCullingCompat.java](fr/madu59/fism/compat/entityculling/EntityCullingCompat.java) |
| `ModCompatPlugin` | class | IMixinConfigPlugin | — | 7 | 39 | [ModCompatPlugin.java](fr/madu59/fism/compat/ModCompatPlugin.java) |
| `ModCompat` | class | — | — | 8 | 52 | [ModCompat.java](fr/madu59/fism/compat/ModCompat.java) |
| `IrisCompat` | class | — | — | 3 | 29 | [IrisCompat.java](fr/madu59/fism/compat/IrisCompat.java) |
| `PlatformHelper` | class | — | — | 3 | 20 | [PlatformHelper.java](fr/madu59/fism/platform/PlatformHelper.java) |
| `Provider` | class | DataProvider | — | 3 | 32 | [Provider.java](fr/madu59/fism/compat/entityculling/Provider.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BannerRendererAccessor` | `BannerRendererAccessor` | [BannerRendererAccessor.java](fr/madu59/fism/mixin/BannerRendererAccessor.java) |
| `MapRendererMixin` | `MapRenderer` | [MapRendererMixin.java](fr/madu59/fism/mixin/MapRendererMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](fr/madu59/fism/mixin/ItemStackMixin.java) |
| `BannerRendererMixin` | `BannerRenderer` | [BannerRendererMixin.java](fr/madu59/fism/mixin/BannerRendererMixin.java) |
| `AbstractSignRendererMixin` | `AbstractSignRenderer` | [AbstractSignRendererMixin.java](fr/madu59/fism/mixin/AbstractSignRendererMixin.java) |

## 全部类（11）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BannerRendererAccessor` | interface | — | Mixin | 0 | 20 | [BannerRendererAccessor.java](fr/madu59/fism/mixin/BannerRendererAccessor.java) |
| `MapRendererMixin` | class | — | Mixin | 0 | 42 | [MapRendererMixin.java](fr/madu59/fism/mixin/MapRendererMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 0 | 19 | [ItemStackMixin.java](fr/madu59/fism/mixin/ItemStackMixin.java) |
| `BannerRendererMixin` | class | — | Mixin | 0 | 42 | [BannerRendererMixin.java](fr/madu59/fism/mixin/BannerRendererMixin.java) |
| `AbstractSignRendererMixin` | class | — | Mixin | 0 | 19 | [AbstractSignRendererMixin.java](fr/madu59/fism/mixin/AbstractSignRendererMixin.java) |
| `EntityCullingCompat` | class | — | — | 2 | 25 | [EntityCullingCompat.java](fr/madu59/fism/compat/entityculling/EntityCullingCompat.java) |
| `ModCompatPlugin` | class | IMixinConfigPlugin | — | 7 | 39 | [ModCompatPlugin.java](fr/madu59/fism/compat/ModCompatPlugin.java) |
| `ModCompat` | class | — | — | 8 | 52 | [ModCompat.java](fr/madu59/fism/compat/ModCompat.java) |
| `IrisCompat` | class | — | — | 3 | 29 | [IrisCompat.java](fr/madu59/fism/compat/IrisCompat.java) |
| `PlatformHelper` | class | — | — | 3 | 20 | [PlatformHelper.java](fr/madu59/fism/platform/PlatformHelper.java) |
| `Provider` | class | DataProvider | — | 3 | 32 | [Provider.java](fr/madu59/fism/compat/entityculling/Provider.java) |
