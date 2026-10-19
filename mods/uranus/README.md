# Uranus（`uranus`）

> jar：`[前置]uranus.jar` ｜ 版本：`2.4` ｜ 作者：IAFEnvoy
> 规模：**143 个类 / 30 个包 / 9484 行** ｜ 事件处理类 1 ｜ Mixin 14 ｜ 注册/管理类 12

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.iafenvoy.uranus.object.entity.pathfinding.raycoms` | 16 | `PathingStuckHandler`、`AdvancedPathNavigate`、`ChunkCache` |
| `com.iafenvoy.uranus.mixin` | 13 | `ServerEntityManagerMixin`、`PlayerManagerMixin`、`BuiltinModelItemRendererMixin` |
| `com.iafenvoy.uranus.object` | 8 | `RegistryHelper`、`EntityUtil`、`BlockUtil` |
| `com.iafenvoy.uranus.client.model` | 8 | `AdvancedEntityModel`、`AdvancedModelBox`、`TabulaModelHandler` |
| `com.iafenvoy.uranus.client.model.tabula` | 8 | `JsonUtils`、`TabulaAnimationComponentContainer`、`Transform` |
| `com.iafenvoy.uranus.object.item` | 8 | `ArmorWithTickItem`、`SwordItemBase`、`FoilSwordItemBase` |
| `com.iafenvoy.uranus.util.function.function` | 8 | `Function3`、`Function9`、`Function4` |
| `com.iafenvoy.uranus.util.function.consumer` | 8 | `Consumer4`、`Consumer6`、`Consumer5` |
| `com.iafenvoy.uranus.util.function.predicate` | 8 | `Predicate3`、`Predicate6`、`Predicate4` |
| `com.iafenvoy.uranus.object.entity.pathfinding.raycoms.pathjobs` | 5 | `AbstractPathJob`、`PathJobMoveToLocation`、`PathJobRandomPos` |
| `com.iafenvoy.uranus.animation` | 5 | `AnimationEvents`、`AnimationHandler`、`IAnimatedEntity` |
| `com.iafenvoy.uranus.client.model.tools` | 5 | `BasicModelRenderer`、`ControlledAnimation`、`IntermittentAnimation` |
| `com.iafenvoy.uranus.util` | 5 | `ClientUtils`、`ShapeBuilder`、`RandomHelper` |
| `com.iafenvoy.uranus.client.render` | 5 | `DynamicItemRenderer`、`EntityTextureProvider`、`EntityWithMarkerTextureProvider` |
| `com.iafenvoy.uranus.event` | 4 | `EntityEvents`、`LivingEntityEvents`、`PlayerEvents` |
| `com.iafenvoy.uranus.client.render.armor` | 4 | `IArmorRendererBase`、`ArmorModelBase`、`IArmorRenderHelper` |
| `com.iafenvoy.uranus.object.entity.collision` | 4 | `CustomCollisionsBlockCollisions`、`CustomCollisionsNavigator`、`CustomCollisionsNodeProcessor` |
| `com.iafenvoy.uranus.client.model.util` | 3 | `TabulaModelHandlerHelper`、`HideableModelRenderer`、`HideableLayer` |
| `com.iafenvoy.uranus.util.function.tuple` | 3 | `Tuple2`、`Tuple4`、`Tuple3` |
| `com.iafenvoy.uranus.neoforge` | 2 | `UranusNeoForgeClient`、`UranusNeoForge` |
| `com.iafenvoy.uranus.network` | 2 | `PacketBufferUtils`、`AnimationPayload` |
| `com.iafenvoy.uranus.client.model.basic` | 2 | `BasicModelPart`、`BasicEntityModel` |
| `com.iafenvoy.uranus` | 2 | `ServerHelper`、`Uranus` |
| `com.iafenvoy.uranus.neoforge.mixin` | 1 | `ArmorFeatureRendererMixin` |
| `com.iafenvoy.uranus.data` | 1 | `EntityPropertyDelegate` |
| `com.iafenvoy.uranus.world` | 1 | `WorldChunkUtil` |
| `com.iafenvoy.uranus.client` | 1 | `UranusClient` |
| `com.iafenvoy.uranus.object.entity` | 1 | `IIntermittentEntity` |
| `architectury_inject_uranus_common_182844b02d204e2d8c2df5c00adadf59_2505cb53797abfadde84919911884c691be58d0338a6fc7344e60b6eb84aa9e4uranuscommon24devjar` | 1 | `PlatformMethods` |
| `com.iafenvoy.uranus.util.function` | 1 | `MemorizeSupplier` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `UranusNeoForgeClient` | class | — | EventBusSubscriber | 1 | 17 | [UranusNeoForgeClient.java](com/iafenvoy/uranus/neoforge/UranusNeoForgeClient.java) |
| `ServerEntityManagerMixin` | class | — | Mixin | 1 | 21 | [ServerEntityManagerMixin.java](com/iafenvoy/uranus/mixin/ServerEntityManagerMixin.java) |
| `PlayerManagerMixin` | class | — | Mixin | 1 | 22 | [PlayerManagerMixin.java](com/iafenvoy/uranus/mixin/PlayerManagerMixin.java) |
| `AbstractPathJob` | class | Callable<Path> | — | 48 | 1066 | [AbstractPathJob.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/pathjobs/AbstractPathJob.java) |
| `UranusNeoForge` | class | — | Mod | 0 | 12 | [UranusNeoForge.java](com/iafenvoy/uranus/neoforge/UranusNeoForge.java) |
| `BuiltinModelItemRendererMixin` | class | — | Mixin、OnlyIn | 1 | 34 | [BuiltinModelItemRendererMixin.java](com/iafenvoy/uranus/mixin/BuiltinModelItemRendererMixin.java) |
| `EntityEvents` | class | — | — | 0 | 33 | [EntityEvents.java](com/iafenvoy/uranus/event/EntityEvents.java) |
| `LivingEntityEvents` | class | — | — | 0 | 33 | [LivingEntityEvents.java](com/iafenvoy/uranus/event/LivingEntityEvents.java) |
| `PathingStuckHandler` | class | IStuckHandler | — | 24 | 370 | [PathingStuckHandler.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathingStuckHandler.java) |
| `AdvancedPathNavigate` | class | AbstractAdvancedPathNavigate | — | 44 | 725 | [AdvancedPathNavigate.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/AdvancedPathNavigate.java) |
| `ArmorFeatureRendererMixin` | class | — | Mixin、OnlyIn | 1 | 55 | [ArmorFeatureRendererMixin.java](com/iafenvoy/uranus/neoforge/mixin/ArmorFeatureRendererMixin.java) |
| `ArmorFeatureRendererMixin` | class | — | Mixin、OnlyIn | 1 | 41 | [ArmorFeatureRendererMixin.java](com/iafenvoy/uranus/mixin/ArmorFeatureRendererMixin.java) |
| `PlayerEntityMixin` | class | — | Mixin | 1 | 21 | [PlayerEntityMixin.java](com/iafenvoy/uranus/mixin/PlayerEntityMixin.java) |
| `EntityMixin` | class | — | Mixin | 2 | 30 | [EntityMixin.java](com/iafenvoy/uranus/mixin/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 3 | 41 | [LivingEntityMixin.java](com/iafenvoy/uranus/mixin/LivingEntityMixin.java) |
| `ClientWorldMixin` | class | — | Mixin | 1 | 21 | [ClientWorldMixin.java](com/iafenvoy/uranus/mixin/ClientWorldMixin.java) |
| `EntityTrackerEntryMixin` | class | — | Mixin | 1 | 25 | [EntityTrackerEntryMixin.java](com/iafenvoy/uranus/mixin/EntityTrackerEntryMixin.java) |
| `PlayerEvents` | class | — | — | 0 | 17 | [PlayerEvents.java](com/iafenvoy/uranus/event/PlayerEvents.java) |
| `AnimationEvents` | class | — | — | 0 | 29 | [AnimationEvents.java](com/iafenvoy/uranus/animation/AnimationEvents.java) |
| `RegistryHelper` | class | — | — | 6 | 37 | [RegistryHelper.java](com/iafenvoy/uranus/object/RegistryHelper.java) |
| `AdvancedEntityModel` | class | BasicEntityModel | OnlyIn | 25 | 151 | [AdvancedEntityModel.java](com/iafenvoy/uranus/client/model/AdvancedEntityModel.java) |
| `TabulaModelHandlerHelper` | class | — | — | 7 | 104 | [TabulaModelHandlerHelper.java](com/iafenvoy/uranus/client/model/util/TabulaModelHandlerHelper.java) |
| `BasicModelRenderer` | class | — | OnlyIn | 19 | 540 | [BasicModelRenderer.java](com/iafenvoy/uranus/client/model/tools/BasicModelRenderer.java) |
| `PacketBufferUtils` | class | — | — | 10 | 100 | [PacketBufferUtils.java](com/iafenvoy/uranus/network/PacketBufferUtils.java) |
| `LevelPropertiesMixin` | class | — | Mixin | 1 | 28 | [LevelPropertiesMixin.java](com/iafenvoy/uranus/mixin/LevelPropertiesMixin.java) |
| `ModelLoaderMixin` | class | — | Mixin | 1 | 21 | [ModelLoaderMixin.java](com/iafenvoy/uranus/mixin/ModelLoaderMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 1 | 20 | [MinecraftServerMixin.java](com/iafenvoy/uranus/mixin/MinecraftServerMixin.java) |
| `PlayerInventoryMixin` | class | — | Mixin | 1 | 25 | [PlayerInventoryMixin.java](com/iafenvoy/uranus/mixin/PlayerInventoryMixin.java) |
| `AdvancedModelBox` | class | BasicModelPart | OnlyIn | 25 | 381 | [AdvancedModelBox.java](com/iafenvoy/uranus/client/model/AdvancedModelBox.java) |
| `ChunkCache` | class | LevelReader | — | 27 | 209 | [ChunkCache.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/ChunkCache.java) |
| `AnimationHandler` | enum | — | — | 2 | 38 | [AnimationHandler.java](com/iafenvoy/uranus/animation/AnimationHandler.java) |
| `TabulaModelHandler` | enum | JsonDeserializationContext | OnlyIn | 2 | 35 | [TabulaModelHandler.java](com/iafenvoy/uranus/client/model/TabulaModelHandler.java) |
| `JsonUtils` | class | — | — | 21 | 194 | [JsonUtils.java](com/iafenvoy/uranus/client/model/tabula/JsonUtils.java) |
| `ControlledAnimation` | class | — | — | 25 | 151 | [ControlledAnimation.java](com/iafenvoy/uranus/client/model/tools/ControlledAnimation.java) |
| `IStuckHandler` | interface | — | — | 0 | 6 | [IStuckHandler.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/IStuckHandler.java) |
| `MNode` | class | Comparable<MNode> | — | 28 | 189 | [MNode.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/MNode.java) |
| `ClientUtils` | class | — | OnlyIn | 5 | 34 | [ClientUtils.java](com/iafenvoy/uranus/util/ClientUtils.java) |
| `DynamicItemRenderer` | interface | — | OnlyIn | 0 | 20 | [DynamicItemRenderer.java](com/iafenvoy/uranus/client/render/DynamicItemRenderer.java) |
| `BasicModelPart` | class | — | OnlyIn | 17 | 384 | [BasicModelPart.java](com/iafenvoy/uranus/client/model/basic/BasicModelPart.java) |
| `IntermittentAnimation` | class | — | — | 24 | 186 | [IntermittentAnimation.java](com/iafenvoy/uranus/client/model/tools/IntermittentAnimation.java) |
| `EntityUtil` | class | — | — | 5 | 45 | [EntityUtil.java](com/iafenvoy/uranus/object/EntityUtil.java) |
| `PathingOptions` | class | — | — | 24 | 127 | [PathingOptions.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathingOptions.java) |
| `Event` | class | — | — | 3 | 27 | [Event.java](com/iafenvoy/uranus/event/Event.java) |
| `ShapeBuilder` | class | — | — | 19 | 196 | [ShapeBuilder.java](com/iafenvoy/uranus/util/ShapeBuilder.java) |
| `TabulaAnimationComponentContainer` | class | — | — | 15 | 82 | [TabulaAnimationComponentContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaAnimationComponentContainer.java) |
| `BlockUtil` | class | — | — | 4 | 25 | [BlockUtil.java](com/iafenvoy/uranus/object/BlockUtil.java) |
| `PathResult` | class | — | — | 18 | 122 | [PathResult.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathResult.java) |
| `EntityPropertyDelegate` | class | ContainerData | — | 3 | 28 | [EntityPropertyDelegate.java](com/iafenvoy/uranus/data/EntityPropertyDelegate.java) |
| `WorldChunkUtil` | class | — | — | 6 | 43 | [WorldChunkUtil.java](com/iafenvoy/uranus/world/WorldChunkUtil.java) |
| `RandomHelper` | class | — | — | 7 | 37 | [RandomHelper.java](com/iafenvoy/uranus/util/RandomHelper.java) |
| `IAnimatedEntity` | interface | — | — | 0 | 16 | [IAnimatedEntity.java](com/iafenvoy/uranus/animation/IAnimatedEntity.java) |
| `UranusClient` | class | — | — | 1 | 26 | [UranusClient.java](com/iafenvoy/uranus/client/UranusClient.java) |
| `EntityTextureProvider` | interface | — | — | 0 | 12 | [EntityTextureProvider.java](com/iafenvoy/uranus/client/render/EntityTextureProvider.java) |
| `EntityWithMarkerTextureProvider` | interface | — | — | 0 | 9 | [EntityWithMarkerTextureProvider.java](com/iafenvoy/uranus/client/render/EntityWithMarkerTextureProvider.java) |
| `IArmorRendererBase` | interface | — | OnlyIn | 0 | 61 | [IArmorRendererBase.java](com/iafenvoy/uranus/client/render/armor/IArmorRendererBase.java) |
| `Transform` | class | — | OnlyIn | 12 | 73 | [Transform.java](com/iafenvoy/uranus/client/model/Transform.java) |
| `ModelAnimator` | class | — | OnlyIn | 14 | 176 | [ModelAnimator.java](com/iafenvoy/uranus/client/model/ModelAnimator.java) |
| `BasicEntityModel` | class | EntityModel | — | 4 | 34 | [BasicEntityModel.java](com/iafenvoy/uranus/client/model/basic/BasicEntityModel.java) |
| `Transform` | class | — | OnlyIn | 12 | 73 | [Transform.java](com/iafenvoy/uranus/client/model/tabula/Transform.java) |
| `TabulaCubeContainer` | class | — | — | 14 | 108 | [TabulaCubeContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaCubeContainer.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `UranusNeoForgeClient` | 1 | `com.iafenvoy.uranus.neoforge` | [UranusNeoForgeClient.java](com/iafenvoy/uranus/neoforge/UranusNeoForgeClient.java) |

## Mixin（14 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ServerEntityManagerMixin` | `ServerEntityManager` | [ServerEntityManagerMixin.java](com/iafenvoy/uranus/mixin/ServerEntityManagerMixin.java) |
| `PlayerManagerMixin` | `PlayerManager` | [PlayerManagerMixin.java](com/iafenvoy/uranus/mixin/PlayerManagerMixin.java) |
| `BuiltinModelItemRendererMixin` | `BuiltinModelItemRenderer` | [BuiltinModelItemRendererMixin.java](com/iafenvoy/uranus/mixin/BuiltinModelItemRendererMixin.java) |
| `ArmorFeatureRendererMixin` | `ArmorFeatureRenderer` | [ArmorFeatureRendererMixin.java](com/iafenvoy/uranus/neoforge/mixin/ArmorFeatureRendererMixin.java) |
| `ArmorFeatureRendererMixin` | `ArmorFeatureRenderer` | [ArmorFeatureRendererMixin.java](com/iafenvoy/uranus/mixin/ArmorFeatureRendererMixin.java) |
| `PlayerEntityMixin` | `PlayerEntity` | [PlayerEntityMixin.java](com/iafenvoy/uranus/mixin/PlayerEntityMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/iafenvoy/uranus/mixin/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/iafenvoy/uranus/mixin/LivingEntityMixin.java) |
| `ClientWorldMixin` | `ClientWorld` | [ClientWorldMixin.java](com/iafenvoy/uranus/mixin/ClientWorldMixin.java) |
| `EntityTrackerEntryMixin` | `EntityTrackerEntry` | [EntityTrackerEntryMixin.java](com/iafenvoy/uranus/mixin/EntityTrackerEntryMixin.java) |
| `LevelPropertiesMixin` | `LevelProperties` | [LevelPropertiesMixin.java](com/iafenvoy/uranus/mixin/LevelPropertiesMixin.java) |
| `ModelLoaderMixin` | `ModelLoader` | [ModelLoaderMixin.java](com/iafenvoy/uranus/mixin/ModelLoaderMixin.java) |
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](com/iafenvoy/uranus/mixin/MinecraftServerMixin.java) |
| `PlayerInventoryMixin` | `PlayerInventory` | [PlayerInventoryMixin.java](com/iafenvoy/uranus/mixin/PlayerInventoryMixin.java) |

