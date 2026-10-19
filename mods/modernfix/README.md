# ModernFix（`modernfix`）

> jar：`[现代化修复]modernfix.jar` ｜ 版本：`5.27.14+mc1.21.1` ｜ 作者：embeddedt
> 规模：**257 个类 / 112 个包 / 14076 行** ｜ 事件处理类 2 ｜ Mixin 153 ｜ 注册/管理类 17

**依赖**：`minecraft`、`jei`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `org.embeddedt.modernfix.util` | 18 | `ClassInfoManager`、`DummyList`、`ItemMesherMap` |
| `org.embeddedt.modernfix.common.mixin.perf.dynamic_resources` | 11 | `ModelManagerMixin`、`ItemRendererMixin`、`BlockStateModelLoaderMixin` |
| `org.embeddedt.modernfix.duck` | 10 | `IProfilingServerFunctionManager`、`IExtendedModelManager`、`IModelHoldingBlockState` |
| `org.embeddedt.modernfix.common.mixin.feature.measure_time` | 8 | `AddReloadListenerEventWrapperMixin`、`ConnectScreenMixin`、`MinecraftMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.optimize_surface_rules` | 6 | `NamespacedSurfaceRuleSourceMixin`、`BiomeManagerAccessor`、`SurfaceSystemMixin` |
| `org.embeddedt.modernfix.common.mixin.core` | 6 | `BootstrapClientMixin`、`GameDataMixin`、`BootstrapMixin` |
| `org.embeddedt.modernfix.dynamicresources` | 6 | `DynamicSoundHelpers`、`DynamicModelCache`、`ModelBakeryHelpers` |
| `org.embeddedt.modernfix.annotation` | 6 | `ClientOnlyMixin`、`RequiresFeatureLevel`、`IgnoreOutsideDev` |
| `org.embeddedt.modernfix.common.mixin.perf.encoder_cache_leak` | 5 | `ClientConfigurationPacketListenerImplMixin`、`DataComponentsAccessor`、`MinecraftMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.worldgen_allocation` | 5 | `MaterialRuleListMixin`、`NoiseChunkMixin`、`SequenceRuleMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.model_optimizations` | 5 | `SelectorMixin`、`PropertyMixin`、`BooleanPropertyMixin` |
| `org.embeddedt.modernfix.common.mixin.safety` | 4 | `LivingEntityRendererMixin`、`ItemPropertiesMixin`、`ItemColorsMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.reduce_blockstate_cache_rebuilds` | 4 | `BlockStateBaseMixin`、`BlockCallbacksMixin`、`BlockBehaviourInvoker` |
| `org.embeddedt.modernfix.common.mixin.perf.dedicated_reload_executor` | 4 | `CreateWorldScreenMixin`、`MinecraftMixin`、`WorldOpenFlowsMixin` |
| `org.embeddedt.modernfix.world.gen` | 4 | `ChunkBiomeLookup`、`PrefetchingBlockColumn`、`PositionalBiomeGetter` |
| `org.embeddedt.modernfix.resources` | 4 | `PackResourcesCacheEngine`、`ZipPackIndex`、`ICachingResourcePack` |
| `org.embeddedt.modernfix.core.config` | 3 | `ModernFixEarlyConfig`、`Option`、`OptionCategories` |
| `org.embeddedt.modernfix.common.mixin.perf.compact_mojang_registries` | 3 | `MappedRegistryMixin`、`BlockStateDataMixin`、`VanillaRegistriesMixin` |
| `org.embeddedt.modernfix.neoforge.dynresources` | 3 | `ModelBakeEventHelper`、`ModelLocationBuilder`、`IModelBakerImpl` |
| `org.embeddedt.modernfix.common.mixin.bugfix.entity_pose_stack` | 3 | `LivingEntityRendererMixin`、`PlayerRendererMixin`、`PoseStackAccessor` |
| `org.embeddedt.modernfix.common.mixin.bugfix.chunk_deadlock` | 3 | `EntityMixin`、`ServerChunkCacheAccessor`、`ChunkMapLoadMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.ingredient_item_deduplication` | 3 | `IngredientItemValueMixin`、`PatchedDataComponentMapAccessor`、`IngredientMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.cache_strongholds` | 3 | `ChunkGeneratorMixin`、`ServerLevelMixin`、`ConcentricRingsStructurePlacementMixin` |
| `org.embeddedt.modernfix.screen` | 3 | `ModernFixConfigScreen`、`OptionList`、`ModernFixOptionInfoScreen` |
| `org.embeddedt.modernfix.common.mixin.perf.resourcepacks` | 3 | `PathPackResourcesMixin`、`SharedZipFileAccessAccessor`、`FilePackResourcesMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.suspend_integrated_server_during_load` | 3 | `MinecraftMixin`、`IntegratedServerMixin`、`PlayerListMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.release_protochunks` | 3 | `ImposterProtoChunkMixin`、`ChunkHolderMixin`、`ChunkMapMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.compress_unihex_font` | 3 | `UnihexProviderShortContentsMixin`、`UnihexProviderByteContentsMixin`、`GlyphProviderTypeMixin` |
| `org.embeddedt.modernfix.render` | 3 | `SimpleItemModelView`、`FastItemRenderType`、`RenderState` |
| `org.embeddedt.modernfix.neoforge.recipe` | 3 | `IngredientItemStacksSoftReference`、`IngredientValueDeduplicator`、`ExtendedIngredient` |
| `org.embeddedt.modernfix.common.mixin.perf.dynamic_resources.ctm` | 2 | `TextureMetadataHandlerMixin`、`CTMModelBakeryAccessor` |
| `org.embeddedt.modernfix.common.mixin.bugfix.unsafe_modded_shape_caches` | 2 | `ShapeCacheCyclicMixin`、`ShapeCacheRSMixin` |
| `org.embeddedt.modernfix.neoforge.init` | 2 | `ModernFixForge`、`ModernFixClientForge` |
| `org.embeddedt.modernfix.common.mixin.feature.remove_telemetry` | 2 | `ClientTelemetryManagerMixin`、`MinecraftMixin_Telemetry` |
| `org.embeddedt.modernfix.common.mixin.perf.capability_list_compaction` | 2 | `RegisterCapabilitiesEventMixin`、`CapabilityHooksMixin` |
| `org.embeddedt.modernfix.common.mixin.feature.remove_chat_signing` | 2 | `ProfileKeyPairManagerMixin`、`ChatTrustLevelMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.faster_item_rendering` | 2 | `ItemRendererMixin`、`GameRendererMixin` |
| `org.embeddedt.modernfix.common.mixin.feature.cause_lag_by_disabling_threads` | 2 | `UtilMixin`、`ChunkRenderDispatcherMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.thread_priorities` | 2 | `UtilMixin`、`IntegratedServerMixin` |
| `org.embeddedt.modernfix.common.mixin.perf.dynamic_dfu` | 2 | `DataFixTypesMixin`、`DataFixersMixin` |
| … | | 其余 72 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientBookRegistryMixin` | class | — | ClientOnlyMixin、Mixin、RequiresMod | 1 | 75 | [ClientBookRegistryMixin.java](org/embeddedt/modernfix/common/mixin/perf/patchouli_deduplicate_books/ClientBookRegistryMixin.java) |
| `TextureMetadataHandlerMixin` | class | ModernFixClientIntegration | ClientOnlyMixin、Mixin、RequiresMod | 5 | 147 | [TextureMetadataHandlerMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ctm/TextureMetadataHandlerMixin.java) |
| `FlagManagerMixin` | class | — | Mixin、Pseudo、RequiresMod | 1 | 60 | [FlagManagerMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/cofh_core_crash/FlagManagerMixin.java) |
| `ClientProxyImplMixin` | class | ModernFixClientIntegration | ClientOnlyMixin、Mixin、RequiresMod | 3 | 115 | [ClientProxyImplMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ldlib/ClientProxyImplMixin.java) |
| `ResourceUtilMixin` | class | — | ClientOnlyMixin、Mixin、RequiresMod | 1 | 33 | [ResourceUtilMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/ctm_resourceutil_cme/ResourceUtilMixin.java) |
| `SortedArraySetMixin` | class | AbstractSet | Mixin、RequiresMod | 1 | 49 | [SortedArraySetMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/paper_chunk_patches/SortedArraySetMixin.java) |
| `ShapeCacheCyclicMixin` | class | — | Mixin、Pseudo、RequiresMod | 0 | 30 | [ShapeCacheCyclicMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/unsafe_modded_shape_caches/ShapeCacheCyclicMixin.java) |
| `ShapeCacheRSMixin` | class | — | Mixin、Pseudo、RequiresMod | 0 | 30 | [ShapeCacheRSMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/unsafe_modded_shape_caches/ShapeCacheRSMixin.java) |
| `StateHolderMixin` | class | — | Mixin、RequiresMod | 1 | 29 | [StateHolderMixin.java](org/embeddedt/modernfix/common/mixin/perf/mojang_registry_size/StateHolderMixin.java) |
| `RebuildTaskMixin` | class | — | ClientOnlyMixin、Mixin、RequiresMod | 1 | 28 | [RebuildTaskMixin.java](org/embeddedt/modernfix/common/mixin/perf/chunk_meshing/RebuildTaskMixin.java) |
| `NamespacedSurfaceRuleSourceMixin` | class | — | Mixin、RequiresFeatureLevel、RequiresMod | 0 | 96 | [NamespacedSurfaceRuleSourceMixin.java](org/embeddedt/modernfix/common/mixin/perf/optimize_surface_rules/NamespacedSurfaceRuleSourceMixin.java) |
| `StateDefinitionMixin` | class | — | Mixin、RequiresMod | 1 | 37 | [StateDefinitionMixin.java](org/embeddedt/modernfix/common/mixin/perf/state_definition_construct/StateDefinitionMixin.java) |
| `ModernFixForge` | class | — | Mod | 5 | 116 | [ModernFixForge.java](org/embeddedt/modernfix/neoforge/init/ModernFixForge.java) |
| `RecipeManagerMixin` | class | — | Mixin | 1 | 20 | [RecipeManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/datapack_reload_exceptions/RecipeManagerMixin.java) |
| `ClientConfigurationPacketListenerImplMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 18 | [ClientConfigurationPacketListenerImplMixin.java](org/embeddedt/modernfix/common/mixin/perf/encoder_cache_leak/ClientConfigurationPacketListenerImplMixin.java) |
| `ClientTelemetryManagerMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 19 | [ClientTelemetryManagerMixin.java](org/embeddedt/modernfix/common/mixin/feature/remove_telemetry/ClientTelemetryManagerMixin.java) |
| `ModernFixEarlyConfig` | class | Builder | — | 25 | 645 | [ModernFixEarlyConfig.java](org/embeddedt/modernfix/core/config/ModernFixEarlyConfig.java) |
| `MappedRegistryMixin` | class | — | IgnoreOutsideDev、Mixin | 1 | 30 | [MappedRegistryMixin.java](org/embeddedt/modernfix/common/mixin/perf/compact_mojang_registries/MappedRegistryMixin.java) |
| `RegisterCapabilitiesEventMixin` | class | ITrackingCapEvent | Mixin | 4 | 51 | [RegisterCapabilitiesEventMixin.java](org/embeddedt/modernfix/common/mixin/perf/capability_list_compaction/RegisterCapabilitiesEventMixin.java) |
| `ModelManagerMixin` | class | IExtendedModelManager | ClientOnlyMixin、Mixin | 8 | 148 | [ModelManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ModelManagerMixin.java) |
| `ReloadableResourceManagerMixin` | class | — | ClientOnlyMixin、Mixin | 2 | 43 | [ReloadableResourceManagerMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/concurrency/ReloadableResourceManagerMixin.java) |
| `ProfileKeyPairManagerMixin` | interface | — | ClientOnlyMixin、Mixin | 0 | 19 | [ProfileKeyPairManagerMixin.java](org/embeddedt/modernfix/common/mixin/feature/remove_chat_signing/ProfileKeyPairManagerMixin.java) |
| `ServerFunctionManagerMixin` | class | IProfilingServerFunctionManager | Mixin | 4 | 102 | [ServerFunctionManagerMixin.java](org/embeddedt/modernfix/common/mixin/feature/mcfunction_profiling/ServerFunctionManagerMixin.java) |
| `StructureManagerMixin` | class | — | Mixin | 1 | 36 | [StructureManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_structure_manager/StructureManagerMixin.java) |
| `SkinManagerMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 40 | [SkinManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/cache_profile_texture_url/SkinManagerMixin.java) |
| `BiomeManagerAccessor` | interface | — | Mixin | 0 | 16 | [BiomeManagerAccessor.java](org/embeddedt/modernfix/common/mixin/perf/optimize_surface_rules/BiomeManagerAccessor.java) |
| `ModelBakeEventHelper` | class | AbstractSet | — | 25 | 317 | [ModelBakeEventHelper.java](org/embeddedt/modernfix/neoforge/dynresources/ModelBakeEventHelper.java) |
| `LivingEntityRendererMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 34 | [LivingEntityRendererMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/entity_pose_stack/LivingEntityRendererMixin.java) |
| `RecipeBookSettingsMixin` | class | — | ClientOnlyMixin、Mixin | 0 | 35 | [RecipeBookSettingsMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/recipe_book_type_desync/RecipeBookSettingsMixin.java) |
| `LivingEntityRendererMixin` | class | — | ClientOnlyMixin、Mixin | 2 | 32 | [LivingEntityRendererMixin.java](org/embeddedt/modernfix/common/mixin/safety/LivingEntityRendererMixin.java) |
| `ItemRendererMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 25 | [ItemRendererMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ItemRendererMixin.java) |
| `ItemRendererMixin` | class | — | ClientOnlyMixin、Mixin | 3 | 91 | [ItemRendererMixin.java](org/embeddedt/modernfix/common/mixin/perf/faster_item_rendering/ItemRendererMixin.java) |
| `AddReloadListenerEventWrapperMixin` | class | PreparableReloadListener | Mixin | 1 | 18 | [AddReloadListenerEventWrapperMixin.java](org/embeddedt/modernfix/common/mixin/feature/measure_time/AddReloadListenerEventWrapperMixin.java) |
| `BlockStateDataMixin` | class | — | Mixin | 2 | 54 | [BlockStateDataMixin.java](org/embeddedt/modernfix/common/mixin/perf/compact_mojang_registries/BlockStateDataMixin.java) |
| `CapabilityHooksMixin` | class | — | Mixin | 1 | 25 | [CapabilityHooksMixin.java](org/embeddedt/modernfix/common/mixin/perf/capability_list_compaction/CapabilityHooksMixin.java) |
| `BlockStateBaseMixin` | class | StateHolder | Mixin | 8 | 130 | [BlockStateBaseMixin.java](org/embeddedt/modernfix/common/mixin/perf/reduce_blockstate_cache_rebuilds/BlockStateBaseMixin.java) |
| `BlockStateModelLoaderMixin` | class | IBlockStateModelLoader | ClientOnlyMixin、Mixin | 7 | 132 | [BlockStateModelLoaderMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/BlockStateModelLoaderMixin.java) |
| `ModelBakeryMixin` | class | IExtendedModelBakery | ClientOnlyMixin、Mixin | 12 | 305 | [ModelBakeryMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ModelBakeryMixin.java) |
| `ItemModelShaperMixin` | class | — | ClientOnlyMixin、Mixin | 5 | 78 | [ItemModelShaperMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ItemModelShaperMixin.java) |
| `BootstrapClientMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 19 | [BootstrapClientMixin.java](org/embeddedt/modernfix/common/mixin/core/BootstrapClientMixin.java) |
| `CreateWorldScreenMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 31 | [CreateWorldScreenMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/extra_experimental_screen/CreateWorldScreenMixin.java) |
| `PlayerRendererMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 34 | [PlayerRendererMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/entity_pose_stack/PlayerRendererMixin.java) |
| `EntityMixin` | class | — | Mixin | 1 | 29 | [EntityMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/chunk_deadlock/EntityMixin.java) |
| `EnderDragonRendererMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 28 | [EnderDragonRendererMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/ender_dragon_leak/EnderDragonRendererMixin.java) |
| `ConnectScreenMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 19 | [ConnectScreenMixin.java](org/embeddedt/modernfix/common/mixin/feature/measure_time/ConnectScreenMixin.java) |
| `ItemPropertiesMixin` | class | — | ClientOnlyMixin、Mixin | 1 | 36 | [ItemPropertiesMixin.java](org/embeddedt/modernfix/common/mixin/safety/ItemPropertiesMixin.java) |
| `ItemColorsMixin` | class | — | ClientOnlyMixin、Mixin | 2 | 27 | [ItemColorsMixin.java](org/embeddedt/modernfix/common/mixin/safety/ItemColorsMixin.java) |
| `BlockColorsMixin` | class | — | ClientOnlyMixin、Mixin | 2 | 27 | [BlockColorsMixin.java](org/embeddedt/modernfix/common/mixin/safety/BlockColorsMixin.java) |
| `IngredientItemValueMixin` | class | — | Mixin | 0 | 19 | [IngredientItemValueMixin.java](org/embeddedt/modernfix/common/mixin/perf/ingredient_item_deduplication/IngredientItemValueMixin.java) |
| `ClientLanguageMixin` | class | — | ClientOnlyMixin、Mixin | 2 | 56 | [ClientLanguageMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_languages/ClientLanguageMixin.java) |
| `WallBlockMixin` | class | Block | Mixin | 2 | 74 | [WallBlockMixin.java](org/embeddedt/modernfix/common/mixin/perf/deduplicate_wall_shapes/WallBlockMixin.java) |
| `AbstractBlockStateCacheMixin` | class | — | Mixin | 2 | 31 | [AbstractBlockStateCacheMixin.java](org/embeddedt/modernfix/common/mixin/perf/cache_blockstate_cache_arrays/AbstractBlockStateCacheMixin.java) |
| `BlockCallbacksMixin` | class | — | Mixin | 2 | 34 | [BlockCallbacksMixin.java](org/embeddedt/modernfix/common/mixin/perf/reduce_blockstate_cache_rebuilds/BlockCallbacksMixin.java) |
| `BlockBehaviourInvoker` | interface | — | Mixin | 0 | 17 | [BlockBehaviourInvoker.java](org/embeddedt/modernfix/common/mixin/perf/reduce_blockstate_cache_rebuilds/BlockBehaviourInvoker.java) |
| `BlocksMixin` | class | — | Mixin | 2 | 28 | [BlocksMixin.java](org/embeddedt/modernfix/common/mixin/perf/reduce_blockstate_cache_rebuilds/BlocksMixin.java) |
| `CreateWorldScreenMixin` | class | — | ClientOnlyMixin、Mixin | 2 | 38 | [CreateWorldScreenMixin.java](org/embeddedt/modernfix/common/mixin/perf/dedicated_reload_executor/CreateWorldScreenMixin.java) |
| `ForgeHooksClientMixin` | class | — | Mixin | 1 | 69 | [ForgeHooksClientMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ForgeHooksClientMixin.java) |
| `ItemModelMesherForgeMixin` | class | ItemModelShaper | ClientOnlyMixin、Mixin | 4 | 77 | [ItemModelMesherForgeMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ItemModelMesherForgeMixin.java) |
| `BlockStateBaseMixin` | class | IModelHoldingBlockState | ClientOnlyMixin、Mixin | 0 | 26 | [BlockStateBaseMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/BlockStateBaseMixin.java) |
| `BlockModelShaperMixin` | class | — | ClientOnlyMixin、Mixin | 3 | 75 | [BlockModelShaperMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/BlockModelShaperMixin.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ModernFixClientForge` | 7 | `org.embeddedt.modernfix.neoforge.init` | [ModernFixClientForge.java](org/embeddedt/modernfix/neoforge/init/ModernFixClientForge.java) |
| `ModernFixForge` | 2 | `org.embeddedt.modernfix.neoforge.init` | [ModernFixForge.java](org/embeddedt/modernfix/neoforge/init/ModernFixForge.java) |

