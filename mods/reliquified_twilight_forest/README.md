# Reliquified Twilight Forest（`reliquified_twilight_forest`）

> jar：`[遗物×暮色]reliquifiedtwilightforest.jar` ｜ 版本：`0.5.3` ｜ 作者：Octo-Studios
> 规模：**76 个类 / 21 个包 / 7044 行** ｜ 事件处理类 17 ｜ Mixin 8 ｜ 注册/管理类 11

**依赖**：`minecraft`、`neoforge`、`curios`、`octolib`、`relics`、`twilightforest`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | 18 | `LichCrownItem`、`DeerAntlerItem`、`Parasite115Item` |
| `it.hurts.octostudios.reliquified_twilight_forest.init` | 10 | `PacketRegistry`、`RemoteRegistry`、`ConfigRegistry` |
| `it.hurts.octostudios.reliquified_twilight_forest.network` | 9 | `EntityStartRidingPacket`、`EntityStopRidingPacket`、`LaunchTwilightBoltPacket` |
| `it.hurts.octostudios.reliquified_twilight_forest.mixin` | 8 | `ItemInHandRendererMixin`、`TFItemStackUtilsMixin`、`Experiment115BlockMixin` |
| `it.hurts.octostudios.reliquified_twilight_forest.item` | 7 | `BrokenCharmItem`、`BundleLikeRelicItem`、`GemItem` |
| `it.hurts.octostudios.reliquified_twilight_forest.api` | 3 | `OreCache`、`ExtraDataDamageSource`、`HurtByTargetGoalWithPredicate` |
| `it.hurts.octostudios.reliquified_twilight_forest.client.gui.layer` | 3 | `TwilightIndicatorLayer`、`VignetteLayer`、`RedVignetteLayer` |
| `it.hurts.octostudios.reliquified_twilight_forest.client.gui.tooltip` | 2 | `ClientBundleLikeTooltip`、`ClientChromaticCloakTooltip` |
| `it.hurts.octostudios.reliquified_twilight_forest.loot.modifier` | 2 | `AddItemsWithChanceModifier`、`ModifyItemsModifier` |
| `it.hurts.octostudios.reliquified_twilight_forest.util` | 2 | `MathButCool`、`EntitiesButCool` |
| `it.hurts.octostudios.reliquified_twilight_forest.gui.tooltip` | 2 | `ChromaticCloakTooltip`、`BundleLikeTooltip` |
| `it.hurts.octostudios.reliquified_twilight_forest.handler` | 1 | `BundleLikeEventHandler` |
| `it.hurts.octostudios.reliquified_twilight_forest.item.ability` | 1 | `LichCrownAbilities` |
| `it.hurts.octostudios.reliquified_twilight_forest` | 1 | `ReliquifiedTwilightForest` |
| `it.hurts.octostudios.reliquified_twilight_forest.client.event` | 1 | `RenderItemInHandEvent` |
| `it.hurts.octostudios.reliquified_twilight_forest.entity.projectile` | 1 | `HydraFireEntity` |
| `it.hurts.octostudios.reliquified_twilight_forest.config` | 1 | `RTFConfig` |
| `it.hurts.octostudios.reliquified_twilight_forest.entity` | 1 | `HydraFirePuddleEntity` |
| `it.hurts.octostudios.reliquified_twilight_forest.data.loot` | 1 | `LootEntries` |
| `it.hurts.octostudios.reliquified_twilight_forest.item.component` | 1 | `BundleLikeContents` |
| `it.hurts.octostudios.reliquified_twilight_forest.effect` | 1 | `CicadaInfestationEffect` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PacketRegistry` | class | — | EventBusSubscriber | 1 | 34 | [PacketRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/PacketRegistry.java) |
| `BundleLikeEventHandler` | class | — | EventBusSubscriber | 2 | 50 | [BundleLikeEventHandler.java](it/hurts/octostudios/reliquified_twilight_forest/handler/BundleLikeEventHandler.java) |
| `LichCrownItem` | class | BundleLikeRelicItem | EventBusSubscriber | 24 | 481 | [LichCrownItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/LichCrownItem.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 4 | 53 | [RemoteRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/RemoteRegistry.java) |
| `DeerAntlerItem` | class | RelicItem | EventBusSubscriber | 12 | 357 | [DeerAntlerItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/DeerAntlerItem.java) |
| `Parasite115Item` | class | RelicItem | EventBusSubscriber | 10 | 293 | [Parasite115Item.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/Parasite115Item.java) |
| `MinotaurHoofItem` | class | RelicItem | EventBusSubscriber | 14 | 258 | [MinotaurHoofItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/MinotaurHoofItem.java) |
| `ScaledCloakItem` | class | RelicItem | EventBusSubscriber | 9 | 232 | [ScaledCloakItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ScaledCloakItem.java) |
| `CicadaBottleItem` | class | RelicItem | EventBusSubscriber | 7 | 170 | [CicadaBottleItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/CicadaBottleItem.java) |
| `HydraHeartItem` | class | RelicItem | EventBusSubscriber | 6 | 125 | [HydraHeartItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/HydraHeartItem.java) |
| `MapleSyrupBottleItem` | class | RelicItem | EventBusSubscriber | 6 | 130 | [MapleSyrupBottleItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/MapleSyrupBottleItem.java) |
| `ThornCrownItem` | class | RelicItem | EventBusSubscriber | 4 | 123 | [ThornCrownItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ThornCrownItem.java) |
| `SteelCapeItem` | class | RelicItem | EventBusSubscriber | 3 | 112 | [SteelCapeItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/SteelCapeItem.java) |
| `TwilightFeatherItem` | class | RelicItem | EventBusSubscriber | 4 | 137 | [TwilightFeatherItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/TwilightFeatherItem.java) |
| `LichCrownAbilities` | class | — | — | 29 | 679 | [LichCrownAbilities.java](it/hurts/octostudios/reliquified_twilight_forest/item/ability/LichCrownAbilities.java) |
| `ReliquifiedTwilightForest` | class | — | Mod | 1 | 32 | [ReliquifiedTwilightForest.java](it/hurts/octostudios/reliquified_twilight_forest/ReliquifiedTwilightForest.java) |
| `ItemInHandRendererMixin` | class | — | Mixin | 1 | 58 | [ItemInHandRendererMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/ItemInHandRendererMixin.java) |
| `ConfigRegistry` | class | — | — | 1 | 13 | [ConfigRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ConfigRegistry.java) |
| `TFItemStackUtilsMixin` | class | — | Mixin | 1 | 53 | [TFItemStackUtilsMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/TFItemStackUtilsMixin.java) |
| `GoblinNoseItem` | class | BundleLikeRelicItem | — | 12 | 268 | [GoblinNoseItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/GoblinNoseItem.java) |
| `GlobalLootModifierSerializerCodecRegistry` | class | — | — | 1 | 27 | [GlobalLootModifierSerializerCodecRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/GlobalLootModifierSerializerCodecRegistry.java) |
| `Experiment115BlockMixin` | class | — | Mixin | 1 | 23 | [Experiment115BlockMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/Experiment115BlockMixin.java) |
| `ChainBlockMixin` | class | — | Mixin | 1 | 49 | [ChainBlockMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/ChainBlockMixin.java) |
| `RenderItemInHandEvent` | class | Event | — | 16 | 109 | [RenderItemInHandEvent.java](it/hurts/octostudios/reliquified_twilight_forest/client/event/RenderItemInHandEvent.java) |
| `ItemRegistry` | class | — | — | 1 | 121 | [ItemRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 26 | [EntityRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/EntityRegistry.java) |
| `GiantGloveItem` | class | RelicItem | — | 9 | 182 | [GiantGloveItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/GiantGloveItem.java) |
| `DataComponentRegistry` | class | — | — | 4 | 52 | [DataComponentRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/DataComponentRegistry.java) |
| `HydraFireEntity` | class | ThrowableProjectile | — | 20 | 184 | [HydraFireEntity.java](it/hurts/octostudios/reliquified_twilight_forest/entity/projectile/HydraFireEntity.java) |
| `NearestAttackableTargetGoalAccessor` | interface | — | Mixin | 0 | 12 | [NearestAttackableTargetGoalAccessor.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/NearestAttackableTargetGoalAccessor.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 42 | [HumanoidArmorLayerMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/HumanoidArmorLayerMixin.java) |
| `MovingCicadaSoundInstanceMixin` | class | — | Mixin | 1 | 27 | [MovingCicadaSoundInstanceMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/MovingCicadaSoundInstanceMixin.java) |
| `HurtByTargetGoalAccessor` | interface | — | Mixin | 0 | 15 | [HurtByTargetGoalAccessor.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/HurtByTargetGoalAccessor.java) |
| `EffectRegistry` | class | — | — | 1 | 18 | [EffectRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/EffectRegistry.java) |
| `ParticleRegistry` | class | — | — | 1 | 15 | [ParticleRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ParticleRegistry.java) |
| `DamageTypeRegistry` | class | — | — | 0 | 16 | [DamageTypeRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/DamageTypeRegistry.java) |
| `EntityStartRidingPacket` | record | CustomPacketPayload | — | 3 | 44 | [EntityStartRidingPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/EntityStartRidingPacket.java) |
| `EntityStopRidingPacket` | record | CustomPacketPayload | — | 3 | 40 | [EntityStopRidingPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/EntityStopRidingPacket.java) |
| `RTFConfig` | class | OctoConfig | — | 6 | 47 | [RTFConfig.java](it/hurts/octostudios/reliquified_twilight_forest/config/RTFConfig.java) |
| `LaunchTwilightBoltPacket` | record | CustomPacketPayload | — | 9 | 128 | [LaunchTwilightBoltPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/LaunchTwilightBoltPacket.java) |
| `HydraFirePuddleEntity` | class | Entity | — | 11 | 145 | [HydraFirePuddleEntity.java](it/hurts/octostudios/reliquified_twilight_forest/entity/HydraFirePuddleEntity.java) |
| `ParasiteEvolveParticlePacket` | record | CustomPacketPayload | — | 3 | 58 | [ParasiteEvolveParticlePacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/ParasiteEvolveParticlePacket.java) |
| `LifedrainParticlePacket` | record | CustomPacketPayload | — | 3 | 46 | [LifedrainParticlePacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/LifedrainParticlePacket.java) |
| `CastRideAlongAbilityPacket` | record | CustomPacketPayload | — | 3 | 62 | [CastRideAlongAbilityPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/CastRideAlongAbilityPacket.java) |
| `UpdateChunkPacket` | record | CustomPacketPayload | — | 3 | 40 | [UpdateChunkPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/UpdateChunkPacket.java) |
| `ExecutionEffectPacket` | record | CustomPacketPayload | — | 3 | 65 | [ExecutionEffectPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/ExecutionEffectPacket.java) |
| `ScaledCloakWallClimbPacket` | record | CustomPacketPayload | — | 3 | 64 | [ScaledCloakWallClimbPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/ScaledCloakWallClimbPacket.java) |
| `LootEntries` | class | — | — | 0 | 70 | [LootEntries.java](it/hurts/octostudios/reliquified_twilight_forest/data/loot/LootEntries.java) |
| `ChromaticCloakItem` | class | BundleLikeRelicItem | — | 9 | 173 | [ChromaticCloakItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ChromaticCloakItem.java) |
| `Parasite116Item` | class | Parasite115Item | — | 5 | 130 | [Parasite116Item.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/Parasite116Item.java) |
| `CharmBackpackItem` | class | BundleLikeRelicItem | — | 5 | 94 | [CharmBackpackItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/CharmBackpackItem.java) |
| `ClientBundleLikeTooltip` | class | ClientTooltipComponent | OnlyIn | 8 | 68 | [ClientBundleLikeTooltip.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/tooltip/ClientBundleLikeTooltip.java) |
| `ClientChromaticCloakTooltip` | class | ClientBundleLikeTooltip | OnlyIn | 5 | 54 | [ClientChromaticCloakTooltip.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/tooltip/ClientChromaticCloakTooltip.java) |
| `AddItemsWithChanceModifier` | class | LootModifier | — | 2 | 43 | [AddItemsWithChanceModifier.java](it/hurts/octostudios/reliquified_twilight_forest/loot/modifier/AddItemsWithChanceModifier.java) |
| `ModifyItemsModifier` | class | LootModifier | — | 2 | 43 | [ModifyItemsModifier.java](it/hurts/octostudios/reliquified_twilight_forest/loot/modifier/ModifyItemsModifier.java) |
| `BrokenCharmItem` | class | Item | — | 1 | 27 | [BrokenCharmItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/BrokenCharmItem.java) |
| `BundleLikeRelicItem` | class | BundleLikeItem | — | 4 | 31 | [BundleLikeRelicItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/BundleLikeRelicItem.java) |
| `GemItem` | class | Item | — | 4 | 45 | [GemItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/GemItem.java) |
| `WandGemItem` | class | GemItem | — | 1 | 14 | [WandGemItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/WandGemItem.java) |
| `BundleLikeItem` | class | Item | — | 2 | 32 | [BundleLikeItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/BundleLikeItem.java) |

## 事件处理（`@SubscribeEvent`，17 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `LichCrownAbilities` | 13 | `it.hurts.octostudios.reliquified_twilight_forest.item.ability` | [LichCrownAbilities.java](it/hurts/octostudios/reliquified_twilight_forest/item/ability/LichCrownAbilities.java) |
| `GoblinNoseItem` | 5 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [GoblinNoseItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/GoblinNoseItem.java) |
| `RemoteRegistry` | 4 | `it.hurts.octostudios.reliquified_twilight_forest.init` | [RemoteRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/RemoteRegistry.java) |
| `Parasite115Item` | 4 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [Parasite115Item.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/Parasite115Item.java) |
| `CicadaBottleItem` | 4 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [CicadaBottleItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/CicadaBottleItem.java) |
| `GiantGloveItem` | 4 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [GiantGloveItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/GiantGloveItem.java) |
| `MapleSyrupBottleItem` | 3 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [MapleSyrupBottleItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/MapleSyrupBottleItem.java) |
| `LichCrownItem` | 2 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [LichCrownItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/LichCrownItem.java) |
| `DeerAntlerItem` | 2 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [DeerAntlerItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/DeerAntlerItem.java) |
| `PacketRegistry` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.init` | [PacketRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/PacketRegistry.java) |
| `BundleLikeEventHandler` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.handler` | [BundleLikeEventHandler.java](it/hurts/octostudios/reliquified_twilight_forest/handler/BundleLikeEventHandler.java) |
| `MinotaurHoofItem` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [MinotaurHoofItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/MinotaurHoofItem.java) |
| `ScaledCloakItem` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [ScaledCloakItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ScaledCloakItem.java) |
| `HydraHeartItem` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [HydraHeartItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/HydraHeartItem.java) |
| `ThornCrownItem` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [ThornCrownItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ThornCrownItem.java) |
| `SteelCapeItem` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [SteelCapeItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/SteelCapeItem.java) |
| `TwilightFeatherItem` | 1 | `it.hurts.octostudios.reliquified_twilight_forest.item.relic` | [TwilightFeatherItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/TwilightFeatherItem.java) |

## Mixin（8 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemInHandRendererMixin` | `ItemInHandRenderer` | [ItemInHandRendererMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/ItemInHandRendererMixin.java) |
| `TFItemStackUtilsMixin` | `TFItemStackUtils` | [TFItemStackUtilsMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/TFItemStackUtilsMixin.java) |
| `Experiment115BlockMixin` | `Experiment115Block` | [Experiment115BlockMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/Experiment115BlockMixin.java) |
| `ChainBlockMixin` | `ChainBlock` | [ChainBlockMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/ChainBlockMixin.java) |
| `NearestAttackableTargetGoalAccessor` | `NearestAttackableTargetGoalAccessor` | [NearestAttackableTargetGoalAccessor.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/NearestAttackableTargetGoalAccessor.java) |
| `HumanoidArmorLayerMixin` | `HumanoidArmorLayer` | [HumanoidArmorLayerMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/HumanoidArmorLayerMixin.java) |
| `MovingCicadaSoundInstanceMixin` | `MovingCicadaSoundInstance` | [MovingCicadaSoundInstanceMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/MovingCicadaSoundInstanceMixin.java) |
| `HurtByTargetGoalAccessor` | `HurtByTargetGoalAccessor` | [HurtByTargetGoalAccessor.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/HurtByTargetGoalAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigRegistry` | — | [ConfigRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ConfigRegistry.java) |
| `RTFConfig` | — | [RTFConfig.java](it/hurts/octostudios/reliquified_twilight_forest/config/RTFConfig.java) |

## 全部类（76）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `PacketRegistry` | class | — | EventBusSubscriber | 1 | 34 | [PacketRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/PacketRegistry.java) |
| `BundleLikeEventHandler` | class | — | EventBusSubscriber | 2 | 50 | [BundleLikeEventHandler.java](it/hurts/octostudios/reliquified_twilight_forest/handler/BundleLikeEventHandler.java) |
| `LichCrownItem` | class | BundleLikeRelicItem | EventBusSubscriber | 24 | 481 | [LichCrownItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/LichCrownItem.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 4 | 53 | [RemoteRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/RemoteRegistry.java) |
| `DeerAntlerItem` | class | RelicItem | EventBusSubscriber | 12 | 357 | [DeerAntlerItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/DeerAntlerItem.java) |
| `Parasite115Item` | class | RelicItem | EventBusSubscriber | 10 | 293 | [Parasite115Item.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/Parasite115Item.java) |
| `MinotaurHoofItem` | class | RelicItem | EventBusSubscriber | 14 | 258 | [MinotaurHoofItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/MinotaurHoofItem.java) |
| `ScaledCloakItem` | class | RelicItem | EventBusSubscriber | 9 | 232 | [ScaledCloakItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ScaledCloakItem.java) |
| `CicadaBottleItem` | class | RelicItem | EventBusSubscriber | 7 | 170 | [CicadaBottleItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/CicadaBottleItem.java) |
| `HydraHeartItem` | class | RelicItem | EventBusSubscriber | 6 | 125 | [HydraHeartItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/HydraHeartItem.java) |
| `MapleSyrupBottleItem` | class | RelicItem | EventBusSubscriber | 6 | 130 | [MapleSyrupBottleItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/MapleSyrupBottleItem.java) |
| `ThornCrownItem` | class | RelicItem | EventBusSubscriber | 4 | 123 | [ThornCrownItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ThornCrownItem.java) |
| `SteelCapeItem` | class | RelicItem | EventBusSubscriber | 3 | 112 | [SteelCapeItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/SteelCapeItem.java) |
| `TwilightFeatherItem` | class | RelicItem | EventBusSubscriber | 4 | 137 | [TwilightFeatherItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/TwilightFeatherItem.java) |
| `LichCrownAbilities` | class | — | — | 29 | 679 | [LichCrownAbilities.java](it/hurts/octostudios/reliquified_twilight_forest/item/ability/LichCrownAbilities.java) |
| `ReliquifiedTwilightForest` | class | — | Mod | 1 | 32 | [ReliquifiedTwilightForest.java](it/hurts/octostudios/reliquified_twilight_forest/ReliquifiedTwilightForest.java) |
| `ItemInHandRendererMixin` | class | — | Mixin | 1 | 58 | [ItemInHandRendererMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/ItemInHandRendererMixin.java) |
| `ConfigRegistry` | class | — | — | 1 | 13 | [ConfigRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ConfigRegistry.java) |
| `TFItemStackUtilsMixin` | class | — | Mixin | 1 | 53 | [TFItemStackUtilsMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/TFItemStackUtilsMixin.java) |
| `GoblinNoseItem` | class | BundleLikeRelicItem | — | 12 | 268 | [GoblinNoseItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/GoblinNoseItem.java) |
| `GlobalLootModifierSerializerCodecRegistry` | class | — | — | 1 | 27 | [GlobalLootModifierSerializerCodecRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/GlobalLootModifierSerializerCodecRegistry.java) |
| `Experiment115BlockMixin` | class | — | Mixin | 1 | 23 | [Experiment115BlockMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/Experiment115BlockMixin.java) |
| `ChainBlockMixin` | class | — | Mixin | 1 | 49 | [ChainBlockMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/ChainBlockMixin.java) |
| `RenderItemInHandEvent` | class | Event | — | 16 | 109 | [RenderItemInHandEvent.java](it/hurts/octostudios/reliquified_twilight_forest/client/event/RenderItemInHandEvent.java) |
| `ItemRegistry` | class | — | — | 1 | 121 | [ItemRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 26 | [EntityRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/EntityRegistry.java) |
| `GiantGloveItem` | class | RelicItem | — | 9 | 182 | [GiantGloveItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/GiantGloveItem.java) |
| `DataComponentRegistry` | class | — | — | 4 | 52 | [DataComponentRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/DataComponentRegistry.java) |
| `HydraFireEntity` | class | ThrowableProjectile | — | 20 | 184 | [HydraFireEntity.java](it/hurts/octostudios/reliquified_twilight_forest/entity/projectile/HydraFireEntity.java) |
| `NearestAttackableTargetGoalAccessor` | interface | — | Mixin | 0 | 12 | [NearestAttackableTargetGoalAccessor.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/NearestAttackableTargetGoalAccessor.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 1 | 42 | [HumanoidArmorLayerMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/HumanoidArmorLayerMixin.java) |
| `MovingCicadaSoundInstanceMixin` | class | — | Mixin | 1 | 27 | [MovingCicadaSoundInstanceMixin.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/MovingCicadaSoundInstanceMixin.java) |
| `HurtByTargetGoalAccessor` | interface | — | Mixin | 0 | 15 | [HurtByTargetGoalAccessor.java](it/hurts/octostudios/reliquified_twilight_forest/mixin/HurtByTargetGoalAccessor.java) |
| `EffectRegistry` | class | — | — | 1 | 18 | [EffectRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/EffectRegistry.java) |
| `ParticleRegistry` | class | — | — | 1 | 15 | [ParticleRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/ParticleRegistry.java) |
| `DamageTypeRegistry` | class | — | — | 0 | 16 | [DamageTypeRegistry.java](it/hurts/octostudios/reliquified_twilight_forest/init/DamageTypeRegistry.java) |
| `EntityStartRidingPacket` | record | CustomPacketPayload | — | 3 | 44 | [EntityStartRidingPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/EntityStartRidingPacket.java) |
| `EntityStopRidingPacket` | record | CustomPacketPayload | — | 3 | 40 | [EntityStopRidingPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/EntityStopRidingPacket.java) |
| `RTFConfig` | class | OctoConfig | — | 6 | 47 | [RTFConfig.java](it/hurts/octostudios/reliquified_twilight_forest/config/RTFConfig.java) |
| `LaunchTwilightBoltPacket` | record | CustomPacketPayload | — | 9 | 128 | [LaunchTwilightBoltPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/LaunchTwilightBoltPacket.java) |
| `HydraFirePuddleEntity` | class | Entity | — | 11 | 145 | [HydraFirePuddleEntity.java](it/hurts/octostudios/reliquified_twilight_forest/entity/HydraFirePuddleEntity.java) |
| `ParasiteEvolveParticlePacket` | record | CustomPacketPayload | — | 3 | 58 | [ParasiteEvolveParticlePacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/ParasiteEvolveParticlePacket.java) |
| `LifedrainParticlePacket` | record | CustomPacketPayload | — | 3 | 46 | [LifedrainParticlePacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/LifedrainParticlePacket.java) |
| `CastRideAlongAbilityPacket` | record | CustomPacketPayload | — | 3 | 62 | [CastRideAlongAbilityPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/CastRideAlongAbilityPacket.java) |
| `UpdateChunkPacket` | record | CustomPacketPayload | — | 3 | 40 | [UpdateChunkPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/UpdateChunkPacket.java) |
| `ExecutionEffectPacket` | record | CustomPacketPayload | — | 3 | 65 | [ExecutionEffectPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/ExecutionEffectPacket.java) |
| `ScaledCloakWallClimbPacket` | record | CustomPacketPayload | — | 3 | 64 | [ScaledCloakWallClimbPacket.java](it/hurts/octostudios/reliquified_twilight_forest/network/ScaledCloakWallClimbPacket.java) |
| `LootEntries` | class | — | — | 0 | 70 | [LootEntries.java](it/hurts/octostudios/reliquified_twilight_forest/data/loot/LootEntries.java) |
| `ChromaticCloakItem` | class | BundleLikeRelicItem | — | 9 | 173 | [ChromaticCloakItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/ChromaticCloakItem.java) |
| `Parasite116Item` | class | Parasite115Item | — | 5 | 130 | [Parasite116Item.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/Parasite116Item.java) |
| `CharmBackpackItem` | class | BundleLikeRelicItem | — | 5 | 94 | [CharmBackpackItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/CharmBackpackItem.java) |
| `ClientBundleLikeTooltip` | class | ClientTooltipComponent | OnlyIn | 8 | 68 | [ClientBundleLikeTooltip.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/tooltip/ClientBundleLikeTooltip.java) |
| `ClientChromaticCloakTooltip` | class | ClientBundleLikeTooltip | OnlyIn | 5 | 54 | [ClientChromaticCloakTooltip.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/tooltip/ClientChromaticCloakTooltip.java) |
| `AddItemsWithChanceModifier` | class | LootModifier | — | 2 | 43 | [AddItemsWithChanceModifier.java](it/hurts/octostudios/reliquified_twilight_forest/loot/modifier/AddItemsWithChanceModifier.java) |
| `ModifyItemsModifier` | class | LootModifier | — | 2 | 43 | [ModifyItemsModifier.java](it/hurts/octostudios/reliquified_twilight_forest/loot/modifier/ModifyItemsModifier.java) |
| `BrokenCharmItem` | class | Item | — | 1 | 27 | [BrokenCharmItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/BrokenCharmItem.java) |
| `BundleLikeRelicItem` | class | BundleLikeItem | — | 4 | 31 | [BundleLikeRelicItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/BundleLikeRelicItem.java) |
| `GemItem` | class | Item | — | 4 | 45 | [GemItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/GemItem.java) |
| `WandGemItem` | class | GemItem | — | 1 | 14 | [WandGemItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/WandGemItem.java) |
| `BundleLikeItem` | class | Item | — | 2 | 32 | [BundleLikeItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/BundleLikeItem.java) |
| `FireflyQueenItem` | class | RelicItem | — | 3 | 116 | [FireflyQueenItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/FireflyQueenItem.java) |
| `InvisibilityCloakItem` | class | RelicItem | — | 3 | 93 | [InvisibilityCloakItem.java](it/hurts/octostudios/reliquified_twilight_forest/item/relic/InvisibilityCloakItem.java) |
| `OreCache` | class | — | — | 5 | 82 | [OreCache.java](it/hurts/octostudios/reliquified_twilight_forest/api/OreCache.java) |
| `ExtraDataDamageSource` | class | DamageSource | — | 1 | 44 | [ExtraDataDamageSource.java](it/hurts/octostudios/reliquified_twilight_forest/api/ExtraDataDamageSource.java) |
| `BundleLikeContents` | class | TooltipComponent | — | 9 | 75 | [BundleLikeContents.java](it/hurts/octostudios/reliquified_twilight_forest/item/component/BundleLikeContents.java) |
| `MathButCool` | class | — | — | 6 | 30 | [MathButCool.java](it/hurts/octostudios/reliquified_twilight_forest/util/MathButCool.java) |
| `ChromaticCloakTooltip` | class | BundleLikeTooltip | — | 0 | 11 | [ChromaticCloakTooltip.java](it/hurts/octostudios/reliquified_twilight_forest/gui/tooltip/ChromaticCloakTooltip.java) |
| `BundleLikeTooltip` | class | TooltipComponent | — | 0 | 15 | [BundleLikeTooltip.java](it/hurts/octostudios/reliquified_twilight_forest/gui/tooltip/BundleLikeTooltip.java) |
| `HurtByTargetGoalWithPredicate` | class | HurtByTargetGoal | — | 1 | 39 | [HurtByTargetGoalWithPredicate.java](it/hurts/octostudios/reliquified_twilight_forest/api/HurtByTargetGoalWithPredicate.java) |
| `Gem` | interface | — | — | 0 | 5 | [Gem.java](it/hurts/octostudios/reliquified_twilight_forest/item/Gem.java) |
| `BundleLike` | interface | — | — | 0 | 178 | [BundleLike.java](it/hurts/octostudios/reliquified_twilight_forest/item/BundleLike.java) |
| `EntitiesButCool` | class | — | — | 4 | 39 | [EntitiesButCool.java](it/hurts/octostudios/reliquified_twilight_forest/util/EntitiesButCool.java) |
| `TwilightIndicatorLayer` | class | Layer | — | 1 | 61 | [TwilightIndicatorLayer.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/layer/TwilightIndicatorLayer.java) |
| `VignetteLayer` | class | Layer | — | 1 | 49 | [VignetteLayer.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/layer/VignetteLayer.java) |
| `RedVignetteLayer` | class | Layer | — | 1 | 50 | [RedVignetteLayer.java](it/hurts/octostudios/reliquified_twilight_forest/client/gui/layer/RedVignetteLayer.java) |
| `CicadaInfestationEffect` | class | MobEffect | — | 1 | 18 | [CicadaInfestationEffect.java](it/hurts/octostudios/reliquified_twilight_forest/effect/CicadaInfestationEffect.java) |
