# CreativeCore（`creativecore`）

> jar：`[前置]CreativeCore.jar` ｜ 版本：`2.13.11` ｜ 作者：CreativeMD
> 规模：**411 个类 / 80 个包 / 48409 行** ｜ 事件处理类 3 ｜ Mixin 10 ｜ 注册/管理类 30

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `team.creative.creativecore.common.gui.control.simple` | 27 | `GuiTextfield`、`GuiSlider`、`GuiLabel` |
| `team.creative.creativecore.common.util.type.itr` | 18 | `ConsecutiveListIterator`、`FilterListIterator`、`ComputeNextListIterator` |
| `team.creative.creativecore.common.config.gui` | 13 | `GuiPlayerSelectorHandler`、`ConfigGuiLayer`、`GuiConfigControl` |
| `team.creative.creativecore.common.gui.control.parent` | 13 | `GuiScrollY`、`GuiScrollX`、`GuiTableScrollable` |
| `team.creative.creativecore.common.util.type.list` | 12 | `CopyArrayCollection`、`PairList`、`SortingList` |
| `team.creative.creativecore.common.util.math.vec` | 12 | `Vec3d`、`Vec3f`、`VectorUtils` |
| `team.creative.creativecore.common.util.mc` | 11 | `ColorUtils`、`JsonUtils`、`TooltipUtils` |
| `team.creative.creativecore.mixin` | 10 | `MouseHandlerAccessor`、`ForgeModelBlockRendererAccessor`、`ShapesMixin` |
| `team.creative.creativecore.client.render.model` | 10 | `CreativeItemBoxModel`、`CreativeBlockModel`、`CreativeItemModel` |
| `team.creative.creativecore.common.util.math.box` | 9 | `ABB`、`BoxesVoxelShape`、`AlignedBox` |
| `team.creative.creativecore.common.util.type.map` | 9 | `HashMapList`、`ChunkLayerMap`、`ChunkLayerMapList` |
| `team.creative.creativecore.common.util.ingredient` | 8 | `GuiCreativeIngredientHandler`、`CreativeIngredient`、`CreativeIngredientItemStack` |
| `team.creative.creativecore.common.gui` | 8 | `GuiParent`、`GuiControl`、`GuiLayer` |
| `team.creative.creativecore.common.gui.event` | 8 | `GuiEventManager`、`GuiControlClickEvent`、`GuiTooltipEvent` |
| `team.creative.creativecore.common.network` | 8 | `CreativeNetworkPacket`、`CreativeNetwork`、`CreativePacket` |
| `team.creative.creativecore.common.gui.control.collection` | 8 | `GuiStackSelector`、`GuiComboBox`、`GuiListBoxBase` |
| `team.creative.creativecore.common.config.premade` | 8 | `SelectableConfig`、`MobEffectConfig`、`SoundConfig` |
| `team.creative.creativecore.common.config.converation` | 7 | `ConfigTypeConveration`、`ConfigTypeNumber`、`ConfigTypeNamedList` |
| `team.creative.creativecore.common.util.math.geo` | 7 | `VectorFan`、`Ray2d`、`Rect` |
| `team.creative.creativecore.common.util.math.matrix` | 7 | `Matrix4`、`Matrix3`、`VecOrigin` |
| `team.creative.creativecore.common.gui.control.inventory` | 7 | `GuiInventoryGrid`、`GuiSlotBase`、`GuiSlot` |
| `team.creative.creativecore.common.gui.sync` | 7 | `GuiSyncHolder`、`GuiSyncLocalLayer`、`GuiSyncLocal` |
| `team.creative.creativecore.common.util.registry` | 6 | `NamedClassBoundHandlerRegistry`、`NamedHandlerRegistry`、`ConfigTypeRegistry` |
| `team.creative.creativecore.common.config.converation.registry` | 6 | `GuiRegistryTagHandler`、`GuiRegistryObjectHandler`、`ConfigTypeRegistryObjectList` |
| `team.creative.creativecore.common.gui.integration` | 6 | `GuiEventHandler`、`ScreenEventListener`、`GuiScreenIntegration` |
| `team.creative.creativecore` | 6 | `CreativeCore`、`CreativeCoreGuiRegistry`、`CreativeCoreConfig` |
| `team.creative.creativecore.common.gui.packet` | 6 | `ImmediateItemStackPacket`、`LayerClosePacket`、`ControlSyncPacket` |
| `team.creative.creativecore.common.util.math.collision` | 5 | `IntersectionHelper`、`CollisionCoordinator`、`MatrixUtils` |
| `team.creative.creativecore.common.config.holder` | 5 | `CreativeConfigRegistry`、`ConfigHolder`、`ConfigHolderDynamic` |
| `team.creative.creativecore.common.gui.control.timeline` | 5 | `GuiTimeline`、`GuiAnimationHandler`、`GuiTimelineChannel` |
| `team.creative.creativecore.common.gui.flow` | 5 | `GuiSizeRule`、`GuiFlowFitX`、`GuiStackX` |
| `team.creative.creativecore.common.util.type` | 5 | `Color`、`ColorType`、`TriPredicate` |
| `team.creative.creativecore.common.level` | 5 | `LevelAccessorFake`、`BlockGetterFake`、`NeighborUpdateCollector` |
| `team.creative.creativecore.common.config.api` | 5 | `CreativeConfig`、`ICreativeConfig`、`IConfigObject` |
| `team.creative.creativecore.common.util.text` | 5 | `TextMapBuilder`、`AdvancedComponentHelper`、`TextBuilder` |
| `team.creative.creativecore.common.util.text.content` | 5 | `ContentItemStack`、`FormattedSingleSink`、`AdvancedContentConsumer` |
| `team.creative.creativecore.common.gui.style.display` | 5 | `StyleDisplay`、`DisplayColor`、`DisplayTextureStretch` |
| `team.creative.creativecore.common.util.math.interpolation` | 5 | `Interpolation`、`CosineInterpolation`、`CubicInterpolation` |
| `team.creative.creativecore.common.network.type` | 4 | `NetworkFieldTypes`、`NetworkFieldType`、`NetworkFieldTypeClass` |
| `team.creative.creativecore.common.config.premade.registry` | 4 | `RegistryObjectListConfig`、`RegistryTagListConfig`、`RegistryTagConfig` |
| … | | 其余 40 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigEventHandler` | class | — | — | 25 | 325 | [ConfigEventHandler.java](team/creative/creativecore/common/config/event/ConfigEventHandler.java) |
| `NamedClassBoundHandlerRegistry` | class | — | — | 17 | 104 | [NamedClassBoundHandlerRegistry.java](team/creative/creativecore/common/util/registry/NamedClassBoundHandlerRegistry.java) |
| `ConfigTypeConveration` | class | ConfigTypeConveration | — | 90 | 723 | [ConfigTypeConveration.java](team/creative/creativecore/common/config/converation/ConfigTypeConveration.java) |
| `GuiRegistryTagHandler` | class | — | Environment、OnlyIn | 11 | 138 | [GuiRegistryTagHandler.java](team/creative/creativecore/common/config/converation/registry/GuiRegistryTagHandler.java) |
| `NamedHandlerRegistry` | class | — | — | 12 | 81 | [NamedHandlerRegistry.java](team/creative/creativecore/common/util/registry/NamedHandlerRegistry.java) |
| `ConfigTypeRegistry` | class | ICreativeRegistry | — | 16 | 123 | [ConfigTypeRegistry.java](team/creative/creativecore/common/util/registry/ConfigTypeRegistry.java) |
| `LocatedHandlerRegistry` | class | — | — | 11 | 76 | [LocatedHandlerRegistry.java](team/creative/creativecore/common/util/registry/LocatedHandlerRegistry.java) |
| `NetworkFieldTypes` | class | — | — | 95 | 706 | [NetworkFieldTypes.java](team/creative/creativecore/common/network/type/NetworkFieldTypes.java) |
| `GuiEventHandler` | class | — | — | 2 | 73 | [GuiEventHandler.java](team/creative/creativecore/common/gui/integration/GuiEventHandler.java) |
| `GuiRegistryObjectHandler` | class | — | Environment、OnlyIn | 9 | 81 | [GuiRegistryObjectHandler.java](team/creative/creativecore/common/config/converation/registry/GuiRegistryObjectHandler.java) |
| `FilteredHandlerRegistry` | class | — | — | 6 | 61 | [FilteredHandlerRegistry.java](team/creative/creativecore/common/util/registry/FilteredHandlerRegistry.java) |
| `IntersectionHelper` | class | — | — | 84 | 1072 | [IntersectionHelper.java](team/creative/creativecore/common/util/math/collision/IntersectionHelper.java) |
| `CreativeCore` | class | — | Mod | 7 | 129 | [CreativeCore.java](team/creative/creativecore/CreativeCore.java) |
| `MouseHandlerAccessor` | interface | — | Mixin | 0 | 12 | [MouseHandlerAccessor.java](team/creative/creativecore/mixin/MouseHandlerAccessor.java) |
| `GuiManagerItem` | class | GuiManager | — | 18 | 221 | [GuiManagerItem.java](team/creative/creativecore/common/gui/manager/GuiManagerItem.java) |
| `CreativeConfigRegistry` | class | ConfigHolderDynamic | — | 9 | 89 | [CreativeConfigRegistry.java](team/creative/creativecore/common/config/holder/CreativeConfigRegistry.java) |
| `RegistryObjectListConfig` | class | Iterable<T> | — | 9 | 78 | [RegistryObjectListConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryObjectListConfig.java) |
| `ConfigTypeRegistryObjectList` | class | ConfigTypeConveration | — | 7 | 132 | [ConfigTypeRegistryObjectList.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryObjectList.java) |
| `ConfigTypeRegistryTagList` | class | ConfigTypeConveration | — | 7 | 133 | [ConfigTypeRegistryTagList.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryTagList.java) |
| `ConfigTypeRegistryObject` | class | ConfigTypeConveration | — | 6 | 58 | [ConfigTypeRegistryObject.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryObject.java) |
| `ConfigTypeRegistryTag` | class | ConfigTypeConveration | — | 6 | 57 | [ConfigTypeRegistryTag.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryTag.java) |
| `VectorFan` | class | Exception | — | 50 | 1139 | [VectorFan.java](team/creative/creativecore/common/util/math/geo/VectorFan.java) |
| `Matrix4` | class | — | — | 100 | 2018 | [Matrix4.java](team/creative/creativecore/common/util/math/matrix/Matrix4.java) |
| `ForgeModelBlockRendererAccessor` | interface | — | Mixin | 0 | 16 | [ForgeModelBlockRendererAccessor.java](team/creative/creativecore/mixin/ForgeModelBlockRendererAccessor.java) |
| `RegistryTagListConfig` | class | Iterable<TagKey<T>> | — | 4 | 36 | [RegistryTagListConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryTagListConfig.java) |
| `RegistryTagConfig` | class | — | — | 1 | 21 | [RegistryTagConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryTagConfig.java) |
| `RegistryObjectConfig` | class | ICreativeConfig | — | 3 | 35 | [RegistryObjectConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryObjectConfig.java) |
| `GuiCreativeIngredientHandler` | class | GuiCheckList | Environment、OnlyIn | 22 | 299 | [GuiCreativeIngredientHandler.java](team/creative/creativecore/common/util/ingredient/GuiCreativeIngredientHandler.java) |
| `NamedTypeRegistry` | class | RegistryException | — | 18 | 236 | [NamedTypeRegistry.java](team/creative/creativecore/common/util/registry/NamedTypeRegistry.java) |
| `Matrix3` | class | — | — | 28 | 1500 | [Matrix3.java](team/creative/creativecore/common/util/math/matrix/Matrix3.java) |
| `GuiParent` | class | GuiControl | — | 73 | 721 | [GuiParent.java](team/creative/creativecore/common/gui/GuiParent.java) |
| `GuiTreeItem` | class | GuiParent | — | 42 | 349 | [GuiTreeItem.java](team/creative/creativecore/common/gui/control/tree/GuiTreeItem.java) |
| `ConfigKey` | class | — | — | 28 | 161 | [ConfigKey.java](team/creative/creativecore/common/config/key/ConfigKey.java) |
| `ConfigurationClientPacket` | class | CreativePacket | — | 4 | 77 | [ConfigurationClientPacket.java](team/creative/creativecore/common/config/sync/ConfigurationClientPacket.java) |
| `ConfigTypeNumber` | class | — | — | 21 | 346 | [ConfigTypeNumber.java](team/creative/creativecore/common/config/converation/ConfigTypeNumber.java) |
| `RenderBox` | class | AlignedBox | OnlyIn | 34 | 656 | [RenderBox.java](team/creative/creativecore/client/render/box/RenderBox.java) |
| `ShapesMixin` | class | — | Mixin | 6 | 251 | [ShapesMixin.java](team/creative/creativecore/mixin/ShapesMixin.java) |
| `GuiControl` | class | — | — | 79 | 465 | [GuiControl.java](team/creative/creativecore/common/gui/GuiControl.java) |
| `GuiTree` | class | GuiScrollXY | — | 32 | 493 | [GuiTree.java](team/creative/creativecore/common/gui/control/tree/GuiTree.java) |
| `GuiTextfield` | class | GuiFocusControl | — | 46 | 574 | [GuiTextfield.java](team/creative/creativecore/common/gui/control/simple/GuiTextfield.java) |
| `GuiEventManager` | class | — | — | 4 | 43 | [GuiEventManager.java](team/creative/creativecore/common/gui/event/GuiEventManager.java) |
| `ScreenEventListener` | class | GuiEventListener | — | 18 | 136 | [ScreenEventListener.java](team/creative/creativecore/common/gui/integration/ScreenEventListener.java) |
| `GuiScreenIntegration` | class | Screen | — | 22 | 166 | [GuiScreenIntegration.java](team/creative/creativecore/common/gui/integration/GuiScreenIntegration.java) |
| `GuiPlayerSelectorHandler` | class | GuiPlayerSelectorHandler | Environment、OnlyIn | 19 | 159 | [GuiPlayerSelectorHandler.java](team/creative/creativecore/common/config/gui/GuiPlayerSelectorHandler.java) |
| `CopyArrayCollection` | class | Collection<E> | — | 35 | 494 | [CopyArrayCollection.java](team/creative/creativecore/common/util/type/list/CopyArrayCollection.java) |
| `Axis` | enum | — | — | 101 | 469 | [Axis.java](team/creative/creativecore/common/util/math/base/Axis.java) |
| `Facing` | enum | — | — | 75 | 455 | [Facing.java](team/creative/creativecore/common/util/math/base/Facing.java) |
| `ABB` | class | — | — | 48 | 505 | [ABB.java](team/creative/creativecore/common/util/math/box/ABB.java) |
| `CreativeCoreClient` | class | — | — | 14 | 127 | [CreativeCoreClient.java](team/creative/creativecore/client/CreativeCoreClient.java) |
| `CompiledText` | class | — | — | 44 | 565 | [CompiledText.java](team/creative/creativecore/client/render/text/CompiledText.java) |
| `GuiLayer` | class | GuiParent | — | 30 | 311 | [GuiLayer.java](team/creative/creativecore/common/gui/GuiLayer.java) |
| `GuiControlRect` | class | — | — | 40 | 258 | [GuiControlRect.java](team/creative/creativecore/common/gui/GuiControlRect.java) |
| `GuiTimeline` | class | GuiParent | — | 30 | 372 | [GuiTimeline.java](team/creative/creativecore/common/gui/control/timeline/GuiTimeline.java) |
| `GuiSlider` | class | GuiControl | — | 36 | 314 | [GuiSlider.java](team/creative/creativecore/common/gui/control/simple/GuiSlider.java) |
| `GuiSizeRule` | class | GuiSizeRule | — | 42 | 248 | [GuiSizeRule.java](team/creative/creativecore/common/gui/flow/GuiSizeRule.java) |
| `ConfigKeyHolder` | class | ConfigKey | — | 15 | 119 | [ConfigKeyHolder.java](team/creative/creativecore/common/config/key/ConfigKeyHolder.java) |
| `ConfigKeyType` | class | ConfigKey | — | 17 | 140 | [ConfigKeyType.java](team/creative/creativecore/common/config/key/ConfigKeyType.java) |
| `ConfigurationPacket` | class | CreativePacket | — | 3 | 63 | [ConfigurationPacket.java](team/creative/creativecore/common/config/sync/ConfigurationPacket.java) |
| `ConfigurationChangePacket` | class | CreativePacket | — | 2 | 39 | [ConfigurationChangePacket.java](team/creative/creativecore/common/config/sync/ConfigurationChangePacket.java) |
| `PlayerSelector` | class | PlayerSelector | — | 36 | 365 | [PlayerSelector.java](team/creative/creativecore/common/util/player/PlayerSelector.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ConfigEventHandler` | 3 | `team.creative.creativecore.common.config.event` | [ConfigEventHandler.java](team/creative/creativecore/common/config/event/ConfigEventHandler.java) |
| `CreativeCoreClient` | 2 | `team.creative.creativecore.client` | [CreativeCoreClient.java](team/creative/creativecore/client/CreativeCoreClient.java) |
| `GuiEventHandler` | 1 | `team.creative.creativecore.common.gui.integration` | [GuiEventHandler.java](team/creative/creativecore/common/gui/integration/GuiEventHandler.java) |

