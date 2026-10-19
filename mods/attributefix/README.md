# AttributeFix（`attributefix`）

> jar：`[属性修复]attributefix.jar` ｜ 版本：`21.1.3` ｜ 作者：Darkhax
> 规模：**5 个类 / 4 个包 / 154 行** ｜ 事件处理类 1 ｜ Mixin 1 ｜ 注册/管理类 0

**官方描述**：Extends the maximum attribute ranges to allow for higher values.

**依赖**：`neoforge`、`minecraft`、`bookshelf`、`prickle`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.darkhax.attributefix.common.impl` | 2 | `Constants`、`AttributeFixMod` |
| `net.darkhax.attributefix.impl` | 1 | `NeoForgeMod` |
| `net.darkhax.attributefix.common.mixin` | 1 | `AccessorRangedAttribute` |
| `net.darkhax.attributefix.common.impl.config` | 1 | `RangeConfig` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 5）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeMod` | class | — | EventBusSubscriber、Mod | 1 | 18 | [NeoForgeMod.java](net/darkhax/attributefix/impl/NeoForgeMod.java) |
| `AccessorRangedAttribute` | interface | — | Mixin | 0 | 18 | [AccessorRangedAttribute.java](net/darkhax/attributefix/common/mixin/AccessorRangedAttribute.java) |
| `RangeConfig` | class | — | — | 1 | 63 | [RangeConfig.java](net/darkhax/attributefix/common/impl/config/RangeConfig.java) |
| `Constants` | class | — | — | 1 | 16 | [Constants.java](net/darkhax/attributefix/common/impl/Constants.java) |
| `AttributeFixMod` | class | — | — | 2 | 39 | [AttributeFixMod.java](net/darkhax/attributefix/common/impl/AttributeFixMod.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoForgeMod` | 1 | `net.darkhax.attributefix.impl` | [NeoForgeMod.java](net/darkhax/attributefix/impl/NeoForgeMod.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AccessorRangedAttribute` | `AccessorRangedAttribute` | [AccessorRangedAttribute.java](net/darkhax/attributefix/common/mixin/AccessorRangedAttribute.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `RangeConfig` | — | [RangeConfig.java](net/darkhax/attributefix/common/impl/config/RangeConfig.java) |

## 全部类（5）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeMod` | class | — | EventBusSubscriber、Mod | 1 | 18 | [NeoForgeMod.java](net/darkhax/attributefix/impl/NeoForgeMod.java) |
| `AccessorRangedAttribute` | interface | — | Mixin | 0 | 18 | [AccessorRangedAttribute.java](net/darkhax/attributefix/common/mixin/AccessorRangedAttribute.java) |
| `RangeConfig` | class | — | — | 1 | 63 | [RangeConfig.java](net/darkhax/attributefix/common/impl/config/RangeConfig.java) |
| `Constants` | class | — | — | 1 | 16 | [Constants.java](net/darkhax/attributefix/common/impl/Constants.java) |
| `AttributeFixMod` | class | — | — | 2 | 39 | [AttributeFixMod.java](net/darkhax/attributefix/common/impl/AttributeFixMod.java) |
