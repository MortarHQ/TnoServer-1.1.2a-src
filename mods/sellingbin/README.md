# Selling Bin（`sellingbin`）

> jar：`[出售箱]SellingBin.jar` ｜ 版本：`1.1.1` ｜ 作者：KaptainWutax
> 规模：**162 个类 / 33 个包 / 17237 行** ｜ 事件处理类 0 ｜ Mixin 9 ｜ 注册/管理类 6

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `bigchadguys.sellingbin.data.adapter.number` | 18 | `NumberAdapter`、`BoundedIntAdapter`、`FloatAdapter` |
| `bigchadguys.sellingbin.data.adapter.nbt` | 18 | `NbtAdapter`、`CollectionNbtAdapter`、`ByteNbtAdapter` |
| `bigchadguys.sellingbin.init` | 11 | `ModScreenHandlers`、`ModBlocks`、`ModConfigs` |
| `bigchadguys.sellingbin.data.tile` | 10 | `PartialBlockState`、`PartialBlockProperties`、`PartialBlock` |
| `bigchadguys.sellingbin.mixin` | 9 | `MixinClientPlayNetworkHandler`、`MixinCommandManager`、`MixinBlockEntityRendererFactories` |
| `bigchadguys.sellingbin.data.adapter` | 9 | `IDataAdapter`、`Adapters`、`IComplexAdapter` |
| `bigchadguys.sellingbin.data.adapter.basic` | 8 | `SupplierAdapter`、`TypeSupplierAdapter`、`EnumAdapter` |
| `bigchadguys.sellingbin.data.adapter.util` | 7 | `RegistryAdapter`、`BlockPosAdapter`、`ItemStackAdapter` |
| `bigchadguys.sellingbin.config` | 6 | `ItemGroupsConfig`、`EntityGroupsConfig`、`Config` |
| `bigchadguys.sellingbin.data.entity` | 6 | `PartialEntity`、`PartialEntityGroup`、`PartialEntityTag` |
| `bigchadguys.sellingbin.data.item` | 6 | `PartialItem`、`PartialItemTag`、`PartialItemGroup` |
| `bigchadguys.sellingbin.world.random` | 6 | `JavaRandom`、`RandomSource`、`ChunkRandom` |
| `bigchadguys.sellingbin.util` | 6 | `MathUtils`、`ItemUtils`、`WeightedTree` |
| `bigchadguys.sellingbin.data.serializable` | 6 | `IDataSerializable`、`IByteSerializable`、`IJsonSerializable` |
| `bigchadguys.sellingbin.block` | 4 | `SellingBinBlock`、`BinMaterial`、`BinSettings` |
| `bigchadguys.sellingbin.data.adapter.array` | 4 | `ArrayAdapter`、`LongArrayAdapter`、`IntArrayAdapter` |
| `bigchadguys.sellingbin.net` | 3 | `ModPacket`、`SellingBinTradesS2CPacket`、`SellingBinEntriesS2CPacket` |
| `bigchadguys.sellingbin.world.data` | 3 | `SellingBinData`、`WorldData`、`WorldDataType` |
| `bigchadguys.sellingbin.data.bit` | 2 | `BitBuffer`、`ArrayBitBuffer` |
| `bigchadguys.sellingbin.block.entity` | 2 | `SellingBinBlockEntity`、`BaseBlockEntity` |
| `bigchadguys.sellingbin.data.nbt` | 2 | `PartialCompoundNbt`、`PartialListNbt` |
| `bigchadguys.sellingbin` | 2 | `SellingBinInventory`、`SellingBinMod` |
| `bigchadguys.sellingbin.command` | 2 | `ReloadCommand`、`Command` |
| `bigchadguys.sellingbin.world.random.lcg` | 2 | `Lcg`、`DiscreteLog` |
| `bigchadguys.sellingbin.render` | 2 | `SellingBinTooltipComponent`、`SellingBinTooltipData` |
| `bigchadguys.sellingbin.forge` | 1 | `SellingBinForgeMod` |
| `bigchadguys.sellingbin.screen.handler` | 1 | `SellingBinScreenHandler` |
| `bigchadguys.sellingbin.block.entity.renderer` | 1 | `SellingBinBlockEntityRenderer` |
| `bigchadguys.sellingbin.trade` | 1 | `Trade` |
| `bigchadguys.sellingbin.item` | 1 | `SellingBinItem` |
| `bigchadguys.sellingbin.world.roll` | 1 | `IntRoll` |
| `bigchadguys.sellingbin.screen` | 1 | `SellingBinScreen` |
| `architectury_inject_SellingBin_common_c7d5a0ca424a40f894120fffa8beb5c9_0ea433c9e6b6cc61035320a0e50f6ab92361b130c2f17865be79a42973b75de4SellingBinmc1211v111common111devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MixinClientPlayNetworkHandler` | class | — | Mixin | 2 | 46 | [MixinClientPlayNetworkHandler.java](bigchadguys/sellingbin/mixin/MixinClientPlayNetworkHandler.java) |
| `MixinCommandManager` | class | — | Mixin | 1 | 27 | [MixinCommandManager.java](bigchadguys/sellingbin/mixin/MixinCommandManager.java) |
| `MixinBlockEntityRendererFactories` | class | — | Mixin | 1 | 26 | [MixinBlockEntityRendererFactories.java](bigchadguys/sellingbin/mixin/MixinBlockEntityRendererFactories.java) |
| `ProxyModelPredicateProviderRegistry` | interface | — | Mixin | 0 | 17 | [ProxyModelPredicateProviderRegistry.java](bigchadguys/sellingbin/mixin/ProxyModelPredicateProviderRegistry.java) |
| `SellingBinForgeMod` | class | — | Mod | 0 | 12 | [SellingBinForgeMod.java](bigchadguys/sellingbin/forge/SellingBinForgeMod.java) |
| `MixinShapedRecipe` | class | — | Mixin | 0 | 9 | [MixinShapedRecipe.java](bigchadguys/sellingbin/mixin/MixinShapedRecipe.java) |
| `SellingBinScreenHandler` | class | AbstractContainerMenu | — | 6 | 116 | [SellingBinScreenHandler.java](bigchadguys/sellingbin/screen/handler/SellingBinScreenHandler.java) |
| `MixinHandledScreen` | class | — | Mixin | 2 | 30 | [MixinHandledScreen.java](bigchadguys/sellingbin/mixin/MixinHandledScreen.java) |
| `MixinItemGroup` | class | — | Mixin | 1 | 50 | [MixinItemGroup.java](bigchadguys/sellingbin/mixin/MixinItemGroup.java) |
| `RegistryAdapter` | class | ISimpleAdapter<V | — | 13 | 123 | [RegistryAdapter.java](bigchadguys/sellingbin/data/adapter/util/RegistryAdapter.java) |
| `BitBuffer` | class | — | — | 62 | 416 | [BitBuffer.java](bigchadguys/sellingbin/data/bit/BitBuffer.java) |
| `ItemGroupsConfig` | class | FileConfig | — | 6 | 50 | [ItemGroupsConfig.java](bigchadguys/sellingbin/config/ItemGroupsConfig.java) |
| `EntityGroupsConfig` | class | FileConfig | — | 6 | 53 | [EntityGroupsConfig.java](bigchadguys/sellingbin/config/EntityGroupsConfig.java) |
| `PartialBlockState` | class | TilePlacement<PartialBlockState> | — | 25 | 173 | [PartialBlockState.java](bigchadguys/sellingbin/data/tile/PartialBlockState.java) |
| `ModScreenHandlers` | class | ModRegistries | — | 1 | 15 | [ModScreenHandlers.java](bigchadguys/sellingbin/init/ModScreenHandlers.java) |
| `SellingBinBlockEntity` | class | BaseBlockEntity | — | 11 | 123 | [SellingBinBlockEntity.java](bigchadguys/sellingbin/block/entity/SellingBinBlockEntity.java) |
| `SellingBinBlockEntityRenderer` | class | BlockEntityRenderer<SellingBinBlockE | — | 1 | 16 | [SellingBinBlockEntityRenderer.java](bigchadguys/sellingbin/block/entity/renderer/SellingBinBlockEntityRenderer.java) |
| `MixinIngredient` | class | — | Mixin | 4 | 71 | [MixinIngredient.java](bigchadguys/sellingbin/mixin/MixinIngredient.java) |
| `MixinDrawContext` | class | — | Mixin | 3 | 48 | [MixinDrawContext.java](bigchadguys/sellingbin/mixin/MixinDrawContext.java) |
| `PartialEntity` | class | EntityPlacement<PartialEntity> | — | 20 | 148 | [PartialEntity.java](bigchadguys/sellingbin/data/entity/PartialEntity.java) |
| `PartialCompoundNbt` | class | TilePlacement<PartialCompoundNbt> | — | 29 | 255 | [PartialCompoundNbt.java](bigchadguys/sellingbin/data/nbt/PartialCompoundNbt.java) |
| `PartialBlockProperties` | class | TilePlacement<PartialBlockProperties | — | 19 | 241 | [PartialBlockProperties.java](bigchadguys/sellingbin/data/tile/PartialBlockProperties.java) |
| `PartialItem` | class | ItemPlacement<PartialItem> | — | 23 | 160 | [PartialItem.java](bigchadguys/sellingbin/data/item/PartialItem.java) |
| `JavaRandom` | class | LcgRandom | — | 25 | 215 | [JavaRandom.java](bigchadguys/sellingbin/world/random/JavaRandom.java) |
| `MathUtils` | class | — | — | 26 | 184 | [MathUtils.java](bigchadguys/sellingbin/util/MathUtils.java) |
| `Trade` | class | ISerializable<CompoundTag | — | 26 | 203 | [Trade.java](bigchadguys/sellingbin/trade/Trade.java) |
| `BaseBlockEntity` | class | BlockEntity | — | 7 | 62 | [BaseBlockEntity.java](bigchadguys/sellingbin/block/entity/BaseBlockEntity.java) |
| `Config` | class | — | — | 5 | 47 | [Config.java](bigchadguys/sellingbin/config/Config.java) |
| `SellingBinConfig` | class | FileConfig | — | 6 | 74 | [SellingBinConfig.java](bigchadguys/sellingbin/config/SellingBinConfig.java) |
| `NumberAdapter` | class | ISimpleAdapter<N | — | 24 | 207 | [NumberAdapter.java](bigchadguys/sellingbin/data/adapter/number/NumberAdapter.java) |
| `PartialEntityGroup` | class | EntityPlacement<PartialEntityGroup> | — | 15 | 127 | [PartialEntityGroup.java](bigchadguys/sellingbin/data/entity/PartialEntityGroup.java) |
| `PartialBlock` | class | TilePlacement<PartialBlock> | — | 18 | 145 | [PartialBlock.java](bigchadguys/sellingbin/data/tile/PartialBlock.java) |
| `PartialItemTag` | class | ItemPlacement<PartialItemTag> | — | 15 | 130 | [PartialItemTag.java](bigchadguys/sellingbin/data/item/PartialItemTag.java) |
| `RandomSource` | interface | — | — | 0 | 2397 | [RandomSource.java](bigchadguys/sellingbin/world/random/RandomSource.java) |
| `SellingBinInventory` | class | WorldlyContainer | — | 18 | 219 | [SellingBinInventory.java](bigchadguys/sellingbin/SellingBinInventory.java) |
| `SellingBinBlock` | class | BaseEntityBlock | — | 14 | 186 | [SellingBinBlock.java](bigchadguys/sellingbin/block/SellingBinBlock.java) |
| `TileGroupsConfig` | class | FileConfig | — | 4 | 42 | [TileGroupsConfig.java](bigchadguys/sellingbin/config/TileGroupsConfig.java) |
| `FileConfig` | class | Config | — | 4 | 36 | [FileConfig.java](bigchadguys/sellingbin/config/FileConfig.java) |
| `ModPacket` | class | CustomPacketPayload | — | 6 | 78 | [ModPacket.java](bigchadguys/sellingbin/net/ModPacket.java) |
| `NbtAdapter` | class | ISimpleAdapter<T | — | 21 | 108 | [NbtAdapter.java](bigchadguys/sellingbin/data/adapter/nbt/NbtAdapter.java) |
| `SupplierAdapter` | class | ISimpleAdapter<T | — | 21 | 108 | [SupplierAdapter.java](bigchadguys/sellingbin/data/adapter/basic/SupplierAdapter.java) |
| `BlockPosAdapter` | class | ISimpleAdapter<BlockPos | — | 12 | 90 | [BlockPosAdapter.java](bigchadguys/sellingbin/data/adapter/util/BlockPosAdapter.java) |
| `ItemStackAdapter` | class | ISimpleAdapter<ItemStack | — | 12 | 182 | [ItemStackAdapter.java](bigchadguys/sellingbin/data/adapter/util/ItemStackAdapter.java) |
| `ArrayAdapter` | class | — | — | 15 | 239 | [ArrayAdapter.java](bigchadguys/sellingbin/data/adapter/array/ArrayAdapter.java) |
| `PartialEntityTag` | class | EntityPlacement<PartialEntityTag> | — | 12 | 116 | [PartialEntityTag.java](bigchadguys/sellingbin/data/entity/PartialEntityTag.java) |
| `PartialBlockTag` | class | TilePlacement<PartialBlockTag> | — | 12 | 113 | [PartialBlockTag.java](bigchadguys/sellingbin/data/tile/PartialBlockTag.java) |
| `PartialBlockGroup` | class | TilePlacement<PartialBlockGroup> | — | 12 | 109 | [PartialBlockGroup.java](bigchadguys/sellingbin/data/tile/PartialBlockGroup.java) |
| `PartialTile` | class | TilePlacement<PartialTile> | — | 22 | 174 | [PartialTile.java](bigchadguys/sellingbin/data/tile/PartialTile.java) |
| `PartialItemGroup` | class | ItemPlacement<PartialItemGroup> | — | 14 | 114 | [PartialItemGroup.java](bigchadguys/sellingbin/data/item/PartialItemGroup.java) |
| `PartialStack` | class | ItemPlacement<PartialStack> | — | 23 | 173 | [PartialStack.java](bigchadguys/sellingbin/data/item/PartialStack.java) |
| `ChunkRandom` | class | JavaRandom | — | 20 | 136 | [ChunkRandom.java](bigchadguys/sellingbin/world/random/ChunkRandom.java) |
| `ModBlocks` | class | ModRegistries | — | 14 | 135 | [ModBlocks.java](bigchadguys/sellingbin/init/ModBlocks.java) |
| `ModConfigs` | class | ModRegistries | — | 1 | 31 | [ModConfigs.java](bigchadguys/sellingbin/init/ModConfigs.java) |
| `SellingBinTradesS2CPacket` | class | ModPacket | — | 4 | 57 | [SellingBinTradesS2CPacket.java](bigchadguys/sellingbin/net/SellingBinTradesS2CPacket.java) |
| `SellingBinEntriesS2CPacket` | class | ModPacket | — | 4 | 52 | [SellingBinEntriesS2CPacket.java](bigchadguys/sellingbin/net/SellingBinEntriesS2CPacket.java) |
| `TypeSupplierAdapter` | class | — | — | 15 | 158 | [TypeSupplierAdapter.java](bigchadguys/sellingbin/data/adapter/basic/TypeSupplierAdapter.java) |
| `EnumAdapter` | class | ISimpleAdapter<E | — | 14 | 244 | [EnumAdapter.java](bigchadguys/sellingbin/data/adapter/basic/EnumAdapter.java) |
| `LongArrayAdapter` | class | — | — | 13 | 261 | [LongArrayAdapter.java](bigchadguys/sellingbin/data/adapter/array/LongArrayAdapter.java) |
| `IntArrayAdapter` | class | — | — | 13 | 252 | [IntArrayAdapter.java](bigchadguys/sellingbin/data/adapter/array/IntArrayAdapter.java) |
| `ByteArrayAdapter` | class | — | — | 13 | 234 | [ByteArrayAdapter.java](bigchadguys/sellingbin/data/adapter/array/ByteArrayAdapter.java) |