## Mixin（10 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MouseHandlerAccessor` | `MouseHandlerAccessor` | [MouseHandlerAccessor.java](team/creative/creativecore/mixin/MouseHandlerAccessor.java) |
| `ForgeModelBlockRendererAccessor` | `ForgeModelBlockRendererAccessor` | [ForgeModelBlockRendererAccessor.java](team/creative/creativecore/mixin/ForgeModelBlockRendererAccessor.java) |
| `ShapesMixin` | `Shapes` | [ShapesMixin.java](team/creative/creativecore/mixin/ShapesMixin.java) |
| `QuadLighterMixin` | `QuadLighter` | [QuadLighterMixin.java](team/creative/creativecore/mixin/QuadLighterMixin.java) |
| `SharedZipFileAccessAccessor` | `SharedZipFileAccessAccessor` | [SharedZipFileAccessAccessor.java](team/creative/creativecore/mixin/SharedZipFileAccessAccessor.java) |
| `PathPackResourcesAccessor` | `PathPackResourcesAccessor` | [PathPackResourcesAccessor.java](team/creative/creativecore/mixin/PathPackResourcesAccessor.java) |
| `StringSplitterAccessor` | `StringSplitterAccessor` | [StringSplitterAccessor.java](team/creative/creativecore/mixin/StringSplitterAccessor.java) |
| `FilePackResourcesAccessor` | `FilePackResourcesAccessor` | [FilePackResourcesAccessor.java](team/creative/creativecore/mixin/FilePackResourcesAccessor.java) |
| `VanillaPackResourcesAccessor` | `VanillaPackResourcesAccessor` | [VanillaPackResourcesAccessor.java](team/creative/creativecore/mixin/VanillaPackResourcesAccessor.java) |
| `VoxelShapeAccessor` | `VoxelShapeAccessor` | [VoxelShapeAccessor.java](team/creative/creativecore/mixin/VoxelShapeAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigEventHandler` | — | [ConfigEventHandler.java](team/creative/creativecore/common/config/event/ConfigEventHandler.java) |
| `ConfigTypeConveration` | — | [ConfigTypeConveration.java](team/creative/creativecore/common/config/converation/ConfigTypeConveration.java) |
| `ConfigTypeRegistry` | — | [ConfigTypeRegistry.java](team/creative/creativecore/common/util/registry/ConfigTypeRegistry.java) |
| `CreativeConfigRegistry` | — | [CreativeConfigRegistry.java](team/creative/creativecore/common/config/holder/CreativeConfigRegistry.java) |
| `RegistryObjectListConfig` | — | [RegistryObjectListConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryObjectListConfig.java) |
| `ConfigTypeRegistryObjectList` | — | [ConfigTypeRegistryObjectList.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryObjectList.java) |
| `ConfigTypeRegistryTagList` | — | [ConfigTypeRegistryTagList.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryTagList.java) |
| `ConfigTypeRegistryObject` | — | [ConfigTypeRegistryObject.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryObject.java) |
| `ConfigTypeRegistryTag` | — | [ConfigTypeRegistryTag.java](team/creative/creativecore/common/config/converation/registry/ConfigTypeRegistryTag.java) |
| `RegistryTagListConfig` | — | [RegistryTagListConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryTagListConfig.java) |
| `RegistryTagConfig` | — | [RegistryTagConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryTagConfig.java) |
| `RegistryObjectConfig` | — | [RegistryObjectConfig.java](team/creative/creativecore/common/config/premade/registry/RegistryObjectConfig.java) |
| `ConfigKey` | — | [ConfigKey.java](team/creative/creativecore/common/config/key/ConfigKey.java) |
| `ConfigurationClientPacket` | — | [ConfigurationClientPacket.java](team/creative/creativecore/common/config/sync/ConfigurationClientPacket.java) |
| `ConfigTypeNumber` | — | [ConfigTypeNumber.java](team/creative/creativecore/common/config/converation/ConfigTypeNumber.java) |
| `ConfigKeyHolder` | — | [ConfigKeyHolder.java](team/creative/creativecore/common/config/key/ConfigKeyHolder.java) |
| `ConfigKeyType` | — | [ConfigKeyType.java](team/creative/creativecore/common/config/key/ConfigKeyType.java) |
| `ConfigurationPacket` | — | [ConfigurationPacket.java](team/creative/creativecore/common/config/sync/ConfigurationPacket.java) |
| `ConfigurationChangePacket` | — | [ConfigurationChangePacket.java](team/creative/creativecore/common/config/sync/ConfigurationChangePacket.java) |
| `ConfigHolder` | — | [ConfigHolder.java](team/creative/creativecore/common/config/holder/ConfigHolder.java) |
| `ConfigTypeNamedList` | — | [ConfigTypeNamedList.java](team/creative/creativecore/common/config/converation/ConfigTypeNamedList.java) |
| `ConfigGuiLayer` | — | [ConfigGuiLayer.java](team/creative/creativecore/common/config/gui/ConfigGuiLayer.java) |
| `GuiConfigControl` | — | [GuiConfigControl.java](team/creative/creativecore/common/config/gui/GuiConfigControl.java) |
| `GuiConfigSubControlNested` | — | [GuiConfigSubControlNested.java](team/creative/creativecore/common/config/gui/GuiConfigSubControlNested.java) |
| `ConfigHolderDynamic` | — | [ConfigHolderDynamic.java](team/creative/creativecore/common/config/holder/ConfigHolderDynamic.java) |
| `ConfigHolderObject` | — | [ConfigHolderObject.java](team/creative/creativecore/common/config/holder/ConfigHolderObject.java) |
| `SelectableConfig` | — | [SelectableConfig.java](team/creative/creativecore/common/config/premade/SelectableConfig.java) |
| `ConfigSynchronization` | — | [ConfigSynchronization.java](team/creative/creativecore/common/config/sync/ConfigSynchronization.java) |
| `ConfigTypeList` | — | [ConfigTypeList.java](team/creative/creativecore/common/config/converation/ConfigTypeList.java) |
| `ConfigTypeToggleable` | — | [ConfigTypeToggleable.java](team/creative/creativecore/common/config/converation/ConfigTypeToggleable.java) |

## 按包浏览

- `team.creative.creativecore`（6 类）
- `team.creative.creativecore.client`（2 类）
- `team.creative.creativecore.client.render`（3 类）
- `team.creative.creativecore.client.render.box`（2 类）
- `team.creative.creativecore.client.render.face`（2 类）
- `team.creative.creativecore.client.render.model`（10 类）
- `team.creative.creativecore.client.render.text`（4 类）
- `team.creative.creativecore.client.sound`（2 类）
- `team.creative.creativecore.common`（1 类）
- `team.creative.creativecore.common.be`（1 类）
- `team.creative.creativecore.common.config.api`（5 类）
- `team.creative.creativecore.common.config.converation`（7 类）
- `team.creative.creativecore.common.config.converation.registry`（6 类）
- `team.creative.creativecore.common.config.core`（2 类）
- `team.creative.creativecore.common.config.event`（1 类）
- `team.creative.creativecore.common.config.field`（4 类）
- `team.creative.creativecore.common.config.group`（1 类）
- `team.creative.creativecore.common.config.gui`（13 类）
- `team.creative.creativecore.common.config.holder`（5 类）
- `team.creative.creativecore.common.config.key`（3 类）
- `team.creative.creativecore.common.config.premade`（8 类）
- `team.creative.creativecore.common.config.premade.curve`（4 类）
- `team.creative.creativecore.common.config.premade.registry`（4 类）
- `team.creative.creativecore.common.config.sync`（4 类）
- `team.creative.creativecore.common.gui`（8 类）
- `team.creative.creativecore.common.gui.control`（1 类）
- `team.creative.creativecore.common.gui.control.collection`（8 类）
- `team.creative.creativecore.common.gui.control.inventory`（7 类）
- `team.creative.creativecore.common.gui.control.menu`（3 类）
- `team.creative.creativecore.common.gui.control.parent`（13 类）
- `team.creative.creativecore.common.gui.control.simple`（27 类）
- `team.creative.creativecore.common.gui.control.timeline`（5 类）
- `team.creative.creativecore.common.gui.control.tree`（3 类）
- `team.creative.creativecore.common.gui.creator`（3 类）
- `team.creative.creativecore.common.gui.dialog`（2 类）
- `team.creative.creativecore.common.gui.event`（8 类）
- `team.creative.creativecore.common.gui.extension`（1 类）
- `team.creative.creativecore.common.gui.flow`（5 类）
- `team.creative.creativecore.common.gui.integration`（6 类）
- `team.creative.creativecore.common.gui.manager`（2 类）
- `team.creative.creativecore.common.gui.packet`（6 类）
- `team.creative.creativecore.common.gui.parser`（4 类）
- `team.creative.creativecore.common.gui.style`（4 类）
- `team.creative.creativecore.common.gui.style.display`（5 类）
- `team.creative.creativecore.common.gui.sync`（7 类）
- `team.creative.creativecore.common.level`（5 类）
- `team.creative.creativecore.common.loader`（3 类）
- `team.creative.creativecore.common.network`（8 类）
- `team.creative.creativecore.common.network.type`（4 类）
- `team.creative.creativecore.common.util`（1 类）
- `team.creative.creativecore.common.util.argument`（1 类）
- `team.creative.creativecore.common.util.filter`（4 类）
- `team.creative.creativecore.common.util.filter.premade`（2 类）
- `team.creative.creativecore.common.util.ingredient`（8 类）
- `team.creative.creativecore.common.util.inventory`（2 类）
- `team.creative.creativecore.common.util.math`（2 类）
- `team.creative.creativecore.common.util.math.base`（2 类）
- `team.creative.creativecore.common.util.math.box`（9 类）
- `team.creative.creativecore.common.util.math.collision`（5 类）
- `team.creative.creativecore.common.util.math.geo`（7 类）
- `team.creative.creativecore.common.util.math.interpolation`（5 类）
- `team.creative.creativecore.common.util.math.matrix`（7 类）
- `team.creative.creativecore.common.util.math.transformation`（3 类）
- `team.creative.creativecore.common.util.math.utils`（2 类）
- `team.creative.creativecore.common.util.math.vec`（12 类）
- `team.creative.creativecore.common.util.mc`（11 类）
- `team.creative.creativecore.common.util.player`（1 类）
- `team.creative.creativecore.common.util.registry`（6 类）
- `team.creative.creativecore.common.util.registry.exception`（2 类）
- `team.creative.creativecore.common.util.text`（5 类）
- `team.creative.creativecore.common.util.text.content`（5 类）
- `team.creative.creativecore.common.util.type`（5 类）
- `team.creative.creativecore.common.util.type.itr`（18 类）
- `team.creative.creativecore.common.util.type.list`（12 类）
- `team.creative.creativecore.common.util.type.map`（9 类）
- `team.creative.creativecore.common.util.type.set`（3 类）
- `team.creative.creativecore.common.util.type.tree`（2 类）
- `team.creative.creativecore.common.util.unsafe`（1 类）
- `team.creative.creativecore.mixin`（10 类）
- `team.creative.creativecore.reflection`（1 类）
