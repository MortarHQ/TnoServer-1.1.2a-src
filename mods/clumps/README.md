# Clumps（`clumps`）

> jar：`[经验机制改革] Clumps-neoforge-1.21.1-19.0.0.1.jar` ｜ 版本：`19.0.0.1` ｜ 作者：Jared
> 规模：**15 个类 / 5 个包 / 469 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.blamejared.clumps.platform` | 5 | `NeoForgeEventHandler`、`IEventHelper`、`NeoForgePlatformHelper` |
| `com.blamejared.clumps.api.events` | 5 | `IEventHandler`、`IRepairEvent`、`RepairEvent` |
| `com.blamejared.clumps` | 2 | `Clumps`、`ClumpsCommon` |
| `com.blamejared.clumps.mixin` | 2 | `MixinExperienceOrb`、`ExperienceOrbAccess` |
| `com.blamejared.clumps.helper` | 1 | `IClumpedOrb` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Clumps` | class | — | Mod | 0 | 13 | [Clumps.java](com/blamejared/clumps/Clumps.java) |
| `MixinExperienceOrb` | class | Entity | Mixin | 9 | 249 | [MixinExperienceOrb.java](com/blamejared/clumps/mixin/MixinExperienceOrb.java) |
| `NeoForgeEventHandler` | class | IEventHelper | — | 2 | 22 | [NeoForgeEventHandler.java](com/blamejared/clumps/platform/NeoForgeEventHandler.java) |
| `IEventHandler` | interface | — | — | 0 | 6 | [IEventHandler.java](com/blamejared/clumps/api/events/IEventHandler.java) |
| `ExperienceOrbAccess` | interface | — | Mixin | 0 | 18 | [ExperienceOrbAccess.java](com/blamejared/clumps/mixin/ExperienceOrbAccess.java) |
| `IEventHelper` | interface | — | — | 0 | 13 | [IEventHelper.java](com/blamejared/clumps/platform/IEventHelper.java) |
| `IRepairEvent` | interface | — | — | 0 | 12 | [IRepairEvent.java](com/blamejared/clumps/api/events/IRepairEvent.java) |
| `RepairEvent` | class | Event | — | 3 | 30 | [RepairEvent.java](com/blamejared/clumps/api/events/RepairEvent.java) |
| `IValueEvent` | interface | — | — | 0 | 12 | [IValueEvent.java](com/blamejared/clumps/api/events/IValueEvent.java) |
| `ValueEvent` | class | Event | — | 3 | 30 | [ValueEvent.java](com/blamejared/clumps/api/events/ValueEvent.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 1 | 11 | [NeoForgePlatformHelper.java](com/blamejared/clumps/platform/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/blamejared/clumps/platform/IPlatformHelper.java) |
| `ClumpsCommon` | class | — | — | 0 | 13 | [ClumpsCommon.java](com/blamejared/clumps/ClumpsCommon.java) |
| `IClumpedOrb` | interface | — | — | 0 | 14 | [IClumpedOrb.java](com/blamejared/clumps/helper/IClumpedOrb.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/blamejared/clumps/platform/Services.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinExperienceOrb` | `ExperienceOrb` | [MixinExperienceOrb.java](com/blamejared/clumps/mixin/MixinExperienceOrb.java) |
| `ExperienceOrbAccess` | `ExperienceOrbAccess` | [ExperienceOrbAccess.java](com/blamejared/clumps/mixin/ExperienceOrbAccess.java) |

## 全部类（15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Clumps` | class | — | Mod | 0 | 13 | [Clumps.java](com/blamejared/clumps/Clumps.java) |
| `MixinExperienceOrb` | class | Entity | Mixin | 9 | 249 | [MixinExperienceOrb.java](com/blamejared/clumps/mixin/MixinExperienceOrb.java) |
| `NeoForgeEventHandler` | class | IEventHelper | — | 2 | 22 | [NeoForgeEventHandler.java](com/blamejared/clumps/platform/NeoForgeEventHandler.java) |
| `IEventHandler` | interface | — | — | 0 | 6 | [IEventHandler.java](com/blamejared/clumps/api/events/IEventHandler.java) |
| `ExperienceOrbAccess` | interface | — | Mixin | 0 | 18 | [ExperienceOrbAccess.java](com/blamejared/clumps/mixin/ExperienceOrbAccess.java) |
| `IEventHelper` | interface | — | — | 0 | 13 | [IEventHelper.java](com/blamejared/clumps/platform/IEventHelper.java) |
| `IRepairEvent` | interface | — | — | 0 | 12 | [IRepairEvent.java](com/blamejared/clumps/api/events/IRepairEvent.java) |
| `RepairEvent` | class | Event | — | 3 | 30 | [RepairEvent.java](com/blamejared/clumps/api/events/RepairEvent.java) |
| `IValueEvent` | interface | — | — | 0 | 12 | [IValueEvent.java](com/blamejared/clumps/api/events/IValueEvent.java) |
| `ValueEvent` | class | Event | — | 3 | 30 | [ValueEvent.java](com/blamejared/clumps/api/events/ValueEvent.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 1 | 11 | [NeoForgePlatformHelper.java](com/blamejared/clumps/platform/NeoForgePlatformHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/blamejared/clumps/platform/IPlatformHelper.java) |
| `ClumpsCommon` | class | — | — | 0 | 13 | [ClumpsCommon.java](com/blamejared/clumps/ClumpsCommon.java) |
| `IClumpedOrb` | interface | — | — | 0 | 14 | [IClumpedOrb.java](com/blamejared/clumps/helper/IClumpedOrb.java) |
| `Services` | class | — | — | 1 | 16 | [Services.java](com/blamejared/clumps/platform/Services.java) |
