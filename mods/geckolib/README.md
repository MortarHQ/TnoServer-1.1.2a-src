# GeckoLib 4（`geckolib`）

> jar：`[前置]geckolib.jar` ｜ 版本：`4.9.2` ｜ 作者：Gecko, Eliot, AzureDoom, DerToaster, Tslat, Witixin
> 规模：**195 个类 / 44 个包 / 15904 行** ｜ 事件处理类 0 ｜ Mixin 7 ｜ 注册/管理类 6

**依赖**：`neoforge`、`minecraft`、`geckoanimfix`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `software.bernie.geckolib.network.packet` | 16 | `BlockEntityDataSyncPacket`、`BlockEntityAnimTriggerPacket`、`StatelessBlockEntityPlayAnimPacket` |
| `software.bernie.geckolib.loading.json.raw` | 13 | `Model`、`MinecraftGeometry`、`UVUnion` |
| `software.bernie.geckolib.loading.math.function.generic` | 12 | `ASinFunction`、`CosFunction`、`ACosFunction` |
| `software.bernie.geckolib.animation` | 9 | `ContextAwareAnimatableManager`、`AnimatableManager`、`AnimationController` |
| `software.bernie.geckolib.loading.math.value` | 9 | `Calculation`、`Variable`、`CompoundValue` |
| `software.bernie.geckolib.renderer` | 7 | `GeoEntityRenderer`、`GeoReplacedEntityRenderer`、`GeoArmorRenderer` |
| `software.bernie.geckolib.util` | 7 | `RenderUtil`、`Color`、`ClientUtil` |
| `software.bernie.geckolib.renderer.layer` | 7 | `BlockAndItemGeoLayer`、`ItemArmorGeoLayer`、`GeoRenderLayer` |
| `software.bernie.geckolib.animatable.stateless` | 7 | `StatelessGeoBlockEntity`、`StatelessGeoEntity`、`StatelessGeoReplacedEntity` |
| `software.bernie.geckolib.animation.keyframe` | 7 | `BoneAnimationQueue`、`Keyframe`、`KeyframeStack` |
| `software.bernie.geckolib.loading.math.function.round` | 7 | `CeilFunction`、`FloorFunction`、`HermiteBlendFunction` |
| `software.bernie.geckolib.animatable` | 6 | `GeoBlockEntity`、`GeoReplacedEntity`、`GeoEntity` |
| `software.bernie.geckolib.cache.object` | 5 | `GeoBone`、`GeoVertex`、`GeoQuad` |
| `software.bernie.geckolib.model` | 5 | `DefaultedEntityGeoModel`、`GeoModel`、`DefaultedItemGeoModel` |
| `software.bernie.geckolib.renderer.layer.vanilla` | 5 | `GeoBlockVanillaRenderLayer`、`GeoItemVanillaRenderLayer`、`GeoEntityVanillaRenderLayer` |
| `software.bernie.geckolib.mixin.client` | 4 | `BlockEntityWithoutLevelRendererMixin`、`TextureManagerMixin`、`LevelRendererMixin` |
| `software.bernie.geckolib` | 4 | `GeckoLib`、`GeckoLibClient`、`GeckoLibServices` |
| `software.bernie.geckolib.service` | 4 | `GeckoLibEvents`、`GeckoLibNetworking`、`GeckoLibClient` |
| `software.bernie.geckolib.loading.math` | 4 | `MathParser`、`MolangQueries`、`Operator` |
| `software.bernie.geckolib.renderer.specialty` | 4 | `DynamicGeoEntityRenderer`、`DynamicGeoItemRenderer`、`DynamicGeoBlockRenderer` |
| `software.bernie.geckolib.loading.object` | 4 | `BakedModelFactory`、`BakedAnimations`、`GeometryTree` |
| `software.bernie.geckolib.animation.keyframe.event` | 4 | `KeyFrameEvent`、`CustomInstructionKeyframeEvent`、`SoundKeyframeEvent` |
| `software.bernie.geckolib.loading.math.function.random` | 4 | `DieRollFunction`、`RandomFunction`、`RandomIntegerFunction` |
| `software.bernie.geckolib.animation.keyframe.event.data` | 4 | `ParticleKeyframeData`、`CustomInstructionKeyframeData`、`KeyFrameData` |
| `software.bernie.geckolib.mixin.common` | 3 | `AbstractContainerMenuMixin`、`LivingEntityMixin`、`ItemStackMixin` |
| `software.bernie.geckolib.cache.texture` | 3 | `AnimatableTexture`、`GeoAbstractTexture`、`AutoGlowingTexture` |
| `software.bernie.geckolib.loading.math.function.misc` | 3 | `PiFunction`、`ToRadFunction`、`ToDegFunction` |
| `software.bernie.geckolib.loading.math.function.limit` | 3 | `MinFunction`、`ClampFunction`、`MaxFunction` |
| `software.bernie.geckolib.animatable.instance` | 3 | `AnimatableInstanceCache`、`InstancedAnimatableInstanceCache`、`SingletonAnimatableInstanceCache` |
| `software.bernie.geckolib.event` | 2 | `GeoRenderEvent`、`GeckoLibEventsNeoForge` |
| `software.bernie.geckolib.constant.dataticket` | 2 | `SerializableDataTicket`、`DataTicket` |
| `software.bernie.geckolib.constant` | 2 | `DefaultAnimations`、`DataTickets` |
| `software.bernie.geckolib.loading.json.typeadapter` | 2 | `BakedAnimationsAdapter`、`KeyFramesAdapter` |
| `software.bernie.geckolib.platform` | 2 | `GeckoLibClientNeoForge`、`GeckoLibNeoForge` |
| `software.bernie.geckolib.cache` | 2 | `GeckoLibCache`、`AnimatableIdCache` |
| `software.bernie.geckolib.animatable.client` | 2 | `DeferredGeoRenderProvider`、`GeoRenderProvider` |
| `software.bernie.geckolib.animation.keyframe.event.builtin` | 1 | `AutoPlayingSoundKeyframeHandler` |
| `software.bernie.geckolib.animation.state` | 1 | `BoneSnapshot` |
| `software.bernie.geckolib.network` | 1 | `GeckoLibNetworkingNeoForge` |
| `software.bernie.geckolib.model.data` | 1 | `EntityModelData` |
| … | | 其余 4 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BlockEntityWithoutLevelRendererMixin` | class | — | Mixin | 0 | 33 | [BlockEntityWithoutLevelRendererMixin.java](software/bernie/geckolib/mixin/client/BlockEntityWithoutLevelRendererMixin.java) |
| `GeoRenderEvent` | interface | Event | — | 79 | 649 | [GeoRenderEvent.java](software/bernie/geckolib/event/GeoRenderEvent.java) |
| `GeoEntityRenderer` | class | EntityRenderer | — | 30 | 518 | [GeoEntityRenderer.java](software/bernie/geckolib/renderer/GeoEntityRenderer.java) |
| `GeoReplacedEntityRenderer` | class | EntityRenderer | — | 31 | 544 | [GeoReplacedEntityRenderer.java](software/bernie/geckolib/renderer/GeoReplacedEntityRenderer.java) |
| `TextureManagerMixin` | class | — | Mixin | 1 | 43 | [TextureManagerMixin.java](software/bernie/geckolib/mixin/client/TextureManagerMixin.java) |
| `GeckoLibEventsNeoForge` | class | GeckoLibEvents | — | 18 | 142 | [GeckoLibEventsNeoForge.java](software/bernie/geckolib/event/GeckoLibEventsNeoForge.java) |
| `GeckoLib` | class | — | Mod | 0 | 25 | [GeckoLib.java](software/bernie/geckolib/GeckoLib.java) |
| `GeoArmorRenderer` | class | HumanoidModel | — | 43 | 530 | [GeoArmorRenderer.java](software/bernie/geckolib/renderer/GeoArmorRenderer.java) |
| `GeoItemRenderer` | class | BlockEntityWithoutLevelRenderer | — | 22 | 257 | [GeoItemRenderer.java](software/bernie/geckolib/renderer/GeoItemRenderer.java) |
| `ContextAwareAnimatableManager` | class | AnimatableManager | — | 20 | 118 | [ContextAwareAnimatableManager.java](software/bernie/geckolib/animation/ContextAwareAnimatableManager.java) |
| `AnimatableManager` | class | — | — | 22 | 139 | [AnimatableManager.java](software/bernie/geckolib/animation/AnimatableManager.java) |
| `BlockEntityDataSyncPacket` | record | MultiloaderPacket | — | 2 | 40 | [BlockEntityDataSyncPacket.java](software/bernie/geckolib/network/packet/BlockEntityDataSyncPacket.java) |
| `AbstractContainerMenuMixin` | class | — | Mixin | 0 | 52 | [AbstractContainerMenuMixin.java](software/bernie/geckolib/mixin/common/AbstractContainerMenuMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 0 | 26 | [LivingEntityMixin.java](software/bernie/geckolib/mixin/common/LivingEntityMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 0 | 38 | [ItemStackMixin.java](software/bernie/geckolib/mixin/common/ItemStackMixin.java) |
| `LevelRendererMixin` | class | — | Mixin | 0 | 36 | [LevelRendererMixin.java](software/bernie/geckolib/mixin/client/LevelRendererMixin.java) |
| `GeoBlockRenderer` | class | GeoRenderer<T> | — | 18 | 237 | [GeoBlockRenderer.java](software/bernie/geckolib/renderer/GeoBlockRenderer.java) |
| `AnimationController` | class | — | — | 34 | 596 | [AnimationController.java](software/bernie/geckolib/animation/AnimationController.java) |
| `GeckoLibEvents` | interface | — | — | 0 | 50 | [GeckoLibEvents.java](software/bernie/geckolib/service/GeckoLibEvents.java) |
| `BlockEntityAnimTriggerPacket` | record | MultiloaderPacket | — | 2 | 41 | [BlockEntityAnimTriggerPacket.java](software/bernie/geckolib/network/packet/BlockEntityAnimTriggerPacket.java) |
| `StatelessBlockEntityPlayAnimPacket` | record | MultiloaderPacket | — | 2 | 43 | [StatelessBlockEntityPlayAnimPacket.java](software/bernie/geckolib/network/packet/StatelessBlockEntityPlayAnimPacket.java) |
| `StatelessBlockEntityStopAnimPacket` | record | MultiloaderPacket | — | 2 | 43 | [StatelessBlockEntityStopAnimPacket.java](software/bernie/geckolib/network/packet/StatelessBlockEntityStopAnimPacket.java) |
| `StopTriggeredBlockEntityAnimPacket` | record | MultiloaderPacket | — | 2 | 41 | [StopTriggeredBlockEntityAnimPacket.java](software/bernie/geckolib/network/packet/StopTriggeredBlockEntityAnimPacket.java) |
| `GeoBone` | class | — | — | 71 | 388 | [GeoBone.java](software/bernie/geckolib/cache/object/GeoBone.java) |
| `RenderUtil` | class | — | — | 28 | 232 | [RenderUtil.java](software/bernie/geckolib/util/RenderUtil.java) |
| `EntityDataSyncPacket` | record | MultiloaderPacket | — | 2 | 52 | [EntityDataSyncPacket.java](software/bernie/geckolib/network/packet/EntityDataSyncPacket.java) |
| `MathParser` | class | — | — | 24 | 488 | [MathParser.java](software/bernie/geckolib/loading/math/MathParser.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 61 | [HumanoidArmorLayerMixin.java](software/bernie/geckolib/mixin/client/HumanoidArmorLayerMixin.java) |
| `StatelessEntityPlayAnimPacket` | record | MultiloaderPacket | — | 2 | 52 | [StatelessEntityPlayAnimPacket.java](software/bernie/geckolib/network/packet/StatelessEntityPlayAnimPacket.java) |
| `StopTriggeredEntityAnimPacket` | record | MultiloaderPacket | — | 2 | 58 | [StopTriggeredEntityAnimPacket.java](software/bernie/geckolib/network/packet/StopTriggeredEntityAnimPacket.java) |
| `EntityAnimTriggerPacket` | record | MultiloaderPacket | — | 2 | 54 | [EntityAnimTriggerPacket.java](software/bernie/geckolib/network/packet/EntityAnimTriggerPacket.java) |
| `StatelessEntityStopAnimPacket` | record | MultiloaderPacket | — | 2 | 51 | [StatelessEntityStopAnimPacket.java](software/bernie/geckolib/network/packet/StatelessEntityStopAnimPacket.java) |
| `GeoObjectRenderer` | class | GeoRenderer<T> | — | 16 | 191 | [GeoObjectRenderer.java](software/bernie/geckolib/renderer/GeoObjectRenderer.java) |
| `BlockAndItemGeoLayer` | class | GeoRenderLayer | — | 6 | 121 | [BlockAndItemGeoLayer.java](software/bernie/geckolib/renderer/layer/BlockAndItemGeoLayer.java) |
| `ItemArmorGeoLayer` | class | GeoRenderLayer | — | 11 | 252 | [ItemArmorGeoLayer.java](software/bernie/geckolib/renderer/layer/ItemArmorGeoLayer.java) |
| `DynamicGeoEntityRenderer` | class | GeoEntityRenderer | — | 7 | 158 | [DynamicGeoEntityRenderer.java](software/bernie/geckolib/renderer/specialty/DynamicGeoEntityRenderer.java) |
| `DynamicGeoItemRenderer` | class | GeoItemRenderer | — | 7 | 152 | [DynamicGeoItemRenderer.java](software/bernie/geckolib/renderer/specialty/DynamicGeoItemRenderer.java) |
| `DynamicGeoBlockRenderer` | class | GeoBlockRenderer | — | 7 | 157 | [DynamicGeoBlockRenderer.java](software/bernie/geckolib/renderer/specialty/DynamicGeoBlockRenderer.java) |
| `AutoPlayingSoundKeyframeHandler` | class | AnimationController.SoundKeyframeHan | — | 1 | 35 | [AutoPlayingSoundKeyframeHandler.java](software/bernie/geckolib/animation/keyframe/event/builtin/AutoPlayingSoundKeyframeHandler.java) |
| `BoneSnapshot` | class | — | — | 28 | 173 | [BoneSnapshot.java](software/bernie/geckolib/animation/state/BoneSnapshot.java) |
| `GeckoLibNetworkingNeoForge` | class | GeckoLibNetworking | — | 5 | 55 | [GeckoLibNetworkingNeoForge.java](software/bernie/geckolib/network/GeckoLibNetworkingNeoForge.java) |
| `SingletonDataSyncPacket` | record | MultiloaderPacket | — | 2 | 41 | [SingletonDataSyncPacket.java](software/bernie/geckolib/network/packet/SingletonDataSyncPacket.java) |
| `SerializableDataTicket` | class | DataTicket | — | 15 | 82 | [SerializableDataTicket.java](software/bernie/geckolib/constant/dataticket/SerializableDataTicket.java) |
| `AnimatableTexture` | class | SimpleTexture | — | 17 | 317 | [AnimatableTexture.java](software/bernie/geckolib/cache/texture/AnimatableTexture.java) |
| `GeoBlockEntity` | interface | — | — | 0 | 95 | [GeoBlockEntity.java](software/bernie/geckolib/animatable/GeoBlockEntity.java) |
| `StatelessGeoBlockEntity` | interface | — | — | 0 | 40 | [StatelessGeoBlockEntity.java](software/bernie/geckolib/animatable/stateless/StatelessGeoBlockEntity.java) |
| `Color` | record | — | — | 23 | 188 | [Color.java](software/bernie/geckolib/util/Color.java) |
| `StopTriggeredSingletonAnimPacket` | record | MultiloaderPacket | — | 2 | 51 | [StopTriggeredSingletonAnimPacket.java](software/bernie/geckolib/network/packet/StopTriggeredSingletonAnimPacket.java) |
| `StatelessSingletonPlayAnimPacket` | record | MultiloaderPacket | — | 2 | 43 | [StatelessSingletonPlayAnimPacket.java](software/bernie/geckolib/network/packet/StatelessSingletonPlayAnimPacket.java) |
| `SingletonAnimTriggerPacket` | record | MultiloaderPacket | — | 2 | 43 | [SingletonAnimTriggerPacket.java](software/bernie/geckolib/network/packet/SingletonAnimTriggerPacket.java) |
| `StatelessSingletonStopAnimPacket` | record | MultiloaderPacket | — | 2 | 42 | [StatelessSingletonStopAnimPacket.java](software/bernie/geckolib/network/packet/StatelessSingletonStopAnimPacket.java) |
| `MultiloaderPacket` | interface | — | — | 0 | 11 | [MultiloaderPacket.java](software/bernie/geckolib/network/packet/MultiloaderPacket.java) |
| `DefaultAnimations` | class | — | — | 15 | 134 | [DefaultAnimations.java](software/bernie/geckolib/constant/DefaultAnimations.java) |
| `BakedAnimationsAdapter` | class | JsonDeserializer<BakedAnimations> | — | 11 | 259 | [BakedAnimationsAdapter.java](software/bernie/geckolib/loading/json/typeadapter/BakedAnimationsAdapter.java) |
| `MolangQueries` | class | — | — | 12 | 365 | [MolangQueries.java](software/bernie/geckolib/loading/math/MolangQueries.java) |
| `BakedModelFactory` | interface | BakedModelFactory | — | 10 | 211 | [BakedModelFactory.java](software/bernie/geckolib/loading/object/BakedModelFactory.java) |
| `GeoRenderer` | interface | — | — | 1 | 378 | [GeoRenderer.java](software/bernie/geckolib/renderer/GeoRenderer.java) |
| `DyeableGeoArmorRenderer` | class | GeoArmorRenderer | — | 6 | 80 | [DyeableGeoArmorRenderer.java](software/bernie/geckolib/renderer/specialty/DyeableGeoArmorRenderer.java) |
| `ClientUtil` | class | — | — | 2 | 16 | [ClientUtil.java](software/bernie/geckolib/util/ClientUtil.java) |
| `AnimationProcessor` | class | — | — | 11 | 247 | [AnimationProcessor.java](software/bernie/geckolib/animation/AnimationProcessor.java) |

## Mixin（7 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockEntityWithoutLevelRendererMixin` | `BlockEntityWithoutLevelRenderer` | [BlockEntityWithoutLevelRendererMixin.java](software/bernie/geckolib/mixin/client/BlockEntityWithoutLevelRendererMixin.java) |
| `TextureManagerMixin` | `TextureManager` | [TextureManagerMixin.java](software/bernie/geckolib/mixin/client/TextureManagerMixin.java) |
| `AbstractContainerMenuMixin` | `AbstractContainerMenu` | [AbstractContainerMenuMixin.java](software/bernie/geckolib/mixin/common/AbstractContainerMenuMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](software/bernie/geckolib/mixin/common/LivingEntityMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](software/bernie/geckolib/mixin/common/ItemStackMixin.java) |
| `LevelRendererMixin` | `LevelRenderer` | [LevelRendererMixin.java](software/bernie/geckolib/mixin/client/LevelRendererMixin.java) |
| `HumanoidArmorLayerMixin` | `HumanoidArmorLayer` | [HumanoidArmorLayerMixin.java](software/bernie/geckolib/mixin/client/HumanoidArmorLayerMixin.java) |

