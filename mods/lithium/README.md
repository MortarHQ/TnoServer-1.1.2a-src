# Lithium（`lithium`）

> jar：`[优化lithium-neoforge-0.15.3+mc1.21.1.jar` ｜ 版本：`0.15.3+mc1.21.1` ｜ 作者：2No2Name, JellySquid
> 规模：**380 个类 / 167 个包 / 22399 行** ｜ 事件处理类 0 ｜ Mixin 231 ｜ 注册/管理类 9

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.caffeinemc.mods.lithium.mixin.block.hopper` | 16 | `HopperBlockEntityMixin`、`ChiseledBookShelfBlockEntityMixin`、`InventoryAccessors` |
| `net.caffeinemc.mods.lithium.common.util.collections` | 12 | `ListeningList`、`HashedReferenceList`、`FixedChunkAccessSectionBitBuffer` |
| `net.caffeinemc.mods.lithium.common.world` | 10 | `GameEventDispatcherStorage`、`ChunkAwareEntityIterable`、`WorldHelper` |
| `net.caffeinemc.mods.lithium.common.hopper` | 9 | `LithiumDoubleStackList`、`LithiumStackList`、`BlockStateOnlyInventory` |
| `net.caffeinemc.mods.lithium.common.shapes` | 9 | `VoxelShapeSimpleCube`、`VoxelShapeAlignedCuboid`、`VoxelShapeAlignedCuboidOffset` |
| `net.caffeinemc.mods.lithium.mixin.util.accessors` | 7 | `TransientEntitySectionManagerAccessor`、`PersistentEntitySectionManagerAccessor`、`ItemEntityAccessor` |
| `net.caffeinemc.mods.lithium.mixin.entity.inactive_navigations` | 7 | `DrownedEntityLeaveWaterGoalMixin`、`LivingEntityMixin`、`ServerLevel` |
| `net.caffeinemc.mods.lithium.mixin.ai.pathing` | 7 | `BlockStateBaseMixin`、`PathNavigationRegionMixin`、`FlyNodeEvaluatorMixin` |
| `net.caffeinemc.mods.lithium.common.entity` | 7 | `LithiumEntityCollisions`、`EntityClassGroup`、`FluidCachingEntity` |
| `net.caffeinemc.mods.lithium.common.tracking.entity` | 7 | `SectionedItemEntityMovementTracker`、`SectionedEntityMovementTracker`、`SectionedEntityMovementListener` |
| `net.caffeinemc.mods.lithium.mixin.entity.collisions.unpushable_cramming` | 6 | `EntitySectionMixin`、`EntityMixin`、`LivingEntityMixin` |
| `net.caffeinemc.mods.lithium.mixin.entity.replace_entitytype_predicates` | 6 | `HangingEntityMixin`、`ItemFrameMixin`、`GolemRandomStrollInVillageGoalMixin` |
| `net.caffeinemc.mods.lithium.common.util` | 6 | `POIRegistryEntries`、`Pos`、`Distances` |
| `net.caffeinemc.mods.lithium.common.block` | 6 | `BlockStateFlags`、`ListeningBlockStatePredicate`、`BlockStateFlagHolder` |
| `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.spawning` | 5 | `PersistentEntitySectionManagerAccessor`、`EntitySectionStorageMixin`、`EntitySectionAccessor` |
| `net.caffeinemc.mods.lithium.neoforge` | 5 | `LithiumNeoForgeMod`、`NeoForgeModCompat`、`NeoForgeRuntimeInformation` |
| `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping` | 5 | `WrappedBlockEntityTickInvokerAccessor`、`BlockEntityMixin`、`ServerLevelMixin` |
| `net.caffeinemc.mods.lithium.common.block.entity` | 5 | `SetBlockStateHandlingBlockEntity`、`SetChangedHandlingBlockEntity`、`SleepUntilTimeBlockEntityTickInvoker` |
| `net.caffeinemc.mods.lithium.common.world.chunk` | 5 | `LithiumHashPalette`、`ChunkHolderExtended`、`ChunkStatusTracker` |
| `net.caffeinemc.mods.lithium.common.services` | 5 | `PlatformModCompat`、`PlatformMappingInformation`、`Services` |
| `net.caffeinemc.mods.lithium.mixin.util.entity_movement_tracking` | 4 | `ServerEntityManagerListenerMixin`、`PersistentEntitySectionManagerAccessor`、`EntitySectionMixin` |
| `net.caffeinemc.mods.lithium.mixin.ai.non_poi_block_search` | 4 | `RemoveBlockGoalMixin`、`MoveToBlockGoalMixin`、`HoglinSpecificSensorMixin` |
| `net.caffeinemc.mods.lithium.mixin.world.chunk_access` | 4 | `ServerChunkCacheMixin`、`LevelMixin`、`ChunkHolderMixin` |
| `net.caffeinemc.mods.lithium.common.tracking.block` | 4 | `SectionedBlockChangeTracker`、`BlockCache`、`BlockCacheProvider` |
| `net.caffeinemc.mods.lithium.common.ai.non_poi_block_search` | 4 | `CheckAndCacheBlockChecker`、`LithiumMoveToBlockGoal`、`CommonBlockSearchesCheckAndCache` |
| `net.caffeinemc.mods.lithium.common.util.tuples` | 4 | `WorldSectionBox`、`SortedPointOfInterest`、`Range6Int` |
| `net.caffeinemc.mods.lithium.api.inventory` | 4 | `LithiumTransferConditionInventory`、`LithiumCooldownReceivingInventory`、`LithiumDefaultedList` |
| `net.caffeinemc.mods.lithium.mixin.debug.palette` | 3 | `ClientBoundLevelChunkPacketDataAccessor`、`ClientPacketListenerMixin`、`PalettedContainerMixin` |
| `net.caffeinemc.mods.lithium.mixin.ai.poi` | 3 | `PoiManagerMixin`、`PoiSectionMixin`、`SectionStorageMixin` |
| `net.caffeinemc.mods.lithium.mixin.util.item_component_and_count_tracking` | 3 | `ItemEntityMixin`、`ItemStackMixin`、`PatchedDataComponentMapMixin` |
| `net.caffeinemc.mods.lithium.mixin.util.inventory_change_listening` | 3 | `BaseContainerBlockEntityMixin`、`BlockEntityMixin`、`StackListReplacementTracking` |
| `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.world.block_entity_ticking.support_cache` | 3 | `BlockEntityMixin`、`DirectBlockEntityTickInvokerMixin`、`LevelChunkMixin` |
| `net.caffeinemc.mods.lithium.mixin.entity.equipment_tracking` | 3 | `LivingEntityMixin`、`MobMixin`、`ArmorStandMixin` |
| `net.caffeinemc.mods.lithium.mixin.util.block_tracking` | 3 | `BlockStateBaseMixin`、`LevelChunkSectionMixin`、`BootstrapMixin` |
| `net.caffeinemc.mods.lithium.mixin.ai.raid` | 3 | `RaidMixin`、`Raider`、`RaiderMixin` |
| `net.caffeinemc.mods.lithium.mixin.math.fast_util` | 3 | `AABBMixin`、`DirectionMixin`、`AxisCycleDirectionMixin` |
| `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.collisions.empty_space` | 3 | `ArrayVoxelShapeInvoker`、`LevelMixin`、`BitSetDiscreteVoxelShapeAccessor` |
| `net.caffeinemc.mods.lithium.common.entity.pushable` | 3 | `BlockCachingEntity`、`EntityPushablePredicate`、`PushableEntityClassGroup` |
| `net.caffeinemc.mods.lithium.common.block.entity.inventory_change_tracking` | 3 | `InventoryChangeListener`、`InventoryChangeEmitter`、`InventoryChangeTracker` |
| `net.caffeinemc.mods.lithium.common.world.interests` | 3 | `PointOfInterestSetExtended`、`PointOfInterestStorageExtended`、`RegionBasedStorageSectionExtended` |
| … | | 其余 127 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `HopperBlockEntityMixin` | class | BlockEntity | Mixin | 31 | 848 | [HopperBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/block/hopper/HopperBlockEntityMixin.java) |
| `ServerEntityManagerListenerMixin` | class | ToggleableMovementTracker | Mixin | 5 | 73 | [ServerEntityManagerListenerMixin.java](net/caffeinemc/mods/lithium/mixin/util/entity_movement_tracking/ServerEntityManagerListenerMixin.java) |
| `TransientEntitySectionManagerAccessor` | interface | — | Mixin | 0 | 14 | [TransientEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/util/accessors/TransientEntitySectionManagerAccessor.java) |
| `PersistentEntitySectionManagerAccessor` | interface | — | Mixin | 0 | 14 | [PersistentEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/util/accessors/PersistentEntitySectionManagerAccessor.java) |
| `PersistentEntitySectionManagerAccessor` | interface | — | Mixin | 0 | 14 | [PersistentEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/util/entity_movement_tracking/PersistentEntitySectionManagerAccessor.java) |
| `PersistentEntitySectionManagerAccessor` | interface | — | Mixin | 0 | 14 | [PersistentEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/spawning/PersistentEntitySectionManagerAccessor.java) |
| `DistanceManagerMixin` | class | — | Mixin | 10 | 126 | [DistanceManagerMixin.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/world/expiring_chunk_tickets/DistanceManagerMixin.java) |
| `ClientBoundLevelChunkPacketDataAccessor` | interface | — | Mixin | 0 | 12 | [ClientBoundLevelChunkPacketDataAccessor.java](net/caffeinemc/mods/lithium/mixin/debug/palette/ClientBoundLevelChunkPacketDataAccessor.java) |
| `PoiManagerMixin` | class | SectionStorage | Mixin | 9 | 163 | [PoiManagerMixin.java](net/caffeinemc/mods/lithium/mixin/ai/poi/PoiManagerMixin.java) |
| `ItemEntityList` | class | AbstractList | — | 36 | 418 | [ItemEntityList.java](net/caffeinemc/mods/lithium/common/entity/item/ItemEntityList.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 1 | 31 | [ClientPacketListenerMixin.java](net/caffeinemc/mods/lithium/mixin/debug/palette/ClientPacketListenerMixin.java) |
| `PoiManagerMixin` | class | SectionStorage | Mixin | 1 | 95 | [PoiManagerMixin.java](net/caffeinemc/mods/lithium/mixin/ai/poi/fast_portals/PoiManagerMixin.java) |
| `LithiumNeoForgeMod` | class | — | Mod | 0 | 12 | [LithiumNeoForgeMod.java](net/caffeinemc/mods/lithium/neoforge/LithiumNeoForgeMod.java) |
| `ChestBlockEntityMixin` | class | RandomizableContainerBlockEntity | Mixin | 1 | 25 | [ChestBlockEntityMixin.java](net/caffeinemc/mods/lithium/neoforge/mixin/util/inventory_change_listening/ChestBlockEntityMixin.java) |
| `ItemEntityMixin` | class | Entity | Mixin | 2 | 74 | [ItemEntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/item_entity_merging/ItemEntityMixin.java) |
| `EntityMixin` | class | — | Mixin | 6 | 225 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/collisions/movement/EntityMixin.java) |
| `FlowingFluidMixin` | class | — | Mixin | 18 | 264 | [FlowingFluidMixin.java](net/caffeinemc/mods/lithium/mixin/block/fluid/flow/FlowingFluidMixin.java) |
| `ChiseledBookShelfBlockEntityMixin` | class | LithiumTransferConditionInventory | Mixin | 0 | 14 | [ChiseledBookShelfBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/block/hopper/ChiseledBookShelfBlockEntityMixin.java) |
| `PistonMovingBlockEntityMixin` | class | — | Mixin | 4 | 93 | [PistonMovingBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/block/moving_block_shapes/PistonMovingBlockEntityMixin.java) |
| `BrainMixin` | class | — | Mixin | 15 | 200 | [BrainMixin.java](net/caffeinemc/mods/lithium/mixin/ai/task/launch/BrainMixin.java) |
| `WrappedBlockEntityTickInvokerAccessor` | interface | — | Mixin | 0 | 16 | [WrappedBlockEntityTickInvokerAccessor.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/WrappedBlockEntityTickInvokerAccessor.java) |
| `BlockEntityMixin` | class | SetChangedHandlingBlockEntity | Mixin | 1 | 17 | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/BlockEntityMixin.java) |
| `CampfireBlockEntityMixin` | class | BlockEntity | Mixin | 2 | 64 | [CampfireBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/campfire/CampfireBlockEntityMixin.java) |
| `CampfireBlockEntityMixin` | class | BlockEntity | Mixin | 1 | 34 | [CampfireBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/campfire/lit/CampfireBlockEntityMixin.java) |
| `CampfireBlockEntityMixin` | class | BlockEntity | Mixin | 1 | 34 | [CampfireBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/campfire/unlit/CampfireBlockEntityMixin.java) |
| `BrewingStandBlockEntityMixin` | class | BlockEntity | Mixin | 4 | 91 | [BrewingStandBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/brewing_stand/BrewingStandBlockEntityMixin.java) |
| `HopperBlockEntityMixin` | class | BlockEntity | Mixin | 3 | 100 | [HopperBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/hopper/HopperBlockEntityMixin.java) |
| `ShulkerBoxBlockEntityMixin` | class | SleepingBlockEntity | Mixin | 2 | 63 | [ShulkerBoxBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/shulker_box/ShulkerBoxBlockEntityMixin.java) |
| `AbstractFurnaceBlockEntityMixin` | class | BlockEntity | Mixin | 4 | 86 | [AbstractFurnaceBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/furnace/AbstractFurnaceBlockEntityMixin.java) |
| `CrafterBlockEntityMixin` | class | BlockEntity | Mixin | 3 | 85 | [CrafterBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/crafter/CrafterBlockEntityMixin.java) |
| `DirectBlockEntityTickInvokerMixin` | class | WorldBorderListenerOnce | Mixin | 4 | 66 | [DirectBlockEntityTickInvokerMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/world_border/DirectBlockEntityTickInvokerMixin.java) |
| `LevelChunkTicksMixin` | class | — | Mixin | 15 | 238 | [LevelChunkTicksMixin.java](net/caffeinemc/mods/lithium/mixin/world/tick_scheduler/LevelChunkTicksMixin.java) |
| `ItemEntityAccessor` | interface | — | Mixin | 0 | 13 | [ItemEntityAccessor.java](net/caffeinemc/mods/lithium/mixin/util/accessors/ItemEntityAccessor.java) |
| `BlockEntityMixin` | class | ComparatorTracker | Mixin | 2 | 64 | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/inventory_comparator_tracking/BlockEntityMixin.java) |
| `ItemEntityMixin` | class | ChangePublisher<ItemEntity> | Mixin | 3 | 110 | [ItemEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/item_component_and_count_tracking/ItemEntityMixin.java) |
| `BaseContainerBlockEntityMixin` | class | InventoryChangeEmitter | Mixin | 1 | 136 | [BaseContainerBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/inventory_change_listening/BaseContainerBlockEntityMixin.java) |
| `BlockEntityMixin` | class | — | Mixin | 1 | 26 | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/inventory_change_listening/BlockEntityMixin.java) |
| `BlockPosMixin` | class | Vec3i | Mixin | 12 | 74 | [BlockPosMixin.java](net/caffeinemc/mods/lithium/mixin/math/fast_blockpos/BlockPosMixin.java) |
| `BlockEntityMixin` | class | SupportCache | Mixin | 3 | 41 | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/world/block_entity_ticking/support_cache/BlockEntityMixin.java) |
| `DirectBlockEntityTickInvokerMixin` | class | — | Mixin | 2 | 58 | [DirectBlockEntityTickInvokerMixin.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/world/block_entity_ticking/support_cache/DirectBlockEntityTickInvokerMixin.java) |
| `EntityMixin` | class | BlockCacheProvider | Mixin | 6 | 109 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/block_caching/block_support/EntityMixin.java) |
| `EntitySectionMixin` | class | ClimbingMobCachingSection | Mixin | 7 | 144 | [EntitySectionMixin.java](net/caffeinemc/mods/lithium/mixin/entity/collisions/unpushable_cramming/EntitySectionMixin.java) |
| `EntityCollisionContextMixin` | class | — | Mixin | 8 | 96 | [EntityCollisionContextMixin.java](net/caffeinemc/mods/lithium/mixin/shapes/lazy_shape_context/EntityCollisionContextMixin.java) |
| `InventoryAccessors` | class | LithiumInventory | — | 16 | 106 | [InventoryAccessors.java](net/caffeinemc/mods/lithium/mixin/block/hopper/InventoryAccessors.java) |
| `EntityDataAccessorMixin` | class | — | Mixin | 1 | 29 | [EntityDataAccessorMixin.java](net/caffeinemc/mods/lithium/mixin/block/hopper/EntityDataAccessorMixin.java) |
| `RedStoneWireBlockMixin` | class | Block | Mixin | 5 | 125 | [RedStoneWireBlockMixin.java](net/caffeinemc/mods/lithium/mixin/block/redstone_wire/RedStoneWireBlockMixin.java) |
| `EntityMixin` | class | — | Mixin | 6 | 85 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/alloc/deep_passengers/EntityMixin.java) |
| `GameEventDispatcherMixin` | class | — | Mixin | 3 | 79 | [GameEventDispatcherMixin.java](net/caffeinemc/mods/lithium/mixin/world/game_events/dispatch/GameEventDispatcherMixin.java) |
| `EntityMixin` | class | BlockCacheProvider | Mixin | 1 | 26 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/block_caching/EntityMixin.java) |
| `EntityMixin` | class | BlockCacheProvider | Mixin | 4 | 91 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/block_caching/fire_lava_touching/EntityMixin.java) |
| `EntityMixin` | class | BlockCacheProvider | Mixin | 3 | 56 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/block_caching/block_touching/EntityMixin.java) |
| `EntityMixin` | class | BlockCacheProvider | Mixin | 1 | 108 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/block_caching/suffocation/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 1 | 73 | [LivingEntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/equipment_tracking/LivingEntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 2 | 51 | [LivingEntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/equipment_tracking/equipment_changes/LivingEntityMixin.java) |
| `LivingEntityMixin` | class | EquipmentEntity.TickableEnchantmentT | Mixin | 2 | 65 | [LivingEntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/equipment_tracking/enchantment_ticking/LivingEntityMixin.java) |
| `EntitySectionStorageMixin` | class | — | Mixin | 4 | 80 | [EntitySectionStorageMixin.java](net/caffeinemc/mods/lithium/mixin/entity/fast_retrieval/EntitySectionStorageMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 4 | 51 | [LivingEntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/fast_powder_snow_check/LivingEntityMixin.java) |
| `HangingEntityMixin` | class | Entity | Mixin | 1 | 39 | [HangingEntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/replace_entitytype_predicates/HangingEntityMixin.java) |
| `ItemFrameMixin` | class | HangingEntity | Mixin | 1 | 34 | [ItemFrameMixin.java](net/caffeinemc/mods/lithium/mixin/entity/replace_entitytype_predicates/ItemFrameMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 15 | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/sprinting_particles/EntityMixin.java) |

## Mixin（231 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `HopperBlockEntityMixin` | `HopperBlockEntity` | [HopperBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/block/hopper/HopperBlockEntityMixin.java) |
| `ServerEntityManagerListenerMixin` | `ServerEntityManagerListener` | [ServerEntityManagerListenerMixin.java](net/caffeinemc/mods/lithium/mixin/util/entity_movement_tracking/ServerEntityManagerListenerMixin.java) |
| `TransientEntitySectionManagerAccessor` | `TransientEntitySectionManagerAccessor` | [TransientEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/util/accessors/TransientEntitySectionManagerAccessor.java) |
| `PersistentEntitySectionManagerAccessor` | `PersistentEntitySectionManagerAccessor` | [PersistentEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/util/accessors/PersistentEntitySectionManagerAccessor.java) |
| `PersistentEntitySectionManagerAccessor` | `PersistentEntitySectionManagerAccessor` | [PersistentEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/util/entity_movement_tracking/PersistentEntitySectionManagerAccessor.java) |
| `PersistentEntitySectionManagerAccessor` | `PersistentEntitySectionManagerAccessor` | [PersistentEntitySectionManagerAccessor.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/spawning/PersistentEntitySectionManagerAccessor.java) |
| `DistanceManagerMixin` | `DistanceManager` | [DistanceManagerMixin.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/world/expiring_chunk_tickets/DistanceManagerMixin.java) |
| `ClientBoundLevelChunkPacketDataAccessor` | `ClientBoundLevelChunkPacketDataAccessor` | [ClientBoundLevelChunkPacketDataAccessor.java](net/caffeinemc/mods/lithium/mixin/debug/palette/ClientBoundLevelChunkPacketDataAccessor.java) |
| `PoiManagerMixin` | `PoiManager` | [PoiManagerMixin.java](net/caffeinemc/mods/lithium/mixin/ai/poi/PoiManagerMixin.java) |
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](net/caffeinemc/mods/lithium/mixin/debug/palette/ClientPacketListenerMixin.java) |
| `PoiManagerMixin` | `PoiManager` | [PoiManagerMixin.java](net/caffeinemc/mods/lithium/mixin/ai/poi/fast_portals/PoiManagerMixin.java) |
| `ChestBlockEntityMixin` | `ChestBlockEntity` | [ChestBlockEntityMixin.java](net/caffeinemc/mods/lithium/neoforge/mixin/util/inventory_change_listening/ChestBlockEntityMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/item_entity_merging/ItemEntityMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/entity/collisions/movement/EntityMixin.java) |
| `FlowingFluidMixin` | `FlowingFluid` | [FlowingFluidMixin.java](net/caffeinemc/mods/lithium/mixin/block/fluid/flow/FlowingFluidMixin.java) |
| `ChiseledBookShelfBlockEntityMixin` | `ChiseledBookShelfBlockEntity` | [ChiseledBookShelfBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/block/hopper/ChiseledBookShelfBlockEntityMixin.java) |
| `PistonMovingBlockEntityMixin` | `PistonMovingBlockEntity` | [PistonMovingBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/block/moving_block_shapes/PistonMovingBlockEntityMixin.java) |
| `BrainMixin` | `Brain` | [BrainMixin.java](net/caffeinemc/mods/lithium/mixin/ai/task/launch/BrainMixin.java) |
| `WrappedBlockEntityTickInvokerAccessor` | `WrappedBlockEntityTickInvokerAccessor` | [WrappedBlockEntityTickInvokerAccessor.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/WrappedBlockEntityTickInvokerAccessor.java) |
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/BlockEntityMixin.java) |
| `CampfireBlockEntityMixin` | `CampfireBlockEntity` | [CampfireBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/campfire/CampfireBlockEntityMixin.java) |
| `CampfireBlockEntityMixin` | `CampfireBlockEntity` | [CampfireBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/campfire/lit/CampfireBlockEntityMixin.java) |
| `CampfireBlockEntityMixin` | `CampfireBlockEntity` | [CampfireBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/campfire/unlit/CampfireBlockEntityMixin.java) |
| `BrewingStandBlockEntityMixin` | `BrewingStandBlockEntity` | [BrewingStandBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/brewing_stand/BrewingStandBlockEntityMixin.java) |
| `HopperBlockEntityMixin` | `HopperBlockEntity` | [HopperBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/hopper/HopperBlockEntityMixin.java) |
| `ShulkerBoxBlockEntityMixin` | `ShulkerBoxBlockEntity` | [ShulkerBoxBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/shulker_box/ShulkerBoxBlockEntityMixin.java) |
| `AbstractFurnaceBlockEntityMixin` | `AbstractFurnaceBlockEntity` | [AbstractFurnaceBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/furnace/AbstractFurnaceBlockEntityMixin.java) |
| `CrafterBlockEntityMixin` | `CrafterBlockEntity` | [CrafterBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/sleeping/crafter/CrafterBlockEntityMixin.java) |
| `DirectBlockEntityTickInvokerMixin` | `DirectBlockEntityTickInvoker` | [DirectBlockEntityTickInvokerMixin.java](net/caffeinemc/mods/lithium/mixin/world/block_entity_ticking/world_border/DirectBlockEntityTickInvokerMixin.java) |
| `LevelChunkTicksMixin` | `LevelChunkTicks` | [LevelChunkTicksMixin.java](net/caffeinemc/mods/lithium/mixin/world/tick_scheduler/LevelChunkTicksMixin.java) |
| `ItemEntityAccessor` | `ItemEntityAccessor` | [ItemEntityAccessor.java](net/caffeinemc/mods/lithium/mixin/util/accessors/ItemEntityAccessor.java) |
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/inventory_comparator_tracking/BlockEntityMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/item_component_and_count_tracking/ItemEntityMixin.java) |
| `BaseContainerBlockEntityMixin` | `BaseContainerBlockEntity` | [BaseContainerBlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/inventory_change_listening/BaseContainerBlockEntityMixin.java) |
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/util/inventory_change_listening/BlockEntityMixin.java) |
| `BlockPosMixin` | `BlockPos` | [BlockPosMixin.java](net/caffeinemc/mods/lithium/mixin/math/fast_blockpos/BlockPosMixin.java) |
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/world/block_entity_ticking/support_cache/BlockEntityMixin.java) |
| `DirectBlockEntityTickInvokerMixin` | `DirectBlockEntityTickInvoker` | [DirectBlockEntityTickInvokerMixin.java](net/caffeinemc/mods/lithium/mixin/minimal_nonvanilla/world/block_entity_ticking/support_cache/DirectBlockEntityTickInvokerMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](net/caffeinemc/mods/lithium/mixin/experimental/entity/block_caching/block_support/EntityMixin.java) |
| `EntitySectionMixin` | `EntitySection` | [EntitySectionMixin.java](net/caffeinemc/mods/lithium/mixin/entity/collisions/unpushable_cramming/EntitySectionMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `LithiumConfig` | — | [LithiumConfig.java](net/caffeinemc/mods/lithium/common/config/LithiumConfig.java) |

## 按包浏览

- `net.caffeinemc.mods.lithium.api.inventory`（4 类）
- `net.caffeinemc.mods.lithium.common`（1 类）
- `net.caffeinemc.mods.lithium.common.ai`（2 类）
- `net.caffeinemc.mods.lithium.common.ai.brain`（1 类）
- `net.caffeinemc.mods.lithium.common.ai.non_poi_block_search`（4 类）
- `net.caffeinemc.mods.lithium.common.ai.pathing`（2 类）
- `net.caffeinemc.mods.lithium.common.block`（6 类）
- `net.caffeinemc.mods.lithium.common.block.entity`（5 类）
- `net.caffeinemc.mods.lithium.common.block.entity.inventory_change_tracking`（3 类）
- `net.caffeinemc.mods.lithium.common.block.entity.inventory_comparator_tracking`（2 类）
- `net.caffeinemc.mods.lithium.common.client`（1 类）
- `net.caffeinemc.mods.lithium.common.config`（2 类）
- `net.caffeinemc.mods.lithium.common.entity`（7 类）
- `net.caffeinemc.mods.lithium.common.entity.item`（2 类）
- `net.caffeinemc.mods.lithium.common.entity.movement`（1 类）
- `net.caffeinemc.mods.lithium.common.entity.projectile`（1 类）
- `net.caffeinemc.mods.lithium.common.entity.pushable`（3 类）
- `net.caffeinemc.mods.lithium.common.hopper`（9 类）
- `net.caffeinemc.mods.lithium.common.reflection`（1 类）
- `net.caffeinemc.mods.lithium.common.services`（5 类）
- `net.caffeinemc.mods.lithium.common.shapes`（9 类）
- `net.caffeinemc.mods.lithium.common.shapes.lists`（1 类）
- `net.caffeinemc.mods.lithium.common.shapes.pairs`（1 类）
- `net.caffeinemc.mods.lithium.common.tracking.block`（4 类）
- `net.caffeinemc.mods.lithium.common.tracking.entity`（7 类）
- `net.caffeinemc.mods.lithium.common.util`（6 类）
- `net.caffeinemc.mods.lithium.common.util.change_tracking`（2 类）
- `net.caffeinemc.mods.lithium.common.util.collections`（12 类）
- `net.caffeinemc.mods.lithium.common.util.deduplication`（1 类）
- `net.caffeinemc.mods.lithium.common.util.lock`（2 类）
- `net.caffeinemc.mods.lithium.common.util.math`（2 类）
- `net.caffeinemc.mods.lithium.common.util.tuples`（4 类）
- `net.caffeinemc.mods.lithium.common.world`（10 类）
- `net.caffeinemc.mods.lithium.common.world.block_pattern_matching`（2 类）
- `net.caffeinemc.mods.lithium.common.world.blockentity`（2 类）
- `net.caffeinemc.mods.lithium.common.world.blockview`（1 类）
- `net.caffeinemc.mods.lithium.common.world.chunk`（5 类）
- `net.caffeinemc.mods.lithium.common.world.chunk.heightmap`（1 类）
- `net.caffeinemc.mods.lithium.common.world.interests`（3 类）
- `net.caffeinemc.mods.lithium.common.world.interests.iterator`（3 类）
- `net.caffeinemc.mods.lithium.common.world.listeners`（2 类）
- `net.caffeinemc.mods.lithium.common.world.scheduler`（1 类）
- `net.caffeinemc.mods.lithium.mixin`（1 类）
- `net.caffeinemc.mods.lithium.mixin.ai.non_poi_block_search`（4 类）
- `net.caffeinemc.mods.lithium.mixin.ai.pathing`（7 类）
- `net.caffeinemc.mods.lithium.mixin.ai.poi`（3 类）
- `net.caffeinemc.mods.lithium.mixin.ai.poi.fast_portals`（2 类）
- `net.caffeinemc.mods.lithium.mixin.ai.poi.tasks`（2 类）
- `net.caffeinemc.mods.lithium.mixin.ai.raid`（3 类）
- `net.caffeinemc.mods.lithium.mixin.ai.sensor.secondary_poi`（1 类）
- `net.caffeinemc.mods.lithium.mixin.ai.task.launch`（1 类）
- `net.caffeinemc.mods.lithium.mixin.ai.task.memory_change_counting`（2 类）
- `net.caffeinemc.mods.lithium.mixin.ai.task.replace_streams`（2 类）
- `net.caffeinemc.mods.lithium.mixin.ai.task.run.long_jump_weighted_choice`（1 类）
- `net.caffeinemc.mods.lithium.mixin.ai.useless_sensors`（2 类）
- `net.caffeinemc.mods.lithium.mixin.ai.useless_sensors.goat_item_sensor`（1 类）
- `net.caffeinemc.mods.lithium.mixin.ai.useless_sensors.parent_animal_sensor`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.chunk_random`（2 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.chunk_ticking`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.composter`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.deep_passengers`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.entity_iteration`（2 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.entity_tracker`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.enum_values.piston_block`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.enum_values.piston_handler`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.enum_values.redstone_wire`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.explosion_behavior`（1 类）
- `net.caffeinemc.mods.lithium.mixin.alloc.nbt`（1 类）
- `net.caffeinemc.mods.lithium.mixin.block.flatten_states`（1 类）
- `net.caffeinemc.mods.lithium.mixin.block.fluid.flow`（1 类）
- `net.caffeinemc.mods.lithium.mixin.block.hopper`（16 类）
- `net.caffeinemc.mods.lithium.mixin.block.moving_block_shapes`（2 类）
- `net.caffeinemc.mods.lithium.mixin.block.redstone_wire`（1 类）
- `net.caffeinemc.mods.lithium.mixin.block_pattern_matching`（2 类）
- `net.caffeinemc.mods.lithium.mixin.cached_hashcode`（1 类）
- `net.caffeinemc.mods.lithium.mixin.chunk.entity_class_groups`（2 类）
- `net.caffeinemc.mods.lithium.mixin.chunk.no_locking`（2 类）
- `net.caffeinemc.mods.lithium.mixin.chunk.no_validation`（2 类）
- `net.caffeinemc.mods.lithium.mixin.chunk.palette`（1 类）
- `net.caffeinemc.mods.lithium.mixin.chunk.serialization`（2 类）
- `net.caffeinemc.mods.lithium.mixin.collections.attributes`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.block_entity_tickers`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.brain`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.chunk_tickets`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.entity_by_type`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.entity_filtering`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.entity_ticking`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.fluid_submersion`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.gamerules`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.goals`（1 类）
- `net.caffeinemc.mods.lithium.mixin.collections.mob_spawning`（2 类）
- `net.caffeinemc.mods.lithium.mixin.debug.palette`（3 类）
- `net.caffeinemc.mods.lithium.mixin.entity.collisions.intersection`（2 类）
- `net.caffeinemc.mods.lithium.mixin.entity.collisions.movement`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.collisions.unpushable_cramming`（6 类）
- `net.caffeinemc.mods.lithium.mixin.entity.equipment_tracking`（3 类）
- `net.caffeinemc.mods.lithium.mixin.entity.equipment_tracking.enchantment_ticking`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.equipment_tracking.equipment_changes`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.fast_elytra_check`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.fast_hand_swing`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.fast_powder_snow_check`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.fast_retrieval`（1 类）
- `net.caffeinemc.mods.lithium.mixin.entity.inactive_navigations`（7 类）
- `net.caffeinemc.mods.lithium.mixin.entity.replace_entitytype_predicates`（6 类）
- `net.caffeinemc.mods.lithium.mixin.entity.sprinting_particles`（1 类）
- `net.caffeinemc.mods.lithium.mixin.experimental.entity.block_caching`（1 类）
- `net.caffeinemc.mods.lithium.mixin.experimental.entity.block_caching.block_support`（1 类）
- `net.caffeinemc.mods.lithium.mixin.experimental.entity.block_caching.block_touching`（1 类）
- `net.caffeinemc.mods.lithium.mixin.experimental.entity.block_caching.fire_lava_touching`（1 类）
- `net.caffeinemc.mods.lithium.mixin.experimental.entity.block_caching.suffocation`（1 类）
- `net.caffeinemc.mods.lithium.mixin.experimental.entity.item_entity_merging`（1 类）
- `net.caffeinemc.mods.lithium.mixin.gen.cached_generator_settings`（1 类）
- `net.caffeinemc.mods.lithium.mixin.math.fast_blockpos`（2 类）
- `net.caffeinemc.mods.lithium.mixin.math.fast_util`（3 类）
- `net.caffeinemc.mods.lithium.mixin.math.sine_lut`（1 类）
- `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.ai.sensor.frog_attackables`（1 类）
- `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.collisions.empty_space`（3 类）
- `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.spawning`（5 类）
- `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.world.block_entity_ticking.support_cache`（3 类）
- `net.caffeinemc.mods.lithium.mixin.minimal_nonvanilla.world.expiring_chunk_tickets`（1 类）
- `net.caffeinemc.mods.lithium.mixin.profiler`（1 类）
- `net.caffeinemc.mods.lithium.mixin.shapes.blockstate_cache`（1 类）
- `net.caffeinemc.mods.lithium.mixin.shapes.lazy_shape_context`（1 类）
- `net.caffeinemc.mods.lithium.mixin.shapes.optimized_matching`（1 类）
- `net.caffeinemc.mods.lithium.mixin.shapes.precompute_shape_arrays`（2 类）
- `net.caffeinemc.mods.lithium.mixin.shapes.shape_merging`（1 类）
- `net.caffeinemc.mods.lithium.mixin.shapes.specialized_shapes`（2 类）
- `net.caffeinemc.mods.lithium.mixin.util.accessors`（7 类）
- `net.caffeinemc.mods.lithium.mixin.util.block_entity_retrieval`（1 类）
- `net.caffeinemc.mods.lithium.mixin.util.block_tracking`（3 类）
- `net.caffeinemc.mods.lithium.mixin.util.chunk_access`（2 类）
- `net.caffeinemc.mods.lithium.mixin.util.chunk_status_tracking`（2 类）
- `net.caffeinemc.mods.lithium.mixin.util.data_storage`（1 类）
- `net.caffeinemc.mods.lithium.mixin.util.entity_collection_replacement`（1 类）
- `net.caffeinemc.mods.lithium.mixin.util.entity_movement_tracking`（4 类）
- `net.caffeinemc.mods.lithium.mixin.util.entity_section_position`（2 类）
- `net.caffeinemc.mods.lithium.mixin.util.inventory_change_listening`（3 类）
- `net.caffeinemc.mods.lithium.mixin.util.inventory_comparator_tracking`（2 类）
- `net.caffeinemc.mods.lithium.mixin.util.item_component_and_count_tracking`（3 类）
- `net.caffeinemc.mods.lithium.mixin.util.world_border_listener`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.chunk_tickable`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping`（5 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.brewing_stand`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.campfire`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.campfire.lit`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.campfire.unlit`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.crafter`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.furnace`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.hopper`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.sleeping.shulker_box`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.block_entity_ticking.world_border`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.chunk_access`（4 类）
- `net.caffeinemc.mods.lithium.mixin.world.chunk_ticking.spread_ice`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.combined_heightmap_update`（2 类）
- `net.caffeinemc.mods.lithium.mixin.world.explosions.block_raycast`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.explosions.cache_exposure`（2 类）
- `net.caffeinemc.mods.lithium.mixin.world.game_events.dispatch`（2 类）
- `net.caffeinemc.mods.lithium.mixin.world.inline_block_access`（2 类）
- `net.caffeinemc.mods.lithium.mixin.world.inline_height`（2 类）
- `net.caffeinemc.mods.lithium.mixin.world.raycast`（2 类）
- `net.caffeinemc.mods.lithium.mixin.world.temperature_cache`（1 类）
- `net.caffeinemc.mods.lithium.mixin.world.tick_scheduler`（1 类）
- `net.caffeinemc.mods.lithium.neoforge`（5 类）
- `net.caffeinemc.mods.lithium.neoforge.mixin.block.hopper`（1 类）
- `net.caffeinemc.mods.lithium.neoforge.mixin.chunk_load_tricks`（1 类）
- `net.caffeinemc.mods.lithium.neoforge.mixin.startup`（1 类）
- `net.caffeinemc.mods.lithium.neoforge.mixin.util.inventory_change_listening`（1 类）
