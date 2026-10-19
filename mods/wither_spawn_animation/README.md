# Wither Spawn Animation（`wither_spawn_animation`）

> jar：`[凋零生成动画]wither_spawn_animation.jar` ｜ 版本：`1.6.2` ｜ 作者：bo bo
> 规模：**114 个类 / 9 个包 / 10900 行** ｜ 事件处理类 13 ｜ Mixin 0 ｜ 注册/管理类 0

**官方描述**：An animation that plays when a Wither is spawning

**依赖**：`neoforge`、`minecraft`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.witherspawnanimation.procedures` | 23 | `CallDetectAltarProcedure`、`AltarDiesProcedure`、`LoadWorldFixProcedure` |
| `net.witherspawnanimation.entity` | 20 | `AnimatedAltarStormSoilXEntity`、`AnimatedAltarSoilZEntity`、`AnimatedAltarStormZEntity` |
| `net.witherspawnanimation.client.renderer` | 20 | `AnimatedAltarMinZRenderer`、`AnimatedAltarSoilXRenderer`、`AnimatedAltarStormMinXRenderer` |
| `net.witherspawnanimation.entity.layer` | 20 | `AnimatedAltarMinZLayer`、`AnimatedAltarStormSoilXLayer`、`AnimatedAltarBygoneXLayer` |
| `net.witherspawnanimation.entity.model` | 20 | `AnimatedAltarBygoneXModel`、`AnimatedAltarStormZModel`、`AnimatedAltarMinXModel` |
| `net.witherspawnanimation.init` | 7 | `WitherSpawnAnimationModEntityRenderers`、`EntityAnimationFactory`、`WitherSpawnAnimationModEntities` |
| `net.witherspawnanimation` | 2 | `WitherSpawnAnimationMod`、`TomlParser` |
| `net.witherspawnanimation.network` | 1 | `WitherSpawnAnimationModVariables` |
| `net.witherspawnanimation.block` | 1 | `NoWBlock` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `WitherSpawnAnimationModVariables` | class | SavedData | EventBusSubscriber | 15 | 201 | [WitherSpawnAnimationModVariables.java](net/witherspawnanimation/network/WitherSpawnAnimationModVariables.java) |
| `WitherSpawnAnimationModEntityRenderers` | class | — | EventBusSubscriber | 1 | 56 | [WitherSpawnAnimationModEntityRenderers.java](net/witherspawnanimation/init/WitherSpawnAnimationModEntityRenderers.java) |
| `EntityAnimationFactory` | class | — | EventBusSubscriber | 1 | 194 | [EntityAnimationFactory.java](net/witherspawnanimation/init/EntityAnimationFactory.java) |
| `CallDetectAltarProcedure` | class | — | EventBusSubscriber | 3 | 229 | [CallDetectAltarProcedure.java](net/witherspawnanimation/procedures/CallDetectAltarProcedure.java) |
| `WitherSpawnAnimationModEntities` | class | — | EventBusSubscriber | 3 | 272 | [WitherSpawnAnimationModEntities.java](net/witherspawnanimation/init/WitherSpawnAnimationModEntities.java) |
| `WitherSpawnAnimationMod` | class | — | Mod | 4 | 87 | [WitherSpawnAnimationMod.java](net/witherspawnanimation/WitherSpawnAnimationMod.java) |
| `AltarDiesProcedure` | class | — | EventBusSubscriber | 3 | 42 | [AltarDiesProcedure.java](net/witherspawnanimation/procedures/AltarDiesProcedure.java) |
| `LoadWorldFixProcedure` | class | — | EventBusSubscriber | 3 | 87 | [LoadWorldFixProcedure.java](net/witherspawnanimation/procedures/LoadWorldFixProcedure.java) |
| `WitherNoDmgExplProcedure` | class | — | EventBusSubscriber | 3 | 41 | [WitherNoDmgExplProcedure.java](net/witherspawnanimation/procedures/WitherNoDmgExplProcedure.java) |
| `DetectSpawnFixProcedure` | class | — | EventBusSubscriber | 3 | 28 | [DetectSpawnFixProcedure.java](net/witherspawnanimation/procedures/DetectSpawnFixProcedure.java) |
| `NoWitherSpawnNormalProcedure` | class | — | EventBusSubscriber | 3 | 52 | [NoWitherSpawnNormalProcedure.java](net/witherspawnanimation/procedures/NoWitherSpawnNormalProcedure.java) |
| `NowitherspawnnormalcheatfixProcedure` | class | — | EventBusSubscriber | 3 | 66 | [NowitherspawnnormalcheatfixProcedure.java](net/witherspawnanimation/procedures/NowitherspawnnormalcheatfixProcedure.java) |
| `WitherSpawnAnimationModGameRules` | class | — | EventBusSubscriber | 1 | 21 | [WitherSpawnAnimationModGameRules.java](net/witherspawnanimation/init/WitherSpawnAnimationModGameRules.java) |
| `AnimatedAltarStormSoilXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilXEntity.java) |
| `AnimatedAltarSoilZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilZEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilZEntity.java) |
| `AnimatedAltarStormZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormZEntity.java) |
| `AnimatedAltarSoilMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilMinXEntity.java) |
| `AnimatedAltarBygoneXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneXEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneXEntity.java) |
| `AnimatedAltarBygoneMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneMinZEntity.java) |
| `AnimatedAltarStormSoilZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilZEntity.java) |
| `AnimatedAltarSoilXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilXEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilXEntity.java) |
| `AnimatedAltarStormSoilMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilMinZEntity.java) |
| `AnimatedAltarStormMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormMinXEntity.java) |
| `AnimatedAltarBygoneMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneMinXEntity.java) |
| `AnimatedAltarZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarZEntity.java](net/witherspawnanimation/entity/AnimatedAltarZEntity.java) |
| `AnimatedAltarBygoneZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneZEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneZEntity.java) |
| `AnimatedAltarMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarMinZEntity.java) |
| `AnimatedAltarSoilMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilMinZEntity.java) |
| `AnimatedAltarStormSoilMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilMinXEntity.java) |
| `AnimatedAltarStormMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormMinZEntity.java) |
| `AnimatedAltarStormXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormXEntity.java) |
| `AnimatedAltarXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarXEntity.java](net/witherspawnanimation/entity/AnimatedAltarXEntity.java) |
| `AnimatedAltarMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarMinXEntity.java) |
| `NoWBlock` | class | Block | — | 7 | 63 | [NoWBlock.java](net/witherspawnanimation/block/NoWBlock.java) |
| `AnimatedAltarEntityIsHurtProcedure` | class | — | — | 1 | 15 | [AnimatedAltarEntityIsHurtProcedure.java](net/witherspawnanimation/procedures/AnimatedAltarEntityIsHurtProcedure.java) |
| `AnimatedAltarMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarMinZRenderer.java) |
| `AnimatedAltarSoilXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilXRenderer.java) |
| `AnimatedAltarStormMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormMinXRenderer.java) |
| `AnimatedAltarBygoneMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneMinZRenderer.java) |
| `AnimatedAltarStormZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormZRenderer.java) |
| `AnimatedAltarZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarZRenderer.java) |
| `AnimatedAltarBygoneMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneMinXRenderer.java) |
| `AnimatedAltarMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarMinXRenderer.java) |
| `AnimatedAltarStormSoilXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilXRenderer.java) |
| `AnimatedAltarBygoneXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneXRenderer.java) |
| `AnimatedAltarSoilMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilMinZRenderer.java) |
| `AnimatedAltarSoilMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilMinXRenderer.java) |
| `AnimatedAltarBygoneZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneZRenderer.java) |
| `AnimatedAltarStormSoilMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilMinZRenderer.java) |
| `AnimatedAltarStormXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormXRenderer.java) |
| `AnimatedAltarXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarXRenderer.java) |
| `AnimatedAltarStormSoilMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilMinXRenderer.java) |
| `AnimatedAltarStormSoilZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilZRenderer.java) |
| `AnimatedAltarSoilZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilZRenderer.java) |
| `AnimatedAltarStormMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormMinZRenderer.java) |
| `WitherSpawnAnimationModBlocks` | class | — | — | 0 | 13 | [WitherSpawnAnimationModBlocks.java](net/witherspawnanimation/init/WitherSpawnAnimationModBlocks.java) |
| `WitherSpawnAnimationModItems` | class | — | — | 1 | 20 | [WitherSpawnAnimationModItems.java](net/witherspawnanimation/init/WitherSpawnAnimationModItems.java) |
| `DetectAltarSoilProcedure` | class | — | — | 1 | 378 | [DetectAltarSoilProcedure.java](net/witherspawnanimation/procedures/DetectAltarSoilProcedure.java) |
| `WitherStormModFixProcedure` | class | — | — | 1 | 378 | [WitherStormModFixProcedure.java](net/witherspawnanimation/procedures/WitherStormModFixProcedure.java) |
| `DetectAltarProcedure` | class | — | — | 1 | 378 | [DetectAltarProcedure.java](net/witherspawnanimation/procedures/DetectAltarProcedure.java) |

## 事件处理（`@SubscribeEvent`，13 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `WitherSpawnAnimationModVariables` | 3 | `net.witherspawnanimation.network` | [WitherSpawnAnimationModVariables.java](net/witherspawnanimation/network/WitherSpawnAnimationModVariables.java) |
| `WitherSpawnAnimationModEntities` | 2 | `net.witherspawnanimation.init` | [WitherSpawnAnimationModEntities.java](net/witherspawnanimation/init/WitherSpawnAnimationModEntities.java) |
| `WitherSpawnAnimationModEntityRenderers` | 1 | `net.witherspawnanimation.init` | [WitherSpawnAnimationModEntityRenderers.java](net/witherspawnanimation/init/WitherSpawnAnimationModEntityRenderers.java) |
| `EntityAnimationFactory` | 1 | `net.witherspawnanimation.init` | [EntityAnimationFactory.java](net/witherspawnanimation/init/EntityAnimationFactory.java) |
| `CallDetectAltarProcedure` | 1 | `net.witherspawnanimation.procedures` | [CallDetectAltarProcedure.java](net/witherspawnanimation/procedures/CallDetectAltarProcedure.java) |
| `WitherSpawnAnimationMod` | 1 | `net.witherspawnanimation` | [WitherSpawnAnimationMod.java](net/witherspawnanimation/WitherSpawnAnimationMod.java) |
| `AltarDiesProcedure` | 1 | `net.witherspawnanimation.procedures` | [AltarDiesProcedure.java](net/witherspawnanimation/procedures/AltarDiesProcedure.java) |
| `LoadWorldFixProcedure` | 1 | `net.witherspawnanimation.procedures` | [LoadWorldFixProcedure.java](net/witherspawnanimation/procedures/LoadWorldFixProcedure.java) |
| `WitherNoDmgExplProcedure` | 1 | `net.witherspawnanimation.procedures` | [WitherNoDmgExplProcedure.java](net/witherspawnanimation/procedures/WitherNoDmgExplProcedure.java) |
| `DetectSpawnFixProcedure` | 1 | `net.witherspawnanimation.procedures` | [DetectSpawnFixProcedure.java](net/witherspawnanimation/procedures/DetectSpawnFixProcedure.java) |
| `NoWitherSpawnNormalProcedure` | 1 | `net.witherspawnanimation.procedures` | [NoWitherSpawnNormalProcedure.java](net/witherspawnanimation/procedures/NoWitherSpawnNormalProcedure.java) |
| `NowitherspawnnormalcheatfixProcedure` | 1 | `net.witherspawnanimation.procedures` | [NowitherspawnnormalcheatfixProcedure.java](net/witherspawnanimation/procedures/NowitherspawnnormalcheatfixProcedure.java) |
| `WitherSpawnAnimationModGameRules` | 1 | `net.witherspawnanimation.init` | [WitherSpawnAnimationModGameRules.java](net/witherspawnanimation/init/WitherSpawnAnimationModGameRules.java) |

## 全部类（114）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `WitherSpawnAnimationModVariables` | class | SavedData | EventBusSubscriber | 15 | 201 | [WitherSpawnAnimationModVariables.java](net/witherspawnanimation/network/WitherSpawnAnimationModVariables.java) |
| `WitherSpawnAnimationModEntityRenderers` | class | — | EventBusSubscriber | 1 | 56 | [WitherSpawnAnimationModEntityRenderers.java](net/witherspawnanimation/init/WitherSpawnAnimationModEntityRenderers.java) |
| `EntityAnimationFactory` | class | — | EventBusSubscriber | 1 | 194 | [EntityAnimationFactory.java](net/witherspawnanimation/init/EntityAnimationFactory.java) |
| `CallDetectAltarProcedure` | class | — | EventBusSubscriber | 3 | 229 | [CallDetectAltarProcedure.java](net/witherspawnanimation/procedures/CallDetectAltarProcedure.java) |
| `WitherSpawnAnimationModEntities` | class | — | EventBusSubscriber | 3 | 272 | [WitherSpawnAnimationModEntities.java](net/witherspawnanimation/init/WitherSpawnAnimationModEntities.java) |
| `WitherSpawnAnimationMod` | class | — | Mod | 4 | 87 | [WitherSpawnAnimationMod.java](net/witherspawnanimation/WitherSpawnAnimationMod.java) |
| `AltarDiesProcedure` | class | — | EventBusSubscriber | 3 | 42 | [AltarDiesProcedure.java](net/witherspawnanimation/procedures/AltarDiesProcedure.java) |
| `LoadWorldFixProcedure` | class | — | EventBusSubscriber | 3 | 87 | [LoadWorldFixProcedure.java](net/witherspawnanimation/procedures/LoadWorldFixProcedure.java) |
| `WitherNoDmgExplProcedure` | class | — | EventBusSubscriber | 3 | 41 | [WitherNoDmgExplProcedure.java](net/witherspawnanimation/procedures/WitherNoDmgExplProcedure.java) |
| `DetectSpawnFixProcedure` | class | — | EventBusSubscriber | 3 | 28 | [DetectSpawnFixProcedure.java](net/witherspawnanimation/procedures/DetectSpawnFixProcedure.java) |
| `NoWitherSpawnNormalProcedure` | class | — | EventBusSubscriber | 3 | 52 | [NoWitherSpawnNormalProcedure.java](net/witherspawnanimation/procedures/NoWitherSpawnNormalProcedure.java) |
| `NowitherspawnnormalcheatfixProcedure` | class | — | EventBusSubscriber | 3 | 66 | [NowitherspawnnormalcheatfixProcedure.java](net/witherspawnanimation/procedures/NowitherspawnnormalcheatfixProcedure.java) |
| `WitherSpawnAnimationModGameRules` | class | — | EventBusSubscriber | 1 | 21 | [WitherSpawnAnimationModGameRules.java](net/witherspawnanimation/init/WitherSpawnAnimationModGameRules.java) |
| `AnimatedAltarStormSoilXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilXEntity.java) |
| `AnimatedAltarSoilZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilZEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilZEntity.java) |
| `AnimatedAltarStormZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormZEntity.java) |
| `AnimatedAltarSoilMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilMinXEntity.java) |
| `AnimatedAltarBygoneXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneXEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneXEntity.java) |
| `AnimatedAltarBygoneMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneMinZEntity.java) |
| `AnimatedAltarStormSoilZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilZEntity.java) |
| `AnimatedAltarSoilXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilXEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilXEntity.java) |
| `AnimatedAltarStormSoilMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilMinZEntity.java) |
| `AnimatedAltarStormMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormMinXEntity.java) |
| `AnimatedAltarBygoneMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneMinXEntity.java) |
| `AnimatedAltarZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarZEntity.java](net/witherspawnanimation/entity/AnimatedAltarZEntity.java) |
| `AnimatedAltarBygoneZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarBygoneZEntity.java](net/witherspawnanimation/entity/AnimatedAltarBygoneZEntity.java) |
| `AnimatedAltarMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarMinZEntity.java) |
| `AnimatedAltarSoilMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarSoilMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarSoilMinZEntity.java) |
| `AnimatedAltarStormSoilMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormSoilMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormSoilMinXEntity.java) |
| `AnimatedAltarStormMinZEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormMinZEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormMinZEntity.java) |
| `AnimatedAltarStormXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarStormXEntity.java](net/witherspawnanimation/entity/AnimatedAltarStormXEntity.java) |
| `AnimatedAltarXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarXEntity.java](net/witherspawnanimation/entity/AnimatedAltarXEntity.java) |
| `AnimatedAltarMinXEntity` | class | PathfinderMob | — | 25 | 229 | [AnimatedAltarMinXEntity.java](net/witherspawnanimation/entity/AnimatedAltarMinXEntity.java) |
| `NoWBlock` | class | Block | — | 7 | 63 | [NoWBlock.java](net/witherspawnanimation/block/NoWBlock.java) |
| `AnimatedAltarEntityIsHurtProcedure` | class | — | — | 1 | 15 | [AnimatedAltarEntityIsHurtProcedure.java](net/witherspawnanimation/procedures/AnimatedAltarEntityIsHurtProcedure.java) |
| `AnimatedAltarMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarMinZRenderer.java) |
| `AnimatedAltarSoilXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilXRenderer.java) |
| `AnimatedAltarStormMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormMinXRenderer.java) |
| `AnimatedAltarBygoneMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneMinZRenderer.java) |
| `AnimatedAltarStormZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormZRenderer.java) |
| `AnimatedAltarZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarZRenderer.java) |
| `AnimatedAltarBygoneMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneMinXRenderer.java) |
| `AnimatedAltarMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarMinXRenderer.java) |
| `AnimatedAltarStormSoilXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilXRenderer.java) |
| `AnimatedAltarBygoneXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneXRenderer.java) |
| `AnimatedAltarSoilMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilMinZRenderer.java) |
| `AnimatedAltarSoilMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilMinXRenderer.java) |
| `AnimatedAltarBygoneZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarBygoneZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarBygoneZRenderer.java) |
| `AnimatedAltarStormSoilMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilMinZRenderer.java) |
| `AnimatedAltarStormXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormXRenderer.java) |
| `AnimatedAltarXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarXRenderer.java) |
| `AnimatedAltarStormSoilMinXRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilMinXRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilMinXRenderer.java) |
| `AnimatedAltarStormSoilZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormSoilZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormSoilZRenderer.java) |
| `AnimatedAltarSoilZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarSoilZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarSoilZRenderer.java) |
| `AnimatedAltarStormMinZRenderer` | class | GeoEntityRenderer | — | 2 | 44 | [AnimatedAltarStormMinZRenderer.java](net/witherspawnanimation/client/renderer/AnimatedAltarStormMinZRenderer.java) |
| `WitherSpawnAnimationModBlocks` | class | — | — | 0 | 13 | [WitherSpawnAnimationModBlocks.java](net/witherspawnanimation/init/WitherSpawnAnimationModBlocks.java) |
| `WitherSpawnAnimationModItems` | class | — | — | 1 | 20 | [WitherSpawnAnimationModItems.java](net/witherspawnanimation/init/WitherSpawnAnimationModItems.java) |
| `DetectAltarSoilProcedure` | class | — | — | 1 | 378 | [DetectAltarSoilProcedure.java](net/witherspawnanimation/procedures/DetectAltarSoilProcedure.java) |
| `WitherStormModFixProcedure` | class | — | — | 1 | 378 | [WitherStormModFixProcedure.java](net/witherspawnanimation/procedures/WitherStormModFixProcedure.java) |
| `DetectAltarProcedure` | class | — | — | 1 | 378 | [DetectAltarProcedure.java](net/witherspawnanimation/procedures/DetectAltarProcedure.java) |
| `AltarEffectsProcedure` | class | — | — | 1 | 306 | [AltarEffectsProcedure.java](net/witherspawnanimation/procedures/AltarEffectsProcedure.java) |
| `DetectAltarBygoneNetherProcedure` | class | — | — | 1 | 381 | [DetectAltarBygoneNetherProcedure.java](net/witherspawnanimation/procedures/DetectAltarBygoneNetherProcedure.java) |
| `WitherStormModFixSoilProcedure` | class | — | — | 1 | 378 | [WitherStormModFixSoilProcedure.java](net/witherspawnanimation/procedures/WitherStormModFixSoilProcedure.java) |
| `TomlParser` | class | — | — | 1 | 31 | [TomlParser.java](net/witherspawnanimation/TomlParser.java) |
| `AnimatedAltarMinZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarMinZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarMinZLayer.java) |
| `AnimatedAltarStormSoilXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormSoilXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormSoilXLayer.java) |
| `AnimatedAltarBygoneXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarBygoneXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarBygoneXLayer.java) |
| `AnimatedAltarSoilMinZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarSoilMinZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarSoilMinZLayer.java) |
| `AnimatedAltarSoilMinXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarSoilMinXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarSoilMinXLayer.java) |
| `AnimatedAltarBygoneMinXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarBygoneMinXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarBygoneMinXLayer.java) |
| `AnimatedAltarBygoneZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarBygoneZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarBygoneZLayer.java) |
| `AnimatedAltarXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarXLayer.java) |
| `AnimatedAltarStormSoilMinXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormSoilMinXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormSoilMinXLayer.java) |
| `AnimatedAltarStormZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormZLayer.java) |
| `AnimatedAltarZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarZLayer.java) |
| `AnimatedAltarSoilZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarSoilZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarSoilZLayer.java) |
| `AnimatedAltarBygoneMinZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarBygoneMinZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarBygoneMinZLayer.java) |
| `AnimatedAltarStormXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormXLayer.java) |
| `AnimatedAltarMinXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarMinXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarMinXLayer.java) |
| `AnimatedAltarSoilXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarSoilXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarSoilXLayer.java) |
| `AnimatedAltarStormSoilZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormSoilZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormSoilZLayer.java) |
| `AnimatedAltarStormMinZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormMinZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormMinZLayer.java) |
| `AnimatedAltarStormMinXLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormMinXLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormMinXLayer.java) |
| `AnimatedAltarStormSoilMinZLayer` | class | GeoRenderLayer | — | 1 | 48 | [AnimatedAltarStormSoilMinZLayer.java](net/witherspawnanimation/entity/layer/AnimatedAltarStormSoilMinZLayer.java) |
| `AnimatedAltarBygoneXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarBygoneXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarBygoneXModel.java) |
| `AnimatedAltarStormZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormZModel.java) |
| `AnimatedAltarMinXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarMinXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarMinXModel.java) |
| `AnimatedAltarStormMinZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormMinZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormMinZModel.java) |
| `AnimatedAltarBygoneMinXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarBygoneMinXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarBygoneMinXModel.java) |
| `AnimatedAltarXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarXModel.java) |
| `AnimatedAltarSoilMinZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarSoilMinZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarSoilMinZModel.java) |
| `AnimatedAltarBygoneMinZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarBygoneMinZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarBygoneMinZModel.java) |
| `AnimatedAltarStormSoilXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormSoilXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormSoilXModel.java) |
| `AnimatedAltarSoilXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarSoilXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarSoilXModel.java) |
| `AnimatedAltarStormXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormXModel.java) |
| `AnimatedAltarStormSoilMinXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormSoilMinXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormSoilMinXModel.java) |
| `AnimatedAltarBygoneZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarBygoneZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarBygoneZModel.java) |
| `AnimatedAltarZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarZModel.java) |
| `AnimatedAltarMinZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarMinZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarMinZModel.java) |
| `AnimatedAltarSoilZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarSoilZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarSoilZModel.java) |
| `AnimatedAltarStormMinXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormMinXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormMinXModel.java) |
| `AnimatedAltarStormSoilMinZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormSoilMinZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormSoilMinZModel.java) |
| `AnimatedAltarStormSoilZModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarStormSoilZModel.java](net/witherspawnanimation/entity/model/AnimatedAltarStormSoilZModel.java) |
| `AnimatedAltarSoilMinXModel` | class | GeoModel | — | 3 | 20 | [AnimatedAltarSoilMinXModel.java](net/witherspawnanimation/entity/model/AnimatedAltarSoilMinXModel.java) |
| `AltarCounterUpdateProcedure` | class | — | — | 1 | 33 | [AltarCounterUpdateProcedure.java](net/witherspawnanimation/procedures/AltarCounterUpdateProcedure.java) |
| `SpawnWitherStormAtCoordinatesProcedure` | class | — | — | 1 | 37 | [SpawnWitherStormAtCoordinatesProcedure.java](net/witherspawnanimation/procedures/SpawnWitherStormAtCoordinatesProcedure.java) |
| `SpawnWitherStormProcedure` | class | — | — | 1 | 35 | [SpawnWitherStormProcedure.java](net/witherspawnanimation/procedures/SpawnWitherStormProcedure.java) |
| `SkullPositionCheckZProcedure` | class | — | — | 1 | 19 | [SkullPositionCheckZProcedure.java](net/witherspawnanimation/procedures/SkullPositionCheckZProcedure.java) |
| `AltarSpawnedProcedure` | class | — | — | 1 | 62 | [AltarSpawnedProcedure.java](net/witherspawnanimation/procedures/AltarSpawnedProcedure.java) |
| `RotationDetectProcedure` | class | — | — | 1 | 61 | [RotationDetectProcedure.java](net/witherspawnanimation/procedures/RotationDetectProcedure.java) |
| `TickUpdateProcedure` | class | — | — | 1 | 29 | [TickUpdateProcedure.java](net/witherspawnanimation/procedures/TickUpdateProcedure.java) |
| `CallDetectionsProcedure` | class | — | — | 1 | 32 | [CallDetectionsProcedure.java](net/witherspawnanimation/procedures/CallDetectionsProcedure.java) |
| `SkullPositionCheckXProcedure` | class | — | — | 1 | 19 | [SkullPositionCheckXProcedure.java](net/witherspawnanimation/procedures/SkullPositionCheckXProcedure.java) |
| `WitherSpawnAnimationModSounds` | class | — | — | 0 | 36 | [WitherSpawnAnimationModSounds.java](net/witherspawnanimation/init/WitherSpawnAnimationModSounds.java) |
