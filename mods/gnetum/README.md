# Gnetum（`gnetum`）

> jar：`[优化]gnetum.jar` ｜ 版本：`3.3.6` ｜ 作者：decce
> 规模：**55 个类 / 17 个包 / 3318 行** ｜ 事件处理类 1 ｜ Mixin 15 ｜ 注册/管理类 5

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `me.decce.gnetum` | 18 | `Gnetum`、`EventListenerHelper`、`FramebufferManager` |
| `me.decce.gnetum.mixins` | 9 | `GlStateManagerMixin`、`GuiLayerManagerAccessor`、`GameRendererMixin` |
| `me.decce.gnetum.gui` | 6 | `ConfigScreen`、`ElementsScreen`、`BaseScreen` |
| `me.decce.gnetum.util` | 4 | `AnyBoolean`、`TriStateBoolean`、`AnyBooleanValue` |
| `me.decce.gnetum.gui.widgets` | 3 | `ToggleButton`、`IconButton`、`IntSlider` |
| `me.decce.gnetum.mixins.compat.jade` | 2 | `ClientProxyMixin`、`BossHealthOverlayMixin` |
| `me.decce.gnetum.compat.embeddium` | 2 | `GnetumEmbeddiumCompat`、`GnetumEmbeddiumPage` |
| `me.decce.gnetum.compat.sodium` | 2 | `GnetumSodiumPage`、`SodiumEntrypoint` |
| `me.decce.gnetum.mixins.compat.journeymap` | 1 | `HudOverlayHandlerMixin` |
| `me.decce.gnetum.mixins.compat` | 1 | `ItemRendererMixin` |
| `me.decce.gnetum.mixins.compat.pingwheel` | 1 | `CommonClientMixin` |
| `me.decce.gnetum.mixins.compat.sodium` | 1 | `SodiumOptionsGUIMixin` |
| `me.decce.gnetum.compat.journeymap` | 1 | `JourneyMapCompat` |
| `me.decce.gnetum.compat.pingwheel` | 1 | `PingWheelCompat` |
| `me.decce.gnetum.compat.immediatelyfast` | 1 | `ImmediatelyFastCompat` |
| `me.decce.gnetum.compat.jade` | 1 | `JadeCompat` |
| `me.decce.gnetum.gl` | 1 | `FramebufferTracker` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 55）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Gnetum` | class | — | Mod | 9 | 146 | [Gnetum.java](me/decce/gnetum/Gnetum.java) |
| `GlStateManagerMixin` | class | — | Mixin | 0 | 61 | [GlStateManagerMixin.java](me/decce/gnetum/mixins/GlStateManagerMixin.java) |
| `GuiLayerManagerAccessor` | interface | — | Mixin | 0 | 14 | [GuiLayerManagerAccessor.java](me/decce/gnetum/mixins/GuiLayerManagerAccessor.java) |
| `HudOverlayHandlerMixin` | class | — | Mixin、Pseudo | 0 | 22 | [HudOverlayHandlerMixin.java](me/decce/gnetum/mixins/compat/journeymap/HudOverlayHandlerMixin.java) |
| `ItemRendererMixin` | class | — | Mixin | 0 | 29 | [ItemRendererMixin.java](me/decce/gnetum/mixins/compat/ItemRendererMixin.java) |
| `EventListenerHelper` | class | — | — | 3 | 79 | [EventListenerHelper.java](me/decce/gnetum/EventListenerHelper.java) |
| `GameRendererMixin` | class | — | Mixin | 0 | 156 | [GameRendererMixin.java](me/decce/gnetum/mixins/GameRendererMixin.java) |
| `CommonClientMixin` | class | — | Mixin、Pseudo | 0 | 21 | [CommonClientMixin.java](me/decce/gnetum/mixins/compat/pingwheel/CommonClientMixin.java) |
| `ClientProxyMixin` | class | — | Mixin、Pseudo | 1 | 18 | [ClientProxyMixin.java](me/decce/gnetum/mixins/compat/jade/ClientProxyMixin.java) |
| `FramebufferManager` | class | — | — | 14 | 146 | [FramebufferManager.java](me/decce/gnetum/FramebufferManager.java) |
| `GnetumConfig` | class | — | — | 13 | 158 | [GnetumConfig.java](me/decce/gnetum/GnetumConfig.java) |
| `GnetumConfigScreenFactory` | class | IConfigScreenFactory | — | 1 | 14 | [GnetumConfigScreenFactory.java](me/decce/gnetum/GnetumConfigScreenFactory.java) |
| `PassManager` | class | — | — | 9 | 138 | [PassManager.java](me/decce/gnetum/PassManager.java) |
| `ConfigScreen` | class | BaseScreen | — | 4 | 135 | [ConfigScreen.java](me/decce/gnetum/gui/ConfigScreen.java) |
| `GuiMixin` | class | — | Mixin | 0 | 95 | [GuiMixin.java](me/decce/gnetum/mixins/GuiMixin.java) |
| `RenderTargetMixin` | class | — | Mixin | 0 | 22 | [RenderTargetMixin.java](me/decce/gnetum/mixins/RenderTargetMixin.java) |
| `PoseStackAccessor` | interface | — | Mixin | 0 | 14 | [PoseStackAccessor.java](me/decce/gnetum/mixins/PoseStackAccessor.java) |
| `DeltaTrackerMixin` | class | — | Mixin | 0 | 17 | [DeltaTrackerMixin.java](me/decce/gnetum/mixins/DeltaTrackerMixin.java) |
| `MinecraftAccessor` | interface | — | Mixin | 0 | 12 | [MinecraftAccessor.java](me/decce/gnetum/mixins/MinecraftAccessor.java) |
| `GuiAccessor` | interface | — | Mixin | 0 | 20 | [GuiAccessor.java](me/decce/gnetum/mixins/GuiAccessor.java) |
| `SodiumOptionsGUIMixin` | class | — | Mixin、Pseudo | 0 | 30 | [SodiumOptionsGUIMixin.java](me/decce/gnetum/mixins/compat/sodium/SodiumOptionsGUIMixin.java) |
| `BossHealthOverlayMixin` | class | — | Mixin | 0 | 23 | [BossHealthOverlayMixin.java](me/decce/gnetum/mixins/compat/jade/BossHealthOverlayMixin.java) |
| `EventBusHelper` | class | — | — | 4 | 62 | [EventBusHelper.java](me/decce/gnetum/EventBusHelper.java) |
| `ElementsScreen` | class | BaseScreen | — | 8 | 203 | [ElementsScreen.java](me/decce/gnetum/gui/ElementsScreen.java) |
| `GuiHelper` | class | — | — | 10 | 150 | [GuiHelper.java](me/decce/gnetum/GuiHelper.java) |
| `BaseScreen` | class | Screen | — | 7 | 63 | [BaseScreen.java](me/decce/gnetum/gui/BaseScreen.java) |
| `PoseStackHelper` | class | — | — | 5 | 71 | [PoseStackHelper.java](me/decce/gnetum/PoseStackHelper.java) |
| `ConfirmationScreen` | class | BaseScreen | — | 3 | 47 | [ConfirmationScreen.java](me/decce/gnetum/gui/ConfirmationScreen.java) |
| `MoreOptionsScreen` | class | BaseScreen | — | 2 | 110 | [MoreOptionsScreen.java](me/decce/gnetum/gui/MoreOptionsScreen.java) |
| `AnalysisScreen` | class | BaseScreen | — | 3 | 70 | [AnalysisScreen.java](me/decce/gnetum/gui/AnalysisScreen.java) |
| `JourneyMapCompat` | class | — | — | 1 | 16 | [JourneyMapCompat.java](me/decce/gnetum/compat/journeymap/JourneyMapCompat.java) |
| `PingWheelCompat` | class | — | — | 1 | 25 | [PingWheelCompat.java](me/decce/gnetum/compat/pingwheel/PingWheelCompat.java) |
| `ImmediatelyFastCompat` | class | — | — | 2 | 40 | [ImmediatelyFastCompat.java](me/decce/gnetum/compat/immediatelyfast/ImmediatelyFastCompat.java) |
| `GnetumEmbeddiumCompat` | class | — | — | 1 | 15 | [GnetumEmbeddiumCompat.java](me/decce/gnetum/compat/embeddium/GnetumEmbeddiumCompat.java) |
| `JadeCompat` | class | — | — | 0 | 6 | [JadeCompat.java](me/decce/gnetum/compat/jade/JadeCompat.java) |
| `HudDeltaTracker` | class | — | — | 6 | 60 | [HudDeltaTracker.java](me/decce/gnetum/HudDeltaTracker.java) |
| `PerformanceAnalyzer` | class | — | — | 9 | 135 | [PerformanceAnalyzer.java](me/decce/gnetum/PerformanceAnalyzer.java) |
| `ToggleButton` | class | Button | — | 5 | 56 | [ToggleButton.java](me/decce/gnetum/gui/widgets/ToggleButton.java) |
| `Icons` | class | — | — | 0 | 18 | [Icons.java](me/decce/gnetum/Icons.java) |
| `UncachedElements` | class | — | — | 2 | 31 | [UncachedElements.java](me/decce/gnetum/UncachedElements.java) |
| `ElementType` | enum | — | — | 0 | 8 | [ElementType.java](me/decce/gnetum/ElementType.java) |
| `PackedVanillaElements` | class | — | — | 3 | 49 | [PackedVanillaElements.java](me/decce/gnetum/PackedVanillaElements.java) |
| `FpsCounter` | class | — | — | 3 | 32 | [FpsCounter.java](me/decce/gnetum/FpsCounter.java) |
| `CacheSetting` | class | — | — | 0 | 20 | [CacheSetting.java](me/decce/gnetum/CacheSetting.java) |
| `SuggestedPass` | class | — | — | 1 | 38 | [SuggestedPass.java](me/decce/gnetum/SuggestedPass.java) |
| `IconButton` | class | Button | — | 2 | 45 | [IconButton.java](me/decce/gnetum/gui/widgets/IconButton.java) |
| `IntSlider` | class | ExtendedSlider | — | 2 | 73 | [IntSlider.java](me/decce/gnetum/gui/widgets/IntSlider.java) |
| `FramebufferTracker` | class | — | — | 2 | 14 | [FramebufferTracker.java](me/decce/gnetum/gl/FramebufferTracker.java) |
| `GnetumSodiumPage` | class | OptionPage | — | 3 | 124 | [GnetumSodiumPage.java](me/decce/gnetum/compat/sodium/GnetumSodiumPage.java) |
| `SodiumEntrypoint` | class | ConfigEntryPoint | — | 1 | 119 | [SodiumEntrypoint.java](me/decce/gnetum/compat/sodium/SodiumEntrypoint.java) |
| `GnetumEmbeddiumPage` | class | OptionPage | — | 3 | 133 | [GnetumEmbeddiumPage.java](me/decce/gnetum/compat/embeddium/GnetumEmbeddiumPage.java) |
| `AnyBoolean` | interface | — | — | 0 | 10 | [AnyBoolean.java](me/decce/gnetum/util/AnyBoolean.java) |
| `TriStateBoolean` | class | AnyBoolean | — | 4 | 49 | [TriStateBoolean.java](me/decce/gnetum/util/TriStateBoolean.java) |
| `AnyBooleanValue` | enum | — | — | 0 | 13 | [AnyBooleanValue.java](me/decce/gnetum/util/AnyBooleanValue.java) |
| `TwoStateBoolean` | class | AnyBoolean | — | 3 | 43 | [TwoStateBoolean.java](me/decce/gnetum/util/TwoStateBoolean.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EventListenerHelper` | 1 | `me.decce.gnetum` | [EventListenerHelper.java](me/decce/gnetum/EventListenerHelper.java) |

