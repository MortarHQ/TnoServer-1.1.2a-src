# Resourceful Lib（`resourcefullib`）

> jar：`[前置]resourcefullib.jar` ｜ 版本：`3.0.12` ｜ 作者：ThatGravyBoat, Epic_Oreo
> 规模：**188 个类 / 67 个包 / 9526 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 19

**官方描述**：Team Resourceful Library

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.teamresourceful.resourcefullib.common.utils` | 10 | `SaveHandler`、`FileUtils`、`GenericMemoryPack` |
| `com.teamresourceful.resourcefullib.common.registry` | 9 | `ItemLikeResourcefulRegistry`、`HolderRegistryEntry`、`ResourcefulRegistryChild` |
| `com.teamresourceful.resourcefullib.client.screens` | 6 | `PriorityScreen`、`CursorScreen`、`AbstractContainerCursorScreen` |
| `com.teamresourceful.resourcefullib.common.nbt.validators.string` | 6 | `ContainsStringValidator`、`RegexStringValidator`、`StringValidator` |
| `com.teamresourceful.resourcefullib.neoforge` | 5 | `ResourcefulLibNeoForge`、`NeoForgeResourcePackHandler`、`NeoForgeClientApiProxy` |
| `com.teamresourceful.resourcefullib.common.fluid.data` | 5 | `FluidProperties`、`InternalFluidData`、`FluidData` |
| `com.teamresourceful.resourcefullib.common.registry.neoforge` | 5 | `NeoForgeResourcefulRegistry`、`NeoForgeHolderRegistryEntry`、`NeoForgeResourcefulFluidRegistry` |
| `com.teamresourceful.resourcefullib.common.network.base` | 5 | `ClientboundPacketType`、`Networking`、`PacketType` |
| `com.teamresourceful.resourcefullib.common.nbt` | 5 | `ObjectTagReaders`、`TagReader`、`TagUtils` |
| `com.teamresourceful.resourcefullib.common.menu` | 5 | `MenuContentHelper`、`MenuContent`、`MenuContentSerializer` |
| `com.teamresourceful.resourcefullib.client.scissor` | 5 | `ScissorBoxStack`、`ClosingScissorBox`、`CloseableScissorStack` |
| `com.teamresourceful.resourcefullib.common.codecs.yabn` | 4 | `YabnOps`、`YabnArrayBuilder`、`YabnObjectMapLike` |
| `com.teamresourceful.resourcefullib.client.components` | 4 | `ParentWidget`、`ImageButton`、`SelectedImageButton` |
| `com.teamresourceful.resourcefullib.common.codecs.predicates.properties` | 4 | `BlockStatePredicate`、`ExactPropertyMatcher`、`PropertyMatcher` |
| `com.teamresourceful.resourcefullib.common.codecs.predicates` | 4 | `RestrictedEntityPredicate`、`RestrictedBlockPredicate`、`RestrictedItemPredicate` |
| `com.teamresourceful.resourcefullib.common.bytecodecs` | 4 | `StreamCodecByteCodec`、`ExtraByteCodecs`、`IdMapByteCodec` |
| `com.teamresourceful.resourcefullib.client.sysinfo.defaults` | 4 | `MinecraftInfo`、`OperatingSystem`、`LauncherInfo` |
| `com.teamresourceful.resourcefullib.common.nbt.validators` | 4 | `ValidatorCodec`、`TagValidationType`、`Validator` |
| `com.teamresourceful.resourcefullib.client.screens.state` | 3 | `ScreenStateManager`、`ScreenState`、`PageState` |
| `com.teamresourceful.resourcefullib.common.color` | 3 | `Color`、`ConstantColors`、`MutableColor` |
| `com.teamresourceful.resourcefullib.common.network.defaults` | 3 | `CodecPacketType`、`DatalessPacketType`、`AbstractPacketType` |
| `com.teamresourceful.resourcefullib.common.collections` | 3 | `WeightedCollection`、`SelectableList`、`CycleableList` |
| `com.teamresourceful.resourcefullib.client.utils` | 3 | `ScreenUtils`、`CursorUtils`、`RenderUtils` |
| `com.teamresourceful.resourcefullib.common.recipe` | 3 | `CodecRecipe`、`CodecRecipeType`、`CodecRecipeSerializer` |
| `com.teamresourceful.resourcefullib.common.fluid` | 3 | `ResourcefulFlowingFluid`、`ResourcefulLiquidBlock`、`ResourcefulBucketItem` |
| `com.teamresourceful.resourcefullib.common.codecs` | 3 | `CodecExtras`、`PrimitiveCodecHelper`、`EnumCodec` |
| `com.teamresourceful.resourcefullib.common.item` | 3 | `OptionalItemStack`、`LazyHolder`、`ResourcefulComponentType` |
| `com.teamresourceful.resourcefullib.common.exceptions` | 3 | `UtilityClassException`、`ValidationException`、`NotImplementedException` |
| `com.teamresourceful.resourcefullib.common.recipe.ingredient` | 3 | `IngredientHelper`、`CodecIngredientSerializer`、`CodecIngredient` |
| `com.teamresourceful.resourcefullib.client.highlights.base` | 3 | `HighlightLine`、`Highlightable`、`Highlight` |
| `com.teamresourceful.resourcefullib.common.nbt.validators.list` | 3 | `ListValidator`、`MatchesListValidator`、`ExactListValidator` |
| `com.teamresourceful.resourcefullib.common.nbt.validators.numeric` | 3 | `ExactNumericValidator`、`NumericValidator`、`BetweenNumericValidator` |
| `com.teamresourceful.resourcefullib.common.nbt.validators.object` | 3 | `PartialObjectValidator`、`ObjectValidator`、`ExactObjectValidator` |
| `com.teamresourceful.resourcefullib.common.caches` | 3 | `CacheableBiFunction`、`CachingFunction`、`CacheableFunction` |
| `com.teamresourceful.resourcefullib.client.registry` | 2 | `ResourcefulClientRegistryType`、`ResourcefulClientRegistries` |
| `com.teamresourceful.resourcefullib.common.network` | 2 | `Network`、`Packet` |
| `com.teamresourceful.resourcefullib.client.components.selection` | 2 | `SelectionList`、`ListEntry` |
| `com.teamresourceful.resourcefullib.common.network.neoforge` | 2 | `NeoForgeNetworking`、`NetworkImpl` |
| `com.teamresourceful.resourcefullib.common.utils.files` | 2 | `CodecSavedData`、`GlobalStorage` |
| `com.teamresourceful.resourcefullib.common.codecs.recipes` | 2 | `ItemStackCodec`、`LazyHolders` |
| … | | 其余 27 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ResourcefulLibNeoForge` | class | — | Mod | 1 | 29 | [ResourcefulLibNeoForge.java](com/teamresourceful/resourcefullib/neoforge/ResourcefulLibNeoForge.java) |
| `ItemLikeResourcefulRegistry` | class | ResourcefulRegistry<T> | — | 11 | 86 | [ItemLikeResourcefulRegistry.java](com/teamresourceful/resourcefullib/common/registry/ItemLikeResourcefulRegistry.java) |
| `ResourcefulClientFluidRegistry` | class | ResourcefulRegistry<ClientFluidPrope | — | 8 | 66 | [ResourcefulClientFluidRegistry.java](com/teamresourceful/resourcefullib/client/fluid/registry/ResourcefulClientFluidRegistry.java) |
| `ResourcefulClientRegistryType` | class | — | — | 1 | 24 | [ResourcefulClientRegistryType.java](com/teamresourceful/resourcefullib/client/registry/ResourcefulClientRegistryType.java) |
| `ScreenStateManager` | class | — | — | 4 | 32 | [ScreenStateManager.java](com/teamresourceful/resourcefullib/client/screens/state/ScreenStateManager.java) |
| `NeoForgeResourcePackHandler` | class | — | — | 8 | 115 | [NeoForgeResourcePackHandler.java](com/teamresourceful/resourcefullib/neoforge/NeoForgeResourcePackHandler.java) |
| `ResourcefulFlowingFluidMixin` | class | Fluid | Mixin | 1 | 28 | [ResourcefulFlowingFluidMixin.java](com/teamresourceful/resourcefullib/mixins/neoforge/ResourcefulFlowingFluidMixin.java) |
| `NetworkPacketPayload` | record | CustomPacketPayload | Internal | 0 | 15 | [NetworkPacketPayload.java](com/teamresourceful/resourcefullib/common/network/internal/NetworkPacketPayload.java) |
| `FluidProperties` | interface | — | — | 28 | 283 | [FluidProperties.java](com/teamresourceful/resourcefullib/common/fluid/data/FluidProperties.java) |
| `ResourcefulFluidRegistry` | interface | RegistryEntry<FluidData> | — | 2 | 45 | [ResourcefulFluidRegistry.java](com/teamresourceful/resourcefullib/common/fluid/registry/ResourcefulFluidRegistry.java) |
| `YabnOps` | class | DynamicOps<YabnElement> | — | 25 | 297 | [YabnOps.java](com/teamresourceful/resourcefullib/common/codecs/yabn/YabnOps.java) |
| `HolderRegistryEntry` | interface | — | — | 0 | 13 | [HolderRegistryEntry.java](com/teamresourceful/resourcefullib/common/registry/HolderRegistryEntry.java) |
| `ResourcefulRegistryChild` | class | ResourcefulRegistry<T> | — | 4 | 33 | [ResourcefulRegistryChild.java](com/teamresourceful/resourcefullib/common/registry/ResourcefulRegistryChild.java) |
| `ResourcefulRegistryType` | class | — | — | 1 | 21 | [ResourcefulRegistryType.java](com/teamresourceful/resourcefullib/common/registry/ResourcefulRegistryType.java) |
| `EnumResourcefulRegistryChild` | class | — | — | 2 | 22 | [EnumResourcefulRegistryChild.java](com/teamresourceful/resourcefullib/common/registry/EnumResourcefulRegistryChild.java) |
| `ResourcefulRegistry` | interface | — | — | 0 | 24 | [ResourcefulRegistry.java](com/teamresourceful/resourcefullib/common/registry/ResourcefulRegistry.java) |
| `RegistryEntries` | class | — | — | 2 | 19 | [RegistryEntries.java](com/teamresourceful/resourcefullib/common/registry/RegistryEntries.java) |
| `RegistryEntry` | interface | — | — | 0 | 12 | [RegistryEntry.java](com/teamresourceful/resourcefullib/common/registry/RegistryEntry.java) |
| `NeoForgeResourcefulRegistry` | class | ResourcefulRegistry<T> | — | 4 | 41 | [NeoForgeResourcefulRegistry.java](com/teamresourceful/resourcefullib/common/registry/neoforge/NeoForgeResourcefulRegistry.java) |
| `NeoForgeHolderRegistryEntry` | class | HolderRegistryEntry<R> | — | 2 | 25 | [NeoForgeHolderRegistryEntry.java](com/teamresourceful/resourcefullib/common/registry/neoforge/NeoForgeHolderRegistryEntry.java) |
| `NeoForgeResourcefulFluidRegistry` | class | ResourcefulFluidRegistry | — | 3 | 48 | [NeoForgeResourcefulFluidRegistry.java](com/teamresourceful/resourcefullib/common/registry/neoforge/NeoForgeResourcefulFluidRegistry.java) |
| `NeoForgeRegistryEntry` | class | RegistryEntry<T> | — | 2 | 24 | [NeoForgeRegistryEntry.java](com/teamresourceful/resourcefullib/common/registry/neoforge/NeoForgeRegistryEntry.java) |
| `Color` | class | — | — | 28 | 269 | [Color.java](com/teamresourceful/resourcefullib/common/color/Color.java) |
| `SaveHandler` | class | SavedData | — | 8 | 56 | [SaveHandler.java](com/teamresourceful/resourcefullib/common/utils/SaveHandler.java) |
| `Network` | class | Networking | — | 13 | 121 | [Network.java](com/teamresourceful/resourcefullib/common/network/Network.java) |
| `ClientboundPacketType` | interface | — | — | 0 | 8 | [ClientboundPacketType.java](com/teamresourceful/resourcefullib/common/network/base/ClientboundPacketType.java) |
| `CodecPacketType` | class | ClientboundPacketType<T> | — | 10 | 111 | [CodecPacketType.java](com/teamresourceful/resourcefullib/common/network/defaults/CodecPacketType.java) |
| `WeightedCollection` | class | Collection<E> | — | 26 | 196 | [WeightedCollection.java](com/teamresourceful/resourcefullib/common/collections/WeightedCollection.java) |
| `HighlightHandler` | class | SimpleJsonResourceReloadListener | — | 4 | 153 | [HighlightHandler.java](com/teamresourceful/resourcefullib/client/highlights/HighlightHandler.java) |
| `SelectionList` | class | AbstractContainerEventHandler | — | 21 | 232 | [SelectionList.java](com/teamresourceful/resourcefullib/client/components/selection/SelectionList.java) |
| `ScreenUtils` | class | — | — | 10 | 74 | [ScreenUtils.java](com/teamresourceful/resourcefullib/client/utils/ScreenUtils.java) |
| `NeoForgeNetworking` | class | Networking | — | 9 | 90 | [NeoForgeNetworking.java](com/teamresourceful/resourcefullib/common/network/neoforge/NeoForgeNetworking.java) |
| `DatalessPacketType` | class | — | — | 2 | 33 | [DatalessPacketType.java](com/teamresourceful/resourcefullib/common/network/defaults/DatalessPacketType.java) |
| `MenuContentHelperImpl` | class | MenuProvider | — | 5 | 55 | [MenuContentHelperImpl.java](com/teamresourceful/resourcefullib/common/menu/neoforge/MenuContentHelperImpl.java) |
| `CodecRecipe` | interface | — | — | 0 | 36 | [CodecRecipe.java](com/teamresourceful/resourcefullib/common/recipe/CodecRecipe.java) |
| `CodecRecipeType` | class | RecipeType<T> | — | 2 | 21 | [CodecRecipeType.java](com/teamresourceful/resourcefullib/common/recipe/CodecRecipeType.java) |
| `CodecRecipeSerializer` | class | RecipeSerializer<R> | — | 3 | 42 | [CodecRecipeSerializer.java](com/teamresourceful/resourcefullib/common/recipe/CodecRecipeSerializer.java) |
| `CodecRecipeBuilder` | class | RecipeBuilder | — | 2 | 27 | [CodecRecipeBuilder.java](com/teamresourceful/resourcefullib/common/datagen/CodecRecipeBuilder.java) |
| `ClientFluidProperties` | interface | — | — | 14 | 176 | [ClientFluidProperties.java](com/teamresourceful/resourcefullib/client/fluid/data/ClientFluidProperties.java) |
| `ParentWidget` | class | AbstractContainerEventHandler | — | 21 | 132 | [ParentWidget.java](com/teamresourceful/resourcefullib/client/components/ParentWidget.java) |
| `Packet` | interface | — | — | 0 | 8 | [Packet.java](com/teamresourceful/resourcefullib/common/network/Packet.java) |
| `Networking` | interface | — | — | 0 | 17 | [Networking.java](com/teamresourceful/resourcefullib/common/network/base/Networking.java) |
| `PacketType` | interface | — | NonExtendable | 0 | 37 | [PacketType.java](com/teamresourceful/resourcefullib/common/network/base/PacketType.java) |
| `ServerboundPacketType` | interface | — | — | 0 | 10 | [ServerboundPacketType.java](com/teamresourceful/resourcefullib/common/network/base/ServerboundPacketType.java) |
| `NetworkHandle` | class | — | — | 4 | 37 | [NetworkHandle.java](com/teamresourceful/resourcefullib/common/network/base/NetworkHandle.java) |
| `NetworkImpl` | class | — | — | 1 | 11 | [NetworkImpl.java](com/teamresourceful/resourcefullib/common/network/neoforge/NetworkImpl.java) |
| `AbstractPacketType` | class | PacketType<T> | — | 1 | 25 | [AbstractPacketType.java](com/teamresourceful/resourcefullib/common/network/defaults/AbstractPacketType.java) |
| `ObjectTagReaders` | class | — | Experimental | 11 | 200 | [ObjectTagReaders.java](com/teamresourceful/resourcefullib/common/nbt/ObjectTagReaders.java) |
| `TagReader` | class | NbtReader<I | Experimental | 18 | 122 | [TagReader.java](com/teamresourceful/resourcefullib/common/nbt/TagReader.java) |
| `ResourcefulFlowingFluid` | class | FlowingFluid | — | 19 | 134 | [ResourcefulFlowingFluid.java](com/teamresourceful/resourcefullib/common/fluid/ResourcefulFlowingFluid.java) |
| `ResourcefulFluidType` | class | FluidType | — | 18 | 170 | [ResourcefulFluidType.java](com/teamresourceful/resourcefullib/common/fluid/neoforge/ResourcefulFluidType.java) |
| `InternalFluidData` | class | FluidData | Internal | 11 | 85 | [InternalFluidData.java](com/teamresourceful/resourcefullib/common/fluid/data/InternalFluidData.java) |
| `MenuContentHelper` | class | — | — | 2 | 38 | [MenuContentHelper.java](com/teamresourceful/resourcefullib/common/menu/MenuContentHelper.java) |
| `BlockStatePredicate` | record | — | — | 5 | 62 | [BlockStatePredicate.java](com/teamresourceful/resourcefullib/common/codecs/predicates/properties/BlockStatePredicate.java) |
| `FileUtils` | class | — | — | 10 | 99 | [FileUtils.java](com/teamresourceful/resourcefullib/common/utils/FileUtils.java) |
| `CodecSavedData` | class | SavedData | — | 10 | 122 | [CodecSavedData.java](com/teamresourceful/resourcefullib/common/utils/files/CodecSavedData.java) |
| `CursorUtils` | class | — | — | 11 | 70 | [CursorUtils.java](com/teamresourceful/resourcefullib/client/utils/CursorUtils.java) |
| `PriorityScreen` | class | BaseCursorScreen | — | 8 | 90 | [PriorityScreen.java](com/teamresourceful/resourcefullib/client/screens/PriorityScreen.java) |
| `NeoForgeClientApiProxy` | class | ApiProxy | — | 1 | 17 | [NeoForgeClientApiProxy.java](com/teamresourceful/resourcefullib/neoforge/NeoForgeClientApiProxy.java) |
| `ResourcefulLibNeoForgeClient` | class | — | — | 4 | 54 | [ResourcefulLibNeoForgeClient.java](com/teamresourceful/resourcefullib/neoforge/ResourcefulLibNeoForgeClient.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ResourcefulFlowingFluidMixin` | `ResourcefulFlowingFluid` | [ResourcefulFlowingFluidMixin.java](com/teamresourceful/resourcefullib/mixins/neoforge/ResourcefulFlowingFluidMixin.java) |

## 按包浏览

- `architectury_inject_ResourcefulLib_common_c438e14532e54b0f808fbfc3c4e70275_f2d92e0d53276cdd30c2b4982d358bdbad5243b82917cf8a748e44a745b4e970resourcefullib3012devjar`（1 类）
- `com.teamresourceful.resourcefullib`（1 类）
- `com.teamresourceful.resourcefullib.client`（1 类）
- `com.teamresourceful.resourcefullib.client.components`（4 类）
- `com.teamresourceful.resourcefullib.client.components.selection`（2 类）
- `com.teamresourceful.resourcefullib.client.fluid.data`（1 类）
- `com.teamresourceful.resourcefullib.client.fluid.registry`（1 类）
- `com.teamresourceful.resourcefullib.client.highlights`（1 类）
- `com.teamresourceful.resourcefullib.client.highlights.base`（3 类）
- `com.teamresourceful.resourcefullib.client.highlights.state`（2 类）
- `com.teamresourceful.resourcefullib.client.registry`（2 类）
- `com.teamresourceful.resourcefullib.client.scissor`（5 类）
- `com.teamresourceful.resourcefullib.client.screens`（6 类）
- `com.teamresourceful.resourcefullib.client.screens.state`（3 类）
- `com.teamresourceful.resourcefullib.client.sysinfo`（2 类）
- `com.teamresourceful.resourcefullib.client.sysinfo.defaults`（4 类）
- `com.teamresourceful.resourcefullib.client.utils`（3 类）
- `com.teamresourceful.resourcefullib.common`（1 类）
- `com.teamresourceful.resourcefullib.common.bytecodecs`（4 类）
- `com.teamresourceful.resourcefullib.common.caches`（3 类）
- `com.teamresourceful.resourcefullib.common.codecs`（3 类）
- `com.teamresourceful.resourcefullib.common.codecs.bounds`（1 类）
- `com.teamresourceful.resourcefullib.common.codecs.deferred`（2 类）
- `com.teamresourceful.resourcefullib.common.codecs.maps`（1 类）
- `com.teamresourceful.resourcefullib.common.codecs.predicates`（4 类）
- `com.teamresourceful.resourcefullib.common.codecs.predicates.properties`（4 类）
- `com.teamresourceful.resourcefullib.common.codecs.recipes`（2 类）
- `com.teamresourceful.resourcefullib.common.codecs.tags`（1 类）
- `com.teamresourceful.resourcefullib.common.codecs.yabn`（4 类）
- `com.teamresourceful.resourcefullib.common.collections`（3 类）
- `com.teamresourceful.resourcefullib.common.color`（3 类）
- `com.teamresourceful.resourcefullib.common.datagen`（1 类）
- `com.teamresourceful.resourcefullib.common.exceptions`（3 类）
- `com.teamresourceful.resourcefullib.common.fluid`（3 类）
- `com.teamresourceful.resourcefullib.common.fluid.data`（5 类）
- `com.teamresourceful.resourcefullib.common.fluid.neoforge`（1 类）
- `com.teamresourceful.resourcefullib.common.fluid.registry`（1 类）
- `com.teamresourceful.resourcefullib.common.inventory`（1 类）
- `com.teamresourceful.resourcefullib.common.item`（3 类）
- `com.teamresourceful.resourcefullib.common.item.tabs`（1 类）
- `com.teamresourceful.resourcefullib.common.item.tabs.neoforge`（1 类）
- `com.teamresourceful.resourcefullib.common.lib`（2 类）
- `com.teamresourceful.resourcefullib.common.menu`（5 类）
- `com.teamresourceful.resourcefullib.common.menu.neoforge`（1 类）
- `com.teamresourceful.resourcefullib.common.nbt`（5 类）
- `com.teamresourceful.resourcefullib.common.nbt.validators`（4 类）
- `com.teamresourceful.resourcefullib.common.nbt.validators.list`（3 类）
- `com.teamresourceful.resourcefullib.common.nbt.validators.numeric`（3 类）
- `com.teamresourceful.resourcefullib.common.nbt.validators.object`（3 类）
- `com.teamresourceful.resourcefullib.common.nbt.validators.string`（6 类）
- `com.teamresourceful.resourcefullib.common.network`（2 类）
- `com.teamresourceful.resourcefullib.common.network.base`（5 类）
- `com.teamresourceful.resourcefullib.common.network.defaults`（3 类）
- `com.teamresourceful.resourcefullib.common.network.internal`（1 类）
- `com.teamresourceful.resourcefullib.common.network.neoforge`（2 类）
- `com.teamresourceful.resourcefullib.common.recipe`（3 类）
- `com.teamresourceful.resourcefullib.common.recipe.ingredient`（3 类）
- `com.teamresourceful.resourcefullib.common.recipe.ingredient.neoforge`（2 类）
- `com.teamresourceful.resourcefullib.common.registry`（9 类）
- `com.teamresourceful.resourcefullib.common.registry.neoforge`（5 类）
- `com.teamresourceful.resourcefullib.common.utils`（10 类）
- `com.teamresourceful.resourcefullib.common.utils.files`（2 类）
- `com.teamresourceful.resourcefullib.common.utils.modinfo`（2 类）
- `com.teamresourceful.resourcefullib.common.utils.modinfo.neoforge`（2 类）
- `com.teamresourceful.resourcefullib.common.utils.neoforge`（1 类）
- `com.teamresourceful.resourcefullib.mixins.neoforge`（1 类）
- `com.teamresourceful.resourcefullib.neoforge`（5 类）