## Mixin（9 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinClientPlayNetworkHandler` | `ClientPlayNetworkHandler` | [MixinClientPlayNetworkHandler.java](bigchadguys/sellingbin/mixin/MixinClientPlayNetworkHandler.java) |
| `MixinCommandManager` | `CommandManager` | [MixinCommandManager.java](bigchadguys/sellingbin/mixin/MixinCommandManager.java) |
| `MixinBlockEntityRendererFactories` | `BlockEntityRendererFactories` | [MixinBlockEntityRendererFactories.java](bigchadguys/sellingbin/mixin/MixinBlockEntityRendererFactories.java) |
| `ProxyModelPredicateProviderRegistry` | `ProxyModelPredicateProviderRegistry` | [ProxyModelPredicateProviderRegistry.java](bigchadguys/sellingbin/mixin/ProxyModelPredicateProviderRegistry.java) |
| `MixinShapedRecipe` | `ShapedRecipe` | [MixinShapedRecipe.java](bigchadguys/sellingbin/mixin/MixinShapedRecipe.java) |
| `MixinHandledScreen` | `HandledScreen` | [MixinHandledScreen.java](bigchadguys/sellingbin/mixin/MixinHandledScreen.java) |
| `MixinItemGroup` | `ItemGroup` | [MixinItemGroup.java](bigchadguys/sellingbin/mixin/MixinItemGroup.java) |
| `MixinIngredient` | `Ingredient` | [MixinIngredient.java](bigchadguys/sellingbin/mixin/MixinIngredient.java) |
| `MixinDrawContext` | `DrawContext` | [MixinDrawContext.java](bigchadguys/sellingbin/mixin/MixinDrawContext.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ItemGroupsConfig` | — | [ItemGroupsConfig.java](bigchadguys/sellingbin/config/ItemGroupsConfig.java) |
| `EntityGroupsConfig` | — | [EntityGroupsConfig.java](bigchadguys/sellingbin/config/EntityGroupsConfig.java) |
| `Config` | — | [Config.java](bigchadguys/sellingbin/config/Config.java) |
| `SellingBinConfig` | — | [SellingBinConfig.java](bigchadguys/sellingbin/config/SellingBinConfig.java) |
| `TileGroupsConfig` | — | [TileGroupsConfig.java](bigchadguys/sellingbin/config/TileGroupsConfig.java) |
| `FileConfig` | — | [FileConfig.java](bigchadguys/sellingbin/config/FileConfig.java) |
| `ModConfigs` | — | [ModConfigs.java](bigchadguys/sellingbin/init/ModConfigs.java) |

## 按包浏览

- `architectury_inject_SellingBin_common_c7d5a0ca424a40f894120fffa8beb5c9_0ea433c9e6b6cc61035320a0e50f6ab92361b130c2f17865be79a42973b75de4SellingBinmc1211v111common111devjar`（1 类）
- `bigchadguys.sellingbin`（2 类）
- `bigchadguys.sellingbin.block`（4 类）
- `bigchadguys.sellingbin.block.entity`（2 类）
- `bigchadguys.sellingbin.block.entity.renderer`（1 类）
- `bigchadguys.sellingbin.command`（2 类）
- `bigchadguys.sellingbin.config`（6 类）
- `bigchadguys.sellingbin.data.adapter`（9 类）
- `bigchadguys.sellingbin.data.adapter.array`（4 类）
- `bigchadguys.sellingbin.data.adapter.basic`（8 类）
- `bigchadguys.sellingbin.data.adapter.nbt`（18 类）
- `bigchadguys.sellingbin.data.adapter.number`（18 类）
- `bigchadguys.sellingbin.data.adapter.util`（7 类）
- `bigchadguys.sellingbin.data.bit`（2 类）
- `bigchadguys.sellingbin.data.entity`（6 类）
- `bigchadguys.sellingbin.data.item`（6 类）
- `bigchadguys.sellingbin.data.nbt`（2 类）
- `bigchadguys.sellingbin.data.serializable`（6 类）
- `bigchadguys.sellingbin.data.tile`（10 类）
- `bigchadguys.sellingbin.forge`（1 类）
- `bigchadguys.sellingbin.init`（11 类）
- `bigchadguys.sellingbin.item`（1 类）
- `bigchadguys.sellingbin.mixin`（9 类）
- `bigchadguys.sellingbin.net`（3 类）
- `bigchadguys.sellingbin.render`（2 类）
- `bigchadguys.sellingbin.screen`（1 类）
- `bigchadguys.sellingbin.screen.handler`（1 类）
- `bigchadguys.sellingbin.trade`（1 类）
- `bigchadguys.sellingbin.util`（6 类）
- `bigchadguys.sellingbin.world.data`（3 类）
- `bigchadguys.sellingbin.world.random`（6 类）
- `bigchadguys.sellingbin.world.random.lcg`（2 类）
- `bigchadguys.sellingbin.world.roll`（1 类）