## Mixin（15 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `GlStateManagerMixin` | `GlStateManager` | [GlStateManagerMixin.java](me/decce/gnetum/mixins/GlStateManagerMixin.java) |
| `GuiLayerManagerAccessor` | `GuiLayerManagerAccessor` | [GuiLayerManagerAccessor.java](me/decce/gnetum/mixins/GuiLayerManagerAccessor.java) |
| `HudOverlayHandlerMixin` | `HudOverlayHandler` | [HudOverlayHandlerMixin.java](me/decce/gnetum/mixins/compat/journeymap/HudOverlayHandlerMixin.java) |
| `ItemRendererMixin` | `ItemRenderer` | [ItemRendererMixin.java](me/decce/gnetum/mixins/compat/ItemRendererMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](me/decce/gnetum/mixins/GameRendererMixin.java) |
| `CommonClientMixin` | `CommonClient` | [CommonClientMixin.java](me/decce/gnetum/mixins/compat/pingwheel/CommonClientMixin.java) |
| `ClientProxyMixin` | `ClientProxy` | [ClientProxyMixin.java](me/decce/gnetum/mixins/compat/jade/ClientProxyMixin.java) |
| `GuiMixin` | `Gui` | [GuiMixin.java](me/decce/gnetum/mixins/GuiMixin.java) |
| `RenderTargetMixin` | `RenderTarget` | [RenderTargetMixin.java](me/decce/gnetum/mixins/RenderTargetMixin.java) |
| `PoseStackAccessor` | `PoseStackAccessor` | [PoseStackAccessor.java](me/decce/gnetum/mixins/PoseStackAccessor.java) |
| `DeltaTrackerMixin` | `DeltaTracker` | [DeltaTrackerMixin.java](me/decce/gnetum/mixins/DeltaTrackerMixin.java) |
| `MinecraftAccessor` | `MinecraftAccessor` | [MinecraftAccessor.java](me/decce/gnetum/mixins/MinecraftAccessor.java) |
| `GuiAccessor` | `GuiAccessor` | [GuiAccessor.java](me/decce/gnetum/mixins/GuiAccessor.java) |
| `SodiumOptionsGUIMixin` | `SodiumOptionsGUI` | [SodiumOptionsGUIMixin.java](me/decce/gnetum/mixins/compat/sodium/SodiumOptionsGUIMixin.java) |
| `BossHealthOverlayMixin` | `BossHealthOverlay` | [BossHealthOverlayMixin.java](me/decce/gnetum/mixins/compat/jade/BossHealthOverlayMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `GnetumConfig` | — | [GnetumConfig.java](me/decce/gnetum/GnetumConfig.java) |
| `GnetumConfigScreenFactory` | — | [GnetumConfigScreenFactory.java](me/decce/gnetum/GnetumConfigScreenFactory.java) |
| `ConfigScreen` | — | [ConfigScreen.java](me/decce/gnetum/gui/ConfigScreen.java) |

## 全部类（55）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Gnetum` | class | — | Mod | 9 | 146 | [Gnetum.java](me/decce/gnetum/Gnetum.java) |
| `GlStateManagerMixin` | class | — | Mixin | 0 | 61 | [GlStateManagerMixin.java](me/decce/gnetum/mixins/GlStateManagerMixin.java) |
| `GuiLayerManagerAccessor` | interface | — | Mixin | 0 | 14 | [GuiLayerManagerAccessor.java](me/decce/gnetum/mixins/GuiLayerManagerAccessor.java) |
| `HudOverlayHandlerMixin` | class | — | Mixin、Pseudo | 0 | 22 | [HudOverlayHandlerMixin.java](me/decce/gnetum/mixins/compat/journeymap/HudOverlayHandlerMixin.java) |
| `ItemRendererMixin` | class | — | Mixin | 0 | 29 | [ItemRendererMixin.java](me/decce/gnetum/mixins/compat/ItemRendererMixin.java) |
| `EventListenerHelper` | class | — | — | 3 | 79 | [EventListenerHelper.java](me/decce/gnetum/EventListenerHelper.java) |
| `GameRendererMixin` | class | — | Mixin | 0 | 156 | [GameRendererMixin.java](me/decce/gnetum/mixins/GameRendererMixin.java) |
| `CommonClientMixin` | class | — | Mixin、Pseudo | 0 | 21 | [CommonClientMixin.java](me/decce/gnetum/mixins/compat/pingwheel/CommonClientMixin.java) |
| `ClientProxyMixin` | class | — | Mixin、Pseudo | 1 | 18 | [ClientProxyMixin.java](me/decce/gnetum/mixins/compat/jade/ClientProxyMixin.java) |
| `FramebufferManager` | class | — | — | 14 | 146 | [FramebufferManager.java](me/decce/gnetum/FramebufferManager.java) |
| `GnetumConfig` | class | — | — | 13 | 158 | [GnetumConfig.java](me/decce/gnetum/GnetumConfig.java) |
| `GnetumConfigScreenFactory` | class | IConfigScreenFactory | — | 1 | 14 | [GnetumConfigScreenFactory.java](me/decce/gnetum/GnetumConfigScreenFactory.java) |
| `PassManager` | class | — | — | 9 | 138 | [PassManager.java](me/decce/gnetum/PassManager.java) |
| `ConfigScreen` | class | BaseScreen | — | 4 | 135 | [ConfigScreen.java](me/decce/gnetum/gui/ConfigScreen.java) |
| `GuiMixin` | class | — | Mixin | 0 | 95 | [GuiMixin.java](me/decce/gnetum/mixins/GuiMixin.java) |
| `RenderTargetMixin` | class | — | Mixin | 0 | 22 | [RenderTargetMixin.java](me/decce/gnetum/mixins/RenderTargetMixin.java) |
| `PoseStackAccessor` | interface | — | Mixin | 0 | 14 | [PoseStackAccessor.java](me/decce/gnetum/mixins/PoseStackAccessor.java) |
| `DeltaTrackerMixin` | class | — | Mixin | 0 | 17 | [DeltaTrackerMixin.java](me/decce/gnetum/mixins/DeltaTrackerMixin.java) |
| `MinecraftAccessor` | interface | — | Mixin | 0 | 12 | [MinecraftAccessor.java](me/decce/gnetum/mixins/MinecraftAccessor.java) |
| `GuiAccessor` | interface | — | Mixin | 0 | 20 | [GuiAccessor.java](me/decce/gnetum/mixins/GuiAccessor.java) |
| `SodiumOptionsGUIMixin` | class | — | Mixin、Pseudo | 0 | 30 | [SodiumOptionsGUIMixin.java](me/decce/gnetum/mixins/compat/sodium/SodiumOptionsGUIMixin.java) |
| `BossHealthOverlayMixin` | class | — | Mixin | 0 | 23 | [BossHealthOverlayMixin.java](me/decce/gnetum/mixins/compat/jade/BossHealthOverlayMixin.java) |
| `EventBusHelper` | class | — | — | 4 | 62 | [EventBusHelper.java](me/decce/gnetum/EventBusHelper.java) |
| `ElementsScreen` | class | BaseScreen | — | 8 | 203 | [ElementsScreen.java](me/decce/gnetum/gui/ElementsScreen.java) |
| `GuiHelper` | class | — | — | 10 | 150 | [GuiHelper.java](me/decce/gnetum/GuiHelper.java) |
| `BaseScreen` | class | Screen | — | 7 | 63 | [BaseScreen.java](me/decce/gnetum/gui/BaseScreen.java) |
| `PoseStackHelper` | class | — | — | 5 | 71 | [PoseStackHelper.java](me/decce/gnetum/PoseStackHelper.java) |
| `ConfirmationScreen` | class | BaseScreen | — | 3 | 47 | [ConfirmationScreen.java](me/decce/gnetum/gui/ConfirmationScreen.java) |
| `MoreOptionsScreen` | class | BaseScreen | — | 2 | 110 | [MoreOptionsScreen.java](me/decce/gnetum/gui/MoreOptionsScreen.java) |
| `AnalysisScreen` | class | BaseScreen | — | 3 | 70 | [AnalysisScreen.java](me/decce/gnetum/gui/AnalysisScreen.java) |
| `JourneyMapCompat` | class | — | — | 1 | 16 | [JourneyMapCompat.java](me/decce/gnetum/compat/journeymap/JourneyMapCompat.java) |
| `PingWheelCompat` | class | — | — | 1 | 25 | [PingWheelCompat.java](me/decce/gnetum/compat/pingwheel/PingWheelCompat.java) |
| `ImmediatelyFastCompat` | class | — | — | 2 | 40 | [ImmediatelyFastCompat.java](me/decce/gnetum/compat/immediatelyfast/ImmediatelyFastCompat.java) |
| `GnetumEmbeddiumCompat` | class | — | — | 1 | 15 | [GnetumEmbeddiumCompat.java](me/decce/gnetum/compat/embeddium/GnetumEmbeddiumCompat.java) |
| `JadeCompat` | class | — | — | 0 | 6 | [JadeCompat.java](me/decce/gnetum/compat/jade/JadeCompat.java) |
| `HudDeltaTracker` | class | — | — | 6 | 60 | [HudDeltaTracker.java](me/decce/gnetum/HudDeltaTracker.java) |
| `PerformanceAnalyzer` | class | — | — | 9 | 135 | [PerformanceAnalyzer.java](me/decce/gnetum/PerformanceAnalyzer.java) |
| `ToggleButton` | class | Button | — | 5 | 56 | [ToggleButton.java](me/decce/gnetum/gui/widgets/ToggleButton.java) |
| `Icons` | class | — | — | 0 | 18 | [Icons.java](me/decce/gnetum/Icons.java) |
| `UncachedElements` | class | — | — | 2 | 31 | [UncachedElements.java](me/decce/gnetum/UncachedElements.java) |
| `ElementType` | enum | — | — | 0 | 8 | [ElementType.java](me/decce/gnetum/ElementType.java) |
| `PackedVanillaElements` | class | — | — | 3 | 49 | [PackedVanillaElements.java](me/decce/gnetum/PackedVanillaElements.java) |
| `FpsCounter` | class | — | — | 3 | 32 | [FpsCounter.java](me/decce/gnetum/FpsCounter.java) |
| `CacheSetting` | class | — | — | 0 | 20 | [CacheSetting.java](me/decce/gnetum/CacheSetting.java) |
| `SuggestedPass` | class | — | — | 1 | 38 | [SuggestedPass.java](me/decce/gnetum/SuggestedPass.java) |
| `IconButton` | class | Button | — | 2 | 45 | [IconButton.java](me/decce/gnetum/gui/widgets/IconButton.java) |
| `IntSlider` | class | ExtendedSlider | — | 2 | 73 | [IntSlider.java](me/decce/gnetum/gui/widgets/IntSlider.java) |
| `FramebufferTracker` | class | — | — | 2 | 14 | [FramebufferTracker.java](me/decce/gnetum/gl/FramebufferTracker.java) |
| `GnetumSodiumPage` | class | OptionPage | — | 3 | 124 | [GnetumSodiumPage.java](me/decce/gnetum/compat/sodium/GnetumSodiumPage.java) |
| `SodiumEntrypoint` | class | ConfigEntryPoint | — | 1 | 119 | [SodiumEntrypoint.java](me/decce/gnetum/compat/sodium/SodiumEntrypoint.java) |
| `GnetumEmbeddiumPage` | class | OptionPage | — | 3 | 133 | [GnetumEmbeddiumPage.java](me/decce/gnetum/compat/embeddium/GnetumEmbeddiumPage.java) |
| `AnyBoolean` | interface | — | — | 0 | 10 | [AnyBoolean.java](me/decce/gnetum/util/AnyBoolean.java) |
| `TriStateBoolean` | class | AnyBoolean | — | 4 | 49 | [TriStateBoolean.java](me/decce/gnetum/util/TriStateBoolean.java) |
| `AnyBooleanValue` | enum | — | — | 0 | 13 | [AnyBooleanValue.java](me/decce/gnetum/util/AnyBooleanValue.java) |
| `TwoStateBoolean` | class | AnyBoolean | — | 3 | 43 | [TwoStateBoolean.java](me/decce/gnetum/util/TwoStateBoolean.java) |