## Mixin（153 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientBookRegistryMixin` | `ClientBookRegistry` | [ClientBookRegistryMixin.java](org/embeddedt/modernfix/common/mixin/perf/patchouli_deduplicate_books/ClientBookRegistryMixin.java) |
| `TextureMetadataHandlerMixin` | `TextureMetadataHandler` | [TextureMetadataHandlerMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ctm/TextureMetadataHandlerMixin.java) |
| `FlagManagerMixin` | `FlagManager` | [FlagManagerMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/cofh_core_crash/FlagManagerMixin.java) |
| `ClientProxyImplMixin` | `ClientProxyImpl` | [ClientProxyImplMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ldlib/ClientProxyImplMixin.java) |
| `ResourceUtilMixin` | `ResourceUtil` | [ResourceUtilMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/ctm_resourceutil_cme/ResourceUtilMixin.java) |
| `SortedArraySetMixin` | `SortedArraySet` | [SortedArraySetMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/paper_chunk_patches/SortedArraySetMixin.java) |
| `ShapeCacheCyclicMixin` | `ShapeCacheCyclic` | [ShapeCacheCyclicMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/unsafe_modded_shape_caches/ShapeCacheCyclicMixin.java) |
| `ShapeCacheRSMixin` | `ShapeCacheRS` | [ShapeCacheRSMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/unsafe_modded_shape_caches/ShapeCacheRSMixin.java) |
| `StateHolderMixin` | `StateHolder` | [StateHolderMixin.java](org/embeddedt/modernfix/common/mixin/perf/mojang_registry_size/StateHolderMixin.java) |
| `RebuildTaskMixin` | `RebuildTask` | [RebuildTaskMixin.java](org/embeddedt/modernfix/common/mixin/perf/chunk_meshing/RebuildTaskMixin.java) |
| `NamespacedSurfaceRuleSourceMixin` | `NamespacedSurfaceRuleSource` | [NamespacedSurfaceRuleSourceMixin.java](org/embeddedt/modernfix/common/mixin/perf/optimize_surface_rules/NamespacedSurfaceRuleSourceMixin.java) |
| `StateDefinitionMixin` | `StateDefinition` | [StateDefinitionMixin.java](org/embeddedt/modernfix/common/mixin/perf/state_definition_construct/StateDefinitionMixin.java) |
| `RecipeManagerMixin` | `RecipeManager` | [RecipeManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/datapack_reload_exceptions/RecipeManagerMixin.java) |
| `ClientConfigurationPacketListenerImplMixin` | `ClientConfigurationPacketListenerImpl` | [ClientConfigurationPacketListenerImplMixin.java](org/embeddedt/modernfix/common/mixin/perf/encoder_cache_leak/ClientConfigurationPacketListenerImplMixin.java) |
| `ClientTelemetryManagerMixin` | `ClientTelemetryManager` | [ClientTelemetryManagerMixin.java](org/embeddedt/modernfix/common/mixin/feature/remove_telemetry/ClientTelemetryManagerMixin.java) |
| `MappedRegistryMixin` | `MappedRegistry` | [MappedRegistryMixin.java](org/embeddedt/modernfix/common/mixin/perf/compact_mojang_registries/MappedRegistryMixin.java) |
| `RegisterCapabilitiesEventMixin` | `RegisterCapabilitiesEvent` | [RegisterCapabilitiesEventMixin.java](org/embeddedt/modernfix/common/mixin/perf/capability_list_compaction/RegisterCapabilitiesEventMixin.java) |
| `ModelManagerMixin` | `ModelManager` | [ModelManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ModelManagerMixin.java) |
| `ReloadableResourceManagerMixin` | `ReloadableResourceManager` | [ReloadableResourceManagerMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/concurrency/ReloadableResourceManagerMixin.java) |
| `ProfileKeyPairManagerMixin` | `ProfileKeyPairManager` | [ProfileKeyPairManagerMixin.java](org/embeddedt/modernfix/common/mixin/feature/remove_chat_signing/ProfileKeyPairManagerMixin.java) |
| `ServerFunctionManagerMixin` | `ServerFunctionManager` | [ServerFunctionManagerMixin.java](org/embeddedt/modernfix/common/mixin/feature/mcfunction_profiling/ServerFunctionManagerMixin.java) |
| `StructureManagerMixin` | `StructureManager` | [StructureManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_structure_manager/StructureManagerMixin.java) |
| `SkinManagerMixin` | `SkinManager` | [SkinManagerMixin.java](org/embeddedt/modernfix/common/mixin/perf/cache_profile_texture_url/SkinManagerMixin.java) |
| `BiomeManagerAccessor` | `BiomeManagerAccessor` | [BiomeManagerAccessor.java](org/embeddedt/modernfix/common/mixin/perf/optimize_surface_rules/BiomeManagerAccessor.java) |
| `LivingEntityRendererMixin` | `LivingEntityRenderer` | [LivingEntityRendererMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/entity_pose_stack/LivingEntityRendererMixin.java) |
| `RecipeBookSettingsMixin` | `RecipeBookSettings` | [RecipeBookSettingsMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/recipe_book_type_desync/RecipeBookSettingsMixin.java) |
| `LivingEntityRendererMixin` | `LivingEntityRenderer` | [LivingEntityRendererMixin.java](org/embeddedt/modernfix/common/mixin/safety/LivingEntityRendererMixin.java) |
| `ItemRendererMixin` | `ItemRenderer` | [ItemRendererMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ItemRendererMixin.java) |
| `ItemRendererMixin` | `ItemRenderer` | [ItemRendererMixin.java](org/embeddedt/modernfix/common/mixin/perf/faster_item_rendering/ItemRendererMixin.java) |
| `AddReloadListenerEventWrapperMixin` | `AddReloadListenerEventWrapper` | [AddReloadListenerEventWrapperMixin.java](org/embeddedt/modernfix/common/mixin/feature/measure_time/AddReloadListenerEventWrapperMixin.java) |
| `BlockStateDataMixin` | `BlockStateData` | [BlockStateDataMixin.java](org/embeddedt/modernfix/common/mixin/perf/compact_mojang_registries/BlockStateDataMixin.java) |
| `CapabilityHooksMixin` | `CapabilityHooks` | [CapabilityHooksMixin.java](org/embeddedt/modernfix/common/mixin/perf/capability_list_compaction/CapabilityHooksMixin.java) |
| `BlockStateBaseMixin` | `BlockStateBase` | [BlockStateBaseMixin.java](org/embeddedt/modernfix/common/mixin/perf/reduce_blockstate_cache_rebuilds/BlockStateBaseMixin.java) |
| `BlockStateModelLoaderMixin` | `BlockStateModelLoader` | [BlockStateModelLoaderMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/BlockStateModelLoaderMixin.java) |
| `ModelBakeryMixin` | `ModelBakery` | [ModelBakeryMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ModelBakeryMixin.java) |
| `ItemModelShaperMixin` | `ItemModelShaper` | [ItemModelShaperMixin.java](org/embeddedt/modernfix/common/mixin/perf/dynamic_resources/ItemModelShaperMixin.java) |
| `BootstrapClientMixin` | `BootstrapClient` | [BootstrapClientMixin.java](org/embeddedt/modernfix/common/mixin/core/BootstrapClientMixin.java) |
| `CreateWorldScreenMixin` | `CreateWorldScreen` | [CreateWorldScreenMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/extra_experimental_screen/CreateWorldScreenMixin.java) |
| `PlayerRendererMixin` | `PlayerRenderer` | [PlayerRendererMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/entity_pose_stack/PlayerRendererMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](org/embeddedt/modernfix/common/mixin/bugfix/chunk_deadlock/EntityMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ClientConfigurationPacketListenerImplMixin` | ClientOnlyMixin;Mixin | [ClientConfigurationPacketListenerImplMixin.java](org/embeddedt/modernfix/common/mixin/perf/encoder_cache_leak/ClientConfigurationPacketListenerImplMixin.java) |
| `ModernFixEarlyConfig` | — | [ModernFixEarlyConfig.java](org/embeddedt/modernfix/core/config/ModernFixEarlyConfig.java) |
| `ModernFixConfigScreen` | — | [ModernFixConfigScreen.java](org/embeddedt/modernfix/screen/ModernFixConfigScreen.java) |
| `ModernFixConfig` | — | [ModernFixConfig.java](org/embeddedt/modernfix/neoforge/ModernFixConfig.java) |

## 按包浏览

- `org.embeddedt.modernfix`（2 类）
- `org.embeddedt.modernfix.annotation`（6 类）
- `org.embeddedt.modernfix.api.constants`（1 类）
- `org.embeddedt.modernfix.api.entrypoint`（1 类）
- `org.embeddedt.modernfix.api.helpers`（1 类）
- `org.embeddedt.modernfix.blockstate`（2 类）
- `org.embeddedt.modernfix.chunk`（1 类）
- `org.embeddedt.modernfix.command`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.chunk_deadlock`（3 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.cofh_core_crash`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.concurrency`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.ctm_resourceutil_cme`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.ender_dragon_leak`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.entity_pose_stack`（3 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.extra_experimental_screen`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.missing_block_entities`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.paper_chunk_patches`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.recipe_book_type_desync`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.restore_old_dragon_movement`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.unsafe_modded_shape_caches`（2 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.world_leaks`（1 类）
- `org.embeddedt.modernfix.common.mixin.bugfix.world_screen_skipped`（1 类）
- `org.embeddedt.modernfix.common.mixin.core`（6 类）
- `org.embeddedt.modernfix.common.mixin.devenv`（1 类）
- `org.embeddedt.modernfix.common.mixin.feature.blockentity_incorrect_thread`（2 类）
- `org.embeddedt.modernfix.common.mixin.feature.branding`（1 类）
- `org.embeddedt.modernfix.common.mixin.feature.cause_lag_by_disabling_threads`（2 类）
- `org.embeddedt.modernfix.common.mixin.feature.mcfunction_profiling`（1 类）
- `org.embeddedt.modernfix.common.mixin.feature.measure_time`（8 类）
- `org.embeddedt.modernfix.common.mixin.feature.registry_event_progress`（1 类）
- `org.embeddedt.modernfix.common.mixin.feature.remove_chat_signing`（2 类）
- `org.embeddedt.modernfix.common.mixin.feature.remove_telemetry`（2 类）
- `org.embeddedt.modernfix.common.mixin.feature.spark_profile_world_join`（2 类）
- `org.embeddedt.modernfix.common.mixin.feature.suppress_narrator_stacktrace`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.attribute_supplier_dedup`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.cache_blockstate_cache_arrays`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.cache_profile_texture_url`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.cache_strongholds`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.capability_list_compaction`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.chunk_meshing`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.compact_bit_storage`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.compact_entity_models`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.compact_imposterprotochunks`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.compact_mojang_registries`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.compress_unihex_font`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.datapack_reload_exceptions`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.dedicated_reload_executor`（4 类）
- `org.embeddedt.modernfix.common.mixin.perf.deduplicate_wall_shapes`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.dynamic_dfu`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.dynamic_languages`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.dynamic_resources`（11 类）
- `org.embeddedt.modernfix.common.mixin.perf.dynamic_resources.ctm`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.dynamic_resources.ldlib`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.dynamic_structure_manager`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.encoder_cache_leak`（5 类）
- `org.embeddedt.modernfix.common.mixin.perf.faster_ingredients`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.faster_item_rendering`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.faster_texture_stitching`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.fix_loop_spin_waiting`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.forge_registry_alloc`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.ingredient_item_deduplication`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.lazy_search_tree_registry`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.memoize_creative_tab_build`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.model_optimizations`（5 类）
- `org.embeddedt.modernfix.common.mixin.perf.mojang_registry_size`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.optimize_surface_rules`（6 类）
- `org.embeddedt.modernfix.common.mixin.perf.patchouli_deduplicate_books`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.reduce_blockstate_cache_rebuilds`（4 类）
- `org.embeddedt.modernfix.common.mixin.perf.release_protochunks`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.remove_biome_temperature_cache`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.resourcepacks`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.smart_ingredient_sync`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.state_definition_construct`（1 类）
- `org.embeddedt.modernfix.common.mixin.perf.suspend_integrated_server_during_load`（3 类）
- `org.embeddedt.modernfix.common.mixin.perf.tag_id_caching`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.thread_priorities`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.ticking_chunk_alloc`（2 类）
- `org.embeddedt.modernfix.common.mixin.perf.worldgen_allocation`（5 类）
- `org.embeddedt.modernfix.common.mixin.safety`（4 类）
- `org.embeddedt.modernfix.core`（1 类）
- `org.embeddedt.modernfix.core.config`（3 类）
- `org.embeddedt.modernfix.core.launchplugin`（1 类）
- `org.embeddedt.modernfix.core.launchplugin.transformer`（1 类）
- `org.embeddedt.modernfix.dfu`（1 类）
- `org.embeddedt.modernfix.duck`（10 类）
- `org.embeddedt.modernfix.duck.release_protochunks`（2 类）
- `org.embeddedt.modernfix.duck.suspend_integrated_server_during_load`（1 类）
- `org.embeddedt.modernfix.dynamiclanguages`（1 类）
- `org.embeddedt.modernfix.dynamicresources`（6 类）
- `org.embeddedt.modernfix.entity`（1 类）
- `org.embeddedt.modernfix.forge.classloading`（1 类）
- `org.embeddedt.modernfix.neoforge`（1 类）
- `org.embeddedt.modernfix.neoforge.caps`（2 类）
- `org.embeddedt.modernfix.neoforge.dynresources`（3 类）
- `org.embeddedt.modernfix.neoforge.init`（2 类）
- `org.embeddedt.modernfix.neoforge.load`（2 类）
- `org.embeddedt.modernfix.neoforge.packet`（2 类）
- `org.embeddedt.modernfix.neoforge.recipe`（3 类）
- `org.embeddedt.modernfix.neoforge.util`（2 类）
- `org.embeddedt.modernfix.platform`（2 类）
- `org.embeddedt.modernfix.platform.neoforge`（1 类）
- `org.embeddedt.modernfix.registry`（1 类）
- `org.embeddedt.modernfix.render`（3 类）
- `org.embeddedt.modernfix.render.font`（2 类）
- `org.embeddedt.modernfix.resources`（4 类）
- `org.embeddedt.modernfix.screen`（3 类）
- `org.embeddedt.modernfix.spark`（1 类）
- `org.embeddedt.modernfix.textures`（1 类）
- `org.embeddedt.modernfix.util`（18 类）
- `org.embeddedt.modernfix.util.blockpos`（1 类）
- `org.embeddedt.modernfix.world`（2 类）
- `org.embeddedt.modernfix.world.gen`（4 类）
