# YUNG's Cave Biomes（`yungscavebiomes`）

> jar：`[YUNG的洞穴生物群系]YungsCaveBiomes.jar` ｜ 版本：`1.21.1-NeoForge-3.1.1` ｜ 作者：YUNGNICKYOUNG, HellionGames, Sully, LudoCrypt, Jasmine, K.jpg, Neko, Frenderman, Refresh Studios
> 规模：**157 个类 / 40 个包 / 13205 行** ｜ 事件处理类 0 ｜ Mixin 38 ｜ 注册/管理类 5

**官方描述**：Adds new vanilla+ cave biomes and underground content!

**依赖**：`minecraft`、`neoforge`、`yungsapi`、`terrablender`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.yungnickyoung.minecraft.yungscavebiomes.module` | 21 | `ConfigModule`、`ConfigModuleNeoForge`、`NetworkModuleNeoForge` |
| `com.yungnickyoung.minecraft.yungscavebiomes.world.feature` | 18 | `ThreeLayerNoisySphereReplaceConfig`、`LargeIceDripstoneConfiguration`、`CeilingReplaceConfig` |
| `com.yungnickyoung.minecraft.yungscavebiomes.block` | 9 | `IcicleBlock`、`IceSheetBlock`、`PricklyPeachCactusBlock` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves` | 9 | `CrossbowItemMixin`、`EntityMixin`、`LivingEntityMixin` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor` | 8 | `StructureManagerAccessor`、`BrushableBlockEntityAccessor`、`AbstractCauldronBlockAccessor` |
| `com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal` | 8 | `GiftLootGoal`、`BuryLootGoal`、`RunFromPlayerGoal` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client` | 7 | `ClientPacketListenerMixin`、`ClientLevelMixin`、`FogRendererMixin` |
| `com.yungnickyoung.minecraft.yungscavebiomes.config` | 6 | `NeoForgeConfigOther`、`NeoForgeConfigFrostedCaves`、`NeoForgeConfigFrostedCavesNoiseParams` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves` | 5 | `DeadBushBlockMixin`、`CactusBlockMixin`、`BrushItemMixin` |
| `com.yungnickyoung.minecraft.yungscavebiomes.client.render` | 5 | `SuspiciousAncientSandBlockRenderer`、`SandSnapperRenderer`、`IceCubeRenderer` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.debug` | 4 | `DebugPacketsMixin`、`DebugRendererMixin`、`PathAccessor` |
| `com.yungnickyoung.minecraft.yungscavebiomes.client.particle` | 4 | `SandstormParticle`、`IceShatterParticle`、`FallingAncientDustParticle` |
| `com.yungnickyoung.minecraft.yungscavebiomes.criteria` | 4 | `EatPricklyPeachTrigger`、`SandstormEndTrigger`、`BreakEnchantedIceTrigger` |
| `com.yungnickyoung.minecraft.yungscavebiomes.client` | 3 | `YungsCaveBiomesClientNeoForge`、`ClientUtils`、`YungsCaveBiomesClientCommon` |
| `com.yungnickyoung.minecraft.yungscavebiomes.world.noise` | 3 | `OpenSimplex2S`、`MarbleCavesInterpolationSlideDensityFunction`、`CellularNoise` |
| `com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm` | 3 | `SandstormClientData`、`ISandstormClientDataProvider`、`SandstormFogRenderer` |
| `com.yungnickyoung.minecraft.yungscavebiomes.services` | 3 | `NeoForgePlatformHelper`、`IPlatformHelper`、`Services` |
| `com.yungnickyoung.minecraft.yungscavebiomes` | 2 | `YungsCaveBiomesNeoForge`、`YungsCaveBiomesCommon` |
| `com.yungnickyoung.minecraft.yungscavebiomes.network` | 2 | `SandstormSyncS2CPacketHandlerNeoForge`、`IcicleShatterS2CPacketHandlerNeoForge` |
| `com.yungnickyoung.minecraft.yungscavebiomes.block.entity` | 2 | `SuspiciousAncientSandBlockEntity`、`RareIceBlockEntity` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin` | 2 | `NoiseChunkMixin`、`NoiseBasedChunkGeneratorMixin` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.marble_caves` | 2 | `NoiseChunkMixin`、`AquiferMixin` |
| `com.yungnickyoung.minecraft.yungscavebiomes.client.sounds` | 2 | `LostCavesAmbientSoundsHandler`、`SandSnapperDiggingSoundInstance` |
| `com.yungnickyoung.minecraft.yungscavebiomes.command` | 2 | `DebugSpawnArmorTrimsCommand`、`SandstormCommand` |
| `com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util` | 2 | `DripstoneIceUtils`、`NoisySphereUtils` |
| `com.yungnickyoung.minecraft.yungscavebiomes.sandstorm` | 2 | `SandstormServerData`、`ISandstormServerDataProvider` |
| `com.yungnickyoung.minecraft.yungscavebiomes.network.payload` | 2 | `SandstormSyncS2CPayload`、`IcicleShatterS2CPayload` |
| `com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal` | 2 | `IceCubeAttackGoal`、`IceCubeLeapGoal` |
| `com.yungnickyoung.minecraft.yungscavebiomes.world` | 2 | `CaveBiomeRegion`、`NoiseSamplerBiomeHolder` |
| `com.yungnickyoung.minecraft.yungscavebiomes.client.model` | 2 | `SandSnapperModel`、`IceCubeModel` |
| `com.yungnickyoung.minecraft.yungscavebiomes.effect` | 2 | `FrostMobEffect`、`BuffetedEffect` |
| `com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper` | 1 | `SandSnapperEntity` |
| `com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube` | 1 | `IceCubeEntity` |
| `com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client.accessor` | 1 | `PalettedPermutationsAccessor` |
| `com.yungnickyoung.minecraft.yungscavebiomes.event` | 1 | `PlayerJoinHandler` |
| `com.yungnickyoung.minecraft.yungscavebiomes.entity` | 1 | `IcicleProjectileEntity` |
| `com.yungnickyoung.minecraft.yungscavebiomes.item` | 1 | `PricklyPeachItem` |
| `com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender` | 1 | `CaveBiomeRegionParameters` |
| `com.yungnickyoung.minecraft.yungscavebiomes.util` | 1 | `DistributionUtils` |
| `com.yungnickyoung.minecraft.yungscavebiomes.modcompat` | 1 | `YungsCaveBiomesJEIPluginNeoForge` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `YungsCaveBiomesClientNeoForge` | class | — | Mod | 4 | 63 | [YungsCaveBiomesClientNeoForge.java](com/yungnickyoung/minecraft/yungscavebiomes/client/YungsCaveBiomesClientNeoForge.java) |
| `SandSnapperEntity` | class | PathfinderMob | — | 53 | 663 | [SandSnapperEntity.java](com/yungnickyoung/minecraft/yungscavebiomes/entity/sand_snapper/SandSnapperEntity.java) |
| `StructureManagerAccessor` | interface | — | Mixin | 0 | 17 | [StructureManagerAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/StructureManagerAccessor.java) |
| `ClientPacketListenerMixin` | class | ClientCommonPacketListenerImpl | Mixin | 1 | 29 | [ClientPacketListenerMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/ClientPacketListenerMixin.java) |
| `YungsCaveBiomesNeoForge` | class | — | Mod | 0 | 24 | [YungsCaveBiomesNeoForge.java](com/yungnickyoung/minecraft/yungscavebiomes/YungsCaveBiomesNeoForge.java) |
| `BrushableBlockEntityAccessor` | interface | — | Mixin | 0 | 13 | [BrushableBlockEntityAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/BrushableBlockEntityAccessor.java) |
| `IcicleBlock` | class | Block | ParametersAreNonnullByDefault | 34 | 358 | [IcicleBlock.java](com/yungnickyoung/minecraft/yungscavebiomes/block/IcicleBlock.java) |
| `DebugPacketsMixin` | class | — | Mixin | 3 | 52 | [DebugPacketsMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/DebugPacketsMixin.java) |
| `ClientLevelMixin` | class | Level | Mixin | 5 | 71 | [ClientLevelMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/ClientLevelMixin.java) |
| `SandstormSyncS2CPacketHandlerNeoForge` | class | — | — | 1 | 12 | [SandstormSyncS2CPacketHandlerNeoForge.java](com/yungnickyoung/minecraft/yungscavebiomes/network/SandstormSyncS2CPacketHandlerNeoForge.java) |
| `IcicleShatterS2CPacketHandlerNeoForge` | class | — | — | 1 | 19 | [IcicleShatterS2CPacketHandlerNeoForge.java](com/yungnickyoung/minecraft/yungscavebiomes/network/IcicleShatterS2CPacketHandlerNeoForge.java) |
| `IceCubeEntity` | class | Monster | — | 29 | 308 | [IceCubeEntity.java](com/yungnickyoung/minecraft/yungscavebiomes/entity/ice_cube/IceCubeEntity.java) |
| `SuspiciousAncientSandBlockEntity` | class | BlockEntity | — | 16 | 265 | [SuspiciousAncientSandBlockEntity.java](com/yungnickyoung/minecraft/yungscavebiomes/block/entity/SuspiciousAncientSandBlockEntity.java) |
| `DebugRendererMixin` | class | — | Mixin | 1 | 33 | [DebugRendererMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/DebugRendererMixin.java) |
| `AbstractCauldronBlockAccessor` | interface | — | Mixin | 0 | 13 | [AbstractCauldronBlockAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/AbstractCauldronBlockAccessor.java) |
| `DeadBushBlockMixin` | class | — | Mixin | 0 | 9 | [DeadBushBlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/DeadBushBlockMixin.java) |
| `CactusBlockMixin` | class | — | Mixin | 0 | 9 | [CactusBlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/CactusBlockMixin.java) |
| `BrushItemMixin` | class | — | Mixin | 1 | 45 | [BrushItemMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/BrushItemMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 4 | 64 | [LivingEntityMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/LivingEntityMixin.java) |
| `FogRendererMixin` | class | — | Mixin | 2 | 47 | [FogRendererMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/FogRendererMixin.java) |
| `DecoratedPotRendererMixin` | class | — | Mixin | 1 | 29 | [DecoratedPotRendererMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/DecoratedPotRendererMixin.java) |
| `CrossbowItemMixin` | class | — | Mixin | 3 | 39 | [CrossbowItemMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/CrossbowItemMixin.java) |
| `EntityMixin` | class | — | Mixin | 3 | 35 | [EntityMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/EntityMixin.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 1 | 27 | [LivingEntityMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/LivingEntityMixin.java) |
| `BlockMixin` | class | — | Mixin | 1 | 34 | [BlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/BlockMixin.java) |
| `AbstractCauldronBlockMixin` | class | Block | Mixin | 3 | 43 | [AbstractCauldronBlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/AbstractCauldronBlockMixin.java) |
| `NoiseChunkMixin` | class | NoiseSamplerBiomeHolder | Mixin | 8 | 71 | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/NoiseChunkMixin.java) |
| `ServerLevelMixin` | class | Level | Mixin | 5 | 90 | [ServerLevelMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/ServerLevelMixin.java) |
| `AbstractArrowMixin` | class | Entity | Mixin | 5 | 107 | [AbstractArrowMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/AbstractArrowMixin.java) |
| `OpenSimplex2S` | class | — | — | 13 | 2789 | [OpenSimplex2S.java](com/yungnickyoung/minecraft/yungscavebiomes/world/noise/OpenSimplex2S.java) |
| `IceSheetBlock` | class | MultifaceBlock | ParametersAreNonnullByDefault | 17 | 290 | [IceSheetBlock.java](com/yungnickyoung/minecraft/yungscavebiomes/block/IceSheetBlock.java) |
| `PricklyPeachCactusBlock` | class | Block | ParametersAreNonnullByDefault | 18 | 205 | [PricklyPeachCactusBlock.java](com/yungnickyoung/minecraft/yungscavebiomes/block/PricklyPeachCactusBlock.java) |
| `NoiseBasedChunkGeneratorMixin` | class | ChunkGenerator | Mixin | 2 | 52 | [NoiseBasedChunkGeneratorMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/NoiseBasedChunkGeneratorMixin.java) |
| `PathAccessor` | interface | — | Mixin | 0 | 9 | [PathAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/PathAccessor.java) |
| `MobAccessor` | interface | — | Mixin | 0 | 9 | [MobAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/MobAccessor.java) |
| `ArrowAccessor` | interface | — | Mixin | 0 | 13 | [ArrowAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/ArrowAccessor.java) |
| `ServerLevelAccessor` | interface | — | Mixin | 0 | 13 | [ServerLevelAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/ServerLevelAccessor.java) |
| `SpawnPlacementsAccessor` | interface | — | Mixin | 0 | 19 | [SpawnPlacementsAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/SpawnPlacementsAccessor.java) |
| `NoiseChunkAccessor` | interface | — | Mixin | 0 | 16 | [NoiseChunkAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/NoiseChunkAccessor.java) |
| `AbstractArrowAccessor` | interface | — | Mixin | 0 | 12 | [AbstractArrowAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/AbstractArrowAccessor.java) |
| `SheetsMixin` | class | — | Mixin | 2 | 40 | [SheetsMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/SheetsMixin.java) |
| `LocalPlayerMixin` | class | — | Mixin | 2 | 38 | [LocalPlayerMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/LocalPlayerMixin.java) |
| `SpriteResourceLoaderMixin` | class | — | Mixin | 1 | 40 | [SpriteResourceLoaderMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/SpriteResourceLoaderMixin.java) |
| `PalettedPermutationsAccessor` | interface | — | Mixin | 0 | 22 | [PalettedPermutationsAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/accessor/PalettedPermutationsAccessor.java) |
| `NoiseChunkMixin` | class | NoiseSamplerBiomeHolder | Mixin | 0 | 10 | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/marble_caves/NoiseChunkMixin.java) |
| `AquiferMixin` | class | — | Mixin | 0 | 9 | [AquiferMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/marble_caves/AquiferMixin.java) |
| `SkeletonMixin` | class | AbstractSkeleton | Mixin | 1 | 26 | [SkeletonMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/SkeletonMixin.java) |
| `ThrownPotionMixin` | class | ThrowableItemProjectile | Mixin | 1 | 89 | [ThrownPotionMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/ThrownPotionMixin.java) |
| `AquiferMixin` | class | Aquifer | Mixin | 1 | 48 | [AquiferMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/AquiferMixin.java) |
| `GiftLootGoal` | class | Goal | — | 10 | 172 | [GiftLootGoal.java](com/yungnickyoung/minecraft/yungscavebiomes/entity/sand_snapper/goal/GiftLootGoal.java) |
| `BuryLootGoal` | class | Goal | — | 10 | 189 | [BuryLootGoal.java](com/yungnickyoung/minecraft/yungscavebiomes/entity/sand_snapper/goal/BuryLootGoal.java) |
| `PlayerJoinHandler` | class | — | — | 1 | 17 | [PlayerJoinHandler.java](com/yungnickyoung/minecraft/yungscavebiomes/event/PlayerJoinHandler.java) |
| `ThreeLayerNoisySphereReplaceConfig` | class | FeatureConfiguration | — | 5 | 113 | [ThreeLayerNoisySphereReplaceConfig.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/ThreeLayerNoisySphereReplaceConfig.java) |
| `LostCavesAmbientSoundsHandler` | class | AmbientSoundHandler | — | 2 | 71 | [LostCavesAmbientSoundsHandler.java](com/yungnickyoung/minecraft/yungscavebiomes/client/sounds/LostCavesAmbientSoundsHandler.java) |
| `SandstormClientData` | class | — | — | 10 | 159 | [SandstormClientData.java](com/yungnickyoung/minecraft/yungscavebiomes/client/render/sandstorm/SandstormClientData.java) |
| `RareIceBlockEntity` | class | BlockEntity | — | 1 | 29 | [RareIceBlockEntity.java](com/yungnickyoung/minecraft/yungscavebiomes/block/entity/RareIceBlockEntity.java) |
| `NeoForgeConfigOther` | class | — | — | 0 | 59 | [NeoForgeConfigOther.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigOther.java) |
| `NeoForgeConfigFrostedCaves` | class | — | — | 0 | 14 | [NeoForgeConfigFrostedCaves.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigFrostedCaves.java) |
| `NeoForgeConfigFrostedCavesNoiseParams` | class | — | — | 0 | 39 | [NeoForgeConfigFrostedCavesNoiseParams.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigFrostedCavesNoiseParams.java) |
| `NeoForgeConfigLostCaves` | class | — | — | 0 | 27 | [NeoForgeConfigLostCaves.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigLostCaves.java) |