## 按包浏览

- `software.bernie.geckolib`（4 类）
- `software.bernie.geckolib.animatable`（6 类）
- `software.bernie.geckolib.animatable.client`（2 类）
- `software.bernie.geckolib.animatable.instance`（3 类）
- `software.bernie.geckolib.animatable.stateless`（7 类）
- `software.bernie.geckolib.animation`（9 类）
- `software.bernie.geckolib.animation.keyframe`（7 类）
- `software.bernie.geckolib.animation.keyframe.event`（4 类）
- `software.bernie.geckolib.animation.keyframe.event.builtin`（1 类）
- `software.bernie.geckolib.animation.keyframe.event.data`（4 类）
- `software.bernie.geckolib.animation.state`（1 类）
- `software.bernie.geckolib.cache`（2 类）
- `software.bernie.geckolib.cache.object`（5 类）
- `software.bernie.geckolib.cache.texture`（3 类）
- `software.bernie.geckolib.constant`（2 类）
- `software.bernie.geckolib.constant.dataticket`（2 类）
- `software.bernie.geckolib.event`（2 类）
- `software.bernie.geckolib.loading`（1 类）
- `software.bernie.geckolib.loading.json`（1 类）
- `software.bernie.geckolib.loading.json.raw`（13 类）
- `software.bernie.geckolib.loading.json.typeadapter`（2 类）
- `software.bernie.geckolib.loading.math`（4 类）
- `software.bernie.geckolib.loading.math.function`（1 类）
- `software.bernie.geckolib.loading.math.function.generic`（12 类）
- `software.bernie.geckolib.loading.math.function.limit`（3 类）
- `software.bernie.geckolib.loading.math.function.misc`（3 类）
- `software.bernie.geckolib.loading.math.function.random`（4 类）
- `software.bernie.geckolib.loading.math.function.round`（7 类）
- `software.bernie.geckolib.loading.math.value`（9 类）
- `software.bernie.geckolib.loading.object`（4 类）
- `software.bernie.geckolib.mixin.client`（4 类）
- `software.bernie.geckolib.mixin.common`（3 类）
- `software.bernie.geckolib.model`（5 类）
- `software.bernie.geckolib.model.data`（1 类）
- `software.bernie.geckolib.network`（1 类）
- `software.bernie.geckolib.network.packet`（16 类）
- `software.bernie.geckolib.platform`（2 类）
- `software.bernie.geckolib.renderer`（7 类）
- `software.bernie.geckolib.renderer.layer`（7 类）
- `software.bernie.geckolib.renderer.layer.vanilla`（5 类）
- `software.bernie.geckolib.renderer.specialty`（4 类）
- `software.bernie.geckolib.resource`（1 类）
- `software.bernie.geckolib.service`（4 类）
- `software.bernie.geckolib.util`（7 类）
