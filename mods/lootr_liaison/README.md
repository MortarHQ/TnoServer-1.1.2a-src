# Lootr Liaison（`lootr_liaison`）

> jar：`[lootr优化]lootrliaison.jar` ｜ 版本：`1.3.0` ｜ 作者：Extra_Special_K, Ginger1y, Manialate, Supremepringle
> 规模：**12 个类 / 10 个包 / 632 行** ｜ 事件处理类 5 ｜ Mixin 1 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`、`lootr`、`artifacts`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `uk.co.extraspecialstudio.lootr_liaison` | 3 | `Config`、`Lootr_liaison`、`LiaisonNbt` |
| `uk.co.extraspecialstudio.lootr_liaison.mixin` | 1 | `MixinLevelChunkSetLevelEarly` |
| `uk.co.extraspecialstudio.lootr_liaison.command` | 1 | `LootrLiaisonCommands` |
| `uk.co.extraspecialstudio.lootr_liaison.queue` | 1 | `FinalizationQueue` |
| `uk.co.extraspecialstudio.lootr_liaison.detection` | 1 | `ContainerDetectionLayer` |
| `uk.co.extraspecialstudio.lootr_liaison.mimic` | 1 | `MimicStabilityLayer` |
| `uk.co.extraspecialstudio.lootr_liaison.lootr` | 1 | `LootrAdapter` |
| `uk.co.extraspecialstudio.lootr_liaison.logging` | 1 | `LootTableLevelWarningFilter` |
| `uk.co.extraspecialstudio.lootr_liaison.compat` | 1 | `DeadLettersCompat` |
| `uk.co.extraspecialstudio.lootr_liaison.context` | 1 | `StructureContextEngine` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 12）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Config` | class | — | EventBusSubscriber | 1 | 106 | [Config.java](uk/co/extraspecialstudio/lootr_liaison/Config.java) |
| `Lootr_liaison` | class | — | Mod | 4 | 79 | [Lootr_liaison.java](uk/co/extraspecialstudio/lootr_liaison/Lootr_liaison.java) |
| `MixinLevelChunkSetLevelEarly` | class | — | Mixin | 1 | 26 | [MixinLevelChunkSetLevelEarly.java](uk/co/extraspecialstudio/lootr_liaison/mixin/MixinLevelChunkSetLevelEarly.java) |
| `LootrLiaisonCommands` | class | — | — | 4 | 113 | [LootrLiaisonCommands.java](uk/co/extraspecialstudio/lootr_liaison/command/LootrLiaisonCommands.java) |
| `FinalizationQueue` | class | — | — | 2 | 80 | [FinalizationQueue.java](uk/co/extraspecialstudio/lootr_liaison/queue/FinalizationQueue.java) |
| `ContainerDetectionLayer` | class | — | — | 1 | 39 | [ContainerDetectionLayer.java](uk/co/extraspecialstudio/lootr_liaison/detection/ContainerDetectionLayer.java) |
| `MimicStabilityLayer` | class | — | — | 4 | 74 | [MimicStabilityLayer.java](uk/co/extraspecialstudio/lootr_liaison/mimic/MimicStabilityLayer.java) |
| `LootrAdapter` | class | — | — | 1 | 15 | [LootrAdapter.java](uk/co/extraspecialstudio/lootr_liaison/lootr/LootrAdapter.java) |
| `LootTableLevelWarningFilter` | class | AbstractFilter | — | 1 | 23 | [LootTableLevelWarningFilter.java](uk/co/extraspecialstudio/lootr_liaison/logging/LootTableLevelWarningFilter.java) |
| `DeadLettersCompat` | class | — | — | 1 | 15 | [DeadLettersCompat.java](uk/co/extraspecialstudio/lootr_liaison/compat/DeadLettersCompat.java) |
| `LiaisonNbt` | class | — | — | 4 | 42 | [LiaisonNbt.java](uk/co/extraspecialstudio/lootr_liaison/LiaisonNbt.java) |
| `StructureContextEngine` | class | — | — | 2 | 20 | [StructureContextEngine.java](uk/co/extraspecialstudio/lootr_liaison/context/StructureContextEngine.java) |

