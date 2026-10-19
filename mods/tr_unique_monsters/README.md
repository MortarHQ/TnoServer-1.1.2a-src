# TensuraUniqueMonsters（`tr_unique_monsters`）

> jar：`[转生附属]tensurauniquemonsters.jar` ｜ 版本：`1.0.2` ｜ 作者：?
> 规模：**14 个类 / 14 个包 / 694 行** ｜ 事件处理类 0 ｜ Mixin 4 ｜ 注册/管理类 3

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.crypticmc.tr_unique_monsters.mixin.tensura.handler` | 1 | `AttributeHandlerMixin` |
| `net.crypticmc.tr_unique_monsters.neoforge` | 1 | `TensuraUniqueMonstersNeoForge` |
| `net.crypticmc.tr_unique_monsters.mixin.tensura.network.c2s` | 1 | `RequestNamingKeyPacketMixin` |
| `net.crypticmc.tr_unique_monsters.mixin.tensura.ability.skill` | 1 | `SkillClientUtilsMixin` |
| `net.crypticmc.tr_unique_monsters.mixin.tensura` | 1 | `TensuraMixin` |
| `net.crypticmc.tr_unique_monsters.registry` | 1 | `TRUniqueMobsRegistry` |
| `net.crypticmc.tr_unique_monsters.handler` | 1 | `MasteryHandler` |
| `net.crypticmc.tr_unique_monsters.ability.skill.extra` | 1 | `AppraisalEyeSkill` |
| `net.crypticmc.tr_unique_monsters.config` | 1 | `TRUniqueMobsConfigs` |
| `net.crypticmc.tr_unique_monsters.config.configs` | 1 | `BaseConfig` |
| `net.crypticmc.tr_unique_monsters.config.configs.ability.skill` | 1 | `ExtraSkillConfig` |
| `architectury_inject_tr_unique_monsters_common_15580675e5bd4072be809c2de1b18d83_ddde4f6cc992b49172b061043cbe1d93ef6b2474c81976e7948f039cbb485dactensurauniquemonsterscommon102devjar` | 1 | `PlatformMethods` |
| `net.crypticmc.tr_unique_monsters` | 1 | `TensuraUniqueMonsters` |
| `net.crypticmc.tr_unique_monsters.registry.skill` | 1 | `ExtraSkills` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 14）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AttributeHandlerMixin` | class | — | Mixin | 4 | 172 | [AttributeHandlerMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/handler/AttributeHandlerMixin.java) |
| `TensuraUniqueMonstersNeoForge` | class | — | Mod | 0 | 12 | [TensuraUniqueMonstersNeoForge.java](net/crypticmc/tr_unique_monsters/neoforge/TensuraUniqueMonstersNeoForge.java) |
| `RequestNamingKeyPacketMixin` | class | — | Mixin | 1 | 34 | [RequestNamingKeyPacketMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/network/c2s/RequestNamingKeyPacketMixin.java) |
| `SkillClientUtilsMixin` | class | — | Mixin | 0 | 55 | [SkillClientUtilsMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/ability/skill/SkillClientUtilsMixin.java) |
| `TensuraMixin` | class | — | Mixin | 1 | 22 | [TensuraMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/TensuraMixin.java) |
| `TRUniqueMobsRegistry` | class | — | — | 2 | 17 | [TRUniqueMobsRegistry.java](net/crypticmc/tr_unique_monsters/registry/TRUniqueMobsRegistry.java) |
| `MasteryHandler` | class | — | — | 2 | 37 | [MasteryHandler.java](net/crypticmc/tr_unique_monsters/handler/MasteryHandler.java) |
| `AppraisalEyeSkill` | class | Skill | — | 16 | 222 | [AppraisalEyeSkill.java](net/crypticmc/tr_unique_monsters/ability/skill/extra/AppraisalEyeSkill.java) |
| `TRUniqueMobsConfigs` | class | — | — | 1 | 13 | [TRUniqueMobsConfigs.java](net/crypticmc/tr_unique_monsters/config/TRUniqueMobsConfigs.java) |
| `BaseConfig` | class | ManasConfig | SyncToClient | 1 | 35 | [BaseConfig.java](net/crypticmc/tr_unique_monsters/config/configs/BaseConfig.java) |
| `ExtraSkillConfig` | class | ManasConfig | — | 1 | 35 | [ExtraSkillConfig.java](net/crypticmc/tr_unique_monsters/config/configs/ability/skill/ExtraSkillConfig.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tr_unique_monsters_common_15580675e5bd4072be809c2de1b18d83_ddde4f6cc992b49172b061043cbe1d93ef6b2474c81976e7948f039cbb485dactensurauniquemonsterscommon102devjar/PlatformMethods.java) |
| `TensuraUniqueMonsters` | class | — | — | 1 | 15 | [TensuraUniqueMonsters.java](net/crypticmc/tr_unique_monsters/TensuraUniqueMonsters.java) |
| `ExtraSkills` | class | — | — | 1 | 17 | [ExtraSkills.java](net/crypticmc/tr_unique_monsters/registry/skill/ExtraSkills.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AttributeHandlerMixin` | `AttributeHandler` | [AttributeHandlerMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/handler/AttributeHandlerMixin.java) |
| `RequestNamingKeyPacketMixin` | `RequestNamingKeyPacket` | [RequestNamingKeyPacketMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/network/c2s/RequestNamingKeyPacketMixin.java) |
| `SkillClientUtilsMixin` | `SkillClientUtils` | [SkillClientUtilsMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/ability/skill/SkillClientUtilsMixin.java) |
| `TensuraMixin` | `Tensura` | [TensuraMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/TensuraMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TRUniqueMobsConfigs` | — | [TRUniqueMobsConfigs.java](net/crypticmc/tr_unique_monsters/config/TRUniqueMobsConfigs.java) |
| `BaseConfig` | SyncToClient | [BaseConfig.java](net/crypticmc/tr_unique_monsters/config/configs/BaseConfig.java) |
| `ExtraSkillConfig` | — | [ExtraSkillConfig.java](net/crypticmc/tr_unique_monsters/config/configs/ability/skill/ExtraSkillConfig.java) |

## 全部类（14）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AttributeHandlerMixin` | class | — | Mixin | 4 | 172 | [AttributeHandlerMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/handler/AttributeHandlerMixin.java) |
| `TensuraUniqueMonstersNeoForge` | class | — | Mod | 0 | 12 | [TensuraUniqueMonstersNeoForge.java](net/crypticmc/tr_unique_monsters/neoforge/TensuraUniqueMonstersNeoForge.java) |
| `RequestNamingKeyPacketMixin` | class | — | Mixin | 1 | 34 | [RequestNamingKeyPacketMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/network/c2s/RequestNamingKeyPacketMixin.java) |
| `SkillClientUtilsMixin` | class | — | Mixin | 0 | 55 | [SkillClientUtilsMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/ability/skill/SkillClientUtilsMixin.java) |
| `TensuraMixin` | class | — | Mixin | 1 | 22 | [TensuraMixin.java](net/crypticmc/tr_unique_monsters/mixin/tensura/TensuraMixin.java) |
| `TRUniqueMobsRegistry` | class | — | — | 2 | 17 | [TRUniqueMobsRegistry.java](net/crypticmc/tr_unique_monsters/registry/TRUniqueMobsRegistry.java) |
| `MasteryHandler` | class | — | — | 2 | 37 | [MasteryHandler.java](net/crypticmc/tr_unique_monsters/handler/MasteryHandler.java) |
| `AppraisalEyeSkill` | class | Skill | — | 16 | 222 | [AppraisalEyeSkill.java](net/crypticmc/tr_unique_monsters/ability/skill/extra/AppraisalEyeSkill.java) |
| `TRUniqueMobsConfigs` | class | — | — | 1 | 13 | [TRUniqueMobsConfigs.java](net/crypticmc/tr_unique_monsters/config/TRUniqueMobsConfigs.java) |
| `BaseConfig` | class | ManasConfig | SyncToClient | 1 | 35 | [BaseConfig.java](net/crypticmc/tr_unique_monsters/config/configs/BaseConfig.java) |
| `ExtraSkillConfig` | class | ManasConfig | — | 1 | 35 | [ExtraSkillConfig.java](net/crypticmc/tr_unique_monsters/config/configs/ability/skill/ExtraSkillConfig.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tr_unique_monsters_common_15580675e5bd4072be809c2de1b18d83_ddde4f6cc992b49172b061043cbe1d93ef6b2474c81976e7948f039cbb485dactensurauniquemonsterscommon102devjar/PlatformMethods.java) |
| `TensuraUniqueMonsters` | class | — | — | 1 | 15 | [TensuraUniqueMonsters.java](net/crypticmc/tr_unique_monsters/TensuraUniqueMonsters.java) |
| `ExtraSkills` | class | — | — | 1 | 17 | [ExtraSkills.java](net/crypticmc/tr_unique_monsters/registry/skill/ExtraSkills.java) |
