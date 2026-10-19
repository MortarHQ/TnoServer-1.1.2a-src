# Does It Tick?（`does_it_tick`）

> jar：`[优化]does_it_tick.jar` ｜ 版本：`1.1.4` ｜ 作者：Toni
> 规模：**8 个类 / 4 个包 / 549 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 1

**官方描述**：Entity Ticking Performance Mod

**依赖**：`minecraft`、`txnilib`、`chunkactivitytracker`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `toni.doesittick.mixin` | 3 | `ClaimedChunkManagerAccessor`、`EntityTypeMixin`、`WorldMixin` |
| `toni.doesittick.integration` | 3 | `IChunkClaimProvider`、`FTBChunkClaimProvider`、`OPACChunkClaimProvider` |
| `toni.doesittick` | 1 | `DoesItTick` |
| `toni.doesittick.api` | 1 | `Tickable` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 8）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DoesItTick` | class | — | Mod | 9 | 362 | [DoesItTick.java](toni/doesittick/DoesItTick.java) |
| `ClaimedChunkManagerAccessor` | interface | — | Mixin | 0 | 15 | [ClaimedChunkManagerAccessor.java](toni/doesittick/mixin/ClaimedChunkManagerAccessor.java) |
| `EntityTypeMixin` | class | Tickable.EntityType | Mixin | 0 | 46 | [EntityTypeMixin.java](toni/doesittick/mixin/EntityTypeMixin.java) |
| `WorldMixin` | class | Tickable.Level | Mixin | 2 | 67 | [WorldMixin.java](toni/doesittick/mixin/WorldMixin.java) |
| `IChunkClaimProvider` | interface | — | — | 0 | 9 | [IChunkClaimProvider.java](toni/doesittick/integration/IChunkClaimProvider.java) |
| `FTBChunkClaimProvider` | class | IChunkClaimProvider | — | 1 | 17 | [FTBChunkClaimProvider.java](toni/doesittick/integration/FTBChunkClaimProvider.java) |
| `OPACChunkClaimProvider` | class | IChunkClaimProvider | — | 1 | 17 | [OPACChunkClaimProvider.java](toni/doesittick/integration/OPACChunkClaimProvider.java) |
| `Tickable` | class | — | — | 0 | 16 | [Tickable.java](toni/doesittick/api/Tickable.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClaimedChunkManagerAccessor` | `ClaimedChunkManagerAccessor` | [ClaimedChunkManagerAccessor.java](toni/doesittick/mixin/ClaimedChunkManagerAccessor.java) |
| `EntityTypeMixin` | `EntityType` | [EntityTypeMixin.java](toni/doesittick/mixin/EntityTypeMixin.java) |
| `WorldMixin` | `World` | [WorldMixin.java](toni/doesittick/mixin/WorldMixin.java) |

## 全部类（8）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DoesItTick` | class | — | Mod | 9 | 362 | [DoesItTick.java](toni/doesittick/DoesItTick.java) |
| `ClaimedChunkManagerAccessor` | interface | — | Mixin | 0 | 15 | [ClaimedChunkManagerAccessor.java](toni/doesittick/mixin/ClaimedChunkManagerAccessor.java) |
| `EntityTypeMixin` | class | Tickable.EntityType | Mixin | 0 | 46 | [EntityTypeMixin.java](toni/doesittick/mixin/EntityTypeMixin.java) |
| `WorldMixin` | class | Tickable.Level | Mixin | 2 | 67 | [WorldMixin.java](toni/doesittick/mixin/WorldMixin.java) |
| `IChunkClaimProvider` | interface | — | — | 0 | 9 | [IChunkClaimProvider.java](toni/doesittick/integration/IChunkClaimProvider.java) |
| `FTBChunkClaimProvider` | class | IChunkClaimProvider | — | 1 | 17 | [FTBChunkClaimProvider.java](toni/doesittick/integration/FTBChunkClaimProvider.java) |
| `OPACChunkClaimProvider` | class | IChunkClaimProvider | — | 1 | 17 | [OPACChunkClaimProvider.java](toni/doesittick/integration/OPACChunkClaimProvider.java) |
| `Tickable` | class | — | — | 0 | 16 | [Tickable.java](toni/doesittick/api/Tickable.java) |
