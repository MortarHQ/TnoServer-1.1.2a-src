# Tensura Nature's Aura（`tensura_naturesaura`）

> jar：`[转生附属]tensura_naturesaura.jar` ｜ 版本：`1.0.0` ｜ 作者：Tensura
> 规模：**11 个类 / 5 个包 / 708 行** ｜ 事件处理类 1 ｜ Mixin 3 ｜ 注册/管理类 3

**依赖**：`neoforge`、`minecraft`、`naturesaura`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tensura.naturesaura.integration` | 4 | `TensuraOreIntegration`、`LegacyGearEpMigration`、`BiomeAuraResolver` |
| `com.tensura.naturesaura.mixin` | 3 | `ClientEventsMixin`、`ClientEventsAccessor`、`InternalHooksMixin` |
| `com.tensura.naturesaura.registry` | 2 | `ModBiomeTags`、`LegacyAttachments` |
| `com.tensura.naturesaura.client` | 1 | `AuraHudHandler` |
| `com.tensura.naturesaura` | 1 | `TensuraNaturesAura` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 11）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AuraHudHandler` | class | — | EventBusSubscriber | 5 | 127 | [AuraHudHandler.java](com/tensura/naturesaura/client/AuraHudHandler.java) |
| `ClientEventsMixin` | class | — | Mixin | 0 | 158 | [ClientEventsMixin.java](com/tensura/naturesaura/mixin/ClientEventsMixin.java) |
| `ClientEventsAccessor` | interface | — | Mixin | 0 | 25 | [ClientEventsAccessor.java](com/tensura/naturesaura/mixin/ClientEventsAccessor.java) |
| `TensuraNaturesAura` | class | — | Mod | 0 | 23 | [TensuraNaturesAura.java](com/tensura/naturesaura/TensuraNaturesAura.java) |
| `InternalHooksMixin` | class | — | Mixin | 0 | 41 | [InternalHooksMixin.java](com/tensura/naturesaura/mixin/InternalHooksMixin.java) |
| `TensuraOreIntegration` | class | — | — | 2 | 27 | [TensuraOreIntegration.java](com/tensura/naturesaura/integration/TensuraOreIntegration.java) |
| `LegacyGearEpMigration` | class | — | — | 10 | 161 | [LegacyGearEpMigration.java](com/tensura/naturesaura/integration/LegacyGearEpMigration.java) |
| `BiomeAuraResolver` | class | — | — | 2 | 51 | [BiomeAuraResolver.java](com/tensura/naturesaura/integration/BiomeAuraResolver.java) |
| `LegacyBiomeAuraMigration` | class | — | — | 2 | 45 | [LegacyBiomeAuraMigration.java](com/tensura/naturesaura/integration/LegacyBiomeAuraMigration.java) |
| `ModBiomeTags` | class | — | — | 0 | 25 | [ModBiomeTags.java](com/tensura/naturesaura/registry/ModBiomeTags.java) |
| `LegacyAttachments` | class | — | — | 1 | 25 | [LegacyAttachments.java](com/tensura/naturesaura/registry/LegacyAttachments.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `AuraHudHandler` | 1 | `com.tensura.naturesaura.client` | [AuraHudHandler.java](com/tensura/naturesaura/client/AuraHudHandler.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientEventsMixin` | `ClientEvents` | [ClientEventsMixin.java](com/tensura/naturesaura/mixin/ClientEventsMixin.java) |
| `ClientEventsAccessor` | `ClientEventsAccessor` | [ClientEventsAccessor.java](com/tensura/naturesaura/mixin/ClientEventsAccessor.java) |
| `InternalHooksMixin` | `InternalHooks` | [InternalHooksMixin.java](com/tensura/naturesaura/mixin/InternalHooksMixin.java) |

## 全部类（11）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AuraHudHandler` | class | — | EventBusSubscriber | 5 | 127 | [AuraHudHandler.java](com/tensura/naturesaura/client/AuraHudHandler.java) |
| `ClientEventsMixin` | class | — | Mixin | 0 | 158 | [ClientEventsMixin.java](com/tensura/naturesaura/mixin/ClientEventsMixin.java) |
| `ClientEventsAccessor` | interface | — | Mixin | 0 | 25 | [ClientEventsAccessor.java](com/tensura/naturesaura/mixin/ClientEventsAccessor.java) |
| `TensuraNaturesAura` | class | — | Mod | 0 | 23 | [TensuraNaturesAura.java](com/tensura/naturesaura/TensuraNaturesAura.java) |
| `InternalHooksMixin` | class | — | Mixin | 0 | 41 | [InternalHooksMixin.java](com/tensura/naturesaura/mixin/InternalHooksMixin.java) |
| `TensuraOreIntegration` | class | — | — | 2 | 27 | [TensuraOreIntegration.java](com/tensura/naturesaura/integration/TensuraOreIntegration.java) |
| `LegacyGearEpMigration` | class | — | — | 10 | 161 | [LegacyGearEpMigration.java](com/tensura/naturesaura/integration/LegacyGearEpMigration.java) |
| `BiomeAuraResolver` | class | — | — | 2 | 51 | [BiomeAuraResolver.java](com/tensura/naturesaura/integration/BiomeAuraResolver.java) |
| `LegacyBiomeAuraMigration` | class | — | — | 2 | 45 | [LegacyBiomeAuraMigration.java](com/tensura/naturesaura/integration/LegacyBiomeAuraMigration.java) |
| `ModBiomeTags` | class | — | — | 0 | 25 | [ModBiomeTags.java](com/tensura/naturesaura/registry/ModBiomeTags.java) |
| `LegacyAttachments` | class | — | — | 1 | 25 | [LegacyAttachments.java](com/tensura/naturesaura/registry/LegacyAttachments.java) |
