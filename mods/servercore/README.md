# ServerCore（`servercore`）

> jar：`[优化]servercore.jar` ｜ 版本：`1.5.5+1.21.1` ｜ 作者：Wesley1808
> 规模：**412 个类 / 87 个包 / 30630 行** ｜ 事件处理类 1 ｜ Mixin 60 ｜ 注册/管理类 8

**依赖**：`minecraft`、`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `servercore_libs.net.kyori.adventure.text` | 45 | `BlockNBTComponentImpl`、`JoinConfigurationImpl`、`AbstractComponentBuilder` |
| `servercore_libs.net.kyori.adventure.text.serializer.gson` | 21 | `LegacyHoverEventSerializer`、`HoverEventActionSerializer`、`ClickEventActionSerializer` |
| `servercore_libs.net.kyori.adventure.util` | 21 | `Index`、`InheritanceAwareMapImpl`、`TriState` |
| `servercore_libs.net.kyori.adventure.text.minimessage.tag.standard` | 19 | `StandardTags`、`AbstractColorChangingTag`、`GradientTag` |
| `servercore_libs.net.kyori.adventure.text.format` | 14 | `StyleImpl`、`DecorationMap`、`TextDecoration` |
| `me.wesley1808.servercore.mixin.features.activation_range.inactive_ticks` | 13 | `ItemEntityMixin`、`LivingEntityMixin`、`VillagerMixin` |
| `servercore_libs.net.kyori.adventure.text.minimessage.tag` | 11 | `InsertingImpl`、`Tag`、`PreProcess` |
| `servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver` | 11 | `SingleResolver`、`CachingTagResolver`、`SequentialTagResolver` |
| `servercore_libs.net.kyori.adventure.text.event` | 10 | `HoverEvent`、`HoverEventSource`、`ClickEvent` |
| `servercore_libs.net.kyori.adventure.resource` | 9 | `ResourcePackRequestImpl`、`ResourcePackInfoImpl`、`ResourcePackStatus` |
| `servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer` | 9 | `StyleClaim`、`TokenEmitter`、`SerializableResolver` |
| `me.wesley1808.servercore.mixin.optimizations.sync_loads` | 8 | `DynamicGameEventListenerMixin`、`MapItemMixin`、`RemoveBlockGoalMixin` |
| `servercore_libs.net.kyori.adventure.text.minimessage` | 8 | `MiniMessageImpl`、`MiniMessageSerializer`、`ContextImpl` |
| `servercore_libs.net.kyori.adventure.key` | 8 | `KeyImpl`、`KeyedValueImpl`、`Key` |
| `servercore_libs.net.kyori.adventure.translation` | 7 | `TranslationRegistryImpl`、`TranslationRegistry`、`GlobalTranslatorImpl` |
| `servercore_libs.net.kyori.adventure.pointer` | 7 | `PointersImpl`、`PointerImpl`、`PointersSupplierImpl` |
| `servercore_libs.net.kyori.adventure.text.serializer.json` | 6 | `LegacyHoverEventSerializer`、`DummyJSONComponentSerializer`、`JSONOptions` |
| `servercore_libs.net.kyori.adventure.audience` | 6 | `EmptyAudience`、`ForwardingAudience`、`Audience` |
| `servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node` | 6 | `TagNode`、`ElementNode`、`ValueNode` |
| `me.wesley1808.servercore.common.config` | 5 | `ConfigManager`、`Config`、`OptimizationConfig` |
| `me.wesley1808.servercore.mixin.features.mob_spawning` | 5 | `NetherPortalBlockMixin`、`ZombieMixin`、`NaturalSpawnerMixin` |
| `me.wesley1808.servercore.common.utils` | 5 | `ChunkManager`、`Util`、`EntityTags` |
| `servercore_libs.net.kyori.adventure.bossbar` | 5 | `BossBarImpl`、`BossBar`、`BossBarImplementation` |
| `me.wesley1808.servercore.neoforge.common` | 4 | `ServerCoreNeoForge`、`NeoForgePermissions`、`NeoForgeMinecraftPlatform` |
| `me.wesley1808.servercore.mixin.optimizations.ticking.chunk.random` | 4 | `LiquidBlockMixin`、`ServerLevelMixin`、`LevelChunkMixin` |
| `me.wesley1808.servercore.common.dynamic` | 4 | `DynamicManager`、`SparkDynamicManager`、`DynamicSetting` |
| `servercore_libs.net.kyori.examination` | 4 | `ExaminableProperty`、`AbstractExaminer`、`Examinable` |
| `servercore_libs.net.kyori.adventure.sound` | 4 | `SoundImpl`、`SoundStopImpl`、`SoundStop` |
| `servercore_libs.net.kyori.adventure.text.minimessage.internal.parser` | 4 | `TokenParser`、`Token`、`ParsingExceptionImpl` |
| `servercore_libs.net.kyori.option` | 4 | `OptionStateImpl`、`OptionImpl`、`Option` |
| `servercore_libs.net.kyori.adventure.identity` | 4 | `IdentityImpl`、`NilIdentity`、`Identity` |
| `servercore_libs.net.kyori.adventure.chat` | 4 | `SignedMessageImpl`、`ChatTypeImpl`、`ChatType` |
| `me.wesley1808.servercore.mixin.features.activation_range.fixes` | 3 | `PistonMovingBlockEntityMixin`、`OcelotMixin`、`CatMixin` |
| `me.wesley1808.servercore.common.services` | 3 | `Events`、`Permission`、`Formatter` |
| `me.wesley1808.servercore.mixin.features.breeding_cap.tasks` | 3 | `VillagerMakeLoveMixin`、`AnimalMakeLoveMixin`、`BreedGoalMixin` |
| `me.wesley1808.servercore.mixin.features.dynamic` | 3 | `MobCategoryMixin`、`ChunkMapMixin`、`PlayerListMixin` |
| `me.wesley1808.servercore.common.config.impl.activation_range` | 3 | `ActivationRangeConfigImpl`、`ActivationTypeImpl`、`CustomActivationTypeImpl` |
| `me.wesley1808.servercore.common.commands` | 3 | `StatisticsCommand`、`ServerCoreCommand`、`MobcapsCommand` |
| `servercore_libs.net.kyori.adventure.text.renderer` | 3 | `TranslatableComponentRenderer`、`AbstractComponentRenderer`、`ComponentRenderer` |
| `me.wesley1808.servercore.common.config.impl.mob_spawning` | 3 | `MobSpawnConfigImpl`、`MobSpawnEntryImpl`、`EnforcedMobcapImpl` |
| … | | 其余 47 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ServerCoreNeoForge` | class | ServerCore | Mod | 5 | 46 | [ServerCoreNeoForge.java](me/wesley1808/servercore/neoforge/common/ServerCoreNeoForge.java) |
| `BaseCommandBlockMixin` | class | — | Mixin | 0 | 51 | [BaseCommandBlockMixin.java](me/wesley1808/servercore/mixin/optimizations/command_blocks/BaseCommandBlockMixin.java) |
| `HoverEvent` | class | Examinable | — | 73 | 575 | [HoverEvent.java](servercore_libs/net/kyori/adventure/text/event/HoverEvent.java) |
| `ItemEntityMixin` | class | — | Mixin | 0 | 16 | [ItemEntityMixin.java](me/wesley1808/servercore/mixin/features/merging/ItemEntityMixin.java) |
| `ItemEntityMixin` | class | Entity | Mixin | 0 | 46 | [ItemEntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/inactive_ticks/ItemEntityMixin.java) |
| `PistonMovingBlockEntityMixin` | class | — | Mixin | 0 | 32 | [PistonMovingBlockEntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/fixes/PistonMovingBlockEntityMixin.java) |
| `ConfigManager` | class | — | — | 6 | 127 | [ConfigManager.java](me/wesley1808/servercore/common/config/ConfigManager.java) |
| `MapItemSavedDataMixin` | class | — | Mixin | 0 | 34 | [MapItemSavedDataMixin.java](me/wesley1808/servercore/mixin/optimizations/misc/MapItemSavedDataMixin.java) |
| `DynamicGameEventListenerMixin` | class | — | Mixin | 0 | 25 | [DynamicGameEventListenerMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/DynamicGameEventListenerMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | ServerCommonPacketListenerImpl | Mixin | 1 | 102 | [ServerGamePacketListenerImplMixin.java](me/wesley1808/servercore/mixin/features/misc/ServerGamePacketListenerImplMixin.java) |
| `BlockNBTComponentImpl` | class | NBTComponentImpl | — | 34 | 376 | [BlockNBTComponentImpl.java](servercore_libs/net/kyori/adventure/text/BlockNBTComponentImpl.java) |
| `TranslationRegistryImpl` | class | Examinable | — | 15 | 157 | [TranslationRegistryImpl.java](servercore_libs/net/kyori/adventure/translation/TranslationRegistryImpl.java) |
| `LiquidBlockMixin` | class | — | Mixin | 0 | 20 | [LiquidBlockMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/LiquidBlockMixin.java) |
| `MapItemMixin` | class | — | Mixin | 0 | 29 | [MapItemMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/MapItemMixin.java) |
| `RemoveBlockGoalMixin` | class | — | Mixin | 0 | 25 | [RemoveBlockGoalMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/RemoveBlockGoalMixin.java) |
| `EntityMixin` | class | Inactive | Mixin | 0 | 139 | [EntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 0 | 24 | [LivingEntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/inactive_ticks/LivingEntityMixin.java) |
| `NetherPortalBlockMixin` | class | — | Mixin | 0 | 30 | [NetherPortalBlockMixin.java](me/wesley1808/servercore/mixin/features/mob_spawning/NetherPortalBlockMixin.java) |
| `Events` | class | — | — | 4 | 37 | [Events.java](me/wesley1808/servercore/common/services/Events.java) |
| `JoinConfigurationImpl` | class | JoinConfiguration | — | 19 | 329 | [JoinConfigurationImpl.java](servercore_libs/net/kyori/adventure/text/JoinConfigurationImpl.java) |
| `LegacyHoverEventSerializer` | interface | — | — | 0 | 20 | [LegacyHoverEventSerializer.java](servercore_libs/net/kyori/adventure/text/serializer/json/LegacyHoverEventSerializer.java) |
| `LegacyHoverEventSerializer` | interface | — | ScheduledForRemoval | 0 | 9 | [LegacyHoverEventSerializer.java](servercore_libs/net/kyori/adventure/text/serializer/gson/LegacyHoverEventSerializer.java) |
| `HoverEventSource` | interface | — | — | 0 | 21 | [HoverEventSource.java](servercore_libs/net/kyori/adventure/text/event/HoverEventSource.java) |
| `StyleImpl` | class | Style | — | 35 | 462 | [StyleImpl.java](servercore_libs/net/kyori/adventure/text/format/StyleImpl.java) |
| `CommandConfig` | interface | — | ConfSerialisers | 0 | 86 | [CommandConfig.java](me/wesley1808/servercore/common/config/data/CommandConfig.java) |
| `DynamicManager` | class | — | — | 10 | 103 | [DynamicManager.java](me/wesley1808/servercore/common/dynamic/DynamicManager.java) |
| `ChunkManager` | class | — | — | 11 | 113 | [ChunkManager.java](me/wesley1808/servercore/common/utils/ChunkManager.java) |
| `ExaminableProperty` | class | — | — | 57 | 327 | [ExaminableProperty.java](servercore_libs/net/kyori/examination/ExaminableProperty.java) |
| `ClickEvent` | class | Examinable | — | 21 | 163 | [ClickEvent.java](servercore_libs/net/kyori/adventure/text/event/ClickEvent.java) |
| `DataComponentValueConverterRegistry` | class | — | — | 4 | 179 | [DataComponentValueConverterRegistry.java](servercore_libs/net/kyori/adventure/text/event/DataComponentValueConverterRegistry.java) |
| `MinecraftServerMixin` | class | IMinecraftServer | Mixin | 0 | 37 | [MinecraftServerMixin.java](me/wesley1808/servercore/mixin/MinecraftServerMixin.java) |
| `PathFinderMixin` | class | — | Mixin | 0 | 100 | [PathFinderMixin.java](me/wesley1808/servercore/mixin/optimizations/misc/PathFinderMixin.java) |
| `ServerPlayerMixin` | class | — | Mixin | 0 | 33 | [ServerPlayerMixin.java](me/wesley1808/servercore/mixin/optimizations/players/ServerPlayerMixin.java) |
| `PlayerListMixin` | class | — | Mixin | 0 | 40 | [PlayerListMixin.java](me/wesley1808/servercore/mixin/optimizations/players/PlayerListMixin.java) |
| `ServerLevelMixin` | class | Level | Mixin | 0 | 63 | [ServerLevelMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/ServerLevelMixin.java) |
| `LevelChunkMixin` | class | ILevelChunk | Mixin | 0 | 53 | [LevelChunkMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/LevelChunkMixin.java) |
| `ServerChunkCacheMixin` | class | — | Mixin | 0 | 27 | [ServerChunkCacheMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/ServerChunkCacheMixin.java) |
| `ServerChunkCacheMixin` | class | — | Mixin | 0 | 91 | [ServerChunkCacheMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/cache/ServerChunkCacheMixin.java) |
| `ChunkHolderMixin` | class | — | Mixin | 0 | 43 | [ChunkHolderMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/broadcast/ChunkHolderMixin.java) |
| `ServerChunkCacheMixin` | class | IServerChunkCache | Mixin | 0 | 42 | [ServerChunkCacheMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/broadcast/ServerChunkCacheMixin.java) |
| `StructureCheckMixin` | class | — | Mixin | 0 | 63 | [StructureCheckMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/StructureCheckMixin.java) |
| `ServerLevelMixin` | class | Level | Mixin | 1 | 49 | [ServerLevelMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/ServerLevelMixin.java) |
| `BeeMixin` | class | Animal | Mixin | 0 | 33 | [BeeMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/BeeMixin.java) |
| `GroundPathNavigationMixin` | class | PathNavigation | Mixin | 0 | 28 | [GroundPathNavigationMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/GroundPathNavigationMixin.java) |
| `SleepInBedMixin` | class | — | Mixin | 0 | 29 | [SleepInBedMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/SleepInBedMixin.java) |
| `NaturalSpawnerMixin` | class | — | Mixin | 0 | 27 | [NaturalSpawnerMixin.java](me/wesley1808/servercore/mixin/optimizations/biome_lookups/NaturalSpawnerMixin.java) |
| `NaturalSpawnerMixin` | class | — | Mixin | 0 | 26 | [NaturalSpawnerMixin.java](me/wesley1808/servercore/mixin/optimizations/tickets/NaturalSpawnerMixin.java) |
| `ChunkGeneratorMixin` | class | — | Mixin | 0 | 26 | [ChunkGeneratorMixin.java](me/wesley1808/servercore/mixin/optimizations/tickets/ChunkGeneratorMixin.java) |
| `AllayMixin` | class | PathfinderMob | Mixin | 1 | 33 | [AllayMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/AllayMixin.java) |
| `ThrownEggMixin` | class | ThrowableItemProjectile | Mixin | 0 | 28 | [ThrownEggMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/ThrownEggMixin.java) |
| `VillagerMakeLoveMixin` | class | — | Mixin | 0 | 30 | [VillagerMakeLoveMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/tasks/VillagerMakeLoveMixin.java) |
| `AnimalMakeLoveMixin` | class | — | Mixin | 0 | 32 | [AnimalMakeLoveMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/tasks/AnimalMakeLoveMixin.java) |
| `BreedGoalMixin` | class | — | Mixin | 0 | 35 | [BreedGoalMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/tasks/BreedGoalMixin.java) |
| `MinecraftServerMixin` | class | — | Mixin | 0 | 16 | [MinecraftServerMixin.java](me/wesley1808/servercore/mixin/features/misc/MinecraftServerMixin.java) |
| `MobCategoryMixin` | class | IMobCategory | Mixin | 0 | 65 | [MobCategoryMixin.java](me/wesley1808/servercore/mixin/features/dynamic/MobCategoryMixin.java) |
| `ChunkMapMixin` | class | — | Mixin | 0 | 21 | [ChunkMapMixin.java](me/wesley1808/servercore/mixin/features/dynamic/ChunkMapMixin.java) |
| `PlayerListMixin` | class | — | Mixin | 0 | 22 | [PlayerListMixin.java](me/wesley1808/servercore/mixin/features/dynamic/PlayerListMixin.java) |
| `ExperienceOrbMixin` | class | — | Mixin | 0 | 27 | [ExperienceOrbMixin.java](me/wesley1808/servercore/mixin/features/merging/ExperienceOrbMixin.java) |
| `ServerLevelMixin` | class | — | Mixin | 0 | 79 | [ServerLevelMixin.java](me/wesley1808/servercore/mixin/features/activation_range/ServerLevelMixin.java) |
| `VillagerMixin` | class | AbstractVillager | Mixin | 2 | 31 | [VillagerMixin.java](me/wesley1808/servercore/mixin/features/activation_range/inactive_ticks/VillagerMixin.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ServerCoreNeoForge` | 5 | `me.wesley1808.servercore.neoforge.common` | [ServerCoreNeoForge.java](me/wesley1808/servercore/neoforge/common/ServerCoreNeoForge.java) |

## Mixin（60 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BaseCommandBlockMixin` | `BaseCommandBlock` | [BaseCommandBlockMixin.java](me/wesley1808/servercore/mixin/optimizations/command_blocks/BaseCommandBlockMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](me/wesley1808/servercore/mixin/features/merging/ItemEntityMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/inactive_ticks/ItemEntityMixin.java) |
| `PistonMovingBlockEntityMixin` | `PistonMovingBlockEntity` | [PistonMovingBlockEntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/fixes/PistonMovingBlockEntityMixin.java) |
| `MapItemSavedDataMixin` | `MapItemSavedData` | [MapItemSavedDataMixin.java](me/wesley1808/servercore/mixin/optimizations/misc/MapItemSavedDataMixin.java) |
| `DynamicGameEventListenerMixin` | `DynamicGameEventListener` | [DynamicGameEventListenerMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/DynamicGameEventListenerMixin.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](me/wesley1808/servercore/mixin/features/misc/ServerGamePacketListenerImplMixin.java) |
| `LiquidBlockMixin` | `LiquidBlock` | [LiquidBlockMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/LiquidBlockMixin.java) |
| `MapItemMixin` | `MapItem` | [MapItemMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/MapItemMixin.java) |
| `RemoveBlockGoalMixin` | `RemoveBlockGoal` | [RemoveBlockGoalMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/RemoveBlockGoalMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](me/wesley1808/servercore/mixin/features/activation_range/inactive_ticks/LivingEntityMixin.java) |
| `NetherPortalBlockMixin` | `NetherPortalBlock` | [NetherPortalBlockMixin.java](me/wesley1808/servercore/mixin/features/mob_spawning/NetherPortalBlockMixin.java) |
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](me/wesley1808/servercore/mixin/MinecraftServerMixin.java) |
| `PathFinderMixin` | `PathFinder` | [PathFinderMixin.java](me/wesley1808/servercore/mixin/optimizations/misc/PathFinderMixin.java) |
| `ServerPlayerMixin` | `ServerPlayer` | [ServerPlayerMixin.java](me/wesley1808/servercore/mixin/optimizations/players/ServerPlayerMixin.java) |
| `PlayerListMixin` | `PlayerList` | [PlayerListMixin.java](me/wesley1808/servercore/mixin/optimizations/players/PlayerListMixin.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/ServerLevelMixin.java) |
| `LevelChunkMixin` | `LevelChunk` | [LevelChunkMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/LevelChunkMixin.java) |
| `ServerChunkCacheMixin` | `ServerChunkCache` | [ServerChunkCacheMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/random/ServerChunkCacheMixin.java) |
| `ServerChunkCacheMixin` | `ServerChunkCache` | [ServerChunkCacheMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/cache/ServerChunkCacheMixin.java) |
| `ChunkHolderMixin` | `ChunkHolder` | [ChunkHolderMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/broadcast/ChunkHolderMixin.java) |
| `ServerChunkCacheMixin` | `ServerChunkCache` | [ServerChunkCacheMixin.java](me/wesley1808/servercore/mixin/optimizations/ticking/chunk/broadcast/ServerChunkCacheMixin.java) |
| `StructureCheckMixin` | `StructureCheck` | [StructureCheckMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/StructureCheckMixin.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/ServerLevelMixin.java) |
| `BeeMixin` | `Bee` | [BeeMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/BeeMixin.java) |
| `GroundPathNavigationMixin` | `GroundPathNavigation` | [GroundPathNavigationMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/GroundPathNavigationMixin.java) |
| `SleepInBedMixin` | `SleepInBed` | [SleepInBedMixin.java](me/wesley1808/servercore/mixin/optimizations/sync_loads/SleepInBedMixin.java) |
| `NaturalSpawnerMixin` | `NaturalSpawner` | [NaturalSpawnerMixin.java](me/wesley1808/servercore/mixin/optimizations/biome_lookups/NaturalSpawnerMixin.java) |
| `NaturalSpawnerMixin` | `NaturalSpawner` | [NaturalSpawnerMixin.java](me/wesley1808/servercore/mixin/optimizations/tickets/NaturalSpawnerMixin.java) |
| `ChunkGeneratorMixin` | `ChunkGenerator` | [ChunkGeneratorMixin.java](me/wesley1808/servercore/mixin/optimizations/tickets/ChunkGeneratorMixin.java) |
| `AllayMixin` | `Allay` | [AllayMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/AllayMixin.java) |
| `ThrownEggMixin` | `ThrownEgg` | [ThrownEggMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/ThrownEggMixin.java) |
| `VillagerMakeLoveMixin` | `VillagerMakeLove` | [VillagerMakeLoveMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/tasks/VillagerMakeLoveMixin.java) |
| `AnimalMakeLoveMixin` | `AnimalMakeLove` | [AnimalMakeLoveMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/tasks/AnimalMakeLoveMixin.java) |
| `BreedGoalMixin` | `BreedGoal` | [BreedGoalMixin.java](me/wesley1808/servercore/mixin/features/breeding_cap/tasks/BreedGoalMixin.java) |
| `MinecraftServerMixin` | `MinecraftServer` | [MinecraftServerMixin.java](me/wesley1808/servercore/mixin/features/misc/MinecraftServerMixin.java) |
| `MobCategoryMixin` | `MobCategory` | [MobCategoryMixin.java](me/wesley1808/servercore/mixin/features/dynamic/MobCategoryMixin.java) |
| `ChunkMapMixin` | `ChunkMap` | [ChunkMapMixin.java](me/wesley1808/servercore/mixin/features/dynamic/ChunkMapMixin.java) |
| `PlayerListMixin` | `PlayerList` | [PlayerListMixin.java](me/wesley1808/servercore/mixin/features/dynamic/PlayerListMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigManager` | — | [ConfigManager.java](me/wesley1808/servercore/common/config/ConfigManager.java) |
| `JoinConfigurationImpl` | — | [JoinConfigurationImpl.java](servercore_libs/net/kyori/adventure/text/JoinConfigurationImpl.java) |
| `CommandConfig` | ConfSerialisers | [CommandConfig.java](me/wesley1808/servercore/common/config/data/CommandConfig.java) |
| `ActivationRangeConfigImpl` | — | [ActivationRangeConfigImpl.java](me/wesley1808/servercore/common/config/impl/activation_range/ActivationRangeConfigImpl.java) |
| `Config` | — | [Config.java](me/wesley1808/servercore/common/config/Config.java) |
| `FeatureConfigImpl` | — | [FeatureConfigImpl.java](me/wesley1808/servercore/common/config/impl/FeatureConfigImpl.java) |
| `MainConfigImpl` | — | [MainConfigImpl.java](me/wesley1808/servercore/common/config/impl/MainConfigImpl.java) |
| `MobSpawnConfigImpl` | — | [MobSpawnConfigImpl.java](me/wesley1808/servercore/common/config/impl/mob_spawning/MobSpawnConfigImpl.java) |
| `TextReplacementConfigImpl` | — | [TextReplacementConfigImpl.java](servercore_libs/net/kyori/adventure/text/TextReplacementConfigImpl.java) |
| `OptimizationConfig` | ConfHeader | [OptimizationConfig.java](me/wesley1808/servercore/common/config/OptimizationConfig.java) |
| `MainConfig` | ConfHeader | [MainConfig.java](me/wesley1808/servercore/common/config/MainConfig.java) |
| `BreedingCapConfigImpl` | — | [BreedingCapConfigImpl.java](me/wesley1808/servercore/common/config/impl/breeding_cap/BreedingCapConfigImpl.java) |
| `DynamicConfigImpl` | — | [DynamicConfigImpl.java](me/wesley1808/servercore/common/config/impl/dynamic/DynamicConfigImpl.java) |
| `FeatureConfig` | — | [FeatureConfig.java](me/wesley1808/servercore/common/config/data/FeatureConfig.java) |
| `BreedingCapConfig` | — | [BreedingCapConfig.java](me/wesley1808/servercore/common/config/data/breeding_cap/BreedingCapConfig.java) |
| `DynamicConfig` | — | [DynamicConfig.java](me/wesley1808/servercore/common/config/data/dynamic/DynamicConfig.java) |
| `ActivationRangeConfig` | ConfSerialisers | [ActivationRangeConfig.java](me/wesley1808/servercore/common/config/data/activation_range/ActivationRangeConfig.java) |
| `MobSpawnConfig` | — | [MobSpawnConfig.java](me/wesley1808/servercore/common/config/data/mob_spawning/MobSpawnConfig.java) |
| `JoinConfiguration` | NonExtendable | [JoinConfiguration.java](servercore_libs/net/kyori/adventure/text/JoinConfiguration.java) |
| `TextReplacementConfig` | — | [TextReplacementConfig.java](servercore_libs/net/kyori/adventure/text/TextReplacementConfig.java) |

## 按包浏览

- `architectury_inject_ServerCore_common_dac59a98fb5745949a975506e0112f99_ce5a6e7ec0e65677fefb362087cc21fe12743130513f03c1f55474b2ab5166d9servercore1551211devjar`（1 类）
- `me.wesley1808.servercore.common`（1 类）
- `me.wesley1808.servercore.common.activation_range`（2 类）
- `me.wesley1808.servercore.common.collections`（1 类）
- `me.wesley1808.servercore.common.commands`（3 类）
- `me.wesley1808.servercore.common.config`（5 类）
- `me.wesley1808.servercore.common.config.data`（2 类）
- `me.wesley1808.servercore.common.config.data.activation_range`（3 类）
- `me.wesley1808.servercore.common.config.data.breeding_cap`（2 类）
- `me.wesley1808.servercore.common.config.data.dynamic`（2 类）
- `me.wesley1808.servercore.common.config.data.mob_spawning`（3 类）
- `me.wesley1808.servercore.common.config.impl`（2 类）
- `me.wesley1808.servercore.common.config.impl.activation_range`（3 类）
- `me.wesley1808.servercore.common.config.impl.breeding_cap`（2 类）
- `me.wesley1808.servercore.common.config.impl.dynamic`（2 类）
- `me.wesley1808.servercore.common.config.impl.mob_spawning`（3 类）
- `me.wesley1808.servercore.common.config.serialization`（3 类）
- `me.wesley1808.servercore.common.dynamic`（4 类）
- `me.wesley1808.servercore.common.interfaces`（2 类）
- `me.wesley1808.servercore.common.interfaces.activation_range`（2 类）
- `me.wesley1808.servercore.common.interfaces.chunk`（3 类）
- `me.wesley1808.servercore.common.services`（3 类）
- `me.wesley1808.servercore.common.services.platform`（3 类）
- `me.wesley1808.servercore.common.utils`（5 类）
- `me.wesley1808.servercore.common.utils.statistics`（3 类）
- `me.wesley1808.servercore.common.utils.statistics.entry`（2 类）
- `me.wesley1808.servercore.mixin`（2 类）
- `me.wesley1808.servercore.mixin.features.activation_range`（2 类）
- `me.wesley1808.servercore.mixin.features.activation_range.fixes`（3 类）
- `me.wesley1808.servercore.mixin.features.activation_range.inactive_ticks`（13 类）
- `me.wesley1808.servercore.mixin.features.breeding_cap`（2 类）
- `me.wesley1808.servercore.mixin.features.breeding_cap.tasks`（3 类）
- `me.wesley1808.servercore.mixin.features.dynamic`（3 类）
- `me.wesley1808.servercore.mixin.features.merging`（2 类）
- `me.wesley1808.servercore.mixin.features.misc`（2 类）
- `me.wesley1808.servercore.mixin.features.mob_spawning`（5 类）
- `me.wesley1808.servercore.mixin.features.ticking`（1 类）
- `me.wesley1808.servercore.mixin.optimizations.biome_lookups`（1 类）
- `me.wesley1808.servercore.mixin.optimizations.command_blocks`（1 类）
- `me.wesley1808.servercore.mixin.optimizations.misc`（2 类）
- `me.wesley1808.servercore.mixin.optimizations.players`（2 类）
- `me.wesley1808.servercore.mixin.optimizations.sync_loads`（8 类）
- `me.wesley1808.servercore.mixin.optimizations.tickets`（2 类）
- `me.wesley1808.servercore.mixin.optimizations.ticking.chunk.broadcast`（2 类）
- `me.wesley1808.servercore.mixin.optimizations.ticking.chunk.cache`（1 类）
- `me.wesley1808.servercore.mixin.optimizations.ticking.chunk.random`（4 类）
- `me.wesley1808.servercore.neoforge.common`（4 类）
- `servercore_libs.net.kyori.adventure`（1 类）
- `servercore_libs.net.kyori.adventure.audience`（6 类）
- `servercore_libs.net.kyori.adventure.bossbar`（5 类）
- `servercore_libs.net.kyori.adventure.builder`（1 类）
- `servercore_libs.net.kyori.adventure.chat`（4 类）
- `servercore_libs.net.kyori.adventure.identity`（4 类）
- `servercore_libs.net.kyori.adventure.internal`（1 类）
- `servercore_libs.net.kyori.adventure.internal.properties`（2 类）
- `servercore_libs.net.kyori.adventure.inventory`（2 类）
- `servercore_libs.net.kyori.adventure.key`（8 类）
- `servercore_libs.net.kyori.adventure.nbt.api`（2 类）
- `servercore_libs.net.kyori.adventure.permission`（2 类）
- `servercore_libs.net.kyori.adventure.pointer`（7 类）
- `servercore_libs.net.kyori.adventure.resource`（9 类）
- `servercore_libs.net.kyori.adventure.sound`（4 类）
- `servercore_libs.net.kyori.adventure.text`（45 类）
- `servercore_libs.net.kyori.adventure.text.event`（10 类）
- `servercore_libs.net.kyori.adventure.text.flattener`（3 类）
- `servercore_libs.net.kyori.adventure.text.format`（14 类）
- `servercore_libs.net.kyori.adventure.text.minimessage`（8 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.internal`（1 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.internal.parser`（4 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.match`（3 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.internal.parser.node`（6 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.internal.serializer`（9 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.tag`（11 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver`（11 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.tag.standard`（19 类）
- `servercore_libs.net.kyori.adventure.text.minimessage.tree`（1 类）
- `servercore_libs.net.kyori.adventure.text.renderer`（3 类）
- `servercore_libs.net.kyori.adventure.text.serializer`（3 类）
- `servercore_libs.net.kyori.adventure.text.serializer.gson`（21 类）
- `servercore_libs.net.kyori.adventure.text.serializer.gson.impl`（2 类）
- `servercore_libs.net.kyori.adventure.text.serializer.json`（6 类）
- `servercore_libs.net.kyori.adventure.title`（3 类）
- `servercore_libs.net.kyori.adventure.translation`（7 类）
- `servercore_libs.net.kyori.adventure.util`（21 类）
- `servercore_libs.net.kyori.examination`（4 类）
- `servercore_libs.net.kyori.examination.string`（3 类）
- `servercore_libs.net.kyori.option`（4 类）
