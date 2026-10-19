# Tensura: Better Subordinates（`tensura_better_subs`）

> jar：`[转生附属]tensura_better_subs.jar` ｜ 版本：`1.2.4` ｜ 作者：TR-BeyondDev
> 规模：**23 个类 / 13 个包 / 3708 行** ｜ 事件处理类 0 ｜ Mixin 6 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`、`architectury`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.trbeyond.tensura_better_subs.client.screen` | 5 | `AbilitySelectionScreenEntity`、`AbilityCategoriesScreenEntity`、`SkillGrantScreen` |
| `com.trbeyond.tensura_better_subs.mixin.tensura.ability.skill.unique` | 3 | `MixinGluttonySkill`、`MixinGourmetSkill`、`MixinStarvedSkill` |
| `com.trbeyond.tensura_better_subs.network.c2s` | 2 | `RequestSubordinateAbilityModeChangePacket`、`SubordinateSkillGrantingPacket` |
| `com.trbeyond.tensura_better_subs.entity` | 2 | `ExpandedSkillStorage`、`IExpandedSkillAcquisition` |
| `com.trbeyond.tensura_better_subs.network.s2c` | 2 | `OpenSkillGrantMenuPayload`、`OpenHumanoidSkillMenuPayload` |
| `com.trbeyond.tensura_better_subs.ability.skill` | 2 | `IFoodChain`、`SkillGranting` |
| `com.trbeyond.tensura_better_subs.neoforge` | 1 | `BetterSubs` |
| `com.trbeyond.tensura_better_subs.mixin.tensura.entity.template` | 1 | `MixinTensuraTamableEntity` |
| `com.trbeyond.tensura_better_subs.mixin.tensura.ability.skill.common` | 1 | `MixinThoughtCommunicationSkill` |
| `com.trbeyond.tensura_better_subs.mixin.tensura.client.screen` | 1 | `MixinHumanoidMainScreen` |
| `com.trbeyond.tensura_better_subs.config` | 1 | `ExpandedSkillConfig` |
| `com.trbeyond.tensura_better_subs` | 1 | `BetterSubs` |
| `architectury_inject_tensura_better_subs_common_c2477061c9884123bf5fa4b2314985ff_9305a8ec55452516d9d153cbee8c40009ebf8d625a3660ffe2b02c82b578f237tensura_better_subscommon124devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 23）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AbilitySelectionScreenEntity` | class | SimpleScreen | — | 28 | 827 | [AbilitySelectionScreenEntity.java](com/trbeyond/tensura_better_subs/client/screen/AbilitySelectionScreenEntity.java) |
| `BetterSubs` | class | — | Mod | 0 | 11 | [BetterSubs.java](com/trbeyond/tensura_better_subs/neoforge/BetterSubs.java) |
| `AbilityCategoriesScreenEntity` | class | SimpleScreen | — | 17 | 528 | [AbilityCategoriesScreenEntity.java](com/trbeyond/tensura_better_subs/client/screen/AbilityCategoriesScreenEntity.java) |
| `MixinTensuraTamableEntity` | class | — | Mixin | 2 | 110 | [MixinTensuraTamableEntity.java](com/trbeyond/tensura_better_subs/mixin/tensura/entity/template/MixinTensuraTamableEntity.java) |
| `MixinThoughtCommunicationSkill` | class | Skill | Mixin | 8 | 242 | [MixinThoughtCommunicationSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/common/MixinThoughtCommunicationSkill.java) |
| `MixinHumanoidMainScreen` | class | — | Mixin | 3 | 77 | [MixinHumanoidMainScreen.java](com/trbeyond/tensura_better_subs/mixin/tensura/client/screen/MixinHumanoidMainScreen.java) |
| `RequestSubordinateAbilityModeChangePacket` | record | CustomPacketPayload | — | 13 | 262 | [RequestSubordinateAbilityModeChangePacket.java](com/trbeyond/tensura_better_subs/network/c2s/RequestSubordinateAbilityModeChangePacket.java) |
| `MixinGluttonySkill` | class | IFoodChain | Mixin | 2 | 33 | [MixinGluttonySkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinGluttonySkill.java) |
| `MixinGourmetSkill` | class | IFoodChain | Mixin | 2 | 33 | [MixinGourmetSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinGourmetSkill.java) |
| `MixinStarvedSkill` | class | IFoodChain | Mixin | 2 | 33 | [MixinStarvedSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinStarvedSkill.java) |
| `SkillGrantScreen` | class | SimpleScreen | — | 19 | 385 | [SkillGrantScreen.java](com/trbeyond/tensura_better_subs/client/screen/SkillGrantScreen.java) |
| `ExpandedSkillConfig` | class | ManasConfig | SyncToClient | 1 | 59 | [ExpandedSkillConfig.java](com/trbeyond/tensura_better_subs/config/ExpandedSkillConfig.java) |
| `ScreenHelperSubordinates` | class | ScreenHelper | — | 6 | 85 | [ScreenHelperSubordinates.java](com/trbeyond/tensura_better_subs/client/screen/ScreenHelperSubordinates.java) |
| `SubordinateSkillGrantingPacket` | record | CustomPacketPayload | — | 3 | 157 | [SubordinateSkillGrantingPacket.java](com/trbeyond/tensura_better_subs/network/c2s/SubordinateSkillGrantingPacket.java) |
| `ExpandedSkillStorage` | class | Storage | — | 10 | 227 | [ExpandedSkillStorage.java](com/trbeyond/tensura_better_subs/entity/ExpandedSkillStorage.java) |
| `BetterSubs` | class | — | — | 7 | 209 | [BetterSubs.java](com/trbeyond/tensura_better_subs/BetterSubs.java) |
| `OpenSkillGrantMenuPayload` | record | CustomPacketPayload | — | 3 | 49 | [OpenSkillGrantMenuPayload.java](com/trbeyond/tensura_better_subs/network/s2c/OpenSkillGrantMenuPayload.java) |
| `OpenHumanoidSkillMenuPayload` | record | CustomPacketPayload | — | 3 | 39 | [OpenHumanoidSkillMenuPayload.java](com/trbeyond/tensura_better_subs/network/s2c/OpenHumanoidSkillMenuPayload.java) |
| `MainScreenSubordinate` | class | SimpleScreen | — | 4 | 167 | [MainScreenSubordinate.java](com/trbeyond/tensura_better_subs/client/screen/MainScreenSubordinate.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_better_subs_common_c2477061c9884123bf5fa4b2314985ff_9305a8ec55452516d9d153cbee8c40009ebf8d625a3660ffe2b02c82b578f237tensura_better_subscommon124devjar/PlatformMethods.java) |
| `IExpandedSkillAcquisition` | interface | — | — | 0 | 63 | [IExpandedSkillAcquisition.java](com/trbeyond/tensura_better_subs/entity/IExpandedSkillAcquisition.java) |
| `IFoodChain` | interface | — | — | 0 | 33 | [IFoodChain.java](com/trbeyond/tensura_better_subs/ability/skill/IFoodChain.java) |
| `SkillGranting` | class | — | — | 3 | 71 | [SkillGranting.java](com/trbeyond/tensura_better_subs/ability/skill/SkillGranting.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinTensuraTamableEntity` | `TensuraTamableEntity` | [MixinTensuraTamableEntity.java](com/trbeyond/tensura_better_subs/mixin/tensura/entity/template/MixinTensuraTamableEntity.java) |
| `MixinThoughtCommunicationSkill` | `ThoughtCommunicationSkill` | [MixinThoughtCommunicationSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/common/MixinThoughtCommunicationSkill.java) |
| `MixinHumanoidMainScreen` | `HumanoidMainScreen` | [MixinHumanoidMainScreen.java](com/trbeyond/tensura_better_subs/mixin/tensura/client/screen/MixinHumanoidMainScreen.java) |
| `MixinGluttonySkill` | `GluttonySkill` | [MixinGluttonySkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinGluttonySkill.java) |
| `MixinGourmetSkill` | `GourmetSkill` | [MixinGourmetSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinGourmetSkill.java) |
| `MixinStarvedSkill` | `StarvedSkill` | [MixinStarvedSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinStarvedSkill.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ExpandedSkillConfig` | SyncToClient | [ExpandedSkillConfig.java](com/trbeyond/tensura_better_subs/config/ExpandedSkillConfig.java) |

## 全部类（23）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AbilitySelectionScreenEntity` | class | SimpleScreen | — | 28 | 827 | [AbilitySelectionScreenEntity.java](com/trbeyond/tensura_better_subs/client/screen/AbilitySelectionScreenEntity.java) |
| `BetterSubs` | class | — | Mod | 0 | 11 | [BetterSubs.java](com/trbeyond/tensura_better_subs/neoforge/BetterSubs.java) |
| `AbilityCategoriesScreenEntity` | class | SimpleScreen | — | 17 | 528 | [AbilityCategoriesScreenEntity.java](com/trbeyond/tensura_better_subs/client/screen/AbilityCategoriesScreenEntity.java) |
| `MixinTensuraTamableEntity` | class | — | Mixin | 2 | 110 | [MixinTensuraTamableEntity.java](com/trbeyond/tensura_better_subs/mixin/tensura/entity/template/MixinTensuraTamableEntity.java) |
| `MixinThoughtCommunicationSkill` | class | Skill | Mixin | 8 | 242 | [MixinThoughtCommunicationSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/common/MixinThoughtCommunicationSkill.java) |
| `MixinHumanoidMainScreen` | class | — | Mixin | 3 | 77 | [MixinHumanoidMainScreen.java](com/trbeyond/tensura_better_subs/mixin/tensura/client/screen/MixinHumanoidMainScreen.java) |
| `RequestSubordinateAbilityModeChangePacket` | record | CustomPacketPayload | — | 13 | 262 | [RequestSubordinateAbilityModeChangePacket.java](com/trbeyond/tensura_better_subs/network/c2s/RequestSubordinateAbilityModeChangePacket.java) |
| `MixinGluttonySkill` | class | IFoodChain | Mixin | 2 | 33 | [MixinGluttonySkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinGluttonySkill.java) |
| `MixinGourmetSkill` | class | IFoodChain | Mixin | 2 | 33 | [MixinGourmetSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinGourmetSkill.java) |
| `MixinStarvedSkill` | class | IFoodChain | Mixin | 2 | 33 | [MixinStarvedSkill.java](com/trbeyond/tensura_better_subs/mixin/tensura/ability/skill/unique/MixinStarvedSkill.java) |
| `SkillGrantScreen` | class | SimpleScreen | — | 19 | 385 | [SkillGrantScreen.java](com/trbeyond/tensura_better_subs/client/screen/SkillGrantScreen.java) |
| `ExpandedSkillConfig` | class | ManasConfig | SyncToClient | 1 | 59 | [ExpandedSkillConfig.java](com/trbeyond/tensura_better_subs/config/ExpandedSkillConfig.java) |
| `ScreenHelperSubordinates` | class | ScreenHelper | — | 6 | 85 | [ScreenHelperSubordinates.java](com/trbeyond/tensura_better_subs/client/screen/ScreenHelperSubordinates.java) |
| `SubordinateSkillGrantingPacket` | record | CustomPacketPayload | — | 3 | 157 | [SubordinateSkillGrantingPacket.java](com/trbeyond/tensura_better_subs/network/c2s/SubordinateSkillGrantingPacket.java) |
| `ExpandedSkillStorage` | class | Storage | — | 10 | 227 | [ExpandedSkillStorage.java](com/trbeyond/tensura_better_subs/entity/ExpandedSkillStorage.java) |
| `BetterSubs` | class | — | — | 7 | 209 | [BetterSubs.java](com/trbeyond/tensura_better_subs/BetterSubs.java) |
| `OpenSkillGrantMenuPayload` | record | CustomPacketPayload | — | 3 | 49 | [OpenSkillGrantMenuPayload.java](com/trbeyond/tensura_better_subs/network/s2c/OpenSkillGrantMenuPayload.java) |
| `OpenHumanoidSkillMenuPayload` | record | CustomPacketPayload | — | 3 | 39 | [OpenHumanoidSkillMenuPayload.java](com/trbeyond/tensura_better_subs/network/s2c/OpenHumanoidSkillMenuPayload.java) |
| `MainScreenSubordinate` | class | SimpleScreen | — | 4 | 167 | [MainScreenSubordinate.java](com/trbeyond/tensura_better_subs/client/screen/MainScreenSubordinate.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_better_subs_common_c2477061c9884123bf5fa4b2314985ff_9305a8ec55452516d9d153cbee8c40009ebf8d625a3660ffe2b02c82b578f237tensura_better_subscommon124devjar/PlatformMethods.java) |
| `IExpandedSkillAcquisition` | interface | — | — | 0 | 63 | [IExpandedSkillAcquisition.java](com/trbeyond/tensura_better_subs/entity/IExpandedSkillAcquisition.java) |
| `IFoodChain` | interface | — | — | 0 | 33 | [IFoodChain.java](com/trbeyond/tensura_better_subs/ability/skill/IFoodChain.java) |
| `SkillGranting` | class | — | — | 3 | 71 | [SkillGranting.java](com/trbeyond/tensura_better_subs/ability/skill/SkillGranting.java) |
