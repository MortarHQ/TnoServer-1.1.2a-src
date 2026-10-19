# Cult of Azazel（`netherman`）

> jar：`[阿撒兹勒教团]cultofazazelneo.jar` ｜ 版本：`1.1.4.12` ｜ 作者：Benj1kus
> 规模：**246 个类 / 23 个包 / 21544 行** ｜ 事件处理类 13 ｜ Mixin 0 ｜ 注册/管理类 13

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.benji.netherman.client.model` | 41 | `VoidNetherMidModel`、`StatueBossunitModel`、`StatueModel` |
| `com.benji.netherman.client.renderer` | 37 | `AzazelWingTrails`、`AltarRenderer`、`FacePuzzleLeftDownRenderer` |
| `com.benji.netherman.common.entity` | 37 | `AzazelEntity`、`AzazelHumanEntity`、`BelieverVillagerEntity` |
| `com.benji.netherman.common.block` | 37 | `AltarBlock`、`CrimsonWebBlock`、`StatueStandBlock` |
| `com.benji.netherman.common.block.entity` | 17 | `MazeDoorBlockEntity`、`GrandDoorBlockEntity`、`FacePuzzleBlockEntity` |
| `com.benji.netherman.common.item` | 16 | `GeoBlockItem`、`AzazelArmorItem`、`AzazelScytheItem` |
| `com.benji.netherman.init` | 12 | `ModBlocks`、`ModRecipeSerializers`、`ModItems` |
| `com.benji.netherman.client.layer` | 8 | `GhastlyHintLayer`、`TraderHintLayer`、`DoctorHintLayer` |
| `com.benji.netherman.events` | 7 | `ManipulatorStickEvents`、`ChanceTotemEvents`、`AzazelTrophyEvents` |
| `com.benji.netherman.common.network` | 7 | `ClientPayloadHandler`、`ServerPayloadHandler`、`AzazelBoostPacket` |
| `com.benji.netherman.client.renderer.entity` | 5 | `CrimsonArrowRenderer`、`GildedGolemRenderer`、`WelcomerRenderer` |
| `com.benji.netherman` | 4 | `ModGameEvents`、`NetherExpClient`、`NetherExp` |
| `com.benji.netherman.client.events` | 3 | `ClientBossBarEvents`、`ClientEffectEvents`、`ClientZoneAmbientEvents` |
| `com.benji.netherman.client` | 3 | `ClientFogHandler`、`ClientActionDelegate`、`ManipulationOverlay` |
| `com.benji.netherman.client.gui` | 3 | `AzazelHumanCutsceneScreen`、`NoteScreen`、`QuotaScreen` |
| `com.benji.netherman.common.effect` | 2 | `ManipulationEffect`、`ZoneEffect` |
| `com.benji.netherman.client.renderer.item` | 1 | `GeoBlockItemRenderer` |
| `com.benji.netherman.common.item.crafting` | 1 | `CrimsonArrowRecipe` |
| `com.benji.netherman.config` | 1 | `AzazelConfig` |
| `com.benji.netherman.common.compat` | 1 | `CuriosCompat` |
| `com.benji.netherman.common.data` | 1 | `TeleportDestinationData` |
| `com.benji.netherman.common.worldgen.structure` | 1 | `MegaJigsawStructure` |
| `com.benji.netherman.client.sound` | 1 | `ZoneAmbientSoundInstance` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ModGameEvents` | class | — | EventBusSubscriber | 10 | 270 | [ModGameEvents.java](com/benji/netherman/ModGameEvents.java) |
| `ClientBossBarEvents` | class | — | EventBusSubscriber | 2 | 153 | [ClientBossBarEvents.java](com/benji/netherman/client/events/ClientBossBarEvents.java) |
| `ClientEffectEvents` | class | — | EventBusSubscriber | 2 | 78 | [ClientEffectEvents.java](com/benji/netherman/client/events/ClientEffectEvents.java) |
| `ClientZoneAmbientEvents` | class | — | EventBusSubscriber | 4 | 152 | [ClientZoneAmbientEvents.java](com/benji/netherman/client/events/ClientZoneAmbientEvents.java) |
| `ManipulatorStickEvents` | class | — | EventBusSubscriber | 2 | 91 | [ManipulatorStickEvents.java](com/benji/netherman/events/ManipulatorStickEvents.java) |
| `ChanceTotemEvents` | class | — | EventBusSubscriber | 1 | 77 | [ChanceTotemEvents.java](com/benji/netherman/events/ChanceTotemEvents.java) |
| `AzazelTrophyEvents` | class | — | EventBusSubscriber | 4 | 152 | [AzazelTrophyEvents.java](com/benji/netherman/events/AzazelTrophyEvents.java) |
| `CommonEvents` | class | — | EventBusSubscriber | 2 | 145 | [CommonEvents.java](com/benji/netherman/events/CommonEvents.java) |
| `ClientFogHandler` | class | — | EventBusSubscriber | 3 | 53 | [ClientFogHandler.java](com/benji/netherman/client/ClientFogHandler.java) |
| `MansionCheckHandler` | class | — | EventBusSubscriber | 1 | 37 | [MansionCheckHandler.java](com/benji/netherman/events/MansionCheckHandler.java) |
| `GildedGolemSpawnEvent` | class | — | EventBusSubscriber | 1 | 65 | [GildedGolemSpawnEvent.java](com/benji/netherman/events/GildedGolemSpawnEvent.java) |
| `AzazelAltarEvent` | class | — | EventBusSubscriber | 2 | 73 | [AzazelAltarEvent.java](com/benji/netherman/events/AzazelAltarEvent.java) |
| `AzazelWingTrails` | class | — | EventBusSubscriber | 14 | 381 | [AzazelWingTrails.java](com/benji/netherman/client/renderer/AzazelWingTrails.java) |
| `NetherExpClient` | class | — | Mod | 9 | 214 | [NetherExpClient.java](com/benji/netherman/NetherExpClient.java) |
| `AzazelEntity` | class | Monster | ParametersAreNonnullByDefault | 46 | 1052 | [AzazelEntity.java](com/benji/netherman/common/entity/AzazelEntity.java) |
| `NetherExp` | class | — | Mod | 1 | 49 | [NetherExp.java](com/benji/netherman/NetherExp.java) |
| `AzazelHumanEntity` | class | Monster | — | 28 | 638 | [AzazelHumanEntity.java](com/benji/netherman/common/entity/AzazelHumanEntity.java) |
| `BelieverVillagerEntity` | class | PathfinderMob | — | 32 | 315 | [BelieverVillagerEntity.java](com/benji/netherman/common/entity/BelieverVillagerEntity.java) |
| `GuardianEntity` | class | Monster | — | 25 | 421 | [GuardianEntity.java](com/benji/netherman/common/entity/GuardianEntity.java) |
| `QuotaManager` | class | — | — | 7 | 232 | [QuotaManager.java](com/benji/netherman/QuotaManager.java) |
| `ClientPayloadHandler` | class | — | — | 2 | 18 | [ClientPayloadHandler.java](com/benji/netherman/common/network/ClientPayloadHandler.java) |
| `MazeDoorBlockEntity` | class | BlockEntity | — | 12 | 207 | [MazeDoorBlockEntity.java](com/benji/netherman/common/block/entity/MazeDoorBlockEntity.java) |
| `GrandDoorBlockEntity` | class | BlockEntity | — | 12 | 219 | [GrandDoorBlockEntity.java](com/benji/netherman/common/block/entity/GrandDoorBlockEntity.java) |
| `GildedGolemEntity` | class | IronGolem | — | 17 | 265 | [GildedGolemEntity.java](com/benji/netherman/common/entity/GildedGolemEntity.java) |
| `BlacksmithEntity` | class | PathfinderMob | — | 19 | 272 | [BlacksmithEntity.java](com/benji/netherman/common/entity/BlacksmithEntity.java) |
| `BelieverEntity` | class | PathfinderMob | — | 19 | 254 | [BelieverEntity.java](com/benji/netherman/common/entity/BelieverEntity.java) |
| `GhastlyEntity` | class | TamableAnimal | — | 17 | 245 | [GhastlyEntity.java](com/benji/netherman/common/entity/GhastlyEntity.java) |
| `TraderEntity` | class | PathfinderMob | — | 17 | 247 | [TraderEntity.java](com/benji/netherman/common/entity/TraderEntity.java) |
| `AzazelGuideBookEntity` | class | PathfinderMob | — | 15 | 331 | [AzazelGuideBookEntity.java](com/benji/netherman/common/entity/AzazelGuideBookEntity.java) |
| `ManipulatorEntity` | class | Monster | — | 16 | 245 | [ManipulatorEntity.java](com/benji/netherman/common/entity/ManipulatorEntity.java) |
| `DoctorEntity` | class | PathfinderMob | — | 16 | 209 | [DoctorEntity.java](com/benji/netherman/common/entity/DoctorEntity.java) |
| `FacePuzzleBlockEntity` | class | BlockEntity | — | 12 | 188 | [FacePuzzleBlockEntity.java](com/benji/netherman/common/block/entity/FacePuzzleBlockEntity.java) |
| `TraphiveBlockEntity` | class | BlockEntity | — | 10 | 178 | [TraphiveBlockEntity.java](com/benji/netherman/common/block/entity/TraphiveBlockEntity.java) |
| `GeoBlockItemRenderer` | class | GeoItemRenderer | — | 3 | 29 | [GeoBlockItemRenderer.java](com/benji/netherman/client/renderer/item/GeoBlockItemRenderer.java) |
| `ModBlocks` | class | — | — | 7 | 620 | [ModBlocks.java](com/benji/netherman/init/ModBlocks.java) |
| `ServerPayloadHandler` | class | — | — | 1 | 106 | [ServerPayloadHandler.java](com/benji/netherman/common/network/ServerPayloadHandler.java) |
| `LaserEntity` | class | Mob | — | 15 | 135 | [LaserEntity.java](com/benji/netherman/common/entity/LaserEntity.java) |
| `PiglinPrisonerEntity` | class | PathfinderMob | — | 13 | 259 | [PiglinPrisonerEntity.java](com/benji/netherman/common/entity/PiglinPrisonerEntity.java) |
| `WelcomerEntity` | class | Monster | — | 16 | 194 | [WelcomerEntity.java](com/benji/netherman/common/entity/WelcomerEntity.java) |
| `VillagerPrisonerEntity` | class | PathfinderMob | — | 13 | 259 | [VillagerPrisonerEntity.java](com/benji/netherman/common/entity/VillagerPrisonerEntity.java) |
| `AltarBlock` | class | HorizontalDirectionalBlock | — | 12 | 218 | [AltarBlock.java](com/benji/netherman/common/block/AltarBlock.java) |
| `TotemusHoleBlockEntity` | class | BlockEntity | — | 9 | 184 | [TotemusHoleBlockEntity.java](com/benji/netherman/common/block/entity/TotemusHoleBlockEntity.java) |
| `GhastlyNestBlockEntity` | class | BlockEntity | — | 6 | 186 | [GhastlyNestBlockEntity.java](com/benji/netherman/common/block/entity/GhastlyNestBlockEntity.java) |
| `NetherSpawnerBlockEntity` | class | BlockEntity | — | 5 | 161 | [NetherSpawnerBlockEntity.java](com/benji/netherman/common/block/entity/NetherSpawnerBlockEntity.java) |
| `TotemusBlockEntity` | class | BlockEntity | — | 6 | 152 | [TotemusBlockEntity.java](com/benji/netherman/common/block/entity/TotemusBlockEntity.java) |
| `AltarBlockEntity` | class | BlockEntity | — | 9 | 149 | [AltarBlockEntity.java](com/benji/netherman/common/block/entity/AltarBlockEntity.java) |
| `CrimsonArrowRecipe` | class | CustomRecipe | — | 5 | 82 | [CrimsonArrowRecipe.java](com/benji/netherman/common/item/crafting/CrimsonArrowRecipe.java) |
| `BellGuardianEntity` | class | Monster | — | 10 | 194 | [BellGuardianEntity.java](com/benji/netherman/common/entity/BellGuardianEntity.java) |
| `AzazelSpikesProjectileEntity` | class | Projectile | — | 10 | 151 | [AzazelSpikesProjectileEntity.java](com/benji/netherman/common/entity/AzazelSpikesProjectileEntity.java) |
| `StatueBossunitEntity` | class | Monster | — | 12 | 118 | [StatueBossunitEntity.java](com/benji/netherman/common/entity/StatueBossunitEntity.java) |
| `TotemusPuzzleEntity` | class | Monster | — | 13 | 107 | [TotemusPuzzleEntity.java](com/benji/netherman/common/entity/TotemusPuzzleEntity.java) |
| `StatueEntity` | class | Monster | — | 12 | 143 | [StatueEntity.java](com/benji/netherman/common/entity/StatueEntity.java) |
| `CrimsonWebBlock` | class | DirectionalBlock | — | 9 | 209 | [CrimsonWebBlock.java](com/benji/netherman/common/block/CrimsonWebBlock.java) |
| `StatueStandBlock` | class | HorizontalDirectionalBlock | — | 8 | 229 | [StatueStandBlock.java](com/benji/netherman/common/block/StatueStandBlock.java) |
| `GrandDoorBlock` | class | HorizontalDirectionalBlock | — | 10 | 148 | [GrandDoorBlock.java](com/benji/netherman/common/block/GrandDoorBlock.java) |
| `VoidNetherCornerBlockEntity` | class | BlockEntity | — | 2 | 27 | [VoidNetherCornerBlockEntity.java](com/benji/netherman/common/block/entity/VoidNetherCornerBlockEntity.java) |
| `LabyrinthTeleportBlockEntity` | class | BlockEntity | — | 3 | 141 | [LabyrinthTeleportBlockEntity.java](com/benji/netherman/common/block/entity/LabyrinthTeleportBlockEntity.java) |
| `SamsoniteBellBlockEntity` | class | BellBlockEntity | — | 2 | 58 | [SamsoniteBellBlockEntity.java](com/benji/netherman/common/block/entity/SamsoniteBellBlockEntity.java) |
| `PointedBlackstoneBlockEntity` | class | BlockEntity | — | 2 | 27 | [PointedBlackstoneBlockEntity.java](com/benji/netherman/common/block/entity/PointedBlackstoneBlockEntity.java) |
| `VoidNetherMidCornerBlockEntity` | class | BlockEntity | — | 2 | 27 | [VoidNetherMidCornerBlockEntity.java](com/benji/netherman/common/block/entity/VoidNetherMidCornerBlockEntity.java) |

