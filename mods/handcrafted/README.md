# Handcrafted（`handcrafted`）

> jar：`[精巧手艺]handcrafted.jar` ｜ 版本：`4.0.3` ｜ 作者：Alex Nijjar, Kekie6
> 规模：**71 个类 / 24 个包 / 6952 行** ｜ 事件处理类 1 ｜ Mixin 1 ｜ 注册/管理类 1

**官方描述**：Epic Furniture mod!

**依赖**：`neoforge`、`minecraft`、`resourcefullib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `earth.terrarium.handcrafted.common.blocks` | 17 | `FancyBedBlock`、`TableBlock`、`DrawerBlock` |
| `earth.terrarium.handcrafted.common.blocks.base.properties` | 7 | `DirectionalBlockProperty`、`ColorProperty`、`OptionalColorProperty` |
| `earth.terrarium.handcrafted.common.registry` | 5 | `ModSoundEvents`、`ModBlocks`、`ModItems` |
| `earth.terrarium.handcrafted.common.blocks.misc` | 5 | `StackableJarBlock`、`KitchenHoodBlock`、`PotBlock` |
| `earth.terrarium.handcrafted.common.items` | 4 | `TooltipBlockItem`、`CustomPaintingItem`、`HammerItem` |
| `earth.terrarium.handcrafted.common.blocks.base` | 4 | `ModularSeatBlock`、`SittableBlock`、`SimpleBlock` |
| `earth.terrarium.handcrafted.common.blocks.crockery` | 3 | `CrockeryBlockEntity`、`CrockeryBlock`、`CrockeryComboBlock` |
| `earth.terrarium.handcrafted.common.blocks.trophies` | 3 | `StatueTrophyBlock`、`WallTrophyBlock`、`HangingTrophyBlock` |
| `earth.terrarium.handcrafted.common.tags` | 3 | `ModItemTags`、`ModBlockTags`、`ModPaintingVariantTags` |
| `earth.terrarium.handcrafted.common.blockentities` | 2 | `ContainerBlockEntity`、`OvenBlockEntity` |
| `earth.terrarium.handcrafted.common.blocks.trims` | 2 | `CornerTrimBlock`、`PillarTrimBlock` |
| `earth.terrarium.handcrafted.common.entities` | 2 | `Seat`、`FancyPainting` |
| `earth.terrarium.handcrafted.common.utils` | 2 | `InteractionUtils`、`TooltipUtils` |
| `earth.terrarium.handcrafted.client.renderer.fancypainting` | 2 | `FancyPaintingRenderer`、`FancyPaintingModel` |
| `earth.terrarium.handcrafted.client.neoforge` | 1 | `HandcraftedClientNeoForge` |
| `earth.terrarium.handcrafted.neoforge` | 1 | `HandcraftedNeoForge` |
| `earth.terrarium.handcrafted.mixins.common` | 1 | `BlockElementMixin` |
| `earth.terrarium.handcrafted.client.utils` | 1 | `ClientPlatformUtils` |
| `earth.terrarium.handcrafted.client.utils.neoforge` | 1 | `ClientPlatformUtilsImpl` |
| `earth.terrarium.handcrafted.client` | 1 | `HandcraftedClient` |
| `earth.terrarium.handcrafted.client.renderer` | 1 | `CrockeryRenderer` |
| `architectury_inject_handcrafted_common_da71c6c9875d49a0abcc5f7027b74f29_ca6ac048e2c766783f641a5662e22a49b682321931fceb35fd8c24c871986438handcraftedcommon1211403devjar` | 1 | `PlatformMethods` |
| `earth.terrarium.handcrafted` | 1 | `Handcrafted` |
| `earth.terrarium.handcrafted.common.constants` | 1 | `ConstantComponents` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `HandcraftedClientNeoForge` | class | — | EventBusSubscriber | 5 | 42 | [HandcraftedClientNeoForge.java](earth/terrarium/handcrafted/client/neoforge/HandcraftedClientNeoForge.java) |
| `HandcraftedNeoForge` | class | — | Mod | 0 | 17 | [HandcraftedNeoForge.java](earth/terrarium/handcrafted/neoforge/HandcraftedNeoForge.java) |
| `BlockElementMixin` | class | — | Mixin | 1 | 32 | [BlockElementMixin.java](earth/terrarium/handcrafted/mixins/common/BlockElementMixin.java) |
| `ModSoundEvents` | class | — | — | 0 | 19 | [ModSoundEvents.java](earth/terrarium/handcrafted/common/registry/ModSoundEvents.java) |
| `ModBlocks` | class | — | — | 0 | 841 | [ModBlocks.java](earth/terrarium/handcrafted/common/registry/ModBlocks.java) |
| `ModItems` | class | — | — | 0 | 878 | [ModItems.java](earth/terrarium/handcrafted/common/registry/ModItems.java) |
| `ContainerBlockEntity` | class | RandomizableContainerBlockEntity | — | 9 | 74 | [ContainerBlockEntity.java](earth/terrarium/handcrafted/common/blockentities/ContainerBlockEntity.java) |
| `CrockeryBlockEntity` | class | BlockEntity | — | 7 | 58 | [CrockeryBlockEntity.java](earth/terrarium/handcrafted/common/blocks/crockery/CrockeryBlockEntity.java) |
| `CornerTrimBlock` | class | SimpleBlock | — | 8 | 411 | [CornerTrimBlock.java](earth/terrarium/handcrafted/common/blocks/trims/CornerTrimBlock.java) |
| `PillarTrimBlock` | class | FaceAttachedHorizontalDirectionalBlo | — | 10 | 244 | [PillarTrimBlock.java](earth/terrarium/handcrafted/common/blocks/trims/PillarTrimBlock.java) |
| `OvenBlockEntity` | class | AbstractFurnaceBlockEntity | — | 3 | 32 | [OvenBlockEntity.java](earth/terrarium/handcrafted/common/blockentities/OvenBlockEntity.java) |
| `TooltipBlockItem` | class | BlockItem | — | 1 | 25 | [TooltipBlockItem.java](earth/terrarium/handcrafted/common/items/TooltipBlockItem.java) |
| `ModBlockEntityTypes` | class | — | — | 2 | 52 | [ModBlockEntityTypes.java](earth/terrarium/handcrafted/common/registry/ModBlockEntityTypes.java) |
| `Seat` | class | Entity | MethodsReturnNonnullByDefault | 21 | 185 | [Seat.java](earth/terrarium/handcrafted/common/entities/Seat.java) |
| `FancyBedBlock` | class | BedBlock | — | 11 | 157 | [FancyBedBlock.java](earth/terrarium/handcrafted/common/blocks/FancyBedBlock.java) |
| `TableBlock` | class | Block | — | 10 | 196 | [TableBlock.java](earth/terrarium/handcrafted/common/blocks/TableBlock.java) |
| `DrawerBlock` | class | HorizontalDirectionalBlock | — | 11 | 130 | [DrawerBlock.java](earth/terrarium/handcrafted/common/blocks/DrawerBlock.java) |
| `CupboardBlock` | class | HorizontalDirectionalBlock | — | 10 | 111 | [CupboardBlock.java](earth/terrarium/handcrafted/common/blocks/CupboardBlock.java) |
| `CounterBlock` | class | HorizontalDirectionalBlock | — | 10 | 124 | [CounterBlock.java](earth/terrarium/handcrafted/common/blocks/CounterBlock.java) |
| `ChairBlock` | class | HorizontalDirectionalBlock | — | 11 | 123 | [ChairBlock.java](earth/terrarium/handcrafted/common/blocks/ChairBlock.java) |
| `ShelfBlock` | class | HorizontalDirectionalBlock | — | 11 | 130 | [ShelfBlock.java](earth/terrarium/handcrafted/common/blocks/ShelfBlock.java) |
| `StatueTrophyBlock` | class | SimpleBlock | — | 10 | 114 | [StatueTrophyBlock.java](earth/terrarium/handcrafted/common/blocks/trophies/StatueTrophyBlock.java) |
| `DiningBenchBlock` | class | HorizontalDirectionalBlock | — | 9 | 123 | [DiningBenchBlock.java](earth/terrarium/handcrafted/common/blocks/DiningBenchBlock.java) |
| `NightstandBlock` | class | SimpleTableBlock | — | 5 | 64 | [NightstandBlock.java](earth/terrarium/handcrafted/common/blocks/NightstandBlock.java) |
| `StackableBookBlock` | class | HorizontalDirectionalBlock | — | 7 | 105 | [StackableBookBlock.java](earth/terrarium/handcrafted/common/blocks/StackableBookBlock.java) |
| `DeskBlock` | class | SimpleTableBlock | — | 5 | 64 | [DeskBlock.java](earth/terrarium/handcrafted/common/blocks/DeskBlock.java) |
| `SideTableBlock` | class | SimpleTableBlock | — | 5 | 64 | [SideTableBlock.java](earth/terrarium/handcrafted/common/blocks/SideTableBlock.java) |
| `BenchBlock` | class | ModularSeatBlock | — | 7 | 92 | [BenchBlock.java](earth/terrarium/handcrafted/common/blocks/BenchBlock.java) |
| `SimpleTableBlock` | class | HorizontalDirectionalBlock | — | 8 | 102 | [SimpleTableBlock.java](earth/terrarium/handcrafted/common/blocks/SimpleTableBlock.java) |
| `CouchBlock` | class | ModularSeatBlock | — | 7 | 92 | [CouchBlock.java](earth/terrarium/handcrafted/common/blocks/CouchBlock.java) |
| `ModularSeatBlock` | class | HorizontalDirectionalBlock | — | 8 | 147 | [ModularSeatBlock.java](earth/terrarium/handcrafted/common/blocks/base/ModularSeatBlock.java) |
| `StackableJarBlock` | class | SimpleBlock | — | 5 | 74 | [StackableJarBlock.java](earth/terrarium/handcrafted/common/blocks/misc/StackableJarBlock.java) |
| `CrockeryBlock` | class | SimpleBlock | — | 6 | 91 | [CrockeryBlock.java](earth/terrarium/handcrafted/common/blocks/crockery/CrockeryBlock.java) |
| `CrockeryComboBlock` | class | SimpleBlock | — | 8 | 100 | [CrockeryComboBlock.java](earth/terrarium/handcrafted/common/blocks/crockery/CrockeryComboBlock.java) |
| `InteractionUtils` | class | — | — | 5 | 239 | [InteractionUtils.java](earth/terrarium/handcrafted/common/utils/InteractionUtils.java) |
| `ClientPlatformUtils` | class | — | — | 1 | 34 | [ClientPlatformUtils.java](earth/terrarium/handcrafted/client/utils/ClientPlatformUtils.java) |
| `ClientPlatformUtilsImpl` | class | — | — | 1 | 14 | [ClientPlatformUtilsImpl.java](earth/terrarium/handcrafted/client/utils/neoforge/ClientPlatformUtilsImpl.java) |
| `CustomPaintingItem` | class | HangingEntityItem | — | 3 | 103 | [CustomPaintingItem.java](earth/terrarium/handcrafted/common/items/CustomPaintingItem.java) |
| `HammerItem` | class | Item | — | 2 | 45 | [HammerItem.java](earth/terrarium/handcrafted/common/items/HammerItem.java) |
| `TooltipItem` | class | Item | — | 1 | 24 | [TooltipItem.java](earth/terrarium/handcrafted/common/items/TooltipItem.java) |
| `ModItemTags` | class | — | — | 1 | 19 | [ModItemTags.java](earth/terrarium/handcrafted/common/tags/ModItemTags.java) |
| `ModBlockTags` | class | — | — | 1 | 15 | [ModBlockTags.java](earth/terrarium/handcrafted/common/tags/ModBlockTags.java) |
| `ModEntityTypes` | class | — | — | 0 | 23 | [ModEntityTypes.java](earth/terrarium/handcrafted/common/registry/ModEntityTypes.java) |
| `CushionBlock` | class | Block | — | 4 | 50 | [CushionBlock.java](earth/terrarium/handcrafted/common/blocks/CushionBlock.java) |
| `MetalBenchBlock` | class | ModularSeatBlock | — | 3 | 45 | [MetalBenchBlock.java](earth/terrarium/handcrafted/common/blocks/MetalBenchBlock.java) |
| `SittableBlock` | interface | — | — | 0 | 28 | [SittableBlock.java](earth/terrarium/handcrafted/common/blocks/base/SittableBlock.java) |
| `SimpleBlock` | class | HorizontalDirectionalBlock | — | 4 | 49 | [SimpleBlock.java](earth/terrarium/handcrafted/common/blocks/base/SimpleBlock.java) |
| `DirectionalBlockProperty` | enum | StringRepresentable | — | 2 | 23 | [DirectionalBlockProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/DirectionalBlockProperty.java) |
| `WallTrophyBlock` | class | SimpleBlock | — | 2 | 47 | [WallTrophyBlock.java](earth/terrarium/handcrafted/common/blocks/trophies/WallTrophyBlock.java) |
| `HangingTrophyBlock` | class | SimpleBlock | — | 1 | 19 | [HangingTrophyBlock.java](earth/terrarium/handcrafted/common/blocks/trophies/HangingTrophyBlock.java) |
| `KitchenHoodBlock` | class | SimpleBlock | MethodsReturnNonnullByDefault | 2 | 43 | [KitchenHoodBlock.java](earth/terrarium/handcrafted/common/blocks/misc/KitchenHoodBlock.java) |
| `PotBlock` | class | Block | — | 2 | 63 | [PotBlock.java](earth/terrarium/handcrafted/common/blocks/misc/PotBlock.java) |
| `KitchenHoodPipeBlock` | class | SimpleBlock | — | 4 | 111 | [KitchenHoodPipeBlock.java](earth/terrarium/handcrafted/common/blocks/misc/KitchenHoodPipeBlock.java) |
| `OvenBlock` | class | SmokerBlock | — | 3 | 40 | [OvenBlock.java](earth/terrarium/handcrafted/common/blocks/misc/OvenBlock.java) |
| `HandcraftedClient` | class | — | — | 4 | 29 | [HandcraftedClient.java](earth/terrarium/handcrafted/client/HandcraftedClient.java) |
| `CrockeryRenderer` | class | BlockEntityRenderer<CrockeryBlockEnt | — | 1 | 42 | [CrockeryRenderer.java](earth/terrarium/handcrafted/client/renderer/CrockeryRenderer.java) |
| `FancyPaintingRenderer` | class | EntityRenderer | — | 4 | 149 | [FancyPaintingRenderer.java](earth/terrarium/handcrafted/client/renderer/fancypainting/FancyPaintingRenderer.java) |
| `FancyPainting` | class | Painting | — | 5 | 59 | [FancyPainting.java](earth/terrarium/handcrafted/common/entities/FancyPainting.java) |
| `ColorProperty` | enum | StringRepresentable | — | 6 | 56 | [ColorProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/ColorProperty.java) |
| `OptionalColorProperty` | enum | StringRepresentable | — | 6 | 60 | [OptionalColorProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/OptionalColorProperty.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `HandcraftedClientNeoForge` | 3 | `earth.terrarium.handcrafted.client.neoforge` | [HandcraftedClientNeoForge.java](earth/terrarium/handcrafted/client/neoforge/HandcraftedClientNeoForge.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockElementMixin` | `BlockElement` | [BlockElementMixin.java](earth/terrarium/handcrafted/mixins/common/BlockElementMixin.java) |

