# Lootr（`lootr`）

> jar：`[宝箱]lootr.jar` ｜ 版本：`1.21.1-1.11.37.122` ｜ 作者：Noobanidus
> 规模：**239 个类 / 73 个包 / 19949 行** ｜ 事件处理类 13 ｜ Mixin 33 ｜ 注册/管理类 8

**官方描述**：Generates unique loot for each player that opens a loot container.

**依赖**：`minecraft`、`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `noobanidus.mods.lootr.common.api` | 22 | `LootrAPI`、`ILootrBlockEntityConverter`、`ILootrEntityConverter` |
| `noobanidus.mods.lootr.common.api.data` | 15 | `CustomLootrInfoProvider`、`BaseLootrInfo`、`SimpleLootrInstance` |
| `noobanidus.mods.lootr.neoforge.gen` | 12 | `LootrDataGenerators`、`LootrBlockEntityTagsProvider`、`LootrEntityTagsProvider` |
| `noobanidus.mods.lootr.common.impl.type` | 12 | `ItemFrameLootrType`、`PotLootrType`、`BrushableLootrType` |
| `noobanidus.mods.lootr.common.mixin.accessor` | 10 | `AccessorMixinBaseContainerBlockEntity`、`AccessorMixinBrushableBlockEntity`、`AccessorMixinFallingBlockEntity` |
| `noobanidus.mods.lootr.neoforge.init` | 10 | `ModLoot`、`ModBlockProperties`、`ModBlockEntities` |
| `noobanidus.mods.lootr.common.block.entity` | 8 | `LootrBrushableBlockEntity`、`LootrDecoratedPotBlockEntity`、`LootrShulkerBlockEntity` |
| `noobanidus.mods.lootr.common.block` | 8 | `LootrInventoryBlock`、`LootrBarrelBlock`、`LootrChestBlock` |
| `noobanidus.mods.lootr.neoforge.event` | 7 | `HandleResourcePacks`、`HandleChunk`、`HandleDebug` |
| `noobanidus.mods.lootr.common.impl.command` | 7 | `ShulkerCommandType`、`PotCommandType`、`GravelCommandType` |
| `noobanidus.mods.lootr.common.impl.replacement` | 7 | `TrappedChestReplacementProvider`、`ShulkerReplacementProvider`、`PotReplacementProvider` |
| `noobanidus.mods.lootr.neoforge.network.toClient` | 6 | `PacketCloseContainer`、`PacketCloseCart`、`PacketPerformBreakEffect` |
| `noobanidus.mods.lootr.neoforge.client.block` | 5 | `LootrShulkerBlockRenderer`、`LootrChestBlockRenderer`、`BarrelModel` |
| `noobanidus.mods.lootr.common.impl.adapter` | 5 | `DecoratedPotBlockEntityAdapter`、`BrushableBlockEntityAdapter`、`RandomizableContainerBlockEntityAdapter` |
| `noobanidus.mods.lootr.common.mixin.ticker` | 4 | `MixinBrushableBlockEntity`、`MixinStructureTemplate`、`MixinRandomizableContainer` |
| `noobanidus.mods.lootr.common.client.item` | 4 | `LootrChestItemRenderer`、`LootrDecoratedPotItemRenderer`、`LootrShulkerItemRenderer` |
| `noobanidus.mods.lootr.common.api.adapter` | 4 | `ILootrItemFrameAdapter`、`ILootrDataAdapter`、`ILootrAdapter` |
| `noobanidus.mods.lootr.common.api.advancement` | 4 | `ILootedStatTrigger`、`ITrigger`、`IContainerTrigger` |
| `noobanidus.mods.lootr.neoforge.impl` | 3 | `LootrRegistryImpl`、`LootrAPIImpl`、`PlatformAPIImpl` |
| `noobanidus.mods.lootr.common.api.registry` | 3 | `LootrRegistry`、`ILootrRegistry`、`LootrProperties` |
| `noobanidus.mods.lootr.common.command` | 3 | `CommandLootr`、`IOUtil`、`CustomConvertJob` |
| `noobanidus.mods.lootr.common.impl` | 3 | `LootrServiceRegistry`、`DefaultLootrAPIImpl`、`DefaultPlatformAPIImpl` |
| `noobanidus.mods.lootr.common.api.data.blockentity` | 3 | `RandomizableContainerBlockEntityLootrInfoProvider`、`ILootrBlockEntity`、`LockMessageSuppression` |
| `noobanidus.mods.lootr.common.mixin.hopper` | 3 | `MixinHopperBlockEntity`、`MixinEntitySelector`、`MixinAbstractContainerMenu` |
| `noobanidus.mods.lootr.common.data` | 3 | `LootrSavedData`、`DataStorage`、`LootrInventory` |
| `noobanidus.mods.lootr.common.api.processor` | 3 | `ILootrBlockEntityProcessor`、`ILootrEntityProcessor`、`ILootrProcessor` |
| `noobanidus.mods.lootr.common.api.data.entity` | 3 | `AbstractMinecartContainerLootrInfoProvider`、`ILootrEntity`、`ILootrCart` |
| `noobanidus.mods.lootr.common.advancement` | 3 | `LootedStatTrigger`、`AdvancementTrigger`、`ContainerTrigger` |
| `noobanidus.mods.lootr.common.api.annotation` | 3 | `ClientOnly`、`MigrateName`、`ServerOnly` |
| `noobanidus.mods.lootr.neoforge.network` | 2 | `PacketHandler`、`ILootrNeoForgePacket` |
| `noobanidus.mods.lootr.neoforge.gen.compat` | 2 | `LootrCompatDataGenerators`、`LootrCompatBlockTagProvider` |
| `noobanidus.mods.lootr.neoforge.gen.optionals` | 2 | `LootrNoAdvancementGenerator`、`LootrNoSuspiciousGenerator` |
| `noobanidus.mods.lootr.neoforge` | 2 | `LootrClient`、`Lootr` |
| `noobanidus.mods.lootr.neoforge.mixin.self` | 2 | `MixinLootrBarrelBlockEntity`、`MixinLootrBrushableBlockEntity` |
| `noobanidus.mods.lootr.common.entity` | 2 | `LootrItemFrame`、`LootrChestMinecartEntity` |
| `noobanidus.mods.lootr.common.client.entity` | 2 | `LootrItemFrameRenderer`、`LootrChestCartRenderer` |
| `noobanidus.mods.lootr.common.client.block` | 2 | `LootrBrushableBlockRenderer`、`LootrDecoratedPotRenderer` |
| `noobanidus.mods.lootr.common.mixin.poi` | 2 | `MixinPoiTypes`、`MixinPoiType` |
| `noobanidus.mods.lootr.common.api.replacement` | 2 | `ILootrBlockReplacementProvider`、`BlockReplacementMap` |
| `noobanidus.mods.lootr.common.api.filter` | 2 | `ILootrFilter`、`ILootrFilterProvider` |
| … | | 其余 33 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ConfigManager` | class | ConfigManagerBase | EventBusSubscriber | 26 | 491 | [ConfigManager.java](noobanidus/mods/lootr/neoforge/config/ConfigManager.java) |
| `PacketHandler` | class | — | EventBusSubscriber | 5 | 48 | [PacketHandler.java](noobanidus/mods/lootr/neoforge/network/PacketHandler.java) |
| `LootrCompatDataGenerators` | class | — | EventBusSubscriber | 2 | 129 | [LootrCompatDataGenerators.java](noobanidus/mods/lootr/neoforge/gen/compat/LootrCompatDataGenerators.java) |
| `HandleResourcePacks` | class | — | EventBusSubscriber | 1 | 45 | [HandleResourcePacks.java](noobanidus/mods/lootr/neoforge/event/HandleResourcePacks.java) |
| `LootrDataGenerators` | class | — | EventBusSubscriber | 1 | 37 | [LootrDataGenerators.java](noobanidus/mods/lootr/neoforge/gen/LootrDataGenerators.java) |
| `ClientSetup` | class | — | EventBusSubscriber | 10 | 100 | [ClientSetup.java](noobanidus/mods/lootr/neoforge/setup/ClientSetup.java) |
| `LootrNoAdvancementGenerator` | class | AdvancementGenerator | EventBusSubscriber | 3 | 102 | [LootrNoAdvancementGenerator.java](noobanidus/mods/lootr/neoforge/gen/optionals/LootrNoAdvancementGenerator.java) |
| `LootrRegistryImpl` | class | ILootrRegistry | — | 42 | 243 | [LootrRegistryImpl.java](noobanidus/mods/lootr/neoforge/impl/LootrRegistryImpl.java) |
| `LootrClient` | class | — | Mod | 0 | 16 | [LootrClient.java](noobanidus/mods/lootr/neoforge/LootrClient.java) |
| `LootrBrushableBlockEntity` | class | BlockEntity | — | 46 | 581 | [LootrBrushableBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrBrushableBlockEntity.java) |
| `LootrDecoratedPotBlockEntity` | class | BlockEntity | — | 52 | 420 | [LootrDecoratedPotBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrDecoratedPotBlockEntity.java) |
| `LootrRegistry` | class | — | — | 43 | 193 | [LootrRegistry.java](noobanidus/mods/lootr/common/api/registry/LootrRegistry.java) |
| `HandleChunk` | class | — | EventBusSubscriber | 4 | 41 | [HandleChunk.java](noobanidus/mods/lootr/neoforge/event/HandleChunk.java) |
| `HandleDebug` | class | — | EventBusSubscriber | 1 | 15 | [HandleDebug.java](noobanidus/mods/lootr/neoforge/event/HandleDebug.java) |
| `HandleTick` | class | — | EventBusSubscriber | 1 | 15 | [HandleTick.java](noobanidus/mods/lootr/neoforge/event/HandleTick.java) |
| `HandleCart` | class | — | EventBusSubscriber | 1 | 77 | [HandleCart.java](noobanidus/mods/lootr/neoforge/event/HandleCart.java) |
| `HandleBreak` | class | — | EventBusSubscriber | 1 | 48 | [HandleBreak.java](noobanidus/mods/lootr/neoforge/event/HandleBreak.java) |
| `HandleAdvancement` | class | — | EventBusSubscriber | 1 | 18 | [HandleAdvancement.java](noobanidus/mods/lootr/neoforge/event/HandleAdvancement.java) |
| `LootrShulkerBlockEntity` | class | RandomizableContainerBlockEntity | — | 47 | 387 | [LootrShulkerBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrShulkerBlockEntity.java) |
| `LootrBarrelBlockEntity` | class | RandomizableContainerBlockEntity | — | 45 | 301 | [LootrBarrelBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrBarrelBlockEntity.java) |
| `LootrChestBlockEntity` | class | ChestBlockEntity | — | 43 | 302 | [LootrChestBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrChestBlockEntity.java) |
| `Lootr` | class | — | Mod | 2 | 56 | [Lootr.java](noobanidus/mods/lootr/neoforge/Lootr.java) |
| `MixinLootrBarrelBlockEntity` | class | IBlockEntityExtension | Mixin | 1 | 20 | [MixinLootrBarrelBlockEntity.java](noobanidus/mods/lootr/neoforge/mixin/self/MixinLootrBarrelBlockEntity.java) |
| `MixinLootrBrushableBlockEntity` | class | IBlockEntityExtension | Mixin | 1 | 20 | [MixinLootrBrushableBlockEntity.java](noobanidus/mods/lootr/neoforge/mixin/self/MixinLootrBrushableBlockEntity.java) |
| `LootrItemFrame` | class | ItemFrame | — | 45 | 433 | [LootrItemFrame.java](noobanidus/mods/lootr/common/entity/LootrItemFrame.java) |
| `LootrChestMinecartEntity` | class | AbstractMinecartContainer | — | 41 | 337 | [LootrChestMinecartEntity.java](noobanidus/mods/lootr/common/entity/LootrChestMinecartEntity.java) |
| `LootrAPI` | class | — | — | 125 | 627 | [LootrAPI.java](noobanidus/mods/lootr/common/api/LootrAPI.java) |
| `CommandLootr` | class | — | — | 10 | 930 | [CommandLootr.java](noobanidus/mods/lootr/common/command/CommandLootr.java) |
| `LootrServiceRegistry` | class | — | — | 7 | 216 | [LootrServiceRegistry.java](noobanidus/mods/lootr/common/impl/LootrServiceRegistry.java) |
| `DefaultLootrAPIImpl` | class | ILootrAPI | — | 41 | 485 | [DefaultLootrAPIImpl.java](noobanidus/mods/lootr/common/impl/DefaultLootrAPIImpl.java) |
| `RandomizableContainerBlockEntityLootrInfoProvider` | record | ILootrBlockEntity | — | 21 | 146 | [RandomizableContainerBlockEntityLootrInfoProvider.java](noobanidus/mods/lootr/common/api/data/blockentity/RandomizableContainerBlockEntityLootrInfoProvider.java) |
| `LootrAPIImpl` | class | DefaultLootrAPIImpl | — | 67 | 380 | [LootrAPIImpl.java](noobanidus/mods/lootr/neoforge/impl/LootrAPIImpl.java) |
| `MixinBrushableBlockEntity` | class | — | Mixin | 0 | 28 | [MixinBrushableBlockEntity.java](noobanidus/mods/lootr/common/mixin/ticker/MixinBrushableBlockEntity.java) |
| `AccessorMixinBaseContainerBlockEntity` | interface | — | Mixin | 0 | 22 | [AccessorMixinBaseContainerBlockEntity.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinBaseContainerBlockEntity.java) |
| `AccessorMixinBrushableBlockEntity` | interface | — | Mixin | 0 | 17 | [AccessorMixinBrushableBlockEntity.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinBrushableBlockEntity.java) |
| `AccessorMixinFallingBlockEntity` | interface | — | Mixin | 0 | 13 | [AccessorMixinFallingBlockEntity.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinFallingBlockEntity.java) |
| `MixinHopperBlockEntity` | class | — | Mixin | 0 | 41 | [MixinHopperBlockEntity.java](noobanidus/mods/lootr/common/mixin/hopper/MixinHopperBlockEntity.java) |
| `MixinBaseContainerBlockEntity` | class | — | Mixin | 0 | 41 | [MixinBaseContainerBlockEntity.java](noobanidus/mods/lootr/common/mixin/lock/MixinBaseContainerBlockEntity.java) |
| `MixinFallingBlockEntity` | class | — | Mixin | 0 | 29 | [MixinFallingBlockEntity.java](noobanidus/mods/lootr/common/mixin/falling/MixinFallingBlockEntity.java) |
| `AccessorMixinLootTable` | interface | — | Mixin | 0 | 14 | [AccessorMixinLootTable.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinLootTable.java) |
| `MixinLootTable` | class | — | Mixin | 1 | 44 | [MixinLootTable.java](noobanidus/mods/lootr/common/mixin/filter/MixinLootTable.java) |
| `MixinServerGamePacketListenerImpl` | class | — | Mixin | 1 | 31 | [MixinServerGamePacketListenerImpl.java](noobanidus/mods/lootr/common/mixin/spawn/MixinServerGamePacketListenerImpl.java) |
| `ConfigManagerBase` | class | — | — | 3 | 81 | [ConfigManagerBase.java](noobanidus/mods/lootr/common/config/ConfigManagerBase.java) |
| `LootrSavedData` | class | SavedData | — | 22 | 264 | [LootrSavedData.java](noobanidus/mods/lootr/common/data/LootrSavedData.java) |
| `ILootrRegistry` | interface | — | Internal | 0 | 109 | [ILootrRegistry.java](noobanidus/mods/lootr/common/api/registry/ILootrRegistry.java) |
| `ClientHandlers` | class | — | — | 5 | 81 | [ClientHandlers.java](noobanidus/mods/lootr/neoforge/network/client/ClientHandlers.java) |
| `MixinModdedBrushItem` | class | IModdedBrushItem | Mixin、Pseudo | 0 | 57 | [MixinModdedBrushItem.java](noobanidus/mods/lootr/neoforge/mixin/integration/digsite_workshop/MixinModdedBrushItem.java) |
| `LootrInventoryBlock` | class | ChestBlock | — | 17 | 136 | [LootrInventoryBlock.java](noobanidus/mods/lootr/common/block/LootrInventoryBlock.java) |
| `LootrInventoryBlockEntity` | class | LootrChestBlockEntity | — | 6 | 60 | [LootrInventoryBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrInventoryBlockEntity.java) |
| `LootrTrappedChestBlockEntity` | class | LootrChestBlockEntity | — | 5 | 50 | [LootrTrappedChestBlockEntity.java](noobanidus/mods/lootr/common/block/entity/LootrTrappedChestBlockEntity.java) |
| `MixinCatSitOnBlockGoal` | class | — | Mixin | 2 | 45 | [MixinCatSitOnBlockGoal.java](noobanidus/mods/lootr/common/mixin/cat/MixinCatSitOnBlockGoal.java) |
| `AccessorMixinShulkerBoxMenu` | interface | — | Mixin | 0 | 13 | [AccessorMixinShulkerBoxMenu.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinShulkerBoxMenu.java) |
| `AccessorMixinBlock` | interface | — | Mixin | 0 | 16 | [AccessorMixinBlock.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinBlock.java) |
| `AccessorMixinItemFrame` | interface | — | Mixin | 0 | 23 | [AccessorMixinItemFrame.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinItemFrame.java) |
| `MixinEntitySelector` | class | — | Mixin | 0 | 18 | [MixinEntitySelector.java](noobanidus/mods/lootr/common/mixin/hopper/MixinEntitySelector.java) |
| `MixinAbstractContainerMenu` | class | — | Mixin | 0 | 23 | [MixinAbstractContainerMenu.java](noobanidus/mods/lootr/common/mixin/hopper/MixinAbstractContainerMenu.java) |
| `MixinAbstractContainerMenu` | class | — | Mixin | 0 | 26 | [MixinAbstractContainerMenu.java](noobanidus/mods/lootr/common/mixin/redstone/MixinAbstractContainerMenu.java) |
| `MixinChestBlock` | class | — | Mixin | 1 | 36 | [MixinChestBlock.java](noobanidus/mods/lootr/common/mixin/chest_blocking/MixinChestBlock.java) |
| `MixinBrushItem` | class | — | Mixin | 0 | 46 | [MixinBrushItem.java](noobanidus/mods/lootr/common/mixin/brushing/MixinBrushItem.java) |
| `LootrBlockEntityTagsProvider` | class | IntrinsicHolderTagsProvider | — | 2 | 86 | [LootrBlockEntityTagsProvider.java](noobanidus/mods/lootr/neoforge/gen/LootrBlockEntityTagsProvider.java) |

