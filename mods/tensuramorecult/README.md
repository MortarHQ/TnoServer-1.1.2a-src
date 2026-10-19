# Tensura More Cult（`tensuramorecult`）

> jar：`[转生附属]tensuramorecult.jar` ｜ 版本：`1.0.0` ｜ 作者：wal_bos
> 规模：**12 个类 / 2 个包 / 1137 行** ｜ 事件处理类 2 ｜ Mixin 6 ｜ 注册/管理类 0

**官方描述**：Native Tensura combat, skills, existence and equipment integration for Cult of Azazel.

**依赖**：`neoforge`、`minecraft`、`tensura`、`manascore`、`netherman`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.wal_bos.tensuramorecult` | 6 | `TensuraMoreCult`、`CultCombat`、`CultEquipment` |
| `com.github.wal_bos.tensuramorecult.mixin` | 6 | `BelieverAccessor`、`MaskComponentsMixin`、`SpikeDamageMixin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 12）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraMoreCult` | class | — | Mod | 1 | 35 | [TensuraMoreCult.java](com/github/wal_bos/tensuramorecult/TensuraMoreCult.java) |
| `CultCombat` | class | — | — | 22 | 450 | [CultCombat.java](com/github/wal_bos/tensuramorecult/CultCombat.java) |
| `CultEquipment` | class | — | — | 11 | 211 | [CultEquipment.java](com/github/wal_bos/tensuramorecult/CultEquipment.java) |
| `BelieverAccessor` | interface | — | Mixin | 0 | 13 | [BelieverAccessor.java](com/github/wal_bos/tensuramorecult/mixin/BelieverAccessor.java) |
| `MaskComponentsMixin` | class | — | Mixin | 0 | 32 | [MaskComponentsMixin.java](com/github/wal_bos/tensuramorecult/mixin/MaskComponentsMixin.java) |
| `SpikeDamageMixin` | class | — | Mixin | 0 | 21 | [SpikeDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/SpikeDamageMixin.java) |
| `AzazelDamageMixin` | class | — | Mixin | 0 | 37 | [AzazelDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/AzazelDamageMixin.java) |
| `CutsceneDeathMixin` | class | — | Mixin | 0 | 21 | [CutsceneDeathMixin.java](com/github/wal_bos/tensuramorecult/mixin/CutsceneDeathMixin.java) |
| `LaserDamageMixin` | class | — | Mixin | 0 | 21 | [LaserDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/LaserDamageMixin.java) |
| `CultConfig` | class | — | — | 0 | 131 | [CultConfig.java](com/github/wal_bos/tensuramorecult/CultConfig.java) |
| `CultGameTests` | class | — | GameTestHolder、PrefixGameTestTemplate | 3 | 125 | [CultGameTests.java](com/github/wal_bos/tensuramorecult/CultGameTests.java) |
| `CultBalance` | class | — | — | 3 | 40 | [CultBalance.java](com/github/wal_bos/tensuramorecult/CultBalance.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CultCombat` | 7 | `com.github.wal_bos.tensuramorecult` | [CultCombat.java](com/github/wal_bos/tensuramorecult/CultCombat.java) |
| `CultEquipment` | 4 | `com.github.wal_bos.tensuramorecult` | [CultEquipment.java](com/github/wal_bos/tensuramorecult/CultEquipment.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BelieverAccessor` | `BelieverAccessor` | [BelieverAccessor.java](com/github/wal_bos/tensuramorecult/mixin/BelieverAccessor.java) |
| `MaskComponentsMixin` | `MaskComponents` | [MaskComponentsMixin.java](com/github/wal_bos/tensuramorecult/mixin/MaskComponentsMixin.java) |
| `SpikeDamageMixin` | `SpikeDamage` | [SpikeDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/SpikeDamageMixin.java) |
| `AzazelDamageMixin` | `AzazelDamage` | [AzazelDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/AzazelDamageMixin.java) |
| `CutsceneDeathMixin` | `CutsceneDeath` | [CutsceneDeathMixin.java](com/github/wal_bos/tensuramorecult/mixin/CutsceneDeathMixin.java) |
| `LaserDamageMixin` | `LaserDamage` | [LaserDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/LaserDamageMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CultConfig` | — | [CultConfig.java](com/github/wal_bos/tensuramorecult/CultConfig.java) |

## 全部类（12）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraMoreCult` | class | — | Mod | 1 | 35 | [TensuraMoreCult.java](com/github/wal_bos/tensuramorecult/TensuraMoreCult.java) |
| `CultCombat` | class | — | — | 22 | 450 | [CultCombat.java](com/github/wal_bos/tensuramorecult/CultCombat.java) |
| `CultEquipment` | class | — | — | 11 | 211 | [CultEquipment.java](com/github/wal_bos/tensuramorecult/CultEquipment.java) |
| `BelieverAccessor` | interface | — | Mixin | 0 | 13 | [BelieverAccessor.java](com/github/wal_bos/tensuramorecult/mixin/BelieverAccessor.java) |
| `MaskComponentsMixin` | class | — | Mixin | 0 | 32 | [MaskComponentsMixin.java](com/github/wal_bos/tensuramorecult/mixin/MaskComponentsMixin.java) |
| `SpikeDamageMixin` | class | — | Mixin | 0 | 21 | [SpikeDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/SpikeDamageMixin.java) |
| `AzazelDamageMixin` | class | — | Mixin | 0 | 37 | [AzazelDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/AzazelDamageMixin.java) |
| `CutsceneDeathMixin` | class | — | Mixin | 0 | 21 | [CutsceneDeathMixin.java](com/github/wal_bos/tensuramorecult/mixin/CutsceneDeathMixin.java) |
| `LaserDamageMixin` | class | — | Mixin | 0 | 21 | [LaserDamageMixin.java](com/github/wal_bos/tensuramorecult/mixin/LaserDamageMixin.java) |
| `CultConfig` | class | — | — | 0 | 131 | [CultConfig.java](com/github/wal_bos/tensuramorecult/CultConfig.java) |
| `CultGameTests` | class | — | GameTestHolder、PrefixGameTestTemplate | 3 | 125 | [CultGameTests.java](com/github/wal_bos/tensuramorecult/CultGameTests.java) |
| `CultBalance` | class | — | — | 3 | 40 | [CultBalance.java](com/github/wal_bos/tensuramorecult/CultBalance.java) |