## 事件处理（`@SubscribeEvent`，13 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ModGameEvents` | 9 | `com.benji.netherman` | [ModGameEvents.java](com/benji/netherman/ModGameEvents.java) |
| `AzazelWingTrails` | 4 | `com.benji.netherman.client.renderer` | [AzazelWingTrails.java](com/benji/netherman/client/renderer/AzazelWingTrails.java) |
| `AzazelTrophyEvents` | 3 | `com.benji.netherman.events` | [AzazelTrophyEvents.java](com/benji/netherman/events/AzazelTrophyEvents.java) |
| `ClientFogHandler` | 3 | `com.benji.netherman.client` | [ClientFogHandler.java](com/benji/netherman/client/ClientFogHandler.java) |
| `ClientBossBarEvents` | 2 | `com.benji.netherman.client.events` | [ClientBossBarEvents.java](com/benji/netherman/client/events/ClientBossBarEvents.java) |
| `ClientEffectEvents` | 2 | `com.benji.netherman.client.events` | [ClientEffectEvents.java](com/benji/netherman/client/events/ClientEffectEvents.java) |
| `ClientZoneAmbientEvents` | 2 | `com.benji.netherman.client.events` | [ClientZoneAmbientEvents.java](com/benji/netherman/client/events/ClientZoneAmbientEvents.java) |
| `ManipulatorStickEvents` | 2 | `com.benji.netherman.events` | [ManipulatorStickEvents.java](com/benji/netherman/events/ManipulatorStickEvents.java) |
| `CommonEvents` | 2 | `com.benji.netherman.events` | [CommonEvents.java](com/benji/netherman/events/CommonEvents.java) |
| `ChanceTotemEvents` | 1 | `com.benji.netherman.events` | [ChanceTotemEvents.java](com/benji/netherman/events/ChanceTotemEvents.java) |
| `MansionCheckHandler` | 1 | `com.benji.netherman.events` | [MansionCheckHandler.java](com/benji/netherman/events/MansionCheckHandler.java) |
| `GildedGolemSpawnEvent` | 1 | `com.benji.netherman.events` | [GildedGolemSpawnEvent.java](com/benji/netherman/events/GildedGolemSpawnEvent.java) |
| `AzazelAltarEvent` | 1 | `com.benji.netherman.events` | [AzazelAltarEvent.java](com/benji/netherman/events/AzazelAltarEvent.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `AzazelConfig` | — | [AzazelConfig.java](com/benji/netherman/config/AzazelConfig.java) |

## 按包浏览

- `com.benji.netherman`（4 类）
- `com.benji.netherman.client`（3 类）
- `com.benji.netherman.client.events`（3 类）
- `com.benji.netherman.client.gui`（3 类）
- `com.benji.netherman.client.layer`（8 类）
- `com.benji.netherman.client.model`（41 类）
- `com.benji.netherman.client.renderer`（37 类）
- `com.benji.netherman.client.renderer.entity`（5 类）
- `com.benji.netherman.client.renderer.item`（1 类）
- `com.benji.netherman.client.sound`（1 类）
- `com.benji.netherman.common.block`（37 类）
- `com.benji.netherman.common.block.entity`（17 类）
- `com.benji.netherman.common.compat`（1 类）
- `com.benji.netherman.common.data`（1 类）
- `com.benji.netherman.common.effect`（2 类）
- `com.benji.netherman.common.entity`（37 类）
- `com.benji.netherman.common.item`（16 类）
- `com.benji.netherman.common.item.crafting`（1 类）
- `com.benji.netherman.common.network`（7 类）
- `com.benji.netherman.common.worldgen.structure`（1 类）
- `com.benji.netherman.config`（1 类）
- `com.benji.netherman.events`（7 类）
- `com.benji.netherman.init`（12 类）
