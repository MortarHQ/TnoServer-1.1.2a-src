# TenSura Compat: FTB（`tensura_ftb`）

> jar：`[转生附属]tensura_ftb.jar` ｜ 版本：`2.0.0.4` ｜ 作者：MinhEragon
> 规模：**18 个类 / 7 个包 / 1722 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `io.github.manasmods.tensura_ftb.rewards` | 6 | `RaceReward`、`SpiritReward`、`RandomAbilityReward` |
| `io.github.manasmods.tensura_ftb.tasks` | 5 | `ExistenceTask`、`AbilityTask`、`RaceTask` |
| `io.github.manasmods.tensura_ftb.mixin` | 2 | `MixinFTBQuestsEventHandler`、`MixinSubordinateHelper` |
| `io.github.manasmods.tensura_ftb` | 2 | `FtbHandler`、`TensuraFtb` |
| `io.github.manasmods.tensura_ftb.neoforge` | 1 | `TensuraFtbNeoForge` |
| `io.github.manasmods.tensura_ftb.registry` | 1 | `FtbConfig` |
| `architectury_inject_tensura_ftb_common_383ad892194b4462bec4809904254890_9e275f33923b96eb6d28d893560558fe2847d27d920a68d0320e322fdd1cbaf9common2004devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 18）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinFTBQuestsEventHandler` | class | — | Mixin | 1 | 45 | [MixinFTBQuestsEventHandler.java](io/github/manasmods/tensura_ftb/mixin/MixinFTBQuestsEventHandler.java) |
| `TensuraFtbNeoForge` | class | — | Mod | 0 | 13 | [TensuraFtbNeoForge.java](io/github/manasmods/tensura_ftb/neoforge/TensuraFtbNeoForge.java) |
| `FtbHandler` | class | — | — | 4 | 416 | [FtbHandler.java](io/github/manasmods/tensura_ftb/FtbHandler.java) |
| `MixinSubordinateHelper` | class | — | Mixin | 1 | 32 | [MixinSubordinateHelper.java](io/github/manasmods/tensura_ftb/mixin/MixinSubordinateHelper.java) |
| `FtbConfig` | class | ManasConfig | — | 1 | 45 | [FtbConfig.java](io/github/manasmods/tensura_ftb/registry/FtbConfig.java) |
| `ExistenceTask` | class | Task | — | 13 | 121 | [ExistenceTask.java](io/github/manasmods/tensura_ftb/tasks/ExistenceTask.java) |
| `RaceReward` | class | Reward | — | 7 | 67 | [RaceReward.java](io/github/manasmods/tensura_ftb/rewards/RaceReward.java) |
| `SpiritReward` | class | Reward | — | 7 | 69 | [SpiritReward.java](io/github/manasmods/tensura_ftb/rewards/SpiritReward.java) |
| `RandomAbilityReward` | class | Reward | — | 8 | 153 | [RandomAbilityReward.java](io/github/manasmods/tensura_ftb/rewards/RandomAbilityReward.java) |
| `ExistenceReward` | class | Reward | — | 8 | 137 | [ExistenceReward.java](io/github/manasmods/tensura_ftb/rewards/ExistenceReward.java) |
| `AbilityReward` | class | Reward | — | 8 | 107 | [AbilityReward.java](io/github/manasmods/tensura_ftb/rewards/AbilityReward.java) |
| `AwakeningReward` | class | Reward | — | 7 | 104 | [AwakeningReward.java](io/github/manasmods/tensura_ftb/rewards/AwakeningReward.java) |
| `AbilityTask` | class | AbstractBooleanTask | — | 8 | 92 | [AbilityTask.java](io/github/manasmods/tensura_ftb/tasks/AbilityTask.java) |
| `RaceTask` | class | AbstractBooleanTask | — | 7 | 69 | [RaceTask.java](io/github/manasmods/tensura_ftb/tasks/RaceTask.java) |
| `SpiritTask` | class | AbstractBooleanTask | — | 7 | 79 | [SpiritTask.java](io/github/manasmods/tensura_ftb/tasks/SpiritTask.java) |
| `AwakeningTask` | class | AbstractBooleanTask | — | 7 | 87 | [AwakeningTask.java](io/github/manasmods/tensura_ftb/tasks/AwakeningTask.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_ftb_common_383ad892194b4462bec4809904254890_9e275f33923b96eb6d28d893560558fe2847d27d920a68d0320e322fdd1cbaf9common2004devjar/PlatformMethods.java) |
| `TensuraFtb` | class | — | — | 3 | 78 | [TensuraFtb.java](io/github/manasmods/tensura_ftb/TensuraFtb.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinFTBQuestsEventHandler` | `FTBQuestsEventHandler` | [MixinFTBQuestsEventHandler.java](io/github/manasmods/tensura_ftb/mixin/MixinFTBQuestsEventHandler.java) |
| `MixinSubordinateHelper` | `SubordinateHelper` | [MixinSubordinateHelper.java](io/github/manasmods/tensura_ftb/mixin/MixinSubordinateHelper.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FtbConfig` | — | [FtbConfig.java](io/github/manasmods/tensura_ftb/registry/FtbConfig.java) |

## 全部类（18）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinFTBQuestsEventHandler` | class | — | Mixin | 1 | 45 | [MixinFTBQuestsEventHandler.java](io/github/manasmods/tensura_ftb/mixin/MixinFTBQuestsEventHandler.java) |
| `TensuraFtbNeoForge` | class | — | Mod | 0 | 13 | [TensuraFtbNeoForge.java](io/github/manasmods/tensura_ftb/neoforge/TensuraFtbNeoForge.java) |
| `FtbHandler` | class | — | — | 4 | 416 | [FtbHandler.java](io/github/manasmods/tensura_ftb/FtbHandler.java) |
| `MixinSubordinateHelper` | class | — | Mixin | 1 | 32 | [MixinSubordinateHelper.java](io/github/manasmods/tensura_ftb/mixin/MixinSubordinateHelper.java) |
| `FtbConfig` | class | ManasConfig | — | 1 | 45 | [FtbConfig.java](io/github/manasmods/tensura_ftb/registry/FtbConfig.java) |
| `ExistenceTask` | class | Task | — | 13 | 121 | [ExistenceTask.java](io/github/manasmods/tensura_ftb/tasks/ExistenceTask.java) |
| `RaceReward` | class | Reward | — | 7 | 67 | [RaceReward.java](io/github/manasmods/tensura_ftb/rewards/RaceReward.java) |
| `SpiritReward` | class | Reward | — | 7 | 69 | [SpiritReward.java](io/github/manasmods/tensura_ftb/rewards/SpiritReward.java) |
| `RandomAbilityReward` | class | Reward | — | 8 | 153 | [RandomAbilityReward.java](io/github/manasmods/tensura_ftb/rewards/RandomAbilityReward.java) |
| `ExistenceReward` | class | Reward | — | 8 | 137 | [ExistenceReward.java](io/github/manasmods/tensura_ftb/rewards/ExistenceReward.java) |
| `AbilityReward` | class | Reward | — | 8 | 107 | [AbilityReward.java](io/github/manasmods/tensura_ftb/rewards/AbilityReward.java) |
| `AwakeningReward` | class | Reward | — | 7 | 104 | [AwakeningReward.java](io/github/manasmods/tensura_ftb/rewards/AwakeningReward.java) |
| `AbilityTask` | class | AbstractBooleanTask | — | 8 | 92 | [AbilityTask.java](io/github/manasmods/tensura_ftb/tasks/AbilityTask.java) |
| `RaceTask` | class | AbstractBooleanTask | — | 7 | 69 | [RaceTask.java](io/github/manasmods/tensura_ftb/tasks/RaceTask.java) |
| `SpiritTask` | class | AbstractBooleanTask | — | 7 | 79 | [SpiritTask.java](io/github/manasmods/tensura_ftb/tasks/SpiritTask.java) |
| `AwakeningTask` | class | AbstractBooleanTask | — | 7 | 87 | [AwakeningTask.java](io/github/manasmods/tensura_ftb/tasks/AwakeningTask.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_ftb_common_383ad892194b4462bec4809904254890_9e275f33923b96eb6d28d893560558fe2847d27d920a68d0320e322fdd1cbaf9common2004devjar/PlatformMethods.java) |
| `TensuraFtb` | class | — | — | 3 | 78 | [TensuraFtb.java](io/github/manasmods/tensura_ftb/TensuraFtb.java) |