## 事件处理（`@SubscribeEvent`，5 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `Config` | 1 | `uk.co.extraspecialstudio.lootr_liaison` | [Config.java](uk/co/extraspecialstudio/lootr_liaison/Config.java) |
| `Lootr_liaison` | 1 | `uk.co.extraspecialstudio.lootr_liaison` | [Lootr_liaison.java](uk/co/extraspecialstudio/lootr_liaison/Lootr_liaison.java) |
| `FinalizationQueue` | 1 | `uk.co.extraspecialstudio.lootr_liaison.queue` | [FinalizationQueue.java](uk/co/extraspecialstudio/lootr_liaison/queue/FinalizationQueue.java) |
| `ContainerDetectionLayer` | 1 | `uk.co.extraspecialstudio.lootr_liaison.detection` | [ContainerDetectionLayer.java](uk/co/extraspecialstudio/lootr_liaison/detection/ContainerDetectionLayer.java) |
| `MimicStabilityLayer` | 1 | `uk.co.extraspecialstudio.lootr_liaison.mimic` | [MimicStabilityLayer.java](uk/co/extraspecialstudio/lootr_liaison/mimic/MimicStabilityLayer.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinLevelChunkSetLevelEarly` | `LevelChunkSetLevelEarly` | [MixinLevelChunkSetLevelEarly.java](uk/co/extraspecialstudio/lootr_liaison/mixin/MixinLevelChunkSetLevelEarly.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | EventBusSubscriber | [Config.java](uk/co/extraspecialstudio/lootr_liaison/Config.java) |

## 全部类（12）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Config` | class | — | EventBusSubscriber | 1 | 106 | [Config.java](uk/co/extraspecialstudio/lootr_liaison/Config.java) |
| `Lootr_liaison` | class | — | Mod | 4 | 79 | [Lootr_liaison.java](uk/co/extraspecialstudio/lootr_liaison/Lootr_liaison.java) |
| `MixinLevelChunkSetLevelEarly` | class | — | Mixin | 1 | 26 | [MixinLevelChunkSetLevelEarly.java](uk/co/extraspecialstudio/lootr_liaison/mixin/MixinLevelChunkSetLevelEarly.java) |
| `LootrLiaisonCommands` | class | — | — | 4 | 113 | [LootrLiaisonCommands.java](uk/co/extraspecialstudio/lootr_liaison/command/LootrLiaisonCommands.java) |
| `FinalizationQueue` | class | — | — | 2 | 80 | [FinalizationQueue.java](uk/co/extraspecialstudio/lootr_liaison/queue/FinalizationQueue.java) |
| `ContainerDetectionLayer` | class | — | — | 1 | 39 | [ContainerDetectionLayer.java](uk/co/extraspecialstudio/lootr_liaison/detection/ContainerDetectionLayer.java) |
| `MimicStabilityLayer` | class | — | — | 4 | 74 | [MimicStabilityLayer.java](uk/co/extraspecialstudio/lootr_liaison/mimic/MimicStabilityLayer.java) |
| `LootrAdapter` | class | — | — | 1 | 15 | [LootrAdapter.java](uk/co/extraspecialstudio/lootr_liaison/lootr/LootrAdapter.java) |
| `LootTableLevelWarningFilter` | class | AbstractFilter | — | 1 | 23 | [LootTableLevelWarningFilter.java](uk/co/extraspecialstudio/lootr_liaison/logging/LootTableLevelWarningFilter.java) |
| `DeadLettersCompat` | class | — | — | 1 | 15 | [DeadLettersCompat.java](uk/co/extraspecialstudio/lootr_liaison/compat/DeadLettersCompat.java) |
| `LiaisonNbt` | class | — | — | 4 | 42 | [LiaisonNbt.java](uk/co/extraspecialstudio/lootr_liaison/LiaisonNbt.java) |
| `StructureContextEngine` | class | — | — | 2 | 20 | [StructureContextEngine.java](uk/co/extraspecialstudio/lootr_liaison/context/StructureContextEngine.java) |
