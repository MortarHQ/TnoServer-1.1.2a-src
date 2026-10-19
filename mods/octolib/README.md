# OctoLib（`octolib`）

> jar：`[前置]OctoLib.jar` ｜ 版本：`0.6.0.4` ｜ 作者：OctoStudios
> 规模：**112 个类 / 29 个包 / 6806 行** ｜ 事件处理类 1 ｜ Mixin 8 ｜ 注册/管理类 5

**官方描述**：Collection of shared code for OctoStudios' mods

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `it.hurts.octostudios.octolib.module.config.cfgbuilder` | 11 | `CompoundEntry`、`ConfigEntry`、`CfgTag` |
| `it.hurts.octostudios.octolib.mixin` | 8 | `AbstractContainerScreenMixin`、`GameRendererMixin`、`ScreenMixin` |
| `it.hurts.octostudios.octolib.client.animation` | 7 | `Tween`、`PropertyTweener`、`Tweener` |
| `it.hurts.octostudios.octolib.module.config.util` | 7 | `ConfigEntryConverter`、`ConfigUtils`、`ConstructorExt` |
| `it.hurts.octostudios.octolib.util` | 7 | `RenderUtils`、`VectorUtils`、`AnimationUtils` |
| `it.hurts.octostudios.octolib.module.config.cfgbuilder.scalar` | 7 | `BinaryEntry`、`StringEntry`、`DoubleEntry` |
| `it.hurts.octostudios.octolib.module.particle.trail` | 6 | `EntityTrailRegistry`、`TrailProvider`、`DefaultTrailBuffer` |
| `it.hurts.octostudios.octolib.module.config.annotation` | 6 | `Prop`、`ParameterizedProp`、`TypeProp` |
| `it.hurts.octostudios.octolib.module.config.annotation.registration` | 5 | `ConfigRegistration`、`Config`、`AnnotationConfigFactory` |
| `it.hurts.octostudios.octolib.client.particle` | 5 | `UIParticle`、`Transform`、`ExtendedUIParticle` |
| `it.hurts.octostudios.octolib.module.config.impl` | 5 | `CompoundConfig`、`ConfigSide`、`FileSpreadConfig` |
| `it.hurts.octostudios.octolib.client.screen.widget` | 4 | `TestGear`、`TestPin`、`HasRenderMatrix` |
| `it.hurts.octostudios.octolib.module.config.network` | 3 | `SyncConfigPacket`、`TestScreenPacket`、`UnholyAbominationPacket` |
| `it.hurts.octostudios.octolib.module.particle` | 3 | `OctoRenderManager`、`RenderProvider`、`RenderBuffer` |
| `it.hurts.octostudios.octolib.module.config.loader` | 3 | `SolidConfigLoader`、`IConfigFileLoader`、`FileSpreadLoader` |
| `it.hurts.octostudios.octolib.module.config.util.properties` | 3 | `GenericPropertyExt`、`MethodPropertyExt`、`FieldPropertyExt` |
| `it.hurts.octostudios.octolib.client.shake` | 3 | `ShakeData`、`Shakeable`、`ShakeSystem` |
| `it.hurts.octostudios.octolib.client.animation.easing` | 3 | `EaseType`、`Interpolator`、`TransitionType` |
| `it.hurts.octostudios.octolib.neoforge` | 2 | `OctoLibNeoForge`、`OctoLibNeoForgeClient` |
| `it.hurts.octostudios.octolib.module.config.provider` | 2 | `ConfigProviderBase`、`ConfigProvider` |
| `it.hurts.octostudios.octolib.module.network` | 2 | `Packet`、`OctolibNetwork` |
| `it.hurts.octostudios.octolib.client` | 2 | `TestScreen`、`TestWidget` |
| `it.hurts.octostudios.octolib` | 2 | `OctoLibClient`、`OctoLib` |
| `it.hurts.octostudios.octolib.event` | 1 | `LevelRenderEvents` |
| `it.hurts.octostudios.octolib.module.config` | 1 | `ConfigManager` |
| `it.hurts.octostudios.octolib.module` | 1 | `ConfigTest` |
| `it.hurts.octostudios.octolib.client.screen` | 1 | `TestGearScreen` |
| `it.hurts.octostudios.octolib.module.command` | 1 | `OctolibCommand` |
| `architectury_inject_octolib_common_02d17019834d42558d69f7778146eb07_2b9ffff7a9504b23694122d199f460091384ced36c30371c66c10d3da02a36eaOctoLibCOMMON0604121devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LevelRenderEvents` | class | — | EventBusSubscriber | 1 | 40 | [LevelRenderEvents.java](it/hurts/octostudios/octolib/event/LevelRenderEvents.java) |
| `ConfigManager` | class | — | — | 21 | 215 | [ConfigManager.java](it/hurts/octostudios/octolib/module/config/ConfigManager.java) |
| `OctoLibNeoForge` | class | — | Mod | 0 | 18 | [OctoLibNeoForge.java](it/hurts/octostudios/octolib/neoforge/OctoLibNeoForge.java) |
| `ConfigRegistration` | interface | — | — | 0 | 8 | [ConfigRegistration.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ConfigRegistration.java) |
| `AbstractContainerScreenMixin` | class | — | Mixin | 1 | 29 | [AbstractContainerScreenMixin.java](it/hurts/octostudios/octolib/mixin/AbstractContainerScreenMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 1 | 21 | [GameRendererMixin.java](it/hurts/octostudios/octolib/mixin/GameRendererMixin.java) |
| `ScreenMixin` | class | — | Mixin | 1 | 23 | [ScreenMixin.java](it/hurts/octostudios/octolib/mixin/ScreenMixin.java) |
| `ClientLevelMixin` | class | — | Mixin | 1 | 23 | [ClientLevelMixin.java](it/hurts/octostudios/octolib/mixin/ClientLevelMixin.java) |
| `EntityTrailRegistry` | class | — | — | 2 | 21 | [EntityTrailRegistry.java](it/hurts/octostudios/octolib/module/particle/trail/EntityTrailRegistry.java) |
| `AbstractWidgetMixin` | class | — | Mixin | 8 | 80 | [AbstractWidgetMixin.java](it/hurts/octostudios/octolib/mixin/AbstractWidgetMixin.java) |
| `UIParticle` | class | — | — | 52 | 388 | [UIParticle.java](it/hurts/octostudios/octolib/client/particle/UIParticle.java) |
| `Tween` | class | — | — | 34 | 260 | [Tween.java](it/hurts/octostudios/octolib/client/animation/Tween.java) |
| `ConfigProviderBase` | class | ConfigProvider | — | 18 | 192 | [ConfigProviderBase.java](it/hurts/octostudios/octolib/module/config/provider/ConfigProviderBase.java) |
| `SyncConfigPacket` | class | Packet | — | 3 | 45 | [SyncConfigPacket.java](it/hurts/octostudios/octolib/module/config/network/SyncConfigPacket.java) |
| `CompoundEntry` | class | ConfigEntry | — | 37 | 289 | [CompoundEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/CompoundEntry.java) |
| `MinecraftMixin` | class | — | Mixin | 3 | 35 | [MinecraftMixin.java](it/hurts/octostudios/octolib/mixin/MinecraftMixin.java) |
| `GuiMixin` | class | — | Mixin | 1 | 19 | [GuiMixin.java](it/hurts/octostudios/octolib/mixin/GuiMixin.java) |
| `AbstractWidgetAccessor` | interface | — | Mixin | 0 | 15 | [AbstractWidgetAccessor.java](it/hurts/octostudios/octolib/mixin/AbstractWidgetAccessor.java) |
| `ConfigEntry` | class | — | TypePropInherited | 14 | 86 | [ConfigEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ConfigEntry.java) |
| `OctoRenderManager` | class | — | — | 6 | 68 | [OctoRenderManager.java](it/hurts/octostudios/octolib/module/particle/OctoRenderManager.java) |
| `TestScreenPacket` | class | Packet | — | 3 | 39 | [TestScreenPacket.java](it/hurts/octostudios/octolib/module/config/network/TestScreenPacket.java) |
| `CompoundConfig` | class | OctoConfig | — | 5 | 28 | [CompoundConfig.java](it/hurts/octostudios/octolib/module/config/impl/CompoundConfig.java) |
| `ConfigEntryConverter` | class | — | — | 5 | 144 | [ConfigEntryConverter.java](it/hurts/octostudios/octolib/module/config/util/ConfigEntryConverter.java) |
| `ConfigUtils` | class | — | — | 1 | 43 | [ConfigUtils.java](it/hurts/octostudios/octolib/module/config/util/ConfigUtils.java) |
| `ConfigTest` | class | — | — | 0 | 17 | [ConfigTest.java](it/hurts/octostudios/octolib/module/ConfigTest.java) |
| `Packet` | class | CustomPacketPayload | — | 6 | 49 | [Packet.java](it/hurts/octostudios/octolib/module/network/Packet.java) |
| `ConfigProvider` | interface | — | — | 0 | 23 | [ConfigProvider.java](it/hurts/octostudios/octolib/module/config/provider/ConfigProvider.java) |
| `ConfigSide` | enum | — | — | 0 | 7 | [ConfigSide.java](it/hurts/octostudios/octolib/module/config/impl/ConfigSide.java) |
| `FileSpreadConfig` | class | OctoConfigBase | — | 1 | 20 | [FileSpreadConfig.java](it/hurts/octostudios/octolib/module/config/impl/FileSpreadConfig.java) |
| `OctoConfig` | interface | — | — | 0 | 21 | [OctoConfig.java](it/hurts/octostudios/octolib/module/config/impl/OctoConfig.java) |
| `OctoConfigBase` | class | OctoConfig | — | 4 | 37 | [OctoConfigBase.java](it/hurts/octostudios/octolib/module/config/impl/OctoConfigBase.java) |
| `Config` | @interface | — | Retention、Target | 0 | 13 | [Config.java](it/hurts/octostudios/octolib/module/config/annotation/registration/Config.java) |
| `AnnotationConfigFactory` | interface | — | — | 0 | 10 | [AnnotationConfigFactory.java](it/hurts/octostudios/octolib/module/config/annotation/registration/AnnotationConfigFactory.java) |
| `ConfigNameGetter` | interface | — | — | 0 | 9 | [ConfigNameGetter.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ConfigNameGetter.java) |
| `ObjectConfig` | @interface | — | Retention、Target | 0 | 23 | [ObjectConfig.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ObjectConfig.java) |
| `ConstructorExt` | class | org.yaml.snakeyaml.constructor.Const | — | 16 | 305 | [ConstructorExt.java](it/hurts/octostudios/octolib/module/config/util/ConstructorExt.java) |
| `SolidConfigLoader` | class | IConfigFileLoader<T | — | 2 | 39 | [SolidConfigLoader.java](it/hurts/octostudios/octolib/module/config/loader/SolidConfigLoader.java) |
| `IConfigFileLoader` | interface | — | — | 0 | 13 | [IConfigFileLoader.java](it/hurts/octostudios/octolib/module/config/loader/IConfigFileLoader.java) |
| `TrailProvider` | class | RenderProvider<TrailProvider | — | 18 | 237 | [TrailProvider.java](it/hurts/octostudios/octolib/module/particle/trail/TrailProvider.java) |
| `TestScreen` | class | Screen | — | 6 | 99 | [TestScreen.java](it/hurts/octostudios/octolib/client/TestScreen.java) |
| `TestGearScreen` | class | Screen | — | 5 | 72 | [TestGearScreen.java](it/hurts/octostudios/octolib/client/screen/TestGearScreen.java) |
| `OctolibNetwork` | class | — | — | 3 | 35 | [OctolibNetwork.java](it/hurts/octostudios/octolib/module/network/OctolibNetwork.java) |
| `UnholyAbominationPacket` | class | Packet | — | 3 | 57 | [UnholyAbominationPacket.java](it/hurts/octostudios/octolib/module/config/network/UnholyAbominationPacket.java) |
| `RepresenterExt` | class | Representer | — | 11 | 304 | [RepresenterExt.java](it/hurts/octostudios/octolib/module/config/util/RepresenterExt.java) |
| `OctolibCommand` | class | — | — | 1 | 127 | [OctolibCommand.java](it/hurts/octostudios/octolib/module/command/OctolibCommand.java) |
| `OctoLibClient` | class | — | — | 3 | 25 | [OctoLibClient.java](it/hurts/octostudios/octolib/OctoLibClient.java) |
| `OctoLibNeoForgeClient` | class | — | — | 0 | 11 | [OctoLibNeoForgeClient.java](it/hurts/octostudios/octolib/neoforge/OctoLibNeoForgeClient.java) |
| `RenderUtils` | class | — | — | 7 | 119 | [RenderUtils.java](it/hurts/octostudios/octolib/util/RenderUtils.java) |
| `VectorUtils` | class | — | — | 7 | 76 | [VectorUtils.java](it/hurts/octostudios/octolib/util/VectorUtils.java) |
| `AnimationUtils` | class | — | — | 8 | 165 | [AnimationUtils.java](it/hurts/octostudios/octolib/util/AnimationUtils.java) |
| `OctoColor` | class | — | — | 14 | 121 | [OctoColor.java](it/hurts/octostudios/octolib/util/OctoColor.java) |
| `TestGear` | class | AbstractWidget | — | 11 | 94 | [TestGear.java](it/hurts/octostudios/octolib/client/screen/widget/TestGear.java) |
| `Transform` | class | — | — | 14 | 87 | [Transform.java](it/hurts/octostudios/octolib/client/particle/Transform.java) |
| `ExtendedUIParticle` | class | UIParticle | — | 10 | 72 | [ExtendedUIParticle.java](it/hurts/octostudios/octolib/client/particle/ExtendedUIParticle.java) |
| `PropertyTweener` | class | Tweener | — | 12 | 181 | [PropertyTweener.java](it/hurts/octostudios/octolib/client/animation/PropertyTweener.java) |
| `CfgTag` | class | — | — | 12 | 99 | [CfgTag.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/CfgTag.java) |
| `ArrayEntry` | class | ConfigEntry | — | 10 | 106 | [ArrayEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ArrayEntry.java) |
| `GenericPropertyExt` | class | GenericProperty | — | 11 | 67 | [GenericPropertyExt.java](it/hurts/octostudios/octolib/module/config/util/properties/GenericPropertyExt.java) |
| `DefaultTrailBuffer` | class | TrailBuffer | — | 11 | 93 | [DefaultTrailBuffer.java](it/hurts/octostudios/octolib/module/particle/trail/DefaultTrailBuffer.java) |
| `EntityTrailProvider` | class | TrailProvider | — | 4 | 33 | [EntityTrailProvider.java](it/hurts/octostudios/octolib/module/particle/trail/EntityTrailProvider.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `LevelRenderEvents` | 1 | `it.hurts.octostudios.octolib.event` | [LevelRenderEvents.java](it/hurts/octostudios/octolib/event/LevelRenderEvents.java) |

## Mixin（8 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](it/hurts/octostudios/octolib/mixin/AbstractContainerScreenMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](it/hurts/octostudios/octolib/mixin/GameRendererMixin.java) |
| `ScreenMixin` | `Screen` | [ScreenMixin.java](it/hurts/octostudios/octolib/mixin/ScreenMixin.java) |
| `ClientLevelMixin` | `ClientLevel` | [ClientLevelMixin.java](it/hurts/octostudios/octolib/mixin/ClientLevelMixin.java) |
| `AbstractWidgetMixin` | `AbstractWidget` | [AbstractWidgetMixin.java](it/hurts/octostudios/octolib/mixin/AbstractWidgetMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](it/hurts/octostudios/octolib/mixin/MinecraftMixin.java) |
| `GuiMixin` | `Gui` | [GuiMixin.java](it/hurts/octostudios/octolib/mixin/GuiMixin.java) |
| `AbstractWidgetAccessor` | `AbstractWidgetAccessor` | [AbstractWidgetAccessor.java](it/hurts/octostudios/octolib/mixin/AbstractWidgetAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigManager` | — | [ConfigManager.java](it/hurts/octostudios/octolib/module/config/ConfigManager.java) |
| `ConfigRegistration` | — | [ConfigRegistration.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ConfigRegistration.java) |
| `ConfigProviderBase` | — | [ConfigProviderBase.java](it/hurts/octostudios/octolib/module/config/provider/ConfigProviderBase.java) |
| `SyncConfigPacket` | — | [SyncConfigPacket.java](it/hurts/octostudios/octolib/module/config/network/SyncConfigPacket.java) |
| `ConfigEntry` | TypePropInherited | [ConfigEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ConfigEntry.java) |
| `CompoundConfig` | — | [CompoundConfig.java](it/hurts/octostudios/octolib/module/config/impl/CompoundConfig.java) |
| `ConfigEntryConverter` | — | [ConfigEntryConverter.java](it/hurts/octostudios/octolib/module/config/util/ConfigEntryConverter.java) |
| `ConfigUtils` | — | [ConfigUtils.java](it/hurts/octostudios/octolib/module/config/util/ConfigUtils.java) |
| `ConfigTest` | — | [ConfigTest.java](it/hurts/octostudios/octolib/module/ConfigTest.java) |
| `ConfigProvider` | — | [ConfigProvider.java](it/hurts/octostudios/octolib/module/config/provider/ConfigProvider.java) |
| `ConfigSide` | — | [ConfigSide.java](it/hurts/octostudios/octolib/module/config/impl/ConfigSide.java) |
| `FileSpreadConfig` | — | [FileSpreadConfig.java](it/hurts/octostudios/octolib/module/config/impl/FileSpreadConfig.java) |
| `OctoConfig` | — | [OctoConfig.java](it/hurts/octostudios/octolib/module/config/impl/OctoConfig.java) |
| `OctoConfigBase` | — | [OctoConfigBase.java](it/hurts/octostudios/octolib/module/config/impl/OctoConfigBase.java) |
| `Config` | Retention;Target | [Config.java](it/hurts/octostudios/octolib/module/config/annotation/registration/Config.java) |
| `AnnotationConfigFactory` | — | [AnnotationConfigFactory.java](it/hurts/octostudios/octolib/module/config/annotation/registration/AnnotationConfigFactory.java) |
| `ConfigNameGetter` | — | [ConfigNameGetter.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ConfigNameGetter.java) |
| `ObjectConfig` | Retention;Target | [ObjectConfig.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ObjectConfig.java) |
| `SolidConfigLoader` | — | [SolidConfigLoader.java](it/hurts/octostudios/octolib/module/config/loader/SolidConfigLoader.java) |
| `IConfigFileLoader` | — | [IConfigFileLoader.java](it/hurts/octostudios/octolib/module/config/loader/IConfigFileLoader.java) |

## 全部类（112）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LevelRenderEvents` | class | — | EventBusSubscriber | 1 | 40 | [LevelRenderEvents.java](it/hurts/octostudios/octolib/event/LevelRenderEvents.java) |
| `ConfigManager` | class | — | — | 21 | 215 | [ConfigManager.java](it/hurts/octostudios/octolib/module/config/ConfigManager.java) |
| `OctoLibNeoForge` | class | — | Mod | 0 | 18 | [OctoLibNeoForge.java](it/hurts/octostudios/octolib/neoforge/OctoLibNeoForge.java) |
| `ConfigRegistration` | interface | — | — | 0 | 8 | [ConfigRegistration.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ConfigRegistration.java) |
| `AbstractContainerScreenMixin` | class | — | Mixin | 1 | 29 | [AbstractContainerScreenMixin.java](it/hurts/octostudios/octolib/mixin/AbstractContainerScreenMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 1 | 21 | [GameRendererMixin.java](it/hurts/octostudios/octolib/mixin/GameRendererMixin.java) |
| `ScreenMixin` | class | — | Mixin | 1 | 23 | [ScreenMixin.java](it/hurts/octostudios/octolib/mixin/ScreenMixin.java) |
| `ClientLevelMixin` | class | — | Mixin | 1 | 23 | [ClientLevelMixin.java](it/hurts/octostudios/octolib/mixin/ClientLevelMixin.java) |
| `EntityTrailRegistry` | class | — | — | 2 | 21 | [EntityTrailRegistry.java](it/hurts/octostudios/octolib/module/particle/trail/EntityTrailRegistry.java) |
| `AbstractWidgetMixin` | class | — | Mixin | 8 | 80 | [AbstractWidgetMixin.java](it/hurts/octostudios/octolib/mixin/AbstractWidgetMixin.java) |
| `UIParticle` | class | — | — | 52 | 388 | [UIParticle.java](it/hurts/octostudios/octolib/client/particle/UIParticle.java) |
| `Tween` | class | — | — | 34 | 260 | [Tween.java](it/hurts/octostudios/octolib/client/animation/Tween.java) |
| `ConfigProviderBase` | class | ConfigProvider | — | 18 | 192 | [ConfigProviderBase.java](it/hurts/octostudios/octolib/module/config/provider/ConfigProviderBase.java) |
| `SyncConfigPacket` | class | Packet | — | 3 | 45 | [SyncConfigPacket.java](it/hurts/octostudios/octolib/module/config/network/SyncConfigPacket.java) |
| `CompoundEntry` | class | ConfigEntry | — | 37 | 289 | [CompoundEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/CompoundEntry.java) |
| `MinecraftMixin` | class | — | Mixin | 3 | 35 | [MinecraftMixin.java](it/hurts/octostudios/octolib/mixin/MinecraftMixin.java) |
| `GuiMixin` | class | — | Mixin | 1 | 19 | [GuiMixin.java](it/hurts/octostudios/octolib/mixin/GuiMixin.java) |
| `AbstractWidgetAccessor` | interface | — | Mixin | 0 | 15 | [AbstractWidgetAccessor.java](it/hurts/octostudios/octolib/mixin/AbstractWidgetAccessor.java) |
| `ConfigEntry` | class | — | TypePropInherited | 14 | 86 | [ConfigEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ConfigEntry.java) |
| `OctoRenderManager` | class | — | — | 6 | 68 | [OctoRenderManager.java](it/hurts/octostudios/octolib/module/particle/OctoRenderManager.java) |
| `TestScreenPacket` | class | Packet | — | 3 | 39 | [TestScreenPacket.java](it/hurts/octostudios/octolib/module/config/network/TestScreenPacket.java) |
| `CompoundConfig` | class | OctoConfig | — | 5 | 28 | [CompoundConfig.java](it/hurts/octostudios/octolib/module/config/impl/CompoundConfig.java) |
| `ConfigEntryConverter` | class | — | — | 5 | 144 | [ConfigEntryConverter.java](it/hurts/octostudios/octolib/module/config/util/ConfigEntryConverter.java) |
| `ConfigUtils` | class | — | — | 1 | 43 | [ConfigUtils.java](it/hurts/octostudios/octolib/module/config/util/ConfigUtils.java) |
| `ConfigTest` | class | — | — | 0 | 17 | [ConfigTest.java](it/hurts/octostudios/octolib/module/ConfigTest.java) |
| `Packet` | class | CustomPacketPayload | — | 6 | 49 | [Packet.java](it/hurts/octostudios/octolib/module/network/Packet.java) |
| `ConfigProvider` | interface | — | — | 0 | 23 | [ConfigProvider.java](it/hurts/octostudios/octolib/module/config/provider/ConfigProvider.java) |
| `ConfigSide` | enum | — | — | 0 | 7 | [ConfigSide.java](it/hurts/octostudios/octolib/module/config/impl/ConfigSide.java) |
| `FileSpreadConfig` | class | OctoConfigBase | — | 1 | 20 | [FileSpreadConfig.java](it/hurts/octostudios/octolib/module/config/impl/FileSpreadConfig.java) |
| `OctoConfig` | interface | — | — | 0 | 21 | [OctoConfig.java](it/hurts/octostudios/octolib/module/config/impl/OctoConfig.java) |
| `OctoConfigBase` | class | OctoConfig | — | 4 | 37 | [OctoConfigBase.java](it/hurts/octostudios/octolib/module/config/impl/OctoConfigBase.java) |
| `Config` | @interface | — | Retention、Target | 0 | 13 | [Config.java](it/hurts/octostudios/octolib/module/config/annotation/registration/Config.java) |
| `AnnotationConfigFactory` | interface | — | — | 0 | 10 | [AnnotationConfigFactory.java](it/hurts/octostudios/octolib/module/config/annotation/registration/AnnotationConfigFactory.java) |
| `ConfigNameGetter` | interface | — | — | 0 | 9 | [ConfigNameGetter.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ConfigNameGetter.java) |
| `ObjectConfig` | @interface | — | Retention、Target | 0 | 23 | [ObjectConfig.java](it/hurts/octostudios/octolib/module/config/annotation/registration/ObjectConfig.java) |
| `ConstructorExt` | class | org.yaml.snakeyaml.constructor.Const | — | 16 | 305 | [ConstructorExt.java](it/hurts/octostudios/octolib/module/config/util/ConstructorExt.java) |
| `SolidConfigLoader` | class | IConfigFileLoader<T | — | 2 | 39 | [SolidConfigLoader.java](it/hurts/octostudios/octolib/module/config/loader/SolidConfigLoader.java) |
| `IConfigFileLoader` | interface | — | — | 0 | 13 | [IConfigFileLoader.java](it/hurts/octostudios/octolib/module/config/loader/IConfigFileLoader.java) |
| `TrailProvider` | class | RenderProvider<TrailProvider | — | 18 | 237 | [TrailProvider.java](it/hurts/octostudios/octolib/module/particle/trail/TrailProvider.java) |
| `TestScreen` | class | Screen | — | 6 | 99 | [TestScreen.java](it/hurts/octostudios/octolib/client/TestScreen.java) |
| `TestGearScreen` | class | Screen | — | 5 | 72 | [TestGearScreen.java](it/hurts/octostudios/octolib/client/screen/TestGearScreen.java) |
| `OctolibNetwork` | class | — | — | 3 | 35 | [OctolibNetwork.java](it/hurts/octostudios/octolib/module/network/OctolibNetwork.java) |
| `UnholyAbominationPacket` | class | Packet | — | 3 | 57 | [UnholyAbominationPacket.java](it/hurts/octostudios/octolib/module/config/network/UnholyAbominationPacket.java) |
| `RepresenterExt` | class | Representer | — | 11 | 304 | [RepresenterExt.java](it/hurts/octostudios/octolib/module/config/util/RepresenterExt.java) |
| `OctolibCommand` | class | — | — | 1 | 127 | [OctolibCommand.java](it/hurts/octostudios/octolib/module/command/OctolibCommand.java) |
| `OctoLibClient` | class | — | — | 3 | 25 | [OctoLibClient.java](it/hurts/octostudios/octolib/OctoLibClient.java) |
| `OctoLibNeoForgeClient` | class | — | — | 0 | 11 | [OctoLibNeoForgeClient.java](it/hurts/octostudios/octolib/neoforge/OctoLibNeoForgeClient.java) |
| `RenderUtils` | class | — | — | 7 | 119 | [RenderUtils.java](it/hurts/octostudios/octolib/util/RenderUtils.java) |
| `VectorUtils` | class | — | — | 7 | 76 | [VectorUtils.java](it/hurts/octostudios/octolib/util/VectorUtils.java) |
| `AnimationUtils` | class | — | — | 8 | 165 | [AnimationUtils.java](it/hurts/octostudios/octolib/util/AnimationUtils.java) |
| `OctoColor` | class | — | — | 14 | 121 | [OctoColor.java](it/hurts/octostudios/octolib/util/OctoColor.java) |
| `TestGear` | class | AbstractWidget | — | 11 | 94 | [TestGear.java](it/hurts/octostudios/octolib/client/screen/widget/TestGear.java) |
| `Transform` | class | — | — | 14 | 87 | [Transform.java](it/hurts/octostudios/octolib/client/particle/Transform.java) |
| `ExtendedUIParticle` | class | UIParticle | — | 10 | 72 | [ExtendedUIParticle.java](it/hurts/octostudios/octolib/client/particle/ExtendedUIParticle.java) |
| `PropertyTweener` | class | Tweener | — | 12 | 181 | [PropertyTweener.java](it/hurts/octostudios/octolib/client/animation/PropertyTweener.java) |
| `CfgTag` | class | — | — | 12 | 99 | [CfgTag.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/CfgTag.java) |
| `ArrayEntry` | class | ConfigEntry | — | 10 | 106 | [ArrayEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ArrayEntry.java) |
| `GenericPropertyExt` | class | GenericProperty | — | 11 | 67 | [GenericPropertyExt.java](it/hurts/octostudios/octolib/module/config/util/properties/GenericPropertyExt.java) |
| `DefaultTrailBuffer` | class | TrailBuffer | — | 11 | 93 | [DefaultTrailBuffer.java](it/hurts/octostudios/octolib/module/particle/trail/DefaultTrailBuffer.java) |
| `EntityTrailProvider` | class | TrailProvider | — | 4 | 33 | [EntityTrailProvider.java](it/hurts/octostudios/octolib/module/particle/trail/EntityTrailProvider.java) |
| `TesselatorUtils` | class | — | — | 2 | 98 | [TesselatorUtils.java](it/hurts/octostudios/octolib/util/TesselatorUtils.java) |
| `ColorUtils` | class | — | — | 3 | 45 | [ColorUtils.java](it/hurts/octostudios/octolib/util/ColorUtils.java) |
| `TestWidget` | class | AbstractWidget | — | 5 | 107 | [TestWidget.java](it/hurts/octostudios/octolib/client/TestWidget.java) |
| `TestPin` | class | AbstractWidget | — | 6 | 66 | [TestPin.java](it/hurts/octostudios/octolib/client/screen/widget/TestPin.java) |
| `ShakeData` | class | — | — | 2 | 52 | [ShakeData.java](it/hurts/octostudios/octolib/client/shake/ShakeData.java) |
| `Tweener` | class | — | — | 7 | 35 | [Tweener.java](it/hurts/octostudios/octolib/client/animation/Tweener.java) |
| `MethodTweener` | class | Tweener | — | 5 | 85 | [MethodTweener.java](it/hurts/octostudios/octolib/client/animation/MethodTweener.java) |
| `TweenSystem` | class | — | — | 7 | 84 | [TweenSystem.java](it/hurts/octostudios/octolib/client/animation/TweenSystem.java) |
| `SchemeInjector` | class | EntryInjector<ConfigEntry> | — | 7 | 136 | [SchemeInjector.java](it/hurts/octostudios/octolib/module/config/util/SchemeInjector.java) |
| `PropertyUtilsExt` | class | PropertyUtils | — | 2 | 160 | [PropertyUtilsExt.java](it/hurts/octostudios/octolib/module/config/util/PropertyUtilsExt.java) |
| `MethodPropertyExt` | class | GenericPropertyExt | — | 7 | 95 | [MethodPropertyExt.java](it/hurts/octostudios/octolib/module/config/util/properties/MethodPropertyExt.java) |
| `TestArrowTrail` | class | EntityTrailProvider | — | 5 | 35 | [TestArrowTrail.java](it/hurts/octostudios/octolib/module/particle/trail/TestArrowTrail.java) |
| `OctoLib` | class | — | — | 3 | 29 | [OctoLib.java](it/hurts/octostudios/octolib/OctoLib.java) |
| `CommonCode` | class | — | — | 1 | 9 | [CommonCode.java](it/hurts/octostudios/octolib/util/CommonCode.java) |
| `HasRenderMatrix` | interface | — | — | 0 | 10 | [HasRenderMatrix.java](it/hurts/octostudios/octolib/client/screen/widget/HasRenderMatrix.java) |
| `Child` | interface | — | — | 0 | 58 | [Child.java](it/hurts/octostudios/octolib/client/screen/widget/Child.java) |
| `Shakeable` | interface | — | — | 0 | 10 | [Shakeable.java](it/hurts/octostudios/octolib/client/shake/Shakeable.java) |
| `ShakeSystem` | class | — | — | 4 | 37 | [ShakeSystem.java](it/hurts/octostudios/octolib/client/shake/ShakeSystem.java) |
| `GalacticUIParticle` | class | ExtendedUIParticle | — | 0 | 33 | [GalacticUIParticle.java](it/hurts/octostudios/octolib/client/particle/GalacticUIParticle.java) |
| `ParticleSystem` | class | — | — | 3 | 32 | [ParticleSystem.java](it/hurts/octostudios/octolib/client/particle/ParticleSystem.java) |
| `RunnableTweener` | class | Tweener | — | 2 | 31 | [RunnableTweener.java](it/hurts/octostudios/octolib/client/animation/RunnableTweener.java) |
| `IntervalTweener` | class | Tweener | — | 1 | 24 | [IntervalTweener.java](it/hurts/octostudios/octolib/client/animation/IntervalTweener.java) |
| `EaseType` | enum | — | — | 1 | 21 | [EaseType.java](it/hurts/octostudios/octolib/client/animation/easing/EaseType.java) |
| `Interpolator` | interface | — | — | 0 | 7 | [Interpolator.java](it/hurts/octostudios/octolib/client/animation/easing/Interpolator.java) |
| `TransitionType` | enum | — | — | 3 | 62 | [TransitionType.java](it/hurts/octostudios/octolib/client/animation/easing/TransitionType.java) |
| `ObjectEntry` | class | ConfigEntry | — | 2 | 25 | [ObjectEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ObjectEntry.java) |
| `NullEntry` | class | ScalarEntry | — | 0 | 10 | [NullEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/NullEntry.java) |
| `EntryId` | enum | — | — | 1 | 22 | [EntryId.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/EntryId.java) |
| `ListEntry` | class | ConfigEntry | — | 1 | 13 | [ListEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/ListEntry.java) |
| `EnumEntry` | class | ScalarEntry | — | 1 | 24 | [EnumEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/EnumEntry.java) |
| `DeconstructedObjectEntry` | class | CompoundEntry | — | 0 | 15 | [DeconstructedObjectEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/DeconstructedObjectEntry.java) |
| `DeconstructedEnumEntry` | class | ScalarEntry | — | 0 | 10 | [DeconstructedEnumEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/DeconstructedEnumEntry.java) |
| `BinaryEntry` | class | NumberEntry | — | 0 | 10 | [BinaryEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/BinaryEntry.java) |
| `StringEntry` | class | ScalarEntry | — | 0 | 10 | [StringEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/StringEntry.java) |
| `DoubleEntry` | class | NumberEntry | — | 0 | 10 | [DoubleEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/DoubleEntry.java) |
| `BoolEntry` | class | ScalarEntry | — | 0 | 10 | [BoolEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/BoolEntry.java) |
| `IntEntry` | class | NumberEntry | — | 0 | 10 | [IntEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/IntEntry.java) |
| `NumberEntry` | class | ScalarEntry | — | 0 | 10 | [NumberEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/NumberEntry.java) |
| `ScalarEntry` | class | ConfigEntry | — | 1 | 20 | [ScalarEntry.java](it/hurts/octostudios/octolib/module/config/cfgbuilder/scalar/ScalarEntry.java) |
| `Prop` | @interface | — | Retention、Target | 0 | 17 | [Prop.java](it/hurts/octostudios/octolib/module/config/annotation/Prop.java) |
| `ParameterizedProp` | @interface | — | Retention、Target | 0 | 13 | [ParameterizedProp.java](it/hurts/octostudios/octolib/module/config/annotation/ParameterizedProp.java) |
| `TypeProp` | @interface | — | Retention、Target | 0 | 20 | [TypeProp.java](it/hurts/octostudios/octolib/module/config/annotation/TypeProp.java) |
| `TypePropInherited` | @interface | — | Inherited、Retention、Target | 0 | 22 | [TypePropInherited.java](it/hurts/octostudios/octolib/module/config/annotation/TypePropInherited.java) |
| `CfgConstructor` | @interface | — | Retention、Target | 0 | 13 | [CfgConstructor.java](it/hurts/octostudios/octolib/module/config/annotation/CfgConstructor.java) |
| `IgnoreProp` | @interface | — | Retention、Target | 0 | 12 | [IgnoreProp.java](it/hurts/octostudios/octolib/module/config/annotation/IgnoreProp.java) |
| `EntryInjector` | interface | — | — | 0 | 8 | [EntryInjector.java](it/hurts/octostudios/octolib/module/config/util/EntryInjector.java) |
| `FieldPropertyExt` | class | GenericPropertyExt | — | 4 | 49 | [FieldPropertyExt.java](it/hurts/octostudios/octolib/module/config/util/properties/FieldPropertyExt.java) |
| `FileSpreadLoader` | class | IConfigFileLoader<Iterable<T> | — | 2 | 38 | [FileSpreadLoader.java](it/hurts/octostudios/octolib/module/config/loader/FileSpreadLoader.java) |
| `RenderProvider` | interface | — | — | 0 | 22 | [RenderProvider.java](it/hurts/octostudios/octolib/module/particle/RenderProvider.java) |
| `RenderBuffer` | interface | — | — | 0 | 6 | [RenderBuffer.java](it/hurts/octostudios/octolib/module/particle/RenderBuffer.java) |
| `TrailBuffer` | interface | — | — | 0 | 23 | [TrailBuffer.java](it/hurts/octostudios/octolib/module/particle/trail/TrailBuffer.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_octolib_common_02d17019834d42558d69f7778146eb07_2b9ffff7a9504b23694122d199f460091384ced36c30371c66c10d3da02a36eaOctoLibCOMMON0604121devjar/PlatformMethods.java) |