## 全部类（71）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `HandcraftedClientNeoForge` | class | — | EventBusSubscriber | 5 | 42 | [HandcraftedClientNeoForge.java](earth/terrarium/handcrafted/client/neoforge/HandcraftedClientNeoForge.java) |
| `HandcraftedNeoForge` | class | — | Mod | 0 | 17 | [HandcraftedNeoForge.java](earth/terrarium/handcrafted/neoforge/HandcraftedNeoForge.java) |
| `BlockElementMixin` | class | — | Mixin | 1 | 32 | [BlockElementMixin.java](earth/terrarium/handcrafted/mixins/common/BlockElementMixin.java) |
| `ModSoundEvents` | class | — | — | 0 | 19 | [ModSoundEvents.java](earth/terrarium/handcrafted/common/registry/ModSoundEvents.java) |
| `ModBlocks` | class | — | — | 0 | 841 | [ModBlocks.java](earth/terrarium/handcrafted/common/registry/ModBlocks.java) |
| `ModItems` | class | — | — | 0 | 878 | [ModItems.java](earth/terrarium/handcrafted/common/registry/ModItems.java) |
| `ContainerBlockEntity` | class | RandomizableContainerBlockEntity | — | 9 | 74 | [ContainerBlockEntity.java](earth/terrarium/handcrafted/common/blockentities/ContainerBlockEntity.java) |
| `CrockeryBlockEntity` | class | BlockEntity | — | 7 | 58 | [CrockeryBlockEntity.java](earth/terrarium/handcrafted/common/blocks/crockery/CrockeryBlockEntity.java) |
| `CornerTrimBlock` | class | SimpleBlock | — | 8 | 411 | [CornerTrimBlock.java](earth/terrarium/handcrafted/common/blocks/trims/CornerTrimBlock.java) |
| `PillarTrimBlock` | class | FaceAttachedHorizontalDirectionalBlo | — | 10 | 244 | [PillarTrimBlock.java](earth/terrarium/handcrafted/common/blocks/trims/PillarTrimBlock.java) |
| `OvenBlockEntity` | class | AbstractFurnaceBlockEntity | — | 3 | 32 | [OvenBlockEntity.java](earth/terrarium/handcrafted/common/blockentities/OvenBlockEntity.java) |
| `TooltipBlockItem` | class | BlockItem | — | 1 | 25 | [TooltipBlockItem.java](earth/terrarium/handcrafted/common/items/TooltipBlockItem.java) |
| `ModBlockEntityTypes` | class | — | — | 2 | 52 | [ModBlockEntityTypes.java](earth/terrarium/handcrafted/common/registry/ModBlockEntityTypes.java) |
| `Seat` | class | Entity | MethodsReturnNonnullByDefault | 21 | 185 | [Seat.java](earth/terrarium/handcrafted/common/entities/Seat.java) |
| `FancyBedBlock` | class | BedBlock | — | 11 | 157 | [FancyBedBlock.java](earth/terrarium/handcrafted/common/blocks/FancyBedBlock.java) |
| `TableBlock` | class | Block | — | 10 | 196 | [TableBlock.java](earth/terrarium/handcrafted/common/blocks/TableBlock.java) |
| `DrawerBlock` | class | HorizontalDirectionalBlock | — | 11 | 130 | [DrawerBlock.java](earth/terrarium/handcrafted/common/blocks/DrawerBlock.java) |
| `CupboardBlock` | class | HorizontalDirectionalBlock | — | 10 | 111 | [CupboardBlock.java](earth/terrarium/handcrafted/common/blocks/CupboardBlock.java) |
| `CounterBlock` | class | HorizontalDirectionalBlock | — | 10 | 124 | [CounterBlock.java](earth/terrarium/handcrafted/common/blocks/CounterBlock.java) |
| `ChairBlock` | class | HorizontalDirectionalBlock | — | 11 | 123 | [ChairBlock.java](earth/terrarium/handcrafted/common/blocks/ChairBlock.java) |
| `ShelfBlock` | class | HorizontalDirectionalBlock | — | 11 | 130 | [ShelfBlock.java](earth/terrarium/handcrafted/common/blocks/ShelfBlock.java) |
| `StatueTrophyBlock` | class | SimpleBlock | — | 10 | 114 | [StatueTrophyBlock.java](earth/terrarium/handcrafted/common/blocks/trophies/StatueTrophyBlock.java) |
| `DiningBenchBlock` | class | HorizontalDirectionalBlock | — | 9 | 123 | [DiningBenchBlock.java](earth/terrarium/handcrafted/common/blocks/DiningBenchBlock.java) |
| `NightstandBlock` | class | SimpleTableBlock | — | 5 | 64 | [NightstandBlock.java](earth/terrarium/handcrafted/common/blocks/NightstandBlock.java) |
| `StackableBookBlock` | class | HorizontalDirectionalBlock | — | 7 | 105 | [StackableBookBlock.java](earth/terrarium/handcrafted/common/blocks/StackableBookBlock.java) |
| `DeskBlock` | class | SimpleTableBlock | — | 5 | 64 | [DeskBlock.java](earth/terrarium/handcrafted/common/blocks/DeskBlock.java) |
| `SideTableBlock` | class | SimpleTableBlock | — | 5 | 64 | [SideTableBlock.java](earth/terrarium/handcrafted/common/blocks/SideTableBlock.java) |
| `BenchBlock` | class | ModularSeatBlock | — | 7 | 92 | [BenchBlock.java](earth/terrarium/handcrafted/common/blocks/BenchBlock.java) |
| `SimpleTableBlock` | class | HorizontalDirectionalBlock | — | 8 | 102 | [SimpleTableBlock.java](earth/terrarium/handcrafted/common/blocks/SimpleTableBlock.java) |
| `CouchBlock` | class | ModularSeatBlock | — | 7 | 92 | [CouchBlock.java](earth/terrarium/handcrafted/common/blocks/CouchBlock.java) |
| `ModularSeatBlock` | class | HorizontalDirectionalBlock | — | 8 | 147 | [ModularSeatBlock.java](earth/terrarium/handcrafted/common/blocks/base/ModularSeatBlock.java) |
| `StackableJarBlock` | class | SimpleBlock | — | 5 | 74 | [StackableJarBlock.java](earth/terrarium/handcrafted/common/blocks/misc/StackableJarBlock.java) |
| `CrockeryBlock` | class | SimpleBlock | — | 6 | 91 | [CrockeryBlock.java](earth/terrarium/handcrafted/common/blocks/crockery/CrockeryBlock.java) |
| `CrockeryComboBlock` | class | SimpleBlock | — | 8 | 100 | [CrockeryComboBlock.java](earth/terrarium/handcrafted/common/blocks/crockery/CrockeryComboBlock.java) |
| `InteractionUtils` | class | — | — | 5 | 239 | [InteractionUtils.java](earth/terrarium/handcrafted/common/utils/InteractionUtils.java) |
| `ClientPlatformUtils` | class | — | — | 1 | 34 | [ClientPlatformUtils.java](earth/terrarium/handcrafted/client/utils/ClientPlatformUtils.java) |
| `ClientPlatformUtilsImpl` | class | — | — | 1 | 14 | [ClientPlatformUtilsImpl.java](earth/terrarium/handcrafted/client/utils/neoforge/ClientPlatformUtilsImpl.java) |
| `CustomPaintingItem` | class | HangingEntityItem | — | 3 | 103 | [CustomPaintingItem.java](earth/terrarium/handcrafted/common/items/CustomPaintingItem.java) |
| `HammerItem` | class | Item | — | 2 | 45 | [HammerItem.java](earth/terrarium/handcrafted/common/items/HammerItem.java) |
| `TooltipItem` | class | Item | — | 1 | 24 | [TooltipItem.java](earth/terrarium/handcrafted/common/items/TooltipItem.java) |
| `ModItemTags` | class | — | — | 1 | 19 | [ModItemTags.java](earth/terrarium/handcrafted/common/tags/ModItemTags.java) |
| `ModBlockTags` | class | — | — | 1 | 15 | [ModBlockTags.java](earth/terrarium/handcrafted/common/tags/ModBlockTags.java) |
| `ModEntityTypes` | class | — | — | 0 | 23 | [ModEntityTypes.java](earth/terrarium/handcrafted/common/registry/ModEntityTypes.java) |
| `CushionBlock` | class | Block | — | 4 | 50 | [CushionBlock.java](earth/terrarium/handcrafted/common/blocks/CushionBlock.java) |
| `MetalBenchBlock` | class | ModularSeatBlock | — | 3 | 45 | [MetalBenchBlock.java](earth/terrarium/handcrafted/common/blocks/MetalBenchBlock.java) |
| `SittableBlock` | interface | — | — | 0 | 28 | [SittableBlock.java](earth/terrarium/handcrafted/common/blocks/base/SittableBlock.java) |
| `SimpleBlock` | class | HorizontalDirectionalBlock | — | 4 | 49 | [SimpleBlock.java](earth/terrarium/handcrafted/common/blocks/base/SimpleBlock.java) |
| `DirectionalBlockProperty` | enum | StringRepresentable | — | 2 | 23 | [DirectionalBlockProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/DirectionalBlockProperty.java) |
| `WallTrophyBlock` | class | SimpleBlock | — | 2 | 47 | [WallTrophyBlock.java](earth/terrarium/handcrafted/common/blocks/trophies/WallTrophyBlock.java) |
| `HangingTrophyBlock` | class | SimpleBlock | — | 1 | 19 | [HangingTrophyBlock.java](earth/terrarium/handcrafted/common/blocks/trophies/HangingTrophyBlock.java) |
| `KitchenHoodBlock` | class | SimpleBlock | MethodsReturnNonnullByDefault | 2 | 43 | [KitchenHoodBlock.java](earth/terrarium/handcrafted/common/blocks/misc/KitchenHoodBlock.java) |
| `PotBlock` | class | Block | — | 2 | 63 | [PotBlock.java](earth/terrarium/handcrafted/common/blocks/misc/PotBlock.java) |
| `KitchenHoodPipeBlock` | class | SimpleBlock | — | 4 | 111 | [KitchenHoodPipeBlock.java](earth/terrarium/handcrafted/common/blocks/misc/KitchenHoodPipeBlock.java) |
| `OvenBlock` | class | SmokerBlock | — | 3 | 40 | [OvenBlock.java](earth/terrarium/handcrafted/common/blocks/misc/OvenBlock.java) |
| `HandcraftedClient` | class | — | — | 4 | 29 | [HandcraftedClient.java](earth/terrarium/handcrafted/client/HandcraftedClient.java) |
| `CrockeryRenderer` | class | BlockEntityRenderer<CrockeryBlockEnt | — | 1 | 42 | [CrockeryRenderer.java](earth/terrarium/handcrafted/client/renderer/CrockeryRenderer.java) |
| `FancyPaintingRenderer` | class | EntityRenderer | — | 4 | 149 | [FancyPaintingRenderer.java](earth/terrarium/handcrafted/client/renderer/fancypainting/FancyPaintingRenderer.java) |
| `FancyPainting` | class | Painting | — | 5 | 59 | [FancyPainting.java](earth/terrarium/handcrafted/common/entities/FancyPainting.java) |
| `ColorProperty` | enum | StringRepresentable | — | 6 | 56 | [ColorProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/ColorProperty.java) |
| `OptionalColorProperty` | enum | StringRepresentable | — | 6 | 60 | [OptionalColorProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/OptionalColorProperty.java) |
| `TooltipUtils` | class | — | — | 1 | 29 | [TooltipUtils.java](earth/terrarium/handcrafted/common/utils/TooltipUtils.java) |
| `FancyPaintingModel` | class | — | — | 6 | 158 | [FancyPaintingModel.java](earth/terrarium/handcrafted/client/renderer/fancypainting/FancyPaintingModel.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_handcrafted_common_da71c6c9875d49a0abcc5f7027b74f29_ca6ac048e2c766783f641a5662e22a49b682321931fceb35fd8c24c871986438handcraftedcommon1211403devjar/PlatformMethods.java) |
| `Handcrafted` | class | — | — | 1 | 21 | [Handcrafted.java](earth/terrarium/handcrafted/Handcrafted.java) |
| `ModPaintingVariantTags` | class | — | — | 1 | 15 | [ModPaintingVariantTags.java](earth/terrarium/handcrafted/common/tags/ModPaintingVariantTags.java) |
| `Hammerable` | interface | — | — | 0 | 13 | [Hammerable.java](earth/terrarium/handcrafted/common/blocks/base/Hammerable.java) |
| `TrimProperty` | enum | StringRepresentable | — | 2 | 22 | [TrimProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/TrimProperty.java) |
| `TableProperty` | enum | StringRepresentable | — | 2 | 35 | [TableProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/TableProperty.java) |
| `ModularSeatProperty` | enum | StringRepresentable | — | 3 | 35 | [ModularSeatProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/ModularSeatProperty.java) |
| `CounterProperty` | enum | StringRepresentable | — | 4 | 52 | [CounterProperty.java](earth/terrarium/handcrafted/common/blocks/base/properties/CounterProperty.java) |
| `ConstantComponents` | class | — | — | 0 | 22 | [ConstantComponents.java](earth/terrarium/handcrafted/common/constants/ConstantComponents.java) |
