# TR Lag Begon（`trlagbegon`）

> jar：`[优化]trlagbegone.jar` ｜ 版本：`1.0.0` ｜ 作者：shadowako
> 规模：**15 个类 / 6 个包 / 1254 行** ｜ 事件处理类 1 ｜ Mixin 5 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.shadowako.trlagbegon.mixin` | 6 | `EntityFluidBypassMixin`、`TensuraParticleHelperMixin`、`TensuraMixinLevelGetPartsWrapMixin` |
| `dev.shadowako.trlagbegon` | 4 | `TrLagBegonMod`、`StressTestRunner`、`TrLagBegonCommands` |
| `dev.shadowako.trlagbegon.util` | 2 | `GiantBlockIterationLimiter`、`TensuraParticleInRangeUtil` |
| `dev.shadowako.trlagbegon.event` | 1 | `TensuraPartEventHandlers` |
| `dev.shadowako.trlagbegon.hook` | 1 | `IGiantMobBlockScanHooks` |
| `dev.shadowako.trlagbegon.part` | 1 | `TensuraPartChunkIndex` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraPartEventHandlers` | class | — | EventBusSubscriber | 4 | 47 | [TensuraPartEventHandlers.java](dev/shadowako/trlagbegon/event/TensuraPartEventHandlers.java) |
| `TrLagBegonMod` | class | — | Mod | 0 | 20 | [TrLagBegonMod.java](dev/shadowako/trlagbegon/TrLagBegonMod.java) |
| `EntityFluidBypassMixin` | class | — | Mixin | 0 | 29 | [EntityFluidBypassMixin.java](dev/shadowako/trlagbegon/mixin/EntityFluidBypassMixin.java) |
| `StressTestRunner` | class | — | — | 26 | 458 | [StressTestRunner.java](dev/shadowako/trlagbegon/StressTestRunner.java) |
| `TensuraParticleHelperMixin` | class | — | Mixin | 0 | 162 | [TensuraParticleHelperMixin.java](dev/shadowako/trlagbegon/mixin/TensuraParticleHelperMixin.java) |
| `TensuraMixinLevelGetPartsWrapMixin` | class | — | Mixin | 1 | 66 | [TensuraMixinLevelGetPartsWrapMixin.java](dev/shadowako/trlagbegon/mixin/TensuraMixinLevelGetPartsWrapMixin.java) |
| `IGiantMobTrLagMixin` | interface | — | Mixin | 0 | 9 | [IGiantMobTrLagMixin.java](dev/shadowako/trlagbegon/mixin/IGiantMobTrLagMixin.java) |
| `BeamProjectileLagMixin` | class | — | Mixin | 0 | 30 | [BeamProjectileLagMixin.java](dev/shadowako/trlagbegon/mixin/BeamProjectileLagMixin.java) |
| `TrLagBegonCommands` | class | — | — | 3 | 68 | [TrLagBegonCommands.java](dev/shadowako/trlagbegon/TrLagBegonCommands.java) |
| `IGiantMobBlockScanHooks` | class | — | — | 0 | 22 | [IGiantMobBlockScanHooks.java](dev/shadowako/trlagbegon/hook/IGiantMobBlockScanHooks.java) |
| `GiantBlockIterationLimiter` | class | — | — | 3 | 33 | [GiantBlockIterationLimiter.java](dev/shadowako/trlagbegon/util/GiantBlockIterationLimiter.java) |
| `TensuraPartChunkIndex` | class | — | — | 9 | 182 | [TensuraPartChunkIndex.java](dev/shadowako/trlagbegon/part/TensuraPartChunkIndex.java) |
| `GiantMobTrLagPlugin` | class | IMixinConfigPlugin | — | 8 | 78 | [GiantMobTrLagPlugin.java](dev/shadowako/trlagbegon/mixin/GiantMobTrLagPlugin.java) |
| `TensuraParticleInRangeUtil` | class | — | — | 1 | 37 | [TensuraParticleInRangeUtil.java](dev/shadowako/trlagbegon/util/TensuraParticleInRangeUtil.java) |
| `ModLifecycle` | class | — | — | 1 | 13 | [ModLifecycle.java](dev/shadowako/trlagbegon/ModLifecycle.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `TensuraPartEventHandlers` | 4 | `dev.shadowako.trlagbegon.event` | [TensuraPartEventHandlers.java](dev/shadowako/trlagbegon/event/TensuraPartEventHandlers.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `EntityFluidBypassMixin` | `EntityFluidBypass` | [EntityFluidBypassMixin.java](dev/shadowako/trlagbegon/mixin/EntityFluidBypassMixin.java) |
| `TensuraParticleHelperMixin` | `TensuraParticleHelper` | [TensuraParticleHelperMixin.java](dev/shadowako/trlagbegon/mixin/TensuraParticleHelperMixin.java) |
| `TensuraMixinLevelGetPartsWrapMixin` | `TensuraLevelGetPartsWrap` | [TensuraMixinLevelGetPartsWrapMixin.java](dev/shadowako/trlagbegon/mixin/TensuraMixinLevelGetPartsWrapMixin.java) |
| `IGiantMobTrLagMixin` | `IGiantMobTrLag` | [IGiantMobTrLagMixin.java](dev/shadowako/trlagbegon/mixin/IGiantMobTrLagMixin.java) |
| `BeamProjectileLagMixin` | `BeamProjectileLag` | [BeamProjectileLagMixin.java](dev/shadowako/trlagbegon/mixin/BeamProjectileLagMixin.java) |

## 全部类（15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraPartEventHandlers` | class | — | EventBusSubscriber | 4 | 47 | [TensuraPartEventHandlers.java](dev/shadowako/trlagbegon/event/TensuraPartEventHandlers.java) |
| `TrLagBegonMod` | class | — | Mod | 0 | 20 | [TrLagBegonMod.java](dev/shadowako/trlagbegon/TrLagBegonMod.java) |
| `EntityFluidBypassMixin` | class | — | Mixin | 0 | 29 | [EntityFluidBypassMixin.java](dev/shadowako/trlagbegon/mixin/EntityFluidBypassMixin.java) |
| `StressTestRunner` | class | — | — | 26 | 458 | [StressTestRunner.java](dev/shadowako/trlagbegon/StressTestRunner.java) |
| `TensuraParticleHelperMixin` | class | — | Mixin | 0 | 162 | [TensuraParticleHelperMixin.java](dev/shadowako/trlagbegon/mixin/TensuraParticleHelperMixin.java) |
| `TensuraMixinLevelGetPartsWrapMixin` | class | — | Mixin | 1 | 66 | [TensuraMixinLevelGetPartsWrapMixin.java](dev/shadowako/trlagbegon/mixin/TensuraMixinLevelGetPartsWrapMixin.java) |
| `IGiantMobTrLagMixin` | interface | — | Mixin | 0 | 9 | [IGiantMobTrLagMixin.java](dev/shadowako/trlagbegon/mixin/IGiantMobTrLagMixin.java) |
| `BeamProjectileLagMixin` | class | — | Mixin | 0 | 30 | [BeamProjectileLagMixin.java](dev/shadowako/trlagbegon/mixin/BeamProjectileLagMixin.java) |
| `TrLagBegonCommands` | class | — | — | 3 | 68 | [TrLagBegonCommands.java](dev/shadowako/trlagbegon/TrLagBegonCommands.java) |
| `IGiantMobBlockScanHooks` | class | — | — | 0 | 22 | [IGiantMobBlockScanHooks.java](dev/shadowako/trlagbegon/hook/IGiantMobBlockScanHooks.java) |
| `GiantBlockIterationLimiter` | class | — | — | 3 | 33 | [GiantBlockIterationLimiter.java](dev/shadowako/trlagbegon/util/GiantBlockIterationLimiter.java) |
| `TensuraPartChunkIndex` | class | — | — | 9 | 182 | [TensuraPartChunkIndex.java](dev/shadowako/trlagbegon/part/TensuraPartChunkIndex.java) |
| `GiantMobTrLagPlugin` | class | IMixinConfigPlugin | — | 8 | 78 | [GiantMobTrLagPlugin.java](dev/shadowako/trlagbegon/mixin/GiantMobTrLagPlugin.java) |
| `TensuraParticleInRangeUtil` | class | — | — | 1 | 37 | [TensuraParticleInRangeUtil.java](dev/shadowako/trlagbegon/util/TensuraParticleInRangeUtil.java) |
| `ModLifecycle` | class | — | — | 1 | 13 | [ModLifecycle.java](dev/shadowako/trlagbegon/ModLifecycle.java) |