## 事件处理（`@SubscribeEvent`，13 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientSetup` | 6 | `noobanidus.mods.lootr.neoforge.setup` | [ClientSetup.java](noobanidus/mods/lootr/neoforge/setup/ClientSetup.java) |
| `HandleChunk` | 4 | `noobanidus.mods.lootr.neoforge.event` | [HandleChunk.java](noobanidus/mods/lootr/neoforge/event/HandleChunk.java) |
| `ConfigManager` | 2 | `noobanidus.mods.lootr.neoforge.config` | [ConfigManager.java](noobanidus/mods/lootr/neoforge/config/ConfigManager.java) |
| `PacketHandler` | 1 | `noobanidus.mods.lootr.neoforge.network` | [PacketHandler.java](noobanidus/mods/lootr/neoforge/network/PacketHandler.java) |
| `LootrCompatDataGenerators` | 1 | `noobanidus.mods.lootr.neoforge.gen.compat` | [LootrCompatDataGenerators.java](noobanidus/mods/lootr/neoforge/gen/compat/LootrCompatDataGenerators.java) |
| `HandleResourcePacks` | 1 | `noobanidus.mods.lootr.neoforge.event` | [HandleResourcePacks.java](noobanidus/mods/lootr/neoforge/event/HandleResourcePacks.java) |
| `LootrDataGenerators` | 1 | `noobanidus.mods.lootr.neoforge.gen` | [LootrDataGenerators.java](noobanidus/mods/lootr/neoforge/gen/LootrDataGenerators.java) |
| `LootrNoAdvancementGenerator` | 1 | `noobanidus.mods.lootr.neoforge.gen.optionals` | [LootrNoAdvancementGenerator.java](noobanidus/mods/lootr/neoforge/gen/optionals/LootrNoAdvancementGenerator.java) |
| `HandleDebug` | 1 | `noobanidus.mods.lootr.neoforge.event` | [HandleDebug.java](noobanidus/mods/lootr/neoforge/event/HandleDebug.java) |
| `HandleTick` | 1 | `noobanidus.mods.lootr.neoforge.event` | [HandleTick.java](noobanidus/mods/lootr/neoforge/event/HandleTick.java) |
| `HandleCart` | 1 | `noobanidus.mods.lootr.neoforge.event` | [HandleCart.java](noobanidus/mods/lootr/neoforge/event/HandleCart.java) |
| `HandleBreak` | 1 | `noobanidus.mods.lootr.neoforge.event` | [HandleBreak.java](noobanidus/mods/lootr/neoforge/event/HandleBreak.java) |
| `HandleAdvancement` | 1 | `noobanidus.mods.lootr.neoforge.event` | [HandleAdvancement.java](noobanidus/mods/lootr/neoforge/event/HandleAdvancement.java) |

