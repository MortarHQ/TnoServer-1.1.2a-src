# RAR-Compat（`rarcompat`）

> jar：`[遗物×奇异饰品]rarcompat.jar` ｜ 版本：`0.9.7` ｜ 作者：Octo-Studios
> 规模：**71 个类 / 17 个包 / 8522 行** ｜ 事件处理类 39 ｜ Mixin 11 ｜ 注册/管理类 9

**依赖**：`minecraft`、`neoforge`、`curios`、`octolib`、`artifacts`、`relics`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `it.hurts.octostudios.rarcompat.items.necklace` | 9 | `ScarfOfInvisibilityItem`、`PanicNecklaceItem`、`CharmOfSinkingItem` |
| `it.hurts.octostudios.rarcompat.items.hat` | 8 | `CowboyHatItem`、`NightVisionGogglesItem`、`VillagerHatItem` |
| `it.hurts.octostudios.rarcompat.items.charm` | 8 | `HeliumFlamingoItem`、`UniversalAttractorItem`、`ObsidianSkullItem` |
| `it.hurts.octostudios.rarcompat.items.hands` | 8 | `PocketPistonItem`、`PowerGloveItem`、`PickaxeHeaterItem` |
| `it.hurts.octostudios.rarcompat.items.feet` | 7 | `KittySlippersItem`、`BunnyHoppersItem`、`SteadfastSpikesItem` |
| `it.hurts.octostudios.rarcompat.init` | 5 | `RemoteRegistry`、`ItemRegistry`、`EntityRegistry` |
| `it.hurts.octostudios.rarcompat.mixin` | 5 | `RollLootTableModifierMixin`、`LivingEntityMixin`、`AbstractHorseMixin` |
| `it.hurts.octostudios.rarcompat.network.packets` | 5 | `PowerJumpPacket`、`RepulsionUmbrellaPacket`、`FlamingoSwimPacket` |
| `it.hurts.octostudios.rarcompat.items` | 3 | `UmbrellaItem`、`MimiDustItem`、`WearableRelicItem` |
| `it.hurts.octostudios.rarcompat.mixin.render` | 3 | `GameRendererMixin`、`BeltModelMixin`、`HumanoidModelMixin` |
| `it.hurts.octostudios.rarcompat.handlers` | 2 | `TooltipHandler`、`MimicHandler` |
| `it.hurts.octostudios.rarcompat.mixin.config` | 2 | `ConfigManagerMixin`、`ModConfigMixin` |
| `it.hurts.octostudios.rarcompat.items.bracelet` | 2 | `WitheredBraceletItem`、`OnionRingItem` |
| `it.hurts.octostudios.rarcompat.network` | 1 | `NetworkHandler` |
| `it.hurts.octostudios.rarcompat` | 1 | `RARCompat` |
| `it.hurts.octostudios.rarcompat.mixin.init` | 1 | `ModItemsMixin` |
| `it.hurts.octostudios.rarcompat.entities` | 1 | `SparkEntity` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NetworkHandler` | class | — | EventBusSubscriber | 1 | 26 | [NetworkHandler.java](it/hurts/octostudios/rarcompat/network/NetworkHandler.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 2 | 26 | [RemoteRegistry.java](it/hurts/octostudios/rarcompat/init/RemoteRegistry.java) |
| `TooltipHandler` | class | — | EventBusSubscriber | 1 | 36 | [TooltipHandler.java](it/hurts/octostudios/rarcompat/handlers/TooltipHandler.java) |
| `MimicHandler` | class | — | EventBusSubscriber | 2 | 68 | [MimicHandler.java](it/hurts/octostudios/rarcompat/handlers/MimicHandler.java) |
| `ConfigManagerMixin` | class | — | Mixin | 2 | 21 | [ConfigManagerMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ConfigManagerMixin.java) |
| `RARCompat` | class | — | Mod | 0 | 21 | [RARCompat.java](it/hurts/octostudios/rarcompat/RARCompat.java) |
| `UmbrellaItem` | class | WearableRelicItem | — | 20 | 383 | [UmbrellaItem.java](it/hurts/octostudios/rarcompat/items/UmbrellaItem.java) |
| `ModConfigMixin` | class | — | Mixin | 1 | 16 | [ModConfigMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ModConfigMixin.java) |
| `CowboyHatItem` | class | WearableRelicItem | — | 13 | 304 | [CowboyHatItem.java](it/hurts/octostudios/rarcompat/items/hat/CowboyHatItem.java) |
| `HeliumFlamingoItem` | class | WearableRelicItem | — | 10 | 215 | [HeliumFlamingoItem.java](it/hurts/octostudios/rarcompat/items/charm/HeliumFlamingoItem.java) |
| `UniversalAttractorItem` | class | WearableRelicItem | — | 11 | 230 | [UniversalAttractorItem.java](it/hurts/octostudios/rarcompat/items/charm/UniversalAttractorItem.java) |
| `RollLootTableModifierMixin` | class | — | Mixin | 1 | 43 | [RollLootTableModifierMixin.java](it/hurts/octostudios/rarcompat/mixin/RollLootTableModifierMixin.java) |
| `KittySlippersItem` | class | WearableRelicItem | — | 7 | 293 | [KittySlippersItem.java](it/hurts/octostudios/rarcompat/items/feet/KittySlippersItem.java) |
| `PocketPistonItem` | class | WearableRelicItem | — | 6 | 221 | [PocketPistonItem.java](it/hurts/octostudios/rarcompat/items/hands/PocketPistonItem.java) |
| `ScarfOfInvisibilityItem` | class | WearableRelicItem | — | 10 | 157 | [ScarfOfInvisibilityItem.java](it/hurts/octostudios/rarcompat/items/necklace/ScarfOfInvisibilityItem.java) |
| `ObsidianSkullItem` | class | WearableRelicItem | — | 13 | 198 | [ObsidianSkullItem.java](it/hurts/octostudios/rarcompat/items/charm/ObsidianSkullItem.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 95 | [LivingEntityMixin.java](it/hurts/octostudios/rarcompat/mixin/LivingEntityMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 1 | 30 | [GameRendererMixin.java](it/hurts/octostudios/rarcompat/mixin/render/GameRendererMixin.java) |
| `ModItemsMixin` | class | — | Mixin | 4 | 132 | [ModItemsMixin.java](it/hurts/octostudios/rarcompat/mixin/init/ModItemsMixin.java) |
| `ItemRegistry` | class | — | — | 1 | 18 | [ItemRegistry.java](it/hurts/octostudios/rarcompat/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 22 | [EntityRegistry.java](it/hurts/octostudios/rarcompat/init/EntityRegistry.java) |
| `BunnyHoppersItem` | class | WearableRelicItem | — | 8 | 188 | [BunnyHoppersItem.java](it/hurts/octostudios/rarcompat/items/feet/BunnyHoppersItem.java) |
| `NightVisionGogglesItem` | class | WearableRelicItem | — | 5 | 153 | [NightVisionGogglesItem.java](it/hurts/octostudios/rarcompat/items/hat/NightVisionGogglesItem.java) |
| `PowerGloveItem` | class | WearableRelicItem | — | 6 | 152 | [PowerGloveItem.java](it/hurts/octostudios/rarcompat/items/hands/PowerGloveItem.java) |
| `PickaxeHeaterItem` | class | WearableRelicItem | — | 7 | 189 | [PickaxeHeaterItem.java](it/hurts/octostudios/rarcompat/items/hands/PickaxeHeaterItem.java) |
| `DiggingClawsItem` | class | WearableRelicItem | — | 6 | 163 | [DiggingClawsItem.java](it/hurts/octostudios/rarcompat/items/hands/DiggingClawsItem.java) |
| `FeralClawsItem` | class | WearableRelicItem | — | 9 | 162 | [FeralClawsItem.java](it/hurts/octostudios/rarcompat/items/hands/FeralClawsItem.java) |
| `PanicNecklaceItem` | class | WearableRelicItem | — | 7 | 141 | [PanicNecklaceItem.java](it/hurts/octostudios/rarcompat/items/necklace/PanicNecklaceItem.java) |
| `CharmOfSinkingItem` | class | WearableRelicItem | — | 6 | 182 | [CharmOfSinkingItem.java](it/hurts/octostudios/rarcompat/items/necklace/CharmOfSinkingItem.java) |
| `CloudInBottleItem` | class | WearableRelicItem | — | 6 | 144 | [CloudInBottleItem.java](it/hurts/octostudios/rarcompat/items/charm/CloudInBottleItem.java) |
| `DataComponentRegistry` | class | — | — | 1 | 20 | [DataComponentRegistry.java](it/hurts/octostudios/rarcompat/init/DataComponentRegistry.java) |
| `SteadfastSpikesItem` | class | WearableRelicItem | — | 4 | 128 | [SteadfastSpikesItem.java](it/hurts/octostudios/rarcompat/items/feet/SteadfastSpikesItem.java) |
| `VillagerHatItem` | class | WearableRelicItem | — | 3 | 147 | [VillagerHatItem.java](it/hurts/octostudios/rarcompat/items/hat/VillagerHatItem.java) |
| `WhoopeeCushionItem` | class | WearableRelicItem | — | 4 | 176 | [WhoopeeCushionItem.java](it/hurts/octostudios/rarcompat/items/hat/WhoopeeCushionItem.java) |
| `AnglersHatItem` | class | WearableRelicItem | — | 2 | 144 | [AnglersHatItem.java](it/hurts/octostudios/rarcompat/items/hat/AnglersHatItem.java) |
| `DrinkingHatItem` | class | WearableRelicItem | — | 4 | 178 | [DrinkingHatItem.java](it/hurts/octostudios/rarcompat/items/hat/DrinkingHatItem.java) |
| `SnorkelItem` | class | WearableRelicItem | — | 3 | 138 | [SnorkelItem.java](it/hurts/octostudios/rarcompat/items/hat/SnorkelItem.java) |
| `WitheredBraceletItem` | class | WearableRelicItem | — | 3 | 158 | [WitheredBraceletItem.java](it/hurts/octostudios/rarcompat/items/bracelet/WitheredBraceletItem.java) |
| `OnionRingItem` | class | WearableRelicItem | — | 3 | 149 | [OnionRingItem.java](it/hurts/octostudios/rarcompat/items/bracelet/OnionRingItem.java) |
| `GoldenHookItem` | class | WearableRelicItem | — | 2 | 109 | [GoldenHookItem.java](it/hurts/octostudios/rarcompat/items/hands/GoldenHookItem.java) |
| `VampiricGloveItem` | class | WearableRelicItem | — | 2 | 104 | [VampiricGloveItem.java](it/hurts/octostudios/rarcompat/items/hands/VampiricGloveItem.java) |
| `FireGauntletItem` | class | WearableRelicItem | — | 2 | 150 | [FireGauntletItem.java](it/hurts/octostudios/rarcompat/items/hands/FireGauntletItem.java) |
| `ThornPendantItem` | class | WearableRelicItem | — | 2 | 141 | [ThornPendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/ThornPendantItem.java) |
| `FlamePendantItem` | class | WearableRelicItem | — | 2 | 129 | [FlamePendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/FlamePendantItem.java) |
| `ShockPendantItem` | class | WearableRelicItem | — | 3 | 181 | [ShockPendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/ShockPendantItem.java) |
| `CrossNecklaceItem` | class | WearableRelicItem | — | 3 | 113 | [CrossNecklaceItem.java](it/hurts/octostudios/rarcompat/items/necklace/CrossNecklaceItem.java) |
| `AntidoteVesselItem` | class | WearableRelicItem | — | 3 | 138 | [AntidoteVesselItem.java](it/hurts/octostudios/rarcompat/items/charm/AntidoteVesselItem.java) |
| `ChorusTotemItem` | class | WearableRelicItem | — | 4 | 175 | [ChorusTotemItem.java](it/hurts/octostudios/rarcompat/items/charm/ChorusTotemItem.java) |
| `CrystalHeartItem` | class | WearableRelicItem | — | 4 | 107 | [CrystalHeartItem.java](it/hurts/octostudios/rarcompat/items/charm/CrystalHeartItem.java) |
| `AbstractHorseMixin` | class | — | Mixin | 1 | 30 | [AbstractHorseMixin.java](it/hurts/octostudios/rarcompat/mixin/AbstractHorseMixin.java) |
| `MobMixin` | class | — | Mixin | 1 | 29 | [MobMixin.java](it/hurts/octostudios/rarcompat/mixin/MobMixin.java) |
| `AbstractVillagerMixin` | class | — | Mixin | 2 | 48 | [AbstractVillagerMixin.java](it/hurts/octostudios/rarcompat/mixin/AbstractVillagerMixin.java) |
| `BeltModelMixin` | class | — | Mixin | 1 | 24 | [BeltModelMixin.java](it/hurts/octostudios/rarcompat/mixin/render/BeltModelMixin.java) |
| `HumanoidModelMixin` | class | — | Mixin | 1 | 42 | [HumanoidModelMixin.java](it/hurts/octostudios/rarcompat/mixin/render/HumanoidModelMixin.java) |
| `SparkEntity` | class | ThrowableProjectile | — | 22 | 182 | [SparkEntity.java](it/hurts/octostudios/rarcompat/entities/SparkEntity.java) |
| `SoundRegistry` | class | — | — | 1 | 20 | [SoundRegistry.java](it/hurts/octostudios/rarcompat/init/SoundRegistry.java) |
| `PowerJumpPacket` | class | CustomPacketPayload | — | 8 | 84 | [PowerJumpPacket.java](it/hurts/octostudios/rarcompat/network/packets/PowerJumpPacket.java) |
| `RepulsionUmbrellaPacket` | class | CustomPacketPayload | — | 8 | 78 | [RepulsionUmbrellaPacket.java](it/hurts/octostudios/rarcompat/network/packets/RepulsionUmbrellaPacket.java) |
| `FlamingoSwimPacket` | class | CustomPacketPayload | — | 7 | 80 | [FlamingoSwimPacket.java](it/hurts/octostudios/rarcompat/network/packets/FlamingoSwimPacket.java) |
| `SteadfastSpikesPacket` | class | CustomPacketPayload | — | 8 | 67 | [SteadfastSpikesPacket.java](it/hurts/octostudios/rarcompat/network/packets/SteadfastSpikesPacket.java) |

## 事件处理（`@SubscribeEvent`，39 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `UmbrellaItem` | 4 | `it.hurts.octostudios.rarcompat.items` | [UmbrellaItem.java](it/hurts/octostudios/rarcompat/items/UmbrellaItem.java) |
| `KittySlippersItem` | 4 | `it.hurts.octostudios.rarcompat.items.feet` | [KittySlippersItem.java](it/hurts/octostudios/rarcompat/items/feet/KittySlippersItem.java) |
| `ScarfOfInvisibilityItem` | 4 | `it.hurts.octostudios.rarcompat.items.necklace` | [ScarfOfInvisibilityItem.java](it/hurts/octostudios/rarcompat/items/necklace/ScarfOfInvisibilityItem.java) |
| `PocketPistonItem` | 3 | `it.hurts.octostudios.rarcompat.items.hands` | [PocketPistonItem.java](it/hurts/octostudios/rarcompat/items/hands/PocketPistonItem.java) |
| `DiggingClawsItem` | 3 | `it.hurts.octostudios.rarcompat.items.hands` | [DiggingClawsItem.java](it/hurts/octostudios/rarcompat/items/hands/DiggingClawsItem.java) |
| `RemoteRegistry` | 2 | `it.hurts.octostudios.rarcompat.init` | [RemoteRegistry.java](it/hurts/octostudios/rarcompat/init/RemoteRegistry.java) |
| `MimicHandler` | 2 | `it.hurts.octostudios.rarcompat.handlers` | [MimicHandler.java](it/hurts/octostudios/rarcompat/handlers/MimicHandler.java) |
| `HeliumFlamingoItem` | 2 | `it.hurts.octostudios.rarcompat.items.charm` | [HeliumFlamingoItem.java](it/hurts/octostudios/rarcompat/items/charm/HeliumFlamingoItem.java) |
| `BunnyHoppersItem` | 2 | `it.hurts.octostudios.rarcompat.items.feet` | [BunnyHoppersItem.java](it/hurts/octostudios/rarcompat/items/feet/BunnyHoppersItem.java) |
| `SteadfastSpikesItem` | 2 | `it.hurts.octostudios.rarcompat.items.feet` | [SteadfastSpikesItem.java](it/hurts/octostudios/rarcompat/items/feet/SteadfastSpikesItem.java) |
| `WitheredBraceletItem` | 2 | `it.hurts.octostudios.rarcompat.items.bracelet` | [WitheredBraceletItem.java](it/hurts/octostudios/rarcompat/items/bracelet/WitheredBraceletItem.java) |
| `OnionRingItem` | 2 | `it.hurts.octostudios.rarcompat.items.bracelet` | [OnionRingItem.java](it/hurts/octostudios/rarcompat/items/bracelet/OnionRingItem.java) |
| `ShockPendantItem` | 2 | `it.hurts.octostudios.rarcompat.items.necklace` | [ShockPendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/ShockPendantItem.java) |
| `AntidoteVesselItem` | 2 | `it.hurts.octostudios.rarcompat.items.charm` | [AntidoteVesselItem.java](it/hurts/octostudios/rarcompat/items/charm/AntidoteVesselItem.java) |
| `NetworkHandler` | 1 | `it.hurts.octostudios.rarcompat.network` | [NetworkHandler.java](it/hurts/octostudios/rarcompat/network/NetworkHandler.java) |
| `TooltipHandler` | 1 | `it.hurts.octostudios.rarcompat.handlers` | [TooltipHandler.java](it/hurts/octostudios/rarcompat/handlers/TooltipHandler.java) |
| `CowboyHatItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [CowboyHatItem.java](it/hurts/octostudios/rarcompat/items/hat/CowboyHatItem.java) |
| `UniversalAttractorItem` | 1 | `it.hurts.octostudios.rarcompat.items.charm` | [UniversalAttractorItem.java](it/hurts/octostudios/rarcompat/items/charm/UniversalAttractorItem.java) |
| `ObsidianSkullItem` | 1 | `it.hurts.octostudios.rarcompat.items.charm` | [ObsidianSkullItem.java](it/hurts/octostudios/rarcompat/items/charm/ObsidianSkullItem.java) |
| `NightVisionGogglesItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [NightVisionGogglesItem.java](it/hurts/octostudios/rarcompat/items/hat/NightVisionGogglesItem.java) |
| `PowerGloveItem` | 1 | `it.hurts.octostudios.rarcompat.items.hands` | [PowerGloveItem.java](it/hurts/octostudios/rarcompat/items/hands/PowerGloveItem.java) |
| `PickaxeHeaterItem` | 1 | `it.hurts.octostudios.rarcompat.items.hands` | [PickaxeHeaterItem.java](it/hurts/octostudios/rarcompat/items/hands/PickaxeHeaterItem.java) |
| `FeralClawsItem` | 1 | `it.hurts.octostudios.rarcompat.items.hands` | [FeralClawsItem.java](it/hurts/octostudios/rarcompat/items/hands/FeralClawsItem.java) |
| `PanicNecklaceItem` | 1 | `it.hurts.octostudios.rarcompat.items.necklace` | [PanicNecklaceItem.java](it/hurts/octostudios/rarcompat/items/necklace/PanicNecklaceItem.java) |
| `CharmOfSinkingItem` | 1 | `it.hurts.octostudios.rarcompat.items.necklace` | [CharmOfSinkingItem.java](it/hurts/octostudios/rarcompat/items/necklace/CharmOfSinkingItem.java) |
| `CloudInBottleItem` | 1 | `it.hurts.octostudios.rarcompat.items.charm` | [CloudInBottleItem.java](it/hurts/octostudios/rarcompat/items/charm/CloudInBottleItem.java) |
| `VillagerHatItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [VillagerHatItem.java](it/hurts/octostudios/rarcompat/items/hat/VillagerHatItem.java) |
| `WhoopeeCushionItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [WhoopeeCushionItem.java](it/hurts/octostudios/rarcompat/items/hat/WhoopeeCushionItem.java) |
| `AnglersHatItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [AnglersHatItem.java](it/hurts/octostudios/rarcompat/items/hat/AnglersHatItem.java) |
| `DrinkingHatItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [DrinkingHatItem.java](it/hurts/octostudios/rarcompat/items/hat/DrinkingHatItem.java) |
| `SnorkelItem` | 1 | `it.hurts.octostudios.rarcompat.items.hat` | [SnorkelItem.java](it/hurts/octostudios/rarcompat/items/hat/SnorkelItem.java) |
| `GoldenHookItem` | 1 | `it.hurts.octostudios.rarcompat.items.hands` | [GoldenHookItem.java](it/hurts/octostudios/rarcompat/items/hands/GoldenHookItem.java) |
| `VampiricGloveItem` | 1 | `it.hurts.octostudios.rarcompat.items.hands` | [VampiricGloveItem.java](it/hurts/octostudios/rarcompat/items/hands/VampiricGloveItem.java) |
| `FireGauntletItem` | 1 | `it.hurts.octostudios.rarcompat.items.hands` | [FireGauntletItem.java](it/hurts/octostudios/rarcompat/items/hands/FireGauntletItem.java) |
| `ThornPendantItem` | 1 | `it.hurts.octostudios.rarcompat.items.necklace` | [ThornPendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/ThornPendantItem.java) |
| `FlamePendantItem` | 1 | `it.hurts.octostudios.rarcompat.items.necklace` | [FlamePendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/FlamePendantItem.java) |
| `CrossNecklaceItem` | 1 | `it.hurts.octostudios.rarcompat.items.necklace` | [CrossNecklaceItem.java](it/hurts/octostudios/rarcompat/items/necklace/CrossNecklaceItem.java) |
| `ChorusTotemItem` | 1 | `it.hurts.octostudios.rarcompat.items.charm` | [ChorusTotemItem.java](it/hurts/octostudios/rarcompat/items/charm/ChorusTotemItem.java) |
| `CrystalHeartItem` | 1 | `it.hurts.octostudios.rarcompat.items.charm` | [CrystalHeartItem.java](it/hurts/octostudios/rarcompat/items/charm/CrystalHeartItem.java) |

## Mixin（11 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ConfigManagerMixin` | `ConfigManager` | [ConfigManagerMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ConfigManagerMixin.java) |
| `ModConfigMixin` | `ModConfig` | [ModConfigMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ModConfigMixin.java) |
| `RollLootTableModifierMixin` | `RollLootTableModifier` | [RollLootTableModifierMixin.java](it/hurts/octostudios/rarcompat/mixin/RollLootTableModifierMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](it/hurts/octostudios/rarcompat/mixin/LivingEntityMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](it/hurts/octostudios/rarcompat/mixin/render/GameRendererMixin.java) |
| `ModItemsMixin` | `ModItems` | [ModItemsMixin.java](it/hurts/octostudios/rarcompat/mixin/init/ModItemsMixin.java) |
| `AbstractHorseMixin` | `AbstractHorse` | [AbstractHorseMixin.java](it/hurts/octostudios/rarcompat/mixin/AbstractHorseMixin.java) |
| `MobMixin` | `Mob` | [MobMixin.java](it/hurts/octostudios/rarcompat/mixin/MobMixin.java) |
| `AbstractVillagerMixin` | `AbstractVillager` | [AbstractVillagerMixin.java](it/hurts/octostudios/rarcompat/mixin/AbstractVillagerMixin.java) |
| `BeltModelMixin` | `BeltModel` | [BeltModelMixin.java](it/hurts/octostudios/rarcompat/mixin/render/BeltModelMixin.java) |
| `HumanoidModelMixin` | `HumanoidModel` | [HumanoidModelMixin.java](it/hurts/octostudios/rarcompat/mixin/render/HumanoidModelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigManagerMixin` | Mixin | [ConfigManagerMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ConfigManagerMixin.java) |
| `ModConfigMixin` | Mixin | [ModConfigMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ModConfigMixin.java) |

## 全部类（71）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NetworkHandler` | class | — | EventBusSubscriber | 1 | 26 | [NetworkHandler.java](it/hurts/octostudios/rarcompat/network/NetworkHandler.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 2 | 26 | [RemoteRegistry.java](it/hurts/octostudios/rarcompat/init/RemoteRegistry.java) |
| `TooltipHandler` | class | — | EventBusSubscriber | 1 | 36 | [TooltipHandler.java](it/hurts/octostudios/rarcompat/handlers/TooltipHandler.java) |
| `MimicHandler` | class | — | EventBusSubscriber | 2 | 68 | [MimicHandler.java](it/hurts/octostudios/rarcompat/handlers/MimicHandler.java) |
| `ConfigManagerMixin` | class | — | Mixin | 2 | 21 | [ConfigManagerMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ConfigManagerMixin.java) |
| `RARCompat` | class | — | Mod | 0 | 21 | [RARCompat.java](it/hurts/octostudios/rarcompat/RARCompat.java) |
| `UmbrellaItem` | class | WearableRelicItem | — | 20 | 383 | [UmbrellaItem.java](it/hurts/octostudios/rarcompat/items/UmbrellaItem.java) |
| `ModConfigMixin` | class | — | Mixin | 1 | 16 | [ModConfigMixin.java](it/hurts/octostudios/rarcompat/mixin/config/ModConfigMixin.java) |
| `CowboyHatItem` | class | WearableRelicItem | — | 13 | 304 | [CowboyHatItem.java](it/hurts/octostudios/rarcompat/items/hat/CowboyHatItem.java) |
| `HeliumFlamingoItem` | class | WearableRelicItem | — | 10 | 215 | [HeliumFlamingoItem.java](it/hurts/octostudios/rarcompat/items/charm/HeliumFlamingoItem.java) |
| `UniversalAttractorItem` | class | WearableRelicItem | — | 11 | 230 | [UniversalAttractorItem.java](it/hurts/octostudios/rarcompat/items/charm/UniversalAttractorItem.java) |
| `RollLootTableModifierMixin` | class | — | Mixin | 1 | 43 | [RollLootTableModifierMixin.java](it/hurts/octostudios/rarcompat/mixin/RollLootTableModifierMixin.java) |
| `KittySlippersItem` | class | WearableRelicItem | — | 7 | 293 | [KittySlippersItem.java](it/hurts/octostudios/rarcompat/items/feet/KittySlippersItem.java) |
| `PocketPistonItem` | class | WearableRelicItem | — | 6 | 221 | [PocketPistonItem.java](it/hurts/octostudios/rarcompat/items/hands/PocketPistonItem.java) |
| `ScarfOfInvisibilityItem` | class | WearableRelicItem | — | 10 | 157 | [ScarfOfInvisibilityItem.java](it/hurts/octostudios/rarcompat/items/necklace/ScarfOfInvisibilityItem.java) |
| `ObsidianSkullItem` | class | WearableRelicItem | — | 13 | 198 | [ObsidianSkullItem.java](it/hurts/octostudios/rarcompat/items/charm/ObsidianSkullItem.java) |
| `LivingEntityMixin` | class | — | Mixin | 1 | 95 | [LivingEntityMixin.java](it/hurts/octostudios/rarcompat/mixin/LivingEntityMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 1 | 30 | [GameRendererMixin.java](it/hurts/octostudios/rarcompat/mixin/render/GameRendererMixin.java) |
| `ModItemsMixin` | class | — | Mixin | 4 | 132 | [ModItemsMixin.java](it/hurts/octostudios/rarcompat/mixin/init/ModItemsMixin.java) |
| `ItemRegistry` | class | — | — | 1 | 18 | [ItemRegistry.java](it/hurts/octostudios/rarcompat/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 22 | [EntityRegistry.java](it/hurts/octostudios/rarcompat/init/EntityRegistry.java) |
| `BunnyHoppersItem` | class | WearableRelicItem | — | 8 | 188 | [BunnyHoppersItem.java](it/hurts/octostudios/rarcompat/items/feet/BunnyHoppersItem.java) |
| `NightVisionGogglesItem` | class | WearableRelicItem | — | 5 | 153 | [NightVisionGogglesItem.java](it/hurts/octostudios/rarcompat/items/hat/NightVisionGogglesItem.java) |
| `PowerGloveItem` | class | WearableRelicItem | — | 6 | 152 | [PowerGloveItem.java](it/hurts/octostudios/rarcompat/items/hands/PowerGloveItem.java) |
| `PickaxeHeaterItem` | class | WearableRelicItem | — | 7 | 189 | [PickaxeHeaterItem.java](it/hurts/octostudios/rarcompat/items/hands/PickaxeHeaterItem.java) |
| `DiggingClawsItem` | class | WearableRelicItem | — | 6 | 163 | [DiggingClawsItem.java](it/hurts/octostudios/rarcompat/items/hands/DiggingClawsItem.java) |
| `FeralClawsItem` | class | WearableRelicItem | — | 9 | 162 | [FeralClawsItem.java](it/hurts/octostudios/rarcompat/items/hands/FeralClawsItem.java) |
| `PanicNecklaceItem` | class | WearableRelicItem | — | 7 | 141 | [PanicNecklaceItem.java](it/hurts/octostudios/rarcompat/items/necklace/PanicNecklaceItem.java) |
| `CharmOfSinkingItem` | class | WearableRelicItem | — | 6 | 182 | [CharmOfSinkingItem.java](it/hurts/octostudios/rarcompat/items/necklace/CharmOfSinkingItem.java) |
| `CloudInBottleItem` | class | WearableRelicItem | — | 6 | 144 | [CloudInBottleItem.java](it/hurts/octostudios/rarcompat/items/charm/CloudInBottleItem.java) |
| `DataComponentRegistry` | class | — | — | 1 | 20 | [DataComponentRegistry.java](it/hurts/octostudios/rarcompat/init/DataComponentRegistry.java) |
| `SteadfastSpikesItem` | class | WearableRelicItem | — | 4 | 128 | [SteadfastSpikesItem.java](it/hurts/octostudios/rarcompat/items/feet/SteadfastSpikesItem.java) |
| `VillagerHatItem` | class | WearableRelicItem | — | 3 | 147 | [VillagerHatItem.java](it/hurts/octostudios/rarcompat/items/hat/VillagerHatItem.java) |
| `WhoopeeCushionItem` | class | WearableRelicItem | — | 4 | 176 | [WhoopeeCushionItem.java](it/hurts/octostudios/rarcompat/items/hat/WhoopeeCushionItem.java) |
| `AnglersHatItem` | class | WearableRelicItem | — | 2 | 144 | [AnglersHatItem.java](it/hurts/octostudios/rarcompat/items/hat/AnglersHatItem.java) |
| `DrinkingHatItem` | class | WearableRelicItem | — | 4 | 178 | [DrinkingHatItem.java](it/hurts/octostudios/rarcompat/items/hat/DrinkingHatItem.java) |
| `SnorkelItem` | class | WearableRelicItem | — | 3 | 138 | [SnorkelItem.java](it/hurts/octostudios/rarcompat/items/hat/SnorkelItem.java) |
| `WitheredBraceletItem` | class | WearableRelicItem | — | 3 | 158 | [WitheredBraceletItem.java](it/hurts/octostudios/rarcompat/items/bracelet/WitheredBraceletItem.java) |
| `OnionRingItem` | class | WearableRelicItem | — | 3 | 149 | [OnionRingItem.java](it/hurts/octostudios/rarcompat/items/bracelet/OnionRingItem.java) |
| `GoldenHookItem` | class | WearableRelicItem | — | 2 | 109 | [GoldenHookItem.java](it/hurts/octostudios/rarcompat/items/hands/GoldenHookItem.java) |
| `VampiricGloveItem` | class | WearableRelicItem | — | 2 | 104 | [VampiricGloveItem.java](it/hurts/octostudios/rarcompat/items/hands/VampiricGloveItem.java) |
| `FireGauntletItem` | class | WearableRelicItem | — | 2 | 150 | [FireGauntletItem.java](it/hurts/octostudios/rarcompat/items/hands/FireGauntletItem.java) |
| `ThornPendantItem` | class | WearableRelicItem | — | 2 | 141 | [ThornPendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/ThornPendantItem.java) |
| `FlamePendantItem` | class | WearableRelicItem | — | 2 | 129 | [FlamePendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/FlamePendantItem.java) |
| `ShockPendantItem` | class | WearableRelicItem | — | 3 | 181 | [ShockPendantItem.java](it/hurts/octostudios/rarcompat/items/necklace/ShockPendantItem.java) |
| `CrossNecklaceItem` | class | WearableRelicItem | — | 3 | 113 | [CrossNecklaceItem.java](it/hurts/octostudios/rarcompat/items/necklace/CrossNecklaceItem.java) |
| `AntidoteVesselItem` | class | WearableRelicItem | — | 3 | 138 | [AntidoteVesselItem.java](it/hurts/octostudios/rarcompat/items/charm/AntidoteVesselItem.java) |
| `ChorusTotemItem` | class | WearableRelicItem | — | 4 | 175 | [ChorusTotemItem.java](it/hurts/octostudios/rarcompat/items/charm/ChorusTotemItem.java) |
| `CrystalHeartItem` | class | WearableRelicItem | — | 4 | 107 | [CrystalHeartItem.java](it/hurts/octostudios/rarcompat/items/charm/CrystalHeartItem.java) |
| `AbstractHorseMixin` | class | — | Mixin | 1 | 30 | [AbstractHorseMixin.java](it/hurts/octostudios/rarcompat/mixin/AbstractHorseMixin.java) |
| `MobMixin` | class | — | Mixin | 1 | 29 | [MobMixin.java](it/hurts/octostudios/rarcompat/mixin/MobMixin.java) |
| `AbstractVillagerMixin` | class | — | Mixin | 2 | 48 | [AbstractVillagerMixin.java](it/hurts/octostudios/rarcompat/mixin/AbstractVillagerMixin.java) |
| `BeltModelMixin` | class | — | Mixin | 1 | 24 | [BeltModelMixin.java](it/hurts/octostudios/rarcompat/mixin/render/BeltModelMixin.java) |
| `HumanoidModelMixin` | class | — | Mixin | 1 | 42 | [HumanoidModelMixin.java](it/hurts/octostudios/rarcompat/mixin/render/HumanoidModelMixin.java) |
| `SparkEntity` | class | ThrowableProjectile | — | 22 | 182 | [SparkEntity.java](it/hurts/octostudios/rarcompat/entities/SparkEntity.java) |
| `SoundRegistry` | class | — | — | 1 | 20 | [SoundRegistry.java](it/hurts/octostudios/rarcompat/init/SoundRegistry.java) |
| `PowerJumpPacket` | class | CustomPacketPayload | — | 8 | 84 | [PowerJumpPacket.java](it/hurts/octostudios/rarcompat/network/packets/PowerJumpPacket.java) |
| `RepulsionUmbrellaPacket` | class | CustomPacketPayload | — | 8 | 78 | [RepulsionUmbrellaPacket.java](it/hurts/octostudios/rarcompat/network/packets/RepulsionUmbrellaPacket.java) |
| `FlamingoSwimPacket` | class | CustomPacketPayload | — | 7 | 80 | [FlamingoSwimPacket.java](it/hurts/octostudios/rarcompat/network/packets/FlamingoSwimPacket.java) |
| `SteadfastSpikesPacket` | class | CustomPacketPayload | — | 8 | 67 | [SteadfastSpikesPacket.java](it/hurts/octostudios/rarcompat/network/packets/SteadfastSpikesPacket.java) |
| `WarpDriveItem` | class | WearableRelicItem | — | 5 | 214 | [WarpDriveItem.java](it/hurts/octostudios/rarcompat/items/charm/WarpDriveItem.java) |
| `DoubleJumpPacket` | class | CustomPacketPayload | — | 4 | 64 | [DoubleJumpPacket.java](it/hurts/octostudios/rarcompat/network/packets/DoubleJumpPacket.java) |
| `SnowshoesItem` | class | WearableRelicItem | — | 8 | 144 | [SnowshoesItem.java](it/hurts/octostudios/rarcompat/items/feet/SnowshoesItem.java) |
| `CharmOfShrinkingItem` | class | WearableRelicItem | — | 5 | 102 | [CharmOfShrinkingItem.java](it/hurts/octostudios/rarcompat/items/necklace/CharmOfShrinkingItem.java) |
| `MimiDustItem` | class | Item | — | 3 | 106 | [MimiDustItem.java](it/hurts/octostudios/rarcompat/items/MimiDustItem.java) |
| `WearableRelicItem` | class | RelicItem | — | 3 | 31 | [WearableRelicItem.java](it/hurts/octostudios/rarcompat/items/WearableRelicItem.java) |
| `RootedBootsItem` | class | WearableRelicItem | — | 2 | 124 | [RootedBootsItem.java](it/hurts/octostudios/rarcompat/items/feet/RootedBootsItem.java) |
| `FlippersItem` | class | WearableRelicItem | — | 3 | 101 | [FlippersItem.java](it/hurts/octostudios/rarcompat/items/feet/FlippersItem.java) |
| `RunningShoesItem` | class | WearableRelicItem | — | 3 | 120 | [RunningShoesItem.java](it/hurts/octostudios/rarcompat/items/feet/RunningShoesItem.java) |
| `SuperstitiousHatItem` | class | WearableRelicItem | — | 2 | 118 | [SuperstitiousHatItem.java](it/hurts/octostudios/rarcompat/items/hat/SuperstitiousHatItem.java) |
| `LuckyScarfItem` | class | WearableRelicItem | — | 2 | 100 | [LuckyScarfItem.java](it/hurts/octostudios/rarcompat/items/necklace/LuckyScarfItem.java) |
