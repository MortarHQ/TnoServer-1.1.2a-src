# Tab List（`tablist`）

> jar：`tablist-neoforge-1.21.1-1.6.jar` ｜ 版本：`1.6` ｜ 作者：sennecools, NeofastFTL, MihailDenisov
> 规模：**14 个类 / 5 个包 / 1266 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`、`ftbranks`、`luckperms`、`spark`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.sennecools.tablist` | 5 | `TabListVariables`、`TabListUpdater`、`LuckPermsIntegration` |
| `com.sennecools.tablist.metrics` | 3 | `ServerMetricsResolver`、`SparkMetricsProvider`、`ServerMetrics` |
| `com.sennecools.tablist.neoforge` | 2 | `TabListNeoForge`、`NeoForgePlatformHelper` |
| `com.sennecools.tablist.config` | 2 | `TabListConfig`、`NameFormattingProvider` |
| `com.sennecools.tablist.platform` | 2 | `PlatformHelper`、`Services` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 14）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TabListNeoForge` | class | — | Mod | 5 | 56 | [TabListNeoForge.java](com/sennecools/tablist/neoforge/TabListNeoForge.java) |
| `TabListVariables` | class | — | — | 21 | 503 | [TabListVariables.java](com/sennecools/tablist/TabListVariables.java) |
| `TabListConfig` | class | — | — | 3 | 133 | [TabListConfig.java](com/sennecools/tablist/config/TabListConfig.java) |
| `TabListUpdater` | class | — | — | 10 | 246 | [TabListUpdater.java](com/sennecools/tablist/TabListUpdater.java) |
| `LuckPermsIntegration` | class | — | — | 4 | 62 | [LuckPermsIntegration.java](com/sennecools/tablist/LuckPermsIntegration.java) |
| `FTBRanksIntegration` | class | — | — | 3 | 25 | [FTBRanksIntegration.java](com/sennecools/tablist/FTBRanksIntegration.java) |
| `NeoForgePlatformHelper` | class | PlatformHelper | — | 3 | 25 | [NeoForgePlatformHelper.java](com/sennecools/tablist/neoforge/NeoForgePlatformHelper.java) |
| `PlatformHelper` | interface | — | — | 0 | 13 | [PlatformHelper.java](com/sennecools/tablist/platform/PlatformHelper.java) |
| `ServerMetricsResolver` | class | — | — | 8 | 111 | [ServerMetricsResolver.java](com/sennecools/tablist/metrics/ServerMetricsResolver.java) |
| `Constants` | class | — | — | 0 | 10 | [Constants.java](com/sennecools/tablist/Constants.java) |
| `NameFormattingProvider` | enum | — | — | 1 | 22 | [NameFormattingProvider.java](com/sennecools/tablist/config/NameFormattingProvider.java) |
| `Services` | class | — | — | 1 | 15 | [Services.java](com/sennecools/tablist/platform/Services.java) |
| `SparkMetricsProvider` | class | — | — | 0 | 40 | [SparkMetricsProvider.java](com/sennecools/tablist/metrics/SparkMetricsProvider.java) |
| `ServerMetrics` | record | — | — | 0 | 5 | [ServerMetrics.java](com/sennecools/tablist/metrics/ServerMetrics.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `TabListNeoForge` | 5 | `com.sennecools.tablist.neoforge` | [TabListNeoForge.java](com/sennecools/tablist/neoforge/TabListNeoForge.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TabListConfig` | — | [TabListConfig.java](com/sennecools/tablist/config/TabListConfig.java) |

## 全部类（14）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TabListNeoForge` | class | — | Mod | 5 | 56 | [TabListNeoForge.java](com/sennecools/tablist/neoforge/TabListNeoForge.java) |
| `TabListVariables` | class | — | — | 21 | 503 | [TabListVariables.java](com/sennecools/tablist/TabListVariables.java) |
| `TabListConfig` | class | — | — | 3 | 133 | [TabListConfig.java](com/sennecools/tablist/config/TabListConfig.java) |
| `TabListUpdater` | class | — | — | 10 | 246 | [TabListUpdater.java](com/sennecools/tablist/TabListUpdater.java) |
| `LuckPermsIntegration` | class | — | — | 4 | 62 | [LuckPermsIntegration.java](com/sennecools/tablist/LuckPermsIntegration.java) |
| `FTBRanksIntegration` | class | — | — | 3 | 25 | [FTBRanksIntegration.java](com/sennecools/tablist/FTBRanksIntegration.java) |
| `NeoForgePlatformHelper` | class | PlatformHelper | — | 3 | 25 | [NeoForgePlatformHelper.java](com/sennecools/tablist/neoforge/NeoForgePlatformHelper.java) |
| `PlatformHelper` | interface | — | — | 0 | 13 | [PlatformHelper.java](com/sennecools/tablist/platform/PlatformHelper.java) |
| `ServerMetricsResolver` | class | — | — | 8 | 111 | [ServerMetricsResolver.java](com/sennecools/tablist/metrics/ServerMetricsResolver.java) |
| `Constants` | class | — | — | 0 | 10 | [Constants.java](com/sennecools/tablist/Constants.java) |
| `NameFormattingProvider` | enum | — | — | 1 | 22 | [NameFormattingProvider.java](com/sennecools/tablist/config/NameFormattingProvider.java) |
| `Services` | class | — | — | 1 | 15 | [Services.java](com/sennecools/tablist/platform/Services.java) |
| `SparkMetricsProvider` | class | — | — | 0 | 40 | [SparkMetricsProvider.java](com/sennecools/tablist/metrics/SparkMetricsProvider.java) |
| `ServerMetrics` | record | — | — | 0 | 5 | [ServerMetrics.java](com/sennecools/tablist/metrics/ServerMetrics.java) |
