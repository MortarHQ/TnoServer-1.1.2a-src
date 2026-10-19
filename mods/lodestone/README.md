# Lodestone（`lodestone`）

> jar：`[前置]lodestone.jar` ｜ 版本：`1.8.2` ｜ 作者：Lodestar
> 规模：**313 个类 / 79 个包 / 22825 行** ｜ 事件处理类 14 ｜ Mixin 15 ｜ 注册/管理类 22

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `team.lodestar.lodestone.helpers` | 16 | `ColorHelper`、`ItemHelper`、`EntityHelper` |
| `team.lodestar.lodestone.mixin.client` | 12 | `LevelRendererMixin`、`AbstractContainerScreenMixin`、`GameRendererMixin` |
| `team.lodestar.lodestone.registry.common` | 10 | `LodestoneNetworkPayloads`、`LodestoneCommands`、`LodestoneAttributes` |
| `team.lodestar.lodestone.systems.multiblock` | 9 | `MultiBlockComponentEntity`、`MultiBlockCoreEntity`、`MultiBlockItem` |
| `team.lodestar.lodestone.systems.enchanting` | 9 | `LodestoneEnchantmentEntityEffectHelper`、`LodestoneEnchantmentEffectActivator`、`LodestoneEnchantmentDataHelper` |
| `team.lodestar.lodestone.systems.block` | 8 | `LodestoneBlockProperties`、`LodestoneEntityBlock`、`WaterLoggedEntityBlock` |
| `team.lodestar.lodestone.handlers` | 7 | `ItemEventHandler`、`WorldEventHandler`、`WorldEventRenderHandler` |
| `team.lodestar.lodestone.systems.rendering` | 7 | `VFXBuilders`、`LodestoneRenderSystem`、`LodestoneRenderType` |
| `team.lodestar.lodestone.systems.rendering.shader` | 7 | `LodestoneShaderRegistry`、`LodestoneProgram`、`ShaderHolder` |
| `team.lodestar.lodestone.systems.particle.screen` | 7 | `LodestoneScreenParticle`、`ScreenParticleItemStackRetrievalKey`、`ScreenParticleItemStackKey` |
| `team.lodestar.lodestone.systems.postprocess` | 6 | `PostProcessHandler`、`PostProcessor`、`MultiInstancePostProcessor` |
| `team.lodestar.lodestone.registry.client` | 6 | `LodestonePostProcessEffects`、`LodestoneShaders`、`LodestoneModels` |
| `team.lodestar.lodestone.command` | 6 | `ListActiveWorldEventsCommand`、`UnfreezeActiveWorldEventsCommand`、`RemoveActiveWorldEventsCommand` |
| `team.lodestar.lodestone.systems.worldgen` | 6 | `LodestoneBlockFiller`、`LodestoneWorldgenBuilderEntry`、`LodestoneWorldgenBuilder` |
| `team.lodestar.lodestone.systems.texture` | 6 | `FlipbookVolumeTexture`、`InternalTextureFormat`、`VolumeTexture` |
| `team.lodestar.lodestone.datagen` | 5 | `DataGenerators`、`LodestoneItemTagDatagen`、`LodestoneBlockTagDatagen` |
| `team.lodestar.lodestone.systems.blockentity` | 5 | `LodestoneBlockEntityInventory`、`LodestoneBlockEntity`、`ItemHolderBlockEntity` |
| `team.lodestar.lodestone.systems.network.particle` | 5 | `NetworkedParticleEffectType`、`NetworkedParticleEffectPositionData`、`NetworkedParticleEffectExtraData` |
| `team.lodestar.lodestone.systems.datagen.itemsmith` | 5 | `ItemModelSmithConfiguration`、`ItemModelSmith`、`ItemModelSmithResult` |
| `team.lodestar.lodestone.systems.rendering.rendeertype` | 5 | `ShaderUniformHandler`、`RenderTypeToken`、`LodestoneRenderTypeBuilder` |
| `team.lodestar.lodestone.systems.item` | 5 | `LodestoneItemProperties`、`LodestoneFuelBlockItem`、`LodestoneFuelItem` |
| `team.lodestar.lodestone.systems.datagen.providers` | 5 | `LodestoneBlockStateProvider`、`LodestoneItemModelProvider`、`LodestoneBlockModelProvider` |
| `team.lodestar.lodestone.systems.model.geo.data` | 5 | `GeoBone`、`GeoVertex`、`GeoQuad` |
| `team.lodestar.lodestone.events.types.worldevent` | 5 | `WorldEventDiscardEvent`、`WorldEventTickEvent`、`WorldEventCreationEvent` |
| `team.lodestar.lodestone.systems.item.tools` | 5 | `LodestoneSwordItem`、`LodestoneShovelItem`、`LodestonePickaxeItem` |
| `team.lodestar.lodestone.systems.item.tools.magic` | 5 | `MagicAxeItem`、`MagicShovelItem`、`MagicHoeItem` |
| `team.lodestar.lodestone.systems.particle.world.behaviors` | 5 | `SparkParticleBehavior`、`PointyDirectionalParticleBehavior`、`DirectionalParticleBehavior` |
| `team.lodestar.lodestone.events` | 4 | `ClientGameEvents`、`ClientModEvents`、`GameEvents` |
| `team.lodestar.lodestone.systems.rendering.shader.compute` | 4 | `SystemDetails`、`ComputeProgram`、`ShaderStorageBufferObject` |
| `team.lodestar.lodestone.systems.network` | 4 | `WeaponParticleEffectType`、`LodestoneNetworkPayloadData`、`OneSidedPayloadData` |
| `team.lodestar.lodestone.systems.sound` | 4 | `CachedBlockEntitySoundInstance`、`LodestoneBlockEntitySoundInstance`、`SoundMotifSoundType` |
| `team.lodestar.lodestone.systems.model.obj.data` | 4 | `Vertex`、`FallbackPair`、`IndexedMesh` |
| `team.lodestar.lodestone.systems.particle.world.type` | 4 | `LodestoneItemCrumbsParticleType`、`LodestoneTerrainParticleType`、`LodestoneWorldParticleType` |
| `team.lodestar.lodestone.systems.model.obj.lod` | 4 | `LODStrategy`、`LODBuilder`、`LevelOfDetail` |
| `team.lodestar.lodestone.systems.rendering.trail` | 4 | `TrailPointBuilder`、`TrailPointRenderData`、`TrailPoint` |
| `team.lodestar.lodestone.mixin.common` | 3 | `BlockItemMixin`、`EntityMixin`、`LivingEntityMixin` |
| `team.lodestar.lodestone.systems.particle.builder` | 3 | `ScreenParticleBuilder`、`WorldParticleBuilder`、`AbstractParticleBuilder` |
| `team.lodestar.lodestone.helpers.block` | 3 | `BlockEntityHelper`、`BlockPosHelper`、`BlockStateHelper` |
| `team.lodestar.lodestone.systems.particle.world` | 3 | `LodestoneWorldParticle`、`LodestoneItemCrumbParticle`、`LodestoneTerrainParticle` |
| `team.lodestar.lodestone.systems.recipe` | 3 | `LodestoneInWorldRecipe`、`LodestoneRecipeType`、`LodestoneRecipeSerializer` |
| … | | 其余 39 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientGameEvents` | class | — | EventBusSubscriber | 8 | 100 | [ClientGameEvents.java](team/lodestar/lodestone/events/ClientGameEvents.java) |
| `ClientModEvents` | class | — | EventBusSubscriber | 3 | 43 | [ClientModEvents.java](team/lodestar/lodestone/events/ClientModEvents.java) |
| `GameEvents` | class | — | EventBusSubscriber | 7 | 55 | [GameEvents.java](team/lodestar/lodestone/events/GameEvents.java) |
| `ModEvents` | class | — | EventBusSubscriber | 3 | 31 | [ModEvents.java](team/lodestar/lodestone/events/ModEvents.java) |
| `PostProcessHandler` | class | — | EventBusSubscriber | 5 | 53 | [PostProcessHandler.java](team/lodestar/lodestone/systems/postprocess/PostProcessHandler.java) |
| `LodestoneNetworkPayloads` | class | — | EventBusSubscriber | 8 | 101 | [LodestoneNetworkPayloads.java](team/lodestar/lodestone/registry/common/LodestoneNetworkPayloads.java) |
| `LodestoneCommands` | class | — | EventBusSubscriber | 1 | 38 | [LodestoneCommands.java](team/lodestar/lodestone/registry/common/LodestoneCommands.java) |
| `SystemDetails` | class | — | EventBusSubscriber | 12 | 94 | [SystemDetails.java](team/lodestar/lodestone/systems/rendering/shader/compute/SystemDetails.java) |
| `LodestonePostProcessEffects` | class | — | EventBusSubscriber | 6 | 46 | [LodestonePostProcessEffects.java](team/lodestar/lodestone/registry/client/LodestonePostProcessEffects.java) |
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 30 | [DataGenerators.java](team/lodestar/lodestone/datagen/DataGenerators.java) |
| `ListActiveWorldEventsCommand` | class | — | — | 11 | 337 | [ListActiveWorldEventsCommand.java](team/lodestar/lodestone/command/ListActiveWorldEventsCommand.java) |
| `LodestoneAttributes` | class | — | EventBusSubscriber | 2 | 44 | [LodestoneAttributes.java](team/lodestar/lodestone/registry/common/LodestoneAttributes.java) |
| `LodestoneShaders` | class | — | EventBusSubscriber | 1 | 72 | [LodestoneShaders.java](team/lodestar/lodestone/registry/client/LodestoneShaders.java) |
| `LodestoneModels` | class | — | EventBusSubscriber | 3 | 33 | [LodestoneModels.java](team/lodestar/lodestone/registry/client/LodestoneModels.java) |
| `ItemEventHandler` | class | — | — | 11 | 181 | [ItemEventHandler.java](team/lodestar/lodestone/handlers/ItemEventHandler.java) |
| `UnfreezeActiveWorldEventsCommand` | class | — | — | 1 | 116 | [UnfreezeActiveWorldEventsCommand.java](team/lodestar/lodestone/command/UnfreezeActiveWorldEventsCommand.java) |
| `RemoveActiveWorldEventsCommand` | class | — | — | 2 | 103 | [RemoveActiveWorldEventsCommand.java](team/lodestar/lodestone/command/RemoveActiveWorldEventsCommand.java) |
| `FreezeActiveWorldEventsCommand` | class | — | — | 1 | 114 | [FreezeActiveWorldEventsCommand.java](team/lodestar/lodestone/command/FreezeActiveWorldEventsCommand.java) |
| `LodestoneLib` | class | — | Mod | 1 | 50 | [LodestoneLib.java](team/lodestar/lodestone/LodestoneLib.java) |
| `LodestoneBlockEntityInventory` | class | ItemStackHandler | — | 28 | 240 | [LodestoneBlockEntityInventory.java](team/lodestar/lodestone/systems/blockentity/LodestoneBlockEntityInventory.java) |
| `VFXBuilders` | class | VFXBuilders.AbstractVFXBuilder | — | 118 | 819 | [VFXBuilders.java](team/lodestar/lodestone/systems/rendering/VFXBuilders.java) |
| `BlockItemMixin` | class | — | Mixin | 0 | 44 | [BlockItemMixin.java](team/lodestar/lodestone/mixin/common/BlockItemMixin.java) |
| `LodestoneBlockProperties` | class | Properties | — | 55 | 341 | [LodestoneBlockProperties.java](team/lodestar/lodestone/systems/block/LodestoneBlockProperties.java) |
| `ScreenParticleBuilder` | class | AbstractParticleBuilder | — | 59 | 322 | [ScreenParticleBuilder.java](team/lodestar/lodestone/systems/particle/builder/ScreenParticleBuilder.java) |
| `WorldParticleBuilder` | class | AbstractParticleBuilder | — | 92 | 601 | [WorldParticleBuilder.java](team/lodestar/lodestone/systems/particle/builder/WorldParticleBuilder.java) |
| `WorldEventHandler` | class | — | — | 5 | 82 | [WorldEventHandler.java](team/lodestar/lodestone/handlers/WorldEventHandler.java) |
| `ScreenParticleHandler` | class | — | — | 11 | 154 | [ScreenParticleHandler.java](team/lodestar/lodestone/handlers/screenparticle/ScreenParticleHandler.java) |
| `LodestoneBlockEntity` | class | BlockEntity | — | 20 | 104 | [LodestoneBlockEntity.java](team/lodestar/lodestone/systems/blockentity/LodestoneBlockEntity.java) |
| `Easing` | class | Easing | — | 46 | 478 | [Easing.java](team/lodestar/lodestone/systems/easing/Easing.java) |
| `BlockEntityHelper` | class | — | — | 16 | 94 | [BlockEntityHelper.java](team/lodestar/lodestone/helpers/block/BlockEntityHelper.java) |
| `BlockPosHelper` | class | — | — | 28 | 137 | [BlockPosHelper.java](team/lodestar/lodestone/helpers/block/BlockPosHelper.java) |
| `EntityMixin` | class | — | Mixin | 0 | 36 | [EntityMixin.java](team/lodestar/lodestone/mixin/common/EntityMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 0 | 38 | [LivingEntityMixin.java](team/lodestar/lodestone/mixin/common/LivingEntityMixin.java) |
| `LevelRendererMixin` | class | — | Mixin | 0 | 81 | [LevelRendererMixin.java](team/lodestar/lodestone/mixin/client/LevelRendererMixin.java) |
| `AbstractContainerScreenMixin` | class | — | Mixin | 0 | 9 | [AbstractContainerScreenMixin.java](team/lodestar/lodestone/mixin/client/AbstractContainerScreenMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 0 | 54 | [GameRendererMixin.java](team/lodestar/lodestone/mixin/client/GameRendererMixin.java) |
| `CreativeModeInventoryScreenMixin` | class | — | Mixin | 0 | 9 | [CreativeModeInventoryScreenMixin.java](team/lodestar/lodestone/mixin/client/CreativeModeInventoryScreenMixin.java) |
| `WorldEventRenderHandler` | class | — | — | 1 | 35 | [WorldEventRenderHandler.java](team/lodestar/lodestone/handlers/WorldEventRenderHandler.java) |
| `LodestoneAttributeEventHandler` | class | — | — | 2 | 62 | [LodestoneAttributeEventHandler.java](team/lodestar/lodestone/handlers/LodestoneAttributeEventHandler.java) |
| `LodestoneRenderHandler` | class | — | — | 15 | 150 | [LodestoneRenderHandler.java](team/lodestar/lodestone/handlers/LodestoneRenderHandler.java) |
| `ThrowawayBlockDataHandler` | class | — | — | 2 | 30 | [ThrowawayBlockDataHandler.java](team/lodestar/lodestone/handlers/ThrowawayBlockDataHandler.java) |
| `WeaponParticleEffectType` | class | NetworkedParticleEffectType | — | 52 | 368 | [WeaponParticleEffectType.java](team/lodestar/lodestone/systems/network/WeaponParticleEffectType.java) |
| `NetworkedParticleEffectType` | class | — | — | 23 | 162 | [NetworkedParticleEffectType.java](team/lodestar/lodestone/systems/network/particle/NetworkedParticleEffectType.java) |
| `LodestoneBlockFiller` | class | ArrayList | — | 20 | 214 | [LodestoneBlockFiller.java](team/lodestar/lodestone/systems/worldgen/LodestoneBlockFiller.java) |
| `ItemHolderBlockEntity` | class | LodestoneBlockEntity | — | 5 | 53 | [ItemHolderBlockEntity.java](team/lodestar/lodestone/systems/blockentity/ItemHolderBlockEntity.java) |
| `IItemHandlerSupplier` | interface | — | — | 0 | 9 | [IItemHandlerSupplier.java](team/lodestar/lodestone/systems/blockentity/IItemHandlerSupplier.java) |
| `ItemModelSmithConfiguration` | class | ItemModelSmith | — | 7 | 62 | [ItemModelSmithConfiguration.java](team/lodestar/lodestone/systems/datagen/itemsmith/ItemModelSmithConfiguration.java) |
| `LodestoneWorldParticle` | class | TextureSheetParticle | — | 39 | 314 | [LodestoneWorldParticle.java](team/lodestar/lodestone/systems/particle/world/LodestoneWorldParticle.java) |
| `AbstractParticleBuilder` | class | — | — | 41 | 204 | [AbstractParticleBuilder.java](team/lodestar/lodestone/systems/particle/builder/AbstractParticleBuilder.java) |
| `ShaderUniformHandler` | class | — | — | 11 | 163 | [ShaderUniformHandler.java](team/lodestar/lodestone/systems/rendering/rendeertype/ShaderUniformHandler.java) |
| `VertexBufferMixin` | class | IVertexBuffer | Mixin | 6 | 71 | [VertexBufferMixin.java](team/lodestar/lodestone/mixin/client/VertexBufferMixin.java) |
| `ShaderInstanceMixin` | class | IShaderInstance | Mixin | 7 | 166 | [ShaderInstanceMixin.java](team/lodestar/lodestone/mixin/client/ShaderInstanceMixin.java) |
| `GetDataWorldEventCommand` | class | — | — | 1 | 23 | [GetDataWorldEventCommand.java](team/lodestar/lodestone/command/GetDataWorldEventCommand.java) |
| `LodestoneRenderTypes` | class | RenderStateShard | — | 27 | 429 | [LodestoneRenderTypes.java](team/lodestar/lodestone/registry/client/LodestoneRenderTypes.java) |
| `ScreenshakeHandler` | class | — | — | 4 | 52 | [ScreenshakeHandler.java](team/lodestar/lodestone/handlers/ScreenshakeHandler.java) |
| `LodestoneEntityBlock` | class | Block | — | 13 | 138 | [LodestoneEntityBlock.java](team/lodestar/lodestone/systems/block/LodestoneEntityBlock.java) |
| `LodestoneShaderRegistry` | class | — | — | 3 | 32 | [LodestoneShaderRegistry.java](team/lodestar/lodestone/systems/rendering/shader/LodestoneShaderRegistry.java) |
| `ComputeProgram` | class | IBufferObject | — | 25 | 224 | [ComputeProgram.java](team/lodestar/lodestone/systems/rendering/shader/compute/ComputeProgram.java) |
| `EffectInstanceMixin` | class | — | Mixin | 3 | 59 | [EffectInstanceMixin.java](team/lodestar/lodestone/mixin/client/EffectInstanceMixin.java) |
| `GuiMixin` | class | — | Mixin | 0 | 24 | [GuiMixin.java](team/lodestar/lodestone/mixin/client/GuiMixin.java) |

## 事件处理（`@SubscribeEvent`，14 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientGameEvents` | 8 | `team.lodestar.lodestone.events` | [ClientGameEvents.java](team/lodestar/lodestone/events/ClientGameEvents.java) |
| `GameEvents` | 7 | `team.lodestar.lodestone.events` | [GameEvents.java](team/lodestar/lodestone/events/GameEvents.java) |
| `ModEvents` | 3 | `team.lodestar.lodestone.events` | [ModEvents.java](team/lodestar/lodestone/events/ModEvents.java) |
| `ClientModEvents` | 2 | `team.lodestar.lodestone.events` | [ClientModEvents.java](team/lodestar/lodestone/events/ClientModEvents.java) |
| `PostProcessHandler` | 1 | `team.lodestar.lodestone.systems.postprocess` | [PostProcessHandler.java](team/lodestar/lodestone/systems/postprocess/PostProcessHandler.java) |
| `LodestoneNetworkPayloads` | 1 | `team.lodestar.lodestone.registry.common` | [LodestoneNetworkPayloads.java](team/lodestar/lodestone/registry/common/LodestoneNetworkPayloads.java) |
| `LodestoneCommands` | 1 | `team.lodestar.lodestone.registry.common` | [LodestoneCommands.java](team/lodestar/lodestone/registry/common/LodestoneCommands.java) |
| `SystemDetails` | 1 | `team.lodestar.lodestone.systems.rendering.shader.compute` | [SystemDetails.java](team/lodestar/lodestone/systems/rendering/shader/compute/SystemDetails.java) |
| `LodestonePostProcessEffects` | 1 | `team.lodestar.lodestone.registry.client` | [LodestonePostProcessEffects.java](team/lodestar/lodestone/registry/client/LodestonePostProcessEffects.java) |
| `DataGenerators` | 1 | `team.lodestar.lodestone.datagen` | [DataGenerators.java](team/lodestar/lodestone/datagen/DataGenerators.java) |
| `LodestoneAttributes` | 1 | `team.lodestar.lodestone.registry.common` | [LodestoneAttributes.java](team/lodestar/lodestone/registry/common/LodestoneAttributes.java) |
| `LodestoneShaders` | 1 | `team.lodestar.lodestone.registry.client` | [LodestoneShaders.java](team/lodestar/lodestone/registry/client/LodestoneShaders.java) |
| `LodestoneModels` | 1 | `team.lodestar.lodestone.registry.client` | [LodestoneModels.java](team/lodestar/lodestone/registry/client/LodestoneModels.java) |
| `LodestoneBlockEntities` | 1 | `team.lodestar.lodestone.registry.common` | [LodestoneBlockEntities.java](team/lodestar/lodestone/registry/common/LodestoneBlockEntities.java) |

