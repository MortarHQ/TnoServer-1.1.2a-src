# lionfishapi（`lionfishapi`）

> jar：`[灾变前置]lionfishapi.jar` ｜ 版本：`2.7` ｜ 作者：L_Ender
> 规模：**43 个类 / 16 个包 / 8170 行** ｜ 事件处理类 1 ｜ Mixin 2 ｜ 注册/管理类 1

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.L_Ender.lionfishapi.client.model.tools` | 7 | `AdvancedEntityModel`、`AdvancedModelBox`、`BasicModelPart` |
| `com.github.L_Ender.lionfishapi.server.animation` | 7 | `AnimationHandler`、`IAnimatedEntity`、`Animation` |
| `com.github.L_Ender.lionfishapi.server.entity` | 6 | `Kobolediator_Entity`、`Internal_Animation_Monster`、`IIntermittentEntity` |
| `com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations` | 4 | `AdvancedKeyframeAnimations`、`AdvancedKeyframe`、`AdvancedAnimationChannel` |
| `com.github.L_Ender.lionfishapi.client.model.Animations` | 3 | `ControlledAnimation`、`IntermittentAnimation`、`ModelAnimator` |
| `com.github.L_Ender.lionfishapi.client.model.render` | 3 | `Kobolediator_Animation`、`RendererKobolediator`、`ModelKobolediator` |
| `com.github.L_Ender.lionfishapi.client.event` | 2 | `ClientSetUp`、`EventGetFluidRenderType` |
| `com.github.L_Ender.lionfishapi.client.util` | 2 | `ClientUtils`、`LFRenderUtils` |
| `com.github.L_Ender.lionfishapi.server.event` | 2 | `StandOnFluidEvent`、`AnimationEvent` |
| `com.github.L_Ender.lionfishapi` | 1 | `LionfishAPI` |
| `com.github.L_Ender.lionfishapi.mixin.client` | 1 | `ItemBlockRenderTypesMixin` |
| `com.github.L_Ender.lionfishapi.mixin` | 1 | `EntityMixin` |
| `com.github.L_Ender.lionfishapi.client.screen` | 1 | `OptifineWarningScreen` |
| `com.github.L_Ender.lionfishapi.client.model` | 1 | `Transform` |
| `com.github.L_Ender.lionfishapi.server.network` | 1 | `AnimationMessage` |
| `com.github.L_Ender.lionfishapi.client.model.container` | 1 | `TextureOffset` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 43）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientSetUp` | class | — | EventBusSubscriber、OnlyIn | 3 | 50 | [ClientSetUp.java](com/github/L_Ender/lionfishapi/client/event/ClientSetUp.java) |
| `LionfishAPI` | class | — | Mod | 3 | 36 | [LionfishAPI.java](com/github/L_Ender/lionfishapi/LionfishAPI.java) |
| `ItemBlockRenderTypesMixin` | class | — | Mixin | 1 | 29 | [ItemBlockRenderTypesMixin.java](com/github/L_Ender/lionfishapi/mixin/client/ItemBlockRenderTypesMixin.java) |
| `Kobolediator_Entity` | class | Internal_Animation_Monster | — | 31 | 364 | [Kobolediator_Entity.java](com/github/L_Ender/lionfishapi/server/entity/Kobolediator_Entity.java) |
| `EntityMixin` | class | — | Mixin | 1 | 87 | [EntityMixin.java](com/github/L_Ender/lionfishapi/mixin/EntityMixin.java) |
| `AdvancedEntityModel` | class | BasicEntityModel | OnlyIn | 30 | 190 | [AdvancedEntityModel.java](com/github/L_Ender/lionfishapi/client/model/tools/AdvancedEntityModel.java) |
| `AdvancedModelBox` | class | BasicModelPart | OnlyIn | 40 | 565 | [AdvancedModelBox.java](com/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox.java) |
| `AnimationHandler` | enum | — | — | 2 | 48 | [AnimationHandler.java](com/github/L_Ender/lionfishapi/server/animation/AnimationHandler.java) |
| `ControlledAnimation` | class | — | OnlyIn | 25 | 154 | [ControlledAnimation.java](com/github/L_Ender/lionfishapi/client/model/Animations/ControlledAnimation.java) |
| `Kobolediator_Animation` | class | — | OnlyIn | 0 | 3609 | [Kobolediator_Animation.java](com/github/L_Ender/lionfishapi/client/model/render/Kobolediator_Animation.java) |
| `BasicModelPart` | class | — | OnlyIn | 19 | 444 | [BasicModelPart.java](com/github/L_Ender/lionfishapi/client/model/tools/BasicModelPart.java) |
| `Internal_Animation_Monster` | class | Monster | — | 17 | 209 | [Internal_Animation_Monster.java](com/github/L_Ender/lionfishapi/server/entity/Internal_Animation_Monster.java) |
| `EventGetFluidRenderType` | class | Event | OnlyIn | 5 | 46 | [EventGetFluidRenderType.java](com/github/L_Ender/lionfishapi/client/event/EventGetFluidRenderType.java) |
| `ClientUtils` | class | — | OnlyIn | 5 | 34 | [ClientUtils.java](com/github/L_Ender/lionfishapi/client/util/ClientUtils.java) |
| `IntermittentAnimation` | class | — | OnlyIn | 24 | 189 | [IntermittentAnimation.java](com/github/L_Ender/lionfishapi/client/model/Animations/IntermittentAnimation.java) |
| `StandOnFluidEvent` | class | LivingEvent | — | 1 | 20 | [StandOnFluidEvent.java](com/github/L_Ender/lionfishapi/server/event/StandOnFluidEvent.java) |
| `AnimationEvent` | class | Event | — | 4 | 49 | [AnimationEvent.java](com/github/L_Ender/lionfishapi/server/event/AnimationEvent.java) |
| `OptifineWarningScreen` | class | Screen | — | 8 | 113 | [OptifineWarningScreen.java](com/github/L_Ender/lionfishapi/client/screen/OptifineWarningScreen.java) |
| `DynamicChain` | class | — | — | 11 | 317 | [DynamicChain.java](com/github/L_Ender/lionfishapi/client/model/tools/DynamicChain.java) |
| `IIntermittentEntity` | interface | — | — | 0 | 6 | [IIntermittentEntity.java](com/github/L_Ender/lionfishapi/server/entity/IIntermittentEntity.java) |
| `IAnimatedEntity` | interface | — | — | 0 | 16 | [IAnimatedEntity.java](com/github/L_Ender/lionfishapi/server/animation/IAnimatedEntity.java) |
| `Transform` | class | — | OnlyIn | 12 | 73 | [Transform.java](com/github/L_Ender/lionfishapi/client/model/Transform.java) |
| `ModelAnimator` | class | — | OnlyIn | 12 | 131 | [ModelAnimator.java](com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.java) |
| `RendererKobolediator` | class | MobRenderer | OnlyIn | 3 | 32 | [RendererKobolediator.java](com/github/L_Ender/lionfishapi/client/model/render/RendererKobolediator.java) |
| `BasicEntityModel` | class | EntityModel | — | 4 | 34 | [BasicEntityModel.java](com/github/L_Ender/lionfishapi/client/model/tools/BasicEntityModel.java) |
| `LionfishModelRenderUtils` | class | — | — | 1 | 220 | [LionfishModelRenderUtils.java](com/github/L_Ender/lionfishapi/client/model/tools/LionfishModelRenderUtils.java) |
| `InternalAttackGoal` | class | Goal | — | 6 | 82 | [InternalAttackGoal.java](com/github/L_Ender/lionfishapi/server/entity/InternalAttackGoal.java) |
| `InternalMoveGoal` | class | Goal | — | 6 | 84 | [InternalMoveGoal.java](com/github/L_Ender/lionfishapi/server/entity/InternalMoveGoal.java) |
| `InternalStateGoal` | class | Goal | — | 6 | 75 | [InternalStateGoal.java](com/github/L_Ender/lionfishapi/server/entity/InternalStateGoal.java) |
| `Animation` | class | — | — | 6 | 42 | [Animation.java](com/github/L_Ender/lionfishapi/server/animation/Animation.java) |
| `LegSolver` | class | — | — | 8 | 108 | [LegSolver.java](com/github/L_Ender/lionfishapi/server/animation/LegSolver.java) |
| `AnimationAI` | class | Goal | — | 7 | 43 | [AnimationAI.java](com/github/L_Ender/lionfishapi/server/animation/AnimationAI.java) |
| `LFRenderUtils` | class | — | — | 2 | 36 | [LFRenderUtils.java](com/github/L_Ender/lionfishapi/client/util/LFRenderUtils.java) |
| `AdvancedKeyframeAnimations` | class | — | OnlyIn | 5 | 58 | [AdvancedKeyframeAnimations.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.java) |
| `ModelKobolediator` | class | AdvancedEntityModel | — | 4 | 258 | [ModelKobolediator.java](com/github/L_Ender/lionfishapi/client/model/render/ModelKobolediator.java) |
| `ChainBuffer` | class | — | OnlyIn | 7 | 116 | [ChainBuffer.java](com/github/L_Ender/lionfishapi/client/model/tools/ChainBuffer.java) |
| `AnimationMessage` | record | CustomPacketPayload | — | 3 | 47 | [AnimationMessage.java](com/github/L_Ender/lionfishapi/server/network/AnimationMessage.java) |
| `NamedAnimation` | class | Animation | — | 2 | 19 | [NamedAnimation.java](com/github/L_Ender/lionfishapi/server/animation/NamedAnimation.java) |
| `LegSolverQuadruped` | class | LegSolver | — | 0 | 22 | [LegSolverQuadruped.java](com/github/L_Ender/lionfishapi/server/animation/LegSolverQuadruped.java) |
| `TextureOffset` | class | — | OnlyIn | 0 | 16 | [TextureOffset.java](com/github/L_Ender/lionfishapi/client/model/container/TextureOffset.java) |
| `AdvancedKeyframe` | record | — | OnlyIn | 0 | 10 | [AdvancedKeyframe.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.java) |
| `AdvancedAnimationChannel` | record | — | — | 0 | 48 | [AdvancedAnimationChannel.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.java) |
| `AdvancedAnimationDefinition` | record | — | OnlyIn | 4 | 41 | [AdvancedAnimationDefinition.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientSetUp` | 3 | `com.github.L_Ender.lionfishapi.client.event` | [ClientSetUp.java](com/github/L_Ender/lionfishapi/client/event/ClientSetUp.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemBlockRenderTypesMixin` | `ItemBlockRenderTypes` | [ItemBlockRenderTypesMixin.java](com/github/L_Ender/lionfishapi/mixin/client/ItemBlockRenderTypesMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/github/L_Ender/lionfishapi/mixin/EntityMixin.java) |

## 全部类（43）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientSetUp` | class | — | EventBusSubscriber、OnlyIn | 3 | 50 | [ClientSetUp.java](com/github/L_Ender/lionfishapi/client/event/ClientSetUp.java) |
| `LionfishAPI` | class | — | Mod | 3 | 36 | [LionfishAPI.java](com/github/L_Ender/lionfishapi/LionfishAPI.java) |
| `ItemBlockRenderTypesMixin` | class | — | Mixin | 1 | 29 | [ItemBlockRenderTypesMixin.java](com/github/L_Ender/lionfishapi/mixin/client/ItemBlockRenderTypesMixin.java) |
| `Kobolediator_Entity` | class | Internal_Animation_Monster | — | 31 | 364 | [Kobolediator_Entity.java](com/github/L_Ender/lionfishapi/server/entity/Kobolediator_Entity.java) |
| `EntityMixin` | class | — | Mixin | 1 | 87 | [EntityMixin.java](com/github/L_Ender/lionfishapi/mixin/EntityMixin.java) |
| `AdvancedEntityModel` | class | BasicEntityModel | OnlyIn | 30 | 190 | [AdvancedEntityModel.java](com/github/L_Ender/lionfishapi/client/model/tools/AdvancedEntityModel.java) |
| `AdvancedModelBox` | class | BasicModelPart | OnlyIn | 40 | 565 | [AdvancedModelBox.java](com/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox.java) |
| `AnimationHandler` | enum | — | — | 2 | 48 | [AnimationHandler.java](com/github/L_Ender/lionfishapi/server/animation/AnimationHandler.java) |
| `ControlledAnimation` | class | — | OnlyIn | 25 | 154 | [ControlledAnimation.java](com/github/L_Ender/lionfishapi/client/model/Animations/ControlledAnimation.java) |
| `Kobolediator_Animation` | class | — | OnlyIn | 0 | 3609 | [Kobolediator_Animation.java](com/github/L_Ender/lionfishapi/client/model/render/Kobolediator_Animation.java) |
| `BasicModelPart` | class | — | OnlyIn | 19 | 444 | [BasicModelPart.java](com/github/L_Ender/lionfishapi/client/model/tools/BasicModelPart.java) |
| `Internal_Animation_Monster` | class | Monster | — | 17 | 209 | [Internal_Animation_Monster.java](com/github/L_Ender/lionfishapi/server/entity/Internal_Animation_Monster.java) |
| `EventGetFluidRenderType` | class | Event | OnlyIn | 5 | 46 | [EventGetFluidRenderType.java](com/github/L_Ender/lionfishapi/client/event/EventGetFluidRenderType.java) |
| `ClientUtils` | class | — | OnlyIn | 5 | 34 | [ClientUtils.java](com/github/L_Ender/lionfishapi/client/util/ClientUtils.java) |
| `IntermittentAnimation` | class | — | OnlyIn | 24 | 189 | [IntermittentAnimation.java](com/github/L_Ender/lionfishapi/client/model/Animations/IntermittentAnimation.java) |
| `StandOnFluidEvent` | class | LivingEvent | — | 1 | 20 | [StandOnFluidEvent.java](com/github/L_Ender/lionfishapi/server/event/StandOnFluidEvent.java) |
| `AnimationEvent` | class | Event | — | 4 | 49 | [AnimationEvent.java](com/github/L_Ender/lionfishapi/server/event/AnimationEvent.java) |
| `OptifineWarningScreen` | class | Screen | — | 8 | 113 | [OptifineWarningScreen.java](com/github/L_Ender/lionfishapi/client/screen/OptifineWarningScreen.java) |
| `DynamicChain` | class | — | — | 11 | 317 | [DynamicChain.java](com/github/L_Ender/lionfishapi/client/model/tools/DynamicChain.java) |
| `IIntermittentEntity` | interface | — | — | 0 | 6 | [IIntermittentEntity.java](com/github/L_Ender/lionfishapi/server/entity/IIntermittentEntity.java) |
| `IAnimatedEntity` | interface | — | — | 0 | 16 | [IAnimatedEntity.java](com/github/L_Ender/lionfishapi/server/animation/IAnimatedEntity.java) |
| `Transform` | class | — | OnlyIn | 12 | 73 | [Transform.java](com/github/L_Ender/lionfishapi/client/model/Transform.java) |
| `ModelAnimator` | class | — | OnlyIn | 12 | 131 | [ModelAnimator.java](com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.java) |
| `RendererKobolediator` | class | MobRenderer | OnlyIn | 3 | 32 | [RendererKobolediator.java](com/github/L_Ender/lionfishapi/client/model/render/RendererKobolediator.java) |
| `BasicEntityModel` | class | EntityModel | — | 4 | 34 | [BasicEntityModel.java](com/github/L_Ender/lionfishapi/client/model/tools/BasicEntityModel.java) |
| `LionfishModelRenderUtils` | class | — | — | 1 | 220 | [LionfishModelRenderUtils.java](com/github/L_Ender/lionfishapi/client/model/tools/LionfishModelRenderUtils.java) |
| `InternalAttackGoal` | class | Goal | — | 6 | 82 | [InternalAttackGoal.java](com/github/L_Ender/lionfishapi/server/entity/InternalAttackGoal.java) |
| `InternalMoveGoal` | class | Goal | — | 6 | 84 | [InternalMoveGoal.java](com/github/L_Ender/lionfishapi/server/entity/InternalMoveGoal.java) |
| `InternalStateGoal` | class | Goal | — | 6 | 75 | [InternalStateGoal.java](com/github/L_Ender/lionfishapi/server/entity/InternalStateGoal.java) |
| `Animation` | class | — | — | 6 | 42 | [Animation.java](com/github/L_Ender/lionfishapi/server/animation/Animation.java) |
| `LegSolver` | class | — | — | 8 | 108 | [LegSolver.java](com/github/L_Ender/lionfishapi/server/animation/LegSolver.java) |
| `AnimationAI` | class | Goal | — | 7 | 43 | [AnimationAI.java](com/github/L_Ender/lionfishapi/server/animation/AnimationAI.java) |
| `LFRenderUtils` | class | — | — | 2 | 36 | [LFRenderUtils.java](com/github/L_Ender/lionfishapi/client/util/LFRenderUtils.java) |
| `AdvancedKeyframeAnimations` | class | — | OnlyIn | 5 | 58 | [AdvancedKeyframeAnimations.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.java) |
| `ModelKobolediator` | class | AdvancedEntityModel | — | 4 | 258 | [ModelKobolediator.java](com/github/L_Ender/lionfishapi/client/model/render/ModelKobolediator.java) |
| `ChainBuffer` | class | — | OnlyIn | 7 | 116 | [ChainBuffer.java](com/github/L_Ender/lionfishapi/client/model/tools/ChainBuffer.java) |
| `AnimationMessage` | record | CustomPacketPayload | — | 3 | 47 | [AnimationMessage.java](com/github/L_Ender/lionfishapi/server/network/AnimationMessage.java) |
| `NamedAnimation` | class | Animation | — | 2 | 19 | [NamedAnimation.java](com/github/L_Ender/lionfishapi/server/animation/NamedAnimation.java) |
| `LegSolverQuadruped` | class | LegSolver | — | 0 | 22 | [LegSolverQuadruped.java](com/github/L_Ender/lionfishapi/server/animation/LegSolverQuadruped.java) |
| `TextureOffset` | class | — | OnlyIn | 0 | 16 | [TextureOffset.java](com/github/L_Ender/lionfishapi/client/model/container/TextureOffset.java) |
| `AdvancedKeyframe` | record | — | OnlyIn | 0 | 10 | [AdvancedKeyframe.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.java) |
| `AdvancedAnimationChannel` | record | — | — | 0 | 48 | [AdvancedAnimationChannel.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.java) |
| `AdvancedAnimationDefinition` | record | — | OnlyIn | 4 | 41 | [AdvancedAnimationDefinition.java](com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition.java) |
