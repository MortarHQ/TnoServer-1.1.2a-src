# Fox Boss（`foxboss`）

> jar：`[转生附属]foxboss.jar` ｜ 版本：`1.0.0` ｜ 作者：AQZL
> 规模：**35 个类 / 5 个包 / 4231 行** ｜ 事件处理类 4 ｜ Mixin 0 ｜ 注册/管理类 0

**依赖**：`geckolib`、`minecraft`、`neoforge`、`tensura`、`manascore`、`manascore_skill`、`manascore_race`、`foxablazeultimate`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.foxboss.entity` | 19 | `MilimCarrionHostility`、`MilimSpawning`、`MilimEntity` |
| `dev.foxboss.client` | 10 | `FoxBossClient`、`MilimBossBarOverlay`、`ThrownBlockRenderer` |
| `dev.foxboss` | 2 | `FoxBoss`、`FoxBossConfig` |
| `dev.foxboss.registry` | 2 | `FoxItems`、`FoxEntities` |
| `dev.foxboss.network` | 2 | `MilimBossBarPayload`、`MilimBossBars` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 35）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FoxBossClient` | class | — | EventBusSubscriber | 1 | 23 | [FoxBossClient.java](dev/foxboss/client/FoxBossClient.java) |
| `MilimCarrionHostility` | class | — | EventBusSubscriber | 2 | 87 | [MilimCarrionHostility.java](dev/foxboss/entity/MilimCarrionHostility.java) |
| `MilimSpawning` | class | — | EventBusSubscriber | 2 | 56 | [MilimSpawning.java](dev/foxboss/entity/MilimSpawning.java) |
| `MilimBossBarOverlay` | class | — | EventBusSubscriber | 3 | 56 | [MilimBossBarOverlay.java](dev/foxboss/client/MilimBossBarOverlay.java) |
| `MilimEntity` | class | TensuraHumanoidEntity | — | 71 | 1232 | [MilimEntity.java](dev/foxboss/entity/MilimEntity.java) |
| `FoxBoss` | class | — | Mod | 1 | 31 | [FoxBoss.java](dev/foxboss/FoxBoss.java) |
| `ThrownBlockEntity` | class | Projectile | — | 13 | 256 | [ThrownBlockEntity.java](dev/foxboss/entity/ThrownBlockEntity.java) |
| `FoxBossConfig` | class | — | — | 0 | 48 | [FoxBossConfig.java](dev/foxboss/FoxBossConfig.java) |
| `DragonStarEntity` | class | Entity | — | 13 | 163 | [DragonStarEntity.java](dev/foxboss/entity/DragonStarEntity.java) |
| `ThrownBlockRenderer` | class | EntityRenderer | — | 2 | 39 | [ThrownBlockRenderer.java](dev/foxboss/client/ThrownBlockRenderer.java) |
| `MilimSatanaelCompat` | class | — | — | 5 | 194 | [MilimSatanaelCompat.java](dev/foxboss/entity/MilimSatanaelCompat.java) |
| `MilimRenderer` | class | GeoEntityRenderer | — | 6 | 96 | [MilimRenderer.java](dev/foxboss/client/MilimRenderer.java) |
| `MilimBlockThrow` | class | — | — | 1 | 159 | [MilimBlockThrow.java](dev/foxboss/entity/MilimBlockThrow.java) |
| `MilimCombatTactics` | class | — | — | 8 | 320 | [MilimCombatTactics.java](dev/foxboss/entity/MilimCombatTactics.java) |
| `FoxItems` | class | — | — | 1 | 27 | [FoxItems.java](dev/foxboss/registry/FoxItems.java) |
| `MilimThrownBlockLayer` | class | BlockAndItemGeoLayer | — | 2 | 34 | [MilimThrownBlockLayer.java](dev/foxboss/client/MilimThrownBlockLayer.java) |
| `MilimHeldItemLayer` | class | BlockAndItemGeoLayer | — | 3 | 65 | [MilimHeldItemLayer.java](dev/foxboss/client/MilimHeldItemLayer.java) |
| `DragonStarRenderer` | class | EntityRenderer | — | 3 | 52 | [DragonStarRenderer.java](dev/foxboss/client/DragonStarRenderer.java) |
| `MilimWrathController` | class | — | — | 6 | 154 | [MilimWrathController.java](dev/foxboss/entity/MilimWrathController.java) |
| `MilimMeleeContact` | class | — | — | 9 | 63 | [MilimMeleeContact.java](dev/foxboss/entity/MilimMeleeContact.java) |
| `MilimFollowOwnerGoal` | class | FollowOwnerGoal | — | 7 | 61 | [MilimFollowOwnerGoal.java](dev/foxboss/entity/MilimFollowOwnerGoal.java) |
| `VfxGeometry` | class | — | — | 6 | 187 | [VfxGeometry.java](dev/foxboss/client/VfxGeometry.java) |
| `MilimBossBarPayload` | record | CustomPacketPayload | — | 2 | 29 | [MilimBossBarPayload.java](dev/foxboss/network/MilimBossBarPayload.java) |
| `MilimBossBars` | class | — | — | 3 | 32 | [MilimBossBars.java](dev/foxboss/network/MilimBossBars.java) |
| `MilimCombatSpacing` | class | — | — | 2 | 134 | [MilimCombatSpacing.java](dev/foxboss/entity/MilimCombatSpacing.java) |
| `MilimDragonAura` | class | — | — | 0 | 32 | [MilimDragonAura.java](dev/foxboss/entity/MilimDragonAura.java) |
| `MilimSatanaelBonuses` | class | — | — | 3 | 53 | [MilimSatanaelBonuses.java](dev/foxboss/entity/MilimSatanaelBonuses.java) |
| `BurstTimeline` | class | — | — | 4 | 31 | [BurstTimeline.java](dev/foxboss/entity/BurstTimeline.java) |
| `MilimBurstPlanner` | class | — | — | 2 | 158 | [MilimBurstPlanner.java](dev/foxboss/entity/MilimBurstPlanner.java) |
| `MilimResistances` | class | — | — | 0 | 88 | [MilimResistances.java](dev/foxboss/entity/MilimResistances.java) |
| `MilimAggroController` | class | — | — | 4 | 86 | [MilimAggroController.java](dev/foxboss/entity/MilimAggroController.java) |
| `MilimSatanaelCombat` | class | — | — | 1 | 84 | [MilimSatanaelCombat.java](dev/foxboss/entity/MilimSatanaelCombat.java) |
| `FoxEntities` | class | — | — | 0 | 48 | [FoxEntities.java](dev/foxboss/registry/FoxEntities.java) |
| `FoxVfxRenderTypes` | class | RenderType | — | 0 | 29 | [FoxVfxRenderTypes.java](dev/foxboss/client/FoxVfxRenderTypes.java) |
| `MilimModel` | class | GeoModel | — | 3 | 24 | [MilimModel.java](dev/foxboss/client/MilimModel.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `MilimBossBarOverlay` | 2 | `dev.foxboss.client` | [MilimBossBarOverlay.java](dev/foxboss/client/MilimBossBarOverlay.java) |
| `FoxBossClient` | 1 | `dev.foxboss.client` | [FoxBossClient.java](dev/foxboss/client/FoxBossClient.java) |
| `MilimCarrionHostility` | 1 | `dev.foxboss.entity` | [MilimCarrionHostility.java](dev/foxboss/entity/MilimCarrionHostility.java) |
| `MilimSpawning` | 1 | `dev.foxboss.entity` | [MilimSpawning.java](dev/foxboss/entity/MilimSpawning.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FoxBossConfig` | — | [FoxBossConfig.java](dev/foxboss/FoxBossConfig.java) |

## 全部类（35）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FoxBossClient` | class | — | EventBusSubscriber | 1 | 23 | [FoxBossClient.java](dev/foxboss/client/FoxBossClient.java) |
| `MilimCarrionHostility` | class | — | EventBusSubscriber | 2 | 87 | [MilimCarrionHostility.java](dev/foxboss/entity/MilimCarrionHostility.java) |
| `MilimSpawning` | class | — | EventBusSubscriber | 2 | 56 | [MilimSpawning.java](dev/foxboss/entity/MilimSpawning.java) |
| `MilimBossBarOverlay` | class | — | EventBusSubscriber | 3 | 56 | [MilimBossBarOverlay.java](dev/foxboss/client/MilimBossBarOverlay.java) |
| `MilimEntity` | class | TensuraHumanoidEntity | — | 71 | 1232 | [MilimEntity.java](dev/foxboss/entity/MilimEntity.java) |
| `FoxBoss` | class | — | Mod | 1 | 31 | [FoxBoss.java](dev/foxboss/FoxBoss.java) |
| `ThrownBlockEntity` | class | Projectile | — | 13 | 256 | [ThrownBlockEntity.java](dev/foxboss/entity/ThrownBlockEntity.java) |
| `FoxBossConfig` | class | — | — | 0 | 48 | [FoxBossConfig.java](dev/foxboss/FoxBossConfig.java) |
| `DragonStarEntity` | class | Entity | — | 13 | 163 | [DragonStarEntity.java](dev/foxboss/entity/DragonStarEntity.java) |
| `ThrownBlockRenderer` | class | EntityRenderer | — | 2 | 39 | [ThrownBlockRenderer.java](dev/foxboss/client/ThrownBlockRenderer.java) |
| `MilimSatanaelCompat` | class | — | — | 5 | 194 | [MilimSatanaelCompat.java](dev/foxboss/entity/MilimSatanaelCompat.java) |
| `MilimRenderer` | class | GeoEntityRenderer | — | 6 | 96 | [MilimRenderer.java](dev/foxboss/client/MilimRenderer.java) |
| `MilimBlockThrow` | class | — | — | 1 | 159 | [MilimBlockThrow.java](dev/foxboss/entity/MilimBlockThrow.java) |
| `MilimCombatTactics` | class | — | — | 8 | 320 | [MilimCombatTactics.java](dev/foxboss/entity/MilimCombatTactics.java) |
| `FoxItems` | class | — | — | 1 | 27 | [FoxItems.java](dev/foxboss/registry/FoxItems.java) |
| `MilimThrownBlockLayer` | class | BlockAndItemGeoLayer | — | 2 | 34 | [MilimThrownBlockLayer.java](dev/foxboss/client/MilimThrownBlockLayer.java) |
| `MilimHeldItemLayer` | class | BlockAndItemGeoLayer | — | 3 | 65 | [MilimHeldItemLayer.java](dev/foxboss/client/MilimHeldItemLayer.java) |
| `DragonStarRenderer` | class | EntityRenderer | — | 3 | 52 | [DragonStarRenderer.java](dev/foxboss/client/DragonStarRenderer.java) |
| `MilimWrathController` | class | — | — | 6 | 154 | [MilimWrathController.java](dev/foxboss/entity/MilimWrathController.java) |
| `MilimMeleeContact` | class | — | — | 9 | 63 | [MilimMeleeContact.java](dev/foxboss/entity/MilimMeleeContact.java) |
| `MilimFollowOwnerGoal` | class | FollowOwnerGoal | — | 7 | 61 | [MilimFollowOwnerGoal.java](dev/foxboss/entity/MilimFollowOwnerGoal.java) |
| `VfxGeometry` | class | — | — | 6 | 187 | [VfxGeometry.java](dev/foxboss/client/VfxGeometry.java) |
| `MilimBossBarPayload` | record | CustomPacketPayload | — | 2 | 29 | [MilimBossBarPayload.java](dev/foxboss/network/MilimBossBarPayload.java) |
| `MilimBossBars` | class | — | — | 3 | 32 | [MilimBossBars.java](dev/foxboss/network/MilimBossBars.java) |
| `MilimCombatSpacing` | class | — | — | 2 | 134 | [MilimCombatSpacing.java](dev/foxboss/entity/MilimCombatSpacing.java) |
| `MilimDragonAura` | class | — | — | 0 | 32 | [MilimDragonAura.java](dev/foxboss/entity/MilimDragonAura.java) |
| `MilimSatanaelBonuses` | class | — | — | 3 | 53 | [MilimSatanaelBonuses.java](dev/foxboss/entity/MilimSatanaelBonuses.java) |
| `BurstTimeline` | class | — | — | 4 | 31 | [BurstTimeline.java](dev/foxboss/entity/BurstTimeline.java) |
| `MilimBurstPlanner` | class | — | — | 2 | 158 | [MilimBurstPlanner.java](dev/foxboss/entity/MilimBurstPlanner.java) |
| `MilimResistances` | class | — | — | 0 | 88 | [MilimResistances.java](dev/foxboss/entity/MilimResistances.java) |
| `MilimAggroController` | class | — | — | 4 | 86 | [MilimAggroController.java](dev/foxboss/entity/MilimAggroController.java) |
| `MilimSatanaelCombat` | class | — | — | 1 | 84 | [MilimSatanaelCombat.java](dev/foxboss/entity/MilimSatanaelCombat.java) |
| `FoxEntities` | class | — | — | 0 | 48 | [FoxEntities.java](dev/foxboss/registry/FoxEntities.java) |
| `FoxVfxRenderTypes` | class | RenderType | — | 0 | 29 | [FoxVfxRenderTypes.java](dev/foxboss/client/FoxVfxRenderTypes.java) |
| `MilimModel` | class | GeoModel | — | 3 | 24 | [MilimModel.java](dev/foxboss/client/MilimModel.java) |