## Mixin（15 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockItemMixin` | `BlockItem` | [BlockItemMixin.java](team/lodestar/lodestone/mixin/common/BlockItemMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](team/lodestar/lodestone/mixin/common/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](team/lodestar/lodestone/mixin/common/LivingEntityMixin.java) |
| `LevelRendererMixin` | `LevelRenderer` | [LevelRendererMixin.java](team/lodestar/lodestone/mixin/client/LevelRendererMixin.java) |
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](team/lodestar/lodestone/mixin/client/AbstractContainerScreenMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](team/lodestar/lodestone/mixin/client/GameRendererMixin.java) |
| `CreativeModeInventoryScreenMixin` | `CreativeModeInventoryScreen` | [CreativeModeInventoryScreenMixin.java](team/lodestar/lodestone/mixin/client/CreativeModeInventoryScreenMixin.java) |
| `VertexBufferMixin` | `VertexBuffer` | [VertexBufferMixin.java](team/lodestar/lodestone/mixin/client/VertexBufferMixin.java) |
| `ShaderInstanceMixin` | `ShaderInstance` | [ShaderInstanceMixin.java](team/lodestar/lodestone/mixin/client/ShaderInstanceMixin.java) |
| `EffectInstanceMixin` | `EffectInstance` | [EffectInstanceMixin.java](team/lodestar/lodestone/mixin/client/EffectInstanceMixin.java) |
| `GuiMixin` | `Gui` | [GuiMixin.java](team/lodestar/lodestone/mixin/client/GuiMixin.java) |
| `GuiGraphicsMixin` | `GuiGraphics` | [GuiGraphicsMixin.java](team/lodestar/lodestone/mixin/client/GuiGraphicsMixin.java) |
| `PostChainMixin` | `PostChain` | [PostChainMixin.java](team/lodestar/lodestone/mixin/client/PostChainMixin.java) |
| `MultiPlayerGameModeMixin` | `MultiPlayerGameMode` | [MultiPlayerGameModeMixin.java](team/lodestar/lodestone/mixin/client/MultiPlayerGameModeMixin.java) |
| `EffectProgramMixin` | `EffectProgram` | [EffectProgramMixin.java](team/lodestar/lodestone/mixin/client/EffectProgramMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ItemModelSmithConfiguration` | — | [ItemModelSmithConfiguration.java](team/lodestar/lodestone/systems/datagen/itemsmith/ItemModelSmithConfiguration.java) |
| `ClientConfig` | — | [ClientConfig.java](team/lodestar/lodestone/config/ClientConfig.java) |
| `LodestoneConfig` | — | [LodestoneConfig.java](team/lodestar/lodestone/systems/config/LodestoneConfig.java) |

## 按包浏览

- `team.lodestar.lodestone`（1 类）
- `team.lodestar.lodestone.attachment`（1 类）
- `team.lodestar.lodestone.command`（6 类）
- `team.lodestar.lodestone.command.arguments`（2 类）
- `team.lodestar.lodestone.compability`（1 类）
- `team.lodestar.lodestone.config`（1 类）
- `team.lodestar.lodestone.datagen`（5 类）
- `team.lodestar.lodestone.events`（4 类）
- `team.lodestar.lodestone.events.types.worldevent`（5 类）
- `team.lodestar.lodestone.handlers`（7 类）
- `team.lodestar.lodestone.handlers.screenparticle`（2 类）
- `team.lodestar.lodestone.helpers`（16 类）
- `team.lodestar.lodestone.helpers.block`（3 类）
- `team.lodestar.lodestone.mixin.client`（12 类）
- `team.lodestar.lodestone.mixin.common`（3 类）
- `team.lodestar.lodestone.network`（2 类）
- `team.lodestar.lodestone.network.worldevent`（2 类）
- `team.lodestar.lodestone.recipe`（1 类）
- `team.lodestar.lodestone.recipe.builder`（2 类）
- `team.lodestar.lodestone.registry.client`（6 类）
- `team.lodestar.lodestone.registry.common`（10 类）
- `team.lodestar.lodestone.registry.common.particle`（2 类）
- `team.lodestar.lodestone.registry.common.tag`（3 类）
- `team.lodestar.lodestone.systems.attribute`（3 类）
- `team.lodestar.lodestone.systems.block`（8 类）
- `team.lodestar.lodestone.systems.block.sign`（2 类）
- `team.lodestar.lodestone.systems.blockentity`（5 类）
- `team.lodestar.lodestone.systems.config`（1 类）
- `team.lodestar.lodestone.systems.datagen`（3 类）
- `team.lodestar.lodestone.systems.datagen.itemsmith`（5 类）
- `team.lodestar.lodestone.systems.datagen.providers`（5 类）
- `team.lodestar.lodestone.systems.datagen.statesmith`（3 类）
- `team.lodestar.lodestone.systems.easing`（1 类）
- `team.lodestar.lodestone.systems.enchanting`（9 类）
- `team.lodestar.lodestone.systems.item`（5 类）
- `team.lodestar.lodestone.systems.item.tools`（5 类）
- `team.lodestar.lodestone.systems.item.tools.magic`（5 类）
- `team.lodestar.lodestone.systems.model`（3 类）
- `team.lodestar.lodestone.systems.model.geo`（2 类）
- `team.lodestar.lodestone.systems.model.geo.data`（5 类）
- `team.lodestar.lodestone.systems.model.obj`（3 类）
- `team.lodestar.lodestone.systems.model.obj.data`（4 类）
- `team.lodestar.lodestone.systems.model.obj.lod`（4 类）
- `team.lodestar.lodestone.systems.model.obj.modifier`（2 类）
- `team.lodestar.lodestone.systems.model.obj.modifier.modifiers`（3 类）
- `team.lodestar.lodestone.systems.multiblock`（9 类）
- `team.lodestar.lodestone.systems.network`（4 类）
- `team.lodestar.lodestone.systems.network.particle`（5 类）
- `team.lodestar.lodestone.systems.particle`（2 类）
- `team.lodestar.lodestone.systems.particle.builder`（3 类）
- `team.lodestar.lodestone.systems.particle.data`（3 类）
- `team.lodestar.lodestone.systems.particle.data.color`（3 类）
- `team.lodestar.lodestone.systems.particle.data.spin`（3 类）
- `team.lodestar.lodestone.systems.particle.render_types`（2 类）
- `team.lodestar.lodestone.systems.particle.screen`（7 类）
- `team.lodestar.lodestone.systems.particle.screen.base`（3 类）
- `team.lodestar.lodestone.systems.particle.world`（3 类）
- `team.lodestar.lodestone.systems.particle.world.behaviors`（5 类）
- `team.lodestar.lodestone.systems.particle.world.options`（3 类）
- `team.lodestar.lodestone.systems.particle.world.type`（4 类）
- `team.lodestar.lodestone.systems.postprocess`（6 类）
- `team.lodestar.lodestone.systems.postprocess.effects`（2 类）
- `team.lodestar.lodestone.systems.recipe`（3 类）
- `team.lodestar.lodestone.systems.rendering`（7 类）
- `team.lodestar.lodestone.systems.rendering.buffer`（2 类）
- `team.lodestar.lodestone.systems.rendering.cube`（1 类）
- `team.lodestar.lodestone.systems.rendering.rendeertype`（5 类）
- `team.lodestar.lodestone.systems.rendering.renderpass`（2 类）
- `team.lodestar.lodestone.systems.rendering.shader`（7 类）
- `team.lodestar.lodestone.systems.rendering.shader.compute`（4 类）
- `team.lodestar.lodestone.systems.rendering.trail`（4 类）
- `team.lodestar.lodestone.systems.rendering.vertexconsumer`（3 类）
- `team.lodestar.lodestone.systems.rendering.vertexconsumer.offset`（2 类）
- `team.lodestar.lodestone.systems.screenshake`（2 类）
- `team.lodestar.lodestone.systems.sound`（4 类）
- `team.lodestar.lodestone.systems.texture`（6 类）
- `team.lodestar.lodestone.systems.worldevent`（3 类）
- `team.lodestar.lodestone.systems.worldgen`（6 类）
- `team.lodestar.lodestone.systems.worldgen.filter`（2 类）
