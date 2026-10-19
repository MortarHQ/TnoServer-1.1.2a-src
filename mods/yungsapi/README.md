# YUNG's API（`yungsapi`）

> jar：`[前置]YungsApi.jar` ｜ 版本：`1.21.1-NeoForge-5.1.6` ｜ 作者：YUNGNICKYOUNG
> 规模：**149 个类 / 33 个包 / 11463 行** ｜ 事件处理类 0 ｜ Mixin 16 ｜ 注册/管理类 3

**官方描述**：API Library for YUNG's minecraft mods.

**依赖**：`minecraft`、`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.yungsapi.module` | 25 | `BlockEntityTypeModuleNeoForge`、`EntityDataSerializerModuleNeoForge`、`CommandModuleNeoForge` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.condition` | 15 | `StructureCondition`、`DepthCondition`、`ModLoadedCondition` |
| `com.yungnickyoung.minecraft.yungsapi.api.autoregister` | 12 | `AutoRegisterBlock`、`AutoRegisterEntityType`、`AutoRegisterBlockEntityType` |
| `com.yungnickyoung.minecraft.yungsapi.services` | 9 | `IBlockEntityTypeHelper`、`NeoForgeBlockEntityTypeHelper`、`NeoForgeAutoRegisterHelper` |
| `com.yungnickyoung.minecraft.yungsapi.mixin` | 8 | `NoiseChunkMixin`、`BeardifierMixin`、`NoDeltasInStructuresMixin` |
| `com.yungnickyoung.minecraft.yungsapi.mixin.accessor` | 8 | `PotionAccessor`、`BeardifierAccessor`、`StructureTemplatePoolAccessor` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.jigsaw.element` | 8 | `YungJigsawSinglePoolElement`、`YungJigsawPoolElement`、`YungJigsawFeatureElement` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.aquiferoverride` | 7 | `AquiferOverrideMask`、`AquiferOverride`、`ReplaceAquiferOverride` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.adaptations` | 6 | `EnhancedTerrainAdaptation`、`NoneAdaptation`、`CustomAdaptation` |
| `com.yungnickyoung.minecraft.yungsapi.autoregister` | 4 | `AutoRegistrationManager`、`AutoRegisterField`、`AutoRegisterEntry` |
| `com.yungnickyoung.minecraft.yungsapi.json` | 4 | `ItemAdapter`、`ItemRandomizerAdapter`、`BlockStateRandomizerAdapter` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.beardifier` | 4 | `EnhancedBeardifierHelper`、`EnhancedBeardifierData`、`EnhancedBeardifierRigid` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.action` | 4 | `StructureActionType`、`StructureAction`、`DelayGenerationAction` |
| `com.yungnickyoung.minecraft.yungsapi.noise` | 3 | `FastNoise`、`OpenSimplex2S`、`INoiseLibrary` |
| `com.yungnickyoung.minecraft.yungsapi` | 3 | `YungsApiNeoForge`、`YungsApiMixinPlugin`、`YungsApiCommon` |
| `com.yungnickyoung.minecraft.yungsapi.math` | 3 | `ColPos`、`Vector2f`、`Vector3f` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.targetselector` | 3 | `StructureTargetSelector`、`SelfTargetSelector`、`StructureTargetSelectorType` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.jigsaw.assembler` | 2 | `JigsawStructureAssembler`、`PieceContext` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.jigsaw` | 2 | `JigsawManager`、`PieceEntry` |
| `com.yungnickyoung.minecraft.yungsapi.api` | 2 | `YungJigsawManager`、`YungAutoRegister` |
| `com.yungnickyoung.minecraft.yungsapi.api.world.randomize` | 2 | `BlockStateRandomizer`、`ItemRandomizer` |
| `com.yungnickyoung.minecraft.yungsapi.world.banner` | 2 | `Banner`、`ColoredBannerPattern` |
| `com.yungnickyoung.minecraft.yungsapi.util` | 2 | `BoxOctree`、`MixinUtils` |
| `com.yungnickyoung.minecraft.yungsapi.world.util` | 2 | `BoundingBoxHelper`、`SurfaceHelper` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.context` | 1 | `StructureContext` |
| `com.yungnickyoung.minecraft.yungsapi.world.spawner` | 1 | `MobSpawnerData` |
| `com.yungnickyoung.minecraft.yungsapi.codec` | 1 | `CodecHelper` |
| `com.yungnickyoung.minecraft.yungsapi.io` | 1 | `JSON` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure` | 1 | `YungJigsawStructure` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.placement` | 1 | `EnhancedRandomSpread` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.processor` | 1 | `ISafeWorldModifier` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.exclusion` | 1 | `EnhancedExclusionZone` |
| `com.yungnickyoung.minecraft.yungsapi.world.structure.modifier` | 1 | `StructureModifier` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AutoRegistrationManager` | class | — | — | 1 | 34 | [AutoRegistrationManager.java](com/yungnickyoung/minecraft/yungsapi/autoregister/AutoRegistrationManager.java) |
| `FastNoise` | class | INoiseLibrary | — | 101 | 2737 | [FastNoise.java](com/yungnickyoung/minecraft/yungsapi/noise/FastNoise.java) |
| `YungsApiNeoForge` | class | — | Mod | 3 | 49 | [YungsApiNeoForge.java](com/yungnickyoung/minecraft/yungsapi/YungsApiNeoForge.java) |
| `NoiseChunkMixin` | class | AquiferOverrideMaskSupplier | Mixin | 6 | 92 | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoiseChunkMixin.java) |
| `BeardifierMixin` | class | EnhancedBeardifierData | Mixin | 8 | 77 | [BeardifierMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/BeardifierMixin.java) |
| `AutoRegisterBlock` | class | AutoRegisterEntry | — | 25 | 147 | [AutoRegisterBlock.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterBlock.java) |
| `AutoRegisterEntityType` | class | AutoRegisterEntry | — | 25 | 189 | [AutoRegisterEntityType.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterEntityType.java) |
| `JigsawStructureAssembler` | class | — | — | 21 | 623 | [JigsawStructureAssembler.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/assembler/JigsawStructureAssembler.java) |
| `NoDeltasInStructuresMixin` | class | — | Mixin | 1 | 25 | [NoDeltasInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoDeltasInStructuresMixin.java) |
| `FixJukeboxCrashMixin` | class | BlockEntity | Mixin | 1 | 31 | [FixJukeboxCrashMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/FixJukeboxCrashMixin.java) |
| `NoVinesInStructuresMixin` | class | — | Mixin | 1 | 33 | [NoVinesInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoVinesInStructuresMixin.java) |
| `IncreaseStructureWeightLimitMixinNeoForge` | class | — | Mixin | 1 | 22 | [IncreaseStructureWeightLimitMixinNeoForge.java](com/yungnickyoung/minecraft/yungsapi/mixin/IncreaseStructureWeightLimitMixinNeoForge.java) |
| `NoMagmaInStructuresMixin` | class | — | Mixin | 1 | 25 | [NoMagmaInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoMagmaInStructuresMixin.java) |
| `NoBasaltColumnsInStructuresMixin` | class | — | Mixin | 1 | 31 | [NoBasaltColumnsInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoBasaltColumnsInStructuresMixin.java) |
| `PotionAccessor` | interface | — | Mixin | 0 | 17 | [PotionAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/PotionAccessor.java) |
| `BeardifierAccessor` | interface | — | Mixin | 0 | 18 | [BeardifierAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/BeardifierAccessor.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 15 | [StructureTemplatePoolAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/StructureTemplatePoolAccessor.java) |
| `FeaturePoolElementAccessor` | interface | — | Mixin | 0 | 14 | [FeaturePoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/FeaturePoolElementAccessor.java) |
| `NoiseChunkAccessor` | interface | — | Mixin | 0 | 13 | [NoiseChunkAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/NoiseChunkAccessor.java) |
| `ListPoolElementAccessor` | interface | — | Mixin | 0 | 14 | [ListPoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | interface | — | Mixin | 0 | 14 | [SinglePoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/SinglePoolElementAccessor.java) |
| `BoundingBoxAccessor` | interface | — | Mixin | 0 | 27 | [BoundingBoxAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/BoundingBoxAccessor.java) |
| `JigsawManager` | class | — | — | 3 | 251 | [JigsawManager.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/JigsawManager.java) |
| `YungJigsawManager` | class | — | — | 1 | 46 | [YungJigsawManager.java](com/yungnickyoung/minecraft/yungsapi/api/YungJigsawManager.java) |
| `AutoRegisterBlockEntityType` | class | AutoRegisterEntry | — | 6 | 62 | [AutoRegisterBlockEntityType.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterBlockEntityType.java) |
| `BlockStateRandomizer` | class | — | — | 12 | 206 | [BlockStateRandomizer.java](com/yungnickyoung/minecraft/yungsapi/api/world/randomize/BlockStateRandomizer.java) |
| `ColPos` | class | ColPos | — | 28 | 176 | [ColPos.java](com/yungnickyoung/minecraft/yungsapi/math/ColPos.java) |
| `IBlockEntityTypeHelper` | interface | — | — | 0 | 11 | [IBlockEntityTypeHelper.java](com/yungnickyoung/minecraft/yungsapi/services/IBlockEntityTypeHelper.java) |
| `NeoForgeBlockEntityTypeHelper` | class | IBlockEntityTypeHelper | — | 1 | 16 | [NeoForgeBlockEntityTypeHelper.java](com/yungnickyoung/minecraft/yungsapi/services/NeoForgeBlockEntityTypeHelper.java) |
| `OpenSimplex2S` | class | INoiseLibrary | — | 13 | 445 | [OpenSimplex2S.java](com/yungnickyoung/minecraft/yungsapi/noise/OpenSimplex2S.java) |
| `ItemRandomizer` | class | — | — | 10 | 169 | [ItemRandomizer.java](com/yungnickyoung/minecraft/yungsapi/api/world/randomize/ItemRandomizer.java) |
| `StructureContext` | class | — | — | 23 | 156 | [StructureContext.java](com/yungnickyoung/minecraft/yungsapi/world/structure/context/StructureContext.java) |
| `YungJigsawSinglePoolElement` | class | YungJigsawPoolElement | MethodsReturnNonnullByDefault、Parameters | 15 | 232 | [YungJigsawSinglePoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/YungJigsawSinglePoolElement.java) |
| `BlockEntityTypeModuleNeoForge` | class | — | — | 2 | 38 | [BlockEntityTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/BlockEntityTypeModuleNeoForge.java) |
| `AutoRegisterCreativeTab` | class | AutoRegisterEntry | — | 17 | 123 | [AutoRegisterCreativeTab.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterCreativeTab.java) |
| `AutoRegisterSoundEvent` | class | AutoRegisterEntry | — | 1 | 16 | [AutoRegisterSoundEvent.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterSoundEvent.java) |
| `AutoRegisterCommand` | class | — | — | 2 | 26 | [AutoRegisterCommand.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterCommand.java) |
| `EnhancedTerrainAdaptation` | class | StringRepresentable | — | 17 | 161 | [EnhancedTerrainAdaptation.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/EnhancedTerrainAdaptation.java) |
| `PieceEntry` | class | — | — | 16 | 119 | [PieceEntry.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/PieceEntry.java) |
| `YungJigsawPoolElement` | class | StructurePoolElement | MethodsReturnNonnullByDefault、Parameters | 18 | 131 | [YungJigsawPoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/YungJigsawPoolElement.java) |
| `MobSpawnerData` | class | — | — | 13 | 119 | [MobSpawnerData.java](com/yungnickyoung/minecraft/yungsapi/world/spawner/MobSpawnerData.java) |
| `Banner` | class | — | — | 15 | 128 | [Banner.java](com/yungnickyoung/minecraft/yungsapi/world/banner/Banner.java) |
| `EntityDataSerializerModuleNeoForge` | class | — | — | 1 | 13 | [EntityDataSerializerModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/EntityDataSerializerModuleNeoForge.java) |
| `CommandModuleNeoForge` | class | — | — | 3 | 31 | [CommandModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/CommandModuleNeoForge.java) |
| `ItemModuleNeoForge` | class | — | — | 5 | 47 | [ItemModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/ItemModuleNeoForge.java) |
| `SoundEventModuleNeoForge` | class | — | — | 2 | 25 | [SoundEventModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/SoundEventModuleNeoForge.java) |
| `ItemAdapter` | class | TypeAdapter | — | 3 | 43 | [ItemAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/ItemAdapter.java) |
| `ItemRandomizerAdapter` | class | TypeAdapter | — | 2 | 63 | [ItemRandomizerAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/ItemRandomizerAdapter.java) |
| `BlockStateRandomizerAdapter` | class | TypeAdapter | — | 3 | 73 | [BlockStateRandomizerAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/BlockStateRandomizerAdapter.java) |
| `BlockStateAdapter` | class | TypeAdapter | — | 4 | 123 | [BlockStateAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/BlockStateAdapter.java) |
| `AutoRegisterItem` | class | AutoRegisterEntry | — | 1 | 16 | [AutoRegisterItem.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterItem.java) |
| `BoxOctree` | class | — | — | 6 | 225 | [BoxOctree.java](com/yungnickyoung/minecraft/yungsapi/util/BoxOctree.java) |
| `NeoForgeAutoRegisterHelper` | class | IAutoRegisterHelper | — | 5 | 184 | [NeoForgeAutoRegisterHelper.java](com/yungnickyoung/minecraft/yungsapi/services/NeoForgeAutoRegisterHelper.java) |
| `EntityTypeModuleNeoForge` | class | — | — | 3 | 46 | [EntityTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/EntityTypeModuleNeoForge.java) |
| `BlockModuleNeoForge` | class | — | — | 4 | 99 | [BlockModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/BlockModuleNeoForge.java) |
| `YungsApiMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 35 | [YungsApiMixinPlugin.java](com/yungnickyoung/minecraft/yungsapi/YungsApiMixinPlugin.java) |
| `CodecHelper` | class | — | — | 2 | 98 | [CodecHelper.java](com/yungnickyoung/minecraft/yungsapi/codec/CodecHelper.java) |
| `AutoRegisterUtils` | class | — | — | 2 | 18 | [AutoRegisterUtils.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterUtils.java) |
| `AquiferOverrideMask` | class | — | — | 6 | 40 | [AquiferOverrideMask.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/AquiferOverrideMask.java) |
| `EnhancedBeardifierHelper` | class | — | — | 3 | 188 | [EnhancedBeardifierHelper.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/beardifier/EnhancedBeardifierHelper.java) |

