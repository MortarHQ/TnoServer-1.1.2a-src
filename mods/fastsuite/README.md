# Fast Suite（`fastsuite`）

> jar：`[配方性能优化]FastSuite-1.21.1-6.0.7.jar` ｜ 版本：`6.0.7` ｜ 作者：Shadows_of_Fire
> 规模：**10 个类 / 3 个包 / 776 行** ｜ 事件处理类 1 ｜ Mixin 5 ｜ 注册/管理类 1

**依赖**：`minecraft`、`neoforge`、`placebo`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.shadowsoffire.fastsuite` | 5 | `FastSuite`、`AuxRecipeManager`、`CachedRecipeList` |
| `dev.shadowsoffire.fastsuite.mixin` | 4 | `ItemStackMixin`、`ServerResourcesMixin`、`CraftingInputMixin` |
| `dev.shadowsoffire.fastsuite.mixin.client` | 1 | `ClientPacketListenerMixin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FastSuite` | class | AbstractContainerMenu | Mod | 8 | 183 | [FastSuite.java](dev/shadowsoffire/fastsuite/FastSuite.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 30 | [ClientPacketListenerMixin.java](dev/shadowsoffire/fastsuite/mixin/client/ClientPacketListenerMixin.java) |
| `AuxRecipeManager` | class | RecipeManager | — | 6 | 130 | [AuxRecipeManager.java](dev/shadowsoffire/fastsuite/AuxRecipeManager.java) |
| `ItemStackMixin` | class | ILockableItemStack | Mixin | 2 | 26 | [ItemStackMixin.java](dev/shadowsoffire/fastsuite/mixin/ItemStackMixin.java) |
| `ServerResourcesMixin` | class | — | Mixin | 1 | 27 | [ServerResourcesMixin.java](dev/shadowsoffire/fastsuite/mixin/ServerResourcesMixin.java) |
| `CraftingInputMixin` | class | RecipeInput | Mixin | 4 | 51 | [CraftingInputMixin.java](dev/shadowsoffire/fastsuite/mixin/CraftingInputMixin.java) |
| `IngredientMixin` | class | — | Mixin | 3 | 51 | [IngredientMixin.java](dev/shadowsoffire/fastsuite/mixin/IngredientMixin.java) |
| `CachedRecipeList` | class | — | — | 5 | 125 | [CachedRecipeList.java](dev/shadowsoffire/fastsuite/CachedRecipeList.java) |
| `StreamUtils` | class | ForkJoinTask | — | 12 | 147 | [StreamUtils.java](dev/shadowsoffire/fastsuite/StreamUtils.java) |
| `ILockableItemStack` | interface | — | — | 0 | 6 | [ILockableItemStack.java](dev/shadowsoffire/fastsuite/ILockableItemStack.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `FastSuite` | 1 | `dev.shadowsoffire.fastsuite` | [FastSuite.java](dev/shadowsoffire/fastsuite/FastSuite.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](dev/shadowsoffire/fastsuite/mixin/client/ClientPacketListenerMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](dev/shadowsoffire/fastsuite/mixin/ItemStackMixin.java) |
| `ServerResourcesMixin` | `ServerResources` | [ServerResourcesMixin.java](dev/shadowsoffire/fastsuite/mixin/ServerResourcesMixin.java) |
| `CraftingInputMixin` | `CraftingInput` | [CraftingInputMixin.java](dev/shadowsoffire/fastsuite/mixin/CraftingInputMixin.java) |
| `IngredientMixin` | `Ingredient` | [IngredientMixin.java](dev/shadowsoffire/fastsuite/mixin/IngredientMixin.java) |

## 全部类（10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FastSuite` | class | AbstractContainerMenu | Mod | 8 | 183 | [FastSuite.java](dev/shadowsoffire/fastsuite/FastSuite.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 30 | [ClientPacketListenerMixin.java](dev/shadowsoffire/fastsuite/mixin/client/ClientPacketListenerMixin.java) |
| `AuxRecipeManager` | class | RecipeManager | — | 6 | 130 | [AuxRecipeManager.java](dev/shadowsoffire/fastsuite/AuxRecipeManager.java) |
| `ItemStackMixin` | class | ILockableItemStack | Mixin | 2 | 26 | [ItemStackMixin.java](dev/shadowsoffire/fastsuite/mixin/ItemStackMixin.java) |
| `ServerResourcesMixin` | class | — | Mixin | 1 | 27 | [ServerResourcesMixin.java](dev/shadowsoffire/fastsuite/mixin/ServerResourcesMixin.java) |
| `CraftingInputMixin` | class | RecipeInput | Mixin | 4 | 51 | [CraftingInputMixin.java](dev/shadowsoffire/fastsuite/mixin/CraftingInputMixin.java) |
| `IngredientMixin` | class | — | Mixin | 3 | 51 | [IngredientMixin.java](dev/shadowsoffire/fastsuite/mixin/IngredientMixin.java) |
| `CachedRecipeList` | class | — | — | 5 | 125 | [CachedRecipeList.java](dev/shadowsoffire/fastsuite/CachedRecipeList.java) |
| `StreamUtils` | class | ForkJoinTask | — | 12 | 147 | [StreamUtils.java](dev/shadowsoffire/fastsuite/StreamUtils.java) |
| `ILockableItemStack` | interface | — | — | 0 | 6 | [ILockableItemStack.java](dev/shadowsoffire/fastsuite/ILockableItemStack.java) |
