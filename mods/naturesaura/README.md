# NaturesAura（`naturesaura`）

> jar：`[自然灵气]NaturesAura.jar` ｜ 版本：`41.10` ｜ 作者：Ellpeck
> 规模：**249 个类 / 35 个包 / 22279 行** ｜ 事件处理类 24 ｜ Mixin 0 ｜ 注册/管理类 7

**依赖**：`minecraft`、`patchouli`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `de.ellpeck.naturesaura.blocks` | 53 | `BlockEndFlower`、`BlockSpawnLamp`、`BlockOakGenerator` |
| `de.ellpeck.naturesaura.blocks.tiles` | 43 | `BlockEntityImpl`、`ItemStackHandlerNA`、`BlockEntityEnderCrate` |
| `de.ellpeck.naturesaura.items` | 24 | `ItemBreakPrevention`、`ItemRangeVisualizer`、`ItemAuraBottle` |
| `de.ellpeck.naturesaura.reg` | 14 | `ModRegistry`、`INoItemBlock`、`ModItemTier` |
| `de.ellpeck.naturesaura.chunk.effect` | 14 | `BreathlessEffect`、`OreSpawnEffect`、`CacheRechargeEffect` |
| `de.ellpeck.naturesaura.blocks.tiles.render` | 8 | `RenderEnderCrate`、`RenderNatureAltar`、`RenderAuraTimer` |
| `de.ellpeck.naturesaura.data` | 7 | `ModData`、`BlockLootProvider`、`BlockTagProvider` |
| `de.ellpeck.naturesaura.recipes` | 7 | `TreeRitualRecipe`、`AnimalSpawnerRecipe`、`OfferingRecipe` |
| `de.ellpeck.naturesaura.items.tools` | 6 | `ItemArmor`、`ItemAxe`、`ItemPickaxe` |
| `de.ellpeck.naturesaura.packet` | 5 | `PacketHandler`、`PacketParticles`、`PacketClient` |
| `de.ellpeck.naturesaura.compat.patchouli` | 5 | `PatchouliCompat`、`ProcessorOffering`、`ProcessorAltar` |
| `de.ellpeck.naturesaura.entities` | 5 | `EntityEffectInhibitor`、`EntityMoverMinecart`、`EntityStructureFinder` |
| `de.ellpeck.naturesaura.compat.jei` | 5 | `JEINaturesAuraPlugin`、`OfferingCategory`、`AnimalSpawnerCategory` |
| `de.ellpeck.naturesaura` | 4 | `NaturesAura`、`Helper`、`InternalHooks` |
| `de.ellpeck.naturesaura.api.aura.container` | 4 | `ItemAuraContainer`、`BasicAuraContainer`、`NaturalAuraContainer` |
| `de.ellpeck.naturesaura.gen` | 4 | `LevelGenAncientTree`、`LevelGenAuraBloom`、`LevelGenNetherWartMushroom` |
| `de.ellpeck.naturesaura.potion` | 3 | `PotionBreathless`、`ModPotions`、`PotionImpl` |
| `de.ellpeck.naturesaura.compat` | 3 | `Compat`、`CuriosCompat`、`ICompat` |
| `de.ellpeck.naturesaura.proxy` | 3 | `ClientProxy`、`ServerProxy`、`IProxy` |
| `de.ellpeck.naturesaura.api.misc` | 3 | `ILevelData`、`WeatherType`、`WeightedOre` |
| `de.ellpeck.naturesaura.gui` | 3 | `GuiEnderCrate`、`ContainerEnderCrate`、`ModContainers` |
| `de.ellpeck.naturesaura.api.aura.chunk` | 3 | `IAuraChunk`、`ISpotDrainable`、`IDrainSpotEffect` |
| `de.ellpeck.naturesaura.entities.render` | 3 | `RenderStub`、`RenderEffectInhibitor`、`RenderMoverMinecart` |
| `de.ellpeck.naturesaura.events` | 2 | `ClientEvents`、`CommonEvents` |
| `de.ellpeck.naturesaura.renderers` | 2 | `SupporterFancyHandler`、`PlayerLayerTrinkets` |
| `de.ellpeck.naturesaura.particles` | 2 | `ParticleHandler`、`ParticleMagic` |
| `de.ellpeck.naturesaura.blocks.multi` | 2 | `Multiblock`、`Multiblocks` |
| `de.ellpeck.naturesaura.misc` | 2 | `ColoredBlockHelper`、`LevelData` |
| `de.ellpeck.naturesaura.api.multiblock` | 2 | `IMultiblock`、`Matcher` |
| `de.ellpeck.naturesaura.api.render` | 2 | `ITrinketItem`、`IVisualizable` |
| `de.ellpeck.naturesaura.api.aura.type` | 2 | `BasicAuraType`、`IAuraType` |
| `de.ellpeck.naturesaura.chunk` | 1 | `AuraChunk` |
| `de.ellpeck.naturesaura.commands` | 1 | `CommandAura` |
| `de.ellpeck.naturesaura.api` | 1 | `NaturesAuraAPI` |
| `de.ellpeck.naturesaura.api.aura.item` | 1 | `IAuraRecharge` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ModRegistry` | class | — | EventBusSubscriber | 6 | 553 | [ModRegistry.java](de/ellpeck/naturesaura/reg/ModRegistry.java) |
| `PacketHandler` | class | — | EventBusSubscriber | 4 | 40 | [PacketHandler.java](de/ellpeck/naturesaura/packet/PacketHandler.java) |
| `ClientEvents` | class | — | OnlyIn | 7 | 578 | [ClientEvents.java](de/ellpeck/naturesaura/events/ClientEvents.java) |
| `ModData` | class | — | EventBusSubscriber | 3 | 71 | [ModData.java](de/ellpeck/naturesaura/data/ModData.java) |
| `CommonEvents` | class | — | — | 8 | 149 | [CommonEvents.java](de/ellpeck/naturesaura/events/CommonEvents.java) |
| `NaturesAura` | class | — | Mod | 4 | 67 | [NaturesAura.java](de/ellpeck/naturesaura/NaturesAura.java) |
| `ItemBreakPrevention` | class | ItemImpl | — | 4 | 83 | [ItemBreakPrevention.java](de/ellpeck/naturesaura/items/ItemBreakPrevention.java) |
| `SupporterFancyHandler` | class | Thread | OnlyIn | 3 | 108 | [SupporterFancyHandler.java](de/ellpeck/naturesaura/renderers/SupporterFancyHandler.java) |
| `Helper` | class | — | — | 26 | 446 | [Helper.java](de/ellpeck/naturesaura/Helper.java) |
| `PacketParticles` | record | CustomPacketPayload | — | 2 | 1101 | [PacketParticles.java](de/ellpeck/naturesaura/packet/PacketParticles.java) |
| `BlockEndFlower` | class | BushBlock | — | 12 | 129 | [BlockEndFlower.java](de/ellpeck/naturesaura/blocks/BlockEndFlower.java) |
| `BlockEntityImpl` | class | BlockEntity | — | 20 | 190 | [BlockEntityImpl.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityImpl.java) |
| `ItemStackHandlerNA` | class | ItemStackHandler | — | 6 | 52 | [ItemStackHandlerNA.java](de/ellpeck/naturesaura/blocks/tiles/ItemStackHandlerNA.java) |
| `ItemRangeVisualizer` | class | ItemImpl | — | 5 | 105 | [ItemRangeVisualizer.java](de/ellpeck/naturesaura/items/ItemRangeVisualizer.java) |
| `ItemAuraBottle` | class | ItemImpl | — | 9 | 145 | [ItemAuraBottle.java](de/ellpeck/naturesaura/items/ItemAuraBottle.java) |
| `PatchouliCompat` | class | ICompat | — | 6 | 156 | [PatchouliCompat.java](de/ellpeck/naturesaura/compat/patchouli/PatchouliCompat.java) |
| `EntityEffectInhibitor` | class | Entity | — | 23 | 208 | [EntityEffectInhibitor.java](de/ellpeck/naturesaura/entities/EntityEffectInhibitor.java) |
| `BlockSpawnLamp` | class | BlockContainerImpl | — | 6 | 110 | [BlockSpawnLamp.java](de/ellpeck/naturesaura/blocks/BlockSpawnLamp.java) |
| `BlockOakGenerator` | class | BlockContainerImpl | — | 5 | 101 | [BlockOakGenerator.java](de/ellpeck/naturesaura/blocks/BlockOakGenerator.java) |
| `BlockAnimalGenerator` | class | BlockContainerImpl | — | 7 | 143 | [BlockAnimalGenerator.java](de/ellpeck/naturesaura/blocks/BlockAnimalGenerator.java) |
| `BlockProjectileGenerator` | class | BlockContainerImpl | — | 5 | 146 | [BlockProjectileGenerator.java](de/ellpeck/naturesaura/blocks/BlockProjectileGenerator.java) |
| `BlockEnderCrate` | class | BlockContainerImpl | — | 9 | 141 | [BlockEnderCrate.java](de/ellpeck/naturesaura/blocks/BlockEnderCrate.java) |
| `BlockWoodStand` | class | BlockContainerImpl | — | 6 | 115 | [BlockWoodStand.java](de/ellpeck/naturesaura/blocks/BlockWoodStand.java) |
| `BlockEntityEnderCrate` | class | BlockEntityImpl | — | 18 | 147 | [BlockEntityEnderCrate.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityEnderCrate.java) |
| `BlockEntitySpring` | class | BlockEntityImpl | — | 17 | 183 | [BlockEntitySpring.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntitySpring.java) |
| `BlockEntityItemDistributor` | class | BlockEntityImpl | — | 5 | 113 | [BlockEntityItemDistributor.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityItemDistributor.java) |
| `ItemPetReviver` | class | ItemImpl | — | 4 | 190 | [ItemPetReviver.java](de/ellpeck/naturesaura/items/ItemPetReviver.java) |
| `ItemBirthSpirit` | class | ItemGlowing | — | 1 | 38 | [ItemBirthSpirit.java](de/ellpeck/naturesaura/items/ItemBirthSpirit.java) |
| `ItemDeathRing` | class | ItemImpl | — | 1 | 43 | [ItemDeathRing.java](de/ellpeck/naturesaura/items/ItemDeathRing.java) |
| `ItemArmor` | class | ArmorItem | — | 4 | 132 | [ItemArmor.java](de/ellpeck/naturesaura/items/tools/ItemArmor.java) |
| `BlockSlimeSplitGenerator` | class | BlockContainerImpl | — | 4 | 77 | [BlockSlimeSplitGenerator.java](de/ellpeck/naturesaura/blocks/BlockSlimeSplitGenerator.java) |
| `BlockPickupStopper` | class | BlockContainerImpl | — | 4 | 95 | [BlockPickupStopper.java](de/ellpeck/naturesaura/blocks/BlockPickupStopper.java) |
| `BlockGoldPowder` | class | BlockImpl | — | 17 | 259 | [BlockGoldPowder.java](de/ellpeck/naturesaura/blocks/BlockGoldPowder.java) |
| `BlockEntityWoodStand` | class | BlockEntityImpl | — | 6 | 242 | [BlockEntityWoodStand.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityWoodStand.java) |
| `BlockEntityBlastFurnaceBooster` | class | BlockEntityImpl | — | 12 | 152 | [BlockEntityBlastFurnaceBooster.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityBlastFurnaceBooster.java) |
| `BlockEntityNatureAltar` | class | BlockEntityImpl | — | 8 | 335 | [BlockEntityNatureAltar.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityNatureAltar.java) |
| `BlockEntityFieldCreator` | class | BlockEntityImpl | — | 9 | 221 | [BlockEntityFieldCreator.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityFieldCreator.java) |
| `ParticleHandler` | class | — | OnlyIn | 6 | 88 | [ParticleHandler.java](de/ellpeck/naturesaura/particles/ParticleHandler.java) |
| `InternalHooks` | class | NaturesAuraAPI.IInternalHooks | — | 20 | 232 | [InternalHooks.java](de/ellpeck/naturesaura/InternalHooks.java) |
| `ModConfig` | class | — | — | 1 | 229 | [ModConfig.java](de/ellpeck/naturesaura/ModConfig.java) |
| `ItemLootFinder` | class | ItemImpl | — | 2 | 89 | [ItemLootFinder.java](de/ellpeck/naturesaura/items/ItemLootFinder.java) |
| `ItemMultiblockMaker` | class | ItemImpl | — | 7 | 100 | [ItemMultiblockMaker.java](de/ellpeck/naturesaura/items/ItemMultiblockMaker.java) |
| `PacketClient` | record | CustomPacketPayload | — | 2 | 53 | [PacketClient.java](de/ellpeck/naturesaura/packet/PacketClient.java) |
| `BlockLootProvider` | class | BlockLootSubProvider | — | 2 | 75 | [BlockLootProvider.java](de/ellpeck/naturesaura/data/BlockLootProvider.java) |
| `TreeRitualRecipe` | class | ModRecipe | — | 5 | 62 | [TreeRitualRecipe.java](de/ellpeck/naturesaura/recipes/TreeRitualRecipe.java) |
| `AnimalSpawnerRecipe` | class | ModRecipe | — | 6 | 74 | [AnimalSpawnerRecipe.java](de/ellpeck/naturesaura/recipes/AnimalSpawnerRecipe.java) |
| `OfferingRecipe` | class | ModRecipe | — | 5 | 57 | [OfferingRecipe.java](de/ellpeck/naturesaura/recipes/OfferingRecipe.java) |
| `AltarRecipe` | class | ModRecipe | — | 5 | 64 | [AltarRecipe.java](de/ellpeck/naturesaura/recipes/AltarRecipe.java) |
| `BlockContainerImpl` | class | BaseEntityBlock | — | 17 | 169 | [BlockContainerImpl.java](de/ellpeck/naturesaura/blocks/BlockContainerImpl.java) |
| `BlockEntityEndFlower` | class | BlockEntityImpl | — | 6 | 133 | [BlockEntityEndFlower.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityEndFlower.java) |
| `BlockEntityAuraTimer` | class | BlockEntityImpl | — | 8 | 135 | [BlockEntityAuraTimer.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityAuraTimer.java) |
| `BlockEntityWeatherChanger` | class | BlockEntityImpl | — | 5 | 154 | [BlockEntityWeatherChanger.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityWeatherChanger.java) |
| `BlockEntityOfferingTable` | class | BlockEntityImpl | — | 5 | 161 | [BlockEntityOfferingTable.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityOfferingTable.java) |
| `BlockEntityChorusGenerator` | class | BlockEntityImpl | — | 5 | 150 | [BlockEntityChorusGenerator.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityChorusGenerator.java) |
| `BlockEntitySpawnLamp` | class | BlockEntityImpl | — | 5 | 46 | [BlockEntitySpawnLamp.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntitySpawnLamp.java) |
| `BlockEntitySnowCreator` | class | BlockEntityImpl | — | 6 | 151 | [BlockEntitySnowCreator.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntitySnowCreator.java) |
| `BlockEntityRFConverter` | class | BlockEntityImpl | — | 5 | 110 | [BlockEntityRFConverter.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityRFConverter.java) |
| `BlockEntityGratedChute` | class | BlockEntityImpl | — | 6 | 167 | [BlockEntityGratedChute.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityGratedChute.java) |
| `BlockEntitySlimeSplitGenerator` | class | BlockEntityImpl | — | 8 | 104 | [BlockEntitySlimeSplitGenerator.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntitySlimeSplitGenerator.java) |
| `BlockEntityAnimalSpawner` | class | BlockEntityImpl | — | 4 | 203 | [BlockEntityAnimalSpawner.java](de/ellpeck/naturesaura/blocks/tiles/BlockEntityAnimalSpawner.java) |

## 事件处理（`@SubscribeEvent`，24 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CommonEvents` | 7 | `de.ellpeck.naturesaura.events` | [CommonEvents.java](de/ellpeck/naturesaura/events/CommonEvents.java) |
| `ModRegistry` | 4 | `de.ellpeck.naturesaura.reg` | [ModRegistry.java](de/ellpeck/naturesaura/reg/ModRegistry.java) |
| `ClientEvents` | 4 | `de.ellpeck.naturesaura.events` | [ClientEvents.java](de/ellpeck/naturesaura/events/ClientEvents.java) |
| `BlockAnimalGenerator` | 4 | `de.ellpeck.naturesaura.blocks` | [BlockAnimalGenerator.java](de/ellpeck/naturesaura/blocks/BlockAnimalGenerator.java) |
| `ItemBreakPrevention` | 3 | `de.ellpeck.naturesaura.items` | [ItemBreakPrevention.java](de/ellpeck/naturesaura/items/ItemBreakPrevention.java) |
| `ItemPetReviver` | 3 | `de.ellpeck.naturesaura.items` | [ItemPetReviver.java](de/ellpeck/naturesaura/items/ItemPetReviver.java) |
| `ItemArmor` | 2 | `de.ellpeck.naturesaura.items.tools` | [ItemArmor.java](de/ellpeck/naturesaura/items/tools/ItemArmor.java) |
| `PacketHandler` | 1 | `de.ellpeck.naturesaura.packet` | [PacketHandler.java](de/ellpeck/naturesaura/packet/PacketHandler.java) |
| `ModData` | 1 | `de.ellpeck.naturesaura.data` | [ModData.java](de/ellpeck/naturesaura/data/ModData.java) |
| `SupporterFancyHandler` | 1 | `de.ellpeck.naturesaura.renderers` | [SupporterFancyHandler.java](de/ellpeck/naturesaura/renderers/SupporterFancyHandler.java) |
| `BlockEndFlower` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockEndFlower.java](de/ellpeck/naturesaura/blocks/BlockEndFlower.java) |
| `ItemRangeVisualizer` | 1 | `de.ellpeck.naturesaura.items` | [ItemRangeVisualizer.java](de/ellpeck/naturesaura/items/ItemRangeVisualizer.java) |
| `ItemAuraBottle` | 1 | `de.ellpeck.naturesaura.items` | [ItemAuraBottle.java](de/ellpeck/naturesaura/items/ItemAuraBottle.java) |
| `PatchouliCompat` | 1 | `de.ellpeck.naturesaura.compat.patchouli` | [PatchouliCompat.java](de/ellpeck/naturesaura/compat/patchouli/PatchouliCompat.java) |
| `BlockSpawnLamp` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockSpawnLamp.java](de/ellpeck/naturesaura/blocks/BlockSpawnLamp.java) |
| `BlockOakGenerator` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockOakGenerator.java](de/ellpeck/naturesaura/blocks/BlockOakGenerator.java) |
| `BlockProjectileGenerator` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockProjectileGenerator.java](de/ellpeck/naturesaura/blocks/BlockProjectileGenerator.java) |
| `BlockEnderCrate` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockEnderCrate.java](de/ellpeck/naturesaura/blocks/BlockEnderCrate.java) |
| `BlockWoodStand` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockWoodStand.java](de/ellpeck/naturesaura/blocks/BlockWoodStand.java) |
| `ItemBirthSpirit` | 1 | `de.ellpeck.naturesaura.items` | [ItemBirthSpirit.java](de/ellpeck/naturesaura/items/ItemBirthSpirit.java) |
| `ItemDeathRing` | 1 | `de.ellpeck.naturesaura.items` | [ItemDeathRing.java](de/ellpeck/naturesaura/items/ItemDeathRing.java) |
| `BlockSlimeSplitGenerator` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockSlimeSplitGenerator.java](de/ellpeck/naturesaura/blocks/BlockSlimeSplitGenerator.java) |
| `BlockPickupStopper` | 1 | `de.ellpeck.naturesaura.blocks` | [BlockPickupStopper.java](de/ellpeck/naturesaura/blocks/BlockPickupStopper.java) |
| `PotionBreathless` | 1 | `de.ellpeck.naturesaura.potion` | [PotionBreathless.java](de/ellpeck/naturesaura/potion/PotionBreathless.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ModConfig` | — | [ModConfig.java](de/ellpeck/naturesaura/ModConfig.java) |

## 按包浏览

- `de.ellpeck.naturesaura`（4 类）
- `de.ellpeck.naturesaura.api`（1 类）
- `de.ellpeck.naturesaura.api.aura.chunk`（3 类）
- `de.ellpeck.naturesaura.api.aura.container`（4 类）
- `de.ellpeck.naturesaura.api.aura.item`（1 类）
- `de.ellpeck.naturesaura.api.aura.type`（2 类）
- `de.ellpeck.naturesaura.api.misc`（3 类）
- `de.ellpeck.naturesaura.api.multiblock`（2 类）
- `de.ellpeck.naturesaura.api.render`（2 类）
- `de.ellpeck.naturesaura.blocks`（53 类）
- `de.ellpeck.naturesaura.blocks.multi`（2 类）
- `de.ellpeck.naturesaura.blocks.tiles`（43 类）
- `de.ellpeck.naturesaura.blocks.tiles.render`（8 类）
- `de.ellpeck.naturesaura.chunk`（1 类）
- `de.ellpeck.naturesaura.chunk.effect`（14 类）
- `de.ellpeck.naturesaura.commands`（1 类）
- `de.ellpeck.naturesaura.compat`（3 类）
- `de.ellpeck.naturesaura.compat.jei`（5 类）
- `de.ellpeck.naturesaura.compat.patchouli`（5 类）
- `de.ellpeck.naturesaura.data`（7 类）
- `de.ellpeck.naturesaura.entities`（5 类）
- `de.ellpeck.naturesaura.entities.render`（3 类）
- `de.ellpeck.naturesaura.events`（2 类）
- `de.ellpeck.naturesaura.gen`（4 类）
- `de.ellpeck.naturesaura.gui`（3 类）
- `de.ellpeck.naturesaura.items`（24 类）
- `de.ellpeck.naturesaura.items.tools`（6 类）
- `de.ellpeck.naturesaura.misc`（2 类）
- `de.ellpeck.naturesaura.packet`（5 类）
- `de.ellpeck.naturesaura.particles`（2 类）
- `de.ellpeck.naturesaura.potion`（3 类）
- `de.ellpeck.naturesaura.proxy`（3 类）
- `de.ellpeck.naturesaura.recipes`（7 类）
- `de.ellpeck.naturesaura.reg`（14 类）
- `de.ellpeck.naturesaura.renderers`（2 类）