## Mixin（16 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `NoiseChunkMixin` | `NoiseChunk` | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoiseChunkMixin.java) |
| `BeardifierMixin` | `Beardifier` | [BeardifierMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/BeardifierMixin.java) |
| `NoDeltasInStructuresMixin` | `NoDeltasInStructures` | [NoDeltasInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoDeltasInStructuresMixin.java) |
| `FixJukeboxCrashMixin` | `FixJukeboxCrash` | [FixJukeboxCrashMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/FixJukeboxCrashMixin.java) |
| `NoVinesInStructuresMixin` | `NoVinesInStructures` | [NoVinesInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoVinesInStructuresMixin.java) |
| `IncreaseStructureWeightLimitMixinNeoForge` | `IncreaseStructureWeightLimitNeoForge` | [IncreaseStructureWeightLimitMixinNeoForge.java](com/yungnickyoung/minecraft/yungsapi/mixin/IncreaseStructureWeightLimitMixinNeoForge.java) |
| `NoMagmaInStructuresMixin` | `NoMagmaInStructures` | [NoMagmaInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoMagmaInStructuresMixin.java) |
| `NoBasaltColumnsInStructuresMixin` | `NoBasaltColumnsInStructures` | [NoBasaltColumnsInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoBasaltColumnsInStructuresMixin.java) |
| `PotionAccessor` | `PotionAccessor` | [PotionAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/PotionAccessor.java) |
| `BeardifierAccessor` | `BeardifierAccessor` | [BeardifierAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/BeardifierAccessor.java) |
| `StructureTemplatePoolAccessor` | `StructureTemplatePoolAccessor` | [StructureTemplatePoolAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/StructureTemplatePoolAccessor.java) |
| `FeaturePoolElementAccessor` | `FeaturePoolElementAccessor` | [FeaturePoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/FeaturePoolElementAccessor.java) |
| `NoiseChunkAccessor` | `NoiseChunkAccessor` | [NoiseChunkAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/NoiseChunkAccessor.java) |
| `ListPoolElementAccessor` | `ListPoolElementAccessor` | [ListPoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | `SinglePoolElementAccessor` | [SinglePoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/SinglePoolElementAccessor.java) |
| `BoundingBoxAccessor` | `BoundingBoxAccessor` | [BoundingBoxAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/BoundingBoxAccessor.java) |

## 全部类（149）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AutoRegistrationManager` | class | — | — | 1 | 34 | [AutoRegistrationManager.java](com/yungnickyoung/minecraft/yungsapi/autoregister/AutoRegistrationManager.java) |
| `FastNoise` | class | INoiseLibrary | — | 101 | 2737 | [FastNoise.java](com/yungnickyoung/minecraft/yungsapi/noise/FastNoise.java) |
| `YungsApiNeoForge` | class | — | Mod | 3 | 49 | [YungsApiNeoForge.java](com/yungnickyoung/minecraft/yungsapi/YungsApiNeoForge.java) |
| `NoiseChunkMixin` | class | AquiferOverrideMaskSupplier | Mixin | 6 | 92 | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoiseChunkMixin.java) |
| `BeardifierMixin` | class | EnhancedBeardifierData | Mixin | 8 | 77 | [BeardifierMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/BeardifierMixin.java) |
| `AutoRegisterBlock` | class | AutoRegisterEntry | — | 25 | 147 | [AutoRegisterBlock.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterBlock.java) |
| `AutoRegisterEntityType` | class | AutoRegisterEntry | — | 25 | 189 | [AutoRegisterEntityType.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterEntityType.java) |
| `JigsawStructureAssembler` | class | — | — | 21 | 623 | [JigsawStructureAssembler.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/assembler/JigsawStructureAssembler.java) |
| `NoDeltasInStructuresMixin` | class | — | Mixin | 1 | 25 | [NoDeltasInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoDeltasInStructuresMixin.java) |
| `FixJukeboxCrashMixin` | class | BlockEntity | Mixin | 1 | 31 | [FixJukeboxCrashMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/FixJukeboxCrashMixin.java) |
| `NoVinesInStructuresMixin` | class | — | Mixin | 1 | 33 | [NoVinesInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoVinesInStructuresMixin.java) |
| `IncreaseStructureWeightLimitMixinNeoForge` | class | — | Mixin | 1 | 22 | [IncreaseStructureWeightLimitMixinNeoForge.java](com/yungnickyoung/minecraft/yungsapi/mixin/IncreaseStructureWeightLimitMixinNeoForge.java) |
| `NoMagmaInStructuresMixin` | class | — | Mixin | 1 | 25 | [NoMagmaInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoMagmaInStructuresMixin.java) |
| `NoBasaltColumnsInStructuresMixin` | class | — | Mixin | 1 | 31 | [NoBasaltColumnsInStructuresMixin.java](com/yungnickyoung/minecraft/yungsapi/mixin/NoBasaltColumnsInStructuresMixin.java) |
| `PotionAccessor` | interface | — | Mixin | 0 | 17 | [PotionAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/PotionAccessor.java) |
| `BeardifierAccessor` | interface | — | Mixin | 0 | 18 | [BeardifierAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/BeardifierAccessor.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 15 | [StructureTemplatePoolAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/StructureTemplatePoolAccessor.java) |
| `FeaturePoolElementAccessor` | interface | — | Mixin | 0 | 14 | [FeaturePoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/FeaturePoolElementAccessor.java) |
| `NoiseChunkAccessor` | interface | — | Mixin | 0 | 13 | [NoiseChunkAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/NoiseChunkAccessor.java) |
| `ListPoolElementAccessor` | interface | — | Mixin | 0 | 14 | [ListPoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | interface | — | Mixin | 0 | 14 | [SinglePoolElementAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/SinglePoolElementAccessor.java) |
| `BoundingBoxAccessor` | interface | — | Mixin | 0 | 27 | [BoundingBoxAccessor.java](com/yungnickyoung/minecraft/yungsapi/mixin/accessor/BoundingBoxAccessor.java) |
| `JigsawManager` | class | — | — | 3 | 251 | [JigsawManager.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/JigsawManager.java) |
| `YungJigsawManager` | class | — | — | 1 | 46 | [YungJigsawManager.java](com/yungnickyoung/minecraft/yungsapi/api/YungJigsawManager.java) |
| `AutoRegisterBlockEntityType` | class | AutoRegisterEntry | — | 6 | 62 | [AutoRegisterBlockEntityType.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterBlockEntityType.java) |
| `BlockStateRandomizer` | class | — | — | 12 | 206 | [BlockStateRandomizer.java](com/yungnickyoung/minecraft/yungsapi/api/world/randomize/BlockStateRandomizer.java) |
| `ColPos` | class | ColPos | — | 28 | 176 | [ColPos.java](com/yungnickyoung/minecraft/yungsapi/math/ColPos.java) |
| `IBlockEntityTypeHelper` | interface | — | — | 0 | 11 | [IBlockEntityTypeHelper.java](com/yungnickyoung/minecraft/yungsapi/services/IBlockEntityTypeHelper.java) |
| `NeoForgeBlockEntityTypeHelper` | class | IBlockEntityTypeHelper | — | 1 | 16 | [NeoForgeBlockEntityTypeHelper.java](com/yungnickyoung/minecraft/yungsapi/services/NeoForgeBlockEntityTypeHelper.java) |
| `OpenSimplex2S` | class | INoiseLibrary | — | 13 | 445 | [OpenSimplex2S.java](com/yungnickyoung/minecraft/yungsapi/noise/OpenSimplex2S.java) |
| `ItemRandomizer` | class | — | — | 10 | 169 | [ItemRandomizer.java](com/yungnickyoung/minecraft/yungsapi/api/world/randomize/ItemRandomizer.java) |
| `StructureContext` | class | — | — | 23 | 156 | [StructureContext.java](com/yungnickyoung/minecraft/yungsapi/world/structure/context/StructureContext.java) |
| `YungJigsawSinglePoolElement` | class | YungJigsawPoolElement | MethodsReturnNonnullByDefault、Parameters | 15 | 232 | [YungJigsawSinglePoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/YungJigsawSinglePoolElement.java) |
| `BlockEntityTypeModuleNeoForge` | class | — | — | 2 | 38 | [BlockEntityTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/BlockEntityTypeModuleNeoForge.java) |
| `AutoRegisterCreativeTab` | class | AutoRegisterEntry | — | 17 | 123 | [AutoRegisterCreativeTab.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterCreativeTab.java) |
| `AutoRegisterSoundEvent` | class | AutoRegisterEntry | — | 1 | 16 | [AutoRegisterSoundEvent.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterSoundEvent.java) |
| `AutoRegisterCommand` | class | — | — | 2 | 26 | [AutoRegisterCommand.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterCommand.java) |
| `EnhancedTerrainAdaptation` | class | StringRepresentable | — | 17 | 161 | [EnhancedTerrainAdaptation.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/EnhancedTerrainAdaptation.java) |
| `PieceEntry` | class | — | — | 16 | 119 | [PieceEntry.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/PieceEntry.java) |
| `YungJigsawPoolElement` | class | StructurePoolElement | MethodsReturnNonnullByDefault、Parameters | 18 | 131 | [YungJigsawPoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/YungJigsawPoolElement.java) |
| `MobSpawnerData` | class | — | — | 13 | 119 | [MobSpawnerData.java](com/yungnickyoung/minecraft/yungsapi/world/spawner/MobSpawnerData.java) |
| `Banner` | class | — | — | 15 | 128 | [Banner.java](com/yungnickyoung/minecraft/yungsapi/world/banner/Banner.java) |
| `EntityDataSerializerModuleNeoForge` | class | — | — | 1 | 13 | [EntityDataSerializerModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/EntityDataSerializerModuleNeoForge.java) |
| `CommandModuleNeoForge` | class | — | — | 3 | 31 | [CommandModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/CommandModuleNeoForge.java) |
| `ItemModuleNeoForge` | class | — | — | 5 | 47 | [ItemModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/ItemModuleNeoForge.java) |
| `SoundEventModuleNeoForge` | class | — | — | 2 | 25 | [SoundEventModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/SoundEventModuleNeoForge.java) |
| `ItemAdapter` | class | TypeAdapter | — | 3 | 43 | [ItemAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/ItemAdapter.java) |
| `ItemRandomizerAdapter` | class | TypeAdapter | — | 2 | 63 | [ItemRandomizerAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/ItemRandomizerAdapter.java) |
| `BlockStateRandomizerAdapter` | class | TypeAdapter | — | 3 | 73 | [BlockStateRandomizerAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/BlockStateRandomizerAdapter.java) |
| `BlockStateAdapter` | class | TypeAdapter | — | 4 | 123 | [BlockStateAdapter.java](com/yungnickyoung/minecraft/yungsapi/json/BlockStateAdapter.java) |
| `AutoRegisterItem` | class | AutoRegisterEntry | — | 1 | 16 | [AutoRegisterItem.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterItem.java) |
| `BoxOctree` | class | — | — | 6 | 225 | [BoxOctree.java](com/yungnickyoung/minecraft/yungsapi/util/BoxOctree.java) |
| `NeoForgeAutoRegisterHelper` | class | IAutoRegisterHelper | — | 5 | 184 | [NeoForgeAutoRegisterHelper.java](com/yungnickyoung/minecraft/yungsapi/services/NeoForgeAutoRegisterHelper.java) |
| `EntityTypeModuleNeoForge` | class | — | — | 3 | 46 | [EntityTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/EntityTypeModuleNeoForge.java) |
| `BlockModuleNeoForge` | class | — | — | 4 | 99 | [BlockModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/BlockModuleNeoForge.java) |
| `YungsApiMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 35 | [YungsApiMixinPlugin.java](com/yungnickyoung/minecraft/yungsapi/YungsApiMixinPlugin.java) |
| `CodecHelper` | class | — | — | 2 | 98 | [CodecHelper.java](com/yungnickyoung/minecraft/yungsapi/codec/CodecHelper.java) |
| `AutoRegisterUtils` | class | — | — | 2 | 18 | [AutoRegisterUtils.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterUtils.java) |
| `AquiferOverrideMask` | class | — | — | 6 | 40 | [AquiferOverrideMask.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/AquiferOverrideMask.java) |
| `EnhancedBeardifierHelper` | class | — | — | 3 | 188 | [EnhancedBeardifierHelper.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/beardifier/EnhancedBeardifierHelper.java) |
| `EnhancedBeardifierData` | interface | — | — | 0 | 21 | [EnhancedBeardifierData.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/beardifier/EnhancedBeardifierData.java) |
| `YungJigsawFeatureElement` | class | YungJigsawPoolElement | MethodsReturnNonnullByDefault、Parameters | 7 | 139 | [YungJigsawFeatureElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/YungJigsawFeatureElement.java) |
| `BoundingBoxHelper` | class | — | — | 1 | 31 | [BoundingBoxHelper.java](com/yungnickyoung/minecraft/yungsapi/world/util/BoundingBoxHelper.java) |
| `SurfaceHelper` | class | — | — | 1 | 33 | [SurfaceHelper.java](com/yungnickyoung/minecraft/yungsapi/world/util/SurfaceHelper.java) |
| `MixinUtils` | class | — | — | 2 | 71 | [MixinUtils.java](com/yungnickyoung/minecraft/yungsapi/util/MixinUtils.java) |
| `JSON` | class | — | — | 7 | 67 | [JSON.java](com/yungnickyoung/minecraft/yungsapi/io/JSON.java) |
| `IAutoRegisterHelper` | interface | — | — | 0 | 19 | [IAutoRegisterHelper.java](com/yungnickyoung/minecraft/yungsapi/services/IAutoRegisterHelper.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 3 | 22 | [NeoForgePlatformHelper.java](com/yungnickyoung/minecraft/yungsapi/services/NeoForgePlatformHelper.java) |
| `IParticleTypeHelper` | interface | — | — | 0 | 8 | [IParticleTypeHelper.java](com/yungnickyoung/minecraft/yungsapi/services/IParticleTypeHelper.java) |
| `NeoForgeParticleTypeHelper` | class | IParticleTypeHelper | — | 1 | 11 | [NeoForgeParticleTypeHelper.java](com/yungnickyoung/minecraft/yungsapi/services/NeoForgeParticleTypeHelper.java) |
| `IPlatformHelper` | interface | — | — | 0 | 10 | [IPlatformHelper.java](com/yungnickyoung/minecraft/yungsapi/services/IPlatformHelper.java) |
| `PotionModuleNeoForge` | class | IBrewingRecipe | — | 7 | 79 | [PotionModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/PotionModuleNeoForge.java) |
| `YungsApiCommon` | class | — | — | 1 | 15 | [YungsApiCommon.java](com/yungnickyoung/minecraft/yungsapi/YungsApiCommon.java) |
| `AutoRegisterField` | class | — | Internal | 4 | 34 | [AutoRegisterField.java](com/yungnickyoung/minecraft/yungsapi/autoregister/AutoRegisterField.java) |
| `AutoRegisterEntry` | class | — | — | 3 | 34 | [AutoRegisterEntry.java](com/yungnickyoung/minecraft/yungsapi/autoregister/AutoRegisterEntry.java) |
| `AutoRegisterFieldRouter` | class | — | — | 1 | 66 | [AutoRegisterFieldRouter.java](com/yungnickyoung/minecraft/yungsapi/autoregister/AutoRegisterFieldRouter.java) |
| `INoiseLibrary` | interface | — | — | 0 | 6 | [INoiseLibrary.java](com/yungnickyoung/minecraft/yungsapi/noise/INoiseLibrary.java) |
| `YungAutoRegister` | class | — | — | 1 | 13 | [YungAutoRegister.java](com/yungnickyoung/minecraft/yungsapi/api/YungAutoRegister.java) |
| `AutoRegisterPotion` | class | AutoRegisterEntry | — | 3 | 33 | [AutoRegisterPotion.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterPotion.java) |
| `AutoRegister` | @interface | — | Retention、Target | 0 | 13 | [AutoRegister.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegister.java) |
| `AutoRegisterParticleType` | class | AutoRegisterEntry | — | 3 | 27 | [AutoRegisterParticleType.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterParticleType.java) |
| `AutoRegisterMobEffect` | class | AutoRegisterEntry | — | 3 | 33 | [AutoRegisterMobEffect.java](com/yungnickyoung/minecraft/yungsapi/api/autoregister/AutoRegisterMobEffect.java) |
| `YungJigsawStructure` | class | Structure | — | 4 | 164 | [YungJigsawStructure.java](com/yungnickyoung/minecraft/yungsapi/world/structure/YungJigsawStructure.java) |
| `StructureActionType` | interface | — | — | 1 | 40 | [StructureActionType.java](com/yungnickyoung/minecraft/yungsapi/world/structure/action/StructureActionType.java) |
| `StructureAction` | class | — | — | 2 | 11 | [StructureAction.java](com/yungnickyoung/minecraft/yungsapi/world/structure/action/StructureAction.java) |
| `DelayGenerationAction` | class | StructureAction | — | 2 | 21 | [DelayGenerationAction.java](com/yungnickyoung/minecraft/yungsapi/world/structure/action/DelayGenerationAction.java) |
| `TransformAction` | class | StructureAction | — | 3 | 113 | [TransformAction.java](com/yungnickyoung/minecraft/yungsapi/world/structure/action/TransformAction.java) |
| `EnhancedRandomSpread` | class | RandomSpreadStructurePlacement | — | 3 | 70 | [EnhancedRandomSpread.java](com/yungnickyoung/minecraft/yungsapi/world/structure/placement/EnhancedRandomSpread.java) |
| `NoneAdaptation` | class | EnhancedTerrainAdaptation | — | 2 | 32 | [NoneAdaptation.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/NoneAdaptation.java) |
| `CustomAdaptation` | class | EnhancedTerrainAdaptation | — | 1 | 45 | [CustomAdaptation.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/CustomAdaptation.java) |
| `SmallCarvedTopNoBeardAdaptation` | class | EnhancedTerrainAdaptation | — | 1 | 27 | [SmallCarvedTopNoBeardAdaptation.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/SmallCarvedTopNoBeardAdaptation.java) |
| `LargeCarvedTopNoBeardAdaptation` | class | EnhancedTerrainAdaptation | — | 1 | 27 | [LargeCarvedTopNoBeardAdaptation.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/LargeCarvedTopNoBeardAdaptation.java) |
| `EnhancedTerrainAdaptationType` | interface | — | — | 1 | 48 | [EnhancedTerrainAdaptationType.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/adaptations/EnhancedTerrainAdaptationType.java) |
| `AquiferOverride` | class | — | Experimental | 2 | 17 | [AquiferOverride.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/AquiferOverride.java) |
| `ReplaceAquiferOverride` | class | AquiferOverride | — | 2 | 28 | [ReplaceAquiferOverride.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/ReplaceAquiferOverride.java) |
| `AquiferOverrideMaskSupplier` | interface | — | — | 0 | 8 | [AquiferOverrideMaskSupplier.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/AquiferOverrideMaskSupplier.java) |
| `AquiferOverrideType` | interface | — | — | 1 | 41 | [AquiferOverrideType.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/AquiferOverrideType.java) |
| `NoneAquiferOverride` | class | AquiferOverride | — | 2 | 20 | [NoneAquiferOverride.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/NoneAquiferOverride.java) |
| `SolidifyAquiferOverride` | class | AquiferOverride | — | 3 | 25 | [SolidifyAquiferOverride.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/aquiferoverride/SolidifyAquiferOverride.java) |
| `EnhancedBeardifierRigid` | record | — | — | 0 | 11 | [EnhancedBeardifierRigid.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/beardifier/EnhancedBeardifierRigid.java) |
| `EnhancedJigsawJunction` | record | — | — | 0 | 8 | [EnhancedJigsawJunction.java](com/yungnickyoung/minecraft/yungsapi/world/structure/terrainadaptation/beardifier/EnhancedJigsawJunction.java) |
| `ISafeWorldModifier` | interface | — | — | 0 | 73 | [ISafeWorldModifier.java](com/yungnickyoung/minecraft/yungsapi/world/structure/processor/ISafeWorldModifier.java) |
| `EnhancedExclusionZone` | class | — | — | 1 | 38 | [EnhancedExclusionZone.java](com/yungnickyoung/minecraft/yungsapi/world/structure/exclusion/EnhancedExclusionZone.java) |
| `StructureModifier` | class | — | — | 1 | 46 | [StructureModifier.java](com/yungnickyoung/minecraft/yungsapi/world/structure/modifier/StructureModifier.java) |
| `MaxCountSinglePoolElement` | class | SinglePoolElement | — | 4 | 66 | [MaxCountSinglePoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/MaxCountSinglePoolElement.java) |
| `IMaxCountJigsawPoolElement` | interface | — | — | 0 | 8 | [IMaxCountJigsawPoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/IMaxCountJigsawPoolElement.java) |
| `MaxCountLegacySinglePoolElement` | class | LegacySinglePoolElement | — | 4 | 65 | [MaxCountLegacySinglePoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/MaxCountLegacySinglePoolElement.java) |
| `MaxCountListPoolElement` | class | ListPoolElement | — | 4 | 59 | [MaxCountListPoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/MaxCountListPoolElement.java) |
| `MaxCountFeaturePoolElement` | class | FeaturePoolElement | — | 4 | 62 | [MaxCountFeaturePoolElement.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/element/MaxCountFeaturePoolElement.java) |
| `PieceContext` | class | — | — | 1 | 55 | [PieceContext.java](com/yungnickyoung/minecraft/yungsapi/world/structure/jigsaw/assembler/PieceContext.java) |
| `StructureTargetSelector` | class | — | — | 2 | 12 | [StructureTargetSelector.java](com/yungnickyoung/minecraft/yungsapi/world/structure/targetselector/StructureTargetSelector.java) |
| `SelfTargetSelector` | class | StructureTargetSelector | — | 2 | 28 | [SelfTargetSelector.java](com/yungnickyoung/minecraft/yungsapi/world/structure/targetselector/SelfTargetSelector.java) |
| `StructureTargetSelectorType` | interface | — | — | 1 | 41 | [StructureTargetSelectorType.java](com/yungnickyoung/minecraft/yungsapi/world/structure/targetselector/StructureTargetSelectorType.java) |
| `StructureCondition` | class | — | — | 2 | 12 | [StructureCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/StructureCondition.java) |
| `DepthCondition` | class | StructureCondition | — | 2 | 38 | [DepthCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/DepthCondition.java) |
| `ModLoadedCondition` | class | StructureCondition | — | 2 | 29 | [ModLoadedCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/ModLoadedCondition.java) |
| `AltitudeCondition` | class | StructureCondition | — | 2 | 37 | [AltitudeCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/AltitudeCondition.java) |
| `RandomChanceCondition` | class | StructureCondition | — | 2 | 37 | [RandomChanceCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/RandomChanceCondition.java) |
| `AlwaysTrueCondition` | class | StructureCondition | — | 2 | 20 | [AlwaysTrueCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/AlwaysTrueCondition.java) |
| `StructureConditionType` | interface | — | — | 1 | 53 | [StructureConditionType.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/StructureConditionType.java) |
| `AllOfCondition` | class | StructureCondition | — | 2 | 29 | [AllOfCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/AllOfCondition.java) |
| `PieceInHorizontalDirectionCondition` | class | StructureCondition | — | 2 | 126 | [PieceInHorizontalDirectionCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/PieceInHorizontalDirectionCondition.java) |
| `PieceInRangeCondition` | class | StructureCondition | — | 2 | 104 | [PieceInRangeCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/PieceInRangeCondition.java) |
| `NotCondition` | class | StructureCondition | — | 2 | 28 | [NotCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/NotCondition.java) |
| `ModLoaderCondition` | class | StructureCondition | — | 2 | 32 | [ModLoaderCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/ModLoaderCondition.java) |
| `AnyOfCondition` | class | StructureCondition | — | 2 | 29 | [AnyOfCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/AnyOfCondition.java) |
| `RotationCondition` | class | StructureCondition | — | 2 | 36 | [RotationCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/RotationCondition.java) |
| `BiomeCondition` | class | StructureCondition | — | 2 | 66 | [BiomeCondition.java](com/yungnickyoung/minecraft/yungsapi/world/structure/condition/BiomeCondition.java) |
| `ColoredBannerPattern` | class | — | — | 4 | 32 | [ColoredBannerPattern.java](com/yungnickyoung/minecraft/yungsapi/world/banner/ColoredBannerPattern.java) |
| `Vector2f` | class | — | — | 3 | 32 | [Vector2f.java](com/yungnickyoung/minecraft/yungsapi/math/Vector2f.java) |
| `Vector3f` | class | — | — | 3 | 34 | [Vector3f.java](com/yungnickyoung/minecraft/yungsapi/math/Vector3f.java) |
| `Services` | class | — | — | 1 | 18 | [Services.java](com/yungnickyoung/minecraft/yungsapi/services/Services.java) |
| `MobEffectModuleNeoForge` | class | — | — | 3 | 33 | [MobEffectModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/MobEffectModuleNeoForge.java) |
| `CompostModuleNeoForge` | class | — | — | 1 | 15 | [CompostModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/CompostModuleNeoForge.java) |
| `TagModule` | class | — | — | 0 | 22 | [TagModule.java](com/yungnickyoung/minecraft/yungsapi/module/TagModule.java) |
| `StructurePlacementTypeModuleNeoForge` | class | — | — | 1 | 13 | [StructurePlacementTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/StructurePlacementTypeModuleNeoForge.java) |
| `StructureProcessorTypeModuleNeoForge` | class | — | — | 1 | 13 | [StructureProcessorTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/StructureProcessorTypeModuleNeoForge.java) |
| `StructurePlacementTypeModule` | class | — | AutoRegister | 0 | 12 | [StructurePlacementTypeModule.java](com/yungnickyoung/minecraft/yungsapi/module/StructurePlacementTypeModule.java) |
| `StructurePoolElementTypeModuleForge` | class | — | — | 1 | 13 | [StructurePoolElementTypeModuleForge.java](com/yungnickyoung/minecraft/yungsapi/module/StructurePoolElementTypeModuleForge.java) |
| `StructurePoolElementTypeModule` | class | — | AutoRegister | 0 | 27 | [StructurePoolElementTypeModule.java](com/yungnickyoung/minecraft/yungsapi/module/StructurePoolElementTypeModule.java) |
| `PlacementModifierTypeModuleNeoForge` | class | — | — | 1 | 13 | [PlacementModifierTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/PlacementModifierTypeModuleNeoForge.java) |
| `CreativeModeTabModuleNeoForge` | class | — | — | 2 | 45 | [CreativeModeTabModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/CreativeModeTabModuleNeoForge.java) |
| `StructurePieceTypeModuleNeoForge` | class | — | — | 1 | 13 | [StructurePieceTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/StructurePieceTypeModuleNeoForge.java) |
| `StructureTypeModuleNeoForge` | class | — | — | 1 | 13 | [StructureTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/StructureTypeModuleNeoForge.java) |
| `StructureTypeModule` | class | — | AutoRegister | 0 | 12 | [StructureTypeModule.java](com/yungnickyoung/minecraft/yungsapi/module/StructureTypeModule.java) |
| `PostLoadModuleNeoForge` | class | — | — | 2 | 41 | [PostLoadModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/PostLoadModuleNeoForge.java) |
| `ParticleTypeModuleNeoForge` | class | — | — | 2 | 22 | [ParticleTypeModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/ParticleTypeModuleNeoForge.java) |
| `FeatureModuleNeoForge` | class | — | — | 1 | 12 | [FeatureModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/FeatureModuleNeoForge.java) |
| `CriteriaModuleNeoForge` | class | — | — | 1 | 13 | [CriteriaModuleNeoForge.java](com/yungnickyoung/minecraft/yungsapi/module/CriteriaModuleNeoForge.java) |
