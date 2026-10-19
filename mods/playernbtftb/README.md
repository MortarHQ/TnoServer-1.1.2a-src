# PlayerNBT FTB（`playernbtftb`）

> jar：`[任务附属]playernbtftb.jar` ｜ 版本：`1.0.1.1` ｜ 作者：AblazeAQZL
> 规模：**6 个类 / 4 个包 / 343 行** ｜ 事件处理类 2 ｜ Mixin 0 ｜ 注册/管理类 0

**依赖**：`minecraft`、`architectury`、`ftblibrary`、`ftbteams`、`ftbquests`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.playernbtftb` | 2 | `PlayerNbtFtbModClient`、`PlayerNbtFtbMod` |
| `com.playernbtftb.tasks` | 2 | `PlayerNbtTask`、`ModTaskTypes` |
| `com.playernbtftb.config` | 1 | `PlayerNbtConfig` |
| `com.playernbtftb.utils` | 1 | `NBTUtils` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 6）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PlayerNbtFtbModClient` | class | — | EventBusSubscriber、Mod | 0 | 23 | [PlayerNbtFtbModClient.java](com/playernbtftb/PlayerNbtFtbModClient.java) |
| `PlayerNbtFtbMod` | class | — | Mod | 2 | 37 | [PlayerNbtFtbMod.java](com/playernbtftb/PlayerNbtFtbMod.java) |
| `PlayerNbtConfig` | class | — | — | 0 | 25 | [PlayerNbtConfig.java](com/playernbtftb/config/PlayerNbtConfig.java) |
| `PlayerNbtTask` | class | Task | — | 10 | 170 | [PlayerNbtTask.java](com/playernbtftb/tasks/PlayerNbtTask.java) |
| `NBTUtils` | class | — | — | 2 | 70 | [NBTUtils.java](com/playernbtftb/utils/NBTUtils.java) |
| `ModTaskTypes` | class | — | — | 1 | 18 | [ModTaskTypes.java](com/playernbtftb/tasks/ModTaskTypes.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `PlayerNbtFtbModClient` | 1 | `com.playernbtftb` | [PlayerNbtFtbModClient.java](com/playernbtftb/PlayerNbtFtbModClient.java) |
| `PlayerNbtFtbMod` | 1 | `com.playernbtftb` | [PlayerNbtFtbMod.java](com/playernbtftb/PlayerNbtFtbMod.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `PlayerNbtConfig` | — | [PlayerNbtConfig.java](com/playernbtftb/config/PlayerNbtConfig.java) |

## 全部类（6）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PlayerNbtFtbModClient` | class | — | EventBusSubscriber、Mod | 0 | 23 | [PlayerNbtFtbModClient.java](com/playernbtftb/PlayerNbtFtbModClient.java) |
| `PlayerNbtFtbMod` | class | — | Mod | 2 | 37 | [PlayerNbtFtbMod.java](com/playernbtftb/PlayerNbtFtbMod.java) |
| `PlayerNbtConfig` | class | — | — | 0 | 25 | [PlayerNbtConfig.java](com/playernbtftb/config/PlayerNbtConfig.java) |
| `PlayerNbtTask` | class | Task | — | 10 | 170 | [PlayerNbtTask.java](com/playernbtftb/tasks/PlayerNbtTask.java) |
| `NBTUtils` | class | — | — | 2 | 70 | [NBTUtils.java](com/playernbtftb/utils/NBTUtils.java) |
| `ModTaskTypes` | class | — | — | 1 | 18 | [ModTaskTypes.java](com/playernbtftb/tasks/ModTaskTypes.java) |
