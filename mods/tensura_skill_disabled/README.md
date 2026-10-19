# TenSura Skill Disabled（`tensura_skill_disabled`）

> jar：`[转生附属]tensura_skill_disabled.jar` ｜ 版本：`1.0.0.0` ｜ 作者：AblazeAQZL
> 规模：**5 个类 / 4 个包 / 692 行** ｜ 事件处理类 2 ｜ Mixin 0 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`、`manascore`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tensuraskilldisabled.handler` | 2 | `SkillBlacklistEvents`、`SkillRemovalHelper` |
| `com.tensuraskilldisabled` | 1 | `TensuraSkillDisabled` |
| `com.tensuraskilldisabled.command` | 1 | `SkillBlacklistCommand` |
| `com.tensuraskilldisabled.config` | 1 | `SkillBlacklistConfig` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 5）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SkillBlacklistEvents` | class | — | EventBusSubscriber | 6 | 101 | [SkillBlacklistEvents.java](com/tensuraskilldisabled/handler/SkillBlacklistEvents.java) |
| `TensuraSkillDisabled` | class | — | Mod | 2 | 39 | [TensuraSkillDisabled.java](com/tensuraskilldisabled/TensuraSkillDisabled.java) |
| `SkillBlacklistCommand` | class | — | — | 17 | 301 | [SkillBlacklistCommand.java](com/tensuraskilldisabled/command/SkillBlacklistCommand.java) |
| `SkillBlacklistConfig` | class | — | — | 8 | 110 | [SkillBlacklistConfig.java](com/tensuraskilldisabled/config/SkillBlacklistConfig.java) |
| `SkillRemovalHelper` | class | — | — | 5 | 141 | [SkillRemovalHelper.java](com/tensuraskilldisabled/handler/SkillRemovalHelper.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SkillBlacklistEvents` | 3 | `com.tensuraskilldisabled.handler` | [SkillBlacklistEvents.java](com/tensuraskilldisabled/handler/SkillBlacklistEvents.java) |
| `TensuraSkillDisabled` | 1 | `com.tensuraskilldisabled` | [TensuraSkillDisabled.java](com/tensuraskilldisabled/TensuraSkillDisabled.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `SkillBlacklistConfig` | — | [SkillBlacklistConfig.java](com/tensuraskilldisabled/config/SkillBlacklistConfig.java) |

## 全部类（5）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SkillBlacklistEvents` | class | — | EventBusSubscriber | 6 | 101 | [SkillBlacklistEvents.java](com/tensuraskilldisabled/handler/SkillBlacklistEvents.java) |
| `TensuraSkillDisabled` | class | — | Mod | 2 | 39 | [TensuraSkillDisabled.java](com/tensuraskilldisabled/TensuraSkillDisabled.java) |
| `SkillBlacklistCommand` | class | — | — | 17 | 301 | [SkillBlacklistCommand.java](com/tensuraskilldisabled/command/SkillBlacklistCommand.java) |
| `SkillBlacklistConfig` | class | — | — | 8 | 110 | [SkillBlacklistConfig.java](com/tensuraskilldisabled/config/SkillBlacklistConfig.java) |
| `SkillRemovalHelper` | class | — | — | 5 | 141 | [SkillRemovalHelper.java](com/tensuraskilldisabled/handler/SkillRemovalHelper.java) |
