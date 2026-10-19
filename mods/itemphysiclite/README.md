# ItemPhysicLite（`itemphysiclite`）

> jar：`[物品物理掉落]ItemPhysicLite.jar` ｜ 版本：`1.6.8` ｜ 作者：CreativeMD
> 规模：**4 个类 / 2 个包 / 287 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`、`creativecore`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `team.creative.itemphysiclite` | 2 | `ItemPhysicLite`、`ItemPhysicLiteConfig` |
| `team.creative.itemphysiclite.mixin` | 2 | `ItemEntityRendererMixin`、`EntityAccessor` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 4）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ItemPhysicLite` | class | ClientLoader | Mod | 7 | 213 | [ItemPhysicLite.java](team/creative/itemphysiclite/ItemPhysicLite.java) |
| `ItemEntityRendererMixin` | class | EntityRenderer | Mixin | 1 | 44 | [ItemEntityRendererMixin.java](team/creative/itemphysiclite/mixin/ItemEntityRendererMixin.java) |
| `EntityAccessor` | interface | — | Mixin | 0 | 13 | [EntityAccessor.java](team/creative/itemphysiclite/mixin/EntityAccessor.java) |
| `ItemPhysicLiteConfig` | class | — | — | 0 | 17 | [ItemPhysicLiteConfig.java](team/creative/itemphysiclite/ItemPhysicLiteConfig.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemEntityRendererMixin` | `ItemEntityRenderer` | [ItemEntityRendererMixin.java](team/creative/itemphysiclite/mixin/ItemEntityRendererMixin.java) |
| `EntityAccessor` | `EntityAccessor` | [EntityAccessor.java](team/creative/itemphysiclite/mixin/EntityAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ItemPhysicLiteConfig` | — | [ItemPhysicLiteConfig.java](team/creative/itemphysiclite/ItemPhysicLiteConfig.java) |

## 全部类（4）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ItemPhysicLite` | class | ClientLoader | Mod | 7 | 213 | [ItemPhysicLite.java](team/creative/itemphysiclite/ItemPhysicLite.java) |
| `ItemEntityRendererMixin` | class | EntityRenderer | Mixin | 1 | 44 | [ItemEntityRendererMixin.java](team/creative/itemphysiclite/mixin/ItemEntityRendererMixin.java) |
| `EntityAccessor` | interface | — | Mixin | 0 | 13 | [EntityAccessor.java](team/creative/itemphysiclite/mixin/EntityAccessor.java) |
| `ItemPhysicLiteConfig` | class | — | — | 0 | 17 | [ItemPhysicLiteConfig.java](team/creative/itemphysiclite/ItemPhysicLiteConfig.java) |