## 全部类（143）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `UranusNeoForgeClient` | class | — | EventBusSubscriber | 1 | 17 | [UranusNeoForgeClient.java](com/iafenvoy/uranus/neoforge/UranusNeoForgeClient.java) |
| `ServerEntityManagerMixin` | class | — | Mixin | 1 | 21 | [ServerEntityManagerMixin.java](com/iafenvoy/uranus/mixin/ServerEntityManagerMixin.java) |
| `PlayerManagerMixin` | class | — | Mixin | 1 | 22 | [PlayerManagerMixin.java](com/iafenvoy/uranus/mixin/PlayerManagerMixin.java) |
| `AbstractPathJob` | class | Callable<Path> | — | 48 | 1066 | [AbstractPathJob.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/pathjobs/AbstractPathJob.java) |
| `UranusNeoForge` | class | — | Mod | 0 | 12 | [UranusNeoForge.java](com/iafenvoy/uranus/neoforge/UranusNeoForge.java) |
| `BuiltinModelItemRendererMixin` | class | — | Mixin、OnlyIn | 1 | 34 | [BuiltinModelItemRendererMixin.java](com/iafenvoy/uranus/mixin/BuiltinModelItemRendererMixin.java) |
| `EntityEvents` | class | — | — | 0 | 33 | [EntityEvents.java](com/iafenvoy/uranus/event/EntityEvents.java) |
| `LivingEntityEvents` | class | — | — | 0 | 33 | [LivingEntityEvents.java](com/iafenvoy/uranus/event/LivingEntityEvents.java) |
| `PathingStuckHandler` | class | IStuckHandler | — | 24 | 370 | [PathingStuckHandler.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathingStuckHandler.java) |
| `AdvancedPathNavigate` | class | AbstractAdvancedPathNavigate | — | 44 | 725 | [AdvancedPathNavigate.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/AdvancedPathNavigate.java) |
| `ArmorFeatureRendererMixin` | class | — | Mixin、OnlyIn | 1 | 55 | [ArmorFeatureRendererMixin.java](com/iafenvoy/uranus/neoforge/mixin/ArmorFeatureRendererMixin.java) |
| `ArmorFeatureRendererMixin` | class | — | Mixin、OnlyIn | 1 | 41 | [ArmorFeatureRendererMixin.java](com/iafenvoy/uranus/mixin/ArmorFeatureRendererMixin.java) |
| `PlayerEntityMixin` | class | — | Mixin | 1 | 21 | [PlayerEntityMixin.java](com/iafenvoy/uranus/mixin/PlayerEntityMixin.java) |
| `EntityMixin` | class | — | Mixin | 2 | 30 | [EntityMixin.java](com/iafenvoy/uranus/mixin/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 3 | 41 | [LivingEntityMixin.java](com/iafenvoy/uranus/mixin/LivingEntityMixin.java) |
| `ClientWorldMixin` | class | — | Mixin | 1 | 21 | [ClientWorldMixin.java](com/iafenvoy/uranus/mixin/ClientWorldMixin.java) |
| `EntityTrackerEntryMixin` | class | — | Mixin | 1 | 25 | [EntityTrackerEntryMixin.java](com/iafenvoy/uranus/mixin/EntityTrackerEntryMixin.java) |
| `PlayerEvents` | class | — | — | 0 | 17 | [PlayerEvents.java](com/iafenvoy/uranus/event/PlayerEvents.java) |
| `AnimationEvents` | class | — | — | 0 | 29 | [AnimationEvents.java](com/iafenvoy/uranus/animation/AnimationEvents.java) |
| `RegistryHelper` | class | — | — | 6 | 37 | [RegistryHelper.java](com/iafenvoy/uranus/object/RegistryHelper.java) |
| `AdvancedEntityModel` | class | BasicEntityModel | OnlyIn | 25 | 151 | [AdvancedEntityModel.java](com/iafenvoy/uranus/client/model/AdvancedEntityModel.java) |
| `TabulaModelHandlerHelper` | class | — | — | 7 | 104 | [TabulaModelHandlerHelper.java](com/iafenvoy/uranus/client/model/util/TabulaModelHandlerHelper.java) |
| `BasicModelRenderer` | class | — | OnlyIn | 19 | 540 | [BasicModelRenderer.java](com/iafenvoy/uranus/client/model/tools/BasicModelRenderer.java) |
| `PacketBufferUtils` | class | — | — | 10 | 100 | [PacketBufferUtils.java](com/iafenvoy/uranus/network/PacketBufferUtils.java) |
| `LevelPropertiesMixin` | class | — | Mixin | 1 | 28 | [LevelPropertiesMixin.java](com/iafenvoy/uranus/mixin/LevelPropertiesMixin.java) |
| `ModelLoaderMixin` | class | — | Mixin | 1 | 21 | [ModelLoaderMixin.java](com/iafenvoy/uranus/mixin/ModelLoaderMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 1 | 20 | [MinecraftServerMixin.java](com/iafenvoy/uranus/mixin/MinecraftServerMixin.java) |
| `PlayerInventoryMixin` | class | — | Mixin | 1 | 25 | [PlayerInventoryMixin.java](com/iafenvoy/uranus/mixin/PlayerInventoryMixin.java) |
| `AdvancedModelBox` | class | BasicModelPart | OnlyIn | 25 | 381 | [AdvancedModelBox.java](com/iafenvoy/uranus/client/model/AdvancedModelBox.java) |
| `ChunkCache` | class | LevelReader | — | 27 | 209 | [ChunkCache.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/ChunkCache.java) |
| `AnimationHandler` | enum | — | — | 2 | 38 | [AnimationHandler.java](com/iafenvoy/uranus/animation/AnimationHandler.java) |
| `TabulaModelHandler` | enum | JsonDeserializationContext | OnlyIn | 2 | 35 | [TabulaModelHandler.java](com/iafenvoy/uranus/client/model/TabulaModelHandler.java) |
| `JsonUtils` | class | — | — | 21 | 194 | [JsonUtils.java](com/iafenvoy/uranus/client/model/tabula/JsonUtils.java) |
| `ControlledAnimation` | class | — | — | 25 | 151 | [ControlledAnimation.java](com/iafenvoy/uranus/client/model/tools/ControlledAnimation.java) |
| `IStuckHandler` | interface | — | — | 0 | 6 | [IStuckHandler.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/IStuckHandler.java) |
| `MNode` | class | Comparable<MNode> | — | 28 | 189 | [MNode.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/MNode.java) |
| `ClientUtils` | class | — | OnlyIn | 5 | 34 | [ClientUtils.java](com/iafenvoy/uranus/util/ClientUtils.java) |
| `DynamicItemRenderer` | interface | — | OnlyIn | 0 | 20 | [DynamicItemRenderer.java](com/iafenvoy/uranus/client/render/DynamicItemRenderer.java) |
| `BasicModelPart` | class | — | OnlyIn | 17 | 384 | [BasicModelPart.java](com/iafenvoy/uranus/client/model/basic/BasicModelPart.java) |
| `IntermittentAnimation` | class | — | — | 24 | 186 | [IntermittentAnimation.java](com/iafenvoy/uranus/client/model/tools/IntermittentAnimation.java) |
| `EntityUtil` | class | — | — | 5 | 45 | [EntityUtil.java](com/iafenvoy/uranus/object/EntityUtil.java) |
| `PathingOptions` | class | — | — | 24 | 127 | [PathingOptions.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathingOptions.java) |
| `Event` | class | — | — | 3 | 27 | [Event.java](com/iafenvoy/uranus/event/Event.java) |
| `ShapeBuilder` | class | — | — | 19 | 196 | [ShapeBuilder.java](com/iafenvoy/uranus/util/ShapeBuilder.java) |
| `TabulaAnimationComponentContainer` | class | — | — | 15 | 82 | [TabulaAnimationComponentContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaAnimationComponentContainer.java) |
| `BlockUtil` | class | — | — | 4 | 25 | [BlockUtil.java](com/iafenvoy/uranus/object/BlockUtil.java) |
| `PathResult` | class | — | — | 18 | 122 | [PathResult.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathResult.java) |
| `EntityPropertyDelegate` | class | ContainerData | — | 3 | 28 | [EntityPropertyDelegate.java](com/iafenvoy/uranus/data/EntityPropertyDelegate.java) |
| `WorldChunkUtil` | class | — | — | 6 | 43 | [WorldChunkUtil.java](com/iafenvoy/uranus/world/WorldChunkUtil.java) |
| `RandomHelper` | class | — | — | 7 | 37 | [RandomHelper.java](com/iafenvoy/uranus/util/RandomHelper.java) |
| `IAnimatedEntity` | interface | — | — | 0 | 16 | [IAnimatedEntity.java](com/iafenvoy/uranus/animation/IAnimatedEntity.java) |
| `UranusClient` | class | — | — | 1 | 26 | [UranusClient.java](com/iafenvoy/uranus/client/UranusClient.java) |
| `EntityTextureProvider` | interface | — | — | 0 | 12 | [EntityTextureProvider.java](com/iafenvoy/uranus/client/render/EntityTextureProvider.java) |
| `EntityWithMarkerTextureProvider` | interface | — | — | 0 | 9 | [EntityWithMarkerTextureProvider.java](com/iafenvoy/uranus/client/render/EntityWithMarkerTextureProvider.java) |
| `IArmorRendererBase` | interface | — | OnlyIn | 0 | 61 | [IArmorRendererBase.java](com/iafenvoy/uranus/client/render/armor/IArmorRendererBase.java) |
| `Transform` | class | — | OnlyIn | 12 | 73 | [Transform.java](com/iafenvoy/uranus/client/model/Transform.java) |
| `ModelAnimator` | class | — | OnlyIn | 14 | 176 | [ModelAnimator.java](com/iafenvoy/uranus/client/model/ModelAnimator.java) |
| `BasicEntityModel` | class | EntityModel | — | 4 | 34 | [BasicEntityModel.java](com/iafenvoy/uranus/client/model/basic/BasicEntityModel.java) |
| `Transform` | class | — | OnlyIn | 12 | 73 | [Transform.java](com/iafenvoy/uranus/client/model/tabula/Transform.java) |
| `TabulaCubeContainer` | class | — | — | 14 | 108 | [TabulaCubeContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaCubeContainer.java) |
| `TabulaModelContainer` | class | — | — | 12 | 78 | [TabulaModelContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaModelContainer.java) |
| `HideableModelRenderer` | class | AdvancedModelBox | — | 3 | 56 | [HideableModelRenderer.java](com/iafenvoy/uranus/client/model/util/HideableModelRenderer.java) |
| `IIntermittentEntity` | interface | — | — | 0 | 6 | [IIntermittentEntity.java](com/iafenvoy/uranus/object/entity/IIntermittentEntity.java) |
| `AbstractAdvancedPathNavigate` | class | GroundPathNavigation | — | 14 | 62 | [AbstractAdvancedPathNavigate.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/AbstractAdvancedPathNavigate.java) |
| `PathPointExtended` | class | Node | — | 12 | 81 | [PathPointExtended.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathPointExtended.java) |
| `CustomCollisionsBlockCollisions` | class | AbstractIterator | — | 2 | 105 | [CustomCollisionsBlockCollisions.java](com/iafenvoy/uranus/object/entity/collision/CustomCollisionsBlockCollisions.java) |
| `ArmorWithTickItem` | class | ArmorItem | — | 2 | 32 | [ArmorWithTickItem.java](com/iafenvoy/uranus/object/item/ArmorWithTickItem.java) |
| `SwordItemBase` | class | SwordItem | — | 2 | 26 | [SwordItemBase.java](com/iafenvoy/uranus/object/item/SwordItemBase.java) |
| `FoilSwordItemBase` | class | SwordItemBase | — | 1 | 20 | [FoilSwordItemBase.java](com/iafenvoy/uranus/object/item/FoilSwordItemBase.java) |
| `FoilItemBase` | class | ItemBase | — | 1 | 20 | [FoilItemBase.java](com/iafenvoy/uranus/object/item/FoilItemBase.java) |
| `ItemBase` | class | Item | — | 3 | 32 | [ItemBase.java](com/iafenvoy/uranus/object/item/ItemBase.java) |
| `ToolMaterialUtil` | class | — | — | 8 | 44 | [ToolMaterialUtil.java](com/iafenvoy/uranus/object/item/ToolMaterialUtil.java) |
| `ServerHelper` | class | — | — | 1 | 18 | [ServerHelper.java](com/iafenvoy/uranus/ServerHelper.java) |
| `Tuple2` | class | — | — | 5 | 36 | [Tuple2.java](com/iafenvoy/uranus/util/function/tuple/Tuple2.java) |
| `AnimationAI` | class | Goal | — | 7 | 43 | [AnimationAI.java](com/iafenvoy/uranus/animation/AnimationAI.java) |
| `ArmorModelBase` | class | HumanoidModel | — | 6 | 91 | [ArmorModelBase.java](com/iafenvoy/uranus/client/render/armor/ArmorModelBase.java) |
| `IArmorRenderHelper` | interface | — | OnlyIn | 0 | 35 | [IArmorRenderHelper.java](com/iafenvoy/uranus/client/render/armor/IArmorRenderHelper.java) |
| `TabulaModel` | class | AdvancedEntityModel | OnlyIn | 9 | 116 | [TabulaModel.java](com/iafenvoy/uranus/client/model/TabulaModel.java) |
| `TabulaModelRenderUtils` | class | — | OnlyIn | 1 | 152 | [TabulaModelRenderUtils.java](com/iafenvoy/uranus/client/model/TabulaModelRenderUtils.java) |
| `TabulaCubeGroupContainer` | class | — | — | 6 | 38 | [TabulaCubeGroupContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaCubeGroupContainer.java) |
| `ChainBuffer` | class | — | OnlyIn | 7 | 113 | [ChainBuffer.java](com/iafenvoy/uranus/client/model/tools/ChainBuffer.java) |
| `DamageUtil` | class | — | — | 2 | 18 | [DamageUtil.java](com/iafenvoy/uranus/object/DamageUtil.java) |
| `IdUtil` | class | — | — | 1 | 10 | [IdUtil.java](com/iafenvoy/uranus/object/IdUtil.java) |
| `PathUtil` | class | — | — | 2 | 20 | [PathUtil.java](com/iafenvoy/uranus/object/PathUtil.java) |
| `VecUtil` | class | — | — | 2 | 15 | [VecUtil.java](com/iafenvoy/uranus/object/VecUtil.java) |
| `SoundUtil` | class | — | — | 3 | 45 | [SoundUtil.java](com/iafenvoy/uranus/object/SoundUtil.java) |
| `PathJobMoveToLocation` | class | AbstractPathJob | — | 5 | 56 | [PathJobMoveToLocation.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/pathjobs/PathJobMoveToLocation.java) |
| `PathJobRandomPos` | class | AbstractPathJob | — | 5 | 79 | [PathJobRandomPos.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/pathjobs/PathJobRandomPos.java) |
| `CustomCollisionsNavigator` | class | GroundPathNavigation | — | 5 | 143 | [CustomCollisionsNavigator.java](com/iafenvoy/uranus/object/entity/collision/CustomCollisionsNavigator.java) |
| `FoodUtils` | class | — | — | 2 | 31 | [FoodUtils.java](com/iafenvoy/uranus/object/item/FoodUtils.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_uranus_common_182844b02d204e2d8c2df5c00adadf59_2505cb53797abfadde84919911884c691be58d0338a6fc7344e60b6eb84aa9e4uranuscommon24devjar/PlatformMethods.java) |
| `Uranus` | class | — | — | 1 | 22 | [Uranus.java](com/iafenvoy/uranus/Uranus.java) |
| `AnimationPayload` | record | CustomPacketPayload | — | 1 | 26 | [AnimationPayload.java](com/iafenvoy/uranus/network/AnimationPayload.java) |
| `Timeout` | class | — | — | 4 | 51 | [Timeout.java](com/iafenvoy/uranus/util/Timeout.java) |
| `StackUnderflowError` | class | Error | OnlyIn | 0 | 15 | [StackUnderflowError.java](com/iafenvoy/uranus/util/StackUnderflowError.java) |
| `MemorizeSupplier` | class | Supplier<T> | — | 1 | 24 | [MemorizeSupplier.java](com/iafenvoy/uranus/util/function/MemorizeSupplier.java) |
| `Tuple4` | class | Tuple3 | — | 3 | 27 | [Tuple4.java](com/iafenvoy/uranus/util/function/tuple/Tuple4.java) |
| `Tuple3` | class | Tuple2 | — | 3 | 27 | [Tuple3.java](com/iafenvoy/uranus/util/function/tuple/Tuple3.java) |
| `Function3` | interface | — | — | 0 | 7 | [Function3.java](com/iafenvoy/uranus/util/function/function/Function3.java) |
| `Function9` | interface | — | — | 0 | 7 | [Function9.java](com/iafenvoy/uranus/util/function/function/Function9.java) |
| `Function4` | interface | — | — | 0 | 7 | [Function4.java](com/iafenvoy/uranus/util/function/function/Function4.java) |
| `Function7` | interface | — | — | 0 | 7 | [Function7.java](com/iafenvoy/uranus/util/function/function/Function7.java) |
| `Function6` | interface | — | — | 0 | 7 | [Function6.java](com/iafenvoy/uranus/util/function/function/Function6.java) |
| `Function5` | interface | — | — | 0 | 7 | [Function5.java](com/iafenvoy/uranus/util/function/function/Function5.java) |
| `Function8` | interface | — | — | 0 | 7 | [Function8.java](com/iafenvoy/uranus/util/function/function/Function8.java) |
| `Function2` | interface | — | — | 0 | 7 | [Function2.java](com/iafenvoy/uranus/util/function/function/Function2.java) |
| `Consumer4` | interface | — | — | 0 | 7 | [Consumer4.java](com/iafenvoy/uranus/util/function/consumer/Consumer4.java) |
| `Consumer6` | interface | — | — | 0 | 7 | [Consumer6.java](com/iafenvoy/uranus/util/function/consumer/Consumer6.java) |
| `Consumer5` | interface | — | — | 0 | 7 | [Consumer5.java](com/iafenvoy/uranus/util/function/consumer/Consumer5.java) |
| `Consumer3` | interface | — | — | 0 | 7 | [Consumer3.java](com/iafenvoy/uranus/util/function/consumer/Consumer3.java) |
| `Consumer2` | interface | — | — | 0 | 7 | [Consumer2.java](com/iafenvoy/uranus/util/function/consumer/Consumer2.java) |
| `Consumer7` | interface | — | — | 0 | 7 | [Consumer7.java](com/iafenvoy/uranus/util/function/consumer/Consumer7.java) |
| `Consumer8` | interface | — | — | 0 | 7 | [Consumer8.java](com/iafenvoy/uranus/util/function/consumer/Consumer8.java) |
| `Consumer9` | interface | — | — | 0 | 7 | [Consumer9.java](com/iafenvoy/uranus/util/function/consumer/Consumer9.java) |
| `Predicate3` | interface | — | — | 0 | 7 | [Predicate3.java](com/iafenvoy/uranus/util/function/predicate/Predicate3.java) |
| `Predicate6` | interface | — | — | 0 | 7 | [Predicate6.java](com/iafenvoy/uranus/util/function/predicate/Predicate6.java) |
| `Predicate4` | interface | — | — | 0 | 7 | [Predicate4.java](com/iafenvoy/uranus/util/function/predicate/Predicate4.java) |
| `Predicate9` | interface | — | — | 0 | 7 | [Predicate9.java](com/iafenvoy/uranus/util/function/predicate/Predicate9.java) |
| `Predicate5` | interface | — | — | 0 | 7 | [Predicate5.java](com/iafenvoy/uranus/util/function/predicate/Predicate5.java) |
| `Predicate8` | interface | — | — | 0 | 7 | [Predicate8.java](com/iafenvoy/uranus/util/function/predicate/Predicate8.java) |
| `Predicate7` | interface | — | — | 0 | 7 | [Predicate7.java](com/iafenvoy/uranus/util/function/predicate/Predicate7.java) |
| `Predicate2` | interface | — | — | 0 | 7 | [Predicate2.java](com/iafenvoy/uranus/util/function/predicate/Predicate2.java) |
| `Animation` | class | — | — | 4 | 32 | [Animation.java](com/iafenvoy/uranus/animation/Animation.java) |
| `ArrayLayeredTexture` | class | AbstractTexture | — | 2 | 65 | [ArrayLayeredTexture.java](com/iafenvoy/uranus/client/render/ArrayLayeredTexture.java) |
| `SimpleTexture` | class | AbstractTexture | OnlyIn | 2 | 26 | [SimpleTexture.java](com/iafenvoy/uranus/client/render/SimpleTexture.java) |
| `IArmorTextureProvider` | interface | — | — | 0 | 13 | [IArmorTextureProvider.java](com/iafenvoy/uranus/client/render/armor/IArmorTextureProvider.java) |
| `ITabulaModelAnimator` | interface | — | OnlyIn | 0 | 12 | [ITabulaModelAnimator.java](com/iafenvoy/uranus/client/model/ITabulaModelAnimator.java) |
| `TextureOffset` | class | — | OnlyIn | 0 | 16 | [TextureOffset.java](com/iafenvoy/uranus/client/model/tabula/TextureOffset.java) |
| `TabulaAnimationContainer` | class | — | — | 4 | 29 | [TabulaAnimationContainer.java](com/iafenvoy/uranus/client/model/tabula/TabulaAnimationContainer.java) |
| `HideableLayer` | class | — | — | 1 | 37 | [HideableLayer.java](com/iafenvoy/uranus/client/model/util/HideableLayer.java) |
| `BasicModelBase` | class | EntityModel | OnlyIn | 3 | 36 | [BasicModelBase.java](com/iafenvoy/uranus/client/model/tools/BasicModelBase.java) |
| `IPassabilityNavigator` | interface | — | — | 0 | 13 | [IPassabilityNavigator.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/IPassabilityNavigator.java) |
| `Pathfinding` | class | ThreadFactory | — | 2 | 45 | [Pathfinding.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/Pathfinding.java) |
| `SurfaceType` | enum | — | — | 3 | 119 | [SurfaceType.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/SurfaceType.java) |
| `ITallWalker` | interface | — | — | 0 | 6 | [ITallWalker.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/ITallWalker.java) |
| `IAdvancedPathingMob` | interface | — | — | 0 | 6 | [IAdvancedPathingMob.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/IAdvancedPathingMob.java) |
| `PathFindingStatus` | enum | — | — | 0 | 10 | [PathFindingStatus.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathFindingStatus.java) |
| `PathfindingConstants` | class | — | — | 0 | 22 | [PathfindingConstants.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/PathfindingConstants.java) |
| `ICustomSizeNavigator` | interface | — | — | 0 | 10 | [ICustomSizeNavigator.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/pathjobs/ICustomSizeNavigator.java) |
| `PathJobMoveAwayFromLocation` | class | AbstractPathJob | — | 3 | 33 | [PathJobMoveAwayFromLocation.java](com/iafenvoy/uranus/object/entity/pathfinding/raycoms/pathjobs/PathJobMoveAwayFromLocation.java) |
| `CustomCollisionsNodeProcessor` | class | WalkNodeEvaluator | — | 4 | 68 | [CustomCollisionsNodeProcessor.java](com/iafenvoy/uranus/object/entity/collision/CustomCollisionsNodeProcessor.java) |
| `ICustomCollisions` | interface | — | — | 2 | 99 | [ICustomCollisions.java](com/iafenvoy/uranus/object/entity/collision/ICustomCollisions.java) |
| `ISwingable` | interface | — | — | 0 | 12 | [ISwingable.java](com/iafenvoy/uranus/object/item/ISwingable.java) |
