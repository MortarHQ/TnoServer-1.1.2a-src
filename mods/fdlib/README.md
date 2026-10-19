# FDLib（`fdlib`）

> jar：`[前置]fdlib.jar` ｜ 版本：`1.0.8` ｜ 作者：FINDERFEED
> 规模：**442 个类 / 100 个包 / 26066 行** ｜ 事件处理类 22 ｜ Mixin 13 ｜ 注册/管理类 16

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.finderfeed.fdlib.shunting_yard.functions` | 25 | `ASinFunction`、`MinFunction`、`SubtractionFunction` |
| `com.finderfeed.fdlib.init` | 19 | `FDClientModEvents`、`FDModEvents`、`FDGameEvents` |
| `com.finderfeed.fdlib.mixin` | 13 | `ItemEntityRendererMixin`、`MouseHandlerMixin`、`ItemInHandRendererMixin` |
| `com.finderfeed.fdlib.systems.cutscenes` | 9 | `CutsceneCameraHandler`、`ClientCameraEntity`、`CutsceneData` |
| `com.finderfeed.fdlib.systems.particle.particle_emitter` | 9 | `ParticleEmitterHandler`、`ParticleEmitterPacket`、`ParticleEmitterData` |
| `com.finderfeed.fdlib` | 8 | `ClientMixinHandler`、`FDLib`、`FDClientPacketExecutables` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item` | 8 | `FDItemAnimationHandler`、`FDModelItemRendererOptions`、`FDFreeItemRenderer` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.renderer` | 8 | `FDBlockEntityRenderer`、`FDBlockEntityRendererBuilder`、`FDBlockEntityRenderLayer` |
| `com.finderfeed.fdlib.util` | 8 | `FDByteBufCodecs`、`ProjectileMovementPath`、`InterpolatedValue` |
| `com.finderfeed.fdlib.systems.shake` | 8 | `PositionedScreenShakePacket`、`DefaultShakePacket`、`FDShakeData` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer` | 8 | `FDEntityRendererBuilder`、`FDEntityRenderer`、`FDFreeEntityRenderer` |
| `com.finderfeed.fdlib.systems.entity.action_chain` | 8 | `AttackOptions`、`AttackChain`、`Attack` |
| `com.finderfeed.fdlib.systems.bedrock.models` | 8 | `FDModelPart`、`FDCube`、`JsonHelper` |
| `com.finderfeed.fdlib.systems.particle` | 8 | `CompositeParticleProcessor`、`CircleParticleProcessor`、`SetParticleSpeedProcessor` |
| `com.finderfeed.fdlib.systems.screen.screen_effect` | 7 | `ScreenEffectOverlay`、`SendScreenEffectPacket`、`ScreenEffect` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system` | 7 | `AnimationTicker`、`AnimationSystem`、`AnimationEvent` |
| `com.finderfeed.fdlib.systems.bedrock.animations` | 7 | `Animation`、`BoneAnimationData`、`AnimationUtil` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head` | 7 | `LookStraightPacket`、`WantedLookCoordinatesPacket`、`ChangeHeadControllerModePacket` |
| `com.finderfeed.fdlib.systems.render_types.instances` | 7 | `FDRTEntityTranslucentCull`、`FDRTEntityTranslucent`、`FDRTEntityCutout` |
| `com.finderfeed.fdlib.systems.screen.annotations` | 7 | `FDScale`、`VComponent`、`OnlyPositive` |
| `com.finderfeed.fdlib.systems.hud.bossbars` | 6 | `FDBossbars`、`FDServerBossBar`、`FDBossBar` |
| `com.finderfeed.fdlib.systems.screen` | 6 | `FDScreenComponent`、`FDScreen`、`FDEditorComponent` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity` | 6 | `ClientsideEntityAnimationSystem`、`FDLivingEntity`、`ServersideEntityAnimationSystem` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments` | 6 | `BaseModelAttachmentData`、`ModelAttachmentRenderer`、`ModelAttachmentData` |
| `com.finderfeed.fdlib.nbt` | 6 | `TagSerializationHelper`、`FDTagHelper`、`TagDeserializer` |
| `com.finderfeed.fdlib.shunting_yard.sy_base` | 6 | `ExpressionContext`、`SYFunction`、`SYNode` |
| `com.finderfeed.fdlib.systems.post_shaders` | 5 | `FDPostShadersHandler`、`FDPostShaderInitializeEvent`、`FDRenderPostShaderEvent` |
| `com.finderfeed.fdlib.systems.screen.default_components.text` | 5 | `FDTextBox`、`FDCenteredTextComponent`、`FDColoredTextBox` |
| `com.finderfeed.fdlib.systems.config` | 5 | `ReflectiveJsonConfig`、`JsonConfig`、`ConfigValue` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile` | 5 | `FDEntityBlock`、`FDBlockEntity`、`ClientTileAnimationSystem` |
| `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.animated_item` | 5 | `ItemEntityStackContext`、`EmptyItemStackContext`、`ItemStackInSlotContext` |
| `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions` | 5 | `TextBlockEntryInteraction`、`TextBlockScroll`、`TextBlockHover` |
| `com.finderfeed.fdlib.systems.music.packets` | 5 | `FDMusicStartPacket`、`FDMusicFadeInPacket`、`FDMusicAreaEnterPacket` |
| `com.finderfeed.fdlib.systems.screen.default_components.buttons` | 5 | `BooleanVComponent`、`TexturedButtonComponent`、`OnFDButtonPress` |
| `com.finderfeed.fdlib.systems.cutscenes.camera_motion` | 5 | `CatmullRomCameraMotion`、`CameraMotion`、`CameraLookProcessor` |
| `com.finderfeed.fdlib.systems.screen.screen_particles` | 4 | `FDScreenParticleEngine`、`FDScreenParticle`、`ScreenParticlesRenderEvent` |
| `com.finderfeed.fdlib.systems.music` | 4 | `FDMusicSystem`、`FDMusic`、`FDMusicPart` |
| `com.finderfeed.fdlib.systems.simple_screen` | 4 | `FDWidget`、`SimpleFDScreen`、`FDScrollableWidget` |
| `com.finderfeed.fdlib.systems.hud.bossbars.packets` | 4 | `BossBarEventPacket`、`RemovePlayerFromBossBarPacket`、`AddPlayerToBossBarPacket` |
| `com.finderfeed.fdlib.shunting_yard` | 4 | `FunctionRegistry`、`ShuntingYard`、`RPNExpression` |
| … | | 其余 60 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FDClientModEvents` | class | — | EventBusSubscriber | 6 | 99 | [FDClientModEvents.java](com/finderfeed/fdlib/init/FDClientModEvents.java) |
| `FDCommandsRegistry` | class | — | EventBusSubscriber | 8 | 193 | [FDCommandsRegistry.java](com/finderfeed/fdlib/commands/FDCommandsRegistry.java) |
| `CutsceneCameraHandler` | class | — | EventBusSubscriber | 16 | 213 | [CutsceneCameraHandler.java](com/finderfeed/fdlib/systems/cutscenes/CutsceneCameraHandler.java) |
| `ClientMixinHandler` | class | — | EventBusSubscriber | 6 | 68 | [ClientMixinHandler.java](com/finderfeed/fdlib/ClientMixinHandler.java) |
| `FDItemAnimationHandler` | class | — | EventBusSubscriber | 7 | 84 | [FDItemAnimationHandler.java](com/finderfeed/fdlib/systems/bedrock/animations/animation_system/item/FDItemAnimationHandler.java) |
| `ShatteredScreenEffectHandler` | class | PostChain | EventBusSubscriber | 9 | 139 | [ShatteredScreenEffectHandler.java](com/finderfeed/fdlib/systems/broken_screen_effect/ShatteredScreenEffectHandler.java) |
| `PacketHandler` | class | — | EventBusSubscriber | 1 | 56 | [PacketHandler.java](com/finderfeed/fdlib/network/PacketHandler.java) |
| `FDModEvents` | class | — | EventBusSubscriber | 3 | 84 | [FDModEvents.java](com/finderfeed/fdlib/init/FDModEvents.java) |
| `FDGameEvents` | class | — | EventBusSubscriber | 2 | 38 | [FDGameEvents.java](com/finderfeed/fdlib/init/FDGameEvents.java) |
| `ImpactFramesHandler` | class | — | EventBusSubscriber | 8 | 126 | [ImpactFramesHandler.java](com/finderfeed/fdlib/systems/impact_frames/ImpactFramesHandler.java) |
| `FDMusicAreasHandler` | class | — | EventBusSubscriber | 6 | 66 | [FDMusicAreasHandler.java](com/finderfeed/fdlib/systems/music/music_areas/FDMusicAreasHandler.java) |
| `FDPostShadersHandler` | class | — | EventBusSubscriber | 2 | 39 | [FDPostShadersHandler.java](com/finderfeed/fdlib/systems/post_shaders/FDPostShadersHandler.java) |
| `ChromaticAbberationHandler` | class | — | EventBusSubscriber | 4 | 51 | [ChromaticAbberationHandler.java](com/finderfeed/fdlib/systems/chromatic_abberation_effect/ChromaticAbberationHandler.java) |
| `ParticleEmitterHandler` | class | — | EventBusSubscriber | 2 | 38 | [ParticleEmitterHandler.java](com/finderfeed/fdlib/systems/particle/particle_emitter/ParticleEmitterHandler.java) |
| `FDScreenParticleEngine` | class | — | EventBusSubscriber | 10 | 120 | [FDScreenParticleEngine.java](com/finderfeed/fdlib/systems/screen/screen_particles/FDScreenParticleEngine.java) |
| `FDMusicSystem` | class | — | EventBusSubscriber | 15 | 183 | [FDMusicSystem.java](com/finderfeed/fdlib/systems/music/FDMusicSystem.java) |
| `ScreenEffectOverlay` | class | Layer | EventBusSubscriber | 4 | 55 | [ScreenEffectOverlay.java](com/finderfeed/fdlib/systems/screen/screen_effect/ScreenEffectOverlay.java) |
| `FDBossbars` | class | — | EventBusSubscriber | 5 | 49 | [FDBossbars.java](com/finderfeed/fdlib/systems/hud/bossbars/FDBossbars.java) |
| `FDRegistries` | class | — | EventBusSubscriber | 2 | 51 | [FDRegistries.java](com/finderfeed/fdlib/systems/FDRegistries.java) |
| `FDHuds` | class | — | EventBusSubscriber | 1 | 24 | [FDHuds.java](com/finderfeed/fdlib/systems/hud/FDHuds.java) |
| `FDCoreShaders` | class | — | EventBusSubscriber | 1 | 30 | [FDCoreShaders.java](com/finderfeed/fdlib/init/FDCoreShaders.java) |
| `ItemEntityRendererMixin` | class | — | Mixin | 2 | 64 | [ItemEntityRendererMixin.java](com/finderfeed/fdlib/mixin/ItemEntityRendererMixin.java) |
| `MouseHandlerMixin` | class | — | Mixin | 1 | 19 | [MouseHandlerMixin.java](com/finderfeed/fdlib/mixin/MouseHandlerMixin.java) |
| `FDLib` | class | — | Mod | 1 | 48 | [FDLib.java](com/finderfeed/fdlib/FDLib.java) |
| `ItemInHandRendererMixin` | class | — | Mixin | 2 | 72 | [ItemInHandRendererMixin.java](com/finderfeed/fdlib/mixin/ItemInHandRendererMixin.java) |
| `FDRenderUtil` | class | RenderType | — | 48 | 673 | [FDRenderUtil.java](com/finderfeed/fdlib/util/rendering/FDRenderUtil.java) |
| `ShapeOnCurveRenderer` | class | — | — | 32 | 474 | [ShapeOnCurveRenderer.java](com/finderfeed/fdlib/util/rendering/renderers/ShapeOnCurveRenderer.java) |
| `FDScreenParticle` | class | — | — | 40 | 223 | [FDScreenParticle.java](com/finderfeed/fdlib/systems/screen/screen_particles/FDScreenParticle.java) |
| `TriggerClientsideConfigReloadPacket` | class | FDPacket | RegisterFDPacket | 3 | 57 | [TriggerClientsideConfigReloadPacket.java](com/finderfeed/fdlib/systems/config/packets/TriggerClientsideConfigReloadPacket.java) |
| `GameRendererMixin` | class | — | Mixin | 5 | 44 | [GameRendererMixin.java](com/finderfeed/fdlib/mixin/GameRendererMixin.java) |
| `FDMathUtil` | class | — | — | 36 | 465 | [FDMathUtil.java](com/finderfeed/fdlib/util/math/FDMathUtil.java) |
| `FDScreenComponent` | class | GuiEventListener | — | 33 | 181 | [FDScreenComponent.java](com/finderfeed/fdlib/systems/screen/FDScreenComponent.java) |
| `FDTextBox` | class | ValueComponent | — | 36 | 586 | [FDTextBox.java](com/finderfeed/fdlib/systems/screen/default_components/text/FDTextBox.java) |
| `FDWidget` | class | GuiEventListener | — | 46 | 421 | [FDWidget.java](com/finderfeed/fdlib/systems/simple_screen/FDWidget.java) |
| `ItemInHandLayerMixin` | class | — | Mixin | 2 | 67 | [ItemInHandLayerMixin.java](com/finderfeed/fdlib/mixin/ItemInHandLayerMixin.java) |
| `AbstractContainerScreenMixin` | class | — | Mixin | 2 | 45 | [AbstractContainerScreenMixin.java](com/finderfeed/fdlib/mixin/AbstractContainerScreenMixin.java) |
| `FDClientPacketExecutables` | class | — | — | 14 | 133 | [FDClientPacketExecutables.java](com/finderfeed/fdlib/FDClientPacketExecutables.java) |
| `JsonConfigSyncPacket` | class | FDPacket | RegisterFDPacket | 3 | 77 | [JsonConfigSyncPacket.java](com/finderfeed/fdlib/systems/config/packets/JsonConfigSyncPacket.java) |
| `AnimationTicker` | class | — | — | 31 | 264 | [AnimationTicker.java](com/finderfeed/fdlib/systems/bedrock/animations/animation_system/AnimationTicker.java) |
| `FDBlockEntityRenderer` | class | BlockEntityRenderer<T> | — | 6 | 122 | [FDBlockEntityRenderer.java](com/finderfeed/fdlib/systems/bedrock/animations/animation_system/tile/renderer/FDBlockEntityRenderer.java) |
| `FDBlockEntityRendererBuilder` | class | — | — | 8 | 59 | [FDBlockEntityRendererBuilder.java](com/finderfeed/fdlib/systems/bedrock/animations/animation_system/tile/renderer/FDBlockEntityRendererBuilder.java) |
| `TextBlockWidget` | class | FDScrollableWidget | — | 24 | 210 | [TextBlockWidget.java](com/finderfeed/fdlib/systems/simple_screen/fdwidgets/text_block/TextBlockWidget.java) |
| `QuadRenderer` | class | — | — | 20 | 278 | [QuadRenderer.java](com/finderfeed/fdlib/util/rendering/renderers/QuadRenderer.java) |
| `FDClientConfig` | class | ReflectiveJsonConfig | — | 1 | 21 | [FDClientConfig.java](com/finderfeed/fdlib/FDClientConfig.java) |
| `ClientCameraEntity` | class | LivingEntity | — | 14 | 79 | [ClientCameraEntity.java](com/finderfeed/fdlib/systems/cutscenes/ClientCameraEntity.java) |
| `FDScreen` | class | Screen | — | 19 | 277 | [FDScreen.java](com/finderfeed/fdlib/systems/screen/FDScreen.java) |
| `ReflectiveJsonConfig` | class | JsonConfig | — | 5 | 201 | [ReflectiveJsonConfig.java](com/finderfeed/fdlib/systems/config/ReflectiveJsonConfig.java) |
| `FDModelItemRendererOptions` | class | — | — | 12 | 78 | [FDModelItemRendererOptions.java](com/finderfeed/fdlib/systems/bedrock/animations/animation_system/item/FDModelItemRendererOptions.java) |
| `BossBarEventPacket` | class | FDPacket | RegisterFDPacket | 3 | 50 | [BossBarEventPacket.java](com/finderfeed/fdlib/systems/hud/bossbars/packets/BossBarEventPacket.java) |
| `FunctionRegistry` | class | — | — | 2 | 58 | [FunctionRegistry.java](com/finderfeed/fdlib/shunting_yard/FunctionRegistry.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 22 | [MinecraftMixin.java](com/finderfeed/fdlib/mixin/MinecraftMixin.java) |
| `LocalPlayerMixin` | class | — | Mixin | 1 | 19 | [LocalPlayerMixin.java](com/finderfeed/fdlib/mixin/LocalPlayerMixin.java) |
| `KeyboardInputMixin` | class | — | Mixin | 1 | 21 | [KeyboardInputMixin.java](com/finderfeed/fdlib/mixin/KeyboardInputMixin.java) |
| `ChannelAccessMixin` | class | — | Mixin | 1 | 26 | [ChannelAccessMixin.java](com/finderfeed/fdlib/mixin/ChannelAccessMixin.java) |
| `ParticleEngineMixin` | class | — | Mixin | 1 | 42 | [ParticleEngineMixin.java](com/finderfeed/fdlib/mixin/ParticleEngineMixin.java) |
| `ChannelMixin` | class | — | Mixin | 2 | 31 | [ChannelMixin.java](com/finderfeed/fdlib/mixin/ChannelMixin.java) |
| `SoundEngineMixin` | class | — | Mixin | 2 | 36 | [SoundEngineMixin.java](com/finderfeed/fdlib/mixin/SoundEngineMixin.java) |
| `FDByteBufCodecs` | class | — | — | 26 | 370 | [FDByteBufCodecs.java](com/finderfeed/fdlib/util/FDByteBufCodecs.java) |
| `CutsceneData` | class | AutoSerializable | — | 23 | 184 | [CutsceneData.java](com/finderfeed/fdlib/systems/cutscenes/CutsceneData.java) |
| `SendScreenEffectPacket` | class | — | RegisterFDPacket | 3 | 63 | [SendScreenEffectPacket.java](com/finderfeed/fdlib/systems/screen/screen_effect/SendScreenEffectPacket.java) |

## 事件处理（`@SubscribeEvent`，22 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CutsceneCameraHandler` | 9 | `com.finderfeed.fdlib.systems.cutscenes` | [CutsceneCameraHandler.java](com/finderfeed/fdlib/systems/cutscenes/CutsceneCameraHandler.java) |
| `FDClientModEvents` | 5 | `com.finderfeed.fdlib.init` | [FDClientModEvents.java](com/finderfeed/fdlib/init/FDClientModEvents.java) |
| `ShatteredScreenEffectHandler` | 5 | `com.finderfeed.fdlib.systems.broken_screen_effect` | [ShatteredScreenEffectHandler.java](com/finderfeed/fdlib/systems/broken_screen_effect/ShatteredScreenEffectHandler.java) |
| `FDScreenParticleEngine` | 5 | `com.finderfeed.fdlib.systems.screen.screen_particles` | [FDScreenParticleEngine.java](com/finderfeed/fdlib/systems/screen/screen_particles/FDScreenParticleEngine.java) |
| `FDMusicSystem` | 4 | `com.finderfeed.fdlib.systems.music` | [FDMusicSystem.java](com/finderfeed/fdlib/systems/music/FDMusicSystem.java) |
| `ImpactFramesHandler` | 3 | `com.finderfeed.fdlib.systems.impact_frames` | [ImpactFramesHandler.java](com/finderfeed/fdlib/systems/impact_frames/ImpactFramesHandler.java) |
| `ChromaticAbberationHandler` | 3 | `com.finderfeed.fdlib.systems.chromatic_abberation_effect` | [ChromaticAbberationHandler.java](com/finderfeed/fdlib/systems/chromatic_abberation_effect/ChromaticAbberationHandler.java) |
| `ClientMixinHandler` | 2 | `com.finderfeed.fdlib` | [ClientMixinHandler.java](com/finderfeed/fdlib/ClientMixinHandler.java) |
| `FDGameEvents` | 2 | `com.finderfeed.fdlib.init` | [FDGameEvents.java](com/finderfeed/fdlib/init/FDGameEvents.java) |
| `FDMusicAreasHandler` | 2 | `com.finderfeed.fdlib.systems.music.music_areas` | [FDMusicAreasHandler.java](com/finderfeed/fdlib/systems/music/music_areas/FDMusicAreasHandler.java) |
| `FDBossbars` | 2 | `com.finderfeed.fdlib.systems.hud.bossbars` | [FDBossbars.java](com/finderfeed/fdlib/systems/hud/bossbars/FDBossbars.java) |
| `FDCommandsRegistry` | 1 | `com.finderfeed.fdlib.commands` | [FDCommandsRegistry.java](com/finderfeed/fdlib/commands/FDCommandsRegistry.java) |
| `FDItemAnimationHandler` | 1 | `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item` | [FDItemAnimationHandler.java](com/finderfeed/fdlib/systems/bedrock/animations/animation_system/item/FDItemAnimationHandler.java) |
| `PacketHandler` | 1 | `com.finderfeed.fdlib.network` | [PacketHandler.java](com/finderfeed/fdlib/network/PacketHandler.java) |
| `FDModEvents` | 1 | `com.finderfeed.fdlib.init` | [FDModEvents.java](com/finderfeed/fdlib/init/FDModEvents.java) |
| `FDPostShadersHandler` | 1 | `com.finderfeed.fdlib.systems.post_shaders` | [FDPostShadersHandler.java](com/finderfeed/fdlib/systems/post_shaders/FDPostShadersHandler.java) |
| `ParticleEmitterHandler` | 1 | `com.finderfeed.fdlib.systems.particle.particle_emitter` | [ParticleEmitterHandler.java](com/finderfeed/fdlib/systems/particle/particle_emitter/ParticleEmitterHandler.java) |
| `ScreenEffectOverlay` | 1 | `com.finderfeed.fdlib.systems.screen.screen_effect` | [ScreenEffectOverlay.java](com/finderfeed/fdlib/systems/screen/screen_effect/ScreenEffectOverlay.java) |
| `FDRegistries` | 1 | `com.finderfeed.fdlib.systems` | [FDRegistries.java](com/finderfeed/fdlib/systems/FDRegistries.java) |
| `FDHuds` | 1 | `com.finderfeed.fdlib.systems.hud` | [FDHuds.java](com/finderfeed/fdlib/systems/hud/FDHuds.java) |
| `FDCoreShaders` | 1 | `com.finderfeed.fdlib.init` | [FDCoreShaders.java](com/finderfeed/fdlib/init/FDCoreShaders.java) |
| `FDEntities` | 1 | `com.finderfeed.fdlib.init` | [FDEntities.java](com/finderfeed/fdlib/init/FDEntities.java) |

## Mixin（13 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemEntityRendererMixin` | `ItemEntityRenderer` | [ItemEntityRendererMixin.java](com/finderfeed/fdlib/mixin/ItemEntityRendererMixin.java) |
| `MouseHandlerMixin` | `MouseHandler` | [MouseHandlerMixin.java](com/finderfeed/fdlib/mixin/MouseHandlerMixin.java) |
| `ItemInHandRendererMixin` | `ItemInHandRenderer` | [ItemInHandRendererMixin.java](com/finderfeed/fdlib/mixin/ItemInHandRendererMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](com/finderfeed/fdlib/mixin/GameRendererMixin.java) |
| `ItemInHandLayerMixin` | `ItemInHandLayer` | [ItemInHandLayerMixin.java](com/finderfeed/fdlib/mixin/ItemInHandLayerMixin.java) |
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](com/finderfeed/fdlib/mixin/AbstractContainerScreenMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/finderfeed/fdlib/mixin/MinecraftMixin.java) |
| `LocalPlayerMixin` | `LocalPlayer` | [LocalPlayerMixin.java](com/finderfeed/fdlib/mixin/LocalPlayerMixin.java) |
| `KeyboardInputMixin` | `KeyboardInput` | [KeyboardInputMixin.java](com/finderfeed/fdlib/mixin/KeyboardInputMixin.java) |
| `ChannelAccessMixin` | `ChannelAccess` | [ChannelAccessMixin.java](com/finderfeed/fdlib/mixin/ChannelAccessMixin.java) |
| `ParticleEngineMixin` | `ParticleEngine` | [ParticleEngineMixin.java](com/finderfeed/fdlib/mixin/ParticleEngineMixin.java) |
| `ChannelMixin` | `Channel` | [ChannelMixin.java](com/finderfeed/fdlib/mixin/ChannelMixin.java) |
| `SoundEngineMixin` | `SoundEngine` | [SoundEngineMixin.java](com/finderfeed/fdlib/mixin/SoundEngineMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TriggerClientsideConfigReloadPacket` | RegisterFDPacket | [TriggerClientsideConfigReloadPacket.java](com/finderfeed/fdlib/systems/config/packets/TriggerClientsideConfigReloadPacket.java) |
| `JsonConfigSyncPacket` | RegisterFDPacket | [JsonConfigSyncPacket.java](com/finderfeed/fdlib/systems/config/packets/JsonConfigSyncPacket.java) |
| `FDClientConfig` | — | [FDClientConfig.java](com/finderfeed/fdlib/FDClientConfig.java) |
| `ReflectiveJsonConfig` | — | [ReflectiveJsonConfig.java](com/finderfeed/fdlib/systems/config/ReflectiveJsonConfig.java) |
| `JsonConfig` | — | [JsonConfig.java](com/finderfeed/fdlib/systems/config/JsonConfig.java) |
| `ConfigValue` | Retention;Target | [ConfigValue.java](com/finderfeed/fdlib/systems/config/ConfigValue.java) |
| `TestConfignEntry` | — | [TestConfignEntry.java](com/finderfeed/fdlib/systems/config/test/TestConfignEntry.java) |
| `TestConfig` | — | [TestConfig.java](com/finderfeed/fdlib/systems/config/test/TestConfig.java) |
| `FDConfigs` | — | [FDConfigs.java](com/finderfeed/fdlib/init/FDConfigs.java) |

## 按包浏览

- `com.finderfeed.fdlib`（8 类）
- `com.finderfeed.fdlib.commands`（2 类）
- `com.finderfeed.fdlib.data_structures`（4 类）
- `com.finderfeed.fdlib.init`（19 类）
- `com.finderfeed.fdlib.mixin`（13 类）
- `com.finderfeed.fdlib.nbt`（6 类）
- `com.finderfeed.fdlib.network`（3 类）
- `com.finderfeed.fdlib.network.lib_packets`（2 类）
- `com.finderfeed.fdlib.shunting_yard`（4 类）
- `com.finderfeed.fdlib.shunting_yard.functions`（25 类）
- `com.finderfeed.fdlib.shunting_yard.sy_base`（6 类）
- `com.finderfeed.fdlib.systems`（1 类）
- `com.finderfeed.fdlib.systems.bedrock`（2 类）
- `com.finderfeed.fdlib.systems.bedrock.animations`（7 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system`（7 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity`（6 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head`（7 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.packets`（4 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer`（8 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item`（8 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.animated_item`（5 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.interfaces`（2 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.packets`（1 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system`（3 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments`（6 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.instances.fdmodel`（4 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.instances.item_stack`（4 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.entity_model_system`（3 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.entity_model_system.packets`（3 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.tile_model_system`（1 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile`（5 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.packets`（4 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.renderer`（8 类）
- `com.finderfeed.fdlib.systems.bedrock.animations.keyframe_processors`（4 类）
- `com.finderfeed.fdlib.systems.bedrock.models`（8 类）
- `com.finderfeed.fdlib.systems.bedrock.models.model_render_info`（3 类）
- `com.finderfeed.fdlib.systems.broken_screen_effect`（3 类）
- `com.finderfeed.fdlib.systems.chromatic_abberation_effect`（2 类）
- `com.finderfeed.fdlib.systems.config`（5 类）
- `com.finderfeed.fdlib.systems.config.packets`（2 类）
- `com.finderfeed.fdlib.systems.config.test`（2 类）
- `com.finderfeed.fdlib.systems.cutscenes`（9 类）
- `com.finderfeed.fdlib.systems.cutscenes.camera_motion`（5 类）
- `com.finderfeed.fdlib.systems.cutscenes.packets`（3 类）
- `com.finderfeed.fdlib.systems.entity.action_chain`（8 类）
- `com.finderfeed.fdlib.systems.hud`（1 类）
- `com.finderfeed.fdlib.systems.hud.bossbars`（6 类）
- `com.finderfeed.fdlib.systems.hud.bossbars.packets`（4 类）
- `com.finderfeed.fdlib.systems.impact_frames`（3 类）
- `com.finderfeed.fdlib.systems.multiblock`（3 类）
- `com.finderfeed.fdlib.systems.music`（4 类）
- `com.finderfeed.fdlib.systems.music.data`（2 类）
- `com.finderfeed.fdlib.systems.music.music_areas`（2 类）
- `com.finderfeed.fdlib.systems.music.music_areas.shapes`（2 类）
- `com.finderfeed.fdlib.systems.music.packets`（5 类）
- `com.finderfeed.fdlib.systems.particle`（8 类）
- `com.finderfeed.fdlib.systems.particle.particle_emitter`（9 类）
- `com.finderfeed.fdlib.systems.particle.particle_emitter.processors`（2 类）
- `com.finderfeed.fdlib.systems.post_shaders`（5 类）
- `com.finderfeed.fdlib.systems.render_types`（2 类）
- `com.finderfeed.fdlib.systems.render_types.instances`（7 类）
- `com.finderfeed.fdlib.systems.screen`（6 类）
- `com.finderfeed.fdlib.systems.screen.annotations`（7 类）
- `com.finderfeed.fdlib.systems.screen.default_components`（1 类）
- `com.finderfeed.fdlib.systems.screen.default_components.buttons`（5 类）
- `com.finderfeed.fdlib.systems.screen.default_components.buttons.selector_button`（3 类）
- `com.finderfeed.fdlib.systems.screen.default_components.misc`（3 类）
- `com.finderfeed.fdlib.systems.screen.default_components.text`（5 类）
- `com.finderfeed.fdlib.systems.screen.default_components.texture_components`（2 类）
- `com.finderfeed.fdlib.systems.screen.default_components.value_components`（3 类）
- `com.finderfeed.fdlib.systems.screen.screen_effect`（7 类）
- `com.finderfeed.fdlib.systems.screen.screen_effect.instances`（1 类）
- `com.finderfeed.fdlib.systems.screen.screen_effect.instances.chromatic_abberation`（2 类）
- `com.finderfeed.fdlib.systems.screen.screen_effect.instances.datas`（2 类）
- `com.finderfeed.fdlib.systems.screen.screen_particles`（4 类）
- `com.finderfeed.fdlib.systems.screen.test`（2 类）
- `com.finderfeed.fdlib.systems.shake`（8 类）
- `com.finderfeed.fdlib.systems.shapes`（1 类）
- `com.finderfeed.fdlib.systems.simple_screen`（4 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets`（3 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.interfaces`（2 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block`（3 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions`（5 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_entries`（3 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_entries.image_entry`（2 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_parser`（4 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_parser.processors`（4 类）
- `com.finderfeed.fdlib.systems.simple_screen.fdwidgets.util`（3 类）
- `com.finderfeed.fdlib.systems.trails`（4 类）
- `com.finderfeed.fdlib.test`（2 类）
- `com.finderfeed.fdlib.util`（8 类）
- `com.finderfeed.fdlib.util.client`（2 类）
- `com.finderfeed.fdlib.util.client.particles`（4 类）
- `com.finderfeed.fdlib.util.client.particles.ball_particle`（2 类）
- `com.finderfeed.fdlib.util.client.particles.lightning_particle`（2 类）
- `com.finderfeed.fdlib.util.client.particles.options`（1 类）
- `com.finderfeed.fdlib.util.math`（3 类）
- `com.finderfeed.fdlib.util.math.curves`（4 类）
- `com.finderfeed.fdlib.util.rendering`（3 类）
- `com.finderfeed.fdlib.util.rendering.renderers`（2 类）