## Mixin（33 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinLootrBarrelBlockEntity` | `LootrBarrelBlockEntity` | [MixinLootrBarrelBlockEntity.java](noobanidus/mods/lootr/neoforge/mixin/self/MixinLootrBarrelBlockEntity.java) |
| `MixinLootrBrushableBlockEntity` | `LootrBrushableBlockEntity` | [MixinLootrBrushableBlockEntity.java](noobanidus/mods/lootr/neoforge/mixin/self/MixinLootrBrushableBlockEntity.java) |
| `MixinBrushableBlockEntity` | `BrushableBlockEntity` | [MixinBrushableBlockEntity.java](noobanidus/mods/lootr/common/mixin/ticker/MixinBrushableBlockEntity.java) |
| `AccessorMixinBaseContainerBlockEntity` | `AccessorBaseContainerBlockEntity` | [AccessorMixinBaseContainerBlockEntity.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinBaseContainerBlockEntity.java) |
| `AccessorMixinBrushableBlockEntity` | `AccessorBrushableBlockEntity` | [AccessorMixinBrushableBlockEntity.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinBrushableBlockEntity.java) |
| `AccessorMixinFallingBlockEntity` | `AccessorFallingBlockEntity` | [AccessorMixinFallingBlockEntity.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinFallingBlockEntity.java) |
| `MixinHopperBlockEntity` | `HopperBlockEntity` | [MixinHopperBlockEntity.java](noobanidus/mods/lootr/common/mixin/hopper/MixinHopperBlockEntity.java) |
| `MixinBaseContainerBlockEntity` | `BaseContainerBlockEntity` | [MixinBaseContainerBlockEntity.java](noobanidus/mods/lootr/common/mixin/lock/MixinBaseContainerBlockEntity.java) |
| `MixinFallingBlockEntity` | `FallingBlockEntity` | [MixinFallingBlockEntity.java](noobanidus/mods/lootr/common/mixin/falling/MixinFallingBlockEntity.java) |
| `AccessorMixinLootTable` | `AccessorLootTable` | [AccessorMixinLootTable.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinLootTable.java) |
| `MixinLootTable` | `LootTable` | [MixinLootTable.java](noobanidus/mods/lootr/common/mixin/filter/MixinLootTable.java) |
| `MixinServerGamePacketListenerImpl` | `ServerGamePacketListenerImpl` | [MixinServerGamePacketListenerImpl.java](noobanidus/mods/lootr/common/mixin/spawn/MixinServerGamePacketListenerImpl.java) |
| `MixinModdedBrushItem` | `ModdedBrushItem` | [MixinModdedBrushItem.java](noobanidus/mods/lootr/neoforge/mixin/integration/digsite_workshop/MixinModdedBrushItem.java) |
| `MixinCatSitOnBlockGoal` | `CatSitOnBlockGoal` | [MixinCatSitOnBlockGoal.java](noobanidus/mods/lootr/common/mixin/cat/MixinCatSitOnBlockGoal.java) |
| `AccessorMixinShulkerBoxMenu` | `AccessorShulkerBoxMenu` | [AccessorMixinShulkerBoxMenu.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinShulkerBoxMenu.java) |
| `AccessorMixinBlock` | `AccessorBlock` | [AccessorMixinBlock.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinBlock.java) |
| `AccessorMixinItemFrame` | `AccessorItemFrame` | [AccessorMixinItemFrame.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinItemFrame.java) |
| `MixinEntitySelector` | `EntitySelector` | [MixinEntitySelector.java](noobanidus/mods/lootr/common/mixin/hopper/MixinEntitySelector.java) |
| `MixinAbstractContainerMenu` | `AbstractContainerMenu` | [MixinAbstractContainerMenu.java](noobanidus/mods/lootr/common/mixin/hopper/MixinAbstractContainerMenu.java) |
| `MixinAbstractContainerMenu` | `AbstractContainerMenu` | [MixinAbstractContainerMenu.java](noobanidus/mods/lootr/common/mixin/redstone/MixinAbstractContainerMenu.java) |
| `MixinChestBlock` | `ChestBlock` | [MixinChestBlock.java](noobanidus/mods/lootr/common/mixin/chest_blocking/MixinChestBlock.java) |
| `MixinBrushItem` | `BrushItem` | [MixinBrushItem.java](noobanidus/mods/lootr/common/mixin/brushing/MixinBrushItem.java) |
| `AccessorMixinDimensionDataStorage` | `AccessorDimensionDataStorage` | [AccessorMixinDimensionDataStorage.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinDimensionDataStorage.java) |
| `MixinStructureTemplate` | `StructureTemplate` | [MixinStructureTemplate.java](noobanidus/mods/lootr/common/mixin/ticker/MixinStructureTemplate.java) |
| `MixinRandomizableContainer` | `RandomizableContainer` | [MixinRandomizableContainer.java](noobanidus/mods/lootr/common/mixin/ticker/MixinRandomizableContainer.java) |
| `MixinLevelChunk` | `LevelChunk` | [MixinLevelChunk.java](noobanidus/mods/lootr/common/mixin/ticker/MixinLevelChunk.java) |
| `AccessorMixinMinecraftServer` | `AccessorMinecraftServer` | [AccessorMixinMinecraftServer.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinMinecraftServer.java) |
| `AccessorMixinChunkMap` | `AccessorChunkMap` | [AccessorMixinChunkMap.java](noobanidus/mods/lootr/common/mixin/accessor/AccessorMixinChunkMap.java) |
| `MixinEndCityPieces` | `EndCityPieces` | [MixinEndCityPieces$EndCityPiece.java](noobanidus/mods/lootr/common/mixin/elytra/MixinEndCityPieces$EndCityPiece.java) |
| `MixinSchematicPrinter` | `SchematicPrinter` | [MixinSchematicPrinter.java](noobanidus/mods/lootr/common/mixin/integration/create/MixinSchematicPrinter.java) |
| `MixinStructureTemplate` | `StructureTemplate` | [MixinStructureTemplate.java](noobanidus/mods/lootr/common/mixin/structure_saving/MixinStructureTemplate.java) |
| `MixinPoiTypes` | `PoiTypes` | [MixinPoiTypes.java](noobanidus/mods/lootr/common/mixin/poi/MixinPoiTypes.java) |
| `MixinPoiType` | `PoiType` | [MixinPoiType.java](noobanidus/mods/lootr/common/mixin/poi/MixinPoiType.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigManager` | EventBusSubscriber | [ConfigManager.java](noobanidus/mods/lootr/neoforge/config/ConfigManager.java) |
| `ConfigManagerBase` | — | [ConfigManagerBase.java](noobanidus/mods/lootr/common/config/ConfigManagerBase.java) |

## 按包浏览

- `noobanidus.mods.lootr.common.advancement`（3 类）
- `noobanidus.mods.lootr.common.api`（22 类）
- `noobanidus.mods.lootr.common.api.adapter`（4 类）
- `noobanidus.mods.lootr.common.api.advancement`（4 类）
- `noobanidus.mods.lootr.common.api.annotation`（3 类）
- `noobanidus.mods.lootr.common.api.client`（2 类）
- `noobanidus.mods.lootr.common.api.command`（1 类）
- `noobanidus.mods.lootr.common.api.config`（1 类）
- `noobanidus.mods.lootr.common.api.data`（15 类）
- `noobanidus.mods.lootr.common.api.data.blockentity`（3 类）
- `noobanidus.mods.lootr.common.api.data.entity`（3 类）
- `noobanidus.mods.lootr.common.api.data.inventory`（1 类）
- `noobanidus.mods.lootr.common.api.filter`（2 类）
- `noobanidus.mods.lootr.common.api.network`（1 类）
- `noobanidus.mods.lootr.common.api.processor`（3 类）
- `noobanidus.mods.lootr.common.api.registry`（3 类）
- `noobanidus.mods.lootr.common.api.replacement`（2 类）
- `noobanidus.mods.lootr.common.block`（8 类）
- `noobanidus.mods.lootr.common.block.entity`（8 类）
- `noobanidus.mods.lootr.common.chunk`（1 类）
- `noobanidus.mods.lootr.common.client`（1 类）
- `noobanidus.mods.lootr.common.client.block`（2 类）
- `noobanidus.mods.lootr.common.client.entity`（2 类）
- `noobanidus.mods.lootr.common.client.item`（4 类）
- `noobanidus.mods.lootr.common.client.particle`（1 类）
- `noobanidus.mods.lootr.common.command`（3 类）
- `noobanidus.mods.lootr.common.config`（1 类）
- `noobanidus.mods.lootr.common.data`（3 类）
- `noobanidus.mods.lootr.common.debug`（1 类）
- `noobanidus.mods.lootr.common.entity`（2 类）
- `noobanidus.mods.lootr.common.impl`（3 类）
- `noobanidus.mods.lootr.common.impl.adapter`（5 类）
- `noobanidus.mods.lootr.common.impl.command`（7 类）
- `noobanidus.mods.lootr.common.impl.replacement`（7 类）
- `noobanidus.mods.lootr.common.impl.type`（12 类）
- `noobanidus.mods.lootr.common.integration.create`（1 类）
- `noobanidus.mods.lootr.common.integration.digsite_workshop`（1 类）
- `noobanidus.mods.lootr.common.integration.jade`（1 类）
- `noobanidus.mods.lootr.common.integration.sherdsapi`（1 类）
- `noobanidus.mods.lootr.common.integration.sherdsapi.impl`（1 类）
- `noobanidus.mods.lootr.common.loot.conditions`（1 类）
- `noobanidus.mods.lootr.common.mixin.accessor`（10 类）
- `noobanidus.mods.lootr.common.mixin.brushing`（1 类）
- `noobanidus.mods.lootr.common.mixin.cat`（1 类）
- `noobanidus.mods.lootr.common.mixin.chest_blocking`（1 类）
- `noobanidus.mods.lootr.common.mixin.elytra`（1 类）
- `noobanidus.mods.lootr.common.mixin.falling`（1 类）
- `noobanidus.mods.lootr.common.mixin.filter`（1 类）
- `noobanidus.mods.lootr.common.mixin.hopper`（3 类）
- `noobanidus.mods.lootr.common.mixin.integration.create`（1 类）
- `noobanidus.mods.lootr.common.mixin.lock`（1 类）
- `noobanidus.mods.lootr.common.mixin.poi`（2 类）
- `noobanidus.mods.lootr.common.mixin.redstone`（1 类）
- `noobanidus.mods.lootr.common.mixin.spawn`（1 类）
- `noobanidus.mods.lootr.common.mixin.structure_saving`（1 类）
- `noobanidus.mods.lootr.common.mixin.ticker`（4 类）
- `noobanidus.mods.lootr.common.particle`（1 类）
- `noobanidus.mods.lootr.neoforge`（2 类）
- `noobanidus.mods.lootr.neoforge.block`（1 类）
- `noobanidus.mods.lootr.neoforge.client.block`（5 类）
- `noobanidus.mods.lootr.neoforge.config`（1 类）
- `noobanidus.mods.lootr.neoforge.event`（7 类）
- `noobanidus.mods.lootr.neoforge.gen`（12 类）
- `noobanidus.mods.lootr.neoforge.gen.compat`（2 类）
- `noobanidus.mods.lootr.neoforge.gen.optionals`（2 类）
- `noobanidus.mods.lootr.neoforge.impl`（3 类）
- `noobanidus.mods.lootr.neoforge.init`（10 类）
- `noobanidus.mods.lootr.neoforge.mixin.integration.digsite_workshop`（1 类）
- `noobanidus.mods.lootr.neoforge.mixin.self`（2 类）
- `noobanidus.mods.lootr.neoforge.network`（2 类）
- `noobanidus.mods.lootr.neoforge.network.client`（1 类）
- `noobanidus.mods.lootr.neoforge.network.toClient`（6 类）
- `noobanidus.mods.lootr.neoforge.setup`（1 类）
