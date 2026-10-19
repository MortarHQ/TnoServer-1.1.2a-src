# Structure Layout Optimizer（`structure_layout_optimizer`）

> jar：`[结构生成优化]structurelayoutoptimizer.jar` ｜ 版本：`1.0.12` ｜ 作者：TelepathicGrunt
> 规模：**15 个类 / 6 个包 / 870 行** ｜ 事件处理类 0 ｜ Mixin 5 ｜ 注册/管理类 0

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `telepathicgrunt.structure_layout_optimizer.mixins` | 5 | `JigsawPlacementMixin`、`SinglePoolElementMixin`、`StructureTemplatePoolAccessor` |
| `telepathicgrunt.structure_layout_optimizer.utils` | 5 | `GeneralUtils`、`BoxOctree`、`StructureTemplateOptimizer` |
| `telepathicgrunt.structure_layout_optimizer` | 2 | `SloConfig`、`StructureLayoutOptimizerMod` |
| `telepathicgrunt.structure_layout_optimizer.neoforge.entrypoints` | 1 | `Main` |
| `telepathicgrunt.structure_layout_optimizer.neoforge.services` | 1 | `NeoPlatformService` |
| `telepathicgrunt.structure_layout_optimizer.services` | 1 | `PlatformService` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SloConfig` | class | — | Config、ConfigInfo | 0 | 46 | [SloConfig.java](telepathicgrunt/structure_layout_optimizer/SloConfig.java) |
| `Main` | class | — | Mod | 0 | 13 | [Main.java](telepathicgrunt/structure_layout_optimizer/neoforge/entrypoints/Main.java) |
| `JigsawPlacementMixin` | class | — | Mixin | 0 | 39 | [JigsawPlacementMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/JigsawPlacementMixin.java) |
| `SinglePoolElementMixin` | class | — | Mixin | 0 | 29 | [SinglePoolElementMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/SinglePoolElementMixin.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 15 | [StructureTemplatePoolAccessor.java](telepathicgrunt/structure_layout_optimizer/mixins/StructureTemplatePoolAccessor.java) |
| `StructureTemplateMixin` | class | — | Mixin | 0 | 27 | [StructureTemplateMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/StructureTemplateMixin.java) |
| `JigsawPlacementPlacerMixin` | class | — | Mixin | 0 | 195 | [JigsawPlacementPlacerMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/JigsawPlacementPlacerMixin.java) |
| `GeneralUtils` | class | — | — | 7 | 90 | [GeneralUtils.java](telepathicgrunt/structure_layout_optimizer/utils/GeneralUtils.java) |
| `BoxOctree` | class | — | — | 9 | 241 | [BoxOctree.java](telepathicgrunt/structure_layout_optimizer/utils/BoxOctree.java) |
| `StructureLayoutOptimizerMod` | class | — | — | 1 | 13 | [StructureLayoutOptimizerMod.java](telepathicgrunt/structure_layout_optimizer/StructureLayoutOptimizerMod.java) |
| `NeoPlatformService` | class | PlatformService | — | 1 | 11 | [NeoPlatformService.java](telepathicgrunt/structure_layout_optimizer/neoforge/services/NeoPlatformService.java) |
| `StructureTemplateOptimizer` | class | — | — | 3 | 110 | [StructureTemplateOptimizer.java](telepathicgrunt/structure_layout_optimizer/utils/StructureTemplateOptimizer.java) |
| `TrojanVoxelShape` | class | VoxelShape | — | 1 | 20 | [TrojanVoxelShape.java](telepathicgrunt/structure_layout_optimizer/utils/TrojanVoxelShape.java) |
| `TrojanArrayList` | class | ArrayList | — | 0 | 11 | [TrojanArrayList.java](telepathicgrunt/structure_layout_optimizer/utils/TrojanArrayList.java) |
| `PlatformService` | interface | — | — | 0 | 10 | [PlatformService.java](telepathicgrunt/structure_layout_optimizer/services/PlatformService.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `JigsawPlacementMixin` | `JigsawPlacement` | [JigsawPlacementMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/JigsawPlacementMixin.java) |
| `SinglePoolElementMixin` | `SinglePoolElement` | [SinglePoolElementMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/SinglePoolElementMixin.java) |
| `StructureTemplatePoolAccessor` | `StructureTemplatePoolAccessor` | [StructureTemplatePoolAccessor.java](telepathicgrunt/structure_layout_optimizer/mixins/StructureTemplatePoolAccessor.java) |
| `StructureTemplateMixin` | `StructureTemplate` | [StructureTemplateMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/StructureTemplateMixin.java) |
| `JigsawPlacementPlacerMixin` | `JigsawPlacementPlacer` | [JigsawPlacementPlacerMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/JigsawPlacementPlacerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `SloConfig` | Config;ConfigInfo | [SloConfig.java](telepathicgrunt/structure_layout_optimizer/SloConfig.java) |

## 全部类（15）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SloConfig` | class | — | Config、ConfigInfo | 0 | 46 | [SloConfig.java](telepathicgrunt/structure_layout_optimizer/SloConfig.java) |
| `Main` | class | — | Mod | 0 | 13 | [Main.java](telepathicgrunt/structure_layout_optimizer/neoforge/entrypoints/Main.java) |
| `JigsawPlacementMixin` | class | — | Mixin | 0 | 39 | [JigsawPlacementMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/JigsawPlacementMixin.java) |
| `SinglePoolElementMixin` | class | — | Mixin | 0 | 29 | [SinglePoolElementMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/SinglePoolElementMixin.java) |
| `StructureTemplatePoolAccessor` | interface | — | Mixin | 0 | 15 | [StructureTemplatePoolAccessor.java](telepathicgrunt/structure_layout_optimizer/mixins/StructureTemplatePoolAccessor.java) |
| `StructureTemplateMixin` | class | — | Mixin | 0 | 27 | [StructureTemplateMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/StructureTemplateMixin.java) |
| `JigsawPlacementPlacerMixin` | class | — | Mixin | 0 | 195 | [JigsawPlacementPlacerMixin.java](telepathicgrunt/structure_layout_optimizer/mixins/JigsawPlacementPlacerMixin.java) |
| `GeneralUtils` | class | — | — | 7 | 90 | [GeneralUtils.java](telepathicgrunt/structure_layout_optimizer/utils/GeneralUtils.java) |
| `BoxOctree` | class | — | — | 9 | 241 | [BoxOctree.java](telepathicgrunt/structure_layout_optimizer/utils/BoxOctree.java) |
| `StructureLayoutOptimizerMod` | class | — | — | 1 | 13 | [StructureLayoutOptimizerMod.java](telepathicgrunt/structure_layout_optimizer/StructureLayoutOptimizerMod.java) |
| `NeoPlatformService` | class | PlatformService | — | 1 | 11 | [NeoPlatformService.java](telepathicgrunt/structure_layout_optimizer/neoforge/services/NeoPlatformService.java) |
| `StructureTemplateOptimizer` | class | — | — | 3 | 110 | [StructureTemplateOptimizer.java](telepathicgrunt/structure_layout_optimizer/utils/StructureTemplateOptimizer.java) |
| `TrojanVoxelShape` | class | VoxelShape | — | 1 | 20 | [TrojanVoxelShape.java](telepathicgrunt/structure_layout_optimizer/utils/TrojanVoxelShape.java) |
| `TrojanArrayList` | class | ArrayList | — | 0 | 11 | [TrojanArrayList.java](telepathicgrunt/structure_layout_optimizer/utils/TrojanArrayList.java) |
| `PlatformService` | interface | — | — | 0 | 10 | [PlatformService.java](telepathicgrunt/structure_layout_optimizer/services/PlatformService.java) |