## Mixin（38 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StructureManagerAccessor` | `StructureManagerAccessor` | [StructureManagerAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/StructureManagerAccessor.java) |
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/ClientPacketListenerMixin.java) |
| `BrushableBlockEntityAccessor` | `BrushableBlockEntityAccessor` | [BrushableBlockEntityAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/BrushableBlockEntityAccessor.java) |
| `DebugPacketsMixin` | `DebugPackets` | [DebugPacketsMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/DebugPacketsMixin.java) |
| `ClientLevelMixin` | `ClientLevel` | [ClientLevelMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/ClientLevelMixin.java) |
| `DebugRendererMixin` | `DebugRenderer` | [DebugRendererMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/DebugRendererMixin.java) |
| `AbstractCauldronBlockAccessor` | `AbstractCauldronBlockAccessor` | [AbstractCauldronBlockAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/AbstractCauldronBlockAccessor.java) |
| `DeadBushBlockMixin` | `DeadBushBlock` | [DeadBushBlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/DeadBushBlockMixin.java) |
| `CactusBlockMixin` | `CactusBlock` | [CactusBlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/CactusBlockMixin.java) |
| `BrushItemMixin` | `BrushItem` | [BrushItemMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/BrushItemMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/LivingEntityMixin.java) |
| `FogRendererMixin` | `FogRenderer` | [FogRendererMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/FogRendererMixin.java) |
| `DecoratedPotRendererMixin` | `DecoratedPotRenderer` | [DecoratedPotRendererMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/DecoratedPotRendererMixin.java) |
| `CrossbowItemMixin` | `CrossbowItem` | [CrossbowItemMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/CrossbowItemMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/EntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/LivingEntityMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/BlockMixin.java) |
| `AbstractCauldronBlockMixin` | `AbstractCauldronBlock` | [AbstractCauldronBlockMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/AbstractCauldronBlockMixin.java) |
| `NoiseChunkMixin` | `NoiseChunk` | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/NoiseChunkMixin.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/ServerLevelMixin.java) |
| `AbstractArrowMixin` | `AbstractArrow` | [AbstractArrowMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/AbstractArrowMixin.java) |
| `NoiseBasedChunkGeneratorMixin` | `NoiseBasedChunkGenerator` | [NoiseBasedChunkGeneratorMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/NoiseBasedChunkGeneratorMixin.java) |
| `PathAccessor` | `PathAccessor` | [PathAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/PathAccessor.java) |
| `MobAccessor` | `MobAccessor` | [MobAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/debug/MobAccessor.java) |
| `ArrowAccessor` | `ArrowAccessor` | [ArrowAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/ArrowAccessor.java) |
| `ServerLevelAccessor` | `ServerLevelAccessor` | [ServerLevelAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/ServerLevelAccessor.java) |
| `SpawnPlacementsAccessor` | `SpawnPlacementsAccessor` | [SpawnPlacementsAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/SpawnPlacementsAccessor.java) |
| `NoiseChunkAccessor` | `NoiseChunkAccessor` | [NoiseChunkAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/NoiseChunkAccessor.java) |
| `AbstractArrowAccessor` | `AbstractArrowAccessor` | [AbstractArrowAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/accessor/AbstractArrowAccessor.java) |
| `SheetsMixin` | `Sheets` | [SheetsMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/SheetsMixin.java) |
| `LocalPlayerMixin` | `LocalPlayer` | [LocalPlayerMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/LocalPlayerMixin.java) |
| `SpriteResourceLoaderMixin` | `SpriteResourceLoader` | [SpriteResourceLoaderMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/SpriteResourceLoaderMixin.java) |
| `PalettedPermutationsAccessor` | `PalettedPermutationsAccessor` | [PalettedPermutationsAccessor.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/accessor/PalettedPermutationsAccessor.java) |
| `NoiseChunkMixin` | `NoiseChunk` | [NoiseChunkMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/marble_caves/NoiseChunkMixin.java) |
| `AquiferMixin` | `Aquifer` | [AquiferMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/marble_caves/AquiferMixin.java) |
| `SkeletonMixin` | `Skeleton` | [SkeletonMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/SkeletonMixin.java) |
| `ThrownPotionMixin` | `ThrownPotion` | [ThrownPotionMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/ThrownPotionMixin.java) |
| `AquiferMixin` | `Aquifer` | [AquiferMixin.java](com/yungnickyoung/minecraft/yungscavebiomes/mixin/frosted_caves/AquiferMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ThreeLayerNoisySphereReplaceConfig` | — | [ThreeLayerNoisySphereReplaceConfig.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/ThreeLayerNoisySphereReplaceConfig.java) |
| `NeoForgeConfigOther` | — | [NeoForgeConfigOther.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigOther.java) |
| `NeoForgeConfigFrostedCaves` | — | [NeoForgeConfigFrostedCaves.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigFrostedCaves.java) |
| `NeoForgeConfigFrostedCavesNoiseParams` | — | [NeoForgeConfigFrostedCavesNoiseParams.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigFrostedCavesNoiseParams.java) |
| `NeoForgeConfigLostCaves` | — | [NeoForgeConfigLostCaves.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigLostCaves.java) |
| `YCBNeoForgeConfig` | — | [YCBNeoForgeConfig.java](com/yungnickyoung/minecraft/yungscavebiomes/config/YCBNeoForgeConfig.java) |
| `NeoForgeConfigLostCavesNoiseParams` | — | [NeoForgeConfigLostCavesNoiseParams.java](com/yungnickyoung/minecraft/yungscavebiomes/config/NeoForgeConfigLostCavesNoiseParams.java) |
| `LargeIceDripstoneConfiguration` | — | [LargeIceDripstoneConfiguration.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/LargeIceDripstoneConfiguration.java) |
| `CeilingReplaceConfig` | — | [CeilingReplaceConfig.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/CeilingReplaceConfig.java) |
| `NoisySphereReplaceConfig` | — | [NoisySphereReplaceConfig.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/NoisySphereReplaceConfig.java) |
| `FloorReplaceConfig` | — | [FloorReplaceConfig.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/FloorReplaceConfig.java) |
| `IceSheetConfiguration` | — | [IceSheetConfiguration.java](com/yungnickyoung/minecraft/yungscavebiomes/world/feature/IceSheetConfiguration.java) |
| `ConfigModule` | — | [ConfigModule.java](com/yungnickyoung/minecraft/yungscavebiomes/module/ConfigModule.java) |
| `ConfigModuleNeoForge` | — | [ConfigModuleNeoForge.java](com/yungnickyoung/minecraft/yungscavebiomes/module/ConfigModuleNeoForge.java) |

## 按包浏览

- `com.yungnickyoung.minecraft.yungscavebiomes`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.block`（9 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.block.entity`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.client`（3 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.client.model`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.client.particle`（4 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.client.render`（5 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm`（3 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.client.sounds`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.command`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.config`（6 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.criteria`（4 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.effect`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.entity`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal`（8 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.event`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.item`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor`（8 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.debug`（4 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves`（9 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves`（5 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client`（7 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client.accessor`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.mixin.marble_caves`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.modcompat`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.module`（21 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.network`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.network.payload`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.sandstorm`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.services`（3 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.util`（1 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.world`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.world.feature`（18 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util`（2 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.world.noise`（3 类）
- `com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender`（1 类）
