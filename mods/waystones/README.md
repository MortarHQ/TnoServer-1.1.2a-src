# Waystones（`waystones`）

> jar：`[传送石碑／指路石]waystones.jar` ｜ 版本：`21.1.29` ｜ 作者：BlayTheNinth
> 规模：**217 个类 / 35 个包 / 14332 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 17

**依赖**：`neoforge`、`minecraft`、`balm`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.blay09.mods.waystones.requirement` | 21 | `RequirementRegistry`、`ConfiguredRequirement`、`ConfiguredRequirementModifier` |
| `net.blay09.mods.waystones.core` | 15 | `PlayerWaystoneManager`、`WaystoneTeleportManager`、`WaystoneManagerImpl` |
| `net.blay09.mods.waystones.network.message` | 15 | `SyncWaystonesConfigMessage`、`EditWaystoneMessage`、`InventoryButtonMessage` |
| `net.blay09.mods.waystones.api` | 14 | `WaystonesAPI`、`WaystoneManager`、`MutableWaystone` |
| `net.blay09.mods.waystones.item` | 12 | `WarpStoneItem`、`BoundScrollItem`、`WarpScrollItem` |
| `net.blay09.mods.waystones.worldgen.namegen` | 11 | `NameGeneratorManager`、`CustomNameGenerator`、`MixedNameGenerator` |
| `net.blay09.mods.waystones.api.event` | 11 | `WaystoneTeleportEvent`、`GenerateWaystoneNameEvent`、`WaystoneUpdatedEvent` |
| `net.blay09.mods.waystones.compat` | 10 | `JourneyMapIntegration`、`DynmapIntegration`、`BlueMapIntegration` |
| `net.blay09.mods.waystones.client.requirement` | 9 | `RequirementClientRegistry`、`ItemRequirementRenderer`、`CooldownRequirementRenderer` |
| `net.blay09.mods.waystones.api.requirement` | 8 | `WarpRequirement`、`VariableResolver`、`ParameterSerializer` |
| `net.blay09.mods.waystones.client` | 7 | `NeoForgeWaystonesClient`、`ModClientEventHandlers`、`InventoryButtonGuiHandler` |
| `net.blay09.mods.waystones.client.gui.screen` | 7 | `WaystoneSelectionScreenBase`、`WaystoneEditScreen`、`AdminSelectionScreen` |
| `net.blay09.mods.waystones.block.entity` | 6 | `WaystoneBlockEntityBase`、`WarpPlateBlockEntity`、`WaystoneBlockEntity` |
| `net.blay09.mods.waystones.handler` | 6 | `ModEventHandlers`、`LoginHandler`、`WaystoneActivationStatHandler` |
| `net.blay09.mods.waystones.block` | 6 | `WaystoneBlockBase`、`WarpPlateBlock`、`PortstoneBlock` |
| `net.blay09.mods.waystones.client.render` | 6 | `PortstoneRenderer`、`WaystoneRenderer`、`SharestoneRenderer` |
| `net.blay09.mods.waystones.client.gui.widget` | 6 | `WaystoneVisbilityButton`、`ITooltipProvider`、`RemoveWaystoneButton` |
| `net.blay09.mods.waystones.config` | 5 | `WaystonesConfigData`、`WaystonesConfig`、`InventoryButtonMode` |
| `net.blay09.mods.waystones.menu` | 5 | `ModMenus`、`WaystoneSelectionMenu`、`WaystoneEditMenu` |
| `net.blay09.mods.waystones.comparator` | 5 | `WaystoneComparators`、`PreferSameDimensionComparator`、`DistanceToPlayerComparator` |
| `net.blay09.mods.waystones` | 4 | `NeoForgeWaystones`、`InternalMethodsImpl`、`InternalClientMethodsImpl` |
| `net.blay09.mods.waystones.command` | 4 | `ModCommands`、`OpenPlayerWaystonesGuiCommand`、`CountWaystonesCommand` |
| `net.blay09.mods.waystones.worldgen` | 4 | `ModWorldGen`、`WaystonePlacement`、`WaystoneFeature` |
| `net.blay09.mods.waystones.mixin` | 3 | `SinglePoolElementMixin`、`StructureTemplatePoolAccessor`、`JigsawPlacementPlacerMixin` |
| `net.blay09.mods.waystones.api.trait` | 3 | `IAttunementItem`、`IResetUseOnDamage`、`IFOVOnUse` |
| `net.blay09.mods.waystones.tag` | 3 | `ModItemTags`、`ModBlockTags`、`ModBiomeTags` |
| `net.blay09.mods.waystones.api.client` | 2 | `WaystonesClientAPI`、`InternalClientMethods` |
| `net.blay09.mods.waystones.api.error` | 2 | `WaystoneTeleportError`、`WaystoneEditError` |
| `net.blay09.mods.waystones.recipe` | 1 | `WaystoneRecipeInput` |
| `net.blay09.mods.waystones.compat.jei` | 1 | `AttunedShardJeiRecipe` |
| `net.blay09.mods.waystones.network` | 1 | `ModNetworking` |
| `net.blay09.mods.waystones.resources` | 1 | `ForceSpawnInVillagesCondition` |
| `net.blay09.mods.waystones.component` | 1 | `ModComponents` |
| `net.blay09.mods.waystones.permission` | 1 | `ModPermissions` |
| `net.blay09.mods.waystones.stats` | 1 | `ModStats` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RequirementRegistry` | class | — | — | 58 | 736 | [RequirementRegistry.java](net/blay09/mods/waystones/requirement/RequirementRegistry.java) |
| `WaystoneBlockEntityBase` | class | BalmBlockEntity | — | 37 | 429 | [WaystoneBlockEntityBase.java](net/blay09/mods/waystones/block/entity/WaystoneBlockEntityBase.java) |
| `NeoForgeWaystonesClient` | class | — | Mod | 0 | 16 | [NeoForgeWaystonesClient.java](net/blay09/mods/waystones/client/NeoForgeWaystonesClient.java) |
| `WaystonesConfigData` | class | BalmConfigData | Config | 1 | 187 | [WaystonesConfigData.java](net/blay09/mods/waystones/config/WaystonesConfigData.java) |
| `NeoForgeWaystones` | class | — | Mod | 0 | 28 | [NeoForgeWaystones.java](net/blay09/mods/waystones/NeoForgeWaystones.java) |
| `PlayerWaystoneManager` | class | — | — | 25 | 194 | [PlayerWaystoneManager.java](net/blay09/mods/waystones/core/PlayerWaystoneManager.java) |
| `ModClientEventHandlers` | class | — | — | 1 | 12 | [ModClientEventHandlers.java](net/blay09/mods/waystones/client/ModClientEventHandlers.java) |
| `WarpPlateBlockEntity` | class | WaystoneBlockEntityBase | — | 22 | 341 | [WarpPlateBlockEntity.java](net/blay09/mods/waystones/block/entity/WarpPlateBlockEntity.java) |
| `ModEventHandlers` | class | — | — | 1 | 17 | [ModEventHandlers.java](net/blay09/mods/waystones/handler/ModEventHandlers.java) |
| `WaystoneTeleportManager` | class | — | — | 10 | 264 | [WaystoneTeleportManager.java](net/blay09/mods/waystones/core/WaystoneTeleportManager.java) |
| `RequirementClientRegistry` | class | — | — | 4 | 39 | [RequirementClientRegistry.java](net/blay09/mods/waystones/client/requirement/RequirementClientRegistry.java) |
| `InternalMethodsImpl` | class | InternalMethods | — | 31 | 324 | [InternalMethodsImpl.java](net/blay09/mods/waystones/InternalMethodsImpl.java) |
| `WaystoneManagerImpl` | class | SavedData | — | 12 | 132 | [WaystoneManagerImpl.java](net/blay09/mods/waystones/core/WaystoneManagerImpl.java) |
| `WaystoneBlockBase` | class | BaseEntityBlock | — | 22 | 342 | [WaystoneBlockBase.java](net/blay09/mods/waystones/block/WaystoneBlockBase.java) |
| `WaystoneSyncManager` | class | — | — | 8 | 72 | [WaystoneSyncManager.java](net/blay09/mods/waystones/core/WaystoneSyncManager.java) |
| `WaystoneImpl` | class | Waystone | — | 25 | 245 | [WaystoneImpl.java](net/blay09/mods/waystones/core/WaystoneImpl.java) |
| `WarpPlateBlock` | class | WaystoneBlockBase | — | 17 | 228 | [WarpPlateBlock.java](net/blay09/mods/waystones/block/WarpPlateBlock.java) |
| `NameGeneratorManager` | class | SavedData | — | 6 | 103 | [NameGeneratorManager.java](net/blay09/mods/waystones/worldgen/namegen/NameGeneratorManager.java) |
| `SinglePoolElementMixin` | class | WaystoneStructurePoolElement | Mixin | 2 | 86 | [SinglePoolElementMixin.java](net/blay09/mods/waystones/mixin/SinglePoolElementMixin.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 28 | [StructureTemplatePoolAccessor.java](net/blay09/mods/waystones/mixin/StructureTemplatePoolAccessor.java) |
| `JigsawPlacementPlacerMixin` | class | — | Mixin | 1 | 52 | [JigsawPlacementPlacerMixin.java](net/blay09/mods/waystones/mixin/JigsawPlacementPlacerMixin.java) |
| `ModMenus` | class | — | — | 21 | 164 | [ModMenus.java](net/blay09/mods/waystones/menu/ModMenus.java) |
| `JourneyMapIntegration` | class | IClientPlugin | JourneyMapPlugin | 18 | 233 | [JourneyMapIntegration.java](net/blay09/mods/waystones/compat/JourneyMapIntegration.java) |
| `WaystonesAPI` | class | — | — | 31 | 160 | [WaystonesAPI.java](net/blay09/mods/waystones/api/WaystonesAPI.java) |
| `WaystoneSelectionScreenBase` | class | AbstractContainerScreen | — | 16 | 351 | [WaystoneSelectionScreenBase.java](net/blay09/mods/waystones/client/gui/screen/WaystoneSelectionScreenBase.java) |
| `LoginHandler` | class | — | — | 1 | 33 | [LoginHandler.java](net/blay09/mods/waystones/handler/LoginHandler.java) |
| `WaystoneActivationStatHandler` | class | — | — | 1 | 11 | [WaystoneActivationStatHandler.java](net/blay09/mods/waystones/handler/WaystoneActivationStatHandler.java) |
| `WaystoneDebugHandler` | class | — | — | 1 | 43 | [WaystoneDebugHandler.java](net/blay09/mods/waystones/handler/WaystoneDebugHandler.java) |
| `WarpStoneFOVHandler` | class | — | — | 2 | 24 | [WarpStoneFOVHandler.java](net/blay09/mods/waystones/handler/WarpStoneFOVHandler.java) |
| `WarpDamageResetHandler` | class | — | — | 1 | 16 | [WarpDamageResetHandler.java](net/blay09/mods/waystones/handler/WarpDamageResetHandler.java) |
| `PersistentPlayerWaystoneData` | class | IPlayerWaystoneData | — | 19 | 239 | [PersistentPlayerWaystoneData.java](net/blay09/mods/waystones/core/PersistentPlayerWaystoneData.java) |
| `WaystonePermissionManager` | class | — | — | 4 | 70 | [WaystonePermissionManager.java](net/blay09/mods/waystones/core/WaystonePermissionManager.java) |
| `WaystoneBlockEntity` | class | WaystoneBlockEntityBase | — | 7 | 74 | [WaystoneBlockEntity.java](net/blay09/mods/waystones/block/entity/WaystoneBlockEntity.java) |
| `SharestoneBlockEntity` | class | WaystoneBlockEntityBase | — | 8 | 83 | [SharestoneBlockEntity.java](net/blay09/mods/waystones/block/entity/SharestoneBlockEntity.java) |
| `DynmapIntegration` | class | DynmapCommonAPIListener | — | 17 | 161 | [DynmapIntegration.java](net/blay09/mods/waystones/compat/DynmapIntegration.java) |
| `WaystoneManager` | interface | — | — | 0 | 24 | [WaystoneManager.java](net/blay09/mods/waystones/api/WaystoneManager.java) |
| `ModCommands` | class | — | — | 2 | 457 | [ModCommands.java](net/blay09/mods/waystones/command/ModCommands.java) |
| `InventoryButtonGuiHandler` | class | — | — | 1 | 144 | [InventoryButtonGuiHandler.java](net/blay09/mods/waystones/client/InventoryButtonGuiHandler.java) |
| `SyncWaystonesConfigMessage` | class | SyncConfigMessage | — | 0 | 15 | [SyncWaystonesConfigMessage.java](net/blay09/mods/waystones/network/message/SyncWaystonesConfigMessage.java) |
| `WaystoneProxy` | class | Waystone | — | 20 | 149 | [WaystoneProxy.java](net/blay09/mods/waystones/core/WaystoneProxy.java) |
| `WaystoneTeleportContextImpl` | class | WaystoneTeleportContext | — | 21 | 151 | [WaystoneTeleportContextImpl.java](net/blay09/mods/waystones/core/WaystoneTeleportContextImpl.java) |
| `InMemoryPlayerWaystoneData` | class | IPlayerWaystoneData | — | 16 | 122 | [InMemoryPlayerWaystoneData.java](net/blay09/mods/waystones/core/InMemoryPlayerWaystoneData.java) |
| `PortstoneBlock` | class | WaystoneBlockBase | — | 13 | 193 | [PortstoneBlock.java](net/blay09/mods/waystones/block/PortstoneBlock.java) |
| `PortstoneBlockEntity` | class | BalmBlockEntity | — | 1 | 26 | [PortstoneBlockEntity.java](net/blay09/mods/waystones/block/entity/PortstoneBlockEntity.java) |
| `WaystoneSelectionMenu` | class | AbstractContainerMenu | — | 10 | 89 | [WaystoneSelectionMenu.java](net/blay09/mods/waystones/menu/WaystoneSelectionMenu.java) |
| `WaystoneRecipeInput` | class | RecipeInput | — | 3 | 33 | [WaystoneRecipeInput.java](net/blay09/mods/waystones/recipe/WaystoneRecipeInput.java) |
| `ConfiguredRequirement` | record | — | — | 0 | 8 | [ConfiguredRequirement.java](net/blay09/mods/waystones/requirement/ConfiguredRequirement.java) |
| `ConfiguredRequirementModifier` | record | — | — | 0 | 8 | [ConfiguredRequirementModifier.java](net/blay09/mods/waystones/requirement/ConfiguredRequirementModifier.java) |
| `ConfiguredCondition` | record | — | — | 0 | 7 | [ConfiguredCondition.java](net/blay09/mods/waystones/requirement/ConfiguredCondition.java) |
| `ItemRequirement` | class | WarpRequirement | — | 10 | 80 | [ItemRequirement.java](net/blay09/mods/waystones/requirement/ItemRequirement.java) |
| `WaystonesConfig` | class | — | — | 2 | 15 | [WaystonesConfig.java](net/blay09/mods/waystones/config/WaystonesConfig.java) |
| `BlueMapIntegration` | class | — | — | 13 | 197 | [BlueMapIntegration.java](net/blay09/mods/waystones/compat/BlueMapIntegration.java) |
| `AttunedShardJeiRecipe` | class | — | — | 2 | 30 | [AttunedShardJeiRecipe.java](net/blay09/mods/waystones/compat/jei/AttunedShardJeiRecipe.java) |
| `WaystoneTeleportEvent` | class | BalmEvent | — | 6 | 52 | [WaystoneTeleportEvent.java](net/blay09/mods/waystones/api/event/WaystoneTeleportEvent.java) |
| `WarpStoneItem` | class | Item | — | 10 | 188 | [WarpStoneItem.java](net/blay09/mods/waystones/item/WarpStoneItem.java) |
| `OpenPlayerWaystonesGuiCommand` | class | Command<CommandSourceStack> | — | 5 | 60 | [OpenPlayerWaystonesGuiCommand.java](net/blay09/mods/waystones/command/OpenPlayerWaystonesGuiCommand.java) |
| `ItemRequirementRenderer` | class | RequirementRenderer<ItemRequirement> | — | 2 | 26 | [ItemRequirementRenderer.java](net/blay09/mods/waystones/client/requirement/ItemRequirementRenderer.java) |
| `ModNetworking` | class | — | — | 1 | 99 | [ModNetworking.java](net/blay09/mods/waystones/network/ModNetworking.java) |
| `WaystoneBlock` | class | WaystoneBlockBase | — | 8 | 167 | [WaystoneBlock.java](net/blay09/mods/waystones/block/WaystoneBlock.java) |
| `SharestoneBlock` | class | WaystoneBlockBase | — | 7 | 104 | [SharestoneBlock.java](net/blay09/mods/waystones/block/SharestoneBlock.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `SinglePoolElementMixin` | `SinglePoolElement` | [SinglePoolElementMixin.java](net/blay09/mods/waystones/mixin/SinglePoolElementMixin.java) |
| `StructureTemplatePoolAccessor` | `StructureTemplatePoolAccessor` | [StructureTemplatePoolAccessor.java](net/blay09/mods/waystones/mixin/StructureTemplatePoolAccessor.java) |
| `JigsawPlacementPlacerMixin` | `JigsawPlacementPlacer` | [JigsawPlacementPlacerMixin.java](net/blay09/mods/waystones/mixin/JigsawPlacementPlacerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `WaystonesConfigData` | Config | [WaystonesConfigData.java](net/blay09/mods/waystones/config/WaystonesConfigData.java) |
| `SyncWaystonesConfigMessage` | — | [SyncWaystonesConfigMessage.java](net/blay09/mods/waystones/network/message/SyncWaystonesConfigMessage.java) |
| `ConfiguredRequirement` | — | [ConfiguredRequirement.java](net/blay09/mods/waystones/requirement/ConfiguredRequirement.java) |
| `ConfiguredRequirementModifier` | — | [ConfiguredRequirementModifier.java](net/blay09/mods/waystones/requirement/ConfiguredRequirementModifier.java) |
| `ConfiguredCondition` | — | [ConfiguredCondition.java](net/blay09/mods/waystones/requirement/ConfiguredCondition.java) |
| `WaystonesConfig` | — | [WaystonesConfig.java](net/blay09/mods/waystones/config/WaystonesConfig.java) |

## 按包浏览

- `net.blay09.mods.waystones`（4 类）
- `net.blay09.mods.waystones.api`（14 类）
- `net.blay09.mods.waystones.api.client`（2 类）
- `net.blay09.mods.waystones.api.error`（2 类）
- `net.blay09.mods.waystones.api.event`（11 类）
- `net.blay09.mods.waystones.api.requirement`（8 类）
- `net.blay09.mods.waystones.api.trait`（3 类）
- `net.blay09.mods.waystones.block`（6 类）
- `net.blay09.mods.waystones.block.entity`（6 类）
- `net.blay09.mods.waystones.client`（7 类）
- `net.blay09.mods.waystones.client.gui.screen`（7 类）
- `net.blay09.mods.waystones.client.gui.widget`（6 类）
- `net.blay09.mods.waystones.client.render`（6 类）
- `net.blay09.mods.waystones.client.requirement`（9 类）
- `net.blay09.mods.waystones.command`（4 类）
- `net.blay09.mods.waystones.comparator`（5 类）
- `net.blay09.mods.waystones.compat`（10 类）
- `net.blay09.mods.waystones.compat.jei`（1 类）
- `net.blay09.mods.waystones.component`（1 类）
- `net.blay09.mods.waystones.config`（5 类）
- `net.blay09.mods.waystones.core`（15 类）
- `net.blay09.mods.waystones.handler`（6 类）
- `net.blay09.mods.waystones.item`（12 类）
- `net.blay09.mods.waystones.menu`（5 类）
- `net.blay09.mods.waystones.mixin`（3 类）
- `net.blay09.mods.waystones.network`（1 类）
- `net.blay09.mods.waystones.network.message`（15 类）
- `net.blay09.mods.waystones.permission`（1 类）
- `net.blay09.mods.waystones.recipe`（1 类）
- `net.blay09.mods.waystones.requirement`（21 类）
- `net.blay09.mods.waystones.resources`（1 类）
- `net.blay09.mods.waystones.stats`（1 类）
- `net.blay09.mods.waystones.tag`（3 类）
- `net.blay09.mods.waystones.worldgen`（4 类）
- `net.blay09.mods.waystones.worldgen.namegen`（11 类）
