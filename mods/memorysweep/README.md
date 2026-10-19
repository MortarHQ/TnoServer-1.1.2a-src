# MemorySweep（`memorysweep`）

> jar：`[服务端-优化]MemorySweep.jar` ｜ 版本：`2.0.3` ｜ 作者：Tki_sor
> 规模：**10 个类 / 8 个包 / 564 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tkisor.memorysweep.mixin` | 2 | `MinecraftMixin`、`MinecraftServerMixin` |
| `com.tkisor.memorysweep.config` | 2 | `ModConfig`、`ClothConfig` |
| `com.tkisor.memorysweep.neoforge` | 1 | `MemorySweepNeoForge` |
| `com.tkisor.memorysweep.utils` | 1 | `SweepUtil` |
| `architectury_inject_memorysweep_common_f6a600bb62424f97b802d0851dac8faa_bc97cdf1102e729a1112d60b077efe953b10c68e8b7860fdda808decf17ae5b8MemorySweep1211common203devjar` | 1 | `PlatformMethods` |
| `com.tkisor.memorysweep` | 1 | `MemorySweep` |
| `com.tkisor.memorysweep.task` | 1 | `MemoryUsageTask` |
| `com.tkisor.memorysweep.data` | 1 | `Flags` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MemorySweepNeoForge` | class | — | Mod | 0 | 12 | [MemorySweepNeoForge.java](com/tkisor/memorysweep/neoforge/MemorySweepNeoForge.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 59 | [MinecraftMixin.java](com/tkisor/memorysweep/mixin/MinecraftMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 1 | 28 | [MinecraftServerMixin.java](com/tkisor/memorysweep/mixin/MinecraftServerMixin.java) |
| `ModConfig` | class | — | — | 9 | 115 | [ModConfig.java](com/tkisor/memorysweep/config/ModConfig.java) |
| `ClothConfig` | class | — | — | 2 | 59 | [ClothConfig.java](com/tkisor/memorysweep/config/ClothConfig.java) |
| `SweepUtil` | class | — | — | 8 | 114 | [SweepUtil.java](com/tkisor/memorysweep/utils/SweepUtil.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_memorysweep_common_f6a600bb62424f97b802d0851dac8faa_bc97cdf1102e729a1112d60b077efe953b10c68e8b7860fdda808decf17ae5b8MemorySweep1211common203devjar/PlatformMethods.java) |
| `MemorySweep` | class | — | — | 1 | 127 | [MemorySweep.java](com/tkisor/memorysweep/MemorySweep.java) |
| `MemoryUsageTask` | class | TimerTask | — | 2 | 34 | [MemoryUsageTask.java](com/tkisor/memorysweep/task/MemoryUsageTask.java) |
| `Flags` | class | — | — | 0 | 8 | [Flags.java](com/tkisor/memorysweep/data/Flags.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/tkisor/memorysweep/mixin/MinecraftMixin.java) |
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](com/tkisor/memorysweep/mixin/MinecraftServerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ModConfig` | — | [ModConfig.java](com/tkisor/memorysweep/config/ModConfig.java) |
| `ClothConfig` | — | [ClothConfig.java](com/tkisor/memorysweep/config/ClothConfig.java) |

## 全部类（10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MemorySweepNeoForge` | class | — | Mod | 0 | 12 | [MemorySweepNeoForge.java](com/tkisor/memorysweep/neoforge/MemorySweepNeoForge.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 59 | [MinecraftMixin.java](com/tkisor/memorysweep/mixin/MinecraftMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 1 | 28 | [MinecraftServerMixin.java](com/tkisor/memorysweep/mixin/MinecraftServerMixin.java) |
| `ModConfig` | class | — | — | 9 | 115 | [ModConfig.java](com/tkisor/memorysweep/config/ModConfig.java) |
| `ClothConfig` | class | — | — | 2 | 59 | [ClothConfig.java](com/tkisor/memorysweep/config/ClothConfig.java) |
| `SweepUtil` | class | — | — | 8 | 114 | [SweepUtil.java](com/tkisor/memorysweep/utils/SweepUtil.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_memorysweep_common_f6a600bb62424f97b802d0851dac8faa_bc97cdf1102e729a1112d60b077efe953b10c68e8b7860fdda808decf17ae5b8MemorySweep1211common203devjar/PlatformMethods.java) |
| `MemorySweep` | class | — | — | 1 | 127 | [MemorySweep.java](com/tkisor/memorysweep/MemorySweep.java) |
| `MemoryUsageTask` | class | TimerTask | — | 2 | 34 | [MemoryUsageTask.java](com/tkisor/memorysweep/task/MemoryUsageTask.java) |
| `Flags` | class | — | — | 0 | 8 | [Flags.java](com/tkisor/memorysweep/data/Flags.java) |
