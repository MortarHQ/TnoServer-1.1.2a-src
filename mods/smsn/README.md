# Save My Shit Network（`smsn`）

> jar：`[救救我的答辩网络]smsn.jar` ｜ 版本：`1.2.10-1.21.1` ｜ 作者：Gizmo
> 规模：**47 个类 / 33 个包 / 1493 行** ｜ 事件处理类 0 ｜ Mixin 25 ｜ 注册/管理类 7

**依赖**：`neoforge`、`minecraft`、`cloth_config`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.aika.smsn.neoforge` | 3 | `SMSNClientNeoForge`、`SMSNNeoForge`、`SMSNPlatformImpl` |
| `dev.aika.smsn.config` | 3 | `SMSNConfig`、`SMSNConfigDefault`、`ModConfig` |
| `dev.aika.smsn` | 3 | `ModMixinPlugin`、`SMSNPlatform`、`SMSN` |
| `dev.aika.smsn.client.gui.components` | 3 | `ComponentBuilder`、`AbstractComponentBuilder`、`SwitchBuilder` |
| `dev.aika.smsn.annotation` | 3 | `Components`、`Category`、`LoaderSpecific` |
| `dev.aika.smsn.neoforge.mixin.placebo` | 2 | `WingsManagerMixin`、`TrailsManagerMixin` |
| `dev.aika.smsn.neoforge.mixin.xaero.minimap` | 2 | `PatreonMixin`、`InternetMixin` |
| `dev.aika.smsn.neoforge.mixin.xaero.world_map` | 2 | `PatreonMixin`、`InternetMixin` |
| `dev.aika.smsn.neoforge.mixin.exposure` | 2 | `GildedMixin`、`PatreonMixin` |
| `dev.aika.smsn.neoforge.mixin.additionalstructures` | 1 | `EventsMixin` |
| `dev.aika.smsn.neoforge.mixin.tenshilib` | 1 | `PatreonDataManagerMixin` |
| `dev.aika.smsn.neoforge.mixin.blueprint` | 1 | `RewardHandlerMixin` |
| `dev.aika.smsn.neoforge.mixin.ipn` | 1 | `IPNInfoManagerMixin` |
| `dev.aika.smsn.neoforge.mixin.petrolpark` | 1 | `BadgeHandlerMixin` |
| `dev.aika.smsn.neoforge.mixin.aetherteam` | 1 | `UserData` |
| `dev.aika.smsn.neoforge.mixin.bagus_lib` | 1 | `TierHelperMixin` |
| `dev.aika.smsn.compat` | 1 | `ClothConfigCompat` |
| `dev.aika.smsn.neoforge.mixin.iris` | 1 | `UpdateCheckerMixin` |
| `dev.aika.smsn.neoforge.mixin.projecte` | 1 | `ThreadCheckUUIDMixin` |
| `dev.aika.smsn.neoforge.mixin.hexerei` | 1 | `HexereiSupporterBenefitsMixin` |
| `dev.aika.smsn.neoforge.mixin.supplementaries` | 1 | `CreditsMixin` |
| `dev.aika.smsn.neoforge.mixin.titanium` | 1 | `RewardMixin` |
| `dev.aika.smsn.neoforge.mixin.immersive_engineering` | 1 | `ImmersiveEngineeringMixin` |
| `dev.aika.smsn.neoforge.mixin.ribbits` | 1 | `SupportersJSONMixin` |
| `dev.aika.smsn.neoforge.mixin.immersivecaves` | 1 | `DiscordInviteOnJoinProcedureMixin` |
| `dev.aika.smsn.neoforge.mixin.imb11.mru` | 1 | `APIMixin` |
| `dev.aika.smsn.neoforge.mixin.actuallyadditions` | 1 | `ThreadSpecialFetcherMixin` |
| `dev.aika.smsn.neoforge.compat` | 1 | `ClothConfigCompat` |
| `dev.aika.smsn.client.gui` | 1 | `MissingClothConfigScreen` |
| `dev.aika.smsn.utils` | 1 | `MapUtils` |
| `architectury_inject_smsn_common_1f6de3d3e22c42338b3d6c0aacf1d9c4_1fabca28a437e057e79abf64373893b7954ff35090bc6f138e77d03b042b6b4fsmsncommon12101211devjar` | 1 | `PlatformMethods` |
| `dev.aika.smsn.neoforge.mixin` | 1 | `MixinInfo` |
| `dev.aika.smsn.api` | 1 | `LoaderType` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 47）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EventsMixin` | class | — | Mixin | 2 | 30 | [EventsMixin.java](dev/aika/smsn/neoforge/mixin/additionalstructures/EventsMixin.java) |
| `SMSNClientNeoForge` | class | — | Mod | 0 | 16 | [SMSNClientNeoForge.java](dev/aika/smsn/neoforge/SMSNClientNeoForge.java) |
| `PatreonDataManagerMixin` | class | — | Mixin | 1 | 33 | [PatreonDataManagerMixin.java](dev/aika/smsn/neoforge/mixin/tenshilib/PatreonDataManagerMixin.java) |
| `RewardHandlerMixin` | class | — | Mixin、OnlyIn | 1 | 23 | [RewardHandlerMixin.java](dev/aika/smsn/neoforge/mixin/blueprint/RewardHandlerMixin.java) |
| `WingsManagerMixin` | class | — | Mixin | 1 | 23 | [WingsManagerMixin.java](dev/aika/smsn/neoforge/mixin/placebo/WingsManagerMixin.java) |
| `TrailsManagerMixin` | class | — | Mixin | 1 | 19 | [TrailsManagerMixin.java](dev/aika/smsn/neoforge/mixin/placebo/TrailsManagerMixin.java) |
| `IPNInfoManagerMixin` | class | — | Mixin | 2 | 26 | [IPNInfoManagerMixin.java](dev/aika/smsn/neoforge/mixin/ipn/IPNInfoManagerMixin.java) |
| `BadgeHandlerMixin` | class | — | Mixin | 1 | 20 | [BadgeHandlerMixin.java](dev/aika/smsn/neoforge/mixin/petrolpark/BadgeHandlerMixin.java) |
| `SMSNNeoForge` | class | — | Mod | 0 | 14 | [SMSNNeoForge.java](dev/aika/smsn/neoforge/SMSNNeoForge.java) |
| `SMSNConfig` | class | ModConfig | — | 22 | 192 | [SMSNConfig.java](dev/aika/smsn/config/SMSNConfig.java) |
| `UserData` | class | — | Mixin | 1 | 26 | [UserData$ServerMixin.java](dev/aika/smsn/neoforge/mixin/aetherteam/UserData$ServerMixin.java) |
| `TierHelperMixin` | class | — | Mixin | 1 | 24 | [TierHelperMixin.java](dev/aika/smsn/neoforge/mixin/bagus_lib/TierHelperMixin.java) |
| `ClothConfigCompat` | class | — | — | 5 | 110 | [ClothConfigCompat.java](dev/aika/smsn/compat/ClothConfigCompat.java) |
| `UpdateCheckerMixin` | class | — | Mixin | 1 | 19 | [UpdateCheckerMixin.java](dev/aika/smsn/neoforge/mixin/iris/UpdateCheckerMixin.java) |
| `ThreadCheckUUIDMixin` | class | — | Mixin | 1 | 19 | [ThreadCheckUUIDMixin.java](dev/aika/smsn/neoforge/mixin/projecte/ThreadCheckUUIDMixin.java) |
| `HexereiSupporterBenefitsMixin` | class | — | Mixin | 2 | 28 | [HexereiSupporterBenefitsMixin.java](dev/aika/smsn/neoforge/mixin/hexerei/HexereiSupporterBenefitsMixin.java) |
| `PatreonMixin` | class | — | Mixin | 1 | 20 | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/xaero/minimap/PatreonMixin.java) |
| `InternetMixin` | class | — | Mixin | 1 | 20 | [InternetMixin.java](dev/aika/smsn/neoforge/mixin/xaero/minimap/InternetMixin.java) |
| `PatreonMixin` | class | — | Mixin | 1 | 19 | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/xaero/world_map/PatreonMixin.java) |
| `InternetMixin` | class | — | Mixin | 1 | 19 | [InternetMixin.java](dev/aika/smsn/neoforge/mixin/xaero/world_map/InternetMixin.java) |
| `CreditsMixin` | class | — | Mixin | 1 | 19 | [CreditsMixin.java](dev/aika/smsn/neoforge/mixin/supplementaries/CreditsMixin.java) |
| `RewardMixin` | class | — | Mixin | 1 | 23 | [RewardMixin.java](dev/aika/smsn/neoforge/mixin/titanium/RewardMixin.java) |
| `ImmersiveEngineeringMixin` | class | — | Mixin | 1 | 19 | [ImmersiveEngineeringMixin.java](dev/aika/smsn/neoforge/mixin/immersive_engineering/ImmersiveEngineeringMixin.java) |
| `SupportersJSONMixin` | class | — | Mixin | 1 | 19 | [SupportersJSONMixin.java](dev/aika/smsn/neoforge/mixin/ribbits/SupportersJSONMixin.java) |
| `DiscordInviteOnJoinProcedureMixin` | class | — | Mixin | 1 | 26 | [DiscordInviteOnJoinProcedureMixin.java](dev/aika/smsn/neoforge/mixin/immersivecaves/DiscordInviteOnJoinProcedureMixin.java) |
| `APIMixin` | class | — | Mixin | 1 | 19 | [APIMixin.java](dev/aika/smsn/neoforge/mixin/imb11/mru/APIMixin.java) |
| `GildedMixin` | class | — | Mixin | 1 | 29 | [GildedMixin.java](dev/aika/smsn/neoforge/mixin/exposure/GildedMixin.java) |
| `PatreonMixin` | class | — | Mixin | 1 | 31 | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/exposure/PatreonMixin.java) |
| `ThreadSpecialFetcherMixin` | class | — | Mixin | 1 | 19 | [ThreadSpecialFetcherMixin.java](dev/aika/smsn/neoforge/mixin/actuallyadditions/ThreadSpecialFetcherMixin.java) |
| `ClothConfigCompat` | class | — | — | 1 | 16 | [ClothConfigCompat.java](dev/aika/smsn/neoforge/compat/ClothConfigCompat.java) |
| `MissingClothConfigScreen` | class | Screen | — | 4 | 65 | [MissingClothConfigScreen.java](dev/aika/smsn/client/gui/MissingClothConfigScreen.java) |
| `SMSNConfigDefault` | class | — | — | 0 | 26 | [SMSNConfigDefault.java](dev/aika/smsn/config/SMSNConfigDefault.java) |
| `ModConfig` | class | — | — | 2 | 41 | [ModConfig.java](dev/aika/smsn/config/ModConfig.java) |
| `ModMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 47 | [ModMixinPlugin.java](dev/aika/smsn/ModMixinPlugin.java) |
| `ComponentBuilder` | class | — | OnlyIn | 5 | 53 | [ComponentBuilder.java](dev/aika/smsn/client/gui/components/ComponentBuilder.java) |
| `AbstractComponentBuilder` | class | — | OnlyIn | 8 | 76 | [AbstractComponentBuilder.java](dev/aika/smsn/client/gui/components/AbstractComponentBuilder.java) |
| `MapUtils` | class | — | — | 1 | 18 | [MapUtils.java](dev/aika/smsn/utils/MapUtils.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_smsn_common_1f6de3d3e22c42338b3d6c0aacf1d9c4_1fabca28a437e057e79abf64373893b7954ff35090bc6f138e77d03b042b6b4fsmsncommon12101211devjar/PlatformMethods.java) |
| `SMSNPlatform` | class | — | — | 3 | 28 | [SMSNPlatform.java](dev/aika/smsn/SMSNPlatform.java) |
| `SMSN` | class | — | — | 1 | 17 | [SMSN.java](dev/aika/smsn/SMSN.java) |
| `SMSNPlatformImpl` | class | — | — | 3 | 22 | [SMSNPlatformImpl.java](dev/aika/smsn/neoforge/SMSNPlatformImpl.java) |
| `MixinInfo` | class | — | — | 1 | 62 | [MixinInfo.java](dev/aika/smsn/neoforge/mixin/MixinInfo.java) |
| `Components` | @interface | — | — | 0 | 17 | [Components.java](dev/aika/smsn/annotation/Components.java) |
| `Category` | @interface | — | Retention、Target | 0 | 13 | [Category.java](dev/aika/smsn/annotation/Category.java) |
| `LoaderSpecific` | @interface | — | Retention、Target | 0 | 14 | [LoaderSpecific.java](dev/aika/smsn/annotation/LoaderSpecific.java) |
| `LoaderType` | enum | — | — | 1 | 23 | [LoaderType.java](dev/aika/smsn/api/LoaderType.java) |
| `SwitchBuilder` | class | AbstractComponentBuilder | OnlyIn | 2 | 43 | [SwitchBuilder.java](dev/aika/smsn/client/gui/components/SwitchBuilder.java) |

## Mixin（25 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `EventsMixin` | `Events` | [EventsMixin.java](dev/aika/smsn/neoforge/mixin/additionalstructures/EventsMixin.java) |
| `PatreonDataManagerMixin` | `PatreonDataManager` | [PatreonDataManagerMixin.java](dev/aika/smsn/neoforge/mixin/tenshilib/PatreonDataManagerMixin.java) |
| `RewardHandlerMixin` | `RewardHandler` | [RewardHandlerMixin.java](dev/aika/smsn/neoforge/mixin/blueprint/RewardHandlerMixin.java) |
| `WingsManagerMixin` | `WingsManager` | [WingsManagerMixin.java](dev/aika/smsn/neoforge/mixin/placebo/WingsManagerMixin.java) |
| `TrailsManagerMixin` | `TrailsManager` | [TrailsManagerMixin.java](dev/aika/smsn/neoforge/mixin/placebo/TrailsManagerMixin.java) |
| `IPNInfoManagerMixin` | `IPNInfoManager` | [IPNInfoManagerMixin.java](dev/aika/smsn/neoforge/mixin/ipn/IPNInfoManagerMixin.java) |
| `BadgeHandlerMixin` | `BadgeHandler` | [BadgeHandlerMixin.java](dev/aika/smsn/neoforge/mixin/petrolpark/BadgeHandlerMixin.java) |
| `UserData` | `UserData` | [UserData$ServerMixin.java](dev/aika/smsn/neoforge/mixin/aetherteam/UserData$ServerMixin.java) |
| `TierHelperMixin` | `TierHelper` | [TierHelperMixin.java](dev/aika/smsn/neoforge/mixin/bagus_lib/TierHelperMixin.java) |
| `UpdateCheckerMixin` | `UpdateChecker` | [UpdateCheckerMixin.java](dev/aika/smsn/neoforge/mixin/iris/UpdateCheckerMixin.java) |
| `ThreadCheckUUIDMixin` | `ThreadCheckUUID` | [ThreadCheckUUIDMixin.java](dev/aika/smsn/neoforge/mixin/projecte/ThreadCheckUUIDMixin.java) |
| `HexereiSupporterBenefitsMixin` | `HexereiSupporterBenefits` | [HexereiSupporterBenefitsMixin.java](dev/aika/smsn/neoforge/mixin/hexerei/HexereiSupporterBenefitsMixin.java) |
| `PatreonMixin` | `Patreon` | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/xaero/minimap/PatreonMixin.java) |
| `InternetMixin` | `Internet` | [InternetMixin.java](dev/aika/smsn/neoforge/mixin/xaero/minimap/InternetMixin.java) |
| `PatreonMixin` | `Patreon` | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/xaero/world_map/PatreonMixin.java) |
| `InternetMixin` | `Internet` | [InternetMixin.java](dev/aika/smsn/neoforge/mixin/xaero/world_map/InternetMixin.java) |
| `CreditsMixin` | `Credits` | [CreditsMixin.java](dev/aika/smsn/neoforge/mixin/supplementaries/CreditsMixin.java) |
| `RewardMixin` | `Reward` | [RewardMixin.java](dev/aika/smsn/neoforge/mixin/titanium/RewardMixin.java) |
| `ImmersiveEngineeringMixin` | `ImmersiveEngineering` | [ImmersiveEngineeringMixin.java](dev/aika/smsn/neoforge/mixin/immersive_engineering/ImmersiveEngineeringMixin.java) |
| `SupportersJSONMixin` | `SupportersJSON` | [SupportersJSONMixin.java](dev/aika/smsn/neoforge/mixin/ribbits/SupportersJSONMixin.java) |
| `DiscordInviteOnJoinProcedureMixin` | `DiscordInviteOnJoinProcedure` | [DiscordInviteOnJoinProcedureMixin.java](dev/aika/smsn/neoforge/mixin/immersivecaves/DiscordInviteOnJoinProcedureMixin.java) |
| `APIMixin` | `API` | [APIMixin.java](dev/aika/smsn/neoforge/mixin/imb11/mru/APIMixin.java) |
| `GildedMixin` | `Gilded` | [GildedMixin.java](dev/aika/smsn/neoforge/mixin/exposure/GildedMixin.java) |
| `PatreonMixin` | `Patreon` | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/exposure/PatreonMixin.java) |
| `ThreadSpecialFetcherMixin` | `ThreadSpecialFetcher` | [ThreadSpecialFetcherMixin.java](dev/aika/smsn/neoforge/mixin/actuallyadditions/ThreadSpecialFetcherMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `SMSNConfig` | — | [SMSNConfig.java](dev/aika/smsn/config/SMSNConfig.java) |
| `ClothConfigCompat` | — | [ClothConfigCompat.java](dev/aika/smsn/compat/ClothConfigCompat.java) |
| `ClothConfigCompat` | — | [ClothConfigCompat.java](dev/aika/smsn/neoforge/compat/ClothConfigCompat.java) |
| `MissingClothConfigScreen` | — | [MissingClothConfigScreen.java](dev/aika/smsn/client/gui/MissingClothConfigScreen.java) |
| `SMSNConfigDefault` | — | [SMSNConfigDefault.java](dev/aika/smsn/config/SMSNConfigDefault.java) |
| `ModConfig` | — | [ModConfig.java](dev/aika/smsn/config/ModConfig.java) |

## 全部类（47）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EventsMixin` | class | — | Mixin | 2 | 30 | [EventsMixin.java](dev/aika/smsn/neoforge/mixin/additionalstructures/EventsMixin.java) |
| `SMSNClientNeoForge` | class | — | Mod | 0 | 16 | [SMSNClientNeoForge.java](dev/aika/smsn/neoforge/SMSNClientNeoForge.java) |
| `PatreonDataManagerMixin` | class | — | Mixin | 1 | 33 | [PatreonDataManagerMixin.java](dev/aika/smsn/neoforge/mixin/tenshilib/PatreonDataManagerMixin.java) |
| `RewardHandlerMixin` | class | — | Mixin、OnlyIn | 1 | 23 | [RewardHandlerMixin.java](dev/aika/smsn/neoforge/mixin/blueprint/RewardHandlerMixin.java) |
| `WingsManagerMixin` | class | — | Mixin | 1 | 23 | [WingsManagerMixin.java](dev/aika/smsn/neoforge/mixin/placebo/WingsManagerMixin.java) |
| `TrailsManagerMixin` | class | — | Mixin | 1 | 19 | [TrailsManagerMixin.java](dev/aika/smsn/neoforge/mixin/placebo/TrailsManagerMixin.java) |
| `IPNInfoManagerMixin` | class | — | Mixin | 2 | 26 | [IPNInfoManagerMixin.java](dev/aika/smsn/neoforge/mixin/ipn/IPNInfoManagerMixin.java) |
| `BadgeHandlerMixin` | class | — | Mixin | 1 | 20 | [BadgeHandlerMixin.java](dev/aika/smsn/neoforge/mixin/petrolpark/BadgeHandlerMixin.java) |
| `SMSNNeoForge` | class | — | Mod | 0 | 14 | [SMSNNeoForge.java](dev/aika/smsn/neoforge/SMSNNeoForge.java) |
| `SMSNConfig` | class | ModConfig | — | 22 | 192 | [SMSNConfig.java](dev/aika/smsn/config/SMSNConfig.java) |
| `UserData` | class | — | Mixin | 1 | 26 | [UserData$ServerMixin.java](dev/aika/smsn/neoforge/mixin/aetherteam/UserData$ServerMixin.java) |
| `TierHelperMixin` | class | — | Mixin | 1 | 24 | [TierHelperMixin.java](dev/aika/smsn/neoforge/mixin/bagus_lib/TierHelperMixin.java) |
| `ClothConfigCompat` | class | — | — | 5 | 110 | [ClothConfigCompat.java](dev/aika/smsn/compat/ClothConfigCompat.java) |
| `UpdateCheckerMixin` | class | — | Mixin | 1 | 19 | [UpdateCheckerMixin.java](dev/aika/smsn/neoforge/mixin/iris/UpdateCheckerMixin.java) |
| `ThreadCheckUUIDMixin` | class | — | Mixin | 1 | 19 | [ThreadCheckUUIDMixin.java](dev/aika/smsn/neoforge/mixin/projecte/ThreadCheckUUIDMixin.java) |
| `HexereiSupporterBenefitsMixin` | class | — | Mixin | 2 | 28 | [HexereiSupporterBenefitsMixin.java](dev/aika/smsn/neoforge/mixin/hexerei/HexereiSupporterBenefitsMixin.java) |
| `PatreonMixin` | class | — | Mixin | 1 | 20 | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/xaero/minimap/PatreonMixin.java) |
| `InternetMixin` | class | — | Mixin | 1 | 20 | [InternetMixin.java](dev/aika/smsn/neoforge/mixin/xaero/minimap/InternetMixin.java) |
| `PatreonMixin` | class | — | Mixin | 1 | 19 | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/xaero/world_map/PatreonMixin.java) |
| `InternetMixin` | class | — | Mixin | 1 | 19 | [InternetMixin.java](dev/aika/smsn/neoforge/mixin/xaero/world_map/InternetMixin.java) |
| `CreditsMixin` | class | — | Mixin | 1 | 19 | [CreditsMixin.java](dev/aika/smsn/neoforge/mixin/supplementaries/CreditsMixin.java) |
| `RewardMixin` | class | — | Mixin | 1 | 23 | [RewardMixin.java](dev/aika/smsn/neoforge/mixin/titanium/RewardMixin.java) |
| `ImmersiveEngineeringMixin` | class | — | Mixin | 1 | 19 | [ImmersiveEngineeringMixin.java](dev/aika/smsn/neoforge/mixin/immersive_engineering/ImmersiveEngineeringMixin.java) |
| `SupportersJSONMixin` | class | — | Mixin | 1 | 19 | [SupportersJSONMixin.java](dev/aika/smsn/neoforge/mixin/ribbits/SupportersJSONMixin.java) |
| `DiscordInviteOnJoinProcedureMixin` | class | — | Mixin | 1 | 26 | [DiscordInviteOnJoinProcedureMixin.java](dev/aika/smsn/neoforge/mixin/immersivecaves/DiscordInviteOnJoinProcedureMixin.java) |
| `APIMixin` | class | — | Mixin | 1 | 19 | [APIMixin.java](dev/aika/smsn/neoforge/mixin/imb11/mru/APIMixin.java) |
| `GildedMixin` | class | — | Mixin | 1 | 29 | [GildedMixin.java](dev/aika/smsn/neoforge/mixin/exposure/GildedMixin.java) |
| `PatreonMixin` | class | — | Mixin | 1 | 31 | [PatreonMixin.java](dev/aika/smsn/neoforge/mixin/exposure/PatreonMixin.java) |
| `ThreadSpecialFetcherMixin` | class | — | Mixin | 1 | 19 | [ThreadSpecialFetcherMixin.java](dev/aika/smsn/neoforge/mixin/actuallyadditions/ThreadSpecialFetcherMixin.java) |
| `ClothConfigCompat` | class | — | — | 1 | 16 | [ClothConfigCompat.java](dev/aika/smsn/neoforge/compat/ClothConfigCompat.java) |
| `MissingClothConfigScreen` | class | Screen | — | 4 | 65 | [MissingClothConfigScreen.java](dev/aika/smsn/client/gui/MissingClothConfigScreen.java) |
| `SMSNConfigDefault` | class | — | — | 0 | 26 | [SMSNConfigDefault.java](dev/aika/smsn/config/SMSNConfigDefault.java) |
| `ModConfig` | class | — | — | 2 | 41 | [ModConfig.java](dev/aika/smsn/config/ModConfig.java) |
| `ModMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 47 | [ModMixinPlugin.java](dev/aika/smsn/ModMixinPlugin.java) |
| `ComponentBuilder` | class | — | OnlyIn | 5 | 53 | [ComponentBuilder.java](dev/aika/smsn/client/gui/components/ComponentBuilder.java) |
| `AbstractComponentBuilder` | class | — | OnlyIn | 8 | 76 | [AbstractComponentBuilder.java](dev/aika/smsn/client/gui/components/AbstractComponentBuilder.java) |
| `MapUtils` | class | — | — | 1 | 18 | [MapUtils.java](dev/aika/smsn/utils/MapUtils.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_smsn_common_1f6de3d3e22c42338b3d6c0aacf1d9c4_1fabca28a437e057e79abf64373893b7954ff35090bc6f138e77d03b042b6b4fsmsncommon12101211devjar/PlatformMethods.java) |
| `SMSNPlatform` | class | — | — | 3 | 28 | [SMSNPlatform.java](dev/aika/smsn/SMSNPlatform.java) |
| `SMSN` | class | — | — | 1 | 17 | [SMSN.java](dev/aika/smsn/SMSN.java) |
| `SMSNPlatformImpl` | class | — | — | 3 | 22 | [SMSNPlatformImpl.java](dev/aika/smsn/neoforge/SMSNPlatformImpl.java) |
| `MixinInfo` | class | — | — | 1 | 62 | [MixinInfo.java](dev/aika/smsn/neoforge/mixin/MixinInfo.java) |
| `Components` | @interface | — | — | 0 | 17 | [Components.java](dev/aika/smsn/annotation/Components.java) |
| `Category` | @interface | — | Retention、Target | 0 | 13 | [Category.java](dev/aika/smsn/annotation/Category.java) |
| `LoaderSpecific` | @interface | — | Retention、Target | 0 | 14 | [LoaderSpecific.java](dev/aika/smsn/annotation/LoaderSpecific.java) |
| `LoaderType` | enum | — | — | 1 | 23 | [LoaderType.java](dev/aika/smsn/api/LoaderType.java) |
| `SwitchBuilder` | class | AbstractComponentBuilder | OnlyIn | 2 | 43 | [SwitchBuilder.java](dev/aika/smsn/client/gui/components/SwitchBuilder.java) |
